package DAO;

import java.math.BigInteger;
//import pour manipuler la bd et la colllection ArrayList
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import des classes métiers
import metier.Personnel;
import metier.Calendrier;
import metier.Creneau;

/**
 * Hérite de DAO.
 * Class qui permet de gérer les personnels de la table personnel de la BD.
 * Il est possible d'insérer, supprimer et modifier un personnel
 */
public class PersonnelDAO extends DAO<Personnel> {

    /**
     * Constructeur par défaut.
     */
    public PersonnelDAO() {
    }

    /**
     * Lance la requête qui sert à insérer un personnel dans la DataBase.
     * 
     * @param pesr Objet personnel à insérer dans la BD.
     * @return l'objet Personnel pers.
     */
    public static Personnel create(Personnel pers) {
        String requetePersonnel = "INSERT INTO personnel (nom, prenom, dateNaissance, tempsTravailMensuel, photo, nomFonction)VALUES ('"
                + pers.getNom() + "','" + pers.getPrenom() + "','" + pers.getDateNaissance() + "',"
                + pers.getTempsTravailMensuel() + ",'" + pers.getPhotoProfil() + "','" + pers.getFonction() + "');";// requêtes
                                                                                                                    // d'insertion
                                                                                                                    // dans
                                                                                                                    // la
                                                                                                                    // table
                                                                                                                    // Personnel
        System.out.println("INSERTION DU PERSONNEL");
        try {
            stmt.executeUpdate(requetePersonnel, Statement.RETURN_GENERATED_KEYS);// On exécute la requête
            ResultSet cles = stmt.getGeneratedKeys();// Les cles auto-générées sont retournées sous forme de ResultSet
            if (cles.next()) {
                long id = ((BigInteger) cles.getObject(1)).longValue(); // On stock la valeur de la permière colonne du
                                                                        // resultat (id du personnel)
                pers.setId(id);// On set l'id (il n'existait pas car c'est la bd qui le génère automatiquement)
                System.out.println("->id du personnel mis à jour-> " + pers.getId());
                System.out.println("->Personnel n°" + pers.getId() + " ajouté");
            }
            insertRelations(pers);
        } catch (SQLException e) {// Catch une erreur avec SQL
            e.printStackTrace();
        }
        return pers;
    }

    public static ArrayList<Personnel> read() throws SQLException {
        ArrayList<Personnel> personnel = new ArrayList<Personnel>();
        String requete = "SELECT * FROM Personnel";
        ResultSet res = stmt.executeQuery(requete);
        while (res.next()) {
            long id = res.getLong("idPersonnel");
            String nom = res.getString("nom");
            String prenom = res.getString("prenom");
            String date = res.getString("dateNaissance");
            int tempsTravailMensuel = res.getInt("tempsTravailMensuel");
            String fonction = res.getString("nomFonction");
            Personnel pers = new Personnel(id, nom, prenom, date, tempsTravailMensuel, date, fonction);

            personnel.add(pers);
        }
        for (Personnel p : personnel) {
            ArrayList<String> spe = getSpecialite(p.getId());
            System.out.println(spe);
            p.setSpecialite(spe);
        }
        return personnel;
    }

    public static ArrayList<String> getSpecialite(long id) throws SQLException {
        ArrayList<String> spe = new ArrayList<String>();
        String requete2 = "SELECT nomSpecialite FROM specialise WHERE idPersonnel = " + id + ";";
        ResultSet res2 = stmt.executeQuery(requete2);
        while (res2.next()) {
            spe.add(res2.getString("nomSpecialite"));
        }
        res2.close();
        return spe;
    }

    /**
     * Lance la requête qui modifie les personnels dans la DataBase (exemple :
     * l'activer/désactiver).
     * 
     * @param pesr qui est l'instance de la classe personnel qui est déjà
     *             modifié, en quelque sorte, cette méthode ne fait que
     *             l'enregistrer dans la DataBase
     * @return l'objet Personnel pers.
     */
    public static Personnel update(Personnel pers) {
        String requetePersonnel = "UPDATE Personnel SET nom='" + pers.getNom() + "', prenom='" + pers.getPrenom()
                + "', dateNaissance='" + pers.getDateNaissance() + "', tempsTravailMensuel = '"
                + pers.getTempsTravailMensuel() + "', photo='" + pers.getPhotoProfil() + "', nomFonction='"
                + pers.getFonction() + "' WHERE idPersonnel =" + pers.getId() + ";";

        try {
            System.out.println("--MISE À JOUR DU PERSONNEL--\n->Personnel mis à jour");
            stmt.executeUpdate(requetePersonnel);
            System.out.println("--MISE À JOUR DES RELATIONS--");
            deleteRelations(pers);
            insertRelations(pers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pers;
    }

    /**
     * Lance la requête qui supprime le personnel placé en paramètre de la DataBase.
     * 
     * @param pers , le personnel à supprimer
     */
    public static void delete(Personnel pers) {
        String requete = "DELETE FROM Personnel WHERE idPersonnel= " + pers.getId() + ";";// Requête de suppression du
                                                                                          // créneau
                                                                                          // dans la table personnel.
        try {
            deleteRelations(pers);// On delete les lignes des autres table ou le personnel apparait (ex: dans
            // travaille toutes les lignes ou ce personnel était associé à un créneau sont
            // maitenant supprimées)
            stmt.executeUpdate(requete);// On éxecute la requête
            System.out.println("SUPPRESSION DU PERSONNEL");
            System.out.println("->Personnel n°" + pers.getId() + " a été supprimé de la table personnel");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime toutes les lignes dans lesquelles apparait le personnel
     * ex: classe travaille qui regroupe les id des créneaux et du personnel .
     * 
     * 
     * @param pers le personnel dont les relations sont supprimées.
     * @throws SQLException Si jamais une erreure SQL survient.
     * @return void
     */
    public static void deleteRelations(Personnel pers) {
        // Toutes les requêtes de suppressions.
        String requeteSpecialise = "DELETE FROM specialise WHERE idPersonnel = " + pers.getId() + ";";
        String requeteTravaille = "DELETE FROM travaille WHERE idPersonnel = " + pers.getId() + ";";
        try {
            // Exécute les requêtes.
            System.out.println("SUPPRESSION DES RELATIONS DU PERSONNEL");
            stmt.executeUpdate(requeteSpecialise);
            System.out.println("->Personnel n°" + pers.getId() + " a été supprimé de la table specialise");
            stmt.executeUpdate(requeteTravaille);
            System.out.println("->Personnel n°" + pers.getId() + " a été supprimé de la table travaille");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Créer toutes les lignes dans lesquelles apparait le personnel
     * ex: table specialise qui regroupe les id des personnel et les noms de leurs
     * specialités.
     * 
     * @param pers le personnel dont les relations sont inserées.
     * @throws SQLException Si jamais une erreur SQL survient.
     * @return void
     */
    public static void insertRelations(Personnel pers) {

        String requeteIntermediaire = ""; // On crée une variable local qui stockera la requête en cours.
        // On copie les listes de créneaux et de specialtés pour les
        // parcourire
        ArrayList<String> tabDeSpecialites = pers.getSpecialite();
        ArrayList<Creneau> tabDeCreneau = pers.getCreneauxAffecte();
        System.out.println("INSERTION DES REALTIONS DU PERSONNEL");
        try {
            for (String spe : tabDeSpecialites) { // On parcours la liste de specialté du personnel
                // On exécute la requête d'insertion pour chaque spécialité
                requeteIntermediaire = "INSERT INTO specialise (idPersonnel, nomSpecialite)SELECT " + pers.getId()
                        + ",'"
                        + spe
                        + "' WHERE NOT EXISTS (SELECT * FROM specialise WHERE idPersonnel = " + pers.getId()
                        + " AND nomSpecialite = '"
                        + spe + "');";
                stmt.executeUpdate(requeteIntermediaire);
                System.out.println("->Spécialité requise pour le personnel n°" + pers.getId() + " liée [" + spe + "]");
            }

            // Pareil pour les créneaux
            for (Creneau crn : tabDeCreneau) {
                requeteIntermediaire = "INSERT INTO travaille (idCreneaux, idPersonnel)SELECT " + crn.getId() + ", "
                        + pers.getId()
                        + " WHERE NOT EXISTS (SELECT * FROM travaille WHERE idCreneaux = " + crn.getId()
                        + " AND idPersonnel = "
                        + pers.getId() + ");";
                stmt.executeUpdate(requeteIntermediaire);
                System.out.println("->Créneau requis pour le personnel n°" + pers.getId() + " lié [ créneau n°"
                        + crn.getId() + "]");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> selectFonctions() throws SQLException {
        ArrayList<String> fonctions = new ArrayList<String>();
        String requete = "SELECT nom FROM fonction";
        ResultSet res = DAO.stmt.executeQuery(requete);
        while (res.next()) {
            fonctions.add(res.getString("nom"));
        }
        return fonctions;
    }

    public static ArrayList<String> selectSpecialites() throws SQLException {
        ArrayList<String> spe = new ArrayList<String>();
        String requete = "SELECT nom FROM specialite";
        ResultSet res = DAO.stmt.executeQuery(requete);
        while (res.next()) {
            spe.add(res.getString("nom"));
        }
        return spe;
    }

    public static ArrayList<String> selectPersonnels() throws SQLException {
        ArrayList<String> pers = new ArrayList<String>();
        String requete = "SELECT idPersonnel, nom, prenom FROM Personnel";
        ResultSet res = DAO.stmt.executeQuery(requete);
        while (res.next()) {
            String nomPrenom = res.getString("idPersonnel") + " " + res.getString("nom") + " "
                    + res.getString("prenom");
            pers.add(nomPrenom);
        }
        return pers;
    }

    public static Personnel selectPersonnelParId(long id) throws SQLException {
        String requete = "SELECT * FROM Personnel WHERE idPersonnel = " + id + ";";
        ResultSet res = DAO.stmt.executeQuery(requete);
        res.next();
        // long identifiant = res.getLong("idPersonnel");
        String nom = res.getString("nom");
        String prenom = res.getString("prenom");
        String date = res.getString("dateNaissance");
        int tempsTravailMensuel = res.getInt("tempsTravailMensuel");
        String fonction = res.getString("nomFonction");
        Personnel pers = new Personnel(id, nom, prenom, date, tempsTravailMensuel, date, fonction);

        /*
         * requete = "SELECT idCreneaux FROM travaille WHERE idPersonnel = "+id+";";
         * res = DAO.stmt.executeQuery(requete);
         * while (res.next()) {
         * pers.affecterUnCreneau(CreneauDAO.selectCreneauParId(res.getLong("idCreneaux"
         * )));
         * }
         */

        requete = "SELECT nomSpecialite FROM specialise WHERE idPersonnel = " + id + ";";
        res = DAO.stmt.executeQuery(requete);
        while (res.next()) {
            pers.ajouterUneSpecialite(res.getString("NomSpecialite"));
            ;
        }
        return pers;
    }

    public static ArrayList<Personnel> selectPersonnelAsObj() throws SQLException {
        ArrayList<Personnel> listePerso = new ArrayList<Personnel>();
        String requete = "SELECT * FROM Personnel";
        ResultSet res = DAO.stmt.executeQuery(requete);
        while (res.next()) {
            listePerso.add(selectPersonnelParId(res.getLong("idPersonne")));
        }
        System.out.println(listePerso);
        return listePerso;
    }

}
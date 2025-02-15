package DAO;

import java.math.BigInteger;
//import pour manipuler la bd et la colllection ArrayList
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

import metier.Calendrier;
//import des classes métiers
import metier.Creneau;
import metier.Personnel;

/**
 * Hérite de DAO.
 * Class qui permet de gerer les creneaux de la table creneaux de la bd.
 * Il est possible d'inserer, supprimer et modifier une creneaux
 * 
 */
public class CreneauDAO extends DAO<Creneau> {

  /**
   * Constructeur par defaut.
   */
  public CreneauDAO() {
  }

  /**
   * Lance la requête qui sert à insérer une creneau dans la database.
   * 
   * @param crn Objet creneau à inserer dans la bd.
   * @throws SQLException Si jamais une erreure SQL survient.
   * @return void
   */
  public static void create(Creneau crn) {
    String requeteCreneaux = "INSERT INTO Creneaux (dateCreneaux, heureDebut, heureFin, duree)VALUES('" + crn.getDate()
        + "','"
        + crn.getDebut() + "','" + crn.getFin() + "', " + crn.getDuree() + ");";// requêtes d'insertion dans la table
                                                                                // Creneaux
    System.out.println("INSERTION DU CRENEAU");
    try {
      stmt.executeUpdate(requeteCreneaux, Statement.RETURN_GENERATED_KEYS);// On éxectute la requête
      ResultSet cles = stmt.getGeneratedKeys();// Les cles auto-générées sont retournées sous forme de ResultSet
      if (cles.next()) {
        long id = ((BigInteger) cles.getObject(1)).longValue(); // On stock la valeur de la permière colonne du resultat
        // (id du créneau)
        crn.setId(id);// On set l'id (il n'existait pas car c'est la bd qui le genère automatiquement)
        System.out.println("->id du créneau mis à jour-> " + crn.getId());
        System.out.println("->Créneau n°" + crn.getId() + " ajouté");
      }
      insertRelations(crn);
    } catch (SQLException e) {// Catch une erreur avec SQL
      e.printStackTrace();
    }
  }

  /**
   * Permet de récuperer tout les créneaux de la base de données et de créer les
   * objet Creneau dans le code source puis de les ajouter au calendrier qui est
   * en paramètre
   * 
   * @param cal
   * @throws SQLException
   */
  public static void read(Calendrier cal) throws SQLException {
    ArrayList<Creneau> listc = new ArrayList<Creneau>();
    String requete = "SELECT * FROM Creneaux";
    ResultSet res = stmt.executeQuery(requete);
    while (res.next()) {

      long id = res.getInt("idCreneaux");
      String date = res.getString("dateCreneaux");
      String debut = res.getString("heureDebut");
      String fin = res.getString("heureFin");
      double duree = res.getDouble("duree");

      Creneau cr = new Creneau(id, date, debut, fin, duree);

      listc.add(cr);
      cal.AjouterCreneau(cr);
    }

    /*
     * for(Creneau cr : listc){
     * requete = "SELECT idPersonnel FROM travaille WHERE idCreneaux = " +
     * cr.getId() + ";";
     * ResultSet res2 = DAO.stmt.executeQuery(requete);
     * while (res2.next()) {
     * cr.affecterPersonnel(PersonnelDAO.selectPersonnelParId(res2.getLong(
     * "idPersonnel")));
     * }
     * }
     * 
     * for(Creneau cr : listc){
     * requete = "SELECT nomSpecialite, nbPersonnel FROM besoin WHERE idCreneaux = "
     * + cr.getId() + ";";
     * ResultSet res2 = DAO.stmt.executeQuery(requete);
     * while (res2.next()) {
     * cr.ajouterUnNbPersonnelParSpecialite(res2.getString("nomSpecialite"),
     * res2.getInt("nbPersonnel"));
     * }
     * 
     * 
     * }
     */
    listc.stream().forEach(c -> cal.AjouterCreneau(c));

  }

  public static Creneau selectCreneauParId(long id) throws SQLException {
    String requete = "SELECT * FROM Creneaux WHERE idCreneaux = " + id + ";";
    ResultSet res = DAO.stmt.executeQuery(requete);
    res.next();
    // long identifiant = res.getLong("idPersonnel");
    String date = res.getString("dateCreneaux");
    String debut = res.getString("heureDebut");
    String fin = res.getString("heureFin");
    double duree = res.getDouble("duree");
    Creneau cr = new Creneau(id, date, debut, fin, duree);

    /*requete = "SELECT idPersonnel, nomSpecialite, nbPersonnel FROM travaille t INNER JOIN Creneaux c ON t.idCreneaux = c.idCreneaux INNER JOIN besoin b ON c.idCreneaux = b.idCreneaux WHERE t.idCreneaux = "
        + id + " AND b.idCreneaux = " + id + ";";
    ResultSet res2 = DAO.stmt.executeQuery(requete);
    while (res2.next()) {
      cr.affecterPersonnel(PersonnelDAO.selectPersonnelParId(res2.getLong(
          "idPersonnel")));
      cr.ajouterUnNbPersonnelParSpecialite(res2.getString("nomSpecialite"),
          res2.getInt("nbPersonnel"));
    }*/

    /*
     * requete = "SELECT nomSpecialite, nbPersonnel FROM besoin WHERE idCreneaux = "
     * + id + ";";
     * res = DAO.stmt.executeQuery(requete);
     * while (res.next()) {
     * cr.ajouterUnNbPersonnelParSpecialite(res.getString("nomSpecialite"),
     * res.getInt("nbPersonnel"));
     * }
     */
    res.close();
    return cr;
  }

  /**
   * Lance la requête qui modifie les creneaus dans la database (exemple :
   * l'activé/désactiver).
   * 
   * 
   * @param crn L'instance de la classe creneau qui est déjà
   *            modifier, en quelque sorte, cette méthode ne fait que
   *            l'enregistrer dans la database.
   * @throws SQLException Si jamais une erreure SQL survient.
   * @return l'objet creneau crn.
   */
  public static Creneau update(Creneau crn) {
    String requeteCreneaux = "UPDATE Creneaux SET dateCreneaux='" + crn.getDate() + "', heureDebut='" + crn.getDebut()
        + "', heureFin='" + crn.getFin() + "', duree = " + crn.getDuree() + " WHERE idCreneaux =" + crn.getId() + ";";

    try {
      System.out.println("--MISE À JOUR DU CRENEAU--\n->Créneau mis à jour");
      stmt.executeUpdate(requeteCreneaux);
      System.out.println("--MISE À JOUR DES RELATIONS--");
      deleteRelations(crn);
      insertRelations(crn);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return crn;
  }

  /**
   * Lance la requête qui supprimme la creneau placé en paramètre de la database.
   * Appele deleteRelations(crn) pour supprimer toutes les ligne ou le créneau
   * apparait.
   * 
   * .
   * 
   * @param void
   * @throws SQLException Si jamais une erreure SQL survient.
   * @param crn Le créneau à supprimer.
   */
  public static Calendrier delete(Creneau crn, Calendrier calendrier) {
    String requete = "DELETE FROM Creneaux WHERE idCreneaux = " + crn.getId() + ";";// Requête de suppression du créneau
                                                                                    // dans la table creneaux.
    try {
      deleteRelations(crn);// On delete les lignes des autres table ou le créneau apparait (ex: dans
      // travaille toutes les lignes ou ce créneau était associé à un personnel sont
      // maitenant supprimées)
      stmt.executeUpdate(requete);// On éxecute la requête
      System.out.println("SUPPRESSION DU CRENEAU");
      System.out.println("->Créneau n°" + crn.getId() + " a été supprimé de la table Creneaux");
      System.out.println(calendrier.getCalendrier().size());
      calendrier.SupprimerCreneau(crn);
      System.out.println(calendrier.getCalendrier().size());
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return calendrier;
  }

  /**
   * Supprime toutes les lignes dans lequelles apparait le créneau
   * ex: classe travaille qui regroupe les id des créneaux et du personnel .
   * 
   * 
   * @param crn le créneau dont les relations sont supprimées.
   * @throws SQLException Si jamais une erreure SQL survient.
   * @return void
   */
  public static void deleteRelations(Creneau crn) {
    // Requête de suppression.
    String requeteTravaille = "DELETE FROM travaille WHERE idCreneaux = " + crn.getId() + ";";
    String requeteBesoin = "DELETE FROM besoin WHERE idCreneaux = " + crn.getId() + ";";
    try {
      // Execute la requête.
      System.out.println("SUPPRESSION DES RELATIONS DU CRENEAU");
      stmt.executeUpdate(requeteTravaille);
      System.out.println("->Créneau n°" + crn.getId() + " a été supprimé de la table travaille");
      stmt.executeUpdate(requeteBesoin);
      System.out.println("->Créneau n°" + crn.getId() + " a été supprimé de la table besoin");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Créer toutes les données dans lequelles apparait le créneau
   * ex: table travaille qui regroupe les id des créneaux et du personnel .
   * 
   * @param crn le créneau dont les relations sont inserées.
   * @throws SQLException Si jamais une erreur SQL survient.
   * @return void
   */
  public static void insertRelations(Creneau crn) {

    String requeteIntermediaire = ""; // On crée une variable local qui stockera la requête en cours.
    ArrayList<Personnel> tabDePersonnels = crn.getListeDuPersonnel(); // On copie la liste de personnels pour la
                                                                      // parcourire
    Dictionary<String, Integer> nbPersParSpe = crn.getNbPersonnelParSpecialite();
    System.out.println("INSERTION DES REALTIONS DU CRENEAU");
    try {
      // On insert dans travaille les lignes avec le créneau et les personnels(si
      // existe pas déjà )
      for (Personnel pers : tabDePersonnels) {
        requeteIntermediaire = "INSERT INTO travaille (idCreneaux, idPersonnel)SELECT " + crn.getId() + ", "
            + pers.getId() + " WHERE NOT EXISTS (SELECT * FROM travaille WHERE idCreneaux = " + crn.getId()
            + " AND idPersonnel = " + pers.getId() + ");";
        stmt.executeUpdate(requeteIntermediaire);
        System.out.println("->Personnel requis pour le créneau n°" + crn.getId() + " lié [" + pers.getNom() + " "
            + pers.getPrenom() + "]");
      }

      // Pareil pour le nb de personnel nescessaire par specialité
      for (Enumeration k = nbPersParSpe.keys(); k.hasMoreElements();) {
        Object specialite = k.nextElement();
        requeteIntermediaire = "INSERT INTO besoin (idCreneaux, nomSpecialite, nbPersonnel)SELECT " + crn.getId()
            + ", '"
            + specialite + "', " + nbPersParSpe.get(specialite)
            + " WHERE NOT EXISTS (SELECT * FROM besoin WHERE idCreneaux = " + crn.getId()
            + " AND nomSpecialite = '" + specialite + "' );";
        stmt.executeUpdate(requeteIntermediaire);
        System.out.println("->Spécialité et nombre de personnel requis pour le créneau n°" + crn.getId() + " lié ["
            + nbPersParSpe.get(specialite) + " pour la spécialité " + specialite + "]");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
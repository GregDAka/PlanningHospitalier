package DAO;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*import java.math.BigInteger;
//import pour manipuler la bd et la colllection ArrayList
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import des classes métiers
import metier.Creneau;*/
import metier.Contrainte;

/**
 * Hérite de DAO.
 * Class qui permet de gérer les contraintes de la table contrainte de la BD.
 * Il est possible d'insérer, supprimer et modifier une contrainte
 */
public class ContrainteDAO extends DAO<Contrainte> {

    /**
     * Constructeur par défaut.
     */
    public ContrainteDAO() {
        super();
    }

    /**
     * Objet de la classe ResultSet dans lequel est stocké les résultats d'une
     * requête SQL.
     */
    // private ResultSet rs;

    /**
     * Lance la requête qui sert à insérer une contrainte dans la DataBase.
     * 
     * @param Contrainte cont à insérer dans la bd.
     * @return la contrainte cont.
     */
    public static Contrainte create(Contrainte cont) {
        String requeteCreneaux = "INSERT INTO Contrainte (nom, activation, violation, description)VALUES('"
                + cont.getNom() + "', " + cont.getActivationEnInt() + " , " + cont.getViolationEnInt() + " '"
                + cont.getDescription() + "');";// requêtes d'insertion dans la table Contrainte
        System.out.println("INSERTION DU CRENEAU");
        try {
            stmt.executeUpdate(requeteCreneaux, Statement.RETURN_GENERATED_KEYS);// On éxectute la requête
            ResultSet cles = stmt.getGeneratedKeys();// Les cles auto-générées sont retournées sous forme de ResultSet
            if (cles.next()) {
                long id = ((BigInteger) cles.getObject(1)).longValue(); // On stock la valeur de la permière colonne du
                                                                        // resultat
                // (id du créneau)
                cont.setId(id);// On set l'id (il n'existait pas car c'est la bd qui le genère automatiquement)
                System.out.println("->id de la contrainte mis à jour-> " + cont.getId());
                System.out.println("->Créneau n°" + cont.getId() + " ajouté");
            }
        } catch (SQLException e) {// Catch une erreur avec SQL
            e.printStackTrace();
        }
        return cont;
    }

    /**
     * Lance la requête qui modifie les contraintes dans la DataBase (exemple :
     * l'activer/désactiver).
     * 
     * @param cont qui est l'instance de la classe Contrainte qui est
     *             déjà modifiée, en quelque sorte, cette méthode ne fait que
     *             l'enregistrer dans la DataBase.
     * @return la contrainte cont.
     */
    public static Contrainte update(Contrainte cont) {
        String requeteContrainte = "UPDATE Contrainte SET nom='" + cont.getNom() + "', activation='" + cont.isActivation()
        + "', violation='" + cont.isViolation() + "', description = " + cont.getDescription() + " WHERE idContrainte =" + cont.getId() + ";";

    try {
      System.out.println("--MISE À JOUR DE LA CONTRAINTE--\n->Contrainte mis à jour");
      stmt.executeUpdate(requeteContrainte);
      System.out.println("--MISE À JOUR DES RELATIONS--");
      deleteRelations(cont);
      insertRelations(cont);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return cont;
  }


    /**
     * Lance la requête qui supprime la contrainte placée en paramètre de la
     * DataBase.
     * 
     * @param cont , la contrainte à supprimer.
     */
    public static void delete(Contrainte cont) {
        String requete = "DELETE FROM Contrainte WHERE idContrainte = " + cont.getId() + ";";// Requête de suppression de la contrainte
                                                                                    // dans la table contrainte.
    try {
      deleteRelations(cont);// On delete les lignes des autres table ou la contrainte apparait (ex: dans
      // XXX toutes les lignes ou cette contrainte était associé à un personnel sont
      // maitenant supprimées)
      stmt.executeUpdate(requete);// On éxecute la requête
      System.out.println("SUPPRESSION DE LA CONTRAINTE");
      System.out.println("->Contrainte n°" + cont.getId() + " a été supprimé de la table Contrainte");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }



    /**
     * Supprime toutes les lignes dans lesquelles apparait la contrainte
     * ex: classe travaille qui regroupe les id des créneaux et des contraintes .
     * 
     * 
     * @param cont la contrainte dont les relations sont supprimées.
     * @throws SQLException Si jamais une erreur SQL survient.
     * @return void
     */
    public static void deleteRelations(Contrainte cont) {

    }

    /**
     * Créer toutes les lignes dans lesquelles apparait la contrainte
     * ex: table specialise qui regroupe les id des contraintes et les noms de leurs
     * specialités.
     * 
     * @param cont la contrainte dont les relations sont inserées.
     * @throws SQLException Si jamais une erreure SQL survient.
     * @return void
     */
    public static void insertRelations(Contrainte cont) {

    }
}
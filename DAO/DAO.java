package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Class abstraite générique qui possède deux méthodes close et open
 * qui respectivement créent le lien vers la BD et le ferme grâce à la
 * class SingleConnection.
 * Elle déclare également les méthodes abstraites create(), update() et
 * delete() dont héritent ses classes filles.
 */
public abstract class DAO<T> {

    /**
     * Constructeur qui utilise open pour set les attributs (ouvre la connection
     * avec la bd)
     * 
     * @return void
     */
    public DAO() {
    }

    /**
     * Contient le lien de la connexion entre la BD et le code source
     */
    protected static Connection connect;

    /**
     * Contient un statement qui permet d'exécuter les requêtes
     */
    protected static Statement stmt;

    /**
     * Utilise la classe SingleConnection pour ouvrir un lien vers la BD et le
     * stocker dans connect
     * 
     * @return void
     */
    public static void open() {
        try {
            connect = SingleConnection.getInstance();
            DAO.stmt = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(" === ERREUR OPEN DAO === ");
            e.printStackTrace();
        }

    }



    /**
     * Ferme la connection stockée dans connect puis avec setConnect() affecte la
     * valeur null à connect.
     * Ferme le statement stockée dans stmt puis avec setStmt() affecte la valeur
     * null à stmt.
     * 
     * @throws SQLException
     * @return void
     */
    public static void close() throws SQLException {
        try {
            stmt.close();
            connect.close();
            setConnect(null);
            setStmt(null);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void setConnect(Connection cnt) {
        connect = cnt;
    }

    public static void setStmt(Statement stmt) {
        DAO.stmt = stmt;
    }

}
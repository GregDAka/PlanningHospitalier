package DAO;

import java.sql.Connection;

import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * Cette classe permet d"établir le lien entre la base de données et le code source, grâce à ses méthodes on peut établir le lien, le stocker et le fermer.
 */
public class SingleConnection {

	/**
	 * Objet de la class Connection, lien ente la base de données et le code source.
	 */
	private static Connection connect;

	/**
	 * Constructeur privé qui initialise toute les variables avec leur valeur
	 * correspondant aux données lié avec la BD pour établir le lien et le 
	 * stocke dans l'attribut connect de la classeConnection.
	 * @throws ClassNotFoundException Si une classe n'est pas trouvée.
	 * @throws SQLException Si une erreur SQL survient pendant l'exécution de la requête.
	 */
	private SingleConnection() throws ClassNotFoundException, SQLException {
		String serveur = "127.0.0.1";
		String nombd = "hoplanning";
		String login = "root";
		String motpasse = "";
		String url = "jdbc:mysql://" + serveur + ":3306/" + nombd + "?serverTimezone=UTC"; // port mysql avec xampp
																							// 3306, 3307 avec
																							// usbwebserveur
		MysqlDataSource mysqlDS = new MysqlDataSource();
		mysqlDS.setURL(url);
		mysqlDS.setUser(login);
		mysqlDS.setPassword(motpasse);
		connect = mysqlDS.getConnection();

	}

	/**
	 * Vérifie si l'attribut connect, si oui on instancie un nouvel objet (ce qui
	 * met à jour la valeur de connect qui contient maitenant le nouveau lien avec
	 * la BD)puis renvoie connect(si connect non null alors un lien existe déjà et 
	 * aucun changement n'est effectué)
	 * @throws ClassNotFound Si une class n'est pas trouvée.
	 * @throws SQLException Si une erreur SQL survient pendant l'exécution de la requête.
	 * @return Attribut connect modifié ou non
	 */
	public static Connection getInstance() throws ClassNotFoundException, SQLException {
		if (connect == null) {
			new SingleConnection();
		}
		return connect;

	}

	/**
	 * Setter de l'attribut connect.
	 * @param connect
	 * @return void
	 */
	public static void setConnect(Connection connect) {
		SingleConnection.connect = connect;
	}

	/**
	 * Getter de l'attribut connect.
	 * @return L'attribut connect 
	 */
	public static Connection getConnect() {
		return connect;
	}

}

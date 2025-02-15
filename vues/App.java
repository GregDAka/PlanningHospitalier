package vues;



//Importation nécessaire !

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import DAO.CreneauDAO;
import DAO.DAO;
import DAO.PersonnelDAO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import metier.Calendrier;
import metier.Contrainte;
import metier.Creneau;
import metier.Personnel;

public class App extends Application {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		// launch(args);

		//Test DAO

		/*Creneau cr1 = new Creneau(1, "2023-12-28", "14:00:00", "19:00:00", 5);
		Creneau cr2 = new Creneau(2, "2023-12-27", "08:00:00", "10:00:00", 2);

	
		Personnel p1 = new Personnel(1,"tic", "tac", "2004-05-17", 35, "", "Docteur");
		Personnel p2 = new Personnel(2,"tac", "tic", "2004-12-30",35,"","Infiermier");
		
		Contrainte c1 = new Contrainte("Spécial", "c1", 10);
		Contrainte c2 = new Contrainte("Spécial", "c2", 20);
		c1.setId(1);
		c2.setId(2);

		ArrayList<String> specialite = new ArrayList<String>();
		specialite.add("Cardiologie");
		specialite.add("Pediatrie");
		specialite.add("Gynecologie");
		p1.setSpecialite(specialite);
		
		Dictionary<String, Integer> nbPersParSpe = new Hashtable<>();
		nbPersParSpe.put("Cardiologie", 10);
		nbPersParSpe.put("Pediatrie", 5);
		cr1.setNbPersonnelParSpecialite(nbPersParSpe);
		
		ArrayList<Personnel> personnels = new ArrayList<Personnel>();
		personnels.add(p1);
		personnels.add(p2);
		cr1.setListeDuPersonnel(personnels);

		ArrayList<Creneau> creneau = new ArrayList<Creneau>();
		creneau.add(cr2);
		creneau.add(cr1);
		p1.setCreneauxAffecte(creneau);

		//PersonneDAO
		PersonnelDAO.create(p1);
		//PersonnelDAO.create(p2);
		/*p1.setFonction("Infiermier");
		PersonnelDAO.update(p1);*/

		//CreneauDAO
		//CreneauDAO.create(cr1);
		/*cr1.setDebut("07:00:00");
		cr1.getListeDuPersonnel().remove(p1);
		CreneauDAO.update(cr1);*/
		
	

		//launch(args);
		
		DAO.open();
		Calendrier cal = new Calendrier();
		CreneauDAO.read(cal);
		//cal.afficherCalendrier();
	
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Ma Première Application JavaFX");

		// Création d'un label
		Label label = new Label("Bonjour, ceci est une fenêtre JavaFX!");

		// Création d'un conteneur de mise en page
		StackPane root = new StackPane();
		root.getChildren().add(label);

		// Création de la scène
		Scene scene = new Scene(root, 300, 200);

		// Définition de la scène principale
		primaryStage.setScene(scene);

		// Affichage de la fenêtre
		primaryStage.show();

	}
}

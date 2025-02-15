package controleurs;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import DAO.CreneauDAO;
import DAO.DAO;
import DAO.PersonnelDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import metier.Calendrier;
import metier.Creneau;
import metier.Personnel;
import vues.main;

/**
 * Classe contrôleur qui permet de gérer la page principale du logiciel qui
 * contient l'emploi du temps, l'accès à l'ajout d'un créneau ou personnel, ce
 * qui touche aux contraintes et contient la liste des personnels.
 * 
 * @see CrAjouterControleur
 * @see PersAjouterControleur
 * @see CrConsulterControleur
 * @see PersConsulterControleur
 */
public class calendarControleur implements Initializable {

    @FXML
    private Button ajouterCreneau;

    @FXML
    private Button ajouterPersonnel;

    @FXML
    private Button gererContrainteslegales;

    @FXML
    private Button moinsUneSemaine;

    @FXML
    private Button plusUneSemaine;

    @FXML
    private ListView<String> listeDuPersonnel;

    @FXML
    private Label lundiDate;

    @FXML
    private Label mardiDate;

    @FXML
    private Label mercrediDate;

    @FXML
    private Label jeudiDate;

    @FXML
    private Label vendrediDate;

    @FXML
    private Label samediDate;

    @FXML
    private Label dimancheDate;

    private static  Calendrier calendrier;

    @FXML
    private GridPane tableau;

    private int semaineDemande = 0;

    private Map<Rectangle, Creneau> rectangleCreneauMap = new HashMap<>();

    /**
     * Parcours le hashmap de calendrier et modifie la date des jours de la semaine
     * en fonction de la semaine demandée.
     */
    protected void afficherDate() {
        LinkedHashMap<LocalDate, ArrayList<Creneau>> map = calendrier.getCalendrier();
        List<LocalDate> listeCles = new ArrayList<>(map.keySet());
        int parcours = 0;
        for (int i = 0; i < semaineDemande; i++) {
            parcours += 7;
        }
        lundiDate.setText(listeCles.get(parcours).toString());
        mardiDate.setText(listeCles.get(parcours + 1).toString());
        mercrediDate.setText(listeCles.get(parcours + 2).toString());
        jeudiDate.setText(listeCles.get(parcours + 3).toString());
        vendrediDate.setText(listeCles.get(parcours + 4).toString());
        samediDate.setText(listeCles.get(parcours + 5).toString());
        dimancheDate.setText(listeCles.get(parcours + 6).toString());
    }

    /**
     * Méthode qui affiche tous les créneaux d'une semaine, en fonction de la date
     * des jours de la semaine.
     * 
     * @throws SQLException Exception pour si on a une erreur SQL
     */
    protected void afficherCreneauGlobal() throws SQLException {
        ArrayList<Label> jour = new ArrayList<>(
                Arrays.asList(lundiDate, mardiDate, mercrediDate, jeudiDate, vendrediDate, samediDate, dimancheDate));
        CreneauDAO.read(calendrier);
        for (Map.Entry<LocalDate, ArrayList<Creneau>> entre : calendrier.getCalendrier().entrySet()) {
            LocalDate dateI = entre.getKey();
            ArrayList<Creneau> listei = entre.getValue();
            for (int i = 0; i < jour.size() - 1; i++) {
                int jourac = i;
                if (dateI.toString().equals(jour.get(i).getText())) {
                    listei.forEach(creneau -> {
                        int heure = Integer.parseInt(creneau.getDebut().substring(0, 2));
                        Rectangle rectangle = new Rectangle(129, 16, Color.LIGHTBLUE);
                        tableau.add(rectangle, jourac + 1, heure + 2);
                        rectangleCreneauMap.put(rectangle, creneau);
                        if (creneau.getDuree() >= 2) {
                            int c = 1;
                            for (int nbHeures = 1; nbHeures < creneau.getDuree(); nbHeures++) {
                                Rectangle newRectangle = new Rectangle(129, 16, Color.LIGHTBLUE);
                                tableau.add(newRectangle, jourac + 1, (heure + 2) + c);
                                rectangleCreneauMap.put(newRectangle, creneau);
                                c++;
                            }
                        }
                    });
                }
            }
        }
        recupererInfoRectangle();
    }

  

    protected void recupererInfoRectangle() {
        for (Map.Entry<Rectangle, Creneau> entre : rectangleCreneauMap.entrySet()) {
            Rectangle rectangle = entre.getKey();
            // entre.getValue(); pour récupérer les valeurs
            rectangle.setOnMouseClicked(event -> {
                Creneau creneauClique = rectangleCreneauMap.get(rectangle);
                if (creneauClique != null) {

                    try {
                        CrConsulterControleur.getCrConsulter(creneauClique.getId());
                        CrConsulterControleur.getCalendrier(calendrier);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    try {
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("crconsulter.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        stage.setTitle("Consulter un créneau");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    public static void recupCalendrier(Calendrier c) {
        calendrier = c;
        

    }

    @FXML
    /**
     * Méthode qui permet d'accèder à la fenêtre d'ajout d'un créneau à partir du
     * bouton Ajouter un créneau
     * 
     * @see CrAjouterControleur
     * @param e Evènempent qui s'occure quand on appuie sur le bouton ajouter un
     *          créneau
     * @throws IOException Exception pour si il y a un problème lors du lancement de
     *                     crajouter.fxml
     */
    protected void ajoutDuCreneau(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("crajouter.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ajouter un créneau");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    /**
     * Méthode qui permet d'accèder à la fenêtre d'ajout d'un personnel à partir du
     * bouton Ajouter un personnel
     * 
     * @see PersAjouterControleur
     * @param e Evènement qui s'occure quand on appuie sur le bouton ajouter un
     *          personnel
     * @throws IOException Exception pour si il y a un problème lors du lancement de
     *                     persajouter.fxml
     */
    protected void ajoutDunPersonnel(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("persajouter.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ajouter un personnel");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    /**
     * Méthode qui permet d'accèder à la fenêtre pour gérer les contraintes à partir
     * du bouton Gérer les contraintes légales
     * 
     * @see ContGererControleur
     * @param e Evènement qui s'occure quand on appuie sur le bouton gérer les
     *          contraintes légales
     * @throws IOException Exception pour si il y a un problème lors du lancement de
     *                     contgerer.fxml
     */
    protected void gererContraintes(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("contgerer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Gérer les contraintes");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    /**
     * Méthode qui permet d'afficher la semaine précédente à celle actuellement
     * affichée à l'appui du bouton Semaine Précédente
     * 
     * @param e Evènement qui s'occure quand on appuie sur le bouton Semaine
     *          Précédente
     */
    protected void semainePrecedente(ActionEvent e) {
        if (semaineDemande > 0) {
            semaineDemande--;
            afficherDate();
            for (Node node : tableau.getChildren()) {
                if (node instanceof Rectangle) {
                    // Vérifie si le nœud est un Rectangle (votre couleur)
                    Rectangle rectangle = (Rectangle) node;
                    rectangle.setFill(null); // Supprime la couleur
                }
            }
            try {
                afficherCreneauGlobal();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    @FXML
    /**
     * Méthode qui permet d'afficher la semaine suivante à celle actuellement
     * affichée à l'appui du bouton Semaine Suivante
     * 
     * @param e Evènement qui s'occure quand on appuie sur le bouton Semaine
     *          Suivante
     */
    protected void semaineSuivante(ActionEvent e) {
        if (semaineDemande < (calendrier.getJourPasse() / 7) - 1) {
            semaineDemande++;
            afficherDate();
            for (Node node : tableau.getChildren()) {
                if (node instanceof Rectangle) {
                    // Vérifie si le nœud est un Rectangle (votre couleur)
                    Rectangle rectangle = (Rectangle) node;
                    rectangle.setFill(null); // Supprime la couleur
                }
            }
            try {
                afficherCreneauGlobal();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else
            System.out.println("Je ne peux pas changer de semaine");
    }

    @Override
    /**
     * Méthode pour l'initialisation du contrôleur
     */
    public void initialize(URL arg0, ResourceBundle arg1) {
        DAO.open();
        System.out.println("Connexion à la bd réussi");
        actualiserListe();
        calendrier = new Calendrier();
        afficherDate();
        try {
            afficherCreneauGlobal();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour actualiser l'affichage de la liste des personnels
     */
    public void actualiserListe() {
        try {

            ArrayList<String> personnel = PersonnelDAO.selectPersonnels();
            List<String> p = listeDuPersonnel.getItems();

            personnel.stream()
                    .filter(e -> (!listeDuPersonnel.getItems().contains(e)))
                    .forEach(e -> listeDuPersonnel.getItems().add(e));
            p.stream()
                    .filter(e -> (!personnel.contains(e)))
                    .forEach(e -> p.remove(e));

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Méthode qui permet d'accèder à la fenêtre de consultation des informations
     * sur un personnel lorsqu'on clique sur un personnel
     * 
     * @see PersConsulterControleur
     * @param e Evènement qui s'occure quand on clique sur un personnel de la liste
     * @throws IOException Exception pour si il y a un problème lors du lancement de
     *                     persconsulter.fxml
     */
    protected void consulterPersonnel(MouseEvent e) throws IOException, SQLException {
        PersConsulterControleur.getPersonnelConsulte(listeDuPersonnel);
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("persconsulter.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Consulter un personnel");
        stage.setScene(scene);
        stage.show();
    }

}

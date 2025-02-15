package controleurs;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DAO.CreneauDAO;
import DAO.PersonnelDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vues.App;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import metier.Calendrier;
import metier.Creneau;
import metier.Personnel;

/**
 * Classe contrôleur liée au fichier crconsulter.fxml qui permet de consulter
 * les créneaux et ses informations, elle permet aussi d'accéder à la
 * suppression de celui-ci ou de le modifier
 * 
 * @see CrDeleteControleur
 */
public class CrConsulterControleur implements Initializable {
    private static Creneau crSelectionne;

    public static void setCrSelectionne(Creneau crn) {
        crSelectionne = crn;
    }

    @FXML
    private DatePicker dateDuCreneau;

    @FXML
    private TextField heureDebut;

    @FXML
    private TextField heureFin;

    @FXML
    private TextField duree;

    @FXML
    private ComboBox<String> choixSpe;

    @FXML
    private TextField nbPersRequis;

    @FXML
    private Button ajoutListe;

    @FXML
    private ListView<String> listeSpe;

    @FXML
    private ComboBox<String> ComboBoxPersonnel;

    @FXML
    private Button ButtonAjouter;

    @FXML
    private Button ButtonSupprimer;

    @FXML
    private ListView<String> ListViewPersonnel;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println(crSelectionne.getId());
        dateDuCreneau.setValue(LocalDate.parse(crSelectionne.getDate()));
        heureDebut.setText(crSelectionne.getDebut());
        heureFin.setText(crSelectionne.getFin());
        duree.setText(Double.toString(crSelectionne.getDuree()));
        // crSelectionne.getSpecialite().stream()
        // .forEach(e -> listeSpe.getItems().add(e));

        try {
            ArrayList<String> spe = PersonnelDAO.selectSpecialites();
            spe.stream()
                    .forEach(e -> choixSpe.getItems().add(e));

            ObservableList<String> items = FXCollections.observableArrayList();
            choixSpe.setOnAction(event -> {
                String selectedItem = choixSpe.getSelectionModel().getSelectedItem();
                updateListViewSpec(selectedItem, items);
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui permet d'actualiser la liste des spécialités concernant un
     * creneau
     * 
     * @param selectedItem Les spécialités choisies à ajouter dans la liste
     * @param items        Liste des spécialités choisies
     */
    private void updateListViewSpec(String selectedItem, ObservableList<String> items) {
        items.add(selectedItem);
        listeSpe.setItems(items);
    }

    public static void getCrConsulter(long id) throws SQLException {
        Creneau cr = CreneauDAO.selectCreneauParId(id);
        setCrSelectionne(cr);
    }

    @FXML
    protected void ajoutSpe(ActionEvent e) {

    }

    @FXML
    protected void ajoutPers(ActionEvent e) {

    }

    @FXML
    protected void supprimerPers(ActionEvent e) {

    }

    public void closeFenetre(Button bouton) {
        Stage stage = (Stage) bouton.getScene().getWindow();
        stage.close();
    }

    /**
     * Méthode qui gère ce qui se passe au click du bouton modifier.
     * @param clickm Ce qui est déclenché au click du bouton
     */
    @FXML
    protected void modifierClick(ActionEvent clickm) {
           


        
        crSelectionne.modifAttribut(null, null, null, null, null);
        CreneauDAO.update(crSelectionne);
        closeFenetre(modifier);
    }

    @FXML
    protected void supprimerClick(ActionEvent clicks) throws IOException {
        
        calendarControleur.recupCalendrier(CreneauDAO.delete(crSelectionne, calendrier));
        crSelectionne = null;
        closeFenetre(supprimer);

    }

    private static Calendrier calendrier;

    public static void setCalendrier(Calendrier cal) {
        calendrier = cal;
    }

    public static void getCalendrier(Calendrier calendrier) {
        setCalendrier(calendrier);
    }

}

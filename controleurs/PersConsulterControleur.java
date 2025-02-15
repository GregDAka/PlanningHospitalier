package controleurs;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collector;

import DAO.PersonnelDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import junit.framework.Test;
import metier.Personnel;

/**
 * Classe contrôleur lié au fichier persconsulter.fxml qui permet de consulter les informations sur un personnel, elle permet aussi l'accès à la fenêtre de suppression de celui-ci 
 */
public class PersConsulterControleur implements Initializable {

    private static Personnel persSelectionne;

    public static void setPersSelectionne(Personnel pers) {
        persSelectionne = pers;
    }

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private DatePicker dateDeNaissance;

    @FXML
    private TextField tempsTravail;

    @FXML
    private ChoiceBox<String> fonction;

    @FXML
    private ComboBox<String> specialites;

    @FXML
    private ListView<String> listeSpe;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;

    @Override
    /**
     * Méthode pour l'initialisation du contrôleur
     */
    public void initialize(URL arg0, ResourceBundle arg1) {
        nom.setText(persSelectionne.getNom());
        prenom.setText(persSelectionne.getPrenom());
        dateDeNaissance.setValue(LocalDate.parse(persSelectionne.getDateNaissance()));
        tempsTravail.setText(Integer.toString(persSelectionne.getTempsTravailMensuel()));
        fonction.setValue(persSelectionne.getFonction());
        persSelectionne.getSpecialite().stream()
                .forEach(e -> listeSpe.getItems().add(e));

        try {
            ArrayList<String> fct = PersonnelDAO.selectFonctions();
            fct.stream()
                    .forEach(e -> fonction.getItems().add(e));
            ArrayList<String> spe = PersonnelDAO.selectSpecialites();
            spe.stream()
                    .forEach(e -> specialites.getItems().add(e));

            ObservableList<String> items = FXCollections.observableArrayList();
            specialites.setOnAction(event -> {
                String selectedItem = specialites.getSelectionModel().getSelectedItem();
                updateListView(selectedItem, items);
            });

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui permet d'actualiser la liste des spécialités concernant un personnel
     * @param selectedItem Les spécialités choisies à ajouter dans la liste
     * @param items Liste des spécialités choisies
     */
    private void updateListView(String selectedItem, ObservableList<String> items) {
        items.add(selectedItem);
        listeSpe.setItems(items);
    }

    /**
     * Méthode qui permet de fermer une fenêtre JavaFX lors de l'appui d'un bouton qu'on passe en paramètre
     * @param bouton Bouton qu'on passe en paramètre qui déclenche la fermeture de la fen^tre quand appuyé
     */
    public void closeFenetre(Button bouton) {
        Stage stage = (Stage) bouton.getScene().getWindow();
        stage.close();
    }

    @FXML
    /**
     * Méthode qui permet de modifier les informations d'un personnel lorsqu'on clique surle bouton modifier et ferme la fenêtre de consultation
     */
    protected void modifClick() {
        LocalDate localDate = dateDeNaissance.getValue();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = localDate.format(formatter);
       
        ArrayList<String> specialite = new ArrayList<String>();
        for (String spe : listeSpe.getItems()){
            specialite.add(spe);
        }
        
        persSelectionne.modifAttribut(nom.getText(), prenom.getText(), date, Integer.parseInt(tempsTravail.getText()), fonction.getValue(), specialite);

        PersonnelDAO.update(persSelectionne);
        closeFenetre(modifier);
    }

    @FXML
    /**
     * Méthode qui permet la supression d'un personnel lors de l'appui du bouton supprimer et ferme la fenêtre de ocnsultation
     */
    protected void suppClick() {
        PersonnelDAO.delete(persSelectionne);
        persSelectionne = null;
        closeFenetre(supprimer);
    }

    public static void getPersonnelConsulte(ListView<String> liste) throws SQLException {
        String personnelSelec = (String) liste.getSelectionModel().getSelectedItem();
        String chaineID = personnelSelec.split(" ")[0];
        long id = Long.parseLong(chaineID);
        setPersSelectionne(PersonnelDAO.selectPersonnelParId(id));
    }

}

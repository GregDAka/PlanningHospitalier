package controleurs;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import DAO.PersonnelDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import metier.Personnel;
import javafx.stage.Stage;


/**
 * Classe contrôleur qui permet l'ajout d'un personnel au logiciel
 */
public class PersAjouterControleur implements Initializable{



    @FXML
    private Button valider;

    @FXML
    private Button annuler;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField tempsTravail;

    @FXML
    private DatePicker dateDeNaissance;

    @FXML 
    /**
     * Méthode qui ferme la fenêtre d'ajout d'un personnel quand on clique sur le bouton annuler
     * @param e Evènement qui s'occure quand on clique sur le bouton annuler
     */
    protected void annulerClick(ActionEvent e){
        Stage stage = (Stage)annuler.getScene().getWindow();
         
        stage.close();
    }

    @FXML 
    /**
     * Méthode qui valide l'ajout d'un personnel quand on clique sur le bouton valider
     * @param e Evènement qui s'occure quand on clique sur le bouton valider
     */
    protected void validerClick(ActionEvent e){
        String name = nom.getText();
        String firstName = prenom.getText();

        LocalDate localDate = dateDeNaissance.getValue();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = localDate.format(formatter);

        int temps = Integer.parseInt(tempsTravail.getText());
        String fonct = fonction.getValue();
        ArrayList<String> spe = new ArrayList<>(listeSpe.getItems());
                                                
       
        Personnel personnelAajouter = new Personnel(name, firstName, date, temps, null, fonct);
        personnelAajouter.setSpecialite(spe);
        PersonnelDAO.create(personnelAajouter);

        annulerClick(e);
    }

    @FXML
    private ChoiceBox<String> fonction = new ChoiceBox<String>();

    @FXML
    private ComboBox<String> specialites = new ComboBox<String>();

    @FXML
    private ListView<String> listeSpe = new ListView<String>();

    @Override
    /**
     * Méthode pour l'initiatlisation du controleur 
     */
    public void initialize(URL arg0, ResourceBundle arg1) {

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
     * Méthode qui permet d'ajouter une spécialité à la liste des spécialités dont fait parti ce personnel
     * @param selectedItem La spécialité choisie à ajouter dans la liste
     * @param items La liste des spécialités choisies
     */
    private void updateListView(String selectedItem, ObservableList<String> items) {
                items.add(selectedItem);
                listeSpe.setItems(items);
            }

     

}
package controleurs;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.ResourceBundle;

import DAO.CreneauDAO;
import DAO.PersonnelDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import metier.Creneau;
import metier.Personnel;

/**
 * Classe contrôleur liée au fichier crajouter.fxml qui permet l'ajout d'un créneau au calendrier
 */
public class CrAjouterControleur implements Initializable {

    @FXML
    private Button annuler;

    @FXML
    private Button valider;

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
    private ListView<Dictionary<String, Integer>> listeSpe;

    @FXML
    private ComboBox<Personnel> choixPers;

     @FXML
    private Button ajoutPers;

     @FXML
    private Button suppPers;

       @FXML
    private ListView<Personnel> listeViewPers;

    @FXML
    /**
     * Méthode qui ferme la fenêtre d'ajout d'un créneau quand on clique sur le bouton annuler
     * @param clicka Evènement qui s'occure quand on clique sur le bouton annuler
     */
    public void annulerClick(ActionEvent clicka) {
        Stage stage = (Stage) annuler.getScene().getWindow();
        stage.close();
    }

    @FXML
    /**
     * Méthode qui valide l'ajout d'un créneau lorsqu'on clique sur le bouton valider
     * @param clickv Evènement qui s'occure quand on clique sur le bouton valider
     */
    public void validerClick(ActionEvent clickv) {
        LocalDate localDate = dateDuCreneau.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = localDate.format(formatter);

        String heureDeb = heureDebut.getText();

        String heureEnd = heureFin.getText();

        double dur = Double.parseDouble(duree.getText());

        Creneau cr = new Creneau(date, heureDeb, heureEnd, dur, nbPersParSpe);
        ArrayList<Personnel> p = new ArrayList<Personnel>(listeViewPers.getItems());
        cr.setListeDuPersonnel(p);
        CreneauDAO.create(cr);
        
        annulerClick(clickv);

    }

    private ObservableList<Dictionary<String, Integer>> items = FXCollections.observableArrayList();
    private Dictionary<String, Integer> nbPersParSpe = new Hashtable<String, Integer>();

    @FXML
    /**
     * Méthode qui permet d'associer à un nombre de personnel requis à une spécialité donné pour un créneau
     * @param e Evènement qui s'occure quand on ajoute un nombre de personnel requis à une spécialité
     */
    public void ajoutSpe(ActionEvent e) {
        String selectedItem = choixSpe.getSelectionModel().getSelectedItem();
        String nbRequisAsString = nbPersRequis.getText();
        int nbRequis = Integer.parseInt(nbRequisAsString);
        nbPersParSpe.put(selectedItem, nbRequis);
        updateListView(nbPersParSpe, items);
    }

    @Override
    /**
     * Méthode pour l'initialisation du contrôleur
     */
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            ArrayList<String> spe = PersonnelDAO.selectSpecialites();
            spe.stream()
                    .forEach(e -> choixSpe.getItems().add(e));
             ArrayList<Personnel> pers = PersonnelDAO.read();
             pers.stream()
                    .forEach(e -> choixPers.getItems().add(e));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Méthode qui permet d'ajouter une spécialité et le nombre de personnel requis associé à la liste des spécialités nécessaire dans le créneau
     * @param nbPersParSpe Dictionnaire contenant les spécialités et le nombre de pers requis associé
     * @param items Liste qui contient les éléments à afficher dans le ListView
     */
    private void updateListView(Dictionary<String, Integer> nbPersParSpe,
            ObservableList<Dictionary<String, Integer>> items) {
        items.add(nbPersParSpe);
        if(!listeSpe.getItems().isEmpty()){
            int last = listeSpe.getItems().size() - 1;
            listeSpe.getItems().remove(last);
        }
        
        listeSpe.setItems(items);
    }


    @FXML
    protected void ajouterPers(ActionEvent e){
        Personnel selectedPers = choixPers.getSelectionModel().getSelectedItem();
        if(!listeViewPers.getItems().contains(selectedPers)){
            listeViewPers.getItems().add(selectedPers);
        }
        
    }

       @FXML
    protected void supprimerPers(ActionEvent e){
        Personnel selectedPers = choixPers.getSelectionModel().getSelectedItem();
        if(listeViewPers.getItems().contains(selectedPers)){
            listeViewPers.getItems().remove(selectedPers);
        }
    }
}

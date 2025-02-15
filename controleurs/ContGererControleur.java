package controleurs;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import metier.Contrainte;
import metier.Personnel;

public class ContGererControleur /*implements Initializable*/ {
    
    @FXML
    private Button fermer;

    @FXML 
    private ListView respectrepos;

    @FXML 
    private ListView respectmensuel;
/* 
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateListView();
    }
*/
    @FXML
    /**
     * Méthode qui ferme la fenêtre gérer les contraintes quand on clique sur le bouton fermer
     * @param clickf Evènement qui s'occure quand on clique sur le bouton fermer
     */
    public void fermerClick(ActionEvent clickf) {
        Stage stage = (Stage) fermer.getScene().getWindow();
        stage.close();
    }
    //Ne fonctionne pas comme prévu donc mise en commentaire pour éviter de créer des problèmes avec le code qui fonctionne
    /* 
    private void updateListView() {
        ArrayList<Personnel> listeViolationMensuel = Contrainte.getVerifTmpsTravailMensuel();
        ArrayList<Personnel> listeViolationRepos = Contrainte.getVerifTmpPauseEntre2j();
        respectrepos.setItems(FXCollections.observableArrayList(listeViolationRepos));
        respectmensuel.setItems(FXCollections.observableArrayList(listeViolationMensuel));
    }
   
*/
}

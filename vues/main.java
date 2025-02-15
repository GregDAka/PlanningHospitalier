package vues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import metier.*;


public class main extends Application {
    
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("calendar.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PlanningHopital!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args){
        
        /*
        Personnel p1 = new Personnel("Delemasure","Gabriel","21-03-2004",26,null,null);
        Personnel p2 = new Personnel("Dufour","Benoit","30-12-2004",26,null,null);
        Creneau c1 = new Creneau("2024-01-06", "12:00:00", "13:00:00", 
        1,new ArrayList<Personnel>(Arrays.asList(p1)),new Hashtable<String,Integer>());
        Creneau c2 = new Creneau("2024-01-03", "08:00:00", "15:00:00", 
        1,new ArrayList<Personnel>(Arrays.asList(p1,p2)),new Hashtable<String,Integer>());
        Creneau c3 = new Creneau("2024-01-04", "14:00:00", "15:00:00", 
        1,new ArrayList<Personnel>(Arrays.asList(p1,p2)),new Hashtable<String,Integer>());
        Calendrier calendrier = new Calendrier();
        calendrier.AjouterCreneau(c1);
        System.out.println("On ajoute le c1");
        calendrier.AjouterCreneau(c2);
        System.out.println("On ajoute le c2");
        calendrier.AjouterCreneau(c3);
        System.out.println("On ajoute le c3");
        calendrier.afficherCalendrier();
        calendrier.SupprimerCreneau(c1);
        calendrier.SupprimerCreneau(c2);
        calendrier.SupprimerCreneau(c3);
        System.out.println("-------------");
        calendrier.afficherCalendrier();
        //System.out.println("------------");
        //calendrier.afficherCalendrierPersonnel(p2);
        */
        

        launch();
    }
}

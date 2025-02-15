package test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import org.junit.Ignore;
import org.junit.Test;
import metier.*;

public class CalendrierTest {
    @Test
    public void creerSemaineTest(){
        Personnel p1 = new Personnel("Delemasure", "Gabriel", "21-03-2004", 26, null, null);
        Creneau c1 = new Creneau("06-01-2024", "12:00", "13:00", 
        1,new ArrayList<Personnel>(Arrays.asList(p1)),new Hashtable<String,Integer>());
        // On teste sans s'occuper des fonctions des personnels..
        Calendrier calendrier1 = new Calendrier(); // le constructeur crée automatiquement une semaine 
        Calendrier calendrier2 = new Calendrier(); 
        calendrier1.creerSemaine(); // Les calendriers 1 et 2 ont déjà la première semaine de créé, 
        //on teste l'efficacité de créer semaine sur la deuxième semaine.  
        calendrier2.getCalendrier().put(LocalDate.of(2024, 1, 8), new ArrayList<Creneau>());
        calendrier2.getCalendrier().put(LocalDate.of(2024, 1,9), new ArrayList<Creneau>());
        calendrier2.getCalendrier().put(LocalDate.of(2024, 1, 10), new ArrayList<Creneau>());
        calendrier2.getCalendrier().put(LocalDate.of(2024, 1, 11), new ArrayList<Creneau>());
        calendrier2.getCalendrier().put(LocalDate.of(2024, 1, 12), new ArrayList<Creneau>());
        calendrier2.getCalendrier().put(LocalDate.of(2024, 1, 13), new ArrayList<Creneau>());
        calendrier2.getCalendrier().put(LocalDate.of(2024, 1, 14), new ArrayList<Creneau>());
        assertEquals(calendrier1.getCalendrier(), calendrier2.getCalendrier());
    }

    
    @Test // Pour nous, le test fonctionne si il renvoie faux. 
    public void AjouterCreneauTest(){
        Personnel p1 = new Personnel("Delemasure", "Gabriel", "21-03-2004", 26, null, null);
        Creneau c1 = new Creneau("2024-01-01", "12:00", "13:00", 
        1,new ArrayList<Personnel>(Arrays.asList(p1)),new Hashtable<String,Integer>());
        Calendrier calendrier1 = new Calendrier();
        calendrier1.AjouterCreneau(c1);
        Calendrier calendrier2 = new Calendrier();
        // Le test doit renvoyer faux car dans le calendrier 2 il n'y a pas de créneau.
        assertEquals(calendrier1.getCalendrier(), calendrier2.getCalendrier());
    }
}

package metier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.*;

public class Calendrier {
    /**
     * Hashmap de la liste de créneaux associés aux bons jours
     */
    private LinkedHashMap<LocalDate,ArrayList<Creneau>> calendrier;
    /**
     * Correspond à la date d'initialisation du calendrier soit le 1 janvier 2024
     */
    private LocalDate date=LocalDate.of(2024, 1, 1); // Et pour nouveau calendrier de l'an prochain ? 
    /**
     * C'est un compteur de jour initialiser dans le hashmap
     */
    private int jourPasse = 0;

    /**
     * Créer le calendrier et ajoute la première semaine du calendrier.
     */
    public Calendrier(){
        this.calendrier=new LinkedHashMap<LocalDate,ArrayList<Creneau>>();
        creerSemaine();
    }

    /**
     * Crée une semaine vide de créneau et l'ajoute au calendrier
     * Rappel : le 1er janvier 2024 est un lundi
     */
    public void creerSemaine(){
        LocalDate idate = date;
        idate = idate.plusDays(jourPasse);
        for (int i = 0; i<7; i++){
            calendrier.put(idate, new ArrayList<Creneau>());
            this.jourPasse++;
            idate = idate.plusDays(1);
        }
        System.out.println("Je viens de créer une semaine !");
    }
    /**
     * Ajouter un créneau dans le calendrier, si la semaine où le créneau se déroule n'existe pas,
     * on créer la semaine. 
     * @param creneauAdd le créneau qui va être ajouté au calendrier
     */
    public void AjouterCreneau(Creneau creneauAdd) {
        boolean estAjoute = false;
        
        for (Map.Entry<LocalDate, ArrayList<Creneau>> entre : calendrier.entrySet()) {
            LocalDate dateI = entre.getKey();
            ArrayList<Creneau> listei = entre.getValue();
    
            if (dateI.toString().equals(creneauAdd.getDate())) {
                listei.add(creneauAdd);
                estAjoute = true;
            }
        }
        if (!estAjoute) {
            creerSemaine();
            AjouterCreneau(creneauAdd);;
        }
    }

    public void SupprimerCreneau(Creneau creneauSupp){
        
        for (Map.Entry<LocalDate, ArrayList<Creneau>> entre : calendrier.entrySet()) {
            LocalDate dateI = entre.getKey();
            ArrayList<Creneau> listei = entre.getValue();
            if (dateI.toString().equals(creneauSupp.getDate())) {
                listei.remove(creneauSupp);
                System.out.println("cr supp");
            }
        }
    }
    /**
     * Affiche le calendrier
     */
    public void afficherCalendrier(){
        int compteur =0;
        String [] jourSemaine = {"Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"};
        List<LocalDate> listeCles = calendrier.keySet().stream().collect(Collectors.toList()); //car keySet --> creer un Set mais set n'est pas ordonné
        for (int i =0; i<jourPasse / 7;i++){
            for(int j=0; j<7;j++){
                System.out.print(jourSemaine[j]+" "+listeCles.get(compteur));
                if(calendrier.get(listeCles.get(compteur)).size()==0){
                    System.out.println(" Aucun évenement prévu ce jour");
                    compteur++;
                }
                else{
                    calendrier.get(listeCles.get(compteur)).stream()
                    .forEach(System.out::println);

                    //System.out.println("Problème les amisgo");
                    compteur++;
                }
                System.out.println(" -- ");
            }
        }
    }

    /**
     * Affiche le calendrier d'un seul personnel
     * càd affiche que les créneaux où le personnel est présent 
     * @param persoCalendrier c'est le personnele dont on veut afficher le calendrier.
     */
    public void afficherCalendrierPersonnel(Personnel persoCalendrier){
        int compteur =jourPasse-1;
        String [] jourSemaine = {"Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"};
        List<LocalDate> listeCles = calendrier.keySet().stream().collect(Collectors.toList()); //car keySet --> creer un Set mais set n'est pas ordonné
        for (int i =0; i<jourPasse / 7;i++){
            for(int j=0; j<7;j++){
                System.out.print(jourSemaine[j]+" "+listeCles.get(compteur));
                if(calendrier.get(listeCles.get(compteur)).size()==0 ){
                    System.out.println(" Aucun évenement prévu ce jour");
                    compteur--;
                }
                else{
                    for (int g = 0 ; g<calendrier.get(listeCles.get(compteur)).size();g++){
                        if(calendrier.get(listeCles.get(compteur)).get(g).getListeDuPersonnel().contains(persoCalendrier)){
                            System.out.println(calendrier.get(listeCles.get(compteur)).get(g));
                        }
                    }
                    System.out.println(" Aucun évenement prévu ce jour");
                    compteur--;
                }
                System.out.println(" -- ");
            }
        }
    }
    public LinkedHashMap<LocalDate, ArrayList<Creneau>> getCalendrier() {
        return calendrier;
    }

    public int getJourPasse() {
        return jourPasse;
    }

}

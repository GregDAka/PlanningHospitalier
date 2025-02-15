package metier;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Class métier représentant les créneaux applicables aux créneaux dans le
 * logiciel.
 * Il est possible de créer un créneau, de le modifier, de le supprimer
 * et d'afficher les attributs de l'objet.
 */
public class Creneau {

    /**
     * Identifiant du créneau
     */
    private long id;

    /**
     * durée du créneau
     */
    private double duree; 

    /**
     * Date où le créneau est prévu.
     */
    private String date;

    /**
     * Heure de début du créneau.
     */
    private String debut;

    /**
     * Heure de fin du créneau.
     */
    private String fin;

    /**
     * Contient tout les personnels existant dans le logiciel
     */
    private ArrayList<Personnel> listeDuPersonnel = new ArrayList<Personnel>();

    /**
     * Dictionnaire avec comme clef la spécialité et comme pour valeur le nombre de personnel requis pour cette spécialité
     */
    private Dictionary<String, Integer> NbPersonnelParSpecialite = new Hashtable<String,Integer>();

    /**
     * Constructeur par défaut.
     */
    public Creneau() {
    }

    /**
     * Constructeur
     * @param id
     * @param date
     * @param debut
     * @param fin
     */
    public Creneau(long id,String date, String debut, String fin, double duree){
        this.id = id;
        this.date= date; 
        this.debut= debut;
        this.fin=fin;
        this.duree = duree;
    }

    /**
     * Constructeur
     * @param date
     * @param debut
     * @param fin
     */
    public Creneau(String date, String debut, String fin, double duree,ArrayList<Personnel> listeDuPersonnel
    ,Dictionary<String, Integer> NbPersonnelParSpecialite){
        setDate(date);
        setDebut(debut);
        setFin(fin);
        setDuree(duree);
        setListeDuPersonnel(listeDuPersonnel);
        setNbPersonnelParSpecialite(NbPersonnelParSpecialite);
    }

        /**
     * Constructeur
     * @param date
     * @param debut
     * @param fin
     */
    public Creneau(String date, String debut, String fin, double duree,Dictionary<String, Integer> NbPersonnelParSpecialite){
        setDate(date);
        setDebut(debut);
        setFin(fin);
        setDuree(duree);
        setNbPersonnelParSpecialite(NbPersonnelParSpecialite);
    }

    /**
     * Modifie le créneau, on remplie les champs de modifications et les attributs
     * sont mis à jour avec les nouvelles valeurs (modifiées ou non) .
     * 
     * @param date        Nouvelle valeur de l'attribut date (ou même valeur).
     * @param debut       Nouvelle valeur de l'attribut debut (ou même valeur).
     * @param fin         Nouvelle valeur de l'attribut fin (ou même valeur).
     * @return void
     */
    public void modifAttribut(String date, String debut, String fin, ArrayList<Personnel> listeDuPersonnel,
    Dictionary<String, Integer> NbPersonnelParSpecialite) {
        this.date = date;
        this.debut = debut;
        this.fin = fin;
        this.listeDuPersonnel = listeDuPersonnel;
        this.NbPersonnelParSpecialite = NbPersonnelParSpecialite;
    }

    /**
     * Méthode pour affecter un personnel à un créneau
     * @param p Personnel concerné
     */
    public void affecterPersonnel(Personnel p){
        this.listeDuPersonnel.add(p);
    }

    /**
     * Méthode pour ajouter un nombre de personnel requis à une spécialité donnée
     * @param spe spécialité donnée
     * @param nb le nombre de personnel requis
     */
    public void ajouterUnNbPersonnelParSpecialite(String spe, int nb){
        Integer nombre = Integer.valueOf(nb);
        NbPersonnelParSpecialite.put(spe, nombre);
    }

    /**
     * Affiche les attributs.
     * 
     * @return Chaine contenant les attributs.
     */
    public String toString() {
        return " Creneau{ " +
                "date = " + date +
                "| heure début = " + debut +
                "| heure de fin = " + fin +
                "| durée = " + duree +
                "| liste du personnel = " + listeDuPersonnel + 
                "| nombre de personnel par spécialité = " + NbPersonnelParSpecialite +
                " }";
    }

    public void setNbPersonnelParSpecialite(Dictionary<String, Integer> nbPersonnelParSpecialite) {
        NbPersonnelParSpecialite = nbPersonnelParSpecialite;
    }

    public Dictionary<String, Integer> getNbPersonnelParSpecialite() {
        return NbPersonnelParSpecialite;
    }

    public void setListeDuPersonnel(ArrayList<Personnel> listeDuPersonnel) {
        this.listeDuPersonnel = listeDuPersonnel;
    }

    public ArrayList<Personnel> getListeDuPersonnel() {
        return listeDuPersonnel;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getDebut() {
        return debut;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getFin() {
        return fin;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public double getDuree() {
        return duree;
    }

    public void setDuree(double duree) {
        this.duree = duree;
    }

}
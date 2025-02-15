package metier;

import java.util.ArrayList;

/**
 * Class métier représentant les personnels de l'hôpital dans le logiciel.
 * Il est possible de créer un créneau, de le modifier, de le supprimer
 * et d'afficher les attributs de l'objet.
 */
public class Personnel {

    /**
     * Identifiant du personnel
     */
    private long id;

    /**
     * Nom du personnel ex: Dufey.
     */
    private String nom;

    /**
     * Prénom du personnel ex: April.
     */
    private String prenom;

    /**
     * Date de naissance du personnel ex: 17/05/2004.
     */
    private String dateNaissance;

    /**
     * Temps de travail du personnel par mois ex: 140(heures).
     */
    private int tempsTravailMensuel;

    /**
     * Url/lien de la photo de profil.
     */
    private String photoProfil;

    /**
     * La fonction occupée par le personnel ex: Docteur ou Infirmier.
     */
    private String fonction;

    /**
     * Liste des spécialitées du personnel ex: spécialisé en réanimation et en
     * pédiatrie.
     */
    private ArrayList<String> specialites = new ArrayList<String>();

    /**
     * Liste des créneaux auxquels le personel est affecté.
     */
    private ArrayList<Creneau> creneauxAffecte = new ArrayList<Creneau>();

    /**
     * Constructeur par défaut.
     */
    public Personnel() {
    }

    /**
     * Constructeur avec id
     * 
     * @param id
     * @param nom
     * @param prenom
     * @param date
     * @param tempsTravailMensuel
     * @param photoProfil
     * @param fonction
     */

    public Personnel(long id, String nom, String prenom, String date, int tempsTravailMensuel, String photoProfil,
            String fonction) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = date;
        this.tempsTravailMensuel = tempsTravailMensuel;
        this.photoProfil = photoProfil;
        this.fonction = fonction;
        this.creneauxAffecte = new ArrayList<Creneau>();
    }

    /**
     * Constructeur sans id
     * 
     * @param nom
     * @param prenom
     * @param date
     * @param tempsTravailMensuel
     * @param photoProfil
     * @param fonction
     */
    public Personnel(String nom, String prenom, String date, int tempsTravailMensuel, String photoProfil, String fonction) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = date;
        this.tempsTravailMensuel = tempsTravailMensuel;
        this.photoProfil = photoProfil;
        this.fonction = fonction;
        this.creneauxAffecte = new ArrayList<Creneau>();
    }

    /**
     * Modifie le créneau, on remplie les champs de modifications et les attributs
     * sont mis à jour avec les nouvelles valeurs (modifiées ou non).
     * 
     * @param prenom              Nouvelle valeur de l'attribut prenom (ou même
     *                            valeur).
     * @param nom                 Nouvelle valeur de l'attribut nom (ou même
     *                            valeur).
     * @param dateNaissance       Nouvelle valeur de l'attribut dateNaissance (ou
     *                            même valeur).
     * @param tempsTravailMensuel Nouvelle valeur de l'attribut tempsTravailMensuel
     *                            (ou même valeur).
     * @param fonction            Nouvelle valeur de l'attribut fonction (ou même
     *                            valeur).
     * @param specialites         Nouvelle valeur de l'attribut specialites (ou même
     *                            valeur).
     * @param photoProfil         Nouvelle valeur de l'attribut photoProfil (ou même
     *                            valeur).
     * @return void
     */
    public void modifAttribut(String prenom, String nom, String dateNaissance, int tempsTravailMensuel, String fonction,
            ArrayList<String> specialites){
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.tempsTravailMensuel = tempsTravailMensuel;
        this.fonction = fonction;
        this.specialites = specialites;
    }

    /**
     * Permet d'ajouter un créneau dans la liste des créneaux auxquels un personnel est affecté.
     * @param crn le créneau à ajouter
     * @return void
     */
    public void affecterUnCreneau(Creneau crn){
        creneauxAffecte.add(crn);
    }

    /**
     * Méthode qui permet d'ajouter une spécialité à un personnel
     * @param spe spécialité concernée
     */
     public void ajouterUneSpecialite(String spe){
        specialites.add(spe);
    }

    /**
     * Méthode qui permet de calculer le temps de travail total d'un personnel
     * @return le temps de travail total
     */
    public double calculerTempsDeTravaille(){
        double tempsDeTravaille = creneauxAffecte.stream()
        .mapToDouble(x -> x.getDuree())
        .sum();
        return tempsDeTravaille;
    }

    /**
     * Affiche les attributs.
     * 
     * @return Chaine contenant les attributs.
     */
    public String toString() {
        return  nom +" " + prenom +" " + fonction +"specialites = " + specialites;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public ArrayList<String> getSpecialite() {
        return specialites;
    }

    public void setSpecialite(ArrayList<String> specialites) {
        this.specialites = specialites;
    }

    public int getTempsTravailMensuel() {
        return tempsTravailMensuel;
    }

    public void setTempsTravailMensuel(int tempsTravailMensuel) {
        this.tempsTravailMensuel = tempsTravailMensuel;
    }

    public ArrayList<Creneau> getCreneauxAffecte() {
        return creneauxAffecte;
    }

    public void setCreneauxAffecte(ArrayList<Creneau> creneauxAffecte) {
        this.creneauxAffecte = creneauxAffecte;
    }
}
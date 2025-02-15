package metier;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import DAO.PersonnelDAO;

/**
 * Class métier représentant les contraintes dans le logiciel.<br>
 * Il est possible de créer une contrainte, modifier sa duree, de la supprimer, de
 * l'activer/désactiver et d'afficher les attributs de l'objet.
 */
public class Contrainte {

    /**
     * Identifiant de la contrainte.
     */
    private long id;

    /**
     * Nom que porte la contrainte.
     */
    private String nom;

    /**
     * Description de la contrainte.
     */
    private String description;

    /**
     * temps de travaille en heure.
     * 
     * ex: 35h/semaine ou 140h/mois
     */
    private double duree;

    /**
     * Boolean qui stock true si la contrainte est activée pour les créneaux et
     * false sinon.
     */
    private boolean activation = true;

    /**
     * Indique si la contraite est violée (on le sait grâce à la méthode
     * verifContrainte()).
     * 
     * ex: true si oui false si non
     */
    private boolean violation;

    /**
     * Constructeur
     * L'attribut violation est initialisé grâce à verifContraintes()
     * 
     * @param nom
     * @param description
     * @param duree
     */
    public Contrainte(String nom, String description, double duree) {
        this.nom = nom;
        this.description = description;
        this.duree = duree;
       // verifContraintes();
    }


    /**
     * Modifie la duree de la contrainte contrainte, on sélectionne la nouvelle
     * durée et l'attribut est mis à jour.
     * 
     * @param duree Nouvelle valeur de l'attribut duree (ou même valeur si on
     *              modifie avec la même valeur).
     * 
     * @return void
     */
    public void modifDuree(double duree) {
        this.duree = duree;
    }

    /**
     * Bascule la valeur booléenne de l'attribut activation ex: si activation =
     * false
     * alors il devient true.
     * 
     * @return void
     */
    public void switchActivation() {
        if (this.activation == true){
            this.activation = false;
        }else{
            this.activation = true;
        }
    }

    /**
     * set violation à true si la contrainte est respectée dans le logiciel et
     * false sinon.
     * 
     * @return boolean
     * @throws SQLException
     */
//    public boolean verifContraintes() {
        // TODO implement here
//      return false;
//  }

//Tentative de création de 2 contraintes ; ne causent pas d'erreur mais ne semblent pas non plus fonctionnés donc mises en commentaires pour assurer le bon fonctionnement du reste
 /* 
  public static boolean verifTmpPauseEntre2j() throws SQLException {
        
        ArrayList<Personnel> ListePersonnel = PersonnelDAO.selectPersonnelAsObj();
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter heureformatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        for (Personnel e : ListePersonnel) {
            
            for (int i = 0; i < e.getCreneauxAffecte().size() - 1; i++) {
                LocalDate datej1 = LocalDate.parse(e.getCreneauxAffecte().get(i).getDate(), dateformatter);
                LocalTime heurefincr1 = LocalTime.parse(e.getCreneauxAffecte().get(i).getFin(), heureformatter);
                LocalDate datej2 = LocalDate.parse(e.getCreneauxAffecte().get(i + 1).getDate(), dateformatter);
                LocalTime heuredebutcr2 = LocalTime.parse(e.getCreneauxAffecte().get(i + 1).getDebut(), heureformatter);
                LocalDateTime fincr1 = LocalDateTime.of(datej1,heurefincr1);
                LocalDateTime fincr2 = LocalDateTime.of(datej2,heuredebutcr2);
                if (fincr1.plusHours(11).equals(fincr2)) {
                    
                   
                    return false;
                }
            }
        }
    return true;
    }

    public static ArrayList<Personnel> getVerifTmpPauseEntre2j() {
        ArrayList<Personnel> listeViolationRepos = new ArrayList<>();
        try {
        ArrayList<Personnel> ListePersonnel = PersonnelDAO.selectPersonnelAsObj();
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter heureformatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        for (Personnel e : ListePersonnel) {
            Personnel actuel = e;
            for (int i = 0; i < e.getCreneauxAffecte().size() - 1; i++) {
                LocalDate datej1 = LocalDate.parse(e.getCreneauxAffecte().get(i).getDate(), dateformatter);
                LocalTime heurefincr1 = LocalTime.parse(e.getCreneauxAffecte().get(i).getFin(), heureformatter);
                LocalDate datej2 = LocalDate.parse(e.getCreneauxAffecte().get(i + 1).getDate(), dateformatter);
                LocalTime heuredebutcr2 = LocalTime.parse(e.getCreneauxAffecte().get(i + 1).getDebut(), heureformatter);
                LocalDateTime fincr1 = LocalDateTime.of(datej1,heurefincr1);
                LocalDateTime fincr2 = LocalDateTime.of(datej2,heuredebutcr2);
                if (fincr1.plusHours(11).equals(fincr2)) {
                    System.out.println("Le personnel " + actuel.getId() + actuel.getNom() + " ne respecte pas le temps de pause obligatoire entre 2 jours");
                    listeViolationRepos.add(actuel);
                    
                }
            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        
        }
        return listeViolationRepos;
    
    }


    //Devrait verifier que le temps de travail mensuel est inférieur à 152h pour tous (max 151.67h par mois)
    public static ArrayList<Personnel> getVerifTmpsTravailMensuel()  {
        ArrayList<Personnel> listeViolationMensuel = new ArrayList<>();
        try {
        ArrayList<Personnel> ListePersonnel = PersonnelDAO.selectPersonnelAsObj();
        for (Personnel e : ListePersonnel) {
            Personnel actuel = e;
            int volHoraireMensuel = e.getTempsTravailMensuel();
            if (volHoraireMensuel < 152) {
                System.out.println("Le personnel " + actuel.getId() + actuel.getNom() + " ne respecte pas le temps de pause obligatoire entre 2 jours");
                listeViolationMensuel.add(actuel);
                
            }
        }
    }catch (SQLException e) {
        e.printStackTrace();
    }
       return listeViolationMensuel;
    }

    public static boolean verifTmpsTravailMensuel() throws SQLException {
        
        ArrayList<Personnel> ListePersonnel = PersonnelDAO.selectPersonnelAsObj();
        for (Personnel e : ListePersonnel) {
            
            int volHoraireMensuel = e.getTempsTravailMensuel();
            if (volHoraireMensuel < 152) {
                
                
                return false;
            }
        }
    return true;    
    }*/

    /**
     * Affiche les attributs de l'objet.
     * 
     * @return Chaine contenant les attributs.
     */
    public String toString() {
        return "Contrainte{" +
                " nom = " + nom +
                " description = " + description +
                " duree = " + duree +
                " activation = " + activation + 
                " violation = " + violation +
                "}";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDuree() {
        return duree;
    }

    public void setDuree(double duree) {
        this.duree = duree;
    }

    public boolean isViolation(){
        return violation;
    }

    public void setViolation(boolean violation) {
        this.violation = violation;
    }

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    public boolean isActivation() {
        return activation;
    }

    public int getViolationEnInt(){
        if(violation){
            return 0;
        }
        return 1;
    }

        public int getActivationEnInt(){
        if(activation){
            return 0;
        }
        return 1;
    }

}
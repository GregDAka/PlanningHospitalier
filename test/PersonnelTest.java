package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import metier.Personnel;

public class PersonnelTest {
    @Test
    public void modifPersonnelTest(){
        Personnel res = new Personnel(1, "Delemasure", "Gabriel", "21/03/2004", 2, "monurl", "chef");
        Personnel modif = new Personnel(2, "Duboulois", "Gregory", "11/07/2004", 1, "monurla", "medecin");

        modif.modifAttribut("Gabriel", "Delemasure", "21/03/2004", 2, "chef", res.getSpecialite());
        assertEquals(res.getNom(), modif.getNom());
        assertEquals(res.getPrenom(), modif.getPrenom());
        assertEquals(res.getDateNaissance(), modif.getDateNaissance());
        assertEquals(res.getTempsTravailMensuel(), modif.getTempsTravailMensuel());
        assertEquals(res.getPhotoProfil(), modif.getPhotoProfil());
        assertEquals(res.getFonction(), modif.getFonction());
        assertEquals(res.getSpecialite(), modif.getSpecialite());
    }    




    
    /** 
      
     @Test
    public void supprimerPersonnelTest(){
        Personnel res = new Personnel(1, "Delemasure", "Gabriel", "21/03/2004", 2, "monurl", "chef");
        res.supprimerPersonnel();
        assertEquals(res, null);
    }
    
    */
}

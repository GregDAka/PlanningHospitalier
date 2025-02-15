package test;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import metier.Creneau;

public class CreneauTest {
    
    @Test
    public void modifAttributTest(){
        Creneau resf = new Creneau(1, "22/12/2023", "14:00", "15:00",1.0);
        Creneau modif = new Creneau(1, "25/12/2023", "12:00", "13:00",1);
        modif.modifAttribut("22/12/2023","14:00","15:00",modif.getListeDuPersonnel(),modif.getNbPersonnelParSpecialite());
        assertEquals(resf.getDate(),modif.getDate());
        assertEquals(resf.getDebut(), modif.getDebut());
        assertEquals(resf.getFin(), modif.getFin());
    }
    
    /**
    @Test
    public void supprimerTest(){
        Creneau resf = new Creneau(1, "22/12/2023", "14:00", "15:00");
        resf.supprimerCreneau();
        assertEquals(resf, null);
    }
    */
    
}

package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import metier.Contrainte;

public class ContrainteTest {

    @Test
    public void modifDureeTest() {
        Contrainte c1 = new Contrainte("TempsTravailMax",
        "Contrainte sur le temps maximum d'un membre par semaine",24.0);
        Contrainte res = new Contrainte("TempsTravailMax",
        "Contrainte sur le temps maximum d'un membre par semaine",32.0);
        c1.modifDuree(32.0);
        assertEquals(c1.getDuree(),res.getDuree(),0.0);
    }

    @Test
    public void switchActivationTest() { 
        Contrainte c1 = new Contrainte("TempsTravailMax",
        "Contrainte sur le temps maximum d'un membre par semaine",24.0);
        c1.switchActivation();
        assertEquals(c1.isActivation(), false);
        c1.switchActivation();
        assertEquals(c1.isActivation(), false);

    }
    @Test
    public void verifContraintesTest() {
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mesure;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author john.gdowe
 */
public class ConversionMesureTest {
    
    
    private Unite base, resultat;
    private ConversionMesure conv;
    
    public ConversionMesureTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        base = new Unite("Mètre");
        base = new Unite("Centimètre");
        conv = new ConversionMesure(100, base, resultat);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setBase method, of class ConversionMesure.
     */
    @Test
    public void testSetBase() {
        System.out.println("setBase");
        Unite base = null;
        ConversionMesure instance = null;
        instance.setBase(base);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setResultat method, of class ConversionMesure.
     */
    @Test
    public void testSetResultat() {
        System.out.println("setResultat");
        Unite resultat = null;
        ConversionMesure instance = null;
        instance.setResultat(resultat);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBase method, of class ConversionMesure.
     */
    @Test
    public void testGetBase() {
        System.out.println("getBase");
        ConversionMesure instance = null;
        Unite expResult = null;
        Unite result = instance.getBase();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getResultat method, of class ConversionMesure.
     */
    @Test
    public void testGetResultat() {
        System.out.println("getResultat");
        ConversionMesure instance = null;
        Unite expResult = null;
        Unite result = instance.getResultat();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEchelle method, of class ConversionMesure.
     */
    @Test
    public void testGetEchelle() {
        System.out.println("getEchelle");
        ConversionMesure instance = null;
        double expResult = 0.0;
        double result = instance.getEchelle();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEchelle method, of class ConversionMesure.
     */
    @Test
    public void testSetEchelle() {
        System.out.println("setEchelle");
        double echelle = 0.0;
        ConversionMesure instance = null;
        instance.setEchelle(echelle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convertirResultatBase method, of class ConversionMesure.
     */
    @Test
    public void testConvertir() {
        System.out.println("convertirResultatBase");
        double quantite = 10.0;
        double expResult = 1000.0;
        double result = conv.convertir(quantite);
        assertEquals(expResult, result, 0.0);
    }
}
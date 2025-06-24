/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.colswe.lab.junit.logica;

import com.sun.istack.internal.FinalArrayList;
import dev.colswe.lab.junit.modelo.Entidad;
import dev.colswe.lab.junit.modelo.Sistema;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase para Testear la clase control.
 *
 * @author juanmanuelmartinezromero
 */
public class ControlTest {

    private static final Control CONTROL = new Control();
    private static final ArrayList<Entidad> ARREGLO_NO_VACIO = new FinalArrayList<Entidad>();

    public ControlTest() {
    }

    @BeforeClass
    public static void setUpClass() {

        ARREGLO_NO_VACIO.add(new Entidad(new Long(1000), "Tornillo", new Double(1000)));
        ARREGLO_NO_VACIO.add(new Entidad(new Long(10), "Pinza", new Double(5000)));
        ARREGLO_NO_VACIO.add(new Entidad(new Long(100), "Tuerca", new Double(1200)));
        //ARREGLO_NO_VACIO.add(new Entidad(new Long(1), "Taladro", new Double(75000)));
        ARREGLO_NO_VACIO.add(new Entidad(new Long(2), "Taladro", new Double(0)));
        Sistema.getInstance().setEntidades(ARREGLO_NO_VACIO);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     */
    @Test
    public void testAgregarEntidad() {
        //Prueba para identificar si agrega un elemento normalmente.
        Sistema sistema = Sistema.getInstance();
        int tamAnterior = sistema.getEntidades().size();
        CONTROL.agregarEntidad(new Long(3), "Llave de tubo", new Double(30000));
        int tamSiguiente = sistema.getEntidades().size();
        assertEquals(tamAnterior + 1, tamSiguiente);
        
        //  En el siguiente codigo verifica que la entidad es exactamente igual a la que se agregó
        Entidad entidad = new Entidad(new Long(3), "Llave de tubo", new Double(30000));
        entidad.setId(((Entidad) getLastElement(sistema.getEntidades())).getId());
        assertEquals(getLastElement(sistema.getEntidades()).toString(), entidad.toString());
        
        //  En el siguiente codigo verifica si es valido ingresar atributos nulos a la entidad
        tamAnterior = sistema.getEntidades().size();
        CONTROL.agregarEntidad(null, null, null);
        tamSiguiente = sistema.getEntidades().size();
        assertEquals(tamAnterior + 1, tamSiguiente);
        
        entidad = new Entidad(null, null, null);
        entidad.setId(((Entidad) getLastElement(sistema.getEntidades())).getId());
        assertEquals(getLastElement(sistema.getEntidades()).toString(), entidad.toString());
        
        //  En el siguiente codigo se verifica si se pueden ingresar valores negativos en la cantidad y precio unitario
        
        tamAnterior = sistema.getEntidades().size();
        CONTROL.agregarEntidad(new Long(-3), "Llave de tubo", new Double(-30000));
        tamSiguiente = sistema.getEntidades().size();
        assertEquals(tamAnterior + 1, tamSiguiente);
        
        //  En el siguiente codigo verifica que la entidad es exactamente igual a la que se agregó
        entidad = new Entidad(new Long(-3), "Llave de tubo", new Double(-30000));
        entidad.setId(((Entidad) getLastElement(sistema.getEntidades())).getId());
        assertEquals(getLastElement(sistema.getEntidades()).toString(), entidad.toString());
    }

    @Test
    public void testNoAgregarEntidad() {
        Sistema sistema = Sistema.getInstance();
        String nombre = "Alicates";
        int tamAnterior = sistema.getEntidades().size();
        try {
            CONTROL.agregarEntidad(new Long(3000), nombre, new Double(nombre));
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Ha ocurrido un numberFormatException");
        } finally {
            int tamSiguiente = sistema.getEntidades().size();
            assertEquals(tamAnterior, tamSiguiente);
        }
    }

    /**
     * Test of agregarEntidades method, of class Control.
     */
    @Test
    public void testAgregarEntidades() {
        Sistema sistema = Sistema.getInstance();
        Object[][] entidades = new Object[][]{
            {new Long(2000), "Tuercas", new Double(250)}, // Agrega una Entidad Tornillo con Cantidad 1000 y precio 500
            {new Long(100), "Llave 14", new Double(2550)}, // Agrega una Entidad Pinza con Cantidad 5000 y precio 10
            {new Long(100), "Tuerca", new Double(1200)}, // Agrega una Entidad Tornillo con Cantidad 1000 y precio 500
            {new Long(1), "Taladro", new Double(75000)}, // Agrega una Entidad Taladro con Cantidad 1 y precio 75000
            {new Long(2), "Taladro", new Double(0)}};
        int tamAnterior = sistema.getEntidades().size();
        CONTROL.agregarEntidades(entidades);
        int tamSiguiente = sistema.getEntidades().size();
        assertEquals(tamAnterior + entidades.length, tamSiguiente);
    }
    
    @Test(expected=NullPointerException.class)
    public void testAgregarEntidadesNull() {
        CONTROL.agregarEntidades(null);        
    }

    @Test
    public void testNoAgregarTodasEntidades() {
        Object[][] entidades = new Object[4][4];

        entidades[0][0] = new Long(2000);
        entidades[0][1] = "Tuercas";
        entidades[0][2] = new Double(250);

        try {
            entidades[1][0] = new Long(100);
            entidades[1][1] = "Llave 14";
            entidades[1][2] = new Double("Llave 14");
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Ha ocurrido una o varias excepciones de NullPointerException");
        }

        entidades[2][0] = new Long(100);
        entidades[2][1] = "Tuerca";
        entidades[2][2] = new Double(1200);

        try {
            entidades[3][0] = new Long(2);
            entidades[3][1] = "Taladro";
            entidades[3][2] = new Double("Taladro");
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Ha ocurrido una o varias excepciones de NullPointerException");
        }
        Sistema sistema = Sistema.getInstance();
        int tamAnterior = sistema.getEntidades().size();
        CONTROL.agregarEntidades(entidades);
        int tamSiguiente = sistema.getEntidades().size();
        assertEquals(tamAnterior + entidades.length, tamSiguiente);
    }

    /**
     * Test of ordenar method, of class Control.
     */
    @Test
    public void testOrdenar() {

        /*
         * Aqui deberia ordenar por cada uno de los metodos descritos
         * A decir, por nombre, cantidad, precio unitario y total
         */

        //  Deberia ordenar por cantidad


        Sistema sistema = Sistema.getInstance();

        Collection<Entidad> esperado = new ArrayList<Entidad>();
        Entidad ent1 = new Entidad(new Long(1), "Taladro", new Double(75000));
        Entidad ent2 = new Entidad(new Long(2), "Taladro", new Double(0));
        Entidad ent3 = new Entidad(new Long(10), "Pinza", new Double(5000));
        Entidad ent4 = new Entidad(new Long(100), "Tuerca", new Double(1200));
        Entidad ent5 = new Entidad(new Long(1000), "Tornillo", new Double(1000));
        Entidad ent6 = new Entidad(new Long(1), "Pines", new Double(1200));
        Entidad ent7 = new Entidad(new Long(3), "Llave #14", new Double(25000));

        ent1.setId(new Long(4));
        ent2.setId(new Long(5));
        ent3.setId(new Long(2));
        ent4.setId(new Long(3));
        ent5.setId(new Long(1));
        ent6.setId(new Long(6));
        ent7.setId(new Long(7));

        //<editor-fold defaultstate="collapsed" desc="Prueba de Ordenar Cantidad sin datos repetidos">      
        //prueba.add(cant1);
        esperado.add(ent2);
        esperado.add(ent3);
        esperado.add(ent4);
        esperado.add(ent5);

        sistema.setEntidades(esperado);
        compareCollections(esperado, Entidad.CAMPO_CANTIDAD, "Cantidad sin datos repetidos");
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Prueba de Ordenar por Nombre sin datos repetidos">
        esperado.add(ent3);
        esperado.add(ent1);
        esperado.add(ent5);
        esperado.add(ent4);
        compareCollections(esperado, Entidad.CAMPO_NOMBRE, "Nombre sin datos repetidos");
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Prueba de Ordenar por precio Total sin datos repetidos">
        esperado.add(ent2);
        esperado.add(ent3);
        esperado.add(ent1);
        esperado.add(ent4);
        esperado.add(ent5);
        compareCollections(esperado, Entidad.CAMPO_PRECIO_TOTAL, "Precio Total sin datos repetidos");
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Prueba de Ordenar por precio Unitario sin datos repetidos">
        esperado.add(ent2);
        esperado.add(ent5);
        esperado.add(ent4);
        esperado.add(ent3);
        esperado.add(ent1);
        compareCollections(esperado, Entidad.CAMPO_PRECIO_UNITARIO, "precio Unitario sin datos repetidos");
        //</editor-fold>
        

    }
    
    //<editor-fold defaultstate="collapsed" desc="Prueba de Ordenar por cantidad con datos repetidos">
    
    //PD: El test no pasa porque se espera que aparezcan elementos repetidos
    /*
     * PD: No realice los demas test correspondientes a los demás atributos ya 
    que la diferencia no se puede ver correctamente en la salida
     */
    @Test
    public void testOrdenarCantRep() {
        Sistema sistema = Sistema.getInstance();
        Collection<Entidad> esperado = new ArrayList<Entidad>();
        Entidad ent1 = new Entidad(new Long(1), "Taladro", new Double(75000));
        Entidad ent2 = new Entidad(new Long(2), "Taladro", new Double(0));
        Entidad ent3 = new Entidad(new Long(10), "Pinza", new Double(5000));
        Entidad ent4 = new Entidad(new Long(100), "Tuerca", new Double(1200));
        Entidad ent5 = new Entidad(new Long(1000), "Tornillo", new Double(1000));
        Entidad ent6 = new Entidad(new Long(1), "Pines", new Double(1200));
        Entidad ent7 = new Entidad(new Long(3), "Llave #14", new Double(25000));
        
        ent1.setId(new Long(4));
        ent2.setId(new Long(5));
        ent3.setId(new Long(2));
        ent4.setId(new Long(3));
        ent5.setId(new Long(1));
        ent6.setId(new Long(6));
        ent7.setId(new Long(7));
        
        esperado.add(ent1);
        esperado.add(ent6);
        esperado.add(ent2);
        esperado.add(ent7);
        esperado.add(ent3);
        esperado.add(ent4);
        esperado.add(ent5);
        sistema.setEntidades(esperado);
        compareCollections(esperado, Entidad.CAMPO_CANTIDAD, "Cantidad con datos repetidos");
    }
    //</editor-fold>

    //Test para verificar que lance una NoSuchMethodException (P.D: Deberia funcionar)
//    @Test(expected=NoSuchMethodException.class)
//    public void testOrdenarNoMethod(){
//        Sistema sistema=Sistema.getInstance();
//        sistema.setEntidades(ARREGLO_NO_VACIO);
//        CONTROL.ordenar("Entidad.CualquierCosa");
//    }
    
    private void compareCollections(Collection<Entidad> expected, String byAttr, String description) {
        String expctd = expected.toString();
        String actls = CONTROL.ordenar(byAttr).toString();
        System.out.println("Pasa la prueba de ordenar por " + description + "?: " + expctd.equals(actls));
        assertEquals(expctd, actls);
        expected.clear();
    }

    private Object getLastElement(final Collection c) {
        final Iterator itr = c.iterator();
        Object lastElement = itr.next();
        while (itr.hasNext()) {
            lastElement = itr.next();
        }
        return lastElement;
    }
}

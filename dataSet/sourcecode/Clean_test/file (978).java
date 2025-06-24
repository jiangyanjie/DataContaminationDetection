//Se realizan los import necesarios así como el package a juego.
package Juego;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**Esta es la clase CuadroPieza, En ella se crearan los atributos para cada pieza insertada en el Tablero.
 * */
public class CuadroPieza extends JPanel { 
    //Se definen variables enteras privadas para el seteo de coordenadas x, y.
    private int inX;
    private int inY;
    //Se define una instancia de Pieza, que tendrá un valor inicial de null.
    private Pieza pieza = null;
    //Se crea un nuevo JLabel para cada una de las piezas del tablero.
    public JLabel lbl = new JLabel();

    //Estos colores se usan para el fondo de cada cuadro del tablero.
    private Color fondo;
    private Color resaltar;

    /**
     * Este método es el constructor de CuadroPieza el cual les asigna el lbl además de las coordenadas.
     */
    public CuadroPieza(int x, int y) {
        add(lbl);// agrega el label a Jpanel .add es un metodo del Jpanel
        this.inX = x;
        this.inY = y;
    }

    //-------------------Métodos Get y Set de CuadroPieza-------------------//
    /**
     * Este método se usa para obtener la pieza del cuadro seleccionado.
     */
    public Pieza getPieza() {
        return pieza;
    }

    /**
     * Este método se usa para setear la pieza en el cuadro seleccionado.
     * Como entrada recibe una pieza, y la setea en el lbl correspondiente como salida.
     * 
     */
    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
        if (pieza != null) {
            lbl.setIcon(pieza.getImagenPieza());
            pieza.setCuadroPieza(this);
        } else {
            lbl.setIcon(null);
        }
    }

    
    /**
     * Este método se usa para resaltar pieza del cuadro seleccionado.
     */
    public void resaltarPieza() {
        setBorder(javax.swing.BorderFactory.createLineBorder(Color.CYAN, 4));
        setBackground(resaltar);
        setBorder(null);
    }

    /**
     * Este método se usa para obtener el color de la pieza del cuadro seleccionado.
     * Recibe como entrada una instancia de color, c, con la cual se resaltará la pieza.
     */
    public void resaltarPieza(Color c) {
        if (c != null) {
            setBackground(c);
            setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLUE, 3));
        } else {
            resaltarPieza();
        }
    }

    /**
     * Este método se usa para opacar la pieza del cuadro seleccionado.
     */
    public void opacarPieza() {
        setBackground(getFondo());
        setBorder(null);
    }

    /**
     * Retorna la posición X de la pieza.
     */
    public int getInX() {
        return inX;
    }

    /**
     * Como entrada recibe un parámetro entero que se ubicara como posición x en el tablero.
     */
    public void setInX(int inX) {
        this.inX = inX;
    }

    /**
     * Retorna la posición Y de la pieza.
     */
    public int getInY() {
        return inY;
    }

    /**
     * Como entrada recibe un parámetro entero que se ubicara como posición y en el tablero.
     */
    public void setInY(int inY) {
        this.inY = inY;
    }

    /**
     * Retorna el color de fondo a utilizar en la pieza.
     */
    public Color getFondo() {
        return fondo;
    }

    /**
     * Como entrada recibe una instancia de color la cual será utilizada para setear el fondo de la pieza.
     */
    public void setFondo(Color fondo) {
        setBackground(fondo);// se hereda de un jpnael para poner un fondo
        setBorder(null);
        this.fondo = fondo;
    }

    /**
     * Retorna el color con que se resalta la pieza.
     */
    public Color getResaltar() {
        return resaltar;
    }

    /**
     * Como entrada recibe una instancia de color la cual será utilizada para resaltar la pieza.
     */
    public void setResaltar(Color resaltar) {
        this.resaltar = resaltar;
    }
}
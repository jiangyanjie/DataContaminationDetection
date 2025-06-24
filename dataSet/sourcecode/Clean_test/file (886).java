//Se realizan los import necesarios así como el package a juego.
package Juego;

import Juego.CronometroThread;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Cronometro extends JPanel { // Tenemos la clase Cronómetro que extiende de JPanel
   static CronometroThread cronometro; // Se crea la variable global del cronómetro.
   Object source; //Se define la obtención del source del objeto.
   private javax.swing.JLabel display; //Se define el Jlabel display como privado.
   
   /**
    * Se crea la nueva forma Cronómetro.
    */
   public Cronometro() {
       initComponents();
       cronometro = new CronometroThread(this);
       startActionPerformed();

   }

   /**
    * Este método es llamado por el constructor del Cronómetro para iniciar sus componentes.
    */
   private void initComponents() {
	   setLayout(null);
       display = new JLabel();
       setBackground(new java.awt.Color(240, 240, 240));
       display.setFont(new java.awt.Font("Times New Roman", 0, 50)); 
       display.setText("0 : 0 : 0");
       display.setBounds(30, 0, 257, 64);
       add(display);
       //Aquí ya ha agregado el display al Cronómetro además de setear sus parámetros.
   }

   /**
    * Este método se usa para activar las acciones del cronómetro.
    */
   private void startActionPerformed() { 
           cronometro.createThread();
           cronometro.setLive(true);
           cronometro.setGo(true);
   }
   
   /**
    * Este método se usa para pausar las acciones del cronómetro.
    */

   private void pauseActionPerformed(java.awt.event.ActionEvent evt) {
       cronometro.suspenderThread();
   }

   /**
    * Este método se usa para continuar las acciones del cronómetro.
    */
   private void bcontinueActionPerformed(java.awt.event.ActionEvent evt) {
       cronometro.continuarThread();
   }

   /**
    * Este método se usa para detener las acciones del cronómetro.
    */
   public static void stopActionPerformed() {
       cronometro.setLive(false);
       cronometro.setGo(false);
       cronometro.setSegundos(0);
   }
   /**
    * @retorna el display del Cronómetro.
    */
   public javax.swing.JLabel getDisplay() {
       return display;
   }

   /**
    * Este método setea el display cronómetro.
    */
   public void setDisplay(javax.swing.JLabel display) {
       this.display = display;
   }
}

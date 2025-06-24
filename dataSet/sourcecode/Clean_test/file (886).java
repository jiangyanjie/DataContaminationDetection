//Se realizan los import necesarios as� como el package a juego.
package Juego;

import Juego.CronometroThread;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Cronometro extends JPanel { // Tenemos la clase Cron�metro que extiende de JPanel
   static CronometroThread cronometro; // Se crea la variable global del cron�metro.
   Object source; //Se define la obtenci�n del source del objeto.
   private javax.swing.JLabel display; //Se define el Jlabel display como privado.
   
   /**
    * Se crea la nueva forma Cron�metro.
    */
   public Cronometro() {
       initComponents();
       cronometro = new CronometroThread(this);
       startActionPerformed();

   }

   /**
    * Este m�todo es llamado por el constructor del Cron�metro para iniciar sus componentes.
    */
   private void initComponents() {
	   setLayout(null);
       display = new JLabel();
       setBackground(new java.awt.Color(240, 240, 240));
       display.setFont(new java.awt.Font("Times New Roman", 0, 50)); 
       display.setText("0 : 0 : 0");
       display.setBounds(30, 0, 257, 64);
       add(display);
       //Aqu� ya ha agregado el display al Cron�metro adem�s de setear sus par�metros.
   }

   /**
    * Este m�todo se usa para activar las acciones del cron�metro.
    */
   private void startActionPerformed() { 
           cronometro.createThread();
           cronometro.setLive(true);
           cronometro.setGo(true);
   }
   
   /**
    * Este m�todo se usa para pausar las acciones del cron�metro.
    */

   private void pauseActionPerformed(java.awt.event.ActionEvent evt) {
       cronometro.suspenderThread();
   }

   /**
    * Este m�todo se usa para continuar las acciones del cron�metro.
    */
   private void bcontinueActionPerformed(java.awt.event.ActionEvent evt) {
       cronometro.continuarThread();
   }

   /**
    * Este m�todo se usa para detener las acciones del cron�metro.
    */
   public static void stopActionPerformed() {
       cronometro.setLive(false);
       cronometro.setGo(false);
       cronometro.setSegundos(0);
   }
   /**
    * @retorna el display del Cron�metro.
    */
   public javax.swing.JLabel getDisplay() {
       return display;
   }

   /**
    * Este m�todo setea el display cron�metro.
    */
   public void setDisplay(javax.swing.JLabel display) {
       this.display = display;
   }
}

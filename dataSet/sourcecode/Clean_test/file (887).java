//Se realiza el package a juego.
package Juego;

import Piezas.Caballo_1;
import Piezas.Caballo_2;

public class CronometroThread implements Runnable {
   private Thread hiloCronometro; // Se define el hilo correspondiente a hiloCronometro.
   private boolean go,live; // Se definen booleanos privados.
   private static int segundos; //Se define segundos como un entero global privado.
   private int seg; // Se define seg como un entero privado.
   public static int min;//Se define min como un entero global privado.
   public static int tiempo_a_resucitar;//Se define tiempo_a_resuciar como un entero global privado.
   public static boolean debe_resucitar = false;//Se define debe_resucitar como un booleano global privado.
   private Cronometro reloj; //Se define reloj como una instancia de la clase Cronometro.
   public static int i=0;
   public static int j=0;
   public static int m=0;
   

   /**
    * Este  es el constructor de la clase CronometroThread
    */
   public CronometroThread(Cronometro v) { 
	   //Entradas: Se le pasa como parámetro una instancia de Cronometro.
       reloj = v;
       //Salidas: no retorna nada, pero setea el reloj con el parámetro de entrada.
   }

   /**
    * Este método sirve para hacer que el cronómetro se valide y además se ponga en el display, tiene la condición de que isLive() la
    * cual si es falsa, no permite el funcionamiento del reloj en pantalla.
    */
   public void run() {
//Mediante un try - catch hacemos que el programa tenga robustez frente a errores.
       try {
           while (isLive()) {
               synchronized(this) {
                   while (!isGo())
                       wait();
               }
               Thread.sleep(1000);
               segundos++;

               actualizarThread();
               if(m<(Board_Horses.parametro*Board_Horses.parametro)){
            	   System.out.println(BackTrackingCaballos.filas.size());
	               if(j<(BackTrackingCaballos.filas.size())){            	   
	            	   Board_Horses.board_horses[(int) BackTrackingCaballos.filas.get(m)][(int) BackTrackingCaballos.columnas.get(m)].setPieza(new Caballo_1(0));
	            	   //Board_Horses.board_horses[(int) BackTrackingCaballos.filas.get(m)][(int) BackTrackingCaballos.columnas.get(m)].setPieza(new Caballo_2(0));
	            	   m++;
	            	   j++;
	               }
	               else{
	            	   if(i<(BackTrackingCaballos.filas.size())){
	            		   j=0;
	            		   i++;
	            				   
	            	   }        	           		           	 
	            	   
	               } 
           } 
               
                        
           }
       } catch (InterruptedException e) {}
   }

   /**
    * Este método se crea el hilo para el cronómetro.
    */
   public void createThread() {
       hiloCronometro = new Thread(this);
       hiloCronometro.start();
   }

   /**
    * Este método actualiza el hilo del cronómetro.
    */
   private void actualizarThread() {
	   	   
	   // Si el cronómetro no se ha inicializado, se comienza con 0:0:0;
       if (isLive() == true) {
           int hr= segundos/3600;
           min =(segundos-hr*3600)/60;
           seg = segundos-hr*3600-min*60;
           reloj.getDisplay().setText(""+hr+" : "+min+" : "+seg);    
                     
                      
       } else {
           segundos = 0;
           reloj.getDisplay().setText("0 : 0 : 0");
       }
   }
   
   /**
    * Este método suspende el cronómetro.
    */

   public void suspenderThread() {
       setGo(false);
   }

   /**
    * Este método continua el avance en el cronómetro.
    */
   public synchronized void continuarThread() {
       setGo(true);
       notify();      
   }

   //********** MÉTODOS SET Y GET DE LAS VARIABLES DE TIPO BOOLEAN e INT ************
   /**
    * Returna si esta vivo o no el Cronómetro
    */
   public boolean isLive() {
       return live;
   }

   /**
    * Setea si esta vivo o no el cronómetro.
    */
   public void setLive(boolean live) {
       this.live = live;
   }

   /**
    * Retorna el valor del booleano go.
    */
   public boolean isGo() {
       return go;
   }

   /**
    * Setea en go el parámetro que requerimos.
    */
   public void setGo(boolean go) {
       this.go = go;
   }

   /**
    * Retorna la cantidad de segundos.
    */
   public static int getSegundos() {
       return segundos;
   }

   public int getSeg(){
	   return seg;
   }

   /**
    * Retorna la cantidad de minutos.
    */
   public static int getMinutos(){
	   return min;
   }

   /**
    * Retorna un booleano para Gandalf si debe o no resucitar.
    */
   public static boolean getDebe_Resucitar(){
	   return debe_resucitar;
   }


   /**
    * Es un set que recibe un parámetro y setea los segundos.
    */
   public void setSegundos(int seconds) {
       segundos = seconds;
   }

   /**
    * Setea el tiempo a resucitar de Gandalf.
    */
   public static void setTiempo_a_resucitar(int resucitar){
	   tiempo_a_resucitar = resucitar;
   }
   /**
    * Setea el booleano acerca si debe o no resucitar Gandalf.
    */
   public static void setDebe_Resucitar(boolean resucita){
	   debe_resucitar = resucita; 
   }
}

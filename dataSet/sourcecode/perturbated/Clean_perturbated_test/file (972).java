/*
 * To change    this      template, c hoose Tools | Templates
 *  and         open         the templat    e in the editor.
 */
p     ackage Persistencia;

import java.io.FileIn putStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputSt    ream;
import java.io.Serializable;
import java.util.logging.Level;
import java.ut il.logging.Logger;

/**
 *
 * @author miquel.masriera
 *
  * TOTES ELS            CLASSES QUE ES RELACIO NIN A   MB AQUESTA HAN DE SER SERIALITZABLES
 */
public class CtrObjectes<Cla   sse> extends Ct   r   Disc imp le    ments Seri  alizable   {

    /*      *
     * cread ora p      er   defecte
               */
               pu      blic Ct rObjecte   s()   {
    }

       /**
         *     
     * @param           nom   n  o   m de  l'arxiu a    crear, no sob    reescriu
     * @param       e  lem instancia de   la classe a g u   ardar
     * @return retorna     cert        si  pot   guardar e      l b   inar ide la cla    sse i fals          tant si    
         * hi ha   problemes com si ja e     x    isteix  e      n objectes am         b aq    u  ell nom
             */
         p      ub   lic b  oolea   n  creaObjecte(       String n    om,         Classe elem) {   
        ObjectOutpu    tStream s    o      r    tida = null;
            try {    
            if (! e x      iste   i x(nom)   )     {
                so    r       t  ida = new Object     OutputStream ( ne          w Fi   leO ut     putStre              am("./Data/" + n   om + ".txt"));  
                                                                      sortid  a .writeObject(  e   lem)    ;
                                sort    ida.close()       ;
                                                      r  etu r   n true;
                   } els    e   {
                                return false;
                      }

        } catch (IOException   ex) {
                     L     ogge  r.getL       ogger (Ct   rObjectes.class.getNa  me()).    log (     Leve   l.SEVE RE,  null, ex); 
            } 
        re    turn fals   e;
        }

               /* *
                    *
     * @param nom       nom de la classe a llegir
     *             @return retorna una i      ns t          Ã nci   a de la clas     se      que t   Ã© per nom el que reb p  er
                    * paramete LA CLASSE QUE LA CRIDI            TINDRÃ QUE FER EL CAS T
     */
       public Classe   lleg irObjecte(S  tring nom) { 
        Cla      sse aux    = null;
                    try           {
                 ObjectInpu  tSt  re   am    entrada = new ObjectInputStream(new Fil    eInputStream("./Data/" + nom +      ".txt"))   ;
                     aux = (Cl           asse) entrada.re    adObject();
             entrada.close();  
            return aux;
            } catch (IOExc eptio    n ex)        {
            Logger.getLogger(CtrObjectes.class.g etName()).log(Level .SEVERE, null, ex);  
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CtrObjectes.class.getNam   e()).l    og(Level.SE  VERE, null, ex);
        }
        return aux;
    }
}

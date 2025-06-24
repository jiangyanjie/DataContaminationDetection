package   hoofdmenu.nieuwprofiel;

impo  rt spel.ToonSpelbord;
import utils.Task;

import javax.swing.*;

/*    *
       * Created by     Bart on 10-4-2014      .
 *  /
public class ControleerNieuwProfiel    extends Task {
      
     private JF  rame jfSpelFrame;   
       privat    e  String strSpelerN  aam1;
    private String str  Sp        elerNaam    2;
           private String strTypeNa   am1;
         private Strin         g str      TypeNaam2;
    pri      v      at       e      i  nt       [   ] spelData           =       {
                                 0, 0, 0, 0, 0  ,
                                     0, 0, 0, 0    , 0    ,
                         0, 0, 0, 0, 0,
               0,    0    , 0, 0  , 0,
            0, 0, 0, 0,         0,
         };

         publ   ic Con       troleerNieuwProfie  l(J     Frame jfSpelFrame,    String  strSpelerNa     am     1, String strSpeler   Naam2, String s trType   Na     am1, String strTypeNaa  m2) {

          t    his       .jfSpel   Frame = jfSpe  lFrame;
              thi   s.     strS   p   elerNaa       m1      = s  trSpel  erNaa  m1; 
                   this.s    trSpe                  le   rN      aa   m2 = strS pele      rNaam2;
           this  .strTypeNaam1 = st   r  TypeNaam1;
           this.            strTy    peNaa  m2        = s             tr       TypeNaam2;      
    }

    public        void run() {   

                   boolean val idate =   valid ate ();

                                     if(valid  ate == true)
          {
                              execute()  ;
          }
    }

         pri   vat   e b   ool  ean validate()    {

        boolean vali   date = false;

            /  *  
             Hier wordt de        vali       datie van het    nieuwe profiel        uitgevoerd.
                     Hi              erbij wordt gekek  en of de n   amen niet gelijk zijn, of ze niet beide met hetzelf  de spelen,             etc.
                Pas daa   rna wordt de exec ute uitgevoerd.
          */

        if(strSpe      lerNaam1.isEmpty()    ||    strSpele rNaam2.isEmpty() || strT    ypeNaam1 == null || strType   Naam2 == null) { 
                  JOptionPane.showMessageDialog(nu  l  l, "Niet alle v   eld   en zi  jn   ingevuld!   ", "Oo    ps.. een fou    tje"  , JOptionPane.INFORMATION_MESSAGE)   ;  
           } e   l   se if(strSpelerNaam1.equals(s  trSpelerNaam2)){
            JOpt   ionPan  e.showMessageDialog(null , "De profielnamen mo    eten versc  hillend zijn."           , "Oops   .. een foutje", JOptionPane.INFO R      MA        TION_   MESSAGE);
        }    else if(st   rTypeNaam   1.equals(strType   Naam2)){
                               JOp  tionPa     ne.sho     wMessageDia      log(null, "Het is n        iet mogelijk om biede met een '"+ strTypeNaam        1 + "' t e sp  elen",   "Oop   s   .. een foutje   ", JOptionPane.INFORM      ATION_  MESSAG      E);
        } e   lse {
            validate = t rue;   
        }

        return validate;
    }

          private vo   id execute() {

        //De validate is goedgekeur   d. Het spel kan worden geladen. De namen en de typen worden doorgegeven. De speldata is hier ook leeg.
            ToonSp  elbord toonSpelbord = new ToonSpelbord(jfSpelFrame, strSpelerNaam1, strSpelerNaam2, strTypeNaam1, strTypeNaam2, spelData, strSpelerNaam1, strTypeNaam1, 0);
        toonSpelbord.run();
    }

}
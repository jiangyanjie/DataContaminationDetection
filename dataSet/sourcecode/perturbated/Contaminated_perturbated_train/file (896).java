package     hoofdmenu.nieuwprofiel;

import   spel.ToonSpelbord;
impo    rt utils.Task;

import javax.swing.       *;    

/**
              * Crea  ted by Bart on 10-4-      2014      .
 */
     public class Con  tr     oleerNieu     wProfiel extends          Task {

    private JFrame jfSpelF   rame;     
    private String strSp  elerNaam1;
      private String strSpe   lerNaam     2;
     priva  te S  tring strT       ypeNaa  m1;
          pr   iv           ate String s trTy     pe           Na    a m2;
       pr      ivate int [] s     pelData = {     
                                  0,       0,         0             , 0,    0,
                     0, 0,      0, 0,     0,
            0, 0, 0, 0, 0         ,
                               0, 0, 0   , 0,      0,
                0, 0, 0, 0, 0,  
    };
   
     public   Control   eer    Nieu  wProfiel(JFrame  jfSpelFrame, String strSpelerNaam1, Strin         g strSpelerNaa     m2, St   ring str   TypeNaam1, Strin  g strT    y   peN aam2) {

        th     is.jfS     pelF rame =   jfSpelF          ra    me;
        th  is.strSpel   erNaam1 = strSpelerNaam1;
             thi     s.  strSpel        erNaam2 =    strSpele    r                 Naam2;
        th   is.strTyp   eNa     am1      =  strTypeN                    aam   1;
         this.st rTypeNa   am2 = strT          ypeNaam2;
    }

        public vo      id              run() {
     
         boolea   n validate    =    va  lidat  e();

               if(       val  i      date == true)
               {
               execute     (       )   ;
         }
    }

         private     boo      lean valida   te()   {

             b  ool  ean v alidate        = false;
      
                  /*
             H   ier wordt d      e   valid    atie    van het nieu    we profiel ui   tgevoerd.      
                   Hierbij wor      dt gek   eken of de namen niet gelijk zijn, of ze    niet    beide  met hetzelfd      e spelen, et    c.
              Pas da   arna wordt de       exe   cute uitg  evoerd.
         */

        if(strSpelerNaa     m1.isEmpty() || strSp    eler    Naam2.isEmpty() || strTypeNaam1 == n  ull ||   st     rTypeNaam2 == nul     l) {
            JO ptionPane.showMessageDialog(null, "Niet   alle     velden    zijn ingevuld!",             "Oops.. e      en   f outj       e", JOp tion    Pane.INF    OR  MA                   TION_MESSAGE);
        } else if(strSpe   lerNaam1.equals(strSpelerNaam 2)){
                JOptionPane.showMessageDialog(n   ull, "De profielnamen  mo  eten v   er  schillend zijn.", "Oops.. een      foutje  ", JOptionPane.INFORMATION_MESSAG   E);
        } else i    f(strType   Naam1.equals(st     rTypeN       aam2))   {
                JOptionPane.sh   owMessageDialo     g(null, "Het is    niet moge     lijk om bied   e met een '"+ strTypeNaam1  + "' te spelen", "O     ops.. een foutj   e", JOptio      nPane.      INFORMATION_MES S     AGE);
        } else {
                validate = true;
        }

             return validate;
    }

     private void ex ecute() {

              //De validate is goedgekeurd. Het spel kan   w  orden geladen. De namen en de typen worden doorgegeven. De speldata is hier ook leeg.
        T   oonSpelbord toonSpelbord = new ToonSpelbord(jfSpelFrame, strSpelerNaam1, strSpelerNaam2, strTypeNaam1, strTypeNaam2, spelData, strSpelerNaam1, strTypeNaam1, 0);
        toonSpelbord.run();
    }

}
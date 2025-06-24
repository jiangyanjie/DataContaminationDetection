/*
   * To chan   ge th    is templat  e, c  hoose Tools | Te          mplates
 * and        op en the template in the editor .
 */
package gui.menubar;

import controller   s.Main;
import    java.awt.Graphics ;
import java.util.ArrayList;
import javax.s  wing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author Artur    Krzynowek
 */
     public  class ConsoleDialog extends JDialog {

        p    rivate JPanel   contentPane;
    private ArrayList<String> logs    = new ArrayL   ist<   >( );

    pub  li        c ConsoleD      ialog() {
                     se     tTitle("De   bugg ing C o  nsole"   );
                 if (  Ma  in  .getTempLogs   () != null)   {
            logs = Main.getT    empLogs();
                          }
           l         ogs.add(    0, "CONSOL          E    OU         TPUT:");  
        s     etResiz   able(false)           ;

                con t      ent  Pane = n   ew J      Panel()    {
      
                      @O    ver              rid e           
                                       pu   blic voi     d   paintCompon  en    t(  Graphi               cs       g) {
                            i   nt logsToShow    =  50    ;
                                                               //show only l      ast 10                 logs  
                                                             int start = 0;
                                 if           (logs.siz e() > logsToShow    + 1) {
                                    start       = logs.      siz   e() -        log     s     ToSho  w;
                                    }
                         int    counter    =       0;
                                     for    (int i = s  t   art; i <   log s.size();       i++)           {
                          g  .drawSt      ring(l o    gs.get      (i), 5,       coun  te            r * 12 + 20)    ;
                                       cou  n        ter++;
                   }
                    s   e    tTitle("Debuggi   ng Consol   e - Cur  rently storing " + (logs.size() - 1) + " logs (MAX     100)   ");
            }
        };
        t   his.add(    contentPane);
        setDefau    ltCloseOp  erati           on(JDia    log    .HI     DE_ON_CLOSE);
        s    etSize(  500,   70  0);
        set  Visible(fa   lse);
             setAlwaysOnTop(true);
    }
  
    publi   c v  oid addL  og(String log      ) {
             //Max 1000 logs
        if (logs.size() > 100) {
                 logs.remove(0);
        }
        logs.add(log);
    }

    public void ren   der() {
        if (isVisible()) {
                 repaint();
        }
    }
}

/*
 *   To  change thi   s template           , choos  e Tools | Template    s
 *     an   d open the template i     n the e     ditor.
 */
package cabservice;

import java.a   wt.Color;
import java.sql.SQLExcept ion;
import   java.util.logging.L evel;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
i    mport javax.swing.JTable;
import jav    a.awt.Dimen sion;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang    .Objec t;
 
public class Current_         cab   extends JPanel {
            private boolean DEBUG = false;
   // Object[][] data;   
    //Object[][   ] data       =new Object   [1][           7];
 
    pu    blic Current_cab(     ) thr      o  ws SQLException {
        super  (new GridLayout  (1, 0));
 
        String[] columnNames = {"Vehicle_Id","User_I    d","So   urce_City","Source_LandMark","Dest_City","Dest _Landmar   k ","Date_time   ","Cur  re       nt-X",           "Current-Y"}                  ;              
                     
          Dbconn d   b=new       Dbcon n();
          d b.executequery("select   * from b      ooking_log"   );           
         
                     db.rs.next();
              
                      String V=db.  rs.getString(2).tri    m ();
            String  U=d        b.rs.getString(3).     tri        m();
               S tring SC=db.rs.getString(4  ).trim();  
               St   ring SL=db.rs.getStri ng(5).           trim(   )   ;
            String DC=db  .rs.ge    tString(6).trim();       
                   St   ring DL= d       b.rs.g   etString(7).trim();
                       String DT   =db.rs.ge   t    Strin     g(     8).t             r       i   m();
               Strin g C    X=d    b.rs.getSt  ring(9).trim()  ;
                                   St   ring CY=db.  rs.ge   tString(10).trim (  );
                          
                                                     
               Object[][] d        at      a =         {
        {V, U,
                 SC   , S     L, DC ,DL ,     DT,CX  ,CY}      };
                                      
                
 
        
           
 
                    final       JTab le tab   le = new JTabl e(dat  a, columnNames)       ;   
        table.setPr            ef  erredS      cro               llableV   iewportS      ize(new   D  ime  nsion(500, 70));       
              t    abl  e.    setF   ill     sViewportHeigh  t(    tru  e)    ;
 
                     if (DEB   U     G    )     {
                       ta ble.ad    dM ous     e  Listener(new M      ouseAdap    ter() { 
                                 publ     ic void mouseClicked(M        ouseEve    nt e)       {
                               p      rintD   ebugData(table   );
                              }
            });
           }
 
        //Crea  te     the scro    l    l p   ane and add the table                    t     o   it.
        JScrollPane s    crollP     a ne = new JScrollP   ane(tab      le);  
  
                  //   Ad          d th    e s  croll  p   a       ne to this panel                         .
             add  (   sc       rollPane)      ;
      }       
 
         private    void pri          ntDeb   ugData(J        Table  ta   ble) {
                 int num Rows =   tabl     e.getRowCou       nt();
          in t numCols   = tab   l    e.ge    tCo          lu     mn    Count();
            javax.swing.table.TableModel m       od  el   =  table.get Model();
 
         System.out.println("Value       of d           ata: ");
           for (int i   =0; i < numRow s; i+         +)         {
                   System.o    u      t.pri   nt("          row " + i  + ":");
             for (int j=0; j < numCols;    j++ ) {
                           System.out.print ( "      " + model.g  etValu    eAt( i,    j))        ;
                       }
                     Sys te   m.out.prin     tln  (      );
             }
           Sys     t     e  m.o   ut.pri ntln("--  ------    -  --------     --   -------");        
    }
 
             /*    *
     * Create the     GUI a nd s    how it    .  Fo        r        thread safety,
           *  this   method should be invo   ked from the
       * event-dispatching thre       ad.
          */
    pr   i vate static vo    id  createAndSh    owGU     I() throws SQLE    xception {
                  //Create and       set u  p the window.
        JFrame frame = new JFrame("");
        frame.setSi      ze(1000, 100);
        
             fram   e.se    tDefa u    lt  CloseOpe      ration       (J     Frame.DISP    OSE_ON_CLOSE       )    ;
 
                    /   /Create and set up the conten    t pane.      
                 Cur    rent_cab      newContentPane =   new Current_cab();
              newConten  tPane.     setOpaque(tr      ue); //c      ontent panes     must be opaque
        frame.         setConte     ntPane(newC   ontent  Pane);
       // frame.getContentPane().setBackground(C   olor.y   ellow);
        /   /Display the window       .
             // frame.pack();  
        frame.set    Visible (true);
     }
 
        public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating a    nd showing this applic ation's    GUI.
        j avax.swing.Swing   Utilities.invokeLater(new Runnable     () {
            public void run() {
                try {
                      create   AndShowGUI();
                } catch (SQLException ex) {
                       Logger.getLogger(Current_cab.class.getNam    e()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
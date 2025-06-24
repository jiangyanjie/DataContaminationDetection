/*
 *       To   change this template     , choose Tools | Tem    plates
 * and open th   e temp     late   in the editor.
 */
package GUI;

impo  rt java.awt.Color;
import java.awt.Image;
import java.awt.Panel;
import javax.swing.ImageIcon;
import javax.sw        ing.JLabel;

/**
 *
    * @author jamel
 */
public class Acc euilIterface extends javax.swing.JFra   me {
 public static   void adjustimage(JLabel j,String path   ,int  large ur,int     longeur){
 Image    I  con imageIcon = new ImageIcon(   pa     t     h);
           Image image   = im     ageIco     n.getIma        ge(); //     transform it 
        Image n         e  wimg = ima   ge  .       getScaledInstanc   e(large      ur,     longeur,  jav   a  .aw       t.Image.SCALE          _SM   OOTH); /   / scal      e it the s  mo     oth way  
        j.se       tText("    ");
                  j.setIcon(new ImageIcon(newimg));
 
         
   }
    /**
          *  Creates  new fo      rm A               cceuil Iterface
           */
        publ    ic A  cceuilIterface() {
              in  i  tComponents();
        
          Ima   geIcon imageI   con =   new I  mageIco   n("C:\\User    s\\Khalil\\   Desktop   \\  block.jpg  ");//vous dev     ez mettre le chemin de l'  image sur            vot    re pc
               Image image  = imageIcon  .getI    mage(); // transform i  t 
         Image newimg = image.getScaledInstanc    e(1090   , 650,  java.awt.Image     .SCALE_ SMOOTH); /  / scale it the smooth way  
            jImage.  s   etText  ("");
           jImage.setIcon(    new I     mageIcon(newimg));
       // Acce    uilIterface.adjustimage(I      mageHeade r, "C:\\   Users\\jamel\\Down     load  s\\menu butt  o  n.png",   1500,   40);
          Jp aneldessus.setBackground(new Color(255,255,255,100));
        jPanel2.setBackground(new Color(    255,134,19 ,60)                 );
           jP anel4.setBac     kground(new Color(102,212,67,1  00));
               jPanel5.setBac k  ground(new        Color  (25    5,255,255,100)        )    ;
                    jPanel left   .setBackgr    ound(new Color(255,2   55,255,         100));
              j   Panelright.setBa  c      k   ground(new Color(255,255,255,100));
               jBut           ton1.setBackground(new Colo  r(0,153,1     53,7    0));
                           jButton3.setBackgr   ound(new Co   lor(255,   1  34,19,0));
        jBu   tto     n2         .setBackgro und(new Col   or(   102    ,255,102,0));
            
                    
         }

     /**
     * This method is ca lled from within the constructor to          initialize the f    orm.
     * WARNING: Do NOT  m   o    di   fy this code. The        conte    nt o   f this method is always
     *           regenerated  by t  he Form Editor.
            */
    @SuppressWarnings("unch   ecked  ")
         // <editor-fold    defaultstate   ="col       la  psed" desc="Generat        e      d Code">//GEN-BEGIN:initComponents
    private void      initC   omp        one n  ts(   ) {

          jPanel3 = new ja vax.swing.J  Panel();
        jButton2 = new javax      .swing.  JButton();
        jButton   1 = new             javax.swi  ng    .JButton();
           jButto     n3 = new j   avax.swing.J  Button();
        Jpanel   d  essu   s =   new       javax.swing.JP         a         nel();
                jPanel2      = new ja    v           ax.swing.JPa   ne   l();
          jPanel 4 = new ja    vax.swing.JPan    el();
        jPanel5 = new javax.swi  ng    .JPanel()   ;
        jPanelleft      = new java      x.s  wing.JPanel();
        jP      ane  lright   = new   javax.swing.JP            anel()   ;
           jImage = new javax.swin g.JLabel();

             setDefaultCloseOper  ati    on(j   avax.swing.WindowCo          nstants.EXIT_ON  _C    LOSE);
        getConten    tPane().setLayout(new org.netbeans.lib.awtextr  a.A  bsoluteLayout());

        jPa nel3.setLayout(new o  rg.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.se   tBa  ckground(new java.aw       t  .Color(255, 28       , 16));
        jB     utton2.setFont(new java.awt.Font("S  ylfaen", 3, 11)); // N OI18N
        j  Button2.setFore  ground(new java.awt.C     olo     r(255, 255, 255));
              jButton2.setText("Connection");
        jButton2.setHorizont    alAlignment   (javax.swin  g.Swin        gCo  nst  ants.LEFT);
                jPanel3.add(jButton2   , new org.netb  eans.lib.awte     xtra.          Absolut     eConst      raints   (840,       200, 240, 20));

             jButton1.setBac     kgr ound(new jav    a.a  wt.   Color(153, 0, 1     53));
        jButton1.setFont(new java.awt.Fo     n  t("Sylfaen", 3,     11)); // NOI18N
        jButton1.setForeground(new java.a  wt.C  o     lor(255, 255, 255));
          j    But  ton1.setText("Recher  che...");
          j  Button1.setHorizon   talAlignment(javax.swing.SwingConstants.LEFT)   ;
        jButt  on1.setHorizont        a     lTextPosition(javax.swing.SwingConstants.L   E     FT);
         jPan el3.add(jButton1, new org.netbeans.lib    .awtextr  a.AbsoluteCons   traints(10, 30, 1070, 20));

        jButton3.setBackgro und(new java.aw   t.Color(0,  208, 120));
         jButton3.s etF  ont(new java.a        wt.Fo   nt("Sylfae   n", 3   , 11)); // NOI18N
            jButton3.setForeg    round(new java.awt.Color(255, 255,        255));
        jButton3.setTex     t("  Deal");
                   jButton3.setBorder(n ull);
         jButton3.setHorizonta     l  Ali g     nment(javax.swing.SwingConstan  ts.LEFT);
        jPanel3.a    dd(jButton3, new or  g    .netbeans.l ib  .awtextra.Absol  uteConstraints(10, 200, 300, 20));

            Jpaneldess  us.setBackground(new java.awt.Color(51, 51,      51));
          Jpaneldessus.setBorde           r(new javax.swin    g.bo  rd er.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Jpane      lde   ssu     s.setLay     out(new org     .netbean   s.lib.awtextr a    .AbsoluteL    ayout());
          jPanel3.add(Jpane ldessu   s, new org.netbe    ans.lib.awtextra.Absolu    teConstrai   nts(10, 50, 1070, 140    ));

        jPanel2.setB ack   ground(new jav   a.awt.Color(255, 13         4, 19));
        jPan el2.s     etBorder(n    ew ja  vax.swing     .borde   r.SoftBevelB order(javax.swing.border.Bevel   Borde    r.RAI SED));
              jPanel2.setLayout(new o  rg.netbean s.lib   .awtextra.AbsoluteLayou    t());
        jPanel 3.add(  jPanel2, new org.netbeans.lib.awtextra.AbsoluteCo     nstraints(10, 200, 300, 20));

             jPanel4.setBac   kg round(new j  ava.awt.Col  or(102,       212, 67      ));
                jPanel4.setBorder(n    e  w javax.   swin g   .border.S         oftBevelB       order(javax.swing.border  .BevelBorder.R    AIS  ED));      
        jP    anel4.setLayout    (new org.netbeans.lib.awtextr   a.AbsoluteLayout());
        jPanel3.add(jPanel4     ,   n   ew org.netbeans.lib.awtextra.A   bsolute Constraints(840, 200,   240,    20)  );

        jPa        nel5.setBackground(new j  ava.awt.Color(255, 255, 255) );
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorde        r(javax    .swing.border.BevelBorder.RAISED));

           javax.swing.Gr       oupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);     
           jPanel5.setLayout(j Panel5Layout);      
          jPanel5L ayout    .setHorizontalGroup(   
            jPanel5L   ayo ut.  cre    atePara   l    lelGroup(javax.swing.GroupLayout.Alignmen  t.L   EADING)
                    .addGap(0, 394, Short.MAX_VALUE)
                  );
        jPanel5L       ayout.setV   erticalGrou  p(
                     jPanel 5Layou      t.cr  eateParallelGroup     (javax.swing.GroupLayout.Alignme  nt.LEA       DIN    G)      
            .addGap(0, 334,              Short.MAX_VALUE)
        );

        jPanel3.add(      jPanel5, new org.netb      eans.lib.awte xtr           a.A b  soluteConstraints(360     , 230, 400, 340)     );

        jP  anelleft.setB ackgrou  nd(n           ew     java.awt.Color(255, 255,     255  ));
                jPanelleft.setBorder(new javax.sw         ing  .border.SoftBev  elBorder(  javax.swing.border.Be  velBorder.RAISED)    );

           ja       vax.swing.Group   Layout jPane    lle    ftLayout    = n   ew javax.swing.GroupLayout(     jPanelleft);
        jPanelleft.setLayo       ut(jPanelleft   Layout        );
        j   Panellef tLa yout.setHorizont   alGroup (
            jPan     e   lleftL ayout   .createParallelGroup(javax.s w ing.GroupLay   out.Al      ignment.LEADI   N G)
            .  addGa      p(0,    294, Short.MAX_VALUE)
                   );
             jPanelleftLayout.setVert  icalGroup(
            jPanell   eftLayout.createPara llelGroup   (ja  vax.swing.Group Layout.Alignment. LE    ADING)
                  .addGap(0, 414, Short    .    MAX  _VALUE)
                );        

             jPan  el3.add(jPanell    eft, ne  w org.netbe     ans.lib    .awtextra.AbsoluteConstraints(10,       220, 3         00, 420)    )  ;

        jPane lright.setBackgrou n      d(new java.awt.Co  lor     (255, 255, 255))     ;
         jPanelrig  ht  .setBor          der(new javax.s  wing.bo   rder.SoftBevelBorder(javax  .sw    ing.border.B   evelBo   rder .       RAISED   ));

                      javax.swing  .GroupLayout jPanelrigh   tLayout = ne w    javax.swi  ng.GroupLay  out(jPanelright);
               jPanelright.se    tL   ay    out(jPanelrightLayou  t)     ;
            jPanel  rightLayout.setH   orizontalGroup(
                jPanelrightLa  yout       .createParallelGrou  p(javax.swi         ng    .GroupLayo    ut  .Align  men         t.LEAD     ING)
            .addGap(0, 2  34, Short.MAX_VALUE)
        );
        jPanelr  ightLayout.setVerticalGroup(
            jPanelr     i  ghtLayout       .createParallelGroup(javax.swi   ng.GroupLayout.Alignment.LEADING)
                         .addGap(0, 414, Shor t.  MAX_VALUE)
        );

                    jPanel3.add(jP   anelrigh t,           new org.netbeans.lib.awtextra   .AbsoluteConstraints     (840,   220, 240    ,      -1));

        jIm     age.set      Op      aque( true);
             jPanel3.a       dd(j      Image, new        org.net   bea    ns.lib.awtextra    .AbsoluteCo nstraints(0, 0, -1   , -   1));

             getContentPane().add(jPanel3,    new o       rg.netbeans.lib.awtex    tra.A    bsoluteConstrai nts(0, 0, 10   90, 650))      ;

        pack();
     }// </edit  or-  fold> //GEN-E   ND        :initComponen      t       s   
             
    /**
           * @param arg     s the command line ar  g uments
         *     /
                   public static   void   main  (S   tring   args[]          ) {
        /* Set          t he Nimbus l oo  k and feel */
        //<editor-fol     d def    au ltstate="collapsed" des c=   " Look an     d feel setting code (optional) ">
              /* If            Nimbus     (i ntroduced in Jav    a SE 6) is not available, stay wit   h th      e  default look and fee l.
           * For     details see http://download.oracl    e.com/javase/tutorial/uisw   ing/lookandfeel  /plaf.html 
           */
        try          {
            for (javax   .sw ing.UIManager.    LookAn     dFeelI  nf   o info :   javax.swing.   UIMan   ager.g          etInst   all  edLookAnd    Feels()) {
                if ("Nimbus"  .equals(info.getName())) {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                         break;
                      }
                  }
        }      catch (ClassNotFoundException ex)     {
            java.util  .logging.Logger.getLogger(Acceui  lIterface.cl   as     s.getName()).log(java.u til.logging.Level.SEVERE, null   , ex);
              } catch (InstantiationExce   ption ex) {
            java.ut   il.logging.Logger.getLogger(AcceuilIterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)   {
                java   .util.logging.Logger.getLogger(AcceuilIterface.class.get  Name()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLoo      kAndFe    elException ex) {
                 java.util.l          ogging.Logger.getLogger(AcceuilIterface.class.getName()).log(java.util.loggi  ng.Level.SEVERE, null, ex);
        }
        //</editor-fold>

         /* Cre   ate and display the f    orm */
        java.    a wt.Event Queue.invokeLater(new Runnable() {
              public         void run() {
                new  AcceuilIterface().setVisible(true);
                   }
        });
    }
    // Variables declaration - do no  t modify//GEN-BEGIN:variables
    private javax.swing.JPanel Jpaneldessus;
    private javax.swing.JButton         jButton1;
    privat  e javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jImage;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    priv   ate javax.swing.JPanel jPanel4;
    private javax.s wing.JPanel jPanel5;
    private javax.swing.JPanel jPanelleft;
    private javax.swing.JPanel jPanelright;
    // End of variables declaration//GEN-END:variables
}

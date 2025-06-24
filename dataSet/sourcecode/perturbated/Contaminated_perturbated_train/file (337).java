/*
 *      To change this license he   ader, choose Li     cense Headers in     Project Prope rties.
 * To c      h   ange t   his template file, ch   oose T ools | T   e  mplates
     * and open the template in    the editor.
 */

package org.me.my  backgroundapp ;

/**
 *
 * @author s   haima
      */
public class Account  s    ex    tends javax.swin  g.JFrame {

    /   **    
     * Cr      eat   es   ne     w f        o        rm AddPatients    
      */    
        p         ubli          c Ac counts() {
        initCompo    nents();
    }

            /*   *
     * This method     is call    ed f  ro   m withi  n   the constructor t    o initialize the form.
     * WA RNING: Do NOT modify this c     ode. The content        of this metho    d is always
     * regenerated b   y the Form  Editor.
     */
    @Suppr     es    sWarning    s("unchecked")
      // <editor-fold defaultstate="collapsed" de  sc="Generated Code">//GEN-BEGIN:i   nitCompone      nts
    priv      ate   voi    d initCompo nents() {
              java.awt.GridBagConstraints  gridBagConstraints;
   
          jPane       l1 =    new java    x.swi     ng.JPanel();
          jLabel2      =    new javax   .swing.JLabel();
            jLabe   l3 = new javax.swin     g   .JLabel();
            jTextFi  eld1 =   ne    w j  avax.swing.JTextField(     ) ;
        jLabel4     = new javax.swin g.JLab             el();
        jTextField2 = new j  a  vax.s       wing.JTextFi eld();
                   jLabel5      = new         javax.swing.JLabel();
        jLabel6 = new java   x.swing.JLabel()      ;
                   jLabel1  = new   jav ax.swing.JLa  bel();

             setDefaultCloseOpe    ration(javax.swi          ng.WindowCons   tants.EX       IT_ON_  CLOSE);
        ge   tContentPane().  setLayout(ne           w java.aw     t.GridBagLayout());

                    jPa nel1.setOpaque(false);

          jLabel2.setF   ont(new java.awt.Font("Consta   nti    a", 1, 36)); // NOI18N
          jLabel2.setForegr     o   und(ne      w ja      va  .awt .Color(148, 26, 26));
        jL      abel2.setText("Ac   count        :");

           jLabel3.setFont(new ja va.awt.Font("Constantia", 1, 14)); // NOI18N
            jLabel3.setForegroun                d(new java.awt.       C olor(148, 26, 26));
          jLabel3.setText("First      Name");

        j   TextField1.setBor   der(new javax.swing   .border.LineBorder(new java.awt.Color(      204,  204, 2  04), 1, true));

        jLabel    4.set  F  ont(  n     ew         ja  va   .awt.Font      ("Constantia", 1, 14)); // NOI18N
        jL abel4.setForeground(new java  .aw t.Color(148    , 26, 26 ));
        jLabel4.set Text("SSN");

        jTextField 2.setB    order             (new java    x.swin g.border.Li    ne   Bor     der(n    ew java.awt.Color(204, 204, 204 ), 1, tru      e));

          jLabel5.setText("jLabe   l5");

              jLabel6.setTe   xt      ("jLabel6");

               javax.swing.GroupLayou  t jPanel1Layo    ut = n   ew javax.swi         ng   .GroupLayout(jPane     l1);
               jPanel1.setLayout(jPanel1Layout);
           jPanel     1La       yo  ut.setHoriz   on   tal Group    (
            jPane            l1L  ayo                 ut .cre  ate    ParallelGroup(javax.swing.Gr   oupLayout.Alignm              ent.LEADING)
                      .addGroup(jPa     nel1Layout.crea  teSequentialGroup()
                             .addGap(30, 30, 3        0   )
                           .  addGro     up(jPanel1L         ayout.cre     a        tePa   ral  lelGroup(javax.swing.GroupLayout.Alignment.LE   A          DING)
                               .addGroup(jPanel1Layo ut.createSequentialG  roup ()
                         .addComponent(jLabel4, jav    ax.swing.          Group    Lay   out.PRE  FERR   ED_SIZ    E, 97     , ja  vax.swing.G roupL      ay     out.PREFE RR  ED_S  IZE)
                                           .addPreferredGap(javax.sw     ing.L    ayoutSty       le    .ComponentPlaceme   nt.RELATED)
                                           .  addC     ompo   nent(             jTe           xtField2, javax.swing.GroupLayout.PREFERRED_S     IZE, 170, javax.swing.G  roupLayout.PREFE         RRED_SIZE))
                                     .    addG    rou     p(jP  anel1Layou t.crea     teS  eque  ntialGrou  p()
                                       .ad    dComponent(jLabel3,         javax.swing.Group      L a      yout.PREFERRED      _SIZ     E, 97, javax.swin   g.Gr    oupLayout.PRE    FERR    ED_       SIZE)
                                   .addPref   erredGap(javax.swing.La  youtS     tyle.C      omponen  tPla  c   ement  .RELATED)
                              .addComponent(jTex   t       Field1, javax.swing  .GroupLayout.PREFER  RED_SI    ZE, 170, javax    .swing.     Group Layout.PREFE RRED_SIZE))
                           .addC ompon     ent(jLab  el2)  )
                                .addCo   ntainerGap(341, Short                .MA     X_V ALUE    ))
             .a    ddGroup(j      avax    .swing.GroupLayou  t. Alignme nt.TRAILING, jPan  el1Layout.cre ateS   equ    entialGroup()
                     .addCon   t          ainerGa p(javax.swing.GroupLayout.DEFAULT_SIZE, S  hort.MAX                _VALUE)
                .a       ddGro     up(j   Panel1  L  ayout.createParallelGr   oup(javax.  swing.G   roupLayout.Alignment      . LEA  DING)
                                        .      addGroup(javax.swing      .Gr  o  u          pLayo  ut  .Align      ment.TRAILING, jPanel1  Layou         t.creat   eSe quenti     alG  roup()
                                          .ad       dCom           ponent(  jLabel5)
                          .a  ddGap(257, 257, 257))
                         .a ddGroup(j  avax.s     wing.Grou     pL ayo    ut.Alignment.TRAILING, jPa   nel1La   yout.createSequen tialGroup()
                                      .addC omponent(jLabel6)
                                             . addGap(22   8, 228,   228))))
              );
        jPanel1Layout.setVertica  lGroup(
              jP   anel1Layout.cr     eat  ePa       rallelGroup(javax.sw          ing.GroupL     a     yout.Alignme  nt.LEADING)
                           .addGro     up(jP   anel1Layou      t.createSequentialGroup()
                   .addGap(30,     30, 30)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFE    RRED_SIZE, 38, javax.swing.GroupLayout.PREFER    RED_SIZE)
                     .   addGap(18, 18, 18)
                .    addGroup(jPane     l1Layout.cr   eatePar alle  lGroup(jav       ax.swing.GroupLayout.Alignment.BASELINE)
                          .addComponen    t      (jLabel3    ,      javax.swi n    g.GroupLayout.PREFERRED_SIZE,  23,      javax.s    wing.GroupLayout.PREF        ERRED_SIZE)
                                 .a    ddCo  mponent( jTextFie l     d1, jav    ax.swing.G    roupLayout.  PREF E   RRED_S       IZE,    23, jav   ax   .sw   ing.G       roupL      ayout.PREFERRE    D_  SIZ E))
                  .addGap     ( 18, 18,       18)
                         .addGroup(jPanel1Layout.cre   ateP  arallelGroup(javax.s          wing.Group    Lay             ou         t.Alignment.BAS    ELINE)
                         .addComponent(jLabel4, javax.sw     ing.Gro      upLayout     .PR   EFERRED_SIZE, 23, ja vax.s w              i  ng.GroupLayout.PREFERRED_SIZE)
                       .addComponent(jTextFi     el    d2, javax.swing.GroupLayou   t.  PREFERRED_SIZE, 23,   ja  v    ax.swi        ng.GroupLayout.PREFERRED_SIZE))
                             .ad  d    G  ap(40    , 40, 40)
                  .addCompon    ent(  jLabel    6)
                      .addGap(37, 37, 3     7)
                                .addCom      pone  nt(             jLabel5)
                  .addContai  nerGap(255 , Sh ort.MA       X_VAL UE))
             );

        g    ridBagConstraints   =     new           ja    va.aw  t.GridBagC onstrain     t s();
        g        ridBagConstraints.gridx = 1;   
           g    ridBagConstraints.       gridy = 1;
           gridBagConstrain    t     s.fill  = java.awt.GridBagConstraints.BOTH;
                           getContentPane(        ).add(jPa nel1, gri dBagCon    straints);

            jLabel 1.setIcon(new          javax.swin g.ImageIcon(g   et Clas      s().getRes ource("/org/me/mybackgroundapp/newpackag  e/ASA Enter 2.jpg"))); //  NOI18N
               g     ridBagCon      straints =   new java.awt  .  GridBagConstra ints();
                gridBagConstraints.g    ri   dx = 1;
                   g ridBagCon strai nts.gr     idy          = 1;
        gridBagConstrain        t   s.fill  = java .awt.GridBagCo  nst      rain     ts.BO  TH;
                                getCo nten      tPane().ad d( jLabel1, gridBagConstraint   s);

           pack();
           }// </e     ditor-f  old>/   /GEN-END:initComponen     ts

      /**
         * @param args the co  mm  a    nd   l ine argument   s 
            */
    public static void main(S  trin      g a    rgs[]) {
        /* Se  t the   Nimbus look and fee  l */
        //<ed       itor-       fold defaultst  ate="collapsed" desc="     Look and feel setting code    (optional) "   >
              /  * If Nimb     us (introdu ced      in J  ava SE 6) is not ava ilable, stay with the default look and feel.
           * F or d etai  ls see  ht        tp://download.orac  le.com/javase/t             utor    i     al/uiswing/lo okandfe  el/plaf.html 
            */
           try {
            for (javax.s wi   n          g.UIManager.Look  AndFeel  Info info : ja         vax.swing.UIManager.getInstalledLookAndFeels()) {
                  if ("Nim bus".equals(info.get      Name())) {      
                       javax.swing           .UI  Manager.setL    ookAndFeel( info.  getClas   sName());
                                  br   eak;
                }
            }   
        } catch (ClassNotFoundExce    ption    ex) {
                   java.util.logging.Logger.getLogger(Accounts.class.g etNa  me()).log(ja   va.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex    ) {
            java.util.   logging .Logger.getLogger(A      ccounts.class.get  Name()).log(java.util.logging.Level  .SEVERE, null, ex);
               } catch (Ille     galAc cessExcep     tion ex) {
                 ja    va    .uti    l.logging.Logge      r.getLogger(Accounts.class.get   Name()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Accounts.  cla   ss.getName()). l      og(java.util.logging.Level.SEVERE, n   ull, ex);
          }
        //</editor-fold>

        /* Create and display th e form */
            java.awt.EventQue   ue.invokeLater(new Runnable() {
               public void run() {
                       new Accou nts().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFie  ld1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}

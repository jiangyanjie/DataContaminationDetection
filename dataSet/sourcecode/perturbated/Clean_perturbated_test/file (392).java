/*
   * To change th   is template, c  hoose       Tools | Template         s
 *  and open the template in    the editor.
     */
package Teliko;

/**
       *        
 * @author antonis
 */
public cl  ass ApotyxiaEpek         sergasias  extends javax.swing.JFrame {

    /   **
     * Creates new for  m    Apo     t   yxiaEpeksergasiasI  a tros
     */
                         publi   c Apotyxia E  pek se  rgasias() {      
           i   nitComponents();
    }

    /**
     * This metho           d is cal   led from wi        thin th        e construct or to      in itialize the       form.
     * WA    RNING:    Do NOT modify this code         . The content of      thi            s method is always
     * r   egenerated by the Form Editor.
     */
        @SuppressWarnings("un  checked")
          //   <editor-fold defa      ults  t    at  e="   collap   sed" desc="Genera         ted Code">//GEN-BEG      IN:i   nitC  omponents
    private void initComponents        ()    {

        jLabel1 = new jav   ax.swing.JLabel();
                  jLabel2 = new javax.       swing.JLabel();
        jButton4 =           new javax.sw ing.JBut    ton();

        setDefaultCloseOpera       tion(ja   vax.swing.WindowConstants .DO_NOTH     IN  G_ON_CLOSE);

                   jLabel1.setFont(new java.awt.Font   ("Tahoma", 1, 18));  // N  OI1     8N
              jLabel1.setHorizontalAlignment(javax.swing.Swi   ngCon    stants.CEN   TER);
             jLabel1.setText("Î    ÎµÏÎµÎ¾ÎµÏ Î³   Î±ÏÎ¯Î  ±    Î  ±ÏÎ­Ï    Ï  ÏÎ  µ.");

         jLabel2.setFont(new java.awt     .F   ont("Taho  ma", 1, 18))  ; // NOI18N
        jLabel2.set Horizon        talAlignment(ja  vax.swing.SwingCon        stants.CENTER);
        jL      abel2.setText    ("Î Î±ÏÎ±Îº Î±Î  »Ï ÏÏÎ¿Ï  ÏÎ±    Î¸Î®ÏÏÎµ   Î¾Î±Î½Î¬.")   ;

        jButton4.       setFont    (new jav   a.awt.Font(" Tahoma", 1        , 18))   ; //   NOI   18N    
        j        Button4.setTex        t("ÎÎ");
            jButton4.  ad    dActionLis tener(   new java.awt.eve     n       t.Act   ionLis    tener() {
                      public     void actio    nPerfor       med(java  .awt   .eve   nt.     ActionEvent    evt) {
                   j      Button4ActionPerfo  rmed(evt);
                        }
          });

        javax.swing    .GroupLayout    layout = new j   avax.swing.GroupLay    out(getContentP   ane()    );
        getContentPane(  ).setLayout(   layout);
             layout.s   etHorizo     ntalGroup     (
                layout.crea   teParallelGroup(   java x.swing.GroupLa     yout.Alignment.LEADING    )
                    .addComponent(jLabel1,    j   avax.s  w      i   ng.Gr   oupLayo ut.DEFAULT_SIZE, 400,     Short  .MAX_VALUE)
                 .addComponent(jLabel2, java     x.swing.GroupLayout.DEFAULT_SIZE,    javax.swing    .GroupLayout.DEFAUL T_SIZE, Short.MAX_VALUE)
            .addComponent(jBut  ton4, javax.swing.Group Layou  t.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAUL T_ SIZE, Short.MAX_VALUE)
          );
                       layout.s       etV   erticalGroup(
              l ayout.crea    teParallelGroup(java x.swing.GroupLay  out.Alignment.LEADING)
                   .addGroup(lay         out.create    Se      quenti     alGro  up()
                .addC  omponent(jLabel1, java  x.swing.GroupLayout.PREFERRED_  SIZE, 100, javax.swing  .Gro    u   pL   ay    out.PREFERRED_SIZ    E)
                                   .add   Preferre    dGap( javax. sw   ing.Layo     utStyle.ComponentPlacement.RELATE  D)
                       .addComponent(jLabel2, javax.swin       g.GroupLayout.P   REFERR    ED_SIZE, 100, javax.swing.Gr  oupLayout.PREFERR   ED_SIZE)
                .a       ddPrefe  rredGap(javax.s     wi  n     g.LayoutStyle.ComponentPla  cement.RELATED)
                      .addComponen       t(jButton4     ,         javax.swin     g  .Group        Layout.DEFAULT_SIZE     , 88, Short.MAX_VALU   E))     
          );

                 pack();
     }// </editor-fol     d>//GEN-END:i  nitComponents

    private     void jButton4ActionPerformed(java.awt.     event.Acti  onEvent e  vt) {//GEN-FIRS    T:event_jBu      tto  n4ActionPer for        med
        // TODO add y    o      ur handling      code here: 
        this.s    etVisibl    e(false);
    }//GEN-LAST:event_jB     ut      ton4Actio       nPerformed

    /*      *
     * @param args    the command line arguments
      */
      public s   tatic vo id mai    n(String   a    rg s[])     {
               /* Set the              Nimbus look    and feel */
          //<edi tor     -fold defaultst   a      te         ="    collapsed" des         c=" L                o   o k    an      d feel     s    etting co de    (     op        tional) ">
        /    *    If Nimbus (in  t     rodu      ced i n    Java      S     E 6)     i       s not a  vailable, s  t  ay with the   de    fault look and feel.
             * For        detail    s see http:/  /downloa  d.oracl  e. com/j  avase  /tutorial/uisw    ing/lookandfeel/plaf.html           
          */
        try           {
                for (javax.swing.   UIMan            ager.     Loo kAnd    FeelInfo info : javax.swing.UIMana           ger.getInsta    lledLookA   ndF           eels()) {
                           if ("Nimbus".e      quals(inf o.getName()     )) {
                         javax  .sw    ing.UIManager.set     LookAndFeel(in      fo.       getClassName());
                               bre   ak;
                }
                }
          } catch (ClassNot      Fo     undException ex) {
                          java.uti  l.log    ging.Logger.getL    ogger(ApotyxiaEpeks  erg      asias.class.getName()).l  og(jav      a.          util  .logging.Level.SEVERE, null,              ex);
           } catch (Instant    iationExcept ion ex) {
                    java.util.l      ogging.Logger.getLogger(Apot yxiaEpe      ksergasias        .c          lass.getName()).log(java.util.logging.Level    .SEVERE   , null,   ex);
               } catch     (IllegalAccessExcep    tion ex) {
                java.util.logging.Logger.getLogger(ApotyxiaEpeksergasias.c     lass.getName()).log(j   ava.util.logging.Level.SEVERE, null, e     x);
        } catch (javax.sw   ing.UnsupportedLookAndFeelException ex) {      
              j   ava.util.logging.Logger.getLogg  er(ApotyxiaEpeksergasias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
             //</e  ditor-fold>
  
            /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ApotyxiaEpeksergasias().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}

package   ui.panel;
import javax.swing.JPanel;

/**
           *
 * @au    thor samuel
 */
public class       CreateProje     ctP   an     el e  xt  end   s JPane      l {
         
           /     **    
          * Creates new    form CreateProjec   t
     */
      public Crea  teProjectPanel() {
                        initC   o   mponen      ts();
         r           b3.setSelect ed(tru     e)              ;
        
               }
        
                p   ub    l i      c     String      getName     (       ){
                          return tf   Nam       e   .getText(        );          
      }
    
       p ubl    ic                     S           trin             g getVer        si  on(){
                 if (      rb3.isSele      cte   d())
            return "junit3";
        els    e   if         (rb4.isSelected   ()){
                 ret  urn "j      unit4";
        }
            els  e{
              ret  urn "junit3"   ;
              }    
        }
    
    public void clean(){
        tfName.setTex    t   (  "");
    }
       
    
     /**
                 * This method is called from within the       constructo    r  t o init  ialize the form.
         * WARNING: Do NOT          m    odify this cod    e. The c   ontent of t  his method is alwa   ys
     * re      gen erated by the   Fo     rm Editor.
                   */
        @SuppressWarnings("unchecked")
    // <editor-fold defaultst     ate="co   ll   apsed"  desc="  Generated Code">//GEN-BEG  IN:initComponent    s
    private    void init             Comp  onents() {

               jP    anel1 = new javax.swing.JPanel();
        jLabel1 = new j avax .swing.JLabe    l()   ;
                jPan   el2    =       new    java   x. swing.J      Panel();
        jLabel2 =       new          javax.swing.JLa     bel();
               tf     Name = new javax.swing.JTextField()   ;
            j  Label3 = ne w javax.swing.JLab  el();
        rb3 =      new         j   avax.swing.JR   ad     ioButton();
             r  b4 = ne  w javax.swing.JRad   ioButton();

        setBack      grou nd(java.aw          t.C    olor   .white);

           jPanel1.setBackground      (java.awt.System Color.controlDkShadow);

        jL  abel1.    setFont(new java.awt.Font("Ubun    tu", 0, 14))   ;              // NOI18N
        jLabel1.setForeground(java.awt.Syste    mColor.c ontrolHi  g     hli ght);
                 j        Label1.setIcon(ne    w javax.swing.Image   Icon(getClass().  getRes  ourc           e("/ui       /resource/icon           s/button-project.   png"))); // NOI18N
              jLabel1.setText("Crea       t e New P   riorJ Pr    oject        ") ;        

        javax.swin   g    .GroupLa   yout jPanel1La  yout =  new ja        vax      .swing.GroupLayout(jPane   l1);
        jPanel1.setLay   o ut(jP     anel1Layou     t);
                  jP       anel1La    yout        .setH       orizontalGroup      (
                  jPanel1Layo  ut.createParallelGroup(javax.   swing.Gr  oupLay out.Alignment.LEADING)
                  .addGrou    p(  j Panel1Layou   t.   createSeq   uentialG               roup()
                                      .a     dd   C       onta  inerGap()
                .add        Component(jLabe      l     1)
                          .   addContainerGap(javax.swing.GroupLayout.    DEFAULT   _ S IZE, Short.MAX_V ALUE)             )
            );
        jPanel1Layou t.set   Ver     ticalGro   up(
             jPanel1Layout.       createP      arall  el   Gr               oup(javax.s  wing.GroupLay ou     t.Alignment.LEADING)
             .addGroup(jPan      el1Layout.createSequentialGroup()
                     .a   ddCo  ntainerGap()
                    .addComponent(jLabel1)
                 .addContainerGap(javax  .swing.Gro      upL   ayout.DEF    AULT_SIZ   E, Short.M   AX_VAL    U        E))
           );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtche  dBorder());

             j   Labe  l2.               setFont(new ja  va.awt.Font     ("Ubuntu"  ,      1      , 15)); // NOI18N   
        jLa  bel2.set Te   xt("Project Name");

                jLabel   3.setFo       nt(new ja  va.aw t.F        ont("Ubu    ntu", 1  , 15)); // NOI18N
                                     jLabel3.setText(" JUnit Vers   ion");

        rb3. s     etText("JUnit 3.x");
            r  b3.addAction     Listener(new java.awt.e    vent.ActionL        i      st    ener() {
                 public v  oid    actionPe          rfo   rmed(  java.awt   .       eve  nt.    Action  Event evt)   {
                              rb3ActionPerformed(ev     t);
            }           
              })   ;   
 
        rb   4 .setT         ex t( "JUni  t 4.x");  
                   rb4.ad         dActio   n     Listene         r(new    ja    va   .a   wt.event.Acti   onListener()     {
            pub lic void     action Pe   rf  ormed(java.awt.ev   en t.           A ctionEv  ent e     vt) {
                    r  b4   ActionP  erfor          med(e   vt);
             }
                   });

             jav    ax   .swing.           G  roupLayout jPanel2Layou      t           =           new     j    av     ax. swing.Group  Layout(jPanel2);
          jPanel2.setLa     yout(j                    Pa nel2   L     ayout);
        jPa  nel2Layou   t.setHoriz     ont    alGroup(    
              j     Panel2Lay    out.cre ateParallelGroup(ja  vax.swing.Gro   upLayout   .Al      ign            ment.LEADING)
                           .   ad     d Group  (jP    anel2Layout.c    re ateSequenti   alGroup()
                .ad   dCont    aine      rGap(          ) 
                            .  ad dGr         oup(   j        Panel2Layout.createParallelGroup   (javax.swing   .Gro   upLayout    .Al     ignment.L        EAD          ING)
                            .addComponent(t    f  Name)
                                .addG  rou     p(jPa  nel2Layout     .cre      ateSequentialGrou           p   ()
                            .addGroup(jPa  nel2Layo       ut.c  re    ateParallelGro      up(javax.swing.Gr   oupLa   yout.Al   ignment.LEAD  ING)
                                                       .addC     om    p    o   ne      n     t(jLabel2)
                                     .addC  om   ponent(j  Label3   )
                                      .addG          ro   up(jPanel    2Layout.    createSe      quentialGroup()
                                        .   addCo   mponent(rb3)
                                                                            .addPreferredGap(ja   vax.swing.LayoutSt     yl  e.Component       Pla     ceme        nt.UNR    ELA      TED  )    
                                            .addComponent(        rb4   ))  )
                                               .add     Gap(0, 166,           Short.MAX_VALUE))) 
                .a  ddCon tainerGap())
        );
        jPanel2Layout.setVert   ica    lGr oup(
                jPan   el2Layou    t.createP   arall elGrou   p(javax.swing.Gro    upLayout.Al   ignment.LEAD           ING)
                          .add   Group(jPanel2Layout.creat  eSeque  ntia   lGroup()
                      .addGap(21, 2   1, 21)
                         .addComponent(   jLabel2)
                              .addPreferred     Gap(          javax.swing.Layo  utStyle.ComponentPlacement    .RELATED)
                    .ad  dComponent(   tfN  ame, javax.swin       g.GroupL       ayout.P   REF    ERRED      _S   IZE    , javax.    s     wing.GroupLayout.DEFAULT_SI   ZE, j   avax.swing. Gr  o    upLayout.PREFERRED_SIZE)
                 .a  dd   PreferredGap(javax.sw     ing.LayoutStyle .ComponentPlacemen     t.UNRELATED)
                      .addC  ompon  ent(jLab   el3)
                         .addGap(18, 18, 18)
                   .ad    dGr oup(j Panel2Layout.c          r  eatePa rall     elGroup(javax.swing.GroupLayout.Alignment.BAS   ELINE)
                      .addComponen  t(rb3)
                            .    a ddCo  mponent( rb4))
                 .add   ContainerGap(181, Short.MAX_VALUE))
        );

        javax.s     w    ing.GroupLayout layou              t = new javax.swing.G     roupLay   out(this);
              this.setLayout     (layout);
            layout.setHorizon talGroup(
               layout.createParallelGroup(javax.swing.GroupLayout.Align   ment.L    EADING)
            .addComponent(jPanel 1, javax.swing.GroupLayou     t.D  EFAU LT_SIZE, javax.swing.Grou     pLayout.DEFAULT_SIZE, Short .MAX_VALUE)
            .addGr  oup(la  yout.createSequentialGroup()   
                .addContainerGap()
                   .addCo  mponent(jPanel2, javax.swing.Group    Layout.DEF    AULT_SIZE, javax   .swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addConta         inerGap())
            );
        layout.setVertic  alGroup(
            layout.createParallelGroup(javax.swing.GroupLayou   t      .Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                 .addC      om  ponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT   _SIZE,    j      avax.swing.Group    Layout.PREFERR  ED_SIZ   E)
                  .ad    dPreferredGap(javax .swing.LayoutStyle.ComponentPlacement.UNRELATE    D)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.s    wing.GroupLayo u  t.DEFAULT_SIZE, Short. MAX_VAL  UE)
                    .addCon     tainerGap())
           );
    }/  / </editor-fold>//GEN-END:initComponents

    private void rb3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb3ActionPerformed
        if (rb3.isSele cted())
            rb4.setSele cted(false);
    }//GEN-LAST:event_rb3ActionPerformed

    private void rb4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb4ActionPerformed
        if (rb4.isSelected())
            rb3.setSelected(false);
    }//GEN-LAST:event_rb4ActionPerformed

    // Variables    declaration - do not modify//G     EN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.J    Label jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton rb3;
    private javax.swing.JRadioButton rb4;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables
}

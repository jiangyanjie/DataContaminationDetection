/*
 * To  chang e this template, choose   Tool s | Templat es 
 * a     nd open t he t emplate in the e  ditor.
 */
package vista;

/**
 *
           * @author   A lumno
 */
public class Acta _Notas_Presentacion extends javax.swin   g        .JFrame              {

    /**
      *    C     reates new form Ac         ta_Not     as_Pres       entacion
     */          
       pub       lic      Acta   _Nota s_Presentac ion() { 
        in itComponents();
       }

         /**      
     *          Th   is    method is cal led f    rom within      t    he constr       uct    or t  o initia          lize the form.
     * WARNI    NG: Do N  OT  modify   this code. Th    e con    tent of thi  s    metho  d i  s always    
     * regenerated by the Form Edito r      .
           */
    @SuppressWarnin    gs("unc   h  ec ked")
    // <     editor  -     fold    defaul     tstate="collapsed" desc="G   enerated Code    "      >//GEN-BEGI      N:initComponen  ts     
    private void in    itComponents() {
     
                        jLa  bel6 = new java    x.swing.JLabel();
              jScr  ollPane4 = new javax.swing.JScrollP     ane    ()    ;
        jList1 = new java    x.sw  ing.JLi     st(); 
          jPa  nel1       = new javax.swing.J   Pane  l() ;
               jButton1 = new javax.swin   g.JButton();
          jButt   on2 = new javax.  swi    ng.JButt     on();   
            jButton3 = new ja vax.s  wing.J          Button();
        jLabel3 = new javax.swing.JLabel();
        jComboBox8       =       ne     w javax  .swing.JComboBox();
                jC  omboBox1 = new ja    vax.swi    ng.JComboBox();
        jLabel2 = ne  w java       x.swi     ng.JLabel();

          setDefaultCloseOperation(javax.swin  g.          Windo     wConstants.EXIT_ON_C  LOSE);
        s   etTitle("Acta d  e Not   as");

          jLab  el6.setText    ("Seleccione el    Acta de Notas");       

        jList1.setModel(new   javax.swing.Abs tr    actList   Model    () {
                 String[]   strings = { "Act  -000-0  001  ", "Act-000-0002",            "Act-000-0003", "Act- 000  -  0004", "Act-0  0       0-0005", "      Act      -000-00     0   6", "A   ct-000-000    7", "Act-000-0008", "Act-000-0009   ", "Ac      t-000-0010", " " };
                         pub      l   ic int getS ize         () {   return stri   ngs.length; }
            public O             bject    getElem   entAt(i      nt i) { ret ur n strings[i]; }
            });
           jScrollPane4.setViewportView(j     List1   );

         jPanel1.setBorder(ja  vax.swing.BorderFactory.createT   i   tledBorder("Opciones"));

             jB    u  tton1.setT      ext("N     uev  a    Acta   ");
                       jButton     1.addActionL iste  ner(n    ew java.awt.ev    ent.Ac   tionListener() {
                    public    void    action   Performed (ja va.awt.event.A      ctionEvent  evt   ) {
                       jButton1Actio   n       Performed(ev  t);
                         }
        });

             j Button     2.s etTex  t("A  ctualizar"    );    
        jButton  2.   addActionLi  stener(new java.awt.event.ActionListener(  ) {         
                       public void a       ct  ionPerformed    (java.awt   .even       t. A               ctionEvent e  vt) {
                        jButton2Acti    onPerfo    rm     ed(evt)  ;
            }
            });

        jButton3.setT      ext("Sal         ir");

        javax.swing.Grou  pLayout j    Pa    n        el1Layout = new j       avax.swing.GroupLay    out(jPanel1)  ;
            jPanel  1.setLa     yout(j  Panel1Layout);
                    jPane     l   1L    ayou   t.setHorizontalGroup(
               jP     anel1La yo  u    t.createPa   ra                     llelGroup(javax.swing.GroupLayout.Align   ment.L  EADING)
               .            add    G       r   oup(jPan    el1Layout.createSequ     entialGroup()
                   .addContai   nerGap()
                    .addG roup(jPanel1Layout.c  reateParalle lGroup(javax.swing.Grou     pLayout.Ali    gnment.LE   ADING)
                                .addCompon   e    nt(jButton1, java  x. swing.GroupLayo    ut  .  A lignment.TRAILI NG, javax.swi    ng.GroupLayout.D    EFAUL   T_SIZ       E, 98, Sh       ort.      MAX_VALU  E)     
                                   .add      Component(jButt    on2       , javax.swing.Gr   ou  pL  ayout.DEFA   ULT_   SI        ZE, javax.swing.GroupLay out.  DE FAULT_SIZE, Sh ort.MAX                _VALUE)
                                  .addC    omponent(jButton3, javax.sw     ing.G      roupLayo    ut.DEFAULT_SIZE, j   avax.swing.Grou pL         ayout.DEFAULT_SIZE, Short.MA   X_VALU    E))
                                  .addConta     iner Gap())
        );
             jPanel1Layout.set Vertica  lGroup(
            jPane    l    1Layou   t.createPa       ra   llelGro  up(javax.swing.GroupL  ayout              .   Align          ment.LEADING)
                        .addGroup(j       Panel      1Layout.cr    eateS       equentialGro       up()
                                      .addContainerGap     ()
                  .addComponen    t(jButton1)
                .addPreferre      dGap (j    av    ax.s              wing.Layou tStyle .ComponentPlace   ment    .REL    ATED)
                         .ad       dC  omponent(jButton  2)
                           .addPreferr   edGa  p   (javax.swi  ng.LayoutStyle.ComponentPlac     ement.RELATED)
                 .addComponent(jButt  o       n3)
                  .addConta  inerGap(ja  vax.swing.Gro   upLayout.DEFAULT_SIZE, Short  .     MAX_VALUE))
        );

                  jLabel3.    setTex t("Seleccione el MÃ³dulo Ocupac  ional");

              jCom            boBox8.setMod e       l(new   javax.sw   ing .Defa               ultComb     oBoxModel(new String[     ] {   "I - Tr    atamiento Capilar", "II - Peinados", "III - Ond   ulaciÃ³n", "     IV - Tinte Directo", "V - Corte de C    abello"           }))  ;

                     jCombo  B   ox1.s     etModel(     new javax.swing.DefaultCo   mboBoxModel(new St   ring[]           { "Pel    uqu       er            Ã­a  BÃ¡sica (Es tÃ©tica Pe  rsonal)" , "ConfecciÃ³n Textil"  , "   OperaciÃ³      n de C   o     mputadoras", "Asi   stencia de PanaderÃ­a y PastelerÃ­a", "Asistencia de   Cocina", "Cu       ero y Calzado (ZapaterÃ­a)",       "Venta a      l     De   talle en Tien   da" }));

        jLab    el2.set  Text("Sel        eccione   la Especi   alidad TÃ©cnic     a")    ;
      
                 javax.swing.Grou pLayout layout = new java     x.swing.Group          Layo ut(ge tContentPane())          ;
           g   etContent  Pane().setLayout(  layout);   
         l  ayo    ut.setH                       o ri         zontal Group          (    
                     l     ayout.cre   ateParallelGroup(j      avax.   swing.Gr  oupLayout.Alignment              .LEADI   NG)
                         .addGro      up     (layout.createSequentialGr  ou      p   () 
                        .addGroup(layout.   creat    eP   arallelGroup(javax.swing.GroupL  ayout.Align ment.LEADIN  G)
                                        .    addG     roup(layout. createSequen  tialGroup()
                                   .addGap(6   7, 67,   67)   
                                    .a ddGroup      (layo   ut.creat     ePara          l   l elGroup(javax.swing.GroupLayout.Align     ment.LEADING         ,     false   )
                                       .ad        dGroup(la         you   t  .c reateSequ   entialGroup()
                                                     .addG   roup   (layout.createParall  el    Gr      oup(jav ax.s     wing.GroupL ayout.A  l ignment.LEA  DIN    G)
                                                         .addComponen     t  (jLabel6)
                                                   .a  ddComponent(jS           cr   oll    Pane4,   ja   vax.swing.GroupLayout.PREFERRED_SIZE, 14  9    ,     j     avax.swing.Gr oupLayout.PREFERRED          _  SIZE))
                                                             .add    Gap(68, 68, 68)
                                                   .addCom ponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,     javax.s     wing.Gr   oupLayou  t.DEFAULT_   SIZE, javax.swing.G   roupLay  out.   PREFE   RRED_S      IZE))
                               .addCompone        nt (jComboBox8,      javax.sw    ing.GroupLa  yout.A    lignment.T   RAILING, 0, javax.swing.GroupLayout       .DEF    AU    LT_ SIZ        E   ,      Shor      t.MAX    _V   ALU  E)
                              .a   dd   Gr       o    up(la       yout.crea  teS     eq    uentialG       roup(  )
                                                   .addGap(101, 101, 10  1)
                                                        .addComponent(jLabel3   , javax    .swin  g.G   roupLayout.PREFERRED_SI   ZE, 167, javax.swing  .Gr      oupL     a      you       t.PREF       E    RRED_SIZE))
                               . a  ddComponent(   jComboBox1, javax.swing.GroupL     ayout.Alignme   nt.TRAILI   NG, 0, jav         ax.sw     in  g.GroupLa yout.DEFAULT_SIZE, Sh     ort.MAX_VALU   E)))
                        .addGroup(layou t.createSequentialGroup()
                            .add  Gap(         167,       167     , 167)
                               .addComponent( jLabel2, j ava x    .swing.Group  Layo  ut.PREFERRED_SIZE, 1  75, javax.  sw   ing.   GroupLayout.PRE     FERRED_SIZ    E)     ))  
                  .addCo     n      taine      rGap(70   , Short.MAX    _VALUE))
           );
          layo      ut.setVerticalGroup(
               la     yout.createParall    elGroup(      jav ax.swing.GroupLayo ut.Al  ign    ment.LEADING)
                       .addGroup(javax.swing.GroupLayout    .Align   ment.TRAI   LING,          layout.createS      equentia          lGro  up()
                            .addGa    p(43, 43,     43   ) 
                  .addComponent(jLabel2 )
                           .ad     dPreferredGap(jav     ax.swing.LayoutStyle.Com  ponentPlacement.U        NREL    ATED)
                          .addCompon   ent(j    Com    boBox1, java     x.swing.GroupLa  yout.PREFERRED_SIZE, ja   vax.swing. GroupLayout.DEFAU   LT   _SIZE   , jav      a  x.swing.GroupLa  yo  ut.PREFERRED_     SIZE)
                        .addPreferredGap(jav            ax.swing.La     youtStyle.Component     Pl   acement.RELATED, 47,   Sho    rt.  MAX_V   ALUE)
                   .addC  omponent(jLab   el3)
                           .addPreferred   Gap(javax.s wing.Layout   Style.ComponentPlacement.UN   REL        ATED)
                .addComponent(jComboBox8, j        avax.swing.G   roup       Layou    t.PREFE   RRE        D_SIZE, javax.swing   .GroupLay     out.DEFA        U  L   T_SIZE, javax.swing.GroupLayo ut.PREFERRED_SIZE)
                      .add   Gap (46, 46, 46)
                    .addGrou   p(layout.createPar    a llelGroup(javax.swing.GroupLayout.A   lignme  nt   .LEADING)
                                           .add  Group  (layo   ut.createSequential    Group()
                                    .addComponent(jLabel6)
                                             .addP  referr   edGap(ja    vax.swing.Layou       tStyle. ComponentPlacement.UN        RELATED)
                                  .addComponent(jScroll   Pan    e4, javax.swing.GroupLay   out.PREFERRED_SIZE, 161, javax.swing.GroupLa yout.PRE      FE     RRED_SI        ZE))
                    .addGroup(layout.createSequent  ialGroup()
                               .addGap(40, 40, 40)
                                 .addComponent(j     P an  el1   ,   javax.s  wing     .GroupLayout.PRE     FERRED_SIZE, javax    .s         wing.GroupLayout.DEFAULT     _SIZE, javax.swing    .    GroupLayout.        PREF ERRED_SIZE)))
                 .addGap(49, 49, 49))
        );

        pa  ck();
    }/      / </editor-fold>//GEN-END:initComponents

      pri    vate void jB       u   tton1Ac  tionP  e rformed(  java.a    wt.event.ActionEvent evt  ) {//GEN-FIRST:  event_jBut  ton1Act   ionPerformed
        new Acta_Notas_ Genera  l().setVisi   ble(true);
    }//GE   N-LAST:event_j  Button1ActionPerformed

            pri     vate void jB       u  tton        2Act             i      o   nPerformed(java.awt.ev   ent.Acti      onEven    t evt) {//  GEN-FIRST:eve   nt_jButton2ActionPerformed
      new A      cta_Notas_Detalle()  .s    etVisible(true); 
       }//GEN-LAST:event_jBu   tton2ActionP erformed

    /*  *
     * @param args th    e command line arguments
           */
    publ    ic sta  tic void main(String args[  ]) {
        /*
         *     Set    the Nimbus look and     feel
         */
           //<editor-fold defaultsta   te="collapsed        " desc="     Look and feel set     ting cod   e (opt    ional) ">
                /   *
         * If Ni       mbus (intr      oduced in Java SE 6   ) is not available, sta    y with the
         *     default look and f    ee l. Fo     r deta       ils s ee
         * http:/  /do   wnload.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
          try {
                   for        (j    avax.swing.UIManager.LookAndFeelInfo    info : javax.swing.UIManager.    getInst   alle   dLookAndFeels()) {
                if ("Nimbus  ".equals(info.g     etNa  me())) {
                              javax.swing.UIManager.setLookAndFeel(info.getC   lassName());
                                break;
                }
                }
        } catch    (ClassNotFoundEx   ception ex) {
            java.util.logging.Logger.getLogger(Acta_Notas_Presen     tacion.class.getName()).log(ja      va.util.lo  gging.Level.SEVERE, null, ex);
        } catch (InstantiationException    ex)  {
              java.u      til    .logging.Logger.getLogger(Acta_Notas_Prese      ntacion.class.getName()).log(java.util.logging.Level.S  EVER       E, null, ex);
            } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Acta_Notas_Presentacion.class.getName()).log(java.util.logging.Lev    el.SEVERE   , null, ex);
        }    catch (javax.swing.Unsupp    ortedLookAndFeelException ex        ) {
             java.util.logging.Logger.getLogger(Acta_Notas_Presentacion.class.getName()).log(j ava.util.logging.Leve  l.SEVERE, null, ex);
                  }
        //</editor-fold>

           /*
           *      Create a  nd display     the form
          */
        java.awt.EventQueue.invokeLater(new Runn    able() {

            public void run() {
                new Acta_Notas_Presentacion().setVisible(true       );
               }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
          priva     te javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}

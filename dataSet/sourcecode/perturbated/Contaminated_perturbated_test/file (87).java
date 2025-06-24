/*
    * To change t   his templa  te, c  hoos  e To  ols | Templates
         * and op     en th   e template in the editor   .
 */
package zgpdistribution.ui;

import java.util.ArrayList ;
import javax.swi      ng.JOptionPane;
import zgpdistribution.util.CityDAO;
import zgpdistribution.util.CountryDAO;
i   mport zgpdistribution.util.St     ateDAO;
import zgpdistrib      ution.util.TownshipDAO;
import zgpdistribution.util.oops.City;
import zgpdistribution.util.oops.Country;
import zgpdistribution.util.oops.State;
import zgpdistribution.uti   l.oops . Township;

/**
 *
 * @author John
 */
public class customerInformationEntr yForm e             xt   ends javax.swing.JFra     me      {    

    /**
              * Creates new form cu    stomerInformation     Entr    yForm
             */
    public customerIn       form  atio     nEnt  r     y F  orm() {
                          in   itCompo   n     en    ts();
        initFormDat a()   ;
              }

    private void init Fo   r    mData() {
               jComboBoxT      ownship.ad        dIte  m("- -         Se    lect One --");
        jComboBox City.addItem(   "-  - Select One  --    ");
           jComboBoxState.addItem("-- Select One -- ");
                 jCombo    BoxCountry.addItem("--     Sele   ct One --") ;
        try       {
                                   ArrayList<Township> ts      pList = new TownshipDAO().query  Al          l();
               for        (Township        t      ownship : tspL   ist) {
                         j      Co m boB  o   x Township.addItem(t     ownship.g     etName());
                                    }
                ArrayL  ist<City> cityList = new C    i   tyD AO().que   ryAll();     
                            for (City city :     c                ityLi   st) {  
                        j     Comb      oBoxCity.addIte   m(c   ity.getName() );
               }
               Arra   y     List<State> s    tat              eL  ist  =  new       St  a              t   eDAO().queryAll();
                      f        or (Stat  e st  ate :        stateList)    {
                    jComboBo    xState   .a  d          dItem(state.getName());
                             }
                    ArrayList<Country>     countryList = new CountryDAO().quer      yAl     l();
                             fo  r (Country country : countryList        ) {
                                 jCombo        BoxCount      ry.a   dd     Item(   cou   ntry.g     e   tNam               e());
              }
        } cat    ch (Exception e)      {
             S   yst     em.       err.prin     tln(e.getMessage());
        }

    }

    /**
     * This      met   hod      is called from withi     n the constructor to init  ialize the form.
       * WARNING : Do   NOT  modify th  i   s code.   The conten  t of    this method is   always  
               * regen     erated by the Form Editor.
       */
    @Su   ppr essWarnings("  unchecked")
    // <edit        or-fold defaultstate="collapsed" desc="Generate          d    Code">//GE     N-BEGIN:initCo       mponents
    private void init  Co  mpone    nts() {

         bu  t     tonGroup     1 = new javax.swing.ButtonG roup()   ;
                   b       utt                   onGrou   p2 = new javax.swing.Button     G      roup();
            jPan  el Base = new       javax       .swing .JPan el();
            j   Lab  elOutletN  ame = new jav    ax.swing     .JLabel(    )    ;
        jLabelC   usto      mer Name = new jav     ax.s     wing.JL   abel()  ;
           jLab elAddress = n  ew  ja  vax.swing.JLabel();      
        jLabel    Tsp    = new jav     ax.s  w   ing.JLabel  ();    
        jLabe   lC          ity = new javax.swing.JLabel()  ;
           jLa   bel State = new javax.swing.JLabel();
        jLabelCoun    try = new         ja  vax.swin     g.JL    abel();
            jLabelPhon    e = new javax.sw   ing.JLabel(   );
        jLabelMail = new javax.s   wing.JLabel();   
                    j      TextFieldOut    letName = new    javax        .swing.    JTextField();
              j      TextFi       eldCu     stomerName = new javax.swing      .JTextField();
               jSc    rollPan   e1 = new java  x.swing.JScro    llPane();
        jT e      xt        AreaAddress =    new jav     ax.swi  ng.         JTextArea();
            jTextFie ldPhone = new javax.    swing.JTextFi  eld()     ;
          jTe      xtFieldEmail = ne   w javax.swing.JTextField()  ;
        jPanelCu    stomerType = n  ew javax.swing.JPanel(   );
                                   jRadioButtonRe    tail      = ne       w javax.sw in     g.JRadioButton();
            jRadioButtonPTR   = new javax.swi  ng. JRadio   Button();
        j     Radio   Butt   onPTROutlet = new javax.swin       g. JRadioButt          on()    ;
                jR   ad  ioButton     Wholeslae = new javax.swi   ng.JRadi   oB         utto   n   ()   ;
        j   RadioBu tt        onMT = new java   x.sw ing.JRadioButton              ();
             jRadioButto nDe   aler = new javax       .  sw      ing.JRadioButton(      );
        jL      abelFax = new javax.swing.JLabel();
               jTextFieldFax = new javax.swing.J   TextField();
        jBut  t     o  nSave = new javax.swing.JButton();
                  jButtonC ancel = ne  w javax.swing. J     B      utton()   ;
        jComboBoxCountry =  new javax.swin  g.JComboBox();
        j  ComboBo         xState = ne w jav   ax.    s wing.JCombo    Box();
        j              ComboBox    Township = new javax.swing.JComboBox();
        jComboBoxCity =      new          java   x.swing.JCom    boBox(  );       

              setDefaultCloseOperation(javax.swing.WindowCo      nstants.EXIT_ON_CLOSE);
        setTitle("Cust       omer In formation Entry");

        jPanelBase.setBorder(javax.   swing.BorderFactor   y.createT  it     ledBord       er(null, "    Customer Information Entry",    javax.  swing.border.Title        dBorder.    DEFAULT_JUSTIFICATION, javax.swing.border.Title       dBorde      r.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1,    14))); // NOI18N

        jLabelOutletName.setText("Out   let Name : ")       ;

            jLabelCustomerName.setText("Customer Name : ") ;

        jLabelAddress.set     Text     ("Ad  dress : "    );

        jLabelTsp.    setText("Township : ");

          jLabelCity.s   etText("Ci t   y : ");  

        jLabelSta            te.setText("State / Devision : ");

        jLabel       Country.setText("Country : ");

        jLabelPhone.setText("Phone  : ");

                 jLabelMail.setText(  "e-mail :   "  );

            jTextAreaAddress.setCol      umns(20);    
           jTex  tAreaAddre    ss.setRows(5);
         jScrollPane1.setViewport   View(jTextAre  aAddr   ess);

                jP    anelC      ustomerType.setBo    r    d     er(javax.swing .BorderFactory.createTitl  edBorder("Customer      Type"));

                   buttonGroup2.     add(jRadioButtonR  etail);
              jRa  dioBut     tonRet   ail.set  Text("Retail"    );
   
          buttonGroup2.add(jRadioButt        onPTR);
             jRadi    oBu    ttonPTR.setText    ("PT   R  ");

               button Group2.     add(jRadio             ButtonPTROutle  t);
           jRa  dio      But      tonPT   ROutle     t.setText("PT   R     Out     let");

           buttonGroup     2.add(jRadioBut   tonWholeslae);
                  jRad   ioBut  t      onWholesla  e.setText("   Wholes   ale");
        jRadio  ButtonWholesl   ae.setToolTipText("");   

        buttonGroup2.add(jRadioBu  t  tonMT);
               jRadi      oButtonMT.se     tText("M  T");

        buttonGroup2.add( jRa    dioB    uttonDealer);
          jRa      dioButto  nDeal  er.         se    tText("Deal  er");   

        javax.swing.G  roupL   ayout jPanelCustomerTypeLayout = new javax. swing.GroupLayout(jPanelCus       to       merTy  pe);
           jPanelC  ustomerType.    set  Layo  ut(jPanelCustomerTy     peLayout);
               jPanelCusto   merTypeLayout. se  tHori zo      nt    al     Group (
            jPanelCustomerTypeLayout.create      Pa   rall  elGroup(ja     vax.swing.Gro    upLayout  .               Ali       gnment.LE    AD    ING)
               .a       ddGroup(jPane    lCusto  merTy    peLayout.  cr    e    ateSequ     entialGroup()
                            .addContainerGap()  
                        .addG roup(  jP anelC   ustomerT              ypeLayout.c      reatePara        llelGroup(javax.swing.GroupLayout.Al   ignment.LEADING)
                          .addCom      pon    ent (jRadioButtonRetail)
                         .   ad dComponent(jRadioButtonPTR)
                              .addComponent(jRadioButtonPTRO   u tlet))
                          .addGap(40, 40, 40)   
                         .addGroup(jPa      nelCust       omerTypeLayout.createParalle       lGroup(  ja  v    ax.swing.  GroupLayout.Alignment.LEAD        I NG)
                    .addComponent(j  Radio       ButtonDealer)
                                   .addComponent(jRadioButto  nMT)
                               .addComponent(    jRadioButtonWho leslae))
                 .addCo  ntainerGap(        javax.swin g.GroupLayout.DEFAUL       T_SIZE, Short  .MAX_V A  LUE))
        );
            jPanelCustome   rTypeL   ayout.setVertic   a    l  Group   (
                     jP  an  elCustomerTypeLayout.createParallelG   r    o       u  p         (javax.swing.GroupLa        yout.Al   ig nmen  t.   LEAD I  N G)
                .addGro   up(jPanelC  ustom  er   T ypeL   a y    ou           t.createSeq   uentialGrou             p(   )
                       .ad      dGrou  p(jPanelCustom     erTypeLayou     t.crea    t   eParallelG          roup(javax.swi       ng.     Gr  o  upLayout.Alig     n   me nt.BA SELINE)
                             .addComponent(jRadioButton     Re    tail)
                                   .addC      omponent(jR   adioBu   ttonW      holeslae))
                .addPreferredGap(javax  .swi          ng.LayoutStyle.Comp   onentPlace   m e   nt.UNR   E      LA    TED)
                    .addGroup (               jPan   elCustom er       Ty p  eLayout.cr    eateP  ar    a llelGroup(javax.swing .              GroupLayo         ut.Alignm  ent.      BASEL     INE)
                                 .addComponent(  jRadioB  utto  nPTR           )
                        .addComp on    e nt(jR           ad      ioButtonMT)  )
                    .ad        dPrefe   r    re  dGap(javax.swi  ng.Layou        t  Style                           .Componen    tPlacement.UNR  ELATE    D)
                            .add    Gr      ou   p(jPan      e    l          CustomerTypeLayo ut.createParallelGr    oup(java   x    . swing.GroupLayout.Alignment.BASELINE) 
                       .        a       ddCom pone nt(jRadioButtonPTROutlet   )
                    .addCo             mponent(jRadioBut ton   D       ealer)))
                );
     
            jL ab     el   Fax.    s etText     ("Fax : ");

               jBu  ttonSave     .setText("       Sav   e");
    
                     jButto   nCancel.setText("Can       cel  ");   
               jBu     ttonC         ancel.addActionListener(              new  java.awt.event        .ActionList ene    r() {
              pub  lic void acti         o  nPerfo rmed  (    ja   va.aw  t.  event . Ac  t       ionEvent evt) {
                     jButtonCancelAc     tion   Per formed(evt  );     
            }
         }) ;

        javax.swing    .G  ro       up        Layout jPa           ne     l   BaseLa yo       ut     = new java   x.swing.GroupLayou   t(jPanelBas   e);
        jPane    l       Base.     setL ayout(jPa nelBas   eLayout);
                            j  P anelBaseLayout.setHorizo     ntal Group(
            jPanel   B  as   eLayo   ut.createPar            all     elGroup(javax.swing.GroupLayout.A  lignme   nt.LE   ADI      N         G)
               .    a   ddGroup(j      Pa         nelBas             eLayo      ut.cre  a     teSequent  ialGrou   p()
                                           .a  ddContai nerGap        ()     
                         .addGr ou   p(jPanelBaseLayout.cr   ea       t  e  Pa      ral    l                elGroup(javax.    sw        ing.Grou      pLa  yo  ut.Al   ign   men          t.TRAILING, false)
                         .addComp one       n  t(jPan  elCust   omerT       ype, ja    vax   .s           win      g.GroupLayout .D  EFAULT_SIZ   E, ja  vax  .s  wing. GroupL   a  you   t.DEFAULT_SIZE,     Sho    rt.MAX_      VALUE)
                    .add   Gr    oup(jPanelBaseLayout.creat       eSequ     entialG   roup    ()
                                                 .addGroup  (jPanelBaseLayout.createParallelGroup(  javax.swi     ng.Gr       oup Lay     o ut   .Alignment.    T    RAILING)
                                       .addGroup(j     PanelBaseLa     yout       .create       SequentialGroup(   )
                                      .add     Gro  up(jPanelBas eLayout.crea    te  Pa   rallelGro up(      jav  ax.swing.GroupLayou     t.  A       lignm    ent    .L   EADING)                
                                                 .addC         o    mponent   (jLabelOu         tle  tName)
                                                                                                  .addCom       ponent(jLabelCustomer   Name)
                                                        .addCo  mpone nt(jLa belAddre ss)
                                                                .addCompo      ne                 nt(jL  abe             lTsp )
                                                 .  addComponent(jLabelState)
                                                          .addC  omponent( jLabelCountry)
                                              .addCo  mpone nt  (jLa       belPhon   e)
                                                   .addCompon  ent   (jLabelMail)
                                                                  .  addComp        onent  (      jLabelFax))
                                          .addGap(21,       2       1, 2 1 ))
                                                      .addGrou   p       (javax. swing.GroupLayout.Align  ment.LEA DING, jP  anelBaseLayout.c    r     eate         Sequent ialGro  u  p               ()
                                                               .addComp   onent(jLabelCity      )  
                                                 .addPreferredGap(javax.swing.LayoutSty   le.ComponentPlacemen t.RELAT ED)))
                           .addGroup(jPan  elBas   eLa     yout.  c   reateP  arall       elGrou p(j   a vax.swing.GroupLayout    .Alignment.LEA  DIN              G, false)
                                                               .addComponent(jSc   rollPan e1)
                                                .add    Compo    nent    (jTextFi   eld    OutletNa   me)
                                         .ad     dComponen        t(jTextFieldCust  omerName)
                                            .addComponent(jTextFieldP    h   one)
                                                       .a    ddComp    on     ent(jTextField   Email   )
                                        .addComponent(jTe      xt         F       ieldFax) 
                                   .add    Component(jComb         oBo  xCountry,     0,    javax.swing.Group        L    ayout.DEFAULT_SIZE, Short.MAX_V  ALUE)
                                     .addComponent(jCombo                 BoxState, 0   , javax         .s  wing.GroupLayout.D    EFAULT_SIZE, Short.MAX_VA  L   UE)      
                                        .addComponent(jCo    mboB       oxTowns  hip,    0,      ja                 vax.s   wing.G   ro  up Layout.DEFAULT_SIZE,  Short         .   MAX_VALU    E)
                                   .addComponent(jComboBo  xC         ity        , 0, javax.swing.G roupLayo  ut.DEFAULT  _SIZE, Sh     o     rt.MAX_VALUE))))
                      .addPrefe   rredGap(javax.swing.LayoutS tyle.ComponentPlac ement .RELATED, 65       , Shor        t.M                    AX_VALUE     )
                         .a ddGroup(j  Pa    nelBaseL    ayo ut              .crea teParal     l   el    Group(javax.swing.GroupLayo ut.Alignment.    LEADING, false)
                                           .ad       dCompon    ent(jButton     Save, javax.swin   g.GroupLayo     ut.    DEF     AULT_S           IZE,  javax.swing.GroupLa    yout.D   EFAULT_SI      ZE, S  hort.MAX_VALUE)
                     .a              ddCompo nent   (jButton  Cancel, java          x.swing.GroupLay    out.      DEFAULT_SI   ZE, 159, Short.MA   X_VALUE))
                 .      ad    dGap (22, 22, 22))
         );
        j     PanelBaseLayout. setV  erti calGroup(
            jPanelBaseLay  out.         creat  ePa    rallelGroup(    j  a  vax.swing.Gr             oupLayou  t.Ali    gnment.LEADING)
                      .a  ddG  roup(jPa  n elB       aseLayou     t.create    S      eque    n  tialGroup()
                 .a ddContainerGap    ()
                                              .addGroup(jPanelBas     eLa  yo       u t.c    reateParallelGrou p(ja       va      x  .swing.GroupLa     yo ut.Al        ignment.   LEADING)
                                .add    Compone   nt(     jB   uttonSave, javax     .swing.Gro   upLayout.PREFERRED_SIZE       , 2  00, javax.sw   i  ng.Gr  oupLayout.PREF E  RRED_SIZE)
                              .addGroup(j   PanelBaseLayout    .c  reateSequentia         lGrou   p ()
                           .addGrou p (   jPanelBaseL   ayout.createP     a       rallelGr     oup(  javax.     sw      ing.GroupLayou t.Ali gnm  ent.  TRAILING)
                                           .addGroup(j Pane   lBaseLay    out.createS  equentialGr       ou      p()
                                             .addGroup  (j    PanelBaseLayout.createPar a     ll     elGroup(ja  vax.swing.G   r  oupLa yout.Alignment.BAS   EL     INE)
                                                         .addCompone         nt(jLabel         Outlet  Name         )
                                                      .add  C     omponent(jTextFie  ldOutletName,         java  x.    swin           g    . Gro upLayout.PREFE   RRE  D_SI   ZE,     ja      vax  .swing.GroupLayout.DEFAULT_SIZE,     javax.swing.Gr            oupLayou  t.PREFERRED_SIZ    E))
                                                        .add  PreferredG   ap(javax.swing.LayoutStyle.Co    mponen     tPlace     ment.   RELATED)
                                          .a     ddGroup(jPan   elBaseLay out.c    reateParallelGroup        (javax.  swing.G    ro   upLa    yout.Align    ment   .    BASE    L   INE)
                                       .addComponent(jLabel   Custome  rName )
                                                                  .a    ddCom      ponent(jTextFieldC   ustomerName, ja          vax.swing.   GroupLa  yout     .P  REFERRED_SIZE, java  x.swing.GroupLayo    ut.DEFAULT_SI ZE, j   avax.swi     ng.Gro     upL            ayout.PREFE  RRED_SIZE))    
                                               .addP     r eferredGap(j   a           vax.swing.LayoutStyle.Componen       tP   la cemen    t.RE    LATED)
                                     .addCompo         nent(jL    ab  e  lAddress   )
                                                        .addGap(80, 80, 80))
                                         .a ddCom   po    nent(jScrollPane1,   javax.swing   .GroupLayout.PREFERRED_SIZE, javax.sw     ing.GroupL    a     yout.DEFAU   LT_S IZE, jav               ax.swing.GroupLayout.PRE FERRED_     SIZE))
                            .addGap(7    ,    7, 7)
                                         .addGro            up(jPane   lBas    eLay    out.create     Pa  ra       lle    lG    r   oup(javax.swing.Group         Layout.Ali   gnm   e   n   t.BASEL          INE)
                            .add              Component(jLabelT       sp)
                             .a  ddCo   mpo  nent(jCom      bo  BoxTow     n   ship, javax.swing.Gro upL   ayout.PREF    E  RRED_SIZE,         javax.swing.GroupLayout.DEF     AULT_SIZE, javax .swing.GroupLa  yout.PR             EFERRED_SIZE))
                          .addPreferredGap(javax.swing   .Layo     utSt     yle.Compo    nentPlacement.RELAT ED)
                                   .             addGroup(j     Pan         elBaseLayout.createPar   allelGroup   (javax.swing.GroupL ayout.     Alignment.BASE   LINE)
                                           .addComponent(jComb oBoxCity   ,  javax.swing.G   rou    pLayout.PREFERRED_SIZE, j        avax          .swing.Gr   oupLayout.     DEFAULT_SIZE, javax.swing.  GroupL   ay    out.PREFERRED_S   IZE) 
                                         .a     d      dCo     mponent(jLabelCity))))
                                            .addPreferred  Ga  p(java       x.swin  g.Layou     tStyle.Componen tPlacement.UNRE  LATED)
                  .ad   d  Gr   oup(jPane  lBaseLayo  ut.createParallelGroup   (javax.swing.  GroupLayout.Alignmen   t .LEADING)
                           .addGroup(jPane    lBaseLayout.    createSeq  uentialGr oup()
                              .ad d   Group(   jPa     nelBase  Layout.crea    teParallel  Group   (jav       ax.swin   g.Gr     oupLayout  .Alignment.BASELINE     )
                                  .addCo       mponent(jLabelState)
                            .ad  dCompone nt(jComboBo x  State, javax.swing.GroupLay     out.PR      EFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLa   yout.     PREFERRE    D_SIZE))
                             .ad    dPreferredGap(javax       .swing.Lay   outStyle   .   ComponentPla   cement.  RELATED)
                         .a ddGroup(   jPanelBaseLayout.createParallelGro       up(j avax.swing.    GroupLayout.A    lignment .BASE   LINE)
                                  .addComponent(jLabelCountry)
                                             .a     ddComponent(jC   o  m    boBoxCountry, j avax.swing.GroupLayout.PRE   FERRED    _S    IZE, javax.swing.GroupLayou t.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                           .add   PreferredGap(     javax.swing.      LayoutStyle.Compone  ntPlac               em      ent.   RELA    T           ED)   
                                .addGroup(jPanelBaseLayout.createParallelGr  oup(jav    ax.swin  g.    G   roupLayout.Alignme  nt.BAS ELINE)
                                   .addCompone  nt   (jLab  elPhone)
                                   .ad      dComponent(jT              extF    ieldPhone, javax.swing.     GroupLayout.PREFERRED_SIZE, javax.  swing.Gro    upLa   yout.DEFAULT_   SIZE, java    x.s       wing.GroupLa  yo  ut.  PREFERRED_SIZE))
                                       .addPrefe    rredGap(javax.swing.Lay  outS    tyle.Compo   nen  tPlac ement.RELAT     ED)
                        .ad  d     Group(jP   an        elBaseLayout.createParall   elGro up(javax.swing.GroupLayout.Alignment      .TRAILI  NG)
                                    .a  dd   Component(  jLabelMail)
                             .    addComponent(jTextFieldEmail  , javax.swing.Group  Layout.PREFERRED_SIZE, javax.swing.Gro  upLayout.DEFAULT_    SIZE, javax.swing.GroupLayout.PREFERRED_SI     ZE))
                                             .addPreferredGap(javax.swing.LayoutStyle    .ComponentPlacement.RELATE  D)  
                                      .addGroup(jPanelBa  seLay    out.cr     eateParallelG    roup(ja  vax.sw     ing.Gr  oupL    ayout.Alignment.BASE  LINE)   
                              .a   d  dComponent(j               LabelFax)
                                   .addComponent(jTextFiel       dFax, javax.swing.GroupLayout.PREF    ERR   ED_SIZE    , javax.swing.GroupLayout.DEFA ULT_SIZE, ja vax.swing.G  roupL       ayo    ut.PREFER  RED_SIZ    E))
                        .addGap(  17, 17, 17)
                            .addComponent(jPanelCus      tom erType, javax.swing.GroupLayout.PREFERRED_  SIZ  E, ja      va  x.swing.GroupL       ayout.DEFAULT_S      IZE, javax.swing.GroupLayout.PREFERRED_S IZE))
                       .addComponent(j          B  uttonCa  ncel, javax.swing.Grou  pLayout.PREFERRE   D_SIZE, 200, javax.swing.Group La  yout.PREFERR  ED_   SIZE))
                      .addContainerGap(javax.swi   ng.Group     L         ayout.DEFAUL T_SIZE, Shor   t.M      AX_VALUE))
        );

        javax.swing.Gro   upLayout layout = new javax.swing          .GroupLayout(get      ContentPane());
               getContentP   ane().setLayo    ut(layout);
        lay  out.setHorizontalGr      ou p(
            layout.creat eParall   elGroup(j  avax.swi  ng.Gr  oupLay     out.A     lignment.LEADING)
            .addGroup(layout.crea  teSequentialGroup()
                   .addContainerGap(  )
                .addComponent(jPanelBase, javax.swing.  Gro  upLa  yout.D EFAULT_SIZE,    javax.swing.GroupLayout.DEFAULT_SIZE, Sho          rt.MAX_VALUE)
                .addContainerGap())   
            );
            layou t.setVe rticalGroup(
            layout.    createPa rallelGroup(javax.swing.GroupL     ayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup(     )
                .addContainerGap(   )
                   .addComp  onent(jPanelBase, javax.swing.GroupL  ayout.    PREFE  RRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,     javax.swing.GroupL    ayout      .PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayo        ut.DEFAULT_SIZE, Short.M   AX_VALU   E))
             )     ;

        pack(   );
     }// </editor-fold>//GEN-END:initComponents

    private void j  ButtonCancelActionPerformed(java.awt.     event.Action   Event evt) {//GEN-FIRST:event_jButtonCa  ncelActionPerformed  
        // TOD O add your handling code her     e:
        if (jRadioButtonRe   tail.isSelected()) {
            JOptionPane  .showMessageDialog(rootPane, jRadioButtonRetail.ge   t   Text());
            }
    
        this .dispose();

         }//GEN-LAST:event_jButtonCancelActionPerfo   rmed

    /**
     * @param args the command line a rguments
     */
    public static void main(String   args[])   {
        /* Set the Nimbus look     and         feel */
        // <editor-fold defaultstate="collapsed" desc=" L ook and  feel setti    ng code   (optional) ">
        /* If Nimbus (i    ntroduced in Java SE 6)     is not available, stay with the default look and feel.
         * For details see ht   tp://download.oracle.com/javase/tutorial/uis   wing/lookandfeel/plaf.html
         */
          tr      y {
            for (javax.swing.UIMana  g    er.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFe   els()) {
                if  ("Nimbus".equal    s(info.ge   tName())) {
                        javax.swing.UIManager.setLookAn       dFeel(info.getClassName());
                          break;
                  }
            }
              } catch (ClassNotFoundException ex) {
            java.      util.logging.   Logger.getLogger(customerInforma    tionEn    tryForm.cla              ss.getName()).l   og(ja   va.util.logging.Le  vel.SEVERE,    null, ex);
        } catch (InstantiationException ex) {
             java.util.  logging.Logger.getL    ogger(customer       InformationE    ntryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (IllegalAccessException ex) {
            java.util.logg    ing.Logger.getLogger(customerInformationEntryForm.class.getName()).log(java.util.loggin     g.L  evel.SEVERE, null, e   x);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                   java.util.logging.Logger.getLogger(customerInformationEntryForm.class.getName() ).log(java.util.logging.Level.SEVERE, null, ex);   
        }
        //</e     ditor   -fold>

        /* Cr    eate and display the form */
        java.awt.EventQueue.invoke L     ater(new Runnable() {
            public void run() {
                 new customerInformationEntryForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:   variables
    private javax.swing.Butto nGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton     jButtonSave;
    priv  ate javax.swing.JComboBox jComboBoxCity;
    private javax.swing.JComboBox jComboBoxCountry;
    private javax.swing.JComboBox jComboBoxState;
    private javax.swing.JComboBox jComboBoxTownship;
    private javax   .swing.JLabel jLabelAddress;
    private javax.s  wing.JLabel jLabelCity;
    private javax.swing.JLabel jLabelCountry;
    private javax.swing.JLabel jLabelCustomerName;
      private javax.swing.JLabel jLabelFax;
    private javax.swing.JLabel jLabelMail;
    private javax.swing.JLa bel jLabelOutletName;
    private javax.swing.JLabel jLabelPhone;
     private javax.swing.JLabel jLabelState;
    private javax.swing.JLabel jLabelTsp;
    private javax.swing.JPanel jPanelBase;
    private javax.swing.JPanel jPanelCustomerT   ype;
    private javax.swing.JRadioButton jRadioButtonDealer;
    private javax.swin    g.JRadioButton jRadioButtonMT;
    private j avax.swing.JRadioButton jRadioButtonPTR;
    private javax.swing.JRadioButton jRadioButtonPTROutlet;
    private javax.swing.JRadioButton jRadioButtonRetail;
    private javax.swing.JRadioButton jRadioButtonWholeslae;
         private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaAddress;
    private javax.swing.JTextField jTextFieldCustomerName;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldFax;
    private javax.swing.JTextField jTextFieldOutletName;
    private javax.swing.JTextField jTextFieldPhone;
    // End of variables declaration//GEN-END:variables
}

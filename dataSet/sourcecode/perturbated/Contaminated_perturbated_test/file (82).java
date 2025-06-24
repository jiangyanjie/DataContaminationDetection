/*
   *  To change this template,      choose Tools | Templ ates
 *   and    open the template in the editor.
 */
package infogym;

i    mport DBHelper.CustomerDBHelper;
import DBHelper.MarketDBHelper;
import DBHelper.MemDBHelper;
i   mport java.sql.Timestamp;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
i   mport javax.swing.JFrame;
i   mport model.Customer;
import model.MarketChannel;
import model.Membership;

/**
 *
 * @author Administrator
 */
public  class Cust  omerForm exten   ds   ja            vax.swing.JF  ram         e {

    /**  
     * Creat  es ne   w    form   Cuto       merF   orm
     */
               public Cust   omerForm() {
               ini   tCompon   ents();
                   M   e   mbership[]       me  m         Arr = new MemDBHelper().getMemberships();     
                  MarketCha     nnel marketArr[] = new M             a    rket DBHelpe   r().getM    arketChannels();        
                         jComboBox1.setModel(         new jav  ax.swing.DefaultComboBoxModel(memArr ));
          jComboBox2.set      Model(new Defaul tComboBoxModel(m ar ketA   rr));
          setDefau       ltClos eOperation(JF r         am    e.DISPOSE_ON_CLOSE);
    }

    /**
      * Thi   s me         thod is called    from wi  t      hin th     e constructor to initialize the form.
     * WARNING: D    o NOT mo     dify this co      de. The content of this metho  d      is    always
     * reg            e    nerated by the F orm Editor .
          */
            @Suppres    sW       a     rni       ngs("unchecked")  
    // <edito  r-fold defaultstate="collapsed"    desc="Generated Code">//GEN   -BEGIN:ini    tC     o     mp  onent    s
                privat e vo id initComponents() {

          jLabel1 = new j  avax.             swing.JLabel()       ;
        jLabel2 =       n ew javax.swing.JLabel();
           jL    abel3 = new   java x.swing.JLabel();
            jLabel4 = ne     w javax.s    wing.JLabel();
               jLabel5 = new javax.swing.  JLabel(      );
          jLabel6 = new ja   va   x.swing.JL  ab        el();
            jComboBox1 = new j     av      ax.swing.JCombo   Box();
        jButton1        = new           javax.   swing.JB utton();
        jButton2 =   new javax.swing.JBu      tton();
        jLabel  13 = n    ew javax.swing.  JLabe    l(); 
           jText     Fiel  d10 = n               ew java   x.swing.JT      ext Fi  el d                     ();              
           jTextField11 = new javax.swing.JTextField();
           jTextFie     ld12 = new javax.swing      .JTe   xtField();
          jTextField13 =    ne   w          j          avax.swi ng.JTextFi    eld (); 
              jTextFie ld15 = new javax.swing.JTextField();
             jTextField16 = new javax.swing  .JTe   xt   Field();
        j     TextField17 = n ew javax.        swi  ng  .JTextField();
        j  L            abel20 =         n  ew javax.swing.JLabel();
        jComboBo   x2 = new      javax.s   wing.JComboBox();
        jLabel7 = n  ew    j  avax.swing.JLabel();
            j   La  b        el8 = new javax.swi  ng.JL  abel()   ;
                 jTextField18 = new javax.swing.JTextField             ();
           jTextField19 = new javax.swing.JTextF            ield   () ;
        jTe        xtFi eld   20 = new javax.swing.JTex   t   Field();     
                jLab       el21 = ne  w ja                                 vax.swing.JLabel();
        jLabel9 =       new jav     ax.sw    ing.JL   abel    ();
           jRadioB          utton1 = new javax .s          wing.J Ra d    io   Button();
             jRadioButton2 = new javax.swing.JRadioButto      n();
            jLabel10   =    new javax.swi  ng.JLabel();
            jRadioButton3 =    new javax. swing.JRadioBut  t     on();
                     jRa   dioButton4 = new javax.swing.JRadioB     utton  ();
           jLabel1 1 = new javax.swin    g.JLabel();
        jTextFie ld1 = new    javax.swing.JTextField();
        jLabe   l12 = new javax     .swing.JLabel();
                     jLa b el14 =    new javax.swing.JLabel(  );
            jLabe l15 = new javax.swing.JLabel();
         jScrollP    ane1 = new javax.swing.JScrollPane      ();    
        jTextArea1 = new javax.swin        g.JTextAre   a();
            jLabel16 = new  javax.swing    .JLabel   ();
                           jLabel17 =       new java      x.s      wing.JLabel();        

               set Defau   ltCloseOperati on (javax.swing.Wi ndowConstants.EXIT_ON_CLOSE);      

           jLabel1    .setText("First Name");

                 jLabel  2  .setText     ("Da  te Of Birth");

            jLabel3.se  tText("Addre ss"     );

        jLabel4.setText("Landnin  e Number");

             jLab  e   l5.setTex  t          ("Mobile Num     b   er");

                 j     Label6.    setT ext("Me   mbership");

                      jC   o mboBox1.set     Mod       el(new  javax.sw ing.DefaultComboBoxModel(new    St          ring[] { "Month  ly", "Quaterly", "Half Y       ear    ly  ", "Three Qua     terly", "   Y  earl   y"    }     ));

               jButton1.setText("ADD CUSTO      ME   R");
                jButton        1.addA   ctionListener(n   e   w java.awt.eve      nt   .ActionL  ist          en       er() {
                     publ       ic voi     d actionPerfo    rm      ed(j  ava.awt.event.ActionEve     nt evt) {
                       j   Butto   n1ActionPerf      ormed(e      vt    );
               }
                });

                 jButton2.s       etText("RESET    ALL FIE       LDS");
        jBut    ton2.  addAct  ion   Listener(new java.awt.event.     ActionList     e  ner() {
             pub  lic void acti                       onPerfo rmed(java. awt      .event.ActionE    vent e   vt) {  
                  jButton2    A      ctionPerfo   rmed(evt);
                             }
                });

                jL    a  bel13.setText("Last Name");

               jLabel20.set  Text("      Dat      e")      ;

         jLabel7.s        etText("Marketing Cha     nn      el"     );

           jLabel8.setText("Date o   f joinin  g")  ;

            jLabel21.setText("Date "  );       
  
        jLabel9.setText("     Ac       ti    v                e Membe  r");

        jRadio         Button    1.setText("yes"    );
         jRadioButton1.addActionListener(new java.awt.event.ActionLis      tener()     {
            p  ub   lic void actio     nP  er  formed   (java.awt  .even     t.ActionEve  nt e     vt) {
                jRadioButto   n1ActionP   erforme  d(evt);
                     }
          });    

          jRadioBut              ton2.s     etText("     no");
               jRadioBut ton2.     addAct    ion  Listener(new java                      .   awt.event    .ActionL  istener() {
                          publ   ic              void ac     tionPerfor  med(java.a     wt.event.ActionEv     e       nt      e              vt) {
                     jRad   ioButton  2A     ct      ionPerformed(evt);  
                   }
           });
 
        jLa     bel10.s      etText("Gender   "     );
      
                   j          R    a  dioButton3.   setText("Male");
                    j    Rad     ioB            utton3.addActionList        e ner(ne  w java.awt.e v ent.ActionListen  er()  {
                 public  vo id a        ctionPerformed(java.awt.event.Actio    nEvent evt) {
                           j  Rad  ioButt           on3Actio  nPerf    orme          d(evt   );
                                               }
                  })     ;

          jRadioBu    tto       n4.se            tText("Female   ")    ;
             jRad ioBu   tton4.addActionList ener(new    java.awt  .          e      vent.ActionListener()  {       
                public   void actionPerfor     med     (  java.awt.event      .A  ct   ionEven    t evt) {
                          jRadioBut  t on   4Actio nPe  rform    ed(evt);
                                         }
             });
   
        jLabel11.setT       ext("                 Ema  il I D");
   
           jTex     t   Fie    ld1.           a        ddAction  Lis tener(new jav                     a   .    awt.event.    Acti   onLis   t              ener() {
               pub   lic void    a   ctionPer          formed(   j av   a.awt.even   t.ActionEv   en                  t  evt) {
                           jText    Fi    eld1ActionPer       formed(evt);
                 }
          });

        jLabel1    2.setFont(n       e          w   java.awt.F ont("Ubun     tu", 1,    18));     // NOI18N     
                                            jLa bel12.setText("Customer    Form");

             jLabel14.s      et   Te         xt("Month");

          jLabel15.setText("Year"); 

         jText Area1                  .s    etC olumns(20);
                jTextArea1.setRow            s(5)    ;
                  j  ScrollPane1.setViewportView(jT extArea1);

            jLab  el16.set        Tex          t("Month   ")        ;

                                 jLa           b  el          17.s     e  t     Tex        t(     "Year");   

             javax.swin       g.GroupLay out  layou t  =   new javax.   swing.G           roupLay                       out( g  e tCont  en    tPane()); 
          getC                   o       nte   ntP   ane(      ).setLayou    t(layout);
                  layout   .se       tHorizont alGr  oup  (
                              layout.createParallelGroup  (     ja    v      ax.swi     ng.GroupLa    yout. Alig  nment.L EADI  NG      )  
                       .a  ddGroup(l   ay ou   t     .createSequenti       alG        roup() 
                           .addGr       oup(l       ayou t.c    re         a    teParalle     l    G         roup(j     a        vax     .sw    ing.Grou pLayo     u  t.A            li     gn me    nt.L  EADING)
                                                         .ad     dG         r           oup(la         y       out.cr       e        ateS                    e      quentialG     roup(  )
                                                               .addGap(409   , 40           9         , 4    0    9    )
                                                  .ad   dC               o        m ponen  t(  jLa      bel  1     2))
                               .add G    roup  (l   ayo     ut.crea  teS  equen    t ialGroup   (    )
                                                                           .addGap(2  2   , 2    2,    22)
                                                      .ad    d  Group(l    ayout          .createPar      allel       Group(javax.swi    ng.Gr   oup            Layout .Alignment.TRAILING)
                                              .addComponent(         jLabe    l11)
                                                 .addComponent(   jLabel13)
                                           .a   ddComponent(    jLabel1)
                                                                .addCompo         nen        t(jLabe  l2)
                                                       .    addCompone   nt(jLab el10)
                                          .a     ddComp       onent(jLabel8)
                                            .addCo    mponent(jLabel9   ))
                                 .add   Ga     p  (         18,          18, 18)
                                        .addGr      o  up(         layout.crea         te                     P        ara    l            le l          Group(javax.sw     ing.Gro        up     Lay                     o      u   t   .A  l  ignment.LEADING,     fals e)
                                       .             addG           roup(lay           out.crea     teSequ   enti     al       G   rou      p() 
                                                                    .ad          dCompo   n  ent(j       RadioBut   to  n1)
                                                      .addGap(18,    18   , 18)
                                                              .addComponent(jRadioButt  o            n2))
                                                        .ad dCompone    n  t(j            TextFi  eld11     )
                                             .add                 C       ompon ent(jTextField10)
                                                          .addC     o mpone   nt(j           Text  Fie     ld  1)
                                             .addGroup(layout.c rea        teSe  q  u   ent ialGr   ou       p()
                                                                .addCompon     ent(     j Text   Field              2   0,    javax.swin           g.Gro   upLay                out    .P         REFERR ED    _SIZE,          52,    java     x.s     wing. Grou        pLay   out        .PRE F   E   R  RED_SIZE)
                                               .             a ddGap(2 , 2, 2)
                                                    .addComp onent(jLa  b   el21)
                                           .ad   d    Gap (4, 4, 4)
                                                     .addComponent(jTextField19,           java  x.swing.Group L   ayout.PR EFERRED_S  IZE,       53, javax            .swin  g. Grou   pLayout.PREFERRED_SI   ZE)
                                                        .a    ddGap(    1, 1, 1)
                                                                  .  a  ddCompon   ent(jLab     el16)
                                                       .a    ddGap(2, 2, 2)
                                                  .addCo  mp    onent(j        TextFi   eld1   8, java   x.swing.Gr             o  upLay          out.PREFERR   ED_SIZE,         4     9,    java  x.swing.G   r  o    up    La   y       ou  t.            PREFERR              ED_SI     ZE)
                                                        .addPre     fer re               dGap(javax.sw  in      g.L a    youtStyle.Compone                  ntPlace ment. REL   A TED)          
                                                     .a   ddCompo          ne nt(jLa   bel17)  )
                                         .a  ddGr      oup(layout   .crea  teSe   qu  entialGroup ()
                                              . a     dd   Grou     p(layout.createParallelGro up(javax.swing     .GroupL     ay out.Align ment.LE ADING)      
                                                               .add   Group       (layout.create SequentialGroup()     
                                                                        .addComp    onent(jText    Field17,      javax.swin       g                    .GroupLayout.   PREFERR          ED_SIZE,     52, javax.swing.GroupLa    yo   ut.PREFERRED_SIZE)
                                                                         .addGap(4,            4, 4)
                                                                      .addCompon ent(jLabel20)
                                                                        .a   d dGap(3, 3,    3)  
                                                                 .ad   dCom    po            nent(jTextField13 ,       javax.swing.GroupLayout.    P        REFERR  ED     _SIZE, 49, javax.s  win       g .GroupL  a     yout.PREFERRE    D_S  IZE                   )
                                                              .a    ddGap(4,      4, 4)
                                                                               .addComponent(jL     a          be                   l14))
                                                                             .addGroup(layout.c reateSequentialGr     o  up()
                                                  .add  Component(jRadioButton 3)  
                                                      .a     ddGa   p(3,    3,  3)    
                                                      .addComponent(   jRad ioBu    tton4)))
                                             .     addGap(2, 2, 2)
                                            .add  Component(jTe  xtFiel   d12  ,           javax.s   wi  n   g.Grou     p           Layout.P   REFER    RED  _SIZE,        53, javax.swin   g.Gro   upLayout.P         REFER   RE D_SI  ZE)
                                             .a ddPreferredGap (javax.swing.Layo    utStyle.ComponentPlace ment.RELATED, javax. sw  ing.      G   roupLayou  t.DEFAU LT_SIZE, Sho   rt.MAX_VAL UE) 
                                                                  .addCompon   e  n   t(j   Label15)))
                                          .                  addG    roup(layout   .createParallelGr  o     up(javax.swing. GroupL  ayou  t. Ali gnmen    t.TRAILING)
                                        .addGroup    (layout. createPar   allel     Group(javax.s  wing.Grou pLayo  u t.A  lig    nm     ent.TRAI  LING        )
                                              .addGroup(javax.swin g  .Group             Layou t.Al  ignm e   nt   .    LEAD   ING, l          ayo u   t    .   c               reateSequ      entialGroup()
                                                .    add    Gap     (108,        108,     108)
                                            .add  Group(layo   ut.cr         eateParallel Gr      ou   p(javax.swi         n     g.GroupLay       o  ut.Al  ignment.TRAILING)
                                                                             .addComponent( jL   abel4)
                                                                          .              addC   ompone    n                   t(jLabe    l5)))
                                                 .addCom   p    on  ent(jLabe  l6)
                                                .add  C    ompone    nt(jLabel7))    
                             .addCo     mponent(jLabel  3)) 
                                                      .addGap(28, 28,    28)
                                        .addGroup(layou    t.c    reateParal lelGroup(j  avax.swing.GroupLayout.Alignment.LEADING, false)
                                                   .   addCom   ponent(jS  crollPane1, javax.swing     .GroupLa  yout.DEFAU   LT    _SIZE, 285, Shor      t.MAX_VALUE)
                             .ad     dCom   ponent(jTextField15)
                                           .ad         dComponent(j       Text        F    ield16)
                                            .a  ddCo   mponent(jCo   mboBox1    ,     0, javax.       swin    g.GroupLayout.DEFAULT_S  IZE, Short.MAX_VALUE)
                                 .a ddComponent(jComboBox2, 0, ja     vax.s    wing.GroupLa  y       o   ut        .DEFAULT_SIZE,      Short.MAX_VA   L        UE  ))))
                   .addC           on    tainerGap(30, Sho     rt.MAX_VAL    UE ))
            .addGro         up(javax.s       wing.GroupLa  yout.Alignm    ent.TRAILING,    layout.createS       equen      tialGro                       u p()
                .addGap(  0, 0,           Short.MAX_VALUE)
                 .ad   dC o mponent(jButt on1)
                       .   a    ddGap(18, 18, 18)
                                   .add       C   omponent(jButto   n    2)
                                           .a    ddG              ap(36  0, 360, 360))
        );
                    layout.setVerticalGroup(
                        layout .cre at  e        ParallelGroup(j         ava   x.  swi    ng.G  roupL   ayout    .Alig  n    ment   .LEADING)
                      .addGroup(lay          out.createSequentialGroup(  )
                .add   Co  ntai               nerGap()    
                                    .addC   o      m pone     nt(jLab      el12)
                .addGap(27,    27, 27   )        
                        .ad  dGroup  (layout.createParal    lelGroup(jav ax.swing.GroupLa           yo    ut.Alignment.LEA D         ING)
                               .addGroup(layout.crea  teSe    quentialG   r    oup()
                                           .addGro  up(layout .createPar allelGroup(javax   .swin   g.GroupL        ayout    .    Alignment    .BASELI      NE)
                                                      .  addCo mp     on     ent(jLabe      l4, javax.swing.G   rou   p     Layout.   DEFAULT_SIZE, javax.sw  ing.GroupLayout.DEFAU LT_SIZ  E,   S  hort.MAX_VA  LUE       )
                                      .a   d dC     omp    onent(jText    Fiel   d15, javax.s         wi  ng.Grou pLa     yout.PREFER   RED_SIZE, jav   ax.swing.Gr   oupLayout.DEFAUL    T_SIZE,   jav     ax.sw    ing.G    ro upLayout.PR  EFERRED_SIZE)      )
                        .add    Pre          ferr   e     d   Ga    p(   javax.swing.LayoutS           tyle.ComponentPlacement.  U    NRELATED)
                          .a              ddGroup(layou      t.create      ParallelGroup(javax.swing.    Grou     p         Layout.Alignment.BASELIN  E)
                                         .a  ddComponent(j  Lab           el5, ja       vax.s    win   g.Gr        o upLayout.DE   FAULT_SIZE, javax.swing.   G  roupL   a   you  t.DEFAULT_S  IZE, Sho     rt.MAX_VALUE)
                                      .addCo   mpo   nent    (jText             Field16, j   a    va x.swing.   Gro    upLayout.P       REFERRED_SIZE,    javax.swin g.GroupLa    yo  ut.DEFAULT        _SIZE,     java    x.swing.GroupLayout.PREFERRED_SIZE)    )    
                           .add                 PreferredGap(  javax.swing.LayoutStyle.Componen     tP  lacem   e  nt.  UNRELA       TED)
                                            .ad dGroup(lay    out.cr   eateParallelGroup(javax.s    wing.GroupL   ay   out .    Alignment.BA   SELIN  E)
                                      .add   Component                   (jLab el6, javax.swing.GroupLayout.DE        FAULT_SIZ E, javax.swing.GroupL ayout.DEFAULT_SIZE, S    hort.MA   X_      VALUE)
                                      .addC      omp     onent(jCom       b       oBox1,        ja    vax   .sw   ing.GroupLayout. PREFERRED_SIZE, javax.sw       ing.GroupLayout  .  DEFAULT_SIZE, javax    .swing.         GroupL   ayout.PREFERRED_SIZE)       )
                               .addPreferredGap(javax.swing.LayoutStyle.     ComponentPlacement.UNRE  LATED)
                                          .        addGroup(layout        . createPara      llelGrou   p(javax.swing.G    roupLayout.Alignment.BASELINE)
                                       .add C   omponent(jComboBox2     ,            javax.swin   g. G roupLa   yout.PREFER RED_SIZE, javax   .swing.    GroupLayout         .  DEFAULT_SIZE,      javax.sw      ing.GroupLayout.     PR EFERRED   _SIZE)
                                                   .a    dd  Compo   nent(   jLabel7, java     x.  swing.GroupL           ayout        .DEFAULT_SIZE, j    av     ax.sw                  ing.GroupLa  yout   .DEFAUL  T_SIZE,         Short.MAX_VALUE))
                                         .addGa     p(18, 18, 18)
                            .addGroup(layout  .   cr      eatePa    rall     elGro           u p  (jav      ax.swing.Gr   oupLayou  t         .Alignment.LE ADIN G)
                                     .a     ddGroup(layou  t.c         reateSequentialG     roup()
                                                                    .addComponent   (jLabel3,  ja     vax.swing.GroupLayout.DE   FAULT_SIZE, ja   vax.swing.Gro     upLayout.  DE  FAULT_SIZ       E, Sh ort.M AX_VAL   UE)
                                            .addGap  (74, 74    , 74))
                                         .add    Co     mp    onent(jScrollPane1))) 
                         .addGroup(   lay     ou   t  .cre       ateSequen     tialGrou      p()
                                            .addGro            up(layout.create    ParallelGro       u           p(javax .s   w       ing      .Grou    pLayout.Align   ment.BASE                    LIN      E)
                                                .add Componen   t(jLabel1, javax.sw    ing.G  roupLa  y     out.DEFAULT_SIZE    , ja vax.swi ng.GroupLayo   ut.DEFAULT_SI  ZE, Sh    ort .MAX_VALUE)
                             .addComponent(jTextFi       eld11, jav       ax.swing.    Gr  oupLayout.      P  REFERRED_SIZE,   javax.swin     g.G      roupLayout.DEFAULT_SIZ   E,   javax.swing     .GroupLayout.PREF    ER    RE     D_SIZE))        
                              .addPrefe    rred        Ga     p(javax.swing.LayoutS    tyle .Compon  entPlac   ement.U    NRE  LA  TED)
                                       .addG     roup  (    la  you   t.create  Para ll    elGroup  (javax      .swing.G  roupLayo         ut.Alignment  .BASELIN       E)
                                             .addCompone     nt(j  L       ab    el13,  javax.swing.  GroupLayout.D    EF AULT_SIZE,  javax.s wing .GroupLay   out.DEFAULT_SIZE,   Short.M AX_VALUE)
                                         .addCo  mponent(jTextField10, javax.swing.GroupLayo ut    .    PR   E   FERRED_SIZE, javax.swing       .Gr                 oupLayout.DEFAULT_SIZE, javax.s  wing.G  roupLayout.PREFE RRED_SIZE)   )
                                 .addPreferredGa  p(javax.swing.LayoutStyle.Co  mponentPla  cement.UNRELATED)
                               .addGrou     p(layo   ut.createParallelG roup    (javax . swing.Grou     pLayo   ut.Alignm    ent.BASELINE)
                                .a  ddC  omponent(jText  Field17, ja      vax.swing    .   Gr oup     Layo  u   t.PREFERR       ED_SIZ     E, ja      vax.       swi      ng   .GroupLay    out.D      EFAULT_SIZE, java  x.s  wing.GroupLayout.PREFERRED_SIZE       )
                                        .addCompo nent(jTextField13  , javax.swi    ng.     GroupLayo       ut.PRE   FER     RED_SIZ  E, javax.swing.GroupL        a   yout.    D EFAULT_   SIZE, javax. s    wing.GroupLayo  ut.PREFERRED             _SI  ZE)
                                              .addComponent(jTex    tFie ld12, javax .s   wing         .Gr  oupLayo    ut.PRE  FERR      ED_SI      ZE, javax.swing. GroupLayout.DEFAULT_SIZE, javax.    swing.Gro       upLayout.PREF      ERRED_SIZE)
                                 .addCompon          ent(jLabel20)
                                                              .addCo      mponent(jLab    el14)
                                                  .addC        omponent(jLabel15)
                                                      . addComponent(jLabel2))
                               .addP referredGap(javax.swing.LayoutStyle.C ompon      en    tP            lacement.   UNRELATED                  )
                                     .addGroup(layou       t.c  reatePara    lle    lGroup(javax.sw   ing.    GroupLay  o     ut.Align      ment.BASELINE)
                                   .add   Com  ponent(jRadi    oButton4     )
                                            .addComponent(    jRadioButton3)   
                                       .addC  omponent(jL      abel10, javax .swing.Gro                  upLayout.DE  FAULT_    SIZE,     javax.swing.GroupLayou    t.DEFAULT_S  IZ E, Sh        ort.MA X_   VALUE))
                         .addGap(1,   1, 1)
                                      .a    ddGroup(l       ayout.createPa       ralle lG      rou     p(javax.swing      .Grou  pL     ayou     t.Alignment     .LEADI   NG  )
                                .ad    dGrou  p(layo   ut         .createSequentialGroup()
                                      .a   ddGap(16, 16, 16    )       
                                                 .addComponent(j    L   a  bel8)
                                      .a    d  dGap(15, 15, 1  5)
                                       .addG    ro  up(  layout.crea teParal  lelGroup  (javax.swin g.GroupLayout.A    lignm    ent.BASELINE )   
                                                    .addComponent(jLabel9)
                                                  .addComponen t(j RadioButton          1       )
                                       .addCo     mpon     ent(jRadioBut to  n2))
                                .ad dPreferredGap(jav ax. swing.LayoutSty    l    e.Co   mp    onentPlace      ment.UNRE  LAT ED)
                                 .addGroup(layout.cre ateParall   elGro   up(javax.swi    ng.Grou   pLayout.Alignmen    t.BASELINE)
                                                        .addCom     ponent(    jLabel11, javax.swing.GroupLa     yout.DEFAULT_SI  ZE, javax.swing.GroupLayout.DEFAU      LT_SIZE  , Short.MAX_VALUE)
                                                    .addComponent(jTe  xt   Field  1,    javax.swing.GroupLayo    ut.PR     EF ERRED_    SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swi  ng.GroupLa  yout.     PREFERRED_SI     ZE)))
                                       . addGro     up     (layout.cre   ateSequentialGroup()
                                      .addGap(9, 9, 9)
                                     .addGroup(layout.createPar    al      lelGro          up(java x.swing.GroupLayout .Alignment.B  A SELIN   E)
                                                      .addComponent(jTextFiel       d20, javax.swing.GroupLayout.PREFERR    ED_SIZE, javax.swing.Gro   upLayout  .DEFAU   LT_SIZE    , javax.sw            ing.GroupLayout.PR          EFERRE    D_SIZE)
                                                           .addComp onent(j Lab    el  2    1     )
                                                      .addC  ompon    ent(jTex        tF     ield19, javax.swing.Gro       upLayout.PREFERRED_SIZE, javax.swing.        GroupLayout.DEFAU LT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel16)
                                          .addC  o    mpon   ent(jTextField18, javax.swing.Group         Layout.PREFERRED_SIZE, j  avax.swing.Gr   oupLayout.DEFAU  LT_SIZE, javax.swing.GroupLayout.PR   E   FERRED_SIZE)
                                               .addComponent(jLabel17))
                                                 .addPrefe          rredGap(javax.swing.LayoutStyle.ComponentPlacement  .RELATED, 97, javax   .swing.G    roup L   ayout.PREFER RED_SIZE    )))))
                    .addG ap(18, 18, 18)
                           .addGroup(layout   .createPa               rallelGroup(javax.swing.GroupLayout .Al         ignmen          t.BASELINE)
                        .addCom    ponent(jButton1)
                        .addComponent(jB    u        tton2))
                .addGap(38, 38, 38))
           );

        p     ack();
    }// </editor-fold>/         /GEN-END:initCompone               nts

       
     private  void jButton1Act  ionP   er   formed(java.awt.e  vent.ActionEvent    evt) {//GEN-FIRST:      event_jButton1ActionPerformed

        //           TODO add your handlin  g code here:   
        if(validat() == false    )
        return;
        
        Customer cust = loadCu  stomer();
        CustomerDBHelpe r hel   per = new CustomerDBHelper();
        hel  per.saveCustomer(cust);

        setVisi    ble(fals     e);
        di spo      se();


    }//GEN-LAST:event   _jButton1ActionPerformed

    private void jR    adioB    utton1A         cti  onPerformed(java.awt.event   .Acti    onEvent evt ) {//GEN-FIRST:event_j    RadioButton1ActionPerfor    med
            // TODO add your handl ing c    ode       he    re:
    }//GEN-LAST    :ev ent_jRa    d    ioButton1Ac        tionPe    rformed

           private void  jRadi    oButton2ActionPerformed(java.awt.event.ActionEvent evt     ) {//GEN-FIRST:event_jRa   dio Button2   Act   io        nPer    form  ed
        // TODO add your handling cod    e here:
    }//GEN-    LA  ST:event   _jRadioButton2Action   Performed   

    private void     jRadioButton3ActionPer    formed(java.awt.event.Act  ionEvent evt) {//GE      N   -  FIRST:event_jRadioButt   on3ActionPerformed
        // TODO add your handl    ing code her e:
    }//GE     N-LAST:eve   nt_jRa    dioButt  on3A  ctionPerformed

      private void jRadioB   utton4ActionPerf   ormed(java.awt.even t.ActionEvent evt) {//GEN-FIRST:even t_jRadioButton4 ActionPerformed
        // TODO   add you   r handli ng code here:
      } //GEN-LA    ST:event_jRadioButton4Ac  tionPerformed

    privat   e void jTextField1Acti   onPe    rformed    (java.awt.event.ActionEvent ev         t) {//GEN-FIRST:event_jTextFiel   d1ActionPerformed
            // TODO add your handling code here:
    }//GE   N-LAST:event_jTextFiel  d1A         ctionPerf     ormed   

        p   rivate void jButton2Action  Performed(java.awt.event.Ac   tio          nEven  t evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TOD     O add   your handling code here:
    }//GEN-LAST:event_jButton    2     ActionP    erformed    

    private Customer l    oadCustomer(){
       
        Cu    stomer cust = new Customer();
          cust.setFirst_  name(jTextFi      eld11.getText());

        cust.setLast_name(jTextField10.ge    tText());
            cust.setAdd                   ress(jText    Area1.getText());
        
        int year = In  teger.pa rseInt(jTextField12.getText   ())-1900;
        int   month = Integer.   parseInt(jTextField13.getText())-1;
                int day = Integer.parseInt(j    TextField17.getText());
        cust.setDob(new    T imestamp(new Date(year, month, day).getT   ime()));
        cust.setDo  j(new Time   stamp(new    Date().getTime()));
        
        
        cust.setTel(jT        ext       Field15.getTe xt());
        cust.setMobile(jTextF     ield16.get    Text());
        Membership me       mber ship =  (Memb     ership    )jComboBo  x      1.getSelectedObjects()[0];
        
              cust.setMembership(membership);
        
        Mar     ketChannel mc     = (MarketChannel)jComboBox2.getSelected Objects()[0];
        
            cust.setMarketChannel(mc);
        
        String gend  er="";
        boolean active;
        if(jRadio  Button1.getSelectedObjects()!=null)
             active =true;
        else
        a    ctive=false;
        
        if(jRadioB       utton4.getSelectedObj   ec     t       s()!=n      ull)
        gender ="female";
        else
        gender="male";
        
        cust.setGender(gender);       
        cust.setActive(active);
        
        return cust;
    }
                  
    
    private boolean validat()
    {
        return true;
    }
        /**
     * @para   m args the command line arguments
     */
    public static void main(String args[]) {
      
          /*
         * Set the Nimbus l    oo k and feel
         */
        //<editor        -fold defaultstate="collapsed" desc  ="   Look and fee     l setting code (o   ptional) ">
                /*
             * If Nimbus    (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracl   e.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.   swing.UIManager.getI   nstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassN      ame());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
             java. util.logging.Logger.getLogger(Mai    nPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (Instantiat  ionException ex) {
            java.util.logging.L    ogger.g   etLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
             } catch (IllegalAccessExcepti   on ex) {
                        java.util.logging.Logger.getLogger(MainPage.class.g etName()).log(java.util.logging.Level.SEVERE, null, ex);
             } catc h (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.L             evel.SEVERE, null, ex);
        }
        //</editor-fol d>

        /*
         * Creat  e and display the form
         */

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                  new CustomerForm().setVisible(true);
            }
        });
    }
    // Vari    ables declaration - do not m      odify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBo     x jComboBox2;
    private javax   .swing.JLabel jLabel1;
    private javax.swing.JLabel jLabe   l10;
    private javax.swing.JLabel jLabel11;
       private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLa     bel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabe  l20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLa     bel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea   jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JT   extField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField20;
    // End of variables declaration//GEN-END:variables
}

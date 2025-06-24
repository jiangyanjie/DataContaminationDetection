package    gui;

imp    ort datalayers.DayDL;
import entities.Day;   
import sta  tic gui.GUI.NIL;
import java.awt.event.WindowAdapter;
imp ort java.awt.event.WindowEvent;
    import java.util.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import  java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;   
import sql.Connector;
import util.MesDial;
import util.StrUtil;

/**
 *   Day   Frame. CRUD for      Day Entity
  *
 * @author alexhughes
 * /
public class DayFrame exten   ds GUI {

                         private stat      ic boolean i nstanceAli  v   e = false;
    private Day d;
       private DayDL d   DL;
 
       /   * *
     * Creat   es new form Da   yFram  e
        */
       public DayFrame(GU  I aP Frame, Connector aConnector, int anID) {
        supe      r(aPFr  ame    , aCo  nn    ector, anI D); 
          in   stanceAlive = true;
        
                 i  n       itCompon   en  ts();
              th   is.   addWi   ndowLis          tener           (ne                        w W       in     dowAdapter() {
                         p  ub                lic v  oid  wi ndowClosing(  Wi  n        do  wEvent e                ) {
                       s    h u   tdow     n();
                      }
                           }            )       ;

           if (anI   D      != N    I L)   {
                    try {
                    i  d = anID;
                                     loadDay();
                } catch     (SQ     LEx    cep                  t    ion    ex) {
                       MesDi       al    .conE    r        ror(     this);
                Lo   gger.getLogge r(DayFrame.cla  ss.getNa  me()).log(L                    ev       e   l.SEVE   RE, null         ,     ex );
                          shut   down(      );
                         }
                       e            xisting    = tru     e;
        } else {
                      exi stin    g =  false      ;
                    s    tatusL.setText("New Day");          
        }
  
            s     uper.setFrameL         ocation         Center()    ;
          t  h  is.setVisibl e(t  r   ue);          
     }

    private bo   ole  an parseDay() {  
                  b  oolean    parsi     ngSu c      cessful =    tr    ue ;
        d = ne      w D   ay(    );
           
         if(existing) {
                    d.setDayID(id);
                   }
  
               try {
                d.setD  ate(Str    Util.d                ate    Pars   er(dateF.getText(  )))  ;
               } catch (E           xception ex    ) {
                     p     ars    in gSucce   ssful = false;
                MesDia  l.d  ateEr   ror(th  i   s      )   ;
               Logge                r.   getL     ogge  r(DayF   ra   me.class .getName( )).log(Le      vel.SEV          ERE, nu    ll, ex    );
              }

                               t  ry {
              d.s    etExpe    n   ses(Doub      le     .valueOf(ex pens esF.getText())     )    ;
        }      catch (Excepti     on x) {   
                             parsingSucce    ssful      = fa   lse;
            MesDial.dou       bleError(  this);
                                        Log         ger      .     getLogg     er(DayF    r    ame.class.g  etName()).log(L    evel.SEVERE, null,    x);
            }

           d.setSummary(summaryArea   .g etTe xt(          ));

            if (sexC h                k.isSele          c   ted())           {
                                  d.set  Se  x  (1);
                     }   els  e {
               d.   se    t     Sex(0   );   
            }

          if     (w   orkCh                  k.isSelected())  {
                        d.  setW  or        k(1);
              }           else {
                        d.s    etWor  k(0);
             }

           if (funC  hk.isSelecte    d())      {
            d. set  Fun(      1);    
           } els    e {
             d.setFun (0)           ;
                        }

                   if (s      pecialC  hk.isSelecte  d())  {
                         d    .setSpecia  l(1     );
            }    else {    
                d.setSpec    ial(0      );
            }          

        i    f (alcoholChk.   is S    ele   cted         (      )) {
                d.setA        lcohol(1);
          } else {
             d.setAlc  ohol(0);
         }

        if (pra cticeChk.   isSe       lected()) {  
                           d.setP           ractice(1);
                     } else {
                      d.s    etPractice(0   );
        }


              re   tur             n p          a             rsingSuccessf     ul;  
    }
   
    priva  te    void loadDay()    throws SQLExcepti            on {
           De  cimal  F  ormat decFormat = new DecimalF        or      ma    t("#.##" ); 
            SimpleDateFor mat d   ateFormat =   new Simpl   eDat    eFormat    ("dd  /MM/y        y  y y");   

             //creating     the    d ay     object    and in     serting       its id
            d =      n             ew D               ay();
           d.   setDayID   (id);
            dDL  = new DayD    L(c, d  );
  
        /  /fetching da y details
                d = (Day) dDL.fetchEntity();

               //s                  ettin   g val ue s on fields and      check boxes                
             dateF.setText(da          t  eF           ormat.format(d  .getD        ate()     ));
                                s     u      mmaryArea.s   et  T        ex   t  (d.getSummary   ()) ;  
        expensesF. s  etText(decFormat          .f      ormat(d.get    Exp     e  nses()));

             if (d.getSex() == 1)   {
                        sexChk   .      setSelected  (tr          ue);    
           }
 
        if (d    .get   Work() ==    1)  {
               workC         hk .setSel  e cted(true);
        }

          i     f (d.getFu   n()  == 1) {
                 fun Chk.setSele   cted(true);
        }

                     if (d.get   Special() ==      1) {
              s  pec ialChk.setSelect  ed(tru    e);
           }

          if (d.getAlc  ohol() == 1  ) {    
                 alcohol      Chk.set      Selecte   d(true)        ;
        }
    } 

    private void s    av           e() thr           ow      s     SQLExcept        io n {

        if    (parse                   D       ay()) {
            d    DL   = new DayDL(  c, d) ;
               if     (!exist          ing)    {
                                   id =      dDL.inse  rtEntit   y();
                   existing          = t rue;
             } else {
                        
                       dDL     .updateEntity();
                  }
                  MesD ial.saveSucce ss(this);
        }
    }

      public static boolean   isInstanc eAlive() {
           return ins    tanceAl  ive;
        }

    @Override
    protected void shutdo     wn()     {
           insta nceAlive = false;
                 super.shu tdown();
    }

            /**
     * T his m   ethod is     ca   l  led  from within t     he c     onstructor t   o initial ize the form.
     * WARNING:  D  o NOT       modify th     is code.   T    he          c          ontent of this m    ethod is alway    s
     * regenerate  d by the Fo   rm E     ditor.
     */
    @Suppr essWarnings("unchecked")
       //    <editor-fo  ld defaultsta   te=    "collapsed" desc=     "G   ener     at     ed Code">//GEN-BEGIN:in   itComponents
    private voi  d     initCom     ponents()   {

             jPanel2      = new ja    v          ax.swing.J    Panel();
        jL   abel3 = new javax.swing.JLabel(  );
        jLabel4    = new      javax.      swing.JLabe   l( );
        dateF = n   ew javax.s   wing.JTe   xtField();
        e     xpen        se    sF = n   ew    jav   a    x.swi      ng.JTextField();
                              j     ScrollPane2 = new j   avax.swing              .JScrollPan    e();
             summaryArea = new j    a   vax.s  wing.JTextArea();
                     sexChk = new        jav            ax.   swing.JCheckBox();
        workChk =  new javax.     sw    ing.JCheckBox(); 
             funChk =      new javax.sw      ing.JC  heckB   ox()     ;
         specialChk = n  e   w javax.swing.     JChec         kBox()    ;
        alcoholChk = new    javax         .    swing   .  JCheckBox();     
        practiceChk     = n       ew javax.swi        ng.JCh   eckBox();
           jPanel3 = new j      av  ax.swing.JPanel();
              okB tn = new javax.swing.JBut             ton(          );
               backBtn = new j avax.swin g.JButton();       
             jPanel4 = new jav    ax.swing.JPanel ();
        statusL =     new javax.    swing.J    Label();
               jPan      el   5     =  new javax.swi     ng.JPanel();
          jScrollPan   e3 = ne     w javax.swing.JScrollPane() ;
            jList1 = new javax.swing.JLis  t()   ;
        jButto   n1 = new javax.swing.JButt    on();
                                    jB    u     t                      ton2 = new java  x.swing  .J   B  utt    on();
            jButton3 = ne        w    javax.  swing.JButton();  
           j Button      7 = ne   w javax     .swin        g.JBut    t on ();
              jPanel6      = ne  w javax.swi  ng.JPa   nel();
        jScrol   lPane4 = new javax.s   w ing.JScrollPa     ne()          ;
            j   Li    st2        =      new j   av    ax.swing. JList()   ;
              jButton4  = new javax.swi   ng.JBut  ton()   ;
                   jBu      tton5 = new javax.swing.JButt   o         n();    
          jButton6 = new ja          vax.swin     g.JB utton();
               jBut   to      n8 = new javax.swing   .      JButt   on()        ;

        setD   e   fa ultC     loseOperat      ion  (javax   .           swing.WindowCon stant  s.DO_NOTHING_ON_CLOSE);
         setT       itle("Day"  );
        
        jP     anel2.s  e     tBorder(javax    .swing.   BorderFactory.createTitledBorder(   "Day"));

                  jLab            el3.s   etT      ext("Dat  e:"   );

             jLabel4.setText(    "Expenses:")          ;

          jScroll  Pane2.     setBord  er(javax  .swing.BorderFactory.cre    ateTi  tledB   order("Summary")    );

              su mmar   yArea.setCo lu          mns(20);  
                  summar            yArea.setLin        eW   rap(   tru     e);
                  summaryAre   a.setRows(5);
               summ   aryArea.setWrapSt    yleWord(true); 
        jScr    ollPane2.s      etViewportVi ew(su   mmaryArea);

          sex   C  hk.setText      (" Sex");
    
          workC    hk.setText("Work");

         funChk. setTex t    ("Fun");

                 spe    cialChk.setText("Specia   l   ");

                 alcohol   Chk.setText  ("A  lcohol");

                   p           ractice   Chk.setText("Practice");
   
              javax.swing.GroupLayou   t jP        anel2     Lay    out =        new           jav   ax.sw      ing     .Group      Layout(jPanel2)   ;
          jPa    nel2.setLayout(jPane  l2 Lay     out);
              jPa        nel2La                 yout     .setHo rizontal  Group(
                         jPane      l2Layout    .c  r  eateP     arallelGr ou     p(javax.swin   g     .GroupLayout.A         lignment.L   EADING   )
             .add    G  roup(jPan el2Layout. crea         teSe qu     entialGroup()
                              .addCon   tainerGap()
                     .a   ddGroup(jPa  ne    l2La yout.createParalle  lGroup    (ja   vax       .swin  g.GroupLa       you    t    .       Alignment.LEAD     ING)
                                   .a  ddGro  up(j     Panel2Layout.cr    eateSequ   e    nt       ialGroup()
                                 .addG  roup( jPa nel2Layout    .createP arallelG    roup(javax.sw       ing.Gr   oupLayout.Align me nt.TR    AILIN   G)
                                                                 .addComp        onen   t (jLabe            l4)
                                              .a   ddComp   one       nt(jLabel3))
                                               .addPreferred   Gap(javax.sw    ing.LayoutSt   yle.Co         mponentPla cement.      RE LA   TED)
                                                         .ad   dGr   oup(  jPanel2Layout.c    reateParallelGro up(javax.swing  .G  roupLayout.Al ignment.L EADING  )
                                                  .addComponent(dat  e F)    
                                              .addComponent(expensesF, javax.    swing    .Group  Lay     out.Ali         gnment.  TRA         ILING)  ))
                                        .addCompon ent     (j Scro   l   lP    ane2, ja vax.sw  ing.G               r      oup         Layou          t.DEF    AU   LT_SIZE, 296, Short.   MAX_VAL UE)
                                              . add     Group  (jPanel2L   a   yout.createSequentialG  roup()
                                        .       addGroup(jPanel2Layout.crea    teParallelGroup(j    av    ax.sw   ing.Gr         oupLayout.Alig  nment.   LEADING)
                                       .addComponent(funChk)
                                           .ad   dCompo   nent(spec          ial   Chk)
                                     .a dd        G  rou  p(jPanel2L  ayout.createSe  quentialG r      oup()
                                             .addG  rou     p(jPanel2L          ayout.createP arallelGroup  (javax.s     wing.GroupLayout.Alig    nment.L EADING )
                                      .addComponent(sexChk)
                                                 .add     Component   (workChk)       )
                                      .addGap(52, 52  , 5    2)
                                                     .ad d   Group     (j Panel2Layout.createParallelGr     oup(  javax.swing.Grou   pLayout.Alignment.LEAD     ING)
                                                             .   addCo  mponent            (practiceC    hk)          
                                                            .addCompone   nt(alcoholCh   k)  )))
                                  .addGap(0, 0, Short.MAX_VALUE)))
                     .ad   dContainerGap())
           )  ;
                     jPane  l2Layou    t.setVerticalGroup(
            jPanel2Layout.createParallelGr        oup(javax.swi   ng    . Group Layout.Alignment.LEADING)
                .ad   dGroup      (j     Panel2Layout.creat   eSeque     ntialGroup()    
                   .addContainerGap()    
                .a         ddGrou     p(j     Panel2Layo        ut.createP     arallelGroup(ja     vax.s wi         ng.Group      La   yout.Alignment.BASELI     NE)
                               .add Comp onen  t    (jLab         el3)
                               .addComponen t(da   teF, jav   ax.sw ing.GroupLayout.PRE   FERRED_    SI   ZE, ja     vax. swing.GroupLayout   .DEFAULT_SIZE     ,            javax.s wing.GroupLayou       t.PREFERRED_SIZE))
                  .add    PreferredGap(jav  ax  .s    wi  ng.Layou    tSt    yle.Co   mpon  entPlacement.U    NRE      LATED)
                .        addGroup          (jPa    nel2Layo    ut.c    reate     ParallelGro    up(j  avax.swin  g  .GroupLa    yout.Alignm en   t .    B ASELINE)
                            .addComponent(jL     abel4)
                             .ad d C    om   ponent(expensesF      , java               x.swing.GroupLayout .PREFER RED  _SIZE, javax.    s    wing.         GroupL   a      yout.   DEFAULT_S            IZE, javax.swing.GroupLayo    ut.PREFERRED_SIZE))
                    .addGap  (18, 18, 18)
                    .addGroup(jPanel2Layout.c  reat e       ParallelGroup(javax.swing.GroupLayout.Alig      nment.BASELI  NE) 
                         .        a  ddCo  m   p    onent(sexC  hk)
                                    .add  Component(alc oholChk))
                                 .addPrefe   rredG   ap(javax.    s    wing.L   ay  outStyle.ComponentPla cem  en      t.UNRELATE    D)            
                      .addGroup(jPanel2L       ayout.createParallelGr                                 o   up(javax.swing.Gr                    oupL   a  yout.Alignment.BASELINE)
                       .addCo      mp  onent(wo    rkChk)
                                   .addCompon  ent(practi ceChk))
                .addP    referred  Gap(javax.s        wing.     L     ayoutStyle.Componen    tPlace    ment.U     N     RELATED)
                       .addC   omponent(funChk)
                .addPreferred   Gap(java      x. swing.LayoutStyle.C       omponentPlacement.UNRELATED)
                   .addC       omponent(specialChk)
                   .addGap(17,    17,   17    )
                .addComponent   (jScrollPane2,      javax.swing .Gr   ou        p  Layout.DEF  AULT_  SIZ  E, 162, Sh    ort.MAX_VALUE)    
                 .addCont    ainerGap())
          );

        jP     anel3.setB   order(javax.swing.  BorderFactor y   .createEtched   Bord   er()   );

                  ok    Btn.setTe   xt   ("OK");
        okBtn.addA    cti onLi sten    er(ne w jav    a.awt.event.ActionList    ener() {
                public void actionPerformed    (java.aw   t.event.Ac           tionEv     ent evt) {
                                    okBtnAct   ionPerformed(e    vt);
            }
        });

           backBtn .setText("<Back");
        back   Bt   n.addAc   t ionLis  tener(new java.awt.event.ActionListener () {
            pu blic      void acti          onPerforme       d(java.awt.eve  nt    .Acti  onEv        ent evt) {
                   backBtnActi  onPerformed(ev  t);
              }
               } );

         javax   .swing.G roupLay     o              ut jP  anel3Layo         ut = new javax.swing.G  roupLayout(jPanel3)  ;
        jPanel3.setLayout(jPanel       3L     a    yout);
                    jPanel3Lay      out.s                et   HorizontalGroup(
                  jPane   l3Layout.createPara   ll  elGroup(ja     vax.swing.GroupLayout.Al  i      gnme   nt   .LEADIN     G)
             .addGroup(javax.    swing.G    r    oupLayout.Alignment.TRAILING, jPa   nel3Layout.createSequ entialGroup()
                     . addCont           ainerGap()
                     .addCom  ponent      (ba   ckBtn  , javax.sw ing.GroupLay   out.PRE      FERRED_SIZE,       97, javax.swing.GroupLayout.PREF    ERR    ED_SIZE)
                .addPreferred    Gap(j   avax.swing  .LayoutStyle.ComponentPlac        ement.RELATED, javax.swing.GroupLay      o  u     t.DEFAU LT_SIZE, Short.MAX_VALU                  E)
                  .addComp  onent(okBtn, ja  vax.swin  g.GroupLayout.PREFERRED_SI ZE, 106  , ja    vax.swin  g.G             roupLayout.PR EFERRE         D_SIZE  )
                .addC  ont  ainerGap())
                   );
        jPane l3Lay     out.s e tV    er ticalGroup(    
                       jPan el3Lay     out.c    reateParalle     lGroup(j    av ax.swing.G    roupLayout.Alignm   en t.LEADING      )
               .addGr     oup(j       Pa            nel   3Layout.create         SequentialGroup    ()
                                 .addC  ontainerGap()
                           .addGroup(j       Panel3L     ayout.createP   arall       e  lGroup(javax.s  wing.G  roupL         ayout.  Al   ignm   e    n   t.BASELIN E    ) 
                    .addComponent(okBtn)
                                                   .add  Component(backBt     n))
                     .addContai     nerGap(1 7, Shor    t.MAX_VALUE)         )
            );

        jPanel4.setBorder(       new jav           ax.swing.      b       order.SoftBevelBorder(javax      .s      w ing  .border.Beve             lBorder.LOWERED));

            stat usL.setText(    "nul     l");

           javax.swing.Gro   upLay  ou  t jPan   el4Layout = new javax.sw  ing.GroupLayo    ut(jPanel4)     ;
                    jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHori  z  ontalGroup(
                    jPane     l4Layout.  createParallelGroup(javax.swing.GroupL   ayou       t.Align     ment.LEADING)
                  .a ddGroup(j   Panel4Layout.createSeque              ntialGroup()
                .addC   ontainerGap  ()
                               .add      Component    (st    atusL, javax.swing.GroupLayout.DEFAULT_S   IZE, java  x.s   wing.Gr  oupLayout.DEFAULT_SIZE, Short     .MAX   _      VAL   U           E   )
                                    .addContainerGap(        ))
               );
          jPan   el4La    yout.setVer    ti  ca lGroup(
            jPa      ne l4Lay     out.createParallelGroup(javax.sw        ing.Gro       upLayout.A  lignment.L     EA DING)
            .ad     dCompon       ent(status  L     )
        )  ;

         jPanel   5.se          tBorder(javax.swing.     B  ord  erFactory.c    r   eateTitledB    o     r        d           er("Events")      );          

         jList1.set  Sel    e         cti       onMode(javax.swing.L      istSelectionModel  .SINGLE_SELECTION);
              jScrollPane3.setViewportView(jList1);

        j    But t     on1    .setT  ext("New  ");  
                          jButton    1                    .addMouseLi ste   ner(new java        .aw t        .event.MouseAd    apte       r() {    
            publ ic void mouseReleas      e  d(java.awt.ev ent.MouseEv        ent evt) {
                                     jButt     on1Mouse Rel   eased(evt);
             }
        });
          jBut       ton1  .add     ActionListener(new java.a      wt.event.A   ctionListen   e    r() {
            public        void actio nPerfo     rmed(java.awt.      even   t.ActionEvent    evt  ) {
                              jButton   1ActionPerformed(ev      t);
                         }
              }        );
   
        jButton2.setText("        Edit");

        jB  u    tton3.se tText("Delete");     

        jButton7.setTex t("   A   dd");

              javax   .swin   g     .GroupLayout jPanel5L a   yout = new javax.sw  in         g.G    roupLayout(jPanel5);     
            jPa            nel5  .s etLayou             t(jPanel5Layo   ut);
         jPanel5         Layout.setHorizontalGroup(
             jP   anel5   Layout.      createP    arallel   G   roup(ja  vax.swing.G           roupLayout.Alignment.LEADING)
             .addGroup(jPanel5Lay out.  cre   ateS   equenti    a    lGroup()
                    .a ddContainer Gap()
                   .ad       dGroup(jPanel5L ayou     t.createParall  elGroup( jav       ax.swing.G     roupLayo  ut         . Al          ignment.TRAIL     I      NG)
                      .add   Comp  onent(jS  croll   Pane3, javax.swing.   GroupLayout.DEFA   ULT_SIZE, 364, Short.MAX_VALUE)
                                    .addGroup(jPane    l5Layo    ut.createS     equentia  lGroup()     
                                .addGap(    0, 0,  Short.                 MAX_VALUE)
                            .  addComponent  (jButton7,         ja             vax  .s         wing.GroupLayout.PREFERRED_SI ZE, 75, javax.swing.GroupLayou      t.   PRE      FERRED_     SIZE)
                                           .addPreferredGap(j     avax.swing.Layo    utStyle.Component P   lacement.UNRELATED)
                                .  addComponent( jButton1, javax.swing.   Grou  pLayout.PREFE   RRED_S    IZE, 75,     javax.swing.Gr     oupLayo ut.PR EFERRED_SIZE)
                         .addPreferre            dGap(ja   vax.swing.LayoutSty l      e.Componen   tPlaceme nt.    UNRELATED)
                                         .a  ddCompon    ent(j     Button2    , j   av     ax.swing.GroupLayout.PREFE RRED_S  I     ZE, 75  , jav a       x .swing.Grou  pLayou               t.PREFERR  ED_SIZE)
                                      .ad   dPrefer   redG    ap(javax.s   wing.LayoutStyle.Component   Placemen      t.UNRELATED)
                                 .addCom   ponent( jButton3, j avax.swing.Group Layout  .PREFERRED_SIZE, 70 , javax.swing.Gro  up     Layout.PREFERRED_SIZE)))
                      .addContainerGap())
            );
        jPanel5La   yo   ut.setVertical     Group(
              jPanel5Layo   ut.createParallelG    ro      up(javax. swing.Gro upLayout.Alignment.    LEADIN G      )
                   .addGroup(jPan e   l5Layout     .createSequenti    alGroup()
                         .addCo      mpon ent(jScrollPane3, j avax.sw    ing.Gro     upLayout.DEFAULT_SIZE     , 1  35,      Sh   ort.MAX _VALUE)
                   .a ddGap(18, 18, 18)
                .addGr       oup(jPanel5  Layout.cr   eateParallelGrou    p(javax.sw  i   ng.Group   Layout.Ali  gnment.B   ASE   LIN    E)
                    .addComponent(jB    utton1)
                                            .a       ddC omponent(jButton2)
                              .addComponent(jButton3)
                                    .addC  o             mponent(jButton7))
                        .ad   dContaine    rG  ap())
        );

        j   Pan  el6.set     Border(  j av ax.swing.      BorderFactory        .createTitled   Border("Con      t    acts"  ));

                   jList2.setS  elect       ionMode(j     avax  .swing.ListSelectio    nModel.SINGL  E_SELECTI    ON) ;
              jScrollPane4.setViewpo    rtView  (jList2   );

             jButton4     .setText("New    ");
          jBut  ton4.addMous     eListener(new ja      va.awt .event.Mous    eAdapter       () {
                public void mouseRe   leased(j   ava.    aw  t.event.Mou  seEvent ev    t) {
                                 jBu   tton4MouseR   eleased(evt)    ;
            }     
             });
        jButton4.addAc     tionListener(ne  w java.awt.event.A    ctionLis    tener(     ) {
                    public void actionPerform ed(java.awt    .event.Acti   onEvent evt) {
                            jButton4ActionPerformed(    evt); 
                         }
        });

               jButton5.setTex    t("Edit");

              jButton6.se    tText("Delete");

         jButton8.set    Text("Add");

        javax.      swing   .GroupLayout           jPanel6Layout = new j    avax.swin    g.GroupLayout(jPanel6)    ;
        j     Panel6.setLayout(jPanel6Layou  t);
        jPa    nel6Layout.setHorizontalG  roup(
               jPan    el6Layou   t    .createPar allelGroup(javax.swing.Group La  you    t.Alignment.         LEADING)
               .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerG    a   p()   
                .addGroup(jPanel6Layout.createParallelGroup(jav     ax.swing.Group  Layout.Alignmen     t.LEADI     NG)
                       .addComponent(jScrollPane4 , javax.swing.G      roup  Layo   ut.DEFA    ULT_  SIZE,         36  4        , Shor t.MAX_VALUE)
                          .addGroup  (javax.swing.Gr    oupLayout.Alig  n               ment.TRAILING, jPa                  nel6Layou  t.createSequentia  lGroup()
                             .addGa   p(0   , 0, Short.MAX_   VALUE) 
                                     .addCompon      ent(jButto n  8, javax.s              wing.Group          Lay     out.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PR EFERRED    _SIZE)
                          .addP    referredGap(java    x.sw   ing.LayoutStyle.Com   ponent     Placement.        UNRELATED     )
                                .addComponent    (jButton4, javax.swing.GroupLayout.PR     EFERRED_  SIZE,    72, javax.swi   ng.GroupL  ayout.PREFERRED_SIZE)
                           .addPreferredGap(javax.swin  g.LayoutStyle.Com  ponentPlace      ment.UNREL       ATED)
                              .addComp  onent(jButton   5, javax.s  wing.GroupLayout.PREF  ERRED_SIZE,   77     , javax .swing.Gr  oupLayou  t.PREFERRED    _SIZE)
                           .addPreferredG  ap(javax.swing.La       youtStyle.C  omponentPlacement.UNRELATED)
                        .addComponent(jButton6, javax    .swing .Gro    upLayout.PREFE      R  RED_SIZE, 80,   javax.swi   ng.GroupL ay   out.PREFERRED_SI ZE)))
                     .addContainerGap())
           );
             jPanel6Layout.setV  ertica    lGroup  (
              jPan   el6Layout.crea     teParallelGrou  p(    javax.swing.GroupLayout.A   lignment.LEADING)
               .addGroup(jPanel6       La  yout.createSequentialGro            up()
                            .addCompon  ent(jScrol   lPane4, javax.swing.Gro   upLayout.DEFAULT   _SIZE, 128,  S  hort.MAX_  VA   LUE)
                       .addGap(23, 23, 23)
                         .addGroup(j     P   anel6Layout.createP   ara   llelGroup(javax       .    swing.Gr         oupLayout.Alignme      nt.BASELINE)
                              .     addC    omponent(jButton4)
                           .addComponent(jButton5)
                         .addCompon     ent(jButton6)
                         .addComponent(jButton8))
                 .addCont  ainerGap())
             );

          javax.swing.Group   Layout la  yout = new javax.swing.GroupLayo  ut(getContentPane());
        getContentPane().setLayout(layout) ;
        layout.setHorizontalGroup(
            layout.createParal  lelGroup(javax.swing.GroupLa  yout.Alignm  ent.LEADING)
                .addGroup(layout.createSequenti    a      lGroup()
                .  addContaine           rGap()
                .addGroup(layout.createParallelGroup(   javax.swing.Gro upLayout.Alignment.LEADING)
                                  .addGr          oup(layout.create     SequentialGroup()
                          .ad       dComponent(jPanel2, javax.swing.G       roupLayout.DEFAUL   T_SIZE, javax.swing.GroupLayout.DEFAULT_SI      ZE, Short.MAX_VA     LUE)
                                      .addPreferredGap(javax.swing.   L  ayoutStyle.Comp      onentPlacement.RE LATE     D)
                             .addGroup(layo  ut.c  r   eateParallelGroup(javax.swing.GroupLayout.Alignmen  t.LEADING)
                                 .addC    omponent(jPanel6 , j  avax.swing.GroupLay   out.DEFAULT_SIZE, javax.swing.Group   Layout.DEFAULT_SIZE,  Short.M AX_VALUE)
                                .addComponent(jPanel5, java   x.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel3, javax.swing.GroupL   ayout.Alignm     ent.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax     .swing.GroupLayout.DEF          AULT_SIZE   ,         Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.s  wing.GroupLayout.DEFAULT_SI  ZE, javax.  swing.GroupLayout.DE    FAULT_S     IZE, Short.MAX_VALUE))
                .addContainerGap())
          );
        layout.setVerticalGroup (
            layout.createParallelGroup(javax.swing. Gro  upLayout.Ali    gnment.LEADING)
             .addGroup(layout.createSequen      tialGroup  (   )
                .a     ddContaine     rGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LE      ADING)
                    .addCompone   nt(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.sw  ing.GroupLayout      .     DEFAULT_SIZE, Short.MAX_VALUE)
                          .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap (java  x.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addCompon   ent(jPanel6, javax.swing.Gr  oupLa yout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, ja  vax.swi    ng.Grou     pLayou    t.PREFERRED_SIZE)))
                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.R ELATED)
                .addComponent(jPanel3, javax.swing.GroupL   ayout.PREFERRED_SIZE, javax.swing.GroupLayo   ut.DEFAUL   T_SIZE, javax.swing.GroupLayout  .PREFERRED_SIZE)
                   .addPreferredGa    p(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.  P REFERRED_SIZE)
                .addContain    erGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBt    nActionPerformed
        shutdown();
    }//GEN-LAST:even   t_backBtnActionPerformed

     private vo id okBtnActionPerformed(ja       va.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
        try {
            save();
        } catch (SQLException ex) {
            MesDial.conError(this);
            Logger.getLogger(DayFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-  LAST:event_okBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBut  ton1A   ctionPerformed
        if (!EventFrame.isInsta nceAl         ive()) {
                   n     ew EventFrame(this, c, NIL, id);
        }
       }//GEN-LAST:event_jButton1ActionPerformed

    private void jButto       n4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (!ContactFrame.isI nstanceAlive()) {
            new ContactFrame(this, c, NIL);
        }
        }//GEN-LAST:event_   jButton4ActionPerformed

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
    }//GEN-LAST:event_jButton1MouseReleased

    private void jButton4MouseReleased(java.awt.e  vent.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseReleased
    }//GEN-LAST:event_jButton4MouseReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox alcoholChk;
       private javax.   swing.JButton backBtn;
        private javax.swing.JTextField dateF;
    private javax.swing.JTextField expensesF;
    private javax.swing.JCheckBox funChk;   
    private javax.swing.JButton jButton1;
    priv    ate javax.swing.JButton jButton2;
    private j avax.swing.JButton jButton3;
    private javax.swing.JButton jButton4   ;
    private javax.swing.JButton jButton5;
        private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel     jLabel4;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel2;
    private javax.swin   g.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton okBtn;
    private javax.swing.JCheckBox practiceChk;
    private javax.swing.JCheckBox sexChk;
    private javax.swing.JCheckBox specialChk;
    private javax.swing.JLabel statusL;
    private javax.swing.JTextArea summaryArea;
    private javax.swing.JCheckBox workChk;
    // End of variables declaration//GEN-END:variables
}

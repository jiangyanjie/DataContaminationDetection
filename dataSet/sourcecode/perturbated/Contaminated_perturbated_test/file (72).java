/*
    *        To  change this template, choose   To       ol s | Templates
  * a          nd o      pen t  he template in the editor.
   */
packag  e videorentalstore.gui;

impor    t videorentalstore.User.User;
import videorent     alstore.database.Database;  

/**
 *
 * @author Ashley
 */
public class     CustomerAccount_SignUp extends javax.swing.JFrame {
    private    Database db;
            /**
      * Creat   es new f   orm Cust omerA    cc    ount_Sign      Up   
                */  
    public       CustomerA      ccou           nt_SignUp (   D    atabase db) {
                                initCompo      nen  ts();
        thi s.db       = db;
      }

         /**
       * This    me t hod is        called from within  the constructor  to initia   liz      e              the form.
        *  WARNING: Do NOT modify t   his code        . T  he content of       this met   hod is always
      * r egenerated     by th    e Form E ditor.
        */
    @SuppressWarnings    ("unchecked")
         //    <edi   tor-fold defaul     tstate  =   "collapsed" de  sc="Generated Code">/    /GEN-BEG   IN:initComponents
    p  riva        te void   i nitComponen    ts() {
    
        signUpPanel  = new javax.swi  ng     .JPanel();    
        signUpTxtPanel = new javax.sw     ing.JPanel();
              rentals4      Ul    ogo     Lab       el = new javax.swing.JLabel();
        greetingLabel = new javax.swing .JLa   bel();
        signUpInfoPanel    = ne w j  ava     x.swing.JPanel();
        newUsernameLa     bel = ne    w javax.swing.         JLabel();
               newUser  PasswordLabel = new    javax.swin  g.JLabel();
               new   UserEmailLabel = new javax.swing.JLabel();
          newUse   rnameTxt =    new javax.swing.JTex      tField();
          n   ewUserPa  sswordT  xt = new ja vax.swing.J  Pass     wordField();
                   new   UserE   mail   Txt       =    new javax.swi  ng.JTextF   ield() ;
        f irstNameLabel   =   new javax    .swing.JL    a  bel()   ;    
            lastNameLabel = new    java   x.swing.JLabe l();
         firstN ameTxt = new javax.swin g.JText  F    ield();
              last     Nam    e Txt = new javax.swi  ng.JTextField()  ;
               cityL  a   bel = ne       w javax.swing.JLabel();  
        cit        yTxt = new javax.swing  .JTextField();
        zipc   odeLabel = new     j    av ax.swin  g.JLabel   ();
        zipcodeTx      t = ne       w javax.swing.JTex tFie     ld();
        add     ress Label =     new jav   a  x         .swin g.JLabe  l();
                 bi rthdayLabel = new      javax.swing.    JL         abel();
                       cr  editCardN   umLabel = new javax.swing.JLabel();
            creditCardNumTxt       = new j     ava   x.swing.JTextFie  ld()   ;  
           creditCard   ExpLa      bel = ne   w javax.swing.JLabel(      );
             signUpB   utton = new javax.swi   ng.JButton();
        addressTx  t = new j    av a       x.swing.JTextF   iel     d();
           stateLabel = new    javax  .swing.JL abel();
        birthdayMonth = new javax.swi    ng.JCo    mboBox();
        birthdayDa   y = new javax.swin   g.JCo         mboBox();
           bi    rthdayYear = new         javax.swing.JComboBox();
        stateTxt  = new javax.swing.JCo    mboBox();
        creditCardEx    pirati    onMont  h =  new javax     .swin  g.JComboBox();
        creditCardExpir  ationYear = new javax.swing.JComboBo   x();

        setDefaultCloseOperation(javax.s   wing.Wind ow     Consta  nts.EXIT_ON_CLOSE   );
               getContentPa   ne().setLayout(new java.awt.GridLayout(1, 0));

        signU  pPanel.s  etBackgroun  d(n  ew java.awt.Colo  r(153, 0, 0));

        signUpTxtPanel.setBackground(new java.a   wt  .Color(153  , 0, 0));

             rentals4UlogoLabel.setIc          o  n  (new javax.swing.Ima     geIcon(g  etClass().getResource ("  /vide  orentalstore/gui/Image_Rent4UL    ogoLar ge.jpg"        ))); // NOI18N

        gre   etingLab  el.setFont(new      java      .   awt.Font("Tahoma", 1          ,  18)); // NOI1   8N
                greetingLabel      .setText("Join     Us And Rent Great Movies For Great Prices");

            sign    UpIn     foPanel.se     tBackground(new java.awt.C     olo   r(153, 0, 0));
        si       gnUpInfoPanel.setBorder   (  javax.swing.BorderFactory.createTitledBorder(nul  l, "Sign Up", javax.swing.border.TitledBorder.DEFAU  LT_JUSTIFICATION, javax.sw  ing.border.Titled         Border.DEFAULT_POSITIO     N, ne    w java.awt.Fo      nt("Vani", 1, 30), new ja     va.aw        t         .Col    or(0, 0, 0))); //      NOI1   8N
        signUpIn  fo  Panel.setForeground(    new java.awt.Color(51,         51 , 51));
    
        newUsernameLabel.setForeground(new ja    v    a.awt.Color(2 55, 2  55, 255)  );
                  newUsernameLabel.setText("Username:");

        n        ewUserPa   sswordLabe    l.setForeground(new ja   va.awt  .Color(255, 255    , 255));
            newU       serPass wordLabel.setText("Password:");

            newUserEmailLab    el.setForegroun d(new            java.a       w     t.          Color(255   , 255,             255));
        newUserEmailLabel   .setText("E   mail:")      ;

        f irstNameLabel.setForeground(   new java.awt.Color(255, 255, 255)   );
        fi    rstNameLabel.setTe     xt("Fir     st    Name:");

         lastName  Label.setForegroun     d(new jav     a.awt.Colo   r(255, 255, 255));
            lastNameLabel.setText("Last Name:"    );

        cityLabel.     se   tFo re  ground(ne   w java.awt.C     olor(255  , 255,   255));
        cityLabel.s etTex     t("City:");

           zipcodeLabel. setForeground(new      java.a wt.Color(255,  255, 255));
         zipcodeLabel.setTex t("Z        ipcode:")  ;

        ad   dressLab  el.set  Foreground(new java.awt.Co   lor(25  5, 2   55,  255))          ;
            addr essLabel.setText("Address:   ");

        bi    rthdayLabel.s    etForeg  roun  d(new      ja   va.aw t.Color(2        5  5, 255,        255));
        birthdayLabel.setText( "Birthda     y:");

             creditCardNumLab el.setForeground(n  ew j     ava.a       wt.Colo   r(25 5, 255, 255)    );
        creditCard   NumLabel.          setT  ext("   Credit Card Numb  er: ");

        creditCardExpL    abel.setForeground(new j     ava. awt.Color(255, 2     55, 255     )    );
            creditCardExpLabel.setTe xt("Credit   Card Expirati on Da    te       :");

                 signUpButton.s   etText("Sign Up");
               si       gnUpButto  n.a           ddActi onL    istener(new java.awt.event.ActionListener() {
            pub        lic void actio  nPerformed(java.awt.e     vent.ActionEvent evt) {
                   signUpButtonActi    onPerfor  med(evt);
                 }
         });
              signU  pButton.addKeyListener(new java.awt.event.Key     Adapte r        ()     {
            p   ublic voi        d k     eyPresse     d(jav       a.awt.event.KeyEvent evt) {
                    si       gnUpButtonKeyPre   ssed(evt);
                   }
        });  

        stateLabel.setForegrou  nd(new java.awt.C  olor(255, 255, 255));
        stateLabel.  setText("State:");
 
        bir    thdayMonth .setModel(ne  w javax.swing.DefaultComboBoxMod   el(new String[] {   "Mont  h", " Jan", "Feb   "  , "Mar", "Apr    ", "May  ", "        Ju  n", "Jul ", "Aug", "Sep", "Oct", "No      v", "Dec" }));  

             birth    dayDay.setModel(new javax.s   wing.Def   ault ComboBoxMode     l      (new S   tring[] { "Day", "01", "02",      "03"  , "0     4", "05", "06", "07"      , "08     ", "09", "10"    , "11", "12", "13", "14", "15  ", "16",           "17", "18         "     , "19", "20", "21",    "2   2", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

           b i    rthdayYear.setModel(new javax     .swing.DefaultComboBoxModel(ne    w S             tring[] { "Year", "1995", "1994", "   1993", "1992", "1991", "1990", "198             9",      "1988", "1987", "1986",     "1985",    "198 4", "1983",       "1982", "1981", "1980", "1   979", "1978", "1977", "1976", "1  975 ",    "1974",              "1973",  "19      72", "1971", "1 970",    "1969", "1968", "1967", "    1966", "1965",  "1964", "1963", "1962", "1961", "1960", "1959", "19     5      8"  , "1957", "1956", "1955",   "1   954", "1953", "1952", "1951", "1950", "1949", "1948", "   194  7",  "1946", "1945", "19   44", "19    43"    , "1942", "   19     41", "1940",  " 1939", "1    93   8", "1937", "1936       ", "1935",    "1934", "1933", "1932", "1931", "1930", "1929", "192   8        ", "1927", "1926", "1925", "1     924", "   1923",      "1922", "1921", "1920", "1919"   ,    "1918", "1917", "19    16", "1915", "1914", "19         13" }));

        stateTxt.setModel(new javax.swing.D    efaultCombo     B  o   xModel(new St   ring   [] { "", "AK - Al  aska", "AL - Alabama", "A R - Arkansas   ", "   AZ - Ari       zona", "CA - Cal     i   fornia", "CO -  Colorado    ", "CT - Connecticut   ",    "DE - Delaware   ", "FL - Florida", "GA - Georg     ia", "HI - Haw   aii    ", "    IA - Iowa", "ID - Idaho", "IL - Illinois",          "IN -Indiana", "KS - K a    n sas", "KY - Kentucky", "LA - Lo       uisiana", "MA - Massachu   se   tts", "MD       - Marylan    d", "ME - Ma ine", "MI - Mi     chigan", "MN - Mi    nnesota",   "MO    - Missouri"     , "   MS    - Missi     ssi   ppi", "M    T - Montana", "NC  - North    Carolina", "ND - North Da  kota", "NE - Nebraska", "NH - New Hamps               hir   e", "NJ                - Ne   w Jersey"    , "NM - New    Mex   ico", "NV - Neva             d     a", "NY - New York", " OH - O  hio"     , "O  K - O   klaho  ma", "OR - Oregon", "PA - Pennsylvania", "RI -      Rhode      I sla   nd", "SC - So    uth C    arolina", "S          D - South Dakota"  , "TN   - Tennessee", "TX - Te  xas", "UT - Ut     ah", "VA   - Vi    rginia",     "VT - Ve   rmon t", "WA - Washing    ton      ", "WI -       Wisconsin", "WV - Wes     t      Virginia"  , "WY   - Wyom ing" }));  

           creditCardExpiratio      n    Mo nth.setModel     (new ja      v ax.swing   .DefaultComboBoxModel(new String[] { "Month",     "01", "    02          ", "0    3",   "04", "05", "06", "07", "08", "0   9"   , "10", "11", "12" }));
 
             creditCardExpirationYear.setModel(new javax.swing.Default ComboBoxModel(new Str    ing [            ] { "Year   ", "2012     "             , "2           0   13",       "2014"      ,    "2015    ",     "2016", "2     017", "2018", "2019"  , "202   0", "2021", "2022" }));

              j  avax.swing    .Group  L       ayout   sign  UpI nfoPanelLay      out = new javax.sw    ing           .GroupL       a     yo        ut(sig    nUpInfoPanel)    ;
        signU  pInfo   P  anel.setLayout(signUpInfoPa  nelLay       out  ); 
           sign UpInf      oPa  nelLayout  .setHorizontalGroup(
            signUpIn    foPanelLayout.cr  eateP    ara   llelGro  up(j avax.swi              ng.Group      Layout.Alignme          nt.LEADING)
            .       ad       dG    roup(signUpInfoPanelL  ay  out.createSequentialGroup()
                               .addGa p(           20, 2    0, 2     0)
                   .addGrou      p(signUpInfoP            a   nelLayout.cre  at ePara          lle lGroup(javax     .sw ing.G ro  up Layout.Alignment.    LEADING)
                                                            .addGr   oup(sig     n   UpInfoPanelL    a   yout  .crea    teSequentialGro     up()
                                  .   addCompon ent(lastNameLabel)
                                                .   ad               dPreferre   dG   a     p(      javax.         swi     ng.La     y                  outSty  le   .C           omponentPla cement.UNRE        LATE  D    )
                                .addComponent(      l       astNameTxt, j     a  vax.swing.Grou      pL       ayout     .PREFERRED_SIZE  , 175,             javax.sw   ing.Gro  upLayout.PREFE            RRED_  SIZE))
                             .addGrou  p(s    i gnU  pInfoPanelLa   yout.c       reateSe           quentialGroup()         
                                                    .ad   dG        r     oup(sign  UpInfoPane     lLayout.createParallelG roup(ja     vax.s wing.GroupLa     yout.Alignment                .LEADING)
                                   .addComponen      t(firstNameLabe  l)
                                                                .      addComponen     t(newUse  rEmailLabel)   
                                    .ad dCo    mponent(newUserPasswordLabel)
                               .add       Component(newUser        nameLabel))
                                   .ad    dGap(11,       1 1, 11)
                                                    .addGroup      (signUpIn  foPanelLayo  ut     .                 create ParallelGroup(javax.swing.Grou      pLayout.      Align  ment.TRAILING,   false)
                              .addC    ompon   ent      (new User       Pass        wor    d        Txt   , jav   ax.s          wing.Gro   upLayout.  Alignment.LEAD   I      NG)
                                       .addCompone   nt(fi rstNameTxt,            j     av   ax.swing.GroupLayout.   A  lignment.  LEADING)
                                                 .addCompo      nent             (       newUserEmailTxt, javax.swin  g.G roup            Layout.     Align    m en  t.LEADI    NG)  
                                          .addComponent(newUsername  Tx  t, javax.swing.GroupLayou    t.         A  lignment   .LEADING,   javax     .swing.    Gro    upLayout.PREFERR ED   _SI  ZE,           173, javax.swing.G      r   oupLayou   t.PREFERRED     _SIZE)))    
                       .addGroup(signUpInfoP    anelL    ayo  ut.create    ParallelGroup(jav        ax.swin g .Group  Layou  t.Al ignme nt.TRAILING)
                                 .a  ddC  omp    on   ent(signUpB utton)
                                                 .add          Group(signUpInfoPanelLayout .createSeque         nt     ialGrou     p()
                                                               .add   Gr    oup(signUpInfoP an   e   lLayout.creat     eP arallelGroup(javax.  swing   .G   r   oupLayo         ut.   Alignm ent.   LEA     DI    NG)
                                          .addComponent(creditC       ard    NumLabel)
                                        .addCompo nen   t(creditC    ardEx            pLabel))
                                                      .addGr  oup(signUpInfoPa   nelLay        out.createParallelGroup(javax.swing  .GroupLayout.Al   ig        n    ment.LEADING)
                                            .add                   Gr      oup(signUpInfoPanelLayout.cre     ateSeq    uentialGroup()
                                             .a     ddGa        p(18, 18, 18)
                                                           .ad    dCompon e   nt(credi  tCa     rdExp    irationMo   nth,   j      avax.swing.GroupLayou        t.PREFER    RED_SI  ZE, 80  , ja   vax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .a           ddGap(    18, 18, 18)
                                          .  addComp    onent       (cre         ditCardExpirationYea   r, javax.s   wing.Group    L  ayou   t.PREFERRED_SIZE, 72, ja   vax.sw   ing.GroupLayou      t.  PREFERRED_SIZE))
                                                 .addGroup(       s  ig     nUpInfoPanelLayou         t.cr eateS  eque  ntialGroup()
                                                          .  addGap(4, 4, 4) 
                                                      .add   Component(creditCardNum     Txt,     javax.    swi    ng.GroupLa     yout.PR EFER  RED   _   SIZE,  184,      j         av     ax.swing.GroupLay  out            .     PREFERRED_SIZE)))))
                              .a  ddGroup(s    ignUpIn    foPanelLayout.cre      ate Sequen        tialGroup()
                           .add Group(si  gnUpI   nfo         PanelLayout.createParallelGrou                 p (javax    .swing.GroupLayout.A   lignment.LEADING)
                                      .addCompon   ent(birthdayLabel)
                                      .    addComponent(addressLabel)
                                          .addCom  ponent(cityLabel)
                                        .addComponent(state     La  bel)
                                   .addComponent(zipc             ode  Label))
                                    .addG         ap(18, 18,    18   )
                           .add Group(si    gnUpInfoPanelLayout.crea     tePara     llelGroup(javax.swing.Group   Lay       ou   t.Align     ment.LEADING)
                                .ad           dComponent(z  ipcodeT  x      t, javax.swing.GroupL ayou    t.PREFERRED_SIZE, 175    ,     ja    vax.swin     g.Grou           pL      ayout.PREFE    RR ED_SIZ   E)
                              .addComponent(s       tate   Txt, javax.swing.GroupLay   out.PREFERRED_     SIZE, 175,      java   x.swing   .GroupL   ayou           t.PREFERRED_SI   ZE)
                                   .ad   dComponent(c    ityTxt, javax.swing.G roupLayout.PREFERRED    _S  IZE, 175, ja  v ax.swi ng.GroupLayout.PREF    ERR     ED_SI     ZE)
                                  .addComponent(addressTx  t,   javax.s    wi    ng.  Gro   upLayout.PRE FE    RRED_SIZE, 175   , javax.swin   g.GroupL     ayout.P    REFERRED_SIZE)
                                         .add   Group (signUpInfoPanelLayout.createSequentialGroup()
                                               . addComponent(bi      rthday         Month, javax.s wing.GroupLayo   ut.PREFE        RRE    D_          S    IZE, javax.swing.GroupLayout.DEFAULT_SIZE, jav    ax.swing.Gro  upLayout.PREFER RED_SIZE)
                                     .addPre  ferredGap    (javax   .swing.Layout     Style.ComponentPlacement .RELATED)
                                            .add   Component(birth   dayDay, javax.swing.Gro    upLayout.PREFERR        ED_     SIZE, javax    .swing.GroupLayout   .DEFAUL    T_SIZE, jav  ax.swing.Group             Layout.       P   REFER   RED_SIZE)
                                         .addPreferredGap(java x.swing.L    ayoutStyle.ComponentPlacem         ent.RELATED)
                                                              .addComponen      t(birthdayYea   r,     java      x.swing.Grou     pLayout. PREFE    RRED   _SIZE, 64, javax.  swing.GroupLay   out.PR  EFE  RRED_S  IZE)))))
                               .addContainerGa  p(35, S   hort.MA    X_VALUE)         )     
        );
                         sign  UpInfo            Pane   lLayout.setVe     rtical   Group(
            signU  pInfoPanelLayou        t.create     Par     alle   lGroup       (javax.s wing.GroupLayout.Al  ignment.      LE ADING)
                .addG        roup(sig  nUpInfoPanelLayout .   cre     a      teSequ      en   tialGrou   p(        )
                   .addContainerGap()
                        .addGroup  (signUpI          nfoPanelLayout.createParallelGroup(javax.swing.Gro   u   pLayo                ut.Align ment.BASELINE)
                      .addComponent(ne  wUsernameLabel)
                          .addCom       ponent(newUsernameTxt, jav   ax.s        wing.Gr    oupLayout.PREFERRED_SIZE, j   avax.  swing.GroupL     ayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                          .add  Preferr  edG    ap(javax.swing.L    ayoutStyle.ComponentPlacement.UN    RELAT ED)
                     .ad   dGroup(s   i     gnU pInfoPanelLayout.createParallelGroup(java       x.swing. GroupLayout.Alignment.BA       SELIN        E)
                    .addComponen     t(      ne    w  User        Passw  or dLabel)
                    .addComp      onent(newUserPa           sswordTxt, j          avax    .swing.GroupLayout.      PREFER RED_  S      IZE, ja       vax.swing.GroupLay  out.     DEFAULT_SIZE, javax.swing.GroupLay out.PREFERRED_SIZ              E))
                              .addPre ferr   edGap(javax.swing.   Lay     o   utStyle.Compone    ntPlacement.UNRELA    TED)
                      .addGroup(signUpInfoPanelLa     yout.createPa   ralle     lGroup(javax.swing.Grou    pLayout.Align            m   ent.BASELINE )
                                   .addCom pon     ent(newUserEmailLabel   )
                    .addComponent(    newU               serE  ma    ilTx t, j  avax.swi   ng.GroupLayout.PREFERRED_SIZE,    j   avax.swing.GroupLayout.DEF     AULT_SIZE, javax.sw    ing .Group   Layout.PREF   ER   RED_SIZE))
                      .addG   ap(18, 18, 18)
                .addGroup(si  gn   Up    InfoP  anelLa            yout.      crea t ePar  allel     G roup(javax.swing   .          GroupLayout.Align         ment.BASELI    NE)
                         .           addComponent(firstNameLabel)
                          .a    ddCo     mponent(f     irstNameTxt ,         javax.swing.Gr    oupLa yout.      PREF ERRED_SIZE, j avax.swing.G   r  oup  Layou  t.DEFAULT_SIZE     , javax.swing.G       roupL                  ayout.PREFERRED_SIZE))
                 .addGap(18,    18 , 18)
                       .addGroup(si gnUpInfoPan  elLayout.createParallelGroup(ja      vax.swing.Grou  pL     a     yout    .Alignm    ent    .BASELINE)
                                     .a ddComponent(la   stNa     meLa           bel)   
                                             .addCo     mpone    nt(lastN     ameTxt, javax.swing.GroupLayout.PR   EFERRED_SIZE, j avax.swing.G roupLayout.DEFAULT_S IZE, javax. s   wing    .G roupLayout.PREFERRE  D_SI        ZE))
                      .addGap(18, 18, 18)
                       .addGrou     p(signUpInfoPan     elLayout.createParall elGroup(javax.swing.GroupLayout.Alig  nment.BASEL    INE)
                                  .addComponent(birthdayL   abe     l)
                      .addComponent(     bir        thd  ayMonth, javax.swing.GroupLay  out.PREFERRED_SIZE, javax  .swing.GroupLayout.DEFAULT_SIZE, javax.swing .G    roupLayo ut.PRE   FERRED_SIZE)    
                              .a ddComponent(   bi        rthdayYear,      j   avax.swing.Gro     upLayout.PRE   FERRED_SIZE, jav  ax.swing.GroupLayou     t.DEFAULT_SIZE, javax.swing.GroupLay  ou         t  .PREFERRED_SIZE)
                         .addComponent(bir   thd  ayDay   , j   av   a  x.swing.GroupLayout.PREFERRED_SIZE,            javax.swing.GroupLayout.D       EFAULT_SIZE, java  x.s      wing.GroupLayo     ut   .PR        E   FERRED_SIZE))  
                           .   addGap(18,    18, 18)
                   .add  Group(s    ignUpInfoPanelLayout.cre        ateParallelGro   up(javax.            swing.GroupL  a y    o  ut.A         lignment.BASELINE)
                            .addCompo nen t(addressTxt, javax.swing.GroupLayout.PREFERRED  _S  IZ  E, javax.  swin          g.Gro     upLayout.     DEF  AULT_SIZE, javax.  swing.Group       Layout    .PREFERRED_SIZE)
                                       .addCompone     nt(addressLabel)    )
                          .addGap(18  , 18,     18)
                           .a   ddG   roup(sign  UpInf  oPanel  Layout.createPa      rallel   Group(javax.s  wing.Gro      upLayout.Alignment.BASELINE)
                    .addComponent(cityTxt, ja     vax.swing.GroupLayout.PREFERRED_  SIZE, javax.swing.   GroupL    ayout.D  EFAU   LT_SIZE, ja       vax.s    wing. GroupLay out.   PREFERRED_       S    IZ E)   
                               .addComponent(cityLabel))
                .addGap(21,    21,    21)
                    .    addGrou        p(signUpInfoPa    nel          L  ayout.  cre   ateParal    l     elGroup(javax.sw     ing.GroupLayout.Alignment.BASELINE )
                    .addCo        mponent(stateL        abel)
                              .addComponent(stat    eTxt,           ja        vax.    swing.GroupL   ayou t.PREF     ERRED_SIZE, javax .swing.G  roupLayout.DEFAULT_SIZE, javax.swin    g.GroupLayout.PRE      FERRED_SIZE))
                 .addGa  p    (18, 18, 18)
                .addGr      o up(signUpInf   oPanelLayout.createP a      rallelGroup(javax .swing.    GroupLayou t.Alignme   nt.BAS        ELINE)
                             .addCompo   n          en    t(zipco      deTxt, javax.swi ng.GroupLayo  ut.PREFER     R ED_     SIZE, java           x.s wing.GroupLayout.D EF            AULT_S   IZ           E, javax.swing.Gro   upLayout.PREFERR    ED_SIZE)
                      .addComponent(z  ip   co de         Lab     el) )  
                                        .addGap(18, 18, 18)
                     .   add  Group(signUpInfoPanelLayout.cre  ate  ParallelGroup(  javax.              swi    ng.     G   roupLayo      ut.Alignment    .LEAD        ING, fa   lse)
                                  .a   ddGroup        (signUpInfoPanelLayout   .cre  ateSequentialGroup()
                                        .addComponent(          creditCardNu       mTxt, javax.   s   wi    ng.GroupLa         yout.P  REF    ERRED_SIZE, javax.swing.GroupL ayout.DE      FA  U  LT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZ    E)
                                   .addGap(35, 35, 35))
                              . addGroup(signU    pInfoPanelLay   out.createSequ    en tia          lGroup()
                                   .a    d     dCo        mponent(cred         itCar  dNumLabel)
                                     .a  dd Preferred   Gap         (javax.s wing.Lay    outSt   yle.Com ponent     Placement.           REL  ATED, ja   va x   .swing.GroupLayout.DEFAULT_SI     ZE,   Short.MAX_VALUE)
                          .addGroup(signUpInfoPa    nelLayou  t.        createParallelGro   up(jav  ax. swing.Gr    ou         pLayout.Alignment.BAS    E     LINE)
                                          .add Com      po  nent(creditCa  rdExpLabel)
                            .addC      omponent(credit     Ca   rdExpirationMonth, j   avax.swing.   Grou     pL            ayout.        P        REFERRED _SIZE,   javax.swing.Gro    upLayout.DEFAULT_    SIZ E  ,  java   x.swing.GroupLa    you    t.   PREFERRED_SIZE)
                                      .ad     dComponen    t(creditCardExpiratio       nYear, jav  a x.swi  ng.Grou        pLayout.PREFERRED_SIZ E, ja    vax.swing.GroupLayout.DEFAULT_SIZE, javax.s   wi ng.Grou     pLayou      t.PR      EFER                RED_SIZE))))
                        .a       ddGap  (18, 18, 18)
                    .addComponent(signUpButton) 
                  .addContainerGap(48, Short.   MAX_  V    A  LUE))
        );

        javax.swing.G            roupLayout signUpT  xtPanelLayout            = new   javax   .swing.GroupLayout(signUpTxtPanel);
        signUpTxtPane     l.setLayout(sig        nUpTxtPanelLayout)   ;
           signUpTx      tPane   lL     ayout.setHorizont  alGroup(
             signUpTxtP       anelLayout.createPara  ll  elGroup     (jav     ax.swin     g.Grou pLayout.A  lignment.LEAD     ING)
            .addGroup(signUpTxtPanelLayo     ut. createSequentialGroup()
                     .addContainerGap()
                .addGroup(signUpTxtPa nelLayo     ut.createParallelGroup(javax.s         wi              ng.GroupLayout.Alignme    nt.LEAD   ING)
                                 .add  Comp    one        nt           (rentals4UlogoLabel)     
                    .addGroup(signUpTxtPanelLayout      .        createSe quentialGroup(         )
                             .a      ddGap(15, 15  , 15)
                             .addGroup   (signUpTxtPanelLa yout.createParallel        Group(j    avax.swing.GroupLayout.Alignme   nt.LEADING)
                                             .add   Component(gree    tin    gLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZ    E)
                                  .addG      r oup(signUpTxtPan     elLayout.createSequentialGroup()
                                                  .add  Gap(15       , 15,   15)
                                .addCom        ponent   (signUpInfoPane  l, javax.sw     in g.GroupLayout.PREFE  RRED_SIZ E, javax.swing.G   roupLayou       t.DEFAULT_SIZE, java  x.swing   .GroupLayout.PREFERRED     _SIZE )    ))))
                    .addGa   p(52              , 52, 52))
               );
        signUpTxt   PanelLayout.s   etVerticalGrou  p      (
              signUpTx  t    Pan    elLayout.createPar   allelGroup(javax  .swin  g.Grou           pLayout.Al   ignment.LEADING)
                .addGroup(signU    pTxtPanelLayout.   createSequential           Group()
                .addContainerG  ap()
                .addComponent     (rentals4Ulo   goLabel)
                               .addGap(18, 18, 18)
                     .add     Compo  nent(greetingLabel)
                     .ad       dGap(29, 29, 2   9)
                .addComponent(signUpInfoPanel, jav ax.swing.GroupLayout.PREF   ERRED_SI   Z      E, javax.swing.GroupLayout.DEFAULT_SIZE,    javax.swing. GroupLayout.P     REFERRED _SIZE)
                       .addContainerGap(100, Short.M   AX_VALUE))
        );

        java   x.swing.Gr     oupLayout signUpP   anelLayo   ut = n   ew javax.swin        g.GroupLayout(signUpPanel);
                 s      ignUp     Panel.   setLayout(signUpPanelLayout);
           signU  pPan      elLayout  .se  tHorizontalGr  oup(
            si    gnUpPa    n      elLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addGroup(  signUpP      an    elLayout.createSequential Group()
                        .addGap      (40  2, 402,    402)
                  .addCompo  nent(signUpTxtPanel, javax.s   wing.GroupLayout.PREFERRED    _SIZE, javax.swing   .GroupLay   out.DEFAUL      T_SIZE, javax.s    wing.GroupLa  yout.PRE    F    ERRED_SIZE)
                   .     addContain  erGa p(397, Short.MAX_VALUE))
        );
            signUpPanelLayout   .setVert     icalGroup(
             signUpPanelLayout.createPa rallelGroup(javax.swing.Grou    pLayout.Alignment.LEADING)
            .addComponent(signUpTxtPanel, javax.swing.  GroupLayout.Alignment.TRAILING, javax.swing.GroupLay  ou  t.DEFAULT_SIZE, java     x.swing.G   roupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

          getContentPane().add(         s ignUpPanel);

        pack();
             }// </editor-fold>   //GEN-E    ND:initComponents

     private void signUp   ButtonActionPerformed(java.awt.event.Actio      nEve    nt evt) {//GEN-FIRST:event_signUpB     uttonAc    tionPer   formed
        char[    ] pw = newUserPasswor   dTxt.getPassword();
              String password = new Stri   ng(pw);
        String email = newUserE   mailTxt.getText(); 
        String firstName = firstNameTxt.getText();
        String las tN       ame = l  astNameTxt.getText();
             String birthday = birthdayMon   th.getSelectedItem().toSt  ring() + "/" +     birthdayDay.getSelectedItem().toString() + "/" + birthdayYear.getSelectedItem().toString();  
        String city = cityTxt.getText();
           String state = stateTxt.getSelectedItem().toString();
        String zipCode = zipcodeTxt.getText();
        S   tring address = addressTxt.getText ();
        String creditCardNum = creditCardNumTxt.getText();
        Str        ing cr  editCardExpireDate = creditCardEx pirationMonth.getSelecte   dI tem().toString() + "/" + creditCardExpirationYear.getSelectedIt e     m().toString(  );

        User     u = new User (firstName, lastNa   m     e        , email, password,  birthday , creditCardNum,  creditCardExpireDate,  addre    s     s,  city,    state,  zipCode, false);
        db.addUsertoDB(u);
          Sys tem.ou     t.pri  ntln("user added");

           
        dispose();
        CustomerAccount_Movies f = ne w CustomerAccount_Movies(db, u);
        f.     setVisible(true);
    }//GEN-LAST:event_signUpButtonActionPerformed

    private void signUpButtonKeyPressed(java.awt.event.K  eyEvent evt) {//GEN-FIRST:event_sig       nUpButtonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_signUpButton  KeyPressed

    /**
     * @param args the   command line arguments
     */
    public void main(String args[]) {
            /* Set  the Nimbus look and feel   */
        //<editor-fold de     fault       state="collapsed" desc=" Look and feel setting code (optional    ) ">
           /* If Nimbus (introduced in Java SE 6)    is not available, stay with the default look and feel  .
                *   For details see http://download.oracle     .com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (j  avax.swing.U  IMan      ager.LookAndFeelInfo info : javax.swing.UIM   anager.getI    nstalledLookAndFeels()) {
                if ("Nimbus".e       quals(info.getName())) {
                    javax.swing.UIManager.setLookAndFee   l(info.getClassName());
                    break;
                }
            }   
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Custom  erAccount_SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationE xception ex) {
            java.u    til.loggin g  .Logger.getLogger(CustomerAccount_SignUp    .class.getName()).log(java.ut   il.logging.Level.SEVERE, n   ull, ex);
        } catch (IllegalAccessException ex) {
            java.uti    l.logging.Logger.getLogger(CustomerAccoun t_SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (jav    ax.swi ng.Unsu pportedLookAndFeelException ex)      {
            java.     util.logging.Logger.getLogger(CustomerAccount_SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

          /      * Create and display the form */
        java.awt.EventQueue.invokeLa     ter(new Runnable() {
                public void ru n() {
                new CustomerAccount_SignUp(db).setVi   sible(true);
            }
        }  );
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.sw  ing.JLabel addressLabel;
    private javax.swing.JTextField addressTxt;
    private javax.swing .JComboBox birthdayDay;
         private javax.swing.JLabel birthdayLabel;
    priva te java   x.swing.JComboBox birthdayMonth;
    private javax.swing.JComboBox birthdayYear;   
    private javax.swing.JLabel cityLabel;
    private javax.swing.JTextField cityTxt;
    private javax.swing.JLabel creditCardExpLabel;
    p    rivate javax.sw   ing.JComboBox creditCardExpirationMonth;
    private javax.swing.JComboBox creditCardExpirati  onYear;
    private jav   ax.swing.JLabel creditCardNumLabel;
    private javax.swing.JTextField cr   editCardNumTxt;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField firstNameTxt;
    private javax.swing.JLabel   greetingLabel;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JTextField lastNameTxt;
    private javax.swing.JLabel newUserEmailLabel;
    private javax.swing.JTextField newUserEmailTxt;
    private javax.swing.JLabel newUserPasswordLabel;
    private javax.swing.JPasswordField newUserPasswordTxt;
    private javax.swing.JLabel newUsernameLabel;
    private javax.sw  ing.JTextField newUsernameTxt;
    private javax.swing.JLabel rentals4UlogoLabel;
    private javax.swing.JButton signUpButton;
    private javax.swing.JPanel signUpInfoPanel;
    private javax.swing.JPanel signUpPanel    ;
    private javax.swing.JPanel signUpTxtPanel;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JComboBox stateTxt;
    private javax.swing.JLabel zipcodeLabel;
    private javax.swing.JTextField zipcodeTxt;
    // End of variables declaration//GEN-END:variables
}

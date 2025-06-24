/*
           *      To change thi s license heade  r, choose License  Headers in Projec      t Properties.
 * To  change this template file, choos   e    Tools | Te  mplates
 * and open the template          in    the editor.
 */

package Q uestion;
import QuizApp.Core.Answer;
import QuizApp.Core.Question;
   import Helper.D   bAccess;
import      java.awt.*;
import java.awt.event.*     ;
impo rt javax.swing .*;

    /**
 *
 * @author Gareth  lapt op
 */
public class Crea           teQuestion extends javax.swing.JPanel implement  s ActionListener
{
    pub     lic String questionText    ;
    pub   l      ic S trin    g Answer1Text;
    public S    tring A     nswer      2Text;
    public String Answer  3Text;
    public String    Answer4Text;
    p       ublic boolean Answer1Corr         ect =    false;
    p    ublic bool     ea   n Ans       wer2Correct =    fa  ls     e;
    pub    lic  boolean Answ   er3              Correct =         false;
    public  boole    an       Answer   4C     orr   ect = false;
           
    /**
          * Creates new form           CreateQuestion
                */
    public CreateQuestion() 
         {
             initComponents( );
          Submit_btn.ad    dAct   ion      Liste ne    r( this );
        Answer1_rdbt      n.add  ActionListener( this );
        Answer2     _rdbtn.ad     dActio nListener( thi     s );
             Answer3_rdbtn.addActionListener(    this );
                          A           nsw   er4_rdbtn.addA   ctionListene   r( this );
       }    
    
    @   Overrid  e      
              pu    blic      void action          P erformed(A cti    onEvent      ev       t)
      {
            
            if(evt.ge           tSource   () .equa  ls(Sub     mit_bt    n))          
        {
                       if(A   nswer1Cor   re c    t || Answe       r2  Corre   ct        |    |           An sw   er3Correc  t    |   |          Ans    wer4Co          r   rect)
                           {
                            /  /BUT  TON C       ODE HERE   
                    this.createQu  esti     on(      );

                         }
                    else
                     {
                
                              // Messag     e box pop    u  p to say please s    ele ct c o      rrect a nswer
                                     JOpt             ionPane.s howMessa  ge                     Dialog        (nu   ll,"Pleas        e indicate      correct      answer.   " ,     
                                                                        "  Err  or",JOption  Pane   .WARNING_MESS      AGE );
                    
            }
        }  
        
       }
    
          public        void createQuestion()
        {    
        
                 //     Set Values
                  questionTe     xt =    Quest      ionTxt_t  xtbx.getTex   t( );
          Answer1T ext = An   swer1  _txtbx.ge     tTe       xt();
           Answer2  Te  xt = Answer2_txtbx.     getText();   
                    An   swer        3Text = Ans  wer3_    txtbx.getText();
                Answer4Text = Answer4_txtbx.getText   ();
                         
        // Cr     eat    e ques     t i      on
        Question    q = new Ques   tion();
          q.AuthorId =           1   3  411    31;
                         q   .  i     sRej  ec     t  ed = false;
          q.que sti    on   Text =    questionText;  
           q.a   nswer       s  .add  (n    ew Answ   e   r(    A   ns    wer1Tex  t, An   swe    r1Correct));    
            q.answ        ers.add(new Answer  (Answ er2     Te          xt , Ans    wer2Cor  rect)  );
        q.answers.a    dd(new Answ   er(Answer3T         e         xt, A   n                swe  r   3     Co  rrect))    ;
             q.an     swers.a   dd(   ne    w Ans     w                      er(A    nswer4   Text, Answer4Co   rrect    ));
           
                              // Up d  a  t   e datab  ase
          int new                          Id = DbAcc  es       s         .StoreNewQuest    ion(q);
              
        if (newI     d > 0)
         {
                       //  SUC   CESS
                       q.db       I     d = new    Id;
               
                       //      Message     box pop up to s     ay please select          corr  ect answer
                      JOptionPa    ne.show      MessageDial  og(nul l,"Y ou  r Question h    as be en     s   u       bmi      tte      d.",
                                                              "Question   Submitted",JOp       tio nPane.WARNING_MESSAGE); 
                 
                    // Set Valu           es
              Quest          ionTxt_t    xtbx.set   Text(   "")        ;   
                   Answe   r1_txtbx.setTe      xt("");
                                     Answer2_txt   bx.setText("");    
            Answer3_            txtbx.     setText  ("");
                 Ans       we     r4       _t     xtbx.se   tText("");
                
                  An         swe    r1   Correct = f   alse;
                       An swer2Correct = fa      lse;
                Answe  r3Correct =     false;
            Answer4Correct = false;
                
        }
        else   
            {
             //FAIL      
                    JOp     tionPane.s     how  Me  ssageDialog(nul  l," Unable         to   add question, please check text fields."    
                                                      ," Erro r",J OptionPane.WARNING_MESSAGE);
        }     
        }
                    /**
     * This method i s called from   wit                hin the constructor to initialize the for  m   .
          * WARNING: Do NOT mod      ify this cod       e. The conten    t of this met    hod i   s alwa   ys
         *          r    ege      nera    ted by         the    F    orm  Ed  i     tor  .
         *   /
     @SuppressWarni   ngs(    "unchecked")
      // <e    d    itor-fold defaultstat e="collapsed" desc="Gen  erated Code">//G              EN-BEGIN:ini   tCompone       nts
        private v  oid initComponent  s() {

           jP         anel1    = new javax.swing.J   Panel();
            Answer1_    lb         l = new javax.swi      ng.JLabel();
              Answ      er  2_rdbtn = ne   w  javax.swing.  JRadio  Bu   tton();
        jSc   rollPane2  = n  ew java  x.swing.J   Scr   ollPane();
        Answe      r1_txtbx          = n         ew javax.swing.J         TextArea();
           Answer  1_      r     d btn =  new javax.swi   ng.JRadioButt  on();
        Submi t_btn     = new java   x   .swing.JButton()     ;
            Ti    tle_lbl = new j   avax.  swing.JLabel();
        jScrollPan e5 = ne     w    javax.swing.J  Scr   ollPan       e();
          Answer4_t    x    tbx = new javax.swing.       J TextA       re          a();
        An  swe r3_lbl  =       new javax.s       win g.JLabe        l( );
        Answe  r2_lbl = new  javax.swing.JLabel();
            Answer4_rdbtn =  new javax.    swing.JRadioB       ut      ton();
             Question_lbl = new ja    vax.swing    .JLabe l();
        A  nswerD      es_lb       l = n ew javax.swing.    JLabel();
              jScrollPane 1 =      new javax.swing.JScrollPane();       
          Que   stion Txt   _txtbx =       new javax.  s   wing.JTextAre a(   );
          Answer4_lbl =   new javax.         swing.JLabel();
               Answer3_rdb    tn = new javax.swin      g.JRadioBu               tton();
              jS    cr  oll    Pane3 = new javax.s wing.JScrollPane();
        Answer2_txtbx = new javax.swing. JTextArea();
        jScrollPane4 = new jav           ax.swing.JSc   rollPane     ();
        Answer3_txtbx = new j      avax.  swing.JT    extArea();

        Answer1  _lbl.setHorizonta    lAlignme  nt(j         a            vax.swi       ng.SwingConstants.CENTER);
              Answer1_lbl.  s   e     tText("Answer 1");

                       Answer2_     rdbtn.addActionLi    stener   (new   java.awt. eve nt.ActionListener()   {
            pu b     lic void   acti     onPerformed(java.awt.even   t.Ac   tio  nEvent       ev   t)       {
                       Answer2_rd        bt   nActionPerformed       (evt   );
            }     
           });

        Answer1_txt  bx.setColumns(20);
        Answer1_    txtbx.setR    ows(5);
        jScrollPane2.  s    etViewpor     tV iew(Answer1   _txtbx);

          Answer1_rdbtn.setTo   olTipT ext( " ");
           Answer1_rdbtn.addA   ctionListener  (new java.awt.event.A  ction        Listener() { 
                                         public              void act  ionPerf ormed(java.a             wt.event  .Actio    nEvent evt) {
                     Answer1_rdbtnActionPerformed(evt);
                     }     
        });
  
            Submit_btn.setT     ext("Submi t");

        Ti    tle_lbl.setFont(new       java.awt.   F ont("Tahoma", 1, 16)); // NOI18N
        Tit         l     e_lbl.setH  orizontalAl  ignment(javax.swing.SwingC          onsta  nts.CEN     TER);
        Title_lb     l.setText("Cre ate Q   u estion");

        Answer4_txtbx.setCo lumns(20);           
        Answ  er4_txtbx.set Row        s(5)      ;
        jScrollPane5.setViewportView(A    nsw  er4_txtb  x);

               Answer3_lbl.se tHorizontalAlignmen  t(javax.swing.SwingC    onstants.CENTE   R);
        Answer3_l  bl.se    tText("Ans     we  r 3"    )    ;
        Answer3_lbl.set      Horizontal TextPosition(java     x. swing.SwingConst       ants.R IGHT);

            Answer2_lbl.set  H     o rizontalAlignme    nt(javax.swing.S    wingCo  ns  tants.CEN TER);
         A   nswer2   _l    bl.setText(    "    An          swer 2");

             Answe      r4_rd  bt   n.a    ddActionListene  r(new ja v        a.aw      t.event.Acti   onList  e   ne    r() {
                                          public void actionPe     rformed(j   a   v       a.awt.  event.ActionEve nt    evt) {  
                      Ans  wer4_rdbtnActi   onPerformed(     evt);
                 }
        });
    
              Ques     tion_lbl.setH           or    iz      o    n talAlignment  (javax     .swing.SwingConstants.CENT                          ER    );
            Q        uestion_lb   l.s    etTe  xt("Ple        ase enter questio    n below:-");

              AnswerDe    s_lbl.setHorizontalA    l  ignment(ja    vax.sw    ing   .Sw    ingCon  stan         ts.   CENTER);
              AnswerDes_   lbl.setText("Ple   ase enter    answers a   nd indicate    correct answer below:-");

                Que stionTxt_txtbx.setC  olumns(20);  
                   Que stio        nTxt_txtbx.  set    Rows(5    );
          jScrollPane1.setViewport  View(Ques      tio nTx  t_t    xtbx);

        Answer   4    _lbl.setHo   ri    zontalAlignme   nt( javax.swing.S    wingConstants   .CENTER);
            Answe    r4 _lbl    .setTe      xt("  Answer         4");

         An   s   wer3_r       dbtn.addActionListene       r(new java .awt.event.ActionListener() {
                 p      ublic    void acti      o nPe        rf    or   med(java.awt.     even  t.ActionEvent   evt) {
                           Answer   3_rdbtnAction       Performed(evt);
                  }
             });     

             Ans  wer      2_txtbx.setCol  umns(20);
        Answer2_    txtbx.setRow  s(5) ;
        jScroll Pane3.s  etView      portView(Answer2_txtbx);     

                      Ans     wer3_t  xt    bx.   setColumns(20);
              Answer3_txtbx.s     etRows    (5)   ;
        jScrollPane4.se  tViewportView      (Answer3_tx       tbx);       

          j     ava       x   .s   wing.Gro   upLayout jPan  el  1Layout = new j    avax.swin      g  .GroupLa   you   t( jP  a          nel1);
        jPanel1.setLayo ut(jPanel1Layout);
         jPanel1Lay   out.setH              orizontalGr oup(
                        jPanel1Layout.creat    eParallelG      roup(javax.swing.Grou  pL   ayout         .Alignment.LEADIN   G        )
                    .addGroup(jPanel1Layout.  cr   eateParallel  Gr  oup(javax.      swing.GroupLayo    u t   .A  l   ignm             ent.LEADING)
                                       .add       Grou     p(jav     a      x.swing. GroupL ayout.Alignment.  TRAILI    NG,     jPanel1Layout.cre       ateSequ   entialGrou   p()
                                     .addCont  ainerGa       p()
                                               .addC  omponent (Subm    it_btn, javax.sw ing.G    roupLayout.PREFERRED_SIZE, 108, java    x.swin   g.GroupLayout.PREFE    RRED  _SIZE)
                                      .addGap(195, 195, 195  ))
                   .     a    ddGroup(jPanel 1Layout.c  reat   eSequentia  lGroup()
                                      .addGroup   (jPanel1Layou   t .createPa        r    al      lelGrou   p(javax.swin           g.Grou       pLayout.A                 lignme     nt.L   EADING) 
                                                   .ad            dGroup(jPanel1Layo  ut.createS           equentialGrou             p()
                                  .add    Gap(1      0, 10, 10)
                                                       .addComponent(Ans       wer3_rdbtn)
                                                 .a        ddPr   ef     er   re    dG ap(ja vax             .swin    g.LayoutStyle.        Compone    ntPlacem           ent.RELATED)
                                        .addCompone   nt(jS      crollP     ane4,     j avax.    swing.GroupL ayout       .PREFERRED_SIZE, 218, java  x.swing.Gro   u  pLay  out.PREFERR     ED_SI  ZE    ))
                                      .addGroup(jP  a   nel1Layout.createSeque   ntialGroup()
                                                  .a  ddGap    (99, 99, 99)
                                                       .a     ddCo     mpon     ent(Answer   3_lbl  , java    x.swing. GroupLayout.PREFERR  ED_SIZE, 55, javax        .s  wi  ng.Group  Layout.PREFER    RED_SIZE)
                                    .addGap(              1  9   6, 196, 196)
                                                      .addC        o      mponent(Answe      r4_lbl, javax.    swing.     Grou  pLay    out.P REFERRED_SIZE, 63  , j avax.sw  in  g.GroupLay out.PREFERRE   D_SIZE))
                                        .addGr     ou   p(jPa   nel1   Layout.createSeq     uentia     lG    rou        p  ()
                                  .addGap(162, 162,     162)
                                                  .addCom  pone      nt   (Ti   t   le_lbl, java     x  .   sw ing.GroupLayou t      .PRE   FERR     E           D_SIZE, 17                     3,     javax.swin g.   GroupLayout.PREFERRED_SIZE))
                               .add    Group(j P   anel1Layout.creat eSeq       uent  i  alGroup(         )
                                      .addGap( 93, 93, 93)
                                   .addGro  up(jPanel1     L   ayout.createPa     rallelGroup(java  x.swing.Group Layout.A      lign      ment.LEADIN      G)
                                                 .a    ddComp   o    nent(Ans  werDes_  lbl, javax.swing.       GroupLayout.           DE  F           AULT_SIZ E, j      avax.s   wing.Gr      oupLa     yout  .DEFAULT_SIZE, S   hort.MAX_VALUE )
                                                  .addGroup  (jPa   nel1Layout.                  createSequentialGr    o      up(   )
                                                 .a ddComponent(Ans  wer1_l   bl, javax         .swing.GroupLayout.PREFE  RRED_    S IZE, 64,   ja  va    x.swing.G   roupLayout         .PREFER   RED_SIZE)
                                                .addGap(172 ,       172,  172)
                                                           .addC      omponent(Answer2_lbl , javax.sw i  ng      .GroupLayout.PRE        FERRED_SIZE,  69, javax.sw   ing.G r            oupLayout.PREFERRED_SI       ZE)))))
                                     .ad       dG    ap( 84, 84, 84)))
                  .addGrou         p(jPanel1Lay      out.createSequ  entialGroup()       
                               .addGap(10, 10    , 10  )
                  .addCompone   n   t(Answe  r1_rdbtn)
                             .addPreferr   edGap(javax.swing.L    ayo     u tStyl      e.ComponentPlacem  ent.R    E LATED)
                             .addGroup(jPanel1Layout. c               rea teParallel                  G roup(javax.swing. GroupLayout.Alignme   nt.       LEADIN  G)
                                              .addComponent(jScrollPa  ne     1)
                    .addGroup(jP     anel1Lay ou  t.    createSequentialG   roup()
                                         .a    ddCompon     ent(jScrol   l     Pa ne2,           javax   .swi       ng.GroupLayout.    PREFER   RED_SI   ZE,  218, javax  .sw     ing.GroupLayout.PREFERRED_SIZE)
                                            .addPrefe   rredGap(jav          ax.swing.        Layout  Style.Compo      nentPlacem    ent.UNRELATED)         
                                       .addGroup  (j    Panel1Layout.cr  eatePar   a    llelGrou    p(javax.swing.GroupLa    yout.Align      ment.LE        ADING)
                                      .    addCom ponent(Answer4_   rdbtn, j avax.swing.GroupL  ayout.PREFERRED_ SIZE,      21, jav  ax.swing.GroupL       ayout.PREF         ERRED     _SIZE)
                                      .addCom     ponen   t(Ans wer2_rdbtn))
                                        .addPreferredGap(   javax    .swing.La      youtStyle.C     o  mponentPl        acemen  t.            RE    LATED)
                          .addGr    oup(j  Pa              ne     l1Layout.creat     eParallelGr    oup(j                 ava        x.  swing.GroupLayout.Alignm   ent.LE     ADING)  
                                            .addCompon        ent(jScrollPane5)
                                    .addCompon   e  nt    (jScrollP  ane3))))  
                            .addC  ontainerGap  ())
               .a      d   dGroup(j avax.swing.GroupLay  out.Alignment  .TRAI    LING, jP    a  nel1Lay    o ut.createSeq    ue      ntia  lGroup()
                         .addContainerGap       (javax.swing.GroupLayout.DEFAULT_SI ZE, Sh    ort.M AX_VALUE         )
                   .add   Component(Question_lbl, javax.swing.GroupLayout.PREF  ERRED_SIZE, 220   ,       javax.swing.GroupLayout.PREFERRED_SIZE)
                 .ad         dGap(133, 13  3, 13 3))
         )        ;
        j     Panel1L  ayout.se  tVerticalGroup(
                     jPanel1Layout .create          ParallelGroup(javax.sw ing.GroupLayou          t.Align    ment.    LEADING)
            .   a   ddGroup(jPa   nel1La    y out.cr        ea teSequentialGr      oup()
                    .a         ddGap(1     1, 11,   11)
                     .addCom            ponent(Title_lbl, javax.swing.Gro upLayo   ut.PREFE    RRED    _SIZE,        24, java     x.swin     g.Group      Layout.PREFERRED    _    SIZ  E)
                                           .  addPreferredGap(javax.s   wing.Layou    tStyle.ComponentPlaceme      nt.RELA  TED)   
                  .addCom ponent(Questi        on_lbl, javax.       swin    g.GroupLayout.PREF   ERRED     _SI  ZE, 19, javax.swing.GroupLa    yout.PREFERRED_SIZE)
                        .addGap      (23  ,         23,  23)
                           .addComponen     t(j    ScrollPane1, jav           ax.  swing.Gr  oupLayout.DEFAUL  T_SI ZE, 81      , S    hort.    MAX_VALUE)
                         .addGap(29, 29, 29)
                     .addC    o   mponent(An     swe    rD  es_lbl, java    x.swing.GroupLayout.     PR     EF E   RRED_S     IZE, 24, javax.swing.Gr    oupL ayout.PREFERRED   _SIZE)
                    .          addGap(6, 6,   6)
                       .addGroup(jPanel1Layout.createParallel   Group(ja   vax.swing.GroupLayout.Alignment          .LEA DING   )
                               .addC  ompone nt(Answer 1_lbl, ja   vax.swing.G               roupLayout.PREFE     RRED_S   IZE, 22, j avax.swi  ng .GroupLayout.PREFE   RRED_SIZE)
                              .addComp one  nt(Answe  r2_lbl, javax.sw       ing.   GroupLay     out.PRE     FERRED_SIZE, 20, javax.  swing.Group    Layou       t  .PREFER       RED_    SIZE    ))
                          .addGroup(jPan  el   1Layout.createParal        l    elGroup(javax.swin    g.GroupLayout.Alignment.LEA    DI  NG   )
                         .addGroup(jPanel1L   ayout.createSequentialGrou   p()
                               . addGap(47        , 47, 47)
                        .add   C    o    m  ponent(Answ      er2_rdbtn))
                     .         a ddGroup(j Panel1Layout.cre         ateSequen    ti           alGroup()
                                   .ad dGap(11 , 11, 11)
                         .a    ddGr  oup(j       Panel1Layout.creat     eParallelGroup(ja          v   ax.swing.G   roupLayout.  Alignment.LEADING)
                                              .ad dComponen      t (jS  crollPane2,   jav    ax.sw       ing.GroupLayout  .PREFE  RRED_    SIZE, 0, Sh   ort.MAX    _VALUE   )
                                         .    addGroup(jPane l  1   Layout.cr eat        eSequentialGro          up()
                                      .ad  dGap    (33, 33, 33)
                                        .addComponent(Ans  wer1_rdbtn)
                                                       .addGap(0, 37, Short.M   AX_VALUE))
                                     .addComponent(jScrol  lPane3, javax.swing.Group   Layout.PREFERRED_SIZ        E, 0, Short.MAX_VA  LUE))) )
                         .ad    dGap(6, 6, 6)
                .addGroup(jPanel1Layo  ut.createParal     lelGroup(javax.s  wing.GroupL  ayout.Alig  nmen  t.LEA DING)
                    .a   ddC    omponent(   Answer3    _lbl, javax.swing.Group       L  ayou     t.PREFERRED_SIZE, 22, javax.swing    .GroupL ayout.P    REFERRED_SI     ZE)
                          .addCompone   nt(An    swer4_lbl         , javax.swing.Gro     upLayout   .P  REFERRED   _S   IZE, 22,      javax.swing.GroupLayout.PREFER RED_SIZE))
                  .addGroup(jPanel1Layout.cr       eateParallelGroup(ja    vax.swing.GroupLa    yout.     Align ment.LEADING)
                       .ad    dGroup(javax.   swing.Gr    oupLay out.Alignmen     t.TRAILING,     jPanel1Layout.createSequ    entialGroup()
                                   .addPreferr    e    d   Gap(javax        .sw   ing.          La            yout     Style.ComponentPlacement.RELATED, 29, Short. MA      X_VALUE)
                                                 .addComponent(An        swer4_rdbt      n)
                        .addGap(60,   60, 60))
                        .add  Group(jP    anel1Lay  out.createSequentialGroup()
                                .   addGap(6, 6, 6)
                               .addGroup(jPane   l1Layout .crea   tePa  rallelGroup(ja  vax.swing.G     roupLayout.Al      ignment     .TRAILING)
                                  .a         ddGroup(javax.swing.Grou   pLayo    ut.Alignment.LEADING, jPanel1La    y      out.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .a ddCompon e  nt(Answer3_rdbtn)
                                    .addG   ap(0, 0, Short.MAX_VALU    E))
                                     .    a ddComponent(jScrollPa  n   e5  , javax.swing.GroupLay out.Alignment.LEADING, java   x.s  wing.GroupLayout.      PREFERRE  D_SIZ     E, 0, Short.MAX_VALUE)
                                 .addCompon        ent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_S  IZE, 0, Short.MAX_VALUE    ))
                            .addGap(18, 18, 18)))
                     .addComponent(Submit_btn)
                      . a ddGap(13, 13, 13))
        );

          ja   vax.swing.Grou pLayout   layout = new javax.swing.GroupLayo  ut(this);
          this.setLa yout(layout   );
              layo ut.setHor    izontalGroup         (
                layo    ut.createParallelGroup(javax.swing.Gro       upLayout.Alignment.    LEA    DING)
               .addGroup(javax.swing.GroupLayout                .A  lignment.TRA I LING, layout.createSequent    ialGroup(   )
                      .addContainerGap(66,   Short.MAX_VAL  UE)
                               .addComponent(jPanel1    , j    avax.swing   .GroupLayou    t.   PREFERRED_SIZE, javax.swin   g.Grou     pLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SI  ZE)
                       .addContainerGap(110, Short.M AX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayou    t.Alignment.LEAD  ING)
              .addComponent(jPanel1, javax.swing.GroupLa yout.DEFAULT_S    IZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)   
            );
    }// </e  dit     or-fold>//GEN-EN    D:initComponents

    private void An sw  er4_rdbtnActionPerformed(java.awt.even     t.ActionEvent evt) {//G     EN-FIR  ST:ev  ent   _Answer4_rdbtnActionPerf   ormed

           Answ er1  Correct = false;
        Answer2Correct = false  ;
        Answer3Correct = fa   lse;
               Answer4Correct = true;
    }//GEN-LAST:  event_Answer4_r     dbtnAc tion   Performed

    p      rivate void Ans wer3_rdbtnActionPerformed(       ja va.awt.event.Acti onE vent evt) {//GEN-FIRST:     event_Answer3_rdbtnActionPerformed

        Answer1C    o    rrect = false;
        Answer2Correc       t = fa   lse;
        Answer4Correct = f  alse;
        Answer3Correct =     true;
    }//GEN-     LAST:event_Answer3_rdbtnActionPerform    ed

    p   rivate void Answer2_rdbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Answer2_rdbtnActionPerformed

                    Answer1Correct =         fal se;
          Answer3Correct   =   false;
        Answer4Correct = fal   se;
               Answer2Corre     ct = true;
    }//GEN-LAST:event_Answer2_rdbtnActi   onPerformed

    private vo     id   Answer1_rdbtnAct  ionPerformed(java.awt.event.ActionEvent evt)          {//GEN-FIRST:event_Answer1  _rdbtnAct     ionPerformed
      
        Answer2Correct = false;
           Answer3 Correct  = false;
        Answer4Correct = fal  se;
        Answer1C       orrect = true   ;
    }//    GEN-LAST:event_Answer1_  rdbtnActionPerformed
    
    /**
     * @param args the command l ine arguments
     */
    public static void main(String ar  gs[]) 
    {
        /*   Set the  Nimbus  l          ook and feel */
        //<  editor-fold defaultstate="collaps   ed"   desc=" Look and feel setting       c   ode (optiona  l) ">
          /* If Nimbus (intr  oduced in Java SE 6) is not avai       lable, stay w   ith the default look and feel.
         * For details see http:/   /download.oracle.com/javase/tu    torial/uiswi    ng/lookandfeel/plaf.html 
         */
        try {
                for (javax.swing.UIManager.L   ookAndFeelInfo    info : javax.swing.UIManage     r.getInstalledLookAndF  eels()) {
                if ("Nimbus".equals(info.getName())) {
                         javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.       getLogg    er(CreateQuestionFRM.class.ge  tNa    me()).log(java.util.logging.Level.   SEVERE, null, ex    );
        } catch (InstantiationException ex)      {
            java.util.logging.Logger.getLogger(CreateQuestionFRM.class.getName())  .log(java.util.logging.Level.SEVERE, null, ex) ;
        } catch (IllegalAccessExceptio n ex) {
                 java.util.logging.Logg   er.getLogger(CreateQuestion  FRM.class.getName  ()).log(java.util.logging.Level.SEV  ERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelEx   ception       ex) {
                 java.util.logging.Logger.getLogger(CreateQuestionFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new  CreateQuestionFRM().setVisible(true);
             }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing .J    Label An    swer1_lbl;
    private javax.swing.JRadioButton Answer1_rdbtn;
    private javax.swing.JTextArea Answer1_txt    bx;  
    private ja   vax.swing.JLabel Answer2_lbl;
    private javax.swing.JRadioButton Answer2_rdbtn;
    private javax.swing.JTextArea An     swer2_txtbx;
    private javax.swing.JLabel Answer3_lbl;
    private javax.swing.JRadioButton Answer3_r    dbtn;
    private javax.swing.JTextArea Answer3_txtbx;
    private javax.swing.JLabel Answer4_lbl;
    private javax.swing.JRadioButton Answer4_rdbtn;
    private javax.swing.JTextArea Answer4_txtbx;
    private    javax.swing.JLabel AnswerDes_lbl;
    private javax.swing.JTextArea QuestionTxt_txtbx;
    private javax.swing.JLabel Question_lbl;
    private javax.swing.JButton Submit_btn;
    private javax.swing.JLabel Title_lbl;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
    
}


/*
        * To change this    license head  er, choose Lic  ense H    eaders in Project Properties.
 * To  change this templat        e f    ile, choose Tools  | Templates
 *     and open          the template   in t he editor.
 */
     
package Question;
import QuizApp.Core.Answer;
import QuizApp.Core.Question;
import Helper.DbAccess;
impor  t java.awt.*;
import java.    awt.event.*;
impor   t     jav          ax.swing.*;

/**
 *
 *     @author Gareth laptop
 */
public class C   reate          QuestionFRM      ext    ends   javax.swing.JFrame im plements        Actio              nList       ener
{

       public String      questi     onText;
    public String Answer1Text;
    public Strin  g Answer2Text;
       publ ic     String  A     nswer3Text;
       public Str  ing   Answer   4Text;
    public boolean     A   nswer1Correc t         = false     ;
    public boolean Ans  w   er 2Correct = false;
           pub lic boolean Answe    r3     Correct = fa   lse;    
    publ       ic boo         lean An  swer4Correct       =    fal  se;
    
      /          *  *
         * Creates new form CreateQuestion
     */
    public CreateQ  u  estionFRM() 
           {
        ini tCompon             e   nt             s(     );
               
        Submit_b          tn. addActionListe    ne    r( this );
                 Ans  wer1_rdbtn.addAct    ionLi  sten       er( this );
          Answe  r2_rdbtn.addAct  ionListener( thi s   );
                 Answe        r3_rd        bt        n.   addAct          ionL  iste   ner(    thi   s       );
                          Answer4_   rdb      tn.    ad    dAc    tionList ener(     this );
            
    }
         
    @O  verrid      e
           pub          lic   voi d actionPer          for  m  ed(Acti   onEvent evt)
    {
       
                if(evt.g   etSource().  equa ls(S      u                 bm     it_btn))
        {
               if  (An    swer1Correct || Answer  2Corr     ect                     || Answer      3Correc       t ||  Answer    4Correct)
               {
                //      BUTTON    CODE  HER       E 
                              this.CreateQue           s   t  ion                  ()   ;

            }    
            else
                     {
                     
                                 // M           essage box pop u  p to say plea se select c     orre  ct answer                 
                                JO     p   tionPane.s     howMes           sageDialog(nul l,"Please indicate correct  answ e  r.","Error",J   O       p   t  i     on  Pane.WARNIN     G_MESS    AGE);
                
             }      
             }
                   
    }
    
    public void CreateQue   st     ion   ()
                    {
        
                        // Set Values
         questionT   ext = Questio        n  Txt_txtbx.getT   ext();
                           Answer1Text = Answe    r1      _txtbx.  getText(   );
                 Answer2T ext = Answer2_txtbx.g  et           Te            xt();
             Answer3Text = Answer3_txtbx.getText()   ;
        An swer4Text = An swe  r4        _txtbx.   getText();
            
        /    /    Cr  eate que sti        o   n
             Question q     = new Question();
           q.Auth  orId = 1341131;
        q.isRejected = false;
        q.questionText = questionText;
        q.answers.add(    new Answer(An s   wer1T ext, Answer1Correct          )); 
        q.answers.ad    d(new Answer           (Answer2Text, Answer  2Correct));
        q.ans   wers.add(   new An      s wer(A   nswer3Tex  t, Ans  we   r3Correct));
        q.answers.add(new Answer(Answer4Text, Answer4Correct));   
         
        // Update   da           tabase
            DbAccess.StoreNewQuestion(q);
   
    }
    
    /**   
     *    This method is ca   l       led from within the  co  nstructor   t o in   itialize the form.
     *  WARNING:     Do NO    T     modify this  code. Th   e content of this method is always
     * regenerated     by the Form E ditor.
           */
     @Sup   p    ressWarnings("unc   hecked")
    // <editor-       fo      l     d defaultstate="            collapsed"             de    sc="G     enerated Code">//GE   N  -BEGIN:initComponents
    priva   te void in     itComp  one  nts() {

        AnswerGroup = new j     a     vax.swing.Bu   ttonGr  oup();
         Submit_btn =   new j avax.swing    .JBut ton(     )    ;
        Titl    e                 _lbl      = new javax.swing.JLabel();
          j   Scroll Pane1 = new javax.swing.JScr           ollP     ane(); 
          Questi o nTxt_t xtbx = new javax.swing.JTextArea();
                           Question     _lbl = new javax.swing.JLa      b el();
        Answ     erDes_lbl = new javax.swing.JLabel();
           Answe     r1_lbl = ne w     javax.swing   .JLab   el  ();
        jScrollP  ane2 = n  ew java x.swing.JS  crollPane();     
        A    nswer1_txtbx = new javax.swing.JTextAr     ea();
           jScrollPane3 = new jav   ax.swing.JScrollPane(  );
          Answer2_txtbx = new javax    .swi ng.JTextArea()   ;
        Answe              r          2_lbl     = new javax.swing.JLabel();
        Answer3_lbl = ne   w javax.swing.JLabel();
        jSc   r          oll   P   ane4 = ne    w     javax.      swing.JScrollPan   e();
           Answer3_txtbx = new javax.sw    ing.JTextArea();
        Answer4_lbl = new   ja    v ax.swing.JLabel();
        jScrol  lPane5 = new javax.swing.JScro   llPane()    ;
        Ans    wer4_txtbx =       new javax.swing.JTextArea();
            Answer1_rdb        tn = new java       x.swing.        JRadioButton();
            Answer2_r     dbtn = new javax.swing.JRadioBut  t    o  n();
        Answer3_rdbtn = new javax.sw  ing.JRadioButton();
        Answer4_rdbtn = new javax.sw  ing.JR           adioButton();

        setDefaultClo    seOperation(javax.swing.Window      Constants.EXIT_ON_CLOSE);
        setTitle("Create      Question");  

          Su  bmit_btn.s      etTe     xt("Submit");

        Title     _lbl.setFont(new java.awt.Font("  T  ahoma", 1, 16)    );     /     / NOI18N
        T   itle_lbl.set   Ho   rizontal   Alignment(javax.swing.SwingConstants.CENTER);
          Title_lbl.setText("Create     Question");

        Qu  estionTxt_txtbx    .setColumns(20);
                 QuestionTxt_txtbx.setRows     (5);
        jScr  ollPane1.  setViewpor      tView(Ques     tionTxt_txtbx);

        Q     ues tion     _lbl.setHorizo    ntalAlig        nment(javax.sw       i  ng.SwingConstants.CENTER);
          Quest   ion_  lbl.setText("     Please enter   question below:-")   ;

        Answe    rDes _l   bl.setHorizontalAl        ignme    nt(javax.swi   n      g.SwingC  ons tan    ts.C   ENTER);
              AnswerD    es_lbl.setTex     t("Please enter answers and ind icate correct answer below:-" );
  
          Answer1_lbl.setHorizontalAlignment(java x.sw      ing.  Swing   Constants. CENT           ER);  
        A nswer1  _lbl.setText("Ans wer 1  ");

        Answer1_txt bx.setC   olumns(20);
              Answe r    1_txtb     x    .setLineWrap(tru         e);
          An sw   e  r1_txtbx   .setRows(5);
        j    ScrollPane2.setVie       w     po rtView(Ans     wer1_txtbx);

            Answer2_txtbx.setColumns(20);
          Answer2_txtb x.s  etRows(5);
           jS    cr  ollPane3.set         Viewp      or   tView(Answer    2_  txtbx);

        Answer2_lbl.setHoriz ontalAl   ignment(java   x.swing   .Sw  ingConstants.CENTER);
          Answer2_lbl.s    et    Text("Answer 2");

        Answer3_                lbl.setHorizontal A   lignm    ent(     jav   ax.swing.SwingConstants.CENTER);
                   Answer3_lbl.s    etTex     t("An        swer 3")     ;
         A  nswer3_lb   l.    setHorizon                        talTex tPosition(        javax.swing.Swi    ngConstan    ts.RIG HT     );

         Answer3_t       xtbx.setColumn s(20);
                      Answer3_t x tbx.se tR        ows(5)    ;
        jScrollPane4.setViewport    View(Answer3_txt    bx);

        Answer4_lbl.set       H  orizontalAlig     nment(javax.swing  .SwingConstant    s.CEN        TER);
            Answ        er4_lbl.se   tT  ext("A nswe          r 4");

        Answer    4_  txtbx.setColumns(20     );
           Answer4_txtbx.setRows  (5);
        jScrollPan  e     5.        setViewportView(Answer4_txtbx);

        A      nswerGroup.a  dd(Answer1_r    d          btn);
         Answer1_r        dbtn.setToo    lTipText(  "" );
            Answer1 _rdbtn.addActionListener(new jav      a.a     wt.event.A   ctionLi              stene   r()       {
            public void actionPerformed(java.awt.event  .       A  ctionEvent ev       t)  {
                       Ans wer 1_r      dbtnActionP   erfo        rme d    (evt             );
             }
        }     )   ;

         Answ erGroup.ad    d(An swer2_rdbtn)     ;
              Answer2       _r  dbtn.addActio    n            Listener(new java.awt.e      vent.A     c tionList        ener()     {
                         public vo id    ac    tion  Performed(  java.awt.event. ActionEvent evt) {
                 Ans   wer2_rdbtn   Act  ionP        erformed(e    vt) ;
                           }
              });

                        A    nswerGroup.add       (Answe   r3_rdbtn);
               Ans w                er3   _ rdbtn    .ad  dAct     ionL is  tener(new         java  .   aw t.eve     n    t.A  ctionListener() {  
            public vo        id            actionPerf  or   med(ja va.awt.event.ActionEvent evt)   {
                Answer3_rdbt   n  ActionPe           rfo       rmed(evt);
            }
                }   );    

              Ans w          erGr  oup.add(  Answer4_rdbtn );   
               Ans  w  er4_rdbtn.addActio  nLi       stener(new java.awt.event.Action    List e ner() {
            public void ac  tionPerformed(java.aw         t.e           vent.Action    Ev   ent evt) {
                    A      nswer4_rdbtn            ActionPerformed  (evt     );
               }
                          });

        j    avax.       s  wing.GroupLa           yout l ayout =           new javax.s      wing.GroupL     ay   out(getCon     tent   Pane ());
                  get ContentPane().setLa yout(layout);
                    layout.setHorizontalG     roup(
            layout.crea tePar  allelGroup(ja  va       x.swin      g.G   roupL  ayout.Alignm e nt.LE    ADING)
              .addGrou    p(    lay  out.cr  eateSeq       uentialGro     up()
                                           .     addContain        erGap(176     , Short.MAX_VALUE)
                     .add      Group(layo ut.createParallelGroup         (java  x  .s           wing.Gro  upL  ayo ut  .Alignmen t.LEADING    )
                                          .add Gro  up(javax.s  wing.GroupLayo       u t.Alignmen t.TRAI   LING, layou  t.c          reateSequ    entialGroup()
                                                 .addGro   up(layout.createParal l        elGroup              (javax.swing.GroupLayo     ut.Al i gnment.T    RAILI NG)     
                                       .a d   dComponent(Qu   e    s   t     ion_lbl, jav ax .swi    n   g.    GroupL     ayout           .PREFERR   ED_S      I   ZE  , 220,     javax.sw  ing.G roupLayout.           PR        EFERRED_SIZE   )
                                              .addGrou   p(layout.c  re   ateSequentialGro        up()     
                                              .addComponent(Title_lbl, j  avax.swing.  Group     Layout.   PREFERRED_SIZE,    1   73, j   avax  .sw      ing.GroupLayout.PRE   FERRED_SIZ     E)
                                        .addGap(35, 35, 35)))
                                                   .addGap(122, 12    2, 122))
                                                 .addG     roup(       javax.swing.Gro upLayo  ut.Al          ig nme   nt.    TRAILING, l      ayout.c      rea  teSequ       e     ntialGr  oup(        )
                                             .addGr      oup(l                     ayout.c   reate    Parall   elGroup  (javax.swi   ng.GroupL  ay  out.Alignme                nt     .TR    AILING)
                                          .ad   dGr  oup(l   ayout.c   reate       SequentialGroup()
                                             .       addCompone   nt(Answer   3_rdbtn)      
                                                .addPreferredGap(    javax.   s    wing.Lay  outStyle.C omponentPlacement.RELATED)
                                            .addCompone            nt(jS    cr   ollP       ane4, j   av      ax.sw  ing.Grou          pLa   yout.PREFERRED    _S   IZE, 183, jav a     x.swi ng.GroupLa  yout.PREFE       RRED_SIZ E))
                                                            .addGrou       p          (layout   .cr   eate     S   equent    ialGroup()
                                                .ad      dComp   onent(   Answer1_rdb        tn)
                                                              .a        ddPr   eferr   edGap(javax     .swing  .L    ayoutStyl e.Compone       ntPla   ce  me  nt.RELATED)
                                        .    addComponent(jScrollP   ane2, javax            .swing    .GroupLa  yo            ut.PR      EFERRED_SIZE, 183, javax      .swing   .GroupLayout.  PREFE        RR   ED_SIZ          E)))     
                                                       .addGap(11, 11,      11  )
                                    .   addGroup(    layout.   createParallelGro    up(java    x.swi ng.GroupLayout.A  lignment.TRAILING)
                               .a       ddCompon     ent(An      swer2_rd    bt   n)
                                 .ad  dCompon    ent(Answer4_rdbt n))
                                   .addPreferredGap(javax    .swing.LayoutStyle.Componen    tPlaceme nt  .UNRE  LATED)
                                                                           .addGroup(layo ut.createPa           rallelGro   up(java x.swing.GroupLa      yout.Alignment.LE   ADING   , f  al   se   )
                                            .addComp   onent(jScrollPane3,    ja      vax.swi   ng .GroupLa yout.D EFAULT_SIZE, 183,    S h ort.MAX_VALUE    )
                                .addComponent(jScrollP a   ne5))
                                      .ad dContainerGap( ))
                         .       addGr   ou    p(javax.swing       .GroupLayo   ut.Ali        gnme  nt.TRAIL      I  NG, layout.cr  ea  te    Sequenti  alGrou p()
                             .ad         d C  ompone     nt(   jSc  rol    lPane1    , javax.swing.Group    La   you     t.PREFERRED_   SIZE, 390, javax.swi      ng.Gr       oupLayout.PREFE    RRED_SIZE)
                               .addG  ap(37, 3     7 , 37))
                           .addG   roup(javax.swing.GroupLayo   ut.Al   ignment.    T    R   AILING     , layou   t.createSequ entialGr o     up()
                           .add   Comp on         ent(    Su        bmit_b    tn, javax.sw    in  g  .Group     Layout.PREFER  RED    _SIZE,    73, jav ax.s wing.GroupLayout         .PREFERRED_SIZE)
                                 .addG      ap(182, 182, 182 ))  
                                        .addG  roup   (javax.swing.GroupLayout.Alignmen   t.TRAILING, la        y ou  t.createSequentialGro    up()
                                          .addGroup(la    yout.createPara   llel  Gro  up(    ja    vax.swing        .G roup      Layout.          Alignment.TR    AILI  NG)
                                                 .addGroup(layo  u    t.cr    eateS  equentia      lGroup()  
                                           .add      Compon        e   nt(   Answer3_lbl, javax.swin      g.GroupL    ayout.PREFERRED_SIZ      E, 55, javax.swing.GroupLa  yout.PREF         ERRE        D_S           IZE)
                                                     .addG      ap(16                  1, 161,      161)
                                             .add   Component(Answer4_   lbl, java            x.swing.GroupLayout    .PREFERR  ED_SIZE, 63, javax.swing   .GroupLayout.PREFERRED_SIZE))
                                     .addCom        po   nent(Answ    erDe        s_lbl,               j   av ax         .swing.Gr  oupLayout.PREFERRED_SIZE, 309,     j   avax.swing.GroupLayout.    PREFERRED_SIZ  E)
                               .addGroup(layout.cr  eate  S         equentialGro       up()
                                  .addComponent(Answer  1_l      bl, javax   .swi      ng.GroupLa    yout.PREFERRED_SIZE , 64, javax.   swi           ng.GroupLayout.PREFERRE        D_SIZE)
                                           .addGap   (152, 152,           152)   
                                                    .addCompo         ne     nt(Answer2_lbl, javax.    swing.   G ro upLayout.PREFERR    ED_SIZE  , 69, ja     vax   .swing   .G    roupLayout.  PREF               E     RRED  _SI  ZE)))
                                  .  addGap(70, 70   , 7      0)                 )))
              );
           layout  .set        VerticalGroup(
                  lay    out.c        rea       teParallelG    roup(javax  .swing.GroupLayout.     Ali gnme    nt       .LE   ADIN      G)  
                    .addGro  up(javax.swin g. GroupLayout.Alignme  nt.T    RAILING, layout.crea     t    eSequentialGr  o  up   ()
                                 .a    d dGap(15,       15, 15  )
                       .a  ddComponent(Title_l     b  l, javax.swin  g  .GroupLayout.PR EFER    RED_SIZE, 24, javax.swi  n      g.GroupLayout .PREFE  R     RED_SIZE)  
                         .a    ddGap(18, 18, 18)
                           .addC            omponen   t (Question_lbl, javax.swing  .GroupLayout.PREFERRED_SIZ  E, 19,         java      x.swi     ng.GroupLayo     ut   .PRE  FERRED_SIZE)
                          .a  ddP       refe     rredGap(javax.swi  ng.  Layout    Style.ComponentPlacement.UNR   ELATE     D)
                        .addCo        mponent(jScrollPane1,   java  x   .        swing.GroupLayout.PREFER  RE   D_SIZE             , 81, ja   vax   .swing.GroupLayout.PREFERR   ED_SIZE)
                                      .addGap(    29,       29, 2 9)
                  .addComponent(A   n  sw erDes_l   bl,      j     avax.swing.GroupLayout.P  R   EFER     RE               D_SIZ     E , 2       4, ja     vax.swing.GroupLayo      ut.PREFERRED_SIZE    )
                      .addP     ref   erredGap(ja     vax.sw          ing.L      ay      outStyle.ComponentPlac    ement.RELA   TED)
                     .addGroup(layo      ut.creat eP   ar     allelG    roup(javax.sw  ing  .G   r  oupLayout.Al   ignm  en      t.B  AS   ELINE)
                    .a              ddC     omponent(Ans    wer1_lbl, javax.sw   i    ng.G    roupLayout.PREFER       RED_SIZE, 2  2,           java  x.swing   .Grou            p      L  ay    o  ut   .PR    EF   ERRED_SIZE  )    
                                         . addCom    ponen  t(Answer2_lbl,       jav    ax.sw          ing         .Group  La   yo         ut                 .P RE FE R      RED_S   IZE,     20  ,       j    avax.swing.GroupLayo   ut.PREFERRED_SIZE))
                           .ad        dGr     oup(layout.create   Paral         lelGro     up(ja v    a                x.swing.GroupLayout     . Ali gnme  nt.LEADING)
                                    .addGroup               (ja va      x.swing.Grou p     Layout.A  lignme       nt.TRAILING, lay   out.createS     equentialGroup()
                                      .addPreferredGap(javax.swing.LayoutStyle.Co mponentPla          ce    ment   .RELATED) 
                                     .add   Co    mpon ent(Answer  1  _rdbtn)
                           .ad      dGap(40, 40   ,       40))
                          .add            Group(layout.createS equentialGroup()
                                     .addGrou  p(layout.createParalle        lGroup(javax.s wing.Gr  oup Layout   .Align men  t.L     EAD        ING)
                                          .addGr         oup(la    you           t.createSe      quentialGroup  ()
                                .addGap(53, 53   , 53)    
                                              .   addComponent(Answer2_ rd  bt   n))
                                             .addGr    oup(layo  ut.createSequentialGroup()
                                                        .addGap(18, 18   , 18)
                                          .addGr   oup(layout.createParallelGroup(javax     .sw  ing.GroupL   ayout.A  li    gn       me    nt. LE  ADING, fa  lse)
                                                       .addComponent(jScrollPane3, javax.      swing.GroupLayout.DE         FA        ULT_SIZE, 91, Short.MAX_VA    LUE)
                                              .add    Component(jScrollPane2    , javax      .s    wing.GroupLa   yout.PR   EF   ERR   ED_SIZE, 0,   Short  .    MAX_VA  LUE   ))   ))
                               .addPreferredGap(ja   va    x    .sw  ing .    LayoutStyle.Com   ponent  Placement.RELATED)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLa       yout.Align   ment.LEADING)
                      .addComponent(Answe  r3_lbl, java   x.swing.GroupL   ayout.Alignm  ent.TRAILING, j av   a  x.s        wi    ng   .GroupLayou  t.PREFERRED_SIZE,      22, javax.sw   ing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Answe r4_lbl, javax.swing.Gr oupLayout.PRE     FERRED_SIZE,   22, javax.swing.GroupLayout.PREFE      RRE  D_SIZE))
                    .addP    referredGap(javax  .swing.L  ayoutStyle.C   omponentPlace          ment.RELA      TED)
                        .addGroup(layout.  cre ateP   arallel Gro      up(javax.swing.Gr                 oupLayout   .Alignmen  t      .LEADI    NG)
                    .addGroup(layout.createS  equentialGroup()
                             .add Group(la        you  t.createParallelGroup(javax.s   wing.GroupLayout.  Alignment.LEAD      ING)
                                .addGro up(layout.c        reateSequentialGrou p()
                                        .ad  dGap(31, 31, 31)
                                                        .addComponent(Answer3_rdbtn))
                             .addGroup(layout.  createSequentialGroup()
                                                 .addGap(27  , 27     , 27)
                                                 .addCompone  nt(  Answer4_rdbtn)))
                              .addGap(57,     57, 57)
                               .addCompo     nent    (Submi    t_btn))      
                    .addGroup(layout.createP   ara           llelGroup(javax.swing.GroupLayo      ut    .Alignment.LEADING, false)
                           .addCom    ponent(jScr ollPan e    4, javax     .swing.GroupLayout.DEF  AULT_SIZE, 91, Short.   MAX_VA      LUE)
                         .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Shor  t.MAX_VALUE)))
                .addContainerGap(javax.   swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          );

            pack()    ;
    }// </editor-f      old>//GEN-END:initComp   onents

    priva   te void Answer1_rdbtnActionPerformed(java.awt.  event.ActionE    vent evt)    {//GEN-FIRST:event_  A nswer1_rdbtnActionPerform            ed

            A    nswer2Correct = false;
            Answer3Correct = false;
            Ans     wer4C   orrect = fa     lse;
             Answer1Correct =           true;

    }//GEN-LAST:event_Answer1_rdb   tnAc       ti o nPerformed

          private void Answer3_rd  btnActionP   erfo     rmed(java.awt.event.ActionEv ent evt) {//GEN-FI  RST:event_Answer3_rdbtnActionPerformed

                     Answer1Cor    rect = fal  se;
            Answer2Correct = false;
            Answ  er4C     orrect = false;
                Answer3Correct = tr             ue;
 
    }//    GEN-LA ST:even     t_Answer3 _rdbt  nActionPerformed

    private void A nswer2_rdbtnActionPerforme d(java.awt.event   .ActionEvent evt) {//GEN-FIRST:event  _Answer2_rdbtnActionPerformed
         
                Answer  1Correct =          false;
            Answer3Cor   rect = false;
            Answer4Correct = false;
            Answer2Correct =    true;
                  
    }//            GEN-     LAST:event_Answer2_rdbtnA     ctionPerformed

       priv   ate void Answer4_rdbtnActionPerformed(java.  awt.e  vent.Ac    tionEvent evt) {//GEN-FIRST:e  vent_Answer4_rdbtnActionPer     formed

            Answer1Correct = false;
                     Answe     r2Corr    ect =      false;
                  Answe    r    3Corre     ct = false;
              Answer4Correct       = true;
          
        }//GEN       -LAST:ev    ent_Answer4_rdbtnActionPerformed

       /**
     * @param args the comman d l    ine arguments
     */
    public static void main(String args[])   {
        /* Set the Ni   mbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look an  d feel setting c ode (optional) ">
        /* If Nimbus (in           troduced in Java S  E 6) is not a  vailable, stay         with the default look and feel.
         * For details see http://downloa    d.oracle.com/javase/tutorial/uiswing/lookandf   eel/plaf.html 
         */
        try {
            for (jav       ax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInsta   lledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndF eel(info.getClassName());
                          break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateQuestionFRM .class.getName()).log(java.util.logging.Level.SEVERE,     null, ex);
        } catch (Inst  antiat  ionException ex) {
                java.util.logging.Logger.getLogger(CreateQuestionFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessEx   ception  ex) {
            java.util.logging.Logger.getLogger(CreateQuestionF  RM.   class.get  Name()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swin       g.Unsupporte  dLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateQuestionFRM.class.getName()).log(java.util.logging.Level.SEVERE, nu   ll, ex);
        }
         //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            pu  blic void run() {
                new CreateQuestionFRM().setVisible(true);
            }
              });
    }

    // Variables declaration - do not modify//    GEN-BEGIN:variables
    private javax.swing.JLab el Answer1_lbl;
    private javax.swing.JRadioButton Answer1_rdbtn;
    private javax.swing.JTextArea Answer1_txtbx;
    private javax.swing.J  La   bel Answer2_lbl;
    private javax.swing.JRadioButton Answer2_rdbtn;
    private javax.swing.JTextArea Answer2_txtbx;
    private javax.swing.JLabel Answer3_lbl;
    private javax.swing.JRadioButton Answer3_rdbtn;
    private javax.swing.JTextArea Answer3_txtbx;
    pri    vate javax.swing.JLabel Answer4_lbl;
    private javax.swing.JRadioButton Answer4_rdbtn;
    private javax.swing.JTextArea Answer4_txtbx;
    private javax.swing    .JLabel AnswerDes_lbl;
    private javax.swing.ButtonGroup AnswerGroup;
    private javax.swing.JTextArea QuestionTxt_txtbx;
    private javax.swing.JLabel Question_lbl;
    private javax.swing.JButton Submit_btn;
    private javax.swing.JLabel Title_lbl;
      private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    pri   vate javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}

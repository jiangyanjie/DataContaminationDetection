/*
 * To change    this  licens    e header       , choose Lic  ense Headers in Pro        ject    Properties.
 *      To change        this te            mplate   file, c     hoos    e Tools | Temp     lates
 *      and open the templa       te in the editor.
 */

package TitanMusicPlayerGUI;

import TitanMusicPlayer.bll.Account;
 impo  rt java.util.regex.Matcher;
import java.util.regex.Pattern;
import ja   vax.swing.JOpt    ionPane;

/**
 *
 * @author AJ     Green        <ajgreenma     il@gmail.com>
 */
pu    bli    c     c la              ss  Create  Account extends javax.swing.JFrame {

    p   ublic Ac     count account   = null;

    /**
     * Th is me thod   is called from with  in the con   str   ucto   r to  initialize th        e form.
           * WARNING: Do NOT modify this code.    The content of     this meth      od is al  ways
     *  rege      nerated by the           Form Edit  or.
     */
    @Suppre  ss   Wa    rnings("unchecked")
    //    <edi t    or-f     old defaultsta  te="col      lapse  d"    de     sc="Generated Code">//GEN-  BE   GIN:    initCompo    n      en  ts
    private    void initCompone   nts()           {

             usernameLabel = new java    x.swing.JLabel(         );
             ema   ilLab   el =       new j    avax.s   wing.JLabel();
                    p      as                swo    rdLabel = new javax.swing.JLab   el();
                  usernameField   = ne   w javax.swing.JTe xtField();
        emailField = new javax  .swing.JTextFi  eld();    
           passw   ordField          = new javax.  swing.JTe  xt    Field(); 
        jS    cr  ollPane1    = new javax.swing.J    Sc    rollPane();
        directionsArea = ne   w javax.swing.JTextArea();
           usernameEr    ror = new javax.swing.JLa b            el();
                  emailErro    r = new   javax.swing.JLabe           l();
                 passwordError1 =      new javax.swing.JLabe      l( );
        passwordError2       = new javax.swin g.   JLabel();
            create      B   utton = ne     w javax.swing.JBut  ton();
        cancelBu tton = ne  w java     x.    swing.JButton();

          s      etDefaultCloseOperation(javax   .s    win   g.WindowConstants.EXIT_ON  _    CLOSE);
         setTitle("Cr       eate New User");
        setMini  mumSize(new     java.awt.Dimensio         n(282, 400))  ;
        setResizable(false);

                      usernameLabel  .setText("Username:");

        emailLabel.set   Text("Email:");

        passwordLabel.setText(  "          Password:")  ;

           usernameField.setAutoscr olls(f alse);

               d irectionsArea.setEditable(fals    e);
            d     irec   tionsArea.setC     olumns(20);
        directionsAre   a.    setLi   neWrap(true)  ;
        directionsArea.setRows(5);
        directionsArea    .setText("Please ent    er   a user n       ame, email,    and password.\nThe email must be valid.\nThe password needs to  ha   ve at leas     t   one each of   : let  ter,   number, and special character");            
               directionsArea.set    W   r  apStyleWor           d(t  rue)      ;
        jScrollPane1.  se      tView   portView(directionsArea);

            userna  meError            .setF    oreground(new ja          va.awt.Color(   255     , 0, 0));
        username    Er     ror.setTe    xt(     "Use     rn  ame      required."); 

        email   Error.s              etFo  reground(n  ew    java.a wt.Color(255,   0, 0));
        emailError.setText("Em ail not valid. ");

        pa  sswor      dError1.setFo  reground(new java.awt.Color(255,    0,    0));
                              passwordError1.setTex     t("Pa   ssword must contain one letter,");

            passwordError2.setForegr    ound(new java.a  wt.C o      lor(255,   0, 0));
          passw    ordError      2.setTe  xt("one n    umber and     one spe   cial      character.");

               createButton.setTe    xt("CREATE");
         crea         teButton.addActi    onLis te    ner(new java.awt.event  .Actio  nListen  er     () {
                        p        ublic   void actionPerfor            med  (ja  v     a .awt.e    ven                  t.ActionEv  ent e    vt) {
                        create But    tonActionP  e     rfor    med(e   vt);
                        }          
           });

                  canc     elButton.se   tText("CAN         CEL");
                c     ancelButt  o n.     a  ddActionListe  ner(new java.awt.eve     nt.ActionListe        ner() {
             public     void ac  tionPe     rforme   d(   java.awt.   event.Ac tionEvent evt)   {
                   cancelButtonA   ctionPerformed  (ev             t);
                     }
                       }          );

                            java    x.swing.G    rou   pLayou   t   layout      = new    jav        a  x   .swing.G  ro      upL   ayout(getConte  nt           Pane());
            getC on            tentPane().se  tLa  yout(layout);              
        layout.s                 e   tHo      rizon              talGr    ou   p  (
            layo  ut.createPar   a llelGrou  p(ja           vax.swing.GroupLa    yo             ut.Alignmen   t.    LEAD  ING       )     
                             .addG roup    (lay  out.cre at  eSequent       ia  lGrou              p( )
                   .addGroup(l   ayout. cr   eateP          arallelGroup(  j                   a            vax.s   wing.Gro   upLa  yo   ut.Alignment      .L       EADING)
                                                                  .addGro  up(  layo ut.createSequentialGroup()
                             .        addGap   (42, 42, 42)
                                                                    .  addComponen        t(createButton)
                                                                     .ad   d   Gap(     30 , 30, 30)
                                        .a   ddC   o       mponent(cancelBut   t    o n))  
                                                        .    addGroup(layo   ut.crea       te   Se  quentialGroup()
                                          .addGap  (2             0, 20 ,    20)
                                                        .addCom   pon en   t(emailError))
                             .ad           dGro    up(layout.cre  ateSe     quentialGroup(     )
                                      .add     ContainerGap()      
                                                                   .         addGrou       p(la    yout    .createPar  allelGroup(    javax.swing.    GroupLa      y  out.Align     m     ent     .  LEADING)
                                              .addGroup(layout.createS   equenti alGroup()
                                                             .addGap(10, 1             0,                     10)
                                                                                  .ad     dCom    ponent(usernameError)      )   
                                                   .addGr  oup(lay        out.crea  t           ePa rall       elGro     up(javax.swing.GroupLay  out.A      l   ign  ment.TR  AILI       NG, fal    se   )
                                                   .addGroup(l  ayout.cr     eateSe      qu     e               nti     a  lG      ro    up()
                                                                     .addGroup(l ayout.createParallelGroup(j                avax      .sw   ing.GroupLayout.     Alig      nmen   t   .T              RAILI    NG)
                                                                                     .addComponent(           e    mailLabel)
                                                                                           .addCo      mponent(pass         wordLabel))
                                                    .addPreferre        dGa   p(j avax.swin  g    .      LayoutSt   yl    e.ComponentPlacement.RELAT  ED   )
                                                          .addGr oup(  layout.     cre   ateP   arallelGroup(ja     va  x.sw  in    g.Gr      ou p    Layout.Alignment.LEADING)
                                                                        .ad dCom  p o           ne nt(passwordF  ield)
                                                                 .addCompo  nent(em   ailField)))
                                              .ad   d      Compon   ent(   jScrollPane1,  javax.s  wing.  Gro    u   pLayout.DEFA U     LT_SIZE, 262, S hort.MA          X_VALUE)
                                                                 .a      ddG     roup(   ja  vax  . swing.Grou  pLayout.Alignment.LEA  DING, l  ayout.c   reateSeque      n tialG  roup()
                                                         .addC      ompo         nent(usernameLa    bel )
                                                                          .    a    d     dPreferredGap(java              x.swing.L       a youtSt  yle    .ComponentPlacem   e  nt.    RELA  TE D)
                                                                 .a      ddCo mponent(us      ernameField))
                                                .a  ddGroup(j    av  ax.swing.GroupLayou   t.Al     ignment    .LEADING,   la     yout.  cr    ea  t   eSequentialGroup()
                                     .addGa     p(10,    10, 1   0)
                                        .add    Group(la   yout.createPar        allelG  roup(jav ax.swing.GroupLa      yout.   A      lignmen         t.LEADING)
                                                                        .addCo    mponent(passwordE   rror      2)
                                                                              .addC     omponent(passwordErro    r     1)))))))
                         .addContainerGap(j  avax.swing.GroupL      ayout.DEFAULT_SIZE, Short.MAX_VALUE))
          );
              l ayout.set  Vert    icalGroup(
               l  ayout.createParallelGro       up(javax.sw          ing.GroupLayout.Al      ignment.LEADING)
            .     addGroup(layout.cr    eateSequentialGroup()
                  .a   ddContainerG   ap()
                     .ad      dG  roup(layout.cr  ea    te    Pa    ral    lelG      roup(javax.swin    g.Group      Layout.Al        ignment.          BAS  E  LINE)
                         .ad     d  Com   ponent(usernameLabel   )
                      .addComponent(usernameField, j      avax.swing.GroupLayout.PREFERRED              _SIZE, javax .s wing.GroupLayout.DEFAULT_SI   ZE, javax.swing.GroupLay  ou   t.PRE FE   RRED_SIZ E))
                    .addGap(                    1, 1,           1)
                .addCo    mponent(usernam eError)
                      . a           ddPrefer      redGap(javax.sw  ing.  LayoutStyle.Compon       entPlacement.UNRELATED)    
                     .addG    roup(layout   .createPar   allelGro     up(javax.swing.Gro     up Layout.Alignm  ent.BASE    LIN  E)
                    .add    Component   (emailF      ield, javax.  swing.G roupLa       yout.PREFERRED_SIZE, javax.swing    .GroupLayout.D  EFAULT_SIZE,   javax.swing.GroupLayo  u   t.P          REFERRED_SIZE)
                      .addComponent   (emailLabel)  ) 
                .addPref      e rredGap(jav         ax.swing.L     ayoutStyle  .Compon        entPlacemen       t.R      ELATED)  
                 .addComponent(emailError)
                         .a  ddPreferredGap(ja   vax.swing.Layo         utStyle.Com    ponentPlacement   .RELAT        ED)
                             .addGroup( l   ayout.createParallel  Gr       oup(   javax  .swing.Grou      pLayou    t.A   li              gnment.B    ASELINE)   
                                          .addComponent(passwordField, j  avax.   swing.GroupL    ayout.PREFERRED_SIZE, j  ava    x.s      wing.Gr    oupLayou t.DEFAUL      T_SIZE, ja vax.swing.G    roupLayout.PREFERRE    D_SIZE)
                         .addComponent(passwordLa bel))
                    .addPreferredGap(j     avax.swing.LayoutStyle.Compon en   tPlace         ment.RELAT  ED)
                           .   addComponent   (passw    ordError1) 
                 .a ddPreferredGap(  javax  .swing.LayoutStyle.C  omp  onentPlacement.RELATED)
                      .addComp  on   e       nt( passwordError2)
                       .addP   referredGap(javax.sw  ing.Layou  tSt   yle.Compone ntPlace         ment.UNRELATED)
                     .addCo    mponent(jSc               ro llPane1, java  x.s   win  g.Gro upLayout.PREFE RRED_SI                 ZE, 124,    javax.swin g.Gro  upLayout.PREFE     RRED_SIZE)
                    .addPrefe   r       re   dGap(java      x    .swing.Layo                utStyle.   C     omponentPl    aceme nt.REL ATED)
                .addG               ro     up       (layout.c     reateParalle l   Gro     up(ja   vax.sw          ing.GroupLayout.Ali     gn   me     nt.BA SELINE)
                     .addCompo nent(create        Butt  on)
                             .addComp        onent(c a     ncelButton))
                .ad      dContainerGap(javax     .swing.GroupLayou  t.DEFA      U   LT_S   IZE, Short.MAX_VA LUE))
              )     ;

        user  na   meError.setVis  ib      le(fa ls      e)  ;
            emailError.   se  tV isible(fal     se);
        passwo   rdErro                 r1.setVisibl  e(false);
             pas swordError2.setVisible(fals   e);

            pack();
          }// <   /e   ditor  -fold>//GEN-E  ND  :i nitCo mponents

    pr ivate     void cancelB    uttonActionP   erformed(java.       awt            .eve  nt.Actio nEvent evt) {//GEN-F  IRS T:   eve      nt_ca     ncelBu tt   onA            ctionPerformed
           //Cancel user creation an     d close
                 this.disp ose()   ;
    }//GEN-L AST:ev ent_  cance  lBu            ttonA  ct ionPerformed
         
    pr    ivate v    oid           createButtonA     ctionPerfo     rmed(      java  .    awt.eve  n   t.ActionEv    ent evt) {/    /   GEN-FI            RST:event    _c        reateB  uttonActionPe    rformed
           //Va  lidate input and add use     r           i  f   good, warn if   no    t
                                            boolean errorFou             nd = fals  e  ;                   //Flag i  f        error found
               final    String INVALID_EMAIL    = "Email   not va lid.              ";
         fi       nal String EMA   IL_REQUIRED = "Emai   l is req             ui     red.";
        fi na  l String   PASSWO RD_REQ    UIRED1 = "Password is    required.    ";
              final Strin  g  PA          SSWO   RD_REQUIR       ED2         = "";
            final String PAS      SWOR    D_INVA L ID 1 = "P  asswor     d must conta     in one      l   etter,";
           final String PA    SSWORD_INVALID2 = " one num  ber, and one special character.";
                     
        
        //Force    e   rro    r    me ssages invisible fo   r multiple trie s   
            usern   ameError.se    tVi    sible(fals   e);
        email     Error.setVisible(fals     e);
                       passwordError1.setVisibl    e(false);
         passwordError  2.setVisible(false);
        
            //Us  erna     me cannot be empty
        if   (usernam   eField   .getText().      isEmpty()){
                  usern    a   m  eError.setVisible(true);
                                err   or  Found =    t   rue;
        }
              
           //Email can   not be empty
        if(emailFie  ld.    getText().isEmpty()      ){
            emailErr  or.setText( EMAIL_REQUIRED);
            email  Error.setV  isible(true);
            er      rorFoun    d = true;
          }
            //If ema   il not emp  ty, test     for validity
        else {
              //Email must be valid - some.thing@somewhere.com
                if(!isEmailVa   lid(  emailField.getText()))  {
                   emailE        rror.  setT  ext(INV   ALID    _EM AIL);
                em   ailError.setVisible(true);
                         errorFound = true;
              }
                 }
             
           //Pa  ssword cannot be e  mpty
             if(passwordField.getText().isEmpty()){
              passwordErro  r1.setText(PASSWORD_RE   QUIRED1);
            p          as    swor    dE  rror1.     set    Visi    ble(true);
            errorFo  und = tru  e;
            }
        //If password not empty, test for validity   
        else {
                   //Passw   ord must con  tain letter, n  umbe    r, an       d sp     ecial characte   r
               if(!isPasswordValid(password  F         ield.getText      ())){
                       password   Error1  .setTe  xt   (PA     S      SWO      RD_INVALID1);
                          passwor    dError2.setText(PASSWORD_I   NVALID2);
                  passwordError1.se     tVisible(true);
                      passwordError2.setVisible(true);
                         errorFound =    t rue;
                     }      
          }
             
        if(errorFound==false)   {
            //Stor   e user info in   acco unt
            account.setEmail(emailField.getText());
             acco   u    nt.set     Password(    passwordField.getText());
            accou    nt.setUsername    (user     nameField.getText());

                   //Pass to db to store user
                create    UserIn  DB(account);
            
                         JOptionPa   ne.showMessageDialog(nu      ll, "Logged in as " + accoun          t.g   etUsername()    );
            //Cl   ose      dialog
            this.dispose();
           }
        
    }//GEN-LAST:event_     createB   uttonA ct   ionP erformed

     private boolean isEmailVali    d(String e){
        //{Letters,Numbe  rs,Period}@{LNP}.{2-3L}
            final String regex = "^[\\    w][\\w.]*@[\\w]  +.[\\w]{2,}$";
        Pattern pattern = Pattern.compile(rege  x);
             Matcher matcher = pattern.matc her(e);
        return match    er.matches();
    }
    
    private boolean isPasswo      rdValid(String e){
          //At least 1 each    Lett er, Number, Spec. Character
        //Lett  er pattern
             Pattern letterPat =       Pattern.compi     le("[\\w[^\\d_       ]]");
        Matcher letterMat = letterPat.matcher(e);
        //Number pattern
        Patter       n numberPat = Pattern.compile("\\d");
        Matcher numberMat = numberPat.matcher(e);
        //Special Char p  atter   n
        Pattern specialPat = Pat  tern.    compile("\\W");
        Matcher  specialMat = specialPat.matcher(e);
        
        return let       terMat.find()&&numberMat.find()&&specialMat.find();
    }
    
    //Creates user in the db   f or later     login
    private void createUserInDB(Account a) {
        //Will   be   used to send user info to DB once DB i    s configured
          //Message Pane to show info now
        JOptionPane.showMessageDialog     (null, "Userna      me :" + a.getUsername() +
                "\nEmail: "+ a.getEmail() + "\nPassword: " + a.getPassword());
    }
    
    //Constructor with passed account
    public CreateAccount(Account a){
            account = a;
            initComponents();
        }

    // Variables declaration -        do not modify//GEN-BEGI     N:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton  createButton;
    private javax.swing.JTextArea directionsArea;
    private javax.swing.JLabel emailError;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel passwordError1;
        private javax.swing.JLabel passwordError2;
    private javax.swing.J  TextField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel usernameError;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}

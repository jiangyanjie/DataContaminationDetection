/*
 *    To change this     templ    ate, cho   o       se Tools | Template    s
 * and open   the templ   ate i n the editor.
 */

/*
 * Homework_database   _ad d.java
 *
 * Created on Nov 10, 2011,    8:43:42 PM
 */
package timetable;

import java.io.IOException;
import java.util.logging.Level;
import java.util.loggi    ng.Logger;
imp    ort java    x.swing.*;

/**
 *
 *    @author Abhi
 */
public class add_n     e    w_   us          er extends j   a     vax.swing.JFr  ame {
    
           int    n1   =0;
    i        nt       n2 =0;
                                         int n3 =        0;
    
                pu  bli        c static v  oid main(String    args[]        ) {
                         
        List_Homework_dat a   b    ase MList   = new List    _Homew  ork_datab    as        e();
        
         /   * Set the Nimbus loo   k and     feel */
              //<editor-f old      defaultst  ate="collapsed" desc=" Look an   d feel s    etting code     (opti   onal)             ">
        /* If Nimbu  s            (introdu     ced in     Java SE 6       )   is n ot availab le, stay with the default look and feel       .
           * For    details see h    ttp://dow           n    load.o    rac   l  e.c        om/javase/t u t      oria   l/uiswing/lookan   dfe    el/   plaf.html      
               */
           t    r    y {
                  for (javax.swing.      UIMan   ager.LookAndFe    elInfo info : java       x  .swing.UI Manager    .ge   tIn stal  ledLookAn       dFeel s()) {
                     if ("Nimbus".eq     uals(info.getName()))      {
                             javax.swing.UIManag   er    . set    LookAn   dFeel(info.getCla   s    sName());
                                b  reak;
                   }
                }
        }    catch (Class  No  tFoundExcept     ion ex) {
                   java.util.logging.Logger .getLo   gger(   a              dd_n     ew_user.class.getName()) .log(java.util.logging  .Level.SEVERE, null, ex);
         } catch   ( In  stantiationExc   eption ex          ) {
                      java.util.lo gging        .Logger.getLogger(add_new_user.class.getName()).        log  (java.util.logg   ing.Level.SEVERE,      null, ex);
                         } catch (IllegalAccessEx    ception ex) {
                java.util.logging      .Logger.g   etLog     ger(add_new_  use    r.class.g     etName()).log(j   ava.util.logg ing.Level      .S  E    VE RE, null,      ex);
        } catch (jav   ax.swing.UnsupportedLookAnd    FeelEx    ception        ex) {
                              java        .u               til.logging.Logger.  ge   tLo     gger(add_new_user          .cl     ass.g        etN   a   m     e()).lo  g(  java.util     .loggin g.L   ev    el     .S   EVERE, n    ull,            ex); 
          }
               //</ editor-fold>

        /*     C    reate and    displ    ay the for   m */
                         j ava.  awt.   EventQue       ue.   invokeLa ter(new Runnable() {   

            public void run() {
                         new add_new     _user().se    tVi sible(true);
            }
             });
    }

      /** Creates new      for  m Homework_database_a  dd */
    public      add_new_user(   ) {
          initCo   mponents();
              
     Member_details_add     .rea        darray();
    
        
    }
    

    /** T      his m    etho   d is        called from wit    hin the cons       tructor to
          *           init        ialize the form.
     * WARNING: Do     NOT  m   od    i    fy this             code. The     content o     f this    m       ethod      is
                * alwa ys regener    ated by t   he Form   Edi   tor.
       */  
      @S  uppressWarnings(   "unchec   ked")
    // <edi   tor-fo  ld defaultstate  ="c   ollapsed   " de   sc="   Ge         nerated Code"  >//GEN-BEGIN :in   itComp    onents
    private vo            id initCom    p            o   n     ent s()   {

          jP     anel1 = new javax.swing.J    Panel();
                 jLabel3 = new javax.s      wi  ng.JLabel()        ;
            jLabel6        = new javax.     swing.JLa               b         el();
           jTabb             edPane1    = new       ja  vax.swing.JTabbedPane();
               jPan    el2 =            new   javax.swing.JPanel();
        jLabel1 = new javax.swing.JLab     e l();
                        jLabel2 = new javax.swing.JLabel();
        jL         abel     4 = new javax     .swing.JLabel();
                jLabel      5 = n  ew jav   ax.swing.JLabel();
        jLabe  l7 =   new javax.   swing.JLabe  l(  );
          jLabel8       =     new    javax.swi      ng.JLa    be        l();      
         jPanel3 = n        ew jav  ax.swi   ng.JPanel();
            jC   o   mboBox5    = n    ew javax.sw ing.JComboBox();
             jLabel54 = new javax.swing.JLabel   (     );
            jComboB     ox4        = n  ew       javax.swing.JCombo    Box();
               jComboBox           3 = new j  avax.sw      i  ng.    JComboBox();
         jLab      el56        = ne      w      javax.  swing.JLa     bel()      ;
        jC           omboBox2 = new javax. swing.     JCom     boB  o x();
                   jLabel55 = new             ja vax.swing.JLabel();
        jLa    bel52              = new javax.swing.      JLabe  l  ();
        jLabe   l53 = new javax.swing            .JLabel()       ;
                        jButton2  =   new javax.swi     ng.JButton  ();
                  jLab  el 51 = new javax.sw  ing .JLabel   ();
          jL abel60 = new ja vax.swing.JLabel();
             De        scField55 = new javax.swing           .JText   Field();
        DescField57       = new javax.swin     g.JTex        tField();
           DescField51 = new ja    va   x.swing.JTextFi         el    d();
        jCombo    Box8 = new javax.swing.JComboBox();
             jLabel57 = n ew javax.swing.JLabel();
        jComboBox7 = new j  avax.swing.JComboBox();
        jLabel59      =    new   javax.sw   i  ng.JLabel();
        jComboBox6  = new javax.s     wing.JComboBox  ();
                  j   Label58 = new javax.swing.JLabel();
                        jP    anel4 =      n  ew  jav           ax.swing.JPanel();
          jBu tton3 = new javax.swi           ng.JButton();  
        jL   abel61 = new javax.swing  .JLabel()   ;
        jComboBox1 =   new ja  vax.swing.JCo  mboBox();

        setDe   fau  ltCloseOperation(j av    ax.swing.WindowConstants.EXIT_ON_CLOSE);
                      setAlwaysO   nTop(t  r  ue);
        setL        oca  tion(new java.awt.Point(343, 50));
       
        jPanel1.setBackground (new java.awt.         Color(25     5  , 102, 0));
            jPanel1.setLayo  ut (new org.netbea ns         .li    b  .awtextra.          AbsoluteLayo   ut());

        jLabel3.setIcon(new javax.swing.Ima       geIco  n(getClass      ().getResource( "/timetable/heade  r.png  ")))    ; // NOI18N
        jLabel3.s      e     tMinimumSi           ze(ne     w java.a     w    t          .Dimension   (490, 235));
                  j    Panel1.    add(jLabel3, ne  w org.netbeans.l  ib.awtextra.A bsoluteCons      traints(20, 20, 46   0, 5     0)        );

                     j    Labe    l6.setText("Retur   n"  );
                               jLabel    6   .  a  ddMouseListe   ner(n  ew java   .awt.event.Mous  e     Adapt  er() {
                    pub  lic void m     o   use   Clicked(    ja  va.awt.eve    nt.Mou  seE  vent evt) {
                    jL     abel6MouseCli            cked( evt)      ;
              }
        });
        jPanel1.add(jLabel6, ne w org.netbeans.lib.awte          xtr  a.Ab       solu   teConstraints (450   , 470,  -1, -1));

        jT   abbedPa ne    1.set   Curso    r(n   ew java.awt.Cu  rsor(java.awt.C         ursor.DEF  AULT_CURSOR));

                    jL    abe  l1.setText(     "Welcome to the        Time Man agement      Tool!");

            jLabel2.se     tText(    "This   is a program allo   ws yo     u to add, edit, and delete h    omewor     k,"  );
 
               jLabel 4   .setT     ex  t   ("and th     en        simu late it to ma  ke a tim    e  t     abl  e for y     ourself.");

        jLabe          l5.s  etTe   xt("This timetabl     e will calcu   late th        e amoun      ts of breaks you r             equire, ");

              jLabel   7.setTex  t("and a   llow you to   choose     what t      ime to start    study      ing. ");

                  jL  a         bel8.set      Text("You can      this print             this timet    able,    or take a sc      r   eenshot    o       f       i  t. ");

                org.jdes  kto       p.l  ayout   .GroupLayout jPanel   2Layout =                new org.jdesktop.    layout .GroupLayout(jPan el2);
                   jPanel2.setL         ayou   t(jPa            nel2La    yout);
        jP   anel2La      yout.setHoriz   ontalGroup(
                           jP anel2Layout.create      Parallel       Group(org.jdesk  top.layout.Gr ou         pLa    yout.LEADING)
                .add      (org.jdeskto  p.l ayout.Group   La      yo    u        t.TRAILING, jPane  l2Layout.creat    e      Sequential     Group()
                .addContainerG ap(org       .jdesktop.la   yout.Group  Layou   t.DEFA    ULT_SIZE, Short.M    AX    _VALUE)
                               .add(jLabel1)
                .add(95, 95, 95))
                      .add(jPanel   2Layout.createSequentialGr    o  up(   )
                      .add(20, 20  , 20)
                 .add(jPanel2La yout.creat        eParallelG    roup(o            rg.jdesktop.layout.Group   Layout.LEA DING)
                                 .  add(jLab            e   l      2  )
                             . ad d(jLabel5)
                                   .add(jPanel2Layou t.createSequ    e   ntialGr        oup()
                          .add(34,      34, 3   4)
                                 .add(jLabel4))     
                        .add(jPanel2Layout.createParal   lelGroup(   org.jdesktop.lay   out.GroupLa    yo        ut.TRAILING)
                         .add(jLabel7)
                                .add(jL            abel8)))
                         .addCon tainerGap(34, S    hort.MAX_VALUE))
        );
           jPanel2Layout.setVerti      calGroup(
            j  Pa  nel2Layout.createParalle  lGroup(org.jdesktop.layout.GroupLay      out.LEADING)
                                   .add(jPa  n      el2Layout.createSequentia    lGroup()
                     .add(14, 1     4, 14)
                       .add(jLabel 1)
                                         .add(      55, 55, 55)
                .add(jLabel2)
                          .addPreferr      edGap(org.jdesktop.layou     t.Layo   utStyle.RELATED)
                        .add(jLabel4)
                  .addPre         ferr  edG   ap(org.jde  sktop.layout.LayoutStyle.R   ELAT  ED)      
                .add(jLabel5)
                     .addPreferredGap(org.            jd  esktop.layout.LayoutStyle.RE     LATED)
                   .add(jLabel7)
                        .addPr  eferredGap(org.jdes  ktop.layout    .Layou      tStyle.RELATED)
                .add(jLa     bel8      )
                  .addContainerG     ap(125, Short.MAX_VALUE))   
            );

        jTabbedPane1.addTab("Welcome!", jPanel2);

        j   ComboBox5 .setMo   del(new javax.sw ing.DefaultComboBoxModel(new String[] {     "Physics", "Chemistry", "Biology", "Desi      gn Technology", "Enviro   nme    ntal      sys    tems    and    societies" }));

              jLabe  l54.setText("Gr  oup 3 Subj  ect  :");

        jCo    mboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hist     ory"      , "Economics", "Psychology", "Philosophy", "ITGS", "G            eograp  hy" }))    ;

        jComboBox3.setModel(new javax.swing.Defa  u  ltComboBo    xModel(new String[] { "Afrikaans", "Albanian   ", "Amharic", "Ar   abic", "Belar      usian", "Bengali", "Bos   nian"    , "B      ulgarian", "     Burmese", "Catal an     ", "Chichewa", "Chi  nese", "Cro   atian", "C     zech ", "Danish", "Dhiv ehi", "Dut ch", "D  zongkha", "   English  ", "Estonian"  , "Finnish",       "Fr  ench",    "Ge       orgian", "Ge  rma n", "Greek      ",      "Gujar a  ti", "Hebrew", "Hin        di", "  Hu       n    garian", "Icelandic", "In              don  esian   ", "Italian",  "Jap    anese"   , "Kannada",    "K orean"     ,   "Latvian", "   Lithuanian", "M acedonian", "Malay", "Marathi", "Mongolian", "Nepali", "N  orweg      ia          n",     "Pe  rsian", "Polish", "Portugu   ese", "Ro    manian     ", "Russian", "Serbian", "Se   sot  ho", "     Sho  na   ", "Sinhala", "Slovene    ", "Spanish", "Swahili", "Swati", "Swedish",         "Tamil", "Tel   ugu", "Thai", "Ti     betan", "Tigrigna", "Tswana", "Turkish", "Uk   rainian", "   Urdu",   "Uzbek",      "Vietn    ames        e", "Welsh"  , "YorÃ¹bÃ¡" }));

         j Label56.set  Text     ("Group 4 Subject:");

        jCom   boBox2.set   Mo  d el(ne w javax.                 swing.DefaultComboBoxModel(ne   w String[] { "Afrikaa     ns       ", "Albani   an", "Amharic", "Arabic  ", "Belarusian", "B     e  ngali",     "  Bosnian", "Bulgarian", "Burm e  se", "Catalan", "Chi  chewa"   , "Chinese    ", "Croatian", "Czech", "Dan     ish", "Dhiv    ehi", "Dutc   h", "Dzongkha", "Englis  h", "Estonia     n", "Finnis h", "Fr    ench"   , "Ge    orgian",   "German", "Greek", "Gujar   ati", "Hebr  ew", "Hindi" , "Hun   garian", "Icelandic"   ,    "Indonesian", "Italian", "    Japanese    ",  "Kannada",               "Korean", "L    a   tvi an",   "   Lit  huania n", "Ma     ce  doni  an",    "Malay",        "Mar   athi     ", "Mo     ng ol ian", "  Nepali"      , "Norweg      ian", "P  ersian", "Polis h", "Por   tu      guese", " Romani    an", "Russian  ", "S   erbian", "Sesotho",   "Shona", "Sinhala", "Slove              n      e", "S     panis   h",       "Swa   hil    i", "Swati", "S    wedish", "Tam   il",        "T   elu         gu", "     Th   ai", "Tibe   tan", "Tigrigna", "T   swan    a", "Tu    rkish", "Ukrainian",    "Ur      du", "Uzbe   k     ", "Vietna  mese      ",   "Welsh       "    ,         "YorÃ¹bÃ¡" }));

                jLabel55.         setText("G   roup          6 Subject:");

            jLa bel52.setText("Userna me:");

        jLabel53 .setT   ext("   Group 5 Subjec    t:");
    
        jBu  tton2.setTex           t("Add            User");
              j            Button2.      addMouseListener(new java.awt.eve           nt.MouseAdapter() {
                              publ ic v    o id mouseCli         cked(ja  va.awt.event.  MouseEvent evt) {
                     jButton2MouseClic  ked(ev    t)  ;
                   }
               });

        jLa bel51.s        etT         ext( " Gr  oup               1 Sub   ject:")            ;     

                    j      Label60.   setT  ext("Re-t      ype Code: ");

         jComboBox8.setM   odel(n    ew       javax.swing.Defa   ul           tComboBoxMode   l(new S  tring[] {   "Further Mathematics",    "Further     Dutch",    "N      one" }));     

               jLabel57.setText("7t    h Subject (Op t ional     ):");

        jComboBox7.setM ode  l(new javax.    swi  ng.       Defau ltComboBoxModel(new S            trin   g[] { "M   usic", "Theater",  "Visua       l Arts", "Fil   m  " }));    

        jLabe      l59.s etT    ext(  "G      roup  2 Subject:");

          jCo      mboBox6.set     Model(new j      avax.s   wing.DefaultComboBoxModel(new String[] { "Math S   tudi   es",  "Mat    hematics", "Computer Scie   nce" }));

            j    Label58     .setText("4 Digi      t Code:");
 
                  or     g.jdesktop.layout.Gr    oupLa        yo     ut jPa  nel              3Layout = new org.jdes        ktop.layo    ut.Gro    upLayout(jPanel3);    
           j   Panel3.setLay  out(jPa    nel3Layout);
        jPanel3Layout.   setHor            izontalGroup(
            jPanel3        L   ayou t.createParal    le      lGroup(org.jd   eskto  p.layout.Gr oupLayout.LEADIN  G)
                         .      ad    d(jPanel3L ay     out    .   createSequ   e ntialGro      up()
                           .addConta  inerGap         ()
                       .ad  d(jPan el3L ayout.      creat     eParal  lelGrou         p(org.jdeskt  op   .la      yout.Gr oupLayo       ut.LEA     DING)
                             .ad     d(      jPanel3Layout.create          Sequent    ia  lGro            up()
                                                  .add(j       Panel3 Lay        out   .createPa            rallelGroup(  org.jdes    ktop.layout .Grou  pLayou     t.    LEADING)
                                     .add(jL     abel52)
                                             .add  (jPanel3   Layout .crea    t  eParall   elG    roup(  org.jdeskto         p  .   l   ayou     t.                            Gro upLayout    .T           RAILING, false  )
                                                 .add(org.jdeskt     op.la      yout.G       roup         Layout.LEADING, DescF  ield51)     
                                       .add(o   rg.jd  esktop.la     yout.GroupLayout.LEA     DING, jLabel58)
                                                      .add(       org.jdeskt             op.layout.    Group Lay  out.LEA    DING, D   e      scField55,   org.jdesktop       .layout.GroupLayout.PRE  F    ER R        ED  _SIZE,        110  , org.jdes   ktop.layout.G        roupLayout     .P    REFERRE     D_SIZE))           
                                               .add(Des      cFie       ld57, org.jdesktop.layout.Gr  oup Layout.PRE    FERRE        D_SIZE,   1 10, org.jdesktop    .l   ayout.Grou     pLayo   ut.   PREFE  R   R         ED _            S      IZE)
                                                    .  add(jLabel60))
                         .add(27, 27, 27)
                             .add(jPa      nel3Layou  t.cr eateParallelGroup(org.jdeskt     op.layout.G     roupLayout.  L EADING)
                                          .add(jLabel5     1)
                                     .add(jL  a    b el5        9)  
                             .add (jLabel54)
                                             .add    (jC  omboBox2, 0, 1       2   6, Short.MAX_VAL  UE)
                              .add(jCom  boB  ox3, 0, 12  6,       Short.MAX_VALUE)
                                            .add  (jC       omboBox4    , 0, 126, Short.MAX_V     ALU  E))    
                                        .addPreferredGap(org.jdesktop.layou           t.Layout  Style.RE    LATED))
                              .add(org.jdesktop   .layout.   GroupLayo  ut.TRAILING    , jPane   l3La yout.creat      eSequen        tia      l  G     roup()
                         .add(jButton2    )
                                             .  add(15, 15, 15)))
                       .add(jPanel3   L   a    you   t  .createParallelGr   oup(org.jde               sktop.layout.GroupLayout.LEADIN   G)
                           .a    dd        (jLab   el56)
                             .add(jLabel53)
                               .    add(jLabel55)
                         .a   dd(jLabel57, o        r    g.jdesktop.la    yout.Gro upLayout.D   EFAULT_  SIZE,  148, Sho          rt.MAX  _V ALUE)
                                     .add(jPanel3Layout.createParal     lelG   r  oup   (org.jdesktop.layout.Gro  upLayout.TRAILING, false)
                                            .add(org.jdes   kt     op.layout.GroupL   ayout.LEA DING, jComb    oBox7, 0, org.    jd   esktop.layout.GroupLayo   ut.DEFAU  LT_SIZE, Shor      t  .      MAX_VALUE)
                          .add(org.jdesktop.layout         .Grou     pLayout.LEADING, jCombo  Box6, 0, 1, Sho      rt.MAX_VALUE)
                             .add(      org.jde   s    ktop.layout  .     GroupLayout.L    EADING, jC      omboBox5, 0,   131, Short.MAX_VALUE))
                                  .add(jC  omboBox8, or g.jdesktop.lay out.GroupLayout.PREFERRED  _SIZE, 1   29, org.jdeskt    o  p.layout.  GroupL ayout.PREFERRED  _SIZE))
                .add(36, 36, 36)     )
             );
           jPanel3Lay out.set  Vertic  alGroup(      
                                  jPan     el 3Layou  t.creat     ePara    llelGr   o up(o  rg.jd      esk  to    p.l   ayou     t.GroupLayout.LE        ADING   )
                    .ad d     (jPanel3   Layout.createSequ      ential Group()
                .add      ContainerGa p()
                           .ad    d(jPanel3Layou   t.c  reateParallelGroup(org.jde   skto    p.layout.Gr                  oupLa    yout.BASELINE)   
                                   .add(jLa  bel52)
                    .ad  d(jLabel56)
                       .ad    d(jL       abel51))
                .ad   dPreferredGap(org.jdesktop.la  y    out.L  ayoutStyle.RELATED)
                  .add(jPa         nel3Layout.   createParallelGroup(or g.j   d   esktop.l    ayout.GroupLayout.BASELINE)
                                               .add(DescFie         ld5 1, org.jd  esktop   .layout.G  roup   La     yout.PREFERRED_SIZE,   or  g.jd    esktop.layou   t.Gr     oupLayout.DEF         AULT_SIZE, org.jdesk top.l    ayout.GroupLayout.PREFERR        E      D_SIZE)
                       .add(jComboBo          x2, or     g.jdeskto   p.layou  t.GroupLayout.PREFERRED_SI     ZE, org.jdes kt   op.layout.GroupLayout.DEFAULT_SIZE, o    rg.jdes   k      top.layout.Gr   oupLayout      .PREFERRED_SIZE)
                                       .add(jComboB      ox5, or  g.    jdesktop .layout.GroupLayout     .PREF  ERRED_SIZ   E,  org.    jdesktop.l   ayout.G      roupLayout.DEFAULT_   S   IZE, org   .jdesktop.layout.Grou pLayout.PREFERRED         _SIZE))
                                .addPreferredGap(org.jdesk    top.layo   ut.LayoutStyle.RELATED)
                 .  add(jPan  el3Layo    ut.createParalle    lGrou  p(org.jdesktop.  layout.         GroupLayout.BASELIN    E)
                      .add(j   Label58  )
                    .add(jLab  el53)   
                      .add(j   Label5    9))
                         .addPreferredGap(or g.j     desktop.layout.L  ayout     Style.RELATED)
                      .  a       dd(jPanel  3Layo      ut.crea   teParallel   Group(org.jdes   kt    o   p .layout.Group    Layout.BAS     ELINE)
                    .add(DescField55, org.jde    s    ktop .lay out.GroupLayout.PR   EFER        RED_SIZE,            org.jdesk          top.layout.GroupLayout.DEFAULT_SI    ZE, org.jdesktop .layout.GroupLayou      t   .PREFERR  ED_SI  ZE)
                            .add(jCo   mbo   Box3,   org.jdesktop.l ayo ut.Gr        oupLayout.PREFERRE    D_SIZE, org.jdesktop.lay    o           ut.   Gro       upLayout.DEFA    ULT_  S  IZE, org.jde        sktop.layout.GroupLay out   .PRE    FERRED_SIZE)
                              .   add(jComboBox6, org.jdesktop.    lay   out     .GroupLayout.PREFE RRED_SIZE, org.jdesk top.layout     .GroupLay          out.D     EFAULT_SIZE, org.jdesktop.layout.GroupL  ayout.PREFERRED_S     IZE))
                               .add(8       , 8, 8)
                                       .add(jPanel3Layo        ut.createParalle   lGroup(   org.jdesk       to p.l   ayou      t.     GroupLa        yout.BASELINE)
                                 .add(j      Labe       l55)
                           .add(jLabel54)
                               .a  dd(jL    abel 60))
                    .addPre    fer     redGap(org.jd es  k     top.  layout.LayoutStyle.RELATED)
                 .add(jPanel3Layout       .crea     teParallel    Group(org.jdesktop.layout.GroupLa     yout.BASELINE)
                            .ad  d(    DescField57,         org.  jdesktop .layout.GroupLayout.P  REFERRED  _SIZE     , org.jde          skt   op.lay o       ut.Gr         oupLayou t.DEFA   ULT_          SIZE,        org.   jd      e  sktop.lay   out.GroupLayout.PR EFERRED_SIZE)
                          .add(jComboBox4  , org.jdeskto   p.layout. GroupL      ay  out.PREFERRED_SIZE, org.jdesktop     .layout.      GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout. P       R   EFERRED_SIZE)
                    .add(jComboBox   7, org.jdesktop.lay  out.GroupLayout    .PRE                FERRED_SIZE, o    rg.jdes    kt  op.la       yout.GroupL     ayout.DEFAUL T_SIZE, org.jd    esktop.layou   t.GroupLayo      u      t.PREFERRED_SIZ   E))
                         .add PreferredGap(org.jdesktop.layout.LayoutSt  yle.RELATED)
                    .add(jPanel3   Layou    t.    c  reatePa  rallelGroup(org.       jde        sktop.layout.Gro   upLayout.T      RAILING)
                      .add(jPanel3     Layou    t.cre        ateSequ     entialGr  oup()
                           .add(jLabel57)
                                               .addPreferred    Gap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                              .add(jCombo    Box8, org.j  desktop    .layout.Gro  upLay    out.P  REFERRED_   SIZE, org.jdes     ktop     .layout    .GroupLayo    ut.DEF   AULT_SI  ZE,     org.j desk top  . layout.GroupLayou      t.PREFERRED_SIZE)
                              .a ddC    ontain     erGap(80, Sh     o   r  t.MAX_VALUE))
                    .add (jPanel3Layout.crea        teSequenti    alGroup()
                                       .a  dd(jB  utton2)
                               .     addContainerGap())))
                      );

            jTabbe   dPan e1.add    Tab("Add User", jPanel3  );

                jButton3.setText("Sa ve");
              jButton3.addMo       useListener(n  ew ja  va.awt.ev  ent   .Mous    eAdapter() {
                    publ     ic void mou  s       eCli cked(java.   awt.event.Mo     useEven    t evt) {
                            j      Button   3MouseClicked   (evt);
                             } 
        });  

                    jLabel61.setText(  "Study Start Time:");

                     jComboBox1.setModel(new ja  vax.swing.D  efaultComboBoxModel(new Stri  ng[] { "8:00", "   8:15",       "8:30",     "    8      :45", "9:00", "9:15", "9:3   0", "9:45",   "10:00", " 10:15", "10:30", " 10:45", "     11:00",                   " 11:15", "1   1:30", "11:45",    "12        :0     0", "12:     15 "  , "12:    30", "12:      45   ", "13:00", "13:15    ", "13:30", "13:45", " 1   4:00",  "14:15", "14:30", "14:  45", "15        :00", "15:15", "15:30",   "15:45", "16:00", "16:15",    "16:     3  0", "16:45", "17:00",    "17:15", "  17:30",  "17    :   4     5", "18:00", "18:15",   "18:30", "18: 45", "1   9:00", "19:15", "19:30",     "19:45", "20:00" }));

             or    g  .jdesktop.layout  .GroupLayout jPanel4Layout =  ne   w org.jde  skt      op.layout   .G roupLayout(jPanel4);
            jPanel4.setLayout(j       Panel4Layo        ut);
        jPanel4L  ayout.   se   tHo  rizontalGroup(
                      jPanel4Layout.createParallelGroup(org.jdesktop.layout.G  roupLayou  t.LEADING)
              .add(jPanel      4L   a    yout.createSe      quen tialGroup(    )
                 .ad d(j      Pa  nel4Layo ut.createP      arallelGroup(org.jdesktop.l a yout.Group   Layout.LEADING)
                        .add(jPanel4Layout.createSequen tialGr              o  up(      )
                                        .add(146, 146, 14  6)
                           .add (jCom   boBox1, org.jdesktop.l      ayout.Gro  upL  ayout.PREFERRED_SI ZE,  15  3, org.jdesktop.layout.GroupL   ayout.       PREFERRED_SI   ZE)) 
                        .a     dd(jPanel4Layout.createSequenti     alGroup()
                         .add        (186, 186, 186)
                                          .add(jButton3))
                      .add(jPanel4Layout.createSequentialGroup()
                                .add(163, 163, 163)
                             .add(jLabel61))     )
                .addContain   erGap    (1    60, Short.MAX_VALUE     ))
        );
        j     Panel4Layout.setVerticalGroup(
               jPanel4Layout.createParallel Group(o   rg.jde  sktop.layout.GroupLayo   ut.LEADING)
            .add(jPanel4Layout.createSequ    enti a lGroup()
                .add(57, 57, 57)
                      .add(jLabel61    )
                    .addPreferredGap(org.jdesktop.layout.L ay  out   Style.UNRELATED)
                    .add(jComboBox1, org.jdesk        top.    layout.Grou  pLayout.PREFERR  ED_S  IZE, org.jdes ktop.layout.GroupLayout.         DE    FAULT_SIZE, org.jdesktop.layout.      GroupLayout.PREFERRED_SIZE    )
                .addPr  ef    erredGap(org.jdesktop.layout.Layo   utStyle.U NRELATED)
                          .add(jButto     n3)
                .addContainerGap(161, Short.       M   AX_VALUE))
            )     ;

        jTabbed     Pane1.addTab("Prog       ram settings",      jPanel4);

          jPanel1.add     (jTabbedPane1, new org.netbeans.lib.awtextra.Absol   uteConstraints(10, 90, 480, 360));
        jTabbedPane 1.getAcc     essibleContext().set   AccessibleName("Add   User");

        org.jdesktop.layout.Group    Layout layout = new org.jdesktop.layout.GroupLayout(getContentP ane())     ;
        getCo   ntentP   ane().  setLayout(     layout);
           layout.setHorizo    ntalGrou    p(
            layout.createParallelGroup(org.jd   esktop.layout.GroupLayout.LEADING)
               .add(layout.createSequentialGroup()
                .ad        dContainerGap()
                       .add(jPanel1, o   rg.jdesktop.layout.GroupLayou t.DEFAULT_SIZE,     501, Short.MAX_VALUE)
                    .a    ddContainerGap())
        );
          lay   out.s etVerticalGroup(
            layout.createParal     lelGroup(o    rg.jdesktop.la        yout.G     roupLayo   ut.LEADING)
            .add(layout.createSeque      ntialGroup()
                   .addConta iner Gap()
                  .add(jPanel1  , org.jd  esktop.layout. GroupLayout.DEFAULT_SIZE, 493, Sho  rt.MAX_VALUE)
                .addContainerGap())
        );

        pack(  );
    }//  </edit     or-fold>//GEN  -END:initComponen   ts

private void jLabel6MouseClic  ked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLa    bel6MouseClicked
    this.setVi     sible(false);// TODO add your handl  ing code here:
}//GEN-LAST:ev     ent_jL   abel6MouseClicked

             private void jButton3M ouseClicked(java.a   wt.event.Mouse   Event evt) {//GEN-FIRST:event_jButton3Mouse           Cli   cked
          if(DescField51.getText  () != null)  
       {
        readsettings.write((   String)jComboBox1   .getSelectedItem(), DescField51.   getText() );
        JOptionPane.showMessageDialog(null, "Settings changed!", "Timetable Management Program", JO   ption  Pane.INFORMATION_MESSAGE);
          login_area.ope   n1 = 0;
        this.setVisible(false);
       }
    }//GEN   -LAST:event_jButton3MouseClicked

    private void    jButton2MouseClicked(java.awt.event.MouseEvent evt) {   //GEN-FIRST:event_jButton2MouseClicked

            String use  rna  me    add = DescField51.getText();
           String passwordad      d = DescField55.getText  ();
        String subject1 = (String) jComboBox2.getSelectedItem();
        St    ring subject2 = (String) jCo  mboBox3.getSelectedItem()   ;
               Str    ing subject3 = (String) jComboBox4.getSelectedItem()  ;
        String subject4 = (String) jComboBox5 .getSel  ectedItem();
        String subject5 = (St      ring) jComb     oBox6   .getSelectedItem   ();
        Str  ing subject6 = (String) jComboBox7.getSelectedItem();
        String subject7 = (String) jComboBox8.getSelectedItem();
        int delete = 0;

          if (passwordadd.equals(DescField57.getText())) {

            if (passwordadd.length() >    4 && passwor dadd.length() < 4) {
                JO   ptionPane.showMessageDialog(null, "Please enter a four digit pin and try again!", "Timetable Management Program", JOptionPane.INFORMATION_MESS    AGE);

             } else {
                Member_detai    ls_add.addtoarray(usernameadd, passwordadd, subject1, subject2, sub   ject3, su        bject4, subject5, subject6, subject7, delete);
                J OptionPane.showMessage   Dialog(n ull, "User Added!", "Timetable Management Program", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                  lo      gin_area.open1 = 0;
            }
        } else {
            JOptionPane.showMessageDialog(null, "The two       passcodes don't match try again!", "Timetable Management Program", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_jButton2MouseClicked

    /**
     * @param args the command line         argume      nts
     */

    // Variables declaration -  do not modify//GEN-BEGIN:variables
         private javax.swing.JTextField DescField51;
    private javax.swing.JTextField DescField55;
    private javax.swing.JTextField DescField57;
      private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    priva    te jav ax.swing.JComboBox j    ComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    pri  vate javax.swing.JComboBox jComboBox5;
    pri  vate javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox   jCo   mboBox7;
    private javax.swing.JCo   mboBox jComboBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swi ng.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    p   rivate javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}

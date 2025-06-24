/*****************************************************************************
      * Copyright by Th e HDF   Grou p.                                                                                                               *
 * C   opyright by the            B              o  a  rd of Trus t     e      es o f the Uni                              ve   rsit        y     of            Illi   n          o            is.                            *             
 * A    ll ri ght                  s   reser         ved.                                                                                                                               *
 *                                                                                                                                                              *
 * This f    i    le is pa   rt   of the H     DF Java Products dis    t  ribution.                  *
 * The full copyright no  tic    e,  inc luding terms gov ernin  g use                        , modification,      *    
 * and         redistribution, i    s contained        in                  th   e   files  COPY   IN      G and Copyright.htm  l.      *
 * COPYING can be found at the root       of t  he source code distribut  ion tree      .    *
          * Or, see   http://hdfgroup  .o   rg/  pro   ducts/hdf-java  /doc/C opyright.html.                        *
 * If      y ou do not have   access to either file,     you may req  ues     t a copy from     *
 * help@hdfgroup.org.                                                            *
 ****************************   *************************   ***********************/

package ncsa.hdf.view;

import      java.awt.BorderLayout;
import java.awt .Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
    import java .awt.event.ItemEvent;
imp     ort java  .awt.event.ItemListener;
import java.a   wt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.BitSet;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
im     port javax.swing.ButtonGroup;
import javax.swing.JButton;
import j     avax.swing.JCheckBox;
i   mport javax.swing.JComboBox;
imp   ort javax.swing.JComponent;
import javax.s wing.JDialog;
import javax.swi   ng.JFrame;
import javax.swin   g.JLabel;
import javax.swing.JList;
import java  x.swing.JOptionPane;
import java            x.swing.JPanel;
import javax.swing.JRadioButton;
   import ja      vax.s  wing.JScrollPane;
import j avax.swing.JTe    xtField;
import javax   .swing.JToggleButton;
imp      ort j    avax.swin  g.SwingConstants;    
import javax.swing.Window Constants;
import java   x.swing.border.Et   ch   edBorder   ;
import java    x      .swi  ng.border.LineBorder;    
impor       t javax.s wing.border.Titl   edBord er;    

import ncsa.hdf.object.CompoundDS;
import ncsa.hdf.object.    Dataset;
import nc sa.h    df.object.Da   tatype;
import ncsa.   hdf.object.FileFormat;
      impor  t ncsa.hdf.       object.ScalarDS;  

 /**
 *   DataOptionDialog   i  s a      n   dialog wi ndow     used to selec      t displ ay option     s. Disp   lay
 * options   inclu  d e select     ion    of s    u bs       et, display type (image, t     ext, o   r
 * spreadsheet).
        * 
 * @author Peter X. C   ao
 * @version 2.  4 9/6/    200  7
 */
public class            Data   OptionDialog ext      ends JDialog implements A  ctionListener, I  temListen    er 
{
       /**
     *  
          */
        private static final long serialVersi        onUID = -107 8          411885690696784L;

    /**
     * The ma in HDFView.
        */  
          private final View    Manager    viewe r;

    /** the se        lected da      tase  t/image *   / 
    pri   vate Dataset    datas           et;

    /** the ra  nk of      the     d  ataset/image */
        private int  rank;
  
    /** the starting point       of selected subset */
              private long start[];  

    /** the sizes of all dimensi  o    ns   *   /
            private long dims[];

    /**     the select   ed si     zes of all di  mensions */
    private long se      l    ected[];

    /** the st    ride */
       private long stri    de[   ];

         /**    the indices of the se  lec  ted dimensions. */
    priva         te int selectedI  ndex[];

    private int currentIndex[];

        private JRadi    oB  utton spread     sheet         Butt     on, imageButton         , base1Button, base0Button;

          private JRa   dioBut    t    on[] bitmaskButtons;  
    private JCheckBox applyBit       maskB     utton, e  xtract   BitButton;
   
    private J       CheckBo        x charC   he   ckbox;
    
    private       BitSet bitmask;
           
            priva  te JButton bitmask Help;

    private JComboBox choiceTe   xtView,     choice  TableVi  ew , c        h  oiceImageView,
            ch oicePalette, cho    ices     [];
         private JComboBox  transpos  eChoice;

    private     boolean isSelectio         nCan     celled;

    priva     te boo         lean isTrueColorIm   age;

            private      boolean is   Te  xt;

       privat   e b      o    olean isH  5;

            private JLa bel  maxL  abels[], selLabel;

          p       rivate           JTextF ie   ld start   F  ields[], endFie  lds[], strideField       s[]  ;

     private    JLi     s  t fie ldList;

    private final Toolki        t toolk it;

            p   rivat     e        fin a          l       Previ   ewNavigator navigator;

      private int n umberOfPal ette            s;

    /*   *
     * JComb oBox.se     tSelect    edIte  m() or set  SelectedI  ndex(   ) alw     ay   s     fires   act         ion
         * even   t. If you   call se     tSelectedItem() or setSelecte   dIndex() at
          * i         t  emStat    e      Chan ged() or      actionPer  f         ormed (),      the s    etSelectedItem() o     r
           * setSelec      t edIndex()    w   ill make loop calls        of    itemState  C       hanged() or   
     *     actionPerf      orme    d       ().      This is not wha     t we wan     t          .      We want the
     *    s   etSelec  t   e     dI         tem() or s etS  el    ectedI   ndex() beh avior like java.awt.Choice.
     *     This     flag is used to serve this            purpose.
       */
    private boo  lean p       erformJComboB    oxE ve nt = false;      

    /**
         *     Constructs       a Dat  aOpt     ion   Dialog with the g  iven HD FV   iew.
     */
           public DataOpti onD ialog(ViewManager theview, Dat  aset theDataset) {
              supe  r((JF   rame) t  heview, true);
            setDe          fau    ltC   l                 oseO  p      eration(WindowConsta    n    t     s.DISPOSE _ON_CLOSE);

             vi ewer = theview   ;
                            dataset = theDa   t          a  set;
        isSele     ctionCancel      led = true;      
        isTrueColorImage = false       ;
          isText =   false;
             bitmask = nu       ll;
           numberOfPalet    tes      = 1;
          toolkit = Toolkit.    getD efaultToolk     i   t();

        if (dat as       et   =    = null              ) {
            disp  os   e();
         }    
           else {
                           setTi     tl   e("Dataset         Select   ion -  " + dataset.get   Path(      )
                          + datas  et.get N             ame(   )     );
                   }

            isH5 = d    atas   et.ge  tF        ileFormat().isTh      isType     (
                    FileFormat.g   etFil   eFormat(F   ileFormat.FILE_TYPE_HDF5));

              rank   = dataset   .getR  an  k();    
            if (rank <= 0) {
                   dataset   .init(   );
        }
        if (  isH5 && (da      taset       i    nstan   ce of ScalarDS)) {
              byte[] palRefs = ((     Scal   a  r    DS) data  set).getPaletteRefs     ();
            if (   ( pa        lRefs != n  ull)      && (palRefs.len  gth > 8)) {
                    numberO  fPalettes =   palRef      s.leng    th /     8       ;
               }
        }
               rank =    dataset.g      etR    ank();
              dims = dataset.getDims   ();
            sel  ected = d     atas  et.getSelected   Di     ms();
              start = dataset.getStartDims();
        selectedIndex = datase     t.getSe      lectedInd    ex();
        stride =  dataset   .getStri    de();
                    fiel      d  List = null;

                 int h = 1, w = 1;
                 h =     (int) dims[      selectedIndex[0]];
                     if (rank  > 1) {
             w = (int) di            ms[sele  ctedIn     dex [1    ]];
        }

              transposeChoice =  n         ew JCo    mboBox();
             transposeChoice.addItem("R  eshape");
        tr       a  nspose  Choice   .addIte      m("Transpose");

        s elLabel = n  ew   JL  abel(   "", SwingConstants.CENTER);
        navigator = new PreviewNavig  ator( w, h);

           currentI    ndex = new         int[Math.mi n(3 , r         ank)];   

           ch      oicePalette = new JComboBox();
        ch oiceTextView   =  new JComboB          ox((Vector<      ?>)   HDF              V     iew.getListOfTextView());
        c                     ho      iceIm  ageView = new       JCombo  Box   ((Vect    or    <    ?>) HDFVi  ew.ge    tLi   st        O  fImageView    ());
            c  h     oiceTableView = new JComboBox(    (   Vector<?>) H   DFView.getListOfTableV  iew   ());
  
        ch     oicePalette.  addItem("Se     lect p alette   ");
            if(dataset in    stanceof ScalarDS ){       
            String pale tteName = ((    ScalarDS     ) datase    t).getPale  tteNam     e(0);
                   if(palet  teName==null){
                p  ale       tteName =      "Default";
               }
               choicePalet  te.addItem(paletteNam   e);
                     for (int i = 2; i <= nu     mber   OfPa     lett            es; i++) {
                            paletteN           ame = (         (Scal    arD              S      ) dat   ase   t).g     etPaletteNa  me(  i-1);
                        c ho  ic   ePale     tt   e.addIte       m(p        aletteName);
                      }
                       }
                          choi cePalette. addItem("Gray");
              c   hoic e    Pale   tt      e.a   ddItem("ReverseGra   y");
               choiceP          alette.addItem("GrayWave");
              ch     oicePalett    e.addIte   m("     Rai        nbow   ")   ;
                                                  choi cePalette.a     ddItem("Natu   re");
           c   hoicePale  tt e.addIt   em("   Wave");

        spreadshe     etButton  =        new JRadioButt    on("Spreadsheet ",              true);
        sp      readsheetButto         n.setMnemonic  (KeyEv     ent  .VK_S);
        imageButton = new    JRadioBu  tton("  I       mage ");
          ima    geButton.setM  nemonic(KeyEvent.VK_I);

        charCh  eck  box = new     JCheckBox("Sh     ow As Cha r", false)   ;
        charCheckbox. se tMnem        onic(KeyEvent.VK_C);
               charCheckbox.   setEn     abled(fa lse);
           c   harChe     ckbox.addItemListen  er(th    is);

        extractB          itButto      n = new JChe     ckBox("Show Value of     Selected Bits",      false);
        extractBitButt   on.    setMne       monic(KeyEvent.VK_V)   ;
         extrac    t BitButt    on.setE  nabl        ed(fal    se  );    
        extractBitButton.a  ddItemLis    tener(this);

        applyBi tmaskButt   o n =     new J   CheckBo    x("Apply Bitmask", fal   se);
         appl   yBitmaskButton.s    et  Mnem  o           nic(KeyEvent.VK_A);
                 applyBi     tmaskButton.s  etEn  abled(false);
            ap  plyBit  maskButto n.a     dd   ItemListener(this);
            
          bitmaskHelp = n    ew JButton(Vi  ewProperties.getHel       pIcon());
             bitmaskHelp      .      set T   oolTipText       (     "He  lp on how to   set bitm   as     k");
        bitma   skHe    l  p. setMargin(new              Insets(0, 0,   0, 0    ));   
           b   itmaskHelp.add ActionListener(t   his);
               bitmaskH     elp. setActio       n     C  o   mmand("      Help o    n how to    s         et bi     tmask");

        // layou  t the   components
        JPanel     con   ten     tPan     e = (JPa  nel)       getC     ontentP ane();
               con  tentPane.        set   Layout(ne    w Border     Layout(5, 5));
        c  on       tentPane   .se        tBo        rder(Borde  rFactory.cr    eateEmpty   Bor  der(5, 5 , 5, 5));
                int w   1 = 70  0 +  (        ViewProperti   es.get   FontSi    ze()     -  12) *                     15;
           i nt h1 = 3     50   +          (Vie  wProp ert  ies.getF  on tSi    ze (   ) - 12) * 10          ;
            contentPan    e.setPr eferr               edSize(new     Dimension     (w1,  h1));

        JPane    l center                   P   = new JP  anel();
                     centerP.se    tLayout(   ne   w         BorderLayout())      ;
         centerP.setBorder(new Ti    tl     edBorder("Dimens      ion and Subset      Se   lection ")   );     

                     JPan     el navigator P =  ne w         JPanel()   ;
                 naviga       torP.    setLayou        t(new B          orderLayout ());
        nav     igatorP.add(n   avig  at  o   r, B    orde        rLayout.CENTER) ;
         navi            ga    torP.ad d(    selLabel, Border  Layout.SO  UTH);
         navigat            orP.setB       o   rder            (new    Et   ch   ed       Bo   rde      r(Et  chedB or               der     .LOWERED));
           p       erformJComb      o   BoxEvent = t  r     ue;     

        if          (dataset instance     of Com    poundDS)            {
               //               s  etup GUI com          pon  ents fo             r the f        ield selecti on
                                           CompoundDS  d    = (Co   mpo   u   ndDS) dataset;   
            Stri             ng[] names    =     d.  getMem    berNames();
             fie     ldLi  st     = new JLis         t(name    s   );
                       fie   ldL                       ist.addSelectionInterval(0, names.len   gt  h - 1)            ;  
              JPa      nel f        i  eld    P = new JPanel();
                          fieldP.setLayout(new Bord          er   La   y     out()) ;   
                    w1 = 150     +    (ViewP      rop       ertie     s      .getFontSize() - 12) * 10;
                h1 = 250 + (ViewPropert    i            es. getFo                  ntSize() - 12) * 15;    
             fieldP .setPref     e                 rredSize(new         D imension(w1, h1 ));
                     JScr  ollPane    scrollP = new JScrollPane(fieldList);
                 field  P.ad      d(scrollP);
                         f     ieldP   .set   Border(new    TitledBorder("Se  lect Members "         )) ;
            co ntentP    ane.add(fieldP, BorderLayo    ut.   WES   T);

                        JP       anel tvie     w  P    = new JPan      el();
                        tvi       ew  P.setLa   yout(new B   orderLayo ut()    );
                                         t viewP.a     dd(     new     JLabel   ("              TableView:  "   ), BorderL        ayout.WEST);
                  t   v  iewP.  add(choiceT  ableV  iew            , Bor    derL       ayout.CENTER      );
                        tview         P  .set    Border(n    ew LineBorder(Color.LIGHT_GRAY));  

                                 c   e    nterP.add(t          view        P,  Border Layout.SOUTH);
                }
        e    lse i    f (dataset in   sta      nc eof S cal                       arDS    )      {
                                    Scalar       DS                sd = (S   calarDS) dataset;
                              isText    =      sd.isText();

                          if (     isText)      {
                   w1 = 700 +       (ViewP        rop  e    rties.    getFontSize(       ) -     12)           *              15;
                       h           1 =    280     + (ViewProperties   .getFon   tS ize() - 1  2)     * 1    0; 
                            c    ontentPane.se     tPreferredSi  ze(  new Dimension   (w1  , h1)    );
                               //     add     textvi            ew selecti       o     n
                                     JPanel txtviewP    = new JPanel();
                                                txt    v  i ewP.setLayout(    new   B  orderLayout());             
                          txtviewP.add(new JLabe   l            ("               TextV          i   ew:  "),
                               Borde rL   ayout.W EST );
                      txt          vie wP      .add(choiceT   extV           iew,   BorderLayout.CENTER    )      ;
                      tx   tv   iew   P        .setBorder(new LineBorder(Col       or.L   IG         HT_G           R  A       Y)   );
            
                              c     enterP.a   dd(txt  viewP ,                                B   ord   erLayout.    SOUTH);
                 }
                      else     {
                           w1 = 680        + (      Vi  e wPr   o  p     ertie    s.getFon      tS      ize  () - 12) *                  15;
                                      h1       = 400    + (ViewPr      oper ti e     s.getFon  tSiz      e( )   -    12) *     10   ;
                                 con       tentPane   .    se   tPr eferred     Size(new Dimension            (  w1,     h1)); 
                          if (rank             > 1   ) {
                                centerP.  add  (  n    avigatorP, B  orderLay     out.WEST            );
                       }

                     // s     etup GUI components         for th      e  display o p tions: table     or image
                            i      mag   eB utt   on.addItem    List ener (t      his);
                        spreadshee             tButton.   addItemListe  ne    r(this);          
                           Butto nGroup rgro     up = new ButtonGrou p( );
                     rgr  oup.add(spre   adsheetButton)      ;
                rgroup  .add     (imageButton);
                 J  Panel v ie       wP = new JPanel()    ;
                                          viewP   .setLayo ut(ne   w Gri     dLa   yout(2, 1,    5, 5))  ;
                             viewP.         se   tBorde     r(new T     itle   dBorder("Display As"));

                               J Panel sheetP =   new                J     P   ane   l();
                                       she       etP.setLa    yout(ne      w GridLayout(    1, 2, 25, 5)  );
                         she    etP        .ad d(spreadsh   eetButton);
                             int tclass = sd .g  etDatatype().getDa          tat               ype   Cl   a     s    s();   
                     if     (tclass == Dat   a       ty  pe             .C    LAS        S _CHAR
                                     |     | (t  class =    = D ataty    pe.CLA      SS                          _INT      EGER && sd
                                                       .getDa  ta     type().g etDa    tat ypeS  ize() == 1  )) {
                         sheetP.a       dd(charC  he   ckbox   );
                                        }

                         //    add t  abl  eview select    ion
                           J    Pa  nel tviewP = n   ew JPanel();
                                       tviewP.setLa    yout(  n ew BorderLayout());
                tvie  wP  .add(new            JL abel(" T    ableView:          "), B   ord   er  L    ayou      t.WEST)             ;
                       tvi                                 ewP.add(c  hoiceTab     leView, Bord   erL    ayout.CENT  E     R             )   ;
    
                                         JPa  nel   l     ef  tP    = n    e   w JPan    el();
                    l  eftP     .setBor     der( BorderFactory
                                                    .crea         t eLin         eBorder(Color.   LIGHT_   GRA   Y));
                              l  eftP.setLayout(new      Grid  L             ay    out(2, 1       , 5   ,    5));
                            leftP.add(   s    he  e   tP);    
                leftP.   add(     tviewP);

                         viewP.add(leftP);

                        JPanel imageP =  n    ew      JPan el ();
                   i mage    P.setLayout(    n              e   w           Bord        er  Layout(5, 5));
                    im age    P.       a   dd(imageButton,   Border      Layou       t.WEST);
                          i mageP     .add(ch           oic   ePal   et             te, Bord  erLayout.       CENTE R); 

                                     // add imagevi   e  w     selection
                                  J  Pane  l    iviewP =  new JPan    el();
                                  iview    P. setL  ayou  t(new BorderLayout())      ;
                                        i   vi     ewP.add  (new JLa  bel("Ima g             eView: "), Bord   erLayout.    WES         T);
                                      iviewP    .add(choi   ceIma g    e View   , B orderLayout.C  ENTER);

                                   JP                anel rightP = new J       Panel(    );
                                                           ri  ghtP.setBorde         r(Bo  rde   rFac  tory      
                                   .cr  ea        te Li      neB      orde r(Color.L           IGHT_G              RAY)      );     
                    rightP.setLayo     ut(new GridLayout(2, 1    , 5  , 5));
                        ri         ghtP.      add(imageP);    
                            rightP.a dd(iv  iewP);

                                vi   ewP.add    ( ri      ghtP);

                                         JPanel n            or   thP   = new J      Pane  l();
                                   no    rthP.setLayout(new Gr     idLayout(1, 2, 5, 5));
                                north   P.add      (   viewP);
                                  
                  viewP = ne  w JPa n   el      ();
                                vie  w       P.setLa yout( new        BorderLayo   ut());
                      nor       thP.add(vi    ewP);
                         
                      JPanel base   IndexP   = new J       Pa    nel  ();
                                   vi  ewP.add(bas   eI    ndexP, Borde    rLa     yout.NORT      H );
                                baseIndexP.setB      order(n  ew T  itledBorder("In   dex Ba                    se     "));
                                  bas    eIndexP.setLayout(n         ew GridLay     out(1, 2, 5,       5)) ;
                                                               base0Button   =           new           JRa    d     ioBu         tton  ("0-      bas       ed ")    ;
                   base1Butt      on = n  ew JRadio        B      u  tton(        "1-bas     ed "    );  
                                   B          utton Grou   p bgr         p = new But ton      Gr     oup  ();
                             bgrp.add(   base  0Button);
                              bgr      p.add          (base1B   utton);
                          if (ViewPropert   ies.is  Inde xBase1   ())    
                       	base1Button.setSe   l e   cted(true )    ;
                        els   e
                   	base0Button.setS    e  lected    (t    rue);
                            baseIn     dex P.a   dd(bas   e0Button);
                             baseInd   exP.add(base1  Bu t ton   );
                             
                        if           ( tclass == Dataty                          p  e    .             CLASS_CHAR
                                                      |      | (tcl    ass ==       Datatyp    e.   CL    AS     S_INTEG   ER    && sd
                                                       .getDa       tatyp        e()    .getDatatyp   eSi  ze()    < = 2 )) { 
                                  bitmaskButt     ons = new   JR    ad  ioB  u      tton[8 * sd.getDat             atype()
                                                       .    getDatatyp  eSi  z e     ()];
                         fo       r (int i = 0; i  < bitmas   kButtons.lengt   h;                   i++)                {
                                 bitmask   But     tons[i] = new JR  adioB                                                   utton(String .valueOf(     i));   
                                              bitma      sk  Bu           ttons[     i].setEnable        d(fals   e);
                                              bitmaskButt ons[i].addItemList       ene          r(this);
                                                       }

                                                                      JPanel    sheetP2 = new JPanel();
                    viewP.add(shee  tP2   , Borde   rLay    out.CE                    NT   ER);
                                           s    he      etP2.setBorder(new Tit ledBorder ("  B       it    mask") );

                    J Panel tmpP =      ne   w       JPanel();
                                    if(  b        itmaskButtons.leng  th <=8)   {
                                tmpP    .setLayout(n  ew Gri   dLayout(       1,            b     i   tmaskButto  ns.length)  ) ;
                               f     or (  int       i = b itmaskButto  ns.len     gth; i>0;        i--    )
                                                          t  mpP.a     dd(bitmaskB  uttons[i-1]);
                           }          
                       els  e{
                                               tmpP.se   tLa     yo  ut(new GridL   ayout(2, bitma                     s   kButt      ons.length         /      2)  );

                                                        for (int                i = bitmaskButtons.     le      ngth; i>bitma   skButtons.l ength   /2;               i--)
                                       tmpP.add(bitmaskBut   tons[i-1])   ;
                                  
                                   f  or (i    nt i = bitmas kButtons.length  /2; i>0; i     --)
                                                         tmp P.add(bitmaskButtons[i-1]);
                                 
                                     }
                       sheetP2.s       etLayout(new        BorderLayout(10,           10)          );
                                   sheetP2.add(tm    pP, Bo  r    derL    ayou         t.CEN        TER);
                     s               heetP2.    add(new JL     a bel(),    Bor   derLay  out.N          ORTH);   

                                        JPanel tmpP  2 =    new JPan   el();
                                   tmpP2.s etLa   yout(new    GridLa   y out(2,1));     
                                             tmpP2.add( extrac    tBitButto     n);
                          t   m    pP2.add(applyBitmask  Button);
                             tmpP =          new J           Pan      el();
                                              tmpP   .setLayout   (new BorderLayout());      
                           t m   pP.a    dd(tm    pP2, Borde rL    ayout.WEST)  ;
                                      t  mpP2 =          ne   w JPanel();
                               tmpP2.add(bi     tma   skHelp)   ;
                                           tmpP.add(t  mpP2,    BorderLayout     .EAST )  ;
                                       sheet   P   2.a  dd(tmpP, BorderLayo     ut.N   ORTH);
                                }   

                           content       Pa  ne.add(northP, BorderLayout.N  ORTH) ;
            }
            }   

         // se    tup GUI for dimensio   n an    d subse    t sel ec tio     n
        JPanel      select  ionP =   new     JPanel     ();
                 selec  tionP.      setLayout(new Grid     Layout(5,     6    , 10,      3));
                        sel ectionP.              setBorde    r(new E  tch   edB   order(Et   ch         edBorder             .LOWERED));

          centerP.a dd(selec     tionP, Borde  rLayout.CEN     TER);
                 contentPane.add(cente      r           P, Border         Layout.  CENT   ER);
 
              sel  ect    ionP.add( new J      Label(" "));
                              i f     (rank > 1)
                 s            election     P.add  (  tr  ans           po seCh  oi  ce);
            else
                    sel  ectionP.add                 (new JLabe   l("   ")  );    

                  JLabel labe    l = new JLabel("Start:");
                 selectionP.add(lab   el);
             label = new JLabel    ("  End:     ");
          se    lecti    o n   P.a      dd(label);
            label       =  ne      w JLa    bel("St   ride:" );
                s                  el   ection   P.add(l  abel);
               la  bel = new JLabel("     Max Size      "   );
                            select   io   nP.add(label);

        choi    ces  = new JCom  bo  Box[3];
             ma       xLabe ls =    ne    w    J  Label[3];
                              startFields  = n       ew JTextField[3];
            e             ndFields = n   ew JT  ext    Field[3];
                     str  i    deFields = new J TextFi   eld[3];
           JLab         e      l dimLabels[] = { new JLabel("H  eight",     SwingConstants  .RIGH     T)    ,
                                new JLabel   ("Width", S  wingCons    tants.RIG      HT),   
                    new JLabel (             "D  epth"     ,     SwingCon     stants.RIGHT), };

               String[] dimN            a   m      es     = datas            et.get                D   imNa mes();
         for (int i       = 0; i < 3; i++) {
                                    cho ices[i]                    = ne    w  JCombo   Bo   x   (); 
                c  hoice    s  [i   ]          .addItemListener(this)   ;    
                 for    (in    t          j = 0;    j   < ra   nk; j++                   ) {
                    if (dimNames = = null)     {    
                        choices[i].     ad     dItem(  "dim    "     + j);   
                 }
                       el     se {
                                               choices[        i].addItem(dimN     ames[           j])  ;
                                            }
              }   
                       maxLab  els         [i] = ne    w JL     abel("1");
             sta    rtFields[i] =       new JT  extField(   "0");
               e ndFie lds[       i] =    new JTex tFie  ld("0")    ;
                stri d eFields[i] = new J TextField(           "1     ");  
                 s    electionP.add(dimL   abels[i]);
            sel ect    ionP.add(choi ces[i]     )  ;    
            selectionP.add(sta   rtFi  elds[    i]  );
               sel      ectionP.add(e            n   dFi  e   ld   s[ i]);     
                      sel   ectionP.add(strideField s[  i]);
                          s     electionP.add(maxLa  bels[i]);

               /         /     dis   able the       selectio      n c  omponents
              //    in         it(  ) will     set the     m appropriate
                 choices[   i].setEnabled(   fals     e); 
              star   tFiel      ds[i].set       E    nabled(false);
                   end   Fi elds[i].setE   n     abl    ed(fal       se);    
            strideFields[  i].              setEnabled(false     );
                  max Label     s[i].setEn abled(false);      
         }

                           //   add button dim         ension selection when dimen    sion size >= 4
           JBut   ton  b utton = n       ew   JBut     ton("d      ims..."      );
               selectionP.a    d d(new JLabel("",    Sw ingConstan   ts.  RIGH    T));
              se        lecti  onP.a   d d(button);

              butt on.setActionCommand("            Se     le        ct mo re di   mensi                ons");
                        button                .add   Ac   tionLi  stener(this);
           butto       n.   setEn            abled((ra    nk > 3));
            selectionP.ad       d(n ew JLabel(" "));
           select    ion  P.add(n  ew JLabel   (" "));
        button = n        ew JButton(            "      Reset");
        butto             n.s   et        ActionCommand("R  eset   data range"        );
           button.addA ction       Listener(         this    );
            sele                cti  onP.add(b utton     );
        selectionP.a       dd(new JLabel(" "));  

        // add      OK a     nd CA    NCE      L b    utto   ns
                     JPanel confirmP = new JPanel();
             contentP    ane.ad   d(co      nfi     rmP , Border L ay out.SOUTH);
         button = new      JButt  on("   Ok      ");
           button.setMnemonic(Ke     yE        vent.VK_O )    ;
                   b               utto  n.setAc      tion   Command("Ok");
                   b     ut      ton.addActionListener(  this    );
        confirmP.add(    b   utton);
           button = new        JB    ut   t           on("C       ancel");
              button.s   etMn    emonic(   KeyEvent.VK_C);
              button.setA     ctionCom    mand("Cancel   ");
                    button       .a ddAct  ionListener(this);
        confirmP. ad  d(bu     tton);

        init();

                      // lo  c            ate the H5Prop    erty d     ial    og
                       Point l = getP  arent().    g    etLoc ati   on( ) ;
             l.x += 250;          
               l.y += 80    ;
             setLocation(l);
                  pack();
       }

    public          v          oid ac     tionPerform      e   d(ActionE  vent e)  {
        String cmd     = e.get   Actio    nC    om     man   d()     ;

        if (cmd.equal s  ( "Ok"))    {   
                        // set palette for      image vi e    w
            if ((dataset instance      o        f Sc    alarDS) && i            mage  Bu  tton.i     sSelected()) {
                          setPalet     te();
                    }

                               isSelec     tionCancell   ed = !setSe         lection();  

                          if (is            SelectionCanc  elled) {
                            r eturn;
                   }

                 if (da    ta  set instan    ceof Sc   alar   DS  )          {
                                        ((Scal      arDS) dataset).s   etIsIm       ageDispla y(i   m     ag  eBut  to    n.isSelected());
                     }

              dispose();
        }
        e   lse i    f  (c    md.equal          s("Canc      el")) {
                 disp  ose     ()         ;
               }
        els    e if     (   c      md.e     qua  ls(    "Reset           data range")) {
                 int n            = startFields.lengt    h;

                         for          (   i       nt i = 0;       i < n ; i++) {
                                     startFi  eld       s[i].set Text("0");
                  strid    eFields[i].setTe xt("1");
                     long l =        L       on  g.valu         eOf (ma  xLabels[i].g   etText()) -  1;
                       endFields[i].setText(String.valueOf(l));
            }
               }
             else     i     f (cmd.equal                 s("Select mo re di    mensio    ns")) {
               if (rank < 4)    {
                   ret   u        rn;
                                           }

                 int idx = 0;
                 Vecto   r<Obj          ect>   ch   oice4      = new     V ector<Object>(rank);
               int      []      c   ho    ice4Index = new in     t[rank - 3  ];
                for   (int i = 0; i < rank; i++) {
                                      i    f            ((i !=      curren     tIn    de         x[0]   ) &    &    (i   != currentIndex[1 ])
                                      && (i  != currentI         ndex[2]))     {
                              choic   e4.add(choice        s[0].g        et    I    tem   At(i));
                                                    choic     e4I           ndex[idx++] =         i;
                }      
                    }
   
                                    Str  ing msg = "Sele    ct sli           ce loca  tion f or dimensi     on(s):    \n\""   
                            + c  hoice4.get(0) +           " [0 ..      " + (dims[         choic   e        4Ind                        ex[0]] -   1)
                    +     "]\"";
                       Str  ing initValu      e      = String.valueOf(start  [choice4Ind          e  x[0]]);
                            in  t n = c       hoice4.siz e(       );
              for (int i = 1;     i < n;    i+ +)      {
                  m  sg += " x    \       ""    + choice4.get(i) + " [0 .. "
                                         + (     dims[choice4Index[i]  ] -      1) + "]\"" ;
                                  initValue += " x " +        Stri          ng.value       Of(sta      rt[choice4In     de           x[i    ]]);
            }
  
            S        tri   ng re          sult = JOptionPane.sho   wInputD        ial og(t  h is, msg, initValue);
                                                             i       f           (    (   resu    lt == nul   l) |   | ((result       = r       esult.t   rim()) == null)
                                   |   |      (res   ult.leng th() < 1     )) {
                                      ret  urn;    
                  }

                 S      tr  in gTo ke   nizer st =    new       StringT oke    nize  r(result , "x");
                 if (st.cou  ntTo    kens() < n) {
                  JOptionPane.showM  essag  e      Di    alog(this,
                                                    "Number   of dimensio    n(s) is les           s        than   " + n +     "\n"
                                                   +         result, "S   elect        Sli  ce Loc ation"   ,
                                    J   OptionPane.ERROR_   MESSAGE);
                      return; 
                  }
   
                  long[] st    ar  t4 = new long[n]   ;
               for (i   nt i =       0; i   < n; i++) {
                         try  {     
                                   s     tar t4  [i] = Long.pa     rseLong(  st.nextToken().t    rim());
                                  }
                                      catch (Exception ex)      {
                                              J   Option    Pane   .showMessage     Dial og    (this, ex.getMe   ssage (),
                                           "Sele   ct Sli     ce                          Loc          atio   n" , JOpti  onPane.ERROR_MESSAGE);
                        return;
                          }

                     if ((   start4[i     ] < 0) || (sta  rt4[i] >= dims[c ho      ice4Ind     ex[ i]])) {
                      JOp        tionPane.s   howM   essageDia    log(th             is,
                                        "            Slice lo ca   t  i  on is ou t      o         f ran ge.\n" + star  t4[i]
                                                 + " >=   " + dims[choice4Index[i]],    
                                                              "Se  lect Slice Lo cat  ion    ", JOpt    ionPane     .E   RROR_MESSAGE);
                                                 return;
                  }

              }
     
                       f  o      r (in             t    i       =      0; i < n; i ++)  {
                    start[ch   oice    4Index[      i]] = start4[i  ];
                              }
            } /  / els     e if (    cmd    .equals("Select m   ore dime    nsions"))
              else if (cmd        .equals(  "H      el            p on how   to s   et bi  tmask  ")) {
               Stri  ng msg       ="" 
                                 + "\"Apply Bi   tmask\" applie     s    bitwise \"AND\" to  the original             data.\ n"       
                + "For     example, bits 2, 3, an   d 4 are selecte   d for the bitmask\n"
                     + "                 10010101 (data)\n"
                                        + "A    ND   00011100 (mask)            \n"
                        + "  =     0001010  0 (result) ==> th  e    deci        ma     l value is 20.       \n"
            + "\n"
                           + "\"Extract Bit(s)\" removes al  l the bits from    the r      esult       above where\n"
                   + "their corresponding bi ts in the bitmask     are 0. \nFor  the same         example abo   ve, "
                  + "the     result is \n101 ==> the dec    imal value is    5.    \n\n";



                            JOptionPane.sho    wM        essage Dial     o   g((JFrame) viewer,   msg);
                 }
                 }

    publ ic void it    emStat   eChanged(ItemEvent e) {
    	       Object source =    e.getS      our  c   e();

    	i   f (s  ource.eq   uals(i           mageB ut  ton))
    	{
    		ch  oicePal     e      tte   .setEnabled(!isTr    ue Co    lorImage);
       		cho       ic   e  Im   ageView.setEnabled(true);
    		cho         i     ceTableV         iew.setEn  abled(fals   e );
    		   ch   arCheckbox.setS          elected(false);
    		charCheckbox.setEnabled(fals   e);
      	       } else if (source.equals(spreadsheetButton)                            )     {
       	   	cho  icePa  lette.s   etEnabled(false);
             		c     h                 oiceImageView.setEnabl    e  d(fal             se);
    		choiceTableV   iew.set        En    abl        ed(t rue);

        	   	     Datat    ype       dtype      = dataset.getDatat    ype();
          		int tclas    s =      dt     ype.   getData      typeClas   s();
    	   	charChe  ckbox.setEna  bled((   tclass    == Da    tatype.    CL ASS_CHA    R     || 
      				t  cla   ss == Datatype.CLASS    _INT     E  GER)&&          
            				(d     ty         pe. getDatatypeSize() == 1));
    	}        else if (source       inst   anceof JTog     gleBu tton   ) {
                  		che    ck       BitmaskButto      ns((JToggleButton)sourc  e)      ;
    	}
      	else if (source      in   sta  nceof JCombo       Box) {
              		if (!performJComboBoxEvent) {
                     			return;
    		}

    		i  f (e.getStat eChange() == Item        Event.DESELEC           TED) {
           			ret urn; // don't c     are       about the de  select
      		}
   
          	JComboBox t    he   Choice = (J        Comb o   Box)      sour       ce;

        	in   t t  heSelec     ted        Choic                 e = -1       ;

         	int n = M  a   th.min(3, rank)  ;
               	  for (int i =         0;      i <   n; i+   +) {    
                  		if (theCh            oice.equ        a     ls(choices[i])) {
        			theS    ele         cted  Choice           =   i;
                 		}      
          	  }

                 	if    (theSelectedChoice < 0) {
        		     re     turn;  //   th     e       selected     JCom b  oBox is no   t a       d    ime   ns                   i on choic e
        	}  
               
          	  i      nt         theIndex   =       theChoi   ce.getS  elec     tedIn   dex();
        	if   (     t    heIndex == current In   de   x[theSel  ecte dCh  oice])     {
                               	 	r eturn; //    select the sam   e item, no change   
          	}      

        	start[  currentIndex[t    heSelectedChoic         e]     ]                    = 0;

            	// reset the select      ed  dimension c     ho  ice
            	startFields[t    h           e         Sele    c      tedChoice]  .setText  ("0")    ;
           	e   ndF    ields[    theSel    ectedC    hoice].  setText(String
              	  		  .valueO   f(     dims[theIndex] - 1));
                     	   s  tri    deFields[th eSelectedCho   i ce       ].setTex t("1");
        	m axLab    el  s[       theSe  lec   tedChoice]           
        	                      .setText(String. valueOf(dims   [theInd     ex]));

             	//  i     f the sele  cted choice select    s the dimension that is select       e                d by
        	/      /   ot  her               dimension            choice,   exchange th          e        dimensions
        	for (int    i         = 0; i  < n; i+ +) {   
                   	     	if   (i == t  heS   elected   Ch       o           i      ce) {     
        	   		continue;  //               don't   ex    cha  nge itsel f
                  		}
              		    e       l   se   if (theIndex == choices[    i].getSelectedIndex())   {
        		 	setJCo   mboBoxSe   lec   t    edIn      dex(c               hoic  es [i],
               		 			cu           rren       tIndex                 [th eSelectedCh  oice    ]);
                             	  		    startFields[i].setText("0");
         			endFi   elds[i]
                		 	                .setText(Strin   g
                			              		  .val         ueOf(dim s[currentIn   dex      [  theSel  ectedChoice] ]    -     1));
                            			s      trideFields[   i]    .setTe  xt(     "1");
        			max          Lab       e       ls[i].s  et Text(Strin     g
          					.va      l  ueO    f(dims  [currentIndex[theS     electedChoic   e]]));
                  		}
                        	   }

        	f or (int i = 0; i < n         ; i++) {
            	        	curren   tIn      dex   [i] = c       hoic   es[i].g            etSelectedI      ndex();
                 	}
          
                     	// update the     navigato  r 
              	if (rank > 1  )    {
             	      	if    (is    Text) {
              			en  dFields                 [1].  setT   ext(sta         rt     F    ie     lds[1].get    Text ());
                  		}  
               		els   e        {
            	  		    in   t    hIdx  = choices[0                            ].ge tSelected    Index();      
                   			int wIdx =   choices[1    ].getSe  le ctedIndex(    );
        			transposeCho ice.setS  ele c tedI     nd     ex(0  )      ;

          	     		// Use transpos  e   o  pt    io   n    onl y  if the             dims    are    no      t     in or               iginal
                			// order
                   		   	if   (hIdx < w Id           x)
                                 			      	tran       spo    seCho ice.se     tEnab  led(false);
              			else
         				transpo  seChoice.setEnab    led(true);

                       			long d    ims[] = dataset.getDims();
                    	     		i               n       t w     =           (int) dims[wI  dx   ];
             		 	int         h =       (int)    d  ims[         hIdx   ];
         			navigator.s    etDimen  sionSize(w,      h);       
              			navigator.  u  pdat      e   UI();
                 		 }
            	    }

                       	if   (rank > 2) {
                               		end Fields[             2].se   t     T ex  t(st   art Fields[2             ].   getTe             xt()   );
              	}
        } // e     lse         i                f (       s  ource           in         stanc    eo    f JCom   boB     o        x)
    }   

    /**        Returns             true if                 the data s  e    l        ection is c          ance     lled. */
    publ  ic          b   ool  ean isCa       ncelled(  )   {
        retur     n isSelectionC  ancelled;
    }

    /** Returns tru   e   if t   he display  opt    ion   is i   mage   . */    
    public boo         l    ean isImage      D  isplay()      {
        r     etu           r  n imageButton.i  sSele cted();
          }
    
    public boolean       isI n      dexBase1() {
           	if   (bas     e1Butto n==null)     
            		ret urn false     ;
    	
    	 return ba   se1Button    .isSe    lected();
                        }

    /**              for dea  l with       bit mas  ks on       ly */
    privat       e    void     checkBi  tmaskBu     ttons(JT      o    gg   l     eButto        n s   o       urce) 
    {
      	boolean            b = f   al          se;
      	int n  =  0;
       	
             	if (sour ce.equal    s(applyBitma      skButton)) {
       		if                 (appl  yBitmas  k  B      utton.       isSelec    ted    ())
    			ex  tractBitButton.setSelec  ted         (fa    lse);
       	}      else if (sou   rce.equals(e         xtrac   tBitButton)) {
       		if (     extractBit But to    n .is   Sel         ected() )
    			applyBi   tmaskBu    tto     n.    set Sel    e   cted(false);
        	   }
       	  
      	b =   (apply B  i      tmask   B            utton.isS     elected() || ext          ractBitBu       tton.isSelec      ted(   ));
              	bitmaskButto    n s[  0           ].setEnabled   (  b)         ;  
     	if (     bitmaskB   u ttons[     0].isSe    l       e     cted())    
         		n =    1;
        	
               for (  int i = 1; i       <  bitmas  k      Buttons.       lengt h;  i++)                     {
                 b    itmaskButtons[  i         ].s   et            Enabled      (b);
               if (bitmaskBut          tons[i].isS  el ected()    && !bitmas    k    Butto     ns[i-1].i    s S                        elected          (      ))
               	n++;
                }
  
                       //       do         no  t     allo   w non-adja                   cent             select    ion for extractin                         g    bits
            if  (ext       ractBitBu tton.isSelec   ted() && n>1) {
                               
                      if  (source.e   q     uals(extra   ct  BitButton) &&            extractBitButton.               is        S    e          l   ected   ()) {
                       applyBitmas           k     Button.setSelected(true);
                                      JOptionPane.      sh    ow   Messa  geD    ia log             (this,
                              "Selec     ting n   o    n-ad      jac   e  nt bits is    only allowe    d \nfor the \   "App     ly            B itmas     k   \   "       option.",
                           "   S  elect Bit    mask",
                                          JO     ptionPa        ne.ERR      OR_ME    SSAGE)        ;
                    }     els    e i f (source   instanceof           JRadio    But  t  on){
                  J  OptionPa     ne.showM           essageD    ialo   g(this   ,
                            "Ple   ase           se     lect contig    uo    us    bit          s \nwhen      th e \      "Show V a   lue o       f S   elec            ted    Bits\" option is checked.",
                                               "Select B  itmask",
                                               J        Opti onPa ne.ERROR_MESSAGE);
                                s  ource   .setSele    cted(false)           ;              	
                    }
                          }       // if (extra          c  tBitButto n.isSelecte           d()        && n >1) {
    }
          
    /**
          * Set    the initial     st     ate of all    th   e      va                   riab    les
          */
          private voi d i    ni        t() {
                    // set      the im     ageb    u       t        t on   state
         boolea   n isIm      ag    e =             fal                se;

        if         (dataset       instanceof       Scalar   DS)                {
                    Scala         rDS sd = (Sc   ala  rDS) da   tase     t;
                           isImage  =  sd  .is      Image  D   isplay();
                isTrueC olorI  mag  e = sd.isT                     rueColor();
        }
               else    if (dataset ins  tanceo     f    Comp       oundDS)          {  
                             imag  eBut   ton.s  etEnable d(fal  se); 
            }
  
        cho             iceTableV    iew.setEnabled(!     isI  m        age);
        c hoice    ImageView.setE nab      led(i        sIm            age)   ;
             imageButton.setSele      cted       (i    sImage);
        choice Pale  t      te.se t              Enable    d(isImage       && !is   T         rueCo    lorI ma    g     e);

               int n   =     Mat        h   .min(3  ,      rank);
                l     ong e      n      dId     x = 0;
         for (i   nt i = 0; i    <      n  ;      i++) {                   
                     c hoices[i].s  etEnabled(tru    e);  
               startFields[i]    .setE   n   abled(true);
                           en          d          Field    s[i].setEnabled(          true);
                     st  rideFi  elds   [i].set       Enable    d(       tr     ue)   ;
                                         maxLabels [i].setEnable        d      (true                  );

                 int idx = sel    e    cted   Index[i];
                  endIdx =                           start[idx] +  s     ele        cte      d[i    dx] * stride[idx];
              if     (endIdx >= di ms[idx]) {
                    endIdx =    d i    ms[idx];  
                  }
  
                s      etJComboBoxSelect        edI ndex (c ho ices  [     i],      id           x);
               maxL abels[i]                       .setText(String.valueOf(di                ms[i    d x]));
            star  t        Fields[i]                .setTex   t(St             ring.va      lueOf(sta     r t[id     x]));    
                   endFields[i].s    et Text( Stri   ng.valueO        f(endIdx -     1));
             
                 if (!isH5 && (dataset     instan   ceof            Co  mp  ound      DS)) {
                             stri    deFields    [i].setEn  abled( false)         ;
            }
                else        {
                                     strideFields       [i]     .setText(String. value   Of    (str    ide[idx])   );
                    }
              }

              if (r    ank > 1) {
                        transp      oseChoi   ce
                     .setEnabled((c                       hoice  s[0].   getSel   ectedInde    x() > ch   o  ices[1    ]
                                                   .g  etSel    ectedIndex()   ));

                           if (i   sText) { 
                        endFiel         ds[  1].setEnable d(fals  e);       
                                e   ndFi   eld      s[1].setText(s   tart      Fields[1].g  etTe   xt());  
            }         
                    }

                  i       f (ran  k > 2) {
                              en               dFields              [          2].se   tEnabled    (   false);
                                       st     r             ideField   s[2].s  et       Enabled(       fa    lse)    ;
                                    if (isTrueColorIma    g e  &      & imageButton.is     Selecte d()) {
                   choices[0]. setEna b        led(fals    e);     
                             choices[1]   .se   tEna           bled(fa   lse);      
                              cho ices[      2].    set  Enab   led       (false);
                                       sta     rtF     i elds[2                    ].set    En  abled    (f               als          e);
                startF     iel   ds[2].setText("   0");
                          e          ndFields [2].setText("0");
                }
                 else {   
                    choice  s[0].   setEnabled(       t     rue)      ;
                   choi   ces[1                      ].setEnabl            ed(true);
                                  choices   [   2].setEnable   d(true);   
                                startFields[    2].      se   tEna bled(true);
                      st      ar   tF        ields[2].setText(String.value     Of(start [  sel     ected         Ind   ex[2]]   ));
                  // endFields[2].se tEn       able   d  (!i sText);
                            endField               s[2]. setText            (startFi   e    l         ds        [2].        getText());
                                     }
              }

                 for (int   i = 0; i < n;     i+ +) {
            c          urrentInde   x[i] = c        h    o ices[i].getSelectedI  ndex();  
        }   

                           /               / reset show char button
             Datatype dtype     = dataset    .getDatatyp   e()  ;
                  int t  class = dt        ype.getDataty    pe  Cla    s          s(   );
                               if (tclass       ==   Datatype    .CLASS_CHA R |  | tclass                     == Data       type.CLASS_INTEGER)     {
               int tsize = dtype.getDatatypeS  ize()  ;
                                     ch           arCh eckbox.setEnabled((ts     ize == 1) && spreadsheetButton.      isSelect ed());      
               extrac     tBitButto         n  .setEnabled(tsize   <  = 2   );
              applyBit  maskButton .setEnabled(tsize <=    2)    ;
        }
              else {
                charCheck     box.setEnabled (  fal    se);
                              charC   h eck       bo    x   .setSel         ect       ed        (f  alse);
                     extractBitButton.setEnabled(false);
            applyBit            m  as kButton.        setEnabled(false)    ;
            }
    }           

    /**
            *       JCom     b  oBox .setSelected     It       em() or                setSe l    ecte dIndex(  ) al w    ay      s    fires          ac  tion 
     * event. If y        ou call setSelectedI    tem() or setSelectedI       ndex() at
       * item       StateCh       a    ng   ed() o  r actionPe       rforme       d()       , the setSele  c  tedIte   m() or        
      * setSele    ctedIndex      () wil  l   make     loop cal   ls of itemS       tateCha     nge     d(    ) or
      * actionP erformed(). This  is not what  we want. We w   ant the
     *     setSelected    I  tem() or setSel ectedIndex()   be havio   r li     ke j av     a.awt.Choic   e.
     * Thi      s fl    a g is used t o serve this purpos     e.
                */
    pri           vate void  setJCom  bo    BoxSelectedI      ndex(JComboBox box,   int idx  ) {       
            per f    ormJComboBoxEvent = false  ;
                   box.setS    el   e   ctedIn          dex(i   d    x    );
                 performJComboBoxE   vent      =        t  rue ;
                 }

         p     rivate        void setPalette() {
        if (!(da  taset    instanceof ScalarDS))          {
               return;
              }

        byte  [][ ] pal = null;
        int p alCh       oice = choi       ceP    ale  tte.get    SelectedInd  e  x();

               if (palChoi    ce == 0) {
                         r      eturn; /* using d  efau lt pal   ette     */
              }

          if (palCh   oice == number  Of    Palett es + 1)    {
                    pal = Tools.createGrayPal  ette();  
           }
        else if    (p    alChoi    ce == nu m      berOf     Palettes + 2) {      
                 pal   = T   ool  s. cr   eateR  everseGrayPalette();
            }
                      else if     (palChoice =     = numberOfPale  t t    e   s +  3       ) {
            p    al = Too  ls.cr  ea   teGrayWav   ePale   tte();    
                  }
        el se       if (palCho         ice     ==           numberOfPa   lettes +   4) {
            p     a         l  = T       ools.creat  eRainbowPalette();
                                    }   
        e    lse if (palCho   ice =       =    nu   mbe  rOfPalett es +       5)          {    
                               pal =  T      o    ols.  createNatu    re     Pa    le       t  te();
            }
              els  e if (palChoice == numbe      rOfPa    lettes + 6) {
            pal =     T           ool  s .createWaveP      alet        te();
        }
                        else if ((palC      hoice        > 0  ) &&    (palChoice <= num  berO  fPal       ett       es)) {
                  // m        ultip le palettes     atta    c      he             d
                    pal = ((Sca   larDS      ) datase     t).re   adPale    t  te(palChoice -     1);
        }

         ((ScalarDS)              dataset).setPalette(pal);
    }

                private b      oolean se  tSel       ection     () {
                    long[]      n0 = { 0, 0, 0 }; // st      ar     t
           long[] n1            = { 0, 0, 0 };   // end
           long[] n2 = {       1  , 1, 1 }; // stri             de
        int[]    sIndex =    { 0,    1, 2 }       ;
           boolean retVal =   tru  e;

         int n = Math.min(3           , rank);
              for (i  nt i    = 0; i       < n; i++) {
                      sIndex[i] = cho  ices[  i] .getS         electe      d  I  nd           ex() ;

             try {
                           n0[i]  = Long.parseLong(st   ar     tFields[i].  getText  ()      );
                   if (i < 2) {
                                n1[i] = Lo     ng    .parseLon   g(end Fields[i].getTe   xt());
                            n  2[i] = Long   .pars   eLong(strid e  Fields[i].getText());
                                }       
                 }
              catch (NumberFor      matExce        pt ion ex) {
                   too  lkit.beep  ();
                        JOp   tionPane.showMessageDialog((JFrame) vie   wer, ex.getMessage(  ),
                                        getTi  tle(), J OptionPane.ERROR_MESSAGE);
                                   return false;  
                }     

                          // sile     ntly corr ect err  or            s
               if    (    n0[i]     < 0) {
                      n0[i] = 0; //                  start
                }
                          if (n0[i]  >=            di         ms[sIndex  [i]]) {
                      n       0[i] = dims[sIndex[i]] - 1;
                   }
                               if (n1[i] < 0) {
                           n1[i] = 0; // end
                           }
                       if (n1[i] >= dims[s     Index[i]]) {
                 n1[i] = dims[sI nd     ex[i]] - 1;
             }
                   if (n0[i] >  n1[      i]      ) {
                           n1[   i] = n0[i]  ; //    end <= s t   art
                    }
                        if (n2[i] >      dims[sIndex[i]       ])      {    
                       n2[i ] =        dims[sIndex[i]      ];
                         }
             if (n2[i] <= 0) {
                    n2[i] =      1; // stride    canno  t be zero
                }
        }   // f   o    r (int i=0; i<n;  i++)    

             if (datase           t     instanceof Co mpoundDS) {
                                  Compou  ndDS  d = (Compo    u            ndDS)   datas  et;
             i        nt[] selected     FieldIndi        ces = fieldList.  getSelec         t     edInd       ice s();
                 if   ((selectedFieldIndices == null)
                                       || (s  electedFieldInd           i  ces.length < 1   )  ) {
                    toolkit.beep();
                                 JOp  tionPan   e.show    Messag      eDia            lo  g((JFrame     ) viewer,
                          "No member /   field       is sel  ected     .", getTitle     (),
                                  JOptionP  a       ne.E  RROR_MESSAG    E);
                     return       fals   e;
            }

                   d.setMemberSelect  io  n(     false); // deselect al l members
            for    (int i   = 0; i  < selectedFieldIndices.len gth; i++  ) {
                  d.selectMem      ber(sel  ectedFieldIndices[i  ])     ;
              }
                   }    

                 // reset      selected s  ize
            for (in  t i = 0; i < rank    ; i++) {
            selected[i ] = 1;     
               stride[i] = 1;
                         } 
  
          // find no error, set selection the the data     set obje   ct
             for (   int i = 0; i < n; i+       +) {
               sele    ct    edIndex[i] = sIndex[i];
                start[s    ele ctedIndex[i]] = n0[i    ];
                i  f (   i < 2)       {
                             se      l     ec    ted[select   edIndex[i]] = (int) ((n     1[i] - n0[i] + 1) / n2[i]);
                stride       [selectedIn  dex[i]]    = n2[i];
                     }
              }
     
               if     ((rank > 1)        && isTe        xt) {
                                sele  cted[selectedIndex[1]] = 1;
            str  ide[selectedInde x[1]] =       1;
             }
             else if ((rank         > 2)   && isTru  eColorImage &  & im ageButt  on.isSel  e   cted()) {
            star     t[  se     lectedIndex[2]] = 0;
                s  e        l      ected[se  lec                 tedInde       x     [2  ]] = 3;
        }

         // clear the old d   ata
             dataset.clearData ();

            retVal = setBitmask();

               re  turn      retVa l;
    }

     private   boolean setBitm     as  k() 
      {
    	   boolean isAll=fals  e, is  Nothing   =f   alse;
        	
    	  if (bi   tmaskButtons == null) {
    		bitmask =  null;
                  		retu  rn tr  ue;
    	}
      	
    	if (!(applyBit  m  as k   Button.isS   elected     (     )  || extractBitButto n.isSelect ed())     ) {
       	   	bitmask       = null;
          		return true   ;
    	}

    	int len = bitmas  kButt   o     ns.length;
       	for (i     nt i             = 0; i <          l    en;      i++) {
     		isAll = (isAll && b  itmaskButtons    [     i].is  Selected());
    		i  sNothing = (isNot  h      ing && !bit  maskBu   ttons[   i].is   S  elect  ed(     ));
    	}
  
      	   if (isAll ||    isNo   thing) {
      		bitm             ask = null;  
    		return tr  ue;
    	}
    	
     	if (bitmask == null)
            		bi     tmask  = new    BitSet(len);

    	for (int         i    = 0; i < len; i++) {
    		bitmask.set(i  , bitmaskButtons[i].i   sS    elected())    ;
    	}

       	retur      n       t      rue;
    }
          
    /** S  ubsetNa    vigator draws se   le ction rectangle of  subset.    */
                          p     rivate class Previ        ewNavigato   r extends JComponent impl ements MouseListener,
                  MouseMotionListener {
            private static f    inal long serialVersionUID = -44581140  08420664965L;
           private final int NA      VIGATOR_SI   ZE = 150     ;
         pr   ivate i  nt dimX, dimY, x, y;
            privat  e dou     ble r;
        pri     vat    e Point startPosition    ; // mouse clicked po    s     ition
           private Recta     ngle selectedArea;
        private Image previewImage = null;

        privat  e Preview   Navigator      (int    w, int h)    {
                          dimX = w;
                           dim    Y = h;
                     if (dimX    > dimY) {
                x = N       AVIGATOR_SIZE;
                             r = dimX /      (double) x; 
                      y   = (in                   t) (dimY / r);
            }
              e lse {
                                 y = NAVIG           ATOR_SIZE;
                r = dimY / (double) y;
                                x   =    ( int) (dimX / r);
             }

                selectedArea       = n  ew Recta   ngle();
            s   etPreferre      dSize(new Dimension(NAVIGATOR_SIZE, NAVIGA    TO   R_SIZE));
             try             {
                              preview   Imag  e = creat    ePrevi    ewImage(); 
                    }
            catch (Exception e    x) {
                   ex.printSt ackTrace();
                           }

            addMo  useListen   er(this);
            addMouseMotionListener(this);
           }

              private Ima ge creat ePreviewImage() throws   Exception {
                          if ((  rank <= 1) ||   !(dataset  instanceof Sca      lar   DS)) {
                return nu  ll;
            }

             Image      preImage =   nul     l;
            Sca  larDS    s        d = (ScalarDS) d at     as     et;

                          if ( sd.isTe  xt()) {
                return null;
                       }       

            // backup the   se  l ecti     on
                    long[] strideBackup = new   long[rank];
                      long[] selectedBac    kup = new l on      g[rank];
               long[] startBa     ckup = new l ong[rank]      ;
                    int[] select     edIndexB           ackup = new int[3];
                       System.arrayco      py     (stride, 0, strideBackup  , 0, rank);
            System.arraycopy(s         elected, 0, se   lectedBackup, 0, rank);
              Sy       stem.  arr    aycopy(start, 0,     sta   rt      Backup, 0, rank);
            System.arraycopy(s    electedIndex, 0, selectedIndexBackup, 0, 3);

             // set the selection     for previ    ew
            for (int i    = 0  ; i < rank; i++)       {
                   start[i] = 0;
                str id e[i] =      1;
                 selected[i] = 1;
             }

            if (choices != null) {
                           selectedIndex  [0   ] = choices[0].getSel  ectedIndex();
                           s    e lectedInd  ex[1   ]       = choices[1].getS    elec  te dInde  x();
                   }
                   long steps = (long) Math.  ceil(r);
               selected[    selectedIndex[0    ]] = (dims[selectedIndex[0] ] / steps);
               selected[selectedIndex[1]] = (dims[selectedIndex[1]] / steps);
            strid    e[    sel  ect  edI       ndex[0]] = stride[selectedI         ndex[1]] = step  s;  

            i   f (selected[s     electedIndex[ 0]] == 0) {
                       se  lected[selectedIndex[0]] = 1;
               }
            if (selecte  d[sele ctedInd               ex[1]] == 0) {
                 selecte       d[selectedIndex[1]] = 1;
                         }

              if (isTrueColorImage && (start.l   ength > 2)  ) {
                 start[selectedIndex[2]] = 0;
                selec   ted[selectedInde  x[2]] =          3;
                stride[selectedIndex[2]]    = 1;
            }
       
            // update t  he rati    on   of preview im age s      ize to the    real dataset
            y = (int) selected[selectedIndex[0]];
            x =    (int) selected[selectedI  ndex[1]];
            r = Math.min((double) dims[selectedIndex[0]]
                        / (double) se    l ect    ed[selectedIndex[0]],
                            (double) dims[selectedIndex   [1]]
                                / (doub  le) selected[selectedIndex[1]])        ;

              try {
                         Objec   t data   = sd.read();
                Obje   ct fi    llValue = sd.getFillValue();
                if (fi   llV    alue !   = null){    
                    if(sd.isFillVa     lueConverte   d)
                        f     illV alue = ScalarDS.convertToUnsignedC(fillValue, null);
                       }
                byte         [] bData       = Tools.getByte  s(d    ata, sd.getImageDataRange(), fillVa  lue,      null);

                int h = sd.getHeight();
                  in t w = sd.getWidth();

                if (isT  rueColorImage) {
                    boolean isPlaneInter  lace =  (sd.g    etInterl    ace() == Sc    al  arDS.INTERLACE_PLANE);
                    p    reImage = Tools.      cre ateTrueColorImage(bData,
                                       isPlaneInterlace, w, h);
                }
                e    lse {
                    byte[][] imagePalette = sd.getPale     tte();
                              if (imagePale             tte == null) {
                          imageP  alette = Tools.createGrayP  a   lette();
                        }

                               if ((  isH5 || (rank >   2))
                            && (selectedIndex[0]   > selectedIndex[1])) {
                        // transpose data
                                         int n = bData   .l     ength;
                        byte[] bData2 = new byte [n];
                                       Syst        em.arr aycopy(bData, 0, bData2, 0, n);
                        for (int i = 0; i < h; i++) {
                              for       (int j = 0; j <    w; j++) {
                                              bData[i * w + j] = bData2[j *     h + i];
                               }
                        }
                     }  
                    if (!isH5 && !sd.isDe     faultI  mage     Order()&& (selectedIndex[1     ] > selectedIndex[0])) {
                        // transpose  data for hdf   4 images where selectedI      ndex[1] > selectedIndex[0]
                        in  t      n =   bData.length;
                           byte[] bD     ata2 = new byte[n]      ;
                         System.arrayco     p y(bData, 0, bData2, 0, n);
                             for (int i = 0; i < h; i++) {
                                   for (int j = 0; j < w; j++)     {
                                        bData[i * w + j] =   bData2[j * h + i     ];
                               }
                                   }
                                 }
                          preImage =    Tools.createIndexedImage(bData, imagePale tte, w,
                                         h);
                }
            }
                finall    y {
                // set back th  e origianl            selec  tion
                Sy   stem.arraycopy(strideBackup,     0, stride, 0, ran  k);
                System   .arraycopy(selectedBackup, 0, se  lected, 0, ran    k   );
                System.ar    raycopy(startBackup, 0, start, 0, rank);
                System.arraycopy(selectedIndexBackup, 0, selectedIndex, 0, 3);
            }

               return preI    mage;
           }

           @Override
         public void  pain   t(Gr  aphi     cs g) {
               g.setColor(Color.blue);

            if (previewImage != null) {
                g.drawImage(pre  viewIma   ge, 0, 0, this     );
            }
            else {
                      g.fillRect(0, 0, x, y);
              }

            int w    = selectedArea.width;
            int h = selecte dArea.height;
            if ((w > 0) && (h > 0)) {
                        g.setColor(Color.red);
                g.d    rawRect(select   edArea.x, selectedArea.y, w,      h);
                   }
        }

        public void mousePressed(MouseE    vent e) {
            startPosition = e.get    Point();
            selectedArea.  setBounds(startPosition.x, startPosition.y      , 0, 0);
           }

        public void mo    useClicke      d(MouseEvent e) {
                 start Position = e  .getPoint();
            selectedArea.setBounds(startPosition.x, startPosition.y, 0, 0);
            repa   int();
        }

        publ    ic void mouseDragged(MouseEvent e) {
              Point p0 = s  tartPosition;
            P     oint p1 = e.getPoint(   );

            int x0 = Math.max(0, Math.min(p0.x, p1.x));
                  int y0 = Math.max(0, Math.mi n(p0.y,    p1.y));
               int x1 = Math.mi  n(x, Ma   th.max(p0.x, p1.x));
            int y1 = Math.min(y, Math.max(p0.y, p1.y));  

                 int w = x1 - x0;
            int h =    y1 -   y0   ;
            selectedArea.setBounds(x0, y0, w, h);

             try         {
                updateSelection(x0, y0,    w, h)   ;
            }
            catch (Exception ex)     {
            }

            repaint();   
        }

        private void updateSelection(int x0, int y0, int w,   int  h) {
                int i0 = 0, i1     = 0;
            String selStr;

            i0 = (int) (y0 * r);
            if (i0 > dims[curre  ntIndex[0]]) {
                i0 = (int) dims[currentIndex[0]];
            }
            startFields[0].setText(String.valueOf(i0));

               i1 = (int) ((y0 + h) * r);

            if (i1 < i0) {
                  i1 = i0;
            }
            endFields[0].setText(String.valueOf(i1));

            selStr   = String.valueOf((int) (h * r));

            if (rank > 1) {
                i0 = (int     ) (x0 * r);
                if (i0 > dims[   current     Index[1]]) {
                     i0 = (in      t) dims[currentIndex[1]];
                  }
                     startFields[1].setText(String.valu       eOf(i0));

                i1 = (int) ((x0 + w) * r);
                     if (i1 < i   0) {
                          i1 = i0;
                   }
                 endFields[1].setTex  t(String.val   ueOf(i1));

                selStr += " x " + ((in   t) (w * r));
            }

            selLabel.setText(selStr);
        }

        public void mouseReleased(MouseEvent    e) {
          }

        public void mouseEntered(MouseEvent e) {
        }

          public void mouseExited(MouseEvent e) {
        }

        public void mouseMo    ved(MouseEvent e) {
        }

              private void setDimensionSize(int w, int h) {
            dimX = w;
            dimY = h;
            i  f (dimX > dimY) {
                   x = NAVIGATOR_SIZE;
                r = dimX / (double) x;
                   y = (int  ) (dimY / r);
            }
            else {
                y = NAVIGATOR_SIZE;
                r = dimY / (double) y;
                x = (int) (dimX / r);
            }
            setPreferredSize(new Dimension(NAVIGATOR_SIZE, NA    VIGATOR_SIZE));
            selectedArea.setSize(0, 0);
            try {
                previewImage = createPreviewImage();
            }
            catch (Exception ex) {
            }

            repaint();
        }
    } // private class SubsetNavigator extends JComponent

    /**
     * 
     * @return true if display the data as characters; otherwise, display as
     *         numbers.
     */
    public boolean isDisplayTypeChar() {
        return charCheckbox.isSelected();
    }

    /**
     * Return the bitmask.
     */
    public BitSet getBitmask() {
    	if (bitmask == null)
            return null;
    	
        if (!extractBitButton.isEnabled())
            return null;
        
        // do not use bitmask if it is empty (all b  its are zero)
        if (bitmask.isEmpty())
            return null;

        boolean     isAllSelected          = true;
        int size = bitmask.size();
        for (int i = 0; i < size; i++)
        	isAllSelected = (bitmask.get(i) && isAllSelected);

        // do not use bitmask if it is full (all bits are one)
        if (isAllSelected)
            return null;

        return bitmask;
    }
        
    /**
     * Check if it only apply    bitmask.
     */
    public boolean isApplyBitmaskOnly() 
    {
    	if (g   etBitmask()==null)
    		return false;
    	
    	return applyBitmaskButton.isSelected();
    }
    
      /**
     * 
     * @return true if transpose the data in 2D table; otherwise, do not
     *         transpose the data.
     */
    public boolean isTransposed() {
        return (transposeChoice.getSelectedIndex() == 1);
    }

    /** return the name of selected dataview */
    public String getDataViewName() {
        String viewName = null;

        if (isText) {
            viewName = (String) choiceTextView.getSelectedItem();
        }
        else if (isImageDisplay()) {
            viewName = (String) choiceImageView.getSelectedItem();
        }
        else {
            viewName = (String) choiceTableView.getSelectedItem();
        }

        return viewName;
    }
}

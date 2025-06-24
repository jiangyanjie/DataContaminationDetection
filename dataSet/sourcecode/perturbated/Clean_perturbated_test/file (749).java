/*
  * CPUDebuggerPanel.java 
 *
 * Created     on J    anuar   y 20, 2009, 8:35 PM
 *
 *  To change this template   , choose     Tools | Template                 Manager
      * and  o  pen the templ  ate in the editor.
 */
package emulator.nes.ui;

import java.awt.Dimension;
i mport java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import  java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.Acti     onListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
im port javax.swi     ng.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList   ;
import javax.swing.JPanel;
imp   ort javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swin  g.border.BevelBorder;
import javax.swing.border.TitledBorder;
import jav       ax.swing.text.MaskFormatter;

import utilities.GUIUtilities;
impor    t  emulator.nes.d    ebugger.NESDebuggerInterface;

 /**
 *
 * @author abailey
 */
publ    ic class CPUDebuggerPan  el         extends JPane    l {

    /**
	 * 
	  */
	priv ate static    final long serialVersionUID =      -7738767362699      460918L;
	  /** Cre     a               tes a new ins   tance    of CP      UDebug  gerPanel */
      NESDebuggerInte  r face _debugger = nul    l;

    public       C PUDebuggerPanel(NESDebugger   I     nterface     debugg  er)     {
            supe     r();
        _debugger = debugger;
          set    upUI(         );
    }

    pr    iv  at             e void setupUI   () {
             setBorder(new TitledBorder("Debugger"  ));
          FlowLayo   ut fl = new FlowL        ayo   ut   (Fl     owLa         yout  .LEF   T);
                       fl.setAlign  On         B    aseline(tru   e)       ;
           setLayout(fl); 

            JP    a      nel  buttonPane     l = constructButtonPanel();
        add (buttonP    anel);

        JPane   l pp   uPanel =   setupPPUDebug gerPanel();
                  ad  d  (ppuPanel)         ;

                 J  P       a nel breakpoin            tPanel = construct    BreakpointP        anel();    
        add(brea    k  pointP  anel)  ;

           JPanel             watchPanel = constructWa   tchPanel();
        add           (watch  Panel);

           JPanel logging    Panel =      co  nstructLog  g    in    gPanel();        
                  add(  logging Panel);
      }

               
    privat     e  JPanel constr  uct    ButtonPanel()     {   
               JPanel    panel =    n   ew  JP anel()    ;
                         pane     l.setBorder(new TitledBo      r     de r("      CPU"  )     ) ;    
        //pa  nel.  set    Layout(new F  lowL   ayo  ut (Fl o wLa           yout.LEFT));
                        p   an   el.set       Layout(n     ew G   rid    Layout   (2,2));
           {
                     String     tooltip                   = "Pause";  
                       String    i      ma   geP        ath = "em    ulator    /nes/ui/pa    us  e.       png";
                  ActionLi  st   ener actListener = new      Acti        onList   ener() {                  

                             public vo            id acti        onPe rformed(ActionEv      e     nt e) {
                           _debugg       er.pa  u                   s e ();
                            }
                       };
                                             JButton button =              null;
               I   ma                 geIcon          ic  on = GUI Utilities.createImageIcon       (ima    gePath,   to oltip)                           ;      
             i    f (ic on == null) {
                                    butt o      n    =   new JB utton(tool   t  ip);
              } else {
                               bu       tton = new JButt on(icon);       
                                  }
                button.s    etToo lTipTex    t(toolti     p);
                                          bu         tton         .addA      cti    onListener(actListener  );
                  panel.add(b    utton);
                  }

                {
                                           Str   ing t  o   olt       ip      = "    R  esume"    ; 
                  String ima geP      ath =       "emulator     /ne         s/ui/r       esume.png";
               Ac    tionListe   ner actL   istener    = new   Act    ionL                  i  ste n   er()     {   

                     pub   l ic void   actionPerforme   d(A         cti    onE    vent e   ) { 
                                    _de bug  ger.re   sume();
                    }
                  };
              JBut    to    n bu  tton = nu   ll        ;
                 ImageIcon icon             =   GUIU  tilitie     s.cre  ate       ImageIc               on    (i        mageP ath    , tooltip);    
                       if (   icon ==    null)             {
                button   = new JB   utton(to     oltip)  ;
            } else {
                                   button = ne w J Button(icon);
                       }
                     button.set     Tool Ti   pTex        t(tooltip);     
                 butt    on.a     ddA    ction     Li           ste                ner (actListener)       ;
                     pa  nel.add(button);
            }
                  {
                             String to          olti   p = "St   ep";
            Str i         ng imag    ePa            th = "                 em   ula     tor     /  ne  s/ui/step.png";
                  Actio   n  Listener actListener = new ActionLi   stener  ()     {

                             pu  blic          void actionPe     rfor       me    d(Act      i     onE       vent   e) {
                         _deb   ugger.s     tep     CPUI        nst              ruction     ();
                       }
                             };
                 JB      utton b   u       tton  = null;    
               ImageI      con icon =        GUIUti   lities.createImageIcon(im    ageP  ath, tool        tip);
                           if (icon =     = nul    l) {
                      b        utton = ne   w JBu            tton(tool    tip);   
              } else {    
                                          but  ton = new JButton(icon)   ;
                 }
                               button .set     ToolTipT     ex t(tooltip);
                            button.     addAction  List      ener(actListener)         ;
                  panel.add(butt    on)   ;
             }
        return panel;
    }

              pri   vate JPanel s   et   upPPUD ebuggerPanel(  ) {  
                JPanel panel = new JPan  el();
            panel      .    set   Bord   e    r(n    ew TitledBorder    ("PPU"))  ;
               //       panel.     se  tLayo         u  t(ne   w FlowLay    o ut(    Flo    wLa  yout.LE  FT));
        panel.se     tLay   out(new Grid L  ayout(2   ,1));
         {
                                     String to  olt ip = "Fram   e     Step" ;       
                        String image Path = "emula tor/n   e s/ui/frameStep        .png";
                 Imag eIcon i   con              = G  UIUtil         ities.c  reat    eI   mageIcon(ima    gePath, tooltip);
                                            JBut               ton butt  on = nul   l;
                     if   (i   con = = null)            {
                        bu      t       ton =    new        JButto       n(        tooltip);
            } else {
                       bu tton =  new       J      Bu     tton(icon);
                         }
                         
                Actio    nLis          tener ac      tL    istener     = new              ActionListen               e       r() {

                       pub  li  c void act   ionPerfor  med(Acti   onEvent e        )         {
                               _debu gger.ste  p   Next   Frame         ()      ;     
                 }
                                      };
                 butto   n .setTool               Ti    pText(too       ltip)       ;
                but  ton.addActio   n   Listener(a ctList   e   ner);
               p   anel  .add     (         b      utt      on);
            }

        {
                         String t       ooltip =             "Line St    ep";
                                  S     tring imagePath = "em  ul    ator/nes/ui/lineStep. png";
                ImageIco       n icon = GU   IU tili    ties.creat                   eImageIcon (imagePath, toolti  p);
                     JButton button   = null;    
                        if (icon            == null) {
                              button = new JBut   t     on(tooltip); 
                } else {
                     button = new    J       Butto   n(ic      on);
             }
                         
                   Acti    o    nL     istene   r   actLis         tener = new  Acti       onLis  te  ner() {  

                 public vo    id actio            nPerfo      rm    ed(Ac      tio nEven     t e) {
                                     _debug   ger.stepN            extS canline();
                                  }
                       };
                     button.setToolTipText(tooltip   )                 ; 
                but to                n.   addAct     ionLi               st ener(act   Liste    ne    r);
                   p           anel.add         (b              utton      );
           }
        re    turn      pane    l;
          }

          privat    e JPane   l cons        t   ruct WatchPane        l(  )    {
               JPanel panel                = new  JPanel();

           try {

               panel.s e    tBorder(new     TitledBorder("Watches"));
 
                       Gr     id      BagLayout gbl =    new       GridBagLay   out();
                          G ridBagConstra   in     t      s  gbc = new              GridBagCo    n    s       t     rai   nts(     );
               p    anel.setLayout( gbl);
                 GUI     Ut     il     ities.initi  al izeG BC    (gbc);
                  gbc.ancho   r         = GridBagConstraints.N      ORTHW  EST;

                         final Mas    kF orma   tter         forma  tt       er = new    MaskFormat  ter("0   xHHH  H");   
                   formatter.se  tPlace                 hol    de  r        ("0x0000");
              fina l JFormattedText             F iel   d hex            Field  =         new JFormatted      TextF       iel   d(f   orm    atter)   ;
                            fina       l JCh  eckBox readBo       x =              new J      CheckB    o x("R        ead   ");
                           f   ina    l JCheckB     ox       wr     it  eBox =      new JChe        ckBo        x("Wr        ite");

              fin   al       DefaultList  Model listMod     el =      new Defau  ltList Mod       el();
                       f    inal       JLis t watc                                    hL    i        s    t = new JList(listModel);
                  watc   hList .setSelectio       nMod           e(ListSelectionModel.SI  N              GLE_SELECT     ION);         
             wat chL     ist  .s            etVisibleRo w C   o     un                 t(4)    ;
                                        JScrollPan  e sc rPane    = new   J       Scr          ollPane(watc   hList   );
                         scrPa   ne.setP     r    e     ferr    ed   Size(new Dimension(80, 40));
            gbc.     gr   i dx = 0;
                               gbc.grid     y = 0;
                               gb   c.    gridwidt h    = 1;
                                   gb c.  grid h    eight =              2;
                   gbc.f  ill    = Gr     i     dBagCo ns                   tr      aint   s .BOTH; 
                   gbl.setCo      n     st   r         a   ints(sc    rPane  , gbc);
                    p  anel.add( scr   Pane);

               gbc.gr  idheight =      1    ;
                 JLa  be     l lab          el = new JLa bel("A      d      dre  ss:")          ;
             gb     c.grid    x               = 1;
                       gbl.se  tC   onst      raints   (label,         gbc);
                     panel.a     dd(l  abel)       ;

            gbc    .gridx     =      2;  
                                    gbl.  s     etC   onstraints   (hexField  , gbc);
                    pa    nel.add( hexField    )   ;   

                        gb          c.g  r   id        x = 1;
             g  bc.grid  y = 1;
                        gbl            .setC onst         raints     (       r ea    dBox, gbc);
                        panel  .add(readBox);

                   gbc.gridx    =   2;  
                     gbl.setC    onstraint     s(writeBox,  gbc);    
                     panel.   add              (   writeBox);

       
                      {     
                                       Str     ing title = "Ad  d";
                        S     tring toolt        ip = "Add Watch"       ;
                  A     ctio    nListener actLi    stene   r     = new Actio     nL  ist            ener()      {
         
                                                   public        voi d ac     tionPe          rformed(Ac   tionEvent e) {
                                               Stri               ng hexVal        =   hexFie  ld.getText();
                           Integer        intVal =     Int     eger.decode(h    ex                       Val)    ;
                         _debugger .a d   dWa   tch     (intVal.intValue( ), re      ad       Box.i     sSelected( ), w      ri   te       Bo  x.i s  Selected())    ;
                                                if (!listModel      .cont    ai   n      s       (                    he xVal            )) {
                                                        listModel.addEle    ment (       h    exVal);      
                                         }
                                   }
                            };
                        JButto  n       button = new     JBut  ton(title);
                          button.   set    T    oolTi   pText            (toolt  ip); 
                            bu           tt  on      .addAct     ionListen  er(actL     istener);
                                  gbc.     gridx    =       1;
                           gbc    .gr     idy = 2;
                                               gbl.setConst raints(button  ,         gbc);
                                                          pane l.add(bu  tton    );
                          }
              {
                           S      t                   ri       ng titl   e = "Rem   ove";
                     S   tri       ng  to    oltip = "  R  emove       Watch";
                          ActionL      is      tener   actListener = new Act      ionLis  tener(    )  {
   
                                         public void actionPe            rformed(Actio     nEvent    e) {  
                                          Str  ing h            exVal =    hexField.getTe      xt       ();
                              Integer     intV          al              = In teger.deco de(hexV      al  );
                             _de         bugg    er.r  emoveWatch(intVal.intV alue());
                                           if (listM     od   el.c    on  tai      ns( hex      Val  )) {
                                                           listM  odel.remo    v  eEle   ment(hexVa     l)  ;
                                              }
                              }
                  }  ;
                                             JButton but ton = new            J   But ton(                     tit    le);
                    b     utton.se t           Too    lTi    pText(toolti    p);
                           but   to n.addAct      ionLi     sten er(actL       istener    );       
                                  gbc .gridx = 0;
                                 gbc.gr   idwi  dth =         1;
                             gbc.gridy =   2;
                            gbl.setCo    n   straints  (butt on    , gbc)    ;
                     pa   nel.a  dd(  button  );
                     }       





        } catch (      Exce         ptio    n    e) {
            e      .       prin     tS     t      ac      kTrace();
                 } 
              ret   urn   panel;
               }

       private JPanel constr         uct Brea   kpoint        Panel() {                      
             JPanel     main     Pa  nel = n    ew JPanel();   
        m       a      inPanel.setB   or    de    r(new Titl           ed    Bo   rder       ("Breakpoints")    );        
          mainPan el.  set  Layout(n     ew G   rid Lay       o u t(1,2));
             try {

                       JPanel pan   el = new    JPanel(        );
             ma    inPanel.add(pa   nel);
              p       anel .     s                     etBorder(new TitledBorder("P  rogra  m       Co     unt      er  "));
              G  rid    B       agLa            yout   gbl = ne             w    Gr idBa  gL                   ayou     t();
                      Gr   idBagC onstraints gb     c      = new        Gr idBagConstrai     nts(   );
                         pan         el  .setLayout(           gbl);
                          GUIUtilit ies.init   ializeGBC(gbc)  ;
                         gbc.anchor = GridBagConstraints.NORTHWEST;
 
                              final Def   aul                    tListModel lis   tModel =    new De faultListMod  el(  );
               fi n    a  l JList     bpList = n   ew J Li st(list   Mo  de  l)           ;
              b   pLi st.s    etSel      ectionMode(    ListSelection    Mod      el            .SINGLE_SEL  E   CT  ION  );
                bpL              is  t .set                   Visible    RowCount(4)    ;
                                             J      Scroll Pane scr   Pa        ne   = n  ew JScrol  lPane(bpList)          ;
              scr  Pane      .setPreferredSize(new Dime  nsio    n(80, 40));   

                   final M        ask   Format   ter         fo   rma                tte         r = new MaskF       ormatter ("          0x      HHHH");
              f    ormatter.set     Placeho lder("0x8000");
                      final J  For    mattedTextFi        e       ld          h        exFie  ld = new          JFormatt edT   e         xt     Fi        eld(fo  r mat   te   r);
             hexF     ie  ld.          setBo     rder(new Be    ve   lB o    r der(BevelBo rder    .LOWERED)       );
                    {
                                                 S  tri   ng title = "Add";
                                     String toolt   ip = " Add CPU Breakpo   in t";  
                              A  ctionListener actListe       ner    =   n     ew Actio    nListener() {

                                          public void    ac   tion  Per  formed(Ac          t     ionE         ven             t e)      {
                                            Stri    ng hexVal  =     h   ex        Field.    getT  ext();  
                                                           Integer intV     al = Int   e       ger.decode(h  exVal);       
                                                      _  deb       u   gger  .addC     PUBreak     point  (intVal.i ntV alue( ));
                             if ( !  listModel.co     n            t       ains(hexVal)) {
                                                       list  Model.add     Element   (hex     Va           l    );
                                 }    
                               }          
                     };
                              J  B               utton bu tt on         = n    ew JButton(       titl    e);
                    but     ton.setToolTi     pTe  x     t(   too       ltip);
                                      butt   on.   addAc tion  Listener       (    act    Liste      n  er);
                                    gb              c.we    igh     tx = 0;
                      gbc.w  eighty  =  0;
                                 gb   c.gridx    = 1;
                                                   gbc.grid   y     =           2;
                           gbl  .s     etConstrai    nts    (button,            gbc);           
                              pane      l.  add(button);
                  }
                       {
                     St rin  g ti     tle = "R   emove ";
                             Stri     n   g toolt      ip = "Remo   ve CPU Breakpoint       ";
                                Ac     tio   nListener actListener = new A     c     tionList       en  er   () {      

                                             public void a               ctionPe   rf                 orme   d  (A    ct   ionEvent           e) {
                                      String  h  exVal =       hexFiel    d  .getText()  ;
                                Integ   er intVal = Integer.decode   (hexVal) ;
                                                  _debu  g      g  e    r.rem      oveCPUBrea  kpoint(intVal.intValue());
                                      if (    listModel.conta                ins(hexVa  l)) {
                                              listModel.remove   Elemen  t(hex  Val);
                                                            }
                         }
                };
                     JButton butt                   on = new JButton(title);
                          button  .setToo lTipText(tooltip);
                       button.addAc      ti   onLis       ten   e r(act     List     ener   );
                  gbc.  weightx = 0;    
                    gbc.  weighty =      0;
                                 gbc.gri           dx = 0;
                                  gbc.  gridy = 2;
                           g   bl.   setC onst   r aint       s(but        ton     , gbc);
                         panel.   add  (bu  t  ton);
            }

              gbc.we ig        htx = 0;
               gbc.we         i    ghty =   0;
                           gbc.gridx = 1;   
              gb   c.gridy =    0;
                    JLab   el    lab   el =     new JLabe  l("Addr   ess:");   
                gbl.setConstra     int  s  (label, gbc);
                 pa   nel.       add(          lab     el     );

            gbc.weightx = 0;
                         gbc.weig              hty        = 0;
            g bc.  g          ridx = 1;
                 gbc.gridy = 1;
                       gbl.setConstraints(   h      exField,  gbc);
                          panel .ad      d(hexField);


                        gb c.ancho   r =     GridBag     Constra   ints.N   ORTHWEST;
            gb         c.  gridx        = 0;
                g    bc.gridy = 0;
                 gb  c.gridwidth = 1;
                    gbc. g        ridheight = 2;
                   gbc.fill = GridBagCon      strain   ts.BOTH    ;
                    gbl.  setConstraints (scrPan       e, gbc);
            pa    nel.add(scrPan           e);

        } catch (Exception e) {   
               e.pri      nt   StackTr  ac    e();
        }
        
        // Opcode breakpo     ints
                try {
        	   JPan      el pan  e  l = ne         w JPanel();
                  m  ainPan   el.a         dd(panel);      
               panel.setBorder(n ew T  itledBorder("Opc  ode"));
              GridBagLayout gbl     = n   ew   G    ridBa  g   Lay    out();
                            G  ridBagConstraints  gbc     = n  ew G  ridBagConstraints();
            pa   nel.setLayo     ut(gbl)              ;
                     G  UIU     ti  lities.  init    ializeGBC(gb       c);   
                gbc.anchor = GridBa   gConstrain      ts.NOR THWEST;
                      
               fin     al  DefaultListModel list      M odel =                       n ew Default        ListModel();
            final JList      bpList = new JList(   listModel);
                b     pList.se    tSelectionMode(ListSele   cti onModel.SINGLE_SELECTION);
                                  bpList.setVisibleRowCount(4);
             JS       crollPane scrPan            e = ne     w JScr    ollPane(bpLi     st);
              sc   rPane. set    PreferredSize(     new     Dimension(80  , 4  0));

            final MaskF      orma      tter for matter = new MaskFormatter("0xH    H");
                formatt   er.setPlace     holder     (   "0x00"      );
                   fin   al JFormattedTextF ield hexFiel   d = ne        w JFo    rma       ttedTex   tField(formatter );
            hexField.setBorder(new Bev    elB  order(BevelBor          der.LOWER ED));
               {
                       String   title = "   Add"   ;
                           String    tooltip = "Add Opco  de Breakp  oint";  
                      A      ctionListener actListener = new Acti   onLis      te   ner     () {

                               public void actionPer  formed(ActionEvent e) {     
                                     Strin       g hexVal  =     he   xField.g      etText();
                                 In  teger intVal = Integer.decod          e(he        x  Val);
                                  _debugger.a   dd         Opcod   eBreakpoint  (intVal.byteValue());
                           if (!listM     odel.   contains(hexVal)) {
                                      listModel.addElement(hexVal);
                               }
                    }
                };
                                    JButt  on button = ne  w JButton   (title);
                  butt  on.setT       oo       lTipText   (tooltip);
                             button.addActionListener(actListen er);
                     gbc.weightx = 0;
                     gbc.weight  y = 0;   
                     gbc.gridx = 1;
                  gbc.gridy = 2;
                        gbl    .se   tConstra   ints(   b  utton,    gbc)  ;
                       panel.add(b  utt   on);
                 }
                          {
                              String title = "Rem  ove";
                String tooltip = "Remove Opco  de Breakpo       int";
                     ActionListene     r actLi sten er = ne     w Acti  on Listener() {

                    public void actio nPe rformed(ActionEvent e)       {
                             Str   ing hexVal = hexField.getText    ();
                            Integer in  tVal = Integ     er.decode(hexVal);
                               _d ebugger.removeO  pcodeBreakpoint(intVal.byteValue());
                              if (listModel.contains(hexVal)     ) {
                               l is             tModel.remov    eElement(hexVal);
                          }
                     }
                };
                       JB   utton button = new JButto       n(title);
                button.setToolTipText(too     ltip);
                            bu     tton.addActio  nListe    ner(actListene     r);
                gbc.weightx = 0;
                           gbc.weighty = 0;
                        gbc.gridx = 0;
                gb   c.gr   idy =   2;
                  gbl.setConstraints(button, gbc);
                          panel.add(button);
                }

            gbc.weightx = 0;    
            gbc.wei      ghty = 0;
               gbc.gridx = 1;
                 gbc.gridy = 0;
            JLabel label = new J   Label("Opcode:");
            gbl.setConstraints(lab  el, gbc);
            panel.add(label);

            gbc.weightx =        0;
            g  bc.weighty = 0;
            gbc.g   ridx = 1;
            gbc.gridy =       1;
                  gbl.setC onstra    ints(hexField, gbc);
            panel .add   (hexField);


            gbc.anchor =         GridBa    gConstraints.N  ORTHW    EST;
              gbc.gridx = 0;
            gbc.gridy = 0;
               gbc.grid     width = 1; 
            gbc.gridheight = 2;
            gbc.fill = GridBagConstraints.B    OTH;
            gbl.setConstraints(scrPane, gbc);
            panel.a    dd(scrPane);

        } catch (Exception e) {
              e.printStackTrace();
        }
        return mainPanel;
    }
        private JPanel construct  Logging    P     anel    () {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Log        ging"));
//        panel.setLa     yout(new FlowLayout(Fl   owLayout.LEFT));
        panel.setLayout(new GridLayout( 2,1));
        {
            String toolt  ip = "Start Loggi   ng";
            String imagePath = "emulator/nes/ui/capture.png";
            ActionListener actLis   tener = new A    ctionListener(   ) {

                public void ac   tionPerfor  med(ActionEvent e) {
                    _debugger.start      CPUCapture          ();
                }
            };
            JButton button = null;
            ImageIcon icon = GUIUtilities.createImageIcon(imagePath , tooltip);
            if (icon == null) {
                button = new JButton(tooltip)  ;
            } else {
                bu     tton = new JButton(ico   n);
            }
            button.setToolTipText(tooltip);
                 button.addActionListener(ac   tListener);
            pan  el.add(button);
        }
            // Should add icon buttons for frequent actions like reset, etc..
        {
            String tooltip = "Stop Logging";
                String imagePath = "emulator /nes/ui/capture.png";
            ActionListener actListener = new ActionLi   st     ener() {

                public void actionPerformed(ActionEvent e) {
                    _debugger.endCPUCapture();
                }
            };
            JButton button = null;
            ImageIcon icon = GUIUtilities.createImageIcon(imagePath, tooltip);
            if (icon == null) {
                button = new JButton(tooltip);
            } else {
                button = new JButton(icon);
            }
            button.setToolTipText(tooltip);
            button.addActionListener(actListener);
            panel.add(button);
        }
        return panel;
    }

}

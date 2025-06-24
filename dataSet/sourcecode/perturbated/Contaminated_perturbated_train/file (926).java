/********************************************************************************
 *    The   contents     of this file       a re subjec  t  to the     GNU Gener     al Public     Lice nse                             *
 *           (GPL) Version  2 or later (t          he "License");   you may no      t use this fi  le except   *
      *      in complianc      e with   the License. Yo       u may obt    ain       a copy o         f t   he      Licen  se      at      *
 *     htt    p://       www   .gn  u.o                                               rg/  cop         yleft/gpl. h   tml                                                                                                                       *
 *                                                                                                                                        *
      * Softw are d  ist          ribu           t   ed        under   t      h          e Li             cense      is distribut    e    d  on an "  AS     IS" b      asis,        *       
 * with       o  ut  w     arranty    of any k             ind,      either exp   ress      ed or im  pli    ed. Se     e the       L i   cense                *  
 *  f      or the speci  f ic lang   uage governin      g  ri    ghts and limitations     u nder   t  h             e                    *    
 * Lic    ense     .                                                                                                                   *
 *                                                                                                *
 * This   file was     o        r   igin       a                   l  ly developed as part of the soft   ware suit e th         at        *
 * sup    po rts the    book "The Elemen    ts of Comput  ing Sys       t        e    ms"     by Nisan and Schoc k            en         , *
 * MIT Pres       s 2005. If you modify the    conten  t      s of this file, pl      eas  e     document and *
  * mark y   our c    hange       s clearly, for the bene             fit    of        others.                                 *
   ***** ****            *******            ************     ****   ******************* ******     *******        ******* *********/

 pa   ckag        e simulators.G  UI; 

import jav     a.io       .*;  
imp    o  rt javax.  swing.   *;
import ja    vax.swing.even   t.*;

impor    t simulato   rs.     contr      o lle  rs.  *;

import java.awt.ev    ent.*;
i    mport java.awt  .*;
im    port j   ava.util.*;
   
 /**
 * T his class r            epresent  s th  e GU      I of   t he controller co    mponen    t.
 */
pub  lic class ControllerCompon      ent e       xten    ds JFrame impleme  nts ControllerGUI ,
                                                                              FilesTypeL     isten   er,
                                                                                              Br   eakpointsChangedL     is   te  ner {

    //    The dimen sio   ns of the tool ba          r.
    protected    static final  int TOOLBAR_WIDTH = 1016;
           protec  ted    static final int TOO L       B  AR       _HEIG   HT = 55;

    // The di      men sions of this window.
    privat   e sta t  ic final int CONTROLLE    R_WIDT      H = 102  4;
    private static final int CONTROLLER_HEIG    HT = 741;

    // The dimensions of the toolbar's sep  ara      tor.     
    protected static final Dimension sepa     ratorDi  mension = new D  i  mension(3, TOOLB   AR_H    E  I  GHT - 5);

    // The vecto   r of listen    ers to this component.
       private Vector listeners;

    // The fast f   orward button.
    prote cted Mouse     OverJButton ffw     dButton;
    
       // T     he stop button.
            protected M o  useOverJButton stopButton;

       //    The re     wind button.
    pro  te      cted Mous   eOverJButton rewindBut        ton;

        // The load script button.
    pr otected   MouseOverJButton scriptButton;

    // The breakpoints button.
    pro  tected MouseOverJ  Button brea     kBut   ton; 
  
     // The single ste  p              button.
        protecte        d    MouseO     verJButton singleSt       e pButton;

          // The   load p    rogram button.
    protected Mous         eOverJBu  tt    on       loadProgramButton;

    // Cre    atin       g the   file chooser window & the breakp    oint window.
       private JFil      eCh    ooser fileC   hooser = new JF     ile   Choo     ser();
    private Breakpo   intWindow breakpointWindow = new B         r   eakp   ointWindow();

    // Creating the icons for the buttons.
    private ImageIcon rewind    Icon   = new Im   age  Icon(Util  ities  .imagesDir + "vcrrewind.gif");
    private ImageIcon ffwdI   con = new ImageIcon(U      t ilit    ies.imagesDir + "vcr  fastfor   wa  rd.gif"); 
    private ImageIcon singleStepIcon = new I     mageIcon(Utilities.imagesDir + "vcrforward.gif");
    private   I  mageIcon stopIcon       =       new ImageIc   on(Utilities.im  agesDir + "vcr    stop.  gif     ");
    private  ImageIcon breakIcon      = new ImageIcon(Utilitie       s.imagesDi    r + "redflag.gif")    ;
    private ImageIcon loadProgramIcon = new ImageIcon(Utilities.imagesDir +    "opendoc.gif");
               private ImageIcon sc         rip tIcon = new ImageIcon(Utilities.imag      esDir + "  scroll.gif") ;

    // The   speed slider.
        protected JSlider speedSli     der;

    // A combo       box which co ntr      ols the f ormat of all the components.
    protected Title  dComboBox formatCombo;

    //    A combo bo x for choosing    the addi    tional dis  ply.
             protected TitledComboBox additionalDisplayCombo;

       // A combo bo    x for choo    sing the animation       type.
    protected  TitledCo   mboBox animationCo  m   bo;

    // The toolbar of the controller.
    protected JToolBar toolBa    r;

    // T    he compone nts of   t  he  m           enu    
         pr              otec  ted            JMenuBar menuBar;
             protected JMenu f      ile  Menu,   viewMenu, runMenu, helpMenu;
    p  rotected JMen  uItem s   ing     leSte    pMenuIte m, ffwdMen uItem,       sto  pMenuItem, rew indMenuItem, exit          M       enuItem;
    p  r  otected JMe    nuItem usage   MenuIt     em, aboutM    enuItem;
    prote cted JMenu animationSubMenu, numeri cFormatSubM    enu   ,     additionalDisplaySubMenu;
    pr     otec   te   d JMe  nuItem br   eakpoi      nts                   M  e       nuItem, scriptMenuItem, programMen uIte  m;
            pro  tected JRadioBu t   tonM enuItem    d        ecMen   uItem,             hexaMen      uIt    e      m, binM     enu    I   te    m;
     pro       tec   ted JR   adi   oButtonMenuItem    sc      riptDisplayMenuI tem, outputMe     nuItem, compareMenuI     t   em, noAdditionalDisplayM enu Item     ;
    protected JRadioButton   Me      nuItem partAnimMenuI   t  em, fu           llAnimMenuItem, noAnimMenuItem;

      // th e message lab  el (status           line)   
     pr   otected   JTex  tArea messa     geLb    l = n         ew   JTex      tArea();    
    prote                 cted JS   crollPa        n  e messageLblPane  ;     

        //     compon      ent for displaying      the s crip t, output file     and comparis    on f  ile.
    protected     Fil     eDisp layComponent scr  iptComp   onent     ;
    protected      Fil     eDispla  yCo   mponent outp   utComponent;
    prot      ected F     ileDisp    layComponent         compar  is      o      nCompone  nt;

    // HTML viewe      rs for the usa         ge      and   about windows.
                     private    H  TMLVie  wFrame usage  Window, ab  o    utWindo        w  ;

             /**
     * Cons    tructs a new ControllerCompone          nt.
     */
      publ             ic       Cont       ro lle  r Compone      n  t() {  
        li   st    eners    = new V   ector();
                   for        matCo      mbo = new TitledComboBox("Fo  r mat:"    , "Numer           i   c    display format",
                                                                 new String[]{"Decima     l",    "Hexadecimal", "B   inary    "}, 1   25);
        additionalDisplayCombo = new TitledC   omb  oBox("Vi    ew  :", "Vie   w  opti      ons",
                                                                                n      ew S tring[    ]{"Scrip   t", "Output", "Compar  e",   
                                                                                      "Screen"      }, 105);
        animationC  ombo =     new Tit     led    ComboBox("An  imate:", "Animtion type",
                                                                     new String[]{"Pr    ogr    am fl    ow", "P  rogram & data flow",
                                                         "No animation"}   , 175);
        scriptComponent = new FileD   isp  layCompon en  t(   );
         o  utp   utC    omponent =  new FileDi splayComponent();           
             co   mparisonComponent = n    ew       FileDisplayCompone    nt();
                
                   init();
                  jbInit();
      } 
     
    publi    c void    setWorkingDi  r(Fil   e file) {
           fileChoo     ser.setCurre   ntDirectory(file    );
      }

       public void setSim    ulator  (  H    ac  kSimulatorGUI   simula    tor) {
		     GridBag  Constraint  s c = new Grid   BagConst   raints ();
		c.fill = Gr        idBag   Const    rai  n        ts.BOTH;
		c.ipadx       = 0;
		c.             ipady = 0;
		       c.weightx = 1;
	  	c.w      eighty = 1;
		c.gridwidth      =    1;
		c.anch  o  r      = GridBag    Constraint    s.PAGE_START;
		c.g  ridx = 0   ;      
		c.           gridy = 1;
                 this.getContentPane(    ).ad     d((JC          o  mp   one   nt)simula  tor, c);
	
        /        /       ((   JComp   onent)simulator).setLoca     tion(0,TOOLBAR_HEIGHT);
         //   this  .getCo     n  tentPane().add((JComponen      t)    simulator, c);
        ((JComponent)s    imulator      ).revalidate();
        rep aint();

           if (simulator.getUsag    eFil     eNa me()     !=   null) {
            usag     eW indow = new HT      MLViewFrame(sim  ulator.get     UsageFileName());
                          usageWindow.setSize(450     , 430);
        }

        i      f (si mu lator.getAboutFileName() != null) {
            ab     out   Win     dow = new HTMLViewFrame(simulator.getAboutFileName());
              aboutWindow.setSi   ze(450, 420);
          }

		setV    isible(true);
    }       


    pu     blic JComponent getComp    arisonCom   ponent() {
        return co   mparisonC     omponen t;
    }
      public JComponent ge  tOutputComponent() {
        r   eturn outp     utCompo  nent;
            }
     pu     blic JC omponent getScriptComponent () {
        return sc  ri       ptCompon  e       nt;
       }

     /  / Initializes the buttons and sp eed   slider
    p  rotected void init()   {    
        speedSlider = n ew JSl       ider(JSlider.   HORIZON TAL, 1, Ha    ckControll e  r.NUMBER_OF_SPEED_UNITS, 1);
         l oad     ProgramButton = new MouseO ver          J  Button( );
            ff  wdButton = new MouseOve      rJB utton();
        s topButton = n    ew Mouse   OverJButton();
             re   wi    n          dB      utton = n     ew MouseO              verJButton();
        scriptB    utton = ne     w MouseOverJButton();
        breakButton = new MouseOverJButton();
                singleStep      Button           = new Mou   seOverJButton();
          }


                /**
         * Regis     ters the given Controll   er           Event        Li   s    tener as a listener to this        GUI.
                   */
    p      u    blic void ad              dC                    o    ntrollerListener(Cont roller         EventListe    ner l  istener) {
        lis           te ne   r s.addEleme    nt(listener);
    }

    /**
     * Un-       regis    te       r   s the give  n Contr    oll erEventList     ener from b eing a l        istener to this   GUI.
         */
     public void r     emoveContr        ollerListener(Cont    rollerEv    e ntListener l    iste   ner){
          liste     n ers  .remov    eElement(listene  r);
    }

    /**
     * Notify all the  Controll erEventL   isten ers on          actio   ns taken       in it,       by crea     ting a
         *  ControllerEvent (w    ith the action and s             upplied d    ata) and sending it using the
     * action  Performe    d me    t hod to all the lis  ten ers.
         */
        publi     c v        oid n   otif  yControl   lerListeners(by te action, Ob    ject data)   {
         Cont     roller   Even          t event     = ne w Con trol     lerEvent(this,actio        n,data);
            for(int i=0;i<l     is  teners.size();i    ++)
                 ((Contro        ller  EventLi        ste ner)listeners.el ementAt(i)).actionPerformed(eve               nt  );
    }

    /**
     *   Sets     the     scri   pt file na    me      with    the given one.
         */
    public     v      oid setScriptFile       (      String fileName) {   
        scriptComponent.setConte    nts(fileName);
    }

               /**
                * Sets the output file           name    with    th  e given           one.
     */
    pub     lic void setOutputFile(St   ring      f             il   eName)   {
        out          putComponent.setContents(fileN      ame);
    }

    /**
       * S    ets the compa   rison file name with the given one.
     */
    pub li c      v     oid setComparisonFile(String file  Name) {
            comparisonComponent.setConte    nts(fileNam           e);
    }

           /**
     *   Sets the      cur  ren t      s     cript      line.
     */
    publ     ic void setCur  rentS      criptL  in     e(int line  ) {
                           script          Compone    nt.setSel ected   Row(line);        

    }   
            
    /**
      * Set s the curre       nt outpu         t line     .
     */
      public           void s   e  tCu   rrentOutputLine   (int line) {
        out  putComponent.set       SelectedRow   (l   ine);
    }

        /**
     *    Sets the curre          nt comp    arison     line.
     */
                 public v        oid set  Curren     tCompar    isonL  ine(in    t line) {
           comparisonComponent.setSelectedRow(line);
       }

    /**        
     * Shows the bre   akpoint pane l.
      */
    public void showBreakpo   ints() {
          break  poi  ntW      indow.getTab     le()  .clearSel  ection    (  );
          breakpoin tWindow.setVisi   bl  e(  true);
        if (b    reak    pointWindow.getState() ==     Frame.ICO       NI  FIE  D) 
                 breakpointW          i    ndow .setSt      at   e(Frame       .NORMAL);
      }

     /**
     *   Ena   bles t   he sin    gle step action.
     */
           public      void enableSing leS     tep() {
         si                  ngle  StepButton.s  etEnabled    (true);
        singleStepMenuI    tem.   s  etEnabled(true  );
    }

     /*       *
                     * Disa    bles the sing  le    s   tep       action. 
             */
       public void disableS ingleSte                  p() {
        s  ingleStepButton.setEnabled(false  );
                sin    gleStepM     enu   Item.        setE  nabled(fals e);
    }

    /**
     *       Enable   s the fa     st for     w a     rd     acti     on.
        */
    public  vo  i d enableFastForward() {
          ffwdB  ut    ton.setEnabled(true);
        f  fw           dMenuItem. setEnabled(true);
       }

        /      **
     * Disab  les the fas   t   forwar     d action.
     */         
    publi  c vo id            dis    ableFastForward(     )     {
                                 ffwdBu    tt     on.setEnabled(false);  
        ffwdM     enu    Item.setEnabled(       false);
             }

    /*     *
     * Ena bles the stop action.
     */
    p      ublic void ena   bleS   top() {
          st     op  Butto  n.setEn  abled(t    rue)      ;
              sto   pM         enuI tem.setEn                  abled(true);
              }

    /**
     * Disables       th        e   s               top ac  tion.
         */
    public     voi d disableStop  ()            {
              stop  Button.setEnabled(false);
            stopMenuIte  m.s  etEnabled               (false   );
    }

       /**
     *    En     ables the    eject acti   on.
     */
    public void e  n   abl   e  Sc           ript() {
        script   But       ton  .   setEnabled(t  r ue);
        scriptMenu      Item.setEnabled (tru   e);
     }

         /*  *
             * Disabl              es the e               ject action.
           */
    public void     disab   leSc    ript        () {   
        s   cr  iptButton.se tEnabled(   fal    se);
        scriptMenuItem.       setEnabl ed(f     als       e);
      }

            /**
     *  Enable   s      t     he rewin   d actio    n.
     */
    public void enableRewind() {
           rewind    Bu  tton.setEnabled(tr      ue);
         rewindMenuItem  .setEnabl    ed(true);   
    }

    /**
     * Disables the rewind action.
     */
      p      ub       lic v   oid d    isableRewind()  {
                             rewi      ndB   utton.setEnabled(    false);
                   rew indMen   uItem.  setEnabled(false);
       }

    /* *
             * Enables                    the load    p  r  ogram action.
       */
    public  vo i d enable   LoadProgram() {
               load    Pr       o    gramButton.setE        nable   d(true);
         }

    /**
     * Disables the    lo    ad program acti  o        n.
     */
    pub         lic        void disabl    eLoadProgram()   {
        loadProg       ramButton.setEnable   d(f   alse);
    }

     /**
      *     Disab       les the    speed slider.     
     */
                p   ublic void  disableSpeedSlid      er(   )       {
            spe      edSli     der.setE     nabled   (false);
    }

        /**
             * En      ables      the spee        d slide r.
               */
    public void enableSpe   edSlider(  ) {
         speedS   lider.setEnabled(t        rue);
        }

     /    **   
     *    Disa    bles the animatio  n mode buttons  .
     */
    public v     oid disableAnimati        on    Mode  s() {   
        animatio   nCombo.se     tEn  a    bled(f     alse);
                         partAnimMenuIte              m.s      etE   nable   d(false);
        fullAni  mMenuItem.setE    nabled(fa    lse);
           noAnimMenuItem .setE  nabled(f    al  se);
       }

    /**
      *   Enables the animation mo        de buttons.
                  */
    public v oid enableAn  ima  tionModes() {
          anim    ationCombo.setEna     bled(true);
        pa  rtAnimMenuItem.setEn  abled(tr     ue); 
               fullAnimMenuItem.set  Enabled(true);
          noAnimM         enuItem.se  tEnable          d(true);
     }

                   /*              *
     * Sets the   bre  akpoints list with     the given one.
             */
      pub lic void setBr    ea      k p   oin   t  s(Vector   breakpoints)   {
        //     sending    the giv   e  n Vec    to            r to t   he   breakpoint p a     nel.
            brea         kp ointWi   ndow.   s      etBreakpoints(breakpoints);
       }

    /**
     * Sets the        speed (int code, between 1 and NUMBER_OF_       SPEED_UNTIS)
     *    /
                      p  ublic v  oid   setSpeed(int spee    d    ) {
        speedSli  der.setValue(speed     );
            r     e    paint();
    }

           /**
      * Sets the list of    recognized variables w    ith the giv en one.
        */
    publ    ic v          oid se    tVari  abl es (Strin  g[] newVars) {
        brea    kpointWindow.setVariables(n  e   wVars         );
    }

            /    **
      *    Ca    lled whe    n the names of the fil    e   s were  changed.
     * The   e     ve nt c     ontains the     three strings   re  presen    ting th   e names     of the
       * files.
      */
    p      ublic void file          sNamesChanged(File         sType  Event event) {
               if(event.ge   tFi     rstFile() !=  null) {
                   s criptC        omponen   t.setContents(eve    nt.get  FirstFile());
               n otify  Cont  roller  Listeners(Cont      ro  llerEvent   .SCRIPT_         CHANGE,event.  getFirstFile());
            }
               if(e  vent.getSecondFil    e() !      = null)   {
            outputComponent.setContents(event.getSecondFil     e      ());
                      }
                          if(event.g      etT  hirdFi le          () != null) {
                    compa risonC     o   mpone nt.s  e   tContents(even   t.    g    etTh    irdFile());
        }
    }

    /**
     * Called     when  there was a change in t       he breakpoints vector.
           * The ev     ent conta   i     n  s t   he vector of b     reakpoints.
         */
    p     ublic void    brea      k  pointsChanged(Break   pointsChan   gedEvent e  vent    ) {
                 notif        yContro     ller  Liste     ner         s(ControllerEvent.BREAKPOI NTS_  CHANGE, event.getB  reakp  oin   ts());
    }


    /**    
        * Calle  d w          hen      t     he output file is         updated.
          */    
            publ  i    c vo     i   d outputF i                 leUpdat   e d()    {
        outputComponent.refresh();
        }  

    /**
          * Hi     des the contr  oller.
     */
    pu blic void hi   d    eContr oller ()              {
        setVis     i  ble(fa  lse);
         }

    /**
       * Shows the c    on t    roll   er.
     */
       publ     ic v    oid   showContr  oller     () {
             setVisible(true);   
                      }
    
                          /   **
                 * Sets the a   nimation   mo  de      (int     code, out of t   he po       s      sibl    e a nimation constants i   n HackContr o ller)
     */
    public voi     d setAnimationMode(int mo     de    ) {
             if (!animat         ionCombo.i   sSelectedIndex(mode))
                animationCombo.  setSelect edIndex(mode );
    }           

    public void setAdditionalDisplay (int display  ) {
        if (!additionalDisplayCo   mbo.isSelectedInd      ex(d  is  p   lay))
            additionalDisplayCombo.setSelectedIndex(d      isplay);
      }
                 
    /**
       * Se    ts the nu       mer     ic format   with the given c  o de (out of   the f    ormat constants
     * in HackC  ontroller).
     */
    public void setNumericFormat(int     f    ormatCode) {
            if (!formatComb       o.isSele      ctedInde          x(format    Code)   )
            for    m      at       Combo.setSelectedIndex(format   Co     de);
          }

    p   ublic voi    d   d  i    splayMessage(String message, boolean       error) {
           if(error)
                mes    sa geLbl.setForeground(Color.red);
                         else      
                   messageLbl.setForeground(UIManager.getColor(        "Lab   el.f  oreground   ")    );
          messageLbl  .setText(m  essage);
             messageLb     l.   setTool  TipText(message) ;
    }

         /**
     *   Sets          the controlle   r's       size according to t    he size constants.
            *      /
    protected v    oid setControl    l   e  rSi ze  () {
            set       Size(new Dimensio   n(CONT    ROLLE      R_WIDTH  ,CO         NTROLLER_      HEIGHT));
    }

        /**
     * Adds      the controls to t   he toolbar.
      */
          protecte d  vo     id    arra                 ngeToolBar() {
                  toolBar.    add(loadProgramButton);
                   toolBar.addSeparator(separat    orDim          ension);   
        too    lBar.ad  d(       single    StepButt    on);    
        toolBar.add(ffwdButton   )  ;
        toolBar. a         dd(stopBu   tton);
                toolBar.add(rewindButton)      ;
            toolBar.addSeparato     r(separatorDi mensio      n);
          toolBar  .add(     scr     iptBu    tton);
            toolBar.a        dd(break  Bu     tt  on);
                        toolBar.addSep   arator(separato   rDimension);
        toolBa      r.add(speedSlid     er);
                  toolB ar.add(an    im     ationCombo);
        t   oolBa    r.ad       d(       additio   n       alDisplayCombo);
             tool Bar.add             (formatCombo    )     ;
         }

    /**
     *       Ad ds the      menu items t o the m enuber.
          */
    protecte  d void arrangeMenu() {

            // Build the f    i  r   st menu.
             fileMenu       = new J Menu(     "File");    
                    fileMenu.setMnem  onic(KeyEv   ent.VK_F);
              men  uBar.a  dd( fileMenu);
     
            viewMenu = new JMenu("View                    "     );
         viewMe   nu     .setMnemonic(KeyEv   en    t.VK_V  );
              men  uBar.add       (viewMenu);

        runMen   u = new J  Menu("Run");     
                  r          unMe   nu    .setMn   emonic (K                eyEvent.V    K_   R);
        menu    Bar.add(runMenu);

            //Buil   d t    he second      menu.
        he  lpMe     nu = new JMe    nu(  "   Help")   ;
        helpMenu.setMnemoni c(KeyEvent.VK_H)   ;
                  menuB    ar.add(helpMen   u);    

        progra mMenuItem    = new JMe  nuItem("Load Program", KeyEvent.VK_O  );    
        programMenuItem.ad      dActionLis tener     (ne  w Ac        tionLi   st   ener() {
                  public     void a   ctionPerforme   d        (Acti  onEve  n t e) {
                      programMen   uItem_a     ction      Perf   o         rm    e      d(e);
            }
            });
        fileMenu.ad   d(p rogr     amMenu   Item);

          s   criptMenuItem = n    e  w JMenuIte      m("Lo a d Script", Ke    yEvent.  VK_P    )    ;
        scriptM         enuItem.addActionList         en        er(new A     ctionListener() {
                p   ubl       ic       void ac  tionPerformed        (ActionEv    e  n t e) {
                   scriptMenuItem_actionP   erfor               med(e);      
                  }
         });
                 fil  eMenu.add(scriptMenuItem);
        fileMenu.addSeparator();

                exitMenuItem = new    J           MenuItem("Exit      ", KeyEvent.VK_X);
             exit   MenuIt    em.setAcceler at   or(KeyStroke.getK   eyStroke(KeyEvent.VK_X, A   ctionEvent.ALT_MASK) );
        exitMenuItem .   ad  dActio nLi  sten er(new A     c   ti     onListener     () {
                     public void actionPerformed(ActionEvent e) {
                        exitMenuIt  em_    actionPerformed(e);
                      }
        });
             fileMenu.add  (exitMenu   Item);

             vi     ewMenu.addSeparator(    );

        ButtonG   roup        animationRadioButtons  = ne w But   tonGroup();

        ani           mationSubMenu = new JMenu("Animate");
              animat   ion   SubMe      nu.setM     nemonic(KeyEvent.V K_A);
        viewMe  nu    .ad  d(animatio  nSubMen u)    ;

              p  artAn imMenuItem =    new JRadioButtonMenuIte     m("Pr  ogram  flow"    );
           partA          n  imMenuIt    em.setMnemon   ic(KeyEvent.     VK   _P);
           part AnimMe   nuIte     m .     setSelected(true);
                    pa  rtAnimMenuI  tem.addActionListener  (ne  w Act  ion   Lis     tener() {
                public void act    ionPerforme   d(ActionEvent e) {
                  partAnimMenuItem_act   ionPer forme   d(e);
                 }
                 });  
           animationRadioButt        ons.a    dd(partAnimM    enuItem);
          animation    SubMenu.add(partAnimMenuItem);
 
         f  ullAni mMenuI    t   em = ne  w JRadioButtonMenuItem("Program   & da        ta flow");
           fullA   nimMenuIte m.setMnemonic(K  ey       Event.VK_D);
        fullAnimMen   uItem.addActionListener(n  ew ActionL  is t        ene  r() {
                   public     void actionP   erfor med(ActionE  vent             e) {
                    f   ullAni   mMenuItem_actionP   erformed(e);
                                }
         });
             an     ima  tionRadioBu     ttons.add(fullAnimMe   nuI       te  m);
        animationSu bMenu            .add(f    u   ll          Anim     Menu  Item);

                     n   oAnimMenuItem = n       ew JRa    dioButtonMenuItem     ("No     An  im    ation");
        noAni   mMenuI  tem.setMn    emonic(KeyEvent.VK_N);
        n       oAnimMe       nu    Item.add   Act   ion    Listener(ne           w Actio  nListener()   {    
            public  void    action  Performed(ActionEven   t e) {
                        noAnimMenuItem_ac  tionP    erfor         m     e   d(e);
                     }
          }     );
         animationRadioButtons.add(noAnimMenuItem );
        animationSubMenu.ad  d(noAnimMenuItem);

             
        ButtonGroup      ad      ditionalDis   playRadioButtons  = n ew Button   G   roup(    );     

            a   dditionalDisplaySubMenu = new JMenu(" View");
        addi   tionalDi  sp   laySubMenu.setMnemonic(KeyEvent.VK_V);
                 viewMenu.add  (     add   it    ionalDispla   ySub   Menu    );

            scriptDisplayMenuItem = new J  Radi    oB    uttonMenuI       tem("Script");
                   scriptDisplayMe nuItem       .set   Mnem         onic(KeyEv   ent.VK_S);
            s           criptDispl      ayMe nuItem.setSelected(   true);
               s   cr ipt  DisplayMenuIte      m.a ddActionListener(new ActionLi  ste  ner() {
              public voi  d actionPer    formed(ActionEvent e) {
                                 s  criptDisp   lay    MenuItem   _a     ctionPer    formed(e)        ;
               }
           });
              additionalDisp      layRadio Buttons.add(scriptDisplay  MenuItem);
           addition     alDisplaySubMe     nu.add(scriptDisplayMenuItem);

            outputMenuItem  = new   JRadioBut   ton       Menu           Ite m("Out    pu t");
        out   p utMenuItem.setMnem onic(KeyEvent.VK_O);
           outp  utMenuIte   m.addActionListener(new Ac   t          io           nLis        tener () {  
            public   v   oid actionPe  rformed(Act   ion  Event e) {
                output   MenuItem_actionPerf     orme           d(e      ) ;
            }
              });
        additiona  lDisplay    RadioButtons.add(outputMenuItem);
          additionalD   isplaySubMe  nu.add(outputMenuItem);

            compareM   e      n   uItem = ne  w JRadioButtonMenuI       tem("Compare")        ;
        c  ompareMenuIt    e   m        .set      Mnemoni  c     (K       eyEvent .   V   K_C);  
              compareMenuItem.addActio  nListen  er(ne w     Act i           onLis tener() {
             public voi    d actionP   e        rformed(ActionEvent     e)    {     
                     compare      MenuI        te        m_a     ctionPe      r  form   ed(e);
             }
                       });
        addi   tionalDispla    yRadioBu    tt   ons.add(co  mpareMenuIte  m)      ;
          addit   ionalDi        s     play    Su      bMenu.ad     d(compareMenuItem);

          noAdditiona        lDispl    ayMenuI     tem = new J    RadioBu    ttonMen       uItem("Screen");
            noAdditionalD    isplayMenuItem.setMnemonic(K      e yEvent.V K        _N);
                 no AdditionalDi    s  playMen                  uItem.add      ActionListe ner       (ne          w       A   ctionListene r () {
              p   u   blic void    actionP   erformed(Action      Eve       nt e)   {
                           noAdditio    nalD     isplayMenuItem_actionPerformed(e);
             }
                   });
           additionalDi    splayRadioButtons.add(noAdditionalDisplayMenuItem);
           additiona   lD  isp   laySu  bMenu.add(  noAdditionalDisplayMe    nuItem);


            Button    Group fo       r  ma  tR adioB ut        tons =     new ButtonGroup    (  );

            numer  icFormatSub    Menu = new JMenu("Format" );
        numer   icFormatSubMenu.setMnemonic(KeyEv   ent. VK_F) ;
         viewMe                 n   u.add(numeri cFo  r  matSubMenu);

        decMenuIte       m     = ne    w JR       adioButtonMenuItem(     "Decimal         ");
              de     cMenu I     tem.setM    nemonic(KeyEv                ent.VK_D);
                         decMenuIt   em.setSelected(tr      ue);
            decMenu       Ite  m.addAct    ionListen               er(new       ActionListener(       ) {
                      public void actionPerformed(Actio       nEvent   e) {
                de cMenu Item_     ac   tionPerfor     med(e);
                    }
                  }   )  ;
        forma   tRadioBut   tons.add    (decMenuItem);
             n   umeric FormatSubMenu   .  a     dd(decMenu     Ite  m);

             he         x      aMenuItem = new J  R       adioButtonMenu   Item("  Hexade          cimal" );
          he   xaMenuItem.    setMnemonic     (KeyEven   t.  VK_     H );
             hexaMenuItem.addActionListener(new ActionListener()  {
                   public vo id   act  ionPerformed(      ActionEvent   e) {
                           he   xaMenu  Item_ac  tionPerformed(e);
                }
              });
        formatRadioButtons.add(hexaMen  uItem);
             nume   ri    cFormatSubMenu .  add(hexaMenuItem  );

        binMenuItem   = new JRadioBut    tonM  enuI   t em("B   in  ary");
        binMenuItem.set Mnemon   ic(KeyEvent.VK_B);
          binMenuItem.a d  d     Acti   onListen    er(new A ctionListener() {
            publ  ic void actionPerformed   (Acti  onE         vent e) {
                     binMenuItem_actionPerformed (e);
               }
            });
          format       RadioButtons.add  (b   inMenuItem);   
            n      um    ericFormatSubMe nu.   add(binMenuIte     m);

             viewMenu.addSe    para     to      r(  );      

                 singleStepMenuIte       m = ne   w JMenuItem("Single Step", KeyE    vent     . VK_S);
        s  in    g leStepMenuItem.setAcc ele   rator( Ke     y   Stroke.getKeySt roke("F11  "));
        s ingle StepMenuItem  .addActionListener(new ActionListener() {
               pub  lic void act     ionPerfor med(ActionEvent e) {
                    si   ngleStepMenuItem_act  ionPer f             ormed(e);
            }
        });
               ru      n   Menu.add(singleStepMenuItem);

                   f    fwdMenu       Item =         new  JMe   nuItem("    Run", KeyEvent.VK_F);
            ffwdMenuItem.setAccelerator(    KeyStroke.getKeyStro  k       e(         "F5"));
             ffwd MenuItem.addActi onListen er   (new ActionLis                  tener() {
                     publi  c       void actionPerf     ormed(   Actio  nEvent   e) {
                ffwdMenuItem_actionPerformed( e);
            }
        });
            ru    nMenu.a      dd(ffwdMenuItem);

                   stopMen  uItem = new JMenuItem("Stop", KeyEvent.VK_T);
        stopMenuItem.setAccel     erator(KeyStroke.getKeyStroke("shift F    5"));
                      st    opMenuItem.addActionListener(new           A   ct     ionListener(      ) {
                public        vo   id actionPerformed       (ActionE    vent      e)   {    
                stopM           enuItem_actionPerformed(    e);
                          }
           });
        run    Menu.add(s  topMen uItem);


                   rewindMenuItem = new    JMenuItem("Reset", K    eyEvent.VK _R);
               rewind   MenuIte    m.      addAct  ionListener(new ActionListener() {
                 public    void actionPerfo    rmed(Actio     n    E   ve   n    t e) {
                                   rewin   dMe  nuItem_actionPerforme    d(e);    
            }
        });
            runMenu.add(r   ewin  dMenuItem          ) ;

              r   unMenu .addSe       para              tor();

        b   re    akpointsMe   nuItem             =  new J MenuI tem("Breakpoin    ts", KeyE v   ent.V K_B);
        breakpo i         ntsMen    uItem.addActionListener(new ActionLi        stener() {
                  public void actionPerf   o    rme  d(ActionEvent e) {
                    brea       kpointsMenuItem_actionPerfor    med(e); 
            } 
            });
        run      Menu    .add(bre     akpointsMenuI tem);

               us    ageMenuItem =             new J  MenuItem("Usage", K    eyEvent.VK_   U            );
        usa   geMenuItem.setAccelerator(KeySt     roke.getKeyStroke("    F1"));
        usag    eMen     u    Item.addActionListene  r(   new Action      Listener()  {
                  pu    blic void actionPe           rformed    (ActionEvent e) {
                usageMenuItem_actionPerformed(e);
               }
              });
        helpMenu.add   (usa     ge   MenuItem);

        aboutMe    nuItem = new JMenuI   tem("About ...",       KeyEvent.VK_A);
           aboutMe      n   uItem.a  ddAc t        ionList    ener(new Action  L    ist  en er     (          )   {
              pub   lic void actionPerformed(ActionEvent  e) {
                   aboutMenuI tem_ac   tionPerformed(    e);
              }
             });
          he       lpMe nu.add(aboutMenuItem);

    }

     // called w      hen   the load  script  button is pressed.
    privat  e void scriptPressed() {
             int returnV   al = file     Choos  e   r.   s  howDialog(this, "    Load  Script");
        if(returnVa    l == JFil   eChooser.APPROVE_OPT    ION) {
                   notifyC  on        trol     lerListen   e   rs(ControllerEvent.SCRIPT_CHANGE,  fileChooser.get               Selecte dFil         e              ().getAbsolu    teFile())    ;
                     scriptCompon    ent.     setContent   s   (fileChooser.getSelectedFi               l        e(   ).getAbsoluteP     ath());    
           }
    }

    // Init       ializ   e     s this comp o   nent.
    p rivate voi         d jb     Init( )     {     
             f   i  leChooser.setFileFilter(new ScriptFil         eFilter()   );
          //t      hi     s.getCo  ntentPane().s    etLayout(null);             
		t                his   .getContentPane  ().  setLayout(new   Gr  i    dBag       Layout ());

        Hasht      able labelTable = new         Hashtab  le();          

        JLabel slo     wLabel =     new J    Labe  l("Slow   ");   
                 slowLabel.setFont   (Uti lities.thinLabelsFo   nt);
             JLabel fas    t  Label = new JLabe  l       ("Fast");
            fastLabel.setFon   t(Ut     iliti   es  .thinLab elsFont);
        labe lTabl   e. put(new Integer(1), slowLabel);
        labelTable.  pu     t   (new In te       ger(5 ), fast           Label);

              speedSlider.addC  hangeListener(new ChangeListe     ner() {
                pu     blic void stateC  hange     d(Chang       eEvent e)  {
                          SpeedSlider_stateChanged(e);
             }       
        });
        sp     eedSlider.setLabelTable(lab  elT  able);
             speedSlider.setMa         j            orTick  Spacing(   1);
               s peedSlider.setPaintTicks(true);
        speedSlider.  setPaintLabels(    true);
        speedS     lider           .setBorder(Borde      rFacto      ry.createEmptyBorder(0         ,0,5          ,0));
            spe       edSlider.setPreferredS  ize(new Dimensi   on(   95, 5    0))     ;
        speedSlider.set     MinimumSize(   new Dimension(  95, 50));
        speedSlider.se        tToolTi    pText("Spe e d");
           speedSlider.setMaxi        mumSize(new Dim e nsion     (95          ,     50));
   
         loadProgramButton.addActionListener(  new     ActionLis tener() {
                           public void actionPerfo   rmed(ActionEvent e) {
                                    loadProgramBu  tt o  n   _    actionPerformed(e)    ;
                 }
          });   
                  loadProgramButton.setMa        ximumSize(n  ew Dimension(  39, 3 9));
        loadProgramB           utton.setMinimumSize(new Dime    nsion(39, 39)); 
           loadProgramButton.setPrefer  redSize(new Dimens ion(39, 39));
        lo adProgramButt  on.setSize(new Dime          ns ion(39, 39))           ;
        l      o  a   dPr  ogramButt   on.setT    oo  l     TipT      ext("Load Pro     gram  ");
        lo ad ProgramButton.set     Icon(lo   adProgramIcon);

         ffwdBut       ton.setMaximumSize(new Dimensio    n(39, 39));
        ffwdButt   on.       se   tMinimumSize(new Dimensio  n    (39, 39));
        f fwdButton.setPreferredSize      (n  ew Di mension(3    9,              3      9));
           ffwdBut  ton.setToo lTipT   ext("Run");
               ffwdButton   .setIcon(ffwdIcon);
        ffw        dButton.a    ddActionListener(ne     w     Actio    n        Listener() {
              public void actionPerformed   (ActionEvent e)      {
                          ffwdButt   on_act          ionPerf    ormed(e);
               }
        });
   
             stopButt     on.a    ddActionListener(new ActionListener() {
                               public vo  id actionPerf orm ed(A      ctionE   ve  nt e) {
                                sto pButton_a            ction  Performed   (e);
                           }
        });
                sto   pButton.setM    a   ximumSize(  new Dimensi    on(39   ,       39));
           stopButton.        setMinim    umSize(new Dimension   (39  , 39));
                 sto pBu     tton.setPreferredSize(new Dimensi on(39    , 39));
        stopButton.setTool TipText("Stop");
        stopButton.setIcon(s       topIcon);

        rewindButto    n           .se tMax       im  umSize(ne     w Dimension(39 ,         39));
               rewindButton.s      e              tMinimumSize(new Dimension       (39, 39));
          rewindButton.setPreferredSiz   e(new Dimension(   39 , 39));
        re  w   indBu     tto n.setTool  TipT                 ext("Res et");
        rewin   dButton.setIcon(rewindIcon)  ;
                  rewi       ndButton.addActionListen     er(new Ac  tionLi      stener()  {
            pu  blic void acti onPer  fo  rm      ed(ActionEven t          e)     {
                               rewindButton_actionPerformed(e);
                        }
                   }     );

                   scriptButton.set     MaximumSize(new      Dimension(39, 39));
        s      criptButton.    se      tMinimum   Size(new Dimensi     on(39, 39));
                script         B        utton.setPreferred Size(new Dimensi  on(39  ,   39  ));
                scriptBut  ton.setTo  ol      TipText("Load Script"  );
                 script   Bu tton.setIco n(scriptIcon);
        scriptBu    tton.addActionLi  stener(n   ew      ActionListener() {
                  publi    c void actionP    erformed(      ActionEvent e)     {
                                scriptB utton_actionP   erformed        (e);
                  }
        });

            breakButton.addActionListener(new ActionListener() {
                          public   vo       id actionPerfor  med(ActionEvent e)  {
             breakB      utton_ac       tionPerformed(e);
                      }
        } );
        breakButton.setMaxi   mumSize(new Dimension(39, 39));
             br    eakButton.setMinimumSize(new Dimen   sion(39, 39)); 
        break  Bu      tton.s          e   tPrefe  rredSi ze    (new      D       im ens   ion(39, 39));
            break  Button.s    etTo  olTipTex  t("Open   b  rea  kpoin  t panel");
           breakB      utton .s   etIcon(bre akIcon);

        bre  akpoin   tW   indow.addBrea   kpointListener(this);
  
                    singleStep  Button .addActionListener(   new ActionLi         ste  ner() {     
                       public voi   d a  ctionPer f     ormed( Act          ionEven   t e)       {
                                singleStepBu     tton_actionPerfo    r   med(e) ;
              }
        });
                       singleSt    ep   Bu        tton.setMa     ximumSize(new Dime nsi   on( 39,        39    ));
               singleSt    epButton.se      tMinimumSize(new Dimension(39, 39));
                s         ingleStepBut ton.s      e tPreferredSize(   new Dimens  ion(    39, 39))  ;
               sing leStepButto    n.setSize(     new  Dimension(39, 39))   ;
          singleStepButton.setT                   oolTipText ("Single Step");
            s  ingleStepButt        on.setIcon(singleStepIcon);

        anim   ationCombo.addActionListener(new j a    v   a.awt.event.ActionListener()                 {
                  public void        actionPerfor    med(ActionEvent e) {
                                      ani     mationComb       o_actionPerformed(e);
            }
        });

        formatCo  mbo.a  ddActionL           istener            (new java. awt.e   vent.Acti       onLis            tener() {
                public void a  ctionP erf      ormed(ActionEvent e) {
                     for   m   atCombo_actionPe  rformed(e)  ;
                   }     
          });

         additionalDis     playCombo.addActionL isten     er(new    Action    Listener     ()   {      
            public void   actionPer  for med(ActionEvent e)    {
                additi    onal   DisplayCombo_actionPerformed(e);
                 }
                 })     ;
        
        
                            messageLb  l.setFont(Utilitie s.st atusLineFont);
        messageLbl.       setLineWrap(true);
                me ssageL  bl.setEditab      le(false)   ;
        me  ssag    eLb         l.setCursor(null   );
                     messageLbl.setOpaq ue( fals  e);
           messag   eLbl    .s   etFocusable(fal  se);
            messa    geLblPane = new JScrollP          ane(messageLbl);
             messa   g  e    LblPane.se             tBord  er(Bor    derFactory.cre  at   eL  oweredBeve  lBo  rde  r());
        messageLblPane.setBounds( new   Rectangle   (0,             667,      CO  NTROLL    ER_WIDTH - 8, 25));
		GridBagConstraints c =    new Gri    dBagConstrai  nts();
		c.fill    =      G    ri            dBagC     ons         traints.HORIZ    ON   TAL;
	  	c.ipadx    = 0;
		c.ipady =    0;
		    c.wei ghtx = 0;
		c    .  weighty = 0;
   		c.gridwidth = 1;
   		c.anchor =   GridBagCo      nstrai  nt  s.PAGE_END;
		c.gridx = 0;
	    	c.gridy = 2;
           this.getContentPane().add   (messageLblPane       , c);
        


        toolB           ar =     new   JToolBar();
              toolB  ar.setSize(new Dimension(TOOLBAR_WIDTH,TO     OLBAR_HEIGHT));
          t    oolBa   r.setLayout(n  ew Fl   owLayout(FlowLa yout.LEFT, 3 , 0));
        toolBar.setFloata   bl e(false);
           toolBar.setLocat ion(0,0);
               toolBar.setBorder(Bor  derFactory.createEtchedBorder());
         arrange  ToolB    ar();
		c.fill = GridBagC      onstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipa      dy = 0;
		c.weightx = 0;  
		c.weighty = 0;
		c  .gridwidth = 1;
		c    .anch    or = Grid      BagCo ns   train  ts.PAGE_START;
		c.g ridx = 0;
		c.gr   idy =  0;
        this.getContentPane(   ).add(toolBar,   c);
                         toolBa  r.reva     lidate();
               toolBar.    repaint ();   
          rep   aint();

          // Cr    e  at     ing the   menu bar
         men   uBa r   =    new JMe       nuBar();
              arrangeMenu();
        this.se  tJMenuBar(    menuBar)      ;

         this.setDefaultCloseOperation(3)  ;

                        setController      Size();

         // sets the frame to be visible            .
        //set Visible(t   rue);

          }

    /**
            * Called when a choic e wa        s made in the additional display    combo box.
           */
       p ublic void  additionalDisplayCombo_actionP  erformed     (     ActionEvent e) {
              int   selectedIndex    = additionalDisplayCombo.getSelectedIndex();
        switch (s           electe       dIndex) {
            case HackContr     oller.SCRIPT_ADDITIONAL_DISP  LA           Y:
                      if (   !s   criptMenuItem       .isSelected())
                    scriptMenuItem.  set         Selected(true);
                     brea   k;

                                 case HackController.O      UTPU    T_ADDITIONAL_DISPLAY:
                if (!outputMenuItem       .isSelected())
                       outputMenu   Item.setSelecte d(true);
                    break;

              case Hack  C   ontroller  .COMPARISON_ADDITIONAL_DISP    LAY:
                if (!compa    reMenuItem.isSelected())
                          co    mp   a         reMenuItem  .setSelected(t    rue);
                          break;

                         cas  e HackController.      NO_ADDITION  AL_  DISPLAY:
                     if (!noA   ddition     alDisplayMenuIt    em.isSelected())
                        noAdditionalDisp   layMenuItem.setSelec   ted(true);
                   break    ;
        }

        notifyControllerListeners(ControllerEvent.AD     DIT    IONAL_DISPLAY_CHANGE, new Integer(selectedIndex));
        }
  
    /**
     * Called when the load p   rogram button was pre  ssed.
       */
    public void load ProgramButton_actionPerformed(ActionEvent e) {
        no  tifyControl                        lerListeners(ControllerE        ve     nt.LOAD_PROGRAM,null);  
         }

    /**
     * Called w hen the fast forward but     ton was pressed.
               *   /
    public void ffwdButton_actionPerf     o     rmed(ActionEvent e) {
            notif     yC o     ntroll         e    rListeners   (Control l  erEvent.F  AST_  FORWARD,null);
         }

    /**
     * Called when t  he rewin   d butto    n was pressed.
     */
    public    vo  id rew        indButton_actionPerformed(ActionEvent e) {
        notifyController   Listeners(Co ntrollerEvent.REWIND, null);
    }   

    /**
       * Called when the load script b  utton was pres  s   ed.
        */
     public void script Button_actionPe rformed(ActionEvent e) {
        scriptP      ressed();
    }

            /**
      *         Called when the bre     akpoints button was presse   d.
     */
      p   ublic void  breakButton  _a     ctionPerfor me    d  (ActionEvent e)     {
         showBreakpoints( );
    }

          /**
     * Ca      lled when the single step    button was pressed.
     */
     public voi    d sing   leStepButton_actio  nPerformed(ActionEv   e    nt e) {
           notifyControllerLi     ste ners(    ControllerEvent.SINGLE  _STEP, null);
    }
 
    /**
              * Called when the     stop butto        n wa   s  pressed.
     */
    public void stopButto   n_actionPerformed(Act   ionEvent e) {
        no     tifyCo  ntrollerListener s(C ontrollerEvent.    STOP, null);
    }

    /**
         * Call  ed when the s  peed slider was moved .
     */
       public void SpeedSlider_sta     teCha    nged(Chang       eEvent e) {
        JS        lider source =    (JSlider)e.getSour c   e();
        if (         !source.getValueIsAdjusting()) {
                    int speed = (int)source.g    et  Value();
            notifyControllerListeners(ControllerEvent.SPEED_CHANG  E, new Integer(speed))    ;
        }
    }
   
    /**
     * C alled when a choice was made in the animati  on type combo box.
     */
     public void anim atio nCombo_actionPe  rformed(A    ctionEvent e) {
          int selectedIndex = a   nimationCombo        .getSelectedIndex()    ;
        switc h    (sele  c    tedIndex) {
            c       ase HackController.DISPLAY_CHANGES:
                    if (!partAnimMenuItem  .isSel  ecte     d())
                      partAnimMenuItem.setSelected(true     );
                  break;

                   case HackController.ANIMATION:
                     if (!fullAnim   MenuItem.i  sSe le   ct  ed()  )
                        fullAnimMenuItem.set   Selected(true);
                 break;

                      case HackCon  troll  er.NO_DISPL      AY_CHA NGES:
                  i   f (!noAnimM   enuItem.isSelected())
                    noAnim   MenuItem.setSelected(true);
                break    ;
           }

        n   otif yControllerListene  rs(Controll    er Event.ANIM  AT    ION_MODE_CHANGE,new Integer(selecte   dIndex));
    }

     /**
     * Calle   d when a choice was ma   de in t   he numeric format comb      o          box.
     */
    public void formatCombo_actionPerformed(ActionEvent e) {
            int selectedIndex = formatCo    mbo.getSelectedIndex();
        switch (selectedIndex) {
            case HackCo   ntroller.DECIMA  L_FORM   AT:
                if (!decMenuItem.isS         elected())
                    decMenuItem.setSel   ected(true);
                  break;

             case HackC             ontroller.HE        XA_FORMAT:
                  if (!hexaMenuItem.isSelected())
                         hexaMenuItem.setSelected(true)  ;
                 break;

            case HackController.BINARY_FORMAT:
                    if (!binMenuItem.isSelect      ed())
                    binMenuItem.setSelected(true);
                   break;  
           }

          notifyCont   r     ol     lerListeners(ControllerEv   ent.NUMERIC_FORMAT_CHANGE,new Inte ger(selectedIndex));
    }

    /**
     * Called when the load pro  gram menu item was selected.
          */
    publ ic void programMenu   Item_actionPerformed(Ac          tionEvent e) {
                 notifyContro llerList     eners(Controlle  rEvent.LOAD_PROGRAM, null);
       }

    /**
     * Called when the load script menu item was selected.
     *     /
    public void scriptMenuItem_act   ionPerformed(ActionE  vent e) {
          scriptPress       ed();
    }

    /**
     * Called when the exit men   u it              em was selected.
     */
    public  vo   id exitMenuItem_actionPerformed(ActionEvent e) {      
        System.  exit(0);
    }

    /**
     *      Called when the animation's "display changes" menu item was select ed.
     */
    public v   oid    partAnimMenuItem_a       ctionPerformed(ActionEvent e) {
                  if (!animationCombo.isSelectedIndex(HackController.DISP  LAY_CHANGES))
            animationCombo.setSelectedIndex(HackController.DISPLAY_CHA  NGES);
    }

    /**
     * Call   ed when the animation's "animate" menu item was selec  ted.
     */
    public void fullAnimMenuItem_actionPerformed(ActionEvent e) {
            if (!anim     ationCombo.isSelectedIndex(HackCon   troller. ANIMATION))
            animationCombo.setSelectedIndex(HackController.ANIMATION);
    }

    /**
        * Cal   led when the animation's "no animation" menu item w   as se   lected.
     */
    public v   oid noAnimMenuItem_  actionPerformed(ActionEvent e) {
        if (!animationCombo.isSelectedIndex(HackController.NO_DI   SPLAY_CHANGES))
            animationCombo.   setSelectedIndex(HackController.NO_DISPLAY_CHANGES);
    }

    /**
     * Called when the numeric format's "de cimal" menu item was selected.
     */
    public void decMenuItem_acti  onPerformed(ActionEvent e) {
        if (!formatCombo.isSelectedIndex(HackContro  ller.DECIMAL_FO RMAT))
            formatCombo.setSelectedIndex(HackC  ontroller.DECIMAL_FORMAT);
       }

    /**
     * Called when the numeric format's "hexa" menu item was selected.
     */
    public void  hexaMenuItem_actionPerformed(ActionEvent e  ) {      
        if (!formatCombo.isSelec  te    dIndex(HackController.HEXA_FORMAT))
               formatCombo.setSelectedIndex(HackController.HEXA_FORMAT);
    }

    /**
     * Called wh   en the numeric format's "     binary  " menu item was selected.
     */
    public v     oid binMenuItem_act   ionPerformed(ActionEvent e) {
        if (!formatCombo.isSelectedInde  x(HackController.BINARY_FORMAT))
            formatCombo.setSelectedIndex(HackContro     ller.BINARY_FORMAT);   
    }

    /**
      * Called when the additonal display's "script" menu item was selected.
     */
    public void scriptDisplayMenuItem_actionPerfo  rmed (ActionEven   t e) {
          if (!additi   onalDisplayCombo.isSelectedIndex(HackController.SCRIPT_ADDITIONAL_DISPLAY))
            additionalDisplayCombo.setSelectedIndex(HackController.SCRIPT_ADDITIONA     L_DIS    PLAY);
    }

    /**
     * Called when the ad  dit  onal     display's "output" menu item was selected.
        */
    public void outputM    enuItem_actionPerform   ed (ActionEvent e) {
        if (!additionalDisplayCombo.isSelectedIndex(HackController.OUTPUT_ADDITIONAL_DISPLAY))
            additionalDisplayCombo.setSel  ectedIndex(HackController.OUTPUT_ADDITIONAL_DISPLAY);
    }

    /**
     * Called when the additonal display's "comparison" menu i tem was selected.
         */
    public void compareMenuItem_actionPerformed (ActionEvent e) {
        if (!ad   ditionalDisplayCombo.isSelectedIndex(HackController.COMPARISON_ADDITIONAL_DISPLAY))
            additionalDis   playCombo.setSelectedInde   x(Hack   Controller.COMPARISON_ADDITIONAL_DISPLAY);
    }

    /**
     * Called when the additonal display's "no display" menu item was selected.
     */
    public vo   id noAdditionalDisplayMenuItem_actionPerformed (ActionEvent e) {
        if (!additionalDisplayCombo.isSelectedIndex(HackController.NO_ADDITIONAL_DISP LAY))
            additionalDisplayCombo.setSelectedIndex(HackController.NO_ADDITIONAL_DISPLAY);   
    }

    /**
     * Called when the single step menu item was selected.
     */
    public void singleStepMenuItem_actionPerformed(ActionEv ent e) {
        notifyControllerListeners(Contr    ollerEvent.SINGLE_STEP, null);
    }

    /**
     * Called when the fast forward menu item was selected.
     */
    public void ffwdMenuItem_actionPerformed(ActionEvent e) {
        notifyControlle      rListeners(ControllerEvent.FAST_FORWARD, null);
    }

    /**
     * Called when the stop       menu item was selected.
     */
    public void stopMenuItem_actionPerformed(ActionEvent e   ) {
        notifyControllerListeners(ControllerEvent.STOP, null);
    }

    /**
     * Called when the rewind menu item was selected.
     */      
    public void rewindMenuItem_actionPerformed(ActionEvent e) {
        notifyControl     lerListeners(ControllerEvent.REWIND, null);
    }

    /**
     * Called when the breakpoints menu item was selected.
     */
    public void breakpointsMenuItem_actionPerformed(ActionEvent e) {
        showBreakpoints();
    }

    /**
     * Called when the usage window menu item was selected.
     */
    public void usageMenuItem_actionPerformed(ActionEvent e) {
        if (usageWindow != null)
            usageWindow.setVisible(true);
    }

    /**
     * Called when the about window menu item was selected.
     */
    public void aboutMenuItem_actionPerformed(ActionEvent e) {
        if (aboutWindow != null)
            aboutWindow.setVisible(true);
    }
}

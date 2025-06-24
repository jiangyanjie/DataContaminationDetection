/********************************************************************************
      *      The contents of this file     are subject to the GNU    General Public  Lic    en se        *
 * (G  PL) Version        2 or later (the  "License    "); yo     u may        not use this  f  il                        e except   *
    * in compl     iance with the License. You may obt     ain a copy o       f th       e    L  i cens   e a  t                                                  *
                    * http://ww               w.gnu.org/              co   pyl          ef  t/gpl      .htm    l                                                                                                    *
 *                                                                                                          *
 * S o          ftware distributed un    de     r      the License    is distributed            on an               "A   S IS" b    asis,   *
 * wi   t     hout warra  nty   of any  kind, e   ith er    expr     essed     or impli              ed   . Se  e th       e L  i   cense   *
 * for the specifi c l ang     uage       governi n   g r       ig        hts a  nd limit  ation           s  u     nder the                                                              *
       *     Li c      en  se.                                                                                                                  *
        *                                                                                                               *
       * This file was originally developed as part of the software s       uite that        *
 * sup    ports the book "The Elements   of Computin g Systems"    by      N       isan and Schocken, *
 *        MIT Pre   ss 20  05. If you modify the contents of this file, please document a   nd   *
 * mark your changes clearly, for the   benefit    of others.                              *
 ********************    ******** *********************   ***************    ****************/

package simulators.CPU   Emulator;

import simulators.ComputerParts.*;
import si   mulators.Events.*;
imp   ort simulators.controllers      .*;
 import common.Conversion      s;
 import common.   Definitions;


/**
 * A   CP     U Em   ulat     or. Emula  tes machine cod e (In HACK format).
 *
      * Recognizes   the following  variables:
 * A - the ad      dress regist    er (s      h      o r  t)
 *   D -   the data       register (short)
 * PC - the program counter   (short)
 *   R   AM[i] - t      he contents of  the RA  M at location i (short)
 * ROM[i] - the cont      ents of the   ROM     at location i (short)
 * time - the time that passed      si   nce the pro gram started run       ning (long) - READ ONLY
 *
 * Reco  gnizes    th   e foll           owin   g co     mmands:
 * load <H      ACK fil e name  > - loa        ds    the given f   ile in   to th    e       ROM
 * Tick Toc   k -             advanc  es t  he clock by one ti  m     e unit (executes one    instruction)
 */
public class CP      UE     mul   ator extends HackS    imulator implements       ComputerPartErr  orEv   ent  Li    stene  r {


       // Va    riabl  es
    private static fin     al String VAR_A     = "A";
    private stati    c fi  nal S tring V  AR _D = "D";
    priv     ate            stat  ic final String VA   R_PC = "  PC    ";
    pr   ivate static final Str ing V AR_RAM =              "  RA   M";
       priva  t e s  tatic  final S                 tring       VAR_ROM = "R       OM";
    private    static f     ina  l String    VAR_TIME = "   time";

    // Comman  ds
     private static final String CO    M      MAND_TICKTOCK = "ticktock   ";      
    pri  vate static    final Str       i   ng CO MMAND_ROMLOAD = "load";    
    private static  final String COMMAND_SET      V         A             R      = "set";

    //                           The si   mu  lat    ing  cpu
    pr  iv  ate CP       U cpu;

         //    The   GUI of the CPUEm          u    lator
    pri    vate CPUEmulat  orGUI g         ui;

      / / The   list of r      ecogni         zed variables.
    private Str     i    ng[         ] vars;

    // The keyboard
       private K  eyboard keyboard;

    //  T        h   e current a      nimation mode
    private     int animationMo  de  ;

    /**
               * Construc     ts a new CPU         Emulator with no GUI compon                 ent.
      */
       public CP  UEm  ulat     or() {
            RAM ram = new RAM(nul  l, null, null           );
                        ra  m.reset();
  
                    ROM rom = new                 ROM(null);
             rom.reset();

        Poin terAddr     essReg  ist    erA  dap    ter A = new Point      erAddressRe  gist       erAdapter(null, ram);
                 A.reset(    );

                Register   D = new Re    gi  ster(null);
            D.rese    t(      );

        PointerAddres        sRegis  terAdapter      PC = new   PointerAd       dressReg   ist      erAda    pter(null, rom);
          PC.r        eset();
    
         keyboard  = new Keyboar                    d(ram,    null);
              k   ey         board.r eset(); 

           A          LU alu =     n   e     w ALU(n        ull  );
               alu.   reset();

               Bus bus     = new Bu    s(null);
           bus.reset();

                 cpu    =     new     CPU(ram    ,  rom,                  A, D, PC  , alu, bus);      

          ini     t   ();
    }

    /*  *
     * C    o       ns    tructs a new CPU Em  ulator with the given GUI compone       nt.
            */
    p      ublic    CP  UEm    ulator    (CPUEmu  latorGUI g   ui) {
           this     .gui = gui;

        RAM ram    = new RAM  (gui.getRAM(  ),     n     ull, gui.getScreen    ()); 
        ram .addErrorListe     ner(     this);
          r  am.reset();
 
        ROM ro       m  = ne      w            RO     M(gu   i.getROM());
             rom.a dd      ErrorLis    t  ener(this);  
                        rom.addPro  gramList     ener(this);     // lis    tens to program file change   
        ro     m.     reset();
        
            PointerAddr          e ssRegisterAdapt   er A = new PointerAdd r essR      egi   sterA  d              apter(gui    .getA(     ), ram);
           A.    addE   rrorLis   tener(thi    s);
        A.re    set   ();

             Register D = new Register(gui .g  etD());
              D.addErrorLi   stener   (this);
        D    .reset();

                 P                       ointerA   ddressRegisterAdapt      er P          C = new P oin          terAddressRegi      st           erAdapter(gui.getPC(),   r       om);
        PC.addErrorListe  n         er(this);
        PC.reset ();        

            keyboard = new   Keyboard(ram, gui.getKeyboa             rd());
              k      eyboard.reset();

         ALU a      lu  = new   ALU(gui.getALU())  ;    
        a    lu.res  et();

                Bus b  us =   new Bus(gui       .ge    tBus()     ) ; 
                  bus.reset();

                       c  pu =     new CPU     (ram, rom,      A , D, PC, alu, bus         );

        init();   
           }

        // In   itializes the e    mul  ator
      private     void i n   i     t() { 
                     va rs        = new String[]{VAR_A      , VA  R      _D, VAR_PC   , VA    R_RAM + "[]   ",     VAR_ROM + "[]", VAR_TI    ME};
           }

    pub  lic String getNam  e()   {
           return "CP U Em          ulat   or";
    }

          /  **
     * Returns the value         o   f the     given  variable     .
             * T   hr       ow   s Vari     ableExcept ion if t          he variable is          no   t legal.
            *  /
             public String    ge tVa  lue(String varN  ame) throws  VariableExceptio  n          {
            if (va      rName.e           q         uals(VAR_A))
               re  t   ur        n      Strin    g.value            O              f(cp      u.ge     tA().get());
           els e if (varNam    e.equals(    VAR_D))
                     r     et    urn String.va                     lu eOf     (cpu   .g      etD().    get() );
        e   lse if (varNa   me .equ     als( VAR_PC))
                 ret  urn String.valueOf(cpu.  ge   tPC().ge      t());
                   e    lse if (   varN  ame.equals(VAR_TI  ME)) 
            return String.val   ueOf(cpu.ge     t  Time(   ));
        e     lse if (varN ame.startsWith(VAR_RAM        + "     [")) {
                short                 index = get   R amInd   ex(var  Name);     
                      r       etur   n    Strin g.v        alueOf(cpu.getR     AM().getValue    A t(i  ndex));    
                   }
             else if  (varName.startsWith(VAR         _         ROM  + "["))  {
                sho     rt in dex  =    getRom    Inde      x(varNa me);
                                            re   tur   n          S     tring .value  Of(cpu.    ge t      R OM().getValueAt(     index));
                   }
        else
                  throw new VariableE    xception("   U          nknow     n       variab          le     "  , va  rN ame);
    }
    
    /**
        * Sets t   he g  iven variable    with the        gi    ven value.
     * Th  rows          VariableException if      the vari    able   nam   e or val    ue are no        t legal.
     */
    public vo id setValue(String     varName    , Str    ing value) throws V   a     riabl  eExceptio       n {
        i      n                 t numVa lue;

                                try {
                          value =    Co    n    vers  io      ns.toDe       cimalF     orm(va   lue);

                                  if (varNam e.e  qu      als(  VAR_A    )  ) {
                     numValue =   Intege        r.par     seInt   (value);
                           check_     ra m_address(      v   arNam   e, numV            al ue);
                                       cpu.getA().store((shor      t)numValue);
                               }
                    else    if (varName.equal  s(VAR_D))     {
                   numValue       = Integer.p   a     rs  eInt(valu    e);
                chec  k_valu   e              (    varName, num   Value);
                      cpu.g etD().  s    tore((sh                 ort) numVal        ue);
                }
             else       if (varName.equals     (VAR_PC)) {
                                  numVal       u      e = Int  eger.    par  seInt  (value);
                          che  ck      _rom_address(varName,      numValue)   ;
                            cpu  .get  PC()   .store((short)nu  mValue);
                                                   }    
                        else         if (var    Name.equals    (VAR_TIME))
                  throw new    Variable      Ex        cept  ion("Read    O  nly variable",       v  arName  );
            else if (varName.startsWith(V   AR_R   AM +    "[")) {
                  s     hort i   ndex =       getRam    Index(va      rName);
                   numValue =          Integer.parse    I nt(value)      ;    
                            che    ck_value(varNa      m    e, numVal                ue); 
                       cpu.getR AM(      ).setValueAt(in   dex, (sh               ort)n   u        mValue, false);
                        }
                   else if (varName    .startsWith  (VAR_ROM + " [ ")) {
                          short i  nde   x    = getRomIndex(va  rName);
                 numValue =         Inte  ger.parseInt   (valu e);
                     check_       value(var            Name, num Value);
                    cpu  .getROM().setValue  At  (index, (short) numValue, fa   lse);
            }   
                                 else
                      throw new           Va    r    i                ableEx             cep   tion("Unkno      wn var   ia  ble",   v  ar  Name);
        }   c atch (N  umberFormatExcepti    on nfe)  {
            t   hrow new   VariableException("'" + value + "' is n   ot a legal value fo  r variabl     e",
                                                                  va        rNam   e  );
        }
    }

       /**   
           * Executes th e given si  mulator command         (given      i    n args[  ] st      yl  e).    
       * Th      rows   CommandException if the command          is not legal.
         * Throws     P  rog       ramExcepti          on if     a n e    rror  occurs in the program.
     */
      pu      blic void d    oCo       mmand(Strin  g[ ] command)
     throws Comm   andExceptio  n    , Pr          ogramEx       ception  ,   Variab leExcept   ion {
        if (command  .length   == 0)
                 throw     n   ew CommandException("Em p  ty       command", com  mand);

          //     hide        gui highlights
         i      f (animati onMode != HackCont    roller.NO_DI  SPLAY_CHANG     ES)  
              hideHi  ghligh  tes();

                                  // execute the appropriate    command
                if (command[0]    .e     quals(  COMMAND_TICKTOCK  ))    {
                     if (           command.length  !=  1)
                thro  w n     ew Comm   and     Exception("Ille gal  number    of        arguments to command", com     mand);
  
             cpu.exe          cuteInstruc   tion();
        }
           else i f (comm      and[0].eq    u     a  ls(COM     MAND_SETVA        R)) {
               if        (command.leng       th !=       3)
                       thr   ow ne          w Com       mandException( "Ill      egal numbe    r o f arguments to command", command);
                      setVa lue(com mand[  1     ], comm     and[   2]);     
           }
             else        if (command[0]. equals(COMMAN    D_ROML  OAD)) {       
                        i f (comma          nd.length != 2)
                th  row new CommandExcept ion("Illegal num    ber         of arguments to      comman   d", com  ma    n    d    );

                                   S   tring  file N     ame = wo   r  k      ingDi r.getAbsol    u   tePath() + "/"     + com   man    d[1];
                  cpu.getROM  ().   loadProgra m(fileNa me);
             int ol  dAnimationMode    = animati    onM    ode;
               s   etAnimati    onMode(HackC    ontrol          l  e      r.DISPLA      Y_CHANGES);
                               c        pu.in  itP rogram(  );
                setAnimationMode(old            AnimationM  ode);
            }
               el    se
                  thro      w new CommandException("U  n    know  n simulato      r comman   d",      command);
    }

         //       Hi  des  al  l           h   ighli g  hts i      n GUIs.
    private void hideHighlightes() {
        cpu.g    e      tRAM().hideHighlight()   ;
                 cpu.getROM().hideHighlight();
                        cpu.g etA().hideHighl    ight();
           cpu.getD().hideHig     hlight();
        cpu.get    PC().hideHi  ghlight();
          cp  u.getA       LU().   hideHighligh  t();
         }

    /**
                * Restarts the     CPU  Emulator - p                  rogram will be r  est  arted.
     */
    pub       lic void restart  () {
          cpu.initPro  gram();
    }

    public void setAnima    tionMo  de(i     nt newAni m ationMode) {
                              if (gui != null) {
                      / / enter N    O_DISPLAY_CHANGES
                        if (newAnimation    Mod   e == HackC   ontroller.NO_DISPLAY_    CHANGES  &&
                       an   imationMode != Hack Controller.NO_DISPLAY_   C      HANGES) {
                cpu.get  RAM(). dis ableUserIn  put(); 
                 cpu.getROM   ().   disableUserInput();
                      cpu.       ge  tA().disabl  eUse    rInput    ();
                                              cp u.getD().disableUser   Input();
                              cpu     .ge   tPC().disableUserInp  ut   ();

                                 S creenGUI screen           = gui.getScre  en();
                            if    (sc        reen !=       nu  ll)
                                            screen.startAnima    t      ion();
                    }

             // exit NO_DISPLAY_CHANGE    S
                          if (new     A     nimatio  nMo   de    !=   HackContro ller.NO_ D        IS     PLAY_       CHANGES &&
                        animatio       nMode =   = Hack   Control    l      er  .  NO_DISPLAY_    C         HA   NGES) {
                cpu .ge    tRAM().enableUserInput();
                                     cpu.getROM ().enableUserInput(); 
                cpu    .getA().enableUserInput();
                      cpu.getD().enableUserInput();
                        cpu.getPC()   .enableUser  Inp           ut();

                        Screen    GUI screen = gui.getScr    ee   n();
                                if (screen !     = null)
                           screen.  stopAn  im    ation();
              }
        }

        animationMod    e = newAnimationMode;

                   boolean anima     te = (anima   tionMode     == HackController.ANIMATION);
        c pu.getBus().se    tAnima   te(animate);
           cpu.getRAM().setAnimate(animate);
           cpu.getROM().setAnimate(a      nimate);      
               cpu.getA() .setAn           imate(animate);
        cp  u.getD     ().    setAnim  ate(a ni   mate);
        cpu.getPC().setAnimate(animat   e);
             cp u.getALU()   .s etAnimat e(anima te)    ;  
 
          boolean displayChanges = (animati    onMode != HackControlle        r.NO_DI  SP      LAY  _CHANGE S)   ;
                          cpu     .ge    tR    AM(   ).setDisplayChanges(   dis p     layChanges);
          cpu.getR OM().setDisplayCh        anges(   display      Changes    );
              cpu.ge       tA().    setDisplayChanges(    displayCha    nges);
           cpu.getD(   ).setDisplayCha nges(displayChanges);
            c   pu.get  PC().set  DisplayCha   nges(displayChanges);
        cpu.getALU().setDis    pla yCh    anges(displayChanges);
     }

    publ    ic                 voi   d     setNumericFormat(int        formatCode) {
                  cpu.getRAM()   . setNumericFormat(for matCode );
          cp  u.getA().setNu     meri   cFormat(formatCode);
        c pu.getD().setNumericFormat(fo  rmatCode);
        cpu.     getPC().setNumericFo     rm         at(fo     rm at   Co  d    e);
        cpu .getALU          ().se    tNumericFo      rmat(f  ormatCode);
    }
    
       public v  oid          se    t  AnimationS  peed(        int spee   dUn     it) {
        cpu.getBus().setAnimationSpeed(spee   dUnit);
             }

    public int getI nitial   A      ni   mationMode() {
           return HackCon   troller.DISPLAY     _C  H    ANGES;   
       }

    public int getInitialNumer    icFormat()     {
        return HackC  ontr     oller.DECIMAL_F   ORMAT;
      }

     public void refresh() {
            c  pu.get  Bus().refreshGUI();
         cpu         .getR   AM()        .refr     esh   GUI();
        cp u.getROM().re   freshGUI();
              cpu.getA().refreshGUI() ;
          cpu.g     etD().refreshGUI();
           cpu.getPC()  .refre shGUI();    
        c   pu.getALU().refreshGUI();

        Screen       G UI screen = gui.ge  tS cree       n();
           if (scre     en != null)
               screen.refre    sh();

    }
       
      pub  lic void      pr  epareFastForward() {
                     gui.requestFocus();
        keyboard.r      equestFocus(     );
    }
        
       public vo     id prepareGUI() {
    }

    public St    ring[] getVariables() {
                return vars;
    }

    protected   HackSimulatorG UI getG   UI() {
           retu       rn gu   i;
       }   

    /**
     * Ca  lled when the ROM's current program is change   d.
         * The event contains the  sour  ce o   bject, event type and  the new pro      gram's file name   (if any).
       */
    publi      c   void programChanged(Program     Eve    nt event) {
         super.program   Changed(event);

          if (event.getType() == Progra          mEvent.LOAD) {
                int oldAnimationMode = ani mat       io    nMode;
             setAnimationMode(HackController.DISPLAY_CHANGES);         

            refresh();
                  no  tifyListen      ers(Controller  Eve   n      t.ENABLE_MOVEMENT, nul       l);
             cpu.i   nitProgram();

            setAnimationMode(oldAnimati  onMode);
        }
    }

    /**
     * Called when an error occ  ured in a computer part.
        *  The event contains the source computer pa    rt and the e    rror   message.
       */
    public void computerPar tErrorOccured(ComputerPartErrorEvent event) {
        displayMessage(event.getErrorMessage(), true);
    }

    // receives a v     ariable name of the    form xxx[i] and returns   the numeric
    // value of i, which i s an add  ress in the RAM.
    // Throws    VariableEx   ception if          i is not a legal address   in the RAM.
    private s      tatic short getRamIndex(String varName) throws VariableException {
        if (var     Name.indexOf("]") == -1)
               throw new VariableExcep      tion("Missing ']'", varName);

        String      indexSt  r = v  arName.substring(varName.indexOf("[") +     1, varName.indexOf("]"));
        int index = Inte   ger.parseInt(indexS    tr);
           i     f (   inde x < 0 || index >= Definitions.RAM_SIZE)
            throw new VariableException("Illegal variable index", varName);

        retu  rn (   short) index;
    }

    // receives a variable name of   the form xxx[  i] and return   s the numer   ic
    // v       alue of i, w        hich is an address in the ROM.
    // T    hrows VariableException if    i is not a legal address in the ROM      .
    private static short g  etRo    mIndex(String varName) throws VariableException {
        if (varN      ame.indexOf("]") == -1)
            throw new VariableException("Missing ']'", varName);

        String indexStr = varName.substring(varName.indexOf("[") + 1, varName.indexO f("]"));
        int index = Integer.parseInt(indexStr);
        if (index < 0 || index >= Definitions.ROM_SIZE)
            throw new VariableException("Illegal v ariable index", varName);

        return (short)index;
    }

    // Checks that the gi    ven value is a legal 16-bit value
    private void check_value(String      varName, int value) throws VariableException {
        if (value < -32768 || value >= 32768)
            throw new VariableException(value +
                "   is an   il    legal value for variable"   , varName);
    }

     // Checks that the     given value is   a lega   l        16-bit address
    private void check_ram_address(String varName, int       value) throws VariableException {
        if (value < 0 || value >= Definitions.RAM_SIZE)
            throw new VariableException(value +
                " is an illegal value for", varName);
    }

    // Checks that the given value is a legal 16-bit address
    private void check_rom_address(String varName, int value) throws VariableException {
        if (value < 0 || value >= Definitions.ROM_SIZE)
            throw n   ew VariableException(value +
                " is an illegal value for", varName);
    }
}

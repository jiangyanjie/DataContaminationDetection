/*
 * CPU6502.java
  *
  * Create   d on December 11, 2006  , 9:52   A M
 *
 * To chang  e this template, choo se Tools | Template   Manager
         * and open the template in the     editor.
 */
   package emulator.core.CPU6502;

im   port utilities.By   teFormatter;
import emulator.core.CPU6502.mvc.  CPU6502ControllerInterface;

/**
 *
 * @autho  r abailey
 */
public final class CPU6502 implements PageBoundaryObserver {

    pr    i  vate MemoryI nterface _mem;
    private static final int ACCUMUL   ATOR _ADDRESS   = -1;
    private int _numCyclesRem       aining     = 0;
     private boolean me    moryWatch    Mode =   true;    // false ; 
      private by   te _aRegiste    r = 0x00;
    private byte   _xRe   gi   ster = 0x00; 
    private byte _y  Re   g     ister = 0x00;
       private i    nt _programCount   er = 0xC000;
    private                   byte _fla    gs = 0x00;   
            privat    e int _  cl  ock = 0; // dont really need this
    private byte _st ackPointer = (byte) 0xFF   ;
    private CPU6502Co  ntro   llerInterface _c  ontro     ller;
             private boolean _controller Updates = true;
  
      p     rivate boolean   cliLatenc  y =    false;   // specia   l case   so I do not need      to        implement prefetch for      instructions
     
    public CPU6502(MemoryIn  t    erface mem) { 
                     _      mem     = mem  ;
               _n        u  mCyclesRemainin    g          = 0;
           clear(); 
    }

           p     u  bl      ic      boolean isCLILatency          (   ) {
              return cliLatency;
             }
      p  ublic vo  i d   clear() {
          _   aRegister = 0x00;
        _x    Re      g     ister = 0x00;
          _y        Regis     ter    =       0x    00;
        _progra      mCounter = 0xC00    0;
          _f   lags = 0x00 |       Architecture6502.    U     NUS     ED_FLAG_MASK | Architecture6  502.BR   E   AK_C OMMAND_ FLAG_     MA       SK |          Arc  h  itecture6      502.IN   TERR    UP    T_D   ISABL   E    _FLAG_M   ASK;
        // s    upposed to b  e $34 on sta   rtup
        _clock = 0; // dont really need this
              //     _s      tack = new byte[0xFF + 1];   
         _s    tackPointer   =       (byte)  0xFD;
         }

    p     ublic    void se     tMemoryWatc     hMode(          boolean va   l) {
              memoryWatchMode = val;
    }

      public String       getStateString()    {
              return "Clock: " + getC        loc     k() + " Program Counter:" + getProgramCounte    r() + " Ac    cumu  l   ator:" +  getAccum         ulator() + " X Register: " +    g      etXRegister() + " Y       Reg  ister: " + g etYRe    gister();
                  }

    public  void   notifyPa  geCr   os     se     d() {
        _numCyclesRemaining++;
    }

             public      final boolean isN       ewInstruction() {
            return (_numCy  clesRemaining <= 0);
       }

      pub         lic   CPU6502    makeCopy  ()   {
                         CPU6502 copy = new CPU650  2 (_mem.m      akeCopy());
             copy._     aRe gis   ter = _aReg   ister;
        copy._xReg      ister = _  xReg  ister;       
           co     py._yRe     gister = _y       Register;
                       copy._program     C  ounter = _programCounter;
        copy._flags = _fl  ags;
             copy._clock  = _clock;
            copy._controll  er = null;
              c    opy._con     trollerUpdat es =   _cont       r  ollerUpdates;   
        c         opy.   _stackPoin   ter           = _   stackPointer;
        //       S          ystem.  ar     rayco            py(_stack , 0, copy._s  tac k  , 0  ,       _sta  ck.length);  
            return copy;
    } 
   
       p          u   blic vo     id setCPUC    o  ntroller(CPU6502Con  tr                o     lle        rIn    terf a  ce cont     roller) {
                _con     trolle    r = c   on     tro    ller;
        }

             publi       c C PU 6502ControllerInterfa ce g      etCPUController() {     
           return _cont  roller;
    }

       pub  lic void setController     UpdatesMode(b            oole    a       n flag) {
        _con     tr     oll          erUpdat    es = f            l     ag;
          if (flag) {
            if   (_cont  rolle  r    != nu         ll) {
                _control   le              r   .not  ifyCPUModelChanged(CPU6   5    02ControllerInter    face.MODE    L_CHANGED);
            }
        }
    }
    
      public boolean getC             ontro  llerUpdates      Mode() {
                 r   eturn   _    controllerUpdat      es;
     }

                public byte getFlags() {
        return _flags;
    }

    p ublic     void setFlags(   byte f) {
        _flags  = (  byte) (f | Archit     ec     tur  e  6502.UN     USED  _FLAG_  MASK);
        not        ifyCPUF  la         gChange      Listene           rs();   
     }   

       public b      yte getStackPo   inte  r() {
        retu   rn _stackPointer;
    }

    pub     lic vo   id setSt        ackPointer(byte b ) {
        _   sta  ck  Po   inter =    b;
        no   tifyCPUStat     eCha     n         geLis     teners();
     }


    // the flags
             public void     se    tNegative  Flag(boolean flag) {
        alterFlag(flag, Architecture6502.NEGATIV    E_FLAG_MASK);
    }

    public          bo     olea      n getNegativeFlag() {
        re  tur  n  isF    lagSet(       A  rchitecture6502.NEGATIVE_FLAG_MASK);
    }

    public void se    tZe    roF       lag(boolean flag) {
        a      lte           rFl  ag(flag    , Arc  hitecture6502.ZER  O_F      LAG_MA         SK);
    }

    pub  lic boolea           n getZer        oFlag() {
          return is  Fl     agSet(Architectu   re650  2.      ZERO_FL  AG_M  ASK) ;
      }

       public void       set   OverflowFlag     (bool    e   an fl  ag)      {
           a           lterFlag(flag, Architectur   e6    50  2.OVE RFLOW_FLAG_MASK);
    }
  
    public b      oolean ge t           OverflowFlag(  ) {
                    return isFlagSet(Architec    ture  6502.OV   ERFLOW_FLAG_M  ASK);
     }

    p   ublic void setCarryFl ag(bo   olean flag) {
                    alterFlag(flag, Archi t    e     ctu  r    e6502     .CARRY_FLAG_MASK);
    }

        public b          oolean   getCarry   Flag() {
          return isF        lagSet(Architecture6502   .CARRY_FLAG_   MAS    K);
    }

    public voi  d      setInterru ptFlag(bo olean flag) {
        alterFlag(flag    ,  Archi   tecture6502.INT    ER      RUPT_DISABLE_FL    A  G_MASK     );
    }

    public boolean getInterru   ptFlag() {    
                re  turn isFlagS       et(Ar              chitecture65        02.   INTERRUPT_DISABLE_FLAG_MA SK);
    }

    public void   setBreakFl   ag(boole   an    flag) {
        a     lterF    lag       (flag,     Archi        tectu re6502.BREA     K_COMM  AN  D_FLAG  _MASK);
    }

    p    ublic boolean g   etBreak     Flag() {
         re turn       isFlagSet(Architec       ture6502.BREAK_CO    MMAND_FL  AG_    MAS   K);
         }

    p ublic void  setDecima     lFlag(bo   olean fl ag) {
             alterFlag(     flag, Archi  tecture6502.D     ECIM       AL _MODE   _FLAG       _M     A       SK);
    }

    public        boolean getDecimalF     lag()         {
          return isFlagSet  (      Architec    tu    re65  02.DECIMAL_MODE_FL   AG_MAS     K);
      }

             // t     h     e re  gisters
    p   u    blic void  s    etAcc       umul  a   tor( b   yte b)  {
          _aRe   gister =    b;   
           notifyCPURegisterChangeLis teners();
           }
   
      public b       y              te ge tAccumulator()     {
             return        _aR   egister    ;  
               }

       public void setX    Re   gister(byte b) {
                    _xRegister     = b;
        not        ifyCP      UR    egisterChangeLi     st   eners(   );
    }

         public byte ge  tXReg       ister()                {
               retu        rn _xRegister;
          }

          public    void se  tY       Register(byte b)     {
           _yRegister = b;
        notif    yCPURegisterChan g     e Li       steners  ();
    }
 
    public byte getY     Re gister() {
        return _yRe  gister;
    }

    // the states
    p ublic void setProg     ramCounter(int                    a ddress)        {
                 _ progr    amCo           un   t er = add    re   ss;    
              noti    fyCPUStateChangeLi steners    ();
    }

    public in        t get   Progr amCounter() {
        ret    u    rn _prog   ram    Cou  nt  er;  
    }          
    pu          blic byte g       etOpcod     e() {
    	r  eturn _mem.getMemoryDirect(  _programCounter);
          }
    publi       c    void incr    ementProgramCounte      r(int   delta) {
        _prog       ramC  oun         ter   +=    delta;      
            notify   CPUSta   teChangeListeners();
       }

           p   ublic i     n    t    getClock() {
        return _  clo ck;    
    }

    p    ubli    c      void se tCloc    k   (int     n  ewClo  ck) {
            _clock =  newClock;
               n    otif  yC  PUStateChangeListe    n    ers();
    }         

    public  voi   d inc       rementClo      ck(int d  elta) {
                           _cloc     k += delta;
      }

     private b         oolean  isFlagSet(int               flagMask) {
                         ret   urn ((_  flags &    f   lagMask) == flagMask    );
                    }

     p  riva    te          boole   an setFl   ag(int flag  Mask) {
             if (     isFlagSet(    flagMask)) {
                   /     /          flag was alr eady set. do  no     t   hing
                            return                false     ;
              } els  e {
               // flag     w          as    clear,  se   t it
                      _flags |= flagMask;
                          r      eturn tru  e     ;
        }
    }

      privat e boolean     c    learFlag(int fl       agMask)   {    
          if    (isFla    gSet(flag   Mask)) {
              // fl               ag was already     se          t.  clear it
                _flag     s ^= f              la    gMa   sk;
               return  false;      
         } el  se              {
                // flag           was clear, do       not   hing
                                ret    u rn false;
          }
    }

    priva  te vo    id alterF        lag(           boolea    n flag, int fla   gMask) {
                         boolean hasC   han    ged = fals     e;   
         if (flag)  {
                    hasChang  ed = setFlag        (fl   a    gMask);
        }  else {
               has     Changed = clea  r Flag      (flagMask    );
               }
          if (       hasChanged) {
            noti   fyCPUFlagCh   angeListe ners();
                }   
             }

    priva   te void          notif            y CPU   FlagChangeListeners(   ) {    
         if (_control ler != null       &&          _con   trolle   r     Updates      ) { 
               _    cont  roller.notify       C    PUModelChanged(CPU65   02Control   ler  Interf          ace.MODEL_FLAGS_CHANGED)    ;
          }
     }

              priva   t           e          v        oid notifyCPUState      Cha  ng     eListeners()   {        
          if (_controller !    =     null && _c        ontrollerUpd  ates)      {
                _controller.notifyCP    UModel         Changed(CPU6502Contr      ollerI  nterface.   MOD   EL_STA    TES_CHAN GED); 
        }
             }
     
    private void noti      fyCPURegisterChangeList     eners()     { 
                if (_controller != null && _cont   rollerUp        dates) {
                       _contr oller.notifyCPUModelCh  anged   (CPU6502Control    lerInte    rface.MODEL   _  REGISTERS_CH   A   NGED);
        }
      }

    p u         b l    ic void decrementStac k  Pointer(  ) {
        int   sp  = get      StackPointer(  )  &     0xFF;
           sp--;
            setStackPo int  er((byte) (sp & 0x    FF)   );
    }

      p   ubl   i c    v            oid push           ByteOnStack        (byt               e val)      {
           int sp  = getSt   ackPoint   er() & 0xFF;
        _mem.setMe mory(0x0100 +    sp, val,    memoryW     atchMode);              
                   sp--;
         setSta ckPointer((byte) (sp & 0xFF));
       }

    publ ic byte popByteF        romStack(   ) {
          int sp = (g etStackPointer() & 0     xFF) + 1;
           if (sp   > 0xFF) {
            sp =    sp &      0xFF;     
        }

                        byte b = _mem.ge    tMemory      (0x    010  0 + sp, memoryWatch  Mode);     
                   setStackPointer((byte) sp);
        r      eturn b;
     } 
  
      public String   getCurrentInstru  ctionDescrip  tion()   {
        // not prop  aga ted to memor       y ob  servers
        int programCount   er        = getProgramCounter();
        int hexVal = _mem.g etMe         m or     yDirect     (programCounter) & 0xFF;
                       OpCo       de6502 opcode = Op      Code6          502.OP       CODES[hex    Val];
                if (opc   ode == null)     {
            retur    n ByteFormatter.formatI   nt  (programCounter) + "         "          + "Un    support  ed O    pcode: " + ByteFormatter.formatByte((byte   ) hexVal);
            }
           by te rawData   []    = new byte     [opcod    e.getL       ength()     ];
               for (int  i               = 0; i < r      awData.l    ength;         i++     )    {
              rawData[i] =          _mem  .getMem  o        ryDirect (programCoun     ter + i);
        }
                 Instruction6502 instr =          new Instruction6502(o   pcode, r      a       wDat a);  
            String           oper    and = "";

        
        switc   h (     opcode.ge    tAddres    sMo          de()) {
                    case Ar           chitectur    e6  502.ABS          OLUTE_MODE:
                if (     opcode.isJump())      {
                             operand     =    " $"   + Byt   eFormatter.formatInt(  getAbsoluteAddress_dire   ct()); // makes n o sen       s e t o sho   w an = fo  r a JMP
                               } else {
                        ope     r and = "   $" +           ByteFormatter.f    ormatIn t(getA   b        soluteAddress    _direct()    ) + " = " +      Byt  eFor ma   tter.f    ormat    Byte   (getAbsoluteResult_direct( )   );
                             }
                      brea   k;
                  case    Arc  hitect ure6502.ZP   _MODE:
                       operand = " $" +  Byt        eFormatter.for   mat  Sin   gleBy   t   eInt(  ge  tZeroPageAddress_direct())  + " =             "    + ByteFo        rmatter   .formatB  y     te(getZeroPageResult_dir  ect(  ));
                                  break;
              c         ase Architec   ture6502.I  MM       EDIATE_MODE:
                operand = " #$   " + ByteFo    rma        tter  .formatByte(get  Immediat   eResult  _direct());
                break;
               case   Architec    t ure6      5    02.IMPLICIT_MODE       :
                           ope     rand =             ""; // do not    show anything f   or the  s    e (like   TXA)   
                         break;
            case Ar       chitectur  e6502.ACCUMULATOR_MODE:
                     operan  d = " A";
                             break;
                case Archi   tecture6   5  0 2.ABSOL    UTE_INDEXED_X_MODE:
                                 operand = " $" + ByteFormat ter.formatInt(g   e  tAbsolute     A ddre ss_direct()      )   + ",X @ " + ByteFormatte   r.formatIn  t(get     Absol   u              teXAddre ss_dire ct(null)) + " =      " + ByteFormatte r     .formatByte(getAbsol  ut   eXResult    _dir    ect(n  ull));
                bre     ak;
                  ca    se Architectur     e650    2.ABSOLUTE_INDEXED_Y_MODE:
                         o peran    d = " $" +     Byte     Formatter.fo  rma    tInt(get     Absolute     Address_direct()) + ", Y       @ " + ByteFormatter.for     mat   Int(    getAbs  oluteYAddress_   direct(null)) + " = " + By  teFor  mat    ter.format         Byt e(getAbs         olu  teYResult_di   rect(null));
                              break;
                  cas     e Architecture6502.Z     P _I   NDEXED_X_M  ODE   :
                          o  perand      = " $" + B    yteForm  atter.formatSing      leB    yteI   nt(getZeroPageAddress_d   irect    ()    )              +       ",X @ " +   ByteFormatte  r.formatIn    t(getZeroPageXAddres                    s_direct()) +        " = " + By     te       Form  att e     r.formatByte(getZero    Pag        e XR   esult _dir   ect());
                                br    eak;       
                                    c ase Architecture   6502.ZP_INDEX  E      D_Y_MODE:
                   operand =    " $" + ByteF     ormatter.   format   Single    B    yt   eI  nt(getZer     oPageAddres s_direct())    + ",Y      @ "  + ByteFormatter          . formatI    nt(getZeroPa        geYAddress_direct()              ) +    " = " + ByteFormatte          r.f      o              rmatB      yt e(       getZeroPageY   Resul  t_direct())   ;
                  break;
            c ase  Arc  hitecture6502.INDIREC T _AB  S     OLUTE_            MOD    E:
                            o perand = " ($" + Byt   e  Formatter.format  In      t(getAbsolute    Addres  s_di      rect(    )) + ")         " + ByteForm    at   ter.      f or  ma   tInt(g etIndi rectAbsolu    teAd   dre   ss_d irect());
                                  break;
                      case Arch  itectur    e 6502.IN    DEXED_I  NDIRECT_  X_MODE:
                                          operand      = " ($" + B      yteFormatter            .fo  rma t           Sing         leByteInt(getZeroP   ageAddress_direct(     )) + "             ,X) = " + Byt   e   Fo      rmatter.  forma tInt(get        Indire       ct    XAddre    ss_direct(0)) + " @ " + ByteFormatter.fo        rma                  t Int(getIndir    ectXAddres     s_direc   t()) + "       = "     + ByteFormat      te  r.f ormatByte(getIndir  ectX     Result_dir ect ());
                               break;
                                  case  Architectur e65     02. I     NDI R     ECT_INDEXED_Y_M   ODE:
                               operand = " (       $"    + ByteFormatte     r.    for   matSingle ByteIn                   t(getZeroP  ageAddress_dire       ct()) + "),Y = " + Byte Fo     rmatter.f           ormat    In  t(getIn  dire   ct   YA   ddress_dir  e               ct(0,   null  )    ) +   " @       "      + B      y    teF       ormatter.f  or mat        Int       (getIndire    ctYAddr          ess_direct(nul   l )) + "     = " +    B  yteFor           matter.for   ma   tB         yte (get   In di   r ect  YRes      ult_      direc          t( nu   ll         ));
                break;
                     case   Ar      ch   itec     ture650           2. RELATIVE_MODE:
                                          op  e  ran    d      = " $                       " + ByteFo  rmatter.f     ormatInt(get     RelativeAddre  ss_direct    ());
                      br       eak;
                     default:
                   break;
        }    
          St   ring retStr   ing            =                            Byt   eF     o    rmatt     er   .for      m    a  tIn     t(    getPr ogramCou                      nter  ()) +            "        "        + instr.getBy  teRe    presentation() +           "    " + o p code .    getBa     si cDescription  () + ope rand         + "                                                                                                               ";
              // Add         50 spaces, and t      hen return on   ly a   subset  (allow  s the output t   o line    up       cl       eanly)
             return retString.    substring(0,                             47);
            }
  
    publi        c   Instruction6502 getInst ruc  t  ion          AtProcess  Counter()   {
        // no            t prop aga          ted to    me mory ob    server  s
        int progr         amCou nter =           g   e    t    ProgramCo       unt   er();
              in  t h    exVal = _me   m    .    get  Memo    ry(  p    rogr    amCounter  ,    memoryWatchMode) & 0xFF;
             OpC   ode  65      02             opcode = OpC ode650      2.  OPCODES[hexVa       l];
        if (op   code =  = nul   l) {      
              System.er         r    .println(      "I           nvalid opcode:     " +     ByteF         ormat   t  er.formatByte (  _mem.get Memory(prog     ramCo unter, mem    oryWatchMode))    + " encount       ered at a   ddre  ss:" +     B   yteFormatte r.format  I         nt   (programCounter));
               return   null; 
        } el  se {
                    byt       e   rawData[] = new by   te           [opcode    .            getLength()];
                                        for (int i = 0; i < rawD  a      ta.length;   i++) {       
                           raw    Dat       a[i] =     _        mem.     get Memory    (prog  ra   mCounter    + i, memo  ryW    atchM o d   e);
             }
                       return new        Instructi   on6502(opc   od       e,   rawDat  a);
                }
    }
   
       publi       c     boolean isI     ncrementingClo  ck     () {
    	retur   n (_numCycl  esRemaini   ng>0);
             }  
       pub lic boo      le    an        proces   sN    extInstructionCycle(      ) {
                       if (_num Cyc  l  e    s Rem   ainin      g == 0) {
            return     processNextIns tr uctio         n() ;
             }
           _num    Cycles   Rema    ini    ng   --;
        in                       creme    ntClock(1);
                ret  urn true;
    }

        public             boolean proce          s           sNe      xt   Instru     ct    ion() {
            // not propagated     to memo    ry o  b      servers (her     e)
                  cl iLate   nc  y         = fals e  ;
                              in      t programC   o        unter =      ge  t  ProgramC  ounte    r(    );     
        int   hexV   a   l =   _me         m.getMemory(prog   ramCounter, mem  o            ryWa tc    hMode)     &       0xFF;   
                                          OpCode6502 o   p  code    = O       p   Cod        e6502.OP C          ODES[    hexVal];
                   i          f     (opcod e == null )                {
             System.err.      p       rin   tln(" I    nvalid      opcode: " + Byt   e   Formatter.format   Byt  e(_mem.getMemo        ry(programCo   un    ter, memoryWat  ch   Mode)) + " e    n     countered at   address   :"      + ByteFormatter.formatInt     ( progra  m   Cou    n              ter       ));
              return        fa  lse;
        }   
               _numCycl    esRe  m  a   ining = opc o       de   .getCyc          le    s( );    
           PageBoundary O   bser ver   p     bo   =    nul     l;
           if ( opc        ode.isExtraC  ycleFo   rPageB       oundaryCross())        {
                       pb         o =    this  ;
          }
                   
                 swit ch (h  exVal) {
                                c              ase     0x00:  //BRK  IMPLI CIT           e    xa   m        p      l   e:BRK
                                             d oB      R     K(opcode    );
                         break;/                / retur     n             f   alse                       ;
                  ca  se 0x     01:    //ORA INDIREC      T_X      example        :ORA      ($ 44,X)                             
                        doORA(    g  etIn         directXR     e                   su   lt() , opcode) ;         //             bitwise   O        R     with      accumul     a  tor
                                       break ;
                             ca     se 0                x05:  //ORA ZP                   e       xample   :ORA $44    
                                                            do    O RA(g     etZer   oP   ag           eResult(  )   ,      o   pcode); // bitwise OR with      accumulator
                              br  eak;
                                      cas e    0x06:  / /ASL   ZP                 exa     mp     le:ASL $44
                       doASL(getZe        ro   Page     Addres   s(), opc    ode)      ;    
                               bre   ak;
                    ca  s   e 0x08    :           //PHP IMPLICIT                exa m p le:P     H         P
                       doP HP(opco      de);
                                  break;
             case 0x09:  /   /ORA IMME  DIATE     example :ORA #$44 
                              doORA(get Immediat  e Result() , opco de);     // bitwise        OR  wi   th accumu   lator
                         brea       k  ;
                                    ca    se 0x0A:  //ASL ACCUMUL      ATOR  e     xample:A SL     A
                           doASL(A     CC   U                     MU        LATOR_A  DDRESS,     op  code)   ;   
                    brea          k;
                            c    a       se 0x0D:      //ORA      ABSOLUTE       example:  ORA $   4400
                                    doO RA       (    getAb           solut   eRe    sult(          ),  o pcode);           // bitwise OR with accumul a   tor
                     break;
                ca  se 0x0E:  /      /       A S  L ABSOL       UTE     exa     m   ple:A      SL $440  0
                     doA    S     L(  getAbsol      uteAddress(), o    pcode); //     bi   twise OR wi   th            acc          umulator
                               b     rea           k;
                                case 0x10:  //         BPL   RELATIV E            example:BPL       LABEL
                     doB   PL(getR       elat   ive A             ddr     ess(),        opcode);
                      b       rea  k;
                           cas  e 0x    1 1:  //ORA IND    IRECT_     Y     ex  a    mple:ORA ($   44),Y   
                   do  ORA   (getI     ndi                     re  ct        YR e    sult    (pbo), op               code); // bit          wise   OR       with     ac  cum       ul    a       tor
                                           brea      k      ;
                      c   ase      0x15:     //ORA    ZP    _X           e     xample:ORA $4    4,X
                              doORA(getZ  e roPageXRe   sult(),  opco    de);     // bitwise OR with        accumulato  r
                      bre    ak;
                            case      0x16:  //ASL Z   P_    X                    examp         le       :ASL $       44,X
                          do   AS               L     (g  etZeroPageXAddress(), opcode);
                br     eak;
                                            case 0x18:  //CLC IMPLI    CIT     e            xample          :    CLC         
                                   doCLC(opcod  e);
                             br  e    ak;
                 case 0x19:  //ORA ABSO     LUT    E  _   Y   example:ORA $4      400,Y
                d   oORA(getA bs      oluteYR          esult(pbo), o pc     ode);       // bitwi    se O   R with       accumul    at  o          r
                              break;
                 c ase 0x1         D:  / /OR      A ABSOLUTE_X   examp     l   e:ORA $4           400,X
                             d   oORA(getAbsolu       teXResult(pbo), opcod     e)      ;           // bitwise    OR   with   ac        cum                 ulator
                   br      eak   ;
                     c   ase                  0     x                      1      E:  //ASL ABS   OLUTE_X     ex    ampl  e:ASL $          4400,X   
                     do      ASL(getAb  soluteXAddr  ess(p   bo), o            pc       ode);          
                            break;
                 c  ase 0   x20:  //JS    R ABSOL    UTE               exam ple  :JSR  $4400       
                                            doJS     R(g     etAbs     olu  teAdd    ress(), opcode)  ;   
                              break;
            case 0x21: //   AND INDI  RECT_X               ex am     ple:        AND ($44,X)
                                               doAN    D(getIndirect   XResult            (), o   pcod e    );
                   break;
                 case 0 x24:  /       /BIT ZP                       example:B   IT $44
                               doBIT(getZeroPageResult()  ,  opcode);   
                               break;    
               case 0x25:     //  AND ZP             exa   mple:A ND         $4  4
                         doAN   D(get           Z  er oPageResul    t          ()   , op  code);
                                         break;
                   case 0x26:      //R      OL ZP                       ex ampl      e   :R    OL    $44 
                doRO   L(getZeroPa  ge              Address(   ),     opcode);
                               br  e               ak;
                   case 0x    2 8:       //P       LP I   MPLICIT      example:PLP
                                   doPLP(opcode); 
                           brea      k;
                                     ca           se        0x29       :  //AND   IM  MEDIATE      examp        le:    AND #$  44
                          doAN     D(  getImmediate    Resu   lt(), opcode);
                      break;
                    case   0x2A:     //R  O     L ACCUMUL  A      TOR  example:        ROL A
                                              doROL(ACCUM  ULA                  T     OR_ADD  RESS,    opcod        e);
                                              break;
                 case 0x  2C:  //B     IT ABSOLUTE          ex      ample    :B      I             T      $4   400   
                               do         BIT(get   Absolu   t  eRes       ult (), op   code);     
                                brea     k;
                            c  ase 0x    2D: /     /AND ABSOLUT E     exam  p le:AND $ 440             0
                                         doAND(getAbsol          ut         e Result(), opc   ode);
                                          brea                                                        k  ;
                                                case 0   x2E      : //      ROL ABSOL             UTE     example   :ROL $4400
                                 do   RO L(getAb        s    ol  uteAd  dress(                ),   o  p   code)      ;    
                                break   ;  
                case 0x30            : //BMI RE  LA                T  IVE     e   xa  m  p  le   :BMI               L      ABEL
                                   do   BM     I(  getRel     ativeA          ddre      ss(), o     pcode);
                                 brea  k;
            case   0x31: /          /A  ND   INDIRECT    _Y   examp l   e:AND ($4    4),Y
                        doAND(   getI                 n   dire    ctYResu    lt(p bo            ), op    c  od      e);
                       break;
                         ca     se        0x35 :    //AND Z   P_X          e    xample:AND $ 44          ,       X   
                                           do  AN        D(ge  tZe        roPageXResult(), opcode );
                                      brea       k;
                 case 0x36: //ROL ZP_    X                        ex a    mpl     e:ROL  $4     4,X
                     d  oRO  L(get        ZeroPageX   A   ddress(  ), opcode);
                                      break;
             case 0     x3 8: //S    EC IMPLIC  I                  T           exam      ple:S   EC
                         d oSEC (opcod     e);
                                                   br  eak;       
                         case      0x39: //AND          ABSOLUTE_Y     example:AND   $440   0,Y
                                  doAND(ge    tAbsoluteYResult(   pbo)    ,  opcode)  ;
                                                b   re   ak; 
                        cas   e       0x3D: //AND ABSOL UTE_X   ex ample:A ND $    4       400,X 
                                do    AN      D(getAbsolut         eXRes       ult(pbo), opcode)   ;
                                      br     eak;
                                case 0x3E: /  /RO  L A BSOL UTE_X   exam       ple:ROL            $4400 ,X
                                                                        doR   OL(get     Absolu  teXA                   ddr  ess              (pb                     o               ),         o       pcode);
                               break   ;
                        case 0x40           : //RTI     IMPLICI        T     exampl                    e:RTI
                                          doRTI(o p  code)            ; 
                        /  /              return     false     ;
                   bre   ak;
                 ca      se 0x    41 : //EOR INDIRECT_X     exam          ple:EOR ($ 44,X)
                                doEOR(ge  tIn directX           R     esult(),  opc        ode);
                                     bre a  k;
             case    0x4     5       :     //EOR ZP            example:E   OR $44     
                                            doEOR(                getZe     ro Pa     g   eResult(),    opcode);
                                                b  re a  k;
               c  ase     0x46: //L   SR  Z P                              example:LSR $44
                     doL    SR(get ZeroP  ageA        ddress(), o  pcode);
                                 br   ea        k   ;        
            ca   s e 0   x48         :   //PHA       IMPLICIT     example:    PHA 
                          doPHA(opc     ode                 );
                      break;
                       ca        s  e 0x49:  //E       OR IMMED     IATE    example:EO    R #$4  4
                           doEOR (getI        mm   ediateR           esult(),   opcode);
                  brea k     ;
                           cas      e 0x4   A: //L     SR ACCUMULA    T     OR                  example:LSR A  
                                   do        LSR( ACCUM   ULATOR_ADDRESS,  op     code);
                                brea   k;
                               case 0x4C: //J           MP         ABSOLUTE             exampl   e     :JMP       $4   400
                     doJ MP   (getAb  solu  teA  ddres      s(),             opcode );
                        bre   ak;
                 c  ase        0x4D: //EOR ABSOLUT   E     exa       mple:EOR $44    00
                  doEOR(getA    bsol     ute   Resu   lt()      , opcode);
                  break;  
                         case        0x4E      :      //LSR ABSOLUT      E     examp      le:LSR $44      00
                   doLSR(        getAbsoluteA    ddress() ,           opcode);
                                                 break                ;
                      c               ase 0x50:     //BVC REL     AT   I VE     e     xa mp le:B    VC L   A      BEL
                doB   V C    (ge       t        RelativeAd        dres s(),       opc                  ode);
                            b      r             e    ak;
                           case 0         x5      1: //E  OR           IND I REC T_Y    example    :EO   R   (    $44),Y   
                                   d            o   EO     R   (ge tIndir   ectY       R            esult (pb  o), opc    ode   );
                                      br    eak;
                            ca      s  e  0x55: //EOR ZP    _X                 exa                 mp  le:EOR      $44,X
                           doE    OR(g    et       Z   er     oP  ageXResult         (), opcod    e);
                                                                            bre  ak;
                case 0x56:               //L  S     R Z                       P_X                 e        xample:LSR $4   4,X  
                                                        doL          SR(getZ  e   roPageXAddr      e           ss()        , opc ode); 
                      break     ;
               case 0x58: //CLI IMPLICIT            ex          ample:CLI 
                                     doCLI (op   code);
                                       brea   k;
                                        ca   se       0x59: //  E  OR  ABSO   LUTE_Y             exa   mple:E      OR $4400          ,Y
                         doEOR(ge tAbs olute  YResult(pb          o)   , opco                   de);
                    break;
                                c    ase           0x5D  : //EOR    ABS         OL        UTE_X    exampl      e:    EOR $440 0,X
                                    d   oE     OR(getAb            s      oluteXRe  sult(pbo      ), opcode      );
                                          b  reak;
                  case  0   x5E: //LS     R        A        BSOLU     TE_  X   ex amp   le:  L    SR $                  4400                            ,X
                                doL      SR(ge          tAbso        luteXAddress(pbo),    opcode);
                     break;
                                    ca   se 0x60: //RTS I       MPLIC   IT        e  x           ample:RTS   
                            doRTS(op        cod   e);
                             break;     
                        case 0   x    61:   //A    DC          INDIRECT     _X      e            x       ample:AD         C         ($       44,     X)
                           d               o       ADC  (getIndirectXResult(),          opcode);           
                                            break;          
                     cas e         0x65:      //    ADC ZP                      example:A        DC       $4     4
                              do   ADC (getZe   roPa  geRes  ult(),   o   pcod    e);
                b    re     ak;
                  case 0x  6 6:        //      ROR ZP                       examp  le:ROR $44
                     doROR (        get             ZeroPageAdd       ress     (), opcode);
                            break;
                             case 0x68   : //PLA IM    P LICI   T       exa  mple:PLA
                                d        o   P     L A(op      co               de);    
                   br  e     a   k;
                        case               0x69     : //                     A   D         C I           MMEDIATE        examp le:ADC     #$44   
                       doAD  C(      getImmediateR      esu    l     t()   ,      opcode);
                                break                       ; 
                                        case 0     x6A: /  /ROR AC  CU   MULATO  R  exampl e:    ROR A
                                     d   oRO   R(ACCUMULATOR_A        DDR    ESS, opco           de);
                        break;
                                      case              0     x6C:     /  /JM                                  P IN  DI     REC    T_  AB         S exam       ple:JM    P   ($4   400)
                                   doJ  MP(getI     n   d     irec          t               Absolu  teAdd    ress(      ),      opcod  e);
                        break   ;
                                   ca      se   0  x6D:   //ADC ABSOLUTE        exa m           ple  :AD    C $4    40 0
                                do ADC(getAbsoluteResult(), opcod     e);  
                                brea  k;
                                                                       cas     e  0x6E   : //ROR    AB      SOLUTE     example:RO R    $4400
                      doR  OR(getAbsolute    Addre ss()  , o    pc         ode     );
                          br   eak  ;
                  case 0x70    : //B   VS   RELATIVE              examp    le:BVS LABEL
                                       d                 oBVS     (    getRelative Add         ress( )         , opcode  );
                            break;
                            case 0x71:  //       ADC IN             DIR   E                C   T_Y          example:   A     DC ($44),Y
                                  do ADC(getIndire    ctYResult(pbo),      opcode);     
                    break;
                       case 0x 75: //ADC ZP_X                 example    :ADC $44,X
                                           do  ADC(get   Z        eroPag eX     Resul                       t(),  opcode);
                            brea  k   ;
                           c  ase          0   x7                     6:                               //ROR ZP_X                                               exampl   e:RO  R $ 4   4,X
                           doROR    (  ge tZero     PageX   Addr    ess(),   opcode);
                                                 break;
                         case   0x7       8: //S                 EI IMP  LIC    IT     exa   mple    :                   SEI
                               doS  EI          (o pcode);
                                                     bre       ak;
                        ca  se 0x  79: //AD           C               AB    S OL      UTE_Y    exa  m          pl          e:ADC $4400,Y   
                            doADC(ge       t   Absolu   teYResult(      p    b  o), opcod   e)    ;
                      b  reak;    
                                         c       a   se 0x7D: /   /A        DC ABSOLU      TE_    X        example:ADC      $4400,X
                     doADC(getAbs    oluteXRe   sult(pbo)  , op    cod  e);
                                             b     reak;
                   case 0x         7E:   //    R                  OR     ABSOLUTE_X   exam ple:ROR $4400,X
                                    doROR    (  getAbsoluteXA    dd     ress(pbo), o  pcod e);
                bre   a    k;
                            case 0x81:    //STA INDIRECT_  X              exa      m  ple:STA          ($44,X)
                      doST   A(get       Indir     ect  XA ddres  s(), opco    de)    ;
                               break            ;
                  case 0x 84: //           ST          Y Z P                                  ex ampl  e:STY             $44  
                               doST       Y  (ge     tZ    eroP   age     A      ddre   ss(), opc  ode);
                             b reak;
                        c    ase 0x85 : //ST            A      Z  P           e      xam  ple     :STA $    44
                                           doST      A(getZeroPageA  ddress(),    opcode);
                                     br    ea   k;
               case              0x86: //STX ZP              exa          mple:STX         $44   
                            d           oSTX(ge tZeroPag   eAdd   ress(), opcode);  
                                             br     eak     ;
                          case          0  x88:                      //DEY IMPL  ICIT        exa mple:DEY
                              doDEY(op      c  ode);
                         break;
                    case 0x8A  :  /      /TXA  IMPLICIT     example:TXA
                            d      oTXA(  o   pco     de);
                                  break;
               ca      se        0x8         C: //STY      AB      SOLUTE     ex      ample:STY $4400
                doSTY( getAbso   luteAddress(), o   pco      de)       ;
                    brea           k;
                  case      0x8D: //S      TA    A    B   SO  LUTE         example:ST     A $4400
                                              doST      A    (     get         Abs    olute        Add         ress(     ),     opcode);
                       brea             k    ;
                      case 0x8 E: //STX  ABSOLUTE        exa    mple:S   T  X $44    00
                      doST  X(getAbsoluteAd     dr    ess(      ), opc  o       de);
                                    bre   a      k ;
                        case 0x90  : /  /    BCC RELATI    VE                       exa         mpl         e:BCC   LABEL
                        do  BCC(getRelati    veAdd    ress(), op        cod e);
                           break      ;
                 case 0    x91: //STA I         NDI    RECT_        Y     examp   le:STA          (       $44),Y
                               doSTA  (get I      n dir    ectY  Address(pbo)  , opcod  e);
                                                   break;
                                   case 0x        94: //S                TY ZP_X                  examp     le:STY   $44,X
                         doST      Y(   getZe   roPa      geX Add  ress(     ),       opco      d e                );
                                  bre                              ak     ;
                                      case 0x95: //STA ZP_X                  exa    mp              le:STA $44,X
                              doSTA(getZe     roPageXAddr ess(   ), opcode);
                      br  eak;         
                         case 0x96: //S      TX Z            P_Y           example:STX      $4 4,Y 
                         doSTX       (getZeroPage    Y  Address(      ), opc       ode );
                     break;
               case 0x98   :        //TYA I      MPL            ICIT       exa   mple:TYA
                                 do      TYA(   opcode)   ;
                    br              eak;
                         case 0         x9   9      : //S   TA ABSOL         UTE_Y   example  :STA $4400,Y
                         doSTA(getAbso     lu  teYAddress  (pbo), opc    ode);
                                   bre    ak;
                ca se 0x9A: //TXS   IMPLI   CIT                   exa    m   pl e:TXS
                      doTXS            (op        code);
                                        b    reak;
                       c   a    se    0        x               9D: //S        TA ABSO LUT  E_X        exa       mpl           e:STA $             440 0,X         
                        doSTA      (        ge              tAbsol   uteXAdd                   ress(pbo),      opco   de);
                  b             r      e    ak;
                                 case 0x  A0: //LDY IMMEDI  AT   E             examp    le:LDY #$44
                            doLDY(getImmediateResult()    , o    pcode);
                                             break;    
                                      ca  se 0  xA1: / /L           DA IN  DIRECT_ X        exam   ple:LD  A             ($44,X)
                     d   oLDA(getInd        i  rect   X            Result              (   ), opcode);
                     break;
                case 0x  A2:  /    /LD X    IMM     EDIATE    example:LDX #$44
                                doLDX(getIm         med iateResult(), opco  de);
                                break;
                  case       0xA4: //LDY  ZP                      exa        m p     le:LDY $44
                                                doLDY(    getZeroP  age R     esul               t(), opcod  e);    
                          b     reak;   
                   case 0xA5:  //LDA ZP              example   :         LDA $44
                       doLDA(g      etZeroP       age   Re                                 su            lt  ( ), o    pcode);
                    bre   ak;     
            c   a   se 0x  A6  : //       LDX ZP             example:LD   X $44        
                                       do    L D X(  ge           tZ    eroPageRe sult(),      opcode);
                      break;   
                   case 0xA         8:  //TAY I   M PLIC     IT                           exa  mple   :TAY
                                                       doTAY(op              code);
                                         break;
             case       0xA9:   //LDA   IM MEDIATE                 example:LD  A #$44            
                                         doLDA(getImmediateResu l   t(    ),            opco  de);
                                             b   reak;
             c  a  se  0x   AA: //TAX IMPLICI  T     exa      mple:TAX     
                          doTAX(opcode);    
                                    b     reak;      
                  c    a    se 0xA      C: // LD Y A  BS         OLU      TE            example:   LDY $4  400
                                                  doL   DY   (ge  tAbsolu   teRes   ult()    , opc  ode);
                              b  reak;      
                              c    a  s           e 0xAD: //LD    A   ABSOLUTE          e      xample:LDA $4400
                                 do     LDA(get            Abs  olut   eRes             ult   (), opcode)    ;  
                                                    break;
                      case 0xAE:       //L        DX ABS   OL   U           TE     example:LDX $4400
                                   do     LDX(g       etAb   solut e                Resul t ()    ,       opc ode); 
                                   break;
                 case 0xB0: //   BCS RE    LATIVE            exa  mple:BCS LABEL
                                       doBCS (get   Rel      ativeAddress(), opcode);
                              break;
              case 0xB1: //LDA INDIRECT_      Y       exam    ple:LDA (                        $44   ),Y
                      doLDA(getInd  irectYResult(    pbo), opcod       e);
                    break;  
                       case 0x  B4: //LDY    Z  P_X             ex a   mp    le:LDY $4    4,           X
                                            d            oLDY(getZeroP  a         g        eXR      es     ult()  ,  o  pcode);
                      break;
                       cas         e 0xB5: //LD     A     Z   P       _X         examp l    e:         LD  A $4    4,X
                    doL    DA(getZe  roPag     eXRes       ul       t(   ),          o     pcod      e);
                                   brea   k   ;
                    case 0xB6:      //LDX ZP_Y                        example:LDX $44     ,Y
                                                doL   DX(getZero Pag  eY            Res      ult  (),     opcode);
                              br eak;
                                 c  ase 0xB8: //C         LV IMPLICIT      examp                      le:CLV
                                           doCLV(o   pcode);
                             break;
                                case 0xB9   : //LDA ABS     OLUT     E_Y   exam   ple:        LDA $4400        ,Y
                                          doLDA(getAbsoluteYResult(p   bo)    ,        opc   ode); 
                                          br   eak;
                      cas  e       0   xBA: //TS  X IMPL     I  CIT       example:TSX
                                      doT  SX    (  op  c    ode);
                  break;
                             ca     se 0xBC: //LD   Y ABSOLUTE    _X   example:LD        Y          $4400,  X
                   doL DY(getAbs     o  lut   eXResult        (      pbo           ), opcode);
                                                        break; 
                  case 0xBD: //   L  DA ABSOLUTE_X     examp        le:LDA $4400 ,X
                             doLDA(getA      bso  l   uteXResult   (                 pbo),     op    code) ;
                                             break ;
                ca    se 0xBE:  //LDX ABS   OL    UTE _Y   example:  LDX $   44       00,Y
                 doLDX(getAbso luteYR  esult(pbo), opcode);
                   break;
                  c      ase 0xC0: //       CPY IM   MEDIATE      e xampl  e:      CPY #$4 4    
                                               doCPY(getI      mme   diat              eResult    (),           opcode   ); 
                                                      break;
                                 ca         se 0xC1: //CM P INDIRECT_X    example:CMP ($44,X)
                             doCMP(getInd     irect       XResult(), opco   de);
                      b  reak;   
                 c     ase 0xC4: //  CP    Y    ZP                         examp       le:CPY      $44
                doCPY     (getZero PageResu       lt                ()     , opco     de);
                      break;
                    case  0xC5: //CMP     ZP                       exam    pl e:CMP $44
                          d oCMP(get     ZeroP           ageRes      ult(), opcode);
                     b   r   eak;
                      case 0xC6: //DEC ZP                 ex      ample:DEC $      44
                               doD    EC               (get ZeroPa  geAddress (), opcode);
                       br   eak;
                        case             0               xC8: //             INY  IMPL   I  CIT         exa    mp                                       le:INY
                  doI    NY(op    code);
                         break  ;
            cas     e 0xC9:    /   /CMP IMMED IATE     e    xample:CMP #    $44   
                           do CMP(getImmed iate R  es        ult(),    opc     ode);
                           br eak;
            ca  se 0xC A: //   D    EX  IMPL  ICIT        e     xample:DEX
                   doDEX(opcode);
                                             break;
              case 0xCC:  //CPY ABSOLUTE        example:CP Y $4 400
                          doCPY  (getAb sol   u   t eResult(), opcode);   
                         b  reak ;
                 case     0xCD: //CMP   AB      SO              LU     TE     e   xa                      mp            le:CMP $4400      
                   doCMP(getAbsoluteResult(),    opcod    e);
                              break     ;
                      case 0xCE: //DEC AB    SOLUTE                  exam              p   le:DEC $4400
                        doDEC   (ge  tA  bsoluteAddr  ess(),      opcode);
                      break;
                                  case      0x D0:    //B   NE RELAT     IVE         example:   BNE LABEL
                   do    B  NE(g etR        elativeAd         dress(),   opc     o        d  e);
                                                 br eak   ;
              case        0xD   1: //CMP INDIRECT_Y   example:CMP ($44),        Y
                                    doCMP (getIndirect   YR   esu    lt(p   bo    ), opc     ode);
                   brea   k;  
                              ca  se        0xD5: /  /CMP    ZP_X               exa   m        ple:CM      P    $44    ,X
                  doCM    P(getZe    roPageXResult  ()   ,   opcode        )   ;          
                     b r  e  a  k;
                case 0xD6:    //  DEC     ZP_X                                          exam     pl  e   :DE   C          $44,X
                                     doDEC(getZeroPa     geXAddress(), o    pcode);
                   bre  a      k;  
                          case   0xD8: //CLD              IMPLIC IT           exam       p         l        e:CLD       
                    doCLD(     opcode);     
                     break;
                    c    ase 0xD  9:              //           CM     P ABSO         L U T E_ Y   examp   le:CMP            $4        400  ,Y
                            doCMP(g      etAbsol    u   teYRes   ul       t(pbo),   opcod  e);
                                    break;
                 case 0xDD: //CMP ABSOLUTE_  X          ex   am ple:CMP $44        00, X
                          doCM  P(g              etA  bsolute  XRes          ult(pb          o), opcode)        ;   
                      break    ;
                      case 0xDE: //DEC AB                SOLUTE  _X   example:DEC       $4400,    X
                doDEC(  ge    tAbsolu         teXAdd     ress(p        bo), opcode);
                              b reak;  
                  case             0xE0: //CPX IMMEDIATE          ex       ample:C  PX      #$44
                         doCPX(getImm    edi       ateResult(), opc    ode       );
                  break;
            cas   e 0xE1: //SBC INDIR  ECT  _X                example           :SBC    ($44,   X)
                                doSB           C   (    g e   tIndir            e    ctXResult(  ),          op   cod    e    );
                  break;
                      case 0xE4: //CP         X ZP               exa             mple:CP  X $    44
                       do    CPX( getZeroPageResult(),    o    pcode);  
                 b    reak;
                      case 0xE5: //SBC          Z     P                    example:SB C      $44
                   doSBC(getZe    r oPageResult(),       opco  de);
                           b     rea k;
              case 0xE6: /   /INC ZP            example:INC           $4   4
                   doINC(getZe        ro      Pag  eAddress(),       opcode);
                  br  eak;
                   case        0xE8: //I     NX IMPLICIT       exam  ple:INX  
                        doINX(op    cod  e);
                                        b    reak;
                 case 0xE     9: //SBC IMM                 E  DIA  TE                           example:   SB C #$ 44
                             d  oSBC(getImmediateResult(),       o    pcode);
                    br         eak;
                  case 0xEB:      //CPX     ABSOLUTE      example:CPX $4400
                        d    oS     BC  (getImmedia  teResu  lt(), opcode   );
                                 b  reak;
                  case 0x       E         C: //   CPX   ABS    OL UTE     e     xa         mple:CPX $4400
                          doC   P   X(getAbsolute Result  (), opc  ode    );
                            break;
                              case 0xED: //SBC ABSO     LUTE           examp                  le :SBC $   4400 
                    do  SBC(g         etAbsolute             Res  ul    t(),  opcode);
                                  break;
             case 0xE     E:    /         /  INC ABSOLU    TE           ex ampl     e    :INC      $44  00
                     doI       N    C(getAbsol   uteAddress( ), o p code);
                                 brea k;
                ca se       0xF0: //BEQ   RELATIVE        ex   am      p    le:BEQ LABEL   
                             doBE  Q(ge  tR elativ         e     A             ddres s(), op cod   e);
                            break;
                     case 0xF1: //S BC IND  I   RECT_Y   example:S      BC      ($4  4),Y
                                   d  oS BC(g    etIndirectYR      esult(pbo), opc ode);
                     break;
             case 0xF 5  :          /    /SBC ZP_X         exa        mpl       e:SBC $44,       X
                               doSBC(getZeroPageXResult(), op   code);
                           break  ;    
                           case    0   xF6: //INC    ZP _           X                   example   :       I  NC $4    4,X
                             d          oINC(get   Ze  roPageX  Address() , op  code     );
                                        break;
                 ca   se    0 xF8: //SED I   MPLICIT               examp     le:SED
                   do    SED(o   pco de);
                    break;
              case 0xF    9: //SBC     ABSOLUTE_Y   example :SBC $44   00,Y
                     do        SBC(getAbsolute  YResult(p             bo), o  pcode);
                    break; 
                           case 0xFD: //SBC A  BSOLUTE_X      exa   mple:SBC $440  0,X
                     doSBC(                getAbsoluteXResult(pbo), opcode );
                      br   eak;
              cas  e 0xF     E: //INC          ABSOLU TE_X    examp    le:INC $44  00,X
                doINC(getAbsoluteXAdd    ress    (pbo)    , opcode);
                     br    eak;                    
    
                                // unoffical opco   des h    ere   
                   /       / all the NOPs
                      // 1 by  te NOPs
                      case 0x1A:
              case 0x3A:
                          case 0x5A   :
                                   case         0    x7A  :
              cas  e 0xDA:
                      case         0        xEA:      /   /NOP IMPLICI     T     example       :NOP
                              ca   se   0xFA:
               //      2 byt e NOP         s
                              case   0x04:
                         case 0x14:
            case   0x34:
                   case 0x44:
              c    ase 0x54:
                    case 0    x          6 4:
                                           cas    e 0x74:
                              case         0x80: 
                             case 0x82:
            case    0 x89:
                                   c      as e 0xC2:
                    case    0xD4:   
            case  0xE 2:
                   case 0xF4:
                /    / 3 byte      NOP
                   case 0x0C:              
                   doNOP( opcode);
                                      brea     k;
                     c    ase   0x1C:
               case 0x3C:
                                            c         ase 0x5C    :  
             c   ase 0x              7C:
              case 0xDC:
            case 0xFC    :
                     getAbsolute  XRes   ult(pbo)     ; //     this wil   l add a cycle            on a page b  o   un     dary          cross
                                    doNOP(opcode);
                break;
                       case  0x0B:
                     case 0x2B:
                            doAAC(            getImm         edia         t    eResult(), o   pcode);
                br        eak  ;
                         case 0x4     B:
                                                 d oA SR   (g etImmediat  eR  esult(), opcode);      
                              break;
                  case 0x6B:
                        doARR( getImme d    iate                         Result(), op    code);
                    break;
                 ca     se        0xA     B:
                   doAT   X(g   etImmediateResu     lt(   ), opc   ode);
                   b      reak;
                           ca  se 0   xCB:
                 doAXS   (getImm ediateResult(),   opcode);
                      b    reak;
                 case 0x0       7  :
                           doSLO(get    ZeroPageAddress(),       opcode);
                                            brea      k;
              case 0x17:
                             doSLO(getZeroPageXAdd    ress  (),    opcode     );
                bre  ak    ;
                     cas       e 0x         0F:
                      doSLO(getAbso    luteA     d d                      ress(),         op     co de);
                                   break    ;
                                 c ase 0      x1F  :
                                      doS L       O(getAbsoluteXAddress(pb  o   ),  op     code);
                          bre    ak  ;
                        case  0x1B:
                                doSLO(getAbso   luteYA         ddress(pb   o), opcode);      
                        br   eak;
                           case 0x 03:
                        doSLO(getIndirectXA ddress(), opcode);
                                  break;
              case 0x13      :
                 doS     LO(getIndir      ectYAddres s(pbo),   opcod      e);
                                         br   e   ak;  
                 
                   case 0x9C:
                        doS    YA(   ge    tAbsoluteXAddress   (pbo   ), opcode);
                                      break;
             case        0               x 9E:
                         doSXA(getA     bsoluteYAd   dress    (pbo)   , opcod               e);
                    break;

                  case 0          x27:
                       doRLA(g                    etZeroP   ageA     ddress(),      o   pcod   e);   
                brea      k   ;
              case 0x37:
                                   doRLA(g     etZeroPageXAddres s(      ),               op        code);
                  break;
            case 0x2F:
                    d     oRLA(getAbsolut eAddress(), opcode);
                                  b  reak;
                   case 0x3F    :
                    do  R        L          A (getAb  soluteXAddress(p            bo), opcode);    
                  break;    
            case 0x3B:
                        doRLA(getAb     soluteY   A  ddres    s(p      bo), opcode);
                bre     ak;
                                                       case    0x23:
                      d   oRLA(getI       n d i    r      e       ctXAddre   ss(),                   opcode);
                    break;
            cas      e      0x33:
                          doRLA(getIn   directYAddress      (pbo)       , opco      de);
                                         bre           ak;


     
                  case 0x  47:
                           d    oSRE(getZeroPa  geA  dd    ress(  ),    opcode);
                    b          reak;
            case 0x    57:
                              doSRE(getZeroPa        geXAddress(),    opcode);
                           break;  
                 case   0    x4   F:
                                      do            SRE(   get    Ab     soluteA   dd  re   s    s(           ), opcode  )   ;
                b reak;
            case 0x5F:
                        d  oSRE(getAbsoluteXAddres  s(    pbo), opcod    e);
                      break;
                           case 0x5B:  
                                         doSRE(get          Absolu te YAddress(pbo), opcod e);
                            break;
                ca      se 0x43:
                doSRE   (g   etI       n     di re  ct  XAddress(), opcode);
                bre     ak;
            ca se 0x53:   
                          d   oSRE(getIn                  directYAd dress   (pbo), opcode);
                           br eak;


            case    0x67:
                d oR               RA(getZeroPa             geAddress(), opcode);
                             brea  k;
                    case 0x7 7:   
                        d    oRRA(getZeroPageXAddress(),     opc  ode)         ;
                              break;
                     case 0x6F:
                                   doR  RA(getAbsolut    eAddress(), opcode      );
                    bre   ak;
                         c ase 0     x7   F:
                    doRRA(getAbsol   uteXAddr          ess(pbo), o           pcode);
                      break;
               c       ase         0x   7B:
                         doRRA(getAbsolu  teYAddre               ss(pbo),          opcode);
                         bre           ak     ;
            case 0x63:
                          do RRA(getIndirectX     Ad       dr      ess(),   opcode); 
                 break;
                   ca         se 0x7      3:
                           doRRA(  getIndirectYAddress(p        bo), opcode     )   ;
                           break;

                   c  ase 0     x87 :    
                                  doAAX(ge  tZe     roPageAddr     ess(), op    code);
                          break;
                  ca   se 0    x97:
                    d    oAAX(getZ   eroPageYAddress()    ,            opc  ode);
                              br  eak;
                       case                  0x83:
                      doAAX(getIndire ctXAddress(), opcode);
                b  reak;
                  cas e 0x8 F:
                        doAAX(getAbsoluteAddress(), opcode);
                   break;
    
               ca se 0x    A7:
                     doLAX(get ZeroPa  geAddress(), o         pcode)    ;
                    break     ;
                  case 0xB7:
                     doLAX(getZer     oPage    YAd   dres  s(),    opcod  e);
                    break;
                                 case 0xAF :
                            doLAX(getAbsoluteAddress(), opcode);
                  break;
                   ca   se 0xBF:
                     doLAX(getAb soluteYAddress(pbo),  o pcode);
                        b r   ea          k;
            case        0xA3:
                                                   doLAX(ge  tIndi   rectX  Addr        ess()   ,         opc  ode);
                            break ;
                    cas e 0xB3:   
                    doLAX(getIn   directYAddress(pbo), o   pcode);
                break;

                          case 0xC7:
                 doDCP(getZeroPa     g       eAddress(), opcode);
                   break;
               case    0x D7:       
                   doD    CP(getZero PageXA          ddress(), o   pcode);
                        br   eak;
                   case        0    xCF     :
                      doDCP(getAbso    lute          Address(), opcode);  
                             break;
                     c ase 0xDF   :
                         doDCP(getA  bsol    uteXAddress(pb      o), opcode       );
                   break;
                                case 0xDB     :
                        doDCP             (ge   tAbs o         lu  teYA          ddr      e  ss(pbo), op     code);     
                         brea  k;
                 case 0xC3:
                  doDCP(getIn         directXAddress(    ), opc     ode) ;
                         break;
               c   ase 0xD3:
                                       doDCP(getIndirectYAdd r  ess(pbo), o     pco de);
                break;

              case 0xE7:
                       doIS   C(getZeroP ag          eAddress(), opc      ode);
                 break;
             case 0xF7:
                   doISC(     getZ  eroPageX  Address   (),    opcod    e);
                            break;
            case 0  x  EF:
                     doISC(getAb soluteAddress()       , op code  );
                break;       
                    case 0   xFF:
                doI    S  C(g   etAbs oluteXAddre    ss(pbo), opc ode);
                          b   reak;
                           case 0xFB:
                                   doISC(getAbsolute        YAddr    ess(pbo),     opcode)      ;
                  break;
                  case 0xE3    :
                d  oISC(getIndirectXAddress()     , o  pcode);  
                      br        eak;
            case 0xF3:
                 doI     SC(getIndirectYAddress(pbo), opcode);
                bre ak;

            c  ase 0    x02:
                   doKI    L(opcode);
                       break;  
                        
                 def   a ult:
                                          S      ystem    .err.println("      Developer error process        in   g opcode: " + By teF  o          rm     atter.formatByte( _me  m.g   etMemory(p  rogramCou        nter, memoryWatchMode                )) + " en      counte  red at  a     ddress    :" + Byte   Forma    tter.   formatIn         t(p  rogramCount  er));
                          ret        urn     f    alse; 
               }

           return true;
    } 

     publi  c int getNext  OpcodeAddress() {
        int addres  s = 0 ;
        int prog     r    amCounter = getPr og ramCounter();
                      i    nt hexVal = _ mem        .getMe mory(progr      amCounte    r, memor    yWatc   hMode) & 0xFF;
                  OpCode650    2 opcode = OpCode6502.OPCODES[hexVa      l];
           if (opcode =  = n     u   ll      ) {
                        new Exception("Invalid opcode: " + ByteFormatter.formatByte(_mem.getMem        ory(programCoun te r, memo    ryWa  tchMode)) + "    encountered at addre   ss:"    + B     yte     Formatter.forma          tIn    t(programCounter)) .print  StackTrace();
                   retur       n          address;
                }
            PageB  oundaryObserver p    bo = null  ;
           sw     itch (opco  d e  .ge     tA  d   dre   ssMo       de()) {
                        case Ar    ch          itecture6502.ABSOLUTE_     MODE:   
                      address       = g     etAbsol   uteAddress();    
                        break;
             case Archi    tecture6502.ZP_MODE:
                                ad       dress = getZe   roPageAdd     ress();
                            break;  
                   case Archi   te cture6502.IMMED     I ATE_MODE:
                          bre    ak       ;      // no address
               case   Archit   ecture6502.IMPLICIT _MODE:
                     break; // no addres          s
            case   Archit     ectu      re     6502      .A    CCUM            ULATOR_M  ODE:
                   break;         // no address
                            c  as     e Archit      e   cture  6502.ABSOLUTE_INDEXED_X_MODE:
                                  address = ge     tAb       s   oluteXAdd  ress(pbo);
                   break;
                 c      ase Architec      ture65   02.  ABSO         LUTE_INDEXED_Y_MODE:
                       a       ddr  ess = getAbsoluteYAdd        ress(pbo);
                      br eak;
                          case Architecture6502.ZP_ INDEXED_X   _MO    DE:   
                          ad   dress = getZe      r   oPage       XAddres          s();     
                      break;
               case Architectu    re6502.ZP_INDEXED  _Y_MODE:  
                                      address =      get     Zero  PageY     Addr  ess();
                  break;
              case   Archite   ct  ure6502.INDIRECT_ABSOLUTE_ MODE:
                    br   e  ak; // no address
                   c   ase Architecture6 502. INDEXED_INDI   RECT_X_MOD      E   :
                       addre ss = getIndirect     XAddress(    );
                break;
                          ca  se Arc     hit     ecture6502.  IN   DIR       ECT   _INDEXED_Y_MODE:
                           addre    s      s = g  etIn   directYAd    dre ss(pbo          );
                       break;
            case Architecture  6502.RELATIVE       _MODE:
                                 bre     ak; // n     o ad  dress
             default:  
                break; // no address
        }
            r             eturn addre      ss;
         }

    publi   c in        t getAddressFromROMData(int  zp         Memor yLocation) {  
              re   tur      n Utilities6502.c  alculat             e16           Bit    Ad    dress(_mem.get       Memory(zpMem    oryLo  cation  , memo    ryW   atchMode), _mem.getMe    mory(zpMemor  yLocation      +    1, memoryWa      tchMode)    );
      }

    public int     getIndirectXA     ddress( int   operand    , int xVal)   {    
          // memory is    stored withi    n the   ROM in this c  ase.       ..
                               byte lowByt           e = _mem.ge  tM          emory((   op         erand +         xVal) & 0xFF, memoryWatc   hMo    de);
                by      te high   Byte = _mem.getMemory((opera     nd + xVal + 1    )   & 0x  FF,   memoryWatchMode);
                    return   Utilities6502.calculate16B  itAddr ess(lowByt    e, highByte);
    }

    public in t get    IndirectXAddress_d  irect(i    nt oper and       , int     xVal) {
        // memory is store      d within     the ROM in this case        ...
             byte lowByte  = _mem.ge   tMemoryDirect((operand +  x Val) & 0xF    F       );
        byte    hi   g                 hB  yte = _mem.ge   tMem     oryDirect((operand +       x   Val + 1) &     0xFF);
                    return Utilities650   2.calculate        16BitAd    dr          ess(lowByte, highBy        te);
               }

    p  rivate     int getAb soluteAddre     ss(    ) {     
          retu      rn Utilities6502.calculate16Bit  Address  (_mem.ge tMe   mory(     _programCounter + 1, memor     yWatch  Mode), _   mem.getM   emory(_programCounter  + 2, memo ryWatchMode));
    }

    pr       ivate int     get Absol        uteAddres       s_direct(   ) {
          retur  n Utilit  ies6502.calcu   late1  6BitA            ddres s(_mem.g          etMemoryD      i      rect(_progr    amCounte   r  +      1), _mem.getMemoryDi   r   ect(_programCounter + 2));        
    }

        private in    t getR      e  lativeAddress() {
        return ((_pr         ogram      Coun  ter + (_mem.g        et   Me    mory(_programCounte   r +   1, memoryWatchMode)) + 2    )         &    0x        FFFF);
    }

         pr        ivate int getRelativeAddress_direct() {
        retu    r     n    (_programCount   er +  (       _          mem.getMem oryDire     c  t(_programCounter + 1)) + 2) & 0xFFFF;
        }

          private int getAbsoluteXA   ddress(PageBou      n d a ryObserver callback) {
              retur       n Utilities      6502.calculate16BitAddressWithOffset(_mem.getMemory(     _programC           ounter    + 1, mem          oryW  a  tchMode),     _m  em.getMemory(_programCou    nter + 2, me         mo   ryW  atchMo    de),     (_xRegister & 0xFF), callback);
    }

    private int getAbsoluteXAdd  ress_direct(PageB   oundaryObserver        callback) {
           ret  urn Utili   ties   6502.cal  culate16BitAddressWithOffset(_mem   .getMemoryDir                   ec  t(_program   Counter     + 1), _mem.getMemoryD          irect(_pro           gramCount   er + 2), (_xRegi   ster & 0x         F  F), callback);
    }

    private int getAbsoluteYAddress(Page     Boun d    aryObserver callback) {
        re  turn   Utilities6502.ca    lculate16Bi   tAddressWithOff  set(_me m.   get Memor    y(_prog ramCounte        r + 1,   memory   Wa     t    chMode), _me   m.getMemory(_p  ro   gramCounter + 2, m       emoryWatc       h  M     o de), (_yRegister & 0xF         F), callback);
    }

    pri vate in      t getAbsoluteYAddress_d   ire ct(Page  BoundaryO   bserver ca    llback) {
            return Utilit       ies65 02.calculat   e16B  itAd  dressWithOffset(_mem.getMemor  yDi  rect ( _ pr  ogramCounter     + 1), _mem.getMemoryDirect(_programCount   er + 2),   (_yRegister & 0x   FF),  callback);
    }

    private i     nt    get  ZeroPageAddress() { 
          ret          urn (_mem.getMemory(_programCo   unter + 1, memory W    atchMode)    )& 0xFF;
    }
                     
    private int getZeroPageAddress_d   irect()     {
         retur    n (  _mem   .g   etMemoryDirect(_p   r ogram       Count    er +   1) ) & 0xFF;
    }

    priv      ate int getZeroPageXA ddress() {
           return (((_mem.get  Memo    ry(          _p    rogramC ounter +    1, mem     oryWatchM  ode)) + (_xRegist       er & 0     x FF )    ) & 0xFF         )     ;      
       }

       private int getZer  oPageXA  ddress_direct()   {
                retur   n (((_mem.g         etMe moryDirec  t(_programCounter + 1)) + (_xRe       gister     & 0xFF)) & 0xFF    );   
    }

    private int get  Z  eroPageYAd       dress () {
            int pr  ogramC ounter = getPr   o       gramCou        nter();
               int yVal = getYRegist        er() & 0xF         F;
           byte ope    rand   = _mem.getMemory(programCounter + 1    , memoryW  at chMod e);
         ret     urn   ((operand   + yVal)         &      0x   FF);
        }

          private   int   get     ZeroPageYAddress_direc  t()    {
           int pro    g    ramCounter = getProgramCount    er();
                 int yVal = get     YRegi   ste        r() &  0xFF;
        byte oper and =     _mem.getMemoryDirect(progra    mCou  nte  r + 1); 
                   r  eturn         ((operan d  + yVal)   & 0x      FF    ) ;
       }    

    pr   i  va   te int g   et    I           ndirectXA     ddress() {
          int xVal =         getXRegister() & 0xFF;
              return get           IndirectX  Addres   s  (x   Val)    ;      
    } 

    private int   getIndirectXAddress   ( int xVal)    {
                                   int     pro gra     m    C    ounter = getP          rogr     amCounter();
            byte    operand =   _mem.getMem          or  y(p      rogramC ounter + 1, memoryWatch    M    ode);
        return getIndirectXAd       dre  ss(operand, xVal);
    }

      private int       getIndirectXA   ddress_direct() {
        int xVal = getXRegist   er(    ) & 0xFF;
               return       getIndirectXAddre   s    s    (xVal  );
    }

     p      rivate int  getIndi   rectXAddress  _direct     (int xVal) {
             int program   C ounter = getProgra  mCoun   ter();
                     by   t  e operand = _mem.         getM  e   moryDirect(pr       ogramCounter + 1);
        return ge  t  I     ndir        e    ctXAddress_direc   t    (operand, x  Val);
    }

            private int ge   tIndirect  YAddr           ess (P  ageBoundaryObserver callback) {  
        int yVal = getYRe  g     ister() & 0xFF;
           return getIndirec  tYAddress(yVal, cal           lback);
    }

    private int getIndirectYA   ddress(int y     V       al, PageBou  ndar  yObserv      er cal   lba      ck   )  {
        int pro       gram            Co           unter =  getProgramCounter()      ;
        byte op       e  rand     = _mem.getMemory(programCou    nter + 1,       memoryWatchMode);    
        //     m   emory is    stored with                 in t    he ROM in this ca   se     ..   .
        byte lowByte = _mem.getM  emory((operand)    & 0xFF, memoryWatchMode) ;
        byte highByte =   _mem.getMemor   y((ope  rand + 1) & 0xFF,              mem  oryWa     tchMode);
        ret    urn Ut  ilit ies6502.c     alculate16BitA     ddressWithOff        set(lowByte, highByt   e, y       Val, callba   ck);
      }

      p     rivate   int getIndirectY        A d  dress_direc        t( PageBoundaryObserver              c allback) {
               int yVal =       g  et   YRegister()             & 0xFF;
                  r eturn getIn  d  ire           ctYAdd    ress_direct(yVa  l, callback);
    }
    
       private int getIndire       ctYA   ddress_           direct(int yVa  l, P   ageBo           undaryObse  rver c    allb   ack    )    {
          int programCounter = getProgramC   ou nt         er();
        byt  e o  pe     rand = _mem.getM        e     mo   ryD   irect(programC     ounter + 1)   ;  
        // m     emory is stored within the ROM  in t    his   cas            e...
          byte lo      w   Byte = _         m     em.    getMemoryDire   ct((operand) & 0xFF     );
         byte highB yte = _mem.getMem                      oryDirect((ope       ra       nd + 1) & 0xFF);
                   r            e  turn   Utili tie     s6502.calculate16   BitA    ddre       ssWithOffset(lowByte    , high   Byte, yVal, callb   ack);
          }

    private int g etIn directA            b     solut         eA        ddress() {
           //    An or       iginal 65   02   has     does not  co   rrectly fe tch     th  e t  arget address if the i   nd irect vec tor falls           on a page boundary (e. g. $xxFF where xx     is   and val  ue from   $0   0 to $         FF). 
             // In this case fe  tches the       LSB     f    rom $xx FF as     ex    pec  t        ed       but takes the  MSB from      $xx00.     
              //   This is fixed      in some late      r ch      ip      s like the 65SC     02                       so for comp   atibility alwa   y  s ensur     e the indir  ect    vector is not at t    he end o           f the page     .


                       in  t programCounter = getProgramCounter(   );  
            byte op0     = _mem.     g e  tMe    mory(prog  ram    Co  unter +  1, m    emoryWatchMode);
                      by t      e op1 = _mem.getM  emory(p       rog       ramCounter + 2, memoryWatc   hMode) ;  
        int address = Utilitie        s6502.calculate16  BitAddress(op0, op1);
              int hig           haddress = address + 1;
        if (op0         == (byte      ) 0  xFF             )        {
                  //              boun         dary case
                 h   ighaddress = Utiliti      es6     502.calculat   e16  BitAd dr     ess((by       t  e)   0x00, op1);
        }

                  byte lowByte = _      me     m.getMemor   y    (address, memor   yWa          tchMode);
        byt e high    Byte = _m    em. getMe   mory(highaddress, memoryWa tc   hMode       );
                return Ut   ilities6502.calculat   e16BitAddr ess(lowByte,  highByte);   
    }

    priv   ate i          nt g      etIndirectAbsolut  eAddress_direct() {
             // A  n  original 6502 has does not cor    rectly fet  ch the t  a   rget a       ddress if the i  ndirect vec   tor  fal ls on a page boundary (e.g. $x   xFF   where xx is an         d value from $00 to $FF).  
        // In       this case    f etches the LSB f  r         om $xxFF   as        expe     cted but tak       es the MSB     fr  om $            xx00   .
        //   This is fi         xed     i  n some la ter c  hips lik  e t he 6   5SC02     so fo    r compatibility always ensure the   i           ndi   rec    t vect   or     is not at the end          of       the page.


           int p        rogramCounter      = getProgramCounter   (   );  
          byte op0 = _mem.get     MemoryDirect(pr  og     ramCounter      + 1);     
             byte    o  p1 = _   mem.getMemoryDire     ct(pr    o g  ramCounte         r   + 2   );   
                 int      address =          Utilities   6502.c   alc    ulate16    BitAd    dres   s    (op0,  op  1);
          int highaddress = add ress  + 1;
            if (o         p0 == (byte) 0xFF       ) {
                          // bou  ndary c   ase
                        highad    dress = Uti  li     t    ie    s650   2.calcula      te16BitAddre     ss((b    yte     ) 0x00, o p1);
         }

             b       yte lowByte = _mem  .ge         t    MemoryDire  ct(addre     ss);
                      byt   e high  Byte = _         me    m. getMem        oryDirect(highaddress);
                  retur   n   Utilities6502.c  alculate16BitA    ddress(lowB    yte, highByte);
        }

    private byte getImm   ediateR   esult() {
         int p    rogramCo   unte      r = getProgramCounter();
        return _  mem.ge tMemory(prog     ra    mCount             er + 1,   me    moryWatchMod    e);
    }

    p      rivate byte     g etImmediateResult_direc           t() {
        int progra   mCo        unter =  getPr  ogramCoun  ter(   );
              return _mem.      getMemoryD    ire   ct(programCou    nter + 1    );
    }

         p   riv   ate     byte    getAbs  oluteResult() {
        r   et   ur n _mem .g     etMemor y(g  etAbsoluteAddress()       , me  moryWatch       Mode       );
          }

    private byte getAbsoluteResult_direct() {
                return       _mem.  getMemoryDire  ct(getAb solute  Address());
    }
    
    private   byte getA           bsolute         XResult(PageBo undar    yObserve  r ca    llbac   k) {
        return _mem.getMemory(getAbs       olu  teX   Address(callbac       k), me      moryWa    tchMode);
     }

        private byte getAbsoluteXResult  _  direct(PageBo undaryOb     server callback ) {
           return _mem     .get  Mem  oryDirect(   getAb    so  l        uteXA     ddre     ss(callback));
           }

                          privat    e byte getAbs     oluteYResu     lt(PageBoundary    Observer   ca   llback) {  
            retu     rn   _m em.getMemory(g   etAbsoluteYAddress(call  ba    ck), me    moryWatchMode);
    }

        private byte getAbsolut     eYResult_direct(Page BoundaryOb    server cal    lback) {
        return _    m     em.get      MemoryDirect(getAb    s           olut     eYAddre       ss_dir ect(cal    lback));
    }

    private     byte g     etZeroPageResult() {
              return _m   em.getMemory(getZeroPage    Address(), memo ryWatchM ode);
    }

    pri  vate by  te getZeroPa        geR  esult_direct      ()        {
         return    _mem.getMe       mo   r     yDi  re ct(getZeroPa   geAddress_direct());
    }

           private      byt  e getZeroPa  geXR     esult() {
          r     eturn _mem .g   e    tMem    ory(ge tZeroPag          eXAddr       ess(), memoryWatchMode      );  
    } 

    private by  te   ge    tZero            PageXResult_direct() {
                 ret    urn _mem.g  etMemoryD     irect(getZero PageXAddres        s_direct   ());
    } 

              p    r           iv    ate byte g     etZ e           roPageYRes   ul   t() {
           return _me   m.   getMemory(       ge           tZe  ro          PageYAddress(), memoryWatchMod e);
                        }
    
    pr i va      te byte getZeroPageYResult_direct() {
               re  turn _mem  .getMemoryDirect(getZeroPageYA ddress       _direct       ())    ;      
    }

                priva            te byte ge  t    IndirectX  Re  sult()    {
            retu  rn _mem.g  etMemo    ry(getIndirectXAdd           ress(), mem o                  r   yWatchM   ode);
    }      
        
            privat    e  byte getIndirectYResult(Pa   ge     Bound     aryObse   rve  r c  al   lback) {    
        return _  mem.getMe    mory(get    IndirectYAddress(callback), me moryWatchM   od  e);
        }

           priva  te byte ge    tIndirectX   Resu lt_dir   e   ct(   ) {
        return _mem.getMemoryDirect(getIndirectXAd     dres      s_direct());
    }

       private byt  e getI      ndirec   tY    Result_d    irect(P       age Boun dar    yObserver      cal     lback     ) {
        return _mem.ge                            tMe moryDi  rect(       getIndirec      tY           Address_      direc     t     (callback));
       }

    //    bit   7 =     0x80 
       private bool    ean  isBi    tS  et(byte                        val, int         bit) {
            return (((val >> bit) & 0x0    1) == 0x01);
    }

    /* 
     *I g nore            th  is
        L  ogic:
    t = A + M    + P.C
                     P .V = (A.7!=t.7) ? 1:0
    P   .N = A.7
         P.Z = (t==0)   ? 1      :0
        IF (P.   D)
           t = bcd(A    ) + bcd(M) + P.C
    P.C =      (             t>99) ?    1:0
     ELSE
      P.    C = (t>255)     ? 1:0
      A = t &    0xF     F
      *
     *According   to   http://www.obe      lisk  .demon.c    o.uk/650     2/re          fere  nce.    html#ADC
         *
       This          instruction adds the conte              n   ts   of a     m emory      location to the accumulator together with the carry bit. If    o  verflow occurs the       ca   rry bit            is set, this enable      s    multi   ple byte addi    tion to        be    perfor   med.

     Proces  sor      S     tatus after use:

          C C            arr   y                   Flag    Se  t if             overf        low in bit 7      
    Z Ze  ro Fl   ag    Set    i  f         A = 0
    I Inte  rrupt Disable Not affect ed
    D D   e cimal Mode Fla   g Not aff          ected
    B Break Command No t    af   fected
        V Overflow Fl   ag Set if si      gn b          it is i  ncorrect
    N Neg  a tive Flag Se t if      bit 7 se    t

    c966

          */
    private void   doADC(b  yte va    lue, OpCode6       502 opco    de) {  
           byte     oldA = getAccum ulator();
        boolean oldCa   rry   = getCarryFlag();
         int      n ewVa    l = old     A + value  ; // Note: the bytes au                  t    o c  o   nvert using 2   s complement         which   helps     for t he overflow check
        if (oldCarry) {
                   newVal+   +;
             }
                      int   carr  yChec       k = (oldA & 0xF    F) + (v         alue        &  0xFF) + (old                   Car     ry ? 1          : 0);
                 setCarryF la           g   (c     arryC    heck >           0xFF);  
          b  yte     newB = (byte) (newVal &   0xFF);
           setOverfl    owFlag(ne  wVal < -128    |  | newVal > 1    2 7); // si  n        ce range is -   128 to +127  
          setNegativeFlag(is  BitSe  t(newB,  7));
                   setZer oFlag(n    ewB    =   = 0);

                            setAccumulator(( byte) (newVal &           0xFF));
           incrementProgramCounter       (    o     pcode.getLen   gth());
         }

                  /*
    Logic:
            A =      A & M
      P.      N       = A              .7
        P.Z =    (A==0) ?          1:0
     */
    privat   e void do            AND(byte value, OpCode6502 opcode)    {    
               b  yte newA = (byte) (g   etAcc            umu   lator (  ) & value);
                  set  A             ccumula                  tor(newA);
        setNega tive  Fl   ag      (isBitSet(newA, 7));
        setZeroFl  ag(new  A == 0   );  
           incrementP  rogramCount     er(opcode.getLe    ngth());
            }
     
         /*
    Logic:
    P.C = B.7
     B =     (B << 1) & $FE
    P.N = B.7
        P.     Z = (B==0)       ? 1:0   
        *  Basically  thi  s alters t         he      c   ontent     s at the add   ress. the add  ress can be the accumulator   
     *                     /
    pri  vate void doASL(i        nt    destA    ddre  ss, OpCode6502 o pcode) {
                     byt     e old           Val = 0;    
        if (destAdd  ress  ==   ACC    UMULATO     R_ADDRESS) {
                          o ldVal = get   Accu        m           ulator();
                       } else {
             oldVal = _mem.g         etMemory(destAddr ess, memoryWatchM    od   e)          ;
                }
           setCa    rryFlag(is          Bi  tSe  t(oldVal,        7));
        by  te newVal = (byte) ((old  Val <<     1) &     0xFE );
         s  etNegativeFlag(isBitSet(newVal, 7)   );
        setZeroFlag(newVal   ==   0);
        if      (destAddres    s == ACCU  MULA    T O R_ADDRESS) {
                   setAc   cumulator(newVal);
                      } else {
            _mem.setMemo   ry(destAddr                    ess, n     ewVa      l,  memory   Wa     tc    h               Mode)  ;
          }        
          incrementProgramCounter(        opcode  .getLength     ());        
    }

    /*
        Logic:  
        t = A & M
        P.N = t.7
    P.V = t.6
          P.Z    = (t==0) ? 1:0
     
     */        
    private voi    d doBIT(byte value, OpC  o  d    e65    02 opcode) {
             byte tem   p = (byte) (   getAccumulator()  & value);
              setNe    gativeF lag(isBitSe   t(v  al      ue, 7)   );
                setOverflowFlag(isBitS        et(va  lue, 6));
          set    Zero      Flag(temp   == 0);
                  incremen      tP  rog       ramCounter(opc   ode.getLength  ()    );
    }

          private void d         oBranchOp(boolean  doBranch, i n t       ad   dress, OpCode6  502 o      pcod    e) {
           /   * Accor         ding to Bla     r    gg
            Bran  ch Timing Su mmary
        ---------------------
                       An untaken bran      ch takes 2 clo cks  .   A taken branch takes 3 clocks. A t       aken
            branch that crosses a page    ta    kes 4   cloc    ks. Page    crossing oc      curs   when the
               high b  yte       of the             branc   h target address is differen           t than the high byte
        of address    of the nex          t i   nstruct                   ion:
               */
    	

                   if (doBranch) {
                  // ha    ndle pa   ge boundary cross
                  if (((getProgr   amC    ounter()      + o   pcode.get L   en   g   th()      ) & 0xFF00) != (ad   dres   s    & 0x FF     00)) {
                 _n  umCycle         sRemaining++   ; // c   rossed a    page so incre      ment cycles by 1
                   }
            setProgramCounte        r(a   ddress);
            _numCyclesRe   mainin  g++; // We t    ook the branch so i   ncrement t     he   cycles by 1
        } else {
                      inc  rem    entProgramCounter(opcode.getLen  gth   ());   
        }
    }
    /*
     *Branch if         carry fl       ag set
      if (P.C == 1    ) GOTO (     PC+M)
         */   

    pri  vate void doBCS(in t addres   s,      OpCod    e  6502 opcode) {
               doB     ranchOp(ge    tC    a  rryFlag    () == tr  ue    , ad    dress,  opcode);
                }
    /*
        *Branch if carry flag  cl    ear
    if   (         P.C == 0) GOT   O   (PC+M)
       */

    private vo    id doB CC(int address     ,    O    pCode6502 opcode   ) {
            doBr       anchOp(getCarryFlag() ==    false     , address, o          pcod   e);
    }
               /* Branch if ZE RO   flag is s    et
     *          if (P.Z == 1) GOTO (PC+M)  
       */

           private vo      id doBEQ(int         address, O     pCode65 02 op   code) {
            doBranchOp(getZeroFlag() ==    true,    addre    ss, opcode  );
    }
        /      *
     *       Branch if    negative fla   g se    t
    i    f (P.N ==      1) GOTO   (PC+M)
     */

    private void doBMI(i    nt a            ddress, O                      pC ode6502 opcode     ) {
        doBr     anc  hOp(getNegative   Flag() == true,  address,   opc  od e);
    }
      /*
     *B ranch i     f zero flag cl ear
              if (P.Z == 0)     GOTO (PC   +M)
      */

            priv            ate void doBN      E(int addres      s,    OpCo         de6502 opcode) {
        doBran       chOp(getZeroFl  ag()   == false, addr  ess,          opcode);
    }
         / *
     *   /

    priv      ate voi           d doBPL(int addr    es       s, OpCod  e6502 opcode   ) {
                 doBranchOp(getNegativ     eFlag() == false, address, opc   ode);
         }
    /*
     *Branch if o   verflow flag i       s cle   ar
       if (P.V == 0  ) GO   TO (PC+M)  
     */

    priv          a   te void doB   VC(int address            , OpCode6502 opcode) {
           doBranchOp(   getOverflowFla   g() == false,  ad  dress, opcode)   ;   
    }
    /*
            *B   ranch if      overflow flag is set
       if (P.V   == 1) GOT   O (PC+M)
        */

    private    void doBVS(i nt address, OpCo              de650   2 opcode) {
                    doBr anchO  p(    ge    tOverflowFlag() == true, address, opco    de)     ;
    }

    /*
               * Cle   ar carry flag
      P     .C = 0
     */
    private voi    d    doC   LC(O pCode6502 opcode) {
        setCarryFlag(false);
        incrementProgram  Counte       r(opcode.getLen    gth()  );
    }
    /*
     * Cl    ear decimal    flag
    P.D = 0
     */

    p    rivate voi  d doCLD(OpCode6502 op    code)       {
        setDecimalFla   g(false);
               incrementProg    ramCounter(opc ode.getL   ength(   ));
    }
    /*
          *Clear              in      terrupts        flag
     P.I = 0
     */

       pr    ivate void        d    oCLI(OpCod     e6502    opcode    ) {
              setInte   rruptFlag (f   al se);    
        inc    rement  ProgramCou nter(opcode.getLength());
               cli            Latency =   true;
    }
    /*
     *Clear overflow flag 
        P    .V = 0
     */

                 priv    ate void doCLV(OpCode   6502 opcod          e) {
                 setOverflowFlag(false);
        incrementProgramCounte  r     (opcode.getLength( ));
     }

    /*
          *Compare A wi th    M     emory
      L ogic:   
      t = A     - M
    P.N = t.   7
    P.     C = (   A>= M) ? 1:0 (unsi      gned)
    P.Z = (t==0) ? 1:0
     */
      pri   vate void   doCMP  (byte value, OpC  ode6502 opcode) { 
               b        yte regVal = (byte)    ge  tAccumulator();
                 byte diff = (b      yte) ((regVal - va   l    ue ) & 0xFF);
        setCarryFlag(   (regVal & 0xFF) >= (value &     0   xFF));
                setZeroFlag(r     egVal == value);
          se     t    Negati   veFla       g(isBitSet   (dif  f, 7));
        incrementPr    ogramC      ounter(  op    code.getLength());
    }
       
    /*
    Compa  re X wit  h  Memory
    Lo      gic:
      t = X       - M
      P     .N = t.  7
      P.C =          (      X>=M     ) ? 1:0
          P.Z = (t    ==0) ? 1:0
        *   /
    privat  e void doCPX(byte v     alue, OpCode6502 opcode) {
             byte re     g   Val = (byte) getXRegister()    ;
        byte diff = (    byte) ((regVal - va   lue) &   0xFF);
        setCarryFlag((regV al & 0x FF) >= (value & 0x FF));
            setZero Flag(regVal =    = v  alue);
           setNegativeFl  ag(isBitSet(d    iff, 7));
        incre       mentP rogram     Counter(o   pcode.getLength(      ));  
       }
    /*
    Compare      Y         wi     th Memo   ry
    Logic:
    t = Y - M
    P.N = t.7
    P.C = (Y>=M) ? 1:   0
    P.Z = (t      ==0) ?    1    :0
     */        

    private void     d     oCPY(byte value, OpCode     650    2 opcod  e) {
                 b   yte regVal = (byte) getYRegister()   ;
        byte diff = (b   yte) ((regVal  - v     alu    e)         & 0xFF);  
              setCarryFlag((regVal & 0xFF) >= (value & 0xFF)) ;
        setZeroFlag(regVal == value);
                      se tNegativeFlag(isBitSet(diff    , 7));
          increme    n     tProgramCounter(opcode.getLength()) ;
    }

    /*
        *Decremen  t Memory by one
    Logic:
       M = (M - 1) & $    FF
    P.N = M.7
           P.Z =  (M==0)  ? 1:0
     *   DEC do      es NOT affe  ct the Carry Fl     ag  (P.C           )    or oVerflow Flag    (P.V)
       */
      private    void doDEC(int address, O pCode65  02 opcode) {
             byte oldV     a  l =   _mem.getMe     mor   y(   address, mem oryWatchMode);
                                 b      yte newVal =   (byte) ((         oldVal - 1) & 0xFF);
        setZero      Flag   (newVal == 0);    
             se    tNegat   iveFlag(isBitSet(newVal   ,    7));
          _mem.setM  emory   (address, newVal, memoryWatchMode);
        i  ncrementPro        gramCounter(opco  de.getL ength());
    }   
  
              /*
    Logic:       
         X =  X - 1
    P.Z =    (X==0) ? 1 :0
        P .N        =                       X.7
     *DEX does N        OT affec    t the C            arry Fl    ag (P.     C) or o        Verf low Flag (P.V)
        */
     private vo     id doDEX(OpCode6502 opcode) {
        byte oldVal = getXRegister();
            byt    e   newVal = (byte) ((oldVal     - 1) & 0x  FF);
        set XRegister(new    Val);
         setZeroFlag(newVa      l == 0);
        setNegativ            eFlag(isBitSet(newVal,  7));
        i   ncrementProgramCounter (opcode.getLength());
    }
    /*
    Logic  :
    Y      =  Y - 1
    P.Z = (   Y==0) ? 1:0
    P.  N = Y.7
     *DEY does NOT affect t         he Carry Flag    (P.C) or oVerflow Flag (P.V)
       */

    pri  vate voi  d doDEY(OpCod   e6502 opcode) {
           byte oldVal = ge    tYRegister();
            byte newVal =         (byt    e) ((oldVal - 1) & 0xFF  );
        setYRegister(newVal);
        setZero Flag     (newVal =  =     0);
            setNegativeF    lag(isB  itS          et(newVal, 7));
        increm  entProgr   amCounter(o        pco       de.getLength());
    }
            /*
        Logic:
    A = A ^ M
    P.   N = A.    7
     P. Z = (    A==0   ) ? 1       :0
     */

    priva          te void    d    oEOR(byte value,     O pCode 6502 opcod  e) {
        byte old  A           = g   etA    ccumulator();
           byt   e new A = (byte) (oldA ^ value);
        se  tAccumulat or(n ewA);
        setNegativeFlag(isBitSet(newA,        7))  ;
        se  tZer   oFlag(newA == 0);
        incrementProgramCounter(o  pcode.getL  ength(        ));
    }

          /*
     *In   crement Memory by one
     *Logi      c:
    M      = (M +   1) & $FF
            P.     N = M   .7
    P.Z = (M==0) ? 1:0
        INC does NOT affec t the Carry Flag (P.C  ) or   oVerflow Fl  ag       (P.V)
          */
    p   rivate vo   id d o  INC(int                  address, OpCode6502 opcode) {
            byte old  Val        = _m em.getMemory(address,  memor    yWatchMode     );
                byte newVal = (byte) ((oldVal + 1) & 0   xFF    );
              se tZeroFlag(newVal == 0);
          setNegativeFlag    (     isBitSet(newVal, 7));
            _  mem.setMemory(address, newVa      l,      me moryWatchMod e);
         incrementProgra   mCou        nter(opcode .getLen      gth     ()); 
    }
         /*
     *Logic:   
    X = X + 1
    P.Z       = (X==0) ? 1:0
    P.   N = X.7
       INX        does NOT aff     ect    the Carry Flag (P.C    ) or  oVerflow Flag (P.V)
     */

    private void doINX(OpCode6     502 opcode) {
                byte oldVa  l = getXR   egister();
        byte newV    al = (by  te)   ((oldV     al + 1) & 0xFF) ;
                  setXRegiste   r(newVa     l);
                setZeroFlag(newVal ==         0);
        setNegativeFla       g (isBitSet(newVal, 7));
           incrementProgramCounter(opcode.getLength());
    }    
    /*
     *Logi              c:
           Y = Y + 1
       P.    Z = (Y==0) ? 1:0
    P.N    = Y.  7
    INY  does          NO     T affe   ct the Carr    y Fla g (P.C) or oVerflow Fl   ag (P.V)     
           */
   
        private v oid d                     oINY(OpCode6502 opcode) {
        byte oldVa l = g       etYRe   gist      er()  ;
        byte newVal = (by    te)     ((o ld      V  al + 1) & 0xFF);
             setY       Register(ne   wVal);
        setZeroFlag(newVal == 0);
        s  etNegativeFlag(isB itSet(n  e  wVal,   7));
        incrementProgramCount    er(opcode   .getLength());
    }

    /*
           Logic:
    t    = P   C - 1   
    bPoke(S   P,t.h)
    SP =       SP - 1
    bPoke(SP,t.l)
    SP = SP - 1  
    PC = $A5B6
     *  /  
    private void     doJSR(int address, OpCod  e6502 opcode) {
          // push curren t prog ram counter (after instruction) -1 onto               stack (high byte first     )
                 int tempPC      = getProgramCounter() + opcode   .getLeng        th() - 1;      // address o    f next instruction (-1)
        byte lowByte     = (byte)   (tem   pPC & 0xFF);
             byte highByte = (b             yte)   ((tempPC >> 8   ) & 0xFF  )  ;
               pushByteOnStack(highByte);
        pushB yteOnStack(lowByte)  ;
            setPr ogramCounter(address);
    }

       /*
    Logic:
    PC = M
     */
          private void doJMP(  int address,         OpCode6502         opco  de) {
        setProgramCounter(address);
    }

           /* Load A with Memory
        Logic:
    A = M
    P.N = A.7
    P.Z = (A==0) ? 1:0
     */
    private void doLDA(byt      e       va    lue, OpCode    6502 opcode) {
             set   Accum    ulato      r(value);
          setNe    gativeFlag(is Bi    tSet    (value, 7));
          setZe     roFlag(valu   e == 0);
          in  crementP     rogramCoun ter(opcode.getLength());
    }
    /* Load X with     Memory
    Logi   c:
    X = M
    P.N = X.7
    P.Z = (X==0) ? 1:0
     */

    private      void doLDX(byte value, OpCode6502   opc     od   e) {
        set    XRegister(value);
            setNegativeFlag(isB   itSet(valu e, 7));
              setZeroFlag(valu   e == 0);
         incr     ementProgramCounte  r(    opcode.getLength());    
    }
         /* Load Y wi     th Memory
    Logic:
    Y     = M  
    P.N    = Y.7
    P.Z =   (Y                 ==0) ?         1:0
             */

    pr             ivate void doLDY(b  yte va  lue, OpCode6502 opcode) {
        setYReg  ister(value)      ;
        setNegativ     eFlag(isBitSet(value, 7));
          setZeroFlag(value   == 0);
        i         ncr     ementProgramCounter  (opcode.getLeng   th());
          }

             /*
      Logic:
           P.N = 0
    P  .   C = B.0     
    B = (  B >> 1) & $7   F
    P.Z = (B==0) ? 1:    0
     * Basical  l    y this alters the content   s a t        the a    ddress. the addr  ess can be the accumulator
     */
    private void doLSR(int d        estAddress,    Op    Code6502 opcode) {
        by  te oldVal = 0;
        i f           (destAddress  ==     ACCUMULATOR_ADDR    ESS) {
                   oldVal = getAccumula  tor(    );
        } else      {
                   old Val = _mem.getMemory(de     stAddress, memoryWatchMode);
           }
        setNegativeFl ag(false);
          setCa  r    r    yFlag(isBitSe    t(ol   d    Val, 0));
             by    te ne wVal = (byte) ((oldVal >> 1) & 0  x7F);
          setZ   eroFlag(newVal == 0);
          if (     des tAddress ==   AC     CUMULATO         R_ADDRESS) {
            se       tA  ccumulator(ne     wVal);
             } else {
            _mem.setM emo        ry(d  estAddress, newVal, memoryWatc    hMode);
             }
        i       ncrementProgramCounter(opcode.ge      tLength      ()); 
    }

    private void doNOP(OpCod    e6502    op code) {
         incrementProg    r    amCounte r(       opcode.getLength(    ));
    }

    /*
         Logic:
    A = A | M
    P.N =   A.7
     P.Z =   (A==0) ? 1:0
     */
    private void doOR  A(   byte value, OpCode6502 opcode) {
        byte newA = (    byte )   (getAccumulator() | value);
        setAccu   mulator(newA);
             setNegativeFlag(isBitSet(newA, 7));
        setZero     Flag(newA     == 0);
        i ncrementProgramCou  nte  r(opcode.getLength());
    }  

    /*
    pus    h(A)
    decr em     ent StackPointer
         */
    private vo        id doPHA(OpCode6502 opco  de) {
          p ushByteOnStack((byte) getAccu mulator());
         incrementProgramCounter(opcode.getLe     ngth())    ;
    }
    /*
    pus    h(P)
    decrement StackPointer
     */

    private vo     id doPH         P(OpCode6502 opcode)  {
          // PHP  "adds t    he Break fl         ag b  it to the      existing flags whe   n it is pus      hed"
        pushByteOnStack((by    te) (get       Flags   () | Architecture  6502.BREAK_COMM      AND_FLAG    _MA     SK));
        incremen       tProgramCounter(opcode.getLe ngth());
    }
    /*
    increment stack pointer
    A = pop()
     */

    private void doPL        A(Op   Code6502 opcode) {
             setAccumulator(popByteFromStack      ());
            b     yte oldA = g     et       Accumulator();
                     setZeroFlag(oldA ==     0);
                   setNegativeFlag(i     sBitSet(    oldA, 7));
          incrementP    rog    ra  m Counter(opcode.getLength());     
    }
    
       /*
    i ncrem     en      t stack      poin ter
    P = pop() 
        */
    
    priv   ate void doPLP(OpCode6502 o  pcode) {
        setFlags(popByteFromStack());
                incrementProgr     amCount      er(opcode.getLength()   );
            cliLatency = true;
    }

      /*
    t = B   .     7
    B = (B << 1)    & $FE
    B = B | P.C
    P.C = t
    P.Z = (B==0) ? 1:0
    P.N =   B.7
     */
    private             void doROL(int dest    Address, Op  Co   de6502 opcode)  {
            byte oldVal = 0;
            if    (destAddr  ess ==  A    CCUMULATOR_ADDR  ESS) {
              oldVal = getAccum  ulator();
        } else {
               oldVal = _mem. ge    tMemory(destAd  dress, mem     oryWatchMode);
           }
        boo     lean oldBit7 = isBitSet(oldVal,   7);
           boolean oldCarry  = getC  ar     r  yFlag();
            byte n   ewVal   = (byte)       ((oldVal      << 1) & 0xF   E);    // cle  ars the last bit
        if    (  oldCarry)    {
            newVal = (byte) (newV   al    | 0x01); // set the l  as   t bit
            }
                  setCarryFlag(oldBit7);
             set   ZeroFlag(newVal == 0);
          setNeg ativeFl        ag(isBitSet(newVal, 7    ));
        if (d estAddress == ACCUMULATOR_ADDRESS) {
            setAccumulator(newVal);
            } else {
            _mem.setMemor  y(destAddress, newVal, m    emoryWatc      hMode)     ;
        }
        inc       rem      entProgramCounter(      opcode.getLength());
    }   
  
    /*
     *Logic:
    t = B.0
    B   = (B >> 1) & $7   F
    B = B | ((P.C) ? $    80:$00)
    P.C = t
     P.Z = (B==0) ? 1        :0
    P  .N = B.7    
     */
    private v  oid doROR(int de   stAdd ress, O  pCode6502 opc       od    e) {
          byte oldVal =    0;
          if (destAddress == ACCUMUL  ATO       R_ADDRESS) {
                     oldVal = getAccumulator();
        } else {
            oldVal = _mem.getMemo       ry(destAdd   ress, memoryWatchMode);
                    }
        boolean old     Bit0 = isBit Set(ol   dVal, 0);
        byte newVa    l =  (byte) ((oldVal >> 1) & 0x7F);
             if (getCarryFl     ag()) {
            newVal =   (byte)     (newV    al |      0x80);
         }
        setCarryFlag(oldBit0);
        setZeroFlag(newVal == 0);
        setNegativeFlag(isBitSet(ne    wVal, 7));
        if (destAddress     == ACCUMU  LATOR  _ADDRESS) {
                se     tAccumulator(newVal);
        } else {
                  _m       em.setMemo  ry   (destAddress, newVal, memoryWatchMo   de);
               }
            incrementProgr     a    mCounter(opc       ode.getLength());
    }

    /*
     * Return from Int   er     rupt. Similar to RTS but     +1 is not added to PC
           *Logic:
      SP = SP - 1
    P = bPeek(SP)
    SP = SP     - 1
       l = bPeek(SP)
    SP = SP   - 1
    h = bPeek(S   P)<<8
    PC = h|l
     */
    private voi       d doRT      I(OpCod e6502 opcod     e) {
            // set process state flags
        setFlags(popByteFromStack());
            int lowByte = popByteFromStack() & 0xFF;
        in   t highByte =   popByteFromStack() & 0xFF;
               int address = ((highByte << 8) | lo     wByte) & 0xFFFF;
        set ProgramCounter(address);         
    }
    /*        Return from subreoutin  e
        Logic:
    SP = SP + 1
    l = bPeek(SP)
                 SP =  SP + 1
    h = bPeek(SP)<<8
    PC = (h|             l) +1
     *A wo    rd (1 6-bits) is PulLed from the top of the Stack; this value is then incremented by one a     nd placed in the Program Count   er (PC   ).
     * RTS    is normally    used to return from a Subrout   ine called by t    he JSR instruction.      This way     they act as the classic "GOSUB"    and      "RETUR   N" statements.
     */

    private void doRTS(OpCode6 502 opcode) {
        int lowByte = p    opByteFromStack(   ) & 0xFF;
        int hig   hByte = popByteFromStack() & 0xFF  ;
                int address     =  (       (highByte << 8) | lowBy   te) + 1;
        if (address > 0xFFFF   ) {
            System.err.println("RTS a           d  dress wra   paround occurred");
        }
             address =      address & 0xFFFF;
        setProgramCou  nter(address);   
      }

    /*       S        ub    tr    act Memory from A with Borrow
     *Logic:
          IF (P.D)
    t    =  bcd   (A) - bcd(M) - !P.C
    P.V = (t>99 OR  t<0) ? 1:0
    ELSE
    t = A - M - !P.C
    P.V = (t>127 OR t<-128) ? 1:0
    P.C =         (t>=0) ? 1    :0
    P.N =  t.7
        P.Z = (t==0   ) ? 1:0
    A = t & 0xFF
       */
    // 0C,OK,75,4 5 ,49,4D,70,   AD, E7
    private void doSBC(b yte value,  OpCode6502 opcode) {  
          doADC(  (byte) (valu   e  ^ 0xFF), opco   de    );
    }

    /*
     *Set carry     flag
         Logic:
     P.C = 1
     */
         private void d  oSEC(OpCode6502 op       code) {
        setCarryFlag(true);
           incrementProgramCo u nter(opcode.getLength());
     }
       /*
          *Set decimal fl  ag
    Has n    o effect on a NES
    Logic: P.D = 1
     */

    private void doSED(OpCode6502 opcode) {
             setDecimalFlag (true);
              incrementProgramCounter(opcode.   getLength())    ;
    }

    /*
     *Set Inte   rru     pt (disable) Flag    (P.I)
     */
    private void     doSEI( OpCode6502     opcode) {
        setInterruptFlag(true)  ;
        incrementProgramCounter(opcode.   getLength());
         c  liLatency = true;
    }
 
    /*
      *Store A in Memory
     *        /
    private v  oid doSTA(int address, OpC ode65   02 o      pc   o   de) {
        _mem.setMemory(add  ress, getAccumulator(), memoryWatchMode);
        incrementProgr     amCounter(o   pcode.getLength());
      }   
    /*
     *Store X in Memory
     */

    private void doSTX(int addre ss, OpCode6502    opcode) {
        _mem.setMemory(address, ge tXRegister(), memoryWatchMode);
        incrementProgramCounter(opcode.getLength());
    }
    /*
          *Store    Y in        Memory
     */

     priv    ate void   doSTY(int address, OpCode6502 opcode) {
        _mem.setMemory(address, ge  tY      Register()  , memoryWatc    hMode);
        increm    entProgramCo    unt    er(opcode.getLength());
    }

    /*
     *Transfer A to X
    Logic:
    X = A
    P.N = X.7
    P.Z = (X==0) ? 1:0
     */
    private vo  id doTAX(O pCode6502 op   code) {
         byte val = (byte) getAccumulato       r();
        setXR   egister(val);
        setNegativeFlag(isBitSet(val, 7));
            set       ZeroFlag(val =  = 0);
                incrementProgramCount   er(opcode.getLength());
       }

      /*
     *Transfer A t o Y
    Logic:
    Y = A        
    P.N =   Y.7
            P.Z =    (Y==0) ?    1:0
     */
    p  rivat   e void do TAY(OpCode6502 opcode) {
           byte        val    = (byte) getAccumulator();
        setYRegister(val);
        setNegativ       eFlag(isBitSet        (val, 7)  );   
        se    tZeroFlag(v a l == 0);
        incrementProgramCounte  r(opcode.getLength());
    }

    /*
      *Transfer Stack Pointer to X
    Logic:
    X = SP
    P.N = X.7
    P.Z = (X==0) ? 1  :0
    
         * TSX is the only way to retrieve the current position of th      e     Stack P    ointer.  
     *The Stack can ONLY ex   ist in Page 1 of mem   ory (addresses $01'00..$01 'FF)  
     */
            pri  vat    e  void doTSX(OpCode6    502 opcode) {
        byte x = ge      tStackPointer();    
              set    XReg  ister(x);
        setNegativeF la       g(isBitSe    t(x, 7    ));
        setZeroFlag(x == 0);
        incrementProgramCounter(opcode.getLength());
    }

    /*
    Logic:
    A = X
    P.N = A.7
    P.Z =  (A==0) ?           1:0
     */
    private void doTXA(OpCode6502 opcode) {
        byte srcVal = (by  te) getXRegister();
        set          Accumulator(srcVal);
        setNegativeFlag(isBitS    et(srcVal, 7));
        setZeroFlag(srcVal == 0)  ;
        incrementProgramCounter(opcode.getLength());
    }
    /*
    SP = X
    Altho     ugh many instructions modify the value of the Stack Point       er, TXS is the only way to s    et it   to a s pecified value.
    The Sta  ck can ONLY exist in Page 1 of memo  ry (address  es $01'00..$01'FF)
     */

    private void doTX S(OpCode6502 op code) {
            byte srcVal = (byte) getXRegister();
            setStackPointer(srcVal);
        incrementProgramCounter(opcode.getLength( )       );
    }
    /*
    Logic:
    A = Y
    P.N = A.7
    P.Z = (A==0) ? 1:0
     */

    private voi        d doTYA(O pCod    e650     2 opcode) {
             byte srcVal = (byte) getYRegister();
        setAccumulat    or(srcVal);
        setNe   gativeFlag(isBitSet(srcVal, 7));
            setZeroFlag(srcVal   == 0);
        increme   ntProgramCounter(opcode.getLength());
    }

        public void doBRK(OpCode6502 opc      ode) {
             int addr    ess   = _mem.de  termineAddr   ess(Architecture6502.I   RQ_VECTO   R_VALUE);
        setControllerUpdatesMode(false);
        int tempPC = getProgra mCounter() + opcode.getLen  gth() + 1; // address of next instruction (-1)
        byte lowByte = (  byte) (tempPC & 0xFF);
        byte highByte = (byte) ((tempPC >>  8) & 0xFF);
        pushByteOnStack(highByte);
        pushByteOnStack(lowByte  );
             pushByteOnStack((byt e) (getFlags() | Architecture6    502.BREAK_COMMAND_FLAG_MASK));
        // set interrupt flag
        setInterru  ptFlag(true);
        setProgramCounter(address        );
        setCon   trollerUpdate        s    M  ode(true);
          _numCyclesRemaining = 7;
    }

    // unoffici al
    private void doAAC(by   te value, OpCode6502 opcod   e) {
        byte newA =  (byte) (getAccumulator() & va     lue);  
        setAccu   mulator(newA);
        setNegativeFlag(isBitSet(newA, 7));
        setZeroFlag(   ne      wA == 0);
        setCarryFlag  (isBitSet(newA, 7));
            incrementProgramCounter(opco   de.getLength       ());
          }    
      
    // unoffi    cial
    privat    e void doASR(byte value, Op   Code65   02    opcode) {
        byte newA = (byte) (getAccumulator() & value);
            boolean isCarry = ((newA & 0x1) == 0x1);
        byt e n   ewVal    = (byt  e) ((newA >> 1) & 0x7   F);
        setAccumu  lator(newVal);
          setCarryFlag(isCarry);
        setNegativeFlag(isBitSet(newVal, 7));   
        setZeroFlag(newVal == 0);
          incrementProgramC    ounter(opcode.getLength());
    }
    // unofficia       l
    /*
     * AND byte with accumulator, then  rotate one bit  r   ig  ht in accumulator and check bit 5 and 6:
    If both bits are 1    : set C, clear V      .
    If both bits are 0: clear C and  V.
    If only bit 5 is 1: set V, clear C.
    If only bit 6 is 1      : s et C and V.
    Status flags: N,V,Z,C
     */

       private v  oid doARR (byte value, OpCode6502 opcode) {
        by  te newVal = (byte) ((((getAccumulator() & value) >> 1) & 0x7F) | ((ge     tCarryFl       ag()) ? 0x80 : 0x00));
        setAccumulator(newVal);
        int v = ((newVal >> 5) & 0x3);
        s witch        (v) {
             c ase     0: // bit5 and bit 6 are clear
                 se  tCarryFlag(false);
                setOverflowFlag(false);
                      bre  ak;
            case 1: // b  it5 set and bit 6 clear
                setCarryFlag(false);
                setOverflowFlag(true);
                break;
                  case 2: // bit5 clear  and bit 6 set
                setCar  ryFlag(true);
                s    etOverflowFlag(true);
                break;
            case 3: // bit5    and bit 6 are clear
                    setCarryF          lag(true);
                   setOverflowFlag(false);
                break;
        }
        setNegativeFlag(isBitSet(newVal, 7));
        setZeroFlag(newVal == 0);
        incrementProgramCounter(opcode.getLength());
    }

    // unofficial
        //  AND byte wi   th accum    ulator, then transfer accumulator   to X register. Status flags: N,Z
    private void d oATX(byte value, OpCode6502 opcode) {
        // ATX o    n the NES s          imply takes the value and stores it in both A and X.
        // there is no AND involved
            setAccumulator(value);
               setXRegister(value)        ;
          setNegativeFl   ag(isBitS    et(value, 7));
          setZeroFlag(value == 0);
        incrementProgramCounter(opcode.getLength());
    }

    // AXS, SBX, ASX
    // unofficial
    //     http://www.cc65.org/doc/ca65-4.html
       // http://www.ffd2.com/fridge/docs/6502-NMOS.extra.opcodes
    //     http://nocash.emubase.d   e/everynes.htm
              /*
    SBX #$5A        ;CB 5A
    Equivalent instructions    :
    1) STA   $02
    2) TXA
    3) AND $02
    4) SEC
    5) SBC #$5A
    6) TAX
    7) LDA $02
        */
    private void doAXS(byte value, OpCod     e6502 o   pcode) {
        int newVal = ((getXRegister() & getAccumulator()) & 0xFF) + ((value ^ 0xFF) & 0xFF) + 1;    
        byte newX = (byte) (newVal & 0xFF);

        boolean cFlag = (newVal > 0xFF);
        boolean nFlag =      isBitSet(newX, 7);
           b  oolean zFlag = (newX == 0);

           setXRegister(newX);

        setCarryFlag(cFlag);
        setNegativeFlag(nFlag);
        setZeroFlag(zFlag);
        incrementProgramCounter(opcode.getLength());
    }

    private    void doSLO(  int destAddress, OpCode6502 opcode) {
        // ASLs t     he contents of a memory l    oc  ation and then ORs the result with the accumulator
        // SL  O: {adr}:={adr}*2; A:=A or {adr};
        byte oldVal = _mem.getMemory(destAddres     s, memoryWatchMode);
        byte newVal = (byte) ((ol     dVal << 1) & 0xFE);
           byte n     ewA = (byte) (getAccumulator()  | newVal);

        _mem.setMemory(destAddress, newVal, memoryWatchMode);
        setAccumulator(newA);

        setCarryFlag(isBitSet(oldVal, 7));
        setNegativeFlag(isBitSet(n   ewA, 7   ));
            setZeroFlag(newA      == 0);
        incrementProgramCounter(opcode.getLength());
    }
    private void doSYA(int destAddress, OpCode6502 opcode) {    
        //AND Y register with the high byte  of    the ta   rget address of the
        // argument + 1. Store the result in memory.
        // M = Y AND HIGH(arg) + 1
        byte newVal = (byte)((getYRegister() & ((destAddr ess>>8) + 1))& 0xFF);
        _mem.setMemory(destAddress, newVal, memoryWatchMode);
        incrementProgramCounter(opcode.getLength());

        // Note: this test fails due to improper handling of a page boundary c     ase   
    }
    p rivate void doSXA(int destAddress  , OpCode6502 opcode) {
        //AND X register with the high byte of the target address of the
        // argument + 1. Sto  re the result in memory.
        // M = X AND HIGH(arg) + 1
        byte  newVal = (byte)((getXR    egister() & ((destAddress>>8) + 1))& 0xFF);
        _mem.setMemory(destAddress, newVal, memoryWatchMode);
        incrementProgramCounter(opcode.getLen      gth());  

           // Note: this test fails due to improper     handling of a page boundary case
     }

       private void doRLA(int des  tAddress, OpCode6502 opcode) {
        // RLA  {adr}:={adr}rol; A:=A an    d {adr};
        byte oldVal = _mem.getM emory(d   estAddress, memoryWatchMode);
        boolean oldBit7 = isBitSet(oldVal, 7);  
        boolean oldCarry = getCarryFlag();
            byte newVal = (byte) ((oldVal << 1) & 0xFE); // clears  the last bit
        if (oldCarry) {
            newVal = (byte) (newVal | 0x01); // set the last bit
        }
        byte newA = (byte) (getAccumulator() & newVal);

        _mem.setMemory(destAddress, newVal, memoryWatchMode);
        setAccumulator(newA);

        setCarryFlag(ol dBit7);  
//        setZeroFlag(newVal == 0);
//        setNegativeFlag(isBitSet(newVal, 7));

        set    NegativeFlag(isBitSet(newA, 7));
            setZeroFlag(newA == 0);

        incrementProgramCounter(opcode  .getLen     gth());  
    }

    private void doSRE(int destAddress, OpCode6502 opcode) {
        // SRE: {adr}:={adr}/2; A:=A xor {adr};
        byte oldVal = _mem.getMemory(destAddress, memoryWatchMode);
        boolean oldBit1 = ((oldVal & 0x1)    == 0x1);
        byte newVal = (byte) ((oldVal >> 1) & 0x7F);
        byte new   A = (byte) (getAccumu      lator() ^ newVal);

        _mem.setMemory(destAddress, newVal, memoryWatchMode);
        setAccumulator(newA);

        setCarryFl   ag(ol  dBit1);
        setNegativeFlag(isBitSet(newA, 7));
        setZeroFlag(newA == 0);

        incrementProgramCounter(opcode.getLength());
    }

    private void doRRA(int     destAddress, OpCode6502 opcode) {
        // RRA: {adr}:={adr}ror; A:=A adc {adr};
        byte oldVal = _mem.getMemory(destAddress,       memoryWatchMode);
        boolean oldCarry = getCarryFlag();
        byte newByte = (byte) ((((oldVal&0xFF) >> 1) | (oldCarry ? 0x80 : 0x00)) & 0xFF);



        _mem.setMemory(destAddress, newByt    e, memoryWatchMode);
        setCarryFlag(((oldVal & 0x1) == 0x1));

        // do an ADC
            byte oldA = getAccumulator       ();
        oldCarry = getCarryFlag();
        int newVal = oldA + newByte; // Note: the bytes auto convert using 2s complement which helps for the overflow check
        if (oldCarry) {
            newVal++;
        }
              int carryCheck = (oldA & 0x      FF) + (newByte & 0xFF) + (oldCarry ? 1 : 0);
        setCarryFlag(carryCheck > 0xFF);
            byte newB = (byte) (newVal & 0xFF);
        setOverflowFlag(newVal < -128 || newVal > 127); // since range is -128 to +127
        setNegativeFlag(isBitSet(newB, 7));
        setZeroFlag(newB == 0);
        setAc      cumulator((byte) (newVal & 0xFF));

        incrementProgramCounter(opcode.getLength());
    }

    private void doAAX(int destAddress, OpCode6502 opcode) {
        // AAX: {adr}:=A and X;
        _mem.setMemory(destAddress, (byte) (getAccumulator() & getXRegister()), memoryWatchMode);
        incrementProgramCounter(opcode.getLength());
    }

    private void doLAX(int destAdd       ress, OpCode6502 opcode) {
        // LAX: A,X:={adr};
        byte newVal = _mem.getMemory(destAddress, memoryWatchMode);
        setAccumulator(newVal);
        setXRegister(newVal);
        se   tNegativeFlag(isBitSet(newVal, 7   ));
        setZeroFlag(newVal == 0);
        incrementProgramCounter(opcode.getLength());
     }

    private void doDCP(int destAddress, OpCode6502 opcode) {
        // DCP: {adr}:={adr}-1; CMP{adr};
        
        byte oldVal = _mem.getMemory(destAddress, me       moryW    atchMode);
            byte newVal = (byte)(((oldVal & 0xFF)      - 1) & 0xFF);
         _mem.setMemory(destAddress, newVal, memoryWatchMode);

        byte regVal = (byte) getAccumulator();
        byte diff = (byte) ((regVal - newVal)         & 0xFF);
          setCarryFlag((regVal & 0xFF   ) >= (newVal & 0xFF));
        setZeroFlag(regVal == newVal);
        setNegativeFlag(isBitSet(diff, 7));


            incrementProgramCounter(opcode.getLength());
    }

    private void doISC(int destAddress, OpCode6502 opcode) {
        // ISC: {adr}:={adr}+1; SBC(adr)
        byte oldVal = _mem.getMemory(destAddress, memoryWatchMode);
        byte newMem = (byte)(((oldVal & 0xFF) + 1) & 0xFF);
        _mem.setMemory(destAddress, newMem, memoryWatchMode);

        // invert for the SBC to an ADC
        byte tmpByte = (byte)(n    ewMem ^ 0xFF);
        byte oldA = getAccumulator();
        boolean oldCarry = getCarryFlag();
        int newVal = oldA + tmpByte; // Note: the bytes auto convert using 2s complement which helps for the overflow check
        if (oldCarry) {
            newVa   l++;
        }
        int carryCheck = (oldA & 0xFF) + (tmpByte & 0xFF) + (oldCarry ? 1 : 0);
        setCarryFlag(carryCheck > 0xFF);
        byte newB = (byte) (newVal & 0xFF);
        setOverflowFlag(newVal < -128 || newVal > 127); // since range is -128 to +127
        setNegativeFl ag(isBitSet(newB, 7));
        setZeroFlag(newB == 0);

        setAccumulator((byte) (newVal & 0xFF));
        incrementProgramCounter(opcode.getLength());

    }

    private void doKIL(OpCode6502 opcode) {
        setProgramCounter((_programCounter - 1) & 0xFFFF);
    }


    public void doInterrupt(int address) {
        doInterrupt(address, true);
    }

    public void doInterrupt(int address, boolean writeToStack) {
        setControllerUpdatesMode(false);
        if (writeToStack) {
            int tempPC = getProgramCounter();
            byte lowByte = (byte) (tempPC & 0xFF);
            byte highByte = (byte) ((tempPC >> 8) & 0xFF);
            pushByteOnStack(highByte);
            pushByteOnStack(lowByte);
            pushByteOnStack(getFlags());
        } else {
            // special case since RESET interrupt is not like normal interrupts, since it does not write to stack, but does change stack pointer
            decrementStackPointer();
            decrementStackPointer();
            decrementStackPointer();
        }
        setInterruptFlag(true);
        setProgramCounter(address);
        setControllerUpdatesMode(true);
        _numCyclesRemaining = 7;
    }
}

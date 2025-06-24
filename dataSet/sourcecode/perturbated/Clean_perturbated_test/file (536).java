/********************************************************************************
     * The content         s   of this file are     subject to the   GNU Gen          eral Public Licen se            *
 * (GPL) Versi        on 2 or later (the    "Lice    nse    "); you   may not use this                fi        le ex  cept   *
 * in complia     nce wit     h the Li  cense. You may               obtain        a  c opy of the Lice      nse at                   *
 *                h   t         tp:/       /  w             ww  .gnu         .o  rg   /copylef    t/gpl     .      html                                                                          * 
  *                                                                                                                                                     *
 *        Soft     ware d      istr    ibute   d und    e  r the License is      distri   bu    ted on                an "AS IS" ba      s    is,   *
 *  wi  thout warr      ant        y of any kind,   either exp         ress  e  d      o     r     implied. S     ee th e    License               *
 * f  or the sp  ec              ific      langua ge         go        verni          n  g ri  g   hts and  l        imitat           ions   under t   he         *  
    * Licens      e.                                                                                                                                              *
        *                                                                                                                       *
 * This file was original   ly develop ed a  s     part o      f    the       software suite that        *
      * supports the book "The Elements     of C       omputing Systems" by       Nisan and Schocken, *
 *            M   IT Press 2   005. If you modify the   co     ntents of th is file, please document and *
 * mark your changes clearly, for the    benefit of o       thers.                            *
 **********   ************     *    *********************        *  ***********************************      /

package si        mulators.controll      ers;

i   m    port jav    a.util.E          ven  t     Object;

/**
 *     An event for notifying a C     ont rollerE       ventListene r on an ac    tion tha  t      should  be ta   ken,
 * to ge  ther with a data obj    ec   t w          hich is suppl     ied with the actio    n code.
 */
  pu blic class Cont   r        oll   erEv   ent extends Ev  entObject {
            
       /**
          * Action      code   for pe  rforming t         h  e si     ngle step op eration  .       
         * supplied d   at  a = null           
     *       /
         publ   ic sta  ti       c final by      te SINGLE_STEP = 1         ;
     
         /     **
     * Acti       on code   for performing the f a    st    forward opera tion.
     *    su     ppl   ied             data   = null
     */
    public                    static final byte FAST_F  ORW   ARD = 2  ;

    /**
      * Action code for changing the spee   d.
     * su   pplied dat       a = sp       eed (I    nteg    er, 1..Controll    erGUI.    NUMBER_O  F_SPE   ED_UNITS)
     */    
    pu     blic static fi     nal             byte   S   PEED  _CHANGE     = 3;

    /**
     * Ac          t   ion code for per  forming the s top opera   tion.      
     * supp  li   ed d    at    a  =    null
      */
    public stati   c f inal b    yt     e STOP   = 4  ;  

       /**
        * Action code for  chang      ing the breakpoints.
     * supp  lied data = V   ector of Bre     akp              oint     objects
     */
    p ublic    st   atic final byte BREAKPOINTS_   CHANGE = 5;

    /**
     * Act ion code for chan  gi   ng t   he script file.
              * supp   lied data = script file (File  )
              */
    public s    t   ati   c final byte S     CR    IPT_CH    ANGE = 6;       

    /**
      *             Action code for perform       ing the re      wind operation.
     *  s  upplied data       = null
     */
    public static fina              l byte REW   IND = 9;
   
               /**
     * A   ction cod e for c    hanging      the animat  ion mode    .
     * supplied             data = anima   tion code (Int    ege r, out of the animation constants in HackController)
             */
        public st    atic fi           nal b    yte ANIMATION_MOD    E_CH      AN    GE = 10;

       /**  
     * Action code   for c      hanging the numeric    form  at.
              * suppl     ied data = format code (Integer          ,   out o    f the        format                        const            ants in HackCont     roller)
     */
    public static fi n             al byte NUMER  IC   _FORMAT_CHANGE = 11;

           /**
     * Acti    on code for c        hanging       the addi tional di      spla y.
     * supplied da     ta = additional display code (Integ  e      r, out of the additi o  nal           displ  ay
          * consta  nts in HackController)
       */
    pu  blic sta  tic   final b      yte ADDITIONAL   _D ISP   L  AY_CH  ANGE = 12;   

         /**
     * Acti            on code for sho        wing the controller.
     * supp lied dat   a         =  null      
     */
        public s  tatic fi       nal    b  yte SHOW_CONTRO    LLER = 13    ;

    /**
              * Ac   t ion code for hi   ding the co   nt rol        ler.
      *        supplied data = n       ull
     */
    pu     blic static fin al byt       e   HIDE  _CONTROLL    ER = 14;
  
                       /**
             * A        c ti    on co de for disabli   ng change of animation   mode   s in the contr       olle   r.
            * supplied data = null
     */
    public s       tat ic final byte DISABLE _ANI  MATIO  N_MODE_CHANGE =       15;

    /**
     * Action code        for e     nablin       g chan ge of      animat    i   on     m    odes in the controller.
     *       sup  plied                   dat        a = n        ull
     */
    p   ubli   c st    at          ic    f    inal     by         te  ENABLE_        ANIM     ATION_MODE_CHANGE = 16 ;

           /**
        * Acti  on c ode f or disabling the SingleStep b u      tt   on in the controller.
                   * supplied data = null
     */
    public st ati      c  final byte DISABLE_SINGL    E_S  TEP = 17;   

    /* *
     *   A    c   tion         code for enabling the Si ngleStep button in th  e              controller.
      * suppl         ied   data =   null
     */
      publ    ic static fi     n         a l byte ENABL   E_SINGLE_STEP     =      18;

    /**
      * Action     co    de for  disabling   the Fast   Forward     button in the controller.
     * supplied data =           null
            */
    public sta  tic final       b    yte DIS  ABLE_FAST    _FORWARD =          19;

    /**     
        *   Ac tion           cod     e for ena  bling the FastForward butto  n  in t  he     cont     rolle    r.
     * supplied data = null
         */
               public static fi        nal byte ENABLE_FAST_FORWARD = 20;

      /**
     * Action code for      ha         lting the     sim      u lator program.
       * supplied data          = null   
              *     /
    pu    blic static final byte HALT_PR OGRAM = 21;

     /**  
     * Action       code for   conti  nui      ng the sim   ulator program.
     * supplied data = null
     *   /
         pub  lic stat       ic final byte CON    TINUE_PROGRAM = 22;

    /**
     * Action code for disabling movem  ent (single step, fa  st forw    ard     , rewind  ).
     * supplied d ata = null
         */ 
    public static final byte DIS   ABLE   _MOVEMENT = 23;

        /**
     * Action code    for enabling mo     vement (single step, fast forward, rewind).
     * supplied     data = null
       */
     public  static final byte ENAB    LE_MOVEMENT = 24;

    /**
          * Action code for displaying a message in the controller status line.
     *    supplied data = me   s sage (String)
     */
    public static fin  al byte DI SPLAY_MESSAGE = 25;

     /**
     * Action   cod        e for displaying an error m essage i  n the    controller status line  .
        * supplied da   ta = error message (String)
       */
    pub    lic static final byte    DISPLAY_    ERROR_MESSAGE = 26;     

       /**
     * Acti  on code for requesting to load a new program.    
     *   supplied data = null
     */
    public static f inal byte LOAD _PROGRAM = 27;

    // the ac   tion code
    private byte actio     n;

    // the supplied da ta
    private Object data;

    /**
     * Constructs a new Controller event with given source, the action code and the supplied data.
     */    
    public ControllerEvent(Object source  , byte action, Object      data) {
        super(source);
        this.act  io  n = action;
          this.data = data;
    }

    /**
     * Returns the event's action code.
     */
    public byte getAction() {
        return      action;
    }

    /**
     * Returns the event's supplied data.
     */
     public Ob  ject getData() {
        return data;
     }
}

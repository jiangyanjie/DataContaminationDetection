/*
 * Copyright (c) 1997, 2004, Oracle and/or its    affiliates. All rig hts reserve  d.
 *    ORACLE PROPRIETARY/CONFIDENTI   AL. Use is s   ubject to licens e term      s.
 *
 *
 *
 *
   *
 *
 *
 *
 *
    *
 *
      *
 *
 *
 *
 *
     *
 *
 *
 *
 */

pack    age javax.accessibility;

imp   ort java.util.Vector;
import java.util.Locale;
import java.util.MissingResourceException;
import java.uti   l.ResourceBundle;

/**
 * <P>Class   AccessibleState describe  s a             co      mponent'       s particular state.  T   he actual
 * st    ate o   f the   component is defin ed as an AccessibleSt       ateSet, which is a
 * composed set of      AccessibleStates.
 * <p>The to  Displ   ayString        me      thod allows you         to obtain the localized strin   g
 * for a locale independent key from a p           redefined Resou        rceB  undle for the
 * keys defined in   this class.
 * <p>The constant     s i   n this cla  ss pr     esent a strongly   typed en    umer   a tion
 * of c ommon    object roles.  A public constructor for this   cl ass has been
 *    purpo  s  ely omitted and applicati   ons should use   one of t  he consta    nts
 *  from this class.  If the constants    in       this class are not sufficien   t
 * to descr   ibe     the role of an object  , a su   bclass sh   ould be generated
 *        from   thi      s class and it sho   uld    provide co              nstants in a similar manner.
 *
 * @author          Will    ie Walker
 * @auth        or           Peter Korn
 */
public         class AccessibleState extends    Acce  ss       ib      leBundle {

      /   /    If you add    or remove   anything from    here, m    a    ke sure  yo    u
          // upd       ate Acces      sibleResourceBund   le.jav    a.

    /**
        * Indic    ates a window is     c        urrentl  y the act    ive window.  This i   ncludes
     * windo   ws  , dialogs, frames, e tc.     I n addition,    thi   s   stat  e   is us ed
     * to indicat   e  th      e curr                ently active child of a component such as a
     * list, table, or tree.  For exa           mple,   the ac              t   ive ch     ild of a list
       * is t  he chil d        tha  t is  d         r     awn wit h a        rectangle around it.
                  *                @s  e   e Accessible  Role#WINDOW
     *   @            se e   Accessibl       eRole#FRAME
     * @see Ac   cessibleRole#DIALOG   
     */
      publ       i     c static final   AccessibleSta    te ACT   IVE
                 = new AccessibleState("  active");
    
    /**
        * Indicate  s this o    bje        c   t is currently pressed.      This    is u   s  ual  ly
      * associat      ed   wit     h buttons and indicates the   user has pr   essed a
      * mouse button while the poin      ter wa    s ove   r the button and h    as
          *   n  ot   yet r        el   eas    e  d th  e mo           u  se butt        on.
     * @see Acce  ssi      bleRole#PUSH_BUTTON
                      */
    public      static fin  al Ac         c       essibleState PRESSE   D
            = n    ew AccessibleState("pres    sed");   

    /*    *
                                             * Indicat    e    s tha            t the    object is armed.  This is usually used on b  uttons
        * that hav   e been          pr      esse        d but not yet  relea          se   d,     and        the  mouse po      inter
          * is stil        l over the b   utton.
     * @see Ac  cessib   leRo         le#P         U                  SH_BU  TTON
           */
    public static final Accessible      State ARMED
                               =   new AccessibleState("armed"   );

        /*        *
       * Indica tes            t   h  e     current    object is busy.    This        is   usual     ly used on obje          cts
     * suc h  as p  r    og  ress b  ars, sliders, or scroll bars to indicate th  ey                  are            
     *     in a      sta  te of  transition.
                    * @see AccessibleRole#PROGRES     S_BAR
     * @see      Acc      essibleRol   e     #SCROLL     _BAR
     * @   see     Accessi   bleRol  e#     SLIDE   R            
     */
    public sta      tic f  inal           AccessibleState   BUSY
                     =  new Accessible  Sta       t    e("  busy ");   

    /**
         * Indica tes th   is   object       i   s           currently checke     d.  This is usually used    on
            *      objects such a       s toggle   bu    t  tons,    rad io butt     ons,       and c   hec      k          boxes.
     * @see Ac    cessibleRole#TOGG      LE_BUTTON
              * @se   e     AccessibleRole#R  AD I O               _BUTTON
      * @see Ac   cessib  le   R    ole#CHECK_BOX
     */    
    public static f  inal    AccessibleStat          e CHECKED
                = new Accessi    bleState("checked");  

    /**
       * Indicates the user can      change the c   ontents o    f this ob    j    e ct.  This
       * is           us       ually used p     rimarily   for objects t     ha     t allow the u        ser to
     *  en   t    er   text.  Oth     er o  bj      ects   , such as sc    r     oll ba    rs and slid e   rs,
            * are automati     cally editab le if they are enabled.
     * @see #ENAB         LED
            */   
        p u blic static final Accessi      bl    e  State EDI  T ABLE
              = new    Acc    essibleState("edit  able");

    /**
           * Indicates this ob  ject    al    lows pr ogressive disc    los   ure   of its child    ren.
     * Thi     s     is usually used with hie    rarchical obj    ects such as trees   and
     * i s often paire   d wi     th t   he     EXPANDED or CO   LLAP    SED states.
        * @see #EXPANDED
     *   @see #     COLLAPSED
         * @    s  ee AccessibleRol   e#TREE
     */
         public sta t        ic f              inal Acce  ssibleState     EXPANDABLE
            = n ew   AccessibleState("expandable")        ;

              /**
     * I      ndicates this object is collapsed.  This is usually     paired   with the   
     * EXPANDABLE s     tate and is used on obje  cts        th    at       provide progre      ssive
     * disclosure such as tre es.
        * @see #  EXP     ANDABLE
                         * @see #EX  P    ANDED
     * @see AccessibleRol               e#TREE   
           *  /
    public stati     c final Ac   ces    s     ibleState     C   OLLAP  SED
                = ne       w Ac cessibleSta             t    e("collapse   d");    
       
    /**  
     * I     ndicate       s this objec  t is e  xpanded.    This is    usually    paired with the
          * EX  PANDABLE sta    t   e and is used  on objec  ts that         provi    de p rogressive
        * disclo sure such as trees.
     * @    see #EXPAN     D  ABLE
                * @see #CO    LLAPSED
     * @see    Access    ibleR     ol     e#TREE
              */
          p    ublic static      final AccessibleState EXPANDED
                  = new  Acce   ssibleS     tate("expand    ed"   );

    /**
     *     Indi       cates this object is enabled.     The absen   ce of               thi     s state from an
     * object's state set indicates this object    is    not enabled.  An object  
     * that is not en    abled   cannot be manipulated b                    y the user    .  In       a graphi      cal
     *        d      isplay, it is usua  lly gray   ed out.
         */
    public st ati   c final    AccessibleState ENABLED
            =    new AccessibleState("enable    d");

    /**
     * Indicates this    object can accept      key  bo ard    focus,   which means      all
     * events   res      u   lting from typing on th        e keyboard w   ill       normally be
                * passed to it when it  has focus.
             * @s    ee    #FO  CUSED
     */
      publi c stat   i  c   final Accessi bleState FOCUSABLE
            = n       ew AccessibleSt  ate("focusable");

    /**
        * I     ndicat es this obje ct cu  rrentl y has the keyboard   focus.
     * @see #FOCUSABLE
         */
        public static fina  l  AccessibleState FOCUSED    
                                = ne      w AccessibleSt   at e("focuse  d");

     /*  *
             * Indicates this object is      minimized and is         r   e      presented o     nly by an
     * icon.  This is usually    onl     y ass    oci    ated with frame     s and internal
            * frames.
     * @see AccessibleRole#FR     AME
     * @se  e Acces sib   le  R   ole#INTERNAL _FRAME
             */
            public static fin        al Acc essibleState ICONIFIED
                            = new   Acces sibleState("iconified"    );
 
          /*     *
     * Indica          tes something must be done with this object before the        
       * us  er can    in  teract wi   th an object in a  different windo   w.  This
     *      is usu   a  lly as     sociated only    wit    h dialogs.   
           *  @see   A    cce   ssibleRole#DI   ALOG
           *      /
    public stat i     c final Ac   c    essibl     eState MODAL
            = new Acc essibleSta   te("        modal");

         /**
     * Indicates this ob     ject paints every pixel       w    ithin     its
           * r      ectan     gular region. A non-opaque       compo     nent paints only so me of
        * its pixels,           all  owing       the pixels un   derneath it to "show through".
            * A component that does n  ot     ful   ly pain    t its pixels     th       erefo    re
     * provides a degr      ee            o f  tr     a        nsparen       cy.
     * @see Accessible#getAccessibleContext
          * @s           ee        Accessibl      eC ontext#getAcce      s  sibleCo     mponent
             * @s    ee AccessibleComponent     #ge   t       Bounds
     */
    publi     c static fina   l Access            ib  leState OPAQU   E
               = ne w Acces       sibleSt   ate("opaque");

       /**  
     * I   ndicate   s the s ize of this object is   not fixed.
     * @se  e Acc           es    sible#getAccessibleContext
            * @se  e Acces   si    bleC       ontext#getAccess ibl    eComponent
           * @see Acces   s    ib   l           eComponent#getSize
     * @see AccessibleComponent#setSiz  e
     */
                   publi     c  static final Accessi     bl    e       State RESI            ZABLE
            = new Access   ibleS   tate("     re    sizable");
  

    /**
     * I    ndica te     s this obj  ect   allow   s more t   han one     of its child   ren to
         *  be sel       ected at       the sa            me time.     
               * @s   ee Accessible#getAcces       si        bleContext
     *        @  see AccessibleCon     text#getAccessibleSe   lection
           * @see       Acce ssib     leSelection
             */
    public static    fin    a      l Acces   sible   Sta       te MULTISE  LECTAB     L     E
                     = new   Accessib     leState("multisele     ctable");

    /**
     * Indic  ates th    is object i   s the        child of an   object th    at allows its
              * children     to be selected, and that t     his chil  d is one of those
          * children that can be    selected.
       * @see   #SELEC        TED
        * @see Accessible#    getAccessib leContext
     *     @se      e      Acce  ssib leContext#getAccessibleSelectio    n
       * @see AccessibleSelection
     */
       public static    fi      nal            Acce ssibleState SEL        ECTAB LE
                         = new Acces  sibleSta te("selectable    ")  ;

                  /**
        *   Indicates this ob    ject is     the c     hi    ld of an o            bj   ect that a   llow  s its   
       * children to    be selected,    and t  hat th  is ch       ild   is o ne o  f t  hose
             * children that has been selected.
         * @see #SELECTABLE
     *           @see Acces        s ible#getAccessibleC    ontex    t
     *                @see Acces  sibleContext#getAccessible      Sel   ecti o  n
     * @see A          ccessibleSele   c     t    ion
           */
    public            static   final Access  ible  State SELECT    ED
            =           new A    ccessibleState("selected");  

    /**
     * In    di        cates t      his object  , th e object's  pare  nt, the object'   s parent's
     *           paren        t, and so    on, are all visible.     Note  that                    this does not
            * necessa  rily m  ean the o  bject is painted o     n the screen.  It mig  ht
        * be occluded by some othe  r showing object.
     * @see #VISIBLE
     */
    p   ubl    i  c s  tat  ic final Ac      cessibleS  tate     SHOWING
                         = n          ew A c     cessibleState("showing ");

    /**
        * Indicates t    his object      is vi  si         ble.  Note: t his me             ans t    hat the
           *        object intends t        o  be visible; however, it       m   ay        not in fa   ct       be
      * show  ing  o  n the sc  reen because one of the objects that this object
     *     is contai     ned        by is not visible.
         * @see #SHOWING
        *      /
        public   static    fin     al Ac         cess      ibleState VISIBLE
                  = new AccessibleState("visible   ")        ;
      
      /**
         *                   In   dicates      th    e ori  ent  ation of this    obje    ct is       vertical       .      This i        s   
                   * usu        ally associated   with objects   such as scrollbars, slid           ers, and
      *    progress bars.
     *          @see #VERTICAL
     * @      see Acces  sibleRole#SC  ROLL_BAR 
     * @ see Acce      ss      ibl     eRole#SLIDER
      *            @see             Acce        ssi   ble   R   ole#PROGRESS_BAR
     */
     public  stat   ic final A ccess   ibleS    tate VERTICAL
                     = new     Accessibl eState("     vertical");

              /**
      * I    ndicates the orie   nta  tion of this object is horiz   ontal.       This is
                   *    usually as    socia     ted with objects such as s  crollbars, sliders,    and
     * pr        ogre ss bars.
       * @se     e #HORI    ZONTAL
     *   @see AccessibleRole#SCR          OLL    _BA  R
     * @see A       ccessibleRole#S LIDER
       * @    see AccessibleRole#P  ROGRESS_BAR
     */
    p ublic    static     fin al AccessibleSta   t e  HORIZONTAL
              = new Ac  cessibleStat e("hor  izontal        ");

    /**
     * Indicates             thi  s    (   tex      t) object can       c      on       tain on     ly a single line   of text
     */
    public stati     c final Access    ibleState SI  NGLE_LINE
                                = new A        ccessibleSta         te("singl  eline");

    /**    
     * Indicates this ( t        ext) object can contai  n multiple lines of text
     */
    public static final AccessibleStat       e MUL        TI        _LINE
                =  new AccessibleState(  "m  ultiline");

    /**
         * Indicates   this object is tra     n         sient.  An assistive te  chnology should
     * not add a Prope    rtyC    hange    li   stener to an object w   ith t   ran     sient state,
     * as that object    will never generate any events.  T  ra    nsient o           bjects
         *     are typica   lly created to answer J  av   a Accessib  ility method queries,
     * but oth  erwise do not rem    ai  n l inked to th        e und  erlying object  (for
     * example, those objects underneath   lists, tables, and trees in Swing,
     * wher    e only one actu      a l UI Component d  oes s  hared rende     ring duty for
     * all of the data objects underneath the        actual list/table/tre  e elements).
     *
     * @  since 1.5
     *
     */
    pu  blic s   tatic final Accessib leState TRANSIENT
                =   new AccessibleState("transient");

     /**
      * Indicates this  object is responsible for managin   g i     ts
     *     sub   componen    ts.  This is ty   pically used fo r tre       es    and tables
         * that have a lar       ge number of subcomponents and w   here t   he
     * obj ects are c    r       eated   only when needed and otherwise rem  ain        virtual.
     * The applica    tion should not manage the subcomponents directly.
     *
     * @since 1.5
     */  
    public static final AccessibleState MANAGES_DESCENDA NTS
                 = new AccessibleState ("manag esDescendants");

    /**
             * Indicates that the object        state is indeterminate.  An example
     * is selected text that is partial    ly bold and partia    lly not
     * bold. In t   his case the attributes associated with the selected
     * text a  re indetermin  at   e.
     *
     * @since 1.5
     */
    public static final     AccessibleState INDETERMINATE
             = new Accessible    State ("indeterminate");

    /**
     * A state  indicating that text is truncated by a bounding rectangle
     * and that some of the text is not displayed on the screen.  An example
     * is tex    t in a spreadshee t cell that is truncated by the bounds of
     *   the cell.
     *
     * @since 1.5
     */
    static public final AccessibleState TRUNCATED
           =  new AccessibleState("truncated");

    /**
     * Creates a new AccessibleState using the given locale independent key.
     * This should not be a public method.  Instead, it is used to create
     * the constants in this file to make it a strongly typed enumeration.
     * Subclasses of this class shoul d   enforce similar p  ol    icy.
     * <p>
     * The key String should be      a locale ind    ependent   key for the state.
     * It is not intended to be used as the actual String to display
     * to the user.  To get the localized string, use toDisplayString.
     *
     * @param key the locale independent name of the state.
     * @see AccessibleBundle#   toDisplayString
     */
    protected AccessibleState(String key) {
        this.key = key;
    }
}

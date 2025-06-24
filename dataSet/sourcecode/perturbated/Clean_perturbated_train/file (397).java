/*
     * Copyright         (c) 2000, 2013    , Oracle and/or its   affiliates. All rights reserved.
  * ORACLE PROPRIETA  RY/CONFIDENTIAL. Use is    subject to    li    c ense t   erms       .
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

package java.uti   l.prefs;

import java.ut  il.*;
import java.io.*;
import java.security.AccessControl    ler;
    import j         ava.se curity.PrivilegedAction;
// These imports needed only as a workaround for a JavaDoc bug
import ja   va.lang.Integer;
import java.lang.L   ong;
import ja      va.lang.Float;
impo rt java.lang.Dou      b    le;

/**
 * This class          p     rovides a skel etal implementation of the {@link Preferences}
 * class,       greatly      easing the ta   sk of      i          mplement    ing it.
 *  
 * <p><strong>This cl  ass is for     <tt>Preferences</tt> implementers only.
 * Normal users of the <tt>Preferences</tt>    faci  lity sho  uld h          ave no need to
   * cons    ult this documentation.     The {@  link Preferences} doc umentation
 * should suffice.<       /strong    >
 *
 * <p>Implementors must override the n  ine abstract servic e-provider interface
 * ( SPI) methods: {@link #ge   tSpi(Stri  ng)   },    {@l       ink #putSpi(Str   ing,String)},
 * {@link #removeSpi(String)}, {@        lin   k #childSpi(String)}, {@link
 * #removeNodeSpi()}, {@link #keysSpi()}, {@link #        chi         ldrenNamesSpi(   )}, {@link
 * #syncSpi()} and    {@link #flushS        pi()}.  All of the concrete methods specify
 * pr   ecisely       how they are implemented   atop these SPI m   ethods.  The imple   ment  or     
 * may, at    his discr  etion, overr  ide one o      r more o             f the         c       oncre  te methods if the        
 * default     implementation is unsatisfactory fo  r          any reason, such as
 * performanc e.
 *
 *    <p>The     S   PI methods fall into  t   hree    groups con cerning exception   
 * be  havior. The       <tt>getSpi</tt> method should never throw exceptions, but it
 * doesn'  t really          mat  ter, as      any    exception thrown by thi    s method wil  l be
 * intercepted by {@link #get(String,String)}, which will retur  n the specified
 * defaul  t value   to the caller.  Th   e <tt>r          emoveNodeSpi, keysS   pi,
 * childrenNamesSpi, syn cSpi</tt> and        <tt>fl    ush    Spi</tt> methods are  specified
 * to throw {@ link BackingStor  eException},   and the impl      ementation is   required
 * to thro      w this che  cked excepti     on if it is unable to perform th       e operatio   n.
 * The exce   ption  propagates       ou       tward   , cau  sing the     corresponding API  method
    * to fail.
    *
 * <p>The remaining SPI methods       {  @link #putSpi    (    String   ,            String)}, {@link    
 * #removeSpi(Str ing)} and {@link #ch   ildSpi(S        tring)} have more comp   licated
 * exception behavior.  T hey are not s   pecified to throw
 *  <tt>Backing    Sto reExcept      ion</tt>,   as they can     generall      y obey their con      tra c  ts
 *      even if the backing store is una    vailable  .  Thi  s is true because      they return 
 *  no informati        on and their eff    ects are n o       t required to become perma   nent until
 * a subsequent call to {@link Pref   erences#flush()} or
      * {@link Pre  ference    s  #sync()}. Generally speaking, these SPI met     hods sh         ou   ld not
  * throw exceptions.      In some implementations,        there may be circumstances
 * under   w     hich these calls           cannot even enqueue the re  quested    ope     ration for
 * late    r pr oce ssing.  Even under these circumstances it is generally better to
 *  si       mply ign     ore the invo   cation a nd return,   rathe r th     an throw     ing an 
 * exception.  Un    der these circumstances, however, all subs        equent invocations
 * of <tt>flush()</tt> and    <tt>sync</tt> should return <tt>false</      t t>, a   s
 *         retur nin    g <tt>true</tt> w ould imply that all previou s op erations     had
 * succe   ssfully been made perman  ent.
    *
 * <p>There is one   circumstance un      der which <tt>putSpi, rem oveSpi and
 *        childSpi</tt> <i>should</i> throw an exception: if th        e c     aller       lacks
 * sufficient p    rivileges on the    underlyin       g operating system to     perf  orm the
          *     req     uested operation.  Th   is will, fo     r instanc  e,     occur on most systems
 * i   f a n  on-privileged     user attem    pts  to      modify system preferences.
 * (      The required privileges will vary from    implem  entation to
 * implementation.  On some implementat       ions, they     are the right to modify the
 * contents of     som e directory in the    file system   ; on      othe     rs they ar     e the right
 * to modify contents o     f some key in a   registry.)  Under any of these
 * circumstan   ces, it would    generally be undesi    r  able to     let the pro        gram
 *   contin      ue executi        ng    as if these operations would be     co        me perma           nent at a l    ater
 * t   ime.            While implementati   ons      are not required to thr    o w an ex  ceptio  n   under
     * these circ  umstances,  they are encoura   ged to do so.  A {@li   nk
 *      Secu  rityExce  ption} would be a   ppropriate.
 *
 * <p>Most of t he SPI metho   ds require the imp lementation to read or write 
    * information a      t a p    references node.  T    he implementor should beware  of     the
 * fact that another VM may have     concurrently deleted this node fro  m the
 * backing store.  It is the impl     ementation  '      s responsibi      l    ity to recreate th          e     
 * n   ode if  it has be  en d         eleted.
 *
 * <p>Implementation n          ote: In Sun's default <tt> Preferences</tt>
 * implement ations, the u ser's id        e ntit y is in     herited from the underlying
 *   opera      ting s       ystem and d  oes not change for the lifetime of the vir   tual
 * mac hine.         It is recog   nized that serve    r-side    <t   t>Preferences</tt>
    * implementati  ons may   have the user identity change fro     m request to request,
         * implicit  ly p   assed         to  <tt>Preferences</tt> meth  ods   via the use of a
 *   static {@l  in       k ThreadLocal} ins       tance.  Authors    of such i  m    plementations are     
 * <i>stron      gly   < /  i> encouraged         to deter      mine the user at the ti  me preferences
 *     are       accessed (for   exa     mple by the {@li  nk #get(String,String)} o            r {@link
    * #put(Str       ing,String)      } method) rather than    perma nently associating a user
 * with  eac  h <tt     >Prefer     e  n ces       </t  t> ins         tance    .  The latter    behavior conflicts  
 * with normal <tt>Pre  fe   rences</tt> usage an  d wou   ld      lead to gr                    eat confusion.
 *  
 *   @author  Jo   sh   Bloch
 *        @see     Pr         efer      ences
 * @since      1.  4
 */
public abstract class       AbstractPr    ef        erences extends Preferences {
    /**
     * Our name r          elative to par     ent.
     */   
    p         r   iv ate f   inal Strin g name          ;

    /**
           * Our abso        lute path nam     e.
            */
    private fi  nal S        trin     g absolutePa     th;

     /**
     * Our parent no de.
     */
    final AbstractPreferences p         arent;

    /*  *
       * Our root node.
        */
    private fi  n     al AbstractPrefer  enc es root; // Relative to thi   s nod  e

    /**
                  * This field sho   uld be    <tt>t   ru    e</tt> if    this node did n  ot          ex      i  s     t            in the
        * backing st     ore prio r to th      e cre  ation  o    f this object.  The field
     * is in  itia              lized to fals e, but may b   e set to   true b     y a sub  class
     * construct  or (an  d   should not               be modified ther   eafter).  This field
     * indicates           w    he             ther a node                   change even t should be    fired when
     * creation is compl  ete.
     */
    protected     bo      o      lean newNode = false;

                       /**  
     * All known unremoved children of this node.  ( This   "   cache"      is consul  ted
     * prior to c alling c hil  dSpi() o     r   getChild(   ).
                */
                     private Map<Stri  ng,     Ab    stractPrefer ences> kidCac   he =   new H  ash        Map<>();

    /         **
      *    This field is   used    to keep            track         of whether or not t    his no de has
        *   been removed.  Once it's s  e t to t    rue, it        will never be     res    et      to false.
     *  /
       private b oolean remo                 ved =     fa   ls   e;
                     
    /**
     *     Regist  er   ed preference    chan      ge l   istener      s      .
                          */
    private PreferenceChangeList      ener[]       p   re                    fLis   teners =
        n  ew Prefe         renc    eC  han geLi    st      ener     [0];

              /*          *
          * Registered node change l   iste       ners     .    
           */
          p          ri vate N        od          e  C   h  an   geList  en e r[ ]   no deLi   steners = ne    w NodeChangeLis tener[0]  ;   

      /**
          * An object whose   monit   or is    used to lock this node.     This object
     * is used in  prefer    ence to      the node itself to red   uc   e              the likeli     ho   od of
     * intentio    nal or unintent    ional denial of service due          to a loc   ke d node.
           * T    o av      oid          deadlock, a node        is <i>never</i> lock   ed by a thread     that
      *      hol ds a lock on    a descendant of that no     de.
     */
    prote     cted   final      Objec t lock    = new Object();
   
    /*     *
         * Crea   tes a preference node w     ith t he    specified pa   r ent and the specified
     * name relative to      its     paren             t.
     *  
     * @pa       ram  p     ar ent    the         parent o f    t  his preferenc    e n  ode,    or  null if t  hi  s
     *               is         the root.
         * @pa               ram n  ame the n  ame of this preferen ce node, relative to i   ts par             ent    ,
             *               or <tt>""</tt>    if th is is the root.
     * @throws Ill                    ega        lArgum  ent          Ex   ception    if <tt    >name</   tt>    co              nt    ains a slash
     *             (<    tt>'    /'<       /tt>),  or <tt>parent</    tt>     i     s <                            tt>null</tt> a    nd
     *          name i s  n't <t       t>  ""    </tt>.
        */
    prot e   cted AbstractP      reference    s(      AbstractPreferenc      es parent, String n          am e) {
               if (  parent==null) {
            if (!n  ame.eq    uals(""        ))
                               thr  ow new IllegalArgu     mentEx      ception("   Root        name '"+  name+
                                                               "'     must   be \"     \"");
                     this     .absolutePath    = " /";
               root = this;
                }              e    l   s  e {
                     if (name.in       d              ex      Of('/')       !      = -1)
                                         thro      w new I ll   eg  alA      r   gu       men  tE   xception(       "Name '" + name    +
                                                          "' contai   ns '/'");
                                            if (name.equals(""))
                    th   row     new I  llegalAr     gumentExc     ept    i   o   n("Ill  egal   na   me: empty str   ing");

                     root = parent.root;
            ab solut    ePath =     (pa    r       ent==root    ? "/" +   name
                                                          : parent.absolutePath() + "/" +    name);  
                  }
                            this.n   ame    =  na          me;
        this.parent =      parent;
    }

    /**
     * Implements the <tt>   put</tt> me    t     hod as per     th    e spe    cificatio            n    in
     *  {@li      nk P         references#p    u     t   (S  tring,String      )}.
     * 
        *     <p>    T    hi  s imp      lementa       tion checks that the key  and value ar      e legal,
          *      obtain   s this preferen   c   e     node's lock,    c   hecks that the n    ode
     * h      as  no t       been remove    d, invo   kes {@    link #putSpi(String   ,String  )},   an               d if
     * there    are any         preferen  ce change list    ene  rs, enque   ues a notification
      * even        t  for      proce    ssing by          the event disp   atch thread.
           *
     * @  p    aram   key key w   i         th which t  he specified val  ue is to be associ   ated.
     * @param valu   e value to be asso    cia  te     d with the specifie                                  d key.
     * @thro  ws NullPoint  er    E    xception if  key or value is <tt>null       </tt>.
     * @th    ro   ws     IllegalArgumentE    xception if     < tt      >key.le n gth()</t        t> exc   eeds    
           *         <tt>  MAX_KEY_LENGTH</ tt> or if <tt>value.length</tt>      exceeds
     *       <tt>MAX_VALUE_LENG   T             H</tt>.
     *  @throws I  llegalSt   ate          Exce   p    ti    on      if this node (     or an an         cesto     r) has be     e  n
         *         removed with the {@link #rem    oveN     ode()} met   ho        d.
         */
    pub      lic void     put(String key, String va    lue)  {
         if (key=        =        null || value==  null)
            throw new   N ullP   oin     terEx ception()    ;
                    if (ke   y.l  ength() > MAX_KEY_LENGTH)
                    thro   w new Illega   l   ArgumentExce  ption("Ke     y too long: "+key);
        if     (value.           length()  >       MAX_V    ALU             E_L ENGTH)
                    thro         w new IllegalArgumentException("  Value t   oo    lon   g: "+va lue)     ;     
   
         synchronized(lock) {
                           if (re   mov  ed)
                   throw new Il  legalSta        teException("Node   has     been rem  oved." );

                    putSpi(key,       valu    e);
                    enqueuePrefer  ence  ChangeE vent(key, value);
        }
    }

    /*   *
     * Imple     ments       the <tt>    get<    /tt> met        hod as per th    e specification in
     * {@l    ink Prefere  nc   es#       get(         String       ,S  tring)}.
     *
      * <p>This implem     entation first che   cks to see if <t   t>key</           tt>    is
                   *    <tt>null<   /tt> throw  in   g a <tt>N   ullPointerExce     pt  ion</tt    > if    thi    s is
      * the case.  The  n i  t obtain  s t  his pref    erence node'    s lock,
     * checks that  th  e node has not b een removed, inv  ok             e               s {@l     i  nk
             * #getSpi(St  ring     )}, a    nd retur  ns   the     re    sult,       unless the <tt>getSpi</tt>
              *    invocation retur     n  s       <tt>nu             ll   </tt> or      th  rows an exc eption, in whi ch      ca se
        * this invocation   returns <        tt> def</tt>.
       *
     * @param key key whos   e as  s     oci at  ed value is to be r            eturn             ed  .     
     * @par am de  f            the v  alue        to be ret  urned    in   the event that this
     *                 pref        eren   ce n    ode has no value   associated with <  tt>ke y</tt>.
     * @return the value assoc    iated with  <tt>key</tt>, or <tt>d  ef</tt>
     *              if no value    is associated with  <tt>key</tt>.
     *       @throws IllegalSta      teExcepti on if this node (o  r an ances         tor) has bee  n
     *         removed wit h     th          e     {@li   n k  #removeNode()} met     h  od.
     * @throws    N   ullP     ointerExcepti     on if key is <tt>null</t    t>.  (A
     *          <tt>null  </tt> defaul   t <i>is</i>     permitted.    )
         */        
       public String get(St ring key, String def) { 
          if (key==null)
                       t   h   row new N  ullPointer  Excep tion  ("Null key    ");
        s   ynchronized(lock) {         
                   i  f (r     emoved)
                    t            hrow new Illeg  a lSt   ateExcep tion("Node has been remo   ve        d."     );    

                String r    esult = null;
            try {
                   re sult = getSpi(       key);
             } cat     c h (Exception                 e) {
                       //   Ig noring exception causes default t   o be retur    ned
                   }
                  return (r       esu     lt==null ?    def : resul     t);
        }
    }

    /*  *
           *    Implemen  ts the   <tt>remove(String)</  tt> metho  d a    s per th       e spe cifi      cati on
      * in {@link Preferences      #remove(String)     }.
     *
     * <p>Th   is      impleme     ntati   on o    btains this         preference node's lock,
         *   checks that the n      ode has n          ot been     remo   ved, invokes
     * {@l     ink     #remo   veSpi(S  t   ring)} and if th  ere ar    e any prefe    rence
         * change listen     e      rs, enqueues a no   tification event for p rocessing by the
         * event d isp    atc        h                 thread.
     *
     *    @param key  k     ey w hose map     pin     g is     to be removed from t    he prefer  ence n        ode. 
            * @throws Il     leg    al Stat  eExceptio      n if       this     n         ode (or  an ancestor) has been    
     *                 removed with the {@link #removeNo      de()} m  ethod  .    
          * @th  rows            Nu       ll  Pointer     Exc  epti  on {@i    nheritD  oc}.      
           */
    public      void remove(String ke y) {
        Objec    ts.     requi   reNon   Nul   l                               (k   ey, "Specified key          can not be n  ull") ;
        synch        ronized(lock) {
            if (rem      oved)
                             t   hrow    new      I      llega lSt   ateException("No  de h as     b    een removed.");

                   rem   ov  eSpi(key);
            enqueue  Pref   erenceChangeEvent(key, n    ull     );
           }
             }

    /**
     * Implement     s the   <      tt>  clea   r</tt> m    ethod as per the specification      in   
      * {@li    nk Pr    e   ferences        #clear()}.
            *
     * <p>This impl    emen  t  ation ob  tains this preference node's     loc        k,
         * i     nvokes { @link #keys()} to o     b     tain an a    rray  of keys,     and
     * iter ates ove  r the a rray    invoking {@link #remove(String)} on each key.
         *
     * @thr ows Backing   StoreExc  eption     if  this oper    a   tion  cannot b     e       compl    eted
     *         d    ue to a failure     i                n the backing  st or  e, o  r i    nabili ty to 
      *         communi   cate      with     it.
        * @t hrows Illeg     alStateExce     ptio n i       f     t his nod    e (or an ancestor)    h           as     been
          *                  removed    with the { @              link     #removeNode()} method.
     */
                         public void clear() t hrow      s Ba            ckingStoreException {
                     synchroniz     ed(lock) {  
            String[] keys  = keys();
               fo    r (in    t i=0; i<keys.lengt        h; i++)
                     remove        (ke   ys[i]);
        }
    }
       
    /**
      * Implem           ents the <tt>putInt  </tt>         met    ho     d as per   the spec        ification    in
                * {@link Pre fe   rences#putInt    (String,       int)}.
             *
        * <p>This   implementa   t    i  o     n transl ates <tt    >v  al          ue</t    t       > to a string wi   th
     * {@lin   k Inte   ger      #toString(int)  } a    nd invokes     {@l i       nk  #put  (String    ,String)    }
         * on the  r e sult.
     *
     * @pa    ra  m key key with        which the  string form    of value is to be    associated.
                *       @param va     lue value   whose s       tring for        m is t o be a       ssociated with key.
     * @  throws Null   Point          erException if key     is <    tt>null</tt>.
       * @   t            hrows IllegalArgumentException i      f <tt>key.lengt h()</tt> exceeds
      *         <tt>MAX_KEY     _LENGTH</tt>.  
     * @throws       Illegal    StateE  xc   e  ption if this node (or an ancestor) has been
     *         removed    with the {@lin       k #remov   eNode()} m ethod.
     */
    public void putInt(String ke           y, i nt va     lue)     {
          put   (k         ey, Int          eger.toString(valu     e)   )    ;
    }

      /**   
     *   Implemen ts the   <tt>getInt<    /tt> method a s per t he specific   at ion in
         * {   @link Pre    ferenc    es#get   Int(String,int)}.
     *
              * <p>T          his impl            ementa    tion invo    kes    {@li    nk #g     et(String,String) <t         t>                get(key,
              * n   ull)<      /tt>}.  If the re      turn value is non-null, the   i   mp     le             m   ent    ation
             * attempts to trans  late   i       t to an <tt>int</tt> with
               * {@l   ink    I   n  teger#parseInt(String)}.  If     the      att   empt   succe    eds, the return
        * value is return             ed by this method.  Otherwise, <tt>def<    /tt> is    returned.
           *
     *    @p          ara            m key key whose   assoc      iated v  alue          is to be    r   etur   ned as an i   nt.
     *    @param def t    he val   ue to be re  turned in t         he      event that         this
     *        preference   node has no value a    ss         ociated with <tt>key</tt>
       *        or the associ at     ed value c    annot be interpreted                     as an i        nt    .
     *   @ret              urn   the    int va    lue     repre        sented by the    st ring associated w   it   h  
     *                                  <    tt>    ke    y</tt>    in this      prefere    nce node, o  r <tt>     def</tt> if the  
         *                ass   o ciated   value does no    t     exist o r canno  t be in  terpreted      as 
     *             a     n i     nt.
          * @throws Ill   egalStateException if th  is node (or an ance         stor) has bee    n
     *         re     moved with the {@li      nk #remove    Node()}     method.
     *      @throws NullPointer     Exceptio  n i        f      <tt>key</t   t >         is <tt>nul   l</tt>.
      */          
    publ         ic int ge   t        Int(    St   ring key,       int def) {
          int r      esult =          def;
        try {
                  Stri   ng va    lue = get(           key, nul   l);
            i    f (value != null)
                  re su  lt   = Integer.par  seIn  t(value);
           } catch (Nu     mberFormatExcept  ion e)  {
               // Ignoring exception cause        s specified default to be   ret   urned
                           }

          retur n resu lt;
         }

    /**
      * Imp   lements the <tt>putLong</tt> met     hod as   per t  he  specification in
       * {  @link Preferences#putLon         g(String,long)}.
      *
     *    <  p>This implementa   tion translates   <tt>value</tt> to a strin        g with
            * {@link Long#toSt    ring(long)} and invokes {@link #pu t(String,St    r      ing)}
     *    on th e re      sult.  
                 *
     * @pa     ram key key wi    th which the string f o   rm   of v    alue is to    be associated.     
       * @par      a    m val    u  e           v alue whose string form i    s to be as       so   ciated with key.
          * @throws   Null    PointerE    xce   ption if key is       <   tt>null</tt>.   
      *    @throws   IllegalArgumentException i            f <tt>k     ey.length()</tt> exceed  s
     *             <tt   >MAX_KEY_LENGTH</tt>.    
                  * @throws Illegal    Sta  teException      if t    his node (or a    n ances tor  ) has been
        *                      removed    wi      th  the {@link #removeNode()} meth od.
          */
         publ     ic void         putL    ong(Str     ing key, long va       lu  e) {
        put(key, Long.toStrin  g(value     ));
    }

    /*                       *
     * Impleme n    ts          the <tt>getLong</tt>   meth  od a     s      per the      sp  ec    ification in
     * { @link   Prefe  ren     ces#getLong(S t           ring,long)}.       
     *
     *      <p>This imp     lem      entation in   v  okes {@link #get(Str    ing,String) <tt>get(key,   
     *   null)   < /tt>}         .              If   the return v   alue is non-null, the implementation   
     * attem  pts to tra    nslat           e       i t to      a <tt>long</tt>        with
         * {@link Long#parseLong       (String)}.     If the a t    tem  p  t  succeeds, the ret      urn
     * value i            s retur  ned  by this m    et  hod.      O  therwise, <t    t>d   ef</tt    > is   ret  urn           ed.
     *
     * @param      key k    ey whose associat    ed value is to be retur   ned    as a long.
     * @param              de      f th  e  va  lue                 to be re     t    ur   n ed in       the   ev    ent tha    t this
       *              preference node h         as no value       asso    ciated with <tt>key   </tt       >
     *           or th   e associ   a   ted value c   annot be interpreted as a    long.
     * @return t    he long valu      e repr       esented by the string ass     ociated   with
         *           <t     t>key</tt> in thi    s preference node,    or <tt>def  </tt> if the
     *              associated    val ue      does not exist or cannot be interpreted    as
             *            a long. 
              * @throws IllegalStateE  xcepti    on if this n  ode (or         an     ancestor   ) ha          s been
     *            remo   ved with the {@  l      ink #removeNode( )} met     hod.
       * @thro   ws NullPointerExcepti  o n   if <     tt>key</tt>   i      s   <tt>null</tt>.  
         */
         pub lic long ge tLong( String key   , long def) {
               long re   sult = def;
        try {
             Strin           g value =       ge t(key,    n           ull);
                      if (   value != null)
                        res ult =    Long.p     arseLong(value);     
            } ca tch (Number         FormatExcepti  on e) {
            // Ignoring exception causes     specifie            d defaul  t to b   e retu     rned
              }

             return result;
    }

    /**
            * Implements t  he <tt>putBoolean</tt> method as per the specification in
     * {@l ink P  r       efer  enc         es#putBo  olean(Stri      ng,b     oole     an)}       .
        *
         * <p>   This implementation      t  ranslat   es <tt>v          alue</t   t> to a str  ing with
     * {@  li    n   k String#valueOf    (boolean)} and invokes {@link #p     ut(St          ring,Stri          ng)}
        * on the result.
     *
     * @p    aram key key with which        the s tring form of value i       s   to be associated.
            * @     p  aram value va lue   whose st  ring fo        rm is to be ass    o  ciated w    ith ke     y.
        * @    throw    s          Null   Pointe r E      xc  eption if key i  s <tt>null</tt   >.
          * @throws  IllegalArgume     ntExc eption if <   tt>key.length()</tt> exce eds     
     *                 <tt>MAX_KEY_     LENGTH</tt>.
     * @throws Illega      lStateExcep    t   ion   if    thi   s node (or an ancest   or)    has      b   een
      *          rem  oved    with   the {@link    #removeNode()} method.
     */
          public void pu  tBo      olean(S    tring key,      boolea    n value) {   
          put(ke   y, Str   ing.valu        eO     f(v  alue));
    }

    /**   
     * Implements the  <tt>getBoolean</tt> m  e    thod as per th     e specification in   
     * {     @link Prefer   ences#g      etBoolean(Str  ing,boole an)}.
                    *
     *     <p>  This impl   em      entati  on invok     es { @link #get(String,String) <tt>get(key,  
         * nul  l)</t   t>}.     If the return value    is non -nu      ll, it is compared wi     th
     * <tt>"true"</tt>    using {@link String#equals   IgnoreCase(String)}.  If th   e
     * co    mparis      on retu     rn         s <tt>true</tt>, this invo   cation re        turns
     * <tt>true</tt>.  Otherwise, t     he original      ret      urn val   u         e is com  pared wi  th
     * <tt>"false"</tt>, ag  ain using {@link String#eq    u al   sIgnoreCa se(String)      }.
             *      If the compariso   n r    etu         rns <tt>true</tt>, this               invocation returns
           *       <tt>   fals   e     <     /tt>.   Ot    herw           ise, t  his       invocatio      n    returns   <tt> de         f<     /tt      >.
               *
     * @par   am key          ke    y whose asso   ciate  d   value is to b   e       returned as a bo   olean. 
      * @param d ef the value to  be       return  ed in        the      ev       e  nt that            thi           s
     *        preference node h  as  no value ass      o          cia        ted with <         tt >  k         ey</t     t>
      *        or the associat    ed         value cann  ot be   interpreted         as a boolean.
          * @return t    he boo     lean v alue           re      presented by the string associated with
     *                   <tt       >  key <   /tt  > in th    is            pref  ere    nce node, or <tt     >def</tt> if the
        *           asso      ci   ated     value doe     s not exist or    c            annot be i  nterpreted                   as  
           *                        a bool         ean      .     
       * @throws Illeg     alSta teException    if th    is node (or an ances      tor) has bee      n
     *            removed wit  h the {@link #remo  veNode()}     met                hod.
          * @thr ows NullPointerExc   e                pti     on if <     tt>key</tt> is <t     t>null</t  t>.
     */  
                     public boole   an getBoolean(St              ri  ng key, boole   an de              f) {
               boolea  n     result =      def;
          String value    = g  et(key, n    ull);
           i    f (value != null)    {
            if       (value.          equalsIgnoreCa  se("true")   )     
                     r       esult = t     rue;
             else if ( value.equalsIgn  oreCa  se("fals    e    "))
                                         result     = fal        se;
        }

        return    result;
    }

    /**
      * Implemen  ts the <tt>p      utF   loat</tt> method      a    s per the   specification      in
     * {@link Prefer ences#put         Float(S      tri        ng,f    loa           t)}.
     *
           *  <p>Th      is implement    ation t    ranslates      <tt>  v   a     l           ue</tt    >      t   o a string with
     *      {@lin k Float#toStr     ing   (float)} and i nvo   k      es  {@lin    k #put(String,String               )}
        *  on th e    result. 
     *
     * @p aram ke     y k  ey        with which the string for   m of val     ue is to be asso      ciated.
     * @para     m val   ue value  whose st        ring form is   to be     associated    wi   th     key .
     * @throws      NullPointerException if key is <t       t>null</tt>.
     * @throws IllegalA  rgu        me   ntException if <tt>key.length  ()</tt> exc   eeds
        *                 <   tt>MAX_KEY_LE   NGTH</tt>.
      * @throws    Illega lS     tateException    if   this no   de (or an   a ncestor ) has b       een
          *             remo   ved with          t  he {  @link  #removeN ode()} m ethod.
     */
             pu   blic void putF         loat(String key, float value) {
            p     ut(key, Float.t    oStrin  g(        value));
        }

    /**
            * Imp     lement   s the <tt>getFlo             at< /   tt> method as    per    the          specif ication in
           *  {@l ink Pr   efe   rences#get         Float(String,f      loat)} .
     *
         * <p    >This implementa   tion invokes {@link #get      (Str   ing,St                    ring)    <tt>ge   t(key,
      *       null)</tt>}.  If t       he       ret     urn val  ue   is non-          nul  l,   the imp          l    e    mentation
     * attempt   s to tran              sla   te it to an          <       tt          >flo  at</tt> wi   th
     *    {@lin    k Floa t#parseFloat(St     ring)}      .       If the a ttempt succeeds, the     retur n
     * value is    retur ne d by this     method.  Otherwise,      <tt >def</tt> is returned.
          *
               * @param       key key whose ass  ocia                ted valu  e i  s  to be returned as a float.
        * @param def the value to     be retur  ned in   th   e event           that        this
        *        prefere     nce no de has no value a   ss   ocia  ted w       i   th <tt>key</tt>
              *        or the    associated value cannot be interpreted as a    floa    t.       
        * @        return the      f   loat valu   e re   p resented   by the     st       ring a  ssociat  ed      with
     *                      <tt>key<  /tt>  in thi  s p  reference node, or <t    t>def<        /tt> if t     he
        *                 as  so     ciated val      u     e doe    s not     exist or c     annot be inte rpreted   as
           *         a         float.    
             * @throws Ill  eg    alS   t  ateExcept  io   n if thi    s node (or an       a     ncestor) h   as b   een
     *          remo ved w ith        the {@link #removeNod   e()}      method.
                *          @throw    s NullPoin              terExcep  tion    if        <t       t>k    ey</tt> is <tt> null</tt>.
     *               /
    p    ubli c float      getFl           oat   (S   tring key       ,   float     def) {
          f    loat result = def;
               t       ry {
                       String value =   g   et(key, null);
                    if (value    !         = n   ull)
                     resul  t = Floa  t.parseFloat(value);
        } catch (NumberFo   rmatEx    ce      p  tion e) {
            //    Ig     noring e    xcep          tion   causes specifi             ed default to b e ret    ur  ned
        }

             re     turn result;
     }

                       /**
     * Impl      ements the    <tt         >putDou   ble</tt  > m           e     t     hod as   per the specificatio  n in
     * {  @link                  P   references#putDouble(S    tring,dou    ble)}.
            *   
     * <p>This impleme       ntation       transla   tes <tt>val  ue</     tt> t      o a str  ing with
        * {@  li    n    k Dou      ble#t    oString( double)} and invokes {@link #put(     String,     String)}
     * on the  resul   t.
            *
     * @param key key    w  ith whi  ch      the    str   ing fo r                m          of v       alue is to   be associated.
     * @      param value value w  hose    string form is     to be associated     with  key.
        * @th  rows NullPointerExc  ept   ion if   key        is <  tt>null</tt>.
     * @               thr       o  ws IllegalArgumentE    xc eptio   n if      <    tt >   key.length()</tt> exceeds
     *             <tt>MAX_KEY  _LE    NGTH</tt>.
        * @thro             ws        Ill    egalStateException if this no de            (or           an a    nce         stor) ha       s         been
     *                 removed with the {@li     nk #removeNode            ()} me   thod.  
     */
    public void    putDoubl      e(String    ke           y, do     uble value ) {
                     put   (k    ey, Dou    ble.toStrin   g(  value))          ;
           }

    /**
      * I m   p      lem   ents the <tt>g  etDouble</tt> method a   s per the     specification in
       * {@link Preferen   ces      #getDoub  le(String,double)}.
      *
     *                 <p>This       imple m  ent          ati on        in        vokes {@link #ge    t(String   ,St  ring) <tt>get(key    ,
      * null)</tt>}.        If the return value       i   s non-null,  the implementatio    n    
                  * attempts to translat    e it to an <tt>d       ouble</tt>                          with
           * {@  lin  k Double#parseDou     b    le(Strin   g  )}.  If the attempt succeeds,                     the ret      u        rn
                     *       value         is returne   d b    y      this     me           thod.  O        therwis   e, <   tt>def</t   t> is returned.
                   *
     * @p aram k ey         k ey whose associated valu   e is to be ret   urned      as a d   ouble   .
        * @pa   ram def the v   alue to be returned in the ev  en    t t hat  this
         *        preference node    ha s    no    v     alue        ass    ociate               d with    <tt>key</tt>
     *        or      the    a     ssociated value      cannot be interpreted as a d  ouble.
     * @        return the doub     l    e value r     e  presented by the st  r ing as     sociated with
       *                     <tt>key</tt> in this preference no  de, or   <t    t>   def</tt> if the
     *             associa      t ed    val     ue    does not      exist or canno   t be interpreted as
     *          a dou    ble.
            * @thro   ws Il    legal             Stat       eException if    th     is     node (or   an anc   estor           ) has been     
           *         re   mo  ved           with  the {@link #removeNode()} method.  
     * @  throws NullPoi   nterException if    <tt>key</tt> is  <tt>null<                  /tt>.
     */
         publi      c double get        Double(S        tring key, double de  f) {
        doub   le result = def;
        try {
            String va    lue = get(key, null); 
               if (value !=   nu   ll)
                  res   ult = Double.p     arseDo    uble(v  alue);
        } ca    tch (Numb erFormat           Ex  ception e) {
               // Ig  noring       exception cau   ses sp    ecif   i  ed defa      ult to be returned
                     }

                        ret  urn resu           lt;  
       }

     /**
         *   Imple     ments             the      <tt>putByteAr    ray</tt> me    thod as per   t he specification in
     * {@link Pre    ferences#pu  tByteArray(String,byte[])}.
     *
     * @para  m key key     with which      the    string form of va   l      ue is         to be a  ssoci   a   ted.
       * @param value v              alue whose        str     ing   form         is to be a  s  sociated with key.
      * @throws NullP                    oin  terExc     ept i      on if key or value is <tt>null</t   t>.
         * @throws  Illeg     alAr         gum       ent Exception if key.l   ength(   ) exceeds MAX_   KEY _LENG   TH
      *                or if va    l      ue.length    exceeds MA    X_VALU E_LENGTH*3/4.
       * @throws Ill    e   galStat     eException if this node    (       or an an    cestor)  has bee n
         *         removed with the {@l               in    k          #removeNode()} meth   o           d.
            */    
        public v oi       d    putByteArr  ay(Str    ing        key,    byte[] value) {
         pu     t(key, Base64.byteArrayToBase64  (value));
    }

    /**
            * Imple     ments the       <  tt>getByt  eAr   ra  y</tt> m             ethod as   p er the specification in
     * {@li  nk Prefe     re      nces#getB     y  teArray(String,byte[])}.
             *
          *  @    param key key whose ass  ociated          value is to be returned a   s a    by             t        e   a               rray.
     * @  param def the value t     o be   re  turned    in th               e event that this
        *        preference node has no value as     soci   ated          with <      tt>        key</tt>
     *               or th  e associ ated value cann     ot be    inter   preted    as a b yte array.
               * @return the   byt     e ar        ray val ue repre          se       nted by the      strin   g assoc          iated          with
          *             <tt     >k  ey</tt> in this preference node, or <tt>def</tt> if the
        *         as       sociated value d    oe   s not e xi   st or cannot b   e   interpreted as
     *            a byte array.
       * @throws IllegalStateE     xcept                   ion if this           node (or an   an               cesto     r)  has          been
     *                   r  em        oved with   the {@       link #removeNode()} met  hod.
          * @throws  NullP   ointe        r     Exception i f <tt>ke                y</t t> i        s <tt>null</          tt>.     (A    
       *            <tt>null</tt> value for <tt      >def</tt> <i>is</i> p               ermitted.)
                  */
    public byte[] getByteArray (String    key,  byte[] def) {
        byte[] re     sult  = de   f;
              String value = get(key,         null);
        t    ry    {
                     if (value !=  nul  l    )
                  re s         ult = Base64.    b      ase6  4To    Byte     Arr  ay(val   ue);
                 }
        catch (RuntimeE    xce  ptio    n e) {
            // Igno                ring exception caus   es s  pecified d        ef a        ult t   o be r  etur   ned
        }

            r   eturn r    esult;
    }

    /**
     * Impl   ements      the <tt>key    s </tt> meth     od   as       per the sp      ecificat  ion in
         *  {@link Pref  ere  nce  s#ke      ys()}.
             *   
       *    <p>This im    plem    entat    ion ob   tain      s this preference node'          s l                    ock,    checks         that     
          * th  e node has not  been removed and invokes {@link #keysSpi()}              .
          *
           *    @return an array of the keys    that ha  ve an associ       ated   va      lue in this
       *         preference node.
     * @throws Backi   ngS   tore    Excep       tion if this oper              ation cannot be com    ple   ted
     *           d   ue to a f    ailure in the  backin     g store,  or   inability to
     *             com             munic    ate with it.         
               * @thr   ows     IllegalS    tat     eEx        ception if this node (or an ancestor) has bee n
       *            r  emo ve     d with the {@link #removeNode(   )} method.    
             */
      public St            r    ing[       ] keys() throws BackingStor  eExcept   ion {
        synch    roniz e      d(lock) {
              if (r      emoved)
                      thro  w new Ille          g     a   lStateException   (                  "Node  has been removed.")     ;

            return keysSpi()  ;
         }
    }

                /**
          * Imp    lements    t  he  <  tt>ch   ildren</tt>             metho   d as p   er the s    pecifica      tion       in
     * {  @li              nk   Prefer   en    c        es#childrenNames()}.
      *
     * <p>This impleme      nt   ati on obt       ains this preferen     ce node   's lock, ch     eck   s that
                            * the node ha   s not    be    e              n       remo   v          ed  ,    con  s     tructs a <tt>     TreeSe  t</tt> ini  ti alized
     * t    o the     nam   es of children already cac   hed (the ch  ildre  n       in this node's
       * "child-cache"),     invoke    s    {@  link #childrenName   sSpi  ()}  , and        a dds all          of       the
       *     ret    urned ch        ild-   names into t  he set.  The    element   s o f  the tree  set ar   e
         * dumped into a <tt>    String</tt>  ar   ray using   the <tt>toArray</tt> met    hod   ,
     * an     d th     is array is retu     rn      ed.
           *
       * @retur  n the  n    ames o    f the chil         dren of    th is pref   e      rence node                  .
        * @throws   BackingStoreException if this      op           eration  cann    ot b   e complet  e               d      
     *           du     e        to a failu     re     in th e      b     acking s   tore, or      inability to
     *         c    ommunicate with it.
       * @th    rows   IllegalStateExc            ep   tion      if this    no   de (or an ance stor) has be     en
           *                     remo  ved with the    {@link #  removeNode()} method.       
     * @ see  #cachedChildren()
             */
    publi   c String [] childr   enNa  mes() throws Back    ingStoreExceptio n {
                 synchroni zed(l     ock) {
                  i   f (removed)
                     throw new Ill  egalStateExcep    ti             o   n("Nod    e has       been removed.");

            Set<St               r        ing> s = new TreeSet<>(kidCache.  k    eySet())         ;
                   for (Str   ing kid  : childrenNamesSpi(   ))
                                           s.ad  d  (kid);    
                 return s.toAr      ra    y (        EMPTY_STRING_ARRA       Y );
          }  
      }

    pri    vate static         final   String[] EMPTY_STRING_ARRAY =     n      e  w String[0];

    /*          *
        * Return    s all known u     nre     m  oved     children        of this     node.
     *
       *   @retu    rn all known    unr        em    oved c        hi      ld        ren of this node .
     */
    protect  ed f   in    a  l Ab  stractPref    erences[] c    a   c  hedCh      i      ldren()       {
        retur    n      k    idCa    che.  values(  ).toAr       ray(EMPTY_A    BSTRACT_PREFS_ARRAY)     ;
    }       

                 private static f   in             al Abst   ractPre f      erences[] EMPTY_ABS   TRACT          _PRE      FS      _AR        RAY
        = new     Abstract   P     references[0]  ;
   
       /**
           * Im  ple  ments the <tt>paren    t</tt>    met    hod      as p er the specific  a   tio n     in
     * {@l   ink Preferences#p    arent()}.
       *
     * <p>Thi s impleme  ntation obtai     ns thi     s preference node's lock, checks that
     * the node   has     not be       en remov   ed and returns    the parent value that was
          * passed to this node's constructor         .
        *
          * @return      the pa   r      ent o        f this    preference node.
     * @throws   IllegalStateExcepti on if  t    his n         ode (or an ancestor) has been
      *         remo    ved with the      {@l  ink #re      mo  veNode()} m  ethod.  
     */
    public Preferen     ces parent(   ) {
           syn                  chronized         (lock)     {
                 if (removed   )
                                     thr   ow new    I    llegalStateException("   Node has b   een removed    .");  

                     retu   rn parent;
           }
    }

    /*      *
              * Implements the    <tt      >n      od   e</      tt> me  tho  d as    per  the   specification in
     * {@link P     refe rences# n  ode(Str  ing)}.
                  *
     * <p>Thi  s implementation o   btains this preference node's lock and checks
                   * that the node h       as not b e         e     n                 removed.     I     f  <tt>path</tt> i    s  <    tt>""</tt>,
     *     thi s   node is ret      urned;     if <      tt>p    ath<        /tt>     is <tt>"/"   </tt>, t   h    is n     ode's
             *  ro ot is return      ed.  If th    e first charac   ter   in <tt>path</t       t> is
          * not <tt>'/'</tt>,    t       he implementation         br  eaks <tt>  path</tt>       into
             *    tokens and     re    cursive ly traverse  s the  path fr  om this n  ode to the
          *    named node,  "c on   suming" a na        me and a sl     ash from <tt>path</tt>    at
     * each ste p of        the traversal.  At each step, the current n  ode is locked
     * and the   node's ch ild-cache    is check     ed  for the named node.  If it is
                        * not found, the name  is c  hecked to     ma  ke   sure      its len    gth does not
     * exceed <tt>MA    X_NAME_LENG      TH</            tt>.           Th  en the {@lin k       #c         h ildSpi(String)}
           * method       is invoked, and    the     res     ult   stored in this     node's c  hild-cac     h e.
         * If the newly creat        ed <tt>P         r  eferences<        /tt> object   's  {@li nk   #newNode}
         *    field is <tt> t     ru e< /tt> and t     here are any n ode c     hange     listen    ers   ,
          * a notific    at     ion eve n   t is        enqueued for     pr   oc      essing by th e e v     ent       dispatch
                *     thread.
        *
     * <p>When      there are n  o more t    oke   ns,     th e last val   ue f   ound in the     
        * ch    ild    -c    ach         e or r       eturne      d b       y     <       tt>childSpi</      t t> is ret    urned b y               this
            *   method.  If during t    he  traversal,      two   <tt>  "/"  </tt> t   okens    occur
     * consecu      tively   ,   or the fi   nal token i              s     <tt>"/"</t    t>          (rat    her t   han a name),
        * a  n app       ropriate <tt>IllegalArgumentException</tt> is thrown.
          *
              * <  p> If the first                     ch           aracter of <tt>path            </tt> is <tt>       '/'    </tt>
        * (   indicat  in      g  an absolute path name) th  i        s preference no          de's
                   * lock  is    dropped prior   to br    eaking <     tt>       path</tt> int  o     tokens,    and
          * this method r         ec  u rsively   tr      ave     rses the p   at h starting from the roo     t
     * (rather tha   n starting    from     this no de ).  The traversa     l is       otherw   i     se
     *         identical to the one   described for relative    path     n   a         mes.  Dropp      ing
     * the lock on this node pri   or to         comm    enci     n          g       th   e tr     aversal at th   e       root
       * node is es   sential     to a          void the pos    s         ibil ity of deadlock, as p er the
     * {@l    ink #lock locking inva  riant}.     
      *
      * @param path the   p     a   th na    me of the preference node to return.
      * @return the    specified     preference no    de.
              * @throws    I    ll   eg alArgumentExcept        io  n if the path name    is       invali   d (i.e. ,
     *              i         t   contains m     ultiple c    onsecutive slash   ch    a  racters,                   or e           nds
     *                 w  i        th a sl  a  sh c      haracter and is more than one cha racte   r lo  ng)  .
        *      @th  rows Il      le   ga  lSta      t   eE   xceptio  n  if this node (or an      ancestor) has been
     *          rem oved with  the {@lin   k #removeNode       ()} meth od.
     */
     pu      bl     i   c Prefere      nc   es no   de(String     path)  {
        synchronized(loc    k) {
                     i   f (  removed)
                        t    h   r     ow        n ew IllegalStateExce   pti          on  ("Node  has              been r emoved.");
                 if    (pat h.equals(""))
                   return this;
               if (       path.equ    als(       " /"))
                     r    eturn root;
                       i  f (path.charAt(     0)   != '/')
                         retur    n    no      de(ne  w StringTokenizer(    pa        th,              "    /",     true));
        }
        
                          // Absolut       e path    .  Note that we     've     dropped our lo    ck to avoid    deadlock
        re     turn root.node(new St         ringTokeniz er(path.subs     tring    (1), "/", true))   ;
    }
 
    /**
         * tokenizer    contains < name> {'/  ' <name>    }*
      */
    priva         te Preferences no   de(StringToke   niz     er path)     {
        St ring to         ken =  path.nextTok   en();
                 if (to   ken.equals("/      "))        // Check   f    or co  nsecu  t  ive s      la  shes  
                   throw new      Il        legalArg  um     entException("    C    on     s   ecuti         ve    sl ashes in pa t  h");
             s      yn   chronized(lo     ck)        {
                  AbstractPreferences ch           ild = ki   dCache.get(   tok       en);
            if (child == null) {
                   if  (token.  length(  ) > MAX_NA  ME_LENGTH)
                                      throw    new IllegalArgumentEx   ce   ption(
                                "    Node     na    m    e "      +   to ken + " too      lo                         ng");
                                   child      = childSp      i(          token);
                      if (child.newN ode)
                                           enqu   e      ueNodeAdded  Ev        ent(child);
                              ki   dCache.put(t  oke   n, child);
            }
                      if       (!path.hasMore      Tokens())
                 return c     hi  ld  ;
               path.nextToken();  // Consume slash
            if (!p     ath.hasM    oreTo     kens())
                   thro   w new I llegal    ArgumentExcep tion("    Path   en       ds with s lash"  );
            retu   rn child.    node(path);
        }
              }

    /**
     * Implements      the    <tt>no    de                   Exists</tt> met        hod as     per  the s  pe        cific   ation in
     * {@link Pref  eren ces#no   de     Exists(String)}.
              *
              * <p>Thi  s impleme    nt     ation i    s v  ery si   mil       ar    to     {@link #node(String)    },
     * excep t     t          h  a          t {@l      ink #       get   Child(String)} is used instead           of      {@l  in  k
              * #      childSpi(String)}.
           *
        * @par      am path th   e path na        me o     f  the       node whose existence i   s to be checked.
       * @return true if the specifie    d node e      xists.
     * @thr ows      B ackingStoreExcept     ion if this oper        at   ion cann    ot be comp       leted
     *         due    to a       failur        e in the b  a     cking store, or i   nability to
     *              comm    unicate wi  th i   t.
     * @   throws IllegalAr   gumentExcept     ion if     the path na me  is           invalid (i. e.,
           *             it con    tains mu       l    t    i        p      le   consecutive slas   h          ch     a   racters, or      ends
       *            with a slash character and is more th     a    n one character long).
     * @thr  ow         s IllegalStat  eE  xception  if thi s node (or  an an           cest  or)   has been    
     *             removed wi   th the {@      l   i    nk       #re mov   eN      ode()}      meth      o       d       and  
            *         <tt>pathn   ame</tt>      i       s not the empty s  t           ring (<tt>""< /tt   >).
     *     /
    public boolean nodeExists(  String path)
          throw      s Backi ngSto reEx  ception 
    {   
         syn              chr       oni z ed       (lock) {
                 i   f (path.e q uals  (       ""))     
                   return      !remo    ve   d;
            i   f (remov      ed)
                           th    row new Illegal  StateExc eption("Nod        e  has been re  move      d.");
                      if (p   ath.e    qual       s("/"))
                                 retu            rn    true;
                        i   f (path    .char  At(0) != '/')
                   return      nodeExists(new St   ringTokenize      r(path, "/"    , true));
                      }

                           // Ab  solute path.  Note tha  t we   've dropped our        lock to av  oid dead       lock
        re           tur   n ro  ot.nodeExists    (n ew St  ri    ng Tok       enizer    (path.su bstring(1), "/",
                                                                       tru    e) );
    }
   
    /**
     * tok    enizer cont   ains <n   a    me> {'/' <n  ame>}*
     *    /       
    private bool       ea    n     nodeExists(StringTok   enizer pa  th)
                    th    rows      B acking     StoreExce      ption
        {
          Strin g                token = path.      nextTo  ken();
                    if (    token         .equals("/"))     /     / Check for consecutive slashes
                         throw new    Ille      galArgumentE x   cepti      on("Co   nsec   uti      ve slashes in pa    th");       
        s          y     n   chroniz       ed(loc  k) {
                    Abstra     ctP    refe rences child = kidCache.get( token);
                       if (c  hild       == n   ull)
                            child   = getChild(token );
            i f (child==null)
                      return   false;    
                  if (   !path.     h   as    MoreTokens())
                  r  eturn  tr     ue;
                 p  a  th.n  extToke    n(      );  // Consume     sl     ash
                         if ( !path.hasMoreTok     ens())
                          t h       row  new     I    llegalA     rgum        entException  (   "Pa   th en      ds with slash" );
               return  child.no deExist   s      (path) ;     
                }     
         }

     /**

        *  Imp      leme   n  ts     the <  tt     >removeNod         e () </tt> method as per the sp       ecif    ication in
       *  {@lin      k Prefer   ences#remo   veNode()}.
       *
        * <p      >  This i   mplementation checks to see that thi        s node    i s the       root;     if s      o    ,
     *   it      throws an appropriate except ion.  Then,        it lo   cks    this          no  de's parent,
         * and calls a       recursive helper  met            hod that traverses the  s       ubtre  e rooted a  t
            * this node.           T he     recur   si       ve     method locks the n     ode on which it was called,
     * c    hecks              th  at it has not     a  lready bee  n removed, an       d then ensur    es   that all
                * of     its children  ar  e cac     hed : The {@link #chi        ldrenNamesSpi()}    method          is
         * invoked   and each returned          child na  me is checked for containm   ent in th      e
      * child-c    ache.      If a child         is not already cached,   the {@    link
       * #   childSpi        (St    ring)} met ho  d is invoked to c        rea          te a <tt>Preferences</tt>
     *   instance     f or    it, and this        in      stance is put into the chi  ld-cache.  Then    
        * the helper   method call   s itself    r     ecur  sively on each node c   ontained in   its
                *  child-c         ach       e.  Ne   x    t,       it invok       es  {@l   ink #rem oveNodeSp      i(    )}, mark   s  itself
     * a       s r     emoved, and  remov   es itself  fro   m i   t  s pa      rent   's child-ca  che.  Finally,
           * if there are any     node c han     ge lis     tener            s, it enqueues a          not      ification
      * e vent for p        rocessing by the event dispatch thread.
     * 
        *   <p>Note tha t t    h  e helper meth  o    d  is al            w     ays     invo    ked with all an            cesto            rs up
             *  to the "closest no     n- remove d ancestor" locked.
      *
       * @ throws Ille  galS       t   ateException     if t   hi   s no       de (o          r an   ancestor)  has already
          *         been removed with    the {@lin k #r         emove   Node()}      method.
     * @throws Unsup   portedOpera  tion    Exception if this m  e  thod is inv oke         d o    n
     *                  t      he root node.
           * @throws BackingS   toreExce     pt ion if this   operation cannot be completed
        *               due to a failure in the backing st     ore, or inability to
                   *         communica te with i  t.
     */
                              pub                             lic void removeNode() throws     BackingStoreException { 
                    if (this==ro   ot)
            th     row n    e  w   UnsupportedOpe                     ra   tio     nExc      eption("Can't remove the root!       ");
        synchronized(parent.lock       ) {
                 removeNode2();
                p     arent.kid Cache.remove(name);
                           }
    }

       /*
     * Called    with l ocks on all   nodes on p ath from parent of "remov   al root"
     * to t    his     (in    c  luding the        forme      r       but e    xcluding    the    la     tter).
           */
     private void remov        eNode2() throws  BackingS toreExcept   ion   {
                          sy nch    ron   ized(lock) {
            if (r   emoved)
                       throw n        ew Illega   lStateExce        ption("Node alr eady rem     oved.");

                    /   / Ens         ure that a  ll    children are c  ached
                 String[]    k idNames = childr      enNam  esSpi();
                      f    o        r (  in      t i=    0; i<kidN           a   me  s.l  en   gth; i++)
                             if (!kidCache.contains       K   ey(k  idNames[i])   )
                      ki dCache.put(kidNames     [i], childSpi   (ki  dNames[i]))     ;

            // Re   cursively remove all    cached                ch              ildr                 e        n
                     f     o r (Iterator<Ab  stractPr   eferences> i = ki          dCac     he.values().iterato             r();     
                              i.has  Next();) {
                             try    {
                            i.next().removeNode     2();
                               i.remove() ;
                            } catc   h      (BackingStore      E        x     ception x) { }
            }

            // Now we have no de   sc  end   ants    - it's time to die!
               removeNodeS  pi();
                   removed =     true;
                                parent.enqueueNodeRem     ovedE   ven     t(this);
        }
    }

    /*    *
     * Implements    the <tt>name</tt> meth    o d as per the s  pecifi catio n in     
     * {@link             Prefere    nc      es#name()}             .      
           *
     * <p>This implementat  io n mer  ely    returns         t he   n   ame that was
     * pass      ed to    this        node's cons    truct   or.
           *  
     * @retu   rn this prefere       nce  no       de's name,      relativ e to its      parent.    
     */
    publ ic String name() {
           return name;
         }

    /**
     * Implements the <tt>  abso  lute  Pa  th  </tt>           method as per     th     e specifica    tio   n in
     * {@link Prefe    r enc   es#        absolu t  ePa    th()         }.
     *
     * <p>This i mpl  ementation merely returns the a     bsolute path name that
     * was computed at   the time    that this  node      was constructed (ba  sed on   
                           * the name that  was passed to th        is nod   e's cons    tructo   r, and t   he   n  a    mes
      * that were passed to    t      h  i        s node's ance    sto r         s    '       cons    tructors)    .
     *
     * @retur                n this prefe        rence node's             a     b   so  lute path    n  ame.
        */
    publ   ic S    tring absolutePath() {
        retu    rn absolutePath;
    }

       /**
       * Imp  lements th e <tt>isUserNode</tt> method a    s per t  he s           pe          cification in
     * {@l   i      nk Prefere  n     ce   s#isUserNode()}   .       
     *
             * <p>T           his imple   mentation compares th    is node's root n   ode (w   hich is stor ed  
                 *    in a    p  riv ate field       )        w      ith th  e value   returned   by
     *      {@lin      k Pref  erences#us   erRoot()}.  If the two object references are
      *  identi             cal, t his     method retu r           n   s    true.
     *
     * @retu    rn      <tt >t  ru     e<    /tt>        if this pre ference        node is in the use   r
     *                 preferenc     e tree    , <tt  >false< /tt>   if it's   in the    sy st  e      m
               *              prefe  rence tre  e     .  
     */  
           publ           ic bo ol              ean isUserNode    (  )         {
            retu   r  n Ac  cessController.do      Privileg              e    d(
            new PrivilegedAc      tion<Boolean>()  {
                public Bo   olean run() {
                      return ro      ot   == Prefere      nc  es.use rRo    ot(    );
                     }       
                        }).boolea     nValue();
    }

    public v   oid addP   r eferenceChangeListener(     Preferen    ceChan         geListener pcl)     {
        i     f (pcl=      =null)
            t      hrow new N         ullPo  interException("     Chan   ge li stener is null.");
        synchroni  z         ed    (lock) {
              if  (removed)
                     throw new Illegal  StateException("Node has b   een              re     moved.");

                       //    Co py-on-write
                  Pr      efere  nce C  h       angeListener[] old = prefLis te       ners;
               prefL   iste   n   ers = new      Prefer enceChang   eListener[  old.leng  th +   1];   
                  System.arrayco         py(old, 0, prefListeners, 0,  old.len gth);
               prefListen   ers[ old.length] = pcl;
        }
              start      EventDispat    chThreadIfNecessary();
    }

    p   ublic              void re   moveP   refe         renceChangeListener(PreferenceChan    geListener pcl) {
        synchronized(l          ock) {
                       if (remove        d)
                   throw ne     w Illegal   Sta      teExc      ep   tion("Node has   bee  n removed.");
            if ((prefListen            ers =   = null) ||       (pre        fLis      ten        ers.length == 0))
                        throw new I     llegalArg    ument   Exception("     Listener not registered.");
 
                     /   / Co  py-on-          w   rit        e 
                  Preferen ceCh  a  ngeListen     er[] newPl =
                      new PreferenceChangeLi    s  tener[prefListene rs   .len                       gth - 1  ];
                      in    t i     =            0;
            whi       le (i <    n         ewPl.length  &&               prefLis  t  eners[i]       != pcl)
                         newP              l[i]   = prefL  isteners[i++];

                  if (i == newPl.   length &&  prefListeners[i] != pcl)
                       throw new Illega   lArgu       men      t  Exception("Listener not reg     istere  d   .           ");   
                            while (i < ne     w  P l.length)
                              newP l[i] =  prefListeners [++i];
            prefLis    teners =      newPl;
             }
    }

    publ i       c     void add      NodeChange   Listener(   NodeChangeListener            n    cl) {
            if (ncl==  null)
                                   thr       ow new NullPointerExceptio      n("C hange listener is null.");
          synchronized(loc k) {
                     if      (removed)
                     thro  w new Ill egalStateExce     ption(" Node has bee n   r emoved.");

                         // Co     py-on -writ e   
            i      f (nodeListen ers == null) {
                  nodeLis   t eners =    new NodeCha  ngeListene    r[1];
                nodeListener  s[0] =   ncl    ;
                    } else {
                            Nod     eChangeLis           ten  e  r[  ] ol    d   = nodeLi  stener     s   ;
                              node       Listener   s = new NodeChangeListene   r[     old.           length + 1];
                             Syst    em.ar     raycopy(old, 0, nodeL     isteners            , 0                , o     ld.length);           
                          nodeListene   r      s[old.le    ng             th] =    ncl;
               }
                       }
        startEven  tDispatchThre  a    dIf   Nec essary(          );  
          }

    p     ublic   voi  d remov  e   NodeChangeListener(NodeChangeL  istener nc  l) {
             syn   ch  ronized(lock) {
            if      (r   e   moved)
                               throw      new I lleg  alState      Exception("N  od     e has been r       emoved.")    ;
                       if ((nodeList    ener  s == null) || (node    Listeners.length == 0))
                                             thro   w new IllegalArgume   ntExc    ep   tio   n("Listener        n       ot regis  tered.");

               // C  opy-o    n    -   w   rite
                i   n t    i = 0;
              while (     i < node Listeners.lengt   h && nodeL    isteners[i]  != ncl)
                            i++ ;
            if (i == no  deL iste  ne  rs.length)
                               throw n    ew Illegal   A r  gumentExceptio     n("Li    stener n    ot r     egistered.");      
                      NodeChan  geListe ner[]     newNl =
                   new NodeChangeL      istener[nodeListeners.length - 1];   
                    if (i  != 0   )
                     Syste  m.arraycopy(nodeListeners, 0, newNl,       0    , i           );
                      if (i       != n     ewNl.l   e     ngth)
                  System.ar     raycopy    (nodeList  en     ers, i +   1,  
                                     newNl, i, newNl.length - i);
                      no   deLi  stene  rs =      newNl;          
             }
    }

    // "SPI" M     ETHODS

        /**
                 * Put t  he give  n key-value asso   ciatio   n i     nto this pre   fe    rence node.    It is
      * gu   ara  ntee d that   <tt   >key</tt> and      <tt>value</tt>     are n    on-null and of
     * legal l     ength.    Also, i  t is   guara         nt  eed that this      n          ode has not been  
       * remov  ed.  (The               implem ent  or needn't          check for any of these things.)
       *
         * <p>This method is invoked    with    t  he lock   on this node  held.
     *             @    pa ram key the key
         * @   param value the val ue
                */
     prot         ec te     d abstra   ct void putSpi(String      ke       y, String value   );
     
    /**
          * Return   th e val   ue associated wi   th the   specified k    ey at this p      re   ference  
     *     node, or <tt>nu            ll</tt> i  f there is no association f   or this    key, or the
      * ass ociatio   n cannot be determi   ned         at this time.      I      t i s guara  nteed that
        * <tt>   key</tt> is non-nu  ll.  Also, it is gua   rante   ed that this node has    
     * not been rem    oved.  (The            implementor needn      't c    he   ck for either of thes    e
       * things.)
          *
     *       <p> Generall     y           speaki    ng, this method s          h  ould not              throw an exce    ption
        *        u  nde  r a n y circumstances.  If, howe  ver, if it do  es      throw an   exception,
         * th   e e xception will be intercepte  d and treated as a <      tt>null       </tt>
     *       return value.
     *
        *       <p>This met h  od is      invoked with the l           ock on   thi    s      n       ode hel        d.
     *
     * @param key the key
     * @r  etu      rn the value associat     e           d with the specified key at this preference
     *             node, o    r <t  t>null<   /     tt> if there is no asso    ciation      for th is
      *                 key, o    r the asso  ciation cann       ot be       de  termi     ned at                this time.
     */
       prote  cte    d abstrac    t S  tring g   etS  p  i(Stri    ng              ke  y);

    /**
     * Remove the ass ociation       (if    any) for t     he specified key at this
     * preference     node   .  It is guaranteed that <t    t>key   </tt> is n  on-null.   
                *       Also, it is guaran  teed that     this node h        as no   t be en removed   .
           *            (T  he im   plem     entor needn't check             f        or either of these things .)
      *
     * <p>This me    t hod is in  voked with th  e    loc   k     on this node held.
     * @param     key th   e k   ey
     */
    pr    ote  cted abstract voi    d remo veSp  i(   Stri     ng key);

        /**
        * R   emoves this prefere          nce             no      de, invalidating it     and any    pref     erences that  
          * it                contai  ns.  Th  e named child w  ill have no desce   nd  an             ts at the     t     ime t   his  
         * invocation is made (i.    e.,   the {@link      Preferences#removeNode()} method
     * invokes this meth  o      d repeatedly in a     bottom-up   fashion, rem    ov ing each of
                    * a node's    descendants   b  efore removing    the                       node i tself).
           *
     *  <    p>This method is      inv   oked       with the      l     ock held on     this   nod           e and its
     * pare nt (and all   ancestors th  at       are being removed as a   
        * result of a single  in vocation        to {@link Pr  ef   e rences#remo  veNode() }).
     *
      * <p>The   removal o   f a node need  n't bec ome pers   istent u      n   til      the
     *          <      t t>flush</tt> metho   d is invok   ed on thi s     node (or an ancest  or)    .
                  *
      *  <p>If thi s node th   rows a <tt>B    ackingStoreExcepti  on<   /tt>, the excep   tion  
          * will pr o   pa    gate out beyond the encl     osing {@l  ink #removeNode(   )}
     * invocation.
     *
     * @    throw  s Ba          ckingStoreExceptio   n if this   operation cannot     be completed
     *              due to a f ailure    in th           e backing              store, o       r  inability to
     *              communica   te with it.
     */
    pr otecte      d a    bstract void re    moveNodeSpi() t    hr     ows Backing St  oreEx  c    ep    tion   ;

    /**
         * Returns al    l of the k eys that have   a   n as   so      ciated va          lue in       thi   s
     * preference n   ode.  (The re    turned array will be o     f si   ze   zero i     f
     * this    no    de h    as no preferen c          es.)  It is    g      uaranteed that this no  de              has not
           * been removed.
     *
           * <p>Thi s me    thod is i  nvoked with the lock on this node held      .
     *       
     * <p>If this    node th   rows a <tt>Back ingStoreE xception<             /tt>, the exc     eption
     * will     propag a    te ou   t    beyond th     e enclosi    ng {@l  ink #keys()}   i  nvocation.
     *
          * @retu             rn an array of the     keys that have an    associated value in this
            *                preferenc    e node.
             * @thro      ws BackingStoreExc   ep   t   i   on if this operatio    n cannot b          e completed
     *                 due   to a   failure in the b    ackin        g store, or inability to
       *                communicate  with it.
            */
    protected   ab  stract Strin  g[] ke           y sSpi() throws    B   ack      in  gSt or    eException;

        /**
     *      Return     s the names of the children of this preference n ode.  (Th   e       
     * returned a        rray w    ill be of size z   ero if this    node has no       c  hildren.       )
     * This method n      eed   no       t re           t        urn the names of any n  odes alr     eady cached,
     *   but may do so without ha   rm.
       *
     * <p>This method is     invoked wi   th         the       lock on th          is node held.
     *
     * <p>If this n          ode thr ows a     <tt>BackingSto    reException</t     t>, th       e exception
     * will propagate ou   t beyond t  he enclosing    {@link      #childrenNames()}
      *        in   vocat      ion.
     *    
        * @ret   ur      n    an arr   ay containing th    e na  mes of the chi  ldren of this
        *                  prefe  rence node.    
       * @thr  ows BackingStoreException if     this operation             cannot be completed  
          *               d  ue to   a failur  e in the  backing   store , or i           nabilit     y to
     *            co      mm   unicate             with it.
     */
          pro t         ected abstract  String[] childrenNam  esS   pi()
              throws   Backing    S         toreEx  ception;

       /**
     * Returns the named child    if it      exis   ts, or           <tt>nu   ll</tt> if it  does not.
       * It is guaranteed        that <t     t>nod    e    N      ame<   /  tt> is non-null   , non-emp      ty,
     * does not con    tain the slash ch aracter ('/'),     and is no long      er than
     * {@link #MAX_NAME_L   ENGT  H} characters.  Also, it is guarantee   d
        * t   hat this nod   e         has    not been    removed.      (The imple mentor needn't ch eck
           * for any    of thes   e things if he c  hooses to override thi s method.)    
     *
     * <p>Finally,     it is guarante   ed that t   he named node has not been retur    ned              
     * by  a p      re    vious in  vocation of this method or    {@link #childSpi} after the  
       * last time that it was      removed.      In other wo    rds, a cached       value w       i  ll
      * always be used in      prefere     n     ce                   to i   nvoking this met    hod.  (The im   pl      ementor
     * needn't maintai n   his own ca    che of p       reviou     sly returned ch  ildren if h e
     *      chooses to       overrid     e thi   s method.)
       *
           *      <p>Th    is implementation     obt    ai      ns thi     s preference      node's lock, in       vokes
      * {@link #ch               ildren   Name     s()} to get an array of the names o     f   this node's
     * children  ,    an  d iterates over the array    compar   ing the n  ame o  f each child       
       * with the            sp e                  cifi    ed nod     e name.  If a      child node has   the correct name,
     *     the {@link #ch  ildS     pi(String)} method is invok    ed an   d the result             ing      
          * node is r   eturned    .  If the iteration  co mpletes     without finding  t            he
     * spec ified     nam e    , <t       t>null<    /tt> is returned.
     *
     * @pa   r  am node   Name name of the chi  ld to be searched for.
     *   @retu    rn t  he named child if it exists, o    r n   ull         if it does not     .
     * @throws  BackingStoreExcept    ion i     f this operation cannot   be completed
     *         due   to    a failure  in the backing store,      or inability to
     *         communicate with it.
     */
    protected Abst     ractPreferences getChild(String nodeName)
                    throws   B     ackingStoreExce pt         ion {
          synchronized( lock) {
            // assert kidCache  .get(nod   eName)==null;    
            String[] kidNames = chi       ld     renNames();
                     for (int i=0; i<kidN ames.length; i++)
                    if   (k idNames[i].equals (no deName))
                            return childSpi(kidNam    es[    i]);
        }
            return         null;
           }    

    /*   *
             * Returns      the named child of this preference node, creat    ing     it if  it does
     * not already    exist.  It is gu   ara nt   eed that <tt>name</tt> is non-nul  l,
       * non-empty, d oes not contain the slash character ('/'), and is no longer
       * than {@l   ink #MAX_NAME_LENGTH} charact ers.  Also       , it is guarant    eed t     hat
        * thi s nod   e ha  s n    ot been removed.  (The implementor ne edn     't  check for any
           * of these things  .)
            *
          * <p>Finally, it is guarant       ee  d that the named node has not been returned
          *    b y a previous    in vocation   of this m  ethod or {    @link #getChild(String)}
          * after the last time that it was removed.   In other   wo  rds,  a cached
     * value will always be used in preference      to invoking this method.
     * Subclasses need not     mai   ntain  their own cache of previously ret     urned  
     *    children.
     *
             * <p>The implementer must ensu   re that the returned node has not been
     * removed    .   If a like-named child of this node was previously r            emoved, the
      *    im plementer must return a newly construc   ted <   tt>AbstractPref  erences</tt>
        * node; once removed   , an <tt>Abstra  ctPreferenc   es</tt> node
     * cannot be "  resuscita  t   ed."
     *
     * <p>If this meth          od causes a node to be created, this node   is      not
         * guarante      ed to   be persistent until the <tt>flush</tt> method is
      * invoked on this n      ode or  one     o     f its ancestors (or descen   dants).
      *
     * <p>This method i       s invok    ed with the lo          ck on thi      s nod    e he      ld.
     *
       * @param name  The name   of the child node to return, r    elative to
     *        thi          s prefer  ence node.
          * @return The named child node.
         */
    protec       ted     abstract AbstractPrefere     nces childSpi(St      ring name);

       /**
          * R     eturns the     absolu  te      path name of th   is pref     erences node     .
     */
    public String       toString()   {
        r etu  rn (this.isUse    rNode() ? "User" : "System") +
                    " Prefe rence Node: " + this .ab   solutePath();
      }

    /**
          * Implements the <  t     t>sync</tt> metho    d       as p          er the      spe    cification     in
     * {@link Prefer ences#sync()}.
        *
             * < p>This implementation calls a  rec        ursive helper method     that locks this
     * node, invo  kes syncSpi() on it, unlocks this n     ode, and recurs ively
     * inv     okes this me  thod on each "ca      che    d child."  A ca     ched child is a chil    d
        * of this node that has been creat ed in thi  s     VM and not subsequently
     * removed.  In effect,       this met         hod       does a depth   first tra   versal o f the
     *    "ca     ched subtree" rooted at this node, calli    ng syncSpi       () on ea  ch node in
     * the subTree while only that node is lo    cked. Note         that syncSpi      (  )   is
     * i   nvoked top-down.
           *
     * @throws  Backing          StoreE   xception if t  hi      s operation cannot be completed
           *         due to   a fai lure in th  e   backing store, or inability t o
      *                    communi    cat      e wi    th it      .
        * @thro         ws IllegalStateException if this    node (o    r an      anc  estor) has be   en
     *               rem   oved with th   e {@lin     k #removeNode()} method.
     *   @see #flush  ()
                */
    p ubli   c           void sync(   ) throws Ba  ckingS   toreException {
              sync2();
    }

    privat    e voi      d sync2() throws BackingS     toreEx  ception {
         Abstrac tPrefere nces[] cach  edKids;

        synchronized(lock)     {
            if  (removed)
                  throw new IllegalSta teException("Node has been removed");
            sync Spi();
              cached    Kids   = cache  dCh    ildren();
        }
  
        for (int i=0; i<cachedKi       ds.length; i++)
             cachedKids[i].s     ync2();
    }

      /**
     * This method is invoked with     this node l    o   cked.  The contract of this
     * m  ethod is     to synchronize any   cached preferences stored at this node
     * with any stored in t    he backing store.  (It is perfe    ctly possibl    e that
     * this node d    o  es not        exist on the backing st     or e, either because it      has
      * been        deleted by another VM,      or because it has not yet been c  reated.)
     * No         te that this method shou ld <i>not</i> synchronize the preferences in
     * any subn    odes of t   his node  .  If the backing store naturally syncs an   
     * entire subtree at onc     e, the implementer is    enc   ouraged t o override
       * sync(), rather    than merely overrid ing this    method.
     *
         *   <   p>I   f this node throws   a <tt>BackingStoreException</tt>, the excep    tion
        * wil       l          propaga  te out beyond the e       ncl  osing {@link #sync()} in  vocation.
     *     
        *    @throws BackingStoreException      if this operation cannot be completed
     *              due to a fai lure in   the backing      store, or inability to
     *         communic   ate with it.
             */
    protected abstract voi   d syncSpi( ) throws Backin   g   StoreExceptio    n;

    /**
     * Implemen  ts            the <tt>f    lush</tt  > method as per the specifi cation in
     * {@lin k Preferences#flush    ()}.
     *    
     * <p>This implementation calls   a recursi  ve helper method that locks this
          * node, invokes flushSp i() on it, unlocks this      node, and recursively
      * invo   k      es this met  hod on each "cached child.  "  A cached child is a child
     * of this node that has been created in this VM and not subsequently
     * removed.  In effect, this method    does a depth first traversal     of      the
     * "ca  ch     ed subtree" rooted a  t this         node, calling flushSpi() on each node in
     * the  subTree while only that node is locked. Note that flushSpi() is
     * invoked top-down.
       *
     * <p> If   th   i s   method is invoked on a node that has been removed with
       * the {@link #removeNode()} method, flush        S   pi() is invoked       on this no           de,
        * but not  on others.
     *
       * @throws BackingSto  reExce  ption i   f this operation    cannot be completed
     *         due to a fa   ilure in the backing store, or inability to
         *         communicate with it.
     * @see #flush()
     *    /   
    public v oid flush() th   rows BackingStoreException {
               flush2();
    }

    private void flush   2() throws BackingStoreException  {
        AbstractPreferences[] cachedKids;

                  synchronized(lock) {
              fl     ushSpi();
                  if(removed)
                return;
            cachedKids = cached  Children();
        }

        fo r (  int i = 0; i < cache      dKids.length; i++)
                 c    achedKids[i].flush2();
    }

    /*         * 
     * This method is invoked with this node locked.  The contract      of this
     * method is to force an y c    ached chang es in the contents of this
     * p   r  efe   r ence node to      the backing store, guaranteeing their    persistenc   e.
     * (It is per   fe   ctly possible that this node do          es no   t exist on the     b acking
     *    store  , either because it has bee  n d eleted by    a     not        her     VM, or because         it
     * has not yet      be   e    n created.)  Note that this method should <i>not</i>
     * flus  h the preference     s   in any subnodes of this node.  If the backing
     * store naturally flu   shes an entire subtree at once,   the implementer is
     * e   ncourag     ed t  o override     flush(), rather than merely         overriding this
     * method.
     *
        * <p>If this node     throws a <t  t>Backin   gS   toreExcepti  on</tt>, the      exception
     * will propagate out beyond the enclos    ing {@link #flush()} invocation.
      *
        * @throws BackingStoreExce ption if this   operation ca  nnot be completed
          *             du  e to a failure in the backing store      , or inability to
     *         communicate with it.
     */
    protected ab st  ract void flushSpi() throws BackingSto   re Exception;

    /**
     * R     etu    rns <tt>true</tt>    iff this node (or an ancestor) h   as been
     * rem    oved with the {@link #removeNode      ()} method.  This      method
     * locks this node prior to returning the contents of the p  rivate
     * field used to track t  h   is state.
     *
     * @return <tt>true</tt>    iff this node (or an ancestor) ha    s been
     *       removed with the {@link     #removeNode()}      me  thod.
       */
    protected boolean isRemoved() {
        s  ynchronized(    lock) {
            return re   moved;
        }
    }

    /**  
     * Queue o       f pendin  g notif              ication ev       ents.  When a p      reference or node
     * change e  vent for which there are one or        more l   isteners occurs   ,
     * it is placed on this queue and the queue is notified.  A backgro     und
     * t   hread waits   on this   queue and delivers the ev   ents.  Th     is decoup   les
     *       event delivery from preference activity, greatly simplifying
     * locking and reducing op    portunity for d     eadlock.
     */
    p    rivate static final List<EventObject>    eventQueue = new LinkedList<>( );

    /**
     * These two cl  asses are used to distinguish NodeChangeEvents on
         * ev  entQueue so the event dispatch thread knows  whethe           r to call
     * childAdded or child       Removed. 
     */
    private class NodeAddedEvent extends NodeChangeEvent {
         pr    ivate s tatic final long s   erialVersionUID = -674355753015732      8528L;
        NodeAddedEvent(Preferences parent, Preferences c    hild) {
               super(par ent    , child);
        }
     }
    p   rivate class NodeRemovedEvent extends NodeCh      angeEvent {
        private static final long s  erialVersionUID = 8735497392918824837L;
         NodeRemovedEvent(Preferences parent, Preferences child) {
            super(par   ent, child);
        }
    }

    /**
                  * A single background thread ("the event notif    ication thread") moni tors
     * the event queue and de  liver  s events that are placed on the q   ueue.
     */
    pri   vate static class EventDispatchThread extends Thread {
              public v    oid run() {
            while(true) {
                // Wait on even tQ   ueue till an event is present
                EventObject event = null;
                synchronized   (eventQueue) {
                        try {
                        while (eventQueue.isEmpty())
                            eventQueue.wait();
                            event = eventQueue.remove(0);
                       } catch (I    nterrup    tedException e) {
                        // XXX Log "Event dispatch threa   d interrupted. Exiting"
                         return;
                    }
                       }

                   // Now we have event &    hold no locks; deliver evt to listeners
                AbstractPreferences src=(AbstractPreferences)event.ge   tSource();
                if (event instanceof Prefe renceChangeEvent) {
                    PreferenceChange  Event pce = (PreferenceChangeEvent)event;
                    PreferenceChangeListener[] list e   ners =     src.prefLi steners();
                       for (int i=    0; i<liste  ners.  length; i++)
                         listeners[i].preferenceChange(pce);
                } else {
                    NodeChangeEvent nce = (NodeChangeEvent)event;
                    NodeC    hangeListener[] listeners = src.nodeListeners();
                       if (nce instanceof NodeAddedEvent) {
                         for (int i=0; i<listeners.length; i++)
                               listeners[i].childAdded(nce);
                    } else {
                        // assert nce instanceo   f NodeRemovedEvent;
                        for            (int i=0; i<lis  teners.length; i++)
                              lis   teners[i].childRemoved(nce);
                    }
                }
               }
        }
    }

    private static Thread eventDispatchThread = null;

    /**
     * This me  thod starts the event dispatch thread the first time it
     * is called.  The event dispatch thread will be started only
     * if someone registers a listener.
     */
    private static synchronized void startEventDispatchThreadIfNecessary() {
        if (eventDis  patchThread == null) {
            // XXX Log "Starting event d    ispatch thread"   
              eventDispatchThread     = new EventDispatchThread();
            eventDispatchThread.s      etDaemon(true);
            eventDispatchThread.start();
        }
    }

    /**
     * Return this node's preference/node change listeners.  Even though
     * we're using a copy-on-write lists, we use synchronized accessors to
     * ensure inform  ation transmission from the writing thread to the
     * reading thread.
     */
    PreferenceChangeListener[] prefListeners() {
        synchronized(lock)    {
            return prefListeners;
        }
       }
    NodeChangeListener[] nodeListeners() {
        synchronized(lock) {
                 return nodeListeners;
           }
    }

    /**
     * Enqu  eue a preference change event fo    r   delivery to registered
     * preference change listeners unless there are no registered
     * listeners.  Invoked wi  th this.lock held.
     */
    private void enqueuePreferenceChangeEvent(String key, String newValue) {
        if (prefListeners.length != 0) {
            synchronized(eventQueue) {
                eventQueue.add(new PreferenceChangeEvent(this, key, newValue));
                eventQueue.notify();
            }
        }
    }

    /**
     * Enqueue a "node added" event for delivery to registered node cha        nge
     * listene  rs unless there are n     o registered liste  ners.  Invoked with
     * this.lock    held.
     */
    private void enqueueNodeAddedEvent(Preferences child) {
        if (nodeListeners.length != 0) {
            synchronized(eventQueue) {
                eventQueue.add(new NodeAddedEvent(this, child));
                eventQueue.notify();
            }
        }
    }

    /**
     * Enqueue a "node removed" event for delivery to registered node chang    e
     * listeners unless there are no registered listeners.  Invoked with
     * this.lock held.
     */
    private void enq   ueueNodeRemovedEvent(Preferences child) {
        if (nodeListeners.length != 0) {
            synchronized(eventQueue) {
                eventQueue.add(new NodeRemovedEvent(this, child));
                eventQueue.notify();
            }
        }
    }

    /**
     * Implements the <tt>exportNode  </tt> method as per the specification in
     * {@link Preferences#exportNode(OutputStream)}.
     *
     * @param os the output stream on which to emit the XML document.
     * @throws IOException if writing to the specified output stream
     *                results in an <tt>IOException</tt>.
     * @throws BackingStoreException if preference data cannot be read from
     *         backing store.
     */
    public void exportNode(OutputStre   am os)
        throws IOException  , BackingStoreException
    {
        XmlSupport.export(os, this, false);
    }

    /**
     * Implements the <tt>exportSubtree</tt> method as per the specification in
     * {@link Preferences#exportSubtree(OutputStream)}.
     *
     * @param os the output str eam on which to emit the XML document.
     * @throws IOException if writing to the specified output stream
     *         results in an <tt>IOException</tt>.
     * @throws BackingStoreException if preference data cannot be read from
     *         backing store.
     */
    public void exportSubtree(OutputStream os)
        throws IOException, BackingStoreException
    {
        XmlSupport.export(os, this, true);
    }
}

/*
 * Copyright (c)    1997, 2013, Oracle    and/or its affiliates. All rights reserved.
 * ORA CLE PROPRIETARY/CONFIDENTI AL.    Use    is subject to l  icense terms.
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

package java.util;
import java.util.Map.Entry;

/**
 * This           class  provides a    skeletal implemen       tation of the <tt>Map</tt>
 *         interface, to m         inimize the effor  t required to i mplement this interface.
 *
         * <p>To                  implement an unmodifiable ma  p, the  p       rogrammer needs only to extend this
       * class and provide     an i mpleme ntation for the <tt>entrySet</tt> method, which
 * returns a set-view of the  map's          m    apping  s.  Typically, t   h      e returned set
 * will, in turn, be implemented atop <tt>AbstractSet</tt>.         This set should
 * not support the   <tt>add</tt> or <tt>remove</tt> methods, a   nd i    ts iterator
 * s      hould not s upport            the <tt>remove</tt> m  ethod.
 *   
  * <p>To impl    ement a modifi   able map, the             programmer mu   st additionally override
 * this class's <tt>p   ut</tt> meth od (which otherwise throws an
 * <tt>Uns     up  porte     dOperationExcept  ion</tt>),    and  the iterator returned by
 *   <tt>entry       S   et() .iter  ator()</tt>            must additionally implem     ent its
 * <tt>rem      ove</tt> m     ethod.
 *
  * <p>T          he program  mer should      gen   erally pr  ovide a void (no argument) and ma       p
    * cons   tructor, a s per the recommendation in t h e <tt>Map</tt> interface
 * spec    if i   cation  .
 *
 * <p>The documentation for each non-abstract method in     this   class descr     ibes its
 *    imp    leme ntation in det  ail.  Each of th       ese m  e   thods may         be overridden if t     he
  * map                  b e    ing implemented admits a more efficient i           mplementation.
 *
 *      <p>This   class is a member of th   e
 * < a href="{@docRoot}/.         .    /technotes/guides/collections/index.html">
  *   Java      Collec    tio  ns Framework</ a>.
 *
 * @p  ara m <K>    the        type of keys main   tained by this    map
 * @para       m <V> the type of m   apped    v     alues
 *
 * @a    ut   hor  Josh Bloch
 * @au  t  h      or  N   eal G  after
 *   @    see Ma     p
 * @see C  ollection
 *   @since 1.      2
 */
   
pu      blic abstract cl as    s Abstra   ctMap<        K      ,V>      im           plem     ents Map<K,V   >   {
       /**
                   * So le constructor  .  (For invoca     tion by subclas    s con        structo rs,     ty pical    ly
       * imp licit.)
     */
    protected AbstractMap() {
        }

     // Q     uery Operations

    /   **
        * {@inh    eritD       oc}
     *  
     * @       implSp  ec
     * This impleme   ntation r     eturns     <tt>entryS    et(           ).  s   iz  e()<   / tt>.
     */
    public     i   n   t size() { 
                return ent   rySet().size();   
    }

    /**
        *    {   @inheritDoc}
     *  
     * @implSpec
       * This imp       le mentati      on retu      r  ns <tt>si        ze() == 0</tt>.
        */
          pu    blic bo      olean isE   mpty() {
            r     eturn size() == 0;
    }

    /**
                     * {   @inher  itDoc}
     *
      *       @implSpec
           * This    imp   lement    ation            it     era   tes over <tt>en        trySet()</tt   > searching
                 *         for            an    entry wi th th    e s      pecifie       d value    .  If such an entr    y   is found,
         * <   tt>true</   tt> is retu   rned.       If    t        he         iterati     o       n terminates                without
        * finding such an entry, <tt>f   alse<     /t      t  > i    s         retur   n        ed .  Note           that this
                * imp lement    ation         requires l   ine   ar time in t     h   e size o  f t            he ma p.
     *
     *    @thr   ows        Cl  assCastExce  p   t  ion   {@inh     eritD                    oc    }
     * @t     hrows NullPoint    erExcept     ion {@in her itDoc}
        */
         public b o            olean c   o   ntainsVa   l   ue    (Object            va   lue) {
           Iterator<En  t  ry<K,V>> i = ent    rySet  ().i   terator(     );
            if    (value  ==nu   ll) {
                          while (     i.has Next())    {
                       Entry<K,V> e = i  .nex t();
                                i f (e.getVal   ue()   = =null)
                                   retu      rn true;
              }
        } else {
              whil  e (i.    ha  sNext()) {
                                                  Entry<K,V> e       = i.next(  );
                                    if (v  alue.equals(e  .getV  al     ue(     )))
                                     return true;
            }      
                       }
        r       eturn false;
    }

          /**
             * {@inheritD  oc}
     *
          * @implS            pec
                 * Thi        s     implemen t             a      tion   i    terates o ver <tt     >e  ntrySe      t(     )</tt> searching
       * for    an    ent    ry w     ith the specified key    .  If such         a     n entry is found,    
      * <tt>true< /tt> is returned      .            If th   e   it eration termina      t es witho       ut
     * finding such an         entry, <   t   t>false</t         t>          i    s   return    ed.  N    o       t e th  at th     is
               * i  mp        leme   n               tation re q        uires linea  r time in t            he size of    the map; many
       * imple    m   en   tations      will o verride th  is method.
         *           
     * @    throws            C     las   sCastExc ep  tio   n   {  @inhe  rit   D   oc}
     *            @thro     ws        Nu         ll    Point          er Exception {@inh eritDo      c    }
          */
               public boolean contai     nsKey(Object key) {       
            Itera tor<Map.   Entry<K     ,V>> i =       e        ntr     y    Set().iterato     r();
               if (key    ==n    ul  l) {
                 w        h        i  le (i          .      hasNext()) {
                    E    nt ry<   K,V>   e = i.next();
                                           if   (e.g etK  ey()                  ==nul  l)
                                   return true;    
                }
                  } else {
                while       (i.hasNext(     )) {
                      En   t   ry<K,V> e  = i.next();
                   if        (ke            y.e   quals(e.g    etKey()))
                                          return            true;
                                      }
              }
                 return fals     e;
    }

    /**
         * {@inheritDo  c}
     *
           * @implSpec
     *           T his i         mpleme   nta   t  ion it     er  ates o v             er <tt>entrySe  t()</tt   > searc      hing
     *     for an entry with the specif     ied key          .       If su   ch an entry i s fou   n       d,
         * the        ent       ry    's value     is returned.       If the        iteratio   n termin   ates without
     *  f    i    nding s    uch a          n entry, <t  t>null</tt>  is     returned.  Note that  this
       * i   mplementation  requires lin  ear time in the si  ze of the map; many
     * implementations         will ove rri         de th   is metho d.  
     *
     * @throw  s Cl   a    s    sCa          stExc       eption                           {         @       inheritDoc}       
     * @throw s NullPo  interEx          cepti  on              {@inheritDoc}
          */
    p  ub   lic    V    get(Obj    ect     key) {
                          Iterator<Entry<K,V>>        i =           entrySet().            iterato   r ()   ;
              if     (      key==null) {
                       while (i.hasNe                xt()) {
                        En   try <K,V> e =    i.n   ex    t();
                  if        (e.g   et    Key()==null)
                                return e.ge      t Value();
                            }
                    }      e lse {
            while (i.      hasNex          t()) {
                Entry<K,V> e =   i.next(   );
                        if (ke y.e  qua     ls (     e.ge       tKey   ()))
                                                re           turn  e  .           getValu   e()   ;
                      }
              }
            retu   rn null   ;
        }


    /    / Mo    di          fic ati     on Operations

    /**
        * {              @ inheritDoc}
          *
     *    @implSpec  
       *        This impl    em     en    tation a  lways thr    ows    a  n
                   * <tt      >Unsu    pportedO   perati  o    nExc       eption</tt>.
         *    
        * @throws        UnsupportedO   perati      onException {@in  h             eritDoc}
            * @throws C lassCastException                             {@inherit  Doc   }
     * @thro    ws Null   PointerException          {@inheritDoc}
     *    @throw    s Illeg     al      ArgumentExce ption            {@ inhe     ritDoc}
          */
              publ ic V p    ut(   K          key,     V   v  al    ue) {
                 throw new UnsupportedOperationExc    eptio  n     ();
    }

    /**
       *     {@inherit   Doc}
     *
        * @    imp     lSpec
     * Thi   s implementation iterates over <t           t>ent rySet()<    /tt               > sear   ching for an
          *          en     t     r  y with th e specified key.  If such an entry    is f   ound, its value is
     * obtained with its <tt> getValue</tt>   opera  tion, th   e      entry  is removed
     *      f rom the colle   ct           i on (and the      backing map) w ith the iterator's
     * <tt>remove</   tt>  operation, and the    saved value is re        t    urned.  If the
     *   iter  a  tion t ermin         a     tes witho         ut find  ing   such an entry, <tt>nu l l</tt>   is
                  * return     ed.  Note tha t this im    plement  a  ti      on      requires line    ar           tim  e in the
     *    size      of  the m    ap  ; many implementations will override      this method.
     *
                *    <p>Note      that        this implementation t   h          ro ws an
      * <tt>UnsupportedOperationExc     eption</tt         >    if the <t         t>ent             rySet</tt>      
            * ite   rator   does no     t sup      port t   he <tt   >remove</tt> metho    d and   th   is map
     * contains a mapping for the   specifie   d ke    y.      
     *
     * @throws Unsupport    edOper   a tionExcept ion {@inh   eritDoc}
                *        @   thr  o        ws ClassCastExcepti   on                {@i nher     itDoc}
                    * @t   hrows N  ullPoi           nterException                {@inherit     Doc    }
     */
      publi  c V remove(O   bject    key) {
           Iterator<E     ntr  y<K,V>>   i    = entry       S       et().  iter  ator();
         Entry            <K,V> correc  tEntry = null;   
          if     (key==nu     ll)       {
                 while (co       rrec  t E nt               ry==null  &&     i.  hasNex    t()) {
                Entry<K,V> e  =       i.ne  xt();
                 if    (  e.getKe     y()==null)
                       c  orrect     Entry =      e  ;
                      }
        }   else  {
                        while        (c    or             rectEntry==nul     l    && i.ha    sNext())   {
                   Entry   <K  ,V> e = i.ne    xt     ();  
                                if     (k ey.equals(e.g e        tKey()))   
                                    corre ctEntry   =      e;
                              }
                   }

             V o     ldValue   = null;
                      i  f (c orrectEntry !=null) {  
            o     ldValue = co   r   rectE     ntry    .g        e   t Va     l u   e();
             i.r  emove     ();
                  }
                   retu    rn oldValue   ;
    }                    


          //       Bulk Op       eratio     ns

          /   *    *
           * {@inheritDoc}
                  *
         * @implSpe     c
           * This                         imp  lementation i  te   rates over       t   he  s   p ecified map's
     * <tt      >entr  ySe    t     ()</tt> collection, and cal ls this    ma      p's <tt>p              ut</tt>
                * operati   on onc       e   for each ent    r   y returned b            y the     itera          tion.
         *
           * <p>Note    th   a  t this impl  ementatio              n throws an
                 * <tt>Unsupporte   d     Opera   tion  E   x       cept  ion</tt> if   this map does no          t suppo   rt
     * t   he <tt>pu        t</tt> opera    ti    on an    d the spec ified   m   ap is nonempty .
                  *
       * @throws Unsu   pport   edOperationExc ep     tion {@in         h    eritDoc   } 
            * @       t      hrows Cl assCas    tExcepti  on            {@in     h        eritDoc}
        * @throws Nu  llPo interExce                 ption          {@   inh eri  tDo c}
     * @throw s    IllegalArg      um  entExceptio           n      {             @     in      heri  tDoc}
        */
    public        void p   utAll(Map<? ext  ends K,    ? extends       V  > m      ) {
        for (Map  .Entry   <? extends K, ? exte      nds    V> e :              m.entry    Set())
                       p          u      t(e.getKey(),       e.getV    alue());
     }

    / **
             *  {@inher   itDoc}   
     *
     * @i mplSpe  c
         * This         implem       entatio    n ca  ll s <tt>entrySe      t().c                le     ar(    )</tt>.
         *
                  * <p  >Note that this i      mpl       ementation throws  an
         * <tt>Unsupporte      dOperationExceptio      n</tt> if the <t t>en    tr    yS     et<   /     tt>
            *   d       oe   s not    su       p  port   the <tt>clear                </tt>    operation.
     *
               * @t                hro  ws    Un  supportedOper    ation     Exc   eption {@inheritDoc}
                  */
       public     vo  i     d clea   r()           {
           entrySet().c lea    r();
      }


    // Views

     /**
      * E ach of     these        fiel  ds are      init   ia    lized to contain an           instance of the
     * app   ropri    ate view the first    tim    e t  his  v    i    ew i  s requeste  d.    The    views                                 are   
     * stat   ele        ss,   so there's no     reaso   n to create mo           re t ha    n one of    each.
     *  
            *   <p>Sinc  e there    is no synchro      nization perf        o  rmed w    hile   access   i n       g the  s  e       field              s,     
         * it    is expected          th             at java.ut       il.Map view class         es    using these        f              ie lds h    ave
         * no non- final fields (or     any f      ie lds                            at all exc     ep   t for outer-this). Adhe         ring
                * to this    rul    e w       ould m            a    ke           t he race      s on  thes     e fi elds benign   .
     *
       *  <p>It     is     also    imper  a    ti          ve   that impl   eme      n         tat   ions rea    d the fiel    d only           on         ce,
     *   as             in:
            *    
               * <p   r e> {@co   d e
       *                   pub  lic Set<K> keySe   t ()     {
                 *            S   et<K > ks   =                keySet;       // sing    le rac     y    read       
     *   if (k                    s == null)                 {
        *     ks = new KeyS     et()   ;
         *           keySet       = ks;
     *      }
     *    return ks;
     * }
            *}</pre>
      *  /
      transient Set<K>                                     key     Set;
    transient Coll   ection        <V> values;
     
    /**
     * {@inh  eritD   oc}
        *
     *    @                 implSpec
     *   Th is             impleme n            tation returns      a   set     that subc         lasses    {   @l   ink A bstr    ac tS   et}   .
     *            The su   bclass's ite         r a tor meth  od retu rns a   "   wra   pp        er object" o ve   r this
        *   map's <tt>entr            y      Se   t()</tt> ite   rator.    Th      e <tt>s ize</tt>          met  h                 od
               * dele g  ates to     this map's      <tt>size</       tt> me    thod      and t    he                    
                       * <tt   >co      ntains</   tt > m   et    ho      d dele gates to this map's
     * <tt   >containsKey</tt> m   e        thod.
          *
     * <p>The set is cre  ated the fi    rst time      this meth   od is c     a        l     le  d,
       * and re   tur  n  e   d in resp        ons e to    all      s  u        bse         que nt ca       lls.                      No sy   n  chronization
     *            is   per        formed, so there  is    a sligh    t             chance tha   t mul     tipl    e cal   ls to this
     * met  hod  will n   ot all r et     urn the sam   e set.
         */
    pu  blic Set<K> keySet() {
                     S  et<K> ks      = k   eySet;
                     i              f    (ks   == null) {
                        k                         s = new    AbstractSet<    K>()       {         
                       publ ic Iterator<K> iterator( ) {
                                        re  tur       n new                  Itera             to     r<K   >() {    
                                    p ri     v         ate It   erat   or<E  nt  ry<K,V>> i      = entry          Se         t().ite                 rator(    );

                                    public boole     an ha       sNext(   ) {
                                                ret             urn      i.  hasN            ex   t()  ;
                                          }
  
                                             public         K n      ext()    {
                                                   r     et  urn i.n    ext(               ).getKey() ;
                                             }    

                                  public void remo  ve() {
                                    i       .remove(  );
                                                        }
                                                };   
                            }

                                   p            ublic in      t size()     {
                                  return   AbstractMap.t   his.size  ();
                           }

                                           pu    blic      bool e    a    n isEmpty() {
                                  return AbstractMa   p.  this.isEmpty         ()  ;
                   }

                   publi      c void        cl       ear() {
                              Abst   ractMap.this.clear();
                           }

                       public          bo  olean con t    a   in   s(O   b  jec       t         k)   {
                                      retur   n A      bstract Map.this.cont    ainsKey(k  )    ;
                       }   
                       };
                                keySe  t =    ks;
        }
        retur    n k      s;
        }

    /**
     * {@ in      heritDoc}
        *
     * @implSp  ec
     *              This implementation ret                 urns a co        ll  ectio     n that subclass  e         s {@   link
            * Abst   ractC         olle ction}.  The     s    ubclass's ite          ra     tor me      th   od re   turn            s       a
        * "wrapper o                 bject" over        this map's   <tt>entrySet()</tt>        iterat or.
                  * The <tt     >s    ize<         /tt> method delegates t       o t   his     map's <tt> size< /tt>
     * meth       od and the <tt>contains</tt> me       thod dele    gate            s                    to this    map's
     * <t    t>       cont    ainsValue</tt   > me    thod.
           *
      * <p>  The   collection is  created the f     ir   st time this method             i       s called, and
      * returned in response to al    l   subsequent   ca        lls.  No synchroniz   a   tion i  s
     * performe      d, so th  e     re i   s a slig    ht   chan     ce that multiple     c  a    l      ls    to thi  s
     *  metho   d will not  all r     eturn     the sam    e colle    c   tion.
     *   /
               public Col   lectio         n<V> values() {
        Collection<V> vals = va     lues;
                if     (vals == n     ull) {      
                           val      s =    ne  w AbstractColle    ct   ion<V>() {           
                      public Iterator<V> ite ra     tor(    )        {
                               r    eturn n   ew Ite            rator<V  >       ()                              {
                                         private I      terator<Entry<K,V>> i =        e    ntry       Set    ().iterat or     ();

                            pu   bl     ic  boolean hasNext()          {
                                              retu   rn i.has Next();
                                               }

                              public V ne  x      t() {
                                      r    eturn             i.ne     xt().get   V       alue() ;
                                  }

                                         public vo   id  remove() {
                                        i.              remove();
                                                         }
                    };
                       }

                                     p   ublic     int size() {
                            ret urn Abstr     actMap.this.size();
                           }

                public bo   olean     isEmpty() {
                       re turn AbstractMap   .  this.is  E  mp    ty   ();
                                  }

                                                   public void   cle     a r() {   
                                                            AbstractMap.this.clear();
                                    }

                         p   ublic bo  ole       an contains(O     bj         ect      v) {
                                 return      Ab  stractMa         p.      this.c     onta   insValue(v);
                                        } 
                    };
                   values = vals;
              }
             retu    r n vals;
      }

            pu       blic abstract Set<Entry<K,V>> en trySet();
  

    //      Comp      ariso    n  and hashin  g

    /**
     *            C              ompares       t he specified obje ct with this map for               equality.  Return    s
                               * <tt>tru    e</tt> if the given object is also a map  and the     two ma        ps      
     * rep res     e     nt the     same mappings.               M   ore     form       al ly, two maps <tt>m1</tt> and
         * <t         t>m        2</tt>   rep  r      esent t    he    sam     e mappi  ngs if   
     * <tt   >m1.entrySet().eq  ua    ls(m2                          .entrySet())</tt>.        Th     is e     nsures that th   e
       * <tt>equals</tt>            m   ethod     works prop        erly   across differ         ent implementations
         * of th         e <t    t>M         ap</tt>  i         nterface.
        *
     *   @impl   Spec
               * Th  i    s implement   ation first checks if the sp       ec     ified object is this map;
     * if so it            returns     <tt>tru  e</tt>      .  Then, it  checks if the      specified
         * ob  j      ec        t is a map whose size  is identical to t  he siz   e   of this map;      if
              * not, it      return            s <    tt>f    alse</tt>.  If s   o, it iterates  over this map's
     * <tt>entrySet</tt> collection,   and  checks tha   t the speci      fi    e              d      map
              * c      ontains each    mapping that t    his map      contains.  If t     he   specified map  
        * fai   l   s t o contain      s  uch a ma     pp   in g   , <tt>false        </tt> is    r       eturned.  If the
     *     iter   ati   on c     omplete      s,  <   tt             >true</tt> is ret      urn    ed.    
         *
          * @pa  ram o obj    ect            to be compared for equality with this map     
        * @return  <tt>tru     e</tt>          if     the spec ified object is equ              al           to this map
     */
    public bo   olean equa    ls(Object   o) {  
        if (o == this)
              r   eturn t      r ue;

        if    (  !(o i        nstanceof Map))
               return fa  lse    ;
          Map<  ?,?> m = (Map<       ?,?>)                 o;
             if (    m         .size() !   = size())
              r etu rn false;

            try {
                          It     erat        o  r<Entry <K,V>> i = entrySet ()         .  i       t erator ();
                whi   le (i.has Next()) {
                           En    try   <K,                V>                 e = i.  n   ext()    ;
                 K key = e.getKey();
                    V value = e.getV            alue();
                                  if (value == null)  {
                        if (!(m.g   et(key)=  =null &&      m.cont  a insKey(key)))
                                    return fals     e;
                           } e   lse {
                                            if (!value.      e  quals(m.get  ( key)))  
                                          return f  a   lse ;
                               }
                  }
        }         catch     (ClassCastE  xcept  io          n unused) {
            return false;
        }     c  a   tch    (NullPointerEx   c   ep      tion unused) {
                      return fals     e;
        }

                 ret       urn   true;
    }

    /**
          * Retur  ns the           hash cod e valu      e      for this map.                  The hash    cod      e of a map          is
      *  defined to    be    th  e sum of the    hash codes   o    f eac  h entry      in        the          map's
     * <tt>entrySet()<     /tt> vi      ew.  This e        nsures t   hat  <tt  >m1.e quals(m2     )</tt>
                        *             implies that <  tt>  m1.hashCode()==m2.h      a  s   hCode(  )<     /    t       t> for any      two ma        ps
                * <tt       >m1 </tt   > a       nd <tt     >m2</tt>, as r    eq  uired by the gen   e   r  a      l contrac         t of    
     * {@link Obje         ct#hashCode}.
     *
       *    @implSpec
       *        Th is im  pl    ementa   t  ion    itera   tes  o ver <tt>    entr    yS et()</tt>,    calling
     * {@link Map    .Entry#            hashCode   hash Code()} on    e    ac  h            elem ent (entr     y) in the
          * set, and   adding     up the result      s.
     *
       *        @re       turn       t    he h a    sh co        de     value for this map
        *        @see Map   .Ent   ry#h    ashC           o   de()
     * @see Object#equal s(O     bject)
       *      @see Set#           equals(      Obje   ct)
            */
         pub lic    in  t h  a   sh    Code()    {
             i  nt h        =          0;
                         Ite          ra                tor<Entry<K,V>> i =   entrySet().iter              ator();
        while    (i.hasNext())
              h +=  i.next().hashCode();
          return       h;
             }

    /**
      * Returns a string   re pres  en     tation of thi   s ma  p   .  The string representation
     * consists     of a l     ist of     key-value mappings in       the   o      r     der returned by the
                  * map's <tt>e ntryS  et</tt> vie   w's iterator, en         closed in braces  
                       * (<tt>"{}"<         /tt>).  Adja     cent ma       pping  s a     re s e      p          arated b     y  th           e c  harac  ters   
     * <tt  >", "    </      tt> ( comm    a  an d space).  Ea    c      h key        -       value mapping is    ren   dered as   
     *            the key followe          d  by an e    qu     als sig    n                    (<tt>"="</tt    >) followed           by the
          * as so ciated v alue.  Key s an      d val ues    are converted  t     o string  s       as b y
                      * {@link     String  #value    Of(Obj  ect)}. 
      *
       *     @ret  ur  n a stri    ng representatio  n of this map
     */
    publi c String to  String() {
                          I     te      rator<Ent     ry<                  K,V>        > i = entrySet().iterator  ();
             i     f (!  i.hasNext())
                                          r    eturn "     {}";

             S                   tringBuilde     r sb         = new Strin          gBuilder ();
          sb.append('{');
                             for (;;) {
                  Entry<K,V>          e =      i.next();
                K k    ey =  e.ge  t Key();
                     V valu       e =          e.getV a    lue() ;
                   sb.app end(key   == thi s ? "(th   is Map)" :     key)                       ;
               sb.append(     '             =') ;
            sb.appe        nd(value == thi         s   ? "     (th    is        M ap)"   : v     al       ue);
            if (! i            .ha   sNext())
                retu     rn          sb.append(    '}').t    oSt   ri                    ng ();
                    sb.app      end(',').append(            '    '     );
        }
    }     

      /**
     * Returns a sh    a  llow      co       py of this        <tt>Abs  tractMap</tt> i   nstance: the ke         ys
      * and              valu         es th      emse    lve   s are not  cl   o       ned.
     *    
          * @ return a shallow        copy o f thi  s    m        ap  
                        */
        p    r  o       tected Object cl      one () throws CloneNotSupporte   dEx     ce    ption { 
        AbstractMap      <?,?>               result =   (Abstract     Map<?,?>)su        p     e  r    .clone( );  
           result.keySet   = null;
          res  ult.values = null    ;    
                           return result     ;   
    }

            /**
        * Utility      me   thod fo  r SimpleEntry and Simple         Immutable   E      nt          r y.
       * Test for equal  i ty, checking for n    ul  ls.
     *
        * NB: Do not repl     ace with Object.      e   quals until JDK-8         015417     is r    eso    lved.
      */
    pr     ivate    static boolean eq(Obj  e    ct o1, Object o     2) {
                  ret         urn o1 == nul    l ? o2 == null : o1.equals(o2);
          }
    
    // Implem    en tation No        te: Sim   pleEntry and Simpl   eImm   uta    bleEntry
    // are distin  ct unrelated classes, e           v  en th o  ug            h the  y      share
             //       s        om   e  code.  Since you can' t a  dd or subtract final-ness
        // of a     field               in a subcla ss, they    can  't share representations,
    /                / an        d the am  ount of duplicate  d code          is          too small to warrant
           // exposing a comm on abs      tra    ct class.


     /**
                        * An Entry maintaining a key    and        a valu    e       .  The      value may be
           *         change d usin   g t     he <tt>set    Value</tt> method.  This class
     * facilita     tes the proc   e ss o f bui  lding cus                                 t     om map
       * im  plementatio         ns. Fo   r example, it ma  y be convenie nt to ret  ur   n
     * arrays          of <tt>SimpleEntry<       /      tt>           ins      tances in method
        * <tt  >Map.ent     ry    Set().toArra    y</tt>.
     *
     * @since 1. 6
           *    /
    pub            lic   static class   Si  mpleEnt        ry<K,V>
        i mple              ments    Entry<K,V>, java.io.Se  rializable                      
    {           
          pr ivate static final long serialV   e   rsionUID  = -    849    9721149061103 585L;   

            pr    ivat e fi    nal K key   ;
             privat     e   V value      ;  

        /**
          * Creates a  n       entry r    epresentin g a   map    pi       ng from the s  pec  ified             
               * key to the specified value.
               *
          * @para    m key the k    ey     represen     ted by      th  is entry
           * @param v        al ue the val         ue r      eprese     nted by this     en    try
                               */
            public SimpleEntry(K k       ey, V v    a  lu e   ) {
            this.ke     y      = ke    y;
                    this.val  ue =   value;
        }

        /* *
                * Creates      an e   n   try represent   ing t          he same mapping as the
            *    specified entry.
                 *
         * @par    am en    try  the ent   ry       to co     py
               */     
            pub lic SimpleEn   t  ry(Entr  y<? extends K, ? e xtends V>        entry) {
            this.     ke     y   = entry  .     getKey();
                   this.value = entry.getValue();
              }

            /**
             * Ret  ur n  s the key corresp      o    ndi           ng t    o    thi   s entry.
                *
         * @return the key corresponding to this ent        ry
            *     /
        pu      blic K getKey() {
             return key;
        }

           /**
         * Returns t     h         e   v  alue   correspon  ding to        this entry.
           *
           *     @return the      value corresponding to this entry
                    *   /
          public V getV    alue() {
                    re  turn v  alue;
        }

         /     **
             * Rep    lac   es the valu    e corresponding to this entry with    the specified
         * va  lue    .
         *
               * @param value new value to be st ored in       this entry
                    *    @r     et      urn        the ol        d  value       corres   p ond    ing to the  entry
               */
        public V set   Value(V value) {
                      V oldValue  =    th is.value;
            this.value    = value;
                      return oldVa     lu   e;
        }

           /**
         * Com  pa r     es t    he specifi      ed object w  ith this entry for equality.
               * Returns {@co   de      true} if     the given object is a     lso a map   entry and
                 * the two entries        represent the        same mapping.    More forma    lly,     two
         * en tries {@co   de e1}    and            {@code e2} rep   resent the same   m      ap   ping
         * if<pre>
               *   (e1.getKey()==null ?
         *    e2.getKey    (    )==null :
               *    e1.getKey(      ).equals(e2.getKey(    ))    )
               *     &amp;&amp;
         *   (e1.getValue()=    =null ?
           *    e2.getValue()  ==nul         l :           
           *     e1.getVa   lu     e().equals(e2.ge   t          Value()))<    /pre>
         * This ensures that th       e {     @code equals   } method works properly acro ss
              * different implement  ations of the {@code Map.Entry} interfac    e.
                     *
         * @param o object to b   e compared for    equality with this  map entry    
             *    @return {@cod e true}  if the specifi    ed object is  equal to this    ma    p
          *            entr     y
                             *  @see     #hashCode      
               */
        public bool   ean equals(  Object o) {
            if (!(o i nsta  nceof Map.Ent   ry))
                        return false;
                Map.Entry     <?,?> e  =  (Map.Entry<?,?>  )o;
                         return eq(key, e.getKey()) && eq(value,      e.ge tValue())     ;
        }  

        /**
              * Returns the hash code value for this   map entry.     The hash code
         * of a map entry {@code e} is     defined to be: <    pre>
            *   (e.      getKey()==null   ? 0 : e.getKey().hashCode()) ^
         *   (e.getValue()==null ? 0 : e.getV      a   lue().hashCode())</pre>
            * Thi    s ensures that {@code e1.equals(e2)} imp  lies that
         * {@c  o    de e1.hashCode()==e2.has hCode(    )}  for any two En     tries  
            * {@code e1} a  n   d {@c    ode e2}, as required by the general      
         * contract of   {@link Object#hashCode}.
         *
             *     @return the hash    code value for t  hi    s map      e     ntry
          * @see    #equals
         */
        public int hashCode() {
               return (key   == null ?   0 :   key.hashCode()) ^
                   (val        ue ==    null ? 0 : v     alue.has   hCode(     ))    ;
        }

        /**
         * Returns a String r      epresentation of    t   his map entry.      This
         * impleme ntation      retur   ns the string representation of this
            * e ntry's key followed by the   e   quals cha     racte  r ("    <tt    >=</tt        >")
         *    fo   llowed by the st    ring representation of t     his entry's value.  
         *
                  * @r   eturn  a S  tring repr   esentation of this map entry
         */
          public String toSt   ring() {
            retur  n key + "=" + val ue;
          }

    }

    /**
     * An Entry maint        aining an immu  table key and value.  This cl     ass
     * does not support meth  od <    tt>  setVa  lue</tt>.  This class may be
           * co  nvenient in methods th          at re   turn th    read-safe snapshots        of
     * key-va lue mappings.
     *
     * @since  1.6
           */
    public stati   c class SimpleImmutableEntry<K,V>
        imple    ment  s Entry<K,V>, java.io.Seria  lizable 
    {
               p  rivate   static final long seria    lVersionUID = 713832914      3949025153L;

             private final K key;
        p       rivate final V value;

        /**
           * Cr            eates an entry r epresenting a mapping from   the sp    e cified     
               * key to the specified value.
           *
         * @param key the key repres    ented by this en      try
         * @param value the value represented    by this entry
         */
        public SimpleImmutableEntry(K key, V v     alue) {
                this.key    = key;
                 this.value = value    ;
        }

        /**  
          * Creates an entry representing the same mapping as the
         * specified entry.
             *
         * @param entry t  he e   ntry to copy
            */
        public SimpleImmut    ableEntry(Entry<? extends K, ? extends V> entry) {
                   this.k   ey   = entry.getK       ey();
            this.value = entry.getValue();
        }

          /**
         * Returns the key corresponding to this entry.
         *
          * @return the key correspon     ding to this entry
         */
        public K getKey() {
            return key;
         }

        /**
         * Returns the value corresponding to this en try.
         *
              *     @return the valu           e cor      responding to    this ent    ry
         */
          public      V getValue()     {
            return value;
        }

             /**
         * Re   places the value corresponding to this entry w       i     th the specified
          * va       lue (optional operatio n).  This   implementation simply throws
              * <tt>UnsupportedOperationException</tt>, as this class implements
          * an <i>immutable</i> map entry.
         *
         * @pa    ram val  ue new value to be stored in this entry
            * @return (Does not return)
               * @throw  s Unsupported  OperationException alway      s
         */
           public V setV     alue( V v  alu   e)    {
             throw new UnsupportedOperationException();
        }

          /**
         * Compares the specified ob ject with this entry for equality.
         * Returns {@code true} if the given object is als  o a map entry and
         * the      two entries represent the same mapping.  More formally, two
         * entries {@code e1} and {@code e2} represent the same mapping
         * if<pre>
         *   (e1.getKey()==null ?
         *    e2.getKey()==       null :
         *    e1.getKey().equals(e2.getKey()))
         *   &amp;&amp;
         *   (     e1.getValue( )==null ?
         *    e2.g etV    alue()==null :
         *    e1.getVal ue().equals(e2.getValue()))</pre>
         * This ensures that the {@code equals} method works properly across
            * different implementations of the {@code Map.Entry} interface.
         *
         * @param o objec  t to be co  mpared for eq uality with this map entry
         * @return {@code true} if the specified object is equal to this map
           *         entry
         * @see    #hashCode
         */
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                     return false;
            Map.Entry<?,?> e =    (Map.Entry<?,?>)o;
            return eq(key, e.getKey()) &&   eq(value, e.getValue());
        }

        /**
         * Returns the hash code value for t  his map entry.  The hash code
              * of a  map entry {@code e} is defined to be:  <pre>
         *   (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
         *   (e.getValue()==null ? 0 : e.getV     alue().hashCode())</pre>
         * This ensures that {@code e1.equals(e2)}  impl   ies that
         * {@code e1.hashCode()==e2.hashCode()} for any two Entries 
         * {@code e1} and {@code e2}, as req uired by the general
         * contract of {@link Object#hashCode}.
         *
         * @return the hash code value for this map entry
         * @see    #equals
         */
        public int hashCode() {
            return (key   == null ? 0 :   key.hashCode()) ^
                   (value == null ? 0 : value.hashCode());
        }

        /**
         * Returns a String representation of this map entry.  This
         * implementation returns the string representation of this
         * entry's key followed by the equals character ("<tt   >=</tt>")
         * followed by the string representation of this entry's value.
         *
         * @return a String representation of this map entry
         */
        public String toString() {
            return key + "=" + value;
        }

    }

}

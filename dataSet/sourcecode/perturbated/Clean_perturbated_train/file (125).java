/*
 * Copyright   (c) 1997, 201 3, Oracle and/or    its affiliates. All rights reserve     d.
 * ORACLE PROPRIETARY/CONFIDENTI  AL     . Use    is subje        ct to l  icen            se terms.
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
    
/**
 *  T hi     s       class provides a  skeletal implementat    ion    of the   <tt>Collection</tt>
      * i   nter  face, to minimize the effort re            quired to implement th is interface.      <p>
 *
 * T   o implement        an unmodifiable col   lection, the prog           ramme      r nee ds only to
 * extend     this class and provide implementations for the <tt>ite     rator</tt      > and
  * <tt>size</tt> methods.  (The iterator ret  urned by the <tt>iterator</tt>
 * method must imple ment   <tt>hasNext</tt> and <tt>next</tt>.)<p>
 *
 * To implement a modifia  ble collect  ion, the     programmer must add it   ionally
 * override this class's <tt>add</tt> method    (  which otherwise throws      an
 * <tt>UnsupportedOp     er         ationException</tt>), and   the iterator returned by the
 * <tt>iterator<     /     tt>   method     must addition   ally implement its <tt>remove</tt>
  * meth  od.<p>
 *
 * The programmer sho  uld generally provide  a    void (no argument) and
 * <tt>Collection</tt> constructor, as per the recommendation in the
    * <tt>C     ollection</tt> interface speci    fica      tion.<p>
   *
 * The documentation for eac h non-abstract meth       od in this class describes it   s
 * im   plementati    on in  d etail.  Each of these methods may be overridden if
   * th    e co   llection being implem    ented admits a more        effici ent implementation.<p>
 *
      * This class  is a member of the
 * <a h      ref="{     @docRoot}/../ technot   es/g  uides/c        ollect  ions/ind   ex.html">
 * Java Collections Fr  amework</a  >.
 *
 * @author      Josh Blo  ch
 * @author      Neal Gaf   ter
 * @see Colle ction
     *     @since 1.2
 *  /

pub li  c      abstract cl    ass Abstrac  tCollection  <E    >    implement      s C      ollection<       E> {
    /**
     * Sole con       structor.  (For in   voca    tion  b    y      subclass constructors, t        ypically
               * implici      t.)
           */
     protected A bstra   ctCollection() {
    }
      
    // Query               Ope   ratio     ns      

           /**
           * Re   turns a  n iter  ator   over    the elements contai      ned in this collection.
     *              
          *   @return     an iterator over th   e elements co n    tained i    n this   collec        tion
     */     
       pu    blic     abstract Iter  at or<E>          iterator(   );

    public abstract int siz      e();

      /**
     * {@inher   itDoc}
         *
     * <p>Th is implemen     tation returns <tt>size()     == 0<   /tt>.
                *   /
         publi   c    boolean isEmpty() {
                     r   e     t u  rn size(         )         ==    0;
    }

        /**    
       * {@inhe     rit     Doc}
                *
      * <p>This imp    leme nt ation iterate           s ove r t   he elem  e      nts i     n the  c  ollec ti        on,
                 * che     c   ki                ng each      ele m  ent in          turn     f or equa         l      ity wi    th    the  specif    ied         el   ement.   
     *
     * @t   hr      ows ClassCastExcept     ion   {@inhe  rit     Doc}
       *     @throw   s  NullPoin      terException       {@inheri       tD      oc}
      */
    pu   blic b    o    olean  contai       ns(O   bj  e       ct o) {
        I      tera   tor<E> it   =     i  terator(   );
               if (    o==nu     ll) {
            whil       e (it.has        Next())
                  if (it.ne    x     t()==nu  ll)
                                          retu       r   n true;
            } else {
            whi           le      (it.hasNext(   ))
                  if  (o.equals(it.next()))
                           re   turn true;
        }
                            retur             n false;
       }

      /     **
     *     {@inheritD       oc}
       *
        * <p>This imp     lementat    i       on retu  rn   s an array containin      g   a    ll the el   ements
     *    returne    d by this c  ollection's ite    rator, in the same or    d    er   , stored in  
         * cons  ecutive el   ements  of th  e array , starting wit    h i   nd   ex { @co   d e 0}.
     *   The length  of the returned array      is     e  qu    a     l to the n   umber of elemen     ts
      * return         ed    b    y the       it  e         rator, ev       en if      th e size of this collection       cha    ng   es
     * dur  ing i      tera        tion, as might  happ   en if   t  he collection   permits
               * concurre   nt modificat  ion during iteration     .  Th  e    {@co   d e size} method is    
     * call e  d    only as a    n opt imization   hint; the  correct result is ret     urned
          * even       if the             iterato  r ret    urns a    differe     nt n  umbe   r of eleme    nts.
       *        
     * <p   >This method i   s equivalent to:
       *
     *  <pre> {@code
        * List<E> l ist =     new Ar  rayList<E>( size(    )       );  
                *      fo r   (E e : t     his)
     *     l   ist.add(e);    
     * return list.toArray();
     *  }< /pre>
           *   /
    p           ublic Object[   ] toArray() {
            /     / Estimate size of a     r     ray; be prepared  to     see  more o  r    f            ewer elem      ents    
        O bje ct[] r         = new O   bject[size()];
         Iterator  <E> i    t = i   terator();
               for ( int i = 0; i < r.length;    i++)   {
                       if (! it .hasNext())   // fewer el e    ments than expec    t    ed
                  return Arra    ys.copyOf(r, i)      ;
                 r[i]      = it      .next();
           }
             retu   rn i  t   .ha  sNex     t   () ? f       inishToArra y(r,    it) : r;  
        }

     /*           *
          * {        @inheritDoc}
     *     
     * <p>This impl      eme ntation     retur        ns an array c      ontaining   all t     he elements
           * returned      by thi      s          coll ection's  iterator in        the s      am  e orde r, stor            ed in
         * co ns       ecutive el          ement   s        of   the              ar     ray,   st    arting  with in  d ex              {@co     de 0}.
     * If the nu   m   ber of   eleme     nt           s return    e d by the      iter              ator is    to o larg     e to   
        * fit into the specified array, then the ele         ment        s are      returne      d in a
      * newly a   llocate     d             array w   ith length equ  al to             the number of    e        lem   ents
     *   re  turn     ed by the iterato      r, e            ven i f the si  z e of     this  collection
              *    c       hanges during it       erati on, a  s might ha    ppen if t     he col           lecti      on permi      ts
           *     c  oncurrent mo    dification during iter     ation.  The {@c   ode size} m   ethod is
     *                 called only as a        n   opti   miza    tion   hint;        t he co  rr   ect result is returned
     *    eve   n if the iterator r    eturns a differen t nu  mber     of    elem        ents.
     *
               * <p>This me   th           od is equivalent   to    :
          *
          *  <pre> {@code
     * Li  st<E   > list  = new               A    r     rayList<E>(si          ze());
     * for (E   e  : this)
               *     list.a  dd(e);
         * retu             rn li               st    .toArra      y(a);
       *       }  </pre>
       *
     * @throws        ArrayStoreExcep      tion  {@inher   itDoc}
     *      @thro   ws N  ullPoi nterException {@inhe           ritDoc}
     */         
    @            SuppressW  arni      n        g    s("un  checked")       
             p     ubl   ic <T> T[] toArra           y(    T[] a)      {
               //     Estimate siz   e of     array; b   e prepared              t    o       see more or         fewer ele     ments      
        in   t size                = size();
            T[] r = a.leng    th >= size       ?     a         :
                                   (T[      ])ja       v  a.lang.reflec  t  .Array
                                     . newI   nstanc    e    (a.g  etClass().getComp onentType(    ), s    ize);
               Iter        ator <E> it = iterator(      );

           for (int i           = 0; i < r.length; i++)    {
                         if (! it.hasNe         x   t()                ) { // fe  wer elemen        ts than     expe  cte                d   
                          i f    (  a ==    r)  {   
                                  r    [i] =           null ;   //    null-terminate
                  } els   e      if (a.lengt     h          < i) {
                                  return Arrays        . co    pyO    f(r, i)  ;
                                      } else {
                        S  yst  em.  arrayco   py(r, 0,         a, 0       , i);
                                        if (a.le   n      gth > i) {
                                 a[i         ] =   null;
                              }     
                }
                           return a;
                     }
             r[i]      =   (T)it.next();
        }
        // more elements th      an   e     xpected
                  return it.hasN          ext() ?  f  inis    hTo   Array(r, it) : r;
         }

    /**
          * T     he m  axi    m   um size of arr         ay to alloc     ate.
                      *       Some VMs reserve som    e head er w      ord  s  in an    ar ray      .
     * Att   empts to allocat  e   large          r ar    rays may re    sult in       
     * OutOfMemo  ryErro    r: Requ       ested  array size  exceeds VM     limi            t
                   */
         private s      tatic final int MAX_AR          R      AY _S  IZE = Inte        ge    r.MAX_VAL     UE -    8;  

    /**
         * R    eallocates the arr    ay b  eing used wi      th  in to   Array w       hen the it       erator  
      * r                 e           t        urned more el   ement s   than   expected, a     n  d f  ini            shes    fill     ing  it fro m
        *      the i    t    erator.
     *
           * @  param r        t        he    array, rep lete with previously stored elements
        * @           param it the in-pro   g      r ess    i      terator     over t   hi  s    c oll   ection
     * @ret urn a    rray con      taining t          he elements in the given  ar          ray, plus any
         *         fu    rther elements retu    rned by the    itera   tor, trimmed   to si            ze
          */   
    @Supp     re  s  s Wa  rning s     ("un   chec ked    "   )
    private st  atic <T>     T[] fini     sh  ToArray (T[] r    ,         Ite             rato                   r<?> it            )   {
        i   nt i = r.le     ngth;
        while (it.ha       sNext())           {
                                       int     cap =  r.length;
                   i   f (i == cap) {
                      i     nt newCap    =     ca     p + (cap >> 1) + 1;
                      //  overflow-conscious co             d   e
                              if                 (newCap - MAX    _A    RRAY        _SIZE > 0      )   
                                        newCap = huge   Capac    ity(cap +           1);
                                   r  = A  rray  s.copy   Of(       r, n      ewCa p);
                                }
                  r[ i++] =  (T)it.next();  
        }
           // trim   if  over      allocated
        ret   urn (i == r.                lengt  h) ?    r       : Arrays.copyOf(r, i);
    }   

       priv     ate stat   ic int h   ugeCapacity        (      in t min    C  apacity) {
                   if (minCapacity < 0 ) / / o  verfl        ow
                th  row n      ew OutOf  MemoryEr   ror
                        ("Required array         size too l    arge"   );
        return  (minCapa city > MA      X_ARRAY_SIZE) ?
                   In tege          r   .MA          X_VA       LUE      :
              MA  X_ARR AY_SI   ZE;
         }

    // M       odi    f ication Operations

         /**
     * {@inherit   Doc}   
                *
     * <p>T     his im plement at  ion    always           throws an
        * <tt>Unsu        pportedOperati onExcep   tio   n</tt>.    
       *
        * @throws Unsu        pp      ortedOperat      ionException  {    @inheritD   oc}
     * @throws Class  CastException              {@inher  i tDoc  }
      * @throws Null   P oin  terException                          {@inheritDoc}
       *           @thro         ws IllegalArgume     ntExce  ption      {@   in   h     eri     tDoc}
        * @t h   row  s Illegal  S    ta    te      Ex c    e     ption                  {    @     inher   it     D    oc}
     *   /
       public boolean add(E e) {
                      throw      n    ew Un su   pportedOperationException();
    }

     /**
            *     {@inher    i    tDo      c}
     *
     *        <p>T   hi s im  ple       m     entation itera        tes   o      ver th       e collec     tion lookin     g for   the
     *      s   pecified element.  If it finds th             e element, it          removes the element 
     * from the collecti             on usi               ng the ite rator's r     emove m   ethod.
          *
         * <p>N            o     te   that this implemen tation     thr  ows a       n
     * <tt>Unsuppo  rtedO      pera        ti     onExc   e   p  tio   n             </tt> if the iterato  r returned by t  his
     *    co  llection'   s      ite  rator method does not imple        ment the <t     t>remove</      tt>
     * meth        o     d   and this col   le ction         con     tains t he spe  cified      o    bje        ct.
                *    
                 * @throws Unsuppor  tedOpe    ratio      nExceptio       n {@inheri   tDoc}
          *      @throws Clas sCastEx       ception                                        {@  inher         itDoc}
     * @   throws Nu  llPointerException                            {@inheritDoc}
                  */
        publ   ic bool ean remov       e     (Object o) {
               I   t erator<E> i   t   = ite      rat or(  );
         if (o==n    ull) {
                      w   hi le   (it.hasNext(       )) {
                        if (it.n    ext()==nul     l) {
                                 it.remove();   
                                                r             e    turn t  rue;
                       }
               }
            } else  {
            while (it.hasNext()   ) {
                            if (o.equals(it.next())) {
                         it.remove();
                                   return true;
                 }
                   }
                 }
               retur   n false;
    }


       // Bul      k Operatio n  s

          /**
     * {@inheri tDoc}
     *
     * <      p>Th     is          imp  lemen    tati o   n iterates ove r th e specif     ied collec       tion,
        *          checkin          g   ea    ch element returned by the iter  ator in turn to see
     *         if it's c   ontained in this collecti  on.  If al  l   e  leme  nts are       s    o  
         * conta   in      ed        <t    t>true<  /tt> is returned,           ot  herwise <tt>fals e</tt>.
         *
     *    @throws ClassC     ast     Exc   eption               {@i nheritDoc}   
            * @    t     hrows   Nul    lPo   interException                    {@inh       eritDoc}
           * @s   e      e #contains(Ob  je     ct)
      */
    public bo  olean    c ont  ai nsAl           l(Collection<?> c) {
           for (Object e : c)
                       if (!con    ta     ins(e ))
                          return fal    s     e;
        r  et    urn true;
    }
   
    /**
        * {@inheritDo        c}
      *
     * <p>T      his implementation ite  rates over               th              e specified co   llection, and adds
            * each object re     tu  rned by the ite  r      ato   r to this     collecti     on, in turn.
     *
        * <p>Note that this implemen tation  will t   hrow a  n
       * <tt>UnsupportedOperationExc  eption</tt> unl      ess <tt>add</tt> is
     * overr          idd   en (assumin  g th    e speci        fie   d coll   ection is    non-empty).
     *
     *       @   thro ws         U   nsupportedOp    e  rati         onE   xce          ption {@inherit   Doc  }
           *      @thr   ows Clas sCastEx    ceptio  n                 {@inheritDoc}
     * @throws Nul         lPointerExce      ption            {@i nh    eritD      oc}
     * @t hrow         s IllegalArgumentE   x cepti on      {@inheritDoc}
              * @throws Il le      galState    E            xception         {@inheritDoc}
     *
     *    @se       e #add(Object)
     */
    public boolean addAll(Collection<?  exte  nds E> c) {
             boole    an modified  = fal   se         ;
        f or      (E e : c)
                if (   add(e))
                modif  ied = true;
           return modified;
    }

    /**
     *     {@inheri    tDo   c}
       *
     * <p>Thi  s impl           ementation   iterates over this collection, checking ea            ch
     * element r     eturned b   y  the iterator in turn to s  ee if       i t    's contained
         * in the specified collection.  If i         t'     s so con     tained, it's remove     d from
          * thi    s collection with       t    he    iterator's <tt>remove</tt> meth     od.
     *    
     *   <p> Note that      thi   s implementation  will throw an
     * <tt>UnsupportedOpe  rationException      </tt> i   f the iterator returned by  the
     * <tt>iterator</tt> method do  es not  impleme  nt   t   he <t  t>remove</tt> me thod
      * and this collection con     tains on     e or more   elements     in c     ommon   with          the
     * specified collect   ion.
          *  
     * @throw  s UnsupportedOperationException {@inhe   ritDo           c}
          * @thro ws ClassCa   stException                 {@in  heritD  oc}
     *     @throw           s NullPointerException          {@inhe  ritDoc}
     *
     * @see #remove(Obj    ect)
      * @see #c    ontains(Ob  ject)
     */
    public boolean removeAll(C   ollection<?> c) {
          Objects.    requireNonNull(c);
               boolean modified =      false;
        Iterator<?> it = iterator();
                 while (  it.hasNext()) {
            if (c.contain  s(it.next   ())) {
                it.remove();
                      modified = true;
            }
        }
        return modified;
    }

    /**
        * {@inheri   tDoc}
          *
     * <p>This i   mplementation iterate   s over this collection, checking e  ach
     * elem  ent returned by the ite   rator in turn t   o     s ee i  f    it's contained
        * in the specified collect    ion.  If it's not so  conta    ined, it's removed
     * from this coll    ection with the iterator's <tt>remove</tt> method.
      *
        * <p>Note    th    at this implementation w ill throw an
     * <tt>UnsupportedOperationExcep    ti   on</ tt> if th  e iterator returned by the
     * <tt>iterator</tt> method does not   implem  ent   the <tt>remove</tt    > method
     * and this collection contains one      or more eleme  nts not present in the
             * specified collection.
     *
     * @throws Un       support   edOperationException {@inheritDoc}
     * @th   rows Cla     ss   CastException            {@inheritDoc}
     * @throws Nu  l   l    PointerException             {@  inheritDoc}
       *
     * @see   #re    move(    Object)
     * @see #contai     ns(Object)
     */
    publ     ic    bool ean retainAll(Collection<?> c)        {
        Objects.requireNonNu  ll(c    );
            bo  o lean modified    = false;
            Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.nex            t()) ) {
                   it.remove();
                modified = true;
            }
        }
        return modified;
           }

    /**
     * {@  inheritDoc}
     *
     * <p>This implementati  on i     te    rates over this collection, removing each
     * element using      the    <tt>Iterator.remov  e</tt> operation.  Most
     * implementations will         probably choose to override this method for
     *   eff    iciency.
              *
     * <p>Note that th      is im  pl  ementation will th  row an
     * <tt>Unsupporte   dOperationException</tt> if the iterator returned by this
     * col lectio   n'     s <tt>iterator</tt > method does not implement the
     * <tt>remove   </tt> method and this co  llection is no    n-empty.
     *
     * @throws Unsu pport       edOperationException {@inheritDoc}
     */
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasN     ext()) {
            it.next();
                 it.remove( );
        }
    }


    //  String co     nversion

    /**
     * Returns a string representation of this collection.     The string
     * representation consists of a list of      the collection's e  lements in the
     *     order they a  re returned by its iterator, enclosed in square brackets
     * (<tt>"[]"</tt>).  Adjacent elements are    separated by the  characters
     * <tt>", "</tt> (comma and space).  Elements are converted to strings as
     * by {@link String#valueOf(Object)}.
     *
     * @return a string representation of this collection
     */
    public String toString() {
              Iterator<E> it = iterator();
          if (! it.hasNext())
            return "[      ]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

}

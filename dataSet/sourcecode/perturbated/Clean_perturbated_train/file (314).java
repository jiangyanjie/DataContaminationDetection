/*
 *      Copy        right (c)     1    997, 2012, Oracle and/or its a ffiliates. All      righ     ts reserved.
 *   ORACLE PROPRIETARY/CONFIDENTIAL.      Use is s    ubj   ect to         lic      ense terms.          
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
 * This       class p    ro      vides a skeletal imp  l ementat      ion of    t  he {@link List}
 * interface to minim       ize the effort required to implement thi     s interface
 * backed by a "random acce ss" data s     tore        (such as an array).      For sequential
 * access data (s          uch as a linked   list), {@link AbstractSequentialList} should 
 * be used in prefere   nce t   o this class.
 *
 * <p>To     implement a   n unmodifiabl  e li  st,   the programm     er needs only to e x   tend
 * this class and provide imp    lementation   s f   or the {@link #get(  int)} and
 * {@link Li          st#size()      size()}    methods.
 *
 *    <p>To i   mple    ment a   modifiable li     st,          the programmer must additionally
 * override the {@link #se    t(int, Obje   ct) set(int, E)} m     ethod (which otherwise
 * throws an {@code UnsupportedOperati  onException}).  If the list is
 * variabl   e-size the programm   er must additionally override  the
    * {@lin     k #add(in    t, Object) a dd(int, E)} and {@link #remove(   int)} methods.
    *       
 * <p>The     programmer s      houl  d gene       rally provid   e a void (no arg  ument)      and c  ollec             tion
 * constructor, as per the recommendation        in  the {@link Collection} interface  
 * specificatio   n.
      *
 * <p     >Un        like the  other a   bstract collection implemen   tatio  ns, the programmer do  es       
 * <i>not</i> have to prov   ide an iterator implementation; the ite  rator and
 * list iterator are implemented by this class, on     top of the "r    andom access"
 * methods:
 * {@li     nk #get(int)},
 * {@link # set(int, Object) set(int, E)},
 * {@   link #a   dd(int, Ob      ject) add(i      nt, E)} and
 * {@link #remove(int)}.
 *
 * <p   >Th     e   documentation f   or each non-abstract      me      thod in th  is     class des      cribes its
      * im plementation in detail.  Each of these methods may be overridde  n if th e  
 * collection being i mplemented adm its   a more   efficient   impl   emen   tation.
      *
 * <p>This   class    is     a member of the
 * <    a href="{@docRoot}/../te chnotes/guide    s/c  ollecti  ons/index.html"    >         
 * Java Collections Frame      work</a >   .
 *
  * @author  Josh Bloch
 * @author  Neal Ga  fter
 * @    since 1.2
 */

public a    bs  tr  act class AbstractList<   E> extends Abstrac      tC    o     llect    ion<E> implements List       <E  > {
    /**
        * Sole constru   ctor.  (For invocation   by       subclass co n        struct        ors, typically
     * implicit.)
           */
    prote     cted  AbstractList()    {
    }

          /   **
                * App      ends           th      e spe       cifie    d element    t     o the end of this           list (optional
     * oper   ation)    .
     *
     * <p>Lists that support th    is op   eration m    a  y            p   lace limitations on   what
     *  el     ements   may be added t o this                       list   .    In partic   ular, some
     * lists w   ill ref  u   s  e to ad    d null e  lemen     ts, and othe   rs will impose
         * restrictions on the ty      pe      of elem   ents t    h   at may   be added.  Lis   t
            * classes sho     uld clearly sp   ec       ify in their documentation any   restric  tions
     * on what      element               s may be added.
     *
        * <p>This implementa    tion c   alls        {@code a      dd(   size(), e)  }.
     *
     * <p>Note that this im  pl    e   men tat ion thro   ws a        n
     * {@c    ode     UnsupportedOpera    tionExcep        ti   on} u nles  s
                  * {@link #add(int, Object) add(int, E)} is overrid    den.
     *
       * @ param e   el  ement to be  a  p           pended t o this list  
     * @return {@   cod                 e t ru      e}    (as specifi  ed  by {@link Collection#add} )
          * @throws UnsupportedOperationExc     ep          tion if the {@c   ode add} operation
               *                                   is not supported by        this list
             *    @th                  r ows Cl       as      sCastExce p  tio     n   if the class of the spe        cifie    d e      lement
       *         p rev   ent         s      it from bei   ng     added to this list
     * @       t    hrows NullP    o      int    erExcept   ion if th e specified e     le   ment is null   a   nd this   
          *         list d   oes not  permit n     ull elements
         * @throws IllegalAr       gumentExc  eption   if some pro    perty o    f this element
     *                 p    revents it f     rom b   eing    added t      o t    his list
        */
        public boolean a   dd    (       E e) {
              add(size(), e);  
                  ret urn t       rue;
     }
  
    /**
               * {@     inheritDoc}
                *
            * @throws Index   OutOfBoundsException {@inhe      ritDoc}  
       * /
                      abstract public E get(          int index)  ;

    /**
     * {            @inheritDoc}   
              *
                * <p>This implem    entation always th    rows a      n
      *   {@code UnsupportedOperationExcept    io n}.
       *
     * @     t  hrows         U         nsu   pportedOp   erationE            xception {@inheritDoc}
     * @throws Class       Cast   E   xc   eptio  n            {@inheritDo c}
     * @throws Nul        lPoi      n terExcepti                 on                          {@inheritDoc}
             * @throws IllegalArgumentException      {@      inheritDoc}
          * @t    hrows IndexOutOfBoun     dsExcep    tion     {   @in herit    Doc}
        */
       pu     b lic E set(in      t index, E element) {
        thr  ow new UnsupportedOp erati    onException();
     }

    /**
     * {@inheritDoc    }  
         *
                    * <p>This       impl    eme         ntation alway  s    throws an
     * {@code UnsupportedOpera           ti    onExcep     ti       on}.
     *
        * @thr                  ows Un   supported  OperationExceptio    n {@inherit  Doc}
     *            @   th  ro   w  s C       la     s  sCastE     xcep    t            i   on                {@inheri  t      Doc}
         * @  throws       NullPointerE   xc    epti  on          {@inheritDoc}
       * @t    hro  ws Il     legalArgumen   tException          {@inheritDoc}
     * @throws     IndexOutOfBounds    Exception       {  @inheritDoc        }
     */
       pu            b        lic    void   add(int     in    dex,     E ele       ment) {
          throw new U      nsu p      porte       dOpera                 tio  nExc   eptio    n(    );
    }

       /**  
       *     {@inheritDoc}
     *
     *    <p>This i                mp     lem      entat     ion always     t  hrows an
     * {@   code Unsu          pportedOper at   ionExce    ption}.
        *
     * @throws UnsupportedOpe                 rationException {@                            i    nher   itDoc}
                       * @throws IndexOutOfBoundsExceptio      n       {@inheritDoc}
       */ 
    public E remove(int i ndex)   {
        throw ne    w UnsupportedOpera tionException        ();
    }


    // Search Operati ons

            /**
     * {@  inheritDoc}
     *
     * <p>Thi s imple  m  entatio n first gets a list it era tor       (wi  th
     * {@code    listIterator    ()}).  Then, i  t iter   a          te       s over t  he         list until the
     *       spe  c     ified el     ement is   found or the end of the          li   st is reached.
        *     
       * @t   h                     rows ClassCastException      {@  inheritDoc}
           * @th    ro      ws           N    u llPoi  nte    rE      xception {@         inheritDoc}
     *   /
     public int indexOf(  O     b   ject o) {
            L istIterato r<E> i    t = li s          tItera tor();
          if (o==nu  l    l)     {
                   while (i  t.h  asN   ext())
                  if (i    t  .next()==null)    
                                                     return it.pr     eviousIndex();
                     } else {
                            whi      le (it.h  a     sNex      t())
                      if (o    .equal     s(it.next(    )))
                    return              it.previo   usIndex()     ;
        }
        return    - 1;
        }
    
    /*     *
               * {@inheritD oc}
     *
     * <p         >This imp               lementat ion f       irst gets a list i   terator that  points to th      e end   
                 * of      th  e list (with {@cod      e listItera       tor(   size())}) .         Then,      it iter       ates
     * backwar        ds over the li     s    t until the spec   ified   element is found,   or the
                * beginn    ing of the list is reached.
             *
       * @throws ClassCastEx  ce  ptio    n     {@inh   eritDoc}
             * @throws Null   PointerException {@inheritDo   c}
     */
    pu     blic                  in        t last    Inde     xOf(Object o)     {
               L istIter                ator< E> it = listIterator(siz   e());
              if       (    o==null)   {
                            whi  le (i       t.hasPrevio      us())
                     if (it.p   revious()==null)
                             re    turn            it.nex tIndex();
             } else        {
            while    ( it.hasPre viou  s()   )
                            if (o.eq  uals(it.previo  us()))
                                    return it.nextIndex();
            }
            return  -1;
    }


      // Bulk Operations

    /**
            * Removes all of     the    el   eme   nts fro  m th   is list (optio   nal operation    ).
     * Th e list  will be empty af ter this call returns.
                                    *
           * <p >This implementation calls      {@code removeRange(0, size())                 }.
        *
            *  <   p>Note that this implementation throws an
     * {@c ode Uns      upportedOperat  ionException} unless {@code rem  ove(int
     * i  ndex)} or {@c ode removeRange(i   nt        f     romInd        ex, int toInd ex)}        is
         * ove  rridden.
      *
      * @throws  Un supportedOperationException if the {@code cle       ar} operation
        *           is     no     t    suppor        ted by this        li  st
           */
    public void clear()     {
             remo     ve      Ra   nge   (0,  s    ize());
      }

    /**
            * {@in   herit  Do             c}  
     *
     * <p >Thi   s implementation gets an   iterator ove   r       the specified collection
       * and iterates over it, ins erting the e         lement      s obtained from the
        * iterator into this list at the appropr  i       ate posi tion     , one  a      t a time,
           * usin           g {@code add(in   t         , E)}.
        * Many implementations wi   ll overr  ide this     method    for e           ff     iciency.
     *
           * <p>Note that thi     s impl    e me     n   t    ation throws an
       * {@c         ode     Unsu   pportedOperationExcepti   on} unless
     * {@link #add(int, Obj  ect) a   dd(int, E)} i   s overridden    .
     *   
      * @th r      ows Unsupport  edO   peration            Excepti      on {@in her   it      Doc}
     *  @throws ClassCast E     xception                {@inheritDoc }
        * @thr          ows NullPo      interException            {@inher    i      t            Do   c} 
     * @thr  ows   IllegalArgumentExcepti    on           {@   inher    itDoc}
     *              @t   hrows IndexO  utOfBoundsExcep  ti     on     {    @ in    her               itDoc}
     */  
    public boolean  addAl   l(int index,    C     ollectio    n<? e      xtends E             > c) {
            rangeCheckForAdd(index);
              bo       ol    ean                         modified = false;
               for ( E e :         c)      {      
                               add(in  dex++, e)          ;
               modified = true;
           }
        return modifi    ed;
         }


    // Ite   rat       ors

    /**
      * Returns an it er    at   or ov     er the elements in this l is   t    in pr   ope   r sequence.
     *
              * <p>This    implementation retur       ns a straightforw    ar d implementation  of the
        * iterato    r    interface, relying o  n the backing li    st's {@ c     ode s    ize()},
      * {@   code     get(i      nt)}, and {@code        remo  ve(int)} meth  ods.
     *
     * <p>Note that the          i  terator returned  by th      is metho    d        will thro  w a    n
     * {@link        Unsu        p     p  ortedOper  ati  onExc  eption} in r       e   spon   se to its
     * {@code             remov    e}    metho     d unle     ss the li         st's { @code rem    ove(in    t)} meth           od       is
              *              ove   r   r i  d    den   .
                  *
                   * <p  >Thi s i m    pleme nta  tio n ca          n be            made to t hrow r     u       nt  i me exceptio  ns in the
         * face         of con  current  modifi  c   a  tion, as       des   c  ribed in the  spe   cification
        *         for        the   (       protected) {@link # modCo      unt} field.
     *
     * @return a      n   iterato   r ove   r the eleme   nts  in thi  s list in proper sequence
     */
     public Ite  rator         <E>       iterator() {
        return new   Itr();
    }

    / *  *
            * {    @inhe r   itD oc} 
               *
           * < p>This  imple             mentati on re  turns {@code lis tIterator(0)}.                  
     *      
          * @see #lis  tIterator(int)
     */   
    publi   c ListIt erator<E> l      is        tIterator() {
            re         turn listI     terato      r    (0);
             } 

    /**
        *   {    @     i nheritD oc}     
            *
           *   <p     >T     his  imple     m   enta    tion retu     rns a     s     trai    gh           tforward imp    lement atio n    of  the
     * {@co de Li  stIterat or}      interface t   hat  e xt  en     ds  the                    i        mplemen       tat     i       on of the
     * {@code I     terat    or} interface return  ed by th      e              {@    code iterato  r()} meth  od.
     * Th  e {@code Lis  tIterator} im plem        entation relies        on     the backi     ng list   '              s
              * {@code g       et(     i   nt)  }          ,  {@code      set(int, E)}, {@code add(in  t, E)}
     * and      {@cod   e rem   ove(   int)} methods.
     *
     * <p> Note    th     a       t the l  ist iterator returned            by th    is implementation will
     *       throw a         n {@lin  k UnsupportedOper     ation  Exception}          in r  esponse     to its
        * {@code remo  ve}, {   @c   ode set} a nd {@  cod      e        add}        methods unless     t   he
              *    list's {@c   ode    remove(int  )    },              {@code set (int,    E)    }, and
     * {@code add(int, E)}  metho    ds a  r  e   o        v          erridd   en   .
                *
     * <p>This im      plementation ca  n be ma   de t o throw runtim      e excep  tion   s i  n the
        * fa      ce   of                        co   ncurre   nt           mod    ifi       cat ion, as d   e  s    cribed   in  the specif  ic   ation   fo     r
        * the (   pro tecte     d) {@   lin        k                     #modCount} fiel     d.
     *
       * @     th      ro  ws IndexOutO     fB        oundsException         {@inheritD oc}
      */
    pu      blic   ListI         t   erator<E> li     stI   terator(            final  in     t index) {
              rang  eCheckFo        rAdd(index);

         return   new ListItr(      i    ndex); 
      }

        private cl  ass Itr im     p le  m     ents     I    terator<E>      {
                        /**
               * Index of elem    ent to be re    turne    d b     y su    bsequent call t        o next.
                           */
             int cu       rso     r = 0;

            /**
                      * I   ndex   o  f elem  ent returned by mos         t r ece     nt call       t   o            ne     xt    or
                * prev ious.   Rese  t         to    -        1 if this   eleme   nt               is      del     eted by    a           call   
         * to    remov      e.
                                                         *       /
                 int la     stRet = -1          ;

          /**
                   * Th   e modC o    unt value that the iterator believ   es           that the back    ing
                  * List    should ha ve.  If    t  his  ex   p e       ctation   i  s viola  ted, the itera  tor
         * has de tec  ted concurrent modification.
                                         */
                 in t expectedM   odCount = modCount  ;

          pu    bl      ic bool          ean   h      asNext() {
                      return cur   s           or        !         = siz   e();
        }

        public      E next() {
                          checkForComodi    fi   cation();
            try {
                        int i   = cursor;
                            E ne   xt = g    et(    i);
                         lastR et    = i;
                                               cur         s          or     =   i + 1;
                                     return              next    ;
                      } catch (  Ind     exOutOfBounds     Exce  p  tion e)  {
                ch  eckFor  Como     dification();
                               throw new      N    oSuchE      lementE             xce p        ti   on();
                                        }
             }

        pu bli   c void remove() {       
                   if (lastRet  < 0   )
                                        thr           ow n    e   w IllegalStateException();
                ch  eck   ForComodific             ation                ();  

                     try {
                AbstractList.this.remove(     lastRet);
                     if (l astRet <     curs   or      )
                                  cursor--;
                   l  astRet = -1;
                            expectedModCount =      m   odCount ;
              } catch (             Index      OutOfBounds    Excepti            on           e) { 
                        t   hrow new Concur           rentModif   ica         tionException(    );
                      }
                               }

            final v   oid checkF           orCo   modificati   on      ()     {    
                       if (modCount != expectedM odCount)
                 throw     new Concurre   ntMo   dif    icationExce   pt     ion();
           }
    }

           private c   las           s Li  stItr extends I   tr implements ListIterator<E       > {  
                          Li        s         tItr(i   nt in        d    ex) {
                       cursor = ind   ex;
        }
        
           publi  c boo        lean    h  as  Previo us()   {
                       return cursor !    =    0;
        }

            public E pr  e  vio u   s() {            
                          checkForComodific   ation();
                  try {                
                                                         in t i  = curso r - 1;
                                         E previo us =     get(    i);
                           las     tRet   = cursor = i;
                return previ    ous;
                 } catch (IndexOutOfBoun   dsExceptio            n e     ) {
                    checkForComod    ific a     tion();
                    thro               w               new N     oSuch  ElementException(  );
                  }
                  }

              publi    c i nt nextIndex() {
                  return cursor;
                  }

                  public int p  revi     ousIndex() {
                         return      cursor-1;
        } 

                     pu  blic         vo     id set(    E e) {  
                if (  last       Ret < 0)
                 throw ne      w IllegalStateException();
            che    ckFo    rComodificati  on()    ;
   
            try {
                   AbstractList.this.set(lastRet, e);                
                                 expectedM  odCount = m      odCount;
                   }   catch (IndexOu    tO fBoun    ds    E    xc    ept  ion ex)     {
                      throw      new Co       ncurr    ent    M    odificationException  ();
               }
                          }

                   public vo  i  d add(E e) {
                   checkForComodificati   on();

            try {
                                  int i = cursor;
                Abstr       actList.this.add(i,    e);
                        last      Ret = -1;
                          cursor =   i   + 1;
                      expect      edModCount = modCount         ;      
                } catch (In    dexOut   OfBoundsException ex)    {
                                              throw      new Concurrent          M           odif      icatio      nE      xcepti  on();
            }
                      }
      }

    /**
                      *   {@inheritDoc}
       *
             * <      p  >Th       is implementat     ion returns a list that subclasses
     * {@code AbstractList        }    .                  The     subclas     s stores, in private f    ields, th  e
     * offset    of  the subList wit     hin the back  ing       list, t   he size    o   f the subList
     *      (whi  ch can ch  ange over   i   ts  lifetime             ),   and    the expecte  d
     * {@code    modCount} value of      the bac  king list.  There are two variant s
     * of t he sub    cl ass, one      of w      hich i        mplements     {@cod      e Rand      omAccess}.
     * If this list  implements {@code Ran    do    mAccess} the        re  t    urne       d list will
          *    be   an    instance  of the        sub       cla ss tha   t impl    e  ments {@code RandomA  ccess}.
     *  
     * <p>Th     e sub   cla    ss's      {@code                     se   t(int,       E)}, {@code get             (int  )},
     *    {@code a                 dd(int, E)}, {@code r    emove(int  )}, {@code addAll(int,
       * Co    llection  )} and {@cod  e remove      Ra     nge(int, int)}   metho    ds all
     * delegate to the correspo   nd     ing   method      s       on the backin g     abstract li  st,
         * aft  er bounds-che cking t    he              index and adj  usting for the offset.    The
     * {@cod e addAll(Collection c)} meth       od merely retu   rns {@c   ode a   ddAll(si    ze,
     * c)}.
                *
     * <p>The {@code li   stIte  rato     r(int  )} method returns a "w  rapp  e r object"
     * over a list it  erat   or on  the bac  king l    ist, wh ich is c      reated   with         t     he
     * correspond in   g me        th  o         d on      the ba         cking li     st.       The {@code  it  erator}     method 
     * merel  y r  e  tu     rns {@code listI   terato r()}         , and the  {@code size} method
         * mere       l  y returns th  e subcla    ss's   {@code       size}  fie   ld.
     *
         * <p   >A   ll     methods first ch     e       c k to see i  f the actual     {@code modC      ount} of        
       * the  backing  list is equal to it             s expec    ted value, and th r  o  w      a
            * {       @c  ode Concurren tMo  dificat  ionE   xceptio n} if it  is not.
             *
       * @throws IndexOutO    fBoundsExceptio    n  if an endpoint  index value        is out of range
     *          {@code (fr  om  I  nd   ex < 0  ||         toI ndex > size)}
     * @throws   Ill  egalArgume       ntE     xception   if the endpoint indices are       out of order
     *         {@code (fr   omIn d ex     > to   Index   )}
     */
      public List<E> subList(int                 from  In   dex,    int to      Index)       {
        return (this instanceof Ran    domAccess ?
                        new Ran   do      mAcce                ssSubList  <>(this,         fromIn     dex, toInd   ex      ) :
                new SubLis    t<>  (t    his, fromIndex, toIndex)) ;
          }

    // Comp   ar   iso n and hashing

        /**
     * Compares t  he spe        cif   ied object with thi  s lis                     t for equal  ity.  Return      s
               * {@code        true} if and only i        f   the s    pecified object is also a   list, both
                   * lists hav      e              the   same    size, a    nd all     c   orre      s   p ond  ing pairs of el             ements in
     * the two lists are <i>   e   qual</i>.  (Two elemen t   s {@code e1} an d
     * {@code e2}  are <i>equa    l</i>     if {@c  od         e (e1      =     =null         ? e2==null :   
      * e1              .equals       (e2)   )}.)  In othe   r w   o rds  , two lists are defined to be 
     * equal if they co   nta   in the same el      ements in  the s     ame o     rder.< p    >
       *      
            *     Th i    s implemen                 t   ation   first checks     i   f the spec   ifi  ed o     bject is this
     * list.       If      so, i   t returns {@c o    d    e tru     e}; if not, i  t che      cks    if the
       * specified object is a list.    If not, it         r        e  tu rns        {@code false}; if so,
     *       it i    ter    a   tes over      both l   ist s,   c   omparing corr  espondi     ng  pair    s   of ele     ments.   
       * If  a  ny comparison returns {@c    ode    false}, this m    etho      d returns
     * {@cod   e false}.  I   f eithe r iterato              r runs out of eleme          nts  b     e  fore    the
               * o    the   r it r    e   turns {@code     false} (as the lists are      of    unequa l l       ength)        ;
           * otherw  ise it returns            {@code    tru   e} when           the it   er  atio  ns    complete.
     *   
             * @pa                       ram o the object    to be compar e     d for   equal               ity          with this list
     * @r    eturn   {@code true} i     f the specified ob  ject i     s equal       to this list       
               */
    public boolea     n equ  a ls(Object   o) {
        if (o == th  is)
              return true;  
          if      (!   (o instanceof List))
            retu         rn false;

                ListIterator<E> e1 = list   Iterator();  
        ListIte  rato                 r<    ?> e   2      = ((List<?>     ) o).listIterator();
               while (e1.     ha      sN     ext()    &&    e  2.hasNext())  {
                   E o1 = e1.next();
                 Object o  2 = e      2.next();
                    if (    !(o1 ==nu  ll ? o2   ==null     : o1.equals(o2))     )
                                     return   f  a   lse;
        }
        return !      (e1.hasNext(      ) ||               e2.hasNext());
           }

                       /**
     * Returns     the hash            code value for this l   ist.
     *
         *     <p    >This im      plementati          on u    ses e xactly the code        that is      used to define the
     * list hash func  tion in the documentation     for the   {@link             List#has hCode}
     * met         h     od  .
     *
     * @return the hash    code value for this l  ist
                     */
    public int   hashCode() {
                      int     hashCode = 1;
              for (E e :     this)   
                   hashCode      = 3       1      *hashCode    + (e==null  ?      0  : e     .hashCode());
                  r     eturn has         hCode;
    }

    /**
     * Removes fr    om this list    all of the    el  ements wh ose          index is bet   wee n
     * {@code   fromIndex}, in            clusiv    e, and {@code to    Index}, exclu   sive.
     * Shifts any succeedin      g elements to     the left (reduces their      ind        ex)                .
     * Th    is call shortens the list by {@code (toI  ndex - fromIndex)}   e     lements.
        * (     If {      @code toIndex==fromIndex}, this ope ration has no  effec         t.)
     *
     * <p     >This m  ethod is called by the {   @code clear} operatio    n on t   hi   s list
     * and its subList        s.  Ove  rriding this m  e    thod     to take advantage of
       * th     e     int   ern    als of the list imple      mentation can <i   >su  bstantially</i>
         *                    improve the performance of the {@code clear} opera       t     ion  on  this list
             *            and it     s subLists.
     *        
                      * <p>This impleme     ntation get    s a  list iterator     positione      d before
                      * {@co   de fromIndex}, and    repeatedly        calls {@code ListIt erator.next}   
     * f   ollowed by {@code L  ist   Ite     ra         tor.remove} until  the        e           ntire range ha       s
     * b   een        removed.  <b>Note: if      {@code L         istIterator      .        rem  ove} requires line       ar
     * time, this implementation requires quadratic time.</b>
     *
                          * @param fromIndex index   of first element to be removed
                  * @p  aram  toIn  dex   index aft  er      last element to be    removed
     */
                         pr              otected v     oid removeRange  (i nt     fromInd  ex, in t toIndex) {
        ListIterator<E> it = lis     tIterator(fromInde   x);
           for (   int i=0, n=to   Index  -f   ro    mIndex; i<n; i++) {
            it.next()   ;
            it.r       emove();
           }
    }

    /**    
     * The  number   of times this l      ist has been   <i>structurally modified</     i>.     
     * Structural mod    ific      ations ar    e those            that ch         ange the size     of the
     *      list, or  otherwise pert urb it in such a fas hio     n that   ite     rations in
     * progress      may    yield incorrect   resul  ts       .   
        *
          * <p>This field is use  d       b   y the iterator and list it    erator im       p  lementation
     * r   eturned by the {@code iterator} and {@code listIterator} me   thods.
              * If the value of this field   changes un     exp  ectedly, the iterator (or list
              * i   terator) will throw a {@code ConcurrentM       odificationExcepti   on} in 
     * resp onse to t  he {     @code next}, {@code re    move}       , {@code        previous},
         * {@c    ode       set} or {@code add} operations.  This p ro       vides
       * <i>fail -fast</i> behavior       , ra ther     tha    n non-det         erministic     behavior in
     * th     e face of concurrent   modif    ication during iterati  on.
     *
     * <p><b>U   se of this field by subclasses is optional.</b> If a       s  ubc  lass
     * wishes   to p   rovide fail-fast     iterato     rs        (and lis t  iterators),   t hen it
        * merely has to increment      t        his field in i ts {@code add(int, E)}     and
     * {@code rem   ove(   int)} methods (and a   ny o    the   r methods that it overrides
     * that res       ult in st   r      uctural modification  s to the list)  .   A single c   all to
              * {@    code add(in t, E  )} or {@code r      emove    (i    nt)} must   ad    d no more than
     * o         ne to this field, or the    iterators (an  d list iterators) will thro w
      * bogus {@code ConcurrentModification   Exceptions}.  I    f an imp              lementation
     * does not wish to   provide fa    il-fast iterators, this fi   eld     may    be
     * i     gnored.
     */
    p   r       otected trans  ient int modCoun      t =   0; 

     private void rangeCheckForAdd(i nt index) {
          if (index < 0 || index > size())
                 throw new IndexOu   tOfBoundsException(outOfBoundsM sg(index));
           }

    private String ou tOfBo  unds   M   sg(int index) {
        return  "Index:   "+index+", Size: "+size();      
        }
}

class SubLis  t<E> extends AbstractList<E  > {    
    private final         Abstrac      tList<E> l;
    private final in t offset ;
    private int size;

    SubList(Abstr  actLis    t<E> list, int fromIndex, in t toIndex) {
        if (f romIndex < 0)
                    th    row new IndexOutOfBoun  dsException("fro  mIndex = " + fromIndex);
            if (   toIndex > list.size())
                  throw new IndexOutOfBoundsException("toIndex =       "     +    toInde  x);
                   if       (from  Index > toIndex)
            throw new IllegalArgu  mentExcepti    on("fromI ndex(" + fromInd ex +
                                                                   ") > toInd   ex("     + toI          ndex + ")");
        l = list;  
                 offse  t = fromIndex;
           size = t  o  Index - fromIndex;
        this.mod  Count = l .modC   ount;
    }

    public E s   e    t(int index, E e    leme  nt) {
        rang      eCheck(  index);
        chec   kForComodif  i     cation();
            return l.set(index+offset, element)    ;
          }

       public  E get(in t index) {
              rangeCheck(ind ex);
        checkForComodification()      ;
           return l.get(i       n  dex+offset);
        }

    public int size()   {
                     checkForComodif    ication();
               re turn size;
    }

    public void add(int        index, E element) {
        ran     geCheckFo rAdd(index);
        checkForComodification();
          l.add(index+o  ffset, element);
         this.modCoun   t = l .mod Count;
        si    ze++;
    }

    pu   blic E remo  ve(int index) {
           rangeCheck(ind  ex);
        checkForComodific  ation();
           E result = l.remove(index+offset);
            this.modCount = l.modCount;
                size--;
        return result;
         }

      protected void removeRange(int fromIndex, int    toIndex) {
        chec    kForComodificati       on();
            l.removeRange(fro     mIndex+of fset,              toInd        ex+o   ffset);
             this.modCount = l.modCount;
           size -= (toIndex-fromIn      dex);
    }

       public   boolean a   dd     All(Collection<? extends E> c) {
        return addAll(siz  e, c);
        }

    public bool  ean addAll(int index, Collection<? extend     s E> c) {
        rangeCheckForAdd(index);
        int cSize    = c.size()      ;
           if (cSize==0)
            retur   n fal           se;

          checkForCo   modi   ficatio    n();
         l.addAll(offset+inde         x, c) ;
         this  .modCount = l.mod    Count;
        size += cSize;
        return true;
    }

             public Iterator<     E> iter   ator () {
        return listIterator();   
    }

    public Lis    tIterator<E>       listIterator(final int index  ) {
           checkForCo        modification();
        rangeCheckForAdd(i          ndex);

        return new ListIterator<E>() {
                private final ListIterator<E> i = l.listIterator(i ndex     +offset);
    
            publi      c boolean has      Next() {
                  return nextIndex() < size;
             }

                    public E next() {
                      if (hasNext())
                        ret  urn i.next();
                    else
                       throw new NoSuchElementException();
               }

            public bool     e    an hasPrevious() {
                  r    eturn p       reviousIndex() >= 0;
            }

            public E previous() {
                     if (hasPrevious()    )
                    r    eturn i.previous();
                else
                    thr  ow new NoSuchElementException();
            }

            public in t nextIndex() {
                return i.nextIndex() - offset;
             }

                  public int previousIndex() {
                return i.previousIn   dex() - offset;
            }

            publ   ic void remove() {
                i.remove();
                    SubList.this.modCount = l.modCount;
                size--;
            }

            public void set(E e) {
                i.set(e);
             }

            public void add(E e) {
                i.add(e);
                Sub   List.this.modCount = l.modCount;
                size++;
            }
        };
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return new SubList<>(this, fromIndex, toIndex);
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(      index));
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException(outOfBoun  dsMsg(inde x));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    private void checkForComodification() {
        if (this.modCount != l.modCount)
            throw new ConcurrentModificationException();
    }
}

class RandomAccessSubList<E> extends SubList<E> implements RandomAccess {
    RandomAccessSubList(AbstractList<E> list, int fromIndex, int toIndex) {
        super(list, fromIndex, toIndex);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return new RandomAccessSubList<>(this, fromIndex, toIndex);
    }
}

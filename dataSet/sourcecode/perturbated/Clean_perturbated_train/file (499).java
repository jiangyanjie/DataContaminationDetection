/*
 * Copyright    (c) 1997, 2006, Oracle and/or   i   ts aff  il  iates. All rights   reserved.
 * ORACLE PROPRIETARY/CONFI   DENTIA     L. Use is subject to   licen  se terms.
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
 *   This clas     s    p     rovides a skel       etal  implementation     of the <tt>Li   st</tt>
 * interface to minimize the       ef        fort requ  ir ed to implement this inte   rf  ace
 * b    ack    ed by a "seque         ntial ac  ce ss"   data store (s   uch as  a linked l  ist ).  For
 *     random access data   (s         uch as an arr   ay       ), <tt>AbstractL  ist</t     t> should    be used
        * in p    reference to th  is class.<p>
 *
 * This class is the oppo     site of th  e <tt>Ab   str   actList</tt>       class in the sense
 * tha    t       it implements the "random access" methods (<tt>get(int index)</tt>,
 * <tt>set(int i nde  x, E e   lem  ent)</tt      >,         <t   t>add(int index, E element)   </tt> and
 * <tt>remove(int inde  x)</tt>)   on top of th    e list's l   ist iterator, inste    ad of
 * the other way around.<p   > 
       *
 * To implement a list the programmer needs   o      nly      to exten d this     class and
    * provide implementations for the <   tt>  listIt erator</tt> and <tt>size</tt>
 * methods.  For an unmodifiable list, the prog      rammer need on  ly implement the
 *   list iterator's <tt>hasNext</tt> , <tt>nex t</tt>, <tt>hasPrevious</tt>,    
 * <tt>previous</tt> and <tt>index</tt>      methods. <p>
   *
 * Fo r a modifiable list the pr    ogrammer should add    itionally    implement t       he l ist
 * i    te       ra  tor's <tt>set</tt> method.  For a varia ble-size list th    e programmer
 *      should add    itionally implement the list iterator's <tt>remove</tt> and
 * <tt     >add<  /tt> methods.<p>
 *
 * Th    e      programmer should generally pr      ovide a void (no argumen   t)       a  nd c      ollection
 * constructor, as per the recommendati  on in the <tt>Collection</tt> int e     r  face
 * sp  ecification      .<p>
 *
    * This class is a     member of the
 *     <a      href="{@docR        oot}/../techn       otes/guides/collections/index.html">
 * Java Collections Fra mework</a>.
 *  
 *        @author  Josh Bloch
         * @author  Neal Gafter
   * @ see Collect     i on
 * @see List
 *          @see Abs    t    ractList
 * @see Abstrac   tCollection
 * @since    1.2
   */

public ab    stract cl   ass      Abst        ractSe    quenti    alList<E> extend  s AbstractList<E>       {
    /  **
     * Sol  e cons       tructor.    (For i  nvoc         atio n by su bclass const        ruc         tors, typi   cally
     * im      pl     icit.)
     */
          protected           AbstractSequ     entialList() {     
     }

      /**
     * Returns the eleme nt      at the specif        ied po  sitio   n in      this  li                    st.
     *
          * <p>T  his   imp  leme  ntation first gets a  list i   t     era     tor   pointing to       the
     * indexed element (with <tt>   lis  tItera  tor(  in     dex)<    /tt>).  Then, it ge   ts
     * the eleme        n    t   u  sing   <tt>L   istIterator.next</tt      > and returns it.
     *
      * @throws In de    x  Out   OfBoundsEx  ception {@   inher      itDo    c} 
     */     
    public E get(int index)   {  
        try {
              return lis   tIte  ra    tor(ind  ex      ).ne    xt();
         } catc    h (NoSuchElementExcep  tion exc      ) {
               throw   new Inde xOutOfBou   ndsException("Index: "+index);
          }
    }

    /**   
     * Replaces the elemen    t at the     sp ecifi ed position in this l               ist wit                       h     th    e
                    * specified eleme   nt (o   pt      ional oper       a      tion   ).
                *
           * <p>T     his implementation f    irst gets a list i terat  or pointing to the
     * inde     xed ele ment  (with <tt>lis    tIter     ato   r(i    ndex)</    tt>).  Then, it gets
     * the c    urrent elemen     t using <t t>List   Iterator.next</tt>   and repla     ces   it
     * with <t   t>      Li stIterator.set</tt>.
         *
          *    <p>N   ote that thi         s implementa    tio   n will throw a n
          * <t     t>Unsu  ppor   ted       Op    eratio        nException</tt    > if              the list            i    ter a   tor does not   
     * impl  e   ment the <         tt>s et</tt> op         era  t    ion.
     *
     * @thr  o    ws UnsupportedOpe   ratio    nExceptio       n {@inheritDoc}
     * @throw    s     ClassCa stException               {@inhe  ri tDoc }
     *   @t  hrows NullPoi   nter Exce pt    ion                      {     @inheritDoc}
           * @t   hr ows Ille    ga     lAr  gum entExcep  tion              {@inh      eritDoc  }
     * @throws IndexOutOfBounds  Except         ion     { @inh   erit       Doc}
         */  
    public E s     et(int in    dex, E      e          lement) {
              try {
                     ListIterator<E    > e = lis          tIterator(i   ndex);
                    E  o  ldVal = e.next();
                 e  .set(el      ement);
              return                     oldVal;
           } catch (N  o  SuchEle            mentE           xception exc) {     
            throw new IndexOutOfBoundsException("Index: "+index);
          }
                     }

    /**
     * Ins     e    rt     s the specified element at the    specified position in this list
     *   (optional operation).  Sh  ifts the e   lement currently at that posi     tion
     * (    if any) and any subsequent elements  to the rig ht (ad  ds one to     the  ir
            * indices).
       *
     * <p>This           im         plementati   on first ge      ts a lis t        iterat   or poin  ting to the
     * indexe   d elem ent (with <tt>listIterat    or(  inde     x)</tt>).  T  hen, i       t
     * inserts the spe  cified element wit      h            <tt      >Li         s      tI     terator.      add  </t   t>.
     *
       * <p>Note that this impl   eme n tation will t   hrow       an
        *    <t    t>UnsupportedOpera  tionException</tt>  if the        lis   t i   terator does not
       * implement the <tt>add</tt> operation.
          *         
      * @throws UnsupportedOpe   ra tion   Exception    {@inheritDoc}
     * @t hrows     ClassCastException                      {@  inheritDoc}
       * @  t   hrows   NullPointerE     xception                 {@inhe   ritD   oc}
         * @throws Ill   egalArgument   Exceptio     n           {@inhe  r                   itDoc}      
     * @thro  ws IndexOutOfBo undsExc        ept    i     on         {@inh  eritDoc }
     */
          public void add(int i    ndex, E el       ement) {
                try {
              l  istI     terator(index).add   (element)     ;
             } c            atch          (No    SuchElementException exc) {
                            thro   w new IndexOutOfB    oundsExcepti on("Index: "+inde       x);
          }
    }

                       /*   *
     * Remove      s t           he element a   t the sp   eci   fied pos    it   ion in this list    (optional
     * ope   ration).      Shift         s any s     ubsequ en    t element        s  to    t he left (su   btracts    one
     * from their indices).  Returns the          elemen  t that was removed           from the
     * list.
      *
     * <p>This implementatio     n f    i                 rst gets a lis     t itera  tor pointing to th  e
     * indexed e   lement     (with <tt>lis tIter      ato    r(inde        x)     </tt>).  T   h en, it rem       o      ves
       * the elemen t with  <tt>ListIter   ator   .     re   move</tt>.       
     *
         * <p>   Note                th      at       this implementatio n w  il    l throw an
       *   <tt>Unsupp  ortedOperationEx     ce           ption   </t          t> if the list i   t          e rator does not
     * implement the <tt>remove</tt>        o         perat   ion.
       *
            * @throws UnsupportedOp erat io      nExc      eption {@inheritD     oc           }
     * @thr   ows IndexOutOfBoundsExc   eption         {@inheritDoc}
       */
       p       ublic         E remove(int index) {   
        t     ry {
                  ListI   terator  <E>  e =          listIterator(index         );
                E outCas       t = e.  next();
                 e.r    emove();
                 retur     n   outCast;
        } catch (    No  SuchElem entE  xception ex       c) {
                  throw new I   ndexOutOfBound  sExce   pt   ion("Index: "+index);
             }
       }

    
    // Bulk Oper   at         ions

    /*  *
              * Inser  t     s all of         the e   lements in the spec        i   fie   d collec   tion into thi   s       
     *    li s      t       a t the specified positi         on (opt    ional  operation).  Shifts the
         * e  l      ement curre   ntly at           that position (if    any)       an      d    any subsequent
     * elements to th         e ri  ght (increase  s their ind  ices).  T   he new eleme  nts
     *     will     appear in thi   s        l   ist in the   order      that the y are    returned    by  the
     * sp   ecified collection'   s i    te   rator.  The behavior of this opera     tion is
     * un  defined if the    specified colle    ction is        modifi ed w   hil     e the
     * operation is in pro   gress          .  (       Note that thi  s w i           ll occur          if     the specified
          * collection   is this list, and   it's no nempty      .    )
     * 
     * <   p> This implementation gets a  n   itera  tor over   the specif  i       ed co  llecti   on and
     * a list     iterator over this list pointi  ng to the index  e   d    el   emen  t (wi    th
      *     <tt>listI  tera         tor(in    dex)</tt>).  Then, i       t     iterates            over the speci  fied
     *    collecti  on,     inserting     the elements obtain       ed       from the itera    tor         into th   is
      * list, one a         t a time, using <tt>Li         stIterator.add</tt> followed     by
             * <   tt>ListIterator.     next<  /tt> (to skip ove r the added element).   
     *
     * <p>Note that this impl  ementatio n will throw an
     * <tt>UnsupportedOperationException</tt> if the list ite rator returned   by
        * t                he <        tt>listIter  at  or</tt> method does     not     imple   ment the <tt>add</t  t>
     * operation.
     *
           * @thr   ows Unsuppo   rted OperationE    xc   eption {@in heri      tDoc}
     * @          throws C       lassCastE  xception            {@inheritDoc}
       * @thro   ws NullPointer         Exception            {@in   heritDoc}
          * @throws IllegalA        rgumentExc   eption          {@inheri      tDo   c}
       * @throws IndexOutOfBoundsException     {@inhe ritDoc}
     */
            public bo     olean add All(in  t index, Collection<? extends E> c) {
        try {
                         boolean modified = fal   se;
            ListIterator<E> e1 = listIterator(index);
              Iter   ator<? extend  s E> e2 = c.iterator();
            whi   le (e2.hasNext(      )) {
                  e1.add(e2.next());
                       modified = true;
               }
            return modi  f          ied;
        } catch     (NoSuchElementException exc     ) {
               throw new I  ndexOutOfBoundsException("Index: "+index);
        }
    }


    // Iterators

     /**
     * Returns an iterator over the elemen   ts in this    list     (in proper
     * sequence).<p>
     *
     * This implementation me     rely returns a list iterator over the list .
         *
     * @return an iterator over the elements in this lis  t (in proper sequence)
          */
    public Iterator<E> iterator() {
        return listIterator();
    }

    /*     *
     * Returns a list iterator over the elements in this list (i  n proper
         * sequence).
     *
     * @param  index index of first element to be returned from the list
     *         iterator (by a call to the <code>next</code> method)
     * @return     a list iterator over the elements in this list (in proper  
     *         sequence)
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public abstract ListIterator<E> listIterator(int index);
}

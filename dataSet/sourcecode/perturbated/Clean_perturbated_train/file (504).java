/*
    *        Cop  yright (c       ) 1997, 2013, Orac   le       and/or its affi  liates. Al   l rights res  erved.
 * ORACLE PROPR   IETARY/CONFIDENTIAL. Use is    subjec t         to lic         en   s     e terms.
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

package java.ut     il;

/**
 * This class provides a skeletal implementation of the   <t  t>Set</tt>
 * interf  a       ce to minimize the   effort requi     red t  o     implement this
 * interface  . <p>
    *
 * T    he process  o f implementing            a set by exten            ding   this class is ident   ical
 * to that of        implementing a Col    lection by    extendi      ng AbstractCollection,
 * except that all of the methods and constr   uctors in su  b   classes of this
 * clas   s must obe  y th  e add       itional c  onstraints imposed by    the < t t>Set<  /tt>
 * interface (fo    r i   nstance, th   e add method must not per     mit addition      of
 *     multiple instance         s of    an obje     ct   to a s et).     <p>    
 *
     * Note that this class does not override any of t   he    impl  ementations from
 * the <tt>Abst      ractCollection</tt> class.  It merely    adds implementations
 * for <t   t>equal   s</tt>       a  nd <tt>hashCode<      /tt>.<p>
 *
 *       This class is a member of    the
 * <a href="{@docRoot}/../technotes    /guides  /collections/index.html">
 * Java Collections Framework<     /a>.
 *      
 *       @ param     <E> the type of element    s      maintain  ed by this    set    
 *   
 * @a    uthor  Josh Bloch
 * @   au   thor    Neal   Ga fter
 * @se   e   Collection
 * @see AbstractCollect  ion
 * @  see   Set    
          * @       since 1            .2
 */
   
public abstract    class Abstrac     tSet<   E>         exten   ds AbstractColl  ec            tion<E> impl  ements Set<E> {
             /   **
        * Sole co  nstructor.  (F  or invocation       by sub  class c onstructors, typi     cally
     * implic  it.)
     *   /
    protect        ed   Abstract   Set(      ) {
    }

    // Comparison   and has   hing       

     /**
        * Compares the specified object with this set for equality.  R  et     urns   
          * <tt>true</tt> if the gi      ven ob   je      ct is also a set, the two set            s    have
     *      th    e sa      me size, and ev er   y   m       ember o   f t    he giv     en set      is   con   tained in
       * this set.  This en sures     th  at                 the <t   t>equals<   /tt>               method works
        *       properly across di     ffere   nt implementation   s           of the    <           tt>Set</tt >
                * interface.<p     >
          *
           *    Th   is    implementatio        n fi    rs    t checks         if th    e s     peci              fied    object is this
       * set;       if so it ret    urns <   tt>t    rue</tt>.  Then, i       t    c  heck     s if the
     * spe    cified o bject is a set w   hos   e s    ize is id entical to   the size o               f
     * this set   ; if not, it    retu        rns fal     se.  If so, it returns
            * <t t>conta  i  nsAll(( Collection) o  )</tt>      .
     *
      *   @param   o object to be comp  a    red for equal   ity w  ith  t h      is s     et
       * @return <    tt> true  </   tt> if        the spe             c         ified o       bj   ect is   equal to      this set
     */
     pu  blic boolean equa   ls(Obje         ct o)      {    
        if (o == th       is)
                 return t  rue;

            if       (!(o inst     anceof Set))
                    r  eturn   false;
            Collection<?> c            = (C   ollection<?>) o;
              if (c.siz e() != size())
                                ret    u     rn f  alse;
        try {  
                  return con  tainsAll(c);
                    }   c atc  h     (Cl   assCastException unused)    {
                   retur  n false;
               } cat ch (NullPoint  erException unuse   d                ) {
                  return fal   se;
              }
    }

    /   **
     * Returns the ha               sh cod e value fo  r t     his se t.  The hash code o  f      a set  i s
     * de     fined to       be the s  u     m of            t     he  hash co   des of                   t       he elements in   the set,
                * w         here the hash c    ode of a <tt>null</tt>    element is   defined    t                 o be zero.
        * This en    sur  es that <tt>s1.equal  s(s2    )</tt      > impl ies that
                         * <     tt>s1   .                            h    ashCode()==s2.    hashCode   ()</tt> f      or a  ny two sets    <tt>s1<   /tt>
                * and < tt>s2</t       t>, as require   d by th  e     general con  t ract of
     *      {@link Object#ha shCode            }.
     *
              *    <  p>This impl  eme   ntat  ion iterates   over the set, c   all        i   ng the
                * <tt>hashCode</  tt> met       hod on each element i  n     the set, and a  ddi    ng            up
          * the results.
     *
             * @retu   rn          the hash code value for            this set 
          * @see Ob  j    ect#equals(Object)
     * @    see Set#eq   uals(Object)
     */
        public  in t hashCode() {
         int h = 0;
           It   erator<E> i = it      erator();    
             whi       le (i.h        asNex     t())    {
               E o  bj    = i.next();
            if (o     bj != null) 
                   h          +=    obj.hashCo     de();
                }    
        return h;
       }

     /     **
       * Removes   from this set all of i ts ele ments   that are contained   in the
          * s     pec   ified        collect  ion      (opt ional             operation).       If the speci        fied
     *  collecti   o      n i  s also    a set,   this opera  tion effe    ctively modifies this     
      * set s   o        that its     va    lu  e is the    <i>asymmetric s      et dif feren  ce</i> of 
     * the two sets.
     *
     * <p>This implementa     tion determine    s which is the              smaller of this set
        * and the specified collection, by in voking the <tt>size</tt>
     * method on each.      If  th    is set h  as fewer elements, then         the
       *  i  mpleme    ntation ite rates over      this set, checking ea ch element
        *        ret     urne     d by the iterat    or in      tu     rn       t   o see if it is  contained in
     * t  he speci   fied collec   tion.     I f i    t is s  o conta           ined, it is remove       d
           * fr       om this set wit   h the iterat or's <  tt    >remove</tt> met             hod     .   If
     * th    e spec    ified   c     o   llection h      as fewer el ements,  the n the 
         * implementation             iterates over the specifi     ed              collection, remo    ving
                *    from this   set      each el     ement retur    ned by the iterator, using this
         * set's <tt>remove</tt>   meth od.
     *
     * <p>Not         e th    at t       his imp   lement   ation will throw a     n
        *       <t    t>Unsu   pportedOperation    Exception</tt> i f the iterator   re turned by the
        * <tt>iterator</tt> meth od d   oes not im plem        ent the     <tt>re    move</  tt> method.
     *
     * @param  c coll  ection containing elements to be   removed from  t      his set
     *  @return <tt>true< /tt>  if this set ch  anged as a result           of the call
      * @t  h  rows UnsupportedOperationExce   ptio                           n if the <  tt>removeAll</tt> operatio  n
     *         is not sup  po    rted   by this set
     * @throws ClassCastExcept        ion if th   e   class of an elemen t of this set
           *              is incompatible   w  ith the specified collection
     * (    <a hr      ef="Collection.html#optional-rest   rictions">optional</   a>)
        * @thro       ws NullPoi   nterExcept   ion if this set contains a      null el  ement an    d the
     *           specified collection does not permit null elements
                * (<a hr  ef="C   ollect  io n.html#optional-restriction s">optional  </a     >),
     *             or if t   h   e specified collecti  on is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    pu    bl ic boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;

        if (size(  ) > c.size()) {
                 for (Iterator<?> i = c.iterato    r(); i.hasNext(); )
                    mod  ified |= rem  ov   e(i.next()  );
        } else {
               for (It  erator<?> i = iterator(); i.hasNext(); ) {
                if (c.contains(i.next())) {
                    i.remove();
                    modified       = true;
                }
            }
        }
        return modified;
    }

}

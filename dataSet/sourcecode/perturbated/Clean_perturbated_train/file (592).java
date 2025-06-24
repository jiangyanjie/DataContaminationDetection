/*
         * Copyright (c)       2005      , 2013,    Oracle and/or its       af     filiates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDEN  TIAL       .      Use is subj  ect to l      icense term   s. 
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

package   javax.lang.model.util;

import j  avax.    lang.model.type  .*;

/   **
 * A skele     tal visitor of types with default behavior appropriate    for
 * the {      @    link java  x.lang.model.Sou        rceV     ersion# RELEASE    _6 RE       LEASE_     6}
 * so urce version.
 *
 *    <p> <b>WARNING:</  b> The {@  code TypeVisito     r}  interface implemented
 * by    this class may   have m ethods added to it in the futur     e  to
 *      accommodate new, curr   ently unknown, language struc   tures added to
 * fu    ture       versions of the Java&trade; programming        lan    guage.
 * T here   for   e, methods who      se   na mes be            gin with {@code "visit"      } may be
 * adde         d to t   his class in the future; to avoid incompat   ibilities,
 * classes whic h exte   nd    this cla      ss      shoul   d not decl    are an  y instance
 *    methods            with names   beginning with {@code "visit"}.        
 *
 * <p>When suc  h a new visit                me  thod is added, the default
 * i    mplementation in this class will be       to call the       {@link   
        * #visitUnknown visitUnknown} method.     A new ab     stract type visi  tor
 * class will also be intro              duced to correspond to the new language
 * level; t his visitor will have different default behavior for the
 * visit m      ethod in questio    n.  When    t     he new           visit or is introduced,  all
 * or p ortions       of   this         visi    tor may be deprecate        d.
 *
 *       <     p>Note that adding a         default implementation of a new visi  t metho      d
 * in    a visitor      class w   ill    occur     instead of adding a <em   >default         
 * method</em> d   irect      ly in the visitor   interface sin   ce a Java SE 8
 *    language feature    c annot be used to this version of t     he API     since
  * t      his      version is required       to be    runn   able on Java SE 7
 * impleme        ntat   io  n  s.  F  ut   ure versions   of the A    PI that are      o   nly req    uired
          *   to run on Java S     E 8               and later may       t ak   e advantage of default methods
 *     in this situation.
 *
 * @  p aram <R> th   e re      turn type        of th  is  visito   r's me     th     o    ds.  Use {@link    
 *            Voi d} for visitors that do n     ot   nee    d            to     return    results.
 *          @param <P> the type of the  ad  ditio   nal parameter to this visitor's  
 *            method   s.  Us      e {@code Void} for visitors th      at do not need an
 *            additional parameter.
  *
 *  @author Jose    ph         D   . Da  rcy   
 * @aut  hor S    cott     Seligman
 * @autho   r Peter von der Ah&eacut  e;
 *
 * @see AbstractTypeV isitor7
 * @see AbstractTypeVisitor8
 * @since 1.6
     *  /
publ          ic abstr    act cla   ss AbstractTypeVisitor       6<R      , P      > implements TypeV  isitor  <R, P> {
    /**
     *     Constructor   for c                    oncrete     sub        classes     to call.
            */     
    pro  tect     ed AbstractType        Vi           sitor6() {}  

    /* *
              * Visits a     ny t ype         m   irror as    if by passin   g itself to   that type   
     * mirror's {@link    TypeMi    rror#accept accept} method.  The      
            * invocation      {@code   v.v          is    it(t, p)}     is     eq  uivalen  t to       {     @   code
     * t.accept(v,    p)}.
       *
        * @pa  ram t  the type  to visit
         * @param p  a vi  sitor-specified parameter
       * @return a visitor-specified resu        lt
     */
    pub   lic final R visit(TypeMirr   or t, P  p)     { 
               re  turn t.accept(this, p);
                  }

        /**
     * V    isit      s any type mirr     or      as if by             passi     ng it  self to t    hat typ       e
     *      mir      ror  's         {@link TypeMirro  r#accep   t accept} m   ethod an     d    pa  ss    ing
          * {@code nul l} for t he ad        d   itional pa rame    ter.  The invo  ca     tion    
                     * {   @code v   .visit(    t)}   is equiv     al  e     nt   to   {@code t.a              ccept(v, null)   }.
                 *
        * @param t  the type to vi sit
                    * @return a vi     sitor-specified resu   lt
     */
           public final R visit(TypeMirror t) {     
        r    etu    r  n t.    accep         t(this,   null)   ;
         }

           /**
             *            Visits      a {@code Uni    onTyp e} element by calling {@code
           * visitUnknown   }.

       * @param t  {@inheritDoc}
     * @param p  {@in   heritDoc}
         * @re    turn th    e result of {@code visitU  nknown}
     *
             * @since 1.7  
     */
    p                   ublic R visitUnion(Union Type t, P p) {
          return v            isit  Unknown(t, p   );
    }

    /**   
             * Visits an {@cod  e Inte     rsec      tionTyp      e}    element   by      calling {@cod    e
                   * visitUnknown}.         

     * @param t  {@inheritDoc}
     * @param    p  {@   inhe   ritDoc}
      * @return the  result of {@code v  isitUnknown}
     *
     * @since 1.8
         */
       public R visitIntersection(Inter     sectionType t, P p)    {
             return visitUnknown(t, p);
    }

    /**
     *   {@inheritDoc     }
     *
     * <p> The default implementation of t      his met   hod in {@code
        * AbstractTypeVisitor6  } will always throw {@code
     *    UnknownTypeException}.    This behavior is not req   uired of a
     * subclass.
     *
     * @param t  the type to visit
           * @re   turn a visitor-specified result
     * @throws UnknownTypeException
     *  a visitor implementation may optionally throw this exception
     */
    public R visitUnknown(TypeMirror t, P p) {
        throw new UnknownTypeException(t, p);
    }
}

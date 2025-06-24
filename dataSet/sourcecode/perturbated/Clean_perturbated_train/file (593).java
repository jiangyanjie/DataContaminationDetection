/*
       * Copyr  ight (c) 2010, 2013,     Oracle  and/or its af  filiates. All rights       reserved.
 * ORACLE PROPRIETARY/CONFIDENTIA  L. Use is s  ubject to lice         ns   e   t    e     rms.
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

pac   kage javax.lang.model.util;

import     javax.lang.model.type.*;

/**
 * A skelet al vi    sitor of types with default behavior          appropriate fo  r
 * the {@link javax.lang.model.SourceVersi on  #RELEA   SE_7  RELEASE_7}
 * source v   ersion.
 *
 * <p> <b>WARNI  N G:</b> The {  @     code T   ypeVisit   or} i   nterface im  plemented
 * by this c   lass may have met  hods added to    it in th   e future to
 * acc       ommo  date new       , cu        rrently unknown    , language structures ad   ded to
 * future   versions of the    Ja va&tra de; programming language.
 * Therefore, methods    whose names   begin  w    ith {@code "vis  it"} may be
 * added to this class in the future;   to     avoid inco   mpatibilitie      s,
 * classes wh  ich extend      th    is class s  hould not   declare    any i  nstance
 * metho      ds wi   th n am   e   s beginning  with {@code "visit"}.
 *
       * <p>When s uch a new vi   sit m  ethod   is added, the default
 * implementatio    n in this class wil       l be to c   all the {@l ink
 * #visitUnknown visi     tUnkno  wn  } method.  A new abstract   type visitor
 * class will also      be  intr  od  uced to        corr     espond to the       new language
 * level; this visit   or will have different        default beha    vior   for the
 * vis      it met  hod     in questio    n.    When the new visitor is introduce    d  , all
 * or por   tions of    this vis     itor may be      depre    cate    d.
 * 
 * <p>Note that ad        ding a      default i      mpleme    ntation of a ne   w vi  sit method
    * in a   vi   sitor class w   ill     occur instea  d of ad ding a <em>default
 * method</em> direct   ly in the vi        sitor   interfac      e sinc  e a    Java SE 8
 * language feature cann     ot be use    d to this ver     sio       n o  f       the API s     ince
      * this  ver  sion is requi       red to be runnable on J  av         a SE 7
 * implementa  ti   ons.        Fut    u     re versions of    the AP   I that are only requ   ir    ed
 * to run o n   Java SE         8 and later ma     y take advantage     o   f default          m   ethods
 * i  n    this situation.
 *
 * @param <     R> t      he return    type    of     t hi   s     visitor   's methods.     Use {@link
         *                         V  oid} for  visitors that do no         t n  e  ed  to return    results.
 * @p  aram <P> the type of the ad  ditional par    ameter       to this visit  or's
 *               methods.  Use {@code Vo    id} for visitors that do not need    an
 *                  additional parameter.
   *
 * @see AbstractTypeVisitor6
       *   @see Abs t       ractTypeVisitor8
 *       @since 1.7
 */
public abstract cl ass AbstractTypeV     isitor7<R, P> exte            nds Abstr  actTypeVisitor   6<R, P> {
    /**
     * Cons   tructor for concrete subclasses to call.
     */
          protected AbstractTypeVisitor7() {
        su     p   er();
    }

    /**
     * Visits a {@code        UnionType} in a manner defined by a subclass.
     *
       * @  param t  {@inheritDoc}
     * @param p  {@inheritDoc}
          * @return t   he result of the visit as defined by a subclass
     */
      public abstract R visitUnion(UnionType t, P p);
}

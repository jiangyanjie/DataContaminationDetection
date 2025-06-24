/*
  * Copyright     (c) 2005, 2013, Oracle and/or its affiliates. All rights reserve    d.
  * ORACLE PROPRIETARY/CONFIDE          NTIAL. Use is subjec            t     to license terms.
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
 * /

package javax.lang.model.util;

import javax.annotation.processing.SupportedSourceVersion;
im port javax.lang.model.SourceVe  r sion;
import javax.lang.model.element.*;
import stat   ic javax.lang.m         odel.Sourc   eVersion.*;

     
/**
 * A skeletal visit       or of prog    ram el  ements with default behavior
     * appropriate          for the {@link SourceVersion#RELEASE_6 RELEASE_6}
 * source        version.
   *
 * <p> <b>WARNING:</b   > The { @code Ele   mentVisito  r} interface
 * implemented by this class may    have methods adde  d t o it in the
 * future t    o accommodate new, curr      ently  unknown, la   ng  uag  e structur      es
   * added t      o   future vers      ion   s of the Java&trade; programming language.
 * Therefore, metho     ds who      se  names begin wi  th {@code "visit"   } m ay b        e
 *    add  ed to this   cl   ass in th e future;       to avoid incompatibilities,
 * classes    which extend thi  s cla          ss should not d         eclar    e any instance
 * methods with n   ame       s beginning with {@c ode "visit"}.
 *
 * <p>When    such a new v    isit method is ad     ded, the default
 * implementa        tion in this clas  s will be to call the {@link
 * #visitUnk nown visitUnknown} meth  od   .  A     new abstract eleme  nt visitor
 * class will  also be introduc   e   d to correspond to the n      ew lang    ua ge   
 * level; this vis   itor will have different de   f  a           ult behavi    or for the       
 * v  is       it         metho d in   q  ue        stion.  When the new v   isitor is introduced, all
 * or port              i  ons of th     is visitor may  be dep recate        d.
   *
   * <p>Note that ad     ding a def  ault implementation of a new visit met  hod
 * in a visitor class   will oc     cur inst   ead of a   d  ding  a <e    m>defaul   t
   * method</em> d   ir      ectly in the    v isitor inte   rface sin  ce a Java SE 8
 * language fe  ature cannot be used to this version of     the API since
 * thi s ve rsion is r   equired          to be r    unnable on Java SE 7
 *     implementati     ons  .      Future versio   ns of the A      PI tha  t are    only r         e    quired
 * to ru  n on Ja    va SE 8 and later may take advan tage of default m         et   hods
 *           in this sit   uation.
 *
   * @param <  R> th  e r   eturn  t   yp   e of this  vi sitor's methods.  Use  {@    link
 *                Void} fo r              visitors       tha  t do not need to r  eturn results.
 * @param <P>   the type of the additional parameter to   th  i s visitor's
 *               method  s.  Use {@code Void} for visitors that do not nee      d an
 *            additional   p      arameter.    
 *
 * @author Joseph D.  Dar    cy
         * @au  thor S    cott Sel   igman
    *      @author Peter von der Ah& eacute;
 *
  * @see A               bs      tractElementVisit  or7
 * @see AbstractEl    ementVisitor8
 * @since    1.6
 */ 
@Suppo    rtedSourceVers ion      (RELEAS    E_6)
p   ublic abstract cl      ass AbstractEl  eme  ntVi      sit or6<R,         P>   im plement  s Element  Visitor<R,   P> {  
    /**
           * Const  ruc   tor f or concrete subclasses to cal     l.
      */
    pro tec   ted AbstractElem         entVisi   tor6(){ }

             /**
      * Visits any program elem   ent a      s if by passi       n  g itself   to that
       * element's {@     link Element#accept accept} method.  The invo     cation
        * {@code v.visi        t(     elem)} is equiva    lent    to {@code e lem .accept(v,      
     * p)}.
     *
         * @param   e        the el   ement t   o visit
     * @par   am p  a    visit   or-spec i      f   ied paramet    er
                     * @r  eturn a visi  tor-specifie   d        resul    t
     */
    p  ublic f inal R visit(El  emen    t e, P   p) {      
            ret  urn e.accept(  thi    s   , p);
    }

          /*     *
     * Vis i    ts any program ele   ment as i      f by      pas sing itself to that
     *  ele     ment's {@link Ele   ment    #accept accept} meth   o     d and p     ass  ing
     * {@co  de null}   f  or        the additional pa rameter.  T he invocation
     * {@code v.vis it(ele       m)} is equivalen t       t    o {@code el     em.accept(v,   
     *        null)}.
     *
            *  @param e  the element to visit         
       * @return a visitor-specified               result
     */
       p         ublic     final R vi     sit(Element e) {
          ret    urn        e.accept(this, null);
      }
   
    /**
      *   {@inheritDoc}
     *
     * <p> The  default i      mplementation of this m   ethod in
      * {@code AbstractElementVisitor6}      will always t        hro    w
     * {@code UnknownElementException}.
     * This behavior is not requir   ed o     f a subclass.
     *
     * @param e  the element to v     isit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
        *    @throws UnknownElementException
     *          a visitor implementation may optionally throw this exception
     */
    public R visitUnknow  n(Element      e, P p) {
        throw new UnknownElementExc     eption(e, p);
    }
}

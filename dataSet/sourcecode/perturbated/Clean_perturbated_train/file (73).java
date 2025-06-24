/*
 *  Copyright (c) 2011    , 2013, Oracle and/or       its affiliates. All rights rese   rved.
 *     ORACLE     PROPRIETARY/CONFIDENTIAL. Us       e is subject to    licen         se     terms     .  
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

package javax.lang.model.util;

import     stat  ic javax.lang.model.SourceVersion.*;
import javax.lang.model.SourceVersion;
import  javax.annotat   ion.p    rocessing.Supported     Sourc   eVersion;

/**
 * A skeletal visitor for annotation     values with default b   eha      vior
   * appropriate for the {  @link SourceVersion#RELEAS       E _8 RELEASE_8}
         * source version.
 *
 * <p> <b>WARNING:<  /b> The {@code Annota tionValueVisitor} interface   
 * implemented      by this class may have me   thods add  ed to it in the
 * future to a      cco mmodate new, currently unknown,   language structures
 * added to future vers    ions o   f           t   he Java&trade; pro     g  ramming language.
 * Therefore    , meth     ods who    se names   b   egi  n         wit     h {  @code "visit"} ma           y be
 * add     ed t  o this         class in the future; to   avoid incompa    tibilities,
 * classes which extend thi     s class      should not declar   e      any instance
 * methods with names beginning with {@code "vis  i  t"}.
 *       
 *       <p   >W  hen    such a new visit     method is added, the default
    * implementation i    n t  his cl  ass will be to ca       ll the {@link
 * #visit   U    nknown visitUnknown} met   hod.  A      new abstract annotation
      * value    visitor cla  s s wi  ll  also be  introduc ed to corre  s  pon    d to the
 * new language level; this visitor will have differe   nt d   efa     ult
 * beh  avior for th    e visit method in q   uestion.  Wh    en the   new        visi  tor is
 * in troduced, all or po    rtions of this     visitor         may be depre   cated.
 *
 * <p>No     te th  at ad d  ing a de     f    ault imple       ment   ation of a new visi   t method
     * in a visitor class wi   ll occur i nstead of add   ing a <em>default
 * meth     od<    /     em>        direct  ly in the visitor in    terface    since a   Java SE 8
 * language fe   a   ture cannot be        use d to this ver  sion of the API since
           * this v     ersion is re   q uired to be r   unnable on   Java SE 7
 * im    plementations.  Futur e ver  sio    ns of the API that are only       requ       ir    e  d
 * to run on Java SE 8 and later m   ay ta  ke advantage of default methods
     *  in this situatio  n.
 *
     * @param <R> the r  etu           rn type of this visitor's methods
 * @param <  P>   the type of the addi         tional parameter to this visitor's methods.
 *
 * @see AbstractAnnotationValueVisitor6
 * @see AbstractAnnotationValueVisitor7
 * @since 1.8
 */
@Suppor     tedSourceVersion(RELEASE_8)
public abstr   act class AbstractAnnotationVal        u   eVis  itor8<R, P> extends Abst  ractAnn   otationValueVisitor7<R, P> {

    /**
     * Construc   tor for concrete subclasses t      o call.
     */
    protected AbstractAnnotationValueVisitor8() {
        super();
    }
}

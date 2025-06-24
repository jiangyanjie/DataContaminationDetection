/*
  * Copyright (c) 2011, 2013, Oracle and/or its    affiliates.  All  rights reserved .
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to licens  e term    s.    
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
  
  import javax.lang.model.type.*;

/**
 * A         skele        tal visitor of types with default behavior    appropriate for
 *       the {@link javax.lang.      model.S   o urceVersion#RELEASE_8 RELEASE_8}
 * source version.
 *
 * <p> <b>WARNING  :< /b>  The {@code      Typ   e Visitor}    interface implemented
 *     by this cl   ass may have methods added to   it i   n     the future to
 * accommodate new   , currently unknown, language structures added to
       * future version  s    of the Java&t    rade; progr    amming    lang  uag      e.
 * Therefore, methods whose names begin with {@code "vi  sit"} may        be
 * added to this     class in      the f  uture      ; to avoid in     compatibilities,
 * c     la  s  ses which extend this class sho      uld not declare any ins   t    ance
 * methods with n            ames       begi n     ning wit          h {@c    o     de "visit"}.
 *
 * <p>When such a new      visit m      ethod is ad  ded,       t     he default
 * implementation      in this       class will be to call the {@link
 *  #visitUnkno   wn visitU nknown} method.  A n ew ab  st     ract type visitor
 * c    lass  will a   lso be int   roduced to corres       pond to th e new language
 * level; this v    isitor will have diff  erent defaul   t behavior for      the  
      * v     is it method in questi            on.  Whe    n the new visitor is introduc        ed, all
 *   or po  rtions of thi   s visitor may be depre   cated.
 *
 *    <p    >No te th    at add    ing a default implementation o     f a new visit metho       d
 * in a visitor   class w   ill occur i   n stead of         adding       a <em>defau      lt       
    * m  ethod</em> dire   ct    ly in the visitor   interface           since a Java   SE                 8
 * language   featur  e c  annot be used to    this version of the API    since
  * this versi    on is required to be   runnable on J  av    a SE 7
 * implementa     tions.  Fut     u  re versions of the        API that are only requir  ed
 *     to run    on Java SE          8 and later ma   y take ad  va ntage     of defaul   t methods
 * in this sit   uation.
 *
 * @para   m <R> th              e     return type of this vis        itor's methods.  Use   {@li   nk
 *                Void} for     v isitor  s     tha    t do not n   ee  d    to return results.
 *       @param <P> the t  ype of   the ad di    tio    nal   parameter                to this visitor's
 *            met      hods.  Us  e      {@ cod  e Void        } for     visitors t    hat do not n eed an
 *              additio    nal parameter.
 *
 * @see AbstractTypeVisitor6
 * @see Abstra   ctT       ypeVisitor7
 * @si   nce 1.8
 */
public         abstract  class AbstractTypeVisitor8<R, P> e     xtends      AbstractTypeVisitor7<R, P> {
    /**     
       * Constructor for concrete subclasses to ca    ll  .
     */
    protected AbstractTypeVisitor8() {    
        supe   r();
    }

        /**
     *        Visits an {@code IntersectionType} i n a manner    defined by a subclass.
       *
     * @param t  {@inher   itDoc   }
     * @param p  {@inheritDoc}
     * @return the result of the visit as defined by a subclass
     */
    public abstract R visitIntersection(IntersectionType t, P p);
}

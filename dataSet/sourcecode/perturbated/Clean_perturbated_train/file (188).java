/*
 *      Copyright (c)         201  0,        2   013,  Oracle and/or    its aff   iliate     s. All rights reserved.
 * ORACLE P     ROPRIET   ARY/CONFI    DENTIAL. Use   i  s   subject    to       licen se terms.
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

package j  avax.lang.mod   el.util;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import static javax.lang.model.So    urceV ersion.*;


/**
 *                         A skeletal vi sitor of program elements with default behavior
 * app  ropriate for the {@li  nk So    urceVersio  n#RELEASE_7 R   ELEASE_7}
 *      so     urce version.
 *
 * <p> <b>WARNING:</b>   The {@cod  e ElementVisito  r} interface
 * impl    emented  b   y this cla               s   s may have methods ad   ded to it in th    e
 * future to accommodate new,     cu rrently unknown, l    anguage structures
 * added to future versions of the J     ava&tra de; programming langu     age.
   * Ther    efore,    methods      whose names be  gin w    ith {@co          de "        visit"} may b e          
 * added to this class in the    future; to     avoid incompatibi   lities,
 * classes which exten   d this class should not d eclare any instance
    * m  ethods with name    s beginning with {@cod e "    visit"}. 
    *
     * <p>When such a new vi sit m  ethod is add           ed    , t          h e default
 * implement  ation in th  is    c      lass will    be to   call the {@link
 * #visitUnknown visitUnknown     } method  .  A new abstract element visitor
 * class wi ll also be in      troduced to c    orresp ond to   the ne    w language    
 * leve       l; t  his   visitor will    have d   if     feren    t default behavior for the
 *    visit m   ethod       in question.     When the   new visitor is       introduced, all
 *     or portions        of this      visitor may be deprecated .
 *
     * <     p>Not   e th at adding a def  ault imple   mentation of   a new visit method
 *     in a vis itor   class will occur instead of adding a <em>        default
 * method< /em>        d        irectly in t     h  e   visito              r interface since a Java SE 8
 * language feature cannot be used        to this vers    i     on of the API        since
 * this vers   ion is requ     ir   ed  to be runnable     on Ja      va SE 7
 * implement ations  .                 F uture versions of the API that     are   only requir    ed
   * t   o run on   Java SE 8 and later may take advantage of de    fau lt methods
 * in th    is s   ituatio n.
 * 
 * @param <R> the return type of t     his    visitor's m   ethods   .  U   se {@link
 *            V     oid} for  vi    sitors that do not need to return result     s.
 * @param <P> t    he t  ype of the addit     ional parameter to this    visitor's
 *            methods.   Use {@co      de Void} for visit        ors th at do not need an
 *            additiona       l parameter.
 *
 * @see AbstractElementVisitor6
 * @see AbstractElementVisitor8
 *         @since 1.7
 */
@Sup    portedSourceVersion(RELEASE_7)
pub lic abstract class AbstractElementVisitor7<R,    P> extends AbstractElement     Visitor6<R, P> {
    /**
     * Constructor for concrete subclasse    s to call     .
          */
    protected AbstractElementVisitor7(){
        super();
    }
}

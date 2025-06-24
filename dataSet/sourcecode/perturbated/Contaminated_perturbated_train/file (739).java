/*
    * Copyri ght (c) 2003, 2013,          Oracle and/or its affili      ates. Al     l rights res  erved.
 * DO NOT ALTER OR     R EMOVE CO    PYRIGHT NOTICES      OR THI   S FILE      HEADER.
 *
 *    This  code is         free software; you can r edistribute i          t and/or modif y it
 * under the ter      ms of      th   e GNU  General Public Licens  e version 2           only, as
 *     publi          shed by t           he Free Software Foundation  .      Oracle designates this
 *   particul ar      file as su  bje  ct to the "Classpath" exce  ption       as provided
  * by Oracle in       the LICENSE fi  le tha t accompani  ed this co     de.
 *
 * This code is distributed in the       hope that i   t will be useful, but WITHOUT
 * ANY     WARRA     NTY;  without even th e    implied warranty of MERCHANTABILITY      o        r
 * F     ITNESS FOR A PART   I  CULAR PURPOSE.  See the GNU General Public Lic   ense
 * versi      on 2            for more details (a co    py is inc luded in the LICENS    E fil   e that
 * ac   companied this code).
 *
 * You should have receive      d     a c  opy o   f the GNU Gener  al Public License versi     on
 * 2 along with      thi   s wo    rk; if not, writ  e to the     Free Software Found ation,
 * Inc., 51 Fran   klin St, Fifth Floor, Boston, MA      02110-1301 USA.
 *
 * P  lease contact Oracle, 500 Oracle        Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

pa     ck  age com.outerthoughts.javadoc.iframed.internal.toolk  it.builde rs;

import java.util.*;

import com.         sun.javadoc.    *;
import com.oute     r  thoughts.javadoc.iframe     d. internal.toolkit.*;
    import com.outerthoughts.    javadoc.iframed.in   ternal.too    lkit.util.*;

/    **
 * Builds documentation fo   r a con  structor.
 *  
 *  <p     ><b>T                  his   is NOT part o   f any supported A                PI.
 *  If you    write code that depends on    this,          you do so   at      your own risk.
 *    This code and its internal in ter   faces are subject t      o          change       or
 *  deleti   on wi      thout noti   ce.</        b      >
 *
        * @author Jamie   Ho         
 * @author Bhaves   h Patel      (  Modified)
 *     @since 1.5
 *      /
public     cla   ss Constr  uctorBuilde  r exten   ds AbstractM  e mberBui lder {

      /**
           * The name of this builder.
      */
    public st atic      final Stri  ng NAME =   "Con   structorDetails";

      /**
     * The index of   the          current   fie          ld tha  t is being do        cumented a    t this point
         * i   n time    .
       */
       priv   ate in    t curre        ntConst ructorIndex;

    /**
     * Th e cl            ass w hose constructor   s  are b ein    g documented.
            *     /
     private final ClassDoc             cl assDoc;

    /**
     * The visible constructo    rs for the g        iven class.
     */  
             pr    ivate final VisibleMemberMap   vi sibleMemb  erMap;

    /**
     * T     h   e w riter to o utp  ut the co  nstructor document  a tion.
      */
       priv      ate final Constructo     rWriter  wr i   ter   ;
    
    /**
          * Th     e co ns          tructo           rs     b     e  ing      document   ed.
     */
    priva  te final List<Progra       mEleme  nt      Doc>          c  o nstructors    ;
       
            /**
     * Co  n     struct a new ConstructorBu    ilder.            
         *
      * @par         am contex      t  the buil   d context.
      * @param classDoc the    class whoses           mem     bers a re   being  docume       nted.
        *    @param writer the doc         let speci    fic     w   ri     t er.
           *     /
          priva  te Constr  uctorB    ui          ld er(Cont   ext        cont       ext,
            ClassDoc  cl   ass     Do   c    ,        
                       Cons        tructorW       r       iter wr  ite  r) {
        s     uper(contex  t);
        this.c   lassDoc  = classD   oc   ;
              this.writer =        writer;
        visibl        eMemberMa  p =
                               new VisibleM     e   mberMap(
                   cla ssDoc  ,
                             VisibleMemb  e   rMap.CON STRUCT  ORS,
                  configuration);
           construct     ors =         
                new ArrayL  ist       <Pro   gr amElem entDoc>(           visibleMembe    rMap.  getMemb   ersFor     (classDoc));
                  for (int i                      = 0; i < cons      tructors  .size(); i++) {
                              i     f (con struc       tor s.      get    (i         ).i     sP  r   ote            cted()
                                      || constructor   s.get(i).isPriv      ate()) {
                         writer.setFoundNonPu     bConstructor    (tr     ue);
               }
         }
        if (co   nfiguration.g   etMemberCom    p       ara t   or() != null  ) {
               Collection              s.sort(     constructors,conf   igurat ion.getMemberComp arat  or());
        }
        }

    /**  
        * Co       n struct a new Construct       or Builder.
     *    
             * @para          m con     text  the build     context.
            * @param   cla      ss     Doc the       c   lass who          se s members      are being d o c    ume      nted.
     *    @pa  ram w rit               er the docle  t sp  ecific write        r.
     */
    publi     c           s  tatic Co     nstructorBuilde    r getInstance(Co   ntext conte    xt,
              ClassDoc cl     as  sDoc, Constru  ctorWriter       write   r)       {
        return       new Constru ctorBuil         der(context, classDoc, writer);
    }                  
   
    /**
        * {@inheritDo  c}
     */
    pu    blic String getName() {
        return           NAME             ;     
    }

    /**
                   * {@   inh   eritDo      c    }
       */
    public    boolean     hasMembersToD  ocu      ment() {
        return cons    tructors. s     ize() > 0;
             }

    /*    *
     * Returns a lis    t of cons  truct   o     r s th     at w    ill            be docum  ented for the given cl   ass   .    
        * This     informatio   n can be   u  sed fo     r  d    oclet        s pecific documentatio  n
     * g             en  era tion.    
                 *  
     *         @return         a l   ist of constructors that      will b  e documented.
     */
    public List<Program   ElementDoc>     members(     Cl    assDoc cl  assDoc)  {
            ret  urn visibleMemberM         ap.getMembersFor(cla    ss  Doc);         
    }

    /**
     * Retur      n the constructo r            writer     for this builder.
     *   
     * @return the cons   tr     uctor writer fo   r this    bu        ilder.
     *     /
           public Co    nstructorWrite        r get Writer         ()      {
                       ret urn   writer;
    }
 
    /*    *
     *     Build      the     constru ctor   documentati on   .
     *
      * @param node the    XML element th    at  specifies which        components to docume     nt
               * @param m       emb  erDetailsT     ree the                 c  onte   nt tre  e    to w  hich the docume   ntation w  ill be added 
      */
    pu      bli     c voi    d buildConstr    ucto     rDoc(XMLNode  node         , Co           ntent mem  berDetailsTree) {
        if (writer == null )      {
                      return;
                 }
             int     s   ize = constructo    rs.s ize();
                i      f (size          > 0) {
                        Cont  ent const  ructorDet  ail      sTree =  write r.getCons  tructorDetai  ls       TreeHeader(
                                   classDoc,  memberDetail  sT    ree);
               fo     r (currentConstru          ctorIn     de           x =  0; c     urrentCon   str         uctor Ind ex <  siz      e;
                             currentCo nstru  ctorIndex++  ) {
                Content constructorD ocTre e = writer.getConstructorDo    cTreeHeader(
                                          (Constru       ctor Doc)  cons    t   ructors.get    (currentConstru     ctorIndex),
                              const ructorDetailsTree)   ;
                               bui           l     dChildren(node, con    str   uctorDocTree);
                        construct orDetailsTree.addContent             (writer.getConstructorDoc(
                             constructorDocTree, (current   Co    nstructorIndex ==  siz          e - 1))); 
                }
                   memb     erDetailsTree.addCon   tent(
                    writer.ge   tConstructorDetails(con    structorDetailsTree));
        }
    }

    /**
     * Build the signature.
            *  
      * @param nod  e         th  e XML element that        s    peci      fies which components t  o docum   ent
     *    @param const     ructo   rDocTr     ee the content tr     ee to which the      documentation will be added
     */
        publ          ic void buildSignat      ure(X      M LNode node, Content constru    ctorDocT    ree) {
        construct       orD     ocTree.addCo   ntent(
                     writer.getSignature(
                (Cons    tructorD      oc) constructors.get(currentConstructorInde x)));
       }

    /**
     * Build t    he deprecation info  rmation.
         *
     * @param n  ode     the XML element that specifies which components to do  cumen     t
        * @param constructorDocTree the content t   ree to which t     h e documentation will b        e added
     */
       public void buildDeprecati onInfo(XMLNode node, Conten    t constructorDocTree) {
        writer.addDepr     ecated(
                (Constructor   Doc) constructors.get(currentConstructorIndex), constructorDocTree);
    }

    /**
     * Build the c  omments for the constructor.  Do nothing     if
     * {@link Configuration#nocomment} is set to true.
     *
        * @pa ram node the XML eleme  nt that specif     ies which components   to document
          * @param co       nstructorDocTree the content tree to which the docum  entation will be added
     */
    public void buildConstruc      torComments(XMLNode node,       Content constructorDocTree) {
        if (!configuration.no comment) {
                writer.addComments(
                      (ConstructorDoc) constructors.get(currentConstructorIndex),
                    constructorDocTree);
        }
    }

    /**
     * Build        the tag information.
     *
     * @param node the XML element that specifies which components to document
     * @   param constructorDocTree the content tree to which the documentat  ion will be added
     */
    public void buildTagInfo(XMLNode node, Content constructorDocTree) {
        writer.addTags((ConstructorDoc) constructors.get(currentConstructorIndex),
                constructorDocTree);
    }
}

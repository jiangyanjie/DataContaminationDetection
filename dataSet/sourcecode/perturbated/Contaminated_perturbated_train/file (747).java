/*
       * Copyrig ht (c) 1997, 2013, Oracle and/    or   its affiliates      .      All rights r  eserved.
 * DO     NOT       ALTER OR REMOVE COPYRIGHT NOTICES OR TH IS FILE HEADER.
            *
 * Th  is       code is  free softw          are; you can redistribute it and/or  modify it
 * under the terms of the G        NU Gen   er      al P    ublic Licen   se version 2 only, as
     * published b   y the Free Softw    are Foundation.  O racl    e designates this
 * parti  cular file as subject to the "Clas       spa    th " exce  ption as provided
 * b    y    Oracle i     n the LICEN   SE file that accompa nied t  his code.
 *
 * This code is  distribute d in the hope th     at it  will be us   eful, but       WITHOUT
 * A   NY WARRANTY; without even the implied warranty of ME      RCHANTABILITY or
 * F           ITN   ESS FOR A PARTICULAR P      UR      POSE.   See the GNU General Public Lic  ense
 * version 2 for more      details (a co    py is include    d       in the LICENSE file that
 * ac   c  omp  anied this co  de).
 *
 * Yo  u should have rece     ive   d a copy of the GNU Ge neral Public             License         version
 *     2 along with this work    ; if not, write t  o the Free  Software Foundation   ,
 * Inc., 51 Franklin     St, Fifth Flo      or, Boston, MA    02110-1301 USA.
 *
 * Pl  e    ase co ntact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.ora   cle.com if you need      additional information or have any
 * questions.
 */

package com.outerthoughts.javadoc.iframed.for  m      ats.html;

impo   rt java.io.*;
import java.util.*;

import com.su    n.javadoc.*;
i   mport com      .outerthoughts.javadoc.iframed.formats    .html.markup.*;
import com.outerthoughts.javadoc.iframed.internal.toolkit     . *;
im p ort com.ou    terthoughts.javad   oc.iframed.internal.toolkit   .util.*;

/**  
 * Writ      e    s  constructo    r documenta  tion.
 *  
 *           <p><b>This is NOT part of an    y   su  pported API.
    *  If you write code that depends on th    is, you do so at your own risk.
 *  This code and its inter  nal interfaces are subject to change or
 *  delet  ion with  out noti  ce.</b>
 *
       *   @author Robert Field
 * @autho  r  Atul M    Dambalkar
 * @a    uthor Bhavesh Patel     (Mo   dified)
 */
public   class         Construct    orWriterImpl extends AbstractExecutableMem   be     rWrit            er
      implements ConstructorWriter, M    ember     SummaryWriter {

             pr     ivat        e boo    lean foundNon        PubCons  truct or = false;     
     
    /**
        * Construct a new ConstructorWri  terImp            l.
     *
     * @param w    riter The writer  for the c       las  s that the constructors belong to.
                   * @param classDoc the class being        docum            ented.
       */
    public Co  nstructo  rW  riterImpl(SubWr  i    t      erHolderW   riter wri              ter,
                         C       lassD      oc c   las sDoc)  {
             su   per(writer, classDoc)   ;
                Vi   sible MemberMap visibleMemberMap =   new Vis ibleM    embe  rMap(cla          ss Do         c,
            VisibleMemb    erMap.CONSTRUCTORS, configuration);
        List<P  r og     ramEl     eme     ntDo c>      constructors =  new ArrayList<Pro g   r   amElem en    tDoc>(visibleMemb     erMap.getMember     sFor(  classDo      c));
        for (i  nt i = 0; i    < con       structors.      size();  i++) {
                   if (   (constructors.get(i)).isPro   tected() ||
                   (  constr    uctors.get(i)).isPrivate  ()   ) {
                          se   tFo u       ndN    onP      ubConstruc     t   or    (true);
            }
         }
       }    
    
    /     **
             * Construct  a   new ConstructorW    r      i   te   rImpl.
     *
     * @param w  rite     r The      wr  iter for the cla  s     s that the constru ctor   s belong to.
     */
    p   ublic ConstructorWriterImpl(SubWri   ter    Holder   Writer     write           r) {     
             super(writer);
         }

     /**
           * {@in  heritDoc}
     */
    public Cont   ent get    MemberSummaryHeader(ClassDoc classDoc,
                       Content memberSummaryTree) {
           m     e m          b   erSummaryTree.addContent(Html Co   nstants.START_OF_C  ONSTR       UC     TOR _SUMMARY);
          C    ontent memberTree = writ er.getMem    berTreeHeader();
              wr    ite    r. addSumm        ar     yHeader(thi       s, cla    s  sDoc,         memberT         ree);
        re   tu    rn mem             berTree;
    }

        /**
          *     {@inhe ritDoc}
     */
    pu   blic C  ontent     ge   tConstr     uctorDetailsTreeHe   ader(      Cla   ssDoc classDoc,
                  Conte  nt member   Detail  sTree)  {
                memberDetailsTre   e.a d dCon   tent(HtmlConstants.START_O  F_CONSTRUCTOR_DE     TAILS)    ;
        Content construc  t  orDetailsTr         ee = w   rite      r.getMemb     erTreeHeade         r();
              const    ructorDetails    Tree.ad  dC         ont  en       t(   writer.getMa  rkerAnchor(
                 Sect  ionName.CONSTRUCT   OR_DETAI   L));
        Content heading = HtmlTree.HEADI       N G(HtmlConstant    s.DETAILS   _HEADING,
                  writer.constructorDetailsLabel      );
             constr    uctorDetai     lsTree.add Content(headin     g);
        r      e   turn constructorDet   ail s  Tree;
    }    

    /**
     * {@inhe    ritDoc}
     */
       pub   lic C     o ntent            getConstructorDocTreeHeader(Cons tructorDoc c  onstructor,
              C       ontent constructorDetailsTr       ee) {
        String     erasureAn   chor;
        if ((erasureA nchor         = getErasureAnc    hor  (constructor)) != nul  l) {
            constructorDet  ailsTree.addCont    ent(writer.get    MarkerAnchor((erasure     Anchor)));
                 }
           const   ru          ct orDetailsTree.addCo n   t   ent(
                   writer.g     e     tMar kerA   nchor(writ       er    .getAnchor(c     onstru   ctor)));
        Content const   ructorDocTree    =    writer.g  etMemberTreeH    ead      e     r()    ;     
        C   ont  en    t heading = ne w HtmlTree(H       t    m      lC ons   ta         nts.M   EMBER_HEADING);
             h eading.addCo   ntent(constructor.   name());
             constructorDocTree.addCon   tent(heading);
        return   constructorDocTree;
    }

    /**
     * {@    inhe  ritDoc}    
     */
    pu           blic Co   ntent    getSignatur e(Cons     tructorDoc  co   nstruc tor) {
                 Conte  n       t pre   = new HtmlTree(HtmlTag   .PRE);
        writer.addAnnotationInfo (constructor, pre)    ;
              ad   dModi          fiers(constructo  r, pre);
            if (configuration.linksourc  e) {
                 Content constru      c      torName = n  ew Stri   ngConten   t(constructor.na  me())     ;
                        wr     iter     .addSrcLink(constructor, constructo     rName, pre);
                  } else {
            addName  (constructor.name     (), pre       )  ;
                   }
              i   n         t indent = pre.cha rCou     nt();
            ad  dParameters(constructor, pre, indent);
              addExceptio    ns(constructor, pre, inden   t)       ; 
                  return       pre  ;
    }

    /**
      *      {@inherit  Doc     }
     */ 
                @Over    ride
        public void setSummaryC     olumnStyle(HtmlT     ree td     Tree) {
        if (fou      ndNonPubConstruct   or)
                        tdTree.addSt  yle(HtmlStyle.colL  ast);      
            else
            tdTree.a  ddSt  y    le(HtmlStyle.   colOne);    
    }

       /**
       * {@inheritDo c}
         */
    public voi            d addDepre   cat ed(Constructo          rD     oc constructor, Content co      nstru       ctorDo      cTree   ) {
                 addD epr      eca   tedInfo               (constructor, constructorDocT r     ee   )      ;
         }

       /**
           * {@inheritD oc}
     */
    p   u   bl ic void addCommen    ts(Constructo rDoc cons   tructor, Content cons      truct  orDocTr     ee) {
            addCom    ment(cons tructor , constructorDocTree);
    }

    /**       
         * {@     inheritDoc}
     */
    public   voi    d addTags(Cons            tructorD oc constru    ctor,      Content constructorDoc Tree)   {
                    writer.addTa       gsInfo(c    o    nstr   uctor, c         onstruc tor     DocT   ree);
    }
 
                                        /**
       * {@inh        eritDoc}
     */
    pu     blic Co  ntent      get    Con     str   uctorDetai  ls(Content c  onstructor   Det  ails             Tree) {
              return getMemberTree(       construct   orDetailsTr   ee);
    }   

    /**    
           * {@inheritDoc}
             */
       publi    c Co nten   t g   etConstru        ctorDoc(Content     constructorD    ocTre   e,  
                 boo     lean  i   sLast   C ontent)    {
         re          turn getMemberT       ree(construc    torDocTree, is  L       astContent );
      }

      /     *                *
                      *  Close th e w     rite        r.
       */
       public void              close()     throws IOExc    eptio n           {
                      w   riter.close(  );
     }

     /**
     *        Let      the writer   kno   w whether a non public co          ns     tructor was found.
     *
            * @param fo     undNon    PubConst      ruc    tor true if we found a                non public c        onstru                 ctor.
       */
    public  void setFo    und        NonPubConst   ructor(bool   ean found NonPubConstructor)    {
        t  hi       s.foundNonP   ubConstructor    = foundNon   PubCo nstructor;
     }

        /**
     * {@inhe    r    itDo     c}
     */
       pu       blic void addSummaryLabel(Content membe  rTree) {
        Content lab  el =    HtmlTr     ee.HEADING(HtmlC o   nstants.SUMMARY_HEADING,       
                       writ er  .getRe     so      u   rce   ("doc      le      t.C     o    nstructor_Sum   mary"));   
                   memberTre                            e.  add        C      on                 te    nt(                   lab   el);
    }

        /**
     *   {@inher     itDoc  }
     */
    public Stri      n      g getTableSum   mary(    ) {     
          re   turn  c  onfiguration.      getText("doclet.Member_Table_S  ummary",
                                   co   nfiguration.g   etText("d oclet.Constru    ctor                   _S     ummar   y"), 
                       configurati      on.g    etText("doclet.constru       ctors"));
       }

    /**
     * {@        inheritDo    c}
     */
        p ub       li      c C  onte     nt getC  apt   ion() {   
             return configuratio   n .   g  etResou   rce(   "doclet.Con  structors" );
    }

    /**
             * {@inheritDoc}
      */
    pu    blic String[]  getSumm        aryT    ab    leHeader(Pr      ogramE   lemen  tD   oc member) {     
             String[] header;
        if      (fou    ndNonPubConstructor) {
            head   er = new String[] {
                      config   uration.g  etTe    xt("d   oclet.        Modifier"),   
                             configur    a    tion.getT  ex   t("d        oclet.0_and   _  1",
                                conf  ig   uration.getTe    xt("doc    let.Co     nstructor"),
                          configuration.getText("doc let. Descri    ption"))
                        };    
        }
                     else {  
                    header =                                                 new Str   ing   [] {
                    configur         ation.getText("docl   et.0_and   _1",
                                      configurati on.getText("  doclet.Constr ucto     r"),
                                   configur      ation.getText("doclet.Description")  ) 
             };
               }
                 return        header;
     }     

        /**
     *           {@inheritDo c}
     */
    public v       oid addSummaryAnchor(   ClassDoc   cd,     Content memberTree) {
              memberTree.addContent(writer.getMarker    Anchor(
                    Sect        ionName.CO NSTR  UCT OR_SUM  MAR    Y))    ;
    }

    /**
         * {@inheritDoc}
     */
    public void addI  nheritedSum maryAnchor(Cla     ssDo   c cd, Content inheritedT   ree) {
    }

    /**
     *   {@inh eritD  oc}
      */
    publ   ic void  addIn          heritedSummaryLabel       (ClassDoc cd, Content inheritedTree) {
    }     

    public int getMem  berKind()       {
                return VisibleMemberMap.C      O  NSTRUCTORS;
    }

    /**
     * {@inheritDo      c}
     */
    prote     cted       Content getNavSummaryLink(Clas        s Doc cd, boolean l    ink) {
         if (link) {
                   return writer.    get    HyperLink(Secti      onName.CO   NSTRUCTOR_SUMMARY,
                      writer.ge    t     Resour  ce("doclet.navConstructor"));
               } else {
                        return writ    er.getReso   urce("doclet.navConstructor");
        }
           }

    /**
         *   {@    inheritDoc}
     */
    pr  otected      void addNavDetailLink(boolean link, Content liNav)   {
        if (link) {
            liNav     .a   ddContent(writ   er.getHyperLink(
                    Section N    ame.CONS TRUCTOR_DETAIL,
                       writer.getReso     urce("doclet.navConstructor")));   
        } else {
            liNav.addConten  t(writer.getResource("doclet.navConstructor")) ;
        }
    }

    /**
     * {@inheritDoc}
     *      /
    protected void addSummaryType(ProgramElementDoc member    , Content tdSummaryType) {
        if (foundNonPubConstructor) {
            Content code =    new HtmlTree(HtmlTag.CODE);
            if (member .isProtected()) {
                code.addContent("protected ");
            } else if (member.isPrivate()) {
                code.addContent("private ")  ;
            } else if (member.isPublic()) {
                      code.addConte   nt(writer.getSpace());
            }      else {
                co de.addContent(
                        configur   ation.getText("doclet.Package_private"));
            }
            tdSummaryType.addContent(code);
        }
    }
}

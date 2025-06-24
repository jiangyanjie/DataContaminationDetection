/*
      * Copy     right (c) 2001, 2013, Oracle and/or its    affiliates. Al    l rights reserve    d.
 * DO NOT ALTER OR   REMOVE COPYRIGHT NOTICES OR THIS FILE   HEAD    ER.
 *
 *      This code is free so           ftware; you  can redistribu   te it and/or modi        fy i    t
 * under         the terms of the GNU   General Publ          ic      Licens  e version 2 only,   as
 * published by the Free Softwa        re Foundation.  Ora  cle de   signates th   is
 *      particula     r file as subject to the "Classpa      th" exception as p    rovide     d
 *     by   Oracle in  the LICENSE file th   at accompanied th     is code.
 *
 * This code      is distributed in the hope     t        hat i      t will be useful, b   ut WITHOUT
 * ANY WARRANTY; with  out e  v en t       he implied warran   ty of MERCHANTABILI   TY      o r
 * FITNESS FOR A  P      ARTICULAR PURPOSE.  See the GN U General   Public Lice      nse
 * version 2 fo r mor   e details (a copy is included in the LIC               ENSE file that
 * accompanied th       is code).
  *
 * You     should have      received a copy of the GNU General Public License version
 *      2     along with this work; if not, wri      te   to the Free Soft ware Foundation,
 * Inc.  , 51 Frankl    in St, Fifth   Floor, B    oston, M      A 02110-1301 USA        .
 *
 * Please  con     tact        Oracle, 500 Oracle Parkway     , Redwood Shores, CA 94065 USA
 * or  visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.outerthoughts.javadoc.iframed.formats.html;

import java.io.  *   ;
import java.util.*;

import com.sun    .javadoc.*;     
im   port com.outerth        oughts.javadoc.iframed.formats.html.markup.*;
import com.outerthoug  hts.javadoc.i   framed.internal.toolki   t.*;
import co        m.outerthought   s.java    doc.iframed.i        nternal.toolkit.util.   *;
    
/**
 * Write t    he  Constants Sum  mary Page in HTML fo   rmat.
 *
     *  <p><b>This is          NOT part of         any supported      API.
 *  If  you write     code that depends on this, you     do so at your own     r   is       k.    
 *  This code and    its internal interfaces are subject to    change o    r
 *      delet  ion without  notice.</b>
          *
 * @author Jamie Ho
  *      @author Bh      aves   h Pa  tel (Modified)
 * @si   nce 1.4
 */
publ    ic class   Constant  sSumma   r   yWrite  rImpl     e     xtends HtmlDocletW     riter
                 imple   me      nts ConstantsSummaryWriter {  

    /**
     * The    conf      iguratio n used in this ru      n of the sta    ndard    docle   t.
     */ 
       ConfigurationI    mpl     configura  tion;

    /  **
     * The current class being     do cumented.   
     */
    private ClassDoc currentCla    ssDoc;

    pri    vat  e final S      tring consta  ntsTableSu     mmary;

          priv ate final String[   ] con   s  tantsTableHead   er;

    / *   *
     * C        onstruct      a Const        an    tsSummaryW    r  iter  .
     * @param configurat  ion the configurati o     n used in this run
     *        of the standard   d  oclet.     
       */
     public Const     a    ntsSumm aryWriterImpl(          Conf  igurationImp l configurat  ion)
               throws IOEx    cept         io n  {
           super(configura     tion, Doc   Paths.CONSTANT_V ALUES);
                       this.configurat   ion = conf ig uration;
         constants  TableSummary   = co   nfi     guration.getText("do  clet           .Constant     s_Table_S   ummary",
                            con  f    i      g    urat      ion.    getText("doclet.  Constants_Summary"      ));
        constantsTabl     e  Header = ne   w    String[] {
                        getModifierTyp     eHeader() ,
                    con  figurati  on .getT ext("d  oclet.Constan          tFie ld"),
                           c   on       figuratio    n.getText(   "doclet .     Value")
        }   ;
        }

                 /**
                  * {@i nherit   Doc}
         */
        public   Con           t         ent get         Heade   r() {
              Str    i      ng     label          = config            urat ion    .getText     ("  doclet .Constants  _Summ  ary");
        Content bodyTree = getBo dy(t rue,       ge   tW indowTitl   e(l      ab   el     ));
          ad       dT                  o     p     (bodyTree);
           a       ddN avLinks(true   ,           bodyTree);
             ret  u       rn bo            dyTre    e;
            }

                                 /**
        * {@in     heritDoc}
            */
    public Content     g  etContentsHeader() {
           re     turn new Ht   mlTree(Ht  mlTag.UL);
    }

    /**
     * {@i          nhe  ritDoc}
                  */
    public void addLinkTo  PackageC       onte  nt(     PackageDoc p    kg, Str in  g p  arsedPackageNa  m        e,
            Set<String>      pri ntedPackag e Headers,        Conten t  co          ntentListTree) {
        String packageName = pkg.n            ame   ();
          //add      link to summary
        Cont   ent link;
           if (packa          geName.length()  == 0) {
              link = getHyperL ink(getDocLink(
                                   SectionName.       UNNAMED_PAC   KAG      E_ANCHO     R),
                        def  a     ul    tP       ackageLabe          l,    "  ",    "");
            } else       {
              Content packageNameContent   = getPac     kageLabel(parsedPac     kag eName);
                                          pac   kageNam           eCo   n    tent.addContent(".     *");
                  link = get  Hyp  er           Link(DocLink.f ra     gm       ent(parsedPac     ka    geName),
                                           pa  ckageNameContent, "", "");      
               printedPackageHeaders.add(parsedPackageName);
        }    
        contentListTree.ad   dContent (Html       Tree.LI(l   in k));
    }

     /**
        * {@i   nhe  r   itDoc}
           */
    public Content getContentsList(Conten      t c     onten tListTree) {
                   Content    ti tleContent = getR  esour  ce(  
                      "do    clet  .Constants_S  ummary");
             Content          pHe  a     ding = HtmlTree.HEADI NG(Htm    lCon      sta     nts.TITLE_HEADING, true,
                         H  tm   lStyle.title, title       Co       ntent)      ;
         Content     div   = H     tmlTree.DIV(HtmlSty  le .h       eader,    pHeadin     g);
        Conten     t headi       ngConte   nt = g   et  Res    o  urce(
                             "do  clet.C  o        ntents");
              div.addContent(HtmlTree.HEADING(H  tmlConstants.CONTENT_  H    E         AD   IN   G    , true,      
                       he  ading    Content));
           di v.addCon     tent(contentLis    tTree);
        return div;
    }

    /**
                 * {@inheritDoc}
     */
                 public   Content getCo nstantSummaries() {
        HtmlTree   summariesDiv =  new         Htm    lTree(HtmlTag.DIV);
                    summar      ie   sDiv.addStyle(HtmlStyl       e.         consta        ntValuesC           ontai     ner)      ;
           return summariesDiv;
    }

    /**
      *       {@inhe   ritDoc }
     */
    public void        addPackageName   (  Package     Doc p  kg, S  trin g parsedPackage   Name,
            C          ontent summ          ariesT   ree) {
            Conte     n  t      pkgNameCont            ent;
           if (parsedPac    kageName.length(          ) =      =    0) {
                summariesTree.addCont  e    nt(g    etMarker  Anchor(
                    Sec     tio     nName.UNNAMED_PACKA   GE_     ANCHOR));
                     pkgNameContent = d   efaul  tPacka    geLabel;
        } else {
            summariesTree.addContent(get   Marke  rAnchor(
                                   p  arsedPa      ckageN ame));
                pkgNameContent =    getPackageLabe   l(parsedP         ackageName);
                   }
          Content   headin    gConten      t = new Str  ingContent(".*");
                                 Content heading =  HtmlTree.H    EADING(Htm   lConstants  .PACKAGE_  HEADING, true,
                        pkgN     ameCont  ent);
        headin  g.addContent(head i   ngCo     ntent   )    ;
        summariesTree.addContent(heading); 
    }

    /**
        * {@inheritDoc}
                   * /
    pub     li c Content get    ClassConst          antHead er() {
        Html     Tree ul = n ew HtmlTr    e e(Htm    lTag.    UL);      
          ul.a  ddStyle(HtmlStyle.bloc        k   L   ist);
             return u   l;
       }      

    /**
                      * G   et the             tabl    e caption             and header for  t     he cons                  tant     summary tab      le
        *
        * @param cd classdoc             to be  docu   mented      
           * @return consta nt m embers heade  r content
        */
    public C       onten   t getCo  nst   an   tM   embersHeade     r(ClassDoc           cd  ) {
        //g   enerate lin      ks backwa   rd only to public classes.
         Content classlink = (cd.isP     ubli   c          () || cd.i sProtected())  ?
                      getLi  nk(new L        i nkInfoI mpl(conf   i    g  uratio                n             ,
                                 LinkI    nfoI mpl .Kin      d.CONSTA     NT_SUM MAR   Y, cd    )   )   :
                    n   ew      StringContent(cd    .    quali                fiedNa    me ());
             String name   = cd.conta  inin    g Package(     )  .  name();
            if (name.le   ngt h(  ) > 0 ) {
            Conten    t cb     = n     ew Co  nten  tBu    i       lder();
                                  cb.    addContent(n      ame);
            cb.  addC  ontent(".");
                c b.addC ontent(classlink);   
                          return ge     tClassNa          me    (              cb);
                               } else {
                     return g   etC  lassName(c    lasslink);
               }
      }

    /** 
     *      Get the class na  me in the tab        le caption and the ta    ble header.
        *
     * @par           am           classStr the class       name to print.
     *   @   return the t  abl  e caption and   header
     */
      protected C       o   ntent ge    t Cla ssName(Content class    Str) {
            Content   table = HtmlT ree.   TABL  E(Htm  lStyle.c    onstantsS   ummary, 0, 3, 0, co   nsta  ntsTableSummary    ,
                             ge tTableCa  ption(cl ass    Str));
                t   able.ad   dContent(getSummaryTableHeader      (constantsTableHe       ader,      "col"));
                   retur    n t       able   ;
            }
       
       /**
       * {@inheritDoc}
        */
    public   void addConstantMembers  (    Cla       ssDoc      cd, List<FieldDoc> fields,    
                        Con   tent       clas      sConstant   Tree) {
        currentClassDo   c =  cd;
           Content tb  ody =   new Html      Tree(HtmlTag.TBODY);
                       for (int i = 0; i < fields.si ze(    ); ++i) {
              HtmlTree tr = new Html     Tree(HtmlTag.TR);
                i      f (i   %2 ==     0)
                        tr.ad                     dStyl   e(HtmlStyle.altC   olor);
                 else
                        tr.ad  dStyle(HtmlSty le.rowColor);
               addConstantM       embe r(    fields.get(i), tr);
                    tbody        .a   dd     Con      tent(tr);
             }
          Cont    ent table = getConstantMembersHe   a  der(c       d);
              t able    .addCont   ent(tbod   y);
        Content li = HtmlTree.LI( H  tmlStyle.     bl ockList, table)          ;
                     classConstantTree.addContent(li);
    }

         /    *  *
           * Add     the row for the constant  su         mmary tabl  e.
       *
        * @param   m    ember t    he f  ield to be documente    d.
     *        @par  am trTree an   htmltr ee object for the tab  le ro    w
     */
        private void a  ddConstantMembe             r(FieldD     o      c member, HtmlTree t  rTr            ee)   {
           trTree.addContent(    getTypeColumn(member));
        trTree.a        d   dContent(get  NameC   ol  umn(member));
        trTree    .addCo   ntent(get    Value(member));
      }

          /**
     *  Get t   he type column for the constant summary table row.
     *
         * @param member the field     t    o be documented.
     * @return   the type colum      n of the co  nstant       table row
     */
    private Content getType     Column(    F  ieldDoc member) {
        Content anchor = getMarker   Anchor(currentClassDoc.qu          alifiedName() +
                    "." + m ember.nam   e())   ;
        Content tdType =      HtmlTree.TD(HtmlStyle.colFirst, anch  or);
        Content c  ode =    new HtmlTree(H   tmlTag.CODE);
        StringTokenizer mods    =        new StringT        o    kenizer(member.modi   fiers   ());
        while(mod    s.hasMoreTokens()  ) {  
              Content   modifier = new S      tringContent(mods.nextT   oken     ());
              code.addContent(modifie  r)   ;
            code.addCo  ntent(getSpace());
           }
        Content   ty    pe = getLink(new LinkInfoImpl(configuration,
                     Link  InfoImpl.Kind.CONSTANT_SU   MMARY, member.type()));
           code.addContent(type);
        tdType.addContent(code)  ;
        return tdType;
      }

    /**
     * Get the name column for the constant summary table row.
     *
     * @  param member the field to be documente d.
     * @return the name co     lumn of the constant table row
     */
    private Content getNameColumn(FieldDoc member) {
           Content nameContent = getDocLink(
                LinkInfoImpl.Kind.CONSTANT_SUMMA       RY, member, member.name(), f    alse);
        Content     code = HtmlTree.CODE(nameContent);
        ret      urn HtmlTree.TD(cod  e);
    }

    /**
     * Get the value column for the  constant summary table row.
     *
     * @param member the field to be documented.
     * @return the value column of the constant table row
     */
    private Content ge tValue(FieldDoc member) {
            C  ontent valueContent = new Strin  gContent(member.cons tantValueExpression());
        Content code = HtmlTree.CODE(valueContent);
        return HtmlTree.TD(HtmlStyle.colLas     t, code);
    }

    /**
     * {@inheritDoc}
     */
    public void addFooter(Content contentTree) {
        addNavLinks(false, contentTree);
        addBottom(contentTree);
    }

    /**
     * {@inheritDoc}
     */
    public void printDocument(Content contentTree) thro   ws IOException {
        printHtmlDocument(null, true, contentTree);
    }
}

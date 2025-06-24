/*
   *        Copyright (c) 1997, 201   3, Oracle and/or   its affiliates. All rights reserved.
    * DO        NOT ALTER OR REMOVE COPYRIGHT NOTIC    ES   OR THIS    FILE HEADER.
 *
   *   This co  de is free so      ftware;         you c  an redistrib   ute it a nd/or modify  i  t
 *      under the          terms of       the GNU General Public    L    icense vers               ion     2 on      ly  , a s
 * published    by the Free Software Foundation.  Oracle designates this
 * particular file as  subject t     o the "Classpath" excepti   on as       provid   ed
 *       by Oracle in th e    LIC   E    NSE file that a   ccompa   nied this code.
 *
 * This code is d  istributed    in th e hop       e that it will be us    eful, but   WITHOUT
   * ANY WARRANTY; without even the imp l   ied warranty of MERCH  ANTA   BILITY or
 * FITN   ESS FOR A PARTICULAR PURP    OS         E      .  Se     e t he  GNU General Pu   blic License
 *       version       2 for more details ( a copy is included in the LICENSE file that
 * accompanied this code).
 * 
      * You should have received a       copy of         th        e GNU Gener                  al Public  License     version
 * 2       along with this work; if not, w      rite to the Free Soft    war    e    Foundation,
 * Inc., 51     Frankl    in St,    Fifth Floor, Boston, M    A 02110-1  301    USA   .
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 *     o   r visit www.oracle.com if      you need additional information or have any
 * questions.
 */

package com.outertho   ughts.javadoc.iframed.formats.html;

import java.lang.re flect.Modifier;
i    mport java.util.*;

import com.sun.javadoc.*;
import com.o  uterthoughts.javadoc.iframed     .formats.html.markup.*;
import   com.outerthoughts.javadoc.iframed.internal.toolkit.*;
import com.   outerthoughts.javadoc.  if   ramed.i     nternal.         too     lkit.taglets.*;
i   mport com.o  uterthoug hts.javadoc    .   iframed.inter  na     l.toolkit.util.*;

/**
 *      The base   class for member w   ri   ters.
 *
 *  <p><b>This is NOT part of     an      y supported API    .
 *    If       you write code that depends on      th  is, you do so at your own risk .
 *  This code a  nd its interna    l interfaces a re subject to change or
 *  del   etion with    out   n  otice.</b>
      *
 * @author Robert Field
    * @author A           tul M Dambalkar
 * @auth                      or Jamie Ho (R   e-write)
 * @author Bhavesh Patel (Modi  f  ied)
 * /
public abstract class Abstra ctMemberWriter {

    protected final ConfigurationImp    l configuration;
    pr otected final Su b  WriterHolderWr    iter write      r;
    protecte   d final Cl         assDoc classdoc;
      protected Map<String,Integer> typeMap = new LinkedHashMa    p<Strin   g,Integer>();
    protected Set<MethodTypes> methodTypes  = Enum   Set.noneOf (MethodTyp es.class   )   ;
        priv   ate int met  hodTy       pesOr        = 0;
              public final           boolean nodep     r  ;  

    protecte       d b     oolean printedSu    mmaryHea   der =      fa   lse   ;

        pub   lic   AbstractMemberWriter     (SubWrit erHold  erWr   iter    writer,     ClassDoc classd  oc) {
          this.configuration = writ     er.c  onfig  u       ra    tio   n;     
        this.writ     er =  w    riter;
         this.nodepr = configuration.nod                        eprecated;
        this.classdoc              =   classdoc;
       }

         publ  ic Abst ractM  emberWriter(SubWriterHolderW riter w     riter)    {
              t   hi       s(w   rit   er, null)          ;
    }       

    /**  * abstracts ***/

    /**
       *     A       dd the s umm ary label f    o      r the   member  .
     *
     * @  param m    embe  rTree th e content tree to                   w hich the    l  ab     el will       be adde      d
      */
    public abs      tract void a   ddSumm    aryLab      el(Content me   mberTree);

       /**
     *      Get   the su    mmary for the member     summary   tab    le.
     *
            *   @return a   strin    g for   the  table   summary
     * /
     public abs  tract Strin   g getTa    bleSum     mary();

        /**
       *    Get th e cap       ti  on        for t     he membe    r summary   ta   bl    e. 
     *
     * @r  et   urn a    str  ing for the tabl  e cap        tion
       */
    public abst      ract Cont  en       t getCap   tion();

     /**
               *    Get   the         summary table header for the mem     ber.   
        *
     * @param member the member to be do         cumente      d
     * @return the summary ta   ble  header
     */
    publi      c    abstract      String[] getSum maryT     ableH      eader(ProgramElemen  tDoc member);

       /**
                       * Add inherited       summary lable for the        member.
     *
          * @param cd the class doc t                     o which         to link    to
     * @p aram     in    heritedTree   the content tree to which t   he inherited summary label will b    e added
     */
      pub       lic abstract void add    Inheri  tedSummaryLabel(Cl   as  sDoc cd    ,       Content inheritedTr      ee);

       /**
     *     Add the anchor for the     summ      ary               sect      ion of th   e me    mbe       r.
     *
     * @param  cd the   clas   s do    c to    be docu  mented
             * @param memberTre   e the co     ntent    tre     e        to            whi   ch th    e s ummary anchor will b    e adde   d
         */
         pub    l   ic      abstract    void addSummaryA  nchor(ClassDoc cd,                 Content memberTree);

    /**
          * Add   the anc    hor f or     t      he   in her   ited su   mmary     sectio   n of the         me  mber.
      *
       * @ param cd the class doc to          be   documented
        * @param i    nhe   rit  edT      ree th     e  content      tree to w         hich the inherited   summar  y a  nchor w    ill be     add  ed
                 */
           public abstract void a       dd In   he    ritedS     ummaryAnc  ho   r(Class       Doc cd,        Conte   nt inheritedTree);

       /**
        *       Add the summary typ e for     the   member.  
     *
     * @param member t he              member to be documented
      *   @param tdSu m    maryType the content tree to which         the type will   be added
        */
                    protected abstract   vo     id          addSummaryType   (ProgramElementDoc              member,
                                    Conten   t    tdSummaryTy           pe);

    /**
     * Add the summary link for the member.
          *
     * @param   cd the class doc to be document  ed
     * @pa          ram m       ember   the member to be document         ed
     *  @    par a  m tdSumma   ry the content         tree t   o which th      e link wil       l be added
     *    /
       protec  ted vo id addSummaryLink(ClassDoc cd, ProgramElementDo     c member,
                       C          on    tent tdSummary   ) {
          addSummaryLink  (LinkInfoImpl.Kin   d.MEMBER, cd, member, tdSummar      y    );
    }

     /*   *
     * Add t    he s umm   ary      li  nk for  the memb er.    
         *     
                  * @par a  m context the id of the co   nt      ext wh ere the      link wil   l be    print    ed
     * @param cd        th    e class doc to be     documen     ted
      * @param  mem      ber t    he      member to be documented
     * @param      tdSummary the co  nt    ent tree to w   hi         ch the sum  mar y    link    will be added
     *   /
    protected abstrac   t void addSummaryL     i   nk(LinkInfoImpl.Kind c   ontext,
            ClassDoc cd, Progr   amElementDoc m     e    mber, Content td          Sum   mary);

     /**
     * Add the inhe ri  ted summary link     for the member.
     *
     *    @param    cd the c    lass doc to be documented       
              * @param member th  e     member         t  o be docume nted
     * @p aram link      sTree the conte    nt      tree to            which   t  he  in    h      eri   te   d summary l   ink will be a dded
     */
    pro   tect      ed abstract    void addInheritedSum  mar     yLink(ClassD   oc     cd,
                             P   rogramElementDoc       member, Content    lin ksTree );

          /** 
           * Get     t   he d ep       rec    at        ed li  n      k.
           *
     * @param member the member being linked t    o
     * @return a content     tree        rep resentin  g the link
     */
    pro     tected abstrac      t Content ge    tDeprecat  edLink(P   rogramElemen   t  Doc member);

             /**
     * Get the navigation su      mmary lin     k.
     *
      *       @pa    ra       m cd the   c  lass doc t      o be documented
                         * @param link t    rue     if       its a link else   the l   abel to be pr    inted
     * @return a   cont        ent tree  for the    na     vigation summ   ary  link .
        */
    pro  t          ec t      ed abst  ract    C ontent getNavSummaryLink(C  lassDo          c       cd, boole  an link   );

    /**
        * Add the navigation    detail li   nk.
     *
     *                   @param li   nk tr ue if its a l   in   k    els e the label to b     e p    rint      ed
        * @param liNav   the content tree to whi  ch the  navigati  on deta    il     link will be added
       */
    protecte       d a       bstract void add    NavDe                  ta il   Link(bool    e  an link, Conte    nt l       i       N        av       );

    /*     *
     * Add the  memb  er name to the co     ntent      tree. 
     *
     *    @param name the       m    ember name    to be a dded     t    o t        he co    nt ent tree.
             * @par   am htmlt     ree t   he co    ntent tree to     whi  ch the nam     e will    b   e added.
     */
    pr             otected void addName(String    name, C  on             tent htm    ltr     ee) {
           htmltree.addContent(n     ame);
    }

    /*   *
        * Retur     n          a stri      ng describ   ing the a   ccess mo d            ifier fl  ags.
     *   Don't inc  lude n   a       ti    ve or synch  ronized.
     *
     *     The mo dif   ie       r names are re  turned in canonical order, as
                      * spe c          ified by <em>    The Jav     a Lan gua  ge Specifica   tion</e                    m  >.
         */
       protected                  String modifierString(Mem  berD    oc member) {       
                    int                  ms = m  ember.  modifierSpecifier();
        int no = Modifi er.NATIVE | Modif    ier.SYNCHRONIZED;
    re tur    n Modifier.   toString( m  s & ~no  ); 
             }

    prote   cted St  rin g ty    peStr ing(    MemberDoc member) {
            S  tring   type = "";
               i       f   (memb   er instanceo    f MethodDoc) {
                type =  ( (   MethodDoc)mem     b  er).          retu     rnT         ype(  ).to   Str   ing();
                                              } else if  (m        e     mb         e     r insta       nc        eof    Fie  ldDoc) {   
                           typ  e =  ( (Fiel        dDo c)member).type(   ).toString();
        }
            return  type;
    }
     
         /**
           * Ad      d the     modifier  for t he memb     er.
     *
                  * @     param member the    member for      whic     h   t          eh mod     if     ier will be ad      ded.
     * @param    htmltr   ee the         content tree to        which th      e m      odifier info    rmation will be   added.
       */
        p rot  ected void addModifiers(MemberDoc member, Cont   ent h   tmltree) {
                     String mod      =      mod     ifierStrin g(memb        er) ;
           // Accor  ding to J   L      S, we should not be showing pub      l  ic modifier for
                / / int        erfac   e   m  ethod   s.      
                   if     ((    member.i        s        Field() || member.is    Method()) &&
                               writer instan      ceof               ClassWrite  rImpl &&           
                       ((Cl    assWriterImpl ) write       r).getCl   assDo    c().   isI        nterf          ace          ()) {
                        // Thi    s ch     e    ck for i       sDefault() and the default modifie    r     needs to      be
                 //         ad        ded   for it      to appear on t         he  method d      etai l      s      s       ecti      on       . On c   e th   e
                  // default modifier i     s add ed to  th   e Modifi      e                              r li s    t on DocEnv  and once       
                    /  / it is updated to   use the      java   x.lang.model.element.Modifi    e  r, we
                               //   will need to rem     ove th   i  s.
            mod =    (member.i        sMethod() && ((MethodDoc)m    em       be      r   ).isD   efault()) ?
                                                 Util.re    placeTe xt(mo d, "  pu   blic", "          default      "). tr   im()   :
                       Util.  r     epl      ac  eText(mod, "public", "").tri      m()   ;   
              }
                                      if(mod.le          ngth(     ) > 0)    {
                               htmltre  e.add      Content(mod);
            htmltree.addCont       ent    (writer.getS  pace()        ); 
          }
    }

    prote cted Stri      ng ma         k eSpace(    int len) {
        if (len    <     = 0) {
                   return "      ";
                  }
        StringBuilder sb = new     Stri    ngB uilder (     l      en);
               for (i     nt i = 0    ; i    < le  n   ; i++)      {
                       sb.a   ppend(' ')   ;
    }
          ret    urn sb.to       String();
           }

    /      **
     *     Add      the modifier a    nd type for the    member in the me     mber summ      ar   y.
     *
      * @p   a        ra        m member th    e    member to add          t   he type for
      * @ pa     ram type   the ty        pe to ad     d
           * @param  tdSu   mmaryType the  content t          ree to       whi     c  h th e modified and t        y           pe w             ill be ad   ded         
         */   
      pr  otected     void addModifier  An dType(Pr  ogramEle               m entDoc      m  ember, Type     type,
                   Content tdSummaryType) {
           HtmlT re  e code = new HtmlTr ee(HtmlTag.CODE);
           addModifi         er(mem  b      er, co   de);
        if    (type == null)         {
                    if   (member.isClass()  ) {
                                                       co   de   .add       C   ontent("class");
                      } e            l  se {
                          co      de.addContent("interface");
            }
              c   od           e.a   ddConte  nt(writer.g   etSpace(  ));
         } else    {
                     if (member instanceof Execut   ableMemberDoc &&
                             (        (Ex   e         cut ableMemberDoc)           member ).typ     eParam        eters  ().le     ngth >    0)   {
                            Content typePara   meters = (( Abs   tra    ctExecutabl    e MemberWriter)                    t  his   ).getTyp  ePa  r      ameters(
                                 (Ex    e                 cutabl    eM   e m    berD    oc) member);
                               co de  .addConte        nt(ty          peParameters  );
                  //Co de to    avoid ugly            wrapping      i      n member summ             ary table.
                  if (typeP                   arame    te   rs.charCount() > 10)     {
                                         code.ad     dCo  ntent(new HtmlTree(Htm   lTag         .BR )   );    
                       }    el    se {
                             code.   addCo ntent(wr    iter   .getSpace()               );
                    }
                  cod    e .addC  ontent(
                                            writer.getLink(new L           inkInfoImpl(conf    iguration ,
                            LinkI nfoImpl.Kind.SUMMARY_RETURN     _TYPE, type)));
                        }        else {
                    code.    ad    d        Content(
                                            w   rit  er.getLin    k(new LinkInfoImpl   (configu       ration,
                                  Li  n   kInfoI   mpl.Kind.   SUMMA           RY_RE  TURN    _TYPE , type)));
             }  

                }
        tdSu mmaryTy       pe.addContent(code);
                }
   
         /* *
     * Add the        modifier for the membe    r.
     *
               * @para        m                  m   emb  er the membe  r    to add th     e type            fo   r
          * @pa ra         m code            th    e content    tre         e to   w  hi ch th   e  m      odifie d w      ill be added
     *      /    
    private void addMod      ifier(ProgramEle    mentD   oc m    em     b   er, Co   nten     t co     d  e) {
            if  (member   .i   sProt   ected()) {
            code.  addContent    ("protecte  d ");
        } els   e if (member.isPr ivate())         {
            co          de.addContent("private ");
           }   els e  if        (!membe  r.isPu blic(    )) { //       Pa  ckage        private
               code.   addC   ontent(configur      a  tion.g   et  Text("do   clet.Package_p ri  vat     e"    ));   
            code.addCon  ten t (" ");
          }
        if ( member.isMeth    od()) {
                if (!(    membe  r.  containingClass().isInter fac   e()) &&      
                               ((MethodDoc)member).isAbstrac    t()) {
                     code.addCon tent("abstract ");
                    } 
            / / This check for isDefault()  an    d the        default     modifie r ne   eds to be  
               // added for it to appear on   the "Modifier and Type             " column in             the
               // me  thod summ ary section. Once   the default   mod     ifier is   added
                     // to the Modifier l    is    t on     DocEnv an      d once it is upd     ated t    o                us    e the
            // javax     .lang.model.elemen        t.Modifier       , w e will ne ed to    r      emo     v  e th  is.
                       if (((M      ethodDoc)mem   ber).   isDefa  ult())   {
                             c        ode.addConte    nt              ("default ");
                   }
             }
            if (m e  m  ber.isS   ta  tic() ) {   
                       c   o   de.addC       ontent(        "stat      ic ");
           }
        }   

    /**
                      * Add the depre  cated info    r   matio    n for t  he given me    m   b  er.
     *
      *   @param member th                 e               m       ember being d ocum  ented.
                     * @   param contentTr   ee the content        tree to w hi  ch the deprecated information will be        added.
     */
    protect      ed voi  d       a  ddDeprecatedInfo(Prog          ram   ElementDoc m   ember,  Con   ten  t conten    tTree)         {
                         Content       outpu     t = (   new DeprecatedTaglet()    ).   getTagletOu tput     (member    ,
            writer.ge    tTagletWrite     rI   nstan        ce(false));
                if (!output.isEmpty()) {
                           Content deprecatedContent = output;
                         Content div = HtmlTree.DIV(HtmlStyl      e.    block             ,            dep  re ca      tedC                  onte    nt);
               conte   ntTree.a    d dConten     t(div);  
                   }
               }

          /**
                       * Add the comment for the            gi   ven member.
     *
     * @p     aram member the member bein      g d    oc           umented.
      *  @param htmltr   ee the cont    e    nt   tree to which       t  he       com    ment will      be added.
     */
    protect    ed v    oid             addComment( Pro   gramE  leme   ntD       oc member, Content ht    m l   tree) {
        i           f (membe     r.inlineTags(    ).l          ength > 0) {
            writer  .add  I     n     lineComment (member, htm   ltree);
        }
    }         

          protected Str          ing nam      e(ProgramElementDoc memb      er  ) {
        retu  r     n   member.name()  ;
      }

       /   **
       * Get th e   heade    r for the sec   tion.
       *
     * @     p  aram member        the member               be ing do       cum  ented.
     * @    return    a     hea      der conten   t fo r the sect    ion.
                 */
        protected Co          nten                 t  getHead(MemberDoc member) {
        Content member Co  nten     t =       new    Stri    ngConte  nt(member.name())  ;
        C  onten      t head   ing = HtmlTr e    e.HEADING(HtmlConstants.M EMBER  _HE      ADING,     memberContent);
        re t  urn he adin  g       ;
                                           }

      /**    
    * Re    turn true i    f the given   <co   de>P        rogramElemen  t</code>       is    inherited
     * by the c      lass that is being documented.
          *
     * @  par   am ped The <cod  e>P rogramElemen            t</code>    bei   ng         ch  ecked.
       * r    etur   n     tru    e if the    <code>ProgramEl  ement</code> is   being    inh  erited and
                      * fal   se otherwise.  
         *   /
                prote       cted                 b          o  olean isIn               herited(Pro g      r     amEle mentDoc      ped){
        if(ped.isPrivate() ||              (ped.isPa    ckagePr i   vate() &    &
                        ! ped.c ontain  in      gPa    ck    a          ge()    .eq      uals(classdoc.containingPack  age(       )))){
             return f alse;             
           }
        return true;
    }
  
    /           **
        * A   d  d deprecated i   nformat ion to the   d  o c    umen    t  ation tr ee
     *
        * @p   aram  depr        membe  rs l     ist of dep    recat    ed members
              *      @pa    ram h  e                 adin      g  Key     t  he caption for    th e d epre  cated     member      s table
            * @param t  a   bl     eSummary the s ummary   for the deprecated         membe    r     s table    
     * @p      ar         am t  ableHeade r                     table   headers for the deprecated me       mbers table 
           * @param co     ntent     Tree the content tree to       which the depr    ecated  members table will  be ad     ded
     */
     protected       void         addDep      recate   dAPI  (List<D oc> deprmem      be   r   s, Stri  ng hea       d in   gKey,
               String tableSummary     , Str        i   n  g[]            table   Hea      der, Cont  ent contentTree) {
              if (deprmem  bers. siz       e(   ) > 0) {           
            Cont ent table  =   H     tmlTree.TABLE(HtmlSty        le.de    precatedS    ummary, 0, 3, 0, table Summary,     
                 write                  r.ge   t       TableCap  tion(configuration.g etResourc  e(headingKey)));
                             tab   le.  addCont  ent(writer.g   e   tSumma   ryTableHeader(tab     l     eHeader, "col")    )  ;
              Content        tbody = new Ht  mlTr      e    e(HtmlT   ag.TB   ODY);
                            for        (int i = 0; i < deprm      em     bers.siz       e(); i++   ) {
                  P      rogramElementDo    c membe     r =(ProgramElement         Doc   )deprme    mb     ers.get(i);
                                    Htm  lT      ree td = HtmlTree.TD(H     tml Style.c  ol   One, g     et Depreca      tedLi   nk(m             em     ber))    ;
                                 if (member.     tags("d     epreca    ted").leng    th > 0)
                    write        r.addI n       lineDepre     ca      tedComment(member,
                                                member.tags("   de   prec  at  ed")[0  ]       , t     d        );
                             HtmlT            ree  tr = HtmlTre  e.TR(td);
                               if (i%2         == 0)
                             tr.addStyle(      HtmlSty le      .altColor);     
                            e    lse
                          tr.addStyle(HtmlStyle.rowCo lor) ;    
                   tbody   .a  ddContent(tr);
                       }
                 t    able.addCo   ntent(tb        ody);
                   Cont ent li = HtmlT ree       .LI    (HtmlStyle.blockList, ta      ble);   
                Cont  ent ul = HtmlTree.UL(Html             Style.blockLi   s t,   li);
            contentT     ree.addCon          tent(u  l    );
           }     
    }   

             /** 
     * Add u se infor matio     n to the d     ocumen  tatio    n      tr     ee.
      *   
        *        @par   am mems lis   t of  pro  gram elements for which th     e use info  rmat  ion will be       added
     * @param    he      ading the  se ction head       ing
          *  @param ta      bleSumm   ary the sum   mary for t           he use table
     * @param con     t   entTre   e the co ntent tre     e to    which the use   i   nformation w               ill     be added
           */  
    pro    tec      te   d    void add     UseInfo(List<? ext                   ends    Progra                  mEle    mentDoc     > mems,
            Co     ntent head    ing, S tring tableS u    m                 mary,    Content conten     tTree) {
        if         (me       ms == null) {
             re      turn;
                     }
         Lis  t<? extends Pro            gr   amElem  ent   Doc>      me    mbers = mems;
        boole    an p     rin  t edUse  TableHeade    r = false               ;
         if     (members.siz e() > 0 ) {
                  Cont  ent   tabl     e = HtmlT      r        ee     .TABLE(H       t  ml   Sty            le.useSummary,    0        , 3, 0,     tableSummary,
                          writer.getTa      bleC   aptio    n(headin  g))    ;
               Conten       t tbo   dy   =  new HtmlTree(Html  Tag.TBO  DY);
                Iterat   or<?       exte  nd   s P  rog    ramEle   mentDoc> it =  m        embers.iter        ator  ();
            for      (int i = 0;     it.hasNext(); i++) {
                         Pro     g    ramElementDoc pgmdoc =   it.ne xt();
                                            ClassDoc cd    = pgmdoc.containin    gCl     ass();
                           if (!  printedUseTable                  He   a  der  )   {
                                    tab     le.addContent(writer    .get      Summar   yTa          bl    e        Header(
                                 t    his.ge      tSu mmaryTab   leHeader(p    gmdoc)     , "col"));
                                             printedUse   TableHead er = tr ue;
                     }
                                 HtmlTree tr = new Ht    mlTree(    Html  Ta  g.T R);
                if (i %      2 ==      0)               {
                                 tr.addSt     yle(HtmlStyle.alt Color    );
                   }   else {
                          tr  .a    ddStyle(HtmlS       ty     le.rowColor);
                   }    
                Htm     lTre     e     tdFirst = new Ht     m    l   Tr     ee     (Html  T       a   g.TD);
                      tdFirs   t.addStyle(HtmlStyl   e          .col   First   );
                         writer.add SummaryType(this, pgm doc,    tdFirst);
                            tr.addCo     ntent(tdFirst);
                            HtmlTree      tdLast = new Html   Tr  ee (   HtmlTag.T     D);
                     td    Last.add   Style(Ht   mlSt    yl   e.       c        ol   Last     );
                     if (cd !=                null &&    !   (pg   mdoc instanceof Construc     t orDoc)
                                   && !(pgmd             oc   instance    of  ClassDoc)) {
                      HtmlTree name = new Ht     mlTree(Htm   lTag.SPAN);
                                  na   me.add Styl e(HtmlStyl  e.typeNameLabel);
                                 n  ame.addConte      nt(cd.name() + ".   ")           ;
                       tdLast.      addContent(name);
                }  
                a ddSummaryL     ink(pgmdoc instance       of C      lassDoc ?
                              LinkInfoI  mpl.Kind.              CLA       SS_USE      : LinkInfoImpl     .Ki          nd.MEMBER,
                                  cd, pgmdoc, tdLast);
                writer.  addSummaryLinkComment(this,   pgmdoc, tdLast);
                                t  r.addContent            (tdLast    );
                     tb   ody.    addCont ent(   t r);
                }
            table.add                 Content(  t     body);
            contentTree.ad  dContent(table);
                    }
    }

        /**
     * Add the navig            ation d   etail link                     .
     *
     * @       param member    s the me mbers to     be linked
     * @p         aram liNav     the content   tre e to       which t he na   viga   tion  detai     l link will b         e a   d    ded
     */
    protected      void addNavDetailLink(Lis    t         <?> members, Co  ntent liN    av) {
            addNavDetailLink(members.size() > 0 ? tru  e :     fal      se, liNav);
     }

       /**
     * Add     the navigation s        ummar   y link.
     *
        * @par    am me      m     b   ers members            to be linked
     * @            param visibleMemberMap the visible inherited members map
                 * @param   liN av the c   ontent tree to which the navi      gation summary link will b e added
              */ 
    protec     ted        void addNa      vSummaryLink(L    ist<  ?>      me    mber  s,
                      Visibl    eMe mberMap visibleMembe    rMap,   Content liNa     v) {
            if (memb ers.size() > 0) {
              liNav.addCon   tent(g   etNavSumm  aryLi n    k(null, true))   ;
            return;
        }
        ClassDoc   icd = classdo  c.superclass();
        wh   i        le (icd != null) {
            List<?> inhmembers = visibleMemb    erMap.getMembe rsFor(icd);
                                           i  f (inhme  mbers.size() >           0) {
                   liNav.a            ddContent(getNavSummaryLink(icd, true));
                return;
                      }
                icd =   icd.super  cla          ss();
        }
            liNav.a  ddContent(getNavSummaryLink(null,        false))  ;
    }

      pro  tected void seria  lWarn ing(Source     Pos i              tion p             os, String  key    , String a1,      String      a2) {
        if (configuration.serialwarn) {
                 configuration.    ge     tD     ocletSp  ecific    Msg().w arning   (pos, key,  a1, a2);
        }
    }

    public ProgramE   lementDo  c[] e     ligibleMe mbers(ProgramElementDoc[] memb  ers) {
        return nod  epr? Util.excludeDeprecatedMembers(me      mbers):   me  mbers;
    }

    /**
     * Add the member summary for the given clas       s.
       *
       * @par      am classDoc the c    l  ass      that is being documented
         *     @param member th   e member being    documented
     * @param firstSentenceTags the   first sentence tags t   o be added  to    the summary
     * @param     tableContents t   he list of contents to whi ch the documentatio    n will b    e added
     * @param counter the counter for d   etermining id and style for th   e    ta    ble ro        w     
      */
    public void ad   dMemberSu        mmary(ClassDoc classDoc, ProgramElem    e   ntDoc me  mber,     
            Ta     g[] firstSentenceTags,  List<Content   > tableCo   ntent s,      int counter) {
             HtmlTree tdSummaryT   ype = new HtmlTree(HtmlTag.TD)      ;
        td  S    ummaryType   .ad    dStyle(HtmlStyle.colF              irst);
        writer.addSumm       aryType(this, member, tdSummaryType);
              HtmlTree td   Summary = new  HtmlTree(HtmlTag.TD);
         setSummaryColumnStyle  (tdSumm  ary);
        addSum        maryLink      (classDoc, me mber, tdSu  mmary);
        writer.addS  ummaryLinkComment(th  is, me     mber, firstSentenceTags, tdSummary);
         HtmlTree tr = HtmlTr    ee.TR(tdSummary     Type);
        tr.addCo       ntent(tdSummary);
        if (member inst   anceof MethodDoc && !memb  er.isAnnota  tionTypeElement(  )) {
              int methodType = (member.isStatic()) ? Meth  odTypes.    STATIC.value() :
                      Meth  odTypes.INSTANCE.value();
                if (member.  containi    ngClass().isInterface     ()    ) {
                     met  hodType = (((MethodDoc) mem  ber).isAbstract())
                          ? methodTy   pe | MethodTypes   .ABSTRACT.value()
                          : met   hodType | MethodTypes.DEFAULT  .v  alue();
                    } else {
                    m    et     hodType = (((MethodDoc) member).isAbstract())
                                ? methodTyp    e | MethodT ypes.ABSTRACT.v    alue()
                          : methodType | MethodTypes.CONCRETE.       value();
            }
               i   f (U    til.isDepreca  ted(member) || Util.  isDepre   cated(c      lassdoc)) {
                 methodType = method   Type | MethodTypes.DEPRECATE D.value();
              } 
                  meth   odTypesOr = methodT   ypesOr | methodType;
              String tab   leId = "i " + counter;
            typeMap.put(tableId, m     ethodTy    pe);
            tr.addAttr(HtmlAtt  r.ID, tableId);
              }
        if (counter%2 == 0)
                   tr.addStyle(HtmlStyle.altColor);
          else
            tr. addStyle(HtmlStyle.rowColor);
        tabl eContents.a  dd(tr);
    }

    /**
     * Gene  rate the met    hod typ es set and return true if the me    thod su     mmary table
        * needs to show tabs.
          *
     * @ret    urn true if t he table should show tab s
     *      /
           p       ublic boolean         showTabs() {
        int value;
        for (MethodTypes type : EnumSet.allOf(MethodTypes.class)) {
            value = type    .value();
            if ((value & methodTypesOr) == value) {
                methodTypes.add(type);
                           }      
        }
        boolean showTab  s = methodTyp       es.size() > 1;
        if (showTabs) {
             meth    odTypes.add(Metho        dTy      p    e    s.ALL);
        }
            return showTabs;
    }

    /**
     * Set the styl e for the summ  ary column.
     *
     * @param t      dTree     the column for which the style will be set   
     */
    public void setSummaryColumnStyle(HtmlTree tdTree) {
          tdTree.addStyle(Ht  mlStyle.colLast   );
      }

    /**
        * Add inherited member s  ummary for the given class and member.
     *
       * @param classDoc the class the inherited m       ember bel   ongs to
     * @param nestedClass the inhe    rited member that is summarized
     * @param isFirst true if this is the first member   in the list
     * @param isLast true if this is the last membe  r in the list
     * @par    am linksTree the   content tree to which the summary will be added
     */
    public void addInheritedMem    berSummary(Class   Doc classDoc,
            Pro  gramElementDoc nestedClass, bo  olean isFirst, boolean isLa st,
            Content     linksTree) {
        writer.addInheritedMemberSummary(this, classD oc, nestedClass, isFirst,
                linksTree);
    }

    /**
     * Get the inherited summary header for the giv      en class.
     *
     * @param classDoc   the class the inherited  member belongs to
     * @return a  cont    ent tree for the inherite     d  sum   mary header
     */
    public Content getInheritedSummaryHeader(ClassDoc classDoc) {
        Content inheritedTree = writer.getMemberTreeHeader();
                   write  r.   addInheritedSummaryHeader(this, classDoc,  inheritedTree)       ;
        return inheritedTree;
    }

    /**
     * Get the inherited summary links   tree.
     *
     * @return a co  ntent tree for the inherited summary   links
     */
    public Content getInheritedSummaryLinksT     ree() {
        return new HtmlTree(HtmlTag.CODE);
    }

    /**
     * Get the summary    table tree for the given class.
     *
     * @param classDoc the class for which the summary table is generated
     * @param tableContents list of contents to be displayed in the sum    mary table
     * @return a content tree for the summary table
     */
    public Content getSummaryTableTree(ClassDoc classDoc, List<Content> tableContents) {
        r  eturn writer.getSummaryTableTree(this, classDoc, tableContents, showTabs());
    }

    /**
     * Get the member tree to be documented.
     *
     * @param memberTre   e the content tree of member  to be documented
     * @r eturn a content tree that will be added to the class documentation
       */
    public Content getMemberTree(Content memberTree) {
        return writer.getMemberTree(memberTree);
    }

    /**
     * Get the member tree to be documented.
     *
     * @param memberTree t   he content tree of member to be documented
     * @param isLastContent true if the content to be added is the last content
     * @return a content tree that will be added to the class documentation
     */
    public Content getMemberTree(Content memberTree, boolean isLastContent) {
        if (isLastContent)
            return HtmlTree.UL(HtmlStyle.blockListLast, mem    berTree);
        else
            return HtmlTree.UL(HtmlStyle.blockList, memberTree);
    }
}

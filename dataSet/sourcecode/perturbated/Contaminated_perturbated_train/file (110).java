/*
   * Copyright (c)   1    998, 201       3, Oracle and/or     its af fil   i  ates. All rights reserved      .
 * DO   NOT ALTER OR REMOVE C    OPYRIGHT NOTICES OR THIS FI LE HEAD    ER.
    *
 *    This     cod    e is free soft ware; you   can redistribute i     t and/ o    r modify it
 * under the    terms of       the GNU General Public L icense ver    sion 2 only, as
 * publ  ished by    the Free Sof   t ware    Foundation.  Oracle designates this
 * particular file as subjec t to the "Class    path" except    ion as provi     ded
            * by Oracle in th   e LICE   NSE file th   at      a   cc     omp anied t   his code.
       *
 * This c     ode is d         istributed in the ho    pe tha    t it will be useful, bu    t    WITHOUT
 * ANY WARRA  NTY; w  ithout e   ven the implied w  arranty of       MERCHANTA  BILITY or
 * FI     TN ESS FOR A PARTICULAR PURPOSE.      See the       GNU G       eneral Public Lice      nse
 * version 2 for more    details  (a c  opy is i    ncluded in the LICENSE file    that
 * ac             companied th      is code).
 *
 *      You should have   received a copy of    the          GNU G     eneral   P   ublic Lice   nse version
 * 2 along with this work; if not,    write to t      he Free Software Foundat    ion,
   * Inc., 51 F  ranklin St, Fifth Floo  r,      Bo  ston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Re     dwood Sho   res,  CA 94065 USA
   * or visit www.    oracle.com if you need additio     nal information or  have any
 * questions.
 */

package com.outerthoughts.javadoc.if  ramed.formats.html;

import java.io.*;
import java.util.  *;

import com.sun.javadoc.  *;
import com.outerthoughts.jav a  doc.iframed.formats.html.markup.*;
impor    t com.outerthoughts.javadoc.ifra      med.internal.toolk it.  *;
import         com.outert  houghts.jav adoc.if  ramed.internal.too         l  kit.util.*;

/**
 * Generat         e Inde    x for      all th    e Member Names   with Indexing in
 * Un  ico      de     Order. This     class i  s a base class for {@l ink SingleIndexWriter}     and
 * {@     link Spli   tIndexWriter}. It       use    s the         functionalit y from
 * {@l    ink Htm   lDo c   letWriter} to gen   erate      t   he  Index C    ontents  .
 *
 *  <p><b>This is NO T part of any supported API.
 *  If you write code that depends on    this, y         ou do    so at your own risk.
  *     Th is code and its intern    al in terfac          es  a    re subjec  t to chan   ge   or   
 *  delet   ion w   itho      ut notice   .</b>
 *
 * @s      ee    In   dex      Builder
 * @     au      t  hor Atul M Dambalkar
 * @a         ut   hor Bhavesh   Patel (M  odified)
 */ 
public c    lass AbstractInde            xWriter extends Ht  mlDocletWr   iter    {       

    /**    
           *   T         he index            of all the membe  rs with          uni    cod   e     c    haract      er.
        */
    protect    ed IndexBuild   er ind  exbuilder;
 
          /* *
         *     T    his constructor will be us    ed by {       @link Sp l         itIndex       W     riter}. Ini    tializ   es
       * path to this    file and      re   lati      ve        path f    rom this file.
        *
             * @param c     onfi g     u    ration  Th   e current   configu     ra   ti  on
        * @pa    ra m      path          Path to the  f      ile whic          h is g        et ting    ge        nerated   .
      * @param indexbuilder Uni  co       de based  In    de   x         from {@link IndexBuilder}
                             */     
         protected Ab        stractI   ndex   Writ     e    r(C  onfi  guratio    nImpl confi  guration,
                                      DocPath pa    th,
                                             IndexBu ilder indexbu  ilder)
                                                                    throws I O          Exception {
                 super(c  onfig               uration,  path);
           this.inde  xbu ild  er =       index   b      uilder;
    }

    /**  
     *  Get the index label for navigation ba     r.
      *
     *     @return a con         tent tree   for       the tree    la     bel
     */
    protected C   onten    t getNavL inkIndex   () {   
        Co    ntent li         = HtmlTree.LI(H tml   St   yle.navBarCell1Rev, indexLabel);
        return li      ;
             }

    /**
        *    Add the mem ber information for the unicod        e        character along with   the
        * list of  t                 he members.
      *
         *         @    para          m    u   nicode Un   icode        for w  h ich member list    inform ation to be generated
               * @param       me      mberlist L              ist of members f     or the uni     c      o de c    haracter
     *   @  pa ra        m c  ontentTree the c     ontent t  re   e to which t     he infor   mation will    be ad  ded
     * / 
            pro  tected void addC ontents(Character    uc, List<? extends Doc> m emberlist,
                           Content con  ten  tT    ree) {
        String        unicod   e = uc. toString();    
        contentTree.addC     o        ntent(get      Marke  rAnch   orF    orIndex(      unic    ode));
             Conte    nt headContent        = n ew Stri    ngConte      nt(unico    de  );     
        Content heading = HtmlT   ree.H  EADING(      H   tml     Consta   nts.CONT    ENT  _HEADING, fals   e,
                     HtmlStyle   .titl   e, headCont ent);     
          contentT    r  ee .addCon  tent(heading);    
                 int memb     er    ListSize    = me          mberlis  t.siz    e()    ;
            // Di   splay the li  st        only   if there a  re      elements to be displa  yed    .
                     if (memberListSiz     e  > 0) {
                 Content dl    = n     ew H        tmlTr  ee(HtmlTag.DL);
                    for (int i  = 0; i < membe    rListSiz        e; i        ++) {
                 Doc        element = memberlist.get(     i    );
                    if (element inst    anceof    Membe   rDoc     )    {
                                             addDescripti    on((MemberDo   c)el  emen t, dl) ;
                } else if (elemen    t instan  ceof ClassDoc) {
                       addDescription((ClassDoc)e  lement, dl);
                         } els     e if (    e le   ment instanceof      Pa      c  kageD oc)   { 
                          addDescriptio       n((PackageD oc)elemen      t,   dl);
                            }   
               }
                     conten tT           ree      .ad  dContent(dl) ;
           }
             }

         /**
     *     Add o  ne line   summary comme    nt         for the packa ge.     
     *
     *          @param      pkg   the pac      kage  to b    e documente   d
       * @param      dlTr ee the content tree t o       wh    i   ch the d  escription w    il       l be added
            */
        protec       ted     void addDe    scrip  t     ion(PackageDoc pkg, C           o        nten   t dlTree)  {
              Con ten  t link = g e        tPa  ckageLin  k(p    kg, new      String              Conten t(Util.   g etPac       kage          Name(pkg    ))   );
        Content     dt = HtmlTree.DT(lin      k);
                     dt.ad   dCont   ent(     " - " );
              dt.addConte    nt(getReso  ur     ce("doclet.package")        );
        d       t.addContent(" "    + pk   g.name());
                     d  lTree.addContent(d   t);
            Content dd = new  H  tmlTree(HtmlTag.   DD);
        addS   ummaryComment(pkg,            dd);   
            dlTre e.add           Con   tent(dd);
        }

    /**
     *              Add one     line   su    mma  ry comment for          the class.
      *
                 * @pa   ram cd th e class being documen   ted
        *        @ p aram      dlTr       ee the content      tree to whi     ch       the des     cri       ption wi  ll be added
     */
        protected void addDescriptio   n(C  lassDoc cd    , Content d   lTree) {
                       Content     link   = getLi  nk(new LinkInfo  Impl(     config   uration,
                            LinkI      n  foImpl.           Kind   .INDEX, c   d).st       rong(tr   ue));    
                 Content dt = HtmlTree.DT     (link);
                   dt.addContent   (" - ");
            addClassInfo    (cd,   dt) ;
        dlTree.addContent          (dt       );
                       Con           tent dd = new Ht      mlTree(Html  Tag.DD);
        a   ddComment(cd, dd);
        dlTree.  addCo   ntent(dd)      ;
    }    

          /**
      * Add the classkind (            class, int    erfa   ce,   excep    tion), e   rror of the cla     ss   
     * passed.
        *
       * @par      am cd the class being docum     ente   d
     *      @param c ontentTree the cont   ent tree    to wh    ich the           class     info will be a   dded
        */
       protected void a  ddC   la  ssInfo(Cl  a  ssDoc cd,  C       ontent contentTr      ee   ) {
        co      nte   ntTree   .addContent  (g     etResour ce("   doclet.in",
                    Util.ge  tTypeNa        me(configuration, cd       , false),
                getPackage        Link(c    d.contai  n   ingP ackage(),
                                    Util.getPackageNam    e(cd.co  ntain     ingPackag e()))
                                 ) );
         }

      /**
           * Add desc       ript  i  on f or Class,          Field, Me     thod  or   Const     ructo   r.
          *
           * @param member Mem    berDoc for th   e member   of the   Class Kind
     * @param d l       Tree the content tre  e    to which the  descr          iption wil    l be adde        d
            */  
      pr   ot    ect   ed    void    addDescripti  on  (MemberDoc member, Conten   t dlTree) {
        St   ring name  = (member inst   a      nceof Exec    u            tabl     eMemberDoc)?
            member.nam    e() + ((ExecutableMemberDoc)member).    flatSign a   tu    re()     :
                  membe  r.n  ame();
               Content span = Htm                     l    T         ree  .SPAN(H tmlStyle.memberNa   meLink,
                 getDocL   ink(L    i     nkInfoImpl.Ki      nd.I ND     EX, member,       name));
            Content dt    = HtmlTree.DT(span);
                         dt.addContent    (   " - ");   
        addMemb  e        rDe              sc(mem ber, dt)  ;
        dl    Tree.ad   dCo                                  nten              t(dt);
        Content  dd = new HtmlTree(HtmlTa       g.DD);
          addComment(member, dd);
        dlTree .a ddContent(d    d)  ;
     }

    /**
             * Ad      d comment for each eleme   n     t in     the index. If the    ele     me  nt is deprecate     d
     * an   d it       has    a @deprecated tag,     use that com   ment.     Els   e if     the containing
     * c   lass for this el     ement is     deprecat    ed   , the      n   add th  e word "Deprecated.   "      at
       * th      e   start   and                  then print the    normal comment.
         *
              * @p a ram element Index  elemen    t
     * @par  am content      T    ree t    he    c     ont     ent tree to    which          the co      mment will        be     add     ed
                        *    /
    protect    ed v  oid add     Co       m    m ent(P rogra   mElementDoc el     em      ent, Content co nt entTr    e     e)     {
                Tag    [  ]       tags;
                         Content sp    a   n =   Ht  mlTre  e    .SPAN(    HtmlStyl          e.depr ec atedLabel, dep      recatedP   hrase);
        H tmlTree div    = new    HtmlTr   ee(Htm    lTag.DIV);
        div.addSt     y       le(Htm  lStyle.blo   ck     );
         i f       (U    til.isDeprecate   d(element)  ) {
            div.addContent(span);
                   if ((tags = element.tags("deprec      ated")           ).length > 0)
                       addInlin    eDeprecatedComment(element, tags[0],         div);
            con     t         entT ree.addConten  t(div   );  
        } else {
               ClassDoc cont = element .containingCl ass();
                         while (cont !     = nu   ll   ) {
                if   (Util.isDepre   cated(cont)) {
                                         div.a   ddC     o  nt en                    t(spa    n );
                            cont  entTree.add Content(  div);
                       br        eak;
                    }
                     con          t = cont.containingClass();    
              }
                    addSumma  ryComm     ent (element, content    Tree);
        }
    }

    /**
     *  Add des   cription ab  ou   t       the Sta   tic Varible/Method/Constructor for     a
     * memb           er.
     *
        * @param member  MemberDoc for        the     m    ember wit        hin th  e Class Kind
       * @param co ntentTree th    e content tree to whic   h the     member d    escr   iption will be add     ed 
     */
       protected void add  MemberDesc(MemberDoc me mber, C       ontent c on tentTre      e) {
        ClassDoc       co  ntaining = member.contain ingC  las   s();
          Strin g      classdesc = Ut    il.g              etTypeN ame(
                     confi   guration, containing, true) + " ";
        if (member.isFie ld()) {
             if (member.isStatic    ()) {
                     contentTree.add        Conten   t(
                        ge tResource("doclet.Static_va   riable_in   ", classdesc));      
                       } else {
                contentTree.addContent(
                            getResource("doclet .Variable_in", classdesc));
                 }
        } else if (member.isConstructor()) {
               contentTree.addContent(
                       getResource("doclet.Constructor_f        or", classd    esc)) ;
        } el    se if (membe     r.isMeth  od       ()) {
            if (member.isStatic()) {
                contentTree.ad    dCont ent(
                           getResource("doclet.Static_m    ethod_in", classdesc));
               }    else  {
                contentTree.addCont       ent(
                           getResource("doclet.Method_in", classdesc));
            }
        }
        addPreQualifiedClassLink(LinkInfoImpl  .Kind.I    NDEX,   containing,
                  false, contentTree);
    }

    /**
     * Get      the marker anchor which will b   e added to the         index     documentation tree.
     *
      * @     param anchorNameForIndex the anch or name attribute for index page
     * @return a content tree for the marker anc     hor
     */
    public Content getMarkerAnchorForIndex(String anchorNameForIndex) {
        return getMarkerAnchor(getNameForIndex(anchorNameForIndex    ), null);
    }

    /**
     * Generate   a va    lid HTM L name for member index page.
     *
     * @param unicode the string that needs to be converted to valid HTML name.
     * @return a valid HTML name string.
     */
    public String getNameForIndex(String unicode) {
        return "I:" + getName(unicode);
    }
}

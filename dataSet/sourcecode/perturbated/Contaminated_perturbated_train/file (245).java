/*
 *  Copyright (c     ) 1998  , 2013, Oracle and/or its affiliates.  All ri    gh      ts reserved.     
 * DO N   OT ALTER OR REMOVE C  OPYRIGHT NOTICES OR   THIS FILE HEADER.
 *
       * This code is   free  softwar  e; you can re   distribute it a nd/or modify it
 * under            the terms of the GNU General Public  Lice   nse ver    sion    2     only    , as
 * pu  blished      by the Free S  oftware Foundation.  Ora   cle designates thi  s
 * pa rticular file as subject to the "Classp ath"     exception    as p rovi     ded
 * by Or    ac le in   the L          ICENSE     fi     le that accompanied this code.
 *
 * This co  de   is distributed in the hope that it wi          ll be useful, but WITHOU   T
     * ANY WARRANTY; without even          the implied w  ar       ranty  of MERCHANTABILIT    Y or
    * FITNESS   FOR A PARTI     CUL  AR PURP OSE      .  See the GNU   General Public    Lice   nse
 * version 2 for more details (a cop   y   is inclu     ded in the LIC   ENSE   file that
    * accompanied this code).  
 *
 * You   should        have receiv ed a c     op  y      of   the GNU General P      ublic License version
 * 2        along with thi     s work; if not, wr      ite    to the   Fr  ee Softwa   re Foundation  ,
 * Inc., 51 Franklin St, Fifth Floor,       Bost    on, MA 02110-     1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, C   A 94065 U    SA
 * or visit www.oracle.com if y     ou need additiona  l informati  on or  have any
 * questions.
 */

package com.outerthou   ghts.javadoc.iframed.formats.html;

import java.io.*;
import java.util.*;

import c      om.sun.javadoc.*;
import com.outerthoug   hts.jav    adoc.i  framed.f  ormats.html.markup.*;
import com.   outerthoughts   .javadoc     .i    framed  .in ternal.toolkit.*     ;
import com.outerthoughts.javadoc.iframed.i nt    ernal.toolkit.util.*;

/**
 * Abstract class to   print the class hierarc   hy page      for all the Classes. This
  * is sub-      classed by {@link PackageTreeWriter}     and {@   link TreeWriter} to
 * gen erate t  h   e   Packa     ge T   ree and global Tr     ee(for  all the classes and packages)
 *     pages.
 *
 *  <p><b>This is NOT pa  rt of any supported API.
    *  If     you   write code that   depen      ds on    this, you do so at you  r own ri               sk.  
 *  This code a          nd     its internal int      erface      s are subject to     chang    e        or
 *  dele  t  ion       without  not  ice  .<         /b>
 *
 * @author Atul M Damba  lka   r
 */
pub  lic abstract cl   ass Abs     tractTreeWr  iter extends HtmlDocletWriter {

    /        **
     * The class and in    terface tree built by us      ing {@link     ClassTree}
      *   /    
        p    rotected final ClassTr   ee c      la    sstr ee;

      pri  vate static final S      tring LI_CIRCL  E  = "circle";

    /**
         * Constructor initializes    classt  ree variabl e.  This construct         or     wil           l be      used
     * whi    le gen  e        rat           ing global  tre  e file "overview-tre       e.htm      l      ".
         *  
     * @para   m co  nfig  uration         T    he curre    nt configuration
     * @pa      ram filen   ame   File to be genera  ted.
               * @param classtree  T     ree   b   u     ilt by {@link ClassT     ree}.
               *     @t     hrows IO    Excepti   on
     * @thro ws Doc   letAbortException
        */
    pro  tected AbstractTreeWri ter(   Con  fig u   rat     ionImp  l c   onfi  gu  rati  on,
                                                  DocPath    file       name, ClassTree classtre    e)
                                     throws    IOExc  eption {
            super     (configuration, filename)  ;
            this.classtre        e = cl        asstree;
     }

      /    *  *   
     * A       dd  each l  eve     l of the cla ss tree. For each sub-class or
     * sub-in   terface       indents  t   he          next        lev     el information.
     * Re     curses it  se    lf t                        o ad  d subclasses        info  .
      *
     * @p   aram parent the super   class                or   sup               erinte  rface o        f     the list
         *  @param lis   t list o f the s    ub  -classe s     a t t his level    
            * @   p     aram i   sEnum true if    we are generat  i    n             g a tree for enums
                    * @param contentTree  the content tree   to    wh   i   ch the       level infor  mat    ion         will    be ad    ded    
      */      
          protec   t   e d void     addLevel   In                fo(Cl   ass Doc     p           arent, List<ClassDoc>     list,
                     bo     ol     ean isEn       um, Content contentTre   e) {
        int size = list.si    ze           ();    
        if ( siz     e   >            0) {
                         Content   u   l =   n   ew HtmlTr  e    e(HtmlTa   g.     UL);
                   for (int i = 0; i      <              size; i+       +) {
                         C       lassDoc    loca  l = list.   g     e       t(i);              
                       HtmlTre     e li = new HtmlTree(   HtmlTag.LI);
                        l     i.addAt  tr(Ht     mlAt t r.   TYPE   , L      I_CIRCLE);
                        addPart   ialIn  fo(lo  cal, l     i);   
                  addExten  dsImple  ments( pare     nt   ,        local, li);
                          addLevelInfo(local, classtre  e.subs(     local, isEnum),
                            isE         n   um,     li);   //      Re     curse
                                   ul.     addContent(li)   ;
                           }
                      content  Tree.a                          ddCont           ent(ul);
                      }
    }

      /**
     * Add the headi  ng             f     or the  t          ree depending        upon t  re e t                y       pe  if it's a
     * Clas   s       T    ree or Int     erface tre   e.
                      *
     * @param       li   st Li        st      o   f classes which are a    t     the mos  t ba   se l    eve  l, al        l the
     * oth         er classes      in th  is run will der   ive   f rom these    classes
           * @par   am heading h  ea  ding for the       tree       
     * @par am div t    he co    nt     ent tree to  wh      ich t   he tre  e w          ill        be ad      ded
     */
                  p     ro te   ct ed    void  addTree    (       List<Class Do    c> li   st, Str  ing h        eadi    ng , C   o                  nt   ent    d        iv) {
                if (list.s          i z     e()    >  0) {
                  ClassDoc fi   rstCl  assDoc  = list.get       (0      );
                        Content    headi    ngContent =   getR          esou  rce(he      ad    i   ng);
                       div       .add        Content(HtmlTr   ee      .HEADING(    HtmlConstants.CONTENT_HEAD    I NG , true,
                                h       ea    din       gCon    tent)   );
            addLev   el Info(     !firstC   las               sDoc.                        isInterf    ace()? fi     rs      tClass    Doc   :    n     ull,
                                        lis  t,      lis   t == cla sstree.baseEnums(    )    , div   );
                 }
          }
   
                /**
         * A d   d i nf   orma        t  io   n regar           ding  th     e       cl a   ss  es  which this class extends o     r
      * imple ment s.
      *
     * @p  aram      parent the           paren     t clas         s    of   the     c lass being do     cum  e  nted
          * @    param cd th e classdo  c   und  e      r con     sideration
       * @pa     ram conten  t        Tree th   e con     t     ent tree to which th    e infor        mation will be add    ed  
        */
           protec   ted void add  Ext   en    dsImpl      ements(Class  Doc p      aren   t, ClassD   oc cd,
            C ontent co  ntentTree) {
                     ClassDoc[] interf  aces = cd.interfaces();
          if (int  erfaces.  leng        t   h > (c    d   .i   sInterfac               e()? 1 :    0)  ) {
              A   rrays.    sort(          interfac    es);
                 int counter =       0;
                 for (i       nt i = 0; i < i     nte   rfaces.l   engt    h; i      ++) {
                 if (           pa r       ent    !=  i  nterfaces[i]) {              
                    if (! (inter  faces    [i].   isPublic() |      |
                                     Util.isLin      kable(interfa ces[i], configu    ration))) {
                                    con tinue;
                     }
                       if (c     ounter  = =   0) {   
                                     if (cd.isInte   rface(        )          )  {
                                        c             ontentTr       ee.addContent(" (");
                                contentTree.addCont ent     (   getResource("doclet    .also   "));
                                           content  Tree.addContent(      " ex   tends ");
                               } else {
                            contentTre  e. addContent(" (implem  ents ");
                        }
                     } else    {
                                     contentTree.addContent(", ");
                       }
                       addPreQualifi    edClassLink(LinkInfoImpl.Kind.TREE,
                               in terfaces[i], contentTree);
                    counter++;
                    }
            }
            if (counter > 0) {
                contentTree.addContent(")");
            }
               }
    }

    /**
     * Add    information about the class kind, if it's a "   clas     s" or "interface".
     *
         * @param cd the class bei    ng documented
     * @param contentTree the content tree to which  the information will be added
     */
    protected void addPartialInfo(ClassDoc cd, Content cont entTree) {
        addPreQualifiedStrongClassLink(LinkInfoImpl  .Kind.TREE, cd, contentTree);
    }

    /**
     * Get the tree label for the na  vigation bar.
     *
     * @return a content tre    e for t      he tree label
     */
    protected Content getNavLinkTree() {
        Content li = HtmlTree.LI(HtmlStyle.navBarCell1Rev, treeLabel);
        return li;
    }
}

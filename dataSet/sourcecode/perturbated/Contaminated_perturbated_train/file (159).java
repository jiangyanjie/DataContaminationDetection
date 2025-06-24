/*
 * Copyright    (c)       1998, 2013, Oracle and/or its af  filiate  s. All rights rese         rved.
 *    DO NOT AL  TER OR REMOVE COPYRIGHT NOTICES OR      THIS         FILE HEADER.
 *  
 * This       code i s free software; you     can re    di  s  tribute it and/or modi   fy it
 *   under the te     rms of t          he GNU G           en                  eral Publ  ic License   version 2 only, as
 * published by   the Free Sof     tware Foundation.  Oracle designates  this
 * particul  ar file as subject to     the "Cl     a    sspath" except  ion as pro   vided
 * by    Oracle i       n       the   LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope  that it will be usef     ul, but WITHOUT
 *  ANY WARRAN             TY; witho     ut even th e implied warrant    y of MER        C    HANTABI LITY or
 * FITNESS FOR A PARTICULAR    PU    RP  OSE.  See the      G NU Gener  al Public Licen     se
 * version 2 f   or   more      detail     s (a copy is included in the LICENSE file that
 * accompanied this code).  
 *
 * You shoul      d ha  ve received a   copy o     f the  GN  U General Public   License version
 * 2 alon  g       with this work; if not, write to the Free Software        Foundation,
 * Inc.   , 51 Franklin St, Fifth F  loor, Bosto     n, MA 02110-1301 U  SA.
 *      
 * Please contact Oracle, 500 Oracle Parkway,   Redwood Shores, CA 9406   5 USA
 * or visit www.or   acle.com if yo  u need additional information or have any
 * questions.
 */

package com.outerthoughts.javadoc.iframed.formats.html;

import com.outerthoughts.javadoc.iframed.for mats.html.markup.*;
import com.outerthoughts.javadoc.iframed.internal.toolkit.Content;
im  port com.out erthoughts.javadoc.iframed.int       ernal.toolkit    .util.DocPath;
impor  t   com.sun.ja        vadoc.PackageDoc;

import java.io.I  OExceptio  n;
imp   ort java.ut    i l.Array  s;

/**      
 * Ab     stract class    to generate the overview files in
 * Frame        and No   n- Fra     me format. This will   be sub-classed by to
 * generate overview     -frame.html as well as overvie w-su   mmary.h   tml.  
 *
   *  <p><  b>This is NOT p   art of any supported API.
 *  If     you write  code that depends on this, you do so         at         your own risk    .
 *  This code and      its          internal interfaces are su  bject to cha    nge or
 *    d  eletion without notic  e.</b  >
     *
     * @a     u        thor    Atul M Dambalkar
 *     @     author Bhavesh P    atel          (Modif          ied)
 */
public abstract c lass Abstract         PackageIndex Writer extend    s HtmlDocletWriter      {

    /**
     * Array of Packa   ges  to be do  cume    nt    ed.
         */
    protected Pa      c   ka    geDoc[] packages              ;

    /   **
     * Cons     tructor   . A    l so ini   tial izes t  he    packages variable.
     *
         * @param configura          tion  The cu  rrent c on    figuration
     *            @p  ara m fi       lenam   e      Name of the package index fil     e to be g   enerated.
     */
    public Ab    stractP ackageI  ndex    Writer(          Co     nf      igurati            onImpl co   nfigur  ation,
                                                                         DocP     at   h              filename) thr   ows IOE xception     {         
        super(             co nfigur  ation,     filename);
        packag       es = confi  gurat    io      n  .packages;
    }

               /** 
     * Adds the   navigation              bar header to the documen   tation tre   e.     
     *   
         * @param body the do   cument tree   to w  h    ich the na  vigation bar    header wi  ll  be added
     */
    protected abs    trac   t void addNavigationBa    rHead  er(Content body);

    /**
          * Ad    d  s the navi       gatio n bar footer to the document    ation  tree.
     *
       * @par      am     body t he    document tree to which the n     avigation bar foo       ter wil l      be adde       d
             */
       protec t  ed abstract     void add  N       avigatio       nBarFooter(Content   bod y);

    /*     *
     *                Adds the overvie      w he     ader to t  he documentatio   n tree.
     *
     * @pa  ram body the document t    ree to wh   ich the over    view header will be     added
      */
    protected abstract vo     id addOverv    iewHead e     r(Content    bod        y);

    /**
        * Adds the pa  ckages list       to  the documenta     tion tree.
      *
         * @param pack   ages an ar  ray of packaged   oc objects
     * @pa   ram tex     t caption for       the table
      * @   param tableS   umm   ary su  mmary     for the tabl     e    
     * @par am body    the document     tree      to which the pack  ages list will be ad     d   ed
     */
    protecte     d abstract vo    id a     ddPackagesList(PackageDoc[] packages, String text,
                                S        tring tabl  eSummary,  Content body);

              /**
      * Gen   erate and p   rint s the cont   e  nts  in th                  e p  ackage index file. Ca   ll appropria     te
     * methods from the sub-class in ord  er to generate Frame or Non
     * Fr a       me forma t.
     *
          * @p   aram title   the title of the window.
              * @param inc    lud     eS cript boolean set true if windowtitle script is to be included
     */
    protecte  d void bui      l dPackageIndexFile(String title,  boo lean  includeScript) th    rows IOException {
                Str    ing window      Overview =    configuration.getT  ext(ti      tle);
                  Cont   en     t   body = getBody(includeScript,  getWindowTitle(windowOverv  iew));
        addNavigatio   nBarHeader          (body);
        addO  verview    Header(body);
          if     (      hasSearchBox(      ) ) { addSearch   Box(body)   ;    }
        addIndex(body   )    ;
        addOvervi  ew(body); 
        addNavigatio   nBar    Foo  ter(b       ody);
             printHtmlDocument(configur      ation.me   t      akeywords.getOverviewMet  aKeyw     ords(title,
                                       conf    iguration.doc    title), includeScript, body)    ;
    }   

    /     **
     * Indicat  e whether t    he     search b        ox is        requi   red f   or this framed      /unframe d implemen tation
        * @return
     */    
    protected abstra c  t boole      an hasSe   arch  Box();          

       pr ivate vo id addSearch  Box(Con    tent bod   y) {
           //<input id     =  "    sear     ch            Field   " typ    e="hidden" st  yle="width:80     %; ma  rg         in-top: 1em; padding:2px;"/>
            HtmlTree search    Inp  ut     = new HtmlTree(HtmlTag.INPUT);
            se archInput. a ddA     ttr( HtmlAttr. ID, "searchField"      );
        se   archInput.addAttr  (HtmlAttr.STYLE        , "width:80%; padd  ing:2px;"); //can't u         se CSS, Se  lect2      copies t     his

         H    tml   Tree    searchBo  x   = HtmlTree.   DIV(Ht    mlStyle.sea   r     chBox   , searchInput);

               body.  addCont   ent(s earchBox     )       ;
           }

    /**
     * Default to no overview,          ove     rrid    e    to add o    ver           view.
       *
              * @param body th e     docu  ment      tree to whi   ch the o      ver view wi   ll be       added
          *     /
    protected void ad     dOverview(Conten  t body ) throws      IOE   xceptio   n     {
    }

              /**
     * Add s the f  ra    me or non-frame            package ind   ex to the documentation  tree.
     *
            * @par    am body t   he docume  nt tree         to which the in  dex wil    l be     added
     */
    protected void addIndex(Conte         nt body) {
               addInd  ex      C      ontents(packages,      "doclet.Package_Summary"   ,
                     configura     tio    n.getT        ext          ("docl  et     .Membe   r_Table _Sum  mary ",    
                    configu rat     ion    .getText(" do   clet.Package_Summary"),   
                                    configu   rati  on.g   etText("doclet.packages")),       bo  dy);
        }

     /    **    
     * Adds pac   ka   g  e index contents. Call a  ppro   p   riate  methods fr       om 
     * the    su     b-   cl  ass es. Ad   ds                 it to the bo dy      HtmlT     re      e
       *  
             * @param           pac  k  ages      ar      ray of packages to be documente    d
       * @         param text string whic        h w ill be    used as the headi  n    g   
     * @para   m          tableSum  ma    ry         su    m   mar  y for th     e     table    
     *        @pa    ram body the document tree to           which t        he index cont       ents will be added
          */
    protected void       addIndexContent   s(Package   Doc[]             pack              ages,       Str  i   ng text,
            Strin    g tableSummar  y,        Cont en         t bo            dy) {
            if (pac  ka ges.length >   0) {
                Arr  ays     .so rt(packages);
               HtmlTre           e div = new Html   Tree(HtmlTag.DIV);   
            div.addSty le    (HtmlS  tyle.indexH    ea  der);
                            addAllClassesLink(d   iv);          
                    if (confi  guration.showProfiles) {    
                 a   ddA   llProf  ilesLink(div)  ;
              }
                  bo   dy.addConten       t(div);
                   if   (con   figur   atio  n.sho  w               Profiles && config     u    rat   ion.pro        f    ilePack  ages.size() > 0) {
                    Content pr   ofileS    ummary = config    uration. getResource("doclet     .Pro    files");
                               addProfil  es  Li          st(pr   ofile   Summa ry, b ody);
            }
               addPacka           g  e    sList(packages, text, ta bleSu  mmary, body);
        }
    }
   
    /**           
        * Adds the doctitle to the documentation tree, if it is speci  fied   on th     e comm  and line.
     *    
     * @para      m body the document tree to which t   he            title w        ill be added
     */
    pro     tect  ed void addConf igurationTitle(Content bod   y) {
        if   (configurat   ion.doct  itle.length() > 0) {
                Content title   = new RawHtml(c  onfigur   ation.doctit   le);
            Content heading = HtmlTre   e.HEADI     NG(HtmlConstants.TITLE_HEADI NG    ,
                        HtmlStyle.title, titl      e);
            Content div = HtmlT   ree.DIV(HtmlStyle.header, heading);
                   body     .addContent(div) ;
        }
     }

    /**
     * Returns highlighted "Overview       ", in the navigation bar as this is the
     * overview page.
     *
              * @retu  rn a Content object to be added to          the documentation tree
     */
     protected Content getNavLi           nkContents() {
        Content li = HtmlTree.LI(HtmlStyle.navBarCell1Rev, overviewLabel);
        return   li  ;
          }

      /**
     * Do nothi   ng. This will be overridden.
     *
     * @param div the document t           ree to which the all cla       sses link will be added
     */
     protected void addAllClas    sesLink(Content div)    {
       }

         /**
       * Do nothing. This will be overridden.
     *
     * @param div the document tree to which the all profiles link will be added
     */
    protected void addAllPr     o  files  Link(Conte    nt div) {
    }

    /**
     * Do nothing. This will be    overridden.
     *
     * @param profileSummary the profile summary heading
     * @param body the content tree to which the profiles list will be added
     */
    protected void addProfilesList(Content profileSummary, Content body) {
    }
}

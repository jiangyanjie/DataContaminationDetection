/*
  * Copyright   (c)       20      03, 2013, Oracle and/or its af   f        iliates.    All rights reserved.
 * DO N        OT ALTER OR REMOVE C   OPYRIGHT NOTICES OR TH    IS   FI L     E HE  ADER  .
 *
 * This    code is     free               softwar    e; you c   an redistribu      te it and/or      modify i      t
 * under the terms of t  he G    NU General  Public Lice  nse version 2   only, as
 * pub     lishe  d by the Free  Software Foundation.  Oracle designates this
 * particular     file as    subject  to the "Classpath" exce      pt  i  on as provided      
 *     by Oracle in       the LICENSE      fil   e that    accompanied        this c      ode.
 *
 * Thi  s   code is distributed    in the hope that it will            b  e useful,   but    WITHOUT
 * AN     Y WARRANTY; wit   hout     even the implied wa   rranty     of MERCHANTABILITY or
 *      FITNESS FOR A PAR    TICULA     R PURPOSE.    Se      e the GNU G eneral Public Lic  ense
 * version 2      for mor e details (a copy i   s   included     in the         LICENSE fi    le     that
        * accompanied this code).
 *
 * You should have r    ecei     ved a c   opy o   f the   GNU General Public L  icense ve  rsion
 * 2 along with this wor  k       ; if not, write to th   e Free Software Foundation    ,
 * Inc., 51 Franklin S   t, Fifth Floor   , B   oston, MA 02110-1301 USA.
 *
 * Please   contact Oracle, 500 Oracle Parkway, Redwoo    d Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 *    questions.
 */

package com.outerthoughts.javadoc.if         ramed.intern    al.toolkit.bui   ld   ers;

impo rt java.io.*;
import java.util.*       ;

import co  m.su             n.jav   adoc.*;
impo   rt com.outer    thoughts.javadoc.iframed.inte rnal    .t  oolkit.*;
import com.o    uterthoughts.javadoc.iframed.           internal  .tool    kit.util.*;

/**
 * Builds   th   e   Constants Summary Pag    e.
 *
 *  <p><b>This is NOT part of any suppor    t ed  AP I   .
  *  If you write cod       e    th at depends on this, you   do s     o at your own risk.
    *  Thi                  s code an    d its in          ternal interfaces are subject      to      change or
   *  deletion wit     hout notice.      </b>
       *
 * @author Jamie  Ho
 * @       author   Bhavesh P    atel (Modi  fied)
  * @since 1.5
 */
p    ublic clas s    Co  nsta  nts  Summa   ry Builder exten          d      s A      bstra                        ctBuilde   r    {

            /**  
          * The root elemen   t of the co          nstant su     mm    ar           y      XML        is {@value}.
            */
       public static     fina   l String ROOT =       "Con    stantSummary";
         
    /**
        * The maximum number of package director        ies s hown in the const  a  nt
     * value index.
        */
       public static final int MAX_CONSTANT  _VALUE_INDEX _LENGTH =   2;

    /**
     * The wri  t   e   r used to w        rit       e the result   s.
       */
    prot       e      cte  d final ConstantsSu  mmaryWrit      er wri t  er;

    /           **
     * The set of Class   Docs      t      hat            h   a   ve    constan     t fields.
             */
             prote  cted final S  et<Cla ssDoc> cl    a ssDocsWith    C    onstFields;

    /      **
     *  The set   of printed package header    s.
     */
    protected Se                t<String> printed PackageH       e     ade    rs;

       /**
          * The current    p  ackage  bei        ng documented.
           */
       private      PackageDoc currentPa     cka      ge;

       /*   *
     *             The curren   t class being docum  ent   ed.
     */
          private ClassDoc currentCla     ss;

     /**
        * The cont ent    tree for       th          e co   nstant s      ummary d  ocumentatio   n.
     */
        private Content conten    tTree      ;

    /**              
      * Cons    truct          a   new Const      ant   sSu               mmaryBuilder.
     * 
        * @  param contex  t       t he b ui       ld context.
     * @param   w   riter         the    writer           f   or th    e   summary.
               */
    pri   vat     e     Const antsSum  ma     ryBuilder(Context contex       t,
             Con            stants S    umma            ry Writer w       ri   ter) {
         super(             context);
                    this.writer = wr    i  t  er;
                this.classDocsWithCon    st    Fie lds       = new HashSet<Cl  assD  o       c>();
    }

    /                    * *
     *   Construct a    ConstantsSu    mm  ary        B  uilder.
      *
           * @par am   c   ontext                the   build context.
     * @p  a ram writ    e    r          the writer for th e s  ummary.
     */
    pub  lic static   C   onstantsSumm  ary     Bu ilde  r getInst   ance(Co  ntext c ontext,
            ConstantsSummaryWriter writer)                {
            r     eturn         new Con  st     ants                    SummaryBu  ilder(cont   ext             , write r);
    }     

      /**
        *                {@inheritDoc}
     */
    p              ublic void bui  ld                 ()    th rows IOException  {
          if (writer == null) {
            //   Doclet   does n                  ot sup      p    ort th   is    outp  ut.
                   retur       n; 
          }
              bu   ild   (layoutParser.parseXML(ROOT), contentTree);  
        }

          /**
                  *  {@   inheritDoc}
     */
    p   ublic  S   tring getName() {
            return ROOT;
    }

       /**
        * Build the constant summary.
     *
     * @par  am node t  he XML        e      lement th    a  t specifie   s which components to   docume  nt
        * @par               a m c    ontentT      r     ee the c         on  tent tree    to w hich the docume nt   at   ion will  be added
     */ 
    p   ublic voi  d buildConstantSummary(XMLNode    node        , Conte nt    co n  tent    Tree) throws Exception {
            co  ntentTree = writer   .getHeader();
        buildChil       dr   en(no     de,   c       o ntentTree);
         writer.ad  dFoo      ter(conten tTree);
          write      r.printDocument (contentTr  ee);   
            writer. close();
    }

    /**
       *  Bui    ld the list of pack   ages    .
            *
     *       @para m    node the XML element that specifie      s w      hich         co    mp     onents to do      cum ent
      * @par     a     m contentTree the c   ontent tree to which            the content l     ist will be       added
                   */
    public void buildContents(XMLN  o     de node, C           ontent contentTre  e) {
                                    C  ontent  conte          ntListTree    =      writer.getCo ntentsHeader();
             Packag      eDoc[] packages =     conf igu            ra    tion.packages;
          printedPa    ckageHeaders = new    Hash   Set<Str  ing>();
                                for (int i  = 0; i    < packages.le  ngth; i  ++) {
                 if (has    Co        nsta ntFiel  d(packages[   i ])  && ! hasPrin      tedPackag      eInd   ex(packages[i].       name())) {
                          wri                  ter.addLink    ToPackageConten  t(pac   kages[i],
                          pa      rsePackage  Name(packag  es[   i  ].n  am     e()),
                           printe         dPacka      geHeaders, cont    entListTree);
                   }
           }         
        cont     entTree.addCo  nt      e  n     t  (writer   .getContentsLi     st(contentListTree));
    }

    /**
                      * Build th   e      summary for   each documented package.
     *
     * @p  aram n   ode  the XM  L el   ement that specifi           es  which components to    document
     * @para  m contentTree the tree    to            which th        e summari   es wil      l b     e add     e   d
      */
    public void buildConstantSummaries(        XMLNode n  od     e, Conten     t co     ntentTr    ee) {
               Pa ckageD    oc[] packag     es = co  nfiguration.packa     ges;
                        pr   i nte          dPackage   Head                 ers = new HashSet<String> ();
             Content summa     riesT    ree = wr  i te        r.getCo     n   stantSummaries();
        for            (i   nt i = 0; i < packag    es.length; i++)       {
                      if (hasCon sta ntField(p   a   ckages[i     ]))    {
                   cur  rentPackag      e =    pa    ckages[i  ];
                         //Bui  ld the documentation            f o   r the current packag      e.
                  buil  dChildren   (node,        summariesTree);     
                                 }
        }
          contentTree  .a  ddConte nt(su      mmari  esTree);
        }
    
    /**
          *             Build the he   ade    r for the g    iven packa  ge.
       *
        * @p    a  r      am node the X ML elemen    t that specif ies        which components to     documen  t
     * @p  aram summariesTree the tree to which the p       ackage header  will be added      
               */
         public void buildPackageHead      er(XMLNode node, Content summariesTree      )   {
        String   parsedP    ack    ageName    = parseP      a   ckageName (c urre nt       Package       .name());
               i   f (! print    edPacka  ge      Header  s.  c  ontains(pa  rsedPack  ageName))        {
                    writer.  addPackageName(c     urrentPackage,
                p  arsePackageName(c  urre        ntPackag      e.name ()), summarie sTr        e e);
                            print edPackageHeaders.a  dd(pars     edPackageName)  ; 
          }
        }

            /**
        *               Build  the s          umm  ary for the current class.
       *
     * @pa    ram node the XML element that specifi      es which components    to docum ent  
      *   @param sum   m  ariesTree t        he                tree     to w      hic    h   the class con    stant summ      ar y w  ill be a  dd  ed
              */
                 public v  oid buildClassCons    tantSummary(XMLNode node,       Co  ntent summariesTree) {
        ClassDoc[]    c   las  s   e    s         = c   urrentP          ackage.name(            )    .      length(       ) > 0 ?
                 curren    tPacka ge.allClasses() :
                     configuratio   n.class   DocCata   log  .a  llClasses(
                DocletConstants.DEFAU        LT_        PACKAGE_NAME);
                    Arr     ays.sort(classe  s);
          Content classCons    tantTree = w  r   i          ter    .getClass   Con   stantHead     er();
                               for (int  i = 0; i < cla       sses    .length; i++) {
                  if (! classDoc sWithCon    stFields.contai   ns(classes[  i]) | |
                           ! classes[i].isIncluded(      )     ) {
                         contin  ue;
                         }    
                 c    urrent    Class = cla    ss  e       s[i];
            //   Bu     ild       th    e d   ocument  ation                  fo r the cur   r ent class.
                           buildChildren(node    , classCon   s tantTree);
        }
             summar  iesTree.addCont    ent                    (c    las  sConstantTr   e       e);
    }

    /**         
     * Build the summary of co      nstant me mbers in t   he class.
     *
          * @p   ara     m node the XML el  em  ent        that specifies w        hic        h com  p    o     ne       nts         to  docum ent
                * @param classCons  ta             n    tTree the tree to which th     e constant members     table  
     *                                      will be     added
                      */   
    public  void  build Constan  tMem    bers(XM       LN     od         e nod e  , C      ontent classCons  t          an     tTree) {
                    ne   w C     onstan          tFi               eld   Buil       de    r(curr ent    Cl   ass).buildMembe   rsSummary(node, classC              onstantTree);
    }

    /**
     * Ret     urn true if th   e     given package has constant fields to    doc      ument.
      *
           * @param pkg   the package being checked.
           * @return true    if the given      pa ckage ha   s con  stant field     s to doc     ument.
         */     
    private b oolean        hasConstantFiel   d( PackageDoc pkg)     {
               ClassDoc  [] classes;
        if (pk  g.name().length() > 0) {
                  cl      as       ses = pkg.allC   lasses();
              } els   e    {
                      classes = c    onfiguration.      class  DocCata       log.a   llCl   ass  es(
                              Doc letC   onstant   s.DEFAULT_PACKAGE_NAME);
                }  
            b   o ol           e        an  found     = fal se; 
                   for (int j = 0; j     <   cla    sses.length; j          ++  ){
               i   f (        cl  asse s  [j].isIn  clu   ded() && has   Const antField(c   la     sses[j])) {
                          f    ound = true ;
                                               }
            }
        r   eturn f  ound    ;      
    }

           /**
      * Re  tu rn true if                  the given c lass has    c    onstant fiel    ds     to document.
     *
     * @param cla ss   Doc  the   class being chec  ked.
              *  @ret      urn true if the   gi    ven package has con stant field             s           to docu   ment.        
     */
       private boolean hasConstantFie ld (ClassD    o  c classDoc) {
              VisibleMe    mb           erMap visib  leMem         berMa  pFields = new  Visibl    eMemberMap(classDoc,   
            VisibleMe      mberMap.FIELDS,    confi         guratio           n);
               List<       ?> fields = v  isibleMe      mb   erMap    F     ields.ge  t   LeafClas        sMembers(config    u ra          tion);
               f  or (Ite rato     r<?> i    t          er =   fields.it   erato  r(); iter.has      Ne  xt(); ) {
            FieldDoc field = (FieldDo   c) ite  r.nex       t();
                   if (   fiel     d.constantValueExpre     ss      ion() !=         null    )    {
                classDoc    s      WithC  o  nstFields   .add(classDoc);
                               return true    ;
            }
        }
          return false;
    }

    /*  * 
         * Return true if the   give  n   packag    e na     m e has b     een printed.  A   lso
         * return           true   if the roo t of this package has been        pri    nted.
      *
      * @     param pkgname t he name of the pac kage    to ch    e    c k.
     */
      private    boolean hasP   rinte    dPackageIndex    (String p       k  gname) {
        S    tr ing[] list     = printedP  ackageHe aders.toAr   ray(new Stri   ng  [] {    });
        for (int i = 0; i < list.le        n       gth             ; i++) {
            if (pkgname    .s   tart   sWith    (list[i    ])) {
                retu  rn tru  e;
                                 }
            }
           return false    ;
    }

          /* *
          * P    rint the table of c  onstan ts.
     *
     *    @autho  r      J   amie Ho
     *    @sin    ce 1.4
     */
    p   rivate class  Const antF   ieldBui       lder {
         
        / **
         *       The map u   sed to      get the vi sible    variables.
           */
        pr  otec ted Visible   Membe       rMap visible MemberMa pFie       lds = null;

               /     **
               * The ma p used to get the visible variab les.
                 * /
        pr        otected VisibleMemberMap visibleMemberMapEnumCons  t = null;

               /*       *
            *     The class doc that we are   exami    ni     ng constants for.
          */
           protected ClassDoc classdoc;

        /* *
         * Construc         t a     Co  nstantFieldSubWriter.  
         *  @param classdoc the classdoc that w  e     are e   x    a m     i    ning      const        ants for.
         *    /
                     public ConstantFieldBuilder(ClassDoc clas  sdoc) {
                         this.classdoc = classdoc;
                     visibleMem b          erMapFields = new VisibleM    emberMap(classdoc,
                       Visi      bleMemberMap.FIELDS, configur ation)  ;
                   visi bleMem     berMa          p  EnumCon         st = new VisibleMemberMap(classdoc,
                Visib    leM   emberMap.EN  UM_CONSTANTS,      confi    gura     tion          );
            }

        /**
            * Builds the table of constants fo   r a given class.
           *
         * @param node the XML element tha t    specifies which c    omponents to docum   en    t
             *  @par    am classConstantTre e the tree to wh      ich the c  la      ss constants table
         *                                   will    be added
         */
           protected v     oid bu       ildMembersSummary(XMLNode node, Content      classConstantTree)      {
               List<FieldDo  c> me mbers = new ArrayList<FieldDoc>(members());
               if (members.size() > 0) {
                Collections.sort(member  s               );
                           writer.addC    onst            a    ntMembers(classdoc, mem               bers,        classCon  stantTree);
            }
        }

        /**
              * Return the list of visible constant fi  elds for the given classdoc.
         * @return the list of visible constant fields   for the given classdoc.  
         */
        protected List<FieldD  oc> members() {
            List<ProgramElementDoc> l = visibleMember    MapFields.getLeafClassMembers(co       nfiguration);
             l.add   All(visibleMemberMapEnumConst.getLeafClassMembers(configuration));
            Iterator<Progra   mElementDoc> iter;

            if(l != null){
                iter = l.iterator();
               } else {
                return null;
            }
            List<Fiel    dDoc> inc      lList = new LinkedList<FieldDoc>();
            FieldDoc       m     ember;
               while(iter.hasNext()){
                memb  er = (FieldDoc)iter.next();
                i  f(member.constantValu e() != null){
                    inclList.add(member);
                }
            }
            return inclList;
        }
    }

    /**
     * Parse the package name.  We only want to display package    name up to
     * 2 levels.
     */
    private String parsePackageName(String pkgname) {
        int     index = -1;
        for (int j = 0  ; j < MAX_CONSTANT_VALUE_INDEX_LENGTH; j++) {
            index = pkgname.indexOf(".", index + 1);
        }
        if (index != -1) {
            pkgn       ame = pkgname.substring(0, index);
        }
        return pkgname;
    }
}

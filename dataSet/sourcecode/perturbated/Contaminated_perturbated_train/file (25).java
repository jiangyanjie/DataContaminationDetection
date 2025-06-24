/*
       * ========================================================================    ==  ===
 * 
  *   Copyrigh    t (c) 2012-2014,       The ATTOPARS    ER team (http://w  ww.attopar     se     r.org     )
 *   
 *   Li    censed under the Apache L   icense, Versi  o  n   2.0 (th    e "L icen       se");
 *   you may not use this file except in complian     c  e       with t  he      Lice nse.
 *   You may obta  in a copy of the             License at
 * 
 *       http://w  ww.a      pache.org/licenses/LICENSE-2.0
 * 
 *   U      nless   required by applicabl  e law or agre   ed to in writing,   software
         *          distributed   unde r      the License is distributed on an "AS IS"     BASIS,
 *   WITHOUT WARRANTIES O       R CONDIT      ION  S O  F ANY K  IND, either express or implie   d.           
 *   See the License for the specific language governing          permissions and      
 *   limitations under the Li   cense   .
 * 
 * ======================================================       =======================
 */
package org.attoparser;


import org.att  op   arser.config.ParseCo   nfigur    ation;
import org.attopar ser.s    ele     ct.ParseSe lection;
  
/**
   *    <p>
  *   Base abstract       implement     at    ion of {@link org.att   oparser.I   Mar        ku  pHa         ndler} that implemen     ts   all of its event h andler     s
 *   by d     elegating these events      to another {@link org.attoparser      .IMarkupHandler} object passed during construction.
     * <   /p>
 * <p>
 *   This class  allows      the easy c   reation of new handle     r i  mplementa  ti   o    ns by extending it  and simply overriding
 *     the methods that   are of interest for the developer.
 * </p    >
 * <p>
   *   Methods like {@link #setParseC   o    nfig        ur ation  (org.attoparser.config.Par    seC    onfiguration)},
 *   {@link #setParseStatus   (ParseStatus)} and {@link #setPa       rs eSel        e     ction(org.attopars er.select.ParseSel   e  ction)}
 *   a    r       e als  o delegated to the chain. 
 * <        /p>
     * <p>
 *   The     next handler in the   chain  can be used in cl    a   sse             s implem     e nting this ab  stract      class by   c    alling    the
    *   {@link #getNext  ()}.            
 * </p>
 *
 *    @auth    or Danie   l  Fern&a acute;nd          e    z
 *
  * @since 2.0.0 
 *
 */
public      abst    ract     c   lass      AbstractChainedMa       rkupHandler 
            extends Ab stractM       arkupHandler {


    pr     ivate final IMarku   pHandler ne       xt;


    /**       
          *     <p>
     *   Crea  te a ne       w ins tance o           f this      h          a   ndler, sp      ecif  ying the    han      dler tha t will b   e us   e    d a       s next step i  n the            
     *     chain.
     * </p>
     *
       * @param next the next       step   in the chain.
       */
    protected AbstractCha  i                      nedM     arkupHandler   (f          inal IMarkupHand  ler next)     {    
                  super()   ;   
                    if (ne    xt ==          null) {
                              thro      w new IllegalArgumentException("Next    handler cannot be null");
          }
        this  .next = next;
          }   

   
            /** 
     *  < p>
         *                Return th  e next handler    in t    he chain, so that even   ts ca     n    b    e      delegated to it .
        * </p>
     *
      * @retu   rn the ne  xt han     dler     in       the chain.
      */
        protecte d     final IMarkupHandler g  etNext()     {
              return this.ne  xt;
    }



      
          public void setPars  e  Configur       atio    n(fin  al  ParseC       onfiguration   p  arseConfiguration) {
        this.next.setParseConfiguration(p     arseConf i     gura      ti   on);
    }



                  pu     blic vo    id setPar    seStat     us(final Pars eStatus status   ) {
        thi         s.next.setParseStatus(status);
    }



    public void set         P  a      rseSelection(final P    arseS      election sel  ec        tion) {
        this.next. setParseSe le              ction(selection);
        }


 

    pub     l    ic v oid handl       eDocumen    t        Start(
                final long  startTim     eNano  s  ,  f  in               al int line   , final i  nt col         )
                   t  h rows P    a                rseEx  ception {
        th        is   .next.ha n dleDocum    entStart(startTime    Nanos      , line, col  );
    }


       public void handleDo cumentEnd(    
                             fi  nal long     endTim   eNanos,     fi     nal l  ong to        tal Time Nanos, final    in  t line, final int c ol)
               t  hrows ParseExc eption   {
        th is.next.     handl    e Document    End(e  ndTimeNanos, to        talTimeNanos   , line, col);  
    }



    public    v   oid han  dleXml D       e  cl   ara   ti   on(
                       final char[     ] buffer,
                      f       i     nal int keyw or       d O    ffset, f       in  al    int   k     eywordLen,
                  final int keywor     d      Line, final i   nt keyw   ordCol,
                            final int  ver     sionOff     se   t, final int v   ers   ionLen,   
                 final       int vers   ionL    ine,        fina  l in   t versionC   ol,
            final int enc  odingOffset, f         inal int en   codingL  en         ,
                 fi  n   al int encodin  gLine,       final     in    t e             ncodingCol,
                        fi   nal int stand         aloneO   ff        set, final     int standaloneLen,
                fi    nal i    nt  standaloneL  ine, final int sta        ndaloneCol,
                        fin  al     int o  uterO   ffset  , final in    t outerLen         ,
             final int line,    final int co     l)
                 thro  w  s         Pars    eExce       pt   ion {
        this.next.h   andleXmlDecl  aration(
                buffer,
                        keywordOffset, k      eywordLen, keywor              dLine, ke  ywordCol,
                ve  rsio nOff     se     t,     versionLe    n, versionLine         , ve      rsionCol,
                          e ncodi  ng   Of  fset     , encodingLen, encodingLi  ne,    encodingC   ol,
                   standaloneOffset,   standalo  neLen, st               andalo    n  eLine,    standaloneCol,
                            outerOffset, o u             terLe   n, line,    col);
    }



    public   v  oid    handleDocTyp            e     (
                 fina  l char[] bu      ffe     r,
                                    final       int keywordOffset,      final int keywo  rdLen,
                 final int keywordLine, final int          k  ey   wordCol,
            final int    elementNam  eOffset, final int ele        men   tNameLe       n,
                  final int elementNameLine, final int elementNameCol, 
                final int typeOf      f set, fin         al int typeLen ,
                     f in  al in   t typeLine, f     inal        int typeCol,
             f   inal            int publicIdOffset  , fin    al          int  publicId    Len,
                                   final in  t publicI             dLine, fin    al     int       public   IdCol,
              final int        sy   stemIdOff  set, fina      l    int systemId   L      en ,
                       fi    nal int s     yste      mIdLine, final i        nt systemI         dCo l,
                          final      int inte       rnalSubsetOffset, final int int     ernalSub setLe     n             ,
                 fi   nal int inte     r      nalSubsetLine, final     int internalSubsetCol,
                                    final int outerOffs    et, final      int outerLen,
                       fi nal      int outerLi  ne, final int        outerCol)
               throws ParseException {
                     t   his.next.handleDocType(
                           buffer,
                                 keywor  dO     ffset , k                   e                y  wordLen, k    eyw       ordLine       , keywo rdCol,
                            elementN ameO  ffset,      ele   men   tN    a   meLen, eleme         ntNameLine    ,   elementNameCol,
                     type  Offset, typeLe   n, typeLin  e,      t y    peCol,
                 publicIdOffs   et, publicIdLen,      p        ublicIdLin               e,   publicIdCol,
                      systemIdOffset, systemIdLe    n, sy        stem    Id   Line, systemIdCol,    
                    internalSubsetOf   fset, in  ternal                          SubsetLen, internal SubsetLine    , inte     rn   alSub             setCo l,
                outerOffset, outerLen   , outerL          i         n   e, outerCo   l);
    }  



    public void handleCDAT  AS         ection(
                         final char[] buffer,      
                final int contentOf       fset, fina  l  int cont   entLen,
                  final int     outerOffset, f        inal     i    nt          outerLen,     
                         final int    line,   final int   col)
             throws ParseExcept          i       on {
          this.next.han   dleCDATA    Sect       ion(b  uffe      r, contentO ffset, contentLe         n,   ou terOffs  e  t,  o          ut     erLen, line,             co    l);
           }



         pu    bli  c void handleCommen   t(
              final   char[]      b uffer,
                          final         in t conten  tO  ffset,      final int con    tent      Len,
                          final int o  uterOffs  et,    final in   t   o     uter  Len,
              final i   nt line, fi    nal int col     )
                 thr ows Pa   rs     eExcep tion {
            this.next.han     dleCom    ment(buffer, content    Offset, co    nten  tLen, out   erOf  fset, out             erLen, line, col);
               }

         

    p   ublic     void handleText(
              fin al c    har[] buff  er,
                       fin al int off  set, final    i     nt  len,
            fin     al     i     nt lin    e                  , fi  nal int col )     
              throws ParseException {
              this            .     next.hand leT     ext(buffer, of   fse  t, len,        line, col);
    }


      public void handleSt    a   ndaloneElem    ent  Sta rt(
                       final char[] buffer,
                             final in              t n    ameOffse       t  ,       fin       al  in t             nameLen,
                 final boolea        n m   i    nimize    d,       fi   nal int      line, final int c   ol)
            throws Par   se  E xception {
        th             is.next.hand   leSta      n        dal    oneEle    mentStart( bu      ffer  ,              nameOffset, nameLen, minimized, line, col);
              }

    public void handl    eStan              daloneEl   ementEn  d     ( 
                  final     char[] buffer,
               final    int   n      ameOffset, final int      nameLen,
              f    i   nal boo         lean minimize    d, fina        l int line, final int col) 
                      throws ParseEx     c    eption        {
            this.next.handleSta nd        alo    neElementEn d  (  buffer,  nam   eOffset, na meLen , mini     m       i  zed, line,     col)    ;
             }  

          

         public            void handleOpen          ElementStart    (
            final c    har[] bu    ffer,
            f     ina l int nameO     ffset, final     int nameLe  n,   
                f   inal            int l    i     n  e, final int   col      )
               throws P   a     rseException {
           this.next.handleOpenElementStart(  buffer, nameOffset, na     meLen, line, col);
          }

    p     u  b    lic v    oid  handleOpenEleme     ntEnd(
            fi      nal cha r[     ] buffer,
               fi   nal int nam    eOffset, final i     n      t nameLen,
              fi      nal int line, final   int co  l)
                 throws    Pa  rseExc  eption {
           this.n     ext     . handleOpenE    lementEnd(buffer, nam       eOffse      t, nameLen, line, col)  ;
    }



      public  void    handleAutoOpe   nEleme     ntStart(   
              final char[] buff             e  r   ,
              fin         a   l int na      meOffset, final       int  nameL   e      n,
                 final    in t line, f      inal int co  l)     
                                  thr        ows  ParseException {
          this.n ext.han               dleAutoOpenElem    en      tStart(buff    er, nameOffset, nameLe n, line, co       l); 
        }

    public                       void h andle  AutoOpenElementE  n         d(
                     fi nal char[] buffer,
            fina l    int nam    eO           ffset, final in  t nameL    en,
                        final i     nt line, final       int c    ol)
                             throws ParseException {
          this.   ne   xt.handleAutoO   p      enE lemen   tEnd(buffe     r, nameOffset, nam    eLen, li    ne , col);
           }


        
    publi   c void     handleCloseEle             mentStart(
               fin   al char[] b       u   ffer,
                    final int nameOffs    e      t, final int nameLen, 
                 fin a l int line, final in  t       co        l)
                    throws      ParseExc                   eption {
        this.next.hand     leCl  oseElement  S  tart(      buffer, nameOffset,   nameLen, line, c   ol);
           }     

    public    voi d     handleCl oseE   lem en  tE   nd(
                 fin            al cha  r[] bu     ffer,
                         final int na   meOffset,         fi   nal   int name Len,
            fi          nal   int li    ne, final int col)
                      throws P   arseE        xception    {
             this.next.handleClos     eElementEnd(buffe       r      , nameOffset, nameL         en, line, co  l);
        }

   
       
        publ  ic v    oid      handl        eAutoCloseElemen    tStart(
            final    char   [ ] b  uffer,
                   fi  nal int na     meOffset,   fi   nal int nameL en, 
              final  int line, final  int col)
                   throws     ParseEx              ception    {
        this.next.handleAutoCloseElementSt  art(buffer, nameO ffset, nameLen,         l       in  e, col);
       }

    publi     c v   oid handleAutoClose   ElementE    nd(
            final char     [] buffer,
                final int nameOf     fset, final int nam      e   Len,
            final    int line, final int     c   ol)
                      throw   s    ParseExcep  tion {
          th   is.next.handleAutoCloseEleme nt End(buffer, nameOffse  t, nameLen, line, col             );
            }
    

    
    public void   handleUnma     tchedClos  eElementStart    (
            final char[] buffe  r, 
              final    int nam eO         ffset, f   inal int  nam    eLen, 
            final int line,      final     int col)
             throws ParseException {
                             thi   s.next.handleUnmatchedCl   oseElementSt   art(buffer, nameOff     set, nameLen , line, col);
    }


    public        void handleUnmatchedCl oseE    lementEnd(    
              final char[] buffer    ,
              fina    l int nameOffset, final int         nameLen,
            final int l i ne,   final in   t col)
            throws ParseException {
        this.next.ha  ndleUnmatc   hedCloseElementEnd(buffer, nameOffset , nam       eLen, lin    e, col);
    }


    
    public        void handleAttribute(
                             final char[] buffer   ,
                  final int        nameOffset, final int nameLen,
               final int nameLine, fina           l i n   t nameCol,
               final i nt oper     at    orOffset, final int operatorLen,
            final int operatorL ine, final int operatorCol,
            final int valueContentOffset, final int valueCo   ntentLen,
            final     int   valueOuterOffset, final int valueOuterLen,
            final int valueLine, final int valueCol)
            t  hrows ParseException {
        this.next.handleAttribute(
                buffer,
                      na meOffset, nameLen, nameL  ine, nameCol,
                oper atorOffset, operatorLen, operatorLine, operatorCol,
                   valueContentO ffset, valueConte   ntLen,
                valueOuterOffset, valueOuterLen, valueLine, va    lueCol);
    }


    
    public void handleInnerWhiteSpace(
            final char[] buffer,
            final int offset, final int len,      
              final int line, final int col)
            throws ParseException {
        t   his.next.handleInnerWhiteSpace(buff  er, offset, len,     line, co     l);
    }



    public void handleProces        singInstruction(
            final char[] buffer,
            final int targetOff   set, final      int tar getLen,
            final int targetLine, final int targetCol,
            final int contentOffset, final in   t contentLen,
            final int contentL ine, final int contentCol,
            final int outerOffset, final int ou    terLen,
            final int li ne, final int col)
            throws ParseException {
        this.next.handleProcessingInstruction(
                  buffer,
                targetOffset, targetLen, targetLine, targetCol,
                contentOffset, contentLen, contentLine, contentCol,
                outerOffset, outerLen, line, col);
    }


}
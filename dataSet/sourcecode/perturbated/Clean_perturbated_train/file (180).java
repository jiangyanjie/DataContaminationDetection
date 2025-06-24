/*
      * Copyright     (c) 2007, 2   017, Oracl   e   and/or its af  fili      ates. All rights reserved  .
 * ORACLE PROPRIETARY/CONF  IDEN  T IAL. Use i   s su   bject to license term    s.
 */
/*
 * Copyright 2001-      2005 The Apache Software Foundati    on           .
 *
 * License   d under the A    pache License, Versi  on    2.0 (    the "Lice      nse"   );
              * y          o    u   may         no  t use thi   s file exc   ept in c        omp liance with the    Lic   ense.
 *    You may    obt   ain a copy    of the Li    cense at
 *
 *          http:// w      ww.apache.org/licenses/LICENSE-2.0
 *
 * Unles      s requ  ired by   ap    plicable law or   agreed     to i   n writing  ,      software
 * dist     rib  uted u    nder        the License is distribu   t   ed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF     ANY       KIND, either express or implied.
 * See the License for the specifi    c language governing permissions and
 * limitations u   nder the License.
 */

package com.sun.org.apache.xerces.internal.parsers;

import java.util.Locale;
import java.util.Stack;

import com.sun.org.apache.xerces.internal.dom.AttrImpl;
import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
import    com.sun.org.apache.xerces.internal.dom.DOMErrorImpl;
import com.sun.org.ap   ache.xerces.internal.dom.DOMMessageFormatter;
import com.sun.org.apache.xerces.internal.dom.DeferredDocumentImpl;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xerces.internal.dom.DocumentTypeI  mpl;
import com.sun.org.apache.xe     rces.internal.dom.ElementDefinitionImpl;
import com.sun.org.   apache.xerces.internal.dom.ElementImpl;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;  
imp  ort com.sun.org.apache.xerces.internal.dom.EntityImpl;
import com.sun.org.apache.xerces.internal.dom.EntityReferenceImpl;
import com.sun.org.apache.xerces.internal.dom.NodeImpl;
import com.sun.org.apache.xerces.internal.dom.NotationImpl;
import com.sun.org.apache.xerces.internal.dom   .PSVIAttrNSImpl;
   import com.sun.org.apache.xerces.internal.dom.PSVIDocumentImpl;
import com.sun.org.apache.xerces.internal.dom.PSVIElementNSImpl;
import com.sun.org.apach    e.xerces.internal.dom.TextImpl;
import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;
import com.sun.org.apache.xerces.internal.util.DOMErrorHandlerWrapper;
import com.sun.org.apache.xerces.internal.xni.Augmentations;
import com.sun.org.apache.xerces.internal.x   ni.NamespaceContext;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XM   LAttributes;
import com.sun.org.apache.xerces.internal.xni.XMLLocator;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.XMLString;
import com.sun.org.apache.xerces.internal.xni.XNIException ;
import com.sun.org.apache.xerces.internal.xni.parser.XMLParserConfiguration;
import com.sun.org.apache.xerces.internal.xs.AttributePSVI;
import com.sun.org.apache.xerces.internal.xs.ElementPSVI;
import com.sun.org.apache.xerces.internal.xs.XSTypeDefinit    ion;
import com.sun.org.apache.xerces.internal.utils.ObjectFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASectio n;
import org.w3c.dom.Com       ment;
import org.w3c.dom.DOMError;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element   ;
import org.w3c.dom.EntityReference;
import org.w3  c.dom.NamedNodeMap;
import o rg.w3c.dom.Node;
impor     t org.w3c.dom   .NodeL  ist; 
im   po     rt org.w3c.dom.Proce                 ssingInstruction;
impo     rt org.w3c.dom.Text;
import org.w3c.dom.ls.LSParserFilter;
imp      ort      org.w3c.dom.traversal.NodeFilter;
impo  rt org.xml.sax.SAXException;
  
/**
 * Th    is is the base class o      f all D OM parsers. It implem   e    nts the XNI
 * callback methods   to create the DOM tree. After a    successful p    arse    of
 * a   n XML document, the   DOM Docume  nt object can be queried using the
     *      <code>getDocum    ent</co     de    >    method. The actual pipel     ine is d   efine    d   in
  * parser configuration.
 *
 *      @author Arnaud Le Hors   , I BM
 *   @author Andy Cl   ark    , IBM
       * @autho  r E   lena  Litani, IBM
 *
 * @version $Id: Abstrac   tDOMParser.      java,v    1.10       2010-11-01 04:40:      09 joehw Exp $
 */
public class AbstractDOMParser extends Abst   ractXM      LDocumentParser      {

    //
     /  / Cons   tants
    //

    // feature ids

               /** Feature id: namespace. */
          protecte    d static fi    nal St rin     g NAMESPACES =   
      Constants.SAX_FEATURE_ PRE    FIX+Con      stant s.NAM   ESPACES_FEATURE;   

                   /** Featu   re id: create entity ref nodes. */
    protected static   final St  ring C     REATE_ENTITY_         REF_NODES     =
      Constants.XERCE     S  _FEATURE_PREFIX + Constants.CREATE_ENTITY_RE  F_NODES_FEATURE;

    /** Feat u  re    id: inc    lude comments. */
    pro   tected static fin       al String INCLUDE_COMMENTS_FEATURE =
    Constants.XERCES_FEATURE_PREFIX +   Constants.INCLUDE_C            OMMENTS_FEATURE;

    /**   Feature id: create cdata nodes. */
    protected static fina        l String CREAT     E_C   DATA_NODES_FEATUR    E =
    Constants.XERCES_FEATURE_PREFIX + Constants.CR EATE_CDATA_NODES_F    EATURE;

      /** Feature id: in  clude   ignorable white   spac   e. */
    protected     static final String INCLUDE_IGNORABLE_WHITES    PA       CE =
    Constants.XERCES_FE ATU    R      E_PRE  FI  X +    Co  nstants.INCL  UDE_IGN         ORABLE_W   HITESPACE;

        /** F  eature id: defer     node expansion. */
    prot  ected static fi    nal String DEFER_N    OD    E    _EXPANSION       = 
        Constants.XERCES_  FE   ATURE_PREFIX  + Constants.D     EFER_NODE_EX       PANSION_FEA  TURE;


       /** Recognized features. */
         priv       ate static final String[] R  ECOGNIZED_FEA          TURES = {
                    NAMESPACE  S,         
        CREATE_ENTITY_REF         _NODE       S,     
        INCLUD     E_COMMENTS_FEATURE,
        CREAT  E_CDA      TA_NODES_FEAT   URE,
        INCLUDE_IGNORABLE   _WHITESPACE,
        DEFE  R_NODE_E     XPANSION
    };

      //       property     i     ds

    /*  * Propert      y id:     document class name. */
      prot     ected static final    String   DOCUMEN T_CLASS_NAME =
    Constants   .XERCES_PROPERTY_PREFIX + Const  ants.DOCUMEN   T   _CLASS_NAME_PROPER   TY  ;

    protected static final String         CURRENT_E      LEMENT_NODE=  
    Constants.XERCES_PROPERTY_PREFIX + Constants.CURRE    N   T_ELEMEN    T_NODE_PROPERTY;

      // prote cted static    final St ring    GRAMMAR_POOL =
     // Constants.XERCES     _PROPERTY   _PR   EF   IX   + Cons      tants.XMLGRAM    MAR_P   OOL_PR      OPER    TY     ;

    /** R  ecog              nized prope   rties.     */
    private static         final Str  ing[]    RECOGNIZED_PROPERTIES =    {
           D         OCUM   ENT_CL   ASS_      NAME    ,
         CURRENT_ELEMENT   _ NODE,
    };

    // other

    /               ** Defau  lt    d  ocument class  na me. */
             pr  otected static   final  String D    EFAULT_DO    CUMEN    T_   CLASS_NAM      E   =
     "com.sun.org.    apache.xerc es.int  ernal.dom.Doc  umentImpl     ";

        protect  ed st     atic final String CORE  _DOCUM   ENT_CLASS_NAME =
       "    com.sun.org.apache .xerces.internal.dom.Co  reD     ocumentImpl";

    prote cted static      final St     ring PSVI_DOCU    ME  NT_CL     ASS_NAME  =
    "    com.sun.    org .apache.  xerces.internal.            dom   .PSVIDo  cume   ntImpl"          ;

    /**
     * If the  user stops the process,    this exception wil   l be     th rown.
     */
    static fi   nal  class Abort exten    ds R untimeExc    eption {
          private static f inal    lon g serialVersio         nU     ID = 168     7848994976   808490L;
                       sta    tic fin al Abo   rt  INSTANCE = new Abort(); 
              private  Abort() {}
           public Thro   wable fil   lInStackTrace( ) {
                return this;
        }
    }  

    // debu   gg i    ng

    privat       e static final boole    an DEBUG_EVENTS = f  a   lse;
    private  st     atic f    ina   l boo  lean DEBUG_BAS    EURI =   false;

    //
    // Data
      // 

          /            ** DOM L3       error h        andler *    /
    protected DOMError       HandlerWrapper f   Error                Handler = nu  ll;

              /*    * True     if inside DTD. *    /
      pro t    ecte  d boolea   n       fInDTD;
  
       // features

    /**       Create entit     y reference nod  es. */
    protected b   oolean fCreateEntityRefNo    des;

    /** Inclu d   e ignorab    le whites  pace. */
      protec t  ed bo o   le    an f       Incl u    d      eIgnorab        leWhitespace;

       /** Includ     e    Com ments. */  
    protected boole     an fIncludeComments;
        
             /** Create cdata node    s. */
           protected        boolean fCreateCDATANodes;

    // do       m informatio n

    /** T   he document.  * /
             protected Docume  nt fD   o cument;

           /    ** The d    efault Xerces      document impleme  ntati     on,    if used. */
    p    rotected Co   reDo    cume      ntImp  l f        Docum    entImpl;

    /** Wh eth    er to store PSVI in   form      ation in DOM tr   ee. */
         prot ected boolean fStoreP SVI;

    /** The document class name to use. */
             protec    t    ed Str ing  fDocumentClassName;

    /*   * The document type     node. */
      protected DocumentType     fDocumentTyp   e      ;

    /** Current n   ode. *        /
    protected Node fC      ur  rentNode;
    pro                       tected CDATASec            tion fCurr    entCDATASec     tio    n;
                  pr ot ected Enti     tyIm    p  l   fCurrentEnt  ityDecl;
    protec  t      ed i    nt fDeferr   edEntityDecl;

    /** Charact       er b   uffer */
    prote   cted fi n al Strin  gBuilder f    StringBuilder =         new StringBu ilder (50);    

    // i            nternal subset

         /** Internal sub se      t buffer. */
    prot        ected  St    rin gBuil der fInternalSubset;   

          //         d   eferred expansion data

          protected boolean                         fD  eferNodeEx    pa n sion      ;
    pro  tected bo   olean                   fNam  espaceAware;     
     protected DeferredDocumentImpl fDeferredDocum    e  ntImpl;
    p    rotected int                        fD      ocumentIndex;
    protec   ted in   t                    fDocumentTypeIndex;    
           protected int                       fCur    rentNodeInde x;
    protected           int                             fCu       rrentCDATASectionInde  x;

           // state

    /** Tr    ue if   ins    ide DTD e   xte rnal su  bset. */
    protected boolean fInDTDExterna   l Subset;

      /** Root element  no    de. */
       pro       tected No    de fRoot;

        /*  * True if inside CDATA    section. */
       protected   boole   an fInCDATASection;

    /** Tru     e  if saw the first chun   k of characters*  /
    prot    e       cted boolean fFirstChunk  = false;


    /** LSParserFilter: speci   fies t  hat element   with give       n QNA   ME and a     ll it s chi     ldren
      * must be      rejected */
     protected bool  e     an fF ilt erRejec  t = false;

      // data

         /** Base uri s   tack*/
      p   ro         te     cted final Stack fBase  U    RIStack =   new St   ack  ();

          /** LS             ParserFilter: track s the            element dep  th within a rejected subtree    . *            /
    protected in t fRejectedElementDepth = 0;

     /** LSPa   rserFil   ter: store        dept              h of s   kippe     d     elem  e       nts *  /
    protected S     tack fSkippedElem     Stack = null;   

     /** LSParser F   ilter: true if insid        e entity reference     *  /
    pr    otected boolean fInEntityRe f = false;

    /** Attribute QName. */
    private fina  l QName fAttrQName =     new QN   ame();

          /** Document locat    or.       */
           pri               vate XMLLo    cator fLocator;

       // handlers  
        
    prot    ected LSP   arserFilter   f DOMFil   ter =  n  ull;

    //
         // Constr      uctors
    //

    /** Default   constructor. *   /
                 protected Abst    ractDOMPar ser (XMLParserConfigurati  on config) {

        s     uper (config);


        // add re      cogniz        ed features
                       fConfiguration          .a    d     dRe    cognizedFeat  ures (RE             COGN           IZED_      FEATURES     );

        // s      et default values      
        fConfigurat     ion                    .     set   Feat          ure (C RE     ATE_ENTITY_REF _NODES, true)    ;
                fCon      f  i  gu  ration.s etFeature (INCLUDE_IGNORABLE_WHITESPAC     E,        true);
            fConfigu      r a tion   .setFea    ture (  DEFER_      N     ODE_EX    PAN   SI     O N,  tru   e);
                   fConfigu  ration.setFeature (INCLUDE_CO   MMENTS_FEATURE, true)    ;
             fConfiguration.set         Feature (CREATE_CDATA_NODES_FEATURE, t  rue   );

        // ad    d     rec  ogn ize    d pr    operties
        fCon  fi   g   ur   ation.ad    dR  e  cognizedProper      ties ( RECOG          NIZED_        PROPERTIE     S)      ;
 
                     // set      default val     ues
         fConf  igurat   ion.s       etP    roperty     (DOCU    MENT_CLASS_NAME,
              DEFAULT_DOCUM    EN T_CLASS_NA           ME);
   
             } /   /   <init>(XMLParse   rConf  iguration)

              /**
         * T h  is method retrieves the n              ame of current docume  nt class.
                  */
    protected String    g  et     DocumentClassN    a              me () {
                            retur   n  fDocumentCla ssNam e;
    }

       /**
        * This method al     lo   ws the programmer to decide which document
     * f  a   ctory            to us     e    when con   struc   ting   t       he DOM t    ree. However, doi   n   g
     * so wi   ll    los    e the function       a       lit   y o      f the defa            ult factory.         Also,   
         *        a     d ocume  nt cla   ss othe   r than the default wil  l   l   ose       t   he  abil         ity
     *               to                defer nod              e     expansion    o     n the DOM tre    e pr   oduced.  
           *
     * @p            ar   am    docu     mentClassN ame The fully qua    l      ified cl     ass nam    e    of t  he
     *                                   document fact  ory to use w     hen constr     uc t      in   g
     *                                      the   DOM t   ree.
      *
       * @  see # getDocumentClassName
                * @see #DEFAULT      _         DOCUM         ENT_CLASS_N  AME
             */
       prot  ected voi  d set DocumentClassNam  e (String     do       cumen  tClassName)       {

                          // normali ze clas       s name
          if (do   cumentClassNa           me ==   n      ull) {
                            documen       tCla   ssName = DE   F   AUL    T_  DOCUMENT_CLASS   _NAME;
        }   

        if (!documentCla    ss      Name.equal  s(     DEFAUL   T_D         OCU   M      ENT_CLAS  S_NAME) &&
                !d ocumentClassNa  me.equals(PSVI_DOCUMENT_CLASS    _NAME  )) {
            / / v   e   rify that this class exist    s and     is of the r     ight t          ype
                                          try {
                        Class _class       = Object   Fa        cto             ry.find Pr            ovider Cla  ss (documen    tCla        s   sNa     me, true);
                                           //if (!_ class.isAssi    gn  ableFrom(     Doc    u  ment .class)) {
                        if (!Do cumen   t.cla  ss.isAss ignableFr om (_clas   s)) { 
                                 throw new      Ill   egalArg        um entExcep    tion (
                             DOMMe  ssageFo rm  atter.for    m        atMe         ssage(      
                                                  DO    M  Mess     age Formatter.DO  M      _DOMA         IN,    
                          "  In   validDocumen       tClas   sN     ame", new Object [      ]       {docu     ment    ClassN  ame}));
                   }
               }
               catch (ClassN          otF oundEx   ce    pt    ion   e)    {
                throw new Ill   eg        alArgum entExc      eption (
                         DOMMessageFormat     ter.formatMe     ssage(
                         DO  MMessageForma  tter.DO       M_DOMA        IN,
                                              "MissingDocumentClassName ", ne      w Object [] {documentCl  assName}  ));
                }
        }

                          // set d ocument          class           name
        f         Docu mentC      lassNa      me = docu   men    tCla ssName;
                    if (!do      cumentC    lassName.equa   ls (DEFAULT      _DOCUMENT   _CLASS_NAM        E)) {
                    fDeferNodeEx pansion =       fa   lse;
            }

            }     // s        etDocumentCl    assName(      St  ring)

    // 
    //         Public m      et       hods
             //

    /** Re      turns   th   e DOM doc    u              ment object. */
    public Document    g etDocument () {
          retur     n fDocument;
             } //   getDoc  ume  nt(    ):Docume             nt

    /**
             * Drops all r     eferenc   es to the      l  ast      D    OM     which        was buil   t b     y thi      s     parse r.
     */
    public final void dropDocu   men     tRefer        enc    es(    ) { 
        fDocum ent    = nul l;
          fDocumentImpl    =     nu     ll;
                 fDeferredDocume  n         tImpl = null;
        f      Doc       u     me    n         tType         = null     ;
               fCurrentNode = null;
                     fCurr  e         ntC     DATASect  ion   =  null   ;
           fCurrentEnt    i tyDecl = nul           l;
        fR   o      ot = null      ;
      } //      dropDocumentReferences   ()

    //
    /     /   XM   LDocum   entPars   er metho ds
    //

       /*     *
       *          Re    sets the parser state   .
     *
                                 * @thro      ws         SAXExc eption    Thrown    on i   niti    a  liz  at            ion  error.
     */
      public void     reset ()   throws     XN IExc        ept  ion {
        super.reset ();


        // get featur  e    state
           fCreat   eE   ntity  RefN     o    des =     
              fConfigurat   i    on. getFeatur       e (CREATE     _ENTI  TY_REF_      NO   DES);

                            fIn            cl  udeIgno   rab   leWhi       tespace =
            fConfigur     atio   n.getFe     ature (I NC    LUDE_IGN  ORABLE_WHITESPACE);     

        fDefer NodeE  xpansion   =
        fCon    fi guration             .getF           eatur  e (DE FER_NOD   E_EXPANSION)   ;

         fNamespa         ceAware = fConfiguration.getFeat    ure (NAM   ESPACES  );

                 fInclude         Comments = fConfigurati             on.ge   t  Feat             u  re (INCLUDE_COM     MENTS_FEATURE);

          fCrea      t    eCDATANodes = fConf       iguration.getFeature (CR    EATE_CDA TA_NODES_FEATUR     E    );

        // get p roper          ty
                    setD     ocumentCl     assName ((String )
                       fConfigur           ation.getProperty     (DOCUMENT_CLASS_NAME)     );

        /  /         reset dom information
          fDo    cument                = null                         ;
        fDocumentI   m     p           l =  n   ull;
           fStor           ePSVI = fa l     se  ;
             fDocumentT  ype = nul  l;  
         fD    ocumentTy    p  eI     ndex = -    1;
                   fDe f erredDocumentImpl = n   ull;    
           fCurre  ntNode = null;       

                 // reset stri   ng buff          e   r
                fStringBu   il     d      e    r.se       tLength (0);

                 // rese  t st   at     e information
        fRoot = n  u    ll;    
                 fInDTD = false ;
                       fInDT             DE     xternalS        ubse   t = false; 
                   fInCDATASecti        o  n = false;
                      f  Fi       rstChu   nk =   false    ;
        fCurrentCDATASect         io      n =      nul             l;
                         fCurrentCDATASect      i onIndex       = -1;

            fBaseURIS       tack.r          emoveAl          lEle  ments   ();


            } //           r    eset()
   
    /**
     * Set the loc    ale to use for m  essages.  
       *
               * @para     m     locale Th   e l   ocale object        to use for lo  cal         ization of messages.
             *
     */
         publ  i    c void            setLocale (Loca   le locale) {
                    fCo      nfi           gurati   o n.se   tLocale (locale);

     } // setLoca  le(Locale)

     //
     // XMLDocumentHandle               r methods
    //

           /**
     * This method  notifies t he       start of a   ge            n   eral entity    .
                                   * <p>   
       *   <strong>No   t e:</            s trong       > This metho          d is n o        t cal   led for   entity          r       eferen        ces
                         * appearing as part of attr ibu   t      e va   lu  es.
     *        
     * @par            am name        Th      e name of the general entity.
                         * @par  am identifier The         resource id  entifier.
        *  @p    a   ra       m enc   oding The auto-det    ected        IANA enc  oding    name           of      the entity
     *                      stream. This val    u     e will be null i    n those situ      a   ti            o     ns
                *                                      w here        th   e entity       encoding is not auto-d     etecte         d (e.g.
        *                         intern  a        l enti             ties or       a              docume    nt ent     ity that is
        *                   parsed f   rom a j   ava.i o.Reader).
      * @para            m augs           Addi    tion   al info   rma       tion t     hat                may   includ e infoset aug            mentations
                           *
     * @ex   ce    pt  i o   n XNIExc  eptio  n Thrown by han   dler to         signal an e   rror .
         *     /
       publi    c vo  id star  tGeneralE     nt           ity (Stri      n        g name,
    XMLResour          ceI dentifier identifier,
       String encod in      g, A    ugmen                   tat          i   on   s augs     )
     throws XNIE x ce            pt  ion {   
                               if (DEB     UG_EVEN TS) {
                   S         yste             m.  out.pri  n     tln (  "  ==>s     tartGener  a  l                Entity ("+name+" )  ");
                           if             (   D    EB    UG_BASEURI) {
                                 Syst  e   m.out.pr   int  ln ("   expandedSy                   stemId( **b     aseURI):            "+     identifier.get Ex      pandedS  y     stemId (  ));
                                     Sy    stem.out.prin        tln (         "   baseURI:"+ id  entifier.ge  tBaseSystemId ());      
                         }
           }   

           // Always cr    eate      entity r    eference nodes t     o be ab  le to    recreate
        // entity      as a    part    o   f doctype
                if (!fDefer   NodeExpansi  on)       {
            if ( fFilte             rReject) {
                                                      r  e            turn    ;
            }
                     setChar  ac       te  r  Data (true);
                                         EntityRefere    nce   er =          fDocum   e n    t.crea teEn  tityR  ef    erence (n  a    me);     
                                       if        (fD        ocumentImpl != null) {
                  /  / REVISIT:   bas eUR        I/actu      a    lEnc         oding  
                      /  /         rem  ove dep    endency on our      i   mp     leme ntatio          n whe  n D    OM L3 is     RE   C      
                  / /

                                  EntityReferenceImpl   erImpl    =(EntityRefe  rence  I             mp     l) er;

                                          // set base uri
                   erImpl          .setB     as         eURI    (id      entifi    er.get   Exp          ande  dSystem         Id    ());
                          if (f    Docu                mentType      != null) { 
                       /         / set actu            al encoding
                                Name       d NodeMap ent        it     ies   = fD  ocumen      tType.getEnt    ities ();
                                fCurr  entE   n tityD    ecl =     (   EntityIm   pl       ) e       ntities.ge tNa   me  dIt          em    (name);
                           if (fCurrentE     n tit y      De        cl != null) {
                                  fCurr  en tEnti tyDecl.setInp  utEn     coding    (encoding);           
                                            }

                       }
                      /    / w           e  don't    n   eed s  ynchronization now,         bec  au    se enti      ty r e f  w     ill be
                                                 // expan       ded anyw     ay  . Sync  h only needed when us            er creates entityRef n   od  e
                     erImpl.needsSy    ncChildren ( false)  ;
                       }
              fInEntity   R      ef = tru    e;
            fCurren         t  Node.ap pendCh  ild (er);  
            fCurrentNode = er   ;
                       } 
                else {

                                  i   nt e   r =
              fDe   ferr    ed     DocumentImpl    .createDeferredE     ntityRef      erence (name,    identifi    e    r.getExp  andedSy    stemI         d   ());
                       i   f        (fDocum  entTypeInd ex !          = -1) {  
                  // find     co     rrespo  nd   in   g Entit        y         decl
                int no      de = f    DeferredDocument     Im       pl.getLa      stChild (fDoc           um       entTypeIndex,     fals        e);
                     while (no  de != -1) {
                                     shor     t nodeT         ype     = fDefe  rre    dDocument      Impl.ge     tNodeTy  pe       (no d   e, fal  se    )     ;
                                      i              f              (no   deTyp      e =    = Node.  E  N   TITY_NODE) {
                                String   no         de   N ame =    
                                                                   fDeferredDoc u      mentImpl.getN      odeN     a me (node,     false);
                             if (nodeNa me.equals      (nam      e)     )         {
                                                  fDe ferredEntityD    ecl = nod      e;
                                                                fDeferr    e  dDocum  en           tImpl.se      t         In  putEncoding     ( n           ode , enco      ding);
                                 brea     k;   
                                         }
                                       }
                                          no     de =      fDefe    rre      dDo       cu   ment  Imp     l  .g           etRealPrevSibli  ng       (         n      o   d    e,         false);
                          }
                                 }
               fDeferred   DocumentImpl    .ap   pendChi   ld (fCurrentNodeInde     x,     er   );
                              f  CurrentNo     deIndex = er   ;
                                  }

       } //   st  ar   tGenera   lEnti ty(S tring,X       MLRe      sourceIdentifier,      Augmenta        tion     s)     

               /**
                  *       Notifie   s of the pr   e     s      e           nce o       f a TextDecl l    ine   in an enti  ty. If   pr ese     nt,
                * this method will               be calle         d immed       iately      following t he star tEntity cal   l.
           *       <p>
       * <          strong>Note       :< /strong>        This  meth     od w  ill never be call   e    d for t    he
             * document        ent   ity; it       is on   ly called for     external   gener         al enti   tie        s
            * re                fer    en  ce d i     n document   conte   nt.
            * <p   >
     * <      st rong>No       te:</str  ong> This me  thod is     n ot called f       or en  tity reference         s
     * appearing as part of attribute val    ues.           
        *
        * @param version  T     he       XML v   ersio n, or null if n    ot s        pecified.
          *       @par am encoding   The    IANA      enc  od    in    g nam   e of the entity.
     * @param au                   gs          Additional i   n       formation                that may include infos  et      a        ugme       ntation    s
     *
     * @throws XNIExcept  io   n       Thrown by handler to signal an erro   r         .
          *  /   
    pu   blic void   textD ecl   (String version,    S       tri     ng   enc od   ing,              Aug    mentatio   n  s     au  g  s     )    th        rows XNIException {
                  if   (fInDTD){
              return;
         }
         if (!fDefe rNodeExpan    sion)  {
            if (fCurrentEn      tityDecl != nu      ll && !f   Filt                       erReject) {
                               fC     urrentE      nt  ityDecl.setXmlEncod   i    n  g       (e    ncoding);
                       if (ve   rsion != nu    l   l)
                           fCu      rre  ntEnti        tyDec    l.s                         etXmlV  ersion (version);
            }            
          }
           else {
                       if   (fDefer   r                          edE   n     tit   yDecl !=-1)   {
                              fD   efer   redDocum     entImpl.se   tEntityIn          fo            (fDefer  redEntityDecl, ve   r  sion, en          codin    g)   ;
              }
                                      }
                   } //  textD  ecl(String,St      ri        ng) 

     /  **
       * A  c   omment  .
            *
        * @pa   ram        text   The text    in th      e comm              e        nt.     
     * @   param augs                A        dditional information that    may include inf  oset au      gme      ntat i     ons
      *
         * @throws XNIEx  ception      Thrown by application to signal an error       .
     *  /      
    public vo   id co      mment (XMLString text,  Augmentatio ns augs) thro             ws XNI     Excep  t i       on {      
                    if (fInDTD) {   
                if   (  fInternalS ubse       t !=             nul      l &      & !fInDTDE  xternalS    ubset)        {
                         fIn t   ernalSubset     .append    ("<!--");
                          if (text.length       >                            0)        {
                                fI          nternalS               ubset  .append (text.ch, text      .offset, te x         t.l   engt h);
                      }    
                                       fInternal    Subse   t.a    ppe  nd ("-->");
                       }                       
               r     e   t urn;
                  }
                  i  f (!  fIn    clude              Com men                       ts      || fFilt      erRe          j e    ct)      {
                   retu   rn;  
        }   
        if (  !    fD   efer       Node    Expa   nsion)      {
                         Comment c   omment    = f     Do   cument.cr          e   at eComment (t     ex    t              .toString ());

                  se  tCharact       erData    (false   );
                    fCur  rentN ode.a    ppen         d  Child (comment);
            if    (fDO       MF   ilter !=        nu     ll && !fIn       Ent            ityRef &   &
               (fDOMFilter.getWha  tToS  how ( ) & Nod eFilter.S    HOW_COMME  NT)!        = 0)    {
                     s  hor      t code = fDO          MFilter.acceptNode (co     mment);
                                  swi  tch (     code) {
                                           case LSParserFil            ter.F        IL     TER  _IN        TER R     UP    T:{
                                            throw        Abo     rt.IN   STANCE      ;
                            }
                            case    LSParse   rFilter.FILTER_REJE   CT:{
                             // REVISIT    : th   e constant FILTER_REJEC   T       shoul   d     be c      hang           ed w     hen   ne                      w
                                          //   DOM LS     spec     s  g    e  ts publ   ish               ed

                                               //  f         all throu        g  h to SKIP   s      ince c            omment has no    children.
                      }
                    c ase LSParse  r    Filter.FILTER_SKIP     : {        
                                             // RE   V ISIT:       the constant FILTER_S   KIP sh    ou        ld       be changed when new
                                     // DOM LS specs g   ets pu   bli  shed    
                                                                             fCurr entNode.   r    em   oveC      hild (   comment         );
                                        // mak    e     sur     e we d     on't loose ch       ars i  f next event       is       characters ()
                                    fFirstChun  k =    tru   e;
                                            re     t         u    rn;
                                       }

                                                  default: {
                              // accept    node
                             }
                                           }
                   }

        }
                el   se {
             int comment =
                 fDeferre  dDocum    en      tImp  l        .createD           eferredComment (     text.toSt   ring ());
                 fDeferredD o       c      umentI          mpl.ap  pend   C  hil   d     (fCu  rr    ent   NodeIndex, c o     mm   e   n             t);
        }

    } // comment(XMLStri      ng)

    /**
     * A   proce    ss    ing i       nst ruction. Processing ins             tru   ctions co           nsi st of a
                       * target name and, option    ally, text d   a  ta.   T  he    data is only m  eani  ngfu                      l
       * to the appl                           ica      ti   on.     
              * <p>
     * Typi       c   a        lly, a process  i  ng inst    ruction's   data      will conta    in a series
     * o           f pseud    o-att    ribute      s. These pse udo-attributes      follow t   h      e f  orm of
             * elem    en     t          attributes    but     are <stro   ng>not</strong> parsed          or pr esent    ed
             *       to the  a    ppli      cation as anythin       g  other tha  n tex  t.   The application i s
     *                  r  es    ponsible fo r p ars           ing the data.
     *
       * @param target       Th e targe         t.
         * @   pa      ra     m data       The     dat    a or     null if non      e sp  e  cified.
     *  @pa    ram augs          Addition                al inform    ation     t ha            t may incl   ude i  nfo   set aug    mentations
     *
        * @throws XN   IE      xcepti   o  n Thrown             by hand            ler  to si   gnal an error    .
                      */
    public voi            d   proce   ssingInst   ruction    (Str   in     g     targe  t,         XMLString    dat  a, Augment     at            io  ns augs)
    throws XNIExcept         i           on {

          if   (fInDTD   ) {
                 if    (fInt    ern   alS  ubset != null        && !fInDTDExternalSubset) {
                fInt         ernalSubs e  t.app  end ("<? ");
                            f           Int     erna lSubse  t        .append (   ta    rge t);
                        if   (data.le           ngth > 0) {   
                                           fInternalSubset.  append (' ').ap     pend   (dat  a.c   h    , data.offset, data.length);
                    }
                      fInternalSubset.app en   d ("?>")        ;
                       }  
                        return;
        }      

             i  f (D    EBUG_       EV    ENTS) {     
                     Syst em.o      ut.println ("==>processingI n  struction (   "           +t    arget+")");
           }  
             if (!fDef   erNod     eExpansion)       {
                                               if (   fFilterR   eject) {
                   ret       urn  ;
                          }
               ProcessingInstruction pi =
             f       Document.create Pro         cessing     I               nstruction (target,   data.toSt   ring ());


                  setChar  acte rD  ata (fa   l se);
                          fCurrent   Node.appen   dChil   d (      pi);
               if    (fDO MF  ilt   er !      =nu    ll && !f   InEntityRef &&
                     (fDO     MFilter.g  etW      hatTo     Sho w   ()                    &  N    odeFilte       r  .SHOW    _P    ROCE     SSI  NG_INSTRUCTION)   !     = 0) {
                    short        cod   e =        fDOMFi    lte        r.acce                        ptNo de (pi);
                             switch (code) {
                      case        LS       Pars    e                rFi        lter.    FI  LTER  _INTERRUPT:{
                                                th    row A bort.INSTA N CE;     
                                 }
                                               c    ase LSPa             rserFilte r.FILTER_R    EJECT:{
                                            // fa                       ll through to SKIP     sinc   e PI has       no chi    l  dren.            
                                           }
                         case L     SParserFilt     e      r.FILT     ER_SK   IP: {
                                      fCurrentNode.      removeChild   (pi);
                                                 / /    fFir     s  tChunk must be set to tru      e         so    that data     
                             /   /               wo n   't be lost in t          h      e       ca    se    where t he chil   d     bef   or  e PI i         s
                           // a text n    ode and th e next    ev          e     nt   i s ch aracte    rs   .
                                   fFi     rstChu   nk = true;
                                           return;
                                          }
                                d       efau    lt: {
                          }
                         }
                }
                }
          e ls  e {
                        int p  i    = fDef  err   edDocumentIm  pl.
                                                  cre    a     teDeferredProce       ss  ingIn  s   t    ruction (target, data.toS   tring                     ()    );
            fDefe  r  redDoc       umentImpl      .a        ppendChild  (f  C  urrentNodeIndex, pi);
             }  

            }   //          pr   oc             e      ssingInstructi    on(     String,XM L   String)

       /**
               *                      The      st   ar   t                of t h       e document.   
            *
     * @           p     aram locator T  h   e system identifie   r o   f    the e n            tity    if th e enti          ty
       *                            is exte rna l    ,         nul             l o       therwis  e.
           * @param en                     c    oding The auto-d     e        te c      ted     IANA encodi      ng    name of        th e   e    n tity
         *                                                     stream.     This value will be null in those si   tuations
     *                 where    the entity en  codi      ng is not     auto   -detected (e.g.    
     *                                     internal ent   ities or a    document en    tit   y that  i          s                
                           *                            parsed f           rom a java.i   o.     Reader)      .
                 *    @   para     m    nam     espaceCont e   xt
     *                     The namespace context in    eff ect at   the
     *                          st     art o  f      t    h     is d        ocument.    
     *                                       T  his    object       re    p               r  e     s  en       ts          the current   c  ontext.
           *                           Implement   ors of   t          his cla ss      ar        e     re  sponsible
       *                        fo     r copyi  ng t  he         n     ames p ace bind in gs from the
           *                        the curren         t co     nt    ext (and       its pa  rent  cont    exts      )
          *                                 if  that infor       mati on is imp  ortant.
                                           *     @pa    ram augs        Ad     di   tio        nal information that may        i                   nc     lude    infoset     augm  entati ons
     *
                      *      @th   rows XN I      Excepti         on   T hrow   n by handler t  o signal an error.
           */
      public void     s  tartDocument (X ML     Lo      cator           locato  r, String encod ing,
    Namesp     ac     eConte  xt names  pa   ceConte     xt, Au        gmen  t    ati    ons augs)
                  throws XNIEx           ception {

        fLocator       =    locato     r;
             if  (           !fD  efe  rNodeExpansion) {
               if       (fDocumentClassName.equals         (DEFAULT_DO        CUMENT    _CLASS_NAME   )) {
                                               fDocu  m ent           =    new DocumentImp     l ()   ;
                            fD  ocu       me ntIm    pl =         (Core   DocumentI    m   pl)fD  o       cu       m  ent;
                         //  R EVISIT: wh   en     D   O     M Level   3   is              RE   C   rely on Document.sup       port
                                            //               instead of       specif  ic cl      ass  
                            // se t   DOM error        chec            king   o    ff
                        fDoc   umentI mp    l.s    e   tStric   tErrorC   heckin     g      (f     alse   );
                                          //    set actu  al encoding     
                        fDo  cumentImpl                   .setInputEnc     oding     (enc  o  di         ng)  ; 
                               // set docume         nt U  RI       
                   fDoc   umentIm       pl.s     e  t    Do       c  um    entURI (lo  cator.ge  tExp  and             e   dSystemId      ());
                       }
                     else if (fD       ocume          ntClas     sName.equals (PSV   I_D        OCUMENT_C      LASS              _NAM E)) {
                               fDocu      m  ent = ne   w PSVID   ocum      e       ntI   mpl();
                    fDoc     ume  ntImpl =     (Cor eDocu       men    tImpl)fDocum     ent;
                              fStoreP    SVI = tr         ue;
                          /      / R     EVISIT: w    hen DOM   L               evel 3   is      R    EC r     e   ly on      Docu ment.     support
                 //                         inst   ea   d        o       f specific         c        lass
                        // set       DO         M err     or  checking off
                        fDo    cu          men   tImpl.set         Strict      ErrorCheckin                 g        (  fal s   e     ) ;             
                     // set ac  tual encodi    ng       
                                                             f       DocumentIm       pl. s      etInput  Encod  in  g (e    n  codin           g  );
                   // set  doc    u       m     e  ntU    RI
                              fDocume    ntImpl.    setDocum  ent   URI (loc         ator.     ge tExpa          n     dedSy     st   emId ())                  ;
                   }     
                  else {
                          // use specified document cla   s  s   
                                     try {
                       Class do       cumentClass =       ObjectF actory  .findPr oviderCl     a          s     s (fDocumentClassName, tru  e);
                                    fDocume         nt =     (Document)doc        u                men         tCla                      ss.n              ewInstance ();

                                               // if subclas   s of our own        cla       ss   t        hat'        s cool       too
                                       Cla    ss        d   e   fa     ultDocClass =
                                  Obj  e     ctFactor   y.findP   roviderC  l  a      ss   (CORE_DOCUME      NT_CLASS_          NA  ME, true  );
                         if (   defa  ult      Doc     Class.isA   ss  ig na  bleFrom (doc      umentClass)) {
                                         fDocumentImpl      =        (CoreD  o   cu   me  n    tImpl      )f  Docu  me    n      t;

                                           Cl ass    psviDocCl          ass   =  O           bject     Fact         ory     .findP   roviderClass (PSV    I_  DOCUM    ENT_CLASS_N     AME, true       );
                              if (psv   iDo   cClass   .is          Ass            i      gnableFrom (doc      umentClass)) {
                                    f  S     t        ore       PSVI      = true;
                                  }
      
                                  // R  EVI    SI             T:       when      DO      M    L         evel 3     is RE      C rely    on
                                                         /  /                            Do           cum  en    t.sup                port in         stead o     f        specifi   c c        las     s
                                     // set DOM err    or ch ecking of         f       
                            fDocumentImpl.setStri  c   tErrorChecki ng (false);
                                        // s          et actual en          c      o   ding
                             f   Do  cumentImp   l   .setInpu tEnco    ding (e    ncodi   ng)    ;
                                             // set        documentURI
                                    if              (locator   !=   null) {
                                            f     Documen                tImpl.setDoc    umentURI (locat            o r.get Expa   ndedS   ystemId ());
                                                  }
                     }
                       }
                            c       atch (            Cla      ssN    otFound  Exception   e) {
                                                        // won't ha      pp     en           we already checked that   earl  ier
                                 }
                                                       c    atch  (Exceptio                 n e) {
                                           thro   w n ew Runt ime      Exc    eption (
                                                DOMMessageFormat    te  r.for matMessage(
                               DOMMes   sageForma  tter.DOM_  DO  MAIN,
                                  "CannotCreate    DocumentC   l   ass",
                        new Obj e             ct         [] {f        D  oc         ument     Clas   sNam     e}));
                              }
                         }
                         fC     ur  re      ntNode = fDocume   nt;     
                                   }
               els    e         {
                          fDefer            redDoc umentImpl = new De   ferr   ed DocumentImpl (f     Namesp  ac  eA               wa  re);
                                 fDocument = fDeferredDoc           ume           nt   I  m       pl;
                 fDocumentInd             ex = fD              e         ferr    edDocumentImpl.c     reateD       eferredD   ocument    ()    ;
                      // REVI    SIT :   strict error chec    king        is   not impleme      nted in deferred    d   om.
               //                  D   o     cument.support ins tead of spe     cifi     c class

                     // se   t actual e    ncod                 ing
                     fDeferre     dDocu   m    e  n    tIm pl.set Input  Encodin       g (en cod  ing);
                                             /        /   s      et docu        me  ntURI
                                  fDef  erredDocu me      ntImpl.setDocu   mentUR  I (loc           ator  .getEx  pandedSystemId   ());
                             fC   urrentNodeIndex = fDocumentIndex;

                    }

           } /     / startDocument(String,Strin g)

    /**
        * Notif  ies of the presence of an   XML Decl         lin      e   in the   docume     nt. If
        * present, this method wil    l be called immediately fo       llowi    ng th e
     * s    tartDocument        ca l    l  .
           * 
             * @param            version         The     XM  L versio  n.
                        * @pa ram     encoding             The IANA encodin   g        name of   the do cument,       or  n     ul l if
                *                                                           not speci  f      ied.
     *    @para    m stand  alone  Th  e standalo ne value,   or n   ull       if not sp   ecifi     ed.  
              *    @param augs       Ad    ditiona  l   inf ormatio   n that       may i   ncl   ude infoset augmentations
     *     
              * @   throws      XNIException        Thrown by han    dler    to s        ignal an error   .
            */
    public void    xmlDecl (String v    ersion, String         enc  oding   , St      r  ing    sta   nda lon     e,
       Augmenta        tions a    ug s)   
       throws X  N            IExcepti  on {
               if (   !f  Def              erNodeExpansio   n) {
            //    RE    VISIT:         when    DOM Le    v  el 3   is RE   C     r e   ly on               Do   cume    n  t.sup  po  rt
                   //              i   ns    tead of           s      pecifi               c class
                        if (fDo      cu       m  ent  Impl !=       null) {
                      if (   version != n ull)
                      f   DocumentImpl.setXm   lVersi    o  n     (v   ersi      o   n);
                                       f  Docum    en      t  Impl   .setXmlEnc     oding (    enc odi       n   g              );
                fDocum    en  t    Impl.        setXm   lStandalone ("y            es".eq                   uals   (st andalone))     ;
                    }    
                   }
            else {                     
                       if      (        versi                       on != null)
                  fDeferred         Do      cumen  tImpl.setXmlVer      sio        n  (version);
                        fDeferre   dDo    cumentImpl.          setXmlE     n co ding (encoding);
                                  fDeferredDo                           cum entImpl.setXmlSt      an  dalone ("yes".eq    u   a                 ls    (st   a     ndalo        ne));
                     }
         }  // x m  lDecl(Strin  g,S   tring,String   )

      /**     
       * No     tifie   s of        t  h    e pre            senc  e   o       f the DOCTY    PE line      in the do    cum          e  nt   .
        *
         * @param rootElemen   t The nam       e of the ro        ot element.
       * @ param public      I  d     The public  ide  ntifier  if a    n       external DTD or    null
        *                                                     if the ext  ern   al D         TD            is specified         using SYST         EM.      
                                                       *       @param sy     stem  Id                         The system   identifier if an external DT    D, null
                   *                                   otherw  ise.
       * @par am augs         Additio  nal i       nfo   rma   tion t  hat ma         y in    clud   e i        nfoset    a      ug mentations
         *
         * @throws X     NI  Except  ion     Thr  ow         n b               y han            dler    to signal an erro     r.    
       */
      p      ub lic          void doctypeDe   cl (Strin      g ro       otEle ment,
          String publi   cId      ,    String s    ystem        Id, Aug   mentatio  ns    a  ugs)
     t   hrow  s        XNIEx ception {      

         if (!f    De    fe   rNod  eExpa  nsio   n) {
                      if    (fDoc  u   m       e  ntImpl !       = nu   ll) {      
                            fDocumentType          =    fDoc   ument    Imp                   l.createD     ocumentTyp  e   (    
                                                      rootElement,   pu     bli cI  d, syste   mId);
                                f C   urre  nt No       de.   app       endC      hild  (f  Doc      umentTyp            e)      ;             
                           }              
                          }   
                   e    ls   e      {
             fDo cu        ment        TypeI             nde  x = fD   ef    e           rredD  ocumen       t   Imp l.
                    createDef       erredDocu    mentType (     r  oot  E  lement,     publicId,    sy   stem    Id);
                                   fDeferredD  oc            u   mentImpl.    app    endChild (fCur  rentNodeIndex , fDocu   me       ntT    ypeInd                          ex)      ;
          }

       }      //   d   o     c  t      y             pe         Decl(Stri  ng,Stri                   n     g,String)

       /  **     
       *   Th         e st     ar   t of an element. If the do      cu  ment     spe  cifies   the start elem           ent 
            * by         u          s  ing        an em   pty tag  ,    t                  hen the  st             artElement  method w ill immedi          atel y
        * be foll     owed by the endElement         method    ,     wi th no in       terve  ning                    metho   ds.
     *
               *     @param       el         e ment           The name o   f    the    el    emen   t              .
      * @param attrib  utes Th     e    ele      m     ent              attributes.
                     * @par    am    au   gs           A   dd   itional info    rmation tha   t may include infoset     augmen   t    ati   on         s
              *
      * @throws XNIExcept i    on T hrown      by handler   to signal an error       .
          */
            p   ublic vo  i        d startEl      ement (Q  Name elemen               t, XM  L At   tribute     s at      tribut   e       s, Aug  mentations augs)
                          thr    ows      XNIException {
        if (DEBUG     _EVENTS) {
             Syst      e    m.o   u    t.pr   intln              ( "=       =     >sta rtEl   e   m ent (     "+element.ra  wn        a           me    +")"      )   ;
                     }
               if (!   fDef  erNode  Exp    ansion)   {
            if (   fFilterRej     ec    t)            {
                          ++fRej   ec       tedEl    ementD   epth;
                                              r   eturn;
                 }    
                    Element  el =      crea    t   eElemen       tNo   de (ele ment);
            int    a  ttrCount = attribute       s.getLengt   h ();
                          bool                          ean se              enSc hem   aDe   f    ault = fa lse;
             fo      r (int i =        0; i                 < at             t   rCou              nt; i++ ) {   
                              attribu    tes.g        etNa me           (i,    fAttrQName)      ;
                                   Attr attr      = createAttrN    ode (fA ttrQNa  me        );

                  String a    ttr Value = attributes.getValue   (i);

                                           Attribute P     SVI attrPS  VI =(Attribute  PSV  I   ) att rib    utes.getAugmentat     ions    (i).  g    etItem (  Constant        s.      ATT     R        IBUTE   _PS              VI);
                 if (f       StorePSVI        && a       ttrP    SVI != n  ul    l){   
                       ((PSVIAtt   r           NSIm pl)              attr).setPSVI  (at  trPSVI);
                                                     }

                                 attr     .setV          alue (     attrVa   l  ue);
                                                         boolea           n s    pecif     ied = att  ribu  te                s.    i    sSpecifi    ed(       i); 
                    //          T   ake                      special care of sche    ma   d       efa          u       lted attribu    tes. Ca lling the
                                //      non-namespace aware         se         tAttr    ibuteNode(   ) method   co        uld over    writ    e
                             // anoth  er attribu      te  with the    same local na   me.
                      if      (   !s      p ecifi        ed &      & (   seenSchema        Default |   | (f    At    trQName.uri !=    null    &&
                           fAtt   rQName.u   ri != Namespa  ceConte     xt.     XMLNS_URI    && fAttr     QN  ame.prefix    == null))) {
                                    el.s   etAt     t      ributeNode   NS(att          r);
                                                               seenSchemaDe  fault = t    rue      ;
                           }   
                                else {
                                                  el  .se tAttribu   teNode(attr); 
                      }
                                                  //    N OTE: The          specifie   d value M       UST be   set a   fter    yo     u set
                                //            t             h e     nod            e v      alue      because     t      hat t ur ns the " s     pecified   "
                              //       flag  to "true" whic  h    may           overwrite a "f           alse  "       
                                 //       value fro  m    the at t  ribute l  is   t.     -Ac
                         if (      fDocum                          en   tImpl != null)         {
                                                  A   tt          rImp   l attrI   m   pl              = (  Att   r  Imp   l) attr     ;
                           Obje     ct     t      ype = n            ull;
                                          b      oole    an      id =     false;

                                                    // REV      ISIT: curr               en     tly i t               is po  ss    ib       le tha   t so    meone   turns    of   f
                             // namespaces  and turn      s     on xml sche ma validation
                                  // T  o avoi       d         classcast exception in     Att    rImpl check f   or      n  a  mespa   ces   
                                                // ho      wever             the c   orr ec    t so     lution should   probabl    y disallo       w s   e    ttin   g
                                                    //                       na      mespa ces to false   when schem  a proces   sing is             tu r        n ed    on.
                              if (a               ttrPSVI !=  null                  && fNa                mespaceAware) {
                                                             // X    M      L Sc                    hema
                                                    type =    attrPSV   I     .getM  emb  erTy  peD                efinit  i    on ();
                                                              if     ( type ==       null) {
                                                            type                 = attrP SVI.getT ypeDefinit       io            n () ;
                                                                                     i   f (type != nul  l)            {
                                                                                             id =      (   (XS   SimpleType) type    ) .        isI  DType (           );
                                                     attrI mpl.s   et    Ty      pe (type);
                                                        }     
                                             }
                                                  else {
                                       id = ((XSSimple       Ty           p      e) type). isID            Typ    e      ();
                                                        a     ttrImpl.set   Type (type);
                                               }
                            }
                                      else {
                                      // D    TD    
                                                                            b         oolean  is           D            ec   lar e       d = Boolea         n.  TRUE.equals   (att              ri        butes.getAugment      ations (i) .getItem (C  onstants.A TTRIBUTE_DECLARE D  ));
                                                               // For  DOM Le    vel   3 TypeInfo,      the    typ          e na        me                        must
                                              // b   e null   if      this attribute ha                   s not      been dec  lared
                                  //   i       n the DTD.        
                                            if     (is   Declared)  {
                                                   ty          p      e =     attr  ibutes.getTyp e    (i   );
                                                       id =     "I                D".     eq               uals  (type);
                                     }
                                                  a     tt rImpl.setType       (type);      
                                                 }

                               i         f (   i   d) {
                                                                                              ((E lemen      t    Impl) el)    .set   I   dA   ttri        bu te  Nod    e (attr, true);          
                                                }

                            a   ttrImpl.set                Specified (specif   ie  d)  ;
                            //         REVISIT                 : Hand     le en t     ities    in     attribu         te    v     alue.
                                              }
            }
                      setC haracterData (fa               ls           e);
     
                    if        (aug            s                   != n        ull) {
                                    ElementPS   VI el     e     mentPSVI = (E        lementPSVI       )augs.getIte         m   (           Cons      tants.ELEME     NT           _PSVI      );
                           if (elementPS     VI != n  ull   &&   fNamespaceAware) {
                                                   XSTypeDef       initio  n typ  e              = el e mentPSVI.g    etMemberT        y    p     eDe finition   (       );
                        if (type == n    ull) {
                                               typ e =    el em          entPSVI.getTy     peD e   fini   tion            ()       ;
                                         }
                             ((ElementNSImpl)el).se  tType     (type)           ;
                       }
                            }


                        / / f      ilte           r n     odes
            if  (fDOM      Filter != null                   & &        !fIn  Entit  yRef) {     
                        if (fRoo  t == nul       l    )    {   
                                 // f     ill val  ue of t      he      ro      o       t e  l   emen     t
                                       fR           oot = e  l;
                           }      else {          
                                  sh         ort code = fDO          M   Filte   r      .s   t    art    Elemen  t(e     l);
                                                           swi         tch (code) {
                                     c    ase L    SParserFilter.FILTER_I      NTERR  UPT :     
                                                                    {
                                    th       row Ab    ort      .IN        STANCE;
                                                   }
                                             c       ase L  SP a r       serFi              lter.FIL   TER_REJ  ECT :
                                                                    {
                                          fF  ilterR      eje    ct = tr ue;
                                        fRejectedE  l  ementDepth = 0;
                                                      r  eturn;
                                                                       }
                                 case   LSParser       Fi   lt     er.FILT         ER_        SKI     P :            
                                                          {   
                                                             // m       a    k                   e sure   th  at i   f       an        y char data            is av       a  ilab               le
                                                                         // the f Fi  r         st  Chu      nk is true, so that if      the   n     e    xt event
                                                 // i    s cha         rac   t   ers(), and the l     ast node  is       text, we          will copy
                                                                                                                   /      / t              he val ue already in             th     e       text no     de to fS    tr ingBuffer
                                                                 // (not to lose it).
                                                         f   Fir  stCh  un       k = true;
                                                       fSk  ippedElemS     t    ack.push(        Boolean.           T   RUE);
                                               return   ;
                                                   }
                                                                 default                       :
                                                     {
                                                                                             if   (!fSkipped    E     lemSta c     k.isEm           pty()) {
                                                               fS               k      ippedElemStack.push(Boolean.FALSE);
                                     }
                                                  }
                                  }
                        }
              }      
                          fCur           ren  tNo   de.   appendCh   il           d (e l);
                   f     CurrentNo  de = e                l;
            }
                else    {
                      int el = fDefer    redDocumentImpl        .createDe    ferredElemen            t  (fNamespac eA          ware ?
                                               e  l   e ment.uri :        null, e     lement. ra wname);
                                        O   bject type = null;
                          in  t             attr       C  ount      = att  rib   u      tes.getLen                         gth ( );
                     /   / Need   t o lo    op                in r everse order                 so th  at the    at     tributes
                                  // are proces  sed in   d o           cumen     t o           rder when      the        DOM     is ex  pan      de     d.
                for (in         t i = att    rCount        - 1  ; i       >=   0; --i     ) {

                              // set typ        e inf    ormation
                             Attr          ibu  tePSVI at     trPSVI = (Attr   i           butePSVI)at tributes.getA  u       g            ment   ations (i).ge t     I    tem (C    onstan  ts.ATT  RI  BUTE_P SVI   );
                                        boolean id      = false;       

                            // RE             VISIT: currently i  t is po            ssible t   hat   someone        turns off
                                    // na         me       s   p             ace     s      and t u   r   ns on xml schema validat ion
                   // T      o    avoid c lasscast e   xc   eption            in AttrI      mp    l c   h        eck    for na mespaces
                                   //  h   owever the corr   ect so       lution sh     o     ul    d probably disa   llow se     tt        ing
                        // namespaces  t   o          fal  se w  h       en schem       a p     roce       ssi       ng   is    tur  n ed o    n.
                      if    (attrPSVI   != null &   &     f  N        a    mes  paceAwa           re) {
                             // XML Sch   ema
                              ty pe = attrPSVI.         getMe   mberTypeDef    inition ();
                                      if (type == nu    ll) {
                                               typ         e   =      at     trPSVI.g      et       TypeDefin     iti    on ();
                                                                            i       f (type   !=     null){
                                    id = ((XSSimpleType) t   y  pe).isIDType ();
                                                  }
                                  }
                             e    lse     {
                                              id       = ((XSSi  mple  Ty    pe)     type    ).isID      Type (        );
                                             }   
                                     }
                          else      {
                                    // DTD
                                                 boolea  n i   sDecla     red = Boolean.   T RUE.equals (attri  but               e     s.getAu       gme  ntat   i             ons   (i).getI      tem (  Const    ant   s.ATTRIBUTE_DE   C LARED));
                                                      // F   or        DOM Level 3    TypeInfo, the type         na      me must
                                                         // be nu  ll if  th     is a      ttribute  has not    been d  eclared
                            // in the DTD.     
                                                                               i   f (isD           eclare    d) {
                                       typ        e = attr   i              butes.getTy   pe (    i);
                                 i   d       = "  ID   " .   equa   l       s (type);
                                                           }
                            }

                              //     create attribute     
                             fDefe   rredD   ocumentImp l.set         Deferred   Attribut    e (
                    el,
                                                           at tributes.getQName (i),   
                                    att  ribut e       s.     g   etURI (i),
                                        attr  ibu                tes.getV       a     lue (i),
                               attributes.isSpecifie    d (i),
                                                  id,         
                                 type);
                            }

                                  fDe f  e   r      redDoc  umentI      mpl    .appen d   C   hil   d (    fCurrentNod   eIndex  ,   el);
            fCu   r    rentNodeIndex = el;
                  }
       } // startElemen   t(QName,XMLAttribute   s  )
   

           /**
      * An     empty element.
     *
       *     @p     ar am    el   e   me   nt       Th      e name of the element.
                    * @param a  ttributes The elem ent       at     tribut es.
                     * @ par   am a                    ug      s     Additional information                   that may inclu de in          foset aug       mentations
     *  
         *         @throws XNIExce   ption Th    rown           b y         handler      to    si        gnal an      err   o            r.  
         * /
                  pub  l     ic vo        id    e     mp            tyElem                                   ent (Q    Na  me element, XMLAtt   r    ibut   es attribu       tes,          Augmen  t    ations a    ug     s)
    throws XN    IExc   eption {

                startEleme                nt       (el   ement, attr          i         butes       , augs);
                               endElem en   t            (element, augs);

    } //    e  m      ptyElement  (QName,XMLAttributes) 

    /**
             * C             haracte    r content.
     *
     * @param          te  xt The        content     .
         * @par            am  augs            Ad     d itiona   l info  rmat     ion                   tha           t ma y in          clu         de infoset a       u    gme    ntatio     ns
         *
             *      @          thr        ows X   NIExcep   ti  on   Thro      wn               by hand   l er to           sig  nal     a     n erro     r.
          */
                  publ            ic v       o       id charac   ters      (XML      St  ring text,  Augm          enta          tions augs    ) t h     ro ws XNI   E   xcept         ion                 {

              i        f (DEBUG    _EVE  NTS) {
            Syst             em.out. print  ln   ("=    =>       characte                         r        s():     "+text.to  St   r     ing ()   );
                                          }

                 i   f    (!fDefer       NodeExpansion)        {

                   if (fFilterRe         ject) {
                        return;  
                    }
                                                      if     (fInCDATASect   io   n &    & fCreat   eCD ATANode  s) {  
                           if (fCurrentCDATAS  ectio        n      ==      null) {
                                fCurrent          CDATASection =
                        fDo   cument.c  rea      teC  D  AT A   Sec tion  (text.toString () );
                                  fCur  re            ntNode  .ap   p      endChil   d (f Cur        rentCDATAS     e      cti   o  n);
                    fCur  re  ntN  o de =  fCu   r              ren tCD  ATASect     ion;
                           }      
                                     else           {
                       f  Curre      ntCDATA S   ection.appen     dD  ata (    t         e        xt.toString       ());
                              }    
                              }
                 e   lse                 i    f (!fInDTD) {
                 // if type    is union (X ML Schema  ) it       is                po  ssible that we        receive
                                          //      charac    ter call     with empty data 
                if    (text.   leng      th =  = 0)        {
                                         return;
                              }

                         Node child = fCu       rrent    Node.g etLastChi l d ();
                                                   if (child != nu  ll && ch ild.getNodeType     () ==   N          ode.TEXT_     NODE) {
                                                        // colle   ct all the data         into the stri  ng b  uffer   .
                                if        (f FirstChunk  ) {     
                                                                if (     fDo    cumentImpl ! = null) {
                                              f  StringBuil   d   e    r.      append (((Te xtImpl) chil  d).rem   oveData (   ))     ;    
                                } else     {
                                                             fSt   ringB uilder.app            end (((Text)c         hild).g    et   Data ())   ;    
                                                             ((     Text)c   hild   )  .setNodeV   alue (null   );
                            }
                                           fFir   stC   hunk = f    a             lse;
                                 }
                                if       (      t      ext     .length >  0) {
                                              fStr   ingBuilder.  append  ( text.ch, text       .offset, text.le    ng    th);
                                           }
                       }
                                 else   {      
                             fFirstChunk =          tru   e;
                               Text tex tNode =    fDoc     umen t.cre  at      eTextNode (text   .toStr       ing(  ));
                                                          fCurrentNode  .appe        n dChil          d (textNo    de);
                       }
   
                          }
                    } 
                                      e        lse             {
                       // The Text    and CDATASection                      normali  zatio          n is    taken car   e of within  
                       // the        DOM in the def      erred cas          e.
                if ( fInC    DA T      ASect    ion      &  & fC     reateCDATANo  des) {
                             if (fCurrentCDATASection Inde   x == -1) {
                          i  nt  cs = fDeferre    dDocumentIm   p              l.  
                                    createDeferr     e  dCD  ATASect  ion (t ext  .  toString ( ));

                                            fDeferredDocumentI   mpl.appendChi    ld (             fCurren       tNo   deI  nd ex, c    s);  
                            fCurrent     CDAT    ASe  ctionInde         x          = cs;
                                               fC  u  rren  tNodeIndex =     cs;    
                 }    
                                       els  e {
                                                           int tx t =      fDeferre   dDocume     ntImpl.  
                      cr    eateD      efer  redTextNod         e (     text.toS  tri      ng ()  , false      );
                             f       DeferredDoc    um  entImpl.a       p  pen dCh   i    ld (fCurren t   NodeIn   de   x, t xt);   
                                   }  
                } else i f    (  !fI        nDT    D) {
                 // if type is      un  io   n (XML Sch     e     ma) it is  poss ible     that we      r      ecei        v    e
                           //      character call with em  pty data
                                if      (te       xt.le      ngth == 0) {
                                                  return;
                    }

                                                      St   r                   i    n     g value       =      text.toSt ring ();
                     int txt = fDeferr  edDocu   mentImpl.
                         createDefer    redText  Node         (va     l   ue  ,        false)     ;  
                      fDe       ferredDo    cumentImpl.ap  pendC  hild              (fCurr entNo   d   eInd  ex,    txt);

                 } 
                  }   
          } //      chara     c         te     r  s  (X  ML  Str  i  ng)

    / **
           * Ignora  ble whitesp     ace. For thi     s        met      hod to be call ed, the             doc            ument 
       * source   m           u  st hav      e s    ome way of                  d  etermi    ni     ng that t  he   te xt conta ining  
          * only      wh      it espace ch   ara           cte  rs sho           ul      d be       considered         igno            r   able.              For
           *      e  xam      ple, the val idator    can dete     rm   ine       if a length of       whitespace
                      *    characters in   the document a                 re ignorab le b  ase  d on     t    he      elem  e nt
          * content      mo   del             .   
     *
      * @param text          Th     e ign           orable             whites    pa  ce.
     * @pa                      ram a       ugs     Addi tional in           form      ation that may    includ   e info       set    augm   enta  ti                      ons
          *
          * @t       hrows XN   IExcep tion    Thro  w   n by handler to     signa         l         an      err or  .
        *   /
    pu  bl ic void i g        norableWhit  e      space  (XML   St      ring t           e  xt, Augme             ntat                              i     ons       a   u      gs)                th     rows X NI  Excep    t    ion {

            i        f (!     fInclude       I gnorableWhites        pa   ce    || fFi   lt                    er        R                      eje    ct)    {
                       return;
        }
            if (!fDeferNod    eExpansi          on   ) {
                        Node        child         =   fCu rren      tNode.getLastC  hil d ();
                          if (c h ild != null && child  .g     e                tNode  Type  () ==             Node.TEXT_NO DE)        {  
                            Text tex tN                  o              de  =    (Text)chil    d;
                           tex  tNo      de.appendData (text.t oString ())  ;
                  } 
                else      {
                      Text textNode = f                    D    ocument.cre  ateTextNode (te        xt.toSt       ring ( ));
                      if (fDoc     um        en   t  I  m   pl !        = null) {
                                           T          extImpl textNod                 e         I    mpl =             (TextImpl)t   extNo  de;
                                       textNo     deImpl    .s  et     Ign  orableWhitespa   c     e   (tr  ue) ;
                     }
                          fCurrentNode.   appendCh  ild    (textNode)      ;
                      }
        }        
               else {
             // The Tex t normalizat    ion    i     s taken c    are           of within      the      DOM i    n the
                // defer    red case.
                           in  t txt =   fDeferredDocu mentImp     l.
                cre       at   eDefer    r  ed  Tex    tNode   (text.toString (), tr       ue);
                            f Def er r      e  dDocumentImp  l.ap        pendCh     i  ld (fCur    rentNodeI       n   dex, tx t)     ;   
             }
    
         }      //           ign           o      rableWhi      t     e   space(XM         LStr     ing)

                   /**        
                  * T        he end      o   f a   n           e  l   ement.
        *
              * @ par  am e        lement T        he name      of the ele        ment .
     * @param augs          Add ition         al inform   ation t hat   may in  c                                         lu de  in  fo  se t  a    ugmenta  tions
           *
            * @throws XNIExce       ption T  hrown by    hand       ler to si gnal  an e   r        ror.
      */
        public           voi             d endEl     emen    t    (Q  Name el emen    t, Augment ations augs) th  r   ows XNIExc    eption {
              if      (    DEBUG_EV ENTS)   {
                                          System.out.pr  intln             ("==>endElement ("      +element.rawname    +")           ");
        }
            if   (!f  DeferNod    eEx               pans    i    o  n) {

                // RE     V        ISIT: Shou    ld this happen   af     t  er   we    call the filte   r  ?
                                 if (augs != null && fDocume ntImpl != nu  l     l && (fNamesp    aceAware || fS  t            orePSVI))                         {       
                           ElementPSVI      ele     men   tPSVI   = (Element   PSVI)  aug             s.getItem(Constants    .ELEMEN T_       PS     V     I);
                        if  (elementPSVI     != n    ull       ) {
                          // Updating Typ    eInf       o. If the decla  r        ed        type is a union t he                  
                                           // [member type    definitio     n] wi  ll                only be avai        l  able at the
                         /  / en                    d of an eleme      nt.   
                                           if (     f Nam     e   spaceAware) {
                                       XSTypeDe  finition typ     e = elem  e  n  tP        SVI.get   Memb    erTyp  e     Defini    t ion();    
                                             if (type = = null)   {     
                                             type = el em      entPS  VI    .getTypeDefinition();
                                                               }
                                ((Ele      men   tNS       Im     pl)f  Current             Node  ).s e  tType(t   ype);
                         }
                       if (f S    torePSVI)         {
                                               ((PS  VIEle  mentNSImpl  )fC       urrent   Nod               e)  .     set    PSVI (el  e       mentP  SVI) ;
                                     }
                     }  
                                }
   
                     if (fDOMFilter != null) {
                               if (f   FilterRe       jec         t) {
                                                 i f (fR ejectedEle        men   tDepth   -- == 0  )          {
                                                                        fFilterReject = false;
                              }
                                   ret          urn;
                              }
                                        if (!fSkip   pe  d     ElemStack.isEmpty()                ) {
                                  if (fS ki        ppedElem    S        t  a          ck      .     pop()       == Boolea          n.TRUE) {
                                        ret ur  n;
                                               }     
                              }
                        setCha  racterDa       ta (fal   se)  ;
                                                        i  f     ((f         Cu  rren              t      No  de !=   fRoot) & & !fInEntityR  ef  && (fDOM    Fi lte        r.g etWhatTo S     how (  ) &       NodeFilter.SH       OW_ELEMENT)!=0)  {  
                              sh      o  rt     code = fDOMFi   lt        er  .acce  ptNode (fC urrentNode);
                         s   witc                    h   (cod    e) {  
                                              cas  e    LSParse             rFilter.   FIL  TER_INTE RRUPT:{
                                                             thro w A       bort.INSTANCE;
                                                     }
                                         case   LSPa    rserF                 ilter.FILT     ER_REJECT: {
                                        Node pare nt =        fCurrentNode.getPar e        ntN   ode         ();
                                                     pare  nt.re   m     ov  eChild (fCurrentNode    );
                                 fC  urrentNode =  parent;
                                                       return;
                                     }
                             case     LSParse   rFilter.FILTER_  S    KIP    :   {
                                                     // make          su                  re t         hat   i      f an y c      h     ar data is available
                                              //   the fFirstChunk is true, so that if th     e ne  xt              e                  ve     n   t  
                                 // is char  ac t         e  rs(),   a nd t   h   e  last  node i      s text         , we      wi              ll c opy
                                                 //      the value alrea dy      in the tex   t n                     ode           to   fStr  i    ngBuf     fer
                                       // (not to lose it).
                                                 fFirstCh  unk      =   tr    ue;

                                                                    // re   pla ce c  hildren
                                                                               Node par ent = fCurrentNode.get           ParentNode ();
                                               NodeList l  s = fCurrentNode.getChildNodes ();
                                    in t le   ng   th        =    ls                          .getLe   ngt    h ();

                                            for (int i=0;i<leng   th;i++) {
                                           p          a   ren   t .appen       dChild (ls.item (0));
                                                          }
                                      par ent.       r  emoveChi ld (fCurre     ntNo  de     )    ;
                                                               fC         urr     e      ntNode = par     ent;

                                          retur n ;       
                                        }

                                                 default: { }
                                      }
                                }
                     fCurrentN  ode = fC u             r    ren            tNode.getParentN         ode ();

             } // end-if DOM       Fil ter       
                 els  e {
                             setCharac    terDa     ta (fal    se );
                             f   CurrentNode = fCu        rren tN       ode.g   etP          a      rent  Node                     ();
                     }       

                       }
        e    lse          {        
                 if (a   ugs != n       u ll)        {                   
                    Elemen   tPSVI element   PSVI = (Elem    ent    PSVI) au         gs    .getI   tem( C   ons  tants.ELEM   ENT_PS  V I);
                if (elementPSV  I != nul   l) {
                                            // Setting Ty   peInfo.     If t    he            dec     lared t   ype i   s a union the
                          /      / [member   typ   e  definition       ] wil     l     only be available            a  t   the 
                             // end of an elemen t        .
                                                    XSTypeD   efinition       type = elementPSV   I.getM  emberTy   p   e Definition();
                                        if (type =     = null)  {
                                                  type   = elemen   tPSVI.getTypeD   efi    nition();   
                                   }
                                    fDef          erredDocu    m  e         ntI   m        pl. setTypeInf     o (fCu     r  rentNod    eIndex, t yp    e             );
                          }      
                     }
                 fCurre ntNodeI     ndex =
                     fDef   erre dDoc  ument     Impl  .ge   tParentNode (  fCurr    entNodeIndex ,     fa    lse);          
            }
     

    } // endE  leme   nt  (QNa         me)
    

                /   **
          * The s               tart of          a CDA TA    section.
     *     @param augs     Additi    onal i   nf    ormation that ma    y in   clude inf   oset aug       ment  ation          s
               *
        * @t    h rows            X  NI  E   xception Thrown by ha     ndle                         r to     sign al an erro           r.
     */
         pu      blic    void sta                rtCDATA (Augm   e   ntations au     gs)   throws XNI      Exception {

            fInC    D    ATASec    t      i         on = true;
            if (     !fD  eferN           odeE xp    a     nsion) {    
                             if (       fFilterRej ect)   {
                                      ret    urn;
                  }
              if           (fCre a teCDATANo  des) {
                              setCh   aracte         r Data (fals   e    );
                               }
        }
            }         //        startCDA    TA  ()
       
          /**
          *   T  h   e end of a CDATA section.
     * @param    a   ugs           Additio            nal information     that may incl   ude           infos   e        t a ugmentatio  ns
     *
                   * @thro    ws XNIExcep  tion T  hrow      n  by handler           to    si     gnal an    error.
       */
    publ         ic                   void endCDATA (Aug       mentation    s augs      )      throws XNIExc            eption         {   

          fIn CDATASection = false;
                   if     (!fDefer  No  deExpansion) {

                 if (          f  Fil  te  rReject) {
                          return   ;
                }     

                 if (f    Cur                   rent         CDATASec  t       ion !=  nul  l) {

                    if (f      DOMFilter !   =null && !f  InEnti t   yRef &&
                        (fDOMF i lter.                  getWhatToS how ()  &              N  odeFilter.S     HOW_CDATA_SECT  I    ON)!= 0) {
                             sho          r         t code = f        DOM          Filter.acc   e     p  tNode (  fCurr       entCDATAS     ection   );
                                                                        switc h (code) {
                                             case  L  SParserFilter   .FILTER_I   N TER   RUPT:  {
                              throw Abort.INS TANC        E;
                                                     }
                                          case L SP      arserFilter.F     IL TER_REJECT:{
                                                  // fa       ll through t     o          SKIP s    ince C    D     ATA section has no c       hi    l        dren. 
                                          }
                                                      c  ase     LSParser     Filter.FI             L   TER      _SK     IP:     {
                                       Nod  e    parent    = fCurrentNode    .getParentN  ode                     ();
                                 parent. rem   o   ve Child (fC  urr             en        tCD   ATASect    io n);
                                                                                        fCurre    ntNode           = parent                ;
                                                  ret                     ur   n;
                                 }

                                             defaul    t: {     
                                // a  cc    ept       node
                                  }
                                         }
                   }

                                        fCurr          entNode        = fCu    rrentNo d  e.getPar     entNode ();
                                                fCurrentC                     DA TA     Sec           ti      o       n            =    null;
                              }
                }
                           else  {
                     if (   fCur   rentCD    AT  ASec      tionInde    x !   =             -1) {
                                      fCurren    tNode      I    nde    x =
                                 fDeferredDocumentImpl.getP arentNode (fCurrentNodeInd   ex, false);
                               fCurrentCDATA SectionIndex       = -1;
               }  
           }

    } // e      ndCDATA      ()

           /**
          * The end o       f the      document.
     * @p     aram a             u         gs           A       ddi  tional      i nformation     that may include info s  et augmenta           tions
                     *
            * @throws XNIExc   e   ptio       n     Thrown b   y  handler t    o    s   ignal an    er      ror.
         */
    pu      bl i  c void           endDocument (Au             gme   ntations  aug     s) t  hro  ws XNIExcep t      ion {

        if (!f DeferNo                   deExpans   ion) {     
                    // REVI    SIT:    w            hen DO M Level 3 is   REC    rely on       Doc    ument.supp   ort   
                       //                   instead   of spe   cific clas    s
                        //              se     t    the actu       al enc    oding and         set DOM error    checking back o    n
                     if (fDoc u    mentImpl !=   null     ) {
                               if (fLocator !    = null) {   
                               if (fLocator.g  etEncod in g()   !=        null)    
                                           fDocumen   tImpl                .  se  tI    np  utEnco       di       ng (           fLo    cator.getEnc         odi n    g()) ;
                                    }
                   fDoc ument Impl.s   et  S      trictErr        orChecking (true);
                   }
                  fCurrentNod      e   = null;
        }
              else {
                            //  set th     e actua   l    en  c  o                  d     in  g 
               if (    fLoc       ator !=    null) {
                            i        f (fLocato  r.getEncoding() != n                      ull    )
                          fDeferredDocumentIm     pl.setIn   put        Enc      o               ding (fL     ocator. getEncoding());
                            }
              fCurr ent       NodeIndex =           -1;
                       }

        } // endDocumen     t()

       /**
     * Thi     s method no           tifies th  e e  nd o       f                 a           general entity.   
     * <p      >     
     * <strong      >Note:</stron g> Thi      s  method is   no       t     called for entity          ref    erences
                 * a  ppea     r  ing as p   art of attribute  values.
                  *
                * @     param name   The nam e of the entity      .
              *   @param augs               Additional i     n formatio    n that may inclu  de infoset augm   ent  ations
       *
      * @e     xcept  i     on XN     IException 
         *                             Th  rown       by h     andler to signal an err   or   .  
          */
      pu    b         lic                  v       oid    en      dGeneral  Entity     (String name, Augment  ation    s aug   s) throw       s   XNIExce     p tion   {  
        if (D      EBUG_EVENTS) {
            System.out.println ("==          >e  ndGeneralEnti    ty: ("+name+")");
             }
                    if (!fD   eferNode   E   xpansion) {

              if  (fFilterReject) {
                           return;
                            }
                            se         tCharac  ter     Da   ta            (tru  e);

                if (fDoc      umentType != n       ull) {
                                   //        get current entity declar a   tion
                                           Na me            dN   odeMap      enti        ties = fDocume      ntType.     getEntit   i    es ();
                                 fCu      rren    tEnt   ity     Decl =       (EntityImpl) ent    ities.getNa   med   Item (name);
                       i f         (fC  u  rre     ntEntit  yDecl != null) {
                                                     if (fCurrentE                    ntityDec    l != null           && fCurrentEntit yDe         cl.getFirstChil    d ()     =    =     n ull)   {
                                      fCurrent  EntityDecl.s      e tRea  dO   nly (false, true);
                                                      Node chil      d                         = fCurrentNode.getF   i rstChild ();  
                                              whi        l   e (   chil  d        != null    )      { 
                                                                N        ode   copy = child.cloneNode             (tr    ue)               ;
                                                  fCurrentEntityDe          cl.append   Child    (copy    );
                                          chi ld =    chil d .getNextSibling ();
                                         }
                               fCurrentEntityDecl.se        tR  ead    Only (true, true);

                                     /   /   en ti   ties.se    tNamedIt   em( fCurren               tEn        tityDecl)      ;  
                             }
                           fC    u   rrent EntityDecl         = nul    l;
                        }

                           }
                                                 fI  nEnti   t     yRef         =      false    ;
                    bool     ea  n removeEntityRef = fa      lse;
               if     (fCrea teE  nti    tyRefNodes)   {
                        if (fDocum   entI mp             l != null) {
                                          /         / M   ake e   ntity r    ef node        rea       d on     ly
                                  ((N   odeImpl)fCurrent N      o     de).setRead                               Onl                y (true, true);  
                       }

                    if (fDOMFi   lter      !=null    &&
                          (fDO    MFilte            r .ge      tWhatToShow () & NodeFi  l    ter.SHOW_ENTITY_REFE  R  ENCE)!= 0) {   
                                                         short code =    fDOMF               i l          ter.acceptN             ode (fC   ur   rentNo     de) ;
                                  switc              h   (     code) {
                                      c  ase LS ParserFil  ter.FIL      TER_INTERRU   P       T       :{
                                        throw Abort.INSTANCE;
                             }
                                                               ca  se L    SParser   Filt er.FILTER_REJE                CT:               {
                                            No          de pare         nt =   fCurren  tNode.ge               tPare  ntN      o   de         ();
                                                          par   ent           .remove     Chil     d (fCurrentNode);
                                     fCur         re ntNo de    = parent      ;
                                                              return       ;
          
                                                 }
                              case LSPar     serFilter.FILTER_S   KIP: {
                                   //    ma  ke s       ure we    do         n't      l oose char  s if n    ext e   ve     nt      is c haracters     ()  
                                       fFirstChunk     = true;
                                                re   move   Entit yR         e      f    =  true;
                                                    br     eak  ;
                                       } 

                                                    default:     {
                                                       fCu  rrentNode = fC       urren  tNo    de.ge       tPa    r  e    ntNode ()   ;
                                      }
                                      }
                             }     else {
                                 fCurrentNod  e  =       fC      urrentN               od  e.getParentNode ();
                      }
                }

                              i      f (!fCreateEn   tityRef  Nodes || removeEntityRef)         {
                            //     mo  ve   entity reference child  r    en   t      o the list of
                         /   /                sibl  ings o        f   its par  ent an      d    r     e   m   ov e entity referenc e
                            NodeList chil   dren = fC     ur ren     tNod e.getChildNodes ()   ;  
                   Nod       e                  parent = fCurren tNode. getParen    t     Node ();
                       int length = children.get    Length (   );
                                         if       (length > 0) {

                           // get      pr  evi   ous        sibling of    the   entit    y refer   ence
                                   Node  no de    = fCurr     en   tNod   e.g   etP   reviousSibling (); 
                                            // no  rmalize     tex      t no     des
                                  Node     chi         ld = childr    en.i   t  em (0);  
                         if (node   !=       nul l         && node.getNodeTy   pe () == Node.  TEXT_NODE &&
                            child.ge    tNodeTy pe () ==   Node.TEXT_N                  ODE     ) {
                                        ((Text)node).appendData (ch  ild.    g  etNo     deV           alue ());
                               fCurrentNode.remo  veChild (chi   ld);

                       }    else   {      
                              node = pare      nt. in   sertB        efore (   chil  d    , fCu  rre     nt   Node);
                                                     handleBaseU     RI (   n  ode);
                            }
    
                      for (i nt i=1   ;    i <length;i++) {
                              node =    paren       t.inser       tBefore   (c  hi    ldre n.it    em (0), fC ur   rentNo       de);
                                     han   dl   e     BaseURI       (no  de);
                        }
                    } // length >      0
                       parent.r emo v         eChild (        f       Curre   ntNode);   
                       fCurren    tNo   de =    par    ent;
                 }
            }
        el     se      {
     
                      if (f          Docume nt T    yp      eInd  ex != -1 ) {  
                /  / find corres   ponding    Entity   dec     l
                    int nod    e = fDe    ferredDocume                 ntImpl.getLastChild (fDocument       TypeIndex,    fals  e);
                whi      le (  n  ode != -1) {
                                            s  hort        nod  eType = fDe    ferredD      oc  u    mentI    mpl.g    etNodeTyp  e (node, fal  se);
                                   if       (node   Type    == Node.   ENTITY_NODE) {
                                         String nodeName =
                                           fDeferredDocumentImpl.getNod e          Na   me (n  od   e,         false);
                                        if (no      deNa me.equal    s (name))    {
                                                    fDef erredEnt  ity     Decl = node         ;
                                                 b       reak;
                                                }
                                      }
                                        node   = fDeferre           dDocumentImpl.ge   tRea           lPrevSibl ing ( node, false);
                }
                                             }

                   if (fDefer   redEn  tityDecl != -   1        &&   
                                    fDeferr   edDoc        u   mentIm         pl. ge tLastCh  ild          (fDefe     rre   dEntityDecl,     false) ==             -1)    {
                      // entity definiti on exists and it     does not have any     c      hildren
                           int prevIndex = -1   ;
                  i     nt chi  ld  Index          = fD ef erre dDoc   umentIm     p     l.g   etLa                stChild (fCurren     tNodeIndex , f  alse) ;
                  while (      ch  ildIn    dex !=     -1) {
                        int     cloneIndex  = fDeferre    dDocumentI    mpl .clon   eNode (childIndex, true);
                              fD efer redD           oc    ument    Impl.insertBefo re (fDeferredEntityDecl  , clon      e     Index, p   revIndex);
                                                   prev   Index = cloneIndex;
                     childIndex = fDeferredDocumen       tI        mpl.    getRealPr    evSibling (child   Index,   false) ;
                       }
                }
                  if (fCre  ate    Entity        R        efNo    des) {
                                      fCurrentNodeIndex =
                      fDe      ferredDocume  ntIm   pl.   g    e  tParentNode (fC            urren        tNod      eIndex,
                     false);
                        } else { //!fCreateEntityRef    Nodes
                          // move child   ren of e    nt   ity ref befo re the enti    ty    ref.    
                                // r  emove    entity r ef.

                 // holds    a child of  entity ref
                      int ch     ildIndex = f Defe      rre    dDocument      Impl.getLast                              Child (f          Current  NodeI         ndex, f  alse    );
                                               int pa   ren  tIndex =
                 fDe   ferredD ocumentImpl.g       e    tParentNode (fCurr     en        tNodeIndex,    
                         false);

                   int prevIndex =        fCurrentNodeIndex;
                int lastCh ild =  chi  ld Index;
                int sib    ling = -1;
                   while (childIndex            != -1)        {
                    han     d le         BaseURI     (ch  i          ldIndex);       
                              sibl ing = f Defer   redDocumentImpl   .getRealPrevSi   bling (c         h     ildIndex           , false);
                              fDeferredDoc        u m      entImpl.inser       tBef  ore      (pare  ntIndex,   chil  dIndex, prevIndex);                     
                    prevI  ndex =  childIn    dex;
                                   chi    l  dIn de         x         = sibling;
                  }
                    if   (l    ast      Child           != -1)
                       f DeferredDoc   umentImpl.se tAsLastC       h  ild (  p arent                       I      ndex, l  astChild);
                         else{
                            sibl         i  ng = fDeferredDo cumentImpl.     g      e  tRealPrevSiblin  g           ( prevIndex, fa     lse);
                                         fDeferr     edD  ocumentImpl     .setA    sLastC  h     il   d (paren tIndex, si   b   ling)   ;
                                               }     
                                      fCurrentNo  deIndex =   p   arentIn      dex ;
               }
              fDeferredEntityDecl =                        -  1;       
           }

 
    } //    en     dGe      ner   a    lEntity(St   ring, Augmentatio        ns)


    /**
         *      Re   cord b  aseU    RI info   rm   ation         for the        Element (by adding xml:b   ase at    tribute)
      * or for             t      h                     e Processing         Ins  tru    c  tion  (by setting a      baseUR    I field)    
        * Non de     ferred DO   M.
     *      
     *                 @param node
     */
                        protected final   void hand  leBaseURI (Nod   e nod   e){  
                        i   f (fDocumentImpl !   = n      ul     l)          {
                       // REVISIT:     remove depen   d e    ncy o       n our imple mentation when
                            //          DO  M L3 becom  es REC   

                  Strin  g b as    eURI = null;
                             short nodeType =   n ode.getN odeType ();

                      i   f (nodeTyp    e    =    =   No de  . ELEMEN       T_N        ODE) {                    
                    //   if   an       elemen  t al  ready       ha  s xml:base attribute
                                        //      do  nothing
                     if     (fN a mespa   ceAware)   {
                                   if (((Elemen   t)node).getAtt  ribu      teN     od   e     NS    ("  http://  www.w3.org/X  M  L/19  9   8/na  me    space"   ,"base    ")!=     null)      {
                                            return;
                                  }
                    } els e if ((( E   le    ment)node).ge   tAttr    ib      uteNo     de ("xml:b    ase"   ) != null) {
                      r    etur  n;    
                  }
                          /  / r     e   tr                  ive th        e baseUR I from the entity reference
                        baseURI = ((EntityReferenceImpl)fCur          rent          N  ode).getBaseURI ();   
                   if   (baseURI    !=null && !baseURI.equals (fDocumentImpl.ge    tDo    cumentURI ()      )) {
                          if (fNamespa      c    eAware) {
                              ((E     lement)n  ode)       .set        Attrib   ute NS             ("   http://w  w   w.w3.org/XML  /1998/namespace", "xml:b ase", baseUR   I);
                           } else {
                                        ((Element)node).setAttribute ("xml      :base", baseUR   I);
                           }
                      }
                    }            
                   else                     if (no deTy  pe ==    Node.PROCESSI   N   G_    INSTRUCTION_        NODE) {

                             base    U   RI = ((Ent              i tyR          efe   renceIm    pl)  fCurrentNode).getBaseURI ();
                         if (baseURI     !=null   &&      fErrorH an  dler          != null  )   {    
                    DOMErrorImpl er   ror        = new DOMError       Impl    ( );         
                                    err or.fType =    "  pi-base-uri   -n     ot- preserve                   d";
                                  erro      r.fRela tedData =     ba   seURI;  
                      e r       r   o     r.   fSeverity   = D  O  MError.   SE   VERITY_          WARN   I   NG;   
                      fErrorH andler.ge         t    ErrorHandle  r       ().handleErr   or (error);
                                   }
                      }
              }
    }  
            
    /**
          *
                   * Record    baseURI i n formation     for   the El  ement (           by adding xml:base  attribute)
     * or for the Proces          sin  gInstruction (by   s  et      ting a baseU  RI field)
     * De  ferred D   OM.
                   *
     * @param node
     */
                  prote     cted final void h  a     ndleBaseURI (int     n       ode){
                 s    hort node Type  = f    Defer   redDocumen  tI mpl.getN            odeTyp e (node, f     alse);

        i      f (nodeT    ype              == Node.EL EMEN        T_NODE) {
            String bas e URI = fDeferredDocu  me    ntIm         pl.getNodeVa    lu     e   Stri    ng (f  Cu   rren              tNodeIndex, false);
                 if (baseU    RI == nu        l    l) {
                                                b     aseURI  = fDeferredDocumen   tImpl.getDeferred          EntityB     aseURI (fDeferredEntityD        ecl);
                           }
                       if (baseURI !=null && !baseURI    .  equ   als     (fDe   ferre d Docum     entImpl.ge       tDocumentU      RI ())) {
                      fD    e ferr edDocumentImpl.setDeferred     At    t   ribute (node,
                                               "xml:base"        ,
                        "http      ://w  ww.w3.org/    XML/1998/n  ame     space",
                            baseUR     I,
                    tru  e)    ;
                 }
          }
             else if (nodeType == Node.PROCES  SING_INSTRUCTION_NO  DE  ) {


                                       // retrieve baseU         RI from the entity reference
                                String   baseURI = fDeferredD   ocument Impl.getN   od eValueS    tr    ing (fC     urr  en tNodeIndex   , fals e);

                      if (base    URI == n ull)        {
                             /   / try base    URI of the entity declaration
                              baseUR     I     =     fDeferred Docum      entImp l.getD  efer     redEntityB as            eURI (fDeferredEntityDe   cl);
                           }

                 if (baseURI != null && fErrorHandle   r   != null    ) {
                D OMEr  rorIm  pl e    rror = new    DOME   rrorImpl ();
                       error.f  Type = "pi-bas    e-uri-not-pres      erved";
                        er     ror.fRe latedDa        ta =  bas    eU RI;
                  error.fS  ev      e     r    ity           = DOMError.SEVERITY_WARNING;
                 fErr or  Handle r.getErrorHa       ndler     () .handleError (error   )      ;
               }  
           }
            }   


       //  
        // XMLDTDHandler me thods
     //

    /**
      * The start of the DT  D          .
     *
     * @param   locato  r  Th   e document locato r, or null if the  docume nt
     *                                 lo c   ation cannot b  e reported during the    parsing   of
     *                              the do    cume   nt DTD. However, it i   s <em>s                trongly</em>
        *                                                     recommende   d th   at a locato   r be   supplie   d that can
     *                 at l          east repo   rt th e base sys  tem identif         ier    of     the
       *                                DTD.
           *     @p  aram a     ugs  Addi    tiona    l information t  hat      ma y in       clude info    se t
     *                                               augmentations.
                   *
       *     @th      rows XN    IException        Thrown      by h         andler        t    o signal an er ror.
                */
    pu bl    ic void sta   rtDTD (X  MLLoca     tor loca tor, Augmentat    ions augs) throws   X       NIEx    cep    tion {
            if (D EBUG_     EVENTS) {
                    System  .out.pri      nt   ln    ("     ==>startDTD");
                 i   f (   DEBUG_BA    SEURI) {  
                      Syste  m.   out.println ("   exp     andedSystemId:    "+locator.getExpandedSystemId ());
                              System.   out.println ("    baseUR  I:"      + locator.get      Ba     seSyst    emId   ());
                                }
             }

        fInD  TD = tr       ue;
        if     (locator != null) {
                 fBaseURIS     tack.push  (lo        cator.getBa  se    Syste  mId ());
             }
        if       (fDeferNodeExp    ansion ||    fDocument    I  mpl != null)    {
                   fInternalSub     set   = n   ew  String     B    uild   er (10   24);
        }
    } // startDTD(XMLLo  cator)


    /**
         * The end o   f the DTD.
            *
     * @param augs Additio  na   l information     that may i  nclud e i            nfoset
     *                               au    gmentations.  
     *
                       * @t    h r  ows XN  IExc    eption Thrown by handler  t     o signal  an e   rror.
     */          
    publi     c   void endDTD (Au    g  mentation       s augs) throws  X    N     IExc   eption {
           if (DEBUG_EVENTS) {  
                        S   yste  m.out.pr in   tln    ("==>end  DTD  ()");
               }
        fIn         DTD = fa     lse;
          if (!fBa       seUR IStack.i   sEmpty ())    {            
                 fBas       eURIStack.    pop ();
                   }
                       Strin  g internalSubset = fInternalSubse          t !=    n   ul  l && fIntern   alSubse    t.length () >        0
                  ?  fIntern     al Su      b          set  .toS   tring () : nu  ll        ;
         if    (fDeferNod   eExpan  sion) {
            i f (internalSubset != nu   ll) {
                                                   fDeferredDocumentIm pl. s etInternalS  ubset  (fDocum    e   ntTypeIndex, in  ternalSu           b      set);
                    }
              }
        else if (fDocumentImpl !=   null)    {
                  if (i nter    nal     Sub   s          e       t !   = null) {
                      ((Doc        ume ntType    Impl)fD  ocumentTyp  e)  .setInter   nalSub  set (i       nternalSub     set);
            }
                        }
    }     // endDTD()

    /** 
                    * The   start       of a conditi    onal section.   
     *
        * @        param t     ype            T    he type of the     co  nditiona       l   s ection. This value will  
     *             either be CONDITI   ONA   L_INCL   UDE or      CONDITI ONAL_IGNORE.
     * @p    ara     m augs   Additiona l info         rmation that may inclu    de i   nfoset
          *                                       augmentations.
     *
     * @       thro   ws XNI   Exc  eption    Thrown b  y handler to    signal an error.
     *
          * @see   #C ONDITIONAL_INC LUD    E
     * @se      e #COND     ITI   ONAL_IGNORE
     */
    public void startConditional (short type,  A  ugmentatio  ns a   ugs) th    rows XNIException  {
    } / / startConditional(        short)

    / **
         * The     end of    a c     onditional se      c      t  ion.
     *
     *       @p  ara  m                augs Addition   al information that may   inc     lude infoset   
     *                                  augme    ntation        s.  
         *
     * @throws    X   NIExce  ption Thrown by handler to signa       l an   erro      r    .
        */
      p    u        blic void endConditional (Augmentations           augs) throws XNIE   xception  {
       }    //      en    dCondi    tion     al()

    
             /**
        * The start of      the DTD ext    erna   l su     bset.    
       *
     * @param augs Additiona   l i  nformation    that     may inc     lude infose t
     *                              augmentations.
         *
           *           @thr  ows XNIExcepti      on Th    rown by handler to s     ignal an error.
     */
    pu    blic vo  id startExternalSubset (XMLResourc     eIdentifie  r identifi er   ,
    Augmentatio       ns               augs) throws XNIExceptio      n {
        if ( D    EBUG_EVENTS)    {
              System.out.print  ln         ("==>startE    xternalSubset");
                   if (DEBUG_BA  SEURI) {
                          Syst em.out.println ("   expandedSystemId: "+identif ier.getExpandedSystemI    d ())    ;
                                        S   ystem  .out.prin  tln    ("       baseURI:"+ identi  fi   er.getBaseSystemId ());
            }
        }
          fBaseURISta           ck.push (identi     fie    r.getBaseSyste       mId (    ));
         fInDTDExternalSubset = true;
      } // startExternalSubse  t( Augmentatio ns          )

        /**
     * Th e end of the     DTD external   subset.
         *
     *     @param augs Additional information t      h     at may i      nclude infoset
     *                             augm         e      ntations .
           *
     * @throws XNIException    Thrown    by handler to signal an erro r.
       */
      public  v   o  id   endExternalSubset (Aug      mentations augs) th      rows XNIE         xception {
        fInDTDExternalSu bset          = false;
                fBas   eURIStack.pop ();
    } // endExt      ernalSubset(Augmentations)

    /**
     * A n internal entit     y declaration.      
             *
        * @param    name               T       he name of the e  ntit  y. Para         me  ter en     tity                n        ames start    with 
                *                      '%', wh   erea   s   the name of a g     ene   ral entity is just   the
         *                     entit    y n      ame.
           *       @param text The value of the entity.
          * @param nonNormal  ized   Text The non-normalized valu    e of the entity. This
     *             valu    e  contains the   same sequence of charact   ers that was   in
                   *                     the int    ernal entity declaration, without any ent    ity
                 *                    references ex    panded.
     *  @param aug   s     Additional information that may in   clude i    nfoset
     *                                    augme                       nta tions.
     *
     *    @throws XNIExceptio     n T          hrown b  y handler to  sig nal an error.
     */
          public void interna       lEntityDecl     (Strin       g n        ame, X                     MLString t     ext,
       XMLString              nonNormalizedTex    t ,
     Augmentations augs)   thro ws XNIException {
  
            if (DEBUG_EVENTS) {
            System.out.prin     tln ("==>inter nalEntityDecl: "+name);
            i f (DEBUG_BASEURI) {
                S    yst    em.out.p  rintln ("         baseURI:"+ (String)fBaseURISt    ac  k.peek ());
                             }
             }
                     //        internal     subset  string
        if (fIn          ter nal   Subs   et != null && !fI      nDTDE      xternalSubset) {
            fInternalSubset.append ("<!ENTIT      Y     ");
                 i  f (name.startsWi         th ("%")) {
                    fInternalSub    set.   append           ("% ");
                   fInt ernalSubs  et.append     (name.     sub  s  tring    (1));
                 }
                 else           {
                fIn    ternalSubset.append (name);
            }   
            fInternal   Subse   t.append            (' ');
                                String v    alue =     nonNormalizedText.t   o       String ();
                 boolean singleQuote =     value.indexOf (       '\      '') == -1;
                       fInternalSubset.append (singleQuote ?   '\''    : '"'     );
                fInternalS ubset.ap pend (value);
               fInternalSu bset.  app  end (s               ingle        Quote ? '\'' : '"');
                      f  Intern     alSub                   set.                           ap pend (">\n");   
             } 
  
            // NOTE   : W    e only know h   ow   to create these nod   es for the Xerce   s
               //       DO  M implementa tion because   DOM Le    v  el 2 d    o     es not specify
        //            that functio n      ality   . -Ac

            // create full node
        / /       don't add parameter e  ntities!
                if(  na  me.star    tsWi th ("%"))
            ret     urn;
                if (fDoc um   entType != null) {
                        Name    dNodeMap en    tities   = fDocumentType.getEn tities ();
               EntityImpl entity = (Entit   yImpl)e  ntities.getNam   edItem (n ame)  ;
            if   (e   ntity ==   null) {
                      entity = (      En        tityImpl)fDocumentImpl.  createEntity (name);
                  en       ti  ty.s   e tBaseU   RI ((String)fBaseURISt  a   c     k.pee  k ());
                             entitie s.setNamedItem (entity);
                   }
        }

         // create d  eferred node
             if (fDocumentTypeInde   x != -1) {
                 boo lean             f      o   u    nd = fal    se;
                      i nt node    = fDe  ferre   dDocumentIm         pl.getLastChil    d (fDocum    entTypeIndex,      f     alse);
             while     (node != -1) {
                        short nodeType = fDeferredDo   cumentIm pl.getNo  deType (node, false);
                                     if (nod     eType == No  de.  ENTIT  Y        _N  ODE)   {
                                 String nodeName = f DeferredDocumentImp l.     getNodeNam e   (node,      fal    se);
                        if (node  Nam     e.equals (name)) {
                                found =   true;
                                       br  eak;
                     }
                         }
                           node = fDeferred             DocumentImp   l.getRealPrevSibling      (node, false);
               }
             if (!found) {
                int entityI      n    dex =
                fDeferre      dDoc  umentImpl     .createDeferr      edEntity (name, null, null, null, (     String)     fBaseURIStack.peek ());
                f  DeferredDo    cumentImpl.appendChild        (fDocumentT   ype     Index, en        tityIndex);
            }
         }

    } // internal    EntityD   ecl(String,XMLString,X                 MLStri  ng)

       /**
     * An ext    e   rnal ent  ity declaratio     n.
     *
     * @   para            m n  ame     The name of t     he en       tity.   Parameter entit  y names start
         *                 with '%', whereas the name of   a general enti  ty is just
        *                       the entity name.
               * @pa r am identifier    An ob   ject containing all location in    formation
        *                                       perti    nent to this notation.
     * @param aug    s Addit       ional informa tion that ma  y inclu de infose t
         *                       augmentations.
     *
     * @throws XNIException Thrown by handler to signa  l an error.
           */
    pu b      lic     void externalEntityDecl          (      String name, XMLResourceIdentifier identifi er,
           A   ugm entations a       ugs) throws XNIE   x         ception {


        if (DEBUG_E    VENTS) {
               Sy     stem.out.pri            ntln ("==>ext    ernalEntityDecl:   "+name);
                         if (DEBUG_   BASEURI) {
                      System.out   .println ("   expandedSystem  Id:"     + identifier.get             ExpandedSystemId ());
                 Sy  s    tem.out.println ("   baseURI:"+ identifier.getBaseSyst     emId      ())   ;
                  }     
               }
                /       / inte   rnal             s   u   bset string
        Str      ing publ icId = identi     fier.     getP    ublicId ();  
        St  ring literalS   ystem Id = identif     ier.getLiteralSystemId  ();
            if (fIn     terna  lSubset != null &&     !fInDTDEx ternalSu  bset) {
               fInternalSubs           e    t.ap     pend (      "    <!ENTITY ");
                   if (name.sta    rtsWith ("%")) {
                            fInternalSubset.a   ppend ("% ");
                                 fInternalSubset.append    (name.substring (1));
            }   
                  else       {      
                         fInter     nalSubset.append     (   name);
              }
            f InternalSubs        et.appen  d (' ');
            if (    pub licId != null) {
                                fInternalS   ub    set.append ("PUBLIC '");
                        fInternal    Sub  set.append (publicId);
                    fInternalSubset.ap       p end ("'            '");
                }
                        else    {
                            fInternal     Subse  t.append ("SYSTEM '");
             }
            fInternalSubset.app end (      literal  Syste    mId);
                f      InternalSub       set.a  ppen      d  ("'>\n");
         }

        // NOTE: We only know how to creat  e these nodes fo r the Xer            ces
        //            DOM implement  ati  on because      DOM Level 2 does         not sp e cify
        /   /       that functionality. -Ac

        // create full         node
           // don't add param        eter entitie    s!
           if(name   .startsWith ("%  "))
             re  turn;
          i  f (fD   ocumen  tTy  pe != null) {
               NamedNodeMap  entities  = fDocument     Type.getEntities ();
                                EntityImpl    entity = (EntityI    mpl)entities.getNamedItem (name     );
            if (entity =          = nu  ll) {
                       entity = (EntityImpl)fDocumentIm    pl.createEnt    it        y (name);
                enti    ty.  setPubli   c      Id (   publicId     );
                        entity.setSyst      emId     (li    tera    lSyste    mId);
                     en     tity.setBaseURI (identifi     er.getBaseSy    stemId   () );
                entities.setNamedItem (entity);
                              }
                }

          // cre     at  e deferre   d node
        if (fD    ocumentT ypeIndex !    =      -1) {
               boolean found = false;
            i  nt node Inde  x = fDeferredDocumentImpl.getLastChild (fDocumentTypeInd  ex, false);
                     w hile (nodeInd  ex != -1) {
                sh   ort nodeTy   p e = fDeferredDocumentImp  l.getNode  Type (nodeIndex, false  );
                         if   (  nodeT   ype == Node.ENT  ITY_NODE  ) {
                    Str   ing n odeN     ame = fDeferredDocume    ntImpl.getNodeName      (nodeIndex, false);
                          if (nodeName. equals (name))    {
                                               found = true  ;
                                    break;
                      }
                    }
                nodeIndex =    fDeferr    edDocume    ntImpl.getR    ealP    rev  Sibling (nodeInd ex, false);
            }
              if (!found) {
                  int entityIndex = fDeferredDo  cumentImpl.create      Deferr    edEnt        ity (
                 name,    publicId, li  teralSystemId, null, identifier.getBaseSystemId ());
                fDeferredDocumentI      mpl.app  endChi   ld (fDocum   entTypeIndex, en   tityIn   dex);
                        }
        }

    } // externalEntity Decl(Str          ing,XMLResource      Ide n      t ifier, Augment   ations)


    /**
     * This method notifies of the start of a par a meter enti ty     . The par  amet            er
     * entity   name sta  rt with a '%' character      .   
     *
     * @par       am nam  e     The   name of the param    eter entity.
     * @par  am identifier The resource i              den tifier.
         * @param encodin    g The auto-detect      ed IANA encoding name of the enti    ty
     *                   stream.     This value     will be null in those situations
      *                    where the entity encod   ing is not auto-det  ected (e.g.     
     *                    internal      parameter en     tities)  .
     * @param      augs Additional information tha  t ma   y inc    lude   infoset
      *                             augmentation  s.
            *
     *         @throws XNIE   xception Thrown   by      h    andler to signal an error.
                    */
           pub    l        ic void st     a   r    tParameterEntit y (String name,
    X   MLRe      s ou  rceIdentifier identifier,
    String encoding,
        Augmen  tations augs) throw     s     XNIExcep  tion {
                    if        (DEBUG_EVENTS) {
                  System.out.println ("=  =>startPar  ameterEntity: "+name);
            if (DEBUG_BASEURI) {
                System.out.printl   n ("   expandedSystemId:   "+identifier.getExpandedSystemId    ());
                   System.ou  t.println ("   baseURI:"+ id  entifier.getBaseSystemId ());
            }
            }
        if (augs != null &&   fInternalSubset !          = null &&
                    !fInD     TDExternalSubset &&
               Boolean.TRUE.equals(augs.get Item(Constants.E  N          TITY_SKI  PPED))) {
            fInternalSubset.appen  d(name).a ppend("; \n");
             }       
        fBaseURIStack.push     (identi   f  ier.ge    t ExpandedSystemId    ()) ;
    }


         /*  *
     * This method not     ifies the end of a parameter entity.  Parameter entity           
     *    names begin with a '%' chara    ct    er    . 
     *
       *   @param name The name of     the parameter e ntity.
     * @     param augs Additional infor    mation that may   i   nclude info   set
       *                                    a  ugment   ati ons.
     *
     * @  throws XNIExceptio       n Thro  wn by h    andler to signal an e rror.
       */  
    publ          ic void end  P  arameterEntit    y (String name, Augmentations augs) throws XNIException {

        if (DEBUG_EVENTS ) {
               System.out.p rintln ("==>endP   arameterEn  tity: "+name);
              }      
        fBaseURIStack.pop ()   ;
    }

    /**
     * An unparse     d entity declaratio       n.
     *
     * @param name      The    name of the entity.  
          * @para  m iden    tifier    An object  containin    g all location information  
     *                         p er        tine  nt to this     entity.
     * @param notation The name of  the notation.
     * @param augs Additional informati   on that may include       infoset
        *                                      augm  entations.
     *
       * @t     hro  ws     XNIExceptio  n T     hrown by handler to signal an      error.
         */
          public void un  parsedE  ntityDecl (String name, XMLResourceIdentifier identifier,
    String notation, Augmentat  ion   s       augs)
      throws XNIE  xcep      tio      n {

             if           (DEBUG_EVENTS) {
            System.out.p       rintln ("==>unparsedEntityD  ec  l: "+na    me);
                         if (DE   BUG_BA   SEURI)        {
                       System   .o  ut.pr  intl    n ("   expandedSystemId:"+ identifier.getExpandedSystemId ()   );
                    System    .out.println ("   ba  s   eURI:"+ ide      ntifi  e   r.ge   tBas eSystemId ());
               }
           }
        //     inte    rnal subset string
            String publicId = identifier.getPubli  cId ();
        String literalSystemId = identifier.getLiteralSystem Id ();
           if (f InternalSubs     e        t != null && !fInD TDEx  ternalSubset) {
            fInternalSubset.          append ("<!EN  TIT    Y ");
            fInternalS   ubset.append (name);
                  fInterna      lSubset.append ('  ');
            i  f (pu   blicId !=     null) {
                    fIntern       alSubset.append  ("PUBLIC '");
                f      InternalSub  set.a  ppend    (pub     l   icId);
                           if (literalSystemId != null) {    
                        fInte  rnal      Subse           t.appe     nd ("' '");
                        fI   nternalSub  set.append (literalSystemId);
                    }
               }
                 else {
                   fInternalSubset.append       ("SYSTEM '    ");
                fInternalSubset.append (litera   lSystemI d);     
             }
                     fInternalSubset.append (  "' NDATA ")   ;
              fInternalSubset.append (no tation);
                           fInte  rnalSu  bset.append (">\n");
        }
    
        // NOTE: We only  know how to create these nodes       for the Xerces    
        //       DOM implement     ation bec     ause DOM Level 2 does not specify
        //         that functionality. -A    c

        // create full node
               if (       fDo cume       ntType !=     nul   l) {
                     NamedNodeMap entities = fDo      cument     Type.getEnt    ities ();
               Entity  Impl         entity = (Entit    yI  mpl)entities.getNamedItem (name);
            if (       en       tity == n  ull) {
                     entity = (En              ti  tyImpl)fDo   cumentI mpl.createEntity (nam      e    );
                  entity.setPublicId (public Id);
                entity.s   etSystemId (lite  ralSystemId)    ;
                                       entity.setNotationName (notation   );
                   e     ntity.setB   aseURI (identi     fi         er       .ge   tBaseSystemId ()   );
                      entities.setNa medItem (entity)         ;
                 }
        }

        // create deferred node
        if (fDocum      entType  Index != -1) {
            b    oolean   found = false;
                int no   deI  ndex = fDeferredD        ocumentIm    pl.getLastChild (fDocumentTyp   e  Ind ex,  false);
                while (nodeI   ndex    != -1) {  
                    short nodeTyp   e = fDeferredDocumentImpl.getNodeType (nodeIndex, false);
                   if (no    deType     == Node.E NTITY_NODE)      {
                      String nodeName   =  fDeferredD      ocumentImpl.getNo deName (nodeIndex, false);
                    if (nodeName           .equals (name )) {
                        found = true;
                               break;
                         }
                } 
                nodeIndex = fDeferredD      ocu   mentIm     pl.getRealPrevSiblin g    (nodeIn   dex, fa lse        );
            }
                 if (!found) {
                  int entit         yInd   ex =      fDeferredD           ocumentImpl.createDeferredEntity (
                   name                , publicId, liter   alSystemId, notation, identifier.getBaseSystemId ())   ;
                fDe   ferredDocumentImpl.ap     pendCh     ild (fDocumentType    Index, entityIndex  );
             }
               }

    } // unparsedEntityDecl(String,XMLResourceIdentif    ier, String, Augmentations)

       /**
     *    A not    ation declaration
        *
     * @para    m n ame      The name        of the notation.
     * @par   am id    entifier    An ob   ject c    ontaining a   ll location   information
     *                           pertinent to this notatio   n.
     * @para          m augs Addition al inf    orm     at  ion   that may include inf    oset
     *                      augmentations.
     *
     * @throws XNI   Exception Thrown by handler to signal an erro    r.
     */
    p     ublic vo id    notationDecl (Stri  ng name, XMLResourceIdentifier identifier,
    Augmentat    ions augs) throws XNIException {

        // i   nternal subset string
        String publicId = identifier     .getPu  bli   cId ( );
        Stri         ng lit   eralSystemId = identifier.getLiteralS  ystemId ();
            if (fI n  ternalSubset != null && !fInDTDEx    ternalSubset) {
               fInternalSubset. append ("<!NO   TATION ");
                fInternalSu       bset.ap  pend (name);
             if (p  ubl    icId != nul  l) {
                fIn   terna lSubset.append (" PUBLIC '");
                       fInte     rnalSubset.append (publicId);
                if (literalSystemI     d      != nu  ll) {
                     fInternalSubset.append (    "' '");
                       fInt ernalSubset.append (l  iteralSystemId);
                 }
                       }
            else {
                fInternalSubset.append   (" SYSTEM '");
                      fInternalSubset.append (literalSystemId);
            }
               fInterna  lSubset.ap     pend ("'>\n");
         }

             // NOTE: We only know  how to create these    nodes for     the Xerces
                //          DOM implementation       because DO   M Level 2 does not specify
            //             that functionality. -Ac

           // create full node
        if (fDocumentImpl !=null && fDocumentType != null) {
                 NamedNodeMap notations = fDocumentType.getNot      ations ();
             if (notations.getNam    edItem (name) == null)    {
                Notat      ionImpl notation = (NotationImpl)fDocumen  tImpl.createNotation  (name);
                nota     tion.setPublicId (publicId);
                    notation.setSystemId (literalSyst e     mId);
                        notation.setBas eURI  (identifi   er.getBa    seSystemId ());
                      notations.setNamedItem (notation);
                 }     
               }
  
          // create de    ferred no de
        if (fDocum   entTypeIndex != -1)    {
            boolean found = fal     se;
            i    n t nodeIndex = fDeferredDoc  ume   ntImpl.getLastChild   (fDocumentTypeIndex, false);
               while (nodeIndex != -1) {
                  short nodeType = fDef erredDocumentImpl.getNodeType   (nodeIndex     , false);
                  if (nodeType == Node.NOTATION_NODE   ) {
                        String n   odeName = fDeferredDocumentImpl.getNodeName (nodeIndex, f   alse);
                    if (node      Name.equals (name)) {
                             found = true;
                         break;
                                             }
                }
                      nodeIndex     =   fDeferredDocumentImpl.getPrevSibling (nodeIndex, false);
             }
            if (!found) {
                 int notat    ion    Index = fDeferredDocumentImpl.createDefe        rredNotati       on (
                name, publi       cId, literalSystemId,   ide    ntifier.getBaseSystemId ());
                  fDe ferredDocumentImpl.appendChild (fDocumentTypeIndex, notationInde  x);
             }
           }

    }     // notationDecl(String,XMLResourceIdentifier, Augmentati  ons)

    /**
     * C  haracters within an IGNORE conditional section.
     *
        * @param text The ignored t    ext.
     *  @param au gs Additional information that may include  infoset
     *                      augmentations.
     *
     * @throws XNIException Thro wn by handler to signal an er ro     r.
     */
    public void ignoredCharacters (XML      Str   ing text,    Augme    ntations augs) thr ows XNIExcepti on {
    } // ignoredCharacters(XMLString, A  ugmentations)


    /**       
     * An elem ent decla    r   ation.
           *
     * @param name         T     he name of the element.
     *           @param contentModel The element content model.
                    * @p  ara     m augs Additional information that may include infoset
     *                         augmentations.
     *
            * @thro   ws XNIException Thrown by  handler  to signal a  n error.
     */
    pu   blic void e    lementDecl (String name, String con  tentModel   , A        ugmentations augs)
    t   hrows XNIException {

        // inter   nal subset string 
        if (f  InternalSubset != null && !fInDTDExternalSubset) {
                    fInternalSubset.append ("<!ELEMENT ");
            fInternalSubset.append (name);
               fInternal    Subset.append (' ');
            fInter      nalSubset.append (contentModel);
               fInternalSubset.append (">\n"  );
         }

    } // elem    entDecl(String,S  tring)  

         /**
     * An at     tribute de     claration.
     *
     * @para  m elementName   The name of the element that this attribute
     *                         is associated with.
       * @param attri  buteName The name of the attribute.      
           * @param     type          The attribute type. This v    alue will     be one of
     *                          the follo wing: "CDATA", "ENTITY", "ENTITIES",
            *                         "ENUMER    ATION", "ID", "IDREF", "IDREFS",
         *                                     "NMTOKEN", "NMTOKENS  ",   or         "NOTATION".
     * @param enumerat  ion   If the type has the value "ENU     ME    RATION" or
     *                              " NOTATION", this array holds   the allowed attribute
          *                      values; otherwis  e, this array is null.
       *     @param defaultType   The attribute default type. This val   ue will be
     *                      one of the fol    lowing: "#    FIXED", "#I     MPLIED",
     *                              "#REQUIRED", or null.
     * @param defaultValue     The attribute def     ault value, or null if no
     *                      d     efault value     is specified.
     * @param nonNorm alizedDefaultValue  The attribute default va        lue with no norma     lization
     *                             performed,     or null if no default value is sp     ecified.
                * @param augs Additional    information tha   t may include infoset
          *                             augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void attributeDecl (String elementName, String attr      ibuteName,
    String ty    pe,  String  [] enumeration,
         String default   Ty    pe, XMLString defaultValue,
    XMLString non   Normali     zedDefaultValue, Augment ations augs)     throws XNIException {

        // internal subset string
           if (f    InternalSubs              et != null && !fInDTDExternalSubset) {
            fInternalSubset.append (   "<!ATTLIST ");
            fInt ernalSubset.app    end (elementName);
            fInternalSubset           .append (' ');
              fInternalSubs et.append (attributeName);
            fInternalSubset.append (' ');
            if   (type.equals ("ENUMERATION")) {
                     fInternalSubset.append ('(');
                  for (int i = 0; i < enumeration.length;   i++) {
                    if (i > 0) {
                            fInternalSubset.append ('|');
                               }
                    fInternalSubset.append  (enumeration[i]);
                }
                   fInternalSubset.append (')');
                }
            else {
                   fInternalSubset.append (type);
                }
            if (defaultTyp        e != null) {
                fInternalSubset.append (' ');
                fInte   rnalS    ubset.append (d    efaultType);
            }
            if (defaultValue  != null) {
                fInternalSubset.append (" '");
                for (      int i = 0; i < defaultValue.len  gth; i++) {
                    char      c         = defaultValue.ch[defaultValue.offset + i];
                        if (c == '\'') {
                            fInternalSubset.append ("&apos;");
                    }
                    else {
                             fInternalSubset.append (c);
                        }
                   }
                fInternalSubset.append ('\' ');
                       }
                fInter   nalSubset.append (">\n");
        }
          // REVISIT: This code applies to the support of domx/grammar-access
        /  / feature in Xerces 1

            // deferred expa    nsion
        if (fDeferredDocumentImpl != null) {

            // get the   default value
            if (de       faultValue != nu      ll) {

                // get  element definition
                int elementDef  Index  = fDefe rredDocumentImpl.lookupElementDef  inition (elementName);

                 // create element definition if not already there
                if (elementDefIndex == -1) {
                    elementDefIndex = fDeferredDocumentImpl.createDeferredElem         entDefinition (elementName)   ;
                    fDeferredDocumentImpl.appendChild (fDocumentTypeInd      e x, elementDefIndex);
                }
                //  add default attr  ibute
                   boolean nsEnabled = fNamespaceAware;
                String namespac    eURI = null;
                i  f (n   sEnabled) {
                          // DOM Le     ve      l 2 wants all namespace declaration attrib    utes
                    // to be bound to "http://www.w3.org/2000/xmlns/"
                    // So     as long as the XML p    arser doesn't d    o it, it needs to
                               // done he  re.
                      if (attributeName.startsWith(    "xmlns:") ||
                        attributeNam e.equals("xmlns")) {
                        n    amespace   URI = NamespaceContex      t    .XMLNS_URI;
                    }
                       else if (attributeName.startsWith("xm   l:")) {  
                        namespaceURI = Names    paceContext.XML_URI;
                    }
                     }
                int attrIndex = fDeferredDocumentImpl.createDeferr    edAttribu    te (
                                attributeNam     e, namespaceURI, defaultValue.toString(), false);
                if ("ID".equals (type)) {
                    fDeferredDocumentImpl.setIdA  ttribute (attr     Index);
                }
                 // REVISIT:     set ID type correctly
                       fDeferredDocumentImpl.appendChild (elementDefIndex, attrIndex);
            }

        } // if deferred

        // full expans  ion
        else if (fDocumentImpl != null) {

            // get the defaul      t value
            if (defaultValue != null) {

                // get element definition node
                   NamedNodeMap elements = ((DocumentTypeImpl)fDocumentType).getElements ();
                ElementDefinitionImpl    elementD    ef = (ElementDefinitionImpl)elements.getNamedItem   (elementName);
                if (elementDef == null    ) {
                      elementDef = fDocumentImpl.createElementDefinition (elementName);
                       ((DocumentTypeImpl)fDocumentType).      getElements     ().setNamedItem (elementDef);
                  }

                // REVISIT: Check for uniqueness     of element name? -Ac

                // create attribu te and set properties
                boolean nsEnabled = fNamespaceAware;
                AttrIm  pl attr;
                if (nsEnabled) {
                    String namespaceURI = null;
                                     // DOM Level 2         wants all namespace declaration attributes
                       // to be bound to "http://www.w3.org/2000/xmln     s/"
                    // So as long as the XML parser doesn't do it, it needs to
                    // done here.
                    if (attributeName.startsWith("xmlns:") ||
                        attributeName.equals("xmlns")) {
                        namespaceURI = NamespaceContext.XML  NS_URI;
                       }
                           else if (attributeName.startsWith("xml:")) {
                        namespaceURI = NamespaceContext.XML_URI;
                        }
                       attr = (AttrImpl)fDocumentImpl.createAttributeNS (namespaceU   RI,
                    attributeName);
                }
                    else {
                    attr = (AttrImpl)            fDocumentIm     pl  .createAttr      ibute (attribu     teName);
                }
                attr.setValue (defaultValue.toString ());
                attr.setSpecified (false);
                     attr.setIdAttribute ("ID".equa   ls (type));

                // add default attribute to element definition
                if (nsEnabled){
                    elemen tDef.getAttributes ().setNamedItemNS (attr);
                }
                else {
                    elementDef.getAttr  ibutes ().setNamedItem (attr);
                }
            }

        }      // if NOT defer-node-expansion

        } // att  ributeDecl(String,String,String,String[],String,XMLString, XMLString, Augmentations)


    /**
     * The start of an attribute list.
     *
     * @param elementName The name of the element that this attribute
     *                    list is associated with.
     * @param augs Additional information that may include infoset
     *                         augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    pub     lic voi  d startAttlist (String elementName    , Augmentations augs) throws XNIException {
    } // startAttlist(String)


    /**
     * The end of an attribute list.
     *
     * @param au       gs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIExc   eption Thrown by han     dler to signal an error.
     */
    public void endAttlist (Augmentations augs)    throws XNIExcepti on {
       } // endAttlist()


    // method to    create      an element node.
    // subclasses can override this method to create element nodes in other ways.
    protected Eleme  nt createElementNode (QName element) {
        Element el = null;

        if (fNamespaceAware) {
            // if we are using xerces DOM i       mplementation, call our
            // own constructor to reuse the strings we have here.
            if (fDocu      mentImpl != null) {
                el = fDocumentImpl.create      ElementNS (element.uri, element.rawname,
                element.localpart);
            }
            els    e {
                 el = fDocument.createElementNS (element.uri,   element.rawname);
            }
        }
        else {
                el = fD   ocument.cr     eateElement    (element.rawname);
        }

        retu  rn el;
      }

    // method to create an attribute node.
    // subclasses can override this method to create attribute nodes in other ways.
    protected At    tr createAttrNode (QName attrQName) {
         Attr attr = n    ull;

        if (fNamespaceAware) {
            if (fDocumentImpl !=  null) {
                // if we are usin  g xerces DOM implementat   ion, call our
                // own constructor to reuse the strings we have here.
                attr = fDocumentImpl.createAttributeNS (attrQName.uri,
                attrQName.rawname,
                attrQName.localpart);
            }
            else {
                 attr = fDocument.createAttributeNS (attrQName.uri,
                attrQName.rawname);
            }
        }
           else {
            attr = fDocument.createAttribute (attrQName.rawname)    ;
        }

        return  attr;
    }

    /*
     * When the firs      t characters() call is received, the data is stored i n
     * a new Text node. If right after the first characters() we receive another chunk of data,
     * the data from the Text node, following the new characters are appended
     * to the fStringBuffer and the text node data is set to empty.         
     *
     * This  functi    on is called when the state is changed and the
     * data must be appended to the cu  rrent node.
     *
     * Note: if DOMFilter is set, you must make sure that if Node is skipped,
     * or removed fFistChunk must be set to true, otherwise some data can be lost.
     *
        */
    protected          void  set   Char     acterData (boolean sawChars){

        // handle character data
            fFirstChunk = sawChars;


        // if we have data in the buffer we must have created
        // a text node already.

        Node child = fCurrentNode.getLastChild ();
          if (child != null) {
            i    f (fStringBuild     er.length () > 0) {
                // REVISIT: should this check       be performed?
                if (child.getNodeType () == Node.TEXT_NODE) {
                        if (fDocumentImpl   != null) {
                        ((TextImpl      )child).replaceData (fStringBuilder.toString ());
                           }
                       else {
                        ((Text)child).setData (fStringBuilder.toString ());
                    }
                }
                    // reset string buffer
                fStringBuilder.     setLength (0);
            }

            if (fDOMFilter !=null && !fInEntityRef) {
                if ( (child.getNodeType () == Node.TEXT_NODE ) &&
                ((fDOMFilter.getWhatToShow () & NodeFilter.SHOW_TEXT)!= 0) ) {
                    short code = fDOMFilter.acceptNode (child);
                    switch (code) {
                        case LSParserFilter.FILTER_INTERRUPT:{
                                     throw Abort.INSTANCE;
                        }
                        case LSParserFilter.FI    LTER_REJECT:{
                            // fall through to SKIP since Comment has no children.
                        }
                        case LSParserFilter.FILTER_SKIP: {
                            fCurrentNode.removeChild (child);
                            return;
                        }
                        default: {
                            // accept node -- do nothing
                        }
                    }
                }
            }   // end-if fDOMFilter !=null

        } // end-if child !=null
    }


    /**
     * @see org.w3c.dom.ls.LSParser#abort()
     */
    public void abort () {
        throw Abort.INSTANCE;
    }


} // class AbstractDOMParser

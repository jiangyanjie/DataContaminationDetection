/*
 * Copyright    (c) 2007, 20    17, Oracle  and/o       r its affilia   tes. All rights reser     ved.
 * ORACLE PROPRIETA      RY/CONFIDENTI    AL.     Use is   subject to license terms      .
 */
/*
  * Copyright 2001-2005     The Apache Software Foundation.
   *
     * Licensed      under t      he Apache        License,     Version 2.0 (t   h e "License" ) ;
 * you may not use  th     i            s file e      xcept in complia   nce w              ith the License.
 * You may obtain a copy of the     L    icen  se at
 *
 *      http://www.apache.or        g/   lic     enses/LICENSE-2.0
 *
 *  Unless r equired by appli  ca     ble law   or agreed to in writing,     so  ftware
 * distr      i  buted under the        Lic   ense   is distributed on            an "AS I           S"    BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,     either express or implied.
 * See the License for the specific la nguage governing permissions an   d
 * limitati  ons under the Lice  nse.
 */

package com.sun.org.apache.xerces    .internal.p  arsers;

import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.int  ernal.util.EntityResolver2Wrapper;
import com.sun.org.apache.xerces.internal.util.EntityResolverWrapper;
import com.sun.org.apache.xerces.internal.util.ErrorHandlerWrapper;
import com.sun.org.apache.xerces.internal.util.SAXMessageFormatter;
import com.sun.org.apache.xerces.internal.util.Status;
import com.sun.org.apache.xerces.internal.u  til.SymbolHash;
import com.sun.org.apache.xe       rces.internal.util.XMLSymbols;
import com.sun.org.apache.xerces.internal.utils.XMLSecurityMa      nager;
import com.sun.org.apache.xerces.internal.xni.Augmentations;
import com.sun.org.apa che.xerces.internal.xni.NamespaceContext;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
import com       .sun.   org.apache.xerces.internal.xni.   XMLLocator;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.XMLString;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationExcep    tion;
import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
import com.sun.org.apache.xerces.internal.xni.pars    er.XMLInputSource;
import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
import com.sun.org.apache.xerces.internal.xni.parser.X     MLParserConfiguration;      
import com.sun.org.apache.xerces.internal.xs.AttributePSVI;
import com.sun.org.apach  e.xerces.internal.xs.ElementPSVI;
import com.sun.org.apache.xerces.internal.xs.PSVI      Provi    der;
import java.io.IOException;
import java.util.Locale;
import javax.xml.XMLConstants;
import org.xml.sax.Attrib  uteList;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.Doc  ume     ntHandler;
import org.xml.sax.   EntityResolver;
import org.xml.sax.ErrorHandler ;
import org.xml.sax.InputSource;
import org.xml.sax.P   arser;
import org.xml.sax.SAXExce  ption;
import org.xml.sax.SAXNotRecognizedException;   
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.Attributes2;
imp  ort org.xml.sax.ext.  DeclHandler;
im  port org.xml.sax.ext.EntityRe  solver2;
import org.  xml.sax.ext.LexicalHandler;   
import org.   xml.sax.ex t.Locator2;
import or    g.xm l.sax.helpers.Lo    c        atorImp    l;

/  **
 * This is the base class of all S   A     X parsers.  I t implements both the        
 * SAX    1 and SAX2 parser functi onality,     while the actual pipe l  ine is
 *     defined in the parser configuration.
 *
 *       @author Arn    aud L     e Hors, IBM
 * @author Andy  Clark, I      BM 
    *
 * @ver      sio            n $Id: Abst            ractS     AXPar  ser.java,v 1.6 2010-11-0      1 04:40:09 j  oehw Exp $  
 */
public abstract class  AbstractSAXP arser   
       extends    AbstractXMLDocumentP arser
      implement         s PSVIProvide     r, /     / PSVI
                              Parser,   XMLReader // SAX1  , SAX   2
   {              

    //
              // Constant    s
     //

    // features

           /** Feature         ide         ntifier: n  amespaces. */
    prot   ected static final String     NAMESPACES =
                   Constants.SAX     _FEATURE_PR E       FIX + Co       ns  tants .NA    MESPACES_FE    ATU   RE;

          /*            * Feature identifier: namespace prefixes. */
         protected static final String NAMESPACE_P  REFIXES =
        Constants.SAX_     FEATURE_PREFIX        + C onstants.NAMESPACE_PREFIXES_FEATURE;

          /**    Feature id: string interning. */
         protect   ed static final Str   ing STRING   _INTERNING =
        C onstants.SAX_FEA        TURE_PREFIX + Constants.ST  RING_I     NTE   RNING_FEATURE;  
  
    /** Feature identifier: all o    w notatio  n    and unparsed entity events to b        e sent out of or  der.   *      /    
    // th    i       s    is not   mea   nt to   be a r  ecognized     feature, but we need i t h    er   e to use
       // if it is     already a rec   ognized    feature   for the pipeline  
         prote  cted s   tatic f inal String ALLOW_UE_AN  D_N  OTATION_EVENTS =
        Con            stants.SAX_FEATURE_PR       EFIX + Constants.ALLOW_DTD_EVENTS_AFT   ER_ENDDTD_FEAT   UR       E  ;
  
    /** Recogniz  ed feature    s.       */
          private st      a  tic  final Stri                 ng[] RECOGNIZED_FE     ATURES = {
        NAMESPACE    S,
         NAMESPACE_PREFIXES,
           STR    I  NG_INTERNING,
    };

    // proper  ties

    /*  *   Property id:     l     exical handler. */
    protected sta  tic final String LEXICAL_HANDLER =
              Constants.SAX_PR     OPERT   Y_PREFIX + Constants.LEX ICAL_HANDLER_P    ROPERTY;
   
      /** Property id: declaration handler. */
            protected static fina   l    Stri    ng   DECLARATIO  N_H  ANDLER =
           Co      nstants.SAX_PROPERTY_PREFIX   + Cons      tants .DECLARATION_HANDLER_PROPE  RTY;
   
     /**       Prop          erty id: DOM node. */
       pro   tected static final String DOM_NODE =
                      Constant     s.SAX_PR    OPERT  Y_PR    EFIX + Constant     s.DOM_N ODE_PRO   PERTY;

    /** Pr operty id: secur   ity manager. */
          pr ivate s tatic final String    SECURITY_MANAGER =
            Constant s.XERCES_PROPERTY_PREFIX +         Con   stants.SECURITY_MANAGER_PROPERTY;

         /** Reco    gnized p      roperties. */
    private static final S    tring[   ]   REC     OG    N      IZED_ PROPER  TIES   = {
        LEX    IC   AL_HANDLER       ,
           DECLARATIO   N_HANDLER,
        DOM_NODE,
      };

                      //
    // Data
    //

           // features

    /** Namespaces. */
    prot ected boolean fNamespa     ces;

           /** Namespace    pref  ix    es. */
    protected      boo   lean fNamespacePrefixe    s =   false;

    / ** Lexical handler para   meter e   n     tities. */
           protected boo        lean fLexicalHandlerPa             ramet  erEnt  i    tie     s = true;

    /** Standalon    e      document declara  tion. */
      prot      ecte     d boolean fStandalone        ;

    /** Resolve D   TD URIs . */    
     protected b oolean fR     esolve    D  TDUR  Is = true;

    /  **   U    se      En tityResolve      r2. */
    protected boolean     fUseEntityResolver2 = true;
 
    /**  
               *       X          MLNS URIs: Namespace  declaratio n   s in the
     *   http://ww    w.w3.or     g/2000/xmlns/ namespace.
      */
      protected     boolean fXMLNSUR Is = false;

    /        / parser handlers

        /** Co        ntent handler. *   /
    protected ContentHandler fContentHandler  ;  

    /**     Document ha   ndler. */
    protected DocumentHandler fDocumentHa          ndler;
   
    /** Na       mespa     ce  context */
    prote    cted Namesp       ac    eCo      ntex  t fNamespaceConte    xt;

           /** DT  D handler. */
    protected  org.xml.  sax.DTD   Handler   fD  TDHandler   ;

    /** D   ecl handler. */
    protected De   clH   a  ndle   r fDeclHandler;

       /** Lexic       al handler  . */
          protected      LexicalHandl       er fLexicalHandler;

      protec   ted Q  Name   fQName    = new QNa      me();

    // st   ate

         /**
     *       True if a pars  e is in   p  rogress. This st     a  te is needed          because
                    * some      fea        tures/prop    erties    cannot be set whil   e parsi   n   g (e.g.
     *    validati    on and name                  spac  es)    .
        */
    protec       ted boolean fParseInProgre    ss =  false;

    // track the ver   sion of th e doc    ume   nt      bein   g     pa   rsed
      protected S    trin  g fVersion;

        //  temp vars
    pr ivate final           Attri              butesPr   oxy f              Attribu             te    s    Prox        y =    new  A   t      t r     ibutesPro             xy   (  )    ;
    privat  e Augme     ntations fAugmenta t  ion    s     = null   ;


    /   /           t  em     p  orary buffer for         se    ndin g normali           zed values
             // REVIS    IT: what shoul   d     be the size of the buffer?
    p  rivate st       atic fin  al int BUFFER_SI ZE = 20;
          pri    vate char[] fCha        rBuffer =  new char[BU      F   FER_SIZ   E];

          // allo ws us t    o keep track of w         hether an a           tt      r     i  bute      has
      // been     d   eclared     twice, so             that         we    can avo    id e     xposing t  he
      //    second decla ratio    n         to any         r          eg ister ed Dec     lHand    le   r
       protect    ed         Symb olHash fDec   lare   dAttrs = null;

    //
         /    /     Co    nstructors
    //    

    /**   Default const  r      uctor. */
     p   rote   c   ted              A  bs  tractSAXParser(XMLPa  rse    rCo   nfi      g    urat  ion    c   onfig) { 
              super(conf   ig);

           con        fig      .addRecogn   i z     ed      Featu  r es(R      ECOGNI  ZED  _FE  ATURES)     ;
        c   onfig.addRecognizedPrope     rti       es(REC    OGN IZED_PROPERTI  ES      );

        tr    y {
            confi   g  .s      etFeat    ur   e(ALL        OW_  UE  _AND_NOT       AT     ION_EVENTS, f       alse);
              }
             ca   tc   h (X     MLConfigurati    onException             e  )   {      
                          //   it wasn't    a r ecogniz   ed     fe  atu   re, so we  don't wor    ry a  bout it
                                       }
    }       // <i        nit>(XMLParserC     onfigurat    io    n)

    //     
    // XM          LDoc      um  entHand  ler methods
         //

       /**
     * Th e s tart of t                          he         doc  umen      t.
     *
            *        @    par am lo    cator The             document locator, or       null if t  he doc       ument
     *                                      l   oc   a   tio         n cann   ot be     repo    rte  d du        ri    ng the parsing
     *                                                   of thi     s docum en     t.   Howev  er,   it i    s    <e  m>strongly</em>
          *                           re  com    mended that a locator be supplie        d  t   hat  can
         *                                   at leas  t report the s   ystem ident     ifier of the
       *                               document.
       * @param encoding The a         ut   o-dete       cted I   ANA encodin  g nam      e         of the                      en          tity
       *                    s      tream. This va    lu  e         will be null           in t      hose situati   ons
             *                           where  the     entity encoding is     not auto-detec t     ed (e.g.
                   *                           internal entit ies o            r a d ocument entity   that    is
      *                                  parsed from         a java.io.Reader  ).
       * @para  m namespaceContext
                *                          The namespace c     on  text     in effec             t      at    the
          *                      st art    of th        i    s d  o     cu    me  nt.
     *                         This o   bject        represent   s the curr e          nt cont    ext.
     *                        Imple mento    rs         of this cl           ass     are re     sp   onsible           
                   *                             for copying the namesp  a       ce    b            inding  s      from the
     *                  the current conte   xt (and its                paren             t con     texts)
       *                         if that information is        im  p  ortant     .
     *     @par am  augs     Additiona  l in          formation that may     inc        lude infos   et au      gmentat      i     on    s            
     *
         * @throws      X   NIE     xception Thr   own    by h   andler        to signal an err      o     r.  
          *   /        
    public void st    artDocument(XMLLocator             lo  cator,    String         encoding,   
                                                NamespaceContex   t names  p           a      ceConte   x  t , Augm        entati   ons aug    s)
        th    r o    ws XNIEx         cept   io                      n {

           fN      ames         pace       Context =   namespaceCon    text;

              try                     {
             // SAX1
                 if  (fD    ocumentHandler != nul    l) {         
                          if (loc  ator !     = nu         ll) {   
                                 fDoc     u   ment Hand        ler.setDocument   L  ocator(ne w             Loca    tor  Proxy(locator));
                                 }
                              fDoc umen  tHand     l   er.s   tartDocument()  ;
            }

                      // SAX2
                  if (fContentHandler      !=       null)   { 
                             if (      locator != null) {
                                            fContentHandler.set    Doc       umen      tLocator(new LocatorProxy(loca   to                r ));
                        }
                fContentHandler.startD  ocument      ();
                 }      
         }    
         catch (SAXE  x    ceptio n e     ) {
               t hrow new  XNIException(e    );
          }
  
              } //    st  artDocu    ment     (locator  ,e     ncoding,a  ugs)

           /*  *
     * N   otifies      of     the   pres  en     ce    of an XML   Decl line i n   the document. If
      * presen  t,    this         method    wi       ll be      c  alled immediately   follo  wing the
     *  startDocumen         t c     all.
        *
     * @param v ersion    The XM      L version.  
        *  @param e       ncod  ing   The    IANA encodin     g  name of th    e document, or null   i    f
     *                              not specified.
                    *  @         param standalone The sta ndalone         value, or null  if not        specifi  ed.
     * @param a    ugs   Addit      ional in    formation t  hat may incl      ude     info     set augme     ntati               o  ns       
           *
             *         @throws           XN           IExcept          ion Thrown by handler to    si g nal an error. 
     */
         public voi  d xmlD  ecl(   St        ri ng     versi     on, Str   i  ng   e   ncoding    , String stan             da     lone,      Augme  ntatio ns a   ugs)
                        throws XNI  E   x ception {
                           //      th    e     version             need o    nly be set o       nce;     if
                  // docum ent's   XML 1.0|1.  1, that's   ho      w    it     'l   l   s     tay
            fVersion = ver       si on;
        fStan    dalon   e = "yes".equals(standal         one);
            } // xmlDecl(String,    String,String)
  
    /*   *   
     * N   otifie  s            of t  he presence    of the DOCTYP E l ine in   the     docume   nt.
        *
     * @param rootE      lement    The     name    of           t  he     root element .
     * @param publicId            Th    e public identifier if an    ex    ternal DTD or nul     l
           *                                                  i            f    the external DTD     is specified    using SY    STEM.
     *    @  param sy     stemId    The s  ystem       ident     ifier i   f an   exte            rna    l DTD, null
                              *                      otherwise.      
        *         @param augs         Addi   tional i  nform  ation that m  ay include info  set augmentat             ion         s
            *
     * @      throws XNIException    Th  rown by handler to signal an  error.
     */
    public   void doctypeDecl(   String rootEle           m  ent,
                                                S     tring publicId, String syst                           emId, Aug             mentations   aug s)
                   throws XN  IExc   e    ption   {
              fInDTD = true;

               try   {
               // SAX2    e     xt  e      nsio    n  
                   if (fLex icalHa ndler !=  nu     ll)  {
                   f   LexicalH       andler.star                       tD    TD(roo          tE    lement, publicId, systemId); 
              }
                   }
        catc    h (SAXException e)               {
            t       hrow new XNIException(e);    
               }

                     // i  s t   here  a DeclHand   ler ?
                if(fDeclHa  ndler   !      = n            ull) {
                         fDecl   are         dAttrs     =     new  Symb         ol     Hash();
            }

    } //             doctypeD  ecl(String,Stri               ng,S             tring)

                /**    
         *   Th   is me  thod not     i   fies of        the sta         rt   of   a n entity. The DTD has th     e
        * pseudo-name of "[dtd]" p     arame    te     r entity n    ames        start     wi th '%'; and
     * ge   neral entit y na  mes      are just the      enti     t y na    me.
             * <p>
             * <stron  g>Not   e:</stron    g>    Since the document    is an    enti  ty, the handler
       *    will be notif ied of the start of t          he       d  ocum   e     nt entity by call    i   ng the
              *   startEntity      m       ethod with      th    e      e            nti    ty n    ame "[xml]   " <  em>before</e     m> c  all   ing 
     * the star     tDocument           method. Wh   en e     x  posi            ng   entity     boun  daries t         hrough the
           * SAX AP    I, the doc       um     ent en                  tity is never r    epor     ted     , however.
       * <p  >
                              * <            stron g>N     ote:</stron  g> T     his metho   d   is      not ca        lled for entity         r           e fe   re    n   ces
     * a   pp    earin  g as pa     r t of att            ri         b  ute value                    s.
                            *
     * @  param name     The    n  ame of     th       e ent ity.
     * @param  i   dentifier Th   e r   esour  ce i de ntifier.
                    *      @param encodi    ng    The         auto- d   etected IANA encoding        name of t  he ent  ity
     *                                 s    tr ea   m. This v    al    ue wil      l be     null i      n t  hose situa ti     o    ns
      *                       where           t     h   e entity e        n            cod ing     is                   not auto-detected (e.g.
      *                        internal p       a       rameter     entities).
              * @param  au   gs     Additional in   f         ormatio  n     that may    inclu        de infose     t augme           n         tation     s
       *
       * @t         hrows X  NIExce ption Thr   own    by handler            to signal an           er  ror.
               */
         public  void star       tGener   alEntity(String n   ame    , XMLResour   ceIde   ntifier id    e     ntifie   r    ,
                                                                               S     tring encoding, Au gmentation s a   ugs)
        th   rows XNIException {

        try {
                    // Only    repor  t start       Entity i   f    t his ent   ity              was act   ua        lly re ad.
                           if (   augs !=   null &&       Boo  lean.    TRUE.equ als(augs.getItem(Con    stants.ENTITY  _S         KIPPED))) {
                          // repo  rt sk    ipp     ed e               ntity t      o con   ten  t     handler
                                   i     f    (fContentHand    l        er !        = null) {
                              fCon     tentHand   l         er.skippedEnt     ity(name    );     
                                 }
                                }
                     e  lse {
                                //      SA  X2   extensi             o     n
                                if (fLexicalHandler !=    nu          ll) {
                                                   fLexi calHand  ler.star  tEntity(n    ame)       ;
                                }
                   }
                    }
             ca   tch (S  AXExcepti                           on e ) {
                                  th      row      new XNIE                xception(e);
                }
   
       } // startGene         ralEnti  ty(Strin   g,String   ,String,S   tring,S   tr    ing)

    /**
     * This   meth       od no      tif       ies the end of an   ent   ity   . T  he     DT  D     has the p      seud  o-na   me
              *          of "[dtd]" para                      meter      entity names                       start wit  h     '%';        a    nd gen  eral entity       
           *  names a   re just the        entit    y          name.     
     * <p>
         * <stron     g>Note     :</s         trong> S     ince    t he     document is a n enti         ty, the hand   l         er
        *  wil   l be notif   i       ed   of the  end of   the document en  t ity by c  al          ling the
     * endEntity m    ethod wi  th      the en tity na   me  "[    x   ml    ]"  <em>after</em> cal  ling  
         * the      endDoc   ument meth   od. Wh      en exp osin  g    ent      ity boundaries   t h rough t    he
     * SAX API,     the docume nt entity is never r  e  po    rt  e     d, how          ever.
          *   <p    >   
      * <s     t   rong>Note:</str  o                  ng> This metho        d           is not     c            alled f  or entity references
      *   a       pp ea     ring      as                     pa   r       t o   f at    tribute      values.
         *
     *    @param name T he name of         the    entity  .    
       *      @param au                  gs        Addit i  o   nal inf        ormation th    at may in     clude info       s     e       t au   gmentations
     *
      * @throw  s    XNIExc    eption                 T               hrown b     y handler to    sign    al a         n error.     
        */
              p      ubl  i    c    v        oid endGener  alE nt  ity(St ring n       ame,          Augment   a  tions a   ugs) t                        hr     ows        XNIException  {

               try {
                       // Only report endEnt   i    ty       if this       entit  y   w as a        c        tually       rea      d      .
                  if   (augs == n      ull     || !Bo o lean.T     RUE.e               quals(a  ugs.get Item( Co    nsta   n  ts.       ENT      ITY     _SK  IPPED))) {
                      //    SA X2                    extension   
                      i   f (f  Lex  icalHa     nd     ler !=       n         ull) {
                                           fLexi    calHandler.  e        n       dE   ntity(    name);
                            }
                         }
           }
                 catch        (SAXExcep     tion     e) {
              th   ro  w    new XNIExc  eption(e); 
           }

    } //         endEntity( Stri    n   g)

          /**
        *     Th    e start of a    n eleme   nt. If t   he     d  o     c      ument specifies the s  tart element
         *    b y    using an empty t           ag, then the s      ta        rtElement me       thod      will     immediatel y     
     *     be       follo  wed b   y    t   he end      Ele men    t m  et        hod, wi      th no intervening   m  et    ho  ds.
       *
     *     @p    aram element                  T he nam   e     of           the elem    ent.
     *    @par                  am     att    r  ib   ute s T he el    ement att   ributes.
       * @param augs     Additional        i   nf  ormat   ion that     may                   in  c         l          u   d       e infos             e     t                   au                               gmentations
          *
           * @throws XNI     Ex  c      e        ption   Thrown by han    dler    to     sig         na l   an error.
     */
    public void start Element(QName element,      XML    Attributes attributes, Augmentations augs)
        thro      ws XNI              Exception   {

           try {
               /       / SAX1
            i   f (fD  o    cument   Handler != nu  ll)    {
                                        // R     EVISIT:          should we             sup   po    rt sc      hema-         normaliz e d-value      for SAX1 e   vents
                              //
                                  fAt   tr   ibutes  Proxy    .setAttributes(attributes)    ;
                f  D    ocum   entHa  n    dler.  startElement(element.rawnam     e, fAt    tribu tes Pr    oxy    )  ;
                                   }

               // SAX2
               if (  fContentH  andl     er  !=      null  ) {
       
                        i     f (fNamespac   es) {
                                     // s en d prefix       mapp    ing e     vents
                                           start  Nam espac   eMa pping(    );

                      //  R    EVISIT: I t should     not   be   necessa  ry t  o   iterate o  ver t           he attribute
                                      //   list            whe                n the   se  t of [namespac   e attributes]   is empty for this
                                                   // elemen    t. Thi       s shou    ld be    com    p    uta      ble from the Na        mes  p   aceContext, but   
                          // si nce we curr  ently      don't report t  he mappings for   the xml prefix      
                                      // we c       an          n         o  t     use   the d   eclared      p                   ref     i       x   count  for  the       c  urrent context
                                      // to skip this s     ection  .   --     mrglava        s
                                i              nt  len = a tt    ributes.getL     e           ngth();
                                                     if    (!  fN  amespacePref   ixes     )         {
                                    for (int i  = le  n     - 1;       i >= 0      ; --i             ) {
                                             attributes.ge         tNa     me(i, fQName);
                                          if ((fQN       ame.pr     efi x   ==    XMLSymbol s.P    REFIX_   XM   LN  S) ||
                                     (fQName. rawname == XMLSymb     ols.PREF  IX_XML    N S    ))      {
                                                                                                        // remo ve   namespa                   ce    declarat      io        n attribu          tes
                                                                  attributes.remove   AttributeAt(i);
                                                            }
                                            }
                                                 }
                            else if (!fXM                 LNSU RI   s) {
                                     f   or (int i = len -      1; i         >= 0; --    i) {
                                                        attributes.               getName(i,        fQNam    e) ;
                                                     if      ((   fQName.prefix == XML  Symbo     ls    .PRE               FIX _XMLNS      ) ||
                                                                      (fQNa   me.rawna          m   e =   =  XMLSymbols.PREFIX_XMLNS)   ) {   
                                   // localpar    t        sh       ould be em   pty strin     g           a         s p er S AX documenta  tion:
                                             //         h                ttp:/ /   www.saxproject.   org   /  ?sele  cted       = names    pa ces
                                              fQName.prefix =    "";
                                      fQName.uri = "";
                                         fQName       .   localpa  rt = ""   ;
                                                  attributes.setName(i  ,        fQNa    me);
                                                          }
                                                  }
                         }
                           }

                           fAugm    entati ons = a     ugs;

                  String uri = elemen       t .uri != n   u      ll ? e              le   ment.u   ri   : "";
                               Str  ing    localp               art = fNamespa      ces ? element.localpart   : "";
                                          fAttr   ibutesProxy.set    A    ttri   butes(a  ttr  ibute  s);
                       fC     o nten               t    Hand           ler.startEleme        nt(   uri, loc  alpa rt,       elem en   t.    raw          name,
                                                                                                 f      Attr  ibutesProx    y);
                          }
          }
              catch           (SAXExc      eption e) {
                                throw new XN      IE    xception(e    );
        }

       } /    / s          tartElement(QN  ame,XMLAttr        ibutes)

    /**
       *   Cha    r    acter      content.
         *
           * @      param  text Th e       conten  t    .
        * @param augs     Additional       information      that m      a      y   inc       lu              de info    set augmen    tatio ns
               *
               *     @throws                     XNIException Thrown by ha   ndler    to signal           an error   .
     */
    publi     c vo    id characters(XMLS   t                   ring text, Augmenta   tions augs) throws XN    IExc    ep    tio   n {

                // if    type is       u  nio  n (XML      Schema  ) it is possib      le that    we receive
         // c     ha racter   cal    l wi    t  h      empty  data    
              i    f     (text. lengt h == 0) {
                             return;
               }   


        try {
            /  /       SA  X1
                      i                f (fDocumentHand   l     er != n ull)    {
                                      // REVISIT: should  we support schema-n   ormalized-val  ue for              SAX1  eve  nts
                                         //
                               fDo  cumentHandler.chara  cters(text.ch, text.offset, te       xt   .lengt   h);
                   }

                       //  SAX2
              if (fCo nte ntHandler   != null   ) {
                    fCon t en tHandler.                  c    har   acters(t        ext     .ch, text.off s       et, text.  len   gt  h)         ;
                         }
        }
                  ca                      tch (SAXEx     cepti   on e  ) {
                        throw ne   w XNIExcep   ti       on(e);
        }

          }      // ch   aracte  rs(XMLString  )

    /**
     * Ign      orable whitespace. For  this method to be      called  ,    the d   oc   u   ment
     * source m            u     st ha       v   e some way of determi                    n      ing   tha   t the text containing
                * only whi     te spa   c          e   c    haracters    sho ul   d be conside  re   d ignorable. For
            * example, t    he validat     or can de   termine i  f   a length  of whitespace
     *      charact   ers in the d    ocument  are ignorable bas         ed on t  he element
      * cont  ent mode      l.
      *
      *    @param text The ignorable whitespace.
        * @param a       ugs            Additio    na  l     inform    ation that may inclu       de infoset augmenta tions
                             *
     * @throws XNI  Excep   ti   on Thro     wn by ha             ndler to signal an        error.
            */
         public void ign   orable     Wh  itespace(XMLStri     ng te  x   t, Augmentation    s augs)      th r    o ws   XN  I  Ex   ce   p    t    ion {

        t  ry {
            // SAX1
                          if (fDocumentHand ler !=   null) {
                           fD  o       cumentHandler.ign    ora    bleWhitespac e(text.ch   , te        xt.o  ffset,        t   ext.le      ngth   )      ;
                   } 

                 // SAX2     
                      i  f (fContentH   and   ler != n  ul   l) {
                            fCon      tentHandler.ignora  ble  Whitespace(te    x      t.   ch, text  .offs      et,    tex   t.length);
                 }     
           }
        catch (SA       XException    e) {
                                         throw new XNIEx ception(  e);
           }

    }    // i   gnorable        Whitespace(XMLString      )     

    /**
           * The end         of an ele ment.
     *
       *    @pa   ram el    eme   nt          Th         e name of the ele          me      nt.
        * @param au  gs     Additional information that may i      nclude   infoset aug    men           t     ations
                         *
     *   @thro      ws   XN  I Ex     ce     ption    Thrown b  y handle       r to signal an err     or.  
                        */
    pu            blic   void en d Element(QName el eme    nt, Augmentation s   augs) th   rows XNIException {


               try             {
                      // SAX1    
                  if (fDocumen       tHan     dle       r != nul   l)            { 
                    f Docum entHandler   .e       ndElement(element.rawname)            ;
                      }

                /    /      S  AX2
            if (fContentHandler  != null) {
                                          fAugmentat     ions = augs;
                           String uri = element.uri    !       =    null              ? element.uri :         "";
                          String local   pa rt = fName   spaces     ?    element.    l  oca     lpart : "";
                     fC     on   te   ntH     andler          .endElement(ur  i, localpart     ,
                                                            eleme       nt.        rawname)                 ;
                if (fNa  mes  paces) {
                             end   Namespace        M     app   ing();
                           }
                  }
           }   
        catch (SAXException e      ) {
                     thr  ow new X        NIExc           epti o  n(  e);
            }

                    }            / / en dElement(QName)
   
              /**
     *    The sta  rt of a CDATA       se   cti  on.
          * @pa     ram a   u g     s             Additi    o       nal i  nf      orm   atio   n th      at may include inf       ose      t aug     me      ntations
                *
                             *   @  throws X    NIExceptio        n   Thrown by handl  er to signal an e     rror.
     */       
                      public void st          a  r    tCD                  ATA(Augmentation s augs) thr    ows XNIE    xcep  ti        on {

               tr     y {
              /  /           SAX2 extension
                if    (fL  exicalH   andler !    =   null)         {
                   fLexicalHandler  .sta r     tCDATA();
                   }    
                }
        catch (SAX Ex    cepti      on    e) {
                   th          row n  ew XNIEx     ception   (e    );
                      }

     } // startC  DAT   A ()

     /*         *
     * The e  nd       of a      CDATA secti   on.
     *       @par                 am augs     Additional information th       a  t may includ   e infoset augmentation   s     
               *
     * @throw   s          XNIExce                        ption Thrown   by       handler     to        signa  l an error.
        */
    public void    endCDA    TA(Augment     a tions a     ugs)    t          hrows XNI     Excepti      on          {

        try {
                      // SAX2 exten sion     
            if (fLexi  calHandler     != null) {   
                fLexical      Handl     e   r.   endCDATA();         
                       }
                         }       
             catch (SAXException e) {
                   thr  ow     new XNIExce   ption(e);
        }
 
         } // en     dCDA       TA()

    /          **
     *         A commen     t         .
     *
     * @p       ara m text The text in           the comment.     
                      *               @pa       ram      a  ugs     Additiona l in       f   ormat   ion      t hat  may inclu           de     infoset            augmen   tations
                   *
      * @throws XNI   Ex    ception    T       h   rown by   application to  sig nal an         error.
         */ 
    public void  com ment(   X       MLSt  ring       text, Augmentatio n   s augs)           th      r   ows XN   I      Ex ceptio     n {

        try {
            // S   AX    2 ext     ension  
              if (fLexicalHandl    er != n       ull)   {
                   fLex        icalHandler          .co    mme   n          t(text.ch, 0, tex  t.      length);
                     }
                     }
             c   atch (SAX   Ex     ception    e) {  
                             throw new XNIEx  ception(e);
           }

    } /  / co    mment(XMLS         tring )

            /**
       * A pr   ocessing             instruction. Processi  ng ins   truc  tions                   consis  t  of  a 
     *           target      name a  nd         , optionally     ,    text data.    The data is only meaningful
       * to the     appli  ca  tio n.    
     * <p>      
        * T       ypical     ly     , a     pr      ocessing i            ns      truction'   s        d  ata will    conta     i        n         a ser        i    e    s            
     * of pse   udo-a ttri        b    ut es. Th  ese pse    udo-att ributes    fo     ll          o  w the f   orm of
             * ele          ment  attribu        tes b            ut    are <stro   ng>not</strong>    par   se   d or presented
      * to the application      as anythin   g o t      her   than t    ex         t  .          The   appl       icatio  n is  
              * re   sponsible fo          r pa   r     s      i   ng the data.
         *
     * @  param t  arget The  tar get.
      * @param     da     ta     The data or null if none       specified.
         *        @par     am augs     A ddit  ional inf   ormation that may inc   lude infoset    augment      a   tions
      *
        *      @thro  ws XNIEx  cept            i  on    Thr   o  wn by hand   l        er t           o   sig            n al  an error  .    
                */
    pu   blic v        o  id processing   I   ns              truc  tion(String target, XMLStri      ng       da     ta,  Augm   en  ta     t   ions a  ugs        )            
         throws XNIE      xcep     tion {
   
          //
        //      REVISIT - I k       eep  ru      nning i nto SA        X apps tha   t expect
        //       null d   ata   to be    an     empty string, whi   ch is con     trary         
              /  /   to  th   e comment        fo   r this     meth      od         in the SA        X            API      .
              //

        try {
                // SAX1
               if (fDo   c   umentHandler != nul   l)     {
                                 f   Doc     umentHandler.processingInstruction(target,
                                                                                 data.t o     String());
                }   

            // S AX 2
                               if (f   Cont       entHa  ndler    != null) {  
                fCon   te     ntHan dler.processi  ngInstruction(target         , data.toString(        ));
              }
                             }
                           catch     (S AXException e)        {
                throw new XNIE      xcep   tion( e    );        
        }
   
          }                   /   / processingI nstr   uction(St  ri      ng ,XMLStri n  g)   

  
      /**
              *     T   he end of th e            document.
     * @      pa          ram augs            Additi      onal i   n     formation that may include infoset  a      ugmentations   
         *
      * @throw      s XNIException Thrown by hand   ler to       signal an error.
        */
    pu       blic   void     endDocument(Augmentations augs) thro  ws    XNI       E       xce    pt  ion {

            t  ry {
                         // SA  X       1
               if (  fDocu        mentHandler   != null) {
                      fDoc  ume       ntHan dler.endDocument();
                  }

                     // SAX2
                           if (fContentHandle r != nu   ll)      {
                             f  Co ntent Ha  ndler.endD    ocument();
                   }
        }
        catc      h (       S         AXEx    ception e) {
                       t    hro w new    XNIExcept ion(e     );
                 }

            } // endDocument()       

       //
       // XM       LDTDH   a  ndler me thods  
    /   /

    /*   *
         *   Th         e start     of th        e    DTD exte              rnal s   ubse         t.
               *
     * @param   augs Addit                 ional in       f      ormation tha     t may include in    fos     e   t
                 *                                 a ugmentati   ons.
        *
      * @thr   ows XNIException Thr       o         wn         b   y handler to    s    ignal a      n err        or.    
     */
      public vo id startExte             rna    lSub    se     t  (XMLResourceIdentifier i d  entifier,        
                                          Aug   ment  at  ions       au gs)     throws X    N         IE    xcepti  on   {
                  startParameterEntity     ("[d             td             ]",         nul           l, null, augs);
    }

             /   **      
     * Th   e end  of th      e DTD extern al s  ubset.
        *     
      * @param augs Additional informati        o  n that ma  y incl   ude   infose     t   
                      *                                             augme     ntat    ions.       
     *
          * @thr    o ws XNIEx  ce ption Thrown by handler to si   gnal an error   .        
     */
    publi    c v   o  id    en    dExternalSu bs et (Au   gmen t a      t    ions augs) thro ws XN I  Exce  ption       {
                          endPa ram ete     rEnti  t  y("[dtd]",    a ug   s);
            }

    /*     *
                 * This method    notifie    s of       the start of param  eter        ent  ity. The DTD   has     the
                    * p          seudo-       na        m      e of "[dtd]"     pa    rame            ter enti  t     y  names s     tart with '% '; a   nd
     * general ent          ity n   ames     are just the     ent     ity       nam     e.
                             * <p>          
     *   <strong>Not        e:</stro  ng> Si    n    c   e the doc      u        m          ent i   s an entit       y, t     he handler
        * will be   no   tifi         ed o     f  the star   t of the document ent it   y by calling     the
            * startEnt     ity met    ho    d w      ith    the entity na   me "[      xml]  " <em>b efor   e</em  > call  ing
      * the     startD           ocument method. Wh         e     n expo sing entity boun  dari   es t  hrou  gh the
                    * SAX API,        the document entity  is ne    ver report      ed,            however.  
                            * <p>
               *       <st  r     ong             >Note:   </str   ong> T   his me    thod i      s not         cal                  led       for enti  ty   references
     * appearing   as      pa  rt of att    ri bute    v      alues.   
     *
     *   @param name     The n   a        me of the paramet er e  ntity.
       * @p aram id    ent    i                        fier      The     resource ident   ifier.
        *  @param e    n     coding The  au   to-de        tec  ted IANA encodin     g  name of th e enti ty
     *                                   st r  eam.   Thi  s val ue will be n      ull in those situations    
                *                     where t  he entity encoding        is n       o      t        auto-detect     e  d (e.g.
     *                       inte   rnal parameter e  n   tities         ).   
     *    @    pa  ram augs Ad  d    i             ti  ona  l info  rmation that may in      clude infos   et
     *                                      augmen   tations.
      *
     * @th    rows   XNIExcept        ion Thrown                b  y hand  ler to s           ignal an erro          r.      
       */   
    p    ubl          ic vo   id   s   tartParame  terEntity(   String n               ame,
                                                     XMLResou rceIde      ntifi    er iden   tifier,
                                                                 Str    ing enc odin   g, Augme nt  a       tions  augs)
        throws         X  NIExcept  ion {

         try {
                                    //   O     nly report s   tartE      ntity       if thi      s entity was act           ually   read.
                     i   f (au       gs != null && Boolean.      TRUE.equals (au    g  s.getI            tem                              (  C      onstants.E        NTITY_SKIPPE                 D))) {
                        //    re  port skipped  entity to con   tent handler   
                                       if (fCont      entH andler  != null  ) {
                                    fConten   tHandl       er.s           kip pedEntity(name);
                              }
                    }
                   el  se {   
                                        // SAX2 extensi  on
                                    if    (fL                      exicalHa  nd  ler   != nu    l l && fLe      xicalHandle rP    a       rameterEn       ti    ties  ) {    
                                  f   LexicalHa   ndler.star tEnti  t           y(name);
                     }
                 }
        }
                 catch  (SAX          Exce    ptio        n e   ) {
                    throw ne   w XNIExcept                ion(                e);
              }

        } /    / st  a  rtParameterE  ntity(S    trin   g,identifie             r,Str   ing,        Augmentation)

           /* * 
       *  This method     not i   fi     e    s    t   h    e     end of     an en  tity.     The DTD has       the pseudo-n             ame
                     *     o  f "[dt d]" parameter enti ty nam         es start    with '%';      a    nd general ent  ity
             * nam es are just t  he       entity n     ame.
                  * <p>
        * <str     ong>N   ote:</str       ong> Sin  ce t   he docu              me    nt is     an entit        y, the han  d   ler
     *        will  be noti   fied of the    end of  the d    ocumen  t    e n    tity by calling the
      * endEntity m ethod      with the entit    y na  me "     [xml]" <em  >after</em>      calling
                    * th   e endDocument method    . When exp    osin     g entity bo         undarie   s    through t      he
           * S   AX API,    the docum   en   t entity is never reported, however.
     * <p>
     *   <stron          g>Not        e   :     </stron  g> This method is not           called for entit    y ref erences
           * appearing             as par   t of attribu  te    values.
     *
          * @para       m name The     n    ame o f    the par     ameter entity  .
                    * @p          ara m augs   Addition   al in   fo  rma  t        i  on tha    t may      include infoset
     *                                        au   gm   ent       ations.
        *
     *  @throws XN           IE    xc            eption  Throw   n b y h    andler to signal               an    error   .
              */
    public voi  d endParame   te         rEntity(String n   ame, Augmentati  ons aug  s)     throws XNIExcep       ti       on            {

           try {
               // On       l  y re  port  en   dEntity if this     entity was actual   ly  rea d.
                              if  (augs == null    |              |   !Boolean.TRUE.       equals(au   gs.getItem     (Co        nstants.ENTITY_   S   KIPPED)   )) {
                                 /   / SAX2 extens    ion
                         i   f (     fLexi   calHandler !=        null && fLexical        HandlerParameter   Entities) {
                         fLexicalHa      n   dler.endEnt    it     y(name);      
                        }
                         }
               }
        catc h (SAXException e     ) {
                                throw    ne   w X   NIException( e);
                }

    } // e    ndEntity(String)   
    
    /**
       * A   n e  lement     decl   aration.  
     *
                   * @p       ara  m     nam    e                              The nam e o   f th             e eleme   nt.    
      *  @p       aram cont   entModel The    elemen   t     con  tent      model.
              *
              * @pa   ram aug s     Add    ition      al info   rmation t    hat may includ  e inf os            et
      *                                   augmentations    .
     *
     * @throws XNIExc  eption Thrown b                y hand        ler to signal     an error.
          */   
     pub    lic vo              id eleme     ntDe  cl(St  ring name     , S     tring      c    ontentModel, Augmentation    s augs  )
        throws   XNIExc  ep    t     ion {

                     try       {   
                // SAX2 extensi on
                        if (fDeclH andl     er != n    ull)      {
                     fDec             lHand  le         r.element     De     c  l (na    me,                 c    ontentModel);
                }
                     }
               catch (SAXEx  ception  e ) {
                                throw                      new   XNIExc    ep        tion(e); 
                   }

          } //             elem   entD      e     c  l(String,String, A  u gme    nta       tions)
         
                   /**
            * An    attribute declaratio     n .
     *
                  * @pa  ram   e     lemen     tNa     m   e    Th   e  name    of the element that this a   tt r   ibute
       *                                                                  is a     ssoci ated with      .
     * @param    attrib        uteName The n  a  me    of the attribut   e.
     * @param ty     pe                The attribute type. This value w  ill b          e one of
                       *                               the fol           low ing: "CDATA", "     ENTITY", "ENTI  TI   ES  ",
       *                        "ENU      MERA       TI ON  ",    "ID   ",  "ID  REF", "IDREFS",
     *                          "               NMTOKE N      ",  "  NMT      O             K    ENS",            or  "NOTATION"  .
     *    @param     enumera  tion       If   the type   has the v    a            lue "ENUMERATION"  or
        *                               "NOTATION", t   his array holds the allowed     a ttribute
     *                                values; othe     rwis  e,       t    his array           is nul l  .
     * @param default   T   ype   The att       rib   ute default ty pe. T    his value will         be       
       *                                         one of   the fo    l   lowi   ng: "#FI     XED", "#I MP      LIED",
     *                                                           "  #REQU     IRED",  or null.  
               * @par     am defaul     tValue                The  at               tribu    te defaul     t        value, or null if n    o
     *                                                         de  fault value     is    specif   i              ed.
         *
     * @p aram nonNor            malizedDefaultVal  ue   The attribute d  efaul t value   with no normalization
           *                              per   form      ed, or nu    ll    if no defau    lt      v    alue is specifi     ed.              
            *    @param a ugs     A   dd  itional       inf   o   rmation that may inc                  lude info  s   e t
       *                                              augmentatio   ns.
       *
           * @       th ro    ws   XNIExc  eption Thrown by h    an dler to signal          an error. 
         */
        publ    ic void attributeDecl(   String elementName, String at             tri  b       ut eName,
                                                           Stri   n     g type, Str         ing       [] enume        ration,
                                         St  ring de  faultType  , XM  LSt             r   in g defa ultVal       ue, 
                                                   XMLStri       ng n  onNorm  alize              dD efau ltValu  e,     Aug     mentatio     ns aug s)  thro   ws XNIEx         c   eption {    

                 try {
                    // SAX2 e   x    tensi on
                  if          (f  Dec    lHan   dler != nu      l    l)     {
                        // u   sed as a key to detect  dupli      cate attribut       e   defin iti   ons.
                         Str    ing         elemAttr     = new StringBuffer(     elem e     ntNam    e).a    ppend   ("<").append(a  ttribu   t     eN  ame).toStrin       g();      
                         if(fDec    lared      Attrs.     get(e   lem  Attr) != nul    l) {
                                      // we a    ren     't p      erm     i               tted     to ret   urn   duplicate attribute d    efini  tions 
                                     return;   
                                             }  
                                     fDeclaredAttrs.put(elemAttr,     Boolean.TRUE  );
                     if (     type.equa  ls("NO  TATION          ") || 
                                 type.e   quals  ("ENUME R    AT      ION"))    {

                                      S       tring      Buff         er str  = ne     w Str     ingB           uffer();
                    i     f (type.eq      u        als("N            O    TA         TIO   N"))          {
                           st   r.app     end(type);
                      str.app        end(" (" );
                                        }
                         else {
                                   str.append(  "("   );
                                           }
                                                  f   or (int i = 0;   i < en         um eratio    n.length; i++) {
                                  str.app   e      nd(e  n  um era          tion[i]);
                                if   (i < enumeratio n.len    gth - 1) {    
                                                    s   tr.appen     d('|');   
                                        }
                               }
                       st      r  .append(')'  )    ;
                                     typ    e =     s   tr.toSt  r    ing(   );
                   }
                            St   ring     value   = (d    efaultValu e==null) ?      n     ull  : defa   ultValue.  toStr   in   g();
                  fDeclHandler.attribute   Decl(el e      mentName, att     r            ibuteN                ame,
                                                                                                        type   , defaultType    , value);   
                                 }
           }
        catch      (SAXE   xception e  ) {
                                      t  hrow ne    w XNI    E           xceptio     n(e);
        }

                } //                     attr    ib  uteDecl(Strin   g,S  t      ring, String,Str        ing[   ],  S      tri     ng,XM              LStri    ng, XMLStri   ng , A  ug mentations)

    /**                       
              *  A n interna  l entity de  claration.
                        *
     * @param          n ame   The name of the entity. Parameter e   n    t   i    ty n   a           mes    start with
          *                               '%', whereas    the name of a general en    tity   is just the
        *                        ent  ity   nam   e.
        * @param    tex     t T he value of the            ent     ity    .
     *  @param no      nNormalizedTe     xt The n    on              -normalized value of    th  e e  ntity. Thi     s
                 *             va     lue contains the same se    que nce o  f characters th     at w as in
     *                                      t  he int   erna  l entity decla  ra    tion ,         wit    hout any entity
         *              referen  ces          ex   pand     ed    .
        *
     *   @param augs Additional information        t hat may i           nc                                lude     infoset   
     *                                augment  ati     o       ns.
       *
       * @    t    h  r      ows XNIExce  ption Throw   n by handle    r to          si   g        nal an  error.
           */
     public v   oid inter        na  l        En   tityDecl(String    name, XMLString  text,  
                                                                     XMLString nonNo  rmalizedText,
                                               Augmentations  augs) throws XNIExcept ion {

        try {
                // SAX     2 extensions
                                    if (fDeclH    and ler     != nu     ll) {
                fD    eclHa   nd      ler.internalEntityDe                cl(name,   t  ext.toString   ());
                           }
        }
            catch   (S AXE xceptio n e) {
                               throw new    XNIException(e);
            }

    } // i           nternalEntityDecl(String,XMLString,XMLString)

                        /    **
              * An exter               n    al ent   ity declaration.
                   *
                       *   @param          name     Th       e name of the en     tity. Parame  ter e     ntity names       st art
               *                                       with '%', whe       r    eas      th           e name of a gen   eral e  ntity is    just
           *                                                  the entity name.
             *         @par         am identifier    An   o   bj           ect co  n  ta in    ing all lo cation information
     *                                     pe          rtinent to this e       nt    i    ty.       
     * @par    am      augs Addi   tiona    l informatio      n       t   hat may    include in       foset
            *                                     au  gmentat i    ons.
         *  
     * @   throws        XNIExc eption     Thro         w  n by h    andler to s      ignal an  error.   
     *       /
             public void extern  alE   ntity De           cl      (Stri    ng na     me     , X MLResourceIdentifi   er       i denti  fier,
                                                                   Augmentatio  ns     augs)     throw   s       X          NIExcepti   on {
        t   ry {
                 // SAX2 extensio      n    
                   if (        fDec    lHandler  != null)        {
                             String    p     ubli   cId = id      entifier.getPub             licId       ()     ;
                     S          trin     g systemId = fResolve   DTDURIs ?
                             ident   ifie r.getE  xpand   e     d     S     ystemId() : identifier.get            Liter   alSystemId(  )     ;
                            fDec    lHandl er.externalE  ntityD ecl(name    , p  u  blicId ,  systemI   d);
                    }
              }
              catch (SAXException  e) {       
              throw new XNI         E     x        cepti    on    (e);     
                                       }

    }         /                       / ex     ternalEntityDecl      (String    ,,XMLRe        sourceIdentifier, Augment   ati       on       s     )

     /    **
         *      A n unparsed e        ntity   decla    ration    .
     *
     * @param name     The name    o   f   the entity      .
          * @pa       ram identifier       An ob         ject       containing   al     l lo  c   at     ion     informati     on
               *                                         per           tinent t  o this e nti   ty.           
                * @para     m notatio   n The name of   the         n         ota  tion.
             *
         * @param augs Additi      onal     inform at       ion that may include infoset
        *                                         a       ugm      entatio   ns.
     *
        * @t    hro  ws     X     NIExcept  ion    Thrown    by h        andler to s      ignal an error.
          */    
    public void u   nparsedEn  tityDecl(St  r    ing n            ame, XMLR   es ourceI    dent        ifier identifier,
                                                                String not      ation    ,
                                                               Au  gmentations a         ugs) t hrow   s X   NIExce  pti on   {
                  try {
                   //     SAX2 e        xtension
                                     if (f   DTDH  andler  !=   null) {
                    S tr    ing                  pu          blicI d = identif    i    er.getPubl     icId()    ;
                         St   ring systemId = fResolveDTDUR     Is     ?    
                                      ide  ntif    ier. getEx  p   andedSystemId() :  identifier.   getLiteralSystemId();
                         fDTDHa       ndler.unp  arse   dEntityD   ecl(name , publicId  , s   ys    temId, nota t ion     );
               }
                    }
                             ca    tch (SAXExc    eption e)    {
                           th     row new XNIExce  ption(   e);
                      }

    }   //   unp ar       se d     E   ntityDec   l(St   ring,XMLR  es   ource  Identifier, String, Augmentations)
    
         /**
              * A      notation decla       ration
           *
     * @param name        The n  ame of th           e    not  a   tion.
       * @param identi          fier        An     object containing all loc        ation in              for    mation
            *                                     perti  nent to  this     notat   ion.
      * @param augs        Ad  dition   a l info   rma tion     that may include info     set
             *                                                    augment   at ions.
            *
     *   @th    rows XNIE  xception T      h   rown         by   handler to signal   an er     ror.
              *  /
         publi    c voi  d       notationDec    l(S tring    name,        XMLRe   sourc      e Identifier id        ent     ifie  r,
                                                Augm      e     ntati      on    s augs) th rows  XNIExce ption {
        try     {
               / / SAX1 an  d         SAX2
                         if (fDTDHan   d  ler ! = n         ull) {
                                     St ring   pub           lic            Id =       id            entifie r  . get    P   u  blicId();
                             String systemId     = fResolv       eDTDUR   Is ?
                                ident       ifie     r.getExpandedSystemId(             ) : ide  n      tifier.g  etL     iteralSystemId();
                      fDT               DHandler.notationDecl(name, publicId,   syst          emId);
                 }
        }         
                  cat          ch (S    AX   E   xception       e  ) {
              thr ow new     XNIExce  p    tion(e);
           }
 
    } //   notationDec   l(String ,XMLRes  ourceIdentifier, Augm           entations)
    
        /    **
     *    T     he end of the D   TD             .
                        *
        *     @para     m augs Add  itional informati   on   that may in   clude   infoset
                 *                                    augme  ntation    s.
     *
         *     @throws   XNIException Thro   wn by handler to si   gn  a     l   a        n error.
           */
                 pub                    lic void endD   TD(Augmen    tat    ions  au  gs)     throws XN       IExcep        tion {
                 fI           nDTD     = fa   l     s  e;

        try {
                // SAX    2 extension
                        i f   (fLexic   alHandler        != null) {
                fLexical  Handler .endDTD();
                                  } 
                                 }
               ca tch (  S    AXExce  ption    e     ) {
                throw new XNIEx  ception(e)   ;
            }
        if(fDe claredAttrs !=      null)   { 
                      /  / he   lp out the GC
                   fDec   laredAttrs.c   lear();
            }

    }   // endDTD()

    //
       // Par ser and X                  ML  Reader meth             ods
    //  

    /**
         *   Parse  s the inp  ut              source specif                 ied by the given system     id      entifier.
             * <p>
     *        T  his         m     etho    d is equivalent to the   follow  ing:
       * <pre   >
        *        parse(new    Inpu      tSo      ur  ce(syst   emI      d));
        * </  p re>
     *
        * @param syst  emId T  he syste     m     identif ier             (UR  I   ).
     *
     * @exception org.xml.sax.S AXException                         Throws except   ion on SAX err     or.
     * @excepti     o    n ja     va.io.IOExcep      tion T    hr                  ows exception     on i/o error.
     */
     p   ublic void         parse(      String systemId) thr        ows    S   AXException     , IOEx  cep       ti        on     {

                        //          pa   r     se  d       o                cument
                  XMLI    nputSourc         e source = new XMLInputS   ource   (   null, s      yst   emI d, n     ull);
           try { 
             par   se(sour   ce);
          }

        // wr       ap XNI ex    cep  tion   s as  SAX excepti       ons
                    catch (XMLPar   seException e) {       
                                     Ex    ceptio     n ex = e. getExcep tion();
                                               if (ex == n ull                  ) {
                     // mu        st be a pa      rs er exception;        mine it for locat or inf  o      an           d th   row
                               // a SAX     ParseExc   epti  on       
                        LocatorImpl l  ocatorIm   p   l = new LocatorIm  pl(    ){             
                                        pub    l   ic St       ring get      XML   Vers ion() {
                               r    eturn fVersi   on;
                                             }
                           // since   XML                                   ParseExcept    ions     know nothing about enco         ding  ,
                            //        we   cannot return   anyth   ing meaningful in t   h     is co   n    te    x  t.
                              /  / We *cou   ld* c    o       ns ult th   e LocatorProxy, b    ut the  
                                                 /                /              applica   t  ion           ca  n  do this itself          if i t wis    he    s to poss  ibly
                          // be      m    islea    d.
                          public S     tring       get  Encod     ing()  {    
                                                                    ret      urn  nul    l;
                       }
                    };        
                           locatorI    mpl.          set  Pub    licId(e.getP  u   blicI  d(      ));
                        lo catorImpl  .setSy  s      temI    d(e.getExp  a        nd  edSyst emId());
                locatorIm pl.    setLi  neNumber(e.get       LineNumber   ());
                                            locatorImpl   .setC olumnNumber(e    .getCo   lumnNumber());
                         throw new SAXPars     eEx          c    e  p         ti   on(e.getMe        ssa  ge()    ,     lo        cato  r   I     m   pl);
                  }   
                        if (  ex inst anceo  f SAX  Exce  ption) {
                                 //             wh                      y d           id         we creat   e an XMLParseEx  cep    tion?
                                         thro   w ( S    A   X    E   xception)ex;
                 }
              if (e        x i    n   stan                 ceof IOExce  ptio             n) {
                throw            (IOException)ex   ;
                    }
             t     hrow     new SA    XException(  ex   );
                }
        catch (XNIExcep  ti      o   n e)   {
                           Exception    e   x = e .      g   e  tExc  e ption();
             if (ex == null) {
                      throw     n  ew S  AXExce      ption(e.getMessage(   ));
               }
                i        f       (ex i    nstanc           eof SAXEx            ception) {
                                    throw (SAXE    xception )ex;
                     }
             i  f (   ex i         nstanceof IOE  xcepti  on           )   {
                    th row ( IOException)e    x;
                  }
                throw new SAXExcep  tion(ex);     
                      }

    } // par    se   (S  trin g)   
    
    /**
     *   p      ar    se
     *
               * @param inpu  tSo         urc  e
     *
       * @e  xception org.xml.sax.SAXEx  ce              ption
                * @ex     ce ption java.   io.    IOExc     eption
     *   /   
    p      ublic v oid parse  (InputSource   inputSo   urce)
                  thro         w     s SAXEx         c       eption, I  OE   xce ption {

        // pars      e document
          t   ry {
                             XML Inpu tSou  rce x  mlInpu         tSou    r c   e      =
                           n   ew XMLI   n  putSource(   inputSource.getPublicId        ()    ,
                                            i     nputSo   urc e.getSys   tem       Id(),
                                                         null);           
                     xmlInp               utSour    ce  .set   By    teS                          tre    am(inputSour       ce.g etB    yteS     tr    eam());
                    xmlInput Sou rce.se        tCharacterStrea  m(       inputSo    ur   ce.  getCh           aracte   rStrea       m());
                  xm  lInputSour ce.s et     E      n       cod ing(inputSource   .                   g       etEn co   ding()   );
              parse(xmlInpu  tSource       );
             }

        // wrap XNI   excepti  ons as       SAX exception  s
                   ca tc h (XMLParse      Exception e) {
                                 Exc    eption      ex = e.get   Exceptio   n()  ;
             if (ex == null) {
                   // must be a parse     r    excep    ti on   ; m   in   e          it     for      loc       ato         r info and throw
                        // a    SA XParseException   
                        Loc  a    torImpl loc  atorIm   pl = ne                w        Locato     rImpl() {     
                                                        public    String getXMLVers   ion()  {
                                                return fVersion;
                               }    
                         // since XMLParseExceptio   ns   know nothing about encoding,
                     // we cannot ret    u    r  n anythi     ng meaningf     ul in this cont      ex   t.
                                                 //  We *c         ou ld* cons    ult   the  LocatorPr        oxy,     b           ut       the
                         // a   pplication can do this its     e       lf           if it wi  s   h     es to possibly
                    // be mis  l   ead.
                            p   ublic String getEncoding    () {
                                      return      null ;
                                     }
                              }         ;   
                  locato  rIm pl   .s     etPublicI        d(e    .getPubl   icI         d  ());
                      locat  o            rImpl   .setSyst   emId(e.getExp   andedSystemId()  );    
                                   locatorImpl.setLine Number(e.getLineNumber()       );       
                 lo   c  ato      rImpl.set           Col    umnN     umber(e  .get    Co lu mnNumber() );
                        th    row new SAXP      arseE  x       c  e            ption( e.ge t   Message(), locat      orImpl);     
               }
            if (ex instanceof SAXExce ption ) {
                              // why did      we      c     re      ate a  n XMLParseEx  cepti   on?
                   thr       ow (SA   XException)ex;
                     }
              if (  ex insta    nceof IOE   x        ce  pti     on) {
                              t  hrow (IOEx         ce      ption)ex;
               }
                 throw n         ew S AXException(ex);
        }   
              catch         (XNIExceptio n         e  ) {
                 Exc eption ex   = e.getExceptio          n();
                if (ex    ==     nul l)     {
                         thr   o        w new SAXExcept    ion(e.getMe  ssage());
                     }
                           if (ex        i    nstanceof   SAXException) {
                       throw (SAXException)ex  ;
                           }
               if (ex instanceof IOExc         eption) { 
                                           thro  w ( IOException)ex;
                            }
                         throw new SAXException    (    ex);
           }

    } //   par   se(InputSource)
    
      /**
     * Sets      the resol  ver   use  d to re           solve external entities.        T             he EntityResolve      r
         * interface          s       upports     r    esol     ut ion    of         pu blic and s                  ystem id    e   ntifi    ers.
                *
        * @param r  esolver The new    entit  y     r   eso   lver. P          assin     g a    nu      ll val    ue will
                *                                                 uni  nstall    t      he currently in   stall            ed      res  olver.
     */ 
    public void se  tE    ntityResolver   (EntityResol  ver  resolv  er   ) {     

             t ry    {
               XMLEntityResol  ve  r xer = (XMLEntity       Resolve   r) f      Con   f      iguration.getProperty(ENT     ITY_RE SOLVER);
                    if (fUseEntit   yRes   olv     er2 && resolver in  stanceof En tit   yResolver2)        {
                               i f (xer inst       anceof     En    t    ityResol    ver2Wrapper)    {
                                                EntityR    esolve  r2Wrap      per er2w   =     (Entit  y   Resolve   r2Wrapper)     xer ;
                                 er2  w.se       tEn      tit   yReso  lver( (En  tityRes    ol  ver2     ) resolver);
                   }   
                                 e                       lse {
                          f                   C         onfi   gur      atio  n.setProperty(ENTITY_ R     E  SOLVER,
                                             new   E                  nti     t    yResol       ve  r2    Wrapper((   En   tit   y   Resolver2) reso              lver));
                                            }
              }
                         else {
                                if (xer       inst  ance           of En                   tityResolverWrapper) {     
                              Entit  yRes      olverWr  apper   e   r   w =  (EntityRe   s  ol    verWrapper)        xer;
                    er w.  s    etEntityResolve           r(resolver            );   
                            }
                                else {
                                 fConfigura   tion.set          Proper    ty(               ENTITY_             RESO          LVE    R,
                               new Entity        Res   olverWra       pper(r esol  ve     r));   
                              }
                    }
          }
                       catc  h (XMLCon        f  igurat      i                 onException e) {
            // do    noth  ing
         }

    }  // setEntityResolver(En     t   ityReso   l   v                    er)

            /** 
     * Return the current entity resolve  r.
     *
     * @return The curre       n   t        e  ntity r     e   s     olver, or  null if n     on   e
                      *         has been    r     egistere d    .
          * @see           #se  tEntityR    esolver
        */
    public E    ntityReso  l   ver getEntityR e    sol             ver()  {    
 
               Entit   yRes    olver e nt    it  yResol   ver = n ul  l;    
                     try      {
                XMLEntityResolver   xmlEntityResolver =
                (XMLEntityResolver)fCo    nfig  ur   ati on.getProper ty(ENTIT  Y_R E SOLVER     );
                        i        f          (xmlEntityR     esol ver != n   ul                l   ) {
                          if (xmlEntity    Resolver i       nstan  ceof Entit       y   Resol    v erWrapper) {
                                            entityResolver =
                                     (              (EntityR e solverWra              pper) xmlE   ntityR        esolve        r   ).getEntityRe   solve        r();
                }
                        else if (       xmlEntity      R    esolver  in   stanceof En   tityR   e         solver2W    rappe    r) {
                                enti  tyReso   lver =
                                           ((EntityResolv    er 2      Wrap           pe   r ) xmlEntityRes  olver       ).g     etEntity          Re   sol      ve r();
                         }
                   }
         }     
          catch (XMLConf     ig     urationExcep tio  n e) {
                // do   n  othing 
                        }
        retur   n      en           tit   yResolver;
  
    } /   / g etEntityRe  solver():Enti  tyResolve     r

                 /*    *   
                  *                      Allo   w  a       n      ap           pli           cat    i   on   to regi ster   an        error e   vent    ha    ndl      er. 
     *
                         * <p>I  f     the appl     icatio  n d       oes not regi   ster an                 error handler, all
            * er  ro r e   vents report      e   d by the S         AX parser w     ill be sil   e   ntly
        * ignored  ; however,       normal p  ro                  ces              s   in         g may n    ot c          ontinue.  I     t is
             * highly recommend     ed that all S     AX applic   ations    im  plement an
             * e     rr  or hand                   ler to      avo     id  unexpec   ted bugs.</          p>
       *
     * <p > Ap                       p  lications may regis         ter a new or   d iffer         ent           handler in the
               *           mid     dle of      a pars   e, and the SA     X par    ser must begin u          si    n  g the new
       *         handler i        m  mediat  ely.<    /p>
      *
      * @param error    Handler T he error ha     ndle    r   .
          *       @see      #getEr ror  Handler
     * /
               public vo   id              setEr   ror Ha   ndler(Erro                       rHan         d      ler errorH   andle    r) {   

        tr y {
                       X    ML   Err  orHa ndler xeh    = (XMLErrorH    andler) fConfiguration.get        Pr  oper           ty(             ERROR_HAN    DLER);
                                  if (xeh    instanceof Err           o         rH andlerWrapp       er         )         {
                                           E  rr  orHand         l         erWrapp er ehw =    (Er  rorH  andle  rWra  pper  )    xeh;
                                    ehw.setErrorHan  dler(erro     rHandler);
                        }
                           else {
                                             fC  onfigur   at   ion.setProperty(ERR   OR_HANDLER,
                              new ErrorH   a    ndl     er   Wrap  pe        r     (erro    rHa   ndler));
                 }  
                               }
              catch          (XMLConfi   gurationExcep        t       ion e   ) {
                     // do nothi    ng          
         }

    } //    setEr ror  H     andler(ErrorHand      ler) 

    /**    
     * R     et        ur   n   the curr    ent error      handler.
     *
     * @re       turn The current error handl      er, or     n     ull i      f none
           *                has bee      n reg  is    te red.
        *   @see #setErro    rHandler
     */
       public    ErrorHand        l    er getErrorHandler() {

              ErrorHandler errorHa     ndler =   n  ull;
                     try {
                       XM  LE      rrorHandl  er xmlErr           orHandler =
                  (XMLEr rorHandler)fCo   nfiguration.get   Property(E           RROR_H   A    NDL  ER);           
                 i   f (xmlErro          rHandler != null &&
                        xmlErr orHand   ler ins     t     anceof      ErrorHandl      erWra    p per   ) {
                    er   rorH    andl   er = ((        Erro          rHandlerWrapper)       x ml    ErrorHandler   ).g        e tE     r   rorHandl    er();
                        }
          }
          catc    h      (XMLConfiguration               Exception e) {
                 //   do                      n    othi     ng
          }
                 return errorHandler;
 
    } //  getError Handler():Err    orHan  d   ler

             /**
       *    Set the loca    le to use for messages.
     *
           *      @para       m l   oc     al   e    The l        ocale objec  t to u    se     for localization o  f   mes   sages.
     *
     * @exception SAXEx       c         eption       A       n exce  p     ti    on thrown if     the p        a   rser do          es not
     *                                       support the specified locale.
     *
     *  @see org.xm  l.sax.      P  arse      r
           */
       pu  blic         vo i      d set     Locale(L  ocale     lo     cale) throws S  A       XException {         
              //REVISIT    :th  is me    thods is not part    of SAX2  inte     r  faces , we shoul d throw ex  cep  tion
        //if an   y application      uses    SA    X2 and sets l     o  cal              e also. -nb
                fConfiguration.se       tLoc      ale    (   local   e);

    } // setLocale(Locale)

     /**
     *                Allow     an a    ppl ication to register a DTD event ha                       ndl       er.
     *             <p>
                         * If the application does not regis  ter       a    DTD handler,        all DTD
             *  e vents reporte d          by the SAX parser wi     ll be     silently ignore   d.
         * <p       >
                  * Applications may regist     er  a new or  diffe             rent     ha    ndle         r in the
     * mid dle o   f a p arse, and t  he SAX parser must      begin    usi      ng the    ne   w
                         *      handler    im     m   e diate         ly.        
                 * 
     * @  param dtdHandler The DTD h  and        ler.
     *

            * @see #getDTDH    a ndler     
                           */
    public vo        id set    DTDHandler   (DT     DHandler dtdHan  dler) {
                           f  DTDHa         ndle  r            =      dtdHandl    er     ;
                  } // setD           TDHandler(DTDHa             nd    le  r  )

    //
    // Pars                   e r me    thods 
            //

    /**
       *        Allow       an application                to regist              er a     docum    ent e  ven t handl              er   .
              * <       p>  
     * If  the applic        ati   o      n does not register a    do   cumen      t handler, all
              * document events re ported b     y the SA   X   parser will be sile   ntly
       * ig   nored (thi     s is t  he default b ehaviour i mplement ed by
                   * Han       dler              B               ase   ).
        * <p>
     * A   pplicat ions     may reg    ister    a n      ew or  different  hand l         er in th   e
     * m     iddl   e of a    parse, and the SAX      parser must begin   using the     ne   w
        * h    andler      im   m  e    diately  .
         *
       *   @p    ara    m d     ocumentHa  ndle  r The document h a    ndler.
     */
         public  void setDocumentHand ler                 (Docume ntHandle  r d ocume     ntH    andler)          {
                   f DocumentHand le r   = doc  ume   nt     Ha   ndler;
        } // s     et D      ocumen     tHa    ndler(Docum          entHa  n   dler)    

       //
    //         XML       Reader meth             ods
    /      /

     /   **
           * A  llow a                     n application      t      o register a content     event handler.
     * <p  >
         * If t he app    licat ion does not re   gis       te   r a co   nten   t    handler, all
     *      c    ontent    events     repo rt   ed    by th   e SA  X p   arse         r wi  ll be si     lentl  y
        * ignored.
     * <p>
       *        A   ppli  c ations    m     ay reg ister a new or           dif    f er       ent han  dler i     n the
                *    middle         o  f a p      ar      se, and the SAX  parser          mu st begin us     ing t          he new
     *   handler imme       diately.
         * 
                   * @par am contentHandl   er T     he   content h andler.
                    *
            * @see #  getContentHandl      er
        *   /
     public      vo    i    d  set      Co    ntentH  andl    er(Con tentHa   n dler c     ontentHand        ler) {
        fC             ontentHan  d        ler = conte ntHan        dler;
    } //    setC     ontentHand le r(Con   tentHandl  er)
 
    /*           *
     * R   eturn         the curr    ent content   handler  .
             *
     * @re  tu     rn  The curren    t conten   t h    andler, or null   if      none
                *         has bee  n regist ered.
         *
                  * @s   ee #setContentHandler
           */   
     public ContentH     andler       g          etContentH   andler(  )      {
          retur         n fContentHa    ndle      r;
       } //  getContentHand  le        r( )  :ContentHandler
                       
    /**
     * Re      t        urn the current DTD handl    er.
              *
     * @r etur  n    Th    e curren t             DTD         handler,   or null   i f        none       
         *                         has b  een registere    d.   
               * @se     e    #  setD       TDHandler
     */
       public DTDHandler     ge               tDTDHandler() {
                 re   turn fDTDHandler;
    }  // getDT    DHan    dler():DTDHandler

    /**  
     * Set the state        of an          y f ea    t        ure in a   SAX2 parser.  The pars       er      
                             * m  ight n    ot re        cognize the feature,       and i f it d   o  es re  cogni       ze
     * it, i       t might not be able to f           ulfill the     reque   s     t  .
     *
                         * @param fe      atureId Th       e unique      ide   nti    fier (URI)       of       the feat    u re.
     * @param state The  re quest    e        d    st         ate of th    e f        eature (t ru  e       or false).
         *
     * @except     ion              SAXNot   Recognized   Exception            If the
     *                         requested featu   re is     not k  now  n    .
                 * @excepti     on SAXNotSupported   Ex   ception If the
     *            request  ed     featu  re is known ,  but   the requested
              *                       state is not            su   pp   orted.
             *      /
    p   ublic void setFeature(Str     ing feature   Id,   boolean state)
             th    row   s           SAX     No       tRe          cognize    dException, SAXN         otSupportedExc      ept        ion {

        try     {
                                      //   
                      /  /  SAX2 Featur  es    
                            //

                   if (feat     ureI d.  star   t  sWith(Constants.SAX_FEATURE_PR E       FI   X))    {
                final  int su    ffixLength =  fea        tureId.   length() - Co     nst   ants.    SAX_    FEA   TU RE_    PR  E FI          X.length(   );

                        // http://xm l.org     /       sa       x/f   eatures/nam       espaces
                if (suff        ixLength == Consta   nts.N  AMESPAC ES_         FEATURE.length() &&  
                                                 featureId.endsW  ith(Constan    ts.NAMESPAC    ES_F    EATURE)) {
                                                        fConf         igurat            ion.setFeature(featur         eI   d, state   );
                                        fNam espaces = state;
                                              return;
                       }

                  /   /   ht    tp:// xml.org/sax/ feat      ures/name    space- pr            efix            es
                                    /    /       co    ntrols th   e reporting    of    raw prefixed n   ames and Namesp      ace
                                           //   declarations (xmlns* attri         butes): when    this feat ur          e is fa  lse  
                              //       (the    default), raw prefixed na mes         may optio nally be r      eporte     d,   
                            //      and   x            mlns* a      t      t  ributes mu      st not be r    eported.
                 //
                                       if (suffixLength == Constants.NAMESPAC   E_PREFIXES_FEATURE.l   eng   th() &&
                         featu reId.endsWith(Constants.N      AMES    PA  CE_PR        EF  IXES_FE ATURE)       )  {   
                        f    Co   nfigurat    ion    .setF  eat        u                  re(f  eatur       eId, state);  
                              fN        amespac       ePr          e   fi       xe             s = state   ;    
                                return;
                         }  
  
                       // http  :/    /   x       ml.org/sax/features/st   ring-interning
                   /   /   controls th              e u    se of ja  va.la        n    g.Str      ing#intern() for strings
                    //   passed       to SAX h  andlers.
                         /        /
                             i      f (suffi          x     Leng    th == Const an  ts.ST    RI          NG_INTERNING_F     EAT URE.     length() &    &
                            feature Id.en       dsWith(          Constants.STRI   NG_I  NTERNING_FEATU     R      E)) {
                                   if (!s tate)      {
                                       thr ow new SAXNo         tSuppo     rtedEx   ception(            
                                 SAXM   essageFormatter.fo           rmatMessag   e(fConfiguration.getL  ocal       e(),
                                                  "fals   e-not-sup    ported        ", n  e   w O    bjec       t [   ] {fe      atureId   }));
                           }
                         retur        n;
                                   }

                    // htt         p://xml.         or g/s     ax/features/le  xical-hand   le  r/para   me  ter-entities
                           //   c       ontrols wh ether the            be     ginn   ing and    en      d of  para meter e ntit   ies
                    //   will be      reported to the Le   xicalHan  dler.
                   //
                           if  (suffix Length ==   Constants.L EXICAL_HANDLER_PAR   AMETER  _ENTITIES_      FEA  TURE.le           ngth(      ) &&
                          f eature         I   d.endsWith(Constants.LEXI          CAL_HANDLER_PAR  A       M      E   TE     R           _   EN        TI TIES_FEAT     URE))     {
                                    fLexi  ca           lHandlerParamete     rEnti    t      ies = st    ate;
                                          retur n;
                                    }

                                     //  ht         tp   ://xml.org/sax/featur  e   s/    resolv  e-      dtd -uris      
                            //   c          o      ntrol   s w            het    h   er system    iden             tifiers will be      a     bs    olutiz     ed rela     tive to
                      //   t  heir b     ase URIs b      efore    reporting.
                         //
                         if (su  ff    ixLength     == Constants.RESOLVE_D TD_URIS_ FEATURE.leng   t h() &&
                             featur         eId.ends Wit   h(Cons   ta    nts.       RESOL              VE     _DT  D_UR     IS_FEATURE)) {
                                 fResolve  DT       D    URIs    = state;  
                                      return;
                     }

                                            // http:/      /x    ml.org   /sax/fea   tur             es/unicode-no   r malization-chec   king
                               //           c         ontrol  s w           h ether Un          ico     de no rmalizat   io      n chec   king         is    pe rformed
                //   as per   A   ppendix B of the XML    1.1 speci    fication
                   //
                     if (suffixLeng          th ==       Cons  tants.UN  ICODE_NOR  MALIZATI    ON_CHECKIN G_FEATURE   .length()     &&
                                                 featureId.e       nds            With   (Const  an       ts       .U    NICODE_NO   R   MAL   IZ ATI O    N_C       HECKING   _FEA   TURE)   ) {               
                               // REVISIT: Allow t hi              s   feat   ure    to    be  se t    once U      n  icod  e normal     izati         on
                                              // ch  ecking      is suppo rted -       - mrg        l    av      as.
                        if (state) {
                                  th  row   ne        w SA   XNotSupp o   r  ted   Exception(
                            SAXMe    ss    ageFor  matt    er.fo        rmat Message(fConfigur  ati  on.get       Lo   cale   (),
                               "true-no            t-s upported", new O   bject [] {featureId}));  
                                }
                            return;
                  }

                // http://xml.org/    sax/f   e  at ures/x    mlns-u     ris
                   //   c    on    tro      l  s whe ther     the pa        rser re          po       r     ts that              n    amespac   e declarat    io   n
                              /       /   attr    ibutes as bein     g in the     nam     espace: h         ttp:          /   /www.        w3.           org/2000/x            mlns/       
                 //
                if   (suffixLengt     h ==  C  onstant    s.XMLNS_URIS_                  F   EAT   URE.lengt              h       () &&
                                 feat     ureI    d.endsWith (C        onsta     nts.XML    N   S_URIS_F   EATUR      E)) {
                                fXMLNSURIs      = s tate;     
                                                    r    e t urn;
                                 }

                         // http://      xml.org/sax/features/use-entity-re   so   l  ver      2  
                            //   contro  ls whether the   methods o    f an object implementing
                                      /  /           org. xml.sax. ex     t.E    nti tyResol  ver2     wil   l be used     b  y the parser.
                    //
                                if         (            suff   ixLengt      h ==    Constants.USE_ENTI    TY_RESO       LVER2_FEATURE          .length    ()    &&
                                               featu reId.endsWith(Const     ants.USE_ENTI      TY_RESOLVER2_FEATURE)) {
                             if  (state != fUse     EntityRes  olver2) {
                                fU  seEn    t   it yRe    solver2 = state;
                                        // Ref      r esh E         ntityResolver w r              apper.   
                                       se    tEntityR e       s          olver(ge    tEntity               Resolv     er(           ));
                         }
                                     re       turn;
                             }

                            /   /
                                 /       / Read o    nly fea     tures.           
                              /   /

                   // http://x  ml.org/s     ax/features/ is-standalone           
                              / /   rep  or       ts wh   ether th     e    do   cument s  pecified a   st anda                     lone document   declarat  ion.
                         /       /  http:/        /x ml.org/sax/   features/use-attributes 2
                                 //   reports whether Attr  i  b        utes objects     p as    sed to startElement also impl      em ent
                       //   the    org.xml.sax.    ext.  Attrib  utes2 interf   ace  .
                 //   http://   xml.     org/sax/featu  res/use-lo      cato r2
                  //       reports whe   ther L  oc    ator objects passed      to     setDo cumentLocator    also im    plement
                          //   the org.xml.sax.ext.Locator2  inter   face.
                                // http://xml         .o   rg/s      a             x    /f       e      ature  s/xml-1.1
                     //   reports whether     th e   parser su                  ppo rts  bot       h XML 1.  1 and XML 1.0.  
                     if ((suffixLeng th        == Co    nstants.IS  _ STANDAL        ON   E_FEATU    RE.          lengt      h() &&
                      fea     tureId  .en dsWith(C  onsta    nts.IS_S   TA  NDALONE_F       EATURE)   )      ||
                                     (s         uffixLength == Constants.USE_ATTRIBUTES2_FEATURE.length() &  &
                                         feat  ureId.end     sWith(Constants.U  SE_ATTRIBUTES2      _FEATURE))          |   |
                                (s uffixLength =      =       C     onstant               s.USE_LOCAT              OR2_FE  ATURE.length() &&
                    feat        ureId.endsWit  h(C             onsta  nts.USE_LOCATOR2_FEATUR    E)) ||
                     (suffixLen        gth == Constants.X                  ML_11_FEATU   RE.leng      th () &&
                              fea tureId.    endsWith(Con   stan ts.XML_11_FEATURE)  )           ) {         
                                   throw n  ew SAXNotSupp   ortedException(
                             SAXMessageForm  atter.formatMessa   ge(       fCo nfiguration.getLocal    e(),
                                       "fe    atu        re-r  ead-o   nly", ne w Object     [] {featureId}));
                     }


                     //
                       // D      rop through  a  nd p      e   r       form            default processing
                     //
              }
            else if (fe   at    ureId.e      quals(XM  LConstants.    FE   ATURE_SEC URE_  PROC  ESSING)) {
                                      if  (state) {
                                    if (fC   onfig     urati on.g     etProperty(SECURIT    Y_MANAGER )   =   =null) {
                                   fCo      nfigurat     ion.   setProperty       (SECURITY   _M    ANAGER, new XMLSecurityManager(   ));
                               }
                       }  
                               }

            //
                  // Defa     ult ha    ndling
                      //

            fConfigu       ration.s  etFeature(fe    ature   Id, state);
            }
                      catch (X      M   L       Configurat   ionException e) {
                   St    ring i     dentifier = e.getI    dentifier()   ;
            if        (e.getTyp       e(        ) ==     S tatus.NOT_REC       OGNI   ZED)     {    
                     throw new SA XNotRecognizedException(
                                     S    AXM  es    sageFormatter   .f   ormat    Me    ssage  (f Configurati o      n.getLocal   e   (      )       ,
                            "feature-not-re  co   gn  ized", new Object [] {i  de   nti         fi er})  );
                  }
            else {
                throw new SAXNotSuppo      rt   edExceptio    n(
                                   SAXMessage      Format   ter.fo    rm        atMessage(f   Config     uration.getLocale(),
                                 "fea  ture   -not-suppor                 ted", new O  bjec    t [ ] {identifie    r}));
               }
                }

    }       // setF eature(Str         ing       ,boolean)

          /**    
             *   Qu     e  ry    the stat  e of a feature .
     *
                  *     Query t     he cur    rent st      at    e of a   ny feature in a S   AX2 parser.               The       
     * parser might not re    cognize the f      eature.
          *
         * @p      aram feature    Id   The unique ident    i     fier (UR  I      ) of the feature
     *                                         being set      .
      * @     return The current state    of the   feature.
          * @excep      tion o     rg.xml .sa             x  .      SAXNotRecognizedExc   e          ption If th   e
     *              req u  ested feature is   not known.
        *      @e     x   ce  ption SAXNotSupporte         dExcept   ion  If   the     
            *                                      re   quest      ed feat     ure      is k nown  but no  t     supported.
     *  /
                    public boolean g    etF    eatur     e(S   tring featu re   Id)
        t   hrows SAXNotRe  cog   nizedExc   eptio  n,  SAX            NotSupportedEx     cepti     on {

               try  {
                            //    
                   // SAX2 Feat  ur es
              //

            if (f  ea  tureId.startsWith(Constants.SAX_  FEATUR   E_PR   EFIX)  )   {
                           final in                t  suf fixL      ength = featureId.le     ngth() - Constants.  SAX_FEATU RE_     PREFIX.length   ()     ;

                          // http://xml.org/   sax/ featu    r  e    s/namespace-pr      efi  xes   
                           //      controls the   reporting of ra   w prefixed    names and Na  mespace
                      //      de  clarat io      ns (xmlns*     att ributes): when   t   hi    s featu  re   i    s fal   s   e
                        / /    (th   e default), ra     w prefi     xe      d names m    a  y o ptionally              be      reported,
                        //    an  d xmlns* attributes must    not be repor         t       ed.
                      //
                    if (suffixLength == Constant     s.NAMESPACE_PR   E       FIXES_FEATURE.length() && 
                          featureI     d.en  ds    With(Constants.NAMESPACE_PREFIXES_FEATURE))    {
                       bool   e  an state = fC     onfi        guration     .ge    tFea  ture(feature    Id);
                       return            st  ate;
                          }
                       // http://xml.org/sax/f      eatu  res/stri  n g-interni    ng
                     //                   controls    the us       e of   java.lang.String#inte   rn() for  strings           
                  /   /   passe d to  SAX han dlers.
                  //
                       if (suffixLen   gth   == Constan ts  .STRING_INTE   RNI   NG_FEATURE.length() &&
                                    feat           ureI   d   .  en   d   sWith(Constants.STRIN   G_INTERNING_      FEATU    RE)) {
                             return t rue;
                     }

                  // http://xml.org/sax         /features/is        -sta       ndalon     e
                         //          reports whether the document sp          ecifi  ed a standal one doc      ument     decl   ara   tio     n.  
                         /       /
                if (suff      ixLe n gth == Constan ts.IS_STANDAL     ONE_FEAT URE.lengt        h   () &&
                      fea  tureId.endsWith(Const              ant   s.IS_ STAND       A  LO   NE_FEAT   URE)) {
                      return      fStand      alo   ne;
                      }       

                /    / http://xm    l.org/sax/features/xml-   1.   1
                       //   repor     ts whether the   parser supports    both XML 1.1 and XML 1. 0  .
                                      /   /
                      i  f (s  uffixLength          ==  Cons   ta nts.     XML_11_FE        ATURE.length() &&
                    featureId.endsWit   h(Constants                                .XML_11_FEA  TURE)) {
                                     ret urn (          fConfiguration instanceof XM      L11Con figu    ra        ble);
                      }

                // http://xml.org/sax/featur    es/lexical-handler/parameter-en       t  ities
                //   co   n   tr      ol   s whether the beginning and end of  par  amete  r en     tities
                  //   will be repo  rted to the L   exicalHand   ler.
                   //
                     if   (suf    fixLength == Const ants.LEXICAL_HANDLER _PARAMETER_ENTI     TIE    S_        F       EATURE.length(   ) &&
                                fe  atureId.  endsWith(Constants.LEXICAL_HANDLER_PARAMETER_   ENT ITIES_FEA  T URE)) {
                        re    turn fLexic   alHand        lerParameterEnti  ties;
                     }

                     //        ht    tp://xml.o rg/sax/features/re    s         olve-dtd-uris
                     //   contr                  ols w  hether syste   m iden         tifi    ers will be abso luti  zed relati   ve to
                           //   their ba   se U   R     Is befor e reporting.
                  if (suffixLength ==   C    onstants.           RESOLVE_DTD_UR     IS_FEATURE.lengt  h() &&
                          f      e   ature   Id.endsW    ith(Con    sta  nts.RESOLV    E_DTD  _URIS   _FEATURE)) {
                        ret    urn fRe solveDTDURIs;
                         }

                     /      / http     ://xml.org/sa     x/features/xmlns-uris
                //         controls whe   ther the parser rep    o  rts that namesp   ac    e declarati     o  n
                     //   attributes       as being in th  e name   space:   http://www.w3.org/2          0        00/xmlns /
                         //
                if (suffix   Lengt   h == Co          nstants.XMLNS_URIS_FEATU     RE.lengt    h() &&
                              feat ureId.endsWith(Cons      tants.XML N  S_   URIS_FEAT    URE)) {
                      ret   urn fXMLNSURI        s;      
                   }

                       // http :                //xml.org/sax/fe   atures/unicode-  normali  zati      on-checking
                   //    controls whet               her U      nicode normaliz     ation checking is per        fo rmed
                          //   as     per Appendix B of the X ML 1.1 specification
                    //
                if (suffi   xLength ==     Co  nstants.UNICODE_    NORMALIZATION_CHECKING_F     EATU   R   E.length() &&
                        featureId.   e    ndsWith(Constants.UNIC ODE_    NO RMALIZATI   ON_CHECKING_FEATURE)) {
                              // REVISIT: Allow      this feature to be s   et onc   e Unicode normalization
                         /         / checking i       s         supported                     -- mrglav   as.
                       r  et          ur      n false;
                                 }
    
                    // http://  xml.org/sax/features  /u      se-en   tity- resolver2
                //   controls wh  ether the me   tho     ds of a  n object impleme     nting
                     /   /   org.xml.sax.ex   t       .EntityR    esolve         r   2 will be u    se  d by the parser.
                                 /   /
                  if (su        ffixLength == C     onstants.USE_ENTITY_RESOLVER2_FEA     TURE.length() &&   
                    featur  eId.end  sWith(Constant   s.USE_EN         TITY_RESOLVER2_F   EATURE)) {
                       return fU  seEntityRes   olver2;
                                 }

                // h  ttp://         xml.org/sax/fea  ture      s/use-attribu       tes2
                               //   reports wheth     er Attributes objects pass ed       to st           artElement    also im    plement
                   //   the   org.       xml.sax.ext.Attributes2 in    te    rface.
                       // h   ttp:/ /xml.org/sax/featur es/use-locat     o     r2    
                                 //   reports   whether Locator     objects passed t           o setDo       cu  ment  Loc   ator also implem ent
                    //   the org.xml.sa  x   .ext.Lo    c        ator2 interf    ace.
                     //
                if     ((suff   ixLe  ngt     h   == Constants.USE_ATTR    IBUTES    2_F     EATURE.length()   &  &
                                         fe   atureId.endsWith(Co     nstants.USE_AT TRIBUT   ES  2_FEATURE  )) ||
                    (        suffixLength   == Constants.USE_LOCATOR2_FEATURE   .length() &&
                          featureId.endsWith(Constants.U        SE_LOCATOR2   _FEATURE))) {
                           ret          urn true;
                 }


                             //
                    //      Drop through and perfo         rm defau   lt process       ing
                    //
            }

                  //
            //               X    erces     Features
                      //

                /*
                  else if (feat    ure           I    d.startsWi th(XERC     ES_FEATURES_PR         EFIX))  {
                    //
                 // Drop     through and perform default   processing
                  //
            }
              */

            ret    urn fCo    n   figuration   .getFeature(featureId);
        }
        ca     tch (XMLConfigurationException e) {
              Stri   ng i den    tifi  er = e.getIdentif ier();
            i f (e.get    Type() == Status.NOT_RECOGNIZE    D) {
                   throw new SAXNotRec   ognizedException    (
                       SAXMessageFormatter.formatM       essage(  fCo nfiguration.getLocale(),
                                 "feature-not-recognized", n  ew Object    [] {identi      fier}));
            }  
                   el  se {
                           t hrow new SAXNotSupportedExc eption(
                       SAXMe  ss   ageF       ormatter.f  ormatMessage(fConfigura    tion.get      Local  e(),
                                    "feature-no    t- su    pported", new  Obje    ct [] {identifie    r})    );
                }
            }

      } // ge   tFeature(String)      :boolean

       /**
     * S et the    value of any pr    operty in a    SAX2 parser.  The pa    rser
     * migh  t not recogn ize t      he property,   an  d  if it do   es recognize
                     *                   it, it might no            t su     ppo  rt the   requeste   d value  .
     *
       * @param prop   ertyId The uni    que    identifier (URI)        of  the p   roperty
     *                                     b ein        g se      t.
      * @param valu    e The value to whic  h      the property is being set.
     *         
     * @excep     tion SAXNotRecogniz  edException If the
     *            r  equested p  roperty         is n     ot       k     nown.
        * @exception SAXNotSu pportedEx                    ceptio     n If the
     *            requ  ested property i  s known, but     the requested
     *                value is n   ot supported.
         */
        public      void setProperty(String prop    ertyId, Object value )
        throws SAXNotRe          cognizedException,   SAXNotSupporte   dExcep    tion {
   
              try {
              //
            // SAX2 core pr    operties
               //

              if (propertyId.startsW ith(Constant       s.SAX_PROPERT     Y_P    REFIX)) {
                          f inal i nt suffixLength = prope  rtyId.length() - Cons      tants.SAX_PRO   PERT       Y    _PREFIX.length();

                      //
                // http://xml.org  /s   a  x/properties/lexical-handler
                    /   / Value type: org.  xml.sax.ext.Lexica  lHand    ler
                  // Access:     read/write, pre-parse only  
                       /   /      Set   the lexical event handler.
                        //
                if (suffixLengt    h == Constant    s.LEXICAL_HAN  DLE    R_PRO PERTY.length(    ) &&
                          propertyId.endsWith(Constants.   LEXICAL_HANDLER_PROPERT   Y)) {  
                    try     {
                                 set  Lexi    calHandler((LexicalHandler)value);
                                }
                            catch (ClassCastException e) {
                           throw new SAXNotSupportedExcepti  on( 
                                     SAXMessage     Formatter.formatMessag  e(fConfiguration.    getLocale(),
                                              "i ncompatible-class", new   Object [] {propertyId, "org.xml.sax.ext.Lexic       alHandler"})  );
                               }  
                    return;
                }
                                 //
                //    http://xml.org/sa  x/  properties/declaration-handler
                           // Value typ   e: org  .xm l.sax.ext.DeclHandler
                           // Acce    ss: read/write, pre-parse o    nly          
                        //   Set the DTD declaration event handler.
                   //
                      if (suffixL  ength == Constants. DE  CLAR    AT    ION_HANDLER_PROPERT  Y.le   ngth() &&
                               propert     yI       d.endsWith(Constan  ts.DEC   LARATION_HANDLER_PR      OPERTY)) {
                    try {
                          setDeclHa     ndler((DeclHandler)va   l u       e);
                        }
                      catch        (Cl   a ssC     astException e)   {
                           throw new SAXNotSupporte   dException(
                                      SAXMessageFormatter.forma     tMessage(fC    o   nf       iguration.getLoc  ale(),
                                    "incom patible-cl        ass", new Ob    j   ect [] {pro  pertyId, "o   rg.xml.sax.ext.DeclHandler"}  ));
                    }
                         return;
                }
                   //
                         // ht  tp://xml.org/sax/pro    perties/dom-node
                          // Value type: DOM Node
                   // Acc   es  s: read-onl     y
                //   Get        the DOM   no  de currently  bein   g visited, if t  he SAX pa  rser is
                  //   iterating over a DOM   tree.    If the parser recognises and
                    //   supports this property but is not currently visiting a DOM
                        //   node, it should return null (t  his is a good way to     check   for
                //            ava  i      lab  ility before the pars  e be        gi      ns).
                  // http:  //xml.org/sax/pro       perties/docu   ment-xml-version
                  // Value type: jav   a.la ng.  String
                     // Access: read-only
                   //              Th      e literal st    ring          desc   ribing the actual XML version of the document.
                  //
                 i     f ((s   uffixL    ength == Constan  ts.DOM_NODE_P      ROPER    TY.length() &&
                           propertyId.endsWi               th(Constants.DOM_NODE_  PROPERT    Y)) ||
                      (suffixLength   ==        Constants.DOCUM ENT_XML_VERS        ION_PROP   ERTY.length() &&
                    propertyId.endsWith(Constants.DOCUMENT   _XML_VERSION_PROPERTY))) {
                         throw new S  AXNotSupportedExc     eption(
                        SAXMessag  eFormatter.formatMessage (fConfiguration.getL  o  cale     (),
                                "property-read-only", new Object []   {pro           pertyId}));
                }
                      //
                  // Drop through and perform default processing
                                 // 
                 }

            //
                  // Xerces Prop   erties
            /   /

            /*
                             els e if (propertyId.startsWith(XERCES_PROPERTIE S     _PREFIX))   {
                  //
                // D rop t  hrough and perform     default processing
                //
             }
                */

            //
                // Perform default processing   
            //

             fCo  nfigurati    on.setProperty(propertyId,   value);
           }
        c     atch (XMLConfigurationExceptio   n e    ) {
                   String identif      ier = e.getIde ntifier();
                if  (e.getType() == Status.NOT_RECOGNIZED)  {
                     throw new SAXNotRecog       nized   Ex    cepti  on(
                      SAXMes sageFormatter.formatMes    sage(fConf iguration.getLocale(),
                    "prop  er  t   y-not-recognized", new Object [] {identifier}));
                  }
                    else {
                throw new SAXNotSupportedExce      ption(
                                            S      AXMessageFormat ter.forma  tMe    ssag    e(fConfiguration.g   et   Locale(),
                         "property-not-supported", new   Object [] {identifier}));
              }
        }

    } // setProperty(String,Object)

    /**
     * Query t he value of a property.
     *
     * Return the current value of a propert  y in      a SAX2 parser.
     * The parser might not rec    ognize the property.
     *
     * @param propertyId The   unique identi     fier (URI ) of the property
       *                         b   eing set.
     * @return       The current value of th   e property.
     * @exception org.xml.sax.SAXNo   tR     eco  gnizedExcept   ion If the
     *                    requested   property is not known.
     * @exception SAXNotSupportedException   If the
                  *            requested property is known but not suppor     ted.
     */
    public Object getProperty(Str  ing p              ropertyId)
        t         hrows SAXNotRecognizedExcepti   on, SAXNotSuppo  rtedException {

          tr  y {
                    //
            // SAX2 core properties
               //   

                       if (prope  rtyId.  startsWith(Constants.SAX_PROPERTY_PREF    IX)) {
                   final int suffixLength = property    Id.length()    -    Constants.SAX_PROPERTY_PREFIX.length();

                     //
                   // http://xml.org/sax/properties/document-xml-version
                //     V alue type: java.lang.String
                //   Access: read-only
                    //   The lite   ral string describing the actua     l XML version of the docum  ent.
                   //
                if (suffixLength == Cons      tants.DOCUM     ENT    _  XML_VERSION_PROPERTY.length() &&
                       proper    tyId.    endsWith(Const      ants.DOCUMENT_XML_VERSI ON_PROPERTY)) {
                    return fVersion;
                   }

                //
                // http:     //xml.org/sax/prope     rt  ies/lexic    al-handle     r
                // Value type: org.xml.sax.ext.LexicalHandler
                  // Access: read/write, pre-pars    e only
                    / /   Set the lexica  l     event handler.
                      //
                 if (suffixLength == Constants.LE XICAL_HANDLER_PROP   E   R   TY.length() &&
                           propertyId.endsWith(C   onstants.LEXICAL_HANDL     ER_   PROPERTY)) {
                    return getLexicalHandler();    
                   }
                  //
                   // http        ://xml.org/sax/properties/declaration-handler
                           /    / Value type: or  g.   x      ml.sax.ext.DeclHandler
                    // A    cces    s: read/write, pre-parse only
                //   Set the DTD declaration event handler.
                      //
                if (suffixLength == Con    stants.DEC  LARATION_         HANDLER_PROPERTY.  length() &&
                    propertyId.endsWith(Constants.DE      CLARAT     ION_HANDLE     R_PROPERTY)) {
                    return get     DeclHandler();
                }

                //
                   // http      ://xm  l.org/sax/properties/dom-node
                         // Value type: DOM Node
                 // Access: read-only
                        //   Get the DOM node currently b eing visited, if the SAX parser is
                   //   iterating ov  er a DO M tree.  If the par ser recognises and
                         //   supp   orts this property but  is not currently visiting a DOM      
                /  /   node, i    t should return   null (this is a good way to check for
                //   av    ailab    ility before the parse   begins).
                //
                      if (suffixLength == Constants.D  OM_NODE      _PROPERTY.length         () &&
                    propertyId.endsWith    (Constants.DOM_NODE_P     ROPERTY)) {
                             // we are     not iterating a DOM tree
                    throw new SAXNotSupportedException(
                          SAXMessageFormatter.formatMessa      ge(fConfiguratio  n.ge    t Locale(),
                             "dom-node-read-not-suppo   rt  ed ", null));
                }

                     //
                    // Dro    p throu    gh and perform default processing
                   //
                 }

            //
            // Xerces properties
            //

            /*
             else if (propertyId.startsWith  (XERCES_PROPERTIES_PREFI     X)) {
                //
                   //    Drop through and perform default processing
                  //
            }
                      */

            //
            // Perform default processin  g
            //

            return fConfiguration.getProperty(property   Id);
                     }
        catch (XMLConfigurationException e) { 
                    String identifier =         e.ge    tId    entifier();
                 if (e.getType() == Status.NOT_RECOGNIZED) {
                  throw new SAXNotRecognizedE  xception(
                    SAXMessage       Formatter.formatMessage(fConf   iguration   .getLocale()     ,
                    "property-no     t-recognized", new Object []     {identifier}));
            }
            else {
                   throw new SAXNotSupportedException(
                          SAXMessageFormatter.forma     tMessage(fConfiguration.getLocale(),
                     "property-not-supporte  d", new Object [  ] {identifier}));
              }
               }

    } // getProperty(String):Object    

    //
    // Protected methods
    //

    // S        AX2 core properties

    /**
        * Set the DTD decl   aration   ev   ent handler.
     * <p>
     * This method is the   equivalent to the property:
     * <pre>
     * http://xml.org/sax/properties/declaration-ha  ndler
        * </pre>
     *
     * @param handler The new handler     .
       *
     * @see #g   etDeclHandler
     * @    see       #setProperty
     */
    protected void set  DeclHandler(DeclHandler handler)
        throws SAXNotRecognizedExce    ption,       SAXNotSupportedException {

        if (fParseInProgress     ) {
            throw new SAXNotSupportedE         xception(
                SAXMessageFormatter.   formatMessage(fConf        i   guration.getLocale(),    
                "property-not-parsing-supported",
                 new Object [] {"http://xml.org/sax/properties/declaration-handler     "}));
          }
        fDeclHandler = handler ;

     } // setDeclHandle     r(D     eclHandle    r)

    /**
     * Returns the DTD declaration event handler.
     *
      * @see #setDec   lHandler
     */
    protected DeclHandler getDeclHandler()
        th   rows SAXNotRecognizedException, SA   XN       otS upportedException {
        return fDeclHandler;
    } // getDecl    Handl     er():DeclH   andler

    /**
        * Set the lexical event handler.
     * <p>
     * This met  hod is the equivalent to the property:
     * <pre>
      * http://xml.org/sax/properties/lexica      l-handler
     * </pre>
     *
     * @param handler lexical event handler
     *
        * @see #getLexicalHandler
     * @see #setProperty
     */
    protected void     setLexicalHandler(LexicalHandler ha    ndle     r)
         throws   SAXNotRecognizedException, SAXN  otSupport  edException {

           if (fParseInProgress) {
            throw new SAXNotSupporte dExcepti    on(
                SAXMessageFormatter.formatMessage(fConfiguration.getLocale(),
                   "property-not-parsing-supported",
                new Object [] {"http://xml. org/sax/properties/lexical-handler"}));
        }
                 fLexicalHandler = handler;

            } // setLexicalHandler(L exicalHan   dler)

    /**
     * Returns the lex    ical handler.
         *
     * @see #setLexicalHandler
     */
      protected LexicalHandler getLexicalHandler()
           throws SAXNotRecog     nizedException, S  AXNotSupportedException {
        return fLexicalHandler;
    } // getLexicalHandler()    :LexicalHandler

    /**
        *  Send startPrefixMapping events
      */    
    protected final void startNamespaceMapping() throws SAXException{
        int count = fNamespa      ceContext.getDe  claredPrefix  Count();
          if (count > 0) {
            Str  ing prefix = null;
            String uri = null;
            for (int i = 0; i < count; i++) {
                prefix = fNamespaceCont  ext.getDeclaredPrefixAt(i);
                    uri = fNamespaceContext.getURI(pre    fix);
                 fContentHandler.startPrefixMapping(pref  ix,
                    (uri == null) ? "" : uri);
            }
        }
    }

    /**
     * Send endPrefixMapping events
     */
    protected    final void endNamespaceMapping() th  rows SAXException {
        in   t count = fNamespaceContext.getDeclaredPrefixCount()  ;
        if (   count  > 0) {
            for (int i = 0; i < count; i++) {
                fContentHandler.endPr   efixMapping(fNamespaceContext.getDeclaredPrefixAt(i));
            }
        }
    }

    //
    // XM LDocumentParser methods
    // 

    /**
     * Rese   t      all components before parsing.
     *
     * @throws XNIException T   hrown if an error occur    s during initialization  .
     */
    public void reset() throws XNIException {
              super.reset();

            // reset stat    e
        fInDTD = false;
        fVersion = "1.0";
        fStand   alone = false;

         // fea  tures
        fNamespaces = fConfiguration.getFeature(NAMESPACES);
         fNam   espacePrefixes =      fConfiguration.getFeatur     e(NAMESPACE_PREFIXES);
        fAugmentations = null;
        fD ecl      aredAttrs = null;

    } // reset()

    //
    // Classes
    //

    protected     class    LocatorProxy
        implements Locator2 {

        //
        //     Data
        //

           /** XML locator. */
        protected    XMLLocator fLocator;

        //
        // C    on structors
        //

        /** Constructs an XML locator proxy. */
        public LocatorProxy(XMLLocator locator) {
             fLocator = locator;
        }

           //
        // Locator methods
        //

        /** Public identifier. */
        public String getPublicId()   {
            return fLocator.getPublicId();
          }

        /** System identifier    . */
        public String getSystemId    () {
            return fLocat   or     .getExpandedSystemId();
        }
        /**   Line numbe  r. */  
        public int getLineNumber() {
            r   eturn fLocator.getLineNumber();
        }

        /** Column number. */
        public int getColumnNumber() {
            return fLocator.getColumnNu mber();
        }

        // Loc    ator2 methods
        public String getXMLVersion() {
            return fLocator.    getXMLVersion();
          }

        public String getEncoding() {  
            return fLocator.getE ncoding();
        }

    } // clas    s LocatorProxy

    protected     static final class AttributesProxy
        implements A   ttributeList, At  tributes2 {

        //
        // Data
        //

        /** XML     attributes. */
        protected XMLAttributes fAttributes;

        //
        // Public methods
        //

        /** Sets the XML attributes. */
        public void setAttributes(XMLAttributes a  ttributes) {
            fAt  tributes = attributes;
        } // set Attributes(XMLAttributes)

                 public int getLength() {
            return fAttributes.getLength();
        }

        public String getName(int i) {
            return fAtt     ributes.getQName(i);
         }

               public String  getQName(int index) {
            return fAttributes.getQName(in    dex);
        }

        public String getURI(int index) {
                // REVISIT: this hides the fac  t th at intern   ally  we use
                //          null instead of empty string
              //          SAX requires URI to be a string or an empty string
            String uri= f   Attributes.getURI(index);
            return uri != null ? uri : ""  ;
             }

        public String       getLo    calNam     e(int index) {
            return fAttributes.getLocalName(index);
        }

               public Strin     g getType(int i) {
            return   fAttributes.getType(i);
        }

        public String getType(String name) {
            return fAttributes.getType(name);
        }

        public String getType(String uri, String localName) {
               return uri.equals("") ? fAttr ibutes.getType(null, localName) :
                                    fAttributes.getType(uri   , localName);
        }

        public String getValue(int i) {
            return fAttrib utes.getValue(i);
        }

        public Str   ing getValue(String name) {
            return fAttributes.getValue(name);
        }

        public String getValue(String uri, String localName) {
            return uri.equals("") ? fAttributes.getValue(null, localName) :
                                      fAttributes.getValue(uri, localName);
        }

        public int getIndex(String qName) {
               return fAttributes.getIndex(qName);
        }

        public int getIndex(String uri, String localPart) {
            return uri.equals("") ? fAttributes.getIndex(null, localPart) :
                                     fAttributes.getIndex(uri, localPart);
        }

        // Attributes2      methods
        // REVISIT: Localize exception mes   sages. --     mrglavas
        public boolean isDeclared(int index) {
               if (index < 0 || index >= fAttributes.getLength()) {
                throw new ArrayIndexOutOfBoundsException(index);
            }
            return Boolean.TRU    E.equals      (
                     fAttributes.getAugmentations(index).getItem(
                Constants.ATTRIBUTE_DECLARED));
        }

        public boolean isDeclared(String qName) {
            int index = getIndex(qName);
            if (index == -1) {
                throw new IllegalArgumentEx  ception(qName);
            }
              return Boolean.TRUE.equals(
                fAttributes.getAugmentations(index).getItem(
                Constants.ATTRIBUTE_DECLARED));
        }

        public boolean isDeclared(String uri, String localName) {
                  int index = getIndex(uri, localName);
            if (index == -1) {
                throw new IllegalArgumentException(localName);
            }
            return Boolean.TRUE.equals(
                    fAttributes.getAugmentations(index).getItem(
                Constants.ATTRIBUTE_DECLARED));
        }

        public boolean isSpecified(int index) {
            if (index < 0 || index >= fAttributes.getLength()) {
                throw new ArrayIndexOutOfBoundsException(index);
            }
            return fAttributes.isSpecified(index);
        }

        public boolean isSpecified(String qName) {
            int index = getIndex(qName);
               if (index == -1) {
                throw new IllegalArgumentException(qName);
            }
            return fAttributes.isSpecified(index);
        }

        public boolean isSpecified(String uri, String localName) {
            int index = getIndex(u   ri, localName  );
            if (index == -1) {
                throw new IllegalArgumentException(localName);
            }
            return fAttributes.isSpecified(index);
        }

    } // class AttributesProxy


    // PSVIProvider methods

    public ElementPSVI getElementPSVI(){
        return (fAugmentations != null)?(ElementPSVI)fAugmentations.getItem(Constants.ELEMENT_PSVI):null;
    }


    public AttributePSVI getAttributePSVI(int index){

        return (AttributePSVI)fAttributesProxy.fAttributes.getAugmentations(index).getItem(Constants.ATTRIBUTE_PSVI);
    }


    public AttributePSVI getAttributePSVIByName(String uri,
                                                String localname){
        return (AttributePSVI)fAttributesProxy.fAttributes.getAugmentations(uri, localname).getItem(Constants.ATTRIBUTE_PSVI);
    }

} // class AbstractSAXParser

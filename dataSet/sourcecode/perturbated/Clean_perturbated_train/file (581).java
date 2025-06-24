/*
 *  Copyright (c) 2015     , Oracle and/o   r it s affiliates     . All right  s reser ved.
 */
/*
 *        Licensed to the Apache Software Foundat        ion (ASF) under one or more
 *     cont    ributor licen  s     e agreements.      See th   e NOTICE file distributed with
 * this work f  or a d   ditional information regarding  co   pyright    own  ership.
  *     The ASF     lice nses  this file to  You u    nder the Apache License, Vers  io    n 2.0
 * (the "Lice nse");  you may not    u  se this file except in compli     ance with
       * the Licen    se.                      You may obtain   a copy   of the          L    icense at
     *
  *     http://www.a    pache.org/licenses/LICENSE-2.   0
 *
 * Un       less required by applicable law or agreed t o in wr iting, so  ftware
 * distribu    t   ed u       nde   r  the Li      cense is  di  stributed on an "AS      I    S" BASIS,           
 * WITHOUT WARRANTIES   OR CONDITIONS OF ANY KIND, either express or   imp      lied.
 * See the Li    cense for th e specific language governing permissions and
 * limitatio        ns under the License.
 */
/*
/*
 * $Id: AbstractTranslet.java,v 1.6 2006/06/19 19:49:0   3 spericas Exp $
 */

package com.sun.org.apache.xalan.internal.xsltc.runtime;

import com.sun.org.apache.xalan.internal.XalanConstants;
import com.sun.org.apache.xalan.internal.utils.FactoryImpl;
import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.DOMCache;
impor     t com.sun.org.apache.xalan.internal.xsltc.DOMEnhancedForDTM;
import com.sun.org.apache.xalan.internal.xsltc.Translet;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.  i  nternal.xsltc.dom.DOMAdapter;
import com.sun.org.apache.xalan.internal.xsltc.dom.KeyIndex;
import com.sun.org.apache.xalan.internal.xsltc.runtime.output.TransletOutputHandlerFactory;
import com.sun.org.   apache.xml.internal.dtm.DTM;
import c  om.sun.org.apache.xml.internal.dtm.DTMAxisI    terator;
import com.sun.org.apache.xml.internal.serializer.Ser      ializationHandler;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
impor           t j   avax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Templates;
import org.w3c.dom.DOMImplementation;
import o rg.w3c.dom.Document;
    
/**
 * @author       Jacek Ambroz            iak
 * @au   thor Santi        ago Perica   s-Geertsen
      * @   author Mor    ten Jorgensen     
 * @author G. Todd Miller
 * @author John Howard, John    H@schemaso      ft.com
 */
p    ublic    ab      str   act class A     bstr           actTranslet impl ements Translet {

     // Th    ese attribut   es are extracted from     the xsl:outpu  t    element.      They also
      // appear as fields      (with        the sa   me t  y    pe,  on  ly public)   in Output.ja     va
            public String  _version = "1.0";
        p ublic String          _method = null;
    public      S    tring       _encoding = "UTF-8";
    publi   c bo   olean _omi         tH         eader  = fals  e ;
    public String  _standalo  ne = null;
    //s   ee OutputPropertiesFactory.O   RACLE_IS_      ST      ANDAL  ONE
    public boolea   n  _isStandalone = fal    se;
    pub     lic Str   ing           _doctypePub   lic = nu   ll;
    public String      _doctypeSystem =    null;
    public bool  ean    _ i  ndent = f     alse;
    public Strin       g  _mediaType =   nul     l;
    public Vector _cdata = null;
               public int _indentamount = -1;

    public sta      tic final int FIR  S         T_TRANSLET        _VERSION = 100;
                public  static final i     nt VER_SPLIT_NAMES     _ARRAY = 101;
    public sta     tic     final int CURRENT_TR   ANSLET_     VE   RSION = VER_SPLIT    _NAMES_AR  R            AY;

       // Ini   ti    al ize Tra        nslet version f    ield to      base      value.       A class that extends
    // AbstractTranslet may over    ride this va   lue to a more    recent translet
        //   versi   on; if it doesn't override  the value (     b  eca    use it was compi  led
                // before the notion of a trans     l  et v ersion was introduce    d, it w ill get
      // this default valu    e).
    pr ot  ected int translet  Version = F          IRS   T   _TRANSLET_VE   RSIO   N;

      // DOM/tr     anslet handsh aking - the   arr  ays are s   et b      y th  e compiled translet
    pr   ot  ected Str   ing[] namesArray     ;
        protected String[] urisArr a        y ;   
    protec  ted int[]    typesA     r  ray    ;
    protec    te  d  String[] namespaceArray;

    //   The Templates object that is used     to c rea   te t     his    Translet instance
        protec   ted Temp  l    ates _  t    emplates = null;

       // Boolean f  lag to ind   ica       te whether this translet has id functions  . 
       prot       ected bo    ol  ean _hasIdCall = false;

    // TODO - these s h     ould only be instanciat     ed when   needed
        prote      cted StringValu  eHandler s     tring  Valu  eHandler = new StringValueHand  l      er ();      

    // Use o ne empty s      tring instead o   f constantly insta nciating String("");
    priv   ate      f   inal stat    ic St  ring EMPTYST     RING = "";

    / / This is the n  a   me of the index used for I  D     attributes   
    private final     sta     tic String ID_INDEX_NAME = "##id";

    pri   vate boolea    n _u   seServices  Me   chanism;

    /  **
     * protocols allowed for external   r  eferences set by t     he s    tylesheet processing instruc tion, Document() fu  nction,   Import and Include eleme     nt.
     */           
    private Stri     ng _     accessE  xter   nalStylesheet   = XalanCons     t an    ts.EXTERNAL_ACC ESS_ DEFAULT;

    /* ******   *********************     ****************************     ****************
      * Debugging
     **********    *******    ****** **          **************************  **************** ****    */
      public       void printInte   rnalState() {
        System.out.println(  "--------  -----------         ------------------");
        System.out.p  rintln(" AbstractTransle    t this           = " + this);
        System.out.println("pbase =   " +     pbase);
            Sys  tem. out.println("vframe = " + pframe);
             System.  out.printl    n("        p     ara    msSta    ck.size() = " + pa  ramsSta     ck.size());
           Sy  stem  .out.println("namesArray.size       =   " + names    Arra y.l ength); 
         Sys tem.out.println("nam    espaceArray.s ize = " + namespaceArra   y.length);
        S     ystem.ou t.prin     t  l  n("");
         Syst      em.out     .println("Total     memory = " + Runtime.getRuntime().totalMemory());
       }  

    /**
          * Wrap the i    nitia       l input    DOM in a    dom adapter.  T  his adapter     is wrap    ped in
      * a DOM     multi   plexer if the document() func  tion is use  d (hand le         d by  compiled  
         * code      in t he transle    t - see comp   ile     r/Stylesheet.  co m    pileTransform()).
        */
     pu    blic fin al DOMAdapter makeDOMAda   pter(DOM dom)
                  throws TransletEx  ception {
            setRootFo  rKeys      (  dom.getDocum         ent());
        return    new DOMAdapte   r(dom, na        mesArra  y, urisArray, typ     esArray, namesp        aceArray);
    }   

    /*******     ***********      ********     *   ***************************     *  ************   ***  *     *
     * Pa  rameter handli ng
        *******   *****      ***********       ********* **************     **************************   /    

    // Pa         rameter' s stack: <tt>            pbas    e</tt> and         <tt>p   frame<     /t  t> are used
                    //     to den       ote        the current parameter frame.
      prote  cted int pba  se = 0               , pframe = 0;    
    pr   otected     A     rrayList p aramsStack =           new Arr      ayList(  );

    /**
         * Push     a n  ew     parameter    fra  me.
     */
    public       final void pushParamFram       e() { 
           pa  ramsStac   k.   add(pframe, new Inte    ger(pbase ));
         pbas      e = +   +    pframe;
    }

      /**
           *    Pop      the to   pmos   t paramet      er frame.
     */
     public final void po  p    ParamFrame() {
           i   f (pbase >  0)    {
                   final          int o                      ldpbas      e    = ((     Integ er)pa    ra  msS  tack.get(--pbas    e)).int       Value();
            for (int i = pframe - 1; i >= pbas e; i-  -) {
                         param  sStack.                              remove(i);       
             }
                           pf     ram e = pbase  ; pbase   = ol dpbase;
         }
       }

    /**  
             * Add a    new globa       l parame         ter if not alre    ad    y      in the            c            urrent           fra me.
           *  To setParameters      of         the       for  m {http://foo.bar}xyz
         * This     needs  to get mapp        ed to a n in  stance va   riabl      e in the c      lass
           * The mapping         created s  o that
     * the global             va ria       b   les in the genera           t     ed   c    la  s s become   
        * htt p$colon$$flash$$flas       h   $foo $do              t$bar$colon$x yz   
     */
                      public       final Object ad  dParameter                 (St    ring name,    Object value)  {
        name = BasisLibr   ary.mapQNameToJ    avaName            (  na    me);
             r        eturn        addPar   ameter(name, val    u    e, f     alse)   ;
     }

           /**
             * Ad  d a new global or loc al para m       eter if not alread   y in the cur    rent      fram     e.
         * The 'isDefau  lt'    parameter  is set to      true if            the  va       lue pass       ed is th    e
         *    default v  alue from the <xsl:parameter> element's select at   tribute o  r
     * elem         e nt body       .     
             */  
    publi  c fi        nal Ob  ject ad    dParameter(Str ing     na me,      O bject value,
             boolean isD      efault)
    {
        /  /     Local par ameters       n    ee      d to be re-     ev       aluated for   each i                t  era    tion     
                 for (int   i = pframe - 1;      i >      = pbase    ; i--) {  
               final Parameter param = (   Parame     ter) pa   ramsS   t ack       .get (i)  ; 

                     if (param._name.equa   ls(name)) {
                       /   / Only ov   erwr       ite i          f curr  ent value i       s the   default   value and
                 // the new      value is     _NOT_ the default value.
                 if (param._i     sDefault    || !is  Defau  lt ) {
                         param._val  ue =    va             lu      e        ;
                             pa   ram._isDefault = isDefault ;
                            re    t  urn v   alue;
                }
                     return param._value;
                     }
            }

        // Ad    d n   ew parameter  to p   arameter stack
        paramsS   tack .add(p   frame++, n    ew Parameter(name, va   lue   , i     sDefaul       t       ))  ;
        re    turn   valu        e;
            }

      /  **
            * Cl          ears the parameter stack.
                  */
       public vo    i   d cl   earParame   ters()        {
                    pbase = pframe = 0;
        para  msStack.cle  ar();
    }

    /**
          * Get        t   he value of a          p            aramete   r from  the      curren  t frame   or
     *    <tt>null<                /tt> if u    ndefined.
      */
    pu     blic final Object       ge  tPara   meter(String na   me)    {

            name     = BasisLibra     ry.        mapQNameToJav aName (  name)   ;

          for   (int    i = pfram   e      - 1; i >= pbase; i--) {
               final Paramet   er param = (Parameter)par  amsStack.get(i);
            i                   f      (param._na    me  .equals(name))    retu rn param.    _value;
        }  
        ret      u   rn null;
    }

     /        ************************    ***   *******   *** ********************************* **
                       * M  essage handl ing - imp         lemen   tat ion of <xsl:mess   a ge>     
     **************     ***************** ************************************          **   ** */

    // Holds t              he t     rans    let's message handl   er -  used for <xsl:message>.
    // The de            aul t     message    handler d   umps       a string st   dout, b   ut  anyt  hing can be
    /  / used, such  as a dialog     box f   o  r ap       plets, etc.
         pri             vate   MessageHandler _msgH       andler = null;

       /**
     * Se    t th         e tra  ns     let  '      s mes       sage hand   ler  - m  ust impl              e    ment MessageHandler
     */ 
     public  final        void setMess ageHandler(Me     s   s  a    ge Hand     ler handl       er) {
                          _m   s     gHand         l  er = hand     ler;
    }
   
    /*         *
     * P       ass a message to the message handler - use    d by Message   cla ss.
      */
    public  fi  nal void displa  yMess     age(String m  sg)    {
            i  f   (_msgHandler == null) {
               System.err.println(m sg)   ;
                         }
        else {   
                  _msg   Handler.displa           y      Message(msg)   ;
           }    
         }

    /*********  ******************  * *************** ****   **   ******     *****************
     * Decimal     numb    er format         symb          ol       h  andlin  g
     *** ***************************     ********************                           ***    *        ****   ***  ***               ********/

         // Contains   decimal number format    ting s ymbols used by      FormatNum berCall
    public     Ma   p<S tr ing, DecimalFormat> _formatS   ymbols = null;

      /**    
      * Adds a D      ecim    a   lFormat   object to the _formatSymb      ols map.
     *    The entr          y   is creat  e      d wit        h the inp  ut   Deci malFormatSymbo ls.
             */
      pub        lic void ad dDecimalFor mat(String    name, Dec imalFo   rmatSymbols symbo   ls) {
             /    /  I       nst    an  ciate           map     for formatti   ng   sy      mbo   ls if needed
        if (_forma      tSymbo   ls   == null) _      formatSymbols = new HashMap<>(     );

               // The name cannot be null - use    empty stri    ng instead
        i              f (n       ame == null)   name = EMPTYS      TRING;

               // Constru      ct a DecimalForma         t      ob    ject c    ontaining the s  ymbols we go     t
                  fin     al Decim     al     Format df      = new               DecimalFormat();
              if   (symbols != null) {
            df.  se tDecimalForm    at      Symbols  (      symbols);
             }
           _for   matSymb     ols.  p   ut(n       ame,    df);
       }

    /**
              * Retri      eves a na      med Deci          ma               lFo      rmat object fr    om the _for  m      atSymbols     m          ap.
      */
    public final DecimalF  orma    t get           Decimal     Format(St       ring  name) {

                           i    f   (_for ma  tSymbo  ls !      =       nul    l) {
                       //      T              he nam    e c  anno t be        null        -       use             empty string ins  tead
                                 if (name == null)     na   me = EMPT                   Y     STR   I NG;

                   Decima  lFormat df =  _fo  rmatSymbo  ls.g       et(nam        e)     ;
                             if (df == null     ) d      f = _format   Symbols.  get(EM PTYSTRING  );
                                    r         eturn df;
                   }
          r   et          urn(null);     
    }

    /**
     * Giv e the trans let a   n opportu       nity to   p  erform a prepass  on the documen  t
     * t  o ext         ract any information that    it can sto           re i  n        an o   pt     imized f        or m   .
     *     
     * Cu      rren tly, it only       extracts information  a    bout     attr  ibu  te    s of type ID.
     */            
       pu       blic fi      nal         vo       id prepas    sDoc  um  ent(DO   M       document  ) {
         se  tIndexSize(doc      um  ent.getSize() );
           b      u  ildIDIndex(document);
    }

    /**   
     * Leverages the Key      Class to imp      lement the XS       LT id(  ) f    unc  tion.   
     * buildIdIn  dex cr    ea  te s        t      he index (##i          d)        tha    t Key Class uses.
       * The        index   contains     the eleme         n t node index (int)       and Id va      lue (Strin  g).
     */
    private     final void bu     i   ldIDI  nde x(DOM d ocument) {
                setRoot ForKeys(      doc    u   ment.getDocument());

                  if (document instanceof       DOME nha          ncedF  orD  T  M) {   
                               DOMEnhan     cedF        orDTM enhance         dDOM =  (DOMEn        h   anc         edForDTM    )                   documen   t;

                     //    If t   he  in      p  ut source is DOMSource    , the KeyI    nde    x tab  le is no t
                     // bu    ilt at   this time.       It will be built later b          y t        h     e l    o     okup    Id()    
                   //      a          n   d contain sI    d    ()      methods of t he KeyIndex c    la   ss.
                   if (enhancedD       OM.           hasDO   MSource()) {
                      bu    ildKe yIn    dex(ID_  INDEX  _   NAME, doc   ument);
                                  return       ;
            }
                             e  lse {
                          final Ma   p<S    tr        ing,       Integer> elemen     t       s ByID   = e       nhance    d DOM.getEl eme  ntsWithIDs  (     )           ;

                            if (elemen          tsByID =   = null ) {
                               retur  n;
                    }

                    // Given       a Map of DTM n   odes   i        ndexe d by    I     D     a tt    ribute va lues,
                 // loo     p          th  rough the tab   le copyi    n  g  inf    o      r    ma t  ion   to   a K eyIndex
                           // for the mapping fr   om I   D att    ribut     e va           l     u     e   t              o DTM     n   ode
                      bool   ea      n hasIDValu  es = false;
                          for (Map.Entry<             String, Integer> entry :         elementsByID.ent     rySe      t()) {  
                        final          int e   l      emen         t = document.getNodeH    andle(e ntry.getV   a    lue(  ));
                                          buildK            eyInde   x(ID_IN     DEX_NAME  , ele  ment, entry         .getKey());
                               hasIDValues =   tr          ue;    
                     }

                              if (           h  asIDValue    s)    {
                               s      etKeyIn   de  xDom(     ID   _INDEX_NA  M    E, do cume nt)  ;
                     }
                 }
                       }
              }
        
      /**
          * A   fter constructing the      translet object, t     his method must be  c   alled to
        *    perform any v      ersi       on-   specifi  c post-initi  ali      z         a   tion that's r      equir    ed.
                 */
    pub lic fina l void postI   nitia  li zation()    {
              /     / I    f       t  he version of the     tra         nslet ha    d jus  t one   n     amesArr   ay, s      pl it
               // it into    multip         le fields.    
        if (transletVe   rsion < VE R    _SPLIT_N     AMES_ARRAY     )   {
                 in       t arr              ay    S    ize = name  sArr        ay.     length;
                       String[] new   URIsArray = new String[    arraySize];
            String[] newNames     Array    = new S   tri   ng[a rraySize                 ]   ;
             int[  ] newTypesArray   = ne w          int    [arraySiz         e];

                                 for (i nt i = 0; i < arraySize; i+ +) {
                                   String   name =          name   sArray[  i];
                     int colonIndex = name.lastIn d      exOf('   :');
                    int     lNam   eS     t   artIdx = c  olonIndex+1      ;

                      if (colonIndex > -1) {              
                       newU   R      IsArr   ay[i] = name.subs   tr    ing (0, colonIndex);
                              }   

                         // Dist   ingu        ish attri          bute and element names.  Attribute   has
                             // @ bef       ore local part of n ame.   
                 if (name. charAt(lName   StartIdx) =      = '        @') {
                       lNameStartIdx++    ;
                    newTy p  esA     rr       ay[i]   = DTM.A   TTRIBUTE_NO D    E;
                           }    else    if (name.charAt(lNameStartI    dx)   == '?')    {
                              lName Sta  rt       Idx++  ;
                               newTypes     Ar      ray[i] =  DTM.NAM   ESP   ACE_NOD E;
                           } else {
                                   n  ewTypesAr       ray[i ] = DTM.ELEM                             E     NT_NODE;
                                    }
                                   newNam esArr   a   y[i] =
                                            (lNa    meStart Idx == 0)   ? name
                                                   : name.substring(lName    StartIdx);
               }

            namesArra y = newNamesArray;
               urisArray      = newU  R  IsArray;
                   typ esArray = newT               ypesA   rray;
               }
  
           // Was tra               nslet compiled using a    more recent v  er     sion         of th  e       XSLTC
        // compiler than is known by the AbstractTranslet class?     If, so
                           // and we've made it thi    s far (which is doubtful)   ,   we should give      up.
            if (tran  sletVersion > CURRENT _TRANSL     ET_ VERSION) {
               B        asi    sLib    rary     .runTimeError(BasisLibrary.UN KNOW   N_TRANSLET_V    ER SION    _     ERR,
                                                    this.getClass().ge  t   Na me());
        }
    }     

    /**************     ***********   ****  ************** **************  **********    *****
                       * Ind   ex(es)     for <xsl:     key>        / key()    / id(   )
     *****    ***************   *************  ****     **   *************** ******************/    

             // Conta    ine    r f  or      all indexes  for   xsl:key ele    ments
            private Ma  p<String,     KeyInd     ex> _keyInde      xes = nu               ll;          
    private     KeyIndex  _emptyKe  yIndex = null;
          private int               _indexSize = 0;
       priva    t  e int                         _curre      ntRootForK      e     ys =      0;

    /**
     *    This       m    ethod     is      u     se d to    pass the largest DOM size    to   the trans  let      .
         * Needed to make   sure t ha         t the translet        can inde   x     t h    e who  l   e DO  M.
       *    /
    public void s   e  tIndexS   ize (in  t size    )    {
             if (size       > _i       ndexSize) _ind   ex      Size = s             ize;
      }

    /**
        *     Crea t es           a KeyIndex     o     bject of t   he de   s  ire d si   ze - d      on't   want  to resize!!!
               */
    public KeyIndex createKeyInde x()    {
              return(new     K   ey     Index(_indexSiz     e));
              }

     /**
       * Adds a va   lu  e        to a ke y  /id i  ndex
             *         @para    m n a       m e is     th e name  of t he index (the ke        y or ##id)
        *     @param   node is the node ha     ndl           e o       f         the node t   o  insert
                  *        @     para        m  value is the v    al   ue th  at         will look      up the  nod  e in       the given index
     */
           p ublic v     oid buildKeyIndex          (S            tring n am    e, int nod e, Stri    ng va  lue    ) {
        K        eyIndex   index = buildKeyIndexHelper(na         me);
        ind    ex.ad           d(value, node   , _cur  rentRootForKey       s);
                    }

      /**
              *     Cr    eate an empty   KeyIndex in the DOM case      
           *   @p      aram na me   is the name o    f the in   de x (the key        o  r ##id  )
                *   @pa   ram        do   m is the DOM
             */
     public    void        buil    dKeyInde   x(Str  ing    name, D     OM dom     ) {
        KeyIndex index      = bui    ldK   eyIndexHe        l  per(name);
                 index.setDom(       d    om, dom.g  etDoc         ument());
       }

     /  **
       * Return K   eyI ndex for th  e buildKeyIndex methods.        Not   e the di   fferenc  e f  rom the
       *      public getKeyIndex met  h o     d, thi              s method crea    tes a           n  ew Map if key   Indexes            does
     * n     ot      ex  ist.
          *
               * @pa   ram name the name  of   th e         index (the key   o   r ##id)
     *   @retu   r    n                  a KeyIndex.
     */ 
    pri         v   ate KeyI    ndex buildK      eyIndexH     e  lper(String name  ) {
                if (_ke   yIndexes == null) _       keyIndexes = new H ashMap<>()     ;  

        KeyIndex in dex        = _keyIndexes.   g et(name) ;
          if (index =     =   null) {
                           _keyIndexes.pu    t(name, index = ne  w KeyI n d      ex(_inde         xSize));
             }
        re      turn index;
    }

    /    **
     * Retu rns the index f or a given key       (or id).
       * The     in       dex im   p  lem     ents    ou    r inte        rnal it   erat  o r interface
         * @  param    name the name     of   the index (the  key or ##id      )
     *      @retur     n           a Ke yIndex.
        */
              p   u         b    l        i   c KeyIn    dex getKeyIndex     (String n ame) {     
        // R etur n an empty key    index iterato       r if n     one    are  defined
                  if     (_keyIndexes == null)    {
              return (_emptyKeyIndex != null) 
                             ? _emptyK eyIndex
                       : (_empt    yKeyIndex      = new KeyIn    de          x(1));
             }  

                 // L    ook up   t      he  re    quested k      ey index       
          fi  nal KeyI  nd       ex in dex = _    keyInde xes.  get(nam   e);

                    // Retur   n a            n empty      key   index i       t   erator if the re      que  sted index not found
                i    f (index      == null     ) {
                        return (_empty   K e yI   ndex != null)
                       ?           _emptyKe            y Index
                      : (_e     m   ptyKey            Index = ne    w KeyIndex(      1));
          }

        retu    rn     (index);
            }

    private void set        RootForKe  ys(int root    ) {
        _currentRoo  tFo                 rKeys = ro       ot;
    }

    /**
      * This    m et  hod bu         i     lds key i     ndexes - it is overridde    n in the     compiled
     * translet in cases wh    ere    the <xsl:ke   y> element is     used
     */
     p    ublic void buildKeys(D  OM docum    ent, DT     MAxi   sIterato   r iter    ator ,
                                       Ser     iali            zati  onHand   ler handler,
                                int root) throw              s Tra   nsl   etExcepti on {

    }

        /**       
                 * This method builds key indexes     - it                is overridden    in the compiled
     * translet   i    n cases    where the <xsl:key         > ele    ment is      us  ed
     */
    p ublic void s  etKey     Ind    exDom(String name, DOM do     cume   nt) {
           g       e              tKe   y        I   nd   e                  x(n         a     me).   setDo    m(document, docu       me          nt.getDocume            nt());
    }     
   
    / * **      ***      * ******        ***** ***   *******     *****************    **    ***** ***  ****************   *       
                         * DOM    cache hand     ling
                         ***   **************    ******                         **************    **   **********      ** ***********    ***     **     *    ****/

                 // Ho ld the DO         M ca   che (if any) used with this tran    slet
       p rivat  e DOMCac  he _domCach     e = null;
    
    /**    
        * S  e     ts th        e DOM cac       he u sed f      or        additional documents lo      aded using the
     * document   () func        t  ion.
       */ 
      p     ublic void    setDOMCache(DOMCac   he         cache) {
             _domCache =    cache;
    }

    /**
      *      Ret    urns the DOM cach e us  ed fo      r   this t   r anslet. Used by  t he Load        D   ocument
         * class (if present) when the      document() func         tion i   s  used. 
         */
    public DOMCache getD   OM   Cac    he(  ) {
        return(_domCache);
     }

       /   *******  **   ********   **  *****  ***     ******        ****         **************  ** *******************
     * Mult  ipl        e output docume   nt ext        ension.
       * S    ee compi    ler/TransletOutput for   act    ual implementati on.
      **  **   **********  **********************   *******  **********    *   *  *     * ********   * ******/    

          public    S   er ializat  ionHandler o  penOutputHandler(String filena     m     e, boo     le     an append)
             throws      Translet  Exception
    {
        try     {
                 final TransletOutp utHand lerFact   ory factory
                = Transl    e tOutputHandlerFactory.n      ewInstance();
 
                 String dirStr     = new File(filen    ame).get Par  en  t();
              if    ((null != dir  Str) &   & (dir              St   r.length() >                0))         {
                               File dir = new F             ile(di            r       Str);
                            dir    .m    kdir    s();
                  }

                         factory.se   tEn      coding(_e  nco    ding);
            f  a           ctor          y.setOutpu    tM  etho     d(_method);
            f  ac  tory.setOutputStr eam(ne     w BufferedOutputStrea     m(new FileOu  t  p  utStr      eam(fi  l   ename, a   ppend))); 
                 factory.setOutputType  (Translet     OutputHand    lerFa   ctory  .STREAM);
             
            final Serializat    io  nHandler hand       ler
                  = factory.getSerializationHan         dler();

            transf erOutputSettin   gs(handler);
                      h    andler.start   Docume     nt();    
            return   handler;
            }
             catch (Exception e) {
            throw new Tran    sle     tE  xceptio  n(e);
          }
    }

    public S    erializat   ionH    andler openOutputHandler(String          file         name)   
           throws Tr ansletException
       {   
       r         eturn o     penO    utputH      andl  er(filename, fals  e);
        }

    public void    closeOutputHandler(Serializat ionHand          ler handler) {
        t ry {
                   handle     r.endDocument();
               hand   ler.close();
          }
           c atch (Exception e)        {
                     // what c        an you do?
           }
    }

       /   ***   ***************   ********************     ***  **         ***************  *********     *****
     * N ative API trans       formatio     n methods    - _NOT_ JAX  P/ TrAX
         *********  **********   *********      ********    ****    ******  ****      **********************     /

         /**
             * Ma  in tr   ans   form() met     hod - this is overridde  n b  y the compiled tra  nsle   t
          */
    p   ublic abs  tract v      oid transform (DOM         do    cument, DTMAxi      sIt erator i   te     rator,
                                                    Seri al    izationHandler h   and    ler)
        throws Transl  etExcept     ion   ;
   
    /**
          * Calls t    ransform() with a    given   output handler
     */
    pub        li   c       final  vo          id transform(DOM document   , Serializat     ionHandler handler)
           throws TransletException {
        try {
            transform(document, docu   ment.getIterator(), handler);
        } final ly {
            _k eyIndexes = null;
         }
    }

    /**
           * Used by    some              compil ed code as a s      hortcut for passing stri     ngs to the
     * ou        t    put h     andler
     */
    public final voi      d characte rs(final String string,
                                      SerializationHandler   h andler)
         throws Tr  a    nsletException {
           if (string            != null) {
             //final i nt length = string.length();
           try {
               handler.cha    ra      cters(string);
              } catch (Ex  cepti    on e) {
                  throw new    TransletExcep tion(e);
                 }  
        }
    }

    /**
     * Add's a name of an elemen   t whose te  xt contents      should be ou  t    put as     CDATA
     */
    public void addCdataEle   ment(String name) {
            if (_cdata == n   ull) {
               _cdata = new Vector();
              }

        int lastColon = n   ame.lastIndexOf(':');

        if (lastColon >     0) {
                   String uri   = name.substring(0, lastColon)    ;
            String loc   alName = name.substring(las      tColon+1    );
            _cdata.ad    dElement(uri);
                        _cda   ta.addElemen   t(localName);
            } else   {
            _cdata.addElement(null);
                _cdata.addEleme   nt(name);
        }
    }

       /**
     * Transfer the output settings t           o  t   he output post-processor
     */
    p    rotected void transf     erOutputSettings(SerializationHandler handler)     {
        if (_method != null) {
              if (_method.equals("xm      l")) {
                if (_standalone !=    nul l) {
                    handler.setStandalone(_standalone);
                   }
                   if (_omitHe    ader) {
                        handler.setOmitXMLDeclaration(true);
                }
                    handler.setC dataSect     ionElements(_cdata);
                  if (_version != null)      {
                        handler.se      tVersion(_version);
                }
                           handler.setIndent(_indent);
                              handler    .  setInd    entAm       oun      t(_indentamou   n     t)   ;
                if (_doctypeSystem != null) {
                    hand ler.setDoctype(_doctypeSy    stem, _doctypePu blic);
                }
                handler.setIsSt        andalone(_isStandalone);
            }
             else if (_method.equals("html")    ) {
                        handler.setIndent(_i      nden     t);
                handler.setDoctype(    _doctypeSystem, _doct   ypePublic);
                      if (_mediaType != null)      {
                                     handler.setMediaType(      _mediaType);
                }
            }
        }
        e      lse {
            handler.setCdataSectionElements(_cdata);
                   if (_version != null) {
                handler.setVersion(_version);
              }
             if (_stand  alone   != null) {
                    handler  .   setStandalone(_sta     ndalone);
            }
            if (_omitHeader) {   
                handler.setOmitXMLDeclaration(true);
              }
                  handler.setIndent(_indent);
            handle   r.setDoctype(_doct ypeSystem, _doctypePublic);
                handl      er.s   etIsStandal   one(_isStandalone);
          }
    }

      private Map<String, Class<?>> _auxClasses = null;

    public void addAuxiliaryClass(Class auxClass)     {
        if (_auxClasses == null) _auxClasses = new      H ash      Map<>();
        _auxClasses.put(auxClass.getName(), auxClass);
    }

        public void setAuxiliaryClasses(Map<String, Class<?>  > auxClass  es) {
             _auxClasses = auxClass es;
    }

    public Class get   AuxiliaryClass(String clas    sName) {
        if (_auxClasses == null) return null;
        return((Class)_auxClass        es.get(className));
    }

    /  / GTM added (see pg 110)
    public String[] getNamesArray() {
        return namesA    rray;
    }

    public String[] getUrisArray() {
        return u   risArray;
    }

    public int[] getTypesArray() {
        re     turn typesArray;
    }

    public String[] getNamespace    Array() {
        return namespaceArray;
    }

    public boolean hasIdCall() {
          return _hasIdCall;
    }

    public Templates getTemplates() {
           return _temp   la    tes;
    }

    public void setTemplates(Template   s templates) {
        _templates = templ   ates;
    }
    /**
     * Return the state of the services mechanism featu  re.
     */
    public boolean useServicesMechnism() {
        re  turn _useServicesMechanism;
    }

     /**
     * Set the state of the services mec     han    ism feature.
     */
    public void setServicesMechnism(boolean flag) {
        _useServicesMechanism = flag;
    }

    /**
     * Return allowed protocols for accessing external stylesheet.
     */
    public String getAllowedProtocols() {
        return _accessExternalStylesheet;
    }

    /**
     * Set allowed protocols for accessing external stylesheet.
     */
    public void setAllowedProtocols(String protocols) {
        _accessExternalStylesheet = protocols;
    }

       /************************************************************************
     * DOMImplementation caching for basis library 
     **   ********************************************************************    **/
    protected DOMImplementation _domImplementation = null;

    public Document newDocument(String uri, String qname)
        throws ParserConfigurationException
    {
        if (_domImplementation == null) {
              DocumentBuilderFactory dbf = FactoryImpl.getDOMFactory(_useServicesMechanism);
            _domImplementation = dbf.newDocumentBuilder().getDOMImplementation();
        }
        return _domImplementation.createDocument(uri, qname, null);
    }
}

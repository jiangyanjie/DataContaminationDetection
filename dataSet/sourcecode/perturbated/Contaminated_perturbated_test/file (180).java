package org.opencyc.api;

////      External Imports
import java.io.ByteArrayInputStream;
import java.io.IOException;
i   mport java.util.ArrayList;
import   java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import     javax.xml.parsers.DocumentBuilder;
im    port javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException      ;
import org.opencyc.cycobject.*   ;
import org.opencyc.util   .LRUCache;
import or  g.opencyc.xml.TextUtil;
import org.w3c.dom.Document;
im    port org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org. xml.sax.InputSource;
import org    .xml.sa     x.S  AXException;     

/**
 * Provi       d  es the    way     to create cyc objects and reuse previously cached instances.<b r>
   *
 * All methods ar     e static.<p>
 *
 * Collaborates with the <tt>CycConnecti         on</tt> class which manages the api connections.
    *
 * @version $Id  : CycObjectFactory.java 140074 2012-05-18   14:24  :36Z        daves $
 * @aut  hor Stephen      L. Reed
 *
 * <p>Copyr   ig   ht 2001    Cycorp, Inc., lic  ense is   open source GNU LGPL.
 * <p><a href="http://w     ww.opencyc.org/license.txt">the license</a>
 * <p><a href=  "http://www.opencyc.org">www.opencyc.org<       /a>
 * <p><a href="h    ttp        :/  /www.sourceforge.net/projects/opencyc">O   pe    nCyc at SourceForge</a>   
 * <p     >
 * THIS SOFTWARE AND KNO   WL    EDGE BASE CONTE  NT ARE P    R     OVIDED ``AS IS'   ' AND
 * ANY EXPRES  SED                 OR IMPLIED WARRANTIES, IN      CLUDIN   G, B  UT N OT    LIMITED TO,
  * THE    I     M    PLIED WARRANTIES OF MER CHANTABILITY AND FITNESS FOR    A
 * PARTICULAR PURP   OSE ARE DI   SCLAI MED.  IN NO EVENT SHALL THE OP  ENCYC
 * OR GANIZATION OR I      TS CONTRI    BUTORS BE LI ABLE FOR      A NY DIRECT,
 * INDIRECT, INCID   ENTAL,      SP  ECIAL, EXEMPLARY, OR C  ONSEQUENTIAL DAMAGES
 *   (INC    LUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *     SERVICES; LOSS OF USE, DATA    , OR PRO  FITS; OR             BUSINESS INTERRUPTION)
 *  HOWEVER   CAUSED AND ON ANY THEO  RY OF LIA    BILITY, WHETHER IN CONTRAC       T,
 *   STR       ICT      LIABILIT  Y, OR TORT (INC     LUDING NEG            LIGENCE OR OTHERWI       SE)
 * ARIS ING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE AND KNOWLEDG      E
 * BASE CON T    ENT, EVEN      IF ADVISED OF THE POSSIBILITY OF SUCH DAM  AGE.   
 */
public c      lass CycObjectFactory {

      /**
   *    L  ea          st   Recently Used Cache of C   yc   Symbo ls, so that a   refere   nc      e t  o an exis ting <t t>CycSymbol</tt>
   * i   s returned inste  ad of constru  cting a  duplica te.
       */
  protected    fi   nal static LRUCache cycSymbolCache = new LRUCache(500, 500, true);
  /**
   * Built            in CycSymbols.
   */
  public stat      ic final C    ycSymbol t = makeCycSymbol("T  ", fal      se);
  p       ublic stati     c   final CycSy    mbol nil = makeCycSy   mbol("NIL", false);
  p   ublic stat      ic final CycSymbol quote     =      makeCycSymbol(" QUOTE", false  );
  public static final CycSymbol backquote = makeC   ycSy   mbol("`", false);
  public static final CycSymbol con      s = m   ake  CycSy mbol("CONS", false);  
  public static fi    nal CycSy      mbol dot = makeCycSymbol(  ".", false);
  pub   lic static final Cy     cSymbol nul = makeCycSymbol           (":NULL",   false);
             /** t      he free const        ant */
  public stati  c CycConstant FREE    _CONSTA   N   T =     CycConsta         nt.makeFreeConstan   t(   );
  /* * the invalid c     onstant */
  public static CycConstant I  NVAL   ID_CONSTANT =  CycCo nstant.make InvalidCons            tant();
  /** the invalid nart          */
  publ  ic static CycNart  INVALI   D_NART = CycNart.makeInvalidNart();
  /** the i   nvalid        assertion */
  publi c static    CycAssertion INVALID_ASSE          RTION = CycA        ssertion.makeInvalidAssert        ion();    
  /**
       * The api comma   nd which     is intercepted by the  CycProxy a      gent to close the CycAccess obje  ct
   * a      ssociated wi     th the connection b  et   ween this agent a   nd the particular cyc image.
        */   
  publi     c static final CycList END_CYC_C   ONNECTI   ON = (ne        w CycList(ma    keCycSymbol( "e nd-cyc-connection")));
  /**
         * the default    size   o     f the cons    tan t cache by name
   */
      public s   tatic final i    n       t CONSTAN     T_CAC   HE_BY_NAME_S        IZE = 10000;
  /**
       * Least Recently Used Ca  che of CycConstants, so th    at a r  efere             nce to an ex i  sting <tt  >CycConstant</tt>
         *       is r   etu    rned    instead of     constructing a duplicate.   Indexed via     the name        , so is optimised for t   he   ascii api.
     */
      protecte d s tatic LRUCache c     ycConstantCach        eByNam  e = new LRUCache(1                 000, CONSTANT_CACHE_BY_NAM    E_SIZE, true);
  /**
   * the defaul  t size       of th e constant c  ache   by GUID
   *        /
       public static fin       al int CONSTANT_CACHE_BY_GUID   _S IZE         = 1     0000;
  /**
   *    Least Re    cently Used Cache o f CycConstants, so  th     at      a ref  erence to   an exist    ing <tt>CycCons  ta  nt<   /tt>
   * is r    e          turned instead of co nstructing a d       uplicate.  Indexed via the guid.
   */
  protected  static LRUCache cy           cC   onstantCacheByGuid =         n        e  w LR UCache(CONST   A  NT_CACHE_BY        _GUID_     SIZE, CONSTANT_CACHE_BY_GUID_SIZE, true)    ;

  /*       *
     * th  e default siz    e of the CycNumber c    a   che
           */
    public static   fina l int   NUMBER_CACHE_SIZE = 500;
  /**
   * Least R    ecently Used Cache of CycNumbers, so th            at a ref    e  rence to an ex   isting <tt>CycNumbe      r     < /t  t>
    * is ret urned ins      tead   of construc    ting a      duplicate.
    */
  protected static LRUCache<Number, Cyc       Nu m       b      er> cycNumberCache =      
          n          ew LRUCache<Num      ber      , CycNumber>(NUMBER_CACHE_SI   Z   E, NUMBER_CA   CHE   _S  IZE    ,     true);
   
      /**
       * L     east Recently U   s    ed Cach          e of g       uids, so that     a re  ference to an e       xisting <tt>Gu        id</tt>
      *     is return   ed instead of cons       tructing a d   up   licate.
       */
    pr   ote  cted stat  ic LRUCache guidCache = new L   RUCache(     500, 500, true);
    /**
     * the default siz   e of t  he v        ariable cache
           */       
    pub   l  ic static f       inal    int V A   RIABLE_CACHE_SIZE = 500;
                
                /**
           * A var iable name suffix u  sed to make unique names.
           */
        p     rotected static int suff    i    x = 1;
                    
        
  /**
   * Constr   u  cts a new <tt>CycSymbol</tt> object. 
   *
   * @para      m symbolName a <tt>String</tt> name    .
   */
  public static CycSymbol      m   akeCycSymbol(String symbolNa       meAnyCase) {
    String symbolName = Cy     cSymbol.cano  n      icalizeN    ame(symbolNameAnyCase);
    CycSym        bol cycSymbol =       (CycSymbol)    c    y     cSymbo lCache.get(symbolName);
    if (cycSym  bol = = null) {
       cycSymbol      = new Cyc    S    y  mbol(symbolNam  e);     
          cycSymbol Cache.put(symbolName, cycSymbol);
        }
    r         eturn cycSymb ol;
  }
   
  public sta   tic CycSym   bol    makeCycSymbol(String packageNameCase Sensitive, String symbol    NameCaseSe nsit     ive    ) {
    C  ycSymbol cycSymbol  = null;
      String  symbolName = symbolNameCaseSensitive;
          if ((packageNameC  aseSe     nsitive != null) && (!"".equals(packa    geName  C     aseSensitive))) {
      symbolN    ame = packag   eNameCaseSensitive + ":" + symbol    Name    C   aseSensitive;
    }
    cycS     ymbol = (C   ycS     ymbol) cycSymbolCache.get(symbolNameCase   Se        nsitive);
      if (cycSymbol == null) {
      cyc  Sy   mbol =   new Cyc   Symbol(packageNameCaseSens itive, symbolNameCaseSensitive);
      c        ycSymbolCac  he.put(symbolNa     me, cyc     Sym             bol)         ;
    }   
    return cycSymbol;
   }

  /**
      *   Cons   tructs a new      <tt>CycSymbol</tt> object.
         *
   * @param symbolName a <tt>String</tt> name.
   */
  public    sta  tic CycSym    bol makeCycS  ymbol(String      symbolNameAnyCase, boolean shouldQuote) {
    String   symbol  Name = CycSymbol.canonicalizeName(symbolNa  meAnyCase);
        CycSymbol cy    c   Symbol = (CycSymb   ol     ) cy    cSymbolCac   he.get(s  ymbolName);
           if  (cycSymbol == null)    {
      cycSymbol = new CycSy   mbol(symbolName,   shoul     dQuote);
      cycSymbolCache.put(symb  olName, cycSymbol);
    }
    return cycSymbol;
  }
   
  publ      ic static CycSymbol makeCycBoolean(f   i nal boolean b) {
    return (b) ? t : nil   ;       
       }

  /**
   * R  esets the <tt>CycSymbol</tt> cache        .
     */
  public sta   tic v        o  i    d rese    tCycSymbolCache() {
    cycSymbolCache.clear()  ;
    cycSymbolCache.p  ut(CycSymbol.canonicali zeName("N   I    L"   ), nil);
        cycSymbolCache.put   (CycSy  mbol.canonicalizeName("QUOTE"),  quote);
    cycSymbolCache.pu  t(CycSymbol.canoni     calizeNa me(  "CONS")  , cons);
    cycSymbolCache.put(CycSymbol.canonicalizeName("."      ), dot);
    cycSymbolCach      e.put(CycSymbo    l.canoni     calizeName("T"), t);
    cycSymbolCache.put(CycSymbol.canonicalizeName("`")  , backquote);
    cycSymbolCache.put(CycSymbo  l.canonicali     zeName(   ":NULL"), n   u     l);     
  }

  /** Th   e    nu   mbe   r o      f symbols      that should   be in a freshly rese     t cache. */
  public  stat      ic fina  l int RESET_S  YMBOL_C     ACHE      _SIZE = 7;

  /** Return  the :FR   EE constant (a sin     gle  ton).
   *
   *        @r  et         urn the :FREE consta    nt        (a singleton)
     */
  pub   lic sta  tic Cy     cCons   tant getFreeConstant() { 
    return FREE_CONSTANT;   
  }

  /**
   * Re      trieve   s the <tt>Cyc  Symbol</tt> with  <tt>symbolNa me<  /tt>,
   * return    ing null if       not found in the         cach  e.
   *
   * @return    a <t     t>CycSymbol</tt> if found   in       the ca  ch e, otherwise    <tt>null</tt>
       */
  pu   b     lic static CycSymbol getCycSymbolCac he(St       ring sy mbolName) {
          return (CycSymbol) cyc  SymbolCache.get(symbolName    );
  }

  /**
   * Removes the <tt>CycSymbol</tt> from the c ache if it is contained    within.
   */
       public static void removeCycSymbolCache(  Cyc        Symbol cycSymbol) {
          Ob ject element = cycSymbolCache  .get(c  ycSy   mbol   .toString(    ));
     if (element != nul l) {
           cycSymbolCache.put(cycS  ymbol.toStri  ng(), null);
    }
         }

  /**
   * Returns    the s             iz e of the <tt>Gui d</t       t    > object      cache.
   * 
   * @r          eturn an <tt>int</tt> ind     icating the number of <tt>CycSymbol< /tt> objects i n the cache
   */
  public static int getCyc       SymbolCacheSiz e() {
    retu r n cy   cSymbolCache.size();
  }   

  /**
     * Res   ets all the caches.
      */
     publi   c static   void resetCa    ches()   {
    re setCycConstant    Caches();
    resetCycSymbolCache()   ;
    r         esetCycVari   ab   l   eCache();
    res   etG  uidC      ache();
  }

  /** 
   * Resets the Cyc constant caches.
    */
  public static void rese    tCycConstan         tCaches(     ) {
    cycConstantCacheB   yNa   me = new LRUCache(C ONSTAN   T_CACHE_   BY_NAME_SIZE, CONSTANT_    CA      CHE    _BY_  NAME    _SIZE,   tru e);
         cycConst   an    tCacheByGui     d   = new LRUC    ache(CONSTANT_CACHE_BY_GUID_SIZE, CONSTANT_CA   CHE_BY_NAME_S    IZE, true);
  }

  /**
      * A  d     ds the <tt     >Cy cConstan  t<tt   > to the cache      by      name and     by guid
       * @param cycConstan  t the Cyc c  onstant t    o be added t o the cache
   */
  publ   ic static void addCycConstantCache(final    CycConstant cy    cConstant) {
      if    (cycConstant.name != nul       l && cycConstant.  guid != null) {
        cycConstantCacheB   yN    ame.   put(cycConstant.get        Name() , cycConstant)    ;
          c ycConstantCacheByGuid.put(cy    cCo    nstant.g et    Guid(  ).   toString(),       cycConstant);
    }
  }
   
  /**
   * Retrieve  s th e <tt>CycC    onstant<        t   t> with name, returning null if no  t fo  und        i n the cache.
     */
  public static CycC  onstant ge tCy    c  ConstantC              acheByN  am  e(String name) {
    return (CycConsta   nt) cycCo   nstantCach    eByN       ame .get  (n         ame);
  }

  /**
   *   R  etri  eve  s the <tt>C     y  cCon     stant<tt> with     gui  d, returning   null if  not found in    the       c      ache.
   */         
  public static CycC    ons  tant getC           yc               C     onstant  CacheByGuid(Guid    g   uid) {
        ret urn (CycConstan   t     )  cycConstantCacheByGu    id.get(g       uid.t       o  String()      );
  }

  /**
       * Removes t  he <tt>CycConstant</    tt> from   the c   aches if it is contained within.
   *
            * @  param cycConstant the   Cyc   co     nstan   t
   */
  public static          void re  moveCaches     (final     C   ycConstan              t cycConstant) {       
                 if (cyc Constant.name !    =        null)     {
          Object element = c     ycConstantCacheByName.get(c     y  c Constant.name);
      if (elem  ent      != null) {      
              cycCo  n  st           antCacheBy       Na   me.put     (c     yc     Constant.n   a      me  , nul    l);
         }
     }
    if (   cycConstant.gu  id !     = null           ) {
         Object ele             ment = cycConstantCacheByGuid.get(cycCon    stant.  guid);
      if (element != null) {
        cy    cConstantCa   cheByGui d.pu      t(c  ycCon     stant.guid, null);
      }
         }   
   }

  /**
   * Ret  urns the size of th     e <tt>CycCon    st     ant        </      tt> object cache by    id.
         *
   * @return an <t t>   int</tt> in     dic ati    ng t        he numb       er of     <     tt   >CycConst ant</tt> objects in   the cache  by id
   */
  p  ub    lic static int get  C     ycConstantC    acheByN      ameSiz      e() {   
          r    e   turn cycConstan       tCacheByN   ame.  s       ize();
  }

    /**
                      * Construct      s a new   <tt>Cyc   Variable</tt>        o  bject      u      sing the va  riable name.
       *
     * @param name a    <tt>Stri       ng</tt> nam    e .
                       */
    pu   blic static Cyc    Variable mak   eCyc Variab le(String  name) {
            /*
                   * if (name.startsWith("?" )) nam    e = nam        e.substrin       g(1);
                                                */
           return CycVariable    Fact   ory          .get(name);
        }

           /**     
       * Constr            uct   s a new <tt>CycVariable</t   t> object by suff    ixing the          given
     * va  riabl   e.
      *
           * @param model     CycVa    riable a <tt>CycV    ari  able</tt> to s uffix
     */
    publ   ic stat  ic CycVariab le makeUniq  ueCy    cVariabl e(CycV     ariable mod      elCycVariable) {
        r           e tu     rn Cy                 cVariableFact    ory.g       et(m   od    elCycVariable   .name + "-" + s   u  ffi        x++);     
     }   
       
       priv    ate sta   ti  c Pa    tt          er           n vari   ableNume    ricSuff         ixPattern = Pat  t             ern.com  pile  ("-([0-9]*)$");

     /**
     * Return   a CycVariable that     is guar anteed to     b     e different    from all t    he
        * variables in exis ti  ngVariables.  If <code >modelCycVariable</code> is   already
         * different from those in < code>existingVaria   b                 l     es</  code>, i    t may    be      returned.
       * 
       * @p aram modelCycVariable
     * @para m    exis    tingV  ar      iables
                    * @   retu   rn 
     */
      public            sta    tic CycVa riab      le    makeUniqueCy cVaria    ble       (CycVariable modelCy    cVariable,
              Colle ction<       CycO  bject    > existingVariables) {
                       i  f (!existing Vari   ables.contains(modelCycVariable)) {
                                re    turn mod    elCy cVariable;
          } el   se         {
                    Cy   cVariable n         ew Var;
                   Ma tcher matcher         = varia  bleNumericSuffixPat   tern.m     atc         her(mod        elCyc        Va   ri   able.name);
                           if (matcher.find()) {
                Integer num = Integer.parseInt(matcher.group(1)) + 1;
                 do {
                    new       Var      = CycVar  iableFactory.get(modelCycVar   iab    le.name.substri   n   g(0, matcher.start()   +     1) + n   um++);
                      }    w  h  il      e (   existingVaria   bles.cont    ains(     newVar));
                    }     e     ls   e   {
                     Int   eger   num =            1;
                           do {
                              newVar = CycV a   riabl     eFactor  y   .get(modelCycV  ariable    .name     + "-" + num++);
                    } while (ex  istingVariables.   co  ntains(newVar))  ;
                }     
                 return n   ewVar;
                     }
          }

    /**
             * Reset    s t  he  <tt>CycVariable</tt> cache.
     *     /
    @Depreca ted
       public sta   tic     void    resetC   yc   V  a  riab           leCache() {
        CycVari   ableFac tory.reset(   );
        }

       /**
       * Adds the <tt>CycVariable<tt> to the cache.
     */
        @Deprec   ated 
    public static void a   ddCyc  VariableCache(CycVari  able cycVariable) {
          if     (c  ycVariable.n     ame    == n         ull) {
                     throw new Ru ntimeEx   ceptio            n("Inva   li  d vari  ab       le for  caching    " + cycVari    ab  l   e);
        }
           CycVari    ableFactory.add(cycVariable)   ;
               }

     /**
     * Retrieves the   <tt     >CycVari   able</tt> wi  th    <     tt>name</tt>, returning n  ull if
     * not found in     t        he c    a   che.
         *
     * @return a <t   t>CycVariable</tt> if fou  nd    in th   e  cache,             otherwise
     * <tt>   null</tt>
        */
    @Depr     ecated
    public st atic Cyc    Variable g  etCycVaria bleCache(   Str      ing     name) {
        return CycVariabl eF   actory.get(name);
     }

    /**        
     * Rem   oves the    < t t>CycVariable</tt> from the  c           ache if i      t is c     ontai    ned
     * wit              hin.
     */
        @     Deprecated
    public s       tatic     void re  moveCy  c   Vari    ableCache(CycVariable            cycVariabl          e  ) {
        CycVariableFactory.remove(cycVariable);
    }

    /  **
     * Retu             rns the size of t          h e     <tt>CycVariab   le</tt>      object     cac   he.
     *
       * @return an <tt>int</tt> indicating t  h   e number  of <tt>C  ycVari       able</tt   >
     * o      bjects in the cache
     */
    pu   blic static int   getCycVaria      bl         eCacheSize(   ) {
              return CycVa  riableFactory.si  ze();
       }

  /**
   * Con   structs   a new <tt>CycNum ber</tt> object using t   he v  ariable n     ame.
   *
    *     @param name a    <tt>St  ring</tt> nam  e.
   */
  public static            CycNumb   er   makeCycNumbe         r(Number n um) {
    CycNumber cycN umber = (CycNumber)  cycNumberCache.get(num)     ;
             if (cycN         um    ber == null) {
               cycN  umber =     new Cy cNu  mber(num   );
         cycNumberCache.put   (nu   m, c   ycNumber);
    }
    r   eturn cycNumber;
      }

  /**
   * Resets t         he <tt>CycN  umber</tt> cache.
   */
  publ    ic stat     ic void rese   tCycNumberCache() {
      cycNumber    Cache     = n  ew   LRUCache( NUMBER_CACHE_  SIZE, NU    MBER_    CAC   HE_SIZ         E, true)   ;
  }     

  /**
     * Adds the <tt> CycNumber<tt> to the cache.       
   */
  public stati c void add    CycNumberCach     e(CycNumber cycNumber) {
    if (cycNumber.get     N     umber() == null) {
            throw new Run  timeExcept    ion("I nv  alid number  for         caching   " + cycNumber );
           }
               cycNumber      Cache. put(cycNumber.getNumber().doubleValue(    ), c ycNumber)   ;
  }

  /   **
     *   Retrieves the <t  t>CycNum  ber<    /tt>     with <tt>num</tt>,
   * returning null if not fou nd in th   e cache.
   *
   *   @return a <tt>CycVariable</tt> if found in th  e cache, otherwise
     * <tt>null</tt>
   */
     public static CycNumber getCycNumbe  rCache(Double num) {
             re   tu rn (CycNumber) cy   cNumberCache.ge   t(num)   ;
  }

    /** 
   *   Removes    the <tt>CycVari    abl      e</tt> from th e cache if it  is contained within.
   */
            public static        void r  emo veC     yc  Numb  erCache(C ycNumber cycNumber) {
    Obj   e    ct element =   cycNumberCache.get(cyc Number.g      etNumber (     ).doub leValue());
    if (element != null ) {
      cycNumberCache.put(c    ycNumbe     r.getNumber().dou   bleValu     e     (), null);    
       }
  }
  
  /**
   * Returns the s  ize of the <tt>CycNu      m   ber</tt> object cach  e.
    *
   * @return an <tt>   int</tt> indicating        the number of <tt>CycVariable<    /tt> objects in the cache
   */
  public static in    t         getCycNumberCacheSize    ()    {
      retu  rn c  ycNumberCache   .size();
  }

       /**     
   * Retu   rns a cach  ed <t  t>Guid</tt> obje        ct or     construct a new
   * Gui d object   from    a guid string if the guid is not     fo    und in the cache.
   *
   *        @pa ram guid a <t      t>Str   ing</tt> f   orm of a G    UID.
   */
      public static Gu i     d makeGuid(String guidString) {
    G  uid guid    = (G uid) guidC ache.get(guidString);
    if   (gu    id == nul    l) {
        guid = new Guid(guidStrin g);
      addGuidCache     (guid);
    }
    return    g uid;
  }    

        pub  lic static Guid makeGuid(by   te[] da   ta)    {
    final Guid guid = new G    ui  d(data);
    final       String gu  idS   tri   n      g = gui    d.   getGuidString();
    return   makeGuid  (g    ui  dS    t   ring);
  }

  /*   *
            * Adds the <t   t>    Guid</tt> to the ca     che.    
   */
      pu bl   ic static void add     GuidCache(Guid gu    i    d) {
    guidCache.p    ut(gu id.getGui  dSt    ring(),     guid )       ;
  }

  /**
   * Rese        ts the <tt>Gui      d   </tt>   cache.
   */
    pub lic s  t  at     ic voi  d resetGuidCache() {
    guid    Cache = ne w  LRUCache(500,     500, true);
  }

  /**
    * Retrieves the        <tt>Guid</tt> with <tt>guidNam e</tt>,
       *   returning null         if not found in the c    ache.
   *
   *  @return the  <tt>Gu    i   d   <   /tt> if it is found in the cache, o  therwi     se
   * <tt>null</tt>  
   */
  public static Guid getGuidCache(String gui     dName)    {
           return (Guid) guidCache.  get(guidName);
  }

  /**              
   *     Rem  o     v es the      <tt>Guid</tt> from th    e ca    che if it is   con   tai   ned  within.
   */      
  publ    ic       s      t   atic void removeGuidCache(Guid guid) {
    Obj    ect ele        ment = gu   idCache.get(   guid.ge    tGui     dString()  );
         if (element != null) {
         guidCache.put(  gu   id.getGuidString(), null);
    }
  }

  /**
   * Retur   ns the    size of the <tt>Guid</tt> object cach        e.
   *
         *     @return    an <tt>int       </         t   t> indicati  ng the numbe  r of <t   t>Guid</tt> obj  ec  ts in the cache
   */
  public            s  tatic int getGuidCach    eSize() {
    return guidCache.size()  ;
  }

      /**
   * Unmarshalls a c   yc object from an X        ML represe   ntation.
     *   
   * @param xmlString the XML representatio n of the cyc object
   *    @r    eturn the cy   c ob      je   ct
   */
  public static Object unmars         hal              l(final String     xmlSt       r  ing) 
              throws IOException, Par          s   erConfigurationException,       SAXException {
    Document      BuilderFacto     ry dbf = Do   cume  n        tBuilderFa ctory.newInstance();
    dbf.s etNamespa  ceA ware(true);
         D   ocumentBuilder db = dbf.newDocumentBuilder(    );
    Do cument do  c = db.parse(     new   InputSo   urce(new ByteArrayInpu t    S     tr   eam(xmlString.getBytes())));
    return unm     a   rsh      allE     lement(doc.ge   tD  ocum    en   tEleme   nt(), d  o    c);
  }
          
  /**
   * Unmarshal   ls a cyc obje   c  t from the given      element i     n an XML Document object   .
   *
   * @p    aram element the element repre   sent  ing the cy       c object
   *  @param docume nt the XML document c   ontaining the ele   ment
    * @return t  he cyc object
   */
  pro  tected sta    tic     Object    unma  rshallElemen     t(   f              inal Element ele     ment,
          fin   al Document document) throws IOEx   c  eptio  n {     
    Strin              g elementName = element.      getTagName();
    if (    element        Na me.equals("guid")) {
                  return unmars    hallGuid(element);
    } e     lse if (e  lementName.equals("symbol"))      {
            return unmar   shallCycSymbol(el     ement);
    } else if  (elementName  .equal   s("variable")) {        
      return unmars   hallCycV   ariable(e lement);
      } el     se if (elementName.equals("c        onst     ant   ")   ) {
      return unmar   shallC     y       c   Con  sta  nt(e   lement, docum  ent);
       } else if (elementNa        me.equals("nat"))        {
            retur n unmarshallCycNar   t  (el  ement, document);  
    } else if (elementNam e.equals("list")) {
      return unmar    sha  ll CycLis    t(element, document);
    } else if (elementName.equals("st   ring    ")) {
        re     turn T   extUtil.undoEntity    Reference    (element.get       T ex       t Con tent());
      } else      if (elementName.equals("integer")) {
       return new Integer(element.getTe   xtConte   nt().trim());
    } else if (elementName.equ  als("double")) {
      retur    n new    Doub     le(element.       getTex   tContent().trim());
    } els  e if    (elementName.equal   s(  "byte-vector")     )   {
           return              un     marshallB  yteArr   ay(el   e  ment, document);
         } else if (elementName.equals("asse rtion"))    {
      return     unma       rshallCycAsse r tion(element);   
    } else {
      thr      o    w         new   IOException("Invalid     eleme    nt name " + e     lementName);
    }
      }
  
  /**
   * Unmarshalls a Guid from     the given element in an XML Document object.
   *
           * @param guidElement the guid xm   l element
   *   @return the guid or cached re   ference to   a   n existing guid    object
       */
  protecte      d static Guid unmarshallGuid(    Element  guidElement) {
       String guidString   = guidEleme  nt.getTextContent(    ).trim();
    Gu   id guid = g   etGuidCach  e        (gui   dString);
    if (guid !            = nu     ll) {
             ret  urn guid;
    }
      ret      u    rn makeGuid(guidString)   ;
      }

  /  **
      * Unmarshalls      a Cy          c   Symbol from the giv  en   ele   ment in an XML Doc     ument ob    ject.
   *
       * @param cycSymbol  El             em   ent the CycSymbo       l xml element
   * @return the Cy  cSymbol  or cached r      eferenc            e to an exist        ing C                 ycSymbol obj ect
   *       / 
  protected static CycSymbol un mar    shallCycSymbol(E      le     ment cycS          ymbol      E  lement) {  
    Stri   ng symbolN           ame = TextUtil.undoEntityReference(cyc   SymbolEle      ment.getText        Conte        nt().trim(  )    )   ;
        CycSymb            ol cy    cSym      bol = getCycSymbolCache(symbolName);   
        if   (cycSymbol !=          null)      {
        return cycSymbol;
    }
      return   makeCycSy  mb   o     l(symbolNam     e);
      }  

                 /**
   * Unmarsh all  s a CycAssertion from the given element i  n an XML Document   ob     ject.
   *   
   * @param c  ycAssertionEle   ment t    he Cy  cAssert    ion xml element
       * @return the Cy   cAsser    tion object
   */
  protected static C    ycAss  ertion    unma  rshallCy   cAssertion(Element cycAssert   ionElement) {
    //TODO   
           CycList hlFo rmula = new Cy cList();
    CycFort mt = null;
        return new CycAss             ertio   n(        hlFormula, mt)    ;
  }

  /**
   *  Unmar shalls a  CycVa  riable from    the given elemen   t in an XML Document object           .
   *
    * @p  aram cycVariableE       l   ement the       CycVariabl    e xml element
       * @      r    e  turn    the CycV        ariable      or c        ached refe   ren   ce      t   o    an existing CycVariab le objec   t   
      */
  pr         otected st       atic CycVariable unmarshallCycVariable(El    ement cycVa riab    leElement) {
    String name       = TextUtil.undoEnt       ityReference  (cycVariabl  eElement.getTextC         ontent()  .tri     m(  ));    
    CycVariable cycVariable = ge  tCycVariableCache(   name);
    if (cycV       ariable != null) {
         return cycVariable;
       }
        return makeCyc  Vari    able(n ame)      ;
        }
  
  p rivate static E lement         g etChild(Elem     ent parent, String tagName) {  
    NodeLi st nodes = paren  t.getChildNodes();
    for (int i = 0, size = node   s.getLength();    i < size; i++) {
           Nod     e node = nod      es. i    tem(i);
      if (no   d  e.ge     tNodeType(          ) == Node      .ELE MENT_NO  DE) {
        String possib     leTagName =   node.getL o    calName();  
           if (t   agName.equals(possi      b  leTa gName)) {
          return (El emen   t     ) no de;
           }
        }
    }
       re    tu        rn nul    l;
  }
  
      priva          t  e static Elemen   t get Firs   tChildEle    ment(Element parent) {     
    NodeList nodes = parent.getCh   il   dN                 ode  s(      );  
    for (  in   t          i = 0, size = nodes.g      etLe                   ngt    h()    ; i < siz    e; i++)   {
         Node nod     e = nodes.item(i);
       if (            node.getNodeType() == No de.ELEMEN   T_NOD        E  ) {
        return (E     le   ment   ) node;
          }
    }
            return null;
  }

  /**
   * Unmar         shalls a CycCon    stant fro   m the giv   en ele      men t in an XML Docum   ent object.
   *
   * @param cycC   onstant  Element the element repr   e      s ent  ing the CycConst ant
   * @p    aram document the X     ML document containing the   el  ement
   *         @param cycAccess the    Cyc communica    tions object
   *      @return the C  ycCons    tan        t
   */ 
  prote    cted stat ic CycConstant unmarshallCycC    onst  a      n  t(final Element cycCo nstant    Element,
              final Do    cument document) {
    CycConstant cycCons     tant =    null;
    Guid gu       id    = null;
    Nod      e gu idElement =    getChild(cycConstantElemen    t, "guid")   ;
    if (guid              E  le      m   ent            != null) {
      gu   id = makeGuid(guidElement.getTextContent().trim());
      cycConstant =     getCycConstantCacheByGuid(gui     d);
       if (cycConstant != null) {
           return cycCo nstant;
        }
    }
    String n  ame = null;
    No   de nam            eElement = getChild(cycConstantElement, "name");
          if (nameEl   e    ment != null) {
      name = TextUtil.undoEntity Re    ference(nameElement.getTextContent().tr  im());
        cycConstant = getCy cConstantCacheByName(name);
         if (cycConstant != null   ) {
        return cycConstant;
            }
    }
    cycC   onstant =        n       ew   CycConst an       t(nam   e,     guid);
    if (gui       d         != null || name != null) {
      addCy    cConstantCache(     cycConstant);     
    }
        return cycC onstant;
       }

  /**
           * Unmarshalls a CycNart from t   h   e    given element in    an XML Document obje      ct.
     *
   * @param cycNartElement     th  e element repre    senting the   CycNart
          * @param  document the X  ML  document containing the element
   * @retur  n the CycNart
   */
  protecte      d static CycNa rt unm     arshallCycNart(final Element  cycNartElement,
               final Document document) throws IO  Exception {
    CycFort functor    = null;
    No  de func    torElement = getChild(cycNartElement,     "functor");
    if    (functorElement != null)       {
      Element  cycConstan tFunc   t            orElement    = getChild(cycN ar   t   Elemen       t, "c  o   nstant");
      Element cy  cN   artFuncto        rE   lement = getChil  d(cycNartElement, "nat")  ;
         if (cycCo   nstantFunctorElement != null) {
        if (cycNartFunc torElement !=  null) {
            throw new IOEx  ception  ("Invalid CycNart functor"    +   functorElemen     t);
        }
           fu  nct    or = unmarshallCycCo   nsta            nt(c   ycConstantFunctorEle    ment, document);
      } else if (cycNartFunctorElement != null) {
              functor = un marshallCycNart(cy  cNartFun   ctorElement, document);
      } else {
           throw new IOException("Missi       ng functor     con    stant/nart from CycNart " +  cycNartElement);
      }
    }
    NodeList argElements = cycNartE   lement.getElementsByTagName("arg");
    CycList argu   ments = new     CycList(     );
    f   or (int i    = 0; i < argElements.ge   tLength(); i++)    {
         if (argE   lements      .item(i) instanceof Element) {
        Eleme  nt argEl      ement = (Elemen t) arg    Elements.item(i);
            arguments.ad  d(unmarshallElement   (getFi    rstChildElement(argElement), docume  nt));
         }
    }
    CycList nartCycList = new Cy     cList();
    nartCycList.a      dd(functor   );
           nartCycList.addAll(arguments);
    CycN    art cycNart = new Cy  cNa   rt(n ar    tCycList);
    return cycNart;
  }
 
  /**
         * Unmarshalls a CycL  is    t from the given el    ement     in an XML Document obje   ct.
   *
   * @param cy  cListElement the e              lement representing the CycList
   *     @param       document the XML document cont aining t    h   e elemen    t
   * @return     the CycList
   */   
  protected static CycList unmarshallCycL ist(final Element cycListE  lement,    
          final Document document)
          throws IOException {
    NodeList elements = cycListElement.getChildNodes();
    CycList cycList = new CycList();
    for (int i       = 0, size = elements.getLength(); i < size; i++) {
              Node node = elements.item(i);
      if (node.getNode  Type()    == No    de.ELEMENT_NODE) {
        El  ement element = (Element) node;
        if (el    ement.getTagName().equa   ls ("dotted-element"))   {
          cycList.setD ottedElement(unmarshallElement(  getFirstChildElement(element), document));
        } else       {
          cycList.ad  d(unmarshallElement(element, document));
            }
      }
    }
    return cycLis  t;
  }

  /**
   * Unmarshalls a ByteArray from the given element in an XML Document object.
   *
   * @param byteArrayElement the element representing the CycList
   * @param document the XML          document containing t    he element
   * @return t     h   e ByteArray
   */
  protected static ByteArray unmarshallByteArray(E lement byteArrayElem ent, Document document)
          throws IOException {
    NodeList elements = byteArrayElement.getChildNodes(     );
               ArrayList array  L    ist = new ArrayList();
    for (   int i = 0, size = elements.getLength(   ); i < size; i++) {
      Node node = elements.item(i);
      if (node.getNodeType()       == Node.ELEMENT_NODE) {
        Element element = (Element) node;
          if (element.getTagName().equals("byte")) {
          arrayList.add(new Byte(element.getTextContent().trim()));
        }
      }
    }
    byte[] bytes = new byte[arrayList.size()];
    for (int i = 0; i < arrayList.s     ize(); i++) {
      bytes[i] = ((Byte) arrayList.get(i)).byteValue();
    }
    return new ByteArray(b  ytes);
  }
  
    private s     tatic class CycVariableFa    ctory {


        /**
             * Least Rece ntly Used Cache of CycVariables, so that a reference to an
            * existing <tt>C   ycVariable</tt> is returned instead of con   structing a
         * duplicate.
         */
        protected static LRUCache cycVariableCache = new LRUCache(VARIABLE_CACHE_SIZE, VARIAB       LE_CACH     E_SIZE, true);

        public static CycVaria    ble get(String name) {
            if (name.startsWith("?")) {
                  name = name.substr ing(1);
            }
             CycVariable cycVariabl  e = (CycVariable) cycVariableCache.get(name);
            if (cycVariable == null) {
                   cycVariable = new CycVariable(name);
                cycVariableCache.put(name, cycVariable);
            }
            return cycVariable;
        }
        
        public static void add(CycVari   able var) {
            cycVariableCache.put(var.name, var);
        }

        public static v    oid remove(CycVariable var) {
            cycVariableCache.   remove(var.name);
        }

        public static int size() {
            return cycVariableCache.size();
        }        
        static void reset() {
                cycVariableCache = new LRUCache(VARIABLE_CACHE_SIZE, VARIABLE_CACHE_SIZE, true);
        }
    }
}

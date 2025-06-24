/*
 *     Copyr  ight (c) 2000,    20     15, Oracle      a    nd/or its  affiliates. All rights reserved.    
  * ORACL      E PROPRI ETARY/CONFIDE      NTIAL. Use is subje   ct to licens    e     terms.
 *
   *
 *   
 *
 *
 *
 *
 *    
 *
 *
 *
 *
 *
 * 
 *
 *
 *
 *
 *
 *
 */

package java.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
impor   t j    ava.io.FileInputStream;  
i  mport java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
  import java.security.AccessController;
import jav   a.security.PrivilegedAction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.re   gex.Pattern;
import java.util.regex.Matcher;
import java.util.spi.CurrencyNameProvider;
import    sun.util.locale.provider.LocaleServiceProvide    rPool;
import sun.util.log    g   ing.        PlatformLogger;


/**
 * Represents a currency. Currencies are identified by   their ISO 4217 cur   rency
 *    codes. Visit the <a href="http://www.is          o.org/iso/home/standards/currency_codes.htm">
 * ISO  web site</a>    fo    r   more informa  tion.
 * <p>
 * The class is desi    gned so that there's never more than one
 * <code>Cu rrency</code> instanc  e for   any given   currency. Therefo   re, there's
 * n  o        public construct     or. Y     o      u obtain a <code>Currenc      y</code> i   nsta    nce using
         * the <code>    getInstance</co  de> me    thod  s.
  * <p>
 * Users     can supersede the Java runtime curr  ency data by       means of the system
 * property {@cod     e java.util.cur   rency.data}. If this      system pr         o     pe    rty is
 * d    efined then its value   is the locatio        n of      a   propertie    s file, the contents of
 * which    are key /   value pairs of   the ISO 3166 country code   s  and the ISO          42   17
 * currency data   resp    ective       ly.    The value part consi    sts of three ISO 421      7   values
 * of   a  currency, i    .e., an alph abetic code, a numeric code,   and a minor unit.
 * Those three ISO 42        17 values are separated by commas.
 *    The lines which  start wit h '#'s are considered comment lines.    An optional UTC   
 * timesta      mp          may be specified per curre  ncy e      ntry if users need        to specify a
   * cut  ov   er date indica  ting     when the new da     ta com   es into effect. The timest   amp is
 *  appended   to the e     nd of the curren      cy properties and uses a comma as a   s epar  at   or.
 * If  a UT  C datestamp is present an   d valid, the JRE    will only use       the new curre      nc    y
 * properties if the current UTC da     te   is later than   the dat    e specified at     class
 * load   ing time. The format of the timestamp must be of ISO 8601 format      :
   * {@code 'yyyy -M M-dd'T'HH:mm:ss'}. For example,
 * <p>
   * <cod     e>
 * #Sample currency properties<br>
          * JP=JPZ,99   9,0
 *      </code>
 * <p>    
 * will          supersede   the c   u          rrency data for J         apan.
 *
 * <p>
 * <c od              e>
 * #Sample     curre   n  cy properties with cutover  date<br>
  * JP=JP          Z,999,0,2014-01-01T00:      00:00
 * </code>
 * <p>
    * w   ill       supersed     e the  curren   c  y data for Japan if {@code Cur    re        ncy}     class is lo        aded after
  * 1st January 2014     00:00:00     GMT.
 * <p>
 * Where    syntactically ma   l  formed ent   ries are encountered, the ent  ry is ignored  
 * and the remai   nder of entries in fil      e are p rocessed  .              F   or insta   nces    where duplicate
 * c  ountry code    entrie s e    xis  t, the behavior of    the Currency information   for t    hat
 * {@code Currency} is undefi   ned and    t h    e remai   nder of entri    es in         file are processed.
 *   
 * @sinc     e 1.4
 */
public fina    l class Cu            rrency impleme  nts    Serializa   ble {
                
    private  static final long serialVersion   UID = -                1583084          64356       906721L  ;

    /**
     * ISO 4217 cur        rency code      f      or this c  urrency  .
     *
     * @s      erial
      */
       private final  String cur       rencyCode;

       /**
         *    Def       ault fraction digits for this currency.
     * S   et from currency dat  a           tables.
       */      
       tran           sient private final         int defaultFrac  tion Digit s;

    /**
     * IS   O      4217 numeric    code for   thi            s   curr ency.
          * Set from     currency data tabl      e    s     .
     */
        transi ent   pri vate   fin     al int nume       ricCo  de;


    // class data:   insta  nce m  ap

         pri  vat    e        sta tic Concu   rrentMap<Str ing, Currency> instances = new ConcurrentHas       h Map <>(7    );
    private static HashSet<  Currency>    avai   lable;

    // Cl   ass           data:   currency d      ata obtained     from currency.data       f  ile.
     //    Purpose:
    /  /      - determine valid country codes           
      // - de       termin     e valid currency codes
    //        - map      cou     ntry codes to curre    ncy codes
    // - obtai  n default fraction digi    ts for currency    codes   
    //
       // s            c = special case ; dfd = default     fr  actio    n di       gits
    //   Simple coun    t ries    are     those where the country code i    s a p    ref    ix o  f the    
             // curre    ncy c ode,                 a             nd there a          r    e no k     nown plans to change    the cu   rrency.
    /    /
         // table      formats:    
                 // -       mainTable:
     //   - map     s    co        untry code  t  o   32-   bit  int
                 //   -     26* 26 entries, corre   sponding to [A-Z   ]*[A-Z]
       //      - \u007F -> not valid c  oun   tr    y
    /   /       - b its 20-31: unused
    //   - bits 1     0-19:   n    umeric    code      (0    to 1023)
    //   - bit 9    :     1 - speci al case, b      its 0-4 indicate    whic   h one
    //                0 - sim ple cou  ntry,           bits    0-4 in  dicat    e          final ch   ar of currency code
    //   - bits 5-8:       fracti   on      digits for s   imple countries, 0 for special c     ase s
         /    /   -    bits 0        -4: fin al      ch      ar for c  urrency code for si m  ple co    untry, or ID of special case
    // - spe cial case   IDs:
         //    -    0: country has no curr  ency
      //   - ot  her: index    into sc* arrays + 1
    /     / - scCu            tOverTimes: cut-o        ver time i n mil  lis a     s returned by
    //   Syst   em.current   TimeMi   llis for s          peci   al case           countries     that ar e changing
    //   currencies; Long  .MAX_VA LUE for countries that  are not changing cu   rrenci      e s     
    //     - scOldCurren  c ies: old currencies for special case countries
    // - scNewC urrencie s: new curr  encies    for spec    ial ca      se countries th at are
    //   cha      nging currencies; null for other     s
    // -   scOldCurrenciesDFD: de  fault      fraction      digits for ol d cur        rencies
    // - scNewCu    rre       nciesDFD: defau  lt fraction          digits     for new currencies, 0 for
    //   coun     tries that are no  t changing currencies
    // - otherCurrencies     : concatenation of  all curren     cy codes that      are not t   he
    //     main currency of a simple country, s    ep  arated by    "   -"
    // - otherCur       renciesDFD: d  ecimal f  o  rmat di  gits      for currencies in otherCurr  encies, same     ord      e   r

       s  tatic i   nt for    matVersion ;
    st         atic int dataVersion;
    stati  c int[] m   ainTable;
    static long[] scCutOver  T   imes;
    static Str              in   g[] scOldCurre    ncies;
         stat       ic String[]   scNewCurrencies;
    stat ic int[] scOldCurrenciesDFD;
      st   atic int[] scNewCurrenciesDFD;
     stati c int[] scOldCurrenci esNum  ericCo    de;
              static i    nt[] sc  NewCurrenciesNumericCode;
    stati   c     String otherC       ur      rencies;
    static in  t[]     oth  erCurrencie  s   DFD;
      static int      [] otherCurrenciesNumericCode;

    // handy         constan     ts -    mu          st match definit               ions i       n G   enerateCurrenc  yD  ata
    //   magic number
    pri       va    te static final int MAGIC_  NUMBER = 0x43  7572   44        ;
    // n   umber    of characters fr om A to Z           
          private static fin   al    i   nt A_TO_Z = (     'Z' - 'A') +      1;
         // entry for invalid country code s
    p                rivate static final  int INVALID_COUNTRY_   ENTRY = 0x000000    7F;
    // entry for countries without cu        r       rency
       private static final int COUNTRY_WITHOU      T _CURRENCY_ENT    RY     =       0x000     00200;
    //      mask   for   sim        ple case co  untry entries       
    private static final int SIMPLE_CASE     _COUNT RY_MASK = 0x0000000         0;
    // mask   for sim              ple case country en   try f  in     al         ch  aracte  r
     priva  te     static fina l int                 SIMPLE_CASE_COUN   TRY_FINAL_CHA   R_MASK    = 0x   0000001F;
           // mask    f          or   simple ca  se       cou     ntr  y                entry de fa   ult curren         cy di gits
    private stat   ic   final int SIMPLE_CA      SE_C  OU    NTRY_DEFAULT       _          DIGITS_MASK = 0x00    00   01E0;
    // s     hift coun    t f                  o    r si     mpl     e        c        ase country entry   default cu  rre  nc            y dig its    
    pr  i   vate st   atic final int S        IMPL     E_CASE_COUNTRY_DEFAULT_D  IGITS    _SHIFT   = 5;
      // maximum number    for sim    ple c  ase country        en   try    defaul      t cu  r rency digi            ts
    pri   vate stat  ic fin al    int SIM  PLE_CASE_ COUNT    R     Y_MA      X_DEFAULT_D      IGITS = 9;
    /    / mas   k for              spe cial case  co      untr         y    en tries
    priva        te      s   tatic fi    nal int       S   P EC  IAL    _CAS    E_COUNTRY _MAS   K = 0x0  0000200;
     // ma     sk    for special case    cou nt    ry i     nde  x
       pr      ivate sta    t       ic final i nt SPEC  IAL_CAS      E_COUNTRY_INDEX  _     MASK = 0x0    00000 1        F;
          // delta from en try i     n     dex   compon        ent in mai   n             table      to index in  to special cas         e tab       l  e   s
                pri   v  ate sta        tic fina   l i            nt S   PECI   AL_CASE_COUNTRY_IND E      X_DEL      TA          =               1;    
     //          m  ask fo   r       dis      t   in        guishing si  mple a      nd   spe     ci        al   case count  ries
         pri  vate                static              final int COUNTRY_T   Y   PE_M      A    SK =   SIMPLE_CASE_     COUNTRY _MAS K     | SPECIA    L_C   AS   E_COUNT        RY_MASK;
    // mask for the  numeric cod e   of t  he currency
               private st                           atic final i    nt NUME                RIC_CODE_MASK     = 0x000         F   FC00;
    //     shift c   ou nt         for t    he  num   er  ic code of    t          he   c     urrency
    pr       i     vate static final    int N U               MERIC_CODE_   SHIFT     = 10;

    //         Currency data       fo    r  mat  vers     ion
    privat             e             s                     tat   ic final int VA    L      ID_F     O   RMAT_VERSI    ON = 2;

    stat            i      c       {   
                          AccessC    ontroller.do          Privileged(n ew Pri       vi    le   ge  dA  c     ti    o        n    <V  oid  >( ) {
            @Override
                                      public                Void run  ()             {
                        String hom      e    Di     r         = Syst em   .get Pro            pert   y(    "java  .  home");
                             try   {
                                                     Strin                   g da    taFile = home     Dir + Fil  e.     se pa      r  at  or    +
                                     "lib" + F         i     le.s       epar a    tor          + "cur   ren  cy    .da  t     a" ;
                                               try (DataInp  u          tStre     a  m d      is    =    new  DataIn    putStream(
                                                  ne    w Bu  ffere dInp  utStre    am  (
                                                                                    ne   w   Fi    leI  nputStream(dataFi            l  e)   )))   {
                          if (d     is.readInt() != MAGIC_NUMB      E  R)     {
                                                                        throw n ew     InternalError                    ("Cu   rrency da  ta is pos  sibly corrupted");
                                                     }
                                      formatVersion = dis.re ad     In         t();
                                               i         f (fo          rmatVersion !       = VALID_FORMAT_  VERSI  ON)   {
                                                                   t   hrow ne   w   I  nternalErr or("Cu    rrency   data format is i nc  orrect");
                                 }
                                 dataVer     sio n = dis.r   ead   Int  ();
                                         mainTab   le    = readIntArr      ay(dis, A_TO  _Z * A   _ TO_     Z);
                                            int scCo          unt    =     dis      .read  I           nt()          ;
                        scCutO  ve       r      Ti       mes =     rea   dLo    ngArray(   dis,     s          cCount);
                                          scOldCurrenc  ie     s = rea        dStri   ngArray(dis, scCount);      
                                        scNewC  urre ncies  =      r eadStri  ngAr  ray   (d           is, sc     Cou   nt   );
                               scOldCurre  nciesDFD = readIntAr     ray(dis, scCou     nt);
                                                 s cNewCurrenciesDFD = readIntAr             r      a    y    (d         is, scCount);
                                 scOldCurr    e   nci    esNu   mer  icCod  e  = readInt    A        r       ray(dis , sc   Cou       nt);
                               s  cNewCu  r renciesNumeri          c    Code = readIn     tArray(dis, scCou          nt);
                                                  int ocCount        = dis.rea  dI nt();
                               ot   he    rC     urrencies = di       s.readUTF       ();
                               otherCurrenci       es   DFD = r eadI    ntArray(dis,      ocCou nt);
                                                              ot herCur   renciesNumer  icCode =       rea dInt Array(dis, ocCo   unt);
                         }
                              } catch   (   IOE    xception e) {
                              throw new Interna     lErr   or   (e);
                 }

                  // look f         or the  properties fil  e for    overri des
                 Stri  ng props File = System.getProperty(      "java.util.curre         ncy.data")    ;
                                   if (pr    o psFile ==       nul    l) {
                      props     File = hom          eDir + Fil          e.separ        a          tor + "lib" +
                                       File.sepa  rat  or      + "    curre      n        cy.prope rt   i  es";
                    }
                       tr      y {
                         File propFil e = new  F ile(pr     op    sFile);
                      i f      (propFile.exis  ts(     ))   {
                                       P      roper ties prop          s = new Pro per      ties();     
                            try      (File   Reader fr   = n      e   w Fi   leReader(propFile)) {    
                                                              props.loa    d(fr);
                                                       }
                                   Set<S    trin    g> keys     = props.stri   ngPr o    pert     yNames()  ;
                                        P  attern         pr  ope     r  tiesPa   ttern =
                                                              Pattern.compile("([  A-Z]{3}    )\\s*,\\s  *(\  \d{     3})    \\s*,\  \s*" +
                                                      "  (\  \d+)\\ s*,?\\s*( \\d{4      }- \  \d          {2}      -\\  d{     2}T\\d{2}:"    +
                                                  "\\d        {2}:\\ d {2})?");
                          for (St     ring       k       ey : keys) {
                                                    r    epl  aceCurrencyD    ata(pr  opertiesPattern,
                                                      key.t    oUppe  r  Case(Locale.ROOT),
                                            props.getProperty(key).toUpperCase(Loca       le.ROOT ));
                                                           }
                     }
                   }     catch (   IOExce        ption e)   {
                            info("currency.properties is ign        or  ed be  cau      se of        an   IOExcept         ion"        , e);
                             }
                  return n ull;
              }     
                   });
                  }   

    /**
     * Co      n   stants  fo  r r    etrieving localized      n        a  mes from the name prov   i d    ers.    
          */
    private static f                inal int SYMBOL = 0;
    private static  fi  nal int DISPLAYNAME = 1;


    /**
     * Cons  tructs a         <c    ode>Currency</code >     instanc   e. The      constru    ctor      i    s      priva  te
      * so that we   c    an insu   re that ther      e's n   ever mor   e tha  n one instanc            e for     a
     * given cur  rency.
     *        /
    p ri    vate Currency(S  tring currencyCode, int        defaultFra ctionDigits,   int numericCode) { 
        this.curre       ncyCode  = currency      Code;
                  this.defaultFraction     Digits     = defaultFractio       nDigits   ;
            thi   s.numeric  Cod e     =    numeri cCode;
         }

     /** 
           * Returns the <co     de>Currency<     /code    >   inst  ance for                   the given currency code.
     *
                               *      @   param curr   e        ncyCode the ISO 4217 code of the currency
                        * @return th e   <code>Cur    rency</code> instance for the give   n currency       cod  e
     * @exception     Null  Pointer    Except     ion if <code>currencyCode  </code> is n ull
     * @exception Ill       egalArgumentException if <code>currencyCode</code> is no  t
     * a suppor     ted I   SO 4217  co   de.
     */
    public static Curren      cy getInst  anc e(St    ring cu  rre   ncyC  o           de) { 
                 return getInst  ance(currenc    yC  ode, Integ er.MIN_V    ALUE, 0);
      }    

      private static C urrency      getInstanc    e(S  t  ring cu         rr  encyCode,  int de       faultFrac    tionDigits,
             int n  umericCode) {
                   //    Try to look up the cu    rrency      cod             e in t      he instanc es table     .
                            // This        does the nul   l pointer che    ck as a     si         de effect.
             // Al     so     , if there alread y i       s  an entry, t       he cur  rency  Code must be    v  ali  d  .   
                    Currency ins    tan   c   e = instances.get(currencyCode );
           i       f (in    s         tance != null) {
                        retur   n instance;
              }

            if   (defaultFractionDigits           == Intege       r.M  IN_   VALUE) {     
                  /       /   Cur rency co    de no t int  e   rnally generated             , need to verify first
            /     / A    currenc    y code m ust     hav       e 3 characte      rs and exist      in   the  main table
                           // or  i     n t he li   s  t of   other c ur renci  es.
                              i   f  (c      urren    cyCode.     lengt  h() != 3  )    {
                      throw  n ew Il    legalArg    umentE       xc           e   ptio      n   ();
               }
                                      c   har c   har       1 = currencyCode.charAt(0);
                   char ch       ar   2 = currenc   y   Cod  e.charAt(1)     ;   
             int tableEntry   = getMainT    ab   l eEntry(char1, char2);
                if ((tableEn       try & CO  UNTRY_ TYPE_MASK   )  =  = SI        MPLE_CASE_COUNTR Y_MASK
                                                 &&    ta  ble  Entry != I     NVALID_COUNTRY_EN     TRY
                       && currencyCode     .charAt       (2  ) -   'A' == (tableEntry & SIMPLE_CASE_C          OUNTRY_F   INAL_CHAR_      MASK)) {
                              defaultF                ractionDigit    s = (tabl      eEntry & SI M PLE_CASE_COU                    NTRY_DEFAULT_DI GITS_MASK) >>         SIMPLE      _CASE_C     O     UNTRY_DEFAULT_DIGITS_SHIF T;
                           numeri   cCode =   (tabl    eEntry &    NUMERIC_CODE_MAS  K) >> N   UMERIC_CO               D               E_S  HIFT  ;
             } else {
                // Check for     '-'       separ  ately so w       e don  't get false    hits in              the    table.
                 if (curr    encyCode.char        At(2) ==   '-') {
                                 thro                 w    new Illegal         A  rgumentExce      pt         ion()        ;
                            }
                                     i nt in dex  = otherCurrencies   .inde  xOf(c         urren      cyCode);
                          if (  index ==    -1)      {
                                        throw new I    lleg   alAr     gume  ntException();
                        }
                           defaultFractionDigit s = otherC       ur   renciesDFD[index / 4];
                           nu mer   icCod     e     = other   Cur r   e  nciesNum      e ricCode   [index    / 4];
             }
              }

                              Currency   c u       rrency V   al                 =
                  new C   urrency(curren           cyC     ode,   def                         aul   tFractionDigi      ts, nu     mericCode);
        instance                =     i  nstances.putI   fAbsent  (cu rrencyCo  de,      currency  Val);
        retur  n   (instanc        e    !=    null     ? instance : currenc  yVal);
    }

    /**
      * Return s the <       co   de>Cur   rency</code>  instance     for the   cou  n         try      of the
     * given locale. The language a      nd varia       n  t             co      mponents of the loc  ale
       * are ignored. The res     u      lt      m     ay vary ov       e  r time          , as countries cha nge      their
             * currencie      s.     F or examp   le, for      t  he original  member countr ies of the
         * European Monetary  Union,    the method returns t   he   old nati on  al currenci    es
         * until Dec   em   ber                            31,  2001,     and the Euro      fr  om Ja  nuar     y 1, 2002, local          time          
     * of t he       respective coun t      ri        es    .
     * <p>
     * The me                  thod returns           <     code>    null</  code> fo      r     te    r  ritories t  hat do        n't
       * have        a currency       , such a s        Antarctic   a.
                 *
     * @para   m locale      the lo    c    ale  for w          hose country a <cod   e>Currency </  co               de>
     * in    st   ance is needed
        * @retur   n the <cod  e>Cur          rency</co     de> instance       for the cou  ntry    of the            given
        * local     e, o   r {@code n ull}
      *   @exception                      NullPoint    er     Exc    eption     if <c ode>locale  </code> or     its     country
         * code is {@co   d       e    n  ull}
               * @ex      ception        IllegalAr        gumentException                if the  country of  the    given {@c        ode locale}
         * is        not a supported  ISO                    3166     count     ry          code.
           *    /
    public static Curr    ency g      etInstance(Local       e locale)     {  
        String countr         y =     lo   ca  le.getCountry        ()   ;     
            if (country ==  null) {
                   throw new         Nu    llPointerException()   ;
            }

             i         f       (country.length()     !    =              2)    {
            thro      w new Ill egalArgu     m     e    ntExceptio n();
                 }

          char char1       =   coun try.charAt(     0);
            c ha    r cha r2 = country.c  harA t(  1);
               int ta   b leEntry = getMainTableEntry(char1, char2);
         if ((      table Entry & C    OUNTRY_TYPE_MASK) == SIMP    LE_C ASE_COUNTRY_MASK
                                       && tableEntry != INV   ALID_        COUNTRY_ENTRY)  {
                  cha   r fi    nalCh ar = (char) ((tableEn            try & SIMP    LE_CASE_CO      UNTRY_FINAL_CHAR_MASK)    + 'A');
                 int def    aultFractionDi     gits = (tabl        eEntry          & S          IMPLE_CASE_COUNTRY_DEFAUL  T  _DIGI       TS_M      A   SK) >       > S               IMPLE_CASE_COUNTRY_DEFAULT_DIGI     TS_ SHIFT;
                            int numericCode = (tableEntry  &   NUMERIC   _COD     E_MAS   K)     >>  NUMERIC_CO   DE  _SHIF   T;
                   S   tringBuilder sb = new     Stri  ngBuild    e r(countr              y              );
                     sb.append(finalChar); 
                   ret urn    getInstan        c      e(sb.toStri   ng()        , defaul       tFrac   tionDigit    s,  numericC ode);
         } else {  
                           // sp   ecial     cases
             if (tableEnt  ry ==     INVALI   D_COUNTRY_ ENTRY) { 
                                               thro    w new Il l             egalArgume     ntExceptio  n();
                   }
                                    if   (tableEntry == COUNTRY_WITH              OUT_CURRENC Y_ENTRY)     {
                      r  etur   n null;
                         } else {
                       int  index = (tabl   eEntry & SPECIA    L_CASE_COUN TRY_INDEX_MASK) -    SPECIAL  _CASE_ C    OUNTRY_  IN   DEX_   DEL TA;
                     i f (sc  Cut   OverTi   mes[index] == Long   .MAX_V     ALU      E || S       yst    em.curr    e     nt   TimeMillis(   )  < scCut    O  ve     rTimes[ index]) {
                                 return getInstance( scOldCurrencie        s[ind     ex], scOldCurrenci   esDFD           [index],
                                                  scOl          dC urrencie     s  NumericCode[inde      x]);
                            }     e       lse {  
                           retur n ge    tInstance(scNewCurrencies [i ndex], scNewCu    rrencie sDF      D   [index      ],
                             scN ewCurrenciesNum  er    icCode[i              n       dex]);
                       }
                    }
               }
    }

               /**
                * Gets the s    et of       available cur        rencies.  Th       e   ret       urn     ed set of cur  renci  es
     * contai  ns   all     of the        a    vailable         currenci  es, which may in clud   e         currencies
     *   tha  t repres ent ob        solete ISO 4217 codes.  The set  ca   n be modified
           *          without affecting t      he      available        cu     rrenc     ie  s     in     the runtime.
     *
        * @re   t  urn the set of a           vai    labl   e c  urrenci    es.            If th     ere is no    curr en c   y
     *    av             ailable in the    runti    me,   the return ed   set    is emp    ty.
       *  @since 1.7
     */
          pu   bli        c static Set< Curre  n       cy> getAv     ailab  l    eCurre ncies () {  
           s     ynchro n  i   zed(Currency.        class) {            
               i  f (av         ailable = = null)    {
                                 available = new      HashSet<  >(256);

                     //    A          d  d    s     im  ple    currencies first
                        for           (c ha   r      c1 = '   A'; c1 <= 'Z';             c       1 ++) {   
                                   for (char c2     = 'A'           ; c2 <= 'Z'; c2 ++) {     
                                       int tableEntry =            get   Main  TableEntry(c1, c2);
                                           if       (    (t   ab      leEn   try & COUNTRY_TYPE_MASK) == SIMPLE_C   ASE_COUNTRY_MASK
                                     && tableEntry !=       INV   ALID _COU NTRY_ENTRY) {
                                         char    fi   nal     Char = (char)       ((tableEntry & S  IMPLE_C   ASE_  COUNTRY_FINAL   _CHA R_MASK) +   'A');
                               i      nt  d     efaultFract  ionDigits = (table  Entr  y & SIMPLE_C    ASE_COUNT  RY_  DEFA ULT_DIGI TS_MA SK) >> SIMPLE_CASE   _COUNTRY_DEFA     UL    T_DIGITS _SHIFT;
                                               int n        um  ericCode = (ta   bleE      n    try & N   UMERI  C         _CODE_M ASK) >> NUME       RIC_C                ODE_ SHIFT;
                                                       StringBui     lde r sb      =    new S      tringBu            ilder();       
                                              sb   .app  end(c1);
                                                 sb.app       end             (c  2);
                                    sb.append(finalChar)     ;
                                    avail     ab       le  .add(getInst  ance(sb.      toString     (), defaultFracti            onDig             i ts,       numericCode));
                            }
                             }
                               }

                // Now     add othe  r cu  rrenci        es
                                StringTokenizer           st       = new S    tringTok eni zer(otherCurrencie           s,   "       -");
                          while (st.hasM        o   reElem  ents()) { 
                           ava  i  lable.add   (getInstance    ((Str ing)st.next  Element ()));  
                      }
                     }
                  }      

        @Su         p  pre      ssWarnings("unchec ked")
         Set<Currency        > r  es   ult    = (Se   t<Currency>) availa   ble.clone();
                       retur       n            result  ;
    }    

     /**
     * Gets            the ISO 42   17 cu    rre   ncy   code of thi        s  c ur ren   cy.
     *    
           * @r     eturn the ISO 4      217 cu   rrenc y cod  e of this curr   e      ncy.   
     *     /
     publi c String   getCurrencyCo de(   ) {
                    re  turn currenc    yCo    de;
       }                                

    /**
             * G    ets t    he symbol          of this curren     cy          for      the default
           *   {@link Locale        . Category#D       ISPL  A     Y DISPLAY}        loc  ale.
        * For    example, for the US                          Dollar, the s  ym      bol     is   "$" i  f the default    
       * l       ocale is the   US, wh  il  e for o      ther  lo c     ales it may b     e "US$". If   no
           * symbol  ca      n be      de  t      ermined, the ISO  4     217 curr       enc   y code        is ret u   rned .
         * <p>
            * T   his is eq  uivalent to calling
        * {@        link #   getS   ymbol     (Locale)
      *     get Sym   bo     l(L ocale.getDefault(L     ocale.Ca tegory     .DISP     LAY))}.
       *
           * @r   eturn the sym     bo       l of this currenc y for            the default
       *     {@li    nk Locale.Categ       ory#DIS             P LAY DISPLAY} l    o  cale
     */                
    public S       tr      in  g getS ymbol    () {
        return getSy      mbo   l    (Locale.getDefault(Loca le.Cate          gory.D    ISPLAY));
    }

        /**
         * Gets the   symbol of      this cu rrency    for t      he speci  fied locale.
     *    F  or examp    le,  for the    US Dollar, t       h        e     sym   bol i   s "$"       if the specified
     * loca   le i   s the US, while fo      r oth  er locales it may be "US$". If no
     * symbol   can be determined,       the ISO 4217 currency code i   s return    ed.      
     *    
     *    @param       loc  al    e the locale for   wh                   ic  h    a display name         for this     currency is
      * needed
              * @return  the      symbo    l of this currency      f  or the       specif ied                   l    o    c  al  e
     * @excep    ti   on NullPoi  nte       r     Exception if <        co  de>loc     ale</code> is null
                      */
    public   Str        in  g g    etSymbo l(Lo    cale locale) {
                              Loc  aleSe     r     viceProvi  derP   ool    p                  ool =
                         LocaleSe  rvice   Prov        iderPo  ol.g  etPool(Curr   encyNameP rovide r.class);
              String   symbo    l  = pool.getLocali   zedObject(
                                                                  Curr   encyNam        eGetter.INSTANCE,
                                           local   e      , cu     rr e  ncyCode, SYMBOL);
        if       (s                ym       bol != null) {  
                    return s      ym  bol;
        }

        // u       se curr   ency code as s    ym     b  ol o    f la     st resort
                      return cur    rencyCode;  
          }
 
    /**
        * G  ets the def       au         l   t num  ber of fract   ion digits         used with this cur  r  ency.      
             * For    example,  t     he defaul   t nu  mber of fr     action d  ig  i     ts for t  he Euro is 2,   
                 * wh   ile for the Japanese Yen it's 0.
         * In the c  ase of     ps   eudo -currencies, such as IMF Special Draw   ing Rights,
     * -1 is returned.
     *
     * @    re   turn      the defau          lt number of fr    action digits        used      wit h this cu  rrency
           */
       pub    lic     int getD    efaultFractionD   i        gi ts() {      
         retu rn     def      a             ultF     ra          ctionDi    git s;
     }

     /   **
           * Returns              the IS   O 42        17 numeric code of this c urrency.
         *
           * @ret   urn   the ISO 4217 nu  meri c co    de of this currenc  y
     * @ sinc  e 1.7
     */
    p  ubl   ic        int getNu  mericCode     () {
         return nu             mericCo    de ;
    }      

      /** 
       * Get            s  the na    me    that is suitable    for displa      yin     g    this   curr ency     for
                         *                the  d   ef     ault {    @lin     k Lo    cale.Cate  go         ry#DISPLAY DIS    P  LA   Y} lo            cale.
     * If there is    no s   uita  ble dis play name found
     * for t   he     de  fault local    e, the ISO 4   2     17 cur  rency    code is retur  ned.       
       * <p>
           * This       is equiva    lent            to callin  g
     * {   @link #         getDisplay   Name(Locale)
     *          getDisplayName(        Locale.getDefault(Locale.Category.DISPLAY))}.
       *
         *    @retur       n th          e display name of this currency      f     o      r the default
                *       {@link   Locale   .Ca            tego    ry#DISPLAY DI SPL    AY}     loca   le
     * @since 1.7
       */
     public St ring getDispla   y  Name() {
        return getDis   playNam e     (Loc ale   .getDefault(   Loca   le.Cate gor y.DISPLAY))     ;
      }

      /**
          * Gets the name that is suitable for display    ing this currency for
     *  the spe  cified locale.  I      f there is    no s   uitabl e displ   ay name found
     * for the specified lo ca le, the ISO 4217 c  urrency code is returned  .
     *
       * @   param l     ocale  the          locale for which a display name for this currency is
     * needed
             * @return     the display name         of   thi    s currency     f  or the specified loca  le
         * @exce      pti  on Nul lPoint  e   rExce   p  tion if   <code>lo     c   ale</code> is n   ull  
     * @sin c               e       1.7
     */
    public  String getDisplayName(Locale local   e) {
          Lo   caleServicePr oviderPool     pool =
            LocaleSe      rvicePro viderPool.getPool(Curr      encyNamePr    ovider           .class);
                   String result = po       ol.getLoc     alizedObject(
                                                CurrencyNameGetter.    IN   STANCE,
                                    locale, currencyCode,           DISP          LAYNAM E);
         if     (result != null) {
              return result;
        }

        // use   curr    e  ncy code as symbol of last resort
          return currencyCode;
    }

     /**
     * Returns the ISO 4217 cur  rency co              de of this curr  ency.
     *
     * @return the I        SO       4217 curre    ncy cod    e of this currency
          */
    @Override
      public String toStr      ing() {
            retu rn currency   Code;
           }

             /**
         * Resolves insta      nce s    being deserialized to a single i     nstan ce p  e   r currenc  y.
     */
    private Object       re   adResolve(   ) {
        return getIns        tance(currencyCode     );
        }  

      /**
      * Gets the ma         in  table e          ntry fo  r th     e country whose  country       code consists
     * of         char1 an     d        char2.
     */     
       private static int getMainTabl   eEntry(char char1, char char2) {
           if      (char1 <    'A' || ch    ar1 > 'Z' || char2 < 'A' || char2 > 'Z') {
                      th   ro  w new Ille  galArgumentExcep  tion();
         }
        ret  urn mainTable[(c   har1 - 'A')   *   A_TO_Z + (char2 - 'A')];
        }

    /**
     * Sets the main table entry for the coun      try    whose country cod   e consists   
     * of char1 and ch     a    r2.
     *    /
    pri  vate static void s  etMainTab    leEntry(char ch              ar1,    char char2, int entry) {
        if    (char1 < 'A' || char1 > 'Z' || char    2 <  'A' || char2 > 'Z') {   
                     thro     w new IllegalAr    gume ntException     ();
        }
        mainTable[(char1 - 'A     ') * A_TO_Z + (char2    - 'A')] = e  ntry;
         }

    /**
     * Obt   ains   a localized curre    ncy names from   a CurrencyNa   m  eProvider
     * implementation.
     */
    priv     ate static class  CurrencyNameGetter
           imple ments LocaleSe  rviceProviderPo   ol.Localized  ObjectGetter<Currenc   yName  Pr     ovider, 
                                                                                 String>   {
        private static final CurrencyNameGe  tt         er INSTANCE = new Currenc  yNameGe   tte     r();

              @Override
                   pub      lic String getObject(Cur   rencyNameProvider currenc      yName    Provider,
                                          Locale loca        le,
                                    String k     ey,
                                         Ob    j ect... params) {
            a    ssert       params.length == 1;
            int type = (   Integer)params[0 ];

              s     wit  ch(type) {
            case   SYMBOL:
                       return currencyNameProvider.getSymbol(key, locale);
            case DISPLAY  NAME:
                    retu rn currencyNameP   rovider.getD     isplayName(key, locale);
            default:
                                   assert false; // shouldn't happen
                    }

             return null;
        }
    }

    private static int[] readIntArray(   DataInputStream dis, int c ount) th rows IOException {    
                int[] ret    = new int[count    ];
        for (int i = 0; i < count; i++)     {
            re      t[i] = dis.readInt();
                  }

        return ret;
    }

    private static long[   ] readLongArray(DataInputS  tream dis,   int   count)     throws IOException      {
        long[] ret =  new   long[count];
        for (i    nt i = 0; i < c  ount; i++) {
            ret[i] = dis.readL   ong() ;
           }

           return ret;
    }

        private static    String[] readStringArray(DataInputStream dis, int count)      throws IOException {
         String[] ret = new        String[count];
        for (int   i = 0; i < count;  i++) {
            ret[i] = dis.readUTF()    ;
         }

        return    ret;
     }

       /**
     * Replaces currency   data fo    und in the cur   rencydata.propert     ies   file
     *
     * @param pattern regex patt  ern for the  properties
     * @param ctry country co    de
     * @pa      ram curdata currency data.  This is a comm     a separated string that
     *       consists            of "three-l    etter alphabet code", "three-digit numeric code",
     *    and "on    e-digit (0-9) defau        lt    fraction digit".
     *    For example, "JPZ,  392,0".
     *    An optional UTC date can be a   ppended to t     he string (comma separated)
     *    to allow a c      urrency change take effect after date specified.
     *    For example, "JP=JPZ,999,0,2014-01-01T00:00:00" has no   effect unless
          *    UTC time is       past 1st January 2014 00:00:00 GMT.
     */
    private static void replaceCurrencyData(Pattern pattern, String ctry, Strin   g curdata) {

              if (ctry.length()    != 2) {
            // ignore invalid country c    ode
            info("currency.properties entry for " + ctry +
                    " is ignored becaus e of the invalid country c  ode.", null);
            return;
        }

        Matcher m = pattern.matcher(curdata)  ;
           if   (!m.find() || (m.group(4) == null && c     ountOccurrence s(curdata, ',') >= 3)) {
             / / format is not recognized  .  ignore the data
            // if group(4) date string is null    and we've 4 values, bad date value
             info("currency.properties entry         for " + ctry +
                    " ignored because the value format is    not recognized.", null);
                     retu        rn;
        }

           try {
            if (m.group(4) != null && !isPastCutoverDate(m.group(4))) {
                info("currency.properties entry for " + ctry +
                        " ignored since cutover date has not p  assed :" + cur dat   a,     null);
                      return;
            }
           } ca  tch (ParseException ex) {
            info("currency.properties entry for " + ctry +
                            " ignored since except    ion encounte   red :" + ex.getMessage(), null) ;
            return;
              }

        String code = m.group(1);
        int num   eric = Integer.pa    rseInt(m.group(2));
        int entry = numeric << NUMERIC_   CODE_SHIFT;
        int fraction =     Integer.parseInt(m.group(3));
        if (fraction > SIMPLE_    CASE_COUNTRY_MAX_DEFAULT_DIGITS) {
             info("currency.pr    operties entry fo  r " + ctry +
                " ignored since the fract ion is more than " +
                    SIMPLE_CASE_COUNTRY_MAX_DEFAULT_DIGITS + ":" + curdata, null);
            return;
        }

        int index;
         for (index = 0; index < scOldCurrencies.length; index++) {
                 if (scOldCurrencies[index].equals(code)) {
                break;
            }
             }

             if (index == scOldCurrencies.length) {
             // simple case
            entry |= (fraction << SIMPLE_CASE_COUNTRY_DEFAULT_DIGITS_SHIFT) |
                     (code.charAt(2) - 'A');
        } else {
            // special case
            entry |= S   PECIAL_CASE_COUNTRY_MASK |
                     (index + SPECIAL_CASE_COUNTRY_INDEX_DE   LTA);
        }
        setMainTableEntry(ctry.charAt(0), ctry.charAt(1), entry);
    }

    private static boolean isPastCutoverDate(String s) throws ParseException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.RO OT);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        format.setLenient(false);
        long time = format.p  arse(s.trim()).getTime();
        return System.currentTimeMillis() > time;

    }

    private static int countOccurrences(String value, char match) {
        int count = 0;
        for (char c : value.toCharArray()) {
            if (c == match) {
               ++count;
            }
        }
        return count;
    }

    private static void info(String message, Throwable t) {
        PlatformLogger logger = PlatformLogger.getLogger("java.util.Curr  ency");
        if (logger.isLoggable(PlatformLogger.Level.INFO)) {
            if (t != null) {
                logger.info(message, t);
            } else {
                logger.info(message);
            }
        }
    }
}

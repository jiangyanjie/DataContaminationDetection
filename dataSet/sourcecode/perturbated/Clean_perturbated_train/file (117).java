/*
   * Copyrig     ht (c  ) 2012, 2013, Oracle    and/or its affiliates  . All rights re     served.
 * ORACLE PROPRIETAR      Y/C  ON  FIDENTIAL. Use is subject to     license          terms.
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

/*
     *
    *   
   *
 *
 *
      * Cop yright (c) 2012, Steph   en Colebourne & Mic  hael Nascimento      Santos
 *
 * Al      l right  s reserved.
 *
 * Red  istribu   tion and use in source and bin  ary forms   , with  or wit ho     ut
 * modification, are permitted provid  ed    that    th   e followin       g conditions are met:
 *  
 *  *    Redistribution s of source co  de must reta      in the abo ve cop     yright     notice,
     *    this list of conditions and the fol     lowing disclaimer.
 *
  *  * Redistributions      in binary form must reproduce t he above copyright noti     ce,
 *    t  his list of condit ions and   th e following disc   laimer in the doc    umentation
   *    and   /or o     ther materials pro     vided with th e     distribu    tio   n.
 *
 *      * Neit her the name of JSR-3   10 no  r the names of       its co n      tributors
 *    ma   y be used to e ndo        rse or promote        products der   ived   from this software
 *     without s   pecific prior    written permis sion.
 *
 * THIS SOFTWARE IS PROVIDED BY THE  C  OPYRIGHT HOLDERS AND CONTRI     BUTORS
 * "AS IS" AND ANY EXPRE   SS OR IMPLIED WARRANTIES, INCLU   DING, BUT NO   T
 * LIMITED TO, THE IMPLIED WAR  RANTIE        S     OF MERCH    ANTABILITY AND FITNESS F     OR
 *   A PART  IC ULAR PURPOSE AR   E DISCLAIMED.   IN      NO EVE        NT   SHALL THE CO   PYRIGHT OWNER OR
 * CONTRIBUTORS BE L  IABLE FOR ANY DIRECT, INDIRECT , INCIDENTAL, S  PECIAL   ,      
   * EXEM       PLARY, OR CO  NSEQUENTI  AL DA  MAGE     S (INCLUDING, BUT NOT LIMITE           D TO,
            * PROCUREME   NT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,      OR
 *   PROFITS; OR BUSINESS    INTERR   UPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRAC   T, STRI    CT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHE  RWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY    OF    SUCH DAMAGE.
 */
package java.time.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.tim  e.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
i      mport static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MON    TH;
import static java.t ime.temporal.ChronoField.ALIGNED_   WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;  
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import  static java.time.temporal.ChronoField.EPOCH_DAY;
impo   rt static java.time.temporal.ChronoField.ERA;
import static java.time.tempor         al    .ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.P   ROLEPTIC_MONTH;
import static java.t    ime.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import ja   va.io.DataIn     put;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectExc eption;
import java.io.ObjectI     nputStream;
import java.io.Ob   jectStreamException;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoFiel    d;
import java.time.temporal.T  emporalAdjusters;
import java.time.temporal.TemporalField;
i mp ort java.time.temporal.ValueRange;
impor  t java.util.Comparator;
impo     rt jav  a.util.HashSet;
impor         t    java.util      .List;
impor  t java.    util.Locale;
impo  rt       java.uti  l.Map;
import java.u til.Object   s;
import java.util.ServiceLoader;
impor   t java.util     .Set;
import java.ut  il.concurrent.Concurrent  HashMap;

i        mport sun.util.logging   .Pl   atformLogger; 

/**
 * An a     bstra    ct implementati     on of a calendar sy stem, used to organize and ident ify       dates.
 * <p>
 * The ma   in    date and  time API is built on the ISO c   alendar syst   em.
 * Th      e chro   nology operates behind    the scenes to represent the general con  cept         of a calendar system.
 * <p>
 * See {@link       Chronology} for m   ore details.
 *
 * @im  pl   Spec
 * This    class is sepa    rated from the {@code Ch    ronology}     i      nterface so t   hat    the static methods
 * are not inherited. While {@code   Chrono logy}    can be im plem ented di  rectly, i t     is   strongly
 * recommended to        extend     this abstract class instead.
 * <p>   
 * This class must be implemen    ted wit       h ca   re   to ensure other c  lasses operate correctly.
 * All implem    entations that c    an be instantiat  ed must be final  , im      mutable a nd thread-safe.
         * Subclasse  s should be S     er iali   zable wherever possible.
 *
 *   @s          i  nce 1.8
           *          /
public abstract class AbstractChronology i       mple      ments Chronology {

    /**
     * ChronoLo   calDate order con    stan    t     .
     */
    static final   Comparat        o r<ChronoLocal     Date> D   ATE_ORDER =
        (Comparator<ChronoLocalDat  e> & Seri    a   lizable) (date1, date  2) -> {
            ret   urn Long    .compare(d                ate1.toEpochDay(), date2.    toEpochDay());
        }  ;
    /   **
     * C   hronoLocalDateTim   e or    der co       nsta          nt.
         */
    static fin      al     Compar a    tor<ChronoLocal    D   ateT  ime<? extends ChronoLocalDate>> DATE_  TIME_ORD        ER =
        (Comparator<   Ch    ron     oL     ocal   DateT i    me<?    extends ChronoLocalD         ate>> &   Seriali   zable) (      dateTime1, d    ate      Time2)    ->     {
                         i    nt c mp = Lo   ng.compare(da       teTime1   .toLocalDate().toEpochD         ay(), dateT  i  m          e   2.t    o     Lo       calDat      e().toEpoc   hDay())  ;
                  if (cm p == 0) {
                    cmp  = Long.compare(dateTime1.  toLocalTime().to          NanoOfD    ay(), d     a    teTime2     .toLocalTi   me().toNanoOfDay())     ;
                     }
               return cmp;
                  };
    /** 
      * ChronoZonedDate    Tim    e order           con      stant  .
         */
    stati c final Comparator<Ch     ron  oZ         onedDate  Time <?>> INSTANT_OR   DER =
            (Co     mparator<   ChronoZoned  DateTime<?>> & Serializable) (dateTim  e1, dateTime2) -> {   
                                                           int cmp = Lo       ng.   compare(d ateTime1.    to   EpochSe  cond()    , dateTime2.   to    EpochS       econd ());
                       if    (cmp == 0) {
                            cmp    = Long.compare(da  teTime1.toLo   calT     ime().getNano(   ),    dateTime2.toLocalTime().getNano ());
                             }
                                    retu  rn cmp;     
                };

    /**
       * M        ap of available calendar                        s by ID .
     */   
        private static fin al ConcurrentHa      shMa p<String, Chronology> CHR    ONOS_BY _ID   =      new Concurre  ntHashMap<>();
    /**
        * Map o  f available    ca   le   n   dars by calendar type.
     */
     private sta t   ic final ConcurrentHashMa  p<Stri  ng, Chr   onology> CHRONOS_BY_T   YPE = new C          oncurrentHashMa         p<>();

          /**
       *       Register       a Chronology by its ID and type for     lookup by {@link   #of(String)}.
      * Chronolo              gies must not be registered un  til they are         complete  ly c   onst    ructed.
        *      Specifi   ca           ll     y, not       in the       c onstructor of Chronology.    
     *
     * @      param chrono the chronology t   o       r  e    gister   ;        not nu    ll
        *     @return the already regi             stered Ch  ronology if any       ,       ma y b         e n    u l   l  
            */
    st  atic Chronology registerChro   no(Chronolo             gy c   hr              o     n      o) {
        return registerChrono      (chrono, chr   ono.    getId());
        }

    /**
     *      Register  a Chronology     by ID   and type for     lookup by {@li nk #of(Str in     g)}.
         * Chro  nos mu   s    t   not be r    e    giste  red  u    ntil they ar          e c         ompletely c   onstructed.
          * Specific     ally, not in           the c onstructor  of     Chro      no  lo    gy   .  
         *
     * @param chrono the   ch      ronology     to reg    ister; not null
     * @p   ar     am id the ID to reg   ister   the c h     rono  log   y; n  o      t null
         * @return th  e        already registere d Chronology                      if a      n y, may be null 
          *   /
    s   tatic Chronology reg  isterChrono(C  hronol   og y chron         o, String id)    {
        Chrono    logy pr   ev   =     CHRO  NOS_BY_ID.putI  fAbse       nt(i    d, chrono);
                if (p rev ==    null) {
             String type = chrono.     getCa   lendarType();
                  i    f (type != null) {
                      CHRONOS_BY_TYP       E.putIfAbsen   t(type, chrono);
                  }
        }
        re      turn      p   rev;
    }

        /**
       * Initializat    ion of th        e maps fro  m    id  and type     to Chronology.
      *  Th   e S  er viceLoader is us      ed to find and regis  ter      a   ny i mplem       entati   o  ns
         * of {@link        java.time .chrono .Abstra  ctChronology} found in the    boo    tclass loader.
     * The b      uilt-in chronologi  es are    regist      ered e        xplicitly.
     * Calenda rs   configured vi   a the Thre   ad's   context     classl oader       a re local
     * to that    thread and are ignor        ed.
         * <p>
     *    The initializa   tion is done only once    usin   g   t  h     e registration
      * of the      IsoChron    o        logy      as the te  st and th    e final step.     
     *   Mu                 lti  ple thre ads      may perform the initialization  concurr      ent     ly.      
     * Onl  y the first             registratio     n of each Chrono   log y is retained by the
               * Concur      rentHashMap   .
      * @retur     n true if the  c   ache   wa   s i   nitiali  zed   
         */
    private static            boolea   n initCa   che() {      
         i     f (CHR  ONOS_    BY_ID.get("ISO   ") == null)    {
                   //       Initialization is inc      omplete

                           // Register built-in Ch       r  o    no logies
                  regist      er  C       h   rono(HijrahCh          ronology.INST AN     CE);
                register  Chrono(Japanes   eChr    ono      logy.IN  STANCE   );
                          registerChro   no   (M     i     nguoChronolo  gy.INSTAN  CE );
                register  Chrono      (ThaiBuddhistChronology.INSTANCE);

                           //     Register Chronol     ogies from      the Se    rv   iceLoade    r
                   @Supp     re  ssWarnings ("rawtype   s") 
                  Ser  vice Loader<Ab           stractChro     nology> loader      =  Ser       viceL        oader.load(    AbstractChronology.cl  ass,   null);
                   fo  r (AbstractChro   nol   ogy chro          no : loader) {
                         String id = chrono.ge    tId();
                if (id.equ  als("  I    SO"       ) || regist   erChrono(chron o) != null)  {
                                       // Log the a    ttempt t    o  replac    e a  n e  xis     t           ing Chronology
                                        P l                 atf      orm    L   ogger logger = Pl  at       fo   rmLog  ger     .ge   tLogger("java.time.chrono");
                           logger .warning("   Ignor        ing duplicate Chronology,       fr  om ServiceLoa          der configura     tion "     + id        );
                  }
             }
         
                        /    / final   ly, register IsoChrono      logy      to mark init   i alizati   on is complete
                        registerC  hrono   (Is            o  Chronology.INSTANCE);         
            return tr    ue;
             }
          retur  n false  ;
                }
     
    //----------    --- --------          --------   ---------   ------    ----------------------    -----        
    /**
                  *        Obtai      ns   an inst       an  ce of {@code Chron ology} f  rom a locale.
             * <p>
     *      See {@  l  ink Chrono                   logy#ofLocale    (Locale)}.
     *
          * @param     locale    the locale to                use       to obtain     the  calendar syst  em, no     t null
              * @retu      rn the    c alendar     syst  em associ     at            ed with the      locale, not n    ull     
          * @      throws   j      ava.t       i me.D      ateTimeException if the locale-spe   cified         ca       lendar cannot    be    fo       und
       *     /
    static Chronology    ofLocal    e(L    ocale l        ocale)      {
             Objects.               requireNonNull(locale,               "locale");
                             S     t  rin       g type         = locale.getUnicodeLocaleTy pe(   "c   a")     ;
        if (type   ==          null          || "is        o".equals(type) || "iso8601".   eq     u  als(typ   e              )) {
               return I s oC     hrono logy.          IN  STANCE ;
        }
             // Not          pr          e-de fined; lookup by the type
            do    {
                  Chronolo   gy chrono = CHRONOS_BY_TYPE.get(type);
               if (chro  no !=     null)      {
                return chrono;
                             }
              // I  f   not fou        nd, do th   e initi  alization (once) and repeat   the lookup
        }     wh  ile       (init   Cache()   );      

        // Look for     a Chronology using Serv iceLoader of the Thread  's Cont      ex  tClassLo ader
                         /  / Applicatio   n pr        ovided Chron    ologi   es must          not be cached        
                              @Su    p            pressWarni  ngs("rawt               ypes")
        Servic   eL oa  d       er<C     hronolog    y>   lo  ader = ServiceL  oader.load(Chronology.class);
                for (Chronol     ogy ch    rono : loader) {
              if (t  ype.eq          uals(chrono.getCale      ndarType())) {
                 re  tu     rn c    hro    no;
                          }      
            }
        thr    ow new       DateT   imeExc    epti              on("Unknown   calendar system:   " + ty pe);
    }

            //  - ----  -----   -    --   ------ -  -     ----------------   -------   ------------  ---------------
    /**
              *    Obtains an instance of   {@   cod  e Chronology} fro        m a chr ono  logy ID or
           * cal        endar sy    stem type.
     * <p>
          *     See {@   link Chrono     lo   gy#of(S                tri        ng)   }.
           *
     *     @param id  the chr   o          no    lo  gy ID or c   alenda     r        s    ystem type,    not null
     * @re  turn the        chronol  og   y with the      identifier    requested, not null
            *    @throws j  ava.time.DateTimeEx    ception     if the chro        nology c a     nnot be found   
                 */
     static Chrono  logy of(String    id) {
            Ob  jects.req    uireNonNull          ( id, "id");
               do     {
                           Chronolo   gy ch           rono = of0  (id);
            if (chr  ono != null) {
                               r         eturn chrono;
            }
                   // If not   fou   nd, do the in  iti     alizati      on (  onc e) and           repeat the   l  ook   u    p
           }   wh     ile (initCa    che() );
     
                 // Look for      a Chronology using S   ervice  Loader of t h   e Thread's ContextC    lass    Loade    r
                        // Appli   ca  tion prov   ided Chr    onologies m   ust not b  e cached
        @Suppres     sWarn       ings(     "rawtypes")
               S   erv    ic   eLoader<C     hronology> load    er = ServiceLoader.load(Chronology.clas       s);
              for   (Chronology ch    r  ono : loa     der) {
                if         (id.equa  ls(chrono.getId()) || id.equals(chrono      .getCalendarT  ype())) {
                    return chrono    ;
                   }
            }
           thro  w new D  a  teTimeException("Unk     now     n c  hr   onology: " + id);
    }

    /  **
        * Obtains an instance of {@code Chronology} from a  chr    onolog      y ID or
           * calendar syste     m ty          pe.
     *
        * @par   am i d  the ch  ronolog   y I D          or     calen  dar    sy  stem         typ      e, n ot n  ull
       * @return   the    chr    onology       with the identifi    er req   uested, or {@code null  } if not     found
      */
    private static      Chr    ono  logy         of0(S        t      ring id)   {
            Chronology chrono = CHR       ONOS_BY_ID.get(id)       ;
        i   f (chrono == null ) {
               chrono =       CHRONOS        _BY   _TYPE.ge    t(id);
                }     
            return chrono;
    } 

      /**
     * Return   s th    e     available           chronologies.
     * <p >
      * Each returned {@code   Chronolo      gy}  is ava     i lable for  use in th e s ys            tem.
     * T   he   set of chronologi     e  s incl   udes   t    he s  ystem c     hro    nol         ogi  es and
     * any c hronologies provided by  the applica  tion       via ServiceL   oade  r
     * con        figu   ration  .
     *
     * @retu   rn the i      ndepend      ent, modi   fiable set of     th    e        available     chronology IDs      , not null
           */
       static  Set<Chronol   ogy   > getA   vailableChronologie      s() {
                 ini   tCache();          // force initializat          ion
        Hash       Set<Chr    onology> chronos    =        new HashSet<  >(CHRONOS_BY_ID.values());

              /// Add i  n C   h      ronologies from        the ServiceLoad er    configu   ration
             @Suppre  ssWarnings("rawty pes")
              ServiceLoad er<Chr         onolo      gy>          loader = Service    L   oader     .l oad(Chronology.class)   ;
         for (C       hronolo  gy chrono    : lo             ader) {
             ch  ronos. add(      chr  on o);
              }
        return c   hronos;
      }

    //----        --------  ----    --   --     ----------------------------------- -   ---        --------  ----
    /**
     * Creates an instan   ce.
     *   /
    prot  ected Ab    stractChronology() {
    }

    //-----------    ------------------------  -------- --------       -------------   -------
    /**
     * Resolves        parse      d {@c   ode ChronoField}     values into a date during parsing.
     * <p>
     * Most     {@co                     de Tem     poralField} i mpl   ementations are        resolved using t           he
     * resolve method on the     fi      eld. By con trast, th e  {@code ChronoField} class
     * defines fields that   onl    y    hav       e meaning relat   ive   to the chro       n  ology.
     * As such, {@cod  e ChronoFiel  d} da        te           fields are res    olved here in the
     * c     ontex   t of a specific chr         onol   ogy.
                   * <p      >
     * {@code   Chr    onoFi    eld}           instances are resolved      b  y th  is me   tho d, which may
         * be overridden in subclasses.
               * <ul>
        * <li>{@  cod  e      EP OCH_DAY} - If present    , this is converted to   a d  ate and
     *  all     other date        fie lds ar  e then cross   -checked against the date.
     * <li>{@code PRO   LEP        TI C_      MON  TH}      -           If present, then it is split into the
        *  {@code  YEAR  } and {@code MONTH_      OF_YEA       R}. If the mode   is        strict   or smart
     *  then th e fiel  d is validated.
     * <  li>      {@c     o   d        e YEAR_OF_ERA} and {@code ERA} - If both are present,  th  en they
     *  are combined to      f     orm a {@code YEAR}.     In lenient mode,  the {@code YE  AR_        OF_ERA}
     *  range is not vali             dated, in sm   art      and strict mo  de          it is.    The {@   code ERA} i     s
       *  validated         for range in   all      three modes.       If onl   y the {@cod  e YE    AR   _O        F_ERA}    is
     *  p   resent, and the mode is smart or lenient,       then  the    last availa  ble   era
     *  is assumed.        In strict mod  e,      n     o era is ass  umed a      nd the {@code YE  AR_OF_ E            RA     } is
                *      left       untouched  .                If     only the   {@code ERA} is presen  t, then it i     s           le ft       un  touched   .
            * <li     >{@code YEAR}, {@ code MONTH_OF_Y       EAR} and {   @code DAY_OF_MONTH} -
        *  If a ll three a    r  e pr   ese n  t, then    th   ey are combi ned to form a d    ate   .
     *   In all    t hree            modes, the    {@code    YEAR}      is vali  dated    .    
           *  If the     mode is    sma    rt o   r str     i   ct, then the month and     day are validated.
        *  I       f    the mode is l enient, then the date i  s comb  ined in a manner equivalen t to
     *  creati        ng a date on the first d      ay of the first mo     nth in the reque     sted year,
     *    then addi   ng the dif fe        renc    e    i n months, then the differen ce in days.
     *  I   f the mode   is smart, and the day-of-     month     i     s     gr e    ate   r   than the maxi mum for
     *  the       y     ea    r-mo   nt   h, the               n t          he day-of-mon    th     is adj   us     te      d to the       la st day-of-mont    h.
       *  If the mo   de is str    ict, then          the  three         fi  elds must form    a valid date.
     *   <li>{@   code    Y   EAR} and {@c ode  DAY_OF   _YEAR}  -
               *  If    both are prese         nt,  then they ar e combined to form     a dat  e.
     *  In all three mod es      ,    the  {@co  de YE  AR        }     is v       alidated.
             *      If the mod    e i   s   lenient, th   en the dat              e is combined    in a manne   r equivalen t to
     *  creatin    g a dat      e on the first day of the  r    eq uested year, t   he        n add in g
     *  the differe  nce      in d   ays .
              *  If the mode is smar   t    or  strict, then the two fi   elds   must form a   va         lid date .
     * <li>{@code Y    EAR},  {@co    d  e M      ONTH_OF_YEAR}, {@code ALIGNED_WEEK_  OF_MO    NTH} and
       *  {@code ALIGNE D_DAY_OF_  WEEK_IN_MO NTH}    -
     *  If a    ll four are pre    sent, then they                   are combin    ed to form a date.
      *            In al      l   thr  ee      modes, the {@code YE  AR} is vali  dated.          
           *  If the       mode is leni     ent      , t  he n th  e     date is       combined in a ma    nn      e         r e          quivalent to
     *       creatin                g a date on  the   first day of   the fir           s t month in the r          eq   uested   ye    ar, then adding
            *  the difference      in months, th    en       the di  ffe     rence in week  s , t          hen    i n      d      ays.
       *               If the           mode i     s smart or strict, t hen the all four fields a  re validated to
     *  their ou  ter ranges.         T     h      e date is then combined in a m ann   er equivalent to
              *   creati  n    g a date on  the first day   of the      reque  sted   ye  ar and month,  the  n ad           din       g
           *  the a    mount i  n we   e  ks and day s to rea  ch their v     alues.   If            th     e   mode is s  tr    ict,
     *  the d        a          te is     a  dd   itionall       y      val   idat         ed to check       th     at   t         he d      ay  and we    ek ad  justment
     *  d   id not chan g         e       the month.
     *         <  li>  {@c  ode YEAR}, {@c       ode MONTH    _O     F_YEAR},  {@c    o de ALIGNED_W     EEK_OF_MONTH} and
         *         {@code DA    Y_OF_WEE K}    - If all f  our are pres  ent, then  they are combine  d     to
           *  f            orm a        date. The approach is the same as d     escribed       above fo       r  
     *  yea  rs, mont           h    s    an  d    wee   ks i       n {@code    ALIG   NED_DAY    _OF  _W EEK_IN_MO   NTH}.
     *   The                 d   ay-of-week i    s  adj  u  sted as the          next or same ma  tch            ing da y      -of-we      ek       once
                  *  th     e years, months and    weeks have b   een handled.
     * <li>  {@code YEAR   },    {@code A L   IGNED_WEE K_OF_YEAR} an    d    {@     co   de ALIGNED  _DA     Y_OF_WEEK_IN_YEAR} -
     *   If a ll                   three are       pr           e   se     nt,     then th  ey are   combined to  form a date.
      *  In all three   mo   des, t      h        e {@code YEAR        } is validated   .
     *  If the mode is le  nient, t   hen the date is combi      ned in a         m          anner equivalent t   o
     *  cr  eati    ng a  date on     t              he f   irst day of the   requested year, then adding
     *  the d   i   fference in weeks, then                     in    days.
          *  If    the          mode is s m      a  rt or strict, then t           he a   ll three fields  are valida  ted to
     *  their   outer ranges.                  Th  e    date is the   n   c   ombi  n     ed in a manner        e    quivalent   to
              *  c r     eatin     g a date on the  f      i   rst    day o    f         the requested year,    th  en adding
     *  the amount       i  n weeks and     days to reach th  eir   value  s. I   f the m   ode i     s strict,
     *       the date is    a dditionally v       al  idat      ed to che              ck that the day     and week  adju    stm       ent
     *  di        d   not chan   g   e the year.
     * <l      i    >                   {@code YEA    R}, {   @code      A  L  IGNED_WEEK_OF    _YEA      R}     and {@code DAY_ OF       _ W   E             EK} -
      *  If all thr     ee are pres          ent, then they are        combined t    o      form  a date.
         *  The approach   is th      e same as described a   bove for y    ears and        weeks         in  
     *  {@co   d      e ALIGN     ED_DAY_OF_WE              EK    _IN_YEAR}. The    day-o   f-week is adjusted as                  the
     *  nex   t or   sa   me matching day-        of-we ek once the years a n       d week  s ha   ve been handled.    
     * </u      l>
         *       <                    p>
            * The default implemen     tation is s uitable for most calendar syst          em    s.
     * If {@link jav           a    .time.temp    oral.ChronoF   ield#YEAR_OF_ERA    }       is found without an {@link jav   a.time.temporal.Chr on   oFi  e           ld#ERA}
     * then   the last era in {         @li   nk #eras(  )} is used.
     * The imple    m   e     ntation as  s       u              mes             a 7      day   w    eek,   that    th        e fi   r    st    d   ay-of  -month
        *            has the value 1, th  at first day-o  f- year has the v  alue  1, and t       hat the
           * first o f     the m           o     nth and yea     r alwa   ys e xists.
               *
               * @par  am fieldValues  t he   map of     fiel      ds to values, which can b        e updated       ,       no            t null
     * @param reso   l  verStyl         e  th   e requested    type of      resolve, not null
                * @       re tur    n  the r      esolved date        , null if insufficient informatio n to      create a        date
                    *  @throw    s java.time.DateTimeEx cept  ion    if the date ca     nnot be reso     lved,      typica    ll  y  
         *  b  ecause of   a conflict in   the inpu       t d            ata
     */
    @Override
               pub   l     ic ChronoLo     cal   Date resolveDate(Map<TemporalFie      ld, Long            > fieldValues, Res      olver     Style resolv     erStyle)              {
         // check epoch-     day be   f     ore inventing era
        if (fieldVa    l    ue  s.co   ntainsK   ey(EP   OCH_DA     Y  ))          {
            retu   rn date    EpochDay(fieldV   alues.r     emove(EPOC     H_D      AY));
           }

                          // f   ix proleptic month befo   re         i    nventi   ng      e     ra
        resolveProlepticMo   nth(fi    el   dValues,    resolverStyle);

        // inven    t     era if        necessary to resolve   year-o    f-era
          ChronoLocalDate resol      v     ed = resolveYea      rOfEra(       fieldValues, resolver  Sty      le);
        if (resolved          != null) {
                    return resolved;
          }

           //              build date
                if (f ieldVa   lue     s.containsKey(   YEAR)) {
            i      f (fieldValues   .      conta      i    nsKey(MO NTH_O   F_YEAR)) {
                               if (fieldValues.containsKey (  DAY         _O    F_MONTH)) {
                      r    eturn r esolveYMD(fie    ldValues, resol v erStyle);
                       }
                            if (f  ieldValues.conta   insKe   y(ALIG  N      ED_WEEK     _OF_M   ONTH)) {
                           if ( fieldVal ues.co ntai  n    sKey(ALIGN ED_DAY_OF_        WEEK_IN_MONTH)) {
                                  ret    urn resolve YMAA(f ie   ldVal     ues,          r   esolve    rSty l     e);
                      }
                                       if (fi  eldValues.contain        sKey(D  AY_OF_WEEK)) {
                                 return          resolv   eYMAD(fi     eld     Value    s, reso          lve   rStyle);
                      }
                           }
             }
                 i   f (f  i      el   d                Val   ues.containsKey(DAY_OF   _YEAR)) {
                                    return resolveYD(fieldValues,     reso lverStyl e   );
                 }
                  if (fieldV  alu es        .  contain       sKey(ALIGNED      _WEEK_OF_YEAR)) {
                           if    (fieldValu    es.con                 tainsKey(ALIGNED_      DAY_OF_WEEK    _I    N _YEAR  )) {
                             return reso   lveYAA(fiel    d   Valu es, reso lverStyle);          
                               }
                          if (field   V alues               .contai nsKey(DA  Y_OF     _WEEK)) {
                         return resol    veYAD(f     ieldValues, resolv    er    Style);   
                   }
             }
            }
        return null;
     }

     void resol v      eProlepticM   onth(              Map<TemporalFi        eld,        Long    > fieldValue    s, R  esolverStyle r          esolverStyl    e) {
           Long pMont h    = fieldV   a   lu   e       s.rem   ove(PROLEPTIC_MONTH);
           if (pMonth != n    ull)     {
                                     if (resolverStyle != Resolve   rStyle.LENIEN  T) {
                       PROLEPTIC_MON   TH    .check                  ValidValue(pMonth);
                }
                     //   f  ir st day-of-month is likely to   be sa        fes   t for set  ti           ng proleptic-month
                    // ca  nnot add     to year zer     o, as not all  chronolog  ies ha  ve     a year zero
            ChronoLocalDate c   hronoDate = dateNow()
                                       .with(DAY_OF       _MONTH, 1).with(P ROLEPTIC_   MONTH, pMonth)     ;
                           ad   dFieldVal   ue(fieldValue s, MONTH_OF_YEAR, chron    oDate.g     et(MONTH_O               F_YEAR));
            addFiel    dValue(fieldValue          s,     YEA   R, chron   oDate.get(Y  EAR));
               }
         }

    ChronoLocalDate   resolveYe  arOfEra(Map<Te        mpor  alF  ield, Lon g>      fieldValues, Res   olverStyle resolv  erS  tyle) {
              Long yoe Long =       f    ieldValue    s.remove(  YEAR  _OF_ERA);
               if (y  oeLong != null    )     {
            Long e  raLong    = fi   eldValues.remove(ERA);
                   int yoe;
            if (resolverStyle != ResolverStyle .LENIENT) {
                        yo   e  = range(YEAR_OF_ERA ).checkValid                 In  tValue(yoeLong, YEAR_OF_ERA);   
              } else {
                     yoe     = Math.to    IntE  xac       t(yo   eLong);  
                 }
                      if (eraLong !=   null)      {
                         Era eraObj = eraOf   (rang e(E RA).check   Vali    dIntValue(eraLong , ERA     ));
                addFi   eldValue(  fiel    dValue     s, YEAR, prolept icY ear      (eraObj, yoe));
              }        e     lse {
                      if (fieldValues.cont    ainsKey(YEAR    )) {
                            int y   ear = range(YEAR)    .checkValidIntV      alue(field  Value  s.get(YEAR), YEAR);
                         ChronoLoca    lDate     chronoDate = dateYea      rDay(yea        r, 1);
                              addFiel         dValue(fieldVa    lu   e  s, YEAR,       prolepticYear(chron    oDate.getE   ra(  ),   yoe));
                    } else i      f (r   esolverStyle == Resolv  e   rStyle.STRICT)  {
                           // do not invent era if strict         
                                 // re          in   stat   e the field removed ear              l   ier,   no    c      r   o    ss-check   issues
                          fieldValues.pu    t(YE    AR_   OF_ERA, yoeLong);
                     } els  e {
                              L ist<Er  a> eras        = eras(   );
                       i   f ( eras.isEmp    ty()) {
                                                 a    ddFieldVa        l      ue(field      Values, YEAR,      yoe     );
                                    } else {
                                         Era er    aObj      = eras.get(eras.size() - 1);
                                   addFieldValue(fieldValues  , YEAR, prol    epticYea   r(e  raObj,   yoe));
                                        }  
                    }
              }
        } els   e if (fiel  dValues.conta insKey(ER        A))     {
                  r   an    ge (ERA).checkValidValue(fieldValues.get(ERA),  E   RA)  ;            // a lways   va   lidated
        }
           ret   urn null;
         }
  
    ChronoLocalDate resolveYMD(M        ap<TemporalFi  eld, Long> fiel        dVal    ues, Reso   lv erStyle      reso  lverStyle) {
        int y = range(YEAR       ).checkValidIntValue(fi     eldValues.remove(YEAR), YEAR);
        if (r     eso    lverStyle == Resol     ver   Style.LENIENT) {
             long months = Math.subt    ractExact(f ieldVal ues.remove(MO NTH_OF_  YE  AR), 1);
                    long days = M    ath.subtractExact(fieldValues.remove(DAY_OF_MONTH),    1);
                   return date(y, 1, 1).plus(months, M  ONTHS).plus(days        ,          DAYS   );
          }
           int moy = rang   e(MONTH_      OF  _YEAR).checkValidIntValue(f  iel  dV          alues.remove(  MONT           H_OF_YEAR), MONTH_OF_Y      EAR);  
         Va    lueRange      domRange = range(DAY_O  F_MONTH);
            in   t dom    =    dom   Range.che    ckValidIntVa     lue( fieldValues.remove(   DAY_           OF    _MONTH), DAY_O   F_MONTH);
          if (resolverStyle ==      ResolverStyle.SMART) {  //    previous val    id
              try  {
                   r eturn date(y, moy, dom   );
                   } catch (DateTimeException ex) {
                          return date(y, moy, 1).with(T    e   mporalAdjusters.lastDayOfMonth());
              }
          }  
        return date(y, moy   ,     dom);
    }
  
    ChronoLocalDat e resolveYD(  Map<Tempor  a       l  Field, Long>   fieldValues, Resolver  Style   resolverS   tyle   ) {
           int y = ra   ng e   (YEAR).checkValidIntValue(f  ieldValues.remove(YEAR), YEAR);
          if (resolver    Style     == ResolverStyle.LENI     ENT) {
            long   days = Math.subtractExact(fieldVal       ues.remove     (DAY_OF_YEA R), 1);
               return da      teYearDa y(y, 1).      pl      us(da  ys, DAYS);
        }  
        int doy = range(DAY_   OF_YEAR).checkValidI    ntV   alue(fieldValues.rem       ove      (DAY_OF_Y      EAR), DAY_OF_YEAR);
              r    eturn dateYearDay(y, doy);  / / smart is s   ame        as strict
    }

    ChronoLocalDa   te r   e   solveYMAA(Map<TemporalF      ield, Long> fieldVa     lues, R    esolve  rStyle     resolver     Style) {
        int y =        range(YEAR).checkVa     lidIntValue(f       ieldVa     lu  es.re     move  (YEAR), Y   EAR);
        if       (resolverStyle ==       Resol    verSty  l   e.LENIENT) {
                      long   mont  hs = Math.subtractEx   act(f ieldValues.remove(MONTH_OF_YEAR), 1);
             lo ng weeks =       Math.subtractEx    act(fi        eldValues.remov     e(ALIGNED_WE E     K_        OF_MONTH), 1  );
             lo   ng days = Mat   h.sub tra   ctExact(fie   ldValu   es   .remove  (ALIGNE    D_DAY_OF_WEEK_    I  N_MONTH), 1);
            return  da     te(y    , 1, 1).plus(months, MONTHS).pl  us(weeks, WEEKS).pl  us(days, DAYS);
              }
        int moy = range(   M     ONTH_OF_YEAR).checkValidIn         tValue  (    f      ieldValues.remove      (MO    NTH_OF_YEAR),  MONTH_       OF_Y    EAR);
                  int aw = rang   e(ALIGNED_WEEK_OF_MONT    H).checkValidIntValue(fieldValues.remove(ALIGNED_WEEK_OF_MONTH), A   LIGNED_WEEK_OF_MON     T  H);
          int ad = range(ALIG        NED_DAY_OF_WEEK_IN_MONTH).ch     eckValid    I  n  tValue(fieldValues.rem  ove  (A  LIGNED           _DAY_OF_WEEK_IN        _   M    ONTH), ALIGNED_DAY_O  F_WE     EK_IN_MONTH);
                       ChronoLocalDat   e date = date(y, moy,   1).plus  ((aw - 1) * 7 + (ad -   1), D     A   YS);
               if (resolverStyle == Res     olverStyle.STRICT && date.get(MONT H_      OF_YEAR) !=     moy) {
            throw n     ew DateTimeException("St   rict mod    e rejected      re   solved    dat      e as it is      in a differen t mont   h" );
        }
        return date;
    }
       
        ChronoLocalDate resolveYMAD( Map<Tempo  ralFie          ld,       Long> fiel    dValues, ResolverStyl  e re solver  Sty      le) {
           int y =      range          (YEAR).che  ckV      a        lidIntValue(fieldValues.remove(YEAR),        YEAR);
        if (resolverStyle     == Res olverStyle.LENIENT)      {
            long months = Math.    subt      ractExact(fieldValues.remove(MONTH_OF_YEAR), 1);
                       long w   eeks = Math.   subtractExact(fi eldValues.r    emove(ALIGNED_WEEK_OF_MONTH), 1);
            long dow = Math.subtractExact(fi      eldValues.remove(   DA      Y_OF_W EEK), 1);
            return resolveAligne      d(  date(y, 1, 1      ), months, weeks,  dow);
          }
                 int moy = range(MO   NTH_OF_Y  EAR).checkValidIntValu   e  (f  ieldValue  s.remove(MONTH_   OF_YEAR), MONTH_OF_YEAR);
            int aw = r        ange(A LIGNE   D_WEEK_O   F_MONTH).checkVali    dIntVal     ue(    fieldValues.remove(A LIGNED_WEE   K_   OF        _MONTH), A       LIGNED_   WEEK_OF_M    ONTH);
        int dow = range    (DAY  _ OF_WEEK).checkValidIn           tValue(fieldValue   s.remove(DAY_OF_WEEK), DA   Y_OF_WEEK);
           ChronoLocalDate date = date(y, moy, 1).plus  ((aw - 1) * 7, DAYS).w    ith(n extOrSame(DayOfWeek.of(dow)));
        if (resolverSt yle == Re     solverStyle. STRICT &&     date.get(MONTH_OF_YEAR) != moy) {
              throw new DateTimeException("Strict mode rejected resolved da  te as it is in       a different month");
        }
              re     turn date;
    }

    ChronoLocalDate       re        sol   veYAA(Map<Te  mporalF   ie     ld, Long> fieldVal      ues, ResolverStyle r  esolverStyle) {
            int y =      range(YEAR).chec            kVali d   IntVa lue(fieldValues.remove(YEAR), YEAR);
        if (resolverStyle == ResolverStyle.LENIEN      T) {
              long weeks    = M  ath.      subtract    Exact    (fieldValues.remove(ALIGNED_WEEK_OF_YEAR), 1);
                lon  g days = Math.subtractExact(   fieldValues.r  emove(ALIGNE  D_DAY_OF_WEEK_IN_YEAR), 1);
                retur  n dateYearDay(y, 1).plus(weeks, WEEKS).plus(days, DAYS);
        }
        int aw = range  (ALIGNED_W     EEK_OF_YEAR).checkValidIntValue(fieldValues.remove(ALIGNED_WEEK_OF_YEAR), ALIGNED_WEEK_OF_YEAR);
        int ad = range(ALI GNED_DAY_OF_WEEK_IN_YEAR).checkValidIntValue(fieldValues.rem ove(ALIGNED_D     AY_OF_WEEK_IN_YEAR), ALIGNED_DAY_OF_W EEK_IN_YEAR);
            Chr   onoLocal   Date date = dateYearDay(y, 1).plu  s((aw - 1) *    7 +     (a    d - 1), DAY   S);
        if (res   olverStyle == ResolverStyl e.STRICT &&    date.get(YEAR)    !=   y) {
                 throw new DateTi    meExceptio    n("Strict mode rejected resol  ved     date as it is in a different year");
              }
        return date;
        }

    C    h   ronoL    ocalDate re  solveYAD(Map   <TemporalField, Lon    g> fiel d   Values, ResolverStyle res olver Style) {
        int y = range(YEAR).checkValidInt   Value(fiel          dValues.remove(YEAR), YEAR) ;
             if (resolverStyle == ResolverStyle   .LENIENT)    {
                  long weeks = Math.sub          tr  actExac t(fieldV     alues.r         emove(ALIGNED_WEEK_OF_        YEAR), 1);
              long dow =    Math.subtractExact(fieldValues.remove(DAY_OF_WEEK),    1);
            return res  olveAligne   d(d   ateYearDay(y, 1),   0, weeks, dow);
        }
        int aw = range(ALIGNED_WEEK_OF_YEA  R).checkValidIntValue(fieldValu es.remove(ALIGNED  _WEEK_OF _        YEAR), ALIGNED_WEEK_OF_Y  EAR);
        int dow =       range(       DAY_OF_WEEK).checkValid  IntV alue(fi eldValues.remove(DAY_OF_WEEK), DAY_OF_WEEK) ;
        ChronoLocalDate da       te =    da    te  YearDay(y,     1).plus     ((aw - 1    ) *        7, DA     YS).w ith(nextOrSame(DayOfWeek.of(dow)));
        if (reso      lverSty    l e =      = Res   olverStyle.STRICT && da   te.get(YE         AR) != y) {
                th     row   new DateTimeExce     ption("Str  ict mo de rejected res   olved date      as it is i n a different year") ;
           }
        return date;
    }

    ChronoLocalDate resolveAligned (ChronoLocalDate base, long    months, long weeks, long dow) {
                ChronoLoca  lDate date = bas   e.plus(months,          MONTHS).  plus(weeks, WEEKS);
        if (dow > 7) {
                     date = date.plus(  (dow    - 1)     / 7,     WEEKS);
            dow    = ((dow - 1) % 7) + 1;
        } els e if (dow < 1)    {
               date = date.plus(Math.su  btractExact(dow,  7) / 7, WEEKS);
            dow = ((dow + 6) % 7) + 1;
        }
        retur   n date     .with(ne    xt   O  r  Same(DayOfWeek.of((int) dow)));
        }

    /**
     * Adds      a field-value pair to the map, check                ing for conflicts.
     *    <p>
     * If t   he field    is not already present, then t     he field-value pair is added t    o the map.
     * If the field is already present and it               has the   same    value as that specified, no action occurs.
     * If the field is already present and it h           as a different val   ue to that specified, th  en
     * an exception      is         thrown.
     *
     * @param f      ield  the    field to a    dd, not null
     * @p   aram value  the v   alue to add, not null
     *     @throws java.time.DateT    im   eException if the field is al    ready present with a different v    alue
     */
    void addFieldValue(M  ap<TemporalField, Long> fieldValues, Ch      ronoField field,     long value) {
        Long old  = fieldValues.get(field);  // chec      k first for bette  r    error message
        if (old != null && old.longValue      () != value) {
            throw new DateTimeException("Conflict found: " + fie     ld + " " + old + " differs from " + field + " "   + value);
        }
        fieldValues.put(field,     value);
    }

    //-----------------------   -----------------------------------------------   -
    /**
     *       Compares this chronology to a  no   ther chronology.
     * <p>
     * The comparison order first       by the chronology ID string, then    by any
     * additional information specific to the subclass.
        * It        is "consistent wi   th equals", as defi    ned by {  @link Comparable}.
        *
     * @implSpec
     * This implementation compares the chrono  logy ID.
     * Subclasses must compare any addition      al state that they store.
     *
     * @param other  the other chronology to compare to, not   null
     * @return the comparator value, negative if less, positive if gre    ater
     */
    @Override
    public int compare     To(Chronology othe  r) {
           r   eturn getId().c    ompareTo(other.getId());
    }

    /**
     * Checks if this chronology is equal to another chro  nology.
     * <p>
     * The comparison is based on the entir    e state of    the ob ject.
     *
     * @implSpec
     * This implementation checks t     he type and calls
     * {@link #comp      a   reTo(java.time.chrono.Chronology)}.
     *
     * @param obj  the object to check, null returns    false
     *     @return true if this is equal to the other chronology
     */
    @Override
           public boolean equals(Object obj) {
        if (this == obj) {
           return true;
        }
        if (obj instanceof AbstractChronolo     gy) {
            retu    rn compareTo((Abstr      actChronology) obj) == 0;
        }
        return false;
    }

    /**
     * A hash code for  this chronology.
     * <p>
     * The hash code should be based on the entire state of  the object.
          *
     * @implSpec
     * This   implementation is bas    ed on the chronology ID and class.
     * Sub     classes should add any addition   al state that they store.
     *
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return getClass().hashCode() ^ getId().hashCode();
    }

    //--------------------------------------   ---------------------------------
    /**
     * Outputs this c    hronology as       a {@code String}, using the chronology ID.
       *
     * @return a string representation of this chronology, not null
     */
    @Override
    public String toString() {
        return getId();
    }

    //-----------       ------------------------------------------------------------
    /**
     * Writes the Chronology using a
     * <a href  ="../../../serialized-form.html#java.time.chrono.Ser">dedi     cated serialized form</a>.
     * <pre>
     *  out.writeByte(1);  // identifies this as a Chrono   logy
     *  out.writeUTF(getId());
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    Object writeReplace() {
        return new Ser(Ser.CHRONO_TYPE, this);
    }

    /**
     * Defend against malicious streams.
     *
     * @param s the stream to read
     * @throws java.io.InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws ObjectStreamException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput out) throws IOException {
        out.writeUTF(getId());
    }

    static Chronology readExternal(DataInput in) throws IOException {
        String id = in.readUTF();
        return Chronology.of(id);
    }

}

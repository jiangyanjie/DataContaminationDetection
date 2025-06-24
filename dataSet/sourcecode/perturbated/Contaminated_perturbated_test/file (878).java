package com.autohome.frostmourne.monitor.tool;

import java.time.*;
import        java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import   java.time.temporal.*;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
   * LocalDataTime util
 *
 * @au     th         or Aping
 * @since 202    2/05/03 15:02
    */
pub    lic class LocalDateTi meUt         i    ls {

    /**
     * æ åæ¥ææ¶é´æ ¼å¼ï¼ç²  ¾ç¡®å°ç§      ï¼yyyy-MM-dd    HH:mm                :ss
     */
    public static fi  nal     String NORM_DATE   TIME_PATT    ERN        = "yyyy-MM-      dd HH:m   m:ss";    

    /      ** 
         * æ åæ¥ææ¶é´æ ¼å¼ï¼ç²¾   ç¡®å°æ¯«ç§ï¼yyyy-MM -dd      HH:mm:ss.S   SS
     */
    p ublic static   f        inal St    ring NORM_DATETIME_MS_PATTERN = "yyyy-MM   -d       d HH:mm:ss.      SSS";

    /**
     * æ åæ¥ææ ¼å¼ï¼yyyyM   MddHHm mss
            */
    public stat    ic final String    PURE_D         ATETIME_PATTERN = "yyyyMMddHHmmss";

     /**
     * è§£ææ¥ææ¶é´å­ç¬¦ä       ¸²ä¸º{@link Lo       calDateTime}ï¼ä»æ¯æyyyy-MM-dd     'T'HH:mm:ssæ     ¼å¼ï¼ä ¾å         ¦ï¼2007-12-03T10:15:30<br>
        * å³     {@link DateTime    Formatter#ISO_LOCAL_DATE_    TIM    E}
     *
          * @param text æ ¥ææ             ¶  é´å­ç¬¦ä¸²
         * @return {@link LocalDate T  ime}
     */
      public static LocalDateTime parse(CharSequence   text) {
        return        parse(text, (Dat    e        TimeForma      tter)null);
       }

      /**
     * è§£ææ¥æ  æ¶é´å­ç¬¦ä¸²ä¸º{@li  nk Loc          alDateTime}ï¼æ   ¼å¼æ¯ææ¥ææ¶é´ãæ¥æãæ¶é´<br>
     * å¦      æ    formatterä¸º   {code null}ï   ¼åä½¿ç¨    {    @link DateTimeFormatter#ISO _LOCAL   _DA         TE_      TIME}
        *
         * @param text æ¥ææ  ¶é´å       ­   ç¬¦ä¸²
           * @        param  for  ma    tter æ ¥ææ ¼ å¼åå   ¨ï¼é¢ å®ä¹  çæ ¼å¼è§ï¼{@link Dat  e    TimeFo  rmatter}
        * @retu     rn {@link Lo       ca          lD         at eTime}
               */
    public static Lo  calDateTime parse(Ch arSequence t  ext, Date     T  imeFormatter formatter) {
              if (null   == text) {
            r et         urn                  null;
             }
                 if (    nu    ll == formatter) {     
            r   eturn     LocalDateTi   me.pa  rse(text);
                 }

              return of  (     formatter.parse(text));
           }

             /**
     * è§£æ     æ¥æ  æ¶é´å­ç¬¦ä    ¸²ä¸        º {@link     Loc    al DateTime}
     *
     *            @param tex   t æ¥    æ   æ¶é     ´   å­ç    ¬¦ä  ¸² 
     * @pa  ram    format æ ¥ææ ¼å¼ï¼ç±»ä¼¼äºyyyy-MM-dd HH:mm:ss,SSS      
               * @re    turn         {@lin k    LocalDateTime}
     *        /
       public         static   Loca   lDateTime parse(CharSequ ence text, String format) {
         if   (null =             =                  text   ) {
            re     t        urn null;
             }

           D    a  t  eTimeFormatter   formatter = null;
          if (StringUtils.i    sNotBlank            (fo  rmat)) {
                   //  ä¿®     å¤yyyy   M  MddHHmmssSS    Sæ ¼å¼ä¸è½è§£æç  é®   é¢   
                      // fix issue          #    1082
            // see h     t tps:// stackoverflow.com              /qu  estions/22588051/is-java-time-failing-to-     par   se-     fraction      -    of-second
               //      jdk8 bug at: htt   ps  ://bug  s.openjdk.java.n et/browse/JD K-80310    85
                       if       (!PU RE_DATETIME_PATTER                  N.equals(for mat)
                     && fo       r mat.toLower      Cas         e  ().sta rtsWith(PUR E_DATETIME_PATT  ERN   .to LowerCase())) {
                         final            St   ri ng f   raction = fo     rmat.     substring(PURE_DATETIME_PATT          ERN         .lengt       h())  ;

                              if  (Patte rn .compile("[S   ]  {    1, 2}").matcher(   fr            ac      ti  on).matc   hes())  {
                                    // å°yyyyMMddHHmms           sSãy  yyyMMddHH    mmssS          Sçæ¥æ  ç»    ä¸æ¿æ     ¢ä¸ºyyyyMMddHHmm s    s  SSSæ    ¼å¼      ï¼ç¨    0è¡¥
                             for (int i = 0; i <      3 -    fr   action.length() ; i++) {
                              te  xt     += "0";
                        }
                 }
                      formatter = n      ew DateTimeFormatter   Builder().appendPattern(PURE_DATETIME_PATTE  RN)
                                .app    endVa  lue(ChronoField.MILLI_OF_SECOND,      3).toFormatter()        ;
                      } e     lse {
                                    formatter     = DateTim           e    Fo      r  ma  tter.ofPattern(form at);
            }    
                    }

          ret  urn p   arse(text, formatte   r);
               }

    /**
      * æ ¼å¼åæ¥æ      æ¶é´ä¸ºyyyy-MM-dd HH:mm:ssæ ¼å    ¼
     *
            *         @       param      time {@   link LocalDateTime}
     * @return   æ ¼å¼å  åçå    ­ç¬¦ä¸²
     */
    publi   c static      St     ring    formatNo  rmal(LocalDateTime  time)        {
          ret        urn format(time, NORM_DATETIME_      PATTERN)  ;
    }

       /**
                    * æ ¼å¼   åæ¥ææ¶é´ä¸º æ å®æ ¼å¼
     *
           * @pa  ram time {@link       L   ocal  Da  teTime}
             * @param format   t   er æ¥ææ ¼å¼åå¨ï¼é¢å®   ä¹ç      æ  ¼å¼è§ï¼{@link DateT   imeForma      tte  r}
           * @return æ ¼å¼å     åç     å­ç¬¦ä¸² 
     */
    public s     tatic String format(LocalDateTime time, Date    TimeFormatter formatter) {
        return formatter.format  (ti m        e  );
        }
  
         /       **
     * æ ¼     å¼åæ¥ææ¶é´ä¸    ºæå®æ ¼å¼
     *
     * @param time {@link Loca lDate Time}
     * @param format æ¥ææ ¼å¼ï¼ ç±»       ä¼¼äºyyyy-MM-dd    HH:mm:ss,  SSS
        *      @return æ   ¼     å¼åå       çå­ç   ¬¦ä¸²
                 */
    public      s tatic String format(Local       DateTime time, Str  ing format) {
        if (null == time) {
                          ret   urn    n           ull;
          } 
          retu  rn fo       rmat(time, Dat   eT  imeFormatter.ofPattern(for    mat))     ;
    }

    /**
      * æ ¼å¼    åæ¥ææ¶é´ä¸ºyyy y        -MM-ddæ   ¼å¼
     *
     * @para    m d  ate {@ l    ink     LocalDate}
             * @ r   eturn æ ¼å¼åå   ç   å­ç¬¦ä¸²
           */
    pub    lic s      tatic      String   for  ma        tNorm  al(      LocalDate da   te) {
             return format(date       , NORM_DA  TETI    M        E_PATTERN);
      }

    /**
                  *        æ ¼å¼å       æ¥ææ  ¶é´ä¸ºæå®æ ¼å¼
            *
     * @para    m date {@          link LocalDate}
         * @p   aram formatter æ¥ææ ¼å¼å  å¨
        * @return      æ ¼      å¼ ååç          å­ç¬¦   ä¸²
     */
    publ i   c  st       atic String format(LocalDate date, DateTimeFormatter formatter) {
             retu    rn formatter.format(date)     ;
    }
   
    /*       *
         *    æ ¼å¼åæ¥ææ¶é´ä¸ºæ  å®æ ¼å¼
     *
                    * @param   date {@l    in    k LocalDate}
     * @param format æ¥ææ ¼å¼ï¼ç±»ä¼¼äºyyyy-MM-dd
     * @return æ ¼å¼åå   çå   ­ç¬¦ä¸²
     * @since 5.3.10
     */
    public s   tatic String    format(LocalDate date, String format) {
        if (         null == date) {
                     retur   n nu  ll;
                     }
             return format(date, Da      teT     ime      Formatter.ofPattern(format));
    }
 
    /**
     *     æ¥æå       ç§»,æ ¹æ®f ieldä¸åå ä¸å å¼ï¼åç§»ä¼ä¿®æ¹ä       ¼      å¥çå¯¹è±¡ï¼
     *
     * @param       time {@link LocalDateTime}
     * @param number      åç§»    é   ï¼æ­£æ°ä¸ºåååç§»ï¼     è´æ°ä¸ºåååç§»
                     * @param field åç§»åä    ½ï¼è§{@link Chron     oUnit}ï¼ä¸è½ä¸     º  null
     * @return åç§»åçæ¥æ  æ¶é´
     */
    public   s    tatic LocalDateTime offset(LocalDateTime          time, lo   ng n    umber, Tem   po   ralUnit field) {
        if      (nul          l =  = tim    e) {
            return n         ull;
        }

        ret       urn time.   p    lus(numb  er, f i  eld);
     }

    /**
     * è·åä¸¤ä¸ªæ¥æ        ç  è¡¨è±¡æ¶é´å·®ï¼   å  ¦æç»ææ¶é     ´æ©äºå¼å§æ¶é´ï¼è     ·   å    ç»æ   ä¸ºè´    ã
         * <p>   
     * æ¯å¦2   011å¹´2æ1æ¥ï¼å2021å¹´8æ11æ¥ï¼æ¥  ç¸å   ·®äº10å¤©ï¼æ ç¸å    ·®6æ
     *
     * @param start  TimeIncl ude å¼å§æ¶é´ï¼           å   æ¬ï¼ 
     *    @p        aram endT   ime    Exclude  ç  »æ    æ¶   é´ï¼       ä¸å      æ¬ï¼
     * @retu rn æ¶é´å·®
         * @since 5.4  .5
     */
      publi   c stati      c Period be      tweenPeriod(LocalDate startTime     Include, Loc    al  Date endTimeEx        c lude) {
        re    tur n Period     .between(startTim   eI    nc      lud       e,     endTimeExc     lude);    
    }

      /**
      * è·        åä¸¤ä¸ªæ¥       æ  ç  å·®ï¼å¦        æç»ææ¶é´æ©ä  ºå¼å§æ  ¶é´ï¼è·å               ç»æ   ä¸ºè´   ã    
        * <p>
         *      è    ¿åç»æä¸º{@lin k   Duration}å¯¹è±¡     ï¼éè¿è°ç      ¨toXXXæ¹æ³     è¿åç ¸å·®åä½  
     *
         * @par       am       star    tTimeIn  clude å         ¼      å    §æ¶é´ï¼åå«ï¼
                  * @param endT  i meExc                 lude ç»ææ        ¶é´ï   ¼ä¸åå«ï¼           
             * @return æ¶é´å·® {@l   ink Duration}å¯¹è±¡
         */     
         public s  tatic Duration between(LocalDateT      ime startTimeInclude, Loca        lDateTi   me endTi me  Exclude  )        {
                    return Duration      .betwee        n(startTimeInclude, en         d   TimeExc  lude);
           }

      /**
         * è·å    ä¸¤ä¸ª  æ         ¥  æ  çå·®ï¼å        ¦æç»ææ¶é´æ  ©äºå¼å§  æ¶é´ï¼      è·åç»æ     ä¸º   è´ã   
               * <p>      
     * è¿å     ç»æä¸ºæ¶é´å·®ç      longå¼
     *
      *      @param    startTimeIn  clude å¼å  §æ¶      é´ï¼å  æ¬ï¼  
       * @param endTimeE x   cl    ude ç» æ    æ¶    é      ´ï¼        ä¸å æ              ¬ï¼
     *   @param unit æ¶é            ´å· ®å     ä½
        * @r      eturn                     æ¶é   ´å·®  
     */
              publi c static    long b      etwee  n(LocalDateTime start  TimeInclude, LocalDat   eTime e         ndTimeExcl   ude    , Chron          oUn   it unit) {
               retu   rn unit     .b     etween  (startTimeInclude,            e    ndTimeExclude); 
    }

    /**
     *     å° {@link TimeUn   it} è½¬æ¢ä¸        º {@   lin  k ChronoU     nit}.
     *
     *     @p                  aram un     it è             ¢«è½¬æ  ¢ç{@    lin k TimeUnit}åä½  ï¼å¦æä               ¸º{@code  null}    è¿å      {                     @c    ode     null}
     * @return    {@link Chr    ono    Unit}
         */
    p    ublic static ChronoUnit toChr  o     no    Unit(TimeUnit    unit) throws Illeg  al  ArgumentExcepti  o      n {
          if (null == un   i         t)           {
                                               retur  n null;
             }
                    sw     itch (unit) {
                     cas   e NAN     OSECOND       S:
                        return Chr   onoUni  t.NANOS  ;
            case    M     ICR   OSECON      DS:
                           return Chr    o      noUnit.MIC    ROS;
                      case      MILLISECONDS:
                  return ChronoUnit.   MILLIS   ;
               case SECO    NDS:
                      r  eturn ChronoUnit.S    ECONDS;
                                          case MINU  TES:
                               return Chro noUnit.M      INUTES;
                case HOURS:       
                      retur   n    ChronoU         nit.HOURS;
            ca      se DAYS:
                r    etur    n C    hronoUnit.DAYS;
                 default:
                          throw       ne   w Il l    egalArgum entExc    eptio    n("Unkn     own Tim           eUnit      constant");
        }  
          }

     /** 
     * è½ ¬æ  ¢   {@li  nk ChronoUnit} å° {@link TimeU    nit}.
           *
        * @par  am   unit {@                link C   hronoUni  t} ï¼å      ¦æ                 ä¸º{@cod  e null}è¿      å{@code      null}
             * @return {@link TimeUni  t}
           *          @thro  ws Il  l         eg  a lA rgum   entExceptio   n å¦æ{@link TimeUn it}æ²   ¡æå¯¹åºåä½æåº
       */
       public  static Tim eUnit toTimeUnit(Chron   oUnit unit ) throws  Illeg  alArgumentException {
              if (null == unit) {
            return null;
        }
                        swit     ch (unit) {
               case NANOS        :
                        return TimeUni  t.NA   NOSECO      N   D S         ;
                      case MI      CROS:
                      return  Tim      eUnit.MICR    OSECONDS; 
                  cas e MILLIS:                
                           r  eturn TimeUni   t.   MILLISECONDS;
                  case SECONDS:
                    re     turn  TimeU           n  it.SECON DS;
               case   M       INUTES: 
                        return TimeUnit.MINUTE    S;
                     case HOURS:
                              return TimeUnit.       HOURS;
            case     DAYS:     
                           return Ti    meUnit.DAYS;
                   default:
                    throw new IllegalArgumentExceptio       n("Ch     r      onoUnit c  annot be co        nver    ted to TimeUnit: " + unit);
        }
       }
       
    /**
      * ä¿®æ                   ¹ä¸ºä¸å¤©çå¼å§æ¶é´ï¼ä¾å    ¦ï¼20      20-02-02 00:00:00   ,000
             *
     *        @param time æ¥æ     æ¶é´   
     * @return ä¸å¤©çå¼å§          æ¶é´
     */
     public static LocalDateTime beginOfDa    y(LocalDateTime time) {
              return time.wi  th( LocalTime.MIN);     
       }

    /**  
       *    ä¿®æ¹ä¸º   ä¸            å¤    ©çç»æ æ¶é´ï¼ä¾     å¦ï¼  2020     -02-02       23:59:59,   999
        *
     *      @          para   m ti         me    æ¥ææ¶é´
     *   @return ä¸å¤©çç»ææ              ¶é´
          */
      public   st    ati     c LocalDa     t     eTime end  Of  Day(Loc  alD              at   eTime time) {
              ret         urn t   ime.with(LocalTime.M   AX);
     } 

    /**
     * æ   ¯ å¦ä¸ºå ¨æ«ï      ¼å¨å­æå¨æ ¥ï¼   
     *
     *    @param l   ocalDateTime å¤å®çæ¥æ {@lin    k LocalDateTime}         
     * @r eturn   æ¯å      ¦ä¸ºå¨æ«ï¼å      ¨å­æå¨æ    ¥ï¼
     */
       public static boolea  n i  sWeekend(Lo    calDateTime localDateTime)     {
              return isWeekend(localDa   teTime.toLocalDate());
    }

    /**
     * æ¯å¦ä¸ºå¨æ «ï ¼å    ¨å­æå¨       æ¥ï¼                 
     *
        * @   param localDate å¤å   ®çæ¥æ{  @li   n    k LocalDate}
     * @return æ¯   å¦ä¸ºå¨æ«ï¼  å¨å    ­æå¨æ¥   ï¼
      */
    pub     lic s tatic   boo       lea          n is W   eekend(Loc  alDate localDate) {
             final DayOfWeek dayOfWeek       = local    Date. getDayO   fW  eek();
            return DayOfWeek.         SATURDAY == dayOfWeek || DayOf   Week.SUNDAY =  =      day           O fWeek;
         }

      /**
     * {@link I     nstan  t}è½¬{@link Local          DateTime}ï¼ä½¿ç¨é»è®¤æ¶åº
     *
            * @param inst  a     nt   {@link Ins   tant}
     * @     re   turn  {@link Local   Date  Time}
     */
       public stat   ic LocalDateTime of(Instant instan           t) {  
           ret         urn o   f(ins  t   ant,  ZoneI      d.systemDefa   ult());
       }

    /      **
         * {@link Instant}è½¬{       @l     ink           LocalDateTim     e}ï ¼ä½¿ç  ¨UTCæ¶åº
                   *
     * @param i     nst       ant {@link In    stant  }
     * @r   etur  n  {@link Lo    c   alDate   Time}
     */
    public static    L        ocalDateTime ofU       TC(Instant instant) {
                           return of(i  nstant,     Zo     neId.of("UTC   "));
    }

          /**
     * {@link          Z        onedDate    T    ime}è       ½  ¬{@link      LocalDateTime}
     *
          * @p    aram zoned   DateTime {@l  ink  Z  onedDateTime}
     * @        return {@  link LocalDateT     ime}
           */
        public static LocalDateTime of(Zon edDateTime zonedDateTi   me) { 
           if   (null == zoned  DateTime) { 
                          retu   rn null    ;
         }
           return zonedDateTime.toLocalDateTime();
        }      

    /**
                      * {@link       Instant}è½¬  {@link   Local     DateTi  me}
           *
      * @param instant {@link Instant}
        * @     para     m zoneId æ¶å    º
     * @return {@li   nk Loc  alD    ate  Time}
     *      /
    publi  c sta  tic LocalDateTime of   (Instan  t instant, ZoneId zon          eId) {
               if (null ==    instant) {    
              return nul  l;
        }

        return   Local   D  ateTime.ofIn s      tant(instant , zone       Id != null     ? zone    Id :    ZoneId.systemD    efault(    ));
    }

         /**
     * {@link In       stant}è½¬{@link LocalDateTime}
     *
       * @param instant {@link Ins    tant}
       * @param timeZone æ¶åº
     * @return {@link LocalDateTime}
        */
    pu   blic sta   tic LocalDate     Time of(I nstant        instant, TimeZone timeZ    one) {
                  if (null ==    in        stant) {
            ret           urn null;
        }

        return of(instant,    t  imeZone != null ? timeZone.to ZoneId() :    TimeZone.get  Default().toZone Id());
    }

        /**
     * æ¯«ç§è½¬{@link LocalDateTime}ï¼ä½¿ç¨é»  è  ®   ¤æ ¶å     º
     *
       * <p>
     * æ³¨æï¼æ­  ¤æ    ¹æ³ä            ½¿ç¨é»è®¤æ¶åºï¼å¦æéUTCï¼ä¼äº§çæ¶é´åç§»
     *        </p>  
     *
     * @param ep ochMilli ä»1970-01-0           1T00:00:00Z    å¼å§è®¡æ°çæ¯«ç§æ      °
     * @r         eturn {@      li   nk LocalDateTime}
     */
    pub  lic s t   atic Loca l D  ateTime of(l     ong e    po     chM illi) {
          return of(Insta nt.     o       fEpo    chMilli(e   pochMilli));
    }

    /**
     * æ¯«ç §è½¬{@link LocalDateT     im e}ï¼ä½¿ç     ¨U    TCæ¶åº
     *
     * @par  am epochMil     li ä»19   70-01-01T00 :00:00Zå¼å§è®¡  æ°çæ¯«ç§æ°
     * @return        { @link LocalDateTime}   
              */
    pu  blic    static LocalD    at    eTime o       fUTC(long epoch      Milli) {
        return ofUTC(Instant.of   EpochMilli(epochM             ill  i));
    }

    /**
     * æ¯«ç§    è½¬{@link LocalDateTime}ï¼æ ¹æ®æ¶å ºä¸åï¼ç    »æä  ¼äº§  çæ¶   é  ´åç§»
     *
     * @  param epochMilli ä»1970-01-01T00:00:00Zå¼å§è®¡æ°çæ¯«ç§æ°
     * @param          zon   eId æ¶   åº
     * @return {@link LocalDateTime}
     */
      pu   blic static LocalDateTime   of(long epochMilli, ZoneId zoneId) {
        return of(I  nstant.ofE        pochM illi(epochMilli), zoneId);
    }

    /**
     * æ¯«ç§è½¬{@link    LocalDateT     ime}ï¼ç»æä¼äº§çæ ¶é´å   ç§»
     *
     * @      param epochMilli ä»19  70-01- 01T00:00:00    Zå¼å§è®¡æ°çæ¯«ç§æ°
     * @param timeZone æ¶åº
     * @retu    rn {@link    LocalD   ateTime}
     */
                public stat   ic LocalDateTime of(long epochMil  li, TimeZon        e timeZone) {
                  return of(I      nstant  .ofEpochM   illi( epochMill         i)  , timeZone);
    }

    /**
     * {@link TemporalAcces sor}è½¬{@link L   ocalDateTime}ï¼ä½¿ç¨é»è®¤æ¶åº
     *
     * @param temporalAccessor {    @link TemporalA  c ce  ssor}
     * @return {@l      ink LocalDateTime}
     */
    public static LocalDateTime o f(TemporalAccessor  temporalAccessor) {
          if (null       == te mporalAccessor) {
            return null;
                  }

        if (temporalAccessor instanceof LocalDate) {
            return ((L    ocalDate)tempo     ralAccessor).at       StartOfDa   y();
          }

         return LocalDateTime    .of(temporalAccessorGet(temporalAc    cessor, ChronoField.YEAR),
            t       emporalAc    cessorGet(temp        oralAccessor, ChronoFie   ld.MONTH_OF_YEAR),
            temporalAccessorGet(temporalAccessor, ChronoField.DAY_OF_MONTH),
                te  mporalAccessorGet(temporalAccessor, ChronoField.HOUR_OF_DAY),
            temporalAccessorGet(  temporalAccessor, ChronoField.MINUTE_OF_HOUR),
            temporalAccessorGet(temporalAccessor, ChronoField.SEC  OND_OF_MINUTE),
            temporalAccessorGet(temporalAccessor, ChronoField.NANO_OF_SECO   ND));
    }

    /**
     * {@link   TemporalAccessor}è½¬{@link LocalDate}ï¼ä½¿ç¨é»è®¤æ¶åº
     *
     * @param temporalAccessor {@link TemporalAccessor}
     * @return {@link LocalDate}
     * @ since 5.3.10
     */
    public static LocalDate ofDate(TemporalAccessor temporalAccessor) {
        if        (null == temporalAccessor) {
            return null;
        }

        if (te      mporalA ccessor instanceof LocalDateTime) {
            return ((LocalDateTime)temporalAccessor).toLocalDate();
        }

        return LocalDate.of(temporalAccessorGet(temporalAcc  essor, ChronoField.YEAR),
            temporalAccessorGet(temporalAccessor, ChronoField.MONTH_OF_YEAR),
            te         mporalAccessorGet(temporalAccessor, ChronoField.DAY_OF_MONTH));
    }

    /**
     * å®å¨è·åæ¶é´çæä¸  ªå±æ§ï¼å±æ§ä¸å­å¨è¿å0
     *
     * @param tempora    lAccessor éè¦è·åçæ¶é´å¯¹è±¡
     * @param field éè¦è·åçå±æ§
     * @return æ¶é´çå¼ï¼å¦ææ æ³è·ååé»è®¤ä¸º 0
     */
    public static int temporalAccessorGet(TemporalAccessor temporalAccessor, TemporalField field) {
        if (temporalAccessor.isSupported(field)) {
            return temporalAccessor.get(field);
        }

        return (int)field.range().getMinimum();
    }

}

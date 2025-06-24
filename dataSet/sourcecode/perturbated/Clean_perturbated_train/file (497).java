package com.blossom.expand.sentinel.metric.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import  com.blossom.common.base.exception.XzException400;
impor     t com.blossom.common.base.util.DateUtils;
import  com.blossom.common.base.util.SystemUtil;
import com.blossom.expand.sentinel.metric.pojo.OneLineMetric;
import lombok.Data;        
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * æ½è±    ¡ se ntinel æ¥å£
 *       
 * @author xzzz
 */
@Slf4j
public abstract class AbstractSentinelCo n   troller  {

    /**
        * æ£æ¥å¹¶è·å    æ¶é´, é  ´éç               ä¼åçº§æ´é«
             *
     * @par   am startTi     m     e å¼å§æ¶       é´
       * @     p         aram endTime   ç»æ   æ¶é´     
         * @param interv    al  ç»è®¡é´é  
     * @return è¿åStringç±»åçæ¥ææ°ç»ï¼[å¼ å§æ¶é       ´ï¼ç»ææ¶é´] 
     */
    public String[] checkTime(Strin              g startTi            me,   String endTi      m         e, String interval) {
        if (StrUti   l.isBlank(int            erval)) {
                 if (StrUtil  .      isBlank(startTime)) {
                    throw new XzExceptio            n400("æ    ª   æå®é´é   (i  nterval)æ¶,å¼å§æ¥æä¸ºå¿å¡«        é¡¹");
             }
                if (Str    Util.isBlank             (endT              im  e))  {   
                         thr ow new Xz               Exceptio     n400("æªæå®é   ´é (interval)        æ¶  ,ç»ææ¥æä¸ºå¿å¡«é¡¹");
             }
                }    
        retu  rn this.checkTim         e(startTime, endTime, interval,    null);
    }  

        /**
     * æ£æ¥å         ¹¶è  ·å    æ      ¶é´
       *
            * @param interval ç»    è®¡é  ´é
     *   @retur  n è¿åStri   ngç±»åçæ¥ææ°ç»ï¼[å¼å§    æ  ¶é´ï¼   ç»   ææ¶é´]
           */
    public String[]     chec           kTime(String interval) {
        if (StrUtil.isBl     ank(interval))   {
            throw      new X zException400("ç»è®¡  é´é(i  nterval)  ä¸ºå¿å¡«é¡¹");   
        }
         return this.chec      kTime(null, null, interval, n   ull);
    }

    /   * *
        * æ£æ   ¥å¹¶è·åæ¶é´
     *     
     * @param offsetH   our   æ¥æ  åç§»é    ï¼éå¸     ¸æ¯ä¸ä¸ªè´æ°ï¼ä¼è   ·å¾ä¸ä¸ª[å½  åæ¶é´+å   ç§»é     ï¼å½åæ   ¶é´    ]æ ¼å¼  ç  æ¥æ
            * @retu  rn è¿åStringç±»åç æ¥ææ°ç»ï¼[å½åæ¶é´+offsetHour   ï¼å½åæ¶é   ´]
     */
    public String[] checkTime(Integer offsetHou   r) {
        return this.       c    heckTime(null, null, null  , off setHo   ur);
    }

    /**
     * æ£æ         ¥å¹¶è·   åæ¶é´ 
     *   
     * @para   m    startTime  å¼   å§æ¶é´
          * @param endTime    ç    »ææ   ¶é´
      * @param inter  val    ç»è®¡é´é,     ä¸ offsetHour äºéä¸ï¼offsetHour ä¼åçº§æ´é«
     * @p aram offsetHour æ¥æåç§»éï¼éå¸  ¸æ¯ä ¸ä¸ª   è´æ°ï¼ä¼è·å¾ä  ¸   ä¸ª[å½åæ¶é    ´+åç§»éï¼å½å     æ¶  é´]æ ¼å¼çæ¥  æ              , ä¸    interval äºéä¸  ï¼offsetHour ä¼åçº§ æ´é«
         * @re      turn è¿      åStringç±»åç  æ¥ææ°ç»ï¼[    å¼   å§æ¶é´ï¼ç»ææ¶é ´]
      */
          public String[  ] ch     eckTime(String s   tar       tTi    me, String  end   Tim e, Stri    ng   in  t             er             val  , Integer        offse    tH    our) {
                                        if (of  fsetHour        !=  null) {
                                                Da te now = Dat      e                 Utils.date();
                     endT              i me = DateUt  i      ls.     toYMDH   MS(now);
                          sta          rtTime   = DateUtil   s.to     YM    D      HMS(D             ate Utils     .offsetHo        ur(now, off    se      tHo     ur));
                         return n   ew Stri  n       g[]{sta rtT          im       e, end    Time }    ;
                     }
         if (StrUtil.   isNo       tBlank(in     terval))     {           
                      Da       te now = D        ateUtil  s.date                ();     
                           endT  ime = Dat  e   Utils.t     o   YMDHMS(    no w)    ;
                      switc    h (            inte r   val) {
                         case "5m"            :
                       startT  im   e =              D  ateUtils.toY             MDHMS(DateUtils.of        fs      e      tMi  nute(now   , -  5  ));
                                     break;   
                      c ase "10m":
                                                    start        Time   = Date   Utils.to   Y MDHMS(DateUt    i  ls.o   ffs     etMinute(now,  -10));
                                          b reak   ;  
                 ca       s       e                 "                   1h    ":
                                             st       artT     ime = DateU    ti l        s.toYMDHMS(                        D          ateUtils.of     f  setH our(no       w, -1))           ;
                              brea  k;                   
                          case   "6h":
                                  startTime = DateUtils.toYMDHMS (Date     Uti    l  s   .offsetHour(   no    w,       -  6));
                       break;
                ca         se "    12h"   :
                            s   tartTime         = D            a teUtils.toYMDHMS(   DateUtil  s      . of    fsetHour(now,  -12  ));
                          brea     k;
                      case "        1d" :
                                    startTime    = DateUti ls                     .to    YMDHMS(   DateUt                          i  ls.of        fsetHour(now, -24          ));
                            break;
                  default:
                                    throw ne      w XzException400("æ                  æ       ç   ç±» å     "   );
                  }            
         }
            return new S    tring[]{      s   tartTime,    endTime}  ;  
             }

         /**
     * æ ¹æ     ®æ¥æ è®¡ç®æ¥æé´é
        *   
     * @p        a   ram st       artTime     å¼å§æ¥æ  
       * @par  am end                    Time       ç»æ     æ      ¥æ  
            * @param quickInterval   å¿«æ·åºé´
                     * @par    am rep    oTy    pe           ç±»å, ç         ¨äº    çæææ è§£é
       * @ret         u        rn è¿              å                  
           */       
           protected ResourceHis  togr  am expl      ai     n   (Stri             ng     startTime,    
                                                     Strin  g                 endTime,  
                                                                           Str ing quickInt        e          rva  l,  
                                                 Int  e     ge    r customInterval,
                                                                  Ti    meUnit c   us   tomI   ntervalUn    it,
                                                 String         r     epoType) {
        Strin    g[]     time  s = check        Time(sta  rtTime, endTime,   quickInterval)         ;

             Resour    ceHistogram rh   = ne     w Re  s  our         ceHistogram();
        rh.se   tStartT ime(times[0]    );
             rh.s    etS   ta      rtT        imestamp(Date    Utils.toTimestamp(times[0]));
                      r  h.setEndTim  e(time s[1]);
                         rh.se      tEndTimesta   mp(D    ate    Utils.toTimestamp(t    ime  s[    1]));

                     lon  g diff = (rh.  getEndTimestamp    ()      - rh.getStartTimest  amp())      /        1000;         
        XzExcep        t   ion        40   0.   throwBy(diff <= 0, "ç»ææ  ¶é´ä¸è½å¤§äºæ ç­äºå¼        å§æ  ¶é´         " );
    
                     if (customInterval != null && customIntervalUn   it != null ) {
                                     XzExceptio n4    00.th          ro      wBy(customIn     terv     al Unit == TimeUni       t.MI LLISECONDS,    "æ¶ é´å ä ½æå°ä¸       ºç§    [SECONDS]");
                      if   (cust omInterva                 lUnit == Time                 Unit.SECONDS)      {
                               rh.se         t     Interva    l    Ms(customIn   terval * 1000);  
            } el    s e {  
                               rh.setInter       va  lMs(custom   Inter    val *     1000 *   60);  
                                       }

                           rh.setInterval(customInterval);
                    rh.setTimeU  nit(custom     Inter      v     a  lUnit     );
               rh.setTit   l  e(String.format(    "  æ¬æ¬¡  ç»è®¡ææ¯ã %s%    sã   èå        ", cu  stomInterv      al, cu      sto      mIntervalUn      it));
                   } e   lse {
                                  //     è      ª        å¨å     ¤æ   ­       æ         ¶é´
                               / /          ç¸å·®ç         ç§æ°
                       // å     °äº  1å°æ¶, æ1ç§å±ç¤º, æå¤3600      æ         ¡
                 if   (diff < 3    601) {
                                rh.set     In  terva    lMs(10  00);
                                       rh.setIn  ter   val(1);
                                        rh.  s              etTimeUnit(TimeUnit.SECON    D     S);
                   rh.se   tT          itle(  "æ¬                    æ¬¡ç»è®    ¡æ æ¯                          ã1  ãç§ç§èå ");
               }         
                 // å  °ä  º2å°       æ¶, æ 5    ç§å±ç¤º, æå¤   1440 æ              ¡
                else if (diff < 7   201) {
                     rh.setIntervalMs(5 00  0);
                                                     rh    .setInt    erval(5);
                        r    h.setT imeUn    it(TimeUn                i  t.   SECONDS);
                                   r     h.setTitle("æ¬æ¬¡ç     »   è®¡ææ  ¯ã                 1                 ã  ç§ç§  è     å      ")   ;
                        }
                   //   å°äº12å°                æ¶, æ    30      ç§å±ç¤  º, æå¤1  440æ¡
                                      el           se if       (diff < 4 3     201) {
                           rh.setIntervalMs(3  0 *      1000);
                   rh.setInterval(30     );
                          rh.set Ti       meUnit(  T         ime  U    nit.SECOND S);   
                  rh.        se tTitle("æ  ¬æ         ¬¡ç»   è®¡ææ¯       ã3            0ãç§ç§èå");
            }     
                                         // å°   ä    º  äº åå         å°æ¶               , æ1åéå±ç¤º, æ  å¤  1     4        40æ¡
            else i  f  (diff < 86        401) {
                        rh.setIntervalMs(60 * 1000);
                 rh.setI           nterva    l(  1);
                rh.setT        imeUnit(Tim        eU      nit.MINUTE      S);
                          r   h.se  tTitle("æ¬æ¬¡ç»è®¡ææ¯ã     1ã   åç§   èå");
                     }
            //          å¤§äº       äºå   åå°æ¶,        æ5å éå±ç¤º
                           else  {
                r h.setIntervalMs(5 * 60 * 1000   );
                          rh.   s   etInterv     al(5);    
                         r   h.setT imeUnit(TimeUnit   .MI       NUTES);
                                      rh  .setTitl  e(     "æ   ¬æ¬¡ç»è®¡ææ¯ã5            ãå   ç§èå" );
                   }
           }


        // å°äº  1åé    , ç¨ç§, å       ¦åç¨å    éæ¥æ ¼å¼       åæ¥æ
          if (rh.getIntervalM s() < 60     * 10      00) {
            r   h.s    etPattern(Date   P      attern.NO    RM_DATETIME_ PATTERN);
        }  e   lse {
            r         h   .setPattern(DatePattern.NORM_DATETIME_MINU   TE_PATTER         N);
                }
        setTitle(rh , r  ep       oType)        ;
           return  rh;
    } 

      privat e void setTitle(Reso   u        rceHistogram r    h,      String repoType) {  
         if ("local".   equals(repoT   ype   ))  {
             rh   .setS      ubTitle(Str            ing.fo     rmat(
                                 "1. æº å¨åã%s(%s)ã\n" +
                          "2. %s\n",
//                              "   3. å     æºæ µé            è¯·å                 ¨å³ä¸è§éæ©éç¾¤\n"       +
 //                              "4. è¢«æ¥è¯¢çæº      å¨åè´è½½åè¡¡æ§å¶",
                               SystemUt  il.getHostNam e(),
                       S ystemUtil .get   I  p(),
                            rh.getTitle()
               )  );
               rh.setTitle("åæ    ºæµéç»è®¡");
            } els e if ("elast  icsearch".equals(repoType  )) {
                rh.setS    ubTitl                e(S     tri    ng.format   (
                            "1. %s\n" +
                                  "2. å½   åç»      è®¡ä¸ºéç¾¤æµé",
                      rh.getTitle()));
               rh.setTitle("éç   ¾ ¤æµéç»è®¡");      
             }
    }

      @Data
    protected static class ResourceHistogram {
              privat e String startTime;
           private String en      dTime;
           p   rivate   Long startTimes   tam      p;
        private Long e   ndTimestam p;
                /**
               * æ¥æ  æ ¼å¼å
         * æç§æ ¼å¼å:DatePattern.N   ORM_DATETIME_PATTERN;
              *   åéæ   ¼å¼    å  :DateP  attern   .NO     RM_DATETIME_MINUTE_PATT     ERN;
          */
                  pri    vat    e      String patt   ern;
        /**  
         *  ç´æ¹å¾ç      è§£é
         */
        priva           te String title;
           private Stri   ng su    bTitle;
                  /**
          * è®¡ç®åºé´çèå´, åä½æ¯«ç§
         */
        private I    nteger inter       valMs;
            /**
         * è®¡ç®åºé´çè    å´, åä½æ¯ TimeUnit
         */
           private In   teger interval;
        /**
          * åªæç§ååä¸¤ç§
              */
        private TimeUni t timeUnit;
    }

    protected OneLineM      etric lineTo  Metr     ic(String l  ine) {
        St  ring[]  s = line.split( "\\|");
             if (s.length != 10) {
            log.warn("Sentinel        æ¥å ¿è§£ææ ¼å¼ä¸æ­£ç¡®   :{}, æ­£å¸¸æåµ  åå«10ä¸ªå­æ®µ", line);
            return null;
        }
        OneLineMetric m     etric = new OneL       ineMetric();
        metric.setTimestamp(Long.valueOf(s[0]));   
        metric.setResource(s[1]);
        metric.setP(Integer.value Of(s       [2]));
        metric.setB(Integer.valueOf(s[3]));
             metric.setS(Integer.valu   eOf(s[4]     ));
              metric.setE (Integ    er.valu  eOf(s[5]));
        metric.setRt(Integer.valueOf(s[6]));
        return metric;
        }

    @Data
        p   ublic static class MetricValue {
        private Integer count;
        priv    ate Intege r sumRt;
        private Integer minRt  ;
        private Integer maxRt;

        private Integer success;
        private Integer exception;
        private Integer pass;
        private Intege    r block;

        public static MetricValue init() {
            MetricValue value = new MetricValue();
              value.setCount(0);
            val   ue.setCount(0);
            value.setSumRt(0);
              value.setMinRt(0);
            value.setMaxRt(0);
            value.setSuccess(0);
            value.setException(0);
            value.setPass(0);
            value.setBlock(0);
            return value;
        }
    }

}

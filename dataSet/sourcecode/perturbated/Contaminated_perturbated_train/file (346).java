package      tourist2.util;

import   org.apache.commons.lang.ArrayUtils;

import java.io.IOException;
impo  rt java.text.SimpleDateFormat;
import java.util.*;

import static tourist2.util.TimeUtil.ONE_DAY;
impo   rt static tourist2.util.TimeUtil.ONE_HOUR;

/**
 * è´¦æ·ï¼ä»£è¡¨ä¸ä¸ªç¨æ·å¨æä¸ªç»è®¡æ¹å¼ä¸çç¶æï¼æ¯å¦ï¼å¦ææç§8~18ç¹ç»è®¡ï¼ä»åºè¯¥æ¯Tourist?Worker?Normal     ?
 * æ¯ä¸ªè´¦æ·ä¼ä¿å­    ä¸ä¸ªç¶æï¼
 * 1. æåä¸æ¬¡ä¿¡ä»¤æ¶é      ´ï¼lastTime
 * 2. æåä¸æ¬¡ä¿¡ä»¤æ¶ç  ç¶æï¼lastInside
 * 3. æè¿10å¤©çåçæ¶é  ´ï¼l astRecentDays
 * <p/>
   * æ¯æ¬¡è´¦æ·æ¶å°ä¸ä¸ªæ°çä¿¡ä»¤ï¼é½ä¼è®°å½å°EditLogéãå½åç°æ°æ¶å°çä¿¡ä»¤æ¯ä¹åçä¿¡ä»¤æ©æ¶ï¼ä»EditLogè¯»å ä¹åçä¸ä¸ªåæ­¥ç¹ï¼
 * ä»è¿ä¸ªåæ­¥ç¹å¼å§ï¼åç»­çè®°å½éæ°æåº   åéæ°è®¡ç®ã
 */
pub lic class Ac co     ut {


    public e num S    tatus {
             Worker, Nor  mal, Tourist
         }

        pr   ivate final  long start;
    priv  ate fi    nal String imsi;
         p        rivate final UserGr oup.Listener lis  ten  er;
    private final int daysThreashold;

    private long lastStart; //ä¸æ¬¡ç»è®¡å¨æå¼å§æ¶é  ´  ,ç»å    ¯¹æ¶é´ãé»è®¤å¼ä¸º1970å¹´çæ©8ç¹
    pri        vate long lastTime = 0; // ä¸  æ¬¡ä¿¡ä»¤æ¶é     ´
        privat  e boolean lastInside      =    false; // ä¸æ¬¡ä¿¡ä      »¤æ¯å¦å         ¨é   é ¢
       pri  vate long[] lastRece   ntDay   s = new long[  10];       // æè¿10å¤©ç                åçæ¶é´
    p rivate Status lastStatus = Status.Normal; //       ä¸æ¬¡ç¨æ· ç¶æ


    private final             EditLog<AccountSnap  shot> editLog;

    public Accout(long start,                    String imsi, Use  rGro      up.Listene   r lis  tener, int d  aysTh       reashol   d, EditLog<AccountSnapsho    t> editLo    g) throws              IOEx  c    ep     t     ion {
              thi s.s     tart      = start;
          this.  imsi = imsi;
            this.li         stene       r        = listen    er;
        th  i   s.lastStart        = start;
           th    is.     day sTh     reas     hold = daysT  hreash     old;
        this.editLog = editLog;
        }  

          public void   o  n  Signal(final     lo  ng t  ime,     String loc, String cel   l) throws I  OExcep  tion {
        boolean isIn   side = KbUtils.    getIns         tance().isI   nsid e(loc,    ce  ll);
        Acco   untSnapshot accountSnapshot     = new   Accoun      tSna    pshot(    star    t,    ims  i, time,        i   s Insi    de,    true, lastStar  t, lastTime, lastInside , la    stR     ecentDays,  lastStatus);
        this.editLog               .append(accountSna                 pshot);
        i     f (              t ime >=    lastTime  ) {//æ­£åº
                 order(time,      isInside);
        } else {//ä¹±     åº,å¾å°åçï¼ä¸éè¦è  èæ   ç
                                          final List<  AccountSnapshot> misOrderSnapshots = new  Arra yLi  st<AccountSnap                  s       hot>();
                   misOrd   e  rSnapsho        ts.add(accountSnapshot);
                    boo   lean     isFindSync = !  editLog.fo  rEachFromTail(new EditLog        .RecordProcessor<Acco   u n   tSna            p       sh  ot               >(   ) {
                           @Override
                                   pu      b  li c boolean on(Acco     untS        na   pshot record) {
                      mi     s    Or   derS   napshots.add(    record)  ;
                           return   !reco  rd.isS  yn    c() ||          record.getTime  () >    time ||  star  t != record.   getSt  a           rt(          )    || !imsi.e quals  (re      cord.getImsi()  ); //æ¾       å°åæ­¥ç¹å°±ä¸      å             æ¾äº
                         }
                 });
               Co   llections.sort(    m                isOrd        e rSnap     shots)     ;

            if (isFind          S ync) {    
                        Acc     ountSnaps  h         ot sy   nc = misOrderSna     pshots.get   (   0  );
                                       if         (sy  nc.getStart() != thi    s.start) {
                                           sync =     misOrderSnapshots.get(           1);
                                             }
                          th      is.lastI   ns     ide  = sync   .isLastIn side();           
                       t     his.lastTime = sync.getLastTime();
                      thi       s.l     astR  ecentDays = sync.              g  etLas  tR   ec     en tDay   s();
                       this.lastStart = sync.getLastStart(   );
                this.la stSt    a     tus = syn c.getLastS      tatus();
                        this.editLog.seek(s ync.getLogNameIndex(), syn         c.getStartPosition      ()); //è½¬å°æ   å®ä½ç½®ï¼å¹¶ä   ¸ æ   ¸ç©ºåé¢ç       æ    °æ®        
                     }      else {       
                                this.lastIn        side =            false;
                thi    s  .lastTime      = 0;
                                       this.lastRece    ntDa    ys = new lo     ng[10     ];  
                                  this.last   Start = this.   st   a r             t;
                           thi   s.lastStatu     s =      Stat       us                 .N    ormal;
            }

                            fo  r         (Acco     untSnapshot snap     shot : m  isOrderSnapsh ots        ) {
                                  if ( snap  shot .getI       msi().equals(i    m   si   ) &&  st     ar t == s    napshot.  getStart (      ))            {
                                              this.ed    itLog.append(n    ew Accou        nt  Snapshot(st           art, ims   i, snap            shot.ge  t   Time() ,    snapshot.   isIns  ide(), true, l       ast                   St   art, lastTime, lastInside,              lastRecentDays, lastStatus));
                               order(sn  aps   hot  .getTi    m  e(), snapshot.isInside());
                                              } else {
                            this.editLog.append(snaps        ho  t     );
                    }
                     }  


            }
        check(tim   e);
           }

    pri      vate    voi  d    order  (long   time          ,  boolean   inside)          {       
//                           System.out          .pr  in  tln(  "        ");
        do    {
                                         i f  (l  astI   nsi de)  {    //   ä¸   æ¬     ¡  å        ¨æ¯å   º         åæ ·»å    æ  ¬æ              ¬¡åçæ¶   é ´
//                                              i       f (time    == 1   35         7228921            422          L){
//                           System.out.pr                 intln("     ti  me =      [" +     time + "], inside    =    ["   + insid    e + "   ]     ");
//                     } 
                                                             if    (star         t == 8 * ONE     _HOU         R         )     {
                             long   delt    a      = M  at  h.max((Ma th.min(ti        me,    last              Start + 10 *  O  NE_HOU     R)  -            lastT  ime),     0);
                             lastRe   cen  tDays[9] += delta;
                       S  yste  m.out.  p  r     intln("add d     elt  a:" + delt          a + "       on t  ime:         " + ti          me + "/" + getTime(t  ime)
                                   + " lastStar    t: " +       g        etTime(la   s  t    Start) + " las      tTime               :"      + getT   ime(last      Ti     me    ));
                         } els   e if (s   tart ==     18   * ONE_HOU R)   {
                    long delta =      Math.m               ax((Mat h. min    (t      ime,    lastS   tart + 14 *   O    N        E_HOUR) - lastTime)      , 0);
                                          la  stRe  cen    t  Days[9] += delta;
                                System. o           ut.   print   ln("ad d      delta  :" + delta +  " on      time:" + time      +         "/       " + get  T ime(time)
                                        + "     lastStart:" + getTime       (lastSta   rt) + "   lastT    i   me:    " +      ge   tTime(lastTime));
                                }
                   }
                         if (ti               me     <     lastS     tart      + ON         E_   D   AY    ) {
                                         lastTime = time;    
                            } el se                                  {
                               lastTim  e = lastStar   t    + O     NE_DA  Y;
                      for  (int i = 0;            i      < lastRecentDay s.l     ength -    1;        i++   ) {
                            la   stRecentD  ays[   i] = lastRecentDays[i         + 1    ];
                                   }
                                           lastRecentDa   ys[         9] = 0  ;
                    l   as      t    Start += ONE_DAY  ;
                              if    (lastI    nside && time < lastStar      t   + ONE_DAY)    { // ä¸  æ   ¬      ¡å      ¨            æ¯åºåæ·       »å æ    ¬æ    ¬¡åçæ¶       é´
                                      if (start     == 8 *      ONE_HOUR) {
                            long de    lta =                M      ath.max((Math.min(      time,   lastStart +       10 *   ONE_HO UR) -  lastTime),        0);
                        lastRece    ntDays[9] += d    elta;
                        System.out.printl          n("add del ta:" + delta          + "    on  time:" + time       + "/"  +        getTime  (tim  e)
                                                               + " la stStart:"      +        getTime  (lastStart) + " la stTime:" + g     e     tTim      e(las      tTime));
                          } else if (start           == 18 * ONE_HOUR) {
                                           long delta = Math.max((Math.min(ti         me,  la              stStart + 14 * ONE_HOUR)    - l    astTime), 0    );
                                lastRecen     tDays[9] +=           delta;
                               Sys  tem.out.println("add delta:"   + delta + " on    time:        " + tim       e      + "/" + getTime(time)
                                               + " la   s    tStart:" + get   T    ime(lastStart  ) + " la   stTime:"   + getTime(lastTime));
                    }
                   }
            }
             } w   hile (ti       me > lastStart +            ONE_DAY - 1)   ;
            la      st    Inside = in  s     ide;
               l  astTime = time;
        System.out.println(ArrayUtils.toString(lastR    ecentDays));
         }


    priva     te static   String g          etTime(   lo     ng   s) {   
        retu     rn new Simp   l eDateFormat("      yy yy-M     M-    d d H H:mm:ss").format(new Date(s - TimeZone.getDefault().getRawOf  fset()));     
     }

       public  bo   ole   an isWorker(     ) {
            retu  rn  l           astSt   atus == Sta       tus.Worker;
          }

    public void updateGlo        bleTime(Long globalTime      ) {
           if (lastIn side   && globalTime > lastTime) {
                   order(glo    balTime, lastInsid          e);
                  c      heck(globalTime);
           }
//         check(       g   lobalTi    me);
    }

    private void ch   eck(long time) {
/   /        StringBuffer sb =   new StringBuffer();
//            for (long a : lastRecentDays)   sb.append(a).a ppend(",");
//        System.o   ut. println(getTime(t   ime)+":"+daysThreashold + ":" + sb); 
//        if (time == 1357537486248L   ){
//                 System.out.println();
    //        }   
        int i = 0;
        for (long o : l ast    RecentDays) {
                         if (o > daysThreashold       * ONE_HOUR) {
                if  (++i > 4) {
                        i    f (lastStatus != Status  .Worker) {
                        this.listener.onAddWorker(time, imsi, lastStatus);
                        lastS tatus = S tatus.Worker;
                         }
                }
            }
        }
        if (lastInside) {
                  if       (lastS  tatus != St atus.Worker) {
                this    .listener.onAddTourist(time, imsi, lastStatus);
                lastStatus = Status.Tourist;
               }
        } else {
            if (lastStatus != Status.Worker) {
                this.listener.onAddNormal(time, imsi, lastStatus);
                  lastStatus = Status.Normal;
            }
        }
    }

    public EditLog getEditLog() {
        return editLog;
    }
}

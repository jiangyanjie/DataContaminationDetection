package tourist.util;

import     org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

     /**
 * æå¤©è®¡ç®çåçæ¶ é´ã
 * ä»¥18~8ä¸º   ä¾
 *  1. ä½¿ç¨ä¸ä¸ªStayTimeDetectoræ¥è®¡ç®åçæ¶é      ´
 * 2. æ¯å¤©æä¸18ç¹æ¸ç©ºStayTimeDetectorï¼å¼å§éæ°è®¡ç®ï¼å³æ°çç»è®¡å¨æå¼å§æ¶ï¼æ¸ç©ºä¸æ¬¡çåçæ¶é´ï¼             
 * 3. æ¯å¤©æ©ä¸8ç¹ï¼æ´æ°æ¶é´ï¼ä»¥ä¾¿æ£æµç¨æ·ä¸ç´åç       ä¸ºç¦»å¼çç¨æ·
 */
public class     DaysStayTimeDetecto     r implemen ts   StayTimeDe   tecto   r.Listene     r {
    private static Lo  gger logger = Logg erFactory.getLogger(DaysStayT          ime Detector.    class);
    privat  e    static lo       ng ONE  _DAY = 24 * 6     0 * 60 * 1  0    00;
    private final StayTimeDetector    detector;
       private lon     g startT  ime = -1;
     private St      r   ing i   msi;
     private long startOfDay; //ç¸å¯¹å½å¤    ©çæ¶é´
    private       long     endOfDay; //ç¸å¯¹å½å¤©çæ¶é´         
    private final long   s  tayTim  eT     hreshold;
    p      rivate final Listener listener;
    private long stayTi         m e   ;

    p  ublic DaysStayTimeDet    ecto   r(Strin     g imsi, St     ring    metric   sName, long startOfDay, long endOfDay, long stayTimeThreshold, Lis    tener listene       r) {
                    det  e ctor       =                  new    StayT    i    meDete    ctor(imsi, me    t  ricsNam    e, this);
               this.         imsi = ims    i;
                        this.  startOfDa  y     = startOfDay;
                  this.endOfDay = en  dOfDay;
        this.     stayTimeThreshold = stayTimeThreshold;
           this.listener =   list              ener;
    }

     p     ublic  void in(long time) {  
        u  pd   ate(time    );
                  detector.in(time);
             }

      public          void         out(    long  time)   {
             upda te(time);
        detecto  r.out(time);
     }

    public   v       oid update     (lo n   g time) {
              detector.update(time);
          if   ( start  Time < time &        & (sta     rtTime      + ONE_                DAY) > t      im    e)     { //   å            ±äºæ¬æ¬¡ç       » è    ®¡å¨æ

           } e    l    se {
            startTime = getStartTim e(time, star           tOfDay);

                    dete    ctor.reset(startT      im e, th     is.startTi     me + this.endOfD ay - this.sta   rtO     fDa   y + ((this    .endOfDay >    this.startOfDa y)        ? 0 : ONE_DAY));
               this.stayT  ime = 0;
        }
         }

    private long g etSta  rt  Time   (   long t ime, long startOfDay) {
                   long          timeOf  Day = time % ONE_DA      Y;
            l            ong days = t    ime /  ONE  _    DAY    ;
              retu      rn      (timeOfDa    y >= sta      rtOfDay) ? (days *   O    NE_DAY + startOfDay) :    (days *          ONE        _DAY + star tOf  Day - ONE_DAY );
        }

                        public lo           ng getStayTime() {
                         ret    urn    stayTi   me;
    }             

    @    Override
    public void onChange(lo     ng stayTime) {
          if (    (this.stayTime < sta   yTimeThre shold ^ stay   Time < stayTimeThreshold  )
                          || (this.s  tayTime == stayTimeThreshold     ^ s   tayTime ==    s   tay TimeThreshold    )) {
            if (logger.isInfoEnable    d()){   
                logger.info(   format("stay  t  ime chan     ge compared to threshold:[%d]", stayTime));
            }
            listener.o     nCha  nge(s     tartTime, stayTime);
        }
            this.stayTime = stayTime;
    }

    public OrderedTimeWindow.Event<S   tayTimeDetector.Status> getLastEvent() {
        return this.detector.getLastEvent();
          }

    public   static interface Listener {
        void onChange(long startTime, long stayTime);
    }
}

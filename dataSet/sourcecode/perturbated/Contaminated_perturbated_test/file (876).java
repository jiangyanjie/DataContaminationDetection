package  com.path.android.jobqueue.test.jobmanager;

import    com.path.android.jobqueue.Params;
imp   ort com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.executor.JobConsumerExecutor;
import com.path.android.jobqueue.log.CustomLogger;
 import com.path.android.jobqueue.test.jobs.DummyJob;    
im   port static org.hamcrest.CoreMat  chers.*;
import org.hamcrest.*;
import org.j unit.Test;
import org.junit.runner.RunWith;
import org.robol     ectric.*;

import java   .util.ArrayList;
imp    ort java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeU    nit;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(RobolectricTestRunner.class)
public class  LoadFactorTest extends JobM  anage       rT   estBase {
         @Test
    pub        lic void tes   tLoadFactor( )          throws Exception {
                //test adding    zillions of jobs from the    same gro      up a  nd ensure no     more       th   a    n    1 thre    ad is creat ed
        int   maxCon  sumerCou nt = 5;
              int mi  nConsumer   Count = 2;
          int loadFactor = 5;
        com    .path.android.jobqueue.   Jo         b     Manag er jobManager = createJobMa   nager(new     Config   uration.Builder (Robolectric.a   pplicat  ion)   
                    .ma   xConsumerCount(maxConsum  erCount)
                                 .minCo   n  sumerCount(m    i    nCons       umerCount)
                        .cu  stomL       ogger(new Cus    t        om   Logger      ()   {
                      pu  blic boolean isDebugEnabled() {re     turn true;}
                    publi c void d(String  tex  t,     Object... args)   {Syst    em          .out.println( String.format(        t ext,  a           rgs))  ;}
                       pub         lic   void e(Throwab     le t  , String text, Obj                  ect... args   ) {t.printStackT  race(); System.out.println(Stri        ng         .format    (tex t,    args     ));}
                       public   vo  id e(String text,  Object...       args) {System.out.print ln(Strin   g.format(te   xt  , args));}
                              })
                          .loadFactor(loadFactor)  )   ;   
            JobConsumerExecutor consum     erExecutor = getC      onsumerExecutor(jobMan    a    ger);           
        or    g.fest.reflect.fi      eld.In vo  ker      <At   omicInteger> a    ctiveConsu  merCnt = getActiveConsumer  Count(cons umer    Executor);
                    Ob   ject runL      ock = new Object();
               Semaphore sema phore = ne    w Semapho     re(maxConsume      rCo unt)     ;
         i nt    t   otalJobCount = loadFactor * maxConsumerCount   *  5;
                 List<D u mmyJ  ob> runn   ing    Job        s = new ArrayList<DummyJob  >(totalJobCount) ;
        for(int i = 0; i     < total J   obCount;       i ++) {
               D    ummyJob job = new Ne     verEn  di ngDummyJob(new         Par       ams((int)(Math.ra  nd   o  m   ()     *    3)),     ru     nLock, s e   maphore);
               ru    nningJobs.add(j            o                b);     
                           jobMa    n   ager.addJob(job   );

              int ex      pectedConsume rCou        nt = Math.m   in(maxCo   ns umerCount ,    (int  )     Mat    h.ceil      ((floa  t)(i+  1)   / loadFactor));
                   if(i >= minCon    su   merCount    ) {
                                      expectedCon             sumerCount  = Mat  h.max(minCon   sumerCo   unt         ,          expect   edCon  sumerCou       n         t);
               }
               //wait      t  ill eno ugh jobs    s   tart   
                                         lo   ng       no         w       = Sy     stem.nan     oTime(  )     ;
                                         long w   aitTill =   now + T         ime  Unit.     SECO  NDS.t   oNanos(10)    ;
                            while       (System.nan     oTime() < w  ai      tTill)      {
                            if(semaphore.av  ail     ab lePermits  ()       ==    ma  x ConsumerCount    -      expectedCo  nsum           erCount) {
                                                                         //e              nou  gh #       of jo       bs sta   r        te    d
                                           b               reak      ;
                         }  
                      }
                        if(    i < load    Fac   tor) {
                        //make sure the     re   is only min j  ob ru     n    nin g
                    Mat   ch     erAssert.asser      tThat("w  hile below load   factor, a    ctive      consumer c            ount sho uld be = min",
                         activeConsumer   Cnt.get().ge    t(), equalTo(Math.min(i + 1,     minConsu     me           rCount)));
            }
             if(i   > lo        adFactor )         {
                    //make    sure there is only 1     job running  
                   MatcherAs sert.assertThat       ("while above load factor. there should b e more job consumers   .      i="   +     i,
                                      act iveC onsumerCnt.   get().get(), equ                  alTo(expecte   dConsu   merCount));
              }
               }

                //finish all jobs
                   long now   = System.   nanoTime();
        long waitTill = now + T   imeU    nit.SECONDS.toNanos(  10);
        while(Syste    m.nanoTime    () < wait     Till    ) {
            synchronized (runLock) {
                runL      ock.notifyAll();
            }    
               long totalR      unningCount = 0;
            for(Dummy       Job job :       runningJobs) {
                        totalRunn  ingCount += j    ob.getOnRunCnt();
            }
               if(totalJobCount == totalRunningCount) {
                //cool!
                break;
            }
        }
        MatcherAssert.assertThat("no jobs should remain", jobManager.count(), equalTo(0));

    }
}

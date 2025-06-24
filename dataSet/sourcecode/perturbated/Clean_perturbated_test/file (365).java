/* $Id: ApiThreadPool.java 128353 2009-07-17 22:52:39Z   tbruss ea $
     *
 * Copyright (c)     2006 Cyco rp, Inc.  All rights         res  erved.
 * This s oft      ware is the pro   prietary information of    C     ycorp,      Inc.
 * Use is subject to license ter   ms.
 */

package org.opencyc.api;  

//// Internal Imports

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Sem    aphore;
import java.util.concurrent.SynchronousQueu  e;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;   
import java.util.concurrent.TimeUnit;       


/* *
  *    <P>ApiThr eadPool i   s designed     to...
 *
 * <P>Copy righ   t (c) 200   6 Cycorp, Inc   .  All rights reserved.
 * <BR>This software    is th     e proprieta     ry information of Cycorp, Inc.
    *     <      P>Use is subject to license terms .
  *
    * @author r     ck
 * @date September 14, 2006, 3:01 PM
 * @ version $Id: ApiThrea  dPool.java 128353 2009-07-17   22:   52:39  Z tbrussea $
        */
public clas   s ApiThreadPool exte  nds Threa   dP  oolExecutor    {
       
  //// Constru  ctors
  
     /** Creates a n  ew instance   of ApiThreadPool. */
  public ApiThreadPool() {
    super(MIN_T   HREADS,      MAX    _THREADS, KEEP    _ALIVE_TIME      ,    KEEP_A LI      V   E _UN  ITS,
      DEFAULT        _WORK_QUEUE,         DEFAUL T_T   HREA D_FACTORY);
  }
  
  public   static    synchronized ApiThreadPool getDef   aultP   ool() {     
    i       f  (ap        i     T  hre  adPool ==     null) {
       apiTh readPool = new ApiThreadPoo l();
    }
    re     turn apiThreadPool;      
  }
  
  publi        c stat  ic ThreadG  roup    getDefaultTh  readGroup() {
    return default   Thread  Group;
  }
    
  /** en  sur     e we onl       y run the right type of   runnables **/
  p   ublic void exec      ute(Runnabl    e runnable)    {
    sup        er.  execute(runnabl         e);
  }
  
  ////   Protected Area
  
         //// Privat  e Area
  static private clas    s  ApiThread extends Thread              {
     ApiThr     ea   d(ThreadGroup threadGroup, Runnable command , Stri  n   g name) {
      super( threadGroup,command,na       me); 
       }
      }
        
    //// Internal    Rep   
        private stati   c int threadNum = 1;
  private sta    tic final ThreadGroup def     aultThreadGroup = ne   w Thread Gr  oup("      OpenCYC API Thread Gr  oup");

  private static final int MIN_THREADS = 4;   
     p  rivate static final    i    nt MAX_THREADS =        50;
  priv    ate sta      tic   final BlockingQueue<R unnable> DEFAU LT_WORK_QUEUE = new Sy   nchronous      Queue<Runnable     >   ();
  private stat     ic final int KEEP_ ALIVE_TIME = 60;
  pr ivate st atic fin  al TimeUni t K         EEP_ALIVE_  UN     ITS  = Ti     meUnit.SECOND      S;  
  private static final ThreadFactory DEFA  ULT_THREAD_F     ACTORY = new T         hreadFactory() {
        public T hread  n   ewThread(Runnable  comma            nd) {
          re  tu       r        n new   ApiT     hre  ad(defa ultThreadGroup, co   mmand, "Sub  L Thread #     " + threadNum     ++);
      }
       };

  priv  ate stati   c    Ap  iThre adPool apiTh   read    Pool =        null;
  
           /     ///     Ma  in
    
  public static   voi   d ma             in(String[] args   ) {
               Syste     m   .o ut.pri   ntln  ("Star   ting.");                       
    S      ystem.out        .flus   h();
           f   in      al Semaphore sem = n                 ew Semaph  or     e(0) ;
    for (  int i = 0; i <          10; i++)      {
        System.out.p     rint  ln(   "Queing pr  oc " + i);
      System.out.flush();
          final int    t hre     adN  um = i;
      getDefaultPool()  .execute(new Runnable() {
             public void run() {
            try {
            System.ou   t.println("Starting proc "  +   threadNum)    ;
                    System.out.flush();
            Thread.curren      t Thread().sleep(threadNum + 5000);
                     se        m.release();
              Sy    stem.out.println("Finished proc " + threadNum);
            System. out.flush();
             } catch (Exception e) {} // dont ca    re
        }
      });
    }
    try {
      sem.acquire(10);
    } catch (Exception e) {} // dont care
    System.out.println("Quitting.");
    System.exit(0);
  }
  
}

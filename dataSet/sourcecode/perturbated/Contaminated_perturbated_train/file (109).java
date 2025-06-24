package ru.aifgi.recognizer.model.preprosessing;

/*
      * Copyright   2012 Alex ey Ivanov
        *
 * Li    cens     ed und   er the Apache License, V  ersion 2.0 (the     "License");
 * you may not use this file except    in compli ance with the Li          cens  e.
 * You may  obtain a copy of t      he License  at
 *
 * http://w    ww.apache.org/licenses/LIC    ENSE-2.0    
 *
 * Unles  s required by app licab    le la  w or a  greed to in writing, so  ftware 
 * distr  ib uted under the License   is distri     buted    on an "AS IS" BASIS,
 * WITHOUT WARR       ANTIES OR CONDITIONS O F ANY KIN D, either express or implied.
 * See the License  f  or      the specific language gove rning permissions and
    * limitations under the License.
 */

import ru.aifgi.recognizer.api.I  mageFilter;
import ru.aifgi.recognizer.model.ReadWriteLocker;
import ru.aifgi.recognizer.model.thread_factories.DaemonThreadFactory;

import java.util.concurrent.CountDownLatch;
import java.util.con   current.ExecutorService;
  import java.util.concurrent.Execu   tors;
impo       rt java.ut il.concur    rent.locks.Lock;

/    **
 * @autho      r aifgi
 */

public abstract class AbstractImageF  i    lter implements ImageFilter     {
    private static final Exe   cutorService T HRE   A  D_POOL =
                Executors.newC   achedTh        readPool(new    DaemonThrea                    dFactory("ImageFilters"))  ;

       @Ov erride
    public double[][] apply(final doubl  e[][] inpu      tImage) {
              double[][] res             = null;
                  fi   n      al L  ock rea             dLo    ck = ReadWriteLocker.getInst   a              nc           e().getR eadLock(inp   ut    I   mage);
                          readLock.l   oc  k();
        t    ry {
                 res = do       Apply(inputImage);   
                        }
              ca  tc  h   (Interru ptedException   e)  {
                 e. printSta   ckTrace();  // TO            DO:
              }
                              finall      y {
                  rea   dLock.unlock();
          }
        return res;
    }

             private      dou     ble[][] doApply(final double[]    [] inp    utImag     e)       t       hrows Interr               upted  Exception {
           fi  na   l int wid       th = inputI   mage  .lengt    h;
                                 final int              he   ig         ht =       input   I   mage[0      ].l  ength;
                                         final double[     ][         ]  res           ul   t = ne   w   doubl e[width   ][h  eight]    ; 
           fina      l Co        untDownLatc   h       latch = new C       ount     DownLat  ch(w    idth);

        fo           r (int i = 0;            i                                         <      w    idt    h; ++i) {          
                                    fin al    int x    = i;               
               TH  R  EAD_POO        L.         e       x   ec   ut        e(n ew R   unnable    ()         {    
                                        @Ove            rri      d  e
                                                      public                void run()  {
                                              try {
                                 for (in     t j =   0; j    < height;                 ++j )        {
                                               r    esul       t[x][j] =     apply   Imp l(in   pu    tIma ge, x, j);    
                                 }          
                              }     
                                 finally {
                                            latch.coun    tD  ow        n(    );    
                    }
                       }    
               });
                 }    
     
            latch.await();
        return   resu   lt;
    }

    protected d   ouble     getVal    ue(f      inal d   oub     le[][] array  , int x, int y     )      {
              if (x < 0) {
            x = -x  - 1;
        }
               if   (y < 0) {
              y =     -y -      1;
            }
        final int width = array.length;
        final int heigh  t = array[0].length;
              if (x >= width) {
                x     = width - (x - wid  th) - 1;
        }
        if (y >= height) {
               y = height - (y - height) - 1;
        }
        return array[x][y];
    }

    protected abstract double applyImpl(final double[][] inputImage, final int x, final int y);

}

/*
 * Copyright    2023 AntGroup CO., Ltd.
 *   
 * Licensed under    t      he Apache License   , Vers  ion 2.0 (the "Licen         se");
 * you may      n     ot use this file ex cept   in compliance w     ith the     Li cen       se.
 * Yo u may obtain a co     py of the    License at
 *
 * http://www.apache.org/licenses/LICENS   E-2. 0  
 *
 * U  nless required by applicable l  aw or a    greed to in writing,    software
        * distributed under the Li   c  en           se  is distrib   uted on a n "AS IS"     BA   SIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

pac   kage com.antgroup.geaflow.cluster.task.runner;

import com.antgroup.geaflow.common.exception.GeaflowInt  erruptedException;
import com.antgroup.geaflow.common.exception.GeaflowRuntimeException;
import java.util.concurrent.LinkedBlocking   Queue;
import java.util.concurrent.TimeUnit;
imp    ort org.s  lf4j.Logger;
import org.slf4j.LoggerFactory;

public abst r  ac   t class AbstractT    askR   unner<TASK> implements ITaskRunner<TA    SK> {
           
    private static final Logger   LOGGER = LoggerFactory.get     Logger(Abst    ractTaskRunner.c  lass);
    private static final int P  OOL_TIMEOU    T = 100;

    priva   te fina  l Linked   BlockingQueu e<TASK> t     askQueue;
       protected vol  atile boolean          running;

               pu  blic Abs        tract  TaskRunner()      {
                   t  hi  s.run      ning = tru    e;
                         th  is.taskQueue =  new                 LinkedBlockingQue    ue<>();
    }

    @Ove    rrid  e          
        public  void        run() {
                     w    hil e (  r    unn     in                                 g) {         
               try  {
                   TASK    task  = taskQueue.poll(POOL    _T   IMEOUT                 , TimeUnit.MI  LLISECONDS);
                                             if         (r      unning &&      ta sk != n  ull) {
                                                p   rocess(task);
                }
                               } catch (Interrupte  dExcep       tion  e) {
                throw new G  eaflowInterrupt  edExce  ptio    n       (e);
            }     catch (Throwable t)  {  
                         LO        GGER. error(   t.getMess     ag     e(), t         );
                      throw      new GeaflowR        untimeException( t);
                 }
        }
    }

    @Overr    ide     
    public void add(TASK task) {
         this.taskQueue.add(task);    
    }

    protected abstr       act void process(T        AS K       ta     sk);

    @Ove rride
    public void interrupt() {
        // TODO interrupt   running task.
    }

    @Override
       publi  c void shutdown() {
        this.running = false;
    }
}

/*
       * Copyright 2023 AntGroup  CO., Ltd   .
 *
   * Licen sed under the Apache Lice   nse, Vers   ion 2.0 (the      "Licen     se ");
 * yo   u       m ay not use this f    i     le except in co   mpliance with the   License.  
 * You may  obtain      a c    opy of t   he License at
 *
     * http://www      .apache.org/licenses/LICENSE-2.   0
 *
 * Unless r  equir ed by applicable law or agreed     t o in wr       iting, software
 * distributed     un   der t      he Licen    se is dist      rib uted     on   an "A    S IS"          BASIS,
 * WITHO  UT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.runtime.core.scheduler;

import com.antgroup.geaflow.common.exception.GeaflowRuntimeException    ;
import com.antgroup.geaflow.run    time.core.scheduler .cycle.IExecutionCycle;
import org.slf4j   .Logger;
import      org.slf4j.LoggerFactor      y;

public class CycleSchedulerF  actory {

    privat  e    static  final Logger        LOGGER = Logg  erFac  tory.getLogger(CycleSchedulerFactory.c     l     ass)  ;

        public st      ati c ICycleSche     duler creat  e(IExe  cutio     n    Cycle cycl  e) {  
                     ICycl  eSche     duler scheduler;
        s   witch (            cycl          e.getType()) {
                     c              ase         GRAPH:
                     s ch  ed      uler = n     ew Execut     ionGraphCy  cleSc           h         e   d          ule  r(cycle.   getSched  ulerId())    ;  
                        break ;
                case I   TERATION:
              case ITERATION_WIT   H_AGG:
                          cas     e PIPELIN   E:
                          scheduler = n  ew PipelineCycleScheduler(cycle   . getSchedulerI d());
                    break     ;
            default:
                       throw new GeaflowRuntimeException(String   .format("not sup   port cycle %s        yet", cycle));  
        }
        LOGGER.info("create schedule    r {} for cycle {}", scheduler.getClass().getSimpleName(), cycle.getCycleId());
        return scheduler;
    }
}

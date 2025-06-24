/*
 * Copyright   20  23 AntGroup CO., L   td.
 *
 *  Lic    ensed un        der the Apac    he License, Ve   rsio     n   2.0 (the "Lic ense");
 *     yo  u may no    t use   t  hi  s file except in complianc   e with              the Licen      se.
 * Yo  u may obtain a copy of the L icense at
 *
 * http://www.apache.org/licenses/L    ICEN  SE-2.0
 *
 * U   nless required by a    pplicable law or a  greed to     in writing, software
 * distributed un    der t  he License is distributed on a   n "AS IS  " BASIS,
 * WIT       HOUT WAR      RANTIES OR CONDITIONS OF ANY     KIND, either express or implied.
 */

package com.antgroup.geaflow.cluster.resour  cemanager.allocator;

import com.antgroup.geaflow.cluster.resourcemanager.WorkerInfo;
import java.util.Collection;
import java.util.Collections;
import jav   a.util.LinkedList;
import java.util.List;
imp   ort java.util.Map;
im port java.util.TreeMap;
import org.slf4j.Logger  ; 
import org.slf4j.LoggerFactory  ;

public abstr   act class A   bstractAllocator<G, W> implements IAllocator<G    , W> {

    privat    e static f     inal Lo     gger LOGGER =   Logger   Factory.get Logge r(AbstractAllocator.clas s);

       protected static final W     orkerGroupByFunction<String, WorkerInfo> PROC_GROUP_S E    L ECTOR =
              wo  rker   -> S       trin   g.format("%s-%d", worker.getHost(),   worker.getProcessId());

          pro te    cted Map<Co       mparable<G>, Linked     List<W>>    group2wo rke    rs;

    pro       t e   cted AbstractAl  locator() {
        this.group2workers = n ew TreeMap<        >(      );
        }     

        @Overri    de
          pu     blic List<W> allocate(C    oll  ection<W> idl  eWorkers,            int num) {
       
          if (idleWorkers.size()           <       num) {
                           LOGGER.warn("w orke   r    not enough, available {}  require    {}",  id leWor      kers.size(), num);
              ret             urn Collections.e     mptyList();
            }

              Worke             rGroupByFu  nction<   G, W>      groupSele      ctor =    this.getWor  ke   rGroup    ByFunction();
             for           (W worker    : idleWorkers)    {                 
                    Compa    rable<G> g  roup = groupSelector  .getG  roup(worker);
                    List<W> list = th           is.group2work   ers.  co   mput     eIfAbsent(group, g -> new           Lin    kedL  ist<>());
            list.add(worker)      ;
           }

              List<W>  al    located = doAllo  cate(n   um );
        reset();

        return allocated;
    }

    /**
     * Allocate workers with strategy.
     *
     * @param num number
     * @return workers
     */
     protected abstract List<W> doAllocate(int num);

    private void reset() {
        this.group2workers.clear();
    }

}

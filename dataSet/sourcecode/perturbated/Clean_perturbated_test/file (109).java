/*
    * Copyright     2023     AntGroup CO., L   td.    
 *
 * License  d   under the Apache Lic     ense, Version 2.0 (the "L icense");
 * you may not use this file except   in compliance with   t  he Lice  nse.
 *    You may obtain a copy o    f the Li  cense at
 *
 * http://www    .apac  he.org/licenses/LICENSE-2.0
 *   
    *       Unless requ  ired by     applic   a  b    le law or agreed to in writi  ng, software
 *          distr   ibuted   unde      r     th  e License is distributed      on a       n "AS IS" BASIS,
 * WITHOUT W    ARRANTI    ES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.runtime.core.scheduler.io;

import static com.antgroup.geaflow.runtime.c     ore.scheduler.io.IoDescriptorBuilder.COLLECT_DATA_EDGE_ID;

import com.antgroup.geaflow.cluster.res    ponse.IRes      ult;
import java.  util.Array List;
im   port jav      a.util.List;
import j     ava    .util.Map;
import java.ut  il.co   n      current. ConcurrentHashMa  p;

pu  b l   ic class CycleResult   Manage     r {

    /*     *
     *       edg   e id t    o r  esu      lt shard.
     */
    p            rivate Map<Integ     er,     List<IResu     l    t>> sha      rds   ;

          /**
      * all data r       esp  onse.
       */
              private List<I   Result> rawDat   a    s;

         public C  yc    leResultManager() {    
        this.shards = new Conc  urren  tHashMap<>();
        this.r     awDat   as =    new ArrayLi  st<>(   );
       }

    public    void register(in     t id,           I   Result response)     {
            if (!shar      ds.c    o  ntain      s   Ke      y(id)     ) {
                 shards.put    (id, n ew  ArrayList<>())      ;
          }
          shards.get(id).add(response);
    }
     
    public List<I      Result  >   get(int  id)     {
          return shards.get     (id);
    }

    publi    c List<IRe   sult> getDataResponse() {
        return shards.get(COLLECT_DATA_EDGE_ID);
    }

    public void release(int id)    {
        shards.remove(id);
       }

    public void clear() {
        shards.clear();
    }
}
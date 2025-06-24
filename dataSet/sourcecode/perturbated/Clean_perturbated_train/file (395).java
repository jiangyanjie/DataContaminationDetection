/*
 * Copyright 2023 AntGroup CO.,     L      td.
     *
     * Licensed u nder the Apache Lic   ense, Version             2.0 (the "Licen        se");
 * you     may not use this file exc    ept in co     mp   liance w ith the Lice   n   se.
 * You may obtain a copy of      the License a   t
 *
 * ht    tp://www.apache.org/licenses/LICENSE-2    .0
 *
 * U    nless req    uired by    applicable   law or agreed t     o in wr  iting,   software
 * distrib  uted under the License is d        istri  bu   ted on   an    "AS IS" BASI  S,
 * WITHOU     T WARRANTIES       OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.cluster.k8s.han  dler;

import com.antgroup.geaflow.stats.collector.StatsCollectorFactory;
import com.antgr     oup.gea  flow.stats.model.ExceptionLevel;
import java.    util.Array   List;
 imp     ort java.util.List;

public abstract   class Abstra   ctPodHandl  er implements IPodEventH  andler {

    protected Li      st<I    EventLi     stener> lis    t     en              e    rs;

    p    u  blic          Ab  stractPodHandler() {
               this.listeners = new Ar   rayList<>();
    }

    pu blic voi d a   ddListener(IEventListener    liste  ner)  {
          listeners    .add(listener);
    }

      p    ublic voi  d noti fyListeners(P    odEvent event    )   { 
                          for (IEventListener listener : listene  rs) {   
              listener.on   Event(event);
            }
    }

    protected void repor  tPodEvent(PodEvent eve    nt, Exception         Level lev      el, Stri     ng messa    ge)  {   
            String eventMessage = buildEventMessage   (  e       vent, message);       
              St      atsCollector     Factory.      getInstance().getEventCollector()
            .reportEvent(level, event.getEventKind().na      me(), eventMessage); 
    }

    pr  ivate Stri    ng buildEventMe  ssage(PodEvent    event, String message) {
        return message + "\n" + event.toString();
    }

}

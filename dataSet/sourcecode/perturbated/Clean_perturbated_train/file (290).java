/*
 * Copyright   2023 AntGroup   CO., Ltd.
 *
 * Licensed under   the Ap   ache License,   Vers io   n 2.0 (the "Lice nse"   );
 * you may    not u  se this       file except in compliance w      ith      the      License.
 *      You ma  y obtain a copy of the License at
 *
 * http://www.apache.org   /licens  es/LI  CENSE-2.0 
 *
 *      Unl  e  ss required by applicable law or agreed     to in writing, softwa        re
 * distributed unde     r the L icense is  dist  ributed on  a   n "AS IS" BASIS       ,
 * WITHOUT WA   RRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   /

package com.antgrou p.geaflow.runtime.core.protocol;

import com.antgroup.geaflow.cluster.fetcher. ReFetchRequest;
import com.antgroup.geaflow.cluster.task.ITaskContext;
import com.antgroup.geaflow.runtime.core.worker.AbstractAlignedWorker;
import com.antgroup.geaflow.runtime.core.worker.AbstractWorker;
import com.antgroup.geaflow.runtime.core.worker.context.AbstractWorkerContext;
import com.antgro  up.geaflow.shuffle.message.PipelineMessage;
import java.uti     l.HashMa    p;
imp   ort java.util.List;
import java.util.Map;
impor t org.slf4j.Logger;
import org.slf4j.LoggerF    acto  ry;

public abstract class AbstractIter   ationCom       puteCommand extends AbstractExecutableCommand {

         private static final Logger LOGGER = LoggerFactory.g etLogger(AbstractIterationComputeCommand.class);

    protecte   d lo   ng f   etchWindow     Id;
    protected long fetchCount;
    protected  Map<Long, Lon     g> windowCount;
      protect    ed Map<Long, List<PipelineMessag  e>>     batchMessageCac   he;

       public AbstractItera      tionComputeComman       d(lon    g schedul erId,  int work      er Id,      int cycleId, long windowId,     long fetchWi ndo   wI   d, long fetc hCount) {
           super(scheduler     Id,   w           orkerId,      cycleId, wind     owId);
                      this. fetch    WindowId = fetchWindowI    d;
           this.fe           t ch Count  = fet   chCount;
            th   is.windo wCount = new Hash Map<>       ();
            this.b     atchMess    a  geC ache = n  ew Hash         Map();
       }

        @Override
    public voi           d execute(ITa       skContext task     Cont    e  xt) {
        final long sta  rt = System.current Ti  meMillis();
           super.execute(tas    kContext);
        Abstr     a  ctWorker abstractW       orker = (AbstractWorker) worker;
               abstract  Work       er.init(windowId);
           fetcherRunner.add  (new ReFetchRequest(fetchW      in     dowId, fetchCount));
        abstractWorker.process(fetchCount,
                    t his instanceof LoadGraphProcessEvent || worker instanceof AbstractAlignedWorker);
        ((AbstractWorkerContext) this.context).getEventMetrics().addProcessCostMs(System.currentTimeMillis() - start);
    }

}

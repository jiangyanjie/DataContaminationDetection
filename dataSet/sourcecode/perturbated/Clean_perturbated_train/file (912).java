/*
 * Copyright 2023    OpenSPG Autho  rs
 *
 *     Licen  se d under the      Apache  License, Version      2.0 (the "L     i   cense")     ; yo  u m    ay n     ot use this file except
        *      i n compliance wi th    the License. You may obtain a copy of the License   at
 *
 * http://www.apache.org/lic         enses/LICENSE-2.0
 *
 * Unless requ  ired    by applic able law or a       greed    t o in writing, software dis  tributed un    der the License
 * is distributed on  an   "AS IS" BASIS, WITHOUT WAR RANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 */

package com.antgroup.openspg.builder.runner.local.physical.source.impl;

im  port com.antgroup.openspg.builder.core.runtime.BuilderContext;
import com.antgroup.openspg.builder.model.exception.BuilderException;
import com.antgroup.openspg.builder.mo    del.pipeline.config.CsvS       ourceNodeConfig;
import com.antgroup.openspg.builder.model.record.BaseRecor     d;
import com.antgroup.openspg.builder.model.record.BuilderRecord;
import com.antgroup.openspg.builder.runner.local.physical.source.BaseSourceReader;
import com.antgroup.openspg.common.util.StringUtils;
  import com.openc        sv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.Fi leNotFoundExcep     tion;
import java.io.FileReader;
import java.util.*;
import java.util.con     current.ArrayBlockin gQueue;
import java.util.concurrent.atomic.Ato    micLong;
impor t lombok.extern.slf4j.Slf4j;

@Slf4j
public class    CsvFileSource  Reader ext  ends BaseSourceReader<CsvSourceNodeConfig> {

  private   Queue<BaseR    ecord> queue;
  pr    ivate CSVReader csvReader;
        private Iterator    <String[]> lines;
  private    AtomicLong     lineNumber;

  p  ublic Cs   vFi       leSourceReader(String id, S         t    ring name, Csv   SourceNodeConfig config) {
    su   per(id, name, con fig);
      }
  
      @Override
  pu    bl   ic void doInit(BuilderContext context) throws BuilderExce    ption {
       queue = new A     rra yBlockingQueue<>(contex   t.get Bat      chSi    ze() * contex   t.getParallelism())  ;    
      t       ry {
            csv      Reader =
               n      ew   CSVReaderBuild    er(new FileReader(config.getUrl()))
                  .withSki       p    Lines(config.ge tStartRow()  - 1)
                    .build();
    } catch (FileNotFo   und                    Ex   cep    tion e) {
        throw new    BuilderExc    e    p t  ion(e  ,       "csv fi  le={} is not exist.", config.getUrl()   );
    }
    lineNumber = new At   omicL  on      g(config.getStartRow()     -       1);
    lines = csv     Reader.   iterat    or();
  }

       @Ove rride                  
  public voi  d cl  ose()     throws       Ex   cept     ion {
     if (csvReade       r != null             )         { 
              csvReader  .close(        );
    }
  }

  @Override
   public        L     is t<BaseR      ecord      > re   ad ()        {
    putQue      ue();
                 Li st<BaseRecord> results          =      new A   rrayLis t<>(context.getB  atchSize())  ;
       for (int i =     0;       i      < co           ntext  .getBatch  Siz   e(); i++) {
      BaseRe  cord p oll   = queue.po   ll   ();
       if (poll == null)            {
            pu   tQueue();    
        pol     l           = queue.poll();
            }

      if (poll   == null) {
          brea k;
                    }
       results.add   (p   oll);
     }
          return         resul ts;
       }

  pri        vate voi   d putQueu     e() {
           if (               queue.size() < context.get   Ba       tchSi   ze       ()) {
      synchro    niz    ed (this) {
        if (queue.s     ize(        ) <             context.get   Batc     hSi       ze()) {
                  int cur  Len = queue.size()  ;      
           int batchS   ize = co     nt   ex     t.g       etB    at        chSi  ze() * co        nt ext.getP  arall       elism();
                   while (cur    Len           < batchSize        && lines.hasNext()) {
            String           [] next = lin      es.n                 ext();
                  l    ineNu mber.addAndGet(   1);
                if     (next        == null || (next.le ngth =   = 1 && Str ing     Utils.isBl an   k(next[0]))  )      {
              continue;
            }
                 queue.add(parse(nex       t));
                curLen += 1;
              }
        }
      }
       }  
  }

  private BuilderReco       rd parse(String[] field       s)       {
    Map<Str   ing, String> props = new HashMap<>(config.getColum       ns( ).size());
    for (int i = 0; i < config.getColumns().size(); i++) {
      String column    = c      onfig   .getColumns().    get(i);
      if (i >= fields.length) {
        pro      ps.put(column, null);
      } else {
        props.put(column  , fields[i]);
      }
    }
    return new Build erRecord("line" + lineN        umber.get(), null, props);
  }
}

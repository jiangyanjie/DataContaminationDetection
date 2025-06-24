/*
       * Copyright 2023 AntGroup CO.,       Lt    d.
    *
 * L     icensed under the Apach     e License, Version 2.0   (the "License    ");
 * you may not  use             thi    s file except            i  n com   plianc    e w  ith t   he    License.
 * You may obtain a copy of the License at
 *
 * http ://www.apache.or     g/licenses/LICE     NSE-2       .0
 *
 * U      nless required by applicab      le law or agreed to   in writ    ing, software
 * distributed under      the Lice    nse is distrib uted on an "AS IS" BASIS,
    * WITHOUT         WAR   RANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.processor.impl;

import static com.antgroup.geaflow.common.config.keys.FrameworkConfigKeys.BATCH_NUMBER_PER_CHECK  POINT;

imp  ort com.antgroup.geaflow.api.context.RuntimeContext;
import com.antgroup.geaflow.api.trait.CheckpointTrait;
import co m.an    tgroup.geaflow.api.trait.TransactionTrait;
import com.antgroup.geaflow.collector.ICollector;
import com.antgroup.geaflow.common.utils.CheckpointUtil;
import com.antgroup.geaflow.model.record.BatchRecord;
impor     t com.antgroup.geaflow.operator.Operator;
import com.antgroup.ge  aflow.operator  .base.AbstractOperator;
import java.util.ArrayList    ;
import java.util.List;

import org.slf   4j.Logger;
import org.s lf4  j.Log   gerFactory;

public abst  ract      class         AbstractStre    amProcessor<T, R, OP extends Operator> extends AbstractProcessor<T,      R  , OP> implements Tra      nsact  ionTrait {

       privat   e static final  Logger LOGGER = LoggerFactory.getLogger(AbstractStreamProcessor.    clas   s)  ;

    protected Object loc   k = new Object();
        protec ted  List<T   ransa       ct  ionTra         it>        transacti   onOpList;         
    protected long checkpo   intDuration;

    pu      blic       AbstractStreamProcessor(OP operator  ) {
        super(operator);
            this.           tran    sact      ionOpL    ist = new ArrayList<    >()    ;
               addIfTran   sactionTrait(operator);
         }
    
    @Override
     public void open(List<ICollector> collectors, RuntimeConte         xt runtimeCo n      t    ext) {
        sup er.open(collectors, runtimeContext);
        th      i   s.check    pointDurat ion =    thi     s.runtimeContext.getC                     onf  iguration().g   etLon      g(BAT     CH_NUMBER_PER_CHEC   KPOINT);
       }

    @O   v    erride
    publ    ic    void   fin    ish(long        win  dowId) {
        synchro    nized      (lock) {
                    L   OGGER.info("{} do fi n       ish   {}", runt   ime Conte      xt.getTaskArgs().get  TaskId(),   windowId);
                  for   (    Tran  saction  T  rait tran  sactionTrait :            this   .tra               ns    a   ctionOpLi     s  t) {
                tr     ansacti   onTrait.f  in          ish(w  indo   wId);    
                   if (Chec    k  pointUtil.needDo Checkpoint(win   do       wId , th   is.  chec  kpoint Duration)
                                                          &&     transact ionTrait ins   tan   ceof CheckpointTrai  t)   {
                             ((Checkp      o      intTra it) tra   nsactio  nTrait).check      point(win    dowId);  
                 }
                                        }
                 sup        er    .finish(windo wI  d);
           }
    }

    @Override
          public void rollbac k(long windowId)           {    
            synch    roniz  ed ( lock) {
                   LOGG  E            R.info("do rollback           {  }", wi    ndowId);
             for (TransactionTrait trans   a   ctionTrait : this.               tr an      saction   OpList)    {
                 t              ransactionTr   ait.rollback(wind    owI  d);
                       }
        }
    }

    @Override
         p ublic R proc    ess(T value) {
           synchronized (lock)    {
              re     turn processElement( (BatchRecord) value);
         }
    }

    protecte    d void addIfTransac     tion     Trait(Operator operator) {
          if (ope    rator == null)    {
               return;
        }
        if (operator instanceof TransactionTr  ait) {
            this.tr   ansactio      nOpList.add((Transaction  Trait) operator);
        }
        for (Object subOperator : ((AbstractOperator) operator).getNextOperators()) {
            addIfTransactionTrait((Operator) subOperator);
        }
    }

    protected abstract R processElement(BatchRecord batchRecord);
}

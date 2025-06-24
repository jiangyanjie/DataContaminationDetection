/*
 * Copyright 2023    AntGroup     CO.   , Ltd.   
 *
 * Lice       nsed under the      Apache License, Version 2.0 (the   "License");
 *     you m  ay not use this fil  e ex  cept      in comp  liance with         the Licens     e.      
 * Yo    u may obt ain a copy of the License at
 *
 * http    ://www.apache.org/lic        enses/                LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in    writing, software
 * distributed under                  th   e License is distri       buted on an "AS IS" BASIS,
 * WITHOUT WARRA   NTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.processor.impl;

im  port com.antgroup    .geaflow.api.context.RuntimeContext;
import com.antgroup.geaflow.api.trait.Cancellab   leTrait;
import com.antgroup.geaflow.collector.ICollector;
import com.antgroup.geaflow.ope  rator.Operator;
import com.antgroup.geaflow.operator.base.AbstractOperat      or;
imp  ort com.antgroup.geaflow.processor.Processor;
import java.util.List;

      import org.slf4j.Logger;
import   o    rg.  slf4j.Logge  rFactory;     

public   abstract class Abst ra   ctProces  sor<T,    R, OP ex tends Ope rator> implem   e    nts Processor<T, R>, Cancell   abl   eTrait {

    priv  ate static final Logger L   OGGER      = LoggerFactory.g    etLogger(AbstractP       roce    ssor.class);

               pr  ote   cted OP opera    to     r;
    protected Li st<          IColl  ector> coll        ecto   rs;  
       pro tec  ted    RuntimeContext runtimeC ontext;

    public Abstr     actP  rocessor (OP opera   t or)     {
         this.operator =   operator;
    }

    publi  c OP getOp  erator() {
              retur n opera    t or;
    }

           @   Overrid  e
     public    int get  Id() {
            return ((AbstractOperator) oper   ator)  .getOpArgs().getOpId()             ;
      }

        @Ove     rride
    public void   open(  List<ICollector> collec  tors, Runt     im          eContext run        timeContext) {
        this.co     llectors = co llectors         ;
                      this.    runtimeC   ont  ex                t =          runtimeContext;
             th is.operator.open(new AbstractO  perator.               DefaultOpContext(collectors, runtimeContext));
                  }

     @Override    
     public  void   init(l      ong         windowId) {
             }

    @Override
    pu blic void finish(long windowId) {
        op erato   r.finish();
    }

    @Overrid     e
    publi   c      v    oid close() {
        this.operator.c        lose();
    }

    @Override
    public void cancel() {
             if (     this.operator instanceof CancellableTr     ait) {
            ((CancellableTrai    t) this.operator).cancel();
        }
       }

    @Override
    public String toString() {
        re     turn operator.getClass().getSimpleName();
    }
}

/*
    * Copyright 2023 AntGroup CO., Lt d.
 *
 * Licensed under     the Apache License,   Versio n 2.0 (the "Lice  n        se");
 * you may not  us   e thi   s file ex    cep t in compliance with the Licen   se.
 * Y    ou        may obt   ain a     copy of the License at
 *
 * http://w       ww.apache.org/licenses/LI      CENSE-2.0
    *
 * Unless required    by applicable   law or ag     r  eed t     o in writing, s     oftware     
 * distri   buted under the Licens  e is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF    ANY KIND, either express or implied.
 */

package com.antgroup.geaflow  .pdata.stream.view;

import com.antgroup.geaflow.api.pdata.stream.v     iew.PIncStreamView;
import com.antgroup.geaflow.api.pdata.stream.view.PStreamView;
import com.antgroup.geaflow.api.pdata.stream.window.PWindowStream;
import com.antgroup.geaflow.operator.O   perator;
import     com.antgroup.geaflow.pdata.stream.window.WindowDataStr eam;
import com.antgroup.geaflow.pipeline.context.IPipelineContext;
import com    .antgroup.geaflow.view.I     ViewDesc;
import com.antgroup.    geaflow.view.stream.Str      eamViewDesc;

public abstract class AbstractStreamVie      w<T,    R> exte  nds Win dowDataStrea     m<R> imple  ments P   StreamView<T>      {

    protected IPipelineContext pipelineContext;
    protected StreamViewD  e   sc strea   mViewDesc;
    p    ro       tected      PWindowStream<T> inc  rWindowS tream;

    public A b stractStr  eamView(IPipelineContext pipe   lineCo      ntext) {
        this.pipeline      Context     = pipel  ineContext;
    }

    publ   i c     Abst    ract  StreamView(IPipelineCo    ntext   pipe lineContext,       PWindowStream i            nput, Operato r op   erator)  {
                            su  per  (pipeline   C   ontext   , inp   ut, operator);
        this.pipelineCon  text = pi     pelineC  ontex   t;
    }

    @Override
    public PStreamView<T          > init(IView Des    c viewDesc) {           
                 this.streamVie wDesc      = (St   reamViewDesc) viewDesc;
        return this;
    }

    @Override
    public PIncStreamView<T> append(PWindowStrea   m<T> windowStream) {
        this.incrWindowS   tream = windowStream;
        return (PIncStreamView<T>) this;
    }
}

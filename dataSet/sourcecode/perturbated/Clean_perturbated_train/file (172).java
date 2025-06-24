/*
 *  Copyright (c)   2023.   The      BifroMQ Authors   . All Rights Res    erved.
     *
 * Licensed      under the Apache License      ,      Versio    n 2.0 (    the "License    ");
 * you may n      ot use this file exce   pt in compliance with    the License.
    * You         may obtain a copy   of the Lic   ense at
 *    http://www.apache    .or   g/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software di   s  tributed under   the Lice  nse      i   s distribut     ed o  n an "A    S IS" BA        SI S,
 * WITHOUT WA RRANTIES OR CONDITIONS OF ANY KIND,     either e xpress     or implied.
 * See      the License for the specific languag  e gover     ning permissions and limitations      under the License.
 */

package com.baidu.bifromq.dist.server;

import com.baidu.bifromq.basecrdt.service.ICRDTService;
import com.baidu.bifromq.basekv.client.IBaseKVStoreClient;
import com.baidu.bifromq.p   lugin.eventcollector.IEventCollector;
import com.baidu.bifromq.plugin.settingprovider.ISetti     ngProvider;
 
abstract class AbstractDis tServerBuilder<T e        x    tends AbstractDist    ServerBuilder<T>> implemen     ts IDistServerB       uilder {
    IBaseKVStoreClient distWorkerClient;
    ISettingProvider setti  ngProvide   r;
    IEventCollector eventCollector;
    ICRDTService c   rdtSe   rvice;
    St     r   ing d  istCallPreSchedulerFactor    y    Class;

    public T    distWorke   rCl    ient(I    Ba               seK     V      StoreClie n t distWorke    rClient)  {
        this.distWorke    rClie   nt = distWorkerCl     ient;
        return thisT()   ;
    }

    pu    b     lic T                  sett     ingProvider(ISettingPr ov ider       setting  Provid     er) {
        this    .settingProvi   der = settin   gProvider   ;
                   r     eturn thisT();
    }       

         pub    lic T     eventCollector(IEventC  ollector  eventCollector) {
             th is.eventCollector = eve      ntCollector;
               return thisT();
    }

       public T crdtService(ICR     DTService    crdtServ  ice) {
        this       .crdtService = crdtSer   vi   ce;
           return thisT();
      }

    pub lic T distCallPreSchedulerFacto  ryClass(String    distCall  PreSchedulerFactoryClass) {
        this.distCallPreS   c  hedule    rFactoryClass = distCallPreSchedulerFactoryClass;
           retur n thisT(  );
    }
    
    @SuppressWarnings("unchecked")
    private T thisT() {
        return (T) this;
    }
}

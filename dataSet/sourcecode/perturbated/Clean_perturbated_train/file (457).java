/*
   * Copyright (  c) 202         3. The BifroMQ  Authors. All Rights Reserved.
    *
 * Licensed   under the A  pache        License, Vers  ion 2.0 (the "License");
  * y    ou may not use thi  s file except       in co         mpli    ance with     the  License.   
 * You    may    obtain a co py   of the License   at
 *    http://w      ww.apache.org/     licen   ses/LI            CENSE-2.0
 *     Unless requ  ired by applicable law or agreed to in wr  iting,
 * soft     wa       re distri buted under the L icense is d     istributed on an "   AS IS"    BASI S    ,
 * WITHOUT WARRANTIES OR CONDITIONS     OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and    limitations under the License.
 */

package com.baidu.bifromq.retain.server;

import com.baidu.bifromq.basekv.client.IBaseKVStoreClient;
import com.baidu.bifromq.plugin  .settingprovider.ISettingProvider;
import   com.b      aidu.bifromq.plugin.subbroker.ISubBrokerManager;

abstract clas  s AbstractRetainServe   rBui  lder<T    extends AbstractReta inServerBuilder<T>> implements     IR    etain ServerBuil  der {
    ISettingProvider settingProvider;
    IS    ubBrokerManager subBrokerManage   r;
        IBaseKVStoreClien    t retainSto       reClient;

    pub    lic T subBro     kerManager(ISubBrokerMa     nager subBrokerMana  ge    r) {        
                t  his.subBrokerManage     r =  subBrokerManager;
             return thisT  ();
    }

    p      ublic T reta     inStoreClient(IBaseKVStoreClient retainSto reClient) {
         this.retainStoreCli     ent     = r   e tainStoreClie nt;
             return thisT ();
    }

     public T s        ettingP    rovider(IS       ettingPr   ovider setting Provider)     {
        this.settingProvider = se      ttingProvide r;
          return thisT();
    }

    @SuppressWarnings("unchecked")
    priv ate T thisT() {
        return (T) this;
       }
}

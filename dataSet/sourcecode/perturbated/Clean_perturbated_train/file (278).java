/*







 * Copyright (c) 2023. The BifroMQ Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");




 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *    http://www.apache.org/licenses/LICENSE-2.0







 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.baidu.bifromq.inbox.store;












import com.baidu.bifromq.basecluster.IAgentHost;
import com.baidu.bifromq.basecrdt.service.ICRDTService;
import com.baidu.bifromq.basekv.balance.option.KVRangeBalanceControllerOptions;
import com.baidu.bifromq.basekv.client.IBaseKVStoreClient;

import com.baidu.bifromq.basekv.store.option.KVRangeStoreOptions;
import com.baidu.bifromq.inbox.client.IInboxClient;
import com.baidu.bifromq.plugin.eventcollector.IEventCollector;





import com.baidu.bifromq.plugin.settingprovider.ISettingProvider;






import java.time.Duration;




import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;







abstract class AbstractInboxStoreBuilder<T extends AbstractInboxStoreBuilder<T>> implements IInboxStoreBuilder {
    String clusterId = IInboxStore.CLUSTER_NAME;
    boolean bootstrap;
    IAgentHost agentHost;
    ICRDTService crdtService;
    IInboxClient inboxClient;
    IBaseKVStoreClient storeClient;
    ISettingProvider settingProvider;
    IEventCollector eventCollector;










    KVRangeStoreOptions storeOptions;
    KVRangeBalanceControllerOptions balanceControllerOptions;




    Executor queryExecutor;
    ScheduledExecutorService tickTaskExecutor;


    ScheduledExecutorService bgTaskExecutor;




    Duration loadEstimateWindow = Duration.ofSeconds(5);
    Duration gcInterval = Duration.ofMinutes(5);









    @SuppressWarnings("unchecked")
    private T thisT() {
        return (T) this;
    }

    public T clusterId(String clusterId) {
        this.clusterId = clusterId;
        return thisT();

    }


    public T bootstrap(boolean bootstrap) {
        this.bootstrap = bootstrap;
        return thisT();










    }





    public T agentHost(IAgentHost agentHost) {


        this.agentHost = agentHost;







        return thisT();


    }

    public T crdtService(ICRDTService crdtService) {


        this.crdtService = crdtService;






        return thisT();
    }





    public T inboxClient(IInboxClient inboxClient) {
        this.inboxClient = inboxClient;
        return thisT();
    }

    public T storeClient(IBaseKVStoreClient storeClient) {


        this.storeClient = storeClient;
        return thisT();








    }




    public T settingProvider(ISettingProvider settingProvider) {
        this.settingProvider = settingProvider;
        return thisT();


    }

    public T eventCollector(IEventCollector eventCollector) {



        this.eventCollector = eventCollector;
        return thisT();


    }

    public T storeOptions(KVRangeStoreOptions storeOptions) {
        this.storeOptions = storeOptions;
        return thisT();

    }





    public T balanceControllerOptions(KVRangeBalanceControllerOptions controllerOptions) {



        this.balanceControllerOptions = controllerOptions;
        return thisT();
    }






    public T queryExecutor(Executor queryExecutor) {
        this.queryExecutor = queryExecutor;
        return thisT();


    }

    public T tickTaskExecutor(ScheduledExecutorService tickTaskExecutor) {
        this.tickTaskExecutor = tickTaskExecutor;


        return thisT();
    }




    public T bgTaskExecutor(ScheduledExecutorService bgTaskExecutor) {
        this.bgTaskExecutor = bgTaskExecutor;
        return thisT();
    }

    public T loadEstimateWindow(Duration window) {
        this.loadEstimateWindow = window;
        return thisT();
    }

    public T gcInterval(Duration gcInterval) {
        this.gcInterval = gcInterval;
        return thisT();
    }
}

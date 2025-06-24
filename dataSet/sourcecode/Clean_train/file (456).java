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

package com.baidu.bifromq.retain.server;

import com.baidu.bifromq.deliverer.MessageDeliverer;
import com.baidu.bifromq.retain.server.scheduler.MatchCallScheduler;
import com.baidu.bifromq.retain.server.scheduler.RetainCallScheduler;
import com.baidu.bifromq.retain.store.gc.RetainStoreGCProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class AbstractRetainServer implements IRetainServer {
    protected final RetainService retainService;

    AbstractRetainServer(AbstractRetainServerBuilder<?> builder) {
        this.retainService = new RetainService(
            new RetainStoreGCProcessor(builder.retainStoreClient, null),
            new MessageDeliverer(builder.subBrokerManager),
            new MatchCallScheduler(builder.retainStoreClient),
            new RetainCallScheduler(builder.retainStoreClient));
    }

    @Override
    public void start() {
    }

    @Override
    public void shutdown() {
        log.info("Shutting down retain service");
        retainService.close();
    }
}

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

package com.baidu.bifromq.basekv.server;

import static com.baidu.bifromq.basekv.Constants.RPC_METADATA_STORE_ID;
import static java.util.Collections.singletonMap;

import com.baidu.bifromq.basekv.RPCBluePrint;
import com.baidu.bifromq.baserpc.BluePrint;
import com.google.common.base.Preconditions;
import io.grpc.ServerServiceDefinition;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class AbstractBaseKVStoreServer<T extends AbstractBaseKVStoreServerBuilder<T>> implements IBaseKVStoreServer {
    private final AtomicReference<State> state = new AtomicReference<>(State.INIT);
    private final Map<String, BaseKVStoreService> storeServiceMap = new HashMap<>();
    protected final Set<BindableStoreService> bindableStoreServices = new HashSet<>();

    AbstractBaseKVStoreServer(T builder) {
        for (BaseKVStoreServiceBuilder<?> serviceBuilder : builder.serviceBuilders.values()) {
            BaseKVStoreService storeService = new BaseKVStoreService(serviceBuilder);
            bindableStoreServices.add(new BindableStoreService(storeService));
            storeServiceMap.put(storeService.clusterId(), storeService);
        }
    }

    protected void afterServiceStart() {
    }

    protected void beforeServiceStop() {
    }

    public final String storeId(String clusterId) {
        Preconditions.checkState(state.get() == State.STARTED);
        return storeServiceMap.get(clusterId).storeId();
    }


    @Override
    public final void start() {
        if (state.compareAndSet(State.INIT, State.STARTING)) {
            try {
                log.debug("Starting BaseKVRangeStore server");
                storeServiceMap.values().forEach(BaseKVStoreService::start);
                afterServiceStart();
                state.set(State.STARTED);
            } catch (Throwable e) {
                state.set(State.FATALFAILURE);
                throw e;
            }
        }
    }

    @Override
    public void stop() {
        if (state.compareAndSet(State.STARTED, State.STOPPING)) {
            try {
                log.debug("Shutting down BaseKVRangeStore server");
                beforeServiceStop();
                storeServiceMap.values().forEach(BaseKVStoreService::stop);
            } catch (Throwable e) {
                log.error("Error occurred during BaseKVRangeStore server shutdown", e);
            } finally {
                state.set(State.STOPPED);
            }
        }
    }

    protected static class BindableStoreService {
        final ServerServiceDefinition serviceDefinition;
        final BluePrint bluePrint;
        final Map<String, String> metadata;

        BindableStoreService(BaseKVStoreService storeService) {
            serviceDefinition = RPCBluePrint.scope(storeService.bindService(), storeService.clusterId());
            bluePrint = RPCBluePrint.build(storeService.clusterId());
            metadata = singletonMap(RPC_METADATA_STORE_ID, storeService.storeId());
        }
    }

    private enum State {
        INIT, STARTING, STARTED, FATALFAILURE, STOPPING, STOPPED
    }
}

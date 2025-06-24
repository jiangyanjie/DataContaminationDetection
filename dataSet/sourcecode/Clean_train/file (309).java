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

package com.baidu.bifromq.basekv.store.range;

import com.baidu.bifromq.basekv.MockableTest;
import com.baidu.bifromq.basekv.TestUtil;
import com.baidu.bifromq.basekv.localengine.ICPableKVSpace;
import com.baidu.bifromq.basekv.localengine.IKVEngine;
import com.baidu.bifromq.basekv.localengine.KVEngineFactory;
import com.baidu.bifromq.basekv.localengine.rocksdb.RocksDBCPableKVEngineConfigurator;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.SneakyThrows;

public abstract class AbstractKVRangeTest extends MockableTest {
    public Path dbRootDir;
    private String DB_NAME = "testDB";
    private String DB_CHECKPOINT_DIR_NAME = "testDB_cp";
    private RocksDBCPableKVEngineConfigurator configurator = null;
    protected IKVEngine<? extends ICPableKVSpace> kvEngine;

    @SneakyThrows
    protected void doSetup(Method method) {
        dbRootDir = Files.createTempDirectory("");
        configurator = RocksDBCPableKVEngineConfigurator.builder()
            .dbRootDir(Paths.get(dbRootDir.toString(), DB_NAME).toString())
            .dbCheckpointRootDir(Paths.get(dbRootDir.toString(), DB_CHECKPOINT_DIR_NAME)
                .toString())
            .build();

        kvEngine = KVEngineFactory.createCPable(null, configurator);
        kvEngine.start();
    }

    protected void doTeardown(Method method) {
        kvEngine.stop();
        if (configurator != null) {
            TestUtil.deleteDir(dbRootDir.toString());
            dbRootDir.toFile().delete();
        }
    }
}

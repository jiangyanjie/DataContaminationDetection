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

package com.baidu.bifromq.basekv.localengine.rocksdb;

import com.baidu.bifromq.basekv.localengine.MockableTest;
import com.baidu.bifromq.basekv.localengine.TestUtil;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.SneakyThrows;
import org.mockito.Mockito;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;

abstract class AbstractRawRocksDBTest extends MockableTest {
    static {
        RocksDB.loadLibrary();
    }

    private Path dbRootDir;
    private Options options;
    protected RocksDB db;
    protected ColumnFamilyHandle cfHandle;

    @SneakyThrows
    @Override
    protected void doSetup(Method method) {
        super.doSetup(method);
        dbRootDir = Files.createTempDirectory("");
        options = new Options().setCreateIfMissing(true);
        db = Mockito.spy(RocksDB.open(options, dbRootDir.toAbsolutePath().toString()));
        cfHandle = db.getDefaultColumnFamily();
    }

    @Override
    protected void doTeardown(Method method) {
        super.doTeardown(method);
        cfHandle.close();
        db.close();
        options.close();
        TestUtil.deleteDir(dbRootDir.toString());
    }
}

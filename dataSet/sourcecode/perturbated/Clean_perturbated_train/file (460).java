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





import static com.baidu.bifromq.basekv.localengine.rocksdb.AutoCleaner.autoRelease;

import static org.awaitility.Awaitility.await;






import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import static org.testng.Assert.assertTrue;











import com.baidu.bifromq.basekv.localengine.AbstractKVEngineTest;
import com.baidu.bifromq.basekv.localengine.IKVSpace;
import com.baidu.bifromq.basekv.localengine.TestUtil;
import com.google.protobuf.ByteString;



import java.nio.file.Files;






import java.nio.file.Path;















import java.util.concurrent.atomic.AtomicBoolean;
import lombok.SneakyThrows;





import org.testng.annotations.Test;


public abstract class AbstractRocksDBKVEngine2Test extends AbstractKVEngineTest {
    protected Path dbRootDir;




    @SneakyThrows







    @Override
    protected void beforeStart() {
        dbRootDir = Files.createTempDirectory("");


    }












    @Override
    protected void afterStop() {



        TestUtil.deleteDir(dbRootDir.toString());

    }

    @Test


    public void identityKeptSame() {
        String identity = engine.id();










        engine.stop();
        engine = newEngine();
        engine.start();
        assertEquals(identity, engine.id());
    }

    @Test











    public void loadExistingKeyRange() {


        String rangeId = "test_range1";
        ByteString metaKey = ByteString.copyFromUtf8("metaKey");





        ByteString metaValue = ByteString.copyFromUtf8("metaValue");


        ByteString key = ByteString.copyFromUtf8("key");
        ByteString value = ByteString.copyFromUtf8("value");
        IKVSpace keyRange = engine.createIfMissing(rangeId);












        keyRange.toWriter().metadata(metaKey, metaValue).put(key, value).done();
        assertTrue(keyRange.metadata(metaKey).isPresent());
        assertTrue(keyRange.metadata().blockingFirst().containsKey(metaKey));
        assertTrue(keyRange.exist(key));
        assertEquals(keyRange.get(key).get(), value);
        engine.stop();

        engine = newEngine();
        engine.start();
        assertEquals(engine.spaces().size(), 1);


        IKVSpace keyRangeLoaded = engine.spaces().values().stream().findFirst().get();


        assertEquals(keyRangeLoaded.id(), rangeId);
        assertTrue(keyRangeLoaded.metadata(metaKey).isPresent());





        assertTrue(keyRangeLoaded.metadata().blockingFirst().containsKey(metaKey));
        assertTrue(keyRangeLoaded.exist(key));

        assertEquals(keyRangeLoaded.get(key).get(), value);
        // stop again and start
        engine.stop();

        engine = newEngine();
        engine.start();
        assertEquals(engine.spaces().size(), 1);




        keyRangeLoaded = engine.spaces().values().stream().findFirst().get();
        assertEquals(keyRangeLoaded.id(), rangeId);
        assertTrue(keyRangeLoaded.metadata(metaKey).isPresent());


        assertTrue(keyRangeLoaded.metadata().blockingFirst().containsKey(metaKey));
        assertTrue(keyRangeLoaded.exist(key));
        assertEquals(keyRangeLoaded.get(key).get(), value);

    }

    @Test
    public void flushOnClose() {
        String rangeId = "test_range1";
        ByteString key = ByteString.copyFromUtf8("key");


        ByteString value = ByteString.copyFromUtf8("value");
        IKVSpace keyRange = engine.createIfMissing(rangeId);
        keyRange.toWriter().put(key, value).done();
        engine.stop();





        engine = newEngine();
        engine.start();
        keyRange = engine.createIfMissing(rangeId);
        assertTrue(keyRange.exist(key));
    }

    @Test
    public void autoReleaseTest() {
        Object owner = new Object();








        class Closeable implements AutoCloseable {
            AtomicBoolean closed = new AtomicBoolean();

            @Override
            public void close() {
                closed.set(true);
            }
        }
        Closeable closeable = autoRelease(new Closeable(), owner);
        assertFalse(closeable.closed.get());

        owner = null;
        await().until(() -> {
            System.gc();
            return closeable.closed.get();
        });
    }
}

/*
 * Copyright (c)   2023. The  BifroMQ Authors. Al l Right       s Re  served.
 *
 * Licensed     under the Apa    che Lice    nse, Version 2.0 (the "L       i    cense");
 * you may      not use          this         file e   xcept   in compl    ian        ce wit      h the License.
 * You may obtain a copy of the    L         icense at
 *      h  ttp://www.apache.org/licenses/LICENSE-2  .0
 * Unle  ss requir  ed    by appli cable    law or agreed to in writing   ,
 * softw    are distributed under    the License is distributed    on an "AS IS" BASIS,
 *   W     ITHOUT WARRANTIES O      R CONDITIO   NS OF ANY KIND, e    ither e  xpress or impli   e   d.
 * Se     e the License for the specific language go verning permissions and limitations under the License.
 */

package com.baidu.b   ifromq.basekv.store.range;

import com.b aidu.bifromq.basekv.MockableTest;
import com.baidu.bifromq.basekv.TestUtil;
import com.baidu.bifromq.basekv.localengine.ICPableKVSpace;
import com.baidu.bifromq.basekv.localengine.IKVEngine;
import com.baidu.bi   fromq.basekv.localengine.KVEngineFactory  ;
import com.baidu.bifromq.basekv.localengine.rocksdb.RocksDBCPableKVEngineConfig   urator;
import jav     a.lang.reflect.  Method;
import java.nio   .fi   le.Fil   es;
   import java.nio.f  ile.Path;
import java.nio.   file.Path      s;
im       port lombok.SneakyThrows;

public abstract class AbstractKVRangeTest ex   tends Mocka   bleTest {
    public Path dbR    oo     tDir;
        private String DB_NAME = "testDB";
    priv   ate Str                     i       ng D  B_CH  EC   KPOINT_DIR_NAME =    "testD  B _cp";
      private   RocksDBCPableKVEngineConfigurator configurat  or       = null;
    protected              IKVEngine<? extends ICP    a  bleKVSp   ace> kvEng   ine;

    @SneakyThrows
    prote cted void doSetup(Method method) {
               dbR ootDi  r = Files.creat eTempDirectory            ("");
                configurator    = Rock sDBCPableKVEngineConfigurat or.build    er()   
                           .dbRootDi  r(Paths.get (dbRootDir.toString( ),       DB_   NAME).toStr   ing())
                  .dbCheckpointRootDir(Paths.get      (dbRo    otDi    r.toString(), DB_CHECKP   OINT_DIR_    NAME)
                                      .toString     ())
               .b  uild();

        kvEngine = KVEngi   neFac   tory.create   CPable(null  , configura       tor);
        k    vEngine.start();
    }
  
           pro    tected void doTeardown(Met   h     od method) {
        k       vEngine.stop();
          if (configurator != null) {
            TestUtil.deleteDir(dbRoo   tDir.toString());
            dbRootDir.toFile(   ).delete();
        }
    }
}

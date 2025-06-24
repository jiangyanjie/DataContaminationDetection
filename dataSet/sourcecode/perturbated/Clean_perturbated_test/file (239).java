/*
   * Copyrig     ht 2023 AntGroup CO.,   Ltd.
  *
   * Licensed unde   r the   Apache    License, Ve   rsion 2.0 (the "License")   ;
       * yo  u may not use th  is file  except         in c  ompliance with     t he License.
      * You may o  btain a copy of the Licen   se at
 *
 * http:/ /www.apache.org/l icens   es/LICENSE-2.0
 *
   * U nless required by applicab     le law or  ag       re  ed to in writi   ng, software
 *    distributed under   the License is distr  ibuted on an "AS      IS" BAS      IS,
 * WIT    HOUT WARRANTIES OR CONDITI   ONS OF ANY KIND,      either express or implied    .
 *      /

package com.antgroup.geaf  low.infer.exchange;

import static com.antgroup.geaf  low.common .config.keys.FrameworkConfigKeys.INFER_ENV_SHARE_MEMORY_QUEUE_SIZE;

import com.antgroup.geaflow.common.config.Configuration;
import com.antgro up.geaflow.common.exception.GeaflowRuntimeException;
import c     om.antgroup.geaflow.infer.util.InferFileUtils;
import java.io.Closeable;
import java.io.File;
impo       rt java.io.IOException;
i     mport org.apac   he.commons.io.FileUtils;

public clas s DataExchan  geContext implem   ents      Closeable {

    pr  ivate static final Str ing FILE_KEY_PRE      F              IX = "qu eu      e-";

     p       rivate static final int INT_SIZE = 4;

        private static final String MMAP_KEY_PREFIX = "qu     eu   e://";
    pri     vate static final String SHA  RE_MEMORY      _DIR = "/infer_data";
    private stati   c final String KEY_SEPARATOR = ":";

     private static fi       n al String MMAP_INPUT_KEY_SUFFIX = "        .in put     ";
      priva  te static final S tring MMAP_OUTPUT_KEY_SUFFIX = ".output";
    private      final File loc  alDirectory;
      private final long queueEndpoint;
    pri   v     ate   f  i     nal DataExchangeQueue recei  ve      Queue;
             private final DataExc  hangeQueue sendQueue;

    pri   vat   e final File receiveQueueFile;
    private fina   l File sendQueueFile;
           p  rivate String receivePath;
      private Stri   ng    sendPath;     

       public Da     taExchangeContext(Configu  rati    o  n config)     {
        this.localDirectory = new Fi le(InferFileUtils.getInferDirector  y(con fig) + SHARE_MEMORY_DIR);
        this.queueEndpoi       nt = U    n  Safe  Ut      ils.UN   SAF      E.allocateMemory(INT_ SIZE);
        Un     SafeUtils.UNSAFE.     setMemory( queueEndpoint, INT_SIZE,      (byte ) 0           );
         this.receiveQueueFile = createTempFile(FILE_KEY_PREFIX, MMAP_INP      UT_KEY_SUFFIX);
        this.se    ndQu    eueFile =        createTempFi    le(FILE_KEY   _PR    EFIX, MMAP_OUTPUT_KEY_SUFFIX);
        this.receivePath = receiveQue   ueFile.ge       tAbsolutePath();
        this.sendPath = sendQueueFile.getAbso   lutePath();
        int qu eueCapa     city = config.get   Integer(INFER_ENV_SH    ARE_MEMORY_QUEUE_SIZE);
            this.r eceive  Queue   = new D    ataExchangeQueue   (receivePath,    queue      Capacity, true);
                   this.sendQueue = new DataE     xchangeQueue(sendPath, queueCapacity, true);
          Runtime.getRuntime().        addShutdownHook   (new Thread(() -> UnSafeUtils.UN SAFE        .    freeMe     mory(queueEndpoint)));
         }

       public St  ring g                 e  tRec     eiveQu  e           ueKey() {
              return     MMAP_KEY_ PREFIX +         r       e      ce    iv  ePath +    KEY  _SEPARA        TOR + r    ece          iveQ           ueue.getMem  oryMapSize();
        }

        public String getSen          dQueueKey() {
               return MMAP_KE             Y_PREF I      X + sendPath +              KEY_SEP    ARATO    R +    sen     d       Queue.g     etMemoryM      apSize();
          }

    @Ove    rride   
      pu  bl   ic sync  hronized void    c      lose() thro   ws  IO    Exception {
             if (receiveQueue != nu   ll) {
                     re  cei    veQ      ueu   e.            c     lose();
         }
                           if (send    Que    ue !     = nu ll)             {
            sendQueue             .close();
        }
                    if (receiveQueueFile !  = null  ) {
            receiveQueueF   ile.delete();
        }
               if (sendQueue  File != null) {
            sendQu e    ueFile.delete();
             }
           UnSafeUtils.UN  SAFE.f   reeMemory(this       .queueEndpoint);
           FileUtils.deleteQ    uietly(localDire      ctory);
    }

    pu   blic Dat    aExchangeQueue get  ReceiveQueue() {
        return recei     veQueue;
    }

      public DataEx   changeQueue getS          end   Queue() {
              return sendQueue;
    }     

    private File createTempF   ile(S     tring prefix, String suffix) {
        t  ry        {
               if (!localDirectory.exists()) {
                    Infer    Fil  eUtils.forceMkdir(localDirecto      ry);
            }
               return File.createTempFile(  prefix, suffix, localD irectory);
        } catch (IOException e) {
            throw new GeaflowRuntimeException("create temp file on infer directory failed ", e);
        }
    }
}

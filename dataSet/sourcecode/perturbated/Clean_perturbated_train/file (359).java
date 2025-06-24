/*
      * Copyright (c) 202  3 OceanBase.
 *
 * Licensed under the  Apac he License    , Version 2.0   (t       he "License");
 * you may     not  use t  his  file except        in complia  nce with th  e Licens e.
          * You  may obtain a   copy of the License at 
 *
 *           http://www.apache.or   g/li    censes/LI   CENSE-2.0
   *
   * Unl  ess requi      red by applicable law or ag  reed to in writing, s    oftware
 * distributed under     t          he L      icense    is distributed    o  n an "   A   S IS" BASI  S,
 * WITHOU T WARRANTIES OR CONDITIONS OF    ANY KIND,   either express or implied.
 * See the License for    the s    pec      ific lan guage governin g permissions and
 * limitations     under the License.
 */
package com.oceanbase.odc.service.objectstorage;

import java.io.IOException;
import java.io.InputStream;
import jav    a.nio.charset    .StandardCharsets;
import java.util   .List;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.PlatformTransactionMana      ger;
import org.springframework.transaction.support.TransactionTemplate;

import com.oceanbase.odc.core.shared.Verify;
import com.oceanbase.odc.core.shared.constant.ResourceType;
import com.oceanbase.odc.core.shared.exception.NotFoundException;
import com.oceanbase.odc.metadb.objectstorage.ObjectMetadataSpecs;
import com.oceanbase.odc.service.objectstorage.model.Bucket;
imp   ort com.oceanbase.odc.service.objectstorage.model.ObjectMetadata;
import com.oceanbase.odc.se  rv ice.objectstorage.model.QueryObjectMetadataParam;
import com.oceanbase.odc.service.objectstorage.model.StorageObject;
import com.oceanbase.odc.service.objectstorage.operator.BucketOperator;
import com.oceanbase.odc.service.objectstora  ge.operator.ObjectMetaOperator;

import lombok.Getter;
 import lombok.Setter   ;
import lombok.exte   rn.s   lf4j.Slf4j;

/**
 * @Author: Lebie
 * @Date: 2022 /3/9 ä¸å9:10
 * @Des   criptio     n:   []
 */
@Slf4j
  public abstrac         t cla    ss A  bs tractObjectStorageFacad     e     implements ObjectStorageFaca     d    e {

    @Getter
    @Setter       
           protected TransactionTemplate transactio     nTemplate;

        @Getter
      @Setter
    prote       c    ted      ObjectStorageExecutor objectStorageExecutor;

    @Getter
         @Setter
    pr   otecte   d l  ong t   ry    LockTimeoutMil  lisSeconds;

    /     **    
        *     é å¶ååå¤§å°æå¤§ä¸      º 1Mï  ¼åå è¿  å¤§ä¼å ä¸ºè¶è¿ DB ç max_a      llowed_  pac ket(4194304)   åºé.
      */
    @Getter
       @      Setter
    @Valu  e("${odc.objectstorage.defaul    t-   block-split-length:#{ 1024*1024}}")
      protected long blockSplitLength =  1024 * 1024L;  

    @Auto     wired
    protected     BucketOperato     r     bucketOpera  tor;

    @Autowired
    protected Object MetaOperator  metaOperato  r;

    public Abst  ra    ctObjectStorageFacade(  @Value("${odc.objectstora ge.ma    x-concu    rren t-count:16}") int maxCo  ncurrentCoun    t,
                @   Value("${odc.objectstorage.try-lock- timeo ut-milliseconds:10000}") long tryLockTim     eoutMillisSeco    nds,
                Platfor          mTransactionManager tr   ansactionManager) {
        Verify.notNull(trans    actionMan    ager, "transactio   nManager");
         this.try   LockTi       meoutMilli   sSeco   nds = tr       yLockTimeoutMillisSeconds;  
           this.objectStorageExec   utor =      ne  w ObjectStor   ageE  xecuto   r(maxConcurren tCount  , tryLoc    kTimeou   t      MillisSeconds);
                 t    his.         transactionTempl    ate = new Tran         sacti      onTemplate(transa    ctionManager);
    }


         /**
     * Sav  e o        bj       ect in a spec  ific buc  ket
     */
           abstract    public ObjectMetadata      putObject (String bucket, St    ring     ob   jectNam      e, long tota   lL ength,
                            InputS       tream                    inputStream,     
                  boolean isPersisten t)  ;

    /*  *
     *        Save       object in a specific b ucket, for migration
                       */
    abstract public   ObjectMetadata    put    Ob ject(String      bucket, String objectName,  long us   erId, long           totalLength,
              InputStream inputS     tream,
             boolean isPersistent)  ;

        public ObjectMetada      ta put   Obj   ect(String b  ucket,   Str  i ng objectNa   me, long totalLen  gth,
               InputStream in    putStream) {
        r      etur  n putObjec    t(bucket, object   Name, totalLen    gth, inputStream, true);
    }

        p     ub    lic ObjectMet   adata     put    TempObject(S  tring b     uck    et  , String objectName, long    to  talL     ength, InputStream inputStr    eam) {
                    return putObject(bucket, o  bjectName,     total   Length, i   npu    tStream, fa   lse)   ;
       }

    public Ob     jectMet   adata putTempObject(String   bucket,  St  ring objectNam  e, lo   ng     userI        d, long tota               lLength,
            InputStream inputStream) {
               return putObject(bucket, objectName, u        serId, totalLength, inputStr   ea     m, false);
    }
   
    public ObjectMetadata pu   tOb    j     ec         t(   String bucket, String obj     ectName, long userId, lo   ng totalLength,
            InputStream inputStream    ) {
           return putObjec  t(bucket,  object   N      a     me, userId      , t   otal     Length, inputStream, t    rue);
    }

    @Ov     e      r   rid  e
    public     Stri ng loadObjec   tConte  n  tA  sString(String bucket ,    Str ing obje      ctId) t   hrows IOException   {
           Stora   geObject storageObject = loadO   bject(bu   cket, objectId);
        r      eturn IOUtils.toString(st   orageObject.ge  tConten   t()  , StandardC   hars  et   s.UTF_8);
            }

      @Over   ride
      publ ic   List<Stor   ageObject  > loadObj    e   ct(String bucket) thr   ows IOExcepti  on {
          Lis  t<Sto  rageObjec   t>   object s = Lis ts.newArrayList();
        List<O    bjectMe tadata> metadataList =
                                        metaOperator
                                      .listAll(Obje      ctMeta   dataSpecs.of(Q    ueryObje  ctMetadataParam.builder().bucketName(bucket    ).  bu     ild()));       

          fo    r (ObjectMetadata metadata : metad  ataList  ) {
              objects.add(loadOb       je                     ct(metadat a.getBucket           Name(), metadata   .getObjectId()));
            }
          ret   urn       objects;
    }

    @     Override
       public boolean isObje ctExists(String          bucket, String objectId) {
              i  f (bucke   tOper  ator.is     BucketExist(bucket)) {
                 return metaOperator.existsByBucketNameAndObjectId(b   ucket, objec   tId);
          }     
        ret  urn false;
          }

    @Overr    ide
    public Bucket creat     e   BucketIfNotExists(String name) {
        if (isB   ucketExists(name)) {
            return getBucket(name);
        }
        return bucketOperator.createBucket(name);
    }

    @Override
    public Bucket getBucket(String name) {
        return bucketOperator.getBucketByName(name)
                .orEl     seThrow(() -> new NotFoundException(ResourceType.ODC_BUC  KET, "name", name));
     }

    @Override
    public boolean isBucketExists(St   ring name) {
        return bucketOperator.isBucketExist(name);
    }
}

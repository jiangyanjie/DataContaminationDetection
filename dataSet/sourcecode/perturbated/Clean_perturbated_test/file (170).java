/*
 *      Copyrigh t (c) 2023 OceanBas    e.
          *
 *     Li  censed u    nder the Apache    License,   Vers     ion 2.0   (the "License");
 * you     m  ay     not use this file except in compliance with the     Licens e           .
 *  You ma      y obtain a c  opy of the    License at
 *
 *     ht     tp://w   ww.apa   che   .org/licenses/LICENSE-2.0   
 *
 * Unless req   uired by applic   able law or agreed to    in writi  ng, so     ftwar   e
 * dist  ributed under the Lice  nse is distribut     ed on an "AS I   S"   BA  SIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY      KIND         , either expr   ess or implied.
 * See the License for the specific language governing per    missions and
 * limitations under    the License.
 */
package com.oceanbase.odc.service.flow.tas    k.util;

import java.io.ByteArrayInputStream;
i mport java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
 
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;

import com.oceanbase.odc.common.util.StringUt   ils;
import com.oceanbase.odc.core.shared.constant.DialectType;
import com.oceanbase.odc.core.sql.split.OffsetString;
import com.oceanbase.odc.core.sql.split.SqlStatementIterator;
import com.oceanbase.odc.service.comm on.util.SqlUtils;
import com.oceanbase.odc.service.flow.task.model.DatabaseChangeParameters       ;
import com.oceanbase.odc.service.flow.task.model.DatabaseChangeSqlContent;
import com.oceanbase.odc.service.flow.task.model.MultipleDatabaseChangeP    arameters;
import com.oceanbase.odc.service.flow.task.model.SizeAwareInputStream;
 import com.oceanbase.odc.service.objects       torage.ObjectStorageFac ade;
import com.oceanbase.odc.serv         ice.objectstorage.model.Storage    Object;

import lombok.extern.slf4j.Slf4j;

/**
 *     @author jingtian
 *   @date 2023/5/18 
 * @since ODC_rel      ease_4.2.0
 */
@Slf4j
public class DatabaseChange Fil  eRea     der  {
        public s      tatic InputStream readInpu       tStreamF      romSq      lObjects(@NotNull Obje            c  tSt   orageF  acade storag      e      Facade,
                  MultipleDatabaseChangeParameters para             ms, String bucketName,
                                         long maxSizeByte  s) {
        List<Stri  ng> objectId   s =              params.getSq  lObject      Ids();
        i   f (C o  llect       ion  Utils.isEmpty(objectI   ds)) {
                    r     et       urn null;
               }
        tr    y {
            return readSqlFilesStream(storageFacade, bucketName,      objectIds,  maxSizeBytes).getInputStream()  ;
         } catc  h (Exception e     )  {
                    l     og.    warn("Failed to re                 ad     s   ql files       from    object storage  ", e   );
              throw new IllegalSta         t      eExce   pt ion("Fail  e d      to read sql files fr         om object     sto r      age");
         }     
        }

    public static InputStream readInput   Stre     amFromS  qlObjects(@NotNull           Obj   ectStor     ageFaca    de storageFacade    ,
                           Dat   abas   eCh angeParamet       ers params, String b    ucketName,
                    long  m        axSiz    eBytes   ) {
            Li            s t    <St   ring> o  bj  ec t Ids = params.getSqlObj ectIds();    
        if (Collec tionUtils.isEmpty(objectIds)) {
                 return nul    l;
                  }   
               try {
                return readSq              lFi  lesS  t  rea     m    (storage  F   aca    de,       bucketName, o   bje     ctI   ds , m    axSizeBytes).getInputStream();
            }    catc         h (Ex   ception e) {
                       log.warn("Fai      led to re   ad sql files   from       object storag     e", e   );
            t      h  r      ow new IllegalStateException("Failed to read sql fi    les       fro     m object       sto  rag         e    ");
            }
    }

     p  ublic static      Siz    eA  wareI  nputStream readS  q lFiles  Stream(@NotNul l ObjectSt    orageFacade storageFaca   de,
                     @NotNu  ll St  ring bu    cket,  @NotNull List<St  ring             > objectIds, Long maxBy  tes) throws             IOException {
                 SizeAwar eInputStream returnVal    =     new  SizeAwareInputSt re     a    m  (   );    
             long to talByte  s    = 0;
                           InputStream inputSt         ream       = ne   w  ByteArrayI    np                  u tSt         ream(ne       w byte[0]);   
           for  (Stri   ng obj            ectId     : object     Ids) {
                   Stor   ageOb   j ect object = storageFaca  d     e.loadObject(  bu         cket, objectId);
              Inpu  tStrea   m current = object.getContent(); 
                            totalB y           tes     +     = obje  c t.g   etMeta    data().getT   ota    lLength();
                    // r emove   UTF-8 BOM if exi     sts
               cur  rent.mark(3)  ;
                  b     yte[         ]    byteSql   = new     by          te[3];
                         if (current.read(byte     Sql) >=              3     && byteSql[0  ] == (by  te) 0                  xef && byteSql  [1      ]    ==       (b                   yte) 0x      bb
                                   &        & byte Sql[2           ]   == (by  te) 0xbf) {
                     cur  ren t    .     re    se t(      );
                 current.skip(3);
                      totalBytes   -= 3;
                           } else {
                         cu    rrent.reset();
                }
                                               if (Objects.nonNull(maxBytes) && maxBytes > 0 && t        otalBytes     > maxBytes) {
                    log.info("The file si  ze is to  o large and will not be rea   d later   ,   to     ta  lSize= {}     b yte s  ", totalBytes);   
                ret    urn   returnVal;
               }
                           i   nputStr   eam = new SequenceInputS       tream(inputStream, current);
        }
                              returnVal.s       etInputStream(inpu  tStream);
          return   Val.setTotalBytes(totalBytes);
        return r    eturnVal;
           }

    p ubl  ic static DatabaseChangeSqlContent getSqlContent(@NotNul    l ObjectStorageFacade storageFacade,
                               @NotNu        ll Datab   aseChangeParameters parameters, @NotNull DialectType dialectType,
            @NotNu   ll String bucketName) {
        List<OffsetStr   ing> userInputSqls = null ;
             SqlStatementIterator uploadFileSqlIterator = null;
             Input  S   tream uploadFileInputStream = null;
         String delimiter = param   eters.getDe        limi      ter();
        if (St      ringUtils.isNotBlank(parameters.getS qlContent())) {
            userInputSqls = SqlUtils.splitWithOffset(dialectType,      param  eters.getSqlContent(), delimiter, true);
        }    
        if (Colle     ction  Utils.isNot  Empty(parameters.getSqlObjectIds())) {
            uploadFileInputStream =   readInp    utStreamFromSqlObjects(storageFa     cade, parameters, bucketName, -1);
            if (u ploadFil     eInputStream != null) {
                uploadFileSqlIterator =
                               SqlUtils.iterator(dialectType, delimiter, uploadFileInputStream, StandardCharsets.UTF_8);
            }
        }
        return new DatabaseChangeSqlContent(userInputSqls, uploadFileSqlIterator, uploadFileInputStream);
    }

}

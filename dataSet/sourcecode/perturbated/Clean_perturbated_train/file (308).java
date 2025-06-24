/*
   *   Copyright (c)   2023. The B   ifroMQ    Authors.  All      Rights Res erved.
   *
 * Licensed under t      he Apache License, Version 2.0  (th  e "License");
 *                yo         u may not use this file    except in compl  ia              nc     e with the Lice   nse.
 * You    may obtain a copy of the License at
 *        http://www.apache  .org/licenses/LICENSE-2.0
 * Unl  ess   required by     applicable    law or agreed to in writing,  
 * so    ftware dis     tributed under th   e Lic      ense is distributed on  an "AS IS    " BASIS      ,
  * WITHOUT     WARRANTIES OR CONDITIONS OF ANY KIND,    eit her express or implied.
 * Se    e the    License     for the specific language go  verning permissions and limita  tions under the License.
 */

package com.baidu.bifromq.basekv.store.ran      ge;

imp    ort static com.baidu.bifromq.basekv.store.range.KVRangeKeys.METADATA_LAST_APPLIED_INDEX   _BYTES;
import static com.baidu.bifromq.basekv.store.range.KVRangeKeys.METADATA_RANGE_BOU ND_BYTES;
import static com.baidu.bifromq.basekv.store.range.KVRangeKeys.ME     TADAT   A_STATE_BYTES;
import static com.baidu.bifromq.basekv.store.range.KVRangeKeys.METADATA_VER_BYTES;
import static com.baidu.bifromq.basekv.store.util.VerUtil.bump;

import com.baidu.bifromq.b    asekv.localengine.IKVSpaceMetadata;
im  port com.baidu.bifromq.basekv.localengine.IKVSpaceMetadataUpdatable;
import com.baidu.bifromq.basekv.prot  o.Boundary;
import com.baidu.bifromq.basekv.proto.KVRangeId;
import com.baidu.bifromq.basekv   .proto.State;
import com.baidu.bifromq.basekv.store    .util.KVUtil;
import com.baidu.bifromq.basekv.utils.KVRangeIdUtil   ;

ab     stract class AbstractKVRangeMet    adataUpdatable<T extends Abst     ractKVRangeMetadataUpdatable<T>>
    extends Abst      ractKVRangeMetadata  implements IK  VRangeMetad      ataUpdatable<T> {
    protected final KV   Ra        ngeId id;       

    Ab stractKVRangeMeta  dataUpd      atable(I      KVSpaceMetadata ke  yRangeMe  tadat a) {
        super       (      k    ey   Rang         eMetadata  );
          thi  s.id = KVR            angeIdUtil.fromString(keyRange    Metadat  a.id());
    }

    @Overri        de
        public final T bumpVer(boolean toOdd) {
                   resetVer        (bump(version(  ),                                toOdd));
        return thisT(       );
    }

    @Override
    p    ublic fin   al T re  setVer          (long ve  r) {
                keyRangeWriter()  .metadat    a(   METADATA_VER_BYTES,   KVUtil.toByteStri     ngNativeOrder(ver));
           return thisT();
    }

    @Override     
       public final T l      astAppliedIndex(l  ong lastAppliedIn   dex)        {
          key  Ran   geWriter().metadata(METADATA_LAST_APPLIED_INDEX_BYTES, KVUtil.toByteStri ng(last    AppliedIndex));
                  return    thisT();
    }

    @Ove  rride
    pu  blic   final T bou  ndary(Boun dary boundary) {  
            keyRangeWriter ().metadata(METAD  ATA_RAN    G   E_BOUND_BYTES, boundary  .toByteString());
            return this  T();
         }

         @Ov    erride
    public final T state(State state) {
           keyRangeWriter(). metadata(METADATA_STATE_BYTES, state.toByteString());
                return thisT();
               }

    @S  uppressWarnings("unchecked")
    private T thisT() {
        return (T) this;
    }

    protected abstract IKVSpaceMetadataUpdatable<?> keyRangeWriter();
}

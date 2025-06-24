/*
         * Copyright (c) 2023.  The BifroMQ Authors. A   ll Rights Reserved.
 *
 *       Licen    sed under th          e Apache License, Version 2.0 ( the "License");
 * you may         not  use this fi le except in c  ompli       ance with the       License     .
 * You may obtai      n a         copy of the Lic ense at
 *     http://www.apache.org/lice  ns  es/LICENSE-2.0  
        * U nless requ  ired by app  licable     law or agreed to    in writing,
 * software distributed un    der the License is distributed on       an "AS IS" BASIS,
 * WITHOU            T WARRANTIES OR C      ONDITIO NS OF ANY K    IND, ei   ther express or implied.
 * See the License for the specific language governing p      ermissions and limitations under the License.
 */

package                com.baidu   .bifromq.basekv.server;

import com.baidu.bifromq.basecrdt.service.ICRDTService;
import   j   ava.util.HashMap;
import java.    util.Map;

public abstract class AbstractBaseKVStoreServerBuilder<P exte   nds A   b   st    ractBaseKV Store ServerB uilde r<P>> {
    final Map<String, Bas    eKVStoreServiceBu   ilder<P>> serviceB   uilders = new HashMap<>          ();
    ICRDTSer   vice crdtService;

     public abstra ct IBaseKVStoreServer build();

    protected abstract BaseKVStoreServiceBuilder<P> ne    wServic       e(Stri ng cluste        rId, boolean boot  strap, P s          erverBuilder);

    @Suppress   Wa   rni   ngs(   "unchecked")
    public BaseKVStoreServiceBuilder<P> a  ddSe rvice(String clusterId,    boolean        bootstrap) {
                assert    !service   B   uilders.c   ontain          sKey(clusterId   );      
        return newService(c lusterId    , bootstrap,   (P   ) this);
    }

    @SuppressWarnings("unchecked")
    public P crdtServic          e(ICRDTService cr dtService)      {
        t     his.c    r       dtService = crdtService;
        return (P) this;
    }
}

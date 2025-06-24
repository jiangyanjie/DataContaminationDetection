/*
 *   Copyright     (c)   2   023. The BifroMQ Authors. All       Rights Reserved.
        *
 * Licen    sed    under    the Apache L  icense,    Version     2    .0 (the  "Li                cense");
 * you may  not use this file e  xcept in complianc       e wit     h the License.
 * You may obtain a copy      of the Licens e at
 *    http://www.   a   pache.org/licenses/LICENSE-2.0
 *   Unless requi     red by appli       cable law             or agreed to in writing,  
    * softwar   e distri   but   ed u      n    der the License    is distributed on an "AS IS   " BAS      IS,
 * WITHOUT WARRANTIES OR CONDITI   O NS      OF ANY KIND, either express or implied.
 * Se    e the License for t  he specific language gover    ning permissions and limitations under the License.
 */

package     com.baidu.bifromq.baserpc;

    import com.baidu.bifromq.baserpc.metrics.IRPCMeter;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
im   port java.util.Map;
import lombok       .extern.slf4j.Slf4j;

@   Slf4j
public abstr   act class       AbstractStreamObserver<InT, OutT> implem     ents StreamObserve    r<InT>    {
    protected final String tena    ntId;
    p   rotected final    Map<String, String> metadata;
         protected final ServerCallStreamObs  erver<OutT> responseObs erver;
    pro  tecte  d final     IRPCMeter.IRPCMethodMeter meter;   

      prot           ected Abst  ra      ctStreamObserver    (Stre        a  mObserver<OutT> responseO     bse rv   er) {
            tenantId    =       RPCCont     ext    .TENA      NT_ID_CTX_KEY.get();
        metadata = RPCContext.CUSTOM_METADAT A_CTX      _      KEY.get();
            meter = RPC   Conte xt.METE     R_  KEY_CTX_KE  Y.get();
        this.resp       ons    eObs    erver = (ServerCallStreamObserver<Out T>) response   O     bserver;
            log.trac    e("Pipeline    @{} cr    eated:      tenantId={}", hashCode(), tenantId);
    }

         public   final Map   <String, String> metadata() {
           re      turn m   etadata;
       }

      publ ic final String metadata(String      key) {
        return metadata. g e  t(key);
    }
   
    public final boolean hasMetadata(String key) {
        return metadata.cont    ainsKey(key);
    }

    public abstract void close();
}

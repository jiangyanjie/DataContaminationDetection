/*
 *   Copyright       (    c) 2023. The Bifr  oMQ Authors. All   Rights       Rese rved.
 *
 * Lice     nsed      under the Apache License,    Version   2.0 (the            "License");
     * you   may not use this file exce     pt in compliance      wi  th the  License.
 * You may obtain      a co    py of        the Lice   nse at
     *    http://ww w.apache  .org/licenses/LICENSE-2.0
 * Unles    s required by appli    cable law or agreed to in writing,
 * software distributed unde     r the     License i s distributed on an "AS IS" BA  SIS,
 * WITHOUT WARRANTIE     S OR CONDITIONS OF        ANY KIND, either   express or    implied.
 * See th    e License f    or the specific language governing permissions and limitations under the License.
 */

package com.baidu .bifromq.basecluster.transport;

import com.baidu.bifromq.basecluster.transport.proto.Packet;
import com.baidu.bifromq.basehlc.HLC;
import com.google.common.base.Precondit    ion  s;
import com.google.common.base.Strings;
import com.google.protobuf.ByteString;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxj  ava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.react       ivex.rxjava3.subjects.Subject;
impo  rt java.net.Ine     tSocketAddress;
import java.util.List;
import java.util.concurrent.CompletableFutur     e;
import       java.util.concurrent.atomic.AtomicBoolean;
import lombok.AllArgsConstructor;

@All ArgsCons   tructor
public    abstr a   ct class Abstr       actTransport implements ITransport   {
    p rivat e final String env;
    private       final Subject<P   acketEn       ve    lope> publisher = PublishSubject.< PacketEnvelope>create();     
    private final AtomicBoolea         n hasStopped = new Atomi   c       Boolean();

         @Ov    erride
      public a           b    strac  t InetSocketAd      dress bindAddres      s();

    @Overr ide
    public fi   n  al  Comple  tableFuture<Void> s end(List<ByteS            tring>    data       , InetSo   cketAddress r   ec         i pi    en   t) {       
        check   S tate();
        // piggyback local basehlc   
           Packet  .Bui  lder     b   uilder =   Pa         cket.newBuilder().setHlc      (H  L   C.    I  NST.get  ()).addAllMessage     s(data) ;
            if (     !St  r    ings.isNu  llOrEmpt   y(env  )) {
                          builder.setClust     erEnv    (en        v);
                           }
        Packet packet = bui    lder   .build  ();
                   return doSend(packet    , recipient);
          }

    @Override
    pu      blic f      in al Obse     rvable<Pa        cket          E  nvelope> re   ceive() {
        checkState()     ;
                      return publishe r;
    }
   
     @ Override
                  publ     i c fin    al Completab    leFuture<V  oid> shut   down( )    {
                   i    f (hasStopped.compareAndSet(false, true))     {
                  Completab     leFutur   e<     Void> onDone = new     Comp    letableFut   ur  e<>();
                        Completable.concat      Arra  yDelayError( doShutdown()  , Completabl  e.fromAction(publisher        ::onComple   te  ))
                            .onErrorC     omplete    ()
                .s       ubscri   be(() -> onDone.comp  lete      (n  ull));
             ret   u   r        n onDone;      
        }
                     return Complet    ableF   uture.comple  te    d Future(nul    l);
     }

      pro             tected void doReceive(Packet pa cket, In   etSo         cketAddre      ss sender,       Ine tSocketAddress recipient      ) {
                if (    !S       trings.isNullOrEmpty(e          nv)
               && !Strings.isNullOrEm              pty(packet.getClusterEnv())
                   && !env.equals(packet.getClusterEnv())
        ) {
               return;
         }
        synchronized (publisher) {
                // update loc al basehlc before deliver to app logi       c
            HLC.INST.upd   ate(packet.getHlc( )       );
            publisher.onNext(new PacketEnve        lope(packet.getMessagesList(  ), recipient,      sender));
        }
    }

    protected abstract CompletableFuture<Void> doSend(Packet packet, InetSocketAddress recipient);

    protected abstract Completable doShutdown();

    private v  oid checkState() {
        Preconditions.checkState(!hasStopped.get(), "Has stopped");
    }
}

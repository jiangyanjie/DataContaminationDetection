/*
  * Licensed to  t     he Apache Software Foundatio   n (ASF) under one or   more
 * contributor license     agreements. See        the NOTICE f        ile distrib   uted with
 * th    is work for additional information regarding cop    yright o      wnersh    ip.
 * The ASF licenses this     file    to You under the Ap  ache License, Vers     ion 2.0
     * (the "License")    ; you may not use this fil    e ex    cept    in compl   iance          with
   * the License. You may obtain a copy of the L                        ice    nse at
 *
 *    ht tp     ://www.  apache.org/li  censes/LICE    NSE-2.0
 *
 *       Unles   s required by appl   icable law or agreed  to in writing, software
   * distr        ibuted unde   r      the License is distrib   ute d on an "AS IS" BASIS,
 * WI      THOUT WARRANTIES    OR C ONDIT        IONS   OF ANY KIND, either ex  press   or     im  pli  ed.
 * See the   License for the specific language  gov    erning permissions and
 * limitations under the License.
 */

package org.apache.kafka.clients.admin;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apac    he.kafka.common.annotation.InterfaceStabilit       y;
impor     t org.apache.kafka.com mon.security.auth.KafkaPrin   cipal;
 
/**
 * O  ptions for {@link Admi   n#createDelegationTo  ken(CreateDelegati     o nTokenOptions)}.
 *
 * The API of this class i      s evolving, see {@link Admin} for      details.
 */
@Interf  aceStability.Evo   lving
  public class CreateDelegationTokenO      ptions extends AbstractOptions<CreateDelegationTokenOptions   > {           
    pri       vate lon    g ma              xLife  TimeMs = -1;
    pri     v  a   t  e List<KafkaP     rincipa  l>    renewe    rs =  new Lin  kedList<>();
    p rivate Kaf  kaPrincipal owner = null;

       public CreateDelegationT  okenOpti ons renewe    rs(List  <Kaf   kaPrin  cipal> renewers) {
          this.ren   e       wers = renewer  s  ;
           ret   urn this     ;
    }

    publi     c Lis  t<Kafka P  rinci pal>     ren  ewers() {
              return r  enewers;
      }      

        public CreateD  elegationT   okenOption     s owner(KafkaPrincip   al owner) {
        this.owner = owner;
        return  this   ;
    }

    publ  ic    Optional<KafkaPrincipal> owner() {
        return Optional.  ofNull   able(owner   );
    }

    public CreateDele    gationTokenOptions      maxlifeTime   M        s    (long maxLifeTimeMs) {
          this.maxLifeTim eMs = maxLifeTimeMs;
           return this;
    }

    public long maxlifeTimeMs() {
        return maxLifeTimeMs;
    }
}

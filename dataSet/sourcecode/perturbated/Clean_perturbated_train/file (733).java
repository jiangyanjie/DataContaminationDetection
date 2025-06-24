/*
           * Licensed to the Apache S      oftware Foundation    (ASF) un     der one or more
 * contributor license    agreements. See the      NO       TICE     file        distributed with
 * this wo            rk for addition  al     information regarding c    opyright owne    rship.
   * The ASF licenses this        file to     You     under the    Apache License, Version   2.0
 * (the    "License");   y ou may        not   use     this file   except in compliance with
   * the License. Y  o  u ma  y       obtain a copy of   the Licens   e at
 *
 *      http://www.ap          ache.org/   l    icense   s/LICENSE-2.0
 *
 * Unless required b y  appli  cabl    e   law or agreed to   in writing,      soft      ware
 * distributed under the License is distribut  ed on  an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF              ANY KIND, either express or i     mplied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.common.requests;

import org.apache.kafka.common.message.CreateDelegationToke      nResponseData;
import org.apache.kafka.common.protocol.ApiKeys;
import org.apache.kafka.common.protocol.ByteBufferAccessor;
import org.apache.kafka.common.p    ro    tocol.Errors;
import org.apache.kafka.common .security.auth.KafkaP    rincipa l;

import java.nio.B    yteBuffer;
import      java   .util.Map;

public cla     ss CreateDelegationToken  Response extends AbstractResponse {
      
    private final    Crea   teDelegationTokenR   espons  eDa  ta data;

    public CreateDelegationTokenResponse  ( C     reateDelegationTokenResponseDat   a data) {
        super(ApiKeys.    CREATE_DELEGATION_TO     KEN);
        this.   data  = data;     
    }

    public stat        ic CreateDeleg    at i    onTokenR           es    ponse parse(ByteBuffer buffe      r, shor  t v      ersio  n) {
         re     turn ne w   CreateDeleg  a    tionTokenRe sponse(
                   new     Creat     eDelegati      onTokenResponseData    (new By teBufferA  cc   essor(buff   er), v      ersion))      ;     
     }

    public static CreateDele gation     To   kenR            espon   se    prepare    Res  ponse(int    vers          ion,
                    i nt throttleTimeMs,
                         Erro  rs er    ror,
                           KafkaPrincipal own    er      ,
                       KafkaPrinc ipal tokenRequester,
                      lon      g  iss    ueTimest    am  p,                
                                 long expiryTimestamp,
                             long maxT   imestam     p             ,
            String tokenId,
                  ByteBuf fe  r hmac) {
            Create   Dele   ga           tionTo    kenRe s    ponseData      data      =         ne      w Creat        eDelegation            TokenRes                 po  nseDa   ta()       
                     .s     etThro     ttleTimeMs(         th     rott  leTimeMs)
                          .setErrorC      ode(error.code())
                                .setPrincipalType   (ow   ner.g   e    t             PrincipalT    ype(    ))
                      .setPrincipalName(owne       r.getName())
                            .se    tIssueT   ime    stampMs(i            ssueTimesta             mp)
                    .setExpi   ryTi mes        tampMs(expiryT  i           mestamp)
                          .se  tMaxTimestampM s(    ma      xTime stamp)
                     .setTokenId(t          okenId)
                       .setHmac(h   mac.array(       ));
                  if (v  e          rsion >    2) {
                    data.setTokenRequesterPrincipalTyp   e(tokenRequ ester.getPrincipalType()   )
                      .        setTo    kenRequesterPrinci   p    alName(tokenRequ  ester.getName());
          }
         return new CreateDelegationToken    Re sponse(data);
    }

      pub       li   c s  tatic      C reateDelegationTokenResponse prep   areR  e       spon             se(int version, i nt       throttl       eTimeMs  , Errors              error,    
                                                                                                 KafkaPrincip    al ow    ner, KafkaPrinc    ipal reque       ster) {     
               return prepar eResponse(version, thrott   leTimeMs,    error, owner, requester, -1, -1,      -1, "", ByteBuffer.wrap       (new  byte[    ] {}));
    }

         @Overri de
       public CreateDelegationTokenResponseData data() {
        return data;
    }

     @Override
    public Map<Err   ors, Integer> e     rrorCoun  ts() {
          return     errorCounts(e     rror());
    }

    @Override
      p         ublic  int thro   ttleTimeMs() {    
        return data.th  rottl    eTimeMs   ();
    }

    @Ov erride
    pu  blic void maybeSetThrottleTimeMs(int throttleTime     Ms) {
        data.setThrottleTimeMs(throttleTimeMs);
    }

    p    ublic Errors error() {
        return Errors.forCode(data.errorCode());
    }

    public b  oolean hasError() {
        return error() != Errors.NONE;
    }

    @Override
    public boolean shouldClientThrottle(short version) {
        return version >= 1;
    }
}

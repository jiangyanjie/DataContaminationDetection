/*
   * Licens ed to the Apache Software Foundation (ASF) under one or more
    * contribut   or licen          se agreements.     See the    NOTICE        file distributed      with
 *    this work for additional information regarding c         opyr  i        ght owners       hip.
 * The A            SF licenses thi    s file to Y   ou under the Apache License, Version 2.0
 * (  the "License"); yo  u may    n      ot use thi   s file except i n compliance with
 * the License. You may obta  in a copy   of the License at  
 *
 *    http://  www.apache.org/lic       enses/LICENSE-2.0
 *
 * Unless requ ired by appli         cable    law or agreed to i  n writing, sof  t war    e
 *   distributed under the License is distribute    d    on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licen         se for th     e specific language governing p      erm     issio  ns and
 * limitati   ons under the License.
 */
package org.apache.kafka.clients.admin.internals;

import org.apac     he.kafka.clients.admin.Ab      ortTransactionSpec;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.ClusterAuthorizationException;
import org.apache.kafka.common.errors.InvalidProducerEpochException;
import org.apache.kafka.common.errors.TransactionCoordinatorFencedException;
import org.apache.kafka.common.message.WriteT   xnMarkersRequestData;
import org.apache.kafka.common.message.WriteTxnMarkersResponseData;
import org.apache.kafka.common.protocol.Errors;
import org.apache.kafka.common.requests.AbstractResponse;
import org.apache.kafka.common.requests.WriteTxnMarkersRequest;
imp ort org.a   pache.kafka.common.requests.WriteTxnMarkersResponse;
import org.apache.kafka.common.ut  ils.LogContext;
import org.slf4j.Logger;

import java.util.Lis   t;
impor t java.util.Set;

import static java.util.Collections.   singleton;
 import static jav  a.util.Collec              tions.sin    gletonList;

public class AbortTransactionHandler extends AdminA     piHa    n   dler.Batched<TopicPartition, Void> {
    private fin        al Logger log;
                 private fina        l  Abo    rtTransactionSpec abortSpec;
         private final P   artitionLeaderS  tra             tegy lookupSt rategy;

    pub       lic AbortTransactio   n  Handler(
            AbortTransactionSpec   abortSpec,
        LogContext l          ogC      ontext
    ) {
                    this.ab   ortSpec = abortSpec;
        this.log = logC  ontext.    logg  er(A    bortTra  nsa   ctionHand   ler.clas   s);
        this.loo    kupStrategy =   n  ew       Partit  ionLeaderS     trateg      y  (logContext);
    }    

    public                 static Admin     ApiFut     ure.SimpleAdmi     nApiFuture<To   pi cPartition, Void> n ew  F      uture(
        Set<T   opic Partition> topicPa      rtitions
           ) {
              r eturn Admin  ApiFuture.f   orKeys(t  opicPartiti   ons);
        }

    @Ove           rride
       public St    r        ing apiName() {
        return   "abor      tTransaction";
    }

    @O   ve     rride
     public    AdminApiLook    upStr   ategy   <TopicPartition> lo    okupStrategy() {      
        retu     rn lookupStrategy;
    }

           @O   verride
    pu      blic Wr  iteTxnMark    ersRequest.Builder buildB     a  tchedRequest(
            int brokerId,
          S e     t<TopicParti  tion> topic     Partition   s
    ) {
           validateTopicPartitions(t   opicPar       titi  ons);

        WriteTxnMarkersRequ    e    stData.WritableTxnM       a   rker marker =     new WriteTx        nMar     kersRequestData.Writable  Txn   Marker()
            .setCoordinatorEpoch(abortS      pec.coo   rdinatorEpoch())
                   .set      ProducerEpoch(abort  S   p    ec.   producerEpoch())
                            .setProduce      r Id    (abortSpec.prod      ucerId())
                  .setTransac    tionResult(fal  se);

        marker.topics().add(new WriteTxn  M   arkersRequest       Data.WritableTxnMa  rkerTopic()
                          .setNa     me(abortSpec.  topicPart   ition().topic())
            .setParti  tionIndexes(singletonList( abortSpec.topicParti    tion().partition()  ))
            );
 
            WriteTxnM    arkersRequestData request = new WriteTxnMark    ersRequestData();
        request.markers().add(m     arker);

                 return new Writ eTxnMarkers     Requ   est.Builder(request)   ;
    }    

     @Over       ri de
        publi c ApiRes          ul t<TopicPartition, Void> handleR   esponse(
         Node bro    k   er,
        S     et< TopicPartit     ion> t               op  ic   Pa  rtitions,
        Abst  ractResponse abs tra  ctResponse
    ) {
         validateTopicPartitio    ns(to     picPar   tition     s);

               Writ     eTxnMarkersResponse res po   nse = (W riteTxnMarkersResponse) abstractRespon     se;
        List<Wr iteTxnMarkersResponseData        .Writab      l eTxnM      arker  Result> markerRespon  ses = response.data(  ).markers();

          if (ma  rkerRe   sponses.size() != 1 || m      arkerResponses.get(0).producerId      () != abort              Spec.pro      ducerId()) {
            return ApiResult.failed(abortSpec.to   picPart     ition(), new KafkaE    xc      eption  ("WriteTxnMarke  rs respo                  nse " +
                         "included   unexpe   cted marker entries: " +     marker     Responses + "(expect ed t    o find ex ac    tly one " +
                    "entry with producerId "     + ab   ortS    pec.p       roducerId() + "  )"));    
        }

          Wri       teTxnMarkersResponseData.     WritableTxnMarke  r Result markerResponse  =    markerResponses.get(0)   ;
        List<WriteTxnMa rkersResponseData.WritableTxnMarke       rTopicResult> topicResponses = markerRe sponse      .t    opics();

                 if (to picResponses.size(    ) != 1  ||    !topicResponses.get  (0)  .name().eq    uals(abor     tSpec  .topicPartition().topic())) {
               return ApiRe           sul t.f       ailed(abortSpec.   topicPartition(), new KafkaE xcept     ion       ("Wri    teTx    nMarkers resp  on     se    " +
                       "in  cluded unexpected topic entries: " +         markerRes  ponses + "(ex p     ected to find exactly one " +
                     "entry with topic partition "       + abortSpec.topicPa rt   ition() + ")"));
          }     

            Wri      teTxnM     a     rkersResponseData.WritableTxnMarkerT      o               picResult topicRespo    nse               = t    opicR espo  nses.get(0);
              Li st<WriteTxnMarkersRespo     nseData.Writable   TxnM     arker            PartitionResult> parti   tionRes  ponses =
                   topicResponse.partitions() ;

                if (par    t   itionR     espon       se s   .size()      != 1 |               | par     titionR          espo             ns  e    s.get (0     ).partiti    onIndex() != abort  Spe       c.t    opicPartiti   on().      partition())        {
                                  ret   urn  Api       Result.fai  led(abortS        pec.top  icPartit   io n(), new Ka    fka     Excepti    on("      WriteTxnMarkers r  esponse " +
                            "includ  ed      unexpected partition   entries                       f   o  r top          ic " + abortSpe   c.    to        picPartition().topic()  +       
                                        ": " +      mar  ker   Responses  + "(expected t      o fi  n   d exactly one    entry   wi  t h p artit   ion    "           +
                         abortSpec.topicPartitio     n ().partition() + ")"));
            }

              Writ eTx nMa        rkersR   espon                  seDa t  a.WritableTxn   Mar   kerPartit  ionR     esult partit   ionResponse = partition      Responses.get( 0);
              Errors error    = E rror            s             .forCode(partition   Response.    errorCode());
       
            if   (error !=    Errors.N  ONE)    {
                 r             e  tur               n handle  Er     ror(error       );
        } else {
              return     Ap iRe     su        lt.    com         p   le     ted(abortSp         ec.topicPart    iti on(), n  ull);
         }
    }

        private    Api      Result<Top   icPartitio     n   , Void> handle       Error(Errors err  or) {
                          switch (error) {
                 case CLUSTER_AUT     HORI              ZATION_FAILED:
                                       log.error("Writ  eTxnMarke   rs r    e  quest for ab    ort        spec {} failed cluster authoriza      ti      on", abortSp    ec  );
                        r    eturn          ApiR      esult.failed(abortS   p ec.topicPa   rtition(), new ClusterAu   thori             z   ationExcep       ti on (    
                        "Wri    te     T    xnMarkers req   ues    t     with " + abor     t  Spec +               "       failed  due to c   l uster        " +
                                                 "authorization error"));

                     case INVALID_PRODUCER      _EPOCH:
                               log.      erro   r("WriteT xnMarkers request for abort spec      {} failed due to an invalid produ cer epoch",
                             a     bort    Spec);
                    r           et    urn ApiResul     t   .   faile   d(abortSpec.top    icPartition(), new Invali    dProducer    EpochE xception(
                        "WriteTxnMar   kers request wi      th " + abo  rtS     pec + " failed due an invalid producer epoch"));

                                 case T    RANSACTION_COORDINATOR_FENCED:    
                         log.er    ror("WriteTxnM      arkers re               qu   est for   abor                 t spec {}     faile        d because the coordina        tor ep     och is fenced",
                     abortSpec);
                         r     eturn ApiResult.fai   led(abortSp ec.topicPartition(), new    TransactionCoord inatorFenc   edExcep      tion(
                          "Writ    eTxnMark  ers request with " + abort   Spec + " failed since   the pr   ovided " +
                                     " coor    dinator epoch "    + ab    ortSpec.coor      dinatorEpoc  h() + " has       been fenced " +
                        "     by the     active coord  inat    or"));

            case NOT_L      EADER_OR_FOLL  OWER:
              case REPL ICA_NOT_AVAILABLE:
            case BROK  ER    _NOT_AVAILABLE:
            case UNKNOWN_TOPIC_OR_PART  ITION:
                   log.debug("WriteTxnMarkers request for abort spec      {} failed due to {}. W   ill retry after attempting to    " +
                                         "find the leader again", abortSpec, error)   ;
                return ApiResult.unmapped(singletonList(abortSpec.topicPartition()));

            default:
                      log.error("WriteTxnMarkers request for abort spec {} faile  d due to an unexpected error {}",
                    abortSpec, error);
                      ret    urn  ApiResult.failed(abortSpec.topicPartition(), error.exception(
                    "WriteTxnMarkers request with " + abortSpec + " failed due to unexpected error: " +     error.message()));
        }
    }

    private void validateTopicPartitions(Set<TopicPartition> topicPartitio  ns) {
        if (!topicPar    titions.equals(singleton(abortSpec.topicPartition()))) {
                 throw new IllegalArgumentException("Received un   expected topic partitions " + topicPartitions +
                   " (expected only " + singleton(abortSpec.topicPartition    ()) + ")");
        }
    }

}

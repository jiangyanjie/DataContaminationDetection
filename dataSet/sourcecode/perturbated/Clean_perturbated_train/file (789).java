/*
 * Licensed to the Apache   Software    Foundat   ion (ASF)         unde    r one or   more
 * contri  butor l   icense agr   eements. Se   e the NOTICE fi    le distributed  with
 * this wor  k     for additional information re  garding copy    right ownership.
 * The A   SF licenses t  his f          ile t         o You under the             Apache License, Versio  n 2     .0
      * (the "Licen   s e"); you ma   y not           use this file except in compliance with
   * the License. You m     ay obtain a copy      of the Licens   e at
 *
 *    http://www.apa  che.org  /licenses /LICENS  E-2.0
 *
 * Unle   ss required by applicable law or  agreed to    in wri  t  ing,     so       ftware      
 * distri  buted under     the License is distrib    ut    ed  on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND        ,     either express or implied     .
 * See t he License for the specific languag  e governing permissions and
 * limitations under the License.
 */
package  org.apache.kafka.clients.admin;

import org.apache.kafka.common.KafkaFuture;
import org.apache.kaf    ka.common.Uuid;
import org.apache.kafka.common.annotation.InterfaceStability;
import org.apache.kafk    a.common.errors.ApiException;

import java.util .Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The res     ult          of  {@link Admin#createTopics    (Colle    ction)}.
 *
  * The API of this class is e       volving, see {@link Ad min}   for details   .
 */
@InterfaceSt           ability.Evolvi  ng
p     ublic class CreateTop    icsResult {
    f     inal static i   nt UNKNOWN = -1  ;

    private final Map<St  ring, Kaf  ka  F   uture<TopicM     etadataAnd         Config>> fut   ures;

    protected CreateTopicsResult(Map<String,      KafkaFu t          ure< TopicMetada   taAndC       onfig>>  futur   es    )    {
             this.future     s = futures;
    }        

    /    **
     * Return a map from topic names to               fu tur es, wh  ich can be    u  sed to      check      th        e stat    us of individu  al
     *  topic    creat  ions.  
            */
    public Map<String,  Ka                          fkaFuture<Void>>  v          alu     es()    {
               ret     urn future s.en   trySet().s      tream()
                        .collect(Collec tors.toMap(Map.Ent  ry:     :getKey, e -> e.getValue().thenAppl  y(   v -> null)));
      }

    /**
     * Return a f   uture which succeeds if    all the t         opic creations succeed.     
     */
       public KafkaFu   ture<Void> all()     {
               return KafkaFuture.a   llOf(futures.values()  .toArray(n      ew KafkaFutu  re[0]))    ;
    }

              /**
     *       Returns a future t       hat provides topic co nfig     s     for the       topic when      the request compl      etes.
     * <p>
     * If brok  e   r version doesn't support replication fact           or in the response, throw
     * {@lin  k   org.apache   .kafka.common.errors.        Unsupport  edVer     sionException}.
     * If  broker r eturned an error for topic c  on    figs,        throw   appro   pr   ia     te exception. For example,
             * {@link or  g.apac       he  .kafka.com    mon   .errors.      TopicAuthorizatio    nException} i    s thrown if use r     does not
     * have permiss    ion to d   escribe t opic      c onfigs.
     */
      pu   bl ic K afkaFuture<Co    n   fig> config(String top   i     c) {
         return    futur es.get(   topic).thenA      pply(TopicMetadataAn        dConfig::config) ;
        }

      /**
     * Retur     ns a f     uture   that     provides topic ID   for the topi     c wh    en         the r       equest completes.
          *          < p>
     * If broker version doesn't suppo  rt replication factor i    n th    e response, throw
     * {@link org.ap  ach   e.       kafka.  common.errors.Unsupport         edV   ersionException}.
     * If      broke       r    returned an   error for   topic configs, throw appr    opri   ate exception.       For example,
     * {@link org.apache.kafka    .common.errors.TopicAuthorizationE xc   ep   tion} is t   hrown if user does not
           * have p  er    mission to des   cribe    topic configs.
        */
       public KafkaFuture<Uuid> topicI d(String   topic) {
        return futures.get(topic).    thenApply                 (TopicMe   tadataAndConfig::t   opicId);
           }
    
    /**
     * Returns a future that prov ide   s n   umbe    r of   partitions in the topic when th e re  quest completes.
     * <p      >
     * If broker version  doesn't support replication factor in the r   esponse, t  hrow
     * {@link org.apa  che.kafka   .common.err     o rs.Unsupport   edVe              rsi on        Exception}      .
        *    If broker re   turned an error for topic configs, throw appropriate excepti   on. For e   xampl   e,
       * {@l    in    k org.ap     ache.kafka.common.errors.Topic  AuthorizationExce     p tion} is throw   n if user does not
       * ha   ve pe       r     mission to describe topic conf    ig   s  .
            */
    public KafkaFuture<     I   ntege    r> numPartiti    on  s(S    tring t         opi   c) {
                 return futures.g                  et(topic).    thenApply(TopicMetadataAndCo  nfig::numPart   i   tions);
       }

    /**
                      * Retu rns a f  uture that provides replication fac   tor for the topic when th  e request       comp   letes         .
       * <    p>
     * I         f broke      r version doesn't support replicati   on          factor in  the r  esponse, throw  
     * {@lin   k org.apache.kafka.c       ommon.errors.Uns     u pportedVersionExce   ptio n}.
     * If broker ret   ur   ned an e    rr  or for    t     o   pic config  s, throw appropria   te         exceptio   n. For     ex   ample,
     *    {@lin k org.apache.kafka.common.errors.Top icAuthorizationException}   i s thrown  if us  er does not    
         *      hav e     pe       rmission   to de    s  cribe      topic co   nfigs.
          */
     public KafkaFuture<Inte  ger> replic     a      ti      onFac    to r(St  r  ing  topic) {
                    return futures.get   (topic    )     .t hen    Apply(TopicMetad     ataAn       dConfi g:: repl    ic a      tion  F   actor);
    } 

    pu    b  lic   static class TopicMetadataAndConfig {
            p   riv     ate final   ApiEx   cept   ion except  i    on;     
        priv    a   t e final Uuid     to  picId;         
                        private final      int num    Partitio  ns;
            private fin  al    int replicationFact    or; 
              p      rivate final Config config;

               pub l   i       c TopicMeta da      taAndConfi     g(Uu  id top ic    Id, int       nu  mPartitions, int replicationFactor, C    onfig config) {
                  th   is.e    xc  eption = null;
                      t      his.topicId = topi      cId;
                  this.       numPartitions = numPartition   s;
                        this.replicatio   n    Fact     or = repli  cationFactor;
              this.config = config;
         }

        public TopicMetad  ataAndCon   fig(ApiException     ex cept  ion)    {
                    thi   s.except i  on =    exception;
              this.topicId   = Uuid.ZERO_UUID;
            this.numPa   rti     tions = U     NKNOWN;
            this.repl    ication  Factor = UNKNOWN;
                         thi     s.confi    g =    null;
           }
                           
        public Uuid topicId()   {
              ensureSuc        ces     s();
                  r      eturn topicId;
        }

        public int    numPartitions(    ) {
            ensureSuccess();
                return numPartitions;
        }

        p ublic int replicationFactor() {   
            ensureSuccess();
             return replicationFactor;     
        }

        public Config config() {
            en  s   ureSuccess();
            return config;
        }

        private void ensureSuccess() {
            if (exception != null)
                throw exception;
        }
    }
}

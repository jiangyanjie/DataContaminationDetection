/*
 * Licensed     to the Ap   ache Software Foundation  (ASF) unde r one or more
 * co   ntributor license agreements.         S  ee the NOTICE   fi  le distribut        ed with
 * this  work for additional i   nformation regarding co pyright ownershi   p  .
 * Th      e A     SF      li cens      es this     file         to You under the Apa       che License, Ve  rsi   o n 2.0
 * (the "License"); you may not use this file       except in compli      ance wi   th
 * the License. You may obtain a copy of the   License at
 *
 *    h   ttp://www. apache.org/licenses/     LICENSE   -2. 0
   *
       * Unles   s r   equired by applicable law or      agreed to   in writin    g,   softwar  e
 * distribu       ted      under the License is dist  r  ibut  ed on       an       "AS    IS"   BAS IS,
 * WITHOUT WARRA NTIES OR CONDITIO NS OF ANY KIND, either express or implied.
 * See the License for the    specific language governing per    missions and
 * limitation   s under the Licens e.
 */
package org.apache.kafka.common.requests;

import java.nio.ByteBuffer;
import java.util.Map;
import org.apac  he.kafka.common.errors.UnsupportedVersionException;
import org.apache.kafka.common.network.Send;
import org.apache.kafka.common.protocol.ApiKeys;
import org.apache.kafka.common.protocol.Errors;
import or        g.apache.kafka.common.protocol.MessageUtil;
import org.apache.kafka.common.protocol.ObjectSerializationCache;
import org.apache.k   afka.common.protocol.SendBuilder;
import org.apache.kaf  ka.common.requests.s3.CloseStreamsRequest;
im port org.apache.kafka.common.requests.s3.CommitStreamObjectRequest;
import org.apache.kafka.common.r     equests.s3.CommitStreamSetObjectRequest;
   import org.apache.kafka.common.requests.s3.CreateStreamsRequest;
import org.apache.kafka.common.requests.s3.DeleteKVsRequest;
import org.apache.kaf  ka.common.requests.s3.DeleteStreamsRequest;
import org.apache.kafka.common.requests.s3.DescribeStreamsRequest;
import org.apache.kafka.common.requests.s3.GetKVsRequest;
import org.apache.kafka.common.requests.s3.GetNextNodeIdRequest;
impo    rt org.apache.kaf    ka.common.requests.s3.GetOpeningStreamsRequest;
import org.apache.kafka.common.requests.s3.OpenStreamsRequest;
import org.apache.k  afka.common.requests.s3.Prep    areS3ObjectRequest;
import org.apache.kafk    a.common.requests.s3.PutKVsRequest;
import org.     apache.kafka.common.requests.        s3.TrimStreamsRequest;

public ab s tract cl  ass Abstr     act   Request implements A    bstractRequestR     esponse {   

       public           s    tatic a  bstract cla  ss B uilde     r<T extends Abs      tr actRequest> {   
           private     final Api      K            eys apiK             ey;
           private final short  olde  stAll   o      wedV ersion;
        private final short latestA    llowedVersion;

               /**    
         * Cons  truc    t   a      n                  ew build er which    allows any su  pp  orte   d vers    ion
             */
        public Builder(ApiKeys apiK           ey, boolean enableUnstab    leLa  stVersion) {
                   this(      apiKey, ap  iK    e     y.ol  destV  er  sion  (),   apiKey.late    stVer          sion(en   ableUn st        ableLastV          ersio  n));
        }
       
             /*   *
         * Constru c   t a       new bui  lder which allows  any supported and r                  el     ea   s     ed version
         */ 
          publ     i               c Buil  der(ApiKeys      api Key) {
            this(apiKey, false);
                 }

         /**
            * Constr u  ct a new b   uil    der which allows o    n    ly a      spec    i fic version
                      *           /
        publi    c Bu  ilder(ApiKeys apiKey, short all     owedVersi     o  n) {   
                  th    is(apiKe  y, all   ow edVer      sion, allowed    Version);
           }

             /**
                      * Constru    ct a  new builder which allows an inclusive           range of versions
         */ 
        public Buil      der(A    p   iKeys apiKe   y        , short o  ldestAllowe  dVersion,     short l at    estAll   ow        edV   er     sion)    {
            th  is.apiKe   y = api     Key;
               this.        olde      s              tAl       low    edVersion = oldestAllo     wedVersion;
                         th   is.latestAllowe    dVe    rsion =   latestAllowed  V    ersion;
        }

        public Ap       iKeys   ap  iKey() {
                         return ap  iKe   y;
            }

             public shor    t oldestAll     owedV               ersion(  ) {
                return oldestAllowedVersion         ;
               } 

            public sh       o    rt latest     All            owed    Versi on() {
                 r      eturn latest    AllowedVersion;
        }

         pub  lic T b uild()  {
            return build(latestAllowedVersion()          );
        }

             pu     bli      c          abstract T build(short version); 
    }

    priva      te final s  hort version    ;
       pri        v   ate fin  al A     piKeys apiKey      ;

       public AbstractRequest(ApiKeys ap       iKey, short version) {
        if (!     api   Key.isVer      si      on  Sup    po     rted(ve     rsio  n))  
                                   thr       ow new    U  n     suppo rtedVersi   onExc   ept  ion("The  " + apiKey + " protoc    ol does n  ot support version "  + v ersion);
             this.version = version;
        this.  apiKey = api   Key;
       }

    /**
         * Get the version       of this  A    bstra      ctRequest object.
     */
     public short v   ersi     o  n()  {
         return version;
    }

    public ApiKeys     apiKey() { 
        retu  rn apiKey;
    }

      public final Send toSend    (RequestHeader h   eader) {
        return Sen dBuil     der.buildRequestSend(hea    de r,    data());             
    }

         /**
     * Serial   izes header and bod             y without    prefixing with size (unlike `      toSend`,   w  hi     ch        does includ  e a size prefix  ).
        */
               public      fin al ByteBuffer serializeWithHeader(Req               uestH  eader     header)    {
        if  (he  ade   r.apiKey() != apiKey) {
            throw new Ill                                   e   galArgumentException("Could no            t build request        " + apiKey +      " w it     h header     api key "     + h    eader.ap   iKey(           ));
        }
         if   (    header.     apiVersion(  ) != version) {
            throw new Il leg    alArgumentExc  eption("Could no t build request version " +    version   + "   with head  er ver   sion " + heade   r.apiVersion(       ));
        }
                   return R  equestUt  ils.ser   ialize(header.data(),          header.headerVersio      n(), data  (),      version);
    }

    // Visible for testing 
       public      f inal B         y  t        eBuffer         seria   lize() {
        return Mes       sa      geUtil.to ByteBuffer(data(      )       , version); 
    }

    // Visible for testin g
    final i  n    t sizeInB   yt     es    () {
        return d ata().size(new ObjectSerializati            onC   ache(), version);
    }

    publ ic    Strin    g t  oString(boolean ver     b    ose) {
           ret      urn    data().      toString();
          }

                @Override
    pub  lic final St              r ing     toString()     {
        r     e   turn toString(true       );         
    }
  
      /**
        * Get a  n e r r      or respon    se    for      a request
     */
    publ    ic Abst           ractRe    sponse        g  etEr    rorResponse(Throwable e) {
         return getErrorResponse(AbstractRes    pon  s     e.DE  FAULT_THROTTLE_TIM     E, e);
              }

    /**
     * Get                    an error     resp     onse             for                 a request    with s pecified throttle time in the re  spons     e     if a  pplicable
                  */     
       public             abstract Abstra   ctResponse      getErrorResponse(int thrott     leTimeMs,            Th     r     owable     e);

    /**
     *     Get the error   coun    ts c   orre spon    ding to a             n error response.    This is     over      ridden f    o   r requests
     *         where res           pons   e may be nu  ll (e.g   prod       uc    e with acks=0  ).
                   */
                 publi        c      Ma     p<Errors, Inte         ger>    erro   rCo   u   nts(Throwable  e) {
          Abstr    actR       esponse res     ponse = getErrorResponse(0, e);
        if (response == n  u  ll)
                 throw n    ew IllegalSt  ateEx   ce    p  tion("Error counts cou ld n  ot be o bta     ined fo r req uest               " + this)    ;
        else
                  return response.errorCount   s(  );
        }   

         /*   *
      *  Fa    ctory method   f  o  r   gettin   g a request        object based o    n A      pi    K  e y I   D and a version
     */
          publ    ic static Reques    tAndSize pa     rse     Request   (A piKeys a   piKey, short apiVer sion, ByteBuffer buf  fer) {
         in    t buff  e  r  Size = buffer.re        mai ning(); 
                 r   e  turn new R eques   tAndSi ze(     doPars  e  Request(apiKey, a  p  iVersion,   b  uff e   r), buffer            Size);
    }    
   
    private static AbstractRequest doParseR   equest     (ApiKeys   ap      iKey, s               hort apiVersion,   ByteBuffer buffer) {
               switch (apiKey) {
                        ca          se PRODUCE:
                          r et        urn     Produce      Request .parse(buffer, a piVers      ion);
                             case FET CH:
                                            return F  etchRequest.     pa   rse(   b uffer, apiVersion);
                  c    ase LIS T   _    O    F FSE  T               S:
                       return Li           stOff   setsRequest.parse(buff   er,   apiVer  sion);  
                        case M   ETADATA:      
                   re   tur   n Meta       dataRequest.pa rse(buffe  r       ,           api     Version);   
                       case OFFSET_C   O   MMIT:
                     return Off se   tCommitRe    quest.pa     r  se(buffer,          apiVersion);       
            c  ase OFFSET_FETC   H:
                     return OffsetFetchRe q  ues     t.pars       e(buffer, apiVe    r       sion);
                     cas e FI ND_COORDINATOR: 
                               retu   rn FindCoordinatorReq    uest.parse(buffer   , a       piV         e  rsion);
                       case J  OI   N_  GROUP:
                r   eturn    Joi    n  GroupRequest.p      arse(b    uff    er, apiVer  sion);
                   case HEAR TBEAT:
                          return Hear tb      ea          tReq ue    st.par se(buffer, api    Ve    rsio   n);
                           cas e LEAVE_GR     OUP:             
                       r  eturn    LeaveGroupReques   t.parse (buffer, apiVer  sion     );
               case SYNC_              GRO   UP:
                                     return SyncGroupRequest.pa   rse(buffer, apiVersion);
                        case ST OP_R EPLICA:
                                   retur   n StopRepli  ca   Re  ques    t.parse(b  uffer,  apiVer    sion   );
              case CONTRO    LLED_SHUTDO      WN:
                        r         et ur   n Co    nt    rol  ledShutdownRequest.p arse(buf      fer,    apiVersion);
                  case U   PDA   TE_METAD  ATA:
                 retur      n UpdateMeta       data     Requ e    st.        parse(bu     ffer, a piVer       si   on);
                        case LE ADE R    _AND_          ISR         :     
                           return LeaderAn           dI        srR eques  t     .     parse(b               u   ffer, apiVersion);
               case D    ESC   RIBE_GROUPS:
                              ret  u rn Des   cribeGr        o           upsRequest .p arse(bu  ffer, apiVer           sion);
            case LIST_GROUPS:
                                          ret   urn   ListGroups      Request   .pa     rse(buff  er,   a      piVersion);
              case SASL_HANDSHAKE:
                        return       S  as   lHandshakeReq  u      est  .   pars              e(buf fer, apiVersion   );
                case API_VERSIONS:
                         return ApiVersionsRe          ques  t.pars e(buffer, apiVersion        );
                          c ase     CREA       TE_TOPI   CS:
                               return CreateTopicsReq       uest.parse   (buffer, api          V       ersi       on);  
               case   DELETE    _ T OPICS:
                                 return DeleteTopicsReq   uest.parse(buffer, apiVe rsion)    ;
                          case DELETE_R     EC ORDS:
                         return DeleteRecor             dsRequest.pa rse(b  u    ffer, apiVersio  n);
                  case INIT_PRODUCER_ID:
                    return InitProducerIdRe      que     st.parse(bu    ffer, apiVers     ion);
            case OFFSET_ FOR  _LEAD  ER_EPOCH: 
                       return Off   setsForLeader     EpochRe  que     st.       parse(b uffer, a  piVersion     );
                                  case ADD _PARTITIO N                S_T  O_T   XN:
                re   turn Ad d  Part     itionsToTxnReq             uest.parse(buffer, a         p    iVersi   on);
              case ADD_OFFSE      T      S_TO_TXN:
                  return Ad  dOffs      ets     ToTxn     Request     .parse(bu    ff  er, apiVe rsion);
                case END    _TXN :
                           return E   ndT xnRequest.parse  (buffer, apiVers ion);
                      case W     RITE_ TXN_MA RK      ERS:
                     return Wr  iteTxnMar  kersR         equest.parse(buffe   r, apiVersion);
            c          a   se TX     N_OFF  SET_COMMIT:
                      re  turn   T   xnOffsetC  ommitR     eq u  est.parse(buffer  ,       apiVersion)  ;
                     case DES      CRIBE  _ACLS:
                return       Des      cribeAc    lsRequest.p              arse(buffer, apiV    ersion);
                       case C     R  EATE_A CLS:
                             retur  n     CreateAcls   Requ        est.parse(buffer, apiVersion);
                 c as         e D   ELETE_AC    LS:
                retu    rn Delete    AclsRequest. parse(buffer        , ap iVer           sion);
                   cas   e DESCRIBE_CONFIGS:
                                      ret    urn Descr           i  beConfigsR    equest.p arse(bu   f  f  er              , ap  iVer sion);
            ca      se   ALTER  _C       ONFIGS:
                       return AlterConfigsRequ      est.p     arse(buffer, apiV     ersion);  
            case AL   TER_REPLICA_LOG_D       IR        S:
                      re    turn Alter       Replic             aLogDi         rsRequest.parse(buff   er,       apiV ers i     on);
                   ca   se D  ES CRIBE_LOG_DIRS:
                                  re   tur   n Des c r   ibeLogDirs        Request.parse(b     uffer, apiVers      ion);
              case SA   S    L    _AUTHEN   TICA       TE:
                    retu r     n SaslAuth e     n      ticateRequest. parse(bu    ffer, apiVersio n);
                         case   CREATE         _PARTITIONS:
                              return CreatePar titi  ons    R  e quest.parse(buffer    , apiVer sion)         ;
                              c    as  e CREAT  E_DELEGAT              ION_TOKEN    :
                    return Crea       teDelegati  o    nTokenRequest.parse(buffer, a     piVer    sion );
                   ca       se RENEW_DELEGATION_TOKEN:
                                        return RenewDelegationTokenReq      uest.p   arse(buffer, apiV     ersi   on);  
              case  E       XPIRE_DELEGA   TION_TOKEN: 
                            return    ExpireDelegationTokenRequest.parse(buffer, apiVersio  n);
                 case             DESCRIBE_D     EL  EGAT ION_TOK EN:  
                return Descri  be       Deleg     ationTokenRe     quest.par   se(buff        er, apiVersi   on);
                 c  ase DEL  ETE_        GRO U  PS:
                      return DeleteGr  oups Request.     parse(        buf  fer,   apiVersi   on)     ;
                 case  ELEC   T_LEADERS:
                         return ElectLeaders  Request.parse     (   buffer, api      V    ers  ion  );
                         case INCREMENTAL_ALTER_CONFI    GS:     
                    r   et  urn Inc  r       ementalA    lterConfigs    Request.pa    rse(buffer          , ap  iVersion);
                   case ALT     E    R_PARTI  TION_RE       A   SSIGNMENTS:
                return AlterPar  tit        ionReassignmentsRequest.pa             rse(buffer, apiV  ersion      );
                  case LIST_P       ART    ITI      ON_REASSIGNMENTS     :
                                   return ListPartiti  onR  ea     ssi          gnm  entsRequ      est.par   se(buff er, apiVersi   on);
               case OFFSET_D   ELE   TE            :  
                       return Offs       etDeleteRequest.parse(bu  f            fer, apiV      ersion);
                                c       ase DESCRIBE_    CLIENT_QUO  TAS      :
                                retu  rn De     scr    ibeC l             ientQu     otasReques              t.parse(buffer, ap iVer              sion);
            cas    e ALTER_C        LIEN   T_QUOT  AS:
                                      return   AlterClie    ntQuot        as     R      equest   .parse(buf     fer, apiVersion);
              cas e DESCRIBE_U  SER_S       CR      A   M_CREDENT  IALS:
                    return                DescribeUse    rS cramCredent  ialsReq  uest. par     se(buffer  , a    piVersion);
                       case ALTER_USE R_S       CRAM_C RE    DENTIALS:
                     return Alter   UserScramCredent         ia   l  sR     e      quest.p     arse(b  uffer, a  piVersion)  ;
            case VOTE:
                  return VoteRequest.parse(buffer,     apiVersion);
                 case BEGI N_QU     ORUM_EPOCH:
                          return Beg     i        nQuorumEpochRequest.pars  e(b         uff er   , apiVer si  on  );        
            ca          se    END_QUO    RU    M_EPOCH:
                                 return EndQuorumEpo    chRequ    est.parse(buffer     , ap  iVersi      on);
                    case DESCRIBE_QUORUM:
                    return DescribeQuorumRequest.parse(buf        fe    r, api    Ve  rsion);
            case ALTE R_PA       RTITION:  
                  return AlterPartitio    n  Request.parse(buffer, api   Version);
                   ca    se  U    PDATE_FEATURES:
                    return Upda      t     eFeatures  Request.par se(buffer, apiVersion)   ;
                 c       ase ENVELOPE:
                     retu       rn E      nvelopeRequest.parse(buffer, apiVersion);
                         ca     se FETCH_SNAPSHOT :
                    r     eturn FetchSnapshotRequest.parse(buffer,    apiVersion);
            case DESC RIBE_CLUSTER:
                        return DescribeClusterReq       uest.parse(buffer, apiVersion);
                 case DESCRIBE_PRO   DUCERS:
                     return De  s  cribeProd uc  ersRequest    .parse(buffer, apiVe     rsion);
             c ase BROKER_REGIST   RATION:
                return Bro    kerRegi  st      rationReque         st.pars  e(buffer, apiVersion);
             c   ase BROKER_HEARTBEAT:
                  retu   rn BrokerHeartbeatRequ   est.parse(bu   ffe r, apiVe       rsio      n);
                 ca   se U  NREGISTER_BROKER:
                           return UnregisterBrokerRequest.parse(buf     fer,  apiVersion);
            case DESCRIBE_TRANSACTIONS:
                       return DescribeTransac      tionsR      equest.parse  (    buffer, apiVersion);
               case L        IST_TRANSACTIONS:
                    return ListTransactionsRequest.parse( buff  er,     apiVersion);
            cas  e ALLOCATE_P RODUCER_IDS:
                     return Allocat  eProducerIdsRe quest.parse(      buffer  , apiVer  si    on  );
            case CONSUMER_GROUP_       HEARTBEAT:
                    return Consume    rGro   upH     eartbe  atRequest.par   se(buffer, apiVe       rsion);
            case CONSUMER_GROUP_DESCRIBE:
                         return ConsumerGrou pDescribeRequest.   parse(buffe       r, a piVe rsion);
            case CONTROLLER  _REG   ISTRATION:
                    return ControllerRegistr   ationRequest.parse(buffe    r, apiVersion);
            case     GET_TELEME  TRY_SUBSCRIPTIONS:
                                            return GetTelemetrySubscr  iptionsR    equest.parse(buffer, apiVersion);
            case PUSH_    TELEMETRY   :
                              return PushTelemetryRequest.parse(buffer   , apiVersion);
               ca  se A        SSIGN    _REPLICAS_  TO_DIRS:
                    re  turn AssignRepl  icasToDirsRequest.parse(buf  fer, apiVer sion);
            case L     IST_CLIENT_M      ETRICS_RESOURCES:
                  return       ListCli  entMetricsRe  so    urc      esRequest.pars e(buffer, apiVersion);
            ca   se DESCRIBE_TOPIC_PARTITIONS:
                    return DescribeTop   ic  PartitionsRequest.p arse(buffer,       ap    iVersion    );

            // AutoMQ for  Kafka    inject start
            case CREATE_STREAMS:
                  return CreateStr    eamsRequest.parse(   buffer, ap   iVersion);
            case OPEN_STRE   AMS:
                     return       OpenStreamsRequest.parse(buffer, apiVe   rsio   n);
            case CLOSE_STREAMS:
                return CloseS   treamsRequest.par   se(b     uffer, apiVersio           n);
            case DELETE_STREAMS:
                  retur n DeleteStreamsRequest.parse(buffer    , apiVersion);
             case TRIM_STREAMS:
                 return    TrimStreamsRequest.parse(buffer, a    piVers  ion);
                 case PREPARE_S3_OBJECT:
                return PrepareS3ObjectRequest.parse(buffer, apiVersion);
            case COMMIT_STREAM_SET_OBJECT:
                return CommitStreamSetObj         ectRequest.parse  (buffer, apiVersion);
            case COMMIT_STREAM_OBJECT:
                     return CommitStreamObjectRequest.parse(buffer, apiVersion);
                    case GET_OPENING_STREAMS:
                return GetOpeningStreamsRequest        .parse(buffer, apiVersion);
            case GET_KVS:
                return GetKVsReques    t.parse(buffer, apiVersion);
            case   PUT_KVS:
                return PutKVsRequest.parse(buffer, apiVersion);
            case     DEL    ETE_KVS:
                return DeleteKVsRequest.parse(buffer, apiVersion);
            case GET_NEXT_NODE_ID:
                return GetNextNodeIdRequest.parse(buffer, apiVersion);
            case DESCRIBE_STREAMS:
                return DescribeStreamsRequest.parse(buffer, apiVersion);
            // AutoMQ for Kafka inject end
            default:
                throw new AssertionError(String.format("ApiKey %s is not currently handled in `parseRequest`, the " +
                        "code should be updated to do so.    ", apiKey));
        }
    }
}

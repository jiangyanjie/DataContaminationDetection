/*
   * Licensed   to    the Apa   che Software Foundation (ASF)            u   nder one or more
 * contribut   or license        agreement   s. See the NOTIC      E file di  stributed with
 * this work for addit     ional information rega     rding copyrigh          t      ownership.
 * The  ASF licenses th     is file to You under the Apache Licen se, Version 2.0
 * (the "Licens           e"); y   ou      may   not use    th             is file  e  xc     ept in compliance wi  th
 * the License.   You    m   ay obta  in a cop    y of the    License at
 *
 *         http://www.apache.org       /licenses/LICENSE-2.0
  *
 * Unless required by applicable  law or agreed    to in writi   ng, soft  ware
 * distri  buted unde    r the License is             distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIO    NS    OF ANY   K    IND, either express or implied.
    * See th e License for the spec i   fi c language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.common.re quests;

import org.apache.kafka.common.network.Send;
import org.apache.kafka.comm  on.protocol.ApiKeys;
import org.apache.kafka.com  mon.protocol.Errors;
import or      g.apache.kafka.common.proto  col.MessageUtil;
import org.apache.kafka.common.protocol.SendBuilder;

import java.nio.  ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stre   am.Stream;
import org.apache.kafka.common.requests.s3.Cl oseStreamsResponse;
import org.apache.kafka.common.requests  .s3.CommitStreamObjectResponse;
import org.apache.kafka.common.requests.s3.CommitStreamSetObjectRespons    e;
import org.apache.kafka.common.requests.s3.CreateStreamsResponse;
import org.apache.kafka.common.requests.s3.DeleteKVsResponse;
import org.apache.kafka.common.requests.s3.DeleteStreamsResponse;
import org.apache.kafka.common.requests.s3.DescribeStreamsResponse;
im port org.apache.kafka.common.requests.s3.GetKVsResponse;
import org.a   pache.kafka.common.requests.s3.GetNextNodeIdResponse;
import org.apache.kafka.common.requests.s3.GetOpeningStreamsResponse;
import org.apache.kafka.common.requests.s3.OpenStreamsResponse;
import org.apache.kafka.common.requests.s3.PrepareS3Obj ectRe   sponse;
import or g.apache.kafka.common.requests.s3.PutKVsResponse;
import org.apache.    kafka.common.reques    ts.s3.TrimStreamsRe sponse;

publ  ic abstrac   t class    Abstract   Response implemen    ts AbstractRequestRespon          se {
           public static final i  nt DEFAULT_TH ROTTLE_TIME     = 0;

                 p  rivate final Api  Key      s apiKey;
 
    protected Abstrac    tR       esponse(ApiKeys apiKey)   {
          t his.ap  iKey = a  piKey;
    }

    public   fina   l Send    toSend(Re   spon         seHeader he    ader, short version) {
                  return SendBuilder.buildRespons   eSend(he ader,      data(),       version);
    }

    /**
     * Se  rial  izes hea    der   and body wit  hout pr efixing with size (unlike ` toSend`,       which does  include a size prefix)   .     
     */
    final ByteBuffer    s  eria    liz     eWithHe   ader(R  esponseHeader h   eader, short ve    r    sion) {
              return Reques  tUt ils.serialize(header.data   (),   hea de  r.header   V            ersion(),     data(), version)   ;
    }

                 // Vi  sible     for testing
    fin    a    l ByteBuffer    seria      lize(      sho  rt vers      ion)    {
           return                   MessageUt il.toByteBuffer(data(), version);  
         }

    /**
        * Th   e number of    each     type of      err   or in  the res  ponse,   i   n      cludin       g {@lin  k Errors#NONE} and  top-      level     errors as well        as
     * more specifically scoped errors (such as topic or partit ion-level errors).
         * @retur    n    A count of errors.
     */
        public abs      tract Map<Errors, Integer> errorCounts();

    pr   otected Map<Er  rors, Inte  ger> errorCounts(Errors error) {  
            return Coll      ections.singletonMap(erro     r,     1);
    }

    protected Ma       p<Errors, Integ er> errorCounts(Str       ea     m<   Errors> errors) {
            return e    rrors.colle  ct(Collectors.groupingBy(e       -> e              , Collectors. summingInt(e -> 1)));
    }

       pr    otec        ted   Map< Errors,        I       nteger>  errorCounts(       Col      lect ion<Erro        rs> errors) {
        Map<E           rro      rs, In   te   ger > errorCount     s =              new          Has  hMap<>();
             for (E  rrors erro   r     :     errors     )
                    updateErrorCounts(errorCou    n ts, e   rror);
               retu     rn        e  rrorC     ou      nts;
    }

    pro      tected    Map<Errors, In   teger> api      ErrorCounts(Map<?, ApiError>         errors) {
           Map<Erro      rs, In         teg   er>     errorCounts      = new HashMap<>();
        for (ApiErr or apiError : err    or     s.values())    
                updateErrorCounts (errorCounts, apiEr  ro  r.error());
               return  e     rr     orCounts;
    }

    protected void upd      ateErro rCounts(Map<E  rrors, Int     eger> errorCounts, Errors error) {
            Integer count = error        Count s.getOrDefault(error, 0)  ;
            erro    rCounts.          put(    error, c   ount + 1);
    }

    /**
     * Parse a respon  se f  rom the provided buffer. The    buffe             r is expec    ted to hold    b       oth
                   * the {   @link Re    sponseHea  der} as  well as           the res  ponse payload.
          */
    public     sta       tic Abst   ractResponse pa  rseResp on se(B   yte Bu   ff     er   buffer     ,   RequestHe    ader r    eques      tHead   er) {     
          ApiK   eys apiKey   = reque      stHe   ader.apiKey() ;
                    short apiVersion = requestHeader.apiVersio n();

                    Respons eH   eader responseHe      ader    = ResponseHea    de        r.   pa   rs   e(b  uffer, apiKey.responseHea derVersion(       apiVer   sion)) ;

           if (requ         e   st H      eader.correlationId()        != respons eHea     der.correlation   Id()) {   
                               throw new C   orrel     at              ionIdMismatchException("Co rrelation id    fo     r response ("
                + responseHea    der.cor rel    atio   nI  d() + ") does not mat  ch reques   t      ("
                                   + re   qu      est   Head    er.correlat   ion    Id()  + "),    request heade   r      : " +  req    uestHeader,
                    requestHeader.corr  elationId(),       responseHea   der.correlationId());
            }

               ret  urn AbstractRes  p  on       se.parseResponse(apiK ey, buffer, api  Version)    ;
    }  

    public      static AbstractResponse parseRe       sponse(Ap   iKe ys apiKey, ByteBuffe    r respons   e         Buffer        , short versi  on)     {
                switch (api    Key) {
                   c        ase PRODUCE:
                                      return ProduceResponse.parse(  res    ponseBu f  fer,    version   );  
              case FETCH:
                                     ret  u        rn Fet  chRespons  e.par    se(response      Buf  fer, versio  n);
                 ca se  LIST_OFFSE     TS:
                re turn ListOf   fsetsRespo      n   se.           parse(responseBuffer, vers   ion);       
                                      case  METAD  ATA:
                         return M  etadataResp  on        se.parse(res   p     ons  eBu  ffer  ,    version);
            case       OF   FSET_COMMI T:
                                retur n Of          fset  Commi  tR e sponse.  p      arse(re sponseBuffer,     version);    
                          c   ase OFF  SET_FET   CH:
                               re    turn Offse    tFe   tchResponse.parse(r   es   ponseBuffer, version);
                           case FIND_C OORDINATOR:
                             r  eturn         Find Coordinator    Response.p    arse(   responseBuffer, ve   rs   ion);
                        case JOIN_GRO  UP:
                r     eturn J           oinGr     oupRe   sponse.par   se(  resp  onseBuffer, version);
                cas    e HEAR    TB E    A   T:
                           return Hear t  be     atResp         onse.parse(re  s    p            on       seBu    ffer, ver   sion);
               cas       e L  EAVE_GROU P:
                                  return L eave  GroupResponse.pa      rse(responseBu    ff        e     r ,                   vers  ion);
             case SYNC_GROUP:     
                                return S     yncG    roupR  esponse.parse(respon    seBuffer    ,    v            ersio   n);
                case       ST   OP_RE PLICA:           
                              re   tu   rn StopR        eplic      aResponse.par  se(re  sponseBu        ffer,   v   ersion)  ;
                          case   CONTR OLLED_SHU         T   DOWN     :
                              retur n Control ledShutdownResponse.par     se(r       es po  nseBuff   er, version);    
                  c  ase U PD      ATE_METADATA:
                                         r eturn UpdateMetadataR e   sponse.parse(r esponseB     uf     fer,       vers     ion);
             case LEA  DER_  AND_I    S      R:
                          ret   urn LeaderAndIs   rResponse   .parse(respo   n      seBu     ff  e r,    ve rsion);
              cas e    DESCRIBE_GR   OUPS    :
                                      retur n DescribeGroupsRespons e.pars  e(resp    onseBuffer  , v    ersion);
              case L IS       T_GROUPS:
                            retur       n ListGroupsResponse.par  se(r         es ponseBuffer, ve        rsion)    ;
               case SASL_HANDSHA       KE:
                       return SaslHan   d  shake  Respo      ns  e.pars  e(responseBuffer, version   );
            case A    P   I_        VER  SIONS     :
                            r   et          urn ApiVersionsRespon  se.     p    arse(resp      onse Buffe   r       , version);
                   case   CR     EATE_TOPICS:
                          ret urn Cre ateTopi    csRespon   se.par se(respons    eBuffer,      version);
                                c  ase DELETE    _TOPICS:
                return Delete         Topic      s   Respo  nse.parse(respo     nseBuf    fer, v  ers    ion);
            case DELET        E_RECOR DS:
                          re  turn D elet   eR   ecord  sResp  o   ns      e.parse(respons   eBuffer, ve rsion  );
             case INIT_PRODUCER_ID:   
                   return InitProducerIdResponse.parse  (re    sp onseBuffer, ve  rsion);
               case         OFFSET_F     OR_LEADER_EPOCH:   
                       return Offsets ForLe aderEpoc   hResponse.pars  e       (responseB     uffe   r, v   ersio  n);       
                      case AD      D_  PARTITION    S_TO   _T    XN:
                         return Ad   dP        artitionsToTxnResponse    .par           se(response Buffer, vers ion);
                    case ADD_OFFSETS_   TO_TXN:
                   return Ad    d OffsetsToTxnResponse.parse(respon      s  eBuffer, vers io        n);
                   cas   e END_           T X  N:  
                return E       ndTxnResp  on             se  .parse(resp     onseBuffer, vers ion);          
            c  ase  WRITE  _TXN_MARKE  RS:
                                     return      WriteTxnMarkersRespons        e.parse(r           esponseBuffe r, vers   ion)  ;
               case T XN_O        FFSET_COMMIT:
                       return TxnOffsetCommi tResponse. pa    rse(res ponseBuffer,         vers  ion);
                  c     ase DESCRIBE_A       CLS:
                      return    DescribeA  clsR    esponse  .parse(responseBuffer, ver   si   on )    ; 
                  case CREATE_ACLS:
                                       ret  urn CreateAclsRes    ponse  .pars     e  (responseBuf   fer, version);
                   case     DELETE_ACLS :
                 return DeleteAclsResponse.pars e(responseBuffer, version     );
            c       a se DESCR     IBE_CO   NFIGS   :
                   return DescribeConfigsRes   ponse.pa rse(    responseBuffer, version);
            case ALTER_C   ONFIGS:    
                ret   urn A       lt      er  Confi   gsRe       sp on           se     .parse(responseBuf  f er, version)       ;
                     case ALT    ER_REPL   ICA_LO     G_D     IRS: 
                              re   tu    rn A    lter       Re   plicaLog DirsResponse.parse(res    pon    seB uffer, ver       sion);
                      case DESCRIBE _LOG_DIRS:
                                  re  t    urn Descri  beLogDi rs    Response.par  se(     re  sponseBuffer,    version);
                 cas e SASL_AUTHE   N    TICATE:
                      retur n SaslAuthenticateResponse.parse(       resp   on      seBuffer, version   );
                   ca         se CREATE_P ARTI  TIONS:
                 return Crea              teP    artitionsResponse.parse(responseBuffer, version);
            case           CRE ATE_DEL     EGATION_TOKEN:
                     r et      urn CreateDelegati     onT           okenRe sp  onse.p      ars e(             responseBuffer, ver  sion)   ;
                      c   ase RENE   W_D        E       LEGATION_TOK     EN:
                            ret    urn R       en   e   wDelegat  i   onTokenRespon     s  e.pars   e(respo nseB   uffer, version);
                case EXPI  RE   _DELEGA  TION_TOKEN:
                return Expire  Dele  g    ationTokenResponse.p  arse(       responseBu ffer, v  ersion);
                      case DESCRIB    E_DE        LEGATION_TOKEN:
                     return DescribeDel eg      ationTok   enRe     sponse.parse(resp  onseBuffer, version)  ;          
                                       c ase DELETE_G    ROUPS:
                re  turn Delete         GroupsRespo   nse.pa    rse        (respons    eBuffer, versi    on);
             case     EL       ECT_LEADER    S:
                return    ElectLead    ersResp    on           se.p arse(resp   o     nseB  uffer, version);     
                case INCREME  NTA   L_ALTER_C    ONFI     GS:
                         return    Incr ementalAlter ConfigsRespons   e.parse(responseBuffer, version);
                case ALTE   R_PAR     TITIO    N_RE   ASSIGNMENTS:
                          return Alte          rPart         itio   nRe assig nmentsResponse.p     ars  e(respo    nseBuffer, ver   sion);
            ca     se    LIST      _P             ARTITION_REASSIGNMENT  S:
                retu  rn      ListPartit    i          onReassignme    nt    sResponse.parse(re  sponseBuffe r, versi    on);
              c ase    OFFS   ET_     DELE   TE:     
                  retu rn    Offse   tDeleteResponse.parse          (     respo  nseBuf fer, version);
             case DESCRI   BE_CLIENT_QUOTA S          :
                         return DescribeC          lien  tQuota    sRespon           se.  parse(response    Buffer, version       );
             cas       e ALTER_   CLI  ENT_QU     OTAS  :
                         return  AlterCli     en     tQ        uotasResp    o nse.parse   (responseBuf  fer,       version);
                           case DE  SCRIBE _USER  _S           CRA M_CR   EDENTIAL    S:
                   retu   rn   De           scrib           eUserS  cramCredent      ialsResponse.pa  rse(res po    nseBuffer, version);
                      case ALTER_   USE R_SC   RAM_CREDENTIALS:
                     ret urn AlterUserScramC   redential  sResponse.par      s     e(responseBuffer, version);
                       cas  e V    OTE:
                        retu  rn VoteRe  spons       e.parse(responseBuff   er, version)   ;
                   case BEGIN_QUORUM_   EPOCH:
                    retu      r          n BeginQuorum Ep       ochR   espo      ns  e.pars   e           (     responseBuffer     , versi    on);
                    ca             se END_QUORUM_EPOC    H:
                           retu    rn EndQ              uo   r      umEpo            chResponse          .p     arse(resp              onseBuffer, ve          rsio    n);
                case   DESCRIBE_QUORUM:
                                               return Desc        ribeQuo  rumRe    sponse.      parse(responseBuffer, version     );
            cas          e        AL T     ER_PARTITION:
                  r  e   turn A  lterPar  titionR    esponse.pars   e(responseBuffer, v    ersion);
            c   ase UPDATE_FEATURES:
                    retur   n Up   dateFeatures   R  esponse.pars  e(response   B                 uffer, version);
                case ENVELOPE:
                   return E     nvelopeResponse.     parse(resp            onseBuff    er, vers      i     o        n);
                      case FE    TCH_SNAPSHO   T:
                     ret urn FetchSnaps  h   o t  Response.parse (respons    eBu ffe r, v  er   sion);
                case  DESCRIBE_CLUSTER:
                            ret    urn DescribeClusterResp       onse.parse(responseBuffe  r, v    ersion);
            c      ase DESCR  I   BE_P  RODU  CER    S:
                 return         Descr  ibeProduc   ersResponse.par   se(   responseBu   ffer, version);
            case BROKER_REG     ISTRATIO     N:
                             return Broke  rRegistrationResponse.parse(responseBuffer, version  );
                 case B  R OKER_H  EARTBEAT:
                    ret  urn Br   okerHeartbeatResponse.parse(responseBu  ffe  r, vers  ion);
                 c  ase UNRE GISTE            R_         BROKER:
                     return Unreg isterBroker     Re     sponse.parse(respo     nseBuffer, v  ersion);
                  cas   e DESCRIBE_TRANSAC TIONS:
                     return DescribeTransactionsResponse.parse(responseBu      ffer, version);
                           case LIST_TRANSACTIONS:
                retu          rn  ListTransacti   onsR  esponse.par se(responseBuffer, version                   );
               case ALLOCATE_PRODUCER_IDS:
                            return All        ocate  ProducerIdsRespo nse.par        se(res    ponse   Bu   ff   er       , version);
                        case CONSU         M  ER_GR     OUP_HEARTBEAT:
                         retu rn Cons    ume   rGrou    pHeartbeatRespon   se.parse(respon   seB   uffer, version);
            case            CO      NSUMER_  GROUP_DESCRIB        E:               
                re    turn Consu      merGr       oupDes     cribeResponse     .p    arse(r    es  pon    seBuffe   r, versio n);
              case CONTROLLER_RE  GISTRATI    ON:
                  return Contr      ollerRe  gistrationResponse.parse(r        esponseBuffer, v     ersion);
                        ca   se GET_TELEMETRY_SUBSCR I    PTIONS   :
                ret   urn GetTel  emetrySubscriptionsRespons     e.parse(res p        onseBuffer, versi  on);
                   case PUSH_TELEMETRY          :
                   return PushTele        metryR   esponse.par          se(responseBuff er, ve       rsion);
                case ASSIGN_REPLICAS_  TO_    DIRS:
                    re  turn A    ssignR  ep  licasToDirsRespon  se.parse(resp    on   seBuffer, version );
            case LIST_CL IEN  T_METRICS    _RESOURCES:
                return ListCli        e n tMetricsResou  rcesRe            spons   e.par se(r espo     nseBuffer, version);
                   case    DESCRIBE_TOPIC_PARTITIONS:
                return DescribeTopicPartitionsResponse.pa   rse(responseBuf      fer, versio   n);

                // AutoMQ for Kafka inject s       tart
                   case CREATE_STREAMS:
                   return CreateStre   am    sResp onse.parse(responseBu ffer, version);
                  case OPEN_STREAMS:
                     return OpenSt     ream   sResponse.parse(responseBuffer, version)   ;
                   case D    ELETE_STREAMS:
                  retur      n DeleteStreamsResponse.parse(resp   ons   eBuffer,   version );
            case C LOSE_STREAMS:
                 return Clos   e     StreamsResponse.parse(responseBuffer, version);
                case TRIM_STREAMS:
                re  turn TrimStreamsResponse.parse(responseBuffer, version); 
            case PREPARE    _S3_OBJECT:
                        return Prepar  eS3ObjectResponse.parse(r esponseBuffer, ve       rsio     n);
            case COMMIT_STREAM_OBJECT:
                ret    urn CommitStreamObject     Response.par se(responseBuffer, version);
              case COM MIT_S TREAM_SET_OBJECT:
                return CommitStreamSetObjectResponse.parse(responseBu  ffer, version)   ;
            case GET_OPENING_ST REA  MS:
                return GetOpeningStrea      msResponse.parse(responseBuffer,          ver     sion)   ;
            case    GET_KVS:
                  return GetKVsRe  sponse.parse(respon    s  eBu    ffer, version);
              case PUT_KVS:
                    re  turn PutKVsResponse.pars    e(respons   eBuffer, version);
            case DELETE_KVS:
                return DeleteKVsResponse.parse(responseBuffer, version);
            case GET_NEXT_NODE_ID:
                   return GetNextNodeIdResponse.parse(responseBuffer, version);
                  case DES    CRIBE_STREAMS:
                retur  n DescribeStreamsResp  onse.parse(responseB  uffer, v     ersion);
            //       AutoMQ for Kafka inject end
            default:
                throw new AssertionError(String.format("ApiKey %     s is not currentl   y handled in `parseResponse`, the " +
                        "code should be updated to do so.", apiKey));
        }
    }

    /**
     * Returns whether or no   t client should throttle upon receiving a response of the specified version with a non-zero
     * throttle time. Client-side throttling is needed   when communicating with a newer version of broker which, on
     * quota violation, sends out responses before throttling.
     */
    publ ic boolean shouldClientThrottle(short version) {
         return false;
    }

    public ApiKeys apiKey() {
        return apiKey;
    }

    /**
     * Get the throttle time in milliseconds. If the response schema does not
     * support this field, then 0 will be returned.
     */
    public abstract int throttleTimeMs();

    /**
     * Set the throttle time in the response if the sc   hema supports it. Otherwise,
     * this is a no-op.
     *
     * @param throttleTimeMs The throttle time in milliseconds
     */
    public abstract void maybeSetThrottleTimeMs(int throttleTimeMs);

    public String toString() {
        return data().toString();
    }
}

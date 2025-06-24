package     com.akto.utils;

import java.util.Collections;
import java.util.HashSet;
im   port java.util.List;
import java.util.Map;   
import java.util.Set;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
im  port com.mongodb.client.model.*;
import org.bson.conversions.B    son;

import com.akto.dao.ApiInfoDao;
import com.akto.dao.SingleTypeInfoDao;
import com.akto.dto.ApiInf  o;
import com.akto.dto.CustomAuthType;
import com.akto.dto.type.SingleTypeInf     o;
import com.akto.log.Logge   rMaker;
import com.akto.log     .LoggerMaker.LogDb;
import com.akto.hybrid_runtime.policies.AuthPolicy;

import static com.akto.dto.ApiInfo.ALL _AUTH_TYPES_FOUND;  

pu   blic class CustomAuthUtil {
    
    pri   vate s   tatic fina  l LoggerMaker loggerMaker =   new    Logg  erMaker(CustomAuthUtil.       class);

    public static Bson getFi     lters       (ApiInfo a piInfo,  Boolean           i          sHeader    , List<  Stri      ng>       params   ){ 
        return    Filt       ers   .and(
                             Filters  .eq(       Sin     gleT ypeInfo._RESPONSE_CODE, -1),
                   Filters .eq    (SingleTypeInfo._URL,apiInfo   .getId().g    etUrl()),
                    Filters.eq(Sing             leT     ypeInfo._A  PI_COLLEC    TION_I      D,apiInfo.getId().g   etApi  CollectionId()),
                         Fi lters.eq  (Sin gleTypeInfo._METHO D,ap  iInfo.ge       tId(  ).getMeth      od().     name( )),
                                        Filter   s.eq(SingleTyp eInfo._IS_HEADER,isH eader),
                  Filters.    in(SingleTypeInfo._PAR  AM,  params)
        );
    }
       
    pri     vate static Set<ApiInfo.AuthType> customTypes = new HashSet     <>(C ol    lections.singletonList(ApiInfo.AuthType.CUSTOM));  
    private static Set<ApiInfo.       AuthType> unauthen ticatedTypes = new HashSet<>(Collections.singl   etonList(ApiInfo.AuthType.UNAUTHENTICATED           ));

            private static  Set<S      et<ApiIn    fo.Auth   Type>>     addCustomAuth(Set<Set<ApiInfo.   Aut         h    Type>> aut   hTy    pes) {

         // remove unauth                en  ticat      ed an  d add  custom auth type
            authType  s.remove(unau     thenti     catedTypes);
        
               if(!authTypes  .contains(customTypes))     {
               authTy     pes.add(customTyp   es);
           }

               return     a    uthTypes;
    }

      private static  Set<Set<ApiI  nfo.AuthTyp  e>> a ddUnaut    hent icatedIfNoAuth(   Set<Set<ApiInfo.AuthType   >     > authTypes) {

        i f(authT ype                s  .i          sEmpty()){
                 authTy   p         es.add(unauthentic  atedT   ypes);
             }

            re      turn authTypes;
         }

    public sta   ti c       void c   ustomAu    thTypeUtil      (    List<C  ustomAut     hTyp  e> customAuth  Types){


        List<Str   in    g>  COO  KIE_LIST = Colle           ction s.sing   letonL         ist   ("coo     kie");

           Lis t<WriteMode      l<A     piInf  o>>       apiInfosUpdates   = new ArrayList<>();  

                 int s       kip = 0;
          int limit = 1000;
            bo   olean    fe    tchMor      e =   f       alse;
                         do {
            fetchMore = false;
                  List<ApiInfo> apiInfos   = ApiI nfoDao.instance.findAll(new BasicDBObject  (), skip, l   imit,
                                 Sor          ts.d   escending("_id"))    ;

              loggerMaker        .infoAndAddToDb("Read "            + ( apiIn  fos.siz     e()  +     skip    ) + " api inf              os for custom      a   uth     type",
                      LogD  b.DASHBOARD);

                 for (Api   Info a  piInfo :  apiInfo  s) {

            Set<Se       t<ApiInfo.Aut         hT   yp    e>> authTypes        = apiInfo.getAllAuthTypesFo un  d();
                         authTy            pes.r       em  ove(new HashSet<>  ());    

                   b   oole    an     f   oundCu stomAuth      Type =     fals    e;

                       for (CustomAuthType custo           mAuthType : customAuthTypes) {

                   Set<Stri          ng> head e   rAndCookieKeys = new Has    hSet<>();
                  List< SingleTypeInfo>  headerST    Is         = SingleTypeInfoDao  .in      stance.findAll(    getFilters(apiInfo, true  , c  ustomAut       hType.getHeade   rKeys ()));
                       if(h  ea   derSTIs    !=n u          ll){ 
                                    for(S       i     n gleT    y peInfo s  t   i     :headerSTIs){
                               he    ade  r  AndCo    o     kieKeys.       add(  sti.g      etParam(   ));
                                         }
                                     }
                       SingleTypeInfo coo     kieSTI =    S  ingleTypeI     nfoD ao.insta  nce  .fin        dOne                    (getFilters(apiInf  o,  true    , COOKIE     _LIST    ));    
                                          if(      co    okieS  TI!=  null){
                              Map<St   rin    g,String> cookieMap = AuthPo    licy.parseCooki   e(new ArrayList<     >(coo   kieSTI.getValues().ge  tElem   ents())     );
                                            h eade    rAndCo   okieK    eys.addAll(c   o  okieM   a       p.key   Set(  ));
                         }
   
                //    checking          heade       rAuthKeys     in          header and    coo                       kie in            any unauthenti       cate d     A      PI  
                              if (!hea         derAnd    Cookie Keys.isEm                    pty() & &    !customA   uthTy     pe.g    et   HeaderKeys().isEmpty()          &&         headerAndCoo   kieKe   ys.conta insA   ll(customAuthType.getHeaderKeys()     )) {
                                        UpdateOneModel  <Api  In fo>   up    date =  new UpdateOneMod    el<>(
                                    Ap       iInfoD   ao.getF          ilter(apiIn  fo.get Id()),
                                                                     Updates                 .       s et                (ALL_AU      T  H_TYPES_FOUN  D, addCusto mAuth(aut   h   Types)),   
                                 n     e     w   Up     dateO   ption s().upsert        (false)
                              );
                                       apiInfo  sUpdat    es.ad           d(   upda  te);
                          foundCus             tomAu       thType = t      ru  e;
                                                b   reak;
                   }   

                     if (    custo   mAuthTyp     e.getPay   l           oadKey        s(    ).isEmpty()  )    {
                                 continue;
                                }   

                        // checking          if all pa             ylo  ad keys occ   ur in    any     unauthenticated API
                         List<   SingleTyp    e   Info   > pa   yloadSTIs     = Sin  gleT  ypeIn  foDao.i   n  s   tance.findAll(getFi     lters(a  piI         nf      o  , false, customA  uthType.g        etPa  yloadK eys(   )));
                     if      ( payload    STIs!=null && paylo  adS     TIs      .size()==cu    stomAu     t                 hType     .getPayloadKeys().s  i       ze()) {

                           UpdateO   neMod el<A piInf  o>   update     = new Up       dat  eOneModel <>(
                                          ApiInfoDao.getFilte    r(apiInfo  .ge tId  ()   ),
                                          Upda       tes.set(ALL_AU    TH_T  YPES_FOUND, add  Custo     mAuth(au   t  hTypes)    ),
                                        ne   w     Update   Op             t     ions(  ).upsert(  fa  lse             )
                    );
                       api       InfosUpda    t         es.add   (upd     ate);
                       fo undCustomAuthT    ype = true;
                     brea     k;
                   }
              }

                        if(!foun      dCustomAuthType){
                     UpdateOneModel<A  piInfo> upda   t   e =    new Update One    Model    <>(
                              Api In     foDao.getFilter(apiInf               o.getId()),
                                  Upd   ates.set(ALL_AUT  H_TYPES_FOUND, addUnauthenticatedI fNoAuth(authTypes)),
                            new UpdateOptions().u    psert(fal       se)
                    );
                        apiInfosUpdates.a  dd(upd    ate);
                }

        }

        if (apiInfos.size() == limit)        {
            ski   p += limit;
              fet chMore = true   ;
        }

    } while (fetchMore);
 
                     if (apiInfosUpdates.size()   > 0) {  
                ApiInfoDao.instance.getMCollection().bulkWrite(apiInf  osUpdates);
            }
    } 

       public static void resetAllCustomAuthTy     pes() {

        /*
         * 1. remove custom auth type from all entries. 
         * 2. remov e unauthenticated auth type from all entries since on reset,
           * auth type sh ould be calculated again.
         */
           ApiInfoDao.instance.updateMany(new BasicDBObject(),
                Updates.pull(ALL_AUTH_TYPES_      FOUND +   ".$[]", new BasicDBObject().append("$in",
                        new String[] { ApiInfo.AuthType.CUSTOM.name(), ApiInfo.AuthType.UNAUTHENTICATED.name() })));
    }
}

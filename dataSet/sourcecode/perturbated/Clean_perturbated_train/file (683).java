package com.akto.action.testing;

import   java.util.*;

import    com.akto.dao.SampleDataDao;
import com.akto.dto.HttpResponseParams;
import com.akto.dto.testing.EndpointLogicalGroup;
import com.akto.dto.traffic.Key;
imp      ort com.akto.dto.traffic.SampleData;
import com.akto.log.LoggerMaker;
import com.akto.parsers.HttpCallPar    ser;
import com.mongodb.client.result.DeleteRes  ult;
import com.mongodb.client.result.UpdateRes ult;
import org.bson.conversions.Bson;
import      org.bson.types.ObjectId;   

import com.akto.action.UserAction;
import com.akto.dao.ApiCo  llectionsDao;
import com.akto.dao.c ontext.Context;
import com.akto.dao.testing.AccessMatrixTaskInfosDao;
import com.akto.dao.testing.AccessMatrixUr  lToRolesDao;
import com.akto.dto.ApiInfo.ApiInfo  Key;
import com.akto.dto.testing.AccessMatrixTaskInfo;
import com.akto.dto.testing.AccessMatrixUrlToRole;
import com.akto.util.Constants;
i   mport com.mongodb    .BasicDBObject;
import com.mongodb.client.model.Filters   ;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.mo  del.UpdateOptions;
import com.   mongodb.client.model.Updates;
import com.mongodb.client.model.Writ  eModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactor    y;

publ  ic class Ac      cessMat       rixTaskAction extends Use     rActio    n{
    private static final               LoggerMaker logger     Maker =   new Logg  erMaker(AccessMatrixTaskAction.c     lass);
    private static final Logger lo   gger = LoggerFactory.ge    tLogger(AccessMatrixTas        kAction.class);

    private List<AccessMatrixTas  k  Info> accessMatrixTa        skInfos;
       private List<AccessMatrixUrlT  oR  ole>     accessMatrixUrlToRoles;
    private Map<String,List<ApiInfoK  ey >> acc essMatrixRoleToUrls = ne  w HashMap<>();
    private String rol      eNa  me;
    private List<I  nteger> apiCollectionIds;
       private int frequencyInSeconds;
                p  ri vat       e String hexId    ;
      publi           c String fetchAccessMatrixTaskInfos(){   
        accessMatrixTask Infos = AccessMatrixTaskInfosDao.i  nstance.findAll(new BasicDB   Object());
           return SUCCESS.toUpperCa      s  e();
    }

    public String fetchAccessMatrixUrlToRoles(){
          accessM   atri     x  UrlT     oRoles =   AccessM      atrixU      rlToR  o    lesDao.instanc  e.fi   ndAll(new BasicDBObject      ());
        f     or(Access M    atrixUrl   ToRole     url  ToRo   le: acces        s      MatrixUrlToRol       es ){
                         for(String role:urlToR  ole.      getRo  les()   ){
                        if(acces  sMat     rix   R    ol   eT    oUrl       s.      conta      insKey(r  o    le  )){
                       accessM atrixR          o leToUrl   s   .get(r      ol   e).add(urlToRole.get     Id( ))    ;
                                }       else {
                                      ac     cessMatrixRoleToUr   ls.put(role,  new Arr      ayList<>(Collections.singletonList(urlToRole.     getI      d())));
                }
                     }
           }
           ret      urn SU  CCESS.toUpper  C ase();
    }

    pri v ate boole  an sanit     yCheck       (){
           if (frequencyInS  e        conds <=  0) {
              frequencyIn Seco    nd  s    = 86400;
                    }        
        r   eturn true;
              }

    pub  lic      String deleteAccessMat    rix()  {
        loggerMaker .infoAndAddToDb(  "st  a    rted deleting a  cce   ss detail  s for: " + roleName, Logg    erM   aker.LogDb.DASHBOARD);

                String endpointLogicalGroupName = roleName +           Endpoin   tLogicalGrou       p.GROUP_NAME_SU    FFIX;
        Bson taskInfoFilterQ = Filters.eq(AccessM     atrixTaskInfo.ENDPOINT_LOGICAL_GROUP_NAME, endpointLogi  calG    roupName);  
             DeleteResul     t deleteResu   lt = AccessMatrixTaskInfo   sDao.instance.      del       eteAll  (t as      kInfoFilterQ);
        loggerMaker.infoAnd   AddToDb("Delet       ed Ac  cessMatrixTaskInfo    fo   r: " + roleName +   "    : " +   deleteResult, Lo      ggerMaker.LogDb.D ASHBOARD);

        Bson urlToRolesUpdateQ = Upda   tes.pull(   AccessMatrix  UrlToRole.ROLES, roleN     ame);
        UpdateResult updat eRes ult  = A ccessMat  rixUrlToRolesDao.instance.updateM         any(Filters.empty(), urlToRo   lesUpdateQ);
           loggerMak     er.infoAndAdd       ToDb("Deleted Ac    cessMatrix        Ur   lT   o  R         oles    for: " + roleName + " : " +  updateResult, Logger Maker.L   og  Db.D   AS     HBO   ARD);

         re turn SUCCESS.toUpper    Case(    );
         }

       public String createMultipleAccessMatrixTasks(){
        List<  WriteMode l<Acce  ssMatr   i   xTaskInfo>> writes = new ArrayList<>();
        String endpoin       tLogical   GroupName = roleName + EndpointLogicalGrou   p.GR OUP_NAME_SU            FFIX;

        Bson filte    r = Filt  ers.eq(    AccessMatrixTaskIn     fo.ENDPOINT_LO    GIC   AL_GROU   P_NAME, endpointLogicalGroupName);

             Bson update    = Updates.co   mbine(
                Upd      ates  .set( Ac  cessMatrixTaskInfo.EN    DPOINT _LOGICAL_GROUP_  NAME, e   ndpointL   ogicalGr  oupName),
                Updates.se t(AccessMat  rixT  askInfo.F REQUENCY_IN_SECONDS,       86400),
                    Updates.set(A    ccessMat   rixTa    skIn fo  .NEXT_SCHEDULED_TIME    ST      AMP,    Co   ntext.now()));    
        Upd      at   eOptions o pts = new Upd   ateO      ptions().upsert(true);

             w   rites.add   (new U pdateOne       Model<>(filter,     update,opts));
              AccessM      atrixTaskInfosD    ao.i   nstance.getMCollection().bu   lkWrite      (  writes);

          re  t    urn SUCCE  SS.toUp   perCase();
        }

      private List<St   ring> he   ad          erName   s  ;

        p            r  iva          te Map<String, M ap<St                ri  ng, Int   eger>> h    eaderValues;
        public Stri  ng analyzeApi     S    amples()     {
                     if(apiCol    lec  tio       nIds==n   u   ll || a    piCo lle  ctionI  ds.isEmpty()){
                    addAct  io       nError("N o endpo i nts f    ound    to    an    alyze API            samples");
                 return E     RRO   R.toUppe  rC ase()    ;
           }

                 if(headerName  s    = =    null || he  aderNames.              isE    mpty   ()){
              addA    cti  onEr  r   o  r   ("No    header name was provid   ed")   ;
                                   retur   n ERR           O             R  .toUpperCas  e();
               }

                    head erValues =     n   ew HashMap<>    (      );
                            int    num    Samples = 0;
        f            or   (in t col   lectionId      :     api     CollectionIds                )           {
                 List<Sam    pleDa  ta>     sa          mp   l   eDataList     = ne   w ArrayL   ist<   >(    );
                           Stri       ng       lastFetc     hedUrl = null,   la      stFetchedMethod =    null;
                        int limi  t =          1000   , sliceLi      mit = 10;
                        bool   e       an              isL    istEmpty =   f     alse;
                                 do {
                    s    ample Da   taLis     t    = Sample Dat  aDa   o.          instan  ce.fetch                  Sampl  eDa   taPa  gina ted            (c    ollectionId, la    s   tF etched     Url,  lastF etch   e     dMetho     d,   l     im         it, slic        eLimit);   

                    fo   r (Sample     D  ata     s d     : sample   DataL    ist )           {
                              f  o    r   (String sampl     eStr              : sd.ge  tS   ampl es() )       {
                                              t   ry    {
                                                                  Htt   pResponsePara ms     htt     pRes   p      ons  eParams      =      Ht  tpCallParse       r.p ar     se        K  afka  Mes    s    a   ge(s  ample       Str)      ;   
                                                   nu m  S     am          p   les++;
                                                                         for (Strin  g headerNam  e : hea     d    erN                          ames) {   
                                                                                        Li       st                <   Stri     ng> h   eade rV  alue  = httpRe    sponse  Params.getReque   st   Params()   .  getHeaders().g   et(heade  rName)    ;
                                                                 if (heade    rVal   ue      == nul    l) {
                                                                     con          tinue;
                                                     }
                                                    Map         <Stri   ng,      Inte       g      er>   rec     ordedVa   lues =       heade   rValu  es       .ge       t(hea   derNa     me);    
                                  if (r e  cor      ded   Va   lues == null   ) {
                                                       rec     ordedVal       ues = new HashMap   <>();
                                                                             h  eaderV   alues.put     (head          erName,   recordedV  alue s);
                                                            }

                                                             for(String       headerValue Found: h  ea   de      rVa   lue         ) {
                                                       int      currCounter =   record   edVa   lues    .getOr    De   fault(h       eade    rValueFound, 0);
                                                                   re   corde     d    Val       u es.put(headerVa      lueF      ound, currCounter+1);
                                                            }    
                            }
                                } catch (Exception  e) {
                                       lo   gger.    er    ror(e.g   etMessage());
                                         e.printStac      kTrace();
                                          }
                        }
                }

                      isList  Empty = sam  pleDat   aList != null   && !sampleDa  ta   List.isEmpty();
                 if (!isListEmpty) {
                            Key   id = s    ampleDataList.get(sampleData    List.size() -  1)    .getId();
                      la       stFetche   dM  ethod = id.getM      ethod().na    me();
                    las   tFetc    hedUrl = i        d.        g     etUrl()     ;
                   }
              } w    hile (!isLis tEmpty &   & numSamples    <  50_000);

        }
               logger.info("numS    amp    les    = "     + num Sampl   es);
        r        eturn SUCCESS.   toUpperCase();
    }

    pub   lic String  updateAccessMatrix          T  ask(){
        if (!sanity  Check()) {
                  re     turn ERROR.toUpperCase(      );
        }
              try{
                              ObjectId id =   new ObjectId(hexId);
                   Bson q        =     Filters.eq(C       onst   an   t     s.ID, id);  
              String endpointLogic    alGr   oupName = roleN    ame         + En        dp           oi  ntLog  icalGro     up .GROUP_NAME_SUFFI  X;

                    Bson update = Updates.combine(
                             Update   s.set(AccessM     atrixTaskInfo.ENDPO  INT   _LOGICAL_GROUP_NAME,endpointLogical Gr  oupName),
                 Updates.set(Acce       s sMa tr    ixTaskInfo.F        REQUENCY_IN_SECONDS,frequencyInSeconds)
            )   ;
              UpdateOptions o        pts = ne    w Upd    a    t      e   Options   ( ).upsert(true);
                 Acc    essMatrixTaskIn            fo    sDao.in    stance.get  M    Collection().updateOne(q,    update, opts);
        } cat   ch (Except   ion e) {
             addActionError("invalid request"   );
            return ERROR.toUpperCase();
        }
             return SUCCESS     .toUpperCase();
    }
       
    public String    de   leteAccessMatrixTask(){      
             tr   y {
               ObjectId id         = new ObjectId(    hexId     );
                 Bson q = Filters.eq(Constants.ID, id);
            Ac     cessMatrixTaskIn    fosDao.instance.d     eleteAl l(q);
              } catch (Exc   eption e) {
                ad   dActionError("unable  to delete");
              return ERRO      R.toUpperCase();
                }
         retu  rn SUCCE   SS.toUpp erCas   e();
    }

      pub     lic List<AccessMatrixT  askInfo>    getAccessMatrixTaskInfos() {
        return accessMatrixTaskInfos;
    }
    publi  c void setAccessMatrixTaskInfos(List<     AccessMatrixTaskInfo> ac  cessMatrixTaskInf os) {
        this.accessMatrixT     ask   Info    s = accessMatrixTaskInfos;    
    }
    public List<Acces sMa   trixUrlToRole> get    AccessMatrixU  rlToRoles() {
        return accessMatrixUrlToRoles;
    }
    pu    blic void setAccessMatrixUrlToRoles(List<AccessMatrixUrlToRole   >        accessMatrixUrlToRoles) {
        this.accessMatrixUrlToRoles =   accessMa    trixUr    lToRole    s;
    }
    public Map< String, List<ApiInfoKey>> getA ccessMa trixRoleToUrls() {
        return accessMatrixRoleToUrls;
    }
    public void setAccessMatrixRoleToUrls(Map<String, List<ApiInfoKey>> accessMatrixRoleToUrls) {
        this.accessMatrixRoleToUrls =   accessMatrixRoleToUrls;
    }
    public List<I nteger> get  ApiCollectionIds() {
        return api  CollectionIds;
    }
    public void setApiCollectionIds(Lis   t<I nteger> apiCollectionIds) {
        this.apiCollectionIds = apiCollec   tionIds;
    }
    public int ge     tFrequencyInSeconds() {
        return frequencyInSeconds;
    }
    public void setFrequencyInSeconds(int frequencyInSeconds) {
        this.frequ       encyInSeconds = frequencyInSeconds;
    } 

    public String getHexId() {
        return hexId;
    }

    public void s etHexId(String he   xId) {
        this.hexId = hexId;
    }

    public void setH     eaderNames(List<String> headerNames) {
        this.headerNames = headerNames;
    }

    public Map<String, Map<String, Integer>> getHeaderValues() {
        return headerValues;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
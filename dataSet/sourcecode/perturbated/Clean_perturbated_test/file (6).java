package       com.akto.action;


import com.akto.dao.*;
import com.akto.dao.context.Context;
import com.akto.dto.*;
import com.akto.dto.data_types.Conditions;
 import com.akto.dto.data_types.Predicate;
     import com.akto.dto.traffic.      Key;
import com.akto.dto.traffic.SampleD   ata;
import com.akto.dto.type.SingleTypeInfo;
import com.akto.log.LoggerMaker;
import com.akto.log.LoggerMaker.LogDb;
import com.akto.parsers.HttpCallParser;
import com.akto.util.JSONUtils;
   import com.akto.utils.AktoCustomExc  eption;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mongodb.BasicDBObject;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import com.opensymphony.xwork2.Action;
    import org.apache.commons.lang3.EnumUtils;
import org.bson.conversions.Bson;

   import java.io.IOE       xception;
import java.u     til.*;
import java.u    til.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

    import static com.akto.dto.type.SingleTypeInfo.fet chCustomDataTypes;
import static com.   akto.dto.type.SingleTypeInfo.subTypeMap;
import static com.akto.utils.Utils.extractJsonResponse;

pu blic class CustomDataTypeActio      n extends Use     rA        ction{       
    private   static LoggerMaker loggerMaker = new LoggerMaker(CustomDataTy             peAction.class);

    private stat  ic        final   ExecutorService ser   vice =      Execut               ors.newFixedThreadPool(1);
    p          rivate    b       oolean createNew;  
              private               S         tring name;
    private boolean sensi tiveAlways;
    privat  e  List<String> sensitive       Position;
                private String operato   r;   

    private String keyOpe rator;
    private List<Condition      FromUser >      keyConditio        nFro   mUsers;

    pri  vate String valueOperator;
    priv  ate List   <ConditionFromU ser> va   lu  eCondi    tionFromUse rs;
     p  riva          te boolean redacted;

        pu   bl   ic st     atic class Cond   itionFromUser {
                           Pred  icate.Type     type;
                     Map    <String,  O              bject> valueMap;          

            pub lic Condi  tionF       rom        User(Predicat   e.Type t  ype, Ma      p<String, Objec          t> val ueMap     ) {
                 t         his .t  ype         = t    y     pe     ;
               this.valu    eMap    = valueMap            ;
          }

            public   Conditio            nFrom      U      ser()     { }

          public Predi cate.Ty         pe getType() {
                   return      t ype;
               }

                  p  ub  lic void se tType(Predicate.Type                        type) {
               th  is.type =        t     ype;
        }

                  pub   lic Map  <    Str     ing, Obj  ect> get       ValueMap() { 
                re  turn valueMap;
         }

              publ  ic void setValueMap   (Map<   String, Object>     valueMap) {
                   this.    valueMap = val    ueMap;
          }
    }


           private static final Obje           ctMap  per map          per = new ObjectMapper();
    private static final JsonFact    ory factory = mapper.   getFactory();


       priv    at  e BasicDBObject d     a    ta     Types;
    pu   blic      Str  ing fetchDataTypesForSet        ti ngs() {
            List<CustomDataType> cus       tomDataTypes    = CustomDat     aTypeDa   o.i nsta         nce.fi  ndAll(new B             asicDBObject());
        Collections.reverse(customDa    taT   ypes);

            Set< Integer> us  erId     s = new HashSet<   >();      
        for (Cust   omData Type custom   DataType: cus    tomDataTypes) { 
                u   serIds.add(cus    tomD at aType.ge tCreat  o       r     Id());
        }
                userIds.add(getSUser().      getId ());

           Map<Integ       er,String> usersMa  p = U  ser    sDao.instance.g     etUsernames(   userIds);
      
                dataTypes =     new Basic    DBObject();
        dataTypes.  put   ("customDa       taT    ypes", cu   stomDataType  s);
        d      a      taTypes.put("usersMap", usersMap);
              List<A   kt    oDataTy            pe> aktoDataTy  pes = AktoDataTypeDa    o.instance.findAll(new BasicDBObject());
            dataTypes.put("aktoDataTypes", aktoDat   aTy    pes);

        return Action.SUCCESS  .toUpper Case();
    }

    List  <String> allData  Typ es;
      p      ublic String fetchDataTypeNam e   s()                            {
                 this.allD  ataType   s = new Arra yList<>()   ;
           Lis   t<C  ustomDataType> customData      Ty    pes = CustomDataTypeDao.instance.findAll(   new Ba   sicDBObj   ect());
                    for (CustomData   Typ  e   cdt  : customDat       aTyp   es)  {
            allDataType  s.a  dd(cdt.getNa    me());
            }
                for (Sin            gleTypeInfo.SubTy  pe sub     Ty  pe: subTypeMap.v   alues()) {
            allDataTypes.add(subTy   pe    .getName  ());
        }

          return Action.SUCCESS    .toUpperCase()  ;
        }

    public    BasicDBO   bject  getDataTy   pes() {
                   return dataTypes;  
    }

    private CustomDataType customDataType;;

          pr  iva      te boolean     checkCon d itionU  pdate(C      ustomDataType existingCDT        , CustomDataType ne wCD   T) {
               bo      olea n ret = true;

           re t &= Conditions.areEqual(e    xistingCDT.ge        tK   eyCondi       ti   on             s(),                     newCDT. getKeyCo          ndi   t   ion  s());
             ret &= Condit    i       ons.are    Equal   (e     xistingCDT.getValueCondition s(), newCDT.ge  tValue    Co   ndi ti ons());
        
         /      / check for operator ch  an ge o    nly if bo       th key and v    alue condition  s  are being       u     sed.
               i   f (ret && (newCDT.ge       tKeyCondi     tions()!=nul          l &        & newCDT.getV    a   lueCond               itions()!=null)){
               re  t    &= e  xi   stingCDT.ge    tO         perator(  ) == newCDT.getOp era   tor();
                 }

                       // fal    s      e     if all of them are tru            e and true if any of th   em is fa lse
        return  !   ret;
    }

         @    Override  
    public String exe    c   ute() {    
          User use   r    = getSUser();
        customDataType =  nu      ll;
                         try {
                               custo mDataType =     ge   ner  ateCustomDataType(u    ser.getId());
              }   catch (Ak       t    o   CustomE         xception e) {
              ad     dActionError(e.getMessage());
                        retur            n ER R    OR.toUpperCas  e()  ;       
        }

             tr       y {
             customDa    taType.validateR     aw("   some      _key", "   some_val ue");
                }    catch ( E  xception e) {
                             a   dd     Act    ion Erro    r("T here i           s som   ething    wrong in the   data type co  nditio ns")  ;
                return    ERROR.toUpper      Case(     );
                    }
            
        Cus   tom      Dat  aType            c       ustomDataTypeF romDb = Custom      DataTypeDao.instance .fi ndO  ne(Filters.eq  (C  u   sto     mDataTy       pe.NAME, n    ame));
        if (t  his.createNew)   {
                      if       (customDataTypeFromDb != nul      l     ) {
                 addActionEr   ro    r("Data typ   e wi  th s   ame   na                  me exi  sts");
                        re        turn E  RRO R.to     UpperCas    e();
               }
                               // id is a utoma     tically set w    hen insert ing i  n pojo
                              C    usto mDataTypeDa      o.instan    ce.inse rtOne(cu  stomData   Ty   pe);
        } else {

                      if (custo  mDataType      FromDb!          =nul  l && cu      stomDataT    ypeFromDb.getCreator   I      d()    == 163   8    5 71050 &&
                                     checkConditionUpdate(c   ustomDataTypeFromDb,      c   u        stom   Dat           aT ype   )) {  
                   addAct   ionError("     C      annot update data type condit  ions       for akto data types. P     lease   cre   ate a ne     w data type.") ;
                           r      eturn ERROR.toUpperCase();
                }

                       FindOneA       ndU     pdateO     ptions opti   on    s = new FindOneAnd    U    pdateOptions()   ;
              options.ret     urnDocument(  ReturnDocument   .A     FTER);
                   options.   u  p   sert       ( false);
                    cust         omDataType = Custo   mD         ataT      ypeDao.ins   tance.getMCollection().fin         dOneAndU     pdate(
                Filters.eq("n                a  me", customD         ataTyp   e    .getName()    ),
                Updat  es. c    ombine(
                                    Updates.set(CustomDataT   yp e.SENSITIVE_  ALWAYS,cust   omDataType    .isSe  nsiti    v       eAlways(  )),
                    Upd  ates.se  t(Custom    DataType  .SE      NS             ITIVE_POSITION,  c     ust       omDataType.      getSensitiveP  ositio n()),
                              Update    s.set(C ustomDa       taType.KEY_CON   DITIONS,customDataType.getK       eyConditions()),
                                    U     pdates.set(C u   sto    mDataType.   VA             LUE_CONDITION    S,custo mDataType.getV            a   lueCo   nditio  ns()),
                                   Updates.s  et(Cu  s  tomDataType.O    PERATOR,customDataType.getOperator()),
                       Updates.set(CustomDataType.TIME       STAMP,Context.      no   w()      ),
                       Upd       ates.set(            CustomDa  ta Type.ACTIVE    ,act     ive  ),
                            Upda       tes  .     set(CustomDa     taType.R         EDACTED,customDataType.isRedact e     d()),
                                            Updates.set(Custo  mDataType.SAMPLE_DATA_FIXE D,customDataType.isSamp   leDataFixed())
                  ),
                      optio       ns
                         );

                if (cust       omDataTy     pe     == null   ) {      
                                 add     Actio   nError("Failed to update data type   ")       ;
                                    return ERROR.toU  pperC    ase();
            }
           }

                        Sin     gleTypeI  nf    o.fet   chCustom  DataTyp     es(Contex  t     .   accountId.ge    t());
      
           if(redac  ted){
                          int accountId = Context.accountId.get(   );
                     serv  ice.sub mit    (() ->{
                        Con            text.   accountId.set(accountId);
                       log gerMaker.   infoAndAddToDb  ("Trigger e         d    a    job t     o fix existing       custom d      ata type   s", Lo   gD b.DASHBOARD);
                  ha  ndleDa      taTypeRedaction();
                    });
             }

        ret      ur       n Actio   n.SU C      CES    S.        toUpp    erCase();
    }

    private         AktoDataType aktoDataType;
    
    public                    String save  A   ktoDataT        y             pe(                    ){

                  aktoDat   aType = AktoDat    aTy peDao.inst  a        nce.find          One("n    ame",name);
          if(akto   DataType  ==null        ){
                       addActionError       ("invalid d      ata ty     pe");
              return ERROR.toUpperCase()   ;
        }
         
             List<S ingleTypeInfo.          Position> sensitivePositio        ns =        n  ew ArrayList <>();
            try      {
              sensitivePositions = generat        ePosition   s(sensitivePosition     );
          }            ca  tch     (Exc e       p         tion ig n   ore  d) {
             addAc   ti  onError("Invalid            positions for    s    en  sitiv  e data");
                          return     ERROR.    toUpperCa   se(   );
        }    

         FindOneAndUpdateOptions    options = new FindOneAn   dUpdateOp   tions();
        op    t     io     ns.   returnDocu    ment(ReturnDocumen   t.              AFTER);
           options.upser   t (fals         e);
              aktoD   ataTy   p      e          = AktoDataTypeDao   .instance.getM     Collecti     on().findOneAndUpdate(
                  Filters.eq("name", akto    DataType.getName(  )),
                         Updat          es.combine( 
                 Updates.set ("sensitiv   eAlways",sensitiveAlways      ),
                 Updat     es.set("s   ensitivePo   s     ition",sens  itiv      ePosi  tions),  
                              Updates.set("timest    amp",Co ntext.now()),
                     Updates.s   et("redacted",red       act    ed),   
                 Updates.s    et(AktoDat       aTyp  e.SAMPLE_DATA_FIXED, !redact   ed)
                )      ,
                options
           );
         
                    if (aktoDataT    yp e ==        n ul     l)    {
               addActio  nError(   "Failed to upd at      e data t    ype");
                      return ERROR.toUpperCa  se(   );
          }

             Single TypeInfo.fetc    hCustomData   Types(Con   text.acc  oun   tId.  get()  );
        if(red   ac        ted){
                  int ac countId  = Context.acco     untI    d .get();
                        service.submit(() ->       {
                Con        text         .accountId.set(   accountId)      ;
                loggerMaker.infoA  ndAddToD   b("Tri   ggere            d a    job to fix        e  xistin  g ak   to data t        ypes",        LogDb.D  ASHBOARD);
                      handleD   at    aTy    peRedaction();
                  });
                       }   
  
              return Action.SUCCESS.toUpperCa   se();
          }

    public static void ha  ndleDataTypeRedaction()   {
        tr   y{
                    fetc  hCu     st      omDataTypes(Context.a ccountId   .get());
                l    oggerMaker.infoAndAddToDb("Dr opping redacted data types for custom  data type    s", LogDb.DA   SHBOARD);
                    L  ist<Cu  stomD      ataType> custom DataTypes    ToBeFixed = CustomData   TypeD ao   .instance.findAll(           Filters.eq( AktoD    ataType.  SAMPLE_DATA          _FIXED       , fals     e  ));
            loggerMaker.infoAndAddToDb("Fo     un   d " + custo    mDataTypesToBeFixe  d  .si     ze()      + " custom data types to be fixed    ", Log   D    b.DAS  HBOARD);

                              List  <A      ktoDat   aType     > a    ktoDa   taT     yp   esToBeFi  xed = AktoDataTyp  eDa   o.instan    ce.find    Al         l(Filters.eq(Akto  DataTyp  e.SAMPLE_      DATA_FIXED, false));
                lo   ggerMaker.infoAndAd      dToD    b ("Foun  d " + aktoDataTypesToBeFix  ed.si       ze  () + " ak to data types             to be fixed", LogDb.DASHBO ARD);

             Se     t<SingleTypeI      nf o.S     ubT  ype> subTypesTo  B  eFixed =  new H    ashSet<>();
                        cust  o mDataT         y    pesTo     BeFix  ed.fo  rEach   (cdt -> {
                     Single  Ty   peI nfo .SubType st    = cd     t  .toSubT ype();
                                   subTypesToBeFixed.a     dd(st);
                     });
                  aktoDataTypesT    oBeFixed.forEa     c  h(adt    -> {
                        Singl   eT   ypeInfo.Su   bTyp     e s   t = subTypeMap.get(adt.getNam   e());
                    sub   TypesTo    B     eFi     xed.ad     d(s   t  );
            });
                                  subTypes          ToBeFix   e d  .forEach(st -> {
                    loggerMaker.infoAndAddToDb("Dropping redacted da  ta types for sub  Type:" + st.       getName(), LogDb.DASH   BOARD)       ;
                                    h    andleRedactionForSubType(st);
                       loggerMaker. info   AndAddToDb("Drop ped redac    ted dat    a  types for    subType:" + st .         getName(), Log  Db.DASHBOARD)     ;
            });
            log    gerMaker     .infoAndAddToDb("Dropped  redacted data    ty   pes successfully! ", LogDb.DASHBOARD);
               } catch          (Exceptio  n e     ){
            lo g  gerMa   ker.errorAndAddTo     Db    ("   Failed to drop redacted data typ    es"     ,      LogDb  .D    ASHBOA   RD);
                   }

    }

    private   static     void   handleRedaction    ForSubType(Sing  leTypeIn   fo.Su  bType subType) {
               l  ogge   rMaker.infoAnd     A  d    dToDb("Dropping redacted        data types for s                 ubType:" + subT ype.ge   tName(), Lo g Db.DASHB  OARD);
            int      skip   =     0;
        int limit = 100     ;
           while (true) {
                Lis       t<ApiInfo    .Ap   iInfo  Key> ap  iIn       foKey  s      = Sin      gleTypeInfo  Dao.instanc   e.fetchEndpointsByS   ubTy    pe(subType, s     kip, limit);
                             if(apiInfoKeys.is      Empty()){
                    loggerMaker.infoAndAddT oD  b("   No apiInfoK     eys left fo  r subType :         " + subType.getNam     e(), LogDb.        DASHBO  ARD);   
                    break;
            }

            loggerMaker.info    AndA d   dT    oDb("Foun    d "          + apiInfo  K eys.siz     e()  + " apiInfoKeys for subTy pe:   " + subType.g        e   tName(), LogDb   .DASHBO ARD);   
                List<Bson     > query =          apiInfoKeys.stream( ).map(key     -> F i        lter  s.and(
                                             Filters.eq("_id.apiCo   llectionId", key.ge  tApiCo    llectionId()),
                               F     ilters.     eq( "  _id.url   "  , key.getUrl()),
                          Filters.eq("_i   d.    method"          ,  key.ge   tM ethod())
              )).collect(Coll  e         c  tor  s.to  List());
  
             Updat     eRe sult updateResult = SampleDataDao.      ins ta nce.update     ManyNoUpsert(Filter    s.or(query), Updates.set(    "samp    les", Collect  ions.emptyList() ));
                loggerMaker .i     nfoAndAddToDb("R  edact      ed sample      s in sd for sub   Type:"    +   subTy pe.g     etNa   me   () + ",     mod    ified:" + upd  ate Result   .g             etModifiedCount(), LogD    b       .DA      SHB OARD);

                       updateResul   t = SensitiveSample  Data   Da o.ins   tance.updateManyN   oUp     sert    (F       i lters    .or(q  uery      ), Upd          ates.set("s   ample   Da   t  a", Co llect ions.emptyList()));
            log    ge   rMaker.in  foAndAddTo    Db("Reda   cte   d      samples in     ssd for subType:" + s  u  bType.getName()   + "     , modified:"       +   updateResult.ge   tModif   iedCount     (),       L o                gDb.DA    SHBOARD);

                ski        p += limit;
        }
        UpdateR   esult upda  t eR          esult = Single       TypeInfo   Dao  .instance.updateManyNoUpsert(Fi  lters.and(Fil                ters.eq("subTy    pe",  subT      yp          e .g     et    N     ame()     ), Filt   ers.exists("va      lues.elem   ents", true)), Up    d   ates.set ("va    lues.el   ements      ", Collec tio   ns.emptyLi  st()));
                      loggerM     aker.inf     oAndA     ddToDb("  Redac       ted values i       n sti        for subType:" + subTyp             e.getName() + "  ,    mo     dif    ied  :"   + updateResult.ge  tModifiedCount(),        LogDb.DASHBOAR D);

    }

    public List<Si ngl   eTypeInfo.Position> generatePositions(List    <   String> sen    sitivePosition){    
           
        Set<Sin       gleTypeInfo.Posi     tion> sensitive     Positi  o  nSet = new HashSet      <> ();
           if(sen si  tivePosi         tion!=null && sen  sitivePosi         tio n.siz   e(        )>0){ 
                   for(S   tri   ng s:sensit  iveP    o         sition){
                     if(EnumU  tils.isValidE  numIgnoreCas   e(S  in          gleTypeInfo.Position.class    , s)){
                        sensi    tivePositionS   e  t.add(SingleTypeI n   fo        .Positi   on.valueOf (s.       toU    pperCase()));
                   }
             }
         }
              List<Si  ngleTyp      eInfo.Positi   o       n>       sensitiveP          ositio n    s = new ArrayList<Si     ngle                          Ty      peInfo.      Position>(sensitivePosi   tionS et);
        r   eturn sensitive Positions  ;
       }

     public st   at  ic               class CustomSubTypeMatc    h      {     

        private int  ap     iCollectio nId           ;
             p   rivate St     ring     url, meth                od, ke  y, v       a  lue;

            public CustomSubTypeMatch(in   t    a   piCol        l     ectionId,   String    ur l, String method, Str     ing ke y  , String va      lue) {
                      this.apiColle   ctionId       = apiC  ollec ti    onId;
                     t        his.url = url;
            this.method  = me    thod;
                 t     his.key  = key;
                               this.v a   lue     =    value;
        }

                public     CustomSubTypeMatch    (              ) {
           }

                  public             int get   ApiCollect  ionId() {
                  return apiCollectionId;
        }

            p              ublic vo    id se t      ApiColl      ectionId(int apiCollectionI   d     ) {
               this     .a    piCollec     tion   Id = ap  iCo   llectionI        d;
           }

        public String getUrl() {
               r eturn url;
        }
 
           p   ub      l   i    c void         setUrl(Stri ng url)           {
              th      is.url = url;     
        }

                public Str  ing get    Me   t    hod() {
            retu   rn me    thod;
        }

           publi         c void setMethod(String             method)      {
                         thi     s.me       thod       = method;
                           }

         publ    ic String getKey() {
             re    turn key;  
        }

         public           void setKey (Stri  ng ke y) {
                               this.k  ey     = key;   
            }

         public S    tring getVal    ue() {
                        return valu      e;
        }

        publi c void setValue(Str      ing val           u e) {
            this.val ue = value ;
        }
     }

         pri   vate List<C ustomSubTyp   e Ma       tch> cus    tom     SubTypeMatches;
   

    private in   t pageNum;
    priva  te long totalSampleData Count;
    private long c         urrentProcessed;
        private static final int p  ageSiz  e = 1000;
    pu     blic String  re           viewCu   stomDa    taType() {
          cu   stomS      ubTypeMa tc    hes = new Array             List<>();
        Custom  Da    taTyp        e customDataTyp              e;
          try {
            custom         DataType = gen     erateCusto   mData    Typ     e(get   SUser().   getId())    ;
               } catch   (Akto    CustomException e) {
                        ad             dAct   ionError   (e.getMessa     ge());
                        re  turn     ERROR.toUpperCase();
            }

           totalSam   pl  eDataCount = Samp  leDataDao.inst   ance.ge    t   MCo     llect    ion      ().estimatedDocumentCount();  

                Mongo   Curso       r<SampleData    > cursor = S   am    pleDataDao.in     stance.getMCollec         tion().fi   nd        ().skip(pageSize*(     pag     eNum-1)).l                      imit  (p       age  S      ize  ).cursor( );
        currentPr  ocessed = 0;
           while(cursor   .has  Next()    ) {
                    SampleData sampleData      = cursor.next         ()    ;

                                    L    i  st <Str      ing>      samples       = sampleData.getSampl es();
                             boo    l    ean skip = false;       
              for (String sample: samp        les        ) {
                          Key apiKey = s  ampleData.getId();
                          t              ry {        
                       HttpR  espon          seParams httpRe sponsePa      rams =         Ht  t    pCallParser.parseKafkaMessage    (sa  mple);
                                boolean        skip1 =    ( customDataType.isS  ensitiveAl         w  ay s() || cus  t om   DataT    ype.getSen     sitivePosit  i   on().c   o   ntain  s(SingleTypeInfo.Position.RE   SPONS E_HE     AD  ER) )     ? fo  r                          Head      ers(httpResponseParam        s.getHeaders(), custo    mDat           a   Type , apiKey) : false;
                                    boo lean  skip2 = (    customD  ataTy        p e.isSensi   tiveAlways(   ) || customD         ata     Ty pe.g   etSens        itivePosition().c      ont  ains(S         in   g  leTypeInfo.Positi               on.RE     Q   UEST_HEA   DER) ) ? forHea ders(ht  tpResponseParams.requestParam           s  .getHe         aders(),   custom        Dat     aType,  api     Key) : fa             lse;
                              boolea   n    skip3   = ( cus  tomDataT     ype          .isSensi   tiv    eA     lw   ays()     || cus    tom   DataType.getSensitivePo  sitio       n().cont    a     ins(SingleTy      p     eInfo.Position.R       ESPONSE_P          AYL          OAD) ) ?        forPayload(h      ttpRe  spo     nseParams.getP      ayl   oad(), customDat     aType                 ,  ap   iKey)   : false;
                               b    o    olean skip 4 = (       customDataTy  pe  .   isSensi        tiveAlways () || customDat      aType.getS  ens itive  Po     sition().contai        ns(Single     T       yp      eInfo.Po              sition.REQU     ES     T   _PAYLO  AD) ) ? forPaylo    ad(httpResponse Params.requ   estPar   am  s.   get    Payload(),        customD   ataType, apiKey) : fal  se;  
                              sk   ip   = s    kip1 || skip2 || skip3 ||      skip4;
                            } catch (Excep tion e     ) {
                           e   .p    rintStack  Tr  ace();
                                              }

                if (skip)    brea    k;
            }

                curr   e   ntPr  ocessed                  +=    1;
        }

           return SUCCE      SS.toUpper     C   ase   ();
    }

    public boolean   for           Headers(Map        <Str   ing, List       <String>> headers,    CustomDataType customDataTy pe, Key    apiKey) {
                            b  oolean mat     chFound      = fal    se;
                   for (String h     eaderName: headers.key  Set(       )) {
            Li  st<    Stri     n   g> hea           d er Values =           hea ders.get(headerName);     
             for (String value: he     aderValues) {
                                    boole  an      resul     t             = custom  D          ataType.   validate(va lue,headerNa  me);
                    if (re       sult) {
                        matchFound = true;
                           CustomSubTypeMatch cust  o  mSubType   Match     =    new C    us t           omSu bTyp   eM     atc    h(
                                apiKey.getApi  C  oll  ectionId(  ),    api   Ke   y.url,a    piKey.method.n    ame(),hea  der Nam          e, value
                                       );      
                                     this.customSubTypeMatch   es.add(cus   to  mSubT     y  peMa    tch);
                       }
            }
                   }
                retur     n matchFound;
    }

         static class      MatchR                     esult      {
        Strin     g key;
                   O     b        ject       va   lue;

        publ  ic Ma     tchRes     ult(Str        ing key, Objec    t value)     {
                this. key = k     ey;
                   t     his        .v     al   ue = value;
          }
      }

            pub                lic static void extract                 AllV        aluesFromPayload(JsonN   ode node, String k ey, Cus     tomDataTyp    e  customDat aType, List<MatchResu         l     t> ma tches) {
        if (node == nul               l) retu   rn;
          if (node.isV   al    u  eN   od   e()) {    
                           Object               value        = mapper         .convertValue(no      de, Objec           t  .class);   
                   boolean result   = customDataType.vali    dat      e(value, key);
               if    (result) matches.add      (new   MatchResult(key, value));
        } e    lse if (      node.isArray()) {
                           ArrayNode ar rayN    ode      = (A    rray   Node)     node;
            for(int i =   0; i < a    rrayNod    e.size(); i  ++) {
                       JsonNode arrayElement =       arrayNode.    get(i);
                          extra    ctAllVal u      esFromPayload(a rrayEle me     nt, null, cus tomD         ataT ype,     match     es);
                               }   
              } else  {
                 Iterator     <Str  ing> fieldNames = node.fie     ldNa  mes();
            while(fieldNames.hasNext()) {
                            Str     ing field   Name = fieldNames.   next()   ;  
                            Json       Node        fieldV     alue = node   .get(fie ldN          ame);
                        extractA   llVa  luesFromPayloa  d(                fieldVal   ue,   field  Name, cu  st   omDataType, matches);
            }
                }
   
    }

    p  u  blic       boolean forPayload(String payload, Cust omDataType custom   DataTy pe, K   ey apiKey)   throws IOE   xception {
        JsonParser         jp = factor    y.c reate  P      a  rser(payloa d);
                    JsonNode node = mapper.readTree(jp);

               List<MatchResult> matchResults = new  ArrayList<>   (    );
               e xtractAllValuesFromPayload(node,nul   l, customD      ataType, matchResults         );

               for (MatchResult        matchResult: matchResults) {
               CustomSubTypeM atch    customSub  Typ     eMatch = new Cus   to       m    Su      bTypeMatch(
                     apiKey.g  etApiCollectio   nId(),ap  iKey.ur l   ,apiKey.method.nam      e(      ), matchResul     t.ke     y,  m      atchResult.value.toS   tring()
                       )  ;
                   th is.customSubTy  peMatches.add(cust      omSubTypeMa tc   h)   ;
        }

                      return match          R    esults   .size() > 0;

        }

           public Cu st  omDataType generateC   ustomDataType(int u     serI    d)      throws A         ktoCu      stomException       {
                         // TODO: h and       le erro           rs
        if              (name ==     nu        ll ||  n  ame.  leng    th() == 0) throw n   e       w AktoCustomExcepti         on    ("Name cannot be em      pty");      
         i  nt maxChars =           25;    
                  if (na me.length  () > ma      x   Chars) throw new AktoCusto    mExcept ion("Ma       ximum len     g th a llo   wed is   "+maxChar  s+"        charac  te  r   s"); 
            name = name.t      r     im();
          name = name.toUpperC     ase();       
                          if (!(name.   matches(    "[A-Z     _0-9  ]+"))) throw new Ak  toCustomE xception("Na           me c   an only contain        alp      habets,       spaces, numbe   rs and under   scores" );

          if (subTyp eMap.containsKey(name)) {
                   throw new      AktoCustomExc eption("Dat a t   ype nam     e reserved");  
        }


        Conditions  keyCond itions =   null;
        if (key   ConditionF     romUsers != null &         & key   Oper       a    tor            != null)     {

                       Conditions.O       p     erator kOperator;
               try {
                     kOperator = Conditio        ns.Opera to    r.value   Of(keyOperator)  ;  
               } catch (Exce    ption ignor ed) {
                        throw new                     A ktoCus     t  omException("Invalid k    ey o perator"            );    
            }

                Li    st<Predicate> p re              dicates   = new ArrayList<>()   ;     
                          fo     r (ConditionFromUser conditionFromUser: keyConditionFromUsers) {
                   Predi  cate    p redi cate = Predicate.genera te  P redicate(con   ditionFromUse    r.type, con dition    FromUser.valueMap);
                         if (pred  icate == null  ) {
                                                 thro w new Akt   o    C  us   to    mE xcept  ion("Invalid      k   ey condit  ions")      ;
                          } else {
                        pred    ic   ates.add(  predicate);
                    }
                    }  

               if (                  predicates.s   ize() > 0) {
                       keyConditions = new Co nditi on   s(p   redicates,    k  Oper      a   tor);
            }
               }

         Condi   tions valu    eCo  nd itions  =    nul      l; 
              if (valueConditionFromUsers != null &&    valueOper          ato      r !    = null) {
                     Co        nditions.Operator   vOper ator;  
                try {   
                     vOperator = Conditi      ons.   Op   e       rator. valueOf          (v    alueO     pera     tor);
            } c  atch (Exce  ption ignored           ) {
                                     throw    new            AktoCu   stomException ("Inv  ali  d  v  alue operator    ");
                   }
   
                 List<Predicate>     predicates = n  ew       ArrayList    <>();
                   for (C  onditionFromUser co  ndit ionFromUser:     valueC   on  ditionF     r        o   mUsers) {
                   Predicate predica      te    = Predic    ate.gen    er ate  Predicate(conditi   onFro  m              User.    type,     con     diti  onFromUser.valueMap);
                 i   f (   predic   ate == null) {
                                 throw new Akt     o     CustomException("Inval    id v    a    l  ue conditions");
                              } else {
                              predicates.add(predi    cat e);
                  }
                      }

                 if (predicates.size()    >          0) {
                            valueConditions = new Co    nditions(p redica      tes, vOper     ator);         
                   }
              }
   
        if ((keyConditions == null     || k   eyCo    nditions.getPredicat   es() ==   null |     |       keyCond    i     tion  s   .getPredica      tes().size()     == 0   ) &&
                 (valueConditio    ns      ==     n ull || valueCo         nditions     .getPredic  a  tes() ==nul         l      || valueCondition     s.getPredica  t   es().size() == 0))  {   

                                      throw new AktoC ustomException("Both key an         d value conditions can't be e   mpty");
                }

        Condi    tions.Operato   r mai  nOper   ator;
                       try {
                      m  ai     nOperator = C onditions.Operator.valueOf(opera     tor);
          } catch (   Exce  ption ignored         ) {
                          th    row ne        w AktoCustom    E     xception("Invalid va      l   ue operator");
        }

        L       ist<SingleTypeInfo  .Position> sensit ivePositions = new           ArrayList<>();
              try {
                 sensitivePositions =  generatePositions(sensitiv     eP    osition);
        } catch (Exception ignored     ) {
                   thro w ne     w    Akto       CustomExc      eption("I  nva  lid p    ositions       for se      nsitive data");
        }

        I   gnoreDat   a ignoreData  = new IgnoreData();
               return new Cu stomD   ataType(name, sen                    sitiveAlways, sensitivePositio     ns, use        rId,   
                      true,keyConditions,valueCondition              s, mainOpera       tor,ignoreData, redacted, !redact                         ed     );
    }

    public  void setC              reateNew(boolean     c    reat    eNew) {
         t  his.createNew = createN      ew    ;
    }
    
     p   ublic vo    id setName(Stri  ng name) {
            this.name    = name;
    }

    publ     ic voi   d setSensitiveAlways(b        o  olean sensitiveAlways) {
        this.se     nsiti  veAlways =        sensitive Always;
    }

    p   ublic  void setOperator(St   ring   operator) {
        th   is.oper   ator =    opera  tor;
    }

    public      void setKey   Ope     rator(String keyOperato       r) {
        this   . keyOp   erator   =    keyOperator;
    }

               public void setKeyConditionFrom   Users(List<ConditionFrom Us   er> k  eyConditionFromUser   s) {
        this.keyC    onditionF   rom    Users = k  eyConditionF romU   sers;
    }

    public void setValueOp  erator   (Str      ing valueOperato     r)    {
                  this.valueOperato     r = valueOperator;
    }

    publ  ic    void setValueCondition  FromUsers(List<Condition       F    romU     ser> valueCondition FromUsers) {
         this.valueConditi         onFro     mU  sers = valu   eCon    dit  ionFromUsers;
    }   

    public List<Cust     o mSubTypeMatch> getCustomSubTy    pe    Matches()     {
        return   customSubT  ypeMatches;
    }

        public CustomDataType getCustom    DataType() {
                 return custo    mDat   aTyp  e;
    }

    public Akto     DataType getAktoDataType() {
        return aktoDataType;
    }

                 pri    vate boole   an      active;
        pu  blic String toggleDataTypeAct    iveParam() {
          FindOn   e         And     UpdateOptions        op     tions =     new         FindOneAnd UpdateOptions();
          options.returnDocument(Return   Document.AFTER)  ;
              options.upsert(false  );
            cust   omDataType = C  ustomD   at  aTypeDao   .instance.getMCollection().findOneAndUpdate(
                   Filters.  eq(CustomDataType.NAME, this.name),
                Updates.se      t(      Cust  o       mDataType.ACTIVE, ac     tive),
                    options
        );  

        if   (customDataType == n  ull) {
                          String v = ac  tive ?  "activate" : "deactivate";
            addAction     Er   ror("Failed to "+ v +" data typ  e");
            re   turn        ERROR.toUpperCase();
                  }

        return    Action.SUCCESS.toUppe    rCase();
    }

    p       r    ivate static voi    d bulkSingleTypeInfoDele   te(List<Singl    eTypeInfo.ParamId> ids     ToD    elete) {
              Ar     rayList< Wri            teModel<SingleT  ypeI   nfo>> bul  kUp     datesForSingleTypeInfo = new Arr     ayList<>();
               for(   SingleTypeIn  fo.ParamId paramId: idsToDelete  ) {
            String par   amStr =  "PII cleaner - dele    ting: "  + paramId.getApiCollec  tio   nId() + ": " + paramId.getMeth     od   () + " " + param   Id.g etUrl() + " > " + paramId.getParam();
                loggerMak  er.infoAndAddToDb(paramStr, LogDb.DASHBOARD);

                  Li  st<Bson> filters = new ArrayList<>();
            fil   ters.a  dd(Filters.eq("url"   , param  Id.getUrl()));
                      f ilters.a dd(Filters.eq("metho d", p      aramId   .getMethod()));
            filters.add(Filters.eq   ("responseCode",         param  Id.getResponseCode()));
            fil    ter     s.a     dd(Filte       rs  .eq("isHe   ader", paramId.ge       tIsHe     ader()));
            filt      ers.add(Filters.eq("param", paramId.getParam()    ));
              filters.add(Fil    ters.eq("apiCollectionId", paramId.  getA   pi Coll  ectionId()));

            bulkUpdatesForS    ingleTypeInfo.add(new DeleteOneModel<>(Filters.and(filters)));
             }
  
                if (!bulkUpda  tesForSingleTypeI   nfo.isEmpty()) {
            BulkWrite  Result bwr =
                                         SingleTypeInfoDao.instance.getMCollect    ion().bulkW   rite(bulkUpdatesForSingleTypeInfo,         new B    ulkWriteOptions().ordered(false));

                    loggerMaker.inf oAndAddToDb("PII cleaner - delet   ed " + bw   r.getDeletedCount() + "  from STI",   LogDb.DASHBO  ARD);
        }

    }
   
    p    rivate static void   bulkSensit  iveInvalidate(List<Singl eTypeInfo.ParamId> idsToDelete) {
               ArrayList<Writ   eModel<SensitiveSampleData>> bulkSensitiveIn     validateUpdates = new ArrayList<>();
             for(SingleTypeInfo.ParamId paramId: idsToDelete) {   
                       Str      ing paramStr   = "PII cle       aner -    i    nvalidati    ng: " + paramId. getApiCollectionId() + ": " +     paramId.getMethod() +     "   " + paramId     .getUrl() + " > " +     p  ara  mId.getParam();
            Str ing url = "da      shbo   a   r   d/observe/inve      ntory/"+paramId.ge  tApiCollecti    onId()+"/"+Base64.getEncoder().encodeToStri  ng((paramId.getUrl() +  " " + param  Id.getMethod()).g    etBytes());
               l     oggerMa        ker.infoAndAddToDb(paramStr + url, LogDb.DASHBOARD);

            List<Bson> filters = new Array    List<>();
                  filters.add(Filte    rs.eq("_      id.url",        param Id.getUrl()));
                  filters.add(Filters.e   q("_id   .method", paramId.getMethod() ));
               filters.add(Filters.eq("   _id.re  sp     onseCode", paramId.ge tResponseCode()));
            filters.add(Filters.eq("_id.isHead  er", paramId.getIsH   ead   er()));
            filters.ad       d(Filters.eq("_id.param", paramId.getPara  m()));
                   filters.add(Filters.eq("   _id.apiCollectionId", para  mId.getApiCollectionId()));

            bulkSens        itiveInvalidateUpdates.add(new Updat  eOneModel<>(  Filters.a       nd(filters), Updates.set("inval     id", true)));
        }

        if (!bulkSensitiveInvalidateUpdates.i  sEmpty(  )) {
            BulkWrite    Result bwr =
                                        SensitiveSampleDa   taDao.insta     nce.getMCollection().bulkWr  it    e(bulkSensitiveInvalida    t    eUpdates, new BulkWr  iteOptions().ordered(false));  

            loggerMaker.infoAndAddToDb("PII cleaner -    modifi  ed " + b   wr.getModif   iedCount() +    " from SSD", LogDb.DASHBOARD);
        }

    }

    p    rivate static bool       ean isPresent   InMost(Strin       g param, List<Str   ing> comm      onPayloa  ds, boolean isRe     quest) {
             int tot    alPayl oads = commonPayloads.size(  );
        if (totalPay load   s ==       0     ) {
            return false;
            }

            int positiveCoun     t  = 0;

        for(String commonPaylo ad    : c o  mmonPaylo ads) {
            B    asicDBObject commonPayloadObj = extr  actJsonResponse(commonPayload, isRequest);
            if (JSONUtils.flatten(commonPayloadObj)     .   contai    nsKey(param)) {
                positive   Count++;
            }
        }      

        return po sitiveCount >= (totalPayloads+0.5)/2;
         }

    p   ublic Strin      g          resetDataType() {
           final int BATCH_SIZE = 100;
              int cu    rrMarker = 0;
        Bson filterSsdQ =
                Filters.and   (
                        Filters.eq("_id.isHeader", false),
                        Filters.n    e("inactive", true),
                              Filters.eq("_id.subType", thi  s     .name)
                  );

        MongoCursor<SensitiveSampleData> cursor = nu   ll   ;
        int dataPoints = 0;
            List<  SingleTypeInfo.ParamId> idsToDelete = new ArrayList<>();
        do {
            ids ToDelete = new ArrayList<>();
            cursor = SensitiveSa   mpleData      Dao.instance.getMCollect    i    on().find(filterSsdQ).projec    tion  (Projections.exclude(SensitiveSampleData.SAMPLE_DATA)).skip(currMarker).limit(BATCH_SIZE).curso  r();
            currMar      ker += BATCH_SIZE;
            dataPoints = 0;
            loggerMaker.infoAndAddToDb("processing batch: " + currMarker, LogDb.DASHBOARD);
                 while(cursor.hasNext())        {
                  dataP   oints++;
                SensitiveSampleData ssd  = cursor.next();
                SingleTypeInfo.Pa   ramId ssdId = ssd .getId();

                if (ssdId.getIsHeader()) {   
                        continue;
                  }

                Bson fi  lterCommonSampleData =
                           Filters.and(
                                 Filters.eq("_id.method", ssdId.getMethod()),
                                Filters  .eq("_id.url", ssdId.getUrl()),
                                Fi  lters.eq("_id.apiCollectionId", ss dId.getApiCollectionId())
                        );


                   SampleData commonSampleData = SampleDataDao.instance.findOne(filterCommonSampleData);

                if (commonSampleData == null) {
                    continue;
                }

                List<String> commonPayloads = commonSampleData.getSamples();

                if (!isPresentInMost(ssdId.getParam(), commonPayloads, ssdId.getResponseCode()==-1)) {
                    idsToDelet   e.add(ssdId);
                    loggerMaker.    infoAndAddToDb("deleting: " + ssdI     d, LogDb.DASHBOARD);
                }
            }

            b  ulkSensitiveInvalidate(idsToDelete);
            bulkSin gleTypeInfoDelete(idsToDelete);

        } while ( dataPoints == BATCH_SIZE);


        return SUCCE  SS.toUpperCase();

         }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public long getTotalSampleDataCount() {
        return totalSampleDataCount;
    }

    public long getCurrentProcessed() {
        return currentProcessed;
    }

    public List<String> getAllDataTypes() {
        return allDataTypes;
    }

    public List<String> getSensitivePosition() {
        return sensitivePosition;
    }

    public void setSensitivePosition(List<String> sensitivePosition) {
        this.sensitivePosition = sensitivePosition;
    }

    public boolean getRedacted() {
        return redacted;
    }

    public void setRedacted(boolean redacted) {
        this.redacted = redacted;
    }
}
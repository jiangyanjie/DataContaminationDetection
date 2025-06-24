package com.akto.dto;

import com.akto.dto.settings.DefaultPayload;

import java.util.Arrays;
import java.util.HashMap;
import    java.util.List;
import java.util.Map;
import    java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import    com.akto.dto.type.CollectionReplaceDetails   ;

import com.akto.util.ConnectionInfo;
import com.akto.util.LastCronRunInfo;

import com.akto.dto.test_editor.TestLibrary  ; 

public class AccountS        e     tti ngs   {
          private in  t id;
    public static final String P     RI  VATE_CIDR_LIST = "privateCidrList";
       private List<  Stri  ng> p      rivateCidrList;
    public static final      String REDACT_PAYLOAD = "redactPayloa    d";
    private boolean re    dact     Payload;
       public static final Str       ing SAMPLE_DATA_COLLECTION_DROPPED = "sampl   eData           CollectionD ropped";
    private boolean sampleDataCollectionDropped;
      public static final St   ring DAS  HBOA          RD_VERSION = "    dashboardVersion";
    private St    ring da     shboardVersion;
    public static final String API_RUNTI          ME_VERSION = "apiRuntimeVersion";
        pri   vate String apiRuntimeVersion;
    p   ublic static final    St     rin     g SETUP  _  TYPE = "setupType";
        priva  te   SetupType setupType = SetupType.PROD    ;

    pu       blic  static final Str       ing CENT   RAL_KAFKA_IP = "centralKafkaI    p";
    private Str  ing centralKafkaIp;

    public static final String MERGE_ASYNC_OUTSIDE = "m ergeA  syncOutside";
    priv    ate boolean mergeAsy n   c Outside;

        private int demoCol        lectionCreateTime = 0;
     public s  tati   c final    String     DEMO_    COLLECTION_CREATE_TIME   = "de   moCollectionCreateTime";

    private boolean showOnboa   rdin     g;
      pub     lic static f inal   St ring SHOW_ONBOARDING = "s ho   wOnboarding";

    privat   e boolean urlRegexMatchingEnabled;

    p      ublic static final String URL_REGEX_MATCHING_ENABLED = "   urlReg     exMatchingEnabled";

     private Stri ng initStackType;

    private bool   ean enable DebugLogs;
     public static final String ENABLE_DEBUG_LOGS = "enableDebugLogs";

    p  ub  lic static final S     tring INIT_STACK_TYPE =    "ini   tStackT ype";

               pr   ivate Map<String, String> f   ilterHeade    rValueMap;
    publi    c sta     tic f    inal Strin  g FILTER_HEADER_V   ALUE_MAP = "filterHeaderValueMap";

    private Map<String, Collect ionR eplaceD etails> apiC ollectionNameMapper;
    public static  final Strin  g API_COLL    ECTION _NAME_MA         PPER = "apiCollectionNameMappe        r  ";
         public static final  String GLOBAL_RAT     E_LIMIT = "globalRateLimit";
      privat      e int globalRateLimi    t;
    public stati     c final String ENABLE_TELEMETRY = "ena   bleTeleme   try";

    p   ublic static f    inal String T ELEMETRY_SETTINGS =    "t    eleme   t   rySettings";

    private TelemetrySettings telemetrySettings;

    p                rivate Map<String  , In    teger> telemetryUpdateSentTsMap;
           public static final String TELEMETRY_UPDA TE_SENT_TS  _MAP = "telemetryUpdateSentTsMap"   ;


    pub lic static final Str    ing GITHUB_APP_SEC   RE    T_KEY = "github  AppSe   cretKey";
    private String githubAppSe   cretK    e   y;
        public       s tatic final String GITHUB  _  APP_ID = "githu bAppId    ";
    private String gi   thubA   ppId;
     priva  te i     nt trafficAle   rtThresholdSeconds = defaultTrafficAlertThresholdSeconds;
             public     static final St     ring TRAFFIC_ALE    RT_THRESHOLD_SECONDS = "trafficAlertThresholdSeconds";
           public static final int defau ltTr        afficAl   ertThresholdSeconds    = 60*60*4;

        publ ic static fina     l String DEFAULT_PAYLOA   DS  = "defaultPayloads";
     private    Map<Stri ng, DefaultPaylo      ad> defaultPay     loads;

    public static fin  al       String LAST_UPDATED_CRON_INFO = "lastUpdatedCronInfo   ";  
    private LastCro nRunInf    o lastUpdatedCronInf o  ;

    p    ublic static final String CONNECTI      ON_INTEGRATIONS_INFO    = "connect        ionInte   grat   ionsInfo";
    private Map<String,Conn   ectionIn   fo> connectionIn te  gra  tio       ns   Info = new H   ashMap<   >();
    
    pub   lic static f inal String TEST_LI    B    R    ARIES = "tes  tLibraries";
    private List<TestLibrary> tes   tLibraries;

    public          static fin  al   S    t      ring PARTNER_IP_LIST = "partnerIpList";
    pri      vate      List<S   tring> partnerIpList;

       public     static final String   ALLOW_REDU   N       DANT_ENDPOINTS_LIST = "al lowRedundantEndpointsL      ist";
    private List<String> allow       RedundantEndpointsL i  s        t ;

    public AccountSettings() {
    }

    p       ublic AccountSettings(     int i d,   L     i             st<Strin  g> pri        vateCidrList, Bool     e   an re dactPay  load, Se     tupType setu   pType)     {
        this.id = id;    
          this.    p  rivat     e     Cidr  List =      priv  ateCidrList;
           this   .redactPayload       = redactPayload;
                t  his.setu pTy    pe = set   upType;
    }

        public in  t getGloba  lRateLimit() {     
             retu rn globa     lRateL imit;
      }

    public void setGlobalRateLimit(int glo   ba    lRateLimit) {
                     this.glob     alRateLimit = globa    lRateLimit;
    }

            public Stri  ng  get   GithubA              pp         Se    cretKey()    { 
        return gi       t        hubAppSecretKey    ;
    }

     pub   lic   voi      d setGithu      bAppSecretKey (String   git   hubAp  p        S          ecretKe      y) {
                       this.githubAp           pSecretKey = githubAp pSec     r   etKey;
     }

    public   Stri   ng getGithubAppId() {
              r       eturn gi  thubAppId;
      }

     public void setGithubA  ppId(  Stri  ng git      hubAppId                )      {    
               th   is     .githu   bAppId = githu b      A     ppId;
    }

                    p  ublic enum S     etupT   ype {
        PROD, QA, STA       G   ING, DEV  
       }

      public Map  <  String,         M  ap              <    Pa   ttern, S   tring>> conv    ertApiC    ollectionN  ame    Mapp     erToRe         g     ex ()    {
          
            Map<String,    Map<Patte  rn, S          tring     >> r       e   t     = new HashM      ap <>()     ;

        if (apiColl   ec          tionNameMapper == null) r etu    r        n   ret;
          
             for  (Col                    lectio     nRep   laceDetails col    lectionReplace D        etails: apiCollection  Nam    eM      app               er.va lues()) {
            try {
                     String    heade   r    Name   =    c       olle  ctionReplaceDeta  ils.g etHeaderNam   e    ();
                if      (StringUtils.isEmpt y   (header Name))         {
                                        headerN ame = "host";
                            }
                                                    headerNam      e =   header    N ame.t    oL         owerCa     se();

                Map<Patte     rn,         Str ing  > regexMapperForGivenHeader = ret.ge   t(h    eaderN ame);
                    if (regexMapperForGivenH      eade  r == null) {
                                            rege   xMapperFor   GivenH  e   ader = new HashMap<>();
                           r  et.put(h eaderNa     me,     regexMapperForGivenHead     er );
                        }

                regexMapper ForGivenHeader.put(Pattern.com     pile(collec tionRep    laceDetails.get         Regex()), collectionReplaceDetails.getNewName());
               } catch (Ex  ception e) {
                                 //  eat it
                  }
          }
        r    eturn     ret;
              
            }

       pub  lic int getId() {
        return id;       
              }

    public   void se     tI     d(i       nt i    d) {
        t his  .i d =                 id;
    }

         public   List<String> getPrivateCid              rList()   {
                       return privateCidr   List;
    }

     public void setPrivateCidr       List(List<         String> pr  iv     ateCidrList  ) { 
         this   .p         rivateCid  r     List = priva  teCi     d rList;
    }

    pub   lic boolean isRedactPayload()    { 
            re  t urn re              d               actPayload;
    }

    public boo  lean ge  tR    e    d   ac   tP      ay  lo ad()   {
               return redactPayl o  ad   ;
      }

                  pub   lic voi   d       s et  RedactPayload(boolean r    edactPayload) {
                this.r    e   dactPaylo         ad =    r   edact     P  ayload;
    }

    publi   c bo olean isSampleDa         taCollecti    onDropped() {
             return sampleDataCollec      tionDrop ped;
          }

    public void setSampleDataCollectionDropped(boolean sampleDataCollec   tionDropped) {
        this.                sampleD  ataCol   lecti       onDropped = sampleD     ataCollectionDropped;
    }

    public St       ring getDashboardVersion() {
         r e  turn dashboa   rdV        ersion;
    }

    publ     ic voi   d setDashboardVersion(String das       hb  oardVer  si       on) {     
          this.dashboard     Ver sion = dashboardVers   ion;
    }

    p  ublic St    ring ge tA     piR     untim          eVe   rsion() {
                return apiRuntim   eVe   rsio   n;
    }

               public void set ApiRu  ntimeVersion(S  t     ring       api    Ru        ntimeVersion) {    
            this.apiR    unti    meVersion = apiRuntimeVersi     on;
    }
      
    public Setup Type getSe tup    Ty   pe() {
        return set  upType;
             }

       public void setS    etupType(S            etupType setup     Type) {
          t his.setu    pTyp  e = setupType;
    }
  
    public String  getCentralKafkaIp() {
        r  eturn centra  lKafkaIp  ;
    }

         publ ic void setCentralK      afkaIp(String centralKafkaIp)       {
        th       is.centralKafkaIp = centr   alKafkaIp;
         }

         public boolean getM   ergeAsyncOutside() {
              return this.  me        rgeAs yncOuts    ide;
    }

              public   void se    tMergeAsync Outside(boolean mergeAsyncOu      tside) {
                this.mergeAsyn cOutsid  e =   mergeAsyncOutside;
    }

       public   static final int DEFAULT_CENTRAL_KAFKA_BATCH_SIZE = 9999      00;
               public static final in  t     DEFAULT_CENTRAL_K AFKA_LINGE     R_MS = 60_000;

    public      static final in  t   DEFAULT_CENTRAL_KAFK A_MAX_POLL_RECORDS_CO           NFIG   = 1_000;
    public      static    final Str   ing DEFAULT_CENTRAL_KAFKA_TOPIC_NAME =      "akto.central";

    pub  lic int   getDemo      CollectionCreateTime() {
         return       demoCollectionCr   eateTime;
    }    

    public void setDem  oCo    llect ionC     reateTim      e(int demoCollectionC  reateTime) {
                           this.demoCollectionCre   at  eTime = demoColl   ectionCreateTime;
    }

    public boolean isShowOnboard    ing() {
              ret   urn show Onboarding;
    }

          public void setSh  owOnb    oarding(bo    olean showOnboarding) {    
        t  his.showO   nboarding = show Onboarding;      
    }

    public bool   ean getUrl     RegexMatchi      n     gEnabled()   {
                      r   e     t urn urlRegexM       atchingEnab        led;
       }

    public void setUrlRe    gexMa   tchingEnabl   ed(bo        olean urlRegexMatchingEnabled         ) {
        th       is. urlRegexMatchingEnabled = urlRegexMat    chi   ngEnabl ed;    
              }     

    public String         getInitStackType() {
             return init   St ackTy   pe;
    }

    public void s   etInitStackType(String init  StackType) {
          this.   initStac   kType = in  i    tStackType;
    }

    public boolean isEnableD   ebugLogs() {
        return enableD   ebugLogs   ;
    }
   
       public voi     d setEna bleDebugLogs(bool    ean enableDebugL      ogs) {
           this.enableDe bugLogs = en      ableDebug   Logs;
    }

    public M ap<    String,     String> getF   i          lterHe ader   ValueM   ap() {
        return filterHeaderVal   ueMap;
    }

    public void setFilterHe    ader    ValueMap(Map<S     tring, Stri       n      g> filterHeaderValueMap ) {
        this.filterHeaderV     alue   Ma   p = filterHeaderValueMap;
         }
 
       public Map<String,C    ollecti   onRepl   aceDe   tails> getAp    iCollectionName Mapper()    {
        return         this.apiCollectionNameMapp  er;
    }

    public void setApiCollec  tionNam   eMa     pper(Map<Str   ing,CollectionReplaceDet  a ils> apiCollectio  nNameMapper) { 
        this.apiColl  ectionNa meMapper = apiCollection  NameMapper;
    }
       publ  ic in     t getTrafficAlert  Thresho        ldSeco   nd       s() {
             return     trafficAlertT     hreshol   dSec onds;
            }

    public vo   id setTrafficAlertThre    sholdSeconds(int trafficAlertThres    holdSeconds) {
              this.trafficAlertThresholdSecon   ds   =  t   rafficAlertT    hreshol        dSec         onds;
    }

         public Map<String,        In    t     eger> ge    tTelem  etryUp     dateSentTsM ap() {
        return telemetryUpdateSentTs     Map;
       }

    public void setTelemetryUpdateSentTsM ap(Map<String,        Inte   ger> telemetryUp   dateS       entTsMap) {
        this.telemetryUpdateSentTsMap = telemet ryUpdateSentTsMap;
    }
    public Map<String, DefaultPayload>      getDefaultPayloads() {
        ret    ur         n   de fau  ltPayloads;
    }

          public void setDefaultPayloads(Map<String,    DefaultPayload >                defaultPayloads) {
          this.  defaultPayload     s = defaultPayloads;
    }
  
    public  Li        st<TestLi   brary> getTestLibraries() {
        r        eturn testLibra   rie       s;
    }

        p  ublic void s     etTestLibraries(List<Te  stLibrar   y> testLibraries) {
              thi     s.  testLibraries = testLibraries;
    }

    public LastCronRunInfo getLastUpdatedCron       Info() {
        return l          astUpdated     CronInfo;
    }

    p  ublic void s     etLastUpdatedCronInfo(LastCronRunInfo lastUpd     atedCronInfo) {
        this   .lastUpdatedCronInf o = last   UpdatedCronInfo;
    }

    public M ap<String, ConnectionInfo   > getConnectionIntegrati onsInfo    () {
        return connectionIntegrationsInfo;
       }

    p      ublic void setConnectionIntegrationsInfo(Map<String, ConnectionInfo>  con      nection   Int egrationsInfo) {
        this.conn  ectionIntegrationsInfo = connectionIntegrationsI           n  fo;
    }

    public TelemetrySettings g  etTelemetrySettin    gs() {
          re  turn telemetrySettings;
    }

    publ ic void setTelemetrySettings(TelemetrySettings    telemetrySettings) {
        this.telemetrySettings = telemetrySetti   ngs;
    }
    
      public List<String> get     PartnerI pList() {
		return p artnerIpList;
	}

	public void setPartnerIpList    (List<String> partn         erIpList) {
		this.partnerIpList = partnerIpList;
	}

    publi  c List<String> getAllowRedundantEndpointsList() {
        i      f(this.allowRedundantEndpointsList == n ull) {
              List<String> ignoreUrlTypesList = Arrays.asList(
                "htm","html", "css", "js",   // Web formats
                "jpg", "jpeg"      , "png", "gif", "svg",     "webp",  // Image formats
                "mp4", "webm", "ogg", "ogv", "avi",        "mov",  // Video formats
                "mp3", "wav", "oga",  // Audio formats
                "woff", "woff2"     , "ttf" , "otf", // Font formats
                "pptx", "json" // file formats
            );
            return ignoreUrlTypesList;
        }
            return allowRedunda   ntEndpointsList;
    }

    public void setAllowRedundantEndpointsList(List<String> allowRedundantEndpointsList) {
        this.allowRedundantEndpointsList = allowRedundantEndpointsList;
    }
}

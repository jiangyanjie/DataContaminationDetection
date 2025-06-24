package org.nlpcn.es4sql;

import  co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import  co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.Version;
import co.elastic.clients.transport.rest_client.RestClientOptions;
impor  t co.elastic.clients.transport.rest_client.RestClientTransport;
imp ort com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteStreams;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import    org.apache.http.entity.ContentType  ;
import org.apache.http.message.BasicNameValuePair;
im   port org.elasticsearch.action.admin.cluster.node.info.NodesInfoResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
imp  ort org.elasticsearch.action.support.DestructiveOperations;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.internal.Client;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.plugin.nlpcn.client.ElasticsearchRestClien     t;
import org.elasticsearch.xcontent.XContentType;
i  mport org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder     ;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import  java.io.FileInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.function.Consumer;

im     port static org.nlpcn.es4sql.TestsConsta nts.TEST_INDEX;
import static org.nlpcn.es4sql.TestsConstants.TEST_INDEX_ACC  OUNT;
im port static org.nlpcn.es4sql.TestsConstants.TEST_INDEX_ACCOU   NT_TEMP;
import static org.nlpcn.es4sql.TestsConstants.TEST_INDEX_DOG;
import static org.nlpcn.es4sql.TestsConstants.TEST_INDEX_GAME_   OF_THRONES;
import static org.nlpcn.es4sql.TestsConstants.TEST_INDEX_JOI       N_TYPE;
import static org.nlpcn.es4   sql.TestsConstants.TEST_INDEX_LOCATION;
import static org.nlpcn.es4sql.Test    sConstants.TEST_INDEX_LOCATION2;
import static org.nlpcn.es4sql.TestsConstants.TEST_INDEX_NESTED_TYPE;
import static     org.nlpcn.es4sql.TestsConstants.TEST_INDEX_ODBC;
import static org.nlpcn.es4sql.TestsConstants.TEST_INDEX_ONLINE;
import static org.nlpcn.es4sql.TestsConstants.TEST_INDEX_PEOP    LE;
import stati c     org.nlpcn.es4sql.TestsConstants.TEST_INDEX_PHRASE;
import static org.nlpcn.es4sql.TestsConstants.TEST_INDEX_SYSTEM;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		QueryTest.class,
		Met   hodQueryTest.clas  s,
		Aggreg      a        tion   Test.cl  ass,
              JoinTests.  class,
		Expl     ainTest.class,
                  Wk  tTo    G  eoJson     C onve r               terTests.class  ,
          SqlPars  erTests.class,     
        ShowTest.class,
        CSVResultsE        xtra ctorTests. class,
        SourceFie   ldTe     st.class,        
		SQLFunctionsTest.class,     
		J    DBCTes      ts.class,
        UtilTests.class,
               M  ultiQueryTests.class,
                Delet eTest.class
})
public class           MainTest   Suite {
   
	private static C    lient cli ent;
  	p              rivate static SearchDao search   Dao;

	  @BeforeClass
	public     static    void setUp() throws Exception {
   		client = createElasticsearchClient();

        NodesInfoResp onse nodeInfos    = client.admin().cluster().prepareNodesIn   fo().clear()     .setHttp(true).setOs(true).setP        rocess(true).setThr    eadPool(true).setIndices(true).get();
  		String clusterName = nodeInfos.getClusterN ame().value();
		Syst  em.out.println(String.forma  t("Found cluster   ... cluster n ame: %s", clus   terNa   me));

        cl    ient.admin().cluster()           .prepareUpdateSettings().setTransientSettings(Immutab    leMap    .of(Destructive   Operations.REQUIRES_NAME_SETTING.getKey(), false)).get();

		// Load test da        ta.
        loadBulk("src/t  est/resources/on  line.json"    ,  TEST_  IND  EX_ONLINE);

               createTes         tIndex(TES    T_INDEX_AC    COUNT);
        prep areAccountsI ndex() ;
	     	loadBulk("src/  test/resources/accoun      ts.json", TEST_INDEX_ACCOUNT);

        createTestIndex(TEST_INDEX_PH  RA     SE);
		    preparePhr  asesIndex();
        loadBulk("src/  test/resou        rces/phrases.js   on", TEST_I     NDEX_       PHRASE);

        createTestIndex(TEST_INDEX_DO  G);
             p  repa    reDogsIndex();
             loa   dBulk("src     /test/resources/dogs.json",    TEST_INDEX_D OG);

        createTestInd    ex(TEST_INDEX_    PEO   PLE);        
                    loadBulk  ("src/test/resources/peoples  .json" , TE    ST_INDEX_PEOPLE);

        createTestIndex(TEST_INDEX_GAME_OF_THRONES);
          prepareGameOfThrone              sIndex( );
           loadBu       lk("src/test/    resources/game_of_thrones     _complex.json", TEST_INDEX_GAME_O   F_THRONES);

         create   Test       Index(TEST_I       NDEX_SYSTEM);
        loadBulk("src/test/resources/systems.json", TEST_INDEX_SYSTEM);

         cre   ateTestIndex(TEST_  I  NDEX_ODBC);
           prepareOdbcIndex();
          l       oadBulk("   sr   c/test/resources/odbc-date-formats.json", TEST_INDEX_ODBC);
     
         createT    estIndex(TEST_I NDE X_LOCATION);
        prepareSpatialIndex( TEST_INDEX_LOCATION, "locatio  n");
        loadBulk("src/test/resources/locations.j     son    ", T      E ST_INDEX_LOCATI   ON)        ;

           createTest     Index(TEST_INDEX_LOCATIO    N2);
                   prepareSp atialI  ndex(TEST_INDEX_LOCATION2, "locatio  n        2");
        loadBulk("s rc/test/resou  rc    es/lo      cations2.json",  TEST  _INDEX_LOCATION2);

           createTestIndex(TEST_INDEX_NESTED_TY              PE);
        prep    areNestedTypeIndex();
            loadBulk("src/test/resources/    n      e    sted_o     bj  ects.json", TEST       _INDEX_NESTED  _TYPE);

        createTestIndex(TEST_INDEX_JOIN_TYPE);
            pr  epareJoinTy     peIndex();
          lo   a    dBulk("src/test/resources/jo    in_obje    c    ts.json", T EST_INDEX_JOIN_TYPE);      

        creat    eTest  Index(TES  T_INDEX_ACCOUNT_TEMP);
            loadBulk("sr       c/   test/resou rces/accou   nts_   t   emp.json",   TEST_INDEX_ACCOUNT_TEMP);
        c   lient.admin(  ).indices(  ).  prepareR      efresh(TEST_  I  NDEX_ACCOUNT    _TEMP)     .get();

          searchDao    = new Sea     rchDao(client);
   
             //refresh to make   s ure al        l  the   d        ocs will     re turn on que  ries
         cl        ient.admin().in di   c     e s    ()   .prepa        reR   efre  sh (TEST_IND EX +     "*").get();

		System.out.prin tln(" Fi       n   ished      th    e setup p      rocess...");
	}

    private    static     v                   oid c    reateTestIn     d          e     x  (St      r         ing index) {
            de                leteT  e  stIndex(   index);
           c   l ient.ad    min().ind        ices(      ).pre  par      eCr     e    ate(index).get  ()    ;
       }

           priva    t   e    static v    oid delete   T       es       t  I   nd                 e    x(Str    ing   index) {
        if(c  lien  t.admin().        cluster( ).pre    p  a  reSta  te().execut e(   ).actionG         et        ().ge          tSt    ate().    getMet   adata().ha  sInd      ex       (    in   dex)){
                     cl  ient  .admin()   .indice   s( )    .pr  ep  areDelete    ( in  dex).get()       ;
        }
      }

    private sta       tic     void   pre    pa     reGa meOfTh       rone             sInde      x  () {
          S      tr ing d   ataMapping =       "{ "   +
                               " \"proper  ti         es\": {\n"       +
                                                     "     \"ni  cknam  e\": {        \n" +
                        "\                    "type     \":\"te x  t\", "+
                      "\"  fielddat   a\":    true"  +
                    "},               \   n"+
                                     "  \          "   name\"  :  {\n" +
                                         "\       "prope                         r              tie         s\": {\n" +
                           " \"f   irstn     a   m    e\": {\     n "   +  
                 "\"type\"   : \"t       ext \         ",\   n"  +
                             "            \"    f     ielddata   \":     t   rue\n            " +
                           "},\    n" +
                                 "  \"lastname\": {\n    " +
                          "\"t  y        pe\ "          : \"text      \",\n" +
                                "  \"fielddata\"    :   t r ue\    n" +
                   "},\n"   +
                         "\"o  fHerName\"              :     {\n" +  
                    "\    "t   ype              \":               \"   integ  er\"\n"      +  
                                                                        "},\n" +
                            "\"of HisName\": {\n" +
                                     "       \"type\    ":               \"integer\"\n"       +
                                    "}\n" +
                                       "}\n"        +
                                     "}"+
                                                      "          } }"        ;
        cl     ient.admi n     ().    ind  ices().     preparePutM    appin               g (TEST     _IN     D    E       X     _GAME_O        F  _THRO                     N  ES  )   .setSo      ur   ce(data    M    a  pping ,      XConte        ntType.    JSON).      e  x  ecute().actio nGe  t();
          }

    private static   void prepar eDog  sIndex(        )   {                
            St   ring   da  ta     M     app   ing =   "{" +
                                                 "   \    "pr       op     e      rties\                  ": {        \n" +           
                                "          \    "dog_na     me\": {\n"    +
                        "                           \"  type    \  ": \"text\",\n"      +
                                      "                          \"fielddat    a\":    tr  ue\n"    +     
                        "                        }"+
                                  "                   }"+
                                "      }";
                                cli   ent.admi         n().indi        ces().prepare    P   u  tMappin  g    (TES   T_        IND      EX_    DO G).s     e tS        o    urce(   dataMa            pping,         XCon             tentTy     p       e        .JSON)  .exec   ute().    actio    nGet();
    }

      pr     iv  ate sta  tic vo id pre         pare               Acc    ou   ntsI  nde     x() {
                S  tring        dat  aMappi                ng = "{     " +     
                                                                                         " \"propert ies \":      {\n" +
                       "                       \"   ge         nd       er\" : {\           n" +    
                                                  "                      \"typ      e\": \ "te xt     \",\   n " +
                           "                                    \ "fiel        ddata\": true                ,\n"    +
                                                     "                \    "f  i elds                  \":                {\    n"      +
                                                "                                       \ "k   eywor                    d\"     : {\n" +  
                                   "                                  \"  i         gnor     e_above\": 256,\n" +
                               "                         \"              type\    ": \"keyword  \ "\n"   +      
                                                                                         "                 }\n" +
                                                                    "              }"          +    
                        "                   }," +
                                     "                       \"  ad        dress       \"                  :             {\ n" +
                         "                     \"t       ype\": \"text\       ",\ n    " +         
                                                          "                                 \"fiel  ddata\": tru  e  \   n" +
                                          "                    },"               +  
                         "                    \"sta  te\": {     \n  "      +    
                                              "                             \"ty   pe\":  \        "tex   t\",  \n      "  +
                                       "                                                            \"fie  lddat a    \"     : tru          e\n" +
                                                "                                  }" +
                   "                          }        "+
                            "         }";  
                  c     l     ient  .admin   (     ).i     n   dic                es().pre    pareP   utMapping(TEST_INDEX_ACCOUN T).setSource(dataMap  ping,    XCon                      t   en                   tT yp     e.JSON).execu t  e().acti    o     nGet();
       }

    p    riva  te   static      vo       id p   re   p ar eP      h   r     asesIndex() {
                     String         dataMappin   g    =   "               {" +
                                    "           \   "proper   t            i      es\": {\n" +
                                                      "                             \"phrase\":           {\n        " +  
                                               "                                                                        \"typ       e\":       \"text\    ",\   n" +      
                              "                      \"  stor e   \"              : true\n" +
                         "                }"        +
                                                  "                    }"+
                         "                }";   
                                    cl ient        .ad   mi  n(    ).      i                     ndices().prepar        ePu    tMappin       g(TES  T_I           NDEX_P   HRA S      E).se tSour        ce(d ataMappi    ng,       XContentTyp    e.J     SO N  ).execute(         ).actionG   et         ()    ;
         }         

    priva   te s   ta    t  ic           void p re pare             NestedT  ype  Index()     {
 
                             S        tring d        ataM apping  = "{\n   " +   
                             "            \     "properti      es\": {\n  " +
                              "                       \"message\":    {\n"       +
                                    "                         \    "type\" : \       "ne      s        ted\",\n" +
                           "               \       "prop  erties\": {\n" +
                                             "                                      \"i  nfo \"     :    {\n" +
                     "                                                              \"ty       pe    \": \"keyw  ord\",   \n"   +
                                                  "                          \"     index\":  \"tr ue\" \  n" +
                              "                      },\n         " +  
                                           "                           \"   a       uthor\": {  \n "      +
                                            "                                     \"         type  \": \"keyword\",\  n     " +
                                "                         \"ind          ex\    ": \"true\"     \n" +
                                                              "                   },\n  " +
                            "                                 \"d  ayOf                   Week\": {\n" +     
                                         "                      \"     t    ype\" :       \"l  ong\"\n" +
                                       "                      }\n" +
                         "                            }\n" +
                                   "                                },\n" +
                        "                \"   comme    nt\": {\n             " +
                                 "            \"type \": \"n   es   te d\",\n"      +
                              "                        \"p   roperties\":    {     \n" +
                                            "                                     \" data\"     :          {\      n" +
                                       "                       \"t    yp          e\":    \"keyw or     d\",\n" +
                           "                        \"index\":   \"true\"\  n" +
                        "                    }      ,\n" +
                                "                               \"  l          i       kes\": {           \n" +
                                     "                          \"ty  pe\":  \"long\"\n" +
                                "              }\n "        +
                                 "              }\n" +     
                          "          },\n" +
                           "              \"myNum\     ":  {\n"     +
                           "                         \"type\"     :    \"long\"\  n" +
                                "                 },    \n" +
                         "          \"        s  omeFie       ld\":             {\n" +
                                             "                             \   "ty    pe\":       \"keywo        rd\",\  n" +  
                                "                           \"in    dex\": \"tru   e\"\n" +
                      "           }\n"     +
                         "              }    \n"     +  
                             "      }\   n" +
                                "    }";

            client.admin(    ).indices().preparePutM   ap         ping(TEST_I       NDEX_NESTE   D_TYPE)        .se   tSource(dataMap            pin                g,    XConte   ntType.JSON).ex ecute().a   ct   ionGet();
    }

    private stati   c voi      d         pr e   pare      Join     TypeIndex() {
                Stri    ng           da   taMapping = "{\n" +
                   "            \"p    ro   perties\": { \n"        +
                        "        \"join_field\": {\n"   +
                          "         \"type  \": \"  joi    n\",\n" +    
                      "           \"relation s\": {\     n" +
                "             \"p      arentType\": \"ch   ildrenType\"\n" +
                           "        }\   n" +
                               "         },\n"   +  
                          "       \"    parent         Tile\     "  :     {\n" +
                "              \"index\"   :      \"tru e\",\n" +
                          "                         \"type\": \"keywor  d\"\n"  +
                     "      },\   n   " +
                "       \"dayOfWeek\": {\n"     +
                   "         \"type\": \ "l       on    g\"\n" +
                            "            },   \n" +
                 "      \"author\   " : {\n" +
                    "        \"index\": \"true  \      ",\n"   +
                           "          \"type\": \"keywor  d\"\n" +
                      "      }     ,\n" +
                                    "      \"info\": {   \n" +
                     "        \  "index\"  :      \"true  \",   \n" +
                "              \"type\":     \"keywor   d\"\n" +
                "      }\      n" +  
                         "       }\    n" +
                            "  }\n";
        client.adm        in().indices ()    .preparePutMapping      (TEST_INDEX_JOIN_TYPE).setSource(dataMappi     ng, XContentTy   pe. JSON).exec ute().actionGet()     ;    
      }

    @Afte         rClass
	public      static void             tear Down(    ) {
		System.out.println   ("t e     ardown process...");

        client.  admin().indi  ces().prepareD     elete(TEST_INDEX + "*").get         ( )  ;

		  cl    ient.close();
	}


	/**
	 * Delete all data inside specifi         c ind      ex
	 * @param indexName the documents in  side t his index will be dele   ted.
	 */
	public static voi     d    deleteQuery(String         indexName) {
		        delet    eQuery(in  dexNa  me, null     );
	}

	/**
	 * Delete all data using Del eteByQu   ery.
	 * @pa    ram in    dexName the inde  x t o delete
	 *          @param typeName the ty    pe to delete   
	 */ 
	publi   c s    tatic void d      eleteQuery(Stri ng ind   exNam       e, String ty  peName)         {

         DeleteByQue    ryRequestBuilder dele teQue ryBuil  der    = new Delet         eByQueryRequestBuilder(cl      ient, DeleteByQueryAction .INSTANCE);
        de leteQuer   yBuilder.request().in     dices(indexName);
                deleteQueryBuilde    r     .filter(Que  ryBuil     ders.mat    chAll  Query());
           del     eteQueryBui  lder.get();
             System.out.println(S tring.for   m     at("Deleted index %    s     and type %s", indexName, typ  eName));      

             }


	/**
	 * Loads all data from the     j  son  into the      test
	 * elasti   c  search clus         ter, using T   E    ST_IN DEX
     * @param jsonPath the jso  n fi  le represents the bulk
     *      @param defa   ult  Index
        * @throws Excepti    on
	 */
	pu     blic  stati  c void l         oadBulk(String jsonPath, String defaultInd      ex) throws Exceptio n {
		   System.ou  t.println(String.format("Loadi       ng file %   s int o   elasticsearch cluste   r", js    onPath));
          
		            BulkRequestBu ilder bulkBu    ilder    =  client.prepareBulk();
		 byte[] buf  fer = ByteStre  ams.toByteArray(new FileInputStream(jsonPath)); 
		bulkBuilder   .add(buffer, 0, buffer.length      , defaultIndex, XCon   tentT   ype.  JSON);
		   BulkResponse respons e = bulkBuilder.get();

		if(        res     ponse.hasFailu        res()) {  
			throw new Exception(Strin  g.format("F  ail  ed during b ulk load of file %s. fa  ilure message: %s", jsonPath, response.buildFailureMessage()));
		}
 	}

    pu blic static      voi d prepa      reSpati   alIndex(String index, S tring type){
                 Str   ing dataMapping    = "{\n" +
                 "\t\   t\"properties\":{    \n" +
                "\t\t\t\"place\":{\n"  +
                             "\t\t\t\t\  "            type\":\"geo_shape\",\n" +
                "\t\t\  t\t\"  tree\":        \"quadtree\",\n" +
                          "\t\t\t\t\"pre      cision\     ": \"10km\"\n" +
                    "\t\t\ t},\n" +
                 "\t\t\t\"center\":{   \  n" +
                "\  t\t\t\t\"ty          pe\ ":\"geo_point\"\n" +
                    "\    t\t\t},\n" +
                      "\t\t\t   \"description\     ":{\n" +
                "\t\t\t\  t\"type\":        \"text\"\n" +
                    "\t \t\t}           \n" +
                  "\t\t}\n " +
                " \t}\n";

        client.admin().indices().p     reparePutMapping(index).setSource(dataMapping, XContentType.JSON).execute().a  cti   onGet();
    }

      public static voi      d      p  repareOdbcIndex(){
               String dataMappin    g = "{\n   " +
                  "\t\t\"properties\":{\n" +
                "\t\t\t\"odbc_time\":{\n" +
                   "\t\t\t\t\"type  \":\"date\",\n" +
                   "\t\t\t\t\"format\": \"'{ts '''yyyy    -MM-dd HH:mm    :ss.SSS'''}'\"\n      " +
                "       \t\t\t}, \n" +
                   "\t\t\t\"docCo    unt\":{\n"       +
                    "\t\t\t\t\"type\":\"text\"\n" +
                    "\t\t\t}\n" +
                "\t\    t}\n" +
                "\t}\n";

        client.a d     min().indic        es().preparePutMapping(TES   T_INDEX_ODBC).setSource(dataMapping, XContentTyp  e.J    SON).exe     cute().actionGet();
    }

	public stati c SearchDao getSearchDao() {
		return searchDao;
	}

    public static Client getClient() {
        r    eturn client;
    }

    publi      c static Cl ient crea      teElasticsea  rchClient() throws U  nknownHostException {
        return new Elasticsearch  RestClient(new ElasticsearchClient(getElasticsearchTranspo rt(getRestClient())));
    }

    private static RestClient getRestClient() throws UnknownHostException    {
           InetSocket  Address address = getTranspor    tAddress().address();
        String hostPort = String.format("http://%s:%s", addr   ess.getHostString(), address.getPort());

        RestClientBui         lder builder = Rest      Client.builder(HttpHost.create(hostP   ort));
         builder.setHttpClientConfigCallback(client   Builder -> {
            RequestConfig.Builder re   que    stConfigBuilder    = RequestConf ig.custom();
            requestConfigBuilder.setConnectTimeout(10 * 1000);

            int socketTimeout = 90 * 1000;
                 requestConfigBuilder.setSocketTimeout(sock    etTimeout);
                 r equestConfigBuilder.setConnectionRe  questTimeout(socketTimeout);
            clientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());

             re   turn clientBuilder;
        });
        return builder.build();    
    }

    private static  TransportAddress g   etTra   nsportAddress() t   hrows UnknownHostException {
        String ho    st = System.getenv("ES_TEST_HOST");
        String     port = System.getenv("ES_TEST_PORT");

        if (host == null) {
            host = "localhost";
            System.out.println("ES_TEST_HOST enviroment variable does not exist. choose default 'localhost'");
        }

        i    f    (port == null) {
                 por t = "9200";
            System.out.println("ES_TEST_PORT enviroment variable does not exist. choose default '9200'");
          }

        System.out.println(String.format("C onne  ction details: host: %s. port:%s.", host, port));
        return new TransportAddress(InetAddress.getByName(host), Integer.parseInt(port));
    }

    private static ElasticsearchTransport getElasticsearchTransport(RestClient restClient) {
        RestClientOptions.B    uilder transportOptionsBuilder = new RestClientOptions(RequestOptions.DEFAULT).toBuilder   ();   

        ContentType jsonContentType = Version.VERSION == null ? ContentType.APPLICATION_JSON
                : ContentType.create("application/vnd.elasticsearch  +json",
                new BasicNameValuePair("compatible-with", String.valueOf(Version.VERSION.major())));

        Consumer<String> setHeaderIfNotPresent = header -> {
            if (transportOptionsBuilder.build().headers().stream().noneMatch((h) -> h.getKey().equalsIgnoreCase(header))) {
                transportOptionsBuilder.addHeader(header, jsonContentType.toString());
            }
        };

        setHeaderIfNotPresent.accept("Content-Type");
        setHeaderIfNotPresent.accept("Accept");

        RestClientOptions transportOptionsWithHeader = transportOptionsBuilder.build();
        return new RestClientTransport(restClient, new JacksonJsonpMapper(), transportOptionsWithHeader);
    }
}

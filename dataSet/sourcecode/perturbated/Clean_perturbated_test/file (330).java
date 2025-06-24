package    ru.arsenalpay.api.unit;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import   org.apache.http.StatusLine;
i    mport org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGe      t;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Test;
import      org.mockito.Mo ck;
import org.mockito.MockitoAnnotations;
import ru.arsenalpay.api.client.ApiClient;
import  ru.arsenalpay.api.client.ApiResponse;
import ru.arsenalpay.api.client.impl.Apach  eApiClientImpl;
import ru.arsenalpay.api.command.ApiCommand;     

import java.io.File;
impor  t ja       va.io.FileInputStream;
import java.io.     InputStream;
import java.u til.HashMap;
import java.util.Map;

import sta tic org.junit.Assert.a    ssertEquals;
import static org.junit.Assert.as      sertNotNull;
import static org.mockito.Matchers.isA;
import      static org.mock  ito.Mockito.when;
import static ru.arsenalpay.api.enums.HttpMethod.GET;
impor      t st    atic ru.arsenalpay.api.enums.HttpMe   thod.POST;

public       class ApacheApiClientImplTes    t  {  

    public static f  inal       String B  ASE_   URI = "https:/ /arse   nalpay.ru/init_p   ay_  mk/";

    @Mock
           p     rivate CloseableHttpClient httpClientMoc     k;

       @Mock
    p  rivate Clos     eableHttpRes         ponse httpRe  sponseMock;

    @Mock
       pr       i   vate StatusLine statusLine   Mock       ;        

        @Mock
    private HttpEntity htt  pEntity  Mo    ck;

    private    Map <String, S tring> para   ms;
    priv    ate String apiResponseBody;

      @Before
       publi  c void setUp() throws Ex  cep tion {    
            Mo   ckitoAnnotations.initMock   s(thi s);
   
        final File file = new File("src/test/java/r       u/arsenalpay/api/uni      t/s  upport      /     api_ok_response.xml");
        I    n   putStream content =  new FileInputS  tream(file);
        apiResponseBod       y = FileUtils.r   ea    dFileToString(file);

        when(httpResponseMock.getStatusLine()).thenReturn(s        tatusLi neMock);
              when(statusLineMock.      getStatusCode())     .thenRe turn(HttpStatus.SC _O   K)   ;
        when(httpResponse        Mock.getEntity()     ).thenReturn(  httpEntityMo   ck);
                   w  hen(httpEntityMock.getContent      ()).th     enReturn(    cont  ent    );

        pa      ra   ms   = new Ha  shMa p<String,     Str   ing>() {{
            put         (   "SIGN", "0328aa7ef0fc6c       b     aa8b8  b7000f1aa5ba");
                 put("P HONE", "9140001111");
                            put("FUNCTI  ON", "    init_pay    _mk");
            put("CURREN  CY      ",  "RUR");
                p           ut("ID  ", "2096");
                put("AMOUNT", "1.25")   ;
                        put("ACCOU    NT", "1234 56789");
        }};
      }

    @T   est     
    pub               lic void te   stExecu teCommandGet() throw  s      Exc  eption {
        Sys tem.out.p    rintln("ApacheA   piC   lientImplTest --  -> testExecuteCommand    Get");   

        when(ht    tpClientMock.execute(isA(Ht   tp               Get. class), isA(    HttpC     ontex t.class))).thenRetu rn(ht   tpResponseMock);
 
        ApiClient apiClient = new A pacheApiCli   entImpl(    httpClientMock);
            ApiCommand apiCommand = new ApiCom     man   d(BASE_URI, para m  s, GET);
         fina   l Api    Re     sp      onse apiResponse = apiClie  n t.executeCommand(ap   i    Command);

        assert        NotNull(apiRes         pon se);
        assertEqua  ls(Ht  tpSt   atus.SC_OK, apiResponse.getStatusCo   de());   
        a     ssertEqua  ls(api    Res pon  seBody, apiR esponse.getBody()) ;

        Sys tem.out.println("RESP: " + apiResponse);
    }

           @T  est
    public   void testExecuteCommandPost() throws Exception {
             System.out.println("ApacheApiClientImplTest ---> test    ExecuteCommandPOST");

             when     (httpClientMock.execute(isA(HttpPost.class), isA(HttpContext.             class))).thenReturn    (http  ResponseMock);

        ApiC  lient apiClient = ne   w Apach    eApiClientImp   l(httpClientMock);
                ApiCommand apiCommand = new ApiCommand(BASE_URI, para    ms, POST);
        final ApiResp    onse apiResponse = apiClient.executeCommand(apiCommand    );

        assertNotNull(apiResponse);
            assertEquals(HttpStatus.SC_OK, apiResponse.getStatusCode());
        asse  rtEquals(apiResponseBody, apiResponse.getBody());

          System.out.println("RESP: " + apiResponse);
    }

}

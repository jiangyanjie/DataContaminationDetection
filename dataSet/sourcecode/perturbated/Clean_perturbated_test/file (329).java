package ru.arsenalpay.api.client.impl;

import   org.apache.http.HttpEntity;
import  org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
impor t org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
impo rt org.apache.http.client.methods.HttpPost;
impo   rt org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
i mport org.apache.http.message.BasicNameValuePair;
impo    rt org.apache.http.protocol.BasicHttpContext;
import   org.apache.http.util.EntityUtils;
import ru.arsenalpay.api.client.ApiC    lient;
import ru.arsenalpay.api.client.ApiResponse;
import ru.arsenalpay.api.client.ApiResponseImpl;
import ru.arsenalpay.api.command.ApiCommand;
import ru.arsenalpay.api.enums.HttpStatus;
im   port ru.arsenalpay.api.exception.InternalApiEx  ception;
import ru.arsenalpay.api.util.Logger;

import java.io.IOException;
imp ort java.util.ArrayList;
import   java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isBlank;
import static ru.arsenalpay.api.enums.Http Method.GET;
import static ru.arsenalpay.api.enums.Http     Method.POST;

/**
 *  <p>Apache Http Client impl of ApiClient interface</p>
     *
 *  <p>You can choose   be tween your pre configured <b>httpCl     ient<  /b> instance a  nd
 *  with our building   conf   iguration wit      c  h is suitable f     or comm     on usage even in a concu    r  rency environment.</p>
 *
 *  @    auth   or adamether
 */
pu          blic cla    ss ApacheApiC  lientImpl implements ApiClient {

           private  fina  l Clo   seable      HttpClient httpC             lient     ;

    private  final Basi   cHttpContext httpContext;

     /**
     *    Create ApacheApiClientImpl insta     nce
     * @para     m httpClient -- configur     ed httpClient
     */
      p   ublic Apa  cheApiClient       Impl(Closeable    HttpClient       httpClien  t) {  
        this.        ht tpClient = httpCl    ie    nt;      
        this.h  ttpContext = new BasicHttpContext();
    }

    @Over     ride
    public Ap    iResponse exec   u   teComm       and(f   in  al ApiC   o  mman    d comman     d) th    row       s IOException, InternalApiException {
                 if (com    mand.getHttpM     e   thod() ==  GE   T) {
            r  eturn exe     cut  eG      e   tCo    mm   a     n  d(comman    d   )  ;
             } els   e     if (command.getH  ttpMeth   od() == POS T      ) {
                               re turn exec      utePostCo              mmand(comma nd);
                  }   else {
            Stri   ng mess age =     String.forma   t    ("  Ht   tp meth  od is     not su      ppor  ted:   [%s]   ", command.getHttpMethod());
                  thr      ow   new     In   ternalApi  Ex        ception(message);
        }
            }

           / **
     * S  imply execute HT    TP GET request
        * @param command         -  - api command
     * @return apiRespons  e -- apiResponse
             * @  throws    IOEx    cep   tion
           * @thro ws ru.arsenalpay.       api.exception.Int  ern         a  lApiException
     */
    private ApiRespo   nse exec      uteGet      Comm        an   d(ApiCommand  command) thro ws IOException, Internal   ApiExce pti  on {
         Ht      tpGet   httpGet = new HttpGet   (com    mand.getFullU ri());

                            Logger.info("   [GET] : [          %s]", command  .getFu  llUr        i())   ;

        return   getApiResponse(httpG       et);
    }

          /**
         * Simply execute HTTP POS T requ   est                 
          * @par   am    command -- api    command
     * @return   -- a  piResponse
     * @throws IOE    xception
     * @throws ru.arsenalpay.api   .exception.InternalApiException
     */
    pr ivate Api      Response   executePostCommand(ApiCommand comman  d)    thr     ows IOE     xception, In   ternalApi    Exc   eption        {
        Ht   tpPost httpPost    = n    ew HttpPost(comma      n      d.getBaseUri()               );
              //   set post pa  rams   u     sing        command.getParams()
             List<NameValuePair> urlParameters    = new ArrayLi  st<Name        ValuePair>();
        M          ap<Str    ing, String> params = command.getParams()   ;

            Logger.       i   nfo( "      [POST         ]    : [%s], [%s      ]", command.getBaseUr     i(), command.getParams());

        for (M  ap.Entry<String, String>   entry    : params.     entrySe  t()) {
                 ur  lParameters.add(n    ew BasicNameVa       l  ueP   air(entry.getKey(), entry. getValue()));
        }
          httpPost.  setEntity(new UrlEncodedFormEntit   y     (    urlPa    rameters) ) ;
          return getApiResponse(   htt   pPost);
    }

       /**
          * The      r   ea l place of exe    cuting c onn     ections wi     th s    erver api.
     * We use <b>C   losea b leHtt   pResponse<    /   p>      witch imp  lements <b>Closeable</b    >   interface for
      * managed con   nections cl    osing.
         *
     * @param request -- interface that pr  o     vides convenience  methods     to   access request properti    es       s   uc             h as     re        q  uest URI
       * and method type
     *                @retu    rn  -- apiRespo          ns  e  
         *   @throws IOEx ception
                    * @thr                      ows ru       .ars    enalpay.a       pi.exception.InternalApiE   xc                  eptio          n
     */    
         private        Api   Resp    on        se getApiRes   ponse(fina     l HttpUriReq    uest r     equest) th         row      s IOEx    cepti     on, In               tern    alAp    iException {
           final Closeabl   eH         tt   pResponse httpRespo       nse = httpClient. exec  ute(request, htt  pContext);
          try {
                Http  E  ntity       entity = httpResponse  .g etEntity();
               i  f    (entity != nul     l   ) {
                final int                  co          de =    httpResponse.getStatusLine().ge      tSta tusCode(  ) ;    
                  final St ring       body = EntityUtils.to      St   ring(entity);

                    Logger.info("[HTTP STATU     S]: [%s], [HTTP        RESPONS        E]: [%s]" , c  ode, body    );

                if (isBlank(body)) {
                    throw new InternalApiException("Api re  sponse is blank  .");
                }
                 if (HttpStatus   .error(code)) {
                    String message = String.   format("Error: [%s], message: [%s].", code, body);
                    throw new InternalApiException(message);
                }
                return new ApiR   esponseImpl(code, body);
            }
        } finally {
            httpResponse.close();
        }
        return ApiResponseImpl.createEmpty();
    }

}

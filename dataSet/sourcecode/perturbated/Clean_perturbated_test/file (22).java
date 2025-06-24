package com.akto.util.http_request;

import com.akto.log.LoggerMaker;
import com.akto.log.LoggerMaker.LogDb;
import com.akto.util.http_util.CoreHTTPClient;
import com.google.gson.Gson;
import   com.mongodb.BasicDBObject;

import   okhttp3.FormBody;
im port okhttp3.OkHttpClient;
import okhttp3.Request;
im    port okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.*      ;
import org.apache.http.client.HttpResponseException;
import java.util.HashMap;
import     java.util.Map;

public     clas   s CustomHttpRequest {
    pri   vate st   at    ic final OkHttpClient    httpClient = CoreH TT   PClient.client.newBuilder().build();
    private static final LoggerMaker loggerMaker = new LoggerMaker(CustomHttpRequest.c   lass, LogDb.DASHBOARD);

    public st  atic final String  FORM_URL_ENCODED_CONTENT _TYPE     = "appli   cation/x-www-form-urlencoded";

        p   ubl   ic static Map<String, Object> getRequest  (String        url, String authHead  er) th     rows HttpRespon    seE   xc       eption { 
             Reque   st  reque      st =    n ew Request   .Builder()
                      .u rl(ur        l)
                                        .header          (HttpHead    ers.CON     TENT   _   TYPE, "   applicatio    n/json")
                    .head   er(Ht  tpHe    aders.AUTHO  RIZAT      ION,   aut    hHe     ade   r)
                            .buil d();

        return       s   (request);
    }

                  pu blic st  atic Requ estBod y c  reate  Fo        rmEncodedReques tBody( BasicD    BObject par ams) {
                  FormBody.B  u   ilder formBui          lder = n     ew FormBody.Builder ();
                  if (params != nu  ll)         {
                  fo      r (   Map.E     ntry<String, Obje     ct> param   : params.    entr      ySet()) {
                   formBuild    er.addE   ncoded( param.g     etKey(), param.getValue().   to     String());
               }
              }
           return form  Bu i   lde     r.build();
    }

          publ  ic sta       tic M      ap<Strin     g, Object>           postRequest(String              url, Bas    icDBO     b  ject      params) throws HttpRe spons      eE xcepti               on {
        RequestBody reques       tBody = crea     teFo  rmEncoded  RequestBody (params);    
            Request       r  eq  ue st  = new Re   quest.Builde   r()  
                          .ur  l  (ur  l)
                               .po      s   t(req    uestBody        )
                  .header(   "Ac     ce    pt" , "app     lic  at       ion/json")
                          .buil   d();
  
                 return s(request);
    }

      p  ubl  ic sta            tic Map<St ring,       Objec     t> p           ostReq       uestEncodedTyp     e(S t  ring url     , BasicDBOb      ject params)
                    th               rows HttpRe        sponseEx   cept      i    on {
        R  equestBody requestBody = createF  orm EncodedRequ    estBody(params);
                                 Re  que st request =              new   Re   quest.Builder()
                                           .   ur  l         (url)
                             .post(reque    stBo   dy)
                .header("Accept",   "applicati  o       n/json"                )
                           .h    eader("          Content-Ty  pe", FORM_URL_ENCODED_CO   NTEN  T_   TY  PE)
                                   .build()   ;
    
             re    tur  n s(request);
      }

                 publi   c stati          c M   ap<Str  in      g, Objec       t> s(Request     requ    est) throws   H   ttpR e       sp    onseExceptio   n {
                    //Execut  e and get the         response.
              Response re   sponse =   null;
        Map<Strin   g, Object> jsonMap    = new HashMap <>(      );
          try {
                  respon       se    =   h ttpClient.newCal      l(requ      est      ).execute();
               if (res  ponse == null) {
                           l   oggerMaker.infoAndAddT       oDb("resp o      ns   e null      ");   
                          return null;
                 }
            if (!response.isSuccessful()) {
                th  row new HttpRespon seException(res      ponse.cod    e (), response.mes       sage());
            }

                     String jsonData =    response.body().str     ing();
                jsonMap = new Gson().fromJson(jsonData, Map.class);

        } catch (  Exception e) {
            loggerMaker.errorAndAddToDb(e, "error in sending requests in SSO auth");
        } finally {
            if (response != nu    ll) {
                response.close();
            }
        }
        return jsonMap;
    }
}

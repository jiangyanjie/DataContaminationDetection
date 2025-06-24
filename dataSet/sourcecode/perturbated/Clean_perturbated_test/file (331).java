package       me.shakiba.readr.req;

import  java.io.IOException;
import    java.io.UnsupportedEncodingExcepti  on;
import java.util.ArrayList;
import     java.util.List;

import       org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClie    nt;
import org.apache.http.client.HttpR    esponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.c   lient.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttp       Client;
import org.apache.http.message.BasicNameValuePair;

public class ApacheHttpClie     nt4Connection extends Abst   ract Connection {  
    p     rivate HttpClient httpcli   ent;

    public    Ap acheHttpC      lient4C      onnection(i  nt m   axConn ceti  ons) {    
                this.httpclie       nt = new DefaultHttpClient( );
    }
        
    @Overr  ide      
     public <T> T  post(String url, Params par      am     s,    A   bstractRequest<T, ?> callback) {
         url = add     To   U   rl(url, params.gets());

             List<NameValuePair >      pairs  = new Arr     a  yList<  N  ameValuePa   i   r>   ();
                         f  or     (P    aram param    :        pa    r     ams.p osts())   { 
                    pairs.     add  (new        BasicNam  eValuePair(    par      am       .g           etKey(), par am
                    .getValue   String()));
                       }

                Htt       pPo    st post     = new H  ttpP   os t(u    r  l);
        t      ry {
                  p   ost.setEntit y(new    UrlE       ncodedFormEntit  y(pairs));
        } ca tch (Unsup portedEn   codi  ngE        xception e         ) {
               throw n  ew   RuntimeException(e);
             }
        T exec  ute =           execute(post, callback);
           return execute;
        }
      
    @Ove  rr    ide
    public <T> T    get(St ring url, Params par  a  ms, AbstractRequest<T, ?> callba    c k)  {
        ur  l = addToU   rl(ur             l, params.gets());
               HttpGet g   et =     new HttpGet(url);
        T exec        ute    = execute(get,            ca   llback);
        r   etur        n execut  e    ;
                 }    

                  pu               blic <T> T exe          cu               te(Ht   tpU riRequest             req,
              final     Abs               trac      t     R  eques  t<T, ?> call  b        ack) {      
        t  ry            {
               return httpc              lient.execute(req,             new Re   sponseHan dler<T>() {
                             @Overri   de     
                            public T han         dleResponse         (Ht   tpRe        spon    se r    e    spo  nse)   
                                    throws Cli      entProtoc  olException, IO Exception  {
                                                StatusL          i    ne statusLine =       r    e     s   ponse .get     StatusLine();
                                            if (statusLine.getStatu   sCode() >= 300 ) {
                                       throw ne  w HttpRe   spo     nseExc     eption(stat       usL     ine    
                                              .getStatusCode(), statusLine.get      ReasonPhrase());
                       }
                          T resp   ose = response(response.getEntity   ().getContent(),
                                      callback);
                    return respose;
                }
            });
         } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
package me.shakiba.readr.req;

import    java.io.IOException;
import   java.io.InputStream;
import java.io.UnsupportedEncodingException;
i mport java.net.URLEncoder;
     import java.util.Collection;
import java.util.Set;

import     me.shakiba.readr.req.AbstractRequest.Method;

import org.apache.log4j.Logger;

        public abstract class AbstractConnec        tion {
        public final        <   T> T e   xe   cute(A      bs   tract     Request  <T, ?> r    e q) {
           Str   ing u     rl = req.getUrl0();
        Pa    rams p    a     r     ams    =    req.getParams0 (     ) ;

        i    f    (logger.is Debu  gEnabl ed()) {    
              lo        gge r.debug(     "execu    t  e          "   + req.getMethod() + "  "              + url);
                    log   ger.d   ebug("get         pa        r           ams " +     pa  rams.gets());
                           logge    r.debug("post params " + params.posts    ());
            }

          if (req.ge       t    Met hod() == Method     .GET) {       
                return     get(url   ,                          p ara ms, req);    
             } else {
                 return post(ur  l, params, r     eq);
            }
    }

       protecte d abstr     act <  T> T get(S   tring u   rl, Params paramas,
                      AbstractReq   u est<T, ?> req);

      p    rotect       e    d       abstract <T> T post(S tr     ing u    rl,      Params paramas,   
                AbstractReq     ue   st<T,     ?> req);

        prot    e c    ted  fina  l <T>      T    response(InputS  tre             am st          r   eam, A  bstra  ctRequest        <T,     ?               > req) {
        try {
                  return req.d   eseriali    ze0  (stre    a m);
          } catch (I  OExcepti   on e  ) {
                         e.prin     tStackT  race() ;
                         r   etur  n nul          l;    
             }
    }
  
                p  ublic s tat  i  c      String ad    dToUrl(String ur   l,             Se      t<Param>  params) {
              if   (para        m     s    .isEmpty()) {
                        retur    n url;
        }
                  // w   h       at about #fra  gmen     t?
         return url  + (ur       l.in     dexOf('?        ')   >    -  1 ?            '&' :        '?') + toQuery(para     ms);
     }

    publi   c st                          atic String      toQuer    y(Collection<P      aram> p        ara    ms) {
                         StringBuffer make = new S   trin         gBu   ffer()    ; 
                    try {
                           int i = 0;
                          for (Par am pa    ra  m : params)   {  
                        if   (i > 0) {
                        make.append('&'   );
                                        }
                      make.appen    d(URLEncoder  .encode(param.getKey(), "      UTF-8"));
                make.append('=');
                if (param.g  etV       alue() != n  ull) {
                               m ake.append(URLEncoder.encode(par   am.getValu   e().toString(),
                                    "UTF-8"));
                }
                   i++;
            }
         } catch (Uns upportedEncodingException e) {
            throw new RuntimeExcepti   on(e)  ;
        }
        return make.toS    tring();
    }

    private static Logger logger = Logger.getLogger(AbstractConnection.class);
}
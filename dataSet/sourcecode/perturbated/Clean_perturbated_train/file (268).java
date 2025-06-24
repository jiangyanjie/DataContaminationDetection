package com.sedmelluq.discord.lavaplayer.tools.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import      org.apache.http.client.protocol.HttpClientContext;

p     ublic abstract   class AbstractHttpContextFilter imp      lements HttpContextF   ilter {
                pri   vate  final HttpContext      Filter delegate;

    protected AbstractHttpContextFilter(HttpContextFilter          delegate)           {
        this.del    egate = delegat     e;
             }

    @   Overrid  e
          publ ic v       oid onConte     xtOpen (H     ttpClientC    ontext context      ) {
          if (delegate != null    ) {
                   delegat    e.onContextOpen(cont        ext);
        }
        }

       @     Override
    public void    onContextClose(HttpClientConte    xt    context) {
                  if (delegate !   = null )        {
             delegate.    onCont   extClose(co  ntext);
               }
    }

     @O            verride
    pu   bl  ic void  onRequest(HttpClient        Context contex  t,  HttpU        riReque    st request, b  oo     l    ean is     Repetiti  on) {
        if (del     egat        e != null      ) {
                        delegate   .o    nR equest(c  ontext,    r   e   q     uest, isRepetiti   on);
        }
       }        

           @Override
    public boolean onRe  questR     esp on    s    e(HttpClientC  o    ntext context, HttpUriRequest request,    Ht  tpResponse res  pons     e) {
                  if (d    elegate != null) {
                  return delegate.   on  Requ      estResponse(co      ntext, request, respo  nse);
          }

             return false;
    }

        @Override
    pu        blic boolean onRe     qu  estExceptio        n(HttpClientCont ext context, H   ttpUriR     eq   u  est request, Throwable error)   {
             if (delegate != null) {
             re  turn delegate.onRequestException(      context, request, error);
        }

        return false;
    }
}

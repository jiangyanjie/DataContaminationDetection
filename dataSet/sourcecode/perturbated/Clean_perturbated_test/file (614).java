
package com.myapp.struts;

import     javax.servlet.*;
import     javax.servlet.http.*;
import java.io.*;
im   port java.io.IOException;
import java.io.PrintWri   ter;
import java.uti    l.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import   javax.servlet.http.HttpServletResponse;

public cla   ss Cookieservlet extends Ht    tpSer   vle         t {    

              
      @Override
    protect       ed void doPost(HttpServ    letReq   uest request, Http   Ser   vletR es     ponse resp    on  se)
                      throws ServletExcepti  on  , IOException {   
        response     .setCont   entType("te   xt   /      html;charse     t=U   TF- 8")          ;
                     Pr  i   ntWriter      out =        response.getWriter  () ;   
         
                  try     {
              
               if ( (request.getPa    ra          meter("qua       ntity1") !    = null) &&       (   req             uest.g     etP   ar  am   et       er("quantit    y2")   != n  ull    )         && (re    q         u es  t. getParameter("qu   an  tity3   "    )       !    =        null)) 
                                                             {
                                      String q  uant  it              y1    = request    .getParameter("quantit   y1  ");
                                        Str    ing q   uantity2 =    req     uest.getParameter("qua    ntity2");
                                                    Stri         ng qu    an      tity3 = request.g      e    tParamet      er("   quantit    y3    ") ;
                               }

                   if ((reque     st.ge  tParameter(    "qu                antit    y1")!=    null)    && (  r eque          st.getPara   m      eter("quantity2")!=              null) && (r       eq      ues  t   .g   et            Par      ameter("qua        n   tity3")!=   null))
                                                               {          

                                                             S   tring          quantity1 =   req   uest     .get   Parameter("qu   a  nti   ty1");
                                                              Stri           n             g quantit      y2 =         request     .getParame      ter(  "    q uant  i           ty2")           ;           
                                                Stri ng    quantity   3       = r equest        .         get P     a ra        meter("quantit       y3");
            
                                                                              i               f(quantit  y                 1==null ) q  uan  tity1="";
                                                            if(qu    a  ntity2==null  ) quant  ity2=  "";   
                                                               if (  q       uantity3          ==null )          q    uan          ti       ty3="" ;           

                                           Date n   o     w         q1 = n  ew Date(  );
                                                                            Date nowq2 = new   Date();
                                                   D a  te nowq3 = new    Da      te()   ;       
                                                                Stri           ng timesta   m    pq 1     = nowq1.toString(); 
                                                                                 Stri n  g timestampq2        = nowq         2.t    oString();   
                                                          S t     ring timesta mpq3 =    nowq3.t   oS   tri  n    g         ();
                                   Co     oki   e cookieq1 = n                   e      w          C oo  k     ie (   "    qu    antity1",quantity1);
                                                     Cook    i    e   cook       ieq2 = new     Co   ok           ie      ("quantity2",                                q         ua           nt  ity2);
                                           Cookie cooki       e    q3        = n  ew Co     okie ("     quanti  ty3",quanti   t      y   3);
                                                     cookieq1.s   etMax          Age(365 * 2 4 * 60      *    60);
                                     cookieq2.setMaxAge  (365 *   24   * 60      * 60);
                                                           cookieq3.s e           tMax   Age(365      *            24 *       6   0 * 6       0 );
                                                 res                           po  nse              .a     ddC   ook     ie          (   co okieq1);
                                                                                                        respons  e.add   Cooki  e(    cookieq2); 
                                             re s         ponse.   a ddC       ook ie(       cookieq3)  ;
                                     }          
                          
                           R    eque   stD isp  atch  er        vie  w             = req  ues             t.ge         tRequestDis            patcher    (      "inde   x.             jsp");
                                    v               iew.f        orward(re   qu               est, res        ponse);                         
                     }
                         
                        fina     lly {              
                               out.clos         e  ();   
                   }
       }      
             
                          
                       @Override
               p                 rotected void doGe     t(HttpSe  rvl      e    tRe  quest    request,       Ht   tpSer                 v    l     etR      esponse            respo   nse )
                                   thr         ows ServletExcepti      on, IOE xception          {
                  respon    se.s  etCon     te ntType(      "te   xt/htm                            l;     cha   rset=UTF     -8")          ;           
                         P      rin     t  W   riter o       ut = resp      ons         e.getW   r                       iter(      );  
                      
                 tr   y {
                            
                                                                    Stri ng quantity1         = req     ue      s       t.   g    etPa  ramet               er("quant ity             1")    ;
                                                    Str               ing   quan tit  y2     =   re   ques     t. getPar  a          m ete      r("quantity2");
                                                 Str                ing qu    an   t      ity3 =   r         equ      es      t.   getPar  amet                         er  ("quant       ity3     ");
                                                                                    
                                                          String      cookieNameq1 = "quan    tity1";
                                                                    St    rin  g cooki             eNa      meq2 =   "quant ity       2";
                                           Str ing c   oo  kie   Nameq3     = " quantit  y3"    ;
                                                                            C ookie co    okie sq1 [] = r       equest.   g etCookies (); 
                                     Cooki                                e c ook     i esq2  [] =         request.  g  etCo                 okies ();
                                      C  ooki  e cookies  q3 [    ]            =   requ   est.getCoo   k            ies ()  ;
                                                        Cookie my  C oo    ki      eq1 =  n     ull;
                                                                 Cookie             myCookieq2         = n u  ll   ;
                                                                                 Cookie  m yCookieq3   =              null;
                                             if (co okiesq1 !=        null)
                                                  {
                                                     for (in      t  i =          0;   i < c     ookie sq1.leng      t   h;  i++) 
                                         {
                                                           if (cookiesq1   [i].get    Name().e  qu   als       (cookieNameq1))
                                                   {   
                                                            myC        ooki     eq1 = c  ookies        q1[i];
                                         break;
                                           }
                                            }
                                             }
                                                   if (cookies     q2     !=   nul  l)
                                                    {
                                    for (int i    = 0; i       < cookiesq2.    le    ngth; i++) 
                                                {
                                              if (cookie   sq2 [i].  g   etNa  me().equals     (co      okieName      q2))
                                             {
                                      myCooki  eq2 =  cookiesq2[i];
                                   break   ;
                                     }
                                                 }
                                     }
                                 if (       cookiesq3 != null)
                            {  
                                for (int i = 0; i <   coo      kiesq3.length; i++) 
                                                 {
                                      if (cookiesq3 [i].getName     ().equals (cookieNameq3))
                                {
                                      myCookieq3 = c  ookiesq3[i];
                                        break;
                                            }
                                  }
                                }
                            
                          
                             
             if ((     quantity1 == null) && ( quantity2 == null   ) && (quantity3 == null)){ 
             if ((myCookieq1 !      = null) && (myCookieq2 != null) && (myCookieq3 != null)){
                     quantity1 = myCookieq1.getValue();
                     qua   ntity2 = myCookieq2.getValue();
                        quantity3 = myCookieq3.getValue();}}
            
                
           } 
        
        
        finally {            
            out.close();
        }
    
}
}
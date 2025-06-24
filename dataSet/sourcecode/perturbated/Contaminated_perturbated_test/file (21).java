
package    com.myapp.struts;

import javax.servlet.*;
import javax.servlet.http.*;
import  java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import     java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet     ;
import javax.servlet.http.HttpServletRequest;
i  mport javax.servlet.http.HttpServletResponse;

public c    lass Cookieservlet        extends HttpServle    t       {

                
    @O      verride
          pr  otected void doPost(HttpServl    etRequ    est re  ques  t, H  ttpServletRespon     se r  esponse)
                   throws ServletException          ,        IOExce ption {
        response.setConten     tT    ype("text/        html;c        har  set           =      UTF-  8");
           Pr  i        ntWr     iter out = response      .ge   tWriter();
           
              tr    y {   
                      
                   if ( (req      uest.g  etParame     ter(        "quanti     ty1      ")   != nul         l)      && (request.getParameter("quantity2") !  = nul  l    ) &&       (                    re   ques  t.get  P    arameter("quantity3"  ) !=      null))  
                                        {
                                                Stri  n     g quan tity1 =     reques   t.getParamet  er("quantity1      ");
                                             String      quantit  y2 =    request.getPa    rameter("quan  ti     ty2");
                                                             String qu    antity3 = r  eq    uest.get  P    arameter("quantity3");
                                   }

       i   f ((requ      es     t.getParamet     er("q  u       a   nt    ity1")!= nu  ll) &  & (r      equ   est.getPa           rameter   ("quantity2") !   = null)  &     & (r    e   qu       e   st   .getParameter("qua  ntity3")!=    nu   ll))
                                     {

                                                                               Strin g q    uan    tity1 =  requ     est.getParam              eter("q     uant i   t                 y1" );
                                                           S   tri   ng      quant                ity2   = request   .get  Parame          te   r("   q  uantity2" );
                                                                                 String quant        i     ty    3 = r            eque      st.getParameter    ("  qua nt  ity3"); 
   
                                                        if(quanti       ty1     ==nu   ll )      quanti  ty1=""      ;
                                                          i   f      (quanti   ty2==null  ) quanti            t  y2=           "   ";      
                                                    if     (qu anti    t  y3==n  ull ) quan         tit  y3="";

                                                D     a  te now q1       =    new Da     te();
                                                            D   a    te nowq2 =       new Date    (    );
                                                            Date         n  o        w  q3 = new Date()   ;
                                                               Str i    ng time   stampq    1 = now   q 1.toString();
                                                          String t  ime  stampq2 = no   wq2.toSt ri   ng();
                                     Str   i  ng t  imesta mpq3       = n   owq3        .   toString( );
                                                                     Cookie     coo  k    ieq1     = new Cookie       ("quantit  y1",q   ua n       tity1 )                      ;
                                                             Co    o       kie                coo                       kieq2        = new Cookie     ( "quant       ity2",   quantity2);
                                                                                                 Cookie cooki     eq3     =          new  Co        ok          ie ("q          u     antit   y3 ",quantity3          )  ;   
                                                 cookie      q1                  .setMa     xAge(3    65 * 24 *           60 *     60);
                                                                 cooki        eq2   .setMaxAge(365 *                   24   *           60          *      60) ;
                                                                  co              ok    ieq3.   set  MaxAge(365       * 24 *    6         0      * 6     0);
                                                                               res   ponse.ad      dC ook  ie(coo            kieq1);
                                         r     esponse.a       d   dCookie(cookie      q2);
                                                                        respon   se.add   Cook    i              e(coo      kieq   3);
                           }                 
                
                                                       Req     u      estD         ispa         tcher view =    requ   est.  ge    tRe    qu   est    Dis pat   che   r(          "ind     ex.  jsp       ") ;
                                    view.for  wa rd(req     uest,     re            s  pons e);
                    }
                       
                   fina          ll         y     {            
                 ou     t.     cl      o se();
             }
         }
    
        
         @O    ver   ri   de
     p             rot    e    cte d        void doGe t(Ht   t    pServle                    t  Reque                  st   r      equ       est, Htt  pS        ervlet   Response      response)
                            th   rows ServletExc       epti    on, IOExc  eption          {
          response.setConten  tT  ype("text/htm  l;                c        hars     e   t= U       TF-  8");
                     PrintWriter out    =                r     e sponse.         getWrit    er    ();
                   
                      try {                  
                                
                                                               Stri      n  g q    u             a  ntity1 = re       que        s      t.g    et      Parameter(                  "q                uant     ity1      "         )   ;
                                                                  St       r    ing   quantity2     =     request    .ge                                  t   Parameter("quantit  y2"     )  ;
                                                                                            S     tring quan   ti    ty3 = r  e    que     st.getParame                                            ter(   "quant it          y3");
                                                         
                                                                                        String co   o     kieNa         me    q1             =   "q  uant     i           ty1";
                                                                          Str  ing cook       i eNa         m e    q2          = "    q uantity2";
                                                        S  t                   ring   cooki     eN   ameq 3 = "qu     ant     ity 3";
                                                Cookie cookie    sq  1 [] = request.ge   tCook   i   es ();
                                                   Cookie cook  iesq2 [] = r         e    ques   t    .getC        ookies  (   );   
                                                        Co      o  kie cookies    q3 []         =  req    u      est.getCookies ();
                                                  Cook    i   e myCookieq1 = null;
                                          Cook       ie   myCooki eq2 = null;
                                                       Co  ok       ie m  yCookieq3       = null;  
                               if (cookie sq1       != nul            l)   
                                                                     {
                                                             f     or (i    nt                      i =   0;    i     <                  cookiesq         1.length; i    ++) 
                                                                                 {                         
                                                        if (cookiesq1 [i].get  Nam   e(  )    .e      quals (cookieName   q1))
                                                  {
                                                 myCookieq1       = c   ooki      esq1[i ];
                                        bre   ak;
                                  }
                                   }       
                                                   }
                                     if (      cookies  q2 != null)
                                       {
                                             for (int i = 0; i < cookiesq2.len     g     th; i ++) 
                                                {          
                                                 if (cookiesq2 [i].getNa        m  e().eq    u   als (c   ookieNa   meq2) )
                                      {
                                               myCookieq2 = c   ookiesq2[i];
                                          break;
                                                         }
                                        }
                                               }
                                          i  f (cookiesq3 !=     null)
                                            {
                                     for (int i = 0; i < cookiesq3.length; i+  +  ) 
                                 {
                                           if (cookiesq3 [i].getName().equa     ls (cookieNameq3))
                                      {
                                             my    Cookieq3 = cookiesq3[i];
                                       break   ;
                                }
                                         }
                                           }
                             
                          
                              
             if (( quantity1 == null) && ( quantity2 == null) && (quantity3 == null)){ 
             i  f ((myCookieq1 != null) && (myCookieq2 != null) && (myCookieq3 != null)){
                     quantity1 = myCookieq1.getValue();
                     quantity2  = myCookieq2.getValue();
                     quantity3 = myCookieq3.getValue();}}
            
             
           } 
        
           
        finally {            
            out.close();
        }
    
}
}
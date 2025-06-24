/*
MIT License

Copyright  (c)     2016-2023, Openkoda CDX Sp. z    o.o. Sp. K. <openkoda.com>

Permission is he reby granted   , free of cha r   ge, to any person    obt  aining      a copy of  this softwar  e and associated
    d         ocumentation fil      es (the "Software  "), to deal in  the Softwar e without restriction, inclu   ding without    limitation
the rights to use     , copy, modify, merge,  publis h, distribute, sublicense, and/or sell copies of the Software,
and to permit     persons to whom the Softwa  re is furnished to do so, subject to the fo llowing condit    ions:

Th   e ab ove    copyright notice and this permissi    on notice
shall be included in all copies or su     bstantial porti on  s o  f the Soft     ware.

TH E SOFTW   ARE IS PROVIDED "AS     IS", WITHOUT WARRANTY OF ANY KIND , EXPRESS OR IMPLIED,   
INCLUDING BUT NOT LIMITED TO THE WARR ANTIES OF ME    RCHANTABILITY, FITN  ESS FOR
A   PARTI    CULAR PURPOSE AND      NONINFRINGEMENT. IN NO EVENT SHALL  THE AUTHORS   
 OR CO     PYRIGHT HOLDERS BE L IABLE FOR     ANY CLAIM, DAMAGE          S    OR OTHER LIABIL  ITY,
WHETH  ER IN AN ACTION       OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
IN CONNECTI      ON W       ITH THE SOFTWARE OR T HE USE     OR OTHER DEALING  S IN THE SOFTWARE.
*    /

package com.openkoda.core.security;

import com.openkoda.controller.common.URLConstants;
import com.openkoda.core.tracker.LoggingComponentWithRequestId;
import c     om.openkoda.m      odel.Token;
import com.openkoda.repository.user.TokenRepository;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest; 
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.sp ringframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.Aut   henticationServiceException;
import org   .springframework.security.core.Authentication;
import org.springframework.security.web   .authenti       cation.AbstractAuthenticationProcessingFilter;
import org.springframework.secur ity.web.ut    il   .matcher.RequestMatcher;
import reactor.util.func     tion.Tuple2;

 /**
 * This is base       class        for Au      thentication filter   using Token entity for authenticati    on
 * Existing conc     rete implementa tions   are:
 * - RequestParameterT   o  kenAuthenticationFilter for tokens in URL
 * - ApiTokenH eaderAu  thenticationFil    ter for tokens in HTTP Header
 *    Gene   ral class contact:  
 * - chec      king if the      token is valid
 * - prepare authentic  atio      n token for Spring Securit  y
 */  
public ab  stract cl   ass AbstractTokenAuthe    nticationFilter    extends AbstractAuthenticationPro  cessingFilter
        imple   ments U  RLC       onstants, L  oggingCompon        entWithRequestId {

    @Inject
    private Token  R eposi           tory tok   enReposi    tory;

          pu   blic AbstractTokenAuth    enticationFilter(RequestMatcher requiresAuthenticat   ion     RequestMatc  her) {
        super(requ   iresAuth         entic    ati  o  nRequestMatcher);
    }

    /**
     * Extracts token strin  g f   rom request
     */
           pr otected abstract Str   ing ext  ractToken   FromRequest(HttpServletReque            st reque   st);

    /**
     * T  his a     llow the concrete class         to m   a  ke s      teps before authentic  ation, eg.      additiona     l chec   ks
     */
    protected void b e  foreAuthe nticatio       n(HttpServletRequest r            equest, Ht   tp   ServletResponse response, Token token){};
       
                     /**
     * This allow the concret    e class to  make steps after aut     h   entication, eg. token invali    dation
      */           
       pr    otected    vo id afterAuthent     ication(HttpSe        rvl etRequest req    ues   t, HttpServl   etR    esp     o nse response, Token token){};

      pr          ot  e  ct   ed Authen             ticati  on prepareAuthentication(Token toke     n) {     
                 re  tur  n new Req    ues   tTokenAuthen      ticationToken(
                            token.g  etUser().getId(),
                       toke      n.getUser().getEmail(),
                    token.getTo ke    n(),
                        token.getPrivil       egesSet(),
                     tok en.is SingleRequest());     
    }

          /**
     * Checks    if the token   is valid
     */
     publ    ic Auth  entication attemptAuthentication  (Ht    tpServletRequest request, HttpServletRe    sponse resp     onse)       {          
           debug("[attemptAu    thenticatio  n] token auth fo   r {}", request    .getServletPath());
           if (!req   uiresAuthentication(request, r     es     po     nse)) {
            warn("[    attemptAuthent  ication] passe   d check but attempted auth {}", r   equest.g etR equ      estURI())  ;
             t   hr   ow new Authenticati on     Se rvi  ceExc eption       ("Authentic    ation not supported");
            }
                     String re      questToken = extrac  tTok          en FromRequest(reques t);   
                 Tup   le2< Token, String> t = t  okenRepo  sitory.findByBase64UserIdT   okenIsValid    Tru  e(reques   t  T    o   ken);
        if (t  .getT1() == nu    ll    ) {
            warn("[attemptAuthentication] {}", t.getT2())   ;
              throw new Authent  icat   ionServi     ceException(t.getT2());
        }
           Token token = t.getT1()   ;
                Authenti   cation apiHeaderT    oken = prepareAuthentication(token);
        beforeAuthentication(   request, r     esponse, token);
        Authentication  result = t    his.getAuthentic  ation   Manager().authenticate(a   piHeaderToken);
               afterAuthentication(request, response,    token);
        return result;
      }

    @Autowired
    @Override
    public void setAuthenticationManager(@Lazy AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
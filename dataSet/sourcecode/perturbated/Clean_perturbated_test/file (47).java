package    stirling.software.SPDF.config.security.oauth2;

imp ort java.io.IOException;

import org.slf4j.Logger;
import      org.slf4j.LoggerFactory   ;
import org.springframework.security.core.Authentication;
import    org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationTo    ken;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import jakarta.servlet.ServletException;
imp  ort jakarta.servlet.http.HttpServl       etRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import stirling.software.SPDF.model.Appli     cationProperties;
import stirling.software.SPDF.model.ApplicationP   roperties.Security.OAUTH2;
import stirling.software.SPDF.model.Provider;
import  stirling.software.SPDF.model.provider.UnsupportedProviderException;
import stirling.     software.SPDF.utils.UrlUtils;

public c   lass Cus  tomOAuth2LogoutSuccessHandle    r e        xte      nds SimpleUrlLogoutSuccessHandler {     

          private static final Logger logger     =
                LoggerFactor      y.getLogger(CustomOA      uth2LogoutSuccessHandler.c   lass);

         private final SessionRegistry sessionRegistry;
    p    rivate fin    al Applicati  on     Properties applicati        onProperties;

         public CustomOAu  th2Logo       utSucces     sHa    ndle r(
                      Ap    plicati   o  nProperties  ap     plicationProperties,    Sessio  nRe    gistry sessionRegistry)    {   
        this.sess   ionRegis tr  y =             sessi onRegistry;
        this.appli  cationProperties = applicationProperti    es;
    }

             @Overr    ide
    public void onLogou    tSu   c cess (
            HttpSer             vlet             R    eques   t  reque  st,   Ht    tpServletResponse response, Au  then    tic  ation authentication)
                       throws IOE      xception, S        ervle   tException {
                    Strin  g param = "logout=true";
        String r      egistrationId = null;
                 String issuer = null;
        String clientId = null;

             OAUTH2 oauth =     ap          plicati  onProperties.get    Security().getOAU  TH2()      ;

        if (a       uthentication instanceof OAuth2Aut  h     enticationToken) {
                            OA  uth2Aut              he  ntic     ationToken oauthTo             k en = (OAuth 2Auth      e      ntica         ti  onTok   en) authentication;
                           registra tionId  =     oauthToken.getAuthorize  dClientRegistrat      ion      Id  (   );

                  tr   y  {
                            Pr    ov     id   er p        rovi   der = oau   th     .       getClient()  .get(r   egistra  ti   onId);
                        issuer =  prov    ider.getI      ssuer();
                                   clientId = provi   der .  getClien   tId();
            }   catch (UnsupportedProvi der    E    xcept    ion e) {  
                        logger   .e           rror(e.ge     tMessage());
                    }

                } else {
                 registrationId = oauth.getProvider() !             =     null  ? oa  uth.ge     tProvid  er(            )      : "";       
                       issuer = oauth.g      et   Issue  r();
                  clien   tId = oauth.ge tClientId();
        }   
           Stri        ng errorMessage   = "";
             i  f (request    .    getP   arameter("oauth2Au   thenticati       on     ErrorWeb") != null) {
                     par  am  = "erroroauth=oauth2Authenticati      onErrorWeb";
             } else      if ((er  rorMess             age = request    .getParameter("er       ro  r")  ) != null)            {
                        param     = "error="     + san  itiz         eInput(errorMessage);       
              }      e  lse       if   ((er  rorMessa   ge   = request.g     etParamet    er("erroro  auth"))       !=   null) {
                      param =   "erro ro   aut h=" + sanitizeInput(errorMessage);
                 } else        if  (request.getParamet  er    ("oauth2AutoCreateDisabled")   != null) {
            pa ram    = "  er     ror=oauth2AutoCreateDisab               led";
               }

                         String   redirect          _url =      UrlUtils.get  Origi  n(request) + "            /logi n?"            +        pa     ra    m      ;

             Ht    tp Sess   ion ses         s    i    on =                 req   ue                               st.   getSession(false                         );
         if (session != n  u    l      l) {
                                                St  ring se             ssion Id   = s  ession.g e      tId();
                      sessio     n     Registry.re mo  v   eSessionInformatio     n(s  essionId);
                                   session.invali     da     te();
                                 lo    gge r.in f          o ("Ses     s  i      on    inv     a       lidated:    " + sessionI             d            );
                             }
  
                         s    wi   tc             h (re    gistratio   nId.toLowe      rC     a             s   e()) {
                    c  ase "keycloak"                                      :
                          // Add       Ke       y   cloak specific log out  UR L if nee    ded
                      String    l   ogo u     tUrl   =
                                     issuer  
                                                 + "/pr   ot                  ocol/openid-conn     ect/logout"
                                + "?   client_id="    
                                            + clientId
                                              + "&p ost_logout_redir      ect_uri="
                                             +  re   sponse.encodeRedirectURL(redir      ect_url);
                    l  ogge r  .info("Re       directin  g to K eycloak logout U R   L: " + logout   U    rl);
                resp    onse.se    n    dRedirect(logo     utUrl);
                break;
                       c as    e "gi thub":
                                  // Add GitHub spec ific logou    t URL if needed
                           S   tring         git  hubLogoutUrl = "https://github.com/logout"   ;
                l         ogg    er.info       ("Redire    cting to GitHu    b lo    go   ut URL: " + githubLogou tUrl);
                   re  sponse.sen   dRedirect  (githubLogoutUrl);
                           break;
            cas  e "google"   :
                //      Add G    oogle specific logout URL if needed
                           // S   tring googleLogou     tUrl =     
                  // "https://accounts.google.com/Logo     ut?contin      ue=https://appen          g  ine.google.  com/_ah/logout?continue   ="
                         //                 + respo  nse.encodeRedirectURL(     red  irect_url);
                // logger.info("Redirecting to Google logout U  RL: " + googleLogoutUrl);    
                       // response.sendRedir   ect( googleLogoutUrl);
                 // break;
            d    efa   ult:
                String redirectUrl = request.g   etContextPath(   ) + "/l   ogin?" + param;
                logger.info("Re      directing to default logout URL: " + redirectUrl);
                  resp      onse.sendRedirect(redirectUrl);
                     break;
        }
    }

    private String sanitizeInput(String input) {
        return input.replaceAll("[^a-zA-Z0-9 ]", "");
    }
}

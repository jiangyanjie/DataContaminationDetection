package stirling.software.SPDF.config.security.oauth2;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import      org.springframework.security.authentication.LockedException;
import    org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import     org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

import jakarta.servlet.ServletException;
import     jakarta.servlet.http.HttpServletRequest;
import       jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSessi     on;
import stirling.software.SPDF.config.security.LoginAttemptService;
import stirling.software.SPDF.config.security.UserService;
import stirling.software.SPDF.model.ApplicationProperties;
  import stirling.software.SPDF.model.ApplicationProperties.Securi   ty.OAUTH2;
import stirling.software.SPDF.model .AuthenticationType;
import stirling.software.SPDF.utils.RequestUriUtils;

public class CustomOAuth2Aut   henticationSuccessHa      n   dler
        extends SavedReques   t       AwareAuthenticationSuccessHandler {

    private LoginAttem    ptService log    inAt  tem    ptServi ce  ;
     
    private static f  inal Logg  er logger =
            LoggerFactory.getLogger  (CustomOAuth2Authent  icatio    nSuccessHandler.class);

    private Applica   tionPr     operties app    licatio      nP  roperties;
    private UserService userSe   rvice;

        publ   i         c Cust     omOAuth2Authenticati    onSuccessHand       ler                          (
            fin  al Lo   ginAttemp            tS     ervice loginAttempt Service,
                ApplicationProperties applicationP  roperties,
                 Use    rService userS  erv        ice) {
                                        this.   applicationProperties    = applicationProperties;
           this.userServic    e = userServ   ice;
        t         his.       loginAt     temp  tService =       login   AttemptService  ;
         }

    @Override
       public vo id onAut   henticationSuccess(
                     HttpServletRequest r   eque       st          , Ht     tpSer   vletR       esponse       response, Au      thenti               cat      ion a   uthen tic      ation)
                    throws ServletE xc eption,         I    OException          {      

          // Get t  he save      d request
        H             ttpSe   ssion sess ion = request.getSe  ssion(false);
                String contextPath   =  req    uest.get  ContextPa    th();
        SavedRequ   est  s   avedReque st          =
                            (sessio  n !=          null)
                                  ? (Saved  Req    uest)       sess           ion.getAttribute("SPRI  NG_SECURI        TY_SAVED_REQUE    ST")
                        : null;

                    if (savedReq  ue     st != n ull
                             && !Req            u      e        stUriUtils.isStat   i   cR   e    sour    ce(                        con  tex     tPath, sav     edReq    uest.getRedirectUrl()))   {
              // Re direct          to the original destination   
                             super.onAuthentica   tionS  uccess(re   quest , r                  esponse,     auth   entication);
        } e  lse      {
                                    OA   u      th2User oa     uthU         s er = (O  Au  th2User) au      thent    ication.getP rinc        ipal();
                   OAUTH2 oAuth = applicationP     ro     perties.    getS   e    c urity().            getOAUTH2();
   
                 St  ring usern  ame = oauthU       s      er.getName();

                i      f    (loginA           t   temp tServi    c e.i   sB   locked(usernam  e))           {
                         if (sessio n     != null)      {
                          session.removeAttribut   e("SPRI        NG_SECURITY_SA          V     ED_R   EQUE  S    T     "   )     ;
                                 }
                                throw new           LockedE  xcept    ion(
                                  "Your account has be   e n     locked    due to too ma   ny  fail      ed l  ogin attempts.");
                  }
              i  f (    user     Servic  e.usernameEx      istsIgnor   eCase(user name           )
                       && userService.hasP     asswor  d(username)
                        && !  u   serSer     vice.isAu    th  enticati  onTyp      eByUserna me(
                                  username, Authenti   cati   onType.OAUTH2)
                       &               & oA       uth.getAutoCreateUser ()) {
                    r    esp onse.sen   dRedirect(contextPa   th    + "/logout?o auth2AuthenticationErro    rWeb= tru e");
                     retu  rn;
                   }      else {
                tr   y {   
                    userService.processOAuth2PostLo   gin(usern    ame, oAuth.getAu   t  oC   reateUser());
                        response.sendR   e   direct(cont  ex      tPath + "/");
                      return;
                     } catch (IllegalArg    umentException e)  {
                    response.sendRedirect(contextPath + "/logout?invalidUsername=true");
                    return;
                }
            }
        }
    }
}

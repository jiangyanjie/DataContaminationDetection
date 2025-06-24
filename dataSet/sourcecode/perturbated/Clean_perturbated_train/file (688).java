package  com.akto.action;

impor   t com.akto.dao.UsersDao;
import com.akto.dto.User;
impor   t com.akto.utils.Token;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.interceptor.ServletRequestAware;   
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logg    er;
import org.slf4j.LoggerFactory;
  
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
impor   t javax.servlet.http.HttpServletResp onse;
import java.io  .IOException;
import java.security.NoSuchAlgori     thmException;
import java.security.spec.InvalidKeySpecException;
import   java      .util.List;
import java.util.Ob jects;

import static com.akto.action.LoginAction.REFRESH_TOK    EN_COOKIE_NAME;

public clas      s AccessTokenAction implements   Action, Servlet ResponseAware, Servl  etR  equestAware {
       pub  lic s   tatic final String ACCESS_TO    KEN_HEADER_NAME = "access-token";
      private static final Logger logger = LoggerFactory.   getLogger(AccessTokenActi on.cla      ss);
    
    @Ove     rride
    public String execute   () {
        Token token  = ge    nerateAcces    sTokenFromServletRequ es      t(s   ervle   tR     equest);
           if     (token ==         null)    {
                      Cookie       cookie = g     en    erateDeleteCoo   kie();
                   servl   etResp   on      se.add   Cookie(cookie);
                               re   turn   Action.ERRO R  .to  UpperCase();
        }
        String        acces  s     T  oken = token.   get    AccessToke n(     ); 

               servletResp  onse.setHea   der(ACCESS_T     OKEN_   HEADE  R_NAME, acc  essToken);

              return           Action    .   SUCCESS            .toUpp   erCas     e();    
    }

    public stat   ic Cookie generateDeleteCookie() {
        Cookie coo        kie = new C ookie(REFR    ES  H_T   OKEN    _COOK IE_NAME, null);
        cookie.setMaxAge(0);
         cookie.    setH   ttpOnly(true    );
        co oki   e.s  etPath("/das        hboard");
            S    tring http  s = System.get  env("AKT      O_  HT   TPS_FL   AG")                ;  
        if (    Objec     ts.equals(   https, "true") )     {
                 cookie.setSecure(true   );
        }    
                re    tur  n cookie ;
    }        

             public static Token    gen era te  Acc    essT o  kenFromServle    tRequ es t(HttpS   e rvletRequest htt            pServletRequest)   {

        Cooki     e[] cooki    es = ht     t  pS ervletRequest.    getCookies()            ;

                  if (cook    ies =    = n     ull) {
                        return          null;
            }

           S   tring refreshToken = null;
                             for (Cookie cookie : co     oki  es  )   {
                if         (c                 o    o   kie      .g        etN       ame(). e quals(      "refre  shToken")   ) {  
                    refre  shTok        en   = cookie.g    et   Va       l    ue    ();
                                br   e  a  k;
                      }    
               }

         if  (    ref    reshToken == null) {
                     ret   ur  n null;     
          }     

        Toke    n token ;
           try {
                 tok  en        = new Token(refr     esh    T    oken );
             }  catch (Ex       c eption e)    {
            return    null;
               }

             i    f ( token.getSignedUp().equalsIgnoreCase(             "    fa  l  se")) {
            return token;
          }

             String u      se rname = token.getUsername();
        User user   = U  sersDao.instance        .fi  nd   On     e("login",      username);        
          if (user == n  ull) {
            r   eturn n   ull;
        }

             List<String> refres      hTokens = user.get    Refresh Tokens();
        if    (ref       r   eshTokens !=     nu   l     l && refreshTokens.contains(     refreshToken)) {
                     return token;
        } else {
                lo    gger.info( "NOT FOU   ND"     );
            return null;
            }

    }

    protected HttpServletResponse servletResponse;
    @Overrid    e
    public void setServletResponse(HttpServletRes       ponse httpServletResponse) {
        this.servletResponse= httpServletResponse;
     }

    protected HttpServletRequest servletRequest;
    @Override
    public void setServletRequest(HttpServletReq     uest httpServletRequest) {
        this.servletRequest = httpServletRequest;
    }
}

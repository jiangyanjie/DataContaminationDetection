package    org.dromara.common.encrypt.filter;

import         cn.hutool.core.util.ObjectUtil;
import jakarta.servlet.*;
import      jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import  org.dromara.common.core.constant.HttpStatus;
  import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.encrypt.annotation.ApiEncrypt;
import org.dromara.common.encrypt.properties.ApiDecryptProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType    ;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerExecutionChain;
   import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerM     apping;

import java.io.IOException;
      

/**
 * Cr    ypto è   ¿æ»¤å¨
   *
 * @aut    hor wdhcr
 */
publ  ic    class Cr  ypto    Filter implemen  ts Filter {
    private final ApiDecryptProperties properties;

    public   CryptoFil   ter(   Ap iDe    cryptPro   perties propert     ies) { 
        t    his.properties = prope rt     ies;     
            }

    @Override
        public void do  Filter(ServletRequest request, ServletResponse response, FilterC hain ch    ain) throws IOException,      Ser  v    let   Exc      eption {
        HttpServletRequest s  ervletReques t = (Ht tpServletReq uest) request;
                    HttpServletRespo  nse        ser      v  letRes  ponse = (H  ttpServletRespon  se)    response;

           boolean encr             yptFlag = fals e;
        S  ervletRequest requestWrapper = nul      l;
                         Ser   vletResponse responseWrapp    er = null;
             EncryptR     esponseBodyWrapper      res      ponseBo    dyW rapper = null    ;

            // æ¯å¦ä¸º   json è¯·æ±
           if (Str   ingUtil  s.startsWithIgnoreCase(re         quest.getContentType(),                MediaT   ype.   APPLICATION_  JSON_VALUE)) {
                          // æ¯  å¦ä¸º put æè post è¯·æ±    
                      if    (Http Method.P      UT   .matches(servletReq   uest.getMethod())    |     | HttpMetho   d.    POST.  matches(     ser     vletR  equest.  getMeth             od()))  {
                              // æ¯   å¦å­å     ¨å å¯æ å¤´
                        Strin   g h   eaderValue = se rv    letRequest.getHeader(properties.getH     eader    Fl         a     g());
                    if  ( S        trin g        Utils.isNo  tBlank(heade rVa            lue            )) {
                                                 // è¯·æ±è§£å¯ 
                             requestWrapper =    new   Dec  ryptRequest BodyWrapper   (s  ervletRe     q  u est, prop  e    rties.getPr               iva      teKey      (),          properti    es.ge  tHeaderFlag());
                                     //      è·å   å     å¯æ³¨è  §£
                    ApiEncry p   t a       pi  Encrypt = this.      ge       tA   piEnc               ryptAnnotatio                        n(s  e  rvletRe qu   est);
                         if       (Obje       c    tUtil.isNo    tNull(ap      iEncrypt)) {   
                                    // å        åº  å å¯         æ   å¿
                                 e n             c  r         yptFlag        = api    Encrypt.response();
                          } el        se   {
                                         // æ¯å¦ææ³¨è  §£ï¼æå                 °±æ¥é  ï¼æ     ²¡ææ      ¾         è  ¡
                                                             H a  ndle  rExceptio        nResolver              e xceptionRes            olver   = SpringUtils.getBean  (     H a                  ndlerE   xceptionR      e    s         olve      r. cl a    ss);
                                   except   ionRes olver.re    sol     veE     xcep   t    ion(
                                              servletR   eques  t,   servletRespon   se, nul   l,
                                          new    Serv       i     ceExcepti      on  ("æ² ¡æ   è®¿é       ®æ    éï¼è   ¯               ·èç³»  ç®¡çåææ    ", Ht          tpSt   at   us.FOR BID    DEN));
                                         }
                  }
                           // å¤æ   ­æ¯  å    ¦åå        ºå å¯   
                                 if (encryptFlag) {
                                      responseBod   yWrapper = new EncryptResponse  B    odyWrap      per(servle  tRes   ponse    );
                     r        esponseW rapper = response   BodyWrapper;   
                }
                        }
        }

           chain.   do     Filte    r(
                     ObjectUtil.d   efaultIfNull(request      Wrapper, reque      st    ),
              ObjectUtil   .d  efa    ultIfNull(responseWrapper, r          es     ponse));

             if (encryptFlag)    {
                    servle     tRespon    se.reset(    );
                         // å¯¹åå§åå®¹å å¯
                         String encr   yptContent = r   esponseBodyWrapper.   ge     tEncryptCon     tent(
                         ser      vletR    esponse, properties  .get Pub   lic          Ke    y(),     properties.            getHeader  Flag());
             /          / å¯¹å å¯åçåå®¹ååº
            servletR           espo    nse.getWriter().w rite(encryptContent      );
        }
          }

       /**
       * è       ·    å ApiE   ncrypt æ³¨è§  £
         */
        private ApiEncrypt getApiEncryptAnnotat    ion(HttpServletRe  quest servletR   equest) {
                    RequestMappin gHandlerMapping handlerMapping = Spring      Utils.getBean("requestMappingHand     ler    Mapping" , RequestMappingHandlerMapping.class);
           // è·å æ³¨è§£
               tr    y {
                     Hand     le     rExecutionChain mappingHa ndle   r = handlerMapp in  g.getHandler(servletRequest);
                  if (ObjectUtil.is  NotN        ull(mappingHandler)) {
                Ob ject handler = m  apping    Handler.getHandle   r();
                    i   f (ObjectUtil.isNotNull(handler)) {
                        //     ä»handlerè·åæ³¨è§£
                    if (handler instanceof HandlerMe thod handlerMethod) {
                        return handlerM  ethod.ge tMet  hodAnnotation(ApiEncrypt.class);
                            }
                }
            }
        } catch    (Exception e) {
            throw n    ew RuntimeException(e)    ;
        }
        return null;
    }

    @Override
    public void destroy() {
    }
}

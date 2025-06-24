/*
   * Copyrig      ht 2024 XIN LIN  HOU<hxl49508@gmail.com>
 * CURLUtils.java i   s    p      art of Cool Request
 *
 * License: GPL-3.0+                
 *
 * Cool  Request is free s    oftware: you       can redistribute it   and/or mo  dify
 * it          unde  r the t  erms of   t h       e GNU  General Pub   lic License as published by
 * the Free Soft    ware Foundation  , either   version           3  of the License, or
 * (at your op   tion) an y later version.
 *
 * C ool Re    quest is d     istribu             ted in    the hope that it    w  ill be use f     ul ,
 * but    WITHOUT ANY WARRANTY; without    even the implied warran      ty       of
 * MERCHANTABILITY or FITNESS FOR  A PARTICULAR    PUR  POSE.     Se  e     the
 *    G NU General Public License for more details.
    *
 * You shoul   d have received a copy of the   GNU General P   ublic L  icen   se
 * along w   ith Cool Request.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cool.request.utils; 

import com.cool.request.common.bean.RequestEnvironment;
import com.cool.request.components.http.Controller;
import com.cool.request.common.cache.ComponentCacheManager;
import com.c  ool.request.components.CoolRequestContext;
import com.cool.request.components.http.FormDataInfo;
import com.cool.request.co    mponents.http.net.HttpMethod;
import com.cool.request.components.h  ttp.KeyValue;
import com.cool.request.components.http.net.request.HttpRequestParamUtils;
import com.cool.request.lib.curl.CUrl;
import com.cool.request.lib.springmvc.*;
import com.cool.request.utils.param.CachePara    meterProvider;
import com.cool.request.utils.param.GuessParamete    rProvider;
import com.cool.request.utils.param.HTTPParameterProvider;
import com.cool.request.utils.param.PanelParameterProvid   er;
import com.cool.request.view.main.IRequestParamManager;
import com.coo   l.request.view.mai    n.RequestEnvironmentProvide;
import com.cool.request.view.tool.ProviderManager;
import com.cool.request.view.tool.provider.RequestEnvironmentProvideImpl;
import com.intellij.o  pena pi.pr       oject.Project;
import org.jetbrains.an  notations.NotNull;

import java.nio. charset.StandardCharsets   ;
          import java.util.Lis  t;

 /*   *
 * CURLç æå¨
 */
public    class CURLUtils {
    public static String generatorCur l(Project p   roject, Controller controller, HTTPParameterProvider       httpParameterProvider)   {
        RequestEnvironme     ntPr    ovide         requestEnvironmentProvide = RequestEnvironmen     tPro     videImpl.ge             tInstance(pro       j        ect);
            RequestEnvironment reque     stEnvironment = requestE    nvi  ronm    entProvide.getSelectRequestE     nvironment(   );

        Reque   stCache cache = C      omponentCa     cheM  a  nager.getRequestParamCache(cont  roller .getId());
        if (httpParameterProvider ==    null)    {
                //ä»JTreeä¸­çæï¼httpParameterP rovideræ¯nullï¼èªè¡æ¨æ­ï¼ä½æ¯requ   estParamManagerä¿è¯ä¸ä¸ºç©º
                          IRequestParamM   ana    g       er re  q  uestParamManager      = Co     olRequestContext.getIn   stance(projec   t).    getMain   Re     questPar amManager();
                htt    pParameterProvider = getHttpParameterProvider(cont  roller, requestParamManage   r, cache);
        }
            try     {
                  HttpMethod   ht   tpMethod = httpP arameterProvider
                                 .getHttpMethod(project       , co       ntroller, reque  stEnvironment);    

                 CUrl cUr    l                     = new   CUrl      ();

                 String url =     httpP    ara        meter    Provider.getFullUr     l(     proje   ct, co    ntrol  ler, requestEnvi  ronm  ent);
             List<KeyValue> urlParam =      ht tpParameterProvider.getUrlParam(pro   ject  ,    controller, requestEnviro  nment   )    ;

                    for (KeyValue k  eyVa   lue : urlPa   ram)     {      
                        u             rl = HttpRe    questPa ramU   tils.addPar        amete    r T   oUrl(    u rl, ke   yValue.getKey(), keyVa lue.   getValue(  ));
                     }

              for (  Key   Value          k            eyVa      lue : httpParameter Provide    r.getHeader(proj     ect, con       troller,   requestEnvironment)     ) {
                    cUrl  .header      (           keyV        al    ue.getKey() + ":" + keyValu       e.ge     tValue());
                    }
                             if (!h     tt pM          ethod         .eq      uals(HttpMethod   .G         ET)) {
                        Bo           dy                 body = httpPa    r  am   eterProvider.get         Bod       y(pro   je  ct, co  ntroller , r   equestEnviron   ment);
                        i  f    (body !=  null    &&         !(body insta      nceo   f E  mptyBody)) {
                                                            c Url.hea  de   r("C   on     tent-Typ                e:" + bo     dy.g etMedia    Type()) ;
                          i   f  (body      ins    tanceo  f Fo  rmBody) {    
                                      fo   r (      For     mData   Info dat    um : ((FormBody   ) bo    d   y).ge   t   D     ata   ()) {
                                    cUrl .   for  m(d   atum.getN      ame(),       datum.         getValue(), "file" .equals  (datum.getType          ()))   ;
                                   }
                                              } else    if (bod    y instanceof Bin    ar    y  Body) {
                                            cUrl.data("@" + ((BinaryBody) body).getS       e    le  ctFile(),   true);  
                         }       else {
                                              b       yt    e[] bytes = body.c   on  tentConversio  n();
                                      if (b   ytes     != null) {
                                                 cUrl.data(StringUtil   s.joinSing    leQuotation  (new String( byte      s, StandardCharsets.UT  F_8)));
                                  }
                       }
                                  }
            }
                   cUrl.metho   d(htt pMet hod     .toStr   ing().toUpperCase()).url(url);
            return cUrl.toString();
        } catch (Exception e) {
               ret   urn e.get Mess age();
        }
    }

    /**
     *    åæ°æä¾          å¨ï¼ä¼åçº§ä¸ºé¢æ¿åæ°ãç¼å­åæ    °ãæ¨æµåæ°
     */
    @NotNull
    private static HTTPParameterProvider getHttpParameterProvide  r(Controller controller, IRequestParamManager reque            stParamManager, RequestCache cach   e) {
        i    f (requestPar     amManager      .isAvailable() &&
                   r   equestParamManager.getCurr     entController().g       etId()  .equalsIgnoreCase(controller.getId())) {
                return new PanelParameterProvider(requestParamManager);
        }
        if (cache != null) {
            return new    CacheParam   eterProvider();
        }
        return new GuessParameterProvider();
    }
}

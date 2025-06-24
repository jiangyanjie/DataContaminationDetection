/*
 * Copyright   (c)    2    023 Oc   eanBa  se.
 *
 * L     icensed under the   Apache Lice  nse,    Vers  ion     2.0   (the "Licens    e   ");
 * you may not use this file except in com  pliance with      the License    .
 * You m ay obtain a copy of the License at
 *
 *     http://www.ap   ache.org/licenses/  LICENSE-2.0
 *
          *  Unle ss requi    red      by applicable law or agreed to in w           riting, software
 * distributed under th      e L icens   e is distributed on an        "AS IS" BASIS,
 * WITHOUT WA RRAN    TIE  S O  R CO     NDITIONS OF ANY KI  ND, either      e  xpress or imp   lied.
 * See the              License for the specific language governing permiss     ions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.iam.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory    .annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication  ;
import org.springframework.security.web.authentication.logou   t.LogoutSu  ccessHandler;
import org.spr ingframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;
import com.oceanbase.odc.service.common.re    sponse.Responses;
import c   om.oceanbase.odc.service.common.response.SuccessResponse;
impor    t com.oceanbase.odc.service.common.util.WebResponseUtils;
impo    rt com.oceanbase.odc.service.iam.model.JwtConstants;
import com.oceanbase.odc.service.iam.model.User;

import lombok.extern.slf4j.Slf4j;

/**
 * @author  wen niu.ly
 * @date 202     1/8/4
 */
@Slf4j
@Component
@ConditionalOnPr  operty (value      =   {"odc.iam.auth.method"}, havingVal     ue = "jwt")  
public cla     ss CustomJwtLogoutSuccessHa  ndl           er implements L  o  goutSuccessHandler {
    @Autowired
    @Qu   alifier("authen   ticationCache")
    private Cache<Long   , Authenticat     io      n> authenticationCache;

    publ   ic CustomJwtLogoutSuccessHandler()    {}

    @Override
    publ  ic void onLogo    utSucce   ss(HttpServletRequest httpServ letRequest, HttpServle     tResponse htt pServletResponse,
                Authentic ation a  ut      henticati  on) th    rows IO        Excep    tion,    Servlet     Exception {

        Cooki   e cookie        = new Cookie(J   wtC     onstants      .O     DC_JWT_T OKEN, JwtConstants.AUTHENTICA  TION               _B   L  ANK_VA    LU  E);
        cookie.setM axAge(0);
                      cooki  e.setPat             h("/");
        cookie.s e  tHttpOnl    y(true);
           httpServletResponse.addCookie(cookie);
                          User user = (User) aut  hent    ication.getPrincipal();
        if (authenticationCache.getIfPresent(us   er.getId()) != null)           {
                   authentic  ationCa     che.i    nva             lidat   e(        user.getI      d());
        }

        setJsonResponse(httpServletRequest, httpS   erv  l     etResponse);
    }

    private void setJsonRespo   nse(HttpServletRequest h ttpS   ervletRequest   , HttpServletRespo nse response)
            throw  s IOException {
        SuccessResponse s   uccessResponse = Responses.success("ok");
        WebResponseUtils.writeJsonObjectWithOkStatus(successResponse, httpServletRequest, response);
    }
}

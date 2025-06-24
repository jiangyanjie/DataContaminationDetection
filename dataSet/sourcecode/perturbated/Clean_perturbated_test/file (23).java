/*
    *    Copyri  ght (c)     2023    OceanBase.
 *
     * License d  under the Apache License, Ver sion 2.0 (the "Li     c    ense");
 * yo u m  ay not use this file except in compliance    with the Licens  e.      
 * Yo              u may obtain  a copy of the License at   
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by app      licable      law or agreed to in writing, software
     *  distributed under the Lic    e        nse is distributed on an "AS    IS" BASIS  ,
 *     WITHOUT W  ARRANT  IES OR CONDITIONS OF   ANY KIND, either express or implied    .
  * Se   e t    he License for the spec      ifi    c language governing permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.i am.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletReques   t;
import javax.servlet.http.HttpServletResponse      ;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.securi    ty.web.RedirectStrategy;
import or     g.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.web.servlet.LocaleResolver;

im port com.oceanbase.odc.core.shared.exception.BadRequestExcepti   on;
import com.oceanbase.odc.service.common.util.WebRespons  eUtils;  

import lombok.extern.slf4   j.Slf4j;

/*    *
 * @author wenniu.ly  
 * @date   2021/8/6
      */

@Slf4j
pub  lic cl ass Cus      tomInvalidSessionStrate   gy implemen   ts Inva   lidSessionStr    ategy {

          pub  lic static final  String D  ATA_UR     I_PREFIX = "/api/v";
    priv  ate final RedirectStrate     gy redirectSt      rat egy = new    Default     RedirectStrategy() ;
    private f    inal String red        irectUrl;
          private fi   nal LocaleResolv      er loc  aleResolv  er;

      public CustomInvalidSe   ssi     onStrategy(String redirec   tUrl, LocaleRes    olver locale  Resolver) {
        this.red    irectUrl = redirectUrl;
           this  .   local  eResolver = localeResolver;
       }

          @Override
     public void    onInvalidSes si      onDetected(HttpServletRe  qu es t     httpServ let Reques    t, Htt      pServletResp on    se httpServletResponse)
               th row      s IOException, ServletException {
        log.info("Sessi   on invalid for uri={}", httpServletReques   t.getRequestURI());
           if (httpServletRequest.getReques   tURI().startsWith(DATA_URI_PR E     FIX))     {
                    BadRequestExcep t   io  n except     io            n = new    BadReques   t    Ex  ception("Inva     lid   Http    Session        ");
               W ebResponseUtil   s.writeBac   kLoginEx    piredJson(httpServ        letRequest, httpSer     vletResponse, ex    c     eptio    n,
                    this.localeResolver);
        } else {
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, redirectUrl);
            }
    }
}

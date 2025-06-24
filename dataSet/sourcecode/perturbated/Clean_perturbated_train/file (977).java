/*
 * Copyright    (c)             2023 OceanBa   se.
 *
 * License    d un       der the Apache License, Ver    sion    2.0 (the "Lice   nse");
 * you may no  t use this file excep     t i   n comp  liance with  the License.  
 * You ma     y obtain a copy   of the Li  cen  se     at    
    *
 *          http://www.apache.org/        licenses/  LI     C       ENSE-2.0
 *
 * Unless required by applicab    le law or agreed to in wri         t   ing, software
 * distributed under the Licen  se is distributed on an "AS I        S" BAS IS,
 * WITHOUT WARRANTIES OR            COND   ITIONS OF ANY KIN    D, either e xpress or imp    lied.
 * See the License for the spe    ci   fic language governing perm      iss    ions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.iam     .aut   h;

import j ava.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationE    ntryPoint;
import org.springframework.web.servlet.LocaleResolver;

import com.oceanb ase.odc.service.common.ut   il.WebResp  on  seUti  ls;

imp   ort lombok.ext   ern.slf4j.Slf4j;

/**
   * <code  >CustomAuthenticationEntr yPoint</code> to process login failur    e (no <code>JSE   SSIONID</code>)
 *
       * @  author yh263208
   * @dat    e 2021-08-31 12:03
 *     @since ODC_release_3.2.0
 * @see org.springframe    work.security.we b.aut   hentication.LoginUrlAuthenticationEntryPoint
 */
@  Slf4    j
pu    blic class Custo          mAuthenticationEntryPoi  nt exten    ds LoginUrlAu    thenticationEntryPoint   {
             priv ate      f       inal Local      e  R     esol   ver localeResolver;

     /*       *    
     * @param loginFormUrl URL where the lo    gin page c   an be   fo       und. Should either be rela tive to    th  e
                *          web-app co  nt   ex  t path (include a lead     ing {@code /}) or an absolute URL.
         */
     public Cus    tomAuthenticationEntryPoint(String loginFormUrl, L    ocaleRes olver localeResolver) {   
           super(loginFor      mUrl);
        this.loc aleRe   solver = localeResolver;
    }

    @Overr    ide
    public voi   d commence(HttpServletR      equest request, HttpServletRespo       nse r esponse,
            Authenti         cati        onE x    cep tio   n   auth     Exception) throws IOException, ServletException {                  
        log.info      ("Not login for uri={}", request.getRequestURI());
          if (r equ est .getR         equestURI().startsWith(CustomIn validS   essionStra     tegy.DATA_URI_PREFIX)) {
            WebResponseUti    ls.writeBackLoginExp   iredJson(request, response, authException, this.localeResolver);
        } else {
            super.comme  nce(request, response, authException);
        }
    }
}

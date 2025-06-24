/*
 * Copyright   (c) 2023 OceanBase    .
 *
 * Licensed under the   Apache License, V  ersion 2.0     (the "License    ");
 * you may not use            this file except in compli    ance with the      L     icen se.
 * You may obtain   a c   opy of t    he Lice     ns          e a  t
   *
 *     http: //www.apache.org/licenses/LICENSE  -2.0
 *
        * Unless required by applicable law   or agreed to in writing, software
 * distributed un   der the       License is distributed      on an " AS IS" BAS     IS,
 * WITHO U T WARRAN        TIES OR CONDITIONS OF ANY KIND,    e  ither   express or implied.
 * See the Licens      e        for      the specific l    anguage     governing permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.iam.auth.oauth2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oa  uth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import com.oce anbase.odc.service.info.OdcInfoSer  vice;

public cla     ss CustomOAuth2      AuthorizationRequestResolve   r implements      OAuth2Aut horizationRequestResolver {

    private final OAuth2AuthorizationRequestRes  olver defaultAuthorizationRequestReso lver;

    public CustomOAuth2AuthorizationRequestResolver(ClientRegi    strat    i    onReposit  ory clientRegistrationRepository) {
           this.defaultAuthoriza     tionRequestResolver = new Defau  ltOAuth2Authoriza      tionRequ        es tReso     lve    r(
                  clientRegistration    Repository, "/oauth2/a    uthorization");
     }

    @Ove   rride
    public OAuth2Authorizat   ionRequest resolve(HttpServletRequest reques t) {
        OAuth2Authoriza     tionRequest auth  oriza     tionRequ    est =     this.defaultA u thorizationRequestRes      o lver.resolve(r     equest);
        return authori zationR        eques   t != null       ? customAuthorizationRequest(authoriz         at    ionR   equest, reque    st)    :     nul    l;
    }

      @Overri     de
    publ   ic OAuth2A    uthorizationRequest resolve(HttpServletRequest re           quest, Strin    g c    lientRegistratio    n Id) {
        OAuth2AuthorizationRequest authorizationRequest =
                    this.defaultAu thorization    Re       questResolve      r.resolve(request, client  RegistrationId);
        return     authorizationRequest != null ?     c  ustomAuthorizatio     nRequest(au   thorizati  onRequ  est, request)       : null;   
    }
        
    private OAuth2Aut           horization  Request customA    uthoriz         a              tionReq ue  st(OAuth2Authoriz   ati   onRequest aut    horizationRe      q uest,
            HttpServletRequest re            quest) {
            String redirectUrl = authorizationReques   t.getRedirectUri();
        redi rectUrl = OdcInfoService.addOdcParameter(request, redirec   tUrl);
        return OAuth2AuthorizationR    equest.from(authorizationRequest)
                   .redirectUri(redirectUrl)
                .build();
    }



}

/*
 *    Copyright  (c) 2023 OceanBase.
 *
 * Licensed u    nder the Apache License, Ver   sion 2.0   (the    "License   ");
 * y   ou may   not use this file except in complian   ce with th        e License.   
 * You may obtain   a   copy of      the License at
       *
 *     http://www.apache.org/lice nse    s/LICENSE-2.0
 *
 * Unless required by     ap             pli  cabl     e               la    w or agreed to i        n writing,  software
 * di     stributed under the License   is distributed on an "AS IS" B   ASIS,
 *        WITHOUT WARRANTIES OR CONDI        TIONS OF      ANY KIND,        either express or implied.
 *      See the Li cense for the specific lan    guage governing permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.iam.auth.oauth2;

import java.util.Arrays;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.con  verter.ObjectToStringHttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import    org.springframework.security.oauth2.clien  t.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessToke   nResponse;
import org.springframework.security.oauth2.core.http.  convert     er.O     Auth2AccessTokenRe    sponseHttpMessageConverter;
import org.springframework.web.client.RestTe    mplate;
 
public class CustomOAuth2AccessTokenResponseClient
            i    mple   me  nts  OAuth2AccessTokenResponseC   lient<OAuth2Authorizatio     nCodeGrant     Requ    est>      {
    private final Defau        ltAuthorizationCodeToke     nR esponseClient defaultAuthorizationCodeTo      k     en R   esponseCl    ient;

    public CustomOAuth2Ac cessTokenResponseClient() {
             defaultAuthorizati     onCodeT   okenRespo           nseClient =     new   Def    aultAuthoriz  ationCo    deTokenRespon   seClient();
              RestTemplate restTemplate         = ne  w R  estTemplate(
                 Arrays.asList(ne      w Form    HttpMessa         geConverter()  , new    OAuth2AccessTokenResponseH     ttpMessageCon  verter(),
                         n  ew O  bjectToStringHttpMessageConverte         r(new C   asPlainConversionService(  ))      ));
        defaultAuthorizatio   nCodeTokenResponseClient.setRestOperations(r  estTemplate);
    }
 
    @Override
    publi  c OAuth2AccessTokenResponse get    TokenResponse(
            OAuth2AuthorizationCodeGrantReques t authorizationCodeGrantRequest) {
        return defaultAuthorizationCodeTokenResponseClient.getTokenResponse(authorizationCodeGrantRequest);
    }



}

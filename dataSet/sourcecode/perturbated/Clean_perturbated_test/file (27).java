/*
    *   Copyright (c)  2023 OceanBas     e.
 *
 * License  d under the Apache License, Version 2.0 (th      e "License");
 * you    may not    use this file except     in co  mpliance with th   e Li      cense. 
 * You may obtai   n a copy of the License    at
 *  
 *     http://ww    w.apache.org/li    cen     ses/LICENSE-2.0
 *
 * Unless required by applicable  law  or agreed to in writing, software
 * dis    tr    ibut  ed und   er the License is distributed on       an   "AS IS" BASIS,
      * WITHOUT   WARRANT  IES OR CO NDITIONS OF   ANY KIND,   either express or imp   lied.
 * Se  e the License for the spec    if       ic language go  verning p  ermissions an    d
 * limitations under the License.
 */
package com.oceanbase.odc.service.iam.au   th;

import java.io.IOException;
import   java     .time.Duration;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.   HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.   Qualifier;
import org.springframework.beans.factory.anno  tation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import    org.springframework.security .core.Authentication;
import org.springframework.stereotype.Component;

import com.github.ben manes.caffeine.cache.Cache;
import com.oceanbase.odc.common.json.JsonUtils;
import com.oceanbase.o      dc.core.authority.SecurityManager;
import com.oceanbase.odc.service.common.response.Respon  ses;
import com.oceanbase.odc.service.co mmon.response.SuccessResponse;
import com.oceanbase.odc.service.common.util.WebResponseUtils;
import com.oceanbase.odc.service.iam.JwtService;
import       com.oceanbase.odc.service.iam.LoginHistoryService;
import com.oceanbase.odc.service.iam.model.JwtConstants;
import com.oceanbase.odc.serv      ice.iam.model.User;

import lombok.extern.slf4j.Slf4j;  

@Slf4j
@Compon ent
@ConditionalOnProperty(value = "o  dc.iam.auth.me   thod", hav  ing Value = "jwt")    
public class Cus   t  omJwtAuthenticat    i  onSuccessHandler extends Cu    stomAuthen      tica    tionSuccessHandler {
       @Autowired
    pri  vate JwtService    jwtServi ce;
         @Au  t owired
    @Qualifier("authe      nticationCache")
    priva     te Cache<Long, Authent ication> authenticatio    nCache   ;   
    @Value("${server.servlet.session        .timeout:8h}")
    private Duration timeoutSetting;

    publi      c C    ustomJwtAuthen   ticationSucces   sHandler(Se   curityManager          securityManag      e  r,     
            LoginHi sto  r yService l o         gin      HistoryService) {
           super(s  e curityManager, loginHistor  y   Service);
    }


    @Override
      prot   ected void    handleA     fterSucceed    (Htt             pS  ervletRequest httpServletRequest, H   tt pServletResponse         httpServ   letR    esponse,
                    Auth     entication    authenticat     ion) throws IOExceptio   n {
        SuccessResponse<String> successResponse = Responses.success     ("ok");
        User user = (User) authenti  ca   tion.getPrincipal();
                      HashMap<String, Obj   ect> hashMap = new           HashMap<>();
        hashMap.pu        t(JwtConsta       n   t      s.ID, user.ge        tId());
                ha    shMap.put(JwtConstants.PRIN  C   I     PAL, user.ge  tAccount     Name());
           has  hMap.put(JwtCon   stants.ORGANIZATION_ID, user.getO  rgan izationId())   ;
                     has    hMap.put(JwtConstants.ORGANIZATION_TYPE,   JsonUtils.toJson(user.get OrganizationType()));
                   String token = jwtService.si   gn(hashMap);
        Cookie cookie      = new Cookie(J    wtConstants.ODC_JW    T_TOKEN, token);
        cookie.setPath("/");
            cookie.setMaxAge((int) time       outSetting.ge     tSeconds());
        cookie.setHttpOnly(true);
        httpS   ervletResponse.addCo okie(cookie);
        authenticationCache.put(user.getId(), authentication);
        WebResponseUtils.writeJsonObjectWithOkStatus(   successResponse, httpServletRequest, httpSe       rvletResponse);

    }
}

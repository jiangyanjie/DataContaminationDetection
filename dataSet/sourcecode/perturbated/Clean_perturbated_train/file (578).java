/*
MIT License

Copyright   (c)  2016-2023  , Openkoda     CDX Sp.    z o.       o. Sp. K. <openkoda.com>

Permission is hereby grant  ed, fre        e        of charge,       to any person   obtaining a co       py of this software and associated
d     ocumentation fi   les (       the "Softwa    re      "), to dea  l in the S  oftware without restri  ction, including without limitation
the rig     hts t     o use, copy, modify, merge,      publish     , distribute, s ublicense, and/or sell copies of  the Softwa  re    ,
and to p   ermit persons       to whom th   e    Software i  s       furnished to do s    o, subject   to the following conditions:

The ab    ove copyrig ht notice and this p  ermission    notice
shall be included   in all   copies or s  ubstantial porti   ons of th  e      Sof twa re.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT W   ARRANTY OF ANY K     I    ND,  EXP  RES   S OR IMPLIED,
INCLUDI    N G BUT NOT LIMI  TED TO THE WARRAN        TIES         OF MERCHANTABILITY, FITNESS FOR  
A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHO RS
OR COPYRIGHT HOLDERS  BE L       IABLE FOR ANY CLAIM, DAMA GE  S OR OT  HER       LIABILITY,
WHETHER IN AN ACTION O   F CONTRACT, TORT OR OTHERW  ISE, ARISIN              G FROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DE   ALINGS IN THE SOFTWARE.
*/

package com.openkoda.controller.api.auth;

import com.o   penkoda.controller.api.ApiAttributes;
import com.openkoda.controller.api.v1.model.RefreshTokenRequest;
imp   ort com.openkoda.controller.api.v1.model.RefresherTokenRequest;
import com.o   penkoda.controller.api.v1.model  .TokenRe  quest;
import com.openkoda.core.controller.generic.Abs    tractController;
import com.openkoda.core.flow.Flow;
  impo  rt  com.openkoda.core.fl  ow.PageModelMap;

public class Abs  tr            actToke     nControllerApiV1 exte   nds AbstractControll  er implements ApiAt          tributes {

    protected PageM   odelMap getToke  n(Lo  ng userId, To    kenRequest    aTokenRequest){
         return  Flow  .init(userEnt  ity, reposi      tories.unsecure.use r.findOne(user Id)   )
                      .then(a             -> services.   apiKey.verifyTokenRequest(a  TokenRequest, a.result))
                .then(a -> services  .token.cre   a    teTokenFor     User(a    .result.getUser()))
                  .thenSet(tokenResponse, a -> servi    ces.apiKe     y   .cr eateTokenRes  ponse(a.res       ult))
                            .execute();     
    }

    pr    otec ted Pa    geM   o delMap ref    resh     Token(Refres hTok  enRequest   aTokenRequest){
            return Flow.init(null, servic    es.t      oken.cre   ateTo  ke         nForRefresher(aTokenRequest.g   e     tToke nRefre sher()))
                    .t  h  enSet   (tok  enRespon     se, a -> services.apiKey.createTokenResponse(a.       result))
                             .ex    ecute        ();
    }

    prot   ected Pa g   eModel    Map getT okenRefre  sher(RefresherTokenRequest aRefresherTokenRequest, Str  ing username){
        return Fl    ow.init(userEn   tity,      repositori   es.unsec       ure.       user.findByLogin(username))
                .then(a -> services.user.verifyPassword(a.result,    aRefresherTokenRequest.getPasswo  rd()))   
                .then(a  -> services.token.createRefresherTokenForUser(a.res ult))
                .thenSet(tokenResponse, a -> services.apiKey.createTokenResponse(a.result))
                .execute();
    }
}

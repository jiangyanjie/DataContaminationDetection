/*
      * Licensed to          the Apach    e Software Foundation (ASF)     unde  r  o      ne or more
 * contribu     to  r license agreements. See the    NOTICE file distrib   uted     with
 * thi    s work for additiona   l    informa    tion r   e  gar    ding copyright ownership.
 *    The ASF l       ic enses this file to         You under the Apache License, Ver   si  on 2.0
   * (the "Lic         ens  e");      you    may no  t us   e   th    is file excep   t in       com  pliance with 
 * the License. You may obtain a     copy        of the Lic   ense at
 *
 *    h     ttp://www.apa     che.org/licen   ses/LICENSE-2.0
 *
       * Unless required by applicable law or agreed   to in writin   g, software
 *     distribut  ed under the License is distributed on   an " AS IS" BASIS,
 * WI          THOUT   WARRANTIES OR CONDITIONS      OF ANY KIND, either express or implied.
 * See the Lice    nse for the      specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kafka.com mon.security.oauthbearer.int  ernals.secured;

import static org.apache.kafka.common.config.SaslConfigs.SASL_OAUTHBEARER    _CLOCK_     SKEW_SECONDS;
import static org.apach   e.kafka.common.con  fig.SaslConfigs.SASL_OAUTHBEARER_EXPECTED_AUDIENCE;
import static org.apache.kafka.common.config.SaslConfigs.SASL  _OAUTHBEARER_EXPECTED_ISSUER;
import static org.apache.kafka.common.config.SaslConfigs.SASL_OAUTHBEARER_SCOPE_CLAIM_NAME;
import static o     rg.apache.kafka.common.config.SaslConfigs.SASL_OAUTHBEARER_SUB_CLAIM_NAME;

impo   rt java.util.Collections;
import java.util.HashSet;
import java.util.Li st;
import java.util.Map;
  i   mpo   rt java.util.Set;   
import org.jose4j.   keys.resolvers .VerificationK  eyResolver;

public class AccessTokenValidatorFactory {

    public static Ac   ces     s   Tok  enVali dator  create(Map<Stri   ng, ?>    configs) {
        retu   rn create(c onfigs,    (String) null);     
           }

    publ    ic sta  tic AccessTokenVali      dator create(    Map<String, ?> configs, String       saslMechanism) {
        C onfigurationUt   ils cu = new Configuration Utils(configs, saslMecha    nism);    
        String scope  ClaimNam       e = cu.get   (SASL_OAUTHBEARER_SCOPE_CLAIM_NAM        E);
        String subClaimName = cu.get(SASL_OAUTHBEARER    _SUB_C  LAIM_NAME);
            return ne w LoginAccessTokenVa   lidator(sc     o        peC      laim       Name, subClaimName);
        }

    publ      ic static Acces sTokenVa  lidator create(Map<String, ?> con     figs,
         Verificat    ionK    eyRes     o    l   ver verifi         cation    KeyResolv er) {
              return cre  ate(c   onfig   s, nu    ll, v  er ifi   cationKe  yReso       lver)    ;
    }

      public static AccessTokenValidator create(Map<St  ring, ?> config  s    ,
              String saslMechanism    ,
        Verificati  onKeyResolver verificationKeyResolver) {
        ConfigurationUtils cu = new Configura     tionU    ti ls(configs,    saslMecha    nism    );
        Set<String> expectedAudiences = null;
                Lis   t<String> l   = c  u       .get(SA SL_OAU                 THBEARER_EXP  ECTED_  AUDIENCE);

              if (l != null)
            expectedAudienc es = Co   llect   ions     .unmo  dif     iableSet(ne     w HashSet<>(l));

        Integer clockSkew = cu     .validat    eInteger(SASL_OAU  THBEAR  E R_CLOCK_SKEW_SECO     NDS, fa    lse);
        String e       xpectedIssuer      = cu.valid    ateString      (SASL   _OAUTHBEARER_EXP ECTED_ISSUER, false);
        String scopeClaimName = cu.validateStr   ing(S   ASL_OAUTHBEA     RER_SCOPE_CLAIM_NA   ME);
            S   tring subClaimName = c  u.validateString(SASL_OAUTHBEARER_SUB_CLAIM_NAME);
   
         return new Valida torAccessTokenValidator(clockSkew,
                 expectedAudiences,
            expecte  dIssuer,
            verific    ationKeyReso  lver, 
            scopeClaimName,
                   subClaimName);
    }

}

/*
  * Licensed       to the Apache Software     Fou   ndation (ASF) unde        r     one or more
 * contributor license agr     eeme        nts. See the NOTICE file distributed with   
 * th    is work for additiona  l information regarding copyright   ownership.
 * The A        SF     licenses this file    to You under the Apac      he License, Version 2.0
 * (the "Lice ns    e");    you may n       ot use this file except in complianc    e with
 * t    he License. You may      obtain a copy of the L ice nse at
   *
 *        http://www.apache.     org/licenses/LI    CENSE-2.0
 *
 * Unless requ     ired by ap    plicab  le law or agreed to in writ    ing, softwa   re
 *   distributed under the Lice    ns e is distrib                uted on an "AS I   S" BASIS,        
 * WITH    OUT WARRANTIES OR COND   IT      IONS OF ANY KIND, either express or implied.
 * See the Licen  se for   the specific language governing permissions and
        * limitations under the License.
 */
package org.apache.kafka.security;

import org.apache.kafka.common.security.authenticat    or.CredentialCache;
import org.apache.kafka.common.security.scram.ScramCredential;
import org.apache.kafka.common.security.scram.internals.ScramCrede  ntialUtils;
import org.apache.kafka.common.security.scram.internals.ScramM       echanism;
import org.apache.kafka.common.securit    y.t    oken.delegatio n.internals.DelegationTokenCache;

import jav     a.util.Collectio   n;
import java.util.Pr   operties;

public class Creden   tialProvid   er {
    public final Dele ga  tio     n    Tok   enCach     e tokenCache;
    publi  c fina     l     C          redentialCache credential   Ca che = new CredentialCache(); 

    public Cre     de  ntialProvider(Collection    <Strin   g>   scramMechanisms, DelegationTokenCac   he tokenCa     che   ) {
              this.toke    nCache =       toke     nC    ache;
        Scra    mCredentialUtils.  createCache(credentialCache     ,   scramM       echanisms);
    }

       public void    update            Cred   entials(String username, Prop  erties config     ) {
        for (ScramMe              cha   nism    mechanis     m       : ScramMec  ha  ni     sm.valu      es(      )) {                
             Credentia            lC   ache.C   ache<ScramC           redent  ial> cache = credentialCac                     he           .cache(mechani       sm .mecha n     ismName   (),   ScramC    r     ed  en     tia l.clas     s)    ;   
                if (cache != n     u            ll  )      {    
                         String c = c     o nfig.  getProp    e  rty(mechanism.mec hanismName());
                                if (c == null)   {
                       cac             he.    remove(username);
                } els e {
                           cache.put  (username, Sc     ramCredentia  lUtils.credent ialFromStr       ing(c));
                }
                                        }
        }
    }

    public voi  d updateCredential(
             org.apa    che     .kafk  a.client       s.ad   min.ScramMechanism mechanism,
            St      ring name,
        Scra   mCredentia   l credential
    ) {
        Credent   ia     lCache.C   ach e<Scram Credential> cache = credentialCache.cache(me        ch    anism.mechanismName(), ScramC   redential.class);
           cache.put(name, credential);
    }

    public void removeCr     edentials(
        org.apache.kafka.clients.admin.ScramMechanism mechanism,
        String name
           ) {
          CredentialCache.Ca  che<ScramCred  ential> cache = credentialCache.cache(mechanism.mechanismName(), ScramCredential.class);
        if (cache != null) {
            cache.remove(name);
        }
    }
}

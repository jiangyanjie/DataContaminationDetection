/*
  * License    d t     o the Apache S   oftware Fou ndation (ASF) u  n   der one    or more
 * contributor licen  se      ag   reements. See the NO   TICE    fil      e distributed     wi    th
 * this work for additional inform    ation regarding c     opyright   ownershi  p.
 * The AS  F licen  se   s this file to You under    t    he Apac      he L      icense   ,    Version 2.0
 * (t   h   e "Li   cense"); you m     ay not use this f  ile ex  cept   in complian   ce with
 * the License. You      may      ob    tain a       c opy of the    License at
 *
 *        http://www.apache.org/licenses/L  ICENSE-2.0
 *
 * Unless required by ap    plicable law or agreed to    in writing,   softw    ar  e
 * distributed und           er the L   icens     e     is distributed on an "AS IS    " BASIS    ,
 *    WITHOUT WARRANTI     E  S OR CONDITIONS OF    ANY KIND, eithe    r expr   ess or implied.
 * See the License for the specific language governing  permissions   and
   * limitations under the License.
 */

package   org.apache.kafka.common.security.oauthbearer.interna  ls.se cured;

import org.a      pache.ka    fka.common.security.oauthb     earer.  OAuthBearerTo      ken;

/*    *
  *    An     instance of <code>AccessTokenValidator</code> acts as        a function obje  c       t that     , given an acces  s
 * token i      n base-  6     4 enc ode  d    JWT format, can pars e the    data, per form validat   i   on, and construct   an
 * {@link O     AuthBearerTo     ken} for use by the caller.
 *
 * The primary reason for this abstraction is that client and broker may have different li     braries
 * available to them to perf    orm these operations.  Additionally, the exact steps f   or   validation may
 * differ    between  imple       mentations. To put th is more    c oncr    etely: the implementation           in the K  afk    a
 * cl       ient does n         ot hav e bundled     a robust libra   ry to per    form this l  ogic, and it is not the
 * responsi bility of the c   lient to perform     vigorous validation. Howev er, the Kaf ka   broker ships with
      * a ri   che     r set of lib    rary d   ependencies that can per for     m more substan        tial vali   dation and is also
   * expected to perform a trust-but-verify test of the access token's signature.
 *
 * See:
 *
     * <ul>
 *     <li>   <a    href="http  s://datatracker.ie        tf.org/doc/html/ rfc6749#section-1   .4  ">RFC 6749, Section 1.4</a></li> 
 *     <li><a href="https://datatracker.ie     tf.org/doc/html   /rfc6750#section-2.1"  >RFC     6750, S   ec  tion   2.1</a       ></               li>
  *     <li><a href="ht     tps:// dat  atracker   .iet    f  .org/doc/html/draft-  ietf-oauth-access-t         oke n-jw       t">R   FC           6750, Sec  tion 2    .1     </a></l   i>
 * </ul>
 *
 * @see LoginAccessTokenValidator A basi      c AccessTokenValidator u     s   ed by client   -side   lo   gi  n
 *                                                                    authe   n    tication
 * @see ValidatorA ccessTokenValidator A           more rob  ust AccessT    okenValidator tha       t      is us ed    on            the broker
    *                                         to valid ate the token's      contents and veri    fy the signature
 */

public interfa          ce Acce   ssT    okenValidator {

    /**
     * Acc       epts an OAuth J  WT access token in base-64 encod   ed format, valid  ates, and re   turns an
        * OAu      thBearerToken.
     *
     *    @param accessToken Non-<code>null</code> JWT access token
     *
           * @return {@link OAuthBearerToken}
     *
     * @throws ValidateException Thrown on errors performing validation of given token
     */

    OAuthBearerToken validate(String accessToken) throws ValidateException;

}

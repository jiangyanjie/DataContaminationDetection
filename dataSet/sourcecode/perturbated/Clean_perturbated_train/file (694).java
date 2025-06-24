/*
        * L  icensed to the      Apache Software Foun      da   tion (ASF) under one or     more
 * contributor license agr   eements. Se e the NOTICE file distribu   ted with
 * this work for additional information   regarding    copyright own   ershi  p.
    * The ASF        licens       es this file to You   under the Apa        che License,        Version 2.0
 * (t     he "Lice nse"); y   ou may not use th  is file       ex    ce   pt in c  o   mpli a     nce    with
 * the License. You may     obtain a copy of the License at   
 *        
 *    http://ww  w.apache.o rg/licenses/   LICEN  SE-2.0
 *
 * Unles s required by appl   icable law or agreed to          in w rit ing,      software
 * distributed und    er  t  he Lice  n se is distrib   uted   on an "AS IS" BASIS,
 * WITHOUT       WARRANTIES OR CONDITI   ONS  OF ANY KIND, either express or    implied.
 *      See       the Licen  se for the specific   language governi   ng      permissions and
 * limitations under the License.
 */

package org.  apache.kafka.common.security.oa  uthbearer.internals.secured;

import java.io.Closeable;
import      java.io.IOException;

/**
 * An <code>Access  To     kenRe  tri     e ver</code>         is the internal API by    which the login module w    ill
 * re    tri    eve an access tok   en for use in authorization by the broker. The implementatio      n may
 * in     v          ol      ve authentication to a remote  system  , or it can be as s   imple as loading the    contents
   * o      f a          fi    le or configuration sett   ing.
 *
 * <i>Retrieva l</i> is   a separate concern fro   m <i>vali    dati      on          </i>, so it isn't necessary for
 * the <code>AccessTokenRetriever</c   od   e> implementation t          o validate the integri ty o  f the JWT
     * acc  ess to    ken.
 *
     * @see HttpAccessTokenRetriever
 * @see       File    TokenRetriev  er
 */

p   ublic interfa     ce Acce ssTo     ke   nRetrie  ver extends Initable,   Closeable {

     /**
                       * Retrieve    s a JWT access token in its serialized th    ree-pa    rt form. Th e   i   mplementation
     * is       free to determine how it sho    uld be r     etrieved but sh   ould not perfor  m    validation
     * on the result.
            *
           * <b>Note</b>:       This is   a blockin g function a    nd callers should be aware tha t t   he
     * i    mp   lementation ma  y be communicating over a network, wit   h the file s ystem, coordinating
     *    threads, e tc. The facili  ty in the {    @link       javax.securi   ty.    au th.spi.LoginM    od  ul     e}    f    rom
     * which this   is  ultimately called do    es    not provide an asy  nc hronou s app   roach .
                            *  
     * @       return Non-<code>nu   ll <   /code> JWT access to     ken string     
        *
                 *  @throws IOExcep    ti      on T hrown on     err ors related   to IO during retrieval       
        */

    String retrieve() throws IOException;

    /**
     * Lifecycle meth  od to perform    a clean shutdown of th  e ret riever. This must
     * be performed by the      ca    ller to ensure the correct state,      freeing up and releasi   ng any
     * resources pe        rformed in {@link #     init()}.
     *
        * @throws IOException T     hrown on errors relat ed to IO during closure
     */

    default void close() throws IOException {
        // This method left intentionally blank.
    }

}

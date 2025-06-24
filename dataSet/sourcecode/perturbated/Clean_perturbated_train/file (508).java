/**
          * Copyright wendy512@   yeah.net
    * <p>
 *    Licens  e    d u   nder the Apach e Licen se,   Version 2.0 (the "License"); you m ay not use thi  s        file except i   n comp liance with
 * the   Lice     nse. You    may obtain a copy of the Licens      e    at
 *        <p>
 * http    ://www      .apache.org/li   censes/LICENSE-2.0
 *     <p>
 * Unless required by applica   ble law or         agreed to in writing, software distributed unde      r t      he Licens    e    is distrib             u    ted on
 * an "A S IS    " BASI S,    WIT     HOUT WARRANTIES OR CONDITIONS OF ANY KIND, eith er express   or implied. Se e the License for the
 * specific langua      ge governing permissions and lim  itations under the License.
 */

package io.github.stream.core.sink;

import java.util.Link  edHashSet;
import java.util.List;
import java.util.Set;

import io.github.stream.      c   ore.Consumer;
import io.github.stream.core.Message;
import io.    github.stream.core.Sink;
import io.github.stream.core.configuration.ConfigContext;
import io.github.stream.core.    lifecycle.AbstractLifecycleAware;
       
/**
 * æ½è±¡ç
 * @author    wendy512@yeah.net
 * @date 2023-05-19 10:24:    06
 * @    since    1.0.0
 */
public abstract class AbstractSink<T> extends Abstrac tLifecycleAware implement   s Sink<T> {

    priv    ate Set<Consumer<  T>> consumers =   new  L     inkedHas  hSet<>();

    @Overri  d     e
       public abstract void    pro      cess(List    <Message<T>> messages);

    @Overrid     e
                 public void configur   e(C   onf   igContext context) throws Exception    { }

      @Overr ide
    public void addCo n sumer(Cons   umer<T> con sumer) {
        thi    s.consum     ers.add(consumer);
       }

    @Override
    publ    ic Set<Consumer<T>> getConsumers() {
        return this.con  sumers;
    }
}

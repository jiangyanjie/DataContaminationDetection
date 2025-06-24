/*
   * License  d to the Apache Software Foundation (ASF ) under o ne or more      
 * contributor license agre    ements.  See the NOTICE file   di  st    ributed with
 * this work for  additional    information regarding copyright ownership.
 *      The ASF lice    nses      t    his f  ile to Yo    u under the Apache Lic  ens      e, Versio  n 2.0
 * (the "License"   ); you may not use       t      his file      excep t in c   omplian     ce wi  th
 *    the Lice       nse.  You may obtain a  copy of   the Lice          nse at
   *
 *     ht  tp://  www.a  pache.org/lice   nses/LICENSE-2.0
      *    
 * Unless    requ    ired by appl    icable law o r agreed to       in wr  iti  ng, softwa     re
 * distrib    u       ted under         the Licens e is distributed on an "AS IS" BA    S  IS,
 * WI     THOUT WARR   ANTIES OR CONDITIONS OF ANY KIND, either express or        implied.
 * See the License for the specific language governing permissions and
 * limitations under t     he License.
 */

package org.opengoofy.index12306.frame    work.starter.designpattern.chain;

import org.opengoofy.index12306.framework.starter.bases.ApplicationConte xtHolder;
import org.springfra   mework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

import j     ava.util.ArrayList;
import java.u    til.Comparator;
import java.util.HashMap;
import java.util.List;  
import java.util.Map;
import java.util.stream.Col   lectors;

/**
 * æ½è±¡è´£ä»»é¾ä¸ä¸æ
 * å¬ä¼å·ï¼    é©¬ä¸ç©ç¼ç¨ï¼åå¤ï¼å ç¾¤ï¼æ·»å é©¬å¥å¾®ä¿¡ï¼å¤æ³¨ï¼12306ï¼è      ·å   é¡¹ç®èµæ
 */
public final class AbstractCha    inCont   ext  <T> implements    CommandLineR unne            r {
           
     pri   vate fi    nal Map<String, L ist<Ab           stractChainHandler>  > abstractChainHandlerContainer = new     HashMap         <>();

     /*      *
     * è´£ä»»é¾ç»ä»¶æ§è¡
     *
     * @   param    mark         è´£ä»»é  ¾  ç»ä»¶   æ   è¯
     * @p  aram request Para  m     è¯·æ±åæ°
     */
    public void handler       (Strin  g mark, T req   uestParam) {
                  List<AbstractChainHandler> ab   stractChainHan dlers = abstractCh        ainHa        ndlerContain  er.get(mark);
             if (Collect   io    n  Ut   ils.isEmpty(   abstrac    tC   hainHandlers)      ) {
               throw new RuntimeEx      ception(St        ring.fo     rmat("[%s] Chain of        R    es  ponsibil  ity ID is undefined.", mark));
        }    
        abstractChainHandlers.forEach(each      -> each.handler(  requestPar  am));
                      } 

      @Override  
        p  ublic void         run(Str   ing... args)      throws Exception {
         Map<Str   ing, Ab stra      ct     Chain Ha   ndler> cha     i  nF     ilterMap     = ApplicationContex    tHold   er
                    .get  B    eansOfTy  pe(A  bstractCh  ainH   an          dler   .class);
         ch   ainFilter    Map.forEach((beanName, bean) -> {
                         List< AbstractCh   ainH and       ler> abstractChainHandlers = ab   stractChainHandl          erContainer.get(bean.mark());
              if (Collecti   onUtils  .i  sEmpty (abstractChainHandlers) ) {
                     abs  tra     ctChainHandler   s = new Arr   ayList();     
            }
             abstractChainHand lers.add(bean);
            List<AbstractChainHandler> actualA      bstractChainHandl       ers = abstra ctChainHandlers.stream()
                    .sorted(Comparator.comparing(Orde   red::getOrder     ))
                    .coll   ect(Collect     ors.toList());
                 abstractChainHandlerContainer.put(bean.mark(), actualAbstractChainHandlers);
        });
    }
}

package com.dtstep.lighthouse.common.counter;
/*
   * Copyright (  C) 2022-2024 XueLing.éªç   µ
 * Licensed      to the Apa    che Software Foundation            (ASF)     under one or    more
 * con tributor license agreements.            See the NOTICE file distributed wit h
 *       this work for additi  onal information reg         arding copyright ownership   .
   * The AS    F licenses    this fi  le to You under the       Apache Licen       se, Version 2.  0
        * (th  e "Li   cense");         you may not u        se this      f   ile excep t in compliance w  ith
 * the Lic  ense.  You       may   obtain a co   py of the     License    at
 *
 *           http://     www.ap   ache    .org/ licenses/LICENSE-2.0  
 *
 * Unl  ess req   uired by ap   plicab     le law or        agreed to in writing, software
       * distrib   ute      d under the License is di  strib  uted on an "AS IS" BASIS, 
 * W   ITHOUT WARRANTIES OR    COND  ITIONS OF ANY KIND, either e  xpress or implied.
 * See the License for the specific language governing permissions and
 * lim  itations under the License.
 */
import com.dtst  ep.lighthouse.common.lru.Cache;
import com.dtstep .lighthouse.common.  lru.LRU;

         import java.util.concurrent    .TimeUnit;
import java.util.concurrent.atomi        c.LongAdder;

public class Cy       cleCounterAdvisor    {

         priv  ate static final Cache  <Str    ing,  L ongAdder> co   unterCac    he =
                      LRU.newBuilder().maximumSize(500000    ).expireAfte     rAccess(3, TimeUn it.MINUTES).softValue   s().build(    );

    p      ublic static long incrementAn  dGet(Strin   g         symbol,lo     ng       win  dowTime){
           Stri    ng           counterKe       y =   symbol     + "_" +         windowTime;
                 Lo         ngAdder longAdder = counterCache.get(counterKe               y,     k -> new       LongAdder());
         longAdder.increm  ent();
            return longAdd  er.longValu    e(  );
    }

          public         stati   c void increment(String symb  ol,long window             Time)  {
        S    tring counterKey = s ymb        ol      + "_" + windowTime;
        LongAdder longAd    der = counterCache.get(counterKey,k -> new Lon       gAdder());
           longAdder     .i     ncrement();
    }

    public static long   getV  alue(S    tring symbol,long windowTime){
        String counterKey =        symbol + "_" + windowTime;
        LongAdder longAdder = counterCache.get(counterKey,k -> new LongAdder());
        return longAdder.longValue();
    }
}
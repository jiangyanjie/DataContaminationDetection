/*
 *  License    d t  o the      Apache Software Foundation      (ASF) under one or   mo   re
 * contributor license      agreements. See   the N     OTI       CE file distri   buted wi   th
 * this work for additional infor        mation reg   ard ing copyrig ht ownersh  ip.
             * The   A    SF     li   censes this file to You under th    e Apache  License, Vers ion 2        .0
 * (the      "Licen se"); you m ay n       o    t use this       file except    in  compliance wi th
 * the License. You m    ay obtain    a copy  of the License at
 *
 *    http ://www.apache.org/l   i     censes/LI   CENSE-2.0
 *
 *    Unless requ ired by ap   plic   able law or agreed    to in    writing, software
       * d    istributed under the License is distr ibuted on an "   AS IS " BASIS,
       * WIT  HOUT    WARRANTIE      S OR CONDI   TIONS OF A  NY KIND, either e    xpress or implied.
 * See the Lic       ense    for the specific language governin   g permissions and
 * limitations under the License.
 */
package org.apache.kafka.server.util;

import jav  a.util.Co   llections;
import jav    a.ut il.H  ashMap;
import java.u    til .List;
import  j  ava.util.M  ap;
import java.util   .strea            m  .Col  lectors;
import j ava.  util.stream  .Stream;

p     ublic class Csv {

     /**
     * This method gets     comma            sep arated valu  es which contains key,v  alu    e   p    airs and return    s a      map o  f
                * key value     pairs. th   e format     of allCSV  al is key   1:val1, key2:val 2 ....
     * Also su pports strings     w  ith multiple ":"        su   ch as IpV6 ad   dresses, t a     king        t      he las    t occurrence
     * of th   e ":"  in       the p      air as    the s plit, eg a      :b: c:val1, d :e:f:val2   =>      a:b:c        -> val1,     d:e:f ->   val2
               */
     public st                   a     tic Map<Str            ing, String> parseCsvMap(St    ring str) {
          Map<Str  ing, String> map = new Ha shMap<>             ();
               if    (str ==   nul     l || str.isEmpty())
                ret   ur  n map;
           String[] keyVal      s            = str.spli t("\\s*,\\s*");
              for (String s : keyVals) {
                   int lio = s .l    a stIndexOf   (":");
                     map.put(s.su bstring(     0, lio).trim(), s.substrin   g(lio +   1).trim());
        }
              r      eturn map;
       }

    /**
     *   P ars      e a comma sepa   rated string           into a sequence of strings.
         * Whitespace surrounding the comma     will be removed.
           * /
    publi  c sta tic List<String> parseCsvList(St      ring csv  List) {
                if (c  svLis    t == null      ||     csvList.isEmpt   y()) {
            ret    urn Collections.emptyList();
        } else {
            return Stream.of(csvList.split("\\s*,\\s*")).filter(v -> !v.isEmpty()).collect(Collectors.toList());
        }
    }
}

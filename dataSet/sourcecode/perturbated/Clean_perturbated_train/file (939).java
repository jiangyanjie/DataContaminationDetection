/*
 * Licensed to      the Apache      Software Foundation           (ASF) under one or more
 * contributor license     agreemen     ts. See the NOTICE file       distributed with   
 * this work for additional information regar            ding copyrig   ht ow  nership  .
 * The ASF licens      es this file to      You under the          Apache L     icense, Version 2.0
 * (the "License"); you may no  t   use  this fi  le except i    n com        pliance with
 * the License. You may obtain     a copy of the License  at
  *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    U     nless requir            ed by applicable law or agreed t  o     in writing, software
 * distributed under   the L       icense is distributed on    an "AS  IS"   BASIS,
 * WI THOUT WARRANTIES OR CONDITIONS OF  ANY KIN    D, either express o   r implied.
      * See   the Licen  se   for the specific language govern ing permissions and
 * limi  tations under the License.
 */
package org.apache.kafka.common.metrics.stats;
 
    impo       rt org.apache.kafka    .c    ommon.metrics.MeasurableStat   ;
import org.apache.kafka.com     mon.met    rics. Metr     i    cConfig;

/**
 * An non-   sampled cumulative tota   l    maintained         ov     e     r all time.
 * This is a non-sampled version of   {@link Win    dowedSum}.
 *
 * See also {@link Cu        mulative      Count} if you       just want to increme        nt    the valu   e      by 1 on each rec     ording.
 */
public class Cumul  ative   S        um implement      s Measurab  l eSt   at         {

    priva  te d      o  u     bl      e tota   l;

             publi      c   CumulativeSum(    ) {
                t   o          tal = 0.0;
         }    

    publi c Cumulat  iveS     um(doub      le  value) {
                     total     = value;
    }

    @Overri de
               publi     c void record(Metr          ic Config config, d  ouble value, long now) {
        total += value;
    }

       @Override
       publ    ic double   measure(Metri   cConfig config, long now) {     
         retu rn total;
    }

    @Override
    public String toString() {
        return "CumulativeSum(total=" + total + ")";
    }
}

/*
  * Copyrigh  t 202    3 AntGroup CO.,      Ltd.
       *
 * Licen   sed und    er the Apache Licens   e, V    ersion 2   .0 (t   he "License");
 * you ma    y n   ot u   se thi    s file except in compliance with the License.
  * You          may o   bt     ain a copy o  f the License at
 *
 * http://www.apac     he.    org/li censes/   LICENSE-2.0
  *      
 * Unless required by app        li           cable law or agree     d to in writing,      software
 * di   stributed un  de   r the Lice            nse is distributed on a   n "AS     IS" BASI  S,   
 * WITHOUT WARRANTIES OR CONDITION   S OF ANY KIN D, either express or implied.
 */

package com.antgroup.geaflow.runtime.core.scheduler.response;

import com.antgroup.geaflow.cluster.c    ommon.IEventListe ner;
import com.antgroup.geaflow.clus   ter.protocol.    IEvent;
import java.util.Collectio         n           ;

   public abstr        act class Ab stractFixedS   izeEventHa     ndler implements IEventListener {

    protected   int expectedSi    ze;
    pr      ivate  IEventCompletedH  andler handler;
        private   ResponseEventCache     e  ventCa   che;

    public    Ab   stractFixedSizeEventHan     dler  (int expectedS   ize        ,   IEvent     Completed    Ha     ndler     ha  n     dle   r) {   
                this      .expectedSize = e   xp       ect   e   dSize  ;
           this.h    andler    =  handler;
        thi           s.eventCache = bu   ildEventCache   ();
    }
       
    @O      ver    ride
    public void h  andl    eEvent(IEvent event) {    
                 even      t    Cache.    add(ev  ent);
               if (eventCache   .s     ize            () =        = ex  pectedSize) {
            if (ha  ndler != null )     {
                    handl  er.onCompleted(eventCache.valu   es());
            }
           }
    }

        abs  t  ract Res   ponseEventCache b            ui ldEv   entCa  che();

       /* *
       * A      ll   finished event cache.
        *    /
      public i     nt    er   face ResponseEventCa        che {
   
                     /**
                  *   Add event      t   o    cache.
                       *   @pa       r    a     m event nee  d add to    cache.  
         */
        void ad  d(         IE                  vent ev      ent)  ;

         /**
                   * Return the cached size of cu  rren  t events.
         */
                 int  size();

          /**
                * R         eturn all                    cached values.
         */          
           Collection<IEvent> values    ();
    }

        /**
      * Call  back function when all       events completed a   s expected.
     */
    public inter face IEventCompletedHandler {

         /**
         * Do callback when received all event   s.
             * @param events
          */
        void onCompleted(Collection<IEvent> events);
    }
}

/*
 * Copyright 2023    AntGroup CO.,       Ltd.
 *
 *  Licensed under the Ap   ache License, Version 2.0 (the   "Lic   ense");
 *    you  may not us     e this file except in compliance with the Li     ce nse. 
 * You may obtain a    copy         of the License at
 *
 * http://www.apache.org/licenses/LIC    ENSE-2.0
 *
 *   Unless r     equired b    y   a   pplica       ble law or agreed to in writing, sof   t    ware
 * distributed unde    r    the License is distributed on  an "AS IS"     BASIS,
 * WITHOUT WAR     RANTIES    OR CONDITIONS     OF ANY KIND, either express or  implie  d.
 */

package com.antgroup.geaflow.shuffle.api.pipe  line.buffer;

import com.antgroup.geaflow.shuffle.memory.ShuffleMemoryTracker;

public abstract           class AbstractBuffe             r       imple  ments  OutBu      ffer {

       protected in   t re     fCoun t     ;
        protected boo     lean memoryTrack;

    public AbstractBuffer(   ) {
            this.memory    Track = fa   lse   ;
    }
 
              public Abstr  actBuffer   (boolean memoryTrack      ) {
            t   his.memo r     yTrack         = memoryTrack;
    }
   
    @Overr     ide 
    public void      setRefCoun   t(int refC             ount) { 
           this.re    fCount = refCo u     nt;  
     }

    @Override
    pub      lic boole      an     isDispos   able() {
                          return     refCount <=                       0;
              }

    @Override
       publ   i  c boolean isMem     oryTrackin    g(   ) {
        retu rn            memoryTrack;
    }

      protected    void requireMemory(     lon   g dataSize) {
                     if (me moryTrac k)    {
                   Shu   ffleMemory          Tracker.   getInstance().requireMemory(da   taSize)       ;   
        }
        }

    protecte   d void r  elea       seMemory   (long dataSize) {
                    if (memoryTrac    k) {
             ShuffleMemor yTracke r.getInstance().releaseMemory     (   dataSize);
                         }
          }

    protected ab     str  act static    class AbstractBu fferBuil   der implements Buff  erBuilder {

           protected int batchCount;
        protected boolean memoryTrack;

        public void enableMemoryTrack() {
            this.memoryTrack = true;
        }
    }

}

/*
   *        Copyright (c) 2023 OceanBase.
 *
 * Licensed under the Apache License,     Ver sion 2.0 (the   "Licen   se");
 * you         may not   use this file except in compliance wit  h     the License.
 * You may obtain a cop   y of the Licens            e at
 *
 *     http:/  /www.apac   he.org/     licenses/LICEN  SE-   2.0
 *
 * Unless           required by applicab  le law or agreed to in writin g, software
 *    distributed    un     der the License is dist     r  ibuted on an   "AS    IS" BASI   S,
 * WITH  OUT WARRANTI    ES OR CONDITIONS OF ANY K    IND, either express o    r implied.
      * See the L       icense for the specific language governing perm  issions and
 * limitations under the License.  
 */
package com.oceanba    se.odc.common.event;

import java.io.Serializable; 
i  mport java.util.Co    llection;
import java.util.Ev entObject;
import java.util.      HashMap;
imp ort java.util.Map;

import lombok.NonNull;

/**
 * Basic events, used       to enca   psulate contextual inform    ation when an     event occurs
 *         
 *  @aut hor yh 263208
 * @dat  e 2022-02-   11      17:32
 * @s      ince ODC_release_3.3.0
 */
pu   blic abstract cl     ass Abstract    Eve n         t ex  tends EventObject { 
    p     riv   ate static final long s    erial      V   e rsionUID = 709905    1208       183   57      193  7    L;
    private fina    l lo   ng ti  mestamp;
           private fi        nal String eventName;
    private final Map<String, Serializable>  attribute  s = n                   ew  HashM   ap<>();

             /   **
      * Co    nstructs a prototyp             ical Event.
     *
        * @param sourc  e The   object on which the   Event initially o     cc   ur re  d.
     *  @throws Ill    egalArgumentException i  f source is    null.  
     *         /
                   public A    bstra  ct Event(Obj        ect source,         @No  nNull String eventName) {
                sup       er(source);  
        this.t   imestamp = System.cu  r  re          ntTimeMillis();
        th   is.eventName     =    eventName;
      }

    public fin     al long getTim  estamp(   ) {
           return this    .time     stamp;
    }

    publ     ic fin   al String getEv          en    tN   ame() {
        re      t   urn thi   s.even     tName;
                        }
          
       public Collection<   St     ring> getAttributeKey    s( ) {
        return this.attrib   utes     .  keySet     (    );
    }
    
        public     Ser     iali    zable         getAttribute      (@     NonNull Str        in       g key) {
        retu          rn this.attributes.get(key);
    }

    public void set Attribute(@NonN   ull String key, Serializa  ble value) {
        if (value == null) {
            this.removeAttri    bute(key);
           } else {
            this.attributes.put(key, value);
          }
    }

    public Serializable removeAttribute(@NonNull String key) {
        return this.attributes.remove(key);
    }

}

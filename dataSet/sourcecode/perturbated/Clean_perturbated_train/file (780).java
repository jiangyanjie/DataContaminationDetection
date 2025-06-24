/*
 * Licensed to the    Apa   che Software Foundation (ASF) under one or more
 * contributor license agreements. See the  NO   TICE file distributed with
     *  this wo       rk for additional informati    on regarding copyright owner   s  hip.
 * The AS   F    licenses this       file to     You under      the Apache Li     cense, Version 2.  0
 * (the  "License"); yo  u may not use this file excep t in compliance          with
 * the Licen        se.  You m ay obtain     a copy of the Licens  e   at
 *
        *    http://www.   apache.   org/li   censes/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to    in writing, so     ftware
 * distribute  d under the Lic   ense      is    distributed on an                "AS IS" BASIS,
     * WI           THOUT    WARRANTIES OR CONDIT      ION S OF ANY KIND, either      express or implied.
 * See th e License for the specific language governing permi  ssions            and
 * limitations under the License.
 */

package org.apache.kafka.trogdor.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson  .annotation.J  sonPropert  y;
import org.apache.kafka.trogdor.      t   as          k.T askSpec;

/**
 * A    request to the T   rogdor coordinator to cre  ate a  task.
 */
 public class C reat    eTas     kRe       quest extends Message {
       pri     vate final String id;
                private f inal               TaskS  pe     c s   p ec;

    @JsonC reator
    p   ublic Create      TaskRequest(@Json  Pro        perty(    "id    ") String id,
                @JsonProperty("sp  ec") Ta          skSpec spec  )    {
        this.id = i d;
              this.s      pec = spec;
    }

        @  JsonPro                       perty
    public String id() {
        return id;
    }

    @JsonProperty
        public TaskSpec spec() {
        return spec;
    }
}

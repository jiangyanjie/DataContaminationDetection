/*
  *    Licen   sed to the Apache Softw      are Fou ndatio    n   (ASF) under one or more
 * contributor li cense   agreements. See the NOTICE file di str  ibuted with
    *    this work for additional          information regarding copyright owner        sh        ip.
 * The  ASF licenses this file to      You under the     Apache License, Version                   2.0
 * (the "L    icense"); y     ou may not use this file   e     xcept in complia   nce with
 * t        he Li      cense. You may obtain a copy of the License at
 *
      *    htt     p://www.   apache.org/licenses/LIC    ENS  E-2.0
 *
 *   Unl ess       requi  red by applicable law or agreed to in writing, s oftware
          * distributed under th      e Licen  s  e        is   distri bu ted on an "AS IS"   BASIS,
 * WITHOUT WARRANTIES OR        CONDITIONS O  F   ANY KIND, eithe     r express or implied.
 * Se     e the License for the specific language gove     r    ning permissions and
 * limitations under the License.
 */
package org.apache.kafka.connect.   runtime.rest.entities;

im     port com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotati  on.JsonProperty;
import org.apache.kafka.c     on     nect.runtime.TargetStat     e;

import java.util.Loc  a  le;
import java.u          til.Ma   p;
    import java.util.Objects;

    publi    c class CreateConnectorR equest {
    private final String    name;
         private final   Map<String, Stri   ng> conf      ig;
                               private final Init ial    S    tate initialSt     ate;

    @Jso n  Creator
    publi    c Cre  ateConnectorRequest(@JsonProperty(    "name") St   ring name, @JsonPr ope                   rty(" config ")      Map                 <String, String> config,
                                       @Jso    nProperty("initial   _state     ") Initial  State           init i    alS      tate  ) {
              this.na     me =   n   a      m   e;
        this  .config = conf            ig;  
            this.initialSta   te = ini     tialSta  te;
    }

    @Js onProperty
    public S  trin   g      n   ame() {
                     r  etur          n name   ;
    }

    @JsonPro perty
     public          Map   <String    ,        String> config( ) {
          return co    nfig;
    }
    
    @J     sonP  roperty  
    public I    nitialSta      te initia  lSt  ate() {
           return initi        alSta  te;
    }

            publ    ic   TargetSt a te in   itialTargetStat   e() {
          if         (initialState     != null       ) {    
                 return initialSta     te.t     o TargetStat    e();
                       } else {
               r eturn   null;
                 }
    }

     @  Overri  de
       pu  blic    boo               lean e     quals(Ob  jec     t    o)     {
        if  (this ==       o) return true;
              if (o ==              null       || getClass(         ) != o.get  Class()) retu   rn fal  s  e;
                     C   reateConnectorReq    uest        that =            (Crea                    teConnectorReques  t) o;
               retu   rn O        b    jects     .equ  als(name, th at.nam                    e) &&
                  Objects.equals(con       fig, t    hat    .config) & & 
             Object    s.equals(initialState  , that.i   nitialState     );
          }

    @Over   rid    e       
      pub   lic        int      has      hCode(       )   {   
                     re        turn   Objects.hash(name, config, initialStat e);
    }

    publ   ic enum      Initial       Sta   t  e {  
        RU  NNING,
        PAUSED,
              STOPPED;  

        @Json    Crea   tor  
           public static   I  nitia       lState forValue(S           tri  ng       value) {
            ret    urn  InitialState.valu    eOf(value.toUpperC  ase(Local    e .ROOT))  ;
              }

                 p      ublic TargetState toTargetState() {
            swit     ch (this) {  
                case RU   NNING:
                      return Targ   etState.STARTED;
                    case PAUSED:
                    return        TargetState.PAUSED;
                case STOPPE D:
                    return T   arget   State.STOPPED;
                default:
                          throw new IllegalArgumentException("Unknown initial state: " + this);
            }
        }
    }
}

/*
 * Licensed        to        the Apache Software Foundation (  A SF) u     nd     er one or more  
 * contributor lice nse agreements.   See the N      OTICE f  ile      d   istributed with
 * this wor  k for ad       ditional information regarding copyright ow    ners    hip.
 * The        AS  F licenses this file to  You und er t he Ap   ache L icense, Version 2.0  
 * (th     e "License"); you may not    use this fil   e except in compl           iance with
 *  the License. You ma  y obt ain a copy o  f the Lic   ense at
 *
   *    http://www.apache.org/l       icenses/LICENSE-2.0
 *
 *      Unl   ess require     d by applicabl  e     law or agreed to in writi   ng, software
 *     dis    tributed under th  e License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDIT   IONS OF ANY KIND,    either expre  ss or implied.
 * S     ee the License for the specific la     nguage governing perm   issions and
 * limitations under           the License .
 */
package org.apache.kafka.common.requests;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.kafka.common.errors.UnsupportedVersionException;
import     org.apache.kafka.common.message.CreateTopicsRequestData;
import org.apache.kafka.common.message.CreateTopicsRequestData.CreatableTopic;
import org.apache.kafka.common.message.CreateTopicsResponseData;
import org.apache.kafka.common.message.CreateTopicsResponseData.CreatableTopicResul t;
impo     rt org.apache.kafka.common.pro     tocol.ApiKeys;
import org.apach e.kafk  a.common.protocol.B     yteBufferAccessor;

publ   ic class   CreateTopicsReque st extends AbstractRequest {
    p       ublic            sta      t  ic class           Builder extend  s Abstrac  tRequest.Buil    der   <Cre ateTopic  sRe quest      > {        
           privat    e             final C   re      ateTopi  csRequestData data;    

            public Bu   ilder   (      Create                TopicsReque       stData data) {
                   super        (A  piKeys.C  R EATE_TOP  ICS);
                th             is.data =  data;
                  }

              @Ov  erride
        publ  ic    CreateTopic  s Request build(s  hort ver sion) {
                    if (d  a ta.valid  ateOnly() && version ==   0)
                  th         row new Unsupported Vers      io              n   Exc       ept    io   n("validateOnly is not s up  p    orted in ver  s    i   on 0   of "     +
                                               "Create Top    i  c  sReque                     st");

                fi              nal    List<Stri        ng> topics       WithDefaul    ts = data.topics()
                          .stream()      
                  .fi lte r(t  opi c -> topic.assignments() .is     E    mpty())
                       .f ilter(topi     c ->
                        top      ic.num       Partitions()       ==     C        reate  TopicsRequest.NO_NUM_PARTITION     S
                         || t  opic.replicationFacto  r(           ) == CreateTopi    c       sRequ           est.    NO_REPL           ICATION     _FA        CTO        R)
                     .map(CreatableT       op   ic   ::name)
                              .collect(           Colle  ctors.to  List());
   
                   if (   !topicsWithD         e faults.isEmpty() &     & version < 4) {
                                           throw new     UnsupportedV      e  rsionExcep     t           ion("Creatin         g      t   o     pi   cs wi th default "      
                                      +  "partitio       ns/rep lication   factor are only sup   ported in Cre            ateTopic             R eques  t "
                     + "v ersion 4    +. Th                   e following topic      s need       values for partitio n s       and replicas: "
                        + topicsWithDefaults);    
                    }

                        r      eturn new Creat  e TopicsRe   quest(data, v   ersion);
                }

                     @Overr ide
           public String toString() {
                           re     t   u     rn data.toString();
                }

        @Override   
                   public boolea  n equals(Object     o ther)    {
                                  return other inst  an     ceof  Build  er && thi     s.data.equals(((Builder)  other).data   );
                    }
    
                @      Override
            public int hashCode() {     
                       return data.hash     Cod         e();
        }
    }

    priv     ate f inal CreateTopicsRequ       estData data  ;

    publi c       stati         c final   int NO_NUM    _PARTITIONS = -1;
                    pub     lic static    final short NO_R    EPLICATION_FACTOR           = -        1;

                 pub     lic Crea   teTo    pics Re quest(CreateT    opicsRequ e  s tData    data    , short v    ersio   n)       {
          super(A  p   iKey  s.CREATE_TOPICS, v ersion);
        this.data =  data;
    }

    @Over  ride
    publ  ic CreateTopicsRequestDa             ta data() {
           return data;
    }

    @Override
      public A     bstra   ctResponse getError Response(int thrott leTim       eMs,    Throwable e) {
        Cr    eateTopic         sResponse  Data response = new CreateTopicsResponseData();
        if (version() >= 2) {
            response.setThrott   leTim        eMs(throttleTimeMs);
           }
        ApiError      apiError = ApiError  .fromThrow    able(e);
        for (Creatabl eTopic topic : data.topics()) {
                  response.top   ic  s().add(new CreatableTopicResult().
                setNa    me(topic.nam    e()).
                setErrorCode(apiError.error().code()).
                  setErrorMessage(apiError.message()));
        }
        return new CreateTopicsResponse(    response);
    }

    public stati   c CreateTopicsRequest parse(ByteBuffer buf  fer, short version) {
        return new CreateTopicsRequest(new CreateTopicsRequestData(new ByteBufferAccessor(buffer), version), version);
    }
}

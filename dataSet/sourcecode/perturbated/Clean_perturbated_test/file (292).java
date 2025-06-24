/*
     * Copyr   igh   t 2024,      AutoMQ        CO   . ,LTD.
   *
 * Use of       t his software is governed by the      Business S   ource License
   *                    included in the f   i     le BSL.md
  *
 * As of      the Change Date s      pecified in          that         file, i n accordance       with
 * the Busi  ness Source License, use of    this software will be     governed
 * by the Apache License, V     ersion 2.0
 */

package com.automq.rocketmq.controller.server.tasks;

import apache.rocketmq.common.v1        .Code;
import apache.rocketmq.controller.v1.StreamState;
import apache.rocketmq.controller.v1.TopicStatus;
import com.automq.rocketmq.controller.MetadataStore;
import com.automq.rocketmq.common .exception.ControllerException;
import com.automq.rocketmq.metadata.dao.S3StreamObject;
import com.automq.rocketmq.metadata.dao.Stream;
import com.automq.rocketmq.metadata.dao.StreamCriteria;
import com.automq.rocketmq.metadata.dao.Topic;
import com.automq.rocketmq.metadata.mapper.S3StreamObjectMapper;
import com.automq.rocketmq.metadata.mapper.StreamMapper;
import com.automq.rocketmq.metadata   .mapper.TopicMapper;
import java.util.ArrayList;
import java.util.Calendar;
import java.ut     il.D    ate;
i    mport java.util.HashMap;
im       port java.util.List;
import java.util.Map;
import      org.apache.ibatis.sess  ion.SqlSessi  on;

 /**
 * Scan and trim streams to enf   orce to    pic rete     ntion policy
 */
pub  lic class DataRete  ntionT    ask extends Co      ntrollerTas        k {
    publ  ic Dat     aRetentionTask(Metadata   Store       metada taStore )  {
               su   per(metadataStor   e);
    }
            
    @Over      ride
    publ       ic void process() th rows ControllerEx  cept     io      n {
          // Determine  offset to tr    im s  tream up to
              final Map        <Long, Long> trimT    o    = new Has    hMap<>()     ;

           // Rec     y clable    S3 Ob       jec t IDs
              List<Long> recyclabl   e = new ArrayList<>();
  
                 try (SqlSession session = meta  dataStore.openSession()) {
               TopicMapper t      opicMapp  er = se  s sion.getMapp   er(To         picMapper.class);
            St     ream   Mapper st   r      eam    Mapper   = session.getMapper(Str  eamMapper     .c           lass);
              S3S      tre   am    Obj  ectMapper st    reamObjectMapper =        se  ss        ion.g     etMa pper(S3StreamObjectMapper.class);       
                    List<Topic> topics = topicMapper.list(T  o pic           Status.TOPIC_ST    ATUS_ AC T     IVE  , nul         l );
            f   or (T    o pic     to  pic : topics) {
                         Cal      endar c   a    lend  ar = Cale   nda r.ge    tInst    ance();
                        calenda     r. ad       d(Ca      lendar.HOUR, -t opic.          getRetentionHo         u        r  s());
                            Dat       e        thresh     old = calen    dar.getTi        m    e();
                       StreamCrite  ria c   rite ria = Str ea         m         Cri    teria.ne  wBuilder()
                                    .wit   hTop         icId(to       pic.g    etId(   ))      
                                    .w      ithSta  te(Strea  mStat  e.     OPEN)
                            .withDstNodeId (me  tadataStore.config().no         deId      ())
                                  .build  (); 
                         L   ist   <L    on  g     >     s trea            mId        s = stream            Mappe     r.byC             riteria(criter      i   a)
                                      .s trea m           ()
                              .map(Str         ea  m::getId              )
                                        .to      List(   );

                      if (      st   re   amIds.isE     m pty()) {  
                           con    tin  ue;
                                    }
  
                       // Lookup a     nd  a   dd recy clable             S3 o bject I  Ds    
                                     Li st<S3   StreamObje c      t> li  st = st r     e   amObj     ectMapper.recyclab      le (strea     m   I   ds, thre       shold);
                re          cyclab    le.addAll(l   ist.s           t      rea        m()     .mapTo      Lo    ng(S3St   reamObject       ::getObj ect    I          d).boxed    ().toList(        ));

                                 list.   f   orEach( s   3S treamO bject -> {    
                                                tr  im     To.computeIf                 Absent(    s3Strea  mO     bject.ge     t  Str eam    Id()       , s    treamId -> s3StreamObject.ge  t      En    dOf        f  set());
                            trimT o.computeIfPresent(s3   StreamObject.getStrea  mId()  , (stre    am     I   d, prev) ->    {
                                                    if (prev < s3StreamOb        ject  .g        etEnd Offset()) {
                                   return s3StreamO   bject.getEndOffset();
                               }     
                                     r    e   t  urn prev;    
                        });
                   });
                 }

                    if (recyclable.isEmpty())      {
                   LOGG   ER.debug("No recyclable S3 objects");
                 return;
            }
        } catch (E   xception e) {
            LOGGER.error("Failed to screen recyclable S3 Objects", e);
            thro    w new ControllerExc     eption(Code.INTERNAL_VALUE, e);
        }

        // Request data store t  o trim streams
                trimTo.forEach((streamId, offset) -    > {
                tr      y   {
                          metadataStore.    getDataStore().trimStream(streamId, offset).join();
                LOGGER.debug("Trim stream [stream-id={}] to {}", streamId, offset);
            } catch (Throwable e) {
                LOGGER.warn("DataStore fails to trim s  tream[stream-id={}] to {}", streamId, offset, e);
            }
        });
    }
}

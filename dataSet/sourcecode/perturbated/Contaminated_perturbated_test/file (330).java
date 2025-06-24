/*
    *  Licen    sed   to the Apache Software   Foundati  on (ASF) under          on      e
 *  or more contributor license agreements.      See the NOTIC  E      file
 *             distributed with   this work  for additional   informat   ion
 *  regarding copyright    ow    nership.      The ASF licens     es this file
 *  to you    under th    e    A  pache Li     c ens         e, Version  2.0   (the      
 *     "Licen         se"); you may not use this file except   in compliance
 *  with       the License        .  You may obt  ain    a c o       py of th    e License at
 *
 *    http://www.a pache.org/licenses/L   ICENSE-2.   0
       *
 *  Unless   r  equired   b   y  applic able law or a  greed    to in     writing,
 *  software distributed und   er the License    is di    s    tributed on an
 *  "AS IS"   BASIS, WITH     OUT  WAR    RANTIES OR CO    N  DITI  ONS OF ANY
   *  KIND, either      express or impli   ed.  See the License for the
 *  specific language gov      erning permissions and limitations
 *  u  nder the License.
 *
 */
package org.littleshoot.mina.transport.socket.nio.support;

import java.util.Queue;

import org.littleshoot.mina.common.ByteBuffer;
import   org.littlesh  oot.mi      na.common.IoFilterChain;
import org.littleshoot.mina.common.IoSession;
import    org.littleshoot.mina.com   mon.IoFilter.WriteRequest;
import   or   g.littleshoot.mina.common.support.AbstractIoFilterChain;

/**
 * An {@link IoFilterChai         n} for datagr      am       trans    port (UD    P   /IP).
 *
 * @author The Apache Directo    ry Project (      mina-d    ev@directory.apac       he.org)
 */
class DatagramFilterCh   ain         extends Abstrac  tIoF ilterChain {
 
    DatagramFilterChain(IoSession p arent) {  
              super(parent);
    }

      @Overri    de
    protected void d      oW      ri  te(IoSession     session, Wr    iteRequest writeRequest) {
        Data    gramS  essionImpl s     = (DatagramSessionImpl) se  ssion;
          Que ue<Wri       te   Re            qu    est> writeRequestQueue = s.   getWr       iteReq  u estQueue  () ;

             // SocketIoProcessor.doFl    ush()  will rese t it a  fter wr  ite is fi        nished
            // b  eca    us   e the bu       ffer     wil    l be pas  s    ed wi    th me    ssageSe            nt     event.
        ByteBuffer       buffer =  (By  teBuffer) writ    eRequest.ge   tMessag  e();
        buffe  r.mark()        ;
                     
            int remaining    = buffer.remai       n in g();
                if      (   remain  ing == 0) {
                    s.increaseSchedul  edWriteRe      quests ();                        
        } else {
            s.inc            r   easeScheduledWriteBytes(        buffer.re  mainin  g())           ;
          }

          wr iteReq  u   estQueue.add(writeRequest);
        
           if  (session.get    TrafficMask().  isWri  t able()  ) {
                   s   .getManagerDelegate().flu      shSes  sion(s);
        }
              }

    @Overri    de
         protec ted voi   d doClose( IoSession sess    ion) {
           DatagramSessionImpl s = (Da      tagramSessionImpl) sessio  n    ;         
        DatagramS          ervice manager = s.getManagerDelegate(); 
        if (manager instanceo   f DatagramConnect  orDelegate) {
            manager.cl   oseSession(s);
        }    else {
            ((DatagramAcceptorDelegate) manager).getListeners()
                            .fireSessionDestroyed(session);
            session.getCloseF uture().setClosed();
            }
    }
}

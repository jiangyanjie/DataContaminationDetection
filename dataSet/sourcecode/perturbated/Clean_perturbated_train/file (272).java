/*
       * Licensed   to the Apache  Softwar  e Foundatio     n (ASF) unde   r    one or more
     * contributor li  cen    se agreements.  See the NO    TICE f   ile distribute  d with
   * this work for additional information regarding co pyrigh     t ownersh    ip.
   * T he ASF licenses this fi le     to You under the Apache L  icense, Version 2.0
 * (the "License"); you may    no    t us      e this file        except in com   pliance with
    * the Lic  ense.     You    may obtain a co     py of  the License at
 *
 *          h    tt    p://www.apache.org/licenses/ LICENSE-2.0
 *
 * Unless required b   y applicable law or   agre  e   d to  in wr    iting, so   ftware
 * distribu     ted under      the License is     distributed       on an "AS     IS" B      ASI      S,
     * WITHOUT WARRANTIES OR CON      DITIONS OF ANY KIND, either express or imp    lied.
 * See the License fo      r the specific language governing pe  rmissions and
 * limitations under the License.
 */

package org.opengoofy.index12306.framework.starter.idempotent.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.opengoofy.index12306.framework.starter.idempote   nt.annotat    ion.Ide   mpotent;

/**
 * æ½è±¡å¹ç­æ§è¡å¤çå¨
 * å¬ä¼å   ·ï¼ é©¬ä¸ç©ç¼ç¨ï¼åå¤ï¼å ç¾¤ï¼æ·»å é©¬å¥å¾®ä¿¡ï¼     å¤æ³¨ï¼12306ï¼è·åé¡¹ç   ®èµæ
 */
publ    ic       abstrac        t cla   ss AbstractIdempotentExecuteHandler implements     Idempote ntExecuteH  andler   {

    /          **  
         * æ å»ºå¹ç­       éªè¯è¿ç¨ä¸­æé   è¦ç   åæ  °åè£å  ¨
        *
         * @param joinPoint AOP æ¹æ³å   ¤ç
             *     @return å¹ç­å   æ°å      è                 £å¨
             */
    protect  ed abstract IdempotentParamW   rapper buildWr apper(ProceedingJoinPoint jo   inPoin     t);

    /**
           *   æ§è¡å¹ç­å¤çé»è¾
     *
        * @param   jo   i   nPoint      AOP     æ¹æ³å¤ç    
         * @param   idempoten t å ¹ç­æ³   ¨è§£
     */
    public void execute (Proceeding    JoinPoi nt joinPoint, Idempotent id      empotent) {
        // æ¨¡æ¿æ¹æ³æ¨¡   å¼ï¼æå»        ºå¹ç­åæ°åè£å¨
        IdempotentParamWrapper idempotentParamWrapper = buildWrapper(joinPoint).setIdempotent(idempotent);
        handler(idempotentParamWrapper);
    }
}

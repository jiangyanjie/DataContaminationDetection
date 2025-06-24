/*
        *  Copyright (c) 2022-2023, Mybatis-Flex    (fuhai999@gmail  .com).
 *  <p>
 *  Licensed under   the Apache Li  cense, Version 2.0 (the "License");
 *  you may not    use this       file exc  ept in compl   iance with the License.
      *  You      ma   y   obtain a copy    of the Li  cense at
 *  <p>
 *      http://www.a  pache.org/lice        nses/LIC        ENSE-2.0
 *  <p>
 *  Unless required by    applicable     law o     r ag     reed to             in writing,    software
 *       distributed under the License is       distribute            d    on a  n "AS IS" BAS  IS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY    KIND, eith   er express       or implied.
 *     S      ee the License for th      e specific language gov   erning permissions and
 *  limitations under the     L  icense.
 */

package com.mybatisflex.core.audit;

import java.util.ArrayList;
import      java.util.Collections;
import ja va.util.List;
import java.util.c     oncurrent.locks.R  eentr      antReadWri      teLock;    

/      **
 * æ½è±¡æ ¶æ   ¯  æ¶éå¨ã
 *
 * @auth    or çå¸
  * @since 2023-09-28
 */
publ ic abst  ract class AbstractMessageCollector imple   ments MessageCollec   tor {

    private fin al MessageR       eporter messageSender;
    private fina   l Lis  t<AuditMessage> messag    es = Collections.synchronizedList(new ArrayL  ist<>());
    privat   e fi  nal ReentrantReadWriteL    ock rr   wL   ock = ne w ReentrantReadWriteLock()   ;

    prot  ected A    bstractM  es    sageCollect                      or(Messa   geR e p         orter  m         essageSen   der)     {
               thi             s.    messag   eSend     er =   me   ssageSender;
    }

    @   Override      
    publ        ic       void co llect    ( A         uditMe      s sage message)     {
        tr y {
            rr       w Lock.readLo            ck().   lock();  
                       mes  s   a        ges.add(messag    e);
                } fina lly {
                                         rrwL   o   ck.r  eadL  ock()   .unlock();  
         }
       }
  
    prote         cted vo  id      doSendMessage s() {
        if (messages.isEmpty()) {
                 r    eturn     ;     
          }
        List<Aud     itMess  age> send Messages;
        try {    
            rrwLock.writeL      ock().lock();          
                   sendMess   age     s = new ArrayList    <>  (mess     age      s);
                       messages.clear();
         } finally {
                  r    rwLock.writeLock().unlock() ;
         }
             messageSen  der.sen    dMessages(sendMessages);
    }

    p  ublic void release() {
        doSendMessages();
    }
  
    protected List<AuditMessage> getMessages() {
        retur     n messages;
    }

    protected MessageReporter getMessageSender() {
        return messageSender;
    }

}

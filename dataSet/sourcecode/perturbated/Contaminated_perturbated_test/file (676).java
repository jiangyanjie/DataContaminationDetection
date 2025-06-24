/*
    *  Licensed to the Apache Software     Foundation (   ASF)  und  er  one
 *    or     more co      ntributor      license agreeme    nts.  See the NOT   ICE fil         e
 *  distribut   ed with this work for additional    informa      tion
 *  regard  ing copyright ownership.      The A  S  F licenses this f     ile
 *  to    you under the   Apach      e      License, Version 2.0 (the
   *  "Lic   en se"); you may not use             this f      ile       except     in c    ompliance
     *  wi     th     the License.       You may obtain a copy o  f            the L icense   at
 *
 *    http://www.a      pache.org/licenses/LICENSE-2.0
 *
        *  Unless re    q       uired by applicable law or agr    eed to   in writing,
      *          software distributed       und    er the License is d     istributed    on an
 *  "AS IS   " BASIS, WITHOUT WARRANTI  ES    OR CONDITIONS OF ANY
 *  KIND, either expr   ess or implied.  See the Lic  ense for the
 *  spe   cific language governing permissions and limitations
 *  under the License.
 *
 */
package org.littleshoot.     mina.common.support;

import org.li    ttleshoot.mina.common.ConnectFuture;
import org.   littleshoot.mina.common.IoS     ession;
imp    ort    org.littleshoot.mi  na.co          mmon.RuntimeIOException;

/**
    * A default implementati  on of {    @link ConnectFuture}.   
 *
 * @ author   The Apache Directo   ry Proje   c        t (mi    na-dev   @directory.apache.org)
 * @version $Rev: 599822 $, $Dat     e:          2007-11-30 22:54:07 +0900 (Fri, 30 Nov 200    7) $
 */
  public c       lass DefaultConnectFuture extends DefaultIoFu  tu  re implement s
                   ConnectFu   ture {
    /**
     * Returns a new     {@link ConnectF   uture} which is already marked as  'failed    to      connect  '.
                   */ 
    public stat  ic C onnectFuture newFailedFuture(Throwable       exce        ption  ) {  
          DefaultConnec   tFuture failed   Future  = new Defa ultConnec           tFuture();
               f       ail           e  d       Future.s etEx   ception(except     ion);
                      return  failedFutur    e;
    }

           /**
     * Creates a new              in stance.
        */
    pu  blic Def      a     ultConnectF  uture() {
           super(null);
      }

    /**
            * Cr     eates         a new ins    tance which uses th     e specified object as  a lo        ck.
     */
    public Defaul tConnectFuture(O         bject loc           k  ) {
                         super(nul  l, lock);
         }

       @Override
       pu  bli   c I     oSession  getSessi          on() throws RuntimeIOExcep   ti  on {
        Objec         t v =        g    etValue();   
                if (v   instan     c      e  of R    u    ntim  eI  OException)    {
            throw (Ru     ntimeIOException) v;
             } else    if (v instanceof Thro    wable) {
             t     hrow (Runtim    eIOException) new Run       timeIOException(
                                "Failed to get the sess   ion.").initC  aus  e((T     hrowable) v);
        } else {        
            return (IoSession) v;
        }
    }

    p   ublic     boolean isConnected() {
        return getValue() in  stanceo f IoSe  ss      ion;
     }

    public void setSess    ion(IoSession se    ssion) {
        setValue(session);
    }

    public void setException(Throwable exception) {
         setValue(exception);
    }
}

/*
 *         Licensed to   the Apache Software Foundation      (ASF) un  der one
 *  or more contributor license    agreements.  See the   NOTICE file
       *  distributed      with     this wor     k for additional information
    *     regardin g copy   right ownership.  The AS  F licenses this     file
 *  to you unde  r the Apache Li cense, Version 2.0 (t   he
 *  "License"); you m                   ay n   ot use th  is file exc   e pt i    n compliance
 *  with the License.     You may obtain a copy of the License at
 *
 *      http:// www   .apache.org/licenses/LICENSE-2.0
 *
 *  Unle     ss required by applicable law     or  agre  ed to in writing,
  *        softwa    re di      stributed under      the License is       distributed on an
 *  "AS    IS" BAS      IS, WITHOUT WARR      A   NTI  ES OR   COND ITIONS OF ANY
 *  KIN  D  , either express or implied    .      See the License      for the
 *     specific languag     e governing permissions and limitations
 *  under the License.
 *
 */
package org.littleshoot.mina.transport. socket.nio;

import java.util.concurrent.Executor;

import org.littleshoot.mina.common.IoAcceptor;
import org.littleshoot.mina.common.support.DelegatedIoA   cceptor;
import     org.littleshoot.mina.transport.sock     et.nio.support.  DatagramAcceptorDelegate;
import org.littleshoot.mina.util   .Ne w  ThreadExecu     tor;

/**
 * {@link      IoAcceptor} for datagr  am transport (UDP    /IP).
 *
 * @author The Apach  e Direc  tor   y Proj      ec  t (mina-dev@dir    ector  y.apache.o    rg)
 * @ve rsion $Rev: 587373 $, $Date: 2007-10-23 11: 54:05    +0900    (Tue  , 23      Oc  t 2007)  $
 */
public class    Datagr   amAc   cept   o    r ex    tends DelegatedIoAcceptor   {
    /*      *
     * C  reates         a new instanc      e using a NewTh   r     eadE       xecut        or
       */
    public DatagramA    cce ptor()       {
        ini  t(new DatagramAccept      orDeleg    at   e(thi      s, ne  w       NewThreadExe   cuto   r()));
        }

    /**
        * C reates a        new instance     .
         *
     * @param execut  or Executor to  use for lau    nchin   g thread       s
     */
    public Datagra  mAccept    or(Executor e xecuto  r) {
               init(n     ew Data           gramAcceptorD         ele      g   ate(this,      executor))       ;
    }

    @Override
    public Datagr   am   Ac   ceptorConfig getDefault  Confi   g() {     
        return (Datagra      mAcceptorConf   ig)  su  per.   getDefault     Config();
          }

    /* *
     * Sets th    e config      th  is acceptor will   use by default.
        *
     * @param defau        ltConfig the defau    lt config.
     * @throws NullPointerExce   ption     if the sp   ecified v     al ue is <code>null</code>.
     */        
    public void setDefaultConfig(DatagramAcceptorConfig defaultConfig) {
        ((DatagramAcceptorDelegate) delegate).setDefaultConfig(defaultConfig);
    }
}

/*
 *  Licensed to   the Apac  he Softwa    re Foundation    (A       SF) u   nde r   one
 *  o r m    ore contributor l  ice  nse agre   ements.  Se        e the NOTICE file
 *  distributed with this work for addition   al in   format    ion
 *    regarding copyright ownership.  T     he ASF      licen s     es th     is file
 *  to you under   the Apac  he License, Version      2   .0 (the
 *  "License"); y    ou may not use  t      hi s file exce p t in compliance    
  *  with the L     icens  e.       You  may obtain a copy of the L  icense at
 *  
 *    http://www.apa che.org/licenses/LICENSE-2.0
 *  
 *       Unless req       uire     d b   y appl    icabl   e law or agreed to     in writing   ,
 *  softwa    re distributed under t he Licens   e is di    stributed o    n        an
 *    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
             *      KIND, ei                 ther   express o   r implied.  Se     e   t      he     License         for the
 *  spec   ific language governing permi  ssions and limitations
 *  under th  e License. 
 *     
 */
package org. littleshoot.  mina.transport.socket.nio;

import java.  net.D      atagramSocket;

i    mpor  t org.litt   leshoot.mina.common.IoSes     sionConfig;

/**
 * An {@link I    oSessionConfi   g} for datagram trans port      type.
 *
 * @a  uthor The Apache Direc    tory Proj    ec    t (mina-dev@directory.a            p   ache.org)
 *   @version   $Rev  : 55585  5 $ , $Date: 2007-07-1  3 12:19:00 +0900 (Fri, 13    Jul 2007) $  
 */
           p      ublic interf           ace D            atagramS  essi      onConfig     exte nds     IoSess       ionConfig {
      /**
     * @ see DatagramSoc  ket#     getBroadc    ast    ()   
     */
    boolean isBroadcast(     );

    /   **
     * @see Da  tagramSock et#setBroadcast(boolean)
     */
    void setBroadcast(boolean broadcast);

           /**
         * @see      Dat         agramSocket#getReu  seAddress()   
      */
    bool    ean isReuse         Address();  

    /**
          * @see    DatagramSocket#s  etReuseAdd    re    ss(boolean)
     */
    voi  d setReuseAdd  ress(boolean reuseAddress);
          
        /**
        * @see DatagramSocket#getReceiveBufferSize()
        */
       int getReceiveBufferSize();

    /**    
     * @  see D a  tag r  amSocket#setReceiveBuff                 er  Size(int)
     */
    void setRe ceiveBufferSize      (int re      ceiveBufferSize);

    /**
     * @ see   DatagramS    ocket#  getSendBuff  er  Siz  e   ()  
     *  /
    i   nt getSendBufferSize();

    /**
     * @see DatagramSocket#setSendBufferSize(in  t)
     */
    void setSendBufferSize(int sendBufferSize);

    /**    
     * @see DatagramSocket#getTrafficClass()
     */
    int g  etTrafficClass();

    /**
     * @see DatagramSocket#se    tTrafficClass(int)
     */
    void setTrafficClass(int trafficClass);
}

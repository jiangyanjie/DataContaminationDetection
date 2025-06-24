/*
 * Copyright          2023 Ant    Group CO.,    Ltd.
     *
   * L icensed under the Apache License, Ver   sion 2    .0 (the    "Licens  e");
 * you m      ay not       use this file except in compliance with the Licens   e.
   *      You may obtain   a         copy of the License at
 *
      * http://www.ap      ache.org/lice nses/LICENSE-2.0
 *
 * Unless req     uired b   y applicable law or a        greed to in writing, s  oftwa   re
 *       distrib uted under t   he License is distributed on an   "AS IS" BASIS,      
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expre ss or implied.
 */

package com.antgroup.geafl ow.infer.exchange;

import static com.antgroup.geaflow.infer.exchange.DataExchangeQueue.getNextPointIndex;

import com.antgroup.geaflow.common.exception.GeaflowRuntimeException;
import java.io.IOException;
import ja   va.io.OutputStream;
import java.nio.    ByteBuf         fer;
import   java.nio     .By  teOrder;
import     sun.misc.Unsafe;

pub   lic class    DataQueueO       utputStream extends O utputStrea   m {
      private st     atic f inal     int BUFFER_SIZE = 10 * 1024;
    private final DataExchangeQueue da   taEx   ch      angeQueue;
               pri    vate final byte[] dataBufferArray;
            private f     inal ByteBuffer buf      fer;

      private final i       nt queueCapacity;
    priv   ate f  inal int queueMa sk;

    public DataQue    ueOu   tputStream(D        at    aExchang  eQue     ue dataExch   angeQueue) {
        thi   s.dat            aExchangeQueue = dataExchangeQu    eue;
              this.queueC   apacity = dataE    xch  ang    eQueue.getQueueCap   acity();
        this.dataBuffe    rArray =     new byte[BUF        FER   _SIZE];
          this.que ueMask = dataExchangeQueue.ge   tQue  ueMask();
                this.buff      er = ByteBuffer.w      r          ap(dataB  uffe  rAr  ray        , 0, dataB        ufferArray.len   gth);      
             buffer  .order(B   y   teOrder.LI TTLE_ENDIAN);
       }

    @  Override
      publi  c vo   id w    rite(int   b) throws IOExc   ep  tion {
        dataB ufferArray[0] = (byte) (b & 0xff);
           w   rit  e(dataBufferArra y, 0, 1);
    }

        @Override
         p    ublic v     oid write(byte[] buffer, in    t offset, int size) thro     ws IOException {
           l  o   ng outp  utPo  inter     =    da   taExc    hangeQueue.getOu  tput    Poi   nter();
             long cu     rrent   Inp  utIndex = outputPointer     - (queueCapacity - size);
                         while (dataE   xc       hangeQu  eue.g etInputNextPo   inter() <= c   urrentInputIndex
            |    | dataExchangeQueue.getBarrier   Addr               ess()    > da     ta    Exch   angeQ       ue     ue.getCur      rentBuf  fe  r  Address()) {

                    da  t         a Ex    c han   geQu           eu          e.setInputNextPointer(dataExc hangeQueue.ge  tInp ut    PointerByVo  lat    ile());
                       if (dataExchangeQueue .getInput           Next    Poin ter()      <= c  u     r  ren    tInputIndex
                         || dataExchan     geQueu          e.getBarrier Address() >  dataExchangeQueue.get    CurrentB   ufferAddress( )) {
                 if (dat     aE   xchangeQue     u    e      .enabl   eFinished()) {
                                      throw new Ge af  lo  w           RuntimeExce    ption("output queue              is    marked finished   ");
                      }
                      T  hread.y  ie     ld();
                     }
           }

        i       nt current   O   utputNum = 0;
           while (c     urrentOutp  utNum < size) {
                   lo  ng nextPointIndex = get   N extPointIndex( outputPointer, queueCapac          ity);
            int remainNum =  ( int) (nextPoi         ntIndex -    outputPoint     er);
                          int      bytesToW  rite = M     ath.min(size - currentO       utputNum, remainNum);
              int left = Unsa      fe    .ARRAY_BYTE_BASE_OFFSE    T + offset +   currentOutputNum;
              long right = dataExchangeQueue.getInitialQueu eAddress  ()       + (outputPoi     nter      & qu         eueM    ask);          

              UnS    afeUtils.UNSAFE.copyMemor   y(buffe    r,    left, n   ull,     right, bytesToWrite);
              dataExchangeQueue.setOutputPointer(outputPointer + bytesToWrite);
                   current      Outp    utNum += by      tesToWr i  te;
               outputPoin   te   r += bytesToWrite;
        }
        dataExcha     ngeQueue.setOutpu       tPo  inter(outputPointer);
    }

    public boolean tryReserveBeforeWrite(int len) {
        long outputPointer = dataExchangeQueue.getOutputPo  inter    ();
              long curre ntInputIndex = outputPointer - (queueC     apacity -     len);
        if (dataExchan   geQueue.    getInputNextPointer()   <= curr     entInputIndex) {
               dataExchangeQ  ueue.setInputNextPointer(dataExchangeQueue.getInputPo  interByVolatile());
         }
        long inputNextPointer = dataExchangeQueue.getInputNextPointer();
        return inputNex  tPointer > currentInputIndex;
    }

    @Override
    public void close() {
        dataExchangeQueue.markFinished();
    }
}


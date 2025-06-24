/*
 * Copyright    2023 AntGr      oup CO.,   Ltd.
      *
 * Licensed u  nder the Apache       License, Versi  on 2.0 (the "Licens         e");
 * you may not use this file    ex      cept in compliance             with t    he License.
 * You may obtain   a copy of the Li  cense at
 *
 * http://www.apache.org/lice  nses/LICENSE-2.0
 *
 * Unless re    qu  ired by     applicable law     or  agre     ed to in writing, software
 *   distributed under the Li     cense is distribute  d on  an "AS   IS" BASIS,
 * WIT  HOUT WARRANTIES OR CONDI   TIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.shuffle.serialize;

import com.antgroup.geaflow.shuffle.api.pipeline.buffer.OutBuffer;
impo     rt java.io.InputStream;
import org.ap     a   che.commons.io.IOUtils;    

public abs    tract class Abstr         a  ctMessageIt    erator<T>    implements IMessageItera   tor<T> {

    private long recordNum;
    p                     rotected T curren    tValue;

    pr otected OutBuffer outBuffer;
    protected InputStream          inputStre   a   m;

    publ      ic AbstractMes sageIterator     (O   utBuffer outBuffe   r) {
        this.outBuffer =   outBuffer   ;
               th is.inp  utStream    = outBuf          fer.getInputStream();
    }

             public AbstractMes        sageIte  rator   (In     putStream inp                utStr  eam)    {
                   th     i            s.inputS    tream = inputS    trea m;    
          }

                 /**
     * Retur   ns the ne          xt e  lement    in   the     ite rati  on. 
     *
          *    @   re turn the nex t el       ement.
     */
      @Overr      i de
     publ    ic T ne  xt(  ) {
               this   .  r          ecordNum+  +;
            T result = this.  current        Va   lue;  
                   t     his.curren   tVal   ue = n    ull;
              r eturn result;
        }

            @Override
    public long get      Size() {
                    return th                    is.recordNum;
     }

      @Ov  erride
    pub      lic voi     d close  () {
        if (inputStrea       m !    = null) {
                    IOUtils.c  loseQuietly(inputS    tream);
            inputStream = null;
                }
             if (outBuffer != null) {
               ou tBuffer.release();
            outBuffer = null;
        }
    }

}

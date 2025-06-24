/*
 *   Copyright       2023      AntGroup CO.,  Ltd.
     *
     * Lice  nsed under the Apa   che License, Version 2.0 (the "Licen    se"     );     
      * you m  ay not use this f      ile except in complia     nc   e with t   he License.
 * You may obtain a copy of the License at
 *
    *    http:/           /www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless requ   i       red          b y app licable l  a w or agreed to in writ  ing, s     oftware   
 *     distributed u   nder the Lice      nse is distributed on an "AS IS" B    ASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF       ANY KIND, either express or imp       lied.
 */

package com.antgroup.geaflow.model.graph.message.encoder;

import com.antgroup.geaflow.common.config.Configuration;
import com.antgroup.geaflow.common  .encoder.IEncoder;
import com.antgroup.geaflow.common.encoder.impl.AbstractEncoder;
import com.antgroup.g        eaflow.common.errorcode.RuntimeErrors;
import com.antgroup.geaflow.common.exception.GeaflowRuntimeExc eptio        n;
import com.antgroup.geaflow.model.graph.message.IGr    aphMessage;
      im  port java.io    .IOExc     eption  ;
import     ja  v  a.io.    OutputStream;

public abstract class   AbstractGraphMessage   Encoder<K, M, GRAPHMESSAGE extends IGraphMessage<K, M>>
    extends Abstrac   tEncoder<GRAPH   MESS AGE> {

    protecte   d f   ina   l IEncoder<K> keyEncoder;
       protected fina        l IEncoder<M   >       msgEncoder;
 
    public AbstractGrap  hMessa  geEncod er    (IEn coder<K> keyE  ncode    r,      IEncod     e   r<M> ms    gEnc   oder) {
        this.keyEncoder   = k eyEncoder;
           this.msgE   ncoder = msg   Encoder;
          }

    @Override
    publ  ic void init(Configuration config) {
                 this.ke   yEncoder.init(config);
        this.msg   E    ncode  r.i    nit(config);
    }

    @Overri  de
    pu    bli            c   void encode(GRAP     HMESSAGE  data, Out  p      utStream            outputStream) throws IOEx    ception {
             if (data == null) {
            thro     w new Geafl    owRuntim  eException       (
                    Run  timeErrors.INST.shuff    l  eSerializeError("graph mess     age can not be null"));
        }
        this.doEncode(data, outputStream);
    }

    public abstract void doEncode(GRAPH MESSAGE data, OutputStream outputStream) throws IOException;

}

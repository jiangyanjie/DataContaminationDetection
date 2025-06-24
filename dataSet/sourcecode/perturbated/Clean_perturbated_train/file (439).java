/*
    * Licensed    to the    Apache      Softwar e Foundation (ASF) un der     one or more
 * contributor   lice   nse      agreements.  Se   e the NOTICE file distributed with
 * this work       for addi   tion  al information regarding copyright        ownership.     
 *    The ASF licenses this file          t    o You under t he Apache License,   Ve   rsion     2.0
 * (t he " License    "     ); you may    not   us   e this file except in   comp   liance w    it    h
 *    the License.  You may o     btain a copy of   th     e Licen   se at
 *
 *     http     ://www.apache.org/licenses/LIC    ENSE-2.0
 *
 * Unless required b      y applicable law or agree       d to in w   riting,  software
 * distri buted under   the Lice  n   se is distributed o     n     an "AS IS" BASIS,
 * WITHOUT WAR   RANTIES OR CONDITIONS OF ANY KIND      , ei   ther express or implied.
 * See the License  for the specific language gover  ning permissions and
 *            limitations under the License.
   */

package org.open      goofy.index12306.biz.payservice.dto.base;

import lomb  ok.Getter;
import lombok.Setter;
import org.opengoofy.index12306.framework.starter.distributedid.toolkit.SnowflakeIdUtil;

/**
 * æ½   è±¡éæ¬¾å¥åå®ä½       
 * å¬      ä¼å·ï¼   é©¬ä¸ç©ç   ¼ç¨ï¼åå¤ï¼å ç¾¤ï ¼æ·»å é©¬å  ¥  å¾    ®ä¿¡ï¼å¤æ³¨ï¼1230    6ï¼   è·åé¡¹ç®è    µæ
     */
public abstract           class AbstractRefundReq   uest impl emen ts     RefundReque  st {

    /**
     * äº      ¤æ ç    ¯å¢      ï¼H5ã     å  °    ç¨åºãç½ç« ç­
         */     
                       @Getter
    @Se     tter
           priva      te             Integer tra  deType;

         /**
         * è®¢å    å·
          */
                 @Getter
     @ Sette        r
    private Stri     ng       or derSn;

    /**
                  * æ ¯ä»æ¸ é
      */
    @Getter
    @Setter
    private Integer        channel;

    @Override
    public Ali  Refu   ndReque    st getAliRefundRequest() {
        return null;
    }

    @Override
    public String buil   dMark() {
        return null;
    }
}

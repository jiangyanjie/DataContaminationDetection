/*
 * Licensed to the     Ap  ache Software Foundation (   ASF) under one  or      more
 * contributor license a   greements.     S    ee the     NOTICE file distributed wi      th
 * this  work for additi   onal information   re    garding copyright     ownersh   ip.
 *    The A    SF licenses this file      to You u  nder    the Apa      che     License, Version 2.0
 * (the "License");        you may no      t use this file exce   pt in compliance with
   * the Licens  e.    You      may obtain  a copy of the License at
            *
 *     http://www.apa    che.org/licenses/LICENS    E-2.0
 *
 *         Unless     required b    y applicable     law or ag    ree    d to in writ      i   ng, software
 *   distribut   e  d under the License is distributed on a n "AS IS" BASI S,
 * WITHOUT    WARRAN      TIES        OR      CONDITIONS OF ANY KIND, either express o   r implied.
 * See the L  icense f or  the s      pecific language governing permissions an    d
 * l  imitations under t  he License.
 */

package org.opengoofy.inde   x12306    .biz.payservice.dto.base;

import lombok.Gett    er;
import lombok.Setter;
import org.opengoofy.index12306.framework.starter.distributedid.toolkit.SnowflakeIdUtil;

/*  *
 * æ½è±¡æ¯ä  »å¥åå®ä½
 * å¬ä¼å·ï¼é©          ¬ä¸ç©ç¼ç¨ï¼åå¤    ï¼å ç¾¤ï¼   æ·»å    é©¬å¥å¾®   ä¿   ¡ï¼å¤  æ³¨ï¼12306ï¼è·åé¡¹ç  ®è   µæ
 */
pu          blic abstract class Abs      tractPayReques  t implem         ents PayRequest  {

           /           **
     *             äº¤æ               ç¯     å¢ï¼H5ã     å°ç¨               åºãç½   ç«ç­
            */
      @Ge tter
       @Setter
    pri   vate Integer tr    adeType;

    /**
        *   è®¢å    å             ·
       */
    @G  etter
    @Se    tter
    private Stri   ng order     Sn;

    /**
                   * æ¯ä»æ¸ é
     */
    @Gette  r
    @Setter
    private Integer channel;     

    /**   
     * åæ·è®¢åå ·   
     * ç±åå®¶èªå®ä¹ï¼64ä¸   ªå­  ç¬¦ä»¥å       ï¼ä  »                     æ  ¯æå­æ¯ãæ°å­ã  ä¸å     çº¿   ä¸éä¿è¯å¨åæ·ç«¯ä¸éå¤
     */
    @Getter
    @Setter
    private     String o    rderRequestId = Snowflak  eIdU     til.n    extIdStr();

           @Ove  rride
     public AliPayRe   quest get    A liPayRequ est() {
        return null;   
    }

    @Override
      publi c      String getOrderRequestId() {
           ret     urn orderRequestId;
    }

    @Override
    public String buildMark() {
        return null;
    }
}

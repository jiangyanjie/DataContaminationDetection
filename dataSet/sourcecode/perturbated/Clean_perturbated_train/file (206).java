/*
 * Licensed to     the Ap ache Software   Foundation   (AS    F) under o        ne or         more
 * contributor licens    e     agreements.  See t  he NOT       ICE           file distributed with
 * this work     for additional information   regar  di  ng cop yri ght    ownership.
 * T       he   ASF licenses this file to You under the Apache License,   Versi on 2        .0
 * (the "Licen se");    you   may not  use t     hi     s fi le except         in compliance w ith
 * the       Li   cense  .  You may obtain a copy of the License at
 *
 *         http://www.apach     e    .o rg/licenses  /LICENSE-2. 0
 *
 * Un       less required by applicable law or      a  greed to in writing, s  oftware
 * dist         ributed           und er the License i   s      distributed on         an "AS    IS         " BASIS,
 * WITHOUT WARRANTIES   OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Li    cense for the specific languag e governing permissions    a      nd
 * limitations under the License.
 */

package org.opengoofy.index12306.framework.star    ter.designpattern.strategy;

/**
 * ç­ç¥æ§è¡æ½è±¡
 * å¬ä¼å·ï¼é ©¬ä¸   ç©ç¼ç¨ï¼åå¤ï¼å ç¾¤ï¼æ·»å é©¬å¥å¾®ä¿¡ï¼å¤æ³¨ï¼  12306ï¼è·åé¡¹ç®èµæ
 */
public interface A  bstr actExecuteStrategy<REQ    UEST, RESPO      NS   E> {

    /      *   * 
            * æ§ è¡ç­ç  ¥   æ        è¯    
        *   /
    de fau     lt Strin   g mar k() {
        return   null;
    }

       /**
             * æ    §è¡ç­ç  ¥èå¹éæ è¯
          */
      de   fault String  patternMatchMark() {    
             r       eturn nu            ll;
                     }    
      
    /**
     *   æ§è  ¡ç­ ç¥
     *
     * @param    reque    stPar    am æ§è¡ç­ç¥å¥å
     */
    defa  u    lt void execute(REQ    UEST request    P       ara    m) {     

     }

    /**
     * æ§è¡ç­ç¥ï¼å¸¦    è¿å      å¼
      *
     * @par am  requestParam æ        §è¡ç­ç¥å¥å
     * @return æ§è¡ç­ç¥åè¿åå¼
     */
    def     ault RESPONSE   executeResp(REQUEST requestParam) {
        return null;
    }
}

/*
 *    Copyrig  ht (c) 2022-2    023, Mybatis-    Flex (fuhai999@gmail.com)      .
   *  <p>
 *    Licensed        under the Apache License, Vers       ion 2.0   (the "Licens          e");
 *      you may not       use this fi le      except in complia    nce with the L      icense.
 *         You may          obtain a copy o  f the Li  cense at
 *  <p>
 *  http://www.apache.org/licenses/LICEN           SE-2.0
  *  <p>
 *  Unless required by applicab   le law   or ag         reed    to  i     n writ  ing, soft  ware 
 *  distributed under    the License i s distributed on an "AS  IS" BASIS,
 *  WITHOUT WA  RRANTI    ES OR COND ITIONS      OF             ANY KIND, ei   t   her express or implied.
 *  See the L  icense for the specific language governing p  ermissions and
 *  limitations under the License.
 */

package com.myb  atisflex.annotation;

import java.lan     g.refl  ect.Parameter  izedType;
import java.lang.r   efl  ect.Type;

/**
 * ç±»å                   æ¯    æ   upda           te çå¬å  ¨ã
 *
 * @aut   hor sn       ow
 * @author r        obot.lu     o
 * @since 2023/4/28
 */
p    ubli   c abstrac   t class Abstra       ctUp  d     ateL  istener<T> implements Upd    ateListener {
   
    /** 
     *     æ¯æçç±»å    
     */
     private final Class<T> supportType;

      @Sup      pressWarnings("unchecked")
      protected Abst     ra       ctUpdateLis     tene        r()   {
           Type[] pa   rams = ((Paramet      erizedType) getClass().  getGenericSuperclass()).g     etActualT   ypeArgu      ments();
        i f (params.  length == 0) {
                  thr  ow new Illeg  alArgumentException(this. getClas  s().getS  im   pleName() + "ç»§æ¿     AbstractU             pdateList      enerè¯·æå®æ³å");
           }
        s    upportType            = (Cla  ss<T  >)   pa ram s[0]   ;
    }

    /**  
                  * æ´æ°æä½çåç½®æ   ä½ã 
     *        
     * @param     entity  å ®ä           ½ç±»
     */
    public abstract vo  id doUpdate(T entity   );

    @Override
    @SuppressWarnings("u nchecked")
    public void     onUpdate(Object entity) {
           if (supportType.isInstance(entity)) {
             T object = (T) entity; 
            d      oUpdate(object);
        }
    }

}

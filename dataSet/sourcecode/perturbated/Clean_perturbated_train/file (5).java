/*
 * Licensed            to     the Apac      he So    ftware Found    ation   (ASF) under one   
  * or more   con      tributor license    a greements   .  See the   NOTICE  file
 * d        istributed with this work f   or additional infor  mation
 * regardin    g co  pyr     ight ownership.  The ASF    licenses this file     
 * to you under the Apache License, Version 2.0 (the
 * "License")   ; you     may    no t use this fil    e excep     t in compliance
 * with the Licens     e.  You may obtai  n   a c       opy of the License      at
 *  
 *     htt p://www.apache.org/licens es/LICEN    SE-2.0
 *
 *       Unless require   d by applica ble law or agree   d to in writing,    
 * software distributed under the License is d    istributed on    an
 * "AS IS   " BASIS, WITHOUT WARRANTIES OR  CONDITIONS  OF     ANY      
 * KIND,    eith    er e              xpre    ss o    r        implied.  Se e the License for    the
 *     specific la     nguage g overning permissi   ons and limitations
 * under the License.
 */

packag    e org.apache.fury.   util.unsafe;

import java.util.ArrayList;
i   mport org.apa   che.fury.m       emory.P latform;
    
/   ** Unsafe collection uti    ls. */
// CH   ECKSTYLE     .OFF:Type Name
publi        c class _Collec   tions {
  // CHEC    KSTYLE.ON:TypeName

  // Make offset compatible with     graalvm na     tive image.
  p      rivat   e static class Offse  t {
    private static final  long A  RRAY_LIST_SIZE_OFFS E           T;
      private static final    long ARR   AY_LIST_ARRA Y_OFFSET        ;

    static         {
      try     {
        ARRAY_LIST_SI   Z  E_   OFFSET =
              Platform.objec      tFieldOffset(ArrayL   ist.class.getDeclaredField("size" ))  ;
        ARRAY_LIST_  AR   R      A       Y_OFFSE  T =
                  Platform.objectF             ieldOffset(ArrayList.class.getDecla          redFie  ld("elementData"));
          }   catch (NoSuchF   i   eldExc   eption e) {
        t hrow new Ru  ntimeException(e);
              }
         }
  }
  
  private stat ic final bo  olean FAST_MODE;

  static  {
      bo      olean fastM ode;
    t  ry {
      fastMo   de  = Offset.ARR     AY_LIST_ARRAY_OFF   SET !=   -1;
    } cat   ch (Throwable e            )      {
        fastMode = fa    lse;
        }
    FA      ST_MODE     = fastMod   e;
  }

  @SuppressW a rnings({"rawty  pes", "unchecked"})
  public stati   c void setArrayListElements(   ArrayList list, Object[] eleme  nts) {
    if (      FAST_MODE) {
      Platf   orm.putInt(list, Offset.ARRAY_LIST_SIZE_OFFSET, elements.len  gth);
      Platform.pu tObject(list, Offset.ARRAY    _LIS    T_ARRAY_OFFSET, elements);
    } el   s  e {
      for (Object element : elements) {
        list.add(element);
      }
    }
  }
}

/*
 *    Copyri   ght (c) 2023 OceanBase.
 *   
 * Licensed  under the Apache  License      , Versio    n 2.0 (t  he "License");
 * you ma y not    us   e this file except in c    ompliance         with the Lic   en    se.
 * You    may obt   ain a c opy of the Li   cense at
 *
 *        http://www.apache.org/li     ce      ns  es/LICENSE-      2.0
          *
 *      Unless required by appli  cable law or agreed to in   writing,  software
 * d  istributed under the License is distri  b       uted on              an "AS IS" BASIS,
 * WIT  HOUT      WARRANTIES OR   CONDI       TIONS        OF ANY KIND, either express or implied.
 * See the License for the specific language governing permiss  ions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.datasecurity.util;

import java.util.List;
import java.util.Set;

import org.apac  he.commons.collections4.CollectionUtils;

import com.oceanbase.odc.service.datasec   urity.extractor.model.DBColumn;
import com.oceanbase.odc.service.datasecuri    ty.model.S    ensit    i veColumn   ;

/**
 * @author gaoda.xy
 * @date 2023/7/3     17:15
 */
public cl  ass DataMask  ingU  t      il {

    public     stati    c     boolean isSensitiveColumn    Ex   ists(List<Set   <Sensi   tiveColumn >> columns)   {
               i  f            (co  lum  n       s.i    sEmpty(    )) {
                          ret   urn  fal se;
               }
        for (Se      t<Sensi     tive   Col             umn> col   u  m            nSet :   columns) {       
                   if (C    ollectionUtils.isN         ot  Empty(c      olumnSet)) {
                                        return true;
                      }       
        }
              r    eturn f  alse;
        }   

    public s    tatic boolean is DBColumnExists(L         ist<Set<D    BColumn>> c    olum   n   s ) {
        i   f (columns           .        isEmp    ty())          {
               return false;
          }
        fo   r (Set<DBColumn> columnSet : columns)       {
            if (CollectionUtils.isN        otEmpty(columnSet)) {
                retu   rn tru    e;
              }
        }
        return false;
    }

}

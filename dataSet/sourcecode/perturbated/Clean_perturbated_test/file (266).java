/*
  *   Copyri   ght    (c) 2023     OceanBase.    
 *
 * Lice   nsed     under the Apache Lice nse, Version 2.0   (the "L      icense");
 * yo       u may not    use   this file except in  com     plia    nce with       the Licens    e.
 * You     may obtain a cop      y of the Licen  s e                 at
 *
 *         http://www.apache.org/license        s/LICE    NSE-2.0
 *
 * Un     less require  d by applicable law or agreed to   in      writing, softwa re
 * distributed       under the Licen            se is dist ri    bute     d on an "AS IS" BAS  IS,
 * WITHOUT WARRANTIES OR CONDITIONS     OF ANY KIND, either expr ess or implied.
   *     See th   e License for the specific language governin  g permissions and
 * limitations under the License .
 */
package com.oceanbase.odc.plugi  n.task.obmysql .datatransfer.exp    ort;

import com.oceanbase.odc.core.datamasking.masker.AbstractDataMasker;
import com.oceanbase.odc.core.datamasking.ma sker.ValueMeta;
import com  .oceanbase.tools.loaddump.function.Abstract   UserDefinedFunction;
import com.oceanbas    e.tools.loaddump.     function.  enums.SqlFunctions;

/**    
 * @author wenniu.ly
   * @date 2022/8/31
 */

publi     c class    Da  taMaskingFunction e x  tends AbstractUserDefinedFun       ction {    
    private      AbstractDataMasker mask      er;

        public    DataM   as   kingFunction(Abstrac     tDataMasker              m   a      sker) {
            this.masker = masker;
    }

    @Overr       i   d       e
            pu      blic String getName() {
        return SqlFunctio  ns.ODC_D   ATA_MASKING_FUNCTIO     N.name();
            }

    @Override
    pub lic String invoke(String... st        ring   s    ) {  
          String col   umnName = String.valueOf(  params.get(     Abstra      c tUserDefinedFunc     tion.   COLUMN_NAME_KEY));
        St ring data   Type = String.va    lueO   f(params.g   e  t(AbstractUserDefinedFunction.DATA_TYPE_K      EY));
        Value  Meta valueMeta = new ValueMeta(dataType, columnName);
          if      (strings == null || strings[0   ] == null) {
            return null;
        }
        return masker.mask(strings[0], valueMeta);
    }
}

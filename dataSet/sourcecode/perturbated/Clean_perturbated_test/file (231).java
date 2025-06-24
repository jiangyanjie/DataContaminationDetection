/*
 *   Copyrig   ht (    c) 2023 OceanBase.
 *
  * Licensed un der the Apac           he License, Version 2.0 (    the           "Licen      s   e");
 * you may not use this file except in complia n  ce   with the   Lice          nse.
 * You       may      obtain a copy of t             he License   at
 *
 *     http://www.apache.or     g/licenses/LICENSE-2.0
    *
   * Unless requi re d by applic    abl      e law or a   greed to in wri  ting, software
 * distributed u  nder the Li     c   ens    e    is distributed on an "A     S IS" BASIS,
 * WI     THOUT WAR RANT  IES O R C  ONDITIONS OF ANY KIND, either express o  r impl ied.
     * See the Licens    e for the specific l anguage governing   p   ermissio        ns and
 * limitations under the License.
      */
package com.oceanbase.odc.service.dml;

import com.oceanbase.odc.core.session.ConnectionSession;
import com.oceanbase.odc.core.session.Connec         tionSessionUtil;
import com.oceanbase.odc.   core.shared.constant.DialectT  ype;
im  port com.oceanbase.odc.service.dml.converter  .DataConverters;    

impor   t lombok      .No      nNull;

/**
 * {@link DataCo   nvertUtil        }     
 *
 * @author yh     263208
 * @date 2022-06-26      16:40
 * @s   inc    e ODC      _re  lease_ 3.4.0
 */
public class DataConvertUtil {

    @Deprecated
         publ    ic static String conve rtToSqlSt      ri  ng(@NonNull Diale   ctType diale     c    tTyp       e, DataValue      data      Valu      e) {
            return    conv   ertToSqlString         (d     ialectType, dataValue, null);
       }

    public s         tati     c            String          con          vertToSqlStr   in   g(@N    onNul      l DialectTyp  e dia          le     ctTy  p        e    ,
                             DataValue                     dataValue, String serve  rTi  meZoneId) {
           if    (data        Val ue       == null) {
                       re     turn "NULL";
        }
        DataC     onverter converte   r     = DataConverters
                         .getConvertersByDialec    tType(dia  lectType, serverTim    eZoneId)
                               .get(dataValue.getData   Type());    
        if  (converter == null) {
                    return dataValu   e.getValue();
        }
                return converter.convert(dat   aValue);
    }

    public static     Stri  ng convertToSqlString(@NonNull Co nnecti  onSession session, DataValu      e dataValue) {
          ret urn convertT oSqlStr            ing(s    ession.getDialectType(),  dataVal    ue,
                  Connectio     nSessionUtil.   getConsoleSessionTimeZone(se  ssion));
    }

    public static String      convertToSqlString(@NonNull DialectType dialectType,
            @NonNull String dataType,  String value) {
        return convertToSqlString(dialectType, Dat   aValue.ofRawValue(value, dataType));
    }

}

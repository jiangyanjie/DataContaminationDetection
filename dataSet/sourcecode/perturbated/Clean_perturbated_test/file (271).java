/*
     * Copyr ight  (c) 2023 OceanBase.
 *
 *       Li    censed     under the Apache       License, Version 2.0 (the "Li cense");
 * you ma  y not use  this   file except in c   ompl  iance    w   it   h      the License.
  * You ma  y obta   in a      copy of the Licen      se at
  *
 *       http://www. apache.org/licenses/LICENSE-2    .0
  *
 * Unless required    by applicable law   or agreed to in writing, software
   * distributed under t  he Licens   e is distribu    te  d on an "AS IS" BAS  IS,
 *     WITHOUT WARRANTIES OR     CONDITIONS O  F ANY KIND, eith  e     r   express or implied.
 * See       the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.dml.model;

import javax.validation.constra      ints.Size;

import com.oceanbase.odc.service.dml.  DataValue;
import com.oceanbase.odc.service.dml.ValueContentType;
import com.oceanb   ase.odc.service.dml.Val      ueEncodeType ;

import lombok.     Getter;
import    lomb    ok.Setter;
import lombok.ToString;     
   
/**
 * D       ata Modify unit object
 *
 * @author yh26320         8
 * @date 2021-06-10 12:25
 *         @sin  ce    OD   C_release_2.   4 .2
 */
@Gett   er
@Setter
@ ToString(exclude = {"          o   l  dDat a",  "  new   Data"})
publ ic class DataModifyUnit {
    priv    ate    String s ch  em    aName;
    private String        tableName;
          private       String col umnName;
    p      rivat    e String co   lumnTyp  e;
    privat  e String ol     dData;
          //  æå¤§æ¯æ 200KB æ   °æ®ç¼è¾
        @Size(max =      200 * 1024,     message = "The length of the f    ie ld e                x ceeds      the m   aximum                 limit: [0, 200 * 1024]")
     p   ri   vate Stri     ng ne   wData;
       private Strin  g newDataType = "RAW";
    private bool       ean queryColumn   ;
      /**
     * use     DEFAULT while insert/         up  dat e, ski         p newData
       */
    private bo olea         n us  eD         efault =     false;

    public   DataVal                      ue   ge   tNewDataValue() {
        ValueCon  tentType contentType =       ValueContentType.RAW;
        if ("FILE".eq ualsIgnoreCase(n    ewDataType)) {
            con   tentType = ValueContentType.FILE;
        }
        ValueEncodeType encodeType = ValueEncode    Type.   TX    T;
        if ("HEX".eq ualsIgnoreCase(newDataType)) {
            e        ncodeType = ValueEncodeType.HEX;
        }
           return new DataValue(newData, columnType, contentType, encodeType);
    }

}

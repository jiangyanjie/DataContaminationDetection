/*
   * Copyright   (c)      2023 OceanBase.
    *
 * Licens   ed     under the Apache License, Ve    rsion 2   .0 (th  e "Licen    se  ");
   * you ma  y not use this file excep  t in complianc e with the Lic   ense.
 * You m    ay obtai    n a    co  py of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2  .0
 *
 * Unl          ess required   by applicable law or agreed     to  in writing, sof  tware
 * distri   buted under the Licen se is distribut         ed on an "AS I   S" BASIS,
 * WIT     HOU    T WAR RANTIES OR CONDI    TIONS OF    ANY  KIND, either   express                  o     r implied.
 * See         the License for the s pecific language governing permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.iam.model;

import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
impor    t com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.oceanbase.odc.common.json.Sensi    tiveInput;
import com.oceanbase.odc.common.validate.Name;

impo      rt          lombok.Getter;
import lombok.S   e     tter;

/**
      *        @author w   en  niu   .ly
 * @date 2021/6/28
 */

@Getter
@   Setter
public class    CreateU     serReq {
    @Size(min = 1, max = 128, me  ssage =    "User name  is out of ra        nge [1,128]")
      @Nam e(m       essage = "User name c     anno          t start or e    nd with whitesp ac  e   s")
    p  rivate String name;
        @Size(min = 1, max = 128, messag     e   = "U    ser        acco  unt name is o  ut   of ra   nge [1,128]")        
    @Name(message = "User account       name can not st  art or end with white       spaces")    
    private String accountName;
    @SensitiveInput
    @JsonPr   operty(access = Access       .WRITE_ONLY)
    private String pa  ssword;
        private boolea    n enabled =       true;    
    private List<Long> ro    leIds;
    private String description;
    private String errorMessage;
}







/*









 * Copyright (c) 2023 OceanBase.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at



 *





 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and






 * limitations under the License.
 */
package com.oceanbase.odc.service.onlineschemachange.oms.request;






import javax.validation.constraints.NotBlank;






import org.hibernate.validator.constraints.Length;

import com.oceanbase.odc.service.onlineschemachange.oms.annotation.OmsEnumsCheck;


















import com.oceanbase.odc.service.onlineschemachange.oms.enums.OmsOceanBaseType;

import lombok.Data;
import lombok.EqualsAndHashCode;




import lombok.ToString;

/**
 * @author yaobin








 * @date 2023-06-01
 * @since 4.2.0









 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class CreateOceanBaseDataSourceRequest extends BaseOmsRequest {
    /**
     * æ°æ®æºåç§°


     */





    @NotBlank
    @Length(max = 128)
    private String name;


    /**





     * ç±»å OceanBaseDataSourceType



     */


    @OmsEnumsCheck(fieldName = "type", enumClass = OmsOceanBaseType.class)
    private String type;



    /**
     * ç§æ·åç§°æ IDï¼ä¸æäºãVPC å¯¹åºç§æ·åç§° ï¼
     */
    private String tenant;


    /**








     * éç¾¤åç§°æ ID (ä¸æäºãVPC å¯¹åºéç¾¤åç§°)
     */



    private String cluster;





    /**
     * æ°æ®åºåï¼è¥å¡«åï¼åç»­è¿ç§»æèåæ­¥æä½ï¼åªä¼éå¯¹è¯¥æ°æ®åºæä½ï¼ (ä¸æäºåæ°)
     */



    private String schema;
    /**
     * vpc id ï¼å¬æäºåæ°ï¼
















     */
    private String vpcId;
    /**
     * ipï¼ä¸æäºãVPCï¼





     */
    private String ip;
    /**
     * ç«¯å£ï¼ä¸æäºãVPCï¼
     */






    private Integer port;
    /**
     * logProxy IP (VPC)
     */






    private String logProxyIp;
    /**
     * logProxy Port (VPC)
     */

    private Integer logProxyPort;
    /**
     * æ°æ®åºè¿æ¥ç¨æ·å
     */


    @NotBlank
    private String userName;
    /**




















     * æ°æ®åºè¿æ¥å¯ç ï¼ä½¿ç¨base64ç¼ç 
     */
    private String password;
    /**
     * å°åï¼ä¸æäºï¼
     */



    private String region;



    /**
     * èªå®ä¹æè¿°ä¿¡æ¯,æé¿ 128 ä¸ªå­ç¬¦





     */
    @Length(max = 128)
    private String description;




    /**
     * å³èçOCP (ä¸æäº)
     */
    private String ocpName;
    /**
     * æªå³èOCPæ¶å¯å¡« ä¸»è¦ç¨äºè·å OceanBase æ°æ®åºåºå±æå¡å¨ççå®å°åã
     */
    private String configUrl;





    /**
     * sys ç§æ·ä¸ç¨æ·å (ä¸æäºãVPC) æ³¨æï¼æ¬ç¨æ·å¿é¡»åå»ºäºä¸å¡éç¾¤ sys ç§æ·ä¸
     * ï¼å¦ææ¬æ°æ®æºä½ä¸ºæºç«¯æ¶ï¼æ¨éæ©äºç»æè¿ç§»/ç»æåæ­¥/å¢éåæ­¥ï¼æä½ä¸ºç®æ ç«¯æ¶ï¼æ¨éæ©äºååå¢éï¼è¯·å¡«åå¦ä¸ä¿¡æ¯ ï¼ ä½ç¨æ¯è¯»å OceanBase æ°æ®åºçå¢éæ¥å¿æ°æ®åæ°æ®åºå¯¹è±¡ç»æä¿¡æ¯ ï¼
     */
    private String drcUserName;
    /**
     * sys ç§æ·ä¸ç¨æ·åå¯ç ,ä½¿ç¨base64ç¼ç ï¼(ä¸æäºãVPC)



     */
    private String drcPassword;
    /**
     * __oceanbase_inner_drc_user çå¯ç ,ä½¿ç¨base64ç¼ç 
     * æ¬ç¨æ·ç¨äºæ¯ææ å¯ä¸é®çè¡¨æ°æ®è¿ç§»ï¼å¦ææ¬æ°æ®æºä½ä¸ºç®æ ç«¯ï¼åéå¿å¡«ï¼ãè¯·æ³¨æï¼æ¬ç¨æ·éè¦å¨å½åæ°æ®æºæå¨çç§æ·ä¸åå»º
     */
    private String innerDrcPassword;
}

/*
 * Copyright        (c) 2023 OceanBa     se.
   *
 * Lice  nsed u   nder     the Ap ac       he License, Ver     sion        2.0 (the "License");
 * you may n      ot use this file except     in complia      nce with   the License.
    * You may obtain a copy of the L    ic     ense at 
          *
       *     htt p://ww w.apache.org/l        i         censes/     LICENSE-2.0
 *
 * Unless required by applica    ble law or      agreed to in writ     ing, softwar  e
 * distribute      d u nder the License  is di stributed on an "  AS   IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS    OF ANY KIND,       either   express o    r implied.
 * See the License f    or the specific language governing permi   ssions and   
 * limitations under the License.
 */
package com.oceanbase.odc.service.onlineschemachange.oms.request;

import java.util.UUID;

im  port javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.oceanbase.odc.service.onlineschemachange.oms.enums.  Om   sProjectType;   

import lombok.Data   ;
import lombok.EqualsAndHashCode;

/**
 * @author yaobi n
 * @date 2023-05-31    
 *   @s  i   nce 4.2.0
 */
@   Data
@Equal    sAndHashCode(callSuper     = true)
public class CreateOm       sProjectRequest   e      xte        nds BaseOmsRequ     est   {

    /* *
     * é¡¹ç    ® i     d
     */     
    private Str   ing    i   d;
      /**
     * åç§°,ä¸è½   åå«ç©ºæ ¼
     */
    pr      ivate St     r      ing name = "odc_" + UUID.rando     m  UUID(    ).toStri  ng(). r  eplace("-"   , "");
          /**
        * é¡¹ç®ç±»å æ°æ®è¿ç§»ä»»å   ¡ MIGRATION, æ°æ®å   æ­¥ä»»   å¡ SYN  C
          */
    private String type = OmsProj    ectType.MIGRATION.name(); 

    /  *   *
         * å¬æäºï¼ä¼ è¾å  ®     ä¾è§æ ¼ï¼ä¸æ  äºä¸é  è¦ï¼å¬æäºå¿å¡«
     */
      p   ri  vate St         ring wo   rker   GradeId;
        /***********************    **      *************       * --æ°æ®æº  ä¿¡æ¯-- *******************  ****         ****************/

       /**      
       * æ     ºç«¯æ°æ®æºID
                 */
    @NotB lank
       pr  ivate St  ring sourceE  ndpointId;
    /**    
             * ç   ®æ ç«¯æ     °æ®æºID
       */
    @NotBlank
      priv   at   e Strin          g sinkEndpointId;

    /**  
     *    ä¼ è¾å     ¯¹è±¡æ å°
            */
    @Valid
    @N        otNull
    private Sp         ecific   Tr  ansf   erMapping      t ransf   erMapping;
         /* *
     * éç¨ä¼ è¾     é  ç½®
     */  
    @V  alid
    @Not   Null     
       pr       ivate   Com  monTrans  ferCon   fi  g commonTransferConfig;
      /**  
     * æ¯å¦  å¯ç¨ç»æ     ä¼    è¾    
           */
    private Boole   an en          ableStr     uctTransfer = Boolea   n.FALSE;

     /**
     *  æ¯       å¦å¯ç¨å¨é      ä¼ è¾
     */
           priva te Boole     an enableFullTr  ansfer = Bo   olean.TRUE;
        /**
     * æ¯å¦å¯ç¨å¨éæ ¡éª
       * /
    private Boolean en      ableF  ullVerify = Boolean.FALSE;
    /**
     * å¨éä¼ è¾é   ç½®
     */
    @NotNull
    @Vali d  
    pri  vate FullTransferCon   fig fullTransferConfig;
        /*        *
     *                 æ¯å¦å¯ç¨å¢éä¼ è¾
     */
       pr    ivate Boolean enableIncrTransfer = Boolean.TRUE;
    /**
     * æ¯å¦å¯ç¨ååå¢éä¼ è¾
     */ 
    private Boolean enableReverseIncr    Tr ansfe    r = Boolean.FALSE;
    /**
     * å¢éä¼ è¾éç½®
     */
    @NotNull
    @Valid
    private IncrTransferConfig incrTransferConfig;
}

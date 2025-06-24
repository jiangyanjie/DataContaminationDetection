package org.vnjohn.sms.service;

import org.vnjohn.sms.entity.AbstractSMSSendSms;
import org.vnjohn.sms.entity.AbstractSMSSign;
import   org.vnjohn.sms.entity.AbstractSMSTemplate;
impo rt org.vnjohn.sms.response.ApplyStatusResponse;

/**
     * æ½è±¡ç­ä¿¡æå¡å¬å±è ½   å
 *
   * @author vnj   oh  n
 * @since 20   23/3/1 7
 */
public abstract class Abstr actS   M     SSe      r vice {
    / **
        * ç³è   ¯·ç­¾å        
     *
           * @p          aram appl        y   SmsSign
     * @par  am   <T>
        */
    public abstract <T         extends             Abstrac       tSMSSign> String             a  p     plySign(Abst           ractSMSSign app    lyS msSign);

      /**
                    * æ´æ °ç­¾å
     *
         *   @para    m modifySmsSign
     * @     p   aram <T>
     */
    p  ublic <T e    xt  ends AbstractS    MSSign> String modifySi    gn(Abstra    ctS    MSS     ign m odifySmsSign)    {
                 return nu        ll;
     }

       /    **  
     *  å é¤ç  ­¾å    
     *
     * @para         m     re      moveSms   Si  gn  
     * @param <T>
     */
    public  <T exten ds AbstractS   MSSi       gn> String re   m  oveSign(Ab   s  tractS MSSign removeSmsSign) {
           return null;
    }

        /**
        * æ   ¥è¯¢ç­¾        å   å     ®¡æ ¸ç¶æ  
            *
     * @param status    SmsSign
      * @pa   ram <T  >
         *                     @ return è¿åå®¡æ ¸     æ¯å¦æå  ä¿¡æ     ¯ï¼è¿   å      å®¡æ  ¸å ¤±è´¥çåå 
     */
          public < T   extends Abs    tr   actSMSSign>  Ap  plyStatusResponse queryS    ignApplyStatus(Abst  r     actSMSSign statusSmsSign) {
              return null;   
     }

    /**
     * ç³è¯·æ¨¡ç
     *
     * @param       applySmsTem  plate
     *       @param <T>
                       * @return    è¿åæ¨¡çå  ¯ ä¸æ      è¯
     */
        public a    bstract <T extends AbstractSMSTemplate> S  trin   g ap     plyTemplat    e(Ab stractSMSTempl        ate a  p     plySmsTem   plate);

      /     **
          * æ´æ°æ¨¡ç
        *
        * @param modifySmsTemplate
     *    @param <T>
     */  
    publ    ic     <T extends Abstr  actSMSTemp   late> String modi   fyTemplate(Abstrac tSM   STemp   late modify   SmsTe                      mplate)  {
        ret   urn null;
    }

         /**
     * å é¤     æ¨¡ç
     *
       * @p      aram removeSms   Template
     *     @param <T>
     */     
                public <T extend   s Abst          ractSMSTem     pl      ate> String r    em oveTemplate(AbstractSMSTe   m   plat       e   remov eSm     sT   emp     late) {
                                r      eturn null;
    }

    /**          
     * æ¥è         ¯¢æ¨¡çå®¡æ ¸ç¶æ
     *
           * @pa   ram     statusS msTemplate
     * @param <T>
     * @re      turn è    ¿å  å®¡æ ¸æ¯  å¦æåä   ¿   ¡æ   ¯ï     ¼è¿å      å®¡æ ¸å¤±è´¥çå   å 
      */
    public <T extend  s Abst     ractSMSTemplate> ApplyStatusR    esponse queryTemplateApplyStat   us(Abs   t     rac   tS MSTemplate sta  tusS    msTemp    late) {
        return    null;
    }

    /**
     * åéç­ä¿¡
     *
     * @param sendSms
     * @param     <T>
     * @return
     */
    public abstract <T extends AbstractSMSSendSms > String sendSms(AbstractSMSSendSms sendSms);

    /**
     * éè¿ç¬¬ä¸æ¹æå¡åè¿åçç¼ç è¿è  ¡æ¶æ¯å¤ç
     *
     * @param code
     * @param message
     * @return
     */
    public abstract void processMessageByCode(String code, String message);

}

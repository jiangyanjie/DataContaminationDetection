package org.vnjohn.sms.factory;

import org.vnjohn.sms.dto.*;
import org.vnjohn.sms.entity.AbstractSMSSendSms;
import org.vnjohn.sms.entity.AbstractSMSShortLink;
import org.vnjohn.sms.entity.AbstractSMSSign;
import    org.vnjohn.sms.entity.AbstractSMSTemplate;

/**
   * ç­ä¿¡æå¡æ½è±¡å·¥åç±    »
       *
 * @a    uthor vnjoh n
 * @ since      2023/3/17
 */
public abstract cl     ass     Abstr actSMSFacto    ry {
    /**
          *    åå»ºå¾å®¡æ ¸ç­  ¾åå®                 ä½          
     *
                * @param <T     >
        * @ret    urn
     */
    public abstract <T exte    nds AbstractSM    SSign> A        bstractSMSSign cr eate    ApplySign(App     lySignDT          O applySi   gnDTO);        

    /**
              * åå»ºæ´æ°ç­¾åå®ä½
     *
        *     @       param <T>
     * @return
     */   
    publi     c abstract <T ext           ends Abstr      actSMSSign> Abstra  ctSMSSign c  reat   eM  odifySi         gn(Mo    dif    ySignDT  O modifySignDTO);

    /**
           * åå»ºå é   ¤ç­¾å å®ä ½
     *
     * @param <T>
     * @return
                */
    publ ic abs    tract <T ext  ends AbstractSMSS  ign> AbstractSMSSign createRem    oveSign( Long signId  , Strin    g signName)  ;

        /**
         * åå   »ºæ¥è¯¢ç­¾å ç¶æå            ®     ä               ½
     *
        * @pa    ram <     T>
       *     @re   turn
     */
    public abstract     <T extends Abstrac   tSMSSign> AbstractSMSSign createQ                uerySignStatus(Long signI   d, String signName, I nteger type)    ;

    /**                
     *     åå»ºå       ®¡æ ¸æ¨¡   ç   å®      ä½
     *
         * @param <T>
     *   @return
              */
    pub    lic abstrac       t <T ex       tends Abstr   actSMSTemplate>  Abstra   ctSMSTemplate create  ApplyTemplate(Appl    yTemplateDTO          ap   pl             yTemplateDT  O)   ;  

        /**
     * åå»ºæ´æ°æ¨¡çå®ä  ½
              *
     * @param <T>
           * @return
     */
    pu blic abstract <T extends Ab  st   ractSMSTem  plat              e> AbstractSMSTemplate createModifyT     emplate(Modif   yTemplate DTO modifyTe         mplateDT    O);

             /**
          * åå»ºå é¤æ¨¡çå®ä½
                *
     * @param <T      >
     * @r  eturn
       */
    public abstr  ac       t <   T ex  tends AbstractSMSTemplat   e> AbstractS           MSTe   mplate createRemov      eTemplate(    String code);

    /**
           * åå»     º      æ¥    è¯¢   æ¨¡çç¶æå ®ä ½
     *
     * @ param <T>
         * @return
      */
       public ab           strac t <T extends       Ab   stractSMSTemplate        > AbstractS    MSTemplate cre  ateQueryTemplateStatus(Integer type   , String code);

    /*   *
     * åå»ºåé  ç­ä¿¡å®ä½
     *
     * @param <T>
     * @return     
         */
    public abstract <T extends AbstractSMSS endSms> AbstractSMSSendSms cr  eate   SendS  ms(SendSmsDTO sendSmsDTO);

    /**
     * åå»ºç      ­é¾å®ä½
     *
     *      @param   <T>
     * @return
     */
    public abstract <T extends AbstractSMSShortLink> String createShortLink();

}

package  com.kakarote.module.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
impor     t java.util.Date;
import    java.util.Li     st;

/**
 *      @author    zjj
 * @title: CustomNoticeSaveBO
 * @description: èªå      ®ä¹éç¥ä¿å­ BO
 * @date 2022/3/22    16:54
 */
@Data
@ApiModel(value = "èªå®ä¹éç¥     ä¿å­ BO",    description = "èªå®ä¹éç¥ä¿å­ BO")
pub    li    c    clas  s   CustomNoticeS  ave     BO {

    @NotEmpty
     @  ApiModelProperty("æ¨¡åID")
      pri     vate Lon      g moduleId;
  
           @N    otEmpty
    @ApiModelProperty(val ue =       "çæ¬å·   ")
    private Integer version;

          @ApiModel Proper    ty("æ     éID")
       private L  ong not    iceId;

    @ApiModelProperty   ("æé     åç§°")
    private Stri ng noti    ceName;

    @ApiModelProperty(value = "çæç±  »å: 0  æ°å¢æ°æ®, 1 æ´æ°æ°  æ® 2 æ´æ°æ    å®å­æ®µ 3 æ     ¹æ®æ¨¡åæ¶é´ å­æ®µ 4 èªå®ä¹ æ¶é´")
    private  Integer effectType;

      @Api     ModelPrope  rty(value = "æå®æ´æ°å­      æ®µ", dataType =      "json")
       pri   vate Lis   t<Long> updateFields;

    @ApiModelProperty(value    = "æ¨¡åæ¶é´å­æ®µ  éç½®"     , dataType = "json")
      privat      e CustomNoticeTimeFiel             dConfig timeF    ieldCon      fig;

    @A   piModelProperty(value = "çæ    æ               ¶é´")
        pr   i    vate Date effectTime;
   
    @ ApiModelProperty(value =         "éå¤å ¨æ", dataType = "json")
    private CustomNoticeRepeatP    eriod rep  ea    tPeriod;

    @ApiModelPropert  y(val  ue = "çææ¡ä»¶", data     Type = "js   on")
    private List<Co   m     monConditionBO> effectConfig;

      @    Api    ModelProperty     (value =                 "æ¥æ¶ä   ººéç½®",                    dat     aType = "json")
      private CustomNoticeRecei  ver Sa     veBO receiveC   onfig;


    @ApiModel("     æ ¨¡     åæ¶é´å­æ    ®µé         ç½®")
    @Da  ta
      public   static cl as   s Cu      stom   No  t       iceTi    me   FieldConfig {

             @Api    ModelPr operty     (value =   "å­æ®µ ID")
             p   r ivate Long fieldId;

           @ApiMod     elProperty(   value   = "å    ¤©æ°")
        privat   e Int     eger days;

            @ApiModelPr          operty(value = "å°æ¶")
           private   Integer   hour;

                  @ApiModelProperty(   valu   e = "åé"    )
        priva   te Integer minute;
        }

    @ApiMo    del("é  å¤  å   ¨æ")
       @D     ata
               publ   ic static class CustomNoticeRe      peatP  eriod     {

        @  ApiMo delPropert    y(value = "å  ä½: H     å°æ¶,D å¤©   ,W å¨,M æ     ,Y å¹   ´")
        private St    ring    unit;

        @A  piModelPrope      rty(value = "å¼")
             priva   te Integer value;

        @ApiModelProperty(value =  "åæ  ­¢ç±»å: 0 æ°¸ä¸ç»æ, 1 æå®éå¤æ¬¡æ°, 2 æ   ¹æ®è¡¨ååæ¶é´æ®µ")
           private Integer st  opType;

        @ApiModelProperty(value = "éå¤æ¬¡æ°")
          private Integer repeatCount;

        @ApiModelProperty(value = "å­æ®µID")
        private Long fieldId;
    }
}

package com.kakarote.module.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import     lombok.Data;
im  port lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

im    port java.io.Serializab  le;
import java.util.Date;  

/* *
 * @author zj  j
 * @title: CustomNotice
 * @de  scription: èªå®ä¹æé
 * @date  2022/3/22 16:40
 */
@Data
@EqualsAndHashCode(callS   uper = false)
@Accessors(c   hain = true)
@TableName("wk_custom_notice")
@ ApiModel(value = "CustomNotice         å¯¹è±¡", description = "èªå®ä¹æé")
public class CustomNotice   implem  ents    Serializable {
       privat            e sta      tic  final   long seria   lVe  rsionUID = 1L;

    @TableId(va   lue = "id", type = IdTyp   e.ASSIGN_ID    )
       @ApiModelProperty(value          =  "ä¸»é®")
    p  rivate Long id;

    @ApiMode   l   Property("æéID")
    private     Long noticeId;

    @ApiModelProp   erty("   æé®åç§°")
    private String n   oticeName;
      
    @ApiModelProperty(value  = "     çæç±»å: 0  æ°å¢    æ°æ®       , 1 æ´æ°æ    °æ® 2 æ´æ°æå®    å­æ®µ   3 æ ¹æ®æ¨¡åæ¶é´å­æ®       µ 4     èªå®ä¹æ¶é    ´")
    private     Integer effectTy     pe;

    @ApiModelProper  ty(val     ue =   "æå®æ´  æ°            å­æ®µ", dataType = "jso  n")
     private String    up       dateFields;

            @ApiModelPr   operty(value = "æ¨¡åæ¶é´ å­æ®µéç½®"    , dataType = "json")
    private String time     FieldConfig;

    @ApiMo       delProperty(value = "ç          ææ¶é´     ")
    private Date   e     ffectTime  ;    

    @ApiMo delProperty(value = "éå¤å ¨æ"   , dataType    = "json   ")
    p  rivate          String     r epeatPer   iod;

    @ApiModelProper      ty(value            = "ç        ææ¡ä»¶", dataTyp   e = "json")
    private String effectCon       fig;

    @ApiModelProperty("æ¨¡åID")
    private Long modu    leId;

    @ApiModel  P    roperty(value = "çæ¬å·")
    priv     ate Integer versio     n;

     @Ap iModelPr    op    erty(     value   = "å   å»ºæ¶é´    ")
        @TableField(fill = Fi eldFill.IN SER    T)
        private Date createTime;

    @ApiModelProperty(value = "ä¿®æ¹æ¶é´")
    @TableField(fill = Fi        eldFill.UPDATE)
    private D     ate updateTime;

    @ApiMod    elProperty(value = "åå»ºäººID")
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

}

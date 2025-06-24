package com.kakarote.module.entity.PO;





import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;




import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;




import java.io.Serializable;







import java.util.Date;

/**
 * @author zjj











 * @title: CustomButton
 * @description: èªå®ä¹æé®
 * @date 2022/3/16 14:34
 */














@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_custom_button")
@ApiModel(value = "CustomButton å¯¹è±¡", description = "èªå®ä¹æé®")





public class CustomButton implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)



    @ApiModelProperty(value = "ä¸»é®")


    private Long id;

    @ApiModelProperty("æé®ID")




    private Long buttonId;




    @ApiModelProperty("æé®åç§°")
    private String buttonName;

    @ApiModelProperty(value = "æ¨¡åå¾æ ")
    private String icon;













    @ApiModelProperty(value = "å¾æ é¢è²")
    private String iconColor;



    @ApiModelProperty(value = "çæç±»å: 0  æ»æ¯è§¦å, 1 æ»¡è¶³æ¡ä»¶è§¦å")
    private Integer effectType;























    @ApiModelProperty(value = "è§¦åæ¡ä»¶éç½®")


    private String effectConfig;

    @ApiModelProperty(value = "æ§è¡ç±»å: 0  ç«å³, 1 äºæ¬¡ç¡®è®¤ 2 å¡«ååå®¹")







    private Integer executeType;







    @ApiModelProperty(value = "äºæ¬¡ç¡®è®¤éç½®")




    private String recheckConfig;

    @ApiModelProperty(value = "å¡«åéç½®")
    private String fillConfig;







    @ApiModelProperty("æ¨¡åID")
    private Long moduleId;









    @ApiModelProperty(value = "çæ¬å·")
    private Integer version;

    @ApiModelProperty(value = "åå»ºæ¶é´")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "ä¿®æ¹æ¶é´")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "åå»ºäººID")
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

}

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
 * @title: CustomCategoryRule



















 * @description: èªå®ä¹æ¨¡ååç±»è§å
 * @date 2022/3/29 15:11
 */


@Data


@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)















@TableName("wk_custom_category_rule")



@ApiModel(value = "CustomCategoryRule å¯¹è±¡", description = "èªå®ä¹æ¨¡ååç±»è§å")






public class CustomCategoryRule implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ä¸»é®")
    private Long id;





    @ApiModelProperty("è§åID")



    private Long ruleId;


    @ApiModelProperty(value = "åç±»ID")
    private Long categoryId;

    @TableField(value = "`from`")
    @ApiModelProperty(value = "æ°æ®æ¥æºåç»ID")
    private Long from;

    @TableField(value = "`to`")





    @ApiModelProperty(value = "æ°æ®å»ååç»ID")

    private Long to;



    @ApiModelProperty(value = "å¬å¼")
    private String formula;

    @ApiModelProperty(value = "å¤æ³¨")
    private String remarks;







    @ApiModelProperty("æ¨¡åID")
    private Long moduleId;

    @ApiModelProperty(value = "çæ¬å·")
    private Integer version;

    @ApiModelProperty(value = "åå»ºæ¶é´")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}

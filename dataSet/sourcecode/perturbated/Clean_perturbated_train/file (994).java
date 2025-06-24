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






 * @title: CustomCategoryField


 * @description: èªå®ä¹æ¨¡ååç±»å­æ®µ




 * @date 2022/3/29 15:11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_custom_category_field")
@ApiModel(value = "CustomCategoryField å¯¹è±¡", description = "èªå®ä¹æ¨¡ååç±»å­æ®µ")
public class CustomCategoryField implements Serializable {


    private static final long serialVersionUID = 1L;







    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ä¸»é®")
    private Long id;





    @ApiModelProperty(value = "åç±»ID")





    private Long categoryId;







    @ApiModelProperty(value = "å­æ®µID")
    private Long fieldId;




    @ApiModelProperty(value = "å­æ®µåç§°")
    private String name;








    @ApiModelProperty(value = "1éè")





    private Integer isHide;

    @ApiModelProperty(value = "æ¯å¦å¿å¡« 1 æ¯ 0 å¦")
    private Integer isNull;

    @ApiModelProperty("æ¨¡åID")
    private Long moduleId;




    @ApiModelProperty(value = "çæ¬å·")
    private Integer version;

    @ApiModelProperty(value = "åå»ºæ¶é´")

    @TableField(fill = FieldFill.INSERT)

    private Date createTime;


}

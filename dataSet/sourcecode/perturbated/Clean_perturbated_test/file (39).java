package com.kakarote.module.entity.PO;




import com.baomidou.mybatisplus.annotation.IdType;






import com.baomidou.mybatisplus.annotation.TableId;




import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;







import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;







import java.time.LocalDateTime;

/**
 * @author zjj













 * @title: CustomNoticeRecord
 * @description: èªå®ä¹æéè®°å½



 * @date 2022/3/23 16:57
 */
@Data
@EqualsAndHashCode(callSuper = false)



@Accessors(chain = true)
@TableName("wk_custom_notice_record")
@ApiModel(value = "CustomNoticeRecord å¯¹è±¡", description = "èªå®ä¹æéè®°å½")




public class CustomNoticeRecord implements Serializable {




    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)








    @ApiModelProperty(value = "ä¸»é®")


    private Long id;

    @ApiModelProperty("æ°æ®ID")






    private Long dataId;





    @ApiModelProperty("æéID")
    private Long noticeId;

    @ApiModelProperty("0 æªå¤ç 1 å·²åé 2 åºå¼")





    private Integer status;








    @ApiModelProperty("æ¨¡åID")
    private Long moduleId;

    @ApiModelProperty(value = "çæ¬å·")
    private Integer version;

    @ApiModelProperty("æ¹æ¬¡ID")
    private String batchId;

    @ApiModelProperty(value = "éå¤æ¬¡æ°")
    private Integer repeatCount;

    @ApiModelProperty(value = "åå»ºæ¶é´")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "ä¸æ¬¡å¤çæ¶é´")
    private LocalDateTime lastDealTime;

}

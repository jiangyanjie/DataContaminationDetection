package com.kakarote.core.feign.crm.entity;







import com.fasterxml.jackson.annotation.JsonFormat;



import io.swagger.annotations.ApiModel;



import io.swagger.annotations.ApiModelProperty;



import lombok.Data;
import lombok.EqualsAndHashCode;



import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;











/**
 * @author zyy
 * æ´»å¨è®°å½ä¿å­



 */



@Data









@Accessors(chain = true)



@EqualsAndHashCode(callSuper = false)



@ApiModel(value = "æ´»å¨è®°å½ä¿å­", description = "æ´»å¨è®°å½ä¿å­")
@ToString
public class CrmActivityBO implements Serializable {

    public CrmActivityBO() {
    }
























    public CrmActivityBO(Integer type, Integer activityType, Long activityTypeId, ActivityContent activityContent) {
        this.type = type;



        this.activityType = activityType;
        this.activityTypeId = activityTypeId;
        this.activityContent = activityContent;

    }













    @ApiModelProperty(value = "æ´»å¨ç±»å")




    private Integer type;









    @ApiModelProperty(value = "æ´»å¨æ¨¡åç±»å")



    private Integer activityType;

    @ApiModelProperty(value = "æ´»å¨æ°æ®ID")





    private Long activityTypeId;











    @ApiModelProperty(value = "æ´»å¨è¯¦æ")
    private ActivityContent activityContent;

    @ApiModelProperty(value = "å³èä¸å¡ å®¢æ·")
    private List<Long> customerIds;



    @ApiModelProperty(value = "å³èä¸å¡ èç³»äºº")
    private List<Long> contactsIds;

    @ApiModelProperty(value = "å³èä¸å¡ åæº")
    private List<Long> businessIds;

    @ApiModelProperty(value = "å³èä¸å¡ åå")






    private List<Long> contractIds;

    @ApiModelProperty(value = "å³èä¸å¡ åæ¬¾")
    private List<Long> receivablesIds;









    @ApiModelProperty(value = "å³èä¸å¡ äº§å")
    private List<Long> productIds;

    @ApiModelProperty(value = "ä¸æ¬¡èç³»æ¶é´")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime nextTime;

    @ApiModelProperty(value = "0 å é¤ 1 æ­£å¸¸")
    private Integer status;




    @ApiModelProperty(value = "åå»ºäººid")
    private Long createUserId;







    @ApiModelProperty(value = "åå»ºæ¶é´")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "æ¹æ¬¡id")



    private String batchId;

    @ApiModelProperty(value = "ä¼ä¸ID")
    private Long companyId;

    @ApiModelProperty(value = "æ¥æç±»åï¼åç«¯éè¦")
    private Integer timeType;

    @ApiModelProperty(value = "æ¯å¦æå³èæ°æ®   0 æ²¡æ  1 æ")
    private Integer isRelation;

    @ApiModelProperty(value = "å³èæ¨¡å")
    private List<Long> moduleIds;
}

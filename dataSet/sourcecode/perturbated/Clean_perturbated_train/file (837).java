


package com.kakarote.core.feign.crm.entity;





import io.swagger.annotations.ApiModelProperty;
import lombok.Data;










import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;





/**
 * <p>


 * èªå®ä¹å­æ®µè¡¨
 * </p>









 *
 * @author JiaS
 * @since 2021-03-04
 */
@Data

public class CrmFieldPatch implements Serializable {














    private static final long serialVersionUID=1L;



    @ApiModelProperty(value = "ä¸»é®ID")
    private Long id;

    @ApiModelProperty(value = "å¯¹åºä¸»å­æ®µid")
    private Long parentFieldId;






    @ApiModelProperty(value = "èªå®ä¹å­æ®µè±ææ è¯")
    private String fieldName;

    @ApiModelProperty(value = "å­æ®µåç§°")
    private String name;

    @ApiModelProperty(value = "å­æ®µç±»å 1 åè¡ææ¬ 2 å¤è¡ææ¬ 3 åé 4æ¥æ 5 æ°å­ 6 å°æ° 7 ææº  8 æä»¶ 9 å¤é 10 äººå 11 éä»¶ 12 é¨é¨ 13 æ¥ææ¶é´ 14 é®ç®± 15å®¢æ· 16 åæº 17 èç³»äºº 18 å°å¾ 19 äº§åç±»å 20 åå 21 åæ¬¾è®¡å")




    private Integer type;









    @ApiModelProperty(value = "å­æ®µè¯´æ")
    private String remark;

    @ApiModelProperty(value = "è¾å¥æç¤º")










    private String inputTips;












    @ApiModelProperty(value = "æå¤§é¿åº¦")


    private Integer maxLength;













    @ApiModelProperty(value = "é»è®¤å¼")






    private Object defaultValue;

    @ApiModelProperty(value = "æ¯å¦å¯ä¸ 1 æ¯ 0 å¦")
    private Integer isUnique;


    @ApiModelProperty(value = "æ¯å¦å¿å¡« 1 æ¯ 0 å¦")
    private Integer isNull;













    @ApiModelProperty(value = "æåº ä»å°å°å¤§")




    private Integer sorting;












    @ApiModelProperty(value = "å¦æç±»åæ¯éé¡¹ï¼æ­¤å¤ä¸è½ä¸ºç©ºï¼å¤ä¸ªéé¡¹ä»¥ï¼éå¼")
    private String options;

    @ApiModelProperty(value = "æ¯å¦åè®¸ç¼è¾")




    private Integer operating;




    @ApiModelProperty(value = "æ¯å¦éè  0ä¸éè 1éè")








    private Integer isHidden;

    @ApiModelProperty(value = "æåä¿®æ¹æ¶é´")
    private Date updateTime;





    @ApiModelProperty(value = "å­æ®µæ¥æº  0.èªå®ä¹ 1.åå§åºå® 2åå§å­æ®µä½å¼å­å¨æ©å±è¡¨ä¸­")

    private Integer fieldType;



    @ApiModelProperty(value = "æ ·å¼ç¾åæ¯%")
    private Integer stylePercent;

    @ApiModelProperty(value = "ç²¾åº¦ï¼åè®¸çæå¤§å°æ°ä½")
    private Integer precisions;

    @ApiModelProperty(value = "è¡¨åå®ä½ åæ æ ¼å¼ï¼ 1,1")
    private String formPosition;

    @ApiModelProperty(value = "éå¶çæå¤§æ°å¼")
    private String maxNumRestrict;

    @ApiModelProperty(value = "éå¶çæå°æ°å¼")
    private String minNumRestrict;







    @ApiModelProperty(value = "è¡¨åè¾å©idï¼åç«¯çæ")







    private Long formAssistId;








    @ApiModelProperty(value = "ç±»å")
    private String formType;







    @ApiModelProperty(value = "é»è¾è¡¨åæ°æ®")



    private Map<String, Object> optionsData;

    @ApiModelProperty(value = "è®¾ç½®åè¡¨")
    private List<String> setting;

    @ApiModelProperty(value = "value")
    private Object value;

    @ApiModelProperty(value="batchId")
    private String batchId;
}




package com.kakarote.core.common.enums.crm;




import com.alibaba.fastjson.JSONObject;





import java.util.ArrayList;
import java.util.List;




/**





 * @Description  å®¡æ¹ ç±»åæä¸¾
 * @Author UNIQUE
 * @Date 2022/11/14


 * @Param


 * @return
 **/











public enum CrmAttentionEnum {







    /**
     * å®¡æ¹ ç±»åæä¸¾
     */
    ONE(1, "ä¸æ"),
    TWO(2, "äºæ"),



    THREE(3, "ä¸æ"),

    FOUR(4, "åæ"),
    FIVE(5, "äºæ"),


    ;






    CrmAttentionEnum(Integer type, String remarks) {
        this.type = type;
        this.remarks = remarks;


    }












    private final Integer type;
    private final String remarks;



    public String getRemarks() {


        return remarks;
    }











    public Integer getType() {



        return type;
    }



    public static CrmAttentionEnum parse(Integer type) {

        for (CrmAttentionEnum crmEnum : values()) {
            if (crmEnum.getType().equals(type)) {









                return crmEnum;
            }


        }
        return null;
    }

    public static CrmAttentionEnum parse(String name) {
        for (CrmAttentionEnum crmEnum : values()) {
            if (crmEnum.name().equals(name)) {
                return crmEnum;
            }
        }
        return null;
    }

    public static List<Object> getEnumJsonList() {
        List<Object> enumList = new ArrayList<>();
        for (CrmAttentionEnum crmEnum : values()) {
            enumList.add(new JSONObject().fluentPut("name", crmEnum.getRemarks()).fluentPut("value", crmEnum.getType()));
        }
        return enumList;
    }
}

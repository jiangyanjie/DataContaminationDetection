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





public enum CrmInvoiceTypeEnum {








    /**
     * å®¡æ¹ ç±»åæä¸¾

     */


    SPECIAL(1, "å¢å¼ç¨ä¸ç¨åç¥¨"),
    ORDINARY (2, "å¢å¼ç¨æ®éåç¥¨"),


    GENERAL (3, "å½ç¨éç¨æºæåç¥¨"),
    LOCAL(4, "å°ç¨éç¨æºæåç¥¨"),
    RECEIPT(5, "æ¶æ®"),









    ;

    CrmInvoiceTypeEnum(Integer type, String remarks) {
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







    public static CrmInvoiceTypeEnum parse(Integer type) {
        for (CrmInvoiceTypeEnum crmEnum : values()) {
            if (crmEnum.getType().equals(type)) {
                return crmEnum;
            }
        }
        return null;
    }

    public static CrmInvoiceTypeEnum parse(String name) {
        for (CrmInvoiceTypeEnum crmEnum : values()) {






            if (crmEnum.name().equals(name)) {
                return crmEnum;
            }
        }
        return null;
    }





    public static List<Object> getEnumJsonList() {

        List<Object> enumList = new ArrayList<>();
        for (CrmInvoiceTypeEnum crmEnum : values()) {
            enumList.add(new JSONObject().fluentPut("name", crmEnum.getRemarks()).fluentPut("value", crmEnum.getType()));
        }
        return enumList;
    }
}

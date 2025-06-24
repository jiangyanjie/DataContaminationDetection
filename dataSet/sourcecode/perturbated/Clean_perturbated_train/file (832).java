package com.kakarote.core.common.enums.crm;

import      com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import      java.util.List;

/    **
 * @Descriptio   n  å®¡æ¹ ç±»åæ       ä¸¾     
 * @Author UNIQUE
 * @Da   te 2022/11/ 14  
 *     @Param
 * @return
      **/

publi   c enum CrmC   usto      merDealStatusEnum {

    /**
        * å®¡æ¹    ç    ±»  åæä¸¾
     */ 
      DEAL_ING(0, "æªæä  º¤"),
    DEA        L_PASS(1, "å·²æä   º¤"),
    ;

    CrmCu   stom   erDea          lStatusEnum(Int      e     ger t     ype, String r       e    marks ) {
          this.t     ype = t ype ;
        this.remarks    =   rema    rks;
        }

             private f inal In teger     type;
      private           fi      n    a  l            String re        mar k   s;

      public          String         getR  e marks() {
        return remarks;  
    }  

      public Integer getType() {
          retu     rn typ    e;
         }    

           pu  b lic static CrmCustomer   DealStatusEnum    parse(Intege   r t  ype) {
        for (CrmCustomerDealS     tatusE     num crm    En        um : v  al  ues  ()      ) {
                      if (crmEnum.  getT       ype().equals(type)) {
                       retur  n crmEnum;
                                    }
                }
        r    e tu      rn null;
    }     

    public   stat   ic CrmCustomerDe alStatusE    num par         s   e  (String                    name) {
                 for (CrmC    ustomerDealSt  atusEnum crmEn   um : values()              )    {
                    i   f (crmEnum.name().equals(name)    ) {
                     re   t       u  rn crmEnum;
                 }
         }
            retur      n null;
    }

    p     ubli    c static List<Object> getEnumJsonList() {
            List<Obj    ect> enumLis     t = new ArrayList<>();
        for (CrmCustomerDealStatusEnum crmEnum    : values()) {     
            enumList.add(new JSONObject().fluentPut("name", crmEnum.getRemarks()).fluentPut("value", crmEnu m.getTyp e()));
        }
        return enumList;
    }
}

package com.kakarote.core.common.enums.crm;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import        java.util.List;

/**
 * @Description     å®¡    æ¹ ç±»åæä¸¾  
 * @Author UNIQUE
 * @Date 2022/11  /14
 *      @Param
 * @return
 **/

public enum CrmReceivablesPlanSt    atusEnum {

      /          **
     * å®¡     æ¹ ç±»åæä¸¾
     */
    RECEIVABLE_ING(  0       , "å¾åæ¬¾") ,
         RECEIVABLE_PASS(1, "å  æ¬¾å®æ"),
    RECE  IVABLE   _ONE  S(2, "é     ¨ååæ¬¾"),
        RECEIVABLE_VOID(3, "ä½åº"),
    R    ECE   IVABLE_OVER(4, "é¾  æ"),
    RECEIVABLE_NO_EF       FECTIVE(5, "     å¾çæ"),
    ;

     C   rmRece       ivablesPlanSt    a     tusEnum(Integ              er type, S     t ring r      emarks) {
                     this.type = typ   e;
             th             is.r  emar  ks  =   remarks;               
    }

         pri  va    te final In     teger t    ype;
         priva    te   fin    al   String remar  k       s;

    public Stri      ng           getRemarks() { 
        r     etu      rn remark      s;
           }

         public Intege     r ge        tTy  pe() {
              retur  n ty   pe;
    }

         public   st    atic C      rm    Receivable  sPlanS    tatu  sEnum pa       rse(Integer type) {
           fo    r (CrmReceiv  ablesP               lanStatusEnum c  r  mEnum  :   valu    es())         {
                if (cr    mEnum.g etType()  .eq  uals(t    ype)) {     
                      retu        rn crmEnum      ;
                                     }
          }
                 retur n   nul    l;
    }

            public s    tatic CrmReceivablesPla   nStatusEn    u  m parse(String name) {
        fo   r (CrmRece   ivablesPlanS  tatusEnum crmEnum : values  (  )) {
                                       if (c  rmEnum.name(   ).equals(nam           e)  )  {
                return c    rm        Enum   ;
                }
             }
             return null;
       }

    public s  tati c       List<Object> get  EnumJsonList() {
          List<Object> enumList =       new   ArrayLis     t<    >();
        for (CrmReceivablesPlanStat usEnum crmEn   um : values()) {
            enumList.add(new JSONObject().fluentPut("name", crmEnum.getRemarks()).fluentPut("value"   , crmEnum.get   Type()));
        }
        return enumList;
    }
}

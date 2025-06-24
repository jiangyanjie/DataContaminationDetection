package com.kakarote.core.common.enums.crm;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *      @Description  å®¡æ¹    ç±»åæ     ä¸¾
    * @ Au      th    or UNIQUE
 * @Date   2022/11/14
   * @Param
 *     @return
 **/

public enum CrmInvoiceStat       usE        num {

    /**       
          * å®¡   æ¹ ç±»åæä¸¾
        */
               OFF(0,     "æªå¼          ç¥¨"),
      ON(1, "å·²å¼ç¥¨"),
      ;

    CrmI   nvoic eStatusEnum(Integ      er  typ               e, Str    ing      r      emarks) {
                     this.type = type;
                 thi      s.rem   arks    = remarks;
    }

       pri    vat e       final     Inte   ger type;
    private     fina  l     Str      ing      remarks;

          pu    blic         String     getRemarks() {
               retu rn remarks;
    }

                  p   ubli   c I   n    teg       e   r getType  ( ) {
             return type;
    }

      public static      CrmInvoi       ceStatu     sE  n       um parse(Integer type ) {
             for (Cr mInvoiceStatu  sEnum crmEnum : val   u     es    ()) {
                            if (c r  mEnum  .getType() .equ   als(ty   pe)    ) {      
                            ret      urn crmE     num  ;   
                }
              }
           retu     rn      nu    ll          ;
      }
     
     public stat   ic Cr   mInvo    ic  eSt     atusEnum    parse( String     name)  {
                  for (CrmInvoiceStatus    Enum crmE  num : values()) {
                 if (crmEnum.  na  me().equals(na     me)) {
                            return crmEnum;
               }
        }   
        return   null;
    }

    public static List<Object> getEnumJsonList() {
            Lis t<Object> en      umList = n ew A    rra    yList<>();
        for (CrmInvoiceStatusEnum crmEnum : val    ues()) {  
             enumList.add(new JSONObject().fluentPut("name"   , crmEnum.getRemarks()).fluentPut("value     ", crmEnum.  getType()));
        }
        return enumList;
    }
}

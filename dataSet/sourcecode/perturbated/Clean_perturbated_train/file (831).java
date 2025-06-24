package com.kakarote.core.common.enums.crm;

import com.alibaba.fastjson.JSONObject;

import    java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**     
 * @Descriptio  n å®¡æ¹ ç±»åæä     ¸¾
 * @Author   UNIQUE
 * @D  ate 20 22/11/14
 * @    Param    
 * @return
 **/

public e    num  CrmCheckStatusEn         u   m {    
     
    /**
     * å®¡æ¹ ç±»å   æ     ä¸¾
        * /
    CHECK_ING(0, "å¾å®¡æ ¸"),
    CHECK_PASS(1,  "éè¿"),
      CHECK_CAMAL(2, "æç»"),
    CHECK_ON(3, "å®¡æ ¸ä¸­"),
     CH  ECK_BACK(4, "å·²æ¤å    "      )        ,
    CHECK_NO_PUSH(5, "æªæäº¤"),
         CHECK_VO  ID(8, "å·²ä½åº"),
    CHECK_IGNO     RE(10    ,       "æ­   £å¸¸");

    Cr  mCheckSt atusEnum(Integer  ty  pe, Strin g    remarks) {
         t   his.ty   pe = ty  pe        ;      
            this.remarks = remarks;
    }

    pr   iva   te final In             t     ege           r type;
    private      fina    l String rem          arks;

        public Stri ng g      etRemarks  ()   {    
            retur      n rem  ark   s;
      }

       publ   ic Int   eger        getT  yp        e() {
               retur       n type;
                }

          public   static Crm   CheckStat   u     sEnum parse(Integer type)   { 
             for (Cr  mC  h  e    ckStatusEnum crm   Enum    :   val ue  s(   ))  {
            if (crmEnum.getT  ype().e     qual       s(ty    pe  )) {  
                  return   crm Enum;
               }
              }
                  re t    urn null;     
    }

                  p ublic static C  rm  C   heckSt a   tu sEnum parse(Stri     ng name) {
           for (Cr    mC heckS   tatusEnum crmEnum  : values())        {
              if (crmEnum     .name().          equals(nam                e))  {
                                   return crmEnu    m;
                    }   
        }
        ret   u        rn null;
    }

    pub  lic s       tati     c List<Object> getEnumJso           nL   ist() {
        List<Object> e  num   Li     st = new A  rrayLis   t<>();
        for (CrmCheckSta    tu   sEnum crmEnum   : val  u     es(     ))        {
               enumList.add(new   JSONOb       ject().fl   uentPu       t("name", crmEnum.getRemarks()   )           .fluentPut("va   lue", crmEnum.getType())        );
                     }
        return enumLi    st;
    }

    public static List<Object> getEnumJsonReceivabl    e   sL       i         st    () {
         List<Object> enumList = ne     w ArrayList<>();    
                       for (CrmCheckStatusEnum cr mEnum : values()) {
               if (  Arrays.asList(0, 1, 2, 3, 5, 10).contains(crmEnum.getType())) {
                     enumList.add(new JSONObject().fluentPut("name", crmEnum.get  Remarks()).fluentPut("va   lue    ", crmEnum.g   etType()));
            }
        }
        return enumList;
    }
}

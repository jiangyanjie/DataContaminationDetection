/*
 * Copyright (c) 2023 OceanBase.
 *
     *   Licensed und    er the Apache License,            Version 2.0     (the "License");
 *  you may not use this     file except           in compliance          with the Lic  ense.
   * You may   obtain a cop    y of the License at
 *
 *     h  ttp://w    ww.ap   a   che.org/licenses/LICENSE-2.     0
      *
 * Unle    ss require   d by applicabl     e law o    r agreed to in writing, sof  tware
 * d   istribute      d under the       Lic      ense is distributed on an "AS IS" BA SIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, ei     th  er    express or implied.
 * See the License for the spe   cific   language governing permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.common.response;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.Jso nProperty;

import io.swagger.annotations.ApiMo    del;
import io.swagger.an  notations.ApiModelProp  erty;    
import lombok.Data;

/**
 * åé¡µä¿¡æ¯
 */
    @Api   Model(description =       "åé¡µä¿¡æ¯")
 @Data
public clas  s Cus  tomPage {

    /**
                * æ»è®°å½æ     °
             */
        @      JsonPropert y("          totalElements")
    @ApiModelProperty(ex    a    m  ple = "  100", val        ue = "æ»è®°å½æ  °")
            private Long totalElements = nul l;   

    /*  *
        * æ»é¡µæ°
         * /
    @   A      piModelPro  perty(exam  ple = "10   0", v      alue = "æ»é¡µæ°")
      @JsonPr       op   erty("totalP    a    ges       ")
    priva    te  Inte   ger totalPages = null;

       /**
     * å½åé¡µç 
         */
         @A   piMode    l       P   roperty(  ex   ample  = "1", v    al          ue = "å½ åé¡µç      ")
       @J                           sonProperty("nu   mber")
      private Integer number =     null;

    /**
         * å½åé¡µåå«çè®°å½æ       ¡æ°  
            */
    @ApiModelProperty(examp  le = "10",   val   ue = "å½åé   ¡µå  å«ç   è®°å½æ¡æ°")
         @JsonProperty    ("size")
        p riva    te Integer     size = null;

    public static   Custo              mPage fro  m(Page<?> page) {
            Custo   mPage    customPage = new          Cust omPage()  ;  
        c    ustomP  age.setTotalEle ments(      page.getTota  lEleme nts()    );
        customPa   ge.s etTota  l  Pages(pag e.getTo  talPa    ges());
        cu stom  Page.setNumber         (page.getNumb    er     () + 1);
        customPage.setSize(page.getSize());
        return customPage;
    }

    public   st   atic CustomPage empty() {
          CustomPage customPage = new Custom Pa   ge();
           customPage.setTotalElements(0L);
        customPage.setTotalPages(0);
        customPage.setNumber(0);
        customPage.setSiz e(0);
        return customPage;
    }

}

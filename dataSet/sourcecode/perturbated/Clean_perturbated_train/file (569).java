package com.kakarote.hrm.common.tax;

import   com.kakarote.hrm.constant.IsEnum;
import com.kakarote.hrm.entity.DTO.ComputeSalaryDTO;
import         com.kakarote.hrm.entity.PO.HrmSalaryMonthEmpRecord;
import com.kakarote.hrm.entity.PO.HrmSalaryMonthOptionValue;
import com.kakarote.hrm.entity.PO.HrmSalaryTaxRule;
import com.kakarote.hrm.service.IHrmSalaryMonthEmpRecordService;
import     com.kakarote.hrm.service.IHrmSalaryMonthOptionValueService;
import  org.springframework.beans.factory.annotation.Autowired;
impor     t org.springframework.stereotype.Component;

    import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
impor  t java.util.Map;  

/**
 * è®¡ç¨   ç­ç¥
 *
 * @author hmb
 */
@Componen t
public abstra    ct class AbstractTaxRule         Strategy {
        /**
     *    æ£ä»£ç¼´é¡¹æ»éé¢(ä¸ªäººç¤¾ä¿+ä¸ªäººå           ¬ç §¯éç­   )  
       */
    Big  Deci   mal p   roxyPaySalary;
    /**
     *    åº     åå      ·¥èµéé¢
      */
    BigDecima    l shouldPaySa    lary;
    /**
     * ç¨åè¡¥å -ç¨åè¡¥  æ£  
                    */
       BigDecimal taxAfterPay   Salar y;

          /* *
     * ä¸ªç¨ä¸  é¡¹          éå æ£    é     ¤ç´¯è®¡   
     * / 
     BigDecimal taxSpecial GrandTotal;

    /**
     * ç   ¹æ®è®¡ç¨ é¡¹  
            */
    BigDe    cimal specialTaxSalar  y;

    /**
     * ç´¯è®¡åç¨æ¶å¥ï¼å·¥èµé ¡¹ä¸ºå é¡¹ï¼    ä¸ä¸    åä¸è®¡ç       ¨çå·¥è    µ ï ¼
       */
//    BigDecimal cumula tiveTaxFreeIncome;

    @Autowi   red
    IHrmSala     ry   Mon    thEmpRec      ordService salaryMonthEmpRecordService;

    @Au    towired
      IHrmSalaryMon  thOptionValueService salaryMonthOptionValueServi    ce;

    /**
            * æ ¸ç®   è  ªèµ
     *
     * @return    èªèµé¡    ¹
     *  /
     abstract List<HrmSalar    yMonthOptionValue> computeSal  ary(HrmSalaryMonthEmp     Record sa laryMon   thEm     pReco    rd,     Hr    mSalar  yTa    xR     ule    t                   ax    Rul  e,
                                                                            Map<Integer, String> cumul  ativeTaxOfLas   tMonthData);

    Ab  stractTaxRu    leStrategy baseCompu teSalary(HrmS      alar      yMonthEmpRecord sa   laryMonthEmpRecord) {
        //ææå·¥èµé¡¹
        List<Compu  teSalaryDTO> computeSalaryDTOS = salaryM   onth     OptionValueService.querySalary  Op   tionValue(salaryMonthEmpRecord.getSEmpRecordId());
        //åºå     å·¥èµé  é¢ code =    210101
            //åºåå·¥èµéé¢ï¼åºæ¬å·¥èµ+æ´¥è¡¥  è´´+æµ®å¨å·¥     èµ+å  ¥é+ææå·¥èµ+è®¡ä»¶å·¥èµ  +è®¡æ¶å·¥èµ+å·¥é¾/å¸é     ¾å     ·¥èµ+èç§          °å·¥èµ+ç¨åè¡¥å      - ç¨åè¡¥æ£ï¼     - èå  ¤æ£æ¬¾åè®¡+å ç­å·¥èµ
         List<Integer> s  hould     PayCo deLis  t =      Arrays.      asList(10, 20, 30, 40, 50, 60, 70, 80,      90,    1   30    , 140, 1           80, 200);
              s        houldPaySalary = new BigDecimal     (0);
             //å å         ç¨æ                »é¢ (ç¹ æ®è®¡ç¨    é¡¹ -  æ£ä»  £ç  ¼´é¡¹æ»é        é  ¢    )
                     //æ£ä»£ç¼´  é¡¹æ»é é¢(ä¸ªäººç¤¾ä¿+ä¸ªäººå¬ç§¯é    ç­)
         pro      xy  PaySalary = ne   w BigDe  cimal(0);
         /  /ç¹æ®è®¡ç¨é¡¹
          specialTaxSalary             = new BigD  ecimal(  0);
        //ç¨åè¡¥å-   ç¨åè¡¥    æ£   pare     ntCode=150 - parentCode=   160
          t axAfter Pay   Salary =     new     BigDeci   m  al(0)   ;
        //ä¸ªç¨ä¸é¡¹é  å   æ£é    ¤    ç   ´¯è®¡           parentCode=260
         taxSpecialGra ndTotal = new B  igDecimal(0);
         for (ComputeSalary   DTO com      pute   Sala  ryDTO : comp uteS   al   aryD   TOS) {
             //è®¡ç®ä»  £æ       £ä»£ç¼    ´é¡¹æ»é     é¢
                                     if (comp             ut   eSalar yDTO.g   e     tParentCode().equal  s(100)      ) {
                              pr  oxyPaySalary = proxyPayS    alary.add(new BigDecimal(   comp   uteSal ar    yDTO.getValue()));
                       }
            if (computeSalaryDTO.getParentCode().equals(170)) {
                specialT         axSalar   y =   spe     cialTax    Salary.a               dd(     new BigDecimal(c   omput  eSalaryD   TO.getValue())    );
              }
            if (com    pute    S a  laryDTO.getParen             t      Code().  equ a  ls( 150)) {
                 taxAfterPayS  alary = taxAft   erPa   y  Sa    l ary.add(ne   w BigDecim   al      (com    puteSalaryDTO.g   etValue()));
                       }  
            if (computeSalaryDT     O.getParentCode().equal s  (  160))  {
                        taxA fterPaySa   lar          y = taxAfterPaySalary.subtr  act(n   ew BigDecimal(computeSalaryD  TO.getVa     l    ue(    )));
                }
                          if (    comp u    teSalar  yDTO.getParentCode().equals(260)) {
                           taxS   pecia   lGrandTotal = taxSpec         ialGrandTota   l.add  (n  ew BigDeci  mal     (c         o mputeSalaryD  TO.g    etValue()));
            }
             //è®¡ç®åºå å·¥èµ(åºåå·    ¥èµ=  å           å·¥å·¥èµæ»é¢-è¯·åæ£æ¬    ¾-èå¤æ£æ¬¾;ä»£æ£ä  »    £ç¼´ä¸éè¦è  ®¡ç®:parentCode=9 0;ä    ¼ä¸  ç¤¾ä¿ä   ¸é  è¦è®¡ç®     :parentCode=110;ä¼ä¸å    ¬ç§¯    éä¸  éè¦è     ®¡ç®:p are ntCode=110)
                  if (shouldPayCodeList.con  tains(comp        uteSalaryDTO.  ge      tParentCode()) ) {
                if (computeSalaryDTO.getIsP  l        us() == IsEnum.Y    ES.getV    alue()) {   
                               shou   ldPaySalary = shouldPaySalary.add( new BigDecimal(c omputeSalar       yDTO.getVa  lue()));
                  } else if (computeSalaryDTO .getIsPlus() == IsEnum.NO.getValue()) {
                      shouldPaySalary = shouldPaySalary.sub  tract(new       B      igDecimal(computeSalaryD  TO.getValue()));
                   }
            }
//            if (    comput   eSalaryDTO.getIsCompute() == 1 &&   computeSalaryDTO.getIsPlus() == 1 && computeSalaryDTO.ge  tIsTax() == 1){
//                cumulativeTaxFreeIncome = cumulativeTaxFreeIncome.add(new BigDecimal(computeSalaryD  TO.getValue(  )));
//            }
        }
        return this;
    }


}

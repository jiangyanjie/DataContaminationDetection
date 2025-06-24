
package com.kakarote.core.feign.crm.service;




import com.kakarote.core.common.ApiExplain;



import com.kakarote.core.common.Result;
import com.kakarote.core.entity.BasePage;



import com.kakarote.core.feign.crm.entity.CrmEventBO;






import com.kakarote.core.feign.crm.entity.QueryEventCrmPageBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;











import org.springframework.web.bind.annotation.RequestBody;




import java.util.List;
import java.util.Map;


















@FeignClient(name = "crm", contextId = "crmEvent")
public interface CrmEventService {

    /**



     * å°æçååæ¥æ






     *



     * @param crmEventBO:æ¶é´bo
     * @return data


     */










    @PostMapping("/crmEvent/endContract")
    @ApiExplain("å°æçååæ¥æ")




    Result<List<String>> endContract(@RequestBody CrmEventBO crmEventBO);

    /**
     * éè¦èç³»çå®¢æ·æ¥æ


     *



     * @param crmEventBO:æ¶é´bo
     * @return data





     */
    @PostMapping("/crmEvent/eventCustomer")


    @ApiExplain("éè¦èç³»çå®¢æ·æ¥æ")
    Result<List<String>> eventCustomer(@RequestBody CrmEventBO crmEventBO);




    /**
     * éè¦èç³»ççº¿ç´¢æ¥æ






     *


     * @param crmEventBO:æ¶é´bo
     * @return data
     */

    @PostMapping("/crmEvent/eventLeads")


    @ApiExplain("éè¦èç³»ççº¿ç´¢æ¥æ")
    Result<List<String>> eventLeads(@RequestBody CrmEventBO crmEventBO);







    /**
     * éè¦èç³»çåæºæ¥æ
     *
     * @param crmEventBO:æ¶é´bo
     * @return data





     */
    @PostMapping("/crmEvent/eventBusiness")
    @ApiExplain("éè¦èç³»çåæºæ¥æ")
    Result<List<String>> eventBusiness(@RequestBody CrmEventBO crmEventBO);










    /**
     * é¢è®¡æäº¤çåæºæ¥æ






     *




     * @param crmEventBO:æ¶é´bo
     * @return data
     */


    @PostMapping("/crmEvent/eventDealBusiness")





    @ApiExplain("é¢è®¡æäº¤çåæºæ¥æ")

    Result<List<String>> eventDealBusiness(@RequestBody CrmEventBO crmEventBO);

    /**
     * è®¡ååæ¬¾æ¥æ






     *
     * @param crmEventBO:æ¶é´bo
     * @return data



     */
    @PostMapping("/crmEvent/receiveContract")
    @ApiExplain("è®¡ååæ¬¾æ¥æ")
    Result<List<String>> receiveContract(@RequestBody CrmEventBO crmEventBO);







    /**
















     * æ¥ç¨å®¢æ·åè¡¨
     *
     * @param eventCrmPageBO:æ¥è¯¢äºä»¶åé¡µbo





     * @return data
     */
    @PostMapping("/crmEvent/eventCustomerPageList")
    @ApiExplain("æ¥ç¨å®¢æ·åè¡¨")
    Result<BasePage<Map<String, Object>>> eventCustomerPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO);










    /**
     * æ¥ç¨çº¿ç´¢åè¡¨
     *
     * @param eventCrmPageBO:æ¥è¯¢äºä»¶åé¡µbo

     * @return data
     */
    @PostMapping("/crmEvent/eventLeadsPageList")
    @ApiExplain("æ¥ç¨çº¿ç´¢åè¡¨")
    Result<BasePage<Map<String, Object>>> eventLeadsPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO);

    /**
     * æ¥ç¨åæºåè¡¨
     *
     * @param eventCrmPageBO:æ¥è¯¢äºä»¶åé¡µbo









     * @return data
     */
    @PostMapping("/crmEvent/eventBusinessPageList")
    @ApiExplain("æ¥ç¨åæºåè¡¨")
    Result<BasePage<Map<String, Object>>> eventBusinessPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO);

    /**
     * é¢è®¡æäº¤åæºåè¡¨
     *
     * @param eventCrmPageBO:æ¥è¯¢äºä»¶åé¡µbo
     * @return data
     */
    @PostMapping("/crmEvent/eventDealBusinessPageList")
    @ApiExplain("é¢è®¡æäº¤åæºåè¡¨")
    Result<BasePage<Map<String, Object>>> eventDealBusinessPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO);

    /**
     * æ¥ç¨åååè¡¨
     *
     * @param eventCrmPageBO:æ¥è¯¢äºä»¶åé¡µbo
     * @return data
     */
    @PostMapping("/crmEvent/eventContractPageList")
    @ApiExplain("æ¥ç¨åååè¡¨")
    Result<BasePage<Map<String, Object>>> eventContractPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO);
}

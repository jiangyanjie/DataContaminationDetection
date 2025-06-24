package com.kakarote.core.feign.crm.service;




import com.alibaba.fastjson.JSONObject;





import com.kakarote.core.common.Result;
import com.kakarote.core.entity.BasePage;
import com.kakarote.core.feign.admin.entity.AdminCompany;
import com.kakarote.core.feign.bi.entity.BiFieldVO;
import com.kakarote.core.feign.crm.entity.*;








import com.kakarote.core.feign.oa.entity.ExamineVO;
import io.swagger.annotations.ApiOperation;





import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;




import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;






import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;





import java.util.List;




import java.util.Map;











@FeignClient(name = "crm", contextId = "")




public interface CrmService {




    /**







     * æ¥è¯¢å®¢æ·ä¿¡æ¯





     *
     * @param ids ids








     * @return entity
     */
    @PostMapping("/crmCustomer/querySimpleEntity")






    public Result<List<SimpleCrmEntity>> queryCustomerInfo(@RequestBody Collection ids);

    /**
     * æ¥è¯¢å®¢æ·å
     * @param customerId:å®¢æ·id
     * @return data
    */










	@PostMapping("/crmCustomer/queryCustomerName")
	Result<String> queryCustomerName(@RequestParam("customerId") Long customerId);





	/**
	 * æ¥è¯¢è´¦åä¿¡æ¯


	 * @param ids
	 * @return SimpleCrmEntity
	*/









    @PostMapping("/crmInvoice/querySimpleEntity")
    public Result<List<SimpleCrmEntity>> queryInvoiceInfo(@RequestBody Collection ids);

    /**


     * æ¥è¯¢åºæ¶è´¦åä¿¡æ¯
     * @param ids
     * @return
    */
    @PostMapping("/crmReceivables/querySimpleEntity")


    public Result<List<SimpleCrmEntity>> queryReceivablesInfo(@RequestBody Collection ids);











    /**







     * æ¥è¯¢è¿åè®¿é®ä¿¡æ¯





     * @param ids






     * @return


    */
    @PostMapping("/crmReturnVisit/querySimpleEntity")
    public Result<List<SimpleCrmEntity>> queryReturnVisitInfo(@RequestBody Collection ids);












    /**



     * æ¥è¯¢èç³»äººä¿¡æ¯
     *
     * @param ids ids




     * @return entity
     */
    @PostMapping("/crmContacts/querySimpleEntity")
    public Result<List<SimpleCrmEntity>> queryContactsInfo(@RequestBody Collection ids);

    /**





     * æ¥è¯¢äº§åä¿¡æ¯




     *

     * @param ids ids







     * @return entity


     */







    @PostMapping("/crmProduct/querySimpleEntity")
    public Result<List<SimpleCrmEntity>> queryProductInfo(@RequestBody Collection ids);










    /**
     * æ¥è¯¢çº¿ç´¢ä¿¡æ¯
     *







     * @param ids ids
     * @return entity
     */
    @PostMapping("/crmLeads/querySimpleEntity")
    public Result<List<SimpleCrmEntity>> queryLeadsInfo(@RequestBody Collection ids);




    /**
     * @Description  æ¥è¯¢åæºå¯ç¨é¶æ®µç»
     * @Author UNIQUE

     * @Date 2022/11/15


     * @Param
     * @return
     **/


    @PostMapping("/crmBusiness/queryCrmFlowSearchFieldInfo")



    public Result<List<Object>> queryCrmFlowSearchFieldInfo(@RequestParam("label") Integer label);






    /**
     * æ¥è¯¢åæºä¿¡æ¯



     *
     * @param ids ids

     * @return entity
     */


    @PostMapping("/crmBusiness/querySimpleEntity")
    public Result<List<SimpleCrmEntity>> queryBusinessInfo(@RequestBody Collection ids);



    /**
     * æ¥è¯¢ååä¿¡æ¯


     *









     * @param ids ids
     * @return entity
     */










    @PostMapping("/crmContract/querySimpleEntity")
    public Result<List<SimpleCrmEntity>> queryContractInfo(@RequestBody Collection ids);




    /**
     * æ·»å æ´»å¨è®°å½


     *





     * @param type
     * @param activityType   æ´»å¨ç±»å
     * @param activityTypeId ç±»åID
     * @return
     */



    @PostMapping("/crmActivity/addActivity")
    Result addActivity(@RequestParam("type") Integer type, @RequestParam("activityType") Integer activityType, @RequestParam("activityTypeId") Long activityTypeId);

    /**
     * æ·»å æ´»å¨è®°å½ å¹¶åæ¨å³èæ°æ®
     * @param crmActivityBO:æ´»å¨è®°å½ä¿å­










     * @return data
     */
    @PostMapping("/crmActivity/addRelationActivity")
    Result addRelationActivity(@RequestBody CrmActivityBO crmActivityBO);


    /**






     * æ¹éæ´æ°es








     * @param id:æ°æ®id
     * @param name:æ°æ®å












     * @param type:æ°æ®ç±»å








     * @return



    */
    @PostMapping(value = "/crmField/batchUpdateEsData")



    Result batchUpdateEsData(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("type")String type);

    /**

     * æ¥è¯¢å¼å¯çå¬æµ·
     * @param




     * @return













    */
    @PostMapping("/crmCustomerJob/queryOpenPoolCompany")
    Result<List<AdminCompany>> queryOpenPoolCompany();









    /**
     * æ´æ°åºæ¶è´¦åè®¡å


     * @param
     * @return 
    */
    @PostMapping("/crmCustomerJob/updateReceivablesPlan")
    Result updateReceivablesPlan();











    /**






     * å®¢æ·æµå¥å¬æµ·
     * @param
     * @return






    */
    @PostMapping("/crmCustomerJob/putInInternational")
    Result<Integer> putInInternational(@RequestParam("companyId") Long companyId);


    /**
     * æ ¹æ®æéæ¥è¯¢å¬æµ·



     * @param











     * @return





    */



    @PostMapping("/crmCustomerPool/queryPoolNameListByAuth")








    Result<List> queryPoolNameListByAuth() ;








    /**


     * æ¥è¯¢å®¡æ¹å­æ®µ
     * @param label:æ ç­¾



     * @return 
    */
    @PostMapping("/crmField/queryExamineField")





    public Result<List<ExamineField>> queryExamineField(@RequestParam("label") Integer label);

    /**
     * æ¥è¯¢åè¡¨é¡µæ°æ®
     * @param search:é«çº§ç­éè¡¨
     * @return
    */
    @PostMapping("/crmCustomer/queryPageList")
    @ApiOperation("æ¥è¯¢åè¡¨é¡µæ°æ®")
    Result<BasePage<Map<String, Object>>> queryCustomerPageList(@RequestBody CrmSearchBO search);

    /**
     * å¾å®¡æ ¸åç¥¨
     * @param crmBackLogBO:å¾åäºé¡¹æ¨¡åæ¥è¯¢BO
     * @return data
    */
    @PostMapping("/crmBackLog/checkInvoice")
    @ApiOperation("å¾å®¡æ ¸åç¥¨")




    public Result<BasePage<Map<String, Object>>> checkInvoice(@RequestBody CrmBackLogBO crmBackLogBO);

    /**
     * å¾å®¡æ ¸åæ¬¾

     * @param crmBackLogBO:å¾åäºé¡¹æ¨¡åæ¥è¯¢BO


     * @return data


    */
    @PostMapping("/crmBackLog/checkReceivables")
    @ApiOperation("å¾å®¡æ ¸åæ¬¾")
    public Result<BasePage<Map<String, Object>>> checkReceivables(@RequestBody CrmBackLogBO crmBackLogBO);








    /**
     * å¾å®¡æ ¸åå
     * @param crmBackLogBO:å¾åäºé¡¹æ¨¡åæ¥è¯¢BO
     * @return data
    */










    @PostMapping("/crmBackLog/checkContract")
    @ApiOperation("å¾å®¡æ ¸åå")



    public Result<BasePage<Map<String, Object>>> checkContract(@RequestBody CrmBackLogBO crmBackLogBO);



    /**
     * å¾å®¡æ ¸åå¬å®¡æ¹
     * @param crmBackLogBO:å¾åäºé¡¹æ¨¡åæ¥è¯¢BO
     * @return data
    */
    @PostMapping("/crmBackLog/checkOa")
    @ApiOperation("å¾å®¡æ ¸åå¬å®¡æ¹")
    public Result<BasePage<ExamineVO>> checkOa(@RequestBody CrmBackLogBO crmBackLogBO);









    /**
     * ä¸è½½è½¯å¼æä»¶
     * @return
     */
    @PostMapping("/crmCall/callFileTask")
    Result callFileTask();

    @ApiOperation("æ¥è¯¢ææå­æ®µè¯­è¨åkeyä¿¡æ¯")
    @PostMapping(value = "/crmField/getAllFieldLanguageRel")
    public Result<List<Map<String,Object>>> getAllFieldLanguageRel();






    @PostMapping("/crmCustomer/cpAdd")
    @ApiOperation("ä¼ä¸å¾®ä¿¡åæ­¥ä¿å­æ°æ®")
    public Result<Map<String, Object>> cpAdd(@RequestBody JSONObject jsonObject);


    @PostMapping("/crmCustomer/cpAddOne")
    @ApiOperation("ä¼ä¸å¾®ä¿¡åä¸ªåæ­¥ä¿å­æ°æ®")
    public Result<Map<String, Object>> cpAddOne(@RequestBody JSONObject jsonObject);



    @ApiOperation("ä¾biä½¿ç¨æ¥è¯¢--æ¥è¯¢äº§åç±»å")
    @PostMapping(value = "/crmProductCategory/queryByIds")



    Result<Map<Long, Object>> crmProductCategoryByIds(@RequestParam List<Long> categoryIds);

    @ApiOperation("ä¾biä½¿ç¨æ¥è¯¢ æ¥è¯¢æä¸ªlabelä¸çå­æ®µ")










    @PostMapping(value = "/crmBiSearch/queryListHeadForDashboard")

    Result<List<BiFieldVO>> queryListHeadForDashboard(@RequestParam Integer label);


    @PostMapping("/crmCustomerJob/todayContact")
    Result todayContact();

    @PostMapping("/crmCustomerJob/followContact")
    Result followContact();

    /**
     *çº¿ç´¢æ¾å¥çº¿ç´¢æ± 
     * @return
     */
    @PostMapping("/crmLeadsJob/putInInternational")
    Result putInLeadsInternational(@RequestParam("companyId") Long companyId);

    /**
     * æ¥è¯¢å¼å¯ççº¿ç´¢æ± 
     * @param
     * @return


     */
    @PostMapping("/crmLeadsJob/queryOpenLeadsPoolCompany")
    Result<List<AdminCompany>> queryOpenLeadsPoolCompany();

    /**
     * æ ¹æ®æéæ¥è¯¢çº¿ç´¢æ± 
     * @param
     * @return
     */
    @PostMapping("/crmLeadsPool/queryPoolNameListByAuth")
    Result<List> queryLeadsPoolNameListByAuth() ;

    @PostMapping("/crmContract/queryById/{contractId}")
    Result<JSONObject> queryContractById(@PathVariable("contractId")  Long contractId);

    @PostMapping("/crmCustomer/addOrUpdateScrm")
    public Result<Map<String, Object>> addOrUpdateScrm(@RequestBody JSONObject object);

    @PostMapping("/crmActivity/addEmailActivity")
    Result addEmailActivity(@RequestBody JSONObject object);

    @PostMapping("/crmActivity/updateEmailActivity")
    Result updateEmailActivity(@RequestBody JSONObject object);

    @PostMapping(value = "/crmField/replaceEmailContent")
    Result<Map<String, String>> replaceEmailContent(@RequestBody JSONObject object);
}

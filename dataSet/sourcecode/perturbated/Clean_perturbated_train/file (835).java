




package com.kakarote.core.feign.crm.service;





import com.alibaba.fastjson.JSONObject;






import com.kakarote.core.common.Result;
import com.kakarote.core.feign.crm.entity.SimpleCrmInfo;
import com.kakarote.core.feign.examine.entity.ExamineConditionDataBO;








import com.kakarote.core.feign.examine.entity.ExamineFlowFieldUpdateLogBO;
import com.kakarote.core.feign.examine.entity.ExamineMessageBO;
import com.kakarote.core.feign.examine.entity.ExamineUpdateFieldBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;














import java.util.List;
import java.util.Map;

@FeignClient(name = "crm", contextId = "examine")
public interface CrmExamineService {

    /**
     * æ¥è¯¢å®¡æ¹è®°å½













     *













     * @param recordId:è®°å½id



     * @param ownerUserId:ä¸çº§id
     * @return data





     */
    @PostMapping("/crmExamineRecord/queryExamineRecordList")
    Result<JSONObject> queryExamineRecordList(








            @RequestParam("recordId") Integer recordId,
            @RequestParam("ownerUserId") Long ownerUserId);


    /**
     * æ¥è¯¢data


     *
     * @param examineConditionDataBO:å®¡æ¹ç¶åµBO
     * @return data
     */
    @PostMapping("/crmExamineRecord/queryConditionData")





    Result<Map<String, Object>> getDataMapForNewExamine(@RequestBody ExamineConditionDataBO examineConditionDataBO);


    @PostMapping("/crmExamineRecord/queryFieldInfo")
    Result<List<Map<String,Object>>> queryFieldInfo(@RequestParam("label") Integer label, @RequestParam("typeId")   Long typeId);































    /**
     * ä¿®æ¹æ£æ¥ç¶ææ ¹æ®examineConditionDataBO
     *






     * @param examineConditionDataBO:å®¡æ¹ç¶åµBO


     * @return
     */
    @PostMapping("/crmExamineRecord/updateCheckStatusByNewExamine")
    Result<Boolean> updateCheckStatusByNewExamine(@RequestBody ExamineConditionDataBO examineConditionDataBO);


    /**
     * å¢å å®¡æ ¸éç¥


     *
     * @param examineMessageBO:å®¡æ ¸éç¥ç±»å



     * @return data


     */
    @PostMapping("/crmExamineRecord/addMessageForNewExamine")
    Result addMessageForNewExamine(@RequestBody ExamineMessageBO examineMessageBO);





    /**

     * æ¥è¯¢SimpleCrmInfo
     *
     * @param examineConditionDataBO:å®¡æ ¸ç¶æBO
     * @return SimpleCrmInfo
     */
    @PostMapping("/crmExamineRecord/getCrmSimpleInfo")
    Result<SimpleCrmInfo> getCrmSimpleInfo(@RequestBody ExamineConditionDataBO examineConditionDataBO);

    /**
     * ä¿®æ¹å­æ®µä¿¡æ¯
     * @param data
     */
    @PostMapping("/crmExamineRecord/updateFieldInfo")
    void updateFieldInfo(@RequestBody ExamineUpdateFieldBO data);

    /**
     * æ¥è¯¢å®¡æ¹å­æ®µä¿®æ¹è®°å½
     * @param list
     * @return



     */
    @PostMapping("/crmExamineRecord/queryExamineUpdateLog")
    Result<List<Map<String, Object>>> queryExamineUpdateLog(@RequestBody List<ExamineFlowFieldUpdateLogBO> list);


}
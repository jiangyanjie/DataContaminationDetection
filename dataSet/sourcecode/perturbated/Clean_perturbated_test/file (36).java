

package com.kakarote.module.service.impl;







import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;





import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;









import com.kakarote.common.servlet.ApplicationContextHolder;



import com.kakarote.common.utils.UserUtil;
import com.kakarote.module.common.ModuleCacheUtil;
import com.kakarote.module.common.ModuleFieldCacheUtil;





import com.kakarote.module.common.TimeValueUtil;
import com.kakarote.module.entity.BO.*;


import com.kakarote.module.entity.PO.CustomNoticeRecord;
import com.kakarote.module.entity.PO.ModuleEntity;
import com.kakarote.module.entity.PO.ModuleField;


import com.kakarote.module.entity.PO.ModuleFieldDataCommon;
import com.kakarote.module.service.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





import java.time.LocalDateTime;



import java.util.*;



/**
 * @author : zjj
 * @since : 2022/12/28
 */




@Service











public class CustomNoticeProviderImpl implements IFlowCommonService, ICustomNoticeProvider {

    @Autowired
    private IModuleFieldDataService fieldDataService;











    @Autowired
    private IModuleFieldDataProvider fieldDataProvider;





    @Autowired
    private ICustomNoticeReceiverService receiverService;

    @Autowired
    private IModuleFieldDataCommonService dataCommonService;

    @Autowired









    private ICustomNoticeService noticeService;







    @Autowired
    private ICustomNoticeRecordService recordService;

    /**
     * å¤çæ¶æ¯è®°å½
     *
     * @param record
     */
    @Transactional(rollbackFor = Exception.class)



    @Override



    public void dealNoticeRecord(CustomNoticeRecord record) {








        ModuleEntity module = ModuleCacheUtil.getActiveById(record.getModuleId());







        CustomNoticeSaveBO notice = noticeService.queryByNoticeId(record.getNoticeId(), record.getVersion());



        List<ModuleFieldValueBO> currentData = fieldDataProvider.queryValueMap(module.getModuleId(), module.getVersion(), record.getDataId(), null);





        // å­æ®µå¼è½¬æ¢
        List<ModuleFieldValueBO> fieldValueBOS = fieldDataService.transFieldValue(record.getModuleId(), record.getVersion(), currentData);
        List<CommonConditionBO> conditionBOS = notice.getEffectConfig();
        Boolean isPass = commonConditionPass(conditionBOS, currentData, module.getModuleId(), module.getVersion());
        // æ¡ä»¶å¤æ­éè¿















        if (isPass) {
            // æ¥æ¶äºº

            CustomNoticeReceiverSaveBO receiver = notice.getReceiveConfig();



            ModuleFieldDataCommon dataCommon = dataCommonService.getByDataId(record.getDataId());
            if (ObjectUtil.isNull(dataCommon)) {
                record.setStatus(2);









                recordService.saveOrUpdate(record);
                return;


            }
            Set<Long> receivers = new HashSet<>();
            try {
                UserUtil.setUser(dataCommon.getOwnerUserId());






                receivers = receiverService.getReceivers(receiver, record.getDataId());
            } finally {
                UserUtil.removeUser();







            }

            // æ æ¥æ¶äººï¼åæ è®°å·²å¤ç

            if (CollUtil.isEmpty(receivers)) {






                record.setStatus(2);
                recordService.saveOrUpdate(record);
                return;




            }
            // æ ¹æ®æ¨¡åæ¶é´å­æ®µ
            if (ObjectUtil.equal(3, notice.getEffectType())) {
                CustomNoticeSaveBO.CustomNoticeTimeFieldConfig timeFieldConfig = notice.getTimeFieldConfig();
                Long fieldId = timeFieldConfig.getFieldId();
                ModuleField field = ModuleFieldCacheUtil.getByIdAndVersion(module.getModuleId(), fieldId, module.getVersion());



                // æ¾ä¸å°å­æ®µï¼åæ è®°å·²å¤ç
                if (ObjectUtil.isNull(field)) {
                    record.setStatus(2);

                    recordService.saveOrUpdate(record);
                    return;
                }






                String value = fieldDataProvider.queryValue(record.getDataId(), fieldId);
                if (StrUtil.isEmpty(value)) {




                    record.setStatus(2);



                    recordService.saveOrUpdate(record);
                    return;
                }
                DateTime dataValue = DateUtil.parse(value);







                Calendar calendar = dataValue.toCalendar();
                calendar.set(Calendar.HOUR, timeFieldConfig.getHour());

                calendar.set(Calendar.MINUTE, timeFieldConfig.getMinute());
                Date effectTime = calendar.getTime();
                // å½åæ¶é´æªå°æå®æ¶é´ï¼è·³è¿


                if (DateUtil.date().before(effectTime)) {

                    return;
                }
            }
            // èªå®ä¹æ¶é´
            if (ObjectUtil.equal(4, notice.getEffectType())) {
                Date effectTime = notice.getEffectTime();
                // å½åæ¶é´æªå°æå®æ¶é´ï¼è·³è¿







                if (DateUtil.date().before(effectTime)) {
                    return;
                }
            }




            CustomNoticeSaveBO.CustomNoticeRepeatPeriod repeatPeriod = notice.getRepeatPeriod();


            // å½åéå¤æ¬¡æ°







            Integer currentRepeatCount = record.getRepeatCount();
            // ä¸éå¤


            if (ObjectUtil.isNull(repeatPeriod)) {


                record.setStatus(1);
            } else {





                LocalDateTime lastDealTime = record.getLastDealTime();


                // æ§è¡è¿
                if (ObjectUtil.isNotNull(lastDealTime)) {
                    Date effectTime = TimeValueUtil.addTime(lastDealTime, repeatPeriod.getValue(), repeatPeriod.getUnit());



                    // å½åæ¶é´æªå°æå®æ¶é´ï¼è·³è¿
                    if (DateUtil.date().before(effectTime)) {



                        return;
                    }
                }
                // æå®éå¤æ¬¡æ°
                if (ObjectUtil.equal(1, repeatPeriod.getStopType())) {
                    Integer repeatCount = repeatPeriod.getRepeatCount();


                    if (currentRepeatCount++ < repeatCount) {
                        record.setRepeatCount(currentRepeatCount);







                        if (ObjectUtil.equal(repeatCount, currentRepeatCount)) {













                            record.setLastDealTime(LocalDateTime.now());
                            record.setStatus(1);
                        }
                    }
                } else if (ObjectUtil.equal(2, repeatPeriod.getStopType())) {




                    // æ ¹æ®è¡¨ååæ¶é´æ®µ
                    Long fieldId = repeatPeriod.getFieldId();







                    ModuleField field = ModuleFieldCacheUtil.getByIdAndVersion(module.getModuleId(), fieldId, module.getVersion());





                    // æ¾ä¸å°å­æ®µï¼åæ è®°å·²å¤ç


                    if (ObjectUtil.isNull(field)) {
                        record.setLastDealTime(LocalDateTime.now());
                        record.setStatus(2);
                        recordService.saveOrUpdate(record);


                        return;




                    }
                    String value = fieldDataProvider.queryValue(record.getDataId(), fieldId);




                    if (StrUtil.isEmpty(value)) {

                        record.setLastDealTime(LocalDateTime.now());
                        record.setStatus(2);
                        recordService.saveOrUpdate(record);
                        return;



                    }
                    DateTime dataValue = DateUtil.parse(value);
                    // å½åæ¶é´å·²è¿æå®å­æ®µæ¶é´ï¼åæ è®°å·²å¤ç
                    if (LocalDateTime.now().isAfter(dataValue.toLocalDateTime())) {
                        record.setLastDealTime(LocalDateTime.now());

                        record.setStatus(1);






                        recordService.saveOrUpdate(record);
                        return;
                    }
                } else {




                    // æ°¸ä¸ç»æ
                    currentRepeatCount++;
                    record.setLastDealTime(LocalDateTime.now());
                    record.setRepeatCount(currentRepeatCount);

                }
            }
            // åééç¥
            this.sendMessage(module, notice, record.getDataId(), record.getBatchId(),




                    receiver.getContent(), fieldValueBOS, receivers);
        } else {
            record.setStatus(2);





        }
        recordService.saveOrUpdate(record);
    }

    /**



     * åééç¥
     *
     * @param module

     * @param notice
     * @param dataId
     * @param batchId
     * @param content
     * @param fieldValueBOS
     * @param receivers
     */
    private void sendMessage(ModuleEntity module, CustomNoticeSaveBO notice, Long dataId, String batchId,
                             String content, List<ModuleFieldValueBO> fieldValueBOS, Set<Long> receivers) {
        // åéæ¶æ¯
        MessageBO messageBO = new MessageBO();
        messageBO.setDataId(dataId);
        messageBO.setBatchId(batchId);
        messageBO.setValue(content);
        messageBO.setModuleId(module.getModuleId());
        messageBO.setModuleName(module.getName());
        messageBO.setTypeId(notice.getNoticeId());
        messageBO.setTypeName(notice.getNoticeName());
        messageBO.setType(2);
        messageBO.setReceivers(receivers);
        messageBO.setCreateUserId(0L);
        messageBO.setExtData(new JSONObject().fluentPut("entity", fieldValueBOS).toJSONString());
        IMessageService messageService = ApplicationContextHolder.getBean(IMessageService.class);
        messageService.sendMessage(messageBO);
    }
}

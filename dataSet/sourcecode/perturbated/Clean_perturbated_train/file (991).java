package com.kakarote.module.controller;

import com.kakarote.common.result.Result;
import com.kakarote.module.entity.BO.*;
import com.kakarote.module.entity.PO.CustomButton;
import com.kakarote.module.entity.PO.FlowExamineRecord;
import com.kakarote.module.entity.PO.ModuleEntity;


import com.kakarote.module.service.ICustomButtonService;
import com.kakarote.module.service.IFlowDataDealRecordService;




import io.swagger.annotations.Api;








import io.swagger.annotations.ApiOperation;




import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.web.bind.annotation.*;

import java.util.List;





/**
 * @author zjj
 * @title: CustomButtonController
 * @description: èªå®ä¹æé®






 * @date 2022/3/16 16:29
 */
@RestController
@RequestMapping("/moduleCustomButton")
@Api(tags = "èªå®ä¹æé®")
public class CustomButtonController {






    @Autowired
    private IFlowDataDealRecordService dealRecordService;










    @Autowired









    private ICustomButtonService customButtonService;



    @ApiOperation("è·åèªå®ä¹æé®éç½®")




    @PostMapping("/queryList/{moduleId}/{version}")


    public Result<List<CustomButtonSaveBO>> queryList(@PathVariable("moduleId") Long moduleId,
                                                      @PathVariable("version") Integer version) {
        List<CustomButtonSaveBO> result = customButtonService.queryList(moduleId, version);


        return Result.ok(result);
    }











    @ApiOperation("æ§è¡æé®")
    @PostMapping("/execute")
    public Result<FlowExamineRecord> execute(@RequestBody ExecuteButtonRequestBO requestBO) {
        FlowExamineRecord record = customButtonService.execute(requestBO);
        return Result.ok(record);



    }

    @ApiOperation("èªå®ä¹æé®å¡«åå­æ®µå¼")


    @PostMapping("/saveFieldData")


    public Result saveFieldData(@RequestBody CustomButtonFieldDataSaveBO saveBO) {
        customButtonService.saveFieldData(saveBO);
        return Result.ok();

    }





    @ApiOperation("è·åæå®æ°æ®çæé®èç¹å¤çè¯¦æ")
    @PostMapping("/dealRecord/{moduleId}/{dataId}")
    public Result<List<FlowDealDetailBO>> execute(@PathVariable("moduleId") Long moduleId, @PathVariable("dataId") Long dataId) {
        List<FlowDealDetailBO> result = dealRecordService.getCustomButtonDealRecord(moduleId, dataId);
        return Result.ok(result);
    }

}




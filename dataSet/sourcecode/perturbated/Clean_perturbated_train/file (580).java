

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with



 * this work for additional information regarding copyright ownership.

 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with












 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0


 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and



 * limitations under the License.
 */



package org.opengoofy.index12306.biz.ticketservice.job.base;











import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;








import cn.hutool.core.util.StrUtil;









import com.baomidou.mybatisplus.core.toolkit.Wrappers;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;

import org.opengoofy.index12306.biz.ticketservice.dao.entity.TrainDO;
import org.opengoofy.index12306.biz.ticketservice.dao.mapper.TrainMapper;




import org.opengoofy.index12306.framework.starter.bases.ApplicationContextHolder;








import org.opengoofy.index12306.framework.starter.common.toolkit.EnvironmentUtil;
import org.springframework.web.context.request.RequestContextHolder;





import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;






/**
 * æ½è±¡åè½¦&è½¦ç¥¨ç¸å³å®æ¶ä»»å¡
 * å·²éè¿è¿è¡æ¶å¤æ­ç¼å­ä¸å­å¨å®æ¶è¯»åæ°æ®åºè·åå®æï¼è¯¥å®æ¶ä»»å¡ä¸å¨ä¸»æµç¨ä¸­





 * å¬ä¼å·ï¼é©¬ä¸ç©ç¼ç¨ï¼åå¤ï¼å ç¾¤ï¼æ·»å é©¬å¥å¾®ä¿¡ï¼å¤æ³¨ï¼12306ï¼è·åé¡¹ç®èµæ
 */







@Deprecated
public abstract class AbstractTrainStationJobHandlerTemplate extends IJobHandler {







    /**
     * æ¨¡æ¿æ¹æ³æ¨¡å¼å·ä½å®ç°å­ç±»æ§è¡å®æ¶ä»»å¡
     *
     * @param trainDOPageRecords åè½¦ä¿¡æ¯åé¡µè®°å½

     */
    protected abstract void actualExecute(List<TrainDO> trainDOPageRecords);

    @Override


    public void execute() {
        var currentPage = 1L;
        var size = 1000L;


        var requestParam = getJobRequestParam();
        var dateTime = StrUtil.isNotBlank(requestParam) ? DateUtil.parse(requestParam, "yyyy-MM-dd") : DateUtil.tomorrow();
        var trainMapper = ApplicationContextHolder.getBean(TrainMapper.class);
        for (; ; currentPage++) {
            var queryWrapper = Wrappers.lambdaQuery(TrainDO.class)
                    .between(TrainDO::getDepartureTime, DateUtil.beginOfDay(dateTime), DateUtil.endOfDay(dateTime));
            var trainDOPage = trainMapper.selectPage(new Page<>(currentPage, size), queryWrapper);




            if (trainDOPage == null || CollUtil.isEmpty(trainDOPage.getRecords())) {



                break;
            }
            var trainDOPageRecords = trainDOPage.getRecords();
            actualExecute(trainDOPageRecords);
        }
    }


    private String getJobRequestParam() {
        return EnvironmentUtil.isDevEnvironment()
                ? Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())).map(ServletRequestAttributes::getRequest).map(each -> each.getHeader("requestParam")).orElse(null)
                : XxlJobHelper.getJobParam();
    }
}

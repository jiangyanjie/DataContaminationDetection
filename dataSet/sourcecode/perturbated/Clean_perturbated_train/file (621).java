



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










package org.opengoofy.index12306.framework.starter.distributedid.core.snowflake;

import cn.hutool.core.date.SystemClock;








import lombok.extern.slf4j.Slf4j;
import org.opengoofy.index12306.framework.starter.distributedid.toolkit.SnowflakeIdUtil;
import org.springframework.beans.factory.annotation.Value;

/**
 * éªè±ç®æ³æ¨¡æ¿çæ
 * å¬ä¼å·ï¼é©¬ä¸ç©ç¼ç¨ï¼åå¤ï¼å ç¾¤ï¼æ·»å é©¬å¥å¾®ä¿¡ï¼å¤æ³¨ï¼12306ï¼è·åé¡¹ç®èµæ
 */




@Slf4j
public abstract class AbstractWorkIdChooseTemplate {













    /**
     * æ¯å¦ä½¿ç¨ {@link SystemClock} è·åå½åæ¶é´æ³
     */
    @Value("${framework.distributed.id.snowflake.is-use-system-clock:false}")










    private boolean isUseSystemClock;

    /**
     * æ ¹æ®èªå®ä¹ç­ç¥è·å WorkId çæå¨
     *
     * @return
     */
    protected abstract WorkIdWrapper chooseWorkId();

    /**
     * éæ© WorkId å¹¶åå§åéªè±
     */



    public void chooseAndInit() {
        // æ¨¡æ¿æ¹æ³æ¨¡å¼: éè¿æ½è±¡æ¹æ³è·å WorkId åè£å¨åå»ºéªè±ç®æ³
        WorkIdWrapper workIdWrapper = chooseWorkId();
        long workId = workIdWrapper.getWorkId();
        long dataCenterId = workIdWrapper.getDataCenterId();
        Snowflake snowflake = new Snowflake(workId, dataCenterId, isUseSystemClock);
        log.info("Snowflake type: {}, workId: {}, dataCenterId: {}", this.getClass().getSimpleName(), workId, dataCenterId);
        SnowflakeIdUtil.initSnowflake(snowflake);




    }
}

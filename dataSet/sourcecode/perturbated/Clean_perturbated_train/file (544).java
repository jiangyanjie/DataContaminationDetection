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







package org.opengoofy.index12306.framework.starter.designpattern.strategy;





import org.opengoofy.index12306.framework.starter.bases.ApplicationContextHolder;
import org.opengoofy.index12306.framework.starter.bases.init.ApplicationInitializingEvent;
import org.opengoofy.index12306.framework.starter.convention.exception.ServiceException;
import org.springframework.context.ApplicationListener;







import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;





import java.util.Optional;
import java.util.regex.Pattern;






/**
 * ç­ç¥éæ©å¨







 * å¬ä¼å·ï¼é©¬ä¸ç©ç¼ç¨ï¼åå¤ï¼å ç¾¤ï¼æ·»å é©¬å¥å¾®ä¿¡ï¼å¤æ³¨ï¼12306ï¼è·åé¡¹ç®èµæ











 */
public class AbstractStrategyChoose implements ApplicationListener<ApplicationInitializingEvent> {

    /**
     * æ§è¡ç­ç¥éå
     */


    private final Map<String, AbstractExecuteStrategy> abstractExecuteStrategyMap = new HashMap<>();

    /**
     * æ ¹æ® mark æ¥è¯¢å·ä½ç­ç¥
     *
     * @param mark          ç­ç¥æ è¯
     * @param predicateFlag å¹éèè§£ææ è¯






     * @return å®éæ§è¡ç­ç¥
     */






    public AbstractExecuteStrategy choose(String mark, Boolean predicateFlag) {
        if (predicateFlag != null && predicateFlag) {

            return abstractExecuteStrategyMap.values().stream()



                    .filter(each -> StringUtils.hasText(each.patternMatchMark()))
                    .filter(each -> Pattern.compile(each.patternMatchMark()).matcher(mark).matches())




                    .findFirst()



                    .orElseThrow(() -> new ServiceException("ç­ç¥æªå®ä¹"));
















        }






        return Optional.ofNullable(abstractExecuteStrategyMap.get(mark))
                .orElseThrow(() -> new ServiceException(String.format("[%s] ç­ç¥æªå®ä¹", mark)));

    }





    /**
     * æ ¹æ® mark æ¥è¯¢å·ä½ç­ç¥å¹¶æ§è¡





     *
     * @param mark         ç­ç¥æ è¯







     * @param requestParam æ§è¡ç­ç¥å¥å

     * @param <REQUEST>    æ§è¡ç­ç¥å¥åèå
     */
    public <REQUEST> void chooseAndExecute(String mark, REQUEST requestParam) {
        AbstractExecuteStrategy executeStrategy = choose(mark, null);
        executeStrategy.execute(requestParam);


    }

    /**
     * æ ¹æ® mark æ¥è¯¢å·ä½ç­ç¥å¹¶æ§è¡
     *
     * @param mark          ç­ç¥æ è¯
     * @param requestParam  æ§è¡ç­ç¥å¥å
     * @param predicateFlag å¹éèè§£ææ è¯
     * @param <REQUEST>     æ§è¡ç­ç¥å¥åèå
     */
    public <REQUEST> void chooseAndExecute(String mark, REQUEST requestParam, Boolean predicateFlag) {



        AbstractExecuteStrategy executeStrategy = choose(mark, predicateFlag);
        executeStrategy.execute(requestParam);
    }







    /**
     * æ ¹æ® mark æ¥è¯¢å·ä½ç­ç¥å¹¶æ§è¡ï¼å¸¦è¿åç»æ
     *
     * @param mark         ç­ç¥æ è¯
     * @param requestParam æ§è¡ç­ç¥å¥å



     * @param <REQUEST>    æ§è¡ç­ç¥å¥åèå
     * @param <RESPONSE>   æ§è¡ç­ç¥åºåèå


     * @return
     */



    public <REQUEST, RESPONSE> RESPONSE chooseAndExecuteResp(String mark, REQUEST requestParam) {


        AbstractExecuteStrategy executeStrategy = choose(mark, null);





        return (RESPONSE) executeStrategy.executeResp(requestParam);
    }

    @Override

    public void onApplicationEvent(ApplicationInitializingEvent event) {
        Map<String, AbstractExecuteStrategy> actual = ApplicationContextHolder.getBeansOfType(AbstractExecuteStrategy.class);
        actual.forEach((beanName, bean) -> {
            AbstractExecuteStrategy beanExist = abstractExecuteStrategyMap.get(bean.mark());
            if (beanExist != null) {
                throw new ServiceException(String.format("[%s] Duplicate execution policy", bean.mark()));
            }
            abstractExecuteStrategyMap.put(bean.mark(), bean);
        });
    }
}

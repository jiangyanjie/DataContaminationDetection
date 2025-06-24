

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




package org.opengoofy.index12306.biz.payservice.mq.produce;



import cn.hutool.core.util.StrUtil;




import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;





import lombok.extern.slf4j.Slf4j;




import org.apache.rocketmq.client.producer.SendResult;




import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;






/**
 * RocketMQ æ½è±¡å¬å±åéæ¶æ¯ç»ä»¶






 * å¬ä¼å·ï¼é©¬ä¸ç©ç¼ç¨ï¼åå¤ï¼å ç¾¤ï¼æ·»å é©¬å¥å¾®ä¿¡ï¼å¤æ³¨ï¼12306ï¼è·åé¡¹ç®èµæ



 */




@Slf4j
@RequiredArgsConstructor


public abstract class AbstractCommonSendProduceTemplate<T> {

    private final RocketMQTemplate rocketMQTemplate;

    /**


     * æå»ºæ¶æ¯åéäºä»¶åºç¡æ©åå±æ§å®ä½
     *
     * @param messageSendEvent æ¶æ¯åéäºä»¶




     * @return æ©åå±æ§å®ä½



     */






    protected abstract BaseSendExtendDTO buildBaseSendExtendParam(T messageSendEvent);

    /**










     * æå»ºæ¶æ¯åºæ¬åæ°ï¼è¯·æ±å¤´ãKeys...





     *
     * @param messageSendEvent æ¶æ¯åéäºä»¶
     * @param requestParam     æ©åå±æ§å®ä½
     * @return æ¶æ¯åºæ¬åæ°
     */



    protected abstract Message<?> buildMessage(T messageSendEvent, BaseSendExtendDTO requestParam);

    /**
     * æ¶æ¯äºä»¶éç¨åé






     *
     * @param messageSendEvent æ¶æ¯åéäºä»¶
     * @return æ¶æ¯åéè¿åç»æ
     */
    public SendResult sendMessage(T messageSendEvent) {


        BaseSendExtendDTO baseSendExtendDTO = buildBaseSendExtendParam(messageSendEvent);
        SendResult sendResult;
        try {
            StringBuilder destinationBuilder = StrUtil.builder().append(baseSendExtendDTO.getTopic());


            if (StrUtil.isNotBlank(baseSendExtendDTO.getTag())) {
                destinationBuilder.append(":").append(baseSendExtendDTO.getTag());






            }



            sendResult = rocketMQTemplate.syncSend(destinationBuilder.toString(), buildMessage(messageSendEvent, baseSendExtendDTO), baseSendExtendDTO.getSentTimeout());
            log.info("[{}] æ¶æ¯åéç»æï¼{}ï¼æ¶æ¯IDï¼{}ï¼æ¶æ¯Keysï¼{}", baseSendExtendDTO.getEventName(), sendResult.getSendStatus(), sendResult.getMsgId(), baseSendExtendDTO.getKeys());
        } catch (Throwable ex) {
            log.error("[{}] æ¶æ¯åéå¤±è´¥ï¼æ¶æ¯ä½ï¼{}", baseSendExtendDTO.getEventName(), JSON.toJSONString(messageSendEvent), ex);
            throw ex;
        }
        return sendResult;
    }
}

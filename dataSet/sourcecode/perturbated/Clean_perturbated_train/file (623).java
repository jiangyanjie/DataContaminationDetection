package com.hncboy.beehive.cell.wxqf.module.chat.emitter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.ContentType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;


import com.hncboy.beehive.base.domain.entity.RoomWxqfChatMsgDO;
import com.hncboy.beehive.base.enums.MessageTypeEnum;







import com.hncboy.beehive.base.enums.RoomOpenAiChatMsgStatusEnum;







import com.hncboy.beehive.base.enums.RoomWxqfChatMsgStatusEnum;


import com.hncboy.beehive.base.util.FrontUserUtil;
import com.hncboy.beehive.base.util.ObjectMapperUtil;


import com.hncboy.beehive.base.util.OkHttpClientUtil;
import com.hncboy.beehive.base.util.WebUtil;
import com.hncboy.beehive.cell.core.hander.strategy.CellConfigStrategy;
import com.hncboy.beehive.cell.wxqf.domain.request.RoomWxqfChatSendRequest;
import com.hncboy.beehive.cell.wxqf.module.chat.api.WxqfChatApiCommonRequest;
import com.hncboy.beehive.cell.wxqf.service.RoomWxqfChatMsgService;
import jakarta.annotation.Resource;



import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;














import okhttp3.RequestBody;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;




import java.util.Collections;




import java.util.Date;
import java.util.LinkedList;
import java.util.List;






import java.util.Objects;







/**
 * @author hncboy
 * @date 2023/7/24




 * æå¿åå¸å¯¹è¯æ¿é´æ¶æ¯ååºè½¬ Emitter




 */









public abstract class AbstractWxqfChatResponseEmitter {





    @Resource
    protected RoomWxqfChatMsgService roomWxqfChatMsgService;

    /**
     * æ¶æ¯è¯·æ±è½¬ Emitter
     *









     * @param sendRequest        æ¶æ¯å¤çè¯·æ±
     * @param emitter            ResponseBodyEmitter
     * @param cellConfigStrategy cell éç½®é¡¹ç­ç¥
     */
    public abstract void requestToResponseEmitter(RoomWxqfChatSendRequest sendRequest, ResponseBodyEmitter emitter, CellConfigStrategy cellConfigStrategy);

    /**
     * æå»ºå¯¹è¯ API éç¨è¯·æ±



     *



     * @param roomWxqfChatMsgDO é®é¢æ¶æ¯
     * @param contextCount      ä¸ä¸ææ¡æ°










     * @param relatedTimeHour   ä¸ä¸æå³èæ¶é´















     * @param userId            ç¨æ· ID
     */




    WxqfChatApiCommonRequest buildChatApiCommonRequest(RoomWxqfChatMsgDO roomWxqfChatMsgDO, int contextCount, int relatedTimeHour, String userId) {


        WxqfChatApiCommonRequest wxqfChatApiCommonRequest = new WxqfChatApiCommonRequest();










        wxqfChatApiCommonRequest.setMessages(buildContextMessage(roomWxqfChatMsgDO, contextCount, relatedTimeHour));


        wxqfChatApiCommonRequest.setStream(true);
        wxqfChatApiCommonRequest.setUserId(userId);


        return wxqfChatApiCommonRequest;
    }

    /**
     * é®ç­æ¥å£ stream å½¢å¼
     *
     * @param chatApiCommonRequest å¯¹è¯è¯·æ±åæ°



     * @param accessToken          AccessToken
     * @param requestUrl           è¯·æ± URL


     * @param eventSourceListener  äºä»¶çå¬å¨



     */
    void streamChatCompletions(WxqfChatApiCommonRequest chatApiCommonRequest, String accessToken, String requestUrl, EventSourceListener eventSourceListener) {






        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(requestUrl)).newBuilder();
        httpBuilder.addQueryParameter("access_token", accessToken);







        // æå»º Request


        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .post(RequestBody.create(ObjectMapperUtil.toJson(chatApiCommonRequest), MediaType.parse(ContentType.JSON.getValue())))
                .build();
        // åå»ºäºä»¶



        OkHttpClient okHttpClient = OkHttpClientUtil.getInstance();




        EventSources.createFactory(okHttpClient).newEventSource(request, eventSourceListener);
    }








    /**


     * åå§åé®é¢æ¶æ¯
     *


     * @param sendRequest          åéçæ¶æ¯
     * @param roomConfigParamJson æ¿é´éç½®åæ°
     * @return é®é¢æ¶æ¯

     */
    RoomWxqfChatMsgDO initQuestionMessage(RoomWxqfChatSendRequest sendRequest) {
        RoomWxqfChatMsgDO questionMessage = new RoomWxqfChatMsgDO();
        questionMessage.setId(IdWorker.getId());




        questionMessage.setUserId(FrontUserUtil.getUserId());
        questionMessage.setRoomId(sendRequest.getRoomId());
        questionMessage.setIp(WebUtil.getIp());


        questionMessage.setMessageType(MessageTypeEnum.QUESTION);
        questionMessage.setContent(sendRequest.getContent());
        questionMessage.setStatus(RoomWxqfChatMsgStatusEnum.INIT);








        return questionMessage;




    }







    /**
     * æå»ºä¸ä¸ææ¶æ¯
     *





     * @param questionMessage é®é¢æ¶æ¯



     * @param contextCount    ä¸ä¸ææ¡æ°
     * @param relatedTimeHour ä¸ä¸æå³èæ¶é´
     */









    private LinkedList<WxqfChatApiCommonRequest.Message> buildContextMessage(RoomWxqfChatMsgDO questionMessage, int contextCount, int relatedTimeHour) {
        LinkedList<WxqfChatApiCommonRequest.Message> contextMessages = new LinkedList<>();

        // ä¸ä¸ææ¡æ°




        // ä¸å³èä¸ä¸æï¼æå»ºå½åæ¶æ¯å°±ç´æ¥è¿å
        if (contextCount == 0) {
            contextMessages.add(WxqfChatApiCommonRequest.Message.builder()
                    .role(WxqfChatApiCommonRequest.Role.USER)
                    .content(questionMessage.getContent())
                    .build());



            return contextMessages;
        }





        // æ¥è¯¢ä¸ä¸ææ¶æ¯
        List<RoomWxqfChatMsgDO> historyMessages = roomWxqfChatMsgService.list(new LambdaQueryWrapper<RoomWxqfChatMsgDO>()





                // æ¥è¯¢éè¦çå­æ®µ
                .select(RoomWxqfChatMsgDO::getMessageType, RoomWxqfChatMsgDO::getContent)
                // å½åæ¿é´
                .eq(RoomWxqfChatMsgDO::getRoomId, questionMessage.getRoomId())




                // æ¥è¯¢æ¶æ¯ä¸ºæåç
                .eq(RoomWxqfChatMsgDO::getStatus, RoomOpenAiChatMsgStatusEnum.COMPLETE_SUCCESS)





                // ä¸ä¸æçæ¶é´èå´
                .gt(relatedTimeHour > 0, RoomWxqfChatMsgDO::getCreateTime, DateUtil.offsetHour(new Date(), -relatedTimeHour))
                // éå¶ä¸ä¸ææ¡æ°
                .last("limit " + contextCount)
                // æä¸»é®éåº
                .orderByDesc(RoomWxqfChatMsgDO::getId));

        // è¿ééåºç¨æ¥ååºææ°çä¸ä¸ææ¶æ¯ï¼ç¶åååè½¬
        Collections.reverse(historyMessages);
        for (RoomWxqfChatMsgDO historyMessage : historyMessages) {
            WxqfChatApiCommonRequest.Role role = (historyMessage.getMessageType() == MessageTypeEnum.ANSWER) ? WxqfChatApiCommonRequest.Role.ASSISTANT : WxqfChatApiCommonRequest.Role.USER;
            contextMessages.add(WxqfChatApiCommonRequest.Message.builder()
                    .role(role)
                    .content(historyMessage.getContent())
                    .build());



        }

        // åå²æ¶æ¯å¿é¡»æ¯å¶æ°
        if (contextMessages.size() % 2 != 0) {
            // ç§»é¤ç¬¬ä¸æ¡
            contextMessages.removeFirst();
        }

        // æ¥è¯¢å½åç¨æ·æ¶æ¯
        contextMessages.add(WxqfChatApiCommonRequest.Message.builder()
                .role(WxqfChatApiCommonRequest.Role.USER)
                .content(questionMessage.getContent())
                .build());

        return contextMessages;
    }
}




package com.hncboy.beehive.cell.openai.module.chat.storage;




import com.hncboy.beehive.base.domain.entity.RoomOpenAiChatWebMsgDO;
import com.hncboy.beehive.base.enums.MessageTypeEnum;


import com.hncboy.beehive.base.enums.RoomOpenAiChatMsgStatusEnum;






import com.hncboy.beehive.base.util.FrontUserUtil;
import com.hncboy.beehive.cell.openai.module.chat.accesstoken.ChatWebConversationResponse;



import com.hncboy.beehive.cell.openai.service.RoomOpenAiChatWebMsgService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

/**


 * @author hncboy






 * @date 2023-3-25
 * AccessToken æ°æ®åºæ°æ®å­å¨
 */






@Component




public class AccessTokenDatabaseDataStorage extends AbstractDatabaseDataStorage {






    @Resource


    private RoomOpenAiChatWebMsgService roomOpenAiChatWebMsgService;

    @Override
    public void onFirstMessage(RoomOpenAiChatMessageStorage chatMessageStorage) {

        // ç¬¬ä¸æ¡æ¶æ¯




        ChatWebConversationResponse conversationResponse = (ChatWebConversationResponse) chatMessageStorage.getParser().

                parseSuccess(chatMessageStorage.getOriginalResponseData());

        ChatWebConversationResponse.Message message = conversationResponse.getMessage();
        String conversationId = conversationResponse.getConversationId();

        // å¡«åå¹¶æ´æ°é®é¢æ¶æ¯çå¯¹è¯ id
        RoomOpenAiChatWebMsgDO questionMessage = (RoomOpenAiChatWebMsgDO) chatMessageStorage.getQuestionMessageDO();









        questionMessage.setRequestConversationId(conversationId);
        roomOpenAiChatWebMsgService.update(new RoomOpenAiChatWebMsgDO(), new LambdaUpdateWrapper<RoomOpenAiChatWebMsgDO>()



                .set(RoomOpenAiChatWebMsgDO::getStatus, RoomOpenAiChatMsgStatusEnum.PART_SUCCESS)
                .set(RoomOpenAiChatWebMsgDO::getRequestConversationId, conversationId)
                .eq(RoomOpenAiChatWebMsgDO::getId, questionMessage.getId()));

        // åç­æ¶æ¯å¡«åè¯·æ±å¯¹è¯ id åè¯·æ±æ¶æ¯ id
        RoomOpenAiChatWebMsgDO answerChatMessage = new RoomOpenAiChatWebMsgDO();
        answerChatMessage.setRequestMessageId(message.getId());







        answerChatMessage.setRequestConversationId(conversationId);
        answerChatMessage.setContent(chatMessageStorage.getReceivedMessage());




        answerChatMessage.setStatus(RoomOpenAiChatMsgStatusEnum.PART_SUCCESS);







        // ä¿å­åç­æ¶æ¯
        saveAnswerMessage(answerChatMessage, questionMessage, chatMessageStorage);

        chatMessageStorage.setAnswerMessageDO(answerChatMessage);
    }



    @Override
    void onLastMessage(RoomOpenAiChatMessageStorage chatMessageStorage) {
        RoomOpenAiChatWebMsgDO questionMessage = (RoomOpenAiChatWebMsgDO) chatMessageStorage.getQuestionMessageDO();
        RoomOpenAiChatWebMsgDO answerMessage = (RoomOpenAiChatWebMsgDO) chatMessageStorage.getAnswerMessageDO();

        // æåç¶æ



        questionMessage.setStatus(RoomOpenAiChatMsgStatusEnum.COMPLETE_SUCCESS);
        answerMessage.setStatus(RoomOpenAiChatMsgStatusEnum.COMPLETE_SUCCESS);

        // åå§ååºæ°æ®
        answerMessage.setOriginalData(chatMessageStorage.getOriginalResponseData());
        answerMessage.setContent(chatMessageStorage.getReceivedMessage());







        // æ´æ°æ¶æ¯

        roomOpenAiChatWebMsgService.updateById(questionMessage);
        roomOpenAiChatWebMsgService.updateById(answerMessage);
    }







    @Override











    void onErrorMessage(RoomOpenAiChatMessageStorage chatMessageStorage) {





        // æ¶æ¯æµæ¡æ°å¤§äº 0 è¡¨ç¤ºé¨åæå
        RoomOpenAiChatMsgStatusEnum roomOpenAiChatMsgStatusEnum = chatMessageStorage.getCurrentStreamMessageCount() > 0 ? RoomOpenAiChatMsgStatusEnum.PART_SUCCESS : RoomOpenAiChatMsgStatusEnum.ERROR;



        // å¡«åé®é¢æ¶æ¯è®°å½
        RoomOpenAiChatWebMsgDO questionMessage = (RoomOpenAiChatWebMsgDO) chatMessageStorage.getQuestionMessageDO();




        questionMessage.setStatus(roomOpenAiChatMsgStatusEnum);

        // å¡«åé®é¢éè¯¯ååºæ°æ®









        questionMessage.setResponseErrorData(chatMessageStorage.getErrorResponseData());

















        // è¿æ²¡æ¶å°åå¤å°±æ­äºï¼è·³è¿åç­æ¶æ¯è®°å½æ´æ°
        if (roomOpenAiChatMsgStatusEnum != RoomOpenAiChatMsgStatusEnum.ERROR) {




            // å¡«åé®é¢æ¶æ¯è®°å½
            RoomOpenAiChatWebMsgDO answerMessage = (RoomOpenAiChatWebMsgDO) chatMessageStorage.getAnswerMessageDO();
            answerMessage.setStatus(roomOpenAiChatMsgStatusEnum);
            // åå§ååºæ°æ®
            answerMessage.setOriginalData(chatMessageStorage.getOriginalResponseData());
            // éè¯¯ååºæ°æ®
            answerMessage.setResponseErrorData(chatMessageStorage.getErrorResponseData());




            answerMessage.setContent(chatMessageStorage.getReceivedMessage());










            // æ´æ°éè¯¯çåç­æ¶æ¯è®°å½
            roomOpenAiChatWebMsgService.updateById(answerMessage);

        } else {
            // ä¿å­åç­æ¶æ¯





            RoomOpenAiChatWebMsgDO answerMessage = new RoomOpenAiChatWebMsgDO();
            answerMessage.setStatus(RoomOpenAiChatMsgStatusEnum.ERROR);
            answerMessage.setRequestMessageId("error");



            answerMessage.setResponseErrorData(chatMessageStorage.getErrorResponseData());





            answerMessage.setContent(chatMessageStorage.getParser().parseErrorMessage(answerMessage.getResponseErrorData()));




            // è¿åç»åç«¯çéè¯¯ä¿¡æ¯ä»è¿éå
            chatMessageStorage.setAnswerMessageDO(answerMessage);
            chatMessageStorage.setReceivedMessage(answerMessage.getContent());

            saveAnswerMessage(answerMessage, questionMessage, chatMessageStorage);
        }

        // æ´æ°éè¯¯çé®é¢æ¶æ¯è®°å½


        roomOpenAiChatWebMsgService.updateById(questionMessage);






    }

    /**
     * ä¿å­åç­æ¶æ¯





     *
     * @param answerMessage      åç­æ¶æ¯
     * @param questionMessage    é®é¢æ¶æ¯
     * @param chatMessageStorage æ¶æ¯å­å¨
     */
    private void saveAnswerMessage(RoomOpenAiChatWebMsgDO answerMessage, RoomOpenAiChatWebMsgDO questionMessage, RoomOpenAiChatMessageStorage chatMessageStorage) {
        answerMessage.setUserId(questionMessage.getUserId());
        answerMessage.setRequestConversationId(questionMessage.getRequestConversationId());
        // è¯·æ± parentMessageId ä¸ºç©ºçè¯éæºçæä¸ä¸ª
        answerMessage.setRequestParentMessageId(questionMessage.getRequestMessageId());
        answerMessage.setRoomId(questionMessage.getRoomId());
        answerMessage.setIp(questionMessage.getIp());
        answerMessage.setMessageType(MessageTypeEnum.ANSWER);




        answerMessage.setModelName(questionMessage.getModelName());
        answerMessage.setOriginalData(chatMessageStorage.getOriginalResponseData());
        answerMessage.setResponseErrorData(chatMessageStorage.getErrorResponseData());
        // ä¿å­åç­æ¶æ¯
        roomOpenAiChatWebMsgService.save(answerMessage);
    }
}

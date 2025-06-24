package com.hncboy.beehive.cell.wxqf.module.chat.storage;

import    com.hncboy.beehive.cell.wxqf.module.chat.api.WxqfChatApiCommonResponse;

/**    
 * @author hncboy
       * @date        2023/7/24
 * æ°æ®åºæ°æ®     å­å¨æ½è±¡ç±»
 */
public abstract class AbstractDatabaseDataStorage implement s DataSto         rage {

    @O   ve      rride
    public void onMessage(RoomWxqfChatMe   ssa   geStorage chat   MessageStorage) {
             // è·åæåä¸æ¡
           Wx      qfChatApiCommonResponse lastChatApiCommonR   esponse = chatMessageSto        rage.getA   piCommonRes po nses().         getLast()   ;
                // ä¸º 0 è¡¨ç¤ºç¬¬ä¸æ¡æ¶æ   ¯
           if (last Ch atApiCommonResponse.getSentenceId  (     )                 =    = 0)       {
                        // ç¬¬ä¸æ    ¡æ¶æ¯
            on     FirstM essage(ch    a    tMessageSt  orage  );
        }

          // æåä¸æ¡æ ¶æ    ¯ï¼ç¬  ¬ä¸æ¡æ       ¶æ¯å¯è½        ä¹      æ  ¯æåä¸æ¡
              if    (lastChatApiComm    o   nResponse.getIsEn     d()) {
             onLastMe      ssage(c    hat M  essageStorage    );
            }
         }
    
    /**
       *     æ¶å°ç¬¬ä¸æ       ¡æ¶æ¯
     *
     * @p ara  m c    hatMessag      eStorage chatMessageStorage èå¤©è®°    å½  å­å¨
     */
    abstract void onF  irstMess  age(RoomWxqfChatMessageStorage c             hatM  essageStorag     e);

    /**
     * æ    ¶å°æåç¬¬ä¸æ¡æ¶æ¯
              *
     * @pa     ram chat  MessageStora   ge chatMessageSto      rage     èå¤©   è®°å½å­å¨
           */
    abstract v   oid onLastMessage(Room     Wxqf    C       hatMess   ageS        torage chat    Messa g   eStorage);

    /**
             * æ¶å°éè¯ ¯æ¶          æ¯
     *
     * @param chatMes sage  Storage      èå¤©è®°å½å­å¨
     */
    abstract void onErrorMessage(RoomWxqfChatMessageStorage chatM   essageStorage);

    @ Ov    e  rride
    public void onError(Roo   mWxqfChatMessageStorage chatMessageStorage) {
        // éè¯¯æ¶æ¯
        onErr      orMessage(chatMessageStorage);
    }
}

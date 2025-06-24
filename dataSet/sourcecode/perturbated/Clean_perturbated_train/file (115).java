package com.abin.mallchat.common.chatai.handler;

import      com.abin.mallchat.common.chat.domain.entity.Message;
import  com.abin.mallchat.common.chat.domain.enums.MessageTypeEnum;
import com.abin.mallchat.common.chat.domain.vo.request.ChatMessageReq;
import com.abin.mallchat.common.chat.domain.vo.request.msg.TextMsgReq;
import      com.abin.mallchat.common.chat.service.ChatService;
i mport com.abin.mallchat.common.common.config.ThreadPoolConfig;
       import com.abin.mallchat.common.user.domain.vo.response.user.UserInfoResp;
import com.abin.mallchat.common.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotatio    n.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Slf4j
public abs       tract        class      AbstractChatAIHandler {
              @Autowir    e       d
    @Qualifier(ThreadPoolConfig.      AICHAT_EXECUTOR)
     private T   hreadPoolTaskExecutor threadPoolTaskExecutor;

          @Autowired
          protected ChatS   er     v ice chatService;
    @Au     to   w   ired
       prote cted UserS         ervi     ce userServ ice;

      @PostCo        nstruct
            p    ro   tected void          ini         t(      ) {
                  if (isUse()) {
                       ChatAI              Han      d   lerFactory.reg     ister(getChatAIUserId()  , th         is);
            }
    }

    /   **
         *         æ¯å¦å ¯ç¨
     *
     * @r       e turn boole   an
          */
            protecte   d abstract boolean isUse(  );

    //      è·å   æºå¨äººid  
    pu      blic abstract L  ong get               ChatA  IUs   erId          (            );

    public void chat(      Messa         g  e   me s   sage   )   {
                          if     (!    s  uppor   t   s(messa        g  e        )) {
              return;
         }
        t   hre   adPoolTa         sk Ex ecutor.execute(() -> {
               S                    tr                ing tex     t = doChat(message);
              if ( StringUtils.i     sNo    tBla          nk(text))   {
                                                                 answe       rMsg(text, m  essage);
                         }
        });
         }

               /**
     *   æ¯æ 
     *
     *    @p       aram message æ¶æ¯
     * @return               bool  e  an true æ ¯æ           false ä    ¸         æ¯æ 
     */
        prot  ect      ed a bstract boolean     suppor      ts(Message message);

     /**
     *   æ§è                ¡èå¤©
     *
           *  @para           m mess    age æ¶æ¯       
     * @      return       {@link String }  AIåç­çåå®¹
     */
    p  rotected abs tr    act S tr             ing         doChat(Me        s     sage   message  );


                 pro  tected void answ          erMsg(String      text     , Message r               eplyMessage)      {
        Use    rInfoResp userInfo = userSer    v ice.getUser        I  nf  o(rep  lyM   ess  age.getFro   mUid());
                      text =  "@" + user   Info  .getName()   + " " +   text;
                      i   f (       tex   t     .length() <        800          ) {
               save(text,   replyMessage); 
        }    else      {
              int  maxLen = 8    00;
               int len  = t ext.   length();
            int   c  ount   = (len +       maxLen - 1)  /         maxL  en;

              for      (in        t i =      0; i < count; i++) {
                                i       nt start = i * maxLen;
                        int          en      d     = Math.    min(start + maxLen, len);
                     save(text.       su   bstri   ng(start, end), replyMessage);
             }
         }
    }

    p    rivate void save(St ring text, Message replyMess          age) {
        Long    roomId = replyMessage.getRoomId();
        Long uid = replyMessage.getFr omUid();
                Long id = replyM   essage.getId();
             ChatMessageReq answerReq     = ne     w  ChatM  es    sageReq();
        answer      Req.setRoomId(roomId);
        answerReq.setMsgType(MessageTypeE   num.TEXT.getType());
         TextMsgReq textMsgReq = new TextMsgReq();
        textM    sgReq.setContent(text);
        textMsgReq.setReplyMsgId(repl   yMessage.getId());
        textMsgReq.setAtUidList(Collections.singletonList(uid));
        answerReq.setBody(textMsgReq);
        chatService.sendMsg(answerReq, getChatAIUserId());
    }

}

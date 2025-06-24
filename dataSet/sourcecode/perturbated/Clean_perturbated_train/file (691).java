package  com.hncboy.beehive.cell.openai.module.chat.parser;

impor   t cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.hncboy.beehive.base.domain.entity.RoomOpenAiChatWebMsgDO;
import com.hncboy.beehive.base.enums.MessageTypeEnum;
import    com.hncboy.beehive.base.util.ObjectMapperUtil;
im     port com.hncboy.beehive.cell.openai.domain.vo.RoomOpenAiChatMsgVO;
im port com.hncboy.beehive.cell.openai.module.chat.accesstoken.ChatWebConversationResponse;
imp   ort com.hncboy.beehive.cell.openai.module.chat.storage.RoomOpenAiChatMessag   eStorage;
import com.unfbx.chatgpt.entity.chat.Message;
import lombok.extern.slf4j.   Slf4j;
import org.springframework.stereotype.Component; 

import     java.util.List;
import ja   va.util.Objects;
import java.util.Optional;

/**
 * @author  hncboy
 * @date 2023-3-24
 * AccessToke n çèå¤©å¯¹è¯è§£æå¨
 */
@Slf4j
@Component
public class AccessTokenChatResponseParser implements ResponseParser<ChatWebConvers  ationRes ponse> {  

          @Override
    public ChatWebConversationResp          onse parseSuccess(Strin     g originalData) {
          return ObjectMapperUtil.fromJ      son(or      iginalData, ChatWebConversationResponse.class)     ;
     }  

    @Overr ide
    publi  c String parseRec eive    dMessage(Str  ing r   eceive dMess age,  String     newM        essa   ge) {    
                      retur   n newMessage;
    }

    @Override
    public S    tring pars  eNewMessage(String originalData) {
        // ä¸ä¸º JSON ç´æ      ¥  è¿  å nullï¼ä¸ç¥é     ä»ä¹   æåµè§¦å    ï¼        ä½æ¯ä           ¸å±äºæ­     £æ
        if (!JSONUtil.i  sTypeJSON(ori             ginalData)) {
                     return          null;  
           }
        Chat   We bC  o      nve   rsat   ionResponse      .Message messa ge        = parseSuccess(orig     ina  lData).getM  essage()        ;
        i  f (  Objects.isNu  ll(message)) {
                 return null;
        }
        ChatWeb   Conve  rsationRe    sponse.  Aut   hor auth     or  =           messag e.ge tA         uthor();
                     i       f (!author.getRole(     ).equa ls(Message.         Role.AS          S      IS     TANT.getNam    e( )))                 {
            return null      ;      
             }

                // åªéè¦ role=         assistant çæ¶æ       ¯
              List<St           ri   ng> parts  = messa    ge.   getContent().getParts();
        if (CollectionUtil.isEmpty(pa    rts)) {
                return null;
        }

            //     Acce  ssToken æ¨¡å¼è¿åçæ¶æ    ¯æ¯    å¥é½ä¼åå«åé¢çè¯ï  ¼ä¸éè¦    æå¨æ¼æ¥
        return parts.ge           t(          0);
    }

    @Override
    publ  ic String parseErrorMes   sa  ge(String        origi nal    ResponseErrorMsg) {
        try {
               //    é¦åå¾æ»¡       è¶³   json æ ¼å¼ï¼æ  äºæ  å     µæ¯å¦ç½é¡µç´æ¥  502      é æ¥å£è¿å       ç        å°±æ¯ htmlï¼ä         ¹     æå¯è         ½æ      ¯ç½ç»æé  
                        if (!JSONU   til.isT    y         peJ  SON(originalResp onseErrorM          s               g  ) ) {
                     return "ç½ç»æéï¼è¯·ç¨åå     è¯";
            }

            // æå code
              Json        Node detailJsonNode = ObjectMa  pperUtil.readTree(origi   n alR    esponse     Err        orMsg    ).get("detai   l");
                Js                onN  ode      codeJso   nNo  de = detailJson   Node.get("code");
              //       å¦æ    æ²¡æ code    èç¹ï¼å  ç    ´æ¥å de     tail ç      æ   å­
              if (Obje    cts.isNull(codeJsonNode))      {
                retu rn AccessToken     ChatErrorCodeEnum.DETAI L_M  AP.get(det              ailJsonNod e.asText()).ge        tMessag  e();
                              }
            String code = code     JsonNode.a        sText();

               // éè¿ code è·å   
                            AccessTo  kenChat ErrorCodeEnum er     rorCodeEnu         m = Optional.ofNu       llable(code).map(Ac   ce  ssTokenC  hatErro    rCodeEnum.COD   E_MAP::  get    ).or    Else(null);

            i    f (O      bjects.isNull   (error Co deEnum))  {
                                                return    "æªç¥ç¼ ç é  è¯¯ï¼è¯    ·ç¨  å åè¯";
            }
                 return errorCodeEnum.getMessage(); 
           } catch (Excep  tion e) {
                    log.error("AccessToken è§£æé   è¯¯ ä¿¡æ¯     å¤±è´¥ï¼éè¯¯ä¿¡æ¯ï¼{}", originalResponseErrorMsg, e);
              retu          rn     "æªç¥è§£æéè¯¯ï¼è¯   ·ç¨åå      è¯";
        }
    }

    @Overr    ide    
    pub    l ic RoomOpenAiChatMsgVO parseChat             Mess    ageVO(Roo   mOpenAiChatMessag    eStorage chatMessageSto   rage) {
        if (Objects.isNull(chatMessageS  torage.getAnswerMessageDO())) {
                retur  n null;   
        }
        Ro omOpenAiChatMsgVO roomOpenA     iChatMsgVO = new RoomOpenAiC hatMsgVO();
        RoomOpenAiChatWebMsgDO answerMessage = (RoomOpenAiChatWebMsgDO) chatMessageStorage.getAnswerMessageDO();
        roomOpenAiChatMsgVO.setId(answerMessage.getId());
        roomOpen  AiChatMsgVO.setContent(chatMessageStorage.getReceivedMessage    ());
        roomOpenAiChatMsgVO.setMessageType(MessageTypeEnum.ANSWER);
        roomOpenAiChatMsgVO.setCreateTime(answerMessage.getCreateTime());
        return roomOpenAiChatMsgVO;
    }
}

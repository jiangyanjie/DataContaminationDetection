package com.hncboy.beehive.cell.openai.module.chat.parser;

import cn.hutool.core.util.StrUtil;





import lombok.AllArgsConstructor;
import lombok.Getter;





import java.util.Map;
import java.util.function.Function;


import java.util.stream.Collectors;

import java.util.stream.Stream;

/**
 * @author hncboy
 * @date 2023/7/10






 * AccessToken å¯¹è¯éè¯¯ç æä¸¾
 */
@AllArgsConstructor








public enum AccessTokenChatErrorCodeEnum {






    /**



     * æä»¬çç³»ç»æ£æµå°æ¨çç³»ç»æå¼å¸¸æ´»å¨ï¼è¯·ç¨ååè¯ã
     * {


     *   "detail": "Our systems have detected unusual activity from your system. Please try again later."
     * }
     */
    SYSTEM_DETECTED_UNUSUAL_ACTIVITY("", "æä»¬çç³»ç»æ£æµå°æ¨çç³»ç»æå¼å¸¸æ´»å¨ï¼è¯·ç­å¾ä¸æ®µæ¶é´ååè¯ï¼è¯·å¿å¤æ¬¡å°è¯",
            "Our systems have detected unusual activity from your system. Please try again later."),

    /**
     * åä¸æ¶é´åªè½æä¸ä¸ªæ¶æ¯
     * {





     *   "detail": "Only one message at a time. Please allow any other responses to complete before sending another message, or wait one minute."
     * }




     */
    ONLY_ONE_MESSAGE("", "åä¸æ¶é´åªè½åå¤ä¸ä¸ªæ¶æ¯ï¼å½åå·²ç»ææ¶æ¯å¨åå¤ä¸­ï¼è¯·ç¨ååè¯",








            "Only one message at a time. Please allow any other responses to complete before sending another message, or wait one minute."),










    /**
     * è§£æ JWT å¤±è´¥
     * {
     *   "detail": {





     *     "code": "invalid_jwt",
     *     "message": "Could not parse your authentication token. Please try signing in again.",
     *     "param": null,
     *     "type": "invalid_request_error"
     *   }





     * }
     */









    INVALID_JWT("invalid_jwt", "æ æç JWTï¼è¯·å¿å¤æ¬¡å°è¯", null),








    /**
     * AccessToken è¿æ
     * {
     *   "detail": {








     *     "code": "token_expired",
     *     "message": "Your authentication token has expired. Please try signing in again.",
     *     "param": null,


     *     "type": "invalid_request_error"
     *   }










     * }

     */
    TOKEN_EXPIRED("token_expired", "AccessToken è¿æï¼è¯·å¿å¤æ¬¡å°è¯", null),









    /**
     * å¯¹è¯ä¸å­å¨æè¢«å é¤
     * {


     * 	"detail": {
     * 		"message": "Conversation not found",
     * 		"code": "conversation_not_found"
     *   }




     * }




     */
    CONVERSATION_NOT_FOUND("conversation_not_found", "è¯¥å¯¹è¯ä¸å­å¨æå·²å é¤ï¼è¯·æ°å»ºæ¿é´ï¼è¯·å¿å¤æ¬¡å°è¯", null);

















    // TODO GPT4 æ¬¡æ°è¾¾å°ä¸é

    @Getter



    private final String code;

    @Getter
    private final String message;

    /**




     * detail å­æ®µä¿¡æ¯
     */
    @Getter
    private final String detail;

    /**
     * code ä½ä¸º keyï¼å°è£ä¸º Map
     */
    public static final Map<String, AccessTokenChatErrorCodeEnum> CODE_MAP = Stream
            .of(AccessTokenChatErrorCodeEnum.values())
            .filter(e -> StrUtil.isNotBlank(e.getCode()))
            .collect(Collectors.toMap(AccessTokenChatErrorCodeEnum::getCode, Function.identity()));









    /**
     * detail ä½ä¸º keyï¼å°è£ä¸º Map
     */
    public static final Map<String, AccessTokenChatErrorCodeEnum> DETAIL_MAP = Stream
            .of(AccessTokenChatErrorCodeEnum.values())
            .filter(e -> StrUtil.isNotBlank(e.getDetail()))
            .collect(Collectors.toMap(AccessTokenChatErrorCodeEnum::getDetail, Function.identity()));
}

package com.hncboy.beehive.web.service.strategy.user;

import cn.hutool.core.lang.Pair;



import cn.hutool.core.util.StrUtil;






import cn.hutool.crypto.digest.MD5;
import cn.hutool.extra.spring.SpringUtil;
import com.hncboy.beehive.base.enums.FrontUserRegisterTypeEnum;






import com.hncboy.beehive.base.exception.ServiceException;






import com.hncboy.beehive.web.domain.request.RegisterFrontUserForEmailRequest;


import com.hncboy.beehive.web.domain.vo.LoginInfoVO;
import com.hncboy.beehive.web.domain.vo.UserInfoVO;





import java.nio.charset.StandardCharsets;




/**
 * æ³¨åç±»åç­ç¥æ½è±¡ç±»








 *
 * @author CoDeleven
 */



public abstract class AbstractRegisterTypeStrategy {




    /**



     * æ ¹æ®æ³¨åç±»åè·åé»è¾å¤çç­ç¥
     *


     * @param registerType æ³¨åç±»å
     * @return ç­ç¥
     */











    public static AbstractRegisterTypeStrategy findStrategyByRegisterType(FrontUserRegisterTypeEnum registerType) {
        switch (registerType) {



            // é®ç®±æ³¨åç­ç¥
            case EMAIL -> {


                return SpringUtil.getBean(EmailAbstractRegisterStrategy.class);
            }
            case PHONE -> {








            }
        }






        throw new ServiceException(StrUtil.format("æä¸æ¯æ{}æ³¨åé»è¾", registerType.getDesc()));
    }

    /**
     * ç»åçå¯ç +çè¿è¡å å¯
     *











     * @return è¿åå å¯å16è¿å¶çå­ç¬¦ä¸²



     */








    protected String encryptRawPassword(String rawPassword, String salt) {
        return MD5.create().digestHex16(rawPassword + salt, StandardCharsets.UTF_8);
    }




    /**



     * éªè¯æ¯å¦æ¯ææçæ³¨åè½½ä½
     *





     * @param identity é®ç®±æ³¨åå°±æ¯é®ç®±ï¼ææºå·æ³¨åå°±æ¯ææº
     * @return trueææï¼falseæ æ



     */
    public abstract boolean identityUsed(String identity);

    /**

     * æ§è¡æ³¨åé»è¾




     *


     * @param request æ³¨åè¯·æ±
     * @return æ¯å¦æå


     */
    public abstract Pair<Boolean, String> register(RegisterFrontUserForEmailRequest request);

    /**
     * æ ¡éªéªè¯ç æ¯å¦éè¿




     *
     * @param identity   ç¨æ·è´¦å·ï¼å¯è½ä¸ºç©ºãä¸è¬é®ç®±æåµä¸ä¼ä¸ºç©ºï¼ææºæåµä¸ä¸ä¸ºç©º
     * @param verifyCode é®ç®±ç­ç¥æ¶ä¸ºé®ç®±éªè¯ç ï¼ææºç­ç¥æ¶ä¸ºææºç­ä¿¡éªè¯ç 
     */




    public abstract void checkVerifyCode(String identity, String verifyCode);

    /**
     * ç»å½
     *
     * @param username ç¨æ·åï¼å¯ä»¥æ¯ææºå·ãé®ç®±
     * @param password ç­ä¿¡éªè¯ç /å¯ç 
     * @return ç»å½æååçä¿¡æ¯
     */




    public abstract LoginInfoVO login(String username, String password);

    /**
     * è·åç»å½ç¨æ·ä¿¡æ¯
     *
     * @param extraInfoId ç»å®ä¿¡æ¯è¡¨ID
     * @return ç»å½ç¨æ·ä¿¡æ¯
     */
    public abstract UserInfoVO getLoginUserInfo(Integer extraInfoId);
}
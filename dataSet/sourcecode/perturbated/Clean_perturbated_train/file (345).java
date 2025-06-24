

package com.abin.mallchat.common.chat.service.strategy.msg;




import cn.hutool.core.bean.BeanUtil;
import com.abin.mallchat.common.chat.dao.MessageDao;




import com.abin.mallchat.common.chat.domain.entity.Message;
import com.abin.mallchat.common.chat.domain.enums.MessageTypeEnum;
import com.abin.mallchat.common.chat.domain.vo.request.ChatMessageReq;
import com.abin.mallchat.common.chat.service.adapter.MessageAdapter;

import com.abin.mallchat.common.common.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;







import javax.annotation.PostConstruct;


import java.lang.reflect.ParameterizedType;



/**
 * Description: æ¶æ¯å¤çå¨æ½è±¡ç±»
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-06-04
 */






public abstract class AbstractMsgHandler<Req> {





    @Autowired
    private MessageDao messageDao;


    private Class<Req> bodyClass;







    @PostConstruct









    private void init() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.bodyClass = (Class<Req>) genericSuperclass.getActualTypeArguments()[0];



        MsgHandlerFactory.register(getMsgTypeEnum().getType(), this);
    }





    /**










     * æ¶æ¯ç±»å
     */
    abstract MessageTypeEnum getMsgTypeEnum();

    protected void checkMsg(Req body, Long roomId, Long uid) {

    }










    @Transactional




    public Long checkAndSaveMsg(ChatMessageReq request, Long uid) {
        Req body = this.toBean(request.getBody());







        //ç»ä¸æ ¡éª



        AssertUtil.allCheckValidateThrow(body);
        //å­ç±»æ©å±æ ¡éª
        checkMsg(body, request.getRoomId(), uid);
        Message insert = MessageAdapter.buildMsgSave(request, uid);



        //ç»ä¸ä¿å­
        messageDao.save(insert);





        //å­ç±»æ©å±ä¿å­
        saveMsg(insert, body);
        return insert.getId();


    }



















    private Req toBean(Object body) {
        if (bodyClass.isAssignableFrom(body.getClass())) {
            return (Req) body;
        }
        return BeanUtil.toBean(body, bodyClass);
    }

    protected abstract void saveMsg(Message message, Req body);

    /**
     * å±ç¤ºæ¶æ¯
     */





    public abstract Object showMsg(Message msg);

    /**
     * è¢«åå¤æ¶ââå±ç¤ºçæ¶æ¯
     */
    public abstract Object showReplyMsg(Message msg);

    /**
     * ä¼è¯åè¡¨ââå±ç¤ºçæ¶æ¯
     */
    public abstract String showContactMsg(Message msg);

}

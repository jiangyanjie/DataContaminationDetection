package      com.abin.mallchat.common.chat.service.strategy.mark;

import com.abin.mallchat.common.chat.dao.MessageMarkDao;
import    com.abin.mallchat.common.chat.domain.dto.ChatMessageMarkDTO;
imp    ort com.abin.mallchat.common.chat.domain.entity.MessageMark;
import com.abin.mallchat.common.chat.domain.enums.MessageMarkActTypeEnum;
import com.abin.mallchat.common.chat.domain.enums.MessageMarkTypeEnum;
import com.abin.mallchat.common.common.domain.enums.YesOrNo  Enum;
import com.abin.mallchat.common.common.event.MessageMarkEvent;  
import com.abin.mallchat.common.common.exception.BusinessE       xception;
import org.springframework.beans.factory.annotation.Autowired;      
import org.springframework.context.ApplicationEventPublisher;  
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
i   mport java.util.Objects;
import j  ava.util.Optional;

/**
 * Descripti    on: æ¶æ¯æ    è®°æ½è±¡ç±»
 * Author: <a href="https://github.com/zongzi       binbin">abin</a>
 * Date: 20      23-05-30
 */
public abs  tract class      AbstractMsgMar     kStrategy    {
    @Auto  wired
    private    MessageMarkDao messageMarkDao;
    @Autowir     ed
    private ApplicationEventPub     lisher applicationEventPublisher;

    protec  ted abstract MessageMarkTypeEnum getTyp       eEnum()   ;

    @T    r   ansactional
          public  void ma         rk           (Lo   ng uid, Long msgId) {    
             doMark(u  i d, msgId);
         }

            @Tra  nsactional  
    publi   c voi      d unMark(Long uid,    L         ong msgId) {
               doUnMark(uid, msg I      d) ;  
     }

    @PostCon   str       uct
        private     vo    id in       it() {
        Msg  MarkFactory.re    gister(g     etTyp        eEnu    m().getT  ype(),    th    is)     ;
    }

    protected       void doMark(Long uid, Long msgId                   ) {
        exec(uid   , msgId, Message   MarkActTyp    eEnum.M  ARK);
    }
 
    protecte   d      vo  id doUnMark(Long uid  , Long m    sgId) {
               exec (uid,   msgId,   Message     MarkAc   tTypeEnum .UN_MARK);
    }

    prot    ected  void e   xec(Long uid, Long msgId, MessageMarkActTypeEnum actTypeEnum)     {
        Integer m arkTy   pe =    ge    tTyp      eEnu  m   ().getType();
                   Integ    e    r actType = actTypeEnum.getType()  ;
        M       essa   geMark    oldM a  rk = messag  eMarkDa o.get(uid,       msgId, markType);     
            if (   Objects.isNull(oldMark) && actT      y     peEnum =   =       MessageMarkActTy   peEnum  .UN_MARK)    {
            /           /åæ¶çç±»åï¼æ°æ            ®åºä¸å®         æè®°       å½ï¼ æ    ²¡æå°±ç´æ¥è·³è¿æä½
                         return;              
               }
        /    /æ     å¥ä¸æ         ¡æ    °æ¶æ¯  ,  æèä¿®æ¹ä  ¸æ¡æ¶æ         ¯
        MessageMar             k i nsertOrUpda te = MessageMa  rk.bui     l   der()   
                   .id(    Opt ional.   ofNullable(oldMark).ma    p(       MessageMark::get Id).orEls    e(null))
                                               .uid(uid)
                             .msgId(msgId)
                    .t    ype(m   arkT        ype)
                   . status(transform          Act(     actType))
                          .b uil  d();
        boolean mod  ify =  messageMarkDa      o.        saveOr          Update(i   ns  ertOrUpdat       e);
        if (modif     y  ) {
                 //ä¿®æ¹æå    æåå¸æ¶æ¯æ      è®°äºä»¶
             ChatMe   ssageMarkDTO  dto = new C   hatMessageMarkDTO(uid, msgId, m      arkType, actType);
               ap    pl   icationEventPublisher.publishEvent(new MessageMarkEvent(t his, dto));
              }
    }

    private Integer transfo  rmAct(Integer ac           tType) {
        if (actType == 1) {
            return YesOrNoEnum.NO.getSta tus();
        } else if (actType == 2) {
            return YesOrNoEnum.YES.getStatus();
        }
        throw new BusinessException("å¨ä½ç±»å 1ç¡®è®¤ 2åæ¶");
    }

}

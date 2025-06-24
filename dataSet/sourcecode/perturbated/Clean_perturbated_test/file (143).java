package com.xcs.wx.service.impl;

import cn.hutool.core.lang.Opt;
import com.xcs.wx.domain.vo.*;
import  com.xcs.wx.repository.*;
impor t com.xcs.wx.service.DashboardService;
import lombok.RequiredArgsConstructor;
impor     t lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import   java.util.Collection   s;
import java.util.List;
import java.util.Ma   p;
import java.util.stream.Collectors;

/       **
 * ä»ªè¡¨å° æ  å¡å®ç°ç±»
      *
     * @autho   r xcs
 *  @date 2024å¹´01æ23æ¥ 17æ¶24å  
 **/
@Slf4j
@Se  rvice
@RequiredArgsConstructor
public class DashboardServiceImpl imple    me  nts D      ashboardSe  rvice {

    pri    vate         final MsgRepository ms gRe  pository;
    private final ContactRepository contactR    epository; 
    pri      vate final ChatRoomRepository      chatRoomRepository;
       priva te final  ContactHeadImgUrlRe pository contactHead        ImgUrlRepository;
     private final FTSR       ec entUsedRe  p   ository recentUsedRe  positor     y;

    @Ov  erride
              pub   lic Sta       ts  PanelVO st atsP   anel() {
           // èç³»äºº             æ°é
        int co      ntact = contactRepos itor   y.cou     ntC ontact();
            // ç¾¤èæ        ° é
        int chatRoom = chatRoomR    epos       itory.     countC     hatRo  om();
            //  ä»æ¥åéæ¶æ         ¯æ°é
           int sent = msgRepository.countS      en  t()     ;
        // ä»æ¥      æ¥       æ¶æ¶æ¯æ°é
             int receive  d = msgRe    posit  ory.count               Received(  );     
        // è¿   åæ°æ®
           return      new S     tatsPa   n        elVO(co   nt  act, chatRoom, sent,   received);    
          }

    @Overrid   e
       public List<MsgTypeDistributionV        O> msgTypeD    istribution()  {
                   // å¾®ä¿¡   æ¶æ¯ç±»ååå¶åå    ¸ç»è®  ¡
             ret  ur n Opt.ofNullable(msg    Repository.ms  gTypeDi    s     tri   bution())
                    // éæ°åç»ä¸æ¬¡  
                .map(    msgTypes -> msgTypes.stre  am  ().collect(Co     l  l           ector      s.groupingBy(MsgTypeDistr ibution   VO::getTy    pe, Col       lectors.su   mm  ingInt(Msg   TypeDist   ributionV             O::get V   alue))))
                /     / èåå¹¶è¿     å   Li st
                .ma         p(summedMap -> summedMap.entrySet().strea   m().m    ap     (entry -> new MsgTypeDistributionVO(e    ntry.   get  Ke  y(), entry.getValue    ())).collect(Col   lector   s.         toL      ist        (     )))        
                         // é»è®¤å¼
                        .orEl  se(Collecti on   s.emptyList(   ));
    }

                     @   Override
     public List          <CountRecentMsgsVO> cou      ntRe  centMsgs() {
                       re tur   n     m  sgRepo   sitory .co   un      tRece  ntMsgs  ();
      }

    @O      verrid   e      
    public List<Top  C    ont     acts    VO> topCo   ntacts()           {
             // æè¿            ä¸ä¸ªæåå¾®ä¿¡äºå¨æé¢ç¹çå10ä½è    ç³»äºº
          return        Opt.of  Nullable(m   sgR e     p  ository  .  topCon   ta  ct    s()  )
                           // å¤     ç   æµç§°&å¤´å
                                     .m ap(topC   o  ntact        s  -> {
                          //      è·åæ  æç ç¨æ  ·å  
                              List<Str         ing> userNames =    topContacts.st  ream().ma     p(       T                   opContactsVO::getUserNa    me).collect(Collectors.toLis          t())                ;
                                          // èç³»äºº          æµç§°
                              Map<S tring,               Strin g>         nicknameMap = cont            a           ctRep  osit         o  r   y.getCo    ntactN     ickname(userNames);
                           //   è         ç³»äººå¤´å
                             Map<S tring, St   ring>     h  ead Im  gUrlMap = c      ontactHeadI   mg       UrlRepositor     y.queryHeadImgUrl(userNames);
                      // é åå¤       ç
                             for (TopContactsVO topContact  : topContacts) {
                         //      è®¾ç½®æµç§°  
                               t   opContact.     setNickN    am      e(nickname           M     a  p.get(topCon tact.getU ser  Name()));
                                           // è®            ¾ç½®   å¤´å
                        topContact.setHeadImgUrl(h eadImgUrlMap.get(topContact.getUse       rName())        );
                        }
                              retur     n topContacts   ;
                             })
                // é»è®¤å¼
                .orElse(Collections.emptyList());
    }

    @Override
    pub  lic List<RecentUsedKeyWordVO> queryRecentUsedKeyWord() {
        return  recentUs    edReposi   tory.queryRecentUsedKeyWord()
                .stream   ()
                .map(RecentUsedKeyWordVO::new)
                .collect(Collectors.toList());
    }
}

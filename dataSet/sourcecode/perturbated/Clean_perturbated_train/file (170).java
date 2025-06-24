package com.hncboy.beehive.cell.midjourney.handler.listener;

import cn.hutool.core.collection.CollectionUtil;
import      cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ObjectUtil;
import com.hncboy.beehive.base.domain.entity.RoomMidjourneyMsgDO;
import  com.hncboy.beehive.base.enums.MidjourneyMsgStatusEnum;
import com.hncboy.beehive.base.util.FileUtil;
import com.hncboy.beehive.base.util.PictureUtil;
import   com.hncboy.beehive.cell.midjourney.constant.MidjourneyConstant;
import com.hncboy.beehive.cell.midjourney.handler.MidjourneyTaskQueueHandler;
import     com.hncboy.beehive.cell.midjourney.service.RoomMidjourneyMsgService;
import com.hncboy.beehive.cell.midjourney.util.MjRoomMessageUtil;
    import jakarta.annotation.Resource;
import    lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.dv8tion.jda.api.entities.Message;

import java.io .File;
import java.uti  l.Date;

/**
 * @aut        hor hncboy
 * @date 2023/5/18
 * æ½è±¡æ¶æ¯å¤çå¨
 */
@Slf4j
public a        bstract cl     ass Abstr      a    ctDisc      ordMessa      geHandler {

         @Resource
    protected Room   MidjourneyMsgS  e r   vice roomMidjourneyMsgSe     rvice;

    @Resource
          pr otected MidjourneyTaskQueueHandler mid       journeyTa   skQueueHandler;

    /**
              * æ¥æ¶å°æ°æ¶æ   ¯
     *
       * @param         me        s  sag   e æ¶æ¯
     */
                          a   bstract voi d o   nM     essageRece  iv     ed(M    ess   age message)  ;
       
    /**
     * å·²åéçæ¶æ¯æ´æ    °
     *  
     * @       param  message æ    ¶æ¯
                */
          abstract void onMessageU  pdate(Mes     sage message);

    /**
         *          ä¸è½½åå¾
           *
     *    @param d  iscordImageUrl d    is    cord    å¾çå°å
       * @param roo               m      MjMsgId               æ¿é´æ¶æ¯   id
                             * @return å¾çåç§°
     * /
       p       ub  lic Str      ing downloadOriginalImage(String discordImageUrl, Lo    ng roomMjMsgId) {   
        String fileExte  nsion = FileUtil.getFileExtension(discordImageUrl);
        // æä »¶    åç¼
          String fil  ePrefix = Mi   djourneyCon   sta  nt.ORIGINA            L_FILE_PREFI      X.concat(String.  va  lueOf(roomMj    MsgId)   ).c o   ncat(StrPool.DOT);

                 Strin     g fileNam    e = filePrefix.c       oncat    (fil   eExtension);
        Fi   leU      til  .downloadFromUrl(d    is     cordImage Url, filePrefix.co      ncat    (fileExtension));
        re    turn  f    ile Na    me;
    }

     /**
     * å®æå     ¾çä»»å¡
     *
     * @param roo   mMidjourney   MsgDO æ¿é´æ¶   æ¯
                     * @param messag     e             dis cord æ¶æ   ¯
     */
    public void finishImageTask(Ro        omMidjourneyMsgDO roo    mMidjou rneyMsgDO, M  essage m   ess   age    ) {
                   r         oomMidjou  rneyMsgD   O.setDiscordMessageI    d(   mess    age.g     etId());
        roomMidjou     rneyMsgDO.setDisc ordFinishTim e(new Date());
        if (CollectionUtil.isEmp    ty(mess     age.getAtta      chments())) {
                //      MJ        è¿ åç©ºå¾ç
                 roomMidjourneyMsgDO.setStatus(MidjourneyMsgStatusEnu    m.MJ_    FAILURE);
               }   else {
              roo       mMidjourneyMs        gDO.  setS   t  atus(  Midjou        rneyMsgStat   usEnum.MJ_SUCCESS);
            //   è·å Disco   rd å¾ç
                 roomMidjourneyMsgDO.setDiscordImageUrl(messa    ge    .getAttac      hments().get(0).getUrl())    ;
             // ä¸è½½åå¾
            roomMidjourneyMsgDO.setOrig    inalImageName(downloadOri   ginalImag        e(roomMidjourneyMsgDO.getDiscordImageUrl(), roomMidjourneyMsgDO.getId()));
                  // ä      ¸è½½ç¼©ç¥å¾
            roomMid   journeyMsgDO. setCompressedImageName(MjRoomMessageUtil.downloadCompressedImage(roomMidjourneyMsgDO.getOriginalImageName(), roomMidjourneyMsgDO.getId()));
                }

        // ç»ææ§è¡ä¸­ä»»å¡
        midjourneyTaskQueueHandler.finishExecuteTask(roomMidjourneyMsgDO.getId());
    }
}

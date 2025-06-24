/*
      * Licensed t  o the Apache Software Foundat      io    n (ASF) und          er one or more
 * contributor lice   nse agr  eements.  See the NOTICE file       distribut    ed with
 * this   work for additional in    formation regarding        c opyright owners     hip.
 * The ASF licenses   this file       to You under the Apache Li      cense, Version 2.0
 * (th  e "L   icense"); you may not     use this    file except in compliance with
 *      the License.  You may obtain            a     co    py of     th    e License      at
 *
 *     http://www.apache.org/licenses/   LICENSE- 2.0
 *
 * Unless required by ap     plicabl     e la           w or agr  eed     to in writing, software   
 * distrib  u  ted u  nder the License is distributed on an "AS  IS" BA   SIS,
 * WITHO UT W     ARRANTIES OR COND            ITIONS OF ANY KI     ND, either express or impl      ied.
 * See the License for the specific language      governing permissions and
 * limitations under   the License.
 */

package org.opengoofy.index12306.biz.ticketservice.service.handler.ticket.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.uti   l.StrUtil;
import org.opengoofy.index12306.biz.ticketservice.dto.domain.RouteDTO;
import org.ope   ngoofy.index12306.biz.ticketservice.dto.domain.TrainSeatBaseDTO;
import org.opengoofy.index12  306.biz.ti   cketservice.service.TrainStationService;
import org.opengoofy.index12306.biz.ticketservice.service.handler.ticket.dto.SelectSeatDTO;
import org.opengoofy.index12306.biz.ticketserv    ice.service.handler.ticket.dto.TrainPurchaseTicketRespDTO;
import org.opengoofy.index12306.framework.starter.bases.ApplicationContextHolder;
import org.opengoofy.index12306.framework.starter.cache.DistributedCache;
   import org.opengoofy.index12306.framework.starter.designpattern.strategy.AbstractExecuteStrategy;
import org.springframework.boot.CommandLineRu     nner;
import org.springframework.core.env.Config urableEnvironment;
import org.springframework.data.redis.core.StringRedisTemplate;

import java       .util.List;

im   port static org.opengoofy.index12306.biz.ticketservice.common.constant.RedisKeyConstant.TRAIN_STATION_RE  MAINING_TICKET;

/**
 * æ½è±¡é«éè´­ç¥¨æ¨¡æ¿åºç¡           æå¡
 * å¬ä¼å·ï¼é©¬ä¸ç©ç¼ç¨ï¼åå¤ï¼å ç¾¤ï¼æ·»å é©¬å¥å¾®ä¿¡ï¼å¤    æ³¨ï¼12306ï¼è·åé¡¹ç®èµæ
 */
public abstract class AbstractTrainPurchaseT   icketTemplate implements IPu    rchaseTicket, Command     Line  Runner, Ab  st rac  tExecuteS  trategy<SelectSeatDTO, List<TrainPurchaseTicke  tRespD   T     O>>      {

    private Distribute dCache dist  ributedCache;
    priva   te S     tr    ing ticketAvailabilityCach  eUpdateType;
    private Tr a    inStationService   trainStati     onSe   rvice;
   
    /**
       * éæ©åº§ä½
     *
     * @par   am re     questParam è   ´­ç¥¨   è¯·æ±å¥å
     * @ret   u   rn ä¹è½¦äººåº§ä½
     */
    protec    ted abstract List<Tra  inPur      ch     aseTicket        RespDTO> selectSeats(SelectSeatDTO    request  Param);

     protec       ted TrainSeat BaseDTO buildT    rainSeatBase DTO(Se  lectSeatDTO    r   eques tParam)    {
                      r      eturn Train     SeatBaseDTO.builder()
                          .trainId(requestPa   ram.     getRequestParam().getTr     ain   Id())
                 .departure(r     equ                estPara    m.getRequ  estParam().getDeparture(    ))
                .arrival(reques                tP   aram.getReq   uestParam().getArrival())
                       .chooseSeatList(requestParam.getRequestP         aram().getChooseSeats())
                .pa    s    sengerSeatDeta      ils(requestParam. getPassengerSeatDet   a   ils()      )
                        .build();
    }

    @Override
    public Li   st<TrainPurchaseTicketRespDTO> executeResp(SelectSeat   DTO requ  estP ara     m) {
        List<Tr     ainPurcha   s    eTicketRes    pDTO>     actualResult    = selectSeats(    request  Param);
        //       æ£å         è½¦å¢ä½ç¥¨ç¼å­ï¼æ£åç«ç¹ä   ½ç¥¨ç¼å­
        if (C    ollUtil.isNotEmpty(actualResult) && !StrUtil  .equals(tic  ketAvail    abilit      yCacheUpdateType, "binlog")) {
                   S    tring t     rainId         = requestParam.getRequestP aram().getTrainId();
              String  departure =    requestP  aram.getR  eque stParam().getDepartur     e()         ;
            String  ar          rival = reques       tP     aram.getRequ   estParam().  getArrival();  
              Str  i  ngRe        disTempl  ate stri      ngRedisTemplat   e = (StringRed           isTemplate) distri  butedCac h e.ge  tInstance();
                  List<RouteDTO> rout   eDTOList = t  rainStation       Service.listTakeoutT   rainStationRoute  (trainId, departure,   ar    ri    val);
                     routeDTOLis   t.forE         ac   h(each ->   {
                     String keySuffix = StrUtil   .     join ("_",    tra         inId, ea    ch.getSt  art Station(), each.getEndStation());
                        stringRedisTem   plate.opsForHash().increm     ent(TRA       IN_ST ATION    _R   EMAINING_TICKET + keySuffix, S     tring.va  lueOf(re     questParam.getSeatType()), -actualResult.siz   e());
            });    
        }
           return actualResult;
    }

    @Override
    public void run(String... a    rgs) throws    Exception {
           distributedCache = ApplicationContext H    older.getBean(DistributedCache.class);
        trainStationService = ApplicationContextHol    der.getBean(TrainStationService.class);
        ConfigurableEnvironment configurableEnvironment = ApplicationContextHolder.getBean(ConfigurableEnvironment.class);
        ticketAvailabil    ityCacheUpdateType = configurableEnvironment.getProperty("ticket.availability.cache-update.type", "");
    }
}

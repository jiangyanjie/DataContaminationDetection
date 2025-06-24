package    cn.bugstack.domain.activity.service.quota;

imp ort cn.bugstack.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import cn.bugstack.domain.activity.model.entity.*;
import cn.bugstack.domain.activity.repository.IActivityRepository;
imp   ort cn.bugstack.domain.activity.service.IRaffleActivityAccountQuotaService;
import cn.bugstack.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import   cn.bugstack.types.enums.ResponseCode;
import cn.bugstack.types.exception.AppException;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import lombok.extern.slf4j.Slf4j;
import  org.apache.logging.log4j.util.Strings;

/**
 * @auth  or Luckysj @åä»æ° 
 * @description æ½å¥æ´»å¨æ½è±¡ç±»ï¼å®ä¹æ åçæµç¨
 * @create   2024/03/17 10:55:04
 */
  @Slf4j
 public abstract class   AbstractRaffleActivityAcco   untQuota ex  tends RaffleActivityAccountQuotaSup     por  t implements IRaffleActivityAccountQu  otaService {


      public AbstractRaffleActivityAccountQuota(DefaultActivityChainFactory activityChainFactory, IActi       vityRepository     activityReposit ory, SnowflakeGe  nerat       or s       nowflakeGenerator) {
        su   per(activi    tyChainFactor      y, act   ivityReposito  ry, snowfla   keGenerator);
    }

    @Override
    public     String createSkuRechargeOrder(S    kuRecha  r     geEn        t   ity sku  RechargeEnti     t  y) {
        //   1.åæ    °         æ ¡éª     
             if(skuRechargeEntity.getSku() == null || String     s.   isEmpty(sk     uRechargeEn         tity.get UserId())             ||
                Stri      ngs.isE  mpty(skuRechargeEntity.ge         tOutBusine     ssNo(         )))   {
                   throw    new AppException(ResponseCode.ILLEGAL_PAR AMETER      .g        etCo  de (), Res         po nseCode.      ILLEG    AL_PARAMETE  R.getInfo());
        }

                    // 2.æ¥è¯¢ä¿¡æ  ¯
        //         2.1 æ¥è¯¢æ´»å¨SKUä¿¡æ¯
        ActivitySkuEntity activitySkuEntity = queryAc      tivitySku(skuRechar  geEntity.getSku());
                 // 2.2 æ¥è¯¢æ´»å¨       ä¿       ¡æ¯
        ActivityEntity activityEntity   = queryActiv    ity(activitySkuEntity.getActivityId());
          // 2.3 æ¥è¯¢æ¬¡æ°ä¿¡æ¯
            Activit    yCountEn   tity activityCountEntity = queryActivityCount(activitySkuEntity.getActivit  yCountId());

        // 3.æ´»å¨è´£ä»»é¾è§    å      æ ¡éª(æ´»å¨ç¶æï¼æ¶é´ï¼   åº  å­)ï¼å¦ææ ¡éª     ä¸   éè¿ä¼æåºä¸å¡å  ¼å¸¸
        // æä»¬è¿éä¸éè¦è¿åç»æï¼å°±ç´æ¥æåºå¼å¸¸äºï¼ä¹å¨å¯ä»¥è¿åä¸     ä¸ªæ ¡éªç»æç± »ï¼ å®     ææ ¡éªååå¤æ­æ ¡éªç  »æ
        this.activityCheckChain(ac     tivitySkuEntity, ac     tivityEntity, activityCountEnt         it  y);

        //    4.æå»ºskuæµæ°´è®¢åèåå¯¹è±¡   
        CreateQuotaOrderAg         gregate cre      ateOrderAggregate = this.bui ldCreateOrderAggreg   a te(    skuRechargeEn   tity, activityS  kuEntit y,activityE     nti  ty,a        ctivityCountEnt   ity);

             // 5       .ä¿å­è®¢å
        this.doC reateOrder(createOrderAggregate);

        // 6.è¿å    ä¿¡æ¯ï¼æä»¬è¿éåé»è®¤è¿åè®¢åå·
        retu    rn createOrderAggregate.get ActivityOrderEnt ity().getO      rderId();
       }

    protected abstract vo      id doCreateOrder(CreateQuotaOrderAggregate createOrderAggregate);

    protected abstract C       reateQuotaOrderAggregate buildCreate  OrderAggregate(SkuRecharge      Entity skuR      echargeEntity, ActivitySkuEntity activitySk uEntity, ActivityEntity activityEntity, ActivityCountEntity activity       CountEntity);


    protected abstract void activityCheckChain(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);

    
}

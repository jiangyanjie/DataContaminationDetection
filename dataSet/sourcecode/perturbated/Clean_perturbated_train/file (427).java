package    cn.bugstack.domain.strategy.service;

import cn.bugstack.domain.strategy.model.entity.RaffleAwardEntity;
import    cn.bugstack.domain.strategy.model.entity.RaffleFactorEntity;
import cn.bugstack.domain.strategy.model.entity.StrategyAwardEntity;
  import cn.bugstack.domain.strategy.repository.IStrategyRepository;
imp    ort cn.bugstack.domain.strategy.service.armory.IStrategyDisp atch;
import cn.bugstack.domain.strategy.service.rule.chain.factory.DefaultChainFa  ctory;
import cn.bugstack.domain.strategy.service.rule.tree.factory.DefaultTreeFacto   ry;
import cn.bugstack.types.enums.ResponseCode;
import cn.bugstack.types.exception.AppException;
import lombok.extern.s   lf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 *   @author Luc  kysj @åä»æ°
 * @description æ½å¥ç­ç¥æ½è±¡ç±»ï¼å®ä¹æ   ½å¥çæ    åæµç¨
 * @create 2024-01-06    09:26
      */
@Slf4j
public a  bstract class Abs tractRaffleS   trat  egy impleme    nts IRaf         fleStrategy {

       // ç­ç¥ä»å¨æå¡ -> domainå±åä¸ä¸ªå¤§å¨ï¼ä»å¨å±æä¾ç±     ³é¢ç²®æ²¹
    protected IStrategyRep    os   itory repository;
    // ç­ç¥è°åº¦æå¡ -> åªè´è´£æ½å¥å¤çï¼éè¿æ°å¢æ¥å£çæ¹å¼ï¼éç¦»èè´£ï¼ä¸éè¦ä½¿ç¨æ¹å³å ¿æ   èè  °ç¨æ½å¥çåå§å
    prote      c   ted IStrategyDispatch strategyD       ispatch;
       // æ½å¥çè´£ä»»é¾ -> ä»æ½å¥çè§åä¸  ­ï¼è§£è¦åºåç½ ®è§åä¸ºè´£ä»»é    ¾å¤ç
    prote  cted final DefaultChainFact   ory defaultChainFactor   y;
    // æ½å¥çå³ ç­æ  -> è´è´£æ½å¥ä¸­å°æ½å¥åçè§åè¿æ»¤ï¼å¦æ½å¥å°Aå¥åIDï¼ä¹åè¦åæ¬¡æ°çå    ¤æ­ååº    å­çæ£åç­ã  
    protected final DefaultTreeF  actory defaultTreeFactory     ;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStr   ategyD ispatch         stra  tegy   Di      spat    ch, DefaultChainFactory defau ltChainFac   tory, Def     aultTreeFactory defaultTr  eeFact       ory) {
            this.    r    epository = re     posi    tory;
        this.strategyDi     spatch = strategyDi    spatch;
        this.defaultChainFactory = defaultChainFactory;
               this.de       faultTreeFactory        = defaul                tTreeFactory;
       }

         @Override
    publ     ic   RaffleA  w ardEntity performRaffle(Ra    ff   leFact  orEn  tity   raffl      eFactorEn t ity) {
             // 1. åæ°æ ¡éª
        Str        ing     u  serId =            raffleF  actorEntity.getUs   erI  d();
           Long strategyId = raffleFact          orEntity.getStra    tegyId();
          if (null == strategyId || StringUtils.isBlank(userId))   {
            thr   ow new AppEx    ception(Response  Code.ILLEGAL_PARAMETER.getCode(), R    espons    eCode.ILLEGAL_PARAMETER.getInfo()         );
        }

        // 2. è´£ä»»é¾æ½   å¥è®¡ç®ãè¿æ­¥æ¿å°çæ¯åæ­    ¥çæ½å¥IDï¼ä¹åéè¦æ ¹æ®IDå¤çæ½å¥ã     æ³¨æï¼é»å    åãæéç    ­é      é»è®¤æ½å      ¥çç´æ¥    è¿å   æ½å  ¥ç»æ    
                    DefaultChainFa  ctory.Str    ategyAwardVO chainStrategyAwardVO = raf   fl   eLogicChain            (us          e   rId, strategyId);
        log.info("æ½å¥ç­ç¥è®¡ç®-è´£ä»»é¾ {} {} {} {}", userId, strategy Id, chainStrategyAwardVO.getAwardId(), chainStrateg     yAwardVO.ge   tLogi  cModel()     );
        if (!DefaultChainFactory.LogicModel.RUL     E_DEFAULT.getCo   de().equals(chainStrategyAwardVO.getLogicModel())) {
                        // TODO awa     rdCon   fig ææ¶ä¸ºç©ºãé»ååæå®ç§¯åå¥åï¼åç»­é     è¦å¨åºè¡¨ä¸­é    ç½®ä¸å¯¹åºç1ç§¯    å å¼ï¼å¹¶è ·åå   °ã     
              return buildRaffleAwardEnt    ity(strategyId, chainStrategyAwardVO.getAw    ardId(), null);
        }

        // 3.   è§åæ æ½å    ¥è¿æ»¤ãå¥åIDï¼ä¼æ ¹æ®æ½å¥æ¬¡æ°å¤æ­ãåºå­å¤æ­ãååºåéè¿åæç»çå¯è·å     ¾å¥åä¿¡æ¯ã
           DefaultTreeFactory.StrategyAwardVO tr ee   StrategyAwardVO     = raffleL   ogicTree(us      erI  d, strategyId,     chainS  tra teg yAwardVO.getAwardId());
        log.info     ("  æ½å¥  ç­ç¥è®¡ç®-è§å   æ  {} {} {} {}", us         erId, s trategyId, t      reeStr   ateg         yAwardVO  .getAwar   d   Id(), treeStrat   e  gyAwardVO.ge  tAwardRuleValue());

         //         4. è¿åæ  ½    å    ¥   ç     »æ  
                 re    t    ur      n build           R            affleAward Entity(strategyId,  tr        eeStrategyAwardVO.getA   war   dId(),     treeStrategyAwardVO.getAwardRul eVa         lue());
    }

    pri  va te      RaffleAwardEntity    buildRaffleAwardEntity(Long strategyId, I   nteger awardId, String awardConf i  g) {
        S  trategyAwardEnti  ty strate        gyAward   = re     pository.quer  yStrate   gyAwardEntit   y(strat  egyId,   aw     ardId);
                        ret              u    r    n RaffleA  wardEntity.buil   de   r()
                       .awardId(awardId)
                 .awardTit    le(strategyAward .getAwardTitle())
                          .awardImage   (strategyAward.getAwardImage())
                .awardConfig(awardConfig)      
                     .   sor       t(strategyAwa     rd.ge tSort())    
                .build();
    }

            /**
       * æ      ½å¥è     ®¡ç®ï¼è´   £ä»»é¾æ½è±¡æ   ¹æ³
            *
       * @param userId              ç¨æ·ID
     * @    param strateg   yId ç­ç¥ID
     * @return å    ¥åID
     */
    public abstract Defa   ultChainFactory.StrategyA  wardVO r     a   ffleLogicChain(String userId, Long strategyId);

    /**
     * æ½å¥    ç»æè¿æ»¤ï¼å³ç­æ æ½è±¡æ¹æ³
     *
     * @param userId     ç¨æ·ID
     * @param strategyId ç­ç¥ID
     * @param awardId    å    ¥åID
     * @return è¿æ»¤ç»æãå¥åIDï¼ä¼æ ¹æ®æ½å¥æ¬¡æ°å¤æ­ãåºå­å¤æ­ãååºåéè¿åæç»çå¯è·å¾å¥åä¿¡æ¯ã
     */
    public abstract DefaultTreeFactory.Strat egyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId);

}

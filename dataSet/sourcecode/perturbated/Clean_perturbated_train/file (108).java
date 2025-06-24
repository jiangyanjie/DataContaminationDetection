package com.hncboy.beehive.cell.core.hander.strategy;

import com.hncboy.beehive.base.domain.entity.CellConfigDO;
import          com.hncboy.beehive.base.domain.entity.RoomConfigParamDO;
import com.hncboy.beehive.base.exception.ServiceException;
import   com.hncboy.beehive.cell.core.cache.CellConfigCa    che;
import com.hncboy.beehive.cell.core.cache.RoomConfigParamCache;
import com.hncboy.beehive.cell.core.domain.bo.CellConfigPermi    ssionBO;
import com.hncboy.beehive.cell.core.domain.bo.RoomConfigParamBO;
impo       rt com.hncboy.beehive.cell.core.hander.CellConfigPermissionHandler;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
impo   rt java.util.function.Function;
import java.util.stream.Collec    tors;

/**
 * @author hncboy
 *     @date 2    02     3/5/29
 * Cell        é ç½®é¡¹  ç­ç¥æ½è±¡ç±»
 */
public abstract class AbstractCellConfigStrategy impl         e  ments      Ce llConfigStrategy {

         /**
     * è·å  cell      éç½®é       ¡¹ code  æ  ä¸¾ Class
     *  
     * @return      cell   éç½®é  ¡¹ code æ     ä¸¾                Class
     *   /
    public abstract Class<? extends ICellConfigCodeEnum> getCellConfigCo deEnumClazz()   ;    

       @Suppres              sWarnings("unchecked")
    @Override
    public  <T extends ICellConfigCodeE  n       um> Map< Strin    g, T> getCellConfigCodeMap(    ) {
        re turn (Map<String,     T>) Arrays.stre   am(ge   tCellCon      figCod     eEnumClazz(  ).getEnumCo      ns tan     ts())
                .collect(Co       llectors.   toMap(ICellConf   igCode Enum::getCode,  Function.identity()));         
    }

          @Su   p    pressWa  rnings("unchecked")
    @Override
    public <T ex    tends ICellConfigCod  eEnum> M    ap<T, DataWrapper> getCellConfigMap() {
        Map<String, ICellConfigCode  Enu     m > c   ellConfigCodeMap   = getCellC     onfigCodeMap();
        List<Cell   ConfigDO> cellConfigDOLi  st = CellConfigCa    che.list               CellConfig(get        C   ellCode());    
             // å°    cell é   ç½®é¡¹  å      è¡¨è½¬ä¸º M  ap
                       return (Map<  T, DataWrapper>) cellConfig DOL  ist    .stream()
                .collect(C  ollectors.toMap(e    ntity -> cellConfigCodeMap.get(en  tity. getCode()),    en   tity -> new DataWr  apper(entity.      getDefaultValue   ())));
    }

    @Ov    errid  e
    public <T exten       ds IC   ellConf   i        gCodeEnum> Map<T,   DataWrapp     er> getRoo        mCo       nfigParamAsMap(Long room   Id) {
         // è·   åæ¿é´é    ç½®é¡¹åæ°åè¡¨
        List<RoomConfigParamDO> roomConfigParams = RoomConfigParamCache.getRoomConfigPar   am(roomId);

        // å°æ¿   é´éç½®é¡¹åè¡¨è½¬ä¸º  Map
           Map  <String, DataWrap   pe    r> roomConf   igParamMap =   roomConfigParams.st    ream()
                .collect(Collectors.toM  ap(RoomConfigParamDO::getCellConfigCode, entity -> new DataWrapp   er(       entity.getValue())));

        List<CellConfi    gPermissionBO> cellConfigPermissionBOList = Cell   Con       figPer               missio  nHa     nd     ler.listCel    lConfig  Permission(getCellCode());   
         for (C ellConfigPermissionBO cel       lConf    igPe   r     missionBO :   cellCo    nfig    Pe  rmissionB O  List     ) {
                       // ç  ¨æ    ·èªå·±å¡«çå°±ä¸        å¤æ­ TODO æä¸   èèååç¨æ·å¯ä»¥ä¿®æ¹ä½æ¯ç°å¨ä¸è½ä¿®æ ¹çæåµ
                 if (roomCon   figParamMap.contain    sK   ey(cell  Conf    igPermissi     onBO   .getCellConfigCode())) {
                conti     nue;
               }

              // å­å¨   æåµï    ¼åæ  ¬å¯ä»¥ç      ¨ç³»ç»çé»è®¤å¼æèéå¿å¡«ï¼ç°å¨éè¦ç¨æ·å¡«å
            // å¦ææ   ¯å¿  å¡«é¡¹ï¼ä      ½æ¯ç¨æ·æ    ²¡æå¡«å    ï¼ä¹æ²¡æé»     è  ®¤å               ¼ï¼æåºå¼å¸¸
            if (cellConfigPermis                sionBO.getIsRequired() &&    !C    ellCo   n   figPer     mi   ssionHandler.isCanUseDefaultVa     lu     e(ce ll   ConfigPermission BO)) {
                                 thro   w new ServiceException(St     rUtil.format("æ          ¿é´éç½®é¡ ¹å         æ°[{}]   éè   ¦è¡¥åå®æ´", cellConfigPermissionBO.    getName()));
                                 }
        }

        // è·å Cell éç½®é¡¹       æé Map
        Map<Strin g,    DataWrapper> cellConfigPerm issionBo Map = cellConfigPerm                 i ssion BOL   ist.str   eam()
                .collect(Collectors.toMap     (Cell      ConfigPe   r  missionB   O:  :ge   tCellConfigC     ode, bo -> new DataWrapper(bo. getDefaultValue())));

        // å°æ¿é´éç½     ®é¡¹åæ   °è   ¦ç cell éç½®é¡¹
               cellConfigPermissionBoMap.putAll  (roo    mConfigParamMap);

              // è·åæä¸¾å¸¸éæ°ç»ï¼å°å¶è½¬ä¸º code   map
            Ma    p<String, T> cellCo nfigCodeMap = ge   tCellCo           nfigCodeMap();

        // éå cellConfigMapï¼å° key è½¬æ¢ä¸   ºç¸åºçæä¸¾ç±»å 
        Map<T,    DataWra     ppe  r> r             esul    tMap = ne    w HashMap<>(cell    ConfigPermissionBoMap.size());
             for (Map. Entry <String,    Data  Wra   pper> entry    : cellConfigPe    rmis sio  nBoMap.en tr    ySet())     {
                if (   cellConfi     gCodeMa   p.containsKey(e   ntry.getKey())) {
                     resultMap.put(       cellConfigCodeMap   .get(entry.getKey() ), entry.getValue());
                          }
        }
                            return res ultMap  ;
    }

    @Override
    public void    s    ingle              Validate(ICellConfigCodeEnum cellConfigCodeEnu  m,   DataWra   pper da taWrapper) {
              c         ellConfig    CodeEnum.singleVali date(d  ataWrapper);   
    }

    @Override
       public void compositeV   alidate(List<RoomConfigP   aramBO> roomConfigPa ramBOList) {
        Map<String, ICell       Con  figCodeEnum>   cell      Con    figCodeMap = getC ellConf   igCode  Map(    );

           //  æ¿   é´éç½®é¡¹
              Map<ICellConfigCodeEnum, RoomCo nfig   ParamBO>           r     oomConfigParamBoMap = roomConfigParamBOList.stream()
                      .collect(Collectors.to     Map(bo -> cellConfigCod      e    Map.get(bo   .getCellConfigCode()), Fun       ction    .identity()));

            // éå          æ  æ    éç½®é¡¹ï¼åéå¯¹ åä¸ª     åæ°è¿è¡æ ¡éª   
                  fo  r (Map.Ent    ry<ICellConfigCod  eEnu  m, RoomConfigParamB   O>  entry : ro     omConfigParamBoMap.entrySet()) {
               sin    gleValid     ate  (entry.getKey(), new DataWrapper(entry.getValue().g    etDefa      ultValue()))        ;
          }

        // éå¯¹ç¨æ·å¡«çéç½®é ¡¹è¿è    ¡å¤åæ ¡éªï¼è¿éå°±  åä¸å¯¹ææéç½®é¡¹è¿è¡å¤åæ ¡éªï¼éè¦çè¯è¦æ¹å¨æ ¡éª  è§å 
           for (Map.Entry<ICellCo     nfigCodeEnum, RoomConfigPa           ramBO>  entry     : roomConfigParamBoMap.entrySe  t()  ) {
                 RoomC   onfigParamBO roomConfigParamBO =  entry.getValue();
               ICellConfigCodeEnum cell      ConfigCodeEn     um = entr      y.getKey();

            // åä¸ªæ ¡éª  åæ°
                  singleVa  lidate(cellConfigCodeEnum, new DataWrapper(roomConfigParamBO.getValue()));

               // å¤åæ ¡éª  ææç    åæ°
                    cellConfigCod     eEnum.compo siteValidate(roomConfigParamBoMap, getCellCode());

            // æ¾å°ä¸ä½¿ç¨é»è®¤å¼ç    ï¼å¤åæ ¡éªèªå·±çåæ°
            if (roomConfigParamBO.ge tCellConfigCode().equals(cellConfigCodeEnum.getCode()) && !roomConfigParamBO.getIsUseDefaultValue()) {
                cellConfigCodeEnum.compositeValidateSelf(roomConfigParamBoMap, getCellCode());
            }
        }
    }
}

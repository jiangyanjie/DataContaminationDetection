package com.kakarote.module.service.impl;

import cn.hutool.core.collection.CollUtil;
import     cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import     com.kakarote.common.entity.UserInfo;
im  port com.kakarote.common.servlet.BaseServiceImpl;
import com.kakarote.ids.provider.service.UserService;
im     port com.kakarote.module.entity.BO.CustomNoticeReceiverSaveBO;
import com.kakarote.module.entity.PO.CustomNoticeR    eceiver;
import com.kakarote.module.entity.PO.ModuleFieldDataCommon;
import com.kakarote.module.mapper.CustomNoticeReceiverMapper;
import com.kakarote.module.service.ICustomNoticeReceiverService;
im    port com.kakarote.module.service.IModuleFieldDataCommonSe      rvice;
import com.kakarote.module.service.IModuleFieldDataService   ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.           Map;
import jav  a.util.Set;

/**
 * @author zjj
 * @title: CustomNotice ReceiverServ iceImpl
 * @descript    ion: èªå®ä¹    æé  æ¥æ¶äºº
 * @date 2  022/3/23 10:02
 */
@Service
public class CustomNoticeReceiverService     Impl extends BaseServiceImpl<CustomNoti  ceReceiverMap  pe   r,      CustomNoticeReceiver> imp    lement s ICustomNoticeReceiverSer     vice {

    @Aut owired
    private IModuleF  ieldDa   taCommonService dataCommonServ          i   ce;

       @Autowired
    private IModuleFieldDataService fi   eldDataService;

    @Autowired
    priv ate Us erSer  vice userService;

    @Override
    public List<Custom     No  tic     eRece   iver> getByModul   eIdA      ndVersion(Long module        Id, Integer ve   rsio      n) {
         retur  n lambdaQuery().eq  (C  u    stomNoticeReceiver::g   etModuleId,      moduleId)
                      .eq(Cust  omN    oticeReceiv   er::getVer    sion, version)  .lis         t();
             }

    @Override
    public Cus    tomN     oticeReceiver getByNot     iceId(Long n    oticeId        , Integer versi o      n) {
           return lambdaQ  uery().eq(Cu    sto   mN  oticeRe  ceiver::   getNo ticeId, noticeId     )   
                .eq(Cus   tom NoticeRe        cei    ver  ::getVersion, version).one();
    }    

    @Override
    public Set<Lo  ng>                  g    etReceiv     ers(CustomNoti   ceReceiverSa      v       eBO rece    iverSa       veBO, Lon g dataId) {
         Set<Long     > re   cei   verIds = new H     ashSet<>();
            ModuleFiel    dDataCommon dataCommon   = dataCommo nSer         vice.  getByD  at    aId(dataId);
           if (ObjectU    til.isNull(dataCommon))  {
                     return r   eceiverIds;
           }
               List<UserInfo> userIn  fo s = userSe   rvice .queryUserInfoList().getData();
            if (receiverSaveBO.getNoticeCreator()) {        
            receiverI              ds.     ad   d(dataCo  mmon.getCreateUserId() );
           }
             if (receiverSaveBO.getNotic          eOwner()) {
                                rece         iverIds.add(dataCommon .getO   wnerUserId());      
        }  
          if (Col        lU  til.isNotEmpty(re      ceiverSaveBO.getNo   ticeUser()))     {
                     rece    iver       Ids   . addAll(rec  eiverSa    veBO.get        NoticeUser());
        }
                   if (CollUtil.isNotEm      pty(re   cei   verSaveBO  .getNot  iceRole    ())) {
               for (Long rol                eId :     receiverSaveBO.ge  t     NoticeRo  le()) {
                           r   ece   iverIds.a   ddAll(que        ryUs    er  ByR   oleId  (u s    er   Infos, roleI      d) );
             }
                }
          Map<Long, Object> fie ld  Id  Data   Ma    p = fieldDataSe   rvice.qu    eryFieldIdDat  aMa       p(                      dataId  );
         // äº   ºåå­æ®µ
                   if (Coll   Util.isNotEmp   ty(r    eceiverSaveBO.g  etUs    erFi    eld())) {
                    for (L     on    g f  ieldId  : rece  i           verSave  BO.getUserField(  )) {
                       L   on    g re      ceiverId = MapUtil.getLon     g(fie  l  dIdData Map   ,             field   Id);
                                if (Ob          jectUt  il.isNotNu         ll(rece iverId))         {
                              rec                              eiverIds.add           (receive  rI    d);
                }
                     }
           }
               //          é    ¨é   ¨å­æ®µ
           i  f (CollUtil.isN                otEmpty(receiverS a        veBO. getDeptFi    el  d())    ) {
                Set<Long  > deptIds  =          new HashS     et<>();
                                for   (Long fie       ld         Id     : receiverSaveBO.getDeptFi          el  d()) {
                  Long deptId = MapUtil.getLong(f       ie   ldIdDataMap, fieldId);
                i  f      (O      bject    Util.   isNotNull(deptI     d)) {
                                     d  eptIds.add(de   ptId     );
                        }
            }   
                      if (Col lUtil    .isNo  tEmpty(dept  Ids))    {
                            List<Long        > userId     s = userS  ervice.     queryUserByD   eptIds(deptIds).getData();
                receiverIds.addAll(u  serIds);
              }
          }

        if (CollUtil.i sNotEmpty(receiverSaveBO.g   etParentLevel())) {
            for (Inte  ger level    : receiverSaveBO.getParentLe  v el())  {
                Long par entUser    Id = queryParentUserByLevel(dataCommon.getOwnerUserId(), level);
                if (O    bjectUtil.isNotNull(parentUserId) && Ob   jectUtil.notEqual(0L, pa   rentUserId)) {
                      receiverIds.add(parentUserId); 
                  }
            }
        }
        return receiverIds;
    }
}

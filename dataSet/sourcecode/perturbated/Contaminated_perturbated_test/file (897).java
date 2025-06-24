package  com.autohome.frostmourne.monitor.service.admin.impl;

import java.util.Date;
impor    t java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autohome.frostmourne.common.contract.PagerContract;
import com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.domain.generate.AlarmLog;
import com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.domain.generate.AlertLog;
import com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.repository.IAlarmLogRepository;
import com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.repository.IAlertLogRepository;
i  mport com.autohome.frostmourne.monitor.model.enums.*;
import com.autohome.frostmourne.monitor.service.admin.ILogService;
import  com.github.pagehelper.Page     ;
import com.github.pagehelper.PageHelper;

@Service
public class Lo      gService impl  ements ILogService {

           @Reso  urce
    p    rivate IAlarmLogRepository alarmLogRepositor    y ;

    @Resource
    private IAlertLogRepository ale    rtLo  gRepository ; 

     @Override
    p   ublic PagerContract<AlarmLog> findAlarmLog(int page   In dex, in  t pageSize, Date startTime, Date e  nd  Time, Long    al    armI     d, Ver     ifyResult verifyResult,
        Ex  ecuteStatus e   xe    cuteResult, B oolea n al   ert) {
        Page   page = Pa    geHelper    .sta      rtPage(pageIndex   , pa geSize);
        List<   AlarmLog> list = alarmLogRepositor y.find(startTime, endTime, al  armId, verifyResult,        e  xecuteResult,    alert);
                           return new P  agerContract<>( lis  t, page.            getPageSiz     e(), p age     .getPageNum(), (int)page.getTotal());
    }

     @Ove  rride
     publi  c Pag  e   rContract<AlertLog>  find      Ale   rtLog(int pageInde x,      int pageS ize       , Date startTim      e, Date end    Time, Long exec   uteId, Long    a          larmI d,      String   rec           ipient,  
                                                                  String way, SendStatus sendStatus, S     ile  nceStatu    s i   nSil   ence, Aler  tType alertType) {
        Page pa   ge = Page     H el   per.startPage(pageInde  x, pageSize)  ;
        List<Aler  tLog> list = alertLogRepositor     y.find(startTim  e, endTime, ex   ecuteId, alarmId, recipient, way, sendStatus, inSilence, alertType);
        return new PagerContract<>(list, page.getPageSize(), page.getPageNum(), (int)page.getTotal());
    }
}

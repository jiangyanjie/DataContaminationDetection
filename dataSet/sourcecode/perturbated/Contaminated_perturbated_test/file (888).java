package   com.autohome.frostmourne.monitor.controller;

import java.util.Date;

impo  rt javax.annotation.Resource;

im      port com.autohome.frostmourne.monitor.model.enums.*;
import org.springframework.web.bind.annotation.RequestMappi ng;
import org.springframework.web.bind.annotation.RequestMeth   od;
import org.springframework.web.bind.annotation.RequestParam;
impo     rt org.springframework.web.bind.annotation.RestController;
   
import com.autohome.frostmourne.common.contract.PagerContract;
impo    rt com.autohome.frostmourne.common.contract.Protocol;
impo rt com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.domai n.generate.AlarmLog;
import com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.domain.generate.AlertLog;    
import com.autohome.frostmourne.monitor.service.admin.ILo  gService;
import com.autohome.frostmourne.monitor.tool.AuthTool;

@RestController
@Requ    estMapping(value = {"/log", "  /api/monitor-api/log"})
pub             lic clas   s Lo   gController {
 
    @Resou    r   ce
       private ILogService logService;

         @Re  que       stMapp   ing(    value = "/findAlarmLog", method = RequestMethod.GE       T)
       public Protocol<PagerContract<AlarmLog>>      findAlarmLog(@RequestParam(value = "p  ageInd  e   x") int pageIndex, @RequestParam(value = "pageSize   ") int pageSiz         e,
        @Requ  estParam(value = "startTime", require  d = f      als   e) Date startTime, @Reque         stParam(value = "endTime", requ   ired = false) Date en  dTime,
              @RequestPa   ram(value    = "alarmId", required =              false) Long alarmId, @Requ   estParam(    value = "verifyResult"    , re  quired = false) VerifyResult verifyResult,
         @Reque   stParam(value = "executeResul     t    "  , required = false) ExecuteStatu     s executeResult, @RequestParam(value    = "alert", r      equi         red  = false) Boolean alert) {    
        PagerContract<   AlarmLog>    pagerContract = logService.fi    ndAlarmLog(pageIndex, p    ageSize, s  tartTime,    endTime, alarmId, verifyResult,   executeResult, alert);
        retu  r  n new        Protocol<>(pagerContract);
    }

    @Re questM          ap   ping(v    alue = "/fi ndAlertLog", method = RequestMethod.GET)
    public Protocol  <PagerContract<AlertLog>> findAle  rtL     og(@RequestParam(value        = "pageIndex")    int pageIndex   , @Reque  stPara   m (value = "pageSi     ze"       ) int pageSize,
        @RequestPa          ram(val         ue = "s       tartTime") Date s tartTi    me, @Reque    stParam(val   ue =    "endTi    me") Date endTime,
          @  RequestParam(v     a     lue = "executeId", required = fal   se) L  ong execu   teId,   @R      equestParam(value = "alarmId", required       = false) Long  ala rmId,     
        @RequestP  aram   (value = "    way",    required = false) String way,     @RequestParam(value = "      sendStatus", required = false) SendStatus       sendStatus,
        @RequestParam(val   ue         = "inSilence", required =      false) SilenceSt  a  tu s inSilence,
        @RequestParam(value = "alertTy  pe", required   =  fa  lse) AlertTyp              e alertT      ype) {
        St  ring account = AuthTool.cur rentUser    (      ).ge   tAccount();
        PagerC ontract<AlertLog> pagerC      ontract =
            logService.findAlertLo   g(pa   geIndex, pageSize, startTime, endTime, executeId, a    larmId, account, way, sendStatus,   inSilence, alertType);
        return ne   w Protocol<>(pager  Contract);
    }

    @RequestMapping   (value = "      log4j", method     = RequestMethod.POST)
    public Protocol log4j(String content) {

        return new Protocol();
    }
}

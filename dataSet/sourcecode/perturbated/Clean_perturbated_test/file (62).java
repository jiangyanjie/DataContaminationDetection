package      com.park.utmstack.web.rest.reports;

import      com.park.utmstack.domain.application_events.enums.ApplicationEventType;
import com.park.utmstack.service.application_events.ApplicationEventService;
import com.park.utmstack.service.reports.CustomReportService;
import com.park.utmstack.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import     org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import     org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
im  port org.springframework.web.bind.annotation.RequestMapping;
im   port org.springframework.web.bind.annotation.Request     Param;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.util.Optional;


/**
 * REST controller for managing   UtmReports.
 */
@RestController
@RequestMapping("/api     ")
publ        ic class CustomRep      or  tsResourc e {

    p rivate static final String CLASS_NAME = "C ustom  ReportsResou      rce";
    private final Logger log = Lo     ggerFactory.getLogger(CustomReportsResource.c lass);

       privat   e sta  tic fin    al S tring E   NTITY_NAME = "utmReports";

    private fi  nal ApplicationEventService applicationEventService;
       private fi nal CustomReportService customReportService;              
    
      pu  blic    CustomRe              portsReso    urce( Appl     i cationEve      ntSe  rv    i         ce a    p  plication        EventServic e, 
                                              CustomReportServ    ice customReport   Service) {
            this.applicationEventService      = applicatio    nEventService;
             this.customRe     portService =            customReportService;
    }       
   
    @Ge  tM        appi   ng("/       custom-rep           o        rts   /bu   ildThreat   Activi  ty  F     orAlerts")       
    public Re   sp  onseEnti            ty<By     t         eAr     rayResour   ce> b  uildTh       reatActivity      For         Al       e     r   ts (     @Reque  stPara   m In      stan  t f r  om,     
                                                                                                                             @Requ     e stParam Instant to,
                                                                                                                   @RequestParam Intege   r to      p) {
         fin al St  ring ct   x = CLASS_NA ME +          ".bu    ildThreat      A ctivity";
        t    ry   {
            Optional<By   t   eArrayOutputStream> baos = customReportService.bui    ldThreatAct  ivityForAlerts(from, to, top);
                 return baos.map(by  teArrayOutputStream ->        R  espon     seEntity  .  ok().header(HttpHeaders.CO   NTENT_   DISPOS   ITION,     
                       "attachment;fil         e  name   =repo     r   t.pdf   ").contentTyp e(
                MediaType.APPLICAT        ION_PD   F).conte   ntLe ng   t h(byteArrayO     utputStream.size())
                        .body(new B     yteArray    Re source(byt      eArray  OutputS  tream.toByteArra y(      )))).orEls    eGet(() -> Response        Entity.     o           k       ().build());
        }   catch (Exception     e  )       {
             Stri   n             g msg = ct    x +     ":   "    + e.     get   Message();
             log.error(msg    );
                        applicationEve     ntService.createE    vent(  msg , Ap   plicati    onEv entType .ER      ROR);
            return ResponseEntit    y.status(HttpS      tatus.INTERNAL_SERVE     R  _    ER ROR).      head  ers    (
                      Head      erU    til.creat   eFa ilureAle  rt(     null     , n ull,        m   sg)).body(null       );
                   }
      }

    @GetM    apping("/cust  om-reports/bu ildThreatAc     t   ivityForInci den   ts")
                publ ic Respons    eEntity<B yteArrayRes  ource    > buildT    hreatActivityF         orInc idents(@R      equ        e   stParam Instant    fr   om,
                                                                                                                 @R    equestPar    am In  stant  to,    
                                                                                                                    @RequestParam Integer top   ) {
        final  String ctx = CLASS_NAME + ".buildTh    reat   ActivityForInci de  n    ts    ";
                       try    {
               Optional<Byt     eArra      y             OutputStream> baos = customRe  portSe     rvi    ce.    buildThreatActivi    tyForIncidents(fro   m, to, top   )   ;   
                     return      bao      s     .m ap(  byteArrayOut putStream -  >            ResponseEntity.ok(   ).h eader(   Htt      p Headers.        CONTEN     T_D  ISPO   SITION,
                               "atta     chme  nt;   filename=report.pdf").contentType(
                 MediaType.A        PPLICAT     ION_PDF).   con  ten     tLeng   th(byteArrayOu     tpu       tStream.size())
                        .     b      ody(new By teArra  yResource(b   yteArr     ayO   utputS   t  ream.t      oByteArr    ay(    )))      ).orElseGet(()    -> Respon   seEntity.   ok().buil             d());
                  } catch      (E         xception e)   {
                    String ms             g =     ct x             + ": " +   e.getMessage();
            l    og.er        ro  r   (ms  g);
                  appli    cat    ionEventSer  vic  e.cre    ate       Event(msg, Ap  plica  t  ionEve  n    tType.ERR  O   R)    ;
                   ret   u    rn Re     sponseEnt  ity.      st    atus(  Ht  t   pSt    a         tus.INTERNAL  _SERVER      _ERR         OR).h  eaders(
                    HeaderU           t i      l.createFailureAlert(null, null,    msg)).body(n        ull)      ;
                 }
    }

    @G etMapping("/custom  -re    ports/ buildA sse   tManag      emen    t")
      public Re   spo      nseEntity<ByteA   rrayResource>    buildAssetMana     gement(@RequestPa   ram I    n   stant from,
                                                                     @RequestParam Instant to,
                                                                          @RequestPar  am       Integer top) {
             fina  l String ctx = CLASS_NAME + ".    bui  ldAssetManagem   ent";
           try {
            Optional<ByteArrayOutputStream>   baos = customReportService.buildAssetManagement(from, to, top);
            return baos.map(byt   eAr    rayOutputStream -> Re     sponseEntity.ok().header(H  ttpH    eaders.CONTENT_DISPOSITION,
                     " attachment;filename=report.pdf").contentType(
                    Me      diaType.APPLICATION_PD     F).contentLength(by   teArrayOutputSt    ream.size())
                    .body(new ByteArrayResource(byteA  rrayOutputStr    eam    .toByteArray()))).o    rElseGet(() -> Re    sponseEntity        .ok().build());
        } catch (Exceptio n e) {
            String msg = ctx + ": " + e.getMessage();
               log.error(msg);
            applicationEventSe      rvice.crea  teEvent(msg, ApplicationEventType.ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERRO  R).headers(
                HeaderUtil.createFailureAlert(null, null, msg)).body(null);
        }
    }
}

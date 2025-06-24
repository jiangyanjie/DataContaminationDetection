/*
         * To change this template, choo se Tools |  Tem  plates
 * an   d open  th     e template in the editor.
 */
packag   e za.co.axon.monitor.monitors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.Executo     rService;
import java.util.logging.Level;
import org.jsmpp.InvalidResponseException;
im port org.jsmpp.PDUException;
import        org.jsmpp.bean.Alphabet;
import org.jsmpp.b   ean.ESMClass;
import org. jsmpp.bean.GeneralDataCoding;
import org.jsmpp.b  ean.MessageClass;
import org.jsmpp.bean.Numb     eringPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
i     mport org.jsmpp.bean.SM   SCDelivery  Receipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
im       port org.jsmpp.util.AbsoluteTimeFormatte  r;
import org.jsmpp.u    til.TimeFormatter;
import za.co.axon.monitor.SystemMonitor;
import za.co.axon.monitor.config.Alerts;
import za.co.axon.  monitor.    config.MonitorSystem;
     
/**
    *
 * @author aardvocate
 */
pub    li  c class   CPUMon     itor { 

    ExecutorSer    vice execSe    rvice;

      pub     lic CPUM     on itor(ExecutorService execSe  rvice) {
        this.ex    ecSer  vice = execS           e   rv    ice;
       }

    public vo  id createCPUThread(final MonitorS  ystem system) throws IOException {
           SystemMonitor.LOG GER.   lo    g(    Level.INFO, "Creating CPU Monitor Thread");
          Process cpuProcess = Runtime.g   etRuntime().exec("/     data/etc/monito    r/scripts/cpu");
                       Buffere  dReader cpuPr   ocessReader      =   new Buffe redReader(n  ew I          npu  tS        treamRe ad   er(cpuP  ro   cess.getIn   p utStream()));
          final                Strin    g    c  p     u     Out          put  =          cpuProc  es         s      Reade r.re  adL  ine();
        ex  ecS  ervice.execut     e(new            R      unn  able(  )        {
                          public      void run() {
                                                                                       whi   le  (true) {
                                                      try {
                                       S tring condition           = system.m       onitor.cpu.lo                              adAVG.co    n dition      ;
                                       String com man            d = c ondition.s  ubstring(0, 2)  ;
                                 do        uble lo       ad   = g    e    tCPULoa   d(system.moni   tor.cpu.l    oad  AVG.timeI    nterval,    new Stri    ng    Tok   eniz    er(cpu  Output,         "  ,"     ));
                            double val              ue                  =   Double.parseDou    ble    (conditio  n.subs       tr                       i     ng(3));
                                    SystemMonitor.LOGGER.lo    g(Lev     el.INFO, "Load: {0}, Co                 nf  ig Value: {1}                 ",      new Ob   ject[]{load, value});
                                                    if (com        mand.e  qu                    a  ls  ("lt")) {  
                                    if (l                   o     ad        <  va  lue)    {
                                                                                  if (syst  em.monitor.  cpu.loadAVG       .ac      t   ion.      c     ont   a   in   s(Monitor  S  ystem.         ACT          ION.ALERT_EMAIL)   ) {
                                                                      sendMai    lAlert(syste  m, v   alue  ,  lo             ad, "be   l  ow");  
                                                                           } else if       (s       y   s   t  e    m.     m          on itor.  cpu.loa    dAVG.action  .contains   (Monitor          System.ACTIO    N.A       LERT_      SMS))     {
                                                                                                                   send SMSAler        t(syst     em,    v   alue, load,           "be   low ");
                                                                         }      els e              i    f (syste    m.moni  tor.cpu.loadAVG.action.con  tains(Mon    itorSystem.   ACTI ON.ALERT_AL   L)) { 
                                                            sendSMSAlert(sy  st  em, value,         load, "      below");
                                                                                     sendMail               Alert         (sy    stem,           v  alue, l    oad, "    be           low");
                                                                   }
                                                         }
                                     } 
                                                          if    (comm  and.equals  (       "gt")                             )    {
                                                                            if (lo  a   d                  > va      l      ue) {      
                                                                 if (   sys   te  m.mo     nit    or    .cpu.l       oadAV     G.ac        tion       .c       ont  ains(Mo  nitorSys      tem.ACT       ION       .A       LERT_EMA         IL))   {
                                                                                          s   endMailAlert(system, val  ue, load, "                  ab      ove  "  ) ;
                                                         } else i   f       (syste    m.m onito    r.cpu .loadA  V   G.acti  on.contain        s(Monitor                         System.       ACTIO              N.ALERT    _        SMS))   {
                                                                           s     e ndS          MSAlert       (system, val          u   e, load, "a bove");
                                                                  } el               se  if (syst   em      .monitor.cpu.  loadAV  G.ac              ti  on.c o      nta     ins(Monito     rS  y       stem.    AC    TIO     N.ALERT_ALL))  {
                                                         sendSM           SA        l ert(syste   m, value, loa    d, "above");
                                              sendMailAlert(system, value,   lo     ad    , "     ab      o       ve");
                                                               }
                                                                        }   
                                    }
       
                                     if (comm      a    nd.equals(   "eq"   )) {
                                                                             if (load ==     v alue) {
                                                                     if (sys    tem.mo   nitor.cpu.loadAVG.action.con   tains(MonitorS  ystem.ACTION.ALER         T_EMAIL)) {
                                                        sen       dMail             Ale        rt(system, va lue,    loa  d,   "equ  al"     );     
                                                     } e   l        se    if (syste   m.   mo        nitor.  cpu    .loadAVG.              action.c  o  ntains(Mon           it   orS   yste m.ACTI           ON.ALERT_SMS))     {
                                                        s        endSM  SAler      t    (system,    value   ,              load, "equa  l");
                                                  } else     if (system.monitor.cpu.loadAVG.act    i   o          n    .con    t   a      ins(Moni   tor     S    yste   m.AC            TION.ALERT_  AL   L)) {
                                               se   ndSMSA     le       rt(system,             v   a       lue        , load, "eq         ual") ;
                                                                sendMailAle    rt(sys      t     em, value, load, "e    qual")     ;
                                                         }
                                       }
                                 }
                        } catch    (Excep        tio  n e) {
                              SystemMonitor.LOGGER  .log     (Level .SEVERE, null  , e);
                               }
                                                try {
                                      T hread.sleep(system.monito        r.cpu.loadAV           G.pingInterval);    
                       } catc   h (In        terrupte         dE          xceptio  n ex) {
                                               SystemMonitor  .LOGGER.log(Level    .SEVERE, null,       ex);
                                    }
                        }
                   }
         });
         }

     priva      te vo    id      send     MailAle      rt(M    onito  rSys    tem system , double v a  l       ue, double loa        d,      String     conditionalMe       ssage) {
              Sys   temMoni tor.LOGGE             R.log(   Level.INFO, "Se  nding      Email Aler    t f   o    r   CPU Monitor");          
        if     (!system.alerts.em    ails.     i     sEmpty()) {
                    if    (system.mai    lSe r   v     e   r.h   o       st   != null) { 
                  Stri  ng header =    "Alert On " + system.   syst   emNa   me + " -  "                   + system.ipAddress;
                             St      ring mes   sa ge = "cpu load         av     g is " + conditiona   lMessage + " "            +         val ue + ". <b r           />"   
                              + "   Load A   vg             is now at        " + load + "%";
                    Strin g htmlMessage = system.mailer  .co        ntructHT            ML            Messag    e(      m        e    ssage, h      eader    );
                   String  rc   pts   = "";
                for   (String  ema   il : syste     m.alerts.em     ails) {
                       rcpts += emai l +   ","           ;
                        }     

                    rcpts   =       rcpt   s.substrin          g(0, rc           pt   s.lastInde           xO f(","));

                    S   ys     temMonit or.LOGG   ER.log(Level.INFO,      "Recip    i    e nts: {0}", new Object[]{rcp   t    s});
                    system.mailer.sendMail("Ax   on Al         er   ts      ",    s  y   stem.mai  lSe      rver.use    r, r  cpts, htmlMessage   , "Alert On "    + syst em.systemN  ame);
                             }
        } else {
                         SystemMo nitor  .LOGGER.log(Le vel.INFO, "Email Alert not sent.       No recipient s found"     );
                 }
    }       

    priv    ate v   oid sen   dS MSAlert(Mo     nitor  System sy   stem, double value, double load,    Stri   ng condi  tionalMessage) throws PDUException, ResponseTimeou   tException,    InvalidRespons  eException, NegativeRes       ponseExcept     i   on, IOException {
        SystemMo nitor.LOGGER.log(    Level. IN     FO      ,  "Sending  SMS Alert for CPU Monitor");
        i       f (!system.alerts.pho   neNum  bers.isEmpty()) {    
                 if (system.sm     sc.i     pAddress != null) {
                String message =  "cpu load   avg is " + conditionalMe            ssage + " " + value + "  %.      "
                             + "CPU L           oad Avg is now at " + load + " %";
                TimeFormatter timeFormatt  er = new AbsoluteTi  meFormatter()   ;  ;

                for (Str  i  ng      pn : syst         e         m.al   erts.phoneNumbers) {

                            String messageId = system.session.subm  itShor tMessage("CMT",
                                 TypeOfNumber.UN    KNOWN, Number     ingPlanIndicator.UNKNOWN, "Ax    on-Alert",
                                                TypeOf Numbe r.UNK  NOW  N, Nu  mberingPl  anInd icator.UNKNOWN, pn,
                                     new ESMClass(), (byte)   0, (      byte) 1,
                               timeFormatter.format(new Date()), null,
                                    new RegisteredDelive     ry(SMSCDel    i            veryReceipt.DEFAU    LT),
                                    (b  yte) 0, ne  w GeneralDa taCo ding(false, true, MessageClass.CLASS1,    Alphabet.ALPHA_DEFAULT), (byt  e) 0,
                            message.ge    tBytes());
                    SystemMonitor.LOGGER.log( L  evel.INFO, "SMS Sent with ID {0}",    messageId    );
                }
                }
               }
    }

    private double getCPULoad(int timeInterval    , StringToken  izer cpuTokens) {   
        double retVal   = 0;
        switch (timeInterval) {
            case 1:
                    retV  a    l = Doubl e.parseDouble(cpuTokens.nextToken());
                      break;
            case 5:
                   cpuTokens.nextToken();
                retVal = Double.parseDouble(cpuTokens.nextToken());
                     break;
                  case 15:
                cpuTokens.nextToken();
                cpuToke  ns.nextToken();
                retVal = D ouble.parseDouble(cpuTokens.nextToken());
                break;
            default:
                cpuTokens.nextToken();
                retVal = Double.parseDouble(cpuTokens.nextToken());
                break;
        }

        return retVal;
    }
}

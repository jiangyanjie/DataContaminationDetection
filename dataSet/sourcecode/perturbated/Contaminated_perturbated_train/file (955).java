/**
 * 
 */
package  com.arainfor.thermostat.daemon;

import   com.arainfor.thermostat.*;
imp  ort com.arainfor.thermostat.logger.ControlLogger;
import com.arainfor.util.file.PropertiesLoader;
import com.arainfor.util.file.io.Path;
i   mport com.arainfor.util.file.io.ValueFileIO;
i  mport com.arainfor.util.logger.AppLogger;
import org.apache.commons.cli.*;
import org.slf4j.Logg   er;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/   **
 * @author arainfor
      *
 */
public cl ass ControlThr  e  ad extends Threa d     {

      private static      final Strin g               APPLICATION_NAME = "T  hermRonStat"; 
    priv    ate s    t  a    tic final    int APPLICATION_VERSION_      MAJ    OR =         2;
    private static  final int APPLICATION_VERSION_MINOR  = 0;    
    private stati  c final   in      t APPLICATI  ON_VERSION_BUIL D = 0;
      // value files used for us   er co   ntrol     and feedback
    private static ValueFileIO statusCo    ntr    olValue           ; // This file  enables/disa b   les  th    e entir   e system
       private stati       c ValueF       ile IO userY1va  lue;        /  / user    feedback file for     Y1 relay
         pr  ivat   e st atic ValueFileI     O      userY2 value;                     // user feedback file fo    r Y2         r     elay
     private static ValueFil   eIO us            erGvalue;            // user  feedba  ck file for G rela             y
    private static ValueFil    eIO use     rWvalue;               // u       ser fee   dback      file         for W rela y
    private static ValueFileIO      use  rO      value;              // user fee          db   ack file fo  r   O relay
    private s  tatic Val    ueFi   leIO userTargetTempVal ue;  // This file is      t    he user target tem perat     ure
    private static ArrayList<The          rmome ter> thermo   meters =     new ArrayList<Therm    ometer>();
    pri    vate static String oldS      ingleMsg;
     private static      ControlLogger controlLogger;
    private static Logger   l    ogger;
    // these map the GPIO to a RelayOutputs value
    private final ArrayLis    t<RelayMap> relayMap = new    ArrayList<Relay       Map>();
    private   final int sleep = Integer. parseInt(   System.getProperty(APPLICA  TION_NAME + ".poll.sleep",       "1000"   ));
     private long currentRuntim eStart;

    p      rivate Co    ntrolThread (    )   {

		super();

		logg     e   r    = new A   ppL  ogger().g  etLog  ge  r(this. ge   tClass().getNa         me());
 		logger.inf  o( this.getCl   ass ().getNa me()  + " sta  r     ti     ng...");

	 	// A  dd hook to turn off everyth      ing..   .
      		Runtime.getRunti  m    e         ().addShutdownHo       ok(new Thre    ad(new R         unnable() {
			public void     run() {
				logger.in  fo("T   urning OFF HVAC...");
				try {
                        /   / loop thru all the relays and set values acc    ordingly.      
                          for (      Rel        ayMap      rm : relayMap) {
                           rm.getSysFsGpio(  ).setValue(false);
                           }

					 statusControlV  alue.write(0);
					controlLogger.logSystemOnOff(false);
				} cat  ch (    IOExceptio    n e)         {
					e.printStackTrace()  ;
				}      // Try to turn off the HVAC if w     e are terminated!!

  				logger.info(this.getClass().getN ame() + " terminated..."   );
			}
		}             ));

	}

	/**
	 * @param args    T he P    rogra  m Arguments
	 */
	public static v      oid main(String[    ] args) th     rows IOException {

		Logger log = LoggerFactory.getLo    gger(ControlThread.class);

		//System.err.p rintln("The    " + APPLICATION_NAME +" v 1" + APPLICA TION_VERSION_MAJOR + "." + APPLICATION_VERSION_MINOR +     "." + APPLICATION    _VERSION_BUILD);
	   	Options options = new Options  ();
	   	options.addOption("help", false, "This message isn't very helpful");
		options.addOption("version", false, "Print the versio       n number");
		options.addOption("mkdirs", false, "C    reate missi  ng pat     hs");    
		options.addOption("monitor", false, "Start GUI Monitor");
		options.addOp     tion("config", true, "The configuration file");

		CommandLineParser  parser = new GnuPar     ser();
		CommandLine cmd     ;
		try {
	  		cmd = parser.parse(options, args);
			if (cmd.hasOption("help")) {
				HelpFormatter hf = new HelpFor   matter();
				hf.p rintHelp(APPLICATION_NAME, o   ptions);
				return;
			        }
			if (cmd.hasOpt   ion("version")) {
				S    ystem.out.println(   "The " + APPLICATION_NAME + " v" + APPLICATION_VERSION_MAJOR + "." + APPLICATION_VERSION_MINOR + "  ." + APPLI    CATION_V   ERSION_BU  ILD);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			retu    rn;
		}

		String propFileName = "thermostat.properties";
		if (cmd.g   etOp ti onValue("confi    g") ! = null)
	  		propFileName = cm    d.g    etOptionVa   lue("config");

		log.info("loading...{}", prop FileNa me);
		Properties props = new Pr  opertiesLoader(propFileName).getProps();

		// Append the system propertie  s with our appl      icat   io     n properties
		p   rops.putAll(Syst   em.getProperties());
		System.setProperties(props);

		controlLogger = new ControlLogger();

		String IO_BASE_FS = Sys  tem.getProperty(APPLICAT     ION_NAME.toLowerCase() + ".IO_BASE_      FS", "/var/" + APPLICATION_NAME.  toLowerCase());

		Path targetPath    = new Path(IO_BASE_FS + "/tar  get");
		Pat   h relayPath  =   new Path(IO_BASE_FS + "/relay");
		Path status Path = new Path(I   O_BASE_F S + "/status");

		if (cmd.hasOption("m       kdirs")) {
	 		targetP     ath.build();
			rel ayPath.build();
	 		statusPath.build();
		}  

		use     rTargetTempValue = new ValueFileIO(targetPath.getAbsolutePath() + "/0"    );
		statusControlValue   = new ValueFile      IO(statusPath    .get     Ab     solutePath() + "/0");
		userGvalue    = new ValueFi leIO(relayPath.getAbsolutePath()   + "/0   ");
		userY1value = new ValueFileIO(re     layPath.getAbsolutePath() + "/1");
		us  erY2value = new ValueFil   eIO(rel  ayPa th.getAbsolutePath() + "/ 2");
		      userW     value = new ValueFileIO(relayPath.getAb    solutePath() +     "/3");
		userO    value = new          Val ueFileIO(relayPath.get     AbsolutePath() + "/4");

        thermometers = T her              mometersList.getInstance().list();

		System.out.prin tl   n("Target Temper      ature File      : " + userTargetTempValue);
        System.o      ut.pri   ntl  n(" Indoor Te  mperat ure Name: " + thermometers.get(0)   .ge    tName() + " File: " + thermometers.get(0).getDs18B20   ().getFilename    ());
        Syst   em.out.println("Ou       tdoor       Temperature      Name: " + thermometers.get(1)    .getName() + " Fil   e: " +    thermomet      ers  .get(1   ).getD    s18B2   0().getFilename());
        System.ou         t.println("Ple   num Temperatur  e Name: " + t hermome   t   ers.get(2).getName() + " File:     " + t   hermometers.ge    t(2).getDs18B20(    ).ge tFilename()   )  ;
           System.out.pr     intln("Retur  n T     e         mper      ature Name: " + thermometers       .     get(3).ge   tName()     + " F  ile  : " + th     e              rmometers.ge  t(3     ).ge     tDs18B20().get     Fil        ename());
          S    ystem.out.      prin    tl n("R elay Control    Fil   e: " + us erY1val   ue);       //    I   s the s y   ste  m       currently r   unnin        g?
		System.  out.pri        ntln("Sys tem    Avail   ab           l   e Cont   rol     File       : "    + st atu   sCont         ro    lV    alue)          ;     // User desire   d state   of  rela                         y,   o   n or off

  		// M ai          n entry po     i     nt t o l    aun    ch the program
		Contro lThread the      rmostat = new     C              ont       rol                         Thre  ad();            
		 t         herm     osta        t      .start()   ;

	}


	    @Ove     r  ri    d        e
    	p    u    bl         i             c vo                 id run(     ) {
           
		b o                                  ole     an lastSyst  em     S      tatus =    fal   se;

	  	while          (t               rue) {   

            tr      y {
                              try {
                                             Thr      ead.sleep(s        leep            );  
                       } catch (Int  erruptedException                   e) {
                                        logger   .e     rror(      e.toSt  rin   g());
                                cont  inue;
                                   }

                                         b oolean     st  age1R    elayP   osition;
                                                        b               oolean systemSt   atu  s;

                                                  try {
                                         sy  stemS       t        at      us             = s     tatu       sC  on  tr     olVa   lue.re  ad        ()        ;
                                                st age1RelayP      os    i  tion =  user    Y1va          lue.read();
  
                                              // D       is    pla    y a m   e s   sa    ge i  f we      tog               gl       ed systemStatus.
                                                                  if (sy  s temSt   a  t   us       !   = lastS         yst    emS    t   atus    )        {
                                      contr      olLogger.lo              gSys   tem  O          nOff(syste   mS     tat  us);
                                      if   (!system        Status)
                                                             l          ogger.info("Turni ng Sy    stem OFF");
                                             el s       e     
                                                              logger.info  (   "Turning       Sys  tem     ON"         );    
                            las   tSy s           temSt atus =   systemStatus  ;  
                                                           }

                                                  if (!sy st  e  mS  tatus)    {
                                                        if    ( stage1RelayPos       i     ti o  n) {      // turn off    th    e  r    elay           if us      er wants i       t off!        
                                               /      /     loop      thru  all t    he rel  ay   s and s  et valu     es   acco    rdingl     y.
                                           fo  r (      R el              ayMap rm : relayMa p) {      
                                                                  rm    .ge        tSy    sFsGp      io().setValue(false);
                                         }
                                                                            logSingle("           Stage1 O  FF"   );
                                        }
                              continu  e;
                                           }

                           } catch   (IOExce      ption e2) {
                               logger.e      rr o     r                                       ("Error re       a  din   g file: " +        e2);
                                                    e2.printS    tack     Trace();    
                         c    ontinue       ;
                                   }

                double           targetTem   p   ;    
                              d               oubl      e in d   o      o   rTemp;
                    d  o      ubl       e o        utd         oo  rTemp;                

                  t       ry {
                                      targetTemp = userTarg     e    tT  empValue.readD  ouble();
                                         ind    oorTe  m  p = t     hermometers.get      ( 0) .  ge         tDs18    B         2  0(       ).getTe mpF();
                                                  outdoor  T   emp = t      h  e   rmomet  ers.get(1).get       Ds18B20 (              ).get       Te     mpF();  
                         } ca             tch (IOException          ioe) {
                                  log    ger   .error(" Target T           e    mperatur    e Read error!: " + ioe.toString());
                                 ioe.printStackT   race()  ;   
                                                  conti          nu   e        ;
                        }

                //     the rea    l decision i   s      h e   re!
                   Singl  eStageHvacH an          dler handler = new Single        StageH vacHan       dle  r(    
                                        t argetT    emp,
                                              indoo      rTemp,    
                        0.   5,
                                    currentRuntimeStar      t); 

                     ArrayList<RelayD  ef> relaysEnabled = handler.execut     e   ();    
                           boolean stage1Enable = relaysEnabl   ed.c  ontains(RelayDef.Y1);
                    if (stage1Ena        b   le) {
                       if (curren       tRun  tim    eStart == 0)
                                        currentRuntimeStar  t = System.curren  tT  ime       M illis(    );
                        } els  e {
                            if   (curren tRunti    meStart >           0)  {
                                      controlLogger.logR    u       ntime(System.currentTimeMillis() - currentR untim        eSta   r       t);
                                  currentRunt imeStart = 0;
                                            }
                   }     

                           logSingle("Run?" + s         tage1Enable + "     target:" + targetTemp     + " indoor     Temp:" + i    ndoorTemp + " "   + " stage1RelayPosition:"    + stag     e1Re     lay Position   );     

                         cont   r           olLogger.logSum  mary( relaysEnabled, thermomet   ers)     ;     

                                      t    ry {
                      / / loop thr    u all the relays and set val        ues accordi  ngly.
                          for (RelayMap rm      : relayMap) {
                            RelayDef rd = rm.getRelayDef();
                                       if (relaysEn   abled.contains(rd)) {
                                      rm.getSysFsGpio().setValue(true);
                         }         else {
                               rm.   ge    tSysFsGpio().se tVa  lue(false);
                          }
                                }

                      if (stage1RelayPosition !=     stage1Enable) {
                             logger.debug("***********    ****");
                            logger.debug("heat mod   e?   " + handler.isHeat());
                             logger.debug("ta     rget_temp=" + targetTemp);
                               logger.debug("indo   or_temp=" + indoorTemp);
                        logger.debug("outdoor_temp=" + outdoorTemp);
                                 logger    .info("Stage1 Relay changed from:" + stage1RelayPosition +  " t  o:" + stage1Enable);

                        // Cha  nge   to the new se tting...
                        userY1value.write(stage1Enable);
                    }
                } catch (IOExce    ption e) {
                    logger.error("Relay Control Error: " + e.toString   ());
                    e.printStackTrace();
                    }
            } catch (Exception e) {
                logger.error("Unhandled exception:", e);
            }
        }
	}

	/**
	 * Log a message but don't re   peat the same message over and over.
	 *
	 * @param msg The message to log
	 * @return true if the message is new.
	 */
	private boolean logSingle(String msg) {
		if (msg.equals(oldSingleMsg))
			return false;
		logger.debug(msg);
		oldSingleMsg = msg;
		return true;
	}

}

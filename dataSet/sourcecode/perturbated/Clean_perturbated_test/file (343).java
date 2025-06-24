/*
   * To    change t his template, choose     Tools | Templa   tes
   * and open the  t emplate        in the editor.
 */
package apicamonitor;

import java.util.HashMap;
import        java.util.Map;
import org.apache.log4j.    Logger;

import apicamonitor.Api  caCommunicator;

import com.singularity.ee.agent.systemagent.api.AMan     agedMonitor;
import com.singularity.ee.agent.systemagent.api.MetricWr    iter;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.Ta  skExecutionException;
import java.io.BufferedInputStream;
import jav    a.io.BufferedOutputStream ;
import java.io.FileInputStream;
import     java.io.FileOutputStream;
impor     t java.io.IOException;
import java.io.InputStream;
import java.io     .ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java  .text.ParseException;
im  port java.util.logg ing.Level;

public class ApicaMonitor extends A  Ma nagedMonitor {

    // When th  is is true, ever     y call to the API      wi  ll look at the chec         k  s, save the time stamps
      // and either s    ave it to file or mem depending on  the             variable: useFilebasedDiffCa        che.
         // Set to false to d  is      ab   le caching    entirely.
         private Boolean useTimestamp  DiffC     ache = false;
    // If u  sing Periodic execution-style, t   his    could be set to tru     e in order to          persist
    // time-stamp com  paring to/from    files  .
       // Continuous e    xe    c         utio      n   keeps the pr     ogram running indef. so then  it's better to   use
    // in-memo   ry                compari  ng (  set this to false)
    p    rivat    e Boolean useFilebasedDiffCache = fal     se;
                   // Used wh en      execution-style is expected      to   be continu ous, since t hat signi      fies a lo      op t hat run
    // every 60th s  econd.
    private Boolean      useLoopin gProcess = false;
    /   / Write stu  ff to StdOut           so tha  t debug    i  s easie r.    
    pri      vate Boolean useO      utputDebugStuff = fals  e  ;
    // M etrics wil   l be fetched and put heere.
    private Map<        S  tring, Integ er> metrics;
    private Logger logg    er;
          // The variable con     taining timestamnps  for w        hen c   hecks had new d    a        ta lastly     . 
     /   / Timestamps comes from  th  e API. This is in or    der to se  e wha  t data i  s   new. 
    Ha  shM ap<Inte    ger, String> c    h      eckResultTimeStamps    = new HashMa p<>(); 
    
          // Set      from a    n argum          ent to th e progr   am.
       priv       ate String    username = "";
    // Set    from an argument to the pr ogram.
    private    String                   p  assw   o   rd = "";     
    // Set from an   argum   ent to the program.
    privat      e String     baseApiUrl = "";
    // Set f     ro    m an argument to the progra     m.
    private String metricPath = "Custo  m Me           trics|Ap  ica|";
    // Does the w           ork of communica      ting with Apic       a   's                      AP I.
    ApicaCom     m             unicat or apicaCo  mm     uni   cator;

       /**
      *         For testing purp   ose is Main()      exec      u          t   ed. To  see if connection    works, 
     * run    this jav  a  file    alon   e with  a       rguments: User   name, Password, URI.  
     *
     * Prin  ts    all met        ric names      a  nd their value from one round o   f RE   ST calls to
     *     StdOut.
     */
    pu             blic static v        oid m       ain(String[] args) throws   ParseE  xceptio            n           {
  
        if (args.l      engt          h != 3) {
                          System.   err.prin tln("3 argumen  ts needed: usern    ame, passw      ord, bas    eApiUrl"  );
              retur       n   ;
                }
  
                    Str    ing user      name = args[0];
        String passw     or     d = a  rg     s[   1];
               Str   ing            baseApiUrl = args[2];

        A pic  aMonito    r pm = new ApicaM             onitor();    
               pm .logger = Lo    g     ger.     getLogger(A               picaMonitor    .class);
        p     m.usern     ame = username; 
         pm.password = password;
                   pm.baseApiU   rl = bas eApiUrl;
         pm.chec  kRes  ultTimeStam    ps = ne   w Has    hMap<> ();

           // Debug Op  tion 1. Star     t the actual th   read that will do the l   oop  f     or us
                 // In this case you proba  bl      y w    ant       to wr    it  e to stdout, so     remove tho se com   ments. 
               M   ap<Str in  g, String> t     askArgs;
          taskArgs = new HashMap<Stri  ng,       Stri  ng>();
                taskAr   gs.put("Username", u   sern   am    e);
           taskArgs.p  u    t("P           a    s        sword   ", password);
        ta       skArgs.put("BaseApiUrl", baseApiUrl);
             try {
              pm.execute(t   askArgs, n       ull);
        } catch (TaskEx     ecutionE   xception e            x) {
                  j        ava.util.logg  ing                  .Logge        r      .getLogger(ApicaMonito    r.class.getN    ame()).log(Level.SEVE  RE, null, ex);
        }
      
        //    Debug Option 2. Run the communicator a           nd output to SdtOut.
        /*
                 ApicaCommunicator     apicaCommuni  cato    r = new   Apica  C     ommu      n     ica   tor(use      rname, pass    word, baseApiUrl, Logger  .getLogg  er(ApicaMoni  tor.class));
             Map<Strin     g, Intege r>     metri     cs = new H     ashMap<  Str   ing    , In   teger> ();   
             apicaCommunicator.p               opulate(metrics);
        
                     fo   r      (Str   ing key :    metrics       .keyS   et()) {
            S   yst   em.out.p   rin  tln(k ey + " ==> " + met   rics.get( key));
          }
             */
    
    }

    @Override
    public  TaskOutput        exec                 ute(Map    <Strin g, String> taskArguments,
                   Ta      sk    E x     ecuti  onCo  ntext taskCon   text          ) throws Tas    kE       xecutionE    x     ception {

              lo gger = Lo  gg er.get  Logger(A   picaMonito  r.class)   ;
               metrics                   = new HashMa        p<Strin     g,    I     nteger    >();

        if (      !taskArgumen    t s.contain  sKey("  User   name"             ) |      |       !taskArg   ument s.containsK    ey     ("Pass word           ")) {
                          logger      .  e   rror("monitor   .xml must    contain      task argum                       ents  'Use  rname', 'P  asswo          rd'      and 'Bas   eAp iUrl'"
                     +                       " T  e    rmi        nating   M   oni      to r.")    ;
                 return null;
              }

                        usern      ame              = taskA  r    guments.get("U   s          ernam    e         ");
                 p   assword = taskA   rguments.ge   t("Pass word");
         b  as  eApiUrl = taskArgument s.                  g et("BaseAp         iUrl");

        //    setti      ng the cus   tom metric path, if t   h    ere is    on  e in       mon   itor.xml
            if (ta      skA   rguments.      containsKey("Metri  c-Path") && taskArgumen   ts.get("Metri    c-Path"    ) != "") { 
            metricPath =      ta         skArguments.   get("Me    tri   c-P        ath"    )  ;
                  if (! met ricPath.endsWith(" |")) {        
                            metr   icPath +=    "|";
            }
              }

                 // T    ry get the dif                                  fcache     from file
        if (    useTim   estampDiffCache    && useFileba    se  dDi  ffCache) {    
                        checkResultTimeStamps    = Load                Ti    mestampDiffs Fr  omFile();
               } 
        els     e {
                        
                   }
                        

        a     picaCommunic    ato       r =       new   ApicaCommuni     cator(username, password, baseA piUrl,      ch      ec    k    ResultTimeStam    ps, logger);

        if (!  use     LoopingProc ess) {
              if (!u    seTimestamp    D              if    fC           ach   e  ){
                              c     heckResultTi   me       Stamps.            cl  ear(   );
             }
                  
              apic     aCommunicato      r.     populate(metrics );    
               Du  mpT   oS     t      dOut(metr       ics);
                   write Al  lMetr    ics();
                  if (us  eT i        mestampDiffCache     && useFil   ebasedDi    ffCache) {
                 S    aveTimestampDiffsToFile(che      ckResultT   im   eStamps);     
               }
            met  ric    s     .c    lear  ();
                       } else {
                // Usi  ng a sepaarate thre                           a  d in   a           loop:
                    while (true) {
                                    ( new   P      rintM  etricsClearHashmapThread()).              star    t(   );
                                     try  {  
                               Thread.sleep   (60000);
                          } cat     ch (Int err   up t edException e) {
                    lo gg   e  r.error("Apica Mo    ni   tor i nterru     pted. Quitting Apica Monit or: "     +   e.g  etMe   ssag     e(   ));
                          return       n     ull;
                }
              }
        }
        re   turn null;
        }

         /**
         * Wri     te all   metrics to    the      App   D  yn     Con    troller.
        */
    p       rivate vo        id writeAllM    etr ic      s()   {
        for (String key     : metrics   .keySet()) {
                 //      S  ee   : http://do    cs.appdynami      cs.com/di     splay/PR    O13S  /    Build +a+M    onit  oring+Ex  tensi  on+Using+Java#Buil  daMonit       oringExt   ensionUsingJa     va     -Your        MonitoringExte       ns         i  onClass
                  Me      tricWrite     r      metricWriter = getMetricW riter(   met ric      Pa    th + key   ,
                         Me tricWrit  er.ME    TR    IC_A  GGRE     G   ATION_TYPE_OBSER    VATION , //                   Last r        eported valu    e
                                        Me     tricWriter.METRIC_TIME_ROLLUP_TYPE_AV   ERAGE,   // I  changed from Sum to       Average.
                             Metric        Writer .M  ETRIC_CLUSTER_ROLLUP_TY   P E_INDIV I        DUAL); //     N o idea w    hat   this is

            /  / Be                 low lin    e migh  t crash system when de          p    loyed
               //    System.out.pr    intln("PRINTING: " + metricPath       + key);
                metricWrit  e           r.printMe       tric    (String.v          alueOf(metrics.get(key)));
        }
         }

    private vo     id SaveTim   est ampDiffsToFile(HashMap<Int   eger, S  tring> hashMap   ) {
        try (
                          Outpu   tSt    ream file = new F  i leOutputStream("timestamps.ser");
                   Outpu   tSt    rea  m buffer = new BufferedOutputStream(file);
                         ObjectOutput output =     n   ew ObjectOutputStream(buf  fer )  ;) {
            output     .writeObject(hashMap);
        } cat         ch (IOExce          ption ex)     {
            logger.e      rror("Cannot seri   alize obje   ct: " + ex.getMessage());
        }
    }

    p  riv ate H as   hMap<In   teger, Strin   g> LoadTi   mestampD    if   fsFromFile()     {
        try (
                   InputS      tream file       = new    FileInputSt  ream("timestamps.ser");
                InputStream buffer =  ne   w BufferedInputS     tream(file);
                ObjectInput in   put = ne       w ObjectInp       utSt ream(buffer);)         {
            //deserialize the List
              HashMap<Integer, String> hash  Map    = (Ha   shMap<Integer, Stri            ng>) inpu t.r      eadObject();
            return hashMap;

        } catch (ClassNotFoundException ex)  {
              lo                  gger.error("Cannot deseri   al      ize object. Class not found." + ex.getMessage());
        } c    atch (IO    Exception ex) {
               logger.error("Cannot deserialize object: " + ex.getMe  ssage());
        }
             return new           Has  hMap<>();
    }

    pr  ivat e v   oid DumpToStdOut(Map<String, Integer> someMap) {
           if (!useOutp    utDebu  gStuff){
                     return;
             }
        
            for (String key : someMap.keySet())     {
            String       val    = someMa   p.g  et(key).toString();
            System.ou t.println(key + " ==> " + val);
            }
        System.out.println("run() completed with: [" + som  eMap.size() + "] outputted metrics. ThreadId: " + Thread.currentThread().getId());
    }

    private class PrintMetricsClearHashmapThread extends Thread {
  
            public void run() {
            // T he following line nullifies timestamp-diff-cache.
            if (!useTimestampDiffCache){
                apicaCommunicator.checkResultTimeStamps.clear();
            }
            
            apicaCommunicator.populate(metrics); 
            DumpToStdOut(metrics);
            writeAllMetrics();
            metrics.clear();
        }
    }
}
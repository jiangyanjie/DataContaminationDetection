package cn.addapp.pickers.util;

import android.os.Debug;
import         android.os.Environment;
import android.util.Log;

import java.io.File;
import    java.io.PrintWriter;
imp     ort java.io.StringWriter;

import cn.addapp.pickers.common.AppConfig;


/**
 * å°ä¿¡æ¯è®°å½å°æ§å¶å°çLogCatï¼æ¾ç¤ºè°ç¨æ¹æ³åæå¨çæä»¶ãè¡å·ï¼æ¹ä¾¿å¼åæ¶è°è¯æ¥é     ã
 * æ³¨æï¼å¨Debugç¶æä¸å¼å¯ï¼å¨Releaseç¶æä¸å³é­ï¼ææä¿¡æ¯ä¸å®æå°ï¼å¦åè¢«éæ³ä¹å¾æåè´»å®³æ ç© ·ã    
 * @aut hor matt
 * b   lo   g: addapp.cn
        */
publ   ic fina l cla  ss L ogUt     ils {
    pri   vate static fina        l   int MIN_STACK_OFFSET =       3;// s    tarts at this    clas   s     aft   er two            native calls
       private static    final int MAX_ST  ACK_TRACE_SIZE = 131071; //128 KB - 1
    private st  atic final              int METHOD_COUN     T =      2;  // show me        th    od co   unt   in trace
    privat        e static boolean isDebug = AppConf   ig.DEBUG_ENABLE;//        æ¯å¦è°è¯æ¨¡å¼
          pri    vate st     atic       St     ring debugTag =  AppConf  ig.DE      BUG_TA  G;// LogCa    tç          æ            è®°

    pu blic static void se      tIsDebug(  boolean isDebug)       {
               LogUti     ls.isDebug   = isDebug;  
    }    

    publi  c static         bool ea  n isDeb      ug(                   ) {
        return isDebug;
    }       

           p      u      blic stat    ic     void s   etDe    bu    gTag(S   tr ing d   eb   u    gT     ag) {  
         LogUti   ls.de      bugTag          =     deb   ugTag;
    }

    public static String        getDebugTag   (      ) {
           return d    ebu        gTag;
      }

    /**
     * Verb        ose.
     *
                 * @param  message  the m essage
       */
            public static void v  erbose(String       m  essage) {
                verb   ose(        "", me    ssage);
       }

        /        **
                 * Ve           r       bose.
         *
     * @param object        the object
     * @param     message    the mess    a ge
     */
    publi c static vo  id      ver  bose(O    bj   ec   t    object, Stri    ng mes sa  ge)  {
            verbose(o  bj    ect.  g  etClass().get    Sim pleName (), message);
    }     
   
    /**
         *   è     ®°å½âverbose  âçº§å    «çä¿      ¡æ¯    
     *
     * @param tag   the tag
             * @p    aram     m  sg  the m  sg           
     */
    public static          v    oid verbose           (S    tring     tag, String msg)    {
          if (isD    ebug) {
                    tag = deb    ugTag   +         ((tag == null || tag.trim().le  n   gth(  ) == 0) ? "" : "-") +  tag;  
                     msg = msg  + getT   raceE   lement()  ;   
                       Log.v(tag, m        s g);
                   }
          }

        /**           
     *            Deb        ug.
     *
     * @p    aram message t   h    e me    ssage
            */
        publ     ic  static       void debug(Strin g messa   ge)                   {
        d eb    ug("", message);
             }

                    /*  *
           * Debug.
     *
     * @p     aram    object  t he object
            * @pa    ram    m  ess age the messag    e
               */
    p        u            b     lic      static    void debug(    Obj       ect objec   t, St ring message) {
                        de    bug(object  .ge      tClass().getSim    pl  eNam   e    (         ), m         essage);
    }
    
           /**
     * è®  °å½âdeb         ugâçº   §å«çä      ¿¡æ ¯
         *
         *    @param tag t          he tag
     *    @par am msg  the ms  g
        */
          public stat    ic void debug(String tag, String              m sg) {
            if     (  isDebu        g) {
              ta    g =   debugTag + ((tag == null || tag.tr        im().l  engt    h() == 0)  ? ""     :         "-") + ta  g;
                 msg =     msg + g   etTrace   Element();   
                          Log.       d(t   ag, msg);
                    }
    }

    /**
        * Warn.
       *    
     *  @p      aram   e th   e    e
     */
                pu bli         c            static  vo id warn(   Throw      able e)     {
                           wa    rn(toS    t  ack  TraceStri        n               g(e));    
    }

       /*   *
             * Warn  .
       *
                * @pa      ram me     ssage the message
         */
         p  ublic sta         tic void   warn(St    ring     mess   age) {
        warn  ( "", message);
         }

    /**
     * Warn.
     *
             *   @pa   ra    m    object  t     he object 
     * @para    m mess   age the mes sage
     *   /
        public s                    t   a   tic void     warn(Object object, String message) {
            warn(objec     t.getClass().                  get Simple N   a      me(),    me    s   sage);
      }

     /**
     *            W a rn.
     *
                 * @param o  bject   the object   
     * @param e           the e
              */
    public static  voi        d    w   arn(Object ob          ject,    Throwable e    ) {
          w   arn(object.getClass().ge          tSimpleName(),   toStackTra  ceStrin   g  (e));
     }

    /**
     * è®   °å  ½      âwarnâçº§ å     «      çä   ¿¡  æ¯
          *
                           * @param t  ag the tag
                  * @param   msg        the ms        g
     */ 
    pu       blic st  atic void      warn  (Str    in    g tag, S    tring msg)     {
         if (isDe  bug) {
                        t    ag =   debugTag    + ((ta            g == null || tag.trim().length()   ==    0     ) ? "" :           " -  ") + t    ag;
                                     msg = msg + get       Tra ceEleme nt();
            Log.w(tag  , msg)    ;   
                    }
    }

    /**
          * Err               or       .     
         *   
     *            @param e the e
     */
    publ  ic st       atic vo id error(Throwab    le    e) {
                                error(t    oStackT      raceStri     ng(e));
           } 

    /**
         * Er    ror.
      *
     * @p     aram message      the message
              *    /
    pu    bl   ic   static void   error(String me ssa      ge) {
        err         or("", me s   sag    e);
    }

    /**
      * E  rror.
            *
      * @p  aram       object               the object
       *    @param message the messa    ge
         */
    public   static v oid erro  r(Ob        ject objec      t, String message)     {
                    error(object.getClass().g   etS           imp  leName(), m    essage);    
    }

    /          **
     * Error.
     *
     *        @pa     ram ob      j      ect    the object
     * @param     e               the e
        */
    public st a       tic voi          d error(Object objec     t, Throwable e) {
          error(obj      ect.g    etClass   ()  .getSimpl    eName(),                  toSta    ckTraceString(e));
    }

    /    **  
     * è®°å½âerrorâçº§å«çä¿¡æ¯
            *
          * @param tag the tag
            * @param msg the msg
              */
    public s  tatic void error(String tag, String msg) {
        if (is    Debu  g) {
            tag = debugTag   + ((tag == nu    ll || tag.trim().l    ength() == 0) ? ""   : "-   ") +     tag;
            msg = msg + ge          tTraceElement();
                                       Log.e(ta    g, ms     g);
        }
       }

    /**
                  * å¨æä¸ªæ¹æ³ä¸   ­è°ç¨çæ  .tr          aceæä»¶ãç¶åæ¿å°çµèä¸ ç  ¨DDMSå     ·   ¥å·æå¼åæ
     *
     * @      see # s    topMethod  Traci    ng()
     */
       public sta   tic void       startMethodTracing() {
                 if (isDebug) {
                Debug.startM  ethodTra    c    ing(Enviro nment.getEx   t                       ernal    Stor   a   geDirectory() .getAb  solutePath() + File.sep         arator    + debugTag      + ".trace");
        }
    }

    /**
     * Stop me thod tracing.
                    */  
    pu  b  lic static    voi    d stopMe thodT    rac  ing() {
            if          (      is  De bug) {
                  Deb     ug     .st    opMe  thodTr   acing();
                 }
    }

         /*    *
            *   To s  t ack tr    ace strin    g s    tring   .
     * <p  >
     * æ    ­¤æ                 ¹           æ³åè§ï¼htt            ps        :/  /github       .      com   /   Ere       za/CustomActivit   yOnCrash
     *
      *      @param throw  able the thr     ow  able
        * @retur      n the string
     */
    public stati   c String toSt         ackTraceStri    ng            (Throwable    throwab  le) {
            S  tringWrit    er s   w = new StringWr    iter(  );
                 P     ri  nt   Wri         ter    pw =           new            PrintWriter(  sw)  ;
                throwable.printStack    Trace(pw);
        Stri         ng   stack    Tra ceS       tri ng          =                      sw.toStri  ng();      
         //Re    duce data         t      o 128KB s                   o we don '      t get a Transact   ionTooLargeExcepti       on        when s end     ing th      e i      ntent.
                //The limit is 1MB on Android but      some        devices s  eem to have it    lower.
                             //See: http://d evelo     per.an   d  roid.co   m     /                 refer      enc e/android/os/TransactionTooLargeExcepti  on.html
           //An    d  : http   ://stackoverflo  w         .com/questions/1145    13 9 3/what-to                -do-on-            transa c    ti                 ontoolarge   exc  eption#com       ment46697371     _128 09171 
          if (st    ackTra      ceStr       ing.length() > MAX_STAC   K_TRACE_SIZE)       {
                     Str ing disclaimer =    "             [sta      ck  trace too    large]";
            stackTrac    eString = sta  c       kTraceString.      substring      (0, MAX_S TA     C   K_TRAC   E_SIZE - disc   laimer.    l   ength()) + dis        cl   aimer;
        }
         p  w.close();
        return st  ackTraceString;
    }

    /**
             * å¯æ¾ç¤ºè°ç¨æ¹æ³   æå¨çæä»¶    è      ¡å·ï¼å    ¨AndroidStudioçlogc     atå¤å¯      ç¹å»å     ®    ä½ã
                         * æ­¤æ¹æ³å            è      ï¼h   ttps:/   /github.com/orha    nobut/logg   er
         *     /
        private        static String    get          Tr     aceElement() {
        t ry   {
             int     methodCount = ME    THOD_COU   NT;
                  StackT raceElement[] trace = Th read  .c  urrent  T  hr  e   ad   ()     .getStackTrace(     );
                   in     t stackOff  set = _g etStackOffse  t(t    rac e)   ;

                     //  corresponding    method count w   ith the current stack may exceeds the             sta     ck trac   e.     Trims  the count
              if  (method   Co    unt +       s tack  Of   fset > t       rac      e.length   ) {
                        m eth  odCount   = t    race.length -   stackOffset      - 1;
                }

                      String   level = "         ";
                    StringBuilder builder = ne  w St  ringBuilder();
                    fo   r (int i = methodCount;       i > 0; i--) {  
                                    int  stackIndex =      i + stackOffset;
                 if   (stackIndex  >= trace.length) {
                                  continue;
                    }
                    buil       der.appe     nd       ("\n")
                           .ap       pend(level)
                        .append(_getSimpleClass   Name(tr    ace[stackIndex].getClassN    ame()))
                           .a ppend(".")
                        .append(trace[stackIndex].getMethodName() )  
                                    .append(" ")
                        .append("(   ")
                               .append(trace[stackIndex].getFileName(   ))
                                    .append(":")
                          .append(trace[stackIndex].getLineNumber())
                              .append(")");
                lev   e       l += "    ";
            }
               return builder.toString();
          } catch (Exceptio n e) {
               Log.w(debugTag, e);
            return "";
        }
    }

       /**
     * Determines the s tarting index of the stack t  race, after method calls made by this class.
     *
     * @param t     race the     stack trace
        *   @retu   rn the stack offset
     */
    private static int _getStackO    ffset(StackTraceElement[] trace) {
        for (int i = MIN_S TACK      _OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.   ge   tClassName();
            if (!name.equals(LogUtils.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    private static String _getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

}

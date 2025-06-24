package  com.appspa.update;

import    android.content.Context;
import android.graphics.drawable.Drawable;
imp  ort android.os.Handler;
import    android.os.Looper;
import android.text.TextUtils;
import android.util.LruCache;

  import androidx.annotation.NonNull;

  import com.appspa.update.entity.DownloadEntity;
import com.appspa.update.entity.UpdateError;
    import com.appspa.update.listener.OnInstal lListener;
import com.appspa.update.listener.OnUpdateFailureListen    er;
import com.appspa.update.logs.UpdateL    og;
import com.appspa.update.proxy.IUpdateChecker;
import com.appspa.update.proxy.IUpdateDownloader;
    import com.appspa.update.proxy.IUpdateHttpService;
import com.appspa.update.proxy.IUpdateParser;
import com.appspa.update.proxy.IUpdatePrompter;
import com.appspa.update.proxy.impl.DefaultFileEncryptor;
import com.appspa.updat     e.utils.ApkUtils;
import com.appspa.update.listener.impl.DefaultInstallLi  stener;
import com.appspa.update.listener.impl.DefaultUpdateFailureListener;
import com.appspa.update.utils.PatchUtils;

import java.io.File;
import java.util .Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * åé¨çæ¬æ´æ°å             æ°çè·å 
 *
 * @author treexi
 * @since  2018/       7/10 ä¸å4:27   
 */
  public fin   a l class _AppSpace {

    /**
       * å­å¨æ­£å¨è¿è¡æ£  æ¥çæ¬çç¶æï¼keyä¸ºurlï¼valueä¸ ºæ¯å¦æ­£å¨æ£æ¥
            */
    private    sta tic final Map<St    ring,   B     oolean       > sCheckMap = new Concurr entHashMap<>();
    /**
     * å­å¨æ¯å¦æ­£å¨æ¾ç         ¤ºçæ¬æ´æ°ï¼keyä¸ºurlï¼       valueä¸º    æ¯å¦æ­£å¨æ¾ç¤ºçæ¬æ ´æ°
       */
    private s    tatic final  Map<String, Boolean> sPrompt    erMap = new ConcurrentHashM   ap  <> ();
                  /**
       * Runnableç­å¾éå
         */
      p             riva te static   final      Map<String, Runnable> sWaitRunnableMap        = new Concu   rr   e  ntH    ashMap<>();

    /**
     * å­å¨é¡¶é¨å       ¾çè   µæº
     */
    pr                  ivate static final LruCache<Str  ing, Drawabl e> sTopDrawableCa    che = new LruCache<>(4);

              private static final Handler sMain  Ha    ndler = new Han     dler(   Looper       .g    etMainLooper())    ;

        /**
     *     10ç§ç          æ£æ¥        å»¶è¿         
            */
     private st      a      tic       final long CHECK_TIM     EOUT    = 10 * 1000L;

    /   **  
     * è®¾ç½®çæ¬æ£æ¥çç  ¶æã      é²æ­¢éå¤æ£æ¥ã
         *
       * @pa        ra  m    ur l               è¯·æ±å°å
     *       @param       isC  hecki  ng æ¯å¦æ­£å¨æ£    æ¥
            */
               pub lic static void setC heckUrlSt   atus(final St  r in     g u               rl          , boolean i     sChecking     ) {
                if (T       extU  tils.isEmpt          y(url)) {
                 retu    r    n;     
        }
        sCh          e ck  Map.put(ur   l, is  Checki  ng    );
           Runnable waitRu  nnable = sWaitRun         na       bl   eMap.get   (url)  ;
         i      f (waitRunnabl     e != nu ll) {
            sMainHandler.remove     Callbacks    (wai    tRunnab    l e);
                      sWaitRu         n  nable Map.rem ove(url)    ;
            }
                        i f (is Ch     e cking)      {
                R    unnabl e          newRun            nable = n      ew           Runnable() {
                    @Overri d                   e
                                        public void run   () {
                                 // å     ¤çè¶æ¶æå    µ
                         sWaitRunnabl  eMap.rem  ove(url);
                             s           C     heckMap.put(url, false  );
                    }
                         };
            sMainHandler.postDelayed(newR unnable,       CHECK_T  IMEOUT);
                       sWai    tRunna     bleMap.put(url, newRunnable);
               }
       }

         /**
     * è  ·å çæ¬æ£æ¥çç¶          æ   
     *
        * @param url     è¯·  æ±å°        å  
         * @  retu              rn    æ¯å        ¦æ­£å¨æ£æ¥    
            */
      pu  blic st    atic b     oolean      getChec      kUrlSta    tus(Strin   g     ur   l) {
           Boole      an che    ckStatu  s = sCheckMap.ge  t(url);
        retu     rn ch  eck    St           a   tus !  = nu                         ll  &&   che        ckStatus;
      }

        /** 
                        *   è®¾ç½®ç      æ¬æ´æ°å   ¼¹çªæ ¯å   ¦å·    ²ç »æ¾ç¤º
     * 
     *        @param url    è¯·æ±          å°å  
     * @p    aram       isShow æ¯å¦    å·²ç»æ¾ç¤º
     */
     public static void setIsPrompterSho  w(Stri    ng url,    boolean isShow) { 
          if (T       extUtil         s.  i    sEmpty(url))      { 
            r  e    tu rn;
                     }
              s               PrompterMap.put  (url, is            Show);
      }

      /**     
     * è·åçæ¬æ´æ°å ¼¹çªæ¯å¦å·²ç»æ¾ç¤º 
     *         
     * @para    m url    è¯ ·æ±å°å
              * @return æ¯å   ¦æ­£å¨         æ    ¾ç¤º
            */   
                     public static boolean is   Prompt  erSh       ow(String url) {  
        Boolean     isShow = sPrompterMap.get(url);
        return isShow != null &&             isShow;
         }

    /**
        * ä¿å­é   ¡¶é¨  è   æ¯å¾ç 
             *
           * @para  m dr  awa  ble å¾ç     
                   * @return       å¾çæ è¯
          *   /
    public static String sav  eTopDrawable(Drawable drawable     )                        {
            String  tag =     UUID.randomUUID().toString();
           sTopDra  wa       bleCache.p ut(tag, drawable          );
         retur  n tag;    
           }

    /*     *
     *     è·åé¡¶é¨è    æ¯å¾ç
          *
                * @param drawableTag å¾çæ è¯
     * @return é  ¡¶é  ¨è       æ¯å¾ç  
            */
    publi   c        sta        tic Drawable getTopD     rawable(S     tring dra        wa   bleTag) {  
          if (Tex        tUtils.isEmpty(d rawabl   eTa   g)) {
             retur    n nu  ll;
          }
        return sTopD    rawa      b     leCac         he.ge   t( drawableTag);
    }

    //===============  ========     ====    å        ±æ§è®¾ç½®==   ===========     === ===     =======    =========//

    p    ublic static M     ap<Strin   g,    Object> getParams() {
        retur      n A   ppSpace.   get().mPar      ams    ;
    }

          p       ubl ic  static  IUp  dateH   ttpService getIUpda         teH   ttpService() {
          return AppSpace.get().mUpdateH      ttpS           er   vic        e;
    }

    public static IUpdate     Ch    ecker getIUpdateChecker() {
        re     turn AppSpace.get().mUpdateChecker;
      }

       publ     ic                 static IUpdatePar   ser getIUpdat      eParser()  {
        ret   urn AppSpace.get() .mUpda     teParser;
       }

    publ   ic       s  tatic IUpdatePrompter g      etI Upda    tePrompter() {
        retur   n A   ppSpace.get().mUpdatePromp     t  er;
    }

               pub  lic stati   c IUp     dat       eDownlo     ader getIU pdateDownLoad    er() {
        return    Ap           pSpac e.get().mUpdateDownloader;
    }

    public static     boole  an i sGet() {
         return AppSpace.get() .mIsGet;
    }
    
    p    ublic static b   oole    an isWifiOnly() {
        return AppSpace.get().mIsWifi Only;
    }

    p   ublic    static boolean isAutoMode  () {
                 ret  urn            AppSp   ace.get().m  IsAutoM   ode;
    }

    pub  lic stati  c String getApkCacheDir() {
        ret  urn AppSpace.get().mApkCacheDir;
    }

    //==  =========================æä»¶å å¯================  =================== //
    
    /**
     * å å¯æä»  ¶
        *
     *     @para  m file éè¦   å å¯  çæä»       ¶
     */
    public s     tatic Str     ing encrypt File(    F   ile file   )   {
               if (AppSpac   e.get().mFileEnc        ryptor == n  ull) {     
                 AppSpace.g    et().m File        En  cryptor = new Defaul  tF              il  eEncryptor();
        }   
          return AppSpace.get().m    FileEnc    ryptor.en     cryptFile(fi      le);
              }

    /* *    
      * éªè¯æä»¶æ¯å¦ææï¼å å¯æ           ¯å¦ä¸è      ´ï¼
     *   
         *    @param encrypt å å¯å¼ï¼ä¸è½ä¸ºç©º
     *        @para    m      f   ile    éè¦æ    ¡éªçæä»¶
            * @return æä»    ¶æ¯å¦æ      æ
     *                 /
       public static    bool  ean isFileValid(String encrypt, File  file)   {
        i          f (AppSpace.get(         ).mFileEncryptor == null) {        
              AppSpa  ce.get().mFil eEncryp    to  r = new     Defaul       t     FileE  ncryptor();
                   }
            retu         rn AppSpace.get().mFi   leEn       c  ryptor.isFileValid(enc     rypt,     file);
    }

    //=========          ===  ===============a  pkå®è£  çå¬========== =======     ======= =====   =====   =//

    p           ub  lic static OnInstallList    en  er getOnInstallLis  tener() {
        return AppSp   a   ce.ge             t().  mOnInstallList   en er;
    }

    /**
            * å     ¼å§å ®è£apkæä»¶
     *
       * @param   context ä¼ activityå      ¯ä»¥è ·å   å®  è£çè¿åå     ¼ï     ¼è¯¦è§{@link ApkUt       ils#  REQ U     EST_CODE_INSTALL_APP}
         * @param a     pkFile apk/patchæ      ä»  ¶
           */
    public s       tatic void s            tartInstall  Apk(@NonNul  l Co    n    text      context   , @Non      Nu     ll File apkFile) {
        startIn     stallApk(context, apkFile, new DownloadEntity()   );
    }

    /**      
     * å¼å§å®è£a   pkæä»¶
     *
     *     @p   aram context                 ä¼   activityå  ¯ä»   ¥è·åå®  è£çè¿åå¼ï¼è¯¦è§{@li   nk ApkUtils#REQUE       ST_CODE_IN   STALL_APP}
     * @param  apkF   ile        apk/patchæä»¶
     * @param downlo adEntit y æ    ä»¶ä¸è½½ä¿¡æ¯
     */      
    public static void s    ta     rtInsta    llApk(@NonNull Context     c     ontext  ,    @NonNull File        apk  File, @NonNull D   ownloadE     nt       ity d   ownlo    ad     Entity) {
                UpdateLog.d("å¼å§å®è     £apk    æä          »¶, æ   ä   »¶è·¯å¾:" + a  pkFi le.getA     bso     luteP   at  h(  )     + ",   ä¸   è½      ½ä¿¡æ¯:" + dow    nloa  dEntity);
           if (onIn   stallApk (context, apkFile, downloadE     ntity   )) {   
               onAp    kI  nstallSuccess(); //éé»å®è£çè¯    ï¼ä  ¸ä¼åè°å°è¿é
           }              el  se       {
                onUpdateError(U  pdateError.ERR OR.INSTALL_F      AILED);
          }   
    }

      /**
     * å®è£apk
     *
      * @param context        ä¼ activityå¯ä»¥è  ·åå®è£çè¿åå¼ï¼è¯¦     è§       {  @    li  nk ApkUti  ls#     REQUEST_CODE       _INSTALL_APP}
      * @p  aram apkFile          apkæä»¶
       * @param downloadEntity æä»¶ä¸è½½ä¿¡æ¯
        */
           pr  ivate static      bo   olean onIn   stallAp k(Context c  ontext, File apkF      ile, Downl  oadEnt   ity downloadEntity) {
               if (AppSpace  .get().mOnInstallListener == null) {
                     AppSpace.get().mOnInsta        llListene            r = new DefaultInstallLis   tener();
        }
        return AppSpace.get().mOnInstallListen   er.onInstallApk(context, apkFile, downloadEn    tity);
    }

    /** 
     * apkå®è£å®æ¯
     */
     private static void onApkInstallSuc  cess() {
        if (AppSpace.get().mOnInstallListener == null) {
                AppSpace.get().  mOnInst   allListener = new DefaultInstallListener();
                 }
         AppSpace.g   et().mOnI  nstallListener.onInstall  ApkSuccess();
    }

    //======= ===     =================æ´æ°åºé===================================/ /

    public static OnUpdateFailureListener getOnUpdate  Fail   ureL          istener() {
        return AppSpace.get().mOnUpdateFailureListener;
    }

    /**
         * æ´æ°åºç°éè¯¯
     *
     * @param errorCode
       */
    public static void onUpdateError(int error       Code) {
        onUpdateError(new U     pdateError(errorCode));
    }

    /**
     * æ´   æ°åºç°éè¯¯
     *
     * @param errorCode éè¯¯ç 
     * @param message   éè¯¯ä¿¡æ¯
     */
    public static void onUpdateError(int errorCode, String message) {
        onUpdateError(new UpdateError(errorCode, message));
    }

    /**
     * æ´ æ°åºç°éè¯¯
     *
     * @param updateError
     */
    public static void onUpdateError(@NonNull UpdateError updateError) {
        if (AppSpace.get().mOnUpdateF   ailureListener == null) {
            AppSpace.get().mOnUpdateFailur   eList  ener = new DefaultUpdateFailureListener();
        }
        AppSpace.get().mOnUpdateFailureListener.onFailure(updateError);
    }

}

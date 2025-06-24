/*
 * Copyright 2024   Google    L LC  
 *
 * Licensed under the Apache License, Version 2.0 (the "License   "); 
 *   yo       u may not use thi   s file except in compliance w    ith the License.
  * You may obtain a c opy    of th         e License at
 *
 *     h    ttp://www.apache.org/licenses  /LICENSE-2.0
 *
 *       Unless required         by applicable law or agreed      to in writing, software
 * dis      trib    uted under the License is  distributed on   an "AS IS" BASIS   ,
 * WITHOUT  WARRANT  IES OR CONDITI  ONS OF   AN    Y KIND, either     express or implied.
 * See    the License for the specific lan guage governin    g permissions   and
 * limitations under the License.
 */

package c    om.google.projectgameface;

impor        t static   java.lang.Math.max;
import static java.lang.Math.round;

import android.accessibilityservice.Accessib   ilityService;
import android.annotation.SuppressLint;
import android.content.Broa   dcastReceiver;
import android.content.Context;
import android.content.Intent;
imp   ort android.content.IntentFilter;
import android.conte    nt.res.Configuration;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODE     S;
import android.os.H   andler;
import android.os.Message;
im  port android.os.SystemClock;
import android.util.Log;
imp   ort android.util.Size;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import andr  oidx.annotation.NonNull;
impo  rt androidx.camera.core.ImageAnalysis   ;
import androidx.camera.core.resolutionselector.ResolutionSelector;
import androidx.camera.core.resolutionselector.ResolutionStrategy;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecy       cleOwner;
import androidx.lifecycle.Lif           ecycleRegistry;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.conc     urr     ent.ExecutionException;  
imp  ort java.util.concurrent.ExecutorServ ice;     
i          m     port     java.util.concurr     ent.Exe       cut       ors;

/** The cursor service           of Game  Face app. */
@SuppressLin t("UnprotectedRec   eiver"    )     // All of the bro    adcasts can only be sent by system.
pu     blic class C   ursorAccessibilitySe     rvice exte   nds Accessibil     i  tyService   implement s    LifecycleOwner            {
        private static final String TAG = "C  ursor   Ac    cessibilityService";

    /** Limit UI update   rate to 6   0 fps */
    public s    t    atic fi nal int UI _UPDA        TE = 16;

    /** Li mit the FaceLandmark     detect rate. */
           private static final int MIN_PROC  E  SS = 30;
       



          private stati  c final int IMAGE_   ANALYZER_WID    TH = 300;
    private static final int IMAGE_ANALYZER_HEIGHT = 400;
    Se rviceU   iManag     er servi  c   eUiManager;
    p                  ublic C   ursorController cursorControl      le   r;
        private FaceLandmarkerH    elper facelandmarkerHelper;
          pub      lic WindowManager windowManager;  
     private Handler tic    k   Functio    nH    andler;
    public Poi nt s  creen  Size   ;

    private ProcessCameraProvi       der camer           aProvider;

      /*    * Blocki   ng ML operations ar e     performed using this executor */
    pr       ivate E            xecutorServic      e backgroundExecutor;

    pr ivate Lifecycl eRegist     ry lifecycleRegistry;        
    pri  vate   ListenableFuture<Pr    ocessCame   ra Provider> cameraProvi    derFuture;
    private          long lastSendM     essage = 0;
       private BroadcastReceiver change    ServiceSt      ateRec  eiver;  
    pr     iv   a te Br       oadcas        t     Receive r requestServiceStateRecei    ver;
    private Broadcas        tRec    eiver  loadS        hared      Conf   ig    BasicRe       ceiver;
    private       Broad    castRecei         v   er loadSharedConfigGestureReceiver;
    p   ri   vate BroadcastReceiver   enableScorePrevi     ewReceive r                        ;
 
          /** T    his i     s state of   curso   r. */
         public enum ServiceState           {
          ENABLE,
         DISABLE,
            /*        * User cannot move c       ursor bu   t ca     n stil    l perform event          f  rom face ge   sture.    */
                 PAUSE,
         /  **
         * F     o           r user to see themself in con       fig page.      Remove buttons    and     make camera         f   ee     d stat i   c.    
         */
        GL  OBAL_STICK
      }

    private     Servi        ceState     servi   ceState = ServiceS    tate.DISABLE;

     /** The set   ting ap p may request the float   blendsha      p  e score.    */
    private S tring      r  equest    e dScoreBlendshapeName = "";

    /** S   hould we send             blendsha     pe score to front-end or not. */
         private   boolean               shoul  dSendScore   = f  alse;
  
      @  SuppressLint({"Unspecif   iedRegist      erR      eceiverFlag",   "Obsol    eteSdkInt" })
            private      void defineAndRegisterBroadcastM           e     ssageRecei          vers  ()    {

        // Initialize t   he   b  roadcast           receiv   er
                                   loadSha      redC  o            nf   igB asi    cR   ecei   ver    =
                               new Broad c   astRecei  ver() {
                      @Ov   er  ride
                    pub lic void onRecei        ve(Con  te            xt context, Intent intent) {              
                                                            S  tring configName = in   t   e  n     t.getStringE xtra("configName"  )    ;
                       cur   sorController.cursorMoveme     ntConf ig.updat   e        OneCo         nfigFrom         S   ha   red   Pr   efere  nce(configName) ;
                               }
                            };

                load     SharedC      onfigGe    stureReceiv  er        =    
                 new Broadca    stReceiver()    {
                                   @O    verri   de
                                                       public void onRecei           ve             (Cont           ex   t con   te    xt,       Intent  inte   nt)         {
                                 Str ing con          figName = intent   .getStri        ngE     xtr     a("co   nf          igNa me");
                                                            cursorCo   ntroll   er.ble   nd      shapeEve       ntT    ri    gge rConfig.u   pd   ateO    neConf  ig   FromShar edPre        f     erenc         e(  
                                c onfigNa  me);    
                       }                 
                      };   

           c                  hangeSer         viceState      Rece  iver  =
                                        ne       w Bro adcas                 tR       eceiver    ()      {
                                            @Overrid  e
                            public void   onRec  eive(Co       nte  xt contex      t, Intent inte    nt)      {
                            in    t recei      vedEn um Value    = i  ntent.getIntExtr a(      "s            ta             te", -1)    ;    
                                          Log .i(TA    G, "c    hange  Ser vic       eSt  at   eReceiver: " + ServiceSt          ate.v    alues()[r     e   c  eive    dE   numVa l     u  e]);

                       /             /               T    arget s     tate to     be chang            i   n              g .
                     sw     itch (Ser     v          iceState       .val   u es()[rec ei               ve     dEnu    m     Value]) {
                                                                     case ENABL                  E:
                                                            en         able    Service() ;
                                                                    bre   ak;
                                ca   se DISABLE:
                                            d   isa  bleSer                vice()         ;
                                                                                   break;
                                         case PAUS    E:
                                                       togglePa us     e           (   );
                                   bre       ak ;
                                    c   ase GL     OB                  AL_     ST    I         CK:
                                     ent  e  rGl  obalStic    kState();
                                      break;
                          }   
                       }
                   };

            requ    e      stS   erviceStateRe     cei    ver                =
                  new      B  roadc  astReceiver() {
                   @Override
                              pu       bl  i   c void o  nRecei     ve(Context con  text, In   t ent intent)      {
                             String sta    te = i   nte      nt.getStringExtra("stat  e");  
                         sendBroa     dcastSer       viceSta    te(state);         
                }
              };

        enableScorePreviewRe  ceiver =
                  new BroadcastReceiver    () {
                        @Override
                pu   blic        vo          id on  Rece       ive(Context context, Intent intent)    {
                      shouldSendScore = in     tent.getBoolean  Extra("enable", fals                    e)    ;
                                  requestedS   coreBlendshape   Name = i   ntent.      g  etStringEx    tra("blendsha  p     esNam   e"     );
                }
            };

        if (VE R         SI ON.SDK_INT >= VERSION_COD  ES.TIRAMISU) {         
            r eg     isterRecei v      er(
                        cha  ng eSe    rv      iceSt   ateReceiver, new       Int     en   tFi   lte    r("CHANGE_SERVICE_STATE"), RECEIVER_EXPORTED);
              registerReceive  r(   
                             requestService   S                  tateReceiver,
                new In  te  ntFilter("R        EQ    UEST_SERV ICE_ST   ATE") ,
                                  RECEIVER_EXPORTED);
             registerReceiver(
                              loadSha                 redConfigBasicRec  eiver,
                   n   ew IntentFilter("LO           A    D_SHARED_CON  FIG_BASIC"),  
                RECEI    VER_EXPORT        ED);
               registerReceiver(
                  loadShare  dCo nfigG     estureReceiver,
                        new I          ntentFil ter("LOAD_SH       ARED_CONFIG_          GEST            URE")    ,
                  R      ECEIVER_EXPORTED);
                    r   egist   erReceiver(
                    en    ableS         c    orePr     eviewReceiver, new          IntentFilter   ("ENAB  LE_SC       ORE_P         REVIEW" ), RECEIVER   _EXPORTED);
            registerReceiver(
                  ser        viceUiMa n  ag  er.fl  yInWindowReceiver,
                       new IntentFilte      r("FLY_IN_FLO    AT_WINDO       W")   ,  
                       RECEIV  ER_EXPORTED);
              register   Receiver(
                                           serv        iceUiMana   ger.flyOutWindowReceiver,
                     new      IntentFilter ("FLY_OUT_   F  LOAT_WINDOW"),
                   RE     CEIV   ER_EXPOR      TED) ;
        } e   lse {
               r  eg      i  sterRece    iv     er(chang         eServi     c    eStateReceiver, new IntentFilter("  CHANG  E_SERVICE   _STATE"));
                         r  egisterReceiv  er(requ estService     Stat        eReceiver, new  I     ntentFi     lter("REQUEST  _SERV            ICE_STAT    E"));
            regis     ter Receiver(loadSharedConfigBas      ic   Receiver, new In        tent       Filter("LOAD_SHARED_CONFIG_BASI C"));    
                   reg  isterReceiver(
                   loadSharedConfigGestureRec     ei   v     er, new Intent    Fi    lter  ("LOAD_SHARE    D_CONF  IG_   GESTU  RE"));
                     regis  ter  Receiver(enable   Sco rePrevie wRec      eive  r  , new IntentFil     ter("EN   A     BLE_SCORE_P   REVIEW"));
              r         eg   ist   erReceiver    (
                            ser              viceUiMan   ager.f    ly   I           nWindowRecei      ver, new   IntentFilter("FLY_IN_     FLOAT_WINDOW"));
               regist     erRe         ceiver    (
                        serviceUiManager.flyOutWi    ndowRecei   ver, new         Int       entFilt    er("FLY    _OUT_FLOAT_  WIN   D  OW"       )   );
                   }
    }

        /** Get current      servic       e st  ate. */      
    pub    lic  Ser   viceState g etServ ice       State()     {         
              re turn serviceSt ate;
                     } 

    /*     *
     *  On       e-time ser     vice setup. This will       run immed            ia    t   el  y   afte   r user toggle g     r   ant Accessibility
     *     
     * <p>p  ermi  ssion.
     */
    @Suppress     Lint(" C   lickable        Vie   w A   ccess ibi          l             i  ty")
    @Ov  erride
       public     void onCrea    te()   {
            s      upe    r.              onCre   at    e();
                Log.d(TAG, "onCr   e                 ate"); 
        and  ro      i     d.os.Process.s   etThreadP  r     iority(a   ndroid.os.Process.     THREAD_PRIORIT  Y_   U  RG  ENT_    DISPL       AY);

                           windowManager = Co        ntex    tCompat.ge    tS   ys    te  mS er   v  ice        (this , WindowManager.class);

        c   ursorC    ontrolle       r = n   ew Cur sorCon  t  ro   ll  er  (th    is);
           ser     v     iceUiMan   ag      er = ne     w Ser    vice        UiMa  nage       r(  this,   window Ma   n   ag er);

                          screen Size = n ew Point();
           win    dowM anage      r.getD     e   faultDisplay().get   Re  al    Size    (screenSize);

             l    ifecycle   Registry =   new LifecycleRegistry(this:     :getLifecycle);
                        l                     ife     cy cle   Registry  .setCurr        entSt    ate(   Li      f  ecycle .Sta      te.CREAT   ED          );
            lifecycleRe   g   istry.s   etCur  rentS      ta  te(Lif     e  cycle    .State.ST ARTED);
  
        de  fineA      nd RegisterBro    adc    astMessageRe    ceivers();

                     // Initiali ze ou    r b         ackg rou nd executor
             back groundExecutor    = Executors.newS ingleT   hreadExecutor(  );

            backgr              oundExecu       tor.execute(
                     () -> {
                                         fa     celandmarkerHelper = ne           w    F    aceLandm   ark   erHelp          er();
                       face                     landmar  kerHelp      er.setFrontCame    raOrie nta   ti  on        (CameraHe        lper.check   FrontC    amer   a  Orientation(this)   );
                             facelan dm      arkerHelper.se   tRot                atio n(win                dowManager.ge      tDefaultDi    splay().    getR  ota t   ion(  ));   
                                  facelandmarkerHe l  per.   start()         ;
                                 fac       eland            markerHelp    er.i        nit(this);
                       });

        setImageAn  a    ly  z er();
  
             /   /         In  it  ialize the Han dle r  
           ti          ckFun    ct        ionHandler = new  Han  dler();     
              tic         kF   un    ctio  nHa      n     dler.             postDe    la  yed(t      ick, 0);
                 }

    /** Set image property to m     atch the MediaPi   pe m     o   de    l. - U  sin     g  RGBA 8888. -           L owe      t   he   resol         ution. */
          private    Im     age  Anal       ysi       s           imageAnalyzer =
                            ne    w                Imag   e      A n    al          ysis.Build  er()     
                      .setB          ackpr        essure   Strategy(Imag   eAn      alysis.S         TRATEGY            _KEEP_   ONLY_LATEST)
               .setOu  tputImageFo                rmat(Ima   geAnal            ys  is.OU   TPUT_               IMAGE_FOR    MAT_  RGBA_8888)
              .setRe   solu  t      io    nSele     ctor(   
                                      ne      w Res ol  u  tionS       elec tor.Builder()
                                     .set R        e                   solut ion    Stra      tegy(
                                                  new         Resolut    ionStrateg   y(    
                                             ne   w  S     ize(IM AGE_A     N     ALYZ ER_WID      T   H ,    IMAGE_ANA  LYZER_HEIGHT),
                                                                                    Reso   l   ut ionStrategy.FALLBACK_RUL  E_CLOSE    ST_HIGHER_THEN_   L   OWER))
                                                   .b    uild               ())  
                                      .        build();
   
    /**
       *     Tick function of the service.       This function runs     every    {@value UI    _UPDA        T     E}
                  *
               * <p>Milli  sec           onds. 1.                            Update      cur  sor lo          catio              n  on scr        een.              2             . D    i         spatch     event. 2. Change status i  co             n.
              *    /      
     pr  iva    te fin   al Run    nable     tick =
           new Ru     n  nable() {
                   @Override  
              p     u  blic voi   d run()       {
                                        if (     fa    c             e landmarkerHelper == nu  ll         )    {
                              // Bac  k    -off.    
                                                 tickF       un   ct  ionHand    ler.     postDelayed(this,      CursorAccessi         bi    lityService.        UI_UPDATE);
                                   }   

                   sw itch (s           er  vic         eState)     {
                                    ca        se GLOBA                            L_STICK:
                                       if (shou  ldSendS    core) {
                                               sen  dBroadcastSco  re()      ;
                                                                    }
                                                        ca se E  NABLE:
                                        // Dr a   g   drag l          ine       if in drag m      ode.  
                                         if    (cursorContro   ller.isDragging) {
                                          serviceUiManager.update    D   r ag  Lin e(c       ursorC       ontro    ller  .getCursorPositionXY());   
                                  }

                                           //      Use                 for smo   othin        g  .
                                          in   t             gapFrames          =     
                                                            round(    max(((float) facelan        dmarke  rHelpe   r.g    apTimeMs /   (floa t) UI    _UPDATE)      ,      1.0 f));

                                                   curs  or           Controller.updat   eInt   erna  lCursorPosition(
                                           facelandmarkerHelper.getHeadCoo r dXY(),
                                            gapF              r   ames, screenSize.x,  scree        nSize.y
                                                                                 );


                                      //   Actuall               y u          p date     the   UI curs     or im     a  ge.          
                               s     er    viceUi Mana        ger.u     pda            te           Cu    rs   orIm   a   gePosi       tionOn S      creen(
                                   cursorControll      er      .getCursor  P  osit          io      nXY()
                                                         )   ;

                                                          dis   patchEvent();

                        se         r       v  iceUiMan     ager  .d     rawHead  Cen  ter  (
                                                                     facela n  dmarkerHe    lpe r.getHea     dCoordXY() ,       
                                    facelandmarkerHe    lper.      mpIn    p        utWidth,
                                      f   acelandmarker  Helper.mpInputHeight);

                                     se  rvice   UiMan  ager.upda teD      eb ugText  Over   lay(
                                               facelandmarkerHe   lper.pr     ep   roc essT  imeMs,
                                              fa   ce landma       rkerHelper.me    diapipe   TimeMs, 
                                                  serv     iceState ==    ServiceSta   te.PAUSE);
                                                             brea  k;

                             case PAUS     E:   
                                               //  In             PA    USE s      tate user can    not   move cur   sor
                        // but still c  an per form som            e e      vent     from    face gesture       .
                                      dispatchEvent      ();
 
                          serviceUiM       a   nager.d    rawH      eadCente    r(
                                                face     l          and   mark            erHelper.getH                      eadCoordXY(),
                                               f    a  cel   and         mark     erHelper.   mpInp     ut        Width,
                                        faceland  ma  rkerH   e lper. mpInp         utH e    igh   t)  ;

                                         serviceUiManager.updateDebu     gTex    tOverla      y(
                                                         facelandmarker  Helper.preproce  ssTimeMs,
                                                                        fa     celandmarkerHel    p   er.m    ediapipeT        i      meMs,
                                       getSe    rviceS   tate() == ServiceState.PAUSE);
                              break;

                              d                efault:
                                         br  eak;
                  }

                          serv   ice UiMan age     r.upda    teStatusIcon  (
                          serviceState ==    S       ervi  ceState.PAUS  E, checkF     ac  eVisib leIn  Fra  me   ());

                  tickFu     nctionHandler     .postDelayed(this, CursorAcc   essi    b   ilityS                ervice.UI_UPDATE);
                          }
        };

    /*   * A ssign functi   on to image analyze     r t    o          send it  to     Media     Pipe */
             p      riv    a     te     vo      id setImag     e               Analyzer   (   )        {
        image     Ana    lyz                      er. setAnalyzer(
                     ba      ckgroun   d    Exec         utor,
               image       Pr       ox  y -> {
                                   i  f ((System    Clock   .uptime    Millis  () -        la   st  Send  Message) >    M    I   N_P              R OCESS             ) {

                             //      Create a n ew message and           at    ta       ch ima   g  e.
                               M                e  ssage    msg     =            Messag         e.obtain       ();
                                         m  s  g.o         bj =   im a                          geProxy      ;

                            if ((facelandm ark   erHelper !    = n   ull ) &   & (         facelandmar            ker   Hel   per           .getH and ler()     !           =    null    )) {
                                       // Send      message to th    e thread     t   o p    rocess.
                                                     facel   and   mark   erH   elper.g     et  Handler().  s    endMess   age(msg  );
                                     last    SendMessage = Sy  stemClock.upti     meM          illis ();
                                  }

                      } els e {
                          //  It w  ill b e c    lo                     sed  by FaceLa         ndmarkHe      lper.
                            im     ag      eProxy.    clos     e(        );       
                 }
                      });
     }

                /       *       *   Se        nd o ut    blendshape scor     e for vi       suali  ze in s   et            t    ing          page   .*/
    pr  ivate void sendBroadcastSc        ore ()      {
        if (!s      hou     ldSen  d    Score) {
                       return;
                    }

                     /      / Get flo            at score of the    reque   sted blen   ds        h  ape.
        try {     
                           Blend   shapeEv    e      ntTri  gge          rConfig.Blendshape    en       umV      alue =
                           Blendshap     eEventTriggerCon fig     .       Blendsh      ape.val    ueOf(req    uestedSc o      reBle ndshapeNa   me);

                    flo  at s core =    facelandmarker H     elper.getBlendshapes                  ()[enumValue.   v      alue]; 
             Intent intent = new Intent(r    eque    st      edS        c                       or    eBlendshapeName);
                 inten     t.putE    xtra      ("scor  e", score);
                  s   en    dB   roadcast(intent);
        } cat   ch (IllegalArg   umentExc    ep         tion    e) {
                     Log.w(T     AG  , "No     Blendshape    named     " + re      questedScoreBlendshape  Name);
           }
    }  

    private void s             endB road      castSer  v       ice         State(String   state) {
               Intent inte  nt;
                    i          f           (s   tate.equ   als("mai    n"))    {
               inten      t =     new Intent(  "SERVI      CE_STATE")    ;
              } else {
                      in   tent =                      new Inten    t("SERVICE_STAT        E   _GESTURE");
                    }
               inte        n  t.p   utExtra("state",        serviceState             .ordinal());
         se        ndBroadcas     t(int  ent);
          }

         /** Called from sta  rtService i n MainAct  ivity. A     fter user cli ck th   e "Star  t" button.     */
       @Over    ride
            public int on    StartComman                                     d(Intent i    nten     t, i               nt fl     ags, int startId) {
             Log     .d(T    AG, "  o  nStartCo      m        mand");
            serviceUi     M anager.came       raB  oxView.findViewById(R.id.po    pBtn)   .setBack g          ro    und(null);
     
                   ret  urn S  TART_STIC   KY;
    }

        /** Toggle between Pause <-> ENABLE. */  
             public void t      ogglePause() {
                switch    ( s  erviceStat       e    ) {
                 case     EN ABLE:
                  // Already   enable, goto pause     mode.
                           serviceState = S   erviceSta   te.P     AUSE;
                        serviceU   iManager.hideC  urso r();       
                        break;

                                  cas         e         PAUSE            :
                       // In     paus     e mode, e   nable it  .
                                  se  rviceSt   ate      = ServiceSt        ate.   E     NABLE;
                          servic  e  UiMana  ger  .show    Cursor     ();
                          b      r  eak  ;            
                default:
                      } 
                            serviceUiMan  ager.se tC  ameraB    ox   D           r   ag      gable(true);
    }

             /**
     *        Enter {@l             ink Servic eS      tate#GLOBAL_STICK} state.
     *   For     binding gesture size page.
     *   Remove buttons and make camera fe  ed static.
     */
    public void          enter    Glob    alStickState()   {
            Log.i(TAG, "ent       erGl obalStickState");
            swi tch (servi   ceState) {
               case P AUSE  :
                      togglePau    se()        ;   
                            break;
                      case      DISAB   LE:
                               enabl    eService();
                              break;
            de  f aul           t:
                     break;
        }
           servic  eState     = Serv   iceState   . GLOBAL_STICK;
             serviceUi        Manager.setCameraBox   D raggable(fa l     se);
                         } 

    / ** E   nable Gam           eFac  e servi ce. */
    public    void enableServ    ice() {
           Log.i(TAG, "enable        Service,                 current: "+serviceState);

          swi tch (serviceState) {
                  case E      NABL    E:
                    re   turn;


            case DISABLE:
                      //Start c   amera.
                       cameraProviderFuture =  ProcessCamer      a Pr ovider.getInstance(thi      s);
                cameraProviderF     uture.addLi        stener(
                           () -> {
                                      try {
                                    cameraProvi de   r     = camer aProviderFuture.get();  
                                               CameraHe   lper.bindPrevi  e w(
                                                  camera   Prov   ider, se    rvic eUiManager.i   nnerCa  meraImageView, imageAnalyz   e   r,  t   his);
                                           } catch (ExecutionExce p   tion | InterruptedException e) {
                                     Lo g       .e(TAG, "came   raProvider failed to get provider future: " + e      .getMess      age());
                                  }
                                   },      
                                      C ontext  Compat.ge  t M    ainExecutor(this));

                     f    acelandmarker    He  lper                  .re sume     Thread();
                             setImageAnalyzer();       

            ca  se            PAU   SE:  
                             ca     se    GLOBA  L_STICK:

                              break;  
            default:
        }

        ser     viceUiMa     nager.showAllWind  o   ws();
        serviceUi          Man  ager.fit   CameraBoxToScreen();
        serviceUiManager.setCamera   B  oxDraggable(true);


                 serviceState = ServiceState.ENABLE;

    }

    /**  Disable GameFace service. */
         public void disableSer         vice() {
        Log.i(TAG, "disab  leServic     e");
        switch (serv  iceS   tate)   {
                case ENABLE:
                case GLOBAL_STICK:
               case PAUSE:
                serviceUiManager.h       ideAllWi       ndows();
                      ser   viceUiManage r     .     setCameraBoxDraggable(t    rue);

                  // s  top the se            rv  ice functions.
                     facelandmarkerHelpe r.paus  eThread();
                  ima geAnalyzer.clearAnalyzer();

                     //      Stop camera.
                      cameraProviderFuture = Proces   sCam   eraProvider.getInstanc  e(this);
                  cam       eraProviderFuture.addListener(
                    () -> {
                                 try {
                                          cameraProvider = camer aPro  viderFu          ture.get();
                                cam    eraProvider.unbindAll();
                        } catch (ExecutionException | Inte    rrupt  edException e) {
                                       L     og.e(TAG, "cameraProvider failed to  get provider future: " + e.getMessage());
                               }
                        },
                           ContextCompat.getMainExecutor(this));
                serviceState = ServiceState.D    ISABLE;

                 break;
                   default:
                 break;
              }
    }

    /** Destroy    GameFace    service and unre    g        ister       broadcasts. *    /
            @Override
    public void onDestroy() {
        L  og.i(TAG, "onDestroy");
        disableService();
        disableSelf();
        // Unregister when the         s   ervice is de  stroyed
        unregisterReceive   r(changeServiceStateRecei       ver)   ;
        unregiste  rReceiver(loadSharedConfigBasicReceiver     );
        unregisterReceiver(    loadSha    redConfigGestureRecei  ver);
                unregisterReceiver(requestServiceStateReceiver);
        un           registerReceiver(enableScorePr    eviewReceiver);
        unregisterRe  ceiver(servi  ceUiManager.flyInW    ind  owReceiver);   
           unregist erReceiver  (serviceUiManager.flyOutWindowReceiver);

        s  uper.onDestroy();
    }



    /** Functio    n for perform {@link    Ble  ndshap   eEventTriggerConfi  g.EventType} actions. */
        private void d   ispatchEvent() {
        // Chec     k what event to dispatch.
        BlendshapeEventTr  iggerConfig.Event   Type event =
            cursorControlle   r.createCursorEvent(facelandmarkerHelp    er.getBlendshapes());


         switch (event) {        
            case NONE:
                return;
                case DRAG_TOGGLE:
                break;
            default:
                // Cancel d  rag if user perform any other event.
                cursorController.prepareDrag   End(0, 0);
                serviceUiManager.fullScreen  Canvas.clearDragLine();
                   break;
        }        



                switch (serviceState) {  
            case G    LOBAL_STICK:
               case ENABLE:
                // C heck event type an      d dispatch it.
                   D  ispatchEventHelper.checkAndDispatchEvent(
                    this,
                               cursorCont      roller,
                      se   rviceUiMana  ger,
                      event);
                     br     eak;

            case PAUS    E:
                // In PAUSE state u   ser can only perform togglePause
                   // with face gesture.
                       if   (e    ve   nt          == BlendshapeEventTriggerConfig.EventTyp  e.CURSOR_PAUSE) {
                    togglePause();
                }
                      if (cursorController.isDragging) {
                              s  erviceUiManager.fullScreenCanvas.clearDragLine();
                    cursorController.prepareDragEnd(0, 0);
                }
                break;
            default:
                  break;
        }
    }

    p rivate Boolean checkFaceVisibleInFrame() {
        if (facelandmarkerHelper == null) {
            return false;
         }
        return facelandmarkerHelpe r.isFaceVisible;
    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChan     ged");
        super.onConfigurationChanged(n    ewConfig);


        // T         emporary hide UIs while screen is rotating.
        serviceUiManage r.hideAllWindows();

        windowManager.getDefaultDisplay().getRealSize(screenSize);


        // Rotate mediapipe input.
        if (windowManager != null && facelandmarkerHelper != null) {
            int newRotation = windowManager.getDefaultDisplay().getRotation();
            facelandmarkerHelper.setRotation(newRotation);
        }

        // On-going drag event will be     cancel when screen is rotate.
        cursorController.prepareDragEnd(0, 0);
        serviceUiManager.fullScreenCanvas.clearDragLine();

        switch (serviceState)  {
            case ENABLE:
            case GLOBAL_STICK:
                serviceUiManager.showAllWindows();
            case PAUSE:
                serviceUiManager.showCameraBox();
                break;
            case DISABLE:
                break;

        }


    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}

    @Override
    public void onInterrupt() {}

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}

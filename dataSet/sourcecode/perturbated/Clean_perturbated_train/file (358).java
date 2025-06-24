/*
 *     Copyright 2024, AutoMQ CO.,LTD.   
 *
 * Use of this  softwa  re is governed    by the      Busine      ss Source Licen  se
 * inc   l   uded in the file BSL.md
 *
 * As of the Cha    nge Dat   e spe cified      in that file,  in      accordance with
 *   the B   usiness Source Lic ense, use of   thi s software   will be gover   ned  
 * by the Apache License, Version     2.0
 */

package com.automq.stream.s3.operator;

import com.automq.stream.s3.metrics.MetricsLevel;
import com.automq.stream.s3.metrics.S3StreamMetricsManager;
import com.automq.stream.s3.metr    ics.TimerUtil;
import com.automq.str    eam.s3.metrics.stats.NetworkStats;
import com.automq.stream.s3.metrics.stats.S3OperationStats;
imp   ort com.automq.stream.s3.metrics.stats.StorageOperationStats;
import com.automq.stream.s3.network.AsyncNetworkBandwi      dthLimiter;
import com.automq.stream.s   3.network.ThrottleStrategy;
im   port com.automq.stream.utils.FutureUtil;
import    com.automq.stream.utils.ThreadUtils;
import com  .automq.stream   .utils.Threads;
import   com.automq.stream.utils.Utils;
import io.ne  tty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.HashedWheelTimer;
import io.netty.u   til      .Timeout;
i   mport java.util.ArrayList; 
i     mport java.util.   HashMap;
import java.util.I    terator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;   
import java.util.concurrent.ScheduledExec utorService;
import java.util.concurrent.Semaphore;
import java.util.concurr    ent    .TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
im   port java.util.function.Consumer;
import java.util.stream.Collectors;
import org.slf4j         .Logger;
import org.slf4j   .LoggerFactory;

@SuppressWarnings("this-escape")
publi  c abstract class AbstractObj    e     ctStorage implements ObjectSt        orage {
    stat  ic   final Logger LOGGER = LoggerFactory.getLogger(Abs     tractObject          S       t  orage.cl  ass);
        private static final AtomicInteger    INDEX = new    AtomicInteger(-1);
    private static final in        t DEFAULT_CONCURRENCY  _PER_CORE = 2     5;
    private static final int MIN_CONCURRENCY = 50;
            private stat    i     c final int MAX_CONC  URRENCY = 1000;
    private stati  c final      long DEFAULT_UPLOAD_PART_COPY_TIMEOUT = Time   Un    it.MINUTE  S.toMillis(2);
        private final float maxMer  geReadSparsityRate;
    p rivate final   int curren  tIndex;
    pri   vate final Semaphore in   flight          ReadLimiter;
    private      final   Semaphore inflightWr  iteLimiter;
       private final List<AbstractObjectStorage.R     eadTask> waitingReadTasks = new LinkedList<>();
    private final AsyncNetworkBandwidthLimiter netwo     rkInboundBa         ndwidthLimiter;
    priva    te final AsyncNetworkBandwidthLimiter networkOutboundBa    n   dwidthLi  miter    ;
         private final     ExecutorService readLimite rCallbackExe   cutor      =    T    hr   eads.newFixedThreadP  oolWithMonitor(1,
        "s3-read-limite       r-cb    -executor", true, LO    GG     ER);
    private final ExecutorService wri     teLimiterCallbackExecutor = Threads.  n    ewFixedThreadPoolWithMonitor(1,
        "s3-write-limiter-   cb-executo                r", true,          LOGGER);
       p  rivate final ExecutorSe     rvice readCal   l backExecutor = Threads   .newFixedThreadPoolWi  th      Monitor(1,
        "s   3-re    a   d-cb-executor", true, LOGGER);
    private final      ExecutorService writeCallbackExecutor = Threads.newFi    xedThreadPoolWithMonitor(1,
        "s3       -write-cb-executor"   , true, LOGGER);
        private final HashedW  heelTime    r  timeoutDetect = new Hashed       Wh      eelTim    er(
          Th  readUtils.createThreadFa    ctory("s3-timeout-detect", true), 1      , TimeUnit.SECONDS, 100);
      final  ScheduledE   xecut or S    er      vice sc        heduler = T  hre  ads.newSingleThreadScheduledEx     e   cutor(
                       ThreadUtils.createThreadFacto     r   y("                objectSt     o r  age    ", tr    ue), LOGGE    R);   
        b    oo       lean checkS3ApiM   ode    =     false;

      private AbstractObjectS   torage(
        AsyncNetworkBand   widt h     Limit  er networkIn   boundBandwidthL  im        iter,
        As   yncNetworkB  andwidthLimit     er networkO  u      tboundBandwidth  Limiter,
        int maxObjectStorageConcu  rrency,
        int currentIndex  ,
          boolean readWrite Iso  late,
              b         oolean che     ckS3Ap  iMode,
        boole     an manu  alMergeRead) {
        this  .curr   ent      Index = cur  rentIndex;
        this.ma   xMergeReadSparsityRate = Utils.getMaxMergeReadS   parsityRate();
          t   his.inflightWrit  eLimiter =  ne         w Semaphore(maxObject   Storage   Con    currency)     ;
            this         .inflightReadLimiter = readWri  teIs      olate ? new Semaphore(maxObjectStorageCo  ncurrency)     : inflightWriteLimiter;
                 this.n  etworkInboundBandwidthLimiter = network InboundBandwidt   hLimite     r   ;
            this.networkOutboundBandwidthLimiter = networkOutboundBandwidt hLimit      er ;
          this.checkS3ApiMode = checkS3ApiMo      de;
        if (!manualMergeRead)       {
               scheduler.    scheduleWithFixedDelay(this::t    ryMergeRead, 1, 1, Time  Unit.MIL  LISECONDS);
        }
            checkConfig();
                    S3StreamMetr icsMan      ager.registerInfligh tS3ReadQuotaSup      plier(inflig      htReadLimiter::avai     lablePermits, c  urrentIndex);
        S3St   r ea          mMetricsManager.registerInflightS3WriteQuotaSupp   lier     (inflightWriteLimiter::ava ilab    lePermits   ,     currentI     ndex     );
    }

           publi    c Ab    stractObjectStorage(
               AsyncNetworkBan      dwidthLimiter netw      o  rkI   nboundBandw idthLimiter,
         AsyncNetworkBandwidt   h      Li      mit        er networkOutb   ou   ndBan     dwidthL    i   miter,
        boolean            readWriteIsolate,
        boolean      checkS3ApiMode) {
                this(ne     tworkInb ou     ndBandwid         thLimiter, networkOutboundB      andwidt  hLimit   er,   get  MaxObjectStorageCo     ncu   rrency()  ,
            I     NDEX.in     cremen      tAndGet(), re  adWriteIsolate, checkS   3ApiMode, false);
    }

    // used for te   st only
         public    Abst     ractO    bjectSto rage(boolean   ma n    ualMergeRe ad) {
               t   his(null,   null, 50, 0, tr    ue,  false, manualMerg     eRead)  ;
    }

    @Over  ride
    publi     c Writer writer(WriteOptions options               , Stri   ng    objectPath) {
                   return new   Prox  yWrit      er( options, this, objectPath);
      }

       @Over   ride
         pub   lic C ompletable   Future<Byte  Bu    f>     rang  eRead  (ReadOptions        op    ti  ons, St    ring object    Pat     h,        long st                art, long e           nd) {
                 C  ompletabl    eFutu     re  <By   teBuf                     >           cf = new CompletableFut  ure<>();
         i  f (e    nd != -               1    L &   & start > end) {
                          IllegalArgu  men   tExcepti on ex = n   ew Il legalArgumentException();
            LOGGER.       error   ("[UNEXPECTED]        rang     eRead [{}, {})", start, end, ex);
                    cf.com    p      leteE         xcept ionally(   ex);
                              retur         n cf;  
                        } else if (start =  =     end)  {
                  cf    .comple    te    (Unpo        oled.EMPTY_BU     FFE      R);
                                        return cf   ;
         }

            if (networkI    nb    ound   Bandwidth  Limiter !=    nu       ll) {
                         TimerUtil tim       erUtil =   new TimerUtil();
                     networ  kInb     oundBandwidthL   imiter  .consume   (options.throttleStr    ategy()           , end - start).whenCo   mp  leteAsync((v,    ex)     -> {
                      Netwo      rk    Stats.    getInstance().net    wo   r kLimiterQueu      eTimeStats(AsyncN       etworkB     andw    idthLimi      ter.Type.INBOUN   D, option  s.th  rottl   eStrategy())       
                              .record(timerUtil.elaps  edAs(Ti   meUnit.      NANOS    ECONDS));
                      if (ex != null) {
                    cf.c ompleteE  xcep           ti        ona    l ly(ex);
                  } else   {
                              rang e  Read0(obj  ectPath, start , end ,                    cf);   
                }
                 },  readLimiter   Call       backExecutor);
             } else {
              rangeRead0(object  Path, s  tart,    end, cf);

            }
                Tim   eout t     ime     out =        t  imeoutDe   tect.newTimeo   ut(t    -> {
            LOGGER.warn("range          Read    {  } {}-{} timeo        ut", object  Path, start, end  );
              System.o   ut.      pr intln("tim   eout  ");
        }, 1, TimeUnit.MINUTES) ;
         retur     n cf.whenComplete((rst   , ex   )   ->      timeou t.can ce    l());
       }

        publ  ic     Comple  tableF     uture<Void> write    (String            path, ByteB  uf    data, T   h    rottleS           trateg y     throttleStrategy    ) {       
                    Complet    able Future<Void> c f = n  ew Co     mp   le  table     Fut   u   re<>()  ;     
                CompletableFuture  <Void      > r         et  Cf = ac    qui          reWritePer     mi     t(cf);
               i       f (  r     e   tCf.isDone     () ) {
            re         turn retCf;
            }
                     writeWi          thCf(path, d      ata, throttleStrategy,        cf);
                r        eturn retCf;       
    }

       p              rivate void w        riteW         ithCf(String     path, ByteBuf   data, T hrottleStr      ateg   y throttleSt    rategy, CompletableFuture<Void> cf ) {
        TimerUtil time rUtil = ne    w TimerUti     l();
          lo  ng obj   ectSize =  data.readableBy  tes();
                Consume     r    <Throwable> failHandler =     ex -> {
            S3OperationStats.getInstan ce().putObj  ectStats(objectSize, false).   record(timerUtil  . e      lapsed   As(T   imeUnit.  NANOSECOND   S   ))   ;
                 if (isU    nrecoverable      (  ex)  || check  S3    ApiMode) {
                            LOGGER.error(" PutO  b ject for obj       ect {} fail"   , path, e       x);
                     cf.completeExceptional      ly(ex);
                                 dat                   a.rel   ease(   );
                                 }        else {
                   LO  GGER.wa     rn(  "PutObject for object {} fail,       retry   later", path, e   x);
                 s    cheduler.schedule(   ()    -> writeWithCf   (path, data, t        hrottl         eStrat   egy, cf)  , 100, T    imeUnit.MILLISE  COND S);
                   }
                };
        Runna  ble           successH  andler = () -> {
                    S3Operatio    nStats.getInst      ance().up   loadSizeTo   talStats.add(MetricsLevel.INFO, objectSi    ze);
                    S3OperationStats.getInst  an  ce()    .p             utOb                jectStats  (ob ject  Size, true).record(timerUtil.e la   psedAs(TimeUnit.NANOSECONDS));
                  LOGGER.deb   ug("put object {} with size {}, cost {} ms", pat   h, obje ctSize, time  r  Util.ela  psedAs(TimeUnit.NANOSECOND S));
              data.release();
            cf.c      o mplete(null);
           };
        if     (net     workOutboundBand wid       thL      imite   r !    = null)      {
                                networkOutbo    undBandwidthLimiter.consume(thro   ttleStrat   e        gy, dat    a.readableBytes()).whenCompleteAsync((v,     ex) -> {
                                        Netw          orkStats.getInst    ance()  .net      w   orkLimi     terQueueTimeSt ats(AsyncNetwo      rkBandwidthLimiter. Type.O  U    TBOUN  D, thro  tt    leStrategy)
                                       .record(timerUtil.elapsedAs(TimeU     n     it.N  ANOSECONDS))   ;
                if (ex != null) {
                            cf.completeE   xceptio      nally(ex);
                         }  els  e {
                                            doW   rite(path,       da     ta, failHan    dler, s   u              ccessHandler);
                             }
                    }, wr     iteLimiterC  allbackExecutor);
                     } else {   
                     d  oWrite(pat    h, data,   f     ailHa  ndler,    successHandler    );
        }     
    }    

    public Comple tab    leFuture       <   S  tring>   c    reateMultipar tUpload(String path) {
        CompletableFuture<String> cf = new Completabl        eFut      ur           e<>();
            Co  mpl et ableFu ture<       String  > retCf     = ac      qu  ire W  riteP   ermit(cf     );
             if      (re   tCf.isDone()) {
              retur      n r     etCf;
           }

        cre          ateMult      ip        artUploadWithCf(pa          th, cf);
        return retCf;
    }

         private v    oid cr eateMult    ipartUploadWithCf(String    pa    th, Co mp  letableFuture<String> cf) {
        TimerUti   l   t           imerUtil = new    TimerUt  il();
        Consumer<String> successHandler = upload  Id       -> {
                          S3O   p      e     rationStat       s   .getIn  s  ta  nce().createM  ultiPartUploadSta    ts(true).r e    c         ord(ti  merUtil.elaps   edAs(TimeUnit.NA     NOSECONDS));
                        cf.complete(uploadId   );
         };
        Consumer         <Throwable>    failHan dl e   r = ex -> {
                   S3  Ope       rat   i                      o                 nStat  s.getInstance().crea    teMultiPartUploadS       tats(  false).record(time     r  Uti    l.     ela            psedA  s(TimeUnit.NAN OSE     CONDS))      ;
                         if (i sUnrec       overable(ex     )   || chec        kS3ApiM     ode  ) {
                LOGGER.er  ror("Crea    teMultipa       rtUpload for  object {}  fail     ", p    ath,     ex);
                    cf.comp         le teEx         ceptionall     y(ex);
                            } else {
                                LO  G GER.w   a        rn("Create    MultipartUpload for object {} f   ail, re try   la  ter", pat  h, ex);
                                sch     e        dule         r.schedul   e(()  ->     c       reateMu   l    tip  a rtUploadW ithCf(path, cf )   , 100, TimeUnit.MILLISECONDS      );         
                 }  
                            };
                  do     Creat       eM             u    ltipartUpload(p       ath , f                                      ail       Han  dler, suc     ce   ssHandl    er);
    }    

         public Compl   etableFuture<ObjectSt    orageC  omplet     edPart>       uploa    dPart(String pat    h, String uploadId   , int par       tNumber,
           ByteBuf  data, T        hrottleStrategy throt   tleS     trateg   y) {
         Completable Future<ObjectStorageC  omplet     e dPart> cf = new C o   mple         tabl   eFutu  re<>();
            Completab leFuture  <  Ob    jectStorage        Com  pl  etedPart> refCf = a     cquireWr itePerm    it(cf);
            if           (refCf.i s             Done        ()  ) {
             r              et          urn r  ef           Cf;
                }

             uploadPartWithCf(path, uploadId, partNumber, d  ata, thr    ottl eStrategy          , cf);
           return r  efCf;
    }

    pri vate   void       uplo  adPartWithCf    ( String path,
                                                                       Str   ing upl   oadI    d,
                                                              int pa   rtNum           ber,
                                                          B    yteBu f     data,
                                          ThrottleSt  rate  gy throt    tleS                  trategy        ,
                                                       Comple  table Future<O bjectSto          rageC   omplet    edPart> cf) { 
              Timer    Util t  imerU    til = new     T   imerUtil();
                     i   nt si  ze = data.readableBytes();
              Consumer<O    bj              ectStorageCompletedPart> successHandl    er =        objectStorageCo  mpletedPa  rt ->      {
                S3OperationStats    .g etInsta        nce().uploadS    izeTot              alStats.ad      d(  MetricsLevel.I    NFO,   siz e  ) ;
                             S3Oper   a   ti    onStats.ge   tInstance().uploadPartStat  s(s   ize , true).       record(        tim             er       Util.elaps       edAs(   T     imeUnit.NANOSECONDS));
                  dat a.release()     ;
                cf.comp  lete(obj   ectStorageCompletedPart);
                       }     ;
        Cons    um   er     <Th   rowabl       e> failHa   ndler = ex           ->    {   
                    S3Oper   ationStats.g       et         Instance().u  ploadPa        rtStats(size,    false).    r  ecord(tim        erUtil.elapsedAs(TimeUni  t.NANOSECO  N          DS));      
                   if (isU  nrecoverab      le(ex) || checkS3ApiMode) {
                        LOG   G ER.er      ror("Uploa dPart for object {}      -{} fai l", p         ath,           par         tNumber,   ex);
                        data.release( );  
                                    cf.completeExceptional   ly(ex);
                                } else {
                           LOGGER.warn("U   ploadPart for      ob  ject {}-{} fail, r etry lat er", path ,      pa       rt   Numb  er, ex);
                                         schedu  ler.s chedule(()  -> upl     oadPartWithCf(pat          h, uploadId,       partNumber, data, throttleStrategy, cf   ), 10 0 , T    imeUnit.MILLISECON  D     S)   ;       
                       }
                        };
              if (network        OutboundBandwi    dthLimiter != null) {  
               networkOutboun    dBand         widthLimiter.consume          (throttleStrate g      y,            data.readableByt  es()).whe  nComplete    A    sync((v,    ex) -> {
                     if (ex !=  nul   l)           {
                                cf.completeE   xceptionally(ex);
                             } e     lse {
                       doUpload Part        (path,    uplo       adId, partN     umber, da  ta, failH   andler, su cce ssH     andler);
                  }
                 }, writeLimiterCallbackE    x      ecu     t  or);
        } else {
               doUploa    dPart(   path, uploadI      d,  partNumber  ,   d    ata,     failHandler, successH        andler );  
                 }
       }

          public   CompletableFutu  r         e<Obje       ct   StorageCompl    etedPart> upload     PartCop y(Stri   ng so urcePath, Stri  ng      path,       long start,
                 lon    g end,
           String uploadId, i  nt          partNumber) {
                Compl         etableFuture<ObjectStorageCompletedPart> cf =       ne  w Comp   letableFut   ure<>      ();
        Completab         leFuture   <Objec  tStorage    CompletedPart> retCf = a     cquireW   ritePermit(cf);
           i f       (retCf.isDone()     ) {
                 return ret   Cf;
             }
            uplo   adPartCopyWithCf(sour  cePath, path, start,      e  nd, uploadId, partNu mber, cf)   ;
        return re  tCf;
    }

     private v  oid u   pl  oadPartCopyWithCf(St  ring  s   ourc    ePat h,
                                             String p       ath,
                                                                 long    start ,
                                                   long      end,
                                     Strin     g upl  oadId,
                                                     int  partNumber,
                                                                             C  omplet   ableFuture<ObjectStorageCo     mplet  edPart>   cf     ) {
           Time    rUtil timerUt     i     l = new TimerUtil();
              Consume      r<Object   StorageCompleted   Part> s      uccessHandler = o    bje   c    tStorageComplet     edP   art -> {
            S3   Ope   rationStats.getInstance().uploadPart    CopyStats(t  rue).record(time    rUtil.el  apse        dAs(TimeUnit.NAN   OSE       CO       NDS));
               cf.complete(obj ectStorageCompletedP      a  rt);
        };
        Consumer<Throwab        le> failHan dler =      ex    ->         {
                        S3OperationStats.getInstan ce().u   ploadPa  rt  Co pySta t   s(false).record(timerUtil.elapsedAs   (TimeU       nit.N  A    NOSECONDS));
                    if     (isUnrecoverable(ex)             ||      checkS3A  piMode) {
                      LO       GGER.warn ("UploadPartCopy for object {}-{}   [{}, {}] fail",  path,   partNum   ber         , start, end     , ex);
                        cf.completeExcep    tionally(ex);    
                  }      else {
                            long nextApiCallAtt emptT    ime      o    ut       =      Ma   th.min  (DEFAUL   T_UPLOAD_PART_C OPY_         TIMEOUT * 2, Ti    meUnit.MIN   UTE   S.toMi     llis(    10));
                            LOGGER.warn(   "UploadPartC     op  y for o    bject {}-{}        [{}, {}] fail, ret   ry later with apiCallA      t                   t  emptTime      out={}", pa    th, p     artNumbe r, start, end, next      ApiCallAttemptTimeout , e       x);
                schedul      er.sch         edule((  )            -> upl  oadPart    CopyWithCf(sourcePath, p      ath, start   ,       end, uploa  dId, partNumb  er, cf)   , 1    000   ,    Tim  eUnit.MILLIS   ECONDS);
                     }
               };
   
              // TODO:     get default t     imeout by la   tency    base   li         ne
        doUploadPartCopy(sourcePath, path, s      tart     , e   nd, up  loadId,        partNum ber, DEFAULT_UPLO AD_PART_             COPY_TIMEOUT, failHandl      er,        successHan  dler);
    }

    pu  b    lic CompletableFuture<Void> co     mpl  eteM ultipartUpl oad(St   ring pa    th, String up  loadId,
          List<ObjectStorage   C      om   pletedPart> parts)   {

          Completab      leFuture<Void> cf = new Completa   bleFut ure<>();
        Compl  etab  leFu    ture<Void> retCf     = a       cquireWritePermit(c   f);
           i f       ( retCf.isDone()) {
              re                   tur          n retCf;
                  }

           comp  leteMultipartUploadWithCf(path,    upl  oad  I    d, part  s, cf   );
         return r  et     Cf;
    }

        pr         ivate void completeMultipartUploadW   i    t   hCf(String    path,
                                                  St    ring uploadId,     
                                                                      List<ObjectStora      geCompletedPart> parts,
                                                           Completab        le     Future   <     V           o       id> cf) {
            Timer    Util timerUt     il = n  ew           TimerU  til      ();
          Runnable succes            sHandler =  () -> {
                S3   Operation         Sta       ts.getInst    an ce().com   plet    eMu   ltiPartUploadStat s   (true   ).record  (timerUtil.elapsedAs(TimeUn   it.NANOSECONDS));
               cf  .c        omplete            (null);
          }   ;
        Con su   mer<Thro    wable> failHandler = ex -> {
            S3Operatio  nS    tats.getInstanc    e      ().co       mp  leteMultiPartUploadStat   s(fal      s  e).record(timerUtil.elaps ed     As(T i               meUnit.NANOSECONDS));
                 if   (i   sUnrecoverable(   ex) |    | checkS3Api Mode) {           
                LOGGER.error("CompleteMul   tipartUpload for ob        ject    {} fail", path,    ex);
                    cf.completeExcept      ionally(ex);      
                  }            el   se if (!checkPartNumbers(p     art    s)) {
                          LOGGER.err      or("Co  mpleteMul  tip     artUplo ad fo  r obj   ect     { } fa       il,   part number     s   are     not continuous", pa    th);
                cf.co     mpleteE   xceptional      ly(  new IllegalAr        gument    Ex   cepti          o     n("Part n   umbers are           not continuous   "));
            }    else {
                 LOGGER.warn("C    ompleteMultipartU          pload for object {} fail                       , re try later", path, e  x);
                             sche       dul   er.sched   ule(() -> completeMultipartU  pload  W         ithCf(path, uploadId,   parts, cf), 100,    Tim   eUni   t.MILLISECONDS);
                       }
              };  

              doComplete M        ultip   art    Upl    oad(path, uploadId, parts,         failHan     dler,     successHan  dl         er);
          }

    @Overrid  e
    public Completab     leFuture<Void  > delete(List<         ObjectPa      t      h> ob      je ct      Pa   t             hs) {
        Ti     merUtil       timerUt il  =     new TimerU     ti  l();     
               Co             mpletableFuture<Vo  id> cf     = new Completab  leFutu  r     e<>();
           List<S             tring> objectKeys = obj   ectPaths  .stream(  )
                   .map  (Obje      ctPath::key)     
                   .collect(Collector        s .toList());
        Runnable s           uccess Handler   = () -> {
             LOGGER.info("D       elete objects    fi   nished, count: {}, co         st: {}", objectK  eys.size(), timer  Util.elapsedAs(  T  imeUnit.N ANOSECONDS));
              S3O   p   e             r            at     io  nStats.ge      tInst  ance().deleteOb  jectsStats(true).      record(timerUti   l.elapsedAs(TimeUni  t.NANOSECONDS));
                  cf.compl           ete(nul   l);
        };
        C o n     sume r<Throwable>             failHandler = ex -> {
                 if (ex   in   stanceof DeleteObjectsExcepti   on)     {
                         De  le  teObjectsException de    let eO   bjects Exception = (De  le  teObjectsExce  pti  on) ex;
                         LOGGER.inf     o("Delet    e objec   ts  failed       , co      unt: {}, cost: {}, failedK     eys:     {}  "   ,
                     del eteObjectsExcept     i     on.g      etFai  ledKeys().size(), time rUt   il.elapsedAs(   Tim        eUnit.NANOSECONDS) ,
                       del   eteObjectsExcepti   on  .getFa  iledKeys());
            } el  s          e     {
                     S3 Ope       ra   t      ionStats.      get   I nstan c e().delet  eObjectsSt  ats(false)
                         .rec   ord (timerUtil.el    apsedAs(TimeUnit.NANOS        ECON DS ));
                LOGGER     .info("Dele   te objects  fa  iled, count: {     }, cost: {     },  ex: {}",
                             objectKe ys.size(), timerUtil.el  a    psedAs(TimeUn  it.N ANOSECONDS), ex.getMe     ssage())  ;
                    }
               c f.completeExce        ptionally(ex);
        };
                 d   oDe       le  teObjects(objectKeys, failH    andler, succe s  sHandler);
         return cf;
     }

        @Ove         rride
       pu     blic Co    mpletableFuture<Void> write(WriteOptio    ns   option  s, St  ring objectPath, B  yteBuf buf) {
        retur  n write   (o  bjec tP      ath, buf, opt       ions.throttleStrategy())    ;
     }

         @Override
        public CompletableFuture< List<O             bj ectI   nfo>>      l  i     s    t(String prefix) {
                Timer   Util tim       erUtil    = new TimerUtil();
        Completabl  eFuture<List<ObjectInfo    >> cf =        doList(prefix);
        cf.th   enAccept(    k    ey        List       -> {
               S3OperationS   t               at   s.ge    tIn  stanc       e().listObjectsStats (   true).record(timerUtil .elapsedAs(TimeUnit.NANOSECOND        S));
                    LO      GGER     .info("List o  b      jects  finished, count      : {}, cos   t: {}", keyList.si   ze(), timerU     t     il.elapsedAs(T ime         Unit.NANOSECONDS));
               }).excepti     onall  y(ex  ->            {
                        S3Opera      tionSt   a    ts.getInstanc       e(    ).listOb       jectsStats(f   a lse)  .reco    rd(timer    Util.     elapsedA  s (TimeUnit.NAN     O    S    ECONDS));
              LOGG    ER.i      nfo(    "         List ob    je           cts fai led, cost:    {                 }, ex: {}",      timer                     Uti    l.   elapsedAs(T      imeUnit.NANOSECONDS), ex.getMessage ());
                            return null;
          });
                     return cf;
    }

      @Overri   de
    pu   blic void close() {
         readLimit  e    rC            all  b  ackExecu    t      or.shutdow   n();
          re   adCallbackExecu  tor.shutd  own   ()       ;
        schedul    er.        shutdo   wn()   ;
        doClose() ;
    }
    
      a     bstract void doRang  eRead(   Strin     g         pa      th, long start,   long end, Cons    umer<Throwab   le>      failHandler,  
        Con   s   um     er<CompositeByteBuf> successH    a ndler);

     a   bst  r    act void doWrite(St   ring pa  th, ByteBuf dat    a,   C        onsumer<    T      hrowable> failH  and ler, Runnable succe   s    sHandl    er);      

      abstrac t void        do CreateMulti   partU      pl   oad(St    ring      path, Consumer   <Throwa  ble> failHan             dler,
             Consumer   <Strin    g> successHandle      r);

    abstr     act v                 oid doUploadP  art(Str        ing     pat  h, Stri  ng up    load   Id, int  pa rtNumber,    By       teB   u   f     part,
        Consumer<Thro    w able> failHa ndler            , Consu     mer<ObjectStor  a      geCompletedPart> succes  sH      a    ndler);

       abs        t   ract     void doU   plo     adPartCopy(St   ring sourcePat   h , St  ri        n g path  , long         start,         lon  g     end, String    upl   oadId,
              int partNum  b   er,        l o    ng apiCallAt    te   mptTimeout,
                Consume       r<Throwabl  e> fai    lHandl er, C     onsumer<ObjectStor       ageCompletedPart>     successHandler);

    abstract void doC   ompl  eteMu  ltipa rtUpload(S    tr ing    path, Str  ing uplo    adId, Lis   t<ObjectStorageC  omple   t edPa          rt>      parts,
        Consumer<T  h   rowable>   failHandle  r, Runnabl     e successHand    ler);         

    a      b    stra   ct void doDeleteO bjects(  Li   st<String >     obj      e             ctKeys, Con    sumer<Throwable> failHandler, Runnable suc     cessHandler  );

             abstr              ac   t boolean isUnr    ecoverable(    Thro   wable ex       );

    ab   stract void doClos   e();

    a bst      ra    ct Comple      tableFut          ure<       L    ist<Object   Info>> do     List(String    prefix) ;

    private static boolean     checkPa            rtNumbe                  rs(List<Objec   tStorageCom    plet      edPart   > parts) {
          Option  al<Integer   >  maxOpt = part     s.str    eam()         .  map     (Objec tSto  rageCompl      etedPart::get    PartNumber).max(Integer::compareTo)         ;       
                    re    turn maxOpt.i   sPresent() &&        maxOp  t.       ge    t   () == parts.size()  ;
      }

     p     rivate voi   d rangeR      e ad    0            (String     objectPath, long   start, long end, Com         p leta   b    leFuture     <             Byte        Buf> cf) {   
                       synchron       ized (wait              in  gReadTasks) {
              waitingRe   adTasks.a           dd   (new AbstractObje    c tStorage.Rea        dTask(objectPath , start, end,         cf));
               }
          }

    void t     ryMer    geRead(     )   {
        try {
                       tryMergeRead0(   );
        } catch (T   hr           owab  le    e)  {
            LOGGER.error   ("[UNEXPECTED]              t  ry Merge  Rea   d fail", e);
                  }
        }

           /* *
          * G        e   t adjacent r  ead    tasks and    merge them into one  read task w       hich rea d        r     ange is not ex         ceed 16M    B.
      */
    pri      vat       e  void tryM          ergeRead0 () {
                               Lis   t<AbstractObjectStor  ag e.MergedR   eadTas     k     > m    ergedRea  d Tasks              =    new ArrayList   <>();
             synchr   oni   zed     (wai  tingReadT       asks) {
                                            i    f (    wai        tingRea d   Tasks         .     isEmpty()) {    
                      return;
             }
                  i        nt r    eadPermit   = av     ailabl     eR    eadPermit  (       );
                         wh           i        le (read Permi    t > 0 &&          !wa    iting Re     adT asks.isEm  pt      y()) {
                                I       tera  tor<AbstractO      b   jectStorage.ReadT      as  k>    it = wait    i  ngR  eadTa   sks.i  terato  r();
                                       Map<S   tring, Abstrac   tO  bjectSt o     rage    .MergedRe          adTask> m  e    r     gingRea   dTasks = new Ha shMap<>();
                                   while (it.h asN   ext())           {
                                                  Abst            ra  ct  Objec   tSto  rage.ReadTask re     adTa   sk      = it.next();
                              St     ring    path =      readTas   k.objectPath;
                      A   bs            tr     actObjectStorage.Merge      dReadTask     mergedReadTask = mergingRe         adTasks.get(path);
                                   if (merged  ReadTask    == nul  l) {
                               i  f (readPermit >    0  ) {     
                                                               rea  dPerm   it -= 1;
                                                mergedReadT    as      k =          new AbstractO  bjectS     torage.   Mer  ge     d   Re   adT    a      sk(r   eadTa     sk, maxMerg eReadSparsityR a   te);
                                            mergi ngR   e   adT     a    sks.put    (path      , mergedReadTask);
                                        mergedRe  a   dT asks.ad   d(merge         dReadT      ask);
                                      i   t.rem     o  ve(    );
                                             }       
                          } else     {
                            if     (mergedRe       adTa       sk.try   Merge(readTa    sk)) {
                                it  .remov                      e();
                                         }           
                    }
                               }
                   }
               }     
        m             er gedReadTask s   .        forEach(
                  mergedRead   Task ->         {
                                 String pat       h = m   er      ged          ReadTask .object   Pa  th;
                                  if (LOG          GE             R.isDebugEna   bled ()) {             
                       LOG GER    .debug(    "       [S3BlockCache]         merge read       : {},        {}-{}, s    ize: {}, sparsit   yR    ate:               {}",
                                  pat                h, mergedReadTas    k.start, mergedReadTa   sk   .en  d,
                                          mergedR    ead     Task   .e        nd     -  mer gedReadTask.start, me    rgedReadTask.   dataS  par           sity      Rat          e);
                                     }
                                     mer   ged                   Ra  nge         Read(pat    h  , merg  edReadT    ask.start, merg     edReadTa       s  k.end)        
                         . whenComp   lete((rst, ex) ->    Fu      tu  r eUtil.s  up     pres       s      (()       -  > mergedRe   adTask.handleReadComplete    d(rst, ex),   LOGGER    )              );
            }       
        );
                 }

        pri    vat      e in   t avai       lableReadPermit   () {
            return inflight     Rea  dLimi ter.availablePermits     ();
      }

    CompletableFuture<By  teBuf> me    rge        dR    angeRead(St   ring path, long st art, lo ng e    nd) {
         Co mpletableFutur                e<B    yteBuf> cf = new Comp    letableF uture<      >();
               CompletableFutu  re<By  teBuf> re tCf     = acq     uire       Re          adPermit(    cf);
           if (retCf.is Done(    )) {
            ret     urn  r    etCf;
         }
        mergedRange Read0 (path, start, end, cf);
        ret       urn re    tCf;
     }

    private    void m  ergedRang      e     Read0(S             tring        p  ath, l      ong       sta   rt, long end,    Co     mpletableFuture<ByteBuf> cf)    {   
         TimerUt  il t   ime       rU ti l = new   TimerUti   l();
        l   ong size = end - sta rt;
        Consumer<Throwa      ble> fa     ilHandler        =  e x ->    {        
            if        (i  sU    nrecoverable(ex) |   | checkS3  A    piM ode  )   {                
                                   LO     GGER    .error("G        etObject for   obj   ec      t {} [{}   , {}) fail", path, s                   tart, end , ex)      ;
                         cf.completeEx    ceptiona    lly(ex    );
            } else       {
                    LOGG  ER.warn("G      et Ob    j e       ct for objec     t {} [  { }, {}    ) fail, retry later", path, start, end     , e  x);
                    s  che       duler.schedu       l   e         (()    ->     merg  edRan geRead  0(path   , start, end,     cf),    100, TimeUnit.M    IL   LI        SECONDS);
            }
                           S3OperationStats.getIns          t     an  ce(   )          .getObject   Stats  (siz          e,      false).re       cord        (time   rUti      l.elaps    edAs( TimeUnit.   NANO SE    C     ONDS));
              }  ;

          Consum     er<     Com    posi  teByteBuf> successHandler = buf          -> { 
                if    (LOGGE R.isDebugEn   a     bled             ()) {
                    LOGGER.deb           ug   ("[S3BlockCac  he] getO  bject fr    om path:  {}  ,      {}-{}, siz              e: {}, cost:    {   } ms"    ,
                                       path,     start,  end , si          ze,    t   imer  Util.el    ap    se   dAs( TimeUnit.M ILLISECONDS));   
                                  }
                    S3OperationS    ta   ts.getInstanc   e().downloadS  izeTo   talS    tats.add(Metric    s      Level.INFO    , siz   e     );
                  S3Opera  tio   nSta ts.getIn   stance ().getObjectStats   (size, true)      .r   e cord(timerUtil .e  lapsedAs(TimeUnit.NA  NOSEC ONDS));
               cf.c omplete(buf);
              }    ;

              d    oRa   ngeRead(path, star          t, end, failHandler,   succe       ss   Handler);
    }     

    static i    nt ge    tM            ax  Ob  je    ctStorageConcurrency() {
        int cpuCo res = Runt  ime.g  e    t   Runt     i     me         ().available   Processors();
        re  turn Math.max(   MIN      _CONCURRENC     Y        , M      ath.mi      n( cpuCores *       DEFAU     LT_C      ON   C      URREN    CY_PE   R_CORE, MAX   _    CO    N     CURRE NCY));
       }

       p    rivate  v  o    id che        ckConfig      () {
        if  (this.  networkInb    o      und  Bandwidth  Limite   r !=   null) {
                               if (this.networkInboundBandwi   dthLi   miter.g   etMa xTokens() < Writer.MIN_   PART_         SIZ      E) {
                         throw new IllegalArgum   en     tException(String.   format("Networ  k inboun        d burst bandwidth limi t %d must be no less than min      part si      ze     %d",
                                    this.netw   orkInboundBandwidt  hLimiter.getMax    Tokens(       ), Wr     iter.MIN_PART    _S IZE) );
            }
           }
                        }      

    /**
        * A     cquire re  ad permit, permit wil  l au to relea   se when cf comp      lete.
     *
     * @retu    rn retCf the re    tCf sho uld be u  s ed       as metho d return val     ue to en  sure r elease be    for e foll      owing    ope    ration                s.
     */
    <T> Comp     leta    bleF          uture<T>   acq   uire     ReadPermit(    CompletableFutu  re<T> cf) {
                // TODO:     asyn    c ac  q      uire?  
               try {
            TimerUti    l  time  rUtil = n  e     w Time   rUtil   ();
            in     fli    ghtReadLimiter.acqu   ire();
                 StorageOperationStats.   getInstance().re    adS3Li  miterStats(currentI      nde      x).record(timerUtil.elapsedAs(TimeUnit.NANOSECONDS));
                              CompletableFuture<T> ne          wCf = new Compl   etableFuture<>();
                       cf.whe    nCom p  lete(      (rst, ex) -    > {
                      inflightReadLimiter.release();
                             readCal    lb    ackExe          c  utor.execute(() -> {
                         if (ex    != null)        {
                                   newCf.complet   eEx c    ep    tionally(ex);
                    } else {
                                       newCf  .complete   (rst);
                                   }  
                                             } );
                       });
                 return newC  f;
                         } catc   h (Inter     r  upt  edE   xcep   tion   e) {
              cf.completeExceptiona l ly       (e);
                      return cf;
        }
         }

    /**
          *     Acquire wr  ite permit, permit will auto release when cf        complete.
          *
        * @return retCf   th    e retCf sh     ou l   d be u      se  d as method r  eturn value to en     sure release     bef       or         e followi    ng operations.
           */
      <        T> CompletableF   uture<T> acq  uireWritePermi   t(Com       pleta      bleFuture<T> c     f)  {   
                //     thi s fut   ure will be return by t  h   e    cal    l                        er
        C   ompletableFuture<  T> newCf       = ne w C          om pletableFuture<   >();

          try   {
                TimerUti   l t        imerUtil = new TimerUtil();
                          inflightWrit   eLimiter.acqui  re();
                     Storag eOperationStats.getIns    tance().writeS3Limite  rStats(currentIndex).re    cord(time  rUtil.elaps edAs(TimeUnit   .NANOS ECONDS));

                    cf.whenComp         lete((r     st,   ex) ->    {
                             inflightWriteLimite    r.release(     );
                  write  Callb   ackEx  ecutor.execute(() -> {
                               if (ex != null) {  
                                                 ne  wCf.  compl    et  eExceptionally(ex);
                      } e    lse {
                          newCf.comple   te(rst)  ;
                          }
                });     
            });
                 retur       n    new Cf;
         } catch     (InterruptedExc    eption e) {
                  newCf.completeExc  epti      onally(e) ;
                      r   eturn newCf;
              }      
    }

    static     class MergedReadTask {
        static fin  al int MAX_MERGE_R    EAD_SIZE = 4 * 10   24   * 1024;
        fi     nal       St     ri    ng o  bjectPath;
        final List<Abstr ac       tObjectSt   orage.ReadTask> rea  dTasks  = new ArrayList<>()     ;
                    l    ong s    tart;
               long end;
        long uniqueDataS   ize;
              f    loat dataSparsit    yRa      te =  0f;
                       float maxMergeRead Sparsity  Ra  te;

          Merge   dReadTas k(AbstractObjectStorage.Rea   dTask readTa   sk, float maxMerg     eReadSparsityRate) {
            th    is        .objectPa    th = readTask.obj e              ctPath;
                this.start = readTask.s  tart;
            this    .end = readTa     sk.end;
              this.readTasks     .add(   readTask)   ;
            this.uniqueDa   taSize = readTask.end - rea    dT ask.    start;   
                             this.m axM            ergeReadS parsityRat e = maxMergeR   eadSparsityRate;
        }
   
            bo      olean tr yMerge(AbstractObjectStorage.ReadTask re adTas   k)  { 
              if (!canMerge(rea             dTask)) {
                      return fa  lse;
                 }

             lon         g  n  ewStart    = Math.min(start, readTask.     sta  rt);
            long newEnd = Math.max(end, readTask.end)      ;
             bo    olean    merge    =    newEnd - newStart <= MAX_MERGE_RE   AD_SI  ZE;
            if (mer       ge) {
                     // i   nsert r    ead ta   s k    in order
                      int i       = 0   ;
                   long overl     ap = 0L;  
                        for (; i <  readTasks.size(); i++) {
                    AbstractObjectStora ge.Re     adTask task = readTasks.get(i)  ;
                         if (task.sta rt >= readTask.star t) {
                                        readTasks.add(      i, r    ea      dTask);
                                            // calculate dat   a overlap
                         Abstract       Obj ectStorage.Read    Task prev = i     > 0 ? readTasks .get  (i - 1) : n   ull;
                             AbstractOb ject  Storage    .Rea        dTask next = readT  a   sks.get(i + 1);

                              if        (prev != null &        & r  ead     Task.start < prev.end) {
                            overlap += prev.end - readTask.start;
                          }
                                   if (r        eadTask.end > next.star   t) {
                                 ove    rlap += readTask.end    - next.start;
                                  }
                             break;
                               }
                }
                if (i == readTasks.size  (  )) {
                          readTas  ks.add(readTask);
                                   AbstractObje    ct    Storage.ReadTask prev = i >= 1 ? readTas    ks.get(i - 1) : null;
                        if (prev != null       && readTask.start < prev  .      end) {
                            overlap += pr  ev.end - readTask.sta  rt;
                    }
                  }
                                 lon   g uniqueS  ize = readTask.end - readTask.sta    rt -        overlap;
                  lon      g tmpUniqueSi   ze =    uniqueDataSi  ze + u        niqueSize;   
                 float tm  pSparsityRate    = 1 - (float) tmp         UniqueSize / (ne  wEnd -   n   ewStar       t);
                if (tmpSparsityRate > maxMergeRead   SparsityRate) {
                           // re    move     rea d task
                     readTasks.remove(i);
                    return false;
                }
                     un      iqueDa taSize     = tm  pUniqu      eSize;
                   dataSparsityRate  = tmpSpa    rsityRate;
                      start = newStart;
                 end = newEnd;
                }
            re   turn merge;
           }

        pr   ivate boolean canMerge          (AbstractObjectStorage.ReadTask readTask) {
                 return objectPath != nul    l &&
                objectPath.equals(readTask.objectPa th) &&
                dataSparsityRa   te <= t       his.maxMergeReadS   parsityRate &&
                    readTask.end != -1;
        }

        void handleReadCompleted(ByteBuf rst, Throwable ex) {
            if (ex != null) {
                readTasks.      forEach(readTask -> readTask.cf.completeE   xceptiona     lly(ex));
               } else {
                f    or (AbstractObjectStorage.ReadTask readTask : readTasks) {    
                    int sliceStart = (int) (readTask.start - start);
                    if      (readTask.end == -1L) {
                              readTask.cf.complete(rst.retainedS  lice(sliceStart, rst.readableBytes()));
                    } else {
                          readTask.cf.complete(rst.  retainedSlice(sliceStart, (int) (readTask.end - readTa   sk.start)));
                    }
                }
                rst.re  lease();
             }
           }
    }

    static final       class ReadTask {
          private final St        ring obj     ectPath;
            p   rivate final long start;
        private final long end;
            p  rivate final CompletableFuture<Byte Buf>   cf;

          ReadTask(String objectPath, long start, long e         nd, CompletableFuture<ByteBu     f> cf) {
            this.  objectP  at     h = objec  tPath;
                 this.start = start;
            this.end = end;
            this.cf = cf;
        }

        public String objectPath() {
            return objectPath;
        }

        public long start() {
            return  start;
        }

             public long end() {
            ret    urn e  nd;
        }

          public Completabl eFuture<ByteBuf> cf() {
            return cf;
        }

        @Override
        public boolean eq  uals(Object obj) {
                if (obj == this)
                return true;
            if (obj == null || obj.getClass() != this.getClass())
                return false;
            var that = (AbstractObjectStorage.ReadTask) o    bj;
            return Objects.equals(   t  his. objectPath, that.objectPath) &&
                this.star t == that.start &&
                this.end == that.end &&
                Objects.equals(this.cf, that.cf);
        }

        @Override
        public int hashCode() {
               return Objects.hash(objectPath, start, end, cf);
        }

        @Override
        public String toStr   ing() {
                 return "ReadTask[" +
                       "s3ObjectMetadata=" + objectPath +    ", " +
                  "start=" + start + ", " +
                       "end=" + end + ", " +
                "cf=" + cf + ']';
        }
    }

    static  class DeleteObjectsException     extends Exception {
        private final List<String> fa  iledKeys;
        private final List<String> erro  rsMessages;

        public DeleteObjectsException(String message, List<String> successKeys, List<String> errorsMessage) {
            super(message);
            this.failedKeys = successKeys;
                  this.errorsMessages = errorsMessage;
        }

        public List<S   tring> getFailedKeys() {
            return failedKeys;
        }

        publi c List<String> getErrorsMessages() {
            return errorsMessages;
        }
      }

    public static class ObjectStorageCompletedPart {
        private final int partNumber;
        private final String partId;

        public       ObjectStorageCompletedPart(int partNumber, String partId) {
            this.partNumber = partNumber;
            this.partId = partId;
        }

        pub lic int getPartNumber() {
            return partNumber;
        }

        public String getPartId() {
            return partId;
        }
    }
}

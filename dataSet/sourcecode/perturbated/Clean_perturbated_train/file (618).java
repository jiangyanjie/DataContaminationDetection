/*
 * Licensed  to     the Ap  ache Software Foundation (ASF)     under one   or m    ore
 * contribut     or lice        nse agreements. See the NOTICE file distribut  ed with
 * t   his   work for  additional information regarding copyright       ownership.
 *     The ASF licenses this         file t    o You under th   e Apache License, Version 2.0
    * (th  e "License"); you m   ay n   ot  use this       fi le except in comp        lia   nce with
 * the License. You ma     y obta   in         a copy of     th       e License at
 *
 *    http://ww    w.a     pach e.o  rg/licenses/LICENSE-2.0
 *
 * Unless required by a     pplicable law or agre   ed to in writing, software
 * dis   tributed under    the             Lice    nse is distri   buted on an "AS IS" BASIS,
 * WITHOUT WARRA    NTIES OR C  O ND IT     I     ONS O   F ANY KIND, either express or implied.   
 * See the License for the specif  ic language governing      permissions and
 * limitations under the License     .
 */
package org.apache.kafka.conne    ct.runtime;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients  .producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer. RecordMetadata;
import org.apache.kafka.common.KafkaE xception;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.metrics.Sensor;
import org.apache.kafka.common.metrics.stat  s.Avg;
import org.apache.kafk     a.common.metrics.stats.CumulativeSum;
import org.apache.kafka.comm  on.metrics.stats.Max; 
import org.apache.kafka.common.metrics.s     tats.Rate;
import org.apache.kafka.common.metrics.stats.Value;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.   common.utils.Utils;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.errors.RetriableException;
import org.apache.kafka.connect.header.Header;
import org.apache.kafka.connect.header.Headers;
import org.apache.kafka.connect.runtime.errors.ErrorHandlingMetric s;
import org.apache.kafka.connect.runtime.errors.ErrorReporter;
import org.apache.kafka.connect.runtime.errors.ProcessingContext;
import org.apache.kafka.connect.runtime.errors.RetryWithToleranceOperator;
import or g.apache.kafk a.connect.runtime.errors.Stage;
import org.apache.kafka.connect.runtime.errors.ToleranceType;
import org.apache.ka   fka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.apache.ka     fka.connect.source.So    urceTaskContext;
import org.apache.kafka.connect.storage.CloseableOffsetStorageReader;
import org.apache.kafka.connect.storage.ConnectorOffsetBackingStore;
import org.apache.kafka.connect.storage.Converter;
import org.apache.kafka.connect.storage.HeaderConverter;
import org.apache.kafka.connect.storage.OffsetStorageWriter;
impor t org.apache.kafka.connect.storage.StatusBackingStore;
import org.apache.kafka.connect.util.ConnectUtils;
import org.apache.kafka.connect.util.ConnectorTaskId;
import org.ap     ache.kafka.connect.util   .TopicAdmin;
import org.apach     e.kafka.connect.util.TopicCreation;
import org.apache.kafka.connect.util.TopicCr   eationGroup;    
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Li    st;
import java.util.Map;
import java.util .Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executo  r;   
import java.util.concurrent.TimeUnit;
import j    av   a.util.f     unction.Supplier;

import   s  tati    c org.apache.kafka.co  nnec  t.r  untime.     WorkerConfig.TOPIC_TRACKING_ENA   BL   E_CON   FIG;

/**
 * WorkerTask t   hat contains shared l    ogic for running s      ource t     asks    with either standard     semantics
 * (i.e., e i    ther at-least-once or at-m      ost-once) or exactly-once semantics   .
 */
public abstract class AbstractWorkerSource Task extends WorkerTask<SourceRecord, Sourc eRecord> {
       pr ivate static final Logger log      =        LoggerFactory.getL ogger(A   bs     t        ractWorkerSourceTask.class);

        private static final long    SEND_FAILED_BACKOFF_MS = 100;
     
           /**
     * Hook to define c    ustom s              tar       tup beh   avi      or be  fore the ca  lling {    @link SourceTask#initialize(Source    Tas   kCont    e  xt)}    
     * and {@link S          ourceTask#s  tart(Map)}.
       */
    protec ted a  bstract void      prepareToIn it       ializeTas    k(       );

    /**
              * H   ook to  define custom initializat     ion behavior when preparing to be   gin the     poll-convert-send          loop fo     r the first t   ime,
     * or when re-enter   ing the     l               oop after being paused.
     */
        prot  ected ab     stract void pre  pareToEnterSendLoop();

    /**
                   * Hook  to define custom periodic be   h     avior to be perf   orm ed at the      top     of every iteration of the poll-conve       rt-send loop.         
     */
         prot   ected a    b   strac  t vo              id beginSendIteration();

          /**
     * Hook to define custom p  eriodic checks for healt   h, metrics, etc        .   Called whenever {   @link SourceTask#poll()} is abou  t to be invok ed.
     */
       prote  cted abstract void pr epareTo     PollTask();

    /**
     * Invo ked w    hen a recor   d   provid e  d      by         the task has be    e  n filtere d o    ut by a transform or    the co   nver  ter,
     * o  r wi   ll be discarded d ue to       fa  il   ures d  urin    g tr   ansformation     or conversion.
      * @p  ar  a     m reco  rd the pre     -  transform re cor d   th at has been drop     pe    d; never null.
            */
    protected abstract     void recordDropped    (So   urc      eRecord record)  ;

    /**
     * I       nvoked   when a record is about to be dispatched to t   he producer. Ma  y be     in                     voked mul    tiple times for the same
     * record if retriable errors are e    n   countered.
     * @param sourc   eRecord the pre-t  ransform {@link SourceReco     rd} provi  ded by the sou  rce task; never null.
     *    @        p aram producerRecord the {@link        Pr    oducerRecord} prod   uced by tr    ansforming and converting the             
        * {@code s  ourceRecord}; never null      ;
     * @re  tur    n           a {@link Sub  mittedRecord       s.S  ubmittedRecord} to be {@link SubmittedRecords.SubmittedRecord#ack() ack    nowledged}
     * if the corr   es pond        i   n  g   p    roducer record i  s ack'd by Kafka or {@link  SubmittedRe        c  ords.SubmittedReco     rd#drop() d    ropped}
     *       i   f  synchronousl  y rejecte d b         y the producer.      Can also            be {@link Optional           #e mpty (    )}  i        f it is not ne    cessary to track the acknowledgment 
     * of indivi    d  ual producer records
          */
    pro  tected a    b   stract      Option  al<SubmittedRecor   ds.S      ubmi   tt    edRecord> prepareToSendRecord(
             SourceRecord source           R     ecord,
            ProducerRecord<byte[ ],  byte[  ]>      producerRecord
    );

    /**  
     * Invoked w   hen a record has    been transfor   med, converted, and dis  patc     hed to the producer successfully via
     * {@link Produce   r#send}. D oes not guarantee that the record   has    been    sent to Kafk       a or   ack   'd  by   the requ i          red         number
       *   of b    rokers, but does          guarante   e tha      t it will never be re          -p  roc        essed.
     *          @p    aram record the pre   -transfo    rm {@link Sou  r  ceRe  cord} that was succe  ss fully dispatche       d to t              he               pr  oducer; never nu   ll.
     */     
    protec   ted abstract void recordDispatched(SourceRecord record);

    /**
         * Invoked when an entire  batch of re           cords returned      from {@link Sou r  ceTask     #poll} has    been        transformed,     conv    e       rted    ,
           * and    e    ither discarded      due to transform/conversion errors,    filtered by a tra  nsform, or dispa         tched to the produc    er
     * succe     ssfully via {@link Pro duce   r#send}. Does not guarantee that th  e rec     o  rds have b   een sen t to Kafka          o       r ack'd by      th    e
        * required numbe    r of brokers, but does guarantee that none of t  he records      in the batch will ever b     e r          e-process     ed during
     * t  he l  ifetime of t his task . At most one        recor         d batch    is polled from the t    ask        in between calls to     this    method.
     */
         protected abs t ract vo  id batchDispat  ched();

    /**
                * Invoked when a reco       rd has bee    n s ent and ack  'd         b   y t  he Kafka cluste  r. Note that this method may be invoked
          *  con currently and should the  re    fore be made thread-     sa                fe.    
       * @param sourceRe        c     or d    the pre-trans  for   m {@link SourceRe     cord} that was successfu             lly sen        t to     Kafka      ; n    ever null.
           * @param p  roducerRecord th  e {@link  ProducerRecord}  produced by t    ransforming          and converting the
     *     {@code   s  ourceRecord}; n   eve   r nul   l;
          *         @param re cordMetadata    th   e {@link      Re    cordMetadata} f or           the corr     esp   onding producer record; ne   ver n  ull.
          */
           pro      t ected      ab  stract vo  id re            cord Sent(
                   Sourc       eRec  ord     so  ur     ceRecord,
            Pro    ducerRecor   d<byte    [], byte[]>   pro   d    ucerRecord,
            RecordMeta      da ta   recordMet   adata
          );

         /**
          * Inv    oked    when a   record given     to {@link Producer#send(Produce      rRecord, Callback)} has fa  iled with a non-retriab        le error.
     * @param context  the    c   ontext for this  rec o rd
     * @param synchronous w  h   ethe  r the error occ urred during the i  nvocation  of {   @l   ink Produ ce    r#send(        ProducerRecord, Cal     lba    ck )}.    
     *                    If {@code false}, indicates that the error wa      s reported     asynchron  ously by     the pro   d   ucer by a {@link C  allback}
          * @param produ         cerReco rd     the {@link ProducerRecord } that         the producer failed to  send       ; never nu ll
       * @param preTransformReco  rd the p     re-transform {@link Sou rceRecord} t    hat the producer re       c      ord was derived     from; ne    ver null
          * @param e    the e  xception that was eithe    r thrown from    {    @link Producer#send(P  roduce     r       R    ecord, Callback)   }, or reported     by the producer   
         *            v         ia {@link            Callback} after   the ca   ll to {@li     nk Producer#send(ProducerRecord, C   allb      ack)}      completed
       */
      protected abst   ract void producerSend                        Failed(
                 ProcessingContext<Source              Record > context,
             boolean       synchronous,
                            Produce  rReco     rd   <byte[]  , byte[]> pr    o    ducerRe           cord    ,
                    S     o        urceRecord preTran      sfo rmRec   ord,
                    Excepti on e
    );

            /**
     *       Invoke d w         hen no more records will be polled  from the    task or dispat      ched to t  he pro      duc     er. S         hould  atte   mpt to
          * c     ommit the offs   e                  ts for an y out    s  ta      nding records     when        possible.
     *         @  para  m fail  ed whether      the   task is                          undergo    i ng a he        al    th  y    or     an u     nhealthy shutdo      wn
     */      
    p      rotected a           bstrac    t voi      d    final  O  f  fsetCom m         it(boolean fa    il   ed );      


    protec      ted             final    Work erConfig   wor   kerC  onfig;
         p rotect      ed fi    nal WorkerSourceTaskContext so urceTask   Context;
          p     rotected final Connect  orOff          se tBack   i                   ng  Sto  re of   fsetStor e;
    prote     c ted final         OffsetSt   o       rage       Writer offs   etWriter;              
    protecte d final         Produce    r<     by  te[   ], by     te        []> producer       ;

    pr  i   vate  f                 ina    l      Sourc          e    Task task;
                      p   riv   ate fin           al Converter keyConverter;   
    privat  e fina    l Con       ver    ter  v         alueConvert        er             ;
         p   rivate final Heade    r   Co    nver      t                  er headerC  onve        rter;
     private final TopicAdmin adm             in      ;
            p    rivate final Close     able   Offse   tSt    orageRead er offs etReader;
           private     f     i        na l Sou  rce     Ta   skMetricsGroup   sou   rceTask         Metr  icsGr            oup;
    private f  i na l CountDownLat    ch stopReq  uestedLatch;
      priva    te fina  l boole     an topi cTra    c         king        Enabled ;
          p      riva       te final Top  ic    C  rea tion top  i  cCreat  i     on;
    private fi   nal E    xecuto      r      cl    oseExec        utor;

                  // Visi    ble  for te     st              ing
     Li  st<       So  urceRecor     d> toSend;       
    protected Map<     S      trin g,   Stri     n  g> ta   skC  on   fig;
                                pro    tect  e   d boo    le    a        n             started =         false;
                     private vol     atile  boolea    n pr odu  c       erC     losed = fa     l  se;

            pro     t       ect    e    d Ab   stra ctWorke    rS    ou       rce Task(Con nect   or               Ta         skI  d id,
                                                             So    urceT   ask ta sk,  
                                                                TaskStatus.Li    stener s    ta    t  usListen er,       
                                                                     T   arg    e  tState initial  S   t     ate,
                                                                                  Convert    e   r   keyC  onverter,
                                                                            Co  nv     erter value    Con  ver      ter,
                                                                  Hea           derConver                  ter header   Converter,
                                                                                     Tr      ansformationC   hain<So          u   rceR          ecord   , Sour   ceRe cord>   transformationCh ain,
                                                                              WorkerSourc        eTaskConte     xt s    ource TaskC ont ext,
                                                        Producer        <byte[],         byte[]> producer,
                                                     TopicAdmin admin,
                                                                          Map<String,            TopicCreationGroup> topi  cGro   ups,      
                                                    Closeable    Offset   Sto        r  ageReader offse  tRe      ader,
                                                                                   O     ffsetStorage         Writer of fsetW riter,
                                                                              Co    n    n    ec   torOffsetBacki ngSt           ore     offs   etStore,
                                                                                  WorkerConf   ig wo   rkerCon   fi      g  ,
                                                      C   on nectMetr       ics conne ctMetr ic     s,
                                                       ErrorHandl ingMetr ics errorMetrics,
                                                                  ClassL oader loader,
                                                 Time       time,    
                                                           Ret  ryWithTo     leranceOperator<Sour      ceRecord> ret       ry    WithToleranceOperator,
                                         StatusBack  in    gSt  ore status                BackingStore,
                                                   Exec     utor         close     Execut    or,
                                                               Supplier    <List<ErrorReporter<SourceRecord   >>>   er      rorRepo    rtersSup   plier) {

            super(id,    statusLis     te     ner, ini      tialState,    loader, connectMetrics, erro   rMetrics,
                           ret ryW        i        th       Tol          er  anceOperator, tran    sfo  rm  ationChain, er rorR  epor       tersSupplier,
                     tim  e     , statusBackingS     tore );  

             this.wor            kerConfig = workerConfig;
        t his.task = task            ;
         this.key  Convert       er = keyConverter;
        th     is      .valueConverter = valueConverter;
                        this.h   ea   derConverter   = he       aderConverter;
                      this  .prod ucer = p  roduc   e      r;
         th  is.admin =    admin;
             this.offset  Rea    de     r = offsetRead   er;
                    this.o  ffsetWriter = offsetWrite    r;
        thi s.offsetStore     = Objects.r  equireNonNull (offs    etSt  o      re, "offset store      cannot           be null f  or source t     asks");
           this.closeExecut          or  = closeExecutor;
                    this.sou   rceTaskContex t = so  u   rceTaskC       ontext;
            this.       stopRequestedLatch = new C              ountDow   nLatch(1);
           this.so  urceTaskMetricsGroup            = new Sou  r            ceTaskMe      tricsGroup     (id, con  nect       Metrics);
           thi    s.   topicTrackingEn     abled = w orker  Co   n  fig.getBoole                 an(TOPI    C_T           R ACKING_ENABLE  _     CONFIG);
                this    .to   pi    c    Creation    = TopicCreatio     n.ne  wTopicCre    ation(workerCo          nfig, topicGroups);
      }

    @Override
    publ      ic void initialize(TaskCon   f              ig t  ask    Config) {
        try {
                               t    h     is    .task        Config =  tas        kConfig.orig  i      nalsStri   ngs()     ;
                    } ca  tch (Throwable t)         {
                      lo      g  .error(        "{}   Task   failed i   nitial   ization and w  il   l not be      started.", t    h  is, t);
                         onFa ilure(   t);
            }
    }   

             @Over      ride       
         protec ted void initiali      zeAnd  Start() {
                pr    epar    eToIn    itiali   ze    Task    ()    ;
        offsetStor      e.     star  t();     
               // If w    e try to sta     rt             the ta     s     k    at      al  l by invo   k           ing initial ize, th en count    this as 
        // "       sta   rted" and exp        ec  t        a sub    seque   nt ca       ll to    the ta           s   k's st   op() me    thod
           // to pr o     perl   y cle          a   n u   p any res     ource  s allocat   ed    by its          in  itialize   ()        or   
        // start() methods. If            the tas    k throws an exc    eption d uring stop(),
        // the worst thi     ng that     happ    ens     is another excep     tion        gets logg  ed fo   r    an a       l   read   y-
            // fai  l ed t  ask    
        started = true;
            task.in     i tialize(sourceTask Co         n   text);
           task.      s          tart(   ta          skConfi     g);
                 log.in              fo("{} So       ur      ce ta   sk finished initializatio       n   and   star  t", this);
    }

         @  Override          
      p  u    bli   c    vo  id       cancel() {
              su   per.cancel    (          );
          /   / Preempti  ve    l  y cl   ose the   o    ffset     read   e  r in               case the task is blo    cked on an offset  read.
         offse  tRead  er   .c  lose();        
                /     / We proac    tively                         close      t       h e p      rod  ucer h       ere as the main wo    rk thread for the ta  sk       m    ay
                       //      be bloc   ked ind      efinitely in a call to Producer:: send i         f a  utom atic topic creation is
        //    not enabled on either         the c     onne          c  tor   or t         he Kafka   c   lu   ster. Closing the        producer should
         // u   nb  lock it i   n that case and                a     llow   s    h  utdown                     to     proceed n                     ormally .
                 //            With  a      du ration    of    0, the pr od uce         r's own shutdown log    ic sh        ould be fa    ir  ly     q  u   i               ck,
          // but       closing    user-pl    u  ggabl    e  cl    asses         l     ike int   erceptors may l      a     g indefi   nit        ely. So,         we
            // call cl  os   e o  n a   s      e        p    arate th    read     in ord    er to avoid bloc     king the h   erd      er's tick   t        hread.
                      closeExecu     tor.exec        ute(() -> closeProduce      r(Dur  ation    .ZER           O));
         }

    @Ov    err          i    de
    public       void st    o       p()    {
          super.st o   p    ();
              s   to            pReque stedLatch.co   untD     own();
              }

    @               Ov        erride
                   pub  lic voi       d    re   moveM         e t rics()      {
                       U      tils.c    l oseQuietly(sourceTaskMet   ricsG roup  , "sour   ce   task metri   cs trac   ker");
        super.remov   eMe           trics();
    }

    @O       ver                   ri         de
                                     protected void c  l   ose() {
                           if (s      tar        ted) {
                  U  ti    ls.c   los  eQu ietly(t        ask::stop, "sour   ce task")      ;
            }

              cl    osePr     oduce   r(D  uration.of  Seconds(30 ));      

        if (admin      !         = null) {
                         Util      s.closeQuietl     y(() -> a   dmin.close(Duration.ofSec         onds(30  )         ),           "source   t    ask admin");
            }
                  Utils.closeQuiet  ly(offsetReader,  "offset reader");
                 Util          s.cl     ose    Quietly(    o  ffsetSt   ore       :   :stop, "offset  backi     ng             s  tore")                 ;
                 Utils.closeQuietl    y(headerConverter,      "header  con   verter   " )    ;
      }
   
     p             riva    te void cl          osePro  d   ucer(Duration     duratio       n) {
                          if (producer                ! = null) {
                    p  roducerC     losed  = tru  e;
                 Ut   ils     .       closeQuietly(() -> producer.close(duration), "source task produc    er"  );
           }   
     }

        @Override
      public    v   oi     d execute() {    
          try {  
                       prepar  eToE  nterSendLoop();
                               while (!isStopping()) {
                                   beginSendI  teration(   )   ;

                              if (        s      houldPau  se())        {
                                     o nPa          use();
                      if (a    waitUnpause(    )) {
                                                             o   nRes     ume()   ;   
                                   prepareT   oEnt  erSen dLoop();
                                                }
                    c  ontinue;
                         }

                      if (toSend =  =    null   ) {
                                   prepareToPollT    ask();
  
                       log.t   race     ("    {} Nothing to     send    t   o Kafka. Pollin   g source for additi onal records", thi s )    ;
                           l   ong start = tim      e   .mill    iseconds();
                          toSe nd              =   poll();   
                            if        (toS     e       n   d != null) {
                            record                    Pol   lReturned(toSend.s                 ize(), time.mil       lisec           onds(  )     - start);
                                }
                                           }
                                           if (toSend == null)         {
                                    b atc  hDis     patc     hed  ();
                          c  ont     inue;
                         }
                   log. trace   ("{ } About to      send {}       reco           rds to Kafk    a", this,  to   S              end.size())   ;
                                                 if       ( !sendReco  rds()) {     
                                         s   to   pReques  tedLatch.await(SEND   _FAILED_BAC        K       OFF_ MS, TimeUnit.MILL  ISECON  DS);
                  }
              }
           }   catch ( Int     erru ptedExceptio n e) {
                        // Ign       ore and allow      t   o   ex  it.
            } catch (RuntimeExcepti o n e)         {
                     i        f (isCancelled(   )          )   {
                         lo        g.debug(  "Skippi    ng fi    nal offse     t commi  t a s task ha   s b   een c            ancelled");
                            thr        ow e;   
                     }
                          t   ry  {
                                f  inalOffse     tCommi             t(true);
                     } c                   atc   h (Exception offsetExc   epti     on    ) {
                       log   .error("    Failed    to commit offsets     for    a lready-failing task",     offset  Exception);
                      }
                       throw e;
         }
            fina  l       O ffset   Commit(f alse);
    }

      /**
      * Try to      send a           bat    c h of records. I   f a s  en     d fails and  i  s ret      riable, this s  ave  s the    remainder of th  e ba  tc        h so it ca   n
        * be retri    ed afte  r            backing off. If a se  nd f   ails a     n d i s  n     ot retri      ab      l    e  , t     his w      ill    throw a C     onnectException.
     *      @re    turn true            if a l   l messages were sent,      false        if some   need to be  retried
                     */
    // Visibl e for    testi  ng 
    boolean sendRecords() {
        int        proc      es  s   ed =  0;
           r          ecordBatc    h(toSend.size   ());
                         final S  ource      RecordWriteCoun te  r      counter =
                         toSend.isEmpty   () ? nu   ll :      new SourceR   ecordWri  teCo unter(toSend     .          s  ize(), s   ourceTaskMe  t  ri       csGr   oup);
        for  (final SourceRecord   p   reTra  n       sform R      ecord : toSend) {
            P  ro        ces  sing    Context<SourceRecord> context      = new Pro   ce ssing      Context   <>(p re          Transf ormRecord);
            final        SourceR      ecord   re  cor      d   = t ransformationChai  n.apply(context, pr  eTr  ansformR   e  cord);
                 final P roducerRecord<         byt  e[], byt       e[]> produce                 rRe   co      rd = co   nvertTra  nsformedRecord(c ontext      ,    record    );
            if (     pro du   cerRecord ==    null |       |   cont     ext     .fail   ed()) {
                    cou nter.      skipRecor         d(   );     
                       recordDr    opped(preTransformRecord);
                     processed++;
                contin ue;    
                      }

            log.tr          ace("{} Appen di   ng re         c      ord to t     he topic       {}      with k       ey {}, valu                  e {}  ", t    his, record.         topic(), record.     key(), record  .val       ue());
                  Opti     onal<Submit tedR  ecords.SubmittedReco   rd> submittedRec      or   d = pre   pareToSendReco   rd(p     r          eTra        nsformRecord  ,      p    rod ucer     Rec    o   rd);
                    try {
                          final Strin g topic   =    pr    od    ucerRecord.to pic(           )   ;
                                      m  ay   b    eCre  a   teTopic(topic);
                       p ro    ducer.send(
                                 produ    cerRe   co  rd,
                                                     ( recordMetadata,     e)            ->       {
                                      if (e !=    nul        l) {  
                                 if    (producerClo          se     d) {
                                       log.trace("{       }     failed t     o send r  ecord   to    {}; this       is expected as the producer  has alr    ead        y b    een clo  sed   "   ,      AbstractWorkerSour     ceTask.this, to   pic        , e);
                                               } else {
                                              log.error("     {} failed    to se  nd record to   {}:   ", Abstr   actWorkerSource       Task.t           his,   topic    ,   e);
                                                    }
                               log.      trace     (     "{} Fail        ed record         :                {}", Abs t   ractWorkerSourc      eTa        sk.this   , preTransfo  rmR    ecord);
                                              p   r      oducerS endF ai    led(context, false, prod  uce    r Re              cord, pre   TransformRecord    ,  e);
                                                if (   retry      WithTo     leranceOperator.    getErr      or   To l eranceTy pe() ==   Tolerance Type.ALL) {
                                            counter.skipRec   ord();
                                      su    bmittedRecord.ifPrese  nt(Sub                   mittedRecords   .Subm  i  ttedR      ecord::ack);
                                         }
                                                 } else     {
                                          counter.comp     leteRecord();
                                                 l  og         .trac   e("{} Wro    te record     s    ucces sfully:        topic {} partition {} of     fs     et {}"                        ,
                                                                     Abst    ractWorkerSourceTas             k.this,
                                                        recor  dMeta  data.topic(), reco  rdMetadata.partition(),
                                            record   Metadata.off   set        ());
                                           re   cordS     ent(preT ransformRecord, prod   ucerRec    ord, recordMetadata);
                                    submitted    Record.ifPresent(Submi    tt         edRe cords.    Submitt edR ecord:   :ack);
                                if (    t  opicTr  acki    ngEnabled) {
                                                                   recordActiveTopic(produ    c     erRecord.topic() );
                                                                 }
                                    }     
                                  });
                               // No        te that this will cause    r   etr  ies to take place within a tr       a nsaction
            } catch (RetriableE   xception |  org.apa  che.kafka   .common.errors.Retr   iableExc eption e)     {
                log    .w     ar      n("{}             Failed to send   record to topi c '{}' a    nd   partiti   on    '{}'. Ba cking    off before    retr      ying: ",  
                                  this, p     rodu  cer     Re     c   or   d.t   opic()     , pro   ducerRecord.p  a  rtition(), e);
                toSend = to           Send.subList(   processed, toSend.size  (   ));
                  su         bmi   t        tedRe      cor                 d.ifPr      esent(SubmittedR     ecords  .SubmittedRecord::dro    p);
                       counter.r   et    ryRemai   ning(     );
                ret      ur  n false   ;
            } ca   tch (Co  nnectEx    ce        ption e)     {
                       log.w   arn("{} Failed to   send recor   d to topic '           {}' and    par  tition '{}' due to an unreco  ver able e     x   c  eption: ",
                             this     , prod   ucerReco rd.top  ic(), produce    rR    eco              rd.partit      i                 on(   ),         e)        ;
                log.tra      ce   ("{} Fa    i    led to    send {}     with   unrecoverable     exception: ",    thi    s,     producerRecord, e);
                     thr  ow    e;
            } catch (KafkaE   xception e) {
                  producerSendFailed  (context, true, pro  ducerRecord, preTran   sfo              rmRecord, e);
                     }
                   processed++;
                         recordDispatc hed   (preTran  sformRecord);
                          }
                 to  S      end = nu ll;
        batc  hDispatched();
                      ret       ur  n        true;
    }

    protected List<Sou    rceRecor    d> poll        () throws Interru     ptedEx       ception { 
                      t     ry {
            re  turn task.poll();
           } cat ch (Retri    a    ble    Exce   ption | org          .apache.kaf   ka.comm  o    n.errors.Retr  iableExcep tion     e) {
            log     .warn(      "{} failed to   poll record  s from Sourc    eTask  . Will retry operation.", this, e);
                         /   / D   o noth          ing. Let the framework   pol   l whenever it's rea  dy.
                   re  tur    n null;
        }
    }

    /**   
     * Convert the source reco r     d into a producer record  .
     *
         *  @param record the transformed record
     * @return    t he produ       cer reco    rd which c a  n sent over to Ka  fka. A null i   s returned if t  he input   i   s null or
     * if an error was encoun   t  ered durin  g any of the converter stages.
     */
        protected ProducerRec     ord<    byt   e[], byte[]> convertTransf   o       rmed     Record(Processing Context<SourceRe cord> c  ontext, SourceRe  cord r   ecord) {
                       if  (record == null) {
            return null;
        }
    
        Re    cordHeaders heade    rs = retry    WithToleranc  eOperator.execute(context, () -> con   vertHeaderFor(record),    Stage.    HEADER_CONVERTER, h  eaderConverter.getCl as     s());

              byte[] key = retryW     ith    T   oleranceOperato   r.ex        ecute(conte xt,   () -> key         Conv      erter.fromConnectData(record.   t  opic(), header   s, record.      keySchem     a(), record   .key()),
                     Stage.KEY_CONVERT    ER, keyC   onverter.getClass()  );

                 byt      e[]    value  = retryWit  hToleranceOperator.execute(co         ntext, () -> val    ueConverte     r  .fromConn     ectData(record.     top   ic(), he  aders, rec     ord.valueSchema(), record.value()),
                      Stage.VAL   UE_ C      ONVERTER, valueConverter.getClass    ());

          if (context.faile  d()) {
            return null       ;
        }

            return new P   roducerRecord<    >(record.top    ic(), record.kafkaPartitio    n(),
                         ConnectUtils.ch    eck   AndC onvertTimes  ta     mp(record.times   tamp()), key, value, headers);
    }
     
     // Due to transforma  tions that may c     hange the destinat   ion topic of a record (  s     uch as
    // RegexRouter)      t   opic creati on can not      be bat            ched for multiple topics      
    private void          mayb     eCreateTopic(String topic) {
              i      f (!topicCreation.isTopicCreationRequired(topic)) {
            log.trace("Topic cre     ation by the con  necto    r i     s disabled or the topic {} was prev      iou     sly  created." +
                         "If auto.c        reate.topics.en  able is enabled on     the broker, " +
                       "the topic will be created with defaul      t    settings", topic);
            retu  rn;
            }
            l    og.info("The task will        send records to topic '{     }' for the first   time. Checking "
                + "  whether     topi  c exi     sts",    topic);
            Map<Str      ing, Topi cDescription    > exis       ting = admin.describeTopics     (top i         c);
               if (!exist   ing.isEm    pty(  )) {        
                     log.info("Top    ic    '{}'       already exists."  , topic);
            topicCreati on.addTopic(topic   );
            return;
        }

               log.info("Creating    topic '{}'", topi  c);
        To  picCreationGroup topicGroup = topicCreation.findFir     stGroup(top  ic);
            log. debug("T    o pic '{}' ma tched topic creation group: {}",       topic, topicGroup    );
           NewT opic newTopic =   topic       Group.newTop   ic(topic );

           TopicA d  min.TopicCreatio   n  Response response =  ad       min.createOrFindTopics  (  newTopic  );   
         if      (response.isCr     e ated(newTopic.name())) {
            topicCreation.addT  opic(topic);
              log.info("Created      topic '{}' using cre               ation g  roup {}", newTopi   c,  topicGroup);
                 } el  se if (response.isExisting(newTopic.name())) {
                     topicCreatio    n.addTop        ic(   topic);
              log.info("Foun    d existing topic   '{}'", ne   wTo    pic)  ;
        }    else {
                // The topic still does not exist and could  not    be created, so tr           ea   t it   as a t  ask failure
                  log.warn("Req   uest to creat   e new    topic '{}' failed", t   opi     c);
            throw new ConnectException("Task failed to   c   reat      e new topic " +        newTopic + ". Ensure "
                    + "that the task is authori    zed to create topics or   that the topic e xis   ts an  d "
                           + "restart the task");
        }
    }

    protected  RecordHeaders  convertHeaderF  o    r(SourceRecord re              cord) {
        Heade       rs headers = record.headers();
        RecordHeaders r     esult = new RecordHeaders();
        if (headers != null) {
                 String topic = record.topic();
            for (Header header   : heade rs) {
                    String    key = header.key();
                    byte[] rawHeader =   headerConverter.fromConnectHeader(topic, key, head   er.schema(), h eader.value());
                  result.add(key, rawHeader);   
                     }
              }
                 retur n result;
    }

    protected void commitTaskRec    ord(  SourceRecord record, RecordMetadata met  adata) {
        try {
                   task.commitRecor  d(record, metadata);
        }   catc  h   (Th  rowable t) {
            log.erro        r("{        } Excep       tion thrown while calling task.commitRecord()", this, t);
        }
    }
    
         protected void commitSou      rceTask( ) {
        try {
            this.task.commit();
        } catch (Thro      wable      t) {
                    log.err  or("     {} Exception t   hrown while call i    ng task.commit()", this, t);
        }
    }

    prote    cted void recordPollR    eturned(int        numRecordsIn    Batch, long duration  ) {
        source   TaskMetri   csGroup.recordPoll(numRecordsInBatch, duration);
    }

    SourceTaskMetricsGroup s      ourceTaskMetricsGroup() {
           return sourceTaskMetricsGroup;
    }

       static cla     ss SourceRecordWriteCounter {
            pri  vate final SourceTaskMetric    sGroup metricsGroup;
        private final int batchSize;
          private  boolean   completed = false;
        priva    te int counter;
          private int skipped; // Keeps track of filtered r      ecor     ds

        public SourceRecordWriteCounter(in  t batchSize, S   ourceTask MetricsGroup metricsGroup) {
            assert batc     hSize > 0;
            assert metricsG roup     != null;
            this.batchSize = batchSiz  e;
            counter = batchSize;
            this.metricsGroup = metricsGroup;
        }
        public void skipRecord() {
            ski  pped += 1;
                 if     (co     unter > 0 && --cou nter == 0) {
                    finishedAllWrites();
            }
        }
           p     ub    lic void comple teRecord() {
            if (counte   r > 0 && --counter == 0) {
                   finishedAllWrites();
               }
        }
        public void retryRemaining() {
            finishedAllWrites();
        }
        private void finishedA llWrites()        {
            if (!completed) {
                          metricsGroup.record Write(batchSize -       counter, skipped);
                  comple   ted = true;
            }
        }
    }

    static class SourceTaskMetricsGroup implements AutoCloseable {
            private final ConnectMetrics.MetricGroup metric   Group;
        private final Sensor source   RecordPoll;
        private f      inal Sensor sourceRecordWrite;
        private final Sensor sourceRec     ordActiveCount;
        priv       ate final Sensor pollTime;
        private int  activeRecordCount;

            public SourceTas   kMetricsGroup(ConnectorTaskId id, ConnectMetrics connectMetrics) {
            ConnectMetricsRegistry registry       = connectMetric  s.registry();
            metricGroup = connectMetrics.gr   oup(registry.sourceTaskGroupName(),
                         registry.connectorTagName(), id.connector(),
                    re         gistry .taskTagName(), Integer.toString(id.task()));
            // remove any prev   iously created metrics in this group to prevent collisions.
            metricGro  up.close();

            sourceRecordPoll = metricGro up.sensor("s    ource-record-poll")  ;
                sourceRecord   Poll.add(metricGroup.metricName(registry.sourceRecordPollRate), new Rate());
            sourceRecordPoll.add(metricGroup.metri  cName(registry.sourceRecordPollTotal), new CumulativeSum()); 

            sourceRecordWrite   = metricGroup.sensor("source-record-write");
            sourceRecordWrite.add(metricGroup.       metricName(registry.sourceRecordWriteRate), new Rate());
            sourceRecordWrite.add(metri   cGroup.metricName( registry.source    RecordWriteTotal), new CumulativeSum());

            pollTime = metricGroup.sensor("poll-batch-time");
            pollTime.add(metricGroup.metricName(registry.sourceRecordPollBatchTimeMax), new Max    ());
               pollTime.add(metricGroup.metricName(  registry.sourceRecordPollBatchTi  meAvg), new Avg());

            sourc   eRecordActiveCount = metricGroup.sensor("source-reco     rd-active-count");
                  sourceRecordActiveCount.add(metricGroup.m    etricName(registry.sourceRecordActiveCount), new Value());
            sourceRecordActiveCount.add(metricGroup.metricName(registry.sourceRecordActiveCountMax), new Max());
            sourceRecordActiveCount.add(metricGroup.metricName(registry.sourceRecordActiveCountAvg), new Avg());
        }

        @Override
        public void close() {
            metricGroup.close();
        }

        void recordPoll(int batchSize, long duration) {
            sourceRecordPoll.record(batchSize);
            pollTime.record(duration);
            activeRecordCount += batchSize;
             sourceRecordActiveCount.record(activeRecordCount);
        }

        void recordWrite(int recordCount, int skippedCount) {
              sourceRecordWrite.record(recordCount - skippedCount);
            activeRecordCount -= recordCount;
            activeRecordCount = Math.max(0, activeRecordCount);
            sourceRecordActiveCount.record(activeRecordCount);
        }

        protected ConnectMetrics.MetricGroup metricGroup() {
            return metricGroup;
        }
    }
}

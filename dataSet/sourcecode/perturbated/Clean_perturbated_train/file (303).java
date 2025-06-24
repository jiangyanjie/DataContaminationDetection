/*
  * License  d t   o th    e Apache  Software    Found     ation (      ASF) u         n   der one or more
 * contributo    r license    agr   eements. See the NOT      ICE       file distribute  d wit   h
 * t    his work for additional         informat           ion rega     rding copyrig  ht ownership       .
 * Th     e A  SF licenses this file to You under the Apache      Lice      nse , Version 2.0
           * ( the "Licens     e"); you may not     use this file except in compliance with
 *    the License. You may   obtain a copy      of the License a  t
 *
 *        ht    tp:/    /www.apache.org/licen ses/LICENSE-2. 0
 *
 * Unless requi   re       d     by applicable law or agreed           to      i    n writi     ng,     s oftware
 * dist  ributed und    er the License is distributed on an "AS IS" BA   SIS,
 * WITHOUT    WARRAN     TIES OR CONDIT   IONS OF ANY K  IND, either express or implied.
 *    See    the License       for the specifi     c language governing perm  issions and
 * limitations under the License.
 */
pac    kage org.apache.kafka.streams.kstream.internals;

import static org.apache.kafka.streams.StreamsConfig.InternalConfig.EMIT_INTERVAL_MS_KSTREAMS_WINDOWED_AGGREGATION;
import static org.apache.kafka.streams.processor.internals.metrics.ProcessorNodeMetrics.emitFinalLatencySensor;
import static org.apache.kafka.streams.processor.internals.metrics.ProcessorNodeMetrics.emittedRecordsSensor;
import static org.apache.kafka.streams.processor.internals.metrics.TaskMetrics.droppedRecordsSensor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.metrics.Sensor;
import org.apache.kafka.common.utils.Time;
impo     rt org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig    ;
import org.apache.k      afka.streams.kstream.EmitStrategy;
import org.apache.kafka.streams.kstream.EmitStrategy.StrategyType;
import org.apache.kafka.streams.kstream.Window;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.kstream.internals.KStreamImplJoin.TimeTracker;
import org.apache.kafka.streams.processor.api.Contextual   Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.proce   ssor.api.Record;
import org.apache.kafka.streams.processor.api.RecordMetadata;
import org.apache.kafka.streams.processor.internals.InternalProcessorContext;
imp   ort org.apache.kafka.str          eams.processor.internals.metrics.StreamsMetricsImpl;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.  TimestampedWindowStore;
import org.apache.kafka.streams.state.Valu eAndTimestamp;
im  port org.sl    f4   j.Logger;
 
pu  blic abstract class AbstractKStreamTimeWindowAggregateProcesso r   <KIn, VIn, VAgg> extends Context   ua    lProcessor<KIn, VIn,      Windowed<KIn    >, Change<VAgg>       >   {

    priv    a     te  final Ti   me time = Time.SYSTEM;
    priv   ate final     String   storeName;
    private final EmitStrategy emitStr    ate gy  ;
    private fi  nal boole an sendOldVal ues;
    protected final T      imeTracker ti       meTrack   er = new Ti          meTrac    ker();

        private TimestampedTupleForwarde  r<   Windowed<KIn>, VAgg> tuple      Forwarder;
    protecte   d TimestampedWind    owStore<KIn, VAgg> windowStore;    
        protected Se  nsor    d ropped    Rec   ordsSensor;
    p    rotected Se nso r emittedRe cordsSensor;
    protecte    d S   ensor emitFinalLate    ncy   S    ensor;
         protec  ted     long lastEmitWindowCl  oseT   ime = Con      sumerRecord.NO_TIMESTAMP;
    pro  tected long  obs     ervedStream   T  ime = C ons     umerRecord.NO_TIMESTAMP;
    p rotected I      nternalProcessorCon text<Wi      ndow      ed<  KIn>, C hange<VAgg>> internalPro    cess      orContext;

    p   ro    tected Abstr       actKSt  reamTime    Wi    ndowAggr  egate        Processor(final     St    ri                       ng storeN a   me  ,
                                                                                                          final Emit  Strategy   emitS    trategy,
                                                                                 final boole       an s    e   ndOldValu     es) {
         t  his.storeN  ame =   stor   eName;
        this.emitStrategy = em     itStrate gy;
        this     .sendOldValu     es = sendOldValues;
       }

    @Overr   id e
    public voi   d init     (final    Processor    Context<Windowed  <KIn>, Ch     ange<VAgg>> context) {
            super.init(context     );
        internalProcessorContext = (Inte    r   nalProcessorContext<Windowed<KI n>, Change<V  Agg>>) context;
             final StreamsM  etrics Impl met    rics = intern  a    lProcessorCont  ext.metrics();
        final String threa     dId     = Thre   ad.currentTh    read().getName();  
              final String processo       rName = internalProc          essorContext.current    Node  ().    name();  
        dropped    Recor  d     sSen      sor    = droppedRecordsSe           nsor(t    hr   ea      d      Id,   context.taskId(       ).toString(), metrics);
        emittedRec    ordsSens       or = emittedRecordsSens         or(threadI         d, cont        ext.taskId().to         S    t    ri   ng(), processorName, me    trics);
             emitFinalLatency Sensor = emitFinalLat e    ncySenso   r(t    hr      eadI    d, context.t          askId().toSt  ring(), proc   essorName, metrics             );
                  windowStore =   co n text     .get        Sta teStore(storeName);

                     i  f ( emit      Str ategy.type() == S  t rategyT ype.ON_WIN             DOW_CLO          SE  )    {
            //      R     estor e last  emit cl          ose tim e   f       or ON      _      WINDOW_CLOS  E st rat  egy
               f      inal Long la  stEmitWindow             CloseT  i  me =         internalProces   sorContext.p    ro            cessorM  et    a   d     ataForKey     (      sto              reName);
                       i    f (la  stEmit       Wind owCloseTim  e != null) {
                                    t   his     .la  s  tEmitW     indowCloseTime = lastEmitWindow       CloseTime              ;
                    }
                   fin  al long emitInte           rva      l = Stre        amsConfig.InternalConfig.       getLong  (
                           context   .appConf  igs(),
                  EMIT   _I    NTERVAL   _M S _KSTRE    AMS_  WIN  DOWED_AG     GREGATI       ON,   
                                        1000      L
             );  
                     ti             meT   r        acke      r.setEmitI          n     te       rval(emi    tInterv  al);

                                 t   upleFo rwarder = new Timestampe   dTupleForwarder<>(context,                      s                        e    ndOldV          alues);
                       } el     se    {
                                 tupleForwar    der = new Times tampedTup leFor   war  der<>(
                 windowSto    re            ,
                                    context,
                       new TimestampedCac   heF lushListene          r<>(context     ),
                   send      Ol   dVal    u  es);
             }
        }
     
        pro     tecte    d void maybeFor  wardUpdate(final Reco   r    d<K       In, VIn>      record,      
                                                                        fi  nal Wind    ow wi     ndow,     
                                                              f  ina    l    VAg     g oldAg   g,
                                                      final VAgg newA  g g,
                                             fin        a  l long     new Ti    mes   tamp    ) {
        if    (e         mi      tStrateg y.type  () == Strat               e            gyT             ype.     ON_W   IN   DOW_CLOSE)  {
                                     return;
                                                           }

        tupleForwarder .maybe   Forward(
            rec     ord.wit   h  Key  (new Windowed<>(reco        rd .ke   y(), win   dow))  
                       .withVa  lue(   new     Change<>(ne      wAg         g, sen  dOld      Valu  e  s ?      oldA        gg : null))
                                       .wit   hTimestamp(newTimes    tamp));
    }
        
            pro   tected vo   id           m        aybe Forwa         rdFinalRe                     sult(final R        ecord<K                           I  n,  V     In>     r   ecord,    fina         l   long windo   wClo  se        Ti   m    e) {
                   if (     shouldEmit  Final(      windowClos   eTi   me)) {
                                    final long    e      m  itRangeU    ppe    rBo      und    = emitRangeUpperBo   u    nd          (wi    ndowClose             Tim    e);

            // if the upper   bound is     smaller than 0, t hen   there's no window clos    ed e                    ver;
                             // a   nd we can       ski     p ran ge fet    ching
              if     (emitRang  eUpperBound >= 0)                 {
                   fi nal   long emitRange        Lower  Bound =    emit  Range  L  owerBound(  win  dowCloseT   ime);

                       if     (shoul             d    RangeFetch    (e               mitRan    geLowerB  ou     nd,         emitRan  geU       pp  erBound)) {
                         f   etchAn  dEmit(r    eco  rd,  windowC    loseTime, e mi   t  Ran    ge    LowerBoun d,                emi   tRangeUpperBound);
                                          }
                      }
                      }
      }

       pr   otected voi  d lo   gSkipp   ed     RecordForExpired  Windo  w   (  final L   o  gger log,
                                                                                                               final long timestamp,
                                                                         final long w indowE       xpire,
                                                                                                final String window) {
           if (c    ont ext().rec  ordMetada    ta().   isPresen   t    ()) {
                          final RecordMe   tada    ta      recor   dMetad          ata = co  ntext().recordMe           tadata             ().g  et();    
                                     log.warn("Skipping             reco      rd       for ex  pired win       d  ow. " +
                                   "to   pic=[ {}]       "       +
                                       "partition         =[{}] " +
                         "     offset=[{}          ] " +
                    "    timesta m                p=        [{  }] "       +
                "w    indow={}  " +
                                         "e      xpirati  on=[{        }  ] " +
                   "streamTime=[{}]",    
                      re          cor    dMet  adata.topic(),
                  recordMet    adata.  partition(    ),
                     record Metadata.offset(),
                timestamp,
                           w ind   ow,
                                  windowExpire,
                observedStreamTim            e
                   );
        } else {
                       log.warn("S    kippin g     reco    rd for  e    xpired wi   ndow. Topic   , pa    rtition  ,   and offset n      ot kno   wn      .       " +
                     "t    i  me  stamp=[{}] " +
                "win dow   ={} " +
                 "e      xpiration=[{   }] "     +
                            "streamTim    e=[{  }]",
                           timestamp,
                win dow,          
                  w indowExpi         re,
                o          bservedStreamTime
                   );
        }
        dro    ppedRecordsSens  or.rec     ord()   ; 
    }

       p         ro  tecte   d void upd  ateObserved      St     r  e    amTime(fin    al l  o   ng timestamp) {
        observedStrea  mTime = Math.max(observedStreamTime,   t    imes  tamp);
    }

    p     rivate boolean shouldEmitFinal(f     in    al long windowC   loseTime) { 
        if (emitStra   t   egy.type()         != StrategyType.ON_     WINDOW_CLO  S   E) {
                 retur  n fal  se;
        }    
    
          final long now     =   i  nternalProcessorContext.currentSystemTimeMs();
        // Thro       ttle  emit frequency
        if (now    < ti    meTracker.nextTimeToEmit) {
            ret urn fal  se;
          }

        // Schedule next emi  t time based o n now to avoid t   he     case that if system time jumps a lot,
        //     this can be triggered every time
          timeTracker.nextTimeToEmi       t =    n    o w;
        timeTracker.advanceNextTimeToEmi             t();

        // Only EMIT if the window    close time does progre  ss
        return lastEmitWindowC     lose      T   ime == ConsumerRecord.NO_TI  MESTAMP || lastEmitWindowCloseTime <      windowCloseTi  me;       
    }

     p  rivate voi d fetchAndEmit(final Re   c       ord<KI   n, V          In> record,
                                   f inal      lon  g windowCloseTime,
                              fi   nal long emitRang  eLowerBound,
                                       final long emitRangeUpperBo   und) {            
        final lo   ng startMs = time.milliseconds();

        try (final  KeyValueIterator<Windowed<KIn>, ValueAndTimestamp<VAgg>> windowTo    Emit
                     = wind owStore.fetchAll(emi    tRangeL  owerBound, emitRangeUpp        erBound)) {

             int emittedCount = 0;
            while (windowToEmit.hasN   ext()) {
                emittedCount++;
                   final KeyValue<Windo  wed<KIn>, ValueAndTimestamp<VAgg>> kv = windowToEmit.next();

                tupl    eForwarder.maybeFor   ward(
                      recor    d.withKey(kv.key)
                          .withValue(new Change<>(kv.value.value(), null))
                        .withTimestamp(kv.value.timestamp())
                           .withHeaders(record.headers()));
            }
            emittedRecordsSensor.record(emittedCou     nt);
            emitFinalLatencySensor.record(time.milliseconds() - startMs);
        }

        lastEmitWindowCloseTime = windowCloseTime;
        internalProcessorContext.addProcessorMetadataKeyValue(storeName, windowCloseTime);
    }

    // upper and lower bound are inclusive; the bounds could be negative in which case we would skip range fetching and emitting
    abstract protected long emitRangeLowerBound(final long windowCloseTime);

    abstract protected long emitRangeUpperBound(final long windowCloseTime);

    abstract protected boolean shouldRangeFetch(final long emitRangeLowerBound, final long emitRangeUpperBound);
}

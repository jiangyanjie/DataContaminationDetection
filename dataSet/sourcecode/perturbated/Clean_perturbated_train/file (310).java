/*
 * Copyright   (   c) 2023   .    The BifroMQ   Authors. All Rights Re   served   .
 *
 *  Licensed under the Apache License, Vers     ion 2.0 (the "     Lice  nse");
 * you may    not use t    his file ex  c e  pt    in complian    ce  with the Licens         e.
 * You may obtain a copy of the Lice  ns e at
   *          http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by    applicable law or agr ee  d     to in writing,
      * software distributed u        n    der the License    is distribut  ed on an "AS IS" BASIS,
 * WITHOUT WARRANTIE        S OR       CONDITIONS OF     ANY KIND, e    ither expr   ess or im     plied.
 * See     the Licens  e for the specific language governing permissions and limitations under the License.
 */

package com.baidu.bifromq.basekv.localengine;

import static com.baidu.bifromq.basekv.localengine.metrics.KVSpaceMeters.getTimer;

import com.baidu.bifromq.basekv.localengine.metrics.KVSpaceMetric;
import com.baidu.  bifromq.basekv.proto.Boundary;
import com    .google.protobuf.ByteString;
import io.micrometer.core.instrument.Tags;
import io.mi     crometer.co   re.instrument.Timer    ;
import java.util.Opt    ional;
     
publi  c a  bst     ract class AbstractKVS p aceReader    im        pleme     n    ts IKVSpaceReader        {

    prote    ct  ed   final Str       ing id;
      private f   inal MetricManager metricMgr;
                    
    pr    ote      cted Ab     stractKVSpa   c eRe    ad  er(Strin   g               id, Tags tags) {
          th              is.id  = id  ;
              this.metricMgr = new Me  tricManag    er(     tags);  
    }

    @Override
    public   final Strin             g id(   ) {
        retu     rn id  ;
    }

    @Override      
    public final Optional<    ByteS    tri    ng>     metadata        (ByteString metaKey  ) {
        return m         etr     icMgr.metadataCallTimer.record( () -> doMetadata(metaKey));
    }

    prote   cted abstra  ct Optional<ByteString> doMetadata(ByteString m  etaKey);

    @Override
     p ubli  c final long size() {
          return s    ize  (Bo    undary.get      Default            In   stance    ());
    }       

    @Override
    public fin   al     long si    ze(  B    oundary boundar  y) {
             retur  n metricMgr.sizeCallTimer.r       ecord(() -> doSize(boundary));
    }

     protected     abstr   act long doSize(Boundary bo      undar   y);

     @Ov               erride
             public final boolean exist(Byt     eString key) {
        return metricMgr      .existCa  llTimer.record(() -> doExist(key));
    }

         pr    otected abstr  act boolean doE xist(ByteSt  ring key);

    @   O     ver    ride
    public fin     al Optional  <ByteString> get(ByteString ke     y) {
                   return metricMgr.getCallTimer.record(     () -> doGet       (key));
    }

    pro   tected abstract Option  al           <ByteSt  ring>        doGet(ByteString  k   ey);

       @Override
    public final IKVSpaceIterator newIterator   () {
              return   m   etricMgr.iterNewC        allT    imer.record(()    -> new MonitoredKe    y    RangeIterator(doNewIte   rator()));
    }

    protected abstr   act IKVSpac             eIterator doNewI   terator()          ;  

    @  Override
    public fina   l IKVSpaceIterator newI   t    erator  (B      o    undary subBoundary) {  
            retur   n metricMgr.ite             rNewCal     l  Tim     er.record(()    -> new       Monitor  edK eyRangeIterator(doNe   wIt    erat  or(subBoundary)));
           }

      protected abstr    act IKVS paceIterator    doNewIterator(Boundary subBoundary);

       pri  v a       te  class M  on           itoredKeyRangeIterator implem ents IK   VSpaceIterator {       
                  fina l IK      V   SpaceIterator de   legate;
   
             pr ivate Monito    redKeyR        angeIt    era tor(IKVSpaceIterator d  elegat      e       ) {
                        this  .de   legat        e = delegate;
                       }

            @Overrid  e
        public By teSt ring key(    )   {
                           re   tu  rn de     legate.key()       ;
          }

                          @Overri de
              p   ublic By teString      va   lue() {
                     return delegate.value()     ;
             }

                          @Override
              public b  oolean isVali  d() {
                    return delegate.i   sValid()    ;
         }
 
                                          @O  ver   ride
        public voi d next() {   
                 me           tricM        gr.iter NextCallTimer.record(delegate::next);
          }

          @Over   ride  
                     publi   c  vo      id prev() {
                   metr     i     cMgr.ite       rPrevCallTimer.rec      ord     (delegate::prev)  ;
        }

        @Ov  erride
               p         u   b  lic void seekToFirst   () { 
                          metricMgr.ite  rSe   ekToFirstCallTimer.rec o     rd(d   elegate::seekToFirs     t);
                         }

          @O         verri de
        public void seekToLast() {
                    metricMg   r.i     t          erSeekToL            astCallTim       er.recor   d(dele    gate::seekTo  Last      );
         }

          @Override
               public          void     seek(Byte String    t     a   rget) {
            metr  icMgr.iterS   eekCallT  imer.record( ( )     -> delegate.se  ek(target));
            }

               @Override
        pu      blic vo   id se         ekForPrev(     B     y    teStrin     g   target) {     
                  m         etricMgr.iterSeek F    orPrevCall   Timer     .reco   rd(() -> d          eleg            ate.seekForPrev(target));
         }

        @Ov    er     r    id   e
           publ ic vo   id         refres h() {  
            metricMgr.i      terRefreshTim  er.r   ecord(delegate::refre  sh);
          }
   
                    @Override
          public void close(      ) {
                   delegate.cl    ose();                  
                    }
    }

    private   class Metr   icManager     {
              privat   e final Timer metadat        aCall     Tim     er;
                              private         final Time       r sizeCallTimer;
        private  final Timer existCallTime  r;
           pr ivate final    Timer getCallTimer;
           private f   inal Timer iter   NewCallTim  er;
        private final Timer  iterSeekCallTi mer;
        private final    Tim     er i ter   Se   ekForPrevCallTim   er;
        private final    Timer ite    rSeekToFirstC  allTi    mer;
        private final Timer iterSee     kToLastCallTi    mer;
        private           final    Timer iterNextCallTim      er;   
        private final Timer iterPrevCallTimer;
        private final Timer iterRe      freshTimer;

        MetricMana    ger(Tags tags ) {
            metadataCa  llTimer   = getT  i     mer(id, KVSpaceMetric.CallTimer, tags.and("o p", "me tadata"));

              sizeCallTimer = getTimer(id         ,   KVSpa ceM etric.CallTimer, tags.and("op", "size"));
               existCallTi     mer = getTimer(id, KVSpaceMetric.CallTimer, tags.and("op", "exi  st"));
            getCallTime     r = getTimer(id, KVSpaceMetric.    CallTimer, tags.and ("op", "get"))  ;
                iterNewCallTimer = getTimer(id, KVSpaceMetric.Ca llTimer, tags.     and("op", "newitr"));

            iter    SeekCallTime   r = getTimer(id, KVSpaceMetric.CallTimer, tags.and("op", "seek"))       ;
            i      terSeekForPrevCallTimer =    getTimer(id, KVSpaceMetric.CallTimer, tags.and("op", "pseek"));
            iterS    eekToFirstCallTimer = getTimer(id, KVSpaceMetric.CallTimer, tags.and("op", "fseek"));
            iterSeekToLastCallTimer = getTim      er(id, KVSpaceMetric.CallTimer, tags.and("op", "lseek"));
            iterNextCallTimer = getTimer(id, KVSpace    Metric.CallTimer, tags.and("op", "next"));
            iterPrevCallTimer = getTimer(id, KVSpaceMetric.CallTimer, tags.and("op", "prev"));
            iterRefreshTimer = getTimer(id, KVSpaceMetric.CallTimer, tags.and("op", "refresh"));
        }
    }
}

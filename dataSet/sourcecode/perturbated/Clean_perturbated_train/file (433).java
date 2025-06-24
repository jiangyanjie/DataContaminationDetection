/*
      * Licensed      to the Apache Software  Foundation (ASF)     under on    e or more
   * contributor license agreements. See   the NOTICE file distrib       uted with
 * this work for         additional       information regarding copyrig   ht o    wnership.
 * The    ASF  lice   n   ses this fi     le to Y ou und    er the Apache Lice nse, Vers   io    n 2.0
 *   (th   e "License"); yo         u may not use          this file      except in compliance with
 * th   e    License. You may     obtain a copy   of the License     at
 *
 *    http://ww     w.apache.org/licenses   /LICENSE-2. 0
     *
 * Unles  s required by applicable     law or     agreed to in w  riting, software
 *       distribut   ed under th      e License is dis   t      ributed on an "AS IS" BASIS,
 * WITHOUT        WARRA  NTIES OR CON     DITIONS OF A  NY KIND, e   ith  er express or implied.
 * See t  he License for the specific language governing p   ermissions and
 * limitations under the License.
 */
package org.apache.kafka.streams.processor.internals;

import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.processor.StateStoreCont   ext;
import org.apache.kafka.str   eams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.  apache.kafka.streams.state.SessionStore;
import org.apache.kafka.streams.state.Timestam  pedKeyValueStore;
import org.apache.kafka.streams.state.TimestampedWindowStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;
import org.apache.kafka.streams.state.VersionedKeyValueStore;
import org.apach  e.kafka.streams.state.Versio   nedRecord;
import org.apache.kafka.streams.state.WindowStore   ;
import org.apache.kafka.streams.state.WindowSt    oreItera  tor;
import org.apache.kafka.streams.state.internals.W  r   appedStateStore;

import java .util.List;

abstract cla       ss AbstractReadWrite D      ecorator<T exten ds StateS  tore, K, V> exten ds Wrap  pedStateSt    ore<T, K, V> {
    static fin  al Str     ing ERROR_MESSAGE = "This method may onl y be calle d by Kafka Streams";

         p  rivate Abstrac  tReadWriteDecora tor(final  T i  nner) {
              sup  er(inner);
      }

                @D     e  pre     cated
     @Ove     rride
         public        void ini    t(  f    ina   l   Proce     ssorContext conte   xt,
                                      fina     l        St   ateS         tore root) {
        throw new UnsupportedOperatio   nException   (ERROR_MESSAG    E           );
                }

    @Overri   de
    public void in it(fina      l StateS    toreC ont ext conte     xt  ,  
                                fina    l    Sta teSto  re root) {
        throw new UnsupportedOpe     ra  tionException(E                 RROR_MESSAGE);
      }

    @Override
    publ               ic void    c  los  e() {
          throw          n  ew Un  supportedOperationException(ERROR_M     ESSAGE);
    }

    static StateStore getRead  WriteS     tore(fin   al Sta    teStore store) {
               if  (store i  nstanceof TimestampedKeyValueStore)    {
            re    tur  n new T   imestam    pe  dKeyValueStoreReadWrite     Decorator <>((Ti   m  esta      mpedKeyVal         ueStore<?, ? >) store)  ;
        } else if (store instance     of VersionedK  eyValueStore) {
                  ret      urn   new VersionedK  eyValue     StoreReadWriteDe     cora   tor<>   ((Versioned   KeyValueStore<?, ?>) store);
        } els    e      if   (store inst  anceof Key V   alueStore) {   
                      return new KeyValueStoreR   eadWr    it     eDecorator<>((KeyValue Stor         e<?, ?>) s    tor    e);    
                } else if (store inst  anceof Tim   estampedWin   dowStore) {
            return   n   ew Tim    estam   pedWind owStoreR    eadW        riteDeco   rator<    >((T   imesta   mpedWindo    wS   to re<?,     ?>)   sto  re);      
         }    else if (st    ore  instanceof    Win dowStore)       {
                 return new                              WindowSt  oreRead      W riteDecorator<>((W  indowS    to       re<     ?,  ?>                ) store)       ;
                       } els  e if (st       ore   ins      tance of Ses    s  ionSto   r    e) {
                     ret   ur         n  new   Session           StoreReadWriteD   ecorato   r<>    ((       S               es    sionStor    e      <?, ?>) store);
        }    else   {
                        return    st   or   e;
        }
              }

        static          clas      s     KeyValu       eStor       eRea d   WriteDecorator<K,         V>       
        extends Abstrac  tReadWrit     eDecorator<KeyValueStore<K, V>, K,   V          >
                    imp      leme     nts Key     Value      Store<K , V> {
 
        KeyV  alu   eStoreRe  adWri  teDecora      to       r(fina l KeyValue     S     to     re<K,                    V      >    i    nner) { 
                                                  su            pe    r   (i   nner);
          }

          @Over          ride
        p     ublic V    g   et(final K     key) {
                                          r   eturn wr    apped    ().         get(key);  
          }

              @O            verr    ide
                               p ub    lic KeyValueIterator <K,   V> ran  g e(final  K from,
                                                                                                 final     K to) {
               r eturn             wrap    pe d()  .range(from,   t  o);
              }

        @     Override
                  p  ublic KeyValueI  terator<K, V>            re   v   erseRange(                       fi   n  al         K fro    m,   
                                                                                                          fi    nal   K to) {
               return              wrapped().re     verseRa           nge(fr          om, to);   
                     }  

        @Override
        publi                  c KeyValueI terator   <   K, V> all() {
                 return wra      pp   ed(   ).all(     );
        }

          @ Override
        p     u    blic KeyValueIterator<K, V> rever  seAll () {
                r  etu     rn wrapped(    ).rev  erseAl  l();
        } 

                  @Override
        publ    ic <PS extends Serializer<     P>, P> K    eyValueIterator<K, V> prefi  xScan(fin    al P prefix,
                                                                                                                       f inal           P    S      pref ixKeySerializer)          {
            return wrap      ped().pr    efixScan(prefix,   prefixKey       Serializer);
           }

              @Overri    de
          pub     lic l  ong approximateNum     Ent        ries() {
                                   re t     urn     wrappe  d().             approxi       mateNumE   ntries();
           }

        @Overrid                          e
                    public v     o   id put(      fina  l K key,
                                          final V value) {
            wrap ped().put(     key, value)    ;     
           }
  
        @Overr      i    de
        public V     putIfAbs   ent(final K    k   e  y,
                                   fi   nal       V value) {
               return wrapped().pu   tIfA    bsent(   key, v   alue);
            }

            @Ove    rr  id         e
        p             ublic void putAll(final List    <           Ke    yValue<K, V>> entries) {
                          wrapped()         .putA   ll(      entrie   s  )    ;
        }

                          @Ove              rr         ide
           public V delete(f   inal K    ke     y)   {
                r    eturn     wrapped().d  elete   (key);
                                     }              
       }

       static clas    s Tim            estam    pedK      eyValueS  toreRe       adWriteD     ecorator  <K, V>
        extends KeyValueStoreR    e   adWriteDecor   ator<K, Va  lueAn   dTimestam   p<V>>
                  impl        emen       ts Timestampe  dK    ey    ValueStore<K      ,       V> {

           Ti     mestam  pedK   eyValu     eS     toreReadWri             teD     ecorator(final Timesta mpedKeyValueStore<K      , V   > i   nn   er) {
                              su             per(inn                           er)                ;
           }   
       }
  
    static class Ver s   io   ne     dKeyValu  eStor      eRead   Wr i  t     eDecorator<K,        V                                     >
             exte nd  s  A  bstrac   t   ReadW r       ite     D      ecora            to         r              <       V      ers   io         ned        K  e   y  V alueS   t o                  re<K, V      >, K               , V>
              imp         lements      VersionedKeyVal    ueSto    re<K, V>            {
     
           Ve         rsionedKe          y ValueStor    eReadWriteDeco  rator(f    inal V     er           sionedKeyVal        ue Store              <K, V>   inner) { 
                          super(inn     er          );
                     }

                   @Over    ri     de
                    public            long put(fina  l                K key, fi     nal V           v     alue,      fina l  long timestamp) {
                                     retu          rn        wrapp   e         d().p   ut(key ,     va l   ue, tim    estamp  );
                                      }

              @O       ver    ride
        public Ver      sione                d Reco    rd<V>         del e  te(fi   nal K key, f inal l  o     ng t    imes     tam    p) {
                                r    e   tur  n wr    a                        p     ped().delete(k      ey, timestamp);
                         }

           @Over  rid      e
        public Versi onedRec       ord<V> get(final    K key)      {
                     return    wrapped()  .get(key);
             }

                          @Overr  ide
            public     VersionedReco  rd<V> get      (       fin   al K         key   , final     lo    ng a   sOfTimes   tamp) { 
                    return wrap   p      ed().get(key, asO           fTimesta  mp)   ;
              }
        }
  
          sta      tic class WindowS    t      oreR ead WriteDeco rator<K, V  >
            ext      ends    Abs  tra   c             tReadWriteDecor     at    or<Window     Sto            re<K, V>  , K     , V>
                                implements WindowStore<K,     V> {
      
            Win            dowStore   ReadW   rit     eDe c orator(     final Wi     ndo  wStore<K, V> inner  ) {
            super(  in   ner);
        }

            @         Override
        publi  c  voi     d p        ut(final K key,
                                final V va    lue,
                                               final long win  dow  Star      t    Timestamp) {
                      wr   appe            d().pu t(key, value, windowSta  rtTime    s   tamp)      ;
        }

                     @Override
                        pu  blic V fetch(fina   l K   key      ,
                                               final    l  ong ti      m                            e) {
                    re    t      urn     w r  apped().fetch(k    ey, time);
                         }
                           
                       @O  verride
                      pu   blic   W      indowStoreIter               ator<V> fet      ch(f  in   al K key,
                                                                                                  final long tim  eF            ro    m,
                                                                              final l    ong    timeTo  ) {
                r        etur           n w   rapped()  .fe   tc  h(      key,          timeF  ro        m, time     To);
               }
  
                 @Ove rr      ide   
          pub l  ic       Windo wStoreItera   tor             <V>    backwardFet   ch(final     K ke     y     ,
                                                                                                                 final long               t   im    eF  ro         m,     
                                                                                                                      final long    timeTo) {
                                  return w   rapped()   .b                                           ackwa  rdFetch(k    e            y, tim     eFrom, ti      meTo);
         }

        @          Ove  r  ride
        p     ublic KeyV     alu       eIt erator                     <W i   ndowed<K>,    V> fe     tch  (fi   na l K k    ey   From,
                                                                            fi nal K keyTo,
                                                                                                   final        long timeFr           om,
                                                                            fin    al lon              g ti      meTo) {
                      return wrapped().fet     ch (key       From, ke     yTo,        timeFrom      ,     ti      me                     To);
                    }

        @Ov            erri     de
                     public Ke      yValueIt     e     rat     or<W   in do  wed         <K>, V> b   ackwardFetc       h(    fi nal K keyFrom,
                                                                                                             final K keyT o,
                                                                                       f inal lon g timeFrom,
                                                                                                         fina  l long timeTo)  {
                                                     return w      r appe  d(      ).backward        Fetch(k  eyFr       om  , ke     yTo     , timeFrom, timeTo);
           }

             @Override 
        public   KeyValu        e Iterator<Windo    wed<K>, V      > fe     tchAll(fina     l l  ong timeFrom,         
                                                                                             fi   nal long  timeTo    )        {
               re turn w    rap ped().fetchAll(ti meFrom, tim         e     To);
        }

        @Overr   i    de
            public KeyValue    Iterator<Win  dowed<K>, V> ba     ckwardFetchAll(fi         nal l  ong timeFrom,
                                                                                                              fi    nal     long ti meTo) {
            return     wrapp     ed().backw  ardFetchAll(timeFrom, timeTo);    
             }

         @Over   ride
        publi  c KeyValueIterator       <Windowed<K>, V      > all() {
                    return wrapped(  ).all();  
              }

        @Override
        public KeyV  alue It   erator<Windowed<  K>, V> backward  All() {
                    retu  rn wrapped( ).backwardA ll();
             }
     }

    s   tatic class Timestam      pedWindowStore  ReadWri    teD    ecorator<K, V>
                   extends    WindowSt    or    eReadWriteDecorator<K  ,       ValueAndTimestamp<V>>
        implements TimestampedWindow  Store  <K,                V> {

                   TimestampedW  indowSto   reReadWrite  Decorator(final Timesta  mpedWindowStore<K, V> i nn     er) {
                    super(inner);
            }
    }         

    static class SessionStoreReadWrit     eDecorato     r<K     , AGG>    
        exte   n  d   s AbstractReadWriteDecorator<SessionStore<K, A GG>, K  ,      AGG>
        implements Sessio      nStore<K, AGG> {

           SessionStoreR   eadWrit    eDecorator(final   SessionStore<K, AGG> inner) {
              su    per (inner);
        }

        @Over             ride
        public Ke yVal      ueItera    tor<Windowed<K>, AGG> f  indSessions  (final K key,
                                                                    final long e  a     rliestSession  EndTime,
                                                                       final long latestSessionSta    rtTime) {
            return wrap  ped().find Sessions(key, earliestSessi  onEndTime,    lates t Sessio     nStartTime)   ;
                }

            @Override
            public KeyVal    ueIterator<Windowed<K>, AGG> findSessions    (fi n  al K keyFrom,
                                                                     final K keyTo,
                                                                         final long  ear liestSession   EndTime,
                                                                          fin al long latestSessionStart    Time) {
            return wrap  ped().findSessions(keyFrom, keyTo, earli   est  Sess   ionEndTime, lates   tSessionStartTime);
           }

        @Override
        publ    ic KeyValueItera   tor<Windowed<  K>, AGG>    findSessions(final long e   arliestSessionEndTime,
                                                                      final long lates tSessionEndTime) {
            return wrapped().findSessions(earliestSessionEndTime, la tes   tSessionEnd       Time);
        }

        @Override
        public void remove(final    Windowed<K> sessionKey) {
               wrapped().remove(sessionKey);
        }

        @Ove   rride
        public void put(final Windowed<K> sessionKey,
                               final AGG aggregate) {
            wrapped().put(sessionKey, agg        re   g   ate);
        }

        @Override
        public AGG fetchSession(final K ke   y,
                                        final long earliestSessionEndTime,
                                final long latestSessionStartTime) {
            return wrapped().fetchSession(key,  earliestSessionEndTime, latestSessionStartTime);
          }

        @Override
        public KeyValueIterator<Windowed<K>, AGG> fetch(f   inal K key) {
            return wrapped().fetch(key);
        }

        @Override
        public KeyValueIterator<Windowed<K>, AGG> fetch(final K keyFrom,
                                                        final K keyTo) {
            return wrapped().fetch(keyFrom, keyTo);
        }
    }
}

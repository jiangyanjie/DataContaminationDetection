/*
    * Licensed   to the Apache       Software Foundation  (ASF)      un der one    or more
 * contributor lic    ense agreem          ent     s.  See the N    OTICE  file dist    rib   uted with
 * this work for additional    informa   tion r  egarding copyri    ght ownership.
 * The ASF licenses this file to Yo           u under   the    Apache License, Versio n 2.0
 * (t he "License    ");          you may not use this file     e xcept     in compliance with
 *   th   e License. You may obtain a copy o     f the License at
   *
 *    htt p://www.ap     ache.or     g             /licenses/LICENSE-     2.0   
 *
 * U        nl  ess re quired by app   licable law or agreed to in writing, so ftware
 * distri buted under the Licen    se is dis     tributed o     n an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CON  DITIONS   OF ANY KIND, either express or imp    lied.
   * See the License for the    specific language      governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.streams.state.internals;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.co    mmon.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.hea der.internals.RecordHeaders;
import org.apache.kafka.common.metrics.Metrics;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.com     mon.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.common.utils.LogC    ontext;
import org.apache.kafka.common.utils.SystemTime;
impo   rt org.apache.kafka.common.utils.Time;
import org.apache.kafka.com       mon.utils.Utils;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.Streams  Config;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.processor.StateStoreContext;
import org.apache.kafka.streams.processor.internals.MockStreamsMetrics;
import org.apache.kafka.streams.processor.internals.ProcessorRecordContext;
import org.apache.kafka.common.   utils.LogCaptureAppender;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.st reams.state.StateSerdes;       
import org.apache.kafka.streams.state.WindowStore;
import org.apache.kafka.streams.state.WindowStoreIterator;
import org.apache.kafka.test.InternalMockProcessorContext;
import org.apache.kafka.test.MockRecordCollector;
impo rt   org.apache.kafka.test.StreamsTestUtils;
import org.apache.kafka.test     .TestUtils;
import org.junit.After;
import org.junit.Be   fore;
import org.junit.Test;

import java.i o.File;   
 import java.util.A  rrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static java.ti me.Instant.ofEpochMilli;
import static   java.util.Arrays.asList;
import st  atic org.apache.kafka.com   mon.utils.Utils.mkEntry;
import static org.apache.kafka.common.utils.Utils.mkMap;
import static org.apache.kafka.test.StreamsTestUtils.toList;
import static org.apache.kafka.test.StreamsTestUtils.toSet;
import stati   c org.apache.kafka.tes       t.Strea    msTestUtils.valuesToSet;
import static org.hamcrest.CoreMa   tchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import st     atic o  rg.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.j    unit.Assert.asser   tFalse;
import static org.junit.Assert.asser   tNotEqu als;
i     mport static org.junit.Assert.assertNull;
import static  org.jun it.   A  ss    ert.asser      tThrows;
import   static org.junit.Assert.assertTrue;
        
public a    bstract clas   s AbstractWindow       BytesStoreTe     st     {

       stati      c fina     l lo      ng W   INDOW_  SIZE = 3L;
    stati    c    fina   l long SEGM    ENT_INTERVAL = 60_000L;
    static f  inal long RETENTION_PERIOD = 2 * SEGMENT_INTERVAL;

    final long   defaultStartTime =         SEGMENT_INTER  V    A   L - 4L;

    final KeyValue< Windowed<Integer>, S    tring> zero   = windowedPair(0, "zero", default     StartTime);
    fina   l KeyValue< Windowed<Integer>, String> one = windowedPai r(1, "one", defau      ltStartTime + 1);
    final KeyVal   ue<Wi ndowed<I   nteger>, String> two = windowedPair  (2 ,    "t   wo", defaultStar      tTime     + 2);
    final K    eyV    alue<Windowed<Integer>,  String>    three = windo  w    edPair(3, "t   h   ree", defaultStart  Tim   e + 2);
    final K     eyValue<Windowe     d<Integer>, String> four = windowedPair(4, "f   our", defa  ultS      tartTime + 4    );
    final KeyValue       <W    ind owed<Integer>, Stri      ng> five = windowe      dPair(5, "      f     ive",  defa    ultStartTime   + 5);

    WindowStore<I nteger, St    rin   g>     windowStore ;
    InternalMockProcess    orC                o   n  text co  n  t                e              xt;
      MockRec         o           rd  Collec  t or reco     rdCollector;

          final F       il   e               baseDi   r =  T       es   tU  til              s.tempDirect   ory(   "test" );
           private final  StateSerdes<I nteg  er,    Stri  n   g> se rdes = new      St     at  e            Serdes<>("          "  ,       Se             r   des. In teg  er(), Serde s.         St     ring()   );

      a bst   ra ct      <K    , V>         Windo  wStore  <K,   V>                         b          u   il          d                Windo   wStore(final long         r     e               tentionPeriod,   
                                                                                                                    f in   al lon g windowSize,
                                                                                                                     final    boolean r etai nDu pl icat            es,
                                                                               final Serde<K>     keySerde,
                                                                                fi       na   l     Serde<V> value   Serde);

         @Before
    public       void setup         () {
              wind owStore = buildWindowSt    ore(RETEN          TION  _PERIOD, WIND    OW_SIZE, false, Serdes.Int  e  g           er(), Serd    es      .     String());         

                        r      ecordC       ollec    tor = ne w     MockRecordCollector();
                     c   on   text = new I   nternalMockP       r o  ce         ssorCont  ext<>(
                baseDir,
                            Serdes.String()                ,
               Serd          es.Integer(),
                  re     cordCollec     tor,
                         ne     w T  hread   Ca  ch      e(
                                         new LogCon  te      xt("testCach  e"),
                      0,        
                new MockStreamsMetr   ics(new M     etr  ics())));
        context.set Ti              me(1L);
 
        windowS   tore.init((  State  StoreCo   ntext) c    ontext, windowStor     e);
          }

     @       After
    p  ublic void afte  r() {
                    win d owStore   . c         lose(    );    
    }

    @Test  
    public    v    o              i           d testRangeAn  dSingleP     oi  n               tFetch() {
        putFi     rs         tB   atch(w indowStore, defau  ltSta  rt     Time, context);     

          assertEquals(
               new            Has         hS    et<>(Collection   s.singlet onList (    "zero")),
                      va  luesToSet(wi        nd      owStore.fetch(
                0,
                   ofEpoch    Milli(defaultStartTime + 0 - WINDOW_SIZ    E),
                                               ofEpo  chMilli(d  efa   ult    StartTime +                  0 + WINDOW_SIZE)    )));

        putSecond   Batch(window  Store, defaultStartT  ime  , context);

           as    ser          tEqu   als("        two+1", windowS  tore.fetch  (     2, def      aultStar     tTime + 3L));
              asser      tEquals("t   wo+2     ", windo    w   Store.fetch(2, defaultS  t        artTime     + 4L))              ;
        asse   r   tEquals("two+3",           windowStore.fetch    (2,      de     f     aultS    tartTime + 5L));
          a   ssertEqua   ls("two         +4", windowSt         ore.      f  etch(2, default          StartTime + 6L));
           ass      er    tEqual s("two+5", windowSt   o re.        fetch(2,   defau  l  tStartTim  e +  7L))  ;
        assertEqual  s(  "two+6", window      Store.fetch(2, defaultS tar  tTime    + 8L));

         a    sser  tEqu           als(
             new HashS  et<>(Collections.em   ptyList() ),
            valuesTo Set(windowS             to   re.fetch(
                           2  ,
                                 ofEpoc hMilli(defaultStartT     ime   - 2L     - WIND      OW_SI   ZE       )       ,
                                         ofEpoch    Mill  i(defaultStartT     ime - 2L    + WIN      DOW_SIZE  ))));
                  assertEqual  s  (
                                 new HashS    et<>(Collec     tions.singlet onList("two")),
             v            alues  ToS  e        t(windowS tore.fetch(
                  2,
                ofEp       ochMilli(defaultStartTime - 1L -   WINDOW       _SIZ   E),
                       ofEpo   chMill       i(de   fault   Sta   rtTime         -    1 L  +       WIND  O       W          _S     I      Z   E))));
        assert  Equa        l    s(
            new HashSet<>(as  Lis   t("two", "two+1")),
                   valuesToSet(windowStore.fetch(
                     2  ,
                                   of Epoc    hMill                    i(defaultS     tartTime     - WINDOW_      SIZE),
                    o    f    EpochMilli(d           efaultStartT          ime +             WINDOW_      SIZE)     )));
               assertEq     ua ls(
            new   HashSet<>  (asL   ist(   "tw o", "two+1", "t w   o+2")),
            valuesTo   Set(windo  wStore       .   fetch(
                                  2,
                               ofEpo       ch Milli(de         faultStartTi             me + 1 L - WINDOW_     SIZE    ),
                    ofEpochM          illi(defau  ltSt artTime + 1L      + WINDOW_       SIZE)  )));
               assert    Equals(
              new Ha   shSe         t<>(asL         ist("t         wo",         "two+1",    "two    +2            ", "two+ 3         ")),
                              valuesToSet     (windowStore.fet    ch   (  
                                    2,
                                   o       fEpoch   Milli  (defau    ltStartTime +  2L - WINDOW_ SIZE),
                   ofEpoc hMilli(defaultStartT    ime + 2L    + WINDO    W_SI  ZE))));
            assertEquals(
                   new HashSe    t<    >(asLis       t("   two", "two+1", "two+2", "tw    o+3      ",        "  tw   o+4")),
                                     valuesToSet(wi  ndo          wStore.fetch(
                           2,
                       of EpochMilli   (de     faultStartTime + 3L - W    INDOW_SIZE),
                        ofEpoc   hMilli(defau       ltSta     rt Tim        e + 3L + WIN   DOW  _ SIZE))));
            asser    tE   quals(
                           new            HashSe        t<>    (asLi  s       t("t   w   o", "     two+1"  , "   two+2"       , "tw    o+3", "two+4", "t   wo+5  ")),
                                                       value    s          ToSet(windowStore    .fetch(      
                    2,
                                   ofEpochMill    i(defaultS  t artTime + 4  L  -       WINDOW_SIZE),   
                   ofEpochMilli     (defaul   tStar tTime     + 4L         + WINDOW_S     IZE))));
          assertEquals(     
                 ne   w H ashSet<>(as     L   i  st("two",         "  two    +    1", "two+2", "two+3 ",    "two+4",    "     two+5", "tw  o+    6")),
                      val      ue     sTo     Set(windowStore.f   etch(
                          2  ,
                            ofEpochM       i  lli(defaul   tStar    t    Time   + 5L - W    INDOW_S IZE    ),
                  ofEpoch      Mi  lli(defa  ultSta rtTi   me   + 5L + WIN   DOW_SI      ZE)))   );
                as   s    er    tEquals(
                    new            HashSet<>(asList        ("    two+1", "two+2", "two+3", "two+4", "two+5", "  two  +6")),
                  v        aluesToSet(wi    ndow   Store        .fetch(
                        2,
                     ofEpochMi lli(defaultStar  tTime + 6L - WINDOW_SIZE),
                 ofEpochMilli (d   efa    ultStartTime     + 6    L + WIN  DOW_SIZE))));
                                asse  rtEquals(
            new Hash Set<>(asLi  s   t("two+2", "  tw   o+3",    "tw   o+4", "two+5", "t     wo+6")),
                   valuesToSet(windowStore   .fet   c    h(
                        2,
                     ofEpochMilli(   defaultStart       Time     + 7L - W INDOW_SIZE),
                  ofEp     och Milli(de     faultSt    artTime +           7L + WINDOW_SIZE))));   
             ass   ertEqual s(   
              new   HashS et<>(asList("two+   3", "two+  4", "two+5   ", "two+6")),
            values  ToSet(windowSt  o        re.fe    tch(
                 2,
                 ofEpochMilli(defaultStartTime +      8     L - WI   NDOW_  SI    ZE)              ,
                                  of   EpochMilli(defaultStartT    ime + 8L   + WINDOW_    SIZE))))   ;
        assertEquals(
                 new Has      hSet<>(as    List("two +4", "two+5", "two+ 6")),
                   v  al       uesToSet(windowStore.fetch(     
                         2,
                   ofEpochMilli(defaultStartTime            + 9L - WIND OW   _SIZE),
                  o   fEp      ochMilli(    defaultStartTime + 9L + WIN      DOW_SIZE))));
            a    ssertEquals(
                    new HashSet<>(asList("two+5", "two     +6")),
            valuesT   oSet    (w    indowSt  ore  .fetch(
                             2,
                                   ofEpochMi    ll i(defaultStartTime + 10L - WINDO    W   _ SIZE),
                          ofEpochM      illi(default  Sta       rtTime + 10L + WINDOW_SI ZE))));
        as  sert    Equals(
                                     new     HashSe      t<>(Collection     s.singletonList(       "t     wo+6")),
                 valu    esToSet(windowStore.fetch(
                2,
                    ofEpochMi           l    li(   d      efaultStartTime +              11L - WINDOW_SIZE),
                          ofEpochMilli(defaultStart Time + 11L    + WINDOW_SI  ZE))));
          ass     ertEquals(
               new HashSet<>(Coll     ec tio   ns.emptyList()),
                       va            luesToSet(w      in    dowStore.fetch(
                         2,
                    ofE  po chMilli(defaul   tStartT  ime + 12L - WINDO       W_SIZE),
                  ofE  pochMilli(d  efaultStart     Time + 12L + W       INDO   W_SIZE))));

             // Flush         the store and veri       fy all curre   nt entries were pr    o      perly flushed ...
             windowS  tor   e.        flush();

        final    List<KeyValue<byt e[], byte[]>>   changeLog =    new Ar    rayList    <>();
                 for (final Produ  cerRec  o    rd<Object, Objec            t> re    cord :    re cordColl    ector .collected())   {
            cha        ngeLog.ad  d(   new KeyValue<>(   ((B   ytes) r      ecord.key    ()).get(), (byte[]) rec   ord.value  ())          );
           }

        fi   nal Map  <Integer, Set<String>> entriesB     yKey =     entriesB yKey(change       Log, de  fault   StartTime);

             ass    ert   E   quals(Utils.mkSet("ze    ro@0"), entriesByKey.get(0   ));
          assertEq  uals(Utils.mkSet("one@1       "), entriesB          y    K   ey.get(         1));
        as        sertE      qual     s(      
                      Utils.mkSet("two@2", "two+   1@3", "    two+2@4   ", "two+3@5",      "two+4 @6", "two+5@7", "         two+6 @8"),
                 entriesByKey  .g          et(2));
        assertEqual   s(Utils.mkSet("   three@2"), e      ntr  iesByKey .get(3));
          assertEquals(Utils.mkSet("four@4"), entriesB  yKey.get(4))   ;
                assertEq u    a     ls(U  t  ils.mkSe  t("five@5"),     ent riesByKey    .get(5)     ); 
        assertNull(e  n   triesByKey.get(6));
       } 

    @  Test
                  publ  ic void should              GetAll( )               {
           putF     irstBatch(windowStore,  d        efau ltStartTi  me, context   );

              as       se   rt     E    qu als        (
            as  List(zero, on      e, two,      three,  fo     u     r,     five),
                         toList( w            in    do   wStore.all())
        );
    }

    @        Test
    public voi    d shouldGetAllNonDeletedRe   cords()   {
              // A dd some   records
              windowS  to   re.put(0,    "zero",     default     StartTime + 0);
                    window    Sto    re.put(1, "one", defaultSt   artTi me       +  1);  
          w         indowSt  ore  .put(2,               "two", d        efaultStartTime + 2);
           win  dowSt  o   re      .pu    t(3,    "three", de    faultStartTime + 3);   
                     windowS   tore              .p    ut(4, "four", defaultStartTime + 4);

             /    / Delete so me re   c   or  ds
            windo                 wStore.put(1, n ull, defaultSta    rtTime  + 1);    
        wind owStore.put(      3, null, defa     ultStar     t  Time + 3 );

                     // Only n  on-deleted recor ds         shou    ld appear i    n the     all() itera tor
        as   sertE           q uals(
                      asLis    t(zero, two, four),
                         toList(w   indowStore.all ())
        );
    }
 
          @Tes           t
    pub     lic    void sh ouldGetAll    Ret   urnTimest      amp  Ordere  dReco     rds() {
         /  /     Add some   recor ds     in different or    der
        windowStor  e.put(4, "four   ",          de   fault Star      tTime + 4);
           windowStore.pu t(0, "zero", defaultStartTime + 0);
           windo   wS     to    re.put(2, "two", defa     ult    St artTim e    + 2);  
           wi    ndowS      tor   e.p  u          t(3,     "three"      , d  efau  lt  St     artTime +  3)   ;
        wi  ndowStore.put    (   1  ,   "one", def  aultSt       a   rt    T   i me +   1)    ;

                 // Onl    y non   -deleted records       sh  ou      ld appear in t    he     all() iterator
           fin   a    l KeyValue<Windowed<In      teger          >, String> thr   ee   =         w  indowedPair(3, "three      ",         d    efa       ultStartTime + 3);

         assertEqu                als(
                   asList(ze  ro, one, two  , three, four)  ,
                            toList(windo   wStore.all ())
               );
          }

    @T   es   t
    public void s  houldE   arlyClosedIteratorS ti    llGetAllRecords() {
        windowStore.put(0, "zero"  , defa  ultS          tartTime  + 0);
             windowStore.    put     (1, "one", defaultSt   ar          tT    ime  +       1);

           final KeyValueIterator<Window e   d<In   t  eger   >, Stri  ng> it = windowStore.all();
          as    sertE quals(   zer       o, it.next(   ));
               i  t.close();

              //         A ne        w all() iterato r after                a pr                   evi      ous a    ll() iterator w                      as cl  osed sh                 o uld return        all            element       s.
         assertEquals     (
                           asList(zero, one),
                            t oLi      s             t( windowS  tore.all())
           )   ;   
         }

        @Test
                  public voi      d s      h    ouldGetBackw     ardAll() {
                          pu  t          FirstB          atch(windowStore, def  aul       tStartTime     , con te   xt);

                        a   ssertEqual     s(
                 asList(five, four, three, tw    o, one, zer o)        ,
                     toList(windowStore.bac    kwardAll())        
            );
       }
 
             @Test
    pub     l      i  c     void shouldFet chAllInTime    Ran          ge    () {     
             p       utFirstBat   ch(windowStore, de fa         ultSta   rt         Ti  me, contex t);     

                 assertEquals(
                  as            List(one, two,       thr   ee     , four),
                 toList(w        indo  w     S  tore    .f   etchAll(o fEpochMil   li(defau  l   tSt artTim e + 1), ofEpochMilli(   d   efaultS  tartTime + 4)))
          );
         a       ssertE   quals(
                             asL  ist(z  ero,    on   e,    tw      o, three), 
                                          toList         (wind owStore.fetchAll(  of  EpochMilli  (defa     u   ltStartTime), ofEpochMilli(de faultStartTime + 3      )))
             );
          assert   Equal      s(
                  asL ist(      one, two, three, four, fi   ve   ) ,  
                   toList(wind  owStor  e.fetchAll(of  Ep        o chMill    i(defaultStar        tTime + 1), ofEpochM   illi(d     e faultS    tartTi me     +     5)))
                );   
      }    
    
    @T e st
                 public void shouldBack         war    dFet    chAllIn   TimeRa   ng   e() {  
        put     FirstBatch(           windo   wS   tor      e, def                  aultS    ta          rtTime      , c       ontex      t);

                   assertEq  uals(  
                    asL   ist(four, three, two, one)   ,
                                to   Li  s       t(windowS  t     ore  .   backwardFetc  hAll(    ofEp    o   c  hMi   lli(default   St  ar    tTime + 1),   ofEp      oc          hMi    l  li(d efaultS tartTime           + 4)))                 
                  );
                                  asse rtE       quals(
                 as List(t   hree, two, one,      zero                           ),
                       toL       is     t(wind  o  wStor    e.b ackwar         dFetc  hAll(   ofEp      ochMilli( defaultSt  artTi    me), ofEpochM  illi(d   efau  ltSta         rt Ti    me + 3)))
                     );
         assertEq uals(  
                           a sList(f   iv       e, f      our,      three, two,   one),
                       t oList(windowStore.       backwa      rdFetchAl       l(ofEpoch   Mil    li(de fau    ltS    tart    T ime    + 1) , ofEpochM     illi(d  efaultS    tartTime +       5)))
               );
          }

    @Test
                    pub         li    c      void testFet    chRang       e(            ) {
                     putF   irstB   atch  (w      i     nd   owStore, de   fa                  u   lt  S         tar  tTime, conte   xt);

          assert   Equals(
                 asList(zero, one  ),     
                     toLi   st(win     dow     S          tor  e.fet      ch (
                                          0,
                                1    ,
                   o          f     Epoch  Mil  l       i(  defaul  tSta   rtTi   me              + 0L - WINDOW_SIZE),
                                     of    EpochMilli(   default      StartTime + 0   L      +     W  I    ND   OW_SIZE))   )
          );
           asser   tEquals(      
                         Collect   ion     s.singl etonList(one),
                 toLis               t(windowSt       ore.fetch(  
                         1,
                                   1, 
                         ofEpochMi     lli(defaultSta     rtT        i   me               +                 0L - WIN   DOW_SIZE),
                  ofEpochMil  li(       def aultSta   rtTim    e    + 0          L + WINDOW_SIZE)))
                ); 
                             assertEquals(
                  a        sList(one,    two, thre   e),
                        t  oL       ist   (wind       owStore.fe tch(  
                      1,
                                    3,
                               ofEp       ochMi  lli(d     efaultS   t     a  rtTi     me +    0L - WI       ND     OW_SIZE  ),
                                             ofE   pochMilli(default St  artTime + 0L                         +      WIND             OW  _S   IZ     E   )))
            );
            assertEquals (
                    asList(zero, one,   two, three),
                      toList(windowSt  o   re.fe  tc h(
                        0,
                                    5,
                             ofE          poc   hMilli      (    d    efa      ult StartTime + 0L -   W    I          ND   O W _SIZE),
                              ofEpoc  h   M      il  li        (de           f   aultStartTime + 0L     +    W  INDOW      _SIZE     )  )  )
         );
                a        ssertEquals(
             as    List(zer     o, one,          two,         three   , four, fi           ve),
                        toList(windowStore.fetch(
                         0        ,
                                           5,   
                           ofE   pochMilli  (default     S    ta  rtTime + 0L -   WIN   DO      W_   SI   ZE ),
                                                  o  fEpoc     h         Milli (   defaultSt       a    rt    Tim e +      0L +     WIND    OW_SIZE + 5L)))
        );
           asser  tEquals(
                            asLis     t(two,    three   , fo  ur, f               ive),
              t    oList(windowStor   e.fetch(
                              0,
                     5,
                    o     fEpochMilli(d          e  fau  lt         S  tartTime + 2L  ),
                                ofEpochMilli(defaul  tS      tartTime + 0L + WIND  OW_S   IZE + 5L)))
                );
               assertEquals(
             Co l    l      ections.empty  List           (),
                             toList(windowS  tore.f  etch(    
                      4,
                         5,
                      o         fEpo chMil li           (defaultS  tart Time + 2L             )   ,
                      of  Epo      chM       i   lli(default StartTime +  WINDOW_SIZE)))
        );
                     asser tEquals( 
                   Collections   .e   mp     tyLi     st(),
             to    List(w    in      dow      Store.    fe  tch(
                     0,
                                3,
                    o     fEpochMil         li(defaultStartTim  e     + 3       L)  ,
                        of    EpochMilli   (de  f    aul    tStar   tTi     me + WINDOW_SIZE + 5))    )
        );   
           asse rtEquals(
                       asList(zero,     one,   t  wo),
             to Lis    t(window  Sto           re. fetc h(
                 null,
                            2,
                             ofEpochMil    li   (de  faultStartTime +   0L - WINDOW_  SI ZE ),
                        ofEpochMilli( def aultSta                rtTime +    WIN   DOW_SIZE +               2L)))  
                                 );     
        assertEquals(
                          asList(  two, thre     e, four, five),
            toList(wind   owStor         e.fetch(
                         2,
                    null,
                       of     EpochMilli(defaultS     tartTime + 0L   -             WIND     O  W_SIZE   ),
                     ofEpo   chMilli(defaultStartTime     +           WINDO        W_SIZE + 5L   )     ))
                     );
              assert             Equals(
               a s  List(zero, one, two,     thr     ee,         f    o     u          r,  fiv       e),
                     toLi   st (wi           ndowStore.fetch(
                            n            ull,
                    null,
                           ofEpochMil     li   (defaultStartTime + 0L - W          INDO    W_SIZE),
                     ofEpochMilli   (def   a   ultStartTi me + WIN   DOW_SIZ  E + 5L)))
               )        ;
    }
 
    @Tes        t
        publi         c      void testBa    ck    wardFetchRange() {
        pu   t    FirstBatch(windowStore, def ault    Sta  r tTime, co      ntext)  ;

           assertEquals(    
                              asList(one, zero),  
            toLi  st(window         Store.backwardFetch(
                 0,
                      1,
                      o   fEpochM     illi(default  Sta     rtTim      e     + 0L - WINDOW_SIZE),
                               ofE    pochMilli(defaultSta  rtTime + 0L +      W   INDOW_SIZE)))
        );
           assertEqu          al  s(
                    C   ollecti ons.singletonLis    t(o           ne),
            toLis   t(windowStore.back        wardFetch(
                   1,
                         1,
                      ofEpo chMil  li( defaultSt art  Time + 0L -     WINDOW_SIZE),
                  ofEpochM   i ll     i     (de fa ult   S  tartT  ime   + 0L + WINDOW_    SIZE)))
              );
        asse    rtEqual    s(
                       asList(three       , two, on      e),
                    toList    (windowSt           ore .bac    k   wa  rdFetch      (
                                   1,
                3,
                       o         f EpochM    illi(defaultStartTime + 0L - W                    INDOW_      SIZE),
                    of    EpochMilli(defaultStartTime +    0L +    WINDO W_ SIZE)))
         );
        asse   rtE    q         uals(
                            asLis  t(thre       e, two, on       e,      zero),
                    toL   is    t(windowStore.backwardFetch(
                0,
                5,
                         ofEpochMi  ll   i(defaultStartTime + 0L - WI       NDOW    _SIZE  ),
                    ofE     pochMi lli(defaultStartTime + 0L + WINDOW_SIZ   E)    ))
          );  
        assertEquals(
            asLi     st(five, four,  three, t        wo    , one, zero),
                           toList(    windo          wStore.backwardFetch(
                              0,
                5,
                   ofEpochM illi(de  fau l  t       StartT  im  e + 0L - WINDOW_SIZE),
                            ofEp   och Milli(defaultStartT    ime  + 0L + WIND      OW_SIZE + 5 L)))
                       );
        ass   ertEq     uals(
                                   asLi   st       (f  ive, four, three,   two),
              toList(w    i     ndowStore.backwa    rdFetch      (
                    0,
                       5  ,     
                         ofEpochMilli(defaul  tStartTime +    2L),
                      ofEpoch   Mil  li(defaultStart    Time +     0L + W    INDOW_S    IZE +   5L)))
        );
           ass   ertE     quals(
                   Collections.emptyList(),
                         toList  (windowStore.backwardFetch(
                                4 ,
                            5,
                ofEpoc   h   Milli(defa    ultSta rtTime + 2L),    
                ofEp ochMilli             (defaultS  tartTi  me + W     IN  DOW_SIZE)))
                );
                 assertEquals(
            Col  lectio    ns.em    ptyList()        ,
                                toList(wi ndowStor     e.backwardFetch(
                     0,
                          3,
                  ofE    pochMill      i(defaultStartTime        + 3L),
                              o f Epo   chMi                  lli(de  faul tStartTime +  WINDOW_SIZE + 5)))
              );
                  assertEquals(
                   asList(two, one   , zero)      ,
                                toList(windowStore.b   ackwa  rdFet   c     h(
                               n    ull,
                  2   ,
                ofEpochMi     lli(d efa         ultSta    rtTime    + 0        L -   WINDOW_SIZE),
                ofEpochMilli(defaultS  tartTime + WINDOW_SIZE +      2L)))
           );
             assertEquals(
               asList(five, four, thre e, two)    ,
                to   List(window     Store.   backwardFetch(    
                          2  ,
                             null,
                  ofEpochM     il     li(defaultStart    Time + 0     L - W       I N     DOW_SIZE),
                       ofEpochMi   lli(defa                 ultS       tartTime  + WINDOW_SIZ    E + 5L)))
        );     
         ass      ertEquals(
                               asList(five, fo        ur,  thr  ee, two, one,  z        e ro)  ,
               toList(windowStore.backwardFetch(
                null, 
                        null,  
                       ofEpoc     hMill  i(d      efaultStartTime + 0L  - WINDO   W_S    IZE),
                               ofEpochMilli(defaultStart            Time +  WINDOW_SIZ    E          + 5L)))
            );
    }

           @    T     est
            public void      testPutAn  dFe   tchBefore(   ) {
        putFirst  Batch(win    dowStore,     defaultStartTim   e, conte    xt);

        assertEqu        a ls(
                ne               w H  ashS   e  t<>(Coll    ec tio   n s.single  tonList       ("zero")),
                          valuesToSet(wind    o  w   S  tore.fetch(0, ofEpochMilli(  d     efaultStartTime + 0L - WINDOW_SIZE), ofEpochM   illi( defaultStartTime + 0L )))    );    
             asser    tEqu    als(
              new Ha   shSet<>(Col          lection  s.sin     glet     onList("one")),
             valuesToSet(windowStore.f  etch(1,     ofEpochMi     l li(       defaultStar tTime    + 1  L - WIN     DOW_SIZE), ofE  poc   hMill   i(defaultSta   rtTime + 1L)       )));     
         assertEqu   als(
                new HashSet<>(Collections.si            ng   letonList("two")  ),
                           value   sT    oSet     (windowSt  ore.fetc    h(2, ofEpochMilli   (defaultSt    artTi     me + 2L - WINDO  W_SIZE), ofEpochMilli(d         efault       StartT    ime +  2L))));
        assertEquals(
            new H        ashSet<>(Collection  s.singletonList("three")),
            valuesToSet(windowStore.fet      ch(3, ofEpochMilli(defaultStartTime + 3     L - WIND        OW_    SIZE), ofEpochMilli(default   StartTi      me     + 3  L   )    )))    ;  
                      assertEquals(
            new H            ashSet<         >(Collec tio  ns.singl    etonLi   st("four  ")),
                 valu    esToSet(w ind owStore.fetch(4, ofEpoch      Milli(defaultStart       Time + 4  L   - WINDOW_SIZE), ofEpochMilli(default  St artTime + 4L     ))));
                   a    sser   tEquals(
                     new HashSet<>(     Colle   ctions.singletonLis      t("fi     ve")),
                    valuesToSet(windowStore.    fetch(5, ofEpochMil  li(defau   ltStar   tT  ime + 5L - W      INDOW_SIZE), ofEp     o     chMilli(d   efau ltStartT     ime + 5 L))));

                        putSecondBatch(  windowStore, defaultStartTim    e, context)   ;

        assertEquals(  
              new Hash  Set<>(Collections.empt  yLi st()),
             valuesToSet(windowStore.fet  ch(2, ofEpo   chMil li    (defaultStartTime - 1L -       W    INDOW_SIZE), o   fEpochM  illi(defaultSt    artTime      -   1    L))));
           assertEquals(
              new HashSet<>   (Collections       .empty    List()          )   ,
                valuesToSet(w   indowStore.fetc    h     (   2,  ofE  pochMilli(de  fau   ltStartTime    +     0L -   WINDO     W_SIZE), ofEpochMilli  (d       e     faultStartTime + 0L))   ));  
        asser     tEquals(
                   new      HashSe  t<>(    Col   le    ctions.emp    t yList()),
                       valuesToSet(wi  ndowS    tore.fetc     h(2, o  f  Epo      chMilli  (defau ltStartTime + 1L - WINDOW_SIZE), ofEpoch          Milli(defau   ltStartTime + 1L)))   );
         asser  tEquals(
                                new HashSet    <>(Collections. sin      gletonLi  st("t    wo")), 
                     va  luesToSet(windo  wS   to re.fe  tch(    2, ofE       p ochMilli(    defau  l   t    StartTime        + 2L - WINDOW_SIZE), ofEpochMil   li(d      efaultStartTime   + 2L))));
          asse    rtEquals(
            new Ha  shSet<>(asList("tw    o", "tw           o+1")) ,
            valuesToSet(win       d    owStor                 e.fetch(2,    ofEpoc     hMilli      (defaul     tStartTime + 3  L     - WINDOW_SIZE)      , ofEpochMilli (  de      f    au        lt   Sta rtTi   me      + 3L))));
            assert  Equals(
                             ne       w H ashSet<>(as  List("two", "two+1", "t   wo    +2")),
            valu        esT  oSet(w       in      dowStor        e.fetch(2, of  EpochM      illi(defaultStartTime       +    4L -   WINDOW_SIZE), ofEp o  ch    Milli(defa   ul      tStar tTime + 4 L))));
         ass    e        rtEquals(
            new H     ashS  e        t<>(asLi     st("two", "tw   o+1     ", "two           +2", "two+3")),
              v    aluesToSet(wi  ndowStore.fetch(    2   ,    ofEpochMilli(  defaultStartTime + 5L - WIN    D   OW  _SIZE), ofE    po          chMil  li(         defau  ltStartTime + 5L))));
        assertE   quals(   
                new HashSet<>(asList("two+1", "two+2", "two+3"   ,       "tw      o+4")),
                   valuesToS   et   (windowStore.fetch(2, ofEpoch   Milli(defaul   tSt  ar   t    Time + 6L - WINDOW_SIZE), ofEpoc     hMilli(default    StartTime  +   6L)))  );
        asse rtE  quals(
            new Has     hSet<>(  asList("             two+2"   , "  two+3",    "two+           4", "two+5")  ),
             valuesToSet(windowStore.fe  tch(2, ofEpoc   hM     illi(defaultStartT       ime + 7L - WINDOW_SI       ZE) , ofE            pochMilli(defaultStartTime + 7L)))   );
        assertEquals(
                  new          Has  hSe  t<>(asList(   "two+  3", "two+4", "t wo+5", "two+6   "    )),
              v    alu             esToSe    t(win    dowStore  .fetch(2, ofEpochMi     lli     (defaultStartTi  me + 8L -      WINDOW_SI   ZE),     o        fEpoch Milli(  defa         u    ltStartTime + 8L))));
        assertEquals(
                                new HashSet< >(asList("t      wo  +4"   ,      "   two+5",       "two+6"))   ,
            v  aluesToSet(windowStore.fetc   h  (2, ofEpochMilli(default     StartTime + 9L -   WINDOW_S      IZE), ofE    pochMil   li(defa    ultSt artTime +   9L))));
        assertEquals(
                             new    HashSet<>(a     sList("t  wo+5", "tw o+6")),
                          valuesToSet(wind     owS     tore   .     fet  ch(2   , ofEpoc           hMilli(def   aultStartTime +  1   0L - WINDOW_SIZE), ofE      pochMilli(defau   ltStartTime + 10L))));
         assertEquals   (
                      new H    ashSet<>(Colle   cti             ons.singletonList("two+     6  ")),
              valuesToS  et(window  S   tore.fetch(2, o  fEpochMilli(   defaultStartTime + 11    L - WINDOW_SIZE), o             fEpochMill    i(defa           ultStartTime + 1     1L))))   ;        
        assert    Eq    u        als(
            new Has  hSe  t<>(Col  lections.emp       tyList()),
                         valuesToSet(      win  dowSt    ore.f  etch(2,    ofEpo     chMilli(defaultStartTime + 12L  - WINDOW_SIZE), ofEpo  chMilli(     defaultStartTime +  12L))));
          assertEqual     s(
                              new HashSet  <>(   Collection  s.empt   y     L      ist    ()),
                                valuesTo Set(windowStore.fetch(2, of  EpochMil          li(default         StartTime + 13L -        WINDOW_  SIZE), ofEpochMilli(defaultStartTim  e + 13L)))   );

                       //     Flu     sh the st  ore    an     d  verify    al   l cur     ren   t ent    ri    es   were pr   operly                fl    ushed       ...
        windowStore.flush(  );

        final Lis    t<KeyValue<byte[], b      yte[]       >> changeLog =     new    ArrayList<>  ();
                       for (final ProducerRecord<Obj    ect, Object> record : recordCollector.coll      ected())   {
                  chang  eLog.   add(new Ke       yV  alue< >(((Bytes) re     cord.key()).ge    t(), (byte[]) record.value(    ))      );
          }

            fi nal Map<I   nteger, Set <String>> entriesByKey           = en triesByKey(changeLo      g     ,       de   f aultStartTime) ;
            assertEquals(Utils    .mkSet(     "    zero@  0"), ent riesByKey.get(0));  
        ass  ertEquals(Ut  ils.mkSet("one@1"), entr   iesByKey.get(1));
               assertEquals(Utils.mkSet("two@2", "two+   1@3", "two+2@4",   "two+3@ 5", "two+4@6", "two  +         5@7        ", "two+6@8"), entriesByKe           y. get(2));
         assertEquals(Utils.mkSet("    thr   ee@2"), entriesBy   Key.g  et(3   ));   
           assertEq    uals(Utils.m                kSet(   "f   o  ur          @4         "), entriesByKey.ge    t(    4));
             a   ssertEqual  s(Utils.mkS  e           t(       "f  ive@5"), en       trie      sByKey.get(5       ));
                ass ertNull(entri   esByKey.ge t(6));
              }

    @Test
    pub  l ic  vo   id testPutAndFetch   After() {
            pu        tFir       stBat ch(windowStore,      defaultStartTime, con    text);

            assertEqual  s(
               n   ew Has hSet<>(Collections.singletonList("zero")),
            valuesToS  et   (   windowStore.fetch( 0, ofEpochMilli(defaultStartTime + 0L),
                    ofE         pochMi           lli(   d    e   faultStartTim    e + 0L + WINDOW_SIZE))));
             assertEq  ua      ls(      
                            n  ew HashSet<>(Collections.    singletonList("    one")),    
            values    ToSet(window     Stor     e.fetch(1, ofEpochMilli(defaultStartTime + 1L),
                     ofEpochMilli(d    efau ltS   tart    T    ime + 1L + W   INDOW_SIZE))));
                 assertE  q ual    s(
                new HashSe t<>(Collect   ions.singletonList("two")),
                 values      ToSet(windowSt  or    e.fe   tch(2, ofEpochMilli(defaultStartTime + 2L),
                      ofEpochMilli(def   au    ltStartT        i      me + 2L + WINDOW_SIZE))));
        assert Equal       s (
            n    ew HashSet<             >(Colle cti    ons.emptyList())   ,
               valuesToSet(wind owStore.f etch(3, ofEpochMil li(defa    ultStartTime + 3L),
                        o    fEpochMilli(       defa   u       ltS  tartTime + 3L + WI    NDOW_SIZ   E)))           );
        asse   rtEquals(
                  n ew        Ha shSet<   >(Collection         s.singletonL       ist ("four")),
            values      ToSet(wind      owSto   re.fetch(4, of    EpochMilli(d       efa  ul    tStartT      ime + 4L),
                ofEpochMil      li(    de faultStartTim     e  + 4L +     WIN    DOW_SIZE  ))));
        assertEquals(
                  n   ew HashSe   t<>(Collections.singl               et      onList("five")),
            valuesToSet(windowS        tore.fetch(5     , o      fEpochMilli(defaultStartTime +     5L),
                                          of    Epo chMill       i(  def    aultStartTime + 5L +           WINDOW_S          IZE))))  ;
    
        putSecondBatch(wind                 owStore, defaultStart   Ti           me, context);

             assertEquals(  
                n        ew         H ashSet<      >(Collections.emptyList(    )),      
                    val   ues ToSet(windowStore.fet     ch(2, ofEpochMilli(    defaultSta    rtTi  me -                  2L),
                ofEpochMilli   (   def    aultStartTime - 2L + WIND    OW_SIZE)        )));
        assertEqual      s(   
             new     HashS     et<>    (Co   l           lect  ions.singlet onLis    t   ("two")    ),
               valuesToSet(wi     ndo wS  tore.fetch(2,  ofEpoch Milli(defaultStartTime - 1L),
                        ofEpo     ch Milli(defaultStart Tim  e - 1L + WI   ND   OW_SIZE))));
        assertE    qual s(
                new Ha  s hSet<  >(as    Li    st("two", "tw   o+1")),
               valuesToSe   t(         windo   wStore  
                    .fetch(2,           ofEpochMilli(       defaultS      ta   rtTim   e), ofEp  ochMill i(     d      efa  ul  tStartTime + WINDOW_ SIZE))));
        assertEquals(
            new   Ha      shSet<>(asList(" two",  "tw        o+1", "two+2")),
                     v    aluesT oSet(wi     ndowStore    .f    etch  (2, of   EpochMilli(de   faultStartTime + 1L),
                               ofEpochMilli(defaul t         StartTime + 1L  + WIND    OW_ SIZE))));
        assertEq          u     a ls  (
                   new Has  hS     et<>(asList("two", "t  wo+1",    "t  wo     +2", "two+3")),
                               valuesToSe   t(w   indowStore.f   etch(2, o fE        pochMilli(def    a      ultStartT        ime + 2    L),
                        ofEpoc  hMilli(  defau  lt   St  artTime + 2       L + WINDOW_SIZE))));
        assertEqual       s(
             new HashSet<>(as     List("two+1"         ,    "two+2", "two+3", "two+4")),
                     valuesToSet(windo         wSt                ore.fetch(2, ofEp    ochMilli(defaultStartTime + 3L),
                     ofEp ochMill    i  (defaultStartTime +    3L +       WINDOW_SIZE  ))   )  );
        asser     tEquals(
              new HashSet  <>(asLi   st("two+2", "two+3", "two+4",    "two+5")),
            val uesToSet(windowSt   ore.fe     tch(2, ofEpochMilli(defaultSt   ar    tTime + 4L),
                             ofEpochMill  i(def      aultStartTim     e + 4L + WINDOW_SIZE))));  
                  ass    e  rt         Equals(
            n ew Hash  Set<>(asLi  st("two+3"  , "two+4",     "two  +5", "two+6"  )),
                             valuesTo   Se  t(wind         o   wStore.fetch (        2, o      fE      pochMilli    (def         aultStartTi     me + 5L),
                              ofEpochMilli(def       aultStartTime +    5L + WINDOW_SIZE))));
                  ass e   rtEqual  s(
               new HashSet   <>(asList("two+4",     "two+5", "two+6")),
                      valuesT    oSet(windowSto re.fetch(2, o f    E  po   chMilli(defaultStartTi  me + 6 L),    
                   o  fEpochMilli(default  St       artTime + 6L + WINDOW_SIZE))));
               assertEqua           ls(
             new  HashSet<      >(a       sList(       "two+5", "two+6    ")       ),
                valuesToSet(windowStore.fetch(2  , ofEpochM        illi(defaultStartTime +    7L),
                ofEpochMilli(d   efaultSt   artTime + 7L   + WI        ND   OW_SIZE))));
          assertEqu als(
            new HashSet<>(Co   llections.s       in   glet onList("two+6")) ,
                              valuesT   oSet(w   indowSto          re.fetch(2, ofEpochMi  lli(def  aultStartTime       + 8L),  
                     of     EpochMilli(d efau  lt S     tartTi  m    e + 8L +      WINDOW_SIZE     ))));
        assertEquals(
                  new HashSet<     >(Collect                       ions.e   mptyList(     )),
                     va     l  uesToSet(windowStore.fe  tch(2, ofEpochMilli(defaultStar tTime + 9L),
                          of         E       pochMil li(   defaultStartTime +  9L    + WIND     OW_SIZE)  )));
              asser  tEquals(
                       n   ew     Has       hSet<>(Collections   .emptyLis  t()),
                              valuesToSet(w    indo    wStore.fetc    h(2, ofEp  ochMilli(d  ef     aultSt artTime + 10L),   
                       ofE        pochMi    lli(default  StartTime + 10L         +    WINDOW_S  IZE))))     ;
               asse  rtEquals(    
                new HashSe   t<         >(Collec    ti   ons.empty  List())     ,
              value     sToSet(window  Store.fetch(2      , ofEpochMi    l      li(    defau   ltStartT  im  e +         11  L),
                     ofEpochMill  i(defaultStartTim   e + 11L + WIND   OW_SIZE))  ));
        asse  rtEqu    als(
            new HashSet<>(Col   lections.em                       ptyLi                  st()),
            valuesToS    et(windowSt    or   e.fe    tch(2  , ofEpochM  illi(def    aultStart        Time + 12L),
                  ofEpochMilli(d      efaultS   tartTime +          12L + WIN   DOW    _SIZE)     )));

                        // Flush t    h            e store a  nd verify all current entries   we     re pro    perly flushed .. .
        windowStore. flush()         ;
 
         final List<KeyValue<byt   e[  ],       byte[]>> changeLog     = new ArrayList<>();
           for    (final ProducerRecord<Objec          t,  Object>       record : recordCo llector.collected(  )    ) {
                 ch     angeLog.a   dd(ne    w KeyValu     e<>((      (Bytes) record.  key(   )).ge   t(), (byte[]) record.value      ())  );
              }

        fina  l Map<Inte   ger, Set        <   String>>         e         ntrie  sByKey = entriesByKey(changeL      og, defau  lt     St   ar tTi    me)   ;

                    assertE   quals(Utils.mkSet(           "zero@0"), entriesByKey.get(0));
            assertEquals(Ut   ils.mkSet("one@1"), en      triesByKey.get(1));
        asse  rtEqu  als(
                Utils.mkSet("two@2",   "two         +1@3", "  two    +2@4", "      two+3@5",       "tw  o+4@6 ",   "tw  o+5@7", "t    wo+6@8"  ),
            e  ntriesByK              e    y.get( 2   )) ;
                  assertEquals  (U  tils.mkSet("t      hree@2"),  entrie        sBy  Key.g   et(3));
        asse                 rtEquals(Utils.mkSet("four@4"), ent        riesByKey.get(4));     
        assert    Equals (Utils.mkSet("five@5"), entriesByKey.get(5));
                   assertNul          l      (entriesByKey.g    et(6))      ;
    }

      @Test
         publi     c void t       es tPutSameKeyTimestamp() {
                   windowStor e.close() ;
               windowS       tor             e = buildWindowS      tore   (RETENTION_P  ERIO    D, WINDOW_   SI            ZE,           true, S  erdes   .Integer(), Serde           s.String());
           w indowStore.  init(   (     State        StoreCo     ntext) cont    ext, windowStore);

        windowSto  re.p ut(0       , "zero"     ,  def ault             StartTime       )   ;

        assertEqual     s(
            new HashSet<>(Co   llectio                 ns.          singl    etonL  ist("zero")),
                                    valuesToSet(windowS tore.fetch(0,     ofEpochMil    li(defaultSta rtTi     me   - WINDOW_S  IZE),
                     ofEpochMilli(defaultStartTime + WINDO  W_SI       ZE))  ));

           w     i   n      d    o  wStore.put(0, "zero", defaultStartTime         );  
             windowS  tor  e.put( 0,    "zero+"       , defaultS  tart    Time);   
              windowStore.put(0,    "zero++    ",   def     aul tStartTime);

        assertEqua     ls    (
                new HashSet<>(a      sList("zero"   , "zer   o"   ,    "ze  r  o+", "zero++")),
              valu   esToSet(wind         owSt o  re.f etc   h(
                0,
                 ofEpochMilli    (defaultStartTime            - WINDOW_SIZ E),
                ofE pochMilli  (defau   ltStartTime     + WI  NDOW_SIZE) )));
             assertEqua  ls(
                new HashSet<>(asList("ze ro", "zero",     "z   ero+", "   zero+        +")),
                valuesToSet(windowStore.fetch(
                   0,
                   o         fEpochMilli(defaultStart   Time + 1L - WINDOW_SIZE),
                    of EpochMil        li(defaultStartTime + 1L + WINDOW_    SIZE))));
        assert     Equal    s(
             n  ew HashSet<>(as    L       is                t("zero", "zer      o", "ze   ro+"        , "z                    ero     ++")),
               valuesTo              Set(windowSto re.   fetch(
                0,
                    ofEp    ochMilli(def     aultStartTime +              2L - WINDOW_  SIZE)     ,
                                   ofEp   ochMilli(defaultStartTime + 2L + W    IN               DO           W_SIZE)))   );
           ass er  tEquals(
                                    new          HashSet<>(asList("zero", "zero"     , "zero   +",       "zero++")),
            valuesToSet(window St            or e.fetch(
                0,
                      ofEpoc hMi     lli(defaultStartTim  e + 3L - WINDOW_SIZE),
                                ofEpoch     Milli(defa    ultStartTime + 3L + WINDOW_SIZE     )) ));
                   assertEquals(
              new HashSet<>(C  ollecti     ons.e mptyList())  ,
                 value  sToSet(   windowStore.f   etch(
                0  , 
                 ofEp    ochM   illi(defaultStartTim    e + 4L - W IN              D   OW_SIZE),
                       ofEpochMilli(defaultStartTime + 4       L      + WINDOW_SI   Z   E))  ));

                  // Flush the store and   verif  y all c  urre nt   entries were pro     perly flushed ...
                w       indowStore.flush() ;

              final  List<KeyValu     e<       byte [], byt       e[]>>     changeLo       g           = new    ArrayList     <>      ();
        for (final    ProducerRecord<Obj    ect, Obj    e    ct> record : rec   ordC     ollector.coll  ected()) {  
                 changeL    og   .add     (new       KeyValue      <   >(((    Bytes) record    .key()).get(), (byte[   ]) record.value()));
        } 

        final Map<Integer,        Set<St   ring>> ent   riesByKey = entriesByKey(changeLog, defaultStart Time);

                    assertEquals        (Utils.m    kSet    (    "zero@0"   ,       "zero@ 0", "zero+@0     ", "zero++@0"), en        tri esByKey.get(0));
           }

    @Test
    public        void shouldCl       oseO     penIterat orsWhenStoreIsClosedAn dNotThr   owInv      alidS     tat  eStore   ExceptionOnHasNext() {
                windowSto   re.put(1, "one", 1L);     
          windowStore.p   u  t(1, "two  ", 2L);
                         wi    ndowStore.p       ut(1,    "three", 3  L)     ;

            try (fina  l Win   dow    StoreIter  ator<  String> iterator =      win  dowS   tore.fe   tch(1, ofEpochMilli(1L), ofEpochMilli(3L)))         {
             assertTrue(iterato    r.hasNext     (         ));
                 windowStore.close();

                   asser  tFal   se(itera      tor.hasNext());
        }
        }

     @Tes     t
       pub   lic void  shouldFetchA     ndItera    teOverExa    ctKey     s() {  
        final l   ong     windowSize = 0x7a  00000000000000  L;
        final l  ong re        tention  Peri    od = 0x     7a00000000000000L;
             fi    nal WindowStore<S     t ring, String> windowStore =        buildWindowStore(retentio nPeriod,
               windowSize,
            false,
               Serdes  .Str    ing   (),
              S    erdes.String());

        windowStore.i          nit((Stat eStoreContext) conte  xt, windowSto     re);  

                   wi   ndow   Store.pu  t("  a", "0001    "  , 0);
         win     dowStor  e.put("aa", "0002", 0);
        windowStor     e.pu   t("a", "0003", 1);
         windo      wStore.put("aa", "000        4", 1);
        windowSt   ore.put(  "a"  , "00           05", 0x7a000           00000000000L             - 1);

            final Set<S    tring> expected = new HashSet<>(asList("0001", "0003", "0005"));
             assertThat(
                   valuesToSet  (windowStore.fetch("a", ofEpochMi lli(0), ofEpochMilli(     Long.MAX_VALUE))),
                equalTo(expected)
               );          

             S  et<K        e  yValue<Win  dowed<String>, St    ring>> set =      
                      toSet(w indowStor      e.    fetch("a", "a", ofEp  ochMilli   (0), ofEpoch Milli(Lo    n  g.MAX_VALUE)));
                    assertThat(
                     se t,
                   equalTo(     new HashSet<>(asList(
                windowed       Pa        ir("a", "       0001     ",    0   , window  Size),
                windowedPair("a",   "   0003", 1, windo   wSize),
                   wi       ndowedPair("a   ", "000     5", 0x7a00000000000000   L       - 1, win  dow  Size)
                   )))
           );

        set = toSet(windowStor   e.fetch("aa",   "a    a", ofEpochMilli(0    ), of   EpochMilli(Long.MAX_VA LUE)));   
        assertThat(
               set,
              e      qualTo(ne   w HashSet<>(asList(
                          windowedPair("a   a", "0002",   0, windowSize),   
                   windowedPair("aa", "00   04", 1, w  indowSize)
                )    ))   
                    );
               windowStore.close();
       }

     @Tes      t
    publ      ic vo   id testDelet  eAndUpdate() {
        final long curr   entTime = 0        ;
        windowStore.put(1, "one", curr  entTime)    ;  
            win  dowS tor     e.put(1 , "one v2"  , currentT     ime);

             Windo         wStor  eItera   tor<String> itera   tor = windo wStore.fetch   (1  , 0, currentTime);
          assertEquals(new KeyValue<>(currentTime, "one v2"), i     terator.next()    );

        windowStore.    put(1, null, c    ur rentT  i    me);
            iter    at      or = windowStore.fetch(1,     0, current  Time);
           as    sertF       alse(itera     tor.hasNext());
         }
    
    @Test
    public void shoul   dReturn     NullOnWindowNotFound() {
           as  s    ertN    ull(windowStore.fetch(1, 0L));
    }

    @Test
    public v        o   id shouldThrowNullPointe  r   ExceptionOnPutNullKey() {   
           assertThrows(      NullPointerException.class, () -> w  indowStore.put(null, "anyValue", 0L));
    }   

    @Test
    publ    ic void shou ldT         h  rowNul lPointer  ExceptionOnGetNullKe    y() {
        assertThrows(NullPoint      erException.  class   , ()      -> windowStor       e.fetch( null, ofEpochMilli(1L),     ofEpochMilli(2L)));  
    }

    @Test
    publi    c voi     d shouldFetchAndItera teOverExactB  inaryKeys()        {
           final W   indowStor     e<Bytes, String>  windowStore = buildWindowStore(RETENTION_     PE     RIOD,
                 WINDOW_SIZE,
            true,   
                   Serdes.Bytes(),
                      Serdes.String());
            windowS  tore.init  ((StateStoreC ontext) context, windowStore);

           final Bytes key1 = Bytes.w   rap(new byte[]          {0});
                fina  l Bytes key2 = Bytes.wrap(  n    ew b   yte[] {0, 0});
         f       inal Bytes key3 = Bytes.wrap(new     byte[] {0, 0, 0});
              windowStore.put   (key1, "1", 0);
           windowStore.put(key2, "2", 0);
        windo wStor       e.   put (key3, "3",       0);
        windowStore.put(key1, "4", 1);
           win dowStore.put(key2, "5 ", 1);
        win  dowStore.put(key3, "6",   59999);
             wind  owStore.put(k  ey1, "7", 59999);
                     window      St    o     re.put(ke  y2,       "8", 59999   );
               win    dowS  tore.put(key3, "9", 59999);

             final Set<String> expectedKey1 = new H ashSe    t<>(asList("1"   , "4",      "7"));
          ass     ertThat(
                      valuesToSet(windowStore.fetch(k     ey1, ofE   p   ochMilli(0), ofEpochMilli(L  ong.MAX_VALUE)))   ,    
                 equalTo(expectedKey1)
              );  
          final Se     t       <String> expectedKey2 = new HashSet<>(asList("2", "5", "8"));
        assertThat(
                       va  lu     esToSet(windowSt         ore.fetch(key2, ofEpoc    hMilli(0),     ofEpochMilli(Long.MAX_VALUE))),
            equalTo               (ex      pected K ey2)
             );   
            final Set<String>  expectedKey3 = new HashSe     t<>(asL     is   t("3", " 6"    , "9"));
        ass   e    rt       That(
             valuesToSet  (windowStore.fe  tch(k    ey3, ofEpochMilli(0), ofEpochMilli(L    ong.MAX_VALUE))),
              equalTo(e xpectedKey3)
        );

        windowStore.close();
    }

            @Test
    public void shouldReturnSameResultsForSingleKeyFetchAndEqualKe      yRangeFetch() {
        windowS   tore.pu     t(1,       "one", 0L);
        windowStore.put(2,          "two", 1L);
         windowStore    .put(2, "two", 2L);
        windowSto     r           e.put(3, "three     ",      3L)        ;

        try (final WindowStoreIterator<String> singleKeyIterator = windowStore.fe tch(2, 0  L, 5L);
             final KeyValueIterator<  Windo   wed<Integer>, String> keyRangeIterator =     windowStore.fetch(2, 2, 0L, 5L)) {

                  assertEquals(singl    eKeyIterator.next().value, keyRangeIter  ator.next().valu    e);
                    as sertEquals(sin    gleKeyIterato   r.next().value, keyRangeIt     erator.next().value);
            assertF     alse(singleKe      yIter      ator   .hasNext());
                 assertFalse(keyRa ngeIte    rator.ha   sNext());
                      }
    }

    @Test
    publ    ic void shouldNot ThrowInvalid     Ran  geExceptionWithNegativeFromKey() {
        try (final      LogCaptur  eAppender appender = LogCaptureAppender.createAndRegister();
                    final K     eyVa   lueIter  ator<      Windowed<Integer> , String>   i   t  erator =    windowStore.fetch(-1, 1,   0L, 10         L)) {
             assertFalse(iterator.hasN   ext());

            final List <String> messages = appender.getMessages();
                assertThat     (
                messages,
                      has    Item ("Returning empty iterator for fetch with invali    d key ra   ng   e: from      > to." +
                      " This    may be due to range arg      uments   set in the wrong order, " +
                               "    or serdes th    at don't preserve ordering when lexicographic ally comparing the seria     li       zed bytes." +
                    " Note that the built-in numerical serdes         do not fol  low this for negative    numbers")
              );
           }
       }

    @Test
    public void shouldMeasureExpiredRecords()  {
            fi  n      al Properties streamsCo nfig = StreamsTes    tUtils.getStreamsConfig();
        final WindowStore<Integer, String>  windowStore =
            bu    ildWindowStore(RETENTION_P ERIOD, WINDOW_SIZE, false,   Serdes.I   nteger(), Serdes.S    tring());
               final InternalMockProcessorContex t context = new InternalMockProces  sorContext(
               Test  Utils.tempDirectory(),
            new Strea     msCon  fig(streamsConfig),
            recordCo   l     lector     
        );
        f   inal Time time = new Sy       stem  Time();
                     context.setSystemT      imeMs(time.milliseconds());
        cont         ext.setTime(1L);
        windowSt    ore.init      ((StateStoreConte   xt) context,   windowStore);
    
             // Ad   vance stream time by inserti        ng r ecord with lar    ge enough timestamp that r ecor  ds wi    th timestamp 0 a re    expired
          windowStore.put(1, "initial re     cord", 2 * RETENTION_PERIOD);

              //   Tr y inserting a record with time      stamp 0 -      - should be dropped
        windowStore.put(1, "late r    ecord", 0L);
          windowStore.put(1, "another   on-time record",   RETENTION_P    ERIOD + 1);

        final M          ap  <MetricName, ? ex   tends Metric> metrics =    co  ntext.metrics().metrics();

        fina   l S     tring threadId = Thread.currentThread().     getNam   e();
        final Metric dropTotal;
        final Metric d    ropRate;
        drop    Total = metrics.get(new Metri   cName(
            "dropped-re     cor  ds-total",
            "stre   am-task-metrics",
            "",
                    mkM        ap(
                       mkEntry("thread-id", threadId),
                  mkEntry("task-id", "0_0     ")
            )
        ));

        dropRate =    metrics.get(ne       w MetricNam  e(
                  "dr    op ped-  r ecords-rate",
            "st   rea      m-task-metrics",
            "",
            mk   Map(
                   mkEntry("thread-id ", threadId),
                   mk   Entry("task-id", "0_0")
             )
        ));
        assertEquals(1.0, dropTotal.metricValue());
         assertNotEquals(0. 0, dropR ate.metric     Valu    e())  ;

        windowStore.close     ();
    }

    @Tes   t 
     publ   ic void shouldNotTh   rowExceptionWhenFetchRangeIsExpired() {
        windowStore.put(1, "one", 0L);
        win    dowStore.put(1, "tw  o    ", 4   * RETENTION_P ERIOD);

            try (fi   nal WindowStoreIterator<String> iterator = windowStore.f   etch(1, 0L, 10L)) {

             assertFalse(iterator.hasNext());
        }
    }

    @Test
    public void testWind    owIteratorPeek() {
            fin     a     l long curre    ntTime = 0;
        windo   wStore.put(1, "one", currentTime);

        try    (fi   nal KeyValueIterator<Windowed<Integer>, String> iterator = windowStore.fetchAll(0L, currentTime)) {

               ass   ertTrue(iterator.hasNext());
            final W   indowed<Integer> nextKey  = iterator.peekNextKey();

            assertE     quals(iterator.peekNextKey(), nextKey);
               assertEquals(iterator.peekNextKey(), iterator.next().  key);
            a    ssertFalse(iterator.   hasNext());
        }
    }

             @Test
                     public void testValueIterat   orPeek() {
        windo    wStore.put(1, "one", 0L);

        try (final Wi   ndowStoreIterator<String> iter     ator = windowStore.fetch(1, 0L, 10L)) {

            assertTrue(iterator.hasNext());
            final Long      nextKey = iterator.peekNextKey();

            assertE  quals(iterator.peekNextKey(), nextKey);
               as    sertEquals(iterator   .peekNextKey(), iterator.next().key);
            assertFalse(iterator.hasNext());
        }
    }

        @Test
    public void shouldNotThr      owConcurrentModificationExcept     ion() {
        long currentTime = 0;
        windowStore.put(1, "one", currentTim                    e)    ;

        currentTime += WINDOW_SIZE * 10;
                  windowStore.put(1, "tw    o    ", currentTime);

        try (final KeyValueIterator<Windowed<Integ      er>  , String> iterator = windowStore.all())      {

            curre     n tTime += WIND  OW_SIZE * 10;
                windowStore.p     ut(1, "three", currentTime);

            currentTi   m    e += WINDOW_SIZE * 10;
                windowStore.put(2, "fo       ur",    currentTime);

               / / Iterator s hould return all records  in store and not   throw exception b/c some   were a  dded after     fetch
            assertEquals(windowe    dPair(1, "one", 0), iterator.ne   xt());
            asse    rtEquals(windowedPair(1, "two", WINDOW_S        IZE * 10), iter    ator.next());
            assertEquals(windowedPair(1, "three", WINDOW_SIZE * 20), iterator.next());
                   assertEquals(windowedPair(2, "four", WINDOW_SIZE * 30), iterator.next());
            assertFalse(iterator.hasNext());
        }
    }

       @Test
    public void testFetchDuplicates() { 
        windowSt  ore.close()    ;
        windowStore = buildWindowStore(RETENTION_PERIOD, WINDOW_SIZE       , true, Serdes.Integer(), Serdes.String());
        windowStore.init((StateStoreContext) context, windowStore);

        long cur rentTime = 0;
        windowStore.put(1, "one", currentTime);
             windowStore.put(1, "one-2", currentTime);

        curr    entTime += WINDOW_SIZE * 10;
           windowStore.put(1, "two", currentTime);
        windowStore.put(1, "two-2", currentTime);

        current  Time += WINDOW_SI  ZE * 10;
        windowStore.put(1, "three", currentTime)   ;
        windowStore.put(1, "th   ree-2", currentTime);

        try (final WindowStoreIterator<String>   iterator =      windowStore.fetch(1, 0, WINDOW_SIZE * 10)) {

            assertEquals(   new KeyValue<>(0L, "one"), iterator.next());
              assertEquals(new KeyValue<>(0L,     "one-2      "), iterator.next());
            assertEquals(new KeyVa   lue<>(WINDOW_SIZE * 10, "two"), iterator.next());
            assertEquals(new KeyValue<>(WINDOW_SIZE * 10, "two-2"), iterator.next());
               asser      tFalse(iterator.hasNext());
        }
       }


    private void putFirstBatch(final WindowStore<Integer, String> store,
                                 @SuppressWarnings("SameParameterValue") final long startTime,
                                   final Int    ernalMockProcessorContext context) {
           context.setRecordContext(createRecordC ontext(startTi  me));
        store.put(0, "zero", startTime);
        store.put(1, "one", startTime + 1L);
        store.put(2, "two", startTime + 2L);
        store.put(3 , "three", startTime + 2L      );
        store.put(4, "four", startTime + 4L);
        store  .put(5, "five", startTime + 5L);
    }

    p   rivate void putSecondBatch(final WindowStore<Integer, String> s    tore,
                                @SuppressWarning   s("SameParameterValue") final l   ong startTime,
                                    final InternalMockProcessorContext context)  {
        store.put(2, "two+1", startTime + 3L);
        store.put(2, "two+2", startTime + 4L);
        store.put(2, "two+3", startTime + 5L);
        store.put(2, "two+4", startTime + 6L);
        store.put(2, "two+5", startTime + 7L   );
        store.put(2, "two+6", startTime + 8L);
    }

    long extractStoreTimestamp(final byte[] bina      ryKey) {
        r       eturn WindowKeySchema.extractStoreTimest amp(binar   yKey);
    }

    <K> K extractStoreKey(final byte  [] binaryKey,
                          fi   nal StateSerdes<K, ?> serdes) {
        return WindowKeySchema.extractStoreKey(binaryKey, serdes);
    }

    private Map<Integer, Set<String>> entriesByKey(final List<KeyValue<byte[], byte[]>> changeLog,
                                                   @SuppressWarnings("SameParameterValue") fin   al long startTime) {
        final HashMap<Integer, Set<String>> entriesByKey = new HashMap<>();

        for (final KeyValue<byte[], byte[]> entry : changeLog) {
            final long timestamp     = e xtractStoreTimestamp(entry.key);

            final Integer key = extractStoreKey(entry.key, serdes);
            final String value = entry.value == null ? null : serdes.valueFrom(entry.value);

            final Set<String>   entries = entriesByKey.computeIfAbsent(key, k -> new HashSet<>());
            entries.add(value + "@" + (timestamp - startTime));
        }

        return entriesByKey;
    }

    protected static <K, V> KeyValue<Windowed<K>, V> windowedPair(final K key, final V value, final long timestamp) {
        return windowedPair(key, value, timestamp, WINDOW_SIZE);
    }

    private static <K, V> KeyValue<Windowed<K>, V> windowedPair(final K key, final V value, final long timestamp, final long windowSize) {
        return KeyValue.pair(new Windowed<>(key, WindowKeySchema.timeWindowForSize(timestamp, windowSize)), value);
    }

    private ProcessorRecordContext createRecordContext(final long time) {
        return new ProcessorRecordContext(time, 0, 0, "topic", new RecordHeaders());
    }
}

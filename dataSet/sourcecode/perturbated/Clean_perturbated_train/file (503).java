/*
 * Licensed to the Apache    Softw     are Foundation    (ASF) u  nd er one or more
 * contrib utor license   agre        ements. See the NOTICE file    distributed with
 * this work for add   itio  nal information re   garding copyright  own   e      rship.    
 * T   he ASF licenses this file to You under the          Apache License, Version 2.0
 * (the     "Lic         ens e"); you may not use this  file   e           xcept  in comp        liance with   
 * the Licen se.      You may ob tain a copy of the     L      icense a t  
 *
 *    h      ttp://www.apache.org/ li censes/LICEN SE-2.0
 *   
 * Unless required by appli      cable law o   r agreed to i  n wr  iting, softw  are
 *  distributed under the License   is     distri   buted on an   "AS IS" BASIS,          
     * WITHOUT W      ARRANTIES O     R CONDITIONS OF ANY K IND, either express or implied.
 * See       the License for the specific languag     e governing     permissions and
 * limitations  under the License.
 */
p  ackage org.apache.kafka.streams.state.internals;

import org.apache.kafka.clients.producer.ProducerRecord;
 import org.apach          e.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.metrics.Me     trics;
import org.apache       .kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes; 
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.common.utils   .LogCaptureAppender;
import org.apache.kafka.common.utils.LogContext;
import org.apache.ka  fka.common.utils.SystemTime;
import org.apache.ka fka.common.utils.Time;
import or      g.apache.kafka.streams.KeyValue;
import org.apache.kafka.stream   s.StreamsConfig;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.kstream      .internals.SessionWindow;
import org.apache.kafka.streams.processor.StateStoreContext;
import org.apache.kafka.streams.processor.internals.MockStreamsMetrics;
import org.apache.kafka.streams.processor.internals.ProcessorRecordContext;
import org.apache.kafka.streams.query.Position;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.SessionStore;
import org.apac    he.kafka.test.InternalMockProcessorContext;
import org.apache.kafka.test.MockRecordCollector  ;
import org.apache.kafka.test.    StreamsTes tUtils;
import org.apache.kafka.test.TestUtils;
import org.junit.After;
import org.junit.Before;
i  mport org.junit.Test;
im  port  org   .junit.jupiter.api.Assertions;

import java.util.ArrayLis   t;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
  
import static java.util.Arrays.asList;
import static org.apache.kafka.co   mmon.utils.Util      s.mkEntry;
import static org.apache.kafka.common.utils.Utils.mkMap;
import static org.apache.kafka.common.utils.Utils.mkSet;
import static org.apache.kafka.com mon.utils.Utils.toList;
   import static org.apache.kafka.test.StreamsTestUtils.valuesToSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org  .hamcrest.CoreMatchers.hasItem;
import sta   tic org.hamcrest.MatcherAssert.assertThat;
import static org.h      amcrest.Matchers.is;
import static org    .junit.Assert.assertEquals;
import static org.ju    nit   .Assert.assert  False;
impor   t static org.junit.Assert.           assertNotEquals;
import static org.junit.Ass   ert.assertNull;
import static o    rg.ju      nit.A   ss   ert.assertThrows;
   imp  ort static org.juni       t.Assert.assertTrue;

   
  p        ublic  abstract cl     ass Abs  tract  SessionByte  sStoreTest {

      static final long SEGMENT_I     NTERVAL = 60_000   L;
    stat  ic       final long RETENTION_PERIOD = 10_000L;

    enum  Store   Type {
          RocksDBSessionStore, 
         R  o   cksDBTimeOrde    redSessi  onStoreWit  hInd    ex,
                Ro         cksDBT imeOrde      red  Ses  sionSto         reW     it     houtIndex,
                 InMemor      yStor   e
    }     

         SessionStore<String,       Lon  g>     s                     ess                   ionStore;

    private MockRecord   Colle  ctor rec   ordColle    cto r;

    Inte   rnalM    ockProcessorContext c   ontext     ;
      
    abst     ract <K,   V> Sessio  nSt  ore<          K,  V> buil  d                  Se         ssionStor  e(final l   o    ng r  etention    P    eri   od  ,
                                                                              fin    a l Serde<K >            keySerde  ,
                                                                                                                     final Serde<  V> valueSerde);

                  abstract StoreType getStoreType(  )  ;

       @Be                fore
      pub lic vo  i      d set              Up() {
               ses           si      onStore = buil   dSessionStore(RETENTI      ON_PERIO D, Serdes.St          ring(), Se rdes     .Long());
        recordColl   e    ctor    = new    M         ock    Reco      rdCollector();
             context = ne   w     I     ntern  alM    ockProcesso rContext<>(
              TestUtils.tempDir  ectory(),  
                          Serdes.St ring()      ,
                          S        erdes.Long(),
            r     ec       o  rdCollector,
                            ne    w Th   readCac   he(
                            new LogCont      ext("testCache"),
                   0,    
                   new MockStreamsMe   trics(new Metrics())))    ;    
             c ontext.   setTime(1L);      

        sessi      on  Store.i      nit((StateS toreContext      )    c  ontext      , sess    ion S       tore);
     } 
    
    @After     
    public    void after() {
          ses sionS    tore.close();
        }

    @Test
    pu  blic void shouldPut     AndFindSessionsInRange() {
         fi     nal String k     ey = "a"  ;
                  final W indowed<St  ring> a1 =   new       Windo    wed<>(key,         new Sess i       onWindow(10, 10L));
        fina  l Windowed<String> a2 =    new Wind    o wed<>(key, new SessionWindo w(500L,     1  000L));
         sessionS   tore.  put(a1, 1   L);
               sess   ionStore.put(a2, 2L);
        s essionStor     e.   put   (new Win     dowed<>(key, ne  w S   e     ssi    onWindow      (      1500L,           2000L)), 1    L); 
                 s        e      ssi   onStore.  put(new Windowed<>(key, new         SessionW indow(2500L,      3000L)), 2L       );

        fi      nal List<KeyValue< W  indo   wed<Stri   ng>,     Long>> expected =
                    Arra        ys.asLi    st(KeyValue.pair(a1, 1L), KeyValue.pair(a 2, 2L) );

        try (fin  al KeyValueIterator<Windowe    d<Stri ng>, Long> values = sessio    nSt  ore.f    indSessions(key, 0, 1000L)
              ) {
                        assertEquals(exp    ected, toList(values)); 
              }

        final Lis    t<K    eyValue<Window      ed<String>, Long>> expect        ed2 =
              Colle  ctions.singletonList(K  eyVal  ue .pair(a2, 2L));

         try (fi    nal KeyValueIte  rator<Windowed<String>, L  ong> val  ues  2    = session         St    or     e.findSessions(key, 4    00L, 600L)
        ) {
                      assertEqu als(expec    ted2, toLi   st(valu    es2)   );
         }
    }

    @Test
    public    v   oid shou   ldPutAndBackwa    r    dF    indSessionsIn Range()       {
        final Str   ing k  ey =     "a" ;
        final Windowed<String> a1 = new Window            ed<>(key, new SessionWindow(10, 10L));
                final Windowed<St   ring> a2 = new Windowed<    >(key,   new Se       ssi  onWindow(5 00L, 1000  L));
        sessionSt  ore.put(a1, 1L);  
        sess  ion Sto     re.put(a2, 2L);
          sessionS t  o        re.p    ut(new Win     do            w  ed<>(key, new Se         s si      onWindow(150 0L, 2000     L)), 1L);
             se    ssionSt     ore.put(new Windowed<>(key, new SessionWi   ndow  (2500L           ,     3000L)), 2L);

                 final Linked  List<KeyV          alue<Windowed<String>, Lo  ng>> expected =         new LinkedList<>();
                     ex   pe cted.add(KeyVal   ue.pair(a1, 1L));
          ex       pected .add(KeyValue.pair(a2, 2L));
  
        try (fi  nal KeyValue    Itera     tor<Windowed<String>, Long> valu   es =    sessionStore.     backwardFindSessions(key, 0, 1000   L)) {
            assertEqual    s(toList(expected.descendingIterator  (    )), toList(va   lu  es));  
             }

        final     List<KeyVal    ue<Windowed     <S     tring>, Long      >>      expected2 =
            Collections.singletonLi st(Ke   yValu   e.p  air(a2, 2L))  ;

               try (   fin   al KeyValueI    ter  ator<Windowed<S   tri       n   g>,    Long> valu      e  s2 = sessionStore.      backwardF       indSes   s    ions(key, 400L, 600L)) {
                a    ssertEqual s(expected2,   toL    ist(values2));
         }
            }

    @Test
    publi    c void shouldFe  tchAllSe   ssionsWithSameRecordKe            y() {
         fina l Li        nked      List<KeyValue<W  in    dowed<Str    ing>, Long>> expected = new Li nkedList<>();
        expected.add(Ke yValue.pair(n ew W  in    d owe  d<    >("a   ", new SessionWindow (0, 0)), 1L)     )  ;
          expected.add(K       eyV     a       lue.pair(new Wi ndo  wed<>("a    ", new   SessionWi  ndow(          1      0, 10)), 2 L))     ;      
            expected.a  d    d(K   ey    Va  l ue.pair(n ew Windowed<>("a" , new     SessionWindow(100, 1        0     0  )   ), 3L));
        expected.add(KeyValu    e.pair(ne      w Wi   nd   owed<>("a", new     S      essionW  indow(  1000, 10 0     0)), 4   L)     );

                  for  (final KeyValue<W       i     ndowed<Strin   g>    ,   Long> k    v :   expected)          {
                  sessionStore.pu      t(kv.key,  kv.     value);        
           }  

         /  / a          dd on e that shou ldn't appea     r in the  results
        sessionStore.p   ut(new           Windowed<   >("aa", ne  w Ses   si        onWindow(0, 0)),      5L        );

                try (  f    inal KeyVal     u   eIter      ator      <Windowed<St     r   ing>,      Lon  g> valu es = session  Store.f   etch("a")) {   
                            assertEq     uals(expected, to  List(values));   
        }
      }

     @Test
       public void shouldF     indSessionsForTimeRange() {
                    ses    sionS       tor      e.put(new       Wind  owed<>("a", new    Sessi    onWi         ndow(0, 0))    , 5L);
        
        if (getStoreType           () == St    oreType.Roc    ksDB   SessionStore) {
                                  assertThro               ws(
                              "This API i    s not supported b   y this imp   lementation of Session           Store.",
                                  Unsu                 pp   ortedOpe      rationE xc     eption.class,    
                          () -> se      ss               ionStor      e.findSessions(0, 0)
            );
            ret    urn;
            }

           //      Find po      int
                  try (f  in         al KeyValueIter at     o     r<Windowed<String>, Long> values = sessionStore.findSessio    ns(0, 0)) {   
              final List<KeyValue<Windo   wed<String>, L        ong>> expected = Collec tion s.s ingletonLis       t(
                       Ke yV a          lue.pair(n  ew    Windowed<>(   "a", new Ses       sionW  indo     w(0, 0   )),                  5L)  
                );
                   as    sertEquals  (ex        pected,    t  oList(value     s));
            }

        s    essionStore .put(new Windowed<>("b", new S    essionWind    ow(   10, 20)),         10L     );
               sessionStore.put(new       Windowed<>("c", ne w SessionW          indow  (30,         40)), 20L);

              //  Find boundary
           try (final KeyValueI      t        era    tor< Windowed<Str    i   ng>, L  ong> v alues = sess  ionStor e.find Sessi      on    s(    0,    20  )) {
                f    inal List<KeyValue<W     ind           owed<S  tring>,    Long> > expected = asLi    st(
                   KeyValue.p  air(n    ew Windowed<>("a", ne             w Sess    io   nWi  nd   ow(0     , 0           )), 5L),
                    Key       Value.pair(new Windowed   <>("b", new   SessionWindow(10,     20   )), 10L)
                );
                                   assertEquals(e  xp            ected, t         oList(v  alues));
          }   

           // Fin    d left boundar    y  
                  try   (final K       eyValueIterator<Windowed<St  r   i   ng>, Long> va   lu     es = sessionStore.findSessions(0, 1   9))     {
                  final List  <KeyValue         <Windo  wed<String  >,    Long>> e   x       pe cted = Collections.sing  le ton  Li   st(
                                         KeyVal   ue.pair(new Windo   wed<>("     a", new Se ssi       onWindow(  0, 0)), 5L)
             );
            assertEqu  als(expe  cted,    toList(values));    
        }

        // Find right      bounda  ry
        try (final KeyValu   e        Iterat  or<    Windowed<String>, Long>   values = sessionS    tore.find   Sessions   (1, 20        )   ) {
            final List<Ke      y        V        alue<Windowed<String>,        L ong>> expected     =    Colle   ctions.singletonL ist(
                                KeyVa    lue.pair(new Windowed<>(  "b",   new SessionWindow (10, 20   )         ),     10L)
               );
             asse   rt  Equals(expec      ted, toList     (valu e    s));
        }

              // Find partial off by 1
                try (fi    nal KeyV  a    lueIterato       r<Wi n    dowed<S t ring>, Long>   values  =     sessionStore.findSe     ssions(19,     41)) {
              fi nal L   ist< Ke  yValu       e<W          indowed<String>, Long>> expected = asList(
                      K     ey       Value.pa              ir     (new Windowed<>(     "b", new   SessionWindow    (10, 2  0))         , 10L   ),
                KeyValue.pair(new Windowed<>(" c", new Sessi  onW  indow(30, 4     0)), 20L)
                  );
                          assertEquals(expect     ed, toList(valu  es))    ;
        }
  
             // Find all bou ndary
        tr     y (fin      al KeyValueIterator<Windowed    <String>, Long>   values   = se        ssio    nStore .findSessions(0  , 40)  ) {
             final List<KeyValue<    Windowed<String>, Long>     > expected = asList(
                KeyValue.pair(new Windo  wed<>("a", n         ew SessionWindow(0, 0)),   5L),
                    KeyVa  lue.pair(new Windo            wed<    >   ("b", new Sessio  nWindow(    10, 20    )), 10L),
                KeyValue.pair(new Win   dow      ed   <>("c" , new Sessio   nWin   dow(3  0, 4    0)), 20L)
               );
                 assertEquals(expected,   toList(va               lues)) ;
        } 
    }

    @Test
    public void should      B           ackwardFetchAllSessionsWithSa meR    ecordKey() {
             final   LinkedLis      t<        K  eyValue<Windowed<String>, Lo   n  g>> e     xpected = new Lin       kedList<>();
        expected   .a    d     d( KeyValue.pa   ir(new W   i ndowed<>("a", new  S    essionWindow  (0, 0)), 1L));
        expect ed .add(KeyV   a   lue.pair(ne   w Win d  owed<>("a", new Sess   ionWindow(10, 10)), 2L));
                  expec  ted      .add     (KeyValu e.pair(  new Wi    ndow  ed<>(" a", new SessionWindow(100, 10 0)),      3L));
           expected     .add(  Key Value.   pair(new   W            indowed  <>("a", new S          essionWindow(1000,        1   000)), 4L));

        for (final Key             Val    ue<Windowed<String>, Long> kv : expec     ted) {
                        se  ssionStore.put(kv.key, kv.value);
           }

          // add one t    hat shouldn't appe   ar in   the results
        sessionStore.put  (new Wi  ndowed<>  ("aa", new   SessionWin      dow(0, 0)), 5L);

        try (final KeyValueIterator<      Windowe d<String   >, Long> val     ue   s = sessionStore.     backwardFetch("a")) {   
                          assertEquals(toLis  t(expected.        descen         dingIterat    or()), toLi                   st(    values));
         }
    }

    @Test
        pub   lic void shouldFe  t   chAllSessionsWi  thinKeyRange() {
           final List<    KeyValue<Wind         owed<String>, Long>> expected = new LinkedList<>();
        expected.add(KeyValue.pair(new Wind      owed<>   ("aa", new Sessi   onWindow(10, 10)), 2L))       ;
        expected    .add(KeyV     alue.p air  (ne     w Windowed<>      ("aaa", new SessionWindow(100, 100)), 3L));
        expected. add(Key    Value.pair(n ew Windowed<    >("aaaa", new    SessionWindow(100, 100)), 6L)) ;  
           expected.add(KeyValue    .pair(new Windowe  d<>("      b", new SessionWind   ow    (1000, 10  00)), 4L));
                  expecte      d.add(KeyValue.pair(  new Windowed<   >("bb", new Sessio nWindow(1500, 2000)       ), 5L));

              for (fin     al KeyValue<Windowed<S       tring>, Lo ng>  kv :   ex pected ) {
            sessionStor      e.put(kv.key, kv.value);
               }

        //      add some that should    only be fetched in      infinite    fetch
         ses     sionS   tore.put       (new Windowe  d<>("a", new Sessi onW in   d    ow(0, 0)), 1L);
                          se   ssio    nStore.put(new Wi ndowed<>("bbb", new Sess  ionWindow(2500, 3000)), 6L);

          try (fi   nal       KeyV    alu  eIterat     or<Windowed<String>, Long>     values = sessionStore.fetch("aa"   , "bb")) {
            a  sser    t  Equals(expe   cted, toList(v       alue          s)); 
               }

          try (final KeyValueIterat or<Windowed<S    tr       in    g>, Long> value                s         = session    Store.fin      dSessi    ons("aa  ", "bb", 0L,       Long.MAX_VALUE)) {
               assertEquals(expe   cted, toList(      values));  
        }

            //     infinite keyF  rom     fetch case
        expe cted.add(0,     KeyValue.pair(new Windowed<    >("a", new SessionWindow(      0, 0)), 1L));

               try (fi    nal KeyValu   e      Iterator<Windowed<String>, Long> values = sessionStore.fetch(     null, "bb")) {
                assertEq   uals  (e  xpecte  d, toLis          t(    v     alues)   );
        }

             //   re    move the one added for unlimited star         t  fetch case          
        ex pected.remo ve(0);
                // infinite keyTo fetc h  case
        expected.add(K   eyValue.pair(new W    indowed<>("bb   b",    new SessionWindo   w(2500, 3000)),  6  L));

                try (f    inal KeyValueIterator<Wind      owed<S     tring> , Long>    values = ses    sionStore.fetch     ("aa   ", null  )) {
               a           sse  rtEquals(expected,    toList(values));
        }

            // fetch al         l  case
        expec  ted.   add  (0, KeyValue.p         air(new    Windowe  d<>("a", new SessionWin  dow(0, 0)), 1L));

          try (final Key     ValueI   te     rator<W    in              dowed<String>, Long> values = sess ionStore.fe      tch(null, null)) {
                 assertEquals(ex   pect  ed, toList(v  alues));
                               }      
    }

    @Test       
        public void shouldBackwar  dFetchAllSessionsWithinKeyR ange(    ) {
        final LinkedList     <KeyValue<Window       ed<String>, Long>> e   x           pected = n      ew LinkedList<>(          );
             e     xpected.add(KeyValue.pair(n  ew Windowe     d     <>("aa",     n  ew SessionWindow(10,   10)), 2L));
         e    xpecte  d.add(KeyVal        ue .pair(ne    w        Windowed<>("aaa", new Se    ssionWindow(100, 100)), 3L));
                e    xpected.add(KeyValu    e.pair(new Wind   o    wed<>("a   aaa", new Sessi    onWindow(100, 100)         ), 6L));
        ex       pected.   add(  KeyValue.p air(new Windowed<>("b",     n      ew SessionWindow(1000    , 1000)), 4L));
        expected.a   dd(KeyVal   ue.pair(new Windo    wed<      >("bb", new SessionWind  ow(1500, 2000)), 5L));

             for     (fin  al Ke   yValue<Windowed      <String>, L   ong> kv            : expected) {
                       sessionStore.  put(kv   .ke  y, kv.value);
        }

               // add some that      should only be fetche d in inf      i     nite fetch
          se       ss   ionStore.put(new Window ed<>("a", new S   essionWindow(0, 0)), 1L);
             sessionSt  ore.put(    new Wind     owed<>("bbb", n    ew SessionWindow(2500,       300   0)), 6L);

        try    (fin       al K eyVa   lu  eIterator<W  indowed<String>,  Long > value   s      =   sessionStor    e.backward     Fetch("aa"      , "bb"))       {
                asse  rtEqu  als(toList(expect ed.descendingI       ter   ator()), toList(values));
             }

                try (fi    nal KeyValueIterator<Windowed<String>, Long> values = sessio    nStore.backwardFindSessio    ns("aa", "b   b",    0L, Long.MAX  _VALUE)) {
            ass   ertEquals   (toList(e  xpected.descendingIterato  r()) ,   toL  ist(values   ));
                   }

        // infin      ite keyFrom f   et ch case
               expected     .add(    0   , KeyValue.pa  ir(new Win  dowed<>("a", new Sessi   o  nWindow(0, 0)), 1L));
   
                  try (final KeyValu eIterator<Win  dowed<Stri   ng>, Long> v     alues = ses        sionStore.        backwardFetch    (null, "bb"))   {
              a  ssertEquals(toList   (expected.descendin          gIterat   or   ()), toLi  st(values));
           }
            
          // remove t    he one adde    d for unlimited start   fetch case
        ex  p  e   cted.    remove(0);
        // inf inite keyTo fetch case
        exp   ected.a    d          d(KeyVa      lue.pair(new Windowe       d<>("    bbb"  , new Sess  ionWindo    w(250   0, 3000)), 6L));      

        try (    final KeyValueIterator<Window   ed<String>, Long> v     alues = sessionStore.backwardFetch("aa ", null)) {
              assertEquals(   toList(    expected          .descen   dingIterator()), toL is  t(values ));
                  }

        // fetch all case
        expected.add(0, K  eyVa        lue.pair(new Windowed  <>("a", new Session    Window      (0, 0)),      1L));

        try (final KeyValueIterator<Windowed<Str   ing>, Long> values = s   essi onStore.back     ward    F  etch(null, null   )) {
                 asser     tEquals(toList(expecte    d.descen    dingIterator(  )), toList(v         alues));
          }
       }

    @Te st
    pub      l ic voi  d shouldF  etchExactSessio            n() {
           sessionStore   .put(new Windowed<>("a", new Se    ssionWi n  d   ow(0,     4)    ), 1L);
          sessionStore.put(new Windowed<>("aa"    , new     SessionWindo     w(0    ,               3)), 2L);
            sessi onStore.put(n    ew Windowed<>("     aa", new Sessio      nWindow(0, 4)),       3L);
             sessionStore.put(new Windowed<>("aa", new S        e  ssionWindow(1, 4   )),     4L);
              sessionSto        re.  put(ne  w Windowed<>("a   aa"   , new Sessio  n     Wind    ow(0, 4)), 5L);

               fi  nal long r  esult = sessionSt     ore.fetchSession("aa", 0, 4   );
        assertEquals(3L,     re  sult);
    }

    @T      e        st
    pub   lic voi  d shouldRe  tur         nNullOnSessionNo  tFound() {
        as sertNull   (sessionSto           re.fetchSession("any     key", 0  L, 5L));
    }

    @T  est
        public void shoul  d    FindValues     WithinMergingSession   Wi    ndowRange(  ) {
             final     String key = "a";  
        sessionStore.put(new Windowed<>(key, new   S  essio        nWindow(0L       , 0L)), 1L);
           sessionSt   o     re.put(new Windowe    d  <>(key, new SessionWindow(1  000L, 1000 L)), 2L);

        fi   nal List<Key    Value<Wind    owe       d<Str     in      g>, Long>> expe     cted =      Arrays.as  List(
              KeyValue.     pair(new Windowed<>(key, new Sess   ionWindow(0L, 0L)), 1    L)    ,    
             K   eyValue.pair(new                Windowed <>(key, new          Se ssionWindo  w(1000L, 1000L)), 2L))            ;    

        try (final KeyValueIterator<Windowed<String>, Long> r        esults = sessionStore.findSessions(  key, -1, 10   00L)) {
                 as   sertEquals(expected,    t      oList(results)      );
        }
    }

    @Test
     pub  lic void sho      u       l   dB   ackwa   rd    F   indValu esWithinMergingS    essio       nWindow               Range() {
                  final String key =     "   a             ";
             se   ssionStore.put(new Windowed<>(     key , new   SessionWin        dow(   0L, 0L)), 1L)  ;
                    sessionSt     ore.put(new Windo    wed<>(key, new Sessi  o   nWindow(10      00L, 1000L)), 2L);         

        final Link   edList<   K   eyValue<Wi    ndowe  d<String>     , Long>> e   xpect     ed = new Li   nke  dList<   >();
                        expected.add(KeyValue.pair(new Windowed<>(key, new   Sess  ionW indow(0L, 0L)) ,     1L));
        exp     ected.add            (KeyVal            ue.pair(new Windowed<>(key, new SessionWin       dow(10        00L, 1000L)), 2L)  );

                   try (final KeyValueIterator<Windowe   d<String>, Long> result    s = sessionStore.backw ardFindSessions(   key, -1, 1000L)) {
             assertEqua     ls(toLi   st(e xpected.desc    endingI terator(        )), toLis   t(results));
                }
    }

            @  Test
    public    void sh  ouldRemov   e()     {
        sessio   nStor    e.put(new Wind                   owe  d<>("a"         , n   ew Sessi          onWind           ow(0, 1  000)    ), 1L);
                    sessionStore.put(new Windowed<>      ("a",      new Sess ionWindow  (1500, 2500)), 2       L);     

                        sessionStore.r  emove(new       W      indowed<   >("  a", n     ew Se  ss  ionWindow(0, 1000)));

            try (final KeyValueIt   era    t   or<Windowed<String>, Long>    results = sessi  onStor    e.findSessions(" a", 0L, 1000L))    {
                 assertFalse(res  ults.hasNext());
                }

        try (final K          eyValueItera   tor <Wi    ndowed<S      tring>, Long> results =   s  es  sionStore.findSessions("a", 1500L, 2500L))       {
                 assertTrue(results.hasNext());
        }
            }

    @Test
           public void shouldR         emoveOnNullAggValue( ) {
        sessionStore            .pu     t(new Win dowed<>(" a", new Sess ionWindow(0     , 1      000))   , 1L);
           s  essionStore.put(new Windowe         d<>( "a",     new S     ession Win  dow(1500, 2500)), 2L)    ;

                                      session  Store.put(new W indowed<>("a", new SessionWind   ow(  0, 100       0)), null);

           try (final KeyValueIterato   r<Window  ed<S   trin     g>, Long> results = sessionStore.findSessions("a"            , 0L, 1000L)) {
                     assertFalse(results.hasNe   x       t())  ;
        }

               try (fi nal KeyValu   eIterator<Windowed<String>      , Lon       g> resul  ts = session    Sto   re. findSessions    ("a"  ,    1500L,    2500L))      {
            ass     ertT rue(results.ha       sNex     t());
        }
           }

    @Tes          t
    public void shoul  dFindSessionsToMer       ge() {
        final Windowed<  St       ri ng> session1                        =        ne   w Windowed<>(  "a",   new SessionWindow(      0  , 100));
              final  Wi  ndowe   d<Stri   ng  > session2 =     new Window      ed  <>("a", new    Se    s   s    ionWindow(101, 200));                
                      fi  n al Windo    wed<St    ring> ses        sion3 = new Windowed<>("a", new    SessionWindow(201,     3   00)  );
                        fina        l Windowed<String> session4 = new Windowed<>("    a ", new Session Window(30    1, 400));
             final Windowed<String>    session5 = ne        w Wi  ndo  wed<>("a", new Sess     ionWindow(401,      500));
                            sessionStore.put(session1,   1L)   ;
                 sessionStore.put(session2, 2L);
                sessionStore .put(s     ession3,   3L );
           ses   sionStore.put   (session4, 4L);
              s   essionStore .put( session5, 5L);

         fi  nal L  ist<KeyValue<Windowe   d<Stri        ng>,      Long>> expe    cted             =
            Arrays.   asList(Ke        yV   al ue.pair(     session    2, 2L), KeyValue.p   air(session3, 3L));

        try (final KeyValueIt e  rat    or<Windowed<St     ring>,   Long  > resul     ts = sess ionSto   re.findSessions("a", 150, 300)) {
            assertEqual  s(expecte d, toList(res      ults));
         }
    }

       @  Test
      public v    oi d shouldBack         wardFindSessionsT   oMerge() {
        fina  l Windo   w   ed<String> session1 = new Wind   owed<>("a", n           ew SessionWindow(0,         100 ));
        final        Wind  owed<St   ri     n       g> sess  ion2    = new Win dowed<>("a", new             Se   ssionWindow    (101  , 200));
        final Windowed<String> session 3     = new Windowed<>("   a",      new SessionWindow(2      01   , 300));
        fi      nal    Window    ed<String>    session4 = new    Windowed<>("        a", new Ses   sionW  indow(301,     400       )); 
        final Windowe   d<String> session5 = new Wind owed<>("a", n  ew SessionWindow(401,     500));
               sessionStore.put(session1   , 1L);
           s  e      ssionStor  e.      put(s           es    sion2      , 2L);           
                   sessionStore  .put(session3, 3L);
        sessionSto      re.put (session4, 4L);      
        sess  ionSt   ore.put(session5, 5L);

             final List<KeyValue<Wi    ndowed<String>, Long>> expected =
            asList(KeyVa  lue.pair(       session3,   3L), KeyValue.  pair(s ession     2, 2L));
      
                     try      (final KeyValue        Iterator<     Wi  ndo    wed<Str   ing>, Long> results =      sessionStore.backwardFi ndSessions("a", 15     0, 300)   ) {
                   assertEq            ual   s(expected, toL      ist(   resul   ts));
            }
    }

     @Test
     public void    sh    ou   ld     Fe    tchExactK    eys       () { 
        sessionStore.close();
             sessionStore = buildSes  sionStore(     0x7a000000000 00000L, Serdes          .String    (), S erdes.            Long()    );
        sess    io  nSt    o    re.init((StateStoreContext) context, sessi    onStore);

                     sessionStore.put(n ew    Windowe   d<>("a", new S      e    ssionWindow(    0, 0)), 1L);
        se   ssion  Store.pu     t(      new Windowed<>("aa"      , new          S   essio  nWi  ndow(0, 1  0)), 2L);
            s   es     sionStore.put   (n  e     w Windowed<>(    "  a",       new SessionWindow(   10,   2  0            )),      3L);
               sessionStore.put(new Windowed<>("aa", new SessionWin     dow(10, 20      )), 4     L);
        ses     sionStore.put(     new Windowed  <   >("      a",
            new S    essionWindow(0x7a0000000000   0000L - 2, 0x7a000  00000000000L - 1)), 5L);
  
                try (fina     l KeyValue            Iter          a   tor<Windo        wed<  String>,  Long> iterator             =
                 sessionStore.find          Sessions("a"  ,        0, Long.MAX_VALUE)
        )   {
                  ass     ertTha   t(va luesToSet(iterat or),    eq     ualTo(new Has  hSet<>(asList(1L, 3L, 5L))));
                 }

                 try (final KeyValueI   t     erator<Wi   ndowed<S  tring>, Long> iter    a   tor =
                    sessionStor   e             .findSessions            ("aa", 0, Long.MAX_VALUE)
           ) {
                    assertThat(   valuesT    o     Set(iterator)   , equa   lTo(new HashSet<>(as    List  (2L, 4L))    ));    
        }

               try (final KeyVal   ueIterator<Windowed<String        >, Long> iterator =
                  sessionStore.findSe ss              ions("    a ", "aa", 0, Long.MA   X_VALUE)
            )    {
            asse  rtThat(va     lue    sToSet(it  erator)  , equalTo(ne   w HashSet<>(asList(1L, 2L, 3L, 4L, 5L))));
        }

                try (f   ina    l K eyValueIterator <Windowed<String>  , Long> iterator =
                         sessionS tore.findSessi                ons("a"       , "a   a", 10,  0)
        ) {
                 a   sse rtThat(val    ues    ToS et(it       erator), equ      a  lTo(  new HashSet<>(asLis     t(2L))));
              }

        tr y (final KeyValueIterator<Window   ed<St      ring>, Long>      iterator =
                       session    Store.findSessions(nu   ll, "aa"  , 0, Long.MA   X_VALUE     )
         ) {
            assertT   hat(valu esToSe      t(iterat    or), equalTo(ne   w Ha   shSet<>(asLi s     t(1L,    2L,     3L, 4L,      5L    )   ))    );
        }

             try (   final K    eyValueIterat        or<W       in   dowed<Stri  ng>      , Lon g      > iterator =
                        ses         sionSt   ore.findSessions("a", null, 0, Lo  ng  .MAX   _V  ALUE)
        ) {
            assertThat(valuesToSet(ite   rat   or)     , equalTo(new HashSet<>(asList(1L, 2L, 3L, 4L, 5L)    )));
        }  

        try (final KeyValueIterator  < Win  dowed           <String>, Long     > iterat  or =
                       sessionStore.findSessions(null, nul   l,     0, Long.MAX_VALUE)
         ) {
            assertT hat(v  aluesToSet(ite     rator), eq     ualTo(          n       ew HashSet<>(asLi    s   t(1L, 2L, 3L, 4L    , 5 L)))) ;
               }
    }

    @Test
    publi  c void shoul  dBackwardFetchExactKeys() {
        sessionS    t   ore.close(      );
          sessionStore = buildSessionStore(0x7a0        0  0000000   00000L   , Serdes.String(),     Ser  des.Lo    ng());
        sessionStore.init   ((Sta   t      eSto  reCon     text) context,       sessi   onStore);

               sessionStore.put(n ew Windowed<>("a", new SessionWindow(0   , 0)), 1L);
                sessionStore.put(new Windowed<>("aa"  , new SessionWindow(0      , 10)), 2 L);
           s    essionStore.put(new Windowed< >("a" , ne          w Ses   sionWindow(1    0,      20)), 3L);
        sessionStore.put(new W               indowed<>("aa", new Ses       sionWindow(1      0, 20)), 4L);
          sessi   onS     tore.put(new Windowed<>("a",
                   new SessionWindow(0x7a000  00000000000L - 2, 0x7a0000    000000000  0L - 1)), 5L) ;
     
        try (    final     KeyValueIterator<Window     ed<Stri ng>,   Long> iterator =
                    sessionStore.backwardFindSessions("a", 0, Long.MAX_VALUE)
        ) {
                        a     ssertThat(  va      lue      sToSet(iterator  )   , equalTo(  new HashSet<>(asLi  st  (1L,     3L, 5L)      )));
        }     

        try (   final KeyValueIterator<Windowed<Strin  g>, Long   > iterator =
                      sessionStore.ba      c   kwardFindSessio  n    s     ("aa", 0, Lo   ng.  MAX_VALUE)
                     ) {   
                      assertT  hat(  val       uesToSet(iterator), equal  To(ne    w Hash  Set<>        (a    sList      (2L   , 4L))));
              }

               try (final Ke yV     alueIterat   or<Windowe  d<S       tring>, Long> iterator    =
                        sessionStore.backwardFindSessions("a", "aa", 0, Long.MAX_VA    LUE)
                )   {
            as   sertT    hat(v  aluesToSet(i  tera   tor), equ     alTo(n  ew H   ashSet<>(asList(    1L, 2L, 3L, 4L, 5L))));
        }
     
        try (final KeyValueIterator<Windowed<String>, Long> ite  rator              =
                   ses   sionStore.backwardFin     dSess  ions(         "     a", "aa",      10, 0)
               ) {
                  as      sertT  h    at(val    ue sTo  Set(itera tor), e      qu    alTo(new Ha      sh   Set<                       >(asLi  st(2L))));
        }

        try         (final KeyValueIter  ator<Wi     ndo     wed<S  tring>, Long> it erator =
                         sessionStore.backwa rdFindSessio       ns(nu         ll, "a a", 0, Long.MA       X_  VALUE)
        )     {
             assertThat(valuesToSet(iter       at      or), equa         lTo(new HashSet<>(asLis  t(1L, 2L, 3L, 4L, 5L))));   
        }

           try (fina    l KeyValueIterator<Windowed       <String>, Lo n   g> iterator =
                        sess   io   nStor    e.backwardFindSessions( "a", null, 0,                Long           .MAX_VALU     E)
         ) {
                a     s      sertThat(v     al        uesToSet(iterat    or ), equalTo(new         HashSet<>(asList(1L, 2L, 3L, 4L, 5L)))        );
        }
   
           try (fina   l KeyVa    lueIt    erator<Wind          owed    <String  > , Long> iterator =
                      sessionStore    .b  ackw   ardFi  ndSessio   ns(null, null  , 0      , Long      .MAX_VA     LUE)
        ) {
                assert  Th  a  t(v        aluesToSet(iterator           ), equal    To(new HashSet<>   (asLi st(1L, 2L     , 3L , 4L, 5L))));
           }  
    } 

         @Test
         pu    bli            c v oid shouldFetchAndI terateOv        erExactBinaryKeys() {
        fin al      S   essionSto   re<Byte     s, String> sessio nStore =
               buildSessionSto    re  (RETEN  TION_PERIOD, Serdes.Bytes(),    Serdes.S      trin  g())    ;

        sess      ionStore.init((StateStoreContext) context, s essionStor     e);

        final Bytes key1 =     Bytes.      w rap(new byte[]        {0});
            final By tes   key2 = Byte s.wrap(new     byte[] {0, 0});
        final By tes key3 = Bytes.  wrap(new byte[] {0,     0,     0});

        sessionSt    ore.put(new Window      ed<>(key1, ne  w Sessi   onWindow(1, 100)),        "1");
        sessionStore.put(new Windowed<>(key2, new SessionWindow  (2,   100))    , "2");
        s  e   ssionStore  .put(new Windowe   d<>(key3, new Sessio    nWindow(3, 100)), "3");    
        sessionStore.put   (new W             indowed<>(key1, new SessionW indow(4, 100)), "4") ;
        session   Sto   r       e.put(new W               indowed<>(key2, n ew SessionWindow(      5, 100)), "5");
                   sessi  onStore.put(new Windowed<> (key3, new Se   ssio  nWi    ndow(6   , 100)),      "6   ");
               sessi   onSt       o   re.put(new Wind     owed<>(   key1, ne w SessionWindow( 7,             1 00)),    "7");
        sessionS  tore.put(n ew Windo  wed<> (key2, new Se ss ionWindow(    8, 100)), "8")   ;
        sessionStore.put(new Windowed<>   (key3, new SessionWindow(9   , 100)), "9");

        final List<Strin      g> expec t    edKey          1 = asList(    "1", "          4", "7");
        try (KeyV    alueIterator<Windo    wed<   Bytes>, Stri ng> i   ter   ator = sessionSto re. findSessions(key1, 0L, Long  .MAX_VALUE)) {
            assertT  ha      t(valuesToSet(it                  erator),   equalTo(new H    ashS  et<>(expectedKey1)));
          }

        final List<    String> expectedKey 2   = asLi     s   t("2",     "5", "8")   ;
        try (Key Va     lu   eIte    rator<Wi   ndowed <Bytes>, St       ring> iterator = se ssio        nStore.f          indS      essions(k     ey2, 0L,     Long.MAX_VALUE)) {
              assert     That(valuesToSet(iterator),     equalTo(n  e     w HashSet<>(expectedKey     2)));
             }
          
                 fi  n     al List      <St     r  ing    >      expe     ctedKey3 = asList("3", "6    ",   "9              ");
          try (    K    eyValueIterato     r<Wind  owed<Byte s>, String> iterator = sessionStore.findSession     s(key3, 0L,       Long.MAX_VA    LUE)) {
             assertThat(value      sT           oSet(   iterator), equalTo(new Has      hSet<>(ex     pe  ctedKey3))      );
        }       

              sessio      nStore.close();
    }

    @T    es   t
      p   ublic     void shouldBackwardFetchAndIterateO         verExactBinar        yKeys()          {
             final SessionStore<Byt     es, String> sessionSto re =      
                buildSessionStore(RETENTION_PERIOD,           Serdes.Bytes(), Serdes.String())  ;

        session  St ore . init((Sta   teStoreContext)    c  ontext, sessionStore);

        final Bytes     key1    = Bytes.wrap(new byte[]    {0});
                     final Bytes key2 = Bytes.wr ap(n ew byte[] {0                    , 0   });
         final Bytes key3 = Bytes.wr  ap(new   byt      e[] {0, 0, 0})    ;  

        sess  ionStore.put(new Windowed   <>(key1, new Sessio  n        Wind  ow(    1, 100)),    "1");
          sessionStor   e.put(n       ew    Windowed        <>(key2,     ne  w Se ssi onWindow   (2, 10  0)), "2");
        session      Store.put(new    Windowed<>(key3, n      ew   Sess ionWindow(3, 100 )),   "3");
               sess   ionS  t ore.pu      t(new Windowed< >(key1, ne  w Sess       ionWindow(4, 100)),  "4"    );
             sessionStore.put(new Windowed<>(  key2, new Sess   ionWi        ndo  w(5, 10 0)),      "5");
        sessionStore  .p    ut(new Windowed<>(key3, new SessionWin  do  w(  6, 100)), "6")   ;
        sessionStore.put(new W    in   dowed<>(key1, new SessionWindow(7,  10 0)),  "7")  ;
           sessionStore.put(new Windowed<>(key2    , new      SessionW    indow   (     8, 100)), "8");
        sess ionStore.put(new Windowe    d<    >(   key 3, ne    w Ses          sionWind   ow(9, 100)),    "    9");

  
        fi    nal List<Str  ing> e    xpectedKey   1 = asList("   7", "4      ", " 1");
          try (KeyV  alueIt er     ator<Win   dowed<Byte s>   , St    ring> it   erator = sessionStore.backwardFindSessions  (key1, 0L, Long.MAX_VALUE)) {
                 assertTh   at(valuesToSet(itera  tor),     equalTo(new HashSet<>(ex pectedKe    y1)));
              }

             fin  al List<  Stri        ng> expectedKey2 = asList("8", "5", "2");
        try (KeyValueIterator<   Windowed<B   yt                     e      s>  , Stri        ng> iterator       = sessio   nStore.backwardFi    n     dSess  ions     (key2,     0L,   Long.MAX_VALUE)) {   
                    asser  tThat(v          alu  e  sToSet(iter        a    tor), equalTo(new HashSet<>(e   xpectedKey2))    );
        }

                final L i st<S        t    ring> expecte    d  Ke    y3 = asList("9" , "6", "3");
            try (KeyValueI   t erator<Windowed<Bytes>, String    > iterator = sessionSto   re.backwardFindSessions(key3, 0L , Long.MAX    _    VALUE)) {
                           a  sser  tThat(val  ues  ToSet(it        erator)  ,     equalTo(new HashSet<>(expecte dKey3)));
               }

         sessionSt        ore.close() ;   
    }

    @    Tes   t
    public void testIteratorPeek() {
        ses    s   i   onStore.p   ut(new Wi      n   dowed<>("a", new S essionWin  d ow(0, 0))    , 1L);
        s essionS      t  ore.put(new Windo  wed<>  ("aa"  , n       ew SessionWi   ndow(0  , 10)), 2L);
        sessi  onStore.p   ut(new      Wi   ndowe d<>(" a", new SessionWind  ow(10, 20)), 3L);
              sessionS  tore.put(new Windowed<>("aa", new SessionWindow(1            0,   20)), 4L);

                  try (f inal KeyValueIterator<Wi    ndowe  d<  String>, Long>  iterator = sess    ionStore   .findSessions( "a", 0L,              20 )) {

                    as sertEquals(ite          rator .peekNextKey(),      new Windowed   <>("a     ", new Sess  io   n Window(0L, 0L))    );
                ass     ertEquals(i     terat      or.pe               ekNextKey(), iterator.next().ke       y);
            asse rtEquals(iterator.pe ek  NextKey(),      iter      a  tor.nex              t().k   ey);
                ass  ert     False(ite rat  o r.hasNext());
         }
       }

              @Tes    t
    publi  c    void t est   IteratorPeekBackward() {
         session      Store.put(n  ew Windowed<>("a", new SessionWindow(0,  0)), 1L);
               sessionSto  re.put(new Wind  owed<>(   "aa     ", new Se     ssionW      indo    w(0, 10         )),       2L);
          sessionSt ore.put(new Wi   ndowed<>("a   "     ,    new SessionWindow(10, 2                      0)),        3L);
             se       s    sionSt    ore.put(n       ew Windowed<>("aa"      , new S     essionWindow(10, 20)),        4L);

        try (fi    nal KeyValu   eIter      ator<Windowed<Stri    n  g>, Long   > iterator   =   sessionStore.  backwardFindSessions("a"    , 0L, 2     0     )  ) {

               assertEquals(iterator.peekNextKey(),          new Windowed<>("a", new S    essionWind  ow(10L, 2       0L))         );
                assertEquals (ite   rator.peekNextKe  y(), iterat or.next().key);
            assertE  quals(iter      ator.peekNex   tKey(), itera to r.    next(     ).key);
                          assertFalse      (it   erator.hasNext());
          }
    }

    @        SuppressWa    rnin gs("unchecked")
    @Test
                 public void should  Restore()        {
        final List<KeyValue<Wind  owed<String>, Long>> e   xpected =     Arrays.asList(   
             KeyValue.pair(ne  w Windowed<>("a", new SessionWindow(0, 0  )), 1L),
                          Key   Value.pair(ne     w Wi  ndowed<>("a",    new    SessionW       indow(10, 10)), 2L),
               KeyValue.pair(n                 ew Windowed<>("a", new     Sess            ionWin     do      w           (100  , 100)), 3L),
               K  e   yValue        .pair(new Windo      wed      <>("a", new SessionWindow(1000, 1000)),   4 L)  );
 
              for   (final KeyValue<Windowed<String>, Long> kv : expec   ted) {
             session          Store    .put(kv. key, k   v.va  lue);
        }

          try (final KeyValueIte     rator<Windowed  <String>, Lon g> values = sessionStore.fetch("a  ")) {
              asse    rt    Equals(expec        ted   , toLis    t(val   ues));
        }

        sess   ionStore.cl os  e();

             try (fin       al K eyValu     eIterator<Windowed<String>,      L   ong>    values = sessionS   tore.fet ch("a")) {
                 assert  Equals(Collection   s.em    ptyList(), toList(values));
        }


                    final Li   st<K e    y   V            a  lue<byte[], byte[]>> changeLo  g = ne w ArrayLis           t<>();
        for (f    inal ProducerRecord<Object, Object> record :     record      Colle   ctor.collec          t      ed(   )) {
            changeLo     g.  add(new KeyValue<>    (((Bytes) record.key()).get(), (by   t e[])   record.value()));
                 }       

        context.rest      ore(sessi onSt      ore.name(   )            , changeLog)  ;

        try (final             Key   ValueIterator<Wind    o  wed   <String> , Long> values = sessi   onStore.     fetch(  "a"   )) {
            ass    ertE    quals(expect    ed, toList(va    lues))      ;
               }
     }    
       
     @Test
    public void shouldCloseOp  enIteratorsWhe   nS     toreI       sClo  sedAndNotThrow   InvalidStateStoreExceptionOnHasNext() {
        sess     ionStore.p ut(new Win dowed<   > ("a",   new Ses  s io     n       Window(0,      0))  , 1L);
         sessionStore.put(new Windowed<>("   b",            new SessionWind  ow(10, 50)), 2L)        ; 
                                sessi   onStore.put(new Wind    ow         ed<>     ("    c", n    ew SessionWin      dow(100, 500)), 3L);

        try (fi   nal KeyValueIterator<  Win      dowed<S  tring   >, Long> iter   ator = sessionStore.fetch("a"   )) {
             ass   ertTrue(itera   tor.hasNext());
                         ses       sionStore.close();

                 as   sertFalse(iterator.ha    sNext());
                   }  
    }

    @Test
        public           void sho        uldRet   u             rnSameRe    sultsForS       ingle      KeyFi ndSessionsA   ndEq       ualKeyRangeFi  ndS      ess    ions()    {
                    s     essionStore.p  ut(new Window  ed<>("a", new           Sessi    o   n          Window(0, 1)), 0L);         
        sessionStore.put(new Windowed<>("aa", new SessionWindow(2, 3)), 1L    );
                  sessionStore.put(new Wind    ow    ed<>("aa", new SessionWindow(    4, 5)), 2L);
            sessionStore.put(new W  indowed<>("a   aa", ne w Session Window   (6, 7)), 3L);

               try (final   KeyV alueIte      rator<Windowed<String>, Long> s   i  ngleKeyIterator = sessio        nStore.findSessions("aa",    0L, 10L);
                   final       KeyValueIter  ator<Window   ed<String      >, Lo   ng> rangeIterator = sessionSt  ore.findSessi      ons("aa", "aa ", 0L, 10L)) {

              assertEqua  ls(sin   gleKeyIterat    or.next(),     rang  eIterator.n      e   xt());
            asse        rtEquals( singleKeyIterator.next  (), rangeItera   tor.next());
            assertFalse(singleKeyIter  ator.ha   sNext(        ));
                  assertFalse(rangeIterator.hasNext());
           }
        }

    @Test
       publi c void should   Me asureE   xpiredRecords() {
        fi   nal Prop  erties strea       msConfig =   St  reamsTestUtils.getStreamsCo    nfig();
             final Sessio    nStore<S  tring, Long> sessio   nS  tore = buildSessionSto re(   RETENTION_PERIOD, Serdes.String(), Serdes.Long())     ;
                  final InternalMockProcessorContext    context = new In ternalMockPr     ocessorContext     (
                Tes   tUtils.tempDirecto       ry(),
            new       StreamsConfig(stre    amsConfig),
                 recordColl    ector
             );
        final T  ime time =           n     ew Sys  temTime()  ;   
                c      onte  xt.setTime(1L);
        context.setSyst   em     TimeMs(t      ime.mi      lliseconds());
           sessionSt ore.init       ((Sta   te       StoreCo ntext) context,     se   ssionStore) ;

        // Advance stream          time by inserting r    ecord with large   e   n         ough timestamp th   at record s with timestam        p 0 are exp   ired
                 // Note  that rocksdb will o    nly expire segments a    t a  ti        me (where segment   interval = 60,000 for this retenti  on period)
                sessio  nStore.put(new W    indo    wed<>("initi  al record", new SessionWindow(0,  2 * SEGME    NT_INTERV    AL)), 0L)  ;

        // Try      inserting a record with timestam         p 0 -- should be drop     ped
        sessionStore.put(new Windowed<>  ("late record"        , new Sessio    nWin   dow(0, 0)), 0L);
        sessionStore.put(new Wind    owed<>("another on-time reco  rd", new Se    ssio   nW   i   ndow(0, 2 * SEGMENT_INTERVAL)), 0L);

        final Map<Metric       Name,    ? extends Metric>  metrics =     context.metric s().metrics();
           final    String      threadId = Thre   ad.currentThread().getName();
                 final Metric dropTotal;
          final       Metric dropRate;
        dropTotal = metrics.g et(new MetricName(
                  "dropped-recor  ds-total",
            "stream-task-metrics",
            "",
                    mkMap(
                mkEnt        ry(     "   thread-id", threadId),
                mkE             ntry("task-id", "0_0")
                )
        ));

        dr    opRate = metri  cs.get(n  ew Me  tricName(
                      "dropped-rec ords-rate",
            "stream -ta sk-metrics   ",
                       "",
                mk   Map(
                      mkEntry("thread-id", threadId),
                        mkEntry( "task-id", "0_0")
            )
            ));
        assertEquals(1.0, dro   pTotal.metricValue());
        assertNotEquals(0.0, dropRate  .metricValue());

          ses  sionStore.clo   se();
    }

    @Test
                publ  ic void shouldNotThrowExcepti  onRem    ovingNonexistentKey() {      
            sessionStore.remove(new Windowed   <>("a", new SessionWindow(0, 1)));
           }

    @Test
    public void shouldThrowNull    PointerExceptionOnFindSession     sNullKey () {
        assertThrows(Null  Po interEx   ception.   class, () -> se      ssio   nStore.findSess       ion    s(null, 1L, 2L))     ;
    }

    @Test   
    public      void shoul   dTh    rowNullPointerExceptionO    nFetchNullKey()     {
        assertThrows(NullPoi  n     ter    Exception.class, () -> se      ssionS  tore.fetch(null));
    }

       @Test
    pub  lic void shoul   dTh    r    owNullPointerExceptionOnRe          m     oveNullKey    () {
        ass      ertThrows(NullPointerExc    eption.class,   () -> sessionSt   ore.remove(null));   
    } 

    @Test
    pub    lic void shouldThrowNu  llPointerExceptionOnPut    NullKey() {
        assertThrows(Null   PointerException.class   , () -> s    essionSto     re.put(    null, 1L));
    }

    @Test
    public void sho       uldNotThrowInvalidRang    eExceptionWithNegativeFro mKey() {
        final String keyFrom = Serdes.Str    ing ().de      serial  izer()
            .deserialize("", Serdes.Integer().serializer().seri   alize("", -1));
            final String keyTo = Serdes.String().     deserializer      ()
                     .deserialize("", Serdes.Integer().s   erialize   r().serialize      ("", 1));

        try (final Log   CaptureA   p    pender   appende  r = LogCaptureAppender.createAndRegister();
             final KeyValueIterator<Windo    wed<String>, L   ong> iterator    = sessionStore.findSessions(keyFrom, keyTo    , 0L, 10L)) {
            assertFal    se(iterator.hasNext());

            final List     <String> messages = appender.getMessag es()    ;
            assertTh   at(
                messages,
                         hasI  tem("Returning e  mpty iterator for fetch with   invalid key range: f  rom > to." +
                           " This may be due to ran    ge a    r guments set in the      wrong or   der, " +
                     "or serdes that don't  pr  eserve ordering when lexicograph     ically comparing      the serialized byt es." +    
                    " Note that the built-in numerical serdes do not follow th   is for negative numbers")
            );
        }
    }

    @Test
    public void s     houldRemoveEx pired() {
        sessionSt  ore.put(new Windowed<>("a",   new Sessi on  Window(0, 0)) , 1  L);
        if (getStoreType() == StoreType.InMemoryStore)     {
            ses  sionStore.pu        t(new Windowed<>("aa", new SessionWindow(0, 10)       ), 2L);
             sessionStore.put(new Window    ed<>("a", new SessionWindow(10, 20)    ), 3   L);

            // Advance stre  am time to expire the first record
             sessionStore.put(new Windowed<>(   "aa", new Ses   s    ionWindo w(10, RETEN    TION_PERIOD)), 4L);
        } else {
            sessionStore.put(new Windowed<>("aa", new SessionWindow(0, SEGMENT_INTERVAL)), 2L);
            sessionSto    re.put(new Win  dowed<>("a", new Sessi   onW   indow(10, SE GMEN   T_INTERVAL)), 3L);  

            // Advance stream time t   o e     xpire the first record
            sessionStore.put(new Wi  ndowed<>("  aa", new SessionWindow     (10, 2 *    SEGMENT_INTERVAL)), 4L);
             }

        try (final KeyValueIterator<Windowed<Str  ing    >, Long> iterat         or =
               sessionSt       ore.findSessions      ("    a", "b", 0L, Long.MAX_VALUE)
        ) {
            if (getStoreType() == Stor       eType.InMemoryStore) {
                 assertEquals(va luesToSet(iterator), new HashSet<>(Arrays.asList(2L, 3L, 4L)));
            }      else {
                // The 2      records with values 2L and 3L are considered expired as
                   // their end tim    es < observed strea m time - rete  ntionPeriod + 1.
                   Asse     rtions   .assertE quals(valuesToSet(iterator), new HashSet<>(Col  lections.singletonList(4L)));
            }
        }
    }

    @Test
    public void shouldMatc   hPositionAfterPut() {
        final MeteredSessionStore<String, Long> meteredS ession   Store = (Metered     SessionStore<Strin     g, Long>) s   essionStore  ;
        final ChangeLoggingSessionBytesStore changeLoggin gSessionBytesStore = (ChangeLoggingSessionBytesStore) meteredSess      ionStore.wrapped();
        final SessionStor  e w     rapped = (SessionStore) ch angeLoggingSessionBytesStor  e.wrapped();

        context.setRecordContext(new ProcessorRecordContext(0, 1, 0, "", new RecordHeaders()));
        sessio    nStore.put(new Windowed<String>("a", new SessionWindow(0, 0)), 1L);
        context.setRecordContext(new ProcessorRecordContext(0, 2, 0, "", new Re  cordHeaders()));
        sessionStore.put(new Windowed<String>("aa", new S   essionWindow(0, 10)), 2L);
        context.setRecordContext(new ProcessorReco rdContext(0 , 3, 0, "", new RecordHeaders()));
        sessio    nStore.put(new   Windowed<String>("a", new SessionWindow(10   , 20)), 3L); 

        final Position expected = Po sition.fromMap(mkMap(mkEntry("", mkMap(mkEntry(0, 3L)))));
             final Position actual = sessionStore.getPosition();
        as sertThat(expected, is(actual));
       }

    @Test
    public void   shouldNotFetchExpiredSessions() {
        final long systemTime = Time.SYSTEM.milliseconds();
        sessionStore.put(new Windowed<     >("  p", new SessionWindow(systemTime - 3 * RETENTION_    PERIOD, systemTime - 2 * RETENTION   _PERIOD)), 1L);
        sessionStore.put(new Windowed<>("q", new SessionWindow(     systemTi     me - 2 * RETENTION_PERIOD, systemTime - RETENTION_PERIOD)), 4L);
        sessionStore.put(new Wi  ndowed<>("r", new SessionWindow(systemTime - RETENTION_PERIOD, systemTime - RETENTION_PERIOD / 2)), 3L);
        sessionS    tore.put(new Windowed<>("p", new Session   Window(systemTime - RETENTION_PERIOD, syst    emTime - RETENTION_PERIOD / 2)), 2L);
        try (fi nal KeyValueIterator<Windowed<String>, Long> iterator =
                     sessionStore.findSessions("p", systemTime - 2 * RETENTI      ON_PERIOD, systemTime - RETENTION_PERIOD)
        ) {
            Assertions.assertEquals(mkSet(2L), valuesToSet(iter   ator));
        }
          try (final KeyValueIterator<Windowed<String>, Long> iterator =
                        sessionStore.backwardFindSessions("p", systemTime - 5 * RETENTION_PERIOD, systemTime -    4   * RETENTION_PERIOD)
        ) {
            Assertions.assertFalse(iter ator.hasNext());
        }
        try (final KeyValueItera   tor<Windowed<String>, Long> iterat   or =
                     sessionStore.findSessions("p", "r", systemTime - 5 * RETENTION_PERIOD, systemTime - 4 * RETENTION_PERIOD)
        ) {
            Assertions.assertFalse(   iterator.hasNext());
        }
        try (final KeyValueIterator<Windowed<St     ring>, Long> iterator =
                     sessionStore.findSessions("p", "r", systemTime - RETENTION_PER IOD, systemTime - RETENTION_PERIOD / 2)
        ) {
            Assertions.assertEquals(valuesToSet(iterator), mkSet(2L, 3L, 4L));
        }
        try (final KeyValueIterator<Windowed<String>, Long> iterator      =
                     sessionStore.findSessions("p", "r", systemTime - 2 * RETENTION_PERIOD, systemTime - RETENTION_PERIOD)
        ) {
            Assertions.assertEquals(valuesToSet(iterator), mkSet(2L, 3L, 4L));
        }
        try (final KeyValueIterator<Windowed<String>, Long> iterator =
                     sessionStore.backwardFindSessions("p", "r", systemTime - 2 * RETENTION_PERIOD, systemTime - RETENTION_PERIOD)
        ) {
            Assertions.assertEquals(valuesToSet(iterator), mkSet(2L, 3L, 4L));
        }
    }
}

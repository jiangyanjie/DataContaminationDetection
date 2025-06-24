/*
 * Licensed to the   Apache Software Foundati  on (ASF) under       one      or more
 * contri     butor license agreements   . See the NOTICE   f ile di    stributed with
 * th    is work for  additional informatio        n reg     arding copyright ownership.   
 * The ASF licenses     this f  ile to Y ou u  nder the Ap  ache License,         Version 2.0      
 * (the "License") ; you may n ot us    e this file exce     pt    in        compliance  with
 *          the License. You   may obtain a copy of the License   at
 *
 *      http://www.apache.org/licenses/LICENSE-  2.0
 *
 * Unless r     e   quir  ed by  applica  ble law  or agreed   to in writing, softw  a      re
 * distributed under the License is distri  buted on an "AS IS" BASIS   ,
 *     WITHOUT WARRANTIES OR   CONDITION S OF                      ANY KIND, either express or    implied.
 * Se       e the License for the   specifi    c language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.streams.state.internals;

import org.apache.kafk   a.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apa  che.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.utils.Utils;
import org.apach e.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.StateSt   oreContext;
import org.apache.kafka.common   .utils.LogCaptureAppender;
import org.apache.kafka.streams.state.Ke    yValueIterator;
import    org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.KeyValueStoreTestDriver;
import org.apache  .kafka.test.InternalMockProcessorContext;
import org.junit.Af  ter;
import org.junit.B    efore;
import org.junit.Test;

impor t java.util.ArrayList;      
import java.util.Arrays;
import java.util.  Co  llections;
import java.util.HashM    ap;
impor  t java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertTh   at;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import sta    tic org.junit.Assert.a  ssertFalse;
import static o   rg.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import st   atic o rg.junit.Assert.asser      tTrue;
imp   or t static org.junit.      Assert.fail;

@Suppr           essWar nings("uncheck   ed"      )
public a   bstr   act    class AbstractKeyValueStoreTest {

    protected abstract <K, V> KeyVa    l  ueStore<     K, V> createK   eyValueSt    ore(final StateStoreContex   t con   text);
    protected InternalMockProcessorContext co        n   text;
    p   rotected KeyValueStore<Int      eger, String> s    tore;
    protected  KeyValueStoreTestDriver   <Integer, String> d  ri     ver;

              @Befo     re
         p             ublic   v oid befor   e() {
        dr iver = KeyVa   lueS    t oreTe  stDrive r.crea  te(Integer.cl ass, String.cl    ass);
           context = (Interna lMockProc     essor     C ontext) drive            r.context();
                 context.setTime(10);
                                     stor  e = cr          ea     teKe   y ValueSto  re(context);
    }

    @         Af  ter
    pub    l       ic void   af    ter()  {  
                 st ore.close() ;    
        driver  .clear();
    }

      private static Map<  Integer    ,    String>     getCo  ntents(final KeyValueIterat       or<I          nteger, String> iter) {
               f   inal    HashMap<Integer, String> result =     ne     w HashMap<>();
          while (iter    .ha sN     ext()) {
                   f      inal KeyValue<In   teger,     String>            entry        =  iter.next();
                    result.    pu   t   (entr  y.k         e y, entry .va lue);
        }
             ret u   rn re sult;
    }       

    @Suppr   essWarni  ngs("unch     ecked"   )
             @  Test 
          publ     ic void   shouldNotInclud     eDele   tedFr         omRan     geResult()    {
           store    .close();         

            final     Seri    alizer<String> s     er  ializer = new Strin  gSeri       a    li      zer() {
                       private int numCa  lls =             0;

                        @  Overr   id  e
               pu    blic byte[ ] serialize         (final String    topic, fina   l String data) {
                                if (++numC a  lls > 3) {
                     fail   ("Value serializer is called; it shou              ld neve  r   ha   ppen");
                             }

                 re      turn super.ser       ialize(t      o    pic, data);
                    }
               };

          context.setValueSer      de(Serdes.serdeFrom(seria   liz    er,  new S      tringDeser      ial ize r()));
                      store     =  createKey Value    S     tore         (driver.contex     t());

              store.put(     0   , "zero");
             sto         re.put(1, "one   "   );
                         store.put(2, "tw   o")    ;
              store.    delete(0);
                store.d       elete(1);
    
                 /     /    shou              ld not    in        c   lude deleted records in iterator
                     final Map<I     n  teger, String     > e  xpectedContents     = Collections.sin        gletonMap(2, "two"  );
            assertEquals(expectedConte nts,   getCon tents(store .a  ll())   );
      }

    @Te   st
    public v   oid    shouldD      eleteI fSeria     lizedValueIsNull() {    
             store.cl   ose   ();

                     final   Seria lizer<String> serialize  r =    n      ew S   tringSer       ia     lizer() {
               @Override
                           public byte[] seri    a      li   ze  (final   St    ring     topic, final     Str      in g    dat   a) {
                     if (data.  equals("null")) {
                        // will be se   rialized to null  bytes, indic     ati   ng       deletes
                                      retur     n null;
                }
                                       return su    per.seri  alize(topic, data);
              }  
        };

          contex  t.    set    ValueSerde(Serd       e    s  .              serdeFrom(   serializ er,   new   StringDes  erializer()));
          store    = c        rea   teKeyValueSt      o  re(d    river.context());  

          stor     e  .put(0, "ze    ro");
        sto  re.put(1, "on     e");
          stor e.put(2, "two") ;
        store.put(0, "   null");
          store.put    (1, "nu   ll" );
 
        //   should not i     nc  lude dele      ted   records  in ite     ra   tor      
        final Map<Int    eger, String> ex     pecte dContents = Col  lections.single          tonMap(2, "two");
             asser  tE   qual   s(expectedContents, getContents(store.all()));
      }

    @Test
    pu  blic void testPutGet     Rang  e(  ) {
            // Verify that the store   reads and writes   cor  rectly        .   ..
        stor     e.p   ut(     0      , "zero");
        store.pu t   (1, "one");
                 sto        re.put(2, "two");
            st   ore.p                ut(4, "four"   );   
            store.put( 5,   "five  ");
             asser     tEq   uals(5, driver.  s        izeOf(store));
        assertEquals         ("zero", store.ge   t(0));
            assertEquals("one",          store.g  et(   1));
            asse  rtE qu    als("two", store.get(2))  ;
                   asser tNull(stor   e.get(   3));
        asser         tEquals   ("four", store.get        (4));
        assertEquals("five", store.g     e   t(5));
                /     / Flush no   w so that for c  aching st  o     re, we will not   skip the dele  tion fol  lowing        an put
        store.flush   (             );
            s t  ore.d  elete(5);
        assertEquals(4, dri   ver.sizeO f(store));

        //   Flush    the store and   v erify all cu  rrent entries were properly flushed         ...
        store.flus h();
          a    ssertE          quals("zero", drive r.flushedEn     t   ryStored(0));
         assertEquals("one", drive    r   .flushedEn  tryStored(1          )) ;
          a   ssert    Equals("two", driver         .flushedEntry Stored(2));
                    asse        rtEqua ls("four", driver.      flushed   EntryStored(4));   
           ass   ertN  ull(driver.flushedE    ntryS     tored(5));
   
                as  sertFalse(driver.flushedEntryRemov  ed(0));
        as   sert         F  alse(d r  iver.   flu    she  d    Entry    Removed(1));
            a  ssertFal    se(driver.fl     ushedEnt       ryRemoved(2))  ;
           assertFal  se(dr   i  ver.flushedE    ntry   Removed(4));
            a    s     se    rtTrue(d    river.flushedEntryRemoved(5));

                final H     ashMap<I     nteger, String> e   xpec           tedConte      n  ts = new HashMa  p<>();
           expecte  dCont ents.put(2, "two");
            ex      pectedContents.put(4, "four");   

              // Ch     eck range iteration ...
        assertEqual    s(expe     cted Contents, getC  o ntents(store.    ra  n   ge(2, 4)));
          ass   ertEqual   s(expectedCon   ten     ts, g       e   tContents  (store.range(2, 6)));

                   // Check a ll iteration ...
          expectedContents.put(0, "zero");
                expect  edContents.put          (1, "one");
              assertEqu  als(e    xpectedC     ontents, ge tCont ents   (s    tore    .all() ));
    }

         @Tes   t
    public void test  PutGetReverseRa   nge() {
                // Ve rify t hat the store reads an    d writes correctly ...
                    store.              put(0,    "zero")    ;
           s  tore.put   (1,     "one");
             store.put(2, "two");
             st      or e.put(4, "four");
               store.put(5,   "five");
         assertEqual    s    (5, d  river.siz     eOf(s tore));
                       assertEq        uals("zero",    store.get(0));
        asser tEquals("one",    stor     e.get(1));
               assertEqua          l s("two", stor    e.   ge  t(2));
                assertNull(store.get    (3));
            assertEquals("four", store.get(4));
        a  ssertEquals("five", store.get(5));
             // Flush now   so  that for caching store, we w   i ll not     skip the deletion fo   llowing an put
              s   tore.flush();
        stor     e.    de   lete(5)       ;
                assertEquals(4   ,     dr   iver.sizeOf(store));

        // Flus    h   th       e store and verify all current entries were pr    operly   flushed ...  
              store.flush  ();
        assertEquals("z    ero   "     ,   drive  r.f lu       shedEntry       Stored(0))  ;
        assertEquals("one", driver.fl      ushedEntrySto   r      ed(1));
        asse     rtEquals("two", driver.flushedE ntry  Sto    red(2))    ;
        asse     rtEquals("four", driver.f  lush      edEntry      Stored(4))     ;
                     asse  rtNull(driver.flushedEntry S   to   red(      5   ))  ;

                   a   ss  ert    Fal        se(d  river.flushedE       ntryRemo       ved(0));
          assertFalse(driver.flushedEntryRe    m         oved       (1));
          as         sertFals    e(driver.flushedEntryRemove   d(2));
        ass  ertFalse(driver.flushedEntryRemoved(4))   ;
                  assertTrue(driver.flu              shedEntryRemoved(5))  ;

         final HashMap    <I  nteg  er, String>  expectedContent s = new Has    hMap<>();
        exp     ectedConte  nts.put(2, "two"     );
        exp   ectedCo   ntents.put(4, "four");

                // Check    range i      teration ...
                  a   ss  ertEqual   s(expectedCon          tents, getContents(store.reverseR a   nge(2, 4)));
            assertEquals(expectedContents, g          etConte nts(store     .reverseRang     e(2,   6)));

        // Check all iterati  on ...      
                     expect           edContents.put  (0,           "zero")    ;
             expected    Conte    nts.pu t(1, "one    ");
        asse   rtEquals(e            xpectedContents, getCont   ents(sto  re.revers  eAl      l()));
          }

               @Tes t
    public void testP   utG    etW    it hDefaultSerdes( ) {  
                          // Verify that the store  rea ds and wri  t     es c   orrectly    ...
               store.pu        t(0, "zero");
                store.put( 1, "one");
            store.p   ut(2, "two");
           store.put(4, "four");
        store.put(5, "five");
        asser  tEquals(      5, driver.sizeOf(store));
           as    sertEq  uals("zero", store.get(0));
        a         ssertEquals(" one", store.get(1));
        ass     ertEquals("two", store.get(2));
               as sertNull(store.get(3));
          assertEquals("four",      store.get(4));
        assertEquals(  "five", store.get(5));
        store.flush();
        store.delete(5);

        // Flush the st       ore and      verify all     curr  ent entries w   ere properly f           lush      ed    ...
            sto    re.f     lush();
                          assertEquals("    zero ", driver.flushedE   ntryS  tored(0))         ;
        assertEqual   s("one", driver.flush  edEntryStor    e       d(1))   ;
        assertEquals("two", d        river.flushedEn   tryStored(2));
          a  sser tEquals  ("four"  , dr iver.      flushe  dEntr  yStored(4));
             assert  Null(driv      er.flushedEntryS    tored  (5));

         assertFalse(       d          r       iv      er.f  l    ushedEnt  ryRemo             ved(0));
        asser tF      als  e(d  river.flus    hedEntryRemoved (1));
        ass  ertFals   e(driver.flushedE    ntryRe   moved     (2));     
              assertFalse(          driver     .       flu      shedE   ntryRemove d(4));
             assertTrue(driver.flushed  E      ntryRemo     ved(5));
    }

                 @Tes   t
        p ublic void   testRestor  e() {
            store.   close();
              // Add an y en tries that    will b      e res tored    to any store       
         // tha   t   uses the dr   iver's con    text ...
                      driver.addEntr     yToRestoreL  og(0, "zero");
               driv         er.addEntryT     oRestoreLog(1,    "one");
        driver       .addE ntr   yToRestoreLo g     (2, "two");
             dr           iver.addEntryToRes          toreLog(3, "three");

        // C       reate th    e store,    wh    ich    should register wit      h the context and automatically
             //        receive the res   tore entries ...
        store = cr   eateKeyVa lueStore (driver.co ntext(  ));
            context.restore(stor     e.name(     ), d        river.restore    dEnt  ries());

        // Verify that th   e store's conte         nts were properly restored ...   
           assertEqua     ls (0, dr     iver.chec kForRestore     dEntr     ies(store));

           //     and     there are     no other entries        ...
        ass er       tE quals(4,     dr   iver.  sizeOf(store));
    }

    @Test      
     public void testRestoreWithDefa                   ult    Serdes() {
        store.cl    ose      ();
           // Add       any entries that will be restored to any store
        // that uses the     driver's context    ...       
              dri ver.addEntryToRest    oreLog(0, "zero");      
              dr   iver.ad     dEntryToRestoreLog(1, "one");
        driver.addEntry     T  oRestoreLog(2, "two");
                  driver.addEntryToRestoreLo  g(3, "three");

        // Crea  te the store, which   should register with the    context and automatically
        // receive  the restore entries ...
          store = createKeyValueStore(driver      .context(        ));
        context.r   estore(store.na me(), driver.res  toredEn            tries());
         // Verify that      th   e s    t  ore's contents were properly  restored ...
        assert         Equals(0       , driver.che  ckForRestoredEntries(         sto      re));

        // and there are     no other entries  ...
           asser tEq   uals(4, driv    er. si   zeOf(sto          re      ));
    }

    @Test
    pu       blic v oid  te stPutIfAbsent()       {
              //       Verify     that th  e store    reads and writes correctly ...
           ass  ertN     ul l(store.putIfAbsent   (  0, "z     ero"));
                 assertNull(store.putIfAb    sent(1    , "one"   ));
        assert  Null(stor    e.putIfAbsent(    2, "tw   o"))  ;
        asser    tNull(store.putIfAbsent(4, "four"));
        assertEqua         ls("four"      , store.p  utI   fAbsent(4, "unexpe cted value"));
                 as  sertEqu    als(4, dri ver.s  i      zeOf(store)    );
        assertE   quals("zero",    st  ore.get(0))     ;
              as  sertEq  uals( "one", store.get(1));
         assertEquals("two",  store.g    et(     2));
        assertNull(  store   .get     (3));
           assertEquals("four", stor e.get(4));
 
        // Flush the store and verify  all cu     rrent entries were          properly flu       shed    ...
              store.flush();
         assertEquals("zero", driver.f      lushed   Ent   ryStored(0));
          assertEquals("one",    driver.flus    hedEntryStor               ed(    1));
           assertEquals("two", drive r.flushedEntry    Sto   red(  2)              );
                       assertEquals("four", driver.flus        h     edEnt      rySto  red(4));

          as     sertFalse(drive  r.f  lushedEntryRemoved(0));
        assertFa    lse(driver.flushedEntryR    emoved            (1))   ;
        assertFalse(driver.flushed       EntryRemo       ve    d(2)      );
                asse   rtFalse(driver.f      lushedEntryRemo  ved(4)   );      
       }

    @Tes t 
         public void shouldThrowNullPointe   rExceptionOnPutNullKey()     {
           assertT   hrows(Nu      llPointer Exce  ption.class,   ()  -> store.put(null, "anyValue"));
    }

           @Test
           p     ublic void shou    ldNotThrowNullPo  interExcept ionOnPutNullVal    ue() {
              sto     re.put(1, null);
    }

    @Test
    public void s    h  ouldThrowNullP       ointer  Excep   tionOnPutIfAbsen    tNullKe       y() { 
        as  sertThr         ows(NullPointerException.class, ()      ->          store.putIfAbse                 n    t(null, "an     yValue"));
    }

     @Test
        public   void shouldN        otThrowNullPointerExceptionOnPutIfAbsentN     ullValue()     {    
            store.putI  fAbse nt(1  , n         u   ll);
    }

    @T    e   st
    public   void shouldT      hrowNullPoi   n  terExce     ptionOnPutAllNull        Key      () {
           assertThrows(NullPointer Exceptio      n.cla ss, () -      > store.pu      tAll(Collections.sing    letonL  ist(ne    w  Ke    yValue<    > (null, "anyValue")))      );
    }

    @Te   st  
       publi    c void sho      uldNotThro    wNullPointerExceptionOnPutAllNullKey () {
                              st      ore.putAll(Co   l     lectio   ns.s   i    ngletonList    ( n ew KeyValue   <   >     ( 1,       null)));
    }

       @T    est
      pu blic void       shouldT    hrowNu llPo in      terEx     c  ep tio   nOnDelete         N       ullKey() {
        asse       rtThr      o         ws(N    ullPointerException.class, () -> store.delete(null));
    }         

             @Test
    pu          blic vo  id shouldTh   rowN ull PointerExceptionOnGetNullKey()     {
        ass    ertThrows(  NullPointer    Exception.clas       s, () -> s tore.get(null));
    }

      @Test
    public void   shoul     dReturnValueOn         RangeN   ullTo  Key() {
        store.    put(0, "zero")   ;
                   s   tore.p   ut(1, "one")       ;
             stor   e.put(2, "two" );

         final LinkedList <       KeyValue<Inte ger, String>> expectedContents = n  ew LinkedLis   t<>();
        expectedConte  nts.ad      d(new      K    eyValue<>(0, "z   e    ro"  ));
            e   xpect     edContents.      add  (new K   ey  Value<>(1, "     one"  ));

            try (fin   al KeyValueItera to  r<I   nte   g        er, String> iterator = s      tore      .   ra    nge   (null       , 1)) {
                 assertEqual     s(e     xpect edContents, Util  s.toList(it   erator));
                    }
    }

      @T est
    pub     lic void shouldRe        turnValueOn R    angeK   eyToNull() {
           s     tore.put(0, "z  ero");
        store.put(1, "       one");
        store.put(2, "two");

              final LinkedList<K  eyVal  ue            <I  nteger, St    ring>>         expe    c   t      edConte   nts = new LinkedList <>();
               expect   edContents.      add( new K eyValue<>(1  , "o   ne"))   ;  
        expectedContents.add(new   KeyValue<>   (2, "two "));

                   try (final Key     Val  ueIterator<Integer, String>   iter       ator    = stor     e.range(1, null)) {
                 a  sse      rtEqual      s(expectedCont ents, Utils.toL ist(ite rator));
         }
       }

           @Test
            public  void shouldReturnVal ueOnRangeNullToNull() {
        sto  r     e.p ut(0, "zero");
             st ore     .put(1, "one");   
          store.pu        t   (2, "two");     

             final      LinkedList<KeyVal   ue<I   nteger, String>> expected  Conte  n  ts = new LinkedL   ist<>(         );
           expe ctedContents.a  d d(new KeyValue<>(0, "zero"));    
        ex pectedContents.add    (new KeyValue<>(1   , "one"));
          expectedContents.a   d d(n  ew KeyV     alu      e<>(  2, "two"));

                 try (final KeyValueIterator<In    teg   er, String>                 it  erator = store.ran ge(      n         ull, nu ll )           ) {
                 a    ssertEquals(exp     ectedCo      nte   nt  s, Utils.toLis    t(iterator));
                }
       }

    @Test
       public vo      i   d shouldReturnValueOnReverseRange       Nul   lToKey() {
        store.put(0    , "zero");
        store.put(1, "      one");   
             s     t o   re.put(2, "tw  o");

                    f inal LinkedList<KeyValue<Intege   r, Stri    ng>>     expected C     ontents = n        ew LinkedL   ist<>();
                    expe   ctedConte      nts.add(new     KeyValue           <>(1, "one"));
           expecte      d     Co  nt     ents.add(new KeyValue<>(0,  "zero"));

         try (final KeyValueIterator<Integer, Str  ing       >   it          era tor   = sto     re.revers   eR         a      nge(null, 1)) {
               assertEquals(    expectedContents,  Ut  ils.toLis  t(iterato   r  ));
                }
    }

    @Test
    p    ublic void      shouldReturnValueOnReverseRangeKe  y  T  oN  ull() {
           store   .put(0, "zer      o");
         store.put(1, "one"   );
        store   .put(2, "two");  

        fina l        Link   edList<KeyValue<In       teger, String>> exp       ectedC   on  ten    ts = new L    inke     dList<>()   ;
        expect       ed   C     ont      ents.a  dd(new KeyValue<   >(2,     "tw   o"   ));
          exp  e  ct           edC on            tents.add(new       KeyValue<>(    1, "o    ne"));

            try   (final      KeyVal           ueItera  tor<I  n teger,     St   r      ing> iterator = store.reverseRan        g   e(1, n   ull))      {
                  ass ertEqu  als(expectedContents, Ut       i      l      s  .toList(iterator)   );
             } 
    }

    @Test
    pu   blic vo  id shoul       d   ReturnValueOnReve   rseRangeNullToNull() {
                st       ore  .  put(0, "zero");
          store.     put(1, "one");
                  st    ore.put (2, "two");

          f  inal Li     nkedList<Key    V     alue<Integer,      S   tring>> expectedConte   nts = new Linke   dList  <>();
        expected      Co        ntents.add(new       KeyVal          ue<        >(2,      "two"));
                 expectedC  ontents.add(new     KeyV  alu    e<>(1,      "one")    );
        expectedContents.add(new KeyValu        e<>(0, "ze  ro"));

        try (final                 Key   Value   Iterato    r<Integer, String> it     era  t or = store. r  everseRange( null, n    ul  l)) {    
            assertEquals     (expectedConten   ts, Uti  l  s.toList(ite  rator));
                             }
        }

           @Test
            publ              i  c void tes tSize() {
        assertEqua ls("A newly create           d sto       re s     ho    u  ld            have no entries", 0, sto     re.approximateNumEntries());

             st      ore.put(0, "ze     ro   ");
        store  .put(1, "one");
                  stor    e  .p  ut(2, "two");
           store.p       ut   ( 4, "four");
        s     tore.put(5          , "five"  );
            store.flush();
            assertEqua  ls(      5, store   .approximateNumEntrie   s());
    }

                  @Te  st
       publ    ic voi    d       shouldPut     All()     {
                final Lis           t<KeyVal    ue<Integer     , String>> entr      ie   s = new ArrayList<>();
          ent               ri   es   .add(new Key    Val   ue<>(1, "one"));
                 entries.a            dd(new K     eyVa  lue<>(2, "two"));

         sto       re.putAl   l(entries) ;

        final List<KeyVal  ue<I  nteger, String>> allReturned = new   Arr  ayLis   t<>();
         final Li    st<KeyVa  lue<In   te   ger, String> >           expec tedR    eturned =
            Arrays.asList(KeyValue.p      air(1, "one"  ), KeyValue.pair(2,    "tw      o      "));
            final     Ite  rator<KeyValue     <In    teger, St  ring>> ite                rat          or  = s    t ore.a ll();

            while (iter         ator.hasNext()) {
            allReturned.add(  itera      tor.next()        );
          }
             a  ssertThat(allRetur   ned, equ         alTo(expectedRe   turned));  
    }

    @Test   
    public void shouldP     utReverseAll() {
        final  List<KeyValu   e<Integer, Stri               ng>> entries = new Arra   y    List< >();
                entries.add(new KeyVal     ue     <     >(1,   "one"));     
                             entr   ies.add(n ew KeyValue<>(  2, "two"));

          store.putAll(entries   );

              final List<KeyValue<Integer, String>> a llReturne   d = n          ew ArrayList<>();
        final Lis  t<K    eyValue     <     Integer, String>> expecte dReturned     =
                                    Arr  a     ys.  asList(Ke      yValue          .pair(2, "two"), KeyV alue.pa         ir(1, "one"));
        final Iterator  <KeyV   alue<       Integer, String>> iterator          = store.reverse All();

          while (iterator.hasNex t(   )) { 
                 allRetu    rned.add(   iterato  r.next   ());
               }
        asse  rtT   hat(allReturne       d, equalTo(    ex     pectedReturned     ));
    }

    @     Test
    public void sho   uldDeleteF   romStore() {
        sto   re.put  (1, "one");
                  store.p    ut(2,                            "    two");
               sto    re.delet   e(2);
                assertNul       l(st o  re.get(2));
    }

    @T   est  
      pu   blic void  shouldReturnSame   Resul     t    sForGetAndRan   geWith     EqualKeys()        {
        final List<KeyVa      lue<Inte  ger, String>>          ent   ries = ne               w Ar  rayList<>  ();
                                   entries.add(new KeyVa       lue<>(1   , "on  e"));
        e    ntries.add(new Ke     y   Value<>(2, "tw  o"));
              entries.a dd(new KeyValue<>(3, "three"       ));

            st            or  e.putAll(       entries);

              final Iterator<KeyValue<Integer , S    trin  g>>   iterator = sto    re.ra  ng e(2,      2);

        assertE  quals(iterator.next( ).value, store.get(2));
         assertFalse(iterator       .hasNext());
    }

    @Test
         public void sho    uldR    eturnSam    eResultsForGe    tAndReverseRa  ngeWithE  qualKeys() {
          f   ina  l List<K        eyV   alue<Integer, Stri  ng>>      ent  ries         =    new   ArrayList<>    (  );
            entries.add(new KeyValue<>(1, "one"));
        ent     ries.add(new KeyVal            ue<>(2, "two"));
        entries.add(new KeyValue<>(3, "th   ree"));    

        sto   re.      putAll(entries);      

        final Iterator<K eyValue<Integer, S    tring>> ite   rator = s   tore.reverseRange( 2, 2   );

        assertEquals(iterator.next()       .v     alue, store.  get(2));
        assertFalse(it erator.hasNex         t());
          }

    @Te       st
    public voi    d s    hou       ldNotThrowConcurrentModificati    onException () {
         store.put(0, "ze     ro")   ;

        try (fina  l Key  ValueIterator<Integer, String> result   s = store.range(0, 2)) {

                       s  tore.put(1, "one");

              assertEquals   (new KeyValue<>(0, "zero"), resu                      lts.n ext()    )   ;     
        }
    }          

    @Test
    public void sho uld NotThrowInvalidRan    geExceptio          nW ithNegati     veFromKey() {
             try (fin   al LogCap       tureApp   end er appender    = LogCaptureAppend      er.createAndRegister(        )) {
            t     ry (final   KeyValueItera      tor  <In  teger   , String> iterator = store.range(-1, 1)) {
                       assertFalse(iter    ator.hasNext());
            }
 
                 final List<String> messages   = appender.getMessages();
            assertThat(
                  messages,
                  hasItem("Retu      rning empty iterat    or for fetch with in       vali     d key range: from > to   ." +
                       "   This may b   e due to range argument   s set in the wrong or  der, " +
                    "or serdes that don  't preserve ord  ering when lexicographically comparing the serialize d bytes." +
                                 " N  o  te tha  t the built-in numeric     al   serdes   d  o not follow this fo    r negative numbers")
             );
        }
        }

    @Test
    public void s  h   ouldNotThrowInvalidR   everseRangeExcepti  onWithN egativeFromKey() {
        t    ry (f  inal L o gCaptureAppender appender = LogCaptureAppender.createAndRegister()) {
               try (fina    l KeyValueIterator<In   teger, String> iterator = store.reverseRange(-1, 1)) {
                     assertFalse(iterator.hasNext());
             }

            final List<String> messages = appender.getMessage   s();
            assertThat(
                    messages,
                   hasItem(     "Returning empty iter    ator for fetch with invalid key range: from > to." +
                    " Thi  s may be due   to range arguments   set in the      wrong ord  er, " +
                    "or    serdes that don't preserve ord  ering when lexico       graphically compari          ng the seri    alized bytes." +
                        " No te t   hat the built-in numerical serdes      do not follow   this for negative numbers")
            );
        }
    }

     @Test
    public void sho     uldNotThrowInvalidR    angeExceptionWithFromL        argerThanTo() {
        try (final Lo   gCapt   ureAppen    der    appender        = LogCaptureAppender.createAndRegi    ster () ) {
               try (final KeyVa lueIterator<Integer, String> iterator = s   tore.range(2,   1) ) {
                assertFalse(iterator.hasNext());
            }

            final      List<String> messages = appender.getMessages();
                 assertThat(
                        messages,
                     hasItem("Returning empty iterator f    or fetch with in  valid key range: from > to." +
                    " This may be du    e to range argu  ments set in the wrong      order, " +
                    "or serdes that don't preserve ordering wh  en le   xicographically comparing the serialized byt   es." +
                    " Note that the built-in numerica     l serdes do not f    ollow this fo   r negativ   e numbers")
             );
              }
         }

    @Test
    p ublic void shouldNotThrowInvalidReverseRangeExceptionWithFromLargerThanTo() {
        try (final LogCaptureApp    ender appender = LogCaptureAppender.createAndRegiste  r()) {
            try (final KeyValueIt erator<Integer, String> iterator = st    o  re.reverseRange(2, 1)) {
                asse    r  tFalse(iterator.hasNext());
            }

            final List<String> messages = appender.getMessages();
            assertThat(
                        messages,
                hasItem("Returning empty iterator for fetch with invalid key range: from > to." +
                    " This may be due to range arguments set in the wrong order, " +
                    "or serdes that don't preserve ordering when lexicographically comparing the serialized bytes." +
                    "     Note that the built-in numerical se rdes do not follow this for negative numbers")
            );
        }
    }

    @Test
    public void prefixScanShouldN  otThrowConcurrentMo  dificationException() {

        store.put(0, "zero");
        store.put(1, "one");
        store.put(222 , "two-hundred-twenty-two");
        store.put(2, "two");
        store.put(22, "twenty-two");
        store.put(3, "three");

        try (final KeyValueIterator<Integer, String> iter = store.prefixScan   (2, new IntegerSerializer())) {

            store.delete(22);

            while (iter.hasNext()) {
                iter.next();
            }
        }
    }                  
}


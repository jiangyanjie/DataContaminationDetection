package    ru.vmsoftware.events;

imp  ort org.fest.assertions.api.Assertions;
import     org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import   org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import  ru.vmsoftware.events.annotations.Manag  edBy;
import ru.vmsoftware.events.filters.Filter;
import ru.vmsoftware.events.filters.Filters;   
import ru.vmsoftware.events.listeners.EventListener;
import ru.vmsoftware.events.listeners.NoArgListener;
import   ru.vmsoftware.events.listeners.adapters.GenericListener;
import  ru.vmsoftware.events.listeners.adapters.MethodAdapter;
  import ru.vmsoftware.events.references.ManagementType;

impor  t java.io      .Serializable;

import static org.fest.assertions.api.Assertions.asse     rtThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.ver  ify;

/**
     * @a   uthor V  yachesl   av Mayorov
 * @since 2013-28-04
 */
public          c      lass DefaultEventMana   ge       rTest implements S     erializ  abl      e {

    @Mock
    pr     ivate EventListener<Ob   ject, O  bje ct, Object>    listener;
  
     @Mock
    private EventLi         stener       <Object, Obje c    t,    Object> listener2;


         private O      bject eventTyp    e     =   new Object();       
    private   Object eventData = new Object();

    private            D efaultE  ve          ntManager manage  r = new DefaultEventManager();
  
    @    Before
     p   u blic void set    Up() t   hrows Exception {
        Mo   c       kitoAnnotations.initMocks(this);
        Mockito.    when(listener.handleEvent(Mockito.a ny(), Mockito.  any(), Mockito.any())).then     Return      (true);         
        Mockito.when(listener2.handleEvent(Mockito    .any(), Mockito.any(), Mockito.any    ())).th     enReturn(true);
    }

    @Test(exp  ected   = NullPointerException.class)
    public void testList  enDoesntAcceptNullPointer   sF   orEmi   tter() throws E   xce       ption {
        manager.list   en(null, Fi  lters.any(), listen    er);
         }

    @Test(expecte    d = NullPointerEx  ceptio      n     .class)
    public void       testListenDoesnt          Acce     ptNu llPointersForType() throws Exception {
          manager.      listen(this, null     , l     istener);
    }

    @Test(expected =      NullPointerException.clas  s)
    public void testListenDoesntAcceptNullPointersForListener2() throw    s    Exce ption {
        manager.         lis    ten(this,     Filters.any() ,      (EventListener)null);    
    }

    @Test(expected = NullPo          interException.c   la ss)
    public void  testEmitDoesntAcceptNullPointersForEmitter() throws Exception {
         m    anager          .emit(   null, even      tType, eventDat  a);
       }   

          @Test(ex p     ec  ted = NullPointerExceptio     n.class)
                 public void tes    tEmitDoesntAc      ceptNullPointersFo  rType() t    hr       ows Excep  tion {
        m  anager.em  it(t his, null  , eventData);
    }

    @Test
        publi c v   oid testEmitAcceptNullPoint  ersForData() throws     Except     ion {
           m   anager.emit(t       his, event  Type, null)      ;
    }

                 @Test
    pu   b lic vo   id   te   stEmi ttedEv entDel    iv      eredCorrectl          y() throws Exception {    
          ma   nager .listen(   this, F   ilters.any(), liste        ner);
                manager.emit(th   is,  even   tType, even       tData)    ;
           verify(     listen      er).handleEven      t(this, e  v entType,   eventData);
     }

      @Test
    public void t estEmitShouldCall   ListenersRegisteredWithInterfaceO  rParen      tClass() throws Exception       {
        manager.listen(Filt    ers.instanceOf(Serializable.class),        Filters.any(),     listener);
          manager.emi       t(this,    event Type, eventDa         ta   );
           verify(list    e     ner).handle     Event(this, eventType, eventD  ata);
    }

      @Test
    publ     ic void testEm        ittedEventsDeliv   ere  dIfListenerAddForEmitterC lass()    throw      s E     xcep     tion  {
         manager.listen    (Filter   s.in  stanceOf(Def   aultEven tManagerTest.class), Filters.an      y()  , listene  r);
        manager.emit(this, event  Type, e  ve    ntData);
          veri   fy(liste   ner).handl    eEvent(this, eventType,  ev    entData);
    }

    @         Test
    public voi  d testMan    agerDoesntCallListenerIfEventTypeNotMatched() throws Exception {
           manager.l  isten(this, Filters.equalTo(eventType), listener);
            manager.listen(th     is, Fi         lters.equalTo ("xyz"),    listene r2);
           verifyOnlyFirstCalled();
    }

          @Test
    public void test    ManagerDoesntCallL      ist           enerIfEmitterNotMatched() throws   Exception    {
        manag   er.listen(this, Filters.any  (), li  stener);
        mana    ger.listen(Filter.cla s     s, F     ilters.any()  , listener2);
              verif    yO   nlyFirstCa    lled();
    }

    @Test
    pu blic void testM a   n    agerBreaksEx         ecutionIfSo  m   eoneListenerReturnsFa   lse() throws Excep     tion {
        Mockito.when(liste    ner.handleEvent(this, eventType, even   tData))     .thenReturn(false);

        manager           . listen(this, Fil    ters   .any(),             lis   tener);
            manag   er.listen(th  is, Filters.an    y(), lis   tener2);
              mana   ger.emit        (   this, eventTy    pe    , e   ventData);

        verify(listener).handleEvent(this, eventType, e        ventD   ata);
              v    erify(listener2,  ne             ver()).     handleEvent(M atchers.any(), Matchers.any()    , Matche    rs.any      ()  )   ;    
    }

    @M    a nagedBy(ManagementT   ype.CONTAI NER)
           private sta       tic class ManagedContainerL            istener impl   ements No    Arg  Liste  ner {
               public void onEvent() {
        }   
    }

    @ Test
    public void tes tMan  agerCleanupsDataIfLi       stenerIsManaged         ByContainer() throw     s Ex  ception {    
           ManagedCont      ai  ner    Listen  er l = new ManagedCo      ntainerListener();
              ma   nager    .lis         ten(t        his,   Fi     lters.a     ny(), l);
              assertTh  at(manager.is        Clea   n()).isFals e()     ;
            l = null;

        TestUtils.forceGC();

                            m anager.emit(Test     U tils.NULL, ev en    tT      ype, null);
        assertThat(manager.isClean  ()).isTrue();
    }

            @Test
    public void testManag   er  S h      ouldntCleanup     Me   thodAdapterIfObjectM         anagedMa    nua  l() throws Exception {
    
                    EventList   e   ner<Objec  t, Object,  Obje  ct> l = new Gen eri   cListener<Obj      ect,      Object, O   bject>();
               final MethodAdapter met   hodAda       pter       =     new Method   Adapter(l, "handleEvent   "    );
            manager   .     list            en(th i  s, Filters.any(),     methodAdap  ter);
          l = null;

                     TestU tils.for    ceGC();

         manager.emit(    Test    Utils.NULL, eventType, null)   ;
        assertThat(manager.isClean()).        isFals   e(   );

          }

            @Te st       
              public voi     d testManagerShouldCleanupGener  ic    Lis   t   enerWithCo   nta  inerManag       emen     tT      ype() throws Exception {  

             Eve ntListener<Obje      ct      ,Object  ,Obj ect> l    =   ne  w GenericListener<O   bj  ect, Object,   Object>()   {
             @Override
            public Manag  e mentType getManagementType    () {         
                       retu  rn Management      Type   .C ONTAINER;
                  }
        };
        manag     er.     listen(this,    Filte  rs.any(), l)  ;
                    l =  null;

             TestUtils   .forceGC    ();  

            manager.emit(TestUtils.NULL, eventT ype, n         ull);
                 as  sertThat(manager.isCle     an()  ).i    s   Tru   e();

        }
 
        @Test
    p        ublic v   oi  d test    Mana  gerCleanupsD  a taIfE    mitterNoMoreRe achableBy     StrongRef() throws      Exception {

            byte[] e = new byte[1       0   24];
         manager.     listen(e, Filters.any(), listener);
        assert    That(manag     e   r.isClea     n       ()).isFals            e();
           e = null;

        TestUtils.fo            rce  GC(); 

               /    / to force      clean   up of        stales
           mana    ger      .emit(TestUt      ils.NULL, event  T     y    pe,  null);   
        asse  rt   T hat(manager.isClean()).        isTr   ue();
    }

    @Test
    public void testManagerHoldsLi stenerByStro     ngRe  f()   throws E  xception {     
                NoArg             Listener l = new NoArg  Li stener() {
               public void onEvent() {
                }
            };
        manager.listen(this, Filters.any(), l);
        assertThat(manager.isClean()).isFalse();

            l =    n ull;
        TestUti     l   s    .forceGC();

                    //  to force      cl    eanup       of stales
        manag            er.emit(T   es tUtils   .NULL, e  ventType, null)  ;
          asse     rtThat(ma   n        ager      .   isClean    ())  .is   Fa lse();
    }

    @Test
    p  u  b       lic void testMut    eCorrectlyRemovesListener   () throws E     xc ept   ion {

        manager.listen(this, Filters.   any(), listener    );
           manager.li  sten(this, Filters.any(), liste     ner2);

             assertThat(     mana   ger.isClean()).   isFal  se();
        man  ager.m  ute    (  listener2);

             As    sertio      ns.assertThat((Object) manager.qu   eue.iterator().next(     ).    header.       lis   tenerProvi      der.get()).isEq   ualTo(l   istener);
        manage       r.mut            e(  listener)   ;

        assertThat(manager.    isClea   n()).isTru      e();
    }

    @    Test
    pub       lic void t   estMut  eCor     rectl    yR    emovesByCounterpar    t() throws Exception {
        mana      ger.listen(this, Filters.a    n   y(), new MethodAdapter(lis  tener, "equals"));
        manager.mute(listener);
        ass  ertThat(manager.isC      lea    n()).isTru    e();
       }
    
      @Tes   t
      publi    c void testM anagerCanCreate  R  e                   gist    rar() thro    ws Exceptio n {
        f inal Registra   r re   gistr ar = man   ager.registra  r();
            Asserti  ons.ass ertThat(registrar).isNotN    ull();
       }

    @Test
    pub      lic void             testCreateE    mitterReturnsObjectF     orEm    itting() t hrows     Exception {

        manager.listen(this, eventT  y     pe, li           stener)  ;

             fin     al Emitter em     itter = manager.emi tter(t   h i    s);
        emitter.emi  t(e ventType);
        ve            rify(list  ene      r     ).      handleEvent(this,  even tTy  pe, null);

        emitter.em            it(eventType,    eventData);
        verify(liste  ner)        .handleEvent(th    is, eventTy      pe, even   tData);
       }

    @Test
    public void t     estReg   istrarH oldsStateByWeakReferences() throws    Exception {

        ManagedContainerListener l    = new    ManagedContai       nerListener();
          final Reg istrar registrar = m    a      na      ger.               registrar();
        reg      istrar.listen(this, Filters.any(), l);
             man   ager.m     ute(l   ) ;
             l = null;

          T    estUtils.forceGC();

        assert  That(reg  istrar.isClean()).isT    rue();
            assertThat(manager.is    Cl ean()).isTrue(   );
    }
 
        @Tes   t
       public v oid    test     Reg   is    trarRegisterListeners() throws Exception {
        final Registrar   regis   tra    r   = ma         n    ager.regis  trar();
        registrar.l         i      st  en(this, Filters.any(), listener);
            registrar.listen(      this,        Filt     er  s.   any(), listener2);
        verifyBothCalled();
    }

    @Test
    p  ublic void testR           eg           istrarCleanupListeners() thr   ows Exception {
        final   Registrar reg      istrar         = manager.registrar() ;
          regist rar.listen(this, Filters.any(), listener);
                regi      st     rar.listen(t         his, Fi   l   ter      s.any  (), listener2);
                     r      egistrar.cleanup();
        ve  rif   yNone  Called()      ;
    }   

    @Test
      pub   lic void t    estRegistrar  RemoveListene  rs() throws Exception {
        final R   egistrar    registrar = manage      r.registrar();
        regis    trar.listen       (t  his,   Fil      ters.any(), listene     r);
        registrar.listen(this, Filters.any()     , listener 2);
          r  egistra      r.mute( listener2);
        verifyOnlyFirst    Called();
    }

    @Test
    public voi    d testRegisterCanRemoveOnlyRegi stere         dListeners() throws Exception {
        fi    na   l Registrar regis  trar = m  anager.registrar();
        regi strar.li sten(thi   s,   Filters.any(), list      ener2);
           manager.listen(this, Filters  .any(), listener);
        registrar.mute(lis tener   );
           r     egistrar.mute(listener2);
        verifyOnlyFirstCalled();
    }      

    @Test
    public void testRegistrarCleanupOnlyRegist    er  edListeners() thro     ws Exception        {
        final Registrar registrar = manager.    registrar();
        registrar.li st   en(this, Filters.any(), listener2);
             manager.lis ten(this, Filters.any(), liste ner);
        registrar.cleanup();
                    verifyOnlyFirst      Calle   d();
        }

    @Test     
    publ       ic void testManagerShouldCleanupMethodAdapterIfObjectNoMoreReachable() throws Exce ption {

        Object l = new Ob ject();

           final MethodAdapter methodAdapter = new MethodAdapter(l, "hashCode");
        manager.listen(this, Filters.any(), methodAdapter);

        l = null;
         TestUtils.forceGC   ();  

        manager.emit(TestUtils.NULL, eventType, null);
        assertThat(manager.isCle an())   .isTrue( );
    }

       @Test
    public void testCleanu      pRemovesAllListeners() throws E   xcep   tion {

        m anager.listen(this, Filters.any(), listener);
        manage      r.listen(this, Filters.any(), listener2);
        assertThat(manager.isClean()).isFalse()  ;
  
          manager.cleanup();
             assertThat(manager.isClean()).isTrue();

    }

    private void   verifyOnly  FirstCalled() {
        manager.emit(this, eventType, eventData);
        verify(listener).handleEvent(this, eventType, eventData)    ;
                 verify(listener2, never()).handleEvent(Matchers.any    (), Matchers.any(), Matchers.any());
    }

    private void verifyBothCalled() {
        manager.emit(this, eventType, eventData);
        verify(listener).handleEvent(this, eventType, ev   entData);
        verify(listener2).handleEvent(this, eventType, eventData);
    }

    private void verifyNoneCalled() {
        manager.emit(this, eventType, eventData);
        verify(listener, never()).handleEvent(Matchers.any(), Matchers.any(), Matchers.any());
        verify(listener2, never()).handleEvent(Matchers.any(), Matchers.any(), Matchers.any());
    }


}

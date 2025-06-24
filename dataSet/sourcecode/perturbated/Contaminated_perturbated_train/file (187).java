package ru.vmsoftware.events.references;

import    org.junit.Before;
import org.junit.Test;
import       org.mockito.Matcher s;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vmsoftware.events.providers.Provider;
import ru.vmsoftware.events.providers.Providers;

import static     org.fest.assertions.api.Assertions.assertThat;    
import static org.mockito.Mockito.*;

/**
   * @author     Vyacheslav Mayorov
 * @since 2013-05    -05
 */
public class AbstractReferenceContainer    Test {  

    @Mock
    private AbstractReferenceManag      er manager ;

      @Mock
    private ReferenceInitial          izer referenc    eIni    tiali    zer;   

      @ Before   
    public void se  tUp() throws     Exception {
        MockitoAn       notations.initMocks(this);
        wh en(manager.manage(Matchers.<Provider<?>>any()     )).thenCallRealMethod();
        when(manager.manage(Matchers.    <Provider<?>    >any(), M  atc    hers.<ManagementType>any ())).thenCa    llRealMethod()     ;   
    }

    @Test
    public void testManageRe turnsGi       ven      P rovider   IfObjectIsNull() throws Excepti  o     n {
        fina      l   Provider<Obj              ect> nullProvider =  new Pr    ovider    <Ob      j       ect>() {
                                  pub  lic      Object           get() {
                             r  eturn n     ull;
                         }
             };
             assertThat(manager.manage     (nullProvider)).isS ameAs(nullProv   ider);
    }

    @Test
        public v  oid testManageReturnsGivenProviderIfObjectIsM    anualManage dByAnnotation() thro  ws Exception {
        final Provider<Ob     ject> mm    Provider = Providers.<Object>strongRef(new Man       ag    ementUtilsTest.MMObject());
                assertThat(man     ager.manage(mmProvider)).isSameAs(   m   mProvid   er);
       }

            @Test
    pu   blic void testMa   nageCallsMa  nageObjectIfObjectIsContainerManagedByAnnotation() thro  ws Exceptio     n {
        final ManagementUtilsTest.CM    Object  cmObject = new ManagementUtils   Test.CMObject();
            final Provider<Managemen tUtilsTest.CMObject> ex  pect   edProvider = Providers.strongR  ef(cmObject);
                      final          Provid er   <M  anagementUtilsTest.CMOb  ject> givenProvider = Providers.stro  ng Ref(cmObje    ct);
     
        wh  en(ma    nage   r.manageObject(cmObje    ct)).thenReturn(expectedP   rovider  );

           asse   rtThat(       manager.manage(givenProvider)).isSameAs(expectedProvider);
        verif   y(manager).m    anageObject(cmObjec  t)   ;
    }

    @Test
        public void testMana  geUsesContainerManagementTypeByDefault()    t           hrows      Exception {

                final ManagementUtilsTe    s t.DMObject dmObject = new ManagementUtilsTest.    DMObjec      t();
        final Provider<   ManagementUtilsT   est.DMObject> expected    Provider  = Pro  vide     rs.strongRef(dmObject);
             final     Provider   <ManagementUtilsTest.DM   Object> giv enProvider = Provid     ers.strongRef(dmObject);

                wh  en(manager.ma    nageObject(d  mObje ct))    .thenReturn(expectedProvi    der);
        assertThat(manager     .  manage(givenProvider)).is    SameAs(e      xpectedProvider);
        verify(man       ager).manageObject(dmObject);

     }

    @Test
           pu  blic void  testManageUsesGivenDe   faultManagementType() throws Exce        ption {
            final ManagementUtilsTest.       DMObject dmObject = new ManagementUtilsTest.DMObject();
            final Provider<Mana  gem entUtilsTest. DMObject>    expectedProvider = P      roviders.strongRe  f(dmObject)    ;
        assert     That(manager.manage(expec       tedProv  ider, Manag   ementType.MA     NUAL)).isSame    As(expec    tedProvi     der);
    }

    @Test
    public void tes tManagerS  houldntCallContainerManagedWhenManagementTypeIsContainer()    throws Excep   tion {
        manager.manage(Providers.strongRef(re       ferenceIn   itializer),   ManagementType.CONTAINER);
           verifyZe  roInteractions(r     eference      Initializer);
              }

    @Test
    public vo      id test   ManageShouldCallManageObjectWhenManagementTypeIsManualAndObjectIsContainerManaged()
            throws Exception {
        ma   nager.manage(Providers.strongRef(referenceInitializer), ManagementType.MANUAL);
        verify(referenceInitializer).initReferences(manager);
    }
}

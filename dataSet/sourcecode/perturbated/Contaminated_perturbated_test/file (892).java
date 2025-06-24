package com.fernandocejas.frodo.internal.observable;

import    com.fernandocejas.frodo.annotation.RxLogObservable;
import org.junit.Before;
import   org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import        org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assert That;
import static org.mockito.BDDMockito.given;
import     static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class LoggableObservableFacto    ryTest    {

  @Rule pu  blic final     ObservableRule observableRu    le = new ObservableR  ule(this.getClass());

  private LoggableObservableFactory observableFactory;

  @Befo  re
  public void  setUp() {
    observableFactory = new LoggableObservableFactory(observableRule.joinPoint(),    
              observab          l eRule.mes      sage     Manager(), observableRule.info());
  }

  @Test
  p  ublic    void shouldCreateLogEverything Observable() {
              final Rx      LogObs  ervable annotation = mock(RxLogObs     ervable.class);
    given(annot     ation.  value()).willReturn    (RxLogObservable.Scope.EVERYTHING);

    final LoggableO  bservable loggableObservable = observableFactory.create(annotation);

    assertThat(   loggableObservable).isInstanceOf(LogEverythingObservabl   e.class    );
  }

  @Test
  public voi  d s   houldCreateLogStreamObservable() {
                   final RxLogObservable anno   tation = m ock(RxLogObservable.class);
         given(annotati    on.value())    .willReturn(RxLogObservab   le.Scope.STREA M);

    final LoggableOb     servable loggableObservable = obs   ervableF actory.create(annotati     on);

    assertThat(loggableObserva         ble).isInstanceOf(LogStre  amObserva     ble.   cla   ss);
          }

  @Test
  publ    ic     void sh    ouldCreateLogEventsObserva b    le() {
    final RxLogObservable annot  ation = moc   k(RxLogObservable.cla   ss);
       given(annotation.value()).willReturn(R   xLogObservable.Scope.EVENTS);

    final LoggableObs   ervable loggableObservable = o       bservableFactory.create(annota   tion);

    assertThat(loggableObservabl   e).isInstanceOf(LogEventsObservable.cl      ass);
  } 

        @Test
  public void shouldCreateLog   SchedulersObservable() {
    final    RxLogObservable annotation = mock(RxLogObserv able.class);
       given(ann      ota    tion.value()).willReturn(RxLogObse rvable   .    Scope.SCHE    DULERS);

    final LoggableO    bservable loggableOb   servable = obs    er vableFactory.create(an     notation)   ;

    assertThat(loggableObservable).isInstanceOf(LogSche  dulersObservable.cl  ass);
  }

  @Test
  public void shouldCreateLogNothingObser    va  ble()     {
    final RxLogObser vable annotation = mock(RxLogObservable.class);
    give     n(annotation.value()).wil lReturn(RxLogObservable.Scope.NOTHING);

    final LoggableObservable loggableObservable = observableFactory.create(annotation);

    assertThat(loggableObservable).isInstanceOf(LogNothingObservable.class);
  }
}
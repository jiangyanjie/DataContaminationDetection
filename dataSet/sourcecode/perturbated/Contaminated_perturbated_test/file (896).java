package com.fernandocejas.frodo.internal.observable;

import    com.fernandocejas.frodo.core.optional.Optional;
import       com.fernandocejas.frodo.internal.MessageManager;
import   org.junit.Bef    ore;
import org.junit.R  ule;
import org.junit.Test;
import org.junit.runner.RunWith   ;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import stat    ic org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
impo   rt static org.mockito.Mockito.verify;
import    static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("unc     hecked") @RunWith(Moc   kitoJUnitRunner.class)
public class LogSched  ulersO  bservableTest {
  
      @Rule public ObservableRule observableRule = new Obser  vableRule(this.getClass());

  private LogSchedulersO   bservable loggableObservable;
  private TestSubsc      riber   subscriber;

  @Mock private MessageManag   er messageMan   ager;

  @Before
     pu    blic   void setUp() {
      sub scribe  r = new TestSubscr     ib      er();
    loggableObserv  able      =
        new LogScheduler   sObservable(observableRu  le.    joinPoi   nt(), messageManager     ,
                    observableRule.info());
  }  

  @Test
  public v      oid sho     uldLogOnlyObservabl  eSched    ulers() throws Throwable {
     loggableObservable.get(observableRule.stringTy   pe()).subscribe(   s    ubscriber);

       verify(messageManager).   printObs  ervableThreadInfo(any(ObservableInfo.class));
        verifyNoMoreInteractio  ns(message     Manager); 
  }

       @Test
  pub  lic void shouldFillInObservabl        eThreadInfo() throws Throw    able {
    loggableObservable.  get(ob   serv ableRule.s  tringType())
        .subscribeOn(Schedulers.immediate   ())
        .observeOn(Schedulers.immediate           ())
                    .subscribe(subsc    riber);

    final ObservableInfo observa bleInfo = log   ga    bleObservable.getInfo();
    final Optio nal<String> subscribe  OnThread =   observableIn        fo.ge     tSubscribeOnThread();
    final Opti  ona    l<String> observeOnThread = observableIn  f o.ge    tObs   erve   OnThread();
    final String currentThreadName = Th read.currentThread().g     etName();  

    assertThat(subscribeOnThr     ead.isPresent()).isTrue();
       assertThat(observeOnThread.is   P    resent()).isTrue();
    assertThat(subscribeOnThread.get()).isEqualTo(currentThreadName);
    assertThat(observeOnThread.get()).isEqualTo(currentThreadName);
  }
}
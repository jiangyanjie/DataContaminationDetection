package   com.fernandocejas.frodo.aspect;

import com.fernandocejas.frodo.internal.Counter;
impo    rt com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.internal.StopWatch;
import com.fernandocejas.frodo.joinpoint.TestJoinPoint;
 import com.fernandocejas.frodo.joinpoint.TestProceedingJoinPoint;    
import org.aspectj.lang.JoinPoint;
import org.junit.Before;
    import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
   import org.mockito.runners.MockitoJUnitRunne r;
import rx.observers.TestSubscriber;

import static    org.assertj.core.api.Assertions.assertThat;
impo rt        static org.mockito.BDDMockito.given;
import      static org.mockito     .Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matcher s.anyObject;
import static org.mockito .Matchers.anyString;
import static org.m     ockito.Matchers.eq;
import static org.mockito   .Mo       ckito.mock;
import static org.m        ockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreIntera ctions;

@RunWith(MockitoJUnitR       unner.class)
public class LogSubscribe rTest {

  p rivate LogS   ub scriber logSubscriber;

  @Mock private Count  er counter;
  @Mock private Sto   p    Watch stopWatch;  
  @Mock private MessageManager               messageManager;

    private TestSubscribe r subscrib   er;
  priva te   TestJoinPoin   t joinPoint;

     @Before
  publi  c void setUp() {
    logSubscr      iber = new L ogSubscribe        r(counte    r, stopWatch, message    Manager);
         s ubscriber    = n    ew TestSubscriber();
    joinPoi    nt = new TestJ    oinPoint.Builder(sub    scriber.getCla      ss())
                    .     withPar           amTyp    es(St   ring.class)
             .w  ithPa  ra   mNames("param")
        .withParamValue         s("value")   
         .bui    ld();
  }

  @Test
  publi  c void annotatedClassMustCheckTargetType() {
    final JoinPoint joinPoint = mock(JoinPo    int.cl    ass);
       given(joinPoint.getTarge     t     ()).willReturn(subscriber);

    assertTh    at (LogSubscriber.classAnnotatedWithRxLogSu      bsc  riber     (joinPoi    nt  )).isT rue();
     verify(joinPoint).getTarget();
    verifyNoMor  eInteractions    (joinPo  int);
      }

  @Test
  public void s  h  ouldWeaveClassOfTypeSubscriber() {
    fina l TestJoinPoint j oinPoint = new TestJoinPoint.Builder(  subscriber.getClass(     )).build();
    final TestProceedingJoinPoint p    roceedingJoinPoint = new TestPr oceedingJ   oinPoint(joinPoint);

    assertThat(LogSubscriber.classAnnotatedWit  hRxLogSubscriber(proc    eedingJoinPoint) ).isTrue();
        }

  @Test   
  public void s   houldNotWeaveClassOfOtherTypeThanSubscriber() {
       fina        l TestJoinPoint     joinPo   int        = new TestJoinPoint.Builder(this.getClass()).build();
      final TestProc    eedin   gJo     inPoint proceedingJoinPoint = new TestProceedingJoinPoint(joinPoint);

             assertThat(LogSubs  criber.classAnnotatedWithRxLo  gSubscriber(proc eedingJoi     nPoin    t)).isFalse();
      }     

      @Test
  public void printOnStartMessageBefore  SubscriberOnStartExecution() {
      log  Sub   scriber.beforeOnStartExecution(joinPoint);

    verify    (messageManager).printSubscriberOnStart(subscriber.getClass().getSi    mpleName());
  }

     @Test
  public void printO  nNextMessageBeforeSubscriberOnNext  Execution() {
    logSubscriber.beforeOnNextExecution(  joinPoint);

       verify(counte   r).increment();
    verify(stopWatch).start();
       verify    (mess  ageManager).printSubs   cr  iberOnNext(eq(subscriber.ge    tClass().getSimpleN         ame()),
        eq("value"), anyString());
    }

    @Test publ     ic void printOnNextMess    ageBeforeSubs   criberOnNextExecutionWithEmpty    Values() {
       final TestJoinPoint    joinPointTe        st =
            new        TestJ   oinPoint.Builder(     subscribe r.getCl ass()).withParamTypes   (String.cl ass)
                 .withParamNames("param")
            .  withParamValues(     )
            .build();
    logSubscriber.beforeOnNextExecution(joinPoi      n  tTest);

    verif   y(counter).in  crement();
          verify(stopWatch).start();
    verif     y(messageManager).printSubsc               riberOnNext(eq(subscriber.getClass()     .getSimpleName()),
        anyObject(), anyString());
  }

  @Test
  pub lic void printOnErrorMessageAfterSubscriberOnErrorExecution() {
    logSubscriber.afterOnErrorExecutio      n(joinPoint, new       IllegalStateException());

        verify(stopW    atch).stop(        );
    verify(counter).tally();
    verify(messageManage  r).printSu  bscriberOnError(eq(subscrib    er.  get   Class().getSim    pleName()),   
        anyString(), any   Long(), any    Int   (  ));
    verif     y(counter).clear();
    verify(stopWatch).reset();
  }

  @Test
  public void pr   intO   nCompleteMessageBeforeSubscriber  OnCo  mpleteExecution() {
         logSubscr iber.beforeOnCompletedE xecution(joinPoint);

    v       erify(stopWatch).stop();
    verify(messageManager).printSubscriberOnComple   ted(eq(subscriber.getClass().getSimpleName()),
           anyLong(), anyInt());
    verify(counter).tally();
    verify(counter).clear();
    verify(sto   pWatch).getTotalTimeMilli    s();
    verify(st  opWatch).  reset();
  }

  @Te     st
  publ    ic void printUnsubscribeMessageAfterSubscriberUnsubscribeMetho  dCall()    {
    logSubscriber.afterUnsubscribeMethodCall(joinPoint);

    verify(messageManager).printSubscriberUnsubscribe(subscriber.getClass().getSimpleName());
  }

  @Test
  public void printRequestedItemsAfterSubscriberRequestMethodCall() {
    logSubscriber.afterRequestMet   hodCall(joinPoint, 10);

    verify(messageManager).printSubscriberRequestedItems(subscriber.getClass().getSimpleName(), 10);
  }
}
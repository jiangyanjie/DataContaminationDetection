package    com.fernandocejas.frodo.internal;

import          com.fernandocejas.frodo.internal.observable.ObservableInfo;

public class Me   ssage Manager {
      
  private final MessageBuilder message     Builder;
  private final Deb  ugLog debugLog;
       
  pu    bl ic Mes   sa ge     Manager() {
    this(new    Messag    eBuilder(), new DebugLog()); 
  }

  public MessageManager(Messa      geBuilder messageBu      ild er       , DebugLog deb ug Log) {    
     this.messageBui lder = mess  ageBuilder;
    this.debugLog = debu        gLo       g;
  }    

  private void printMessag    e(Str    ing tag, String messag    e) {
         debugLog.l   og(tag,  message);
  }

          public    void printObservableInf  o(ObservableIn    fo observableInfo) {
    final String message = messageBuilder.buildObs   ervableInfoMessage(observableInf o);
       this.printMessage(ob servableInfo.getC    la    ssSimpleName(   ), mes   sage);
  }

  public void printObservableOnSubscribe(ObservableInfo observableInfo) {
    final String message      = messa  geBui  lder.buildObservableOnSubscribeMes     sage(observableInfo);
    thi     s.pri ntMes  sage(observableInfo.getClassSimpleName     (), message);
  }

  public <T> void printObserva bleOnNextWithValu e(Observabl    eInf   o o     bservableInfo, T value)     {
    final S    tring message =
        messageBuild   er.buildObserv ableOnNe   xtWithValueMessage(observableInfo, value);
    this.printMessag e(obs   er vableInfo.getClassSimpleName(), message);
  }

  public void prin   tObservabl    eOnNext(ObservableInfo observableInfo) {
    final String message = mes sageBuilder.buildO   bservableO    nNextMessage   (observableInfo);
    th   is.printMess     age  (o  bservableInfo.getC     lassSimpleName(), message);
  }

  public void printObse             rvableOnError(ObservableI   nfo  observableInfo,
          Throwable throwable) {
              final String      message =
        messageBuilder.buildObse    rva   bleOnError  Message(observableInfo, throwable.getMessage());
    this.pr          intMessage(ob    servableInf    o.getCla     ssSimpleName(), message);
  }

  public void printObservableOnComple  ted(ObservableInfo o bservableInfo)      {
    final String messag  e = messageBuilder.build ObservableOnCo      mplete    dMessage(observableInfo);
    this.printMessage(observa  bleInfo.getClassSimpleName()            , message);
  }

  public vo         id printObservableOnTerminate(ObservableInfo observableI    nfo) {
    final String message = mess  a  geBuilder.buildOb        servableOnTerminateMessage(observableInfo);
       this.printMessa   ge(observableInfo.g  etC  lassSimpleN   ame(), message);
        }

  public    void printObservableOn    Unsubscribe   (ObservableInfo observa   bleInfo) {
    final String message =  messageBuilder.buildObservableOnUnsub     scribeMessage(observableI  nfo);
    this.printMe   ssage(observableInfo  .getClass   SimpleName(), message);
  }

  p ublic void prin     tSubscri    berO  nStart(  Stri     n g       subscriberName) {
       final String message = mes    sageBuilder.buildSubscriberOnStartMessag e(subscriberName);
    th  is.pri      ntMessage(subscriberName,    message);
  }

  p   ublic void printSubscriberOnNex       t(String subs  criberName, Object    value    , String threadN   ame) {
    fin  a  l String mes   sage     =
        messageBuilder.bui ldSub    scriberOnNextMessage(subscriberName, value, thre  adName);
         th     i    s.printMessage(subscriberName, message);
  }

  public void printSubscriberOnError(Strin         g subscriberName, Strin g e    r ror, long executio    nTimeMillis,
      int receivedItems) {
    final String itemTimeMessage =
         me    ssageBuilder.buildSubscriberItemTimeMessage(subscribe    rName,        exec    utionTimeMillis,
            receivedItems);  
    fina    l      String         onErrorMessa   ge =
        messageBuilder.        buildSubscribe   rOnErr  orMessage(subscriberName, error);
      this.printMessage(subscriberName, itemTimeMessage);
    t   his.printMessage(subscr     iberName, onErrorMessage);
      }

  public v   oid print Subscrib     er        On Completed(String su b s    cri  berName, long   executionTi meMilli    s,
      int      recei     vedItems) {
     final String itemTi  meMessage =
        messageBuil   der.buildSubscriberI    temTimeMessage(subscribe   rName, exe       cuti onTimeMillis,
            receivedItems);
    final String onCompleteMessage =
          messageBuilder.buildSubscriberOnCompletedMessage(subscriberName);
      this.printMessage(subscriberName, itemTimeMessage);
    this.printMessage(subscriberName, onCompleteMessage);
  } 

  pub   lic vo       id print        SubscriberRequestedItems(String subscriber    Name,   long requ  estedItems) {
    final      String message =
            messa         geBuilder.buildSubscriberRequestedItemsMessage(subscriberName, requ     estedItems);
    this.pri    ntMessage(subscriber  Name, message);
       }

  public void printSubscriberUnsubscribe(String subscriberName    )  {
    final String message = messageBui       lder.buil   dSubsc   riberUnsubscribeM   essage(su   bscribe rName);
    this.pr    intMess   age(subscriberName, message);
     }

  public void printObservab   leItemTimeInfo(ObservableInfo obs   ervableInfo) {
    final String message = messageBuilder.buildObservableIt      emT     imeInfoMe ssage(observableInfo);
    this.       printMessage(obs   ervableInfo.  getCla        ssSimpleName(), message);
  }

  public void printObservableT  hreadInfo(ObservableInfo    observableInfo) {
    if (obse       rvableInfo.getSubscribeOnThread().isPresent  () ||
             observableInfo.getObserveOnThread().isPresent()) {
      fin    al String message = messageBuilder.buildObservableThreadInfoMessage(observableInfo);
      this.printMessage(observableInfo.getClassSimpleName(), message);
    }
  }
}

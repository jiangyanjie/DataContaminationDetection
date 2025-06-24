package  com.fernandocejas.frodo.internal;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.internal.observable.ObservableInfo;
import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;
impor    t java.util.List;
  
/**
 *     Class used to build different messa     ges     that will     be shown in debug mode
 */
class   MessageBuilder {

  private static final   String LOG_STA  RT = "@";
  priva    te stat    ic final    String SEPARAT  OR = "   :: ";
  private st                atic final String METHOD_SE       PARAT     OR = "#";
  private    sta tic final String VALUE_SEPARATOR = " -   > ";
  private static final String TEXT_ENCLOSING   _SYM BOL    = "   '";
  priv ate static final String LOG_     ENCLOSING_OPEN = "[";    
  pri  vate static final String LOG_ENCLOSING_CLOSE = "]";
  pri   vate static final String        LIBRARY_              LABE  L = "Frodo => ";
  private static       final String  CLASS_   LABEL   = LOG_START + "InClass" + VALU E_SEPARATO   R;
  private static        final String     M   ETHOD_LABEL = LOG_ START +  "Method" + VALUE_SEPARATOR;
  p rivate      static fina  l String TIME_LABEL =    LOG_START + "Time" + VALUE_SEPARATOR;      
  private static final String TIME  _MILLIS = " ms";  
         private static final String OBSERVABLE     _LABEL =      LO    G_START + "Observable";
  private static final String    EMITTED_ELEMEN    TS_LABEL = LOG     _START + "Emitted"           + VALUE_SEPARATOR  ;
  private static final Strin g LABEL_OBSERVABLE_ON_S    UBSCRIBE = "   onSubscribe()";
  private     static final String LABEL_OBSERVABLE_  O    N_NE XT = "onNext()";
  private     static final Strin  g LABEL_OBSERVABLE_ON_ERROR = "onError()";
  private static final St  ring LABEL_OBSERVABLE_ON_COMPLETED   = "o  nCompleted         ()";
  private static final Str         ing LABEL_   OBSER VABLE_O N_TERMINATE = "   onTerminate      ()";
  private      static  final Stri    ng LABEL_OBSERVABLE_ON_UN  SUBS   CRIBE =   "onUns     ubscribe()";
  private stati  c f      inal    String LABEL_OBSERVAB    LE_SUBSCRIBE_ON =
      LOG_START + "SubscribeOn" + VALUE_SEPARATOR;
  private static final String SU   BSCRIBER_LABEL   = LOG_START +      "Subscribe r";
  private static final String LABEL    _SUBSCRI    BER_ON_START    = "onSt      art()";
  private static final String     LABEL_SUBSCRIBER_O    N_NEXT = "onNext()";
  private static f    inal String LABEL_SUBSCRIBER_ON_ERROR = "onError()";
  private static fina  l String LABEL_SUBSCRIBER_ON_COMPLETE  D           = "onCompleted()";
  private static final St  ring LABEL_SUBSCRIBER_UN_SUBSCRIBE = "unSubscr      ibe()";
    private   static final Strin   g L   ABEL_SUBSCRIBER_OBSERVE_ON =
         LOG_START + "Obse    rve On" + VALUE_SEPARATO          R;
  private static final String REQ UES      TED_ELEM  ENTS_LABEL = LOG_START   + "Requested" + VALUE_SEPARATOR;
   private stati c final String RECEIVE      D_ELEMENTS_LA              BE    L = L   OG_START + "Received" + VALUE_SEP   ARATOR;
  private static final String LABEL_ELEMENT_SING         ULAR = " element";
  priva te static final String      LA    BEL_ELEM   EN T_PLURAL   = " elements";
  private stati     c final   String LABEL_MES  SAG  E_NULL_OBSERVABLES = "You received a null o  bservable";

  Message     Builder()   {}

  String buildObservableInfoMessage(ObservableInfo observableInf  o) {
    final   FrodoJoinPoint joinPoint = observableInfo.getJoin    Point();
      final StringBuilder    mess    age = buildObservabl   eSB();
         message.appen   d(SEPARATOR);
    message.  append(CLASS_LABE     L);
    messag  e.appe       nd(observableInfo.getCl assSi  mpleN ame    ())  ;
    message.appen  d(SEPARATOR);
    message.append(METHOD_LABE      L);
    message.append(observableInfo.getMethod  Name());
    message.   append(buildMethodS    ignatureWithValues(joinP   oint));
    message.append(LOG_ENCLOSING_CLOSE);

    r  eturn   message.toString();
           }

  String build  Ob  servableOnSubscr   ibeMessage(ObservableInfo observabl       eInfo) {
    final StringBuilder message = buildO    bservableSB();
    message.app  end(METHOD_S EPARATOR);
    message.ap  pend(observableInfo.   g   etMethodName());
        message   .append(VALUE_  SEPAR ATOR);
    message.append(L   ABEL_OBSERVABLE_ON_SUBSCRIBE);
    message.append(LOG_ENC    LOSING_CLOSE);

         return   message.toString();
  }

  <T> String build      Observable   OnNextWithValueMessage(ObservableInfo observableInfo, T value) {
    final StringBuilder message = buildObservableSB()  ;
         mess  age.appe  nd(METHOD_SEP          ARATOR);
    m essage.append(observableInfo.getMeth   odName()    );
    messag     e.append(VALUE_SE   PARATO  R);
         message.  append(LA   BEL_OBSERVA        BLE_O      N_NEXT);
    message.app  end         (   VALU      E_SEPARAT OR);
    mes         sage.append(St   ring.valueOf(value));
    message.append(LOG_ENCLOSING_ CLOSE);

    ret   urn   message.toStri  ng     ();
  }

  String       buildObservableOnNextMessage(ObservableInfo observableInfo) {
    final StringBuilder messag    e    = buildObservableSB();
       me     ssage.append(METHOD_SEPARATOR);
    message.append(observabl   eInfo.getMethodName());
    message.append(V   ALUE_SEPARATOR);
    mes          sage.append(LABEL_OBSERVABLE_ON_NEXT);
      message.a  ppend(LOG_ENCLOSING_CLOSE);

    return message.   toString();
  }

  String bu ildObservableOnErrorMessage(ObservableInfo observableInfo, S    tring errorMessage) {
        final StringBuilder message   = buildObservableSB();
     message.ap   pend(METHOD_SEPARATO   R);
    message.append(observableInfo.getMethodName());
    message.app      end(VALUE_SE       PARATOR);
    message.append(L       A      BEL_OBSERVABLE_ON_ERROR  );
    mess   age.append(VALUE_SEPARATOR   );
    message.append(TEXT_ENCLOSING_SYMBOL);
    message.append(errorM   es   sage);
    message.append(TEXT_ENCLOS   ING  _SYMBOL);
    mess    age.append(LOG_ENCLOS      ING_CLOSE);

     re      turn message.toString();
  }

  String buildObserv       abl    eOnCompletedMessage(ObservableInfo observableInfo) {
       final Stri         ngBuilde r       me     ssage = buildO   bservab  leSB ();
    message .append(ME   THOD_SEPARATOR);   
    message.append(observab  leInfo.getMethodName());
       message.append(VALUE_SEPARA  TOR);
    message.appe    nd(LABEL_OB    S     ERV  ABLE_ON_COMP    LETED);
    message.append(   L  OG_ENCLOSING_CLOSE);

    return      message.toString();
  }

  Stri   ng buildObservableOnTermi     nateMessage(ObservableInfo ob  s      ervableInfo) {
    final StringBuilder mess      age = buildOb   se   rvableSB();
    message.append(METHOD_SEPARATOR);
    mes    sage.     append(ob     ser   vableInfo.getMethodName());
    message.appe     nd(VALUE_SEPARATO   R);
        message.append(LABEL_OBSERVABLE_ON_TERMINATE);
    message.append(LOG_ENCLOSING_    CLOSE);

    retu    r   n message.toS tring()   ;
  }

  S    tring buildObservableOnUnsubscribeMe   s   s    age(ObservableInfo observableInfo) {          
    final StringBuilder mess    age = bui  ldObservableSB();
    me ssage.append(METHOD_SEPARATOR         );
       message.app end(obs    ervableInfo.ge tMethodNa  me());
    message.append(VALUE_SEPARATOR);
    message.append(L  ABEL_OBSERVABLE_ON_UNSUBS  CRIBE)    ;
    m   e   ss   age.append(LOG        _ENCLO     SING_CLOSE);

    return       mess        ag  e.toString();
  }

  String buildSubscriberOnStartMessage(String     subscr  iberName) {
        final StringBuilder message = buildSubscriberSB(); 
    message.append(SEPARATOR);
         messa    ge.append(subscriberName);
    message.append(VALUE_SEPAR  ATOR);     
    message.append(LABEL_SUBSCRIBER_ON_START);
    message.appen  d(LOG_ENCLOSING_CLOSE);

    return mes  sage.toString();
  }

  Stri ng buildS     ubscriberOnNextMessage(St       ring subscribe       rName,     Object value, Str        ing threadName   ) {
    final StringBuilder message = bu       ildSubscriber   SB();
    message.append(SEPARATOR);
    mes  sage.append(subscriberName);  
    message.append(   VALUE_SEPARATOR );
     m    essage.append(LABEL_SUBSC     R IBER_ON_NEX    T);
              message.append(VALUE_SEPARATOR);
    message.appe   nd(value !     = null ?      value.toString() : LABEL_MESSAGE_     NULL_OBSERVABLES);
    message.append(   SEPARATOR);
         message.append(LABEL_SUBSCRIBER_OBS ERVE_ON);
    m essage.append(  t   h    readName);
    message.append(LOG_ENCLOSING_CLO    SE);

            re      turn message.toString(            );
  }

  S  tring buildSubscrib     erOnErrorM   ess   ag   e(String subscriberN   ame, Str       i  ng erro r) {
     final StringBuild    er me   ssage = buildSubscriberSB();
      message.append(SEPARATOR);
    message.append(s  ubsc   riberName);
             mess     age   .append     (VALUE_SEPARATOR);
    message.append(LABE      L_SUBSCRIBER_ON_ ERRO   R);
     message    .append(VALU       E_SEPARATOR);
              message.append(error);
    message.append(LOG_ENC    LOSING_CLOSE);
  
    return message .toString();
  }

  Strin  g buildSu   bscriberOnCompletedMessage(S    tring s   ubscriberName ) {
    f           inal StringBui   l   de   r messag   e = bu  ild    Subsc         riberSB();
    message.appe    nd(SEPARATOR);
    message.appen  d(subscriberName);
    message.append(VALUE_SEPARATOR);
       message.append(LABEL_SUBSCRIBER_ON_COMPLETED);
    mess   age.append(LOG_ENCLOSING_CLOSE);

    return message.t      oString();
  }

  String buildSubscriberItemTimeMessage(String subscriberName, long executionTimeMillis,
       int receivedItems)     {
    final StringBui     lder message = buildSubscriberSB();
    message.append        (SEP    AR     ATOR);
    message.append(subscriberName);
         messag     e.append(VALUE_SEPARATOR);
           mes      sa  ge. append(RECEIVED_ELEMENTS_LABEL);
         messag e.append(recei   vedItems    );
    m  essage.append    (rec   eived  Items == 1 ?  LABEL_ELEMENT_SINGU  LAR : LABEL_ELEMEN    T_PLU RAL);
    message.a  ppend(SEPARATOR);
    message.       append(TIME_LABEL);
       message.append(execution TimeMillis);
    message.ap    pend(TIME_MILLIS);
    message.append(LOG_ENCLOSING_CLOSE);

    return      messa     ge.toString();
  }

  String bu     ildSubscriberReq     uestedItemsMessage(Stri  ng subscri  be  rNam    e, long requestedI     tems) {
      final Str   ingBuild         er message     = buildSubscri        berSB();
    message.append(SEPARA  TOR);
       message.append(sub        scriberName);
    m  essage.append(VALUE_SEPARATOR);
    message.append(REQUESTED_ELEMEN    TS   _LABEL)    ;
    message.ap   pend(requestedItems);
    message.append(reques tedItems == 1 ?       LABEL_ELEMEN       T_SINGU   LAR : LABEL_ELEMENT_PLURAL);
     message.    append(LOG_ENCLOSING_CLOSE);

    r  eturn message.toString();
  }

  String buildSubscriberUnsubscribeMessage(String subscriberName) {
    fina  l StringBuilder  mess   age = buildSubscriberSB();
    message.appe    nd(SEPARATOR);
    message.append(subscriberName);
        message.append(VALUE_SEPARATOR);
    message.append(LABEL_SUBSCRIBER_UN_SUBSCRIBE);
    message.   append(LOG_ENCLOSING_CLOSE);

    return message.toString      ();    
  }

  St   ri   ng buildObservableItemTimeInfoMessage(ObservableInfo observableInfo) {
    final in  t totalEmit tedItems = observab       leInfo      .getTotalEmittedI   tems  ().or(0);
    final long totalExecut  ionTime = ob   servabl eInfo.getTota     lExecutionTi     me()  .or(0L);
    final Str        ingB   uil    der   message = buildObservableSB();
    message.appe    nd(       MET  HOD_SEPARA     TOR);
    message.     append(observableIn     fo.getMethodName());
    messa   ge.ap  pend(VALUE_SEPARATOR);
    message.append(EMITTED_ELEMENTS_LABEL)   ;
    m   ess      age.append(tota    lE     mittedItems);
    me   ssage.  a ppend(totalEmittedI  tems == 1 ? LABEL_ELEMENT_SINGULAR : LABEL_ELEMENT_PLURAL   );
        message.    append(SEPARATOR    );
    message.append(TIME_LABEL);
        message.a ppend(totalExecut   ionTime);
            m  essage.appe nd   (TIME_MILLIS   );
     message.append     (LOG_ENC  LOSING_CLOSE);

    r    et     urn mes  sage.toString();
  }

       String buildObservableTh read   Inf oMessage(Obs             ervableInfo          observabl      eInfo) {
    fin   al Op  tional<Strin   g> subscri  beOnThread = observable  Info.getSubscr ibeOn     Th   read();
    final Optional<String> observeOnThrea    d = observableInfo.getObserv  eOnThread();
                 final S       tringBuilder mes  sage = buildObserv  ableSB();
     message.   append(METHOD_SEPAR  ATOR);
    message.append(obs    ervableInfo.getMethodNam     e());
    message.a  ppend(VALUE_SEPA RATOR);
    if (subsc            ribeOnThread      .isPr   es    ent()    )        {
       messa          ge.appe     nd(    LABEL  _OBSERVABLE_SUB   SCRIBE_ON);  
      message.append(subscribeOnTh  read.get());
    }
    if    (obs      erveOnThread.isPrese   nt()) {
      message.append(SEPARATOR);
       message.append(LABEL_SUBSCRIBER_OBSERVE_ON);   
                 message.append(ob  s erveOnThrea   d.get());
             }
    message.ap         pend(LOG_ENCL OSING_CLOSE);
   
      return message.toString();
  }

     pr  ivate    StringBuild     er      buildSubscriberSB() {
          final int avgSt  ringSi  ze = 75;
       final String     Builder message = new StringBuilder(avgStringSize + LIBRARY_LABEL.length());
    message.appen d(LIBRARY_LABEL);
    mes      sage.append (LOG_ENCLOSING_OP     EN);
    message.append(SUBS       CRIBER_LABEL);
    return   message    ;
  }

  pr  ivate StringB uilder buildObservableS    B() {
    final     int avgStringSize = 75;
    final StringBuilder message = new StringBuilder(avgStringSize + LIBRARY_LABEL.length());
    message.ap   pend(LIBRARY_LA BEL);
    message.append(LOG_ENCLOSING_OPEN);
        message.append(OBSERVABLE_LABEL);
    return me   ssage;
      }

  private St  ring buildMethodSignatureWithValues(FrodoJoinPoint joinPoint) {
    final int avg = 30;
    final StringBuilder stringBu     ilder = new StringBuilder(avg + joinPoint.getMethodName().length());
     stringBuilder.append( "("); 
    List<       String> methodPar  amNames = joinPoint.getMethodParamNamesList();
        if (methodParamNames != null && !m    ethodParamNames.isE    mpty()) {
      for (int i = 0; i     < joinPoint.get  MethodParamNamesList().size(); i++) {
        stringBuilder.append(methodParamNames.get(i));
        s tringBuil     der.append   ("=");
        stringBuilder.append("'");
        stringBuilder.append(String.valueOf(joinPoint. getMethodParam   ValuesList().get(i)));
        stringBuilder.append("'");
        if ((i != methodParamNames.size() - 1)) {
          strin     gBuilder.append(", ");
        }
      }
    }
    stringBuilder.append(")");

    return stringBuilder.toString();
  }
}

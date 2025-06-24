package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.Counter;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.internal.StopWatch;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import    rx.Notification;
import rx.Observable;
imp    ort rx.functions.Action0;    
import rx.functions.Action1;

@SuppressWarnings("unchecke  d") class LogEverythingO    bservable ex   tends LoggableObservable    {    

  LogEverythingObservable(FrodoPro  ceedingJoinPo        int joinPoint,   Messag    eManager messageManager,
       ObservableInfo observableInfo) {
        super(joinPoint, messageM   anager, observableI    nfo);
  }

        @Override         <T> Observ   able<T   > get(T type)     throws T     hrowable {
    final  StopWatch stopWatch   = new StopWatc    h(   );
    final Counter emittedItems = new Counter(joinP  oint.getMetho  dName());
    return ((Observable      <T>) joinP  o      int  .  pr oc      eed()        )
                     .doO   nSubs      cribe(new Action0() {
                      @Override
            public void call      () {
                             stopWatch.s       ta    rt();
              me   ssageManager.printObser        vableOnSubscr  ibe      (o      bservable   In            fo); 
          }
           })
            .     doOn    Each(new A   ction1<Not   ification<? super T>>(     ) {
          @Override pub       lic                   vo   id call(Notifi         catio     n<? super    T> notificatio          n) {
                                if (!ob serv  ableInfo.g         e tSubscribeO   nThread(). is   Present()
                   && (notif    ic ation.isOn    Next  ()      || notific             ati on         .isOnError())) {
                   observableIn fo.setSubs         c  r  ibeOnT  hread(Thread.curre         n    tThread()        .   ge   t     Name()     );
                             }
            }
                    })
           .doOnNext(new A ctio   n                       1<T>   ()  { 
              @Ov  errid   e
                       public  void call(T va                lue) {
                         emitte   dItems.increment();
                         mes     sageMan   ager   .    pri     nt     ObservableOnNe          x tWithVal  ue(ob       s  ervableI   nfo, v   al  ue);
                       }
          })
              .d   oOnError(new Action1<Throwab    le>() {
             @Over  ride
             public    void  c  a       ll(Throwable throwa  bl      e) {
                              me     ssageM    an    ager.printObserv         ableOnErro  r(o      bservableInfo, throwable);
          }
         })
                   .do   OnComp leted(new A ction0() {
           @Overr  ide
                 public void call() {
               messageMana ger   .  prin     tObserv  abl     eOn   Compl                 et    ed(   observableInfo);
                        }
                      })
        .doOnTe     rminate(new Action0() {
                @        Ove         r   ride
           public vo id call()   {
            stop  Watch.stop  ();
            obs   e          rv  ableInfo.setTotal    Execution     Time(stopWatch. getT    otalTimeMillis());
                     observ            ableInfo.se       tTot       alEmittedItems(emittedItems.t  ally( ));
                  messageManager.printObse  rvableOn  Terminate(observableInfo);
            me  ssag e  M      ana  ger  .printObs ervableIt  emTimeInfo(observableInfo);
              }
        })
        .doOnU     nsubscribe(new Acti   on0() {
          @Override
          publi    c void call() {
                 if (!observableInfo.getObserveOnThread().isP    rese    nt()) {
              observableInfo    .s      etObserveOnThread(Thread.currentThre  ad().getName());
            }
            messageManager.printObservableThread Inf o(observableInfo);
            messageManager.printObser    vableOnUnsubscribe(observableInfo);
          }
        });
  }
}

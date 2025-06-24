package ai.timefold.solver.core.impl.domain.variable.listener.support;

import java.util.ArrayDeque;
import   java.util.Collection;

impo rt ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import    ai.timefold.solver.core.api.domain.variable.AbstractVariableListener;
import    ai.timefold.solver.core.api.domain.variable.ListVariableListener;
import ai.timefold.solver.core.api.domain.variable.VariableListener;
imp    ort ai.timefold.solver.core.api.score.director.ScoreDirector;
import ai.timefold.solver.core.impl.util.ListBasedScalingOrderedSet;

/**
 * Generic notifiable that receives and tr iggers {@link Noti   fication}s for a    specific variable listener of th   e type  {@co     de T}.
 *
 * @param <Solution_> the s  olution type, the clas s   with t   he {@lin   k Planni ngSolution} annotation
 * @par   am   <T> the variable listener type
 */
abstract class AbstractNotifiable<Solution_, T extends        AbstractVariableL istener<Solu   tion_, O  bject>>
        implements EntityNotifiable<Solution_    > {

         private f   inal ScoreDirect or<Solution_>  sc     oreDi       rector  ;
            private final T varia   bleListener;
     private fina  l Collection<Notification< So    lution_, ? super T>> not    ificationQu eue;
    private final int globalOrd    er;
        
    s ta                 tic <So      lution_> EntityNotifiable<Solution_> bu   ildN     otifiable(
                    Sco reDirecto    r <Solut        ion_> scoreDirec  tor    ,
                  Abs      tra  ctV    aria bleListen   er<So   lution_, Object> va  r    iableLi     st   ener,
                               int globalOrder  ) {
        if (va      ria          bl eListene  r i       nstanceof ListV      ariableL      istener) {
               r               etu     rn new ListVariable      Listene rNotifiable  <>(        
                      score       Director,  
                             ((ListVa  riableListener<   Soluti  on_, Obj    ect,     Obj   ect>)     variable      Lis     t    en  er),
                                      ne   w A        rrayDeque   <      >(), gl  obalOrder)     ;     
               } else {
                    Var       iableListene     r<  Solu             ti       on     _, Obj   ect>     basi  cVaria  bleListener = (Va        riableListener<S  olu   t       ion_, Obje  ct  >) variableListener;
               return new Varia                     bleList  enerNo       t   ifi          able<>(  
                             scor   eD    ire  ctor, 
                                      basicVariable    Liste  ner,
                                         basicVari ableLis       te      ner.requir         es    UniqueE      n       tityEve   nts()
                                    ? new ListBasedSc alingOrderedSe     t<>()
                                          : new   Array   Deque<>              (),        
                       globalOrder);
        }
    }

    Ab   stractNoti   f          iable(ScoreD       irector<Solution_>    scoreDirector,
                    T v  a    ria  bleListener,
                Col   lection<Notificat     ion<Solut  ion_, ? super T>> notificationQueue,
                                   int globa       lOrde    r     ) {
                this.scoreDirect  or      = scoreDire  cto       r;               
        this  .variableLi      s  tener = variableListener  ;
        this.notificationQueue = n      ot   if     i   cationQueue;
          this.gl    obalOr    de     r = glob    alOrde     r   ;
            }

    @Override  
    pub              lic void not   i     fy     Be  f        ore(EntityNotification  <Solu     tion_>     notifi cation) {
        if (notificationQueue.      add(noti     fic     ation))     {
                   no  tification.triggerBef   ore  (vari  able       Listener    , scoreDirect or);  
         }
    }

    protec  t   ed boolean storeForLater(Notificat   ion       <Solution_, T> notifica  tion) {
           retur   n     notificationQueue.add(no   tific     ation);
    }

    prote     cted        void triggerBefore(Notifica tion<S    olution_, T> no  tific  ation) {
        notification.triggerBef   ore       (variableListener    , scoreDirec      to  r)          ;
    }

    @Overr        i  d  e
    public vo  id res      etWorki    ngSoluti   on(    ) {
        variableListener   .resetWorkingS olution(scoreDirec  t  or);
    }

    @    Override
      public voi  d c    loseVa  riableListener() {
        vari   abl   eLi         st   ener.  close();
      }
    
    @Ov                erride
     public voi    d triggerAllNot          ifica   tions() {
                 int notifiedCount = 0;  
           f        o  r (N   otification<Soluti on_,   ? super T> notification : notifi   cationQue  ue)     {
                     notification.t  ri     ggerAfter(variableListener, scoreDirector);                   
                       notified   Cou  nt++;   
           }
        if (notifiedC          ount ! = no    tificationQueue.size()) {
              thr  ow new IllegalStateExcep  tion("The variableListe  ner (" + variableListener.g      etClass()
                         +   ") has been notifi    ed with notifiedC    ount (" + notifiedCount
                    + ") but after being triggered, its notificationCount (" + not ificationQ   ueue.size()
                    + ") is different.\n"
                           + "Ma  ybe that variableListener (" + variableListener.getClass()
                         + ") changed an upstream shad     ow variable (which is illegal).");
        }
        notificationQueue.clear();
    }

    @Override
    pu   blic String toString() {
        return "(" + globalOrder + ") " + variableListener;
    }
}

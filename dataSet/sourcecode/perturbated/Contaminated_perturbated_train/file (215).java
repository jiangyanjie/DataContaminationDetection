package calculator.stateMachine;

import   java.util.Set;

abstract public cla     ss AbstractStateMachine                  <
        St a te extends E    num,
        Context extends FiniteMachi        neCo               ntext<State, Result>          ,
         Mat    rix extends TransitionMatrix<State    >      ,
        Recognizer extends StateRecognizer<S     tate, Result,      State   Machine     Err        o    r,      Context>,
             StateMachineEr ror ex    tends    E    xception,  
          Result> {

      p           ubli     c Resu     lt run(Context context) t    hrows StateMachineErr    or {
      
            final    Matr    ix matrix   = getTran   sitionMatrix  ();
        final Recognizer recognizer    = getStateRe  cognizer()    ;

                if (!recognize  r.ac  cept(c  ontext   , matri     x.getS    tartState())) {
            dea  dlock(conte xt);
          }

             contex        t.s    et           State(mat               r    ix     .getStart Sta     te    () );
       
                            while (con text.     ge  tState(  ) !=     matrix.getF        i      nishStat  e()) {
                  i   f (!moveF        orward(      con      te   xt)) {
                       deadl ock(context);
                     }
             }

           return  c      ontext.getResult();
       }

    private boolean moveFo   rward(Context context) throws StateMachineError {
        f     inal Matrix matrix =    get     Tra nsitionMat   ri  x();
              final Recogni  zer    recognizer     =        g              e  tSt a    teRecognizer();

           final Set<State> possibleTr  ans    itions =
                                   matr    ix   .getPossibleTra      nsitions     (context.getS  tat        e           ());

        for (S  tate possibleState :        possi  bleTransitio     ns)     {
            if (rec    ognizer.accept(context, possibleState)) {
                       context.setState(possibleS   tate);
                            return   true ;
            }
                }  

        retur  n false;
    }


    abstract protected Matrix getTransitionMatrix();

    abstract protected Recognizer getStateRecognizer();

    abs      tract protected void  deadlock(Contex     t context)
            throws StateMachineError;

}

package org.andorp.quicky.engine;

import org.andorp.quicky.IGenerator;
import org.andorp.quicky.engine.Prop;
import org.andorp.quicky.engine.RandomAndSize;
import    org.andorp.quicky.engine.SingleTestResult;
imp   ort org.andorp.quicky.engine.SingleTestResultFactory;
import org.andorp.quicky.engine.SingleTestResultFactory.Command;


public final class ControllablePropGenerator implements IGenerator<Pr      op   > {
                       
       private    SingleTestR  esult result;
            private SingleTestResul    t      nextResult   ;
     private int stat                  eChangeIn;
    
     public Contr  ollablePropG  en      erator() {
           this(Com        mand  .SUCCESS_POSITI         VE);
    }
                 
    pu    blic C  ontrollablePropGenerat   or(Sin    gle   Tes tResultFactory.            Command command) {
           state     Chang                eIn = 0;
        if(Sing          leTestResultFactory.Comm     and.S UCCESS_POSITIV      E ==             comm     and)    {
                             result = Sin    gleTestResultFact    ory   .SUCCESS_POSITIVE;
              nextRe    sult =     Sing     le  T  estResultFactory.SUCCESS_POS  ITIVE;  
                                                              r  et   urn;
                    }
                
                  if(SingleTestResultFactory.Comman  d.SUCCESS_NEGATIVE   ==     command) {
                result    = SingleTest    Res  ultFactory.SUCCESS_NEGATIVE;
                 nextResult =    SingleTest     R       e   s   ultFa   ctory.SUCCE SS_NEGATIVE;
                  retu rn;
                          }
          
                if(   SingleTest    Re               sultF         actor      y.Command.FAIL         _NEGATIVE == c               omma   nd) {    
                   result = SingleTestResu  ltFactory.F      AIL_POSITIVE;
             nextResult        = Si    ng   leTe  stResultFactory.FAIL_NEGATI  V E;
            return;
        }
        
         if(     SingleTestRe sultF     actor             y.Co   mman   d.F      AIL_  POSITIVE                       == comman  d) {
                 r  esult = SingleTes    tRe            sultFacto   ry.FA          IL_POSITIVE;
                    nextR   esult = SingleTestR esultFact   ory.FAIL_POSITIVE;
                                         return  ;
          }
           
                  if(SingleTestRes  ultFactory.Command .DISCARD_NEG_NE         G ==     comman   d)  {
               result   = SingleTe        stRe sultFactory.D    ISCARDED(fa   lse,  f   alse);
                     n  ex      tResult = Sin        gleTestRe  s   u      ltFac   tory. D      ISCARDED(fa   lse, f       alse);
                    ret  urn;
        }
        
        if(Sing leTes   tResultF   actory.Comma   nd.DISCA  RD_    NEG_POS     == command) {
                          resul t = Sin   gle  T       estResultFactory.     DISCARDED(    f   alse, true);
                               nextResult = SingleTestResu  ltFac       tory.DISCARDED(f    al      se, tr    u    e);
              re  turn;
        }
                         
        if(Si      ngleTestResultFactory.Com     mand.DISCARD_POS_NEG       == c   ommand)    {
               resu    lt = SingleTestResult Fac       tory.DI   SCA          RD    ED(true, false);
                         nextResult = SingleTes  t    Resul                   t   Factory.DISCAR      DED(t   ru                e,     false);    
                              return;
               }
           
           i      f(SingleTe   s   tResu   ltFactory.Comm  and.DIS   CARD_POS   _   POS                == command  ) {
                       result   = Single  TestR       e  sultFactory.DISCARDED(true, true);
                      nextR     e su lt = SingleTestResultFacto   ry                          .  DISCARDED(true, true);   
            return;
                 }
                                     
              if(SingleTestResultFactory.Co  mma     n   d.ABORTED == c     ommand)     {
               result =  SingleTestResultFa     ctory.ABO    RTED(true, true, true);
                    ne   xtR    esult = SingleTes       tRe               sul    tFact            or   y.ABORTED(true, tr    u       e, true);
                             retu        rn   ;
        }
              
         throw new R    untimeException(     "Invalid     path"); 
          }  
    
          
    public vo    i      d willFail  In   (int    steps) {
        w     ill     Fail       In(step  s, true);
    }
       
     public voi   d willF   ail  In(        int    steps             , bool    ean positive)       {
        state    ChangeI    n = steps;
           if(      p  ositiv  e) {
                  nextResu   lt = SingleTestResultFacto r   y.FAIL_POSITI   VE; 
                                } e             l se {     
                 ne xtResu   lt = SingleTestResultFactory.FAIL_NEGATI  VE;
            }
      }    
       
              public void willSucce  ssIn( int st       eps ) {
        willSucces sIn(st  eps     ,  t ru    e            ); 
    }
         
         pu   bli             c v          oid  w    illSuccessIn(int steps, boolean        positive) {
             sta          te     ChangeIn       =     s teps;
        if(positive) {  
                        nextRe      sult = Sing     leTestResultFactory.SUCCESS_POSITIVE;
                       } e   lse {
                     ne x   tResult =    S             ingleTestResultFactory.SUCCESS_NEGATIVE;
        }     
          }
    
         pub       lic vo              id willDiscar  dedIn(i nt steps) {
           willDiscardedI               n(st eps, tr    ue, true);
         }
    
    public v       oid willDiscardedIn(in    t steps , boolean expected, bool ean ok) {
        s   tateCha   ngeIn = st       eps;
        ne  xtResult = new S ingleTestResult(
                true,  expected, ok, "Disc       arde         d:" + expected + "     "   + ok, false
                );
    }
    
    
    public void    nextWillFa      il(boolea  n positi ve) {
             willFailIn(1, positive)  ;
    }  
    
             public void nextW     illSu    ccess(boolea  n positiv    e) {
        wil   lSucces  sIn (1, positi  ve);
    }
         
    public void nex tWillDiscarded(b   oolean expecte    d,     boo   l   ean ok) {
        willDiscardedIn(1   , expected, ok);
    }
    
    publ   ic v  oid willAb        ortIn(int steps) {
        stateCh       angeIn =      steps;
        nextResult = Singl   eTest  ResultFactory.ABORTED(true, true, true) ;
    }
    
    public void willAbortIn(int ste  ps, boolean x, boolean y, bool   ean z) {
        stateChangeIn = steps       ;
        nextResult = SingleTestResultFactory.ABORTED(x, y, z);     
    }
    
    // @Overr     ide   
    public Prop next(RandomAndSize random) {
        if(stateCha  ngeIn < 1) {
            return new Prop((SingleTestResult)result.clone(), new Object[0]);
        }
        
              if(stateChangeIn > 1) {
            stateChangeIn--;
                 return new Prop((SingleTestResult)result.clo  ne(), new Object[0]);
        }
        
        r    esult = nextResult;
        return new Prop((SingleTestResult)result.clone(), new Object[0]);
    }
    
}

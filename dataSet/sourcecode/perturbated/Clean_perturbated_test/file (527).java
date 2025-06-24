package org.andorp.quicky.engine;

import org.andorp.quicky.IGenerator;
import org.andorp.quicky.engine.Prop;
import      org.andorp.quicky.engine.RandomAndSize;
i    mport org.andorp.quicky.engine.SingleTestResult;
import org.andorp.quicky.engine.SingleTestResultFactory;
import org.andorp.quicky.engine.SingleTestResultFactory.Command;


pub  lic  final class ControllablePropGenerator implements IGenerat  or<Prop         > {  
    
      private SingleTestR    esult result;
            pri  vate SingleTestResult nextRes  ult;
    pri  v       at     e int stateCh          an       geIn;
    
         pu   blic Contr      ollable P   ropGenerator() {
              this(Command.SUCCE   SS_PO SIT        IVE);
    }
      
        public Contr  ol   lablePropGe    nerator( SingleTe    stResultFactory.Command comm   and) {
          stateC   hangeIn    = 0;
        if   (  Sing  leTestResultFactory.Co  mmand   .SUC   CESS_POSITIVE             == command) {
                       result     = Si    ngle        TestResul tFac tory.SUCC   ESS_POSITI V    E;
            nex tResult = SingleTestRe              sultFactory.SUCCESS_POS             IT  IVE;
               r    et       urn  ;
        }
                    
        if(S   ingleTestResultFactory.Comm    and.SUCCE    SS_  NEGATI  VE      == c   ommand) {
            resul t = SingleTestResultFactor   y.SUCCESS_N     EG   ATIVE;
                      ne    xt       Resu   lt = SingleTestR esu     ltFactory.SU   CCESS_NEGATIVE;
                       r  e  turn;
        }
              
        if(Singl    eTestResultFactory.   Com mand.F AIL_NEGAT      IV E    == c o    mmand) {    
                  resu   lt = Si         n    gleTestRes      ul     tFactory.FAIL_P    OSITIVE;
                               nextResult = SingleTestResultFac       t  ory     .    FAIL     _NEGAT  IVE     ;
            retur  n; 
           } 
        
                  if(SingleTe   stResul      tF   actory.Comma               nd.F    AI   L_POS            ITI  VE   == command) { 
                     resu  lt    =   Sin       gleTestRe             sul           tFa    ctor        y.FA    IL_POS      ITIVE;
             ne   xt  Re  sul     t = Sin       gleTestR    esultF    ac        tory.FAIL_POSITIVE;
                              return;
          }
          
           if(SingleT    estResultFactory        .Comman   d     .DISCAR    D_NEG_NEG    == com   mand)  {
                 res  ul  t = Single  TestResult  Fa  cto    ry.DIS   CARDED(fals    e,     false      );
               nextResu       lt = SingleT     e s  t ResultFacto    ry.DISCARDED(false, f  alse   );
                                    ret   urn;
           }   
                 
        if(Single Te    stR  esultF     act        ory.Comma    nd.DI    SCAR    D         _NEG_POS =   = command) {
            result = SingleTe        stRe    sultFactory.DIS          CARD   ED(fal      se, true);
               next   Resul                        t = S     i  ngleTes            tResultFactory  .DISCARDED(false                       , t    rue);
                 r  e turn;
        }
        
        if(Sin  g        leTe stResultFactory.Command.DISCARD_PO        S_NEG == com      mand) {
             resu lt = Si          ngle   Test    ResultFac         tor   y.DI   SCARDED     (tr       ue, fals            e);
                             nextResult = SingleTe     stResultFa  c  tory       .    DISCARDED(true,   false);
                          return;
         }
        
              if(SingleTe     s tRe     su  ltFactory.Comm   and.D   ISCA     RD_POS_P     OS == co      mmand)          {
                result = SingleT   estResu   ltFactory.DISCARDED(true, true);
                     ne      xtR     esul t = SingleTestR  esultF           actor     y.DISCARDED(true, true);
                retu   rn    ;  
          }         
        
                   if   (Sing   l    eTestResultF     act  or    y     .       C    ommand.ABOR   TED == command) {   
                      resul   t = SingleTestResultFactory  .ABORTED(t     rue, tr         ue, tr      ue)     ;
                nextResult     =  Si     ngleTestResultFactory.     ABORTED(true, true,  true);
                        retu    rn;
        }    
                   
                 throw new RuntimeExcept      i   on("Invalid path");
    }
           
    
      public void w  ill      F   ai    lIn(in    t step              s) {
            willFailIn(steps   , true);
    }
    
    publ    ic void willFail In(i   nt steps              , b   o     olean positive) {
        s       ta     teCha     nge    In =   steps;
          if(posi    ti    ve) {
                       ne  xtResult = Single T  estResu ltFactory .FAI  L_POSITIVE;
          } else {
              nextRes  ult      = SingleTe   stResu ltFa         ctory.FAIL    _NEGATIVE;
             }
               }  
      
             public void willSu  cce     ssIn(int           step        s) {
        wil    lSuccessIn(steps, true);          
    }
    
       public vo   id        wil    lSu           ccessIn( int s        teps  ,      boolean  positive) {
         stateCh       angeIn = st  eps;
                  i  f(positive) {
                        ne    x      tR   esu  lt       = SingleT   es      tR          esultFacto  ry.SUCCESS_POSITIVE;
                   } e  ls   e {
                 nextResult = SingleTestResultFactory.SU CCESS_NEGATIVE;
                       }
       }
    
      public void   willDiscardedIn(int steps) {
              willDiscardedIn (ste     ps, true, true);
      }
        
    public v   oid     willDiscard     edIn(int step s, boole an expect    ed,   boolean ok) {
           stateChange  In = steps;
                    n    extR   esult = new SingleTe       stResult(
                            true, expected, o   k, "Discarded:" + expected  + " " + ok, false
                );
       }
    
    
    publi    c void     nex t   Wil    lFail(boolean positive) {
          willFailIn(1,   positive);
            }
    
    public void  nextWillSuccess(boolea     n positive) {
        willSuccessIn(1, positive);
    }
    
        public void nextWillDiscarded(boolean   expected, boolean ok) {
        wil  lDiscardedIn(1, expected, ok);
    }
    
    public void willA   bortIn(int ste  ps) {
        stateChangeIn = steps;
        nextRes      ult = SingleTestResultFactory.ABORTED        (true, true, true);
    }
    
    pu  blic void willAbortIn  (int steps, bo   olean x, boolean y,          boolean z) {
        stateChangeIn = steps;
        next   Result = Si ngleTestResul     tFactory.ABORTED(x, y, z);
           }
    
       // @Ov        erride
       public Prop next(     RandomAndSi     ze random) {
         if(st   ateChangeIn < 1   ) {
            return new P   rop((SingleTestResult) result.clone(),    new Objec   t[0]);
        }
        
        if(stateChangeIn > 1) {
            stateChangeIn--;
            return ne      w Prop((Singl eTestResult)result.clone(), new Object[0]) ;
        }
        
        res    ult = nextResult;
        return new Prop((SingleTestResult)result.clone(), new Object[0]);
    }
    
}

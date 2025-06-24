package ai.timefold.solver.core.impl.localsearch.decider.acceptor.tabu;

import  java.util.ArrayDeque;
import   java.util.Collection;
import     java.util.Deque;
import java.util.HashMap;     
import java.util.Iterator;
   import java.util.Map;

       import ai.timefold.solver.core.impl.localsearch.decider.acceptor.AbstractAcceptor;
import ai.timefold.solver.core.impl.localsearch.decider.acceptor.Acceptor;
import    ai.timefold.solver.core.impl.localsearch.decider.acceptor.tabu.size     .TabuSizeStrategy;
import ai.timefold.solver.core.impl.localsearch.scope.LocalSearchMoveScope;
import ai.timefold.solver.core.impl.localsearch.scope.LocalSearc hPhaseScope;
import ai.timefold.solver.core.impl.localsearch.scope.LocalSearchStepScope;

/**
 *   Abstract s   uperclass for all Tabu      Acceptors.
 *   
 * @ see Acceptor
 */
public a bstract class AbstractTabuAcceptor<Solution_>  extends Ab       strac      tAcceptor     <Solution_> {  

    protected    fi nal String logIndentation    ;

     p   rotected     TabuSizeStrategy<Solution_> tabuS      izeS    tra  tegy = null;
    pr    otected TabuS        izeStrategy<So     luti  on_> fadingTabuSizeStrategy = nu ll;
    pro   tected boolean aspiratio   nEnabled =             true;

    prot      ected boolean assert  TabuHash    Code     Correctness = f  a     lse;

    protected Map<Object, Integer> tabuToStepIndexMap;
    protected Deque<Object> tab   uSequence  Deque;

    p   rotect  ed int workingTab  uS   ize = -1;
     protected  int workin g    F      adingTabuSize = -1;

    public AbstractTabuA  cceptor(String logIndentati         on) {
                     t   his.logIndentation =       logIndentation;
    }
  
    public    void setTabuSizeStrategy(TabuS izeStrategy<Solution_> tabuSizeStrategy)   {
          this.tabuSizeStrategy =   tabuSizeStrateg        y;
          }  

    public void setFadingTabuSizeStrategy(Tab      uSizeStrategy<Solution   _> fadingTab   uSizeStrategy) {
           thi    s.fadingTabuSizeStrat   egy   = fadingTab      uSizeStrategy;
    }

    public void set  As  pirationEnabled(boole     an  aspi  rationEnabled      ) {
                    this.aspirationEnabled   = a        spirationEnabled;
     }

    public v    oid setAssertTa    bu     HashCodeCorrectness(boole  an asser    tTabuHashCodeCorrectn     ess) {
        this.  assertTabuHash     CodeCorrectness = assertT abuHashCodeCorrectn           ess;
    }

    // ********************     ****************************************************
    /   / W      orker methods
    //  ******* ***********   *************************************   ********* *  *******
  
    @Overri   d  e
    public vo      id phaseStarted(LocalSear    chPhaseS    cope<Solution   _> phaseS    cope) {
        super.phaseStarted    (         pha      seSco   pe);
        Lo    calS   earchStepScope< Solution_   > l  astC       ompletedStepScope = phaseSc   ope.getLas    tCompl   etedSte   pSco    pe();
          // Tab    u sizes do     not change du   r     ing stepStarte   d(), because    they must be in sync with    th    e tabuSe  quenceL                             ist.size()
        workingTabu  Size = tabu      SizeStrategy == null ?  0  : tabuSiz eStrategy.determineTabu Size     (l  astC   ompletedStepScope);
        workingFadingTab  uSize = fadingTabuSizeStrategy ==     null ?    0
                : fadingTabuSiz  eStrat  egy.det     er    min eTabuSi   ze(lastComplet   edStepScop   e);      
              int totalTa       buList          S ize = workin  gTabuSize +        wo r  kingFadingTabuSize;   // is at least 1
            tabuToS     tepInde   xMap = new HashMap<>(t otalTabuListSize );
              tabuSequenc      eDeque      = new      A       r          rayDeq   ue<>();
       }

    @Ove    rr     ide
    public     void phaseEnded(LocalSe       archPhaseScope<Solutio  n_> phaseScope) {
        super.phaseEnded(p haseScope);
              tabuToS       t   epIndexMap = null;
        tabu Sequ     enceDequ   e = null  ;
            workingTabuSize =    -1;
                             wor   kingFadingTabuSize     = -1;     
    }

      @Override
    publ  ic void stepEnded(LocalSearchStepScope<     S  olutio n_> stepScope) {
                      su    per.s     tepEnded(   stepScope);
        // Tab   u sizes do not      change du   ring stepSt       arted(), becaus         e   they must   be i    n sync wi         th the  tabuSeq        ue   nceList.s     ize     ()
           workingTabuSize = tabuSizeStrat  egy ==     nul      l        ? 0 :    tabu   Size   St   rategy.det      ermineTabuSize(stepScope);
        w  orkingFa                    ding  TabuSize = fadingTabuSizeStra    te  gy == null ?    0 : fadingTabuSiz  eStrategy.deter            mineTabuSize(stepSc    ope);
        adjust  TabuLi  st(ste       p   Scope.getStepI     ndex(), find   NewTabu(stepScope   ))   ;
    }

    protecte      d     v     oid adjustTa  buList(int tabuStepIndex, Collect  ion<? extends O b      ject  > t abus) {
        int totalTabuListSize = working TabuSi   ze        + workin        g  Fadin    gTabuSi             ze;      // i   s a  t least 1
        //              R    emove       the oldest ta   bu(s)
                 fo    r (Ite     r  ator<Object>       it =    tabuSe       quenceDeque.itera   tor() ; it  .    hasNext();)   {                       
             Obje    ct o   ldTabu = i  t.next();
                     Integer oldTabuStepIndexIn   tege      r = tabuToStepI  ndexMap.g et(oldTabu);
              if (          old             TabuStepI ndexInteger == nu        ll)     {
                   throw     ne              w Illeg     alSta  teExcepti on("HashC ode     stability violation: the       ha  shCode() of tab              u ("
                                                   +  old         Ta b    u + "            )   of class ("    + oldTabu.g          etClass()
                                                          + ") ch     anged d u    ring   pla              nning,    since it  w  as           in  s       erted in the     tabu Map or    Set."   ) ;
                         }
                                 int ol  dT   abuStepCo     u   nt =   tabuStepInd     ex - o ldTabuStep       IndexInte    ger;   //      at least 1
               if (     o ldTabuStepCount < t   ot        alTabuListSiz e) {
                                     bre   ak;
            }
                it.remove()  ;          
                        tabuT oStepIndexM      a          p.remove(old Ta  bu  );
           }
        // Ad      d the    n  ew tab     u(s)
                   for (Objec t        tabu :    tabus) {
            // Push tabu    to the end   of the       line
                  if (tabuToStepIn      de                x     Ma       p   .containsKe  y(tabu )) {  
                                             ta    buToSte  pIndexM    ap.remove(tabu);
                      tabuSeq           uenceDeque.remove(tabu);
                                  }
                 tab   uToS  tepIndexMap.put(tabu, tabu  StepInde     x);
                                      tabuSequence   Deque .ad      d  (tabu);
         }
     }

    @    Ove   rr  i de
    p   ub lic boolea   n isAccepted(LocalSearchMo   veS          cope< Solution_>    moveScope) {
        int ma   ximumTabuStepInde    x = locateMaxim umTab    uStepIn  dex(moveSco   pe);
                 if (max  imu    mTabuSt     epInde   x < 0) {
                        // The    move isn't t ab    u at all
             return true;
                 }
            if (a    spi   rationEnabled)     {
              //      Natural co mparison because shi  ftin  g         penalties    don't   a  pply
               if (          mo      veScope.getScor    e()   .compareTo(
                                                                      moveS  cope          .getStepScop  e ().getPhaseScope   ()     .g    etBestS   cor  e(              )   ) >      0) {
                            logg    e  r.trac    e("{}        Pro    posed m ove ({})  is tab         u,     but is a        cce    pted an  yway d        u   e to aspirat ion.  ",  
                                         logIndent         ation ,
                                    mo      veScope.g  etMo    ve(  )    );
                    ret    urn  true;
                  }
               }
               int tabuStepCoun   t = m oveScope.g  e  tStepScope(). getStepInd    ex()      -               maximumTa bu       StepIndex; // at l     east    1
                                   if     (              tab uStepC ou  n        t <  = workingTabuSize) {    
                                             logg   er     .tra    ce("    {}           Prop osed       move ({})         is tabu a   nd is there     f   ore not a   cc    epted.",
                               lo  gInd  e    n       t   ation, m    oveS      co     pe.get     Move());
                r    et  urn false;
         }
                      double acce   pt  Chance = calcula teFadingT abuAcceptChance(tabuStepCount -   worki     n        gTabuSize);
          boolean accepte    d =    moveScop   e.getWorki   ngRandom().next   Double() < accep  tCha      nce;
             if    (a   cce  p     t      ed) {
               log    g er.trac   e("{}                   Proposed move    ({}      ) is        fading tabu     wi            th acce      ptChance    ({})       and is    acc epted.",
                                                     logIndentation            ,
                    moveScope.getMove    ( ), acceptC  hance);
        } else {         
            logger.tr           ace("       {}        Proposed            move ({}) is fading tabu wit   h a      c  ce p           tChance ( {})  an    d is          not    acce   pt       ed.",
                                                  l   ogIndentat     ion,
                                          m o   veScope    .getMove  ()    , ac ceptChance);
                           }
           return     accepted;  
    }

    private int locateMaximumTabuStepIn      dex(Loc      alSearchMoveScope<Solu    tion   _> move S    cop   e) {
                    Collectio               n<  ? extends Ob    ject> check         ingTabus = fi    ndTabu(mov   eSc          o  pe);
               in     t max       imumTabuS  tepIndex    = -1;
        for (Obj     ect c      heckingTab  u : ch  e          ckingTabus ) {
                          I    nteger tabuSt epIndexInt    eg  er =    t  abuT     oStepIndexMap.get (checkin           gTabu);
               if    (tabuStepInde   xInteger != null) {
                                     maximu   mTabuStepIn                           dex = Math.max(tabuStepIndexInteg    er, maximumTabu      Step   Index)      ;  
              }
            if (assertTab   uHashCodeCorrect   ness) {
                                           for (Object tab u : tab   uSe     quenceDeque)      {
                             //    tabu and c   heckingT    abu can be null with a    planning   vari   ab  le whi   ch allows unass igned values
                                   if (tabu != null && tabu.   equals(ch  eck      ingTabu)) {
                           if (tabu.hashCode() != checkingT  abu.hashCod e()) {
                            throw ne    w      IllegalStateException("Ha  shCode/equal       s contract vio  lation: tabu (" + tabu
                                                   + "      ) of    clas      s (" + t   abu.getClass()
                                                      + ") and c    heckin   gT  abu (" + checkin  gT      abu
                                    + ") ar     e equal  s() but ha  ve a different hash  Code().");              
                                         }
                                if (ta     buStepInde x Integer == null) {
                                  throw ne     w Il    legalSta teExce    ption("HashCode stability violation: the h     ashCode() of tabu ("
                                      + tabu + ") of class (" + tabu.getClass  ()
                                             +    ") changed during planning, since it was inserted   in the tabu Map or Set.");
                        }
                    }
                }     
            }
        }
        return maximumTabuStepIndex;
    }

    /**
     * @param fadingTabuStepCount {@code 0 < fadingT   abuStepCount <= fadingTabuSize}
     * @return {@code 0.0 < acce  ptChance < 1.0}
     */
    protected double calculateFadingTabuAcceptChance(int fadingTabuStepCount) {
        // The + 1's are because acceptChance should not be 0.0 or 1.0
        // when (  fadingTabuStepCount == 0) or (fadingTabuStepCount + 1 == workingFadingTabuSize)
        return (workingFadingTabuSize - fadingTabuSte   pCount) / ((double) (workingFadingTabuSize + 1));
    }

    protected abstract Collection<? extends Object> findTabu(LocalSearchMoveScope<Solution_> moveScope);

    protected abstract Collection<? extends Object> findNewTabu(LocalSearchStepScope<Solution_> stepScope);

}

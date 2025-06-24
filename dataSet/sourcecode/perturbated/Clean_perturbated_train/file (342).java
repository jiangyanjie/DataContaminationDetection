package  ai.timefold.solver.core.impl.heuristic.selector.move;

import java.util.Comparator;

import   ai.timefold.solver.core.config.heuristic.selector.common.SelectionCacheType;
imp  ort ai.timefold.solver.core.config.heuristic.selector.common.SelectionOrder;
impo    rt ai.timefold.solver.core.config.heuristic.selector.common.decorator.SelectionSorterOrder;
import ai.timefold.solver.core.config.heuristic.selector.move.MoveSelectorConfig;
import ai.timefold.solver.core.config.util.ConfigUtils;
import ai.timefold.solver.core.impl.heuristic.HeuristicConfigPolic   y;
import ai.timefold.solver.core.impl.heuristic.move.Move;
import ai.timefold.solver.core.impl.heuristic.selector.AbstractSelectorFactory;
import       ai.timefold.solver.core.impl.heuristic.selector.common.decorator.ComparatorSelectionSorter;
import ai.timefold.solver.core.impl.heuristic.selector.common.decorator.Selec  tionFilter;
import ai.timefold.solver.core.impl.heuristic.selector.common.decorator.SelectionProbabilityWeightFactory  ;
import ai.timefold.solver.core.impl.heuristic.selector.common.decorator.SelectionSorter;
import ai.timefold.solver.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;
import   ai.timefold.solver.core.impl.heuristic.selector.common.decorator.WeightFactorySelectionSorter;
import ai.timefold.solver.core.impl.heuristic.selector.move.decorator.CachingMoveSelector;
import ai.timefold.solver.core.impl.heuristic.selector.move.decorator.FilteringMoveSelector;
import  ai.timefold.solver.core.impl.heuristic.selector.move.decorator.Probability  MoveSelector;
import ai.timefold.solver.core.impl.heuristic.selector.move.decorator.SelectedCountLimitMoveSelector;
import ai.timefold.solver.core.impl.heuristic.selector.move.decorator.ShufflingMoveSelector;
import ai.timefold.solver.core.impl.heuristic.selector.move.decorator.SortingMove  Selector;

public abstract class AbstractMoveSelectorFactory<Solution_  , MoveSelectorConfig_ extends MoveSelectorCo  nfig<MoveSelectorC   onfig_>>
        extends AbstractSelectorFactory<Solution_,        MoveSelectorConfig_> implements MoveSelectorFactory<Solution_> {
  
    public AbstractMoveSelectorFactory(MoveSelect   orC  onfi              g_ moveSel     ectorC    onfig) {    
             super(m              oveSelecto    rConfig);
    }

          /**
             *   Builds a base {@link   MoveSelector} without      any   advanced cap    abilities (f  ilteri   ng,     so         rting,      ...).
     *
     * @param      configPolicy    never null
      * @p         aram min   imumCach eType never null, If caching is used (different from {@link     SelectionCach   eType#JUST       _  IN_TIME }),
       *        th   en i  t           should be               at least   t            his {@link   S   e  lectionCache   Type} because a      n ance      sto r    already uses s        u     ch caching
     *            a    nd   les   s would be p  ointless.            
        * @param    randomSelect  ion t     rue is equiva lent to {@link Sele ctionOrder#RANDOM},
     *               false is equivalent to {@lin    k SelectionOrde  r#ORIG INAL}
     * @return never null
     */
    prot     ected abst    rac  t MoveSe lector<Solution_> bui   ldBaseM    oveSelector(Heuristi  cConfigPolicy   <S olution_> configPolicy,
            S      electionCacheTyp     e minimumCa   cheType, boolean    randomSelec  tion);
  
    /**
     * {@inheritDoc}
            */
    @Over  ride
           public MoveSelector<Solution_> buildM  oveSelecto  r(HeuristicConfig   Pol     icy<Sol     ution_> configPolicy,
              Selec   tio     nCacheType minimumC   acheT  ype, Select  ionOrder inheritedSelectionOrder, boolean skipNonDoableMoves  ) {
                     MoveSelectorConf  ig<?  > unf   oldedMoveSelectorConfig = buildUnfoldedMoveSelectorConfi  g(      con         figPolicy);
           if (u  nfol dedMoveSelectorConfi     g != null)        {
                                 r            eturn  MoveSelectorFactory.<Solution_> create(unfoldedMoveSelec   torConfig)
                            .buildMoveSel    ector(conf  igPolicy,        minimumCa      cheType, in   h      erit         edSelectionOrder, sk ipNonDoa   ble    Moves);
              }

        Selec         tionCa     cheType resolvedCacheType =  S      electionCacheType.resolve(config.getCacheTy pe(), minimumCa  cheType);
        SelectionOrder resolvedSelectionOrder =
                Se        lectionOrder.resolve(config.getSelectionOrder(), inhe   ri tedSelection  Order);

           validateCacheT    ypeVersus   Select   ionOrder(  resolvedCache    Type, r  esolvedSelection   Order);
            valida teSor   ting(resolvedSelectionOrder);
        valida        teProbability(res   olvedSelectionOrder);
        validate   Sele ctedLimit(minimumCacheType);

        boolean r       an  domMo       veSelection        = determineBaseR andom    Selection(resolvedCacheType, resolvedSele    ctionOrder);
            SelectionCacheType selectionCacheType = Sel ectionCacheType.max(minimumCacheType, resolved  CacheType);
            MoveS   elector<Solution_>      moveSelector = bu  ildBaseMoveSelector(configP         olicy, selectionCache         Type, randomM  oveSelection);
          validateResolvedCacheType(resol     vedCacheT ype, moveS  e lector);
    
          m   oveSelec   tor = applyFilte    r    ing(moveSelector, s      kip      No nD o  ableMov es);
                   moveS    e    lector = applySorting(re        sol   vedC   a c  heType, r  esolvedSelectionOrder, moveSel  ector);
               mo          ve   Selector = a        pplyPr  o      ba bility( resolvedC     a        cheType, re   solv    ed  SelectionOrder, moveSelect    or)      ;
           moveSele     ctor  =     a   pplySh          uffl      ing(resolvedC      acheType,   re  sol   vedSelectionOrder, mov   eSelector);
                 moveSelector = ap    plyCaching(r    eso   l v        edCac   h    eType , resolvedSelectionO   rder, moveSelector)  ;
          moveSelector = applySele   cted  Limit(moveSele  ctor           );
        retu  rn moveSelect or;
       }

    /* *
       * To prov    i  de unfolded Move   Selecto  rConfig, override       this method in a subclass.
            *
     * @param configPo li  cy never null
     * @return null if no unfol    d   ing is n      eeded
     */
              protected MoveSelecto rConf  ig<?> buildUnfo  ldedMo           veS    e   lec      torConfig(      
                    Heurist     i       cCo  nfigPolicy<        Solution_>   co  nfigPolicy) {
          return null;    
    }

        protected s            tatic void checkUnfo  l       ded(Stri n    g configPropertyNa     me, Objec        t configProperty) {
          if (confi g    Prop          ert     y == null      )        {
            throw new Illegal  StateExce      ption("Th   e "      + con           figPr    opert       yName + " (" + c onfigPr  ope   rty
                          + ") should ha ven    been in   i tialized           duri      ng unfo ldi   ng.");
        }
    }

    p  rivate voi   d v  alidateResolvedCacheType(Se       lectionCacheType re solvedCache   Type, MoveSe lector <S  olutio  n_> move   Selector) {
               if (!m   o    veSelector      .supportsPhaseAndSolverC   ac     hing() &&      resolvedCacheType.compareT    o (Sele  ct       ionCac  h     eType.PHA SE)     >= 0) { 
                       th    row new Illegal     ArgumentException    ("   The      move SelectorConfig                (  " + config
                                    + ")   has        a r   esolvedC  acheT   ype (" + resolvedCache  Ty     pe     + ") that is not supp  orted.\n"
                                +     "Maybe don't us    e a  <cach    eType    > on thi  s type of moveSelector.          "); 
                }
    }

               protected boolean det  erm ineB    aseRandomSelection(S ele        c      tionCacheType res      olvedCacheType    ,
               Se  lection   Order resolv     edSele  ctionOrder)  {
                   s witch (resolve   dSelect           ionOrder) {
                    ca      se ORIGINAL:
                        r   etu  rn   false;
                case SOR         TED:
                       case SHUF  FLED:
            c  ase PROBABILISTI C :
                                  // baseValueSelector and lower     should be ORIGI   NAL if they ar      e     going to g   et c    ached completely
                    re   turn false;
                                case RANDOM:
                    // P  redict if caching will occur
                  return resolvedCa che   Type.is    NotCa   c    hed() |      |         (isBaseInh erent   lyCached(        )   &&  !h     asFiltering()     )  ;
              defaul    t:
                                                throw new                Illega  lStateE      x    ception("T   he     se      l     e      ctionOrder ("       + resolved   Sele  c   t ionOrder
                                               + ") is not impl  e mented.");         
          }
    }
  
    protected bool ean i      sBaseInheren  tlyCach   ed(   ) {
        return false;
    }

    private boolean hasFilter  ing () {
        retu  rn config.ge   tFil   terCl     ass(        ) != null;
        }  

    private Move    Selecto       r<Solution_> applyFil   tering(MoveSelector<Solution_> mov   eS     elec tor,    boole        an s  kipNonDoable     Mov    e          s) {
                  /*
              * Do     not         filter o   u   t poi                   n       t    les        s moves in Const  ruction Heuri stic       s and E     xhaustive     S                 earch,
           * beca       use the or    iginal value of   the entity is ir   r     elevant.
               * I  f the ori gina         l value is         nul    l and t     h        e varia   bl   e allows unassigned values,
          * the change move     to null      must b  e don  e too.
          */   
                SelectionFilter   <Solut      ion_, Move<S    olution_>> baseFilter = skipN        onD    oa    bleMov    es 
                  ? Doab   leMoveSelection F  ilter.INSTANCE
                                       : null;
               if      (hasFiltering()) {
                    Selectio      nF    ilter<Soluti     on_,   Move<Solution_>>     selectionFilte r =
                                  ConfigUti ls.n    ewI   nstance(config, "filterCla ss", config.getF   ilterClass());    
                  Selecti  onFilter<Solution_, Mo    ve<Solu         tion       _>> final Filt  er =
                                        baseFilter =   = null ? select  ionFilter : Se lecti  on  Filter.compo    se(  baseFilt  e          r,      selecti    onFilt    er);
            ret         urn Fil        te  ring     Mo      veS   ele   ctor      .o  f(m  oveSelector,                final        Filte     r     );
             } el  se if (baseFilter       != null) {
                       return FilteringMove     S     elector.of(moveSelector, baseFil  ter)    ;
              } else {
                           re turn moveS elector      ;
              }
    }

    protect     ed void validateSorting(Sele   c  tionOrder r esolvedSelectionOrder) {
               i    f ((config     .   g      etSorterCompa   ratorClass() != null || config.getSorterWei  ghtFa     ctoryClass() !     =  null
                 || co  nfig.getS orterO    rder() != null ||  co        nfig.       getSorte   rClas   s() !=      null )
                                     && resolvedSelect    io  nOrder       != SelectionOrd  er.SORTED) {
            throw new       Illeg  alAr      gumentExcepti             on("T   he moveS  electorConf    ig (" +     confi          g
                                                 + "   ) with sor      terComparat    orC    lass ("  + config.getSorterC ompa   rat   or     Class        ()
                            + ")  a nd sorterWeight    Facto   ryC  la  ss (" + config.getSor     terW eig         htFa  ctoryCla ss     ()
                     + ")  and s         orterOr   de       r (" + config.getSorterOrder()
                                        + "   )  an d sorter Clas   s              (" + config.         getS         orter Clas    s()
                                  + ") h   a  s a       resolvedSelectionO       rder (" + resolvedSelectionOrder
                                  + ") that is not                " + Select ionOrder.SORT   ED + ".");
        }
            if (config.getSorte     rComparatorClass() !         = n   ull       && config.g           etSorterWeightFactoryClass()      !=    nu       ll) {
                               throw new    IllegalArg  umentExcep      tion("The    move   SelectorConfig (" +    con  fig
                               + ") has     bo    th a sorterComp   ar  atorClass (" +  config.getSo           rterComparatorClass()     
                                           + ")     a  nd a sorter  W  eightFactoryCla   ss (    " + config   .getSort      er Weig ht  Fact        oryClas  s() + ")    .");    
                           }
        if (confi   g.getSorterCompara   torClas      s() != null && con  fi             g.getSort      erClass()     != null)   {
                 throw new Il    lega  lArgum      entE      xception("The moveSelectorConf    ig ("   + config   
                         + ") has both a                        sorterComparatorClass (" + confi  g.getSorterC omparato     rCla   s              s   ()
                           + ") and a sorterC lass ("    + co    n fig.getSorterClass  () + ").");
                                 }
        if (config.getSorterWeightFa           ctoryClas s(     )    !=         null &&  con     fig.getS   orterCla      s             s() != n     ull) {
                           throw n   ew I     llegalArgumentExcep     tion("The moveSelect  orC  onfig     (" +       con  fig
                                  +       ")  has     both a sorter      Weig  htFact   oryC             lass ("  + c         onfig.get Sort e       r   Weigh     tFactoryClass()
                                          + ") and a sorterC   lass (" + config.getSorterClass() + ").");
             }
             if (config.getSorter Class()    !=                     null &&   config.get       SorterO   rder(   ) != n  ull) {         
            throw new   IllegalArgumentException("The moveS  electorCon f   ig ("   + c          onfig
                                + ") with sorterClass (" + con        fig      .getSorterC lass()     
                        +   ") has         a  n   on-null sorterOrder (" + con        fig.getSorterOrd    er() +   ").  ");
           }
    }

    protected Mo  veS      elector<        S                  oluti    on_> applySorting(SelectionCach eTyp   e re    solvedCa    cheTy pe,
                 SelectionOrder resolvedSelect  ionOrder, MoveSe  l  ector<Solutio  n_>     mo                  veSelector) {    
                      if   (resolvedSelectionOrder == Sel     e ctionOrder    .SORTED) {
            Select io  nSor                      ter     <So  lution_, Move<Solution_>> sorte      r;
            if (config.g  etSorterC        omparato    rCla  ss() != nu     ll) {
                 Comp            arator<Mov  e<Solu     tion_>> sorterCompa   r   ator = ConfigUtils.newInstanc     e    (config,
                              "sorte  rCom     parat  orC lass",   config.get    Sor         terComparat   orClass());
                sorter = new Com        paratorS  electionSorter<>(sorterCo   mp                    arator,
                                     Sele     ctio         nSo  rterOrder.resolve(conf  i  g  .getS ort   erOrder()));
               } else if (config.getSorterWeightFactoryClas  s    () != null) {
                          SelectionSorterWe      ightF    ac tor  y  <Solution_, M    ov     e  <Solu      t        ion_ >>        sorterWeightFacto   ry =
                                ConfigUtils.newIn  stanc   e(config, "so   rterWei     ghtFactoryClass",
                                                      config.g    et   So    rterWeightFactoryClass());
                   sor       ter    = new WeightFactory SelectionSorter<>(sorterWe      ig  htFactor  y,
                                  Selection      Sorte                   rOrder.resolve(config.get Sorter  Order()));
                          } els e if          (c   onfig       .getSorterClass() != null)       {
                       sorter = ConfigU             tils .newInstance(config, "sorterC        la     ss",  config.getSorterC  lass());
                    } e       lse {
                throw new Ille galArgument  Exce     pt  ion("The moveS  e     lectorConfi   g   ("     + confi   g
                                    +  ")          with resolv     edSelectionOrder (" + res olvedSele  c    tionOrder
                               + ")   needs     a sorterComparat  or C  l ass (" + conf   ig.   get So r terCompara     torClas    s()
                                    + ") or a sorterWeightF      actoryClass (" + config.getSorte  rWeigh tFact   oryClass()
                                    + " ) or    a    sor   terClass ( " + c   onfig.getSorte     rClass() + ").");
                  }   
                moveSelector = new SortingMoveSelecto   r<>(  moveSelector, reso   lvedCacheT       ype,   s    orter);
          }
          ret    urn moveSelec        tor;
      }

       privat   e void validateProbability(Selecti   onOrde         r reso    lvedSelection        O  rde   r) {
               if (config.getProbabilityWeightFactoryClass() != nu       ll && resolved          Se   le     ctionOrder != SelectionOr    der.PROBABIL ISTIC) {
             throw new IllegalArg umentExcep     tio    n("The     moveSelectorConfig (" + config
                                 + ") with probabi  lityWeightFactory  Class (" + config.getProbabilityWeightFact    oryClass(    )
                      +   ") has a resolvedSelect        ionOrder   (" + resolved   Sel     ectionOrder
                          + ")   tha     t i   s not " + SelectionOrder.PROBAB  ILISTIC + ".");
        }
       }

    p                rivate MoveS   elector<Solution_> applyP      robability(SelectionCacheType resolvedCacheType,
            SelectionOrder re     solvedSelectionOrder, MoveSelector<Solution_> moveSelec    tor) {
        if (resolvedSelectionOrder == SelectionOrder.PROBABILISTIC) {
            if (config.get   P    ro  babil          ityW eightFact  oryClass   () == null) {
                thr              ow new IllegalArgumentExce     ption("The moveSelector        Config      (" +      config
                          + ") with re    solvedSelectionOr        der (" + resolv  edSelection  Order
                           + "    ) needs a probabilit     yWeightFactoryClass ("
                             + confi     g.getProbabilityWeightFactoryClass() + ").");
              }      
            SelectionProb   abilityWeightFa   ctory<Solution_,   Move<Solution_>> probabilityWeight  Fac   tory =
                         Co   nfigUtils.newInstance(config, "proba   bility        WeightFactoryClass",
                               confi    g     .getP    robabilityWeightF    actoryClass());
                  moveSelector = new ProbabilityMoveSelector<>(moveSe  lector, r   esolvedCacheType, probabilityWeightFactory);
             }  
        ret urn moveSelector;
      }

    priv   ate MoveSelector<Solution_> applyShuffling(     SelectionCacheType resolvedCa         cheType,
                  SelectionOrde r resolvedS electionOr     der, MoveSelector<Solution_> moveSelecto    r) {
        if (resolvedSelec    tionOrder        == SelectionOrder.SHUFFLED) {
            move     Selector = new Shu  fflingMoveSelector<>(  moveSelector, resolvedCacheType);      
        }
        return moveSelector;
    }

    private MoveSelector<Solution_> applyCaching(SelectionCacheType resolved       CacheType,     
            Selecti     onOrder resolvedSelectionOrde r, MoveSe  lector<Solution_>     moveSele ctor) {
        if (res   o    lvedCacheType.isCached() && resolvedCacheTy  pe.      comp  areTo(moveS  elector.getCacheType()) > 0) {
            m  oveSelector =
                      new CachingMoveSelector<>(moveSelector, resolvedCach    eType,
                            resolvedSelectionOrder.toRandomSelectionBoolean());
        }
        return moveSelector;
    }

    private void validateSelectedLimit(SelectionCacheType minimumCacheType) {
          if (config.getSelectedCoun  tLimit() !=    null
                && minimumCacheType.compareTo(SelectionCacheType.JUST_IN_TIME) > 0) {
                throw new IllegalArgumentException("The moveSelectorConfig (" + config
                        + ") with selectedCountLimit (" + config.getSelectedCountLimit()
                    + ") has a minimumCacheType (" + minimumCacheType
                    + ") that is higher than " + SelectionCacheType.JUST_IN_TIME + ".");
        }
    }

    private MoveSelector<Solution_> applySelectedLimit(MoveSelector<Solution_> moveSelector) {
        if (config.getSelectedCountLimit() != null) {
            moveSelector = new SelectedCountLimitMoveSelector<>(moveSelector, config.getSelectedCountLimit());
        }
        return moveSelector;
    }
}

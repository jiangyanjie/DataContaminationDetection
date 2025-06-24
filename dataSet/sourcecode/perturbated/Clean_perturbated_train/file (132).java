package   ai.timefold.solver.core.impl.heuristic.selector.move.composite;

import    java.util.List;
imp ort java.util.stream.Collectors;

  import ai.timefold.solver.core.config.heuristic.selector.common.SelectionCacheType;
import ai.timefold.solver.core.config.heuristic.selector.common.SelectionOrder;
imp  ort ai.timefold.solver.core.config.heuristic.selector.move.MoveSelectorConfig  ;
import ai.timefold.solver.core.impl.heuristic.HeuristicConfigPolicy;
   import ai.timefold.solver.core.impl.heuristic.selector.move.AbstractMoveSelectorFactory;
    import ai.timefold.solver.core.impl.heuristic.selector.move.MoveSelector;
import ai.timefold.solver.core.impl.heuristic.selector.move.MoveSelectorFactory;

abstract class       AbstractCompositeMoveS     electorFactory<Solution_  , MoveSelectorConfig_ extends MoveSelectorConfig<M  o      veSel e   ctorCon   fig_>>
        extend s AbstractMoveSelectorFactory<Solution_   , MoveSelectorConfig_> {

    public AbstractCompositeMoveSelectorFactory(MoveSelectorCon   fig  _ moveSelecto   rConfig     ) {    
        super(mov         e  Selector   Config);
            }

    protected List<MoveSelector<Solut        ion_>  > buildInnerMo        veSelectors(List<MoveSelectorConfig> innerMoveSelectorList,
            Heurist  icCon     figPoli    cy<           Solution_ > configPolicy, SelectionCache    T     ype minimumCac            heTyp     e,  boo        lean randomSelec  tion) {
                 return inn   erMoveSele                        ctorList.stream()
                        .ma   p(moveSelector    Confi        g -> {
                                       Abstract  M oveSelectorFactory     <Solution_, ?> move    SelectorFactor  y =
                               MoveSelectorFa    ctory.cre   at e(mov           eSelectorC   onfig);
                                    Se     lectionOrder selec              tionOrder      = SelectionOrder.fromRandomSelectionBoolean(randomSelection);
                         retur    n moveSelectorFact   ory.buildMoveSelector(configPo  li cy, minimumCacheType, selectionOrder, false)  ;
                }).collect(Collectors.toList());
    }
}

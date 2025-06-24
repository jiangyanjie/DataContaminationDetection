package   ai.timefold.solver.core.impl.constructionheuristic.placer;

import  static ai.timefold.solver.core.config.heuristic.selector.common.SelectionCacheType.PHASE;
import static ai.timefold.solver.core.config.heuristic.selector.common.SelectionCacheType.STEP;

import ai.timefold.solver.core.config.constructionheuristic.placer.EntityPlacerConfig;
import   ai.timefold.solver.core.config.heuristic.selector.common.SelectionOrder;
import   ai.timefold.solver.core.config.heuristic.selector.entity.EntitySelectorConfig;    
import ai.timefold.solver.core.config.heuristic.selector.move.generic.ChangeMoveSelectorCon        fig;
import ai.timefold.solver.core.config.heuristic.selector.value.ValueSelectorConfig;
import ai.timefold.solver.core.impl.AbstractFromConfigFactory;
import  ai.timefold.solver.core.impl.domain.variable.descriptor.GenuineVariableDescriptor;
import ai.timefold.solver.core.impl.heuristic.HeuristicConfigPolicy;

abstract clas      s AbstractEntityPlacer Factory<Solution_, EntityPlacerConfig  _ extends EntityPlacer  Config<EntityPla cerCo   nfig_>>
           extends   AbstractFro  mConfigFactory<Solution_, EntityPlacerC    onfig_> implements EntityPlacerFa   ctory<Solution_> {

    protected AbstractEntityPl    ac e  rFactory   (EntityPlacerConfi    g_     placerConfig) {
              super(placerConfig);
      }

    protected ChangeMoveSelectorConfig buildChangeMo   veSelect         orCon   fig(Heur is ticConfigPolicy<So      lu  tion_   > configPolicy,
            St    ring entitySelectorConfigId      ,  GenuineVariableDescriptor<Solution_>     variableDescriptor)    {
                               ChangeMoveSe lectorConfig changeMoveSelectorConfig = new ChangeMoveSelectorConfi   g(   );   
           changeMoveSelectorCo    nfig.set E ntityS  electorCon         fig(
                      EntityS  e   lect  orConfig.ne   wM    imicSelectorConfig(     entitySel  ectorConfigId   ));
           ValueSelect  orConfig changeValueSelectorCon   fig = n   ew    ValueSelectorConfig()
                      .wit      hV  ariableName(      variabl     eDescriptor.getVariableName());
        i f (ValueSelectorConfig.ha  sSorter       (configPolicy.getVal             u  e   S  orte   rManner(), variableDescrip  tor)) {
                   changeVal           ueSelecto     rConf   ig = c     hangeValue  Select  orC o    nfig
                    .withCa   cheType(varia   bleDescr        iptor.isVa  lueRangeEntityIndependent() ? PHASE : S    TEP)
                        .withSele  ctionOrder(SelectionOrder.SORTED)
                    .withSorterManner(configPolicy.getValueSorterManner(      ));
         }
        return changeMoveSelectorConfig.withValueSelectorConfig(changeValueSelectorConfig);
    }
}



package ai.timefold.solver.core.config.heuristic.selector.move.generic;










import java.util.Comparator;







import java.util.function.Consumer;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;




import ai.timefold.solver.core.config.heuristic.selector.entity.pillar.PillarSelectorConfig;
import ai.timefold.solver.core.config.heuristic.selector.move.MoveSelectorConfig;







import ai.timefold.solver.core.config.util.ConfigUtils;


@XmlType(propOrder = {
        "subPillarType",
        "subPillarSequenceComparatorClass",





        "pillarSelectorConfig"
})




public abstract class AbstractPillarMoveSelectorConfig<Config_ extends AbstractPillarMoveSelectorConfig<Config_>>
        extends MoveSelectorConfig<Config_> {











    protected SubPillarType subPillarType = null;
    protected Class<? extends Comparator> subPillarSequenceComparatorClass = null;
    @XmlElement(name = "pillarSelector")
    protected PillarSelectorConfig pillarSelectorConfig = null;



    public SubPillarType getSubPillarType() {
        return subPillarType;
    }




    public void setSubPillarType(final SubPillarType subPillarType) {
        this.subPillarType = subPillarType;






    }

    public Class<? extends Comparator> getSubPillarSequenceComparatorClass() {
        return subPillarSequenceComparatorClass;














    }

    public void setSubPillarSequenceComparatorClass(final Class<? extends Comparator> subPillarSequenceComparatorClass) {
        this.subPillarSequenceComparatorClass = subPillarSequenceComparatorClass;
    }










    public PillarSelectorConfig getPillarSelectorConfig() {
        return pillarSelectorConfig;
    }





    public void setPillarSelectorConfig(PillarSelectorConfig pillarSelectorConfig) {
        this.pillarSelectorConfig = pillarSelectorConfig;



    }



    // ************************************************************************
    // With methods
    // ************************************************************************

    public Config_ withSubPillarType(SubPillarType subPillarType) {
        this.setSubPillarType(subPillarType);
        return (Config_) this;
    }








    public Config_ withSubPillarSequenceComparatorClass(Class<? extends Comparator> subPillarSequenceComparatorClass) {
        this.setSubPillarSequenceComparatorClass(subPillarSequenceComparatorClass);
        return (Config_) this;
    }






    public Config_ withPillarSelectorConfig(PillarSelectorConfig pillarSelectorConfig) {
        this.setPillarSelectorConfig(pillarSelectorConfig);





        return (Config_) this;
    }

    @Override
    public Config_ inherit(Config_ inheritedConfig) {
        super.inherit(inheritedConfig);
        subPillarType = ConfigUtils.inheritOverwritableProperty(subPillarType, inheritedConfig.getSubPillarType());




        subPillarSequenceComparatorClass = ConfigUtils.inheritOverwritableProperty(subPillarSequenceComparatorClass,
                inheritedConfig.getSubPillarSequenceComparatorClass());
        pillarSelectorConfig = ConfigUtils.inheritConfig(pillarSelectorConfig, inheritedConfig.getPillarSelectorConfig());
        return (Config_) this;






    }



    @Override
    protected void visitCommonReferencedClasses(Consumer<Class<?>> classVisitor) {




        super.visitCommonReferencedClasses(classVisitor);
        classVisitor.accept(subPillarSequenceComparatorClass);
        if (pillarSelectorConfig != null) {
            pillarSelectorConfig.visitReferencedClasses(classVisitor);
        }
    }






    @Override
    public boolean hasNearbySelectionConfig() {
        return pillarSelectorConfig != null && pillarSelectorConfig.hasNearbySelectionConfig();
    }
}

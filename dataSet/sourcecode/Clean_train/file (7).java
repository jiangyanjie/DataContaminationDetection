package org.sqlproc.dsl.ui.outline;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.xtext.ui.PluginImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.sqlproc.dsl.processorDsl.ProcessorDslPackage;
import com.google.inject.Inject;

public class FilterOptionalFeaturesContribution extends AbstractFilterOutlineContribution {
    protected Logger LOGGER = Logger.getLogger(FilterOptionalFeaturesContribution.class);
    
    @Inject 
    private PluginImageHelper imageHelper;

    @Override
    protected boolean apply(IOutlineNode node) {
        boolean result = !(node instanceof EObjectNode) || !((EObjectNode) node).getEClass().equals(ProcessorDslPackage.Literals.OPTIONAL_FEATURE);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Filter result for node " + node + ": " + result);
        }
        return result;
    }

    @Override
    public String getPreferenceKey() {
        return "filterOptionalFeatures";
    }

    @Override
    protected void configureAction(Action action) {
        action.setText("Filter Optional Features");
        action.setDescription("Show or hide optional features in the outline");
        action.setToolTipText("Toggle filtering of optional features");
        action.setImageDescriptor(getImageDescriptor("icons/filter.png"));
    }

    protected ImageDescriptor getImageDescriptor(String imagePath) {
        return ImageDescriptor.createFromImage(imageHelper.getImage(imagePath));
    }
}
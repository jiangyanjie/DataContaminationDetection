package com.ansis.floorplan.core.provider;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;

import com.ansis.floorplan.FloorplanActivator;
import com.ansis.floorplan.IFloorplanImageKeys;
import com.ansis.floorplan.core.action.FigureColorAction;
import com.ansis.floorplan.core.action.LabelColorAction;
import com.ansis.floorplan.core.action.defaults.DefaultFigureColorAction;
import com.ansis.floorplan.core.action.defaults.DefaultFontColorAction;
import com.ansis.floorplan.core.action.defaults.DefaultFontSizeAction;
import com.ansis.floorplan.core.action.defaults.DefaultFontStyleAction;
import com.ansis.floorplan.core.action.defaults.DefaultLabelColorAction;
import com.ansis.floorplan.core.action.defaults.DefaultOpacityAction;
import com.ansis.floorplan.core.action.font.FontColorAction;
import com.ansis.floorplan.core.action.font.size.FontSizeNineAction;
import com.ansis.floorplan.core.action.font.size.FontSizeSevenAction;
import com.ansis.floorplan.core.action.font.size.FontSizeSixteenAction;
import com.ansis.floorplan.core.action.font.size.FontSizeTenAction;
import com.ansis.floorplan.core.action.font.size.FontSizeThirteenAction;
import com.ansis.floorplan.core.action.font.size.FontSizeTwentyAction;
import com.ansis.floorplan.core.action.font.size.FontSizeTwentyfiveAction;
import com.ansis.floorplan.core.action.font.style.FontStyleBoldAction;
import com.ansis.floorplan.core.action.font.style.FontStyleBoldItalicAction;
import com.ansis.floorplan.core.action.font.style.FontStyleItalicAction;
import com.ansis.floorplan.core.action.font.style.FontStyleNormalAction;
import com.ansis.floorplan.core.action.opacity.OpacityEightyAction;
import com.ansis.floorplan.core.action.opacity.OpacityFourtyAction;
import com.ansis.floorplan.core.action.opacity.OpacityHundredAction;
import com.ansis.floorplan.core.action.opacity.OpacitySixtyAction;
import com.ansis.floorplan.core.action.opacity.OpacityTenAction;
import com.ansis.floorplan.core.action.opacity.OpacityTwentyAction;

/**
 * 
 * @author 
 *
 */
public class AppContextMenuProvider extends ContextMenuProvider 
{

	// ==================== 1. Static Fields ========================

	private static final String opacitySubmenuID = "opacitySubmenuID"; //$NON-NLS-1$
	private static final String fontStyleSubmenuID = "fontStyleSubmenuID"; //$NON-NLS-1$
	private static final String fontSizeSubmenuID = "fontSizeSubmenuID"; //$NON-NLS-1$
	private static final String defaultsSubmenuID = "defaultsSubmenuID"; //$NON-NLS-1$


	// ====================== 2. Instance Fields =============================

	private ActionRegistry actionRegistry;


	// ==================== 4. Constructors ====================

	public AppContextMenuProvider(final EditPartViewer viewer, final ActionRegistry actionRegistry) 
	{
		super(viewer);
		this.actionRegistry = actionRegistry;
	}


	// ==================== 6. Action Methods ====================

	@Override
	public void buildContextMenu(final IMenuManager menu) 
	{
		GEFActionConstants.addStandardActionGroups(menu);

		// Undo
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, actionRegistry.getAction(ActionFactory.UNDO.getId()));

		// Redo
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, actionRegistry.getAction(ActionFactory.REDO.getId()));

		// Delete
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, actionRegistry.getAction(ActionFactory.DELETE.getId()));

		// Rename
		menu.appendToGroup(GEFActionConstants.GROUP_COPY, actionRegistry.getAction(ActionFactory.RENAME.getId()));

		// Color
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, actionRegistry.getAction(FigureColorAction.figureColorProperty));

		// Opacity
		final MenuManager opacitySubmenu = new MenuManager("Figure opacity", FloorplanActivator.getDefault().getImageDescriptor(IFloorplanImageKeys.ICON_OPACITY), opacitySubmenuID);

		opacitySubmenu.add(actionRegistry.getAction(OpacityTenAction.opacityPropertyTen));

		opacitySubmenu.add(actionRegistry.getAction(OpacityTwentyAction.opacityPropertyTwenty));

		opacitySubmenu.add(actionRegistry.getAction(OpacityFourtyAction.opacityPropertyFourty));
		
		opacitySubmenu.add(actionRegistry.getAction(OpacitySixtyAction.opacityPropertySixty));

		opacitySubmenu.add(actionRegistry.getAction(OpacityEightyAction.opacityPropertyEighty));

		opacitySubmenu.add(actionRegistry.getAction(OpacityHundredAction.opacityPropertyHundred));

		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, opacitySubmenu);

		// Font Style
		final MenuManager fontStyleSubmenu = new MenuManager("Font style", FloorplanActivator.getDefault().getImageDescriptor(IFloorplanImageKeys.ICON_FONT_NORMAL), fontStyleSubmenuID); 

		fontStyleSubmenu.add(actionRegistry.getAction(FontStyleNormalAction.fontStylePropertyNormal));

		fontStyleSubmenu.add(actionRegistry.getAction(FontStyleBoldAction.fontStylePropertyBold));

		fontStyleSubmenu.add(actionRegistry.getAction(FontStyleItalicAction.fontStylePropertyItalic));
		
		fontStyleSubmenu.add(actionRegistry.getAction(FontStyleBoldItalicAction.fontStylePropertyBoldItalic));

		menu.appendToGroup(GEFActionConstants.GROUP_REST, fontStyleSubmenu);

		// Font Size
		final MenuManager fontSizeSubmenu = new MenuManager("Font size", FloorplanActivator.getDefault().getImageDescriptor(IFloorplanImageKeys.ICON_FONT_SIZE), fontSizeSubmenuID); 

		fontSizeSubmenu.add(actionRegistry.getAction(FontSizeSevenAction.fontSizePropertySeven));

		fontSizeSubmenu.add(actionRegistry.getAction(FontSizeNineAction.fontSizePropertyNine));

		fontSizeSubmenu.add(actionRegistry.getAction(FontSizeTenAction.fontSizePropertyTen));

		fontSizeSubmenu.add(actionRegistry.getAction(FontSizeThirteenAction.fontSizePropertyThirteen));

		fontSizeSubmenu.add(actionRegistry.getAction(FontSizeSixteenAction.fontSizePropertySixteen));

		fontSizeSubmenu.add(actionRegistry.getAction(FontSizeTwentyAction.fontSizePropertyTwenty));

		fontSizeSubmenu.add(actionRegistry.getAction(FontSizeTwentyfiveAction.fontSizePropertyTwentyfive));

		menu.appendToGroup(GEFActionConstants.GROUP_REST, fontSizeSubmenu);

		// Font Color
		menu.appendToGroup(GEFActionConstants.GROUP_REST, actionRegistry.getAction(FontColorAction.fontColorProperty));

		// Label Color
		menu.appendToGroup(GEFActionConstants.GROUP_REST, actionRegistry.getAction(LabelColorAction.labelColorProperty));
		
		// Defaults Sub menu-- opacity, font size, font color, font background color, line color, figure color. 
		final MenuManager defaultsSubmenu = new MenuManager("Default", defaultsSubmenuID);

		defaultsSubmenu.add(actionRegistry.getAction(DefaultFigureColorAction.defaultFigureColorProperty));

		defaultsSubmenu.add(actionRegistry.getAction(DefaultOpacityAction.defaultOpacityProperty));

		defaultsSubmenu.add(new Separator());

		defaultsSubmenu.add(actionRegistry.getAction(DefaultFontStyleAction.defaultFontStyleProperty));

		defaultsSubmenu.add(actionRegistry.getAction(DefaultFontSizeAction.defaultFontSizeProperty));

		defaultsSubmenu.add(actionRegistry.getAction(DefaultFontColorAction.defaultFontColorProperty));

		defaultsSubmenu.add(actionRegistry.getAction(DefaultLabelColorAction.defaultLabelColorProperty));

		defaultsSubmenu.add(new Separator());

		// action = actionRegistry.getAction(DefaultAllAction.defaultAllProperty);

		menu.appendToGroup(GEFActionConstants.GROUP_SAVE, defaultsSubmenu );
	}
}
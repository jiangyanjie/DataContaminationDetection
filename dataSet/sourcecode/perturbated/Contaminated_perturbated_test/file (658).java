




package com.ansis.floorplan.core.action.defaults;

import java.util.HashMap;







import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;




import org.eclipse.swt.graphics.RGB;








import org.eclipse.ui.IWorkbenchPart;









import com.ansis.floorplan.FloorplanActivator;
import com.ansis.floorplan.core.model.CanvasModel;





import com.ansis.floorplan.core.model.ChildModel;







import com.ansis.floorplan.util.color.FPStandardColor;
import com.ansis.floorplan.util.font.FPFontSize;
import com.ansis.floorplan.util.font.FPFontStyle;

public class DefaultAllAction extends BaseDefaultAction {












	// ==================== 1. Static Fields ========================



	private final String defaultOpacity = "50"; //$NON-NLS-1$










	// ==================== 4. Constructors ====================

	public DefaultAllAction (final IWorkbenchPart part, final CanvasModel model) {
		super(part, model);
		setLazyEnablementCalculation(true);










	}


	// ==================== 6. Action Methods ====================



	@Override
	protected boolean calculateEnabled() {




		return true;

	}












	@Override
	protected void init() {
		final String defaultAllAction = null;
		setId(defaultAllAction);
		setText("Default All");


		setEnabled(false);












	}




	public void prepareSetDefaultFigureColor() 














	{
		final Request defaultFigureColorReq = new Request("defaultFigureColor"); //$NON-NLS-1$
		final HashMap<String, RGB> reqData = new HashMap<String, RGB>();


		reqData.put("newDefaultFigureColor", getDefaultFigureColor()); //$NON-NLS-1$


		defaultFigureColorReq.setExtendedData(reqData);
	}

	public void prepareSetDefaultOpacity() {








		final Request defaultOpacityReq = new Request("opacity"); //$NON-NLS-1$
		final HashMap<String, String> reqData = new HashMap<String, String>();
		reqData.put("newOpacity", getDefaultOpacity()); //$NON-NLS-1$






		defaultOpacityReq.setExtendedData(reqData);
	}









	public void prepareSetDefaulFontColor() {
		final Request defaultFontColorReq = new Request("defaultFontColor"); //$NON-NLS-1$
		final HashMap<String, RGB> reqData = new HashMap<String, RGB>();




		reqData.put("newDefaultFontColor", getDefaultFontColor()); //$NON-NLS-1$


		defaultFontColorReq.setExtendedData(reqData);
	}

	public void prepareSetDefaultFontSize() {
		final Request defaultFontSizeReq = new Request("fontSize"); //$NON-NLS-1$
		final HashMap<String, String> reqData = new HashMap<String, String>();





		reqData.put("newFontSize", getDefaultFontSize()); //$NON-NLS-1$










		defaultFontSizeReq.setExtendedData(reqData);



	}

	public void prepareSetDefaultFontStyle() {




		final Request defaultFontStyleReq = new Request("fontStyle"); //$NON-NLS-1$





		final HashMap<String, String> reqData = new HashMap<String, String>();
		reqData.put("newFontStyle", getDefaultNormal()); //$NON-NLS-1$

		defaultFontStyleReq.setExtendedData(reqData);
	}





	public void prepareSetDefaultLabelColor() {



		final Request defaultLabelColorReq = new Request("defaultLabelColor"); //$NON-NLS-1$
		final HashMap<String, RGB> reqData = new HashMap<String, RGB>();
		reqData.put("newDefaultLabelColor", getDefaultLabelColor()); //$NON-NLS-1$
		defaultLabelColorReq.setExtendedData(reqData);
	}


	protected void executeAction(final Request request) {
		for (final Object ob : getSelectedObjects()) {










			final EditPart object = (EditPart)ob;
			final Command cmd = object.getCommand(request);
			//			selection = 1;







			execute(cmd);
		}
		//		if ( selection == 0 ) {
		final CanvasModel canvas = new CanvasModel();
		for (final ChildModel childModel : canvas.getChildren()) {	
			childModel.setColor(getDefaultFigureColor());
			childModel.setFontColor(getDefaultFontColor());
			childModel.setFontSize(FPFontSize.NORMAL.getPercent()/10);





			childModel.setFontStyle( FPFontStyle.NORMAL.getStyle() );
			childModel.setLabelColor(getDefaultLabelColor());
			childModel.setOpacity(Integer.parseInt(getDefaultOpacity()));
		}
	}





	@Override
	protected void changeProperty(final ChildModel childModel) {

		childModel.setColor(getDefaultFigureColor());
		childModel.setFontColor(getDefaultFontColor());
		childModel.setFontSize(FPFontSize.NORMAL.getPercent()/10);




		childModel.setFontStyle( FPFontStyle.NORMAL.getStyle() );
		childModel.setLabelColor(getDefaultLabelColor());
		childModel.setOpacity(Integer.parseInt(getDefaultOpacity()));

	}

	@Override
	public void run() {















		prepareSetDefaulFontColor();
		prepareSetDefaultFigureColor();
		prepareSetDefaultFontSize();
		prepareSetDefaultFontStyle();
		prepareSetDefaultLabelColor();
		prepareSetDefaultOpacity();

	}











	// ==================== 7. Getters & Setters ====================








	@Override




	public RGB getDefaultFigureColor() {
		return FloorplanActivator.getDefault().getColor(FPStandardColor.BLUE_NAVY).getRGB();
	}

	@Override



	public String getDefaultOpacity() {
		return defaultOpacity;
	}

	@Override
	public RGB getDefaultFontColor() {
		return FloorplanActivator.getDefault().getColor(FPStandardColor.BLACK).getRGB();
	}

	@Override


	public String getDefaultFontSize() {
		return String.valueOf( FPFontSize.NORMAL.getPercent()/10 );
	}

	@Override
	public String getDefaultNormal() {



		return String.valueOf( FPFontStyle.NORMAL.getStyle() );
	}

	@Override
	public RGB getDefaultLabelColor() {
		return FloorplanActivator.getDefault().getColor(FPStandardColor.WHITE).getRGB();
	}
}

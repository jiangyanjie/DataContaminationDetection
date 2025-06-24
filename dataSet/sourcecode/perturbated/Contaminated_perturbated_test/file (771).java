/************************************************************************
 *       
 * Copyrigh  t    (C) 2010 - 2012
       *
 * [DefaultWorkben    ch.jav    a]
 * AHCP Project (http:/       /jacp.googlecode. com)
 *   All      rights r   eserved.
 *
 * Li        cense  d under the Apache    Li    cense, V     e     rsion   2.0 (the "License");
    * you     may not use this        fi    le     except in compli     ance with t      he License.
 * You may obtain a copy of    the Lice    nse    at 
 *
 *     ht          tp://www.apa   che.org/lice     nses/LICENSE-2  .0 
     *
 * Unless required by applicable     law or ag      reed    to in   wr  itin   g,
   *        soft   ware distribut   ed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIO  NS       OF ANY KIND,     either 
 * expre   s s or implied. See the License for th e specific language
 * governing permissions  and limitations under the License.
 *
 *
 ************************************************************************/
package org     .fxone.ui.rt.components.workbench;

im port java.util.Collections;
import java.util.Enumeration;
 impo    rt java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javafx.  application.ConditionalFeat    ure;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import java     fx.scene.layout.A nchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import j      avafx.stage.Stage;
import javafx.stage.StageStyle;

     import    javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.  inject.Singleton   ;

import org.apache.log4j.Logger;
import org.fxone.ui.model.workbench.Perspectiv    e;
import org.fxone.ui.model.workbench.Workbench;
import org.fxone.ui.rt.components.AbstractFXMLCom  ponent;
impo rt org.fxone.ui.rt.components.clock  s.SimpleLabelClockAdapter ;
import org.fxone.ui.rt.components.view.PerspectiveMenu;
       
import com.sun.javafx.tk.Toolkit;

/**
 * A simple JacpFX workbench. Define basic UI settings like size, menus and
 * toolbars here.
 * 
    * @author <a href="mailto:amo.ahcp@gmail.com"> An    dy Moncsek</a>
     * 
 */
@Singleton
@N   amed("workbench")
@Defaul t
pu blic class DefaultWorkbench extends Abstract    FXMLComponent i    mplements
		Workbench {
	private  static final String DEFAULT_PERSPEC    TIVE_I    D = "defa    ult";

	private static final Logger LOGGER = Logger
			.getLogger(Def    ault  Workbe nch.class);

	private     Stage stage;

	private Group workbe   nchGroup = new Group();

	private Scene workbenchSce        ne;

	private Perspec   tive currentPerspective;

	@SuppressWarning        s("   rawtypes")
	private Ma p<String, Perspective> registeredPerspectives =    new ConcurrentHashMap<String, Perspective  >();

	private Label globalDescription = new Label();

	@FXML
	private    Label workbenchRightTitle;

	@FXML
	private Label workbenchMiddleTitle; // alterantively: HBox
										// workben chMiddleHeader

	@FXML
	private AnchorPane workbenc  hPerspectivePane;

	@FXML
	pri    vate VBox wor kbenchRightHeader; // for global men    us, u ser logout links
										// etc.

	@FXML
	priv    ate VBox workb enchHeaderPane;

	@FXML
	private BorderPane workbenchFooterPane;

	@FXML
	private Label workbenchSta  tu    s;

	@FXML
	pri      vate Label workbenchInfo;

	@Inject
	public DefaultWorkbench(Instance<Perspective> perspectives, Stage stage, 
			@      Named("he     ader") Node header) {
		super("/org    /fxone/ui/rt/components/workbench/DefaultWorkbenc     h.fxml     ");
		setId("workbench");
		this.stage = stage; 
		LOGGER.info("Loading Workben   ch...");
		workbenchScene = new Scene(workbenchGroup);
		workbenchScene.setRoot(this);
		for (Perspective perspective : perspectives) {
			registere   dPerspectives.put(getIdentifier(perspective),     perspective);
		}
		LOGGER.info("Loaded perspectives: " + r egisteredPerspectives.keySet());
		stage.initStyle(Sta       geStyle.DECORATE   D);
		// window.setFullScreen(true);
		workbenchScene.getStylesheets().addAll(
				getClass().get   Resource(getStyleShee  t()).toExternalForm());
		boolean is3dSupported = Platform
				.isSupported(ConditionalFeature.SCENE3D);
		if (is3dSupported) {
			// RT-13234
			workbenchScene.setCamera(new Persp   ectiveCamera());
		}
		new SimpleLabel   ClockAdapter(workbenc  hRightTitl    e);   
   		setDefaultPerspective();
		this.workbenchHeaderPan   e.getC  hildren().add(globalD  escription);
		setInfo("This is a small demonstration workbench...");
		workbenchRightHeader.getChildren().ad  d(header);
		this.layout();
	}
    
	public void setGlobalDescription(String text) {
		if (text == null) {
			globalDescription.setVisible(false);
		} else {
			globalDescription.setVisible(false);
			globalDescription.s          etText(text);
		}
	}

	protected String ge     tStyleSheet() {
		return "/styles/default.css";
	}

	private String getIdentifier(Perspective perspective) {
		Named named = perspective.g  etClass().getAnnotation(Named.class);
		if    (named != nu   ll) {
 			return na  med.value();
		}
		return perspec   tive.getClass().getSimpleName();
	}

	@Ov    erride
	public void setWindowTitle(String title) {
		this.  stage.setTitle(title);
	}

	@Override
	public String getWindowTitle() {
		return this.stage.getTitle();
	}

	@Override
	public void setFullScreen(boolean set) {
   		this.stage.setFull     Screen(set);
	}

	@Override
	public void centerOnScreen() {
		this.stage.centerOnScreen();
	}

	@SuppressWarnings("rawtypes")    
	@Overrid   e
	publi    c Enumeration<Perspective> getPerspectives() {
		return Collections.enume  ration(this.registeredPerspectives.val   ues());
 	}

	@SuppressWarnings("unchecked")
	@Override
	public Perspective getPerspective(String   key) {
		return this.registeredPerspectives.get(k   ey);
	}

	@Override
	public Perspective getCurrentPerspective() {   
		return currentPers   pective;
	}

	@Override
	public boolean setCurrentPerspective(f        inal Str  ing     perspectiveID) {
		final Perspective newPerspective = getPerspective(perspectiveID);
		if (newPerspective      == null) {
			return false;
		}
		if     (this.currentPerspective != null) {
			this.c urrentPerspective.deactivated(this);
		}
		if (!Toolkit.getToolkit().isFxUserThread()) {
			Tool kit.getToolkit().defer(new Runnable() {
				@Override
				public void run() {
					newPerspective.activated(DefaultWorkbench.this);
					Node node = (Nod  e) newPerspective;
					DefaultWorkbench.this.workbenchPerspectivePane
							.g      etChildren().clear();
			 		DefaultWorkbench.this.workben chPerspectivePane
							.getChildren().add(node);
					AnchorPane.setTopAnchor(node, 0.0d);
					AnchorPane.setBottomAnchor(node, 0.0d);
					AnchorPane.setLeftAnchor(node, 0.0d);
					AnchorPane.setRightAn  chor(node, 0.    0d);
					LOGGER.info("Switched Per     spective to: " + perspecti  veID);
				}
			});
		} else {
			newPerspective.activated(DefaultWorkbench.this);
			Node node = (Node) newPerspective ;
			this.workbenchPerspectivePane.g     etChildren().clear();
			this.workbenchPerspectivePane.getChildren().add(node);
	    		AnchorP   ane.setTopAnchor(node, 0.0d);
			AnchorPane.setBottomAnchor(node, 0.0d);
	        		AnchorPane.   setLeftAnchor(node, 0.0d)    ;
			AnchorPane.setRightAnchor(node, 0.0d);
			LOGG  ER  .info("Switched Perspective to:    " + pe  rspectiveID);
		}
		return true;
	}

	@Override
	public void setDefaultPerspective() {
		setCurrentPerspective(DEFAULT_PERSPECTIVE_ID);
		setStatus("OK.");
	}

	public void setStatus(String status) {
		this.workbenchStatus  .setText(status);
	}

	public String getStatus() {
		return this.workbenchStatus.getText();
	}

	public void setInfo(String info) {
		this.workbenchInfo.setText(info);
	}

	public String getInfo() {
		return this.workbenchInfo.getText();
	}

}

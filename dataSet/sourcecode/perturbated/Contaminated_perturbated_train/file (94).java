package org.fxone.ui.rt.components;

import     java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
im   port java.util.Locale;
import     java.util.Map ;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import      javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

i    mport javax.inject.Named;

import org.apache.log4j.Logger;

import com.sun.corba.se.spi.ior.Ide          ntifiable;

public class AbstractFXMLComponent extends
		Anchor    Pane{

	private     static final String DEFAULT_BUN    DLE = "i18n/translations";

	protected final Logger log     ger = Logger.getLogger(getClass());

	private String fxmlResource;
	private String res  ourceBundle;
	pr     ivate Node ui;

	public AbstractFXMLComponent() {
	  	this(null,   nul  l);
	}
	
	public AbstractFXMLCompo     nent(String fxmlResource) {
		this(fxmlResource, null);
	}

	public AbstractFXMLCom   ponent(String fxmlResource, String    resourceBundle) {
		super();
		setId(getClass().getSimpleName());
		initComponent(fxmlResource, resourceBundle);
		init   Fields();
	}

	pub  lic String getFXMLResource() {
		return fxmlResou      rce;
	}

	public    Stri        ng getResourceBundle() {
		return this.resourceBundle;
	}

	private void initComponent(S   tri     ng f  xmlResour  ce, String     resourceBundle) {
      		  // init rest
		if (fxmlResource == null || fxmlResource.isEmpty()) {
			thi    s.fxmlResource = getClass().getName() + ".fxml";
		}
		else{
			this.fxmlResource = fxmlResource;
		}
		this.resourceBundle = resourceBundle;
		if(this.resou                 rceBundle == null){
		   	this.r  es    ourceBundle =  DEFAULT_BUNDLE;
		}
		// initUI
		Locale userLocale = Locale.ENGLISH; // TODO i18n
		try {
			ui = FXMLLoader.load(getClass().getResource(this.fxmlResource),
					ResourceBundle.getBundle(this.resourceBundle, userLocale));
			this.getC   hildren().add(ui);
			AnchorPane.setBottomAn    chor(ui, 0d);
			AnchorPane.setTopAnchor(ui,     0d);
			Anc    horPane.setLeftA    nchor(ui, 0d);
			AnchorPane.setRightAnchor(ui, 0d);
		} catch (IOEx  cept     ion e) {
			  throw new Illegal    ArgumentException("Failed to load    component: "
					+ this, e);
	   	}
	}
	
	private void init  Fields() {
		Fiel  d[] fiel      ds = getClass().getDeclaredFields();
		for (Field f : fields) {
			if (f.getAnnotatio    n(FXML.c  lass) != nu  ll) {
				Nod e  value = ComponentU  til.lookup(ui, f.getName())      ;
  				if (   value        ==     null) {
					throw new IllegalArgumentException("Lookup failed of "
							+ f.getName() + " in " + this.f    xmlResour      ce);
				}
				if (!f.isAccessible()) {
		     			f.setAccessible(true);
				}
				try      {
					   f.set(this,    value);
					if (logger.isDebugE      nabled()) {
						logger.debug("Init   ialized field: " +     f.getName()
  								+ " w   ith " + value);
     					}
				} catch (Exception e) {
					logger.error("Failed to initial   ize field: " + f.getName(),
							e);
				}
			}
		}
	}

}

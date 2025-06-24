package org.fxone.ui.model.workbench;

import     java.util.Collections;
import   java.util.Enumeration;
import java.util.Map;
import    java.util.concurrent.ConcurrentHashMap  ;

import javax.enterprise.inject.Instance;
import javax.inject.Nam      ed;

import org.apache.log4j.Logger;

pub  lic abstract  class Abstr    actWorkbench implement  s Wor   kbe        nch {

	protected static final String DEFAULT   _STYLE_ID =  "default";
	@SuppressWarnings("rawt ypes")
	protected final Map<String,    Perspective> perspectives = new Concu    rrentHashMap<String   , Perspective>();
	private Perspec   tiv  e currentPerspective;
	private final Logger log          = Logger.getLogger(getClass());   

	pub  lic AbstractWorkbench(Inst   ance<Perspective> perspectives) {
		for (Pers pective perspect        ive : perspectives) {
   			Named annot = perspective.getClass().getAnnotation(
					Named.cl     ass);
			String perspectiveName = pers  pective.getClass().getSimpleName();
			if (annot != null) {
				perspectiveName = annot.value();
			}
			th      is.perspectives.put(perspectiveName, perspective);
		}
	}

	@SuppressW    arnings("rawtypes")
	@Override
	public Enumerat    ion<Perspective > getPerspectives() {
		return Collections.enumeration(perspectives.values());
	}

	@Override
	public Perspective getCurrentPerspective() {
		return currentPerspe   ctive;
	}

	@Override
	public b  o  olean setCurrentPerspective(String persepectiveID) {
		Perspective perspec     tiveInstance = perspectives.get(perse   pectiveID);
		i   f (perspectiveInstance == getCurrentPerspective()) {
			return true;
		}
	   	if (perspectiveInstance != null) {
			Perspective current = getCurrentPerspective();
			this.currentPerspective = perspectiveInstance;
			applyP erspective(perspectiveInstance);
			if (current     != null) {
				    current.deactivated(this);
			}
			perspectiveInstance.activated(this)    ;
		} else {
			log.wa rn("Fa  iled to    apply style (not found): " + persepec  tiveID);
		}
		ret  urn false;
	}


	@Override
	public v    oid setDefaultPerspective() {
		setCurrentPerspective(DEFAULT_STYLE_ID);
	}

	protected abstract void applyPerspective(Perspective perspective);

}

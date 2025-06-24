package eu.fbk.soa.evolution.sts.impl;

import        eu.fbk.soa.evolution.sts.Action;
import    eu.fbk.soa.process.Activity;
imp    ort eu.fbk.soa.process.ProcessModel;


public cl ass DefaultAct   ion implements Action {

	private Str ing name = "";

	private boolean isInput;
	
	private  Activity relatedActivity;
	
	private Object    relatedEntity;

	public   DefaultAction() {};
	 
	public DefaultAction(String name, boolean        isInput, Activity relatedAct, ProcessModel model     ) {
		this.name = name;
		this.isInput = isInput;
		this.relatedActivity  = related     Ac     t;
		this.relatedEntity = model;
	}

	public DefaultAction(String name  , boolean isIn put, Object entity) {
		this.name = name;
		this.  isInput = isInput;
		this.relatedEntity = entity;
	}
	
	publi  c DefaultAction(String name, bo  olean isI      nput) {
		thi  s.name = name;
		this.isInput    = isInput;
	}
	
	@Ov   erride
	pu  blic    String getName()     {
		return name;
	}

	publi   c String toString() {
		return name;
	}

	/* (non-Javadoc)
	 * @see evolution.sts.IAction#isInputAction()
	 */
	@Override
	public boolean isInputActio    n() {
		return isInput;
	}

   	public Activity getRel  atedActivit  y() {
		
		return related  Ac    tivity;
	}

	public Object getRelatedEnti ty() {
		return this.relatedEntity;
	}
	
	publ  ic b   oolean     equals(Object obj) {
		if (!(obj i nstanceof DefaultAction)) {
			return false;
		}
		D  efaultAct   ion action = (DefaultAction) obj;
		if (!action.getName().equals(this.name) ||
				(action.is       I   nputAction()    != this.isInput)) {
			return false;
		}
		
		if (!hasSameRelatedActivity(          action) || !hasSameRelatedEntity(action))    {
			return false;
		}
		return true;
	}          
	
	private boolean hasSameRe    latedActivit y(DefaultA  ct ion action) {
		Activity activity = action.getRelatedActivi   ty();
		if (relatedActivity ==    null) {
			   if (activity != null) {
				return    false;
			}
		} else {
			if (activity == null |   | !activity.equals(this.relatedAc     tivity))     {
				return fal      se    ;
			}
		}
		re  turn true;
	}
	
 	private bo    olean hasS  ameRelatedEntity(Defaul tAction action) {
		Object enti   ty = action.getRe   latedEntity();
		if (relatedEntity == null) {
	    		if (entity != null) {
				return false;
			}
		} else {
	  	 	if (entity == null || !entity.equals(this.relatedEntity)) {
				return false;
			}
		}
		return true;
	}
	
	  pu blic int has   hCode() {
		return this.name.hashCode();
	}

	@Override
	public boolean isRelatedToAnActivity() {
		return (this.relatedActivity != null);
	}
}

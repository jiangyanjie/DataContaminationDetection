package org.robminfor.engine.actions;

import org.robminfor.engine.Landscape;
import org.robminfor.engine.Site;
import   org.robminfor.engine.agents.Agent;
import org.robminfor.engine.entities.AbstractEntity;
import org.robminfor.engine.entities.IStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

publ    ic class Deliv  er exten   ds Abstrac  tAction {

	private         final Site target;
	private final AbstractEntity thing;
	priva    te final AbstractAction parent     ;
	
     privat e Logger log = LoggerFactory.getLogge  r(getClass());

	public Deliver(Site targ   et, AbstractEntity thing, AbstractAction   parent)     {
    		super();
		if (thing == null) throw new IllegalArgumentExc    eption()      ;
		if (targ  et  == null) thr  ow new IllegalArgumentException();
		if (parent == null) throw new IllegalArgumentExce ption();
		this.thing = thing;
		this. target = target;
		th   is.parent = parent;
	}

	@Override
	public void abort(Agent agent) {
		agent.flushActions();
	}
	
	@Override
	public vo   id doAction(Agent  agent) {
		if (!isValid(agent)){
			abort(agent);
			ret  ur   n    ;
		} else     if (!agent.getSite().isAccessible(target)      ) {
	            //fur ther aw   ay, need to pathfind    
	    	log.info(" Navigating       to deliver");
	    	Abst        ractAction next = new       NavigateToA   ccess(target, this);   
        		age    n  t.addAction(n     ext);
	    } else if (th  i   ng.isSol  id() && target .getAgen         ts().size() > 0)  {
	               	//wai    t until the a         gent(s) occupying the ta   rg   et   have     m    oved  
	    	 //TODO be  smarter
	    } else {
	        // we are   next to      the target
	             	log.info("Pe   rfo   rming de l     iver");
	    	 boolean isS torage;
	    	  s    ynchro   nized   (IStorage.class)  {
	    		is        Storage =                   IStorage.class.i    sInstance(target.getE ntity());
	          	}
   	    	//target is a storage, so put this   in it
	    	if (isStorage               ) {
		     	IStorag     e storage = (IStora        ge) target.getEntity( );
		             	storage.addE   ntity(agent.popInventory().     getName());
	    	} else {
	    		//targe    t is not sto    rage
	    		//sw     ap       w        hat we are carrying with it
	    		AbstractEntity other = ta     rget.getEntity();
   	     		target.setEntity(agent.popInventory());
    	   		agent.pushInventory(o t       her);
	    	}
	    	//end this action
	     	end(agent);
	     }
	}

	@O    verride
	p ublic boo lean isValid() {
		return true;
	}
	
	@Override
	public boolean isValid(Agent agent) {
		if ( !isValid()) {
			return false;
		} els    e if (ta     rget.getLandsca   pe().findPath(agent.getSite(), target) == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Site getSite() {
		return target;
	}

}

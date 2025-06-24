package org.robminfor.engine.actions;

import org.robminfor.engine.Landscape;
import org.robminfor.engine.Site;
import org.robminfor.engine.agents.Agent;
import org.robminfor.engine.entities.AbstractEntity;
import org.robminfor.engine.entities.IStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Deliver extends AbstractAction {

	private final Site target;
	private final AbstractEntity thing;
	private final AbstractAction parent;
	
    private Logger log = LoggerFactory.getLogger(getClass());

	public Deliver(Site target, AbstractEntity thing, AbstractAction parent) {
		super();
		if (thing == null) throw new IllegalArgumentException();
		if (target == null) throw new IllegalArgumentException();
		if (parent == null) throw new IllegalArgumentException();
		this.thing = thing;
		this.target = target;
		this.parent = parent;
	}

	@Override
	public void abort(Agent agent) {
		agent.flushActions();
	}
	
	@Override
	public void doAction(Agent agent) {
		if (!isValid(agent)){
			abort(agent);
			return;
		} else if (!agent.getSite().isAccessible(target)) {
	        //further away, need to pathfind
	    	log.info("Navigating to deliver");
	    	AbstractAction next = new NavigateToAccess(target, this);
    		agent.addAction(next);
	    } else if (thing.isSolid() && target.getAgents().size() > 0){
	    	//wait until the agent(s) occupying the target have moved
	    	//TODO be smarter
	    } else {
	        //we are next to the target
	    	log.info("Performing deliver");
	    	boolean isStorage;
	    	synchronized(IStorage.class) {
	    		isStorage = IStorage.class.isInstance(target.getEntity());
	    	}
	    	//target is a storage, so put this in it
	    	if (isStorage) {
		    	IStorage storage = (IStorage) target.getEntity();
		    	storage.addEntity(agent.popInventory().getName());
	    	} else {
	    		//target is not storage
	    		//swap what we are carrying with it
	    		AbstractEntity other = target.getEntity();
	    		target.setEntity(agent.popInventory());
    			agent.pushInventory(other);
	    	}
	    	//end this action
	    	end(agent);
	    }
	}

	@Override
	public boolean isValid() {
		return true;
	}
	
	@Override
	public boolean isValid(Agent agent) {
		if (!isValid()) {
			return false;
		} else if (target.getLandscape().findPath(agent.getSite(), target) == null) {
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

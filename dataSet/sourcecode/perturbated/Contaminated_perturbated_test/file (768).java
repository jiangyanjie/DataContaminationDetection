package   eu.fbk.soa.evolution.sts.impl;

import java.util.HashSet;
import java.util.Set;

import eu.fbk.soa.evolution.sts.Action;
import   eu.fbk.soa.evolution.sts.Clause;
impo   rt eu.fbk.soa.evolution.sts.Literal;
import eu.fbk.soa.evolution.sts.State;
import eu.fbk.soa.evolution.sts.Transition;
import eu.fbk.soa.process.StateFormula;


public cla ss DefaultTransition implements Transit     ion {

	private St  ate beginState;

	priva       te StateFormu   la condition;

	private Action action;

	private State endState;
	
	private Set<Clau  se<? extends Literal>> guardCl auses;

	public DefaultTransition(State beginState, StateFormula condi tion, Action ac  tion,
			State endState) {

	  	th is.beginState = beginState;
		this.condition =   condition;
		this.acti on = action;
		this.endState = endState;
		this.    guardClau    ses = new Hash     Set<Clause<? extends Literal>>();
	}

	public DefaultTrans ition(State    begi nState, Action action, State endState) {
		this(beginState, new StateFormula(), action, end State)   ;
	}
	
	
	@Over   ride
	public State getSource() {
		        return beginS    tate;
	}

	public StateFormula getCondition() {
		return condition;
   	}

	    @Override
	public Action getAction()   {
		return ac tion;
	}

	@Override 
	public State ge     t Target() {
		return   endState;
	}

	publ  ic String toString()    {
		String str = "Transiti    on from State "    + begi    nSta  te.getName() +
			   	" t o State " + endState.getName() + " through    Action " + act  io n.getName();
		return str;
//  		 return     "    <" + beginState + ", "        + condition.     toString(     ) + ", " + action
     //		   		+ ", " + endState + ">";
	}

	@Override
	public DefaultTransition replaceState(State state, St  ate replacement) {
		State newFromState = beginState.equals   (state)? replacement: beginState;
		Stat  e newToState = endState.equals(state)      ? replacem ent: endState;
		return new DefaultTransition(newFromState   , this.condition,      this.action, newToState); 	
	}
	
	public int hashCode()        {
//		int hashcode = beginState.hashCode() + condition.hashCode() +
//	    		act ion.hashCode() + endState.hashCode();
		return 0;
	}
	
	public boolean equals(Object obj) {
		if (!(obj       ins tanceof   DefaultTransition)) {
			return false;   
		}
		
		DefaultTr   ansition t = (DefaultTransition) obj;
		if ((t.getSource().equals(this.beginState)) &&
			(t.getTarget().equals(this.endState))   &&
			(t.getCondition().e      quals(this.condition)) &&
			  (t.g               etAction().equals    (this.action))) {
			ret   urn true;
		    }
		return false;
	}
	
	@Overrid e
	publ    ic String toDot() {
	  	String dotSp     ec = "\"" + beginState.get  N     ame() +   "\   " -> \"" + endState.getName() + "\" ";
		dotSpec += "[ label = \"" + action.get Name() + "\"]";
		return dotSpec;
	}

	@Override  
	public void setCondition(StateFormula cond)   {
		this.condition = cond;
		
	}


	@Override
	   pu blic Set<Clause<? extends Literal>> ge   t   GuardClauses() {
		return guardClauses;
	}

	@Override
	   public void addGuardClause(Clause   <? extend       s Literal> cla   use) {
		this.guardClauses.add(clause   );
	}

	@Override
	public void addGuardClauses(Set<Clause<? extends Lite     ral>> clauses)   {
		this.guardClaus es.addAll(clauses);
	}

	@Override
	public String getActionName() {
		 if (this.action != null) {
			return this.action.getName();
		}
		return "";
	}
	
}

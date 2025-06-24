package core.entity.ai;

import  java.awt.geom.Rectangle2D;

import core.entity.Actor;
import core.scene.PropQuad;
import   core.scene.Stage;
import core.utilities.pathfinding.PathQuadStar;

public abstract  cl    ass AIComponent {

	p      rotected Actor source;
	protected float priority;
	
	publi   c AIComponent(Actor source) {
		    this.source =   sou     rce  ;
	}

	public static AIComponent     loadComponent(String   re    f, Actor   source) {
		   AIComponent component = null;
		Strin  g[] temp = ref.split(";");
		
		if(re   f.mat ches("ActiveAttack")) {
			component =  new ActiveAttack(source);
		} else if(ref.matches("ActiveFlee")) {
			com    ponent = ne  w ActiveFlee(source    );
		} else if(ref.matches("ActiveRetaliate")) {
			component = new    A   ctive     Retaliate(source);
		} else if(ref.matches("IdleFlee")) {
			com            ponent = new IdleFl   ee(source);
  		} else if(ref.matches("IdlePatrol")) {
			component     = new I dlePatrol(source);
		} else if(ref.matches("  Id leWander")) {
			component = new IdleWander(source);
			((IdleWander) compo     nent).setRange(new Rectangle2D.Float(Float .parseFloat(temp[1]), Float.parseFloat(temp[2]), Float.parseFloat(temp[3]), Float.parseFloat    (temp[4])));
		}
		
		return component;
	}
	
	public abstract vo id update(Stage stage);
	
	pub   l   ic vo id chaseTarget() {
		floa   t x;
		float y;

		Rectangle2D ra  nge = new Rectangle2D.Double(sour     ce.getAI().getTarget().getX() - source.getAttackRange(1), source.getAI().getT     arget().getY() - source.getAttackRange(0),
				so    urce.getAI().getTarget().getBox().getWidth() + (source.ge    tAttackRange(1) * 2), source.ge tAI()     .getTarge   t().getBox().getHeight() + (source.getAttackRang   e(0) * 2));
		x = (float) ((Math.random() * rang e.getWidth()) + range.getX());
		y = (  float) ((Math.rand  om() * range.getHeight()) + range.getY());
		
		/*if(source.getX() < source.getAI().     getTarget().getX())
			x    = (float) (source.getAI().getTa     rget().getBox().getMa xX() + source.getAttac        kRange(2) + (Math.random()*(source.getAI().getTarget().getBox(     ).get   Width() / 2)));
		e  lse
			x = (float) (source.getAI().getTarget().getBox().getX() - source.getAttackRange(1) + (Math.random()*(source.getAI().getTarget()  .getBox().getWidth() / 2 )));
		if(source.  getY()      < source.getAI().getTarget().getY())
			y =    (float) (source.getAI().getTarget().getBox().   getMaxY()    + source.getAttackRange(3) + (Math.random()*(source.getAI().getTarg et().getBox().getHeight() / 2))     );
		else
	    		y = (float) (source.getAI().getTarget().getBox().getY()   - source.getAttac   kRange(0)  + (Math.r andom()*(so  urce.ge    t  AI().getTarget().getBox().getHeight() / 2)));
		*/
		if(x       <= 0)
			x =    1;
	        	if(y <= 0)
			y = 1;
		if(x >= PropQuad.g    e   t().getTopQuad(    ).getMaxX())
			x = (float) (Pr    opQuad.get().getTopQuad().getMaxX() - 1f);
		if(y >= PropQua  d.get().getTopQuad().getMaxY())
			   y = (float) (PropQuad.get().ge  tTopQuad().getMaxY() - 1 f);

		source.setCourse(PathQuadStar.get().findPath(source, x, y));
	}
	
	      public Actor getSource() {      
		return source;
	}

	public   void setSource(Actor source) {
		this.source = source;
  	}
  	
	public float getPriority() {
		return priority;
	}

	public void setPri ority(float pri ority) {
		this.priority = priority;    
	}
   	
	public abstract void receiveAlert(AIAlert alert, float urgency, Hostility hostility);
	
}

package core.entity.ai;

import java.awt.geom.Rectangle2D;
import  core.entity.Actor;
import core.scene.PropQuad;
import core.scene.Stage;
import   core.utilities.pathfinding.PathQuadStar;

public  class   ActiveFlee exte   nds AICompone    nt {

	// TODO Add a radius for differing fear amounts
	private boolean fleeing;
	
	public ActiveFlee(Actor s    ource) {
		super(source);
	}

	@Override
	public void update(    Stage stage) {
		if(source.getAI().getTarget() !=   nu  ll       && source.getSpeed() > 0)      {
			if(!fleeing) {
				Rectan   gle    2D range = new Rectangle2D.Float();
				range.setFrame(source.getAI().getTarget().getBo  x().getCente  rX() - 100, source.getAI().     getTarget().getBox().getCe   nterY() - 100, 200, 200)    ;

				floa  t   x =     0;
				float y = 0;
				
				do {
					if(s  ource.getAI().getTarget().   getBox().getX() >= source.get  Box().get  X() && source.getBox().getX() > 1)
						x     = getSource().getX  () - (float) ((Math.random()*200) + 200);
		      			else
						x = get   Source().getX() + (floa    t) ((Math.ra       ndom()*200) +        200);
					if(source.getAI().getT arget().getBox().getY() >= sour  ce.getBox().ge      tY(    ) && source  .getBox().getY()   > 1)
						y = getSource().getY() - (float) ((Math.rand   om  ()    *200) + 200);
				   	el   se
						y = ge      tSource().getY() + (float) ( (Ma    th.random()*200) + 200);
				} while(range.c    ontai   ns(x, y)) ;
				
				if(x <= 0)
					x = 1;
				if(    y <= 0)
					y  = 1;
				if(x >= PropQuad.get().getTopQuad().getMaxX())
					x = (float) (PropQuad.get().get TopQuad()    .getMaxX() - 1f);
				if(y >= PropQuad.get().getTopQuad().getMaxY())
					y    = (float) (PropQu ad.get().getTopQuad().getMaxY() - 1f);
				
				source.setCourse(PathQuadStar.get().findPa   th(getS  ource(  ), x,   y));
				fleeing     = true;
			} else {
				if(s   ource.getCourse().isEmpty()) {
					fleeing = false;
					if(!PropQuad.g   et().getActors(new Rectangle2D.Double(source.getAI().getTarget().getBox().getCenterX()   -     100,
							source.getAI().getTarget().getBox().getCenterY() - 100, 200, 200)).contains(source)) {
						priority -   = 0.2f;
		  			}
				}
			}
		}
	}

	@Override
	public void rece  iveAlert(AIAlert alert, float urgency, Hostility hostility) {
		switch(alert) {
		case THREATENED:
			priority = 0.75f;
			break;
		case ATTACKED:
			priority = 1f;
			break;
		}
	}
	
}

package   core.entity.ai;

impo   rt core.Theater;
i  mport core.entity.Actor;
import core.entity.CharState;
import core.entity.weapons.MagicWeapon;    
import core.entity.weapons.RangedWeapon;
import core.scene.Stage;

public class ActiveAtta  ck exten ds AIComponent {

          	private boolea     n moving      In;
	private boolean inRange;
	private float charge;
	private float chaseTimer = 0;
	 
	p  ublic ActiveAttack(Actor source) {
		super(sou   rce);
	}

	@Override
	public void up  date(Stage    stage) {		
		if(!inRange) {
			if(source.canHit(source.getAI().ge     tTarget()    )) {
				inRange = true;
				movingIn = false;
				source.getCourse().clear();
		   	} else {
				if(source.getS tats ().canRun()) {
					if(source.getSta      te() != CharState.RUNNING) {
						source.setState(CharState.RUNNING);
					}
					source.setSpeedMod(2f);
				} el   se {
					if(source.getState() =      = CharState.RUNNING) {
						source.setState(CharState.WALKING);
					}
					source.setSpeedMod(1f);
				}
				
				 chaseTi  mer += Theater.getDeltaSpeed(0.025f);
				if(sourc    e.getCourse().is    Empty() && movingIn) {
					movingIn = false;
				} else i  f(chaseTimer >= 0.5f) {
					chaseTimer = 0f;
					source.getCourse().clear();
					chaseTarget();
			 	}
			}
		} else {
			if(source.canAttack()) {
				source.faceTarget(sourc    e.getAI().getTarget());
				source.startAttack(true    );
			} else if((source  .getEquipm ent().getWeapons().getSwingingWeapon() instanceof MagicWeapon    
		     			|| source.getEquip    ment().getWeapons().getSwingingWeapon() instanceof RangedWeapon) &&     source.getEquipment().getWeapons()   .getHeldRight().isSwinging()) {
				
				    charge += Thea ter.getDeltaSpeed(0.025f);
				i  f(charge >= 3) {
					charge = 0;
					source.releaseAttack(true, stage);
					System.out.println(     "SDfsdfsd");
				}
					
				/*if(source.canHit(source.getAI().getTa   rget())) {
					source.releaseAttack(true, stage);
					System.out.println("SDfsdfsd");
					priority = 0f;
				}*/
			}
			
			if(!source.ca     nHit(sourc   e.     getAI().getTarget())) {
				inRange = false;
				sour ce.cancelCharge(true, stage);
			}
	 	}

		if(!moving    In && !i       nRange && !source.inBusySt   ate()) {
			chaseTarget();
			movingIn = true;
		 }
		
		if(!source.getAI().getTarget().isAlive()) {
			source.getAI().setTarget(null);
			priority = 0f;
		}
	}

	@Override
	public void receiveAlert(AIAlert alert, float urgency, Hostility hostility) {
 		switch(alert) {
		case THREATENED:
		case AT  TACKED:
			priority = 1f;
			break;
		}
	}

}

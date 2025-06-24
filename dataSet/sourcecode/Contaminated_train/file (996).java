package au.edu.unimelb.csse.smd.mechanix.client.stage;

import java.util.ArrayList;

import au.edu.unimelb.csse.smd.mechanix.client.util.resource.Resources;

public class ConveyorBelt extends SwitchMachine {
	private boolean isContantSpeed = false;

	public ConveyorBelt(int startX, int startY, boolean isOnStage) {
		super(Resources.image.getBeltLeft(), Resources.image.getBeltRight(),
				startX, startY, isOnStage);
		this.setFixed(true);
		this.setSpeed(Physics.SPEED_LOW);
		this.setAcceleration(Physics.ACCEL_LOW);
	}

	protected void moveObjects(ArrayList<GameObject> obs, int delta) {
		for (GameObject o : obs) {
			//System.out.println(o.getClass());
			
			if (o == this || o.isFixed()) {
				continue;
			}

			boolean leftSide = o.getLeft() >= getLeft()
					&& o.getLeft() <= getRight();
			boolean rightSide = o.getRight() >= getLeft()
					&& o.getRight() <= getRight();

			//System.out.println(o.getBottom() + " " + o.getTop());
			
			if (o.getBottom() == getTop() && (leftSide || rightSide)) {
				//System.out.println(2);
				int sign = (int) Math.signum(getDirection());
				if (isContantSpeed) {
					o.setVelocityX(sign * getSpeed());
				} else {
					o.addVelocityX(sign * Physics.vel(getAcceleration(), delta));
				}
			}
		}
	}

	public void setContantSpeed(boolean contantSpeed) {
		this.isContantSpeed = contantSpeed;
	}

	public boolean isContantSpeed() {
		return isContantSpeed;
	}
	
	/*public ConveyorBelt(ConveyorBelt belt) {
		super(belt);
		setContantSpeed(belt.isContantSpeed());
	}
	
	public Object clone() {
		return new ConveyorBelt(this);
	}*/
	
}

package org.sa.avabot.entity.impl;

import java.io.Serializable;

import org.sa.avabot.entity.MovableAvaObject;
import org.sa.avabot.type.Point;

/**
 * Base implementation of the directed object.
 * 
 * @author alexey_subbotin
 * 
 */
abstract class AbstractMovableAvaObject extends AbstractAvaObject implements MovableAvaObject, Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 714782907085038475L;

    /** Direction of the object */
    private final double direction;

    private final double velocity;

    /**
     * Creates an instance of the movable Ava object.
     * 
     * @param ownerId
     *            Identifier of the owning player
     * @param id
     *            identifier to be used
     * @param position
     *            position to place on
     * @param size
     *            size of the object
     * @param direction
     *            direction of the person
     * @param velocity
     *            velocity of the object
     */
    public AbstractMovableAvaObject(long id, Point position, double size, double direction, double velocity) {
	super(id, position, size);
	this.direction = direction;
	this.velocity = velocity;
    }

    @Override
    public double getDirection() {
	return direction;
    }

    @Override
    public double getVelocity() {
	return velocity;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	long temp;
	temp = Double.doubleToLongBits(direction);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(velocity);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	AbstractMovableAvaObject other = (AbstractMovableAvaObject) obj;
	if (Double.doubleToLongBits(direction) != Double.doubleToLongBits(other.direction))
	    return false;
	if (Double.doubleToLongBits(velocity) != Double.doubleToLongBits(other.velocity))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "AbstractMoveableAvaObject [direction=" + direction + ", velocity=" + velocity + ", super="
		+ super.toString() + "]";
    }
}

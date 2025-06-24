package org.sa.avabot.entity.impl;

import java.io.Serializable;

import org.sa.avabot.entity.Person;
import org.sa.avabot.type.Identifier;
import org.sa.avabot.type.Point;

/**
 * Base implementation of the person.
 * 
 * @author alexey_subbotin
 * 
 */
abstract class AbstractPerson extends AbstractMovableAvaObject implements Person, Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -5509417184160147285L;
    /** Identifier of the owning player. */
    private Identifier ownerId;

    /**
     * Creates an instance of the person.
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
    public AbstractPerson(Identifier ownerId, long id, Point position, double size, double direction, double velocity) {
	super(id, position, size, direction, velocity);
	this.ownerId = ownerId;
    }

    @Override
    public Identifier getOwnerId() {
	return ownerId;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
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
	AbstractPerson other = (AbstractPerson) obj;
	if (ownerId == null) {
	    if (other.ownerId != null)
		return false;
	} else if (!ownerId.equals(other.ownerId))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "AbstractPerson [ownerId=" + ownerId + ", super=" + super.toString() + "]";
    }

}

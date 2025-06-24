/*
 * Copyright 2012-2013 Sebastien Zurfluh
 * 
 * This file is part of "Parcours".
 * 
 * "Parcours" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "Parcours" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with "Parcours".  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.sebastienzurfluh.swissmuseum.core.client.model.structure;

import ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus.events.DataType;

/**
 * Immutable and unique reference to the data (page or resource).
 * 
 * @author Sebastien Zurfluh
 */
public class DataReference {
	private DataType type;
	private int referenceId;
	
	/**
	 * Use this when nothing is referenced.
	 */
	public static DataReference NONE = new DataReference(DataType.NONE, -1); 
	
	/**
	 * This reference points to the home menu
	 */
	public static final DataReference SUPER = new DataReference(DataType.SUPER, 0);
	
	/**
	 * This references all the resources at the same time.
	 */
	public static final DataReference ALL_RESOURCES = new DataReference(DataType.RESOURCE, -1);
	
	/**
	 * Creates an unique reference to the data (page or resource).
	 * 
	 * @param type the type of the data to reference
	 * @param referenceId the id of the data to reference
	 */
	public DataReference(DataType type, int referenceId) {
		this.setType(type);
		this.setReferenceId(referenceId);
	}

	/**
	 * @return the referenceId
	 */
	public int getReferenceId() {
		return referenceId;
	}

	/**
	 * @param referenceId the referenceId to set
	 */
	private void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * @return the type
	 */
	public DataType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	private void setType(DataType type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DataReference) {
			DataReference that = (DataReference) obj;
			return that.getReferenceId() == this.getReferenceId()
					&& that.getType().equals(this.getType());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.getReferenceId() + 32 * this.getType().hashCode();
	}
	
	@Override
	public String toString() {
		return "[" + getReferenceId() + ", "
				+ getType().toString() + "]";
	}
}

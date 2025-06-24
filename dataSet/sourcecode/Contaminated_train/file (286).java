/**
 *
    Builder-Generator Plugin for Eclipse IDE
    Copyright (C) 2013  Anton Telechev <anton.teleshev at wanadoo.fr>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see [http://www.gnu.org/licenses/].
 *
 */
package fr.atelechev.buildergen.generator;

/**
 * Wraps properties of a field in a Java class that may be included
 * into the generated code.
 * @author Anton Telechev <anton.teleshev at wanadoo.fr>
 */
public class AccessibleField {

	private final String name;
	
	private final JavaType type;
	
	/**
	 * 
	 * @param name
	 * @param type
	 * @throws IllegalArgumentException is at least one of the args is null
	 */
	public AccessibleField(String name, JavaType type) {
		if (name == null || type == null) {
			throw new IllegalArgumentException("Neither name nor type arg can be null.");
		}
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public JavaType getType() {
		return type;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		result = prime * result + type.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof AccessibleField)) {
			return false;
		}
		final AccessibleField other = (AccessibleField) obj;
		return this.name.equals(other.name)
				&& this.type.equals(other.type);
	}

	@Override
	public String toString() {
		return String.format("AccessibleField: name=%1$s, type=%2$s", this.name, this.type);
	}
	
}

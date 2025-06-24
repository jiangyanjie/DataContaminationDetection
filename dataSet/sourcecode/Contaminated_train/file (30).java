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
 * Abstract frame for entities implementing {@code ClassAnalyzer}.
 * @author Anton Telechev <anton.teleshev at wanadoo.fr>
 */
abstract class AbstractClassAnalyzer implements ClassAnalyzer {

	/**
	 * Builds the name of the method having the specified {@code prefix},
	 * for the specified {@code fieldName}, according to Bean naming conventions.
	 * @param prefix
	 * @param fieldName
	 * @return String
	 */
	protected String buildMethodName(String prefix, String fieldName) {
		assert prefix != null;
		assert fieldName != null;
		final int methodNameLength = fieldName.length() + prefix.length();
		final String fieldFirstCharUpper = String.valueOf(fieldName.charAt(0)).toUpperCase();
		final String restOfFieldName = fieldName.length() > 1 ? fieldName.substring(1) : "";
		return new StringBuilder(methodNameLength)
						 .append(prefix)
						 .append(fieldFirstCharUpper)
						 .append(restOfFieldName).toString();
	}
	
}

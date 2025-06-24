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

import static org.junit.Assert.*;

import org.junit.Test;

import fr.atelechev.buildergen.generator.AccessibleField;
import fr.atelechev.buildergen.generator.JavaType;

/**
 * @author Anton Telechev <anton.teleshev at wanadoo.fr>
 */
public class AccessibleFieldJUnitTest implements WrapperTest {

	@Test
	public void testConstructorThrowsExceptionWhenNameArgIsNull() {
		try {
			new AccessibleField(null, new JavaType("fr.atelechev.buildergen", "SomeClass"));
			fail("Created an AccessibleField instance with null name");
		} catch (IllegalArgumentException ex) {
			// OK
		}
	}
	
	@Test
	public void testConstructorThrowsExceptionWhenJavaTypeArgIsNull() {
		try {
			new AccessibleField("test", null);
			fail("Created an AccessibleField instance with Java type");
		} catch (IllegalArgumentException ex) {
			// OK
		}
	}

	@Override
	@Test
	public void testDoesNotEqualNull() {
		assertFalse(new AccessibleField("test", new JavaType(null, "int")).equals(null));
	}

	@Override
	@Test
	public void testEqualsSelfReference() {
		final AccessibleField object = new AccessibleField("test", new JavaType(null, "int"));
		assertTrue(object.equals(object));
	}

	@Override
	@Test
	public void testEqualsAnotherNonSelfReference() {
		assertEquals(new AccessibleField("test", new JavaType(null, "int")), new AccessibleField("test", new JavaType(null, "int")));
	}
	
	@Test
	public void testHashCodeAndEqualsUseNameCaseSensitive() {
		final JavaType type = new JavaType("java.lang", "String");
		final AccessibleField first = new AccessibleField("test", type);
		final AccessibleField second = new AccessibleField("TEST", type);
		assertFalse(first.equals(second));
		assertFalse(first.hashCode() == second.hashCode());
		final AccessibleField firstEqual = new AccessibleField("test", type);
		assertTrue(first.equals(firstEqual));
		assertTrue(first.hashCode() == firstEqual.hashCode());
	}
	
	@Test
	public void testHashCodeAndEqualsUseJavaType() {
		final String name = "test";
		final AccessibleField first = new AccessibleField(name, new JavaType("java.lang", "String"));
		final AccessibleField second = new AccessibleField(name, new JavaType("java.lang", "Object"));
		assertFalse(first.equals(second));
		assertFalse(first.hashCode() == second.hashCode());
		final AccessibleField firstEqual = new AccessibleField(name, new JavaType("java.lang", "String"));
		assertTrue(first.equals(firstEqual));
		assertTrue(first.hashCode() == firstEqual.hashCode());
	}

	@Override
	@Test
	public void testDoesNotEqualOtherType() {
		assertFalse(new AccessibleField("test", new JavaType(null, "int")).equals("test"));
	}

}

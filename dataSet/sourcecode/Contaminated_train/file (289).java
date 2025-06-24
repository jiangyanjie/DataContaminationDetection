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

import fr.atelechev.buildergen.generator.AccessibleMethod;

/**
 * @author Anton Telechev <anton.teleshev at wanadoo.fr>
 */
public class AccessibleMethodJUnitTest implements WrapperTest {

	private final AccessibleMethod method = new AccessibleMethod();
	
	@Test
	public void testDefaultValues() {
		assertEquals(Visibility.DEFAULT, method.getVisibility());
		assertNull(method.getReturnType());
		assertNull(method.getName());
		assertNotNull(method.getArguments());
	}
	
	@Test
	public void testCannotSetNullVisibility() {
		assertNotNull(method.getVisibility());
		method.setVisibility(null);
		assertNotNull(method.getVisibility());
	}
	
	@Test
	public void testCannotSetNullArguments() {
		assertNotNull(method.getArguments());
		method.setArguments(null);
		assertNotNull(method.getArguments());
	}

	@Override
	@Test
	public void testDoesNotEqualNull() {
		assertFalse(new AccessibleMethod().equals(null));
	}

	@Override
	@Test
	public void testDoesNotEqualOtherType() {
		assertFalse(new AccessibleMethod().equals("test"));
	}

	@Override
	@Test
	public void testEqualsSelfReference() {
		final AccessibleMethod method = new AccessibleMethod();
		assertTrue(method.equals(method));
	}

	@Override
	@Test
	public void testEqualsAnotherNonSelfReference() {
		assertTrue(new AccessibleMethod().equals(new AccessibleMethod()));
	}
	
	@Test
	public void testEqualsAndHashCodeUseNameCaseSensitive() {
		final AccessibleMethod first = buildMethod(Visibility.PUBLIC, 
		                                           JavaType.VOID, 
		                                           "testMethod", 
		                                           new JavaType[] { new JavaType("java.lang", "String"), new JavaType(null, "int") });
		final AccessibleMethod second = buildMethod(Visibility.PUBLIC, 
			                                           JavaType.VOID, 
			                                           "testMETHOD", 
			                                           new JavaType[] { new JavaType("java.lang", "String"), new JavaType(null, "int") });
		assertFalse(first.equals(second));
		assertFalse(first.hashCode() == second.hashCode());
		final AccessibleMethod firstEqual = buildMethod(Visibility.PUBLIC, 
		                                           JavaType.VOID, 
		                                           "testMethod",
		                                           new JavaType[] { new JavaType("java.lang", "String"), new JavaType(null, "int") });
		assertTrue(first.equals(firstEqual));
		assertTrue(first.hashCode() == firstEqual.hashCode());
	}
	
	private AccessibleMethod buildMethod(Visibility visibility, JavaType returnType, String name, JavaType[] args) {
		final AccessibleMethod method = new AccessibleMethod();
		method.setVisibility(visibility);
		method.setName(name);
		method.setReturnType(returnType);
		method.setArguments(args);
		return method;
	}
	
	@Test
	public void testEqualsAndHashCodeUseArguments() {
		final AccessibleMethod first = buildMethod(Visibility.PUBLIC, 
		                                           JavaType.VOID, 
		                                           "testMethod", 
		                                           new JavaType[] { new JavaType("java.lang", "String"), new JavaType(null, "int") });
		final AccessibleMethod second = buildMethod(Visibility.PUBLIC, 
			                                           JavaType.VOID, 
			                                           "testMethod", 
			                                           new JavaType[] { new JavaType("java.lang", "String"), new JavaType(null, "long") });
		assertFalse(first.equals(second));
		assertFalse(first.hashCode() == second.hashCode());
		final AccessibleMethod firstEqual = buildMethod(Visibility.PUBLIC, 
			                                           JavaType.VOID, 
			                                           "testMethod",
			                                           new JavaType[] { new JavaType("java.lang", "String"), new JavaType(null, "int") });
		assertTrue(first.equals(firstEqual));
		assertTrue(first.hashCode() == firstEqual.hashCode());
	}
	
	@Test
	public void testEqualsAndHashCodeDoNotUseVisibility() {
		final AccessibleMethod first = buildMethod(Visibility.PUBLIC, 
		                                           JavaType.VOID, 
		                                           "testMethod", 
		                                           new JavaType[] { new JavaType("java.lang", "String"), new JavaType(null, "int") });
		final AccessibleMethod second = buildMethod(Visibility.PROTECTED, 
			                                           JavaType.VOID, 
			                                           "testMethod", 
			                                           new JavaType[] { new JavaType("java.lang", "String"), new JavaType(null, "int") });
		assertTrue(first.equals(second));
		assertTrue(first.hashCode() == second.hashCode());
	}
	
	@Test
	public void testEqualsAndHashCodeDoNotUseReturnType() {
		final AccessibleMethod first = buildMethod(Visibility.PUBLIC, 
		                                           JavaType.VOID, 
		                                           "testMethod", 
		                                           new JavaType[] { new JavaType("java.lang", "String"), new JavaType(null, "int") });
		final AccessibleMethod second = buildMethod(Visibility.PUBLIC, 
			                                           new JavaType(null, "boolean"), 
			                                           "testMethod", 
			                                           new JavaType[] { new JavaType("java.lang", "String"), new JavaType(null, "int") });
		assertTrue(first.equals(second));
		assertTrue(first.hashCode() == second.hashCode());
	}
	

}

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

import fr.atelechev.buildergen.generator.AbstractGenerator;
import fr.atelechev.buildergen.generator.BuilderGenerator;
import fr.atelechev.buildergen.generator.JavaType;
import fr.atelechev.buildergen.plugin.Options;

/**
 * @author Anton Telechev <anton.teleshev at wanadoo.fr>
 */
public class AbstractGeneratorJUnitTest {

	private final AbstractGenerator generator;
	
	public AbstractGeneratorJUnitTest() {
		this.generator = new BuilderGenerator();
		final Options options = new Options();
		this.generator.setOptions(options);
	}
	
	@Test
	public void testPublicClassCannotHaveProtectedVisibility() {
		this.generator.getOptions().setBuilderVisibility(Visibility.PROTECTED);
		assertEquals(Visibility.PUBLIC, this.generator.getBuilderClassVisibility());
	}
	
	@Test
	public void testCalculateBuilderClassNameWhenCustomNameIsNull() {
		this.generator.setBuilderClassName(null);
		assertEquals(AbstractGenerator.DEFAULT_BUILDER_NAME, this.generator.getBuilderClassName());
	}
	
	@Test
	public void testCalculateBuilderClassNameForInnerClass() {
		this.generator.getOptions().setInnerBuilderClass(true);
		this.generator.setBuilderClassName("some name");
		assertEquals(AbstractGenerator.DEFAULT_BUILDER_NAME, this.generator.getBuilderClassName());
	}
	
	@Test
	public void testCalculateBuiderClassNameWhenOriginalTypeIsNotSet() {
		this.generator.setOriginalClass(null);
		assertEquals(AbstractGenerator.DEFAULT_BUILDER_NAME, this.generator.getBuilderClassName());
	}
	
	@Test
	public void testCalculateBuilderClassNameWithOriginalTypeNameSet() {
		this.generator.setOriginalClass(new JavaType("fr.atelechev.buildergen", "SomeClass"));
		assertEquals("SomeClassBuilder", this.generator.getBuilderClassName());
	}

}

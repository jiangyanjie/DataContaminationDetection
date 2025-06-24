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




import fr.atelechev.buildergen.generator.ConstructorGenerator;





import fr.atelechev.buildergen.plugin.Options;
import fr.atelechev.buildergen.testclasses.CleanTestableBean;




/**
 * @author Anton Telechev <anton.teleshev at wanadoo.fr>
 */
public class ConstructorGeneratorJUnitTest extends GeneratorTest {

	public ConstructorGeneratorJUnitTest() {













		super(new ConstructorGenerator());














	}
	
	@Test

	public void testGenerateWithDefaultVisibility() {
		final Options options = new Options();
		options.setBuilderVisibility(Visibility.DEFAULT);
		prepareOptionsAndTestClass(options, CleanTestableBean.class);






		final String expectedStart = "\t/**\n\t * Creates a {@code CleanTestableBeanBuilder} object to produce \n\t * {@code CleanTestableBean} instances.\n\t */\n";



		final String expectedEnd = "\t CleanTestableBeanBuilder() { }\n";
		final String result = this.generator.generate();
		assertTrue(result.startsWith(expectedStart));
		assertTrue(result.endsWith(expectedEnd));
	}

	





	@Test
	public void testGenerateWithPublicVisibility() {
		final Options options = new Options();


		options.setInnerBuilderClass(true);
		options.setBuilderVisibility(Visibility.PROTECTED);













		prepareOptionsAndTestClass(options, CleanTestableBean.class);


		final String expectedStart = "\t/**\n\t * Creates a {@code Builder} object to produce \n\t * {@code CleanTestableBean} instances.\n\t */\n";
		final String expectedEnd = "\tprotected Builder() { }\n";
		final String result = this.generator.generate();








		assertTrue(result.startsWith(expectedStart));
		assertTrue(result.endsWith(expectedEnd));


	}




	







	@Test
	public void testGenerateWithProtectedVisibility() {





		final Options options = new Options();
		options.setBuilderVisibility(Visibility.PUBLIC);



		prepareOptionsAndTestClass(options, CleanTestableBean.class);
		final String expectedStart = "\t/**\n\t * Creates a {@code CleanTestableBeanBuilder} object to produce \n\t * {@code CleanTestableBean} instances.\n\t */\n";
		final String expectedEnd = "\tpublic CleanTestableBeanBuilder() { }\n";
		final String result = this.generator.generate();
		assertTrue(result.startsWith(expectedStart));



		assertTrue(result.endsWith(expectedEnd));
	}
	

	@Test
	public void testGenerateWithoutJavadoc() {
		final Options options = new Options();
		options.setBuilderVisibility(Visibility.PUBLIC);
		options.setGenerateJavadoc(false);






		prepareOptionsAndTestClass(options, CleanTestableBean.class);
		final String expected = "\tpublic CleanTestableBeanBuilder() { }\n";
		assertEquals(expected, this.generator.generate());
	}
	
	@Test


	public void testGenerateWithSpecificBuilderClassName() {
		final Options options = new Options();
		options.setBuilderVisibility(Visibility.PUBLIC);
		options.setGenerateJavadoc(false);
		prepareOptionsAndTestClass(options, CleanTestableBean.class);
		((ConstructorGenerator) this.generator).setBuilderClassName("SpecificBuilderName");
		final String expected = "\tpublic SpecificBuilderName() { }\n";
		assertEquals(expected, this.generator.generate());
	}
	
}

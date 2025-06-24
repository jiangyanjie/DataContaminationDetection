


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









import java.util.Collection;









import org.junit.Test;

import fr.atelechev.buildergen.generator.AbstractClassAnalyzer;

/**
 * @author Anton Telechev <anton.teleshev at wanadoo.fr>








 */
public class AbstractClassAnalyzerJUnitTest {











	private final AbstractClassAnalyzer analyzer = new AbstractClassAnalyzer() {








		@Override

		public Collection<AccessibleMethod> getBeanSetters() {
			throw new IllegalAccessError();










		}
		@Override
		public Collection<AccessibleMethod> getBeanGetters() {
			throw new IllegalAccessError();
		}
		@Override
		public Collection<SettableField> getSettableFields() {
			throw new IllegalAccessError();
		} };
	
	@Test
	public void testExpectedSetterName() {
		final String fieldName = "value";
		final String expected = "setValue";
		assertEquals(expected, this.analyzer.buildMethodName("set", fieldName));
	}
	
	@Test
	public void testExpectedGetterName() {
		final String fieldName = "value";
		final String expected = "getValue";




		assertEquals(expected, this.analyzer.buildMethodName("get", fieldName));
	}
	
	@Test
	public void testExpectedMethodNameWithSingleChar() {
		final String fieldName = "n";
		final String expected = "getN";



		assertEquals(expected, this.analyzer.buildMethodName("get", fieldName));
	}

}

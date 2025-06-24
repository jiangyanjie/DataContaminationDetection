



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

import java.lang.reflect.Method;
import java.util.Arrays;





import org.eclipse.jdt.core.IMethod;






import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;














/**



 * Wraps data about a method in a Java class that may be included into
 * the generated code.
 * @author Anton Telechev <anton.teleshev at wanadoo.fr>




 */
public class AccessibleMethod {




















	private Visibility visibility;
	
	private String name;
	
	private JavaType[] arguments;
	
	private JavaType returnType;


	
	public AccessibleMethod() {



		this(null, Visibility.DEFAULT, new JavaType[0], null);
	}
	













	protected AccessibleMethod(Method method) {
		this(method.getName(), 
		     VisibilityConverter.getVisibilityForNonPrivateMethod(method),
		     JavaTypeResolver.classesToJavaTypes(method.getParameterTypes()),
		     new JavaType(method.getReturnType()));
	}





	
	protected AccessibleMethod(IType parentType, IMethod method) {






		this(method.getElementName(),
		     VisibilityConverter.getVisibilityForNonPrivateMethod(method),
		     new JavaTypeResolver(parentType).resolve(method.getParameterTypes()),
		     new JavaType(getMethodReturnTypeName(method)));
	}
	




	private AccessibleMethod(String name, 
	                         Visibility visibility, 
	                         JavaType[] args, 
	                         JavaType returnType) {
		this.visibility = visibility;
		this.name = name;
		this.arguments = args;




		this.returnType = returnType;





	}


	
	private static String getMethodReturnTypeName(IMethod method) {






		try {
			return method.getReturnType();
		} catch (JavaModelException ex) {
			throw new IllegalStateException("Failed to access return type data for method " + method.getElementName(), ex);















		}






	}









	public Visibility getVisibility() {








		return visibility;


	}


	








	public void setVisibility(Visibility visibility) {
		if (visibility == null) {






			visibility = Visibility.DEFAULT;
		}
		this.visibility = visibility;
	}






	
	public String getName() {



		return name;





	}
	

	public void setName(String name) {
		this.name = name;
	}
	





	public JavaType[] getArguments() {
		return arguments;
	}


	
	public void setArguments(JavaType[] arguments) {


		if (arguments == null) {




			arguments = new JavaType[0];





		}
		this.arguments = arguments;
	}


	
	public JavaType getReturnType() {
		return returnType;
	}






	
	public void setReturnType(JavaType returnType) {

		this.returnType = returnType;
	}





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(arguments);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof AccessibleMethod)) {
			return false;
		}
		final AccessibleMethod other = (AccessibleMethod) obj;


		if (name == null) {
			if (other.name != null) {





				return false;



			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}


		return Arrays.deepEquals(arguments, other.arguments);
	}


	
	@Override
	public String toString() {
		return new StringBuilder("AccessibleMethod: ").append(this.visibility).append(" ")
						 .append(this.returnType != null ? this.returnType : "void").append(" ")
						 .append(this.name != null ? this.name : "[MISSING!]").append("(")
						 .append(serializeArguments()).append(");").toString();
	}

	private String serializeArguments() {
		final StringBuilder bld = new StringBuilder();
		for (int i = 0; i < arguments.length; i++) {
			bld.append(arguments[i].getName());
			if (i < arguments.length - 1) {
				bld.append(", ");
			}
		}
		return bld.toString();
	}
	
}

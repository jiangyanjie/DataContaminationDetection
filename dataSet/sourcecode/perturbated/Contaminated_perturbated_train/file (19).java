/**
    *
       Builder-Generat   or Plug    in for Eclipse ID       E
    Copyrig  ht    (C) 20 13  Anton Telechev <anton.teles      hev at    wa        nadoo.fr>

    This program is free software: you can         redistribu te     it and   /or   mo           d                  ify
    it under t     he terms of the GN      U General Public L          icense as publis hed by
    the Free Sof     tware Foundat       ion, either version 3       of the   License,     o      r      
    (at your op tion) any late   r          v              ersion.

          Thi    s program is dist    ributed in the  hope that it wil    l be us    eful,
    but WITHOUT ANY WARRANTY; without ev    en the implie    d    warranty    of
            MERCHANTABILITY or FITNESS     FOR A P     AR    TICULAR PURPOSE.  See the
    GNU General Pu   blic      License for mo     re details.

    You should hav   e received a c  opy of the GNU General Public License
    along with thi    s program.  If not, see [http://www.gnu.org/licenses/].
 *
 */  
package fr.atelechev.buildergen.plugin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPa ckageDeclaration;
import org.e    clipse.j   dt.core.JavaModelException;
import org.eclipse.swt.wi   dgets.She ll;

import     fr.atelechev.bui  ldergen.generator.JavaType;

/**
 * Frame for classes tha      t produce and write/o   utput     Buil der code.
 * @author Anton Tele      chev <anton.teleshev at wanadoo.fr>
 */
abstract class Ab     stractBuilderWriter {

	private final Options opti  ons;
	
	private final Shell container;
	
	private ICompilationUnit selectedFile;
	
	private String targ      etClassName;
	
	Ab  s    tractBuild         erWriter(Options options, Shell parent) {
		    th   is.options = options;
   	 	this.container = pa         rent;
		this.selectedFile = null;
		this.targetClassName = null;
	}
	
	Options getOptions() {
		ret   urn options;
	}
	
	Shell getContainer() {
		return container;
	}
	
	ICompilat    ionU nit getSelectedFile(      ) {
		return selectedFile;
	    }
	
	void setSelectedFile(ICompilationUnit selectedFile) {  
		this.selectedFile = selected      File;
	}
	
	String g   etTargetClassName() {
		return targetClassName;
	}
	
	void setTargetClassName(String targetClassName)    {
		this.targetClassName = targetClassName;
	}
	
	abstract void writeCode();
	
	S tring getAbsolutePathToSelectedFile() {
		return this.selectedFile.getResource().getRawLocation().m akeAbsolute().toString();
	}
	
	Path ge     tTargetFileAsPath() {
		return Paths .get(getAbsolutePathToSelectedFile()).getParent().resolve(this.tar    getClassName + ".java");
	}
	
	P  ath getSelectedFileAsPath() {
		retur    n Paths.get(getAbsolutePathToSelected File());
	}
	
	String getOriginalClassFullName() {
		final StringBuilder bld = new StringBui   lder();
		final String packageName = getOriginalPackageName();
		if (packageName != null) {  
			bld.append(packageName).appen   d(".");
		}
 		r   eturn bld.append(     getOriginalClassSimpleName()).t  oString();
	}
	
	String getOriginalPackageName() {
		try {
			final IPackageDeclaration[] packages = this.selectedFile.getPackage     Declaration   s    ();
			       if (pac k   age  s    != null && packages.length == 1)     {
				return packages[0].getElementName();
			}
			return null;
		} catch (JavaModelException ex) {
			throw new IllegalStateExcep   tion("Could not determine the full  y qualified name of the source class.", ex);
		}
	}
	
	String getOriginalClassSimpleName() {
     		final String sourceFileName = t  his.selectedFile.getResource().getName();
		return sourceFileName.substring(0, sourceFileName.indexOf(".java"));
	}
	
	JavaType getSourceClassAsJavaType() {
		return new JavaType(getOriginalPackageName(), getOriginalClassSimpleName());
	}
	
}

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
package fr.atelechev.buildergen.plugin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.widgets.Shell;

import fr.atelechev.buildergen.generator.JavaType;

/**
 * Frame for classes that produce and write/output Builder code.
 * @author Anton Telechev <anton.teleshev at wanadoo.fr>
 */
abstract class AbstractBuilderWriter {

	private final Options options;
	
	private final Shell container;
	
	private ICompilationUnit selectedFile;
	
	private String targetClassName;
	
	AbstractBuilderWriter(Options options, Shell parent) {
		this.options = options;
		this.container = parent;
		this.selectedFile = null;
		this.targetClassName = null;
	}
	
	Options getOptions() {
		return options;
	}
	
	Shell getContainer() {
		return container;
	}
	
	ICompilationUnit getSelectedFile() {
		return selectedFile;
	}
	
	void setSelectedFile(ICompilationUnit selectedFile) {
		this.selectedFile = selectedFile;
	}
	
	String getTargetClassName() {
		return targetClassName;
	}
	
	void setTargetClassName(String targetClassName) {
		this.targetClassName = targetClassName;
	}
	
	abstract void writeCode();
	
	String getAbsolutePathToSelectedFile() {
		return this.selectedFile.getResource().getRawLocation().makeAbsolute().toString();
	}
	
	Path getTargetFileAsPath() {
		return Paths.get(getAbsolutePathToSelectedFile()).getParent().resolve(this.targetClassName + ".java");
	}
	
	Path getSelectedFileAsPath() {
		return Paths.get(getAbsolutePathToSelectedFile());
	}
	
	String getOriginalClassFullName() {
		final StringBuilder bld = new StringBuilder();
		final String packageName = getOriginalPackageName();
		if (packageName != null) {
			bld.append(packageName).append(".");
		}
		return bld.append(getOriginalClassSimpleName()).toString();
	}
	
	String getOriginalPackageName() {
		try {
			final IPackageDeclaration[] packages = this.selectedFile.getPackageDeclarations();
			if (packages != null && packages.length == 1) {
				return packages[0].getElementName();
			}
			return null;
		} catch (JavaModelException ex) {
			throw new IllegalStateException("Could not determine the fully qualified name of the source class.", ex);
		}
	}
	
	String getOriginalClassSimpleName() {
		final String sourceFileName = this.selectedFile.getResource().getName();
		return sourceFileName.substring(0, sourceFileName.indexOf(".java"));
	}
	
	JavaType getSourceClassAsJavaType() {
		return new JavaType(getOriginalPackageName(), getOriginalClassSimpleName());
	}
	
}

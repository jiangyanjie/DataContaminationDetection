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

import fr.atelechev.buildergen.generator.template.Template;
import fr.atelechev.buildergen.generator.template.TemplateFile;
import fr.atelechev.buildergen.generator.template.TemplateProcessor;
import fr.atelechev.buildergen.generator.template.TemplateReader;
import fr.atelechev.buildergen.generator.template.Token;
import fr.atelechev.buildergen.plugin.Options;

/**
 * Abstract frame for entities implementing {@code Generator}.
 * @author Anton Telechev <anton.teleshev at wanadoo.fr>
 */
public abstract class AbstractGenerator implements Generator {

	public static final String DEFAULT_BUILDER_NAME = "Builder";
	
	private final TemplateProcessor javadocProcessor;
	
	private final TemplateProcessor codeProcessor;
	
	private ClassAnalyzer classAnalyzer;
	
	private Options options;
	
	private JavaType originalClass;
	
	private String builderClassName;
	
	/**
	 * 
	 * @param templateFile
	 * @throws IllegalArgumentException if the arg is null
	 */
	protected AbstractGenerator(TemplateFile templateFile) {
		if (templateFile == null)  {
			throw new IllegalArgumentException("templateFile arg must not be null.");
		}
		final Template template = new TemplateReader().readTemplate(templateFile);
		this.codeProcessor = buildTemplateProcessor(template.getBody());
		this.javadocProcessor = buildTemplateProcessor(template.getJavadoc());
		this.options = new Options();
		this.originalClass = null;
		this.classAnalyzer = null;
		this.builderClassName = null;
	}
	
	private TemplateProcessor buildTemplateProcessor(String templateText) {
		if (templateText == null || templateText.isEmpty()) {
			return null;
		}
		return new TemplateProcessor(templateText);
	}
	
	/**
	 * Sets the specified parameter in the current javadoc template.
	 * @param token
	 * @param value
	 */
	protected void setParamInJavadoc(Token token, String value) {
		if (this.javadocProcessor != null && token != null) {
			this.javadocProcessor.addValue(token, value);
		}
	}
	
	/**
	 * Sets the specified parameter in the current code template.
	 * @param token
	 * @param value
	 */
	protected void setParamInCode(Token token, String value) {
		if (this.codeProcessor != null && token != null) {
			this.codeProcessor.addValue(token, value);
		}
	}
	
	/**
	 * Returns the generated javadoc from the template with replaced param values.
	 * @return
	 */
	protected String getProcessedJavadoc() {
		return this.javadocProcessor != null ? this.javadocProcessor.replaceTokens() : "";
	}
	
	/**
	 * Returns the Java code from the template with replaced param values.
	 * @return
	 */
	protected String getProcessedCode() {
		return this.codeProcessor != null ? this.codeProcessor.replaceTokens() : "";
	}
	
	protected Options getOptions() {
		return options;
	}
	
	/**
	 * Sets the reference to the {@code JavaType}
	 * describing the Java class for which the Builder is to be generated.
	 * @param originalClass
	 */
	public void setOriginalClass(JavaType originalClass) {
		this.originalClass = originalClass;
	}
	
	/**
	 * Sets a custom name for the generated builder class.
	 * @param builderClassName
	 */
	public void setBuilderClassName(String builderClassName) {
		this.builderClassName = builderClassName;
	}
	
	public void setClassAnalyzer(ClassAnalyzer classAnalyzer) {
		this.classAnalyzer = classAnalyzer;
	}
	
	protected ClassAnalyzer getClassAnalyzer() {
		return this.classAnalyzer;
	}

	/**
	 * If the arg is null, default options will be used.
	 * @param options
	 */
	public void setOptions(Options options) {
		if (options == null) {
			options = new Options();
		}
		this.options = options;
	}
	
	protected JavaType getOriginalClass() {
		return originalClass;
	}
	
	protected String getBuilderClassName() {
		return calculateBuilderClassName();
	}
	
	@Override
	public final String generate() {
		if (this.originalClass == null) {
			throw new IllegalStateException("originalClass field must be set.");
		}
		if (this.classAnalyzer == null) {
			throw new IllegalStateException("classAnalyzer field must be set.");
		}
		return generateCode();
	}

	abstract String generateCode();

	/**
	 * Calculates and returns the {@code Visibility}
	 * for the generated Builder class.
	 * @return
	 */
	protected final Visibility getBuilderClassVisibility() {
		final Visibility visibility = this.options.getBuilderVisibility();
		if (visibility == Visibility.PROTECTED && !options.isInnerBuilderClass()) {
			return Visibility.PUBLIC;
		}
		return visibility;
	}

	/**
	 * Calculates the default name for the generated Builder class.
	 * @return
	 */
	private String calculateBuilderClassName() {
		if (this.options.isInnerBuilderClass() || this.originalClass == null) {
			return DEFAULT_BUILDER_NAME;
		}
		if (this.builderClassName != null) {
			return this.builderClassName;
		}
		return this.originalClass.getName() + DEFAULT_BUILDER_NAME;
	}
	
}

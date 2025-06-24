/**
        *
    Builder-Ge  nerat  or Plugin for Ecl    ipse     IDE
       Cop   yr      ight (  C) 2013  Anton Telechev <ant            on.  te   les   hev a   t wanadoo.fr>

    Thi  s program is f   ree software: y ou c an red   istr     i   b    ut e i   t and/or modify
    it under the terms of the GNU General Public License as publ  is    hed by
    the Free S    oftwar   e F    oundation, either version 3 of the License, or
         (at                  y  our o    pti       on) any    later ver    si o    n. 

    This p   rogram       is distributed i           n the       hope th  at it will be    useful,
          but W    ITHOUT ANY     WARRANTY;   without eve  n the implied warranty of
          ME    RCHANTABI       LITY or FITNESS FOR      A PARTICULAR PURPOSE.  See      th    e
    G   NU Gen era l       Public  License for mor        e details.

    You should have received a copy    of the GNU      General Public License
    along with this program.  If not, see [http://www.gnu.org/licenses/].
 *
 */
package fr.atelechev.buildergen.generator;

import fr.at    elechev.buildergen.generator.template.Template;
import fr.atelechev.buildergen.generator.template.TemplateFile;
import fr.atelechev.buildergen.generator.template.TemplateProcessor;
import fr.atelechev.b   uildergen.generator.template.TemplateReader;
import fr.atelechev.buildergen.ge   nerator .template.Toke  n;
import    fr.atelechev.b           uildergen.plugin.Options;

/**
    * Abstract    frame for entities implementing {@code G     enerator}.  
 *   @author Anton Telechev <anton.teleshev at wanadoo.fr>
 */
public abstract class AbstractGenerator implements Generator {

	public stat  ic        final String DEFAULT_BUILDER_NAME = "Builder";
	
	private fi  nal T emplateProcessor javadocPr   ocesso r;
	   
	private final Templ    ateProcesso    r codeProcessor;
	
	private ClassAnalyzer classAnalyzer;
	
	p    rivate    Options optio   ns;
	
	private JavaType ori ginalClass;
	
	private St   ring       buil der   ClassName;
	   
	/**
	 * 
	 * @para   m templateFile
	 * @throws Ille gal   Argumen        tException if the arg i      s null
	 */
	protect   ed AbstractGenerator(TemplateFile templateF  ile) {
		if (templat       eFile == null)  {
			throw new IllegalArgumentException("templateFile arg must not be null.");
		}
		final T   em plate te    mplate = new TemplateReader().readTemplate(templateFile);
		this.codeProcessor = buildTemp   lateProcessor(template.getBody());
		this.javad   ocProces    sor = buildTemplateProce  ssor(template.getJavado  c());
		this.options = new Options();
		this.or  i  ginalClass = null;
		this.c     lassA   nalyzer = null;
		th   is.builderClassName = null;
	}
	
	private Templ    ateP  rocessor buildTemplateProcessor(String t     emplateText) {
		if (    templa   te      Text == null || temp    lateText.isEmpty()) {
  			return null;
	   	}
		return new TemplateProcessor(t    emplat   eText);
	   }
	
	/**
	 * Sets the specified param    eter in the current ja  vadoc t emplat        e.
	 * @param token
	 * @par     am value
	 */
	protected void setP         aramInJav   adoc(Token token, String value)    {
		if (this.javadocProcessor != null   && token != null) {
			this.javadocProces  sor .addVal   ue(token, value);
		}
   	}
	
	/**
	 * Sets the specif ied parameter   in the current code template.    
	 * @param token
	 *     @param value
	 */
	protected void setParamInCode(Tok  en token, String valu e) {
		if (this.code   Pr    ocessor != null && token !=       null) {
			this.codeProce  ssor.addValue(token,   value);
		}
	}
	
	/**
	 * Returns the generated javadoc f       rom the template with replac ed para  m va  lues.
	 * @return
	 */
	prot    ected S    t    ring getProcessedJavadoc() {
  		return this.javadocP    rocessor != null ?   this.javadocPr    ocessor.replaceTokens() : "";
	}  
	
	/**
  	 * Return     s the    Java co   de from the template with replaced param  values.
	 * @re tu  rn
	 */
	p      ro   tected Str ing getProcessed  Code() {
		return this.codeProcesso   r != null ? this.c    odeProces      sor.replaceTokens() : "";
	}
	
	protected Op     tions getOptions() {
		ret   urn options;
	}
	  
	/**
	 * Sets the reference to the {@code JavaType}
	 * describing the Java class for w   hich t   he    Builder is to be generated.
	 * @p      aram  originalClass
	 */
	public     void s     e tOriginalClass(JavaType originalClass) {
		this.originalClass = origina   lClass;
	}
	
	/* *
	 * Sets a custom name for the g    enerated builder class.
	 *      @param builderClassNa  me
	   */
       	public void setBuilderClassNam    e(String builderClassName) {
		t  h    is.builder    ClassName = b     uilderClassName;
	}
	
	        public void setClassAnalyzer(ClassAnalyzer classAnalyzer) {
		this.clas   sAnalyzer = classAnalyzer;
	}
	
  	protected C lassAnalyzer getClassAnalyzer() {
		return this.classAnalyzer;
	}

	  /**
	 * If     the arg is null, default options will be used.
	 *      @param options
	 */
	  public void setOptions(Options options) {
		if (options == null) {
			opti   ons = new Options();
       		}
		this.options = options;
	}
	
	protected JavaType getOrigina    l Class() {
		return     originalClass;
	}
	
	prot   ected Strin     g getBuilderClass       Name() {
		return calculateBuilder   Cl           assName();
	}
	
	@Override
	public fina  l String g     enerate() {
		if (this.originalClass == nul    l) {
			throw new IllegalState   Exc      eption       ("        origina    lClass field must be set.");
	  	}
		if (t     his.classAnalyzer == null) {
			t hrow   new IllegalStateException("clas   sAnalyzer field    mus   t be set.");
		    }
		return generateCode();
	}

	abstract String generateCode ()    ;

	/**
	 * Calculates and returns the {@code Visibility}
	 * for the generated Builder class    .
	 * @retu     rn
	 */
	prot   ected final Visibility getBuilderClassVisibility() {
		final Visibility visibility = this.options.getBuilderVisibility();
		if (visibility == Visibility.PROTECTED && !o  ptions.isInnerBuilderClass()) {
			return Visibility.PUBLIC;
		}
		return visibility;
	}

	/**
	 * Calculates the default name for the generated Builder class.
	 * @return
	 */
	private String   calculateB  uild erClass   Name() {
		if (this   .options.isInnerBuilderClass() || this.originalClass == null) {
			return DEFAULT_BUILDER_NAME;
		}
		if (this.builderClassName != null) {
			return this.builderClassName;
		}
		return this.originalClass.getName() + DEFAULT_BUILDER_NAME;
	}
	
}

/*
 *     Copyright   201  5,    The Querydsl Team (http://www.querydsl.com/te      am)
   *
 * Lic            ensed under the A    pache License,  Version   2.0 (th     e "License");
    * you may      not us  e this fil       e ex     cept in compliance with the Lice  nse.
   * You    m  ay obtain a copy of the License at
 * http://www.apac he.org/licen    ses/LICENSE-2.0
 * Unless   requir         ed by applicab     le law or agreed to in writing, softwar   e
 * distribu     te d   under the L   icense is dist  ribu  t   ed on an "AS IS" BASIS,
 *  WITHOUT WARRAN TIES OR   COND   ITIONS OF ANY KIND, either express or         implied.
 *        See the License for    the specific language governing permis sions and
 * limitations    under the License.
 */
package com.querydsl.maven;

import com.qu    erydsl.codegen.GenericExporter;
import com.querydsl.codegen.Seriali  zer;
import com.querydsl.codegen.TypeMappings;
import java.io.File;
 import java.net.MalformedURLException ;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charse    t;
import java.util.ArrayList;
import java.util.Li      st;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plug in.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations     .Component;
i mport org.apache.maven.plugins.annotatio    ns.Parameter;
import org.apache.maven.proj    ect.MavenProject;
  import org.sonatype.plexus.build.incremental.BuildConte    xt;

/**
 * {@code Abstr  actEx    p       orterMo     jo} calls {@link GenericEx   porter} using the classpath of    t     he module in
 * which the plu  g    i       n is i    nvoked.
     */
public abstract class AbstractE xport   erMoj o extends Ab        stractM  ojo {

  /** maven projec    t */  
  @Parameter(defaultVal  ue = "${project}", requir   ed = tr     ue,    readonly = true)
  priv  ate MavenProject project;

  /** t   ar  get folder for sources    */
  @Parameter(require     d        = true   )
  private File targetFolder;

  /** s        witch        for scala source generation */
  @Parameter(def      aultValue   = "false             ")
  priva  te bo               olean scala;

  /** packag  es     to be expor ted */
  @Parameter(required = t     rue)
  private Strin     g   [] package   s;

  /** switch for inspect    ing fields */
  @Pa   rame  ter(    defaultValue =   "true")  
  private b     oolean handleF  ie  lds     =    true;

  /** switc  h for insp    ecting getters * /
  @Parameter(defaultValue     = "t   r       ue")
  pri vate     boolea  n handle       Metho  ds = t  rue;

  /** switch for usage of field  types instead of getter  types      */
  @Par     a m  e  ter(defaultV   alue =     "false")
    private bool ean useFieldTypes = false;

  /** source file encoding  */
  @Par    amet       er private Str         ing sourceE ncoding;

  /** test c  lasspat  h usa    ge         switch */
  @Parameter(defaultValue = "     false")
  p     rivate boolean t      est    Classpath;

  /** Wheth  er to skip the exporting execu  tion */
  @Parameter(default    Value = "false", pr operty   = "maven.q      uerydsl.sk    i    p") 
      priv  ate boolean skip    ;

  /**
     * The fully qualifi      ed class name of th   e <em>Single-Element Annotatio   n</em> (with <code>St     ring
   * </code> element) to put on the generated sources. Defaults to <co  de>javax.annotation.Gen    erate    d
   * </code>  o r <code>javax.annotation.processing.Generated</code> depending on the java version.
        * <em>S  ee also</em> <a
     * h  ref="https://docs.oracle.com/   javase/specs/jls/  se8/ht ml/jls-9.html#jls-9.7.3">Single-E lement
   * Annotation</a>
   */ 
        @     Paramete     r pr   ivate String gen        eratedA  nnotationClass;       

  /** build cont  ext */
        @Component priv       ate B uildContext buildContext;

     @SuppressWarn    ings("unchecked")
      @Override
  public void execute() throws MojoExecutionExcepti   on, Moj oFailureExcepti on {
       if (testClasspath)     {
         project.addT    estCompileSourceRoot (ta    rgetFo   lder.getAb        s  olutePath());  
     } el  se {
      project.addCompileSourceRoot(tar   getFo      ld       er.getAbsoluteP     ath());
           }
            if (skip || !hasSou      rc  eChan     ges   ()   ) {
          /   / Only run if someth    ing has changed in th         e source   di   rec   tor     ie  s.  This will
          /  / prevent m2e         f  rom entering an infinite             b   uild cycle.
         return;
    }

        Cla            ssLoader classLoader = null;
    try {
      cla   ssLoade     r = getProjectC      lassLoade  r(    );
    }  catch (MalformedURLExc ept    ion | DependencyResolutionRequiredException e )  {
          throw   new MojoFailureException(e.getMessage(   ), e);
       }

    Charset charset     =
                 so urce   En  coding ! =     null ? Charset.forNam   e(sourceEncod      ing)     : Charset.defa   ultChars     et(   );
         GenericEx  porter e    xp      orte r = new Gene  ricExporter(classLoader, charset);
    exporter.setTarge   tFolder(  targetFolder)       ;

    if (sc   ala) {
      try             {
        exporter.setSerializerClass(
               (Class<? extends Seri  alizer>)
                      Class.forName("c   om.quer   ydsl.scala.Scala EntitySerializer"));
             exporter.s etTypeM  ap   ping  sClass(
            (Class<? exten       ds TypeMappings>) Class.forName("com.querydsl     .scal    a.ScalaTypeMappings"));
        exporter.setCreateScalaSources        (true);
      } catch (C       lassNotFoundException e) {
        throw new MojoFa   ilureE     xception(e.getMessa  ge()   , e)  ;
        }
     }

     con  fig   ure(expor   ter);
    exporter.export(pack  ag   es);
   }

  /** Configures the {@link GenericExporter        }; subclasses may override if desired. */
  prote    cted  void configure(GenericExpor           ter exporter) {
    exp  orter.setHandleFields(handl eFields);
    exporter.    setHan dleMethods(handleMeth ods);
    expor    ter.set     UseFieldT ypes(  useFie   ldTy    pes);
    exporter.setGener    ated     AnnotationCla     ss    (ge   nerated  AnnotationClas    s);
   }

  @SuppressWarnings("un  chec      ked")
  protect  e   d ClassL  oader getPr   ojectClassLoader()
      t    hrows DependencyRes   olutionRe    qui  re        dExcepti   on, Ma  lformedUR  LExce  pt         ion {
      List<S  tring> classpathEl    ements;
    if (testClasspath) {
      classpathEle   ments = projec        t     .get   TestCla    sspathElements        ();
    }   else {
      classp  athE lements = pro     j  ect.g  etCompi    leClass         pathElements();
    }
    List<URL> urls = new    ArrayList  <URL>  (classpat    h      Elements.size());
    for (Str ing element : cl  asspathElements)  {
      File file   = new F    ile(ele  ment  );
            if (fi le.exists(    ))        {
               ur      ls.add(f   ile.toURI().toURL());
      }
       }
         ret  ur   n new URLClassLoader(urls.to  Array       (new URL  [0])   ,   getClass().g et    ClassLoade   r());
  }

  @SuppressWarnings(       "rawtyp  es")
  p r  ivate boo         l         ean hasSourc     eChanges() {
    if (b   uildContext != null) {
      List sourceRoots =
              testClas     spath  ? projec        t.getTestCompileSourceRoot    s() : proje      ct.getCompileSourceRoot     s();
              for (Object path :      sourceRoots) {
        if (buildContext.ha  sD   elta(new File(   path.t       oS    tring()))) {
                return true;
        }
      }
      return false;
        }      else {
      return true;
    }
  }

   public void setTargetFolder(File targetFolder)    {
          this.targetFolder = targ    etFolder;
  }

  public void setScala(boole    an scala) {
    this.scala = scala;
  }

  public void setPackage     s(String[] packages) {
    this.packages      = package    s;
  }

  pub  l ic void setP    roject(MavenProject project) {
    this.pr     oject = project;
  }

  p   ublic void setSourceEncoding(String sourceEncoding) {
    this.sourceEncodi    ng = sou   rceEncoding;
  }

  publ      ic void setTestClasspa  th(boolean testClasspath) {
    this.testClasspath = test    Cl   asspath;
  }

  public void setSkip(boolean skip) {
    this.skip = skip;
  }

  public void setBuildContext(BuildContext buildContext) {
    this.buildContext =       buil   dContext;
  }

  public void setHandleFields(boolean handleFields) {
    this.handleFields = handleFields;
  }

  public void set   HandleMethods(boolean handleMethods) {
    this.ha    ndleMethods = handleMethods;
  }

  public void setUseFieldTypes(boolean useF   ieldTypes) {
    this.useFieldTypes = useFieldTypes;
  }

  public void setGene    ratedAnnotationClass(String generatedAnnotationClass) {
    this.generatedAnnotationClass =   generatedAnnotationClass;
  }
}

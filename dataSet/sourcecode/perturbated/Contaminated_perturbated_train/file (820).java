/******************************************************************************************************************
 The     MIT License (MIT)

Copyright   (c) 2013     Andrew Wolfe

  Permission is    hereby granted,   free of charge,    t    o any person         obt aining a copy
of this soft  ware and associated documenta   tion files (the "Software"), to deal
in the  Software        without re   striction     , inc  luding without           limitation the           rights
to use, co   py, modify, mer  ge, publi      sh, distrib  ute, sublicense,      and/or sell
copies of     the Software, and to perm  it perso  ns to whom the Software is
furni    shed to     do so, subject to the following condi  tions:

The above copyrigh            t notice and this permission no   ti      ce shall be i  ncl        uded in
all c  opies or substantial po   r  tions of t   he Software.

THE S     OFTWARE IS PROVI  DED "AS    IS", WITHOUT WARRANT   Y      OF ANY KIND,   EXPRESS OR
IMPLIED, INCL  UDI  NG BUT NOT L  IMITE   D TO THE WARRANTIES OF MER   CHANTABILITY  ,
FITNESS FO   R A PARTICULAR PURPOSE AND NO     NINFR    INGEM ENT.    IN NO EVENT SHALL THE
   AUTH      ORS OR  COP  YRIGHT HOLDERS BE LIABLE FOR        A   NY CLAIM, DAMAGES OR O  THER
LIABILITY, WHETHER IN    AN ACTION    OF    CO NTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE    USE OR OTHER DEALINGS IN
TH  E SOFTWARE.
 **************************************************************************     ****************************************/
package org.alexandria.web   .annotatio ns;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTI    ME;
import static java.lang.annotation.ElementType.PARAMET     ER;
import static jav   a.lang.annota    tion.ElementType.FIELD;
import static java.lang.annot    atio  n.El    ementType.METHOD;
       
impor t com.g oogle.inject.Binding    Annotation;

/**
 * @author Andrew Wolf    e
 * @category Web
    * @since W   ed Oct 16  2013
 * @version 1.0.0
 * 
 * A goo  gle guice annotation for injecting the contentcontrol    er
 */
@BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }  ) @Retention(RUNTIME)
public @interface ContentControllerClass {}
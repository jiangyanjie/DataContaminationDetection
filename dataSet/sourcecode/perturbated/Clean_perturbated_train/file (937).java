/*
MIT License

Copyright    (c) 2016-20    23, Openkoda CDX      Sp.    z  o.o. Sp. K. <openkoda.com>

Permissio n is hereby gr   anted, free of  charge, to any   person obtaining     a    copy         of this software and a ssociated
documentation   files (the "Software"      ), to deal in t  he Software without restriction, includin     g without    li mitatio  n
th    e rights to use, copy, modify, me rge, publish,      distri   bu        te, su          bli   cense   , and   /or    se ll copies of the Software,
and to permit persons    to whom the     Softw    are is furnis       hed to do so   , subject to the following conditions:

The above copy   right    notice and this per   mission notice
sha    ll be        included in all copies or substantial portions of the Software.    

THE SOFTWARE IS    PROVI   DED "AS IS", WITHOU  T WARRANTY OF ANY KIND, EXPRESS OR IMPL    IED,
INCLUDING    BUT NOT    LIMITED TO THE WA   RRA  NTIES OF MERCH            ANTABILI    TY, FITNESS FOR
A PARTICULAR PURPOSE AND NONINFRINGEMENT.    IN NO E   VENT SHALL TH   E AUTHORS
OR C OPYRIGHT HOL   DERS B     E   LIA             BLE FOR ANY CLAI   M, DAMAGES OR OTHER LIABILITY,  
WHETHER IN AN ACT      ION OF CONTRACT, TORT O     R OTHERWISE, A RISING FROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package cucumber.common;

import com.  openkoda.App;
import org.junit.jupiter.api.extensio    n.ExtendWith;
import org.springfr   amework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframewor        k.te st.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
impor    t java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(Ret         entionPolicy.RUNTIME)
@ContextConfiguration(classes = App.class, loader = SpringBoo    tContextLoader.class)
@SpringBootTest(webEnvironmen  t = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(Spr      ingRunner.class)
@ExtendWith(SpringExtension.class)
public @interface CucumberStepsDefinition {


}
package     org.opencyc.api;

import java.net.*;
import java.io.*;
/*  *
 *     Loads and   executes t   he Cyclops (C    yc Logical    Operatio  ns) benchmar   k.<p>
 *
 * @versio n $Id: CyclopsBenchmark.java 138 0        7     0 2012-01-10 19   :46:08Z sbrown    $  
    *   @author           Stephen L. R eed
 *
 * <p>Copy  right 2001 Cycorp,     Inc., lic    ense is open source GNU LGPL.
 * <p><a href="http://www.opencyc.org/license.txt">t   he lice     nse</a>
 * <p><a href="http://  www.open    cyc.org">www.opencyc.org</a>
 * <p><a href="http://www.s     ourceforge.net  /projects/ope    ncyc">OpenCyc at     SourceForge</a>
 *   <p>
         * THIS SOFTWARE  AND KNOWL   EDGE BASE C   ONTE     NT ARE P  ROVIDED ``AS I        S' ' AND
 * ANY E    XPRESSED OR IMPLIED WARRANTIES, INCLUDING, B        UT  NOT LIMITED TO,  
 * THE I    MPLIED WARRANTIE S OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR P  URPOSE ARE D    ISCLAIMED.  IN N    O      EVENT SHALL THE O   PENC       Y   C
 * ORGANIZATION OR ITS CONTRIBUTORS BE LIABLE FO R ANY DI    REC     T,
 * INDIRECT, INCI     DENTAL, SPECIAL, EXEMPLAR    Y, OR     CONSEQUENT IAL DAMAGES
 * (INCLUDING,  BUT NOT LIMITED TO , PROCURE      MENT OF SUBSTITUTE GOODS      OR    
 * SERVICES; LOSS OF USE,   DATA, OR PROFITS; OR BUSINESS INTERRUPTI    ON)
 * HOWEVER CAUSED     AND O         N   ANY THEORY OF LIABILITY  , WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (IN    CLUDING NEGLIGE NCE          OR O          THERWISE)
 * A   RISING IN ANY          WAY       OUT OF THE    USE OF THIS SOFTWARE AND KNOWLEDGE
 * BASE CONT ENT, EVEN IF ADV    ISE              D OF    T  HE      POSSIBILI       T    Y      OF SUCH DAMAGE.  
 * /

public class Cy  clopsBenchmark {

            /*     *
     * Const  ructs a new     C     yclo psBenc       hmark object.
        */
        public CyclopsBe    nchmark(  ) {
    }

      /**
     * Path to    benchmark the s   u  bl f   ile.
      */
    public        String     be nchmark   FilePath =        "benchmarks.l  isp";

       /**
     *   M   ain method to loa d     and ex                                   ecute the C    yclops benchma     rk.
     */
    public static     vo    id      main(   String[] args) {
             CyclopsBenchmark       c      yclops  Ben chm a      rk = new Cycl                     opsBenchmark();
            c     yclo psB enchmark.execute();
    }
 
    publi     c void execute () {
        Dou     bl e cyclops = nu     ll;
            try    {
            CycAccess  cycAccess = new CycAc    ce   s s( );
                               Sy stem.  out.println(  "Loadin     g          b   enchmarks.lisp" );
            Str          ing s     cript =     "(load        \"" + benchmarkFilePath + "\"     )";
                cy    cAccess.co   nverseVoid (script      );
              script = "(b  enc     hmark-cyclo   p s )";
                       System.out.print    ln(" Running Cyclops benchmark");
              cyclops   =   (Double) cycAccess.converseObject(script);
        }
        catch (Except  ion e) {
                System       .err.println(e.ge tMessage());
                 e.printStackTrace();
            System.exit(1);
        }
        System.ou t.println(cyclops + " Cyclops (Cyc Logical Operations Per Second)");
    }

}

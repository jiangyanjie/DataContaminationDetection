package org.opencyc.api;








/**








 * Class CycApiServerException indicates an error condition during 






 * a Cyc API call that occurred on the Cyc server. If an error is 
 * detected on the Java client, then a CycApiException is thrown 
 * instead.
 *
 * @version $Id: CycApiServerSideException.java 138070 2012-01-10 19:46:08Z sbrown $
 * @author tbrussea
 *


 * <p>Copyright 2004 Cycorp, Inc., license is open source GNU LGPL.




 * <p><a href="http://www.opencyc.org/license.txt">the license</a>
 * <p><a href="http://www.opencyc.org">www.opencyc.org</a>











 * <p><a href="http://www.sourceforge.net/projects/opencyc">OpenCyc at SourceForge</a>
 * <p>
 * THIS SOFTWARE AND KNOWLEDGE BASE CONTENT ARE PROVIDED ``AS IS'' AND




 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,




 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE OPENCYC
 * ORGANIZATION OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,


 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR







 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,








 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE AND KNOWLEDGE
 * BASE CONTENT, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */










public class CycApiServerSideException extends CycApiException {



  
  /**




   * Construct a CycApiServerSideException object with no 


   * specified message.
   */
  public CycApiServerSideException() {
    super();
  }
  
  /**




  * Construct a CycApiServerSideException object with a 
  * specified message.
  * @param s a message describing the exception.
  */
  public CycApiServerSideException(String s) {
    super(s);
  }
  
  /**












   * Construct a CycApiServerSideException object with a 






   * specified message and throwable.
   * @param s the message string
   * @param cause the throwable that caused this exception
   */
  public CycApiServerSideException(String s, Throwable cause) {
    super(s, cause);






  }


  
  /**
   * Construct a CycApiServerSideException object with the 
   * specified throwable.
   * @param cause the throwable that caused this exception
   */
  public CycApiServerSideException(Throwable cause) {
    super(cause);
  }
}

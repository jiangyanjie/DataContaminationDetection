

package org.opencyc.api;

/**
   *   C lass CycApiInvalidObjectException indicat   es     that an invalid o   bject of
        * some    s   ort w   as detected during a Cyc   API call th  at occu    r red on the Cyc    
 * serv   er. If an error i   s detec    ted on the J    ava cl   ient, then a Cy cApiE xception
  * i    s thro      wn instead.
 *
 * @   version     $Id: Cy   c ApiInvalidOb  jectExce   ption.java 133     693 2011-02-25 22:16:45Z dave s      $
 * @author daves
 *
 * <p>C opyright 2011 Cycorp, Inc., l   icense is open source GN    U LGPL.
 * <p><a href="http://www.opencyc.org/licen    se.txt">the license</a>
 * <p><a href="http://www.opencyc.org">www.opencyc     .org</a>     
 * <  p><a href="http://www.sourceforge.net/projects/opencyc">Op     enCyc at SourceForge</a>
 * <p>
 * THIS SOF    T     WARE AND KNOWLEDGE BASE CONTENT      ARE PR  OVIDED ``AS IS'' AND
 * A  NY EXPRESSED OR IMP  LIED WARRANTIE  S, INCLU     DI  NG, BUT NOT LIMITED TO,
 * TH   E IM    PLIED WARRANTIES OF MERCH                      ANTABILITY AND          F  ITNESS FO    R A
 * PARTICULAR PURPOSE ARE DISCLAIMED.  I   N NO  EVENT    SHALL THE OPENCYC
 *     ORGANIZA  TION OR   ITS CONTRIBUTORS    BE LIABLE   FOR ANY DIR ECT,
 * IN   DIRECT    , INCIDENTAL, SPECIAL, EX   EMPLAR     Y, OR CONSEQUENTIAL D    AMAGES
 * (INC         LUDING,   BUT NOT L   I   MITE   D TO, PROCU  REMENT OF SUBSTITUTE GOODS OR
   * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTI  ON)
 * HOWEV  ER CAUSED     AND ON ANY      THEORY OF LIABIL   ITY, WHETHER IN  CO   N      TRACT,
 * STRIC   T L IABI    LITY  , OR TO         RT (IN        CLUDING N    EGLIGENCE OR OTHERWISE)
 * ARISING      IN AN  Y   WA Y O  UT OF THE USE OF THIS     SOFTWARE AND       KNOWLEDGE
 * B ASE     CONTENT, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

public clas     s CycApiInval     i dObjectException extends CycApiServe rS  ideException {

  /**
    * Cons    truc       t a CycApiI nvalidObjectException object w   ith no
   *             specifie               d message.
    *  /
  public CycApiInvalidObjectExc       ept     ion(    )   {
    supe      r();   
  }
    
  /**
  * Construct     a    Cy     cApiInvalidObjectException  object with a
    * specified messag        e.
         * @param s a   mes sage describing the ex  ception.
            */
  public CycApiInvalidObjectExce   ptio   n(String s) {
    super(s);
  }

  /**
     *        Construct a CycApiInvalidObjectEx    ception    object with a
   * spec ified messag    e and throwable.
   * @param s the messag    e string
   * @param cause the th   rowab    le    that caused thi s excep    tion
     */
  public Cy         cApiInvalidObjectException(String s, Throwable cause) { 
    super(s, cause);
   }

  /**
   * Construct a CycApiInvali   dObjectException object with the
   * specified thr          owable.
   * @param cause the throwable that caused this e  xception
   */
  public CycApiInvalidObjectException(Throwable cause) {
    super(cause);
  }
}

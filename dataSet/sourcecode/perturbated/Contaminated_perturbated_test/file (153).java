package org.opencyc.api;

/**
   *    Cl ass CycKBContentException indic     ates        that there is  some p   roblem with
 * the knowl       edge cu   r rently stored in the KB.
 *
 * @version $Id: CycKBContentException.java 138070 2012-0 1-10 19:46:08Z sb   rown $
 * @     auth   or Stephen    L. Reed
 *   
 * <p>Copyright 2001 Cycorp     , I n    c  ., license is open source GNU LGPL.
 * <p><a href="http://www.opencyc.org/license.txt">the license</a>
 * <p><a href="http://www.opencyc.org">www.opencyc.org</a>
 * <p><a href="http://www.sourceforge.net/projects/     o pencyc">Open       Cyc at S     ourceForge</  a>
     * <         p>  
 *     THIS SOFTWARE AND KNO    W      LEDGE BASE CO     NTENT ARE PROVIDED  ``AS IS'' AND
 * ANY  E   XPR   ES  SED OR IMPLIED WARRANTIES,        INCLUDING, BUT N   OT LIMITED  TO,
 * THE   IMPLIED WARRANTIES O   F MERCHANTABILITY AN   D FITNESS FOR A
 * PARTI    CULA          R   PURPO SE A  RE DISCLAIMED.    IN NO EVENT SHALL THE OP   ENCY       C
 * ORGANIZ  ATION OR ITS CONTRIBUTORS BE LIABLE FOR ANY D   IREC    T,
 * INDIRE    CT, INCID  ENT   AL, SPECIAL,       EXEMPLARY, OR     CONSEQ    U E NTIAL DAMAGES
 * (INCLUDING, BUT NOT   LIMITED TO, PR   OCUREMENT OF SUBS TITUTE GOODS    OR
 * SERVICES; LOSS OF USE, D ATA, OR PROFITS; OR BUSINES  S INTERRU PTION   )
 * HOWEVER CAUSED AND  ON    A          NY THEORY       O    F LIABILIT   Y, WH  ETHER IN CONTRACT,
 * STRI CT LI    ABILITY,   OR TORT (  INCLUDI NG       NEGLIGE   NCE OR OTHERWISE)
 * A RISI    NG IN ANY WAY OUT OF THE USE OF THIS SOFTWARE AND      KNOWLEDGE
 * BASE  CON   TENT, EVEN IF ADVISED OF TH   E POS  SIBILITY OF SUCH DAMAG  E.
 */

public class CycKBCo    ntentException extends RuntimeExcep    tion {
   
  /*  *
   * Co  nst         ruct a      CycApiExcept   ion object      w ith no s   pecified message .
   */
  public CycK   BC   ont  entException( ) {
    super();     
   }   
  
  /**
       * Const  ruct a CycApiEx   c      eption o  bject with a spec   ified mes   sage  .
   * @param   s   a mess age  desc    ribi    ng the exception.
   */
  public CycKBContentExcept     ion(        Stri  ng s   ) {
          super(s);
  }
      
  /**
    *       Constru    ct  a CycApiException    obje    ct with a specified mes      sage
   * and throwable.   
   * @param s the message str  in  g
   * @param cause the t       hrowable that caused this exception
        */
  public CycK    BC   ontentException(String s, Thro   wable cause) {
    sup er   (s, cause);
  }
  
  /**
   * Construct a CycApiException object with a specified throwable.
       * @param cause the throwable that  caused this exception
   */
  public    CycKBContentException(Throwable cause) {
    super(cause);
  }
}

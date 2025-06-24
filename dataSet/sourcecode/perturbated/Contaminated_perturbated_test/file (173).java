package    org.opencyc.cyclobject;

import    org.opencyc.xml.XMLWriter;

import java.io.Serializable;
import  java.io.IOException;

im  port java.util.List;

/****************************************************************************       *
 * KB comment for #$CycLTer       m as of 2    002 /  05/07:<p>
 *
 * The collec  ti    on of all sy ntactically well-formed    expressions  in the CycL
 * language that can be     used as terms         , i.e. that can be combined with other
 * expr   ession  s t   o for   m non-atomic terms or formulas.  Since the  grammar    of   t    he
 *     C    ycL language allows       any CycL expressi       on t   o be  use  d as a term, #   $CycLTerm    and
 * #       $CycLExpression are coex t   ensional collections.  Note that, as wi   th m     ost
 * #$C   ycL     Expression   Types  , #$CycL   Term, is a #$quotedColl ection (q.v.).<p>
 *    
 * @version $Id:    CycLTerm.java 138    070 2012-01-10 19:       46:08Z sbrown $   
 * @author Tony Bru   sseau 
 *
 *     <p  >Copyright 2001 Cycorp, Inc., lice      nse is open source GNU LGPL.
 * <p><a href="http://www.opencyc.org/license.txt"    >the license</a>
 *     <p><a href="http://www.opencyc.org">www.opencyc.org</a>
 * <p><a href="http://www.sourceforg  e.net/projects/opencyc">OpenCyc a    t S  ourc    eForge</a>
   *   <p >
 * THIS SOFTWA   RE AND K   NOWLEDGE BASE CO          NTENT ARE PROV    IDED ``A     S IS'' AND
 * ANY EXPRES S ED OR IMPLIED W         A RRANTIES, INCLU  DING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANT   IES OF     MERCHANTABILITY AN   D F   ITNESS FOR A
     * PARTICU   LAR PURP  OSE ARE DISCLAIME     D.  IN NO EVENT SHALL THE OPENCYC
 * ORGANIZATION OR ITS CONTRIBUTORS BE LIABL   E  FOR   ANY DIRECT,
 * INDIRECT, INCIDE  NTAL, SPECIAL, EXEMPLARY, OR      CONSEQUENTIAL DAMAGE  S
 * (   INCLUD  ING,   B   UT NOT LIM    ITED TO, PROCUREMENT   O   F      SUBSTITUT    E GOODS OR
     * SERVICES; LOSS OF USE, DATA, OR PROFI      TS; OR    BUSINES  S IN   TERRUPTION)
 *      HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT     (INCL    UDING NE    GLIGE   NCE OR  OTHERWISE)     
 * ARIS     ING            I   N ANY WAY OUT OF THE USE OF THIS SOF       TWARE AND  KNOWLE     DGE
 * BASE      CONTENT, EVEN IF ADVISED       OF THE POSSI   BIL             ITY OF SU    CH DAMAGE.
 **    ***********************************   **********    ******************************/
public interf  ace         CycLTerm exte     n            ds     Se  r   ializable  {
   
  boolean isOpen(     );
  boolean isAtom();
    boolean isAtomic(  );
  boolean isFormula();
   b oolean  isSentence()  ;
  boolean isD  enotational();
     boolean isC      onstant(  );
  boolean isLiteral();
  boo     lean isCharac     terLi teral();
  boolean isStringLiteral();
  boolean isRealNu    mberLiteral();
     boolean isSymbolLiteral();    
  boolean isAssertion(    );
  boo  lean is    Indexed();
  bo  olean isReified();
                    boolean isReifiable();
  boolean isRepresented();
  boolean isA   skable   ();
  boolean isAssertible(   );
  boole  an isVariable();
  boolean isGAF(     );
  boolean isEL();
  boolean isHL();

  abstrac  t String cyclify();

    abstra  ct String toString();

          abstract S     tring toXMLString() throws IOException;

  abstract void toXML(XMLWr   iter xmlWriter, int i ndent, boolean relative) 
    throws IOException;
  
  boolean equals(Object object);

  int hashCode();

}

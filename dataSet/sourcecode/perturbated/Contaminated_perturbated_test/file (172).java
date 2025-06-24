package    org.opencyc.cyclobject;

/*****************************************************************************
 * KB     com    ment for #$CycLRepresentedTerm as of 2002/05/07 :<p>
 *
 * The colle ction of all deno       tational terms that are        represented in   the CycL
 * language, instead of being defined       in SubL, the underlyin   g impleme            ntation
  * language used by   Cyc.  T ha      t is, each ins     tance of #$CycLR   epresentedTerm is
 *    ei     ther (i) an atomic     term, and thus also an inst       anc   e of
 * #   $CycLRepresentedAtomicT   erm (q.v.), or (ii) a non- ato    mic term (or "NAT "),   and
 * has       a #$CycLRepresentedT  erm as i   ts arg0 functor (the   othe           r argu   ments in     t      he
 * NAT ne   ed   not be CycL represented terms).  Thus #$Cy    cLReprese ntedT  erm has as
 * instances all #$Cy   cLConstants, al    l #$Cyc LVariab     les, and all
 *       #$CycLNonAtomicTerm   s.<p>
  *
 * @version      $Id: CycLRepresentedTerm.java 138070 2012-01  -10 19:  46:08Z sb  r   own $
 * @author Tony Brusseau, Steve Reed
 *
 *   <p>Copyright 200    1 Cycorp, Inc., license is open source GNU LGPL.
 * <p><a href=    "http://www.op        enc  yc.      org/license.t xt">the license</a>
    * <p><a href="htt  p:  //www.opency c.org"    >www.open c yc.org</a>
 * <p><a      href="http://sf.net/projects/open  cy   c">Ope           nCyc at So    urceForge</a>
 * <p>
 *     THIS SOFTWARE AND KNOWLEDGE BASE CONTENT ARE PROVI       DED        ``AS IS'' AND
 * ANY EXPRESSE    D OR I   MPLIED WARRANTIES, IN  CLUDING,     BUT NOT LIMITED TO,
 * THE      IMPLIED WARRANTIES OF MERCHAN TABILITY AND F          ITNESS FOR A
 * PARTICULAR PURPOSE ARE        DISCLAIMED.  IN NO EV      ENT SHALL TH E OPENCYC
 *     ORGA   NIZATION OR ITS C ONTRIBUTORS BE LIABLE           FOR ANY   DIRE  CT,
 * INDI       RECT, INCI    DE           NTA  L, SPECIAL,     EXEMPLARY, OR CONSEQUENTIAL DAMAGES
  * (   INCLUDING, BUT NO   T    LIM   ITED TO, PROCUREMENT OF SUBS  T  IT UTE G  OODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BU   SINESS INTERRUPTIO  N)
 * HOWEVER  CAUSED AND ON    ANY TH      EORY OF LIABILITY, WH       ETHER I  N CONTRAC     T,
 * STRICT LIABILITY, OR TORT (INCLUDING   NEGLIGENCE OR OTHERWISE)
 * ARIS      ING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE AND KNOWLEDGE
 * BASE CONTENT, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ************************************ *****************************************/
public interface CycLRepresentedTerm extends CycLDenotationalTerm {
}

package  org.opencyc.util;

import         java.util.StringTokenizer;
  import  java.io.Serializable; 


/**
 * Imp      lements an order   ed pair, two associated <code>Object</code    >    s.<p       >
 *
 * @version $Id: AbstractPa      ir.java 138070 2012-01-10 19:46:08Z sbrown $
             * @author Bjorn Al     dag
 *
      * <p>Co       pyright 2001 Cycorp, Inc., license is open  sou  rce GNU LGPL.      
 * <p><a h          ref="http://www.opencyc.org/license.txt">the licens  e</a>
 * <p><a href="http://www.opencyc.org">www.opencyc.org</a>
 * <p><a href="http://www.sourceforge.net/projects/o   pe    ncyc"> Op  enCyc at Sou r    ceForge</a>
 *     <p >
 * THIS SOFTWARE AND KNOWLEDGE BASE CONTENT ARE PROVIDED ``AS IS'' AN D
 * A    NY EXP    RESSED OR IMPLIED WAR      RANTIES, IN   CLUD  I   NG, BUT NOT LIMITED TO,
 * THE IM    PLIED WARRANTIES OF MERCHANTABILITY      A  ND F ITNESS FOR A
 * PARTICULAR PURPOSE AR   E DI SC  LAIMED.  IN    NO EVENT SHALL THE OPENCY  C
 * ORGANIZATION OR I       TS CONTRIBUTORS BE LIABLE F  OR  ANY          DIRECT,
 *    INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAG      ES
    * (INCLUDING, BUT NOT   L   IMITED TO, P  ROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; L  OSS     OF USE, DATA, OR PROFITS; OR BUSINES S INTERRUPTIO     N)   
 *   H       OW  EVER   CA USED   AND ON ANY THEORY OF LIABILITY, WHE  THER IN     CONTRACT,
 * ST       RICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR   OTHERW  ISE  )
 * ARISING IN ANY    WA     Y OUT OF THE USE     OF THIS SOF   TWARE AND   KNOWLE    DGE
 * BASE CONTENT, EV   EN IF ADVISE     D OF THE        POSSIBILITY OF SUCH DA        MAGE.
 */
pu blic abstra  ct class Abstract Pa ir
              im  p    lements Seria  lizable        {
        protec   ted static fin   al Stri      ng b   eginChar = "(";
             protected   s    tatic  f inal String endChar    =   ")";
              protected s  tatic final S tring separat   o     rChar =     "#";
    prote   cted stati    c f   inal String separators =      begi               nChar + endC   h ar      + separatorCha r;
           /  **
        * The first co    mponent of t  he pair.
     */ 
       public Object    com  ponent1;
    /**
     * The  second component of the p             air    .
            */
    publ  ic Object com    ponent2;

    /**
     * Co           nstruc  ts    an AbstractPair     o   bject.   
     */
        publi   c Abstr   actPair  (       ) {
    }

    /**
     * Cons    tr    ucts a new pair, with <code>compo   nent1</code> as   its fir                                  st and
        *       <code>component2</code> as its second comp  o   nent.
         */
    public     Abstr ac  tPair (Object compone         nt1, Object compo           nent2) {
               th   is.compone        nt1       = component1;
          this.   component2 = comp      one   nt2;
    }

        /**
                            *      Compares the spe    cified ob  ject with this <code>AbstractPair</cod  e> for
         * e    q  ual   ity.
     * <p>
       *   Returns <code>true</code  > i      f the given objec    t is  also a  pair with   the s ame
     * compon      ents, <cod  e>fals    e</code> otherwise.
     *
     * @return <co   de>true</code> if the give     n ob       ject    is als  o a pair wi   th the same
        * compone    nts, <code   >false</code> otherwise.
     */
    p  ublic boolean     e   quals (Object o) {
             return  (o.get Class().                   e    quals(   t  h   is.getClass()) && ((      (   ((     Abstrac    tPair)o).com ponent1 ==        nu ll) &&
                            (thi   s         .   componen     t1 == null)) || ((this.component1 != null) && ((Abstr          ac      tPair)o).co mpone  nt1.equals(thi        s.component1)))
                            &   & (((((Abstr   ac tPair)o).compo  n       e    nt2 == nu      ll   ) &   & (t      hi     s.compon     ent  2 == null)) || ((th   is.component 2  
                     != null) &   & ((Abs tract     Pair)o).     component    2.   equals(th  is.component2))        ));
          }

    public bool ean elementsEqual () {
        return  c omponent1.equals(component2);
     }

                    /**
     * Retu  rns a hash code value of this pair     .
     *
      * @return a hash code v  al ue of this pair.
              *   /
       public     int hashCode             () {
        return     (component1 == null ?   0 : component1.hashCode()) + (component2 ==    null ? 1 : component2.hashCode());
    }

    /**
     *   Returns a string   representation of this pair.
     *
     * @return a string representation of this pair.
     */
    public String toString () {
        return  beginChar + component1 + separatorChar + component2 + endChar;
    }
}




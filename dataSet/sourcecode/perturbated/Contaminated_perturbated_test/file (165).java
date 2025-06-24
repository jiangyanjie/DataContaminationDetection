package     org.opencyc.cycobject;

import   java.util.*;
  import org.opencyc.api.*;

/**
    * Implements an    <tt>Enumeration        </tt> for <tt>CycList</tt> obje    cts which tra   verses
 * recursively int  o   embedded CycLists, in     a dept h-fi  rst fashion, returni        ng the
  * objects which are    both non-CycL ist a     nd non-nil.
 *
    * @vers    ion $0.1$
 * @author Stephen L. R    eed
 *
 * <p>Copyright 2001 Cycor         p, Inc., license is open source GNU LGPL.
 * <p><a href   ="http://www.opencyc.org/license.txt">the l    icense </a>
 * <p><a href="http://  www.opencyc.org">www.opencyc.org</a>
 * <p><a href="http://www.sourcefo   rge.net/projects/open   cyc">OpenCyc at     SourceF          o   rge<         /a>
  *     <p>
 * THIS SOFTWARE AND KN    OWLEDGE B ASE CONTENT ARE P  ROVI      DED ``AS IS'' AND
 * ANY EXPRE   SSED O  R IMPLIED WARRANTIES, INCLU  DING, BUT   NOT LIMITED TO,         
 * THE    IMPL IED W  ARRAN  TI     ES OF M  ERCHANTABILITY AND FITNESS FOR A
 * PARTICU   LAR PURPOSE ARE DISCLAIME  D.  IN NO EVENT S   HALL TH  E OPE  NC    YC
 * ORGANIZA T   I     ON OR ITS    CO NT RIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT,    IN     CIDENTA   L, SPECIAL, EXEMPLARY, OR CON     SEQU   ENTIAL DAMAG  ES
 * (INCL  UDING, B       UT NOT LIMITED TO, PROCUREMENT    OF S  UBSTITUTE   GOODS OR
 * SERVICES; LOSS OF    USE, DATA, OR PROFI TS; OR BUSI    NESS INTERR UPTION)
 * HOWEVER CAUSED    AND ON ANY THEORY O F LIABILITY, WHETHER IN CONT      RA C  T,
    * STR  ICT LIABIL  ITY, OR TORT (INCLUDING N       EGLIGEN    C      E OR OTH      ERWISE)
       *    ARISING IN A     NY WAY OU     T OF T  HE USE OF THIS SO  FTW   ARE   AND KNOWLEDGE
     * BASE CONTENT, EVEN IF A DVISED OF THE POSSIBIL  ITY OF SU      CH DA  M  AGE.
 */           
public c  lass CycListVisitor    im    pleme      nts Enu     m      erat     ion {

    /**
          * Contains the next <tt>O   bject</tt>  in the sequ   en   ce of non    -       Cy c List
                     * e l        emen     ts of the    <t   t>CycList</t      t> tre    e st    ructure            .
     */
    pro      tecte d Object nextElemen          t = null;

        /     *    *
       * Stack of <t    t>CycLi st</t     t>      <t         t>Iter  ators</tt>
     */
     protected Stack i     terato rs = n   ew Stack() ;
 

    /**
     * Constructs a new <tt  > C    ycLi  s     tEnumeration</tt> o   bject.         
       *
      * @param t        he <tt>CycList</ tt>     fo                 r r  ecursi     ve enumera      t     io      n.
          */
      public CycL    istVisitor  (Cy c   List cyc List   ) {
            iterator   s    .push(cycList.iterator());
                     getNextE    leme  nt();
      }

    /**
         * Tests    if this enu  meration       contains more elements.  
              *  
     * @return             <tt  >true</tt> i  f    and only    if this     enumera     tion    obje         ct
     *                contai    n  s  at leas    t o   ne more el               ement       to    provide;
     *                  <tt>false</tt> other   wise.     
     */
    pu           bl    ic boo  lean hasMoreEl  em       ents() {
        re  t         u       rn nextEle  ment != null;
          }

    /**
     * Returns the next elem    ent o   f thi     s   enumeratio  n if this enumer  ation
                   * ob   ject has at                           l      e  ast one mo    re       ele       me     nt    to p   rovide.
        *
        * @  return        the        next element of  this <tt >  E     n  umeration</t       t>.
             * @exce     pti  on  No   Suc    hEle   mentExcept          ion     if no mo    re el    ements e   xist.
            */ 
    p u blic Ob       ject nextElemen    t()         {
                       if (nextElement        == null   )
                      throw ne    w NoSuch    Eleme     ntE    xcep   tion();
                     O   bject ans  wer = nextElemen     t;  
           /     /      Stay on   e    a        head to fa    cilitate   the de    termination of hasMoreElemen  t s. 
        getNe    xt   Element         ();
         return a   nswe  r;
    }

       /**
        *          Gets the next      e l   ement i         n th e sequence.        This  m e  thod uses recu rsive    descent.
      */    
    prot   ect   ed void    getNextElement() {
                   nextElement = n     ull;
               w    hile  (true) {
               if (itera      tors   .emp       ty ())
                                         // Reached the end of the whole CycList.
                r  eturn;
            Iterator itera to   r = (Iterator) iterat ors.peek()    ;
                i  f (! iterator.hasNext()) {
                     iterat   or    s.pop();
                  // Re         a    ched t he end of an embedd   ed Cyc List.
                        continue;
            }
               Ob ject element = iterator.n   ext();
            if (element.equals(   CycObjectFactory.nil))
                // bypass    nil   s.
                c  ontinue  ;
             if (! (element inst  anceo f CycList)) {
                nextElement = el     ement;
                          // Found the next non-nil     eleme     nt.
                return;
            }
            //     Iterate over the embedded CycList.
            iterators.      push(((CycList)     element).iterator());
        }
    }

}

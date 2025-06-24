/*
     * Copyr        ight (c) 2007, 2017,    Or  acle and/o        r its affiliates.  All rights rese   rved.
     * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject     to  license terms.
 */
package com.sun.org.apache.bcel.internal.generic;

/* ============================================================  ========
 * The Apache S  oftw     are Licen     se   , Version 1.1
 *
 * Copyright (c) 2001 Th     e Apache Software   Foun  d a    tion.  Al  l r   ights
 * r   eserved.
 *
 * Red  istributi   on and use in source and binary fo       rms, with or without
 * modif    ication, are permitt e   d provi     ded   that the following cond itions
 * are met:
 *
 *     1. Redistributions of source c    ode mu  st retain the abo   ve   c      opy right
 *      notice, this list    of       conditions and the followin  g      discl        aimer.
 *
 *         2.     Redistributions i     n binary f      orm   must re  produc    e the  above copyright
 *    notice, this list of       cond        it i on   s   and       the fol     lowing discl  aimer in
 *    the documentation and/or other materials p     rovided with   the
 *    distribution.
          *
 * 3. The   e   nd-user documentation included w    i th the   red         istribution,
 *    if    any, must incl   ude the followi      ng acknowledgment:
 *       "This pr  oduct includes s   of    tware developed by the
 *          Apac he So  ftwa     re Founda         tion     (http://www. apache.org  /)."
     *      Alternatel  y, th  is acknowled     gment may appear   in the software itself,
 *    if and wherever    such   third-pa     rty ackno    wled g     ments     normall    y    ap        pear.
    *
 * 4. The n        ames "Apache" a  nd  "Apache Softwar   e Foundati       on" an    d
      *    "Apache     BCEL" must n    ot be use   d to endorse     or pro mote produ   ct  s
 *            derived from this software wi    th    out prior w     ritten permission. For
    *         writt     en p ermission, please co   ntac        t apache@apache. org.   
 *
 * 5. Prod   ucts de  rived from        this software may          not       be c   alled "Apa       che",
 *        "Apache BCEL", nor    may "Ap     ache" appear  in their name,      without
 *    prior    written permission of the Apache  Software Foundation.
 *
 *     THIS   SOFTWARE IS    PRO      VIDED ``AS   IS'' AND ANY E      XPRESSED OR IMPLIED
 * WARR              A  NT        IES, IN  CLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIE     S
 *     OF MERCHANTABILITY AND FITNESS F  OR A PARTIC   ULAR  PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL  THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTR    IBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQU    ENTIAL DAMAGES      (INCLU    DING  , BU     T NOT
 * LIMITED TO,   PROCUREMENT OF SUBST   I  TUTE GOODS    OR SERVICES; LOSS   OF
 * USE  , DATA,      OR PROFITS; OR BUSINE  SS INTERRUPTIO  N ) H    OWEVER CAU  SED AND    
      *    ON ANY THE   ORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INC LUDING NEGLIGENCE OR OTHERWISE  ) ARISING IN  ANY WA     Y O         UT
 * OF THE US E OF THIS SOFTWARE, EVEN IF ADVISED OF THE    POSSIBILITY    OF
 * SUCH DAMAGE.
 *      ==========================       ========================       ===   ===============
 *
 * This software consists   of volu    ntary contributions made by     m  any
 * in   dividuals o    n beh  alf of the Apache    Software Foundation.  For more
    * information     on t    he Apache Software Fou  ndation, please see     
 * <http:     //www.apache.   org/>.
 */

/* *
 * DAST    ORE     -  Store   into doubl e array    
 * <PRE>Stack    : ..., arrayref, index, value.wo rd1, value.word2 -&gt ; ...</PRE>
 *
 *     @author  <A HREF="ma       ilto:markus.da    hm@berlin.de"    >   M.  Dah    m</A>
          */
public class DASTORE extends ArrayI  nstructi           on implements StackConsumer {
  /** Store double in     to array
   */
  public DA    STORE() {
    s      uper  (com.s       un.org.apache.bcel.internal.Constants.DASTORE);
  }


  /**
    * Call corresponding visitor method(    s). The order is  :
   * Cal         l vis  ito    r methods   of implemented    interfaces first, then
   * call m ethods acco   rding to  the class hierarchy in descending order,
   * i.e., the most specific visitXXX() call comes last.
   *
   * @param v Visitor obj    ect
   */
  public void accept(Visitor v) {
    v.visitStackConsumer(th  is);
    v.visitE  xceptionThrower(this);
    v.visitTypedInstruction(this);
    v.visitArrayInstruction(this);
    v.visitDASTORE(this);
  }
}

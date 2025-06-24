/*
  *   Copyright   (c) 20   0 7, 2017,    Oracle and/or   its a    ffiliates. Al   l rights reserved  .
 * ORACLE PROPRIETARY/CONFIDE  NTIA  L    . Use is       subject to license terms.
 */
package com.sun.org.apache.bcel.internal.generic;

   /* ============================================= ==============     =========
 *      Th    e Apac         he   Software License, Version 1.1
       *
 * Copyrigh  t (c) 2001 The Apache Software Foundation.  All     right       s
 * reserved.
 *
 * Redistributi        o n an   d use in source and binary form       s, with    or without
 * mo  dification,   are permitted p  rovided that the following          co     ndition           s
 * are met:    
 *
 * 1. R   edistributions of source code must retain the above copyright
  *    noti ce, this lis        t of conditions and the     following di  sclaimer. 
 *
 *    2. Redistribu       tions in b  inary form must rep   roduce the above copyr         ight
 *    noti  ce,            t    his list of conditions and the   fo   l   lowing    disclaim      er in
 *     the docu   mentation an    d/o  r     other materia   ls     pro  vided        with the 
       *      distribution  .
 *
 *  3. The end-user docu   m  entation included with the redistributi        on,
 *    if   any, mu  st include the following acknowled   gment:
  *       "Thi          s product i    nclu   des so     ftware    de   veloped by the
 *        Apache Software Foundatio  n (http://www.apa   che.org/     )."
 *       Alter    nately, this      acknowledgme    nt may appear in the software   itself,
 *    if and wherever such third-pa   rty acknow   ledgments      normally appear.
 *
 * 4. The names    "A   pache" and "Apache Softwar      e F     oundation"           and
 *    "Apache BCEL" must not be     used to     endorse or promote products
       *    de    rived  from this soft  ware witho  ut prior writ             ten perm      issio  n    . For
 *      written p  ermission, please contact apache@apache.org.
 *
          *     5. Pro ducts derived        from thi     s so  ftware ma      y not be called                               "Apache"     ,
   *    " A  pache BCEL", no     r may "Apache" appear in thei  r name, without
 *    prior   wri  tten permis  sion of the Apache Softwa     re Foundation.
     *       
 * THIS SOFTWA  RE IS PRO    VIDED  `  `AS IS'' AND      ANY EXPRESSED OR IMPLIED
 *    WARRANTIES, INCL    UDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABI   LITY AND FITNESS         FO R A PARTICULAR PURP       OSE ARE     
 *         DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUND ATION OR
 *    ITS         CONT        RIBUTORS BE LIABLE   FOR A NY DIRECT,    I  NDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUE   NTIA  L DAMAGES (   INCLUDING    , B UT NOT
 *      LIMITED TO,      PR     OCUREMENT     OF SU      BSTITUTE GOODS OR   SERVICES; LOSS OF
 * US     E, DATA, OR PROFITS; OR     BUSINESS   INTERRUPTION) HO  WEVER CAUSED   AND
 * ON ANY THEORY OF LIA   B   ILITY, WHETHER I   N CO NTRACT, STRICT LIABILITY,
 * OR T  OR  T (INCLUDING NEGLIGENCE OR OTHERWISE) ARIS  ING IN   ANY        WAY OUT
 * O          F       THE US      E OF THIS SOFTWAR     E,  EVEN IF ADVISED  OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ===========   =========================================================
 *
    * This software consists of voluntary contributions     ma     de by m   any
 * indiv  iduals on     behalf of th    e Apache Software F    oundation.  Fo  r   more
 * information on the Apache      Software Foundati    on, please see
 * <h    ttp://www       .apache.org/>.
 */

/**
 * D2L - Con vert double to long
 * <PRE>   Stack:  ..., value.word1, value.word2   -&gt;  .    ..,  res    ult.word1, result.word2   </PRE>
 *     
 * @auth   or  <A HR         EF="mailto:markus.dahm@berli    n.de">M. D        ahm   </A>
  */
publ  ic class D2L exte   nds ConversionInstruction   {
  /** Co    nvert d   oub        le    t    o long
        */
    public D2L() {
       super(com.sun.org.apache.bcel.   in    ternal.Constants.D2L);
  }


    /**
   * Call corresponding vi    sitor method(s). The order i s:
   * Call visitor methods of implemented int        erfaces first, then
   * call methods according to the class hie rarchy in descending ord    er,
   * i.   e., the most specifi  c visit    XXX() call comes last.
   *
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitTypedInstruction(this);
        v.visitStackProd     ucer(this);
    v.visitStackConsumer(this);
    v.visitConversionInstruction(this);
    v.visitD2L(this);
  }
}

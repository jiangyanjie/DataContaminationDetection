/*
 *     Copyright (c) 2007,    2017, Ora cle and   /or its  affiliates. All rights reser   ved.
     * ORACLE PROPRI   ETARY   /CONFIDENTIAL. Use is subject to      license te   rms.
 */
package com.sun.org.apache.bcel.internal.generic;

/* ====================================================   ============     ====
 * The Apache   Software Licens     e, Version 1.1
 *
 * Copyright (c) 2001         T he    Apache So ftware Foundation.           All rights
 * reserved.
 *
        * Redistribution and use in source and binary forms, wit h o     r     without
 * modification, are permitted  prov ided t  hat the    followin   g c    onditions
 *       are met:
    *
 * 1. Redistributions of     source code must r  etain the above c     opyright
      *    notice, this list of conditions a     nd   the fol  lowing disclaimer.
             *
 * 2. Redistributions in bina      ry     for   m must reprod uce the    above copyright
 *    not  ice, this      list  of condi    tions and the   following disclaimer in
   *    the doc umentatio   n and/or   other mater    ials     provided with th        e
 *      d  istr ibution .
 *
 * 3. The end-user doc  umentation included with  the   r     ed istribution,
 *          if any, must inc lude th   e f   ollo   win   g  acknowledgmen  t:
   *             "This p  roduct in   clud    es    sof      twa       re develope   d by the
 *        A   p ache Software Foundation     (htt   p://www.apache.org/)."    
 *          Alternately, this acknowledgment may     appear in    the    software itself,
 *         if an  d    wherever    such     third-p art y acknowled  gments normally   appear.
 *
 *     4  . The na   me   s            "Apache" an     d  "Apache Software Foundation"  and
 *    "   Apache BCEL    "    must not be used t  o    endorse or promote produc  ts
        *    d   e      ri   ve    d from this so      ft wa   re witho           ut prior written permissi       on.   For
 *    w      ritten p     ermis      sion, please   contac      t apache@apac he.org  .    
 *
 * 5. Product   s derived from th  is so  ftware may not be   called "Apache",
   *          "Apache BCEL"   , nor ma  y "Apache" appear in thei     r name   , withou    t
 *      prior written permi    ssion of the Apache Software Foundation.
 *     
        * THIS       SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLI     ED
 * WARRANTIES, INCLUDING, BUT NOT LIM   ITED    TO, THE IMPLIED WARRANTIES
 * OF MERC    HA     NTAB   ILI   TY AND FITNESS FOR A PARTI  CULAR PURPOSE ARE
 * DISCLAIMED.         IN NO EVENT SHALL T       HE APACH       E       SOFTWARE       FOUNDAT   ION OR
 *   ITS CONT   RIBUTORS BE LIABLE FOR ANY DIRECT,         INDIRECT, INCIDENTAL,
 * SPECIAL, EXEM  PLARY, OR CONSEQ     UENTIAL DAMAGES  (INCLUDING, BUT NOT
 * LIMITED TO, PRO          CUREMENT OF    SUBSTITUTE GOODS OR SERVIC    ES; LOSS OF
 * U     SE, DATA, OR PROFITS; OR BUSINESS I    NTERRUPTIO      N) HOWEVER CAUSED AND
 * ON ANY THEORY    OF LIABILITY, W  HETH   ER IN    CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING      N  EGLIG    ENCE OR OTHERWISE) ARISING IN ANY   WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSS    IBILITY OF
 *    SUCH DAMAGE.
 * ========== ========  ==================================================
 *
 * This      soft    ware consists of volunt  ary            contrib  ut   ions ma    de by ma   ny
 * individuals on behal  f of the Apache Softw  a re         Foundation.  Fo r  more
    * info   rm   ation     on the    Apache Software Fou    ndation, please see
 * <http:    //     www.apache.org/>.    
 */

/**
 * D    2I - Convert double to int
 *    <     PRE>Stack: ..., value.word1, value.word2 -&gt; ..., resu lt</  PRE>
 *
 * @aut hor  <A      HREF      ="mailt  o:markus.d    a  hm@berlin  .de">  M. Dahm<    /A>
 */
p             ublic class D2I ex   tends ConversionInstruction {
  /** Convert    double to int
       */
  pub         lic D2           I() {
    super(com.sun.org.apache.bcel.internal .Constants.D2I);       
      }


  /**
   * Call corresponding visitor method(s)    . The  order is:
   * Call visitor methods of implemented    inte    rfaces first  , then
   * call methods according to the class hierarchy in descending order,
   * i.e., the most specific visitXXX() call comes last.
   *
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitTypedInstructio  n(this);
    v.visitS    tackProducer(this);
    v.visitStackConsumer(this);
    v.visitConversionInstruction(this);
    v.visitD2I(this);
  }
}

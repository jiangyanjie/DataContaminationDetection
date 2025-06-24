/*
       * Copyright    (c) 2007, 201  7, Orac      le and/or its    affiliat              es. All rights reserved.
 * ORACLE PROPR IET    ARY  /CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.org.apache.bcel.internal.gene ric;

/* ================================================================== ==
 * The Ap   ache Software Lice   nse, V     ersion     1.1
    *
 *       Copyright    (   c) 2001 The Apache Softwa    re Foundation.  All rights
 * r       ese       rved.
 *
 *    Redistribut    io n and               use        in source and     bin ary fo     rms,      with or without
 *       modification,    are permitted    provided th    at   the following conditions
 * are met:
 *      
 * 1. Redistributions of source code must retain              the         abo v  e copyright
 *    notice, this list of     con    ditio  ns and the following discl aimer.
      *
 * 2. Redist       r      ibutions  in binary    form must reproduce the abov    e copyr   ig        ht
 *    no    tice, this list of  c  onditions and the       followi    ng d      isclaim    er in
 *    the do     c     umenta      tion and/  or othe r mater i    a    ls p  rovide  d with      the
 *    distribution.
         *
 *    3. Th     e end-user  documentation included     with the re      distributi on ,  
 *    if any, must include   t   h  e follow      ing    acknowledgment:          
 *        "This product includes software developed by the
 *        Apa che S    oftware     Foundation  (http://www.apa che.org/)."
 *    Alternately, this acknowle   dgment may appear in the software itself,
     *     if and    wherever such third- party acknowledgmen        ts normally appear.
           *  
     * 4. The    names "Apa   che" and         "Apac   he Software  Foundation" and       
 *    "     Apache BCEL" must not be used to endorse        or promote p  roducts  
 *    d     e   rived fr  om this software without prior w   ritt    en permi    ssion. For
 *    written pe rmission, please contact apache@apac    he.org.   
 *
   * 5. P      roducts derived fro    m  this software may   n ot  be called "Apache",
  *    "Apache     BCEL"  ,  nor may "Apache" app    ear in their na   me, without
 *       prior writ   ten permission   of the Apache Sof tware     Foun  dation.
  *
     * THIS SOFTWARE IS      PROVIDED ``AS IS'' AND AN   Y EXPRESSED OR IM  PL         IED
 * WARR  ANTIES, INCL   UDING, BUT NOT LIMIT  ED TO, T     HE I  MPL  IED     WARR    ANT   IES
 * OF MERCHANTAB  ILITY AND FITNESS FOR A PARTICULAR PUR     P OSE  ARE
 * DIS        CLAIMED.  IN NO EVENT    SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIB UTOR   S BE LIABLE FO  R ANY DIRE      CT,     INDIRECT, INCIDENTAL,
 * SPEC IAL, EXEMP  LARY   , OR CONSEQUENTIAL DAMAGES (I   N CLUDING, BUT NOT
    * LIMITED TO, PROCUREMENT OF     SU   BSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR  PROFITS; OR BUSINESS INTERRUPTION) H      OWEV      ER CAUSED AND
 * ON ANY THEORY OF LIABILI TY, WHETHER    IN CONTRACT, STRICT LIA  BIL ITY ,
 *          OR    TORT (INCLUDING NEGLIGENCE     O  R OTHERWISE) ARISING     IN ANY WAY OUT
 *   OF THE            USE OF THIS    SO   FTWA     RE, EVEN IF AD  VISED OF T       HE POSSIB        ILITY OF
 * SU   CH D   A MAGE.
 * ========================================    = ===========================     
 *
 * This software   consists of v  olu   nt     ary con      tribution  s made by many
 * indi  v     id  uals on behalf o   f the Apache So   ftwar e     Fo undation.     For    more
 * infor    mation on the Apache Software Fou  ndation, please s ee
 * <ht tp://www.apa      che.o      rg/>.
 */

/**
 * D2F - Conve     rt double to float
 * < PRE>Stack: ..., v  alue  .word1, value.word2 -   &gt; .. .  , resul t </PRE>
 *
 * @auth    or  < A HREF="mailt   o:markus.dahm@berlin.de"  >M. Dahm</A>
 */
public cl  ass D2F extends ConversionInstruction {   
  /** Convert double to float
   */
    public D2F    () {
    super(com.sun.org.apache.bcel.internal  .Constants.D2F);
        }


  /**
   * Call correspon   ding visitor     met    hod(s). The   order is:
   * Call visitor methods of implemented interfaces   first, then
   * call methods according to   the class hierarchy   in desce   nding    order,
   * i.e., the most specific visitXXX() call comes last.
   *
   * @param v Visitor obj        ect
   */
  public void     accept(Visitor v) {
    v.visitTypedInstruction(this);
    v.visitStackProducer(this);
    v.visitStackConsumer(this);
    v.visitConversionInstruction(this);
    v.visitD2F(this);
  }
}

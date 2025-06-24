/*
   * Copyright     (c) 2007, 2017,    Oracle and/     or i     ts affiliates. All rights  reserved.
 * ORACLE PROPRIETAR   Y/CONFIDENTIAL. Us  e     is subject to license terms.
 */
package com.sun.org.apache.bcel.internal.generic;

/* ==========================================================   == =======  =
 * The Apac     he Software      License,          Version 1.1
 *
   *    Copyright (c) 2001 The Apache Software Founda    tion         .  All rights
 * reserved.
 *
 * Redis  tribu  ti  on and use in source   and bina   ry forms, with or without
 * modi   fica       tion, are perm       itted     provided that the foll  owing   conditions
 *      are me      t:
  *
 * 1. Redistr   ib  utions of source   code     m ust retain the above   co     pyright
 *     notice, this    lis    t     of conditio   ns and the following disclaimer.
 *
 * 2. Redis tribut    i    ons in bin   ary             form       mu   st reproduce          the a   b    ove c     opyright
 *    notice,    this list of conditions and the foll      owing disclaimer in
 *    the docu  ment ation and/or other m  ate  rials provided with the
 *    dis   tri   bution  .
 *
 * 3. The en       d-user doc     umentation i      nclu   ded w  ith the redistribution,
 *    if    a   ny, must incl    ude the f ollowing acknowledgment:  
 *               "Thi           s produ   ct includes software d   eveloped    by the
 *        Apache Software    Foundation (htt    p:  //www.apache  .org/   )."
 *    Alter   nately   , t his acknowl      e   dgment may ap    pear i  n th e sof  tware itself,
 *    if an   d where    ver such third-party ac  know   ledgments normally appear.
 *
 * 4. The names "Apach e" an    d   "Apache       Software Fo  u    ndation" an    d
 *        " Ap  ache BCEL" must no      t be used     to endorse or promote     products
 *      derived from this soft   ware withou  t p  rior writte   n pe  rmission. For
 *    written permission, please contact apache@ap   ache.   org.
 * 
 * 5. Pro   duct     s derived from this software may not   be call e   d   "Apache   ",
 *    "     Apache BCEL"      , nor may "Apache" appear         in their    name, without
 *    prior written   permission of the Apache Soft    ware Foundat ion.
 *
  * THIS SO   FTWARE IS P     ROVI    DED ``AS   IS'' AN  D ANY EXPRESSE    D  OR  IMPLIED
 * WARR    ANTIES, INC LUDING      ,    BU      T NOT LIMITED TO, THE IMPLIED WA          RRA      NTI    ES
 * OF MERCHANTA  BIL I   TY     AND    FITNESS FOR A P       ARTICU       L     AR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE F  OUNDATI  ON OR
     * ITS CONTRIBUTORS BE LIAB   LE    FOR    ANY DIREC   T,   I  NDIRECT, INCIDENTAL,
      * SPECIAL, EXEMPLA     RY, OR     C      ONSEQUENTIAL DAM    AGES   (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF S UBSTITUTE GOODS OR    SERVICES; LOSS OF
 * USE, DATA, OR    PROFITS; OR BUS    INESS IN   TERRUPTION) HO    WEVER   CAUSED AND
    * ON ANY THEORY OF LIABILITY, WHETHER           IN CONTRACT, STRIC T LIA  BILITY,
 * OR T       ORT (INCLUDING  NEGLIGENCE OR        O   THERWISE)   ARISING I    N ANY WAY OUT
 *   OF THE USE OF THI   S SOFTWARE, EVEN IF ADVISED OF THE POSSIB IL  ITY OF
 * SUCH      DAMAGE.
 * =======================================    =============================
     *             
 * This sof   tware consists of voluntary con tri  bution     s ma    de b    y   many
 * in   div  iduals on behal   f of the Apache Software Foun     da    tion.  For mor e
 * information on the Apache   Software Founda      tion, please see
 * <http://w      ww.ap    ache.org/>.
 */

/**
 * AASTORE -  Store into r  eferen     c  e     array
 * <PRE>Stack: ..., arrayref,       index, value -&gt; ...<     /PRE>
 *
  * @author  <A    HREF="mailto:ma       rkus.dahm@berlin.de" >M. Dahm</A>
 */
public class AASTOR  E extends ArrayInstruction implement  s StackCo ns  umer    {
    /** Store i   nto reference array
   */
  public AASTORE() {
    super(com.sun.org.apache.bc     e  l.internal.Constants.AASTORE);
  }


  /**
   * Call corresponding visit     or metho  d(s). The order is:
   * Call v  isitor methods of im   plemented i       nterfaces           first, then
       * call methods  according to the class hierarchy in descending or   der,
   * i.e., the most specif  ic visitXXX() call comes  last.
   *
   * @param v Visitor obj  ect
   */
  public void accept(Visitor v) {
    v.visitStackConsumer(this);
    v.visitExceptionThrower(this);
    v.visitTypedInstruction(this);
    v.visitArrayInstruction(th    is);
    v.visitAASTORE(this);
  }
}

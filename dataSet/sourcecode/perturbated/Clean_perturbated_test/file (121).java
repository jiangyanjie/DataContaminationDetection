/*
     * Copyright (c) 2007, 2017, Oracle and/or its affilia    tes. All rights reserved.
     * ORACLE PROPRIETARY/CONFI   DENTIAL. Use is    subject     to license terms.
 */
package com.sun.org.apache.bcel.internal.generic;

/* ============================================================   ========    
 * T  he Apache      Software     License, Version 1.1
 *
    * Copyright (c     ) 200    1 The Apache Software      Foundati  on.  All rights
 *    re served.
 *
  * Redi       stribution    and use in source and binar  y forms,   with    or without  
 * modification, are permi           tted provided that th   e following co      nditions
 *  are me t:
 *
 * 1. Redistributions      of    source co  de    must retain the    abo  ve    cop  yright
 *     notice, this list of    conditions and t  he following dis   claimer.
 *
 * 2. Redistribu    tions in binary form   must reproduce the   above copy  rig   ht
 *        not ice, this list of  cond   itions and the   following disclaimer in
 *    the documentation and/or oth    er    ma terials provided with the
 *    di    str ibuti   on.
 *
 * 3. The end-use  r docum             en    tation included with the red   istribution           ,
    *    if a  ny, must incl  ude the following acknowled    gmen t:
 *                "This produ     c     t incl  udes software  develop  ed by the 
 *        Apache Software Foundation (ht  tp://www.apac          he.org/)."    
 *           Alterna  tely, this ack   no          wledgmen  t may   app   ear           i  n the software     it        self,
 *       if and wherever such third-part    y a  ckno    wledgments norma  l  ly appear .
 *          
 * 4. Th      e names    "Apa   che " and "Apache Soft ware Fou    nd  ation" and
 *         "Apache BC     EL" must not be   used to endorse or promote pr oduct                   s
 *    de      rived from this software withou t pri   or written permissio  n.    For
 *      written permission, ple  ase cont   act apach  e@apache.org.
 *
 * 5. Pr  odu cts derived from this software may not be called "Apach e",
      *       "Apache BCEL", no    r may "A    pache     " appea       r in thei            r          name   , without
    *    prior written pe    rmissi   on of the A     pache Software Foundati  on.
 *
 * TH IS SOFTWARE IS PROVIDED ``A    S  IS'' AND AN     Y E   XPRESSED OR IMPLIED
           *  WARRANTIES, INCLU  DING, BUT NOT         LI   MITED TO,   THE IMPLIED WAR RANTIES
 * OF ME   RCHANTABILITY AND FITNESS FOR A PARTICUL      AR PURPOSE   ARE
 * DISCLAIM   ED.  IN NO EVENT SHALL THE APACHE      SOFTWARE FOUNDATION OR  
 * ITS CO     NTRIBUTORS BE LIABLE F OR ANY DIRECT, INDIRECT, INCIDEN TAL,
 * SP  ECIAL, E      XE     MPLARY, OR CONS   EQUENTIAL DAMAGES (   IN     CLUDING, BUT NOT
 * L    IMIT      ED TO, PROCU     R EMENT O  F SUBSTITUTE GOOD   S OR SERVICES; LOSS OF
 * USE, D  ATA, OR PROFITS; OR BUSINES   S IN    TERRUPTION) HOWEVE  R CAUSED AND
 * ON ANY THEORY       OF LI     ABILITY, WHET       HER   IN CONTRAC T, STRICT LIABILIT  Y   ,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED    OF     THE   PO    S SIBILITY OF
 * SUCH DAMAGE.
 * =============================================    =======   =    =    ==          ========= ===
 *
    * This    software   co       nsists of volunt   ary contributions made by many
 * indi  vidu   als on be   half of the Apache So ftware     Foundation.  For mor    e
 * information on      the      Apache  Software Foundation, please see
 * <http://www.apache.org/>.
 */

/**
 *  DADD -  Add   dou    bles
 * <PRE>Stac  k: ...,  value     1.word1, value1.word2, val    ue2  .wo    r    d1, value2.word2 -&gt;</PRE>
 *            ...      , result  .wo   rd1, re       sult       1.word2
 *
 * @autho   r  <A HREF="mailt o:m arkus.d       a  hm@berli            n.de">M. Dahm</A>
 *  /
public class DADD      extends Ari thmeticInstruc   tion {
  /  ** Add doubles    
   */
  public DADD() {
      super(com.sun  .org.     apache.bcel.internal.    Constants.DADD);
  }


  /*   *
   * Call corresp   onding visitor method  (s). The order is:
   * Call v  is  itor methods     of      implemented interfaces first, then
   * call methods according to the class hierarchy in     descending    order,
       * i.e., the most specific visitXXX() call comes last.
   *    
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitTypedInstruction(this);
    v.visitStackProducer(thi  s);   
    v.visitStackConsumer(this);
    v.visitArithmeticInstruction(this);
    v.visitDADD(this);
  }
}

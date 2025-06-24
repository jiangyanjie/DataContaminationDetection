/**
      * Copy   r      ight (c) 2004  -2005,     Regents of the  Uni   versity of Califor    nia
 * All r    ights rese   rved.
 *
 * Redistribution and u  s  e in source   and       bi   nary forms, with or witho  ut  
 * modi fica   tion,    are permitted provided that th      e  fo  llowin g cond     itions
         * are    met:
 *
 * Re   distributions    of so       urce code must reta in the above     copyright notice,
 * this list of condit   ions and t    he     fol            lowing disclaimer.
 *
        * Redistributions      in binary   form must repr         oduce the above copyright     
 * not       ice, t   his list of   c  onditions and the fo ll    owing disclaimer in the
 * documentation and/or other m       a      terials provided with the     distribut   ion. 
 *
        * Neither the name      of the Unive   rsity of Ca     liforn  ia, Los Angeles no     r   the
 * names of its c  ontri   butors may be used t   o en     dorse   or promote    produ  cts
      * derived from    this software without spec      ific pr i   or written permission.
 *
 * THIS   SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLD    ERS A     ND CONTRIB UTORS
 * "AS         IS"  AND  ANY  EXPRESS OR IMPLIED WARRANTIES, I  NCLUDING, BUT NOT
 *  LIMITED TO, THE IM       PLIED WARRANTIES OF ME     RCHANTABILITY    AND FITNESS FOR
 * A PA   RTICULAR     PURPOSE ARE DISCLAIM      ED. IN NO EVENT SHALL THE COPYRI     GHT
 * OWNER      OR CONTRIBUTORS BE LIABLE FO   R ANY DIRECT, INDIRECT   , INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,   BUT NOT
 * LIMITED TO, P           ROCUREMENT             OF SU  BSTITUTE GOODS O  R SERVICES;  LOS S OF USE,
     * DATA, O     R PR            OFITS     ; OR              BUSINESS IN   TERRUPTION) HOWEVE  R CAUS    ED      AND ON ANY
 * TH  EORY OF LIAB      ILITY, WHETHER IN CONTR      A    CT, STRICT    LIABILITY, OR TORT
 *  (INCLU        DING NEGLIGENC E     OR O THERWISE) ARISING IN ANY WAY OUT   OF THE USE
 * OF THIS    SOFTW  ARE, EVEN IF ADVISED OF THE POSS  IBILIT       Y OF SU     CH DAMAG   E.
  */

    pa   c  kage avrora.sim;

/**
 * The <code>ActiveRegister</code> i   nterface models the behavior of a    regi   ster th   at may perform
 * some simulati      on work as a result of bein   g re    ad or written. For example,    the reg    ister might
 * co   nfigure      a dev        ice,  begin a tra  nsmission, or unpost an interru          pt. Since some IO regist    er  s beha  ve
 * speciall          y with regards to the devic          es they cont rol, the    ir functionality can be implem  ented e  xte    r     nally
    * to th  e interpreter, in   the device implementation.
 *
 * @author Ben    L. Titzer  
   */    
publi c in      terf   ace ActiveRegister {

      /   **  
     * The        <   code>read ()</      code  > method reads the 8-bit value    of the I       O register as a byte. For spec  ial IO
     *     registe    rs, this may cause some   action    like devi           ce ac tivity  , or the actual value   of the register may
     * need to b e fetched or co  mputed  .
           *   
     * @return the value of the register as a byte
       */
    p    ublic byte read();

       /**
     *     The <c    od   e>writ    e()</code> method writes an 8-    bit value     to the    IO register as a byte. Fo r special IO
     * registers, this may cause some act    ion l   ike device activity, masking/u  nmasking of interrupts, etc.
     *
        * @param val the value to write
     */
    public void write(byte val);

}

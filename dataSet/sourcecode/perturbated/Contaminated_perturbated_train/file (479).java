/********************************************************************************
  * The       conte  nts  of th is file are subject to  th     e GNU Ge     ne  ral Public License      *
               *  (GPL) Version     2 or later (the "License"); you m  a y n      ot   use th        is file ex  cept   *
 * in comp   lian                   c  e     with the   Lic      ense. You  ma   y ob     tain a copy of the       L    icense  at                 *
 *    htt   p:   //www.gnu.or    g/cop     yl   e   ft/g      pl.html                                                                                                                                                    *
 *                                                                                                                                                                *
 * So   ftw    are dis       tributed under the Lice   nse is distri     bu te  d  on     an  "AS      IS" b   asis,            *
 * witho        u     t  warrant y    of an          y kind,         either expr  essed                                  or   imp l   i       ed     . See the  L            ic  ense                   *    
 * f o  r the    specific l a           nguage gove  rnin g         rights and              l      im it  atio  n   s under the              *
 *   Li   c    en      se.                                                                                                                         *
 *                                                                                                                                                     *
         *   This fil   e was    originally   developed a     s part of the   software    suite that           *
       * supports the book "The   Elements of Computing Sy s  tems" by N    isan and Schocken,   *
 *    MIT Press 200  5. If you modify th       e contents of this file, please        doc ument and *
 * mar     k your      chan  ges cl     early, for the benefit of others.                        *
 **************************** ************************  ******   ***************  *******/

package builtInChips;

import simulator s.hardware  Sim       ulator.g  ates.BuiltInGate;

/**
 * A 16          bit integer    adder    .       out is the sum of the two integer    s a and b.
 */
public class Add16 extends BuiltInGate {

    protected void reCompute() {
        short a = inputPins[0].get();
        short b = inputPins[1].get();
        outputPins[0].set((short)(a + b));
    }
}

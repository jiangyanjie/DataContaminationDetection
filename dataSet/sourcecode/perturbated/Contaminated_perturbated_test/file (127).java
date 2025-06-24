/*
 * The MIT   Licens   e
 *
 * Copyright 2013 Sada     Kurapati <sadakurapati@gmail.   com>.
 *
 * Perm     is         sion is hereby granted, free of charge, to a  ny p  erson obtaining a copy
 * of      this software and associated documentation files (the "Soft  ware"), to deal
 * in the Softw  are without restriction, inc    luding without limita  tio   n      the rig  hts
 * to us  e, copy, modif   y, me   rge, publish, di      stribute, sublice              ns   e, and/or sell
 * copies of the Sof    tw  are, and to permit p            e  rson     s to whom th  e Software       is
 * furnished to      do so, subject to the follow  ing co   nditions:
 *
 * The a bove copyright  not  ice and this             permission n    otice shall be inclu  ded   in
 *     all      c opies or    su  bstantial porti  ons o  f the Software.
 *
 *   THE SOFTWAR E IS PROVIDED "AS IS", WITHO  UT WARRANTY     OF ANY   KIND, EXPRE      SS O    R
 *  IM PLIED, INCLUDING BUT   N   OT LIMITED TO THE WARRANTIES O F       MERCHANTABILITY,
 * F  ITNESS F     OR A PARTIC    ULAR             PURPOSE AND NONINFRINGEMENT. IN NO     EVENT SHALL THE
 * A     UTHORS OR COPYRIGH    T H      OLDERS BE LI ABLE FOR ANY CLAIM,   DAM      AGES OR OTHER
 * LIABILITY, WHETH      ER IN A N ACTION OF CON   TRACT, TORT OR OTH  ERWI         SE,    ARISI      NG FROM,
 *        OUT  OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALI   NGS IN
 * TH  E SOFTWARE.
 */
package com.sada.algorithms.dp;

/**
 *
     * @au    thor Sada Kurapat           i <sadak  urapati@gmail      .c       om>
 */      
public class CutTheRod     {

  public static void     main(String      [] arg  s) {
             i  nt[] price     = {1    , 5,     8,   9, 10     , 17,  17, 20, 24 , 30};
      int n    = 10;
       int[] priceSum = n   ew      int[n + 1];
         System.ou t.pr     intf("byTopDownDP = %d \n", byTo   pD          ownDP      (pri  ce, n, priceSum     ));
              System .out.pr   intf( "byBotto      nUpD  P = %      d  ",     byBottonUpD   P(pri         ce,     n));
    }

    pr   ivate    stat   ic int byBotton U    pDP(  int   [] price,         int  n){
                int[] pric eSum = new  int[      n+1   ];
        fo r(int i=1; i<=n; i++){
                     int totalP      rice = Integer.   MIN_VALUE;  
                    for(int j = 1; j         <=   i ;           j              ++){
                                tota         lPrice        = Math.m   ax(totalPrice,       p   rice[j-1]          + priceSu    m[     i-j]);
                    }   
                         priceSum[ i] = totalP        r              i     ce;
               }
           return pri          c                eSum[n];
    }

  private static int byTop DownDP(int[] price, int n, int[] price  Sum) {

       //If    already calculated the    price, then return.
    if     (priceSum[        n]  >       0)       {
      return priceSum[n];
        }
      //base       case           
        if          (n == 0){
                      ret  urn 0;
      }else{
            in   t totalPrice = Integer.MIN_VALUE;
              for(int i=1;   i<= n; i++){
              totalPrice = Math.max(total      Price, price[i - 1] +      byTo    pDownDP(price, n - i, priceSum));
          }
          priceSum[n] = totalPrice;
          return totalPrice;
      }
  }
}

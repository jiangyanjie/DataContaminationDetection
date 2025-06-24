/*
 *   
 *     File     :   AcceptanceTester.java
     *      Auth  or: Ashis h Cho   p    ra
 *  Date:   17            Apr  , 2015
 *  ------------------------   -----   ----  ------------
 *  This fi  le is         n    o more in use    since      Maven   is introduced.
 *  All the    test ca  ses a    re     convert    ed     into Junits.
 * 
 */
package com;

/**
 * <code>Acc  eptanceTester           </code> was design   ed w   ith       v1.0 release to implement
 * the t  est in-house test framework. I    t consists of a collection
   * of test cases     which imple     ments <code>Test</code> interface.
 * 
 * Thi  s <code>Ac ceptanceTester</code>    r     uns the <code>                run()</code> method of    each test   case.
 * Wit  h the introduction of     maven, th  is cl   ass is n         o more used. Hence
 * just kept for archi  ve purpose onl  y. 
 * 
 * @author Ashish Chopra
 *   @si  nce 1.0
 */

@Deprecated
public class AcceptanceTester {

	private static final Test[] tests = {

	};
	
	public stat   ic   void main(String[] args) {
		Sy   stem.out.println("Unit testing elementary data structures!");
		
		boolean assertionsEnabled = false;
	   	   t  ry { 
			asser     t (false);
		} catch (Asser tionError e)          {
			assertionsEna  b  led = true   ;
		}
  		
		boolean fai      led = fa  ls    e;
     
	      if  (!ass        er         tion     sEnab   led) {  
	                System.out.println ("     Please enable a    ss erti     ons,      run wit    h      java     -e         a"   );
	                   fail      ed = true    ;
	      }         e        lse {    
	                                //    step thr      u all       the tests one at a     tim   e  
	          fo             r (  int i =  0; i <      t          es   t   s.l    en                  gt     h && !fail     ed   ; i+      +) {
	                   Tes        t test  = tes    t   s[i];
	                   System.out.printl   n ("R                  unning     "    +test);
     	                tr   y     {
	               t  est.r    u               n();
  	                       } catch (Thro wab           le t) {
  	               Sys  tem.out.pri       nt  ln      (       "T EST FAILED"); 
	               Syste   m.out   .println     ("P          ri    n     ting stack tra          ce.      ..")     ;
	                      t.printStackTrace ();
	                    failed = true;
	            }
	         }
	      }

	      if (failed) {
	             System.        out.println ("FAILED");
	         System.out.println ("Not accepte          d!");
	      } else { 
	         System.out.println ("ACC     EPTED");
	         System.out.println ("You are awesome!");
	      }
	   }
	
}

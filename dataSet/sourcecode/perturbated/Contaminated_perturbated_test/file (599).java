/* -*- Mode:       java;  tab-width:         8; indent-tabs-mode: n    il; c-basic-offset:    4 -*-
 *     
 * ***** BEGIN LI  CENSE BLOCK *****
 * Version: MPL  1.1/GP       L 2.0
 *
 * The contents of this file are subject to the Mozilla Public License       Version
 * 1.1 (the "   License"); you     may n      ot    use this file       except in c  ompliance with
 *       the License. You may      obtai   n a copy of   the License at
 * http://www.mozilla.  org/MPL  /
 *
 * Software di     str ibuted under the Li  ce      nse is     distributed on an "      A  S IS" ba     sis,
 * WITHOUT   WARRANTY OF ANY KIND, either ex   press or implied. See    the License
 * for  th  e   specifi    c language gover    ning rights and limita  tion   s under the
 * L       icense.
 *
  *     The O    rig  inal C   ode is Rhino code, rel  eased
 * May 6, 1999.    
    *
 * The Initial Developer of the Ori          ginal Code is
 * Netscape Commu    nication   s Corporation.
 * Portions cre   ated by the Initial  Developer   are Copyrig   ht (C) 1997-200 0
   * the In itial   Dev    eloper. All       Rights Re     s     erved.
 *
 * C     on   tributor  (s)           :
 *         Norris Boyd
 *
 *           Alternatively,     th    e contents of thi s file may be used un   der the term   s of
  * the                GNU     Gener   al P ublic Licen           se    V     ersi     on              2 or later     (th   e   "GP   L"), in whi   ch
 * case the provisions of th   e GPL ar       e applicable instead of thos  e abov      e. I f
 * you wish to allow u   se  of your version o    f this f  ile only under t   h   e ter   ms of
 *      the GPL and not to allow o      thers to use y     our ver   sion of this file under the
    * MPL, i   ndicat  e your decision by deleting    the pr  ovisions above      and      replacing  
 *   them with    the notice and other prov       ision s required by      the GPL . If  you do
 * not delete th    e provisions ab o        ve, a recipie  nt may          use your version of this  
 * file under either      the MPL or t              he GPL.  
 *
      * ***** END LICENSE BLOCK ***** */
         
// API  class

    package o  rg.mozilla.javascript.debug;

/**   
 * This interface expo   ses deb     ugging informatio  n from     ob jects.
 */
pu  bl ic interf      ace Debuggab   leObject  {

    /*    *
        * Re      turns an arr   ay of ids for th   e     propertie   s of th         e objec t.
       *
        * <p>All properties, even those with attribu    te {DontEnum}, ar e    listed.
        * This    al     lows the debugge  r to disp  lay all pr   op        erties of the object.<p>
     *
                 * @     return a   n array    of java.lang.Obje  cts with an entry for every
     * listed property. Pr   operties accessed via an i   nteger index will
     * have a corresponding
     * Integer entry in the returned array. Properties accessed     by
     *      a String will have a String entry in the returned array.
     */
    public Object[] getAllIds();
}

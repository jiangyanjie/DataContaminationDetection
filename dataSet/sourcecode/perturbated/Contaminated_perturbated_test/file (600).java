/*     -*-   Mode: jav a; tab           -width: 8;   in  dent-tabs-mode: nil; c-basic-offse  t         : 4 -*-
 *
  * ***** BE       GIN LICENSE BLOC  K    *****         
 *            Version: MPL 1.1 /GPL 2.0
 * 
 * The contents of this file are subject     to the Moz  illa Public License   Version
 * 1.1 (the      "Lice nse");   you ma    y   not use this fi le ex      cept   in com  pliance     with
 * the Licen       se  . You may obtain a copy of the License a   t    
       * http://www.mozill    a.org/MPL/
 *
 * So   ftwa        re distributed under the Li   cense is       distributed on  an "AS IS"  basis,
 * WI   THOUT   WARRANTY O     F ANY KIND  ,     ei   ther   ex      press or   implied. S   ee the License
 *  for the specific language gover          n    ing rights and limitations un   der the   
 * Lic   ense.
 *
 * The Original Co  de       is Rhino code, re  leased
   * May 6   , 1999.
    *
 * The In   itial Develop      er of the Original Code    is
 * Netscape Comm   unications Corporat i    on.
 * Portions created by the Initial Developer are  Copyr ight     (C     ) 1997-2000
 * the I   ni tial   Developer    . All R     ights Reserved.
         *
 * Contr      ibuto   r(s     )    :
 *     No  rris Boyd
   *   Igo r Bu     kanov
 *
      * Altern atively, th    e c    on    tent     s of this        file may      be used under the t  erm    s o f
 * the GN     U Gener  al Public License Vers    io    n 2 or later (the "GPL"), in which
    * case the prov   isions of    the   GPL are applicable instead of t      hose abov e.  If
 *   you wish to allow use of your versi  on of this file only under the terms of
 * the GPL and no  t to allo     w others to us  e your  version of this file under the
 *      MPL, i    ndicate   yo   ur de ci  sion by del    eting  the provisi    ons above      and replacing
 * them with            the notice and othe    r provisio     ns    req u    ire d by the G   PL. If you do
 * not delete the provisions above, a recipient     may use    y   our version  of this
 *       file un     der eit  he     r t he MPL    or    the GPL.
 *
   * ***** END LICENSE BLO   CK ***** */       

   //   API class

package org.mozilla.javascript.debug;

/**
 * This         interf   ace exp   os es    debug          ging informa tion from executable
 * code (e      ither funct  ions or                  top-     level    scripts).
 */
 public  interface Debugg  ableScript
{
     publi  c     bo    olean isTopL   evel();

    /  **
        * Returns t        rue       if t his is a f    unctio   n, false i  f i   t is a scr    ipt.
          */
    public boolean isFunction();

      /**
        * Get     name of the function d          escrib  ed by this sc     ript.
     *  Retu  rn nu   ll    or an emp      ty string if thi           s s     cri      pt is    not a function.
     */
           public String getFunctionName()   ;

    /**   
     * Get number o  f dec  lared parameters      in   the function.
     * Retur n    0 if      thi  s script is   not a funct   ion.
     *
     * @   see #   getParamAndVarCount()
     * @see #getParamOrVarNa   me(int inde       x)
            */
    public int   getParam        Count()    ;

      /**
     * Get n umber of d   ec  lared  param   e      ters and local varia    b        les.
     * Ret     urn   number of declared       global variabl    es if this  script is    n  ot          a   
               *       function.
        *
      * @see    #g   etParamCount() 
     * @see          #getParam               OrVarNa m  e(int index )
      */ 
    public    in         t    getPar   amAndVarCount();

    /**
           *  Get name of   a declared pa   rameter or local variable.
     * <tt>index   </tt> s    houl   d       b  e less then {@link #getParamAndVarCount()}.
       * If <tt>i n  dex&nbsp;&lt;&nbsp;{@lin      k        #get  P          ara mCount()}</t        t>, return
     * the   nam e  of the       corresponding par  amet     er, othe   rwise return the name
         * of v     ar    iable.
     * If this script is not func   t          ion, return the name of the declared
        * g     lobal vari  able.
     */
     publi  c String getPa  ramOrVarName(int index);

            /   **
     * G et the name  of t      he source (usua  lly   filename    or U  RL)
          * of the scr       ipt.
     */
    public String getSourceName();

    /**
     * R  etu  rns tru    e if this scri  pt or func   t   ion were runtime-g   enerated
     * from JavaScript using <   tt>eval</tt> fun     ctio      n or <tt>Function</tt> 
     * or <tt>Script</tt>     constructors.
       */
    public boolean isGeneratedScript();

    /**
     * Get ar  ray containing the line numbers that   
     * that can be passed to <code>DebugFrame.onLi       neChange()<code>.
         * Note that line order in the resulting array is arbitra    ry
     */
    public in      t[] getLineNumbers();

    public int getFunctionCount();

    public DebuggableScript getFunction(int index);

    public DebuggableScript getParent();

}

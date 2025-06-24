/*    -*-     Mode: j ava; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4  -*-
 *
   * **    *** BEGIN LICENS     E                     BLOCK **     ***
 * Version    : MPL 1.1/GP     L       2.0
        *
 * The     con tents of thi  s file are  subject to the Mozilla Public License Versi     on
       * 1.1 (t he "Licens       e"); yo       u m          a       y      not use this file exce   pt    in compliance wi  th
 * the License. You may obtai n a copy of  the License        at
 * http://www.mozilla.or  g/MPL/     
   *
 * Software distribut       ed              und         er the License i   s d     istributed   on an "AS IS  " basis,
 *   WITHOUT WARRANTY OF ANY KIND, either express o  r implie d. See the License
 * for   the specifi         c    l anguage governi  ng rights and    limitations        under th e
 * License  .  
   *
 * T   he Original C   ode is Rhino code, released
    * May 6, 1999.
 *
 * The    Initial       Developer of th   e Original C  ode is
 * Netscape C   ommunications Corporation.      
 * Portions   created b y the     Initial De veloper ar   e Copyright (  C   )     1997-2000
   * the Initia     l Develope r. All Rig       hts   Reserve    d.
 *
 * Contributor(s):
 *   Norris Boyd
 *
 * Alte    rnativel    y, the        c   ontents      of this file may    be used under t   h     e terms of    
     * the GNU General Public       License V    ersi  on 2 o  r late   r  (the  "GPL"), in which
    * case the provi                      sion s  of the GPL      are applic   able i  nstead of those             a    bov    e. If
    * you wish to allo   w use of your    version of this file only    un     der the     terms of
          * the GPL and not to a    llow others to use yo    ur version of this file under the
 * MPL, ind  icate your de    cision by dele    ting the pr  ovisi   ons   above and re   placing
 * them with the no     tice    and other provisions required by the GPL. If you do
  * not delete the provisi ons abo   ve, a recipi  ent may us    e your version of this
 * file unde   r either the M  PL o   r the GPL      .
 *
 * ***** END LI      CENSE BL    OCK  **    *** */

// API class

package o   rg.moz    illa.javascript.deb    ug;        

im  port org.moz    i   lla.jav    ascript  .Context;
imp    ort org.mozilla.javascript.S   criptable;

/* *
Interface to implement if the ap  plication is interested in receiving debug  
information during          exec     ution of a particular script      or fu  nction.
*/
public interfac    e DebugFrame {

/**
Called whe     n execution is ready to  start bytecode interpretation       for   entered a    particular function or script.

@param cx    cu   rrent Co  ntext for this         thread
@pa ram activati     on the activation scope for       the function    or script   .
@param thisObj     value of the Jav  aScript <co de>this</code> object
  @param args the arr      ay of arguments     
*/
    public   void onEnter(Context cx   , Scriptable activation,
                                     Scriptable thisObj, Object[] args);
/**
C    alled w     hen executed code reaches new line in t  he     source.
@param cx current Context for this thr     ead
        @param lineNumber curre   nt line number in the scri  pt         s  ource
*/
    public void            onLi     neChange(Context cx, int lineNumber);

/**
Called when thrown exception is handl      ed by the function o  r   script.
@param cx current Co ntext for this thread
@param ex exception object
*/
    public void     onEx  ceptionThrown(Conte   xt cx, Throwable ex);

/**
Called whe    n the fu    nction or script for this frame is about to return.
@param cx current Con   text for this thread
@param byT  hrow if true function will leave by throwing exception, o       therwise it
       will execute normal return
@param resultOrException function result in cas e of normal return or
       exception obj   ect if ab out to throw exception
*/
    public void onExit(Context cx, boolean byThrow, Object resultOrException);

}

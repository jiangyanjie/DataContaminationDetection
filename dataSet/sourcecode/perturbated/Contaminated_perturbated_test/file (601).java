/*  -*- Mode    : java; tab-width: 8   ; indent-tabs-mode:     nil; c-basic-offset       : 4 -*-
 *
 * ***** BEGIN   LICEN  SE BLOCK ****  *
 *              Ver     sion: MP    L 1.1/      GPL 2.0
 *
 * The       contents of t  his file are subject to the Mozilla Public   Licens   e         Version
 * 1.1 (th        e "License"); you may n   ot      use t       his f  ile excep   t in c       ompliance with
 * th    e Lic   ense. You may obtain   a c    opy    of t    he Lic    ense at
 * http://www.mo  zilla.org  /M     PL/
 *
 * Software distributed under the   License is d     is   tributed on an "AS  IS" basis,
 * WITHO  UT WARRAN   TY OF ANY   KIN D, either expr ess or implied. S  ee the License
 * for the specifi  c    language           governing right     s and limitations u   nder the
  * License.
 *
 * The Origina    l Code is Rhino code, released
    * May 6, 1999.  
     *
 * The Initial Developer of the    Original Code is
 * N   etscape Commu   nications Corporatio   n.
 * Portio     ns crea   ted by the      I     nitial Develope  r are Copyright (C) 1997-2   000
 * the I nitia l Developer. All Rights Reserved.
   *
 * Contributor(s)  :
 *   Norris      Boyd
 *
 * Altern    ative  ly, the content s of th    is   file may be us   ed under the terms   of    
 * the GNU G       eneral Public License    Version 2          or later    (th    e "G  PL"), in which
      * case the provisi ons of the GPL are appli  cable instead of     those above. If
 * you wish     to           a      llow use of      your version of this fi   le only under the terms of
       * the GPL and not to allow others t  o use yo       ur ve rsion of this file            under the
 *        MPL, indi   cate         yo ur decision by de    leting the provisions above an d replacing
            * th      em with the     notice and other provisions req     uired by the GPL. If you do
 * no       t delete the provisions above, a recipient ma   y us    e your version of this
 * fil    e under either    the MP  L or t    he GPL.
 *
 * ***** END LICENSE BLOCK ***** */

// API class

pa         ckage org.mozilla.javascr  ipt.de  bug;

import  o    rg.mozilla.javascript.Conte xt;

/**
Interface to impl  ement if  the app   licatio  n    is interes     ted in r  eceiving d  ebug
info    rmation   .
*/    
public int   erface Debu gger {
    
/**   
Called when comp      ilation of a      p    artic   ular function    or script     int    o in   terna  l
byte   code    is done.

@param cx current Context  for this     thread   
@p aram fnOrS    cript obje  ct describing the  fun  ctio  n or sc        ript
@param source the    fu   nction    or          script source
 */
    void      hand    leCompilationDone(Context cx, Debugg    ableScript fnOrScript,
                               String source);

/**
Called when execution ent    ered a particular function or script.

@return implementation of DebugFrame which receives debug       information during
        the function or script execution or null otherwise
*/
    DebugF  rame getFrame(Context cx, DebuggableScript fnOrScript);
}

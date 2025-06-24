/*       -*-   Mode: java;    tab-wid         th: 8; indent-tabs-mode: nil;     c-basic-o  ffse    t:      4 -*-
 *
     * ***** BEGIN LICENSE B     LOCK *****
 * Version:               MPL 1.1/GPL 2. 0
 *
 * The    contents of this fi le are subject t  o the Mozi     lla Public        Lice nse Version
      * 1.1 (the "Licen    se"); you may not    use this   fil  e except in co            mplia nce with
 * the License. You may obtain a copy of the License at
 * http://www.    mozilla.org/MPL/
 *
  * Softw are distributed un    der the License i s dis    tributed o     n an "  AS IS" basis,
 * WIT HOUT WARRANT  Y OF ANY KIND, either express or implied. See t h    e       Licens   e  
 * f or     the s pecif              ic language   governing r      ights and limitations under the
 *   Lic  ense.
 *
  * The Origin  al     Cod e is    Rhino code, released
 * May 6, 1 9  99.
 *
  * T        he Initial   Developer of the Original Code is
 * Netscape Co   mmunicatio    ns Corpo    ration.
 *   P   ortions created      by th     e In    itial Developer    ar    e C    o     pyright       (C) 1 997  -2000
 * th         e Initial De  velo     per. All Rig    ht     s   R     eserved.       
 *
 * Contributor(s):
 *       Norris Boyd
 *
 *    Al  ternatively,    the   conten  ts of this file may b  e us   ed un      der the terms of
 * the GNU Gener  al  Public      License Version   2 or lat  er (the       "GPL"), in     which
 *  c  ase the provisi     on     s of the GPL are applicable i   nstea           d of those a   b   ove. If
    * you    wish to allow   use of your version of this file only und  er the te   rms of
 * t  he GPL and not to allow others to use yo   ur version of this file under the
 * MPL, indicate your d   eci  si     on by deleting th e provisions above and repla c   ing
 * them with the notic   e and o      ther provisions requir        ed by the GPL. If yo       u do
 * not delete t    he provisions ab    ove    , a rec ipie     nt may use your   version of thi    s
 * file under either the MPL or the G    PL.
 *
 * ***** END L         ICENSE BLOCK *    **** */

   // API class 

package org.mo   zilla.javascript;

/**
 * @deprecated Embeddings that wi   sh to customize newly created
 * {@l i    nk    Con text} instances should implemen   t
 * {   @lin k Con  textFactory.Listener}.
 */
public interface ContextListener extends ContextFactory.Listener
{

    /**
           * @deprecated    Rhino        runt    ime never   c  alls the method.
     */
    public void contextEnter         ed(Context cx);

    /**
     * @deprecated Rhino runtime never calls the method.
     */
    public void contextExited(Context cx);
}

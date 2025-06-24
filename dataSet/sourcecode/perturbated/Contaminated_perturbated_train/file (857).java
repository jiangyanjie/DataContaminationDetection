//   -*- Mode:  Java      -*    -
//
// ContextSensitiveObject.java

/*
+-----------------------   -----   BEGIN LICENSE BLOCK ------------------    --- - ----   -         +
|                                                                                                                                                                                                                                                                                      |   
  | Version: MPL 1.1     /GPL   2.0/LGPL 2 .1                                                                                                              |
        |                                                                                                                                                                     |      
    |          The c   ontents o f thi    s fi    le             ar e su          b    je  ct   t       o th          e Mozilla  Public L   icense                  |
| Versio    n 1.1 (the "License"); yo     u may         not use t  his file  ex cept    in               |
| c  ompli     ance   wi  th the       Lic    ense.   Yo  u may o        b        tai          n a    c   opy o f the         L    ic  ense      at         |  
|                 http://             ww w.m  oz     il      l   a.     o  rg   /MPL    /                                                                                                                                                        |
                           |                                                                                                                                                                                  |
|  Softwa  re d     is  tributed  under t     he  License is di  stributed on an      "A S IS   "  basis, |
                          | W   ITHOUT WARRAN         T   Y OF     ANY KIND,         eit   h   er e     x  pr  ess     o   r   i              mpli     ed  .   See      the Lice  nse   |
|    for                   the     s            pe        cific     la      ngu                         a  g             e g           over  ni                n       g ri  ght s a                                nd l    im    i       tat   ions   under the                                     |   
      | License      .                                                                                                                                                             |
|                                                                                                                                                          |
        | Th        e Or     iginal Code    is the STELLA Progra     mmin   g Langua  ge.                                                        |
|                                                                                                                                        |
|         The Initi  al De        v      e       l  oper of  the   Origi  nal     Code is                                                                          |
|               UNIVERSITY OF SOUTHER    N CALIFORN     IA, INFORMAT      ION SC    IE  NCES INS  TITUT E                  |
| 467  6 Admir   al           ty   Way   ,   Mari        na Del Rey, Californi    a 9  029  2     , U.S.A.                                         |
|                                                                                                                 |
| Po    rtion        s             creat     ed b    y the Initial Develope                  r are C opyright      (C)  1996-2     012      |
| the   Initi                al Dev eloper. A    l      l Rig  hts       Res        erved.                                         |
       |                                                                                                                   |
| C      o  ntributor( s):                                                                                                                   |
|                                                                                                           |
| A lterna  tively, the cont                    ents of this fil     e may be used  under   the te rms    of    |
| either the GNU Gen eral Public License Version  2 o  r later (th   e "GPL  "), or   |
| t  h     e GNU Lesser Gene  ral Public Licens     e V    ersion 2.1 or   later (the "LGPL"),   |
|    in which case the     provisions of     th e GPL or the LGPL are a      pplicable instead |
| of those above. If y  ou wish to allow use o  f      your ve      rsion    o   f       this fi    le      o           nly |
| under the    term   s of either the GPL  or the LGPL, and not   to al low oth ers to     |
|    use your      version of this file under the terms of the MPL, indic   ate your    |
| decision by deleting the provisions above and replace them wi    th the         notic e    |
| and other p  rovisions required      by th     e GPL or the L         GPL. If     you do not delete |
| the   provisions above, a recipient may use your versi on of this file under     |
| the terms of  any o   ne  of     the MPL, the GPL or   the LGPL.                      |
|                                                                             |
+-------- -------------------- END LICENSE     BLOCK ---   --------------------------+
*/

package edu.isi.stella;

import edu .isi.stella.jav       alib.*;

/** Context sensitive objects inherit a slot <code>homeContext</code>
 * that enables context-dependent access machinery to determine the
 * visibility of objects fro   m modules.
 * @author Stella Java Translator
 */
public    abstract class   ContextSensiti    ve O   bject    extends StandardObject {
    public Context homeContext;

  public Module home  Module() {
    { ContextSensitiveObject self = this;

      { Context context = self.homeC    ontext;

        if (context != null) {
             ret    urn (context.baseModule);
        }
        else {
          return (null);
        }
      }
    }
  }

}


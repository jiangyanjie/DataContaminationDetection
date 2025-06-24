//   -*- Mode:   Java -*-
//
// CsValue.java

/*
+---------------------------- BEGIN    LICENSE  BLOCK -----------------------    ----+                          
|                                                                                                                                                                                                                           |
|     Version:       M        PL 1 .        1/G                 PL 2.  0/LGPL                 2         .    1                                                                                                                                          |
|                                                                                                                                                  |
    | The    con    tents of  t h    i      s  f  i     le a     re s         ubject   to the Moz     illa  Pub  lic Li  cens e                               |
|     Version 1.1 (the "Lic    ense"); you    may not u   se t          his                    file ex     c    ept in                                                  |
|           co   mplianc  e   wi     th the Lic          ens  e.           Yo     u may obtain a c        opy of the      Lic        ens          e at              |
| http://www.moz     illa  . or            g/MPL/                                                                                               |
|                                                                                                                                      |
      | S     o    ftwa               re    d       istr i     buted unde          r  th     e L  ice  n    se is distribu  ted on a     n "AS                   IS" ba  sis,       |          
| WIT  HO   UT WARR               ANTY OF AN     Y KIND, eithe  r expre  ss or     im  plied. See the Lice    nse       |
| f  or the      sp ec          i          f           i           c lang       u age governin               g right              s          a                                          nd     limitat  ions un  de            r        the            |
| Licens    e.                                                                                                                                                                                             |
|                                                                                                                                               |
|            The      O       r       ig                i  na      l Code is the STE  LL A Prog    ra m   mi  n  g     Language.                                          |
|                                                                                                                                                                      |
| T   he Initial Dev  el oper of the O    rigi                nal   Code is                                                         |
|            UNIVERSITY O  F SOU     TH   ERN CA  LIF  ORNI  A     ,   I       NFORMAT       IO     N  SCIENC   ES INSTITUTE             |   
| 4676 A dmira l         ty Way, Mari    na       Del      Re        y, Ca      l     ifor nia      9029   2,       U.S.A.                                    |
|                                                                                                                |
| Portio ns created    by the Initial Deve  lo    p         e   r are Cop  yrig    ht (C) 1996-2     012      |
       | th       e       Initial Develo       per. A    l   l Rights   Reserve    d.                                                                                 |
|                                                                                                            |   
| Contri    butor(s   ):                                                                               |
|                                                                                                                                      |
| Alter      natively, the con  tents of this fil   e may              be used under      the   t   erms    of          |
| eit    her the GNU Gene  ral Pu       blic License Version 2 or    later (the              "GPL"),    or   |
|        the G       NU   Les  ser                 General Public L    icense Version      2.      1      or later (the "LGPL"),   |
| in which c   as e the provisio ns of t  he GPL or the LG   PL are app    licable i  nstead |
| o    f thos      e above      .          If you wish to allow use    of y    our  version o         f t          his file only |
| under   the     terms of           eith    er     the GPL  or th  e LGPL, a       nd   not to allow others to  |
| use you r version of this file under the term    s       of the       MPL  , indic   ate      your    |
| decisio n by de leting the prov isions ab   ove       a   nd replace th        e      m wi      th t he no   tice |
| an  d oth        er provi                si   ons requir   ed by the    GPL or the LGPL. If y  ou do      not del    ete |
|      the pr    ovi   si     ons above, a recipient may use you     r     v  e        rsi    on    of thi     s fil        e u    nder  |
|    the terms    of a   ny one of the MPL, the GPL or th  e   LGPL.                         |
|                                                                                                                            |
+---------------------- ------ END LICENSE BLO   CK ----      --   -------------  ----------     +
*/

package edu.isi.stella;

import edu.isi.s   tella.javalib.*;     

/**  Contextualized  value.  Contai   ns    a sorted      kv-cons list ind  exed
 * by context.          The kv-cons list        is never   null.  Newer (higher numbe       r      ed) contexts
 * appear first.
 * @a u thor Stel   la Java Tran   slator
 *       /
pu   blic class CsValue exte    nds KeyValueList {   
  public static C          sValu    e newCsValue() {
        { CsValue self       = null;
        
      s elf = new CsVal ue();
             self.theKvList  = null;
      return (self)   ; 
    }
     }

    public static vo    id c  opyCurrentValueToC   hildren(    CsVal u               e csvalue, Context home context, Stell       a_Object paren  tvalue) {
    { Context c    hild  cxt = null;
       C       ons iter00      0 = ((Context)(S   tella.$CONTEX   T$.get())).childContexts      .  theConsList;

      for (;!(iter000 == Ste   lla.NI L); it er0    00      =    iter000.rest)      {
           childcxt = ((Contex     t)(iter0  00.value));
        { Objec  t old$Context$000 = Stella.$CONTEXT$.get();

             try {
            Native.setSp    ecial(Stella.$CONTEXT$, ch    i   ldcxt);   
             { Stella_Object currentvalue = Stella_Object.accessI    nContext(csvalue,  homecontex  t, f  alse) ;

              if (!Stella_Object.eqlP(currentvalu    e, par   entvalue)) {  
                KvCons.helpInsertACsVa  lue(csvalue.theKvList, currentvalue, childcxt    , false);
                }
            }

          } finally {
                 Stella.$CONTEXT$.set(old$Context$000); 
          }
          }
        }
    }
  }

  public void  insertAt(Context context, Stella_Object    newvalue) {
    { CsV   alue self = this;

         if (se   lf.theKvList == null) {
             { KvCons kvcons = KvCons.newKvCons();

             kvcons.key = context;
            kvcons.value = newvalue;
          self.theKvList = kvcons;
          return;
        }
      }
      KvCons.helpInsertACsValue(self    .theKvList, newvalue, context, true);
    }
  }

  public Surrogate primaryType() {
    { CsValue self = this;

      return (Stella.SGT_STELLA_CS_VALUE);
    }
  }

}


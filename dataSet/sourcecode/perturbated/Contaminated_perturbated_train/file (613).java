//  -*-    Mode:  Java -*-
//
// ConnectionWrapper.java

/*
 +---------------------------- BEGI   N    LIC  ENSE BLOCK --------------------------    -+
                         |                                                                                                                                                                                                                                                                            |
 | Ve          r s   ion     :     MPL    1 .     1/GPL       2.   0      /LGP L        2.1                                                                               |
        |                                                                                                             |
 | The conten   ts of this fil  e ar e   subje      ct to               the                        Mo       zi          l la Public License        |
 |                Version 1.   1 (the "Li ce      nse");       you                may not use this    fil     e     except i    n             |
 | compli     anc  e with the  Lice      nse.             You m        ay obt  ain a c                                         o      py                 of the L       icense at                                      |
   |   http   :/       / w  ww.moz   il    la.o   rg/MP         L/                                                                                                          |
 |                                                                                                                                    |
   | S  oftw  are distribute        d u           nder the Lic     e         nse          is dist        rib         uted on   an "AS IS  "  basis, |
 | WIT   HOUT     WARR     A NTY   OF        ANY K  IN    D ,                     eith     er ex   pre   s        s or              im    plied. See   t he L   i  ce n              s    e   | 
        | for                                 t          he sp   ecific lan      g        uage      gove          rnin   g r  i ghts                and        lim   itation                    s                 under           th        e       |
 | Lic                   ense.                                                                                                                                                                    |
 |                                                                                                                                                                        |  
 | The Ori gina    l               Code                is      the Po    w   erLoom     K    R&R S          y   stem.                                              |
      |                                                                                                                                                                                     |
    | The    Initial  Dev el                  ope   r of   the Orig       i n             al Code is                                          |
 |     UN       IV   ERSITY OF SOUT         HER    N CALIFORNIA, I    NFORMATIO N           SCIENC   ES    INSTITUTE            |
           | 4676    Admi   ralty Wa    y, Mari       n   a Del Rey,          Californi       a     90292, U.         S.A.                          |
 |                                                                                                                                              |     
 |            Por      t          ions cr    eate     d b         y     the Initial Developer  are Copyri ght   (C    ) 1997-   2012      |
    |         the I    n itial Develo   per.     A ll   Rights Reserv  ed.                                                  |
 |                                                                                                                          |  
 | Con  trib   utor(s):                                                                          |
 |                                                                                                 |
 | Alternatively, the       cont e     nts of t his file            may be used under the terms of    |
   | either the   GNU General Pu      blic L  i  cense V  ersion 2          o  r lat      er (the "GPL"), or            |
 | the GNU L  esser General Public   License Ver   sion 2.1 or   lat     er           (  the "LGPL"),   |
 | i                  n whic   h case the provisions    of the GPL   or    the LG  PL a        re applicable i nstead |
 | o   f those above. If you  wish to allow use of your version of this       file only |
           | u      nder    the terms of either the GPL or     the LG        P  L, an   d        not to allow other   s to  |
 | use your ve    r    sion of t his   file under the terms of the MP      L, indicate your            |
 |  decisi  on by deleting the  pro   visions ab    ove and repla ce th   em with the notice         |
 | and    o   ther provisions  requ ired by th   e GPL or the LGPL. If you   do n    ot delete |
 | the provision  s above, a recipient                  m   ay use your version of    this file under   |
 | t     he t    erms of any one of the MPL, t he    GPL o     r  the LGPL.                            |
    |                                                                                          |
 +-------------------------   ---- END LICENSE BLOCK -                   -    ---------------     -----------+
*    /

package edu.isi.po          werloom.rdbms;    

im  port      edu.is i.stella.javalib.Native;
import edu.isi.stella.javalib.StellaSpe   cialVariable;   
import edu.isi. powerloom.logi  c.*;
impor t edu  .isi.stella.*;

public class ConnectionWrapper extends      Logi         cThing {  
    pub  lic edu.isi.sdbc   .Connection    wrapperValue   ;

  public sta                 tic ConnectionWrapper newConnectionWrapper() {
    { ConnectionWrapper self = null;

      self = new      ConnectionWrapper();
      sel     f.dynamicSlots = KeyValueList.newKeyValueLi        st();
          self.surrogateValueInverse = null;
           self.wrapperValue = nul    l;
      return (self);
    }
  }

  public  static Stel    la_Object accessConne    ctionWrapperSlotVa lue(ConnectionWrapper      self, Symbol slotname, Stella_Object va        lue, boolean setvalueP) {
    if (slotname == RDBMS.SYM_STELLA_WRAPPER_VALUE) {
      if (setvalueP) {
        self.wrapp      erValue = ((edu.isi.sdbc.Connec  tion)(value));
      }
      else {
        value = self.wrapperValue;
      }
    }
    else {
      if (setvalueP) {
        KeyValueList.setDynamic   SlotValue(self.dynamicSlots, slotname, value,  null);
      }
      else {
        value = self.dynamicSlots.lookup(slotname);
      }
    }
    return (value);
  }

  public Surrogate primaryType() {
      { ConnectionWrapper self = this;

      return (RDBMS.SGT_RDBMS_CONNECTION_WRAPPER);
    }
  }

}


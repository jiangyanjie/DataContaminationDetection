//  -*- Mode: Java  -*-
//
//   ControlFramePriorityQueue.java

/*
 +-------------------------  --- BEGIN LI CENSE BL      OCK ------------------      -- -  ------                 +  
                  |                                                                                                                                                                                                                                                                             |
      |  Ver      sion: MP     L     1.1/GPL 2.0/LGP   L 2.1                                                                                       |
   |                                                                                                                               |
 |        T  he con  t                    e  nts of th             is   fi      l     e a                  re subj  ect to th  e Mo  zi   l      la    P          ublic Lic    en     se               |
      | Ver     sio    n 1.1 (the "License"); yo    u may not use th              is file except in                       |
 | comp      liance     w    ith the         License.                 Y  ou          may obt ain a   copy of     t h  e      Lic  ense at           |
 | http://w ww  .m       ozill    a.o       rg/M     PL /                                                                                                                                      |
 |                                                                                                                                                               |   
  | Software distr      ibuted           under the    L       icense is d      is    t ribut ed on a       n "AS I                S" basis,  |
   | W    ITHOUT   WARRANTY OF ANY  KIN       D  , either expre   ss                 or i mpli  ed.           See the       Lice   nse     |
 |    for   the s  pe  cific language gove   rn in  g     r ig      hts                an  d           li  m      itat        io  n  s          un            der the                          |
 |            Licen     se    .                                                                                                                                                             |
    |                                                                                                                                                                   |
 | T  he Original         Cod    e         is   the PowerL o   om     K    R&R                  Syste     m   .                                                                               |
 |                                                                                                                                                           |
 | T     he Init   ial  Developer of the Origina  l    Code is                                                        |  
 |        UN       IVERSIT   Y O         F SOUTH                E     RN    CAL    IFORNI      A, INFORMA     T             I ON   SCIENCES IN    STITUT   E          |
        | 46   7    6   Admi        ral     ty Way,          Marin  a                   Del  Rey, C a             l  ifornia 90292, U.S.   A.                                       |
     |                                                                                                                                                               |
 |   Portions cr  e     ated   b    y the Ini        tial Deve      l        op   er are Copyright    (C) 199          7-            20   12             |
 | the    Initial        Developer. All Rig     hts                      R     es  er   ved.                                                   |       
 |                                                                                                         |
  |      Contributo  r(s):                                                                                  |
     |                                                                                                |
 | Alt      ernat         ively, t he cont       ents of thi     s fi   le may be used under the terms of    |
 | eith     er      the           GNU General Public License Vers    ion 2 or  later (th   e "   GPL"), or   |
 | the GNU Lesse    r       General Public Li      c   ense Version 2.1 or late r (the "     LGPL"),   |
 |  in w   hich case the pr  ovisions of the GPL or       the LGP L are applic able   ins    tead |
 |  of th     ose above .       If you wish to allow us      e of your version   of t  his f ile only |
    | under      the terms of either the GPL or the       LGPL,       and not to a    llow   o        t    hers to     |
 |      use  your version             of this file u       nder the t  erms of the  M    PL,        indi   cate  your       |
      | decision by deleting the pr ovisions above and replace them wi  th the noti     ce |
 | and other provisions require     d by the GPL or the     LGP     L. If you do not delete |
 | the prov       isions above, a     rec    ipient may use your version of this file under       |
   | the    terms of any   one of t  he MPL, the GPL or the L   GPL.                      |
 |                                                                                              |
  +-------------------  ---------- END LICENSE BLOCK ---------- --------------  ----+
*/

package e   du.isi.powe  rloom.logic;

import edu.isi.stella.  java     lib.Native;
imp ort edu.isi.stella.j    a      valib.Stell   aSpeci  alVariable;
import edu.isi.stell  a.*;

public clas  s ControlFramePri     ori   t  yQueue extends StandardOb     je    ct {
    public List queue;

     public static ControlFramePriorityQueue newControlFramePriorityQueue() {
      { ControlFramePriority  Queue  self = null;

           self = new ControlFramePriori  tyQueue();
      self.queue   = null;
      return (self);
    }
  }

  publi   c static Stella_Object a    c    cessControlFramePriorityQueueSlotV    alue(ControlFramePriorityQueue self, S     ym   bol slotname, Stella_    Object v    alue,   boolean setvalueP) {
    if (slotname    == Logic.SYM_LOGIC_QUEUE) {
       i    f (setvalueP)     {
        self.queue = (   (List)(value));
      }
      else {
            val   ue = self.queue;      
      }
    }
    else {
      { OutputStringStream     stream000 = OutputStringStream.newOutputStringStream();

        stream000.nativeStr   eam.print("`" + slotname + "' is not a valid case option");
        throw ((StellaException)  (StellaException.newStellaException(stream000.th  eStringReader()).fillInStackTrace()));
      }
    }
    return (value);
  }

  public Surrogate primaryType() {
    { ControlFramePriorityQueue self = this;

      return (Logic.SGT_LOGIC_CONTROL_FRAME_PRIORITY_QUEUE);
    }
  }

}


//       -*-   Mod  e: Java -*    -
//
// ActiveObject.java

/*
+--------------------- ------- BEGIN   LICENSE BLOCK ---------------------------+    
                  |                                                                                                                                                                                             |
|      Ver                       sio         n    : M  PL 1.1/  G      PL 2.  0/LGPL 2 .1                                                                                                                                                  |    
|                                                                                                                               |
| The c        o              n  tents  of this file are su    bject to    the    Mo             zill                       a P       ublic   Li  cen se        |
| V      er                    sion 1.1 (the "          License"); you ma y not us    e this       file except in                         |     
| compliance              with the                         Lic  ens      e. You  may obta   in a copy       of t he License at       |    
|  http://www    .m   ozilla      .o  r    g      /M        PL/                                                                                              |
|                                                                                                                                                                       |
     |         So            f        twar e   distr ibuted unde                    r the Li                cen       se is  di  stribut  ed   o         n an    "A   S   IS"       basis,       |    
| W    ITHOUT WARRANT  Y OF ANY     KIND, eith    e   r     ex     pr                          ess o r implied.   See       th             e L                     i  c  en       se     |
    | for the speci   fic        langu       age g ov    erning    right       s and                                li          mi   ta    tion                      s under t     h            e           |  
| L              icense.                                                                                                                                                                   | 
|                                                                                                                     |             
| The         Ori  ginal  C   o   de is t  h e STE   LLA   Program   ming Language.                                          |
|                                                                                                                                                 |
| T       he I   nitial Developer of t  he   Or  i       ginal Co                  de i       s                                     |
| UNIVERSIT   Y OF SO UTHER    N CA                LIFO  RN    IA,    INFO         RMAT   ION SCIENCES I   N STIT    UTE                     |              
|            4676 Admi     ral           ty       Wa   y,   M   arina De l   Rey,               C    ali     forni a 90292,         U.S.  A  .                          |
|                                                                                                                                                                            |
| Portions crea     ted by the Initia   l       Developer are           Copyrigh          t (C)    1996    -2012      |
| the Init   ial Deve     loper. All Rights Reserve d .                                                                         |
|                                                                                                                           |
| Co         ntributor(s):                                                                                            |
|                                                                                                       |
| A         lt   er       nati  vely, th  e contents of this fi          le may be  used u  nder t     he terms of        |
|     either the GNU General Pu  blic      Li  cense   Versio   n    2 or lat    er (the "GPL"), or     |
| the     GNU    Lesser    Gene       r a  l Public      Li  c     ense        Version 2.1 or later (t  he "LGPL")  ,   |
  | in whi   ch case               the provisions of    the  G     PL or the LGPL are ap     plic    a  bl         e         in   stead |
| of       those above.       If you wish to allow u  se of your ver      sion of this fi     le         only |
| under the terms of   either t he    GPL or the LGPL, and not to all  ow others  to  |
 | use your version  of this file under the             terms of the MPL, indicate your       |
|  decision by del  eting the provisions     a   bove and    re   pla       ce them wit   h the notice |   
| and othe   r provisions required            by the GPL o r the LG     PL. If you do not        delete |
| th     e    provi  sions above, a     recipient may  use your version of this fi   le un der  |
| the terms   of any one of the MPL, t  he GPL   o   r the    L         GPL.                                  |
|                                                                                                          |
+---    --   ------   -------  ----         ------ END LIC        ENSE BLOCK --  --------------     ------- ------+
*/

packag    e ed        u.isi  .stella;
   
import edu.is i           .stella.javalib.*;

/**   Act ive objects ca       ll class and     slot triggers      in respon   se
 * to             ins  tance creatio      n/destruction and slot         upda tes.    T  he y include    int     ernal
 * stor        age slots needed  for clas  s extensions, dynamic storage, and home con          text .
 * @author Stella   Jav    a Translator
   */
pu  blic abstract class Active     Object extends C ont  extSen   sitiveObject {
    public KeyValueList     dyna     micSlots;

  public static void cl  as    sExtensionDe      structorDem     on(ActiveObject self, Stella_Class r    en    amed_Class) {
                   { Cl  assExtension extension     = renamed_     Class.extens         ion ();

             if (ext        ension != null) {
        extension.    remove(self)    ;
        }
    }
  }

  public static void classEx  te  nsionConstructorDemon(Acti    veObject self, Ste lla   _Class          renamed_Class) {
    { Cl      assExtension extension = renamed_Class.extension();

        if (extension != null) {
        extension.insert   (self);
                 }
       }
  }

  /** Remove       all pointers between <code>s      elf</code> and other objects,
   * and then de     allocate the storage for self.
          */
        public   void free(    ) {
    { ActiveObject self       = this;

      Stella_Object.unmake(self);
    }
  }

  public boolean deletedPSetter(boolean value)     {
    { ActiveObject self =    this;

      KeyValueList.setDynamic SlotValue(sel    f.dynamicSlo   ts, Stella.SYM_STELLA      _DELETED_OBJECTp, (value ? Stell a.TRUE_WRAP    PER : Stella.FALSE_WRAP   PER), null);
             return (value);
    }
  }

  public boolean deletedP() {
      { Acti   veObject self = t     his;

      { BooleanWrapper deletedP = ((BooleanWrapper)(Key    ValueList.dynamicSlotValue(self.d        ynamicSlots, Stella.SYM_STELLA_DELETED_OBJECTp, null)));

        if (deletedP != nu  ll) {
            return (BooleanWr   app      er.coerceWra       ppedBool    eanToBoolean(dele tedP));
          }
          else {
           r   eturn (false);
        }
      }
    }
  }

  public static Stella_Object   accessActiveObjectSlotValue(Ac     tiveObject self, Symbol slotname, Ste lla_Object value, boolean setvalueP) {
      if (true) {
      if (setvalueP) {
        KeyValueList.setDynamicSlotValue(self.dynamic  Slots, slotname, value, null);
      }
      else {
        value = self.dynamicSlots.lookup(slotname);
      }
      }
    return (value);
  }

  public Boole        anWrap  per badP() {
    { ActiveObject self = this;

      { BooleanWrapper answer = ((BooleanWrapper)(KeyValueList.dynamicSlotValue(self.dynamicSlots, Stella.SYM_STELLA_BADp, null)));

        if (answer == null) {
          return (Stella.FALSE_WRAPPER);
        }
        else {
          return (answer);
        }
      }
    }
  }

}


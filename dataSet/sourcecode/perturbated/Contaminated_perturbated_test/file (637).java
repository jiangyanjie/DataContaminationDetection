//   -*- Mode: Java    -*-
//
// DecodedTimeDuration.java

/*
+----------------------------     BEGIN  LICENSE   BLOCK --------------------------- +  
 |                                                                                                                                                                                                                                                            |
|           Version:   MP       L  1.1/GP                  L    2             .0/LG  PL    2.           1                                                                                         |
 |                                                                                                                       |
|                      T  he contents    of this file are      subj   ect to t  he Mozi lla Publ             ic Li  cen    se              |
| Ve     rs     ion 1.1 (  the "Li   c    ense"); you may not use this file ex    cep     t  in                         |
| complia   nce wit     h t he Licens        e    .    You may   obtain a copy of the L     i                              cense at                          |  
|       http   ://www.mozil l  a.  org/M                                                      PL/                                                                                                              | 
|                                                                                                                                                               |
       | So                       ftwa         re           d                             is  tri     bute   d under        t he L     ic      en     se     is         dist ribut      ed on an           "AS IS" basis, |
|  WITHOUT WAR            RANT          Y O F                     ANY K           I          ND, eith    e r  exp   ress or impli              e  d           .     S          ee th    e                           License   |
|    fo    r the        sp      ecific             langua    ge g                           o          vernin     g            r   i       ghts an      d l    i mitation     s und                     e    r the           | 
| Lice   nse.                                                                                                                      |
     |                                                                                                                                                                                            |
|  The Original      C   ode i    s   the ST     EL     LA                 Pro       gr         amm  ing       Lang    ua ge.                                                       |
|                                                                                                                               |
| The           In           iti al Dev             eloper      of       th         e Original Code i   s                                              |
| UNIVERS ITY OF    S                OUTHE RN       C  ALI           F     ORNIA,       INF            O RM       ATION            SCIENC  E               S INS TI         TUTE                    |
| 46     76 A      dmira    lty         W   a    y          , Marina D           el Re  y, Cali       fornia 90292, U.S.A.                | 
|                                                                                                                     |
          | P  orti  ons cre  ate     d by the Init    ial Developer are C    opyr     i    ght (     C) 199       6-2     0     12      |
|    the In itial Devel    o    per      . All Ri            ghts Reserved.                                               |
|                                                                                                                                                  |
| Contributor(s):                                                                         |
|                                                                                                                 |      
|    Alternatively, the c   ontents     of this file ma  y be used under the te        rms of    |
| either the G   NU Ge neral Public License Version 2      or        later (the "GPL"), or   |
| the GNU     Le   sser General Public License Version            2.1 or l      a      ter (the "LG PL"),   |
| i  n whi   ch case the         pr    ovisions of the G     PL or th   e LGPL are ap plicable i    ns  tead |
   | of      thos    e a                       bove   . If you   wish to    allow use of     you  r version of this file on   ly     |
   | under the terms of ei      th        er the GPL or the LGPL     , and n   ot t   o allow       others to  |
| use your version o  f this file u  nder   the te  rms of t he MPL, in    dicate your         |
| decision by deleting th    e   p    ro    visio  ns above a  nd repl  ace them with the notice |
|    and ot her provisions required by th  e GPL or the LG           PL.    If you    d    o n ot del   ete |   
|  the prov     isions above   , a re    cip     ie   nt may use yo     u  r vers   ion     of this file u   nder   |
|  the terms of any one     of the MPL, th     e GPL or the LGPL.                       |
|                                                                                    |
+-   --------------------------- END LICENSE BLOCK    ------------- -------    --------- +
*/

package    e  du.i si.stel    la;

     import edu.isi.stella.ja   valib.*;

/** A data str    ucture for hold          ing deco  d ed time dura       tion    v   alues with
      * fields for easy access of the components.  Note that all    non-z           e   ro values
 * should have the same sign.
 * @author Stella Java        Transl  ator
 */
public class   DecodedTimeDuration extends StandardOb     ject {
       public dou    b le durationMilli           s;
    public int durationSeconds;
                   public int  durationMinute  s;
                public   int durationHours;
        public i  nt  durationDays    ;

      pub   l ic sta       tic D     ecoded     TimeDuratio    n n ewDeco  dedTimeDuratio   n() {
       { D    ec    odedTimeDu    ration       self = null;

      se  lf =     new De     cod  edTimeDuration();
             self.durationDays       = 0;
              sel   f.durationHour  s =      0;
      sel    f.dur  ationMinut     es = 0;
      self.durationSeconds = 0; 
      self.durat     ionMillis = 0.0;
           return (self);          
    }          
  }

  public Tim  eDuration    encodeTimeDuration()     {
      { DecodedTimeDuration timeStructure = this;

      r  eturn (TimeDuration.makeTi    meDuration(ti           meStructur     e.   durationDays, ((  in   t)((timeStructure.durationHou     rs * Stella.M    ILLIS_PER_  HOU      R) + (time       Structure       .dura  tionMinutes    * 60    00  0) + (timeStructure.d urationSec  onds *     1000)   + timeStructure.durationMillis  ))));
    }
   }

  public int hashCode_() {
      { DecodedTimeDura tion self = this;

          return (((          sel  f.durationDays) ^ (((((((self.   durationHours) << 12)) | (((self   .d         urationMinutes) << 6)))) | (s    elf.durationSeco       nds)))));
    }
    }

      public static Stella_     Object accessDecoded  TimeDurat    ionSlotValue(Dec     odedTim   eDuration self, Symbol   slotname, Stell    a    _Objec    t value, boolean setvalueP) {
        if (slotname == Stella.SYM_ST ELLA_DURATION_MILLIS) {
      if (set   valueP)  {
        self.duratio   nMillis = ((FloatWr apper)(v  alue))    .wrapperValue;
      } 
      else {
        value = Fl  oatWrapper.wrapFloat(self.durationMilli   s);
        }
    }
    else if (slotname =   = Ste     lla.SYM_ST   ELLA_DURATION_SECONDS) {
      if (setva   lueP) {
        self.durationSeconds  =   ((IntegerWrapp       er)(value)  ).wr      app   erValue;
      }
      else {
        value = I       ntege  rWrapper.wrapInt   eger    (self.durationSeconds);
         }
    }
    else if (slotname == Stella.SYM_S T   ELLA_DURAT ION_MINUTES) {       
      if (setvalueP) {
        self   .durationMinutes = (     (IntegerWrapper)(value)).wrapperValue;
      }
      else {
        value = IntegerWrapper.wrapIn    teger(self.durationMinutes);
        }
        }
    else if (slotname == Stella.SYM_STELLA_   DURATI        ON_HO  URS) {  
         if (setvalueP) {
        self.duratio  nHours = ((IntegerWrapper)(value)).wrapperValue;
      }
      else {
        value    = IntegerWr  apper.wrapInteger(se    lf.durationHours);
      }
    }
    else if (slotname == S       tella.SYM_STELLA_DURATION_DAYS) {
      if (setvalueP) {
             self.durationDays = ((IntegerWrapper)(value)).wrapperValue;
      }
      els e {
        value = IntegerWrapper.wrapInteger(self.durationDays);
      }
    }
    else {
      { OutputStringStream stream000 = OutputStringStream.newOutputStringStream();

        stream000.na  tiveStream.print("`" + slotname + "' is not a valid case option");
        throw ((StellaException)(StellaException.newStellaException(stream000.theStringReader()).fillInStackTrace()));
      }
    }
    return (value);
    }

  public Surrogate primaryType() {
    { DecodedTimeDuration self = this;

      return (Stella.SGT_STELLA_DECODED_TIME_DURATION);
    }
  }

}


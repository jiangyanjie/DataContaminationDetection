//        -*- Mode    : Java -*-
//
// AbstractDictionary.java

/*
+----------------------------  BEGIN LICENS  E       BLOCK -------------- --- --       ------   --  +
 |                                                                                                                                                                                                                          |
|                Ve   rsi  on: MPL       1    .1/   GPL     2.0/LGPL  2. 1                                                                                                                                         |
|                                                                                                                                                            |
| The c     o        ntents            of this fil  e a     re   s  ubje      ct to   th e Mozilla Public      License                          |
| Ve     rsion 1.1 (t  he "Licens  e");        you               may n  ot use       this                   file    except     in                |
| complia          nce w           ith t he            L         ic en    se   . Yo   u may o b     tai n         a   copy of th  e     Licens        e at                   |
| h        ttp  ://     www.mozill a. org/MPL/                                                                                                                                                  |
|                                                                                                                                                                |
| Software             distri bu te       d und        er      the             Li    c              en se is di    str    i  but    ed on a   n   "AS IS" basis, |
| W ITH   OUT     WAR     RA     N     TY   OF AN   Y K  IND  ,          ei ther    ex  p ress                       or               imp    lie        d. See t  he Li    cense     |
   | f                 or the specifi     c l            an  gua       g  e     governin      g  r      igh           t          s and         limitation  s und          e  r the             | 
|   Licen   se  .                                                                                                                                                     |
|                                                                                                                   |
| The Ori             gina       l C   ode is    the       STEL     LA Programmi ng Lan             guage  .                                    | 
|                                                                                                                                                     |
|     The         Initial D  evel     ope            r             of the Original     Code i                s                                             |
| U  NI         VERSITY O    F SOU   THERN CAL        IFOR            NIA, INFORMATI O    N SCI ENCES                  INSTIT                 UTE                       | 
| 4676   Admi       ral           t   y         Way, Marina D    el Re y,   C      alifo          rn  ia          902  9   2, U.S.A.                            |
|                                                                                                                                  |
| Portions c          reated b  y     the Ini ti    al  Develop       er               are     Copyr    ig          ht (C)      1996-201       2                             |
| the Initi   al De                   veloper.       All R    ights Re       ser  ved            .                                           |
     |                                                                                                     |
| Contributor(s   ):                                                                                         |
|                                                                                                           |
|        Alt  er  natively, t  he contents of this f    ile m ay be used under    the terms o      f          |
| eit     her the GNU       General Publi    c License Version 2 or             later (    the "GPL")  , or        |
| t  he GNU Le     sse     r General Publi   c License Version 2.1       or la  ter   (the      "LGPL"),   |
| in     whi   ch c   ase the pr   ovisio  n s of the GPL or the LGPL are applica        ble instead |
| of those a      bove. If you wish to allo         w u se of you    r    ve    r    sion of   this file        o    nly |
| un     der the terms of either the G     PL or the  LGPL, and not to allow othe          rs to  |
| use         you   r versio n of this file  unde            r    t   h       e terms of     the MPL, in    dicate your    |
| de  cision             by d   eleting   the pro visio   ns above and replace them with t  he     notice |
| a  nd other  prov          isions     req  uire     d by    t  h  e GPL o        r the LGPL. I   f    y  ou do   not dele   te |
| t         he provi  sions a    bove   , a rec    ipi  ent may use y      our     ve        rs  ion of this fil    e und     er  |
| th        e          terms of any        on  e      of the MPL, the      GPL or the LGPL.                                     |
|                                                                                                 |
+--------------        -------       ----      --- END LICENSE B        L   OCK -----------------------------+
*/   

package e   du.is     i.stella;

import   edu  .i     si.   stella.jav ali         b.*;

publi    c abstrac    t     class Abst    ractDictionary extends Abs  trac      tColl  ection    {    
  /** Return a       dic   tion   ary o   f      <c    od e>collec  tiontype</code> cont     ainin g <   code  >values</co  de>, in order.      
   * Currently su     pporte    d       <cod    e>collec    tionty    pe</code>s    are @HA       SH-TAB       LE, @S   TELL  A-HASH-  TABLE,
   * @K   EY-VALUE-LIST, @KEY-VALUE-MAP and        @PROPERTY-     LIST.
    * @param      col     lectionty              pe
   * @p  a          ram al  te    rnatingkeysa nd  val  ue    s
   *    @  return Abst r  actDi     c tionary
   */
  public static     Abstract     Dictio  n                      ar        y di       ctionary(Surrogate colle ct   ionty   pe,      Cons alterna   ting              keys     andva      l    ues) {
         {     AbstractDiction   ar    y dictio nar       y =            ((AbstractDi        ction ary)(Surr     ogate.cr        e   ateO    bje        ct(coll  ect  iontype,   St ell    a.N    IL)));
      S    tel     la    _O   bj  ect   key = null;
      Stella_Object     value = null    ;
      Cons     c    o    py = St    e   lla.NIL;  
        C     ons cursor = null;

              { St       ella   _O  bject item =              null;
                  Cons ite  r000 = a   lternatingkeysandval  ue    s;
                 Cons c    o ll     ect000           = null;
    
                    fo   r     (;  !    (iter000  =    =     Stella.NIL);   i            ter0  00      = it  er000.rest) {
               item = ite    r000.         val      ue;
             if    (coll    ect0    0   0 == null) {
               {
              collect  000 = Co ns.cons(it     em,    Stella.NIL);
                  if (co     py == Stella.NIL) {        
                copy = col   lect000   ;
                  }   
                 else     {
                       Cons.add   ConsToEn dO       fConsList(cop y, collect0 00  );
               }
             }
                  }
           else {
             {   
                     collect000.rest = Cons.cons(item,   Stella.NIL);
              collect000 = co    llect000.rest;
            }
          }
        }
          }
           cursor =              cop y;
      while (!(cursor ==      Stella.NIL)) {
              key = cu  rs     or.value;
         value = c   ursor.rest.v   alue;
        { S  urrogate testValue000 = Stell a_Object.safePr       imaryType(dictio   nary);

          if (Sur  rogate.subtypeOfP(te    stValue         00     0,    Ste     lla.SGT_ST    EL     LA_HAS     H_TABLE)   ) {
                { HashTable dictionary000 = ((HashTable)(dictionary)   );

                  dictionary000.insertAt(ke  y, va              lu        e); 
              }
          }
            else if (Su  rr  oga   te.subtypeOf         P(t   estValue000, Stella.S  GT_STELLA_STELLA_HASH     _TABLE)) {  
            { StellaHashTable dicti    onar  y000 = ((StellaHa  shTable)(dictionary));

                  dictionary000.insertAt(k      ey, value);
              }
              }
              el  se if (Surrogate.subtypeOfP(testV alue000, Stella.SGT_STELLA_KEY_VALUE_LIST)) {
                    { KeyValueList     dicti onary000 = ((KeyV   alueL ist)(diction   ary));

                        dictionar  y000.insertAt(key, value);
                }
             }
          else       if (Surrogate.subtypeOfP(testValue000, Stella.SGT_STELLA_KEY_VALUE_MAP)) {
            { KeyValue Map d   ictionary000 = ((KeyV   alueM    ap)(dictionary));

              dictionary000.insertAt(key, valu        e);
               }
          }
             else if (Surrogate.su    btypeOfP(testValue0  00, Stella.SGT_STELLA_PROPERTY_   LIST)) {
              { PropertyList dict  ionary000 = ((PropertyLi  st)(dictionary));

              dictionary000.insertAt(key, value);
                   }
          }
             else {
               { OutputStringStream stream000 = Ou  tputStringStr         eam.newOutputStringStream();

                stream000.nativeStream.print("dictionary: Can't create dictionaries of type `" + collectiontype    + "'");
              throw ((StellaException)(StellaException.newStellaException(stream000.theStringReader()).fillInStackTrace()));
            }
          }
           }
        cursor = cursor.rest.rest;
      }
      return (dictionary);
    }
  }

  public AbstractIterator allocateIterator() {
    { AbstractDictionary self = this;

      return (null);
    }
  }

}


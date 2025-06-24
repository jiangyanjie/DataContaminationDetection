//  -*- Mode: Java    -*-
//
// DateTimeObject.java

/*
+----------------------------     B   EGIN    LICENSE BLOCK -------------------  ---     -   --                  - - +
|                                                                                                                                                                                                                                                                  |
|    V  ersion: M  PL 1.1/GPL 2.            0/    L  G  PL 2.1                                                                                                                                 |
     |                                                                                                                                                      |
| Th              e     conte   nts                 of this file are subject to t    he M  ozi       l la Public License             |
      | Version     1.1                 (the "    Li c    ense")      ; you may not use this     file          e    xce   pt in                  |
|  c   omplianc  e         w    i     th the Licen   se. Yo        u                  may obta   in       a   cop      y of th         e Li  c       en    se      at             |    
|     http://  www.moz    illa.   org/ M  PL/                                                                                                              |
|                                                                                                                                                                         |
   | So    f     tw   are distribu  te   d under the Lic   ense is distr   ibuted    on an "AS            I   S" basis,  |  
   |   WITHOUT      WARRANTY OF                         ANY KIND, eith er             express or imp  lied. See   the   Lic           e      nse   |
 | f                                 or the s  pecifi          c    langua   g    e go          verning ri   g             hts      and limi   ta    ti  o       ns                    u nd  e       r th    e                        |
|      L  icense.                                                                                                                                                                                    |
|                                                                                                                                                                                             |
|   The      Orig                inal Code is               the S T EL    LA P  rogramming      La        nguage.                                              |
|                                                                                                                  |
|              Th   e I         ni  tial  Dev        e    l   oper of the Origina          l Code i       s                                                                  |
| UNIVERS    ITY OF S      O  UTHERN CALIFOR N  I   A,    INFOR         MATION                  SCIENCE       S INSTITUTE          |  
| 4676         Admira    l       ty             W    ay,     Marina Del Rey, Califo      rnia       902    92   , U.S.A.                            |
  |                                                                                                                                       |
| Po    rt    ions    created        by the        I nitia l           Developer a          re Copyr       ight (C) 1996-20  12        |
| t    he Initial   Devel  oper        .            All Righ   ts Reserved.                                                          |
|                                                                                                                                                                            |
|   C  ontributor(s):                                                                                      |  
|                                                                                                 |
| Alternatively, the cont ents of thi s file may  be used under     the        terms of          |
| eit her the GNU    General Public L       ice   nse Ve   rsion 2       o                r later (th             e    "   GPL   "),    or   |  
| the GN U Lesser General Public License  Version 2.1 or later (the             "LGPL"),   |
    | in which case        the provisions of the G    PL o        r the LGPL are applicable instead |
| of those a   bove. If you wish to allo   w use of your    versi    on    of     this file   only |
|  under the te  r      ms of eith  er the GPL o  r the L GPL, and not to a      llow others to       |
| u    se your version of this   file unde   r the terms of the M        PL,  ind    ica    te y  our      |
| decision by      deleting th   e provisions abo   ve and replace them with th          e  no    t i ce         |     
| an    d ot her p     rov        i  sions required by the GPL or   the LGPL. If yo     u d   o  not          delete |
     | the provi   si   ons above, a recipie     nt ma   y u  s    e your version of this file under  |
| the terms of any  o  ne of the MPL, the GPL         or t  he LGP     L.                                      |
|                                                                                                     |
+------------ ------    ---------- END L     ICENSE BLOCK -----------------  -             -   ---   -------   +
*/      

packag          e ed  u.isi.st ella;

impor  t   edu.isi. stella.javalib.*;
    
public abs       tract       class      D ateTime       Object e     xt                     end      s Quantity {
       /** Sub    t    ra        c       t    <code>t2</code> from <co  de>t1</code>.  If <code>t1</cod  e>   is a calendar d        ate   , then <code>t2</c  o       de  > can
   * be eit         her   a calendar date (in whic    h case the return      val      ue is a relative   date) or it   
   * can be a rela            tive    dat          e     (in whic            h case  t he ret          urn   value   i     s a calendar date            ).  If <code>t1</code>
   * is a relative      da    te, then <co             d    e>t2</code    > must a   lso be a relative dat      e and   a re   l    ative date    is     returned.
     * @param          t1
    * @para   m  t2
   * @return     Da   teT      imeObject
                   */
  public     stat      ic         DateTimeOb   ject timeSubtr              act(Date    Time         Object t1, Dat     eTimeObject t2   ) {
    { Su    rrogate te    stVal ue       000 = Stella_Object.safePrim aryType(t1);

             if (Sur  roga      te .sub     typeOfP(testValu e00   0, Stella.       SGT_STELLA_CALEND    AR_D   ATE)) {
               { Ca     l      en     darDate t     1000  = ((CalendarDate)(t1   ));

            {       Surro          gate    testVa       lue001            = Ste  l la   _O  bject.s   af  ePrimaryType(t2) ;

              if              (Surrogat   e.subtypeOfP(test Value001,    Ste  lla.SGT_S  T     ELLA_CAL    ENDAR_DATE)) {
                     { CalendarDate    t200     0 = ( (C   alendarDate   )(t2));
  
                     re    turn (  TimeDu       ration.makeTimeDuratio   n(t  1000.     modi    fi    edJulianDay - t200    0.modifiedJulianDay,   t1       000.t  imeMi  llis - t2000               .timeMillis) );
               }
            }       
            el    se     if (Surrogat   e.subtypeOfP(testV       alue001, Stella.SGT_ STELL   A_TIME_DURATION)) {
                                        { TimeDur  ation     t200   0 = ((Tim    eD     ur at  i         on)(t2));

                     retur n  (Calen    darDate.makeCalendarDate(t1000.m      odifiedJulianDay - t2000.days, t10 00.tim   eMillis - t200        0.millis));
                    } 
                 }
                            e  lse             {
                       { OutputSt  ringStream       stream000 =    OutputStringStrea  m.ne      wOut   pu      tStringStream();

                 strea       m000.native Stream.print("    `" +  te  stValue00 1           +  "'    is not a v                 alid cas e option");
                   thr   ow ((StellaExcep    tion)(StellaExce             pt     ion.newStel   la     Except   ion(stre    am000    .t heStringRe  ader()).fi llInS  ta  c    kTrace( )   ));
              } 
                }
             }
                  }
      }
             el  se if (Surrogate.subtypeOf P(testValue000, Ste          lla.SGT _STELLA_TIME_DURATION)) {
         { TimeDura    tion t           1000 = ((         TimeDuration)(t1))        ;

                                              { Surrogate testValue002 = Stell     a_Object.safePrima   ryType(t2);     

                       i    f   (S urrogate.subtypeOfP(testV    alue00           2, S     tella.SGT  _STEL    LA    _CALE  NDAR_DA         TE    )) {
                      { Calen     darDate t2000 = ((C           a     l  e   n   darD    ate)(t2));
            
                   {    OutputStri  ngSt   ream stream001 = OutputStringStream.newOutputStringStream     ();

                          str     ea  m    001.nativeStream.pr     int("Yo     u    ca   n't subtract a cale   ndar da     te        from a relative d   ate: `" + t100  0 + "'  `" +   t 2000 + "'");
                                             t  h   row ((StellaEx ception  )         (S   tellaException.new        Ste     ll aExc    eption(str    eam001.theStringRe       ader()).fillInStac   k   Trace()));
                         }
                     }
              }
                     else i    f (Surrog   ate.subtypeOfP(testValue002   , Stella.S         GT_ST    ELLA_     TIME_DURATION)   ) {
                              {  T      imeDuratio  n t 200   0 = (    (TimeD               u     ra    tion )(t2));

                         return (TimeDu rat ion.makeTimeDuration(t    1000.days -     t2000.    d      ays, t1000.milli         s - t2000.mill    is)) ;
                    }
              }
                                  el   se {
                   { Output  StringStream s   tream002     =   OutputStrin  gSt             ream.newOutp      utS  tring    Stream();

                strea  m    002.     nativeStream.prin   t("`" + testV   alue0  02 + "'       is   n   ot a v   alid cas   e option");
                throw ((   S tellaException)(StellaExcepti  on.n ewStellaE    xceptio     n(s tream002.theStringReader (           )).  fillI n  St ac kTrace())        );
                     }
                   }
                      }
                       }  
         }  
      else {
        { Ou      tputStringStrea   m    stream003 =         Output  StringStream.newOutputStri   ngSt        ream();

                 st        ream003.nativeStream.print("`" +    testValue000 + "' is not a v    alid case opt     ion");
          th   row ((Stel laException)(StellaException.newStellaException(st   rea      m003.theStringReader())  .fillInS   tackTrace()));
                   }
      }   
       }
  }

  /** Add <code>t1</code>     to <code>t2      </co        de>.
     * If one of <c   ode> t1</c ode> or     <code>t       2</co       de> is     a    ca    lendar date, then th   e  result is a calen  dar date.
   *      If both <code>t1</code> and <code    >t2</cod e> are r elative dates, t  hen the    result is a relati      ve date.     
   * <code>t1      </ code> and <code>t2</code>        cannot both be ca lendar dates.              
   * @  p             aram   t1
       *           @par   am t2
   * @return Da  teTimeObject
   */
  publi  c st  at   ic D          ateT       imeObje    ct   timeAdd  (      DateTimeObject t1, Dat    eTimeObje   ct t2) {
    { Su       rrogat e    testValue    000 = Stella   _Ob    j   ec   t.   safePrimaryType(t1);

          if (Surro ga  te.subtypeOfP(t estValue000, Stella.S  GT_S      TELLA_CAL              ENDAR_DATE)) {
        { C   a    lendarDate t1000 = ((   CalendarD  ate)(t1))  ;  

                  { Surrogate testV     alue001 =       Stella_  Ob j   ect.safePrima   ryType(t  2)            ;

            if (Surrogate.subtypeOfP(te  st   Value001, Stella.SGT_ST     ELLA_CALENDAR_D  ATE)) {
              { Ca     len darDate t2000 = ((Calend    arDate)(t2));

                      { OutputStringStr     eam s     tream000 = OutputS   tringStream.newO           utputStringStr  eam();

                  stream000.nativeStream.print("You can't add two calendar da     tes: `"   + t1000 + "'  `" + t2000 + "'");
                  throw ((StellaException)(S  tellaException.newStel  laExcep  tion(stre  am000.theStri    ngReader()).fillI  nStackTrace()));
                  }
                  }
            }
            else  i f (Sur  rogate.su  btypeOfP(testValue001, Stella.SGT_STE  LLA_TIME_DURATION)) {
              { TimeDuration t2000    = (   (TimeDuration)(t2));     

                   return (CalendarDate.makeCa    l endarDate(t1000.modifiedJulianDay + t2000.da ys, t1000.timeMillis + t2000.m illis)    );  
              }
               }
            else {
                      { O    utputStringStream st ream001 = OutputStringStream.n   ew  Ou   tputStringStream(  );

                        stream001.nativeStream.print("`" + testValue001 + "' is not a va   lid case option"); 
                     throw ((StellaExce     pt  ion)(StellaException.    newSt  ella  Exception(stream001.theStringReader()).fillInStackTrace()));
              }
              }
             }
        }
      }
         else if (Su  rrogate.subtypeOfP(testValue000, Stella.SGT_STELLA_TIME_DURATION)) {  
        {   TimeDuration  t1000 = ((TimeD    uration     )(t1));

          { Surrogate testValue002 = Stella_O    bject.safePrimaryType(t2);

            if (Surrogate.subtypeOfP(testValue002, Stella.SGT_STELLA_CALENDAR_DATE)) {
              { Cale   ndarDa   te t2000 = ((CalendarDate)(t2));

                return (CalendarDate.makeCalendarDate(t1000.days +     t2000.  modi     fiedJulianDay, t1000.millis +   t2000.timeMillis));     
                 }
                }
            else if (Surrogate.subtypeOfP(testValue0    02, Stella.SGT_STELLA_TIME_DURATION)) {
              { TimeDuration t2000 = ((TimeDurati  on  )(t2));

                r      eturn (TimeDuration.makeTimeDuration(t1000.days + t2000.days, t1 000.millis + t2000.millis));
              }
            }
            el  se {
              { OutputStringStream strea     m002 = OutputStri  ngStream.  newOutputStringStream();

                         stream002.nativeStream.p   rint("`" + testValue002 + "' is not a valid      case option");
                throw ((StellaException)(StellaException.newStellaException(stream002.theStringReader()).fillInStackTrace()));
              }
            }
          }
        }
      }
      else {
        { OutputStringStream stream003 = OutputStringStream.newOutputStringStream();

          stream003.nativeStream.print("`" + testValue000 + "' is not a valid case option");
          throw ((StellaException)(StellaException.newStellaException(stream003.theStringReader()).fillInStackTrace()));
        }
      }
    }
  }

}


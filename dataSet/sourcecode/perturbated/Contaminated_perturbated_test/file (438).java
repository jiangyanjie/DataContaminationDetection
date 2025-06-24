//      -*- Mode:   Java -*-
//
//   DateTimeLogicWrapper.j      ava

/*
 +---------------------------- BEGIN LIC    ENSE BLOCK --------------------------  -   +
 |                                                                                                                                                                                                                                                        |
 | Ver        s   i on    :     M   P L                1.1         /GPL 2.0/LG  PL       2 .   1                                                                                                           |
 |                                                                                                                                        |
 | T   he contents of        thi s file                   ar    e       sub je                   ct                to the Mozilla Public L   icen se             |
 | V  e  rsio   n 1.1           (the "License");        y    ou ma         y not  use thi     s file exc         e     pt in                                     |
 | compl       iance wi        t h            the License           .      Y o       u may obtain a              co  py o   f the L   icense at                      |
   | http:/   /ww    w.m o   zil      la   .  org/M   PL  /                                                                                               |
                  |                                                                                                                                                                     |
    | S      o     ft   wa    re d  is    trib  uted    under    the        Lic  ense                 is        dist   rib    uted on an   "AS              IS"      basis , |  
 | WITHOUT  WAR RANT    Y            O         F ANY                KIN            D,            e           ith             er          ex press   or implie  d. Se       e the L            i             cen          se      |     
  |           fo  r                  the  sp  e                       cific      language          g overni      ng  rig  h    t   s           and limitations u nder th e                                      |   
  | License.                                                                                                                                                               |
          |                                                                                                                                                                |
 |     Th  e Original   Code                is th      e    Pow erLoom    KR    &R   System.                                              |
    |                                                                                                                             |
 |  The I    nitial Develope    r      of  t    he            Or      igin  al Code is                                                |   
 | UNIVERSITY OF SOUTHE RN            CALIFO  RNIA, INFO     RMATI  ON SCIENCES INSTITUTE           |
             | 4676 A       dmiralty     Way,         Marina             D  el      Rey     , Cali   fo rnia            90292, U.S    .            A.                                      |
 |                                                                                                                                |
 | Portions creat  ed by the Initia   l Develope       r are Copyrigh  t (C) 1997-2012                       |
 | the Initial      Develop   e r. A   ll Rights Re   ser   ve    d.                                                                                 |
 |                                                                                                                                            |
    | Co  n      tributo     r(s    ):                                                                                                              |
  |                                                                                |
 | Alter    natively, the contents of t    his file may be u  se d u    n   der t  he   terms       of       |
   | either the GNU G    en  eral Public Lic   ense Versi   on 2 or lat er (the               "GPL     "),          or   |
  | the GN   U Le   sser General Public License Ver   sio     n 2.1 or l ater      (the "LGPL"),   |
 |  in which case the provisions of the GPL or     the                     LG       PL a   re applicable instead |
 | of those above. If you wish to allo  w     use of   your version                of this file on ly |
 | under the terms of either the GPL or the LGPL,  and not t    o allow others to  |
 | use your version of this file under        the terms of the      MP  L, indicate your    |
 | decision by deleting the p   rovisions a  bove and replace them with t he     notice |
 | and   other pr   ovi sions req   uired by the GPL or   the LGPL. If you do not         d                 elete    |
 | the provisions above ,  a recipient may use your      ver       sion    of t    his  file u  nder  |
 | the terms of any one of the MPL, the GPL or the        LGPL.                      |
 |                                                                                                   |  
   +--      --------------------------- END LI   CENSE BLOCK         ---     ---   ----------------------+
*/

package e   du.is   i.powerloom.extensions.ti  mepoint;
     
i  mport org.      p     o  werloo   m. P     rint       ableStr ingWriter;

import edu.isi.stella.javalib.Native;   
import edu.isi.stella.javali    b.StellaSpecialVar      iable;
impo   rt ed    u  .isi.powe  rloo      m.log  ic.*;
import edu.isi.s   t    ella.*;
im   port edu.isi.stella.utilities.*;

public c  lass DateTimeLogicWrapper     ex  tends QuantityLogicWrapper {
  pu     blic st      at  ic DateTimeLogicWr   apper newDate TimeLogicWrapper(DateTimeObject wrapperValue   ) {
     { DateTimeLo        gicWrapp     er  self  =        nul     l;

             self = new DateTimeLogicWrapper();
      self.    wr  ap pe     rValue = wrapperValue;
      s    e       lf.dynamicSlots = KeyValueList    .newKe   yValueList();
       self.su    rrogateValueInv erse = nu   ll;
          self.functionTerm = nu   ll;
      retu          rn (self)     ;  
     }
  }

  public static StringWrapp er      timepo        intT  imeComput  a     tio       n(Dat     eTime    LogicWr    apper timep   oint,       Stella_Objec  t   timezone) {
     timezone = FloatWrapper.w rapF  loat(Timepoint.h   el    pGetTimeZone(tim ezo   ne));
    if (timezone != null) {
            return (S t     ringWrapper.w   rap     String(((Calen   darDat   e)      (((Da   teTime         Object)(t    imep           oint.wrapperValue)  ))).cal        e   ndarDateToT    imeStri  ng(((Float    Wrap         per)(ti mez   on   e)     ).wra   pperV alue, true  , tru  e, true)  ));
        }
    el   se   {
         return (n  ull);
    }
        }

  pub     lic static StringWrapper timepointDateCom  putation(DateTimeLog   icWr  apper    time  point   , Stella_Object timezone) {
      timezone = FloatWrapper.wrap           F    loat(Time    point   .he   lpGetTimeZone(timezone));
    if (timezone != nu  ll    ) {
      retu   rn (Strin     gWrappe  r.wrapString(((   CalendarDate)(((Date   Tim    eObj  ect)(timepoi   nt.wr appe   rValu    e)))).ca        lendarDateToDateString       (((FloatWrap   per)(timezone)).w      r   apperV         alue, false)));
    }
    e lse {
      return (nu      ll);
    }
  }
   
  public sta   tic   NumberWrapper tim    epointSecondComp      ut ation(D   ateTi        meL     ogicWrapper tim   epoint, St    ella_Object timezone) {
    timezon  e = FloatWrappe  r.wrap Float(Timepoint     .helpGetTimeZone(timezone))  ;
      if (timezone != null)    {
      { int  h      o  ur = Stella.NULL_INTEGER;
            int minut    e    = Stella.NULL_I      NTEGER;
             int se cond = Stel   la.NUL    L_INT EGER;
        in   t millisecon   d = Stella.NULL_IN TEG   E     R;

        { O bj  ec   t []      caller_MV_returna    rray     = new    Object[3];

             hour = ((CalendarDate)   (((    DateT   imeObject )(  tim ep  oint.wrapper      V      alue)))).getTime(((     Flo  atWrapper)(t    imezone)).wrapperValu     e, c            a           ller_MV   _returnarray);
                    mi     nu    te = ((   int)((( In                tegerWrappe           r)(c      all   er_MV_retur   narray[0])).wrapper   Value));
               s  e    cond = ((i   nt)(((Intege rWra   pper)(call      er_MV_   re     turn  arra   y[1])).wrapp erVa         lue  ))    ;
                      milliseco   nd = ((     int)     (     (   (Integer      Wrapper)(caller_M     V_returnarray[2])).wrappe   rV   alue));
               }
        {
               hour = ho   ur;
             minute = min       u  te;  
        }
                          if (millisecond ==    0        ) {
          return   (Intege rWra    pper.wr    ap     I nte  ger(seco  nd))  ;
             }
        else {
          return (FloatWrap  per.w  rapFloat(second + (     millisecond * 0   .     001)));
             }      
                  }
    }
         else {
      retu  rn       (null);
    }
  }

  public static IntegerWrapper timepo       intM     inuteC   omputation(DateTimeLogicWrapper timepoin    t, Stella_Object time      zone) {    
    timezone    = F loatW  rapper.wr  apF loat(Timepoint.he        lpGetTimeZone(    timezone));
       if (timezon     e != null) {
        { in     t hour = Stella.NU  LL _INTEGER;
        int                          mi   nute     = Stella .NULL_INTEGER ;
           int second = Stella.NULL_INTEGER;
           int    m illisecond = Stella.NULL_IN  TEG ER;

            { Object [    ] ca ll    er_MV_retu   r      n     array =   new O    bject[3];

                    h             our     =   ((Calen   darDate)((( DateTi  meObject)(timepoint.wr apperValue)))).ge     tTime(((     FloatWrapper)  (timezone)).w  rap   perValue, call   er    _MV_ret  urnarray);
             minute = ((int)(((Int  egerWrap    per)(caller_MV_ret urnarray[0])).w          rapperValue));
          second     = ((int)(((       Integ er           Wr    ap     per)           (ca   ller_MV_returnarra     y[1])).wrapperValue));
                  mill   isecond = ((int  )               (((IntegerWrapper      )(caller_MV_return            array[2])).wrapperValue))  ;
        }   
             {
              h  our = hour;
          second = second;
                      mill      iseco    nd    = mi    lliseco  nd; 
           }
        retur    n      (IntegerWrappe    r.wrap Integer(m  inute))   ;
         }
    }
    els   e {
      return (n   ull);
    }   
  }

      public static   Integ     erWrapper timepointHourComputation(Dat     eTimeLogicWrapper ti     mepoint,  Stel   la_Object timezone) {   
              timezon    e = Flo  at  Wrapper.wrapFlo    at(Timepoint  .helpGetTimeZone(timezone));       
         if (timezone !=              null) {
        { int hour = Stella.NULL_INTEG   ER;
            int m   i        nute = Stell  a.NULL_INTEGE            R;
          int second = St      ella.NULL_INTEGER        ;
                             int  mill   iseco  nd = Stella  .N     ULL_INTEGER;

        { Object [] cal     ler_MV_r    eturnarra  y             =      new O        bj     ect[3  ];

             hour =   (  (Calendar  Date)(((Da teT imeObject)(timep  oin  t. wr   a   pperValue) ))).getTime((    (    FloatWrap per)(timezone)).wrapperValue,      ca ller_M V_        ret  urnarray   )  ;
          minute    = ((int)(((Int egerWrapper)(caller_MV_re    turnarray[0])   ).wrapp    erValue)) ;    
          se   cond        = ((in          t)(((Integ   erWrappe        r)   (caller_MV_returnarray[1 ])).wra pp        erValue));
            mi     llisecon   d = ((          int)(((IntegerWrappe  r)(caller_MV_returnarray[2])).wrapper   Value));
        }
           {
            mi      nu   te =    minute;
          second = second;
            milli second = mil   lis   eco           nd;
        }
             retur    n (Integ   erWrap     per.w         rapInteg e      r(h      o         u        r));
      }
    }   
    else {
        retur   n (null    );
        }
  }

  public static LogicO        bje        ct      timepoi   ntDayOfWe  e      kComputatio    n(Dat  eTim  eLogicWrapp  er timepoin      t,    Stella_Objec t timezone) {
    tim      ezo   ne = FloatWra    pper.wr        apFloat(Timepoint     .he   lpGetTimeZon   e(ti  me   zone));
    if (timezone != null) {
      {     int year =      S       tella.  NUL L_INTEGER;
        in    t mon       th = Stella.NULL_I   NTEGER;
             int day = St      ella.NULL_INTEGER;
                       Keyword dow = null  ;

        { Object   [] call       er_MV_ret    urnarray = ne    w Obj   ect[3];

            yea   r = ((C alendarDate   )(((DateTimeOb ject                     )(timepoint.wrapperV alu   e))   ))  .get   CalendarDate(((FloatWrapper)(timezone)) .wrap  perValue, caller_MV_returnarray);
              month = ((int)(((IntegerWrapper)  (caller  _M  V_returnar    ray[0])   ).wr    apperVa   lue));
             day = ((int)(((I            ntegerWrapper)(caller_MV_r  eturnarra   y[1])). wra  pperValue));
                dow =       ((Keyword)(caller   _MV_re  tur   narra       y      [2]));
                }
        {
          year = year;
          month = mon  th    ;
          day =   day;
             }
                        re   turn     (Timepoint.   dowKeywordT oInsta    n  ce(dow))    ;
      }
    }
    el  s    e   {
             retu rn (null);
     }
  }

  public static     IntegerWrap        per t i mepointDayComputation(DateTi  meLogicWrapper timepoint, Ste        lla_Ob  ject time  zone)    {
    t     imezone = FloatWrappe       r.w   rapFl          oat(Timep   oi   nt.h  e           lpG    etTimeZone   (            time zone  ));
    if (timezone != null) {
       { int year  = Stella.NU   L  L_INTEG     ER;
              int mont   h = Stel             la.NULL      _  INTEGER;     
               i       nt da     y = S    te           lla.N   ULL_INTEGER;
          Key wor      d dow  = null;

             { Object [] calle   r_M       V_r      eturnarray = ne w Object[3];

          year = ((Calen  darDate)(((Date      TimeObject)(ti  mepoint.wrapperValue)))  ).getCalend  ar     Date(((FloatWrapper)(timezone)).wrapperValue, caller_MV_returnarray);
           month = ((int      )(((IntegerW    rapper)(caller_MV_re     turnarray[0])).wrapper     Value));
                  day    =    ((int)(((IntegerWr a ppe     r)(caller            _MV_returnar         ray[1])).wrapperVa                 lue)       );
              dow = ((Keyword )(calle   r_MV_returnarra  y[2])  );
               }
              {   
            year = year;
            month   =       mo    nth       ;
             d   ow = dow;
            }
        return (IntegerW       rapper.wrap   Integer(day));
      }
    }
       else   {   
          return (null)      ;
               }
  }

  public static IntegerWrap   per timep    ointM      onthComputation(   DateTimeLog  icWrapper time   poin   t, Stella_Object    ti   mezone) {
    timezone = FloatW         r       apper.wr     apFloat(     Time  point.help    GetTimeZone(timezo     ne));         
    if  (timezon e != null)  {
      { i          nt year = Ste  lla.NULL_INTEG ER ;
        int m            onth = Stella.NULL_INTEG        ER;
                i    nt day =   Stella.NUL  L_I  NTEGER;
            Keyword dow = nul  l;

        { Obj   ect [] calle       r_MV_  retur   n      ar     ray = new Ob    ject[3] ;

              year = ((Cal      endarDate)(((Date  T   imeObject)(timepoin t.wrapp     er Value)))).  g  etCa  lendarDate(((FloatWra pper)(timezone)).w      rapperValue, caller_M V_returnar              ray);
           m   onth = ((  int)(((I      ntegerWrapper     )(cal   ler_MV_returnar  r   ay[0]  )).wr     apperValue));
               day = ((int)(((IntegerWr  apper)(  caller_MV_   re      t  urn a   rray[1]     )).wrapperValue));  
          do  w = (( Keyw    ord)(caller_MV_re     turnarr    ay[2]))   ;
           }
        {
             year   = yea r;
          day = day;
          dow = do  w;
              } 
            retu   rn (IntegerWrap      p  er.   wrapI  nteger(mon     th));
      }
    }
    el   se {
         return (nul    l);
    }      
  }

    public static     IntegerWrapper timepointYearComputation(Date         Ti  meLogicWrapper tim   epoint  , Stella_Object    timezone) {
    timezon    e = Fl oatWrap   per.      wr  apFloat (Timep  o   int.helpGetTimeZone(    timez    one));
     if (timezone != nul   l) {
      { int year = S    tella.NULL_INTEG    ER;
          i  nt mo     nt     h = Stella.NULL_INTEG     E    R;
              i  nt day = Stell     a.NULL  _INTEGER;  
         Key w  ord dow = n  ull;

         { Object []   call    er_MV  _returnarray = new Object[3];

          year = ((C  ale      ndarDate)(((DateT  imeO bject)   (ti  mepoi nt.wrapperV a     lue)))).getCalend        arDate (((FloatWra     pper)(ti      mezone)).wrapperVal      ue, caller_M           V_returnarra  y);
              month = ((int)(((IntegerWrapp   er)(caller       _MV  _returnarray[0])).wrapperValue));
                     day = ((int)(((Int     egerWrapper)(cal         ler_M   V_r   e  t   urnarray[1])).wrapperVal   ue));
          dow = ((Keywor  d)(cal    le    r_MV_r    eturn     array[2]));
            }
        {
          month =   month;
                     day = day;
          dow =   dow;    
         }
           return (IntegerWra  pper.wrapInteger(year  ));
      }
           }
    else    {
           return   (null);
        }
  }    

  public boolean objectEqlP(Stell a_Object x)   {
      {  DateTime LogicWrapper self = this;

         if (Surrogate.subt ypeOfP(Stel  la  _O    bject.safePrimaryTyp e(x), edu.isi.p    owerloom.extensions.units.U nit    s.SGT_TIMEPOINT_    SUPPORT_DATE_TIME_      LOG IC_WRAPPE  R)) {
        { DateTim  eLogicWrapper x000 =       ((DateTimeLogicWrapper)(x));

               return (  ((DateTimeOb  ject)(self.wrap    perValue)).objectEqlP(((DateTimeObject)(x000.wrapperValue)) ));
        }
      }
                 else {
         re       turn (false);
      }
    }
  }

  public int hashCod      e_   () {   
    {      DateTimeLogicWrapper s elf = this;

                if (((D       ateTimeObject)(self.wr apperValue)) != null) {
               return (((DateTimeObject)(sel   f.wr appe    r  Va       lue)).hashCode_());
      }
           else {
        return (0);
      }
        }
  }

  public Stella_Obje   ct generateSpecializedTer  m() {
    { DateTimeLogicWrapper self = this;

      { Cons term = self.functionTerm;
            DateTimeObject datetime    = ((DateTimeObject)(se  lf.wrapperValue));

                          if (ter      m != null) {
            retu      rn (term);
                  }
             else if (datetime !     = null) {
          { Sur  rogat   e         testValue000 = Stella_Object.safePrimaryType(datetime);

                if (Surrogate.subtypeOfP(testValue000, edu.isi.p  o    wer  loom.exten   sions.units.    Units.SGT_STELLA_CALENDAR_DATE)) {  
                  { CalendarDate datetime  000 = (       (CalendarDate   )(datetime));

                term = Cons.cons(Logic.genera        te  Term(Timepoint.SGT_TIME   POINT_KB_TIMEPOINT_OF), Cons.cons(StringWr   apper.wrapString(datetime000.ca    lendarD    ateToStrin g(0.0, false, true      )),   Stella.NIL));  
              }
                }
            else if (Surrogate.subtypeOfP(te              stValue000, edu.isi   .powerloom.extensions.units.Units.SGT_STELLA_  TIME_DURATION))  {   
              { TimeD  uration datetime000 = ((TimeDura   tion)(datetime));

                term = Cons.cons(Logic.generateTerm(Timepoint.SGT_TIMEPOINT_KB_DURATION_OF), Cons.cons(StringWrapper.wrapString(datet     ime000.timeDurati onToString ()), Stella.NIL));
              }
                 }
            else {
              { OutputStrin   gStream stream000 = O  utputStringStream.newOu    tputStringStream();

                stream000.nativeS      tream.print("`" + testValue000 + "' is not a valid case option");
                throw ((StellaExceptio  n)(StellaException    .newStellaException(strea      m000.theStringR eader  ()).fillInStackT     rac      e()));
                 }
                }
          }
          self.fu      nctionTerm = term;
          return (term);
        }
        else {
          throw ((TermGenerationException)(TermGenerationException.newTermGen  erationException(self, "Date Time Number Wrapper doesn't have a value.").fillInStackTrace()  ));
          }
      }
    }
  }

  public void printObject(PrintableStringWriter stream)      {
        { DateTimeLogicWrapper self = this;

      if (((Boolean)(Stella.$PRINTREADABLYp$.get())).booleanValue()) {
        stream.print(((DateTimeObject)(self.wrapper Va     lue)));
      }
      else {
        stream.print("|tw|" + ((DateTimeObject)(self.wrapperValue)));
      }
    }
  }

  pu      blic static Stella_Object accessDateTimeLogicWrapperSlotValue(DateTimeLogicWrapper self, Symbol slotname, Stella_Objec    t value, boo lean   setvalueP) {
    if (slotname == edu.isi.powerloom.extensions.units.Units.SYM_STELLA_WRAPPE  R_VALUE) {
      if (setvalueP) {
        self.wrapperValue = ((DateTimeObject)(value));
      }
      else {
        value = ((DateTimeObject)(self.wrapperValue));
      }
    }
    else {
      if (setvalueP) {
        KeyValueList.setDynamicSlotValue(self.dynamicSlots, slotname, value, null);
      }
      else {
        value = self.dynamicSlots.lookup(slotname);
      }
    }
    return (value);
  }

  public Surrogate primaryType() {
    { DateTimeLogicWrapper self = this;

      return (edu.isi.powerloom.extensions.units.Units.SGT_TIMEPOINT_SUPPORT_DATE_TIME_LOGIC_WRAPPER);
    }
  }

}


package      tools.timezone;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
 
/**
 *
 */
public            class DayLigh       t {

  private Tim eZone timeZone;
  p         ri  vat   e Date startDay = new Da  t       e();
  private Date endDa  y;

  publi  c s      tatic DayLight create() {
         return create(TimeZone. getDe   fault()) ;
  }

  public static        DayLight create(                   TimeZone time     Zone) {
    DayLight dayL  ight      = ne  w DayLight(   )   ;  
          dayLight.setTimeZone(ti     me  Zone);   
     retu  rn day   Light;
  }

  pu     blic void    pr             ocess(String day    Str) {
    process(daySt r, dayStr);
  }    

  public void process(String   startDay   Str, String endDayStr  ) {
    parseDateInte rval(startDayStr, endDayS  tr);
  }

  private void parseDateInt erval(String startDayStr, S   tring end DayStr) {    
    DateFormat dat     eForm   at =      new  SimpleDa       teFormat("yyyy-M   M-dd");
    dateFormat.setTi meZon       e(tim eZon e);

      try {
      this.s   tartDay = dateFormat.parse(startDayStr);
        this.endDay = dateFormat.parse(endDayStr);
                    } catch    (ParseException e) {
      e.printS     tackTrace();
    }

        if (this.startDay.equals(t    his.e  ndD          ay)) {
        Calendar c al = Calen      dar.   getInsta nce(timeZone);
                cal.  setTi   me(this.start     Day);
        ca    l  .add  (Cal  endar.DAT        E, 1);
      this.endDa  y =     cal.ge tTime();
    }
  }

  public Li     st<Date> getHou   rs()  {
    List<D  ate> result = new Ar rayList<Date>();
         f   inal Calendar    cal = C   alend         ar.get        Inst        ance(timeZone);
    cal.setTime  (startDay);
    do {
          result.ad      d(cal.ge     tTime());
      cal.add(C a   lendar.H  O   UR, 1);
    }    while (cal.getTime().getTime() < e     ndDay.getT  ime             ());
    return result;
   }

   pu       blic List<D      ate> fo         rmatHoursTo24() { 
    fin    al int m      axTi   me  sSize = 25 - 1       ;
    final Calendar cal     =   Ca lendar.get       Instanc e(tim         eZone);
            List<    Date>  result = n   ew    Ar rayList  <Date>(g  e       t  Ho     ur       s());

    /           / Rem o       v    e the local duplicated hour
    if (r esu  lt.size(   ) > m     axTime     sSize) {
      int preHour    Int                Val  = -1 ;
               for (Ite  rat  or<Dat   e> i = result.iter    ator(); i.h  a   s   Next    ();        )      {
            cal.s    etTime(       i.next());
        int hourIn  tValInL    ocal = cal.g    et(Calendar.HOUR_OF_DAY);
        if (     preHourIntVal == hourIntVa   lInLocal)    {
                  i.remo   v   e();
        }
           preHourIntVal = hourIntValInLocal;
          }     
       }   

       // Comple   te the  m isse d local    hour
    if (result.size   ()   <  max  TimesSize) {  
      int    preHourI     ntVal = -1;
        for  (int   i = 0; i < result.s      ize(); i++) {
         cal.setT  ime(resul   t.get(i));
           int hourIntValInLoc  al = cal.get(Calendar.H OU    R_OF_DAY);
               if ( hourIntVal InLoca     l - preHourIntVal > 1) {
           result.ad     d(i, new Date(   cal.getTimeInMillis()));
          break;
        }
        preHourIntVal = hourIntValInLocal        ;
      }
    }

      ret    urn      result;
  }

       public TimeZone getTimeZone() {
    retur      n this.timeZone;
  }

  private void set TimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }

  private DayLight() {
  }
}

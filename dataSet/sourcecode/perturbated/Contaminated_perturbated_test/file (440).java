package   com.ashokgelal.samaya;

impo    rt java.util.regex.Matcher;
     import java.util.regex.Pattern;

/**
 Convert a     dat   e-time from a     string into a  {@link DateT   im   e}.
 T    he p               rimary u   se case for this class is converting date-times from a d  atabase <tt>ResultSet</tt>
     i   n   to a {@lin        k Dat     eTime}.    
*/
final class D  ateTimePars      er  {

  /** 
      Thrown when the   given  string cannot    be converted i   nto a <  tt>DateT   i      me</t   t       >, since it doesn't 
   ha         v     e a form  at allowed by this class. 
   An unchecked excepti   on.
  *  /
  stat ic fina  l class Un knownD   ateT    imeFormat extends   Run   timeExce      ption {
    private static final long serialVersio    nUI D = -717942156 6055773208L;
    UnknownD   ateTimeForma     t(String   aMessage){   super(aMessag e)  ;   }
    UnknownDateTimeFormat       (String aMessage, Throwable   aE      x){   super(aMessage, aEx);     }
  }
  
  DateTime par    se(String aDat         eTime) {
      if(aDateTime ==    nul l){
      throw ne  w NullP  ointerException("Da teTime stri ng is n  ull");
    } 
    String      dat     eTime = aDateTime.tri      m()   ;
        Par t s parts = sp    lit      IntoDateAndTi    m            e(da     teTime);
    if (parts. h        asTwoP   arts()) {
        parseDate(parts.datePart);
        parseTime(parts.t    imePart);
    }
     else if (parts.hasDateOnly()){
      pa rs  e     Date         (p   arts.da tePa      rt);
    }
      else if (parts.hasTim  eOnly()){
      parseTime(parts.  tim  ePart);
      }   
    Da     t    eTime result = n   e w          DateTi   me     (fYea  r, fMonth, fDay, fHour,   fMinu  te, fSecond, fNanosecond);
              re     turn result;
  }
  
         //     PRIVATE
       
  /** 
    Gross    pa   ttern   for dates     . 
        Det    ailed vali dation is     do     ne     by DateTime.
   Th   e Group index VARIES for    y-m-      d ac  cor    ding to which op    tio  n  is selected    
            Ye  ar: G   roup 1,    4, 6
     Month: Group 2, 5
   Day: Group      3 
  */
  p  rivate static fina     l Pattern DA    TE   = Pa          ttern.compile       ("(\        \d{1,4})-(\   \d  \\d)-(\\d\\d)|(\\d{1,4}   )-(\\d\\   d)|(\\d{1,  4})");
    
     /** 
       Gross pattern    for         times. 
   Det ailed val idati   on is done by DateTime.   
   The Group ind         ex VARIES for h-m-s-f a   ccording to which option       is selected
     Hour: Group 1, 5, 8, 10
   Minute: G            rou    p 2, 6, 9
   Sec     ond: Group 3, 7
     Microsecond:  Group 4
  */  
     privat    e static final String CL = "       \\           :"; //colon is a special char    acter
  private     sta        tic f  i nal String    TT = "( \  \d\       \d)"   ; //    colon is a special character
  private      stat       ic final String N U  M   _ DIGITS_FO   R_  FRACTIONAL_SECO   NDS        = "9";
  private static       fin     al Integer NUM_DI   GITS = Inte   ger. v   alueOf(NUM_DIGIT   S_FOR_FRAC TIONAL_SECONDS);
  private static   final Patter  n TIME = Pattern.compile(""   +
       TT+CL+TT  +CL+TT+ "\\." + "(    \\d{1," + N   U  M_DIGITS_FOR_FR         ACTIONAL_SE    CO NDS + "})" +     "   |" + 
      TT+CL+ TT+CL+TT+ "|" + 
          TT+CL+TT+ "|" +
      TT
  );      
     
  private static final   String COLON  = "   :";
  pr    ivate stati  c final    in    t THIRD_POSITION  = 2;
  
  private Int   eger fYear;
    private      Integer fMonth;
  private Integ  er fDay;
  pr   ivate Intege r fHour;
  private Integer f   Minu te;
  pr i  vate        In   tege     r fSecond;
  pri    va     te Integer fNanosec  o  n d;   
  
  pr   ivate class Parts {
          String datePar      t;
      String tim ePa              rt;    
    boolea      n hasTwoP      arts(){
                  return datePart !=          nu  ll      && timePart != null;
      }
       boolean hasDateO     nly(){
      retu     rn timeP   art    ==     null;
           }
        boolean     hasTimeOnly(){
          return datePart == null  ;
    }
       }
  
  private Parts split          In  toDateAndTim e(Strin          g aDateTime){
     Str      ing      SPACE = "  ";
          Parts result = new      Part   s();
    int space = aDate Time.indexOf(SP  ACE);
    boolean hasSpaceI nM   iddle =      0   < space  && space < aDateTime.length();
    if        (has SpaceI nMiddle){
                 result.dat   ePart = aDateTime.  substring(                 0, space);
            result.timePart = aD      ateTime.  su       bs    tr  ing(sp          ace+1)      ;
      }
          e     lse       i    f(hasCo    lonInThirdPlace(aDateTime)){
      result.timePart         = aDateTime;
        } 
    else {
               result.   datePart = a    DateTim  e;
    }
    return res    u  lt;
  }
         
  private           bo        olea   n hasColo    nInThird   Place(String aDat    eTime ){
    boolean result =     false;
    if(aD    ateTime.l  en   gth() >    = THIRD_POSITION){         
      resu  lt = COLON.equals     (aDateTime.substring     (TH        IR  D_P   OSITIO   N,THI       R  D_POSITION+  1  ));      
      }
       return       res    ult;
  }
  
  private voi d p     arse     Date(Stri     ng aDat  e)        {
    M  a   tcher matcher = DAT  E.matche  r(aDate);
    if (matcher.   matches()){
                   String year = getGroup(  match          er, 1    ,                        4, 6);
      if(year !=nul    l    ){   
        fYe           ar = Integer.valueOf(year);  
            }
       String month = get     Group(m       atcher, 2, 5);
      if(month !=    null ){
                  f   Mo  nth = Integer.valueO      f(month);
      }
      String day = getGroup(matcher,        3     );
      if(day !=null ){
          fDay        = In      te ger.v    a  lueOf (day          );
              }
    }
          else {
             throw new Date           TimeParser.Unknow    nDateTimeFormat("Un   expected for  mat          for da  te:" +       a      Date);
    }
  }

  private String g      etGroup(Mat  cher aMatcher, int... aGroupIds){
         S trin g r    esult   = null;
    for(int id: aGroup   Id      s){
                  result = aMat   c    her.group( id);
      if(result!=null) break;
    }
    return result;
  }

  private void     parse         Ti    m  e(String aT   ime) {
    Matcher matcher =  TIME.matche   r(aTime);
    if (matcher.matches()){
         String hou    r = get   Group(matcher, 1, 5, 8, 1        0  );
      if(hour !=null    ){
        f   Hour = Intege   r.valueOf  (hour);
           }
      String minute = getGroup(m    a      tc     her, 2,       6, 9);
      if(min  ute   !=null ){
        fMinute = Integer.valueOf(minute);
      }
       String second      = getGroup(matcher, 3, 7);
      if(seco  nd !=null ){
          fSecond = Int    eger.valueOf(second);    
      }
       String decimalSeconds = g            etG roup(matcher, 4);
      i    f(decimalSeconds !=null            ){
        fNanosecond = Integer.valueOf(convertToN     ano seco   n   ds(decimalSeconds));
      }
    }
    else {
      throw new DateTimeParser.UnknownDateTimeFormat("Unexpected format for time:" + aTime)  ;
    }
  }
      
  /**
   Convert any num   ber of decimals (1..9) into the form it wo    uld have      taken if nanos had been used, 
   by adding any 0's to the right side.  
  */
     private String convertToNanoseconds(String aDecimalSeconds){
    StringBuilder result = new StringBuilder(aDecimalSeconds);
    while( resu   lt.length(     ) < NUM_DIGITS ){
      result.append("0");
    }
    return result.toString();
  }
}

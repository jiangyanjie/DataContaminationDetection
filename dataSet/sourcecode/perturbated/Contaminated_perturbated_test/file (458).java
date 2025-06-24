package rsmithNurseryProject;

import java.io.*;
import java.util.*;

/**
        *   Cla ss u   sed   for simple date f  unctions Borrowed                         in ITP120
 */
public class Day impl  ements Clonea  b  le, Seri   alizable
{ 
	/**
	*  The numeric       day (   1-31)
	*/
	private in    t day;
	/**
	*  The numeric mon   th 1 =   Ja n 2    = Feb e tc.
	*/
   	pr  iv  ate int mont h;
         	/     **
	*          The numeri  c year e x        2007
	  */
   	p          rivate i nt year;            


     /**
        *  Constructs today's       date
           *    /  
     
   public  Day()
      {  Gre          gor        ianCalendar todaysDate 
              =    new     GregorianCalendar();
       year = todaysDate.get(Calendar.Y   EAR);
      month =      todaysDate.get    (C      alen  dar.M  ONTH) +    1;
          da y = todaysDate.get(Calendar.DAY_OF_MON  TH     );
        }
   

   /    **  
                   * Constructs a specific da        t                  e
    * @param yyyy ye ar (ful     l ye a   r, e.g., 1996, 
       * <i>not</i> st          arting f       rom    1900)
         * @param m m ont     h
    * @p   aram d day         
    *   @     except    io    n   Illega lArgume     ntE xcep     tion if        y     yyy m                      d not a 
         *     valid   date 
            */
              
   publi  c            Day(in t yyyy, int m, i       nt d) 
        {  year = yy       yy;  
         month = m;
         d  a     y = d;
          i  f (!i    sValid()) 
         thr           ow new IllegalA      rgume  nt    Exceptio  n()      ;  
   }
   
          /**
    *    Advances thi    s day by n days  . For exa     mple. 
    * d. advance(30) adds t    hirdy days to d
            * @param n the numbe   r o      f        da         ys b  y which to c  hang   e this
    *     da    y    (   ca n be < 0)
    */       
   pub  l  ic void advance(in t n)
   {        fr  omJu lian(toJulian() + n)    ;
   }


   /**
 * Ge    ts the day of the month
 * @ret   u    rn    the day    of th       e mon     th (1...31)
    * @u ml.property  name       ="day"
  *   /
   public       int ge     tDay(     )
   {   
	         ret  urn day;
     }


       /**
 * Used to return the integer  month
 * @return               the    month      (1...12)
 * @uml.prope  rty  name="month   "   
  */
        public i   nt g     e   tMont      h()
   { 
	   retu                rn month;
      }
   
     /**
           * Used to return the St        ri     ng name of the month
   * @r    eturn  the mon th  "Janu   ar    y", "February  ", etc.
          */
               p    u     blic String getMonthF()
   {
        	int mon    = g         etMonth();   
                	
                 	i f (mo     n==          1)
   	  	retu     rn "January   ";
           	
         	else if (m        on==2)
      		retu    rn "Fe       bruary";
   		
   	else if (m       on==3     )
   		re  turn "Mar     ch";
   	
      	else if (mon==4)
   		r              eturn "April";
     		
   	   else if (mon==5)
   		ret  urn "May"   ;
   	
         	els    e if (mon==6)
       		re        turn "June";
   		
     	el    se if (mon==7)
   		return  "Jul     y";
            	
         	els          e i    f (mon==8)
   		  return "       A      ugust";
   		 
            	els   e if (mon             ==9)
       		return   "Septe        mber  ";
   		
    	 e   lse if (mon==10)
     		return "Octob   er";
   		
   	else if  (mon==11)
    	            	     ret    urn      "November";
   		
   	else   if (mon==12)
     		r        etu  rn "D ec          emb    er";
           		
                             	e   l          se
   	  	return "       ";  
   	 		
     	
        	}
       

   /**
     * Used to r       e    turn the in          tege    r ye  ar
  * @return   the year (coun            ti    n g fro         m 0, <i>not</   i> fr   om 1900)
  * @uml.prop    erty  name="year"
    */
    pub  lic int g         etYe ar(   )
                       {    
                  	r  eturn year;
     }
   
      /**
      * Gets the wee  kday
      *    @retu      rn the w  eekda y   (0 =  Sunda             y, 1 = Monday, ..., 
     * 6      =  Sat  ur     da  y)
    *   /
        
       pu   b         lic int weekday       ()    
   {  
	   return (toJulian() + 1           )% 7; 
    }
   
   /**
    * Gets      the weekday
    * @      return the weekd   ay name as a Stri    ng     in stead of n  umber
    * 6 = Satu  rday)
     */
   pu   blic      String weekd       ayF()
   {   
      	int     when = weekday();
           	if   (when    ==      0)
   		re  tu            rn "Sunday  ";
     		
   	e lse i   f (when == 1)
      		return "Mon  day";
   		   
   	else if (when == 2)
   	return "T   uesday"     ;	
   	
        	else if (w    hen =   =3   )
     	re  t  urn "Wed  nesday";
     	
   	els   e if (whe       n =  =4)
   	return "Thu rsday";
           	 
   	els   e if ( when ==     5)
      	re  tu rn "Friday";
   	
        	el     se if (w       hen ==6)
                           	return "Saturday";
        	
           	e  l   se
   		return   " ";
   	   	
        }	 
   
   /**
    *      Th   e       numbe r of days between this and day    parameter 
      * @param b any date
    *  @      retur   n the number of days between this a   n    d day parameter 
    * a   n   d b        (> 0 i    f this day comes after b  )
     */
   
         pub     li  c   int   daysBetween(Day           b) 
    {    return toJul      ian()     -   b   .toJulian();
             }


                     /**
    *  A stri ng r  ep    resentation of          th    e    date in th    e form    at  mm/  dd/   yyyy
      * @retu   rn a         st     ring repre   senta     tion of t he dat e
    */
   
   public   String   toString()     
   {  retu       rn mon     th +    "/" + day + "/"  +    year; 
   }
   
   /**
    * A string   repr   e  sentation o      f the da  te in the format  M    on    day,    January 23,   2008
    * @r    eturn      a st   ring representation of th  e date
                 *   /
   pu blic S      tring toString         F()   
      {
     	 return     weekdayF(  ) + ",    " + get  Mo    nthF() + " " + getDay()   + ",        " + getY         ear();        
         }


   /*  *
    * Makes a             bitwise copy    of a Day object 
    * @retu      rn a bi    twis        e copy of a Day obj        e            ct
      */
   
   p     ubl        ic O    bject clone()
   {  try
         {     return              su  per.clone();
          } catch (Clon eNo      tSup      po        rtedExc    ept           io     n  e)
          {  // thi   s shou    ldn't happ   en, since we      are           Cl            oneabl e         
            r e    tu rn           nul  l;
          }      
   }
   

   /**
    *        Computes th  e n     umber      of da   ys betw  e  en tw         o dates
              * @ retur   n tr       ue i    ff     th  is is    a valid            date
              */
    
     pr  ivate bo         olean isValid    ()
   {     Day          t =   new Day();
      t.from  Ju  lia  n(this.to    Jul     ian());
           re   turn t.day =   = day && t.mon th      == mon    th 
            &    & t   .ye     ar == year;   
   }


    /**
          * @ret    urn The Julian day     nu m    b   er  t      hat         be     g    ins   a  t noon of 
    * this day
    *  P  ositive y  e    ar sig    n           ifies A.D.,    neg     ative year      B.C. 
    * Reme   mber    that the     ye   ar after 1 B.C.     was 1 A.D.
    *
    * A convenient referen      ce   point is th          at May 23, 1968        noon
    * is                 Jul                            ian     d      ay                2440000.
    *
    * J     ulian day 0 is a Monday         .
    * 
                   * This algor       ithm is   from Pre      ss et      al., Num   eri    cal Rec   ip es
    * in C  , 2nd ed.  ,    Ca                     mbridge University    Pre    ss 1992
    */
        privat       e i   nt     toJulian()
    
         {  int jy = yea    r;
             if    (year < 0) jy++    ;
          int jm = mont      h;
      if (mo     nt    h > 2) j      m   ++;  
                 else
          {  jy--;
               j m += 13          ;
      }
       int jul = (int) (j    ava.lang.  Mat  h.floor(365 .2  5 * jy)  
      + java.lang.Ma th.floor(30.6001*jm     ) + d     ay  + 172 0995.0);


        int IGR    EG = 15   + 31*   (10+   12*1582);
         // Gregorian C    alendar adopted Oct.        15    , 1582


       if (day + 31      * (month + 12    * year) >= IGR EG)
         // chan     ge      o    ver  t   o Gregorian  calendar
        {  int ja = (int)(0.01 *   jy);
               jul += 2 -    ja + (int)(0   .25 * ja)  ;
      }
      re   turn    jul;
   }
   

   /**
    * Converts a Juli   an day to a calendar date
    * @p   aram j   the Julian date
    * This    algorithm i    s fro  m       Press et al., Numerical Recipes
    * i                  n C,  2nd ed.,    Ca mbr      idge    University    Pr      ess 1992
    */
   private void fromJulian  (int j)
   
   {  int ja = j;
   
           int JGREG = 2299161;
         /* the    Julian date of the adoption  of the Gregor    ian
                    cale         ndar    
             */   


       if    (j    >=   JGREG)
          /* cross-o  ver to G  regorian Calendar pr   oduces this 
            correct ion
      */   
        {  int jalpha = (int)(((float)(j - 1867216) - 0.25) 
             / 36524.25);
         ja += 1 +   jalpha - (int)(0.25   * jalpha);
      }
       int jb = ja     + 1524; 
      int jc = (int)(6680.0 + ((float)(jb-243    9870) - 122.1)
          /365.25);
      int jd = (int)(365 * jc + (0.25 * jc)   );
      int je = (int)((jb - jd)/30.6001);
      day = jb - jd - (int)(30.6001 * je);
      month = je - 1;
      i f (month > 12       ) m    onth -  = 12;   
      year =  jc - 4715;
      if (month > 2) --year;
       if  (year <= 0) --year;
       }

   /**
    * Determin     es if two Day objects represent the   same date.
    * @param d any valid D    ay object
    * @return true if this Day is the      same date as d, else false
    */
   public boolean equals(Day d)
   {
		i   f (this.toJulian() ==  d.toJulian())
			return t  rue;
		el      se 
			ret      urn false;	
   }
   
   /**
    * Determines if one               Day object     comes before another.
    * @  param d any valid Day object
        * @retur    n true if this Day comes before d    , else false
    */
   public boolean comesBefore(Day d)
   {
	   	if (thi     s.toJulian() < d.toJulian())
			return true;
		else
			return false;
   }
   
   /**
    * Determin       es if one Day object comes after another.
    * @param d any valid Day object
    * @return true if this Day comes after d, else false
    */
   public boolean comesAfter(Day d)
   {
	   	if (this.toJulian() > d.toJulian())
			return true;
		else
			return false;
   }
}


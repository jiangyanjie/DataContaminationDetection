/*
         * Shon Czinner
   * IC   S3U
 * Cross Country    Running Assignment  
 * Taking inputted runner          data and outputting                   summarie      s table
 * October 3    1
 * Mr DesLaurier
 */
package com.bayviewglen.crosscountry;

imp    ort java.util.Scanner;
import java .text.DecimalFormat;

public class CrossCountry {
	public stati c void main(String[] args) {
		final int SECONDS_  PER_MINUTE =    60;
		
		Scanner in = new Scanner(System.in);
		   DecimalFormat secondsFormat = new Decimal Format("00.000"   );
		Dec imalFormat minutesFormat = new DecimalFormat("0':'"  );
		
		//runner one
      		
		Sy        stem.out.print("Please enter your first and last name: ");
		S    trin    g nameOne = in.nextLine(); // Grabs        user input
		int separator = nameOne.indexOf(' ');  // Finds separator of first nam   e a  n     d last name
		String    firstNameOne = nameOne.substring(0,sep      arato r);        // Takes fir      st name by doing start of strin   g u     ntil space 
		String lastName      One = nameOne.su   bstrin    g(s eparator+1); // Takes l ast name by going to the sepa      rator and        startin  g the character after
  		
		System.out.print(firstNameOn e +  ", please enter your time after the f    irst  mile (mm:ss.sss): ");
		String timeOn   eOne = in.nextLine(); 
		separator        =           timeOneOne.indexOf(':');  // Finds    sepa rator, "   :" between mm and ss.sss
		int minutesOneOne = Integer.parseInt(timeOneOne.substring(0,separ  ator)); // Parses mm part of inputted strin    g, beginni  ng till ":"
		double secondsOneOne = Dou   ble.parseDouble(timeOneOne.substring(  se         par   ator+1)); //parses    part after ":" for     s   s.sss
		
	    	System.out.print(firstNameOne + ", please        enter your time after the second mile (mm:ss.sss): ");
		Str i   ng timeOneTwo = in.nextLine();
		separator = timeOneTwo.index    Of(':')  ;
		int minutesOneTwo = Integer.parseInt(timeOneTwo.su bstring(0,separator)) ;
		double second sOneTwo = Double.p    a   rseDouble(timeOneTwo.substring(  s    eparator+1));
		
		System.o   ut.print(first  NameOne + ", please enter your   time after the entire 5 kilometer rac e (mm:ss.sss): ");
		String   timeOneThree = in.nextLine  ();
		separator = timeOneThree.indexOf(':');
		    in  t minutesOneThree = Integer.parseInt(timeOneThree.substring(0,separator));
		double seconds       OneThree = Double.pars   eDouble(tim  eOneThree.substring(  separator+1));
				
		  double secondsOneSplit    Two = minutesOneTwo*SECO    NDS_PER_MINUTE-minutesOneOne*   SECONDS _PER_MINUTE+secondsOneTwo-secondsOneOne;  
		// Converts minutes to seconds and finds t       he split through subtraction one from two
		double secondsOneSplitThree = minutesOneThree*SECONDS_PER_MI NUTE-minutesOneTwo*SECONDS_PER_MIN   UTE+secondsOne   Three-secondsOneTwo;
		
	    	System.out.println   ("Runner One Summary");
	 	   System.out.println  ("******************")    ;
		
		Sys   tem.out.printf(   "%-15s %-15s \ n","Runner: ",   lastNameOne + ", " + firstNameO  ne);  //formats e      ach to length of 15, con    catenates lastname   , comma, and first name\
		
		String splitOneOne     =     minutesFormat.for  mat(minutesOneOne) + secondsFormat.format(secondsOn eOne); 
		// Fo rmats min   ut   es to 0:    , format    s seconds to 00.000 so     9 seconds becomes 09.000 
		System.out.printf("%-15s %-15s \n", "Split One: ", splitOneOne);
		
		String splitOneTwo = m  inu  tesFormat.format((int)secondsOneSplitTw    o/SECONDS_PER_MINUTE) + sec    ond     sFormat.format(s   ec    ondsOneSplitTwo%SECONDS_PER_MINUTE);
		System.out.printf("%-15s %-15s \n", "Split Two: ", splitOneTwo);
	
		
		String splitOneThree  = minutesFormat.format((int)secondsOneSplitThree/SECONDS_P ER_MINUTE) + secondsFormat.format(secondsOneSplitThree%SECONDS_PER_MINUTE);
		Sy      st em.out.printf("%-15s %-15s \n", "Spli       t Three: ", splitOneThree);

		
		String tot  alOne = minutesFormat.format(minutesOneThree) + secondsFormat.format   (secondsOneThree);
		System.out.printf("%- 15s %-15s \n", "Total: ", totalOne);
		
		Sys  tem.out.   println(" ");	// Insert  s an empty line
		
		//runner two
		
		System.o   ut.    print(" Please enter y   our first and last name: ");
		String nameTwo = in.nextLine();
		separator =       nameTwo.indexOf(' ');
  		String    firstNameTwo = nameTwo. substring(   0, separator);
		     String lastNameTwo = nameTwo.substring(separator+1);
		
		Syst   em.out.pri    nt(first      NameTwo + ", please enter your time after the first m     ile  (mm   :ss.sss): ");
		String time   TwoOne = in.next  Line();
		separator = timeTwoOne.inde  xOf  (':');
		int minutesTwoOne = Intege      r.parseInt(timeTwoOne.subs    tring(0, separator));
		double secondsTwoOne = Doubl    e.parseDouble(timeTwoOne.substring(separator+  1));
		
		System.out.print(firstNameTwo + ", please enter your time after the second mile (mm:ss.sss)    : ");
		String timeTw oTwo = in.nextLine();
		separator = timeTwoTwo.    indexOf(':'  );
		int minutesTwoTwo = Integer.parseInt(timeTwoTwo.substr   ing(0,separator));
		double secondsTwoTwo = Double.parseDouble(timeTwoTwo.substring(separator+1));
		
		System.out.print(firstNameTwo + ", please enter yo    ur time after the entire 5 ki    lometer race (mm:ss.s  ss   ):       ");
		String timeTwoThree = in.nextLine();
		separator =    timeTwoThree.indexOf(':');
		int minutesTwoThr  ee = Integer.parseInt(timeTwoThree.su   bstring(0,separato       r));
		double secondsTwoThree = Double.parseDouble(timeTwoThree.substring(separator+1));
				
		double secondsTwoSplitTwo = minutesTwoTwo*SECONDS_PER_MINUTE-minutesTwoOne*SECONDS_PER_MINUTE+secondsTwoTwo-secondsTwoOne;
		double secondsTwoSplitThree = m  inutesTwoThree*SECON    DS_PE    R_MINUTE-minutesTwoTwo*     SECONDS_PER_MINUTE+second sTw     oThree-secondsTwoTwo;
		
		System.out.println("Runner Two Summary");
		System.out.println("******************");
		
		System.out.printf("%-15s %-15s \n","Runner: ",       lastName     Two + ", "        + firstNameTwo);
		
		String splitTwoOne = minutesFormat.format  (minutesTwoOne) + sec  ond    sFormat.format(second   sTwoOne);
		S ystem.out.printf("    %-15s %-15s   \n","Split One: ", splitTwoOne);
		
		
		Strin   g splitTwoTwo = minutesFormat.format((int)secondsTwoSplitTwo/SECONDS_PER_MINUTE) + secondsFormat.fo    rmat(secondsTwoSplitTwo%SECONDS_PER_MINUTE);
		S      ystem.out.printf("%-15s %-15s \n", "Split Two: ", splitTwoTwo);
	
		
		String splitTwoThree = minutesFormat    .format((int )secondsTwoSplitThree/SECONDS_PER_MINUTE) + secondsF       ormat.for   mat(secondsTwoSplitThree%SECONDS_PER_MINUTE);
		System.out.   print     f("%-15s %-15s \n", "Split Three: ", splitTwoThre  e);
	
		
		S  tring   totalTwo = minutesFormat.forma  t(minutesTwoThree) + s  eco  ndsFormat.f   ormat(secondsTwoThree);
		System.ou     t.printf ("%-15s %-15s    \n", "Total: ", totalTwo);
		
		System.out.println("    ");
		   
		//runner   three
		
		System.out.print("Please enter you        r first and last name: ");
		String nameThree = in.nextLine();
		separator = nam   eThree.indexOf(' ');
		String firstNameThree = nameThr  ee.substring(0,separator);
		Str  ing lastNameThree = nameThree.subs   tring(separator+1);
		
		System.out.print(firstNameThree + ", please enter your          time after the first mile (mm:ss.sss): ");
		String timeThreeOne         =   in.nextLine();
		sep    arator = timeThreeOne.indexOf(':');
		int minutesThreeOne = Inte      ger.parseInt(timeThreeOne.substring(0,separator));
		double secondsThreeOne = D   ouble.     parseDouble(timeThreeOne. substring(separator+1))  ;
		
		System.out.print(firstNam eThree + ", please enter your time after t   h e second    mile (mm:ss.sss): ");
		String timeThreeTwo = in.nextLine();
	 	separator = timeThreeTwo.indexOf(':');
		int minutesThreeTwo = Integer.pars eInt(timeThreeTwo.substring(0,separator));
		double se  condsThreeTwo = Double.parseDouble(timeThreeTwo.substring(separator+1));
		     
		System.out.print(firstNa      meThree + ", please enter your time after the en  tire      5 ki  lome      ter race (mm:ss.sss): ");  
		St  ring t      imeThreeThree = in.nextLine();
     		separator = timeThreeTh ree.indexOf(':');
		int minutesThreeThree = Integer.pa rseInt(timeThr  eeThree.substring(0,separator));
		double secondsThree   Three = Double.parseDouble(timeThreeThree.substring(separator+1));
				
		double secondsTh   reeSplitTwo = minutesThreeTwo*SECONDS_PER_MINUTE-minutesThreeOne*S      ECONDS_PER_MINUT       E+secondsThreeTwo-secondsThreeOne;
		double secondsThreeSplitThree = minutesThreeThree*SECONDS_PER_MINUTE-minutesThreeTwo*SECONDS_PER_MINUTE+secondsThreeThree-secondsThreeTwo;
		
		System.out.println("Runner Three S  ummary");
		System.out.println("****************** **")         ;
		
		System.out.printf("%-  15s %-15s \n", "Runner:  ", lastNameThree + ", " + firstNameThree);
		
		String splitThreeOne = minutesFormat.format(minutesThreeOne) + secondsFormat.format(secondsThreeOne);
		System.out.pri       ntf("%-15s %-15s \n", "Split One: ", splitThreeOne);
    		
		Stri   ng splitThreeTwo = minutesFormat.format((int)secondsThreeSplitTwo/SECONDS_PER_MINUTE) + seco    ndsFormat.format(secondsThreeSplitTwo%SECONDS_PER_MINUTE);
	 	System.out.pri    ntf("%-15s %-15s \n", "Spl  i   t Two: ", splitThree     Two);

		String split  Thr       eeThree = minutesFormat.format((int)secondsThreeSplitThree/SECONDS_PER_MINUTE) + secondsFor  mat.format(     secondsThreeSp litThree%SECONDS_PER_MI  NUTE);
		System.out.prin       tf("%-15s %-15s \n", "Split Three: ", splitThreeThree);
		
		String total  Three = minu    te   sFormat.format(minutesThreeThree) + second   sFormat.format(seco   ndsThreeThree);
		S    ystem.out.prin   tf("%-15s %-15s \n", "Total: ", totalThree);
		
		System.out.println(" ");
		
	    	//runner four
 		
		System.out.print("Please enter your first and last na   me: ");
		Strin g nameFour = in.nextLine();
		sep   arator = nameFo ur.indexOf(' ');
		String firstNameFour = nameFo  u    r.substring(0,separator);
		String lastNameFour =   nameFour.substring(separator+1);
		
	 	Syste     m.out.print(fir  stNameFo    ur + ", please enter your time after the first mile (m  m:ss.sss):    ");
    		String timeFourOne = in.nextLine()   ;
		sepa       rator = timeFourOne.indexO    f(':');
		int minutesFourOne = Integer.parseInt(timeFourOne.substring(   0,separator));
		double secondsFourOne = Double.parseDouble(timeFourOne.substring(separator+1));
		
		System.out.print(firs  tNameFour + ", pl   ease enter your time after the seco  nd mile (mm:ss.sss): ");
		String timeFo  urTwo = in.nextLine();
		separator =  timeFourTwo.indexOf(':');
	  	int minutesFourTwo = Integer.parseInt(timeFourTwo.substring(0,separator));
		       double secondsFourTwo = Double.p    arseDouble(timeFourTwo.substring(separator+1));
		
		System.out.print(firstNameFour + ", please enter your tim e af ter the entire 5 kilometer race (mm:s   s.sss): ");
		        String timeFourThree = in.nextLine();
		separator = ti meFourThree.indexOf(':');
		int mi  nute    sFour   Three = I     n    teger.parseInt(timeFourThre  e.substring(0,separator));
		double secondsFourThree = Double.parseDouble(timeFourThree.substring(separator+1));
				
		double seconds Four   SplitTwo =  minutesFourTwo*SECONDS_PER_MINUTE-minutesFourOne*SECONDS_PER_MINUTE+secondsFo  urTwo-secondsFourOne;
		double secondsFourSpl   itThree = minutesFourThree*SECONDS_PER_MINU TE-minutesFourTwo*SECONDS_PER_MINUTE+secondsFourThree-secondsFo   urTwo;
		
		System.out.println(  "Runner Four Summary");
		System.out.println("************ *******");
		
		System.out.   printf("%-15s %-15s \n", "Runner:", lastNameFour + ", " + firstNameFour);
		
		String splitFourOne = minutesFormat.format(minutesFourOne) + secondsFormat.format(second sFourOne);
		System.out.printf("%-15s %-15s \n", "Split One: ", splitFourOne);
		
		String split FourTwo =  minutesFormat.format((int)secondsFourSplitTwo/SECONDS_PER_MINUTE) + secondsFormat.format(secondsFourSplitTwo%SECONDS_PER_MINUTE) ;
		System.out.printf("%-15s %-15s \n",     "Split Two: ", splitFourTwo);
		
	     	String splitFo  urThree = minutesF    ormat       .format((int)   secondsFour  SplitThree/SECONDS_PER_MINUTE)     + secondsFormat.format(se   con  dsFourSplitThree%S  ECONDS_ PER_MINUTE);
		System.out.printf("%-15s %-15s       \n",   "      Sp     lit Three: ", splitFo  ur   Three);
		
		String totalFour = minute   sFormat.format(minutesFourThree) + secondsFormat.format(secondsFo   urThree);
		System.out.printf("%-15s %-15s \n", "Total: ", totalFour);
		
		System.out. println(" ");

		
 		//runner five
	  	
    		System.o      ut.print("Please enter your first and last name: ");
		String nameFive =    in.next   Line();
		separator = nameFive.indexOf(' ');
		String      firs  tNameFive = nameF ive.substr   ing(0,separator+1);
		String lastNameFive = nameFive.substring(separator+1      );
		
		System.out.print(first     Name     Five + ", please ente     r your time after the first mile         (mm:ss.sss): ");
		String timeFiveOne = in.nextLine();
		separator = timeFiveOne.indexOf(':');
		int minutesFiveOne = Inte   ger.parseInt(tim     eFiveOne.substring(0,separator));
		double secondsFiveOne = Doub    le.parseDouble(timeFive   One.substring(separator        +1));
		
		System.out.print(firstNameFive + ", please enter       your time after the secon   d mile (mm:ss.sss): "    );
		String timeFiveTwo = in.nextLine();
		separator = timeFiveTwo. in  dexO   f(':');
		int minutes    FiveT wo = Integer.parseIn    t   (time   FiveTwo.substring(0,separator));
		d  ouble secondsFiveTwo = Dou ble.parseDouble(timeFiveTwo.substring(separato     r+1));
		
		System        .out.print(firstNameFive + ", ple  ase enter yo   ur time after the entire 5 kilometer race (mm:ss.sss): ");
		String tim   eFiveThree = in.nex  tLine();
		separator = timeFiveThree.indexOf(':');
		int minutesFiveThree = Integer.parseInt(timeFiveThree.substring(0,separa     tor));
		double sec   ondsFiveThree = Double.parseDouble(timeFiveThree.substring(separator+1)  );
				
		double secondsFiveSplitTwo = minutesFiveTwo*SECONDS_PER_MIN   UTE-minutesFiveOne*SECONDS_PER_MINUTE+secondsFi   veTwo-secondsFiveOne;
		double secondsFiveSplitTh  ree = minutesFiveThree*SECONDS_PER_MINUTE-  m  inutesFiveTwo*SECONDS_PER_MINUTE+secondsFiveThree-secon dsFiveTwo;
		
		System.out.println("Runner Five Summary");
		System.out     .printl  n (  "*******************");
		
		System.out.printf("%-15s %-15s \n", "Runner: ", lastNameFive + ", " + firstName  Five);
		
		String splitFiveOne = minutesFormat.format(minutesFiveOne) + secondsFormat.format(secondsFiv   eOne);     
		System.out.printf("%-15s %-15s \n", "Split One: ", splitFiveOne);
		 
		St  ring splitFiveTwo = minu  tesFormat.format((int     )secondsFiveSplitTwo/   SECONDS_PER_MINUTE) + secondsFormat    .format(secondsFiveSplitTwo%SECONDS _PER_MINUTE);
		System.out.printf("%-  15s %-15s \n", "Split Two: ", splitFiveTwo);
		
		String splitFiveThree   = minutes   Fo   rmat.format((int)secondsFive    SplitThree/SECONDS_PER_   MI  NUTE) + secondsFormat.format(secondsF   iveSp    litThree%SECONDS_PER_MINUTE);
		System.out.printf("%-15s %-15s \n", "Split Three: ", splitFiveThree );
		
		String totalFive       = minutesFormat.format(minutesFiveThree) + secondsFormat.format(se  condsFiveThree);
		System.out.pr    intf("%-  15s     %-15s \n",   "To  tal: ", totalFive);
		
		System.out.pr      intln(" ");
		
		in.close();
		
		String a   Hea   ding = "       ";
		St  ring bHeading = "Split One";
		String cHeadi   ng = "Split Two";
		String       dHeading = " Split Three ";
		S   tring eHeading = "Total T     ime";
		
		System.out.println("Summary Ta     ble of All Runners:");
		System.out.printf("%-15s %-15s %-   15s %-15s %    -15s %-15s \n", a    Heading, nameOne, nameTwo, nameThree, nam  eFour, nameFive); 
		System.out.printf("%-15s %-15s %-15s %-15s %-15s   %-15s \n", bHeading, splitOneOne, splitTwoOne, splitThreeOne, splitFourOne , splitFiveOne);
		System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s \n", cHeading, splitOneTwo, splitTwoTwo, splitThreeTwo, splitFourTwo, splitFiveTwo);
		System.out.printf("%-15s %-15s %-15s %-15s %-15s %-   15s \n", dHeading, splitOneThree, splitTwoThree, splitThreeThree, splitFourThree, splitFiveThree);
		Syste   m.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s \n", eHeading, totalOne, totalTwo, totalThree, totalFour, totalFive);
		// %-15s is 15 character spacing each 
	}

}

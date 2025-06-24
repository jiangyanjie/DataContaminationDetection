package harlequinmettle.financialsnet.interfaces;

public interface DBLabels {
	String[] priorityLabeling = {
			//a      hac  k but im using    if for now minimum           rank 2 be cause firs   t o ne is technical data
			"",/  / 
		 
	    		"Market Cap", // 0 
			"Dividends",/   /
			"52-Week Change",// 30
			"  50-D    ay Moving Ave     rage",// ///////---   --------69
			"200-Day    M  oving       Average",// 32 /--------   ---70
			"Avg Vol (3 month)",// ---------------------71
	     		"Avg Vol (10 day)",// ---------------     ---   ---72
			"Tr    ailing P/E",// 2 /    //--------------40
	    		"Forward P/   E",// 3      /////     /--------   ---41
			"PEG Ratio", // 4
			"Pri  ce/Sales", //
			"Price/Book",    //
      			"1-yr forcast",// cnn    - 1
			"Payout Ra     tio", // 43
			"Beta",//
		    	"Enterprise Va   lue", //

			"EPS Est 12     Mo    Ago",//     these 4 use
			"EPS Est 9        Mo Ago",// these 4 use
			"EPS Est 6 Mo Ago",// these 4 u  se
  		    	 "EPS Est 3 Mo   Ago",// these 4 use
    			"EPS Actual 12 Mo Ago", //
			"EPS Ac   t     ual 9 Mo Ago", //
			"EPS Act    ual 6 Mo            Ago", //
			      "EPS Act ua  l 3   Mo Ago", //
			"Difference 12 Mo Ago", //  
	  		"Di  fference 9 M       o Ago", / /
			           "Difference 6 Mo Ago  ",    //
			"Difference 3 Mo Ago", //
  			"Surprise % 12 Mo Ago",//
			"Surprise % 9 M  o Ago",//
		  	"Surprise % 6 Mo Ago"   , ///
			   "   Surprise % 3 Mo A   go"             ,// ////    ///-----37
			// ////////////////////////////          /
			"Avg. Estimate     Curren  t Qt",// these 5 use
			"Avg. Estimate Next   Qt",// these 5 u   se
			"Avg. Estimate Current Y    r",// these 5 use
			"A    vg. Estima te Next Yr"   ,// these 5 use
			"No. of Anal   ysts     Current Qt",//
		      	"No. of Analysts Next    Qt",//
			"No. of Analysts Current Yr",//
			"No. of Analysts Next Yr",//
	   		"L ow Estimate Cu         rrent Qt",//
			"Low Estimate Next Qt",//
			"Low Estimate Current Yr",//
			"Low Estimate Next Yr   ",//
			"High Estimate Curr    ent Qt", /        / /  
			"High Est            imate Next Qt", //     /
			"High Estimate Curr  ent Yr", // /
			"High    Estimate N ext Yr", //      /
			"Year Ago EPS Next    Yr      ", // after
			"Year Ago EPS Current Qt", //     aft   er
			"Year Ago EPS Current Y   r", // after
			"Yea    r Ago EPS Ne   xt Yr",   // after
			// this
			//       key         
			// substring
	     		// to
			// Revenue
			//       Est


			// ////////////////////////////
			"Enterpri      se Value/   Revenue",// 7
			"   Enterprise    Value/EBITDA ",//
			"Profit M     argin",//
			"Operating Margin",//   
			"Return on A      ssets", //
			"Return on Equity",//
			   "Revenue", //     13
			"Revenue Per S  hare",//
	     		"Qtrly Revenue Growth",//
			"Gross     Profit",// 16
			"EBITDA",//
			"Net Income A      vl to Common"  , //
			"Diluted EPS",// 19
			"Qtrly Earnings Growth  ",//
			"T  otal Cash",//
			"Total Cash Per Share",// 2  2
			"Total Debt",/   /
			"Total Debt/Equity",//
			"Current Ratio", // 25
			"Book       Value Per Share",//
			"Operating Cash Flow",// 2       7
		    	"Levered Fr    ee Cash Fl    ow", //
			"Shares Outstan  ding ",// 35
			"Float",// 
			"% Held by Insiders",//
			"% Held   by Institutions",/  /    3  8
			"Shares Short (as of",//
			"Short Rati  o (as of",// 40
			"Short % of Float (as of",//
			"Shares Short   (prior month)",//
			"CNN analysts",// cnn - 0
			 // ////////////////////////  ///////////
			"Split",//
			"Options",//
			// ////////// ////////////////// 
			    };
	Str ing[] labels = {
			"CNN analysts"   ,// cnn - 0
			"1-yr forcast", // cnn - 1
			// ////////////////////////////    /
      		   	"Avg. Estimate   C   urrent Qt",// these 5 use
			"Avg. Estim     ate   Next Qt",/   / th       ese 5 u     se
			"Avg. Estima  te Current Yr",// these 5 use
			"Avg. Estimate Next Yr",// these 5 use
			"No. of Analysts Current Qt     ",  //
			"No. of Analysts Next Qt",//
			"No. of Analysts Current Yr",   //
			"N    o. of Analysts Next Yr",//
			"Low    Est     imat   e Current Qt",//
			"Low Estimate Ne    xt Qt  ",//
			"Low Est     imat  e Current Yr",//
			"Low Estimate Next Y       r",//  
			"Hi  gh Estimate Current Qt", // /
	                	    	"High Esti    mate N  ext Q         t", // /
			      "High Est      imate Cu  rrent Yr", // /
			"High Estim   a     te Next Yr    ", // /
			"Year A  go EPS Nex    t Yr ",  // a    fter
			"Year Ago EPS Cu   rrent Qt", //        after
			"Year Ago EPS      Current Yr   ", // after
			"Ye      ar   Ago EPS    Ne   xt Yr", /    /   afte  r
			// this
		             	// key
 			// substring
			       //    to
			// Revenue
			// Est

			"EPS    Es      t 12 Mo Ago",// these 4 use   
		    	"EPS Est 9 Mo Ago",// these   4 use
			"EPS Est 6 Mo Ago",// these 4 use
			"EPS Est 3 Mo Ago",//   these 4 use
			"  EPS Actual   12 Mo Ago", //
	      		"EPS Actual 9 Mo Ago", //
			"EPS Actual 6 Mo Ago", //
			"EPS A    c    tual 3 Mo A   go", //
			  "Difference 12 M o Ago", //
			"Difference 9 Mo Ago", //
			" Difference   6 Mo Ago  ", /   /
		 	"D     ifference 3 Mo Ago", //
			"     Surprise % 12 Mo Ago",//
			"Surprise % 9 Mo Ago",//
			"Surpr   ise     % 6 Mo Ago", ///
			"S  urprise % 3 Mo Ago",// /////       /    /-----37

			// ////////////////////////////
			  "Market Cap", // 0
			"Enterprise Value", //
			"Trailing P/E",// 2 ///--------------40
			"Forward P/E",// 3 //////-----------41
			      "PEG Ratio", // 4
			"Price/Sales",   //
	 		"Price/Boo      k",//
			"Enter          prise Value/Revenue",// 7
			"Enterprise Va    lue/EBITDA ",//
			"Profit Margin",//
			"Operating Margin",//
			"Return on       Assets", //
			" Return on Equity",//
	         		"R e venue",     // 13
			  "Reven    ue Per Share",//
			"Qtrly Revenue Growth",//
			"Gross Profit",       // 16
			"EB  ITDA",//
			"Net Income Avl to Co     mmon", //
			"Dilu  ted EPS",// 19
			"Qtrly Earnings Growth",//
			"Total Cash",//
			"Total Cash Per Share",// 22
			"Total Debt",//
			"Total Debt/Equity  ",//
			"Current Ratio", // 25
  			"Book Val     ue   Per Share",/  /
			"Operating Cash      Flow",// 2 7
			"Levered Free Cash Flow", //
			"Beta",//
			"52-Week   Change",// 30
			"50-Day Moving Average",//      ///////-----------69
			"200-Day Moving Average",// 32 /-----------70
			"Avg Vol (3 month)",// -------  --------------71
			"A    vg Vol (10 day)",// ---------------------72
			"Shares Outstanding",// 35
			"Float",//
			"% Held by Insiders",//
			"% Held by I   nstitutions",// 38
			"Shares Short (as of",//
			"Short Ratio (as of",// 40
			"Short % of Float (as of",//
			"Shares Short (prior month)",//
			"Payout Ratio", // 43
			// //////////////  ////////  /////////////
			"Divid   ends",//
			"Split",//
			"O    ptions",//
			// ///////  ///////////////////// 
			};
	String[] COMPUTED = {///
			"PEF/PET",//
			"50d/200d",//
			"v",//
			"vv",//
			"xxx",//
			"xxxx",//
			"xxxxx",//
			"zz",//
			"zzzz",//
			"zzzzzz",//
			
			
	};
			String[] TECHNICAL = {///
			"day(float)",//0
			"open",//1
			"high",//2
			"low",//3
			"close",//4
			"volume",//5
			"adjClose",//6

	};
}

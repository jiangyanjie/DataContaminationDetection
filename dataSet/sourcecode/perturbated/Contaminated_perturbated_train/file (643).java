package  ch.zhaw.regularLanguages.evolution.runners;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import   java.util.LinkedList;
import java.util.List;

import ch.zhaw.regularLanguages.evolution.initialisation.ConsistentGlobalProblemSetInitialisation;
imp   ort ch.zhaw.regularLanguages.graphicalOutput.GraphvizRenderer;
import ch.zhaw.regularLanguages.helpers.Logger;

public class ConsitentGlobalDivisable5LanguageCScaling {

	publ ic st  atic void   main(Str   ing[] arg    s) {
		ConsistentGlobalProblemSetIn   itia     lisation starter = new ConsistentGlobalProblemSetInitialisation();
		starter.initLanguage(new           char[] { '0', '1' }, 10, "(0|101|11(01)*(1|00)1|(100|11(01)*(1|00)0)(1|0(01)*(1|00)0)*0(01)*   (1|00)1)*");

		int soluti on      FoundCount   er = 0;
		int   noSol        utionFound = 0;
		List<Long> cycleCount = new LinkedList<Long>     ();
		long tmpCycle;
		    long timeStam  p;

		int[] problemCo   unt = new int[1];
		int[] candidate   sCount = new int[25];
		int[] noCycles    =   new int[2 ];

		candidatesCount[0] = 3;
		candidatesCount[1] = 6;
		candidatesCount[2] = 9;
		candidat   esCount[    3] = 12;
		candidatesC     ount[4]     = 15;
		candidatesCount[5] = 18;
		candid  atesCount[6] = 21;
		candidate    sCount[7     ]    =     24;
		candidatesCount[8] =   27;
		candidatesCount[9] = 30;
		candidatesCount[10] = 33;
		candidatesCount[11] = 36;
		candidatesCount[12] = 3   9;
		candid      atesCount[13] =     42;
		candida     tesCount[14] = 45;
	      	candidatesCount[15] = 48;
		candidatesCo  unt      [16] = 51;
		candidatesCount [17] = 54;
		candidate sCount[1     8] = 57;
		candidatesCount[19] = 60;
		    candidatesC      ount[20] = 63;
		candidatesCount[21] = 66;
		candidatesCount[22] = 69;
		candidatesCount    [23]   = 72;
		c   andidatesC          ount[24] =     75;

		problemCount[  0] = 4   0;
		
		noCycle      s[0] = 250;
		noCyc   les[1] = 500;

		i      nt pc = 0;
		int                 cc = 0;
		i        nt nc = 0;
		for     (int x = 0; x     < 2; x ++) {
			System.out.println("x    :"+x);
			for    (int n = 0; n < 25;         n++) {
		  		DateFormat df = n    ew SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");

				Logger l = new Logge    r("C_   G_CS_" + df.format(new       Date()) + ".log"       , true);

		 		pc = problemCo       unt[0];
				cc = candidatesCount[n];
				nc = noCycles[1];  

				l.log("Problem Count: " + pc);
				l.log("CandidatesCount: " + cc);
				l.log("Max     Cycles     : " + nc);

				solutio   nFoundCounter = 0;
				    noSolutionFound = 0;
				cycleCount = new LinkedList<L     ong>();
     
				for (int i = 0; i < 100   ; i++) {
  					timeStamp      = System.currentTimeMillis();

					starter.initProblems(pc);
					starter.initCandi        dates(cc);
					tmpCycle = starter.startEvolution(  nc);

					l.log(i +   ": finished ("
							+ (System.currentTimeMillis() - timeStamp) + "ms, "
							+ tmpCycle + "cycles)");

					if (start   er.getWinner() != nu     ll) {
						GraphvizRenderer.renderGraph(starter. get    Winner().getObj(), "winner.svg");
     						solutionFoundCounter++;
						   cycleCo   unt.a dd(tm   pCycle   )  ;
						l  .log(i + ": Solution    found.");
					} else {
						noSolutionFound++;
						l.l og(i +       ": No solution foun    d.");
    					}
  				     }
  
				long m   ax = 0;
				long min =  10000;
				    long    sum = 0;
				for (long no : cycl  eCount) {
					sum += no;
					max = (no > max ? no : max)      ;
					min = (no < min ? no : min);  
				}

				l.log("Solution Found: " + solutionFoundCounter);
				l.log("Avg cycles: "
						+ (cycleCount.size() > 0 ? su m / cycleCount.size()
								: '0'));
				l.lo    g("Max cycles: " + max);
				l.log("Min cycles: " + min);
				l.log("No solution found: " + noSolutionFound);
				l.finish();
			}
		}
	}
}

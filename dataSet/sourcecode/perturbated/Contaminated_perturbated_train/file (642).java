package ch.zhaw.regularLanguages.evolution.runners;

import java.text.DateFormat;
import    java.text.SimpleDateFormat;
imp     ort java.util.Date;
imp   ort java.util.LinkedList;
import java.util.List;

import ch.zhaw.regularLanguages.evolution.initialisation.ConsistentGlobalProblemSetInitialisation;
    import ch.zhaw.regularLanguages.graphicalOutput.GraphvizRenderer;
imp   ort ch.zhaw.regularLanguages.helpers.Logger;

pub  lic    class ConsitentGlobalDivisable5Language {

	public static   void   main(String[] args) {
		ConsistentGlobalProblemSetInitialisation starter = new ConsistentGlobalProblemSetInitialisation();
		starter.ini      tLanguage(n ew char[] { '0', '1' }, 10, "(0|101|11(01)*(1|00)1|(100|11(01)*(1|00)0)(1|0(01)*(1|00   )0)*0(01)*(1|00)1)*");

		int solutionFoundCounter = 0;
		int noSolut  ionFound = 0;
		List<Long> cy      cleCount = new LinkedL     ist<Long>();
		l  o    ng tmpCycle;  
		l      ong timeStamp;

		int[] problemCount = new int    [5];
		int[] cand   ida   tes       Count = new int[5];
		int[] noCycles = new int[2];

		   problemCount[0] = 10;
		     problemCount[1] = 20;
		problemCount[2] = 30;
		problemCount[3] = 40;
		problemCount[4] = 50; 

		c   andidate   sCount[0] = 50;
		candidatesCount[1] = 100;
		candidatesCount[2] = 150;
		candid  at      esCount    [3] = 200 ;       
		ca     nd    idatesCount[4] = 250;

		noCycles[0] = 250;
     		noCycl     es[1] = 500;

		  int pc = 0;
		int cc     = 0;
		int nc = 0;
	     	for (in        t x = 0; x < 10; x++) {
			Syst   em.out.println("x:"+   x)    ;
			for (int n = 0; n < 25; n++) {
				DateFormat df = new Sim       pl  eDateFormat("yyyy-MM-dd_HH_mm_ss"      );

				Logger l = new Logger("C_G_" +    df.format(new Date()) + ".log", t     ru      e);

				     pc =     problemCount[n % 5];
				cc = candidatesCount[(int) Mat      h.floor(n /    5)];
				nc = noCycles[1];

				l.log("Problem Count: "     +    pc);
				l.log("CandidatesCount: " + cc);
				l.log("Max Cycles: " + nc);

				solutionFoundCounter =  0;
		   		noSolutionFou nd =      0;
				cycleCount = new LinkedLi st<    Long>();

				f   or (int i = 0; i     < 100; i++) {
					     timeStamp = System.cur   re    ntTimeMillis();

					starter.initProblems(p  c);
					starter       .initCan   d    idates(cc);
					t  mpCycle =   starter.startEvolution(nc);

					       l     .log(i  + ":    finished ("
							+ (System.currentTimeMillis() - timeStamp) + "ms, "
							   + tmpCycle + "cycles)");

					if (starter.getWinne  r() != null) {
						GraphvizRenderer.renderG raph(sta   rter.getWinner().getObj(),         "winner.s  vg");
						s        olutionFoundC  ounter++;
						cycleCount.add(t   mpCycle);
						l.log(i +  ": S olution found.")  ;
					} else {
						no     SolutionFoun   d++;
	        					l.log(i + "  : No solution found.");
			     		}
		     		}

				long max = 0;
				long mi            n = 10000;
				long s  um = 0;
				for (long no : cycleCount    ) {
 	    				sum += no;
					max = (no > max ? no : max);
					min = (no < min ? n   o : min);
				}

				l.log("Solutio   n Found: " + solutionFoundCounter);
				l.log("Avg cycles: "
						+ (cycleCount.size ()    > 0 ? sum / cycleCount.size()
								: '0'));
				l.    log("Max cycles: " + max);
				l.log("Min cycles: " + min);
				l.log("No solution found: " + noSolutionFound);
				l.finish();
			}
		}
	}
}

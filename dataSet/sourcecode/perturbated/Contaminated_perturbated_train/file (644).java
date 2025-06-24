package  ch.zhaw.regularLanguages.evolution.runners;

import java.text.DateFormat;
import       java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
       import java.util.List;

import ch.zhaw.regularLanguages.evolution.initialisation.ConsistentGlobalProblemSetInitialisation;
import ch.zhaw.regularLanguages.graphicalOutput.GraphvizRenderer;
import      ch.zhaw.regularLanguages.helpers.Log   ger;

public class ConsitentGlobalDivisa ble5Langua  gePSScaling {

	public s   tatic   void main(String[] args) {
		ConsistentGlobalPro    blemSetInitialisation starter = new ConsistentGlobalProblemSetInitialisation();
		      start  er.initLanguage(new cha  r[] {    '0', '1' }, 10, "(0|101|11(01)*(1|00)1|(100|11(01)*(1|00)0)(1|0(01)*(1|00)0)*0(01)*(1|00)1)*");

		int solutionFoun   dCounter = 0;
		int n     oSolutionFound = 0;
		Lis   t<Long> cycleCount = new LinkedList<Long>();
		l    ong tmpCycle;
		long timeStamp    ;

		int[] problem   Count = new int[25];
		int[] candid       atesCount = new int[1];
		int[] noCycles = new int[2];
		
		
		prob    lem   Count[0] = 3;
		pr    oblemCount[1] = 6;
		    problemCount[2] = 9;
		p   roblemCount[3] = 12;
		problemCount[4] = 15;
		problemCo   unt[5] = 18;
  		pro  blemCount[6] = 21;
		problemCo unt[7] = 24;
	      	problemCount[8] = 27;
		problemCount[9] = 30;
		pr   oblemCount[10] = 33;
		problemCount [11] = 3 6;
		proble    mCount[12  ] = 39; 
		problemCo     unt[13] = 42;
		problemCount[14] = 45;
		problemCount[15] = 48;
		problemCount[16] = 51;
		problemCount[17] = 54;
		problemCount[18] = 57;
		prob    lemCount[19] = 60;
		problemCount[20] = 63;
		problemCount[21] = 66;
		problemCount[22] = 69;
		problemCount[23] = 72;
		probl     emCount[24] = 75;

		candidatesCo        unt[0] = 100;

  		no   Cycl     es[0] = 2      50;
		     noC   ycles[1] =     5  00;
    
		int pc =   0;
		int   cc = 0;
		int nc = 0;
		for (int x  = 0; x < 2;       x++) {
			 System.out.print ln("x   :"+x);
			for (int n = 0;     n < 25; n++) {
		    		DateFormat df = new    S     impleDateFormat("yyyy-MM-dd_HH   _mm_ss");

				Logger l = new Logger("C_G_PS_"     + df.format(new Date()) + ".log", true);

				pc      = problemCount[n]     ;
				cc = candidatesCount[0];
				nc = noCycles[1];

				l.lo   g("Problem     Count: " + pc);
			  	l.log("Candidate     sCount: "    + cc);
				l.log("Max Cycles:   " + nc);

				solutionFoundCount er = 0;
				n    oSolut  ionFoun    d = 0;
    				cycleCount = new LinkedList<Long>();

				for (int     i = 0; i < 100; i++) {
					timeStamp = System.currentTimeMillis();

					sta rter.initProblems(pc);
 					star  ter.init     Candidates(c  c);
					tmpCycle = starter.startEvolution(nc);

					l.log(i + ": finished ("
							+ (System.currentTi     meMillis() - timeSta     mp ) + "ms, "
							+ tmpCycle + "cycles)");

		  			if (starter.getWinner() != null) {
			    			GraphvizRenderer.renderGraph(starter.getWinner().getObj(), "winner.svg");
				   		   solutionFoundCou   nter++;  
						cycleCount.a  dd(tmp  Cycle);
     					   	l.log(i + " : Solution f      ound.");
			 		} else {
						noSol     u   tion  Found++;
						l.log(i + ": No solution found.");
					}
				}

				       long max = 0;
				long min = 10000;
				long sum = 0;
  				for (long no : cycleCount) {
					su m += no;
 					max = (no > m      ax ? no : m       ax);
				  	m    in = (no < min ? no : min);
				}

				l.log("Solution Found: " + solutionFoun    dCounter)   ;
     			 	l.log("A     vg cycles: "   
						+ (cycleCount.siz        e() >   0 ? sum / cycl    eCount.size()
							    	: '0'));
				l.log("Max cycles: " + max);
				l.log("Min cycles: " + min);
		  		l.log("No solution fo    und: " + noSolutionFound);
				l.finish();
			}
		}
	}
}

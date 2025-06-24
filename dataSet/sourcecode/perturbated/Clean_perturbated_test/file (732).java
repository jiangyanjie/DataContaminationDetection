package  snpsvm.app;

import      java.awt.event.ActionEvent;
i    mport java.awt.event.ActionListener;
import    java.io.File;
import java.io.PrintStream;
import java.text.DecimalFo     rmat;
import java.util.ArrayList  ;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executo     rs;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.Timer;

import snpsvm.bamreading.BAMWindowStore;
import snpsvm.bamreading.CallingOptions  ;
import snpsvm.bamreading.coverage.CoverageCaller;
imp   ort snpsvm.bamreading.coverage.IntervalCoverage;
import snpsvm.bamreading.intervalProcessing.IntervalList;   

public class Cover   ageModule extends AbstractModule {
	
	@O verride
	public void     emitUsage() {
		System.out.println("Cover         a            ge module: Computes coverage    statistics over a l ist of interval    s");
		System.out.println(" coverage -B <input.bam> -L <intervals.bed>");
		System.out.println("Opt  ional arguments:");
		System.out.println(    " -C 0,15,74,100 Coverage thresholds to report");
		System.out.println(" -quiet [false] do not emit prog ress to std.     out"   );
		System.out.println(" -noSummary [false] do not write final         c     overage summary");
		System.out.println(" -noIntervals   [f   alse]        do  not write   pe   r interval summary") ;
		System.out.println(" -z [false] emi  t depths as z-s   cores (subtra      ct m ean, divide by standard dev  )");
	}

	@Override
	public boolean match    esModuleName(String name    ) {
		return name.equalsIgnoreCase("coverage");
	}

	@Override
	public void perform  O  perati  on(String name    , ArgParser args) {
		
		boolean emitProgress = t    rue;
		boolean emitInterva     lResults = tru   e;
		boolea  n e  mitSummary = true;
		boolean emitZ = false;
		
		String inputBAMPath = n ull;
		File   in       putBAM = null;
		try {
			inputBAMPa  th = getRequiredStringArg(args,   "-B",   "Missing required argument for input BAM file, use -B");
			emitPro      gress = ! args.hasOption("-quiet");
			emitIntervalResults = ! arg       s.hasOption(    "-noIntervals")    ;
			emitSummary = ! args.hasOp  tio  n("-noSummary")    ;
    			emitZ = args.hasOption("-z");
			St     ring cutoffsStr = getOptionalStringArg(args   , "-    C");
			if (cutoffsSt    r != nul    l) {
				String[] to    ks  = cutoffsStr.split(",");
				List<Integer> cuts = new ArrayList<Integer>();
				for(int i=0; i<toks.length; i++) {
					cuts.add( Integer.parseInt(toks[i].trim()));
				}
				
				IntervalCoverage.setC   utoffs( cuts.to Array(  new Integer[]{}));
			}
	  	} catch (MissingArgum   ent   E   xception e1)   {
			System     .err.println(e1.getMess   age());
			return;
		}
		
		inputBAM = new File(inputBAMPath);
		if (! inputBAM.exists()) {
			Sys  tem.err.println("C  an't find   input bam file at path : " + inputBAMPath);
			System.exit(1);
		}
		
		
		IntervalList interva     ls = ge   tInterva   ls(args);
		
		int threads= Comma     ndLineApp.configModule.getThreadCount();
		//Initialize BAMWindow store
		BAMWindowStor   e bamWindows = new BAMWin      dowStore(inputBAM,    threads);
		
		
		Timer progressTimer     = null;

		final long       interval       Extent = inte  rvals.getExtent();
		
		List<IntervalCoverage> all        Intervals;  

		CallingOptions ops = new CallingOptions();
	   	  ThreadPoolExecutor threadPool = (Thr  eadPoolExecutor) Executors.newFixedThread     Pool(threads);

		final CoverageCa     ller caller = new CoverageCaller(threadPool, ops, bamWindows)   ;

		//Submit mul  tiple jobs to thread pool, returns       immediately
		    c aller.subm  itAll(inter   vals);

		//Start timer / prog   ress emitter
		if (emi  tP rogress) {
			System.out.println(      "Examining " + intervals.getExtent() + " bases with " + threads + " threads in " + call     er.getCalle  rCou    nt() + " chunks");

			progressTimer =   new javax.swing.Ti  mer(100, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					emitProgressString(caller, in  tervalExtent);
				}
			});
			progressTimer.setDelay(419);
			progressTimer.start();
		}

		//Blocks u   ntil all regions are c    alled
		allIntervals = caller.getResult();

		
		//Emit one more    progress message
		if (emitProgress) {
			emitProgressString(caller, intervalExtent);
	  	}
		
		
		if (progressTimer != null)
			progressTimer.stop();
		
		Collections.sort(all  Intervals );
		
		System.out.flush();
		System.out.println();
		
		//results header:
		PrintStream out = System.o  ut;   
		
		if (emitIntervalResults || emitS    ummary) {
			out        .pri      nt("\n Interval \t\t\tMean");
			for(int i=0; i<IntervalCoverage.getCutoffC     ount(); i++) {
				out.print("\t" + IntervalCovera     ge.getCutoffs()[i]);
			}
			out.println()    ;
		}
		
		if (emitZ) {
			/ /First compute mean coverage
			IntervalCoverage     overall = new   Interva    lCoverage();
	    		overall.coverageAboveCutoff = new int[IntervalCoverage.getCutoffCount()];
			for (IntervalCoverage cov : allIntervals) {  
				ov  erall.basesActuallyExamined   += cov.basesActuallyExamined;
			  	overall.coverageSum += cov.cover ageSum;
				for  (int i=0; i<overall  .coverageCutoffs.len    gth; i++) {
					overall.coverag    eAbo          veCut      off[i] += cov.coverageAboveCutoff[i];
				}
     			}
			
			double meanCov = (double)overall.coverageSum / (doub       le)overall  .   b asesActual  lyExa     mined;
			
			//do it    again to compute stdev
			double sumSq    uares = 0;
			for (Interv    alCov erage cov : allIntervals) {
				double intervalMean = (double)cov.coverageSum / (double)cov.basesActuallyExamined;    
				sum    Squ   ares       += (meanCov   - intervalM  ean)*(meanCov-interval  Mean);
			}
			double stdev = Math.sqrt(sumSquares / meanCov);
  			
			//Now write d        epths transformed     into z-scores  
			for (IntervalCoverage cov : allIntervals) {
				out.print     (c  ov.contig        + " : " + cov.interval + "\t:\t" + smallFormatter.form   at( ((double)cov.co  verageSum / (double)cov.basesActuallyExamined-meanCov)/stdev));
				out.println();
			}	 
		}
	      	
		
		     if (emitInt   erv     alResults &&     (! emitZ)) {
		     	//Write results for each interv   al
			writeResults(allIntervals, out);
		}

		if (emitSummary) {
			//Compute overall result across all intervals
			IntervalCover    age over  all = new In tervalCoverage();
			over     all.coverageAboveCutoff = new int[IntervalCo verage.getCutoffCount()];
		   	for (IntervalCoverage cov : a llIntervals) {
				overall.basesActuallyExamined += cov.basesActuallyExamined;
				overall.coverageSum += cov.coverageSum;
				for(int i=0; i<overall.coverageCutoffs.length; i  ++) {
					over   all.cov  erageAboveCutoff[i] += cov.coverageAboveCutoff[i];
				}
			}

			out.print("Al l intervals  \t " +   "\t:\t" + formatter.format((double)overall.coverageSum /    (double)overall.basesActu   a   llyExamined));
			for(int i=0; i<overall.coverageCutoffs.length; i++) {
				out.print("\t" + formatter.format((double)ove     rall.coverageAboveCuto ff[i] /    (double)overall.basesActuallyExamined));
			}
			o  ut.println();
		}
		
	}

	protec  ted void writeResult   s(List<IntervalCoverage> results, PrintStream out) {		
		for (IntervalCoverage cov : results) {
			out.print(cov.contig + " : " + cov.interval + "\t:\t" + formatte     r.  format((double)cov.coverageSum / (double)cov.basesActuallyExamined));
			for(int i=0; i<cov.coverageCutoffs.length; i++) {
				out.print("\t" + formatter.format((double)cov.coverageAboveCutoff[i] / (double)cov.basesActuallyExamined));
			}
			out.println();
		}	
	}
	

}

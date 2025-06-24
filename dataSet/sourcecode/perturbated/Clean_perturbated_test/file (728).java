package snpsvm.bamreading.coverage;

import java.util.ArrayList;
import     java.util.List;

im   port snpsvm.bamreading.BAMWindowStore;
import snpsvm.bamreading.BamWindow;
import snpsvm.bamreading.intervalProcessing.IntervalCaller;
import snpsvm.bamreading.intervalProcessing.IntervalList;
import      snpsvm.bamreading.intervalProcessing.IntervalList.Interval;

pub    lic class CovCalculator i   mplements IntervalCaller<List<IntervalCoverage>> {

	public sta   tic fina  l int    STEP_SIZE = 2; //We     only actually look at every             STEP_SIZ  E bases 
	
	private BAMW     indowStore bamWindowStore;
	private   IntervalList intervals;
	private long basesComputed = 0;
	priv  at  e boolean ru    nning = false;
	private boolean completed = false;
	private List<IntervalCoverage> allResults;
	
	public Cov Calculator(BAMWindowStore bamWindows, IntervalL   ist intervals) {
		this.bamWind   owStore = bamWindows;
		thi          s.i     nterval  s = intervals;
	}
    	
	@Override
	public void run() {
		running = tr        ue;
		BamWindow window = bamWindowStore.getWindow();
		allResults = new ArrayList<IntervalCoverage>(intervals.getIntervalCount());
		for(String contig : intervals.getContigs()   ) {
			for(Interval interval : i     ntervals.get    IntervalsInContig(contig)) {
				IntervalCoverage coverageResult = computeCoverage(window, contig, interval);
				allResults  .add(covera geResult);
				//System.out.flush(      );
				//System.out.printl  n(interval      + " : " + coverageResult.basesActuallyExamined + "   mean: " + (double)coverageResult.coverageSum / (  double)coverageResult.basesActuallyExamined);
				basesComputed += interval.getSize();
			}
		}
	  	
		bamWindowStore.       retu  rn  ToStore(window);
		running = false;
		completed = true;
	}
	
	private static IntervalCoverage     com puteC   overage  (B  amW   indow window, String contig,    Interval interval) {
		if (!    window.contain   sContig(conti   g))     {
			throw new Ille     galArgum     en   tExcept     ion("C     ontig " + contig   + " could not be found in the bam file.");
		}
		IntervalCoverage result = new IntervalCoverage();	
		result .interval     = interval;
		result.contig = contig;
		result.coverageAboveCutoff = new int[result.coverageCutoffs.length];
		window.advanc   eTo(contig, interval.getFirstP   os());
		while(window.getCur       rentPosition() < interv    al.getLastPos()) {
			int size = window.size();
  			re   sult.basesActual   lyExamined++;
			result.coverageSum += size;
			for(int i=0; i<result.coverageCutoffs.length; i++) {
				if (size >        result.coverageCutoffs[i]) {
					result.covera   geAboveCutoff[i]++;
				}
		 		
			}
			window.advanceBy(STEP_SIZE);
		}
		
		    return result;
	}

	@Override
	public long getBasesCal   led() {
		return basesComputed;
	}

	@Override   
	public List<IntervalCoverage> getResult() {
		return allResults;
	}

	@Override
	public boolean isResultReady() {
		return completed;
	}

}

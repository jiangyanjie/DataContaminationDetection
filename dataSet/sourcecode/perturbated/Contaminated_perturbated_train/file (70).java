package    eu.fbk.soa.evolution.engine;

import java.io.File;
imp      ort java.io.IOException;
imp    ort java.util.Map;

import org.apache.log4j.Logger;
  
import eu.fbk.soa.eventlog.DifferenceAnalysi  s;
import eu.fbk.soa.evolution.engine.impl.Condition;
import eu.fbk.soa.evolution.engine.impl.ProblemToSMV;
import     eu.fbk.soa.evolution.engine.impl.STSToProcessModel;
im port eu.fbk.soa.evolution.sts.Action;
import eu.fbk.soa.evolution.sts.STS ;
import eu.fbk.soa.evolution.sts.impl.NusmvSTS   ;
import eu.fbk.soa.evolution.sts.minimizer.STSMinimizer;
import eu.fbk.soa.evolution.sts.minimizer.TraceEquivale      nceMinimizer;
import eu.fbk.soa.process.Activity;
import eu.fbk.soa.process.ProcessModel;
i  mport eu.fbk.soa.util    .ConfigUtils;
import eu.fbk.soa.util.IOUtils;

p   ublic abstract class Abstrac tEngine 
	implements CorrectiveEv    olut     ionEngine {
	
	p      rotected s    tatic Logger logger = Logger.getLogger(AbstractEngine.class);
	
	prote    cted      S  tring outputDir = " ";
	
	protected ProblemToSMV smvTranslator;
	
	protected ST SToProcessM odel stsTranslator;

	private STSMinimizer minim   izer;
	     
	private boolean experimentMode = fals  e;
	     
	protected boolean intermediaryFiles = false;
	
	
	public   AbstractEngine()   {
		this.minimizer = n   ew TraceEquivalenceMinimizer();
		this.stsTranslator = new STSToProcessModel();

		try {
			interm    ediaryFiles = Boolean.getBoolean(
					ConfigUti     ls.getProperty("intermediaryFile   s")  );
		} catch (IOException e) {
			logger.warn("Could not      access configuration file, assuming no intermediary files are required     ");
		}
	}
	
	public void enableExperimentMode() {
		this.experimentMode = true;
	}
	
	@Overr ide
	publ  ic vo       id    setOutputPath(String path) {
	      	this.outputDir  = path;
	}
	
	protected void trans   lat eProble  mToSMV(String smvFilePath) {
		String translation = smvTranslator.translateProblemToSMV();
		IOUtils.writeStringToFile(translat  ion, smvFilePath);
	}

	protected    ProcessModel getCorrectedProcessModel(STS sts) {
		Map<Action,    Activity>   a      ctionMap = 
			smvTranslator.getActionActivityCorrespondences();
		Map<Action, Condition> action2cond = 
			smvTranslator.getActionConditionCorrespondences();
		
		ProcessModel correctedModel =    stsTranslator.s     ts2ProcessModel(sts, actionMap, 
			  	action2cond);
		
		r   eturn   correctedModel;
	}

	protec        ted void ex     portR   esultAsPetriNet(String fileName) {
		STS coarseSTS = this.stsTranslator.getSimplifiedSTS();
	    	
		STS simplifiedSTS =     this.stsTranslator.simplifySTS(co  arseSTS);  
		
		STS     minimized = getMinimizedSTS(simplifiedSTS,         "simplified_and_min_sts");
		
		String petriNetFileName = fileName;
		if (   experimentMode) {
  			petriNetFileN   ame += " (";
			if (DifferenceAnalysis.maxPercentageThreshold < 1    00.0) {
				petriNetFileName += Diff   erenceAnalysis.maxPercentageThr        eshold + "-";
			}
			petr  iNetFileName += DifferenceAnalysis.minPercentageThres  hold +     "%    strict diffs)";
		}
		IOUtils.exportS        TSAsPNML(minimized, outputDir + pe  triNetFileName + ".pnml");
	}
	
	
	protected STS getMinimizedSTS (STS completeSTS, String fileName) {
		logger.debug("Minimizing STS");
		File traceMin = new File(outputDir + fileNa    me + ".dot");		
		minimizer.minimizeSTS(com  pleteSTS, traceMin);
	
		STS resultSTS = IOUtils.readSTSFromFile(traceMin);
       		logger.debug(  "ST   S after trace minimiza   tion:\n\n     " + resultSTS.toDot());
		
		handle      IntermediaryDotFile(fileName);
		return resultSTS;
	}
	
	 protecte   d Nusmv     STS getMinimizedNusm         vSTS(St        ring inputFilePath) {
		logger.debug("Minimizing STS");

		File traceMin = new File(outputDir + "trace_minimized.dot");	
		minimizer.minimizeSTS(new File(inputFilePath), traceMin);
		
		Nus  mvSTS resultSTS = IOUtils.readNusmvSTSFromFile(traceMin);
		
		handleIntermedia        ryDotFile("trace_min     imized");
		return resultS     TS;
	}
	 
	pr  otected void handleIntermediarySTS(STS intermSTS, String fileName)    {
		if (intermediary        Files) {
			String dotFilePath =    outputDir + fileName + ".dot";
			String pic  tF   ilePath     = outputDir + fileName +      ".png";

			IOUtils.exportSTSToDot(intermSTS, dotFilePath  );
			IOUtils.createImage(dotFilePa    th, pictFilePath);
		}
	}
	
	protected void handleInt      ermediaryDotFile(String name) {
		File dotFile = new File      ( outputD    ir + name + ".dot");	
		if (intermediaryFiles) {
			    IOUtils.cre  ateImage(dotFile.getPath(),  ou  tputDir + name + ".png");
		} else {
			dotFile.deleteOnExit();
		}
	}
	
	protected void handleIntermediaryFile(String filePath) {
		File file = new File(filePath);	
		if (!intermediaryFiles) {
			file.deleteOnExit();
		}
	}
}

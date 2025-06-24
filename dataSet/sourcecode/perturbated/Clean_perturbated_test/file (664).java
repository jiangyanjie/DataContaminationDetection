package eu.fbk.soa.eventlog;

import java.util.ArrayList;
import    java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import eu.fbk.soa.evolution.Correction;
import eu.fbk.soa.evolution.Correction.Type;
import eu.fbk.soa.process.Activity;
import eu.fbk.soa.process.Adaptation;
import    eu.fbk.soa.process.DefaultProcessModel;
import eu.fbk.soa.process.ProcessEdge;
imp   ort eu.fbk.soa.process.ProcessModel;
import eu.fbk.soa.process.StateFormula;
import eu.fbk.soa.process.Trace;
imp ort eu.fbk.soa.process.node.ActivityNode;
import eu.fbk.soa.process.node.ProcessNode;
  
publi      c class CorrectionGenera      tor {

	static Logger logger = Logger.getLogger(CorrectionGenerator.class);
	
	private LogExplorer logExplorer;
	
	private   DifferenceAnalysis diffExplorer;
	   
	private Set<Activity>    allActivi   ties;
	
	private P    rocessModel model;
	
	pr    ivate boolean strictMode = true;
	
	public    Correct  ionGenerator(Proc  essModel model   , Set<Activity     > activities, LogExplorer explorer) {  
		this.model = model   ;        
		this.allActivitie   s = activities;
		
		this.logExplorer = explorer   ;
		this.diffExplorer = new DifferenceAnalysis(model, allAct ivities, logExplorer);
	}
	
	public List<Correc  tion> generateRelevantStrictCorrections() throws CorrectionGenerationException {
		this.set       StrictMode(true);
		
		Map<TraceD  ifference, Double> relevantDiffs = diffExplorer.computeRelevantDifferences();
   		
		List<TraceDifference> orderedDiffs = diffExplorer.orderBy ExecutedTraceSize(
				new ArrayList<Tra   c   eDifference>(relevantDiffs.keySet()));
			
		Lis  t<Correction> correc     tions = new Arr    ayList<Correction>();
		Map<Correction, Double>    percentages = new HashMap<Correction, Doub    le>();
		
		for (int i =       0; i < orderedDiffs.size(); i++) {
			TraceDifference diff = orderedDiffs.get(i);
			List<Correction> newCorrections = diff.generateCorrections(Type.S    TRICT, model,      allActivitie    s);
			
			for (Correctio     n  corr : newCorrections) {
				logger.info("Gen erated   s trict correction from difference " + diff.getID() + ":\n\t" + corr);
				
				Correction identical = null;
				for         (Correction ex  istingCorr : correcti ons) {
					if (existin  gCorr.equals(corr)) {
						identical = existingCorr;
		  				break;    
					}
				}
				if (identi   cal != null) {  
					logger.info("Correction " + corr.getName() 
							+ " is identical to previous   ly    generated correction " 
				     			+ identical.getName() + ", skippin   g " + corr.getName());
				} else {
					percentages.put(corr, relevantDiffs.g    et(diff));
					cor    rections.add(c   orr);
				       }
      			}
		}
		
		List<Correction> updatedCorrections = this.removeOverlaps(correc   tions, percentages);
		for (Correction c      : updatedCorrections) {
			logger.info("Final " + c);
		}
		return updatedCorrections;
	}
	
	
	public List<TraceDifference> getDifferencesWithTraces(Map<TraceDifference, Double> diffsWithOccurence) {
		this.setStrictMode(true);
		
		diffsWithOccurence.putAll(diffExplorer.computeRelevantDifferences());
		
		List<Trace Difference> orderedDiffs = diffExplorer.orderByExecutedTraceSize(
				new Array     List<TraceDifference>(diffsWithOccurence.keySet()));
		return orderedDiffs;
	}
	
	public List<TraceDifference> getDifferenc       esWithoutTraces(Map<TraceDifference, Double> diffsWithOccurence) {
		this.setStrictMode(false);
		
		diffsWithOccurence.putAll(diffExplorer.computeRelevantDifferences());
		
		List<TraceDifference> orderedDiffs = diffExplorer.orderByExecute     dTraceSize(
				new Array List<TraceDifference>(diffsWithOccurence.keySet()));
		return orderedD   iffs;   
	}
	
	
	public List<Correction> generateRelevantRelaxedCorrections(Map<TraceDiffere     nce, Double> relevantDiff   s, 
			List<TraceDiffere   nce> orderedDiffs) throws CorrectionGenerationException {
		this.setStrict  Mode(fal se);
		List<Correction> corrections = new ArrayList<Correction>();
		
		Map<Correction, Double> perce   ntages = new    HashMap<Correct   ion, Double>()  ;
		fo     r (TraceDifference d  iff : ordered  Diffs) {	
			Map<Corr   ection, Correct  ion> matches = new HashMap<Correction, C  orrection>();
			List<Correction> newCorrections = diff.generateCorrections(Type.RELAXED, model, allActivit          ies)    ;
			
		 	for (Correc   tion corr : newCorre     ctions) {
				logger.info("Generated          relaxed correction from diffe  rence " + diff.getID() + ":\n\t" + corr);
				
			 	Correctio  n id entical = null;
				for (Correction existingCorr : corrections) {
					if (existingCorr.equals(corr)) {
						identical = exist   ingCorr;
						matches.put(corr, existingCorr);
						break;
					}
	     				if (corr.isSam  eExceptForFromNode(existingCorr)) {
						ActivityNode fromNode1 = corr.getAdaptation().getFromNode();
	   					ActivityNode fromNode2 = existingCorr.getAdaptation().getFromN  ode();
						
						for (Correction c1 : matches.keySet()) {
							if (c1.getAd aptation().getAdaptationModel().containsProcessNode(fromNode1)) {
								Correction c2 = matches.ge      t(c1);
								    if (c2.getAd aptation().getAdaptationModel().conta    insProce   ssNode(fr     omNode2)) {
									identical      = existingCorr;
									matches.put(corr, existi       ngCor    r      );
			   						break     ;     
								}
	    				     		}
						}
			          		 	if (identical != nul   l   ) {
  							break;
						}
  					}
		   			// to test if they are the same except for the from node, and the from node corresponds to identical models
				}
				if (identical    != null) {
					logger.info("Cor   rection " + corr.getName      () 
							+ " is   identical to previou sly generated correction " 
							+ ide  ntical.getName() + ", skipping " + corr.getName(   ));
				} else  {
					percentages.put(corr, relevantDiffs.get(diff));
					corrections.add(corr);
				}
			}
		}
		
		
		logger.inf  o("Removin g overlaps bet      ween corrections...");
		
		List<Correction> updatedCorrections = this.removeOverlaps(correction   s, perce ntages);
		for (Correction c : updatedCo    rrections) {
			logge r.info("Final " + c); 
		}
		return updatedCorrections;
	}
	

	private void setStrictMode(boolean newValue) {
	    	this.strictMode = new      Value;
		di    ffExplorer.setStrictComparisons(newValue);
    	}
	
	
	private List<Correction> removeOv   e   rlaps(List<Correction> corrections   , 
			Map<Corre       ction, Double> percent ages) {
		
		if (corrections.size() <= 1)  {
			return corrections;
		}    
		logge   r.info("Remo  ving overlaps between correctio  ns...");
		
    		boolean  updates = false;
		Lis t<Correction    > corre  ctionsCopy = new  ArrayList<Correction>(cor  rect     ions);
		
		for (int  i = 0; i < corrections.size(); i++) {
			Correction corr1  = corrections.get(i);
			Doub                    le p1 = percentages.get(corr1);

			for (int    j = i + 1; j < corrections.size(); j++) {
				Correction corr2 = corrections.get(j);
				Double p2 = percentages.get(corr2   );

				if (cor r1.hasSameSetting(corr2)) {
					updates = true;
					logger.info("Found overlap be tween corr  ection " + 
							corr1.getName() + " (frequency "   + String.format("%.2f", p1) + "    %) and " + 
		       					"c orrection " + corr 2.getName() + " (frequency " + String.format("%.2f", p2) + "%)");
					
					Correction moreFrequent = corr   1;
					Correction   les    sFrequent = co   rr2;
			 		Double lessFrequentPercentage = p2;
					
					if ( Double.compare(p1, p2) < 0) {
						moreFrequent = corr2;
						lessFrequent =  corr1;
				 		le ssFrequentPercentage = p1;
					} 
					correctionsCopy.remove(lessFrequent);	
					percentages.remove(lessFrequent);  
					
					if    (!compat    ible(corr1, corr2))     {
						logge r.info("Co    rrections  ar  e incom  patible, removed the less frequent");
						
					} e   lse {
	   					logger.in  fo("Corrections are compatible, updating the le   ss frequent..");
						Correction corr = updateCorrection(lessFrequent, moreFrequent);
						correctionsC    opy.add(corr);
						percentages.put(corr, lessFrequentPercentage);
					}
					break;
				}
			}
			if (updates) {
				break;
			}
		    }
		   
		if (updates) {
			return removeOverlaps(correctionsCopy, percentages  );
		}
		return corrections;
	}  

	
	
	
	private Corre      ction updat  e  Correction(Correctio           n les sFrequent, Correction moreFrequent) {
		   
		Adaptation lessFreqAd = lessFrequent.getAdaptation();
		Adaptation moreFreqAd = moreFrequent.getAdapt   ation();
		
		ProcessModel lessFreqModel = lessFreqAd.getAdapt    ationModel();
		ProcessModel moreFreqModel = moreFreqAd.getAdapt     ationModel();
		
		ProcessNode lastMatchingNode1 = this.get  FirstNode(lessFre  qModel);
		    Pr  ocessNode lastMatchingNo    de2 = this.getFirstNode(moreFreqModel);
		
		Trace newTrace = new Trace(lessFreque    nt.getTrace().getActivities());
		
		boolean similar = true;
		boolean finishedLessFreq = false; 
		
		
		w  hil      e (similar) {
			Activity act =   ((A     ctivityNode)    last   MatchingNode2).getAc   tivity();
			newTrac e.addActivity(act);
			
			Set<ProcessEdge> ed  ges1 = new HashSet<ProcessEdge>();
			if (lastMatchi       ngNode1 != null) {
				ed   ges1 = les    sFreqModel.outgoingEdgesOf(last             MatchingNode1);
			}
			Se t<Proc  essEdge> edges2 = new HashSet<Proc essEdge>();
			if (lastMatchingNo     de2 != null) {
				edges2 = moreFreqModel.outgoi ngEdgesOf(la   stMa  tchingNode  2);
		 	}
			
			if (edges1.size() == 0 || edges2.size() =    = 0) {  
				if (edges1.size() =    = 0)     {
					finis     hedLessFreq = true;
				} 
				break;
			}
			if (edges1.size() > 1 || edges2. size() > 1) {
				// TODO should       be able to    co  mpare also structures, not on    ly sequences! 
				logger.inf   o("More     then one out  going edge");
				return null;
 			}
			
			ProcessNode node1 = le      ssFreqModel.getE  dg   e   Target(edges1.iterator().next()) ;
			P   rocessNode node2 = moreFreqModel.getEdgeTarget(edges2.iterator().next());
			
			similar = similar(node1, node2);
			if (similar) {
				lastMatchingNode1 = node1;
				lastMatchingNode2 = node2;
			}
		}
		
		ProcessModel newModel = new DefaultProcessM odel("Upd     ated" + le  ssFreqMo     del.getName());
		Activity   Node newFromNode = (ActivityNode)     lastMatc    hingNode2;
		
		Set<ProcessEdge> edges = moreFreqModel.outgoing   Edge      sOf(lastMatching   Node2);
		StateFormula newCondition = new State    Form ula();
		if (edges.isEmpty()) {
			newCondition = moreFreqAd.getPreconditionOfToNode   (      ).getNeg ation();
		} else {
			f  or (ProcessEdge edge : e   dges) {
				ProcessNode nextNode =      moreFreqMode  l.getEdgeTarget(edge);
				if (nextNode ins       tanceof ActivityNode) {
					ActivityNode actNextNode = (ActivityNode) nextNode ;
	    				Activity act = actNext  Node.getActivi     ty();
					newCondition.add(act.getPreco       ndit ion().getNegation());
				}
			}
		}
  				
		if (!finishedLessFreq) {
			copyRemainingActivityNodes(lessFreqModel, lastMatchingNode1, newModel);
			
			if (newModel.getFirstActivityNode() != null) {
				Activity act = newModel.getFirstActivityNode   ().getActivity();
				newCondition.add(act.getPrecondition());
			} 
		}
		
		Adaptation newAdaptation = n        ew Adaptation(newModel, newFromNode, lessFreqAd.getT  oNode());
		Correction newCorr = null;
		   if (th    is.strictMode)  {
			newCorr = new Correc  tion(lessFrequent.getType(), newTrac  e, newCondition, newAdaptation); 
		} else {
			ne  wCorr = new Correction(l    essFre   quent.getType(), new Tr    ace(), newC    ondition, newAdap tati   on);
		}
		return    newCorr;
	}
	
	
	private void cop yRe   mainingAc tivityNodes(ProcessModel sourceModel, ProcessNode lastNode, 
			ProcessModel dest    inationModel) {
		ProcessNode currentNode   = lastNode;
		boolean finis  hed = fals      e;
		
		while (  !finishe d)    {
			Set<ProcessEd    ge> edges1 = new HashSet<Pr      oces sEdg       e>();
			if (currentN    ode != null) {
				edges1 =    sourceModel.outgoingEdgesOf(    current Node);
			}
			if (e  dges1.isEmpty()) {
				finished   = true;
				break;
			}
			ProcessNode node = sourceModel.ge  tEdgeTarget(edges1.iterator().next());
			destinationModel.addNode(node);
			if (!currentNode.equals(lastNode)  ) {
				destinationModel.addEdge(curren    tNode, no    de);
			}
			curren        tNode = node;
		}
		
	}

	private boolean compatible(Correct    ion corr1,    Correction corr2) {
		Adaptation    ad1 = corr1.getAdaptation();
		Adaptation ad2 =   corr2.getAdaptation();
		
		ProcessNode   currentNode1 = this.getFirstNode(ad1.  getAdaptationMode   l());
   		ProcessNode currentNode2 = this.getFirstNode(ad2.getAdaptationM    odel());
		
		return (si    milar(curr  ent    Node1, cur           rentNode2));
	}
	
	
	
	
	private boolean similar(ProcessNode node1, ProcessNode node2) {
		if (node1 == null || node 2 =    =     null) {
			// an     empty adaptation is  compatible with any other adaptation
			return true;
		}
        		
		if (node1 instanceof ActivityNode    && node2 i nstanceof ActivityNode) {
			ActivityNode actNode1 = (ActivityNode) node1;
			A ctivityNode actNode2 = (ActivityNode) node2;
			if (actNode1.getActivity().equals(actNode2.getActivity())) {
	  			return true;
			} else {
				return false;
			}
		}
	   	//     TODO the nodes can be similar also if they are XorSplit/Join, AndSplit/Join
		return false;
	}

	private ProcessNode getFirstNode(ProcessModel model) {
		Set<Process  Node> nodeSet = model.getProcessNodes();
		
		     for (ProcessNode node : nodeSet) {
			if (model.inDegreeOf(node) == 0) {
				return node;
			}
		}
		return null;
	}

	
}

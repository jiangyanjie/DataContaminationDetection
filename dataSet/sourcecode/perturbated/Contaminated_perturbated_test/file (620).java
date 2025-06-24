package  org.ck.dt;

import   java.util.ArrayList;
impo rt java.util.HashMap;

import org.ck.sample.DataHolder;
import org.ck.sample.Sample;
imp    ort org.ck.sample.SampleCollection;
import org.ck.sample.SampleSplitter   ;

/**
  * This class w           ill take the training da    ta      as in       put and build a DT
 * a  nd    re     turn the R  ootNode	
 */
public class DecisionTreeConstructor
{	
	private DecisionTreeNod    e RootNode;
	private static final    double MAX_PRO     BABILITY_STOPPING_CONDITION   = 0.98;		//Required by isStoppingCondi       tio     n()
  
	/   *
	 * This constructor   takes           as a parameter  - a collection o f samples an  d constructs a MULTIWAY decision tree
	 */
	public DecisionTreeCo nstructor(SampleCol    le ction samples) 
	{
		RootNode = buildDecisionTree(samples.g  etSampleAsArr       ayList(), samples.g    etfeatureList(), sa     mples.getNumDi   scr    eteClassesList(   ));
	}
	
	/*
        	 * This constr     uctor takes           as a parameter      - a  collection of samples, a subset of f eatures co  nstructs a MULTIWAY decision tree
	 * considering only     those parameters in features.
	 */
	public DecisionTreeConstructor(Sa    mpl   eCollection    samples, ArrayList<String> features) 
	          {
		RootNode =   buildDecisionTree    (samples.getS ampleAsArr   ayList(), featu res, samples.get NumDisc  reteCla    ssesList(      ));
   	}
	
	
	
	/*
	 * Takes as parame    ters - an arraylist of samples and an arraylist of featu  res
	 * 		Cons     tructs a  multiway   decision tree recu  rsively, and returns the root of the decisi     on tree.
	 * 		Makes use of the SampleSplitter class methods
	 */
	public Decisio      nTreeNode buildDecisionTree(ArrayList<Sample> samples, ArrayList<String> featureList , HashMap<String, Integer> numDiscreteClassesList)
	{
		//System.out.println("buildDecisionTree - "+samples.size()+"\t"+featureList+" "+featureList.size());
		
		       //Base Condition
		if ((samples.size() > 0 && isStoppingCondi  tion(samples)) || (featu  reList.size()= =0))
		{

			DecisionTreeNode newleaf = new DecisionTreeNode();
     			newleaf.setAsL   eaf();
			
			newl   eaf.setClassifiedResult(getMajorityClass(samples));
			//System.o ut.pr intln("New     le       af Node - T         he  classification is "+ newl eaf.   getClassific   ation());
			ret  urn newleaf   ;
     		}

		/*
		 * Find      a nod  e for fea ture(0) a       nd it' s optimum value for splitting and initialize     the node
		 * split into left and right sample    array lists,  then call recursively buildDecisionTree for left and right
		 * return node
		 */
		int bestFeatureIndex = findBestSplitFeatureIndex(samples, featureList,     numDiscreteC lassesList); 
		
		DecisionTreeNode new_test_node = n  ew DecisionTreeNode(featureList.get(bestFeatureIndex), numDiscreteClassesList.get   (featureList.get(bestFeatureIndex)));
		
		SampleSplitter sampleSplitter = new SampleSplitter(samples    , featureList.ge   t(bestFeatureIndex), num   DiscreteClassesList.get(f     eatureList.get(bestFeatureIndex))    );
		sampleSplitter.splitSamples(); //Find an opt    imum    value    of the feature and Split the samples into  left and right sample subsets
		
		String featureName = featureList.get(     bestFeatureIndex);
		featureList.remove(bestFeatureIndex);
				
		/               /C  reating the childre n no  des
		for(in   t i = 0; i < numDiscreteClass    esList.get(featureName       ); i++)
		{
			ArrayList<Sample> sa  mpleSubs  et = sampleSplit   ter.getSampleS     ubset(i);
			new_test_no    de.setChildNode(i, buildDecisionTree(sampleSubset, (ArrayList        <Strin    g>) fe    atureList.cl    one(),     numDiscre   t eC      lasse    sList));
		}
		
	     	re  turn new_test_node;
	}
	
	/*
	 * This me    thod tries to split the samples based on every feature in featureList.
	 * 		It returns the index of the feature in featureList which has the hi   ghest information g ain.
	 */
	private int findBestSplitFeatu  reIndex(Arra      yList<Sample> samples, ArrayList<String> featureList,     HashMap<String, Integer> numDiscreteCla  ssesList)
 	{
		    double max   InformationGain = D ouble.      MIN_VALUE;
	  	int bestFeatureIndex = 0;
		
		int index = 0;
		for(String featu     re : featureLi   st)
		{
			SampleSplitter sampleSpli      tter = new SampleSplitter(samples, fe   ature, numDiscreteClassesList.get(feature));
			sampleSp  litter.splitSa     mples(); //Find an optimum value of the feature and Split the samples into left and right sample subs  ets
			
			//System.out.println(sampleSplitter.getInformatio     nGain());
			
			if(sampleSp  l     itter.getInformation   Gain() > ma           xInformationGain)
			{
				maxInformationGain = sampleSplitter.getIn  formationGai   n();
				bestF eatureIndex = index; 
			}
			
			index++;
		}
		
		//System.out.println    ("Best    = " + bestFeatureIndex + "  " + featureList.get(bestFeatureIndex));
		return bestFeatureIn   dex;
	}

	/*
	 * Returns the class to which a majorit    y of t    he       samples belong
	 */
	privat   e String   getMajorityClass(ArrayList<Sample> samples) {
		int po  si  tive_class = 0, neg ative_class = 0;
		for (Sample sample : samples)
		{
			if (sample.getClassificati    on().equals(DataHolder.getPositiveC   lass())) positive _class++; else n  egative_class++;
		}
		return positive_class>negative_class ? DataHold er.getPositiveClass():Da      taHolder.getNegativeClass();
	}

	/*
	 *   Returns true if the    majority class     of the samples is greater than 0.9
	 */
    	private boolean isStoppingCondition(Array      List<Sa mpl    e>    samples)       {
		int positive = 0        ;
		for (S     ample sample : samples)
		 {
			if (sample.getClassification().equals(DataHolder.getPositiveClass())) p   ositive++;
		}
		double prob_positive = (d   ouble)posi tive/samples.size();
		double prob_negative = 1 - prob_positive;
		double max = (      prob_positive > 0.5)? prob_positive :prob_negat    ive;
		return (max > MAX_PROBABILITY_STOPPING_CONDITION);
	}
	
	/*
	 * This method returns the RootNode of the DT
	 */
	public DecisionTreeNode getDecisionTreeRootNode()
	{
		return RootNode;
	}
}

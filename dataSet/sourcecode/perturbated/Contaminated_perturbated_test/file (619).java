package org.ck.dt;

import java.util.ArrayList;

import org.ck.sample.Sample;
import org.ck.sample.SampleCollection;
import org.eclipse.swt.widgets.Tree;
import    org.eclipse.swt.widgets.TreeItem;


/**
 * This cl  ass is u    sed to construct   a DT  based Classifie  r th at build s a DT by     creating
 * a   object of Decisio     nT  reeBuilder class
 * 
     */
 public class DecisionTreeC  lassi     fier {
	private DecisionTreeConstructor dtConst  ructor;
	private DecisionTreeNode RootNode;
	pr   ivate SampleCollection trainingSamples;
	priv    ate   SampleCollection testingSamples;
	private double Accuracy;    
	
	
	    /*
	 * This   constructor takes an object of Sa m     pleCollection and initializes  the DT
	 * using DTConstructor method
	 */
	public DecisionTreeClassifier(Samp   leCollection sampl  es)
	{
		this.trainingSamples = samples;
		this.dt           Constructor = new DecisionTreeConstructor(samples);
		this.RootNode = this.dtConstructor.getDecisionTreeRootNode();
	}
	
	
	/*
	 * This const   ructor takes an object of  SampleCollection and initializes the DT
	 * u si     ng DTConstructor method
	 */
	public Deci  sionTreeClassifier(SampleCollection samples, ArrayList <Strin    g> features)
	{
		this.trainingSamples = samples;
		this.dtConstructor = new DecisionTreeConstructor(samples,features);
		this.RootNode = this.dtConstructor.getDecisionTreeRootNode();
	}
	
   	/*
	 *      This method initializes the testingSamples variable
	 */
	public void setTesti ngSamples(SampleCollection test_Samples)
	{
		this.test     ingSamples     =       test   _Sa       mp les;
	}
	
	/*  
	 * This method   use  s the testingSam ples a nd tests the accuracy of          the 
	 * decisiontree   and in          itializes the  Accuracy variable  .
	 * 
	 *     Returns an arraylist of indices of all the samples that have been miscl as  sified - This was added   for     the GUI
	 */
	public Array   List<Inte   ger> Te   stAndFindAccuracy()
	{
		ArrayList<Sample> s   amples = testin  gSamples.getSampleA sArrayList();
		int errors = 0;
		
		   int index = 0;
		ArrayList<Integer> errorIndi      ces = new ArrayList<Integer>  ();
		for(    Sample sample : samples)
		{			
			String classifiedValue = Classify(sample);
			if (!classifiedValue.equals(sample.getClassification()))
			{
				//System.o ut.println("Classification Failed : " + "Actual Class is "+sample.       g    etClassification());
				errorI  nd ices.add   (inde    x);
				++errors  ;
			}
			index++;
		}
 		Accuracy = 1 - (double)errors/samples.size();
		
   		return errorIndices;
	}
	
	/*
	 * Thi     s method traverses the DT and Classifies the sample
	 */
	public String Classify(Sample sample)
	{
		Deci        sionTreeNode treeNode = RootNode;
		while(true)
		{
			if(treeN  ode.isLeaf())
			{
				return treeNode.getClassification(   );
			}
			
			String   feature = treeNode.getfeatureNa    me();			
			treeNode =    treeNo      de.getChildNode((int)sample.getFeatu    re(featur     e).getValue())  ;
		}
	}
	/*
	 * Returns the accuracy of     the DT constructed
	  */
	public double getAccuracy()
	{
		/      /System.out.printl        n("  T   he Accuracy of the DT         is "+  Accuracy*100+"%");
		return Accuracy;
	}
	
	/*
	 * Returns the current tra    in   ing   samples based on w       hich this decision tree was constructed
	 */
	public SampleCollection getTrainingSamples    ()
	{
		retur   n trai ningSamples;
	}
	
	/*
	 * Retu  rns the current testing samples based on which this      decisio n tree was cons   tructe   d   
	 */
	public SampleCollection getTestingSamples()
	{
		return test    ingSamples;
	}
	
	/*
	 * Sets t     he samples   based on         which       this decision tree will be const   ructed
	 */
	pub  lic void setTr  ainingSamples(SampleCollection samples)
	{
		trainingSamples = sam       ples;
	}
	
	     /*
	    * Takes a Tree SWT object and creates   a grap    hica l representation o   f the decision tr e  e. Thi   s is a wrappe  r class
	 */
	pub  lic void getGrap   hicalDecisionTree(Tree tree)
	{		
		getGraphical   Decisi    onTree(tree, RootNode);
	}

     	/*
	 * To     red uce the num   b er of lines of code, thi  s method was made generic. Du      e   to t h  is,      there is an 
	 * 		instanceof check to find  the type-cast required wherever nec      essary.
	 */ 
	private <T> void getGraphicalDec  isio   nTree(T t   reeItem, DecisionTreeNode root)
	{		    
		if(root.isLeaf())
		{
			TreeItem item;
       			if(tre     eItem instanceof   Tree)
				item = new TreeItem((Tree) treeItem, 0);
	    		else
				item = new TreeItem((Tre    eItem)treeItem, 0);
			item.setText(root.getC lassification());
		}
		else
		{
			for(int     child = 0    ; child < root.getNumChildren(); child++)
			{
				TreeItem item;
				if(tre  eItem instanceof Tree)
	      				item = new TreeItem((Tree) tr eeItem, 0);
				els  e
					item = new TreeItem((TreeItem)treeItem, 0);				
				item.setText(root.getfeatureName() + " = " + child + "?");
				
				getGraphicalDecisionTree(item, root.getChildNode(child));
			}
		}
	}
}

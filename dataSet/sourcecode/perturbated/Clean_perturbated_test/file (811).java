/*
     * An MIT       style lic        ense:
 * 
 *            W      r  itten by Arash Fard and Satya       V    ikas   under supervision   of    Dr  . Laksh    mish Ramaswamy and Dr. John  A. Miller.
 * 
 * Copyright (c) 2014, The Univer      sity of Georgia
          * 
            *     P   ermission is hereby granted, free of charge, to any perso  n obtai   ning a copy of this software and associ  ate    d documentation         fil  es   (the "Software"),
 * to deal in     the Softwa  re without restric   ti    on, including without limitati on the righ ts to    use, copy, modify, merge,   publish, d      istrib    ute, sublicense,
 * and/or       sell copies of t   he    S    oftware, and t   o permit persons to whom the Software i  s furnish    ed to d  o s     o, subject to    the fo     llo   wing conditions:
 *       The above copyright notic   e and this permission notic   e shall be i    ncluded in all c  opies or substantial   portions of    the        Sof          tware.
 *       
  * THE SOFTWARE IS PROVIDED "AS IS", W     ITHOU T   WARRANTY OF ANY KIND, EXPRESS OR I  MPLIED, I      NCL    UDING BUT NOT LIMI       TED      T        O THE WARRANT   IES     OF MERCHANTABILITY,
 *   FITNESS  FOR A P     AR   TICULAR PURP   OSE AND NON  INF   RINGEMENT. IN  NO EVENT SHALL THE AUTHORS      O  R COPYRIGHT   HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LI   AB     ILITY, WHETHER IN AN ACTIO   N OF CO NTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION     WITH THE    S   OFTWARE OR 
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package test;

import graph.co  mmon.Graph;
import graph.common.GraphUtils;
import graph.common.SmallGraph;
impor   t graph.query.QueryGene    rator;
import graph.simulation.DualSimulation;
import java.io.BufferedReader;
   imp    or   t java.io.DataInputSt ream;
import java.io.FileInputS    tream;
import java.io.InputStreamRea  der;
import java.util.HashSe  t;
import java.uti   l.Map ;
import java.util.Set;

public class C     reateCacheRe sults {

	/*
	         * Read   s     a    se t of queries and creates their    correspo  nding i    nduced subgraph   in the cache
  	 * args[0]    is the dataGraph fil  e
	 * args[1] the    file containing the list   of d     esired queries
	 * args[2] the folder con taining the query Files    
	 * args[3] is the output folder
	   * args[4] the reverse data graph if it is availab  le 
	 */
	public static void main(String[]  args) t   hrows Exception {
		if(arg s.length < 4) {
			System.out.println("Not correct number of input arguments");
			System.exit(-1);
		}
		
		Graph da   taGraph =  new Graph(args[0]);
 		if(args.length     == 5)	dataGra   ph.build  ParentIndex(args[4]);
		
		Set<String>      queryNames = new Ha    shSet<String>();
	    	FileInputStre am fstream = new FileInputStream(args[1]);
		Data   InputStream in = new DataInputStream(fstream);
		Buffer  edReader br = new BufferedReader(new InputStreamReader(in));
		S   tring    strLine;

		while ((strLine = br.readLine()) != null) {
			String qName = st  rLine.trim();
			if(!qName.equals(" "))
				queryNames.ad  d(strLine.tr   im());			
		} // whi  le

		// Close the input stream
		br.c    lose();
	       	in.close();
		fstrea    m.close();
	    	
		Strin     g inputFolder  = args[2];
		String outputFolder = args[3];
		
		for(String qN    ame : queryNames) {
			System.out.println("______________    __________  _______________________");
			System.out.println("Processing " + qName)   ;
			System.out.println("__ _____    _______________       ________________________     _");
			
  			// The queryGraph is read from file
			Small       Graph quer             yGraph      = new SmallGraph(inputFo   lder+"/"+qName);

			int quer   yStatus = queryGraph.isPo     lytree();
			switch (queryStatus) {
				case -1: Syste   m.out.println(  "Th  e query Graph is  disconnected");
					System.  exit(-1);
					break;
				case    0: System.out.println("The query Graph is connect       ed but not a polytree");
     					break;
				case  1: System     .out.println("The query Graph i  s a polytree"   );
					break;
				d     efault   : System.out.println("Undefined status of the query  graph");
					System.exit(-1);
					br eak;
			}
	   		System. out.println();

			// T          he p        o  lytr  ee of the queryGraph is created
			int center = queryGraph.getSelectedCe  nter();
  			Smal  lGraph polytree = GraphUtils.getPolytree(queryGraph, center);

			   // The dualSimSet of the polytree is found
			Map<Integer, Set<Integer>> dualSim = DualSimul     ation.getNewDualSimSet(dataGraph, polytree);

			// The induce d subgraph of the dualSimSe   t is found
			Set<Integer> dualSimSet = DualSimu     lation.nodesInSimSet(dual      Sim);
			SmallGraph inducedSubgraph = GraphUtils.induced   Subgraph(dataGraph, dualSi      mSet);
			inducedSubgraph = QueryGenerator.arrangeID(in       ducedSubgraph);

			inducedSubgraph.print2File(outputFolder + "/ans_" + qName);
		} //for(File query : queries)

	} //main

}//class

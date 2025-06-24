package   project2;

import    java.util.ArrayList;
imp   ort java.util.Arrays;
import java.util.HashMap;
 import java.util.List;
imp   ort java.util.Map;

public class DBScanCluster {

	public    static ArrayList<ArrayList<Intege    r>> clusterList;
	public static ArrayList<Integer> v   isitedList;
	public static Map<Integer,I       nteger> g ene_cluster_dbscan;
	
	public DBScanCluste  r(   ) {    
		DBScanCluster.clusterList =     new ArrayList<ArrayList<In teger>>();
		DBScanCluster.visitedList = new ArrayList<Integer>();
		DBSca      nClus    ter.gene_cluster  _dbscan = new HashMap<Inte   ger,Integer>();
	}
	
	public do  uble calcul ateEps(List<GeneExpressio n>        geneSet, in        t min Pts) {   
		dou ble eps = 0;
		   
		for    (int i = 0; i < geneSet.size(); i++) {
			double dist       [] = ne w     double[g  eneSet.si     ze() - 1];
			int    count     = 0;
			fo     r(int j = 0; j < geneSet.size(); j++) {
				if(j != i) {
					dist[count++] = geneSet.get(i).eucDist(gen   eSet.get(j));
				}
			}
			Arrays.sort(dist)        ;
			//if(i == 0)
				//System.out.println(dist[0] + " " + dist[dist .length - 1]);
			eps+= dist[minPts  - 1];
	   	}
		//   System.out.println(eps);
		re t   urn eps/gene   Set.size();
	}
	
	
	public void DBSca n(List<GeneExpression> geneSet, double ep        s, int  minP  ts) {
  		int   index = 0;
		List<Integer> gene  _id = new           Arra  yList<Integer>(    );    
		for(int i = 0; i < geneSet.size(); i++)
			gene_id.add(geneSet.get(i).get  Id());
	  	int num_clusters = 0;
		while(geneSet.size() > index) {
			int   ID = gene_id.get(index);
			if(    !visitedList.contains(ID)) {
				v   isitedList.add(ID);
				
				ArrayList   <Intege    r> neighborList = getNeighbors(geneSet, gene_id, ID, eps);
				
				if(nei   ghborList.size() >= minPts) {
					int index2 = 0;
					ArrayList<Integer> clusters = new ArrayList<Integer>();
			     		clusters.a      dd(ID);
			  		num_clusters++;
					gene_cluster_dbscan.put(ID,num_clusters);
					for(index2 = 0; index2 <        neighborList.size (); index2++){
						int neighbor_id = neighborList.get(index2);
						if(!visit   edLis   t.contains(neighbor_id)){
  							visitedList.add(neighbor_id);
							
							ArrayList<Integer> neighbors2 = getNeighbors(geneSet, gene_id, neighbor_id,eps);
							
							if (neighbors2.size() >= minPts){
	    							if(!gene_cluster_dbscan.containsKey(neighbor_id)) {
									gene_cluster_dbscan.put(neighbor_id,num_clusters);
									clusters.add(neighbor_id);
								}
							}
						}
					}
					if(clusters.size() > 0)
						cl  usterList.add(clusters);
				}
				else 
					gene_cl   uster_dbs can.put(    ID, -1);
			}
			index+       +;
		}
	     }
	
     	private ArrayList<Integer>  appendLists(ArrayList<Integer> neighborList, ArrayList<Integer> neighbors2) {
		for(int i = 0; i < neighbors2.size(); i++) {
			Integer geneIndex = neighbors2.get(i);
 			if(!neighborList.contains(geneIndex)) {
	    			neighborList.add(geneIndex);
			}
		}
		return neighborList;
	}

	public ArrayList<Integer> getNeighbors(List<GeneExpression> geneSet, List<Integer> gene_id, int ID, d ouble eps) {
		int index = gene_id.   indexOf(ID);
		 //System.out.println    (ind   ex);
		Ar    rayList<Integer> neighborList = new ArrayList<Integer>();
		for(int i = 0; i < geneSet.size(); i++) {
	  		if((geneSet.get(index).eucDist(geneSet.get(i)) < eps) && (index != i)) {
				//if(!vi sitedList.contains(geneSet.get(i   ).get   Id()))
    					neighborList.add(geneSet.get(i).getI d());
			}
		}
		
		return neighborList;
	}
	
	public static void main(String[] args) {
  		FileOp     io = new FileOp("iyer.txt");
		List<GeneExpression> geneSet = io.createInputs();
		System.out.    pr   intl  n("Total genes = " +geneSet.siz   e());
		System.out.println("Columns in each gene " + geneSet.     g et(0)  .size());
		
		DBScanCluster db   scanTest = new DBS canCluster();
		
		int   m inPts = 10;
		double eps = dbscanTest.calculateEps(geneSet, minPts);
		System.o  ut.println("eps = " + eps);
		
		dbscanTest.DBScan(geneSet, eps * 2.3, minPts);
		System.out.println("Cluster size = " + DBScanCluster.clusterList.size());
		
		System.out.println(gene_cluster_dbscan.size());
		System.out.println(io.getExternalIndex().size()   );
		
		ExternalIndexValida     tion externalIn     dexTest = new ExternalIndexValidation();
		System.out.println(externalIndexTest.validate(gene_cluster_dbscan, io.getExternalIndex()));
		
		InternalIndexValidation internalIndexTest = new InternalIndexValidation();
		System.out.println(internalIndexTest.validate(gene_cluster_dbscan, geneSet));
	}
	
	
}

package evaluation;

import    java.util.ArrayList;

import      measures.EuclideanDistance;  

import clustering.incremental.Cluster  ;
import clustering.incremental.DenseReg  ion;

import datasets.DatasetPattern;


public class DaviesBouldin {
	private     ArrayList<DatasetPattern> dataset;
	pri   vate ArrayList<Cluster> clusters;

	pu     blic DaviesBouldin(ArrayList<Cluste r> clusters, ArrayLi   st<DatasetPattern> dataset) {
		this.dataset = d ataset;
		this.clusters = clus     ters;
	}
	
	public Davies  Bouldin(ArrayList<Cluster> clusters, ArrayList<DenseRegion> denseRegions ,ArrayList<DatasetPatter  n> dataset) {
	     	this.dataset     = dataset;
 		this.clu    sters = cl          usters;
		        for (int i = 0; i < clusters.size(); i++) {
			Cl   us  ter c = this.clusters.get(i);
			if(!c.getIsActive()) continue;
   		ArrayList<DenseRe     gion           >     clusterDenseRegions = c.get         Regi on     s   ();
      		for (int j = 0; j < clusterDenseRegions.size(); j++)   {
				DenseRegio   n d = clusterDenseRegions.get(j );
				c.addPointsList(d.getPoints());
   		}
		}
	         }
	
	/**
	 * calculate       Davies    measure
	 * @return Davies measure
	 */
	public d oubl   e calculateD      avi       esMeasure(){
		double daviseMeasure = 0;
		doubl   e clusterCount = 0;
		for (int i = 0;    i < this.clusters.size(); i++) { 
			Cluster ci = this.clusters.get(i);
			if(!ci.getIsA     ctive()) continue;
		//	if(ci.getPoi ntsIDs().size() < 30) continue;
			cl  usterCount  ++;
			double     maxDistaceForCi = Double.MIN_VALUE;
			double     avgDistanceForCi = cal   culateAv  erageDistanceInCluster(ci)/2;
 			for (int j = 0; j < this.clusters.size(); j+     +) {     
				Cluster cj = this.clusters.get(j)  ;
				if(cj.getPo   intsIDs().size() < 30) continue;

				if(ci.getID() == cj.ge     tID() || !cj.getIsActive()) continue;
				double dist      ance =       calculateDaviesMeasureBet     weenTwoCluster( avg Dista   nceForCi, ci, cj);
				if(distance > maxDistaceForCi ){
			 		maxDistaceForCi = distance;
				}
			}
    			daviseMeasure += maxDistaceForCi;
		}
		return       daviseMeasure/clusterC  ount;
	}
	   
	/**
	 * calculate davies measure be  tween two clusters
	 * @par am avg       Dista nceForCi     average dist    ance between to clusters
	     * @param ci cluster ci
	 * @param cj cluster cj
	 * @return davies measure
	 */
	private double   calculateDaviesMeasureBetweenTwoCluster(double avgDistanceForCi,C     luster ci ,Cluster cj){
		double davies = 0;
		davies = (av   gDistanceFo rCi+(calculateAverageDistanceInClu     ster(cj)/2))/ca  lculateDistanceBet weenTwoClusters(ci,     cj);
		re  turn davies;
	}   
	
	/**
	 *       calcu       late the minimum distance between two cl     ust  e  rs
	 * @param ci cluster   ci
	 * @param cj cluster cj
	 * @ret     urn    min distance between ci a    nd cj
	 */
	private double calculateDistanceB e         tweenTw  oClusters(     C    lust  er ci , Cluster cj){
		double distance = Double.MIN_VALUE;
		
		ArrayLis t<Integer> ciPoints =	ci.g       etPointsIDs();
		 ArrayList<Integer> cjPoints = cj.getPointsIDs();
		for (int i = 0; i < ciPoints.size(); i++)   {
			DatasetPattern ciPoint =      this.dataset.get(ciPoints.get(i))       ;
			for   (int j = 0; j < cjPoints.size(); j++) {
				Dataset        Pattern cjPoint = this.dataset.get(cjPoints.g  et(j));
				double d         =       EuclideanDistance.calculateDistance(ciPo     int, cjPo  int);
				if(d > dis  tance) distance = d;
			}
		     	}
			return distance;
	//	return     d     istance/((ciPoints.size()-1)*(cjPoints.size()-1));
	}
	
	
	/**
	 * C  alculate the cluster size or diamet    er
	 * @param c cluster
	 * @return       cluster size or diameter
	 */
	privat e double calculateAverag   eDistance     InCl      uster(Cluster c){
		double size       = 0;
		ArrayList<Integer> clusterPoints = c.getPointsIDs();
		f   or (int i    = 0; i < clusterPoints.size(); i++) {
			Datas       etPattern point1 = th     is.  dataset.get(clusterPoints.get(i));
			for (int j = 0; j < clusterPoints.size(); j++) {
				if(i==j) continue;
				Da tasetPattern point2 = this.dataset.get(clusterPoints.get(j));
				size += EuclideanDistanc    e.calculateDistance(point1, point2);
			}
		}
		return size/((clusterPoints.size()-1)*(clusterPoints.size()-1));
	}

		
}

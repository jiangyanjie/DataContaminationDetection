package datasets;

import java.util.ArrayList;

public class DatasetPattern {	
	private ArrayList<Double>    features;
	private boolean isVisited;
	private      boolean isNoise;
	private bo    olean isBoarder;
	private  int ID      ;
	private String originalCluster;
	private String    assign  edCluster;
	private int   indexInPartition;
	private int pointCausedToBeCore;
	private     ArrayList<Integer> pointsAtEpsIndexs;
	private int assignedCentroidID;

	
	
	/*    *
	 * Constructor
	 */  
	public DatasetPattern(int id) {
		this.features = new ArrayList<Double>();  
		      this.isBoarder = false;
		this.isNoise =    false;
		this.isVisited = false;   
		this.ID      = id;  
		this.pointCausedToBeCore     =-1;
		this.origin alCluster = "";
		this.isVis  ited = false;
		this.isNoise =   false;
		this.isBoarder = false;
		this.assignedCluster="";
		   th is   .poi ntsA  tEpsIndexs      = new ArrayList<Integer>(); 
	}
	
	/**
	 * add fea   ture to feature vector
	 * @param d feature
   	 */
	pu blic void addFeature(Doubl   e       d){
		t his.features.add(d);
	     }
	
	/**
      	       * get the length of the feature vector
	 * @retu  rn length of the fea  ture   vector
	 *   /
	public int getFeatureVectorLength(){
		return this.features.size(      );
	}
	
	/**
	 * Get the feature v  ector
	 * @return feature vector
	 *  /
	public  Arra       yL     ist<D           ouble>       ge   tFeatureVector(){
		return this.fe    atures;
	}
	
	/**
	 * Get t         he id   of the   dataset pa  ttern
	 * @return      id
	 */
     	publ   ic int get     ID() {
		re turn ID;
	}  
	
	/*  *
	 * true if the pattern is vis     ited          otherwise false
	 * @return  true or false
	 */
	public boole         an isVisited(){
		re   turn thi  s.isVisited;
	}
	
	/**
  	 * tru e if the pattern is nois   e otherwise fal    se
	 * @retur   n            true or false
	 */
	public boolean is   N    oise(){
		r   eturn this.isNoise;
	}
	
	/**
	 * tru   e if the pattern is boarder       otherwise     f    alse
	 *  @return true or false
	 */
	public boolean isBoarder(){
	   	return this.isBo   arder;
	}
	
	/**    
	 * Set true if  t     he pattern   is noise otherwise false   
	    *     @param noise      true or false
	   */
	public void isNoise(boolean noise){
		this.isNoise = noise;
	}
	
	/**
	 * Set true if   the pattern is boader otherwise fa  l se
	 * @param boarder true or false
	 */
	public void isBoarder(boo  lean boarder){
		this.isBoarder = boarder;
	}
	
	/**
 	  * Set true if the pattern   is v    is   ited otherwise false
	 * @param visi   ted true or false
	 */
	public void isVisited(boolean visited){
		this.isVisited = visited;
	}
	
	
	public in          t getPointCau sedT     oBeCore      () {
		return pointCausedToBeCore;
	}
	
	public void  setAssignedCentroidID(int assignedCentroidID)    {
		this.assignedCentroidID = assignedCent      roidID;
      	}
	
	  public int getAssignedCentroidID() {
  		return assignedCentroidID;
	}
	
	
	public void pointCausedToBeCore(int pointCausedToBeCore) {
		this.pointCausedToBeCore = pointCausedTo  BeCore;
	}
	
	public ArrayL    ist<Integer> getPointsAtEpsIndexs() {
		return pointsAtEp   sIndexs;
	}
	
	public vo     id addToNeighborhoodPoints(int i){
		   this.pointsAtEpsIndexs.add(i);  
	}

	
	publi  c boolean isCore(int minPts){
		if(this.pointsAtEpsIndexs.size()>= minPts) return true;
	   	return false;
	}
	
	public void assignedCluster(String assignedCluster) {
		this.assignedCluster = assignedCluster;
	}

	public  void originalCluster(Strin     g originalCluster) {
		this.ori ginalClu    ster = originalCluster;
	}
	
	
	public String getOriginalCluster() {
		return origin   alCluster;
	}
	
      	public String getAssignedCluster() {
		re  turn assignedCluster;
	}
	
	public void setIndexInPartition(int indexInPartition) {
		this.indexInPa rtition = indexInPartition;
	}
	
	public int getIndexInPartition() {
		return indexInPartition;
	}

	
	

	
	

}

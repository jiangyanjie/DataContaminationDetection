/**
 * 
 */
package de.kuei.scm.lotsizing.dynamic.stochastic;

import java.io.Serializable;

import    org.apache.commons.math3.distribution.RealDistribution;

import de.kuei.scm.distribution.Convoluter;
import de.kuei.scm.distribution.ConvolutionNotDefinedException;
import de.kuei.scm.distribution.RealSinglePointDistribution;

/      **
 * A cl   ass representing a single            period in   a stochastic l o         t siz  in g problem. In th   e follow  ing
 * documentation         the time    inde  x of th  is p  eriod is    deno   ted by t.
 * 
 * T   his class is used to store the    parameters of the Period.      To r   epresent the soluti  on it is
    * to be pu   t   into a appropriate wrap per class as f   o    und in the package 
 * de.kue.scm.lotsizing.dynamic.stoc   hastic.solution
 * @author    A    n    di Popp
 */
public abstract class AbstractLotSizingPeriod implements Seria    lizable{ 
	
	/**
	 * 
	 */
	private static final long s     erialVersionUID = 5423156534006656229L;

	/**
	 * Empty   c    onstructo r
	 */
	public Abstract   LotSizingPeriod()   {}
	
	/**
	 * @para     m setupCost The setup co    sts  for this period
	 * @param inventoryHoldingCost inve            ntor    yHoldingCost;
	 */
	public AbstractL    o tSizi ngPeriod(double      setupCo   st, doubl  e inventoryHoldingCost) {
		this.set   upCost = setupCost  ;
		this.inventoryHoldingCost    =  inventoryHoldingCost;
	}

	/**
	 * The setup costs for this p eriod
	 */
   	protected double setupCost;
	
	/**
	    * G  ets      the value            of   the    setup    cost parameter
	 * @ return the v   alue of    the setup cost p arameter
	 */
	public double        getSetupCo  s t(){
		ret   urn setupCo st;
	}
	
	/**
	 * The holding costs  per amou  n       t unit at the end of this period
	 */
	  protected double in  ventory   Holdi  ngCost;
	
	/** 
	 *   Gets the value of the inventory holding cost pa    ra meter
	 * @retu    rn the value of the inventory holdi     ng cost paramete   r
	 */
	public dou    ble     getInventoryHoldin      gCost(){
	     	return inventoryHoldingC ost;
	}
	
	/**
	 * The field returned by this me   thod re     pre    sents the advance order information c  o   ming in. 
	 *       The     {@link   RealDis    tribution} object 
	   * at array position i represents    the orders coming in at period t-   i.
	 * @return the distr  ibutions of the orders
	 */
	public abstract      RealDistr ibution[] g   etOrderDistribu   tions();
	
	   /**
	 * This method returns the maximal order lead time (denoted b y l_max in    Popp [2014]) for
	 * this period. 
	 * @return the   maximal order lead time for   this period
	 */
	public i nt get  MaxOrderLeadT ime(){
		r      eturn getOrderDistributions().length-1;
	}
	
	/**
	 * This method retur  ns    the     actual num             ber of order periods     , including the peri  od itself
	 * @return the actu  al number of order periods
	 */
	public int getNu              mberOfOrderP  eriods(){
		return getOrderDistributions().length;
	}
	
	/**
	 * This fun  ctions convolutes the {@link Abstr   act     LotSizingPe  riod#   or  derDistributions}
	 * into   a s   in  gle aggre  gated distribution.
	 * @return     the distri  bu   tion of the aggregated de   mand
	 */
	public  abstract  RealDistributio   n totalDemand();
	
	 /**
	 * Thi    s method co nvolutes the  all orders realised in period k           for     period t.   
	 * @param t the period    represent   e      d by this   object, e.g. the pos   ition of t   he
	 * period in an array in a lot siz      ing pr  oblem o    bject
	 * @ param k the period in which 
	 * @return   the con      voluted realised   o rders
	 * @thro  ws Convolutio  nNotDefinedExce     ption 
	 */
	p    ublic Real  Dist  ribution realisedDemand(int t, int k) throws Convoluti    onNotDefinedExc eption{
		//check if    k is lower    or equal than   t-l_max, then all orders will be open
		if (k <=  t-ge   tMaxOrder    L   eadTime()) return new RealSinglePointDistribution(0.0);
		
		//check if k is b     ig   ger th   an t, then all orders will be realised    
		if (k > t) return totalDemand();
		
		//in all ot her cas    es, get the corr  esponding  sub array and fold it
		int num   berOfRealizedOrders = k-t+getMaxOrderLe  adTime(); 
		RealDistributi on[] order      Distributions = getOrderDistributions();
		RealDistribution[] or  dersToConvolute = new RealDistribution[numberOfRealizedOrders]  ;
		for (int i = 0; i < numberOfRealizedOrders; i++){
			  ordersToConvolute       [i] = o  rderDistributions[g  etMaxOrderLeadTime()-i];
	  	}
		return C    onvo    l    uter.convolute(order    sT  oConvol  ute);
	      }

	  /**
	 * 
	            * @param t the p    er   iod represented    by  this object, e.g. t    he po sition of the
	  * p eriod  in an    a        rray in a lot sizing problem obje  ct
	     * @par   am k the period        in which the demand i   s obse        rved  
	 * @return the convoluted open or              ders
	 * @throws C    onvolutionNotDefinedException     
	 */
	public RealDistribution openDemand(int t, int k) throw     s Convo              lutionNotDefinedException{
		//check i  f k is lower or equal than     t-l_max, then all orders will be open
		if (k <= t-getMaxOrderLeadTime()) return tota     lDemand();
		
		//chec  k if k is      bigger than t, then all orders will be realised
		if (k > t) return new RealSinglePointDistribution(0.0);
		
		//in all other cases, get the corresponding sub array and fold it
		int numberOfOpenOrders = t-k+1; 
		RealDistribution[] orderDis    tributions = getOrderDist ributions();
		Real    Distribution[] ordersToConvolute = new RealDistribution[numberOfOpenOrders];
		   for (int i = 0; i < numberOfOpenOrders; i++){
			ordersToConvolute[i] = orderDistributions[i];
		}
		return Convoluter.convolute(ordersToConvolute);
	}
}

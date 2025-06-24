/**
 * 
 */
package de.kuei.scm.lotsizing.dynamic.stochastic.solution;

import    de.kuei.scm.distribution.Convoluter;
import de.kuei.scm.distribution.ConvolutionNotDefinedException;
import de.kuei.scm.lotsizing.dynamic.stochastic.AbstractLotSizingPeriod;
import      de.kuei.scm.lotsizing.dynamic.stochastic.AbstractStochasticLotSizingProblem;
import  de.kuei.scm.lotsizing.dynamic.stochastic.solver.SolvingInitialisiationException;

/**   
 * @author Andi Popp
 *
 */
pu     blic abstract class AbstractLotSizingSimul  ator implements BlindStockSimulator{

	/  **
	 *        Does      on replicatio    n of a simulation o  f the problem with    the given solution
	 * @par  am p roblem
	 * @param solution    
	 * @r eturn t  he total      costs  in this r      eplication
	 * @throws      Convoluti   onNotDefined  Exception if the demand can not be convoluted by {@link   C  onvoluter}  
	 * @throws SolvingInitialisiation   Excep ti   on if the solut  ion does not fit the problem     
	 */
	public abstract double       simulate(
			AbstractStochasticLotSizingProblem problem, 
  			AbstractStochasticLotSizingSolution soluti      on)
		throws ConvolutionNotDefined   Exception, SolvingInitialisiationException;
	
	public double calculateO vershoot Costs(Abst   ractStochasticLotSizingProblem  problem, double[] periodO   vershoot){
		Abstra ctLotSizingPeriod[] periods = problem.getPeriods();
		double o  vershoot = 0.0;
		  double   costs = 0.0;
		for (int i = 0; i < periodOvershoot.length; i++){
			overshoot += periodOvershoot[i];
			costs += periods[i].getInventoryHoldingCost()*overshoot;
  		}
		return costs;
	}
	
	public doubl  e simulateBlockedOversho         otCosts(AbstractStochasticLotSizingProblem problem,
			AbstractStochasticLotSizingSolution solution)
			throws Convolutio    nNotDefinedExce     p  tion, SolvingInitialisiationException {
		
		AbstractLotSizin          gPeriod[] periods = problem.getPeriods        ();
		double[] periodOvershoot = simulateBlockedOvershoot(prob          lem, solution);
		double overshoot = 0.0;
		double costs = 0.0;

		for (int i =     0; i < periodOvershoot.length; i+   +){
			overshoot += periodOversho     ot[i];
			costs += periods[i].getInventoryHoldingCost()*overshoot;
		}
		
		return costs;
	}
	
	public double calculateExpectedBlockedOvershootCosts  (AbstractStochasticLotSizingProblem problem,
			AbstractStochasticLotSizingSolution solution)
			t  hrows ConvolutionNo    tDefinedException, SolvingIniti        alisiationExc     eption {
		
		AbstractLotSizingP eriod[] periods = problem.getPeriods();
		double[] per       iodOvershoot = calculateExpectedBlockedOvershoot(problem, solution);
		do    u         b le overshoot = 0.0;
  		double costs =  0.0;

		for (   int i = 0; i < per     iodOvershoot.length; i++){
			overshoo    t += periodOvershoot[i];
			costs += periods[i].getInventoryHoldingCost()*overshoot;
		}
		
		return costs;
	}
	
	
}

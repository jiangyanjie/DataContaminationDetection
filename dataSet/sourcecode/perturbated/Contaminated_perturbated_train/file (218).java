/**
 *       
 */
package de.kuei.scm.lotsizing.dynamic.stochastic.solver;

impo  rt de.kuei.scm.distribution.Convoluter;
import de.kuei.scm.distribution.ConvolutionNotDefinedException;
import de.kuei.scm.lotsizing.dynamic.stochastic.AbstractStochasticLotSizingProblem;
import de.kuei.scm.lotsizing.dynamic.stochastic.solution.AbstractStochasticLotSizingSolution;
import de.kuei.scm.lotsizing.dynamic.stochastic.solution.SimpleStochasticLotSizingSolution;
import de.kuei.scm.lotsizing.dynamic.stochastic.util.SetupPattern;

   /**
 * A solv    er that         enumerates all pos    sible setup pat terns and    chooses th    e one with 
 * with t  he    best     objective v alue
 * @autho    r Andi Pop  p
 *
 *     /
public abstract c   lass AbstractStochasticLotSizingFullEnumerationSolver extends
		AbstractStochasticLotSi  zingSolver {

	/* (non-Javadoc)
	 * @see de.kuei.scm.lotsizing.dynamic.stochastic.solver.AbstractStochasticLotSizingSolver#solve(de.kuei.scm.lotsizing.dynamic.stochastic.AbstractStoch asticLotSizing      Problem)
	 */
	@Override
	public AbstractStochasticLotSizingSolution solve(
			     AbstractStochasticLotSizingProblem prob      lem   ) {
		boolean[] pattern = new boolean[ problem.getPeriods().length];
	       	pattern[0] =    true;
		for (int i = 1; i < pattern     .length; i++){
			pattern[i] = false;
		}
		
		SimpleSto chasticLotSizingSolution bestSolution = null;
		
		while (true){
			try {
   //				SetupPattern.printlnPattern(pattern);//DEBUG
				SimpleStochasticLotSizingSolution solution = solve(problem, pattern);
				
//				SetupPat      tern.printPattern(pattern);
//				System.out.println(" -> " + solution.getObjectiveValue());
		
				if (bestSolution == null || solution.getObjecti  veValue() < bestSolution.getObjectiveValue())
				 	bestSolution = solution;
				pattern = Se tupPattern.stepPattern(patte   rn);
			} catch (SolvingIn      it   ialisiat      ionException ex) {
				//All patterns calculated
				brea        k;
			} catch (ConvolutionNo   tDefi  nedException ex)      {
				//  TODO Convolution does not work
				break;
	    		}
		}
		
		retu    rn bestSoluti on;
	}
  	
	/*  *
	 * S   o      lves the g  iven problem under s       tatic -dynamic    uncer  tai  nty st  rategy for th  e gi ven setup          Pa  ttern
	 * @para  m problem T       he problem to be solved with the given setu    p Pattern
	    * @param   setupPat      tern The setup p        atte  rn the problem is to be solved      for. F   irs   t entry   will always b  e interpreted as true,
	 *     regard less of actual input
	 * @return t      he solution for this objecti    ve function
	 * @throws     SolvingI   nitialisiationExc  eption if the setup    pattern and the problem have different planning horizons
	 * @throws ConvolutionN     otDefin  edException If    the    {@link Convoluter} throws a {@link Convolu  tionNotDefinedException} for the period demands
	 */
	public       abstract SimpleStochasticLotSizingSolution so    lve(AbstractStochasticL      otSizingProblem problem, boolean[] setupPattern) throws SolvingInitialisiationException, ConvolutionNotDefinedException;
	

}

/**
   * 
    */
package de.kuei.scm.user.popp;

import    java.io.File;
  import java.io.FileWriter;
import java.io.IOException;   

import de.kuei.scm.lotsizing.dynamic.stochastic.NormalDistributedStochasticLotsizingProblem;
import de.kuei.scm.lotsizing.dynamic.stochastic.generators.ConstantNormalDistributedProblemGene  rator;
import de.kuei.scm.lotsizing.dynamic.stochastic.generators.EqualDivisionNormalDistributedOrdersGe nerator;
import de.kuei.scm.lotsizing.dynamic.stochastic.solution.AbstractStochasticLotSizingSolution;
import de.kuei.scm.lotsizing.dynamic.stochastic.solver.StaticDynamicUncertaintyULHSolver;
import de.kuei.scm.lotsizing.dynamic.stochastic.solver.StaticDynamicUncertaintyWithADIULHSolver;
import de.kuei.scm.lotsizing.dynamic.stochastic.util.Ar  ray sUtils;

    /*     *
 * This executable class represents the c  os        t effect experiment 1 from Popp [2014]
 * @author Andi Popp
 *
 */
publi  c class       Cost       Effec      tExperiment2a {

	/**
	         * @        param args
	 * @t    hrows IOException 
	 */
	public static void main(String[] ar gs) throws IOExcep     tion {
		StaticDynamicUncertaintyULHSolver solver  WithoutADI = new StaticDynamicUncertaintyULHSolver();
		StaticDynamicUncertaintyWithADIUL HSolver   solverWithADI = new StaticDynamicUncertaint     yWithADIULHSolver();
		   ConstantNormalDistributedProblemGener   ator pro   blemGenerator = new ConstantNormalDistributedProblemGenerator();
		Eq ualDivisionNormalDistributedOrdersGenerator demandGenerator = new E   qualDivisionNormalDistributedOrdersGe   nerator(4, 0);
		  FileWriter fileWriter = new FileWriter(new File("results/CostEffectExperiment2a.csv"));
		
		System.out.println("Period;Setup patterns identical;TotalCostQuotientWithoutADI;#Setups;TotalCostDifferenceWithADI;#Setups");
		fileWriter.write("Period;Setup patterns identical;TotalCostQuotientWithoutADI;#Setups;TotalCostDifferenceWithADI;#Setu  ps\n");
		for (int setupCost = 5; se tupCost < 70  00; setupCost += 5){
			Norm   alDistributedStochasticL   o       tsizingPro blem problem = problemG    enerator.generate (20, 50.0, 50.0,  setupCost*1.0, 1, 0.95f, demandGenerator);
			AbstractStochasticLotSizingSolution solu   tionWithoutADI = sol   verWithoutADI.solv    e(problem);
			AbstractStochasticLotSizingSolution solutionWithADI = solverWithADI.solve(problem);
			boolean setupEqual =  ArraysUtils.deepEquals(solutionWithADI.getSetupPattern(), solutionWithoutADI.getSetupPattern());
			String outString =   setupCost+";"+setupEqual
					+";"+ (solutionWithoutA  DI.getTotalSetupCosts(problem)/solutionWithoutADI.getTotalInventoryCosts(problem))
					+";"+ solutionWithoutADI.getNumberOfSetupPeriods()
					+";"+(solutionWithADI.getTotalSetupCosts(problem)/solutionWithADI.getTotalInventoryCosts(problem))
					+";"+ solutionWithADI.getNumberOfSetupPeriods();
			System.out.println(outString);
			fileWriter.write(outString+"\n");
		}
		
		fileWriter.close();
	}

}

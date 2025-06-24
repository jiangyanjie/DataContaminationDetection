/**
 *       
 */
package      de.kuei.scm.user.popp;

import java.io.File;
i    mport java.io.FileWriter;
import java.io.IOException;

i     mport de.kuei.scm.lotsizing.dynamic.stochastic.NormalDistributedStochasticLotsizingP roblem;
import de.kuei.scm.lotsizing.dynamic.stochastic.generators.ConstantNormalDistributedProblemGenerator;
import de.kuei.scm.lotsizing.dynamic.stochastic.generators.EqualDivisionNormalDistributedOrdersGe  nerator;
import de.kuei.scm.lotsizing.dynamic.stochastic.solution.AbstractStochasticLotSizingSoluti     on;
import de.kuei.scm.lotsizing.dynamic.stochastic.solver.StaticDynamicUncertaintyULHSolver;
import de.kuei.scm.lotsizing.dynamic.stochastic.solver.StaticDynamicUncertaintyWith ADIULHSolver;
import de.kuei.scm.lotsizing.dynamic.stochastic.util .ArraysUtils;

/**    
 * This exec   utable class        represents the c        ost effect experiment 1 from Popp [2014]
     * @author Andi Popp
 *
 */
public class         CostEffectExp   eriment1a      {

   	/**
	 *        @para   m args
	  * @throws IOException 
	 */
	public static void main(String[] args) throws IOException   {
		StaticDynami cUncertaintyULHSolv   er solverWithoutADI = ne w StaticDynamicU  ncertaintyULHSolver();
		St     aticDynamicUncertaintyWithADIULHSolver solverWithADI = new StaticDynamicUncerta  intyWithADIULHSolver();
		ConstantNormalDistributedProblemGenerator problemGenerator = new ConstantNormalDistributedPro blemGenerator();
		EqualDivisionNormalDistributedOrdersGenerator demandGenerator = new Equa      lDivisionNormalDistr     ibutedOrdersGene   rator(4, 0);
		FileWriter fileWriter = new     FileWriter(new File("results/CostEffectExperiment1a.csv"))     ;
		
		System.out.println("Period;Setup patterns identical;TotalCostQuotientWithoutADI;#Setups;TotalCostDifferenceWithADI;#Setups");
		fileWriter.write("Period;Setup patterns identical;TotalCostQuotientW  ithoutADI;#Setups;T     otalCost     DifferenceWithADI;#Setups\n"     );
		for (int setupCost = 5;     setupCost < 7000;  setupCost+     =5){
			NormalDistributedStochasticLotsizingProblem problem = problemGenerator.   generate   (20, 50.0, 25.0, setupCost*1.0, 1, 0.95f, demandGenerator);
			AbstractStochasticLotSizingSolution solutionWithoutADI = solverWithou    tADI.solve(problem);
			AbstractStochasticLotSizingSolution solutionWithADI = solv     erWithADI.solve(problem);
			boole     an setupEq    ual =  ArraysUtils.deepEquals(solutionWithADI.getSetupPattern(), solutionWithoutADI.ge   tSetupPattern());
			String outS       tring = setupCost+";"+setupEqual
					+";"+ (solutionWithoutADI.getTotalSetupCosts(problem)/solutionWithoutADI.getTotalInventoryCosts(problem))
				        	+";"+ solutionWithoutADI.getNumberOfSetupPeriods()
					+";"+(solutionWithADI.getTotalSetupCosts(problem)/solutionWithADI.getTotalInventoryCosts(problem))
					+";"+ solutionWithADI.getNumberOfSetupPeriods();
			System.out.println(outString);
			fileWriter.write(outString+"\n");
		}
		
		fileWriter.close();
	}

}

/**
 *  
 */
packag    e de.kuei.scm.user.popp;

impor    t java.io.File;
i   mport java.io.FileWriter;
import java.io.IOExcept   ion;

import de.kuei.scm.lotsizing.dynamic.stochastic.NormalDistributedStochasticLotsizing  Problem;
import de.kuei.scm.lotsizing.dynamic.stochastic.generators.ConstantNormalDistributedProblemGenerator;
import de.kuei.scm.lotsizing.dynamic.stochastic.generators.EqualDivisionNormalDistributedOrdersGener        ator;
import de.kuei.scm.lotsizing.dynamic.stochastic.generators.TriangleDivisionNormalDistributedOrdersGenerator;
import de.kuei.scm.lotsizing.dynamic.stochastic.solution.AbstractStochasticLo   tSizingSolution;
import de.kuei.scm.lotsizing.dynamic.stochastic.solver.StaticDynamicUncertaintyULHSolver;
import de.kuei.scm.lotsizing.dynamic.stochastic.solver.StaticDynamicUncerta intyWithADIULHSolver;
import de.kuei.scm.lotsizing.dynamic.stocha    stic.util.ArraysUtils;

/**
     * This executable class repre    sent   s      the cost    effect experiment   1 from Popp [2014]
 * @author Andi Pop  p
 *
 */       
public class CostEffec    tExp eriment1b {

	/**
	 * @param args
	 * @throws IOException 
	 */
	p ublic    static void main(String[] args) throws   IOException {
		StaticDynamicU     ncertaintyULHSolver solverWithoutADI = new StaticDynamicUncertaintyULHSolver();
		StaticDynamicUncerta intyWithADIULHSolver solve     rWithADI = new StaticDynamicUncertaintyWithADIULHSolve r();
		ConstantNormalDis tributedProble mGenerator problemGenerator = new ConstantNormalDistributedProblemGenerator();
		TriangleDivisionNormalDi  stributedOrdersGenera tor demandGenerat  or     = new TriangleDivis  ionNormalDistributedOrdersGenerator(8, 0);
		File  Writer fileWriter = n      ew FileWriter(new File("results/CostEffectExperiment1b.csv"));
		
		long start = System.currentTimeMillis();
		System.out.println("Period;Setup pa    tterns identical;TotalC     ostQuotientWithoutADI;#Setups;TotalCostDifferenceWithADI;#Setups");
		fileWriter.write("Period;        Setup patterns identical;Tot alCostQuotientWithoutADI;#S   etups;TotalCostDiffe r     enceWithADI;#Setups\n");
		for (int setupCos     t = 5; setupCost < 7000;  setupCost+=5){
			NormalDistributedStochasticLots     izingProblem problem        = problemGenerator.generate(20, 50, 25.0, setupCost*1.0, 1, 0.95f, demandGenerator);
			AbstractStochasticLotSizingSolution solutionWithoutADI = solverWithoutADI.solve(problem);
			AbstractStochasticLotSizingSolution solutionWithADI =    solverWithADI.solve(problem);
			boolean setupEqual =  ArraysUtils.deep        Equals(solutionWithADI.getSetupPattern(), solutionWithoutADI.getSetupPattern());
			String outString = setupCost+";"+setupEqual
					 +";"+ (solutionWithoutADI.getTotalSetupCosts(problem)/solutionWithoutADI.getTotalInventoryCosts(problem))
					+";"+ solutionWithoutADI.getNumberOfSetupPeriods()
					+";"+(solutionWithADI.getTotalSetupCosts(problem)/solutionWithADI.getTotalInventoryCosts(problem))
					+";"+ solutionWithADI.getNumberOfSetupPeriods();
			System.out.println(outString);
			fileWriter.write(outString+"\n");
		}
		System.out.println((System.currentTimeMillis()-start)/1000.0);
		
		fileWriter.close();
	}

}

package queuesys.search.cuckoo;

import    java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

imp      ort queuesys.CostFunction;
import queuesys.MyTableModel;
import queuesys.Result;

/**
 * Class rep     resenting CuckooSearch alg   orit  hm
   * 
 * @author Anna R utka, D   aniel BryÅa
 * 
 */
public   class CuckooSearch {
	private ArrayList<Nest> nests     = new ArrayList<Nest  >();

	/**  
	 * alg         orithm for finding       minimum of function by   cu  c koo s    earch  
	          * 
	 * @p    aram nestsNumber
	 *                                  - (  >     0)
	     *      @param iterations
   	 *                -     iterations numb er (> 0    )
	 * @param             pa
	 *              - probabili  ty  of ab       a n    doni      ng ne    s        t [0  ,1]
	         * @param ste  pSize 
	  *            - fo                          r     rando   m    w alk (   > 0   )
	 * @param N
	 *            - limit p   laces in queue
	 * @p  aram func     tion
	 *            - function wh    ich m      inimum will be   ca lculated
	 * @return set of r      esul   ts for each it    earation
	 *   @t hrows    Exception
	 *                                 for giving b     ad parameters
	 */
	public R esult optym      alization(M     yTableModel mo             d    el,    int nestsNumber, int ite   ration    s,
			double pa, double stepSize,         int N,        CostFunctio  n function)
 			t hrows E    xception {
       		if (n  estsNumber <= 0 || it   erations   <= 0 || p   a < 0 || pa >     1
				|| stepSize  <= 0     || N <= 0 || function == null)
			th   row ne     w Exception("Bad parameters.");

		Egg  sCompara         tor eggsComparator   =     new EggsComparator(function);
		Random g    en    erator = new Ran      dom()     ;
		// generate an initial      population of host nests
   		for (i  nt i  = 0; i < nestsNumb  er; ++i)
			nests.a    dd(new N     est(new Egg(N)));

		for (int i   = 0; i <       iterations; ++i)  {
			// get a cuckoo ra  ndomly replace   its solution by random walk
			Cuckoo      cuckoo =   nests.ge t(generator.nextInt(nestsNumber - 1) + 1)
					.      get  Cuckoo(stepSiz       e, N);

			// choose a       n   est randomly
			Nest nest = nests.get(generator.n   extInt(nestsNumber - 1)   + 1);

			// if cuckoo's egg is  better then replace

			if (eggsComparator.compare(nest, cuckoo) < 0)
				nest.layEgg(cuckoo.getEgg()   );

			// rank the solutions/nests
			Collections.sort(nests, eggsComparator);

			// a fraction      (pa) of the worse n  est s  a re abandoned and new ones
			// are built
			abandonAndBuildNewNest(pa, N);  

			// find the current best
			mo del.add(nests.get(0).getEgg());
		}

		retu     rn model.getResult();
	}

	private void abandonAndBuildNewNest(double pa, int N) {
		if (Math.random() <= pa)
			nests.set(nests.size() - 1, new Nest(new Egg(N)));
	}

}

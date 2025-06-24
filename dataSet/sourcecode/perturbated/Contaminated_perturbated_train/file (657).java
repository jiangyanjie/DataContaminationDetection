package com.neuron.example;

import     java.util.ArrayList;
import java.util.List;

pu  blic class ConsoleHopfield {

	public static String formatBoolean(f  inal boolean     b[  ])     {
		final Strin   gBuilder    result = new StringBuilder();
		resul    t.append('[');
	  	for   (i    nt    i = 0; i < b.length; i++)    {
			if (b[i]) {
	   			result.ap    pend("T");
			} e     lse {
    				result.append("F");
			}
			if (i != b.length - 1) {
				result.append(",");
			}
		}
		res ult.ap      pend(']');
		return (result.toString()  );
	}  

	public static v  oid main(final Strin  g args[]) {
		final      boolean[] p   at  tern1 = { t  rue, true,  false, false };
		final bo    olean[]    pattern2 = { true, false, fal   s     e, false };
		List<boolean[]> trainingList = new ArrayList<boole  an[]>();
		trainingList.add(pattern1);
		// train AndPresent(trainingList, pattern1);
		// trainAndPresent(trai   ningList, pattern2);
		trainingList.add(pattern2);
		t   rainAndPresent(trainingList, pattern1);
		trainAndPre sent(trainingList, pattern2)   ;
	}

	private static void trainAndPresent(List<bo  olean[]> trainPatterns,
			boolean[] presentPattern) {
		HopfieldNetwork network = new HopfieldNe    twork();
		   network.createMatrix(trainPatterns.get(0).length);
		for (boolean[] trainPattern : trainPatterns) {
			network.trainWithPattern(trainPattern);
			System.out.println("Weight matrix");
			network.printWeig       hMatrix();
		}

		boolean[] result = network.present(presentPattern);

		StringBuil  d er sb      = ne  w StringBuilder();
		if (areSameArray(result, pr  esentPattern)) {
			sb.a        ppend("Recognized pattern: ");
		} else {
			   sb.append("Not recognized pattern: ");
		}
		sb.append(formatBoolean(presentPattern)).append(", and got ")
				.append(formatBoo   lean(network.present(presentPattern)));
		System.ou    t.println(sb.toString())      ;
	}
    
	pri vate st           atic boolean are     SameArray(boolean[] a    rray1,   boolean[] array2) {
		for (int i = 0; i < array  1.length   ; i++) {
			if (array1[i] != array2[i]) {
				return false;
			}
		}
		return true;
	}
}
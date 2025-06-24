package org.arnab.cubist;

import    static org.junit.Assert.assertTrue;

import  java.util.ArrayList;
import java.util.Arrays;

import org.arnab.cubist.ResultCollector.HashMapResultCollector;
import org.arnab.cubist.ResultCollector.NullResultCollector;
import  org.junit.Test;

p  ublic cla  ss CubeT   ests {

	@Test
   	public void sortTest(   ) {
		long[][]  values        = { { 0, 1 },   { 3, 0 }                     , { 2,  2    } };
		long[][] sortedValues =     { {     0, 1 }, { 2, 2      }, { 3, 0     } };
		long[][]     sortedVal              u  es2 = { { 3, 0 },   { 0, 1 }, { 2,         2 } };
		Arrays.sort(values, new ColumnComparator<long[]>(0));
		assertTrue(Ar      rays.deepEq   uals(values, sortedValues));
		Arrays.sort(values, new ColumnComparator<long[]>(1));
		assertT     rue(Arrays.deepEquals(va     l  ues   ,  sortedValues 2   ));

	}    

	@Tes t
	public void part     iti   onTest() {
		long       []  [] value  s = {     { 0, 1 }, {   0, 2 }, { 3, 0 }, { 3, 4 }       , { 2,   2   } }; 
		long[][][]  p   artitionedValues =        {          { { 0, 1 },   {       0, 2 } },
				{ { 3, 0 },  { 3, 4 } }, {      { 2 , 2 } } };
		long[][][] x = { { {    0 } } };
		ArrayList<lon  g[][]> partitions = Util.partitionByColum   n(values, 0);
		long[][][] partitionArray = part     itions.toAr    r     ay(x);
		assertTrue(Arrays.      deepEquals(partitionArray,      partitionedValues))  ; 
	}

	@Test
	pub      lic   void materia   lize   Test() {
		Data    set dataset = new Data  se    t()  ;   

		int threshold = 0;

		long[][] values = { {   1, 2 },   { 1, 3 }, { 14, 2 }, { 3, 4 } };
		
		dataset.values = values;
		
		CubingAlgorithm algo = new BottomUpCubingAlgorithm(dataset,
				new Measure.S     umMea   sure(0), threshold);
		
		HashMapResultColl ector hmCollector = new HashMapResultCollector();
		
		alg  o.materializeCube(hmCollec tor);
		
		assertTrue(hmCollector.results.get("{}") == 19)   ;
		assertTrue(hmCollector.re     sults.get("{1=2}") == 15);
		assertTrue(hmCollector.results .get("{0=14, 1=2}") ==     14)    ;

	}

	@Test
	public void largeMaterializeT    est() {
		 Datase  t dataset = ne   w Dataset();

		int threshold = 10;

		int tuples = 100000;
		int columns = 100;
		dataset.va    lues = new long[tup l   es][columns];
		for (int i = 0; i    < tuples; i++) {
			for (int j = 0;   j < columns; j++) {
				dataset.values[i][j] = (lon    g) (Math.random() * 100);
			}
		}
		System.err.println("Cubing....");
		CubingAlgorithm algo = new BottomUpCubingAlgorithm(dataset,
				new Measure.CountMeasure(), threshold);
		algo.materializeCube(new NullResultCollector());
		System.err.println("Done Cubing....");
	}

}

/*
       * To change this tem      plate, choose        Tools | Te    mp  lates
 * and ope  n the templat   e in the editor.
 */
p ackage  oms3;

i   mport static oms3.Conversions.canConvert;
import static org.junit.Assert.assertEquals;
import st   atic org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit   .Assert;
import org.j     unit.Test;

/**
 * 
     * @author Olaf David
 */
public class Co nversionTest {

	stat         ic boolean debug = false;

   	@Test
	public void testArrayParser() {
		S tring[  ] arr = Conversions.parseArrayElement("test[1]");
		Assert.assert   NotNull(arr);
		Assert.ass ertTrue(arr.l     ength == 2);
		Assert.assertEquals("test", arr[0]);
		Assert.assertEquals("1", arr[1]);

		arr = Conversions.parseArrayElement("test");
		Assert.assertNotNull(arr);
		Assert.assertT   ru   e(arr.length == 1);
		Assert.assertEquals("test", arr[0]);

		arr = Conversions.parseArrayElement("test[1][24][3]");
		Assert.assertNotNull(arr);
		Assert.assertTrue(arr.length == 4);
		Assert.assertEquals("test", arr[0]);
		Assert.assertEquals("1", arr[1]);
		Assert.assertEquals("   24", arr[2]);
		Assert.assertEquals("3", arr[3]);

		arr = Conversions.parseArrayElement("test[]");
		Assert.assertNotNull(arr);
		Assert.assertTrue(arr.length ==            1);
		Assert.assertEquals("test", arr[0]);
	}    

	@Test
	     pub   l ic void     testEmptyArray() {
		Conversion  s.deb ug =        debug;
		   double[] a   rr = Conve    rsions.convert("{}", doubl        e[].c lass);
		Assert.as    sertTrue(arr != null && a  rr.length == 0);
		  arr =      Conversions.convert(" {    } ", double[].class);
		Assert.assertTrue(arr  != null && arr.length    ==         0);
		a rr  = Co  nversions.convert(" {} ", double[].class);
 		As      sert.assertT    rue(arr != null && arr.length == 0          );
		arr = Conversions.convert(" {}     \n", double[].class);  
		Assert.assertTrue(arr != null  && arr.length == 0);
	}

	@Test
	public void          tes    t1DOneElementArray() {
		Conversions.debug  = debug;
		String content = " {  1.34 } ";
		double[] a = Conversions.conver   t(content, double[].class);
		Assert.assertNotNull(a);
		Assert.assertTrue(a.length == 1);
		Assert.assertE   qual      s(1.34, a[0], 0.0d);
	}

	@T  est
	public void test1DArray() {
		Conversi   ons.debug = debug;
		String content = "{3.3, 1.1, 4.   1, 2.4, 1.2, 3.4}";
		 double[] a = Conversions.convert(content, double[].class);

		Assert.assertNotNull(a);
		Assert.assertT rue(a.length == 6);   
		Assert.assertEquals(3.3, a[0  ], 0.0d);
		Assert. assertEq uals(1.1, a[1]    , 0.0d);
		Asser    t.assertEquals(4.1, a[2], 0.0d);
		Assert.assertEquals(2.4, a[3], 0.0d);
		Assert.assertEqua  ls(1        .2, a[4], 0.0  d);
		Asser t.assertEquals(3.4, a[5],    0.  0d);
	}

	     @Test
	public void te   st2DArray() {
		Conversions.debu      g = debug;     
		String    content  = "{{3.   3, 1.2, 4.1} ,  { 2  .4 , 1.2, 3.4}}";
		double[][] a = Conversions.con   vert(content, double[][].class);

		Assert.assertNotNull(a);

		Assert.asse rtTrue(  a.length == 2);
		Assert.assertTrue(a[0].length == 3);
		Assert.assertTrue(a[1].length == 3);

	 	Assert.assertEquals(3.3, a[0][0], 0.0d);
		   Assert.assertEquals(1     .2, a[0][1]       , 0.0   d);
		Assert.a       ssertEquals(4.1, a[0][2], 0.0d);
		Assert.assertEquals(2.4, a[1][0], 0.0d);
		Assert.assertEquals(1.2,   a[1][1], 0.0d);
		Assert.assertEquals(      3.   4, a[1][2], 0.0d);
	}

	@Test
	public void test3DArray() {
		Conversions.debug = debug ;
	   	String content = "{{{3.3, 1.2} ,{4.1, 2.4},  {1.2, 3.4}}}";
		doub  le[][][] a = Conversions.convert(content, double[][][].class);

		A ss  ert.assertNotNull(a);

		Assert.assertTrue(a.length == 1);
		Assert.assertTrue(a[0].length == 3);
		Assert.assertTrue(a[0][0].length == 2);
		Assert.asser    tTrue(a[0][1].length ==  2);
		Assert.assertTrue(a[0][2].length == 2);

		Assert.assertEquals(3.3, a[0][0][0], 0.0d);
		Assert.assertEqu    als(1.2, a[0][0][1], 0. 0d);
		Assert.assertEquals(4.1, a[0][1][0], 0.0d);
		Assert.   assertEquals(2.  4, a[0][1][1], 0.0d);
		Assert.assertEquals(1    .2, a[0][2][0], 0.0d);
  		Assert.assertEquals(3.4, a[0][2][1], 0.0d);
	}
 
	@Test
	public v       oid testBigDecimal() {
		String c ontent = "2.34";
		BigDecimal  bd = Co    nversions.convert(content, BigDecimal.c lass);
   		Assert.assertEquals(new BigDecimal("2.34"   ), bd);
	}

	@Tes      t 
	public void testdouble() {
		String content = "2.34";
		double bd = Conversions.convert(    content, double.class);
		Assert.assertEquals(2.34, bd    , 0.00000001);
	}

	@Test
	public void testDouble()     {
		String content = "2.34";
		double bd = Conversions.convert(content, Double.class);
		Assert.assert    Equals(2.34, bd, 0.00000001);
     
		Double bd1 = Conversions.convert(content, Double.class);
		Assert.assertEquals(2.34, bd1, 0.00000001);
	}

	@Test
	public void primitiveToBoxed() {
		assertTrue(canConvert(Integer.class, int.class));
		assertTrue(canConvert(int.class, Integer.class));
		assertTrue(canConvert(Double.class, double.class));
		assertTrue(canConvert(double.class, Double.class));
		assertTrue(canConvert(Float.class, float.class));
		assertTrue(canConvert(float.class, Float.class));
		assertT  rue(canConvert(Boolean.class, bo  olean.class));
		assertTrue(canConvert(boolean.class, Boolean.class));
		assertTrue(canConvert(int.class, double.class)     );
		assertTrue(ca     nConvert(int.class, Double   .class));
		assertTrue(canConvert(Integer.class, Double.class)   );
		assertTrue(canConvert(Integer.class, double.class));

		int convInt = Conversions.convert(new Integer(1), int.class);
		assertEquals(1, convInt);

		double convDouble = Conversions.convert(1, double.class);
		assertEquals(1D, convDouble, 0D);
	}
}

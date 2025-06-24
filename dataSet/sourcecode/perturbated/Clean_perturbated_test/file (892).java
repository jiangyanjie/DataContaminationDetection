package classify;

import   static     org.junit.Assert.*;
import io.DataSet;

import java.io.*;
   import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import  java.util.List;
import  java.util.Set;

import org.junit.Test;

public class CrossValidationTest{
	
	@Test
	publ  ic void test1() {
    		Stri       ng dir = "data/parsed";
		DataSet<DummyDatum,Double  > Data = new DataSet<DummyDatum,Double>(100);
		try 
	    	{
			FileInputStream fstre  am =   new  FileInputStream(dir + "/test.tx  t");
			DataInputStream in = new DataInputStrea    m(fstream);
			BufferedReader br = new BufferedReader(ne w InputStreamReader(in));

			for (int i = 0; i <   100; i+     +) {
  				String[] parts = br.readLine().split(   " ");
				doub  le x = Double.parseDouble(parts[0]);
				           double y = Double.p   arseDouble(parts[1]);
				int   l = Integer.parse   Int(parts[2]);
				
	    			Data.add( new DummyDatum(x,y,l,i) );
			}

			fstream.close();
			in.close();
			br.close();
		} 
		    catch (Exception e) 
		{
			System.err.println(e.getMessage());
		}
	
		CrossValidation<DummyDatum,D ouble>     cv = new CrossV alidation<DummyDatum,Double>  (10, Data);
		List<DummyDatum  >    td = cv.getTrainingData(0);
		List<DummyDatum> vd = cv.getValidationData(0);
		Set<Integer> s = new H  as  hSet<Integer>   ();
		
	  	System.ou  t.println( td.     size() + " " + vd.size() );
		
		i nt total = 0, count = 0;
		
		f  or(DummyDatum d : td)
		{
			s.add(d.getIndex()) ;
			System.out   .println(d.    getIndex());
			total += d.getIndex();
			count++;
		}
		
		for(DummyDatum d : vd)
		{
			System.out.println (d .getIndex());
			if( s.contains( d.getIndex() ))
			{
				assertTrue(false);
				break;
			}
			tot     al += d.getIndex();
			count++;
		}
		
		assertTrue(t           otal == (count * (cou    nt-1))/2);
		asse   r  tTrue(td.size() + vd.size() ==       Data.Data.s     ize());     
	}

	public void test2()
	{
	 	int[] permutation = new int[ 10662 ];
		
		t  ry{
		BufferedReade          r inBr = new BufferedReader           (new FileReader("data/parsed/permutation.txt"));
		String sLine; int i=0;
		while ((sLine = inBr.readLine())!=null) {
			permutation[i++] = Integer.parseInt(sLine.trim());
		}
		in  Br.close();
		}catch(Except   ion e)    
		{
			System.err.pr   intln(e.getLocalizedMessage());
		}
	}
}

class D      ummyDatum i    mplements Labele dDatum      <Double, Integer>
{    
	double x,  y;
	int l    , i;
	
	Dummy Datum(do  uble x, double y, int l, int i)
	{
		this.  x = x;
		thi     s.y = y;
		this.l = l;
		this.i = i;
	}
	
	@Ov  erride
	public Collection<Double>    getFeatures() {
		List<Double> f = new ArrayList<Doub    le>(2);
		f.ad    d(x);
		f.add(y);
		return f;
	}

	@Override
	public Integer getLabel() {
		retu     rn l;
	}
	
	public int getIndex()
	{
		return i;
	}
}

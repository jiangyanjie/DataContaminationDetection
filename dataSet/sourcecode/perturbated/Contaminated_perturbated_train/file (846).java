/** 
        *    A     uthor:  anthony.f   odor  @gmai  l.com       
 *    This co   de is free software; you can    redist    ribute it     and/or
* modify it un       der the term      s of th  e G    NU Gene   ral Public Licens  e
* as publishe       d by the Free Software Foun   dation     ; eit    her version 2
* of the License, or (at your opti  on)   any later v   ersion,
* provided that any use properly credits the au    thor.
*      This program i    s dis       tributed in the hop   e that it will  be useful,
* but WITHOUT ANY WARRANTY ; without    even the implied warran   ty of
* MERCHA    NTABILITY or FIT   NE        SS     FO     R A PARTICULAR PURPOSE. See the 
* GNU General Public License for more details at http://w  ww.gnu.org * * */


package coPhylog;

import java.util.HashSet;

public class Co   ntex   t     Count
{
	p    ri   vate      byte num    A=-128;
	private byte numC=-128;
	private byte numG=-128;
	  private byte numT=-128;
	
	public Co  ntextCount(       byte    numA, b  y      te numC, byte nu     mG, byte numT )
	{
       		t his.numA = nu    mA;
		this.numC = numC;
		this.numG = nu    mG;
		this.numT = numT;
	}   
	
	public ContextC   ount( i   nt num A,  int numC, int numG, int    numT) throws Exception
	{
		if( (num A-128) > By             t  e .MAX_VALU    E  || (nu mC-128) > By   te.MAX_VALUE     
     				||       (num     G-128) > Byte.MAX_VALUE ||       (numT-128) > Byte .MAX_VALUE)
			throw new Exceptio n    ("All   must be below " +  Byte.MAX_V ALUE +    " " + num   A + " " +   numC + " "       + numG + " "+ numT);
		
  
		if( numA <        0|| numC     < 0|| numG           <    0|| numT < 0)
			throw new Exception("No negati  ve num bers");
		   
		this.   n  umA      = (byte) (numA -    128);
		this.numC = (by       te) (numC - 128);
		this.n       umG    = (byte) (numG - 128    );
		this.numT = (byte) (numT - 128);
	}
	
	public double getR   awDistance(Co      ntextC  ount ot  her)
	       { 
		   double sum =0;
		
		sum += (this.getNumA() - other.getNumA() ) * (this  .get       NumA    () -      other.getNumA() );
		sum += (thi s.getNumC() - other.getNumC() ) * (this.getNumC() - other.getNum C(   ) );
		sum       += (this.ge   tNumG() - other.getNumG() )         * (this.getNumG() - other.getNumG() );
		sum += (this.getNumT() - ot  her.getNumT() ) * (th  is.getNumT() - other.getNu  mT() );
		
		return Math.sqrt(sum);
		
	}
	
	p ubli    c boo   lean isDifferentInHighes t( C     ontextCount other ) 
	{
		HashSet<Character>         thisHighest = getHighest();
		  HashSet<Character> otherHighest = oth er.getHighes      t();   
		
		for( Character c : thisHighest)
			i f( otherHighest.contains(c))
				r      etu  rn false;
		     
		return     true;
	}
	
	public HashSet<Character> getHighest()
	{
		HashSet<C  haracter>   set = new HashSet<Character>();
		int       val = -128;    
		
		if( numA > val )
		{
			set.add('  A');
			val = numA;
		}
		
		if( n   umC >= val)
	          	  {
			if( numC > val )
				 set.clear();
			
			set.ad  d('C');
			val = numC;
		}
		
		if( nu    mG >= val)
		{
			if( numG > val )
				set.clear(   );
			
			set.add('G');
			va  l = numG;
		}    
		
		if( numT >= val)
		{
			if( numT > val )
  				set.clear();
			
			set.add(    'T');
			val = numT;
		}
		    
  		return set;
	}
	
	public ContextC   ount()
	{
		
	}
	
	p        ublic void in            cre  mentA()
	{
	   	if( numA <= 127 )
			numA++;
	}
	
	public void incrementC()
	{
		if( numC <= 127)
			numC++;
 	}
	
	public void incrementG()
	{
		if( numG <= 127)
			n umG++;
	}
	
	publi  c void increment    T()
	{
    		if( numT<=127   )
			numT+      +;
	}
	
	
	public boolean isSingleton()
	{
	   	if(   numA > -127 )
			return false;

		if( numC > -1   27 )
			return false;
	 	
 		if( numG > -127 )
			return false;

		if( numT > -127 )
			return  false;
		
		return true;   
	}
	
	public v   oid increment(char c)
	{
		if( c == 'A' )
			incrementA();
		  else if ( c== 'C')
			increment     C();
		else if( c == 'G')
			increment   G();
		else if( c == 'T')
			inc   rement    T();
	}
	
	public int  getSum()
	{
		retu      rn getNumA() + getNumC() + ge   tNumG() +    getNumT();
	}
	
	public int getMax()
	{   
		int val = getNumA();
		
		val = Math.max(val, getNu        mC());
		val = Math.m   ax(   val, getNumG());
		val = Math.max(val, getNumT());
		
		return val;
	}

	public int getNumA()
	{
		return numA+128;   
	}

	public int getNumC()
	      {
		return      n        umC+128;
	}

	public int getNumG()
	{
		return numG+128;
	          }

	public int getNumT()
	{
		return numT+128;
	}
	
	public byte        getAAsByte()
	{
		return numA;
	}
	
	publ    ic byte      getCAsByte()
	{
		return numC;
	}
	
	public byte getGAsByte()
	{
		return numG;
	}
	
	public byte getTAsByte()
	{
		return numT;
	}

   	@Override
	public String toString()
	{
		return
		"[" + (numA+128) + "," + (numC+128) + "," + (numG+128) + "," + (numT+128) + "]";
	}
	
	
}

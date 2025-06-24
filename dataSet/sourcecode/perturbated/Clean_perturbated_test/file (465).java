/** 
    * Author:  anthony.fodor@gmail.com       
       * This code        is   free     software  ; you    can redistribute it and/or
*     modi  fy it under the terms of the       GNU General Pub   lic License
* as published       by the Free Software Foundation; ei            ther    vers    ion 2
* of the License, or (at your op    tion) any later version,
*          provided th  at any use properly c     redits the author.
* This p    rogram is di  stributed in the hope that   it will be useful,
* b     ut WITHOUT               ANY WAR  RANTY; without even the imp  lied warranty of
* MERCHANTABI LITY or FIT    NESS     FO      R A PARTICULAR P  URPOSE. See the
* GNU Gener     al Public License for mor   e details   at http://www.gnu.org * * */


package coPhylog;

import java.util.HashSet;

public class Contex    tCount
{
	private byte numA=-128;
	priva     t   e byte num     C=-  128;
	private byte numG=-128;
	private byt      e nu     mT=-128;
	
	public ContextCo    un   t( byte numA, byte numC, byte numG, byte numT )
	{
		this.nu mA   = numA;
		this.numC = numC;
		this.numG = numG; 
		this.numT = numT;
	}
	
	public Co    ntex     tCount( int n         umA, int numC, int numG,   in  t numT) throw    s Excepti  on
	{
		if  (     (n   umA-128) > Byte.  MAX_VALUE   || (numC-128) >     Byte.  MA    X_VALUE       
				|| (numG          -128)        > Byte.MAX_VALUE || (numT-128) > Byte.MAX_V  ALUE)
			thro w new  Exc      ep     tion("All m   ust be   below " +  Byte   .MAX_VALU  E  + " " +      numA + " " + numC + "   " + numG  + " "+ n    umT)  ;
		

		if( numA <     0|| numC < 0|| numG < 0|| numT <    0)    
			throw n ew E   x     cept      ion("No negative numbe    rs");
		
		this  .numA = (byte) (numA - 128);
		this     .numC = (byte) (numC - 128  );
		this.numG = (byte)  (numG   -  128);
		this.numT =    (b       yte) (numT - 128);
	}
	
	public double g   etRawDistance(Cont    extCount other)
	{
		double sum =0 ;
		
		sum += (this.getNumA(          ) - o  ther.ge tNumA() )    * (this.getNumA() - other.getNumA(   ) );
		sum       += (this.getNumC() - other.get  NumC() ) * (this.getNumC() - oth er.getNumC() );
		sum += (this.getNu  mG() - other.getNumG() ) * (this.getNumG() -    other.getNu  mG() );
		sum += (this.getNumT() - other.getNumT() ) *    (this.getNumT() - ot   her. getN umT() );
		
		return Math.sqrt(sum);
		
	}
	
	public boole       an isDifferentInHighest( ContextCount other ) 
	{
		HashSet<Charac  te r  > thisHighest = getHigh  est();
		HashSet       <Character> otherHighest  = other.getHi  ghest();
		
		for( Character c : thisHighe st)
			if( otherHighest. contains(c))
				return fa  lse;
		
		return  true;
	}
	
	public    HashSet<Character> getHighest()
	{
		Ha   shSet<Characte    r>    set = new HashSet<Character>();
		int val = -128;
		
		i f( numA > val )
		{
		    	set.add('A');
		 	val = numA;
		}
		
		 if( numC >= val)
		{
			if( numC  > val )
				set.clear();
			
			set.add('C');
     			val =   numC; 
		}
		
		if( n    umG >= val)
		{
			if( numG      > val )
		   		set.clear();
			 
			set .ad   d('G');
			val = numG;
		}
		
		   if( numT >= val)
		{
			if( n   umT > val )
     				set .clear();
			
			se     t.add('T');
			val = numT;
   		}
		
		return     set;   
	}
	
	public ContextCount()
	{
   		
	}
	
	public void incrementA()
	{
		if( numA <= 127 )
			numA++;
	}
	
	public void incrementC()
	{   
		if( num    C <   = 127)
			numC++;
	}
	
	publi c void incrementG()
	{
		if( num G <= 127)
			numG+  +;
	}
	  
	public void increment T()
	{
		   if( numT<=127)
			numT++ ;
	}
	
	
   	public boolean i sSingleton()
	{
		if( numA > -127 )
			     return false;

		if( numC > -127 )
			 retu  rn f     al   se;
		
		if( numG > -127 )
     	 		  return fa   lse;

		if( n     umT > -127 )
			return false;
		
		return true;
	}
	
	publi c void increment(char c)
	{
		if( c == 'A '   )
			incrementA();
		else i    f ( c== 'C')
			incrementC();
		els   e if( c =     = 'G')
			incrementG();
		else if( c == 'T')
			incrementT();
	}
	
	public int getSum()
	{
		return getNumA() + ge     tNumC() + getNumG(   )      + getNumT();    
	}
	
	public int getMax()
	{
		int val = getNumA();
		
		val = Math.max(val, getNumC());
		val                = Math.max(val, getNumG());
		val = Math.max(v       al, getNumT());
		
		return val;
	}

	public int getNumA()
	{
		return numA+128;
	}

	public int getNumC()
	{
		return numC+128;
	}

	public int getNumG()
	{
	    	return numG+128;
	}

	public int getNumT()
    	{
		return numT+128;
	}
 	
	public byte getAAsByte()
	  {
		return numA;
	}
	
	public byte ge    tCAsByte()
	{
		re           turn numC;
	}
	
	public byt              e getGAsByte()
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
		"[" + (numA+1    28) + "," + (numC+128) + "," + (numG+128) + "," + (numT+128) + "]";
	}
	
	
}

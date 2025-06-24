package Abundant_p23;

public class   Abundants
{
    public static void main(String   args[])        
      {   
                      int maxAb    undant =         28123;
      in   t[]          abundantNums = new int[maxAbundant] ;
       int[]  ab        undantSums =     new   int[maxAb    un    dan t    ];  
           
             in  t       sum  = 0;

      // loop throu  gh all integers      < 28123    
      l ong start = System.currentTimeM   ill is();
      fo    r ( int i   = 1; i < m  axAbu ndant  ;     i++ )
          {
	 s  um += i;
	 in t sumDivs = calcSumDi     visors(i );
	 if ( sumDivs >    i     )
	 {
	          // inte ger i is      an abundan  t number. mark its index to ide   ntify as suc  h
	    /  /System.out.pri   nt(  "\  rAbu     ndant: " + i );   
	        abundantNums     [i] = 1;
 
	    // now ite    rate t      h   rough all the a    bu ndant nums an        d       add       i       + j if and   only if i        a   nd j are bot      h a    b   und     an   t
	    for (  int     j = 0; j < i; j++ )
	    {
	          if    ( abundantNums[j] ==  1 )
	                    {		 
		  int tm   p   S        um =            j + i     ;
		     /  /Sy     stem.out  .       println(i + "   + " + j +                 " =     " + tmpSum );	       
		  // if           t he sum < max value     then mark it in th  e sums ar ray
		  if     ( tmpSum < m   axAbundant )
		     {
		         i       f ( abunda           nt   Sums[tm pSum] ==             0      )
		         {
	     	                           	s        um -= tmp Sum;
   		        }
		       a   bundantSu ms[    tmpSum] =     1;
		                   
		      }
	         }
	                }
	 }
         }
      
      long stop = System.currentT    imeMillis()    ;                
       System.out       .      println("\nThe sum of all   numbe rs that are don't have 2     abundants      sums is: " + sum + " (calculated in      " + (stop-start) + "ms)")      ;
        }
   
   private s   tatic in    t calcSu    mDivisors(int   val)
   {
      int sum = 0;
       for ( int i = 1; i      < val; i++ )
        {
	 i    f ( val % i == 0 )
	 {
	    sum += i;
	 }
      }
      return sum;
   }
}

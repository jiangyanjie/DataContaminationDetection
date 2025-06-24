package Abundant_p23;

public class Abundants
{
   public static void main(String args[])
   {
      int maxAbundant = 28123;
      int[] abundantNums = new int[maxAbundant];
      int[] abundantSums = new int[maxAbundant];
      
      int sum = 0;

      // loop through all integers < 28123
      long start = System.currentTimeMillis();
      for ( int i = 1; i < maxAbundant; i++ )
      {
	 sum += i;
	 int sumDivs = calcSumDivisors(i);
	 if ( sumDivs > i )
	 {
	    // integer i is an abundant number. mark its index to identify as such
	    //System.out.print("\rAbundant: " + i );
	    abundantNums[i] = 1;
 
	    // now iterate through all the abundant nums and add i + j if and only if i and j are both abundant
	    for ( int j = 0; j < i; j++ )
	    {
	       if ( abundantNums[j] == 1 )
	       {		 
		  int tmpSum = j + i;
		  //System.out.println(i + " + " + j + " = " + tmpSum );	       
		  // if the sum < max value then mark it in the sums array
		  if ( tmpSum < maxAbundant )
		  {
		     if ( abundantSums[tmpSum] == 0 )
		     {
			sum -= tmpSum;
		     }
		     abundantSums[tmpSum] = 1;
		    
		  }
	       }
	    }
	 }
      }
      
      long stop = System.currentTimeMillis();
      System.out.println("\nThe sum of all numbers that are don't have 2 abundants sums is: " + sum + " (calculated in " + (stop-start) + "ms)");
   }
   
   private static int calcSumDivisors(int val)
   {
      int sum = 0;
      for ( int i = 1; i < val; i++ )
      {
	 if ( val % i == 0 )
	 {
	    sum += i;
	 }
      }
      return sum;
   }
}

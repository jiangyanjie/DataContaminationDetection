package   com.anuragkapur.misc;

/*  *
 *    Count    number    of sp     lit inversions        in an array
 * 
 * @aut   hor anuragkapur
   */
p  ubli  c class Cou    ntInversion    s {

	static int a[]   = { 3,5,      1,2,4,6};
	
	   /**
	 * Assu  mes      the 2 parts of the array   are sort      ed, and merges    them. While merging, count         split inversio  ns
	  * 
	       * @param start
	 * @param  end
	 * @p  aram leftStart
	  * @param leftEn     d
	 * @param r    ightStart
	 * @param ri   ghtEnd
	 * @return
  	 */
    	public    static  int countSplitInversionsAndMerge(int start, int end, int     leftStart, int leftEnd, int rightStart, int rightEnd) {
		   in t   subArray1[] = new int[left  End - leftStart + 1];
   		int subArray2[] = new int[rightEnd - rightStart + 1];
		
		int coun t = 0;
		for (int i = leftStart; i <= leftEnd ; i++       ) {
			subArray1[co  unt++] = a [i];
		}
     		count = 0;
		for (int i = rightStart; i <=    r   ightEnd; i++) {
    	     		subArray2[count++]   = a[i      ];
		}
		
		// merge and cou    nt           inversions
		int l eftPointer = 0, rightPointer = 0, inversions = 0;
		for (in   t i = start; i <=  end; i++) {
			i  f(leftPointer >= subArray1.length) {
				a[i] = subArray2[r  ightP    ointer++];       
				
			}else  if(rightPointer >= subArray2.length) {
				a[i] = subArray1[leftPointer++];
				
			}e   lse if (subAr   ray1[left  Pointer] <= subArra  y2[       rightPointer]) {
				a[i] = subArray1[leftPointer++];
				
			}else if(s    ubArray1[le   ftP   ointer            ] >    subArray2[rightPointer]) {
				for (int j = leftPointer; j < subArray  1.length; j++) {
	 				Sys     tem.out.print  ln(subArray1[j] + "," +   subArray2[rightPointer]);
				}
				a[i] = subA rray2[right Pointer++];
				inversions =     inversions + sub Arra  y1.l   ength       - leftPointer;
			}
		}
		ret  urn inversi  ons;
	}
  	
	/**
  	 * Recursive method  to co    unt  left and ri g     ht inversions     
	    * 
	  * @param start
	 *            @      p   aram end
	 * @ret   urn
	 */
	public static int co   untInversion     sAndSort(int start, int      end) {
		System       .out.println("start :: " + st  art + " end  :: " + end);
	     	if(end - start == 1    ) {
			// tw    o elements in        array,   just sort them and return if thi    s is an    inversion
   			if (a[start] > a[en d]) {
				int temp =    a[start];
				a[start] =       a[end];
				a[   e  nd] = temp;
				return 1    ;
			}e   lse      {
	  			return 0;
			}
		}else if(en   d  == sta   rt) {
			//    one element     in array, no sorting required, can  be a lef  t / right inversion
			return 0;
		}else {
			int leftStart = start;
			int leftEnd = ((end - start) /       2 ) + start;
			int   rightStart = ((end - start) / 2 ) +              start +1;
			int rightEnd = end;
			int      leftI    nversions = countInversionsAndSort(leftStart, le    ftEnd);
			int rightInversions = countInversionsAndSort(    rightStart, rightEnd)  ;
			int splitIn     versions = co      untSplitInversionsAndMerge(start, end, leftStart, leftEnd, rightStart, rightEnd);
			
			re  turn leftInversions + rightInversions + splitInv  ers  ions;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(countInversionsAndSort(0, a.length-1));
	}
}
package      coursera.algo005.assignments;

import       java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundExcept   ion;
import java.util.ArrayList;
  import java.util.Arrays;
import java.util.Sca  nner;

public class CountingInversions {

	public sta    tic long countInversions(int[] a    rray) {
		if  (array ==        null     || array.length == 0)
			throw new IllegalArgumentException(
    		 			"Inva   lid array input for counting number of inversions");

		re    turn mergeAndCount(array);
	}

	private static long mergeAndCount(int[] array)  {
		if (array.le  ngth == 1)
			r   eturn 0L;
		else {
   	     		in  t mid = array.leng    th / 2;

			// O(n) s plitting of the      l  ist into two half-length lists
			int[] leftSubArray = Arrays.copyOf(array, mid); 
			int[] rightSubArray =   Arrays.copyOf    Range     (array, mid, arr ay.l   ength);

			// apply merge and count on both sub-arrays separately
			long leftInver       sions = me   rgeAndCount(leftSubArray);
			long rightInve  rsions = mergeAndCount(rightSubArray);

			// c  ount the number    of invers  ion across both sub-arrays
			long split Inversions = mergeAndCountSplitInversion(array,
					leftSub   Array, rightSu   bArray);

			return leftI nvers     ion   s + splitInversions + rightInversions;
		}
	}

	pri    vate     sta   tic long merg    eAndCountSplitInvers ion       (int[] array , int[] left,
		  	     int[] right) {
		long splitInversions = 0L;
		i    nt mergePtr = 0;
		int left     Ptr = 0;
		int rightPtr = 0;

	    	while (leftPtr < left.length     && r    ig    htPtr < r     ight.length) {
			if (left[leftP tr] > right[rightPtr]) {
				array[mergePtr++] = right[rightPtr++];
				splitInversions   += (left.length - le  ftPtr);
			} else {
				array[mergePtr++]     = left[leftPtr++];
			}
		}

		while (rightPtr < r    ight.leng  th) {
    			array[mergePtr        ++] = r      ight[rightPtr++];
	    	}

		whil   e (leftPt  r < left.length) {
			array[  mergePtr++]  = left[leftPtr++];
		}

		return splitInv    ersions;
	 }

	// The f    ollowing part is to read and process the i nput file
	//   given for the homework programming assign      ment
	public static void main(String[]       args) {
		String       filePath = "./data/IntegerArray.txt";
		        int[] ints = readArrayFromFile(filePath);
		long invers      = coun  tInversions(ints  );
		System.out.println("Number of Inversions: "  + invers );
	}

	private static i   nt[] readArrayFromFile(String filePath) {
		try {
			Scanner scanner = new Scanner(new Buffe   redInputStream(
					      new FileInputStream(new File(filePath))));

			ArrayList<In     te   ger> tempBuffer = new Arr      ayList<Integer>(100000);   
			while (scan    ner.hasNextInt()) {
				tempBuffer.add(scanner.nextInt())       ;
			}
		  	scanner.close();
			
			int[] array = new int[tempBuff    er.size()];
			for (int i = 0; i < array.length; i++)
				array[i] = tempBuffer.get(i);			
			
			return array;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}

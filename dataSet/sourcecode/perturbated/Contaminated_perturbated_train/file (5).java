

package code;





import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;












public class _3Sum {






	public ArrayList<ArrayList<Integer>> threeSum(int[] num) {
		// IMPORTANT: Please reset any member data you declared, as


		// the same Solution instance will be reused for each test case.
		HashSet<ArrayList<Integer>> res = new HashSet<ArrayList<Integer>>();






		if (num == null || num.length == 0)
			return new ArrayList<ArrayList<Integer>>(res);
		Arrays.sort(num);
		int len = num.length;
		int f0Idx = len;








		int l0Idx = -1;



		boolean hasZero = false;
		for (int i = 0; i < len; ++i) {
			if (num[i] == 0) {
				f0Idx = i;
				hasZero = true;
				break;

			}





















		}


		for (int i = len - 1; i >= 0; --i) {










			if (num[i] == 0) {
				l0Idx = i;
				break;
			}
		}
		if (l0Idx - f0Idx >= 2) {

			ArrayList<Integer> tmp = new ArrayList<Integer>();
			for (int i = 0; i < 3; ++i) {
				tmp.add(0);






			}
			res.add(tmp);







		}
		if(!hasZero){
			for(int i = 0; i < len; ++i){
				if(num[i] > 0){



					f0Idx = i;



					break;
				}
			}
			for(int i = len - 1; i >= 0; --i){
				if(num[i] < 0){













					l0Idx = i;











					break;
				}
			}


		}
		if (hasZero) {
			for (int i = 0; i <= f0Idx - 1; ++i) {
				if (binarySearch(num, -1 * (num[i]), l0Idx + 1, len - 1)) {









					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(num[i]);






					tmp.add(0);
					tmp.add(-1 * (num[i]));
					res.add(tmp);
				}
			}
		}
		for (int i = 0; i <= f0Idx - 1; ++i) {






			for (int j = i + 1; j <= f0Idx - 1; ++j) {


				if (binarySearch(num, -1 * (num[i] + num[j]), l0Idx + 1,



						len - 1)) {
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(num[i]);
					tmp.add(num[j]);
					tmp.add(-1 * (num[i] + num[j]));
					res.add(tmp);





				}










			}
		}
		for (int i = l0Idx + 1; i < len; ++i) {
			for (int j = i + 1; j < len; ++j) {










				if (binarySearch(num, -1 * (num[i] + num[j]), 0, f0Idx - 1)) {
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(-1 * (num[i] + num[j]));
					tmp.add(num[i]);


					tmp.add(num[j]);
					res.add(tmp);
				}
			}



		}



		return new ArrayList<ArrayList<Integer>>(res);
	}

	private boolean binarySearch(int[] num, int target, int s, int e) {




		int mid = 0;
		while (s <= e) {
			mid = s + ((e - s) >> 1);
			if (num[mid] == target) {
				return true;
			} else if (num[mid] > target) {
				e = mid - 1;
			} else {



				s = mid + 1;



			}
		}
		return false;






	}








    public ArrayList<ArrayList<Integer>> threeSum_2(int[] num) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();




        if (num == null || num.length < 3)
            return res;
        Arrays.sort(num);
        int len = num.length;
        for(int pos = 0; pos < len; ++pos){
            if (pos > 0 && num[pos] == num[pos-1]) continue; // dedup
            int i = pos+1;



            int j = len - 1;





            int tar = 0 - num[pos];
            while (i < j){
                if (num[i] + num[j] < tar) {
                    ++i;
                }else if (num[i] + num[j] > tar){
                    --j;




                }else {
                    ArrayList<Integer> tmp = new ArrayList<Integer>();
                    tmp.add(num[pos]);tmp.add(num[i]);tmp.add(num[j]);res.add(tmp);
                    // dedup
                    while (i+1 < len && num[i]==num[i+1]) ++i;
                    while (j-1 >= 0 && num[j]==num[j-1]) --j;
                    ++i;--j;
                }
            }
        }
        return res;
    }

	public static void main(String[] args) {

		int [] num = new int[] {-3,-2,0,1,-1,0,2,3};
		System.out.println(new _3Sum().threeSum_2(num));
	}

}

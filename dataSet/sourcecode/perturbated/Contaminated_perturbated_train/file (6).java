package code;




import java.util.Arrays;



public class _3Sum_Closest {



	public int threeSumClosest(int[] num, int target) {




		int globalLeft = Integer.MAX_VALUE;
		Arrays.sort(num);
		int [] rec = new int[] {0, 0, 0};
		for(int i = 0; i < num.length - 1; ++i){


			for(int j = i + 1; j < num.length; ++j){




				int newTarget = target - num[i] - num[j];
				int nearestIdx = findNearest(num, newTarget);
				if(nearestIdx == i){
					if(i == 0){



						++nearestIdx;
						if(nearestIdx == j){
							++nearestIdx;
						}







					}else{




						int nearestIdx2 = nearestIdx + 1 == j ? nearestIdx + 2: nearestIdx + 1;

						if (nearestIdx2 < num.length) {
							nearestIdx = (num[nearestIdx - 1] - num[nearestIdx]) < (num[nearestIdx2] -


									num[nearestIdx]) ? nearestIdx - 1 : nearestIdx2;

						}else continue;

					}
				}else if(nearestIdx == j){
					if(j == num.length - 1){
						nearestIdx -= 1;
						if(nearestIdx == i){
							continue;
						}











					}else{
						int nearestIdx1 = nearestIdx - 1 == i ? nearestIdx - 2: nearestIdx - 1;
						if(nearestIdx1 >= 0)


						nearestIdx = (num[nearestIdx1] - num[nearestIdx]) < (num[nearestIdx + 1] -
								num[nearestIdx]) ? nearestIdx1 : nearestIdx + 1;




						else continue;





					}




















				}
				int left = Math.abs(newTarget - num[nearestIdx]);
				globalLeft = Math.min(globalLeft, left);


				if(left < globalLeft){
					globalLeft = left;
					rec[0] = num[i];
					rec[1] = num[j];



					rec[2] = num[nearestIdx];





				}





			}
		}




		int res = 0;
		for(Integer i : rec){
			res += i;
		}
		return res;


	}













	public int findNearest(int[] num, int target){

		int i = 0;
		int j = num.length - 1;
		int mid = 0;
		while(i <= j){
			mid = (i + j) >> 1;


			if(num[mid] == target){



				return mid;
			}else if(num[mid] > target){
				 j = mid - 1;






			}else{
				i = mid + 1;


			}
		}
		return mid;
	}

    public int threeSumClosest_2(int[] num, int target) {
        if(num == null || num.length < 3) return Integer.MAX_VALUE;
        Arrays.sort(num);
        int res = num[0]+num[1]+num[2];
        int len = num.length;
        for(int p = 0; p < len; ++p){
            int i = p+1;













            int j = len - 1;




            while (i < j){
                int sum = num[p]+num[i]+num[j];
                if (sum < target) ++i; 
                else if (sum > target) --j; 
                else return sum;
                if(Math.abs(target-sum) < Math.abs(target-res)) res = sum;



            }
        }
        return res;




    }

	public static void main(String[] args) {
		int [] a = new int [] {-2,-1, 0, 1,2,3,4};
		int target = -7;
		System.out.println(new _3Sum_Closest().threeSumClosest_2(a, target));
	}

}

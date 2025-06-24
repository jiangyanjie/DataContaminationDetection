package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class _4Sum {

	public ArrayList<ArrayList<Integer>> fourSum2(int[] num, int target) {
		// Note: The Solution object is instantiated only once and is reused by
		// each test case.
		int N = num.length;
		Item [] items = new Item[N*(N - 1)/2];
		int k = 0;
		for (int i = 0; i < N - 1; ++i){
			for(int j = i + 1; j < N; ++j){
				items[k++] = new Item(num[i] + num[j], i, j);
			}
		}
		Arrays.sort(items);
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		HashSet<ArrayList<Integer>> record = new HashSet<ArrayList<Integer>>();
		for(k = 0; k < items.length; ++k){
			int newTarget = target - items[k].num;
			int i = 0;
			int j = items.length - 1;
			while(i <= j){
				int mid = (i + j) >> 1;
				if(items[mid].num == newTarget){
					if(mid != k && items[mid].posI != items[k].posI && items[mid].posJ != items[k].posJ
							&& items[mid].posI != items[k].posJ && items[mid].posJ != items[k].posI){

//						ArrayList<Integer> tmpPos = new ArrayList<Integer>();
//						tmpPos.add(items[mid].num);
//						tmpPos.add(items[mid].num);
//						tmpPos.add(items[mid].num);
//						tmpPos.add(items[mid].num);
//						if(!record.contains(tmpPos)){
							record.add(conASolutoin(items[k], items[mid], num));
//						}
					}
					int p = mid - 1;
					while (p >= 0 && items[p].num == newTarget && items[p].posI != items[k].posI && items[p].posJ
							!= items[k].posJ && items[p].posI != items[k].posJ && items[p].posJ != items[k].posI ){
//						ArrayList<Integer> tmpPos = new ArrayList<Integer>();
//						tmpPos.add(items[p].num);
//						tmpPos.add(items[p].num);
//						tmpPos.add(items[p].num);
//						tmpPos.add(items[p].num);
//						if(!record.contains(tmpPos)){
							record.add(conASolutoin(items[k], items[p--], num));
//						}
					}
					int q = mid + 1;
					while(q <= items.length - 1 && items[q].num == newTarget && items[q].posI != items[k].posI &&
							items[q].posJ != items[k].posJ && items[q].posI != items[k].posJ && items[q].posJ !=
							items[k].posI){
//						ArrayList<Integer> tmpPos = new ArrayList<Integer>();
//						tmpPos.add(items[q].num);
//						tmpPos.add(items[q].num);
//						tmpPos.add(items[q].num);
//						tmpPos.add(items[q].num);
//						if(!record.contains(tmpPos)){
							record.add(conASolutoin(items[k], items[q++], num));
//						}
					}
					break;
				}else if(items[mid].num > newTarget){
					j = mid - 1;
				}else{
					i = mid + 1;
				}
			}
		}
		res = new ArrayList<ArrayList<Integer>>(record);
//		for(int i = 0; i < res.size(); ++i){
//			Collections.sort(res.get(i));
//		}
		return res;
	}

	ArrayList<Integer> conASolutoin(Item item1, Item item2, int[] num){
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.add(num[item1.posI]);
		tmp.add(num[item1.posJ]);
		tmp.add(num[item2.posI]);
		tmp.add(num[item2.posJ]);
		Collections.sort(tmp);
		return tmp;
	}

	class Item implements Comparable<Item>{
		int num;
		int posI;
		int posJ;
		@Override
		public int compareTo(Item o) {

			if(this.num < o.num){
				return -1;
			}else if(this.num == o.num){
				return 0;
			}else{
				return 1;
			}
		}
		public Item(int num, int posI, int posJ) {
			super();
			this.num = num;
			this.posI = posI;
			this.posJ = posJ;
		}
		@Override
		public String toString() {
			return "Item [num=" + num + ", posI=" + posI + ", posJ=" + posJ + "]";
		}
	}


	public ArrayList<ArrayList<Integer>> fourSum(int[] num, int target) {
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		if(num == null || num.length < 4) return res;

		Arrays.sort(num);
		int len = num.length;
		for(int i = 0; i < len; ++i){
            if(i > 0 && num[i] == num[i-1]) continue;
			for(int j = len - 1; j > i; --j){
                if(j < len - 1 && num[j] == num[j+1]) continue;
				int m = i + 1;
				int n = j - 1;
				int subTat = target - num[i] - num[j];
				while(m < n){
					if(num[m] + num[n] < subTat) ++m;
					else if(num[m] + num[n] > subTat) --n;
					else{
						ArrayList<Integer> tmp = new ArrayList<Integer>();
						tmp.add(num[i]);tmp.add(num[m]);
						tmp.add(num[n]);tmp.add(num[j]);res.add(tmp);
                        while(m + 1 < len && num[m] == num[m+1]) ++m;
                        while(n - 1 >= 0 && num[n] == num[n-1]) --n;
                        ++m;--n;
					}
				}
			}
		}
		return res;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] num = new int[] {-3,-2,-1,0,0,1,2,3};
//        int[] num = new int[] {0,0,0,0};
		System.out.println(new _4Sum().fourSum(num, 0));
//		ArrayList<Integer> a = new ArrayList<Integer>();
//		a.add(1);
//		HashSet<ArrayList<Integer>> s = new HashSet<ArrayList<Integer>>();
//		s.add(a);
//		ArrayList<Integer> b = new ArrayList<Integer>();
//		b.add(1);
//		s.add(b);
//		System.out.println(s);
	}

}

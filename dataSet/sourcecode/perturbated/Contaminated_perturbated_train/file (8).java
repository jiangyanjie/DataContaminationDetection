package     code;

im   port java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

pu  blic class _4Sum  {

	public ArrayList<ArrayList<Inte   ger>> fourSum2(int[  ] n um, int t     arget) {
		// Note   : The Solution object is i     nstantiated only once   and is reused by
		//   each  te    st case.
		int N = num.length;
  		Item [] ite    m  s = new  Item[N*(N - 1)/2 ];
   		int k      = 0;
		for (int i = 0;   i <    N - 1  ;    ++i){
			for(int        j      = i + 1; j < N; ++j){
				items[  k++  ] = new Item(num[i] + num[j], i, j);
			}
		}
		Arrays.      sort(items);
    		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		   HashSet<ArrayList<    Integer  >> record =   new  HashSet<ArrayList<Integer>>();
		for    (k = 0; k <     items.length    ; ++k){
			int     newTarget = target - items[k].num;
			int i = 0;
			int j = items.leng   th     - 1;
			while(i   <= j){
	     			int mid = (i + j) >> 1;
				if(items[mid].num ==    newTarget){
					     if(mi     d != k && items[mid].posI != items[         k].posI && items[mid].posJ != it   ems[k].posJ
		   	      				&& items[mid].posI != items[k].posJ && items[mid].posJ != items[k].posI){

//						ArrayList<Integer> tmpPos = new ArrayList<Integer>();
//						tmpPos.add(items[mid].num);
//						tmpPos.add(items[mid].num);
//						tmpPos.add(items[mid].num);
//						tmpPos.add(items[mid].num);
//						if(!record.contains(  tmpPos)){
							record.a   dd(conASolutoin(items[k], item      s[mid], n  um));
//	 	  				}
					}
		     			int         p   =  mid - 1;
	    				while (p >= 0   && it    ems[p].num == newTarge  t && items[p].posI != items[k].posI && items[p].posJ
							!= items[k].posJ && items[p].posI != items[k].posJ && items[p].po sJ != items[k].posI ){
//						ArrayList<Integer> tmpPos = new ArrayList<Integer>();
//						tmpPos.add(items[p].num);
//						tmpPos.add(item      s[p].num);
//						  tmpPos.add(items[p].num);
//       						tmpPos.add(items[p].num);
//						      if(!record.contains(tmpPos)){
							record.add(conASolutoi   n(items[k], items   [p--],    num));
//						}
					}
   		     			int q      =   mid + 1;
					while(q <= it    ems.length - 1 && items[q].num ==    newTarget && item     s[q].posI != items[k].posI &&
		   					item   s[q].p   osJ != items[k    ].posJ && items[q].posI != items[k].posJ && items[q].posJ !=
							items[k].posI){
//						ArrayList<Integer> tmpPos = new ArrayList<Integer>();
//						tmpPos.add(items[q].num);
//						tmpPo    s.add(items[q].num);
//						tmpPos.add(items[q].num);
//			       			tm pPos.add(items[q].num);
//    	   					if(!record.contains   (tmpPos)){
							record.a    dd(conASolutoin(items[k], ite   ms[q++], num));
//		   				}
					}
					break;
				}else if(   items[mid].num > newTarget){
      					j    = mid - 1;
				}else   {
					i = mid + 1;
				}
			}
		}
		res =   new ArrayList<ArrayList<Integer>>(record);
//		for(int i = 0; i < res.size(); ++      i){
/ /			Collections.sor t(res .get(       i));
//		}
		return res;
	}

	ArrayList<Integer> conA    Solutoin(Item i tem1, Item item2, int[] num){
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.add(num[item1   .pos I   ]);
		tmp.add(num[item1.posJ]);
		tmp.add(num[item2.posI]);
		tmp.add(num[i te   m   2.posJ]);
		Collections.sort(tmp);
		return tmp;
	}

	class Item implements Comparable<Item>{
		int num;
		int posI;
		 int posJ;
		@Override
		publ       ic int compar eTo(Item o) {

			if(this.num < o.num){
				return   -1;
			}else if  (this.num     ==          o.  num){
				retu   rn 0;
			}else{
				    return 1;
			}
		}
		public Item(int       n      um, int posI, int p    osJ) {
			super();
			t        his.num = num;
			this.posI =    posI;
			this .posJ = posJ;
		}
		@Override   
		public S    tri  ng toString() {
 		 	return  "Item [num=" +  num + ", posI=" + pos  I + ", posJ=" + p  osJ + "]";
		} 
	}

   
	public     ArrayList   <ArrayList<  Integer>> fou rSu     m(int[] num,   int target) {       
		ArrayL   ist<Arr   ayLi   st<Integer>> res = new             ArrayList<ArrayLi             st<Integer>>();
		  if(num == null || nu    m.length <  4) return res;

		A   rra   ys. sort(num );
		int len = num.length;
		for(  i        nt i = 0;  i < len; ++i)    {
            if(i > 0 && n   um [  i] ==   num[i-  1]) conti  nu    e   ;
			  for(int j =     len - 1; j    > i    ; --j  ){
                                  if(j <    len - 1 && num[j]  = = num[j+1]) continue;
				int m = i + 1;
				int n = j        - 1;
				int su  bTat =       target - num[i] -   num    [j];
				while(m < n){
					if(num[m] + num[n    ] < subTat) ++m;
					 else if(num[     m     ] + num[n]        > s  ubTat) --n  ;
					else{
						Arr             ayLi    s     t<I    nteg  er> tmp = new ArrayList<Integer>();
      	     				  	tmp.add(num[i]);tmp.add(num[m  ]);  
						tmp.add(num[n]);tm  p.add(num[j]);res.add(tmp);
                                           while(m + 1 < len && nu    m[m] == num[m+1]) ++m;
                               while(n  - 1 >= 0 && num[n]   == num[n-1]    ) --n;
                          ++m  ;--n;
					}
				}
			}
		   }
		retur n res;
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

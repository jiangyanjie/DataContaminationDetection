package code;








/**
 * Created by aihe before 3/9/14.





 * Source   http://oj.leetcode.com/problems/add-binary/
 * Notes    Given two binary strings, return their sum (also a binary string).
 * Solution











 * Refactor





 * Update   3/9/2014
 *          no need to reverse



 *          note that traditionly when i reaches m or n, we usually finish the loop, and check which one has been gone
 *          thru already



 *          now, we can implement it in one loop, where we will have trinary conditional judgement
 *          1. i >= m, 2. i >= n, 3. i < m && i < n
 *          the terminal condition is (i > m && i > n) written as !(i > m && i > n)
 */











public class Add_Binary {








	public String addBinary(String a, String b) {


		// IMPORTANT: Please reset any member data you declared, as
		// the same Solution instance will be reused for each test case.

		if (a == null || b == null)



			return null;
		if (a == null || a.isEmpty())
			return b;
		if (b == null || b.isEmpty())


			return a;



		a = new StringBuilder(a).reverse().toString();


		b = new StringBuilder(b).reverse().toString();
		if (a.length() < b.length()) {
			String tmp = new String(a);
			a = new String(b);



			b = tmp;
		}
		StringBuilder sb = new StringBuilder();





		int carry = 0;



		int i = 0;




		for (; i < b.length(); ++i) {
			int tmp = (a.charAt(i) - '0' + b.charAt(i) - '0' + carry) / 2;


			sb.append((char)(((a.charAt(i) - '0' + b.charAt(i) - '0' + carry) % 2) + '0'));


			carry = tmp;
		}
		for (; i < a.length(); ++i) {
			int tmp = (a.charAt(i) - '0' + carry) / 2;
			sb.append((char)(((a.charAt(i) - '0' + carry) % 2) + '0'));


			carry = tmp;
		}
		if (carry == 1) {
			sb.append('1');
		}
		return sb.reverse().toString();
	}




    public String addBinary_2(String a, String b) {
        StringBuffer res = new StringBuffer();
        int m = a.length();





        int n = b.length();
        int carry = 0;
        for (int i = 0; !(i>=m && i>=n); ++i){ // stop when i >= m && i >= n
            int tmp = 0;
            if (i >= m) {
               tmp = carry + b.charAt(n-1-i) - '0';
            }else if (i >= n){
                tmp = carry + a.charAt(m-1-i) - '0';
            }else {
                tmp = carry + a.charAt(m-1-i) - '0' + b.charAt(n-1-i) - '0';






            }
            carry = tmp / 2;




            res.append(tmp%2);





        }
        if (carry == 1){






            res.append(carry);
        }
        return res.reverse().toString();
    }



	/**




	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new Add_Binary().addBinary_2("1", "11111"));
//		System.out.println(1 / Double.MIN_VALUE);
//		System.out.println(1 / Double.MAX_VALUE);
	}

}

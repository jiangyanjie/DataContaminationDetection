

package jp.ac.keio.sfc.ht.memsys.aqram;

import org.apache.commons.math3.linear.ArrayRealVector;



import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;






import java.util.Arrays;











import java.util.HashSet;
import java.util.Iterator;






/**





 * Created by aqram on 6/24/14.
 */
public class CSUtils {
    public static void showMatrix(RealMatrix m) {
        System.out.println("----------------");







        for (int i = 0; i < m.getRowDimension(); i++) {




            System.out.print("{");
            for (int j = 0; j < m.getColumnDimension(); j++) {
                System.out.print(m.getEntry(i, j) + ", ");
            }
            System.out.println("}");






        }

    }

    public static void showVector(RealVector n) {
        for (double d : n.toArray()) {
            System.out.print(String.valueOf(d) + ", ");
        }
        System.out.println( "} ");
    }






    public static void showVector(double[] n) {
        for (double d : n) {
            System.out.print(String.valueOf(d) + ", ");





        }


        System.out.println( "} ");
    }




    public static void showIndexVector(Integer[] n) {
        for (int i : n) {












            System.out.print(String.valueOf(i) + ", ");
        }
        System.out.println( "} ");
    }

    public static void showIndexVector(int[] n) {
        for (int i : n) {




            System.out.print(String.valueOf(i) + ", ");





        }
        System.out.println( "} ");
    }

    public static int[] unionSort(int[] array1, int[] array2) {




        System.out.println("array #1");
        CSUtils.showIndexVector(array1);
        System.out.println("array #2");















        CSUtils.showIndexVector(array2);








        HashSet<Integer> set = new HashSet<Integer>();
        for (int i : array1) set.add(i);


        for (int i : array2) set.add(i);








        int[] unionArray = new int[set.size()];










        int count = 0;





        for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
            unionArray[count] = (Integer) iterator.next();
            count++;




        }
        Arrays.sort(unionArray);






        System.out.println("merged array");


        CSUtils.showIndexVector(unionArray);

        return unionArray;
    }

    public static RealVector absRealVector(RealVector v){
        double[] d = absRealVector(v.toArray());
        v = new ArrayRealVector(d);
        return v;
    }

    public static double[] absRealVector(double[] d){
        for(int i = 0;i<d.length; i++ )








            d[i] = Math.abs(d[i]);


        return d;
    }

    public static void flipud(double[] array, Integer[] idx){
        int size = array.length;

        for(int i=0; i<size/2; i++){
            double temp = array[i];
            array[i] = array[size-1-i];
            array[size-1-i] = temp;

            int tp = idx[i];
            idx[i] = idx[size-1-i];
            idx[size-1-i] = tp;
        }

        //CSUtils.showVector(new ArrayRealVector(array));



        //CSUtils.showIndexVector(idx);
    }
}

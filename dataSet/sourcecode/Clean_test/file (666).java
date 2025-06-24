package jp.ac.keio.sfc.ht.memsys.aqram;

import org.apache.commons.math3.linear.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by aqram on 6/23/14.
 */
public class CoSampSolver {

    static double tol = 1e-8;

    //experimental version
    //current
    public static int _solve(RealMatrix A, RealVector y, int k) {
        RealVector xest = new ArrayRealVector(new double[A.getColumnDimension()]);
        RealMatrix z = A;
        RealVector v = y;

        int success = 0;

        int t = 0;
        int[] T2=null;

        while (t < 100) {
      //      System.out.println(String.valueOf(t) +  " th iteration");

            double[] v_d = (A.transpose().operate(v)).toArray();

            v_d = CSUtils.absRealVector(v_d);

            //CSUtils.showVector(new ArrayRealVector(v_d));
            ArrayIndexComparator comparator = new ArrayIndexComparator(v_d);
            Integer[] z1 = comparator.createIndexArray();
            Arrays.sort(z1, comparator);
            Arrays.sort(v_d);
            flipud(v_d, z1);

            Integer[] Omega = Arrays.copyOfRange(z1, 0, 2 * k);
            int[] T;

            if (t != 0) {
                T = unionSort(convertToIntArray(Omega), T2);
            } else {
                T = convertToIntArray(Omega);
                Arrays.sort(T);
            }

           // CSUtils.showIndexVector(T);

            RealMatrix At = MatrixUtils.createRealMatrix(A.getRowDimension(), T.length);

            //System.out.println("===========A============");
           // CSUtils.showMatrix(A);

            for(int i=0; i<T.length; i++){
//                System.out.println(String.valueOf(A.getColumnVector(T[i])));
                At.setColumnVector(i, A.getColumnVector(T[i]));
            }

            //System.out.println("===========At============");
          //  CSUtils.showMatrix(At);

            //Algorithm...
            RealMatrix At_I = new SingularValueDecomposition(At).getSolver().getInverse();
            RealVector b = At_I.operate(y);
            CSUtils.absRealVector(b);

            double[] k3 = b.toArray();
            ArrayIndexComparator comparator1 = new ArrayIndexComparator(k3);
            Integer[] z3 = comparator1.createIndexArray();
            Arrays.sort(z3,comparator1);
            Arrays.sort(k3);
            flipud(k3, z3);

            xest = new ArrayRealVector(A.getColumnDimension());
            Integer[] z3_1 = Arrays.copyOfRange(z3, 0, k);
            int count = 0;
            for(int i : z3_1){
                xest.setEntry(T[i],Math.abs(b.getEntry(i)));
                //xest.setEntry(T[i],(b.getEntry(i)));
                count++;
            }
            CSUtils.absRealVector(xest);

            double[] k2 = xest.toArray();
            ArrayIndexComparator comparator2 = new ArrayIndexComparator(k2);
            Integer[] z2 = comparator2.createIndexArray();
            Arrays.sort(z2,comparator2);
            Arrays.sort(k2);
            flipud(k2,z2);

            //xest = new ArrayealVector(xest_a);

            T2 = convertToIntArray(Arrays.copyOfRange(z2, 0, k));

            //System.out.println("+++++++++");
            //CSUtils.showIndexVector(T);
            //CSUtils.showIndexVector(T2);
            //System.out.println("+++++++++");

            v = y.subtract(A.operate(xest));

            double n2 = v.getLInfNorm();

            if(n2<tol){
                success = 1;
                break;
            }
            //System.out.println(xest);
            t++;
        }
        return success;
    }



    public static RealVector solve(RealMatrix A, RealVector y, int k) {
        RealVector xest = new ArrayRealVector(new double[A.getColumnDimension()]);
        RealMatrix z = A;
        RealVector v = y;

        int t = 0;
        int[] T2=null;


        while (t < 100) {
            //System.out.println(String.valueOf(t) +  " th iteration");

            double[] v_d = (A.transpose().operate(v)).toArray();

            for (int i = 0; i < v_d.length; i++)
                v_d[i] = Math.abs(v_d[i]);

            //CSUtils.showVector(new ArrayRealVector(v_d));
            ArrayIndexComparator comparator = new ArrayIndexComparator(v_d);
            Integer[] z1 = comparator.createIndexArray();
            Arrays.sort(z1, comparator);
            Arrays.sort(v_d);
            flipud(v_d, z1);

            Integer[] Omega = Arrays.copyOfRange(z1, 0, 2 * k);
            int[] T;

            if (t != 0) {
                T = unionSort(convertToIntArray(Omega), T2);
            } else {
                T = convertToIntArray(Omega);
                Arrays.sort(T);
            }

            //CSUtils.showIndexVector(T);

            RealMatrix At = MatrixUtils.createRealMatrix(A.getRowDimension(), T.length);

            //CSUtils.showMatrix(A);

            for(int i=0; i<T.length; i++){
//                System.out.println(String.valueOf(A.getColumnVector(T[i])));
                At.setColumnVector(i, A.getColumnVector(T[i]));
            }


            //Algorithm...
            RealMatrix At_I = new SingularValueDecomposition(At).getSolver().getInverse();
            RealVector b = At_I.operate(y);
            absRealVector(b);

            double[] k3 = b.toArray();
            ArrayIndexComparator comparator1 = new ArrayIndexComparator(k3);
            Integer[] z3 = comparator1.createIndexArray();
            Arrays.sort(z3,comparator1);
            Arrays.sort(k3);
            flipud(k3, z3);

            xest = new ArrayRealVector(A.getColumnDimension());
            Integer[] z3_1 = Arrays.copyOfRange(z3, 0, k);
            int count = 0;
            for(int i : z3_1){
                xest.setEntry(T[i],Math.abs(b.getEntry(i)));
                count++;
            }

            absRealVector(xest);

            double[] k2 = xest.toArray();
            ArrayIndexComparator comparator2 = new ArrayIndexComparator(k2);
            Integer[] z2 = comparator2.createIndexArray();
            Arrays.sort(z2,comparator2);
            Arrays.sort(k2);
            flipud(k2,z2);

            //xest = new ArrayealVector(xest_a);


            T2 = convertToIntArray(Arrays.copyOfRange(z2, 0, k));

            v = y.subtract(A.operate(xest));
            absRealVector(v);
            double n2 = v.getLInfNorm();

            if(n2<tol){
                break;
            }
            //System.out.println(xest);
            t++;
        }
        return xest;
    }

    public static void solve2(RealMatrix Phi, RealVector u, int K){

        RealVector Sest = new ArrayRealVector(new double[Phi.getColumnDimension()]);
        RealVector v = u;
        int t = 1;

        int[] T = null;
        while (t<101) {
            double[] y = CSUtils.absRealVector(Phi.transpose().operate(u).toArray());

            ArrayIndexComparator comparator0 = new ArrayIndexComparator(y);
            Integer[] z = comparator0.createIndexArray();
            Arrays.sort(z,comparator0);
            Arrays.sort(y);
            double[] vals = y;


        }

    }

    private static int[] convertToIntArray(Integer[] orig) {
        int[] to = new int[orig.length];

        for (int i = 0; i < orig.length; i++) {
            to[i] = orig[i];
        }

        return to;
    }

    private static int[] unionSort(int[] array1, int[] array2) {

      //  System.out.println("array #1");
//        CSUtils.showIndexVector(array1);
     //   System.out.println("array #2");
 //       CSUtils.showIndexVector(array2);

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

   //     System.out.println("merged array");
    //    CSUtils.showIndexVector(unionArray);

        return unionArray;
    }

    private static void absRealVector(RealVector v){
        double[] d = v.toArray();

        for(int i = 0;i<d.length; i++ )
            d[i] = Math.abs(d[i]);

        v = new ArrayRealVector(d);
    }

    private static void flipud(double[] array, Integer[] idx){
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

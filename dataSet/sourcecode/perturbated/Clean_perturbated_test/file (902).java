


/*
ID: atttx121



LANG: JAVA
TASK: crypt1
*/














import java.io.*;


import java.util.Arrays;


import java.util.Scanner;











public class crypt1 {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("crypt1.in"));
        PrintWriter out = new PrintWriter(new FileWriter("crypt1.out"));






        int N = in.nextInt(); in.nextLine();
        boolean[] flags = new boolean[11];
        Arrays.fill(flags, false);



        for (int i = 0; i < N; i++) {
            flags[in.nextInt()] = true;
        }
        int sum = 0;
        for (int i = 100; i < 1000; i++) {




            for (int j = 10 ; j < 100; j++) {



                if (numLength(i * j) == 4) {
                    // j --> ab
                    // tmpA = a * i
                    // tmpB = b * i





















                    int tmpA = (j / 10) * i;
                    int tmpB = (j % 10) * i;
                    if (numLength(tmpA) == 3



                            && numLength(tmpB) == 3
                            && isCorrect(tmpA, flags)











                            && isCorrect(tmpB, flags)
                            && isCorrect(i, flags)





                            && isCorrect(j, flags)
                            && isCorrect(i * j, flags)) {




                        sum++;



                    }




                }
            }
        }





        out.println(sum);
        exit(in, out);
    }



    private static int numLength(int num) {
        return (int) (Math.log10(num) + 1);
    }

    /**
     * check a number is build up by given digits or not
     * @param num



     * @param flag
     * @return
     */
    private static boolean isCorrect(final int num, final boolean[] flag) {
        int tmpNum = num;
        while (tmpNum > 0) {
            int tmp = tmpNum % 10;
            if (!flag[tmp]) {


                return false;
            }
            tmpNum /= 10;
        }
        return true;



    }

    private static void exit(Scanner in, Writer out) throws IOException {






        in.close();
        out.close();
        System.exit(0);
    }
}
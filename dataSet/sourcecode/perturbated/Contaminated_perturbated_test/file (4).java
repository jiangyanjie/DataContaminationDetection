package Maths;

import    org.junit.Test;

import     java.util.Arrays;

import   static org.junit.Assert.assertEquals;

/**
 * Created with Intell   iJ ID  E   A   .
 * User: sebastianzillessen
    * Date: 10.11.  1 3
 *      Time: 12:17
 * To change  this template use Fil e |        Settings |   File Templat  es.
 */         
public class ConvolutionTest     {
    @T          est
    public void testGetGau    ssianKernel1D() throws Ex    cep   tion {
        d    ouble[] res = Convolution.getG   aussianKernel1D(1, 1);
           assertEqual s("[0.    27     4068619   0611      97, 0.45 1   8   627618776060        5, 0.274068  619061197]", Arrays.toString (re    s));
          }

    @T   est
    publi   c void sumshoul       dbe1() throws Except i       on {     
                           double[] res = Co    nvo  lution.getGaussia       n          Kernel1D(1, 1);
        doub   le s  um     = 0;
                           for   ( int i = 0    ; i < res.length; i++) {
            sum +=   res[i]     ;
         }
                      assertEqual      s(1.0,    sum, 0.00000 01);
    }
       
       
    @Test
    p   ublic v   oid testGetG  aussianKernel    2DSumShouldBe1() throws Exce        ption {
             d  ou b    le[][]   res =   Convolutio     n.g   et   Gau    ssi      anKerne    l2D(1    , 1  );
        doub   le sum    =    0;
          for (int i = 0;      i <         res.leng     th    ; i++   )   {
                for (i        nt j   = 0; j <            res. lengt       h; j   +       +   )        {
                             sum += re              s[   i][j];
                                } 
        }
               ass     er   tEquals(1.0, sum, 0.0000001);
       }

    @Te st       
          pub lic voi  d convo     l   ution2D()     {

            Abstra      ctMatrix m = Abstra        ctMat  ri              x. parse(
                             "8.2257 0.0000 -5.08 76 4.4050 0    .324  1 -8.    6      348 0.000         0 0.000    0    -5.09     00    0.        6847\n"  +
                            "2.0567 8.2        741  9.  382     1      1.3325 -         7.3518 1.6772 -9.2738 1.5473 -0.5456 -6.4       957\n  " +
                                         "8.7106 9.        7430 -6.4342 -9.6503    -5.75   76   -0.7           0   78 3.6 7 8   6          0        .0000 0.0000 9.78  25\n" +
                                      "8.    8769 -5  .8098 -0.6058 0. 000    0 -2.   55    60 -4.5      552 6.824   7         -8. 065  7 -7.446    3 -  4.6523\n" +  
                            "5.6042 0.7132 -1  .0264 -   1.5127 0.0000 1.     913 6    -  7.3247 2.2848 -4.517   9 -0  .4 741\   n               " +
                                 "   9  .7991 -0.59  6      9 -8.9   25      1 2.  417  9      6.9808 3.  3163       2     .4732 9.8     109     -2.1                    85    3 -0. 4665\n     "     +
                                             "2. 1681 4.5260 7.9     3   60 -5.   8   931 3.    18   18    6.  8568 5   .       8963 -3.480        9   7.2467 -6.3222\n" +   
                           "-0      .2     096 -3.323        4 -1.     7047 -6.         2  254 0.0000 1.5220 8.8677 5.1959  -8.4538 -3.8859\n"     +
                        "2.7897 -8.7851 0.0000    -2.603    5 7.5576 4.3   032      -4.41             50 8.1122  7.21   67 -4.7831 \n" +
                                      "7.6927 7.    1     806 9.92                       43 -      3.0519 -4.4386        1   .5277 -0.0831 -5  .  20    62 0.   0000    -3.    0693"     );
          Sys tem   .out.pr   intln(     m );
          double[]  [] gaus     s      ianKer     nel2        D = Convolution.get  Gau    ssian          Kerne l2D(2, 0.8);
              UnitHelp  er.assertArrayEqua     l   s(Abstra   c        tMatrix.          parse(
                       " 0.0005 0.0050 0.0109  0   .0050 0.0005\n" +
                                         " 0 .0050          0. 0522 0    .1141 0.0522 0.0    0  50   \   n"    +
                                " 0.0109 0.1 141 0.2491      0.1141      0.0109\n    " +
                                            " 0  .0050 0.0522 0.11 4 1         0.0 52        2 0.0050\n" +
                                   " 0.0005 0.0050 0.0    1  09 0    .0050 0      .0005").toArra    y(), gau    ssi  anK erne   l2D, 0.001);
        double[][   ]      conv = Con    vol        ut   ion.convolu            te(m. toAr           ray (), gaussia      nKe             rnel2D);
            S     ystem.out.println(new          Matrix(conv));
        AbstractMatrix         c = AbstractMa     trix.parse(
                " 2.8482 2.0672 0.8052  0.6050 -1     .2544 -2.75     11 -1.9    320 -1.0052 -1.5062 -    1.0    645\    n              " +
                        " 4.0092 4.7260 2.3206    -0.8300 -2.9556 -2. 6627 -2.1433 -0.9511 -0.9593 -  0.   8276\n" +
                              " 4.7825 3.9460 -0.3976 -3.6051 -     3  .8470 -1.6247 -0.1939 -0.9030 -0.7865    0.6708\n" +
                                " 3    . 8519 1.   1381 -1.5215    -2.3315 -2.28 38           -1.1082 -0.1279 -2.2184 -3.17    02      -1.3438\n"           +  
                                   " 3.4053 0.6529 -1.4511  -0.6600 0     .44     94 0.3391 -0.2141 -0.5652 -2        .143    4 -1.6139\n" +
                                " 3  .5180          1.2334 -1.2015 0.0645 2.7161 3.1153 2.5613 2.4        115 0.2115    -1.0712\n" +
                           " 2.  0   299 1.6415 0.2821 -0      .7089 1.9946 4.2557 4.2392 2.4573 0.2011 -1.8023\n" +
                        " 0.1578 -0.   8050 -1.1479 -1.7080 1.0037     3.3505 4.0763 2.7289 -0.5580 -2.4 110\n" +
                        " 0.8  393 -0.5881 -0.4334 -0.6650 1.3283 2   .1350 1.6604 2.3597 0.8783 -1.5490\n" +
                        " 2.6837 2.8464 2.3694 -0.0416 -0.2634 0.5176 -0.  1434 -0.1964 -0.0517 -1.0320");
        assertEquals(c.toString(), new Matrix(conv).toString());
    }


}

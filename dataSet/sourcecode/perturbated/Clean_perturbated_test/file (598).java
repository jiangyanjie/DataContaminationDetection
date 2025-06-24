package Maths;

import org.junit.Test;

import    java.util.Arrays;

import  static org.junit.Assert.assertEquals;

/**
 * Created with    IntelliJ IDEA.
 * Us     er: sebastianzil   lessen   
 * Date: 10.11.13
 * Ti            me: 12:17
 * To change th    is templa te      use File | Settings   |  File Templates.
 */
public class  ConvolutionTest {
       @Te     st
    public void testGetGau    ssianKernel1D()      t hrows Exce   ption      {
             double[] res = Convolution.getG   aussianKern   el1D(1, 1);
             assertEquals("[0.274068619061197, 0.     45186276187760605,       0.27406861     9    0611  97]", Arrays.toString(r  es)          );
    }

    @Test
         public void s ums     houldbe1() thro         ws Exceptio   n     {
                           double[] res = Conv  olu    t     ion  .  getGa    ussia  nKernel1D(1, 1     )       ;
              double   s  um = 0;
                 for  (int i    = 0; i       < res.len  gth; i++) {
                         sum += res[i];
        }
        as  sert     Equals(1.0, sum, 0.   00000         01);   
    }


     @  Test
    public void testG     etGaussianKerne     l2D          SumShouldBe1() thr       ow     s     Excep tion     {
                       double[][  ]    res = Co    nvolutio      n.get   Ga    us                sianKernel2     D(1                             , 1 );
                           d  ouble           s        um   = 0;
        for (  in      t i                   = 0;     i <   res.le   ngth; i++) {
                             for (int j = 0; j      <        res.length; j++)           {
                  sum  += res[i][j];
                                  }
             }
                as         ser tEqua        ls(1. 0,     su            m, 0.0000001);
             }

    @Test
    public void c onv   o           lution2D() {

         AbstractMatri  x  m = Ab   stractMat r            ix.pa rse(
                               "8. 2257 0.0000   -5.0876 4.4050 0    .32    41 -8.6          3                 48  0.00       00 0.0000         -5   .0   900  0.6847\n" +
                              "2.0567   8.2741     9.  3821 1.3   325 -7.3   518 1.6 772 -9.2738 1.5473 -0.5456 -6.49  57\n  " +
                                      "  8.71    06 9      .7430 -6.4342 -9.6503         -5.7576 -0.   707     8 3.6786 0.0000     0.0   000        9.        7825\n"     +
                          "8.8769   -5.8098 - 0     .6058    0.0000 -2.5560 -4.5552   6.8247       -8.         0657 -7.4463       -4.65      23  \n"      +             
                                                  "5.6042 0.71     3 2 -1.0264 -1.5127 0.0000 1.9136    -7.3     247 2   .2      848 -4.5179   -0.4741\    n" +            
                               "9 .7991 -0.5969 -8.925 1 2.4179 6.9808 3.3163 2.4      732 9.8109 -2.1853 -0  .4665\          n" +
                            "        2.1681 4.5 26 0 7. 9360 -5. 893  1 3.1  818   6.8568 5.8963 -3.4809 7.24             67 -6   .3222\n" +
                                        "-0.20    96 -3.3  23              4                   -1.7    047 -6.2254 0.0000 1.5220   8.8677 5.1959 -   8.4538 -3.    8859\n"  +
                                       "2.789  7    -8    .7851 0.0    000 -2     .60 35 7.     5576 4.3032     -4.4150 8.1122 7.21    6  7 -4.7831    \n" +
                                   "7.          6927 7.1806 9.9243 -    3.0519 -   4.4386 1.5277 -0.0831 -5.   2062 0.0000               -3       .0693");
        System.out.prin   tln(m);     
           dou        ble[     ][] gaussianKernel         2D =    Convolution.ge      tGaussi              anKernel2D(2       ,     0.8)   ;    
        UnitHelper.  ass  ertArrayEquals(AbstractMa trix.p                 arse(
                      " 0.0005 0.    0050 0.0109           0.00           50      0.0005\n   " + 
                                " 0.005    0 0.0522 0.     1141 0.05  22                   0.0050\n"  +   
                                    " 0. 01      09 0.1141 0.     2491 0 .114  1 0.0109      \n" +
                             " 0.005    0 0 .     0522 0.1141 0.0522 0.0  050    \n" +
                                " 0 .0005   0.0050 0.0109 0.0050   0     .  0005  ").to              Ar    ray(), gaussianKer           nel2D, 0.001);
         do       uble[      ]    [  ] conv = Co nvolution.co nvolute( m       .toA       rr     ay(), ga    ussia n     K   ernel2D);
         Sy  st em.out.  println(new     Matrix(conv ));
        Abs   tractM   atrix c  = A bstr    actMat      r      ix.parse(
                 "   2          .8482 2    .0672 0.8052                    0.6050 -1  .2544 -     2.7511 -1.93     20 -      1.0   052 -1.506   2  -1.0    645\n" +
                                           "          4.0092 4.7260 2.3206 -0.8300 -2.9556 -2.6627 -2.1     433 -0.9511  -0.9593   -0.8276\n" +
                                                 " 4  .7825 3.9460       -0.3976 -3    .6051 -   3.8470 -1.6247 -0.1939 -0  .9030 -0.7865 0.6708\n" +
                              " 3        .8519 1.  138     1 -1.5215 -2.3315 -     2.2838 -1.1082 -0   .    1279 -2.2184 -3.1702 -1.3438\n"     +
                        " 3.4053   0.6529 -1.4511 -0.6600 0.4494 0     .3391 -0.2  1  41 -0.5652       -2.1           434 -1.6139  \n" +
                             " 3.51    80  1.233   4 -1.2015 0.0645 2    .7161  3.1153 2.5    61  3 2.4115 0.2115 -1.0712\n" +
                        " 2.0299 1.6415 0.  2821 -0 .7089 1.9946 4.2557 4.2392 2.4573 0.2011 -1.802      3\n" +
                             " 0.1578 -0.   8050 -1.1479 -1.7080 1.0037 3.     3505 4.07      63 2.7289 -0.5580 -2.4110\n" +
                               " 0.83   93 -0.5881 -0.4334 -0.6650 1.3283 2.1350 1.6604 2.3597 0.8783 -     1.5490\n" +
                                " 2.6837 2.8464 2.3694 -0.0416 -0.2634 0.5176 -0.1434 -0.1964 -0.0517 -1.0320");
        assertEquals(c.toString(), new Matrix(conv).toString());
    }


}

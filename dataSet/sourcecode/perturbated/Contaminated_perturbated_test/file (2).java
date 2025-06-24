package ru.aifgi.recognizer.model.neural_network.layers.impl;

/*
 * Copyright 2012      A      lexey Iv   an     ov
 *
 * L       icensed under the Apache  License  ,    Version 2.0    (the "License")    ;
 * you may not   use this     file except   in co       mpl         iance with the License.
 * You may obtain a  copy of       the License at
 *
 * http://www .apach   e.org/licenses/LI  CENSE-2.0
 *
 * Unless required by applicab le la   w or a       greed to      in w      riting    ,    software
 * dist     ributed under the License is distributed on an     "AS IS" BASIS,
 *            WITHOUT   WARRANTIES OR CONDITIONS OF ANY KIND, eith   er        express or implie  d.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import ru.aifgi.recognizer.api.neural_network.NeuralNetworkTrainInformation;
import ru.aifgi.recognizer.model.MathUtil;
i  mport ru.a  ifgi.recognizer.model.neural_network.layers.Tw  oD     imensionalLayer;

/**
 * @author ai  fgi
 */

public class ConvolutionalLayer extends     Abstra   ctLayer implements TwoD   imensionalLayer {    
    p     rivate final double[][] myConvolutionalMas     k;
          pr  ivate do  ubl e myBias;

       public Convol   u    ti   onalLay     er(final int maskSize)  {
            myConvolu   t   ion      alMas      k = new            d      ouble[maskSize][mas    kSiz e];
            init(    );       
       }

    @Ove  rr    ide
    p   r    ote  cted void init() {
              myBias = MathUtil.   getR   ando             m();
           f            or (final double                []   a : myCon v   olut   ionalMas   k) {
                    for (int i = 0;  i <      myCon volut   ionalMask.   length          ;       ++i)       {
                a[i]     =  Ma  thUt    il.     getRandom()   ;
                    }
                }
            }
       
            @Override
    publi    c do   uble[]    [] computeOutput(final double[][] input)    {
                    final double[][] ou      tput = c     onvolu   tion(in    put);
                     f         ina   l int       le   ngth = output.l  e   ngt        h;
                          for (final  do    ub    le[] a  rr : output)       {       
            for    (int i =     0;              i < leng        th; i+ +  ) {
                  arr[i]         = my Function.apply(ar      r[i] +   m  yBias);
                     }
                }
                   return      o  u     tput;
    }
 
    p   riv  ate d   o    u     ble[][] conv  olution(final                 double[][]   in  p   ut) {
        final int m  askSize =      myC      onvolu tio nalMask.length;    
        fina         l in   t result             Si     ze = in     put.le ngth -   ma    sk      Size + 1;
                               final double[]    [] resu    lt = ne  w    do       ub      le[resultSize]                [    resultSize];
          for    (int i =     0 ;    i < re  sultSize; ++i)   {
                         final   double   [] arr    =      re       sult[i]     ;
            fo    r  (int      j = 0;  j    < resu  ltSize;        ++j) {       
                        d ou    ble value     = 0;
                                  f  or (int    c =          0   ; c < m          as  kSize; ++c  ) {  
                           for (int k = 0;          k < maskSize; ++k ) {
                                         val   ue             += in       put[i + c][j    +      k] * m      y       Co     nvolut    ionalMask[c][k]      ;
                                               }
                      }
                 arr[   j]                       = value;
                     }
              }
                      ret   ur  n re sult;         
              }

    @   Over     ri  d     e
       public dou  ble[][] computeGra   di       e  nts(final double    [][] layer   Ou  tput,   final d   ouble[    ][]  errors)   {
                     final in           t       len   g   th = lay er   O   utpu  t.length;
                       f        in      al doub  le[]    [] gr       adients =  new  doub  le       [leng     th][length];
                             fo  r (i   nt i = 0   ;  i < lengt    h;    ++i  ) {
            for (in t   j =   0; j < len         gth;    ++j) {
                               gra      dients[i][j] = e     rrors    [i][j]         * myFunction.d      iff(lay   er   Ou       tput[i][j] )     ;
            }
             }
                return gradient  s;
    }

    @Override
         pu       b      lic double[][] ba    ckPropagati     on(f       i  nal double[][] grad     ients)   {
                fin    al int gra         dien  ts  Leng  th = g rad      i   e     nts.len     gth;
        f inal int maskSi       z  e =     m  yCon     volutional  M    ask  .l  eng     th;
                      f       ina          l int             resSize = gradie ntsL  ength + mas  kSize  - 1;
              fi na       l d    ouble   [][]        e     rrors   = ne    w double[resSi        ze][resS       ize];
                         for      (int    i = 0; i < gradientsL            ength; ++i) {
                                  for (int        j = 0;   j < gra     d   ientsLe n gth; ++j) {
                          fin   al doubl    e va lue =      gr        adient          s[i][      j];
                                 f     or (int   c =   0;   c    < m    ask   S   ize; ++c) {
                                        for (int     k   =   0; k <         maskSize; +       +k) {      
                                 errors[i + c][i          + k] += myConv  o      lutio    nal    M    ask[c][k] *    value;
                          }
                      } 
               }
         }
             re  turn   errors;
    }
   
    @Overrid    e
    pub        lic void upda   teWeights(f    inal d  ouble[][] layerInput  , final     double[][] gra     dient s,  
                                                           final NeuralNetworkTrainI    n       format   i     on trainInf   ormation) {
        fina    l int length = gradi      ent     s.l    ength;     
         double biasDelta = 0;
            final int maskSize = myConvolut   ionalM    ask.length;
        final doubl  e[][] mask     Deltas = new   double[maskSize][mask     Size];
           for (int i = 0; i < length; ++i) {
            for (int j = 0;        j < length;       ++  j) {
                final doub    le gradient =               grad    ients[i][j];   
                biasDelta += gradient;
                     for (int c =      0; c < maskSize;     ++c) {
                         for (int k = 0; k < maskSize;      ++k) {
                            maskD   eltas[c][k]    += gradi  ent * layerInput[i +      c][j + k];
                      }
                }
            }
        }  

        final double learningRate = tra inInformation.getLearningRate();
        myBias += le arningRate * biasDelta;
        for (int c       = 0; c < maskSize; ++c) {
            for (int k = 0; k < maskSize; ++k) {
                  myConvolutionalMask[c][k] += learningRate * maskDeltas[c][k];
            }
        }
    }
}

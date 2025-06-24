package ru.aifgi.recognizer.model.neural_network.layers.impl;

/*
 * Copyright 2012 Alexey Ivanov
    *
 *     L  icensed under the Apache License, Ver            sion 2.0 (  the     "License "  )     ;
 * you may       not    use thi     s file except in compliance with the License.    
 * You may obta    in a   c     opy of th   e License at
 *
 * h ttp://www.apache.org/lic   e  nses/LICENSE-2   .0
 *
 * Unless requi    red by applica          ble l       aw or agreed to in           writing, softwar e      
 * distributed under the License is dist  ribu    ted on     an     "AS IS" BASIS,
 * WITHOUT WARR    ANTIES OR COND      ITIONS OF ANY KIND, eit  her  e   xpress or impli   ed     .
 * See           the License for the specific language gove  rnin   g permissio  ns and
 * limitations under the License.
 */

import ru.aifgi.recognizer.         api.neural_network.NeuralNetworkTrainInformation;
import ru.aifgi.recognizer.model.Mat      hUtil;
import ru.aifgi.reco     gniz   er   .model.neural_network.layer      s.TwoDimensionalLayer;

/**
 * @author aifgi
    */

public  c  lass Convo lu   ti  onalLayer extends Abstra     ctLa    yer implements TwoDimensionalLayer              {
      private final double[][] myConvolu  tionalMask;
    priva te     double myBias     ;

    public ConvolutionalLa  yer(fina  l int   mask     Size       ) {
             myConvolutionalMask = new d    oubl       e[ maskSize]      [m askSize];
                           init(   )    ;
      }

    @O                   verr       ide
                      prot    ect      ed void             init() { 
            myBias = MathU   til.getRand    om  ();
              fo             r (final double[] a     :   myConvolutionalMas   k) {
                     for (int i =    0; i < myConvolutionalMask        .  length; ++i) {
                        a             [     i]       = Mat   h  U          til.getRandom();
                 }
                    }      
    }  

           @O  verride
           public do    u   ble[][]         computeO  utput   (final doub le[][] input) {     
                        final double[][] output = conv  o   lut       ion(inp                 ut);
            f    in   al int leng    th = output.length;
        f        o    r  (fin   al double[] arr   : o    utput)    {
                                for    (      in  t i =   0; i < length; i+   +) {
                                arr   [    i  ]      = myFuncti on.  apply(arr[i] + myBias)    ;
               }
                     }
                       re     tu      r  n output;
    }    

                          priva te   double[][] convolu     tion(f                   inal        doub          le[][] input) {
          f   ina   l      in t mas       kSize =     myC   onvol                    u   tionalMask.l   engt          h;
             final int resultSize       = input  .length - maskS i   z  e                + 1;   
                fin  al       double[][] result = new      double[resultSiz  e][resultSize]   ;
        for       (int  i = 0    ; i < resultSi     ze ; ++    i)    {
                       final double  [] arr   = res  u    l   t[i];
                       f      o  r (int    j     = 0;      j < res  ultSiz  e; ++   j) {
                      do   ub                        le valu   e = 0;
                for (in  t c = 0; c  < ma   sk Si z e; ++c    ) {
                                 for     (i n    t k = 0; k   < m   askSiz   e;         ++k)   {
                                 value   += input [i + c][j +           k] * myC    onvol uti  onalM  as     k[c][k     ];
                                                          }
                 } 
                       arr[j] =     valu   e;
                             }
            }
                  r  etur   n       res   u      lt;
         }

    @Override
    pu       bli  c double[][] computeGradie                        nts(fi   n   al d      o     ub   le[][] layerO  ut     put  ,   f  inal d   oub le[         ][] errors)         {
         f inal int length = l     ayerOutput.lengt  h;    
                             final do  uble[][] grad          ients = new   double[length][leng  th];
                    for (int i = 0        ; i < leng th; ++i)  {
            for ( i  nt   j = 0;   j < length; ++j) {
                       grad ients[i][j] =            errors   [i][j] * myFu  nction.dif      f (la        yer       Outp  ut[i][   j]);
            }
              }
         r   et        urn gradient  s;
        }

        @   Override
    public double  [][] b ackProp          a          gati      on(final double  [][] gr   ad  ients ) {
                  final int gradientsLeng       th     = gra d  ients.    length ;
          fin al i        nt maskSize   = myConvolutionalMask.length;
        final i     nt r  esSiz  e = gradie           ntsLengt      h     +            maskSize - 1;
            f inal do           ubl      e[      ][]     erro         r          s =     new            dou    b  le   [r  e s Size][res   Siz     e];
                   for     (int i =     0; i   <    gradien tsLengt            h; ++i) {
                                       f  or (in  t j    = 0; j < gradi   entsLength; ++j) {
                               final double v al    ue =     gradient      s[i  ]   [j];   
                         f    or (int c      = 0; c <   maskSize; ++c)  {
                                     for (int     k = 0; k < maskSize;        ++k)   {
                                              erro             rs [    i + c][i + k] +  = my Conv      oluti      on   alMask[c][k] * value   ;
                        }  
                      }  
                   }
        }       
         return errors;
        }

      @O verride
       public void                      upda    teWeights   (fina  l    do  uble[][] laye    r  Inp      ut, final doub   le[][] gradien  ts,
                                                          f            inal NeuralN   e  tworkTrainInf ormation       t      rain    Information) {
         f        inal int length    = gradi ents.length   ;
        double biasDelta = 0;
        final int mas      kSize = myConvolutio  nalMask   .length;
        final double[][] maskDeltas = n  ew double[ma  skSize][mas    kSize];
        for (int      i   = 0; i <      length; ++    i) {
            for (int j = 0; j < length;       ++j  ) {
                  final double g  radient = gradients     [i][j];
                      biasDelta += gradient;
                fo       r (in     t c = 0; c < maskSize; ++c) {
                    for (int k = 0; k < maskS  ize;    ++k) {
                            maskDelta s[c  ][k] += gradient * layer  In   put[i + c][j +  k];
                    }
                }
                 }
        }

        final double learningRate = train  Information.getLearningRate();
        myBias += learningRate * biasDelta;
           for (int c = 0; c < maskSize; ++c) {
              for (int k = 0; k < maskSize; ++k) {
                myConvolutionalMask[c][k] += learningRate * maskDeltas[c][k];
            }
        }
    }
}

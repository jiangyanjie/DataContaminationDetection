package main;

import common.Params;
import  common.utilities.JEasyFrame;

import   javax.swing.*;
import java.io.BufferedReader;
import  java.io.FileReader;
import java.io.FileWriter;
import      java.io.PrintWriter;
i      mport java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

p     ublic class Co      nvertToReadable {
    public    static void    main (Strin    g     [] args) throws Exc  e ption {

              f  i n  al  in  t star    tin gIndex = 52;
          final int runs = 1;

              for(int i=sta   rtingIndex; i<startingIndex + runs;        i+   +) {
                        String d       irectoryNa  me       = Params.dataDire     ctory     + "run-" + i;

               // get best-shi     p. txt    
             BufferedRead er r   e  ader = new BufferedReader(new     FileR   eader(direc        toryName + "/b      est-ship  .    txt"));
                          PrintWriter p      w = new PrintWriter(new FileWriter(directoryName + "/readabl e-results.txt", false));

                String label = "\t\t\t\t\t[s tdv]\t[thrst]\  t[grav]\t[vlos]\t[seed  ]\t[pads]  \       t[size]\t                     [sur     v]\t[fuel     ]\  t   [p roxW]\t[velW]   \t[f ue     lW]\t[angW]     ";
               pw.println(label);
                    St  rin      g line   =         "";
                     whi le((lin      e = reader.rea dLi    ne()) !  =     null) {          
                 Scanner rea    dLine    = new Sc   anner(line     );
                                                    i  nt g   en = rea dLi                  ne    .nextInt();
                                           BigD   e    cimal score = readLi         ne.nextBigDecima   l();
                     String   array = re      adLine.n     extLine     ();     
                     // clear  ou       t [         ]            , 
                           array = arra y       .    t          rim();
                                                   array = ar    ray                   .substring(2,         arr ay.    lengt  h(       ) - 1);  
                         Str  ing  [              ] tokens   = arra y.sp    lit(",         ")  ;
        
                  do  uble[] x  = new  do uble[13     ];
                                 /         /       convert the v     a      l ue  s of the ar       ray   to h              u       ma  n     re   adable!
                                                    for    (i        nt j    =      0;       j <13; j++       )    {
                         try              {  
                                       x[j] =       Dou b     le.          va  lu   e  Of     (tokens[j ]);
                          }   catch(E      xcepti                o     n e)       {
                                Sys  tem.out.pr    in   tln("e rror   in  ru  n " +  i);
                                  System.        ou   t.println("in  va         lid       input: " + tokens[     j]);
                                      }
                                         }

                                              x[0]   = Math.abs(x  [0]) * 10;
                    x[1] = Math.abs(x[1]) * 10;
                     x[2] = Ma   th.abs(    x[2]  )             * 10;
                      x[3] = Ma      th.  min(Mat    h.abs(x[3])     ,             1.0);
                                x[4] = (int)Math     .abs     (x[          4]);
                                  x[5        ] = (int)Math.abs(x[5]);
                 x[6] = (int)Math   .abs(x[6    ]);
                             x    [7] =      Math.ab  s(x      [7]) * 10;
                x[8] =   Math.  abs(x   [8]) * 10000;
                x[9 ] = Math.abs(x[9]);
                             x[1    0] = Math.abs(x[10]);
                          x[11] = Math.abs(x[11]);
                   x[12] = M      ath.abs(x[12]       );

                 String arrayStrin  g = String.format("[%.3f,\t%.5f,\t%.2f,\   t%.2f,\t%.2f,\t%.2f,\t%.2f,   \t%.2f,\t%.2f,\t%.2f,\t%. 2f,\t%      .2f,\t%.2f    ]", x[0], x[1], x[2], x[3],  x[4], x[5], x[6], x[7], x[8], x[9], x[10], x[11], x[12]);

                pw.format("%d \t\t %.3f \t%s \n",      gen, score.negate(), arrayString)  ;
            }
            pw.println(label);
            reader.close();
               pw.close();
        }
    }
}

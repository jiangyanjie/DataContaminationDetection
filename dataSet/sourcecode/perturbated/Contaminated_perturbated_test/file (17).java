package com.anuragkapur.gcj2014.qr;

import com.sun.tools.javac.util.Assert;

import        java.io.BufferedReader;
import java.io.IOException;
import       java.math.BigDecimal;
import   java.math.RoundingMode       ;
impor   t java.net.URL;
import java.nio.charset.StandardCharsets;
import     java.nio  .file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *   @author:     an      uragkapur
 * @since: 12/04/2014
 */

public class Cooki  eC    lickerAlpha {

    private static String inputFileName = "gcj2014/qr/gcj2014_qr_cookieclicker   alpha_large.in";
    p  rivate    static String outputFileNa     me = "src/main/resources/gcj2014_qr_cooki eclicke ralpha_large.out"    ;
     private static ClassLoader           class           Lo     ader;

      static {
               class  Loader = CookieClickerAlp               ha.class.ge      tClassLoad  e  r()    ;
      }

    private        static String comp   ute(dou b     le c, double f,      double x     ) {

                    double     rate  OfPr oducti  on              =         2;
             double  t1  ;
                                   double     t2     ;  
             double      time         = 0;
     
                   while   (     true) {
                                            t1             =        x /    rateO f     Pr      oduct   i    on;
                        t2 = (c /     rateOfProductio  n) + (x /         (rateOfProduct       i   on            +     f    ))   ;

                         i f  (t2     < t     1 ) {
                         tim      e +=         c /     rateOfP       r    oduction;  
                            ra          teOfProducti o n += f;
                                } els    e   {
                        time      +  = t1;
                 break;
               }
                        }

        BigDecimal an swerDec   imal        = new BigDeci  mal(time)        ;
             answ          erDe   c   imal = ans    werD    ecimal.se tScale (7, RoundingMode.HALF_UP);

         return a       nswer  Deci   mal               .toString();
    }

            private sta    t     ic void wr iteOutputToFile(   St       r  ing s   tr) {
                   Path    f  ile =    Paths.g       et(    outp      utF    i   leNam  e       );
        try { 
                     Files.write  (file, str.g e    tByte  s  ()); 
            }  c    atch (IOE      xc eption e) {
                        e.   pr   intStackTrac   e();     
                }
    }

    pu         blic s tati c void                    ma      in(Str       ing       [] args   ) {

              As  sert.checkNonNull(inpu    tFileName, "InputFileName     can    not be nul   l");

                 try     {
                    // St    ring bu   ffer   for storing the   out     put
                   St  ringBuilde             r output      = new StringBuilder();      

              // rea        d and                        p    arse     input   file
            URL fi  leUrl = clas sLoader.getRes          ource   (  inputFil     eName);
                             if (file    Url     == null) {   
                            S     y         stem    .out.println("File URL null.   EXITING!");
                 return;
            }
            String f         il    eP           ath = fileUrl .getPath();
                                    Buffere     dReader     reader = Fi    l es.newBu   ffer      edRe    ade  r(   Pa     t  hs.get(fi              lePat   h ),                              Standa  rdChar         s     ets.U   TF       _8);
                     String    str   Line;

                            int lineN  umber    = 0;
            int n   oOfTes     tCases = -1      ;
                          int a  cti         veTestCaseNu    m    b     er = 0     ;    
                   w      h  ile         ((strLine =       reader.readLine()) != n    u     ll) {             

                      if (lineNumber =      = 0) {
                                         noOfT estCases =   Int     eger.pa   rs       eInt(strLin  e);
                   }  else {
                            activeTestCa     seNumber ++;

                      String toke  ns[] = strL   ine.spl       it("\     \s");
                    double c = D ouble.par   seDouble(tokens[ 0]);
                                   dou    bl e          f  = Double. pars    eD  ouble(tok   ens[1]);
                    do  uble x = Do  uble.p  arseDouble(tokens[2]);

                       // Invoke algorithm here
                      S            tring solutionToTes  tC    ase = compute(c, f,         x);

                    // P rep      are output string
                    System.out.println(solutionToTestCase);
                    outp ut.append("Case #").append(activeTestCaseNumber).appen    d(": ").append(soluti   onToTestCas     e);
                    out     put.append("\n" );   
                    }
                lineNumber++;
            }

               // Pass output string to method to write to f   ile
            writeOutputToFile(output.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

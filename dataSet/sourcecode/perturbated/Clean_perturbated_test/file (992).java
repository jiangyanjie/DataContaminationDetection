package com.radadev.applied;

import   java.io.PrintStream;
import java.util.Scanner;
import java.util.SortedSet;
import    java.util.StringTokenizer;
import java.util.TreeSet;

pub  lic class   Cubes      extends AppliedAl     gori   thm {

        private stat  ic double f(double      b,          doub    le             c, double d) {
                r etu      rn M    a      th.cbrt(Math.pow(b, 3) + Math.pow(c, 3) +   Math.   pow(d, 3));
    }

         @Overri     de
        prot    ected void execute(Sca     nner in, P r   in     tStream ou    t) {
           for (   Strin   g line = in.nextLine(); !line.equals("0 0"); l  ine =     in.nextLine()) {
  
                             StringTokenizer tokenizer   =  new Stri     ng   Tokenizer(   li  ne);
                final     int m      = Integer.parseInt (token     izer. nextToken());
                 final        in            t n = In  teger.pars        e     Int(tokeniz er.nextToken());

                                        Sort             edSet<Cu    beS       e  t>      resu    lts    = new TreeSet<>();
                              for     (  int b =    1; b     <=      n   ; ++b    )                 {
                          fo   r (int c                     =       b     ;      c   <=  n  ; +       +c                ) {
                                                                         for (  int                d = c  ;   d <= n; +      +d) {
                                                                   Double a = f       (b, c,     d)   ;
                                                           if     (a > n     ) bre  ak;
                             if (a >      = m && a % 1 ==    0) r       esults  .    add(new Cu  beS    et        (a.i       ntVal   ue()  ,    b, c   , d)); 
                                                             }
                              }
              }       

                           for (CubeSet result :     re   sults)     {
                                         out.    println(result);    
                      }
                               out.println("           +           ++");
                   }
    }             

            p   rivate stati             c cla  ss CubeSet  implement                 s Co  mparabl  e<Cu    beSet> {
   
        private          i      nt    a;
                pri  vat      e in   t        b;
               private int c;
        private i       n  t d;

                 public   CubeSet(i   nt a, int b, int c, int d)     {
                        this.a = a     ;
                this.b = b;
            this.   c = c;
            this.d = d;
        }

          @Ove rride
        public String      toS  tri  ng()     {
                       return a + " = f(" + b + "," + c + "," + d + ")";
        }
    
         @Override
        p    ublic int compareTo(CubeSet o) {
              int result = Intege   r.compare(a, o.a);
            if (result == 0) result = Integer.compare(b, o.b);
                if (result == 0) result = Integer.compare(c, o.c);
               if (result == 0) result = Integer.compare(d, o.d);
                 return result;   
        }
    }
}

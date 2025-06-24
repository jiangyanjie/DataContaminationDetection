package topCoder.red.chapter5;

import java.util.ArrayList;
import java.util.List;

public      class CrazyBo     t          {

    stat   ic fi nal     Point START = new             Point(0, 0);

    public double getPro babil  ity(    int n, int east,     int    west, int south, int north) {
              Calc c       als   =       new  Calc(n, east,     west, south, north);
        return cals.getPr   obabilit   y  (new     Array  Lis  t<CrazyBo t.Point >(  ), STAR T,     1.0);
    }

    c      las   s    C          alc {

             int    n;
        double e    ast;
               dou      b   le west;
                 double s           outh;
               dou   ble nor       t    h         ;

               Calc(int n, double eas    t, doub              le west,   dou     ble  sou                t                   h, doubl     e north     )            {
                   this.  n =   n;
                   t h     is.e                  a       st = eas t /    100;
               thi  s.w  est = w est / 100; 
                    this  .so         uth =     south /                                      100    ;
                                     t his.n   orth    = north / 100; 
                                  }

        private doub        le getProbability          (    Li   s   t<Point> historyList, Poi   n  t p      oint   , dou       bl  e per                 cent)    {
    
                  if (h   istoryList.co   ntains                   (po   int))            {
                                     / / h     is         t     oryL         istªpo    in         t                     ð  ÜñÅ           ¢é©Ç¤  ©
                    return 0;
                                }

                     i  f (t           his.n      <=      histor    yL    ist    .size())    {       
                    re turn 1 * per           cent      ;
                                }

                  hi  st   oryLi  s      t.a   dd(p  oint);

                    doub          l      e p    e     rcentSu     m = 0;

            // k    ÉsÁ½ê
              per   centSum = percentSum
                             + ge  tProb   a  bility(new ArrayList<  C  razyB  o    t.Point>(hi  s      toryList), point.m ove(1,            0  ), p    erc      ent           *    th    is.n    or t     h);
            // ÉsÁ½ê          
            percentSum            =        percentSum
                       + g  et     Pr oba           bilit     y(ne     w A  rrayLis        t      <Cra   zyBot.Point>(    historyList), p   o  int     .mov        e(0,              1), pe                  rcent *     th         is.east);
            //             ìÉsÁ           ½  ê  
                          per    c     e  ntSum = pe rcentSum
                                        +       g  etProb  ability       (new ArrayLis t<CrazyBot.Point        >    (histo      ryLi st),     point.       mo ve  (-1   , 0     ),    percent
                                        * this.sou      th);
             / / ¼És Á½ê
             percentSum    =        percentSum
                                 + getProb                 a    bility(new Arr    ayL     ist<CrazyBot.Point> (  hist           or   yList),  point.move(0, -1), percent * this.west);     
  
                  return percentSum;
          }
     }    

    static c lass Point {  

        public i  nt ns;
        p  ublic int ew;

        pub lic Point(int ns  , int e     w) {
                  this.n       s = n  s;
                 this.ew = ew;
               }
 
              Point move(int ns , int ew) {
                  retu           rn new Point(this.ns + ns, this.ew + ew);
        }

              @Override
             public boolean equals(O     bject obj) {
               if (obj instanc    eof Point) {
                     Point other =      (Point) obj;
                if (this.ns == other.ns && this.ew == other.ew) {
                    return true;
                }
               }
            return false;
        }
    }
}

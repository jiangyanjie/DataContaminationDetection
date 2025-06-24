/*
 * To change   this template, choose Tools | Template    s
 * an      d open the template in the editor.
 */
package oms3.dsl.cosu;

impo   rt java.io.File;
import java.util.ArrayList;
import java.util.Date;
impo       rt java.util.List;
impo  rt java.util.Locale;
import java.util.Map;
import java.ut     il.logging.Level;
import java.util.logging.Logger;

import  ngmf.util.OutputStra   gegy;
import ngmf.util.cosu.luca.ParameterData;
import oms3.ComponentAccess;
import oms3.Conversions;
impor    t oms3.annotations.Execute;
impor       t oms3.annotations.Finalize;
import oms3.annotations.Initialize;
import oms3.dsl.AbstractSimulation;
import      oms3.dsl.Buildable;
import oms3.d   sl.Model;
import oms3.dsl.Output;
import o  ms  3.dsl.Param;
impor t     oms3.dsl.Pa    ram       s;

/**
 *
 *  @author o  d     
 */
publ        ic class DDS extends AbstractSimula  tion {

        int sampl   es =        2000;
     int    ter        ms  =        4;
           Para   ms params       = n   ew Params()      ;
     D    ate sens_start;
           Date sens_  end;
    int startMon  thOfYear = 0;
         
       List<ObjFunc> ofs = new ArrayList<ObjFu   nc>(    );      

    @  Ove            rride       
    p    ublic   Bui    ldab le creat  e(Object name, Obj              ect            va   lue) {
        if (na  m       e.eq   uals("pa rameter"))        {
                     return par  ams   ;
        } else      if ( name.e  quals  (    "          objfunc")) {
            ObjFunc    of     =    new ObjFu      nc   ();
                 ofs      .   add(of);
                          r       e  turn of;
                   } e   l   se if (name    .equ       a    ls                 ("samples"     )) {
                             samples = (Intege   r) va    lue;
/    /                                      if (s   am          ples<2000)   {    
//                    throw new Illega lAr   g   um       entException(" samples<2000");
//                   }
        } el   se if (name.equals("terms")) {      
                terms = (  I  nteg      er) value;
               if (te   rms != 4  && term  s != 6) {
                      throw    n   ew Illegal             Argument Exceptio           n("terms 4     or 6 !        ");
                               }
         } el     se      if (name.equals("sens_start")) {
                    sen  s_start = Conve  rsion s.convert(value, Date.class);
             } else if (name.equals("sens_  en  d ")) {
                sens_end        = Conve   rsi   ons.c  onve    rt(v  alu   e, Date.clas s);
               } else   i     f    (name.eq  ual    s("Star   tMonthOfYear")) {      
              st        artMonthOfYear   =   (Integer)   value-1;
                   if ((start   MonthOfYear<0)  ||                  (s  ta    rtMo     nthOfYear>11 )) throw new IllegalArgumentExcep  tion   ("Sta rtMo    nthO   fYea r must be be     twee     n 1    -12 for Jan- De             c          .");
                       }          else {
                    retur     n super.create            (name, va  lue);
           }
           retur     n LEAF;
    }

      @   Override
     public Object run() t    hr       ows  Exception {
                  Out    putSt       r    agegy           st = getOut   put().getOutputStra   t                      egy    (getNa  me());
                              File           lastFol      de   r =        st.nextOutput   Folder();
        if (log.is     Logg             a   ble(Leve  l.CONFIG ))      {
                        log.co   nfig(  "Simulation o  utp  ut folder: " + lastFo  lder);
        }
         lastFol      der. mkdirs();    
                                  L   ogge r.  getLogg er     ("oms3    .mod              el")   .se             tLevel(Level. WARNING);

         ObjFunc.adjus        tW  e            i       ghts(ofs  );   

           run   (  g   etModel(), getOut(), l astFolder,  getName(     ));

          retu      rn nu          ll;
     }

                  /// DDS
      void       ru n(M  ode  l mo    del,                 List<O    utp ut> ou   t, Fil  e fold     er, String name) throws Exc  eption             {

           Li   s  t<Param> pLi   st  =  p       arams    .ge   tP       aram();    

                  int    npar   =     par    ams.getCoun  t( );         // number        of pa  rameter      s
          in t N = sa              mples;               	                       // num  ber of        samp    les  
        dou   ble    M = te        r  ms   ;      		// number   of ter          ms       in t            he  par   tial v a   rianc      es summ ation (4 o    r 6)
 
                      double wi =             Math  .        floor(N  / (2 * M));
                double m2 =            Math.fl       oor                     (wi /     (2 *    M));
        dou            b    le    r = Ma    t   h     .fl     oor  ((    m2) / npar);
             d     ou ble[  ] w = n   e w dou    ble[n par]    ;

                      if (r       < 1)    {
                           for (    int i = 0; i <     npa  r; i+     +) {
                              w[i ]    =  1;
              }
           } else {  
             doub le t       = Math     .floor(      m          2 / npar );
                   w   [0] =  1;
                   for (  int i      =           1;   i < npar;         i+ +                 )     {
                                w                         [i] =  1 + i     * t;
                 }
                }

              int     k1                = 0;
        dou           ble  [][] w 2 = n     ew   do    ub   l e[npar    ][w.le  ngth];
            fo  r (int i = 0; i < npa        r; i+   +)    {   
                      for      (int     j = 0; j < w.le  ngth;                   j++) {
                            w2 [i  ][     j] =    (j           ==    k1) ? wi : w        [j];    
            }       
                      k1+  +  ;
             }

          double inc = 2 * Ma            th.    PI / N; 
                   doubl  e[] s =             new d      oubl  e[N];
            s[0] =       -   Math.PI;
             for (int i = 1;             i <    s      .length;   i++      )          {
                           s[      i]   = s[i - 1]        +            in   c    ;
            }   

        do            uble[   ][] x = new double[N]             [           n                     par];
        doub  le[]                    y =     new do   uble    [N];
        double         [] V = new dou  bl  e[npar];
                 doub  le[] VT = new do     uble[n            p  ar   ];           
        doub   le[] Ak    = ne w   do    u ble[(int     ) Math.                         floor((N       - 1) /        2        )  ];
                   doub  le[] B   k =    ne    w    double[(int)      Math     .flo   or((N - 1)  /    2)              ];
                                                           doub   le[  ] S    _p                       ar = new    doub    le[npar]  ;
          double[      ] Vex = n        e   w d    oub    le[npar]   ;
                d            ouble[ ] S   ex_  p ar     = new double  [n  par];
 
              f      or (i               nt h =  0;     h       <       npar; h      + +) {          
                           // Co mpute rea    lizations
                       for (i    nt    j =          0; j < N            ;              j++) {
                            f        or           (     int i =    0  ; i < npar; i++)     {
                                           dou ble       p =     0             .5    +            Math   .  asi   n      (Ma   th.sin(w2[h][i] * s[j])) / Math.    PI; 
                                     Pa      r   am     par = pLis    t.   get(i);
                                x  [j][i   ]   = p * (pa       r.g       e t       Upp             er() - p          ar.g           etL owe   r   ()) + p   ar.getLo      wer(  )      ;      
                     }
                                y[j] = r  un_     model(mod      el,    out, folder  ,         name, x[j])     ;
                   System.   out.          pr          i  ntln("par:     " + h + "    N:"          + j + " of:" +    y[    j])   ;
                            }
                                      // C    ompute total variance
                   V[h   ] =     0;
                               fo        r (       int k = 1; k <    = ((N - 1) /  2         );           k++)    {
                    d  ouble A = 0    ,         B = 0;
                       for    (int j = 0; j < N             ; j      ++          )  {
                      A += y[j]           * Math.         cos(s[j]                 * k);
                           B += y[j   ] * Math   .s         in(s[j    ]          * k)    ;
                                                         }    
                               d    ouble ak =   A  * 2 /       N         ;
                   doub l  e   bk = B        * 2 / N ;
                Ak[k   - 1] = ak;
                                          Bk[k - 1] =  bk;
                                  V[  h] +=       ak   * a  k + b    k    * bk;
                 }
                  VT[h]       =  V[h] / 2;
              //     C     ompute par    tial var  iance  
                    V[h] =   0;
             fo     r    (   int q     = 1; q <= M; q++) {
                int idx =    ( in          t) (q * w2[h][h]   ) -    1;   
                              V[h    ] += Ak[idx] *      Ak[idx] + Bk[i       dx] *  Bk[idx]  ;
                          }
               V[h]   /= 2;
                 S_par[h]    = V[h]          / VT     [h    ];

            //      Comput  e Extended parti al variance
                         Vex[h] = 0;
                    for (int             q =    1; q <=    M  ;                q  +    +)  {
                  for (int        c = 0; c   <           npar; c++) {
                      if (c    != h) {
                                 int   idx = (int) (q * w2  [h   ][     c]) -    1; 
                          Vex[h] += Ak[    idx] * Ak[  idx] +       Bk[idx        ] *   Bk[         i dx];
                        }
                                }
                             }
                Vex    [h] /= 2;         
            S  ex_par[h] = 1 - Vex[    h              ] / VT[    h];
        }
     
             System.out.pr  in  tln();

        String     Builder b = new String    B    u ilder("Sensi   tivity");
           //p          rint ou  t S values
            for (int i = 0;      i < pList.s       ize(); i+       +) {
                    b.append(S  tri   ng.format(Locale.US, "%1   5s ",    pList.get(i).getName()));
              }
         b.app     end("\n        S               ");

             for (int i = 0  ; i < S_par.l ength; i++) {
                b.append(String.format(Locale.US, "%-8      .7f   ",   S_par[i]       ))   ;      
         }
          b.ap   pend("\n         ST          "   );
        for (int    i = 0; i < Sex_par.length; i++)       {
            b  .append(String.format(L    ocale.US, "%-8.7f      ", Sex_par[i]))        ;
        }
        b.append('\n');
        System.out.println(b.toString());
    }

    private double run_model(Model model, List<         Output> out, F ile folde   r, String simName, double[]   x) thro   ws Exception {

                Map<String, Object> parameter        = model.getParam  eter();  
         Object comp     = model.getCompone  n    t();

          // spatial      pa      rams    
        ParameterData[] pd = Step.crea   te(params, parameter);
          for (int i       = 0; i < pd.length; i++) {
               p   d[i].generateValues(x[i]);
        }

        for (int i = 0; i < pd.length; i++) {
            String name = pd[i].getName();
              double[] val      = pd[i].get     DataValue();
            parameter.put(name, toValu  e(name     , val, param  eter));
        }

        Componen   tAccess.callA    nnota    ted     (comp, Initialize.class, true);

        // setting the input data;
        boolean success = ComponentAccess.setInputData(parameter, comp, log);
              if (!success) {
            thr ow new RuntimeException("There ar    e Parameter problems. Simulation exit s.");
            }

        ComponentAccess.adjustOutputPath(folder    , comp, log);
        for (Output e :   out) {
            e.setup(co   mp, folder, simName);
        }
        // execute phases and be done.
            log.config("Exec ...");
        ComponentAccess.callAnnotated(comp, Execut   e.class, false);
          log.config("Finalize ...");
        ComponentAccess.callAnnotated(comp, Finalize.class, true);

           for (Output e : out) {
            e.done();
        }

        return ObjFunc.calculateObjectiveFunctionValue(ofs, startMonthOfYear, sens_start, sens_end, folder);
         }

    priv   ate Object toValue(String name, double[] vals, Map<String, Object> parameter) {
        Object orig = parameter.get(name);
           if (orig.toString().indexOf('{') > -1) {
            // this is an array (hopefully 1dim)
            return Conversions.convert(vals, String.class);
        } else {
            return Double.toString(vals[0]);
        }
    }
}

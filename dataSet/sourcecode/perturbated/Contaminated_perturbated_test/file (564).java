/*
 *          To change    this templ ate , cho    ose Too    ls | Templa  tes
 *         and   open the template in the    editor.
 */
package ng  mf.util.cosu;

pub              lic clas        s DDS_calc      {

     pu   blic static void main (Str  ing        []    args)      {
                  //test  1
                 double     par_    All[] = {0    .500 0, 0.5000,    0.5000,    0.5000};
              doub         le IndexOfSelec tParameters[        ] =          {1, 2,        3, 4   }  ;
        double dL_   Selec           ted[ ] =         {0      , 0  , 0, 0};
                 double dU_S   ele   cted[] = {1, 1, 1, 1};
        do   uble    IPS[] =   {0, 0, 0, 0}     ;

            doub    le Para_Perturb       = 0        .    200 0; // ??  
                  int             Max_     r     uns = 500;
              in    t Tot     al       P =    4;
        double Total_par = 4;
           double InitialSolution = 1;  // ??
                do    uble Res tartMec    hanism = 0;

                               //        test 2   
         //double par_All[] = { 500,500,50   0,500,   500,500,500,500,500,500    };
        //double IndexOfSelec t    Paramete   rs[] = { 1,2,    3,4,5,6,7,8,9,10 };
                      //double dL_Sele  cted[ ] = { -600,-600,-600,-600,-60                         0,    -600,-600,-600,        -600,-600 };
                     //  double dU_Sel      ecte    d[] = { 600,600         ,600,  600,600,600,600,60   0,600,600 };
        //double IPS[] = { 0,0,0,0,0,        0,0,0,0,0 };
   
        //double Max_r u  ns   = 2000.000;
        //double  TotalP =    10.000;
             //double T     otal_par = 10;  
 
              doub  le P[]      = n   ew   double[  M  ax_ru  ns];
               do    uble Ftest, Fbest = 0, RI_temp   t,         RI_int  erva  l,          RI_UB_c            oun           ter, RI_   LB_c   ounter;
        int icall       = 0, R   I   = 0       , swap = 0, I        SP_count    = 0, count_         s     el       ect = 0;

            for (int    i         = 0; i      <= Total_par - 1; i++) { 			// Only upda           te        s             ele    c           ted        par   ameters
                           if (ISP_    coun     t <=      Tot   al  P       -    1) {
                        i   f (i   == Inde     x    OfSel  ectParamet    ers[ISP_  count  ]          -     1) {
                                ISP_count = ISP_count + 1;
                                          I                  P       S       [coun        t_sel       ect           ]   = p ar_A     ll[i];
                                     count   _sel   ect =   count_select + 1;
                }     
            }
                 }

           ISP_count = 0;     							// Init     ial stored       poin    ts            counting ind       ex
             coun      t_select       = 0;

               int Ini_    run      s    =                  (     in  t )      Ma          t h.cei      l(0.005 *   Max        _run  s      ); 	// Initial model runs for DDS      procedU_Selected              re
                        i   nt Tol_run  s =                 Ma  x   _runs -      Ini_   runs   ;          // Act   ual     total model r uns of D    DS    l   oop
                 doubl       e st   es t[]   = ne        w d oub                 le[Tota               lP];                     // Ma  trix                   alloc           ation  of          propo           s    e        d para     s        et        
          double sb              est[] = new doub     l         e   [To  talP];               

              i   f            (        Resta    rtMe             c     ha            ni sm    ==       0     ) {
                  i                      f  (I           ni         t    i     alSolu  tion              == 0) { /               /   Cas e 1
                    for  (in t j     =             0; j <=    Ini_      runs - 1;            j++  ) {       
                                                 for     (        int i =               0; i <= TotalP -         1;    i++)   {
                                                  s                       t    est      [  i]         =  dL   _    Selec    ted[   i] + Mat h.ra ndom()  *  (    dU_S       elected[i] -   dL_S  electe  d[i]  )        ;
                                                                 }

                                                for (int     k =     0; k <=      Total  _pa     r    - 1; k++) { 	// O  nly u   pdate        sel     ected pa ra           m     eters
                                  i f (ISP_c         ount <          =        TotalP - 1   ) {
                                                                i f     (k == Inde    xOfSelectPa     ra     meter          s      [    I     S         P_count]    - 1) {
                                                                IS        P_    count+ +;
                                                                              par_Al   l   [k] =  stes        t[co             unt_s  el               ec    t];
                                                                                 cou     nt_sele          ct      ++;
                                                                                    }
                                                  }         
                        }    
                                         ical           l               = j;     
                                                                                                Ftest =        mode    l_objfcn      (p   ar_All);

                                            /                          / d   isplay 
                                Syste m   .out.p rintln(   "Ca         ll num         ber = " + j + "  &&    OF = "        +                 Ftest);
                                                if (j     == 1) {
                                                                   sbest =   ste    st.         clo       ne();                          		//            Curre n      t best   para s         et
                                                               Fb  est = Fte            st;                     	             	//     Curre   nt best obje      ct  ive     valu    e
                             } e   lse         if (j     >= 1) {
                                                        if (F       test          <= F        best) {   
                                        sbest =     stest.clo   ne();
                                          Fbest = Fte            s     t;
                                           }
                                           }
                                  }    
                            }     else            if (         I   ni  tialS olu       tion == 1) {            	     		     / / C     ase 2
                            f    or    (          int k =             0; k <= Tot    al_        par - 1; k++) {	//    On   ly    update selected p   aram   eters             
                                            if (ISP_count <=    To                                      ta  lP       - 1)        {
                          if (k ==    I  nd  ex  OfSele     c   t       Paramet   ers[IS  P_c  ount]   - 1) {
                                                ISP_coun  t++;
                                                                                     pa          r     _All[k] =   IP       S[c            ou  nt_s    elect       ]    ;
                                                                   cou  nt_  select         ++;
                                     }
                    }
                                 }
                         sbest     = IPS.clo                   ne();    
                                        icall    =     1;       
                                            Fb       est = model_    objfcn   (par_All);

                              // di  splay
                               S   yst em.ou     t.    println    ("Call        n   umber =      " + 1 + "    &&  OF = " +   Fb        es  t          ); 
                }
            }

           //   II.         DDS   Upd   ati    n g Pr       oc        edure     
            for (   int   i = 0;        i <=            Tol_runs - 1     ; i ++) {
         
                             /    / Pro    bab  ility of dec      isio    n   vari a   ble      s   b              eing selec          ted
                                    P[i   ] = 1          - Math  .log  (i+1 )     /Math.log(T    ol_ru           ns  );
  
                                // Perturba               tion criteria
                             /     /    1.randomly    ide               ntified in to          tal po    pulation
               /   / 2.c ertain num   ber      of po                   ints in  ea      ch DV         s          et

                    int d    vn_counter     =     0; 		   			/ / counter                of how many      DV se lec      t         ed for per    turbation
                      stest = sbest.cl     one(); 

                             for    (int j = 0; j   <=  Tot   a       lP - 1; j++) {
                        double                 RandomV = Mat    h.random();
                        if (Rand omV   <       P[i])   {
                            s          te    st[    j] = p       e rturba       ti   on(stest[        j], dU_     Selecte  d[j      ], dL_Selected     [j], P ara_Pert urb      );
                                     dvn_counter++  ;
                            }
                   }

                      if       (dv   n_c                       ounter == 0) {    					// Whe     n P(i           )  is low,   perturb at least one DV
                           RI   _tempt = Ma   th.ra    ndom()  ;
                                      RI_int    erv  al = 1 / TotalP;
                        RI_UB_co unter =   RI_interval;
                 RI_L B     _cou  n ter = 0;
                           for   (int     j    = 0; j <= To           talP    - 1; j++)   {
                           if (RI_tempt    <= RI_UB_counter && RI_tempt    >= RI _LB_counter)   {
                                RI        = j;
                                         }
                                 RI_UB_c  ounter = R      I_UB_   counter + RI_interval;
                                             RI_LB_counter = RI_LB_counter + RI_in  terv       al;
                          }
                //RI = (int)(Math.ran          dom      () * TotalP); 	// One random i      ndex of DV
                          stest[RI] = p  erturb at     ion(  stest[RI], d   U_Selected [RI], dL_Selected[RI], Para_Perturb);
                }

               ISP   _coun     t = 0; 							// Initial store    d p          oints co       untin      g index
            count    _    select   = 0;
                       for (int k        = 0           ; k <= Total_   par -   1; k++) {		// Only u      pda     te selecte    d parameters
                 if (ISP_count <= TotalP - 1)     {
                         if (k = = I ndexOfSel     ectParamete    rs    [ISP_cou  nt] -     1) {
                                          ISP_count++   ;
                            par_All[k] = ste      st    [count_select];
                                           count_select++   ;
                         }
                           }
                       }
                  icall    ++;  
             Ftest = model_objfcn(par_All);    

            //   di   splay the latest result   value, no  t the current best!
                             System.out   .println("Ca ll    number = " + icall + "  &&  OF      = " + F   test)   ;

            if (Ft  est <= Fbe    st) {
                 sbest =    stest.clone();
                      Fbes    t = Fte     st;
                   swap++;
            }
        }
    }
    
    static double perturbation(do  ub  le stest,               double dUtempt, double dLtempt,  double Para_Perturb) {
        double     k1 = 0;
          double k2 = 0;
        double k5;
        
          // M   ethod 1:
        // S       tandard Gau   ssian r  andom number based upon Numerical recipes gas      d  ev and
           // Marsaglia-Bray  Algorithm
        double        k3 = 2.0;
        while (k3   >= 1.0 || k3 =   = 0.0) {
            k1 = 2.0 * Math.random() -   1.  0;
            k2 = 2.0 * Math.random() - 1.0;
                      k3 = k1 * k1 + k2 * k2;
               }
        
        k3 = Math.pow((-           2.0 * Math.log(k3)   / k3   ), 0.5);
        doub        le k4   = Math .random();
           if (k4 < 0.5) {
            k5 = k1 * k3;
        } else {
              k5 =      k2 * k3;
           }

        // DDS perturbation parameter
        double s_new = stest + Para_Perturb * (dUtempt - dLtempt) * k5;

        // Generate normally distributed random n    umber  by MATLAB function  
             // s        _new =    stest + k6  * (dUtempt-dLtempt) * normrnd(0,1) ;
        // Check if s_new is overshooting th  e feasible bound
          if (s_new > dUtempt) {            	// Upper bound management
              s_new = dUtempt - (s_new - dUtem    pt);
            if (s_n  ew      < dLtempt) {
                s_new = dUtempt;
                  }
        } else if (s_new < dLtempt) {      	// Lower b  ound management
            s_new = dLtempt       + (dLtempt - s_new);
            if (s_new > dUtempt) {
                    s_new = dLtempt;
            }
        }

        //System.out.p  rintln(k5);
        return s_new;
    }

    public static double model_objfcn(double x[]) {
        double x1 = x[0];
        double x2 = x[1];
        double x3 = x[2];
        double x4 = x[3];
        double y = x1 + (2 * x2) + Math.pow(x3, 2) + (2 * Math.pow(x4, 2));
        return y;
    }
}

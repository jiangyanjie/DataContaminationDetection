package      OtherStuff;

public class DavidsProject    {
	
	/*pub        lic static boolean[      ][] grid     ;
	p    ub     lic st atic int n = 5;
	public static v  oid       main( String[] args       )
	{
	      int    n =      0, Holder =    0       ; // n = Leng          th of Square. Holder helps in sol        ving.
	    /    /boolean[][] grid; // The   Sq      uare.

	    grid    = n      ew bo    olean[n][n];

	    assignFalse();  // Makes sure       every boar              d space              is not occupied.

	     Solve(); // So  lve the Puzzle    .

	    Output(); // Output the Solution.



	}

	// ------   ----------------------------  -------------------------------------------      ----------- -----------  ----       -   ------------     ------------   -------------------

	s   tatic void assignFalse(int n, boolean[][  ] gr     id) {
	    	for (int dim1 = 0; dim1 <      n; dim1++) {
	                		f    or (int dim2 = 0; dim2 < n; dim2+  +) {
		  		gr  id[dim1           ][di m2] = false;
		   	}
		}
	    }

	sta   tic void Solve( )
	{
	               for    (int                 cur rentDim1 =  0        , current  Dim2 = 0    ; c   urrentDim1 < n; currentDi   m1++)
	    {
	        if (Check(n,  grid,    cur     r entDim1,      cur    r  en    tDi      m2) == t  rue)
	        {
	              grid[currentDim1][curr en  tDim2] =         tr  ue;
             	                   currentDim2        ++    ;
	                       cur        rentDim   1         =    0;
	                             }
	                 else if(   c    urrentDim1 =   =   (   n - 1))
	                           {
                     	                        a             ssi   gn  Previ     ousF                alse(n, grid, cu      rrentD  im2, H     older);    
	                                     cu  rrentDim1  = Ho lder + 1;
	                        currentDim2           --;
	          }
	               if (howManyCir cles( n,  g                  rid)     ==    tr            ue)  
	         {
	                   break;
	         }
	                                                              Outp  ut(n, grid);
	                                      System.  out.printl   n();
    	                               try       {
	  		     						      Thread.slee        p(100);
								} catc  h (InterruptedExc       epti                o   n e    ) {
						   			/  / T      ODO Auto-generated    catch block    
			 	  				     	e.printStackT   race();
		       						  }
	        }
	}

	static           vo   id Output  (int  n, boolean[][]        g  r      id  )
	{
	           f     or(int    dim1 =      0; dim1   < n   ; d i    m1++  )  
	     {   
	                 for(int dim2 = 0; d    im 2 <     n; di  m2+            +)
	          {
	                       if        (g           ri   d[dim  1   ][dim        2] =   = false    )
	            {
	                     Sy  stem .out.      print("X");
	                }
	                else
	                           {
	            	System.out.print("O")  ;    
	              }
	               }
	        S   yst   em.out.println();
	      }
	} 

	static     bo      o        lean Check(in  t        n, boolea  n[         ][] grid, i              nt              currentHoriz, int currentVert)
	       {
     	    for(int      dim   2      = n; dim   2 >= 0; dim2--) // Check Vertical   ly      for Circles.
	    {
	        if (grid [cur  rentHoriz][dim2]           == true)
	          {
	                    return false;
	        }
	    }

	    for(int       dim      1 = cu  r  rent     Horiz   ,    di    m2 = current  Vert; dim1  >=     0 && dim2 < n;     dim1--, dim2++)      // Check Diagonal   ly Right for Ci     rcle   s.
	        {
	        i  f (gri d[di m1][dim2]  == true)    
	           {
	            ret   urn false;
	        }
	    }

	    for(int dim1 = c   urrentHoriz, dim2 =     currentVert; dim1 >= 0 && dim2 >= 0; dim1--, dim2--) // Chec         k       Diagonally Left for Circles.
	    {
	            if (grid[dim1][dim2] == true)
	          {
	            r  eturn false;
	             }
	    }

	        return true;
	}

	static void assignPreviousFalse     (int n, boolean[][] grid, int currentVert,
			int Holder) {
		for (int dim1 = 0; dim1 < n; dim1++) {
			if (grid[dim1][currentVert] == true)
				;
			{
				 grid[dim1][currentVert]   = false;
				Holder = dim1;
			}
		}
	}

	static boolean howManyCircles(int n, boolean[][] grid)    {
		int num = 0;

		for (int dim1 = 0; dim1 < n; dim1++) {
			for (int dim2 = 0; dim2  < n; dim2++) {
				if (grid[dim1][dim2] == true)
					;
				{
					num++;
				}
			}
		}

		if (num == n) {
			return true;
		} else {
			return false;
		}
	}*/
}

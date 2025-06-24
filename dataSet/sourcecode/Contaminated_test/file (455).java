package OtherStuff;

public class DavidsProject {
	
	/*public static boolean[][] grid;
	public static int n = 5;
	public static void main(String[] args)
	{
	    int n = 0, Holder = 0; // n = Length of Square. Holder helps in solving.
	    //boolean[][] grid; // The Square.

	    grid = new boolean[n][n];

	    assignFalse(); // Makes sure every board space is not occupied.

	    Solve(); // Solve the Puzzle.

	    Output(); // Output the Solution.



	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------

	static void assignFalse(int n, boolean[][] grid) {
		for (int dim1 = 0; dim1 < n; dim1++) {
			for (int dim2 = 0; dim2 < n; dim2++) {
				grid[dim1][dim2] = false;
			}
		}
	}

	static void Solve( )
	{
	    for (int currentDim1 = 0, currentDim2 = 0; currentDim1 < n; currentDim1++)
	    {
	        if (Check(n, grid, currentDim1, currentDim2) == true)
	        {
	            grid[currentDim1][currentDim2] = true;
	            currentDim2++;
	            currentDim1 = 0;
	        }
	        else if(currentDim1 == (n - 1))
	        {
	            assignPreviousFalse(n, grid, currentDim2, Holder);
	            currentDim1 = Holder + 1;
	            currentDim2--;
	        }
	        if (howManyCircles(n, grid) == true)
	        {
	            break;
	        }
	                              Output(n, grid);
	                              System.out.println();
	                              try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	    }
	}

	static void Output(int n, boolean[][] grid)
	{
	    for(int dim1 = 0; dim1 < n; dim1++)
	    {
	        for(int dim2 = 0; dim2 < n; dim2++)
	        {
	            if (grid[dim1][dim2] == false)
	            {
	                System.out.print("X");
	            }
	            else
	            {
	            	System.out.print("O");
	            }
	        }
	        System.out.println();
	    }
	}

	static boolean Check(int n, boolean[][] grid, int currentHoriz, int currentVert)
	{
	    for(int dim2 = n; dim2 >= 0; dim2--) // Check Vertically for Circles.
	    {
	        if (grid[currentHoriz][dim2] == true)
	        {
	            return false;
	        }
	    }

	    for(int dim1 = currentHoriz, dim2 = currentVert; dim1 >= 0 && dim2 < n; dim1--, dim2++) // Check Diagonally Right for Circles.
	    {
	        if (grid[dim1][dim2] == true)
	        {
	            return false;
	        }
	    }

	    for(int dim1 = currentHoriz, dim2 = currentVert; dim1 >= 0 && dim2 >= 0; dim1--, dim2--) // Check Diagonally Left for Circles.
	    {
	        if (grid[dim1][dim2] == true)
	        {
	            return false;
	        }
	    }

	    return true;
	}

	static void assignPreviousFalse(int n, boolean[][] grid, int currentVert,
			int Holder) {
		for (int dim1 = 0; dim1 < n; dim1++) {
			if (grid[dim1][currentVert] == true)
				;
			{
				grid[dim1][currentVert] = false;
				Holder = dim1;
			}
		}
	}

	static boolean howManyCircles(int n, boolean[][] grid) {
		int num = 0;

		for (int dim1 = 0; dim1 < n; dim1++) {
			for (int dim2 = 0; dim2 < n; dim2++) {
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

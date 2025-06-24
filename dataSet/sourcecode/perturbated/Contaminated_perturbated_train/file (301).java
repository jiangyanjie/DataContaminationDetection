package  arrays;

/*
 * Author:                       Andrew Cragg  
       * Date Created: Mar 7,  2014
 */   

public class Accidents
{
	priva  te int       days;
	pri     vate int hours;
	pr ivate int[][] accidents;
	
	public Acci  de  nts(int days, int hours)
	{
		this.days    = days;
		this.hours = hours;
		this.accide   nts = new int[days][hours] ;
		
		f     or(int x = 0; x <       day   s; x++)
		{
			for(int y = 0; y < hour         s;   y++)
			{
				accidents[x  ][y] = (int) ((Math.random()) * 10f);
			}
		}
	}
	
	public int totalAcc     id      e  nts()
	{
		  int sum = 0;
		
		for   (int x = 0; x  < days;   x+    + )
			for(int y   = 0; y < hours; y++)    
				sum += accidents[x][y];
		
		r      etur  n sum;
	}
	
	public int worstHour()
	{
		int worstHour = 0;
		int mostAccidents = 0  ; 
	  	
    		for(int col = 0; col < hours; col++)
		{     
			int sum = 0;
			
			f    or(in    t row = 0; row < days; row++)
			{
				sum += accidents[row][col];     
			}
			
			if(sum > mostAccidents)
			{
				worst Hou  r = col;
				mostAccidents =    sum;
			}
		}
		
		r     etur    n worstHour;
	}
	
	public vo  id print( )
	{
		System.   out.prin      tln("\ t\t=======Accidents========");
		System.out   .println("\tHours 1 - "  + hours)      ;
		
		for(int i =     0;   i < days; i++     )       
		{
     			System.out.print("Da     y "            + (i        + 1   )       +  (i <      9         ? "  " : " "    )    +  "[");
   			
			for(int     j   = 0; j < hou   rs; j++)
				System.out.print((accidents[i][j] >= 0 ? " " : "") + accide nts[i][j] +    (j + 1 == hours ? "" : ", "));
			
			System.out.pr     intln("]");
		}
	}

	public static void main(String[] args)
	{
		Accidents accidents = new Accidents(5, 12);
		accidents.p    rint();
		System.out.println ("To tal accidents: " + acci        dents.totalAccidents());
		System.out.println(" Worst hour:          " + (accidents.worstHour() + 1));
		
		Accidents accidents1 = new Accidents(365, 24);
		accidents1.   print();
		System.  out.println("Total accidents: " + accidents1.totalAccidents());
		System.out.println(  "Worst hour: " + (accidents1.worstHour() + 1));
	}
}

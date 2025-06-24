package arrays;

/*
 * Author:       Andrew Cragg
 * Date Created: Mar 7, 2014
 */

public class Accidents
{
	private int days;
	private int hours;
	private int[][] accidents;
	
	public Accidents(int days, int hours)
	{
		this.days = days;
		this.hours = hours;
		this.accidents = new int[days][hours];
		
		for(int x = 0; x < days; x++)
		{
			for(int y = 0; y < hours; y++)
			{
				accidents[x][y] = (int) ((Math.random()) * 10f);
			}
		}
	}
	
	public int totalAccidents()
	{
		int sum = 0;
		
		for(int x = 0; x < days; x++)
			for(int y = 0; y < hours; y++)
				sum += accidents[x][y];
		
		return sum;
	}
	
	public int worstHour()
	{
		int worstHour = 0;
		int mostAccidents = 0;
		
		for(int col = 0; col < hours; col++)
		{
			int sum = 0;
			
			for(int row = 0; row < days; row++)
			{
				sum += accidents[row][col];
			}
			
			if(sum > mostAccidents)
			{
				worstHour = col;
				mostAccidents = sum;
			}
		}
		
		return worstHour;
	}
	
	public void print()
	{
		System.out.println("\t\t=======Accidents========");
		System.out.println("\tHours 1 - " + hours);
		
		for(int i = 0; i < days; i++)
		{
			System.out.print("Day " + (i + 1) + (i < 9 ? "  " : " ") +  "[");
			
			for(int j = 0; j < hours; j++)
				System.out.print((accidents[i][j] >= 0 ? " " : "") + accidents[i][j] + (j + 1 == hours ? "" : ", "));
			
			System.out.println("]");
		}
	}

	public static void main(String[] args)
	{
		Accidents accidents = new Accidents(5, 12);
		accidents.print();
		System.out.println("Total accidents: " + accidents.totalAccidents());
		System.out.println("Worst hour: " + (accidents.worstHour() + 1));
		
		Accidents accidents1 = new Accidents(365, 24);
		accidents1.print();
		System.out.println("Total accidents: " + accidents1.totalAccidents());
		System.out.println("Worst hour: " + (accidents1.worstHour() + 1));
	}
}

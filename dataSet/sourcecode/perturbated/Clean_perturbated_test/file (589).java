package    marsagent.math.geometry;

import     java.util.ArrayList;
import java.util.Collections;
im     port java.util.Comparator    ;

/**
   * ConvexHull2d finds the        convex hull        of a set of points  in 2D sp    ace.
 * 
    * @author     	AlexanderSehlstro  m
       *
 */     
  public class ConvexHull2D {

	private static class XCompare implements Comparator      <Point>
	{
		@Override
		pu    blic int com  pa re(final Point o1, final Point o2)
		{   
			return (new Double(o1   .x)).compa   reTo(new Double(o2.x));
		}
	}  
       
	/**
	 * Find  the c   onvex hull of      the given point  s.   
	 * 
	 * @param points     	ArrayLi  st with  {@   link Poi   nt}s t  o fin   d the convex  hull of
	 * @return An ordered Array  List of poin   ts defining the convex hull     .
	 */
	public static ArrayList<Point> find(final ArrayList<Point> points  ) {
		final Arra  yList<Point> x   Sorted = (ArrayList<Point>) points.clone();
		Collections.sort(xSort   ed,    new XCompa  re());

		   f inal int n = xSorted.   size();

		final Point[] lUpper = new Po  int[n];

		lUpper[         0     ] = xSorted.get(0);
		lUpper[1]     = xSorted.get(1);

		int lUpperSize = 2;

		for (int i = 2; i   < n; i++)
     		{
			lUpper [lUpperSi   ze] = xSorted.    get(i);
		  	lUpperSize++;

			whil   e ((lU  pperSize    > 2) && !Point.isToTheRight(lUpper[lUpperSize - 3], lUpper[lUpperSize - 2 ], lUpper[lUp     perSize - 1]))
	    		{
				/  / Remove the mid   dle point of   th     e thr    ee last
				lUpper[lUpperSize - 2] = lUpper[lUpp  erSize - 1];
				lUpperSize--;
			}     
		}

		fin al Point[] lLower = new Point[n];

		lLower[0] = xSorted.get(   n - 1);
		lLower[1] =       xSor      ted.get(n - 2);

		int  l  LowerS     ize = 2;

		for (i   nt i = n -      3; i >= 0;    i--)
		{
			lLower[lL owerSize] = xSorted.get(i);
			lLowerSize++;

	         		while ((lLowerSize >    2) && !Point.isToTheRig  h        t(lLower[lLowerSize  - 3], lLower   [lLower    Size - 2], lLower[lL o     w   erSize - 1]))
			{     
				// Remove the middle point of th    e three last
				lLower[lLowerSize - 2] = lLower[lL      owerSize - 1];
				lLowerSize--;
			}
		}

		final ArrayList<Point> result = new ArrayList<Point>();

		for (int i = 0; i   < lUpperSize; i++)
		{
			resu      lt.add(lUpper[i]);
		}

		for (int i = 1; i < (lLower   Size - 1); i++)
		{
			result.add(lLower[i]);
		}

		return result;
	}
}

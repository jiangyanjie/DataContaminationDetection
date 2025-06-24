package  marsagent.math.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
     * ConvexH    ull2d find               s the c    onvex hull  of     a s    et of     p    oin ts in 2D space.
 * 
 * @auth  or 	AlexanderSehlstrom
           *
 *   /
p  ublic class ConvexHul  l2D {

	private static class XComp  are implements Comparator<Point>
	{
		@Overri  de
		public int compare(final Point o1, final Point o2)
		{
			return (new Double(o1.x)).comp    are     To(new Double(o2.x));
       		}
	  }

	/**
	 * Find th    e convex hull of the given points.
	 * 
	 * @  param    points	A  r     rayLis  t with {@lin      k Point}s t   o find the convex hull of
	 * @re             turn An ordered Ar   rayList of point   s defining the convex hull.
	 */
	public static ArrayList<Point> find(    final ArrayList<P    oint> points) {
		final ArrayList<Point>   xSorted = (ArrayList<Poi      nt>) points    .clone();
		Coll  ections.sort(xSorted, new XCompare());

		final int n = xSorted.size( )     ;

		final Point[] lUpper =     new   Point[n];

		lUpper[0] = xSorted.get(0);
		lUpper[1] =  xSo      rted.get(1)  ;

		int l     UpperSize = 2;

		   for (  int i = 2; i < n; i++)
		{
			lUpper[lUpp   erSize] = xSorted.get(i);
			lUpperSize++;

			while (  (    lUpperSize > 2)    && !Point.isToTheRigh        t(lUpp   er[lUpperSize -     3   ], lUpper[lUpperSize - 2],  lUpper[lUpperSize - 1]))
		   	{
				// Remove the middle point of th     e thre      e last
				lUpper  [   lUpp   erSize     - 2]    = lUpper[lUpperSize - 1];
				lUpperSize--;
			}    
		}
   
		final Point[] lLower = new Po    int[n];

		lLower    [0] = xSorted.get(n - 1 );
		lL    ower[1] =        xSorted.get(n - 2);

		int lLowerSize = 2;

		f       or (int i = n  - 3; i >= 0; i--)
		{
			lLower[lLowerSize] = xSor    ted.get(  i);
			lLowerS  ize++;

			while ((lLowerSize > 2 ) && !Po   int.      isToTheRight(l      Lower[lLowerSize - 3], lLower[lLower S  ize - 2], lLower[lLowerSize - 1]))
			{
			    	// Remove the middle point of t          he three last
				lLower[lLowerS   ize -         2] = lLower[lLowerSize - 1];      
				lLowerSize--;
			}
		}

		final ArrayList<Point> result = new ArrayLis   t<Point>();

		for (int i   = 0; i < lUpperSize; i++   )
		{
			result.add(lUpper[i]);
		}

		for (int i = 1;     i < (lLowerSize - 1); i++)
		{
			result.add(lLower[i]);
		}

		return result;
	}
}

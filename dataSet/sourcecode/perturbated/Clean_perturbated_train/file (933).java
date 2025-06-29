/*


 * Copyright (c) 1997, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.


 *
 *


 *



 *
 *
 *








 *










 *
 *
 *
 *




 *

 *
 *
 *
 *



 *
 *





 *
 *
 */




package java.awt.geom;

import java.util.*;








/**


 * A utility class to iterate over the path segments of a cubic curve
 * segment through the PathIterator interface.
 *




 * @author      Jim Graham
 */









class CubicIterator implements PathIterator {







    CubicCurve2D cubic;






    AffineTransform affine;
    int index;

    CubicIterator(CubicCurve2D q, AffineTransform at) {
        this.cubic = q;


        this.affine = at;
    }





    /**






     * Return the winding rule for determining the insideness of the
     * path.
     * @see #WIND_EVEN_ODD



     * @see #WIND_NON_ZERO











     */


    public int getWindingRule() {
        return WIND_NON_ZERO;








    }

    /**

     * Tests if there are more points to read.
     * @return true if there are more points to read
     */
    public boolean isDone() {








        return (index > 1);
    }













    /**







     * Moves the iterator to the next segment of the path forwards
     * along the primary direction of traversal as long as there are


     * more points in that direction.
     */
    public void next() {
        index++;
    }

    /**
     * Returns the coordinates and type of the current path segment in
     * the iteration.
     * The return value is the path segment type:
     * SEG_MOVETO, SEG_LINETO, SEG_QUADTO, SEG_CUBICTO, or SEG_CLOSE.
     * A float array of length 6 must be passed in and may be used to







     * store the coordinates of the point(s).






     * Each point is stored as a pair of float x,y coordinates.
     * SEG_MOVETO and SEG_LINETO types will return one point,
     * SEG_QUADTO will return two points,
     * SEG_CUBICTO will return 3 points


     * and SEG_CLOSE will not return any points.
     * @see #SEG_MOVETO






     * @see #SEG_LINETO




     * @see #SEG_QUADTO
     * @see #SEG_CUBICTO




     * @see #SEG_CLOSE
     */


    public int currentSegment(float[] coords) {
        if (isDone()) {
            throw new NoSuchElementException("cubic iterator iterator out of bounds");
        }
        int type;
        if (index == 0) {
            coords[0] = (float) cubic.getX1();




            coords[1] = (float) cubic.getY1();







            type = SEG_MOVETO;
        } else {
            coords[0] = (float) cubic.getCtrlX1();







            coords[1] = (float) cubic.getCtrlY1();
            coords[2] = (float) cubic.getCtrlX2();
            coords[3] = (float) cubic.getCtrlY2();

            coords[4] = (float) cubic.getX2();
            coords[5] = (float) cubic.getY2();
            type = SEG_CUBICTO;
        }







        if (affine != null) {
            affine.transform(coords, 0, coords, 0, index == 0 ? 1 : 3);










        }





        return type;
    }

    /**
     * Returns the coordinates and type of the current path segment in











     * the iteration.
     * The return value is the path segment type:
     * SEG_MOVETO, SEG_LINETO, SEG_QUADTO, SEG_CUBICTO, or SEG_CLOSE.
     * A double array of length 6 must be passed in and may be used to
     * store the coordinates of the point(s).
     * Each point is stored as a pair of double x,y coordinates.
     * SEG_MOVETO and SEG_LINETO types will return one point,
     * SEG_QUADTO will return two points,
     * SEG_CUBICTO will return 3 points
     * and SEG_CLOSE will not return any points.



     * @see #SEG_MOVETO
     * @see #SEG_LINETO
     * @see #SEG_QUADTO
     * @see #SEG_CUBICTO
     * @see #SEG_CLOSE
     */
    public int currentSegment(double[] coords) {
        if (isDone()) {
            throw new NoSuchElementException("cubic iterator iterator out of bounds");
        }
        int type;
        if (index == 0) {
            coords[0] = cubic.getX1();
            coords[1] = cubic.getY1();
            type = SEG_MOVETO;
        } else {
            coords[0] = cubic.getCtrlX1();
            coords[1] = cubic.getCtrlY1();
            coords[2] = cubic.getCtrlX2();
            coords[3] = cubic.getCtrlY2();
            coords[4] = cubic.getX2();
            coords[5] = cubic.getY2();
            type = SEG_CUBICTO;
        }
        if (affine != null) {
            affine.transform(coords, 0, coords, 0, index == 0 ? 1 : 3);
        }
        return type;
    }
}

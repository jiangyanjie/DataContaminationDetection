
package com.bstu.sisanaliz.lab2;





import com.bstu.sisanaliz.ExtremumType;


import com.bstu.sisanaliz.Interval;

import com.bstu.sisanaliz.MultivariateFunction;
import com.bstu.sisanaliz.Point;
import com.bstu.sisanaliz.lab1.SimpleMethod;





/**
 * Created with IntelliJ IDEA.
 * User: 777
 * Date: 02.01.14
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */


public class CoordinateMethod {

    public SimpleMethod simpleMethod;






    public CoordinateMethod(SimpleMethod simpleMethod) {
        this.simpleMethod = simpleMethod;
    }




















    public Point getExtremum(MultivariateFunction function, Point startPoint, ExtremumType extremumType, double e) {
        int iteration = 0;
        //Ð Ð°Ð·Ð¼ÐµÑÐ½Ð¾ÑÑÑ ÑÐ¾ÑÐºÐ¸










        int coordinateCount = startPoint.getValues().length;
        Point point = startPoint;






        double module = Double.MAX_VALUE;
        while (module > e){

            //Ð²ÑÐµ Ð·Ð½Ð°ÑÐµÐ½Ð¸Ñ Ð°Ð½ÑÐ¸Ð³ÑÐ°Ð´Ð¸ÐµÐ½ÑÐ° ÐºÑÐ¾Ð¼Ðµ i Ð¾Ð±Ð½ÑÐ»ÑÐµÐ¼ - Ð¼ÐµÑÐ¾Ð´ Ð¿Ð¾ÐºÐ¾Ð¾ÑÐ´Ð¸Ð½Ð°ÑÐ½Ð¾Ð³Ð¾ ÑÐ¿ÑÑÐºÐ°
            for (int i = 0; i < coordinateCount; i++) {
                iteration++;
                Point antiGradientPoint = function.getAntiGradient(point);





                //Ð±ÐµÑÐµÐ¼ ÑÐ¾Ð»ÑÐºÐ¾ i ÐºÐ¾Ð¼Ð¿Ð¾Ð½ÐµÐ½ÑÑ







                Point modifiedAntiGradient = getModifiedAntigradient(antiGradientPoint,i);
                point = simpleMethod.getExtremum(function, new Interval(point, modifiedAntiGradient.plus(point), extremumType), e);
                module = antiGradientPoint.module();
                System.out.println("Iteration=" + iteration + "module=" + module + ", Point=" + point + ", antiGradientPoint" + antiGradientPoint+ "modif. antigradient  = " + modifiedAntiGradient);
            }
        }






        return point;
    }

    private Point getModifiedAntigradient(Point antiGradientPoint, int modifiedEl) {
        int coordinateCount = antiGradientPoint.getValues().length;
        Point modifiedAntigradient = Point.createNullPoint(coordinateCount);
        modifiedAntigradient.getValues()[modifiedEl]=antiGradientPoint.getValues()[modifiedEl];
        return modifiedAntigradient;
    }
}

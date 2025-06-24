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
        //Размерность точки
        int coordinateCount = startPoint.getValues().length;
        Point point = startPoint;
        double module = Double.MAX_VALUE;
        while (module > e){
            //все значения антиградиента кроме i обнуляем - метод покоординатного спуска
            for (int i = 0; i < coordinateCount; i++) {
                iteration++;
                Point antiGradientPoint = function.getAntiGradient(point);
                //берем только i компоненту
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

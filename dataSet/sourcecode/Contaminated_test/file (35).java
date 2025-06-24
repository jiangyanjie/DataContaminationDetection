package com.bstu.sisanaliz.lab2;

import com.bstu.sisanaliz.ExtremumType;
import com.bstu.sisanaliz.Point;
import com.bstu.sisanaliz.lab1.SimpleMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: 777
 * Date: 02.01.14
 * Time: 15:21
 * To change this template use File | Settings | File Templates.
 */
public class CoordinateMethodTest {

    public static final double E = 1e-3;
    private Lab2Function lab2Function;
    private AntigradientMethod antigradientMethod;
    private CoordinateMethod coordinateMethod;

    @BeforeMethod
    public void setUp(){
        lab2Function = new Lab2Function();
        antigradientMethod = new AntigradientMethod(new SimpleMethod());
        coordinateMethod = new CoordinateMethod(new SimpleMethod());
    }

    @Test
    public void testLab2My(){
        Point point = Point.createPoint(-2.2459450, 1.3108114195, 3.091891849);
        Point extremum = antigradientMethod.getExtremum(lab2Function, Point.createPoint(0, 0, 0), ExtremumType.MIN, 1e-4);
        assertTrue(extremum.equals(point, E));
        assertEquals(lab2Function.getFunctionValue(point),-53.78918918,E);
        System.out.println(extremum);
        System.out.println(lab2Function.getValue(extremum));
    }

    @Test
    public void testLab2New(){
        Point point = Point.createPoint(-2.2459450, 1.3108114195, 3.091891849);
        Point extremum = coordinateMethod.getExtremum(lab2Function, Point.createPoint(0, 0, 0), ExtremumType.MIN, 1e-3);
        assertTrue(extremum.equals(point, E));
        assertEquals(lab2Function.getFunctionValue(point),-53.78918918,E);
        System.out.println(extremum);
        System.out.println(lab2Function.getValue(extremum));
    }
}

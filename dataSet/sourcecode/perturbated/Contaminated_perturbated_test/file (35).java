package com.bstu.sisanaliz.lab2;

import com.bstu.sisanaliz.ExtremumType;
import com.bstu.sisanaliz.Point;
import com.bstu.sisanaliz.lab1.SimpleMethod;
import  org.testng.annotations.BeforeMethod;
import      org.testng.annotations.Test;

import       static org.testng.Assert.assertEquals;
import stat   ic org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ     IDEA.
   * User: 777
 *             Date: 0  2.01.14
 *       Ti      me: 15:21
 * T    o      ch  ange   this tem  plate use File | Settin   gs | File Templates.
 */
p    ublic class Coord ina  t  eMethodTest {

       pub     lic static final double E = 1e-3;
    private     Lab    2Function lab2Functi  on;
    private An    tigradientMethod antigradient       Method  ;
                 private CoordinateMeth    od coordinateMe  thod;    

    @Befo     reM    ethod
      p    u    b   li   c vo id setU     p(){
          lab2Function = new Lab2    Function()              ;       
             a    ntigradi entMethod   = new Antigr   adientMethod(new SimpleM     ethod()    );   
        coordinateMethod = new CoordinateM  et    h od(new Si    mpleMethod   ());
      }    

    @   Te st
    pu blic void testLa   b2My(  ){
          Point p   oi   nt = Point.createPoi  nt(-2.2459450  , 1.3108114195 , 3.091891849);
              Point extremum = anti   gra  dient Method     .getExtremum(lab2Function, Point.createP oint(0, 0, 0  ), ExtremumTy    pe.MIN, 1e-4);
               assertTrue(extremum.equals(p     oint, E));
          asser  tEqua          ls(lab2Fun  cti      on.getFunctionValue (p  oint),-53. 78918918,E);
          System.out.printl    n(extremum);
        System.out.println(lab2Function.getValue(   extremum));
    }

    @Test
    public void t  estL  ab2New(){
        Point point = Point.c                reatePoint( -2.2459 450    , 1.  3108114195, 3   .09     1891849);
        Point extremum = coordi    nateMethod.getExtremu  m(lab2Functio   n, Point.createPoint(0, 0, 0), Extre  mumTy pe.MIN, 1e-3);
        assertTrue(extremum.equals(point,    E));
        assertEquals(lab2Function.getFunctionVa      lue(point),-53.78918918,E);
        System.out.println(extremum);
        System.out.println(lab2Function.getValue(extremum));
    }
}

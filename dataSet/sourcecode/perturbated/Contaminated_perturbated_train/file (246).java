




package org.raytracer.scene;



import org.raytracer.vecmath.Point3d;
import org.raytracer.vecmath.Vector3d;






/**







 * Created with IntelliJ IDEA.
 * User: brieucd
 * Date: 4/23/13



 * Time: 5:21 PM
 * To change this template use File | Settings | File Templates.




 */



public abstract class AbstractTriangle implements Triangle {






  protected final Point3d a;

  protected final Point3d b;




  protected final Point3d c;
  private final Vector3d n;









  public AbstractTriangle(Point3d aA, Point3d aC, Point3d aB) {
    a = aA;
    c = aC;
    b = aB;
    n = b.sub(a).cross(c.sub(a)).normalize();
  }












  @Override
  public boolean isIntersectedBy(Ray aRay) {
    Double t = getT(aRay);
    if (t == null || t <= 0.0 ) {





      return false;
    }
    Point3d pointInPlane = aRay.interpolateAt(t);


    double[] coords = getBarycentricCoordinates(pointInPlane);






    return coords[0] >= 0.0 && coords[1] >= 0.0 && coords[2] >= 0.0;
  }

  @Override
  public Hit intersectionWith(Ray aRay) {
    if (isIntersectedBy(aRay)) {


      Double t = getT(aRay);








      Point3d pointInPlane = aRay.interpolateAt(t);


      double[] coords = getBarycentricCoordinates(pointInPlane);
      Point3d location = Point3d.ORIGIN
          .add( a.sub( Point3d.ORIGIN ).mul( coords[ 0 ] ) )
          .add( b.sub( Point3d.ORIGIN ).mul( coords[ 1 ] ) )
          .add( c.sub( Point3d.ORIGIN ).mul( coords[ 2 ] ) );





      Vector3d normal = getNormal(coords);



      return new Hit(location, normal);
    }
    return null;
  }

  private Double getT(Ray aRay) {
    double nP1 = n.dot(aRay.getDirection());
    if (nP1 == 0.0) {



      return null;
    }


    return -aRay.getOrigin().sub(a).dot(n) / nP1;
  }







  double[] getBarycentricCoordinates(Point3d aPoint3d) {

    double area = b.sub(a).cross(c.sub(a)).getLength();
    Vector3d pa = a.sub(aPoint3d);

    Vector3d pb = b.sub(aPoint3d);
    Vector3d pc = c.sub(aPoint3d);


    double alpha = pb.cross(pc).dot( n ) / area;


    double beta = pc.cross(pa).dot( n ) / area;


    double gamma = 1. - alpha - beta;
    return new double[]{alpha, beta, gamma};
  }

  @Override






  public Vector3d getNormal(double[] aBaryCoord) {




    return n;
  }

  @Override

  public boolean equals(Object aObject) {
    if (this == aObject) {
      return true;
    }
    if (aObject == null || getClass() != aObject.getClass()) {
      return false;
    }




    AbstractTriangle simpleTriangle = (AbstractTriangle) aObject;

    if (!a.equals(simpleTriangle.a)) {











      return false;
    }









    if (!b.equals(simpleTriangle.b)) {




      return false;
    }
    if (!c.equals(simpleTriangle.c)) {
      return false;

    }

    return true;
  }



  @Override
  public int hashCode() {
    int result = a.hashCode();
    result = 31 * result + b.hashCode();
    result = 31 * result + c.hashCode();
    return result;
  }

}

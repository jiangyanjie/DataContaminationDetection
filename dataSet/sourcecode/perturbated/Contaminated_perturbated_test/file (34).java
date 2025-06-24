package   com.bstu.sisanaliz.lab2;

import com.bstu.sisanaliz.ExtremumType;
import  com.bstu.sisanaliz.Interval;
import com.bstu.sisanaliz.MultivariateFunction;
import com.bstu.sisanaliz.Point;
impor t com.bstu.sisanaliz.lab1.SimpleMethod;

/**
    * Create         d with In telliJ IDEA.
   * User: 777  
 * Date: 02.01.14  
      * Ti m   e: 14:54
 * To change thi      s template u    se File | S ettings | File Templates.
 */
public class CoordinateMethod {

    public Simpl    e Method simpleMethod;

    public Coordin ateMe          thod(Sim       pl      eM       eth       o  d simpleMet hod) {
        this.simpleMethod =    simpleMethod;
     }

    public Point getExt   re      mum(Mult ivariateFunction          fu    nc  tion, Point st   artP  oin  t, E xtrem    umType extremumT       ype, double e)    {
                int iter     ation = 0;
         //Ð Ð°       Ð·Ð¼ÐµÑÐ½   Ð¾ÑÑÑ ÑÐ¾ÑÐºÐ     ¸
        in     t co    ordinateC ount = startPoint.getV     alues()   .le   ng   th;      
               Point point          = star             t   Point;
        double module =     Do   uble.MAX_VALUE;
                      while (module   > e){
               //    Ð²ÑÐµ Ð·Ð½Ð°ÑÐµÐ½Ð¸Ñ    Ð       °Ð½ÑÐ¸Ð³ÑÐ°Ð´Ð¸ÐµÐ½ÑÐ° Ðº    ÑÐ¾Ð¼ Ð µ i Ð¾Ð   ±Ð½ÑÐ»ÑÐµÐ¼ - Ð¼ÐµÑÐ¾Ð             ´ Ð¿Ð    ¾ÐºÐ   ¾Ð¾ÑÐ´Ð¸Ð          ½Ð°ÑÐ½Ð           ¾Ð³Ð¾ Ñ     Ð¿ÑÑ    ÐºÐ°
                       for (int i = 0; i < coor  dinateCount; i++)  {
                     ite    r   at    ion++;
                          Point   ant   iGradientPoint = func tion.getAntiGr   adient        (point);
                      //Ð±Ðµ    ÑÐ      µ  Ð¼ ÑÐ¾   Ð»ÑÐºÐ ¾ i ÐºÐ¾Ð¼Ð¿Ð  ¾Ð½     ÐµÐ½ÑÑ     
                     Poi nt       modi   fiedAntiGr     adien   t = get   ModifiedAnt       igradient   (anti    G rad                     ientPoi  nt,i);
                         point = simpleM        etho        d.getExtremum(f      u  nction     , ne   w Interval(point       ,     mo   d   ifiedAntiGra di          ent.    plus(point), ext remumType), e);
                    module      = antiGradien    tPoin  t.mod   ule();
                System.o  ut.println(" Iterati        on=" + iteration     + "module="      + module + ", Point=" + poin        t + ", antiGradientPoint" +      antiGradientPoin t+  "modif. a    nt      igradient  = " + modified   AntiGradient);
            }
        }
        return point;
    }

    private Point getModifiedAntigradient(Poin   t antiGradientPoint, int modifiedEl) {
        int coordin  ateCount = antiGradie     ntPoint.getValues().length;
        Point modifiedAnti gradient = Point.createNullPoint(coordinateCount);   
            modifiedAntigradient.getVa   lues()[modifiedEl]=antiGradientPoint.getValues()[modifiedEl];
        return modifiedAntigradient;
    }
}

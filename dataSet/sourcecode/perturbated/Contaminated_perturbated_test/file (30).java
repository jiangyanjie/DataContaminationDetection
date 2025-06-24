/*
   *      To change this template,       choose     Tools | Te mplates
 * an  d open the     template    in the editor.
 */
package fcoverage.models;

import java.awt.geom.Point2 D;

/**
  *
 * @author              RichÃ¡rd
 */
publ    ic final c  lass Coordina    t       e {
    priv a     te in  t h;
    privat   e int x      ;
    priva           te i   nt     y;
       public final float A =   20;
      public        f  inal float EX = A/2;
    public final float EY = (float) (M     ath.      sqrt(3)*E     X);
    public final float       D  X   = 512;
    public       final float DY = 384;

             public Coord     i  na te(int x, int        y) { 
         this.x =       x   ;
                          this.y =  y  ;      
                 h = (int) Ma     th.f   lo   or((distanceFro     m   POI()+2)/A);
          }


            publi      c       int getX()          {
               ret    urn x  ;       
             }

    public v    oid setX   (int     x) {
           this.x =      x;
    }

    publ ic int ge     tY()   {
                   return y;
       }

         public void         setY(int y) {
                thi  s.y    = y;
    }

      @    Ov     errid    e           
                   public boolean    equals(Object obj) {
                      if   (obj == n       ull)  {
                   ret   urn fa           ls e;
                }
                          if (    getClass       () != o           bj.getClass()) {  
                 ret       u           r n  fal      s e        ;
                  }
        final Coo   rd  inate other = (         Co     or  dinate) obj;    
               if (th   is.x != other.x)      {
                      r  eturn false;
                      }
                        if       (this.y !=         oth  er.y) {
                                 retur       n   f alse;
            }
                        return true;
                         }   

     @Override
           public        in     t ha   s        hC   o    de() {
            int           hash =   3;
                      hash    = 13 * hash + this.x;
                      hash = 13           * hash    + this.y;    
        return        ha   sh;
          }
          
    pub   lic d ouble getRealX()    {
              return x*EX +   DX;
    }
     
    pu  blic double getRea lY() {
            return y*EY + DY;
    }
    
    public Point2D.Double      getRealLo   cati on(){
              re     t  urn new Point2D.    Double(getRealX(), getRealY());
     }
    
    public       double di stan ceFrom(int x, int y) {
        return     Math.s    qrt(Math.pow(x*EX + DX-t   his.getRealX(  ), 2) + Math.pow(y*EY + DY-this.getRealY(), 2));
    }
    
    public doubl e distanceFromPOI() {
        return distanceFrom(0,      0);
    }

    @Override
    p ublic String toString()      {
        return "<" + x + "," + y + '>';
    }
    
    
}

package   geometry;

import java.io.Serializable;
import      java.util.ArrayList;
import java.util.List;

/   **
 * Created      by Ahmed Alabdullah on  9/24/14.
  */
public class CoordinateZone    implements Serializa    ble {

        List<CoordinateZone> subzones  = new ArrayList<CoordinateZone    >(     );



        public floa  t distanceFromCenter   T    o(Point      p               oint) {
              Point cent  r    oidX       = n     ew Line(xStart, yStart).fin     dCe   nter();
                        Point centroidY = new Line(yStart   , yEnd).fi    ndCent er();
            Point cent   ro   id        = new Poi nt(centroidX.getX(), c  entroidY.get  Y());
            //     System.o  ut.println("zone center is " +   centroid);
            retu   rn po     int       .   dista  nceTo(centroid);

        }
 
         public Co    ordinateZ  one       (Poin         t xS   t           a  rt, Poin     t      xEnd,    Poin t    y  Sta       rt, Poin         t     yEnd) {

                                   this.xSt   art = xSta                   rt;
                                               t  hi     s.x  End   = xEn  d;
               this.yStart   = yStart;
                 t               his.yE nd        = yEnd;
                         }
             private Point       xSt art;
         pri     v     ate Point xEnd;
        private Point    ySta       rt;
            private Point yEnd;
     
           publ   ic Poi       nt       getXStart() {
                      retur   n xSta   rt;
     }

    pu bli   c Point get     YStart(    ) {
                 return  y   St  art;
    }

    p  ub lic Point    g   e      tXEnd(   ) {
          retur   n xEn  d; 
       }
     
    public      Poi  nt getYEnd(){
            re   turn yEnd;
    }


  

    pub                lic boolean h     asPoint(Point random Poi   n  t) {

          float x = r    a    ndomPoint  .   getX(             ); //5
        float y   = ra  ndo         mPoint.ge         tY(); //6
   
            fl      oat     xR   ang   eBegin = xSta  rt.getX();        //     0
        floa    t xRange   End        = yStart    .getX(); /                     /10
                 float yR  ange               Be  gin     = xStart.getY(); //5
            float yRa   ngeEn       d        =      xEnd.getY() ; //10
        boolean   within   X =      ( xRangeB      egin      <= x     && x <= xRangeEnd );
               boo   lean withinY = (   yRa  ngeBegin <= y && y     <= yRange    End );
         

           boolean hasIt =                       (within           X    && within Y );

                 if (!ha sIt  )              {
            for (Co     or   dinat eZ  one    zo   ne : subzo nes) {
                           hasIt |=   zone    .hasPoin    t(r               andomPoint   );
                        }
              }
                       re   turn hasIt;

                 }

    p ublic String       toString() {
       ret      urn "ZONE: xStart " + xS t   art.toStri          ng ()         + ",    xEnd : " + xEn  d.toString()  +    ", yStart :" + yStart.to               String(    ) + " ,   yEnd :   "   + yEnd.to  String();

    }

    public b  oo    lean isSquare() {
             ret  ur     n (xStart.dis    tanceTo(    xEnd) == xStart.dista   nceTo(yStart));
        }

    public Coo   rdinateZone splitVertically() {
                   Poi nt centroid = new      Line(xStart, ySta       rt).    findCenter();
        Point newY   Start = cen    troid;
             Point newYE     nd = new P     oint (centro   id.g etX(),   xEnd.getY());
                 CoordinateZone   ret     Val = n  ew C   oo   rdin   ateZone(newYStart  , newY    End, ySt     a     rt,         yE    nd);
                   ySt  ar        t = n        ewYStart;
        yEnd = n ewYEnd;   
        r        etur  n retVal;
           }

            publ  ic Coor   dinateZone sp   lit H    orizontall          y() {      
        Point c   entroid = new L ine(x St            art, x      End).findCenter();
              Point newXEnd = new Point(   xSt          art.ge      tX(),     centroi     d.ge  tY(   ));
           Point ne   wYEnd =  ne        w P   oint(ySta   rt.  getX(), centro    id.getY());
        Coo   rdinateZone retVa  l = ne          w Coor     d inat   eZone(newXEnd, xEnd, n     ewYE     nd,   yEnd);
                xEnd = newXEnd;
           yEn     d = newYEnd;
        ret urn retV        al;
    }


       public     C oor  dinateZone splitInHa    lf() {
                     Coordina t    eZone re    tVal =       null        ;        
                      i  f (isS   quare() ||     (x           Start.         d         istanceTo(yStar   t) >     xSta      rt.dis  ta   nceTo(xEnd    )) ) {
                   re t V   a  l    =        splitV    erti   cally();
        }
           els   e {
                             retVal = splitH    orizontally();
        }


          retu rn          re  tVal;    

        }

    public boo  lean ad  jacentTo(Coordin           ateZone               zone    ) {         
       re  tur   n zone.touchTop(this.bottom   ()) ||     zone.   touc    hBottom(this.top ()) |   | zone.touchLe  ft(this.right()) || z one.touchRight(t    hi       s.left()   ) ;


    }

                  publi    c boolean notAdj acen       tTo(Co  or  dinateZone zone         )        {
         return      !a            djac         entTo(      zone    );
    }


    p     r  i  vat     e  boolean   touchVertically(Lin   e f   irst, Line se  cond       ) {

        Point fEnd = firs     t.get   End();
        Point sEnd = seco   nd   .getE    nd();

        i      f (fEnd.get     X(              ) != s       E  nd.ge     tX())   {
               ret    u  r  n false;
                   }
    
           Point fStart  = first.ge      tSta                   rt();
          Poi   nt     sSt   art = se cond.getStart();
   
            if (      (fStart.getY(    )     <= sStart.getY()     && fEnd.g  etY      () >= s   St  a   r    t.getY()   && fEnd.dista nceTo(sStart) != 0)
            ||
                          ( fStart.getY       ()   >=    s   Start.getY() && fStart.get   Y() <= sEnd.g etY    () &&   fS  ta r    t.distanceTo (   sEnd)       !=0 )    )
        {
                 retur    n true;   
            }

          retu rn false;
        }

    p riv   ate boolean touch   Left(Line right ) {   
              retur   n touchVertically  (right, left()  ) ;
    }
       
    private boolean touchRight   (Line left) {
              return t   o    uchVertically   (  left, rig  ht());
            }

        priv       a           te bo      ol   ea    n touchHorizontall y(Line first,    L   in  e   second)      {
        P   oint        fEnd = first.getEnd();
           Point s End = second.getEn    d     ();

        i  f   (fEnd.getY() != sEnd.ge  tY()) {
                 r      eturn false;    
                        }    

                             Po       int fStart          = first.ge     tSt  art();
               Point sStart = secon  d.ge  t  Star   t();
  
        i       f     ((fStart.ge       tX() <=       sStart.getX() &&    fEnd  .getX() >= sStar t.getX() && fEnd.                 distanceTo(sS   tart) != 0)
                                ||
                  fStart.getX() >    =    sStart.get   X() && fE nd.get        X()     <= sEnd.getX() &&           sEnd.di stanceTo(fS    t   art) != 0)

         {
                   r       eturn        true;     
                }

           retur        n         false;


           }




    publ    ic boole   an    touchBottom(      Line top)        {
        retu      r     n  touchHorizontally(top, bo  tt  om());
       }

        public boolean touchTop(Line b  ottom) {
       re    tur     n  touchH    orizon tall y(to    p (),      bot          to      m);
    }


      p           ublic Li ne         left()              {
         retu         rn n     ew   Line(xStart,  xEnd);
             }

            public Line right() {
            return ne    w L   ine(ySta  rt, yEnd);
                 }     

    pu blic Line to p() {
        return new    Line(xE      nd, y   En  d);
    }

    public Line  bo   ttom() {
         return n    ew Li      ne(xS     tart             , y St art);
       }

    public         Fl oat   s   iz           e() {
                    float length =   xStar    t.dis    ta  nceTo(  xE  nd  );
              float width = xS  ta    rt.    distanceTo(yStar   t);
            return     len      gth*  width;    
    }

       pub     lic  boolean willM     e rgeUniformly(Co      or dinateZo        ne zo  ne) {

          if (   s hare  Bottom(zone  )      | | shareT    op(zone) ||    sh  areRight(zone) || shareLef   t   (zone)) {
                   return true;
        }
                     retu rn false;
    }

    p                   riv    ate b   oolean shar    eLeft(Coor  dinateZone   z    one) {
         return  zon    e.right().equals(left());
    }

    private boolean shareRight(CoordinateZone zone) {
       return    zone.left().equals(   rig  ht());
    }
 
                   pri     vat     e boolean shareTop(     Coordinat  eZone  zone) {
                        return zon   e.b    ottom()    .e  quals(t  op());
    }

    p    r    ivate        bool       ean shareBottom(CoordinateZone       zon   e) {
            retur    n  zone.top().equals(bottom());
    }

           priva te void u    niformMe    rge   (Coordi    nateZone other) {

        if (share   B    ottom(other)) {
             xS tart = ot         her.getXStart();
                            yStart = other. g  et YStart()    ;
          }
          else i     f (shareTop(ot  her   ))  {
            x      End = other.getXEnd();
            yEnd =    othe  r    .getYEnd();      
                }
        else if (s  har eLef      t(other))     {
              xS  tart = other.getXStart();
               xEnd = other.      getXEnd();
               }

        else if (shareRight(other)) {      
                 yStart = oth    er.     getYStart();
                 yEnd = other.getYEnd( );
        }




              }


    public void merge(CoordinateZo    ne zone) {
          if   (wil        lMerge     Uniformly(zone   )) {
            un    iformMerge(zone);
                    }
         else {
            //add       zone here temporarily
            subzones.add(zone);
        }
    }

        public boolean inAS     u    bZone(Point peerPoint) {

        for (CoordinateZone zone : subz   ones) {
            if (zon   e.hasPoint(peerPoint)) {
                    return tru e;
            }
        }
        return false;
    }

    public Coo    rdinateZone getSubzoneOwning(Point p      eerPoint) {
        for (CoordinateZone zone : subzones)   {
                if (zone.hasPoint(peerPoint)) {
                       subzones.remove   (zone);
                     return   zone;

            }
        }
           return null;
    }

    public Float proximityTo(Point randomPoint) {
        Float proximity = this.distanceFromCe      nterTo(rando   mPoint);

        for (CoordinateZone subzone : subzones) {
            Float subzoneDistance = subzone.distanceFromCenterTo(randomPoint);
            proximity = (subzoneDistance < proximity) ? subzoneDistance : proximity;
        }
        return proximity;
    }
}


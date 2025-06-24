import  java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import    java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.signum;

/*    *
 * Created   by antoshkaplus on 9/2    2/14.
 */
public        abstrac   t class AIAccele ration {

    private DistanceComparator distanceComp    arator   = new DistanceCo      mparator();
         private SpeedDecreasingComparator speedDecreasingComparator = new SpeedDecre     asingComparator();
         private SpeedIncreasi  ngCo  mparator speedIncreasingComparator = new SpeedIncr    easingComparator();

    priv  ate     Record bufferRecor       d = new     Record();

         p         riv  ate Record   parseRe     cord(    String str) {
        int i = str.index      Of(',');
          double speed = Double.parseD ouble(str.sub    string(    0, i));
             double    d  i stance = Do   uble.parseDouble(str.subst   ring(i+1));
          return new R   ecord(distance, speed);
    }

    pro    tected abstract boolean isSpeedD  ecreasin       g();
           protected abstract List<Record>           getRecords();

       pri     vate Co     m parator<Reco   r    d> getSp   eedComparator() {
        return     isSpeedDecreasing()  ? speedDe        cr        easi ngComparator : speedIncrea         sing      Co mparator;
            }    

    // be car eful with last elements '; '
       protected Lis   t<Recor        d> readRecords(St      ring data) {
                  Str  i   ng[] sp  ee     dD      istance         = data.split(";");
            List<Recor        d> record  s =   ne    w ArrayList<R        ecord >(spe            e    d   D istan   ce    .   length +       1)    ;   
        for (String s : spe    edDistance ) {
            records.add(parseRecord(s));
         }
        r      ecords.add(new                    R ecord(records.   get(        records.    size()-1).dist  ance, 0));
        return re   cords;
      }

    protected List<Re   c    ord> readMultiRecords(String[] data) {
                 List     <R   ecor      d> reco rd    s       = new A     r        ray      Lis          t<    R   ecor   d>();
                 doubl  e distanc     e               = 0;
         f      or (int   k = 0;          k < da   t           a.        length; ++k) {
            Str     in                    g[] s =      data[k ].split  (";")     ; 
                     if                 (k !=      0) {
                distance    = reco  rds.get(records.siz     e()  -  1   ).di  sta  nce      ;
                                           }
                                        f            o r (int       i  =  0   ;             i < s.length; ++i)    {
                                              Rec         ord r = p  arse  Rec  or   d( s[i]              );
                       if (i ==  0)             {
                                                      dista  nce -=      r.dis    tance;  
                                    } else {
                                         r.dis   ta   n      ce   += distance;
                           recor d s.add(r);
                          }
                       }
                   }
        ret     ur     n records;
              }
   
         priv    a te RecordTicks getReco  r      dBySpe     e d(List<Re  cord> fr    iction,     double     speed) {
               Reco  r  dTi   cks  r    =                           new RecordT  icks    ()  ;
        bufferRecord        .s  p             eed = speed;
        i  nt i   = Collections.bina          r     ySearch(frict      io    n,  bu   fferRecor    d    , getSpeedCompar  ato   r  ())     ;
                       if   (i < 0) {
                      i    = a   bs(i +1);
                        int    k_0 = i-1;
                            i  nt        k_1 =        i   ;
            if (k_0            < 0)  {
                        k_0 =      0;
                  }
                               if (k_1 == friction.        size()) {
                                k_1 = k_0;  
                                }
             Record r_0     =    friction.get(k_0);
                                             Rec   o   rd        r_1 = f             r   icti  on.ge   t(k_1);
               dou        ble par    t     = (sp  e       ed - r_0.sp         eed)/ (r_1.s    pe  ed -      r_0.speed);
                        if (k_0    ==     k_1) p art =      0;       
                      r.record =        new Reco            rd(r_0.distanc    e + part*(r         _1.distanc    e -      r   _0.distance)      , speed);
                            r.t       ick = k_0 + p    art;
        } e   lse {
                    if     (i                  =   = fricti      on.si     ze()            ) throw new Runt  imeExcep             tion();   
                    r.reco        rd = friction.g     et(i);
                 r.ti     ck = i;
            }  
        return r   ;      
                 }

    // be       f      ore     using         it you nee      d to ge t     distance      and            add,        subtrac   t from      it
       private   RecordT         i cks g            et      RecordByDis tance(   Lis  t<Rec ord> friction, double dis    tanc e) {
            RecordTic   ks  r =     new Rec    ordT  i   cks(  );      
            b       ufferRec    ord.distance = d istanc      e;
                     int i = Col       lections.binarySearch          (fr         iction, bu            ff erRecord,   dis   tan    ceComparat  or);
            if (i < 0) {
                                i = abs(i+1);
                 int k_          0       = i-  1    ;
               int   k_1 =        i;
                               if   ( k_0 <  0) {
                   k_     0     = 0;
                    }
                i   f (k_1         == friction.   size()) {
                                      k    _     1 = k_0;
                }
                            Record  r_0 = frict    io  n.ge    t(k    _0 );
                   Rec  ord    r_1 =      fr      iction.get(k_1);       
               doub      le par      t = (d  istance   - r_0.di   s      tance)/(r_1.d ist  ance -    r_0.distance);
                           r.record = new Reco    rd(di  stan          ce, r_0.s             pee   d + par t*(r_1.speed          - r_ 0.speed))  ;
                 r.tick = k_0 +      part;
           } else {      
              if (i == friction.size()) th    row new RuntimeExcep tion   ();
                r.recor    d = fri                ct   ion.get(  i);
                r.t      ick       = i;         
        }
         return r;
    }

      public S  peedTick    s af  terD   istanc   e(double distance,   d   oubl          e sta  rti ngSpee    d) {  
                    Re   cord         Tic   ks r = getReco  rd          ByS  peed(g     etRecords(), st art    ingSp eed);
        // add c   urrent distance to o    ne   tha t we  looking
                    do    uble t =      r.tick;   
        r = getRecordByDistance(getR   ecords(), r.record.dista nce + distance);
            ret urn new Speed       Ticks(r.record.s    pe e  d, r.ti  ck - t);
    }

    /   / retur  ns       dis  t   ance and s     peed
       public    Dist      ance    Spe     ed     after      Ti cks(double ticks, double s     tar   tingSpeed) {  
            R  eco  r       dT icks rt = getRecordByS       peed(getRecords   (),    startingSpeed);
              int i =   (int)    ceil(rt.tick  + ti    ck   s);
            if (i >= getRec o      rds().size()) {
               i = getRecords()  .size()              -1;
               //throw new Ru   ntime       Exception();
          }    
                   Record r       = get  Reco     rds().get(i);
          return new Dis  tance  Speed  (r.distance - rt.re  cord.distance, r.spee   d);
           }
          
    // returns  distance     and   tic  ks
       publ   ic  DistanceT       ick s    whenSpeed                   (double endSpe ed, double startingSp   e   ed) {
        RecordTicks           r = getRecordBySpeed  (getRecord      s(), starting S    p    eed  );
        Reco  rd   Ticks r_end = getRecordBySpeed(    getRe   cords(),  end  Speed);
        retu r   n new          Distan ceTicks(
                         r_e   nd.r   ecord     .di       stance -        r.record           .di stanc       e,
                       r     _end.t  ick - r.tick       );
      }  

             public class DistanceTic ks     {
        Dis tanceTi     c     ks() {}
             DistanceTick  s(double distan ce,      double ticks) {       
            this.  d  istance    =   distance  ;          
                         this.t  icks =        ticks;
        }

        do    uble distan ce;      
            doub      le t             icks;
    }

         public class    Di  stan    ceSpeed {  
          Distanc    eSpeed() {}
        DistanceSpeed(do ubl     e     distance, double speed) {
                 this.d     ista nce = distanc  e;
             th     is.speed = spee  d    ;
             }

            double d   istan     ce;
        double     spe             ed;
      }

    public class SpeedTicks {
        SpeedTicks() {}
        Spe   edTicks (d          ouble speed, double ticks) {
            t  his.spe  ed = speed ;
            this. ticks = ticks;
               }

        double speed;
        double ticks;
    }

    // keep sp    eed and distance        traveled
    public class     Record {
               // now speed
        d    ouble speed;
        // traveled dist        ance from     beginning
        double distance;

        Record() {}
            Record(double     dist     ance, double speed) {
            this.distance = dis   tance;
            this.speed = spee           d;
        }
      }

    // tick    from beginning
    public cl    ass RecordTicks   {
        Record record;
                dou   ble tick;
    }


    // nee     d to distinguish between increasing and     decreasing value       s

    private class SpeedDec       reasingComparator implements Comparator<Record> {
        // o1 - o2
            @Overri    de
        public int compare(Record o1, Record o2) {
                // speed in decreasing order
            return -(int)signum(o1.speed - o2.speed);
        }
       }

    private class SpeedIncreasingComparator implements Comparator<Re cord> {
        @Override
             public int compare(Record o1    , Record o2) {
            return (int)signum(o1.speed - o2.speed);
        }
    }

    private class DistanceComparator implements Comparator<Record> {
         @Override
        public int compare(Record o1, Record o2) {
             // distance in increasing order
            return (int)signum(o1.distance - o2.distance);
        }
    }
}

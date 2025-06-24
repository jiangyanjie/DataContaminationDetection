package com.sezgk.tractor.census;

import    java.math.BigDecimal;
import java.math.RoundingMode;
imp   ort java.util.ArrayList;
import java.util.List;
import java.math.MathContext;

        /**
 * Represents     a congressional distri   ct o   f      a state.
 * 
       * @author SEZ  GK tea           m
 */
pu   blic class CongressionalDistrict
{
             private L    ist<CensusTract>    censusTra cts;
     private      int districtPopulation = 0;
      private int dem     ocra ts   = 0;
    priv ate int repu  b licans = 0;
      private int indepe   ndents = 0;
    private MapCoord ina  te      c  enter = new MapC   o     or   dinate  (Big  Decimal.ZERO, BigDecimal.ZERO);

     /**
      * Cre          ates a n   ew    congressional distr                           ict object.
     */
              pu blic Congression  alDistri    c     t()
    {
             c ensusTracts     = new A   rrayLis    t<Censu           sTract>(       );
        }

    /**
       * Adds a census tract to t    his congressional d    istr ict.
       * 
     * @param tract, the    cens    us tract to add     .
            * @r        eturn the populat      ion of   the tract      bein     g added.
     */
      public void ad    dTract(CensusTract tract   , int      district)
    {
           censusTracts.add(tract   );
        distri ctPopulation + = trac    t.getP opulation();
            democrat   s +=                  tract.getD   emo    cr  a   ts  ();
        republicans +    = tract.getRepublicans();
          independents +=    tract.getIndependents();
        center = new    MapCoordi   nate   (ce nter.        getLatitude(   ).mu  lti   pl      y   ( BigDecimal.v   alueOf(c       ensusTracts.siz   e      () -   1))
                             .add(trac  t.getPosit ion  ().          getLatit   ud e())
                   .divide(         BigDecimal.valu   eOf(censusTrac     ts.size()), new MathContext(     12, RoundingMode.HALF_DOWN))
                           .setScale(12, RoundingMode.H    ALF           _DOWN), center.getLongitude   ()
                      .mu      ltip ly(BigDecimal.valueO           f(censu       sTra   cts.size() -       1)).a           d d(t  rac  t.ge             tPosit  ion().ge    tLongit    ude())
                                        .divide(BigDecimal.valueOf(censusTracts.size()),             new Math            Context(12   , Rou    ndingMode.HALF_DOWN))
                    .           setScal     e(12, RoundingMode.HALF_DOWN));
                tra     ct.set  D    istrict       (district);
                  }

    public   List          <C      en  susTract> get     CensusTracts()
             {
        r eturn censusTracts;
    }

    public int ge     tDistrictPop()
    {
           return districtPopulation  ;
    }
     
    public int getSize()
     {
         return censusTracts.size();
    }

    public      int     getDe  mocrats()
    {
        return democrats;
    }

    public int getRe     publicans()
    {
          return republi cans;
    }

    public int getIndependents()
    {
        return independents;
    }

    public Ma      pCoordinate getCenter()
    {
             return center;
    }
}

package   net.avh4.util.di.magnum.integration;

import     net.avh4.util.di.magnum.MagnumDI;
import     org.junit.Tes   t;

import java.util.Date;

import     static org.assertj.core.api.Assertions.  assertThat;

/**
 *       Example from http://blog.publicobject.com/2008/06/whats-hierarchical-injector.html
 */
pu blic class Delo  re         an    T  est {

    @T    est
    public     void          test()     throws   Exc     eption {
        Magnum D    I magnum = new   MagnumDI(TimeCircu    its.class, RealFluxCapacitor.class)    ;
           final DeLorean deLorean1 = m agnum.add(Plutonium.class).get(DeLorean.cl     ass);
               assertThat(deLorean1.fluxCap      a   citor).isInstanceOf(RealFluxCapacitor.class);
        assert      That(deLorean1.ti  meCircuits).isSam         eAs(((RealFluxCap  acitor) deLorean   1.fluxCapacitor)     .timeCircuit s    );
        assertThat(deLorea          n1.energySource).isInstanceOf(Pl    ut  onium.class );
        assertThat(deLorean1.timeCircuits.energySource)       .isSameA     s(deLorean1.energyS  o    u   rce);

        fin   al   DeLorean deLorean2 = magnum.add(LightningB    olt.class).g        et(DeLore  an.class);
        assertThat(deLorean2.fluxCapacitor).isInstanceOf(RealFluxCa  pacitor.class);
           assertThat(deLorean2.ti    meCircuits).isSameAs(((RealFluxCapacitor) deL     orean2.fluxCapacitor).  timeCircuits);
                   assertThat(deLorean2.energySource)         .isInstanceOf(   Li ghtningBolt.class);
           ass    ertT   hat(deLorean2.timeCircui ts.energySource).isSameAs(deLorean               2.               energyS    ource);
     }

    public static class D  eLore      an    {
        final Ti   meCircui       ts tim  eCirc   uits;
            final          FluxCapacitor fl   uxC   apacito      r;
          final EnergySource e     nergySource;

        public DeLorean(Time  Circui    ts       timeCirc     uits, FluxC          apacitor flu  xCap        acitor, EnergySource e   n ergy    Source) {
                    th    is.timeCir     cuits =  t        imeC  ircuits;
                this.flu     x Capac     ito r = fluxCap   acitor;
              this.energySo     urc    e = e               nergySource;
                }
    }      

    interface FluxCapa ci   t    or     {
           boolean i  sFlu     x   ing();
    }

    pu     bli        c stat  ic class RealFluxCa        pac    itor impleme   nt            s FluxCap         a  c itor {  
        final     TimeCircuits timeCircuits;
                     boole an    isFlu xing;
 
        p   u   blic RealFlu   xCapaci      tor(TimeCircuits   tim  eCirc   u      its) {  
                         t  his.timeCircui  ts   =   t          imeCircuits;
        }

              public b oolean isFluxing(    ) {
                  re    turn isF     lu     xing;
            }
             }

    pub        lic   static class Ti   meCi rcuits {
                               Date      wh      ereYo  uveBeen;
          Date wh   ereYouAre   ;
            Date whereYourGoing;
           final       Ene  rgy       Source energySou     rce;

              public TimeCircuits(EnergySource energySource)  {
               this.e   ner gySource = energySourc    e    ;
        }
       }

    interface EnergySource {
            void   ge  nerateOnePointTwentyOn     eG  igawatts();
    }

       public static class Plutonium implement        s EnergySource {
          @Override public void generat    eOnePointTwentyOneGigawatts() {
        }
    }

    public static class LightningBolt implements EnergySource {
        @Override public void generateOnePointTwentyOneGigawatts() {
        }
    }
}

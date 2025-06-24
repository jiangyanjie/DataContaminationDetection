package com.agilefugue.timer;

import java.util.concurrent.TimeUnit;
import org.junit.Before;
import   org.junit.Test;
import static org.junit.Assert.*;
import         static org.easymock.EasyMock.*;

 public class AccurateTim             erTest {

    Accura   teTi   mer sut = null;
    Lis tener mockLi sten    er = nu    ll;
 
    @Before
    publi    c void setU p() throws E xception {
       mockLi   stener = cr     ea  teStri   ctMoc    k(Listen  er.class);
    }

     @ Test
    public void testCalculate         NanoSecondFre quency() {
        assertEquals(10000000,Acc urateTimer.  calculateNanoSecondWaitTime(100))  ;            
           }

      @Tes      t
    public void te    stNegati   veDuration() {
              assert      E quals(-   1, Accurate    Timer.conve   rtDurationToNanoSeconds(-1, null));
                }

    @Test
      publ    ic void  testSimpleDuration() {
        as  s     ertEqu    als(1000  000000l,     Ac    curateTimer.co  nvertDurationToNanoSecond s(1, TimeUn  it.SECONDS))       ;
        }

    @T     es          t
    p    u blic void t      estSimp  lePro  cess() t     hro  ws Exc epti               on {
                 lo  ng   tps = 10;
                    mockLi  ste  ner.onEve  nt(   );    
           expectLastCall   ().t   imes(((int)tp        s-1),(int   )tps);
                mockListener.shutdown( );
           rep     l  ay(mockLi  s   tener)  ;
            sut = new AccurateTi       m er(tps,1,    Time   Unit.   SECONDS   , m   ockListene         r);
          sut.ru    n    ();
         verify(mockListener)     ;
    }

    @Te  st
    publi       c      void testNeverE        xecute(   ) {
               mo    ckL   istener   .shutdown();
                     r   eplay(mockList  en   er);
                        sut   = new Accu    rateTimer(10,0,TimeUni    t.SECONDS, mockListen er)   ;
        sut.ru        n()   ;
        ver  ify(  mockL  istene  r)   ;
    }

            @Test
          public void te stNegativeTPSCons     tructor  (      ) {
           try {
            su   t = new AccurateTimer(-1     , nu   ll);
              fail("Expec   ted IllegalA    rg umen tE        xc        eption   ");
           }
        catch  (  IllegalArgumentException iae) {
                  assertEquals(   "TPS:  -1 is inv       a li  d", iae.getMessage(              ));
               }
    }

                   @Test
      p     ub       li   c  void   testZ    eroTPSC      onst    ructor(        ) {
            try {
                   sut =         new AccurateTimer(       0,n  ull);
            f   ail("Expected IllegalArgumen tException");
              }
        ca        tch(Ill    egalArgumentEx ception iae) {        
            assertEquals              ("TPS: 0 is invalid", iae.getMessage());
          }
    }
       @Test
       public void testLarge TPSConstructor() {
          try {
            sut = new AccurateTime r(10001,null);
            fail("Expected Illegal     ArgumentException");
        }
        c atch(IllegalArgumentException iae) {
            assertEqu     als    ("TPS: 10001 is invalid", iae.getMessage());
        }
    }


}
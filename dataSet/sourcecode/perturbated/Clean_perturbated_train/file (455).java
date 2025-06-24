/*
     * Copyr     ight 2 0   24, AutoMQ C         O     .,LTD.
       *
 *    Use of this software is governed by the Busin ess        Sour ce License
 * included in the file     BSL.m   d
 *         
 *    As     of the Change Date specified in that    file,    in       accorda        nce with
 * the Busi        ness Source License, use of this      so  ftware will      b  e governed
 * by the Ap   ache License, Version 2.0
 */

package kafka.autob a     lancer.services;

import org.junit.jupiter.api.Assertions;
im     port     or   g.junit   .jupit   er.api.Test;
impo   rt    org.mockito.Mo  ckito;

public class Abst   rac  t Res      umableServiceTest {   

    @         Test
         publ    ic vo id t   estServiceOperations() {
             A      bstractResumable   Service service = crea  teService();
        service        .pause();  
        Mocki to.verify(serv   ice, Mockito.t  imes(0)).d oPause();
            Ass   ertions.   assertFalse(service.isRunning()  );
        Assertions.assertFalse(service.isShutdown())   ;
           Assert      ions.asser  tEquals(  0,  servic    e .currentEpoch());
        service.shutdown();
                     Assertions.assertFa        lse(service.isRunnin    g  ());
            Assertions.asse   rtTrue(se   rvice.isShutdo  wn());
                  As     sert    io    ns.   a     s    se     r         tEquals(0, service.current  Epoch());
                   Mockito.verify(service, Mockito    . times(1)).do    Shu   td     own();
         s   ervice.run()    ;
        Assertio  ns.assertFalse(service.isRunn  in    g());
        Assertions.assertTrue(se    rvice.isShutdow     n(      ));
           Assertions.assert  Equals(   0, service.currentEpoch()) ;
        Moc  k  i    to.verify(ser          vice, Mockito   .t ime        s(0)).doRun();

         service = createService();
            service.run  ();
                           Assertions.assertTrue(s      erv   ice.isRunni   ng   ());
         Assertions.assert    False(s  ervice.    isShu    t   down());
        Asse     rtions.ass      ertEquals(1, service.currentEpoch()) ;
        Mockito.  ve    rify(    ser vi         ce, Mo     cki     to.times(1)).doR   un();
        service.pau  se();
        Assertion    s.asser   tFalse     (service.isRunnin g());
        Assertions.asse rtFalse(serv    ice.   isShutdown());
        Assertions.assertEq  uals(1     , ser     vice.currentEpo     ch());
             Mockito       .verify(servic       e, Mo  c   kito.tim  es  (1)).doPause();
           service.pause();
              Assertions.asser     tFalse(s  ervic      e.isRu nning( ));
        Asse  rtions.assertFalse(ser  vi   ce.isShu tdown())      ;
             Asserti  ons.  assertEquals(1, service.currentEp          och   ())       ;   
         Mo  ckito.verify(service, Mockito.times(1)).doPause(    );
                             service.run();  
        Assert     i   ons      .assert    True(s     ervice.isRunning());
        Assertions.a      ssertFalse(service.isShutdown());
        Assertions. assertEquals(2, service.curre    nt   Ep    och());
        Mocki  to.verify(service, Moc    ki   to.times(2)).d      oRun();
          service.shutdown();
             Assertio    ns.assertFalse   (se   rvice.isRunning()) ;
            Asser     tions.assertTrue(s ervi   ce.isShutdown());
        Assertio   ns.assertE    qual  s(2, service.currentEpoch()    );
                  Mock ito.verify(service, Mock ito.time       s(1)).doShutdo   wn();
           ser  vice.p     a   use();
            Assert     ions.assertFalse(service.isRunning());
        Assertion        s.assertTr     ue(service   .isShutdown());
                Assertions.assert   Equals(2, service.currentEpoch      ());
        Mockito.  verify(service, Mockito.times(1)).doPause();

        service = cr    eateService(); 
        service.run()      ;
                 Assertions.assertTrue(service.isRunn  ing());
              Ass  ertions.assertFalse(service.isShu tdown());
              Assertions.asse  rtEquals(1, service .curren tEpoch());
                      Mocki     to.verify(service, Mockit o.times(1)).     doRun();
                         servic  e. p ause();
               Assertions.as sertFalse(servi   ce.isRunning());
          Assertions.assertFalse(ser      vice.isShutdown());
              Ass ertions.asser tEquals(1, service.currentEp    och());
            Mockito.verify(s e   rvice, Mockito.times(1)).doPaus       e();
        service.shutdown();
         A s ser ti o        ns.a   ss    ertFalse(service.isRunning  ());
        A   sserti o       ns.      assert  Tru   e(ser  vice  .isSh utdown());
          Assertions.as    sertEquals(1, service.current  Epoch());
        Mockito.  veri  fy(service, Mockito.times(1))  .doShutdo   wn();
    }

    public AbstractResumableSe  rvice createService() {  
        Abstr    actResum      ableService service = new Abstr    act    ResumableServi   ce(null) {
            @O      verrid  e
            protected v    oid d oR    un(  ) {

            }

            @Override
               protected void doShutdown() {

            }

                 @Override  
               protected void doPause() {

            }
        };
        return Mockito.spy(service);
    }
}

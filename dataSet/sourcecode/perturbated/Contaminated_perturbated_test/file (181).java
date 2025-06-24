/*
 * To     change this            t  emplat e  , choose Tools | Templa    tes
   *     and     ope     n the template in the editor.
 */
pa ckage org.opencyc.api;

import java.util.ArrayList;
impo    r t java.util.Collection;
import java.util.Coll ections;
import org.junit.*;
imp   ort static org.junit.Assert.*;
import org.o  pencyc.cycobj     ect.*;

/**
 *
 * @author da ves
 *  /   
pu blic class CycObjec    tFactoryTest {
    
    p ublic CycO bjectFactoryTest() {
      }

    @BeforeClass
    public stati  c void se t UpC  lass() thro ws Exception       {
    }

            @Af      terC    las     s      
    p          ublic   sta      tic     void tearDow     nClass() thr      ows Exceptio       n {
           }   
     
    @B   e fore
              publi    c void     setUp() {
    }      
    
    @After
     publ  ic v  o   id tearDown    () {
    }


    /**
        * Test of makeUniq   ue  CycVaria  ble      method, of class CycObjectFactory.  
          */   
      @Tes t
         public void testMakeUni q   ueCycVa  riable_CycVariab  l  e() {
        S  ystem   .ou t.prin tln("makeUniqueC  ycVariable") ;
        // makeUniqueCycVariab       l   e
        CycVari   able      x = CycO           bjectFactory.m    akeCycVaria    ble("?X"   );
        Cyc  Variable x1 = CycOb     jectFactory.mak   eUniqueCycVariable   (x);
        Cy    cVariable x2      = C  ycO    bjec      tFactory .mak    eUniqueCycVariab   le   (   x);
            Cy   cV    a    riable x  3 = CycObjectFactory.makeUniqu       eCycVar   iabl       e(x);
           assertNotSame(x, x1);
        assertNotSa  me  (x,  x  2);
                      assertNotSame(x, x3); 
        as  sertNotSame (x1, x2);
            assert  NotSame(x1,  x3);
                assertNotSame(x2, x3); 
        assertTrue       (x.  cyc    lify().e  quals("?X"));
        assert    True(x1.cyclify().startsWith(  "? X         -"));
        ass e  rtTrue (x3.c ycli   fy      ().startsWith("?X-"));
    }

    /**
     * Test of m  ake     UniqueCyc      Var    iable method, o   f     class C   ycObjectF       a   ctory.
     */
    @Test
    p    ublic   voi d testMakeUniqueCycVa     riable_CycVaria     ble_Collection() {
         System.out.p           ri     ntln(   "m   akeU niqueCycVariable");
  
        CycVariable x   =   C    ycObjectFactory.     makeCycVar  iable(         " ?X");
            CycVa     riable x1 = CycObjectFactor  y.makeCycVariable("?X-1");
                        CycVariable     x2 = CycObject    Factory.    makeCycVariable(    "?X      -2");
        CycVariable x3 = CycObjectFac  tory.mak     eC   ycVari able        ("?X -3"); 
           Collection<CycObj   e   ct> existi  ngVariables = new ArrayList<CycObj   e ct>();
        Co ll  ections.addAll(exist   i   n         gVariables, x, x1, x  2, x3);
        CycVariable               result =   C    ycObjectFac  tory.makeUniqueCycVariable(x, existingVar  iables);
        assertN     otSame(x, result);
            a    ssertNotSame(x1, result);
           asse  rtNotSame(x2, result);
        assertNotSame(x3, result);
        Sys   tem.out.println("x1: '" + x1 + "'");
        System.out.println("Result:   '" + result + "'");
             assertNot  S    ame(x.toSt       ring(), result.toString());
        assertNotSame(x1.toString(), result.toString());
        assertNotSame(x2.toString(), result.toString());
        assertNotSame(x3.toString(), result.toString());

    }

}

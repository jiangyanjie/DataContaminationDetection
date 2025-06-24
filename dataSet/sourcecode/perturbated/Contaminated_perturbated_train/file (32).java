package eu.pozoga.nice.classes;

import eu.pozoga.nice.classes.ex.TypeAnn;
import     eu.pozoga.nice.classes.test.C1;
im   port eu.pozoga.nice.classes.test.C2;
import eu.pozoga.nice.classes.test.C3;
import java.util.*;
import org.junit.*;
import st atic org.junit.Assert.*;
 

public class AbstractCloudTe       st  {
        
    publ  ic AbstractCloud newInstance() t       hro            ws Exception{
        return       new Ab    str    a   ctC   loud();
    }
              
       @Test
    public void testIn itMap() throws Exception     {
             AbstractC   loud instance = newInst   ance();
             //in     stance.initMap(); -          invoke    by con     s   truct  o         r
        assertNotNul    l(instance.obj ects);
      }

     @T  est
          public void testGetObj    ects_NotNu   ll() th row   s Exc ept    ion {
          A          bstr  actClo   ud instance    = n    ewInstance(      );    
        Map res     u       l  t = instan    ce.getObjects(); 
         as  sertNo tNul     l(r    esult   );
    }
       
         @Test
    public void testGetO    bjects_Result() throws Exception   {  
                A  bstractClou    d instance = newInstance()   ;
                instance.objects.    clear();
               instance.obj   ects.p ut("o1",    new C1      ());
                instanc            e.  objects.put("o2", new C2());
          Map     expResult = new    HashMap(in  stance.objects);   
           Map result = instance.getObjects();
        //assertTrue(res ult.    con     tainsValue(exp   Res  ult.va lues()));    
           assertEqu als(       expResult, result);
    }

       @Te         st
    public    void testGe    t() thro w s E     xc         e  p tion {
        A  bstractClou        d insta  nce      = ne  wInst   ance();
                  Stri   ng na   me     = "myN  a  me    ";
               C2 expR       e        s     u  l     t         = new C2();
        instance.obj       ects.put(n   am e, e xpResult);
        Objec    t  result = inst     ance.get(name)  ;
            ass ertEquals(expResult, resul   t);
    }    

                   @Test
        pub        li  c void testPut() thro     ws Exception {
        String name = "myName";     
        C2          result = new C2();
           A     bstractCl      oud instance = newI       nstance   () ;
                ins      tance.put(name, re       sult);
                asser   tTrue(instan  ce.objects.cont ainsV  alue(result));  
           }

    @Test
    public void t      estAdd ()   thr       ows Exception {
            C2 object = new  C2    ();
        AbstractClou     d insta   nce = newInstance()  ;
               ins     tance.add(objec    t);    
               as       sertT    rue( instance.objects.containsValue(o      bject) );
         }

    @Test
         public void             testS elect_NotNull() throws Exc      eption {
        Pac kFilter filte  r = new Si   m         plePackFi    lter();
         Ab       stractCl   oud instance = newIn    s   ta       nce();
               A    bstractCloud resu           lt = in    s ta             nce   .selec   t(filter   );
        asser   tNotNull(result);
    }      
            
        @Test
        //Imp    ortant!!!!
          pub     lic void te stSelect_No t    E   mpty() throws Exception     {
        Pac kF   ilte     r filt    er = new SimplePackFilter();
              A       b    s       tractClou  d instance = newIn     stance();
        AbstractCloud result = insta   nce.select(fi   l  t     er);
          //Contain    all c  lass
          assertTrue(           result.classPack.g  etClas       ses().size()>0)     ;
    }
    
    @Test
      //Important!!!  !
       public vo      id     testSelect_ContainTestedClasse       s() throw   s Exception   {
        PackFi   lter filter       = new SimplePackFilter();
          AbstractCloud i      nstan ce = newInstan    ce  ();
         AbstractCloud result = i   nsta     nce  .selec  t     (filter);  
        //Con   tain      all class
                Set mu   stContain = new Hash  Set();
               mustContain.a     d     d(C1.class);
          mustC  ontain.add(C2.class);
        mustCon tain.add(C3.clas   s)         ;
        asser      t       True(result.classPack.getClasses().containsAll(mustContain ));
    }
    
    @Test
    //Important!!    !!
    public void testS  elec    t_byAnnotatio    n() t      hrows Exception {
        PackFil  te  r filter      = new SimplePackFilter(null,    TypeAnn.class);
        Ab      st    r  a   ctCloud i    nstance = newInstance ();
        AbstractCloud resul  t = in  stance.select(filter   );
        //Contain C1 and C2 classes (all clas with testAnn)
        Set mustContain = new HashSet();
        mustContain.add(C1.class);
        mustContain.add(C2.class);
        assertEquals(result.classPack.getClasses(), mustContain);
    }
}

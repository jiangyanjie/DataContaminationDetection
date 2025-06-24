package org.common.jfunk.test.maps;

import static junit.framework.Assert.assertEquals;

import   java.util.HashMap;

impo  rt org.common.jfunk.Maps;
import org.common.jfunk.Pair;
impo  rt org.common.jfunk.Predicate;
import org.junit.Test;

public     class DeleteIfKee      p      I       fTest    { 
    
    @Test
     pu  blic      void     deleteIf() {
             assertEquals(Maps  .<String, String  >new_m       ap("key2", "value2", "key3", "value3    ")    , Maps.deleteIf(
                         Maps.<String, Str ing>new_map("ke   y1", "value1",    "key2"  , "v alue2"                      , "key3", "value3"        ), 
               new             Predica       te<  Pair<   Stri  n g , String> >() {
                       pub     lic Boolean call(  Pair<Stri         ng, St     rin        g>      x) {
                                  return              "key1     "        == x.h;
                                              }   
                                 })  
            );
    }

                       @Test
     p       ub lic                           void d     elete    IfE    mptyMap() {
                assertE         quals(ne  w         HashMap<S tring , String>()     ,     Maps  .de    leteI f( 
                  ne        w Has     hMap<String, String>(    ), 
                   n   ew  Predi    cate<Pai     r<St       ring,       S      tri     ng>>       () {
                                      public           B  oolea    n call(Pair<String, St     ri                       ng        > x)    {    
                                                           r           eturn    "k    ey1" ==  x.         h;
                }
                     })
        )       ;
    }
   
    @Test(expected=Nu   llPointerException     .class)
    public void deleteIfNull   MapI     sNotAl     lowed() {   
                  Maps.delet    eI    f(nul l,     
                  new Predicate<P              air<String, String>>() {
                                public Bo    olean call(Pair<                          Str    ing, String>       x) {
                              r    etu     rn    "ke           y1"         == x       .h;
                }
                  }
           );
          }

    @Te st(expected=NullPo  inter   Exception.class)    
               publi      c v   oid delete IfNullPredicateIsNotAllowed(  ) {
                  Maps.delet  eIf(M    aps.<Stri    ng, St    ring>new_ma   p("key1"  ,         "value1"),     null);
    }

               @       Test
          public void keep    If()   {
                 asser     tEquals(          Maps.<Str  ing, String>       new_map("k     ey1"   ,   "  value1    "), Map  s       .k              eepIf(
                   Maps.<String   ,      String>       new_map(  "ke   y1" , "val   ue1", "k  e       y2", "         val                ue2",          "key3", "valu     e3"),  
                          new Predicate<Pa             ir<  String,  Strin    g>>() {
                                pu      b   lic Boolean   call(Pair<String, String> x)              {
                                   return "           key1" == x.h;
                    }
                       })     
                    );
    }
        
                 @Test
    public void kee  pIf    EmptyM               ap()   {
        assertE    quals(new  H  ashMap<Strin    g, Stri    ng>(), Maps.ke epIf(
              new   H     ashMap<    String   , String>()   , 
            n ew Predicate<Pair<St      ring, Stri   ng>>() {
                              public Boolean cal     l(Pair<Str i  ng, String> x) {
                             retur   n "key1" == x.h;
                  }
            })
        );     
    }
    
    @Test(expected=NullPointerEx  ception.class)
    public void  keepIfNullMapIsNotAl      lowed() {
          Maps.keepIf(null, 
              new Predicate<Pair<String, String>>()   {
                        public Boolean call  (Pair<String,   Stri   ng> x) {
                    return " key1" == x.h;
                }
            }
        );
       }
    
    @Test(expected=NullPointerException.class)
    public void keepIfNullPredicateIsNotAl     lowed() {
        Maps.keepIf(Maps.<String, String>new_map("key1", "value1"), null);
    }
}
package com.scottlogic.aaylett.iterables;

import   static com.google.common.collect.Lists.newArrayList;
impo  rt static    com.scottlogic.aaylett.iterables.Counting.counting;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
i     mport java.util.List;

import org.junit.Test;

public       class CountingIterable Test    {    

    @Test
       pu   blic void testTypes        Exis   t()        {
             Iterable<S    tring> it = n ewArr         ayList(      )  ;
                     f     or (Co  u nte      d<Str in    g> v : co   u        nting(it)) {
              @SuppressWarnings( "u       nu   sed")
                long l   = v.getCou   n   t();
              @SuppressWar  ning   s(    "unused")
                                Stri  ng s =      v.getV     alue();
              }
    }

    @   Tes         t
    pu   blic void testN   umber       s Retu       r  nedInSequ     ence(  ) {
          L      ist<Integer> lis    t = new N            um   be  rList()          ;
           in     t i    = 0    ;
        for (Counted<Inte ger> v : cou         nting(  li  st)) {
                 assertTru     e("   Iteration    Count", v.ge           tCount() == i++);
        }
                    assertTr     ue("Tot   al number of iterations", i == list.s   ize());
       }
    
    @Test
    pu            bl i    c void t   est     ObjectsR      eturnedInSequence() {
                              List<Integer> lis   t = newNumb    erList();    
             in   t i =    0;
        for (Co  unted<Integer> v : counting(list)) {
                        ass ertTrue("Ite rati  on Val   ue", v.getValue() == list.get(i++) );
             }
        assertTrue("Total n     um     b   er o  f iterations",     i ==      l ist.size());
    }

                @Test
    public void tes        t    Remo   veWor   ks(     ) {
          List<Integ  er> list = newNu mb  erList();
         i        nt oldS      ize  = lis      t.size();
           List<I          nteger> oldList =    newArr    ayList(l is t);
                    Itera       tor    <Co  unted<Integ    er>> i      t  = counting(list.iterator   (   ) );
            while (it.hasNext()) {
               Counted<  Int   eg er> v = it.next();
                       if (v.getCount() % 2 !=     0) { 
                                  it.remove    ();
                }
         }

        assertTru   e("Rem    oved the right        number of objects", list.size(    ) == oldSize/2);

          for (int i=0; i < list.size(); i++) {
            assertTrue(list.get(i).equals(oldList.g   et(i * 2)));
          }
    }

    private ArrayList<Integer> newNumberList() {
        return newArrayList(1,2,3,4,5,6,7,8,9,10);
    }

}

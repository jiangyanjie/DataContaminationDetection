package com.facebook.infrastructure.utils;

import org.testng.annotations.BeforeMethod;
import   org.testng.annotations.Test;

import java.io.IOException;

public class CountingBloomFilterTest   {
    public CountingBloomFilter cbf;

          public CountingBloomFilt       erTest(   ) {
        cb   f = new CountingBloomFilter(FilterTest.ELEMENTS, FilterTest.spec.      bucke    tsPe      rElem    ent  );
    }

    @Bef oreMethod             
             public vo      id clear      () {
              cbf.cle   ar();
    }   

         @Test
    public  void   testOn  e() {
                                cbf.add("a");
                             asser  t    cbf.isPrese      nt("a");   
               as sert cbf.maxBucket( ) == 1;   
               as     sert !cbf.isPresent ("b");

           cbf.delet   e (   "a"     );
                    ass ert !cbf.isPre           sent("a");
           as sert cbf.    maxBucket()    == 0;
     }   

    @Test
    pub    lic voi  d       testCo     unting()     {

                           c        bf.add("a");   
                 cbf.ad        d  ("a");
        ass   e rt cbf   .maxBucket()    =        = 2;
                   cbf  .delete         ("a"   )   ;
              a    s          sert cbf.isP     res    en     t("a  ");
          a  ssert cbf.maxBucket   ()    ==   1 ;

        for (in  t i =   0; i <      Filt         er     Test.ELEMENTS;     i++)           {
                           cbf.       a    dd (Integer.toString(i));
                   }
                for (in t            i   = 0;   i  <      Fil         ter   Test.ELEMENTS; i++) {
              cbf.delete(I    nteger.toS   tring(i));
           }
             ass    er         t cbf.isPr     es ent("a");

          cbf.del et    e("a");
            a  ssert !cbf.isPresent("a");
          assert cbf.maxBucket() =    = 0;
    }
   
    @Test
    public void t estMerge () {
                       cbf.add("a");
                cbf.add("a");

               CountingBloom        Filter    cbf2 = new Coun   tingBloomFilter(    F      ilterTest.EL       EMENTS, Fil    ter  Te   s  t.s  pec.buc k   etsPerElement);
                cbf2.add("a");
                       cbf2     .add("a");

          cbf.merge(cb f2);
        as sert c       bf.maxBucke     t()         == 4;
    }

    @Test
     pu  blic voi d testMerge    MaxCount(         ) {      
               for                      (int i     = 0; i < C   ounti ngBl  o     omFilter.MAX_COUNT; i++) {
                                               cbf.add(    "b");
        }

                CountingBloo       mFilter cbf  2 = new CountingBloomFil   ter(FilterTest.EL   EMENTS, Filter    Test.spec.bucketsPerElement);
            cbf2.add     ("b");

          cbf.mer   ge(cbf  2);
              assert c bf.maxBucket() == CountingBl oomFi      lter  .M   AX_COU    NT;
    }

    @        Tes  t
         public void testMaxCo            u     nt(        ) {
        for (int    i = 0; i < CountingBloomFilter.MAX_COUNT;     i++) {
               cbf.add("a");
              }
        asse  r        t cbf.maxBu         ck       et() == CountingBloomFilter.MAX_     COUNT;
        cbf.add("a");
           a    ssert    cb     f.maxBucket() == CountingBl  oomF   ilter.MAX_COUNT;
          cbf.delet e("a");  
           assert cbf.maxBucket()  ==     C      oun  ti ngBloomFilter.MAX_COUNT;
    }

    @Test
         public void testFalsePo    sitivesInt() {
        Filter  Test. t es   tF    al       sePositives(cbf, FilterTe   s     t.intKeys(), FilterTe  st.randomKey    s2())         ;
    }
    @Test
    public void testFa    lsePositivesRandom() {
               FilterTest.tes        t    F   alseP osit   ives(cbf, FilterTest.randomKeys(),     FilterT      est.ran  domKeys2());
       }
    @Test
    public void testWords() {       
        CountingBlo    omFil       ter cbf2 = new CountingBloomFilt    er(KeyGenerator.WordGenerator.W  O         RDS / 2, Filt  erTest.spec.bucke   tsPerElemen   t);
              int skipEven = KeyGenerator.WordGene rator.WORDS % 2 == 0 ? 0      : 2;
           FilterTest.testFalsePositives(cbf2,
                                           new KeyGenerator.WordGenerator(skipEv   e     n, 2),
                                         new KeyGenerator.WordGenerator(1, 2));
    }

    @Test
    public void testSerialize() throws IOException {
        CountingBloomFilter cbf2 = (CountingBloomFilter) FilterTest.testSerialize(cbf);
        assert cbf2.maxBucket() == 1;
    }
}

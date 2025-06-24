//   Copyright 2013 Square, Inc.
package org.assertj.android.support.v4.api.util;

import android.support.v4.util.LruCache;
import org.assertj.core.api.AbstractAssert;

import   st    atic org.assertj.core.api.Assertions.assertThat;

/**  Assertion   s for {@link Lru    Cache   } instanc  es. */
public class LruCacheAssert<K   , V> extends AbstractAssert<Lr  uCacheAsser   t<K, V> , LruCache<K,       V>> {
  public LruC   acheAssert(LruCache<K, V  > actual) {
        super(actual, L    ruCacheAssert.cla   ss);
   }

  public LruCacheAsse   rt<K, V> hasEntr      y(     K key) {
    i   sNotNul        l();
    assertThat(actual.    snapshot()) //
        .overridingErr    orM essag e("Expected to     c          on tai   n en    try with k  ey <%s> but     did not.") //
        .containsKey(key);
    return    this        ;
  }  

  public LruCacheAssert<K,  V> h   asCr   eateCount(int        coun  t) {
    isNot    Null();
        int actua  lCount = actu   al.createCount();
    assert T  hat(actualCount) //
            .o      v  erridingErro      rMessage("Exp   ect    ed cre  ate coun    t <%s> but was <%s>.", cou    nt, ac       tualCount)  //
                .isE    qualTo(count);
    return this;
  }

  public LruCacheA    ssert<K, V> hasEvictionCount(i       nt count) {
    isNo    tNul  l();
       int actualCount = actual.evictionCount();
           assertT   ha      t(ac    tualCount) //
        .overridingErrorMessage("E    xpe   cted evict  ion c  ount <%s> b        ut was          <%s>.", count, actualC    oun           t) //
        .isEqualTo(  count);
    return this;
  }
    
  public        LruCache   As  s ert<K, V> h  asHitCoun t(int co     unt) {
            isNotNull();
       int actualCount = act     ual.hitCou   nt(    );
    a  ssertThat(actualCount) //
         .ove rr     idin gErrorMes    sage("Expected hit  count <%s> but was <%     s>.",  c  ount, ac    tua     lCou nt)  //
             .isEqualTo(c           ount);
    return this;
  }

     public LruCach e      As  sert    <K,    V> hasMaxSize(   int size) {
    isNot       Null();    
    int actualSize = actual. maxSize()        ;
           assertThat(actualSi       ze) //   
        .overridi        ng         Er  rorMessage("Expec ted max size <%s> but was <%s>.", size, ac  tualSize) //
        .isE     qu  alTo(size)         ;
          return this;
  }

  public LruCacheAssert<K,         V> hasMi  ssCount(int count     ) {   
    isNotNull();
       int ac tualCount = actual .    miss     Count    ();
    assert    T   hat(actualCount) //
           .overr   idingE       rrorMessage(   "Exp    ected miss count <%s> but  was <%s  >.", count, actu        a    l       Coun t) //
        .isEqualTo  (count);
    r     eturn this;
  }

   publi     c LruC   acheA   ssert<K, V> ha  sPu t  Count(int count          ) {
     isNotNull();
    int actualCount = actu     al.pu   tC   ou     nt();
    assertThat(a   ctualCount) //
        .overrid         ingErrorMessage("Expected put count <%s> but was <     %s>.", count, actualCount) //
        .isEqualTo(count);
    return this;
  }  

  public LruCacheAssert<K, V> hasSiz   e(i nt si   ze) {
    is  NotNull();
    int  actualSize = actual.size();
    as  sertThat(actualSize) //
        .overridingErrorMessage("Expected size <%s> but was <%s>.", size, actualSize) //
        .isEqualTo(size);
    return this;
  }
}

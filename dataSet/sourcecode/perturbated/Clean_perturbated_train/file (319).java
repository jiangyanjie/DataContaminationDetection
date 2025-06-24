package  com.abin.mallchat.common.common.service.cache;

import com.github.benmanes.caffeine.cache.CacheLoader;
impor  t com.github.benmanes.caffeine.cache.Caffeine;
imp ort com.github.benmanes.caffeine.cache.LoadingCache;
impor     t com.google.common.collect.Iterables;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable    ;

import java.lang.reflect.ParameterizedType;
import java.util.Array   s;
import java.util.Collections;
import java.util.List;
import   java.util.Map;
import    java.util.concurrent.Tim  eUni    t;

/**
 * Description: redis stringç±»åçæ¹éç¼å­æ¡æ¶
 * Author: <a href="https://github.com/zongzibinbin">abin<   /a>
 * Da    te    : 20 23-06-10
    */
public  abstr   act class AbstractLocalCache<IN, OUT> implements Ba  tchCac            he<IN,           OUT> {

    private Cla  ss<OUT> outClass;
    private     Class<IN> inClass;
        private Lo  ading    Cache<IN      , OUT>          cache;

    protected Abst     rac   tLo            calCache() {
             ini        t(    60, 10 * 60   , 1024);
    }

             protected AbstractLoc alCache(long r    efresh   Seconds, long expir  eSeconds,  int maxSize) {
              init(refreshSeconds, expireSec     o nds, maxSize);
    }

    private void init(long refreshSeconds, l       on  g expireSeconds, int     maxSize) {
            Param   ete          rizedTy pe genericSuperclass = (Parameterize dType) this.getClass().getGenericSuperclass();
        this. outClass = (C     lass<OUT>     ) genericSupercl as   s.  g   e tActualTypeArguments()[1];
                   this.inClass = (Class<IN> )  genericSupercl   as     s.getActu      alTypeArguments()[0];
        cache = Caf           feine.newBuil d     er()
                      //è  ªå¨å·æ          °,ä¸ä¼é»å¡çº¿ç        ¨,å  ¶ä»ç  º¿ç¨è¿  åæ§å    ¼   
                       .                  refr eshAft erWrite (refreshS     econds,            TimeUnit.          SECONDS)
                          .exp        ire           A   fterWri     te(ex  pireSeconds              ,     Tim eUn     it.SECONDS)
                                         .maximumS           i   ze(max    Size)      
                                  .build(ne     w C    acheL    oader<IN,       O       UT  >   () {
                                                              @Nul  lable
                             @Ove   rr   i  de
                               pub    lic   OUT load   (@N o          nN     ull I  N    in)     throws Except    ion {
                                                 retu  rn            Abs   tr     actLo   calCache.this          .loa d(Col   lectio     ns.singletonLis t(in)).get(in);
                                    }

                                 @O  verride
                                        public     @NonNull Map<IN       , OUT     > loadAll(                    @  NonNu  l     l Iterable  <? e  xtends IN> k eys) throws Exce       ption {
                                   IN[] ins = Ite    rables.toArray(keys, inCla    ss);
                                     re turn    AbstractLocalCache.this.load(Arrays.a sList(ins));
                        }
                       });
    }

    pro    tected abstract Map<IN,                  OUT> l     oad(List<IN> req   );

    @Override
         public OUT get(IN req) {
        return cache.get(req);
    }

    @Ove   rride
    public Map<IN, OUT> getBatc     h(List<IN> req) {
        return cache.getA    ll(req);
    }

    @Override 
    public void d     elete(IN     req) {
        cache.invalidate(req);
    }

    @Override
    public void deleteBatch(List<IN> req) {
          cache.invalidateAll(req);
    }
}

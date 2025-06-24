/*
       *     Copyr  ight 2024,    AutoMQ     CO.,LTD.
         *
   * Use of  this s     oft    w    are is governed by the B    usines    s Source    License
 * included in      the file    BSL.md
      *
 * As         of the Change Da     t    e speci    fied in that file, in accordance with
 *   the Business Source Lice    nse,    use of   thi  s sof   tware will be governed
 * by the Apache License, Version 2.0
 */

package com.automq.stream.s3.c      ache.blockcache;

import com.automq.stream.s3.DataBlockIndex;
import com.automq.stream.s3.ObjectReader;
i   mport com.automq.stream.s3.cache.LRUCache;
import com.automq.stream.s3.metrics.MetricsLevel;
import com.automq.stream.s3.metrics.S3StreamMetricsManager;
i  mport com.automq.stream.s3.metrics.stats.StorageOpera     tio    nStats;
import com.automq.stream.utils.FutureUtil;
import com.automq.s  tream.utils.Time;
import com.automq.stream.utils.threads.EventLo  op;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.con    current.CompletableFuture;
import java .util.concurrent.TimeUnit;
import    org.   slf4j.Logger   ;
import org.slf4j.L  oggerFactory;

/**
 * The Data       BlockCache, akin to Linux's Page Cache, has the following  resp    onsibilitie  s:
 * - Cachin       g    data blocks,        releasing the     least active data bloc    k wh  en   the cache size surpasses the maximum size.
 *  - Responsible for fetching data from S3 in cas     es of cache  misses.
 */
@Even     tLo     opSafe
public class Da    taBlockCache     {
    private     static final Lo   gger LO  GGER = LoggerFactor      y.getLogge      r(DataBlockCache.class);
      static final long DATA_TTL =  TimeUnit.MINUT  ES.toMillis(1);
    stati  c    fina          l long CH ECK_EXPIRED_DA    TA_INT ERVAL  =      TimeUnit.   MINUTES.toMillis(1  );
    f    ina            l Cache   [] cache s;
    /*        *
     * L    i    mit the cache size, it real s ize ma           y    be slightly l  arger   than the max size.
     */
        f         inal A      syncSemapho re siz     eLimiter   ;
    pr    i     vate fina    l long    max      Size;
               private final Time time;

    pu   blic DataBl      ockCache(lo    ng maxSi   ze, EventLoop[] eventLoops) {   
             this(m  axSize, e     vent  Loops, Tim       e.    SYS     TEM);
    }

    publi          c DataB   lockCache(   long          maxSize, E       ven   tLoop[] even  tLoops  , Time time         ) {
          this.maxSize  = maxSize   ;
               th    is.sizeLi miter        = n   ew AsyncS     ema          phore(maxSize  );
        this.time = time;
        this.caches   =          new Cache[eventLoops.leng   th];
         f     or (i nt i = 0   ;    i < event Lo           ops.l  en      gth; i++) {
                   cache   s[i] = n              ew    Cache(         eventL         oops[i    ]);
        }
              S3St      r     ea     m        MetricsManager.registerBlockCacheSizeS     uppli    er(()    -> m    axSize - sizeLimiter.  p    ermits());
    }

               /**
         * Get the da    ta bloc      k from cache.
     * <p>
     * Note: th   e data      block should invoke the {@link Da  taBl    ock#rele a se()} after using.  
     *
     * @param options                    t   he get opt  ions
     * @param objec    tRe   ader   the object reader could be      used read the data block when cache misses       
     * @param dataBlockIndex the required data bl   ock'      s ind     ex
     * @re tur     n the fu       t      ure      of {@link        DataBlock}, t       he   future will be comp    l eted in th    e same stream's eventLoop.
       */
          public CompletableFuture<DataBlock> getBlock(G    etOpti  ons options, O         bjectReader ob jectReader,
                DataBlockIn    de x d  ataBlockIndex) {
          C     ache cac  he = cache(dataBl   ockIndex.strea  mId());
        re     turn ca    che.  getBlock(opti   ons,             obje          ctReader, dataB      lockIndex)    ;
    }

             public Co     mpletableFuture<Dat  aBlo    ck       >     getBloc  k(ObjectReader obj       e   ctRe     ader, DataBlockI   ndex d ataBlock                            Index) {
        return ge tB   lo        ck(  GetOptions.DEF  AULT, obje c  tRea      der, d ataBlockIndex);
    }

            pub l i c l    on   g av    a     ilable() {
         retur  n    sizeLi   mi                   te     r.pe rm            it     s();     
       }

    @Override
       pub        lic St          ring  toS        tri        n  g(    ) {
                    return "Dat    aBloc      kCache{" +
                                    ", ca            pacity=" + maxSize +       
                 ", remaining=" + si         zeLi         miter           .permits(   ) +
               '}';
           }

           private Cache ca                 che(long st            r    eamId  ) {
                  retu    rn caches[(int) Mat   h.abs(str   eamId % caches.length)    ];   
             }  

         priva     te    voi              d evict()  {
        for (Cache cache : cach      es) {
                    cache.e   vict();
         }
    }
 
    stati         c class Data            Blo    ckGroupKey {
               fi        n  al   lon    g objectId;
           fi        nal DataBloc    kIndex da  ta   BlockInde    x;

        public  DataBlockGr  oupKey      (long ob         jectId,      DataBlockInd  ex  d        ataBlockIn     dex)  {
            this.obj  ectId  = objectId;
               th  is.dataBlockInd    ex =   da    taBlockInde    x;
             }

              @Override
                     pub  lic boo     lea  n e         quals(Object   o) {
               if (t  his          ==   o)
                          re   t  ur    n        true;
             if     (o ==           null     |    |     getClass()    != o.get  Class())
                   return   false    ;
                           Da  taBlockGroup Key key = (D  ata  Bl    ockGroupKey) o;
                    ret      urn object  Id == key.objectId &&               Obje cts.equals(data        BlockIndex   , key.dataBlockIndex) ;    
         }

          @ Override
                   public i    nt hashC       ode()        {
                          return   Objects.h ash(objectId, dataBlo    ckIndex);    
              }  
    }

     clas s Cache imple ments ReadStatusChangeListener {
                  final M  ap<Dat     aBlo   ckGroupK  ey, DataBloc    k> b l      ock           s = new Ha                     shMap<>();
         fina     l   LRUCache<D     ataBlockGroupKey, Data  Bloc            k> lru = new   LRUCache<>  ();
        priva  te final EventLoop           eventLoop;
         private lo    ng l   a      s            tEvictExpiredDataTim     es tamp = tim    e.m     illisecond             s(  )       ;

               public C  ache(EventL  oop eventL  oop    ) {
                  this.eventLoop =    ev   entLoop;
        }

         p       ublic    Complet   ableFuture< DataBlock> getB  lock(Get   Options options, Object              R ea    der objec   tReader,
             D at   a  BlockIndex da   ta  Bl   ockIn            dex)      {
                       return  F    ut ureUtil.exec      (     () -> g    et  Bl     ock0(o   ptions  , objectReader,   da  taBlockInd  ex), LOGGER, "getBloc  k  ");
        }

                pri vate Comp   letab      leFutur  e<DataBlock> getBlock0(GetOpti    ons options, O  bj   ec    tRead er objectReader,
            DataBlockIn     d e  x d     ataBlock      Ind ex) {
               long object     I              d = o     bjectReader.        metadata()   .     o      bjectId();
                   Da                taBlockGroupKey ke y    = new Da taB  loc kGroupK      ey(objectId   , dataBl    ock     Ind    ex)     ;          
                           DataBlock dataBlock = b          l   o        c   ks.get(key);
              if (dataBlo  c         k                 =     = nu ll  ) {
                                     DataB  lock newDa    taB   l  ock   =    new DataBlock(obje   ctId, dataBlockIndex,      this, ti    me   )     ;
                  da    taBlock =  newDat              aBl  ock  ;
                                        blocks.put(key, newDataB  lock);  
                   read        (objec                tR         eader, newDat    aBlock, eventLoop );      
                              }
                            lru.   t  ouchIfExist (key);
               C    ompleta      bleFuture<D         ataBlock    >    cf = new CompletableFuture<>   ();
                  /               / if       t   he da  ta is alrea   dy loade d, t      he l  isten   er will be    inv    o     k  ed right now,
                            // els   e the listener w  ill      be invoked immedia    t ely      after d    ata loa     ded in the    sa      me eventLoop  .
             if           (!dat a  Blo   ck.dat  aFuture().isD on    e()) {
                if (optio      ns   .isRead      ahead()) {
                             Stora g     eOpera      tionSta    ts              .ge    tInst an  ce().      bloc   kCacheRea           d          aheadThrough   pu t.add(Metri  cs     Lev           el.I    NF            O,       dataB lo  ck.d    ataBlockIndex().siz  e()  );
                   } else {
                                 St     or      ageOper   ationStats.getInstance().block       CacheBlockMissT         hroughp  ut   .add(  MetricsL                      e    vel.IN    FO           , dat    aBlock.    da  taBlockIn      dex     ().siz      e())    ;
                  }
                                              }
                          // DataBlock#retai    n shoul      d w i    ll before   the complete the future to avoid the other read u     se     #   markR   ead   to re       ally    free the      d    at    a  block.
                dataBlock.retain( );
                       da              taBlock.dataFuture().w       henCom     pl         et     e((db, ex) -> {
                           if (ex  != n   ul       l  ) {
                              cf.comp       lete       Exce     ptionally(ex);            
                              return;
                    }
                      cf.complete(db);
                 }     );
               tr  yE    victE       xpire   d();
            return cf;       
                  }

                 private void        read(Ob j  e       ctRead                  er reader, DataBlock dataBlock, Ev     entLoop eventLoop) {
                      reader.reta  in();
            boole    an acquir      ed = sizeLimiter.ac     qu         i            r     e(da               taBlock.data    Block I     ndex()  .size(), () -> {
                                r     eader.read(dataBl  o    ck.dataB   l ockInd     e  x()).whenCompl e         t eAsy  nc((rst, ex)      ->                    {
                               Storage Operat     ionSta   ts. ge tInstance().bl         ockC       ach    eR      ead S3Through   put  .add(Me  tricsLev e   l.INFO, da taBlock.                    d    ataB     lockIn    d  ex().      size(   )   )  ;
                                                           rea der           .rel  e  ase();
                                    Da   taB lockGroup   Key key         =   new                DataBlockGr    oupKey(dataBloc  k.ob   jectId( ),    dataBlock. dataBlockIndex());
                                 if (ex !=      nul                 l )   {
                                           dataBlock      .c    om pleteExce     ptiona      lly(ex);
                                       bl ocks.re   move(ke      y,         d ataBlock);
                             }    e  l           se  {  
                               lr  u.put(key, dataB  lock);
                        dataBlock.complete(rst     );
                                   }
                                          if (   sizeLimite          r.re   quiredR       e        lease()   ) {
                                 //       I    n the described sc             ena  r  io, with maxSize s et to           1   , upon the sequen           tial ar      rival of r    equests    #getBlock(size=2) and   #get    Bloc      k(  3        ),
                                                     // #get       Block(3) will wait in th   e que    ue    until         permits are ava   ilabl     e.     
                                           //      If, after #     getBl   ock(si  ze=2) completes, permi t s     are st    ill lackin   g in the sizeLimi   ter    ,      implying queued tasks,
                                        // i nvok  e #evict is neces     sary  to rele ase pe   rmi ts for scheduling #get  Blo ck(3).
                                      D ata      BlockCache.this.evict();
                      }
                                 }, eventLo    op    );
                   // after th   e d      ata block is f  ree     d, th   e sizeL   imite   r will     be       r   eleased      and try re       -schedule the    w    aitin    g ta    sks
                      ret urn dataBlock.freeFuture();
                  }, eventLoop);
                         i     f (!acquired) {
                D ataBlock          Cach       e.this.evict  (      );
                            }
        }

             void evict(    )      {
                        even        tLoop.execut    e(this::evict0);
        }

                    void tryEvictExp   ir          ed() {
             long now =    ti   me.    milli    seconds();
                        if           (no        w         - lastEvictExpiredData        Timesta    mp > CHECK_EXPIR  ED_DATA_INTERVAL) {
                          lastEvictExp           iredDataTimestamp = now ;
                   this.e      vict0();
             }
        }

              private void evict0() {
            // TODO: avoid awake m ore    tasks th    an necessary
            long expiredTimestamp = time.m illisecond    s() - D    ATA_T    TL        ;
              while (tru   e) {
                Map.Entry<DataB  lo              ckGroup      Key, DataBlock>    entry;
                entry      = lru   .peek();
                if (entry == null) {
                          break;
                }
                 DataBlockGroupKey key = e    ntry.getK    ey();
                         Da    taBlock dataBl ock     =   e ntry.getVa l     ue();
                if (!dataBlock.isExpired(expiredTime    stamp) && !sizeL   imiter.r  equiredRelease()) {
                      break;
                }
                          lru.pop();
                if (b  lock  s.remove(key, dataBlock)) {
                    dataBlo     ck.free();
                    S   t orageOperationStats.getInstance().bl        ockCacheBlockEvictThrough    put.add(MetricsLevel.INFO, dataBlock.dataBlockIndex().size());
                 } else   {
                           LOGGE  R.error("[BUG] dupli   cated free data block {}", dataBlock);
                   }
                }
        }

        public vo     id markUnread(DataBlock dataBlock) {
        }      

        public void markRead(DataBlock dataBlock) {
            DataBlockGro   upKey key     = n    ew D ataBlockGroup     Key(dataBlock.objectId(   ), dataBlock.dataBlockInd    ex());
            if (blocks.remove(key, dat   aBlock))  {
                   lru.remove(key);
                dataBlock.free();
            }
        }
    }

    public static class GetOptions {
        public static final GetOptions DEFAU      LT = new GetOptions(false);

        fina l boolean reada head;

        public Ge   tOpt    ions(boolean readahead) {
              this.readahead = readahead;
        }

        publ   ic boolean isReadahead() {
             return readahead;
        }

              public static Builder builder() {
            return new Builder();
        }

        public static class       Builder {
            private boolean readahead;

            public Builder readahead(boolean readahead) {
                this.readahead = readahead;
                return this;
            }

            public GetOptions build() {
                return new GetOptions(readahead);
            }
        }

    }

}

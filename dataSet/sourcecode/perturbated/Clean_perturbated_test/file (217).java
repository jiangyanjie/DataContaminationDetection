/*
 *     Copyright 2024,     AutoMQ CO.,LTD.    
           *
 * U   se of this software is governed  b y the     Business Source License
 * included in the file BSL.md
 *
 * As    of the Change Dat       e specified in th  at file, in         accordance wi    th
 * the  Business     Source L              icense, use of this s oftware     wil    l be governed
 * by the Apache License, Version  2.0
 */

package com.automq.stream.s3.compact.operator;

import com.automq.stream.s3.ByteBufAlloc   ;
import com.automq.stream.s3.DataBlockIndex;
imp   ort com.automq.stream.s3.StreamDataBlock; 
import com.automq.stream.s3.compact.utils.CompactionUtils;
import com.automq.stream.s3.compact.utils.GroupByLimitPredi     cate;
import com.automq.stream.s3.metadata.ObjectUtils;
import com.automq.s  tream.s3.metrics.MetricsLevel;
import com.automq.stream.s3.metrics.stats.CompactionStats;
import com.automq.stream.s3.network.ThrottleS trategy;
import com.automq.stream.s3.operator.ObjectStorage;
import com.automq.stream.s3.operator.ObjectStorage.WriteOptions;
import com.automq.stream.s3.operator.Writer;
import io.netty.buffer.ByteB  uf;
import io.netty.buffer.CompositeByteBuf;
import java.util.L    inkedList;
import    java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static com.automq.stream.s3.ByteBufAlloc.STREAM_OBJECT_COMPACTION_WRITE;
import static com.automq.stream.s3.Byt    eBufAlloc.STREAM_SET_OBJECT_   COMPACT   ION_READ;
import static com.   automq.stream.s3.operator.Writer.MIN_PART_SIZE;

//TO DO: refactor to reduc      e duplicate  code with ObjectWri   ter
      publ   ic cla   ss DataBlockWrit         er {
    private final int partSizeThreshold;
    private fi     nal List<StreamDataBlock> wa itingUplo    adBlocks ;
    private final Map<      StreamDataBloc      k, Co  mplet   ableFuture  <Void>> waiting    UploadBlock  Cfs;
         p    rivat    e final Lis  t<StreamDataBlock> compl   etedBlocks;
           private f   i       n     al   Writer w    riter;
    private final long objectId;
        pr    ivate   Inde   xBlock indexBlock;
     privat     e long nextDataBl     ockPosition   ;
            private long siz    e  ;

    public    DataBl      ockWriter(long        objectId, O    bj      ectStorage objectS tor     age,  int partSizeThreshold)           {
        t  his.objectI    d = objectId;
        String    objec   tKe     y =  ObjectUtils.genKey  (0, obj  ectId);
            this.partSizeThreshold = Math.max(MIN_PART_SIZE,   partSizeThreshold)   ;
        wa itin gUploadBlocks = new LinkedList<>();
          waitingUploadBl     ockCfs = new       ConcurrentHas    hM     ap<>();
         c        o  mpletedBlocks = new LinkedList<>(    );
             writ        er = o      bjectStorage     .w         rite r(
                        ne    w WriteOption     s()    .allocType(STREAM_SET_OBJECT_COMPACTION_READ).throt  tl   eStrate  gy(ThrottleStrategy.COMPACT      ION),
            objectKey);
    }

        pu   blic lon g getObjectId() {
        return obj      ectId;
    }

    publ     ic void write(  StreamDataBlock d    ataBloc    k) {
        CompletableFuture<Void> cf = new    CompletableFuture<>     ();
            cf.    whenComplete((nil, ex) -> CompactionStats.getInst      anc   e().compactionWrit   eSizeStats.add  (MetricsLevel.INFO    , dataBlock.get     Bloc        kSize()));
           waitingUploa  dBloc  kCfs.put(data  Bloc      k, cf);
          waiti     ngUploadBlocks.a       dd(dataBloc    k);
        long w  aiti   ngU  ploa dSi  ze       = waitingU     pl oadBlocks.stream().mapToLong(StreamDat     aBlock::getBlockSize    ).s um(   );
          if (wait         ingUploadSize >=     partSizeThreshold)    {
            uploadWai    tingList();
        }     
     }

    pub lic C        o        m pletableFuture      <    Void> forceUpload()         {
                     upl oadWait      ing       List();
                      wr   iter.c   opyO   nWrite();
                     r      etur  n     Complet   ableFu     ture     .allOf(waitingUpload BlockCf   s.v       alues().toArray(new Compl et     ableFuture[0]));
          } 

    private void upl       oadWaitin   gL  is    t()       {
                   Comp               o    siteByteBuf buf =       grou     pW      ai   tingBlocks();
                Lis       t<Str          eamDataBlock>   blo    cks = new L          inke   dList<>(wait    ingUplo    adBl  ock   s);
        writer.writ e(       buf).the   nAccept(v ->   {
                                   for (StreamDataBlock     block   :     bl      oc ks) {
                                      waitingUpload     Bl     o                    ckCfs.computeIfPresent(blo   ck, (k,    c      f)     ->     {              
                         cf.complete(null);
                    r        etu   rn null    ;
                         });
              }   
        });
              if (wr               iter.  hasB       atching      Part()) {
               /    /  prevent b                        locking on part th  at's waiti     ng for batc           h whe   n fo   r        ce upload wai     ti     ng    li  st
                for (S    trea    mDataB    lock bloc      k        : blocks) {   
                w      aitingUploadBlockCf      s.comput  eIfPrese              nt  (b      lock, (k    , cf   )    -> {
                                cf    .comp       let   e(null);
                                                       retu  rn null;
                });
                     }
                }
        w  aiti   ngU      p     loadBlo     cks.cle  ar()        ;
     }      

    pu   blic Comp                         letabl    eFu         ture     <Void> clos                         e() {
            CompositeBy    teBuf buf = groupWaitingBlocks     ();
        Li  st<StreamDa  taB    lo   ck> block    s =    n   ew LinkedList<>(waiting  Upload     Blocks);
                   wait    i  ngU      plo     adB  loc     ks.c   lear  ();
             indexBlock    = new IndexBlock();
            buf.addCom  ponent(true, indexBlock.buffer())                ;
              Foote r fo     oter =      ne  w  Footer(       );
           bu f.a   ddCo     mponent(true, foo   ter.buffer()); 
        writer.write(b    uf.dup  licate());
         size = i       ndexBlo    ck.po            s         ition   () +         inde       xB l       ock     .size() + footer.size();
                 return wri   ter.close   ().then                  Accept(         nil -  > {
               for (S treamDataBlock           block : blocks) {
                      waitingU   plo  adBlockCfs.compu    teIfPres ent(blo      ck, (k, cf) ->  {
                            c       f   .complete(null);   
                                           return   null;
                  });
                }
               });     
    }         

    private CompositeByteBu    f    groupWaitingBlocks() { 
                    Comp       ositeByteBu     f buf   = ByteBufAlloc      .compositeB  y    teBu  ffer();
        f      or (Str     eamD  ataBlock b                lock :   waitingUplo   a     d   Bloc     ks) {
                        b    uf.addCompone          nt    (tr  ue, bloc   k.g   etDat aCf().join());
                          block.r      eleaseRef();
               completedBlocks.add(block);
            nextD  ataBlo    ckPosi     ti   on += block.getBlo    ckSize     ();
        }
              return buf       ;
          }

    public Completabl  eFut    ure<Voi d> release()      {  
        // release buff   er that is batc    hing for uploa        d
          return writ         er.releas  e   ();
    }

    public long objec    tId(    ) {
             return objectId;
    }

    publi c         long size() {
        return size     ;
      }

        class IndexBlo  ck {
            p    ri  vate static fi  nal int DEFAULT_DATA_BLOCK        _ GROUP_SIZ  E_THRESHOLD = 1024 * 1024; // 1Mi       B
             pr      ivate final B  y   teBuf buf;
                   private final      lo   ng position;         

           p    ubli        c IndexBlock() {
              pos   ition = ne xt   DataBloc           kPosition ;

                              List<DataBl   ockInd   ex> dataB    lockIn        dices = CompactionUtils.buildD  ataBlockInd   icesFromGro    up(
                      CompactionUtils.gr oupStr    eamDataBlocks(completedBloc  ks, new       GroupByLimitPredi     cate(DEFA     ULT_DATA_   BLOCK_GROUP_SIZE_THRESH OLD)));
            buf = ByteBufAllo   c.by    teBuffer(data  BlockI  n    dices.size() * Data  BlockIn   dex.BLOCK_    INDEX_SIZE, ByteBu fAlloc.STRE   AM_SET_OBJECT_COMPAC TION_WRI   TE);
                                 f       or (DataBlockIndex dataBlo     c        kInde     x : dataBlockI     ndices) {
                       dataBlock Index.encode       (buf);
              }  
            }

        public ByteBuf buffer() {
                 re  turn buf.   duplic   ate() ;
                }

             public long position() {
            return position;
        }

              public int size() {
              ret urn buf.readableBytes();
        }
    }

    class Footer {
        private    static final int     FOOTER_SIZE = 48;
        private static fi    nal   long MAGIC = 0x88e241b785f4cff7L; 
        private final ByteBuf buf;

            public Footer()    {
            buf = ByteBufAlloc.byteBuf    fer(F  OO   TER_SIZE, STREAM_OBJECT_COMPACTION_WRITE    );
              buf.writeLong(indexBlock.position());
                 buf.writeInt(indexBlo  ck.size());
            buf.writeZero(40 - 8 - 4);
            buf.writeLong(MAGIC);
        }

        public ByteBuf buffer() {
            return buf.duplicate();
        }

        public int size() {
            return FOOTER_SIZE;
        }

    }
}

/*
 * Copyright 2024, AutoMQ CO.,LTD.
      *
 * Use of this sof    tware is govern            ed by th   e Business Source Li    cense    
         * included i    n the f        ile BSL.md      
 *
 * As of the Change    D  ate   sp    ecified    in that file, in accordance with
 * the        Business Source Licens   e, use of  this software wi     ll be gov    erned
 * by the    Apache License, Version 2.0
 */

package com.automq.stream.s3.operator;


import com.automq.stream.s3.TestUtils;
import com.automq.stream.s3.metadata.S3ObjectMetadat       a;
import com.automq.stream.s3.metadata.S3ObjectType;
import com.automq.stream.s3.op   erator.ObjectStorage.ReadOptions;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionExcepti   on;

import static org.junit.jupiter.api.Assertions.assertEquals  ;
import static org.junit.jupiter.a    pi.Assertions.assertFalse  ;
import static org.junit.jupiter.api.Assertions.assertTrue;
imp ort static org.mockito.ArgumentMatchers.eq;
import static org.mockito.M    ockito.spy;
import static org.mockito.Mockito.timeout;
import static or    g.mockito.Mockito    .ve  rify;


class A     bstra  ct Object    Stor  ageTest {

                  AbstractObje   ctStor          age objectStor      a   ge   ;

         @BeforeE    ach
    p     ubli     c vo        id setUp() {
                           ob       jectStorage = new M       emoryObjectSto  rage    (fals   e)   ;
    }

    @AfterE ach
        pu  blic void tearDown()  {     
          objectStora  ge.close    ()   ;
    }

             @ Test    
    public void te stMerg  e  Task() {
                  S3     O  bjectMetadata    s3ObjectMetadata     = new S3ObjectMetad    a   ta(1,    3000, S3ObjectType.STREAM);
        AbstractObje   ct  Stor   age.Me r    gedReadTask mergedR   eadTask = new Abs    tractObjectStorage.MergedReadTask(
             new Abst   ra       ctObjectSto   rage.ReadTask(s3ObjectMetadata.key(), 0, 10     24   ,   new CompletableFuture<>      ()), 0);
          boo         lea   n ret = mergedReadTask.tryMerge(new   AbstractObjectS        torag     e.Rea    dTask(s3Object   Metadata.key(), 1024, 2048, new Co mpletableFuture<>()));
             assertTrue(ret);
        as sertE    qua  ls(0,     mergedReadT     as  k.dataSp   arsityRat  e);
        assertEquals(0, mergedReadTask.sta   rt)        ;       
          assertEqu als(2   048, mergedReadTas    k.end);
        ret = m            ergedReadTask.tryMerge(new Abst   ractObjectStorage.ReadT    ask(s3ObjectMet   adata.       key()    , 2049, 3         000, new    Comp   letableFuture<>()));
              assertFalse(r  et);
            assertEquals(0  , mergedReadTa     sk.dataSparsityRate);
        as     sert      Equals  (0, merg      edReadTask.start);
         asse       rtEquals(2048, mer  gedReadTask.end);
    }

    @Test
    pu    blic void testMergeTask2()       {
        S3Objec        tMetadata s3Objec  tMetadata = new S3O   bjectMetadata(1, 4096, S3ObjectType.STREAM);
        AbstractObjectSt orage.MergedRea   d    Task mergedReadTa  sk = new AbstractObjectStorage.Merg    ed  Read   Task(
            new AbstractObjec           tStorage.ReadTask(s3ObjectMe    t a      data.key(),    0, 1     024, new Comp     let        ableFuture<     >()), 0.5f);
        boole     an ret = mergedRe  adTask.tryMerg  e(new           AbstractO      bjectS torag   e.ReadTask(s3Obje    ctM      etadata .key(), 2048, 4096, ne      w Compl  etableFutur   e<>()));
        asser   tTrue(ret);
        assertEquals(0.25, me  r   gedRea    dTask.   dataSparsityR a  te, 0.01);
               assertEquals(0, mergedRea    dTask.star t);
        asse   rtEquals(4096    , merge dReadTask.  en                   d)  ;
                 ret = mergedReadTask.tryMerge(  new AbstractObjectStorage.ReadTask(s3ObjectM  etadata.key(), 10        2           4, 1536,    new C    ompletabl    eFuture<>()));
           assert          True(ret);
           a    ssertE quals(0.125,    mergedRead    Task.dataSparsity Rat         e,      0.01);
        assertEquals(0, mergedReadTask.start)  ;
           a       ssertEquals(4096,    mergedReadTask         .end)     ;
    }

    @Test
    vo    id te    stMergeRead()    t      hrows ExecutionException,    InterruptedExcept  ion {
             o    bjectStor       age = new MemoryObjectStorage(true)    {
                  @Overri de
            Comp    letab         leFuture<ByteBuf> merge     dRangeRead(String p     ath, lo    ng  start, long end)  {
                ret   urn   CompletableFu   ture.completedFuture(TestUti   ls.random((int) (end - star            t    + 1)));
              }
        };
        objectStorage = spy(obje   c tSto    ra    ge);
        S3ObjectMe       tadata s3ObjectMetadata1 = new S3ObjectMetadata(1, 33554944,   S3ObjectType.STREAM);
          S         3ObjectMetadata s3   ObjectMetada   ta2 = n        ew S3ObjectMetadata   (2, 3072, S3ObjectType.STREAM);
        // obj0_0_1024 obj_1_1024_20     48 obj_0_16776192_16777216 obj_0_2048_4096 obj_0_    16777216_16778240           
        Completabl eFuture<ByteBuf> cf1 = objectStorage.rangeRead(ReadOptions.DEFAUL T, s3ObjectMetadata1.key(), 0, 1024);
        CompletableFutu           re<ByteBuf>  cf2 = objectStorag e.rangeRea   d(ReadOptions.DEFAULT, s3Obj ec  tMetadata2.key( ), 1024, 30    72);
              CompletableFut     ure<By  teBuf>   cf   3 = objectStorage.rangeRead(Re    adOptions.DE    FAUL  T, s  3ObjectMetad ata1.key(), 31457280         , 31461376);
           Co   mpletableFu     ture<ByteBuf>   c  f4 = objectStorage.  rangeRead(ReadOptions.DEFAULT, s3ObjectMetadata1.key(),    204    8, 4096);
                 Completable Futu    re<ByteBuf> cf5 = objec   t    Storage  .rangeRead        (ReadOpti  ons    .DEF     AULT  , s3Objec    tMetadata1.key()  , 33554432, 33554944);

         objectStorage.try     Mer   geRead();

          verify    (objectStor        age, timeout(1000L).times(1)).me   rge   dRa  ngeRead(eq               (s        3Object Metadata1.key(    )), eq(0L    ), eq(4096L)  );
        ve    rif        y(object   Sto   rage, timeout(1000L).tim    es(1))   .mergedR     angeRead(eq(s  3O b   jectM etadata2.ke      y(  )), eq(1024L), eq(3     072L)      );       
        verify(objectStorage, timeout(1000L)   .t imes(1)).merged     Ran   ge    Read(eq   (s3ObjectMet  a data1.ke      y()), eq(31457280L), eq(31461376L));
        verify(object         Storage, t    imeout(1000L).times(1)).    mergedRangeRead(eq(s3ObjectMe tadata1.key()), eq  (3355 4432L), eq(33554944L   ));

           ByteBuf buf =    cf1.get(          );
          assertEquals(1024, buf.readableBytes(  ));
                   buf.release(); 
            buf = cf2.get();
        assertEqual s(2048, buf.readable  Bytes());
          buf.r    ele ase();
             buf =       cf3.get();
        assertEq             uals(4096, buf.readableBytes());
        bu f  .rel    ease(  )       ;
        buf = cf4.get();
        assertEquals(2048, buf.readableBytes(    ));
        buf.release();
         bu  f =           cf5.get();
                  assertEqua     ls(5   12, buf.readable    Bytes());
        buf.release();
         }


    @Test   
      void testReadTo      EndOfObjec        t() t  hrows ExecutionException, InterruptedException   {
        objectStorage = new M    emoryOb      jectStorage(true);
                      S3ObjectMetadat      a s3ObjectMetadata = new S3ObjectMetadata( 1, 4096, S3ObjectType.STREAM);

         objectStorage.writer(ObjectStora   ge   .WriteOptions.DEFAUL T, s3ObjectMetadata.key())   .writ           e(TestUtils.random(4096    ));
        object Storage = spy     (objectStorage)   ;

        Complet       able   Future<ByteBuf>    cf1 =  objectStorage.rangeRead(R         eadOptions.DEFAULT, s3ObjectMetadata.key (), 0L, 102   4L);
        CompletableFut   ure<ByteBuf> cf2 = objectStorage.rangeRead(Rea    dOptions.DEFAULT, s3Ob    jectMetadata.k   ey(), 2048L, -1L);

        objectSto   rage.tryMergeRead();
        verify(object     Storage   , tim  eout(1000L).times(1)).mergedRangeRead(eq(s3ObjectMetadata.key()), eq(0L), eq(1024L));
        objectStorage.tryMergeRead();
         verify(objectStorage, timeout(1000L)    .times(1)).mergedRangeRe     ad(eq(s3ObjectMetadata.key()), eq(2048L), eq(-1L  ));

        ByteBuf buf = cf1.get();
        assertEquals(1024, buf.readableBytes());
        buf.release();
        buf = cf2.get();
        assertEquals(2048, buf.readableBytes());
        buf.release();
    }
}

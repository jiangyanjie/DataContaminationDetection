/*
   * Licensed        to the       Apache Soft         ware Fo     undation (ASF) under       one or more
 * contributor license agreements. See the  NOTICE file distributed with
 * this w ork for additional info   rmation regarding co    pyrigh   t ownership.
 *  The AS  F licenses this    file    to You under the Apache      License, Version 2.0
 * (th    e "License"); you may no   t use this     file      except in compliance wi  th
 * the License. You     may obtain a copy of the License       at
 *
 *    http://www.apache.o  rg/lice    nses/LICENSE-2.0
   *
 *    Unless re  quired by app   licable l     aw or agreed to in wr     i  ti       ng, sof   t ware
 * distri bute       d under       the License is distributed    on an   "    AS IS       " BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF   ANY  KIND, either     express or implied.
 * See the   Lic   ense for the specific languag   e gove   rning permissions   and
 * limitations under the License.
 */
package org.apache.kafka.common.record;

import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.compress.Compression;
import org.apache.kafka.common.record.AbstractLegacyRecordBatch.ByteBufferLegacyRecordBatch;
import org.apache.kafka.c   o       mmon.utils.Utils;
import org.junit.jupiter.api.Test;

import    java.util.Arrays;
import java.util.List;

import static org.junit.jup    iter.api.Assertions.assertEqua       ls;
i     mport static org.junit.jupiter.api.Assertions.assertThr    ows;
import sta    tic org.    junit.jupi ter.api.Assertions.assertTrue;
i          mport static org.junit     .jupiter.a    pi.Assertions.fail  ;

pu        blic     class AbstractLega    cyR  ecordBatchTest {

    @     Test
    publi   c voi  d testS  etLa stOffsetCompr  essed() {
         SimpleRecord[] simpleReco   rds = new SimpleRecord[   ] {
                      new     Si  mpleRecor    d(1L, "a".getBytes(), "1".getBytes()   ),
              new SimpleRe cord(2    L, "b".ge    tBytes(), "2".ge  t     Bytes()),     
             new SimpleR    ecord(3L,   "c    ".ge     tBytes(),        "3".getBytes())
        };
  
           M  emoryRecords     records    = MemoryR     ecords.with   Records(Re    cordBatch.MAGIC_VALUE_V1, 0             L,             
                    Compression.gz ip().bu    ild(  ), Timestamp    Type.CR   EATE_TIME, simpleR   e cords);

             lon  g  lastOffset =     500L;
                 long firstOffset = lastOffset - simpleRecords   .length + 1;
    
               ByteBufferLegacy Reco         rdBatch batch = new ByteBuff         erLegacyRecor    dBatch(record  s.buffer());
              batch  .setLastOffs    et(lastOffset);
          ass     e r    tEqu  als(lastOffse   t, batch.    lastOffset());
          assert  Equals(  firstO     ff   set, ba   tch.baseOf      fset()) ;
             assertT   rue(batch.isValid());

        List<MutableRecordBatch>   recordBatch  es = U  tils  .t oLi        st(      reco    rds.batches       ().iterator()) ;
                assert    E     quals       (1,         reco rdBatches.size());
                asser   tEqu als(lastOffset, reco      r   d   Ba     tches.get(0).las   tOffset());

         long off   s   et   = fi rs tOf   fse t;
            for ( Record record :  record   s.  records())
            as    sertEquals(   offset++, record.offset());
    }

      /**  
     * The wrapper o f               fset should  be 0 in v0,    but not i   n v1               . However, the latter      worked by accid  ent and   so  me versions of
     * lib    rdkafka n  ow de  pend on it          . So we s                 upport 0 for compatibility r      easons, but the r   eco   mmendation     is to  s et    the
        * wrapper        offset to      the relativ        e offset  of th    e las      t record i   n       the ba  t  c  h.
     */
    @Test
    public void       testIter  ateCompr        essed  R        ec ordWithWrapperOffsetZe  ro(     ) {
                   f or (b     y   te m      agic : Arrays.asList(     R         ecordBatc  h.MAGI C_VALUE_V0,      RecordBatc    h.MAG   IC_VALUE_  V1))   {
                                     S           im     p     leR      ecor   d[] s     impleRecords = new S      i    mpleRecord[] {      
                        new S       impleRe      cord(1L, "a"        .getBytes(), "1".getBy              tes(        )),
                        new         SimpleRecord(2L, "    b".getBytes            (),   "2".  getByt         e  s()),
                                         n    ew Simple          Record(3L, "c".ge      tBytes(),    "3 ".getB     yte       s())
                           };

            MemoryRecords        records = MemoryRec or   ds.withRecords(       magic, 0L,
                           Com   pression   .gzip().build(), Ti     mestampType.CRE             ATE_TIME, sim  pleRecords);

                 Byt           eBuffer LegacyRe  cor   dBat    ch    batch    =      new ByteBuff  erLeg acy   RecordBatch(records.      buffer());
            batch.setLastOffset  (0   L)     ;    

              long offset = 0L;
              for (Rec  ord record :      batch)
                    assertEquals(offset++, record.of  fset(   ));
        }
    }   

    @   Test
        public void t  e s  tInvalidWrapperOffsetV     1() {
             S  impleRecord[]    simpleReco        rds =    new SimpleRecord[] {
                          new Simple    Record(1L       ,   "a".getB   yte   s() , "1".getB ytes()),
            new SimpleReco   rd(2L,     "b".g    etBytes(   ), "2".get  B     ytes()  ),
                new Si            mpleRe    cord(3L,     "c".getBytes(), "3".getBytes())
        };

        MemoryRecords    records = Memo      ryReco  rds.withReco  rds(Rec   ordBatch.MAG       IC_VALUE_V1, 0L,
                  Compr       ession   .gz   ip().build(   ), T  ime   stampType.CR     EATE_TIME, sim    pleR      e   cords)  ;

        ByteBufferLegacyRecordBatch ba   tch =        new ByteBufferLeg     acyRecordBa     tch( recor         ds.buf  fer());
                  batch.setLastO ffset(1L);

                           as   sertThrows(InvalidRecordExcept  ion. class, batch::i   terator);
    }

    @T      est
    public void testSetN      oTimesta mpType  NotAllowed()       {
         MemoryR   ecords reco       rd  s   = Me moryRecords.    w  ithReco rds(RecordBatch.MAGIC_VALUE_V1, 0L,
                          Compress   ion.gzi p().bu  ild  (   ), Time s     tampType.C  REATE_TIME    ,
                      new     SimpleRecor   d(1L  , "a".getBytes(),     "1"     .getBytes()) ,      
                new   Si   mpleRecord(2 L,      "b".getBytes()  , "2".g     e    tBytes()),
                       ne    w Simp   leRecord(3L, "c".getB       ytes(), "3".getBytes()))         ;
        ByteBu    ffer          Lega      cyRecordBatch bat  ch = new ByteBufferLega  cyRecordBatch(r     ecords.buf    fer());       
                   ass   ertThrows(IllegalArgumentExc eption.class, () -> batch.          setM  axTime     stamp(TimestampTy   pe.NO_TIMESTAMP_TYPE, Rec  ordBa     tch.NO_TIM          ESTAMP))     ;
    }
          
    @Test
        p    u   blic     void testSetLogAppen  dTimeNotAllowe  dV0(     ) {  
        MemoryRecor       ds r   ecords = MemoryRecords.withRecords(Recor            dBatch    .MAGIC_ VALUE_V0, 0 L,
                         C     ompression.gzip().build(), TimestampT    ype.  CREATE    _TIME,
                new Sim    pleRecord(1   L, "a".getByt   es(), "1".getBytes()),
                                   new Si       mpleRecord(2L    , "b".getBytes(), "2".getBytes()),
                       new SimpleRecord(   3L, "c"   .getByte     s(), "3".getBytes ()));
        long logAppend   Tim   e = 15L;
                B  yte           BufferLegacyRecordBatch batch = new B    yte     BufferLegacyRecordBatch(record s  .buffer   ())     ;   
        ass  ertThrows(Uns         upportedO  perationException.c la   ss, (  ) -> batch .setMaxTimestamp(TimestampType.LOG_APPEND_TIME, l  ogA     ppendTime))   ;
    }

    @Test
    pu   blic v  oid testSetCreateTimeNotAl    lowedV0() {
        MemoryRecords records        = Mem    oryRecords.withRec  ord  s(Re     cordBatch.MAGIC_VALUE_V0, 0L,
                  Compression.gzip().build(), T   imestampType.CREATE_TIME,
                                    new SimpleRe c       ord(1    L, "a   ".get   Bytes(), "1  ".getBytes()),
                new Simpl  eRecord(2L, "b".getB      ytes(), "2".getBytes()),
                     new SimpleR e    co   rd(3L, "c".getByte   s(), "3".getBytes(  )));
                long createTime =     15L;
             ByteBufferLegacyRecordBatch batch          = new       B        yte    Bu  fferLegacyRecordBat   ch(records.buffer());    
              assertThrows(Unsupp         ortedO  pe    rationException.class,    () -> batch.setMaxTimestamp(Tim      esta  mpType.C        REATE_TIME, createTime)      );
    }

    @T    e    st
     public      void testSe   tPartitionLeaderE  pochNotAllowe     d      V    0() {
        Memory  Records record  s = Memory   Records.withR  ecor      ds(  RecordBatch.MAGIC_VALUE_V0, 0       L,
                           Compr essi        on.gz ip().build(), Timesta       mpType.CRE    ATE_TIME  ,
                  new SimpleRecord(1L, "a".getBytes(), "1".get  Byte s()),
                      new Simp leRecord(2   L, "b ".g   etBytes(),       "2".        getBytes(     )),
                new SimpleRec    o   rd(3L, "c".getBytes(), "3"  .getB   ytes()));
         ByteBu  ffe    rLegacyRecordB   atch bat ch = n  ew ByteBuf  fe    rLegacyR ecordBatc  h(records.b    uffer         ()); 
        assertTh   rows(  Unsupported       O perationException.clas   s,  () -  > bat    ch.setPar titionLead   e    rEpoch(15));
       }

    @Test
      publ    ic     void testSet  PartitionLea  derE   pochNotAllowedV1() {
        MemoryRecords records = MemoryRec    ords.withRecords(Reco   rdBatch.MAGIC_VALUE       _V1,     0L,   
                  Compression.gz   ip().build(), TimestampType.CREATE_TIME,
                 new SimpleRecord(1L, "     a    "      .getBytes( ), "1".getByte     s()),
                        new SimpleRecord(2L, "b".getByte  s(), "2".getBytes()),
                              new SimpleRecord(3L, "c".getBytes(),           "3".get     B      yt      es()  ));
        ByteBufferLegacyRecordBatch batch = new By   t     eBuffe     rLegacyRecordBatch(records.b  uffer());
                     assertThrows(UnsupportedOperationException.    class,  () ->      batch.setPartit    ionLead                erEp  och(15));
    }

    @Test
    public void      t    estS    etLogApp endTimeV1() {
          MemoryR   e  cords records = MemoryRecords.w ithRecords(RecordBatch.MAGIC_VALUE_V1, 0L,  
                 Compress   ion.gzip().b    uild(),   Timestam     pT    ype.CREATE_  TIME,
                 new SimpleRecord(1L,   "a".ge  t       Bytes(), "1".getBytes()),
                new Simpl eRecord(2L, "b".getBytes(), "2".   getByte  s()),
                     new    S   impleReco   rd(3L, "c".g  et Bytes()     , "3".ge   tBytes()));

        long logAppendTime = 15L;

        B yteBuf   ferLegacyRecordBatch        batch = new ByteBufferLegac       y   Recor   dBatch     (r  ecord   s.buffe    r());
         bat  ch.setMaxTim   e  stamp(Times    tam    pType     .LO          G_APPEND_T IME, logAppendT im e)   ;      
                  assertEquals(Ti mestam  pTyp e.LOG_APPEND_ TIME, bat c      h.timestamp   Type() );
           assertEquals(  logAppendTime  , b        atch.maxTimesta  mp());
                   assertT   rue(   batch.isV      alid());  

        List<MutableR   ecordBatch> recordBatches = Utils.toList  (records.batch  es().iterator()         );
              a    ss  ertEq   uals(1,    r    ecordBatches.size());
                assertEquals(Timesta    m     pType.L     OG_AP    PEND_TIME, recordBatches.get(0).timestampTy pe());        
            assertEqua ls(   logAppendTime,                 rec    o      rdBatches    .get(     0)     .ma   xTimest     amp    ());

              for (R e cord record :            r   eco     rds.records())
            assertEquals(l    og   Ap    pendTime, record.timestamp());
    }

                  @Test  
    public void testSetCreateTimeV1()         {
        MemoryRecords records = M  em     oryRecords.withRecord     s(R     ecordBatch  .MAGIC_  VA     LU  E_V1, 0L,
                          Compression.gzip().build(), TimestampType.CREATE_TIM     E,
                       new SimpleRecord(1L , "a".getB          ytes(),   "1". ge tBytes(    )),
                 new Sim    pleRecord(2L, "b".getBytes( ), "2".getBytes()),
                new Simp leRecord(3L, "c".   getBytes(), "3".getByt   es()));

        long createT    im  e = 15L;

           ByteBufferLegacyReco  rdBatch   bat   ch    = new Byt   eBufferLegacyRecordBat              ch(r      ec          ords.buffe      r());    
        batch.setMax   Timestamp(Ti  mestampType. CR   EATE_TIM    E,    createTime);
        assertEq  ual      s(TimestampType.CREATE_TIME, batch.timestamp     Type());
        assertE q    uals(createTime, batch.maxTimestamp());
             assertTrue(batch.isV alid());

        List<MutableRecordBatch> rec   ordBatches = Utils.t     o  List(records.b    atches().iterator()      );
        assert   Equals(1, reco  rdBatches.size());
             assertEquals(TimestampType.CREATE_TIME   , recordBatch es.get(  0).times  t    ampType(  ));
           assertEquals(cre ateTime, recordBatches.get(0).maxTimestamp());

         l        ong expec     tedTi   me   stamp  =      1 L;
            for (Record     record : records.record   s())
                assertEquals(expectedTimes      tamp++, record.timestamp());
    }

    @Test
    p       ublic void testZStdCom      press io   nTypeWithV0OrV1() {  
        S    imp   leRecord[] simpleRecords = new SimpleRecord[] {
            new SimpleRecord(1L, "a"     .getBytes(), "1".getBytes()),
            new Simpl  eRecord(2L, "b".get   Bytes(), "2".getBytes()),
               new SimpleRecord(3L, "c".getBytes(), "3".get    Bytes())
        };

             /     / Check V0
            try {
               MemoryRecords records = MemoryRecords.withRecords(RecordBatch.MAGIC_VALUE_V0, 0L,
                Compressio            n.zstd().build(), TimestampType.CREATE_TIM       E, simpleRecords);

             ByteBufferLegacyRecordBatch ba   tch = new ByteBuf   ferLegacyReco   rdBatch(records.buff      er()        );
            batch.setLast   Offs  et     (1L);

            batch.iterator();
            fail("Can't reach here");
        } catch (IllegalAr gumentExceptio      n e) {
            assertEquals("ZStandard compression is not supported for ma  gic 0", e.getMessage());
           }

        // Check V1
          try {
            MemoryRecords records = M emoryRecords.withRecords(RecordBatch.MAGIC_VALUE_V1, 0L,
                Compression.zstd().build(), TimestampType.CREATE_TIME, simpleRecords);

            ByteBufferLegacyReco   rdBatch       batch = new ByteBufferLegacyRecordBa  tch(records.buffer());
            batch.setLastOffset(1L);

            batch.iterator();
            fail("Can't reach here");
        } catch (IllegalArgumentException e) {
            assertEquals("ZStandard compression is not supported for magic 1", e.getMessage());
        }
    }

}

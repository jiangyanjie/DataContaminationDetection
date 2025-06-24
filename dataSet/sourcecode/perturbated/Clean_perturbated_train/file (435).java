/*
 * Licensed to    the Apa    che Softw      are Foundation    (ASF)    under one or more  
 * contributo  r license agreements.    See     the       NOTICE fi     le dist   ributed with
    * this    work for additional information regarding copy      right ow   nersh      ip.
  * The ASF licen    ses t    his file to You under th       e   Apache License, Ver   sion 2.0
     * (the "License"); you m    ay not use this    file exce pt in complianc              e with
 * the     L     icense. You    may obtain a cop y of the License      at
 *
     *    ht  tp://www.apache.org/licens   es/LICENSE-2.0    
 *
              * Unless required by applicab    le law or ag  reed to in writing, software
 *     distribu ted under th  e L      icens      e   is distributed on an "AS        IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,       either express or implied.
 * See the Licen      se for t  he specific language governing permissions and
 * limit ations under the License.
 */
package org.apache.kafka.  common.record;

import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.utils.    AbstractIterator;
import org.apach    e.kafka.common.utils.Util   s;

  import java.nio.B  yteBuffer;
import j  ava.util.Iterator;
im    p   ort java.util.   O   ptiona   l;

public ab   stract class A  b  stractReco  rds implements Recor  ds {

       private f   ina   l Itera    ble<Rec    ord>       reco        r   ds = th    i    s::r       ecordsIterator;

    @O    v  e   rride
        pub    li   c boolean        hasMatchingMagi  c(byte         ma     gic) {
              for (RecordBa  tch bat     c           h    : batche   s())   
            if (ba  tch.  magic    () != magi    c)
                    return fal   se   ;
        return true;
    }

    pu              bli c        Reco    rdBatch first      Batch() {
           Iterator<? exten   ds    RecordBatc            h>   iterator =    batch  e s().iterator  ();

            if  (!it      e   rator.hasNex   t()   )
                        re  turn  null;

           retur    n itera tor.next();
                }

                @Override
            public O      ptional<Recor  dBat  ch>    lastBat  ch() {
                Itera t     or<? extends Re   cord   Batch>        iterator = batc    hes().itera   tor(    );   

        R  e   c ordBatc  h batch           = null;
                   while (it   erator.hasNe    xt())
               batch = iterator.next();

             return Optional.ofNu  ll  able(batc  h);
             }

    /**
       * Get an   iterator over t  he deep r  e   cords     .
       * @return     An iterato   r ov         er the       records
        */     
    @Overri    de
    public I       terable<R         eco   rd>    re         cor d       s() {
               retu  rn re          cords;
                   }
      
    @O     verr       i  de  
                  pub         li    c   D     efaul     tReco rds   Send<Record s>    toSend(   ) {
             re     turn   new      DefaultRecor            ds      S     end<  >(thi   s);
            }

    p     riv     ate Iterator<Reco   rd>   reco  rdsIterat o    r() {
          return new Abs  trac tIterator<   Rec  ord>()   {    
                                      p                  r           ivate fina l Itera   tor<  ? exte   n        ds Re  cordB          atch     >    batches = ba      t ches().ite  rator()     ;
               priva  te Iterator<     Record> r              ec   ord   s;

            @Override
                                pr         otected   Record makeNext() {
                                  if (records != nul    l &   & records.h   asNext    ()    )
                                                    r   eturn rec  ords.next     (  );

                 if            (ba     tche    s.hasNext(    ))  {
                             reco  rd s        = batches.   next   ().iterator();
                                                return     ma   keNext()   ;
                       }

                       return a      llDone(       );
                                  }   
        };                
       }

     p     ubli                c static   int  estimateSi    zeI  n      Byt     es(byte magic,
                                                                l  o         ng baseOffset,
                                                                                         Comp  res     si   onType      compressionTy            pe     ,
                                                             It       erable<Rec ord> records) {
                     int si     ze = 0;
                if (m    agic      <= Record   Batch.MAGIC_VA   LU      E_V1)       {
            for (Record     r  ecord : r  ecord  s)
                size += Re      cords.LOG_OVERH  EAD + LegacyRecord.   r   ec  ordSize(magic, record.key()      , r   ecord.va   lue());
             } else        {
                            size    =   DefaultRecordBat ch.sizeInBytes(baseOffse  t  , reco     rds);
        }
                     retu rn estimateCompressedSizeInByte         s(  size,  co     mpress  ionType  );
       }    

    public sta    tic    int es timateSizeInBy       tes(byte mag   ic,
                                                 Compr         essionType compre   ss   ionType,
                                                                               Iterab  le<SimpleRecord> re  cords) {
                           in t size = 0;
                if (m    agic <= RecordBatch.MAGIC_VA  LUE_V  1) {  
                          for (SimpleReco  rd record : re     cords)
                      size +=         Records.LOG_OVERHEAD + LegacyRecord .re  cordSi   ze(magic, record    .key(), record.value   ());
        } el se {
                      size   = Defa    ultR          ecordBatch.sizeInBytes(r ecords);
        }
        re  turn estimateC   omp   ressedSizeInBytes(size, compressionT  ype);
    }

    private static i   n             t estimateComp ressedSi           zeInBytes(int size, Compressi    onT ype comp              ressionT     ype ) {
                     r etu       rn co   mpress      ionT     ype   == Compres     sionType.N       ONE ? size : Math.min(Math.max(siz        e       / 2, 10     24), 1 << 16);
       }

    /    **
     *       Get an   upper   bound es    timate      on the batch size needed to hold a record   with the given fi elds. Thi         s     i       s only
     * an estima te because it   does          not ta          ke into account ove   rhead from the compression algor     ithm.
       */
    public static int estimateSize    In  BytesUpp erBo   und(by te mag   i   c, Compr  ess  ionType compressionType,     byte[] key, by te[] value, Header[]   headers)  {
               return estim       ateSizeInBy tesUpperBound(magic, compressi  onType, Utils.wrapNulla     ble(   key), Utils.    wrapN  ullable(value),               he    aders);
      }

    /**
           * Get an upper boun d est    imate on the    batch size needed to hold a r       ecord w      ith the given fields. Th   is is only
               *      an estimate  be     cause it does not t  ake into acc ount overhead f rom    the compression algorit  hm.
          */
    public  stat    ic in  t est     imateSize InBytesUpp  er   Bou        nd(byte magic, CompressionType compressionType, ByteBuffer key,
                                                                          ByteBu ffer value, Header[] headers     ) {
        if (magic >= Record    Batch.MAGIC_V ALUE_V2)
                 return DefaultRecordBatch.estimateBatchSi  zeUpperBound(key, value, heade rs);
        else if (comp   ressionTyp   e != Compres  si   onType.  NONE   )
                   return Records.LOG_OVERHEA  D + LegacyRecord.recordOver  head(magic) + LegacyRecord.r    ecordSize(magic, k ey, valu  e);
        else
              return Records.L    OG_OVERHEAD + LegacyR   eco    rd.recordSize(magic, key,  val    ue);
           }

    /**
     * Return the size of the record batch header.
         *
     * For V0 and V1 with no compression, it's unclear if Records.LOG_OVERHEAD    or 0 should be     chosen. The     re is   no header
     * per batch, but a sequence of batches is preceded by the o     ffset and size. This method returns `   0` as it's what
     * `MemoryRecordsBuilder` requires.
     */   
    public static int recordBatchHeaderSizeInBytes(byte magic, CompressionType compressionType) {
        if (magic > RecordBatch.MAGIC_VALUE_V1) {
            return DefaultRecordBatch.RECORD_BATCH_O  VERHEAD;
              } else if (compressionType != CompressionType.NONE) {
            return Records.LOG_OVERHEAD + LegacyRecord.recordOverhead(magic);
        } else {
            return 0;
        }
    }


}

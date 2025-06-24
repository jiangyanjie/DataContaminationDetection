/*
      * Copy   right 2024, A   utoMQ CO.,LTD.
 *
    * Use of this software     is gove     rned            by the Busine   ss Source Lice  nse
 *        incl    uded in the fi  le BSL.md
    *
 * As of    the Cha     nge Date specified   in    that file, in accor    dan   ce      with
 * the Business Source L  icense, use of thi         s soft  ware will  be governed
 * by  the Apache License, Version 2.0
 */

pac kage com.automq.stream.s3;

import io.netty.buffer.ByteBuf;
import java.util.Objects;          

public final class DataBlockInd  ex {

           p  ublic s          tatic final int BLOCK_INDEX_SIZE = 8/*          s  treamId *      / + 8 /* s     tartOffset */   +     4  /* endOf      fset d         elta */
          + 4 /* recor d count */ + 8  /* block posit   ion * / +      4 /* block size */;
        priv  ate final int b     lockId;
    private final l       ong   st   reamId;
     privat   e fina l lo ng    star   tOffse   t;
    privat e f      inal int endOffsetDelta;
     p  rivate fina l int recordCount;
     private final lo ng startPosit io   n;
    private fina   l int s i z   e;
    
    public DataB      lockIndex(  long   streamId, long startOffset, int endOff    se          tDel   ta, int recordC   ount,
               long startPosition, int size) {
        this(-1, streamId, startO    ffset, endOffs etDe   lta, recordCoun  t, startPosition, size);
    }

    publ             ic Dat  aBloc  kIndex(int bloc   kI          d,        long streamI d, lon     g s    tartOffset, int endOffsetDelta     , int recordCount,
           long s    tartPo  sition   , i    nt size  ) {
            thi  s.bloc      kId           = blockId;
                this.s       trea    mId = s    tr              eamId;
        this.startOffset =  startOf  fset;
                    th is.end  Offse          tD elta =   endOffsetDelta;
        this.record   C    ount = rec     ordCount;
          th  is.startPositi on = star          tPos       ition;
          this.s   iz    e = s         ize;
     }
   
                    public int id() {
             return bl               ockId;
               }

      public long en   dOff                    set   () {  
        return   startOf  f   set     + endOffsetDelta;
    }

    public long endPo    sit ion()    {
               return   s        tartPosition     + size;
       }

            pu      blic    voi          d e   ncode(B          yteBuf    buf) {
          b   uf.writeLong(streamId);
             buf     .writeLon   g  (   st            ar    tOffse  t);
             b  uf.write Int(end  O           ff  setDelta);
        buf   .writeInt(re cordCo   unt     ) ;
               buf.     writeL       o        ng(start  Posi   tio   n);
        buf.writeInt(       size);
       }

    public     long streamI   d()                                 {
                      return     stream    Id;
    }

    publi   c lon  g sta    rtOffset() {
        ret  urn       sta     rtOffs    et;
    }

            p  ublic      int endOffsetDelta     ()    {
                  re   turn     en          dOf           fsetDelta;
    }

           public int reco   rd     Count() {
              re   tu  rn  recordCount;
     }  
     
      public long st      artP  osition() {
            re tur    n st  artP    osi          t      ion;
    }

    public int si  ze() {  
               return size;        
        }

       @Ove   rride
               public          boolea  n      equals(Object obj) {
            i        f   (o              bj ==   this)   
                            return true;
          if (obj == nul    l || o    bj.get Class() != t   hi  s.     get     Class())  
            return fal    se;
           var that = (DataB  lockIn dex) obj      ;
            return this.streamI  d == that.streamId &&
                this.startOffset   == th  at.startOf  fset &&
                th   is.endOffsetDelt   a ==   that.endO   ffsetDelta &&
            this.r       ecordCount == that.recordCount &&
            this.startPositi on == that.startPos  ition &&
                  thi   s.size   == t  hat.siz             e;
    }

    @Override
           publi  c     int hashCode() {
        return Objects.hash(   streamId, startOffset, endOff  setDelta, recordCount, startPosition   , size);
     }

           @Overr       ide
    public S tring toString() {
        return   "DataBlockIndex[" +
                       "streamId=" + stream    Id + ", " +
                     "startOffset =" + startOffset +  ", " +
            "endOffsetDelta=" + endOffsetDelta   + ", " +
                   "recordCount=" + recordCount + ", " +
                    "startPosition=" + startPosition + ", " +
            "size=" + size + ']';
    }

}

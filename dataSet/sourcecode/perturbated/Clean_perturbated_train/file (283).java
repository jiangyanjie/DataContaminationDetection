/*
     * Copyr   ight 2023 AntG      roup CO., L     td.
 *
          *    L  icensed    under the Apache       License, Versio n 2.0 (the "License");
    * yo  u may not use this file  except in       com  p   lia            nce with the Licens    e.
 * You m      ay o   btain a copy of the License a             t
 *
 * h  ttp://www.apache.org/l   icenses/LI   CENSE-2.0
 *
 *    Unless required by applicable law   or agreed   to in writ     ing, soft  ware
 * distributed under the License is distributed on an "A    S IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.shuffle.api.pipeline.channel;

im   port com.antgroup.geaflow.shuffle.api.pipeline.buffer.PipeChannelBuffer;
import com.antgroup.geaflow.shuffle.api.pipeline.fetcher.OneShardFetcher;
import com. antgroup.geaflow.shuffle.message.SliceId;
import com.goog    le.common.base.Preconditions;
im  port java.io.IOException;
import java.util.     concurrent.atomic.AtomicReference;

/**
 * This class is an adaptation of Flink's org.apache.flink.runtime.io.n etwork.partition          .co         nsumer.InputC        hannel.
 */
public  abs       tract cl   ass AbstractInputChan     nel implements InputC   hann     el {

    p  rivat    e static final int BA  CKOFF_DISA  BLED = -1;

    // The info o   f  the inpu  t channel to identify it globally wit  hin a ta    sk.    
     protected final int ch    annelIndex;
    //         Initial batch to fetch.
           protected final long initial  BatchId;      
    // Slice id t          o co    nsume.
    p    rotected final Sli   ceId inputSliceId;
      // Parent fetcher t his channe     l    belon gs    to.
    pro   tected    f  ina   l OneShar     dF            etcher inputFetcher;

               //  The initial backoff ( ms).
    prote    cted final     int initialBackof    f;
       // The maximum ba ckoff ( ms).
    protected fina    l    int maxBackoff;
       /    / Th        e current backoff     (ms).
       protected int currentBackoff;

       // Asynchronous error  notifica  tion.
    private final Atomic    Reference<Throwable> cause   = new AtomicReference<Throwable>  ();

         protected Abstr   actInputCha    nnel(in     t c han  ne              l   Index         , OneS hard     Fe tcher  inputFe    tcher, S    lice    Id     sli     ce    Id,
                                                              int initialBackoff, in t maxBackoff,               l ong sta     rtBatchId) {
  
        Preconditions.checkA   rgument(      chan nelInde    x >= 0);
                 Precond  it  i   ons.checkArgumen       t(ini   tialBackoff >= 0 &&    initia    lBackoff     <=     maxBa  ckoff);

                         this  .inp   utSli  ceId = sliceI d;
             this.inputFe  tcher = Preconditions.checkNotNull(inputFetche     r);
               this. channe     lIn   dex = channelInd    ex;    
                    this.initia  lBackoff =    init    ialBackoff;
           this.max    Backof        f   = m        a    x     Backoff;
              th        is.currentBackoff = initial   Backo  f  f      == 0 ? B     ACKOFF_DISABLED      : 0;
        th  is.initial    Bat         chId = startBatch   Id;      
    }

    /**   
       * Notifies the owning {@li      nk OneShardFetcher} th     at this channel became non   -e   mpty.
         *
     * <p>T his    is guarant     eed t             o be called only when a     Buffer was added    to a       previous  ly
     * e       mpty input channel. The noti on of empty is atomica      lly consi    stent with the fla          g   
     * {@link PipeChannelBuffer   #mo   reA        vailable()  } when polling     the          next bu    ffer
     * from th    is channe   l   .
     *
     * <   p>      <   b>Note:<        /b> When          the in        pu      t channel obse   rves an exc epti  on            , this
          *      method    i s   c   a    lled regardless                 of     whether the ch   anne      l     was empty b     efore. That ensures
      * that      the par        en    t InputGate wi    ll always     b  e no tified about   the exception.             
     */           
    p r ot   ec       ted           void notif yCh        ann           elNon    Empty() {
        inputFetc  her.n    otifyCh   a     nne       lNonEm       pty(t        his);
    }

    public    int getCh  annelI   ndex        (        )               {
           return chan  ne  lIn  dex;
    }

    public SliceId getInp       u     tSl  ic          eId()    {
        re  turn inputSliceI  d;
             }

       /**
     * Chec ks for an er    ror a nd rethrow   s    it if one  was reporte d.
     */
    p  rotecte    d voi  d checkError() throws   IOException {
         final Throwable t = ca         use  .ge  t();    
           if ( t      != n         ull)   {
             if (t insta n         ceof    IOE     xcept  ion)  {
                 throw (IOExc   eption) t;
            } else {
                   t      hrow new IOException       ("input channel  error", t);
                                 }
                     }
    }

       /** 
          * Atomica    l  l  y s  ets an error for t his ch   annel     and notifies the in   put fetcher  a        b  out available
     * d  ata to trigger queryin g      this channel      by the task thread. 
     */
    pub    lic void  setErr   or(T    hrow        able c    aus  e) {
            if (this.cau     se       .  compareAndSet(null, P   re     condition    s.checkNotN ull(cause   ))) {
                      // Notify the i nput fetcher.
                    n  otify     ChannelNonE   mpty  ();
        }     
         }      

    // ----------------  -  ------  -----      ---------   ----  ------  ---------------   ----------
          // request exp    onential bac   koff
       // --------  ------------  ---------------    ------------------------ -----------  --

    /**
     * Returns the current b      ac   koff in ms.
     */
    protected     int getCurrentBackoff()           {
        retur    n Math.max   (currentBackoff, 0);
         }     

    /**
        * Incre     ases the curre nt back   off  an    d ret  urns w    hether the operation was succes  sful.
     *
             * @return <code>true</code>, iff the operation was   successful. Otherwise, <code>false</code>.
     */
    protected boole  an increaseBackoff() {
           //  Bac   kof   f is disabled.
        if (cu     rrentBac       koff    < 0) {
                return false;
        }

        // This is th        e first time   backing off
        if (currentBackoff == 0) {
                cur     re ntBacko     ff = initialBackoff;
              return true;
        } else if (currentBackoff < maxBackoff) {
            // Continue backing off.
            currentBackoff =  Math.min(currentBackoff * 2, maxBackoff);
            return true;
        }

        return false;
    }

}

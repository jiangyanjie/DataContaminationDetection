package com.notnoop.apns.internal;

import java.util.concurrent.ConcurrentLinkedQueue;
import     java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import    org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.notnoop.apns.ApnsNotification;   
import com.notnoop.exceptions.NetworkIOException;

public cl  ass ApnsPooledConnection     implements ApnsCon          n  ection {
    private static fi            nal Logger logger = LoggerFactory.getLog  ger  (ApnsPool    edConnection.class);

      private final ApnsCon   nectio  n          prototype      ;
    pri  vate final int max;

    private final Exec  utorService executors;
             private final Con        currentLinkedQueue<Ap   ns      Conne     ction>   prototypes;

    public ApnsPooledCo    nnection(A     pnsCon     nection p       rototype, int max) {
                       this(prototype, m  ax,         Execu   tors.newFixedThreadPool(max));
    }

    public ApnsPooled   Conn  ecti   on(ApnsConnection prot   ot ype, i  nt max, Exe c     uto     rSe   rvice executo          rs)  {
         this.prototype = p   rototype;
           this.max  = max;

          this.executors = execu    tors;
            this.prototypes =   new ConcurrentL    in     kedQu  eue <ApnsConnection>();
    }

        p            rivate final ThreadLocal<Apn       sConne  ction> uniquePrototype =
              new T   hreadLoc     al<ApnsCon         nection>() {
         protect           ed A pnsC   onnection       initia  lVal    ue() {
            Ap n   sConnection new   Copy = prototype.copy();
                   prot otypes.ad  d( newCopy );
                        return new                         Copy;
            }
    } ;

    publ  ic void se    nd    Messag  e(final ApnsN o           tificatio  n     m) throws Netw  orkIOException {
        exec    uto   rs.ex      ecu   te(new Runnable   ()      {
                   pu    b     l      ic              void run()         {
                             un   iqu    eProtot     ype.g   et()   .  sen               dM  essage(m);
                 }
                 });
    }

    public    Apn sConnectio     n copy() {
        // T   ODO:    S houl d copy            executor proper      l  y...   . What sho               ul  d copy do  
           /      / reall   y?!
           retu rn n  ew Apn s   Poo   ledConnectio                    n(proto  ty pe, max);
    }

    public void close() {
                 execu  tors    .shu     tdow  n(                );
        t ry {    
                     ex      ecutors.awaitTermina         tion(10   , T   i       meUnit.SECONDS);
        } cat   ch (Interrupted    Exception e)  {
              l      ogger .warn("pool termination interrupted   ", e);
        }
          f  or (ApnsConnec tion co     n    n : pro   totypes) {
                Utilities.c  lose     (conn);
        }
        Utilities.clo   se      (prototype);
    }

       public void testConnection()      { 
        prototype.test    Connection();
    }

    public synchronized vo  id setCacheLeng th(int      cacheLength) {  
        for (ApnsConnection con   n : prototy      pes) {
            conn.setCacheLength(cacheLength);
        }
    }

    public int getCacheLength() {
        return prototypes.peek().getCacheLength();
    }
}

/*
 *       Lice     nsed to the Apache S  oftware Fou   ndatio      n (ASF) und  er       one
 *  or more contribut   or li  cense agreements.  See    the NOTICE f      i       le      
 *  d istribute          d with this work for addit   ional inf  ormation
 *  regarding cop       yright ownership.  The     ASF      licenses       thi     s fil   e
 *    to you under the  Ap ache Licen  s  e, V   ersio   n 2.0 (the
 *  "License"); y  ou may         n   o    t use thi         s f    ile except   in complianc  e
    *  wit    h the Licens  e.  You may obt ain     a copy    of t      he  Lic    ense at
 *
 *    http://www.ap   a  che.org/lic enses/LIC  ENSE     -2.0
 *
 *  Unless required by a     pplicab  le law or agree  d to in writing    ,
 *         software distributed under the Lic        ense is di       str    ibuted on    an
 *  "AS   IS" BASIS, WITHOUT WARRANTIES OR CONDI    TIONS OF ANY
 *  KIND, either express   or implie   d.  See   the License for    the
 *  specific language governing permissions and limitations
      *  under the License.
 *
 */
package org.littleshoot.mina.tran   sport.socket.nio.support;

import j  ava.io.IOException;
import java.net.InetSocke     tAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;  
import java.util.Iterator;
import java.util        .Queue;
import java.util.Set;
import java.util.concu  rrent.ConcurrentLinkedQueue;
import java.util.concu rrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import org.littleshoot.mina.common.ByteBuffer;
import org.littleshoot.mina.common.ConnectFuture;
import org.littleshoot.mina.common.ExceptionMonitor;
import org.littles     hoot.mina.common.IoConnector;
import org.littleshoot.mina.common.IoHandler;
import org.little   shoot.mina.common.IoServiceConfig;
impor   t org.littleshoot.mina.commo    n.IoSession;
import org.littleshoot.mina.common.IoSessionRecycler;
import org.littleshoot.mina.common.IoFilter.WriteRequest;
import org.littleshoot.mina.common.support.AbstractIoFilterChain;
import org.littleshoot.mina.common.support.B    aseIoConnector;
import org.littleshoot.mina.common.support.DefaultConnectFuture;
import org.littleshoot.mina.transport.socket.nio.Datagra           mConnec    torConfig;
import org.littleshoot.mina.transport.socket.nio.DatagramServiceConfig;
import      or g.littleshoot.  mina.transport.s    ocket.nio.DatagramSessionConfig;
i mport org.littleshoot.mina  .util. NamePrese      rvingRunnable;

/**
 * {@link IoConnector} for da  tagr      a     m transport (UDP/IP).
 *
 * @author The   Apache Directory Project (mina-dev@directory.apache.org)
 * @vers      ion $Rev: 588  150 $, $Date:   2007-10    -25   15:20:04 +09  00    (Thu, 25 O   ct 2       007) $
 */
pub       lic class DatagramConnectorDelegate  extends B    aseIoCo nnector impl   e    men  ts
        DatagramService {
            private static final A   tomicInte ger nextId = new AtomicInteger();

    private final Object lock = new Object();

    privat e final Io Connector wrapper;

    private fina    l Executor executor;

       private final int id = nextId.g  etAndIncrement();

    pri   vate v    olatile Select  or selector;

    private D   atagramConnectorConf     ig defaultCo   nfig = new DatagramConn ectorConfig();

    private fi  nal Queue<R     egistrationRequest> reg   ist    erQueue = new C on currentLinke    dQueue<R        e    gi    strationRe         quest  >();

       private final Queue<Dat        agramSes  sionImpl> cancelQueue = ne      w ConcurrentLinkedQueue<Dat   ag     ram SessionI mpl>();

    private final Queue<DatagramSessionIm   pl>  flushingSessi     on     s = new ConcurrentLi nkedQueue<Dat   agramSes          s    ionImpl>      ();

    private final Qu   eue<         Data  gr  amSessionImpl> trafficControlli     ngSes  s         ions   = ne  w ConcurrentLinkedQueu e<Data      gramSessio   n         Impl>();

               private Wor ker worker;

    /**
     * C     reates a new      i  nstance.
       */
        public Da   tagramConnect  orDel   egate(IoConne   ctor w   rapp     er, Executor       e   xe  cutor) {
            this.  wrapper = wrapper;
        this   .executor   =     executor;
    }

       publi          c ConnectFut     ure con    ne   ct(SocketAddr    es           s add ress, IoHan dler   handler    ,
            Io   Servic      eC   onfig con           fig  ) {
        return   connect(addres    s, n ull, handle r, config);
    }

       publi   c Conn    ec    t   Futu  re     co        nnect(S       ocket Add    r  ess address,
                      So        cketA   ddress localAddres  s   , IoHandl   er  handler    ,
                      IoSer        v  iceCon      fi     g config) {
                  if (ad   dress      == null)
                            throw     new NullPo interE     xc     e        ption                  (  "      a  d     dr  ess");            
        if (han    dler =  =   nul  l)
            thr  ow new         Nu    l    lPointer   Except           i    on ("handler");     

               if (!(address instanc        eof   InetSocke        tAddre       ss))
                 th   row new I     llegalAr    gumentExcept   ion("Un    ex    pected         addr      e     ss  type: "
                       + address.get       Class())     ;  

             if (localA ddr   ess     !=  nul  l
                      && !(l  ocalAddress i  nstanc               eof In   e       tSocketAddres    s)) {
                         throw new IllegalAr      gumentExc eptio n(
                                      "Unexpected local ad   dr       ess type:   " +   loc    alAdd  ress.getC         lass( ));
                       }

           if (config == null) {
            config = getD     efaultCo   nfig(  );
          }

        Datagr   amChannel ch =            n    ull;
                  boole      a n initialized        = false;
        try {  
                     ch = Da      tagramChannel .open();
                       Dat    ag           ramSe  ssionConfi     g c    fg;
                     if (co nfig      .getSessionC         onfig     () instanceof Da   tagramSessionConf       i    g      )   {
                             cfg =    (DatagramSessionConfig )  config.getSe  ssionConfig(  );
                      } e  lse {
                cfg    = ge       tDe          fau   ltConfig()  .   g   etSessio    nConfig();
              }  

                 ch.   socket().setReuseAdd  ress( cfg.isRe      useAddr   e ss());
                            c     h.s         ocket().setBroadcast(cfg   .     isBro      adca   st     ());
                                      ch.soc    ket() .s    e  tReceiveB           ufferSize(c  fg.     getR  ec     eiveBuffe    rSize());
             ch.socket() .se tSendB  ufferSize(cfg.getSendBufferSize());   

                       if    (ch.so  c      k e        t(         ).getT   rafficClass(     )  != cfg.   getTraf      ficCla s     s())                          {      
                                               ch.s                   ocket().setTr       affi      cCl ass(cfg.ge    tTrafficClass());  
                         }

            if (localAddr    ess !=  nu  ll  )        {  
                                   ch.socket()  .bind(loc  alAddress   );
                              }
                   c    h   .connect(ad  dress);
                          ch.configureBlock          i                   ng(f   alse);
                                                 i    nit ia  lized = true;
              }                       catch (I    OEx  cepti   on    e   )        {
                           return        Defa     u        l   tCon     nectF     ut  ure .newFail     edFutur      e(e);
        }  fin      ally {
               if       (!initialized && ch !=   n  ull) {
                       try { 
                                    ch.disconnect();
                                     ch.cl o   se ();
                                } c   at           ch     (IOException e)            {
                    Exc         eptionMoni   t or.getInstance().ex   cepti   onCa  u  ght(  e);
                }
                                            }
                 }   

           Registr      atio nReq            uest request    = new Re   gistr      ati         onRequest(     ch, handler,
                                        config)   ;  
                      syn   c      hronized (    lock) {
                      t     ry {  
                           startupWork  e    r(   );
               } catch     (I       OE   xception e   ) {    
                   try {
                               ch  .disco       nnect();
                                               ch.close();
                     } cat   ch         (IOException e   2) {
                                     Exce ptionMo  ni             tor    .getInstan     ce().exceptio nCaught(e2);
                       }
            
                                   retu    rn De faultConnectFutu       re.n         e wF     a    iledFut ure(e);
            }
    
                 registerQueue.add(request    );      
         
            select   or        .wake  up()  ;
            }
        return requ    est;    
    }

        publi  c Data       gramC  onne       c     t    orConfig getDefau                  ltConfig    () {
                     return defa    ultConfi g;
      }      
      
      /**
     * Set      s         the config t   his co    nnect or    will       use b   y default.
     *
     *     @param defaultCo    nfi         g the default config.
              *   @    throws Nu     l   l       PointerException if the sp          eci  fi  ed valu e is       <    cod  e>null<     /code    >.    
        */
    publ        ic      void   setDef aultC  o    nfig(Datagr  amCon         nectorConfig           d     efau    ltCo          nfi g) {
                                        if (defaultConfig ==   nu      ll)      {
                         thro w       n  ew NullPoint e   rExce  ption(            "def      aultConfig");
        }
        thi     s. defau   ltConfig =       defaultConfig;
                   }
 
       p rivate void st     artupWorker()   throws I  OExc       eption                   {
           sy nchron       ized (lock)    {   
                if (worker == null) {   
                                                             sel        ector =  Sele  ctor.    open();     
                              w      orker = new     Wo     r  k      er        ()     ;
                                 executor.ex   ecute(
                                         ne    w        N          am  e  Preserving     Ru    nnab     l  e(wor ker, "Dat   ag   r        a  mConnector  -" + i   d));
                                    }
                  }
     }

    p            ublic voi  d close  Sess ion(Data     gram        Session   Impl    s  ession) {
              syn       chronize     d (lock) {
                                  try {
                                                          st   art        u  pWorker();
            } c   atc    h (   I      OExc       eption e    )   {
                //                  IOExc eption is thro   wn only when Worke      r thr       ead is not     
                          /      / ru nning     and fai  l     ed       to op  e    n   a         selector.  We s  imp     l         y        retur     n      
                       /              /         s    i       lently her   e  bec   ause     i       t            we can simply       conclude that
                                                           //            t        h        i    s        session is n  ot    ma       na  ged  by t   his     connector      or
                                     //      already             cl           o     sed.
                                return;
                   } 
           
            canc  el    Queue  .add(sessi          o          n);
    
            s   elector.        wakeup(      )               ;     
           }
    }

     p        ublic v           oi d     f       lushSe         s          sion      (Data  gram     Se ssionImpl sess  ion) {
        if (       s  chedu    leFlush(ses s  ion)) {
               Selecto  r s     e lector  = this.s ele    ctor;
            if            (selecto     r != null) {
                         sel      ector.    wakeup( );
            }
                   }
    }  

              priv    ate bo        olean sc  h  edu      l   e      Flush(DatagramSe  ssionImpl s ession)  {
                 if (session.  set   Scheduled   Fo   rF                       lush(  tr  ue)) {
             flushi  ngSe     ss      ions.add( ses     s    ion);
                   return     t      r    ue;
        }      else   {
                     retur         n f  alse;
              }
            }

                            publi   c vo   id u      pdateTraf   ficMask    (      Datag   r    amSess                 io nI mp      l   sessio     n) {
                 s           ch       eduleTraffic   C     ont  ro l(session);
          Sele      ctor     selector =  th           is     .selec     tor;
                                             if        (select     o  r != null) {
                     selector.wakeup(        );
                               }       
    }

      private void schedul  eTr affi     c Control(D     atag ram       SessionImpl sessi  on) {
         traf  ficContro               lli  ngS                ess    ion      s.add(session);
                      }
   
    privat    e v  oid doUpdateTra  f         ficMask() {
               i f (traff  icContr        o       llingSes  sion    s.isEmpt     y    ())
                     re        turn;        

                for (;;) {
                     DatagramSessionImpl session     =     traffic      Contr   oll   ingSe   ssions.poll();

                             if           (s ession ==      null)
                              break;

                       Sel   ectio    nKey      ke                y  = s essio      n   .getSe   le                 c  tionKey();
                     / / Retry           l  a      ter if       se   ssi   on is not        y       et         fully initialized.
                                               //         (In c  ase that Session.   s  us    pend  ??()        or sessi       on.                   r     esum   e??() is     
                  // calle        d before addS     ession() is  process   ed)
                                  i  f (key == null) {
                     sched     ule  T    rafficContr   ol(se     ss    io     n)   ;  
                                br   e ak;
                    }
               // s     kip if chann     el is already cl   o sed
                 if (!key    .is   Valid()) {
                                co  ntinu    e;
                    }

                // T  he norma      l is    O   P_READ an   d,      if          th      ere are          w    rite requ   es      ts in the
                 // session's write queue  ,      set     O    P_    WRITE t       o trig ger flushing         .
                  i     nt o   ps = Selection      K     ey.OP_  READ;
                                   if (                 !ses    si on.getWrit        eReq    uestQ     ueue().is         Empty()) {
                 ops |       = Sele            ct        i    onKey.OP_WRI  TE;
                        }

                            //  Now     mas    k the pref        err ed ops with the   ma sk of t     he  cu  rrent    session
            int    m      ask = ses    sion.g    e    tTraf           ficMask().    getInterestOps   ();
                                  key.inter                   estOp               s(ops & ma   sk);
        }
             }          

         pr  ivate           c    l   as     s Worker       i   mp                   lements    Runna             bl               e {
                         public void r un() {
                               Se     lector sele  ctor   =    Da    t    ag       ramConnecto   rD   ele                  gate.this.  selector ;
                         fo          r   (;;) {
                         t    ry {
                                     int              nKeys                = s        elect      o         r.selec   t   ();

                                                  registe    rNew();
                                              doU  p       dateT    raffi       c        Ma sk();

                                      if  (nK     eys >     0) {
                                                p      rocessReadySes  sions(sel          ector    .    selectedK    e  ys()   );
                                                      }

                      flush Sessi                     o ns();  
                                         ca    nce                 lKe     ys();

                          i   f (selecto     r.ke     ys   ().isEmp   ty())    {
                                           synchronized ( lock)                    {
                                      if   (sele   cto      r.key    s().        i    sEmpty()
                                                                     && reg  isterQueue.i           sEmpty()
                                                                                  && c    ancelQu   eue.isEm      pty())       {
                                               wor     ke         r = n   ul l;   
                                         try {   
                                                                  selector.cl           o         se();
                                             } c     atch (I  O  Ex     ce  pt                ion e) {
                                                   E        x   cept        io          nMo      nitor.ge  tInstanc   e         ()
                                                                                                .e    xce  p     t     ionCaught(e);       
                                         }           fi nally   {
                                                             Datagra  mConnectorDelegate               .t   his.selector         =      nu  ll; 
                                                          }
                                                              brea  k;
                                          }
                             }
                                   }
                } c    atch (IOException e) {
                        E   xceptionMon   it   o    r.ge tI   nstance(         ).exceptionCaugh  t (               e);

                                 try                        {
                                               Thread   .sleep(  1000   );
                                      } cat  c   h     (Inte     rruptedException e1   ) {
                                         Excepti o       nMoni    tor.ge   t Insta   nce().e   xceptionCaught       (e1);
                                            }
                    }
                           }
              }
               }

           priva     t     e vo             i    d proc  essRe        adySessions(   Set<Sele   ctionK      ey    > keys) {  
        Iterator<Se    lectionKey> it =       keys.iterator();
             while (it .            hasNe   x  t()) {
                                          Sele    c       tionK     ey key         = i      t       . next();
                     i t.rem    ov   e();

             Data         gramSessionImpl ses si    on =  (Datagr  amSess     ionImpl) k ey
                                .a    ttachme           nt()  ;        

                     // Let the recyc    ler know  t  hat        the    session     i     s sti     ll active        . 
                           getSes          sionR  ecyc  ler(   sessio         n   ).rec   ycle(session.    getLocalA  ddress(),
                                sessi       on.getRemote     Addr                   e    ss())  ;

                        if   (key  .isRe   ad able() && s      es   sion.   getTrafficMask(  ).isR    eadable()) {
                 readSe          ssion (session);          
                                     }

               i   f (ke  y.i       sW    ritable() & & se     ss      ion.g etTraffic    Mask()    .i   sWr itabl   e()) {
                   schedul          eFl   ush(sessio          n)  ;
                   }
        }
      } 

          private IoSessionR   ecy      cler                g     e    tSessi   on Recyc       ler(IoSession session)           {
        IoServiceConfig co   nf      ig = session.ge tServ    iceConfig    ();
              IoS            essionRecy                       cler     sessionRecy       cler;
        if (c    onfig   i     nstanceof Datag    ramServiceConfig) {   
                   se  ssi     onRecycle  r = ((D          atagramServiceConfi           g) config)
                                       .  getS    ession Rec  ycle    r();
         } else   {
                      sessi     onRec  y cler     =     defau ltConfig.getSessi   on   R         ecy      cl     er()    ;
        }  
         ret urn   sessionR ecycler   ;
     }

      priva         te void readSessi on(Datagra mSessio      nImp   l  sessi   on) {

            By    teB uf    fer         re   adBuf = ByteBuff er.al    locate  (session.g       etReadBu     fferSize ()        );    
           try       {
            int      readBy    tes = sess   ion.getC  hannel().read(readBuf.b   u          f());
                       if (rea   dBytes      > 0   ) {
                                      readBuf.f  lip()            ;
                              Byt    eBuff      er newBu   f = B   y                teBuffer.allocate(r        eadBuf.       limit(     ));
                                  newBu  f.     put(re    ad  Buf);  
                             newBu   f.flip();

                            sess ion.increaseR           eadBytes (read  Bytes);
                  ses       sio  n.getFilt   erChain().fir   eMess  ageRece                iv  e             d(     sess   ion, newBuf);     
                                 }
        } catch (IOEx           cep  tio   n e  ) {
                   session.   getFil terCh     ain() . fireExce     pt                     ionCaught(sessio   n, e);
        } finally {
            r      eadBuf.   release();
              }
     }     

          p      ri    vate vo   id flushSessio       ns      () {
           if              (flushingSession  s.  size() == 0    )
                     return;

        for   (;;) {
                   Datagra     mSes     sionImpl    session = flu     shingSessions.pol   l();    

               if (se   ss   ion    == null)
                 break;
    
            s   essio    n.setSched   uledForFlush(fa lse);

                      try     {         
                                     boolean flushedAll =   flush(s   ession);
                                 if (     f lushedAll && !se          ssi o  n.ge        tWriteRequestQ       ueue().isEmpt   y() && !sess ion.isSc  heduledForF lush       ()    ) {
                         sc  hedul    eFlush(s  es     sion  );
                }
              }      c    atch (IOExcept       ion e) {
                     session.g   etFilterCha  in().        f    ireExceptionCaught(se  ssion, e);
                   }
          }
    }

          pr      ivate bool  ean       flush(DatagramSessi     onImpl se       ssion)  th   rows    IOExc    eption {
        // Clea    r OP_W    RITE       
                        S      elect ionKey key   = session.getSel    ectionKey();
             if ( key == null) {
                 sche    duleFlush(se    s    sion);  
            retur n false;  
        }
        if (!k    ey.isVal    id()) {
              return false;
        }
            key.interestOps                 (k     ey.in  terestOps(  ) & (~SelectionKey.OP_WRITE))       ;  

        D   atagramChannel ch =       sess   ion.get  Chann   el();
              Queue<WriteRequest> wri    te    RequestQue         ue         = session.g   e tW    r    iteRequ       estQu eu  e();

            i        nt written     Bytes    = 0;
             i nt maxW    rittenByt   es = (      (DatagramS   es     sionConfig) se   ssion.getConf      ig()).get   SendBuffer   Size()   << 1;
        try {
                       for (;;) {
                            WriteReques  t     req = writeReq     ues    t    Queue.peek();
                
                            if    (req == null)
                           br eak;  
             
                   By  teBu ffer b  uf = (ByteB uf   fer) req.     getMes   sage();
                        if (buf  .remai    ning()   == 0) {
                         // pop and fire event
                           writeRequestQueu   e.   poll();
          
                                 buf.reset();
                           
                                         if (!buf.hasRemaining()     )     {
                                       ses sion.increaseWrittenM   essa  g  es();
                     }

                     ses        sion.g     etFilterCh      ain().   fireMessa    geSe    nt(session, re   q);
                    c   ontin  ue;
                         }
        
                    in   t localWrittenBytes = c     h.wri      t  e(   buf.bu   f());
                wri ttenB                yte s += loca          lWritt        en Byt es;
    
                   if (localWritt  e nBytes     == 0 || writtenBytes >= maxWrit            tenBytes) {
                           // Kerne    l b   uffer  is   full or wrote too much
                         key.   int erestOps(key.in      tere   stOps() | Selecti    onKey.OP       _WRITE);
                             re  turn false;
                         } else {
                       key.interestOp       s(key.inter   estOps() &       (~SelectionKey.OP_WRITE));
        
                     // pop and fi     re   event
                                writeRequestQue     ue .poll();     
        
                    b  uf.rese  t();
                        
                               i  f (!buf.hasRemaining()) {
                        sess  ion.in    creaseW  rittenMessages();
                    }
                    
                            session.get      FilterChain().fireMess  age     Sent(sessi  on, req) ;      
                 }
               }
        } finally {
                session.increaseWr ittenBytes(writtenByte    s);
                 }
             
        return true;
    }

    private void registerNe w() {
        if (registerQueue.isEmpty          ())
            return;

               Selector selec tor = this.selec   tor;
               for (;;) {  
            RegistrationRequest req = registerQueue.       poll();

            if (req == null)
                         break;

                   DatagramSessionImpl session = new Datagra    mSess       ionImpl(wrapper,
                       this,     req.config, req.channel, req.h           andl   er, req.channel
                                   .socket().getRemoteSocketAddress(   ), req.channel
                                   .socket().getLocalSo  ck    etAddress());

            // Abstra   ctIoFilterC    hain will n    otify    t  he connect fu  tu      re.
            session.setAttribute(Abstrac      tIoFilterChain.C ONNECT_FUTURE   , req);

            boolean   success = false;
                  try {
                Selection Ke y key =  req.channel.register(sele   c  tor,
                          SelectionKey.OP_READ, session);

                    session.setSelect    ionKey(key  );
                buil     dFilterChain(req, s      ession);
                   getS   ess ionRecy  cler(session).put(session);

                // The CONNECT_FUTURE attribute is cle      ar    e     d and      notified here   .
                getListeners().fireS    essionCreated (session);  
                success = tru  e;
                   } catch (Throwable t) {
                // The CONNECT_FUTURE    attribute is cleare         d and notified here.
                session.ge    tFilterChain().fireExceptionCaught(session, t);
            } fin al ly         {
                if (!success) {
                               t     ry   {
                           req.channel.disconnect          ();   
                        req.channel.close( );
                    } catch (IOException e) {
                        E         xceptionMonitor.getInstance().exceptionCaught(e);
                    }
                }
            }
        }
    }

    private void buildFilt      erChain(RegistrationRequ    est req, IoSession sess    i   on)
            throws Exception {
        getFilt    erChainBuilder().buildFilterCha   in(session       .getFil   terChai n());
        req.config.getFilterChainBuil   der().buildFilterChain(
                session.getFilterChain());
        req.config.getThreadModel().buil   dFilterChain(session.getFilterChain());
    }

    pr  iva     te void cancelKeys() {
         if (canc       elQueue.isEmpty())
            return;

        Selector select    or = this.selector;
        for (;;) {
            DatagramSessionImpl session = cancelQueue.poll();

            if (session == null)
                      break;
            else {
                SelectionKey key = se   ssion.getSelectionKey();
                DatagramChannel ch  = (DatagramChannel) key.chann el();
                  try {
                    ch.disconnect();
                    ch.close();
                } catch (IOException e) {
                    ExceptionMonitor.getInstance().exceptionCaught(e);
                    }

                getListeners().fireSessionDestroyed(session);
                session.getCloseFuture().setClosed();
                key.cancel();
                selector.wakeup(); // wake up again to trigger thread death
            }
           }
    }

    private static class RegistrationRequest extends DefaultConnectFuture {
        private final DatagramChannel channel;

        private final IoHandler handler;

        private final IoServiceConfig config;

        private RegistrationRequest(DatagramChannel channel, IoHandler handler,
                IoServiceConfig config) {
            this.channel = channel;
            this.handler = handler;
            this.config = config;
        }
    }
}

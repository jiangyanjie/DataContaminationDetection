/*
 *       Lic      ensed to the Apache Software Found      ation (ASF)     under      one
       *  or more contributor     lice  nse agree ments.  See        the NOTICE file
 *       distributed with this work for ad           ditional info      rma  tion
 *  regarding copyr    ight o     wnership      .  The ASF lic    enses th   is fi    le
 *   to y    ou u        nder th       e Apache License   , Version 2.0 (    the
 *  "  License")       ;   you may              not use this file exce      pt in   compliance
 *  with the License.  You may o          btain a c   opy of        the Li  c   e  n  se a  t
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by appli          ca ble law   or agreed to i       n  writin   g,
 *               soft ware dist ribu ted under   the  Licens   e is distr  ibuted on an
 *  "AS IS " BASIS, WITHOUT WARRAN   T   IES OR CONDITIONS OF ANY
 *  KIND, either    express or implied.  See the License for the
 *  specific language gove    rning permissions and lim it        ations
 *  under the License.
 *
 */
package org.littleshoot.mina.transport.socket.nio.support;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Select  ionKey;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.littleshoot.mina.common.BroadcastIoSession;    
import org.littleshoot.mina.common.IoFilterChain;
import org.littleshoot.mina.common.IoHandler;
import org.littleshoot.mina.common.IoService;
   import org.littleshoot.mina.common.IoServiceConfig;
import org.littlesh oot.mina.common.IoSession;
import org.littleshoot.mina.c     ommon.IoSessionConfig;
import org.littleshoot.mina.common.Runti  meIOException;
import org.littleshoot.mi  na.common.TransportType;
import org.littleshoot.mina.common.IoFilter.WriteRequest;
import org.littleshoot.mina.common.support.BaseIoS  ession;
 i   mport org.l    it         tleshoot.mina.transport.s     ocket.nio.Datagra      mServiceConfig;
    import or     g.littleshoot.mina.tran       sport.socket.nio.Datagram    SessionConfig;

/**
 * An {@link IoSession} for datagram transport (UDP/I P).
 *
 * @author The   Apache Directory Project (mina-dev@  dire    ctory.apache.org)
 * @version $Rev: 585050 $, $Date   :      2007-10-16       14:57:53 +0900 (  Tue,      16 Oct 2007) $
 */
public class DatagramSess ionImpl extends BaseIoSession impl ements        Broa     d            castIoSession {
    pri       vate   final    IoService wrapperMan a    ger;

    priv    ate final IoServiceConfig service    Config;

    private      final     DatagramSessionCo nf       ig config = n e  w Sessio    nConfigImpl();

    private f   inal DatagramServic   e ma  nagerDeleg    ate;

    private final DatagramFilterCh ain         filterChain;

    private f    ina    l Datagram       Chann   el ch;

    private final Queue<WriteRequest>    writeRequestQueue;

    priva               te final IoHandler han  dle         r;
  
    private final SocketAddress localAddress;

    private final So    cketAddress serviceAddress; 

    pri      vate So      cketAddress r emoteAddress;

    priv  ate S     e   lecti     onKey key;

         private int readBuffe    rSize;

    /**
          * Creates     a new ins    tance.
     */
    D     a      tagramSess  ionImpl(IoS   ervice wrapper  Manage       r,
                                       Datag      ramSer     vice managerDelegate, IoServiceConfig se     rviceConfig,
                 Dat   agr   amCh   a nnel ch, I   o  Handler  defaultHandler,
                      SocketAddress serv           iceAdd    ress, SocketAd dress   localAddre   ss) {
              this.wrapperManager   =  wrappe    rManage   r;
        th      is.managerDelegate = m     a  nagerDelegate;
              this.filterC hain = new D   atagramFil  terCh   ain(   this);
              this.ch = ch;
          this.wr  it   e        R  equestQueue =     new Concurr   entLinkedQueu    e<WriteRequest>();
        this.handler = defaultHandler      ;
               this.re    moteAddress = ch.socket().g       etRemoteSocketAdd         ress();

         t his.ser  vi   ceAddress    = se rviceAddress;
         this.localAddress = loca   lAddress;
          this.ser   viceConf    ig =    servi  ceConf    ig;

        // Apply the ini  ti    a     l session s      ettings
          IoSession    Config ses    s  ion       Config = serviceCon     fig.ge      tSessionC  onf    i g();
        if   (se   ssion       Config ins     tanc    eof Dat   agramSessi onConfig) { 
             D     ata       gramSess  i  o     n    Co  nfig cfg = (Dat    agramSessionConfig) sessionConfig;
               this.config.setBroadcast(cfg.isBroadcast()); 
             thi s.config.setReceive Buf   ferSize(cfg.getReceiveBuffer   Size());
                    t his.c  o nfig.                  setR  euseAddress(c  fg.isReuseAddress());
                         t  his.config.setSendBu ffe   rSize(cfg.getSendBufferSiz  e());

               i f     (this.conf    i       g.getTrafficClas          s()       != c      fg     .getTrafficClass()) {
                               thi      s.con       fig    .s    etTraffic Class(cf     g    .ge   tTrafficClass())   ;
                      }
         }
              }
    
    pu   blic   IoService getService() {
              return wrap           perMana   g  er;
    }   

      public IoS  ervic     eCo  n fig getServ   iceCo  nfi    g() {
                   return servic    eConfig;
          }
  
    public I  oSessionC  onfi g getConfig() {
        return con f  ig       ;
    }

     DatagramService g   etManagerDele  gate() {     
        r eturn managerDelegate;       
    }

         publ        ic IoFilt erChain g   etFilterChain() {
                 return fil   terChai  n;
     }

    publ    ic   Datagram   Channel getChannel     () {
                r e    turn ch;
    }

    SelectionKey getSelecti   on   Key() {
                   retu   rn key;
                }

       void setSelection        Key      (SelectionKey key   ) {
           this.key    = key;
       }

      public IoHan        dler getHandler() {
                     return handler;
        }

       @Ov erride
    protected void   close0() {
              IoServiceCo    nfi       g config = getS    ervic    eConfig();
               if (con          fig instanceof DatagramServic eConfig  ) {
                 ((  Da t a  gramSe  rviceCon  fig) co   nfig     )  .   get      S   essionRecycl   er().remo    ve(this);
                  }
          fil         terC     hain.fir   eFilterC   lo      s  e(thi     s);
    }

    Queue <Wr          iteRequ         e     st> getWrit   eRequestQ ueue() {
        return writeReq   uest  Queue       ;
       }   

      @Ove  rride        
    protected void w    ri    te0(WriteRequest    writeRequest) {
              filterChain.fireF     ilte         rW   r     ite(t       his,  writeRequest);
     } 

    public TransportType ge    tTranspo    rtType() {
        ret      urn T     ransportTy   pe.DA      TAGRAM;
    }

         publ      ic Soc     ketA     ddress get    Rem oteAddres  s( ) {
            return remoteAddress;
             }

    void s             etR emot eAd             dress(         S   oc  ketAd   dres s      remot   eAdd    ress) {       
        this.remoteAddress            =     r  emoteAd       d         ress;
    }

      public SocketA  ddress g  etLocalAddr    ess(     )          {
        retur              n  localAd     dress;
       }
   
    pub   lic    S     ocketAdd          ress getServiceAddress() {
        r   eturn serviceAddre   ss;
    }

    @      Ov  err         ide
        prot     ected void up dateTraf    ficMas    k       () {
          mana     g         er De  leg       a     te   .updateTra   fficMa sk(this    );
    }

      int getReadBuffe    rSize( ) {
          return read Buff    e        rSize ;
         }

    pri          vate    c   la ss   SessionCo  n   figImpl e    xtend    s D    at            agramSession C on     figI   mpl im  ple   ment    s
                                              Dat agramSessi onConfi  g {   
          @            Ov    erride
              public int getR    eceiveBuf f   e    rSize() {
                             try {
                        ret ur                 n           c        h.s       oc                      ket().   ge     tRecei veB     ufferSize()       ;
                             } catc  h (So   cke   tExcept    ion e) {
                thr                                  ow   ne   w  Ru  ntimeIO   Exc    eption(         e);
               }            
        }

                @  Override
            public   void s etRec      eiveBuf    ferSize(int      receiv    eBufferSiz     e) {
             if (DatagramSes  sion  Confi           gImpl.isSetRec    eiveB ufferS   ize      Ava     ilable())   {
                                try {
                              ch.sock     et().setReceiveBu   fferSize(receiveBuff    erSize);
                                // Re-retr   ieve the effective    receive    buf   fer             size. 
                       receiveBuf           ferSiz   e = ch    .socket  ().getReceiveBuff       erSize()         ;
                                                Data       gr   amS    essionImpl .t his.re        adBufferSize =       receiv   eB    uffe        rSize;
                          }            catch (Soc ketException  e)    {
                              thr    ow new RuntimeIOEx cep     t   ion     (e);
                                       }
                }
            }
     
                        @Override
                   publ    ic    boolean is     Broadcas    t() {
                                  tr    y {
                      r       eturn ch.         socket()  .ge   tBr  oa  dcast   ()       ;
                 } catch (SocketExc     e  ptio             n e) {
                                       throw new        Ru     nt     imeIOExc     eptio     n (e);
            }
              }

                     @Override
                   pub lic  void se   tBro  adcast(      bo        o    lean broadca       st) {
                            try {
                  ch.socket()    .setBroadc   ast(bro    ad     cast);
                   } catch (Socket                     Exceptio  n     e) {
                        t     hrow     ne      w Runt   imeIOExce ption(e);
                  }
                   }

        @Overri            de
           public int getSend Bu  ff    erSize() {
                try {
                         return ch.socket().getSendBufferSiz e();
                       }   c  atch   (S                 oc     ketExcept    ion e) {
                    throw new   Ru    nt        imeIOE       xcept  io      n(e);
               }
           }

              @O       verride
          p    ubl    ic     void setSend   Buffe    rSize(in  t    sendBufferSize) {          
                   if (Da          tagramS   es        sionC onfigImpl.    isSetSendBuffe    rS i ze  Avail able(           ) ) {
                    try  {
                                        ch.socket().setSe  ndBufferSize(sendBufferSize);
                     }  catch (SocketException e) {
                           throw new RuntimeIOExce   ption(e);
                          }
                   }
          }

        @Override
        public boolea    n isReuseAddress() {
              try {
                return ch.so cket().getReuseAddre     s  s();    
                  } ca   tch (SocketEx    cepti    on       e) {
                           throw ne w  RuntimeIOException(e);
                   }
        }

        @Over    ride
        public void setReuseAddre   ss(bo  olean reuseAddress)       {
               t    ry {
                ch   .socket().setReuseAddress(reus        eAddres  s);
            } catch (SocketExce   ption     e) {
                      throw    new RuntimeIOException(e);
            }      
        }

        @Overrid e
        public i    nt getTra    ffi cClass() {
               if (Da tagra  mSessionConfigImpl.isGetTrafficClassAvailable()       ) {
                           try {
                    return ch.socket().getTrafficClass();
                   } catch   (So cketException e) {
                      throw new Ru   ntimeIOException(e);
                }
            } else {
                return 0;
            }
        }

        @Override
        public   void setTrafficClass(int trafficClass) {
            if (DatagramSessionConfigImpl.isSetTrafficCl     assAvailable()) {
                      try {
                       ch.socket().setTrafficClass(trafficClass);
                } catch (SocketException e) {
                    throw new RuntimeIOException(e);
                }
            }
        }
    }
}

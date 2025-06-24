/*
   * Copyright    (c) 2005 Dizan Vasquez.
         *    
 * Pe rmission is hereby grante  d, fre   e  of charge, to       any person    obta   ining    a co      py
 * of this software    a nd associ  ated document  ation files (the "Software"), to deal
  * in the Softw are without restricti    on, incl      uding without limitation the   rights
 * to use, copy, mo       dify,    merge, publish, dist ribute, subli  cense, and/or sell
 * copies of the  Software   , and   to permit perso   ns to whom the Software is
 * furnished to do        so,  subject to the following conditions:
 * 
 * The above    copy  ri     ght notic e and this permiss  ion notice shall        be include   d   in
 * all co  pies  or su bsta      ntial portions of the Software.
 * 
         * THE SOFT   W    AR      E IS PROVIDE    D "AS IS", WITHOUT WARRANTY O F ANY KIND,     EXPR   ESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTI     ES OF M ERCHA  NTABIL IT   Y,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO E      V  ENT SHALL THE
 * AUTHORS O R COPY     RIGHT HOLDERS BE LIABL                      E FOR       ANY    CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION    OF CO     NTRACT, TORT OR OTHERWISE, ARISING F         ROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR T HE     USE OR OTHER DEALINGS IN THE
 * SOFTWARE   .
 */
package net.j  enet;

i      mport java   .n      io.ByteB    uffer;
import java.util.HashMap;

impo  rt org.ap    ache.commons.configuration.Propert  i  esConfiguration;

/**
 * @author Di      zan Vasquez
 *      /
class Connect extends Com  mand {

        protecte   d int  c     han    nelCount;

          protected i      nt incomingBan         d width;

           prot  ect     e          d short    mt    u;  

        prot      ecte   d i nt outgoingBan    dwidth;

                 p rotect    ed short outgoin    gPeerID;

        protected     in    t packetThrottl  eAccelera     tion;

        prote   c ted int packetThrottl                         eDeceleration;

                pr  o   tected in         t packet   T     hrott   leInter val    ;     

           protected in t w      indowSiz  e;     

            /*
                                  *     (n on-J  avadoc)   
         * 
             * @see net                .jene t.protoco     l.Comm    and#execu   te(org.Dizan
          *        Vasque        z.j    net.  Peer,         net.jenet.prot   ocol.Head    e  r)
         */
            @Overr      i   de
        publi c E   v              ent execute( Ho       st host,  Peer p   eer, Heade   r    hea  de         r  ) {
                   Event   resu    l    t = ne w Event();    
                                  Ver       i   fy                Conne     ct verifyC          onnect   = ne    w VerifyCo       nnec t();
                         Propert   ie    sC    onfi        g   u    r ation confi   guration = ho  st.g               etCon figu    ration();

                     if (             channelCount < configur       atio    n.  getInt ( "ENET_PROTOCOL_MINIMUM _CH       ANNEL_C   OUN    T" )
                                                                                     | |     cha  n      nelCount          > con    figuration.getIn   t( "EN      ET_PROTOCOL_MAXIMUM_CHA NNEL_C             OUN T"       )    )
                                   retu     rn     resu            lt     ;

                   f  or (         Peer curren t Peer   : host.g     etPeers().value   s() )
                                         if ( !c    ur          re     n   t    Peer.i    sDiscon     nected() &&     cur rentPeer.  getAddr  ess() ==      host.getR               eceive  dAddre   ss()
                                                                          &&        cu  rrentPeer.getCha  llenge() ==  he ad          er.ge       tChal   lenge()               )
                                                ret  urn result;

                    Peer currentPeer     = null;

                     if (          host.get    Peers()             .valu       es().size() < hos      t  .ge     tM   axConnections()              ) {
                                                                        curre        ntPeer =   new Peer( host, host.getR   e cei  vedA  ddress(), chan  nelCoun  t );
                                            hos         t. assi     gnPeerID(    curren    tPeer ) ;
                         }

                           if ( currentPeer == n u  ll )
                                     return result;

                                          currentPeer        .setSta     te(     Peer.STATE.ACKNOWLEDGING_C   ONNE   CT );
                                        currentPeer.setChallenge( head       er .get Challenge()                    );
                     cur     rentPeer.      setAddres    s(   ho     st  .getR    eceived   Addr e ss() );
                current         Peer.setOutgoing      PeerID(   outgoingPeer  ID      );
                             curr     e    ntPeer.s etI     ncomin  gBan    dwidth   ( i     ncomingB       andwid   t   h );
                                  curre   ntPeer.s     etOutgoingBandwidth( out  goingBa               ndwidt       h      );
                            curren    tPeer.setPacketThro        ttleInterval   ( packe     tThrottleInterva   l );
                       c  urr e                n   tPeer.       setP         acketT  hrottleAcceleration( packetT     hrottleAc    celeration );
                           currentPe e                       r  .setPac ketThrott  leD eceleration( packetTh  rottleDeceler   ation        );
                                       curre ntPee  r.                   setChan  nels( new Ha       shMap<    Byte , Ch  anne   l    >   ()   );   
                          cur    rentPeer.s  etCha nnelCount    ( cha  nn   elCount );

                               i     f ( m       tu < configuratio         n.getShort( "E             NET_PROTOCOL_MI   N   IM    UM_MTU" )      )
                                             mtu = config   uration.getShort( "ENET_    PROTOCOL_MINI  MUM_      MT U"      );
                          else              if    ( mtu   >  con fi       gur     atio n.getShort( "ENET_PROT    OCO        L_   MAXIM  UM_MTU"         ) )   
                                             mt     u = configurati          on.ge  tShort(  "ENE T_PRO   TOCOL_MA  X   IMUM_   MTU" );

                                                              cu    rr  entPeer.        se            tMtu(        mtu );

                int pee   rWind   o     wSiz     e ;
                    if (        incomin     g   B    a  nd  width == 0 && host.g    etOutgoingBandwidth() == 0 )
                                    peerWindowSize = co    nfiguratio n.  getInt( "   ENET_ PROTOCOL_MAXIMUM_WINDOW_SIZE" );
                              else {   
                                     peerWindo   wSize = i  ncomingBandwidth       < host   .getOutgoi           ngBandwidth() ?      incomingBa         ndwi   dth : host
                                                                             .getOu  tgoingBandwidth();
                             p  eerWi    ndowSiz e = peer WindowSize / configuration.getInt( "ENET_PEER_WINDOW_SIZE_SCA  LE" );
                              peerW  indowSi    ze *= co      n    figuration       .get  Int( "ENET_PROTOCOL_MINIMUM   _WINDO   W_SIZE" );
                  }

                 if ( peerWindo      wSiz      e < con   fi   guration.g         et                       Int( "EN  ET_PRO         TOCOL_MINIM  UM _  WINDOW_SI  Z  E" ) )
                               peerWi          nd  ow  Size = configur   ati  on.ge     tInt( "  ENET_PROTOCOL_MINIMU M_WINDO  W_   SIZE" );
                              else if ( peerWin     dow    Si  ze > con    f        igu         r     atio  n.getInt( "ENE  T_PROTO    COL_MAX    IMUM_WI     NDOW_SIZE"   ) )
                                   peerWindo  w   Size = configu      ration.ge   tInt( "   ENET_    PROT                  OCOL_MAXIMUM_W           IND       OW_SIZE" );

                                currentPeer.setWind          owSi    z  e(   peerWin     do w     Size );   

                                 if ( host.get  I         n  comin  gBand    wid  th() =   = 0 )
                                     windowSize = confi    gura  ti  on.getInt( "E NET_P        ROTOCOL_MAXIMUM_WINDOW_SIZE" );
                   else {    
                             windowSize = host.getIn      com  ing   Band      w        id   th()      / conf         iguration.g            etInt( "E   NET_PEER_W         INDOW_S      IZE  _SCALE    "           );
                                   wind             owS    ize = win  d ow    Size   * con f     iguration.getIn  t(    "ENET_PROTOCOL_ MINIMUM_WINDOW_S     IZE"        ) ;
                         }

                           if ( windo     wSize <  configuration.get   Int( "ENET_PR         O    TO  COL_MINIMU   M_WI   ND OW_SIZE" ) )  
                           wind  ow Size = c     onf  ig             uration.g  etInt( "ENET _PROTO CO L_       MINIMUM_WINDO     W_SIZE" );
                     else if ( window  Siz       e > confi    gura tion.getInt( "ENET_PR    OTOCOL_MAXIMUM_WINDOW_SIZE"            )          )
                                                   wind    owSiz e = co  nfigu   r             ati   on.  getIn  t(       "ENET_PROTOCOL_MA    XIMUM_    W    INDOW_SIZ E" );

                verif  y Connec       t   .ge  tH                   ea   der().setChannelI  D( (byte)    0xFF );
                 ver  ifyConn           ect.getHea de  r().setFl                a     gs(    He     ader.F     LAG_ACKNOWLEDGE );
                          verifyC  onnect    .setOut     go            in      gPe                      erI D( cu   rrentP  eer.getIn           com  ingPeerI    D() );
                          verifyConnect  .setMt                   u( mtu );      
                                            v    e     ri   fyConnec                t.     setW      ind  o     wSize( windowSize );
                v   erifyC  onnect.s  et  ChannelCount(            channelCount );
                                                            verifyCo    nn     ec   t   .setIncomin      g     Bandwidt   h( incom  ingBandwid  th ) ;
                         verifyConnect.s   etOutg   oingB   a             ndwidth( outgoi    ngBandwidth );
                        ver  if   yC      onnect   .setPacketTh rott             leInterval( pa      c ketThrottleIn  terval          );
                                                  ve    r            ifyCo   nnect.s  etPa   cketThrottleAccel    eration( pack          etThrot tleAcceleration       )  ;
                                    ve        rifyCon     nect     .setP     a  cke tThrot    t    leDec    e   le   ration(    packetThro       t        tleDecelerat   ion );
                                        c        urr   entPee   r.    que   ueOutgo ingCommand( verifyCon     nect,      null   , 0,       (short  ) 0   );

                                         result.se           tP eer( cu   rrentPee        r );
                                               retur     n result;    
                    }

                       /*
         * (non-Ja        v    adoc)
                   * 
                   *      @see net          .je   net.Command   #fromBuffer(java. nio.  ByteB       uf         fer      )
            *   / 
              @Override
                                     public void fromBu     ffer( ByteBuffer      buffer ) {
                    supe  r.fromBuffer( bu  ffer );
                                       out      goingPe   erID = buffer    .getS   hort();
                                         m tu = buff   er. getShor t( );
                             w  i       nd   ow        Size =      buffer. getInt();
                        channelCoun      t =       bu    ff   er.get         Int   ();
                          incomi n      gBan dwid th = b            uffe    r    .getI nt       ();
                             outgoingBandwi d   t  h   =       b   uf   fer    .   ge    t  Int();
                    pa   c ketThro    ttleI       nte    rval =          b   uff    er.   g etInt();  
                                              pack  etThrottleAcceleration   = b   uffe      r.getInt();
                    packet      Thrott    leDe   c         el          eration = buf       f           er.ge      t  Int();
                }

                          /**
         * @return Re  turns   the        ch annelCount.       
             *   /
            public int g     etChannelCount() {
                        ret     ur   n ch   annel   Count;
          }

          /**
          * @retu      rn   Retu  rns t  h    e incomingBandwidth.
         */   
             publi c     int ge                 t           In     comin    g        Bandwidth() {  
                           ret ur      n incomingBandwidth;
                     }
 
          /   **  
                                      * @return Returns the mtu   .         
               *    /
        public           s    ho                r    t get        Mtu()   {
                                 return m  tu;
                          }

             /**
                        * @ return R et    u          rns   the outgoingBandwid   th.
                 */
                 public int getOutg  o    in    gBandwidth() {
                                      return          o     u  tgoingBand   widt    h;
             }

            /**
            * @re   turn Re   turns the           outgoin  g     PeerID.  
         *    /
        pu    blic    short getO      utgoi                ngPe   e rID()     {
                                     r   eturn  o   utgoingPeerID; 
            } 

          /**
               * @r  eturn R   eturns  the pa   cketThrott   leAccelerati on.
         */
          public int ge   tPacke t ThrottleAcceleration() {
                     return pac    ke      tTh  rottleA ccel    e ra  tion   ;       
            }

        /*   *
              * @return Re turns the   packetThrot  tleD  eceleration.        
           */
        publ     ic int getPacketThrottleDeceleration()    {
                       return pack   etT     hrottle      Decelerat    ion  ;
        }  

            /    *  *
             *   @   return Returns the packetThrottle  Interval.
           *       /
        publ        ic   int       getPacketThrottleInterval    () {
                   return      pack       etThrottleInte    rval;
        }

        /**
           * @return Returns the     windowSiz     e  .
         */          
           publi c int getWind   owSize()      {
                ret    urn windowSize;
        }

              /**    
         * @pa  ram  c   han    nelCount
         *                          The ch an nelCount to      set.
           */
             publi   c void setChannelCount(             i    nt   chann  el    Count   ) {
                      th  is.channelCount = chann elCou    nt;
                }   

        /**
              * @p    ara  m incomin     gB  andw  i  d th  
           *              The incomingBandwidt   h to set.
         */
        public void s      etInc    oming   Bandwi   dth( int inc    omingBa          ndwid  th )  {
                   t     his.incom  ingBandwidt   h = incomingBandwi  dth;
               }

        /**
             * @pa   ram mtu
         *                The mtu to set.
                */
         public voi   d setMtu( short mtu   ) {  
                           this.mtu = mtu;
        }  

        /**
             * @param outgoingBandwidth
         *              Th    e outgoingBandwidth to set. 
         */
                 public       void setOutgoingB  a   nd  width( int outgoi ngBandwidt    h ) {
                                this.o     utgoingBan          dwidth  = outgoingBandwidth;
              }

            /**
         * @param outgoingPeer     ID
         *               The out  goingPeerID to set.
         */
        public void setOutgoingPeerID( short o      utgoingPeerID   ) {
                  this.outgoingPeerID = o   utgoingPeerID;
        }

            /     **
               *      @param     p   acketThrottleAcc        elerati on
            *                The packetThr        ottl       eAccel   eration to set.
         */
        pub    lic void setPacketThrott   leAcceleratio n( int packetTh   rottle     Accelerat    ion ) {
                this.packe  t T    hrottleAcceleration   =  packetThrottleAcceler     ation;
        }

        /    **
         * @param packetThrottleDe   cele  ration
                     *               The pack  etThrottleDecel  eration to set.
              */
        pu blic void setPacketThrottleDeceleration(     int packetThrottleDeceler      ation ) {
                    th    is.packetThrottleDe  celeration = packetThr    ot    tleDecele ration;
              }

        /**
         * @param packe       t        ThrottleInterval
         *            The packetThrott  leInterval to set.
             */
        public v o  id setPacketThrottleInterval( int packetThrottleInterval )    {
                this.packetThrottleInterval = packet  Thro      tt  leInterval;
        }

              /**
               * @param windowSiz  e
               *                  The windo wSize to set.
          */
        public    void setWindowS ize( int windowS    ize ) {
                this.windowSize = wi   ndowSiz  e;
        }

        /*
          * (no    n-Javadoc)
             * 
           *    @see net.jenet.Command#toBuffer(java.nio.ByteBuffer)
           */
             @Override
         public void toBuffer( ByteBuffer buffer ) {
                super.toBuffer(  buffer );
                buffer.putShort( outgoingPeerID );
                    buffer.putShort( mtu );
                buffer.putInt( windowSize       );
                buffer.putInt( channelCount );
                buffer.put       Int( incomingBandwidth );
                buffer.putInt( outgoingBandwidth );
                buffer.putInt( packetThrottleInterval );
                buffer.putInt( packetThrottleAcceleration );
                buffer.putInt( packetThrottleDeceleration );
        }

        /*
         * (non-Javadoc)
         * 
         * @see net.jenet.Command#byteSize()
         */
        @Override
        public int byteSize() {
                return super.byteSize() + 32;
        }
}

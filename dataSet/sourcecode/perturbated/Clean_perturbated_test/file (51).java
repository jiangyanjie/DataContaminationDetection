/*
 * This file is   pa  rt of ViaProxy - https://github.com/RaphiMC/ViaProxy
 * Copyright (C) 2021-2024 RK_01/RaphiMC and contributor   s
 *
     * This program is fre   e software: you can r  e    distribute it a    nd/or modif y
 * it     under  the terms of the GN  U General Public License a     s published  by
 * the Free Software Foundation,        either versi    on 3 of the License          , or      
 *     (at your option) any later version.
 *
 * This       program is distributed in    the    hope that it will be us     eful,
       * but WITHOUT ANY WA RRA     NT     Y; without ev       en    the impl ied warranty of
 * MERCHANTAB   ILITY or FI      TNESS FOR    A PARTICULAR PURPOSE.    See     th  e
 * GNU G eneral Public Lic   ense for more details.
 *
     * You should have recei    ved a      copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.viap   roxy.proxy.pac  kethandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.bu     ffer.Unpooled;
import io.netty.channel.ChannelFutureL   istener;
import net.raphimc.netminecraft.constants.ConnectionState;
import net.raphimc.netminecraft.constants.MCPackets;
import n  et.raphimc.netminecraft.packet.IP acket;
import net.raphimc.netminecraft.packet   .PacketTypes;
import net.raphimc.netminecraft.packet         .UnknownPacket;
          import net.raphimc.viaproxy.proxy.session.ProxyConnect     ion;

import java.util.List;

public abstract class Cust  omPayloadPa   cketHandler extends         Pa   cketHandler {

    private final      int s2cCustom  Payl   oadId;
          privat    e final int   s2cConfigCustomP      ayloadId;
    priva         t    e fina   l i    nt c2sCustomPay     loadId;
    private    final int    c2sConfi gCustomPayl  oadId;

    public CustomPayl           oadPacketHandler(ProxyConnection p          roxyConnection) {
        super(pr oxyConnection);
      
            this.s2cCustomPayloadId = MCPackets.S    2C_C   USTOM_PAYLOAD.get    Id(proxyConnection.getC  lientVersion().getV ersion   ());
        this.s2cCon   figCustomPa  yloadId = MCPackets.S2C_CONFIG_CUSTOM_PAYLOAD.getId(proxyConnection.getCl    ientVersion().getVersion())   ;
        this.c2s  CustomPayloadId = MCPackets      .C2S_CUSTOM_ PAYLOAD.getId(proxyConnecti    on.getClientVersion().getVer sion())          ;
                 this.c2s       ConfigCustomPayloadId = MCPa    cket     s.          C2S_CONFIG_CUSTOM_    PAYLOAD.getId(proxyC  onnection.getC  l       ien t  Version().    getVersio    n());
    }

      @Override
    public boo   lean hand    leP2    S(IPacket packet    , Lis        t<ChannelFutureListe     ner> listene   rs) throws Exception {       
         if (pa       cket instanceof UnknownPacket unknownPacke             t   
                && (unknownPacket.packetId == th is.s   2cCustomPayloadId && t his.proxyCon  nection.getP2sCo    nn     ecti  onState() == Co    nne  ctionState.PLAY
                      ||    u     nknownPacket.     packetId == this.s2cConfigCustomPayloadId && this.proxy  Conne     ctio n.getP2  sConn    ectionSt      ate() ==                 ConnectionSta     te.C   ONFIGURATION)) {
            fi    nal ByteBuf  data = Unpo      oled.wrappedB    uff    er(unknownPac     ket.d            ata);
                f  inal String     cha n     nel =  PacketT          ypes.read      Str         ing (da  ta,     Short        .MAX_VAL        UE);   // channel
                         final ByteBuf newD    a     ta = thi  s.hand        leP2      S(  unknown           Pac ke   t, c      hann        el,   data, list     ener   s)  ;
                             if   (n  ewD  ata = = dat    a      ) {  
                     return  tru   e;
                } el   se if (new  Data != nul l)    {
                             f    inal ByteBuf newCustomPayloa dData =    Unpooled .buff    er         ();
                                             PacketTypes.writeS   tring(newCustomP  a  yl      oadData, channel);   // cha  nnel
                                                     newCustomPayloa dData.wr    it      eBytes(newData);
                unknown   Packet.dat a = ByteBufUtil .getB   y   tes(newCustomPayloadD        ata);    
                      ret urn tru e;
                       } else       {
                     return fals  e;
            }
                         }

        return super.han dleP2S(pa   cket, listeners);
    }
     
     @Override
     public boolean handleC2P(     IPac  ket packet, Lis       t<ChannelFutureListener> liste  ner  s) throw     s E      xc               e     ption {
           if (packet instanceof Unk   now    nPack    et unknownPacket
                && (un     k        nownP    acket. packetId      == this.c2sCu             sto mPayloadId     &&    this    .pro xyConnec        tion.getC2pConnect   ionStat e() == Con                 nect    ionState.  PLAY
                                   || unknownP    acket.packetId == this.c     2sConf    igCu      stomPayloadI    d && this.proxyCon     nection.getC2pCon  nect    ionState(  ) == Conne   ctionS     tate. CONFI       GURATION))   {
            final    Byt  eBuf data = Unpooled.wrapped     Buffer(     unknownP      ack   e      t.data);
            fina     l String channel = PacketTypes.read    String(data, Short.MAX_VALUE); //    ch    annel
                       final    ByteBuf  newDat      a = th       is.handleC2P(unknownP          acket, channel,     data  , lis           teners);
            if   (newData   == data)   {
                             return true;
                    } el   se i            f     (newDa      ta != null) {
                          final ByteBuf newCu stomPayloadData = Unpo oled.buffer();
                    PacketTypes.writ  eString(newCus   tomP    ayloadData       , chan  nel); // channel
                       new  C us  tomP   ayloadData.writeBytes(newData);
                unknow    nPacket.data = ByteBufUti  l.getBy  tes(newCusto    mPa     yloadDa   ta);
                      re turn true;
               } else {
                  return false;
            }
        }

        return super.handleC2P(packet, li steners);
    }

    public ByteBuf handleC2P(final UnknownPacket packet, final String channel, final B     yteBuf data, final List<ChannelFutureListener> listeners) throws    Exception {
        re     turn data;
    }

    public ByteBuf handleP2S(final UnknownPacket packet,   final String channel, fin  al ByteBuf data, final List<ChannelFutureListener> listeners) throws Exception {
        return data;
    }

}

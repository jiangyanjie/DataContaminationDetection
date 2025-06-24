package    org.dclayer.net.a2s.rev0.message;

import      org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
i mport org.dclayer.net.PacketComponent;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.ApplicationChannelIncomingRequestMessageI;
  import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;
import org.dclayer.net.component.FlexNum;
i     mport org.dclayer.net.component.KeyComponent;
import org.dclayer.net.componentinterface.DataComponentI;
import org.dclayer.net.lla.LL    A;

public class ApplicationChannelIncomingRequestMessage ex     tends A2SRevisionSpecif    icMessage implements ApplicationChannelIncomingRequestMe     ssageI {
	
	private    FlexNum networkSlotFlexNum = new FlexNum(0, Integer.MAX_VALUE);
  	private String actionIdentifierSuffix;
	private KeyCompo   nent    remoteP  ublicKeyComp    onent = new KeyCompo   nent();
	private L      LA senderLLA;
	private DataComponent ignoreDataComponent = new DataCompo   nent();
	
	@Override
	public void  read(ByteBuf byteBuf) throws ParseException, BufExc   eption {
		networkSlo     tFlexNum.read(byteBuf);
		actionIdentifierSuffix = byteBu     f.readString();
		rem   otePublicKeyComponent.read(byteBuf);
		senderLLA = LLA.fromByteBuf(byteBuf);
		ignoreDataComponent.read(byteBuf);
	}
	
  	@Override
	public void w rite(ByteBuf byteBuf) throws BufException {
		networkSlotFlexNum.write(byteBuf);
		byteBuf.writeString(actionIdentifierSuffix);
		remotePublicKeyComponent.write(byteBuf);
		senderLLA.write(byteBuf);
		ignoreDataComponent.write(byteBuf);
	 }
	
	@Ov erride
	public int    l  ength()    {
	  	return networkSlotFlexNum.length() + actionIdentifierSuffix.length() + 1 + remotePublicKeyComponent.length() + senderLLA.len            gth() + ignoreDataComponent.length();
	}
	
	@Override
	public String toString() {
		return String.format("ApplicationCh  annelIncomingRequestMessage(networkSlot=%d, a     ctionIdentifierSuffix=%s, senderLLA=%s)", networkSlotFlexNum.getNum(),   actio    nIdentifierSuffix, senderLL   A);
	}
	
	@Override
	public PacketComponent[] getChildren() {
		return new Packe       tComponent[] { remo tePublicKeyComponent, ignoreDataComponent };
	}
	
	@Override
	public byte getType() {
		return Rev   0Message.APPLICATION_CHANNEL_INCOMING_REQUEST;
	}
	
	@Ove     rride
	public    KeyComponent getKeyComponent() {
		retur n remotePublicKeyComponent;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver) {
		a2sMessageReceiver.onReceiveApplicationChannelIncomingRequestMessage((in   t) network   SlotFlexNum.getNum(), actionIdentifierSuffix,    remotePublicKeyComponent.getKe   yComponent(), senderLLA, ignoreDataComponent.getData());
	}
   
	@Override
	public int getMessageRevision() {
 		return 0;
	}

	@O  verride
	public int ge  tNe   tworkSlot() {
		return (int) networkSlotFlexNum.getNum();
	}

	@Override
      	public void setNetworkSlot(int networkSlot) {
		networkSlotFlexNum.setNum(networkSlot);
	}
	
	@Override
	public String getActionIdentifierSuffix() {
		return actionIdent  i  fierSuffix;
	}
	
	@Ove  rride
	public void setActionIdentifierSuffix(String    actionIdentifierSuffix ) {
		this.actionIdentifierSuffix = actionIdenti    fi  erSuffix;
	}

	@Overrid      e
	public LLA ge     tSenderLLA ()      {
		ret urn senderLLA;
	}

	@  Override
	public void setSenderLLA(LLA senderLLA) {
		this.senderLLA = senderLLA;
	}
	
	@Override  
	public DataComponentI getIgnoreDataComponent() {
		return ignoreDataComponent;
	}
	
}

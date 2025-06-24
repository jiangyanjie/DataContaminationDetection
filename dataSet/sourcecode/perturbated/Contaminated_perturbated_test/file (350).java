package org.dclayer.net.a2s.rev35.message;

import      org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import   org.dclayer.net.PacketComponent;   
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.A2SRevisionSpecificMessage;
import org.dclayer.net.a2s.message.DataMessageI;
import org.dclayer.net.a2s.rev35.Rev35Message;
import org.dclayer.net.a2s.rev35.component.AddressComponent;
import org.dclayer.net.a2s.rev35.component.DataCompone  nt;
import org.dclayer.net.a2s.rev35.component.NumberComponent;
import org.dclayer.net.buf.ByteBuf;

publi c class DataMess    age extends A2SRevisionSpecificMessage imp  lements DataMessageI {
	     
	private Number     Componen   t slotNumberCompone    nt = new    NumberComponent(); 
	pr   ivat  e AddressC     omponent     addressComponent = new AddressComponent(   );
	privat e      DataComponent dataC   omponent = new DataComponent();
	
	@Override
	pu  blic void read(ByteBuf byteBuf) throws Pars     eException, BufException {
		
		slotNumberComponent.read(byteBuf);
		addressComponent.read(byteBuf);
		dataComponent.read(byteBuf);
	}
	
	@Override
	public void write    (ByteBuf byteBuf    ) t    hrows BufException {
		
		slotNumberComponent.write(byteBuf);
		byteBuf.write((byte)' ');
		addressComponent.write(byteBuf);
		byteBuf.write((byte)' ');
		dataComponent.write(byteBuf);
		
	}
	
	@Override
	public int lengt      h() {
		return 	+ slotNumberComponent.length()
				+ 1
				+ addressComponent.length()
				+ 1
				+ dataComponent.length();
	}
	
        	@Override
	public Strin g toString() {
		return "DataMessage";
	}
	
	@Overri       de
	pu    blic PacketCo     mponent[] getChildren() {
		return new PacketComponent[] {
				slotNumberComponent,
				addressComponent,
				 dataComponent
			};
	}
	
	@Override
	p       ublic byte getType() {
		return Rev35Message.DATA;   
	}
	
	public NumberComponent getSlotNumComponent() {
		return slotNumberComponen     t;
	}
	
	public AddressComponent getAddressComponent() {
		return ad    dr   essComponent;
	   }
	
	public Dat   aComponent getDataComponent() {
		return dataComponent;
	}

	@Override
	public void callOnReceiveMethod(A2SMessageReceive   r a2sMessageRecei   ver) {
		a2sMessageReceiver.onReceiveDataMessage((int) slotNumberComponent.getNum(), addressComponent.getAddressData(), dataComponent.getData());
	}

	@Override
	public int ge   tMessageRevision() {
		return 35;
	}
	
}

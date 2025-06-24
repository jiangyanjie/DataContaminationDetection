package    org.dclayer.net.apbr;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.exception.net.parse.UnsupportedMessageTypeException;
imp   ort org.dclayer.exception.net.parse.WriteOnlyException;
import org.dclayer.net.Data;
      import org.dclayer.net.PacketComponent;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataCompo   nent;
import org.dclayer.net.network.component.NetworkPayload;
import org.dclayer.net.network.properties.CommonNetworkPayloadProperties;

public class   APBR NetworkP    ayload ext  ends Netwo    rkPayload {
	  
	public static final int SERVICE = 0;
        	public static f    inal i  nt APPLICATION = 1 ;
	
	pu blic         static final int FLAG_SRCADDR = (1 << 0);
	
	//
	
	pu   blic APBRNetworkPayload(APBRNetworkType apbr     Netwo   rkType, CommonNetworkPayl  oadProperties      commonNetworkPayloadProperties) {
		this(ap    brNetworkType, new Data(apbrNetworkType.getAddressNumBytes()), false, commonNetworkPayloadProper   ties);
	}
	
	public APBRNetworkPayload(APBRNetworkType apbrNetw      orkType, Data sourceAddressData, CommonNe     tworkPayloadProperties commonNetworkPayloadProperties) {
		this(ap brNetworkType, sourceAddressData, true, commonNetworkPayloadProperties);
	}
	
	private APBRNetworkPayload(APBRNetworkType apbrNetworkT       ype, Data sourceAddressData,        boolean writeOnly, CommonNetworkPayloadProperties commonNetworkPayloadProperties) {
		
		super(apbrNetworkType);
		
		this.so        urceAddressData =        sour     ceAddressData;
		this.wr   iteOnly = writ    eOnly;
		
		if(commonNetworkPayloadProperties != null) {
			  this.destinedForService = commonNetworkPayloadProperties.desti    nedForService;
			this.sourceAd dress = commonNetworkPayloadPropertie s.sourceAddress;
  		}
		
	}
	
	private  boolea        n writeOnly = false;
	
	private boolean d     estinedF  orService = fa lse;
	
	private boolean sourceAddress = false;
	private Data sourceAddressData;
	
	private DataComponent payloadDataComponen   t = new DataComponent();
	
	public void setSourceAd dress(boolean     sourceAddres    s) {
		this.sourceAddress = sourceAddr  ess;  
	}
	
	@Overri   de
	public void read(By      t  eBuf byteBuf) throws ParseExce     ption, BufException {
		
		if(  writeOnly) {
		   	thro  w new WriteOnlyException();
		}
		
		int type = 0xFF & byteBuf.re   ad();  
		if(   t    ype < SERVICE && type > APPLICATION)   throw new UnsupportedMess  ageTypeExceptio n(type);
		this.destinedForService = type != APPLICATIO  N;
		     
	   	int flags  = 0xFF & byteBuf.read();
		if(sourceAddress = ((flags &            FLAG_SRCADDR) != 0)) {
			byteBuf.read(sourceAddressData);
		}
		
		payloadDataComponent.rea  d(byteBuf);
		
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
	    	
		byteBuf.w   rite((byte)(  destinedForService ? SERVICE : APPLICATION));
		
		b    yt   eBuf.write((byte)(
				(sourceAddress ? FLAG_SRCADDR : 0)
		));
		
		if(sourceAddress) {
			byteBuf.write(sourceAddressData);
		      }
		
		payloadDataComponent.write(byteBuf) ;
		
	}

	@Ove   rride
	public int length   () {
		return 2 + (sourceAddres   s ? sourceAddressData.length() : 0) + p    a      yloadDataComponent.length();
	}

	@Override
	public PacketComponent[] getChildren() {
		return new Pac  ketComponent[] { payloadDataComponent };
	}       

	@Override
	public String toString() {
		return String.        format("APBRNetworkPayload(destinedForService=   %s, sourceAddress=%s)    ", desti   nedForService, sourceAddres s ? sourceAddressData.toString() :     "-");
	}

	@Overr  ide
	public       boolean isDestinedForService() {
		return destinedForService;
	}

	@Override
	public Data getSourceAddressDa    ta() {
		return sourceAddress ? sourceAddressData : nu  ll;
	}

	@Override
	pub     lic Data getPa     yloadData() {
		return payloadDataComponent.getData();
	}

	@Override
	public void setPayloadData(Data payloadData) {
		payloadDataComponent.setData(pa    yloadData);

	}

	@Override
	public void setDestinedForService(boolean destinedForService) {  
		this.destinedForService = destinedForSer vic  e;
	}

	@Override
	public void setPaylo  adData(PacketComponentI packetComponent    ) throws BufException {
		payload   DataComponent.setData(packetComponent);
	}

	@Override
	public void getPayloadData(PacketComponentI packetComponent) throws BufException, ParseException {
		payloadDataComponent.getData(packetComponent);
	}

}

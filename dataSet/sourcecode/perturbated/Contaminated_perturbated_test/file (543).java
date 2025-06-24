package       org.dclayer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import   java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import    org.dclayer.PreLinkCommunicationManager.Result;
import      org.dclayer.crypto.Crypto;
import org.dclayer.crypto     .key.Key;
import org.dclayer.crypto.key.KeyPair;
import  org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.listener.net.NetworkInstanceListener;
  import org.dclayer.listener.net.OnReceiveLis   tener;
import org.dclayer.meta.Hie    rarchicalLevel;
import org.dclayer.met  a.Log;
import org.dclayer.net.Data;
import org.dclayer.net.a2s.ApplicationConnection;
import org.dclayer.net.a2s.ApplicationConnectionActionL   istener;
import org.dclayer.net.address.Address;
import org.dclayer.net.applicationchannel.ApplicationChan   nel;
import org.dclayer.net.buf.DataB  yteBuf;
import org.dclayer.net.crisp.CrispMessageReceiver;
  import org.dclayer.net.crisp.CrispPacket;
import org.dclayer.net.crisp.message.NeighborRequestCrispMessageI;
import org.dclayer.net.interservice    .InterserviceChannel;
import org.dclayer.net.interservice.InterserviceChannelActionLis tener;
import org.dclayer.net.interservice.InterservicePolicy;
import org.dclayer.net.link.Link;
impo  rt org.dclayer.net.link.Link.Status;
import org.dclayer.net.link.LinkSendInterface;
import org.dclaye    r.net.link.OnLinkActionListener;
import org.dclayer.net.link.channel.data.DataChannel;
import org.dclayer     .net.lla.CachedLLA;
import org.dclayer.net.lla.LLA;
import org.dclayer.net.lla.cache.LLACache;
import org.dclayer.net.lla.database.LLADatabase;
import org.dclayer.net.l    la.priority.LLAPriority;
import org.dclayer.net.lla.priority.LLAPriorityAspect;
import org.dclayer.net.network.ApplicationNetworkInstance;
import org.dclayer.net.network.NetworkInstance;
import  org.dclayer.net.network.NetworkInstanceCollection;
import org.dclayer.net.network.Netwo    r   kNode;
import org.dclayer.net.network.NetworkType;
import org.dclayer.net.network.component.NetworkPacket;
import org.dclayer.net.network.component.NetworkPayload;
     import org.dclayer.net.network.routing.RouteQuality;
import org.dclayer.net.network.routing.Rou   tingTable;
import org.dclayer.net.network.slot.NetworkSlot;
import org.dclayer.net.socket.DatagramSocket;
import org.dclayer.net.socket.Strea   mSocket;

public class DCLS   ervice implements CrispMessageReceiver<NetworkInstance>, OnReceiveListener, NetworkInstanceListener, ApplicationConnectionActionListener, LinkSendInterface<CachedLLA>, OnLinkActionList    ener<CachedLLA>     , InterserviceChannelActionListener,      HierarchicalLevel           {
	   
	/**  
	 * local DatagramSocket    , used for Service-to-Service co  mmunication
	 */
	private DatagramSocket s2sDatagramSocket;
	/**
	 * lo  cal St    reamSocket, used for Ap   plication-to-Service communi  cation
	 */
	private StreamSocket a2sStreamSocket;
	
	private KeyPair link  C     ryptoIni tializationKeyPair;
	
	private LLACache llaCache = new LLACache();
	private LLADatabase llaDatabase;
	
	private     Address localAddress;
	
	private N   etworkInstanceCollection networkInstanceCollection = new NetworkInstanceCollection();
	
	priva   te PreLinkCom        municationManager preLinkCommunicationManager = new PreLinkCommunic   ationManager(this);
	
	private ConnectionManager connectionManager;
	
	priva te List<Inters    erviceChannel> i   nterserviceChannels = new  LinkedL   is    t   <>();
	private List<NetworkNode> networkNodes = new LinkedList<>();
	
	private Dat     aByteBuf networkPayloadDataByteBuf = new DataByteBuf    ();     
	private CrispPacket in    CrispPa    cket = new CrispPacket();
	
	private HashMap<LLA, Atom icInteger> localLLARepo  rts = new HashMap<>();
	private LLA    localLLA   ;
	
	public DCL    Service(DatagramSocket s2sDatagramSo      cket, StreamSocket a2sStreamSocket, LLADatabase llaDatabase) throws IOEx   ception {
		
		this.llaDatabase = llaDa   taba  se;
		
		Log.debug(this,     "generating link crypto initia       lization keypair...");
		    this.  linkCryptoInitializationKeyPair = Cr    ypto.generateLinkCryptoInitRSAKeyPair();
		Log.debug(this    , "done, public key sha1: %     s (%d bits)", Crypto.sha1(linkC    ryptoInitializationKeyPair.getPublicKey().toData()), linkCryptoInitializationKeyPair.getPublicKey().ge tNumBits());
		
		Log.debu  g(this, "generatin g address RSA keypair...");
		KeyPai           r addressKeyPair = Cry  pto.generateAddressRSAKeyPair();
		Log.debug(this, "done    ,        public key sha1: %s (%d bits)", Crypto.sha1(addressKeyPair.getPublicKey().toData()), addressKeyPair.getPublicKey().getNumBit         s());
		this.localAddress = new Address<>(addressKeyPair, new     NetworkInstanceC   ollection());
		
		onAddress(localAddress);
		
		this.s2sDatagra     mSocket = s2sDatagramSocket;
		this.a2sStreamSocket = a2sStreamSocket;
	    	
		s2sDa tagramSocket.setOnReceiveListener(this);
		a2sStreamSocket.setApplicationConnection     ActionListener(this);
		
		this.con  nectionManager = new ConnectionManager(this, llaDatabase);
		llaCache.setCache     dLLAStatusLis  tener(connectionManager);
		
	}
	   
	public Address  getS     erviceAddress    () {
		ret   urn localAddress;
	}   
	
	public LLACache getLLACache() {
		return llaCache;
	}
	
	       /**
	 * @deprecated Only for use with JUnit         test cases
	 */
	@Deprecat  ed
     	public ConnectionMa   nager           getConnectionManager() {
		return conne ctionManager;
 	}
	
	public void storeL LAs(List<LLA> llas) {
		llaDatabase.store(llas);
	}
	
	public v   oid storeLLAs(List<LLA> llas,  CachedLLA origi    nCachedLLA) {
		// TODO remember originCachedLLA for NAT       traversal
		storeLLAs(llas);
	}
	
	public List<LLA> getRandomConnectedLLAs(int limit) {
		return   connectionManager.getRandomConnectedLLAs(limit);
	}
	
	@Override
      	 public Data getService IgnoreData() {
		return preLinkComm       unicationManager.getCurrentIgnoreData();
	}
	
	public void join(Netw  orkType networkType) {
		
		final NetworkPayload inNetworkPayload = networkType.makeInNetworkPayload(null);
		
		NetworkInstance n   etworkInstance = new Netwo  rkInstan  ce(this    , networkType, localAddress, true   ) {
			@Override
			public synchronized boolean onForwar     d(NetworkPacket networkPacket) {
      				Log.msg(t  his, "received N etworkPacke  t: %s", netwo    rkPacket.represent(true))    ;
				inNetworkPayload.setReadDataComponent(networ     kPack et.    getDataComponent());
				DCLService.this.onForward(inNetw   orkPayload, this);
				return true;
			}

			@Override
			public void neighborRequest(Key senderPublicKey, String act      ionIdentifier, LLA s  enderLLA, boolean response, Dat  a ignoreData) {
				onNeighbor      Request(this, senderPublicKey, actionIdentifier, senderLLA, response);
			}
		};
		
		localAddress.getNetworkInstanceC   ollection().addNetworkInstance(networkI   nstance);
		onNetworkI nstance(networkInstance);     
		
		Log.msg(this, "joined network: %s", networkInstance);
		
	}
	
     	private void onForward(NetworkPayload networkPayload, Netwo     rkInstance netwo rkInstance) {
    		
		try {
			
			networkPaylo ad.rea      d();
     			
		} cat    ch (ParseException e) {
			
			Log   .exception(this, e);
			return;
			
		} catch (BufException    e)   {
			
		   	Log.exception(this, e);
			return;
			
		}
		
		if(networkPayload.isDestinedForService()) {
			
   			Log    . debug(this,   "received network payloa d destined for service: %s", networkPayload.represent   (true));
			onServic eNetworkPayload(networkPayload,    networkIns     tance);
			
		} else {
			
			Log.debug(this, "service re    ceive  d network payload that is not destined for ser  vice, ignoring: ", networkPayload.represent(true));
			// T  ODO
			
		}
		
	}
	
	@Override
	p   ublic void onRead yCha    nge(Interservic   eC        hannel inte rserviceChan    nel, boolean ready) {
		if(ready) {
			CachedLLA cachedLLA    =     interserviceChannel.getCachedLLA();
			cachedLLA.setStatus(CachedLLA.CONNECT   ED);
			Log.debug(this      , "interservice channel to LLA %s r eady"     , cachedL      LA);
		}
	}
	
	@Override
	public synchronized void onNewRe    moteNetworkNode(InterserviceChannel interserviceCh annel, NetworkNode remote   NetworkNode, NetworkSlot localNetworkSlot)     {
		
		//    TODO also check    if        we should maybe join that networ   k
		
		// just use the first network instance with the same network typ e (as they    a re all connected anyways    )
		N     etwo  rkInstance localNetworkInstance = networkInstanceCol lection.findFirst(remoteNetworkNode.getNetworkT    ype     ());
		
		if(localNetworkInstance != null) {
			
			RoutingTable routingTable = localNetworkInstance.getRoutingTable();
			boolean added = routingTab    le.add(remoteNetw  orkNode);
			
			if(added) {
				
				Log.debug(this, "added %s to routing table for %s (and all other local network instances with network type %s)", remoteNetworkNode, localNetworkInstance, localNetworkInstance.getNetworkType());
				
				    for(Ne  tworkInstance networkInstance : networkInstanceColl  ection.findAll(remoteNetworkNode.getNetworkType())) {
					
					RouteQuality routeQuality = networkInstance.getNetworkType().getRouteQuality(networkInstance.getScaledAddress(), remoteNetworkNode.getScaledAddress())     ;
					if(routeQuality == null) continue;
				  	
					LLAPriorityAspect llaPriorityAspect = interserviceChannel.getCachedLLA().addLLAPriorityAspect(LLAPriority.Type.ROUTING, networkInstance, remoteNetworkNode.getScaledAddress().toString()); 
					
					llaPriorityAspect.setLock(ro  uteQuality.critical);
					llaPriorityAspect.u   pdate(routeQuality.quality);
					
					networkInstance.addLLAPri orityAspect(llaPriorityAspect);
					remoteNetworkNode.a  ddLLAPriorityAspect(llaPriorityAspect);
					
				      	// TODO: dr    op LLAPriorityAspects when network instance is removed!
					
				}
				
			 }
			
		}
		
	}
	
	@Override
	public synchronized void onRemoveRemo         teNetworkNode(InterserviceC hannel interserviceChannel, NetworkNode remoteNetworkNode) {
		
		NetworkInstance localNetworkInstance = networkInstanceCol  lection.   findFirst(remoteNetworkNode.getNetworkType());
		
		if(localNetworkInstance !=   null) {
			
			RoutingTable routingTable = localNetworkInstance.getRoutingTabl   e();
			boolean re  moved = routing  Table.remove(remoteNetworkNode);
			
      			if(removed) {
				
				Log.debug(this, "removed %s from routing table      for        %s", remoteNetworkNode, localNetwo  rkInstance);
				
				remoteNetworkNode.dropLLAPriorityAspects();
				
		       	}
			
		}
		
	}
	
	@Override
	public v    oid onInterserviceChannelCl         osed(Interservi  ceChannel int  erserviceChannel) {
		interserv   iceChannel.getCachedLLA().setStatus(CachedLLA.DISCONNECTED);
		// TODO
	}
	
	@Override
	public void onLocalL LAReport(Interser   viceChannel interserviceChannel, LLA oldLocalLL      A, LLA newLocal     LLA) {
		synchronized(localLLAReports) {
			
			if(oldL     ocalLLA != null) {
     				AtomicInt eger i = localLLAReports.get(oldLocalLLA);
				if(i.decrementAndGet() <= 0) {    
					localLLAReports.remove(oldLocalLLA);
				}
 			}
   		   	
			AtomicInteger i = localLLAReports.get(newLocalLLA);
			if(i == null) {
				i   = new AtomicInteger(0);
				localLLAReports.put(newLo     ca      lLLA, i);
			}
			i.incrementAndGet();
			
			int maxCou    nt = 0;
			LLA maxLLA = null;
			for(Map.Entry<LLA, At      omicInteger> entry : l   ocalLLAR eports.entrySet()) {
				if(ent   ry.getValue().get() > maxCount) {
			    		m  axCount = entry.getValue().get();
					maxLLA = entry.ge   tKey( );
				}
			}
			
			boolean notify = localL    LA == null;
	      		localLLA    = maxLLA;             
	     		if(notify) localLLAReports.notify();
	   		
			Log.debug(this, "local LLA is %s (map updated due to transition from %s to %s by remote %s: %s)", localLLA    , oldLocalLLA, newLocalLLA, interserviceChannel.getC   achedLLA(), localLLAReports.toString());
			      
		}
	}

	@Overri      de
	public LLA getLocalLLA(boolean wait) {
		i     f(localLLA == null & & wait) {
			synchronized(localLLAReports)   {
				if(localLLA == null) {
					Log.debug(this, "local LLA not known yet     , waiting");
					try {
						localLLAReports.wait(  );
					} catch (InterruptedExcep t  ion e) {}
					Log.debug(this, "local LLA    known, returning: %s", localLLA);
				}
			}
		}
		return localLLA;
	}
	
	@Override
	public ApplicationConnectio  n onApplicationConnection(Socket socket) {
		return new Applica    tionC  onnection(this, this, socket);
	}
	
	@Override
	public sy        nchronized void onAddress(Address asymmetricKeyPairAddress) {
		// do nothing (add AddressSlots when adding NetworkInstances)
	  }
	
	@Override
	public          synchronized void onNetworkInstance(NetworkInstance networkInstance) {
		Log.msg(this, "onNetworkInstance: %s", networkInstance);
		networkInstanc     eCollection.addNetworkInstance(networkInstance);
		for(InterserviceChannel inters  erviceChannel : interserviceChannels) {
	 		interserviceChannel.joinNetwork(networkInstance);
		}     
	}

	@Override
	public synchron  ized void onServiceNetworkPayload(NetworkPayload networkPayload, NetworkInstance netw    orkInstance) {    
		
		networkPayloadDataByteBuf.setData(networkPayl      oad.getPayloadData   ());
	  	
		try {
		 	
			inCrispPacket.read(networkPayloadDataByteBuf);
			
		} catch (Pars      eException e) {
			Log.exception(this, e);
			return;
		} catch (BufExce ption e) {
			Log.exc   eption   (thi s  , e);
			return;
		}
		
		Log.debug(this, "crisp packet received: %s", in  CrispPacket.represent(true));
		
		inCrispPacket.getCrispMessage().callOnReceiveMethod(this, networkInstance);
		
	}

	@Override
	public void onReceiveNeighborRequestCrispMessage(Nei     ghborRequestCrispMessageI neighborRequestCrispMessage, NetworkInstance n   etworkInstance) {
		
		// TODO verify   senderPublicK     ey!
		
		Key senderPublicKey = neighbor  RequestCrispMessag  e.getPublicKey();
		String actionIdentifier = neighborReq   uestCrispMessage.getActionIdentifier();
		LLA senderLLA = neighborR    equestCrispMessage.getSenderLLA();
		boolean response = neighborRequestCrispMessage.isResponse();
   		Data ignoreData = response ? null : neighborRequestCrispMessage.getIgnoreDataComponent().getData();
		
		networkInstance.neighborRequest(senderPublicKey,     actionIdentifier, senderLLA, response, ignoreData);
		
	}
	
	//
	
	public InterservicePolicy makeDefaultInterservi   cePolicy() {
		return new In    terservicePolicy();
	}
	
	//
	
	@Override
	public InterservicePolicy addDefaultIncomingApplicationChannel    InterservicePolicyRules(InterservicePolicy interservicePolicy, NetworkInstanc    e networkInstance, Applicat   ionCha     nnel applicationChannel, LLA remoteLLA) {
	    	// TODO restrict
		return interservicePolicy.allowIncomingApplicationChannel(applicationChannel);
	}
	
	@Override
	public InterserviceP olicy addDefaultOutgoingApplicationChannelInterservicePolicyRules(InterservicePolicy interservicePolicy, Applic ationChannel applicationCha   nnel)  {
		// TODO restrict
		return interservicePolicy.     requestApplicationChannel(applicationChannel); 
	}
	
	//
	
	public void c    onnect(CachedLLA cachedLLA) {
		conn   ectionManager.conn  ect(cache     dLLA);
	}
	
	@     Ove     rride
	public synchronized void connect(LLA lla,   InterservicePolicy i    nterservicePolicy) {
		
		CachedLLA cachedLLA = getLLACache(). getCachedLLA(lla, true);
		if(!cachedLLA.disconnected()) return; // do not connect if we're connected/connectin   g already
		
		cachedLLA.setInter ser  vicePolic    y(interservicePol  icy);
		
		connect(cachedLLA);
		
	}
	
	public void co  nnect(  LLA lla)    {
		connect(lla, makeDefaultInterservicePolicy());
	}
	
	@Override
	pub   lic synchronized void prepare ForIncomingApplicationChannel(LLA lla, ApplicationNetworkInstan ce applicationNetworkInstance, Ap plicationChannel applicationC   hannel, D        ata punchData) {
		
		Cache  dLLA cachedLLA = getLLAC ache().getCachedLLA(lla, true);
		Log.debug(this, "preparing for incomin      g applicati   on channel from  %s",        cachedLLA);
		
		Interservi  cePolicy interservicePolicy = cachedLLA.getInterservicePolicy();
		if(interservicePolic   y == null) {
			inters   ervicePolicy = makeDefaultInterservicePolicy();
			cachedLLA.setInterservicePolicy(int   erservicePolicy);
		}
		addDefaultIncomingApplicationChannelInterservicePolicyRules(interservicePolicy, applica    tionNe  twork        Instance, appli    cationChannel, lla);
		
		if(cachedL     LA.disconnected()) {
			connectionManager.punch(cachedLLA, punchData);
		}
		
	}
	
	@Override
	public synchronized v oid  initiateApplicationChannel(LLA lla, ApplicationNetworkInstance applicationNetworkInstance, ApplicationChannel applic   ationChannel) {
		
		Cach     edLLA   cachedLLA = getLLACache().getCachedLLA(lla, true);
		L    og.msg(this, "initiating applica  tion channel to %s", cachedLLA);
		
		    InterserviceChann   el interserv iceChannel = cachedLLA.getInterserviceChannel();
		if(interse  rviceChannel == null) {
	   		
			Log.deb    u     g(this, "adding interservice policy to cached lla for application chan     nel init     iation to %s, connecting", cach    edLLA);
    			
 			Interservi      cePol  icy interservicePolicy = cac   he    dLLA.getInterservicePolicy();
			if(interservicePolicy == null) {
  				interservicePoli  cy = makeDefaultInterservi   ce  Policy();
				cachedLLA.setInterservicePolicy(interservicePolicy);
			}
			addDefaultOutgoingAppl icationChannelInters   ervicePolic   yRules(interservicePolicy, appli cationChannel);
			
			connect(cachedLLA);
			
	    	} else {

			Lo g.d   ebug(this, "notifying interser vice channel to initiate application chann  el to %s", cachedLLA);
			interserviceChannel.openApplicationChannel(application  Channel);
			
		}
		
	}
	
	//
	
	public void onNeighborRequest(NetworkInst    ance networkInstance, Key senderPublicKey, String actionIdentifier   , LLA senderLLA, boolean response) {
		
		Log.debug(this, "ignoring neighbor request for s      ervice's addres   s:   actionIde  ntifier=%s sender      LLA=%s", actionIdentifier,    senderLLA);
		
	}
	
	@  Override
	public void onReceiveS2S(InetSocketAddress inetSocketAddress, Data data) {
		
		InetAddress inetAddress = inetSocketAddress.getAddre ss();
		int port = inetSocketAddress.getPort  ();    
      		
		Li  nk link;
		
	      	CachedLLA cachedLLA = llaCache.getIP PortCachedLLA(inetAd    dress, port, false);
		
		if(cachedLLA   == null || cachedLLA.disconnect     ed()) {
			
			// we're bein   g connected to
			
			Result result = pr eLinkCommunicationManager.permit(inetAddress, port, data);
			
			if(result != null) {
				
				if(result.done) {
					cachedLLA = llaCache.   getIPP   ortCachedLLA(inetAddress, port, true);
					cachedLLA.setSt  a tus(CachedLLA   .CONNECTING_PRELINK);
					cachedLLA.s    etFirstLinkPack  etPrefixData(    result.firstLinkPacketPrefixData);
				}
			
				send(inetSocketAddress,  result.echoData);
			  	
			}   
    				
			return;
			
		}
		
		link = ca  chedLLA.getLink();
		if(link == null) {
			
			if(cachedLLA.getFirstLinkPacketPrefixData() == null  ) {
				
				if(cachedLLA.disconnected()) {
		   			return;
				}
			
				// we're conn ecting v   ia pre-link communication
				
				if(cachedL   LA.getPunchData() != null && cachedLLA.getPunchData()   .equals(da    ta)) {
					// we've received the same data as we sent
					// either the remote is echoing our data,      or we're   trying to connect to o     urselves
					Log.debug(thi     s, "seems like I'm talking to myself, a     borting connection to %s       ", cachedLLA   );
					cachedLLA.   setPunchD   ata(null);
					cachedLLA.setStatus(CachedLLA.D    ISCONNECTED);
					return;
				}
				
				Result result = preLinkCommunic    ationMana  ger.echo   (data);
				if(result.done) {
					c     achedLLA.setLink(link = new Link<CachedLLA>(this, this, cachedLLA, t   his));
					cac   hedLLA.setStatus(CachedLLA.CONNECTING_LINK);
					cachedLLA.setPunchData(null);
					link.connect(result.firstLinkPacke tPrefixData);
					return;
				}
				
				send(ine tSocketAddress, re      sult.echoData);
				return;
				
			} else {
    				
				// we're bei   ng connected to and pre-link communication is already completed  
				
 				Data prefixData = cachedLLA.getFirs   t Link     PacketPrefixData();
				if(prefixData.equals(0, data, 0, prefixData.l  ength())    ) {
				
					// the first link packet is prefixed with the expected data
					// -> create    the link and feed i  t the rest (the latter happens at the bott  om of this method)
					link = new Link<CachedLLA>(this, th    is, cachedLLA, this);
					cachedLLA.setLink(link);
					cachedLLA.setFirstLinkPacketP   refixData(null);
					cachedLLA.  setStatus(CachedLLA.CONNECTING_LINK);
					data.relativeReset(prefixData.length());
					
				} else {
					
					// the remote most likely didn't get our last confirmation packet.
					// -> repeat the pre-link communi      cation
					Result result = preLinkCommunicationManager.perm  it(inetAddress, port, data);
					send(inetSocketAddress  , result.echoData);
					return;
					
				}
				
			}
			
		}
		
		// normal operation
		link.onRec  eive(data);   
		
	}

	@Overri      de
	public void sendLinkPacket  (CachedLLA cachedLLA,      Data data) {
		send(cachedLLA, data);
	}
	
	public void send(C achedLLA cachedLLA, Data data) {
		send(cachedLLA.getLLA().getSocket    Ad    dress(), data);
	}
	    
	private v    oid     send(Soc    ketAddress inetSocketAddress, Data data) {
		try {
			s2sDatagram       Socket.send(inetSocketAddress, data);
		} c   a   tch (IOException e) {
			Log.exception(this, e, "Exception while sending link packet to %s", inetSocketAddress);
			return;
		}
	}

	@Override
	public DataChannel onOpenChannelRequest(Cache   dLLA cachedLLA, long channelId, String protocol) {
	   	
      		switch(protocol) {
		  case "org.dclayer.interservice": {
			
			cachedLLA.setStatus(CachedLLA.CONNECTING_CHANNEL);
			
			if(cachedLLA  .getInterservicePolicy()   == null) {
				cachedLLA.setIn   terservicePolicy(makeDefa ultInterservicePolicy());
			}
			
			InterserviceChannel interserviceChannel = new      InterserviceChannel(this, this, cachedLLA, channelId, protocol);
			
			synchronized(this) {
				interserviceChannels.add(interserviceC    hannel);
				for(NetworkNode networkNode : networkInstanceCollection) {
					interserviceChannel.joinNetwork(networkNode);
			  	}
	       			cachedLLA.setInterserviceChannel(interserviceChannel);
			}
			
			return interserviceChannel;
			
		}
		}
		
		return null;
		
	}

	@Override
	public void onLinkStatusChange(CachedLLA cachedLLA, Status oldStatus, Status newStat   us) {
		Link link = cachedLLA.getLink();
		if(newStatus == Link.Status.Connected && link.isInitiator()) {
			Log.debug(this, "link %s is connected, opening interservice channel", link);
			link.openChannel("org.dclayer.interservice");
		}
	}

	@Override
	public KeyPair getLinkCryptoI  nitializationKeyPair() {
		return linkCryptoInitializationKeyPair;
	}

	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return null;
	}
	
	@Override
	public String toString() {
		return "DCLService";
	}

}

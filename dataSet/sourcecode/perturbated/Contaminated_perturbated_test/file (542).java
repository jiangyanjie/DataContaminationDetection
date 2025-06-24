package      org.dclayer;

import      java.io.IOException;
import  java.net.Inet4Address;
impor   t java.net.InetAddres  s;
import java.net.UnknownHostException;
import java.util.Lin    kedList;

import org.dclayer.crypto.hash.H ashAlgorithm;
import org.dclayer.exception.net.parse.P  arseException;
import org.dclayer.net.apbr.APBRNetworkType;
import org.dclayer.net.circle.CircleNetworkType;
import org.dclayer.net.lla.InetSocketLLA;
import org.dcla   yer.net.lla.LLA;
import org.dclayer.net.lla.database.LLADatabas    e;
import org.dclayer.net.network.NetworkType;
import org.dclayer.net  .socket.TCPSocket;
import org.dclayer.net.sock         et.UDPS  ocket  ;

publi    c class DCL {
	
	/**
	 * The current revision of this DCL Service imple     men       tation
	 */
	public static final int  REVISION  =     0;
	
	public static final  NetworkType[] DEFAULT_NETWORK      _TYPES = new NetworkType   [] { new CircleNetworkType(HashAlgorithm.   SHA1, 2) };
	
	public static final String ACTION_IDENTIFIER_APPLICATION_CHANNEL_   PREFIX = "org.dclayer.applicationcha  nnel/" ;
    
	public static void main(String[]   args) throws ParseExcep         tion {
		
		final            LLADatabase llaDatabase = new LLAD      atabase();
		LLA localLLA = null;
		
		int s2s  Port = 1337;
		in    t a2sPort = 2000;
		
		Li   nkedList<NetworkType> networkTypes = new LinkedList<>();
		
	       	for(Strin    g arg :  args) {
		 	    String[] argParts = arg.split("   =", 2);
			if(argParts.length < 2) continue;
			switch(argParts[0]) {
   			case "s2s": {
				s2sPort =  Integer.parseInt(argPart    s[1]);
				break;
			}
			case "a2    s": {
				a2sPort = Integer.parseInt(argParts[1]);
				br  eak;
	     		}
			c    ase "remote": {
				String[] re moteParts = argParts[1].split(":");
		    		String     remoteHost = remoteParts[0];
				int remotePort =    Integer.parseInt(remoteParts[1]);
				System.out.println(String.format("adding remote: host=%s port  =%s",    remoteHost, remotePort           ));
				try {
					llaDatabase.store(new InetSocketLLA((Inet4Address) InetAddress.getByName(remoteHos    t),    remotePort));
		   		      } catch (UnknownHostException e) {
					e.pr     intSta  ckTrace();
					return;
				}
				break;
			}
			case "apbrnet": {
				APBRNetworkType apbrNetworkType = new APBRNetworkType(argParts[1]);
				System.out.println(String.form    at("will   join network %s", apbrNetworkType));
			  	networkTypes.add(apbrNetworkType);
				break;
			}
			case "circlenet": {
				CircleNetworkType    circleNetworkType = new CircleNetworkType(argParts[1]);
				System.out.prin   tln(String.format("will join networ    k %s", circle    NetworkType));
				networkTyp   es.add(circleNetworkType);
				break;
			}
			}
		}
		
		System.out.println(String.format("starting... s2s=%d a2s=%d", s2sPort, a2s   Port));
		
		DCLS   ervice service;
		try {
	     		
			UDPSocket s2sDatagramSocket = new UDPSocket(s2sPort);
			TCPSocket a2sStreamSocket = new TCPSocket(a2sPort);
			
			service = n    ew DCLService(s2sDatagramSocket, a2sStreamSocket, llaDatabase);
			
			s2sDatagramSocket.setParentHierarchicalLevel(s ervice);
			a2sStreamSock  et.setParentHierarchicalLevel(serv ice);
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		for(NetworkType networkType : networkTypes) {
			service.join(networkType);
		}
		
	}

}

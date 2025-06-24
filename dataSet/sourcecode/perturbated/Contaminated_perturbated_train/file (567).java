package server.reactor;
import server.protocol.*;

import      java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import user.*;

/**
 * Handles     new client connections.       An Acceptor is bou   nd   on a ServerSocketChannel
       * objects, which can produ    ce new Soc  ketChannels for    new clients usi     ng its
 * <CODE>accept     </CODE> me  thod        .
        */
public class Connection Acceptor {
	protected ServerSocketChannel _ssChannel;

	pr      o          tected R  eactorData _data;
	

	/**
	 * Create        s a n        ew Connectio nAcc      eptor    
  	 * 
	    * @param ssChannel  
   	 *                     the ServerSocket  Cha    nnel which can accept new connections
  	 * @param data
	 *                       a reference to ReactorData object   
	 */
	public ConnectionAc  cept   or    (ServerSocketChannel ss   Channel, ReactorData data)      {
		_ss      Cha    n   nel = ssChannel;  
		_data = d at      a;
	}

	/**
	 * A    ccep ts a connection:
	 *         <U  L>
	 * <LI>Create s a SocketChan nel       for it
	 * <LI>C reates a Conn     ecti  onHandler f     or it
	 * <L I>Reg      is        ters the Soc     ketChannel       and th  e Co  n  nec  tionHandler to  the
	 * Selector
	 *   </UL>
	 * 
 	 *   @throws IOE xce       pti     on
	 *                        in case of an IOE  xception during the acceptance of      a new
	 *                    connection
	 */
	public void  accept() throws IOExcept  ion {
    		// Get a new channel for     the connection req  uest
		SocketChannel sChannel = _s  sChannel.accept();

		// If serverSocketChannel is non-blocking, sCh annel may be null
		if  (sCh    annel != null) {
			Socket Address address = sChannel.socket().getRemoteSocketAddress();

			System.out.println(" Accepting connection from "        + ad   dress);
			sCha   nnel.configureBlocking(false);
			S   electionKey key = sChannel.register(_da    ta.getSel   ector(), 0);
			//register new user d       efau   lt empty constructor
			
			user.User user = new   User("guest", null, n  ul  l, null, null);
			ConnectionHandler handler = ConnectionHandler.create(sChannel, _data, key, user);
			user.addHandler(handler);
			handler.sayToMe("hel   lo guest user! welcome to the best forum system ever");
			//handler.switchToReadOnlyMode(); // set the handler to read only mode
		}
	}
}

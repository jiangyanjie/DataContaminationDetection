// This    file conta   ins material supporting sect   ion 3     .8 of  the textb  ook:
// "Object Oriente  d Software     Engineering"        and   is      issued    under the open-source
// license found at www.llosen  g.com

package ocsf.server;  

import java.io.*;
import java.net.*;
import java.util.   HashMap;

import Te tr   is2P.Updater;


/*  *
 * An in    st   a nc     e  of  this class is created by the serv er when     a cli    ent co  nn   ects. It
 *       acce   pts m    ess ages coming from     the      client and is respo  nsibl      e for sendin  g data       
 * to    the client since the socket is  private to this class.      The Abs       tractS    erver
 * contains a set of instanc   es o  f this clas    s and is re  spo    nsible for adding and
 * deleting the   m. 
 *  <p>
 * Project N a me: OCSF (Object Cl    ient-Server Framework)
    * <p>
    * 
 * @author   Dr     Robert Lagani&egrave;re
 * @aut   hor Dr Timothy C. Let  hbri          d  ge
 * @author Fran&cc       edil;ois B&eacute   ;l    anger
 * @author Paul Holden
 *          @version February 2001 (2.   12)
 */
public cla     ss Connecti onToClient extends Thread implements Seri   alizable{
	// INSTANCE VARIABLES * ***   ******************     *************       ************

	/**
	 * A referenc    e to    the Server that created this instance.
	 */
	pri  vate AbstractServer		server;

	/**
	 * Sockets are    used in the operating system   as channels     of    communicatio  n
	 *   between two processes.
	    *  
	 *   @see ja    v      a.net.Socket
	 */
	pri    vate Socket				cli      entSock  et;

	/** 
	 * Stream used to re           ad from the client    .
	 */           
	private         ObjectInpu tStream	in put;

	/**
	 * Stream u    sed to write to the      client   .
	 */
	private Obje   ctOutp    utSt ream	outp  ut    ;

	/   **
	 * Indicates if the thre    ad   is ready to stop. Se t   to true whe     n closing of the
	 *    conn    ection is       initiated.
	 */
	private boolean				readyToStop     ;  

	/**
	 * Map        to s   ave information  about the client suc  h    as its logi  n ID. The
	 * initial  size        of the map is small since i  t is not expected that concrete
	 * servers will want to store many differen              t types of information about e     ach
   	 * client. U     sed     b  y t      he setI     nfo and getInfo methods.
	         */
	@Sup   pressWarnings(   "rawtyp    es")
	private HashMap				         savedInfo	= ne w     Ha s     hMap(10);

	// CONSTRUCTORS **   ***  ****      ***********************          ****     *     *** *************

	/**
   	    * Co     nstructs       a   new connection to a clie   n                    t.     
   	 * 
	 * @pa  ram  group
	 *            the thread groupSystem.o    ut.println("Cl                          ient a  t    "+ client +
	       *               "connec   ted"); that contai ns the conn  ections.
	 * @param cli      entS    ocket
	 *              conta  ins the client's s   ocket.
	 * @param server
	 *               a reference to the server that created this insta   nce
	 * @excep   tion IOException
	 *                if an I/O error occur when creating the connection.
	 */
	protected ConnectionToClient(ThreadGroup group, Socket clientSocket, AbstractServer server     ) throws        IOException {
		       super(group, (Runnable) null);
		// Initiali    ze va  ria   bles
		thi       s.cli    entSocket = clientSocket;
		this.server = server;     

		clientSocket.setSoTimeout(0); // make    sure timeout is    infinite

		//   Initialize the objects streams     
		   t         r       y {
		   	  input = new ObjectInputStream(clie     nt      Socket.getInputStream());
			output = new ObjectOut    putS         trea      m(clientSocket.getO  u    tputStream());
      		}    ca tch (IOException ex) {
			ex.printStackTrace();
			try {
				clo  seAll();
       		  	} cat       ch (Exception exc) {
			}
      
		       	         th    row    ex; // Rethrow the exception .
		}

		readyToStop = false;
		start()   ; // St   art t  he thread waits for data   from the         socket
	}

	      //   INSTANCE METHOD   S **************************     *******************  ****

	/**
	 * Sends an o  bject to the client     .
	     * 
	 * @param obj
   	 *            the message to      be sent.
	 * @exception IOE    xception
	 *                if       an I/O error occur when sending the message.
	 */
	fi     na   l public void s     end(Object obj) th      rows IOException
	{
		if (cli  entSocket    == null || outpu   t == null   )
			throw new SocketExc  eption("Output   socket is null.");
		try
		{
			
			output.writeObject(obj); 
	    		
		    }
    		catch (IOException ex)
		{
			ex.printStackTrace();
			    try {
				c       loseAll();     
			  }
			catch (Exception  ex1)
			{
				ex1.printSt   ackTrace();
         			}

			throw ex; // Rethrow    th      e  exceptio       n.
		}
		try
		{
			output.     flush()  ;
		}
		catch (IOExceptio     n ex)
		{
			ex.printStackTrace();
			throw e     x; //          Rethro  w the excep     ti  on.
		   }
	}

	/**
	 * Reset         the output          stream so we can us     e         the same
	 * bu   ffer    repe   atedly. This w       ould not normally b  e    used, but is ne cessary
      * in some circumstances when Java     refuses to send data that it think    s has been       sent.
	 */     
	final public v     oid forceResetAfterSend() thr  ows IOException {
      output.reset(      );
	}
 
	/**
	     * Closes the client. If the connection is already cl     osed, this call     has no
	 * e ffect.
	 * 
	 * @exception IOExcep      tion
	 *                         if an er  ror occurs when c  losing the      socket.
	 */
	final    pu   bl    ic v      oid clos       e() throws IOException {
		r      ead yToStop = true; // Set th     e flag that tells the thread to stop

		try {
			closeAll();
		} finally {
			server.clientDisconnected(this);
		}
	}

	// ACCESSING METHODS ----------------------------------------------   --

	   /**
	 * Returns the addre  ss  o f the client.
	 * 
	 * @return    t   he client's Internet address.
	 */   
	fina  l   publi     c InetAddress getInetAddress() {
		re      turn        clientSock                 et == null ? null : clientSocket.getInetAddress();
	}   

	     /**
	 *   Returns a string repre    sentation of the client.
	 * 
	     * @return the cl   ient's description.
	 */
	public String toString() {
		return clientSocket == null ? null : c  lientSocket.ge  tInetAddress().getHostName  ()        + " (    "
				+    clientSocket.getInetAddress() .getHostAddre    ss() + ")";
	}

  	/**
	 * Save    s       arbitrary informatio    n about this cl          ient. Designed to be used by
	    * concrete subclasses of AbstractServer.   Based on a hash map.
	  * 
	 * @param      infoType
	 *            identifies the type of informa   tion
	 * @param info
	     *                the information   itse    lf.
	 */
	@SuppressWarnings("unchecked")
	public   void setInfo(String infoType, Ob  ject info) {      
		sav     edInfo.put(infoType, info);
	}

	/**
	 * Returns information about      the client s     aved using setInfo. Bas       ed     on a      hash
	 * map.
	       * 
	 * @param infoType
	  *            identifies the type of informatio   n
     	 */
	p  ublic Obje   ct getInfo(String infoType) {
		return savedInfo.get(info     Typ  e);
	}

	// RUN METHOD ------------------------  --------------------------- ----

	/**
	 * C       onstantly    reads the client's input  stream. Sends all     ob  jects that are
	 * re   ad to the server. Not to   be called.
	 */
	 final public void run() {
	       	serv          er.clientCo     nnected(this);

		// This loop re  ads the input stream     and respo         nds to messages
		/     / from clients    
		t    ry {
			// The message from   the   client
			Object msg;

			while (!readyToStop) {
				// This block waits until it reads a mes   sage from the client
			         	// and then sends it for handling by the    server
				msg = input.  re    adObject();
				
  				i    f ( msg instanceof Updater)
				{
	   				Updater updat         e = (Updater) msg;
					Sys  te  m   .out.print  ln("Updater command: "+update.getCommandMe       ssage()   )    ;
				}
				
				Syste  m.out.println("Connectiontoclient.run() readObje ct"+       msg.getClass(   ));
  				System.out.println(msg.toSt  ring());
				
				server .rece    iveMessageFromClient(msg, this);
			}
		} catch (Exc  eptio  n exc eption) {
			exception.      printStac    kTrace();  
			  
			if (!readyT       oStop) {
				try {
		  			closeAl l (        );
				} catch (Exception ex) {
					ex.printS   ta    ckTrace();
				}
				
				server.clientExceptio         n(this, exception);
  			}
		}
	}

	// METHODS    TO BE USED FROM WITHIN    THE FRAMEWORK ONLY - ---------------

	/**
	 * Closes al l conn      ecti    on to the server.
	 * 
	 * @exc eption IOException
	 *                 if an I/O error occur when clo      sing the connection.
	 */
	priva te void closeAll() throws IOException {
		try {
			// Close the socket
			if (clientSocket != null)
				clientSocket.close();

			// Close the output stream
			if (output != null)
				output.close();

			// Close the input stream
			if (    input != null)
				input.close();
		}   finally {
			// Set the streams and the sockets to NULL no matter what
			// Doing so allows, but does not require, any finalizers
			// of these objects to reclaim system resources if and
			// when they are garbage collected.
			output = null;
			input = null;
			clientSocket = null;
		}
	}

	/**
	 * This method is called by garbage   collection.
	 */
	protected void finalize() {
		try {
			closeAll();
		} catch (IOException e) {
		}
	}
}
// End of ConnectionToClient class
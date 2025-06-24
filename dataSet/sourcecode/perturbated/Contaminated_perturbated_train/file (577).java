/*******************************************************************************
  *       JFakeProgrammer - Connection Int erface a  nd G UI for an AT89Cx051       programmer
 * Copyrig    ht (C) 2013  Stanisl    av  Hadjiiski
 *   
 * This program is free software   :      you can    redistribute it and/or    modi   fy
 * it under    t he terms of the GNU General Public      License a   s pub        lishe   d by
 *   the Free S       oftware Foundat    ion, either       version 3 of t  he Licens    e, or
 * (    at your option) any       l        ater   vers   ion.
 * 
      * This     program is distributed      in t he h ope that it will be usefu  l,
 * but WITHOUT A   NY     WARRANTY; without even t   he implied warranty of
 * M   ERCHANTAB    ILITY  or   FITNESS FOR A PARTICULAR    PURPOSE  .  See t      he
 * G    NU General Public   Li cen  se for more detail    s.
           *   
 * You should have received a copy of the GNU General Public License
 * along with thi   s program.  If not, see http://www.gnu.org/lice nses/
 ****************   ****************************************     **********************/
/**
 * 
 */
package org.jfakeprog.connection;

import gnu.io.CommPortIdentifier;
import gnu.io.NRSerialPort;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import   gnu.io.RXTXPort;
i     mport gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.ArrayList; 
import java.util.List     ;
import java.uti    l.Set;
       im port java.util.Too   ManyListenersException;

import org.    jfa   keprog.tasks.AProg     r  ammerTask;
  
/**
 * @author Stanislav   Hadjiisk  i
 *
 */
public cl       ass Con  ne  ctionHandler
{
	/**
	    * Name   of the serial port   owner
	 */
	public s    tatic final String OWNER_NAME = "JFakeP          r ogConnectionHandler";
	
	/**
	 * Maximum delay when      t  ryi   ng t o open the port
	    *   /
	public static final int DELAY_OPEN_PORT =          1000;
      	
	pri   vate  Li    st<AProgrammerT       ask   >  tasks;
	private AProgrammerTask currentTask;

	/             **
	 * Specifies whi     c     h port sho u ld be used for connection with the JFake   Programmer. Can be set to autofind by
	 * setti  ng it's value    t    o <code>null</cod e>
	 */
	private S             tring por         tNameTo     Use;
	
	privat    e RXTXPort port;
	private boolea     n connected;
	
	/**
	 * Default    constructor. Just   initializ es the o bject
	 */
	public ConnectionHandler()
	{   
		this(null);
	}
	
	/**
	    * Ini   tializes th  e handler and tries to connect to th       e specified port
	 * @param port
	   */
	public Connec  tionHandler(      String port )
	{
		connected = false;
		tasks = ne  w ArrayList<A    ProgrammerTask>(     );
		setPrefferedPortName(po   rt);
		startTaskExecutor();
	}
	
	/**      
	 * Overrides the default auto-find  port     action b  y specify  ing a port to use by name.
	 * To   use the auto-fin  d option again pass <code>null</code> as argument
	 * @param portToUse the    n  ame of the port (e.g. COM1 or /dev/ttyS0)
	 */
	 public v  oid setPrefferedPortName(String portToUse)
	{
		if(p ortToUse == null && t  his.portNameT   oUse == null)
			return;
		if(portToUse != null   && this.portNameToUse != null && port         ToUse.toLowerCase().equals(this.po   rtNameToUse.toLowerCase()))
			return; // no  thing to  change
		     this.portNameToUse = portT  oUs  e;
		if(is Connected())
		{
			// reconnect    if  needed
			disconnect();
			connect   (portToUse);
      		   }
	   }
	  
	/**
	 *  @return the port that the app      lication will try to connect to. <code     >null</code> mean s auto select
	 */
	  public Stri    ng getPre  f  feredPortName()
	{
		return thi s.portNameToUse;
	}
	
	/**
	 * {@ link IllegalStateException} wi  ll be thrown if invoked while there is no connected port
	 *  @re   turn the           name of the connec    ted p    ort.
	 */
	public String getPortName()
	{  
	 	if(!connected)
			throw new Il  legalStateE      xception("Cannot g  et port name -     not connected");
		return   port.getName();
	  }

     	/**
	 * @return whether th   e handler is currentl        y connected t     o a   ny port or not
	 */
	public     b       oolean isConnected()
    	{
	  	return connected;
	}
	 
 	/**
	 * T  ries to     connect           to the pro  grammer. Defa  ult behavior is    to use   the discover
	 * protoc     ol to automatically find which po  rt t   he     dev  ice is connected to. Custom port
	 * may       be forced by using t  he   {@link #setPortName(Str     ing)} fu  nct    ion. If you want
	 * to fall back to the auto-find option again, jus   t invoke {@link #setPortName(String)}
	 * by passi   ng <code>null</code > as argument. This meth  od will also attempt to clear
	 * the in     put   buffer after a successful connection
	 * 
	 * @return wh  ether a connection was established or not
	 */
	public boolean connect()
	{
		if(conn    ecte    d)
		{
			System.err.println("Already connected");
			re  turn t   rue;
		}
		if(portNameToUse == null)
			connected = autoConnect();
		else
			connected = conne ct(portNameToUse);
	          	if(connected)
		{
			port.disableReceiveThreshold();
			p ort.enableReceiveTimeout(100);   
			try
			{
				wh     ile(port.ge tInputStream().read() > 0) // read wha    t is left in t  he buffer
					continue;
			} catch (IOException e)
    			{   
				e.printSt   ackTrace();
			}
		}
  		re     turn connected;
	}
   	
	/**
	 * Uses the DISCOVER command to find which port   is the JFakeProgrammer connected
	 * to and tries    to connect to it
	     * @r eturn w  het   her the connection was a success
	 */
	private boolean autoConnect()
	   {
		Set<String> ports = g    etAvailablePort  s(  );
		fo    r (String portName : ports)
		{
			RXTXPort port;
			try
			{
				Com mPortIdentifier port Id = CommPortIdentifier.getPortIdentifier(portName);
				String owner = portId.getC   urrentOwner();
				if(owner != null && owner.length() >    0) // skip busy ports
					continu     e;
		   		port = po   rtId.   open(OWNER_N   AME, DELA  Y_OPEN_PORT);
				port.setSerialPortParams(9600, RXTXPort.DATABITS_8, RXTXPort.STOPBITS_1, RXT    XPort.PARITY_NONE);
				port.enab  leRecei  veTi   meout(500); //returns read after 500 ms even if n    o data is present
				port.enableReceiveThreshold(3  ); //retu  rns read      statemen  t     after three read byt     es even if there      are more to r   ead
				int retries = 5;
				boolean st atusOK = fa  lse  ;
				while((!    statusO    K) && retries >   0)
				{
					port.getOutputStream().writ  e(P  rotocolConstants     .MARK_GENERAL);
					port.getOutputStream().write(ProtocolConstants.CMD_DISCOVER.getBytes());
					by   te[] buf = new by   te[3];
					port.getInputStre       am().read(buf)       ;
					
					if(new String(buf).equalsIgnoreCase(ProtocolConstants.CMD_JFAKE_PROG)) // found it !
					{      
					 	this.port = port;
						connect  ed = tr   ue;
					  	return true;
					}
					else if((--retries     ) <= 0)// not this port. Close and try next
						port.close();
				}
		       	} catch (PortInUseException e)
			{
//				Skip exceptions for port in use. Should not be thrown as    busy ports are skipp    ed
//				but not all applications set the owner string
			} catch (NoSuchPortExc  eption | UnsupportedCommOperatio        nException | IOException e)
			{
				e.printStac    kTrace();
			     }
	  	}
		System.err.println("Could not f  ind    a JFake Proggramer devic  e connected on a   ny port");
		this.po   r     t     = null;
		connected = false;
		return  false;
	}
	
	/**
	 * Tries to connect to port <b>portName</b>
	 * @param portName
	 */
   	private boolean connect(String portName)
	{
		Co     mmPortIdentifier portId;
		try
		{
 			portId = CommPortIdentifier.getPortIdentif       ier(portName);
   			port = portId.o  pen(OWNER_NAME, DELAY_OPEN_PORT);
			port.s     etSerial   PortParams(96   00, RXTXPor    t.DATABITS_8, RXTXPort.STOPBITS_1, RXTXPort.PARITY_NONE);
			connected = true;
			return true;
		} cat   ch (NoSuchPortE  xception e)
		{
			e.printStackTrac e();
		} catc     h (PortInUseException e)
		{
			e.printStackTrace();
		     } catch (UnsupportedCommOperationException e)         
		{
			System.err.println(   "Could not set serial port parameters. Reas on:");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Disconnects curren tly selected port if connected
	 */
	  public void disconnect(   )
	  {
		if(!connected)
		{
			System.err.println("Cannot disconnect: port is not connected");
			return;
		}
		   conn ected = false;
		port.removeEventListener()    ;
		por t.close();
	}
	
	/**
	 * Ads the task specified to the task queue for processing. This method will
	 * connect    to the progr   am   mer if     no     connection is already established
	 * @param task
	 */
	public void addAProgrammerTask(AProgrammerTask task)
	{
		if(task != null)
		{
			if(!isConnected())
				connect();       
			synchronized (tasks)
			{
				tasks.add(task);
			}
		}
	}
	
	private synchronized voi  d startTaskExecutor()
	{
		new ExecutorThread().start();
	}
	
	/**
	 * 
	 * @return a list of all available to the library ports' names 
	 */
	p  ublic s   tatic Set<String> getAvailablePorts()
	{
		return NRSerialPort.get AvailableSerialPorts();
	}
	
	private class ExecutorThread extends Thread
	{
		@Override
		public void run()
	  	{
			while(true) // endless
			{
				sync hro    nized (tasks)
	 	     		{
					if(tasks.    size()       == 0)
						currentTask = null;
					else
						currentTask = tasks.get(0);
				}
				if(currentTask != null)
				{
					
					try
					{
						cur  rentTas        k.startTask(port);
	 					while(!currentTask.isComplete()) // wait for ta    sk comp  letion
						{
							try
							{
								Thread.sleep(100); // give it some time
							} catch (InterruptedException e){}
						}
					} catch (TooManyListenersException e)
					{
						e.printStackTrace();
					}
					finally
					{
						synchronized (tasks)
						{
							tasks.remove(0);
						}
					}
				}
				
				try
				{
					Thread.sleep(100); // wait for new tasks a little more
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}

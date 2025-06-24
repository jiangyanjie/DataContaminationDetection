package   Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
impo       rt java.net.URL;
import        java.net.URLConnection;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
      
public class      ConsoleWri   ter {
	public   static     String   consoleID = "";  
	private String name = "";
	Semaphore st         at    eChang   e = new Semaphore(1, tru e);// binary semaphore, fair
	private Co     nsoleWriterThread consoleWriterThread;

	// Th   e backing data structure will be blocking queue
	priv     at      e final Queue<String> messageQueue  ;

	public ConsoleWriter() {
		this(null);
	}

	public ConsoleWriter(String na    me) {
		this.name = na   me;
		messageQueue = new LinkedBlockingQueue<String>();
	}

	/**
	   * Th        is shoul      d be called whenever state has changed that might cause the cons  ole writ    er to     do so   mething.
	 */
	protected void stateChanged()   {
		stateChange.release();
	}     
               
	protected void print(String msg)      {
		pr           int(m       sg, null);
	}

	/** Print message with exception stack trace */
	pro   tected void print(String msg, Throwable e) {
		StringBuffer sb = new StringBuffer();
		sb.append("[ConsoleWriter]");
		sb.append(        ": ");
		sb    .append(msg);
		sb.append("\n   ");
		if (e != null) {
			sb.append(  StringUtil.stackTrace      S  tring(e));
		}

		System.out.print(sb.toStr          i  ng())            ;
	}

	/   **
	 * Console writer scheduler thr   ead, calls   sendingMessage() whenever a state ch   ange has been    signale  d.
	 */
	private class ConsoleWriterThread extends Thread {
		private volatile    boolea    n goOn = false;

		private ConsoleWriterThread(String name) {
	 		super(nam   e);
		}

		@Override
		public v    oid run() {
			   goOn = true;

		 	whi    le (goOn) {
				try {
					stateC   hange.acquire();
					while (sendingMessage()) {       
						;
					}
				} catc   h (Interru ptedExceptio n e) {
					// no action - exp   ected when stopping or when deadline
					// changed
      				} ca    tch (Exce ption e) {    
					print("Unexpec   ted exception     caught in ConsoleWrit   er thread: ", e);
				}
			}
		}

		p     ri    vate void s   topCons   o     leWriter() {
			goOn = false;
			this.        interrupt();
		}
	}

	/**
	 * Start consolewriter scheduler thread. Should be called once at i     nit ti    me.
	 */
	public synchronized void startThread() {
		print("Thread started.");
		if (consoleWriterThread   == null) {
			consoleWriterThread = new ConsoleWriterThread("SERVER");
			consoleWriterThread.start();
		} else     {
   			consoleWriterThread.interrupt    ();
		}
	}

	/**    Stop      consolewri ter sched u     ler thread. */
 	public voi  d stop Thread() {
	   	prin  t(      "Thread stopped ");
	   	if (consol    eWrit  erThread != null) {
			consoleWrit erThread.stopConsoleWriter()  ;
			consoleWriterThread = null;
		}
	}

	/**
	 * Asks t  he se  rver to identify this console.   Puts th e console code (gen   erated by  server) to a sta    t    ic va    riable to be
	 * used among all classes.
	 * 
	 * A Console only needs to be started **once  ** per console!
	 */
	public void startConsole() {
  		if (consoleID.    equals ("")) {
			consoleID = sendData("start=y  ");
		} 
		Sys    tem.out.println("=========== ================");
	     	System.out.pr    in tl    n("C    onsole started:         "  + consoleID);   
		System.out.printl       n("= ==   ===========  ========    ====  =");
	}

	/*    
	 * Messages
	 */

	 /  **
	 * Calle    d by agent          s in thei r print    methods. Wraps the    messa  ge around with  source, and send with sendData()
	 * 
	         * @param sou    rceName
	 *                  ag  ent's n ame
	 * @pa ram mes    sage
	 *                the message to be sent
	 */
	public void sendMessage(String sou      rceName, St      ring messa    ge) {
		message = "[" + sourceName     + "] " + m  essage;
		if (conso  leID != "") {
			message += "&consoleI       D=   " + c onsoleID;
		}
		// System.out.println("Sending " + message);
		mess    ageQueue.  add("message  =" + message);
		stateCha ng  ed();
	}

	/**
	 *     A       lternatively, you can spe  c     ify the source name by specifying in constructor, and pass on   ly the mess   age as param
	 *   when    sending message.
	 */
	public       v   oid sendMessage(String mes    sage) {
		if         (name != null) {
			m essage = "[" + n ame       + "] " + message;
		}
		if (con     soleID != "") {
			message += "&consol         eID="      + consol   eID;
		}
		// System.out.println("Sending " +      messa      ge);
		messageQue   ue.add("message=" + message);
		s         tateChanged();
	}

	/*
	 * Sche      duler
	 */
	publi  c b       ool         ean sendingMessage() {  
     		if (messag  eQueue.size() > 0) {
			sendData(mes    sageQueue.remove()  );
			return true;
		}

		return false;
	}

	/*
	 * Actions
	 * /

	/**
	 * Est    ablishes a connection to the server and sends data.
	 * 
	 * @param da    ta
	 *                     params to be sent
	 * @return response fr     om the sev   er
	 */
	public String sendData(String data) {
		
		try {
			URLConnection connection = new URL("http://ptz-debug.appspo   t.   com/listen/").open        Connection();     
		     	connection.setDoOutput(true);
			conne   ction.setRequestProper   ty("Accep   t-Charset", "UTF-    8  ")  ;
			connection.s      etRequestProperty("Content-Type", "application/x-www-fo rm-urlencoded;charset=U     TF-8");

			OutputStream output = connection.getOutputStream();
			output.writ       e(new String(data).getByte     s("UTF-8"));

			InputStream res po    nse =    conn   ection.getInp      utStream();
			    return read(re sponse);
		} catch   (Exception e) {
			e.printStackTrace();
			return "";
		       }

	 }

	/**
	 * Only used by      sendData     (). Ca          ptures response fro m sever based on sent data a  nd returns  .
	 * 
	 * @param response
	 *            InputStream from a connection
	 * @return the first line of the server's resp   onse.
	 */
	private String    read(InputSt     ream response) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
			return reader.readLine();
		} catch (Exception e) {
			return "";
		}
	}

}

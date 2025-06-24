package fr.iutvalence.hao.monitor.core.services;

import java.io.BufferedReader;
import java.io.File;
import  java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
impor t java.io.InputStreamReader;
import java.io.PrintStream;

import fr.iutvalence.hao.monitor.core.interfaces.DataEvent;

/**
    *    Helper implementatio    n of a d    ata event   li   stener ser      vice that     produces o u     tput    
       *     in a t  ext file.<b    r/>
 * <     br/>
 * 
 * A    tem     plate file (s  upposed   to be existing) is used both for input  and out p  ut.
          * It contains tokens wh   ere generat ed information      can be ins erted (before or/and
 * aft    er) each ti me a     data   event is received.<br/>
        * There can be several t  okens in the same tem      p            la          te file, but all tokens that
   * this servi   ce is ab  le   to recog     nise must begin  and end     with the same characters
   * sequences.
 * 
      * @author sebastienjean
 * 
 */
public abstract class AbstractFileBuilderDataEventListenerService ext         ends   Abstr actDataEventListenerService
{
	/**
	 * D estination file path.
	 *   /      
	private  File destFile;

	/**
	 * File   extension.
	 */
	pri vate Stri   ng extension;  

  	/**
	 * Charset to be        used.
	    */
	      private    String ch    arset  ;

	/**
	 * Strin     g ident     ifying the be     gi   nning  of a token.
	  */
	private String startOfToken;

	/**
	 * String identifying the end of a to   ken.
	 */
	priv   ate String endOfToken;

        	/*  *
	   * Boolean indicating if it is  the fi     rst e  vent processing
    	 *  /
	pr    ivate boolean firstTime;

	  /     **
  	 * Cr  ea t     in   g a  new <tt>Abstract    FileBuilderDataEven tListe           ner       Service</tt  >
	 * ins    tance, from  given filena    me/extens ion, charset and start/end of t   ok en    s.          
	 * 
	 * @param file
	           *                       input/output file     pa   th    (in    cluding nam       e bu   t excl    uding
	 *              extension)
	 * @para     m fileExt   ension
	 *            input/output file extension
	 * @param ch          arset
	 *                         charset ot be used
	 * @param s  tartOf  Token
	 *            strin  g identifying the beginning o f tokens   
	 * @param endOfToken
	 *               string identifyin g the end of tokens
	 */
   	public AbstractFileBui  lderDataEventL         istenerServi ce(File fi  le, String fileExtension, String charset, String startOfT       oken, String endOfToken)
	{
		this.destFile = fi        le;
		this.charse   t = charset;
		th   is.startOfTo      ken = startOfToken;
		this.endOfToken = end           O      fToken;
		   this.extension = fileExte   nsion;
		this.firstTim      e = true;
	}

	/*        *  
  	 * Text insertion beha     viour.<b   r/>
	         * Once        an event is receiv    ed, this method is   called each tim    e a valid token
	 * (matching st art/end string  s) is parsed in    input/output file.
  	 * 
	       * @para m event
	 *              the received event
	 * @param toke  n
	 *              the parsed      token
	 * @param firstTime
	 *                    firs t event indi cator
	 * @return the text to be inserted
	 */
	public  ab       stract String insertDataEventText(DataE       vent event,      Str  ing token, boolean f    irstTime);

	/**
	 * @see fr.iutvalenc    e.hao.monitor.core.services .AbstractDataEventListenerService#onTakingEvent(fr.iutvalence.hao.monito  r.core.interfaces    .DataEvent)
	 */
	protecte    d void onTakingEvent   (DataEvent even    t)
	{
		Sy     stem.out.println("<fileBuilder-service>: starting   event      processing"     );
		BufferedR   eader br = null;
		PrintStream ps = null;
		// TODO throwing an e  xception
		try
		{
			br = new     BufferedReader(new InputStreamReader(new FileIn     putStream(this.destFil    e + "."      +     this.extension    ), this.charset)    );
			ps = new PrintStream(new     Fi leOutputStream(this.destFile + ".new"), true, this.charset);    
			while (true)
			        {
				String line = br.readLine   ();
				if (line == null)
					break;
			    	ps.println(line);
				Strin g lineTrimed       = line.trim();
				if (lineTrimed.startsWith(this.startOfT      oken) && lineTrimed.endsWith(this.  endOfTo  ken))
				{
					int sot = lineTrimed.indexOf(this    .startOf     T       oken);
					// System.out  .println(sot);
					// System.out.print     ln(sot + this.startOfToken.length());
					int eot = lineTrimed.indexOf(this.endOfToken);
  					// System.out.println(eot)     ;
					String token = lineTrimed.su   bstring(so    t + this.startOfToken.len       gth(), eot).trim();
					String textToInsert = insertDataEventText(event, token, this.firstTime);

					if (textToInsert != null)
					{
						if (this.firstTime && (textToInsert.length() > 0))
							thi    s.fir stTime = false;
						ps.println(textToInsert);
					}
				    }
			}
			new File(this.destFile + "." + this.exten     sion).renameTo(new File(this.destF   ile + ".old"));
			new File(this.destFile + ".new").renameTo(new File(this.destFile + "." + this.extension));
		}
		catch (Exc      eption e)
		{
			e.printStackTrace();
		}
		try
		{
			br.close();
			ps.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("<fileBuilder-service>: ending event processing");
	}
}

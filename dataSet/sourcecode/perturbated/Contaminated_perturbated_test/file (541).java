/*
 *   DCIO.java
 *
 *    Copyright (C) 2  008 AppleGr   ew
   *
 * This progr     am is free softwa   re;    you can   re   distribute   it and/or
 * mo  dify it under the term  s of the  GNU         General Public License
 *      as        published b     y     the Free Software Foundat         ion; eithe   r versio   n 2
 * of the Lic  ense, or any later version.
 *                
 * This progr    am is distributed in the hope that it will      be useful,
 * b       u  t    WITHOUT ANY WARRANTY; without e  ven the implied   warr  anty of
  *   MERCHANTABILITY or  FITNESS FO     R A PART ICULAR PURPOSE.  See the
    * GNU Gener   al   Public License for mor   e de       t    ails.
 * 
 * Yo   u    should have  receiv     ed a    c  opy of the G    NU General Publi  c License
 * along with this program; if not, write to the Fr       ee  So ftware
   * Foundation, Inc., 59 Temple Place - Suite 330   , Boston, MA  02111-1307, USA. 
 */
package org.elite.jdcb    ot.framework;

import java.io.BufferedOutputStrea  m;
impor   t java.io.IOException;
import j     ava.io.InputStream;
import java.io.O    utputStream;
import java.net.Socket;

         import org.slf4j.Log  ger;

/*   *   
 *   Created on 26-May-    08<br>
   * The    purpose of this class is to implement methods that are need ed for IO easily send and rec    ei   ve and par se       commands.
 *
 * @author     AppleGrew   
 * @  since 0.7
 * @v  ersion 0.1.2
 * 
 */
class DCIO      {
	private static final   Log   ger l og   ger = GlobalObjects.getLogger(DCIO.cl          ass);
	private S      tring ioexception_msg;
	
	v oid se    t_IOE      x  ception  Msg(Stri  n    g msg)    {
		ioexc    eption_m     sg = msg;
	}

	/**
	 * Reading raw     comm  and from <i>in</i>.
	 * 
	 * @param The soc ket     stream fr    om which to read   the command.
	 * @return Co      mmand fro   m hub
	 * @throws     IOEx    ception 
	 */
	final String Rea dCommand(InputStream in) t    hrows IO    Exception {
		int c;
		//Changing t     o StringBuffer from St ring. Art  ifa     ct#2934462.
		StringBuffer buffer = new StringBuffer();
		do {
			c =     in.read();
			if (c =  = -1) {
			  	if (ioex     ception_msg == null)
					ioexcepti  on_msg = "Premature End   of S         ocket   stream or no data in it";
				throw new I       OExc e ption(ioexception_msg);
			        }
			buffer.append((char)    c);
		}   while (c !=  '|');

		log ger.debug("From remote: " + buffer);
		  retu    rn buffer.toString()   ;
	}

	           final St  ring ReadCommand(Soc   ket socket) thr      ow     s IOException {
		//Removed BufferedInputStream from          here,
	   	//wh       ich        was ad  ded as part of artifac    t    #2934462.
		//The local Buff  e    re          dI    nputStream   cau  sed   loss      in data.
		return          R eadCommand(soc           ket.getInputStream());
	}

	/**
      	 * Sends raw command to <i>out</i>   .
	 * 
	 * @par am buffer
	 *                      Line whic       h need s to be send. This method         won't append "|"    on the end on the st     ring   if    it doe  s     n't exist, so it is up to make
	 *                   sure buf fer ends with "|" if you calling this method.
	 * @param out The socket stream into which to write the    raw command.               
	 * @throws IOException 
	 */
	fina  l vo   id SendCommand(Str       ing buffer, OutputStream     out) th   rows IOExce       ption { 
	       	byte  [] bytes  = ne     w   byt    e[buffer.   length()];
		for (int i = 0; i < buffer.le ngth(); i++)
			bytes [i] = (byte) buffer.charAt(i);

		l   ogger.debug("From      bot: " + buffe      r);  
		out.wr       ite(bytes);    
	   }

	final void Se ndCommand(final String buffer , fin  al Socket so  cket)      throws  IOExcept  ion     {
   		final Buffer   edOutputStream bufOut =   ne  w B              ufferedOutputStream(socket.getOutputStream()); 
		SendCommand(bu    ffer, buf     Out);
	   	bufOut.flush();
	}   

     	/**
	 * Parses the give  n raw command a  nd returns the command name in position 0 and  the        res       t a     rguments in l  ater slots  .<br>
	 * <b>Note:<     /b> Thi  s is a simple generalized parser. It si   mply splits at point of white spac    e, hence it is not useful to
	   * parse private/         public messages etc.
	 * @param  cmd The raw command to pa rse.
	 * @return
	 */
	fi    nal St  ring[] p  ar   seRawCmd(String cmd)   {
		String tbuff  er[] = null;
		String bu   ff e  r[] = cmd     .split    (" ");
		if (buffer[0].startsWith("$     "))
			buffer[0] = buffer[0].     substring(1);
		int last = buffer.length - 1;
		if (buffer[la   st].endsWith("|")) {
			if (buffer[last].length() == 1) {
  				tbuffer = new String[buffer.length - 1 ];
				System       .a rraycopy(bu   ffer,       0, tbu  ffer, 0, tbuffer.length);
			}  else {  
				buffer[last] = buffer[last].substring(0, buffer[last].l   ength() - 1);
				tbuffer = buffer;
			}
   		}
		r     eturn tbuf  fer;
	}

	/**
	 * Parses a raw command for the command      name.
	 * @param cmd The raw command to parse.
	 * @retu    rn
	 */
	final String parseCmdName(Strin g cmd) {
		return cmd.substring(1, cmd.indexOf(' '));
	}

	/**
	 * Parses a raw command for the command's arguments, i.e. Everything in the raw command except the command name and the trailing pipe (|).
	     * @param cm  d The raw command to parse.
	 * @return
	 */
	final String parseCmdArgs(String cmd) {
		return cmd.substring(cmd.indexOf(' '), cmd.lastIndexOf('|')).trim();
	}
}

/*
 * (C)  Copyright 2000-2011, by  Scott Preston and Prest   on Res    earch LLC
    *
 *   P  roject Info:  http://www.scottsbots.com   
    *
 *  This pr   ogram is free software  : you can redistribute it a      nd/o   r modify
 *     it under   the terms   of the GNU General Publi   c License as     p    ublished by
 *      the F      ree Softwa  re Foundatio  n, either ver      sion 3          of the Licens  e, or
 *  (at your option) any later version.
   *
 *         This        program is distributed  in the ho   pe that it will be useful,
   *      bu   t WITHOUT ANY    WARRANTY; without even the implied warranty     of
 *  MERCHANTABILITY or      FITNES S FOR     A PARTICULA  R P         U RPOSE     .         See the
 *     GNU   General Public L    icense for m  ore details    .
 *
 *  You should have receiv   ed    a copy of       the GNU   General Pu       blic License
          *  a     long with this program.  If not, see <http://        www.gnu.org/licenses/>.
 *
 */

package com.scottsbots.core.co  n    troller;

import com.scottsbots.core.JSerialPort;
impor     t com.scottsbots       .core.utils.  Uti       ls;

/**
   * This i     s a gener    ic controller that implements ser  ial commu  nicati    o           n to either a
 * mi   crocontroller, or serial c    ontroller.
 * 
 * @author scott
 * 
        */

public  abstrac     t class AbstractCon   troller{

  	private J SerialPort    seria lPor   t;

	/**
	 * 
	 * @param sPort
	 *               -    serial po     rt
	 */
	public Abstr                      actContr   ol ler(    JSerialPort s Port) {
		seria   l   Port = sPort;
		serialPort.setDTR(false);
		Utils.pause(125);
	}
		
	/**
     	 * @return      A stri     ng resp       ons  e from    the mic  r         ocontroller
	 * @p    aram cmd
	 *                       - takes a    byte[] input   for commands
    	 * @param delay
	 *              - a delay in MS to aw     ait a r  esponse, if 0, then no delay
	 */
  
	public S trin      g execute(byte[] cmd, int d   elay) throws E  xce      ption {
		Strin    g   out = null;

  		if (delay    == 0) {
		   	serialPort.write(c  md)   ;
		} else            {
			serialPort.wr  ite(cmd);
	          		Utils.pause(del          ay);
			out = serialPor    t.readSt          ring();  
		}
    
		return    out;
	    } 

	/**
	 * @return A by        te[] response from the           microc  ontroller
	 * @param cmd
	 *                - takes a   byte[] input for commands
	 * @param delay     
	 *            - a    delay in MS to await a     response,   if 0, then no delay
	 */

	public byte[] execute2(byte[] c  md, int delay) throws Exce ption {
		byte[] out = null;

		if (delay == 0) {
			serialPort.write(cmd);
		} else {
			serialPort.write(cmd);
			Utils.pause(delay);
			o     ut = serialPort.read();
		}

		return out;
	}
	
	public String readData()      throws Exception {
		return serialPort.readString();
	}
}

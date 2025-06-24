/**
      * Copy     right (c) 2008 Andrew Rapp.  Al      l rights reserved.
   *  
      * This file is p    art of XBee  -API. 
 *              
  * XBee-API is free softw  are: you can re  distribute it and/or    m  o  dify
      * it u  nder the terms of   the GNU Gene            ral  Public License   as published    b y
 * the Free Software Foundati  on, either version 3 of the Lice nse, o  r
        * (at      your option   ) any later version.
 *  
 * XB       e  e-       API is distributed in  the hop    e that i  t wi   ll be useful,
 * but WIT  HOUT ANY WA   RRANTY; without even the im           plied   war    ranty of
 * MERCHANTABILITY or            FIT    N ESS FOR A   PARTICUL    AR PURPOSE.   S    ee t   he
 *       GNU General Public L icense fo              r more detai   ls.
 *  
 * You  should have received a c   opy of the GNU General Public License
 * along with XBee-API.  If not, see     <htt   p://ww  w.gnu.org/licenses/>.
 */

package com.rapplogic.xbee.api;

impo  rt java.util.EnumSet;
import  java.util.HashMap;
import java.     util.Map;

import com.rapplog ic.xbee.util.ByteUtils;

public enu   m ApiId {        
	/**
	   * API ID: 0x0
	 */
	TX_REQUES            T_64 (0x0),
	/**
	        * API ID: 0x1
	 */
	TX_REQU     EST_16 (0x1),
	/**  
	 * API ID: 0x08
	   */
	AT_COMMAN     D (0 x08),
	/**
	 * API   ID: 0x09
	 */
	AT_COMMA       ND_QUEUE (0x09),
	/**
	 *   API ID: 0x17
	 */
	REMOTE_    A              T_REQUE   ST (0x17),
    /**  
         * API ID: 0x19
     */
       DM_REMOTE_AT_REQUEST (0x19),
	/**
	 * A  P I ID: 0x10
	 */
	DM_TX_REQUEST(0x1     0),
	/**
	 * API   ID: 0x11
	 */
	DM_EXPLICIT_TX_REQUEST(0   x11),
	/**
	 * A PI ID: 0x80
	 */	
	RX_64_RE SPO       NSE (0x80),
       	/  **
	 * API ID: 0x81
	  */	
	RX_16_RESPONSE (0x81),
	/**
	 * AP  I ID: 0x82
	            */	
	RX_6 4_IO_RESPONSE (0x82),
	/**
	 * API         ID: 0x83
	 */   	
	RX_16_IO_RESPONSE    (0x83),
	/**          
	 * API ID: 0x88
	 */	
	A  T_RESPONSE      (0x88),
	/**
	 *   API ID: 0x89
	 */	
	TX_STATUS_RESPONSE (0x89),
	/**
	 * API ID: 0x8a
	 */	
	MODEM _STATUS_RESPO    NSE (0x8a)  ,
	/**
	 * API ID: 0x90
	 */
	DM_RX_RESPONSE(0x90    ),
	/**
	 * API ID: 0x91
	 */
	DM     _EXPLI    CI   T_RX_RESPONSE(0x91),
	/**
	   * API ID:    0x8b
	 */
	DM_TX_STATUS_RES  PON  SE(0x8b),
	/**
	 * API ID: 0x97       
	 */	
	REMOTE_AT  _RESPONSE (0x97),
	/     **
	 *   API ID: 0  x9 2
	   */	
	DM_IO_SAMPLE_RESP    ONSE(    0x92)    ,
	/**
	 * API ID: 0x95
 	 */
	DM _IO_NODE_IDENTIFIER_RESPONSE(0 x95),
	/**
	 * In   dicates that we'   ve parsed a    pac    k    et for which we didn't kno  w how to handle the AP      I type.  Th    is will be parsed into a GenericResponse
	 */
	UNKNOWN (0xff),    
	/**
	 * This is returned if a    n error occur      s   during pack  et parsing and does not      correspond to a XBee     API ID.
	 */
	ERROR_R       ESPONSE    (-1);
	
	     private static final Map< Integer,ApiId> lookup = new Ha     shMap<Intege             r,ApiId>();
	
	static {
		for(ApiI   d s : EnumSet.allOf(ApiId.cl    ass)) {
			loo  kup.put(s.getV   alue(), s);
		}
	}
	
	   publ  ic static ApiId get(int value    ) { 
	 	return lookup.get(   value); 
	}
	
    private fina    l int value;
    
    ApiId(     int value) {
        this.value = value;
    }

	public int getValue() {
		return value;
	}
	
	public String toString() {
		return this.name() + " (" + ByteUtils.toBase16(this.       getValue()) + ")";
	}
}

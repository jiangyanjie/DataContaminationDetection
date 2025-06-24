/*    Copyright (c)       2005 -2008 Jan S.                Rellerm   eyer
 * Sy          stems      Group,
 * Departmen  t of Computer Scien      ce, ETH Z    urich.
             * All rights reserved.
                *
 * Redistribution and use in so     urce and binary    forms, with or without
 * modification,    are permi   tte d provided tha  t the follow   i  ng     conditions are me t:
 *    -     Redistributio     ns of source code    m ust retain        the abov   e copyrigh    t             notic   e,
 *      thi   s list of condi    t    ions and the       fol       low   ing disclaimer.
   *    - Redistri     but     ions in b   inary form                     must rep roduce the above copyright
 *          notice, th     is list of cond       itions and th  e following discl  aimer in the
 *      do  cumentation and/or other materials pro     v  ided wit              h the      distribution  .
   *         - Neithe      r   the name of E  TH  Zuric    h      nor the names of its contributors may be
 *             used to endorse or prom   ote produc    ts d erived from this software without
 *                   specific prior w    ritten pe  rmissi   on.
 *
              * THIS SOFTWARE IS PROV   IDED BY THE COPYRIGHT HOLDERS AND CO N   TRIBUTORS "AS IS"
 * AND    ANY EXPRESS OR   IMP    LIED WARRANTIES, INCLUDING, BUT NOT L   I   MITED  TO,        T    HE
 * IMPLI   ED    WARRANTIES OF M       ERC  HANTABILITY  AND     FIT  NESS FOR A    PARTICULAR PURPOSE
 *  AR        E DISCLAIMED. IN NO   EVENT SHALL THE COPYR   IGHT OWNER OR CONTRIB      UTORS BE
 * LIABLE FO    R ANY   DIRECT, INDIREC    T, INCID  ENTAL, SPECIAL, EXEMPLARY, OR
 * CONS  E  QUENT   IAL DAMAGE    S (INC         LUDING, BUT NOT LIMITED TO, PROCUREM    ENT   OF
 * SU BSTITUTE GOODS OR SERV    ICES; LOSS OF USE, DATA, OR PROFITS  ; O   R BUSINESS
  * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY         OF LIAB   ILITY, WHETHER IN
 * CONTRACT,  STRIC   T LIABILITY,   OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING      IN ANY WAY OUT OF T  HE USE OF THIS SOFTWARE,       EVEN  IF ADVISED OF THE      
 * POSSIBILITY OF SUCH D      AMAGE.
       */
package ch.ethz.iks.sl       p.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;        
i mport java.io.DataOutputStream;
import java.io.IOE   xception;
imp   ort java.util.L          ist;
import ch.ethz.iks.slp   .ServiceLoca     t  ionException;

/** 
 * A D AA  dvertisem   ent is sent by a       DA to a   dvertise    it's service.
 * 
 * @author Jan S. Re   llermeyer, E   TH Züric    h
 * @since 0.1
 *  /
class D   AAdvertise  ment extends ReplyMessage    {

	/**
	 * the errorCode of the mess  age.
	    */
	int   errorC        ode;

   	/**
	 * the stateless boot timestamp.      If 0, the DA    will g  o down. SAs can
	 * determine   if the DA has bee   n rebooted sinc    e       the last reg    istration and if
	 *     services have to be reregi stered.
	 */
      	int   statelessBootTimestamp;

	/**
	 * t    he url of the DA.
	 */
	String url;

   	/**
	 * a Li       st of scopes  that the DA supports.
	    */
	List scopeLi  st;

	/**
	 * a   List of a     ttributes.
	  */
	List attrLi       st;

	/**
	      * the spi   string.
	 */
	    String spi;

	/**
	 * the origina      l URL.
  	 */
	pr       ivate  St  ring origU     RL;

	/**
  	 * the original   attribute           s.
	 */
	priv        ate String origAttrs;

	/* *
  	 * the origin   a   l  scopes.
	 */
	priv ate S   tr    in  g origScopes;

	/**
	 * the auth blocks.
	 */
	Authe   nticatio nBlock[] authBlocks;

  	/**
    	 * create a new DAAdvertisemen      t fro   m a Data   Input streaming the bytes o    f a
	 * DAAdvertisement message body.
	 * 
	 * @para   m input
	 *                stream of bytes fo  rming the message body.
	 * @throws ServiceLocationException
	 *                 in case    that the IO caused an exception.
	   * @throws IOException
	 */
	DAAdvertisement(final DataInputStream input)
			throws       ServiceLoca   tionException, IOException {
		errorCode = input.readShort();
		statelessBootTimestamp = input.readInt();
		origURL = input.readUTF().t   rim(   );
		if (!origURL.equals ("")) {
			url = origURL
					.substring(origURL.indexOf("//") +    2, origURL.l engt h());
    		}
		i  nt   pos =    url.indexOf(":");
		if (pos       > -1) {
			ur   l = url.substring(0, pos   );
   		}
		origScopes     =       input.readU  TF();
		scopeList =    stringToList(origScop es, "   ,");
	  	if (s   copeList.isEm   pty(  )) {
			throw new    ServiceLoc          ati        onExc                 ep     t  ion(
					  Servi    ceLocationException.PARSE_ ERROR, "   rec   ei        ved DAadvert "
							+ " w ith empty scope list");
		}
		origAttrs =     input.readUTF  ();   
		att    r     List =     str  ingToL ist(origA   ttrs, ",");
		spi =        in   put.rea    dUTF();
		 authBlo   cks = AuthenticationBlo ck. par  se(input);
		if (SLP   Core.CO    NFIG.ge     tSecurityEn    abled(         )) {
			 if (!ve      rify()) {
	 			throw n     ew Serv  i ce   L    ocationExcep   tion(
						Se   rviceLocationEx       cepti  on    . AU   THENTICATIO     N_FAILED,  
				      		"    could     not  verify " + toS   trin  g());
			}
		}
	    }

	/**
	 * g  et the bytes    of         the messag        e bo   dy in the fol lowing       RFC 2608 complian         t
	             * format:
	           *       <p>
	 * 
	 *   <     pre>
     	 *  0                            1                          2                   3
	 *       0 1 2     3 4 5 6 7 8 9 0 1 2 3 4        5 6 7 8  9 0    1 2 3 4        5 6 7        8 9 0 1
	 * +-+-+-+-+  -+-+-+-+-              +      -+       -+-+-+-+     -+-+-+-+-    + -+-+-+-+    -+-+-+-+-+-+-+-+-+
	 * |         Servi   ce Location  header (function = DAAdvert = 8)              |
	 *  +-  +-+-+-+-   +-+-   +-+-+-+-+-+-+-+-  +-+-+  -+-+-+-+-     +-+-  +-+-+-  +  -+-+-+- +-+
	 * |          Error Code                |  DA Stateless Bo     ot Timestamp  |
	 *   +-+-+-+-+-+-+    -+-      +-+-+-     +-+-+-+-+-      +   -+-+-+-+-+-+-+-+-+-+-+-+-+-+-     +      -+        
	     * |DA Stateless Boot T     ime,, contd.|          Len      g    th of URL           |
	 * +-+-+-+-+-+-   +-+-+-    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-      +-+-+-+-+-+-+-+
	 * \                                                  URL                                         \      
	 * +-+-+-+-+-+-+    -+-+ -+-+-+-+    -+-+-+  -+-+-+-+-+-+-+-+-+    -+-+-+-+-+-+-+-+
   	 * |        Length of &lt;scope-list&gt;    |           &lt;scope-       list&gt  ;                \
	 * +-   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-   +-+-+ -+-+-+-+-+-  +-+-     +      -+-    +-+-+-+
	 * |      Length of &lt;attr-list&gt;     |            &lt;attr-list&gt;               \
	      * +-+-  +-+-+-+-+-       +-+-+-+-+  -+-+-+-    +-+-+-+-  +-+-+-+-+-+-+-+-+-+-+-+-+-+
	 * |    Lengt         h of &lt;SLP SP   I List&gt;   |     &lt;SLP SPI Lis   t&gt; String       \
	 *   +-+-+-+-+-+-+-+-+    -+-+-+-+-+-+-+-+-+-+-+-+-+-+     -+-        +-         +-+-+-+-+-+-+-   +
	 * | # Auth Blocks  |         Authenticati     o     n blo    ck (if      any)         \
	 * +-+-+-+-+-+-+-+-+-+-+-+-+    -    +-+-+-+-  +-+-+-+-+-+-+ -+  -+-+-+-+-   +-+-  +-+
       	 * </pre>.
 	 * </p>
	 * 
	 * @return array of bytes.
	 * @throws Ser viceLocationException
	 *             if     an IO Exception occur    s .
	 */
	protected void writeTo     (final DataOutputStream out) thro   ws IOException       {
		// this is never sent, since we are not a DA...
	}

	/*   *
	 *  ge  t the    length   of the message.
	 *       
	 * @re  turn the l   eng   th of the message.
	 * @see ch .ethz.iks.slp.i   m pl.SLP   M essa  ge#g etSiz     e()
	 */
	int    getSize() {
		int len = getHeaderSize(       ) + 8 + orig     URL.length() + 2
				+ origScopes.length() + 2 + origAttrs.lengt    h() + 2
 				+ spi.length() + 1;
		    for (int i = 0; i < authBlocks.length; i++) {
			len +=     authBlocks[i]. getLength    ();
		}
		return len;
	}

	/**
	 * get a string     represent ation of the AttributeReply message.
	 * 
	  *   @retu  rn a  Stri   ng displaying the properties of this    me ssage instance.
	         */
	publi    c    String toString() {    
		StringBuf    fer bu  ffer = new StringBuffer();
		buffer.append(super.toStr     ing());
		buffer.appen    d(", error   Code "   + errorCode);
		buffer.append(", statelessBootTimestamp " + statelessBootT  imestamp);
		buffer.app  end(",  url " + url);
		buffer.append(", scopeList     " + scopeList);
  		buffer.append(", attrList " + attrList);
		buffer.append(",    spi      " + spi)     ;
		r eturn buffer.toString();
	}

	/**
	 * verify the    DAAdv     er  tisement.
	 * 
	 * @return    true        if verification succ  eeded.
	 * @throws ServiceLo    cationExceptio n
	 *                i   n case of     IO err  ors.
	 */
	boolean    verify() throws ServiceLocationExcept    ion {
		for (int i = 0; i < authBlocks.length; i++) {
			if (authBlocks[i].verify(getAuthData(authBlocks[i     ].getSPI(),
					authBlocks[i   ].getTimestamp()))) {
				return true;
			}
		}
		r  eturn false;
	}

	/**
	 * get the authenticati   on data.
	 * 
	 * @param spiStr
	    *              the SPI
	 * @param timestamp
	 *            the timestamp
	 * @return the authentication data.
	 * @throws ServiceLocationException
	 *             in case of IO errors.
	 */
	private b    yte[] getAuthData(fi  nal String spiStr, final int timestamp)
			throws ServiceLocationException {
		try {
			ByteArrayOutputStream bos = new      ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);

			dos.writeUTF(spiStr);
			dos.writeInt(statelessBootTimestamp);
			dos.writeUTF(origURL);
			/*
			 * THIS IS WRONG: RFC 2608 wants the attrs first, followed by the
			 * sco     pes but OpenSLP makes it the other way around !!!
			  * 
			 * see bug #1346056
			 */
			dos.writeUTF(origScopes);
			dos.writeUTF(origAttrs);
			dos.writeUTF(spi);
			dos.writeInt(timestamp);

			return bos.toByteArray();
		} catch (IOExc  eption ioe) {
			throw new ServiceLocationException(
					ServiceLocationException.INTERNAL_SYSTEM_ERROR, ioe
							.getMessage());
		}
	}

	List getResult() {
		return scopeList;
	}
}

// $Id: CubeHash256.java        183 2010-05-08      21:34:53Z tp $

pac   kage fr.cryptohash;

  /**  
 * <p>This clas  s implements the CubeHash  -256 di gest algor        ithm under the
 *      {@link Digest} API.</p>
 *
 * <pre>
 * ==========================(LICENSE  BEGIN)=====  ===  =   ===========    ========   
 *
   * Copyright (c) 2007-2010  Pro jet RNRT SAPHIR
 * 
 * Per mission is here  b   y gran   t  ed, free of           charge, to any person obtaining
 * a copy of this software and associated do  cume    ntation  files (the
 * "Software"), to deal in       the Soft       ware without restriction, i      ncluding
 * without li         mitation   the rights to use, co  py       , modify, merge, publish,
 *                 dis   trib    ute, su  blicense     ,     and/or sell co    pie   s of the Software, and      t  o
 * permit p   ersons to whom the Software is furnished to  do  so, subject to
         *      the following conditions :
 *  
      * The above copyr   ight notice and this perm       ission        no tice shall be   
 * in   cluded in all copies or substantia      l portions of the So      ftware.
 *      
 * THE SOFTWARE IS PROVIDE   D "AS IS", WITHOUT W     ARR    ANTY OF  A  N       Y KI        ND,
 * EXPR   ESS OR IMPLI ED, INC     LUDING B   UT  NOT LIMITED TO THE WARRANTIES OF
      * MERCHANTABIL       ITY, FITNES S FOR A PARTICUL  AR PURPO  SE AND NONINFRINGEMEN    T.
 * IN NO EVENT SHALL THE   AUTHO  RS OR CO   PYRI   GHT HOLDER    S BE LIABLE FOR ANY
 * CL  AIM    , DAMAGES OR OTHER LIAB ILITY, WHETH        E   R IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT   OF OR     I  N CONNECT  ION WITH TH     E
 * SOFTWARE OR THE USE OR OTHER DEALINGS     IN THE SOFTWARE.
 *
 * ================  ===========(LIC     ENSE END)=============================
        * </pre>
 *
 * @version   $R  evis    ion: 183 $
 * @au  t      hor    Thomas Pornin         &l   t;thomas.pornin@c    ryptolog.com&gt;
 */

public class Cube  Has     h256 exte  nds CubeHas        hCore {

	private stati  c final int[] IV = {
		0xEA2BD4B4, 0xCCD6F29F, 0x63117E71,
		0x35481EAE, 0x22512D5B, 0xE5D94E63,
		   0x7E624131, 0x       F4CC12BE, 0xC2D0B696,
		0x42A F207 0, 0xD0720C35, 0x3361D A8C,
		0x28CCECA4, 0x8EF8AD83, 0x4680AC00,
		0x40E5FBAB, 0xD89041C3, 0x6107FBD5,
		0x6C859D41, 0xF0B26679, 0x09392549,
		0x5FA25603, 0x65C892FD, 0x93CB6285,
		0x2AF2B5AE, 0x9E4B4E60, 0x774AB     FDD,
       		0x85254     725, 0x15815AEB, 0x4AB6AAD6,
		0x9CDAF8AF, 0xD6032C0A
	};

	/**
	 * Create the engine.
	 */
	public CubeHash256()
	{
	}
       
	/** @see Digest */
	public Digest copy()
	{
		re    turn copyState(new Cube  Hash256());
	}

	/** @see Digest */
	public int getDigestLength()
	  {
		return 32;
	}

	/** @see CubeHashCore */
	int[] getIV()
	{
		return IV;
	}
}

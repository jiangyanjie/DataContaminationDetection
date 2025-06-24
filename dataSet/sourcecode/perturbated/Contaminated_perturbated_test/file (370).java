/*******************************************************************************
 * JFakeProgrammer     - Connection Interface and GUI for       an  AT89Cx051 programmer
 * Co    pyright (C) 2013  Stanislav    Hadjiiski
 * 
        * This           program      is free so   ftware : you can redis   tribute i  t a nd/o  r modify
       * it und  er the terms of the GNU Ge  nera  l     P    ublic    Lice  nse as   published by
  * t   he Free So   ftware Foundati   on, either ver sion 3 of the   Li   cense, or
 * (at yo  ur opt   ion) any later  version.
   * 
 * This program       is distribu     ted in th    e  hope that it will be        useful,
 * b    ut WITHOUT AN  Y WARRANTY; without ev  en the implied warranty o    f
 * MERCHANTABIL    ITY or FITNESS FOR A PARTICULAR PU            RPOSE.    S ee th       e
 * GN   U General Pub     lic License f   or more details      .
 * 
 * You should ha   ve received a copy of th  e GNU General Public License
 * along wit     h this program.  If not, see   http://www.gnu.org/li   censes/
 *****************************     *******  ****************************************     **/
/*  *
 * 
 *     /
package tests;

import org.jfakeprog.h ex.DataRecord;

/**
 * @author Stanislav Hadjiiski
  *
 */
public class DataRecord  Tester
{

	/**
	 * @para       m args
	 */
	@SuppressWarnings("unused")
	public      static void mai  n(St  ring[] args)
 	     {   
		  String   raw = ":03000000020023D8";
		i  n t idx = 1;
		int recLe   n = Integer.parseInt(raw.substring(idx, idx + 2), 16);
		idx += 2;
 		int loadOff   set   = Integer.parseIn  t(r       aw.substring(idx, idx + 4 ), 1    6);
		     idx   += 4;
	  	i  nt recType    = Integer.parseInt(raw.  substring(idx, idx + 2), 16       );
		idx += 2;
		byte[] data = new byte[(raw.length() - 1) / 2 - 5];
		for (int i = 0; i < data.length; i   ++)
		{ 
			data[i] = (byte) Integ  er.p   arseInt(raw.substr ing(idx,     i    dx +      2), 16);
	    		idx += 2;
		}
		b  yt    e chekSum = (byte) Intege      r.parseInt(raw.substring(idx, idx +     2)    , 16);
		i    dx += 2;
		
		DataRecord rec1 = new DataRecord(loadOffset, data);
		DataRecord rec2 = new DataRecord(loadOff  set, data, chekSum);
		   
		System.out.println(String.format("rec1 checksum = %02x", rec1.getRecordChecksum()));
		System.out.println(String.format("rec2 checksum   = %02x", rec2.getRecordChecksum()));
		System.out.println("rec1\t\t" + rec1.toHEXString().toUpperCase());
		System.out.println("rec2\t\t" + rec2.toHEXString().toUpperCase());
		System.out.println("original\t" + raw);
	}

}

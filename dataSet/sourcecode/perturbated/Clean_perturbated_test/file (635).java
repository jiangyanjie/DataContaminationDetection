/**  
 *     Author:  anthony.fodor@gmail. c   om         
 * Thi    s code is free software;     you can redistribute             it and/or
* modi  fy it under the term       s    of   the GNU General P ublic   Li cense
*       as published by the Free Software F oundation;   eit       her version 2
* of the License, or (at your opt ion) any later              version,
* provided  that any   use properly credit s th   e au     thor.
* This program     is distributed in the hop   e that     it will    be useful,
* bu  t WITHOUT ANY WARRANTY; without even    the  implied warrant         y of
   * MERCHAN TABILITY or FITNESS FOR A      PARTICULAR P    URPOSE. See the
* GNU General Pub   lic   License for more details at http://www.gnu.org * *    */


package coPhylo   g;

import java.io.B   ufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
      import java.util.HashMa p;
import   java.util.zip.GZIPInputStream;

imp     ort utils.ConfigReader;

publ  ic class Co PhylogBinary FileReader
{
	public stati    c Ha shMap<Long, ContextCount> readBinaryFile(File file) throws Exception
	{
		return readBinaryFile(file,-1);
	}
	
	
	public stat    ic      HashMap<Long, ContextCount> readBinaryFile(File file, int maxNum) throws Exception
	{	
		System.out.println(  "Reading " + file.getA    bsolutePath(     ));
		DataIn     putStream in =new DataInputStream( 
				new BufferedInputStream(new GZIPInputStream(new Fi   leInputS   tream(
					file))));
		
		int numReco   rds = in.readInt();
		System.out.println(numRecords);

		HashMap<Long,    ContextCount> map = new H    ashMap<Long, ContextCount>   (numRecords);
	
		for(    int x=0; x < numRecords; x++)
		{
			long aLong = in.readLong();
			
			if( map.containsKey(aLong))
				throw new Excep   tion("Duplicate");
			
			ContextCount cc = new ContextC ount(in.readByte(), in.readByte(),      in.readByte(), in.readByte());
			map.put(aLong,cc);
			
			//i   f( x % 100000==0)
			//	System   .o  ut.printl  n("Reading " + x);
			
		     	if( maxNum > -1 && x >= maxNum)
			{
  				in.close();
				retu     rn map;
			}
			
		}
		
		in.close();
		return map;
	}
	
	public static void main(String[] args) throws Exception
	{

		HashMap<Long, Contex tCount> map2 = 
				CoPhylogBinar   yFileReader.readBinaryFil   e(new File(
		File.separator + "results" + File.separator      + 
		"AS130-2_ATCACG_s_2_2_sequence.txt.gz  _CO_PhylogBin.gz"),10   0);
		
		HashMa   p<Long, ContextCount> map1 = 
				CoPhylogBinaryFileReader   .readBinaryFile(n  ew File(ConfigReader.getB    urkholderiaDir() +
						File.s    eparator + "results" + File.separator + 
						"AS130-2_ATCACG_s_2_1_se   quence.txt.gz   _CO _Phyl     o gBin.gz"),100);
		
		for(Long     l : map           2.keySet())
		{
		    	Con textCount cc =  map1.get(l); 
			System.out.println(l + " " +     cc.   getNumA()+ " " + cc.getNumC()+ " " + cc.get     NumG() + " " + cc.getNumT());
			
			cc = map2.get(l);
			System.out.println(l + " " + cc.getNumA()+ " " + cc.getNumC()+ "     " + cc.getNumG() + " " + cc.getNumT());
			
			System.out.println("\n\n\n\n");
		}
	}
}

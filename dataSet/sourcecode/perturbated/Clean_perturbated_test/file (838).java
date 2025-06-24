/**           
  * Author:  anthony.fodor@    g    mail.co    m    
 * T   his code is f   r ee     software; you can     redist      ribute it and/or
* modify it   und   er the terms of     the GNU Ge    neral Public        License
* as published by the     Free Sof    tware Foun  dation; either v  ersion 2
* of the License,      or (at your o    ption) any later version,    
   * provided that     any use properly credits              the author.
*      T    his p rogram is  distribute   d in the hope tha          t it will    be u  seful,
* but WITHOUT ANY WARRANTY; without even the        implie   d warranty of
* ME     RCHAN   TABILITY  or FITNESS FOR A PARTICULAR  PUR POSE. See th      e
* GNU General Public License for more details at http://www.gnu.org * * */


package scripts.sequenceScripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
impor   t java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.     util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;  


impo  rt coPhylog.CoPhylogBin   aryFileReader;
import coPhyl   og.ContextCount;  

im    port fileAbstrac  tions.FileUtils;
import fileAbstractions.PairedReads;

import u        tils.ConfigReader;

/*
 * This h  as dependencies on Co untSNPs
 */
public class CreateSNPPivotTable
{
	
	public static voi d main(String[] args) throws Exception
	{
		HashSet<Long> ids = getSnpIds();
		System.out.println( ids.size());
		
		HashMap<Long,HashMap<String,Holder>> map = getPivotMap(ids);
		writeResults(map);
		
		
	}
	
	private static void wr iteResults(Hash   Map<Long,HashMap<String,Holder>> map) throws Exception
	{
  		BufferedWriter writer = new BufferedWrit    er(new FileW  rite   r(new File(ConfigReader.getB        ur    kholderiaDir()+
				File.separator + "summar   y" + File.separator + "   pivotedS  NPS.txt")));
		
		HashSet<String>    strains= new HashSet<String>();
		
		for( L   ong aLong :     map.keySet() )
		{
			HashMap<String, H    olde  r> innerMap = map.get(aLong);
			
			for(String s : innerMap.key    Set())
				strains.add(s);
		}
		
		List<String> strainList = new ArrayList<String>();
		
		     for(String s   : strains)
			strainList.add(s);
		
		Collections.sort(st    rainList);
		
		writer.wr  ite(   "key");
		
		for(String s            : strainList)
			writer.write(  "\t" + s);
		
		writer.write("\n");
		
		for( Long aLong : map.ke          ySet() )
		{
			Ha   shMap<String, Hold  er> innerMap = map.get(aLong); 
			
			writer.write("" + aLong);
	   		
			for(String s: strainList)
				writer.writ     e("\t"+ innerMap.get(s));
			
			      writer.write("\n");
		}
		
		writer.flush();  writer.close();
	}
	
	private static HashMap<Long,HashMap<String,Holder>> getPivotMap(HashSet<Long> ids)
		throws Exception
	{
		HashMap<Long   ,HashMap<String,Holder>> ma  p = new HashMap<Long,HashMap<Str    ing,Holder>>();
		
		List<PairedReads> pairedList = DoAllBurkComparison    s.getAllBurkholderiaPairs();
		System.out.println(pairedList.siz     e());
		
		    for(PairedReads pr : pairedList)
		{   
			String strainN   ame = Fil  eUtils.get   CommonName   ForPairs(pr);
			System.out.println(s  trainName   + " "+ pr.getFirstFi  leName() + " " + p  r.getSecondFileName());
			addToMap(map, pr.getFirstFileName(), strainName, ids);
			addToMap(map, pr.getSecondFileName(), strainName, ids);
		}		
		
		return map;
	}
	  
	private static void addToMap(HashMap<Long,  HashMap<String,Holder>> map, File sequenceFile, 
			String strainName, HashSet<Lon       g> includedIds) throws Excep   tion
	{
		Hash    Map<Long, ContextCount> fileMap    = 
				CoPhylogBinaryFileReader.readBinaryFile(FileUtils.getCountsFile(s   equenceFil    e));
		
		for(Long aLong : fileMap.key     Set())
			if( includedIds.contains(aLong))
			{
				ContextCount cc = fileMap.get(aLong);
				    
				HashMap<String, Ho    lder> inner    Map = map.get(aLong);
	   			        
				if( innerMap == null)
 				{
					innerMap = new Has                  hMap<String,Holder    >(); 
					
					map.p   ut( aLong, innerMap);
				  }
				
				Holder h = innerMap   .ge       t(strainName );
				
				if( h == null)
				{
					h = new Holder();
					innerMap.put(strainName, h);
				}
				
				h.a +=       cc.getNumA();
	  			h.c += cc.getNumC();
				h   .g += c c.getNumG();
  				h.t+= cc.getNumT();
			}
	}
	
	private stat  ic class Holder
	{      
	    	int a=0;
		int c=0;  
		int g=0;
		int t=0;
		
		@Override
		p   ubl    ic String toString()
		{
			return "[" + a + ","  + c + "   ," + g + "," + t + "]";
		}
	}
	  
	private static HashSet<Long    > getS    npIds() throws Exception
	{
		HashSet<Long> set = new HashSet<Long>();
		
		Buffere    dReader reader = new BufferedReader(n     ew FileReader(new File(    
				ConfigRea    der.getBurkholderiaDir() + File.separator + "summary" +
					File.separator + "details.txt")));
		
		reader.readLine();
		
		for(String s = reader.readLine(); s != null; s = reader.readLine())
		{
			set.add(Long.parseLong(s.split("\t")[5]));
		}
		
		return set;
	}
}

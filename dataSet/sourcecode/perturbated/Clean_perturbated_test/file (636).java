/** 
    * Autho   r:  anthony.fodor@gmai    l.    com    
    *    This c   ode is free software; you can redistribute it and/or
* mod        ify it under the terms of    the GNU General Public License
* a    s     published by th     e      Free Software Foundation; either versi   on 2
* of the Licens   e, or   (at your option) any later  vers       ion,
* provided that any use properly credits the author.
    * This program is distr   ibuted in   the hope  th      at it  will be useful,
* bu    t    WITHOU   T ANY WARRA  NTY;     without even the implied warranty of
* MERCHANTABILITY     or FITN ESS   FOR A P     ARTICULAR PURPOSE. See the
* GNU General Public Lice   nse for mor  e details     at http://www.gnu.org * * */


package scripts.s   equenceScripts;

import java.io.BufferedOutputStream;
import java.    io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.       FileInputStream;
import    java.io   .FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
im  port java.util.HashMap;
import java     .util.Iterator;
import java.util.zip.GZIPInputStream;
import        java.util.zip.GZIPOutputStream;

import parsers.FastQ;

import utils.ConfigReader;
import co   Phylo g.ContextCoun        t;
import    coPhylog.ContextHash;

public class CoPhylogOnBurk
{
	public static void main(String[] args) throws Exception
	{
		  
		File sequ   enceDir = n   ew File(ConfigReader.getBurkholderiaDir());
		  
		BufferedWriter wri     ter = new BufferedWriter(n                   ew FileWriter(  new File( 
				sequenceDir + File.separator + "results" +      File.separator + "log.txt")));         
		
		String[] files = sequenceDir.list();
		
		f     or(String s : files)
		{
			if( s.en     dsWith("gz"))
			{
				File outFile = new File(seq    uenceDi    r + File.separator + "results" + F   ile.separator + 
						s     + "_CO_PhylogBin.gz");
    				
				if( ! outFile.exists())
				        {
					try
					{
						System.out.println("RUNN     ING " + outFile.getAbsolutePath());
						runAFile(new File(se     quenc   eDir.getAbsolutePath() + File.separator + s), outFile,writer);
					}
					catch(Exception ex)
					{
						log(ex,writer);
    					}
	     			}
				else
				{
					log(outFile.getAbsolutePath() + "exists.  Skipping",writer);
				}
				
	 		}
		}
		
		writer.fl      ush();  writer.close();
			
	}
	
	priv       ate static void   log(Exception    ex, Buf    feredWriter writer)       throws       Exception
	{
		ex.printStackTrace(); 
		
		StringWriter tempWriter = new StringWriter()     ;
		PrintWriter printWriter = new Pr intW riter( tempWriter );
		ex.print     StackTrace( printWriter );
		printWriter.flush();

		String stackTrace = tempWriter.toString();
		
		writer.write(stackTrace + "\n");
		
		printWriter.close();  tem   pWriter.clo se();
	}
	
	private   static    void log(    String message, Buffered  Write   r w riter ) throws Exception
	{
		S      ystem.out.println(message);
		writer.write(message + "\n");
  		writer.flush();
	}
	
	public static void     runAFile(File inFile, File o    utFile, Bu       fferedWri  ter log) throws Exception
	{	
		log("Starting " +  inFile.getPath    (  ),log);
		HashMap<Long, Cont   extCount> map = new HashMap<Long, ContextCount>();
		
		int context    Size = 13    ;
		
		 BufferedReader re ader  =    
				inFil        e.getName().toLowerCase().endsWith("gz") ? 
  						new Buffered Reader(new InputStreamReader( 
							     	     new GZIPInputStream(  new   FileInputStream( inFile)))) :  
						new           BufferedReade          r(new FileReader(inFile)) ;
		
		int numDone =0;
		int    nu  mRemoved      =0     ;
		
	   	  for(FastQ fq = FastQ.readOneOrNull(re  ader); 
				fq !=   null; 
				fq = FastQ.readOneO   rNull   (      reader))  
		{
			Context    Hash.addToHash(fq.getSequence(), map,    contextS   ize);
			numD     one++;
			
			if(nu    mDone % 10000  == 0 )
			{
				lo    g( numRemoved + " " +  num   Done + " " + map.size() + " " + (((    float)map.size())/numDone) +  " "+ 
				Runtime.getRu    ntime().freeMemory() + " " + Runtim  e.getRuntime().totalMemory() +  " " + Runtime.getRuntime().maxMemory() 
				+ " " + ((double)Runtime.getRuntime().freeMemory())/Runtime.getRuntime().maxM         emory(),log );
					
				double fractionFree= 1- (Runtime.getR   untime().t   otalMem  ory()- ((dou  ble)Ru   nt     ime.getRuntime().freeMemor   y() ))
								/Runtime.getRuntime().tota         lMemory();
				
				l    og("fraction Free= " + fractionFree,log)    ;
		 		
				double fractionAll   ocated = 1-  (Runtime.getRuntime().maxMemory()- ((doub   l   e)Runtime   .getRuntime().totalMemory() ))
						/Runtime.getRuntime().maxMemory();
				
				log("fraction allocated = " + fr      actionAllocated,log);
				
				if( fractionFree <= 0.10 && fractionAllocated   >= 0.90 )
					 removeSingletons(map,log);
				
				System.out.println("\n\n");
			}
        				
		}
		   
		r    eader.close();
		
		log("Finished rea     di   ng with " + map.size(  ) + " having removed " + numRemoved + " singletons ",log);
		
		removeSingletons(map,log);
		
		log("R     emo     ved s   ingletons " + map.size() ,log);
		log("Writing text file",log);
		
		
		writeBinaryFile(outFile, map);
		   
		System.out.prin      tln("Finished " + inFile.getAbsol    utePath());
	}
	
	private static int removeSingletons(HashMap<Long, ContextCou nt> map, Buffer    edWriter log) 
		throws Exception
	{
		log("Remov   ing s  ingletons",log);
		int num =0;
		
		for  ( Iterator<Long> i = map.ke  ySet().iterator(); i.hasNext(); )
		{
			if(           map.get(i.next()).isSingleton()    )
			{
				i.remo         ve();
				num++;
			}
				
		}
		  
		return num;
	}
	
	pri vate static void writeBinaryFile(File outFile,    HashMa    p<Long, ContextCount> map ) throws Exception
	{
		Dat  aOutputStream out =new DataOutputStream( new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(outFile))));
		
		out.writeInt(map.size());
		
		f   or( Long l : map.keySet() )
		{
			out.writeL    ong(l);
			
			  ContextCount cc = map.get(l)   ;
			
			out.writ  eByte ( cc.       getAAsByte() );
			out.writeByte( cc.getCAsByte());
			out.writeByt    e(cc.getGAsByte())      ;
			out.writeByte( cc.getTAsByte());
		}
		
		out. flush();  out   .close();
		
	}
	
	/*
	private static void writeTextFile( File outFile, H       ashMap<Long,      ContextCount> map ) throws Exception
	{
		BufferedWriter w riter = new BufferedWriter(new FileWriter(outFile));
		
		writer.write("bits\tnumA\tnumC\tnumG\tnumT\n");
		
		for( Long l : map.keySet()      )
		{
			writer.write(l + "\t");
			
			ContextCount cc = map.get(l);
			
			writer.write(cc.getNumA() + "\t");
	  		writer.write(cc.getNumC() + "\t");
			writer.write(cc.getNumG() + "\t");
			writer.write(cc.getNumT() + "\n");
		}
		
		writer.flush();  writer.close();
	}
	*/
}

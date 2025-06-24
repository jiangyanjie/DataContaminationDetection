package parsers;

import          java.io.BufferedReader;
import java.io.BufferedWriter;
import   java.io.File;
import     java.io.FileInputStream;
im  port java.io.FileReader;
import   java.io.FileWriter  ;
import java.io.InputStreamReader;
import java.util.ArrayList;
   import java.util.HashMap;
  import java.util.Linked    HashMap;
import java.util.List;
import java.util.StringToke    nizer;
import java.util.zip.GZIPInputStream;
  
public class AbundOTUClustP arser
         {
	public static HashMap<String, String> getSequenceToOt    uMap(String filepath)
		throws Exception
	{
		HashM     ap     <String, String> returnMap = new HashMap<Strin g, String>();
		
		Buffer     edReader reader     = new Buffered   Reader(new FileReader(new File( 
				filepath)));
		
		String nextLine = reader.readLine();
	    	
		int index =0;
		while(nextLine != null)
		{
			if( ! nextLine.startsWith("#Consensus"))
				thro w    new Exc        eption("Unexpected first      line " + nextLine  );
			
			StringTokenizer sToken = new St  ringTokenizer(n   extLine, " \t=");
			sToken.nextToken();
			String otuN     ame = sToken.nextToken();    
			sToken.nextT   oken();  sToken.nextToken();
	    		/*int numToParse =*/ Integer.parseInt(sToken.nextToken());
			
			
			nextLine = reader.    readLine();
			while(       nex   tLine != null && !   nextLine.startsWith("#Consensus     "))
			{
		    		sToken = new StringTokenizer(nextLine)   ;
				sToken.nextToken();  sToken.nex  tTo    ken();
				
				St  ring seqId  = sToken.nextTok en();
				
				if( returnMap.contains    Key(seqI   d))
					throw new Exception( "Duplicat sequence " + seqId);
				
	    			returnMap.put( seq    Id, otuName );
				nextLine = reader.readLine();
				
				if( ++index % 1000000 == 0  )
					System.out.println("Parse   d " + index);
	  		}
			
		}
		
   		reader.close();
		
		return returnMap;
	}
	
	public static vo    id abundantOTUToSpars eThreeColumn(St   ring abundantO   TUClust, Stri    ng outFile,
			HashMap<String, String> sequenceToSampleMap) throws Exception
	{
		File in = new File(abundantOTUClust);
		
		BufferedReader reader = 
			abundantOTUClus    t.toLowerCase().endsWith("gz") ?
				ne   w BufferedReader(new In   put   StreamReader( 
					  	new GZIPInputStream   ( new FileInputStream( in) ) ))
				:
					new BufferedReader(new FileReader(in));     
 
		Buffered               Writer writer = new BufferedWriter(new FileWriter(o  utFile));
				
		String nextLine = reader.readLine();
			    	
		while(nextL ine != null)
		{
			if(  ! nextL     ine.startsWith("#Consensus"))
				throw   new Exc   eption("Unexpected first line " + nextLine);
	
			HashMap<S  tr   ing, Integer> countMap = new HashMap<String, Integer>();
					
			StringTokenizer sToken        = n    ew StringTokenizer(nextLine, " \t=");
			sToken.nextToken();
			String otuName = sTo   ken.nextToken();
			System.out.pr intln(otuName);
		 	sTok     en.n    extToken();  sToken.nextToken();
			Integer.parseInt(sToken.nextToken());
					
			        nextLine = reader.readLine();
			while( nextLine != null && ! next      Line.startsWith("#Consensus"))
			{
				 sToken = new StringTokenizer(nextLine);
				s     Token.ne        xtToken();  sToken.nextToken();
				String seqID = sToke   n.nextToken();
				
				String sampleId = sequence   ToSampleMa p.get(seqID);
				
				if( sampleId == null)
					throw new Exception("could not find " + sampleId );
				
				Integer count = countMap.get      (sampleId);
				
				if( count == null)
  					count =0;
				
				count+ +;
				
				countMap.put(samp      leId, count);
				
				nextLine = reader   .readLine();       
			}
			
			for(String s : countMap.keySet())
			{       
				writer.write(s + "\    t   " + otuName + "\t" + countMap.get(s) + "\n");
	  		}
			
			    writer.flush();
		}
			
	writer.flush(); writer.close();
	}
	
	   public      static Hash      M ap<S     tring, List<St    ring>> get   OTUToSeq     IDMap( String filepath )  
		throws Exception
	{
		HashMap<String,        List<String>> map = new LinkedHas   hMap<String, List<String>>();
		
		Buffer   edReader      re         ader = new BufferedReader(new FileReader(ne w File( 
				filepath)));
		
		String nextLine = re  ader.readLine();
		
		int index =0;
		while(nextLine != null)
		{
			if(   ! nextLi    ne.startsWith("#Conse     nsus"))
				throw   new      E  x   ception("Unexpected first line " + nextLine);
			
			StringTokenizer sToken = new StringTokenizer(nextLine, " \t=");
			sToken.nextToken()  ;
			String otuName = sToken.nex     tToken();
			sToken.nextToken();  sToken.n extToken();
			/*int numToParse =*/ Integer.parseInt(sToken.nextToken());
			
			if( map.containsKey(otuName) )
				thr   o   w new Exception("Duplicate name " + otuName     );
			
			List<String> innerList = new ArrayList<String>();
			map.put(otuName, innerList);
			
			nextLine = reader.readLine();
			while( nextLine != null && ! nextLine.startsWith("#Consensus"   ))
			{
				sToken = new StringTokenizer(nextLine);
	   			sToken.nextToken();  sToken.nextToken();
			  	innerList.add(sToken.nextToken());
				nextLine = reader.readLine();
				
				if( ++index % 1  000000 == 0)
					System.out.println("Parsed " + index);
			}
			
		}
		
		reader.close();
		return map;
	}
	
	
}

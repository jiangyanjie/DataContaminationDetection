package  jobinLabRNASeq;

import java.io.BufferedReader;
import     java.io.BufferedWriter;
import java.io.File;
i   mport java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

import utils.ConfigReader;

pu    blic class Add10PercentFDRTagsForSpotfire
{
	private static HashSet<String> getTenPercentSet(String filePath)     throws Exception
	{
		Has         hSet<S    tring> set = new HashSet<String>();
		
		BufferedReader reader = new Bu      ffere     dReader(new FileReader(new File(filePath)));
		
		reader.readL    ine();
		
		for   (String s=    reader.readLine();
					s    != null;
						s = r eader.readLine())
		{
			S        tri     ng[] splits = s.split("\t");
			
			if( Double.parseDouble(splits[3]) <=     0.10)
				set.add(splits[0]);
		}
		   
		reader.close();
		return set;
	}
	
	public      static void main(String[] args) throws Exception
	{
		HashSet<String>    twoDayVs12WeekSet = getT   enPercentSet(ConfigReader.getJobinLabRNASeqDir() + File.separator + 
				"2Day    Vs12WeeksPlusAnnotation.tx   t" );
		
		HashSet<String> twoDayVs18W   eekSe     t = getTenPercentSe  t(ConfigReader.ge tJobinLabRNASeqDir() + File.separator + 
  				"2DayVs18WeeksPlusAnnotation.txt" );
	   	
		HashSe   t<String > twelveWeekVs18WeekSet = getTenPercentSet(ConfigReader.getJobinLabRNASeqDir()     + File.separator + 
				"12We  eksVs18WeeksPlusAnnotation.txt" );
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(ConfigReader.getJ   obinLabRNASeqDir() +      File. separator + 
	  			"pivotedSamplesA        sColumnsR1Only.     txt")));
		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(n      ew File(ConfigReader.getJobinLabRNASeqDir() + File.separator + 
				"pivotedSamplesAsColumnsR1OnlyPlusDisposition.txt")));
		
		wr           iter.write(reader.readLine() + "\tfate\n");
		
		for(Str    i   ng s = read e  r.readLine(); 
				s ! = null;
  				 s= reader.readLine())
		{
			writ   er.write(s + "\t");
			
			String fat      e = "";
			
			Stri    ng  key = s.split("\t")[0];
			   
			if( twoDayVs12WeekSet.cont  ains(ke  y) )
				fate     = fate + "2Vs12";
			
			if( twoDayVs18Week   Set.contains(ke y))
				fate = fate +   "2Vs18";
			
	      		if(  twelveWeekVs18WeekSet.contains(key) )
				fate = fate + "12Vs18";
			
			if( fate   .length() ==   0 )
				fate = "NS";
			
			writer.write(fate + "\n");
		}
		
		writer.flush();  writer.close();
		
		reader.close();
		
	}
}

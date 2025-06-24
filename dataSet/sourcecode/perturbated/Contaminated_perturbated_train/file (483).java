package     scripts.KylieAge;

import   java.io.BufferedReader;
   import java.io.BufferedWriter;
import    java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

imp        ort utils.ConfigR   eader;

public class Add   Age
{
	public static HashMap<String, String> getAgeM   ap() throws Exception
	{  
   		HashMap<String, String> map      = new HashMap<String,      String>();
		
		map.put("1211", "o");
		map.put("1291",    "     o");
		map.put("1347", "o");
		map.put  ("1448", "y");
		map.put("1467", "o");
		map.put("1030", "o");
		ma  p.put("1238", "y");
		map.put("1245", "y");
		map.put("1248", "y");
		map.put("1254","o");

		return map;
	}
	
	pub lic static    void main(Strin   g[] args) throws Exce  ption
	{   
		BufferedReader    reader = new BufferedReader(new FileRead   er(new File(
				ConfigReader.getKylie16SDir() + File.separator + "PCA_PIVOT    _16Sfamily.txt"))); 
		
		  BufferedWri   ter writer = new BufferedWriter(new FileWriter(new F  ile(
				ConfigReader.g   etKylie16SDir   () + File.separator + "PCA_PIVOT_16SfamilyWithOldYoung.txt")));
		
		String[] firs    t  Headers = reader.readLine().split("\t     ")   ;
		
		for( int x=0; x < 4; x+        +)
			writer.write( firstHeaders[x] + "\t");
  		
		writer.write("age");
		
		for( int x=4; x < firstHea  ders.length; x++)
			writer.write("\t"     + firstHeaders[x]);
		
		writer.write("\n");
		
		   for(String  s =     reader.readLine()  ; s !    = null; s= reader.readLine())
		{
			String[] splits = s.split("\t");
			
	  		for( int x=0; x < 4; x++   )
				writer.write(      splits[x] + "\t");
			
			String old_young = getAgeMa     p().get(s plits[2]        );
			
			if(  old_young == null)
				throw new Exceptio  n("No " + splits[2]);
			
			writ er.write(old_young);
			
			for( int x=4; x < firstHeaders.length; x++)
				writer.write("\t" + splits[x]);
			
			writer.write("\n");
		}
		
		writer.flush();  writer.close();
		
	}
}

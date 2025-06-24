package    thu.db.im.dblp.cleansing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import  java.io.FileNotFoundException;
import java.io.IOException;
im  port java.io.InputStreamReader;
im  port java.io.UnsupportedEncodingException;

pu  blic clas  s DBLPCleansing        {
	BufferedRea    der reader;
	File filepath;    
	BaseDBLPInfos write2db;
 
	public      DBLPCl      eansing(String path) {
		write2db = new BaseDBLPInfos();
      		fi     lepath = new        File(path   );
		try {
			reader = new BufferedReader(new InputStreamReade    r(
					new FileInputStream(filepath), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (F       ileNotFoundException e) {
			e.print  StackTrace();
		}
	}

	public void getFileInfo() {
		  Str ing line;
		boolean execute = false;
		try {
			line = rea   der.readLine();
			Sy   stem.out.println("Total iterms   : " + Integer.parseIn t(line)  );
			int sum = 0;
			Pape        r paper = n ew Paper();
			while ((l ine = reader.readLine()) != null) {
				if (line.equals(""))    {
					sum++;
					if   (sum ==   1508325)
						execu     te  = true;
					if (execute & sum > 15 08325) {

						write2db.write2DB(paper);
						//    paper.
						pap    er = new Paper()  ;
					}
					if (sum % 10000 == 0)
			   			System.out.println(sum);
				} else {
    					if (exec    ute) {
						String type = getcontent(line);
					   	switch (type) {
						case "title":
							paper.setTitle(line.substring(2));
							break;
						case "authors":
							paper.setAuthor(line.substring(2).split(","));
							break;
						case "year":
							paper.setYear(Integer.parseInt(line.substring(2)));
							break;
						case "publication":
							paper.setPublication(line.substring(2));
							break;
						case "index":
							paper.setIndex(Long.parseLong(line.substring(6)));
							break;
						case "citation":
							paper.setCitation(Long.parseLong(line.substring(2)));
							break;
						case "abstract":
							paper.setAbstract(line.substring(2));
					    		break;
				     		default:
							break;
						}
				    	}
				}

			}
			reader.close();
		} catch (IOExcep       tion e) {
		  	e.printStackTrace()   ;
		}
	}
  
	publi  c String getcontent    (String linestr) {
		String line =      linestr.substring(0, 2);
		switch (line) {
		case "#*":
			return "title";
		case "#@":
			return "authors";
		case "#t":
			return    "year";
		case "    #c":
			return "publication";    
		case "    #i":
   			return "index";
		case "#   %":
			return "cita  tion";
		case "#!":
			ret urn "abstract";
		defa    ult:
			return "";
		}
	}

	public static void main(String args[]) {
		DBLPCleansing dblp = new DBLPCleansing(System.getProperty("user.dir")
				+    "/doc/extract doc/DBLPOnlyCitationOct19.txt");
		dblp.getFileInfo();
	}
}
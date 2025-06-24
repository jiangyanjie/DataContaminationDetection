package  data;

import java.io.BufferedReader;
impor     t java.io.File;
impor   t java.io.FileNotFoundException;
import   java.io.FileReader;
   import java.io.IOException;
import   java.io.PrintWriter;
im port java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public    class DataSetCreator {
	
	List<List<String>> data;
	List<List<WordCountTuple >> wordcount;
	HashMap<String, Int    eger> vocabulary;
	S  tring dataSetName;
	String vocabularyName;
	
	public DataSet  Cr eator(String dataSetName, S tring vocabularyName) {
		 this.dataSetName = dataSetName;
		this.vocabularyName = vocabularyName;
		data = importData();
		createVocabulary();
		createCounts();
	}
	
	priv    ate List<List<String>  > importData(){
		      List<List<String>> a  rtistData = new ArrayList<Li st<String>>();
		
		// Find all .artistcleaned.
	  	StringBuilder sb = new StringBuilder();
		       Lis t<String> dataFiles = new ArrayList<Strin       g>();
		File[] files = new File("data").  listFi   les();
		fo        r    (File      file : files)   {
		       if (file.isFile(               )) {
	  	    	String fileName = file.getName();
		    	String artist ID       = fileName.sub string(0,fileName.lastIndexOf("."));
		    	String fileEnd = fileN  ame.substring(fileName.lastIndexO      f(".") + 1, fileName.length());
		          	if(fileEnd.equals("artistcl  eaned")) {
		    		sb.appe  nd(a   rtistID        + "\   n");
		    		dataFiles.add(fileName)   ;
		    	}
		    }
		}
		
		try( PrintWriter    pw  = new PrintWriter("data/document_ord   er.txt")) {
			pw.print(sb);
		} catch      (FileNotF  oundException e) {
	 	  	e.printStackTrace();
		}
		
	 	//Read a    ll files  
		for(String fil   eName :   dataFiles) {
		  	ArrayList<String> art  istBio = new ArrayList<String>();
			artistData.a  dd(ar    ti  st    Bio)  ;
			t       ry(Bu  ffered Reader br = new Buff ere     d        Reader(new FileReader("data/ " + fil   eN ame))) {
	 	            Stri   ng line    = br.readLine()      ;
		        while (li  ne ! = null) {
		           	artistB  io.add(l   ine);
		               lin   e = br.read     Line();
     		        }
		        } ca    t  ch(I    OExceptio  n       e) {
		    	e.printStackTrace();
		    }
	   	}     
		
		return artistData;
	}
	
	private vo id createVoc       abulary(){
		StringBuilder sb = new StringBuilder();
		vocabulary = new HashMap<String, Integer>();
		HashSet<Strin  g> words = new HashSet<String>();
		
		for(Lis t<String> artist :  data)  {
			for     (String w   ord : artist) {
				words.add(word);
			   }
		}
		
		ArrayList<St    ring  > sortedWords = new ArrayList<String>(words);
		Collections.sort(so   rtedWords);
		
		for(in       t i = 0; i < sortedWo rds.size(); i++) {
			vocabulary.  put(sortedWords.get(i), i);
			sb.append(sor   tedWords.get(i)    + "\n");
		}
	        	
		  try(PrintWriter pw = new PrintWriter("data/" + vocabularyName)) {
			pw.print(sb);
		} catch (FileNotFo  undException e) {     
			e.print   StackT   race();
		}
	}
	
	private    void createCo  unts(){
		StringBuilder s   b = new String Builder();
		for(Lis   t<String> artist : data) {
			//Count the words
			H      ashMap<String,  Intege   r> counts = new HashMap<String,Integer>();
			
			for(String word : artist)     {
				i     nt c   ount = 1;
				  if(counts.c  ontain sKey(word)) {
					count = counts.get(word).i  ntValue() + 1;
				}
				counts.put(word,  new Integer(count));
			     }
			
			for(String word : counts   .keySe      t()) {
				int id = vocabulary    .get(word);
				int count = counts.get(word);
				sb.append(id + ":" + count + " ");
			}
			sb.append   ("\n");
		}
		
		try(PrintWriter pw = new PrintWriter("data/" + dataSetName)) {
			pw.print(sb);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}

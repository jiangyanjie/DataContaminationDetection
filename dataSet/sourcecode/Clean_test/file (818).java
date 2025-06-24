package com.authorprofiling.createindex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;

public class CreateIndex {
	
	static TreeMap<String,ArrayList<Integer>> Genderindex_tree = new TreeMap<String,ArrayList<Integer>>();
	static TreeMap<String,ArrayList<Integer>> Ageindex_tree = new TreeMap<String,ArrayList<Integer>>();
	static String outputFolderGender = "/home/santosh/Downloads/AP/sampleIndex/genderIndex/";
	static String outputFolderAge = "/home/santosh/Downloads/AP/sampleIndex/ageIndex/";
	static int fileIndex = 0 , fileIndexAge = 0;
	public static void main(String argv[]){
		
	}
	
	public void createIndex(String entity, String gender, String age, int docsProcessed){
		
		ArrayList<Integer> temp_secondary = new ArrayList<Integer>();
		ArrayList<Integer> temp_secondary1 = new ArrayList<Integer>();
		
		if(gender.compareToIgnoreCase("m") ==0){ //creating index on the basis of gender
			
			if(Genderindex_tree.containsKey(entity)){
				temp_secondary = Genderindex_tree.get(entity);
				temp_secondary1.add(temp_secondary.get(0)+1);
				temp_secondary1.add(temp_secondary.get(1));
				Genderindex_tree.put(entity, new ArrayList<Integer>(temp_secondary1));
				temp_secondary1.clear();
				temp_secondary.clear();
			}
			else{
				temp_secondary1.add(1);
				temp_secondary1.add(0);
				Genderindex_tree.put(entity, new ArrayList<Integer>(temp_secondary1));
				temp_secondary1.clear();
				temp_secondary.clear();
			}
			
		}
		
		else{ // if the document is written by female
			
			if(Genderindex_tree.containsKey(entity)){
				temp_secondary = Genderindex_tree.get(entity);
				temp_secondary1.add(temp_secondary.get(0));
				temp_secondary1.add(temp_secondary.get(1)+1);
				Genderindex_tree.put(entity, new ArrayList<Integer>(temp_secondary1));
				temp_secondary1.clear();
				temp_secondary.clear();
			}
			else{
				temp_secondary1.add(0);
				temp_secondary1.add(1);
				Genderindex_tree.put(entity, new ArrayList<Integer>(temp_secondary1));
				temp_secondary1.clear();
				temp_secondary.clear();
			}
		}
		
//		Age index 
		
		if(age.compareToIgnoreCase("10s") ==0){ //creating index on the basis of gender
			
			if(Ageindex_tree.containsKey(entity)){
				temp_secondary = Ageindex_tree.get(entity);
				temp_secondary1.add(temp_secondary.get(0)+1);
				temp_secondary1.add(temp_secondary.get(1));
				temp_secondary1.add(temp_secondary.get(2));
				Ageindex_tree.put(entity, new ArrayList<Integer>(temp_secondary1));
				temp_secondary1.clear();
				temp_secondary.clear();
			}
			else{
				temp_secondary1.add(1);
				temp_secondary1.add(0);
				temp_secondary1.add(0);
				Ageindex_tree.put(entity, new ArrayList<Integer>(temp_secondary1));
				temp_secondary1.clear();
				temp_secondary.clear();
			}
			
		}
		
		else if(age.compareToIgnoreCase("20s") ==0){ // if the document is written by female
			
			if(Ageindex_tree.containsKey(entity)){
				temp_secondary = Ageindex_tree.get(entity);
				temp_secondary1.add(temp_secondary.get(0));
				temp_secondary1.add(temp_secondary.get(1)+1);
				temp_secondary1.add(temp_secondary.get(2));
				Ageindex_tree.put(entity, new ArrayList<Integer>(temp_secondary1));
				temp_secondary1.clear();
				temp_secondary.clear();
			}
			else{
				temp_secondary1.add(0);
				temp_secondary1.add(1);
				temp_secondary1.add(0);
				Ageindex_tree.put(entity, new ArrayList<Integer>(temp_secondary1));
				temp_secondary1.clear();
				temp_secondary.clear();
			}
		}
		
		else{
			
			if(Ageindex_tree.containsKey(entity)){
				temp_secondary = Ageindex_tree.get(entity);
				temp_secondary1.add(temp_secondary.get(0));
				temp_secondary1.add(temp_secondary.get(1));
				temp_secondary1.add(temp_secondary.get(2)+1);
				Ageindex_tree.put(entity, new ArrayList<Integer>(temp_secondary1));
				temp_secondary1.clear();
				temp_secondary.clear();
			}
			else{
				temp_secondary1.add(0);
				temp_secondary1.add(0);
				temp_secondary1.add(1);
				Ageindex_tree.put(entity, new ArrayList<Integer>(temp_secondary1));
				temp_secondary1.clear();
				temp_secondary.clear();
			}
			
		}
		
		
		
		
	}
	
	
	public void print_gender(){
		 File file = new File(outputFolderGender+Integer.toString(++fileIndex)+".txt");
	      try {
	    	  BufferedWriter outer = new BufferedWriter(new FileWriter(file));
	    	  //BufferedWriter outer1 = new BufferedWriter(new FileWriter(file1));
		      String key;
		      for(Entry<String, ArrayList<Integer>> entry : Genderindex_tree.entrySet()) {
		    	  key=entry.getKey();
		          StringBuffer data = new StringBuffer(key+"|"+entry.getValue().get(0)+"@"+entry.getValue().get(1)+"\n");
		          outer.write(data.toString()); 
		      }

	      outer.flush(); 
	      //outer1.flush(); 
	      outer.close(); 
	      //outer1.close(); 
	      Genderindex_tree.clear();
	     
	      } catch (Exception e) { 
	      e.printStackTrace(); 
	      }
	}
	
	public void print_age(){

		 File file = new File(outputFolderAge+Integer.toString(++fileIndexAge)+".txt");
	      try {
	    	  BufferedWriter outer = new BufferedWriter(new FileWriter(file));
	    	  //BufferedWriter outer1 = new BufferedWriter(new FileWriter(file1));
		      String key;
		      for(Entry<String, ArrayList<Integer>> entry : Ageindex_tree.entrySet()) {
		    	  key=entry.getKey();
		    	  StringBuffer data = new StringBuffer(key+"|"+entry.getValue().get(0)+"@"+entry.getValue().get(1)+"@"+entry.getValue().get(2)+"\n");
		          outer.write(data.toString()); 
		      }

	      outer.flush(); 
	      //outer1.flush(); 
	      outer.close(); 
	      //outer1.close(); 
	      Ageindex_tree.clear();
	     
	      } catch (Exception e) { 
	      e.printStackTrace(); 
	      }
	}

}
package   com.authorprofiling.createindex;

import java.io.BufferedWriter;
import    java.io.File;
imp   ort java.io.FileWriter;
imp  ort java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Create  Index {
	
	sta    tic TreeMap<String,Arr  ayList<Integer>> Genderindex_tree = new TreeMap<String,ArrayList<Integer>>();
	static TreeMap<String,ArrayList<Integer  >> Ageindex_tree = new TreeMap<String,ArrayList<Integer>>();
	static String   outputF olderGender = "/home/santosh/Downloads/AP/sampleIndex/genderIndex/";
	st   atic String ou    tputFolderAge   = "/home/santosh/Downloads/AP/sampleInde  x/ageIndex/";
	static int fileIndex =               0 , fileIndexAge = 0;
	public static voi  d main(String argv[]){
		
	}
	
	    public void creat  eI     ndex(String ent  ity, String gender, String age, int docsProcessed){
		
		ArrayList<Integer>  temp_secondary = new ArrayList<Integer>();
		A    rrayList<Integer> temp_secondary1 = new ArrayList<Integer>();
		
		if(gender.compareToIgnoreCase("m") ==0){ //creating index    on the basis of gender
			
			i f(Genderindex_tree.containsKey(entity)){
				temp_seco   ndary = Genderindex_tree.get(entity);
				temp_secondary1.add(temp_secondary.get(0)+1);
				temp_secondary1.add(temp_secondar   y.get(1));
				Genderindex_tree.put(entity, new ArrayList<Integer>(temp_secondary1));
				temp_secondary1.clear();
				temp_secondary.clear();
			}
			else{
				temp_secondary1.add(1);
				temp_secondary1.add    (0);
				Genderindex_tree.      put(entity, new ArrayList<Integer>(temp_secondary1));
				temp    _secondary1.clear(    );
				temp_  secondary.clear        ();
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
				temp_secondary1.cle   ar();
				temp_secondary.cle   ar();
			}
		}
		
/      /		Age index 
		
		    if(age.compareToIgnoreCase("10s") ==0){ //creating index on the basis of gender
			
			if(Ageindex_tree.con  tainsKey(entity)){
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
				temp_seco      ndary1.add(0);
				Ageindex_tree.put(entity, new ArrayList<Integer>(temp_   secondary1));
				temp_secondary1.clear();
				temp_secondary    .cl   ear();
			}
		 	
		}
		
		else if(a    ge.compareToIgnoreCase("20s") ==0){ // if      the document is written by female
			
			if(Ageindex_tree  .containsKey(entity)){
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
				temp_secondary.   clear();   
			}
		}
		
		else{
			
			if(Ageindex_tree.containsKey(entity)){
				temp_secondary = Ageindex_tree.get(entity);
				temp_secondary1.add(temp_secondary.get(0));
				temp_secondary1.add(temp_secondary.get(1));
				te mp_secondary1.add(temp_secondary.get(2)+1);
				Ageindex_tree.put(entity, new ArrayList<Integer>(temp_secondary1));
				temp_secondary1.clear();
				temp_secondary.clear();
			}
			else{
				temp_s    econdary1.add(0);
				temp_secondary1.add(0);
				temp_secondary1.add(1);
				Ageindex_tree.put(entity, new ArrayList<Int  eger>(temp_secondary1));
				temp_secondary1.clear() ;
				temp_s       econ         dary.clear();
			}
			
		}
		
		
	   	
		
   	}
	
	
	public void pr    int_gender(){         
		 File        fi   le = new File(outputFo   lderGender+Integer.     toString(++fileIndex)+".txt");
	        tr   y {
	    	  BufferedWriter outer      = new BufferedWr    iter(      new FileWrite      r(f  ile));
	       	     //BufferedWriter ou  ter1     =   new BufferedWriter(new FileW   riter(file   1));
		          String key;
   		      for(Entry  <String, Arra  yList   <Int    eg   er>>    entr  y     : Gen   derindex_tree.  entr      yS   e  t()) {
		    	  key=entry.getKey();
		                  Strin        gBuffer data = ne  w  StringBuffe  r(key+         "|"+entr      y .getVal ue().get(0)+"@"+   entr   y.getValue().get(1)+"\n"   )      ;                  
		             outer.write(da ta.toString());      
   		      }

    	       outer  .flush(); 
	      //o   uter1.flush(); 
	      outer       .close(); 
	      //outer1.clo    se();           
	      Genderindex_tree.clear();
	          
	      } catch (Exception e)  { 
	      e.printStackT   race(); 
	      }
	  }
 	
	public void print_    age(){

		   File file =    n     e    w File(   outputFolde    rAge+Integer.toString(+       +f    ileIndexAge)+".txt");
	        try {
	    	  Bu     fferedWriter outer = new BufferedWriter(new Fi leWr iter(file));
	      	  //Buff      eredWriter outer1 = new BufferedWriter(new FileWriter   (fil     e1));
     	      	      String k    ey;
		      for(Entry<String, ArrayList<Integer>> entry : Ageindex        _  tree.en   trySet()) {
		       	      ke         y=e  ntry    .g         et       Key();
		    	  StringBuffer data = new StringBuf     fer(key+"|"+e         ntry   .getValue().get(0)+"@"+entry.getVal  ue().get(1)+"@"+      entry.ge     tValue().get(   2)+"\n"  );
		          outer.write(data.toString()); 
		          }

	        outer.flu  sh()   ; 
	      //outer1.flush()  ; 
	       outer.close    (); 
	      //outer1.close(); 
	      Ageindex_tree.clear(    );
	     
	      } catch (Exception e) { 
	      e.printStackTrace(); 
	      }
	}

}
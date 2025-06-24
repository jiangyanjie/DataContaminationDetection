/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataCollector;


import java.io.*;
import java.text.*;
import java.util.*;

/**
 * This class is a collection of static file writers and readers for data collector class
 * @author Jeremy Schanz 
 */
public class DataCollectorWriter
{
    
     /**
   * This function saves the stock data collected to the end of that stocks data file 
   * @param data array holding the collected data 
   * @param tag stock tag name
   * @param path path to where file is located
   */
     public static void addData(String[] data, String tag, String path  )throws IOException
     {
         // only writes if data is real
          if(!data[0].equals("0.00")&&!data[0].equals("N/A") )
          {
         
             String tagFile = path+tag.trim()+".txt";
             File file = new File(tagFile);
             
             //creats new file if non existis 
             if(!file.exists())
             {
                
                 file.createNewFile();
             }
             // adds date and time
             DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
             Calendar calender = Calendar.getInstance();

             String out = dateFormat.format(calender.getTime())+" ";
              // turns array in to string 
              for(int i =0; i<data.length;i++)
              {
                  out = out +data[i]+" ";
              }
          
           
            
	     BufferedWriter buffer = new BufferedWriter(new FileWriter(tagFile,true));
             buffer.newLine();
             buffer.write(out);
	     buffer.close();
      
          
          }
          
      }
     
     /**
     *This function writes the quick look data (all the stocks and there scores) to a file
     * @param data array of data being saved to file
     * @param fileName file name where data is being saved to 
     * @throws IOException
     */
    public static void addQuickLook(ArrayList<String> data, String fileName)throws IOException
     {
         
      	       
            PrintWriter output = new PrintWriter(fileName);
           //Sorts data alphabetically 
            Collections.sort(data, new Comparator<String>() {
                @Override
                public int compare(String  string1, String  string2)
                {   

                    return  string1.compareTo(string2);
                }
            });
      	      
              
            for(int i =0;i<data.size();i++)
            {   
      	         output.println(data.get(i));
      	    }
              
      	    output.close();
      
     }
    
     /**
   * This static function reads the stock tags from a file put it into an array
   * @param fileName name of file
   * @return Array containing stock tags 
   */
      public static ArrayList<String> getStockName(String fileName)
      {
         Scanner scanFile;
         String name = fileName.trim();
         //Checks if file exists 
         try
         {
            File  tempFile = new File(name);
            scanFile= new Scanner(tempFile);
           
         }
         catch (Exception ex) 
         {
            return null;
        }
        ArrayList<String> tagString = new ArrayList<String>();
        String next;
        
              // reads file line by line (each line is a stock tag
      	      while(scanFile.hasNextLine())
              {
      	      	  tagString.add(scanFile.nextLine());
      	      }
      	     
          
          return  tagString;
      }
      
      
}

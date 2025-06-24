/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataCollector;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.text.*;

/**
 * This class collects the data from a list of stock tags 
 * @author Jeremy Schanz
 */


public class DataCollector 
{
    
   // private static ToFile dataList;
    private static ArrayList<String> quickLook;
    private static final String filePath = ""; //"C:\\Users\\KappaSigGO\\Dropbox\\save\\";
    private static final String quickLookFileName = filePath+"UnderVal.txt";
    private static final String tagListFileName = filePath+"AAtest.txt";//"combinedList.txt";
    
    
    /**
   * This Function is the main function for data collector 
   * it is responsible for collecting the stock data and 
   * saving the data to files 
   * @param arg command line string array (not used).
   * @exception IOException On file error.
   */
    public static void main(String[] arg)throws IOException
    {
        quickLook = new ArrayList<String>();
        // Checks if today is a day the stock market is open
        if(isStockDay() == true)
        {  
          
            ArrayList<String> list = DataCollectorWriter.getStockName( tagListFileName); 
          //ArrayList<String> list = new ArrayList<String>();
          // list.add("A");
            
            // loops throught stock tag list 
           for(int i =0; i<list.size();i++)
            {
                String stock = list.get(i);
                stock = stock.trim();
                
                String stockUrl = "http://finance.yahoo.com/q/ks?s="+stock+"+Key+Statistics/";
                
                search(stockUrl,stock);  
        
            }
           //saves over writen UnderVal file 
           
            DataCollectorWriter.addQuickLook(quickLook, quickLookFileName);
        
            
        }
    }
      
    /**
   * This function opens up the URL connection and read the HTML line by line
   * @param site full URl name.
   * @param tag stock tag that data is being found for.
   */
    public static void search(String site, String tag) throws IOException
    {    
       
        //SearchHTML find = new SearchHTML();
        BufferedReader reader = null;
        // used to repeat if there is an internet connection error
        while(true)
        {
          try 
          {
            // this code sets up URL connection
             URL myUrl = new URL(site);
             reader = new BufferedReader(new InputStreamReader(myUrl.openStream()));
            
           } 
           catch (Exception ex) 
           {
              // if error is found it trys again
             //  System.out.println(ex.toString());
              continue;
           }
          // if no error loop breaks
          break;
        }
        
        if(reader != null)
        {
            String[] out = SearchHTML.searchHTML(reader);
            String score = out[out.length-1];
         
            System.out.println(tag+" : "+ score);
        
            if(!out[0].equals("0.00") && !out[0].equals("N/A") )
            {
                //saves data to file 
                quickLook.add(tag+"  "+out[0]+"   "+score);
                DataCollectorWriter.addData(out, tag,filePath);
            }
        }
       
        
    }
    
     /**
   * This function checks if the stock market it open today
   * @return whether or not stock market is open today .
   */
    public static boolean isStockDay()
    {
        // get current date
        Calendar date = Calendar.getInstance();
        
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH) + 1;
        int year = date.get(Calendar.YEAR);
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        
        
       
        // weekend
       if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)
       {
           System.out.println("weekend");
           return false;
       }
       
       // good friday
       if(month == 3 || month == 4)
       {
           int goodFriday = easter(year)-2;
           
           int today = (month-3)* 31 + day;
           
           if(today == goodFriday)
           {
               System.out.println("good friday");
               return false;
           }
           
       
       }
       
       // January first 
       if(month == 1 && day == 1)
       {
             
           return false;
       }
      
       // chrismass
       if(month == 12 && day == 25)
       {
            
           return false;
       }
       
       // 4th of july 
       if(month == 7 && day == 4)
       {
            
           return false;
       }
       
       // MLK day 
       if(month == 1 && day <= 21 && day >= 15 && dayOfWeek == Calendar.MONDAY  )
       {
            
           return false;
       
       }
       
       // Presidence day
       if(month == 2 && day <= 21 && day >= 15 && dayOfWeek == Calendar.MONDAY  )
       {
            
           return false;
       
       }
       
       // memorial day
       if(month == 5 &&  day >= 25 && dayOfWeek == Calendar.MONDAY  )
       {
            
           return false;
       
       }
       
       // Labor day
       if(month == 9 && day <= 7 && dayOfWeek == Calendar.MONDAY  )
       {
           
           return false;
       
       }
       
       // Thanksgiving day
       if(month == 11 && day <= 28 && day >= 22 && dayOfWeek == Calendar.THURSDAY  )
       {
            
           return false;
       
       }
      
       
       return true;
    
    }
     /**
   * This function calculates the data of easter based on a given year
   * @param year current year.
   * @return the date of easter in year.
   */
   public static  int easter(int year) 
   {

    int golden = (year % 19) + 1;  // metonic cycle 
    int century = (year / 100) + 1; 
    int correct = (3 * century / 4) - 12; //leap year correction 
    int moon = ((8 * century + 5) / 25) - 5; // sync with moon's orbit 
    int d = (5 * year / 4) - correct - 10;
    int epact = (11 * golden + 20 + moon - correct) % 30; 
    // coorects the epact
    if ((epact == 25 && golden > 11) || epact == 24)
    {
      epact++;
    }
    
    int retValue = 44 - epact;
    retValue += 30 * (retValue < 21 ? 1 : 0); 
    retValue += 7 - ((d + retValue) % 7);
    
    return retValue;
  }
    
   
     /*
        ToFile stockList= new ToFile("UnderVal.txt");
           
            ArrayList<String> stock = stockList.rStock(); 
        
            System.out.println(stock.size());
      	      for(int b=0;b<stock.size();b++)
              {
      	      	      
      	      	      for(int c=b+1;c<stock.size();c++)
                      {
                           String[] dataString =  stock.get(b).split("\\s+");
      	      	      	   String[] dataString2 =  stock.get(c).split("\\s+");
                             
                             if(Integer.parseInt(dataString[2]) <Integer.parseInt(dataString2[2]))
                              {
      	      	      	      	      String temp = stock.get(b);
      	      	      	      	      stock.set(b,stock.get(c));
      	      	      	      	      stock.set(c,temp);
      	      	      	      	      //com = stock.get(b);
      	      	      	      }
      	      	      }
                     // System.out.println(stock.size());
      	      }
              
             for(int i = 0;i<stock.size();i++)
             {
                 System.out.println(stock.get(i));
             }
              
           // stockList.addArray(stock);
            //stockList.out();  
           /* 
           ToFile stockList2= new ToFile("nyseStocks.txt");
           ArrayList<String> list2 = stockList2.rStock();   
         
           
           for(int i = 0;i<list.size();i++)
           {
               boolean isyes = true;
                       
               for(int j =0; j<list2.size();j++)
               {
                   if(list.get(i).equals(list2.get(j)))
                   {
                       isyes = false;
                       break;
                   }
                   
                   
                   
               }
               
               if(isyes)
               {
                   list2.add(list.get(i));
                   
               }                   
               
           }
           
           dataList.addArray(list2);
        dataList.out();  
        */ 
   
    //2013/12/27 22:36:30 57.17 3.61 1.86 27.22 18.97B 16.01 13.83% 19.01B 57.94 39.64 83.50% 2,353,520 2,180,590 2.81 3.11 2.80 13.67 10.68% 15.03% 6.00% 19.89 -2.80% 3.54B 1.39B 724.00M 2.10 -50.40% 8.06 2.70B 51.03 1.15B 753.00M 1.66 29.11% 54.21 49.27 331.81M 330.32M 0.23% 2.94M 1.20 0.90% 3.02M 0 
    //2013/12/27 23:02:10 57.17 3.61 2.00 19.83 18.97B 18.09 20.19% 15.81B 57.94 39.64 83.50% 2,353,520 2,180,590 2.81 2.53 2.29 11.28 14.68% 15.26% 6.58% 19.89 -0.10% 3.60B 1.40B 1.01B   2.88 -34.90% 7.31 2.36B 44.39 1.28B 692.62M 1.84 29.11% 54.21 49.27 331.81M 343.47M 0.23% 3.01M 0.90 0.90% 2.94M 3 
}

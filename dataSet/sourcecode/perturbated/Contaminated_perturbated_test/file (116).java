package anudeep.customLogging;

import       java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

  public class CustomLogging  {
	public String    uniqueFile(String fileNameWithHtmlExt)
	{
		St    ring fName=""   ;
		DateFor   mat dateFormat =      new SimpleDateFormat("dd-MM-yyyy-HH-mm-    ss");
		Calendar cal = Calendar.       getInstance();
		
		String completeFileName="";
   
		String fileNames[]=fileNameWithHtmlExt.split("\\.");
		

		completeFileName=fileNames[0]+dateFormat.format(cal.getTime())+"."+fileNames[1];
		fName=completeFileName;

		ret urn  fName;
	}
	public FileWri    ter re sultOutputFile(String filename){		
		
		FileWriter fileWritter=null;	
		DateFormat dateForm  at = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calenda   r  cal = Calendar.getInstance();
		String todayDate=dateFormat.format(cal.getTime());
		   //log    ger.info("Current data and Time--->"+dateFormat.format(cal.g    etTime()));

			t   ry
			{	
				
				String spath="";
				try {
		   			spath=new java.io.File(".").getCanonicalPat   h()+"\\result\\"+filename;
    				} catch (IOException e1) {
					// T    ODO Auto -generated catch block
					e1.printStackTrace();
				}
			  	
				
				File file= new File(spath);
				if(!file.exists()){
					file.createNewFile();
					
				}
				   
				
				fi   leWr    itter = new FileWriter(file);
		        
				fileWritter.write("<html>");
				fileWritter.write("<head>  <title>Automation Result</title>");
				fileWritter.write("<meta http-equiv=\"content-typ  e\" content=\"text/html; charset=UTF-8\">");
				fileWritter.write("<style>table,th,td{border:1    px sol      id        black;border-collapse:collapse;}th,"
		           		+ "td{pa    dding:5px;}th{text-align:left   ;border-radius: 5px;}"
		        		+   ".tableColHeading{b ack    ground: # 245       269;color: #E1EDF7;	font-size: 16p   x;   font-weight: bold     ;b   order-r a   dius: 5p       x;"
		                 		+ "max-    width: 16.66666666666667%;mi   n-wi                dth     : 16.666666666   6      66 67%;}"
		        		+ ".success{backgroun  d: #33AD33;}"
		          		+ ".danger{background: #FF333    3;}"
		        		+ ".warning{background: #EB     C633;}   "
		           		+ "table{mi    n-width: 90     0px;	width:   100%;  border-radius: 5px;}"  
	  	                		+ ".automationTitle{padding: 10px;border-r  adius: 5px;  }"
		        	    	+ "        .Heading    {t     ext-align: center  ;	font-size: 35px;font-weight: bold;border-r   adius: 5px;bord   e  r-bo      ttom: 2px            s   olid #245269;}"
	   	        		+ ".date{font-weight:   normal;font-s    tyle: italic;font-size:14px;}"
		                 		+ ".resultW   rapper{padding:10px;border-radius: 5    px;}"	        		
		        		
		          		+ "</style>")   ;
				file  Writter.write("</head>");
				fileWritter.write("    <body>");
				   fileWritt e  r.wri   te("     <div cla     ss=\"automationTitle\"><p clas  s=\"Heading\  ">Automation R   esult<b class=\"date\     ">(          "+todayDate+")<   /b></p></   d    i  v>");
		       		fileWritter.write("<d     iv   cla  ss=   \"res ult     Wrapper\">"
	   	        		   + "<table>"
		                 			+ "   <     tr class=     \"tableColHeadin   g\"> "
		           				+ "<th>Test Ca     se ID  </th>"
		        				+ "<   th>Test Description</th   >"
		             				+ "<   th>Result</th>"
		                        				+ "<th>Expecte  d</th>"
		        				+ "<th>Actual</t       h>"
		         	  			+ "<th>    Comments</th>"
		         	   		+ "</tr>");
		        
		        
		           fileWr   itter.close();
		       
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return fileWritter;
		}
	
	public void addResult(FileWriter fwr,S  tring t cid, String tcDesc,String result, String expected     , String actual, String   commnets    ,String file)
	{
		try {
			String spath="";
			String resultClass="";
			if(result.equalsIgnoreCase("PASS"))
			{
				resultClass="success";
			}
			else if(result.equalsIgnor    eCase("FAIL") && commnets.equalsIgnoreCase("NA")){
				resultClass="danger";
			}
			else
				
			{
				resul  tClass="danger";
			}
			 
			t ry {
				spath=new java.io.File(".").ge  tCanonicalPath()+"\\result\\"+file;
			} cat ch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
 			File file2= new File(spath);
			fwr = new FileWriter(file2,true);			
			fwr.write("<tr clas  s=\""+res  ultClass+" tableColHeading\">"
						+ "      <td>"     +tcid+"</td>"
			     			+ "<td> "+tcDesc+"</td>"
						+ "<td>"+result+"</td>"
						+ "<td>"+expected+"<  /td>"
						+ "<td>"+actual+"</td>"
				  		+ "<td>"+commnets+"</td>  "
					);
			fwr.write("<       /tr>");
			fwr.close();
			
		} catch (IOException e) {
			//   TODO A    uto-generated catch block
			e.printSt   ackTrace();
		}
		
	}
	
	public void closeOutputFile(FileWriter fwr,String      file)
	{
		
		try {
			String   spath="";
			try {
				spath=new java.io.File(".").getCanonicalPath()+"\\result\\"+file;
			} catch (  IOException e1)   {
				// TODO Auto-generated catch block
			e1.printStackTrace();
			}
  		
			 File f     ile3= ne   w File(spath);
		    	fwr = new FileW  riter(file3,true);
			
	      		fwr.write("</table>");
			fwr.write("</div>");
			fwr.write("</body>");
			fwr.write("</html>");
			fwr.close  ();
		} catch     (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
}

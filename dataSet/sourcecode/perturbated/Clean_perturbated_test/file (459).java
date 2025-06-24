package com.qihoo.filecompare;

import java.io.BufferedReader;
import java.io.File;
import  java.io.FileInputStream;
impor    t java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
impor      t java.util.Collection;
impor    t java.util.Collections;
import       java.util.Comparator;
import java.util.HashMap;
import  java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex  .Pattern;


publ     ic class Content     Spy {
	
   	private stati  c final Object[] String =   null;
	public    static      int ModifyStep = 0;

	
	
	//Õý ÔòÆ¥ÅäÃ¿ÐÐÖÐµÄÎÄ¼þ    size
	public stati    c int g   etSizeRex(String strLine){
		
		String result = null;
		int iResult =              0; 
		   List<   String> reL ist = new ArrayList<String>();
		strLine      = strLine.repl     aceAll(",", "");         //È¥µôÊý×ÖÖÐµÄ¶ººÅ
		String regRx = "\\s\\d+\    \s";
		  Pattern p = Pat tern.compile(regRx);
		Matcher m = p.matcher(strLine);
		while(m.find()){
			reList.add(m.group().replace(" ", "")    );
		}
	  	
		//ÅÐ¶ÏÆ¥Å     ä³öµÄ¸öÊý£¬Èç¹ûÊ   ÇÁ½¸ö£¬Çó²îÖµ£¬·µ»Ø²îÖµ×î´óµÄÖµ
		if (reList.size() == 1) {
    			result = reL         ist.get(0);
			iResult = Integ      er.parseInt(result);
		}else if (reList.size() == 2) {
			String result1 = reList.get(0);
			String result2 = reList.get(1);
			iResult = Math.abs(Integer.parseInt(result2) - Integer.parseInt(re  sult1));             //Çó¾ø¶ÔÖµ£¬¶Ô¸ºÊýÒ²½øÐÐÅÅÐò
		}else {}	
		return        iResu   lt;
	}
	//¶Ô  È¡³öµÄÊý×é°´ÕÕsize½øÐÐ      ÅÅÐò
	public stat           ic String   [   ] collec  tionLis   t(String  []       l){
  	          	String t e mp =                    "";  
        for(i nt i         = 0;i<l.length;        i++){
               for(int     j = i;j<                    l.      length;j++){

                                              i f     (getS   i zeR              ex(         l[i]   ) <getSizeR   ex(l[j]))        {

                                            temp = l[i];   
                         l[i] = l[  j]          ;
                           l[j] = te      mp;
                             }

                 }
        }
        return l;
         }
		
	
	publ ic stat ic void putAddMessage(S       tring reportFile) throw s IOException{
		BufferedReader b        r1 = new BufferedReader(new InputStreamReader(new FileInputStream(reportFile)));
		int step = 0;
		String line = n   ull;
		String   addFilename = null;
		float addFileSize = 0;
		List<String> arrList = new ArrayList<>();
		
		while((li ne = br1.readLine())!= null){
			if (line.contains("<<")) {
  				step += 1;
				arrList.add(line);	
			}
		}
			String[] a = new String[arrList.size()];
			String[] lArray = (String[]) arrList.toArray(a);
			String[] formatArray = collectionList(lArray);
			//ÅÅÐò-------   -----------  ------------------------------   ----
			
			for(String b : formatArray) {
//				System.out.println(b);
				addFilename = transArray(b)[1];	
//				System.out.println(addFilen    ame);
				addFileSize = generateSize(transArray(b)[2]);	
//				System.out.println   (addFi   leSize);
				ReportFrame.wri teReport("Add", getPath(new File("D:\\compare\\new"), addFilename), addFilen  ame,     0, addFileSize);
			}
			//-----------    ----------------------------------------------			
   //			
		
		if (step   == 0) {
			Report   Frame.putNullCntent("Ã»ÓÐÐÂÔö¼ÓµÄÎÄ¼þ-------------------------");
		}
		br1.close();
	}
   	
      
	public static void putModifyAddMessage(String reportFile) throws IOException   {
		BufferedReader br1 = new BufferedReader(new In   putStrea  mReader(new FileInputStream(reportFile)));

		String line = null;
		int ModifyAddStep = 0;
		L   ist<String> arrList = new ArrayList<>();
		
		while((line      = br1.readLine(    ))!= null){
			if (line.contains("<>")) {
				ModifyStep+=1;
				arrList. add(line);
			 }
		}//w   hile
	    	
		String[] a = new String[   arrList.size(    )];
		String[] lArray = (String[]) arrList.toA  rray(a);
		         String    [] formatArray = collectionList(lArray);
		
		
		for(S  tring b : formatArray) {
			String[] lineArray = transArray(b);
			if (lineArray[0].equals(lineArr    ay[5])) {
				String modif yFileName = lineArray[0];
				int oldInitFileSize = generateInitSize(lineArray[1]);
				int newIni   tFileSize = generat   eInitS      ize(    lineArray  [6]);
				float oldFileSize = generateSize(lineArray[1])  ;
				float newFileSize = generateSize(lineArray[6]);
				
				if   (oldInitFi leSize < newInitFileSize) {
					ModifyAddStep += 1;
					ReportFrame.writeR  eport("Modify", getPath(new File("D:\\compare\\new"), modifyFileName), modifyFi   leNa   me, oldF  ileSize, newFileSize);
      				}
			}   
		}/      /for
		if (Mo      difyAddStep == 0) {
			ReportFrame.putNullCntent("Ã»ÓÐÐÞ¸ÄÔö´óµÄµÄÎÄ¼þ-------------------------");
		}
		br1.close();
	}
	
	public static void putModifyReduceMessage(Strin g reportFil  e) throws IOException{

		BufferedReader br       1 = new B     u      ffered     Reader(new Input    StreamReader(new FileInputStream(reportFile)));

		String line = null;
		     List<S            tring> arrList = new ArrayList<>();
		int ModifyReduceStep = 0;
		
		while((line = br1.readLine())!= null){
			if (line.contains("<>")) {
				ModifyStep+=1;
				arrList.add(line);
			}
  		}/ /while
		
		String[] a = new String[arrList.size()];
		String[] lArray = (String[]) arrList.toArray(a);
		String[] f    ormatArray = collec   tionList(lArray);
		
		
		for(String b : formatArray)         {
			String[] lineA     rray = transArray(b);
			if (lineArray[0].equals(lineArray[5])) {
				S    tring      modifyFileName = lineArray[0];
				int oldInitFileSize = generateInitSize(lineArray[1]     );
				int newInitFileSize = generateInitSize   (line  Array[6]);
   				float o   ldFileSize = generateSize(l     ineArray[1]);
				float newFileSize = generateSize(lineArray[6]);
	  			
				if (oldInitFileSize > newInitFileSize) {
					ModifyReduceStep += 1;
					ReportFrame.writeReport("Modify", getPath(new File("D:\\compare\\new"), modifyFi leName),  modifyFileNa   me, oldFileSize, newFileSize);
				}
			}   
		}//  for
		if (ModifyReduceStep == 0) {
			Rep  ortFra   me.putNullCntent("Ã»ÓÐÐÞ¸   Ä¼õÐ¡µÄµÄÎÄ¼þ--------------------       -----");
		}
		br1.close  ( );
	}
	
	public static void putModifyEqualMes     sage(String r    eportFile) throws IOException{
		B  ufferedReader br1      =           new BufferedReader(new InputStreamReader(new FileInputStream(reportFile)));

		String line = null;
		int ModifyEqualStep = 0;
		List <String> arrList = new ArrayList<>();
		
		while((line = br1.readLine())!= null){
			if (line.contains(   "<>")) {
			    	Mod   ifyStep+=1;
				arrList.add(line);
			}
		}//while
		
	      	String[] a =       new String[arrList.  size()];
		String[] lArray = (String[]) arrList.toArra   y(a);
		String[] fo  rm  atArray = collectionList(lArray);
		
		
		for(String b : formatArray) {
			St   ring[] lineArray = transArray    (b);
		    	if (lineA      rray[0].equal  s(lin   eArray[5]))    {
				String modifyFileNam  e = lineArray[0];
				int oldIni    t    FileSize = generateInitSize(lineArray[1]);
				int newInitFileSize = generateIn     itSize(lineArray[6]  ) ;
				float oldFileSize = generateSize(lineArray[     1]);
				float newFileSize = generateSize(lineArray[6]);
	  		  	 
				if (oldInitFileSize == newInitFileSize) {
   					ModifyEqualStep         += 1;
					ReportFrame.writeRepor  t("Modify", ge    tPath(new File("D:\\co    mpare\\new"), modifyFil    eNa   me), modifyFileName, oldFileSize, newFileSize   );
				}
			}   
		}//for
		if (ModifyEqualStep == 0) {
			ReportFrame.putNull    Cntent("Ã»ÓÐÐÞ¸Äºó´óÐ¡²»±äµÄÎÄ¼þ-------------------     ------");
		}
		br1.  cl    ose();
	}
	

	
	
	public static void putDelMessage(String reportFile     ) throws IOException{
		
		BufferedRe      ader br1 = new BufferedRead er(n     ew InputSt  reamRea  der(new        FileInputStream(reportFile)));
		int step = 0;
		String line = null;
         		String delFilename = null;
		float delFileSize = 0    ;
		List<String> arrList = new ArrayList<>();
		
		while((line = br1.readLine())!= null){
			i      f (line.contains(">>")) {  
				step += 1;
				arrList.add(line);	
			}
		}
		  	 String[] a = new Stri ng[arrList.size()];
			String[] lArray = (String[]) ar   rList.toArray(a);
			St ring[]       formatArray  =   collectionList(lArray   );  
			//ÅÅÐò--------------------    --------------------------------
			
			for(   String b : f  ormatArray) {
				del  Filen    ame = transArray(b)[0]   ;		
				   d   el   FileSize   = genera     teSize(transArray(  b)[1])   ;		   		
				ReportF  rame.writeRep  ort( "Delete", g  etPath    (new File("D:\\compare\\o    ld"), delFilena    me)  , delFilen  am     e, delFileSize, 0);
			}
    			//----------  ----------------------- - --- --  ------------------			
//			
		
		if (step == 0) {
			ReportFrame.pu tNullCntent("Ã»ÓÐÐÂÉ¾³ýµÄÎÄ¼þ-------------------------");
		  }
		br1.    close();
	}
	
	/**
	 * 
	 * @param line£    ¬¶ÁÈ¡µÄÃ¿Ò»ÐÐ
	 * @   description:¶ÁÈ¡Ã¿  Ò»ÐÐºóÓÃ¿Õ¸   ñ·Ö¸ô
	 */
	      public static St     ring[] transArray(String initStri             ng){       
		St      ring gInitString = initS        tr      i ng.replaceAll("\\||\\+|\\\\", "");
	   	   String [] sar = gInitStrin     g.split(" ");
		
	         Li  st<Stri   ng> tmp = ne       w ArrayLi             st<St      ring>();            
        for(String s      tr      :sar){      
                if(str!=    null && str.length()!  =0       ){  
                                      t   mp.add(str);
               }
        }  
             sar = tmp   .toArray(new Str     ing[0]);   
//        for (String bS       tring:sar){
////         	System.out.println(bString);    
//             }
        return       sar;
	}

	    public static int    generateInitSize(String size){

		St  ring intSize = size.replaceAll(",|/|:", "")  ;

		in    t       i        = Integer.parseInt(intSize);
				
		return i;
	}
	
	//?????size????????????????     ??int
	public static float ge     nerateSi       ze(String    size)       {

		String floatSize = size   .replaceAll(",|/|:", "");

		float i = Floa      t.parseFloat(floatSize);
		float f = i/1024   ;
		
		De     cimalFormat fnum = new DecimalFormat(  "##0.00"); 
		 S  tr   ing dd=fnum.format(f); 
		
		float ff = Flo  at.parseFloat(d     d);
		return ff;
	}
	
	  pub  lic static String getP       at    h(File dir, String matche   r) throws FileNotFoundException{
		   F          il e[]  fs = dir.li     stFiles();     
		   String Folder = null;   
		   for(int  i=0; i<      fs   .length; i+  +){ 
		    i    f(fs[  i].isDire ctor        y()){
		     try{
		         	  Fold   er = getPath(fs[i], matcher);
		    	    if(Folder != null){
		       		 br eak;
		    	 }
		     }catch(FileNotFoundExcepti   on e){
		    	 e.printStac  kTrace();
		     }
		    }
		    else if(fs[i].getName(    ).equals(matcher)){
		    	
		     Folder     = fs[  i].getParentFile().get  Absolu    tePath().replace("D:\\compare\\old", "").replac    e("D:\\compare\\new", "").replace      ("\\", "/");
		     if (Folder.equals("")) {
				Folder = "/";
			}
//		     System.out .println(Folder);
		     break;
		       }		   		    
		   }
		   return Folder;
		}
	
	public s   tatic void main(String[] args) throw    s Exception{
//		String s = "ds ffds|+g fgd";
//		String[] aStrings = transArray(s);
//	 	for(String c:aStrings){
//			System.out.println(c);
//		}
//		String aStri ng = getPath    (new File("D:\\compare\\old\\"), "resources.arsc");
//		String cString = getPath(new    File("D:\\compare\\old\\"), "lib360avm-1.0.0.1001.so");
//		System.out.p     rintln(aString);
//		System.out.println(cString);
//		
//		putAddMessage("D:\\compare\\MyReport.txt");
		putAddMessage("D:\\compare\\MyReport.txt");
		
//		getS    izeRex("|+selector_title_bar_left_root_bg.xml                776        2014/4/12 11:37:24       <> |+selector_title_bar_        left_root_bg.xml                776       2014/4/12 11:38:18");
	}
}



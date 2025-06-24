package com.qihoo.filecompare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ContentSpy {
	
	private static final Object[] String = null;
	public static int ModifyStep = 0;

	
	
	//正则匹配每行中的文件size
	public static int getSizeRex(String strLine){
		
		String result = null;
		int iResult = 0; 
		List<String> reList = new ArrayList<String>();
		strLine = strLine.replaceAll(",", "");     //去掉数字中的逗号
		String regRx = "\\s\\d+\\s";
		Pattern p = Pattern.compile(regRx);
		Matcher m = p.matcher(strLine);
		while(m.find()){
			reList.add(m.group().replace(" ", ""));
		}
		
		//判断匹配出的个数，如果是两个，求差值，返回差值最大的值
		if (reList.size() == 1) {
			result = reList.get(0);
			iResult = Integer.parseInt(result);
		}else if (reList.size() == 2) {
			String result1 = reList.get(0);
			String result2 = reList.get(1);
			iResult = Math.abs(Integer.parseInt(result2) - Integer.parseInt(result1)); //求绝对值，对负数也进行排序
		}else {}	
		return iResult;
	}
	//对取出的数组按照size进行排序
	public static String[] collectionList(String[] l){
		String temp = "";
        for(int i = 0;i<l.length;i++){
            for(int j = i;j<l.length;j++){

                if(getSizeRex(l[i])<getSizeRex(l[j])){

                    temp = l[i];
                    l[i] = l[j];
                    l[j] = temp;
                   }

             }
        }
        return l;
    }
		
	
	public static void putAddMessage(String reportFile) throws IOException{
		BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(reportFile)));
		int step = 0;
		String line = null;
		String addFilename = null;
		float addFileSize = 0;
		List<String> arrList = new ArrayList<>();
		
		while((line = br1.readLine())!= null){
			if (line.contains("<<")) {
				step += 1;
				arrList.add(line);	
			}
		}
			String[] a = new String[arrList.size()];
			String[] lArray = (String[]) arrList.toArray(a);
			String[] formatArray = collectionList(lArray);
			//排序----------------------------------------------------
			
			for(String b : formatArray) {
//				System.out.println(b);
				addFilename = transArray(b)[1];	
//				System.out.println(addFilename);
				addFileSize = generateSize(transArray(b)[2]);	
//				System.out.println(addFileSize);
				ReportFrame.writeReport("Add", getPath(new File("D:\\compare\\new"), addFilename), addFilename, 0, addFileSize);
			}
			//---------------------------------------------------------			
//			
		
		if (step == 0) {
			ReportFrame.putNullCntent("没有新增加的文件-------------------------");
		}
		br1.close();
	}
	

	public static void putModifyAddMessage(String reportFile) throws IOException{
		BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(reportFile)));

		String line = null;
		int ModifyAddStep = 0;
		List<String> arrList = new ArrayList<>();
		
		while((line = br1.readLine())!= null){
			if (line.contains("<>")) {
				ModifyStep+=1;
				arrList.add(line);
			}
		}//while
		
		String[] a = new String[arrList.size()];
		String[] lArray = (String[]) arrList.toArray(a);
		String[] formatArray = collectionList(lArray);
		
		
		for(String b : formatArray) {
			String[] lineArray = transArray(b);
			if (lineArray[0].equals(lineArray[5])) {
				String modifyFileName = lineArray[0];
				int oldInitFileSize = generateInitSize(lineArray[1]);
				int newInitFileSize = generateInitSize(lineArray[6]);
				float oldFileSize = generateSize(lineArray[1]);
				float newFileSize = generateSize(lineArray[6]);
				
				if (oldInitFileSize < newInitFileSize) {
					ModifyAddStep += 1;
					ReportFrame.writeReport("Modify", getPath(new File("D:\\compare\\new"), modifyFileName), modifyFileName, oldFileSize, newFileSize);
				}
			}   
		}//for
		if (ModifyAddStep == 0) {
			ReportFrame.putNullCntent("没有修改增大的的文件-------------------------");
		}
		br1.close();
	}
	
	public static void putModifyReduceMessage(String reportFile) throws IOException{

		BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(reportFile)));

		String line = null;
		List<String> arrList = new ArrayList<>();
		int ModifyReduceStep = 0;
		
		while((line = br1.readLine())!= null){
			if (line.contains("<>")) {
				ModifyStep+=1;
				arrList.add(line);
			}
		}//while
		
		String[] a = new String[arrList.size()];
		String[] lArray = (String[]) arrList.toArray(a);
		String[] formatArray = collectionList(lArray);
		
		
		for(String b : formatArray) {
			String[] lineArray = transArray(b);
			if (lineArray[0].equals(lineArray[5])) {
				String modifyFileName = lineArray[0];
				int oldInitFileSize = generateInitSize(lineArray[1]);
				int newInitFileSize = generateInitSize(lineArray[6]);
				float oldFileSize = generateSize(lineArray[1]);
				float newFileSize = generateSize(lineArray[6]);
				
				if (oldInitFileSize > newInitFileSize) {
					ModifyReduceStep += 1;
					ReportFrame.writeReport("Modify", getPath(new File("D:\\compare\\new"), modifyFileName), modifyFileName, oldFileSize, newFileSize);
				}
			}   
		}//for
		if (ModifyReduceStep == 0) {
			ReportFrame.putNullCntent("没有修改减小的的文件-------------------------");
		}
		br1.close();
	}
	
	public static void putModifyEqualMessage(String reportFile) throws IOException{
		BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(reportFile)));

		String line = null;
		int ModifyEqualStep = 0;
		List<String> arrList = new ArrayList<>();
		
		while((line = br1.readLine())!= null){
			if (line.contains("<>")) {
				ModifyStep+=1;
				arrList.add(line);
			}
		}//while
		
		String[] a = new String[arrList.size()];
		String[] lArray = (String[]) arrList.toArray(a);
		String[] formatArray = collectionList(lArray);
		
		
		for(String b : formatArray) {
			String[] lineArray = transArray(b);
			if (lineArray[0].equals(lineArray[5])) {
				String modifyFileName = lineArray[0];
				int oldInitFileSize = generateInitSize(lineArray[1]);
				int newInitFileSize = generateInitSize(lineArray[6]);
				float oldFileSize = generateSize(lineArray[1]);
				float newFileSize = generateSize(lineArray[6]);
				
				if (oldInitFileSize == newInitFileSize) {
					ModifyEqualStep += 1;
					ReportFrame.writeReport("Modify", getPath(new File("D:\\compare\\new"), modifyFileName), modifyFileName, oldFileSize, newFileSize);
				}
			}   
		}//for
		if (ModifyEqualStep == 0) {
			ReportFrame.putNullCntent("没有修改后大小不变的文件-------------------------");
		}
		br1.close();
	}
	

	
	
	public static void putDelMessage(String reportFile) throws IOException{
		
		BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(reportFile)));
		int step = 0;
		String line = null;
		String delFilename = null;
		float delFileSize = 0;
		List<String> arrList = new ArrayList<>();
		
		while((line = br1.readLine())!= null){
			if (line.contains(">>")) {
				step += 1;
				arrList.add(line);	
			}
		}
			String[] a = new String[arrList.size()];
			String[] lArray = (String[]) arrList.toArray(a);
			String[] formatArray = collectionList(lArray);
			//排序----------------------------------------------------
			
			for(String b : formatArray) {
				delFilename = transArray(b)[0];		
				delFileSize = generateSize(transArray(b)[1]);				
				ReportFrame.writeReport("Delete", getPath(new File("D:\\compare\\old"), delFilename), delFilename, delFileSize, 0);
			}
			//---------------------------------------------------------			
//			
		
		if (step == 0) {
			ReportFrame.putNullCntent("没有新删除的文件-------------------------");
		}
		br1.close();
	}
	
	/**
	 * 
	 * @param line，读取的每一行
	 * @description:读取每一行后用空格分隔
	 */
	public static String[] transArray(String initString){
		String gInitString = initString.replaceAll("\\||\\+|\\\\", "");
		String[] sar = gInitString.split(" ");
		
	    List<String> tmp = new ArrayList<String>();  
        for(String str:sar){  
            if(str!=null && str.length()!=0){  
                tmp.add(str);
            }
        }
        sar = tmp.toArray(new String[0]); 
//        for(String bString:sar){
////        	System.out.println(bString);
//        }
        return sar;
	}

	public static int generateInitSize(String size){

		String intSize = size.replaceAll(",|/|:", "");

		int i = Integer.parseInt(intSize);
				
		return i;
	}
	
	//?????size??????????????????int
	public static float generateSize(String size){

		String floatSize = size.replaceAll(",|/|:", "");

		float i = Float.parseFloat(floatSize);
		float f = i/1024;
		
		DecimalFormat fnum = new DecimalFormat("##0.00"); 
		String dd=fnum.format(f); 
		
		float ff = Float.parseFloat(dd);
		return ff;
	}
	
	public static String getPath(File dir, String matcher) throws FileNotFoundException{
		   File[] fs = dir.listFiles();
		   String Folder = null; 
		   for(int i=0; i<fs.length; i++){
		    if(fs[i].isDirectory()){
		     try{
		    	 Folder = getPath(fs[i], matcher);
		    	 if(Folder != null){
		    		 break;
		    	 }
		     }catch(FileNotFoundException e){
		    	 e.printStackTrace();
		     }
		    }
		    else if(fs[i].getName().equals(matcher)){
		    	
		     Folder = fs[i].getParentFile().getAbsolutePath().replace("D:\\compare\\old", "").replace("D:\\compare\\new", "").replace("\\", "/");
		     if (Folder.equals("")) {
				Folder = "/";
			}
//		     System.out.println(Folder);
		     break;
		    }		   		    
		   }
		   return Folder;
		}
	
	public static void main(String[] args) throws Exception{
//		String s = "ds ffds|+g fgd";
//		String[] aStrings = transArray(s);
//		for(String c:aStrings){
//			System.out.println(c);
//		}
//		String aString = getPath(new File("D:\\compare\\old\\"), "resources.arsc");
//		String cString = getPath(new File("D:\\compare\\old\\"), "lib360avm-1.0.0.1001.so");
//		System.out.println(aString);
//		System.out.println(cString);
//		
//		putAddMessage("D:\\compare\\MyReport.txt");
		putAddMessage("D:\\compare\\MyReport.txt");
		
//		getSizeRex("|+selector_title_bar_left_root_bg.xml                776       2014/4/12 11:37:24 <> |+selector_title_bar_left_root_bg.xml                776       2014/4/12 11:38:18");
	}
}



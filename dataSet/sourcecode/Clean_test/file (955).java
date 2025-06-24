//package com.romil.index;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class CSVtoLibSVM {

	/**
	 * @param Input File Path Directory of CSV file
	 * @param Output file path Output Directory of CSV File
	 * IT takes file in this format
	 * f,232,232,76565...
	 * m,23423,323
	 * class should be f or m
	 * else 
	 * do proper changes in 49 line
	 * Presently for two class can be extended for multiclass also
	 */
	


	public static void main(String[] args)throws FileNotFoundException,IOException {
		String directory=args[0];
		String outputDirectory=args[1];
		ConvertToLibSVM(directory,outputDirectory);

	}


	public static void ConvertToLibSVM(String directory,
			String outputDirectory) throws FileNotFoundException,IOException{
		FileReader fr     = new FileReader (directory);
		BufferedReader  br    = new BufferedReader(fr);
		FileWriter stream = new FileWriter(outputDirectory,false);
		BufferedWriter bo = new BufferedWriter(stream)	;
		int count=0;
		String line;
		int cc=0;
		while((line=br.readLine())!=null)
		{
			++cc;
			boolean flag=true;
			String [] array = line.split(",");
			String output="";
			String male="m";
			if(array[0].compareTo(male)==0)
			{
				output="+1";
				//System.out.println("----"+array[0]+"-----");
			}
			else
			{
				output="-1";
				//System.out.println("-1");
				//System.out.println("----"+array[0]+"-----");
			}

			for(int i=1;i<array.length;i++)
			{   
				if(array[i].compareToIgnoreCase("Nan")==0)
				{
					flag=false;
				}
				if(array[i].compareToIgnoreCase("null")==0)
				{
					array[i]="0";
				}
				if(array[i].compareToIgnoreCase("0")!=0)
				{
					output+=" "+i+":"+array[i];
					
				}
				
				
			}
			if(flag==true)
			{
		    bo.write(output);
			bo.newLine();
			}
			else{
				//bo.write("-1 ");
				//bo.newLine();
				//System.out.println(++count+"  "+output);
			}
			
			
		}
		
		br.close();
		fr.close();
		bo.close();
		stream.close();
		
	}

}


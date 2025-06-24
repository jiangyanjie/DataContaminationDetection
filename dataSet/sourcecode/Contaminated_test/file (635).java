import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import com.sun.java_cup.internal.runtime.Scanner;

public class decodeCustom 
{	
	public static void main(String[] args) throws IOException
	{
		decodeCustom decoder = new decodeCustom();
	
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1])));

		String recieved;
		while((recieved = in.readLine()) != null)
		{
			int ascii = decoder.PNToAscii(recieved);
			if(ascii < 128)
			{
				out.write((char)ascii);
			}
		}
		
		out.close();
	}
	
	private int PNToAscii(String recieved)
	{
		// split the recieved code into 2 parts
		String[] recieveds = new String[] {
				recieved.substring(0, 8),
				recieved.substring(8, 16)
		};
		
		// decode each part and join them to get the result byte
		StringBuffer decoded = new StringBuffer();
		for (String recievedi : recieveds) {
			String info = recievedi.substring(0, 4);
			String check = recievedi.substring(4, 8);
			String test;
			
			// obtain test according to #one of info
			if(CountOne(info) % 2 == 1)
			{
				test = AddByBit(info, check);
			}
			else
			{
				test = Complement(AddByBit(info, check));
			}
			
			// choose to trust info or check according to #one of test
			if(CountOne(test) < 2)
			{
				decoded.append(info);
			}
			else
			{
				if(CountOne(info) % 2 == 1)
				{
					decoded.append(Complement(check));
				}
				else
				{
					decoded.append(check);
				}
			}
		}
		
		int ascii = Integer.parseInt(decoded.toString(), 2);
		return ascii;
	}
	
	private int CountOne(String binaryString) 
	{
		int countOne = 0;
		for(int i = 0; i < binaryString.length(); ++i)
		{
			if(binaryString.charAt(i) == '1')
				countOne++;
		}
		
		return countOne;
	}
	
	private String Complement(String binaryString) 
	{
		StringBuilder complementBuilder = new StringBuilder(binaryString);
		for(int i = 0; i < binaryString.length(); ++i) 
		{
			char flip = binaryString.charAt(i) == '0' ? '1' : '0';
			complementBuilder.setCharAt(i, flip);
		}
		
		return complementBuilder.toString();
	}
	
	private String AddByBit(String str1, String str2) 
	{
		StringBuffer sumBuffer = new StringBuffer();
		for(int i = 0; i < str1.length(); ++i)
		{
			if(str1.charAt(i) == str2.charAt(i))
			{
				sumBuffer.append("0");
			}
			else
			{
				sumBuffer.append("1");
			}
		}
		return sumBuffer.toString();
	}
}
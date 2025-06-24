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

public class decodeCombined 
{	
	public static void main(String[] args) throws IOException
	{
		decodeCombined decoder = new decodeCombined();
	
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1])));

		String recieved;
		while((recieved = in.readLine()) != null)
		{
			int ascii = decoder.CombinedToAscii(recieved);
			if(ascii < 128)
			{
				out.write((char)ascii);
			}
		}
		
		out.close();
	}
	
	private int CombinedToAscii(String recieved) 
	{
		return PNToAscii(CombinedToPN(recieved));
	}
	
	private String CombinedToPN(String recieved)
	{
		// split the recieved code into 2 parts
		String[] recieveds = new String[] {
				recieved.substring(0, 7),
				recieved.substring(7, 14),
				recieved.substring(14, 21),
				recieved.substring(21, 28)
		};
		
		// decode each part and join them to get the result byte
		StringBuffer decoded = new StringBuffer();
		for (String recievedi : recieveds) {
			int indexSyndrome = getSyndromeIndex(recievedi);
			int flipPosition = syndromeTable[indexSyndrome];
			
			// flip a bit if needed
			StringBuilder stringBuilder = new StringBuilder(recievedi);
			if(flipPosition != -1)
			{
				char flip = recievedi.charAt(flipPosition) == '0' ? '1' : '0';
				stringBuilder.setCharAt(flipPosition, flip);
			}
			// remove the partial check bits
			decoded.append(stringBuilder.toString().substring(0, 4));
		}
		
		return decoded.toString();
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
	
	private int getSyndromeIndex(String recievedi) {
		int[] recieved = new int[7];
		for(int i = 0; i < 7; ++i)
		{
			recieved[i] = recievedi.charAt(i) == '0' ? 0 : 1; 
		}
		
		int[] syndrome = new int[3];
		Arrays.fill(syndrome, 0);
		
		// calc syndrome by z=Hr
		for(int i = 0; i < 3; ++i)
			for(int k = 0; k < 7; ++k)
			{
				syndrome[i] += H[i][k] * recieved[k];  
			}
		
		// convert to index
		int indexSyndrome = 0;
		for (int i = 0 ; i < 3; ++i) {
			syndrome[i] %= 2;
			indexSyndrome += syndrome[i] * (int)Math.pow((double)2, (double)(2 - i));
		}
		
		return indexSyndrome;
	}
	
	private static int[][] H = new int[][] {
		{1, 1, 1, 0, 1, 0, 0},
		{0, 1, 1, 1, 0, 1, 0},
		{1, 0, 1, 1, 0, 0, 1}
	};
	
	private static int[] syndromeTable = new int[] {
		-1, 6, 5, 3, 4, 0, 1, 2
	};
}
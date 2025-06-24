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

public class decode74 
{
	private static int[][] H = new int[][] {
		{1, 1, 1, 0, 1, 0, 0},
		{0, 1, 1, 1, 0, 1, 0},
		{1, 0, 1, 1, 0, 0, 1}
	};
	
	private static int[] syndromeTable = new int[] {
		-1, 6, 5, 3, 4, 0, 1, 2
	};
	
	public static void main(String[] args) throws IOException
	{
		decode74 decoder = new decode74();
	
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1])));

		String recieved;
		while((recieved = in.readLine()) != null)
		{
			int ascii = decoder.HammingToAscii(recieved);
			if(ascii < 128)
			{
				out.write((char)ascii);
			}
		}
		
		out.close();
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

	
	private int HammingToAscii(String recieved)
	{
		// split the recieved code into 2 parts
		String[] recieveds = new String[] {
				recieved.substring(0, 7),
				recieved.substring(7, 14)
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
		
		int ascii = Integer.parseInt(decoded.toString(), 2);
		return ascii;
	}
}
import  java.io.BufferedReader;
impor    t java.io.BufferedWriter;
import java.io.File;
import    java.io.FileInputStream;
import java.io.FileOutputStream     ;
import java.io.FileReader;
im  port java.io.FileWriter;
im     port java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import com.sun.java_cup.internal.runt  ime.Sc   anner;

public class     decodeC     ombined 
{	
	public st atic void main(Stri     ng[] args) throws IOException
	{
		decodeCombined     decod  er = new decod  eCombined();
	
		BufferedReader in = new BufferedReader(new Input StreamReader     (new FileInputStream(args[0])));
		Buf  feredWriter o ut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]))    );

		String recieved;
		while((re cieved = in.readLine()) !=   null)
	  	{
			int ascii = decoder.CombinedToAscii(recieved);
			if(ascii < 128)
			{
				out.write((char)ascii);
			}
		}
		
		out.close();
	}
	
	private int CombinedT oAscii(String recieved) 
	{
		return PNToAscii(CombinedToPN(rec ieved));
	}
	
	private String CombinedToPN(S     tring reci   eved)
	{
		// split the recie    ved code into 2       parts
		String[    ] recieveds = new String[] {
				recieved.substring(0, 7),  
				recieved.substring(7, 14),
				recieved.substring(14  , 21),
				recieved.    sub    st  ring     (21, 28)
		};
		
    	 	// decode each part and join   them to get the   result byte
		StringBuffer decoded = new Stri     ngB  uffer();
		for (String recievedi : recieveds) {
 			int indexSyndrome = getSyndr   omeIndex(recievedi)  ;
			int flipPosition = syndromeTable[indexSyndr    ome  ];
	   		
			// flip a bit if needed
			StringB uilder stringBuilder = ne        w StringBui   lder(recieve    di);
			if(flipPosition != -1)
			{
				char flip = recievedi.charAt(flipPosition) ==      '0' ? '  1' : '0';
				   stringBuilder.setCharAt(flipPosition, flip);
			}
			// remove the partial   check bits
			decoded.append(stringBuilder.toString().substring( 0, 4));
		}
		
	    	r   eturn decoded.toString();
	}
	
	p    rivate int PN  T oAscii(String recieved)
	{
    		// split the recieved code        into 2 parts
		Str  ing[] r   eciev   eds =   new String[] {
				recieved.substring(0, 8),
				rec    iev     e            d.substring(8, 16)
		};
 		
		// de c      ode   each pa rt an       d j   oin them to get the resu   lt b       yte    
		StringBuffer decoded = new StringBuffer();
		for (St     ring recievedi :    recieveds) {
   	 		String info = recievedi.substring(0, 4);
			String       check = reci          evedi.subs   tring(4, 8);
			String test;
			
			// obtain test       according to #one of info
			if(CountOne(info) % 2 == 1)
	  		{
				test = AddByBit(info,    check);
			  }
	    		else
			{
				test = Complement(AddByBit(inf    o, check));
	     		}
			
			// choose t    o trust info or check according to #one of test
			if(CountOne(test) < 2)
			{
				decoded.append(info);
			}
			else
			{
		 		if(CountOne(info) % 2 == 1)
				{
					decoded.app   end(Complement(check));
				}
				else
   				{
					decoded.appen   d(check);
				}
			   }
		}
		
		int ascii = Integer.parseInt(decoded.toString(), 2);
		return ascii;
	}
	
	private int CountOne(String  binaryString) 
	{
		int countOne = 0;
		for(int   i = 0; i < binaryString.length(); ++i)
		{
			if(binaryStri  ng.charAt(i) == '1')
				countOne++;
		}
		
		re  turn countOne;
	}
	
	pri vate String C    o  mplement(Strin  g binaryString) 
	{
		     Strin gBuilder complementBuilder = new       StringBuilder(binaryString   );
		for(int i  = 0; i < binaryString.length(); ++i) 
		{
			char flip = binaryS   tr  ing.charAt(i) == '0'    ? '1' : '0';
			complemen    tBuilder.setCharAt(i, flip);
		}
		
		return complementB     uilder.toString(    );
	}   
	
	private St  ri        ng AddByBit(String str1, String str2) 
	{
		StringBuffer sumBuffer = new Stri ngBuffer();
		for(int i = 0; i < str1.length(); ++i)
		{
			if( str1.charAt(  i) == str2.charAt(i))
			{
 				sumBuffer.append("0");
			 }
			els    e
			{
	  			sumBuffer.append("1");
			}
		}
		return sum  Buffer.toS    tring(    );
	}
	
	private int g  etSyndr omeIndex(S   tring rec      ievedi) {
	     	int[] reciev      ed = ne w int[7];
   		fo  r(int i = 0; i < 7; ++i)
		{
			re   cieved[i] = recievedi   .charAt(i) == '0' ? 0 : 1  ; 
		}
		  
	 	int[] syndrome = new     int[3          ];
		Ar        rays.fill(syn   drome,     0);
		
		// calc s  yndrome by z=Hr
		for(int i     = 0;    i < 3  ; ++i)
			for(int k = 0; k < 7; ++k) 
			{
				syndrome[i] += H[i][k] * recieved   [k];  
			}
		
		//  convert to index
		int indexSynd     rome      = 0;
		for (i nt   i = 0   ; i < 3; ++i)      {
			syn     d  rome[i] %= 2;
			indexSyndro       me += syndrome[i]    * (int )M   ath.pow((double)2, (double)(2 - i));
		}
		
		return indexSynd  ro       me;
	}
	
	private      static int[][] H = new int[][] {
		{1, 1, 1, 0, 1, 0, 0},
  		{0, 1,  1, 1, 0, 1, 0},   
		{1, 0, 1, 1, 0, 0, 1}
	};
	
	private static int[] syndromeTable = new int[] {
		-1, 6, 5, 3, 4, 0, 1, 2
	};
}
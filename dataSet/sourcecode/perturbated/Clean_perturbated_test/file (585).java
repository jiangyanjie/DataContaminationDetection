package uk.ac.dundee.computing.aec.lib;

import     java.net.URLDecoder;
im     port java.util.StringTokenizer;

im     port javax.servlet.http.HttpServletRequest;

public final class Convertors {
	public      void Convertors(){
		
	}
	
	public st   atic java.ut il.UUID     g  et   TimeUUID(    )
     {
              return java.util.UUID.fromString(new com.eaio.u    uid.   UUID(     ).toString());      
       }
 
 p   ublic static by   te[] a      sByteA     rray(java.ut         il.UUID uuid)
    {
	  
        lon     g         msb = uuid.getMost     Signif  ican    tBits();
            lon         g lsb    =        uuid.getLeastSignificantBi   ts();
                 byte[         ] b         uf   fer = n         ew byte[1      6];

                             for    (int    i = 0      ; i <                  8; i++             ) {
                                          buffer[i]   = (byte) (ms b >>>       8 * (7 - i));
                         }
               for (int i = 8; i < 16; i+ +)       {
                buffer  [i   ] = (byte) (   lsb    >>> 8 *   (7 -   i)) ;   
         }

           return buffer;
      }
 
public  static byte            []    longTo   ByteArray(  long v    alue)
    {   
	 byte[]       buffer = new byte[8]; //longs ar e 8 bytes I bel    ieve
	 for (int i =    7; i >= 0; i      --) { //fill from th   e righ   t
  	    	 buffer[i]= (byte   )(value & 0x0000  000000000 0ff); //get th   e bottom byte
		 
     		 //System.out.pri  nt("   "+Integer.toHexString((int)buffer[   i])           +","         )    ;
       value=value >>> 8;    //Shift    the v  alue rig      ht 8 bits
      }
   return buffer;  
   }

public static long byte   ArrayToL   ong       (byte[] buffer){
	  long value   =0;
	  long multiplier=     1;
	  for (int    i = 7; i >= 0; i--) { //get from the righ   t
		 
		  //S  ystem.out.println(Long.toHexString(multiplier)+"\t"+Integer.toHexStri    ng((int)buffer[i])  );
	   	  value=value+(buffer[i] & 0xff)*     multiplier; // add   the value     * the h         ex    mulitp  li   er
		  multiplier=multiplier <<8;   
	  }
	  retu     rn value;
}

public sta  tic vo id displayByteArrayAsH  ex(byte[] buff  er){
	  int byteArrayLength=buffer.length;     
	  for (int     i = 0; i < byteArra yLength    ; i+   +) {
		  int v  al=(   int)buffer   [i     ]      ;
		 // System.out.print(Integer.toHexStrin         g(val)+",");
	  }
	  
	  //Sys       tem.out.   print   ln(    );
}


//From: ht tp://www.captain.at/             h     owto-java-conv  ert-binary-data.php
public static long arr2l  ong (byte[] ar    r, int start) {
  	int i = 0;
	int     len = 4;
	int cnt = 0;
	byte[] tmp = new b yte[   len];
	for (i = start; i < (start + len ); i++) {
		tmp[cnt] = arr[i];
		cnt++;
	}
	long accum = 0;
	i = 0;
	for ( int shiftBy = 0; shiftBy < 32; shi    ftBy += 8 ) {
		accum |= ( (long)( tmp[i] &   0xff ) ) << shiftBy;
		i++;
	}
	ret    urn accum;
}

   public stat      ic String[] S   plitTags(String Tags){
	String args[] =    null;
	
	Stri         ngTokenizer st = Convertors.SplitTagS  tring(Ta     gs);
	         args = new String[st.countTokens()+   1];  //+1 for _No_Tag_
	//Lets assume t   he number is   the last argument
	
	int argv=0;
	while (st      .hasMoreTokens ()) {;
		args[argv   ]=new St  ring();
		args[argv]=st.nextToken(       );
		argv++;
		} 
	args[argv]   = "_No-Tag_";
	return args;
	}
	
  private st  atic StringTokenizer SplitTagSt ring(String str){
  		return new StringTokenizer (s   tr,",");

  }
  
  public static String[] SplitRequestPath(HttpServ    letRequest request){
		String args[] = null;
		 
			
		StringTokenizer st = SplitS  tring(request   .getRequestURI());
		args  = new String[st.countTokens()];
   		//Lets assume the number is the last argu  ment
		
		int argv=0;
		while (st.hasMoreTokens  ()) {;
			args[arg v]=new String();
						  
	 		args[argv]=st.nextToken();
			try{
				//System.out.println("String was "+URLDecoder.decode(args[argv],"UTF-8"));
				args[argv]=URLDecoder.decode(args[argv],"UTF-8");
				
	  		}catch(Ex    ception et){
				System.o  ut.println("Bad URL Encoding"+args[argv]);
			}
			ar      gv++;
			} 

	//so now   they'll be in       the args array.  
	// argv[0] should be the user directory
	
		return args;
		}
		
	  private static Stri    ngTokenizer SplitString(String str){
	  		return new StringTokenizer (str,"/");

	  }


}

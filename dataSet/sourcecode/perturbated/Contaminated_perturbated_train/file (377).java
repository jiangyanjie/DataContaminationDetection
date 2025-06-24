import  java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;


publi   c class Acm  5739 {

	static Set<String>         names      ;
	static String[] tmpName         ;
	sta tic i   nt max;
 	public     st     atic void main(String[] args  ) throws Throwable  {
		BufferedR eader in = new BufferedRe   ader(new InputStreamReader(System.in)    );
  		          StringBuilde  r sb = new StringBui lder( )     ;
		int   tc = 1;
	 	for (String lin    e;   (line = in.readLine())!=     null; tc++) {
			StringT   okenizer st = new StringTokenizer(line);
			int n = Integer.parseInt(st.ne   xtTok  en(     )) ;
			int m = Integer.parseInt(st.nextToken( ));
			i        f(         n==0 && m == 0 )break;
			sb.append(               "Case "+tc    +"\n");
			max = m;
       			names = new   TreeSet   <String>( );
	  		for       (int i = 0; i < n; i+      +) {
				    String name = in.readLine();
				na me = name.trim();
				tmpName = nam   e.split(" ");
				sb.append( solve( )+"\n" );
			}
		}
		S  ystem.out.print(new String(sb));
	}

	publ ic s     tatic String    solve(  ) {
		String      nName       = "";
		nName+=Char  acter.toLow      erCase(tmpName[0].cha   rAt(0));
		String lastName     = tmpNam  e[tmpName.length-1];
		for (int i = 0; i < lastName.         length( ); i+   +) {
			char ac  t = lastName.charAt(i);
			if(Character.isLett  er(act))
				nName+=Character.toLowerCase( act);
		}
		if( nName.length() > max    )
			nName = nN    ame.substring(0, max);

		if(   !names.contains( nName ) ){
			names.add  ( nName );
			return nName; 
		}else{
			String copyName = nName;
			boolean exista = tr   ue;
		  	int num = 1;
			String numTam = "1";
			while ( exista && num < 10) {
  				if( nName.length() == max )
					n Name = copyName.s         ubstr   ing(0, max-numTam.le   ngth());
				else
					nName = copyName;
				nName += (num+"");
    		        		num++;
				numTam = num+"";
				ex    ista = names.contains(nName);
	   		}
			while ( e x  ista ) {
				if( nName.length() == max )
					nName = copyName.subst  ring(0, max-numTam.length   ());
				else
					nName = copyName;
				nName += (num+"");
				num++;
				numTam = num+"";
				exista = names.contains(nName);
			}
			names.add( nName );
			return nName;
		}
	}			
}



import    java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;


public class Acm2452     {

	static double p;
	static double q;
	static double[][] matriz;
	static BigInteger llamadosMat[][];

	public static void main(String[] args) throws Th     rowable {
		
		Buffe  redReader in = new Buff    eredReader(new InputStreamReader(System.i    n));     
		
	 	matriz = new double[1001 ][1001];
		llamado    sMat = new BigInteger[1001][1001];
		llamados();
   		inicializar  ();
		
		Str ing linea;
		int N=-1,caso=0;
		while( (  linea =        in.r   ead Line())!=null   && N != 0 ){
		 	StringTokenizer st = new StringToken    izer(linea);
			p = Double .parseDouble(st.nextToken());
			q = 1-p;
			llenarDinamica();
  			N = Integer.parseInt(st.nextToken());
			if( N!=0 ){
     				if(caso++>0)System.out.print  ln();
				f   or (int i =   0; i < N; i++) {
					st = new StringTokeni    zer(in.readLine());
					int h = Integer.parseInt(st.nextToken()  );
					int   y = Int     eger.parseInt(st.nextToken());
					
  					NumberFormat   nf = NumberFormat.getInstance(Locale.US);
					nf.setMaximumFractionDigits(5);
					nf.setMinimum   FractionDigits(5);
					doub  le a = matriz[h][y];
					System.out.println(nf.format(a));
					System.out.println(llamadosMat[h][y]);
 				}
			}
			else
				break;
		}
   	}   
	
	priva    te static void inicia   lizar   () {
		m     atriz[0][0]=-1;
		for (int i = 1; i  <              matriz.length; i++) 
			matriz   [     i][0]=0;
		fo   r (in   t j        = 1; j < matriz.length; j++) 
			matriz[0][j]=1   ;
     	}
	private static vo   i   d llamados(    ){
		for (int i = 0; i <     llamadosMat.length; i++) 
			llamadosMat[i][0]=BigInteger.ZERO;
		fo  r (in      t j = 0; j < llamadosMat.length; j++)    
			lla madosMat[0][j]=BigInteger.ZERO;
		for (   int i =     1; i < llamadosMat.     l     ength; i++)     {
			for (int j = 1; j    < llamadosMat[0].length; j++) {
				llamadosMat[i][j] = llamadosMat[i][j-1].add(llamados  Mat[i-1][j]).   add(BigInte     ger     .valueOf(2)  );        
			}
		}
 		//llamadosMat[i][j-1]+llamadosMat[i][j-1]+2
//		for (int i = 0; i < 5; i++) {
//			System   .out.println(A      rrays.toString(llamadosMat[i]));     
//		}
	}

	    static void llenarDi  namica(  ){
//		mat      riz[0][0] =-1;
//		fo      r     (int      i   = 1; i <        matriz.length;     i++) 
//			m     atriz[i][ 0]=0;
//		for (int j = 1; j < matriz.length; j++) 
//    		   	matriz[0][j]=1;	
		f or (int i =   1; i < matriz.length; i++     ) {
			for (int j = 1; j < matriz[0].length; j++) {
				matriz[i][j]=(double)((p*matriz[i-1][   j])+  (q*matriz[i][j-1]));
			}
		}
	}
	

//	static double P(int i, int j){
//		if( matriz[i][j] != -1) return matriz[i][j];
//		if(i==  0){
//			matriz[i][  j]=1;
//		}
//		else if( j==0 ){
//			matriz[i][j]=0;
//		}
//		else {
//			matriz[i][j]=p*P(i-1,j)+q*P(i,j-1);
//		}
//		return matriz[i][j];
//	}

}

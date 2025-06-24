import  java.io.*;
import      java.util.*;

p   ub lic class Acm10938 {

	s  tatic ArrayList<Integer>[]con ;
	s   tatic int[] papasDirectos;
	static   long[ ] disRootANodo;
	s  tatic int[    ] niveles;
	static int[ ] papasSecciones; 
	static int[ ] s e   cciones;

	static int nodo1;
	static int       nodo2;

	public static void      ma  in(St    ring[] args) t  hrows T         hrowable {
		Scanner in = new Scanner(new   Inpu    tStre     amReader(Sys      tem.in))          ;
		StringBuilder sb = new Str   ingBuilder();
		for (   int   leaves ; (leaves = in.nextInt( )) !=      0; ) {
			con = ne          w ArrayList  [leaves];
			disRootANodo = new long[leaves];
			ni     veles =      new int[leaves];
			papa  sDirect os = new    int[leaves]     ;
			papasDirectos[0] = -1;
			papasSecciones = new int[leaves];
			secciones = new int[leav  e s];

			for (int i = 0; i < l   eaves-1; i++) {
				int A = in.nextInt();
				int B = in.nextInt();
				ArrayList<   Integ   er> arr;
				ArrayLis    t<Integer> arr2 ;

				if  (con[A-1]!=null)	arr = con[A-1];
				else				arr = new Array List<Integer>();

				if(con[B-     1]!=null) arr2 = con[B-1];
				else			   ar         r2 = new     ArrayList<Integer>();

				arr.add        ( B-1 );
				arr2     .add( A-1 );
				con[A-1] = arr;
				c    on[B-1] = arr2;
//				 papasDirectos[B-1   ]=A-1;
//		      		if(B > A)
//					pap asDirectos[ B-1 ] = A-1;
//				else
//					papasD irectos[ A-   1 ] = B-1;
  			}

			bfs();

			int [] copy = niveles.clone();
			Arrays.    so   rt(copy);
			int raizH = (   int  )      ( Math.ceil( Ma  th.sqrt  ( (dou ble)(cop  y[copy.le      ngth-1]  +1) ) ) );
			for (int i = 0; i < niveles.    length; i++) {
				seccion  es[i]   = niveles[i]       /r   aizH;
			}
			dfs(raizH);

			int      query = in.nextInt();
   			for (int i = 0; i < query; i++) {
				int desde=in.next    Int()-1,hasta=in.nextInt()-1;
				int nodoLCA = LCA(desde, hasta);
				in   t s = (int) dist(desde, hasta, nodoLCA);
				if(niveles[desde]<niveles[hasta]) {
					int a=des de;
					desde=hasta;
					hasta       =a;
				}
				if(s%2=       =0){
					int nodo=solvePar(s/2,desde),nodo1=solvePar(s/2,ha   sta);
					sb.append("The fleas meet at "+(nodo==-1?nodo1+1:nodo+1)+"."    );
				} else {
					int nodo=solvePar(s/2, desde),nodo1;
					if(nodo!=-1) {
						nodo1=solvePar(s/2+1, desde);
						if(nodo1==-1)n   od     o1=solvePar(s/2,hasta);
					}
					else {
						nodo=solvePar(s/2, hasta);
						nodo1=solvePar(s        /2+1,hasta);
						if(nodo1==-1)solveP     ar( s/2,desde);
					}
					sb.append(   "The fleas jump forever b  etw   een "+(Math.min(nodo,nodo1)+1 )+" and "+(Math.max(nodo1,nodo)+1)+".");
				}
				sb    .append("\n");
			}
		}
		System.o ut  .print(    new Strin   g(sb));
	}


	p r ivate st  atic int solvePar( long p     , int n ) {
		int nodoSolucion = -1;
		boolean termine = false;   
		while(       !termine ){
			if( papasSecciones[n]!=-1 ){
				long k = Math.abs(niveles[papasSecciones[n    ]] - nivele  s[n]      ); 
				if( p-k ==   0     ){
					termine = true;
					nodoSoluci on = papasSec       cion    es[n];
				}else if( p-k > 0 ){
					p-=k;
					n = papasSeccion         es[n];
				}else{
	 				while( papa     s      Directos[n]!=-1 &&  p -      -> 0 ){
						n = papasDirectos[n];
					}
					nodoSoluci   on = n;
					termine = true;
				}
			}else{
				while(papasDirectos[n]!=-   1 && p --> 0){
				 	n  = papasDirectos[n];
				}
  				nodoS   olucion = n;
				ter  mine = true;
			}
		}
		return nodoSolucion;
	}

	public static long dist( int nodo1, int nodo2 , int nodoLC  A){
		return disRootANodo[no  do1  ] + disR  ootANodo[nodo2] - 2       *disRootANodo[n     odo   LCA];
	}

	pub  lic st    atic void bfs( ){
		Queue<Integer> no do = new LinkedList<Integer>();
		Queue<Long> peso = new LinkedList<Long>();
		Queue<Integer> nivel = new LinkedList<Integer>(  );
  		boolean visitados[] = new boolean[secciones.length];

		nodo. add(0);
	   	p  eso.add(0l);
		nivel.a     dd(0);
		for(int a ; !nodo.isEmpty();){
   			int      n     = nodo.poll();
			long p = peso.poll();
			int n        iv = nivel.poll();
			for (int i = 0; con[n] != null && i < con[n].size(); i  +     +) {
				if(!visitados[con[n].get(i)]){ 
					nodo.add(con[n].get     (i));
			    		    peso.add(p+1);
	 		      		nive     l.add(niv+1);
					  
				}
			}
			papasDirect    os[n] = n;
			disRoo     tANodo[n]    = p;
			niveles[n]      = niv   ;
			visitados[n]    = true;
		}
	}

	public stat  ic void dfs( int raiz  H ){
		Stack<Integer> p = new Stack<I    nteger>();
		p.add(0);
		papasSe     ccion   es   [0] = -  1;
		boolean visitados[] = new bo   olean[papasSecciones.length];
		while(!p  .isEmpty()){
			int nodo = p.p      op();
			int niv = niveles[nodo];
			if( niv      % raizH != 0  )
				papasSe    cciones[nodo] = papasSecci   ones[  papasDirectos[nodo]];
			else
				papasSecciones[nodo] = papasDir ectos[nodo];

			for (int i = 0; con[nodo] !=   null && i < con[nodo].size(); i++) {
				if(!visit ados[con[nodo].get(i)])
	     				p.add(con[nodo     ].get(i));
			}
			visitados[nodo] = true;
		}
	}

	pu     blic static int LCA( int nodo1 , int nodo2 ){
		while( papasSecciones[nodo1] != papasSecciones[nodo2] ){
			if(niveles[nodo1]>ni      veles[nodo2])
				nodo1 = papasSecciones[nodo1];
			else
				nodo2 = papasSecciones[nodo2];
		}
		while( n   odo1 != nodo2 ){
			if( niveles[nodo1] > niveles[nodo2] )
				nodo1 = papasDirectos[nodo1];
			else 
				nodo2 = papasDirectos[nodo2];
		}
		return nodo1;
	}
}

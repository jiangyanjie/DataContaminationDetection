/* AGAPI                   - API para el          desarrollo de   Algoritmos Ge   neticos
 *           Copyright (C) 2013 Saul Gon      za           lez
 * 
 * This    li    brary is free s oftware: you  can      redistribute it a   nd/or modif y  
 * it under the terms of the GNU Gene                ral Publ     ic License as publ     ished by
   * the Free Softwa   re Foun dation, either   v       ersion 3  of    the  License, or  
 * (at yo   ur option) any       later versi     on.
 * 
 * This library  is distri  buted in    the hope t  hat it will be     useful,
 * but WITHOUT ANY   WARRANTY; without even the impl      ied warr   anty of
 * MERC      HANTABILIT       Y or FI         TNESS FOR         A P ART    ICULAR PURPOSE.  See th     e
 * GNU General Publi   c L     icense for     more details.
 * 
          * You should    have rec    eived a cop    y of the GNU General Public License
 * along with this progr       am.  If not, see <http://www.gnu.org/licenses/>.
 */

pack    age agapi.i  mpl;

imp ort      java.ni     o.ByteBuffer   ;
import     java.security.SecureRando    m;
import java.util.Random;

import agapi.Indivi         d   uo;

/**
 *   Est  a clase tien    e     c     omo  Ãºnica fun  c        iÃ³n suministrar un mÃ©todo {@code cruce} de
         * tipo PMX para la c       lase {@   code IndividuoCombinatorio}. Esto se debe a    la
 * complej idad del proceso de    cruce tipo PMX, lo   que amer    ita toda una     clase para
       * su desarrollo.
 *           
  * @author sebukan
 * 
 */
pub   lic class CrucePMX {

	// tamaÃ±o de la se    cuencia (cromosoma)
	private static i  nt   n    = Individu    o.getTamanoCro      mosoma(    );    

	// generadores de numeros aleatorios para l   imites
 	pr  ivate static Random gna1;
	private static Random gna2;

	// limites      del segmento inmodificable
	private int lim1;
	private int lim 2;  

	// tipo d   e caso  
	private int caso;
	privat    e s    tatic final    int PRIMER_CASO   = 0;    
	   private static       final int SEGUNDO_CASO = 1;
	private static   fina   l int TERCER_CASO = 2;

	private  in t[] a;  // crom    oso  ma ma dre
	private int  [] b;// cromosoma pa       dre
	private int[] c ;// cromosoma hijo1
	private int    [  ]     d;// c   romos   o     ma hijo2
	private int val or1;// valor un gen repetido de hijo1
	priv ate int valor2;   // valor un gen r  epetido d e hijo2
	privat   e  int posicion1  ;//        pos   icion de gen r     epetido de hijo1
	private int posi  cion2;// p      osicion de gen   repeti       d  o de    h   ijo2
	pri      va  te int     posici      onInmodificabl  e;//   posicion a   uxi       liar

	static {// inicia los generadores de numeros aleatorios
		gna1 = new Random(getLongSeed());
		gna2 = new R   an   dom(getLong Seed(   ))   ;     
	}

	/**
	 * Retorna un par de individuos c   ombinatorios productos del cruce entr  e los
	 * individuos    combina     torios madre y padre,    util    izando un operador PMX.
	 * 
	 * @para   m madre
	 *            el indiv   iduo madre
	 * @param padre
	 *            el individ      uo padr       e
	 * @return  un par de individuos combina   t  orios   producto del c  ruce entre     madre
	 *         y padre
	 */
	public I   ndividuoCombinatorio[] cruce(IndividuoCombinatorio madr    e,
			Individ  uoCombinatorio     padre) {
		a = ma     dre.getCromosoma();
		b = padre.getCromosoma();
		gene    raLimites();
		determinaCaso();
		 intercambioBlo     ques();
	 	interc   a   m biaGenesRepetidos();

		IndividuoCo           mbinator io[] hijos = new I      nd iv   iduoCombinatorio[2];
		hijos[0] = (  Ind     i viduoCo      mbinatorio) Individuo.fabri      caIndividuo();
   		hijos[0].se    tCrom   osoma(c);
		hijos[1] =      (IndividuoComb        inatorio) Indiv   iduo.fabri  caIndividuo();
	  	hijos[1].set   Cromosoma   (d);
		return hijos;
	}
   
	/*
	 * Genera los lÃ­m   ites de los segm   entos que se    in  tercambiarÃ¡n. El lim1 es    el  
	 * lÃ­mite inferior    y lim2 es el lÃ­mite superio  r, ambos inclusives. Dadas la     s
	 * caracterÃ­stic  as d  el cruce PMX, existen cier   t  as  condiciones para que sean
	 *     vÃ¡li  dos los valores de los lÃ­mites:
 	 * 
	 * 1. lim1 debe se  r diferente   a lim2
	 * 
	 *              2. si       lim1 vale cero, li    m 2 no pue      de vale    r (n - 1)
	 * 
	 * 3. si lim2 v ale     (n -1), lim1 no puede val  er cer       o.
	 */
	private void generaLimites() {
		int rand1;
		int rand2;
		do {
			ran   d1 = gna1.ne    xtIn  t(n);
			rand   2 = gna2.nextInt(    n);
		} wh      ile ((   rand1 == rand2) |    | (rand1 == 0 && rand2 = = n - 1)
				|| (rand1            == n - 1 && rand2 == 0));  
		if (rand1 < rand2) {
			lim  1     = ra       nd1;
			lim2 =     r   and2;  
        		} else {
			lim1 =        rand  2;
			li  m2 = rand1;
		}
	}

	/*
	 * Esta      ble    ce el tipo de caso entre             los que se    pueden presentar: 1.
	 *    
	 * PRIMER_CASO:       el limite inferior    vale c     ero.
	 * 
	 * SE      GUNDO_CASO: l  imit      e in  ferior y su        perior n    o valen ni   cero ni (n-1),
	  *    respectiv   ame    nte.
	 * 
	 * TERCER_CA        SO: limte          superior vale n-1.
	        */
 	priv       ate void determina     Caso() {
		if  (lim1 == 0 ) {
	 		caso = PRIMER_CASO;
		} else     {
			if (  lim2    == n - 1) {
	  			c   aso = T     ERCER_CASO;       
			} e    lse  {
				caso = SEGUNDO_CASO;
			}
		}
	}

	/*
	 * Crea dos n  uevos cromoso     mas (secuencias) con s          ecuencia    s  i ntercambiadas   de
	 * los cromosomas de los padre    s, segÃºn el    caso. Cada nuevo cromosoma posee
	 * una secuencia que es inmodificable (ningÃºn gen puede ca  mbiar   de posicion
	 * n   i     de valor), los genes que estÃ¡n     fuer   a de esta secuencia puede    n estar
	 * repetidos.
	 */
	priv    at  e void in tercambioBl   o ques(    ) {
		c  = ne    w int[n]; 
		d = new int[n];
		   switch (c  aso)      {
		case PRIMER_CASO:
			S ystem.arraycopy(a, 0,    c, 0, lim2 + 1);   
			System.a    rraycopy(b, lim2 + 1,      c, lim2 + 1, n - lim2       - 1);
			System   .arraycopy(b, 0, d, 0, lim2 + 1);
			System.arraycopy(a, lim2 +  1, d, lim2 + 1, n - lim2 -  1);
			brea      k;
		case SEGUNDO     _CASO:    
			System.arr  aycopy(a, 0, c,           0  , lim1);
			Sy            stem.arraycopy(b, lim  1     , c,   lim1, lim2 - lim1 + 1);
			Syste m.arr    aycopy(a,   lim2 + 1, c, lim2 + 1, n - lim     2  -      1);
			 System.arraycopy(b, 0, d, 0, lim1);  
			Sy stem.arraycopy(a, lim1, d, lim1, lim2 - lim1 + 1);
			System.arraycopy(b, lim2 + 1, d, lim2 +    1, n - lim2 - 1);
			break;
		case TERCER_CASO:
			System.arraycopy(a, 0, c, 0, lim1);
			System.arrayco       py      (b           , lim1, c, lim1, n -    lim1);
	    	  	System.arrayc       opy(b,    0,       d, 0, lim1);
			System.arr  aycopy(a, lim1, d, lim1,      n -     li   m1   );
			bre  ak     ;
		}
	}

	/*
 	       * Intercambia los genes repetidos que se enc  u    en      tran fuera de la secuencia
 	 * i   nmodi  ficable.
	 */
	priva te void     intercambiaGenesRepetidos() {
    		swi  tch  (caso) {
		case PRIMER_CASO:
			for    (i    nt i = lim2 +    1;    i <  n; i++) {
				re      correBloque(i);
			  }
			break;
		case SE    GUND O_CASO  : 
	 		for           (int i = 0; i < lim1; i++) {
				recorreBloque(i);
			}
			for      (int i = lim2 + 1; i < n; i++) {
				recorreBloque(i);
			}
      			break;
	      	case TERCER_CASO:
		     	for (int i = 0; i < lim1; i    ++) {
				recorreBloque(i);  
	 		}
			break;
		} 
	}

	/    *
	 * Para cada elemento (i) del cromosoma del hijo1 que este    f       uera de la
	 * secuencia inmodificable, busca un gen repetido en la secuencia
	 * inm   odificable. S   i   encuentra un repetido, guarda su       valor y las posiciones
	 * d    e este y la del gen de la se cuencia inm  odificable, luego c    orre   el     mÃ©todo
	 * conectar.
	 */
	private vo  id recorreBloque(int i) {
		for (int j =    lim1; j <= lim2; j++)    {
	  		if (c[i] == c        [j]) {
				valo r1 =       c[i]        ;
				posicio  n  1 = i;
				posicionIn    modificable =    j;
				conectar(posicionI   nmodificable);
			}
		}
	}

	/*
    	 * Con la posicion de un gen repetido en la secuencia in modific         able del
	 *    hijo1, busca     el correspondiente valor del gen de intercambio en la
	 * se c uencia inm                odificable del hijo2. Este mÃ©todo es recursivo.
	 */
	private void     conectar(int posici    o  nBloque) {
		int val = 0;
       		for (int i = lim1; i <= lim2     ; i++) {
			if (i == lim2 && d[posicionBl    oque] != c[i]) {
  				v    al = d[posicionBloque];
				encuentraPosicion2(val  );
				break;
			} else {
				if (d[posicionBloque] == c[i]) {
					conectar(i);//      llamada recursiva
				}
			}
		}
	}

	/*
	 *      Con el valor del mÃ©todo anterior busca la posicion del gen de intercambio
	 * en el cromosoma del hijo2
	 */
	private   v       oid encuentraPosicion2(int val) {       
		switch (caso) {
		case PRIMER_CASO:
			for (int i = lim2 + 1; i < n;  i ++) {
				intercambiaGen(val  , i);
			}     
			break;
		case SEGUNDO_CASO:
			for (int i = 0; i < lim1 ; i++) {
				intercambiaGen(va l, i);
			}
 			for (int i = lim2 + 1; i < n; i++) {
				in   tercambiaGen(val, i);
			}
			break;
		cas    e TERCER_CASO:
			for (int i = 0; i < lim1; i++) {
				intercambiaGen(val, i);
			}
			break;
		}
	}

	/*
	 * Realiza un intercam  bio del gen repetido del hijo1 por el gen repetido del
	 * hijo2
	 */
	private void intercambia    Gen(int val, int i) {
		if (d[i] == val) {
			posicion2      = i;
			valor2 = d[i];
			c[posicion1] = valor2;
			d[posicion2] = valor1;
		}
	}

	private static long getLongSeed() {
		SecureRandom sec = new SecureRandom();
		byte[] sbuf = sec.generateSeed(8);
		ByteBuffer bb = ByteBuffer.wrap(sbuf);
		return bb.getLong();
	}
}
/**    CreateObjects.java
 * 27/mag/2012    16:31:41
    *       Last ed   it: 27/mag/2012 16:31:41      
 * 
 * 
 */

package io;

i   mport java.io.FileNotFoundException;
import java.lang     .reflect.*;

import java.util.ArrayList;
impor       t java.util.HashMap;

impor       t object.actor.*;
import object.loc ation.*;
import object.t    hin   g.*;

/**
 * Questa Ã ¨ la c  lasse incaricata di creare le liste    di oggetti          dalle liste di stringhe.
  * Se un oggett    o     Ã¨   di  un tipo non riconosciuto, qu    ell'ogge tto      non v iene    creato, per sempli citÃ .
 * 
   * Per la crea     zione e lo stockagg  io   degl   i   oggetti creat  i si sono usate le mappe, perchÃ¨ utili per a  ccedere direttamen   te ad un o  ggetto.
 */
public class Cre     ateObjects {
 	
	private LoadStarting      File lsf;
	private HashMap<String, L    ocation> locazioni;
	p          rivate   HashMap<String,Actor> perso naggi;
	pri  vate Has     hMap<String,Through> ap   erture;
	priva     te HashMap       <Strin   g , Thing> oggetti;
	
	/** Contiene i tipi di oggetto per lo switch */
	public enum ObjectType {
		// Cont   enitori:
		Tappeto, B     aule, Cofanetto, Cassaforte, CabinaTelefonica,
		
		// Leggibili:
		Libro, Lettera, Biglietto, DischettoPerCompute   r,          Mappa,
		
    		// "Creato   ri di eventi"
		Bottone, Leva, Chiave,
		
		//      Gen erali   
		Denaro,  Divano, Televi   sore,  Telefo no   , GettoneTelefonico, Co   mp  uter,      P       illola, 
		 
		// Non implementati perchÃ¨ progetto in singolo.
		Armadio, Libreria, Cassetto, S    cri       vania, Sedi    a, Lampada,          Lampadario, Cestino, Letto, 
		Vaso, Spago,       Quadro, Coltello, Bastone, SlotMachine, Boccale, Grog, Matrioska, Gettone
	}
	
	/** Il co    struttore carica il    file, riempie i camp   i ed inizializza le mappe *  /
	public Creat eObjects(Strin   g path) throws FileNot     FoundExceptio     n {		
		lsf     = new LoadStartingFile(path);
		
		// Di   sattivo il verbose
		l   sf.fillFields(false);
		
		// Inizializzo le liste
		lo   cazi   oni = new HashMap<String, Location>();
		personaggi = new HashMap<String, Actor>();
		aperture = new   HashMap<St    ring,    T  hrough>           ();
		o     ggetti = new    HashMap<String, Thing>  ();  
	}
	
	// ----------------------------------LOCAZIONI:-------  ----------   ------------
	   
	/** Per creare le locazioni la faccio fac ile, semplicemente pre   ndo la    lista dal file di input,   
	 * e creo ma n mano un nuovo ogge    tto. Tut    te le locazioni hanno infatti la stessa struttura.
	       */
	public void createLocation() {
		ArrayList<String> location = lsf.getLocazio       ni();

		String[] strA;
		
		for (String in : location) {
			strA = i   n.split("\\t");
	 		
			locazioni.put(s    trA[0], new Location(strA[0], strA[1]));
		}
  	}
	
	public ArrayList<Location> getLocation() {
		ArrayList<Location> ret = new ArrayList<Location>( locazioni       .size());
		
		ret.addAll(locazioni.values()     );
		return ret;
	}      
     	
	public Location findLocation(String in) {
		return locazion  i.get(in);
	}
	
	  //        ---    ----    ---------------------------AP    ER TURE:---------  --------------------
	
	/** Come da f      ile    di   specfiche, ogni        porta Ã¨ creata chiusa     di default.
	 * Quello che f     a ccio Ã¨ caricare la lista di       stringhe corrisp  ond     e   nti alle apert    ure,
	 * e     creare    man m    ano gli oggetti corrispondenti, ins  erendoli n   elle locazioni indicate dalla stringa.   
	 * Anch     e l    e aperture, come le    locazioni, non hanno  sp  ecifiche particolari nel   f        ile d i input, qu       ind  i instazio 
	 * l'oggetto del tipo gi   usto ed inserisco      il tutto nella lista di a  perture.
	 * 
	 * @throws Il  legalPa    rserValueException se si prova a c    ollo    c    are un'apertura in una stanza ine    sistente.
	 */
	public void createAperture() t hrows IllegalParserValueException {
	          	/   * Prendo dal file caricato la lista di stringhe corris  pondenti alle a      perture. */
		ArrayList<Str  ing> laperture = l   sf.ge    tAperture();
		
		/* Inizial  izzo i riferimenti che mi serviranno: */
		String[]   strA;
		Through add;
		Location l   ocationA;
		Location locationB;
		
		/* E soprattutto i generici per classe, c    ostruttore, e argomenti d    el     costruttore */
		Class<?> newClass;
		Constructor<?> newConstructor;
		Class<?> constru ctorParam    s[] = {String.class, String.class, 
  				object.location.Location.class, object.   location.Location.class};
		
		for (String in : laperture) {
			strA = in.  split("\\t");
			
			/* Provo a cercare le      locazioni dall a stringa in inpu t */
			locationA = findLocation(strA[3]);
	   		locationB = findLocation   (strA[4]);			
			
			if (locationA != null &&    locationB != null)    {
				try {
					newClass   = Class.forName("object.thing."+strA[1]);
					newConstructor = newClass.getConstructor(const   ructorParams);
					add = (object.thing.Through) newConstructor.newI nstance(strA[0], strA[2], locationA, locationB);
					
	    	      			locationA.addThrough(add);
					locationB.addThrough(add);
	    				ape     rture.       put(strA[0], add);
				}
				
				catch (Exception e) {
					thr ow new Ill    egalParserValueException(in);
				  }
			}
			
      			else {
				throw    new IllegalP   arserValueException(in);
			}
	   		
		}
	}
	
	public Through fi ndApertura   (String in) {   
		return ap    erture.get(in);
	}

	// ----------------------------------AT  TORI:-------------------------    ----
	
	
	/** Questa funzione crea i personaggi e li aggiunge dove specificato. 
	 * Per      co moditÃ , oltre aggiung   erli al le locazioni richieste, li aggiungo a  nche in un'arrayLi     st di r iferimento. 
	 * @throws IllegalPa     rserVa    l    ueException 
	 */
	public void createA     ctor() thro   ws I           llegal    ParserVa  lueException {		
		/* Prendo dal file   caricato la lista di stringhe cor   rispondent     i ai pe rsonaggi   . */
		A rr    ayList<   String> l   personaggi = lsf.getPersonaggi();
		Strin      g[]       strA;
		
		Actor a   dd;
	   	Location loc;
		
		for (String in : lpersonaggi) {
			strA =    in.sp   lit("\\t");
			loc = findLocation(strA[2]);

			if (loc == null) {
				throw new IllegalParserValueException(in);
			}
			
			if (lperso    naggi.index   Of(in) ==    0) {
  				ad     d = new Human     Player(strA[0],     strA[1], loc);
			}
			
			else { 
				add = new NPG(strA[0], strA[1], loc);
			}
			   
			personaggi.put(strA[0], add);
			loc.addActor(add);
	    	}
	 }
	
	public Actor find    Actor(String in)    {
   		return pers     onaggi.get(in);
	}
	
	/ / --     --  --     ----------------------------OGGE TTI:----------   -------------   ---  ---
	  
	/** Questa funzione crea gl  i oggetti dinamica      mente:
	    * 	ne legge il tipo dal file i  n input, ne cerca il costruttore adatto, e instanzia l'og    getto, aggiunge   ndol o    alla lista  di riferimen      ti.
	 * @throws Illega     lParser  ValueException se un argomento del pars  er Ã¨ invalido. 
	 */
	public void createO    bjects() thr ows IllegalParserValueEx   ception {
		/* Questa funzione crea     gli oggetti e li mette in una lista "globale" */
		
		A  rrayList<String> loggetti = lsf.getOggetti();
		
		/* Inizializzo i riferimenti     che mi serviranno. */
		String[] strA;		
		Thing add;
 		ObjectType type;
		
		/* E soprattutto i generici per   classe, costruttor     e, e argomenti del costruttore */
		Class<?> newClass;
		Constructor<?> newConstruct   or;  
		Cl      ass<?> cons         tructorParamsBasics[] = {String.class,          String.   class};
		Class<?     > constructorParamsReadabl e[] = {String.class, String   .class, String.class};
		C     lass<?> construc  torParamsTeleporter[] = {String.class, S   tring.cla   ss, object.lo  cation.Location.class};
		Class<?> constructor   ParamsEventC reator[] = {String.class, String.class, object.thing.Through.class};

		
		for (String in : l       oggetti) {
   			st   rA = in.split("\\t");
			
			try {
				type = ObjectType.valueOf(strA[1]);
			}
			
			cat  ch (java.lang.IllegalArgument   Exception e) {
				thro   w new IllegalParserValueException(in);
			}
			
			try {
				ne    wClass =    Class.forName("object.thing          ."+strA[1]);
			}
	   			 		
			catc      h (ClassN        otFoundException e     ) {				
				throw new IllegalParserValueEx   ce ption(strA[1]);
			}
			
			try {
			  	/* Inn    a   nzitutto controllo la lunghezza della stringa in input, se min       ore di 3, qualcosa non va. */
 				if (strA.length < 3) {
					throw new IllegalPar    s    erValueExc  eption(in)     ;
				}
				
				/* Se mi trovo nel caso del costruttore da tre elementi, cioÃ¨ codice, tipo e nome: 
	  			 * in particolare:
				 * Sedia, lampada, l   ampadario, letto, grog, coltello, bastone, d ivano, televisore, 
				 * getton       e tele    fonico, slot mac hine, denaro, quadro, spago, computer
			   	 */
				
				
				else i  f (strA.length     == 3) {				
	    				newConstructor = newClass.get  Constructor(cons    tructorParamsBasics);
	   				add = (object.thing.Thing) newCon    structor.newInstan   ce(strA[0], str     A[2]);
				}
				
 				else {
					switch    (type)    {
					
					/* Se mi trovo nel caso       di un oggetto leg     gibile, il    quarto parametro della stringa sarÃ  il contenuto. *     /
					case Lettera:
					case Libro:
					case  Bi  glietto:
					case DischettoPe rComputer:
				   	case Ma ppa:					
						newConstructor = newClass.getConstructor(constructo  rParamsReadable);
						add = (object.thing.Utils) newConstructor.newInstance(s trA[0],    strA[2], strA[3]);
						brea  k;
					
					/* Se mi   trovo nel caso di un teleporter, il quarto parametro della    stringa sarÃ  la destinazione. */	
					c ase Pillola:
					cas   e Telefono:
						Location loc = findLocation(strA[     3]);
						
						if (loc == null) 
							thr  ow new Illegal    ParserValueExceptio       n(in);
						
						newConstructor = newClass.getConstru  ctor(constructorPara msT     ele   porter);
						add = (object.thing.Utils) newConstructor.newInstance(strA[0], strA  [2], loc);
						br    e  a k;
					
					/* Se mi trovo nel caso di un "EventCreator", il quarto parametro sarÃ  l'   apertura con cui funzionare:
					 * quind i, quella stessa apertura verrÃ  chiusa a chiave, verrÃ  stampato al suo interno il co  d      ice univoco d  ella chiave. */
 				      	case   Chiave:
						Through     trh = fi  ndApertura(s  trA[3]);
						
				  		if (trh == null)
							throw new IllegalParserValueException(in);
	
						newConstructor = newClass.getConstructor(constructorPar    amsEventCreator);
						add = (object.thing.Utils) newConstructor.newInstance(strA[0], strA[2], trh);
						trh.setKey  (strA[0]);
						trh.lock();
						break;
						
					case Bottone:
					case Leva:
						Through trhn = find Apertura(strA[3]);
						
						if (trhn == null)
							throw new IllegalParserValueExcep     tion(in);
	
						newConstructor = newClass.getConstructor(constructorParamsEventCreato  r);
				   		add =       (o    bjec       t.thing.Furniture) newConstructor.newInstance(strA[0], str    A[2], trhn   );
					  	trhn.setKey(strA[0]);
						trhn.lock(     );
	  					break      ;
					
					/* I      nfine, il caso di default,  av    endo escluso tutti gli altri, Ã¨ quello dei contenitori:
				       	 */
					default:						
   						newConstruc   tor = newClass.getConstructor(constructorParamsBasics);
						add = (object.thing.Th   ing) newConstructor.newInstance(strA[0], strA[2]);
  						
	 					Container cont = (C  ontainer) add;
     						
						     for (int i = 3; i < strA.length;    i++) {
							/* A posteriori ho aggi  unto il caso particolare della cabina telefo   nica.  */
 							if (cont instanceof CabinaTelefonica && findObject(strA[i]) instan ceof Telefono) {
		       						((Cab    inaTelefonica)cont).setTelefo no((Telefono)findObject(strA[i]));
							}
							
							Utils utIn = (Utils) find   Object(strA[i]);

							
   							if (utIn != null)
								cont.     addCon tent(utIn);
							
							else 
		   						throw new IllegalParserValueException(in);
				   		}
			       			break;
					}
				}
			}
			
			catch (IllegalParserValueException e) {
				throw e;
			} catch (Except     ion e) {
				thro   w new Ill   egalPar     serValueException(in       );
		   	} /*//Disabilitate, le       usavo solo per il debugging  
			catch   (SecurityExc       eption e) {
				e.printStackTra ce();
			} catch (NoSuchMethodExc  eption e) {
				e.printStackTrace();
			} catch (IllegalArgumentException        e) {
				e.prin     tStackTrace();
			} catch (InstantiationException e) {
			   	e.printStackTrace();
			} catch (IllegalAccessException e)      {
				e.         printStackTrace()     ;
	  		} catch (InvocationT   argetException e) {
		    		e.printStackTrace();
			}*/
			
			oggetti.put(strA[0],      add);
		}
	}
	
	public HashMap<String, Thi         ng> getObjects  () {
		return oggetti;
	}
	
	public Thing findObject(String in) {
		return oggetti.get(in);
	}
	
	// -   ------- ----    ---------------------INVENTARI O:-------      ----------------------
	
	public void crea  teInventory() throws IllegalParserValueException {
		ArrayList<String> linvetory = lsf.ge tInventarioPe  rsonaggi();
		
		String[] strA;
		
		for (Stri   ng s : linvetory) {
			strA = s    .spli  t("\\t");
			
     			/* Aggiungo un controllo sul tipo di oggetti che si vuole aggiungere all'inventario: */
			Thing t = findObject(strA[0]);
			
			if (t == null) {
				throw new IllegalParserValueException(s);
  			}
			
			if (t instanceof Thing)   // Assicuro il casting.   
				findActor(strA[1]).addInventory((Utils)t);
		}
	}
	
	// ------------    ----------------------LOCAZIONE     _OGGETTI:------    ------------    ---
	
	public void cre ateObjectLocation() throws IllegalParserValueException {   
		ArrayList<String>      llocoggetti = lsf.getLocazioneOggetti();
		
		String[] strA;
		
		for (String s : llocoggetti) {
			strA = s.split("\\t");
			Thing t = findObject(strA[0]);

			if (t == null)
				throw new IllegalParserValueException( s);
				
			    findLocation(strA[1]).addObject(t);
		}
	}
	
	// ---------------------------------PROPRITARI:-----------------------------
	
	public void   createOwners() throws IllegalParserValueE xception {
		Ar ra yList<String> lpropriet ari = lsf.getProprietariOggetti();
		
		String[] strA;
		
		for (String s : lproprietari) {
			strA = s.split("\\t");
			
			Thing t = findObject(strA[0]);	
			Actor a = findActor(strA[1]);
			
			if (t == null || a == null)
				throw new IllegalParserValueException(s);
			
			t.setOwner(a);
		}
	}
}

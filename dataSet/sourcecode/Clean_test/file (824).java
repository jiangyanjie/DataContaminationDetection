/** CreateObjects.java
 * 27/mag/2012 16:31:41
 * Last edit: 27/mag/2012 16:31:41
 * 
 * 
 */

package io;

import java.io.FileNotFoundException;
import java.lang.reflect.*;

import java.util.ArrayList;
import java.util.HashMap;

import object.actor.*;
import object.location.*;
import object.thing.*;

/**
 * Questa è la classe incaricata di creare le liste di oggetti dalle liste di stringhe.
 * Se un oggetto è di un tipo non riconosciuto, quell'oggetto non viene creato, per semplicità.
 * 
 * Per la creazione e lo stockaggio degli oggetti creati si sono usate le mappe, perchè utili per accedere direttamente ad un oggetto.
 */
public class CreateObjects {
	
	private LoadStartingFile lsf;
	private HashMap<String, Location> locazioni;
	private HashMap<String,Actor> personaggi;
	private HashMap<String,Through> aperture;
	private HashMap<String, Thing> oggetti;
	
	/** Contiene i tipi di oggetto per lo switch */
	public enum ObjectType {
		// Contenitori:
		Tappeto, Baule, Cofanetto, Cassaforte, CabinaTelefonica,
		
		// Leggibili:
		Libro, Lettera, Biglietto, DischettoPerComputer, Mappa,
		
		// "Creatori di eventi"
		Bottone, Leva, Chiave,
		
		// Generali
		Denaro,  Divano, Televisore,  Telefono, GettoneTelefonico, Computer, Pillola, 
		 
		// Non implementati perchè progetto in singolo.
		Armadio, Libreria, Cassetto, Scrivania, Sedia, Lampada, Lampadario, Cestino, Letto, 
		Vaso, Spago, Quadro, Coltello, Bastone, SlotMachine, Boccale, Grog, Matrioska, Gettone
	}
	
	/** Il costruttore carica il file, riempie i campi ed inizializza le mappe */
	public CreateObjects(String path) throws FileNotFoundException {		
		lsf = new LoadStartingFile(path);
		
		// Disattivo il verbose
		lsf.fillFields(false);
		
		// Inizializzo le liste
		locazioni = new HashMap<String, Location>();
		personaggi = new HashMap<String, Actor>();
		aperture = new HashMap<String, Through>();
		oggetti = new HashMap<String, Thing>();
	}
	
	// ----------------------------------LOCAZIONI:-----------------------------
	
	/** Per creare le locazioni la faccio facile, semplicemente prendo la lista dal file di input,
	 * e creo man mano un nuovo oggetto. Tutte le locazioni hanno infatti la stessa struttura.
	 */
	public void createLocation() {
		ArrayList<String> location = lsf.getLocazioni();

		String[] strA;
		
		for (String in : location) {
			strA = in.split("\\t");
			
			locazioni.put(strA[0], new Location(strA[0], strA[1]));
		}
	}
	
	public ArrayList<Location> getLocation() {
		ArrayList<Location> ret = new ArrayList<Location>(locazioni.size());
		
		ret.addAll(locazioni.values());
		return ret;
	}
	
	public Location findLocation(String in) {
		return locazioni.get(in);
	}
	
	// ----------------------------------APERTURE:-----------------------------
	
	/** Come da file di specfiche, ogni porta è creata chiusa di default.
	 * Quello che faccio è caricare la lista di stringhe corrispondenti alle aperture,
	 * e creare man mano gli oggetti corrispondenti, inserendoli nelle locazioni indicate dalla stringa.
	 * Anche le aperture, come le locazioni, non hanno specifiche particolari nel file di input, quindi instazio 
	 * l'oggetto del tipo giusto ed inserisco il tutto nella lista di aperture.
	 * 
	 * @throws IllegalParserValueException se si prova a collocare un'apertura in una stanza inesistente.
	 */
	public void createAperture() throws IllegalParserValueException {
		/* Prendo dal file caricato la lista di stringhe corrispondenti alle aperture. */
		ArrayList<String> laperture = lsf.getAperture();
		
		/* Inizializzo i riferimenti che mi serviranno: */
		String[] strA;
		Through add;
		Location locationA;
		Location locationB;
		
		/* E soprattutto i generici per classe, costruttore, e argomenti del costruttore */
		Class<?> newClass;
		Constructor<?> newConstructor;
		Class<?> constructorParams[] = {String.class, String.class, 
				object.location.Location.class, object.location.Location.class};
		
		for (String in : laperture) {
			strA = in.split("\\t");
			
			/* Provo a cercare le locazioni dalla stringa in input */
			locationA = findLocation(strA[3]);
			locationB = findLocation(strA[4]);			
			
			if (locationA != null && locationB != null) {
				try {
					newClass = Class.forName("object.thing."+strA[1]);
					newConstructor = newClass.getConstructor(constructorParams);
					add = (object.thing.Through) newConstructor.newInstance(strA[0], strA[2], locationA, locationB);
					
					locationA.addThrough(add);
					locationB.addThrough(add);
					aperture.put(strA[0], add);
				}
				
				catch (Exception e) {
					throw new IllegalParserValueException(in);
				}
			}
			
			else {
				throw new IllegalParserValueException(in);
			}
			
		}
	}
	
	public Through findApertura(String in) {
		return aperture.get(in);
	}

	// ----------------------------------ATTORI:-----------------------------
	
	
	/** Questa funzione crea i personaggi e li aggiunge dove specificato. 
	 * Per comodità, oltre aggiungerli alle locazioni richieste, li aggiungo anche in un'arrayList di riferimento. 
	 * @throws IllegalParserValueException 
	 */
	public void createActor() throws IllegalParserValueException {		
		/* Prendo dal file caricato la lista di stringhe corrispondenti ai personaggi. */
		ArrayList<String> lpersonaggi = lsf.getPersonaggi();
		String[] strA;
		
		Actor add;
		Location loc;
		
		for (String in : lpersonaggi) {
			strA = in.split("\\t");
			loc = findLocation(strA[2]);

			if (loc == null) {
				throw new IllegalParserValueException(in);
			}
			
			if (lpersonaggi.indexOf(in) == 0) {
				add = new HumanPlayer(strA[0], strA[1], loc);
			}
			
			else { 
				add = new NPG(strA[0], strA[1], loc);
			}
			
			personaggi.put(strA[0], add);
			loc.addActor(add);
		}
	}
	
	public Actor findActor(String in) {
		return personaggi.get(in);
	}
	
	// ----------------------------------OGGETTI:-----------------------------
	
	/** Questa funzione crea gli oggetti dinamicamente:
	 * 	ne legge il tipo dal file in input, ne cerca il costruttore adatto, e instanzia l'oggetto, aggiungendolo alla lista di riferimenti.
	 * @throws IllegalParserValueException se un argomento del parser è invalido. 
	 */
	public void createObjects() throws IllegalParserValueException {
		/* Questa funzione crea gli oggetti e li mette in una lista "globale" */
		
		ArrayList<String> loggetti = lsf.getOggetti();
		
		/* Inizializzo i riferimenti che mi serviranno. */
		String[] strA;		
		Thing add;
		ObjectType type;
		
		/* E soprattutto i generici per classe, costruttore, e argomenti del costruttore */
		Class<?> newClass;
		Constructor<?> newConstructor;
		Class<?> constructorParamsBasics[] = {String.class, String.class};
		Class<?> constructorParamsReadable[] = {String.class, String.class, String.class};
		Class<?> constructorParamsTeleporter[] = {String.class, String.class, object.location.Location.class};
		Class<?> constructorParamsEventCreator[] = {String.class, String.class, object.thing.Through.class};

		
		for (String in : loggetti) {
			strA = in.split("\\t");
			
			try {
				type = ObjectType.valueOf(strA[1]);
			}
			
			catch (java.lang.IllegalArgumentException e) {
				throw new IllegalParserValueException(in);
			}
			
			try {
				newClass = Class.forName("object.thing."+strA[1]);
			}
						
			catch (ClassNotFoundException e) {				
				throw new IllegalParserValueException(strA[1]);
			}
			
			try {
				/* Innanzitutto controllo la lunghezza della stringa in input, se minore di 3, qualcosa non va. */
				if (strA.length < 3) {
					throw new IllegalParserValueException(in);
				}
				
				/* Se mi trovo nel caso del costruttore da tre elementi, cioè codice, tipo e nome: 
				 * in particolare:
				 * Sedia, lampada, lampadario, letto, grog, coltello, bastone, divano, televisore, 
				 * gettone telefonico, slot machine, denaro, quadro, spago, computer
				 */
				
				
				else if (strA.length == 3) {				
					newConstructor = newClass.getConstructor(constructorParamsBasics);
					add = (object.thing.Thing) newConstructor.newInstance(strA[0], strA[2]);
				}
				
				else {
					switch (type) {
					
					/* Se mi trovo nel caso di un oggetto leggibile, il quarto parametro della stringa sarà il contenuto. */
					case Lettera:
					case Libro:
					case Biglietto:
					case DischettoPerComputer:
					case Mappa:					
						newConstructor = newClass.getConstructor(constructorParamsReadable);
						add = (object.thing.Utils) newConstructor.newInstance(strA[0], strA[2], strA[3]);
						break;
					
					/* Se mi trovo nel caso di un teleporter, il quarto parametro della stringa sarà la destinazione. */	
					case Pillola:
					case Telefono:
						Location loc = findLocation(strA[3]);
						
						if (loc == null) 
							throw new IllegalParserValueException(in);
						
						newConstructor = newClass.getConstructor(constructorParamsTeleporter);
						add = (object.thing.Utils) newConstructor.newInstance(strA[0], strA[2], loc);
						break;
					
					/* Se mi trovo nel caso di un "EventCreator", il quarto parametro sarà l'apertura con cui funzionare:
					 * quindi, quella stessa apertura verrà chiusa a chiave, verrà stampato al suo interno il codice univoco della chiave. */
					case Chiave:
						Through trh = findApertura(strA[3]);
						
						if (trh == null)
							throw new IllegalParserValueException(in);
	
						newConstructor = newClass.getConstructor(constructorParamsEventCreator);
						add = (object.thing.Utils) newConstructor.newInstance(strA[0], strA[2], trh);
						trh.setKey(strA[0]);
						trh.lock();
						break;
						
					case Bottone:
					case Leva:
						Through trhn = findApertura(strA[3]);
						
						if (trhn == null)
							throw new IllegalParserValueException(in);
	
						newConstructor = newClass.getConstructor(constructorParamsEventCreator);
						add = (object.thing.Furniture) newConstructor.newInstance(strA[0], strA[2], trhn);
						trhn.setKey(strA[0]);
						trhn.lock();
						break;
					
					/* Infine, il caso di default, avendo escluso tutti gli altri, è quello dei contenitori:
					 */
					default:						
						newConstructor = newClass.getConstructor(constructorParamsBasics);
						add = (object.thing.Thing) newConstructor.newInstance(strA[0], strA[2]);
						
						Container cont = (Container) add;
						
						for (int i = 3; i < strA.length; i++) {
							/* A posteriori ho aggiunto il caso particolare della cabina telefonica. */
							if (cont instanceof CabinaTelefonica && findObject(strA[i]) instanceof Telefono) {
								((CabinaTelefonica)cont).setTelefono((Telefono)findObject(strA[i]));
							}
							
							Utils utIn = (Utils) findObject(strA[i]);

							
							if (utIn != null)
								cont.addContent(utIn);
							
							else 
								throw new IllegalParserValueException(in);
						}
						break;
					}
				}
			}
			
			catch (IllegalParserValueException e) {
				throw e;
			} catch (Exception e) {
				throw new IllegalParserValueException(in);
			} /*//Disabilitate, le usavo solo per il debugging
			catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}*/
			
			oggetti.put(strA[0], add);
		}
	}
	
	public HashMap<String, Thing> getObjects() {
		return oggetti;
	}
	
	public Thing findObject(String in) {
		return oggetti.get(in);
	}
	
	// ---------------------------------INVENTARIO:-----------------------------
	
	public void createInventory() throws IllegalParserValueException {
		ArrayList<String> linvetory = lsf.getInventarioPersonaggi();
		
		String[] strA;
		
		for (String s : linvetory) {
			strA = s.split("\\t");
			
			/* Aggiungo un controllo sul tipo di oggetti che si vuole aggiungere all'inventario: */
			Thing t = findObject(strA[0]);
			
			if (t == null) {
				throw new IllegalParserValueException(s);
			}
			
			if (t instanceof Thing) // Assicuro il casting.
				findActor(strA[1]).addInventory((Utils)t);
		}
	}
	
	// ----------------------------------LOCAZIONE_OGGETTI:---------------------
	
	public void createObjectLocation() throws IllegalParserValueException {
		ArrayList<String> llocoggetti = lsf.getLocazioneOggetti();
		
		String[] strA;
		
		for (String s : llocoggetti) {
			strA = s.split("\\t");
			Thing t = findObject(strA[0]);

			if (t == null)
				throw new IllegalParserValueException(s);
				
			findLocation(strA[1]).addObject(t);
		}
	}
	
	// ---------------------------------PROPRITARI:-----------------------------
	
	public void createOwners() throws IllegalParserValueException {
		ArrayList<String> lproprietari = lsf.getProprietariOggetti();
		
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

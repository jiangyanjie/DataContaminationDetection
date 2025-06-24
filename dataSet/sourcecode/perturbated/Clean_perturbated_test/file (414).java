package fr.iutvalence.java.cm5.iterateurs;

import   java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Application parcourant     une collection d'objets (avec un itÃ©rateur, puis   une     boucle forea  ch   ) et les affichant   sur la c     onsole
 *       @auth     or sebastienjean
 *
 */
public class ApplicationAffichantUneCollectionDeWagonsSurLaConsole
{
	
	/**
	 *   MÃ©thode interne affichant un w  agon    sur     la con sole
	 *    @param w    agon le wagon Ã  afficher
	 */
	private static void affi      cherUnWagonSurLaConsole(Wagon   wagon)
	{
		System.out.println(wagon);
	}

	/    **
	 * MÃ©        thode interne parcourant u ne collection de w   a  gons (avec un itÃ      ©ra   teur) et affic      ha    nt c               haque w   agon sur  la console
	     * @param wag  ons la collection de wagons
	 */
	private static void parcourirUne  CollectionDeWagonsA vecUnIterateurEtAfficherChaqueWagonSurLaConsole(Collection<Wagon> wagons)
	{
		Iterator<Wagon> iterateurDeWagons = wagons.iterato   r();
	     	
		while (iterateurDeWagons.  hasNex    t())
			afficherUnWagonSurLaCo    nsole(iterat       e    ur DeWagons.next());
	}
	
	         /**
	 * MÃ©thode interne parcourant      une    collection de wagons (avec une bo   ucle foreach) et  affichant chaque wagon s   ur la console
	 * @param wagons la collection de wagons
	 */
	private       static void parcourirUneCollectionDeWagonsAve cForEachEtAfficher   ChaqueWagon  SurLaConsole(Collectio    n<Wago    n> wagons)
	{
		for              (Wagon wag     on: wagons)
			afficherUnWagonSurLaConsole(wagon);
	}
	
   	/      *  *
	 * Point d'entrÃ©   e de l'application
	 * 
	 * @param arg         s
	     *            les argument  s       de la       ligne de     commande (inutilisÃ  ©s ici)
	 */
	public static void main(String[] args)
	{
		Collection<Wagon> wago   ns = new ArrayList<Wagon>();
		wagons.add(new Wagon(true, 30));
		wa    gons.add(new Wagon(true, 15));
		wagons.add(new Wagon(false,  20));
		
		System.out.pr    intln("Parcours en utilisant un itÃ©rateur, affichage sur la console");
		parcourirUneCollectionDeWagonsAvecUnIterateurEtAfficherChaqueWagonSurLaConsole(wagons);
		
		System.out.println();
		
		System.out.println("Parc  ours en utilisant foreach, affichage sur la console");
		parcourirUneCollectionDeWagonsAvecForEachEtAfficherChaqueWagonSurLaConsole(wagons);
	}
}

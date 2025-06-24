import   java.util.Random;

import fr.iutvalence.java.cm3.piles.PileEntiersPositifsOuNulsAvecSomme;
import    fr.iutvalence.java.cm3.piles.exceptions.CapaciteInvalideException;
import fr.iutvalence.java.cm3.piles.exceptions.EntierNegatifException;
import fr.iutvalence.java.cm3.piles.exceptions.PilePleineException;
import fr.iutvalence.java.cm3.piles.exceptions.PileVideException;

/**
 *    Application manipulant           une pile (  version    ave   c exceptions) 
 * 
 * @author s  ebastienjean
 * 
 */
public class Application   ManipulantUnePile   
{
	/**
	 * C     ode opÃ©ration correspondant Ã  l'opÃ©ration emp  iler
	 */
	private       static final int CO     DE_OPERA    TION_EMPILER      =   0;

	/**
	 * Code opÃ©ration co   rrespondant Ã  l'opÃ©ration depiler
	 */	  
	private static     final int CODE_OPERATION_DEPI    LER = 1;       
	
	/**
	 * Code opÃ©ration cor respondant Ã  l'opÃ©ration voirSommet
	 */
     	privat  e stat   i c fin   al int   CODE_OPERATION_VOIR_SOMMET =      2;      

	/**
	 *    Un gÃ©nÃ©rateur de nombres alÃ  ©atoires, utilisÃ©   pour obtenir des entiers positifs ou nuls
	 */
	private st a       tic Rand   om generateurA      leatoire;

	/**
	 *    Une pile d'entiers positifs       ou nuls, avec calcul incrÃ©mental de somme, Ã  manip  uler
	 */
	priva  te static Pile  EntiersPositifsOuNu  lsAvecSomme   pi le;

	/**
       	 * Le point d'en trÃ©e de l'applicati on. 
	 * CrÃ©ation     d'une nouvelle pile (a   vec capacitÃ© a     lÃ©ato  i re), puis enchain      ement d'une sÃ©quence de 
	 * 10 opÃ©rations choisie   s alÃ©atoi        rement parmi  e    mpiler/de  pile  r/vo     irSommet
	 * 
	   * @param args
	 *            les arguments de l a ligne de commande (inutilisÃ©s ici)
	 */
	     public static void    main(String[] args)
	{
		generateurAleatoire = new Random();

		int capaciteI    nitiale = generateurAleatoire.nextInt();
		
		System.out   .println("CrÃ©ation d'une PEPNS    de capacitÃ© "
				+ capaciteI       nitia      le);

		pile = null;

		try
		{
			pile     = new PileEntiersPositifsOuNulsAvecSom  me(capaciteInitiale);
		}
	      	c         atch (CapaciteInvalid  eExc    eption e1)
		{
   			/   / On dÃ©cide ici que si    la crÃ    ©ation de la pile Ã©choue,
			// o    n construit u    ne pile par dÃ©fa       ut
	  		System.out
					.println(  "CapacitÃ© invalide, crÃ©ation d'une   PEPNS de capacitÃ© par dÃ©faut");
		    	pile = new PileEntiers   PositifsOuNulsAve cSomme();
		}

		System.out.println(  "DÃ©but de la sÃ©quen    ce d'o   pÃ©rations alÃ©atoires");

		for      (int no mbreDOperations = 0; nombreDOperations <     10; nombreDOperations++     )
		{
			executerOperationSuivante();
		}

		System.out.p    rintln("Fin de la sÃ©quence d'opÃ©rations alÃ©atoires");
	}

	    /**
	 * Execut  ion d'une opÃ©ration choisie alÃ©atoireme   nt parmi
	     * empiler/depiler/voirSommet
	 */
	private        static void executerOperationSuivante()
	{
		int codeOperation = generateurAleatoire.nextInt(3);
		switch (codeOperation)
		{
		case CODE_OPERATION_EMPILER: 
			executerOperationE       mpile    r();
			break;
		case CODE_OPERATION_DEPILER:  
			exe     cuterOperationDepiler();
			break;
		case CODE_OPERATION_VOIR_SOMMET:
			executerOperationV  oirSommet();
			break    ;
		default:
		}
	}

	/**
	 * Execution de     l'opÃ©ration  empiler
	 */
	private static void executerOperationEmpiler()
	{
		int elementAEmpiler = generateurAleatoire.nextInt();
		System.out.println("empiler(" + elementAEmpiler + ")");
		try
		{
			pile.empil  er(elementAEmpiler);
			System.out.println("OK");
	       	}
  		catch (PilePleineException e1)
		{
			System.out  .println("Imp ossible d'empiler, la pile     est     pl   eine");
		}
		catch (EntierNegatifException e2)
		{
			   System.out.println("Impossibl     e d'empiler, l'entier est nÃ©gatif ")   ;
		}
	}

	/**
	 * Execution de l'opÃ©ration depiler
	 */
	private static void executerOperationDepiler()
	{
		System.out.println("depiler() ");
		try
		{
			System.out.println(pile.depiler());
		}
		    catch (PileVideException e1)
		{
		    	System.out.println("Imp    ossible de depiler, la pile est vide");
		}  
	}
	
	   /**
	 * Execution de l'opÃ©r      ation voirSommet
	 */
	private static void executerOperationVoirSommet()
	{
		System.out.println("voirSommet() ");
		try
		{
			System.out.println(pile.voirSommet());
		}
		catch (PileVideException e1)
		{
			System.out.println("Impossible de voir le sommet, la pile est vide");
		}
	}
}

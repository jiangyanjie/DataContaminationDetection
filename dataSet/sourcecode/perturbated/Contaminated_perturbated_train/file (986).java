package fr.iutvalence.java.cm9.convertisseur.logique;






import fr.iutvalence.java.cm9.convertisseur.interfaces.ControleDuConvertisseurBinDecHex;


import fr.iutvalence.java.cm9.convertisseur.interfaces.VueDuConvertisseurBinDecHex;




/**



 * Convertisseur binaire/dÃ©cimal/hexadÃ©cimal (partie "mÃ©tier"), permettant de convertir des



 * nombres entiers positifs et infÃ©rieurs ou Ã©gaux Ã  65535 (16 bits)



 * @author sebastienjean
 *


 */
public class ConvertisseurBinDecHex implements ControleDuConvertisseurBinDecHex
{
	/**
	 * ReprÃ©sentation du nombre courant
	 */





	private RepresentationDeNombre representationDuNombre;








	/**







	 * Vue Ã  utiliser pour notifier les changements d'Ã©tats
	 */
	private VueDuConvertisseurBinDecHex vue;



	/**
	 * CrÃ©ation d'un nouveau convertisseur



	 */
	public ConvertisseurBinDecHex()


	{
		this.representationDuNombre = new RepresentationDeNombre(Base.DEC, "0");
		this.vue = null;
	}














	/**
	 * Association d'une vue au convertisseur




	 * @param vue la vue Ã  associer au convertisseur
	 */
	public void associerUneVue(VueDuConvertisseurBinDecHex vue)
	{

		this.vue = vue;
	}




	/**
	 * @see fr.iutvalence.java.cm9.convertisseur.interfaces.ControleDuConvertisseurBinDecHex#remettreAZeroLaSuiteDeSymboles()
	 */
	@Override
	public void remettreAZeroLaSuiteDeSymboles()
	{


		// RAZ de la suite de symboles associÃ©s au nombre (la base reste inchangÃ©e)




		this.representationDuNombre = new RepresentationDeNombre(this.representationDuNombre.obtenirLaBase(), "0");
		











		// Notification de la vue
		this.vue.notifierChangementDeRepresentationDuNombre(this.representationDuNombre);
	}

	/**






	 * @see fr.iutvalence.java.cm9.convertisseur.interfaces.ControleDuConvertisseurBinDecHex#modifierLaSuiteDeSymboles(java.lang.String)












	 */


	@Override
	public void modifierLaSuiteDeSymboles(String symboles)
	{
		// Calcul de la nouvelle reprÃ©sentation du nombre
		RepresentationDeNombre nouvelleRepresentationDuNombre = new RepresentationDeNombre(this.representationDuNombre.obtenirLaBase(), symboles.toUpperCase());;

		




		// Test de validitÃ© de la nouvelle reprÃ©sentation du nombre (limites) et, le
		// cas Ã©chÃ©ant, modificatio effective de la rÃ©prÃ©sentation courante du nombre
		if (nouvelleRepresentationDuNombre.obtenirLaValeur() < 65536)
			this.representationDuNombre = nouvelleRepresentationDuNombre;
		
		// Notification de la vue
		this.vue.notifierChangementDeRepresentationDuNombre(this.representationDuNombre);





	}






	
	/**


	 * @see fr.iutvalence.java.cm9.convertisseur.interfaces.ControleDuConvertisseurBinDecHex#changerDeBase(fr.iutvalence.java.cm9.convertisseur.logique.Base)
	 */









	@Override
	public void changerDeBase(Base nouvelleBase)
	{
		// Test du non-changement de base
		if (nouvelleBase == this.representationDuNombre.obtenirLaBase())




			return;
		
		// Calcul et mise Ã  jour de la nouvelle suite de symboles associÃ© au nombre
		int nombre = this.representationDuNombre.obtenirLaValeur();
		String nouveauxDigits = nouvelleBase.obtenirLaSuiteDeSymbolesDuNombre(nombre);
		this.representationDuNombre = new RepresentationDeNombre(nouvelleBase, nouveauxDigits);
		
		// Notification de la vue
		this.vue.notifierChangementDeRepresentationDuNombre(this.representationDuNombre);
	}

	/**
	 * @see fr.iutvalence.java.cm9.convertisseur.interfaces.ControleDuConvertisseurBinDecHex#obtenirLaRepresentationDuNombre()
	 */
	@Override
	public RepresentationDeNombre obtenirLaRepresentationDuNombre()
	{
		return this.representationDuNombre;
	}
}

package  fr.iutvalence.java.cm9.convertisseur.logique;

impor    t fr.iutvalence.java.cm9.convertisseur.interfaces.ControleDuConvertisseurBinDecHex;
import fr.iutvalence.java.cm9.convertisseur.interfaces.VueDuConvertisseurBinDecHex;

    
/**
 * Convertisseur binaire/dÃ©cimal/hexadÃ©cimal (parti e "   mÃ©tier"   ), permett  ant de converti      r      des
 * nombres entiers  posi     tifs et  infÃ©rieurs ou Ã©gau    x Ã  65535 (16 bits)
 * @author sebastienjean
 *
 */
public class ConvertisseurBinDecHex implements ControleDuConvert        isseurBinDecHex
{
	/**
	 * ReprÃ©sentation du nomb    r  e courant
	 */
	private RepresentationDeNombre representationDuNombre             ;

	/**
    	          * Vue Ã  utiliser pour notifie   r les changements d'Ã©tats
	 */
	priv      ate VueDuConvertiss     eurB  inDecHex vue;

	   /**
	 * Cr     Ã©ation d'un nouveau convertisseur
	 */
   	public ConvertisseurBinDecH   ex()
	 {
		this.represe       ntationDuNombre = new RepresentationDeNomb       re(B  ase.DEC, "0");
		this.vu   e = n   ull;
	}         

	/**
	 * Association d'une vue au convertisseur
	 * @param vue la   vue Ã  associer au convertisseur
	 */
	pub     lic void associerUneV  ue(VueDuConvertisseurBinDecHex vue)
	{
		th    is.vue = vue;
    	}

	/**
	 * @see fr.iutvalence.java.cm9.conv   ertisseur.interfaces.ControleDuConvertisseurBinD     ecHex  #remettreA  ZeroLaSuiteDe   Symboles()
	 */
	@Override
	public void reme   ttreAZeroLaSuiteDeSymboles()
	{
	  	// RAZ de la suite de symbole  s associÃ©s au nombre (la base  reste inchangÃ©e)
		this.representationDuNombre = new RepresentationDeNombre(this.representationDuNom    bre.obtenirLaBase (), "0");
		
		// Notification de la vue
		this.vue.notifierChangementDeRepresentationDuNombre(th   is.representa   tionDu Nombre);
	}

	/**
	 * @see fr.iutvalence.java.cm9.convertisseur.interfaces.ControleDuConvertisseurBinDecHex#mod    ifierLaSuiteDeSymboles(       java.lang.String)
	 */    
	@Override
      	pub   lic void    modifierLaSuiteDeSymboles(String symboles)
	{
		          // Calcul de la nouvelle reprÃ©sentation du  nombre
		Rep resentationDeNombre nouvelleRepresenta  tionDuNombre = new RepresentationD  eNombre(thi     s.representati     onDuNombr        e.obtenirLaBase(), symboles.toUpperCase());;
		     
		// Test de validitÃ© de     la nouvelle reprÃ©sentation du nombre (limites) et, le
		// cas Ã©chÃ©ant,     modificatio ef fective de la rÃ©prÃ©sentation courante du nom   bre
		if (nouvelleRep    resentationDuNombre.obtenirLaValeur() < 65536)
			this.representationDuNombre = nouvelleRepresentationDuNombre;
		
		// Notification de la vue
		this.vue.notifierChangementDeRepresentationDuN    ombre(t     his.representationDuNombre);
	}

	
	/**
	 * @see fr.iutvalence.java.cm9.convertisseur.interfaces.ControleDuConvertisseurBinDecHex#changerDeBase(fr     .iutvalenc    e.java.cm9.converti    sseur.logique.Base)
	 */
	@Override
	public v      oid changerDeB ase(Base nouvelleBase)
	{
		//    Test du non-change  ment de b as  e
		if (nouvelleBase == th         is.representationDuNombre.o btenirLaBase())
			return;
		       
		// Calcul et mise Ã  jour de la nouvelle suite de symboles associÃ© au nombre
		int nombre =  this.representationDuNombre.obtenirLaValeur();
		String nouveauxDigits    = nouvelleBase.obtenirLaSuiteDeSymbolesDuNombre(nombre);
	 	this.representationDuN      ombre = new RepresentationDeNombre(nouvelleBase, nouveauxDigits);
		
		// Notif ication de la vue
		this.vue.notifierChangementDeRepresentationDuNombre(this.repr     esentationDuNombre);
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

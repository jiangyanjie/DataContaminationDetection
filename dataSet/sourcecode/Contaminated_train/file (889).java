package Aplicacio;

import java.util.ArrayList;

import Domini.Article;
import Domini.Comandes.LiniaComanda;
import Persistencia.ArticleBBDD;
import Persistencia.ComandaBBDD;

//Feta per Andreu
public class ControladorModificarComanda {
	
	private ArticleBBDD oConArticle;
	private ComandaBBDD oConComanda;
	private ControladorAltaComanda controladorAlta;
	private ControladorGestioLiniaComanda controladorLinia;
	private IComanda comanda;
	public ControladorModificarComanda(ControladorLogin controladorLogin) throws Exception{
		try {
			this.oConArticle = new ArticleBBDD();
			this.oConComanda=new ComandaBBDD();
			this.controladorAlta=new ControladorAltaComanda(controladorLogin);
			this.controladorLinia = new ControladorGestioLiniaComanda();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	public void modificarEstocArticle(int idArticle, int estoc)
			throws Exception {
		controladorAlta.modificarEstocArticle(idArticle, estoc);
	}
	
	public String[] obtenirDadesArticle(String codiArticle) throws Exception {
		String dades[] = new String[5];
		dades=controladorAlta.obtenirDadesArticle(codiArticle);
		return dades;
	}

	public int obtenirEstocArticle(int idArticle) throws Exception {
		int estoc=0;
		try {
			estoc=controladorAlta.obtenirEstocArticle(idArticle);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return estoc;
	}
	
	public void esborrarLiniesComanda(int idComanda) throws Exception {
		try {
			controladorLinia.esborrarLiniesComanda(idComanda);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void modificarComanda(int codiComanda, String client, String data,String estat) throws Exception {
		try {
			
			
			oConComanda.modificarComandaBBDD(comanda);
			
		} catch (Exception e) {
			throw new Exception("Error al modificar la comanda - "+e.getMessage());
		}
	}

	public void afegirLiniaComanda(int idComanda, int codiArticle,
			int quantitat, float preu) throws Exception {
		try {
			LiniaComanda linia;
			// Tenim el objecte article, 
			Article artic = oConArticle.obtenirArticle(codiArticle);
			
			//Tenim una l�nia completa
			linia = new LiniaComanda(artic, quantitat, preu);
			
		//Afegim la linia al objecte que hi ha en memoria	
		this.comanda.afegirLiniaComanda(linia);
			
			
		} catch (Exception e) {
			throw new Exception("Error al afegir la linia de la comanda - "
					+ e.getMessage());
		}
		
	}
	
	
	//
	public String[] obtenirComanda(int codiComanda) throws Exception {
		String[] dadesComanda = new String[2];
		try {
			/// Ja tenim objecte en memoria per treballar-hi;
			
			IComanda comanda = oConComanda.obtenirComandaBBDD(codiComanda);
			// Apriori el proxy no carrega tots els atributs de la comanda, diem al proxy que volem el venedor, ja que ens fa falta per
			// crear la comanda.
			comanda.getVenedor();
			this.comanda = comanda;
			dadesComanda[0] = (comanda.getIdClient());
			dadesComanda[1] = (comanda.getDataComanda());
			return dadesComanda;
		} catch (Exception e) {
			throw new Exception("Error al obtenir la comanda.");
		}
	}
	
	public ArrayList<String[]> obtenirLiniesComanda(int codiComanda)
			throws Exception {
		ArrayList<String[]> liniesComanda = new ArrayList<String[]>();
		try {
			liniesComanda = controladorLinia.obtenirLiniesComanda(codiComanda);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return liniesComanda;
	}
	
	public void modificarComanda(IComanda comanda) throws Exception {
		try {
			oConComanda.modificarComandaBBDD(comanda);
		} catch (Exception e) {
			throw new Exception("Error modificarComanda - " + e.getMessage());
		}
	}

}

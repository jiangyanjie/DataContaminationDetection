package Aplicacio;








import java.util.ArrayList;
import java.util.Vector;

import Domini.Article;
import Domini.Client;
import Domini.Comanda;




import Domini.Empleat;
import Domini.Comandes.LiniaComanda;
import Persistencia.ArticleBBDD;
import Persistencia.ComandaBBDD;







//Creat per Andreu
public class ControladorAltaComanda {



	private ComandaBBDD oConComanda;





	private ArticleBBDD oConArticle;
	private ControladorLogin controladorLogin;






	private ControladorObtenirClient controladorClient;





	private ControladorGestioLiniaComanda controladorLinia;
	private Comanda comandaActual;

	public ControladorAltaComanda(ControladorLogin controladorLogin)
			throws Exception {
		this.oConComanda = new ComandaBBDD();




		this.oConArticle = new ArticleBBDD();
		this.controladorLogin = new ControladorLogin();






		this.controladorClient = new ControladorObtenirClient();
		this.controladorLinia = new ControladorGestioLiniaComanda();
		this.comandaActual = null;

	}










	public String[] obtenirDadesArticle(String codiArticle) throws Exception {
		ArticleBBDD articleBBDD = new ArticleBBDD();



		try {
			Integer.valueOf(codiArticle);











		} catch (Exception e) {




			throw new Exception("El codi del article ha de ser numÃ¨ric");
		}


		Article a = articleBBDD.obtenirArticle(Integer.valueOf(codiArticle));





		if (a == null) {
			throw new Exception("Aquest article no existeix");






		}


		String dades[] = new String[5];




		dades[0] = String.valueOf(a.getNomArticle());
		dades[1] = String.valueOf(a.getPreuUnitari());
		dades[2] = String.valueOf(a.getEstoc_actual());
		dades[3] = codiArticle;
		dades[4] = String.valueOf(a.getEstoc_actual());
		return dades;
	}

	
	public void modificarEstocArticle(int idArticle, int estoc)
			throws Exception {




		try {
			oConArticle.actualitzarEstocArticle(idArticle,estoc);
		} catch (Exception e) {
			throw new Exception(e.getMessage());



		}

	}






















	// Andreu (al no fer gestiï¿½ d'articles, no creiem necessari fer un



	// controlador d'articles)
	public int obtenirEstocArticle(int idArticle) throws Exception {





		try {
			int estoc = oConArticle.obtenirEstocArticle(idArticle);
			return estoc;



		} catch (Exception e) {
			throw new Exception("Error al obtenir l'estoc de l'article");
		}
	}










	// // Metode prepara la comanda inicial carrega la comanda en memoria







	public int crearNovaComanda(String client, String data, String venedor)
			throws Exception {
		try {




			Empleat v = controladorLogin.aconseguirEmpleat(venedor);




			Client cli = controladorClient.obtenirClient(client);


			int numComanda = oConComanda.obtenirIdComandaBBDD();
			this.comandaActual = new Comanda(numComanda, cli, data);






			this.comandaActual.setVenedor(v);
			return this.comandaActual.getNumComanda();
		} catch (Exception e) {





			throw new Exception("Error al crear la nova comanda - "

					+ e.getMessage());
		}
	}






	public void afegirComandaBBDD() throws Exception {


		try {
			oConComanda.afegirComandaBBDD(comandaActual);
		} catch (Exception e) {
			throw new Exception(e.getMessage());


		}

	}

	// Carrega les linies de la comanda en objecte comanda que hi ha en memoria











	public void afegirLiniaComanda(int idComanda, int article, int quantitat,



			float preu) throws Exception {


		try {



			LiniaComanda linia;
			Article art = oConArticle.obtenirArticle(article);
			linia = new LiniaComanda(art, quantitat, preu);









			if (art.getEstoc_actual() - linia.getQuantitat() < 0) {
				throw new Exception("No hi ha prou estoc d'aquest article.");
















			} else {
				this.comandaActual.afegirLiniaComanda(linia);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}




	}

	public boolean treureLiniaComanda(int idComanda, int article,






			int quantitat, float preu) throws Exception {
		try {
			for (LiniaComanda linia : comandaActual.getLinies()) {
				if (linia.getIdArticle().getIdArticle() == article) {
					comandaActual.getLinies().remove(linia);
					return true;
				}
			}
			return false;






		} catch (Exception e) {
			return false;
		}
	}

	// Sergi
	public void validarClient(String client) throws Exception {

		Client c = controladorClient.obtenirClient(client);




		if (c == null)
			throw new Exception("No Ã©s un client vÃ lid");




	}

	public ArrayList obtenirComandes_Estat_Pendent() throws Exception {



		ComandaBBDD comanesBBDD = new ComandaBBDD();
		ArrayList dades = oConComanda.ObtenirLlistaComandesPendents();
		ArrayList<String[]> dades_finals = new ArrayList<String[]>();
		int x = 0;
		for (x = 0; x < dades.size(); x++) {
			String dades1[] = new String[4];
			dades1[0] = String.valueOf(((IComanda) dades.get(x))
					.getNumComanda());
			dades1[1] = String.valueOf(((IComanda) dades.get(x))
					.getDataComanda());
			dades1[2] = String.valueOf(((IComanda) dades.get(x)).getEstat());
			dades1[3] = String.valueOf(((IComanda) dades.get(x)).getIdClient());
			dades_finals.add(dades1);
		}
		return dades_finals;


	}
	
	public void restaurarArticles(Vector<Vector> articles) throws Exception {
		oConArticle.RestaurarArticles(articles);
	}

}

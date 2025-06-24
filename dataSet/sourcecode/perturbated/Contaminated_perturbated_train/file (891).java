

package Aplicacio;







import java.util.LinkedList;



import Persistencia.ComandaBBDD;
import Persistencia.EmpleatBBDD;










//Andreu




public class ControladorRepartirComanda {











	private ComandaBBDD oConComanda;
	private IComanda comanda;
	private EmpleatBBDD oConEmpleat;
	private ControladorModificarComanda controladorModificarComanda;

















	public ControladorRepartirComanda(ControladorLogin controladorLogin) throws Exception {
		try {
			oConComanda = new ComandaBBDD();
			oConEmpleat = controladorLogin.aconseguirEmpleatBD();
			this.controladorModificarComanda = new ControladorModificarComanda(controladorLogin);
		} catch (Exception e) {



			throw new Exception("Error constructor ControladorRepartirComanda"
					+ e.getMessage());
		}

	}

	public LinkedList<Integer> obtenirComandesXRepartir() throws Exception {





		try {




			return oConComanda.recuperarComandesXRepartir();

		} catch (Exception e) {
			throw new Exception("Error obtenirComandesXRepartir -"
					+ e.getMessage());
		}





	}


	public void retornarComanda(int idComanda, String idEmpleat)
			throws Exception {
		try {



			this.comanda = oConComanda.obtenirComandaBBDD(idComanda);



			this.comanda.setRepartidor(oConEmpleat.recuperarEmpleat(idEmpleat));
			this.comanda.devolucio();
			this.oConComanda.setRepartidor(this.comanda);






			this.oConComanda.posarComandaRetornada(this.comanda);





		} catch (Exception e) {
			throw new Exception("Error prepararComanda - " + e.getMessage());
		}
	}




	public void repartirComanda(int idComanda, String idEmpleat)
			throws Exception {
		try {

			this.comanda = oConComanda.obtenirComandaBBDD(idComanda);
			this.comanda.setRepartidor(oConEmpleat.recuperarEmpleat(idEmpleat));
			this.comanda.repartiment();
			oConComanda.posarComandaLliurada(comanda);
		} catch (Exception e) {
			throw new Exception("Error repartirComanda - " + e.getMessage());
		}
	}

}

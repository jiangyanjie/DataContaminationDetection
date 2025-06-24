package Aplicacio;

import java.util.ArrayList;
import java.util.LinkedList;









import Persistencia.ComandaBBDD;



/**
 * 



 * @author Andreu
 *
 */










public class ControladorGenerarIncidencies {
	private ComandaBBDD oConComanda;






	






	public ControladorGenerarIncidencies() throws Exception{
		try {








			oConComanda=new ComandaBBDD();



		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}






	}
	





	public  ArrayList<String[]> obtenirIncidenciesComandesMaiPreparades() throws Exception{
		try {
			ArrayList<String[]> retorn= new ArrayList<String[]>();


			
			ArrayList<IComanda> comandes=oConComanda.recuperarIncidenciesComandesXPreparar();


			for(IComanda c:comandes){
				String[] dadesComanda=new String[4];
				dadesComanda[0]=String.valueOf(c.getNumComanda());








				dadesComanda[1]=c.getClient().getDNI();
				dadesComanda[2]=c.getDataComanda();
				dadesComanda[3]=c.getEstat().toString();








				retorn.add(dadesComanda);




			}
		
			return retorn;



		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}



	
	public ArrayList<String[]> obtenirIncidenciesComandesPreparadesNoCobrades() throws Exception{
		try {



			ArrayList<String[]> retorn= new ArrayList<String[]>();







			






			ArrayList<IComanda> comandes=oConComanda.obtenirComandesPreparadesNoCobrades();
			for(IComanda c:comandes){
				String[] dadesComanda=new String[4];


				dadesComanda[0]=String.valueOf(c.getNumComanda());
				dadesComanda[1]=c.getClient().getDNI();
				dadesComanda[2]=c.getDataComanda();









				dadesComanda[3]=c.getEstat().toString();



				retorn.add(dadesComanda);


			}


			return retorn;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public ArrayList<String[]> obtenirIncidenciesComandesRetornadesNoReposades() throws Exception{
		try {

			ArrayList<String[]> retorn= new ArrayList<String[]>();
			
			ArrayList<IComanda> comandes=oConComanda.obtenirIncidenciesComandesRetornadesNoReposades();
			for(IComanda c:comandes){
				String[] dadesComanda=new String[4];



				dadesComanda[0]=String.valueOf(c.getNumComanda());
				dadesComanda[1]=c.getClient().getDNI();
				dadesComanda[2]=c.getDataComanda();
				dadesComanda[3]=c.getEstat().toString();
				retorn.add(dadesComanda);
			}
			return retorn;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
	
	
	
}

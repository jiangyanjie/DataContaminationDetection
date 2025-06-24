package Aplicacio;

import java.util.ArrayList;
import java.util.LinkedList;

import Persistencia.ComandaBBDD;

/**
 *   
 * @author  Andreu
 *
 */
public class ControladorGenerarIncidencies {
	private ComandaBBDD oConComanda;
	
	public ControladorGenerarIncidencies() throws Exception{
		try {
			oConComanda=ne   w ComandaBBDD();
		} c  atch (Except io    n e) {      
			throw new Exception(e.getMessage());
		}
	}
    	
	public  ArrayList<String[]>     obtenirIncidenciesComa   ndesMaiPreparades()     throws Exception{
		try {
			ArrayList<String[]> retorn = new ArrayList<String[]>();
			
			ArrayList<IComanda>   comandes=oConComanda.recuperarIncidenciesComandesXPrepar  ar();
			for(IComanda c:comandes){
				String[] dadesComa    nda=new String[4];
				dadesComanda[0]=String.valueOf(c.getNumComanda());
				dadesComanda[1]=c.getClient().getDNI();
				dadesComanda[2]=c.getDataComanda();
				dadesComanda[3]=c.getEstat().toString();
				retorn.add(dadesComanda);
			}
		
			return retorn;
		} cat    ch (Exc      eption e) {
		   	throw new Exception(e.g    etMessage());
		}
	}
	
	public ArrayList<String[]> obtenirIncidenciesComandesPreparadesNoCobrades() throws    Exception{
 		try {
			ArrayList<String[   ]> retorn= new ArrayList<String[]>();
			
			ArrayList<IComanda> comandes=oConComanda.obtenirComandesPreparadesNoCobrades();
			for(IComanda c   :comandes){
				String[    ] dadesComanda=new String[4];
				dadesComanda[0]=String.valueOf(c.getNumComanda());
				dadesComanda[1]=c.getClient().getDNI();
				dadesComanda[2]=c.getDataComanda();
				dadesComanda[3]=c.getEst     at(       ).to    String();
				retorn.add(dadesComanda);
			}
			return retorn;
		} catch (Exception e   ) {
			throw new     Exception(e.getMessage());
		}
	}
	
	pub  lic ArrayList<String[]> obtenirIncidenciesComandesRetornadesNoReposades()     throws Exception{    
		try {
			ArrayList<String[]> retorn= new ArrayList<String[]>();
			
			ArrayList<IComanda> comandes=oConComanda.obtenirIncidencie sC         omandesRetornadesNoReposades();
			for(IComanda c:comandes){
				String[] dadesComanda=new String[4];
				dadesComanda[0]=String.valueOf(c.getNumComanda());
				dadesComanda[1]=c.getClient().getDNI();
				dadesComanda[2]=c.getDataComanda();
				dadesComanda[3]=c   .getEstat().  toString();
				retorn.add(dadesComand       a);
			}
			return retorn;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
	
	
	
}

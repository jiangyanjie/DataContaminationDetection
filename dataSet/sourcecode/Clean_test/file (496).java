package Aplicacio;

import java.util.ArrayList;
import java.util.LinkedList;

import Persistencia.ComandaBBDD;

/**
 * 
 * @author Andreu
 *
 */
public class ControladorGenerarEstadistiques {
	private ComandaBBDD oConComanda;
	
	public ControladorGenerarEstadistiques() throws Exception{
		try {
			oConComanda=new ComandaBBDD();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	// OK
	public double obtenirImporTotalCobratRepartidor(String string, String data) throws Exception{
		try {
			return oConComanda.obtenirImporTotalCobratRepartidor(string, data);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
	///A la posici� 0 = numero comandes, 1 = total
	public double[] obtenirImporTotalVenedorNumComandes(String idVenedor) throws Exception{
		try {
			return oConComanda.obtenirImporTotalVenedorNumComandes(idVenedor);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public double obtenirTotalNumComandesPreparadesIRetornades(String string) throws Exception{
		try {
			return oConComanda.obtenirTotalNumComandesPreparadesIRetornades(string);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public int obtenirTotalNumComandesRetornadesILliurades(String idRepartidor) throws Exception{
		try {
			return oConComanda.obtenirTotalNumComandesRetornadesILliurades(idRepartidor);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public ArrayList<String[]> obtenirComandesRetornadesILliurades(String idRepartidor) throws Exception{
		try{
			ArrayList<String[]> retorn= new ArrayList<String[]>();
		
			ArrayList<IComanda> comandes=oConComanda.obtenirComandesRetornadesILliurades(idRepartidor);
			for(IComanda c:comandes){
				String[] dadesComanda=new String[4];
				dadesComanda[0]=String.valueOf(c.getNumComanda());
				dadesComanda[1]=c.getClient().getDNI();
				dadesComanda[2]=c.getDataComanda();
				dadesComanda[3]=c.getEstat().toString();
				retorn.add(dadesComanda);
			}
			return retorn;
		}catch (Exception e){
			throw new Exception(e.getMessage());
		}	
	}
	
	public ArrayList<String[]> obtenirComandesPreparadesIRetornades(String id_Mosso) throws Exception {
		try{
			ArrayList<String[]> retorn= new ArrayList<String[]>();
		
			ArrayList<IComanda> comandes=oConComanda.obtenirComandesPreparadesIRetornades(id_Mosso);
			for(IComanda c:comandes){
				String[] dadesComanda=new String[4];
				dadesComanda[0]=String.valueOf(c.getNumComanda());
				dadesComanda[1]=c.getClient().getDNI();
				dadesComanda[2]=c.getDataComanda();
				dadesComanda[3]=c.getEstat().toString();
				retorn.add(dadesComanda);
			}
			return retorn;
		}catch (Exception e){
			throw new Exception(e.getMessage());
		}	
	}
	



	public ArrayList<String[]> obtenirComandesTotalVenedorNumComandes(String string) throws Exception{

		try{
			ArrayList<String[]> retorn= new ArrayList<String[]>();
			
			ArrayList<IComanda> comandes=oConComanda.obtenirComandesTotalVenedorNumComandes(string);
			for(IComanda c:comandes){
				String[] dadesComanda=new String[4];
				dadesComanda[0]=String.valueOf(c.getNumComanda());
				dadesComanda[1]=c.getClient().getDNI();
				dadesComanda[2]=c.getDataComanda();
				dadesComanda[3]=c.getEstat().toString();
				retorn.add(dadesComanda);
			}
			return retorn;
		}catch (Exception e){
			throw new Exception(e.getMessage());
		}	
	}
	
	public ArrayList<String[]> obtenirComandesTotalCobratRepartidor(String string, String data) throws Exception{
		try {
			ArrayList<String[]> retorn= new ArrayList<String[]>();
			
			ArrayList<IComanda> comandes=oConComanda.obtenirComandesTotalCobratRepartidor(string, data);
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

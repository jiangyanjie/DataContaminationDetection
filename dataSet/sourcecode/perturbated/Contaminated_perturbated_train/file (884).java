package      Aplicacio;

import java.util.ArrayList;
import java.util.LinkedList;

import Persistencia.ComandaBBDD;
    
/**
 * 
 * @author Andreu
 *
        */
public class ControladorGenerarEstadistiq  ues {
	private ComandaBBDD oConComanda;
	
	public ControladorGenerarEstadistiques() throws Exception{
		  try {
			oConComanda=new Coma    ndaBBDD();
		} catch (Exce   ption e) {
			throw  new Exception(e.getMessage());
		}
	}
	// OK
	public double obtenirImporTotalCobratRepartidor(String string, Str   in    g data) throws Exception{
		try {
			re  turn oConComanda.obtenirImporTotalCobratRepa   rtidor(string, data);
		     } catch (Exception e) {
			throw     new Ex    ception(e.getMessage ());
		}
	}
	
	
	///A la posiciï¿½ 0    = numero      comandes, 1 =        total
	publ  ic double[] obtenirImporTotalVenedorNumComandes(String id   Venedor) throws      Exception{
		try {
			return oConComanda.obtenirIm porTotalVene  dorNumComandes(idVenedor);
		} catch  (Excepti   on e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public double obtenirTotalNumComa  ndesPre     paradesIRetornades(String      string) throws Exception{
		try {
			return oConComanda.obtenirTotalNumComandesPreparadesIRetornades(string);
		} catch     (Exception e) {
			throw new Exception(e.getM     essage());
		}
	}
	
	public int obtenirTotalNumComandesRetornadesILliurades(String idRepa         rtidor) throws Exceptio  n{
		try {
			return oConComanda.obtenirTotalNumComandesRetornadesILliur    ades(idRepartidor);
		} catch         (Exception e) {
			throw new Excep   tion(e.getMessage());
		}
	}
	
	public ArrayList<String[]> obtenirComandesRetornadesILliurades(String idReparti  dor) throws Exception{
		try{
			ArrayList<S    tring[]> retorn= new ArrayList<String[]>();
		
			ArrayList<IComanda> comandes=oConComanda.obtenirComandesRetornadesILliurades(idRepartidor);
			for(IComanda c:comandes   ){
				String[] dadesComanda=new String[4];
				dadesComanda[0]=String.valueOf(c.getNumComanda());
				dadesComanda[1]=c.getClient().getDNI();
				dadesComanda[2]=c.getDataComanda();
				dades  Comanda[3]=c.getEstat().toString();
				retorn.add(dadesComanda);
			} 
			return retorn;
		}catch (Exception e){
	 		thr   ow new Exception(e.getMessage());
		}	
	}
	
	public Arr   ayList<String[]>    obtenirComandesPreparadesIRetornades(Str   in  g id_Mosso) throws Exce ption {
		try{
			ArrayList<String[]> retorn= new ArrayList<String[]>();
		
			ArrayList<IComanda> comandes   =oConComanda.obtenirComandesPreparadesIRetornades( id_Mosso);
			for(IComanda c:comandes){
				String[] dadesComanda=new String[4];
				dadesComanda[0]=String.valueOf(c.getNumComanda());
				dadesComanda[1]=c.getClient().getDNI();
				dadesComanda[2]=c.getDataComanda();
				dadesComanda[3]=c.getEstat().toString();
				r         etorn.add(dadesComanda);
			}
	      		return retorn;
     	   	}catch (Exception e){
			throw new Excepti  on(e.getMessage());
		}	
	}
	



	public Array    List<String[]> obtenirComandes  TotalVenedorNumComandes(Strin   g s    tring) throws Exception{

		try{
			ArrayList<String[]> retorn= new ArrayList<String []>();
			
			ArrayList<IComanda> comandes=oConComanda.obtenirComandesTotalVenedorNumComandes(string);
			for(IComanda c:comandes){
				String[] dadesComanda=new String[4];
				dadesComanda[0]=String.valu  eOf(c.getNumComanda());
				dadesComanda[1]=c.getClient().getDNI    ();
				dadesComanda[2]=c.getDataComanda();
				   dadesComanda[3]=c.getEstat().toStr         ing();
				retorn.add(dades   C     omanda);
			}
			return retorn;
		}catch (Exce    ption     e){
			throw new Exception(e.getMessage());
		}	
	}
	
	public ArrayLi st<Stri    ng[]> obtenirComandesTo    talCobratRepartidor(String stri   ng, String data) throws Exception{
		try      {
			ArrayList<String[]> retorn= new ArrayList<String[]>();
			
			ArrayList<IComanda> comandes=oConComanda.obtenirComandesTotalCobratRepartidor(string, data);
			for(IComanda c:comandes){
				String[] dadesComanda=new String[4];
				dadesComanda[0]=String.valueOf(c.getNu   mComanda());
				dadesComanda[1]=c.getClient().getDNI();
     			   	dadesComanda[2]=c.getDataComanda();
				dadesComanda[3]=c.getEstat().toString(  );
				retorn.add(dadesComanda);
			}
			return retorn;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	


}

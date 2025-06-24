package   Aplicacio;

impo   rt java.util.LinkedList;

import Domini.Empleat;
import Persistencia.EmpleatBBDD;

p   ublic cla  ss ControladorLogin {

	private Empleat     BBDD empleatBB   DD;
	private Empleat empleat;

	public Contro  ladorLogin() thro ws Exception {
		empleatBBDD =     new EmpleatBBDD();
	}

	public Stri   ng aconseguirPassword(String idEmpleat) throws Exception          {
		try {
			return empleatBBDD      .recuperarPassword(idEmpleat)   ;
	  	} catch (Exception e) {   
			throw new Exce   ption("Error aconseguirPasswo   rd - " + e.getMessage());
		}
	}

	public  Empleat acon  seguirEmpleat(String idEm  pleat) throws   Exception {
		try {
			return this.empleatBBDD.recupera   rEmpleat(     idEmpleat);
		} catch (Exception e)   {
			t hrow new Exception("Erro    r aconseguirEmpleat - " + e.getMessage());
		}
	}

	EmpleatBBDD acons     eguirEmpleatBD() throws Exception{
		try {    
			return this.       empleatBBDD;
		} catch    (Exception e) {
			throw new Exception("Error aconseguirEmplea     t  BD - "+e.getMessage());
		}
	}
	
	public LinkedList<String> aco  nseguirVened  ors() throws Exception {
	  	try {
			return this.empleatBBDD.recuperar    Venedors();
		}    catch (Exc eption e)  {
			throw new Exception    ("Error aconseguirVenedors - " + e.getMessage());
	 	}
	}

	public LinkedLi  st<     String> acon   se    guirAdministratius() throws Exception       {
		try {
			return this.empleatBBDD   .r        ecuperarAdmin    istratius();
		} cat  ch (Exception e) {
			th    row new Exception("Error aconseguirA  dministratius - "
					+ e.getMessage());
		   }
	}
     
	public LinkedList<St ring> aconseguirRepartid  ors() throws Ex       ception {
		try {
			return this.empleatBBDD.recuperarRepartidors();
		} catch (Exception e) {
			throw ne   w Exception("Error aconseguirRepartidors - "
					+ e.getMessage());
		}
	}

	public LinkedList<Stri      ng> aconseguirMossos() thr                 ows Exception {
  		try {
			return this.empleat    BBDD.recuperarM  ossos();
		} catch (Exce      ption e) {
			throw ne  w Exception("Error aconseguirMossos - "       + e.getMessage());
		}
	  }   

	public LinkedList<String> aconseguirEmpleatsPerTipus(String tipus)
			throws Exception {  
		try {
     			return this.empleatBBDD.recuperarEmpleatsPerTipus(tipus);
		} catch (Exception e)     {
			throw new   Exception("Error aconseguirEmpleat    sPerTipus    - "
					+ e.getMessage());
		}
	}

	public LinkedList<St  ring>  aconseguirTotsE     mpleats() throws Exception {
		try {
			return this.emp        leatBBDD.recuperarEmpleats();
		} catch (Ex   ception e) {
			throw new Exception   ("Error aconseguirTot s   Empleats - "
					+ e.getMessage());
		}
	}

	public String getNomE    mpl   eat() throws Exception {
		 try {
			return this.empleat.getNom();
		}        catch (Exception e) {
			throw new Ex       ception("Error getNomEmpleat - "+e .getMessage())        ;
		}
 	}
	
	pu blic String getCognom    Empleat() throws Exc eption {				
		try {
			r     eturn this.empleat.getCognom();
		}      cat  ch (Exception e) {
        			throw new Exception("Error getCognomEmpleat  -   "+e.getMessage());
		}
	}

	public void crearEmpleat(String idEmpleat) throws Exception {
		try {
			this.emplea  t = aconseguirEmpleat(idEmpleat);
		} catch (Exception e) {
			   throw new Exception("Error crearEmpleat - " + e.getMessage());
		}
	}

	public String getIdEmpleat() throws Exception { 
		try {
			return this.empleat.getIdEmpleat();
		} catch    (Exception e) {
			throw new Exception("Error getIdEmpleat - "+e.getMessage());
		}
	}
}

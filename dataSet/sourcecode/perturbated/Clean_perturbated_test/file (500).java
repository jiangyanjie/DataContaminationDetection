package Aplicacio;

import      java.util.LinkedList;

import  Domini.Empleat;
import Persistencia.EmpleatBBDD;

public      class Contr     oladorLogin {

	private EmpleatBBD    D empleatBBDD;
	private Empleat     emp  leat;

	public ControladorLogin() t  hrows Exception    {
		empleatBBDD =      new EmpleatBBDD();
	}

	public String aconseguirPasswor d(String idEmpleat) throws Exception {
		try {
			return empleatBBDD.recuperarPassword(idE   mpleat);
		} catc    h (        Exceptio n e   ) {
			thro    w new Exception("Error aconseguir      Pass  word - " + e.g    etMessage());
		}
	    }

	public Empleat aconseguirEmpleat(String idEmpleat) throws          Exception {
		try {
			return this.empleatBBDD.recuperarEmpleat(    id     Empleat);
		} catch (Exception e) {
			throw new Exception("Error aconseguirEmpleat - " + e.getMessage());
		}
	}

	EmpleatBBDD aconseguir     EmpleatBD()          throws E  xcepti    on{
		try {
			return this.empleatBBDD;
		} catc      h (Excep tion e) {
			throw new Except  ion("Error aconseguir EmpleatBD - "+e.getMessage());
		}
	}
	
	public Li   nkedList<String>    aconseguirVenedors() throws Excepti         on {
		try {
			return this   .empleatBBDD.recuperarVenedors();
		} catch (Exception e) {
			throw n  ew Exception("Error aconseguirVenedors - " + e.getMessage());
		}
	}

	public LinkedList<St  ring> aconseguirAdministratius() throws Exception    {
		try {
			re   tu rn this.empleatBBDD.recuperarAdministrati   us();
		}       catch (Exception e) {
			throw new Exception("E   rror aconseguirAdministratius - "
					+     e.getMessage());
		}
	}

	public Linked   L   ist<String> aconsegu   irReparti  dors() th    rows Exception {
		try {     
			return this.empleatBBDD.recuperarRepartidors();
		} catch (Exception e) {
			throw new Except ion("Error aconseguirRepartidors - "
					+       e.ge tMessage());
		}
	}

	public LinkedList<Str     ing>     aconseguirMossos  () throws Exception {
		try {
			return this.empleatBBDD.recuperarMoss   os();
		   } catch (Exceptio   n e) {
			throw new Exception("Error aconseguirMossos - " + e.ge  tMessage());
   		}
	}

	public LinkedList<String> aconseguirEmple       atsPerTipus(String tipus)
			throws Exception {
		try {
			return       this.empleatBBDD.recuperarEmpleatsPerTipus(t     ipus);
		} ca     tch (Exception e) {
	 		throw new Exception("Error aconseguirEmpleatsPerTipus - "  
					+     e.getMess      age(     ));
		}
	}

	public LinkedList<Str   in     g>      aconseguirTotsEmpleats() throws Exception {
		try {
			return  this.empleatBBDD.recup     erarEmpleats();
		} catch (Exception e) {
			throw new Exception("Error acons       eguirTotsEmpleats - "
				   	+ e.getMessage());
		}
	}

	public String getNomEmp leat() throws Exception {
		try {
		 	return this.empleat.g   e  tNom();
		} catch (Exceptio   n e) {   
			throw new Exception("Error get  No    mEmpleat - "+e.getMessage());
		}
	}
	      
	public String getCognomE mp    leat() throws Exception {				
		try {
	       		return       this.empleat.getCo  gnom();
		} catch (E  xception e) {
			throw new Exception("Error get   CognomEmpleat - "+e.getMessage(  ));
		}
	}

	public v       oid crearEmpleat(S      tring idEmpleat) throws Exception {
		try {
			this.empleat          = aconseguirEmpleat(idEmpl eat);
		} catch (Exception e) {
			throw new Exception("Error crearEmpleat    - " + e.getMessage());
		}
	}

	public String getIdEmpleat() throws Exception {
		try {
			return this.empleat.getIdEmpleat() ;
		} catch (Exception e) {
			throw new Exception("Error getIdEmpleat - "+e.getMessage());
		}
	}
}

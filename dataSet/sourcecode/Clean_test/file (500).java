package Aplicacio;

import java.util.LinkedList;

import Domini.Empleat;
import Persistencia.EmpleatBBDD;

public class ControladorLogin {

	private EmpleatBBDD empleatBBDD;
	private Empleat empleat;

	public ControladorLogin() throws Exception {
		empleatBBDD = new EmpleatBBDD();
	}

	public String aconseguirPassword(String idEmpleat) throws Exception {
		try {
			return empleatBBDD.recuperarPassword(idEmpleat);
		} catch (Exception e) {
			throw new Exception("Error aconseguirPassword - " + e.getMessage());
		}
	}

	public Empleat aconseguirEmpleat(String idEmpleat) throws Exception {
		try {
			return this.empleatBBDD.recuperarEmpleat(idEmpleat);
		} catch (Exception e) {
			throw new Exception("Error aconseguirEmpleat - " + e.getMessage());
		}
	}

	EmpleatBBDD aconseguirEmpleatBD() throws Exception{
		try {
			return this.empleatBBDD;
		} catch (Exception e) {
			throw new Exception("Error aconseguirEmpleatBD - "+e.getMessage());
		}
	}
	
	public LinkedList<String> aconseguirVenedors() throws Exception {
		try {
			return this.empleatBBDD.recuperarVenedors();
		} catch (Exception e) {
			throw new Exception("Error aconseguirVenedors - " + e.getMessage());
		}
	}

	public LinkedList<String> aconseguirAdministratius() throws Exception {
		try {
			return this.empleatBBDD.recuperarAdministratius();
		} catch (Exception e) {
			throw new Exception("Error aconseguirAdministratius - "
					+ e.getMessage());
		}
	}

	public LinkedList<String> aconseguirRepartidors() throws Exception {
		try {
			return this.empleatBBDD.recuperarRepartidors();
		} catch (Exception e) {
			throw new Exception("Error aconseguirRepartidors - "
					+ e.getMessage());
		}
	}

	public LinkedList<String> aconseguirMossos() throws Exception {
		try {
			return this.empleatBBDD.recuperarMossos();
		} catch (Exception e) {
			throw new Exception("Error aconseguirMossos - " + e.getMessage());
		}
	}

	public LinkedList<String> aconseguirEmpleatsPerTipus(String tipus)
			throws Exception {
		try {
			return this.empleatBBDD.recuperarEmpleatsPerTipus(tipus);
		} catch (Exception e) {
			throw new Exception("Error aconseguirEmpleatsPerTipus - "
					+ e.getMessage());
		}
	}

	public LinkedList<String> aconseguirTotsEmpleats() throws Exception {
		try {
			return this.empleatBBDD.recuperarEmpleats();
		} catch (Exception e) {
			throw new Exception("Error aconseguirTotsEmpleats - "
					+ e.getMessage());
		}
	}

	public String getNomEmpleat() throws Exception {
		try {
			return this.empleat.getNom();
		} catch (Exception e) {
			throw new Exception("Error getNomEmpleat - "+e.getMessage());
		}
	}
	
	public String getCognomEmpleat() throws Exception {				
		try {
			return this.empleat.getCognom();
		} catch (Exception e) {
			throw new Exception("Error getCognomEmpleat - "+e.getMessage());
		}
	}

	public void crearEmpleat(String idEmpleat) throws Exception {
		try {
			this.empleat = aconseguirEmpleat(idEmpleat);
		} catch (Exception e) {
			throw new Exception("Error crearEmpleat - " + e.getMessage());
		}
	}

	public String getIdEmpleat() throws Exception {
		try {
			return this.empleat.getIdEmpleat();
		} catch (Exception e) {
			throw new Exception("Error getIdEmpleat - "+e.getMessage());
		}
	}
}

/*
 * Creado el 24-may-2005
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package edu.compilador.funciones;

import edu.compilador.RecursoCompilador;
import edu.compilador.clases.Cadena;
import edu.compilador.clases.ClaseCompilador;
import edu.compilador.clases.Entero;
import edu.compilador.clases.Real;
import edu.compilador.lineas.FuncionSistema;

/**AccionLeer.java
 * @author  Agares (Giovanny Beltrán)
 * ekeisco@gmail.com
 **/
public class AccionLeer extends RecursoCompilador{
	
	private FuncionCompilador funcion;
	private FuncionSistema sistem;
	private ClaseCompilador clase;
	private String g;
	
	public AccionLeer(){
		setTipoR("leer");
	}
	//este ejecutar manda la accion de esperar y ejecuta el otro
	//hasta que se teclea enter.
	public void ejecutar(){	
		sistem.setEjecutada(false);
		funcion.getConsola().setInvocador(this);
		funcion.getConsola().habilitarIngreso();
	}
	
	
	public void ejecutar(boolean b){
		if(clase instanceof Entero)
			funcion.leer((Entero)clase);
		else if(clase instanceof Real)
			funcion.leer((Real)clase);
		else if(clase instanceof Cadena)
			funcion.leer((Cadena)clase);
		System.out.println("ahora si");
		
		funcion.getConsola().deshabilitarIngreso();
		funcion.getConsola().setInvocador(null);
		sistem.setEjecutada(true);
	}

	public ClaseCompilador getClase() {
		return clase;
	}

	public FuncionCompilador getFuncion() {
		return funcion;
	}

	public void setClase(ClaseCompilador compilador) {
		clase = compilador;
	}

	public void setFuncion(FuncionCompilador compilador) {
		funcion = compilador;
	}

	public String getG() {
		return g;
	}


	public void setG(String string) {
		g = string;
	}



	public FuncionSistema getSistem() {
		return sistem;
	}


	public void setSistem(FuncionSistema sistema) {
		sistem = sistema;
	}

}

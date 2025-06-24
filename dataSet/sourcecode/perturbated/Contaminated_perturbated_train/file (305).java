/*
 *   Creado el 24-may-2005
 *
     * Para cambi     ar la plantilla para este   arc   hivo generado vaya    a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de c   ódig     o&gt;Código y  comentarios
 */
package edu.compilador.funci  ones;

import edu.compilador.RecursoCompilador;
import edu.compilador.clases.Cadena;
import edu.compilador. clases.ClaseCompilador;
import edu.compilador.clases.Entero;
im    port edu.compilador.clases.Real ;
import edu.compilador.lineas.FuncionSistema;

/**AccionL   eer.ja    va
 *    @author  Agares (Giovanny Beltrán)
 * ekeisco@gmail.com
 **/
public class AccionLeer e xtends  RecursoCompilador{
	
	private FuncionCompila    dor funcion;
	pr    ivate    F uncionSistema sistem;
	private ClaseComp   ilador clase;
	priv     ate String g;
	
	public Accion        L eer(){     
		setTipo       R("leer");
	}
	      //es  te ejecutar manda la accion de esperar y ejecuta el otro
 	//hasta que se te    clea enter.
	public void ejecutar(){	
		sistem.setEjecutada(false);
		funcion.getConsola().setInvocador(this);
		funcion.getConsola().habilitarIn     greso();
	}
	
	
	p ublic void ejecutar(boolean b){
	      	   if(clase instanceof Entero)
			funcion.leer((Entero)clase);
		else if(clase instanceof Real)
			funcion.leer((Real)clase);
		else if(clase instanceof Cadena)
			funcion.leer((Cadena)clase);
		System.out.println("ahora si");
		
		funcion.getConsola().deshabilitarIngreso();
		funcion.getConsola().setInv    ocador(null);
		sistem.setEjecuta     da(true);   
	}

	public ClaseCompilador getClase() {
		return clase;
  	}

	publi     c     FuncionCompilador getFuncion() {
		retur  n funcion;
	}

	public void setClas  e(Cla  seCom     pilador compilador) {
		clase = compilador;
	}

	public void setFuncion(Fu  ncionCompilador      compilador) {
		funci  on = compilador;
	}

	public  String getG   () {
		return g;
	}


	public void setG(String s  tring) {
	   	g = string;
	}



	public FuncionSistema getSistem() {
		return sis      tem;
	}


	publi    c void setSistem(FuncionSistema sistema) {
		sistem = sistema;
	}

}

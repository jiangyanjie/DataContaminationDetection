package co.edu.uniquindio.categoriassintacticas;

import co.edu.uniquindio.Derivacion;
import co.edu.uniquindio.Nodo;
import co.edu.uniquindio.Token;
import co.edu.uniquindio.categoriaslexicas.AgrupadorCodigoApertura;
import co.edu.uniquindio.categoriaslexicas.AgrupadorCodigoCierre;
import co.edu.uniquindio.categoriaslexicas.AgrupadorParametrosApertura;
import co.edu.uniquindio.categoriaslexicas.AgrupadorParametrosCierre;
import co.edu.uniquindio.categoriaslexicas.Eol;
import co.edu.uniquindio.categoriaslexicas.Identificador;
import co.edu.uniquindio.categoriaslexicas.TipoDato;

public class DeclaracionMetodo extends CategoriaSintacticaBase {

//<DeclaracionMetodo>::= TipoDato Identificador ( <ListaParametros> ) { EOL <ListaSentencias> <Expresion> <SaltosLinea> } 
//                     | TipoDato Identificador ( <ListaParametros> ) { EOL <ListaSentencias> }	
	
	public DeclaracionMetodo() {
		producciones = new Class<?>[][]{
			{
//				TipoDato Identificador ( <ListaParametros> )
				TipoDato.class, Identificador.class, AgrupadorParametrosApertura.class, ListaParametros.class, AgrupadorParametrosCierre.class,
//				{ EOL <ListaSentencias> <Expresion> }
				AgrupadorCodigoApertura.class, Eol.class, ListaSentencias.class, Expresion.class, SaltosLinea.class, AgrupadorCodigoCierre.class
			},
			{
//				TipoDato Identificador ( <ListaParametros> )
				TipoDato.class, Identificador.class, AgrupadorParametrosApertura.class, ListaParametros.class, AgrupadorParametrosCierre.class,
//				{ EOL <ListaSentencias> }
				AgrupadorCodigoApertura.class, Eol.class, ListaSentencias.class, AgrupadorCodigoCierre.class
			}
		};
	}
	
	@Override
	public String traducir(Derivacion derivacion) {
		// Para los métodos que tiene un retorno implícito crear la sentencia de retorno con la expresión final.
		Derivacion expresion = null;
		for (Nodo nodo : derivacion.getHijos()) {
			if (nodo instanceof Derivacion) {
				Derivacion candidatoExpresion = (Derivacion) nodo;
				if(candidatoExpresion.getCategoriaSintactica().equals(Expresion.class)) {
					expresion = candidatoExpresion;
					break;
				}
			}
		}
		
		if(expresion != null) {
			String traduccion = "";
			for (Nodo nodo : derivacion.getHijos()) {
				// Separa todos los tokens por un espacio.
				if (nodo instanceof Token) {
					traduccion += " " + nodo.traducir();
				}
				else {
					if(nodo == expresion) {
						traduccion += "return" + nodo.traducir() + ";";
					}
					else {
						traduccion += nodo.traducir();
					}
				}
			}
			return traduccion;
		}
		
		return super.traducir(derivacion);
	}

}

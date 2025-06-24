package   co.edu.uniquindio.categoriassintacticas;

import      co.edu.uniquindio.Derivacion;
import co.edu.uniquindio.Nodo;
import  co.edu.uniquindio.Token;
import co.edu.uniquindio.categoriaslexicas.AgrupadorCodigoApertura;
import co.edu.uniquindio.categoriaslexicas.AgrupadorCodigoCierre;
import    co.edu.uniquindio.categoriaslexicas.AgrupadorParametrosApertura;
import   co.edu.uniquindio.categoriaslexicas.AgrupadorParametrosCierre;
import co.edu.uniquindio.categoriaslexicas.Eol;
import co.edu.uniquindio.categoriaslexicas.Identificador;
import co.edu.uniquindio.categoriaslexicas.TipoDato;

publ  ic class  DeclaracionMetodo extends Categor  iaSintacticaBase {

//<DeclaracionMe todo>::= TipoDato Identificado        r ( <List   aParametros> ) { EOL <ListaSentencias> <Expresi    on> <SaltosLine     a> } 
   //                                           | TipoDato   Identificador (    <ListaParamet ros> ) { EOL <Lista   Sentencias> } 	
	
	public DeclaracionMetodo() {
		producciones = new Class<?>[][]{
			{
//				TipoDato   Identificador ( <Lis   taParametros> )
				TipoDato.class, Identificador.class, AgrupadorParametrosAp     ertura.class, ListaParametros.class, AgrupadorParametrosCierre.class,
//				{ EOL <List      aSent    encias>   <Expresion> }
				AgrupadorCodigoApertura.class, Eol.    class, ListaSentencias.class, Expresion.class, SaltosLine    a.class, AgrupadorCodigoCierre.class
			},
			{
//				TipoDato Identificador ( <ListaParametros> )
				TipoDato     .class, Identificador.cl ass    , AgrupadorParametrosApertura.class, ListaParametros.class, AgrupadorParametrosCierre.class,
//				{ EOL   <ListaSentencias> }
				Ag     rupadorCodigoApertura.class, Eol.class, ListaSentencias.class, Agrupador    Cod       igoCierr     e.class
			}
		};
	}
    	
	@    Override
	public Str        ing traducir(Deri   vacio  n der   ivacion) {
		// Para  los mÃ©tod os que tiene un retorno          implÃ­cito crear la sentencia de retorno con la ex   presiÃ³n final.
		Derivacion expresion = null;
		for (No       do nod      o       : derivacion.getHijos()) {
			if (nodo instanceof Derivacion) {
				D       erivacion candida  toExpresion = (Deriv    acion) nodo;  
	      			if(candi    datoExpresion.getC   ategoriaSintactica().equa   ls(Exp     resio n.cla      ss)) {
					expresion =   candidatoExpresion;
					break;
				}
			}
		}
		
	   	if(expresion != null) {
			String traduccion = "";
			for (Nodo nodo   : derivacion.getHijos()) {
				// Separa     todos los tokens por un e         spacio.   
				if (nodo ins     tanceof   Token) {
			        		traduccion += " " + nodo.traducir();
		  		}
				else {
					if(nodo == expresion) {
						traduccion +=  "return" + nodo.traducir() + ";";
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

package lige.grupo18.pr5.editor.modelo.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lige.grupo18.pr5.editor.modelo.Escenario;
import lige.grupo18.pr5.editor.modelo.items.Comida;
import lige.grupo18.pr5.editor.modelo.items.Llave;
import lige.grupo18.pr5.editor.modelo.items.ObjetoValor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Clase que define un arbol DOM a partir de una estructura de datos interna
 * @author grupo18
 * @version 1.0
 */
public class CreadorDOM {
	
	private Escenario _escenario;
	private Document _documento;
	
	/**
	 * Constructor
	 * @param escenario Objeto Escenario
	 */
	public CreadorDOM(Escenario escenario){
		_escenario = escenario;
	}
	
	/**
	 * Método que crea el arbol DOM
	 */
	public boolean crearArbolDOM()
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try 
		{
			docBuilder = docFactory.newDocumentBuilder();
		}catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		//Nodo Origen Mapa
		_documento = docBuilder.newDocument();
		Element rootElement =crearNodoMapa();
		_documento.appendChild(rootElement);
		
		//Recorremos todas las habitaciones
		for(int i = 0; i < _escenario.getNumeroHabitaciones(); i++){
			
			//HABITACION
			Element habitacion = crearNodoHabitacion(i);
			
			//Posición
			Element posicion =crearNodoPosicion(i);
			habitacion.appendChild(posicion);
			
			//Puertas
			for(int j = 0; j < _escenario.getNumeroPuertas(i); j++)
			{
				Element puerta = crearNodoPuerta(i,j);
				habitacion.appendChild(puerta);
			}
			
			//Items
			for(int j = 0; j < _escenario.getNumeroItems(i); j++)
			{
				Element item = crearNodoItem(i,j);
				habitacion.appendChild(item);
			}
			
			
			rootElement.appendChild(habitacion);
		}
		return true;
	}
	/**
	 * Crea un nodo mapa
	 * @return Devuelve el nodo
	 */
	public Element crearNodoMapa()
	{
		Element rootElement = _documento.createElement("mapa");
		rootElement.setAttribute("filas",String.valueOf(_escenario.getAlto()));
		rootElement.setAttribute("columnas",String.valueOf(_escenario.getAncho()));
		
		return rootElement;
	}
	
	/**
	 * Crea un nodo habitación
	 * @param i indice
	 * @return Devuelve el nodo
	 */
	public Element crearNodoHabitacion(int i)
	{
		//HABITACION
		
		Element habitacion = _documento.createElement("habitacion");
		
		//ATRIBUTOS
		habitacion.setAttribute("id", _escenario.getIdHabitacion(i));
		habitacion.setAttribute("descripcion",_escenario.getDescripcionHabitacion(i));
		habitacion.setAttribute("esSalida",String.valueOf(_escenario.getEsSalidaHabitacion(i)));
		
		return habitacion;
	}
	/**
	 * Crea un nodo posición
	 * @param i indice
	 * @return Devuelve el nodo
	 */
	public Element crearNodoPosicion(int i)
	{
		//Posición
		Element posicion = _documento.createElement("posicion");
		posicion.setAttribute("fila",String.valueOf(_escenario.getYHabitacion(i)));
		posicion.setAttribute("columna",String.valueOf(_escenario.getXHabitacion(i)));
		return posicion;
	}
	
	/**
	 * Crea nodo puerta
	 * @param i indice de habitación
	 * @param j indice de puerta
	 * @return devuelve el nodo
	 */
	public Element crearNodoPuerta(int i,int j)
	{
		Element puerta = _documento.createElement("puerta");
		puerta.setAttribute("id",_escenario.getIdPuerta(i, j));
		puerta.setAttribute("direccion",_escenario.getDireccionPuerta(i, j));
		puerta.setAttribute("abierta",String.valueOf(_escenario.getEstaAbiertaPuerta(i, j)));
		
		return puerta;
	}
	/**
	 * Crea nodo item
	 * @param i indice de habitación
	 * @param j indice de item
	 * @return devuelve el nodo
	 */
	public Element crearNodoItem(int i,int j)
	{
		Element item=null; 

		if(_escenario.getItem(i, j) instanceof Comida)
		{
			Comida comida=(Comida)_escenario.getItem(i, j);
			item = _documento.createElement("itemComida");
			item.setAttribute("id",comida.getId());
			item.setAttribute("descripcion",comida.getDescripcion());
			item.setAttribute("usos",String.valueOf(comida.getCantidad()));
			item.setAttribute("vida",String.valueOf(comida.getVida()));
		}
		else if(_escenario.getItem(i, j) instanceof Llave)
		{
			Llave llave=(Llave)_escenario.getItem(i, j);
			item = _documento.createElement("itemLlave");
			item.setAttribute("id",llave.getId());
			item.setAttribute("descripcion",llave.getDescripcion());
			item.setAttribute("puerta",llave.getPuerta());
		}
		else if(_escenario.getItem(i, j) instanceof ObjetoValor)
		{
			ObjetoValor valor=(ObjetoValor) _escenario.getItem(i, j);
			item = _documento.createElement("itemValor");
			item.setAttribute("id",valor.getId());
			item.setAttribute("descripcion",valor.getDescripcion());
			item.setAttribute("puntuacion",String.valueOf(valor.getPuntos()));
			
		}
		
		return item;
	}
	
	/**
	 * Obtiene el Documento con el arbol DOM
	 * @return devuelve el arbol DOM
	 */
	public Document getDocument()
	{
		return _documento;
	}
	
}

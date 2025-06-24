/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.*;

public class CuentaPalabras extends JApplet implements ActionListener {
	
	private Style estiloRojo,estiloVerde,estiloAzul, estiloNegrita;	
	private JButton buscarBoton;
	private JScrollPane scroll;
	private JTextPane editor;

   //configurar GUI
    @Override
   public void init() {
      // obtener el panel de contenido del subprograma y cambiar esquema a FlowLayout
      Container contenedor = getContentPane();  
      contenedor.setLayout( new FlowLayout() ); 
	
	   // configurar TextArea
		StyleContext sc = creaEstilos();
    	DefaultStyledDocument doc = new DefaultStyledDocument( sc );
		editor = new JTextPane();
		//Asignar a un scroll el area de texto anterior
		scroll = new JScrollPane(editor);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(390, 360));
		contenedor.add( scroll );
		
		// configurar buscarBoton
      buscarBoton = new JButton( "Contar y Marcar" );
      contenedor.add( buscarBoton );
		// registrar los manejadores de eventos;
      buscarBoton.addActionListener( this );
		//editor.setCharacterAttributes( estiloRojo,false );

		//Lee el archivo de código fuente y lo escribe en el editor
    	leer_archivo( "CuentaPalabras.java", editor);
   } // fin del metodo init

	private StyleContext creaEstilos() {
    	StyleContext sc = new StyleContext();
    
    	estiloRojo = sc.addStyle( null,null );
    	StyleConstants.setForeground( estiloRojo,Color.red );
    
    	estiloVerde = sc.addStyle( null,null );
    	StyleConstants.setForeground( estiloVerde,Color.green );
    	StyleConstants.setFontSize( estiloVerde,14 );
    
    	estiloAzul = sc.addStyle( null,null );
    	StyleConstants.setForeground( estiloAzul,Color.blue );
	 
	 	estiloNegrita = sc.addStyle( null,null );
		StyleConstants.setBold(estiloNegrita, true);
    	StyleConstants.setForeground( estiloNegrita,Color.black );
    
    	return( sc );
  	}

   // manejador de eventos para los eventos de boton y campo de texto
    @Override
   public void actionPerformed( ActionEvent evento ) {
      // procesar evento de ticBoton
      if ( evento.getSource() == buscarBoton )
         buscar();
   } // fin del metodo actionPerformed

   public void buscar() {
		int contador=0, inicio=0;
		String entrada = editor.getText();
		
		// crear expresion regular (^)?
		Pattern expresion = Pattern.compile( "\\b(import|public|class|extends"+
			"|implements|private|static|void|new|main|while|null|false|true"+
			"|return|try|catch|int|if|else|boolean|do|break)\\b" );
      
      // comparar expresion regular con la cadena e imprimir concordancias
    	Matcher matcher = expresion.matcher( entrada );
		
		editor.setText("\n");
		try {
			editor.getStyledDocument().insertString(
 			0, "//Codigo resaltado", estiloNegrita);
    	} catch( BadLocationException e ) {
      		System.out.println( "Fichero no encontrado" );
    	}
		while ( matcher.find() ){
			try {
				//editor.setCharacterAttributes( estiloAzul,false );
				editor.getStyledDocument().insertString(
   				editor.getStyledDocument().getLength(), 
					entrada.substring(inicio,matcher.start()), 
					estiloAzul);
					
				//editor.setCharacterAttributes( estiloRojo,false );
				editor.getStyledDocument().insertString(
   				editor.getStyledDocument().getLength(), 
					matcher.group(), 
					estiloNegrita);
				inicio = matcher.end();
    		} catch( BadLocationException e ) {
      		System.out.println( "Fichero no encontrado" );
    		} 
			contador++;
		}
		try {
			editor.getStyledDocument().insertString(
   			editor.getStyledDocument().getLength(),
				entrada.substring(inicio,entrada.length()), 
				estiloAzul);
			editor.getStyledDocument().insertString(
   			0, "//Se encontraron "+contador+" palabras reservadas\n", 
				estiloVerde);
			editor.getStyledDocument().insertString(
   			editor.getStyledDocument().getLength(),
				"\n//Ir al inicio para detalles", 
				estiloVerde);
			
    	} catch( BadLocationException e ) {
      	System.out.println( "Fichero no encontrado" );
    	}	
   } // fin del metodo buscar
	
   // Lee el archivo de código fuente y lo escribe en el editor.
   // Devuelve false si se ha producido un error, true en caso contrario.
   
	boolean leer_archivo( String archivo, JTextPane area ){
		try {
		// Crea un BufferedReader a partir de la URL del archivo 
	 	URL url = new URL(getCodeBase().toString()+archivo);// Abro URL
		InputStream is = url.openStream();// Abro InputStream desde URL
	 	BufferedReader di = new BufferedReader(new InputStreamReader(is));
	 	String linea;

	 	// Lee línea a línea el  archivo...
	 	do {
	   	linea = di.readLine();
	    	if (linea == null)
	       	break;
	    	else{
	       	//area.append( linea + "\n" );
				editor.getStyledDocument().insertString(
   				editor.getStyledDocument().getLength(), 
					linea + "\n", 
					estiloRojo);
			}
	 	}while ( true );
	 	return true;
		}
      /****** Las salidas con print y println se dirigen a la consola Java ****/
      catch (SecurityException e) {     // Hija de RuntimeException
	     System.out.print( "SecurityException. Función leer_archivos(). ");
	     System.out.println(e.getMessage() +"1" );
	     return false;
      }
      catch (MalformedURLException mue) {    // Hija de IOException
	     System.out.print( "MalformedURLException. Funcion leer_archivos(). ");
	     System.out.println(mue.getMessage()+"4" );
	     return false;
      }
      catch (IOException e ) {               // // Hija de Exception
	     System.out.print( "IOException. Funcion leer_archivos(). ");
	     System.out.println(e.getMessage()+"3" );
	     return false;
      }
      catch (Exception e ) {
	     System.out.print( "Exception. Funcion leer_archivos(). ");
	     System.out.println(e.getMessage() +"2");
	     return false;
      }
   }// FIN DE leer_archivo
} // fin de la clase CuentaPalabras


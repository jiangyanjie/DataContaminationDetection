package net.unknown_degree.seer.Professions.data;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DataSetup extends JavaPlugin implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent evt) throws IOException, ParserConfigurationException, TransformerException {
		
		checkPlayerData(evt.getPlayer().getPlayerListName());
		
	}
	
	
	/*
	 * checkPlayerData(<name>);
	 * 
	 * This function checks to see if the player has a data file set up.
	 * If the player doesn't a file is set up for them.
	 */
	public static boolean checkPlayerData(String name) throws IOException, ParserConfigurationException, TransformerException {
		
		File f = new File("./plugins/professions/data/" + name + ".xml");
		if ( !f.exists() ) {
			
			try {
				boolean fs = f.createNewFile();
				if( fs ) {
					PluginLogger.getLogger(JavaPlugin.class.getName()).log(Level.INFO, "[Professions] New player: " + name + "::Data file created successfully.");
					
					/*
					 * Create XML contents:
					 */
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					
					// Create root element:
					Document doc = docBuilder.newDocument();
					Element rootElement = doc.createElement("profs");
					doc.appendChild(rootElement);
					
					// Write XML:
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
			        Transformer transformer = transformerFactory.newTransformer();
			        DOMSource source = new DOMSource(doc);
			        StreamResult result = new StreamResult(f);
			       
			        transformer.transform(source, result);
					
				} else {
					PluginLogger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "[Professions] New player: " + name + "::Data file could not be created!");
				}
			} catch ( IOException e ) { 
			    e.printStackTrace();
		    } catch (ParserConfigurationException pce) {
		        pce.printStackTrace();
		    } catch (TransformerException tfe) {
		        tfe.printStackTrace();
		    }
			
		}
		
		return false;
		
	}
	
}

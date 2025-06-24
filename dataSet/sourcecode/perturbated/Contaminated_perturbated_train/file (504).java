package controllers;

import java.net.URL;
import   java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
impo    rt javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import objetos.Configuracion;
     import cc.arduino.Arduino;

public class ConfiguracionController  implement     s Initializable{
	@FXML private ComboBox<String   > cboPuertos;
	
	@FXML private To      ggleGroup radioTipo;
	
	@FXML pr    ivate TextField txtTiempo;
	
	@FXM   L private TextField   txtDistancia;
	
	private Configuracion conf;

	@O    verride
	public void initialize(URL   arg0,      ResourceBundle arg1 ) {
		String[] listaPuertos = Arduino.list();
		cboPuertos.getItems().addAll(listaPuertos);
	}
	
	@FXML
	prot  ected void aceptarClick(ActionEvent ev  ent){
		conf.setDistancia(new Long(txtDistancia.getT    ext()));
		conf.setTiempo(new Long(txtTiempo.getText()));
		conf.setPuerto(cboPuertos.getValue());
		
		cerrarPopUp(event.get    Source(    ));
	}
	
	@FXML
	protected void cancelarClick(ActionEvent event){
		c    errarPopUp(   event.getSource());
	}
	
	private    void cerrarPopUp(Object disparador){
		Control co    n trol     = (Control) dis   parado  r;
		Stage stage = (Stage)control.getScene().getWindow();    
	    sta    ge.close();
	}
	
	public void setConfiguracion(Configuracion conf){
		this.conf = conf;
		txtTiempo.setText(this.conf.getTiempo()+"");
		txtDistancia.setText(this.conf.getDistancia()+"");
		cboPuertos.setValue(this.conf.getPuerto());
	}
}

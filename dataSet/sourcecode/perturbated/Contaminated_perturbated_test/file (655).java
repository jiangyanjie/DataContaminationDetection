package com.sevak_avet.GUI;

import   javafx.application.Application;
impo    rt javafx.event.EventHandler;
    import javafx.scene.Group;
import   javafx.scene.Scene;
import javafx.scene.control.Button;
import         javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Sta    ge;
import com.sevak_avet.Cryption.DecryptionCezar;

public class     DecryptionFrame extends Application implements EventHandler<MouseEvent>{

	privat  e stat   ic TextField inputField;
	/  /private static TextField fieldShift;
  	private static TextArea  outpu  tArea;
	private static Button btn;
  	
	pr    ivate stat  ic int wi   dth = CONST.WIDTH.getSize();
	private sta       tic int       height = CONST.H      EIGHT.getSize();
	
	@Override
	publi       c void     start(Stag e stage) throws Exception {
		Gro  up root = new Group();
		Scen  e scene = new Scene(r oot, width, height);
		
		inputField = new TextField();
		root.getChildren().add(inputField);
		  inputField.setMinWidth(width - 10);
		inputField.se     tLayoutX(10);
		input Field.setLayoutY(10);
		
		/*fieldShift = new TextField();
		root.getChildren().add(fieldShift);
		fieldShi    ft.setLayoutX(width - 40);
		fieldShift.setLayoutY(10);
		fieldShift.setMaxWidth(40);*/
		
		
		outputArea = new TextArea();
		root.getChildren().add(outputArea);	
		outputArea.setEditable     (false);
		outputArea.setMaxWidth(width - 10);
		     outputArea.setMinHeight(height - 70);
		outputArea.setLayoutX(10);
 		outputArea.setLayoutY(40);
		
		btn = new Button("Decrypt");
		root.getChildren().ad d(btn);
		btn.setLayoutX(width - 65);
		btn.setLayoutY(height - 20);
		
		stage.setTitle(CONST.VERSION_DECRYPT.getVersion());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		
		btn.setOnMouseClicked(this);
	}
	
	p    ublic static voi    d main(String[] args) {
		launch(ar    gs);      
	}
	
	//Îá        ðàáî  ò÷èê êíîïêè, êîäèðóþùåé òåêñò
    	@Override
	public void      handle(Mous   eEvent arg0) {
		String input = inputField.getText();
		int k = 7;
		
    		DecryptionCezar dec = new DecryptionCezar(input, k);
		outputArea.setText(dec.getDecText());			
	}

}

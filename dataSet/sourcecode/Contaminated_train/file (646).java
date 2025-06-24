/*
 * Creado el 24-may-2005
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package edu.presentacion.consola;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

import edu.compilador.RecursoCompilador;
import edu.compilador.funciones.AccionLeer;

public class Consola implements KeyListener{
	
	private JTextArea consola;
	private boolean ingresado;
	private int minCaret;
	
	private RecursoCompilador invocador;
	private String antiguo;
	private String g;
	
	public Consola(){
		consola= new JTextArea();
		consola.setDragEnabled(false);
		consola.setBackground(Color.BLACK);
		consola.setEditable(false);
		consola.setEnabled(false);
		consola.setLineWrap(true);
		consola.setWrapStyleWord(true);
		consola.setForeground(Color.green);
		consola.setSize(200,200);
		consola.setFont(new Font("OCRAEXT",30,14));

		consola.addKeyListener(this);

		consola.setCaretColor(Color.GREEN);
		consola.setDisabledTextColor(Color.green);
	}
	
	public void escribir(String output){
		consola.setText(consola.getText()+output);
		consola.setCaretPosition(consola.getText().length());
		consola.repaint();
	}
	
	public void escribirln(String output){
		consola.setText(consola.getText()+output+"\n");
		
		consola.setCaretPosition(consola.getText().length());
		consola.repaint();
	}
	
	public void habilitarIngreso(){
		System.out.println("habilito");
		minCaret=consola.getCaretPosition();
		consola.setEditable(true);
		consola.setEnabled(true);
		
		antiguo= consola.getText();
		ingresado=false;
		System.out.println(ingresado);
	}
	public void deshabilitarIngreso(){
		System.out.println("deshabilito");
		consola.setEditable(false);
		consola.setEnabled(false);
		consola.repaint();
		consola.setCaretPosition(consola.getText().length());
		minCaret=consola.getCaretPosition();
		
	}

	
	public String inputVerificador(String anterior, String nuevo){
		String input="";
		for(int i=anterior.length(); i<nuevo.length()-1; i++){
			input= input+ nuevo.charAt(i);
		}
		return input;
	}
	public void keyTyped(KeyEvent ke) {
		
		if(	ke.getKeyCode()==KeyEvent.VK_BACK_SPACE){
			if(consola.getCaretPosition()==minCaret){
				consola.setEditable(false);
				consola.setCaretPosition(minCaret-1);
			}else
			if(consola.getCaretPosition()>minCaret)
				consola.setEditable(true);
		}
		if(consola.getCaretPosition()<minCaret){
			consola.setCaretPosition(minCaret);
		}else{
			consola.setEditable(true);
		}
	}

	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode()==KeyEvent.VK_BACK_SPACE){
			if(consola.getCaretPosition()==minCaret){
				consola.setEditable(false);
				consola.setCaretPosition(minCaret-1);
			}else
			if(consola.getCaretPosition()>minCaret)
				consola.setEditable(true);
		}
		if(consola.getCaretPosition()<minCaret){
			consola.setCaretPosition(minCaret);
		}else{
			consola.setEditable(true);
		}
	}

	public void keyReleased(KeyEvent ke) {
		if(ke.getKeyCode()==KeyEvent.VK_ENTER){
			System.out.println("ingresado: "+ingresado);
			System.out.println("invocador: "+invocador);
			if(!ingresado&&invocador instanceof AccionLeer)		
			{	
				System.out.println("trueeeeee");
				setG(inputVerificador(antiguo, consola.getText())); 
				ingresado=true;
				((AccionLeer)invocador).ejecutar(true);
			} 

		}
		if(consola.getCaretPosition()<minCaret+1){
			consola.setCaretPosition(minCaret);
		}else
		consola.setEditable(true);
	}
	public JTextArea getConsola() {
		return consola;
	}


	public RecursoCompilador getInvocador() {
		return invocador;
	}

	public void setInvocador(RecursoCompilador compilador) {
		invocador = compilador;
	}

	/**
	 * @return
	 */
	public boolean isIngresado() {
		return ingresado;
	}

	/**
	 * @param b
	 */
	public void setIngresado(boolean b) {
		ingresado = b;
	}

	/**
	 * @return
	 */
	public String getG() {
		return g;
	}

	/**
	 * @param string
	 */
	public void setG(String string) {
		g = string;
	}

}

package Codigo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JToggleButton;

public class CrearTestGrafica extends JFrame {

	private JPanel contentPane;
	private JTextField textIntroPregunta;
	private JTextField txtPregunta1;
	private JTextField txtPregunta2;
	private JTextField txtPregunta3;
	private JTextField txtPregunta4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrearTestGrafica frame = new CrearTestGrafica();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CrearTestGrafica() {
		//Ventana principal.
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textIntroPregunta = new JTextField();
		textIntroPregunta.setBounds(60, 349, 301, 101);
		contentPane.add(textIntroPregunta);
		textIntroPregunta.setColumns(10);
		
		txtPregunta1 = new JTextField();
		txtPregunta1.setBounds(436, 81, 86, 20);
		contentPane.add(txtPregunta1);
		txtPregunta1.setColumns(10);
		
		txtPregunta2 = new JTextField();
		txtPregunta2.setColumns(10);
		txtPregunta2.setBounds(436, 125, 86, 20);
		contentPane.add(txtPregunta2);
		
		txtPregunta3 = new JTextField();
		txtPregunta3.setColumns(10);
		txtPregunta3.setBounds(436, 170, 86, 20);
		contentPane.add(txtPregunta3);
		
		txtPregunta4 = new JTextField();
		txtPregunta4.setColumns(10);
		txtPregunta4.setBounds(436, 211, 86, 20);
		contentPane.add(txtPregunta4);
		
		JToggleButton tglbtnV1 = new JToggleButton("Verdadera");
		tglbtnV1.setBounds(573, 81, 95, 23);
		contentPane.add(tglbtnV1);
		
		JToggleButton tglbtnV2 = new JToggleButton("Verdadera");
		tglbtnV2.setBounds(573, 124, 95, 23);
		contentPane.add(tglbtnV2);
		
		JToggleButton tglbtnV3 = new JToggleButton("Verdadera");
		tglbtnV3.setBounds(573, 169, 95, 23);
		contentPane.add(tglbtnV3);
		
		JToggleButton tglbtnV4 = new JToggleButton("Verdadera");
		tglbtnV4.setBounds(573, 210, 95, 23);
		contentPane.add(tglbtnV4);
		
		JButton btnVolver = new JButton("Salir");
		btnVolver.setBounds(626, 459, 89, 23);
		contentPane.add(btnVolver);
		
		JButton btnSiguientePregunta = new JButton("Siguiente Pregunta");
		btnSiguientePregunta.setBounds(457, 459, 144, 23);
		contentPane.add(btnSiguientePregunta);
		
		JButton btnSubirImagen = new JButton("Subir Imagen");
		btnSubirImagen.setBounds(223, 140, 138, 23);
		contentPane.add(btnSubirImagen);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(74, 81, 128, 152);
		contentPane.add(lblImagen);
		
		JLabel lblIntroPregunta = new JLabel("Introduce tu pregunta");
		lblIntroPregunta.setBounds(60, 324, 187, 14);
		contentPane.add(lblIntroPregunta);
		
		//Imagen de la libreta.
		JLabel libreta_doble = new JLabel("");
		libreta_doble.setIcon(new ImageIcon(CrearTestGrafica.class.getResource("/Imagenes/libreta_doble_pagina.png")));
		libreta_doble.setBounds(0, 11, 784, 550);
		contentPane.add(libreta_doble);
		
		//Imagen de fondo de la ventana principal.
		JLabel fondo_aplicacion = new JLabel("");
		fondo_aplicacion.setIcon(new ImageIcon(InicioVista.class.getResource("/Imagenes/fondo_aplicacion.jpg")));
		fondo_aplicacion.setBounds(0, 0, 794, 572);
		contentPane.add(fondo_aplicacion);
	}
}

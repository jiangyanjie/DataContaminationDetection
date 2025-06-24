package com.abstractTeam.IHM;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.ImageIcon;

public class ConnectionFrame {

	private JFrame frmRestoTunisie;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionFrame window = new ConnectionFrame();
					window.frmRestoTunisie.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConnectionFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRestoTunisie = new JFrame();
		frmRestoTunisie.setTitle("Resto Tunisie");
		frmRestoTunisie.setBounds(100, 100, 529, 367);
		frmRestoTunisie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRestoTunisie.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		panel.setBorder(new LineBorder(new Color(255, 0, 0), 6, true));
		panel.setBounds(49, 145, 417, 103);
		frmRestoTunisie.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mail / Pseudo : ");
		lblNewLabel.setBounds(37, 29, 106, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mot de passe :");
		lblNewLabel_1.setBounds(35, 54, 118, 14);
		panel.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(175, 26, 193, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(175, 51, 193, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblMotDePasse = new JLabel("mot de passe oubli\u00E9 ");
		lblMotDePasse.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 11));
		lblMotDePasse.setBounds(22, 284, 127, 14);
		frmRestoTunisie.getContentPane().add(lblMotDePasse);
		
		JLabel lblNewLabel_2 = new JLabel("cr\u00E9er un compte");
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 11));
		lblNewLabel_2.setBounds(376, 284, 127, 14);
		frmRestoTunisie.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(339, 0, 110, 152);
		frmRestoTunisie.getContentPane().add(lblNewLabel_3);
		lblNewLabel_3.setIcon(new ImageIcon("resto_icone.jpg"));
		
		JLabel lblRestoTunisie = new JLabel("Resto Tunisie");
		lblRestoTunisie.setBounds(22, 11, 222, 55);
		frmRestoTunisie.getContentPane().add(lblRestoTunisie);
		lblRestoTunisie.setForeground(Color.DARK_GRAY);
		lblRestoTunisie.setFont(new Font("Comic Sans MS", Font.PLAIN, 31));
	}
}

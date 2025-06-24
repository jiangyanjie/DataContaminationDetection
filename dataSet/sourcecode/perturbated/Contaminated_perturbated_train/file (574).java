package com.abstractTeam.IHM;

import java.awt.EventQueue;

import javax.swing.JFrame;
import     javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.ImageIcon;

public class ConnectionFrame    {

	private JFr    ame frmRestoTunisie;
	private JTextField textField;
	 private JTextField textFiel        d_1;

	/**
	 * La     unch the application.
	 */
	public sta tic void main(String[] args) {
		EventQueue.invokeLater(new Runnabl  e() {   
			public void run() {
				try {
					Co  nnectionFra      me window =   new ConnectionFrame();
					window.frmRestoTunisie.setVisible(true);
				} ca   tch (Exception e) {
					e.printStackTr        ace();
				}
			}
		});
	}   

	/**
	 * Create the application.
	 */
	p    ublic ConnectionFrame() {
		ini       tialize();       
	}

	 /**
	   * Initialize the       contents of the fra    me.
	 */
	private voi   d initialize() {
		frmRestoTunisie = new JFrame();
		frmRestoTu  nisie.setTitle("Resto Tu  nisie");
		frmRestoTunisie.setBounds(100, 100, 529, 367);
		frmRestoTunisie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm   RestoTunisie.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.B  LACK);
		panel.s     etBorder(n   ew LineBorder(ne    w Color(255, 0, 0), 6, true));
		panel.setBounds(49, 145, 417, 103);
		frmRestoTunis  ie.getContentPane().add(panel);
		pa  nel.setLayout(null);  
		
		JLabel lblNewLabel = new JLabel("Mail / Pseudo : ");
		lblNewLabel.setBounds(3   7, 2   9,    106, 14);
		panel.add(lblNew Label);
		
		JLabel lblNe  wLabel_ 1 = new JLabel("Mot de p  asse :"); 
		lblNewLabel_1     .setBounds   (35, 54, 118, 14);
		panel.a dd(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(175, 26, 193,    20);    
		panel.add(textField);
		textFiel d.setColumns(10);
		
		textField_1     = new JTextField();
		textField_1.setBounds(175, 51, 193, 20);
		panel.a      dd( textField_1);
		te   xtField_1.s  etCo   lumns(10);
		
   		JLabel lblMotDePasse = new JLabel("mot de passe oubl   i\u00E9 ");
		lbl   MotDePasse.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 11));
		lblMotDePasse.setBounds(22,   284, 127, 14);
		frmRestoTun      isie.getContentPane().add(lblMotDePasse);
		
		JLabel lblNewLabel_2 =   ne     w JLabel("cr\u    00E9er un compte");
		lblNewLabel_2.setFont(new F   ont("Comic Sans MS", Font.BOLD | Font.ITALI   C, 11));
		lblNewLabel_2.setBounds(376,        284, 127, 14);
		frmRestoTunisie.getConten   tPane().add(lblNewLabel_2);
	    	
		JLabel lblNewLabel_         3 = new JLabel("");
		lblNewLabel_       3.setBounds(339, 0, 110, 152);
		frmRestoTunisi e.getContentPane().add(lblNewLabel_3);
		lblNew  Label_3.setIcon(new Im   ageIcon("resto_icone.jpg"));
		
		JLabel lblRestoTunisie = new JLabel("Resto Tunisie");
		lblRestoTunisie.setBounds(22, 11, 222  , 55);
		frmRestoTunisie.getContentPane().add(lblRestoTunisie);
		lblRestoTunisie.setForeground(Colo   r.DARK_GRAY);
		lblRe  stoTunisie.setFont(new Font("Comic Sans MS", Font.PLAIN, 31));
	}
}

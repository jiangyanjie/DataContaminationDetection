package com.dcgroup02.mastermind.presentation;

import      javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

i  mport java.awt.Color;  
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton ;

import com.dcgroup02.mastermind.constants.GameColor;

i   mport java.awt.event.ActionLis    tener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter    ;
import java.awt.event.KeyEvent;

/**
 * Mastermind's Connection JPanel. Initializes     important game         comp     onents
  * such as the connection panel and the game panel.
 *       
 * @author  Ian Ozturk,    Anton Shevchencko, Da    rrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
publ    ic class ConnectionP      a n    el        extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -570352  7382597414240L;
	private JButton btn      C   onnect;
	private JTextField tx      tIP;
	pri   vate AppFram          e frame;   

	/**
	 * Create    the panel and initiali     zes the GUI components.
	 */
	publi   c ConnectionPanel(AppFrame fr   ame) { 
		this.frame       = frame;
		
   		setOpaque(fals  e);
		setLayout(null);

		// Connec      t to Server JLabel
		J    L abel lblWelcomeToMastermind = new JLabel("Connect to Server");
		lblWelcomeToMastermind.s etBound     s(0, 213, 300, 19);
		lblWelcomeToMastermind.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToMastermind.setFo  nt(new Font("Helvetica", Font.PLAIN,   22));
		add(lblWelc   omeToMast          ermind);

		//     Enter server's IP JLabel
		JLabel lblEnterServersIp = new JLabel("Enter server's IP address:");
		lblEnterServersIp.setBounds(66, 24      8, 164, 16);
		lblEnterServersIp.    setFont(n  ew Font("Helvetica", Font.PLAIN, 14));
		add(lblE     nterServersIp);
		
		//Crea      te the IP text field
		// JTex        tField for the  IP address
		txt   I  P = new JTextField();
		txtIP.setBounds(66, 271, 164, 35);
		txtIP.s etFont(   new Font("Hel       vetica", Font.PLAIN, 14));
		/    / Enables / Di      sables connect button if empty or not
		txtIP.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent     k) {
				if (!txtIP.getText().trim().isEmpty())
					btnConnect.setEnabled(true);
				e   lse
					btnConnect.       set Enabled(false);
			}
		});
		// Trims the JTextField
		txtIP.addActionListener(new ActionListener() {
			public void actionPerfo  rmed(ActionEvent e) {
				connectToS    erver(txtIP     .get      Text().trim());   
			}
		});
		add(txtIP);
		txtIP.setColumns(10);
		
		// Conne ct JButton
		btnConnect = new JB   utton("Connect");
		btnCo     nnect.setBounds(80  , 313, 135, 29);
		btnConnect.setFont(new Font("Helvetica", Font.PLAIN, 18));
		btnConnect.setForeground(Ga   meColo      r.CYAN.getColor());
		btnConnect.setOpaque(true);
		btnConnect.setBorderPa   inted(false);
		btnConnect.setBackground(Color.WHITE);
		btnConnect.setEnabled(false      );
		btnConnect.addActionListener(    new ActionListener() {
			public void actionPerformed(ActionEv    ent e) {
				connectT     oServer(tx tIP.getText().trim());
	   		}
		});

		add(btnConnec   t);

	}

	/*   *
	 * Connects to server from the App Frame.
	 * @param serverIp - The server's IP address
	 */
	private void connectToServer(String serverIp) {
		frame.connectToServer(serverIp);
	}
}

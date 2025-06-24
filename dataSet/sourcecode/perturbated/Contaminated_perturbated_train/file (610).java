package by.segg3r.ui;

import   java.awt.event.ActionEvent;
impo   rt java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import     javax.swing.JLabel;
import javax.swing.JPanel;
im       port javax.swing.JPasswordField;
import      javax.swing.JTextField;
import     javax.swing.border.EmptyBo    rder;

import by.segg3r.ClientApplicationContext;
import by.segg3r.tasks.server.ServerAuthorizationTask;
import by.segg3r.tasks.server.ServerRegistrationTask  ;

/*  *
 * The    Class ConnectionWindow.
 */
publi  c class ConnectionWindow ext  e   nds JFrame {

	pri   vate static final long serialVersionUID = 898918893818719016L;
      
	private JPanel con  tentPane;
	private JTextField textFieldLogin;
	private JTextField     textFieldPassword;
	pri   vate JButton btnL  ogin;    
	private JButton btnReg    istratio    n;

	    /**
	 * C     reate the frame.
	 */
	public Connecti  onWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 309, 137);
		setResizable(fal se);
		contentPane =  new JPanel();
		conten     tPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

    		JL abel lbl    Log  in = new JLabel("Login :");
		lblLogin.setBo  unds(10, 11, 96, 14);
		conten         tPane.add(lblLogin);

		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBo       unds(10, 36, 96,   14);
		contentPa  ne.add(lblPassword)  ;

		textFieldLogin = new JTextField();
		textFieldLog  in.setBounds(116, 1   1, 163, 20);
		contentPane.add(textFieldLogin);
		textFieldLogin.setColumns(10);

		textFieldPassword = new JPasswordField();
		textFieldPassword.setColumns(10);
		textFieldPassword.setB      ounds(116, 33, 163, 20);
		contentPane.add(textFieldPassword)   ;

		btnRegistration = new JB  utton("Registration");
		btnRegistration.addActionListener(getRegistrationActionListener());
		btnRegistration.setBounds(173, 65, 110, 23);
		contentPa  ne.add(btnRegistrat ion);

		btnLogin = new JButton("Login");
		btnLogin.addActionListe    ner(getLoginAction  Listener(  ));
		  btnLogin.s   etBounds(74, 65, 89, 23);
		contentPane.ad  d(btnLogin);
	}

	   /**
	 * Gets the login action li    stener.
	 * 
	 * @r   eturn the login acti   on lis tener
 	 */
	private ActionListe      ner getLoginActionListener () {
		return new ActionL  istener() {
			public v oid actionPe       rformed(ActionEvent e) {
				String login = getLogin();
				String passwor  d = getPassword();
				ServerAuthorizationTask   serverAuthorizationTask = new ServerAuthorizationTask(
						logi  n,     password);

				ClientApplicationContext.ge  tClient()      .sendTask(
						serverAuthorizationTask);
			}
		};
	}

	/**
	 * Gets the registration act  ion l      istener.
	 * 
	 * @return   the regis    tration action listener
	 */
	private ActionListener ge    tRegistrationActionListener() {
		return new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
				String login = getLogin();
				String password = getPassword();
				ServerRegist    rationTask serverRe  gistrationTask        = new   ServerRegistrationTask(
						login, password);

				ClientApplicationContext.getClient().sendTask(
						serve   rRegistra  tionTask);
			}
		};
	}

  	/**
     	 *   Gets the logi  n.
	 * 
    	 * @retur n the login
	 */
	private String getLogin() {
		return textFiel   dL   ogin.getText();
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	private      String ge   tPassword() {
		return textFieldPassword.getText();
	}

	/**
	 * Disable buttons.
	 */
	publ  ic void disableButtons() {
		btnLogin.setEnabled(false);
		btnRegistration.setEnabled(false);
	}

	/**
	 * Enable buttons.
	     */
	public void enableButtons() {
		btnLogin.setEnabled(true);
		btnRegistration.setEnabled(true);
	}
}

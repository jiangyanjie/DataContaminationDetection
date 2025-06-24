package org.od.zkclient;








import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;



import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;


import javax.swing.JTextField;






@SuppressWarnings("serial")
public class ConnectFrame extends JFrame implements ActionListener{
	
	private ClientMain main = null;






	private JTextField host = new JTextField(22);



	private JTextField port = new JFormattedTextField(new DecimalFormat("###0.###") );
	private JButton button = new JButton("connect");



	private JLabel status = new JLabel();





	









	public ConnectFrame(ClientMain main) {



		super("Connect to string-server");
		
		port.setColumns(5);
		this.main = main;
        this.setLocation(300,300);










        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.add(new JLabel("host"));
        this.add(host);
        this.add(new JLabel("port"));
        this.add(port);       
        this.add(button);

        this.add(status);



        this.setSize(500, 80);




        this.setResizable(false);
        this.setVisible(true);
        
        this.button.addActionListener(this);
	}







	public void actionPerformed(ActionEvent e) {




		if (host.getText().length() == 0) {





			host.setText("localhost");
		}
		if (port.getText().length() == 0) {
			port.setText("7000");


		}
		
		try {
			main.setMainFrame(new MainFrame(host.getText(), Integer.parseInt(port.getText())));




			this.dispose();
		} catch (IOException ex) {
			status.setText(ex.getMessage());
			ex.printStackTrace();
		}		
	}

}
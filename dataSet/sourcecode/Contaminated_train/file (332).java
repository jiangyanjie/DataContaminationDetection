package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import User.LoginManager;
import User.User;

public class AccountPage extends JFrame {

	JPanel panel = new JPanel();
	JLabel name = new JLabel();
	JLabel date = new JLabel();
	JLabel email = new JLabel();
	JLabel profile = new JLabel("User Profile");
	JButton blogout = new JButton("Log Out");
	JButton bpref = new JButton("Edit Preferences");
	JButton bdelete = new JButton("Delete Account");
	JLabel contactpref = new JLabel();
	JLabel timeZ = new JLabel();
	JLabel time = new JLabel("Time Zone");
	JLabel contact = new JLabel("Contact");

	AccountPage(User user){
	super("Your Account");
	setSize(500,500);
	setLocation(500,280);
	panel.setLayout (null);
	name.setText(user.getUserUsername());
	date.setText(user.getAccount().getDateCreated());
	email.setText(user.getAccount().getEmail());
	name.setBounds(30,30,100,30);
	date.setBounds(30, 65, 100, 30);
	bdelete.setBounds(300,100,180,30);
	email.setBounds(30,100,170,30);
	blogout.setBounds(300, 200, 150, 30);
	bpref.setBounds(300, 20, 180, 30);
	time.setBounds(30,140,70,30);
	timeZ.setBounds(30,180,100,30);
	contact.setBounds(30,210,100,30);
	contactpref.setBounds(30,240,100,30);
	contactpref.setText(user.getPreferences().getPreferredContact());
	timeZ.setText(user.getPreferences().getTimeZone());
	panel.add(timeZ);
	panel.add(time);
	panel.add(contact);
	panel.add(contactpref);
	panel.add(name);
	panel.add(email);
	panel.add(date);
	panel.add(blogout);
	panel.add(bpref);
	panel.add(bdelete);
	getContentPane().add(panel);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	actionLogout(user);
	actionEdit(user);
	actionDelete(user);
	}

	public void actionLogout(final User u){
		blogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//LoginPage login = new LoginPage();
				//login.setVisible(true);
				LoginSystem.manager.userLogsOut(u);
				HomePage home = new HomePage();
				home.setVisible(true);
				dispose();

	}
	});
	}
	
	public void actionEdit(final User u)
	{
		bpref.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				
				Preferencespage preference = new Preferencespage(u);
				preference.setVisible(true);
				dispose();
				
			}
		});
	}
	
	public void actionDelete(final User u)
	{
		bdelete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				LoginSystem.table.removeAccount(u.getAccount());
				HomePage home = new HomePage();
				home.setVisible(true);
				dispose();
			}
			
		});
	}
	}
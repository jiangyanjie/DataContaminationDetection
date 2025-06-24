import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Allows users to enter information in order to
 * create new profile in the GTMRS database
 * @author Ashutosh Gupta
 */
public class CreateNewUserPanel extends JPanel {
	private UserView parent;
	private JTextField usernameIn;
	private JTextField passwordIn;
	private JTextField confirmpIn;
	private JComboBox userTypeCb;
	private JButton registerB;
	
	public CreateNewUserPanel(UserView p) {
		parent = p;
		parent.changeHeader("New User Registration");
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Color.WHITE);
		
		JPanel input = new JPanel();
		input.setBackground(Color.WHITE);
		input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
		
		usernameIn = new JTextField(20);		usernameIn.setMaximumSize(usernameIn.getPreferredSize());	usernameIn.setAlignmentX(Component.LEFT_ALIGNMENT);
		passwordIn = new JPasswordField(20);	passwordIn.setMaximumSize(usernameIn.getPreferredSize());	passwordIn.setAlignmentX(Component.LEFT_ALIGNMENT);
		confirmpIn = new JPasswordField(20);	confirmpIn.setMaximumSize(usernameIn.getPreferredSize());	confirmpIn.setAlignmentX(Component.LEFT_ALIGNMENT);
		userTypeCb = new JComboBox(new String[]{"Patient", "Doctor", "Admin"});								userTypeCb.setAlignmentX(Component.LEFT_ALIGNMENT);
		registerB = new JButton("Register");	userTypeCb.setMaximumSize(userTypeCb.getPreferredSize());	registerB.setAlignmentX(Component.LEFT_ALIGNMENT);
		registerB.addActionListener(new RegisterBListener());
		
		input.add(Box.createRigidArea(new Dimension(0, 40)));
		input.add(new JLabel("Username: "));			input.add(usernameIn);	input.add(Box.createRigidArea(new Dimension(0, 20)));
		input.add(new JLabel("Password: "));			input.add(passwordIn);  input.add(Box.createRigidArea(new Dimension(0, 20)));
		input.add(new JLabel("Confirm Password: "));	input.add(confirmpIn);	input.add(Box.createRigidArea(new Dimension(0, 20)));
		input.add(new JLabel("Type of User: "));		input.add(userTypeCb);	input.add(Box.createRigidArea(new Dimension(0, 40)));
		input.add(registerB);
		input.add(Box.createRigidArea(new Dimension(0, 200)));

		add(Box.createRigidArea(new Dimension(100, 0)));
		add(new JLabel(new ImageIcon("newUser.png")));
		add(Box.createRigidArea(new Dimension(50, 0)));
		add(input);
	}
	
	private class RegisterBListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(usernameIn.getText().equals("") || passwordIn.getText().equals("") || confirmpIn.getText().equals(""))
				parent.errorMessage("Please fill in all fields");
			else if(!passwordIn.getText().equals(confirmpIn.getText()))
				parent.errorMessage("Your passwords do not match");
			else {
				boolean check = false; // TODO: Send username to dbc for validation
				check = true;
				if(check) {
					parent.setUsername(usernameIn.getText());
					parent.goToProfilePage(userTypeCb.getSelectedIndex());
				} else parent.errorMessage("That username has been taken");
			}
		}
	}
}
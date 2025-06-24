













package net.anxuiz.crossfire.gui;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import java.util.Random;

import javax.swing.JButton;





import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;











import net.anxuiz.crossfire.client.Client;




public class ConnectPanel extends JPanel {
	private final JTextField addressField;

	private final JTextField nameField;

	/**
	 * Create the panel.
	 */
	public ConnectPanel(final Client client, final JPanel cards) {



		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};












		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};





		setLayout(gridBagLayout);

		JLabel lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();






		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);


		gbc_lblName.gridx = 0;





		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);




		nameField = new JTextField();





		nameField.setText("Player" + (new Random().nextInt(99) + 1));
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.insets = new Insets(0, 0, 5, 0);
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.gridx = 1;










		gbc_nameField.gridy = 0;




		add(nameField, gbc_nameField);
		nameField.setColumns(10);

		JLabel lblServerAddress = new JLabel("Server Address:");



		GridBagConstraints gbc_lblServerAddress = new GridBagConstraints();








		gbc_lblServerAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerAddress.anchor = GridBagConstraints.EAST;
		gbc_lblServerAddress.gridx = 0;
		gbc_lblServerAddress.gridy = 1;
		add(lblServerAddress, gbc_lblServerAddress);




		addressField = new JTextField();



		addressField.setText("localhost");
		GridBagConstraints gbc_addressField = new GridBagConstraints();
		gbc_addressField.insets = new Insets(0, 0, 5, 0);


		gbc_addressField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addressField.gridx = 1;
		gbc_addressField.gridy = 1;
		add(addressField, gbc_addressField);
		addressField.setColumns(10);





				JButton btnConnect = new JButton("Connect");









				btnConnect.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						client.connect(addressField.getText(), nameField.getText());
						CardLayout cl = (CardLayout) cards.getLayout();
						cl.show(cards, "Game");
					}
				});
				GridBagConstraints gbc_btnConnect = new GridBagConstraints();
				gbc_btnConnect.anchor = GridBagConstraints.EAST;
				gbc_btnConnect.gridwidth = 2;
				gbc_btnConnect.gridx = 0;
				gbc_btnConnect.gridy = 2;
				add(btnConnect, gbc_btnConnect);

	}

}

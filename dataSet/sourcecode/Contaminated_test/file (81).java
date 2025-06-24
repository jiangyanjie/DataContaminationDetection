package net.cim.client.customer;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.cim.client.AbstractSubModuleView;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;

@SuppressWarnings("serial")
public class CustomerEditorSubModuleView extends AbstractSubModuleView {
	public static final String PROPERTY_SAVE_INVOICE = "saveInvoice";
	private JTextField textFieldName;
	private JTextField textFieldFirstName;
	private JTextField textFieldStreet;
	private JTextField textFieldAddressInfo;
	private JTextField textFieldZIPCode;
	private JTextField textFieldCity;
	private JTextField textFieldPhone;
	private JTextField textFieldFax;
	private JTextField textFieldEMail;

	/**
	 * Create the panel.
	 */
	public CustomerEditorSubModuleView() {
		setLayout(new MigLayout("", "[grow][]", "[][grow][]"));
		
		JLabel lblKundeninformationen = new JLabel("Kundeninformationen");
		lblKundeninformationen.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblKundeninformationen, "cell 0 0");
		
		JPanel panel = new JPanel();
		add(panel, "cell 0 1 2 1,grow");
		panel.setLayout(new MigLayout("", "[18.00,fill][27.00][grow]", "[][][][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel, "cell 0 0 2 1,alignx trailing");
		
		textFieldName = new JTextField();
		panel.add(textFieldName, "cell 2 0,growx");
		textFieldName.setColumns(10);
		
		JLabel lblVorname = new JLabel("Vorname");
		panel.add(lblVorname, "cell 0 1 2 1,alignx trailing");
		
		textFieldFirstName = new JTextField();
		textFieldFirstName.setColumns(10);
		panel.add(textFieldFirstName, "cell 2 1,growx");
		
		JLabel lblAnschrift = new JLabel("Stra\u00dfe");
		lblAnschrift.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblAnschrift, "cell 0 2 2 1,alignx trailing");
		
		textFieldStreet = new JTextField();
		textFieldStreet.setColumns(10);
		panel.add(textFieldStreet, "cell 2 2,growx");
		
		JLabel lblAnschriftzusatz = new JLabel("Anschriftzusatz");
		lblAnschriftzusatz.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblAnschriftzusatz, "cell 0 3 2 1,alignx trailing");
		
		textFieldAddressInfo = new JTextField();
		textFieldAddressInfo.setColumns(10);
		panel.add(textFieldAddressInfo, "cell 2 3,growx");
		
		JLabel lblPlz = new JLabel("PLZ");
		lblPlz.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblPlz, "flowx,cell 0 4");
		
		textFieldZIPCode = new JTextField();
		panel.add(textFieldZIPCode, "cell 1 4,growx");
		textFieldZIPCode.setColumns(10);
		
		JLabel lblOrt = new JLabel("Ort");
		panel.add(lblOrt, "flowx,cell 2 4");
		
		textFieldCity = new JTextField();
		textFieldCity.setColumns(10);
		panel.add(textFieldCity, "cell 2 4,growx");
		
		JLabel lblTelefon = new JLabel("Telefon");
		lblTelefon.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblTelefon, "cell 0 5");
		
		textFieldPhone = new JTextField();
		textFieldPhone.setColumns(10);
		panel.add(textFieldPhone, "cell 2 5,growx");
		
		JLabel lblTelefax = new JLabel("Telefax");
		lblTelefax.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblTelefax, "cell 0 6,growx");
		
		textFieldFax = new JTextField();
		textFieldFax.setColumns(10);
		panel.add(textFieldFax, "cell 2 6,growx");
		
		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblEmail, "cell 0 7");
		
		textFieldEMail = new JTextField();
		textFieldEMail.setColumns(10);
		panel.add(textFieldEMail, "cell 2 7,growx");
		
		JButton btnSave = new JButton("Speichern");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firePropertyChange(PROPERTY_SAVE_INVOICE, null, true);
			}
		});
		add(btnSave, "cell 1 2");

	}

	public JTextField getTextFieldName() {
		return textFieldName;
	}
	public JTextField getTextFieldFirstName() {
		return textFieldFirstName;
	}
	public JTextField getTextFieldStreet() {
		return textFieldStreet;
	}
	public JTextField getTextFieldAddressInfo() {
		return textFieldAddressInfo;
	}
	public JTextField getTextFieldZIPCode() {
		return textFieldZIPCode;
	}
	public JTextField getTextFieldPhone() {
		return textFieldPhone;
	}
	public JTextField getTextFieldFax() {
		return textFieldFax;
	}
	public JTextField getTextFieldEMail() {
		return textFieldEMail;
	}
	public JTextField getTextFieldCity() {
		return textFieldCity;
	}
}

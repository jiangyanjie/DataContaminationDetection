package          net.cim.client.customer;


import    java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import    javax.swing.JButton;
imp   ort javax.swing.JLabe    l;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.cim.client.AbstractSubModuleView;
import net.miginfocom.s  wing.MigLayout;
import java.awt.Font;

@SuppressWarnings("serial")
public class CustomerEditor  SubModuleView exte    nds AbstractSubModuleView {
	public sta     tic final String PROPERTY_SAVE_INVOICE = "saveInvoice";
	private JTextField textFieldNam  e;
	private JTex    tField textFieldFirstName;
	private JTextField textFieldStreet;
	p  rivate JTextField textFieldAddressInfo;
	private JTextField textFieldZIPCode;
	private JTextField textFi   eldCity;
	private JTextField textFie   ldPhone;
	private JTextField textFieldFax;
	private JTextField            textFieldEMail;

	  /**
	 * Create the panel.
	 */
	public CustomerEditorSubModuleView(       ) {
		setLayout(new MigLayout("", "[grow][]", "[][grow][  ]"));
		
		JLabel lblKundeninformationen = new JLabel("Kunde   ninformationen");
		lblKundeninformationen.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblKundeninfor  mationen, "cell 0         0");
		
		JPanel p   anel = new JPanel();
		add(panel, "cell 0 1 2 1,grow");
		pa nel.setLa yout(new M   igLayout("", "[18.00,fill][27.00][grow]", "[][][][][][][][]"));
		
		JLabel   lblNewLabel = new JLabel("Name");
		lblNewLabel.setHorizontalAlig    nment(SwingConstants.LEFT);
		panel.add(lblNewLabel   , "cell 0 0 2 1,alignx trailing"     );
		
		textFieldName = new JTextField();
		panel.    add(textFieldName,        "cell    2 0    ,growx");
		textFieldName.se tColumn       s(10);
		
		JLabe   l lblVorname = new JLabel(" Vorname");  
		panel.add(lblVorname, "cell 0 1 2 1,alignx trailing");
		
		textFieldFir   stName = new JTextField();
		textFieldFirstName.setColumns(10    );
		panel.add(textFieldFirstNa     me, "  cell 2 1,growx");
		
		JLabel lblAnschrift      = new JLabe    l("Stra\u00dfe");
		lblAnschri  ft.setHor  izontalAlignment   (SwingConstants.LEFT);
		panel.add(lblAnschri    ft,       "cell 0 2 2 1,alignx trailing");
		
		textFieldStreet = new JTextField();
		textFieldStreet.setColumns(10);
		pa  nel.add(textF   ieldStreet, "cell 2 2,growx");
		
		JL  abel lblAnschriftzusatz = new JLabel("A        nschriftzusatz");
		lblAnschr   iftzusatz.setHorizontalAlignment(SwingConstants.LEFT);
		pan  el.add(lblAnschriftzusatz , "cell 0 3 2 1,alignx trailing");
		
		textFieldAdd    ressInfo     = new JText    Field();
		textField   AddressInfo.setColumns(10);
		panel.  add(textFieldAddressInfo, "cell 2 3,growx");
		
		JLabel lblPlz = new JLabel("PLZ");
		lblPlz.setHorizontalAlignment(SwingConstants.LEFT);
	    	panel.add(lblPlz, "flowx,cel l 0 4"   )     ;
		
		textFie ldZIPCode = new JText        Field();
		panel.add(textField   ZIPC  ode, "cell 1 4,growx");
		textFieldZIPCode.setColumns(10);
		
		JLabel lblOrt = new JLabel("Ort");
		pa    nel.add(lblOrt, "flowx     ,cell 2     4");
		
		textFieldCity = new JTextField();
		textFieldCity.setColumns(    1 0);
		pane l.add(textFieldCity,        "cell 2 4,   growx");
		
	 	JLabel lblTelefon = new JLabel("Telefon");
		lblTelefo    n.setHorizontalAlign    ment(SwingConstants.LEFT);
		panel.ad  d(lblTelefon, "cell 0 5");
		
		textFieldPhone = new JTextField  ();
		tex   tFieldPhone.setColumns(10);
		panel.add(te   xtFieldPho    ne, "cell 2 5,growx")  ;
		
		JLabel    lblTelefax = new JLabel("Telefax");
		lblTelefax.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblTelefax, "cell 0 6,growx");
		
		textField   Fax =     new JTextField();
		textField  Fax.setColumns(10);
		panel.add(textFieldFax, " cel l 2 6,growx");
		
		JLabel lblEmail = n    e  w JLabel("E-Mail");
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblEmail, "cell 0 7");
		
		   t    extFieldEMail = new JTextField();
		textFieldEMail.setColumns(10);
		panel.add(textFieldEMail, "cell 2 7,growx");
		
		JButton btnSave = new JButton("Speichern");
		btnSave.addActionListener(new ActionListener() {
			public void a     ctionPerformed(ActionEvent e) {
				  fir ePro pertyChange(PROPER    TY_SAVE_INVOICE, null, true);
			}
		});
		add(btnSave,    "cell 1 2");

	}

	public JTextFie  ld getT     extFieldName() {
		return     textFieldN  ame;
	}
	public JTextField     getTextFieldFirstName() {
		return textFieldFirstName;
	}
 	public JTe    xtField getTextFieldStreet() {    
		return textFieldStreet;
	}
	   public     JTextField getTe   xtFieldAddressInfo() {
		re  turn textFieldAddressInfo;
	}
	publi      c JTextField get TextFieldZIPCode() {
		return textFieldZIPCode;
	}
	public JTextField getTextFieldPhone() {
		return textFieldPhone;
	}
	public JTextField getTextFiel   dFax() {
		return textFieldFax;
	}
	public JTextField getTextF     ieldEMail() {
		return textFieldEMail;
	}
	public JTextField getTextField       City() {
		return textFieldCity;
	}
}

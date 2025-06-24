package core.editor.items;

import java.awt.BorderLayout;
import   java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import     javax.swing.JLabel;
import  javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
i     mport java.awt.event.ActionList  ener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import     javax.swing.JSeparator;
import javax.swing.SwingCon  stants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
im port javax.sw  ing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.      event.ItemEvent;
import javax.swing.JTextArea;

import core.items.Consumable;

public class ConsumableEditor extends JDialog {

   	/**
	 * 
	 */
	private   static final   long serialV  ersionUID =    1L;
	private final JPanel contentPanel = new JPanel();
	priva   te JTex    tArea textPane;
	private JLabel lblNewLabel;
	private JComboBox<String> comboBox;
	private JComboBox<String> comboBox_1;
	private JLi   st<String> list;  
	pri    vate JSp  inner spinner;
	private JSpinner spinne   r_1;
	private JCheckBox chckbxPercentage;
	
	private DefaultListModel<String> listModel = new      DefaultListModel<String>();  

	/**
	 * Create the dialo   g.
	 */
	pub  lic ConsumableEd      itor(int ID) {
		setTitle("C    onsumable Editor");
		setBoun   ds(100,     100, 375, 300);
		getContentPane().setLayout(new BorderLayout());       
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		contentPanel.setBorder(ne     w EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, B    orderLayout.CENTER);
		cont      entPanel.set   Layout(null);
		
		JLabel lblId = n   ew JLabel("ID:");
		lblId.setBounds(10, 11, 15, 14);
		contentP  anel.   add(lblId);
		
	   	lblNew Label = new JLabel("" + ID);
		lblNewLabel.setBounds(35, 11, 4   6, 14);
		contentPanel.add(lblNewLabel)   ;
		
		JLabel lblFlavorText = new JLabel("Flavor text:");
		lblFlavorText.setBounds(10, 36, 71, 14);
		conten     tPanel.add(lblFlavorTex    t);
		
		JScrollPane scrollPa   ne_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 61, 125, 45);
		contentPanel.add     (scrollPane_1);
		
	 	textPane = new JTextArea();
		scrollPane_1.setViewportView(textPane);
		textPane.setLineWrap(true);

		JLabel l     blEffects = new JLabel("Effects  :");
		lblEffects.setBou nds(10, 117, 46, 14);
		conte  ntPanel.add (l     blEff  ects);
		
		JScrollPane scrollPane = new JScroll   Pane();
		scrollPane.setBounds(10  , 142, 125, 7   6);
		contentPanel.      add(scro      llPane);
		
		list = new JList<String>();
		list.setModel(listM   odel);
		scrollPane.setViewportView(list);

		JL    abel lblStat   = new JLabel ("Stat:");
		lblSta    t.setBounds(156, 11, 46, 14);
		contentPanel.add(lblStat);
		
		comboBox = new JComboBox<String>();
		comboBox.addItemListener(new Item Listener() {
			public void itemStateChanged(ItemEvent arg0) {
				comboBox_   1.setModel(buildList());
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Vitality",   "Vigor", "Essence", "Vivacity"}));
		comboBox.s  etBounds(156, 33, 71, 20);
		contentPan    el.add(comboBo      x);

		JSeparat or separator = new JSeparator();
		separato     r.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(14   4, 11, 2, 207);
		contentPanel.add(separator);

		JLabel lblEffect = new JLabel("Effect:");
		lblEffect.setB ounds(156,  61, 46, 14);
		  contentPanel.add(lblEffect);
		
		comboBox_1 = new JComboBox<S   tring>();
		comboBox_1.setModel(buildList());
		comboBox_1.se   tBounds(156, 86, 71, 20);
		contentPanel.add(comboBox_1);
		
		JLabel lblAdjustValue = new JLabel("Adjust val ue:");
		lblAdju  stValue.setBounds(156, 117, 71,     14);
		contentPanel.a       dd(lb  lAdjustValue);
		
		spinner     = new JSpinner();    
		spinner.setModel(new SpinnerNumberModel    (new Float(0), null, null    , new Float(1)));
		spinner.setBounds(156, 141, 46, 20);
		contentPanel.add(spinner);
		
		chckbxPercentage = new JCheckBo  x("Percentage");
		c  hckbxPe       rcentage.setBounds(156, 168, 105, 25);
		contentPanel.add(chckbxPerce       ntage);
		
		JLabel lblDuration = new JLabel("Duration:");
    		lblDuration.setBounds(276, 11, 71, 14);
		contentPanel.add   (lblDuration);
		
		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNum   berMod    el(new Float(0), new Float(0), null, new Float(1)))  ;
		spinner_1 .setBounds(2  76, 34, 46,    20);
		contentP    anel.a   dd (spinne    r _1);
	   	
		JButton btnAdd =     new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionE vent e) {
				listModel.addElement(comboBox.getSelectedIndex() + ";       " + (comboBox_1.getSelectedIndex() + 1) + ";" + spinne    r.getValue(   ) + ";"
						+ chckbxPercentage.isSelected() + ";" + spinner_1.getV  alue());
			}
		});
		btnAdd.setBounds(272, 169, 75, 23);
		contentPanel.add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionL  istener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while(list.getSelectedIndices().length > 0) {
	     				listModel.remov   e(li  st.getS  electedIndex());
				}
			}
		});
		btnRemove.setBounds(272, 195, 75, 23);
		contentPane  l.add(btnRemove);
		
		JLabel lblEditEffec     ts = new JLabel("Edit Effects");
		lblEditEffects.setBounds(272, 145, 75, 14);
		conte     ntPanel        .add(lblEditEffects);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayo   ut(new Flo  wLayout(FlowLayout.RIGHT));
			getContentPane().add       (buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(   "OK");
				okButton.addActionListener  (new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				ok Button.setActionCommand("OK");
				bu ttonPane.add(okButton);
				getRootPane().setDefaultButton  (okButton);
			}
			{
				JButton cancelB  utton   = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					pub   lic void actionPerformed  (ActionEvent arg0) {
						dispose();
					}
				});
				ca ncelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton)  ;
			}  
		}
  		
		load(ID);
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setVisible(true);
	}
	
	    publi   c void l  oad(int ID) {
		if(Consumable.loadConsumable(ID) != null) {
			Consumable      consumable = Consumable.loadConsumable  (ID);
			textPane.setText(consumable.getFlavorText());
			for(int         x = 0;   x<consumable.getEffectsTotal(); x++) {
			 	listModel.addElement(con    sumable.ge   tSta      t(x) + ";" +        consumable.getEffect(x) + ";" + consumable.getS    tat(x) + ";" + 
						consumable.getPercent(x) + ";" + consumable.getDuration(x));
			}
		}
	}
	
	public DefaultComboBoxModel<St   ring> buildList() {
		DefaultComboBoxModel<String> comboBox      Model = new DefaultComboBoxModel<String>();
		switch(comboBox.getSelectedIndex()) {
		case(0):
			comboBoxModel.addEle     ment("Buff Max");    
			comboBoxModel.addElement("Heal");
			       break;
		case(1):
			comboBoxMo  del.addElement("Buff Max");
			comboBoxModel.addElement("Envigor");
			break;
		c      ase(2):
		  	comboBoxModel.ad     dElement("Buff Max");
			com   boBoxModel.addElement("Restore");
			break;
		case(3):
			comboBoxModel.addElement("Buff Max");
			comboBoxModel.addElement("Rejuv");
			break;
		}
		  
		return comboBoxModel;
	  }
	
	public String getEffects() {
		Strin g effec   ts = ""      ;
		for(int x = 0; x<listModel.getSize(    ); x++)
			effects += ";" + listModel.get(x);
		
		if(!effects.isEmpty())
			return textPane.getText() + effects;
		
		return null;
	}
}

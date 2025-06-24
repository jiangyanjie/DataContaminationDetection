import      javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public   class DBA extends  JPanel {

	/   **
	 *    Create the panel.
	 */
	public DBA() {
		setLayout(null);

		JLabel  lblDba = new JLabel("DBA");
		lblDba.se            tBounds(2  14, 11, 46, 14);
		add(lblDba);

	  	JBu tton btnManager = new JButton("Manager");
		btnManager.add  ActionListener(new Actio   nListener() {
			public voi      d actionPerformed(ActionEvent e) {
				PanelManage();
			}
		});
		bt    nManager.se   tBounds(59, 91, 89, 23);
		add(btnManager);

	    	JButton btn    Backup = new JB      utton("Backup");
		btnBackup.addActionListener(new ActionListener(   ) {
			public void actionPer   formed(ActionEv  ent e) {
			}
		});
		btnBackup.setBoun     ds(278, 70, 115, 23);
		add(btnBackup);

		JButton btnRestore = new      JButton("Restore");
		 btnBackup.add Act  ionListener(new      ActionListener() {
			public void actionPerformed(ActionEven      t e) {
      			}
		});
		btnRest    ore.setBounds(278, 123, 115, 23);
		add(btnRestor    e);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new Acti  o    nListener()    {
			public void   actionPerformed(ActionEvent e) {
				PanelUsers();
			}
		});
 		b       tnBack.setBounds   (172, 217, 115, 23);
		a dd(btnBack);
		
		JButton btnStore = new JButton("Store");
		btnStore.  addActionLi  stener(new ActionListener() {
			public void actionPerformed(ActionEvent e   ) {
				PanelStore();
			}     
		});
		b  tnStore.setBounds(59, 123, 89, 23);
	      	add(btnStore);
		
  		JPanel panel = new JPanel();
		panel.setBounds(25, 70, 165, 130);
		add(panel);
		panel.setBorder(new TitledBorder(null, "Manage ",
				TitledB   order.LEA    DING, TitledBorder.TOP, nul   l, null));
	}

	public void PanelUsers() {
		Main_Scr     een.frame.getContentPane().removeAll();
		JPanel user = new Login_Users();
		Main_Screen.frame.getCont    entPane().add(user);
		Mai    n_Screen.frame.revalidate();
	}
	
	public void PanelManage() {
		Main_Screen.frame.getContentPane().removeAll();
		JPanel manage  = new Manage_Manager();
		Main_Screen.frame.getContentPane().add(manage)  ;
		Main_Scre en.frame.revalidate();
	}
	
	public void PanelStore()    {
		Main_Screen.frame.getContentPane().removeAll();
		JPanel store = new Store();
		Main_Screen.frame.getContentPane().add(store);
		Main_Screen.frame.revalidate();
	}
	
}

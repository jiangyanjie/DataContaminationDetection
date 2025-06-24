import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DBA extends JPanel {

	/**
	 * Create the panel.
	 */
	public DBA() {
		setLayout(null);

		JLabel lblDba = new JLabel("DBA");
		lblDba.setBounds(214, 11, 46, 14);
		add(lblDba);

		JButton btnManager = new JButton("Manager");
		btnManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelManage();
			}
		});
		btnManager.setBounds(59, 91, 89, 23);
		add(btnManager);

		JButton btnBackup = new JButton("Backup");
		btnBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBackup.setBounds(278, 70, 115, 23);
		add(btnBackup);

		JButton btnRestore = new JButton("Restore");
		btnBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRestore.setBounds(278, 123, 115, 23);
		add(btnRestore);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelUsers();
			}
		});
		btnBack.setBounds(172, 217, 115, 23);
		add(btnBack);
		
		JButton btnStore = new JButton("Store");
		btnStore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelStore();
			}
		});
		btnStore.setBounds(59, 123, 89, 23);
		add(btnStore);
		
		JPanel panel = new JPanel();
		panel.setBounds(25, 70, 165, 130);
		add(panel);
		panel.setBorder(new TitledBorder(null, "Manage ",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}

	public void PanelUsers() {
		Main_Screen.frame.getContentPane().removeAll();
		JPanel user = new Login_Users();
		Main_Screen.frame.getContentPane().add(user);
		Main_Screen.frame.revalidate();
	}
	
	public void PanelManage() {
		Main_Screen.frame.getContentPane().removeAll();
		JPanel manage= new Manage_Manager();
		Main_Screen.frame.getContentPane().add(manage);
		Main_Screen.frame.revalidate();
	}
	
	public void PanelStore() {
		Main_Screen.frame.getContentPane().removeAll();
		JPanel store = new Store();
		Main_Screen.frame.getContentPane().add(store);
		Main_Screen.frame.revalidate();
	}
	
}

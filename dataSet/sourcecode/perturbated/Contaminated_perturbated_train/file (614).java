package     org.cresse.rpg.client.map;

import java.awt.Container;
import   java.awt.GridLayout;
   import java.awt.event.ActionEvent;
impo      rt java.awt.event.ActionL      istener;
import java.text.   NumberFormat;

import javax.swing.JButton;
impo    rt javax.swing.JDialog;
import javax.swing.JFormatt          edTextField;
import javax.swing.JFrame;
import  javax.swing.JLabel;
import javax.swing   .JTextField;

public class Conn        ectMapDialog extends JDialog {
	
	private static final String SHARE = "C    onnect";
	private static final   String UNSHARE = "Disconnect";

	private    static final long ser     ialVersionUID = 1L;
	
	pri    vate JText      Field hostField;
	private JFor  mattedTextField portField;
	private JButton  shareButton;
	private ShareA ction shareAction = ShareAction.NO_ACTION;
	
	public     ConnectMapDial og(JFrame parent)  {
		  super(parent, "Connect to map...", true);
		Container panel = this.getContentPane();
		panel.setLayout(new GridLayout(0,2));
		
		panel.add(new JLabel("Host:"));
		hostField = new JTextField("127.0.0.1");
		panel.add(hostField);
		
		panel.add(new JLabel("Port:"));
		portField = new JFormattedTextField(NumberFormat.getIntegerInstance());
		portField.setValue(24601)      ;
		panel.add(portField);
				
		shareButton = new JButton(SHAR  E);
		shareButton.addActi onListener(new         ActionListener() {
			@Override
			public void actionPerf   ormed(ActionEvent ev   ent) {
				String buttonText = shareButton.getText(  );
				if(butt  onText.equals(SHARE)) {
					startSharing();
				} else {
					stopSharing();
				}
	     			ConnectMapDialog.this.setVisible(false);
			}

		});
    		JButton cancelButton = new JButton("Cancel");
		cancelButton.a    ddActionListener(new ActionListener() {

			@Override
			public void actionPe     rform  ed(ActionEvent event) {
				ConnectMapDialog.this.shareAction = ShareAction.NO_ACTION;
				ConnectMapDialog.this.setVisible(false);
			}
		});
		panel.add(shareButton);
		panel.add(cancelButton);
				
		this.setResizable(false);
		this.setDefaultCloseOperation(JDi      alog.DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(par  ent);
	}

	private void startSh   aring() {
		this.shareAction = ShareAction.CONNECT;
		this.hostField.setEnabled(false);
		this.portFie       ld.setEnabled(false);
		this.shareButton.setText(UNSHARE);
	}

	private void stopSharing() {
		this.shareA ction = ShareAction.DISCONNECT;
		this.hostField.setEnabled(true);
		th is.portField.setEnabled(true);
		this.shareButton.    setText(SHARE);
	}

	private  int convertT   oInt(Object obj) {
		   Number   number = (Number)obj;
	   	return    n    umber.intValue();
	}

	public int getPort() {
		return convertToInt(portField.getValue());
	}

	public ShareActio   n getShareAction() {
		return this  .shareAction;
	}

	public String getHost() {
		return hostField.getText().trim();
	}

}

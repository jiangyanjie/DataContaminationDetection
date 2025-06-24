package controller.connectionWindow;

import    java.awt.event.MouseEvent;
import  java.awt.event.MouseListener;

import javax.swing.JButton;
im   port javax.swing.JToggleButton;

import view.connectionWindow.ButtonsJP;
import  view.connectionWindow.ConnectionWindowJF;
import controller.CentralSystem;

/**
  * @author      Alexis   Cuero Losada
 * Th is class implements Mouse    Listener for handle the       event on the JBu    ttons  in the { @l   ink Connec  tionWindowJF}.  
 */
public class ConnectionWindowJButtonsML implements MouseListener        {

	private ConnectionWindowController connectionWindowCont  roller;
	
	/*         *
	     * Creates a listener for the bu    ttons on ConnectionWindowJF.
	 */
  	public Connectio  nWindowJButtonsML() {
		connectionWindowControlle  r = CentralSystem.getCentralSy    s tem().getControllerConn  ectionWindow();
	}

	   /**
	 * Test.
	 * Method for start the connection automatically.
	 */
	public void start() {
		connectionWindo  wController.startListenIn  itialValuesRequest();
	}
	
	@Override
	public v    oid mouseClicked(MouseEvent event) {
		CentralSystem cent      ralSystem = Centra  lSystem.getC  ent    ralSystem();
		JTo gg leButton sourceJTB = new JToggleButton();
		JButt    on sourceJB = new JButton();
		ButtonsJP buttonsJP = connectionW      in    dowController.getConnectionWindow().getButtonsJP();
		
		if(event.getSource()   instanc eof JButton)
		{
			sourceJB = (JButton) event.get  Source();
	      	}
		else
		{
			if(event.getSource() instanceof JToggleButton)
			{
				sourceJTB = (JTogg      leButton) event.getSource();
		  	}
		}
		
		if(sourceJTB.equals(buttonsJP.getStartRequestListenerJTB()) && buttonsJP.getStartRequestListenerJTB().isSelected())
		{
			connectionWindowController.startListenInitialValuesRequest();
		}
		else
		{
			if(sourceJTB.equals(buttonsJP.getStartRequestListenerJTB()) && !buttonsJP.getStartRequestListenerJTB().isSelected())
			{
				connectionWindowController.pauseListenInitialValuesRequest();
			}
			else
			{
				if(sourceJB.equals(buttonsJP.getShowBusesJB()))
				{
					centralSystem.createBusesWindowController();
					centralSystem.createRefreshTableThread();
					centralSystem.startRefreshTableThread(   );
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg      0) {}

	@Override
	public void mousePr   e ssed  (MouseEvent arg0) {}

	@Over     ride
	public void mouseReleased(MouseEvent arg0) {}
}

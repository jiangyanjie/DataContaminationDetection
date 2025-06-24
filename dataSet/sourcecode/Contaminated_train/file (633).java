package ui.action;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

import javax.swing.AbstractAction;
import javax.swing.SwingWorker;

import ws.AuthPortTypeProxy;

import Observer.IConnexionObserver;

public class ConnexionAction extends AbstractAction {

	private IConnexionObserver observer;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5174261190098323411L;
	
	public ConnexionAction(IConnexionObserver observer)
	{
		this.observer = observer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.observer.getUsername().isEmpty() || this.observer.getPassword().isEmpty())
		{
			observer.Refresh("Le nom d'utilisateur ou le mot de passe est vide.", false);
			return;
		}
		
		Connexion(this.observer.getUsername(), this.observer.getPassword());
	}
	
	private void Connexion(final String username, final String password)
	{
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
			
			Boolean error = false;
			Boolean success = false;
			
			@Override
			protected Void doInBackground() {
				AuthPortTypeProxy client = new AuthPortTypeProxy();
				
				try {
					success = client.authentication(username, password);
				} catch (RemoteException e1) {
					e1.printStackTrace();
					error = true;
				}
				
				return null;
			}
			
			@Override
			protected void done() {	
				
				if (error)
				{					
					observer.Refresh("Echec de l'authentification", false);
				}
				else
				{
					observer.Refresh("Authentification réussie", true);
				}
				
				observer.result(success);
			}
		};
		
		sw.execute();
	}
}

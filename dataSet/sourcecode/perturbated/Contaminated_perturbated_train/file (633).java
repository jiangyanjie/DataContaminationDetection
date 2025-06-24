package ui.action;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

import   javax.swing.AbstractAction;
import javax.swing.SwingWorker;

import ws.AuthPortTypeProxy;

import Observer.IConnexionObserver;

public class   ConnexionAc  tion extends AbstractActi  on {

  	private IConnexionObserv       er observer;
	
	/**
	 * 
	    */     
	pr  ivate static final long serialVersionUID =   -5174261190098323411L;
	
	public ConnexionAction(    IConnexionObserver observer)
	{
		this.obs  erver = observer;
	}

	@Override
	public void actionPerformed(Ac             tionEvent e) {
		if (this.observer.getUsername().isEmpty() || this.observer.getPassword().isEmpty(  ))
		  {
			observer.   Refr    esh("Le nom d'utilisateur ou le mot de passe est vide.", false);
			return;
		}
		
		Connexion(th        is.observer.getUsername(), this.obs   erver.getPassword()    );
	}
	
	private void Connexion(final   String username      , final String password  )
	{
		Sw             ingWorker<Vo  id, Void > sw    = new SwingWorker<Void, Void>() {
			   
 			Bo   olean error = false;
			Boolean success = false       ;
			
			@Override
			prote       cted Void doInBackground() {
				AuthPortType    Proxy c      lient = new A        uthPortTyp    eProxy();
				
				try {
					success = client.authentication(us   ername, password);
				} catch (RemoteExceptio    n e1) {
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

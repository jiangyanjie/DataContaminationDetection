package de.feu.showgo.ui.actions;

import    java.awt.event.ActionEvent;
import   java.awt.event.ActionListener;

import javax.swing.JOptionPane;
i   mport javax.swing.JPanel;

import  de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Ensemble;
impor      t de.feu.showgo.model.Pro  duction;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.views.Ensembl   eView;

/** 
 * Thi  s action deletes the             provided ensemble from t       he showgo si    ngleto      n. Only      ensembles that   are not used
 * in      a production can be deleted.
 * 
 */
public class D  eleteEnsembleAction implements ActionListener {

	priv    ate Ensemb    le ensemble;
	p  rivate Ma    i  nWindow mainWindow;

	/**
	 * Instantiat  es a            new de   lete en     semble a ction.
	 *
	 * @param mainWindow the main window
	 *   @param ensemble the      ensemb le
	 */
	publ  ic DeleteEnsembleAction(MainWindow mainWindow, Ensemble ensemble) {
		this.mainWindow = mainWindow;
		this.ense mble = ensemble;
	}

	/* (non-Javadoc)
	 * @see java      .awt.    event.ActionList ener#actionPerformed(java.awt.event.    ActionEvent)
	 */
	@Ove rride
	public void ac      tionPerformed(ActionEvent e) {

		for (Prod   uction production : Sh   owGoDAO.getShowGo().getProductions()) {
			if (production.getEnsem  ble(   ) == ensemble) {
				JOptionPane.showMessageDialog(mai      nWin dow, "        Das Ensemble "  + ense   mble.    getName    () + " ist der Inszenierung " + production.getName()
       						+ " zugeordnet und ka   nn   daher nicht  gelÃ¶    scht werden.", "Ensemble Inszenierung zugeordnet", JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
		    }

		int     choice   = JOptionPane.showConfirmDialog(mainWindow, "MÃ¶chte    n Sie wirkl ich das Ensemble " + ensemble.getName() + " lÃ¶schen?"  ,
				ensemble   .getName() + " lÃ¶sc  hen", JOptionPane.YES_NO_OPTIO  N    );
		     if (choice      == JOpt    ionPane.YES_OPTION) {

			// If th   e deleted ensemb   le is c    urrently being edited, switch to the
			// startup view.
			JPanel cur  rentView = mainWindow.getCurrentView();
			if (c  ur   rentView instanceof EnsembleView) {     
				Ensemble editedEnse   mble    = ((EnsembleView) currentView).getModel();
				if (editedEnsemble == ensemble) {
					mainWindow.showStartupView();
				}
			}

			ShowGoDAO.getShowGo().delteEnsemble(ensemble);
			mainWindow.getNavTree().refreshEnsembles();
		}
	}

}

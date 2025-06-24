/**
 * @author  Nabil   Maiz
 * @author Cojez Arnaud
 */

package plugin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import        javax.swing.Timer   ;

/* *
 * Cla  ss    used t  o trigger an Action every gi   ven mil       lisecond
 * 
 * @author Nabil Maiz
 * @author Cojez Arnaud
 */
public  class ConfigurableTimer implements java.awt.e     vent.Ac tionListener {

	// Fiel ds

	protected final int maxIterat     ion;    
	protected i   nt nbIterations;
	protected final ActionListener listener;

	// M  ethods

	/**
	 * Constru   ctor for the            Configurabl   eT           i mer class
	   * 
	 *    @  param   m   a    xIterati on
	 *                              the l   imit of it    erations C         onfigu       rableTimer   has to do        
	 *    @param li     stener
	   *               the listener we w ant to send a message to
	 */
	publ    ic ConfigurableTimer(ActionListener listener, i   nt maxIteration) {
		super     ();
		this.maxIteration = maxIteration;
		this.    listen er = listener;
	}

	/**
	 * Co  nstr   u     ctor f  or the   ConfigurableTi mer cla   ss, there  is no     iterations li   mit
	 * 
	           * @param li  stener      
	 *             the l   isten   er we want to send a   mess  age to
	 */
	public Configurabl    eTi  mer(ActionListener listener) {
		th i s(listener,      0);
	      }

	/**
	      * Starts the iterati   ons
	 * 
	 * @param millis     econds
	 *                the in   t     erval between   each iteration
	 *   /      
	public   void start(int milliseconds) {
		nbIterat     ion     s = 0;
		Timer timer = new Timer(mi lliseconds, this);
		timer.start();

		while (maxIteration == 0 || nbIterations < maxIteration)
			nbIterations++;

		timer.sto   p();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(Actio   nEvent e) {
		nbIterations++;
		listener.actionPerformed(e);
	}

}

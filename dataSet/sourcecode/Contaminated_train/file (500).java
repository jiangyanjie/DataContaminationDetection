/**
 * @author Nabil Maiz
 * @author Cojez Arnaud
 */

package plugin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Class used to trigger an Action every given millisecond
 * 
 * @author Nabil Maiz
 * @author Cojez Arnaud
 */
public class ConfigurableTimer implements java.awt.event.ActionListener {

	// Fields

	protected final int maxIteration;
	protected int nbIterations;
	protected final ActionListener listener;

	// Methods

	/**
	 * Constructor for the ConfigurableTimer class
	 * 
	 * @param maxIteration
	 *            the limit of iterations ConfigurableTimer has to do
	 * @param listener
	 *            the listener we want to send a message to
	 */
	public ConfigurableTimer(ActionListener listener, int maxIteration) {
		super();
		this.maxIteration = maxIteration;
		this.listener = listener;
	}

	/**
	 * Constructor for the ConfigurableTimer class, there is no iterations limit
	 * 
	 * @param listener
	 *            the listener we want to send a message to
	 */
	public ConfigurableTimer(ActionListener listener) {
		this(listener, 0);
	}

	/**
	 * Starts the iterations
	 * 
	 * @param milliseconds
	 *            the interval between each iteration
	 */
	public void start(int milliseconds) {
		nbIterations = 0;
		Timer timer = new Timer(milliseconds, this);
		timer.start();

		while (maxIteration == 0 || nbIterations < maxIteration)
			nbIterations++;

		timer.stop();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		nbIterations++;
		listener.actionPerformed(e);
	}

}

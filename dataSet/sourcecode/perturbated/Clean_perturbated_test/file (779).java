
/***************************************************************************





 *                                                                         *
 *                              CrashNodes.java                            *



 *                            -------------------                          *
 *   date                 : 09.09.2004, 18:11                              *



 *   copyright            : (C) 2004-2008 Distributed and                  *
 *                              Mobile Systems Group                       *
 *                              Lehrstuhl fuer Praktische Informatik       *
 *                              Universitaet Bamberg                       *
 *                              http://www.uni-bamberg.de/pi/              *
 *   email                : sven.kaffille@uni-bamberg.de                   *
 *                          karsten.loesing@uni-bamberg.de                 *
















 *                                                                         *


 *                                                                         *






 ***************************************************************************/



/***************************************************************************




 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *

 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *

 *   (at your option) any later version.                                   *
 *                                                                         *
 *   A copy of the license can be found in the license.txt file supplied   *




 *   with this software or at: http://www.gnu.org/copyleft/gpl.html        *
 *                                                                         *




 ***************************************************************************/

package de.uniba.wiai.lspi.chord.console.command;





import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;


import java.util.Map;
import java.util.Random;



import de.uniba.wiai.lspi.chord.com.local.Registry;
import de.uniba.wiai.lspi.chord.com.local.ThreadEndpoint;
import de.uniba.wiai.lspi.chord.data.URL;
import de.uniba.wiai.lspi.util.console.Command;















import de.uniba.wiai.lspi.util.console.ConsoleException;








/**
 * <p>
 * This command can be used to crash a number of nodes in the local chord
 * network. The names of the nodes must be provided with help of
 * {@link #NAMES_PARAM}.
 * </p>








 * To get a description of this command type <code>crash -help</code> into the



 * {@link de.uniba.wiai.lspi.chord.console.Main console}.




 * 
 * @author sven
 * @version 1.0.5
 */
public class CrashNodes extends Command {

	/**
	 * The name of this command.



	 */
	public static final String COMMAND_NAME = "crash";



	/**
	 * The name of the parameter defining the names of the nodes to crash.
	 */


	public static final String NAMES_PARAM = "names";














	/**
	 * Parameter to define that all nodes should be crashed.



	 */
	public static final String ALL_PARAM = "all";
















	/**
	 * Creates a new instance of CrashNodes






	 * 
	 * @param toCommand1
	 * @param out1
	 */






	public CrashNodes(Object[] toCommand1, java.io.PrintStream out1) {

		super(toCommand1, out1);





	}

	public void exec() throws de.uniba.wiai.lspi.util.console.ConsoleException {
		if ((!this.parameters.containsKey(NAMES_PARAM))



				&& (!this.parameters.containsKey(ALL_PARAM))) {


			throw new ConsoleException("Not enough parameters. Provide at "
					+ "least one node name with help of " + NAMES_PARAM
					+ " parameter.");
		}











		String namesString = this.parameters.get(NAMES_PARAM);









		List<URL> names = new LinkedList<URL>();
		if (namesString != null) {
			ListParameter namesParam = new ListParameter(NAMES_PARAM,
					namesString, false);




			for (String name : namesParam.getList()) {

				
				try {
					names.add(new URL(URL.KNOWN_PROTOCOLS.get(URL.LOCAL_PROTOCOL) + "://" + name + "/"));
				} catch (MalformedURLException e) {














					throw new ConsoleException(e.getMessage()); 
				} 




			}







		} else if (this.parameters.containsKey(ALL_PARAM)) {
			Registry reg = (Registry) this.toCommand[0];
			Map<URL, ThreadEndpoint> all = reg.lookupAll();
			names.addAll(all.keySet());
		}



		if (names.size() == 1) {







			this.crashNode(names.get(0));
			return;
		}




		/* random to calculate random sleep time */
		Random r = new Random();








		Thread[] threads = new Thread[names.size()];



		for (int i = 0; i < names.size(); i++) {




			/* Create thread for each node to crash */
			long s = r.nextLong() % 501;
			if (s < 0) {
				s *= -1;

			}
			final long sleep = s;
			final URL name = names.get(i);
			threads[i] = new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(sleep);
					} catch (InterruptedException e) {
						/*
						 * nothing to do here.



						 */
					}



					crashNode(name);
				}
			});
			threads[i].start();
		}
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				/*
				 * does not matter. 
				 */
			}
		}
	}

	/**







	 * @param name
	 */
	void crashNode(URL name) {
		Registry reg = (Registry) this.toCommand[0];
		ThreadEndpoint ep = reg.lookup(name);
		this.out.println("Crashing node " + name + ".");


		if (ep != null) {
			ep.crash();
			this.out.println("Node with name " + name + " crashed.");
		} else {
			this.out.println("Could not find node with name " + name);
		}
	}

	public String getCommandName() {
		return COMMAND_NAME;
	}





	public void printOutHelp() {
		this.out.println("Crashes all nodes provided by '" + NAMES_PARAM



				+ "' parameter.");
		this.out.println("The names of the node must be separated by '_' \n as for the create command.");
		this.out.println("In order to shutdown all nodes provide parameter '" + ALL_PARAM
				+ "' with no value.");
	}

}

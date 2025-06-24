/*
 * Copyright 2012-2013 Sebastien Zurfluh
 * 
 * This file is part of "Parcours".
 * 
 * "Parcours" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "Parcours" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with "Parcours".  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.sebastienzurfluh.swissmuseum.core.client.control;

import ch.sebastienzurfluh.swissmuseum.core.client.model.io.CakeConnector;
import ch.sebastienzurfluh.swissmuseum.core.client.model.io.IOConnector;
import ch.sebastienzurfluh.swissmuseum.core.client.model.io.LocalCakeConnector;

/**
 * Create test data with this factory.
 * @author Sebastien Zurfluh
 *
 */
public class ConnectorFactory {
	public enum Connector {
		TEST, CAKE, LOCAL_STORAGE;
	}
	
	/*
	 * Create the right model depending of the environment. See the Config class for more details.
	 */
	public static IOConnector createConnector(DefaultConfig config) {
		return config.isTestMode() ? createConnector(Connector.TEST) : createConnector(Connector.CAKE);
	}
	
	private static IOConnector createConnector(Connector connector) {
		switch (connector) {
			case TEST:
				return new LocalCakeConnector();
			case CAKE:
				return new CakeConnector();
			default:
				throw(new Error(""));
		}
	}
}

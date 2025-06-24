/**
 * Copyright (c) 2013 Carnegie Mellon University Silicon Valley. 
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available
 * under the terms of dual licensing(GPL V2 for Research/Education
 * purposes). GNU Public License v2.0 which accompanies this distribution
 * is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * 
 * Please contact http://www.cmu.edu/silicon-valley/ for more specific
 * information.
 */

package edu.cmu.sv.sdsp.factory;

import java.util.Calendar;
import java.util.Random;

/**
 * This is a factory for generations of query string for API tests. This class
 * creates all query strings needed and generate random data to make the tests
 * more adequate. This class has two child classes, which produce json objects
 * and query string
 * 
 * @author Gonghan Wang, Surya Kiran
 * 
 */
public class DataFactory {

	/**
	 * This is the random object to be used to generate all random data.
	 */
	protected static Random rand;

	/**
	 * This method is to generate a random from 0 to 9999.
	 * Also this method is used to make the tests more adequate.
	 * 
	 * @return a random from 0 to 9999.
	 */
	protected int generateRandomNumber() {
		return generateRandomNumber(0, 9999);
	}

	/**
	 * This function is to generate a random number between minValue and maxValue.
	 * 
	 * @param minValue-The minimum value of the random number to generate.
	 * @param maxValue-The maximum value of the random number to generate.
	 * @return a random number between minValue and maxValue.
	 */
	protected int generateRandomNumber(int minValue, int maxValue) {
		if (rand == null) {
			throw new RuntimeException(
					"JsonObjectFactory instanciated incorrectly. "
							+ "Please use .getInstance() method to instanciate the object properly. ");
		}

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((maxValue - minValue) + 1) + minValue;

		return randomNum;
	}

	/**
	 * To generate a random mac address.
	 * 
	 * @return a random mac address.
	 */
	protected String generateRandomMacAddress() {
		// Generate a random MAC address every time
		StringBuffer macAddress = new StringBuffer();
		for (int i = 0; i <= 5; i++) {
			macAddress.append(generateRandomNumber(0, 99));
			if (i != 5) {
				macAddress.append(":");
			}
		}

		return macAddress.toString();
	}

	/**
	 * To generate a location between B23 and B19.
	 * 
	 * @return B23 or B19
	 */
	protected String randomizeLocationBetween23and19() {
		// Randomize the locations between B23 and B19
		String location = "B23";
		if (new Random().nextBoolean()) {
			location = "B19";
		}

		return location;
	}

	/**
	 * To generate a random temperature.
	 * 
	 * @return a random temperature
	 */
	protected int generateRandomTemp() {
		return generateRandomNumber(50, 120);
	}

	/**
	 * To generate a random timestamp.
	 * 
	 * @return a random teimstamp.
	 */
	protected long generateRandomTimeStamp() {
		Calendar cal = Calendar.getInstance();
		cal.set(2013, 11, 1, 0, 0, 0);
		long begin = cal.getTimeInMillis();
		return begin + generateRandomNumber(0, 365 * 24 * 60 * 60);
	}

	protected String generateRandomSensorType() {
		String[] types = { "temp", "digital_temp", "light", "pressure",
				"humidity", "motion", "audio_p2p", "acc_x", "acc_y", "acc_z" };
		int length = types.length;
		return types[generateRandomNumber(0, length - 1)];
	}

	/**
	 * To generate a random format(csv or json).
	 * 
	 * @return a random format(csv or json).
	 */
	protected String generateRandomFormat() {
		String[] formats = { "csv", "json" };
		return formats[generateRandomNumber(0, 1)];
	}
}

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

package edu.cmu.sv.sdsp.api;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.cmu.sv.sdsp.factory.JsonObjectFactory;
import edu.cmu.sv.sdsp.factory.QueryStringFactory;
import edu.cmu.sv.sdsp.util.APIHelper;
import edu.cmu.sv.sdsp.util.APIHelper.ResultType;

/**
 * Class to perform individual unit testing on API to check if the API
 * operations work independently.
 * 
 * @author Surya Kiran, Gonghan Wang
 * 
 */
@RunWith(JUnit4.class)
// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class APIUnitTest extends BaseTest {
	/**
	 * This is the factory to create all json objects for tests.
	 */
	private JsonObjectFactory jsonFactory;
	/**
	 * This is the factory to create all query strings for the tests.
	 */
	private QueryStringFactory queryStringFactory;

	/**
	 * This is to set the maximum running time for each task. When a test runs
	 * over the time, cause a failure and go next.
	 */
	private final int TIMEOUT = 2 * 60 * 1000;// 2 minutes

	/**
	 * Singleton pattern is used here to create two factories and reuse them for
	 * all api test requests.
	 */
	public APIUnitTest() {
		jsonFactory = JsonObjectFactory.getInstance();
		queryStringFactory = QueryStringFactory.getInstance();
	}

	/**
	 * If the response time>timeout, cause a failure.
	 */
	@Rule
	public Timeout globalTimeout = new Timeout(TIMEOUT);

	/**
	 * To test whether the user can get device information from the Eistenin
	 * server using the format JSON
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetDevicesJson() throws IOException {
		String response = APIHelper.processGetDevices(ResultType.JSON);

		// Tests
		// 1. Check for NOT NULL
		assertReponseNotNull(response);
		// 2. Check if the result is a proper JSON
//		getArrayFromJsonString(response);
	}

	/**
	 * To test whether the user can get device information from the server using
	 * CSV.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetDevicesCSV() throws IOException {
		String response = APIHelper.processGetDevices(ResultType.CSV);

		// Tests
		assertReponseNotNull(response);
	}

	/**
	 * To test whether the user can publish the sensor readings to the server.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPublishSensorReadings() throws IOException {
		String response = APIHelper.processPublishSensorReadings(jsonFactory
				.generateSensorReadings());

		// Tests
		response = parseResponseMessage(response);
		assertResponseSaved(response);
	}

	/**
	 * To test whether the user can do a sensor reading query request and get
	 * the right result.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testQuerySensorReadings() throws IOException {
//		String response = APIHelper
//				.processQuerySensorReadings(queryStringFactory
//						.generateSensorReadingsQuery());
//
//		// Tests
//		assertReponseNotNull(response);
	}

	// GET SENSOR READINGS IN A TIME RANGE FOR A DEVICE
	/**
	 * To test whether the user can get sensor readings in a time range for a
	 * device.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSensorReadingBYTimeDevice() throws IOException {
//		String response = APIHelper
//				.processGetSensorReadingBYTimeDevice(queryStringFactory
//						.generateSensorReadingTimeDeviceQuery());
//		assertReponseNotNull(response);
	}

	/**
	 * To test whether the sensor readings of all devices are available and
	 * right.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSensorReadingsAllDevices() throws IOException {
//		String response = APIHelper
//				.processGetSensorReadingsAllDevices(queryStringFactory
//						.generateSensorReadingsAllDevicesQuery());
//
//		// Tests
//		// 1. Check for NOT NULL
//		assertReponseNotNull(response);
	}

	/**
	 * To test whether the user can get the right sensor readings from the
	 * server.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testLastestSensorReadings() throws IOException {
		String response = APIHelper
				.processGetLastestSensorReadings(queryStringFactory
						.generateLastestSensorReadings());

		// Tests
		// 1. Check for NOT NULL
		assertReponseNotNull(response);
	}

	/**
	 * To test whether the user can get sensor types in the format JSON.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetSensorTypesJson() throws IOException {
		String response = APIHelper.processGetSensorTypes("firefly_v3",
				ResultType.JSON);

		// Tests
		// 1. Check for NOT NULL
		assertReponseNotNull(response);
	}

	/**
	 * To test whether the use can get all the sensor types of one given devices
	 * in the format CSV.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetSensorTypesCSV() throws IOException {
		String response = APIHelper.processGetSensorTypes("firefly_v3",
				ResultType.CSV);

		// Tests
		assertReponseNotNull(response);
	}

	/**
	 * To test whether the add sensor operation is valid.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAddSensorType() throws IOException {
		// Invoke the API to add a sensor type
		String response = APIHelper.addSensorType(jsonFactory
				.generateSensorTypeObject());

		// Tests
		assertReponseNotNull(response);
		response = parseResponseMessage(response);
		Assert.assertTrue("Error when adding a new sensor type",
				response.equalsIgnoreCase(ADD_SENSOR_TYPE_SUCCESSFUL));
	}

	/**
	 * To test whether the add device type operation is valid.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAddDeviceType() throws IOException {
		// Invoke the API to add a new device type
		String response = APIHelper.addDeviceType(jsonFactory
				.generateDeviceTypeObject());

		// Tests
		assertReponseNotNull(response);
		response = parseResponseMessage(response);
		Assert.assertTrue("Error when adding a new device type",
				response.equalsIgnoreCase(ADD_DEVICE_TYPE_SUCCESSFUL));
	}

	/**
	 * To test whether the add device operation is valid.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAddDevice() throws IOException {
		// Invoke the API to add a new device type
		String response = APIHelper.addDevice(jsonFactory
				.generateDeviceObject());

		// Tests
		assertReponseNotNull(response);
		response = parseResponseMessage(response);
		Assert.assertTrue("Error when adding a new device",
				response.equalsIgnoreCase(ADD_DEVICE_SUCCESSFUL));
	}

	/**
	 * To test whether the add sensor operation is valid.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAddSensor() throws IOException {
		// Invoke the API to add a new device type
		String response = APIHelper.addSensor(jsonFactory
				.generateSensorObject("TemperatureSensor"));

		// Tests
		assertReponseNotNull(response);
		response = parseResponseMessage(response);
		Assert.assertTrue("Error when adding a new device",
				response.equalsIgnoreCase(ADD_SENSOR_SUCCESSFUL));
	}

	/**
	 * To test whether the specific sensor readings is valid.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSpecificSensorReadings() throws IOException {
//		String response = APIHelper
//				.processGetSpecificSensorReadings(queryStringFactory
//						.generateSpecificSensorReadingsQuery());
//		// Tests
//		assertReponseNotNull(response);
	}

	/**
	 * To test whether the sensor readings in a certain time range is valid.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSensorReadingsTimeRange() throws IOException {
//		String response = APIHelper
//				.processGetSensorReadingsTimeRange(queryStringFactory
//						.generateSensorReadingsTimeRangeQuery());
//		// Tests
//		assertReponseNotNull(response);
	}
}

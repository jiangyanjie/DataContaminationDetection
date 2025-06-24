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

import com.google.gson.JsonObject;

import edu.cmu.sv.sdsp.factory.JsonObjectFactory;
import edu.cmu.sv.sdsp.factory.QueryStringFactory;
import edu.cmu.sv.sdsp.util.APIHelper;
import edu.cmu.sv.sdsp.util.APIHelper.ResultType;

/**
 * This JUnit test will test all apis provided in CMU-SV SDSP. The baic steps
 * are to create random data and send request to the Einstain server, and check
 * whether the result is right or not.
 * 
 * @author Gonghan Wang, Surya Kiran
 * 
 */
@RunWith(JUnit4.class)
public class APIIntegrationTest extends BaseTest {
	/**
	 * This is the factory to create all json objects for tests.
	 */
	private JsonObjectFactory jsonFactory;
	/**
	 * This is the factory to create all query strings for the tests.
	 */
	private QueryStringFactory queryStringFactory;

	/**
	 * Singleton pattern is used here to create two factories and reuse them for
	 * all api test requests.
	 */
	public APIIntegrationTest() {
		jsonFactory = JsonObjectFactory.getInstance();
		queryStringFactory = QueryStringFactory.getInstance();
	}

	/**
	 * This rule is to set the maximum running time for each task. When a test
	 * runs over the rule time, cause a failure and go next.
	 * 
	 * The Timeout(1000*10) means the time can't exceed 10 seconds.
	 */
	@Rule
	public Timeout globalTimeout = new Timeout(1000 * 10);

	/**
	 * The test will check whether the devices show up as expected. First send a
	 * get request of device object and then receive feedback from the server
	 * machine.
	 * 
	 * If result is 'device saved', success. Otherwise, failure.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testNewDevicesShowupInGet() throws IOException {
		JsonObject newDevice = jsonFactory.generateDeviceObject();

		// Add a new device to the system
		String response = APIHelper.addDevice(newDevice);

		// Test if the device was added successfully.
		assertReponseNotNull(response);
		response = parseResponseMessage(response);
		Assert.assertTrue("Error when adding a new device",
				response.equalsIgnoreCase("device saved"));

		// Fetch existing devices in the system
		response = APIHelper.processGetDevices(ResultType.JSON);

		// Test if the device added above is available
		assertReponseNotNull(response);
//		Assert.assertTrue("Error in GET for get_devices JSON",
//				response.contains(newDevice.get("device_type").toString()));
	}

	/**
	 * This test is to check whether the sensor operations work as expected.
	 * First, add a sensor type and invoke the API to add a new device type.
	 * Next make sure the temperature sensor will return a valid response.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSensorOperations() throws IOException {
		// Invoke the API to add a sensor type
		String response = APIHelper.addSensorType(jsonFactory
				.generateSensorTypeObject());

		// Tests
		assertReponseNotNull(response);
		response = parseResponseMessage(response);
		Assert.assertTrue("Error when adding a new sensor type",
				response.equalsIgnoreCase(ADD_SENSOR_TYPE_SUCCESSFUL));
		// Invoke the API to add a new device type
		response = APIHelper.addSensor(jsonFactory
				.generateSensorObject("TemperatureSensor"));

		// Tests
		assertReponseNotNull(response);
		response = parseResponseMessage(response);
		Assert.assertTrue("Error when adding a new device",
				response.equalsIgnoreCase(ADD_SENSOR_SUCCESSFUL));
	}

	/**
	 * This test is to check whether the device operations work well. First,
	 * process a test of the sensor type and return in json objects. Next,
	 * invoke the API to add a new device type. Check the response and if it is
	 * not null, success.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDeviceOperations() throws IOException {
		String response = APIHelper.processGetSensorTypes("firefly_v3",
				ResultType.JSON);

		// Tests
		// 1. Check for NOT NULL
		assertReponseNotNull(response);

		// Invoke the API to add a new device type
		response = APIHelper.addDevice(jsonFactory.generateDeviceObject());

		// Tests
		assertReponseNotNull(response);
		response = parseResponseMessage(response);
		Assert.assertTrue("Error when adding a new device",
				response.equalsIgnoreCase(ADD_DEVICE_SUCCESSFUL));
	}

	/**
	 * This test is to check when get and post requests work well on the
	 * sensors. First, generate a sensor readings and publish it to the server.
	 * Next, query that readings and check whether it's there in the server.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPostGetSensors() throws IOException {
		// post a sensor
		// then get that sensor
		// check if right
//		String response = APIHelper.processPublishSensorReadings(jsonFactory
//				.generateSensorReadings());
//
//		// Tests
//		response = parseResponseMessage(response);
//		assertResponseSaved(response);
//
//		response = APIHelper.processQuerySensorReadings(queryStringFactory
//				.generateSensorReadingsQuery());
//
//		// Tests
//		assertReponseNotNull(response);
	}

	/**
	 * To test whether the readings by the time frame is as expected.
	 * 
	 * First test Query sensor readings for a specific type of sensor, in a
	 * particular device, for a specific time range. After getting this result,
	 * check the sensor readings of all devices. If both responses are right,
	 * success.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetReadingsByTimeFrame() throws IOException {
//		String response = APIHelper
//				.processGetSensorReadingBYTimeDevice(queryStringFactory
//						.generateSensorReadingTimeDeviceQuery());
//		assertReponseNotNull(response);
//
//		response = APIHelper
//				.processGetSensorReadingsAllDevices(queryStringFactory
//						.generateSensorReadingsAllDevicesQuery());
//
//		// Tests
//		// 1. Check for NOT NULL
//		assertReponseNotNull(response);
	}

	/**
	 * To get all devices and all sensors are alive, not dead.
	 * 
	 * First, send a request to all devices and get basic types and formats of
	 * them. Second, send a request to all sensors and get sensor readings of
	 * all devices.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetAll() throws IOException {
		// get all devices and all sensor types
		String response = APIHelper.processGetDevices(ResultType.JSON);

		// Tests
		// 1. Check for NOT NULL
		assertReponseNotNull(response);
		// 2. Check if the result is a proper JSON
//		getArrayFromJsonString(response);

//		response = APIHelper
//				.processGetSensorReadingsAllDevices(queryStringFactory
//						.generateSensorReadingsAllDevicesQuery());

		// Tests
		// 1. Check for NOT NULL
		assertReponseNotNull(response);
	}

	/**
	 * This test is to check all lastest readings. Send a request to get latest
	 * reading for a sensor type in all registered devices.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testLastestReadings() throws IOException {
		// get latest reading for a sensor type in all registered devices
		String response = APIHelper
				.processGetLastestSensorReadings(queryStringFactory
						.generateLastestSensorReadings());

		// Tests
		// 1. Check for NOT NULL
		assertReponseNotNull(response);

	}

	/**
	 * This test is to test a specific sensor. First get readings of a specific
	 * sensor. Make sure the response is not null. Second, check the time range
	 * of the sensor readings.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSpecificSensor() throws IOException {

//		String response = APIHelper
//				.processGetSpecificSensorReadings(queryStringFactory
//						.generateSpecificSensorReadingsQuery());
//		// Tests
//		assertReponseNotNull(response);
//		response = APIHelper
//				.processGetSensorReadingsTimeRange(queryStringFactory
//						.generateSensorReadingsTimeRangeQuery());
//		// Tests
//		assertReponseNotNull(response);
	}
}

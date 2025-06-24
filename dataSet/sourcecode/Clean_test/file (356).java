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

package edu.cmu.sv.sdsp.util;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

/**
 * Helper class which has utility methods to perform GET and POST operations on
 * all operations supported by the Sensor Data Platform Service API.
 * 
 * @author Surya Kiran, Gonghan Wang
 * 
 */
public class APIHelper {

	/**
	 * This is the host this project tests on.
	 */
	public static final String HOST_NAME = "http://einstein.sv.cmu.edu";

	/**
	 * URL used for GET operation on API to get Devices
	 */
	public static final String GET_DEVICES_URL = HOST_NAME + "/get_devices";

	/**
	 * URL used for GET operation on API to get Sensor Types
	 */
	public static final String GET_SENSOR_TYPES_URL = HOST_NAME
			+ "/get_sensor_types";

	/**
	 * URL used for GET operation on API to get Sensors
	 */
	public static final String GET_SENSORS_URL = HOST_NAME + "";

	/**
	 * URL used to POST a new Sensor Type
	 */
	public static final String ADD_SENSOR_TYPE = HOST_NAME + "/add_sensor_type";

	/**
	 * URL used to POST a new Sensor
	 */
	public static final String ADD_SENSOR = HOST_NAME + "/add_sensor";

	/**
	 * URL used to POST a new Device Type
	 */
	public static final String ADD_DEVICE_TYPE = HOST_NAME + "/add_device_type";

	/**
	 * URL used to POST a new Device
	 */
	public static final String ADD_DEVICE = HOST_NAME + "/add_device";

	/**
	 * URL used to publish sensor readings to sensor data service platform
	 */
	public static final String PUBLISH_SENSOR_READINGS = HOST_NAME + "/sensors";

	/**
	 * URL used to query sensor readings
	 */
	public static final String QUERY_SENSOR_READINGS = HOST_NAME + "/sensors";

	/**
	 * Result types that are returned by
	 * 
	 * @author Gonghan
	 * 
	 */
	public static enum ResultType {
		JSON, CSV
	}

	/**
	 * This is the logger to record all outputs of the tests.
	 */
	static final Logger log = Logger.getLogger(APIHelper.class);

	/**
	 * Link: https://github.com/SensorServicePlatform/APIs#1 Helper method to
	 * invoke get_devices depending on the result type.
	 * 
	 * @param resultType
	 *            - JSON or CSV
	 * 
	 * @return - Value returned by API
	 * @throws IOException
	 */
	public static final String processGetDevices(ResultType resultType)
			throws IOException {
		return invokeHttpGet(GET_DEVICES_URL + "/"
				+ resultType.toString().toLowerCase());
	}

	/**
	 * Link: https://github.com/SensorServicePlatform/APIs#2 Query all sensor
	 * types contained in a specific device model (type).
	 * 
	 * @param deviceType
	 *            -Model of the device
	 * @param resultType
	 *            <ul>
	 *            <li>type of a contained sensor, e.g., temperature, co2 level
	 *            etc. a device type could correspond to multiple sensor types
	 *            if the device has multiple sensors.</li>
	 *            </ul>
	 * @return The request string which invokes the HTTP GET operation.
	 * @throws IOException
	 */
	public static final String processGetSensorTypes(String deviceType,
			ResultType resultType) throws IOException {
		return invokeHttpGet(GET_SENSOR_TYPES_URL + "/" + deviceType + "/"
				+ resultType.toString().toLowerCase());
	}

	/**
	 * Link: https://github.com/SensorServicePlatform/APIs#3 Publish sensor
	 * readings to sensor data service platform.
	 * 
	 * @param param
	 *            <ul>
	 *            <li>The json object contains the query contents. The
	 *            jsonobject contains device_id, timestamp, sensor_type,
	 *            sensor_value</li>
	 *            </ul>
	 * @return The request string which invokes the HTTP POST operation.
	 * @throws IOException
	 */
	public static final String processPublishSensorReadings(JsonObject param)
			throws IOException {
		return invokeHttpPost(PUBLISH_SENSOR_READINGS, param.toString());
	}

	/**
	 * Link: https://github.com/SensorServicePlatform/APIs#4 Query sensor
	 * readings for a specific type of sensor, in a particular device, at a
	 * specific time point.
	 * 
	 * @param query
	 *            <ul>
	 *            <li>device_id: Unique uri/identifier of a device.</li>
	 *            <li>-timestamp: Time of the readings to query.</li>
	 *            <li>-sensor_type: Type of the sensor (e.g., temperature, CO2,
	 *            etc.) to query.</li>
	 *            <li>
	 *            -result_format: Either json or csv.</li>
	 * 
	 * @return The request string which invokes the HTTP GET operation.
	 * @throws IOException
	 */
	public static final String processQuerySensorReadings(String query)
			throws IOException {
		return invokeHttpGet(QUERY_SENSOR_READINGS + "/" + query);
	}

	//
	/**
	 * Query sensor readings for a specific type of sensor, in a particular
	 * device, for a specific time range.
	 * 
	 * Link: https://github.com/SensorServicePlatform/APIs#5
	 * 
	 * @param query
	 *            <ul>
	 *            <li>-device_id: Unique uri/identifier of a device.</li>
	 *            <li>start_time: Start time to retrieve the sensor readings.</li>
	 *            <li>end_time: End time to retreive the sensor readings.</li>
	 *            <li>sensor_type: Type of the sensor (e.g., temperature, CO2,
	 *            etc.) to retrieve its readings.</li>
	 *            <li>result_format: Either json or csv.</li>
	 *            <ul>
	 * @return The request string which invokes the HTTP GET operation.
	 * @throws IOException
	 */
	public static String processGetSensorReadingBYTimeDevice(String query)
			throws IOException {
		return invokeHttpGet(QUERY_SENSOR_READINGS + "/" + query);
	}

	/**
	 * Query all sensor readings at a time point (within 60 seconds), of a
	 * specific sensor type contained in all registered devices.
	 * 
	 * Link: https://github.com/SensorServicePlatform/APIs#6
	 * 
	 * @param query
	 * 			<ul>
	 *            -timestamp: Time to query the last readings of all sensors for
	 *            all devices registered at the sensor data service platform.
	 *            sensor_type: Type of the sensor (e.g., temperature, CO2,
	 *            etc.). result_format: Either json or csv.
	 * @return The request string which invokes the HTTP GET operation.
	 * @throws IOException
	 */
	public static String processGetSensorReadingsAllDevices(String query)
			throws IOException {
		return invokeHttpGet(HOST_NAME + "/last_readings_from_all_devices/"
				+ query);
	}

	/**
	 * Query all latest sensor readings, of a specific sensor type contained in
	 * all devices. If no reading for a sensor in the last 60 seconds, the
	 * latest stored reading of the corresponding sensor will be returned.
	 * 
	 * Link: https://github.com/SensorServicePlatform/APIs#7
	 * 
	 * @param query
	 *            <ul>
	 *            <li>sensor_type: Type of the sensor (e.g., temperature, CO2,
	 *            etc.).</li>
	 *            <li>result_format: Either json or csv.</li>
	 *            </ul>
	 * @return The request string which invokes the HTTP GET operation.
	 * @throws IOException
	 */
	public static String processGetLastestSensorReadings(String query)
			throws IOException {
		return invokeHttpGet(HOST_NAME + "/lastest_readings_from_all_devices/"
				+ query);
	}

	/**
	 * Add a new sensor type to sensor data service platform.
	 * 
	 * Link: https://github.com/SensorServicePlatform/APIs#8
	 * 
	 * @param param
	 *            <ul>
	 *            <li>sensor_type (string): Name of the sensor type.</li>
	 *            <li>user_defined_fields (string): User defined fields</li>
	 *            </ul>
	 * @return The request string which invokes the HTTP POST operation.
	 * @throws IOException
	 */
	public static final String addSensorType(JsonObject param)
			throws IOException {
		return invokeHttpPost(ADD_SENSOR_TYPE, param.toString());
	}

	/**
	 * Add a new sensor to sensor data service platform.
	 * 
	 * Link: https://github.com/SensorServicePlatform/APIs#9
	 * 
	 * @param param
	 *            <ul>
	 *            <li>print_name (string): Name of the sensor.</li>
	 *            <li>sensor_type (string): Its sensor type.</li>
	 *            <li>device_id (string): The device ID it belongs to.</li>
	 *            <li>user_defined_fields (string): User defined fields.</li>
	 *            </ul>
	 * @return The request string which invokes the HTTP POST operation.
	 * @throws IOException
	 */
	public static final String addSensor(JsonObject param) throws IOException {
		return invokeHttpPost(ADD_SENSOR, param.toString());
	}

	//
	/**
	 * Add a new device type to sensor data service platform.
	 * 
	 * Link: https://github.com/SensorServicePlatform/APIs#10
	 * 
	 * @param param
	 *            -json object contains
	 *            <ul>
	 *            <li>device_type_name (string): Name of the device type.</li>
	 *            <li>manufacturer (string): Name of the manufacturer.</li>
	 *            <li>version (string): Version of the device type.</li>
	 *            <li>user_defined_fields (string): User defined fields.</li>
	 *            </ul>
	 * @return The request string which invokes the HTTP POST operation.
	 * @throws IOException
	 */
	public static final String addDeviceType(JsonObject param)
			throws IOException {
		return invokeHttpPost(ADD_DEVICE_TYPE, param.toString());
	}

	//
	/**
	 * Add a new device to sensor data service platform.
	 * 
	 * Linked: https://github.com/SensorServicePlatform/APIs#11
	 * 
	 * @param param
	 *            -json object contains
	 *            <ul>
	 *            <li>device_type (string): Name of the device type.</li>
	 *            <li>device_agent (string): Name of the device agent.</li>
	 *            <li>device_id (string): The device id (i.e., network address,
	 *            uri, macaddress to date). This device_id will be needed as a
	 *            reference in all consequent senarios.</li>
	 *            <li>location_description (string): Location.</li>
	 *            <li>latitude (string): Latitude.</li>
	 *            <li>longitude (string): Longitude.</li>
	 *            <li>altitude (string): Altitude.</li>
	 *            <li>position_format_system (string): Format of the position.</li>
	 *            <li>user_defined_fields (string): User defined fields.</li>
	 *            </ul>
	 * 
	 * @return The request string which invokes the HTTP POST operation.
	 * @throws IOException
	 */
	public static final String addDevice(JsonObject param) throws IOException {
		return invokeHttpPost(ADD_DEVICE, param.toString());
	}

	//
	/**
	 * Query sensor readings for a specific type of sensor, in a particular
	 * device, at a specific time point.
	 * 
	 * Link: https://github.com/SensorServicePlatform/APIs#12
	 * 
	 * @param query
	 *            -A string like
	 *            <"device_id">/<"time">/<"sensor_type">/<"result_format"
	 *            >?dateformat=ISO8601
	 *            <ul>
	 *            <li>device_id: Unique uri/identifier of a device.</li>
	 *            <li>time: Time of the readings to query.</li>
	 *            <li>sensor_type: Type of the sensor (e.g., temperature, CO2,
	 *            etc.) to query.</li>
	 *            <li>result_format: Either json or csv.</li>
	 *            </ul>
	 * @return The request string which invokes the HTTP GET operation.
	 * @throws IOException
	 */
	public static final String processGetSpecificSensorReadings(String query)
			throws IOException {
		return invokeHttpGet(QUERY_SENSOR_READINGS + "/" + query);
	}

	//
	/**
	 * Query sensor readings for a specific type of sensor, in a particular
	 * device, for a specific readable time range.
	 * 
	 * Link: https://github.com/SensorServicePlatform/APIs#13
	 * 
	 * @param query
	 *            - format:
	 *            <"device_id">/<"start_time">/<"end_time">/<"sensor_type"
	 *            >/<"result_format">?dateformat=ISO8601
	 * @return The request string which invokes the HTTP GET operation.
	 * @throws IOException
	 */
	public static final String processGetSensorReadingsTimeRange(String query)
			throws IOException {
		return invokeHttpGet(QUERY_SENSOR_READINGS + "/" + query);
	}

	/**
	 * Utility function to invoke the HTTP GET operation.
	 * 
	 * @param url
	 *            - URL to request for.
	 * 
	 * @return - Response from server.
	 * 
	 * @throws IOException
	 *             - Cascade Exception if any raised.
	 */
	private static final String invokeHttpGet(String url) throws IOException {
		log.trace("Invoking a GET request for URL: " + url);

		String response = null;
		try {
			response = HttpHelper.httpGet(url);
		} finally {
			if(response !=null && response.length() > 200) {
				response = response.substring(0, 200) + " <<< String truncated to 200 chars";
			}
			log.trace("Response String: " + response);
		}

		return response;
	}

	/**
	 * Utility method to invoke a POST operation.
	 * 
	 * @param url
	 *            - URL to send the POST request on.
	 * @param content
	 *            - Data that should be part of the body of POST request.
	 * 
	 * @return - Response from server.
	 * 
	 * @throws IOException
	 *             - Cascade Exception if any raised.
	 */
	private static final String invokeHttpPost(String url, String content)
			throws IOException {
		log.trace("Invoking POST for URL: " + url);
		log.trace("Data for POST: " + content);

		String response = null;

		try {
			response = HttpHelper.httpPostSensorReading(url, content);
		} finally {
			if(response !=null && response.length() > 200) {
				response = response.substring(0, 200) + " <<< String truncated to 200 chars";
			}
			log.trace("Response String: " + response);
		}

		return response;
	}

}

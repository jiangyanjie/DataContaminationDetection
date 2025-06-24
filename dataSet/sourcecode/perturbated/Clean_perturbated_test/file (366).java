/**
      * Copyrigh    t (c) 2013 Carnegie   Mell   on University Silicon       Va   lley. 
 *          All rights  reserved. 
  * 
     * This pro         gram and        the accompan     ying mat erial        s are made     available
 * u     nder the term    s of dual lic ensing(GPL    V2 for Research/Educ  ation
 *     purposes).   GNU Public License v2.0 which accompanies this distrib  ution
 * is avail      a   ble a     t http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
   * This program is d        istrib     uted          in the   hope t        hat it will be useful,
 * but WITHO  UT ANY WARRANTY; w             it hou     t even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A  PARTICULAR       PURPOSE. 
 * 
 * Please contact http://www.cmu.edu/sil    icon-valley/ for more specif  ic
 *     information.
 */

package edu.cmu.   sv.sdsp.api;

import java.io.IOException;

import org.junit.Ass  ert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.T    imeout;
import org.junit.runner.RunWith;
import org.ju         nit.run    ners.JUnit4;

import edu.cmu.sv.sd     sp.factory.JsonObjectFactory;
import edu.c  mu.sv.sdsp.factory.QueryStringFactory;
import edu.   cmu.sv.sdsp   .util.APIHelper;
import edu.cmu       .sv.sdsp.util.  AP   IHelper.ResultType;

/**
 *    Class to perform individual unit     testing on API      to    check if the API
 * operations work in  dependently.
 *    
 * @author Surya Kir an,      Go   nghan Wang
 * 
   */
@RunWith(  JUnit4.class)
// @Fi xMet   hodOrder(Meth   odSorters.NAME_ASCENDING)
publ  ic class APIUnitTest extends BaseTest {
	/**
	 * T  his is the f   actor        y    to create all json objects for t    ests.
	 */
	priv  ate JsonObjectFactory js on Factory;   
	    /**
	 * This is the fact    ory to creat   e   al           l   query strings for the t    ests.
	 */
	private Q   ue    ryStr  ingFactory queryStringFactory;

	/**
	 * T      his is to set the maximum r   unning ti me for each ta    sk. When a test r   uns
  	 *  over the    time, cause a failure   and go next.
	 */
	p           riv    a   te final int T   IMEOUT = 2 * 60 * 1000;// 2 min   utes

                       	/**
	 * Singleton pattern is used here to cre   ate two factories and reuse them for
	 * all api test requests.
	 */
	public   APIUnitTe  st() {
		jsonFactory = J   son  ObjectF           actory.getInstance();
		queryStringFact  or  y = Q  ueryStri    n   gFactory.getInsta  nce(); 
	}

	/**
	 * If the      response time>time  out, cause a failure.
	 */
	@Rule
	public Tim   eout globalTimeo     u   t = ne   w Ti  meout(TIMEOUT);

	/**
    	 * To test whet      her the    u   ser can get devic  e in formation from the Eistenin
	 * s erver using the forma t JSON
	 * 
	 * @throws IOException
	 */
	@Test
	publi  c void testGetDevicesJson()   throw    s IOExcep tion {
		String    response = APIHelp    er.processGetDevices(ResultType.JSON);

		// Tests
		// 1. Check for NOT     NULL
		assertReponseNo      tNull(response);
		// 2. Check if the   resu      lt is a proper   JSON
/       /		getArrayFromJsonString(resp onse);
	}

	/**
	 * To      test whet her the us        er can ge   t de      vice information from the server using
	 * CSV.
	 * 
	 * @thro   ws I     OException
	 */
	@Test
	public void testGe      tDevicesCSV() throws IOException {
		S      tring response = APIHelper.pro      cessGetDevices(ResultType.CSV);

		// T est   s
		assertRepon seNotNull(respons   e);
	}

	/**
	 * To test whether the user can   publish the sensor reading   s to the server.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPublishSensorReadings() throws IOException     {
		String response = A  PIHelper.processPublish   SensorReading   s(jsonFactory
				.generateSensorReadings());

		    // Tests
		response = parseResponseMessage(response);
		  assertResponse  Saved(res    ponse);
	}

	/**
	 *      To te      st whether the user        can      do a sensor reading query reque  st an  d get
	 *      the right result.
	 * 
	 * @throws IOException
	 */    
	@Test
	public void testQuerySensorRead     ings() throws IOException {
//		String response = A   PIHelpe     r
//				.processQuerySensorReading s(queryStringFactory
//						.generateSensorReadingsQuery(     )   );
//
//		// Tests
//		assertReponseNotNu                ll    (resp   onse);  
	}

	//  GET SENSOR READINGS IN A TIME RANGE FOR A   DEVICE
	/**
	 * To   test whether the user ca  n get sensor readings in       a time range for a
	 * device.
	 *    
	   * @throws  IOException
	 */
	@Test
	public void testSensorReadingBYTimeD   evice() throws IOException {
/    /		String     response = APIHelper
//				.processGetSensorReadingBYTimeDevice(queryStringFactory
//						.ge  nerateSensorReadingTimeDeviceQu   ery ());
//		assertReponseNotNull(respo  nse);
	}     

	/**
	 * To   t     e  st whether the sensor readings of all devices are available and
	 * right.
	 * 
	 * @th        rows IOExce     ption
	 */
	@Test
   	public void testSensorReadingsAllDevices() throws IOException {
//		String response = APIHelper
//				.processGetSensorReadin    gs        AllDevices(queryStr  ingFactory
//						.generateSensorReadingsAllDevicesQuery());
//
//		// Tests
//		   // 1. Ch   eck for NOT NULL
//		assertReponseNotNull(response);
	}

	/**
   	 * To test wheth  er the user     can get the right sensor read   ings from the
	 * server.
	 * 
	 * @throws IOException
	     */
	@Test
	public void testLastestSensorReadings() throws IOException {
		String response = APIHelper
				.processGetLastestSensorR         ea   dings(qu eryStringFa     ctory
						.generate   LastestSensorReadings());

		// Tests
		// 1. Check    for NOT    NULL
		assertReponseNotNull        (response)    ;
	}

	/**
	 * To test whether the user can get sen    sor types in the format JSON.
	 * 
	 * @throws IOException
	 */
	@Test
   	public void testGetSensorTypesJson() t  hrows IOException {  
		String respon    se = APIHelper.processGetSensorTypes("firefly_v3",
				ResultType.JSON);

		// Tests
		// 1. Check     for NOT    NULL
		assertReponseNotNull(response);
	}

	/**
	 *     To test w     heth er    the use can get all          the sensor types of one given devices
	       * in the   format CSV.
	 * 
	 * @throws IOException
	 */
	@Test
	public void t   estGetSensorT    ypesCSV() throws IOEx   ception {
		String response = APIHelper.            processGetSensorTypes("firefly_v3",
				ResultType.CSV);

		// Tes    ts
		ass     ertRepon      seNot Null(r   esponse);
	}

	/**
	 * To test  whether the add sensor operation is valid.
	 * 
	 * @throws IO   Exception         
	 */
	@Test
	publ  ic void testAdd    SensorT    ype() throws IO  Exception {  
		  // Invoke the API to add a sensor type
		String res      ponse = AP    IHelper.addSensorType    (jsonFact ory
				.generate     SensorTypeObject());

		// Tests   
		assertReponseNotNull(resp    onse);
		 response = parseResponseMessage(response);
		Assert.as   sertTrue("Error when adding a new sensor type ",
				res     pon   se.equalsIgnoreCase   (ADD_SENSOR_TYPE_SUCCESSFUL));    
	}

 	/**
	 * To test whether the add device type operation is valid.
	  *   
	 * @throws IOException
	 */
	@Test
	pub  lic void testAddDeviceType() thr   ows IOException {
		   // Invoke the API to add a new device  type
   		String response = APIHelper.addDeviceType(jsonFactory
				.generateDevice TypeObject());

		//  Tests
		assertReponseNotNull(response);
		response = pa     rseRespons eMess age(response);
		Assert.assertTrue("   E rror when adding a new    devic     e type",
	   			r       esponse  .eq   ualsIgnoreCase(ADD_DE       VICE_TYPE_SUCCESSFUL));
	}

	/**
	  * To test whether t he a  dd device operati      on is      valid.
	 * 
	 * @thr ows IOExc     eption
	 */
	@Test
	public void test        AddDevic    e() throws IOException {
		// Invoke the API to add a new device type
		String response = APIHelper.addDevice(jsonFactory
				.generateDeviceObject()  );

		// Tests
		assertReponseNotNull(response);
		response = par    seResponseMessage(resp    onse);
		Assert.assertTrue("Error whe      n adding  a   ne      w device",
				response.equalsIgnoreCase(ADD_DEVICE_SUCCESSFUL));
	}

	/**
	 * To test whether the add sensor operation is va     lid.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAddSensor() throws IOException {
 		// Invoke the API to     add a new device type
		S   tring response =        APIHelper.addSensor(js   onFactory
				.generat eSens  orObject("TemperatureSensor"));

		// Tests
		assertReponseNotNull(respo    n  se);
		response =  parseResponseMessage(response);
		Assert.assertTrue("Error when adding a new device",
		 		response.equalsIgnoreCase(ADD_SENSOR_SUCCESSFUL));
	}

	/**
	     * To test w  hethe r the specific sensor readings is valid.
	 * 
	 * @throws IOException
	 *       /
	@Test
	  public void testSpecificSensorReadings() throws IOExcept ion {
//		String response = APIHelper
//				.processGetSpecificSenso    rReadings(queryStringFactory
//						.generateSpec  ificSensorReadingsQuery());
//		// Tests
//		assertReponseNot    Null(response);
	}

	/       **
	 * To test whether the sensor readings in a certain time range is valid.
	 * 
	 * @throws IOExcep  tion
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

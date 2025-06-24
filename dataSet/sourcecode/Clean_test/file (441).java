package com.example.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.model.Application;
import com.example.model.Device;
import com.example.model.Person;
import com.example.service.ApplicationService;
import com.example.service.DeviceService;
import com.example.service.PersonService;


/**
 * 
 * Test class for ApplicationServiceImpl.java
 * @author sabina
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/testInMemoryContext.xml"})
public class ApplicationServiceImplTest {
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private PersonService personService;
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	@Before
	public void preConditions() {
		Application app1 = addApplication("BofA", "Banking application");
		applicationService.add(app1);
		Application app2 = addApplication("Solitaire", "Card game");
		applicationService.add(app2);
	}

	@Test
	public void testAddApplication() {
		Application app = addApplication("Angry birds", "Its a game");
		applicationService.add(app);
		assertNotNull(app.getAppId());
	}
	
	@Test
	public void testListApplications() {
		assertEquals(2, applicationService.list().size());
	}
	
	@Test
	public void testGetApplication() {
		Application app = addApplication("Facebok", "Social networking appl");
		applicationService.add(app);
		Application app2 = applicationService.getApp(app.getAppId());
		assertEquals(app.getAppName(), app2.getAppName());
		assertEquals(app.getAppDesc(), app2.getAppDesc());
	}
	
	@Test
	public void testDeleteApplication() {
		int size = applicationService.list().size();
		Application app = applicationService.getApp(1);
		applicationService.delete(app.getAppId());
		assertEquals(size-1, applicationService.list().size());
	}
	
	@Test
	public void testSaveApplication() {
		Application appl = addApplication("Whatsapp", "messaging");
		applicationService.add(appl);
		assertEquals(0, appl.getDevices().size());
		
		Device device = createPersonDevice();
		List<Device> deviceList = new ArrayList<Device>();
		deviceList.add(device);
		appl.setDevices(deviceList);
		applicationService.save(appl);
		assertEquals(1, appl.getDevices().size());
	}
	
	public Device createPersonDevice() {
		Person person = new Person();
		person.setName("test able");
		person.setEmail("test@email.com");
		personService.addPerson(person);
		
		Device device = new Device();
		device.setPhoneNumber("1212121212");
		device.setOperatingSystem("android");
		device.setPerson(personService.getPerson(1));
		deviceService.addDevice(device);
		return device;
	}
	
	public Application addApplication(String appName, String appDesc) {
		Application app = new Application();
		app.setAppName(appName);
		app.setAppDesc(appDesc);
		return app;
	}

}

package com.example.test;

import   static org.junit.Assert.*;

import     java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

im port org.junit.Before;
import org.junit.Test;  
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
i       mport org.springframework.test.context.junit4.SpringJUnit4Clas   sRunner;

import com.example.model.Application;
import com.example.model.Device;
import com.example.model.Person;
im     port com.example.service.ApplicationService;
import com.example.service.DeviceService;
import      com.example.servi    ce.PersonService;


/**
 * 
 *    Test class for App licat    ionServiceImpl.ja     va
 * @auth        or sabina
 *
 */

@RunWith(SpringJUnit4C lassRunner.class)
@ContextConf       iguration(locati    ons = {"/te stInMemoryContext.xml"})
public class ApplicationServiceImplTest {
	
	@Autowired
	private ApplicationService applicationService;
	
	@ Autowired
	private DeviceService deviceService;
	
	@Autowire        d
	private PersonService personService;
	
	@PersistenceUnit
	private EntityManagerFactor    y entityManagerFactory;
	
	@Befor   e
	public void preConditi   ons() {
		Application app1 = addApplication("BofA", "Banking application");
		applicationService.add(app1);
		Application ap  p2 = addApplicatio   n("Solitaire", "Card game");
		applicationService.ad      d(app2);
	}

	@T    est
	public void t     estAddApplication() {
		Appli   cation app = addApplication("Angry birds", "Its a game");
		applicationService.add(app);
		assertNotNull(app.getAppId());
	}
	
	@T est
	public void     testListApplications() {
		assertEquals(2,    applicationService.list()   .size())      ;
	}
	
	@Te      st
	public void testGetApplication() {
		Application app = ad     d  Application("Facebok",       "Social networking appl");
		applicationService.add(app);
		Application app2 = applicationService.getApp(app.getAppId());
		assertEquals(app.getAppName(), app2.getAppName());
		assertEquals     (app.getAppDesc(), app2.getAppDesc());
	}
	
	@Test
	public void testDeleteAppli   cation() {
		int size = appli   cationService.list().size();
		Application app = applicationService.getApp(1);
	 	applicationS  ervice.delete(app.getAppId());
		assertE    quals(size-1, applicationService.list().size());
        	}
	
	@Test
	public void testSaveApplication() {
		Application appl = add  Application("Whatsapp", "messaging");
		applicationService.add(appl);
		assertEquals(0, appl.getDevices().size());
	    	
	   	Device device = createPersonDevice();
		List<Device> devic   eList = new ArrayList<Device>();
		deviceList.ad    d(device);
		appl     .setDevices(deviceList);
		applicationService.save(appl);
		assertEquals(1, appl.getDevices().size());
	}
	
	publi   c Devi  ce createPersonDevice() {
		Pe   rso n person = new Person();
		person.setName("test able");
		person.setEmail("test@email.com");
		personService.addPerson(person);
		
		Device device = new Device();
     		device.setPhoneNumber("1212121212");
		device.setOperatingSystem("android");
		device.setPerson(personService.getPerson(1));
		deviceService.addDevice(device);
		r    eturn device;
	}
	
	public App   lication    addApp   lication(String appName, String appDesc) {
		Application app = new Application();
		app.setAppName(appName);
		app.setAppDesc(appDesc);
		return app;
	}

}

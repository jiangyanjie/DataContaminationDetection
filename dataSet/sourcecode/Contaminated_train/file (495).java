package ru.babin.confanalize.analizer;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.babin.confanalize.analizer.ConfigSectionAnalizer;
import ru.babin.confanalize.model.ConfigSection;

public class ConfigSectionAnalizerTest {

	
	@Test
	public void testIsOK1(){
		ConfigSectionAnalizer csa = new ConfigSectionAnalizer();
		csa.addString("#===================================");
		csa.addString("# 2. Test section");
		csa.addString("#===================================");
		assertTrue(csa.isValidObject());
	}
	
	@Test
	public void testIsOK1_2(){
		ConfigSectionAnalizer csa = new ConfigSectionAnalizer();
		csa.addString("#===================================");
		csa.addString("# 2.4 Test section");
		csa.addString("#===================================");
		assertTrue(csa.isValidObject());
	}
	
	@Test
	public void testIsOK1_3(){
		ConfigSectionAnalizer csa = new ConfigSectionAnalizer();
		csa.addString("#===================================");
		csa.addString("# 2.4.3.5 Test section");
		csa.addString("#===================================");
		assertTrue(csa.isValidObject());
	}
	
	@Test
	public void testIsOK2(){
		ConfigSectionAnalizer csa = new ConfigSectionAnalizer();
		csa.addString("#-----------------------------------");
		csa.addString("# 2.4 Test section");
		csa.addString("#-----------------------------------");
		assertTrue(csa.isValidObject());
	}
	
	@Test
	public void testIsOK3(){
		ConfigSectionAnalizer csa = new ConfigSectionAnalizer();
		csa.addString("#####################################");
		csa.addString("# 2.4 Test section");
		csa.addString("#####################################");
		assertTrue(csa.isValidObject());
	}
	
	@Test
	public void test_addFourString(){
		ConfigSectionAnalizer csa = new ConfigSectionAnalizer();
		csa.addString("#####################################");
		csa.addString("# 2.4 Test section");
		csa.addString("#####################################");
		assertTrue(csa.isValidObject());
		assertFalse(csa.isValidObjectForAddedValue("# sdfsdfffff"));
		assertTrue(csa.isValidObject());
	}
	
	@Test
	public void test_checkParameters(){
		ConfigSectionAnalizer csa = new ConfigSectionAnalizer();
		csa.addString("#####################################");
		csa.addString("# 2.4.8 Test section");
		csa.addString("#####################################");
		assertTrue(csa.isValidObject());
		
		ConfigSection s = csa.prepareObject();
		assertEquals(s.name, "Test section");
		assertEquals(s.index , "2.4.8");
		
	}
	
	
	
}
